package com.projectwork.action.matches.shuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class AddNewShuffleMatchAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 94295116625197560L;

    private HttpServletRequest request;

    private String matchNo;

    private String businessDate;

    private String frameType;

    private String tableNo;

    private List<String> selectedPlayersList = new ArrayList<String>();

    public String execute() throws Exception
    {
        String forwardString = RETURN_LOGIN_ERROR;

        HttpSession session = request.getSession(false);
        if (session == null)
        {
            request.getSession().removeAttribute(USER_STATUS);
            return forwardString;
        }

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        List<String> selectedPlayersIDList = new ArrayList<String>();

        if (!selectedPlayersList.isEmpty())
        {
            for (String playerName : selectedPlayersList)
            {
                String playerID = customerServiceImplObj.getCustomerIDFromName(playerName);
                selectedPlayersIDList.add(playerID);
            }

            Integer limit = 8;

            if (selectedPlayersIDList.size() > limit)
            {
                Integer listSize = selectedPlayersIDList.size();

                String[] errorArray = { limit.toString(), listSize.toString() };

                addActionError(getText("errors.maximumPlayerLimitCrossed", errorArray));
                request.getSession().setAttribute("errors", getText("errors.maximumPlayerLimitCrossed", errorArray));

                forwardString = RETURN_ERROR;
                return forwardString;
            }
        }

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();

        String employeeID = (String)request.getSession().getAttribute(USER_NAME);

        String paymentAmountstring = getText("matchRate." + tableNo + "." + frameType);
        int paymentAmount = Integer.parseInt(paymentAmountstring);

        String playingCustomers = "";

        for (String player : selectedPlayersIDList)
        {
            playingCustomers = playingCustomers + player + "_";
        }

        playingCustomers = playingCustomers.substring(0, playingCustomers.lastIndexOf("_"));

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

        request.getSession().removeAttribute("AddNewMatchDetailsDTO");

        return forwardString;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
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

    public List<String> getSelectedPlayersList()
    {
        return selectedPlayersList;
    }

    public void setSelectedPlayersList(List<String> selectedPlayersList)
    {
        this.selectedPlayersList = selectedPlayersList;
    }

}
