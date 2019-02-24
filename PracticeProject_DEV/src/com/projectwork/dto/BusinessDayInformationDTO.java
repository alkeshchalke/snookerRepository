package com.projectwork.dto;

import java.io.Serializable;

public class BusinessDayInformationDTO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8750477965953327743L;

    private String businessDate;
    
    private String incomeFromMatches;
    
    private String incomeFromBeverages;
    
    private String totalBusiness;
    
    private String totalMoneyPaid;
    
    private String totalBalanceAmount;

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public String getIncomeFromMatches()
    {
        return incomeFromMatches;
    }

    public void setIncomeFromMatches(String incomeFromMatches)
    {
        this.incomeFromMatches = incomeFromMatches;
    }

    public String getIncomeFromBeverages()
    {
        return incomeFromBeverages;
    }

    public void setIncomeFromBeverages(String incomeFromBeverages)
    {
        this.incomeFromBeverages = incomeFromBeverages;
    }

    public String getTotalBusiness()
    {
        return totalBusiness;
    }

    public void setTotalBusiness(String totalBusiness)
    {
        this.totalBusiness = totalBusiness;
    }

    public String getTotalMoneyPaid()
    {
        return totalMoneyPaid;
    }

    public void setTotalMoneyPaid(String totalMoneyPaid)
    {
        this.totalMoneyPaid = totalMoneyPaid;
    }

    public String getTotalBalanceAmount()
    {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(String totalBalanceAmount)
    {
        this.totalBalanceAmount = totalBalanceAmount;
    }

}
