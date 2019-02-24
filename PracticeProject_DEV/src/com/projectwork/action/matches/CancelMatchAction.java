package com.projectwork.action.matches;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class CancelMatchAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 3715350891547430208L;

    private HttpServletRequest request;
    
    private String matchNo;
    
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

        if (request.getParameter("matchNo") != null)
        {
            matchNo = request.getParameter("matchNo");
            request.getSession().setAttribute("matchNoForUpdate", matchNo);
        }
        
        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);
        
        bean.setPayingPlayer("");
        bean.setPaymentAmount(0);
        bean.setMatchStatus("Cancelled");

        boolean isCustomerMatchCancelled = matchesServiceImplObj.updateFrameCompletionDetails(bean);
        
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        boolean isCustomerPlayingFlagUpdated = customerServiceImplObj.updateCurrentlyPlayingCustomerStatus(matchNo, "N");
        
        if(isCustomerMatchCancelled && isCustomerPlayingFlagUpdated)
        {
            forwardString = RETURN_SUCCESS;
        }
        
        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}
