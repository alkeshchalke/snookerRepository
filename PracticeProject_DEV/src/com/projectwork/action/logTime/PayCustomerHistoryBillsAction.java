package com.projectwork.action.logTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class PayCustomerHistoryBillsAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 377145336218020969L;

    private HttpServletRequest request;

    String custEntryNo;

    String customerID;

    int paymentAmt;

    LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();

    CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
    
    private static Logger logger = Logger.getLogger(PayCustomerHistoryBillsAction.class);

    public String execute() throws Exception
    {
        String forwardString = RETURN_ERROR;

        HttpSession session = request.getSession(false);
        if (session == null)
        {
            request.getSession().removeAttribute(USER_STATUS);
            forwardString = RETURN_LOGIN_ERROR;
            return forwardString;
        }

        // Get List of all Customer Bills

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        customerID = customerServiceImplObj.getCustomerIDFromName(customerID);

        List<LogTimeBean> businessCustomerBillDetails = logTimeServiceImplObj
                .getBusinessCustomerBillDetails(customerID);

        updateBusinessCustomerBills(businessCustomerBillDetails);

        request.getSession().setAttribute("custEntryNo", custEntryNo);
        forwardString = RETURN_SUCCESS;
        return forwardString;
    }

    private boolean updateBusinessCustomerBills(List<LogTimeBean> businessCustomerBillDetails)
    {
        int balanceAmount = 0;
        int paidAmount = paymentAmt;

        boolean isCustomerBillPaid = false;
        boolean isCustomerTableUpdated = false;

        logger.info("Payment Amount: " + paidAmount);

        businessCustomerBillDetails = sortBoughtItems(businessCustomerBillDetails);

        logger.info("After Sort: ");

        for (LogTimeBean logTimeBean : businessCustomerBillDetails)
        {
            logger.info(
                    "Entry No:  " + logTimeBean.getCustEntryNo() + "\t Bill:  " + logTimeBean.getCustRemainingBill());
        }

        Iterator<LogTimeBean> it = businessCustomerBillDetails.iterator();

        while (it.hasNext())
        {
            LogTimeBean obj = it.next();

            if (paidAmount < obj.getCustRemainingBill())
            {
                balanceAmount = paidAmount;
                isCustomerBillPaid = true;
                isCustomerTableUpdated = true;
                break;
            }

            balanceAmount = paidAmount - obj.getCustRemainingBill();
            paidAmount = balanceAmount;

            logger.info("Balance: " + balanceAmount + " Remaining Bill : " + obj.getCustRemainingBill());

            // Sort all remaining balance amounts from lowest to highest and
            // marking bills as paid.

            isCustomerBillPaid = logTimeServiceImplObj.updateCustomerHistoryPaymentDetails(obj.getCustEntryNo(),
                    obj.getCustRemainingBill());

            // Update Customer paid bill in Customer table.

            isCustomerTableUpdated = customerServiceImplObj.updateCustomerBill(customerID, 0, 0,
                    obj.getCustRemainingBill());

            it.remove();
        }

        logger.info("\nBalance Amount: " + balanceAmount);

        if (isCustomerBillPaid && isCustomerTableUpdated)
        {
            // This will be the balance amount to be adjusted depending on
            // availability and is partially paid.

            if (!businessCustomerBillDetails.isEmpty())
            {
                isCustomerBillPaid = logTimeServiceImplObj.updateCustomerPaymentDetails(
                        businessCustomerBillDetails.get(0).getCustEntryNo(), 0, 0, balanceAmount);

                isCustomerTableUpdated = customerServiceImplObj.updateCustomerBill(customerID, 0, 0, balanceAmount);
            }

        }

        return (isCustomerBillPaid && isCustomerTableUpdated);
    }

    private static List<LogTimeBean> sortBoughtItems(List<LogTimeBean> businessCustomerBillDetails)
    {
        Collections.sort(businessCustomerBillDetails, new Comparator<LogTimeBean>()
        {
            public int compare(LogTimeBean o1, LogTimeBean o2)
            {
                int num = o1.getCustRemainingBill() - o2.getCustRemainingBill();

                return num;
            }
        });

        return businessCustomerBillDetails;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

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

    public int getPaymentAmt()
    {
        return paymentAmt;
    }

    public void setPaymentAmt(int paymentAmt)
    {
        this.paymentAmt = paymentAmt;
    }

}
