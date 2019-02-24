package com.projectwork.action.customer.delete;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.CustomerDTO;

public class DeleteCustomerConfirmationAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -7309994339449160523L;
    private HttpServletRequest request;
    
    private String customerName;
    
    private List<CustomerDTO> customerDTOList;
    
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
        
        request.setAttribute("customerName", customerName);
        
        customerDTOList = new ArrayList<CustomerDTO>();
        
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerName(customerName);
        
        customerDTOList.add(dto);
        
        forwardString = RETURN_SUCCESS;
        
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

    public List<CustomerDTO> getCustomerDTOList()
    {
        return customerDTOList;
    }

    public void setCustomerDTOList(List<CustomerDTO> customerDTOList)
    {
        this.customerDTOList = customerDTOList;
    }

}
