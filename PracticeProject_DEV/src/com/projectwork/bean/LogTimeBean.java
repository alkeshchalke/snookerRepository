package com.projectwork.bean;

import java.util.Calendar;

public class LogTimeBean
{
    private String custEntryNo;
    
    private String businessDate;
    
    private String employeeID;
    
    private String customerID;
    
    private Calendar inTime;

    private Calendar outTime;
    
    private int custBeveragesTotalBill;
    
    private int custMatchesTotalBill;
    
    private int custPaidBill;
    
    private int custRemainingBill;

    private String custName;

    private String custStatus;

    private String paidStatus;
    
    private Calendar creationTime;
    
    private Calendar modificationTime;

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

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
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

    public int getCustBeveragesTotalBill()
    {
        return custBeveragesTotalBill;
    }

    public void setCustBeveragesTotalBill(int custBeveragesTotalBill)
    {
        this.custBeveragesTotalBill = custBeveragesTotalBill;
    }

    public int getCustMatchesTotalBill()
    {
        return custMatchesTotalBill;
    }

    public void setCustMatchesTotalBill(int custMatchesTotalBill)
    {
        this.custMatchesTotalBill = custMatchesTotalBill;
    }

    public int getCustPaidBill()
    {
        return custPaidBill;
    }

    public void setCustPaidBill(int custPaidBill)
    {
        this.custPaidBill = custPaidBill;
    }

    public int getCustRemainingBill()
    {
        return custRemainingBill;
    }

    public void setCustRemainingBill(int custRemainingBill)
    {
        this.custRemainingBill = custRemainingBill;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public String getCustStatus()
    {
        return custStatus;
    }

    public void setCustStatus(String custStatus)
    {
        this.custStatus = custStatus;
    }

    public String getPaidStatus()
    {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus)
    {
        this.paidStatus = paidStatus;
    }

    public Calendar getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Calendar creationTime)
    {
        this.creationTime = creationTime;
    }

    public Calendar getModificationTime()
    {
        return modificationTime;
    }

    public void setModificationTime(Calendar modificationTime)
    {
        this.modificationTime = modificationTime;
    }

}
