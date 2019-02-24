package com.projectwork.action.matches.rd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class UpdateOngoingRDMatchAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 7797188884658694483L;

    private HttpServletRequest request;

    private String matchNo;

    private List<String> selectedPlayersList = new ArrayList<String>();

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

        if (selectedPlayersList.isEmpty())
        {
            forwardString = RETURN_ERROR;
            return forwardString;
        }

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        Integer limit = 10;

        String[] playersInTheMatch = bean.getPlayingCustomerID().split("_");

        int existingPlayersCount = playersInTheMatch.length;

        limit = limit - existingPlayersCount;

        if (selectedPlayersList.size() > limit)
        {
            Integer listSize = selectedPlayersList.size();

            String[] errorArray = { limit.toString(), listSize.toString() };

            addActionError(getText("errors.maximumPlayerLimitCrossed", errorArray));
            request.getSession().setAttribute("errors", getText("errors.maximumPlayerLimitCrossed", errorArray));

            forwardString = RETURN_ERROR;
            return forwardString;
        }

        String playingCustomers = bean.getPlayingCustomerID() + "_";

        for (String player : selectedPlayersList)
        {
            playingCustomers = playingCustomers + player + "_";
        }

        // Update the Match Records into BUSINESS_DAY_MATCHES table with new
        // Customer IDs.

        playingCustomers = playingCustomers.substring(0, playingCustomers.lastIndexOf("_"));
        bean.setPlayingCustomerID(playingCustomers);

        boolean isNewMatchRecordUpdated = matchesServiceImplObj.addNewPlayersInOngoingRDMatch(bean);

        // Update Playing Status to 'Y' for players in the match in CUSTOMER
        // table.

        String newPlayingCustomers = "";
        for (String player : selectedPlayersList)
        {
            newPlayingCustomers = newPlayingCustomers + player + "_";
        }
        newPlayingCustomers = newPlayingCustomers.substring(0, newPlayingCustomers.lastIndexOf("_"));

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        boolean isCurrentlyPlayingCustomerStatusUpdated = customerServiceImplObj
                .updateCurrentlyPlayingCustomerStatus(matchNo, newPlayingCustomers, "Y");

        // Get Active Customer ID and Customer Name pairs from the CUSTOMER
        // table for players playing in this match.

        Map<String, String> customerEntryNameMap = customerServiceImplObj
                .getActiveCustomerEntryDetails(newPlayingCustomers);

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
                        currentPlayerEntryNo, matchNo, bean.getBusinessDate(), currentPlayer, bean.getFrameType());

                if (!isNewCustomerMatchRecordInserted)
                {
                    break;
                }
            }
        }

        if (isNewMatchRecordUpdated && isCurrentlyPlayingCustomerStatusUpdated && isNewCustomerMatchRecordInserted)
            forwardString = RETURN_SUCCESS;

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

    public List<String> getSelectedPlayersList()
    {
        return selectedPlayersList;
    }

    public void setSelectedPlayersList(List<String> selectedPlayersList)
    {
        this.selectedPlayersList = selectedPlayersList;
    }
}
