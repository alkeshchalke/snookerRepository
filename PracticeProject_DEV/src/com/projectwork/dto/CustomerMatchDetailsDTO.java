package com.projectwork.dto;

import java.io.Serializable;

public class CustomerMatchDetailsDTO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2679379331557624009L;

    private String custEntryNo;
    
    private String customerID;
    
    private String employeeID;
    
    private String matchNo;

    private String businessDate;

    private String playingCustomerID;
    
    private String frameType;
    
    private String tableNo;
    
    private String frameStartTime;

    private String frameEndTime;

    private String custMatchTotalBill;
    
    private String custMatchPaidBill;
    
    private String matchStatus;
    
    private String creationTime;

    private String modificationTime;

    public String getCustEntryNo()
    {
        return custEntryNo;
    }

    public void setCustEntryNo(String custEntryNo)
    {
        this.custEntryNo = custEntryNo;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
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

    public String getFrameStartTime()
    {
        return frameStartTime;
    }

    public void setFrameStartTime(String frameStartTime)
    {
        this.frameStartTime = frameStartTime;
    }

    public String getFrameEndTime()
    {
        return frameEndTime;
    }

    public void setFrameEndTime(String frameEndTime)
    {
        this.frameEndTime = frameEndTime;
    }

    public String getCustMatchTotalBill()
    {
        return custMatchTotalBill;
    }

    public void setCustMatchTotalBill(String custMatchTotalBill)
    {
        this.custMatchTotalBill = custMatchTotalBill;
    }

    public String getCustMatchPaidBill()
    {
        return custMatchPaidBill;
    }

    public void setCustMatchPaidBill(String custMatchPaidBill)
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

    public String getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(String creationTime)
    {
        this.creationTime = creationTime;
    }

    public String getModificationTime()
    {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime)
    {
        this.modificationTime = modificationTime;
    }
    
    
}
