package com.projectwork.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;

public class LogoutAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -6138389050355326996L;
    /**
     * 
     */
    private HttpServletRequest request;

    /**
     * This method will end the current session and user will be navigated to
     * login page
     * 
     * @return success: Session is invalidated
     * @throws Exception
     */

    public String execute() throws Exception

    {
        request.getSession().removeAttribute(USER_STATUS);
        request.getSession().invalidate();
        return RETURN_SUCCESS;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}
