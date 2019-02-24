package com.projectwork.dto;

import java.io.Serializable;

public class CustomerBeverageDetailsDTO implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8112669779187279224L;
    
    private String custEntryNo;
    
    private String businessDate;
    
    private String customerID;
    
    private String teaQty;
    
    private String coffeeQty;
    
    private String bottleQty;
    
    private String colddrinkQty;
    
    private String totalBill;
    
    private String customerEntryOld;
    
    /*private String paidBill;
    
    private String remainingBalance;*/

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

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getTeaQty()
    {
        return teaQty;
    }

    public void setTeaQty(String teaQty)
    {
        this.teaQty = teaQty;
    }

    public String getCoffeeQty()
    {
        return coffeeQty;
    }

    public void setCoffeeQty(String coffeeQty)
    {
        this.coffeeQty = coffeeQty;
    }

    public String getBottleQty()
    {
        return bottleQty;
    }

    public void setBottleQty(String bottleQty)
    {
        this.bottleQty = bottleQty;
    }

    public String getColddrinkQty()
    {
        return colddrinkQty;
    }

    public void setColddrinkQty(String colddrinkQty)
    {
        this.colddrinkQty = colddrinkQty;
    }

    public String getTotalBill()
    {
        return totalBill;
    }

    public void setTotalBill(String totalBill)
    {
        this.totalBill = totalBill;
    }

    public String getCustomerEntryOld()
    {
        return customerEntryOld;
    }

    public void setCustomerEntryOld(String customerEntryOld)
    {
        this.customerEntryOld = customerEntryOld;
    }


    /*public String getPaidBill()
    {
        return paidBill;
    }

    public void setPaidBill(String paidBill)
    {
        this.paidBill = paidBill;
    }

    public String getRemainingBalance()
    {
        return remainingBalance;
    }

    public void setRemainingBalance(String remainingBalance)
    {
        this.remainingBalance = remainingBalance;
    }*/
    


}
