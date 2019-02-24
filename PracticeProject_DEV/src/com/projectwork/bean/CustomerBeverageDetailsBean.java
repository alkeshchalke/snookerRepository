package com.projectwork.bean;

public class CustomerBeverageDetailsBean
{
    private String custEntryNo;
    
    private String customerID;
    
    private String businessDate;
    
    private int TeaQty;
    
    private int coffeeQty;
    
    private int bottleQty;
    
    private int colddrinkQty;
    
    private int totalBill;
    
   /* private int paidBill;
    
    private int remainingBalance;*/

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

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public int getTeaQty()
    {
        return TeaQty;
    }

    public void setTeaQty(int teaQty)
    {
        TeaQty = teaQty;
    }

    public int getCoffeeQty()
    {
        return coffeeQty;
    }

    public void setCoffeeQty(int coffeeQty)
    {
        this.coffeeQty = coffeeQty;
    }

    public int getBottleQty()
    {
        return bottleQty;
    }

    public void setBottleQty(int bottleQty)
    {
        this.bottleQty = bottleQty;
    }

    public int getColddrinkQty()
    {
        return colddrinkQty;
    }

    public void setColddrinkQty(int colddrinkQty)
    {
        this.colddrinkQty = colddrinkQty;
    }

    public int getTotalBill()
    {
        return totalBill;
    }

    public void setTotalBill(int totalBill)
    {
        this.totalBill = totalBill;
    }

    /*public int getPaidBill()
    {
        return paidBill;
    }

    public void setPaidBill(int paidBill)
    {
        this.paidBill = paidBill;
    }

    public int getRemainingBalance()
    {
        return remainingBalance;
    }

    public void setRemainingBalance(int remainingBalance)
    {
        this.remainingBalance = remainingBalance;
    }*/
}

