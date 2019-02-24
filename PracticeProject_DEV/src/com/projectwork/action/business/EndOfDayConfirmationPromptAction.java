package com.projectwork.action.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class EndOfDayConfirmationPromptAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 2141996990534007198L;
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
        
        // Check if there are any in-progress matches. if Yes then ask user to
        // go to Matches tab and mark them as finished

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        Integer ongoingMatchesCount = matchesServiceImplObj.getOngoingMatchesCount();

        if (ongoingMatchesCount > 0)
        {
            String[] errorArray = { ongoingMatchesCount.toString() };
            request.getSession().setAttribute("errors", getText("errors.eodRestrictionMatchesInProgress", errorArray));
            forwardString = RETURN_ERROR;
            return forwardString;
        }
        
        //   Check of there are any Customers in the system whos Out time is not recorded. If Yes then ask user to Mark Out t 
        
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        Integer unMarkedOutTimesCount = customerServiceImplObj.getUnmarkedOutTimesCount();
        
        if (unMarkedOutTimesCount > 0)
        {
            String[] errorArray = { unMarkedOutTimesCount.toString() };
            request.getSession().setAttribute("errors", getText("errors.eodRestrictionOutTimeNotRecorded", errorArray));
            forwardString = RETURN_ERROR;
            return forwardString;
        }
        
        forwardString = RETURN_SUCCESS;
        
        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}
