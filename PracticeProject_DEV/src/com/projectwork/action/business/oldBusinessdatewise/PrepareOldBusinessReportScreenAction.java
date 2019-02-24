package com.projectwork.action.business.oldBusinessdatewise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;

public class PrepareOldBusinessReportScreenAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -9085240365828091658L;

    private HttpServletRequest request;

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

        if (request.getSession().getAttribute("errors") != null)
        {
            String actionError = (String)request.getSession().getAttribute("errors");
            addActionError(actionError);
            request.getSession().removeAttribute("errors");
        }

        forwardString = RETURN_SUCCESS;
        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}
