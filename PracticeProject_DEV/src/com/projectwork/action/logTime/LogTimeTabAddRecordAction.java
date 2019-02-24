package com.projectwork.action.logTime;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.BeveragesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class LogTimeTabAddRecordAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    private static final long serialVersionUID = -6443237909363409615L;

    private HttpServletRequest request;

    private String custEntryNo;
    
    private String businessDate;

    private String employeeID;

    private Calendar inTime;

    private Calendar outTime;

    private String custName;
    
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

        employeeID = (String)request.getSession().getAttribute(USER_NAME);
        
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        String customerID = customerServiceImplObj.getCustomerIDFromName(custName);

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();

        LogTimeBean bean = new LogTimeBean();
        bean.setCustEntryNo(custEntryNo);
        bean.setEmployeeID(employeeID);
        bean.setCustomerID(customerID);
        bean.setBusinessDate(businessDate);

        //  Add record of the new customer into Business table.
        
        boolean isRecordInserted = logTimeServiceImplObj.addNewBusinessRecord(bean);

        //  Add record of the new customer into Beverages table.
        
        BeveragesServiceImpl beveragesServiceImplObj = new BeveragesServiceImpl();
        beveragesServiceImplObj.addNewCustomerBeverageRecord(custEntryNo, customerID);

        //  Mark Customer presence status in Customer table.
        
        boolean isCustomerPresentStatusMarked = customerServiceImplObj.updateCustomerPresenceStatus(custEntryNo,
                customerID, "Y");

        if (isRecordInserted && isCustomerPresentStatusMarked)
        {
            forwardString = RETURN_SUCCESS;
        }
        else
        {
            forwardString = RETURN_FAIL;
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

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
    }

    public Calendar getInTime()
    {
        return inTime;
    }

    public void setInTime(Calendar inTime)
    {
        this.inTime = inTime;
    }

    public Calendar getOutTime()
    {
        return outTime;
    }

    public void setOutTime(Calendar outTime)
    {
        this.outTime = outTime;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }
}
