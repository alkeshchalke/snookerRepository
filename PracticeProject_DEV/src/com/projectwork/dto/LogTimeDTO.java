package com.projectwork.dto;

import java.io.Serializable;

public class LogTimeDTO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -506125297233974717L;

    private String custEntryNo;
    
    private String businessDate;
    
    private String employeeID;
    
    private String customerID;
    
    private String frames;
    
    private String tableNumber;

    private String inTime;

    private String outTime;
    
    private int totalBill;
    
    private int custBeveragesTotalBill;
    
    private int custMatchesTotalBill;
    
    private int custPaidBill;
    
    private int custRemainingBill;

    private String custName;

    private String custStatus;

    private String paidStatus;

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

    public String getFrames()
    {
        return frames;
    }

    public void setFrames(String frames)
    {
        this.frames = frames;
    }

    public String getTableNumber()
    {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber)
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

    public int getTotalBill()
    {
        return totalBill;
    }

    public void setTotalBill(int totalBill)
    {
        this.totalBill = totalBill;
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

}
