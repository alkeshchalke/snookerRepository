package com.projectwork.action.customer.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.LogTimeDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class ViewSelectedCustomerRecords extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -8599178118519708615L;

    private HttpServletRequest request;

    String customerID;

    List<LogTimeBean> selectedCustomerRecordsList = new ArrayList<LogTimeBean>();

    List<LogTimeDTO> customerRecordsDTOs = new ArrayList<LogTimeDTO>();
    
    int finalTotalPayableAmount = 0;

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
        String custID = customerServiceImplObj.getCustomerIDFromName(customerID);
        
        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        
        selectedCustomerRecordsList = logTimeServiceImplObj.viewSelectedCustomerRecords(custID);

        Iterator<LogTimeBean> iterator = selectedCustomerRecordsList.iterator();

        while (iterator.hasNext())
        {
            LogTimeDTO dto = getselectedCustomerRecordsListDTO(iterator.next());
            customerRecordsDTOs.add(dto);
        }

        request.setAttribute("finalTotalPayableAmount", finalTotalPayableAmount);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private LogTimeDTO getselectedCustomerRecordsListDTO(LogTimeBean bean)
    {
        LogTimeDTO dto = new LogTimeDTO();

        dto.setCustomerID(customerID);

        dto.setBusinessDate(bean.getBusinessDate());
        dto.setCustMatchesTotalBill(bean.getCustMatchesTotalBill());
        dto.setCustBeveragesTotalBill(bean.getCustBeveragesTotalBill());
        dto.setTotalBill(bean.getCustMatchesTotalBill() + bean.getCustBeveragesTotalBill());
        dto.setCustPaidBill(bean.getCustPaidBill());
        dto.setCustRemainingBill(bean.getCustRemainingBill());
        
        finalTotalPayableAmount = finalTotalPayableAmount + bean.getCustRemainingBill();

        return dto;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public List<LogTimeDTO> getCustomerRecordsDTOs()
    {
        return customerRecordsDTOs;
    }

    public void setCustomerRecordsDTOs(List<LogTimeDTO> customerRecordsDTOs)
    {
        this.customerRecordsDTOs = customerRecordsDTOs;
    }
}
