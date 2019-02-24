package com.projectwork.action.matches.singles;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class AddNewSinglesMatchAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -7634955900378705785L;

    private HttpServletRequest request;

    private String matchNo;

    private String businessDate;

    private String frameType;

    private String tableNo;

    private String player1;

    private String player2;

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

        String employeeID = (String)request.getSession().getAttribute(USER_NAME);

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        player1 = customerServiceImplObj.getCustomerIDFromName(player1);
        player2 = customerServiceImplObj.getCustomerIDFromName(player2);

        String playingCustomers = player1 + "_" + player2;

        String paymentAmountstring = getText("matchRate." + tableNo + "." + frameType);
        int paymentAmount = Integer.parseInt(paymentAmountstring);

        String matchNo = matchesServiceImplObj.getNextMatchSequenceNumber(businessDate);

        // Insert New Match Records into BUSINESS_DAY_MATCHES table.

        boolean isNewMatchRecordInserted = matchesServiceImplObj.addNewMatchRecord(matchNo, businessDate, employeeID,
                frameType, tableNo, playingCustomers, paymentAmount);

        // Update Playing Status to 'Y' for players in the match in CUSTOMER
        // table.

        boolean isCurrentlyPlayingCustomerStatusUpdated = customerServiceImplObj
                .updateCurrentlyPlayingCustomerStatus(matchNo, playingCustomers, "Y");

        // Get Active Customer ID and Customer Name pairs from the CUSTOMER
        // table for players playing in this match.

        Map<String, String> customerEntryNameMap = customerServiceImplObj
                .getActiveCustomerEntryDetails(playingCustomers);

        CustomerMatchesServiceImpl customerMatchesServiceImplObj = new CustomerMatchesServiceImpl();

        boolean isNewCustomerMatchRecordInserted = true;

        if (!customerEntryNameMap.isEmpty())
        {
            for (Map.Entry<String, String> pair : customerEntryNameMap.entrySet())
            {
                String currentPlayer = pair.getKey();
                String currentPlayerEntryNo = pair.getValue();

                // Enter a new record for every MatchNo-Customer pair in
                // CUSTOMER_MATCHES_RECORD table

                isNewCustomerMatchRecordInserted = customerMatchesServiceImplObj.addNewCustomerMatchRecord(
                        currentPlayerEntryNo, matchNo, businessDate, currentPlayer, frameType);

                if (!isNewCustomerMatchRecordInserted)
                {
                    break;
                }
            }
        }

        if (isNewMatchRecordInserted && isCurrentlyPlayingCustomerStatusUpdated && isNewCustomerMatchRecordInserted)
            forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
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

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public String getMatchNo()
    {
        return matchNo;
    }

    public void setMatchNo(String matchNo)
    {
        this.matchNo = matchNo;
    }

    public String getFrameType()
    {
        return frameType;
    }

    public void setFrameType(String frameType)
    {
        this.frameType = frameType;
    }

    public String getTableNo()
    {
        return tableNo;
    }

    public void setTableNo(String tableNo)
    {
        this.tableNo = tableNo;
    }
}
