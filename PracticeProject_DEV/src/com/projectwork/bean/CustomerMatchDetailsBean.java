package com.projectwork.bean;

import java.util.Calendar;

public class CustomerMatchDetailsBean
{
    private String custEntryNo;
    
    private String employeeID;
    
    private String matchNo;

    private String businessDate;

    private String playingCustomerID;
    
    private String frameType;
    
    private String tableNo;
    
    private Calendar frameStartTime;

    private Calendar frameEndTime;

    private int custMatchTotalBill;
    
    private int custMatchPaidBill;
    
    private String matchStatus;
    
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

    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
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

    public String getPlayingCustomerID()
    {
        return playingCustomerID;
    }

    public void setPlayingCustomerID(String playingCustomerID)
    {
        this.playingCustomerID = playingCustomerID;
    }

    public String getFrameType()
    {
        return frameType;
    }

    public void setFrameType(String frameType)
    {
        this.frameType = frameType;
    }

    public String getTableNo()
    {
        return tableNo;
    }

    public void setTableNo(String tableNo)
    {
        this.tableNo = tableNo;
    }

    public Calendar getFrameStartTime()
    {
        return frameStartTime;
    }

    public void setFrameStartTime(Calendar frameStartTime)
    {
        this.frameStartTime = frameStartTime;
    }

    public Calendar getFrameEndTime()
    {
        return frameEndTime;
    }

    public void setFrameEndTime(Calendar frameEndTime)
    {
        this.frameEndTime = frameEndTime;
    }

    public int getCustMatchTotalBill()
    {
        return custMatchTotalBill;
    }

    public void setCustMatchTotalBill(int custMatchTotalBill)
    {
        this.custMatchTotalBill = custMatchTotalBill;
    }

    public int getCustMatchPaidBill()
    {
        return custMatchPaidBill;
    }

    public void setCustMatchPaidBill(int custMatchPaidBill)
    {
        this.custMatchPaidBill = custMatchPaidBill;
    }

    public String getMatchStatus()
    {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus)
    {
        this.matchStatus = matchStatus;
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
