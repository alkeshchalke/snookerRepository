package com.projectwork.action.customer.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;

public class SearchForCustomerAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 8357955800169066396L;

    List<String> customerList = new ArrayList<String>();

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

        CustomerServiceImpl customerServiceObject = new CustomerServiceImpl();

        customerList = customerServiceObject.populateAllCustomersList();

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<String> getCustomerList()
    {
        return customerList;
    }

    public void setCustomerList(List<String> customerList)
    {
        this.customerList = customerList;
    }

}
