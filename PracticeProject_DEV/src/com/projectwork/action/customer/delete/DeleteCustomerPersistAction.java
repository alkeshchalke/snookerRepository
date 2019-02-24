package com.projectwork.action.customer.delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;

public class DeleteCustomerPersistAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = -5033630584296407808L;

    private HttpServletRequest request;

    private String customerName;

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

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

        boolean isRecordDeleted = customerServiceImplObj.deleteCustomer(customerName);
        if (isRecordDeleted)
        {
            forwardString = RETURN_SUCCESS;
        }

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

}
