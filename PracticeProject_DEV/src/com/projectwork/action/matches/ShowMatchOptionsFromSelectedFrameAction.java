package com.projectwork.action.matches;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.AddNewMatchDetailsDTO;

public class ShowMatchOptionsFromSelectedFrameAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 3009601831380482049L;

    private HttpServletRequest request;

    private String selectedFrameType;

    private String selectedTableNo;

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

        switch (selectedFrameType)
        {
            case "Single_Player":
                forwardString = "SinglePlayer";
                break;

            case "Double_Player":
                forwardString = "DoublePlayer";
                break;

            case "RD":
                forwardString = "RD";
                break;

            case "Shuffle":
                forwardString = "Shuffle";
                break;

            case "Rummy":
                forwardString = "Rummy";
                break;
        }

        AddNewMatchDetailsDTO newdto = createNewMatchDetailsDTO();

        if (request.getParameter("matchNo") != null)
        {
            String matchNo = request.getParameter("matchNo");
            String businessDate = request.getParameter("businessDate");
            newdto.setMatchNo(matchNo);
            newdto.setBusinessDate(businessDate);
        }

        request.getSession().setAttribute("AddNewMatchDetailsDTO", newdto);

        return forwardString;
    }

    private AddNewMatchDetailsDTO createNewMatchDetailsDTO()
    {
        AddNewMatchDetailsDTO dto = new AddNewMatchDetailsDTO();

        dto.setSelectedFrameType(selectedFrameType);
        dto.setSelectedTableNo(selectedTableNo);

        return dto;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getSelectedFrameType()
    {
        return selectedFrameType;
    }

    public void setSelectedFrameType(String selectedFrameType)
    {
        this.selectedFrameType = selectedFrameType;
    }

    public String getSelectedTableNo()
    {
        return selectedTableNo;
    }

    public void setSelectedTableNo(String selectedTableNo)
    {
        this.selectedTableNo = selectedTableNo;
    }
}
