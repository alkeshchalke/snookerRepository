package com.projectwork.action.matches.rd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class OptionsToAddPlayerInOngoingRDMatchAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 4895565733386223653L;

    private HttpServletRequest request;

    private List<MatchDetailsDTO> addRDPlayerDTO = new ArrayList<MatchDetailsDTO>();

    private List<String> selectPlayerList = new ArrayList<String>();
    
    public String execute() throws Exception
    {
        String forwardString = RETURN_LOGIN_ERROR;

        HttpSession session = request.getSession(false);
        if (session == null)
        {
            request.getSession().removeAttribute(USER_STATUS);
            return forwardString;
        }

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();

        if (request.getParameter("matchNo") != null)
        {
            String matchNo = request.getParameter("matchNo");
            request.getSession().setAttribute("matchNoForUpdate", matchNo);

            MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

            MatchDetailsDTO dto = getMatchDetailsDTO(bean);
            
            addRDPlayerDTO.add(dto);

            forwardString = RETURN_SUCCESS;
        }

        return forwardString;
    }

    private MatchDetailsDTO getMatchDetailsDTO(MatchDetailsBean bean)
    {
        MatchDetailsDTO dto = new MatchDetailsDTO();

        dto.setMatchNo(bean.getMatchNo());
        dto.setBusinessDate(bean.getBusinessDate());

        dto.setEmployeeID(bean.getEmployeeID());
        dto.setFrameType(bean.getFrameType());

        dto.setTableNo(bean.getTableNo());
        dto.setPlayingCustomerID(bean.getPlayingCustomerID().replaceAll("_", " "));

        SimpleDateFormat newformatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        dto.setFrameStartTime(newformatter.format(bean.getFrameStartTime().getTime()));

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        selectPlayerList = customerServiceImplObj.populateAllCustomersList();

        String[] playersInTheMatch = bean.getPlayingCustomerID().split("_");
        
        for (int i = 0; i < playersInTheMatch.length; i++)
        {
            String playerName = customerServiceImplObj.getCustomerNameFromID(playersInTheMatch[i]);
            selectPlayerList.remove(playerName);
        }
        
        return dto;

    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<MatchDetailsDTO> getAddRDPlayerDTO()
    {
        return addRDPlayerDTO;
    }

    public void setAddRDPlayerDTO(List<MatchDetailsDTO> addRDPlayerDTO)
    {
        this.addRDPlayerDTO = addRDPlayerDTO;
    }

    public List<String> getSelectPlayerList()
    {
        return selectPlayerList;
    }

    public void setSelectPlayerList(List<String> selectPlayerList)
    {
        this.selectPlayerList = selectPlayerList;
    }
}
