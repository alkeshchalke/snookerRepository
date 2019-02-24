package com.projectwork.action.matches.doubles;

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

public class UpdateDoublesFrameCompletionDetailsAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = -6650870048076427562L;

    private HttpServletRequest request;

    private String matchNo;

    private String payingTeam;

    private int paymentAmount;

    private String player1;

    private String player2;

    private int payShare1;

    private int payShare2;

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
        
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        bean.setPayingPlayer(payingTeam);
        bean.setPaymentAmount(paymentAmount);
        bean.setMatchStatus("Completed");

        // Update Frame End Time and Payment related details in BUSINESS_DAY_MATCHES table.

        boolean isCustomerOutTimeUpdated = matchesServiceImplObj.updateFrameCompletionDetails(bean);

        // Reset Customer playing status to 'N'.

        boolean isCustomerCurrentlyPlaying = customerServiceImplObj.updateCurrentlyPlayingCustomerStatus(matchNo, "N");

        String payee = bean.getPayingPlayer();

        String[] losingTeamPlayers = payee.split("_");
        int[] payshares = { payShare1, payShare2 };

        for (int i = 0; i < 2; i++)
        {
            String losingTeamPlayer = customerServiceImplObj.getCustomerIDFromName(losingTeamPlayers[i]);
            String custEntryNo = customerServiceImplObj.getActiveCustomerEntryNoFromID(losingTeamPlayer);

            // Set the same matches total amount in Business table
            logTimeServiceImplObj.updateCustomerPaymentDetails(custEntryNo, 0, payshares[i], 0);

            // Update payment amount in Customer table.
            customerServiceImplObj.updateCustomerBill(losingTeamPlayer, 0, payshares[i], 0);
            
            // Update Payment related details in CUSTOMER_MATCHES_RECORD table for
            // each losing player.
            
            customerMatchesServiceImplObj.updateMatchPayeeBill(matchNo, losingTeamPlayer, payshares[i]);
        }

        if (isCustomerOutTimeUpdated && isCustomerCurrentlyPlaying)
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

    public String getPayingTeam()
    {
        return payingTeam;
    }

    public void setPayingTeam(String payingTeam)
    {
        this.payingTeam = payingTeam;
    }

    public int getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

    public String getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(String player1)
    {
        this.player1 = player1;
    }

    public String getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(String player2)
    {
        this.player2 = player2;
    }

    public int getPayShare1()
    {
        return payShare1;
    }

    public void setPayShare1(int payShare1)
    {
        this.payShare1 = payShare1;
    }

    public int getPayShare2()
    {
        return payShare2;
    }

    public void setPayShare2(int payShare2)
    {
        this.payShare2 = payShare2;
    }
}
