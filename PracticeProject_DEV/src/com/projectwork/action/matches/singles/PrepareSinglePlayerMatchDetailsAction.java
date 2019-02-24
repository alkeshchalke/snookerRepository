package com.projectwork.action.matches.singles;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.AddNewMatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class PrepareSinglePlayerMatchDetailsAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 7374555777283295922L;

    private HttpServletRequest request;

    List<MatchDetailsBean> ongoingSelectedFrameTypeMatchesList = new ArrayList<MatchDetailsBean>();

    List<String> selectPlayerList = new ArrayList<String>();

    private List<AddNewMatchDetailsDTO> newMatchDetailsDTOs = new ArrayList<AddNewMatchDetailsDTO>();

    MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();

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

        AddNewMatchDetailsDTO newdto = (AddNewMatchDetailsDTO)request.getSession()
                .getAttribute("AddNewMatchDetailsDTO");

        request.getSession().removeAttribute("AddNewMatchDetailsDTO");

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        selectPlayerList = customerServiceImplObj.populateAvailableCustomerList();

        newMatchDetailsDTOs.add(newdto);

        forwardString = RETURN_SUCCESS;
        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<String> getSelectPlayerList()
    {
        return selectPlayerList;
    }

    public void setSelectPlayerList(List<String> selectPlayerList)
    {
        this.selectPlayerList = selectPlayerList;
    }

    public List<AddNewMatchDetailsDTO> getNewMatchDetailsDTOs()
    {
        return newMatchDetailsDTOs;
    }

    public void setNewMatchDetailsDTOs(List<AddNewMatchDetailsDTO> newMatchDetailsDTOs)
    {
        this.newMatchDetailsDTOs = newMatchDetailsDTOs;
    }
}
