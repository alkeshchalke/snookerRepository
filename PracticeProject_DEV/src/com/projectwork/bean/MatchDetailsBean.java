package com.projectwork.bean;

import java.util.Calendar;

public class MatchDetailsBean
{
    private String matchNo;

    private String businessDate;

    private String employeeID;

    private String frameType;

    private String tableNo;
    
    private String playingCustomerID;

    private Calendar frameStartTime;

    private Calendar frameEndTime;

    private String payingPlayer;
    
    private int paymentAmount;
    
    private String matchStatus;

    private Calendar creationTime;

    private Calendar modificationTime;

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

    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
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

    public String getPlayingCustomerID()
    {
        return playingCustomerID;
    }

    public void setPlayingCustomerID(String playingCustomerID)
    {
        this.playingCustomerID = playingCustomerID;
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

    public String getPayingPlayer()
    {
        return payingPlayer;
    }

    public void setPayingPlayer(String payingPlayer)
    {
        this.payingPlayer = payingPlayer;
    }

    public int getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount)
    {
        this.paymentAmount = paymentAmount;
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
