package com.projectwork.action.matches.shuffle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class UpdateShuffleFrameCompletionDetailsAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -6233054203163046698L;

    private HttpServletRequest request;

    private String matchNo;

    private String payingPlayer;

    private int paymentAmount;

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

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        CustomerMatchesServiceImpl customerMatchesServiceImplObj = new CustomerMatchesServiceImpl();

        // Update Frame End Time and Payment related details in
        // BUSINESS_DAY_MATCHES table.

        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);
        payingPlayer = customerServiceImplObj.getCustomerIDFromName(payingPlayer);
        
        bean.setPayingPlayer(payingPlayer);
        bean.setPaymentAmount(paymentAmount);
        bean.setMatchStatus("Completed");

        boolean isCustomerOutTimeUpdated = matchesServiceImplObj.updateFrameCompletionDetails(bean);

        String custEntryNo = customerServiceImplObj.getActiveCustomerEntryNoFromID(payingPlayer);

        // Set the same matches total amount in Business table
        boolean isCustomerBillPaid = logTimeServiceImplObj.updateCustomerPaymentDetails(custEntryNo, 0, paymentAmount,
                0);

        // Update payment amount in Customer table.
        boolean isCustomerBillUpdated = customerServiceImplObj.updateCustomerBill(payingPlayer, 0, paymentAmount, 0);

        // Reset Customer playing status to 'N'.

        boolean isCustomerCurrentlyPlaying = customerServiceImplObj.updateCurrentlyPlayingCustomerStatus(matchNo, "N");

        // Update Payment related details in CUSTOMER_MATCHES_RECORD table.

        boolean isMatchPayeeUpdated = customerMatchesServiceImplObj.updateMatchPayeeBill(matchNo, payingPlayer,
                paymentAmount);

        if (isCustomerOutTimeUpdated && isCustomerBillPaid && isCustomerBillUpdated && isCustomerCurrentlyPlaying
                && isMatchPayeeUpdated)
        {
            forwardString = RETURN_SUCCESS;
        }

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getMatchNo()
    {
        return matchNo;
    }

    public void setMatchNo(String matchNo)
    {
        this.matchNo = matchNo;
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
}
