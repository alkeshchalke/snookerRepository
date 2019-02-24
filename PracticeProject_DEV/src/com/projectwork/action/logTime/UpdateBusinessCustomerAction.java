package com.projectwork.action.logTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class UpdateBusinessCustomerAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 4972456141189683873L;

    private HttpServletRequest request;

    private static Logger logger = Logger.getLogger(UpdateBusinessCustomerAction.class);

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

        if (request.getParameter("custEntryNo") != null)
        {
            String custEntryNo = request.getParameter("custEntryNo");
            String customerID = request.getParameter("customerID");

            LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
            CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

            // Check if this Customer is part of any unfinished match which is
            // not updated in the system.

            boolean isCustomerMatchUnfinished = customerServiceImplObj.isCustomerMatchUnfinished(customerID);

            if (isCustomerMatchUnfinished)
            {
                logger.info("The customer " + customerID + " has still got a match not marked finished.");

                request.getSession().setAttribute("errors", getText("errors.customerHasUnfinishedMatch"));
                forwardString = RETURN_ERROR;
                return forwardString;
            }

            // If the Customer ID is 'Guest' then the Customer should not be
            // allowed to put out-time unless he pays the bill.

            LogTimeBean bean = logTimeServiceImplObj.getCustomerTotalBillDetails(custEntryNo);
            int totalBill = bean.getCustMatchesTotalBill() + bean.getCustBeveragesTotalBill();

            if (customerID.startsWith("guest"))
            {
                int paidBill = bean.getCustPaidBill();

                logger.info("The customer " + customerID + " is a guest customer. His Total Bill is : " + totalBill
                        + " and Paid Bill is " + paidBill);

                if (totalBill != paidBill)
                {
                    request.getSession().setAttribute("errors", getText("errors.customerHasUnpaidBill"));
                    forwardString = RETURN_ERROR;
                    return forwardString;
                }
            }

            // If Customer Bill is '0' then Update Payment Status as 'Paid' in
            // Business table

            if (totalBill == 0)
            {
                logTimeServiceImplObj.updatePaymentStatus(custEntryNo, bean.getBusinessDate(), "Paid");
            }

            // Update the Out Time

            boolean isCustomerOutTimeUpdated = logTimeServiceImplObj.updateCustomerOutTime(custEntryNo);

            // Update the Customer Table and remove the active customer no.
            // entry.

            boolean isCustomerPresentStatusUnmarked = customerServiceImplObj.updateCustomerPresenceStatus(null,
                    customerID, "N");

            if (isCustomerOutTimeUpdated && isCustomerPresentStatusUnmarked)
            {
                forwardString = RETURN_SUCCESS;
            }
        }

        return forwardString;

        /*
         * BeveragesServiceImpl beveragesServiceImplObj = new
         * BeveragesServiceImpl(); Integer remainingBillAmount =
         * beveragesServiceImplObj.getCustomerBeveragesRemainingBillDetails(
         * businessDate, customerID, custInTime, inTime); if
         * (remainingBillAmount > 0) {
         * addActionError(getText("logTimePage.balanceRemaining",
         * remainingBillAmount.toString())); forwardString = RETURN_ERROR; }
         * else { logTimeServiceImplObj.updateCustomerOutTime(index,
         * businessDate, cal); forwardString = RETURN_SUCCESS; }
         */
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

}
