package com.projectwork.bean;

import java.io.Serializable;
import java.util.Calendar;

public class DateWiseReportBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7925249265550014182L;

    private String custEntryNo;
    
    private String matchNo;
    
    private String businessDate;
    
    private String customerID;
    
    private String customerName;
    
    private Calendar inTime;

    private Calendar outTime;
    
    private String frameType;
    
    private int custBeveragesTotalBill;
    
    private int custMatchesTotalBill;
    
    private int custTotalBill;
    
    private int custPaidBill;
    
    private int custRemainingBill;

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

    public String getMatchNo()
    {
        return matchNo;
    }

    public void setMatchNo(String matchNo)
    {
        this.matchNo = matchNo;
    }

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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

    public String getFrameType()
    {
        return frameType;
    }

    public void setFrameType(String frameType)
    {
        this.frameType = frameType;
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

    public int getCustTotalBill()
    {
        return custTotalBill;
    }

    public void setCustTotalBill(int custTotalBill)
    {
        this.custTotalBill = custTotalBill;
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
