package com.projectwork.bean;

public class CustomerBean
{
    String customerID;

    String customerPassword;

    String customerFirstName;

    String customerLastName;

    String customerDob;

    String customerContactNumber;
    
    int custBeverageTotalBill;
    
    int custMatchTotalBill;
    
    int custPaidBill;

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getCustomerPassword()
    {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword)
    {
        this.customerPassword = customerPassword;
    }

    public String getCustomerFirstName()
    {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName)
    {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName()
    {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName)
    {
        this.customerLastName = customerLastName;
    }

    public String getCustomerDob()
    {
        return customerDob;
    }

    public void setCustomerDob(String customerDob)
    {
        this.customerDob = customerDob;
    }

    public String getCustomerContactNumber()
    {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber)
    {
        this.customerContactNumber = customerContactNumber;
    }

    public int getCustBeverageTotalBill()
    {
        return custBeverageTotalBill;
    }

    public void setCustBeverageTotalBill(int custBeverageTotalBill)
    {
        this.custBeverageTotalBill = custBeverageTotalBill;
    }

    public int getCustMatchTotalBill()
    {
        return custMatchTotalBill;
    }

    public void setCustMatchTotalBill(int custMatchTotalBill)
    {
        this.custMatchTotalBill = custMatchTotalBill;
    }

    public int getCustPaidBill()
    {
        return custPaidBill;
    }

    public void setCustPaidBill(int custPaidBill)
    {
        this.custPaidBill = custPaidBill;
    }
}
