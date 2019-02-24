package com.projectwork.action.logTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class PayCustomerCurrentBillAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 377145336218020969L;

    private HttpServletRequest request;

    String custEntryNo;

    String customerID;

    int totalCustBill;

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

        if (request.getParameter("custEntryNo") != null)
        {
            custEntryNo = request.getParameter("custEntryNo");

            LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
            boolean isCustomerBillPaid = logTimeServiceImplObj.updateCustomerPaymentDetails(custEntryNo, 0, 0, totalCustBill);
            
            // Set the same beverages total amount in Customer table

            CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
            boolean isCustomerBillUpdated = customerServiceImplObj.updateCustomerBill(customerID, 0, 0, totalCustBill);

            if (isCustomerBillPaid && isCustomerBillUpdated)
            {
                forwardString = RETURN_SUCCESS;
            }
        }

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getCustEntryNo()
    {
        return custEntryNo;
    }

    public void setCustEntryNo(String custEntryNo)
    {
        this.custEntryNo = custEntryNo;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public int getTotalCustBill()
    {
        return totalCustBill;
    }

    public void setTotalCustBill(int totalCustBill)
    {
        this.totalCustBill = totalCustBill;
    }

}
