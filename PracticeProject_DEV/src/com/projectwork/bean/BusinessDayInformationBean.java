package com.projectwork.bean;

public class BusinessDayInformationBean
{
    private String businessDate;
    
    private int incomeFromMatches;
    
    private int incomeFromBeverages;
    
    private int totalBusiness;
    
    private int totalMoneyPaid;
    
    private int totalBalanceAmount;

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public int getIncomeFromMatches()
    {
        return incomeFromMatches;
    }

    public void setIncomeFromMatches(int incomeFromMatches)
    {
        this.incomeFromMatches = incomeFromMatches;
    }

    public int getIncomeFromBeverages()
    {
        return incomeFromBeverages;
    }

    public void setIncomeFromBeverages(int incomeFromBeverages)
    {
        this.incomeFromBeverages = incomeFromBeverages;
    }

    public int getTotalBusiness()
    {
        return totalBusiness;
    }

    public void setTotalBusiness(int totalBusiness)
    {
        this.totalBusiness = totalBusiness;
    }

    public int getTotalMoneyPaid()
    {
        return totalMoneyPaid;
    }

    public void setTotalMoneyPaid(int totalMoneyPaid)
    {
        this.totalMoneyPaid = totalMoneyPaid;
    }

    public int getTotalBalanceAmount()
    {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(int totalBalanceAmount)
    {
        this.totalBalanceAmount = totalBalanceAmount;
    }

}
