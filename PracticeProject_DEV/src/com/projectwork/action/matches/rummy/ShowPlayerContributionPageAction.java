package com.projectwork.action.matches.rummy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class ShowPlayerContributionPageAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 8881414461459074175L;

    private HttpServletRequest request;

    private List<MatchDetailsDTO> finishMatchDTO = new ArrayList<MatchDetailsDTO>();    
    
    private Map<String, String> paymentContributorsMap = new HashMap<String, String>();
    
    public String execute() throws Exception
    {
        String forwardString = RETURN_ERROR;

        HttpSession session = request.getSession(false);
        if (session == null)
        {
            request.getSession().removeAttribute(USER_STATUS);
            forwardString = RETURN_LOGIN_ERROR;
            return forwardString;
        }

        String matchNo = null;
        matchNo = (String)request.getSession().getAttribute("matchNumberforRummy");

        if (matchNo == null)
        {
            return forwardString;
        }
        
        request.getSession().removeAttribute("matchNumberforRummy");
        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        int paymentAmount = bean.getPaymentAmount();
        
        // Find the list of all players playing in the match.

        String[] playersInTheMatch = bean.getPlayingCustomerID().split("_");
        
        MatchDetailsDTO dto = getMatchDetailsDTO(bean);
        finishMatchDTO.add(dto);

        int perPlayerContribution = paymentAmount/playersInTheMatch.length;

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        
        for (String currentPlayer : playersInTheMatch)
        {
            String playerName = customerServiceImplObj.getCustomerNameFromID(currentPlayer);
            paymentContributorsMap.put(playerName, String.valueOf(perPlayerContribution));
        }
        
        request.getSession().setAttribute("paymentContributorsMap", paymentContributorsMap);
        
        forwardString = RETURN_SUCCESS;

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
        dto.setFrameEndTime(newformatter.format(Calendar.getInstance().getTime()));

        return dto;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<MatchDetailsDTO> getFinishMatchDTO()
    {
        return finishMatchDTO;
    }

    public void setFinishMatchDTO(List<MatchDetailsDTO> finishMatchDTO)
    {
        this.finishMatchDTO = finishMatchDTO;
    }

    public Map<String, String> getPaymentContributorsMap()
    {
        return paymentContributorsMap;
    }

    public void setPaymentContributorsMap(Map<String, String> paymentContributorsMap)
    {
        this.paymentContributorsMap = paymentContributorsMap;
    }

}
