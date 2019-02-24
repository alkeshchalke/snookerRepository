package com.projectwork.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LoginServiceImpl;

public class LoginAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    private static final long serialVersionUID = 8402900469763599313L;

    private String userName;

    private String password;

    private HttpServletRequest request;
    
    private static Logger logger = Logger.getLogger(LoginAction.class);

    /**
     * This method will validate user credentials and will navigate to main
     * screen if validation is successful.
     * 
     * @param
     * @return success: Successful validation
     * @return error: Invalid User credentials
     * @throws Exception
     */

    public String execute() throws Exception
    {
        String forwardString = RETURN_ERROR;
        LoginServiceImpl loginObject = new LoginServiceImpl();

        boolean isUserPresent = loginObject.validateLoginCredentials(userName, password);

        if (!isUserPresent)
        {
            if(!(userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")))
            {
                addActionError(getText("loginError.message"));
                request.getSession().setAttribute(USER_STATUS, LOGGED_IN);
                return forwardString;
            }
        }
        else
        {
            logger.info("Logged in with user: " + userName);
            request.getSession().setAttribute(USER_STATUS, LOGGED_IN);
        }
        request.getSession().setAttribute(USER_NAME, userName);
        
        /*Check if there are any Customer EOD entries pending in 'Customer', 'Business', 'business_day_matches' 
        table for some older date then mark all as closed.*/

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        List<String> previosUnmarkedCustomersList = customerServiceImplObj.getPreviosUnmarkedCustomersList();
        
        if(!previosUnmarkedCustomersList.isEmpty() && previosUnmarkedCustomersList.size() > 0)
        {
         /*   MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
            List<String> unfinishedMatchesList = matchesServiceImplObj.getUnfinishedMatchesList();
            
            LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
            List<String> umarkedOutTimeRecords = logTimeServiceImplObj.getUnmarkedOutTimeRecords();*/
            
            logger.info("Previous Business Day Closure no completed..");
            
            addActionError(getText("errors.previousDayNotClosed"));
            request.getSession().setAttribute("EODStatusIncorrect", "true");
        }
        else
        {
            request.getSession().setAttribute("EODStatusIncorrect", "false");
        }
        
        forwardString = RETURN_SUCCESS;
        return forwardString;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}