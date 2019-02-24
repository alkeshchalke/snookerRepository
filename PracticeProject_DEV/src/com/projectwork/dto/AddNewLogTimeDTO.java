package com.projectwork.dto;

import java.io.Serializable;
import java.util.List;

public class AddNewLogTimeDTO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2315831746532502765L;

    private String custEntryNo;
    
    private String businessDate;
    
    private String employeeID;
    
    private List<String> customerID;
    
    private String frames;
    
    private List<String> tableNumber;

    private String inTime;

    private String outTime;

    private String custName;
    
    private List<String> custStatus;
    
    private List<String> paidStatus;

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

    public List<String> getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(List<String> customerID)
    {
        this.customerID = customerID;
    }

    public String getFrames()
    {
        return frames;
    }

    public void setFrames(String frames)
    {
        this.frames = frames;
    }

    public List<String> getTableNumber()
    {
        return tableNumber;
    }

    public void setTableNumber(List<String> tableNumber)
    {
        this.tableNumber = tableNumber;
    }

    public String getInTime()
    {
        return inTime;
    }

    public void setInTime(String inTime)
    {
        this.inTime = inTime;
    }

    public String getOutTime()
    {
        return outTime;
    }

    public void setOutTime(String outTime)
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

    public List<String> getCustStatus()
    {
        return custStatus;
    }

    public void setCustStatus(List<String> custStatus)
    {
        this.custStatus = custStatus;
    }

    public List<String> getPaidStatus()
    {
        return paidStatus;
    }

    public void setPaidStatus(List<String> paidStatus)
    {
        this.paidStatus = paidStatus;
    }


}
