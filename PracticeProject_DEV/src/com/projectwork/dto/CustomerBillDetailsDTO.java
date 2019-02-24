package com.projectwork.dto;

import java.io.Serializable;

public class CustomerBillDetailsDTO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -1535809571729429472L;

    private String custEntryNo;
    
    private String customerID;
    
    private int custMatchTotalBill;
    
    private int custBevTotalBill;
    
    private int custTotalBill;
    
    private int custPaidBill;
    
    private int custRemainingBalance;

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

    public int getCustMatchTotalBill()
    {
        return custMatchTotalBill;
    }

    public void setCustMatchTotalBill(int custMatchTotalBill)
    {
        this.custMatchTotalBill = custMatchTotalBill;
    }

    public int getCustBevTotalBill()
    {
        return custBevTotalBill;
    }

    public void setCustBevTotalBill(int custBevTotalBill)
    {
        this.custBevTotalBill = custBevTotalBill;
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

    public int getCustRemainingBalance()
    {
        return custRemainingBalance;
    }

    public void setCustRemainingBalance(int custRemainingBalance)
    {
        this.custRemainingBalance = custRemainingBalance;
    }

   
}
