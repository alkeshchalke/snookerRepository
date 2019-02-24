package com.projectwork.action.matches.rummy;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class UpdateRummyFrameCompletionDetailsAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -1094002024594075588L;

    private HttpServletRequest request;

    private String matchNo;

    @SuppressWarnings("unchecked")
    public String execute() throws Exception
    {
        String forwardString = RETURN_LOGIN_ERROR;

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        CustomerMatchesServiceImpl customerMatchesServiceImplObj = new CustomerMatchesServiceImpl();

        Map<String, String> paymentContributorsMap = (Map<String, String>)request.getSession()
                .getAttribute("paymentContributorsMap");

        if (paymentContributorsMap.isEmpty())
        {
            return forwardString;
        }

        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        String payingCustomers = "";

        for (Map.Entry<String, String> entry : paymentContributorsMap.entrySet())
        {
            String player = customerServiceImplObj.getCustomerIDFromName(entry.getKey());
            payingCustomers = payingCustomers + player + "_";
        }

        payingCustomers = payingCustomers.substring(0, payingCustomers.lastIndexOf("_"));

        bean.setPayingPlayer(payingCustomers);
        bean.setPaymentAmount(bean.getPaymentAmount());
        bean.setMatchStatus("Completed");

        boolean isCustomerOutTimeUpdated = false;
        boolean isCustomerMatchBillUpdated = false;
        boolean isCustomerBillUpdated = false;
        boolean isCustomerCurrentlyPlaying = false;
        boolean isMatchPayeeUpdated = false;

        // Update Frame End Time and Payment related details in
        // BUSINESS_DAY_MATCHES table.

        isCustomerOutTimeUpdated = matchesServiceImplObj.updateFrameCompletionDetails(bean);

        for (Map.Entry<String, String> entry : paymentContributorsMap.entrySet())
        {
            String player = customerServiceImplObj.getCustomerIDFromName(entry.getKey());
            
            String custEntryNo = customerServiceImplObj.getActiveCustomerEntryNoFromID(player);
            int amount = Integer.parseInt(entry.getValue());

            // Set the same matches total amount in Business table

            isCustomerMatchBillUpdated = logTimeServiceImplObj.updateCustomerPaymentDetails(custEntryNo, 0, amount, 0);

            // Update payment amount in Customer table.
            isCustomerBillUpdated = customerServiceImplObj.updateCustomerBill(player, 0, amount, 0);

            // Update Payment related details in CUSTOMER_MATCHES_RECORD table.

            isMatchPayeeUpdated = customerMatchesServiceImplObj.updateMatchPayeeBill(matchNo, player, amount);
            
            boolean isPairedMatchInProgress = matchesServiceImplObj.checkPairedRummyMatchStatus(bean);
            if(!isPairedMatchInProgress)
            {
                // Reset Customer playing status to 'N'.
                isCustomerCurrentlyPlaying = customerServiceImplObj.updateCurrentlyPlayingCustomerStatus(matchNo, "N");
            }
        }

        if (isCustomerOutTimeUpdated && isCustomerMatchBillUpdated && isCustomerBillUpdated && isCustomerCurrentlyPlaying
                && isMatchPayeeUpdated)
        {
            forwardString = RETURN_SUCCESS;
        }

        request.getSession().removeAttribute("markedPlayersList");
        request.getSession().removeAttribute("markedPlayersSharesList");
        request.getSession().removeAttribute("playersToPayCount");
        request.getSession().removeAttribute("paymentContributorsMap");
        request.getSession().removeAttribute("currentDropdownIndex");

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
}
