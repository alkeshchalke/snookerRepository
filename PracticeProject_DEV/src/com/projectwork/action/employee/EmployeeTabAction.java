package com.projectwork.action.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;

public class EmployeeTabAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -6443237909363409615L;
    private HttpServletRequest request;
    
    /**
     * Navigates to Add Employee jsp when Add employee tab is clicked.
     * 
     * @param 
     * @return success: Successful entry of new employee record in the db     
     * @throws Exception
     */
    
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
        
        removeAllSessionAttributes();
        
        return RETURN_SUCCESS;
    }
    
    public void removeAllSessionAttributes()
    {
        request.getSession().removeAttribute("businessDayCustomerRecordsDTOs");
        request.getSession().removeAttribute("businessDayInformationDTO");
        request.getSession().removeAttribute("customerID");
        request.getSession().removeAttribute("custEntryNo");
        request.getSession().removeAttribute("orgteafield");
        request.getSession().removeAttribute("orgcoffeefield");
        request.getSession().removeAttribute("orgbottlefield");
        request.getSession().removeAttribute("orgcolddrinkfield");
        request.getSession().removeAttribute("matchNoForUpdate");
        request.getSession().removeAttribute("playersToPayCount");
        request.getSession().removeAttribute("matchNumberforRummy");
        request.getSession().removeAttribute("payingPlayersAmountList");
        request.getSession().removeAttribute("paymentContributorsMap");
        request.getSession().removeAttribute("markedPlayersList");
        request.getSession().removeAttribute("payingPlayersList");
        request.getSession().removeAttribute("currentDropdownIndex");
        request.getSession().removeAttribute("markedPlayersSharesList");
        request.getSession().removeAttribute("AddNewMatchDetailsDTO");
    }
    
    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }
}
