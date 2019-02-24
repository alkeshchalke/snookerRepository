package com.projectwork.action.matches.rummy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class DistributeAmountInAllPlayersAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = -4055258020719182044L;

    private HttpServletRequest request;

    private String matchNo;

    private String markedPlayer;

    private String loosingPlayer1;

    private String loosingPlayer2;

    private String loosingPlayer3;

    private String loosingPlayer4;

    private String loosingPlayer5;

    private String loosingPlayer6;

    private int playersToPayCount = 0;

    private List<String> payingPlayersList = new ArrayList<String>();

    private List<Integer> payingPlayersAmountList = new ArrayList<Integer>();

    private List<String> loosingPlayersNamesList = new ArrayList<String>();

    private List<String> markedPlayersList = new ArrayList<String>();

    private List<int[]> markedPlayersSharesList = new ArrayList<int[]>();

    private List<MatchDetailsDTO> finishMatchDTO = new ArrayList<MatchDetailsDTO>();

    private Map<String, String> paymentContributorsMap = new HashMap<String, String>();

    private int paymentAmount;

    private String payingPlayers = "";

    @SuppressWarnings("unchecked")
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
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        // Find the list of all players playing in the match.
        String[] playersInTheMatch = bean.getPlayingCustomerID().split("_");

        // Get the total number of players that will be paying the losing
        // amount.
        playersToPayCount = playersInTheMatch.length;
        request.getSession().setAttribute("playersToPayCount", playersToPayCount);

        String payAmount = getText("matchRate." + bean.getTableNo() + "." + bean.getFrameType());

        /*
         * Check if all numbers in the payeeOption are same. If yes then
         * directly show the next page with all the values hard coded. if not
         * then go with the current flow until there is no need to show the drop
         * down.
         */

        if (request.getParameter("markedPlayer") == null)
        {
            Integer payAmt = Integer.parseInt(payAmount);

            if (payAmt % playersToPayCount == 0)
            {
                int divisionAmt = payAmt / playersToPayCount;

                if (divisionAmt % 5 == 0)
                {
                    request.getSession().setAttribute("matchNumberforRummy", matchNo);
                    forwardString = "showPlayerContributionPage";
                    return forwardString;
                }
            }

            if (bean.getTableNo().equals("Table1") && playersToPayCount == 5)
            {
                payingPlayersAmountList.clear();
                
                payingPlayersAmountList.add(20);
                payingPlayersAmountList.add(25);
                payingPlayersAmountList.add(25);
                payingPlayersAmountList.add(25);
                payingPlayersAmountList.add(25);
            }

            if (bean.getTableNo().equals("Table2") && playersToPayCount == 3)
            {
                payingPlayersAmountList.add(30);
                payingPlayersAmountList.add(35);
                payingPlayersAmountList.add(35);
            }

            if (bean.getTableNo().equals("Table2") && playersToPayCount == 6)
            {
                payingPlayersAmountList.add(20);
                payingPlayersAmountList.add(20);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
            }

            if (bean.getTableNo().equals("Table3") && playersToPayCount == 3)
            {
                payingPlayersAmountList.add(20);
                payingPlayersAmountList.add(30);
                payingPlayersAmountList.add(30);
            }

            if (bean.getTableNo().equals("Table3") && playersToPayCount == 5)
            {
                payingPlayersAmountList.add(20);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
            }

            if (bean.getTableNo().equals("Table3") && playersToPayCount == 6)
            {
                payingPlayersAmountList.add(10);
                payingPlayersAmountList.add(10);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
                payingPlayersAmountList.add(15);
            }

            request.getSession().setAttribute("payingPlayersAmountList", payingPlayersAmountList);
        }

        // Put list of all paying players in the list

        for (int i = 0; i < playersInTheMatch.length; i++)
        {
            payingPlayersList.add(playersInTheMatch[i]);
        }

        // The if condition will be set to true only when the user is being
        // redirected after clicking on 'confirm' button.

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

        if (request.getParameter("markedPlayer") != null)
        {
            markedPlayer = request.getParameter("markedPlayer");
            markedPlayer = customerServiceImplObj.getCustomerIDFromName(markedPlayer);
            markedPlayersList = (List<String>)request.getSession().getAttribute("markedPlayersList");
            markedPlayersList.add(markedPlayer);

            String playerName = customerServiceImplObj.getCustomerNameFromID(markedPlayer);
            payingPlayersAmountList = (List<Integer>)request.getSession().getAttribute("payingPlayersAmountList");

            paymentContributorsMap = (Map<String, String>)request.getSession().getAttribute("paymentContributorsMap");

            if (paymentContributorsMap == null)
            {
                paymentContributorsMap = new HashMap<String, String>();
            }

            paymentContributorsMap.put(playerName, String.valueOf(payingPlayersAmountList.get(0)));

            payingPlayersAmountList.remove(0);
            request.getSession().setAttribute("payingPlayersAmountList", payingPlayersAmountList);
            request.getSession().setAttribute("paymentContributorsMap", paymentContributorsMap);

        }

        // Keep all the players marked to pay in session.

        request.getSession().setAttribute("markedPlayersList", markedPlayersList);

        // Remove the recently marked player from the drop down.

        for (String markedPlayers : markedPlayersList)
        {
            payingPlayersList.remove(markedPlayers);
        }

        for (String payingPlayerID : payingPlayersList)
        {
            String payingPlayerName = customerServiceImplObj.getCustomerNameFromID(payingPlayerID);
            loosingPlayersNamesList.add(payingPlayerName);
        }

        payingPlayersList = loosingPlayersNamesList;

        if (isDuplicateEntryFound())
        {
            request.getSession().setAttribute("payingPlayersList", payingPlayersList);
            request.getSession().setAttribute("matchNumberforRummy", matchNo);
            forwardString = "showPlayerDiffContributionPage";
            return forwardString;
        }

        // This variable will be used in the java script to call appropriate
        // condition.

        int currentDropdownIndex = markedPlayersList.size();

        MatchDetailsDTO dto = getMatchDetailsDTO(bean, currentDropdownIndex);
        request.getSession().setAttribute("currentDropdownIndex", currentDropdownIndex);

        finishMatchDTO.add(dto);

        request.getSession().setAttribute("markedPlayersSharesList", markedPlayersSharesList);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private MatchDetailsDTO getMatchDetailsDTO(MatchDetailsBean bean, int currentDropdownIndex)
    {
        MatchDetailsDTO dto = new MatchDetailsDTO();

        dto.setMatchNo(bean.getMatchNo());
        dto.setBusinessDate(bean.getBusinessDate());

        dto.setEmployeeID(bean.getEmployeeID());
        dto.setFrameType(bean.getFrameType());

        dto.setTableNo(bean.getTableNo());
        dto.setPlayingCustomerID(bean.getPlayingCustomerID().replaceAll("_", " "));

        SimpleDateFormat newformatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        dto.setFrameStartTime(newformatter.format(bean.getFrameStartTime().getTime()));
        dto.setFrameEndTime(newformatter.format(Calendar.getInstance().getTime()));

        String paymentAmount = getText("matchRate." + bean.getTableNo() + "." + bean.getFrameType());
        dto.setPaymentAmount(paymentAmount);

        if (bean.getTableNo().equals("Table1") && playersToPayCount == 5)
        {
            dto.setLoosingPlayer1Amount(20);
            dto.setLoosingPlayer2Amount(25);
            dto.setLoosingPlayer3Amount(25);
            dto.setLoosingPlayer4Amount(25);
            dto.setLoosingPlayer5Amount(25);

            int[] amountShare = { 20, 25, 25, 25, 25 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        if (bean.getTableNo().equals("Table2") && playersToPayCount == 3)
        {
            dto.setLoosingPlayer1Amount(30);
            dto.setLoosingPlayer2Amount(35);
            dto.setLoosingPlayer3Amount(35);

            int[] amountShare = { 30, 35, 35 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        if (bean.getTableNo().equals("Table2") && playersToPayCount == 6)
        {
            dto.setLoosingPlayer1Amount(20);
            dto.setLoosingPlayer2Amount(20);
            dto.setLoosingPlayer3Amount(15);
            dto.setLoosingPlayer4Amount(15);
            dto.setLoosingPlayer5Amount(15);
            dto.setLoosingPlayer6Amount(15);

            CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
            dto.setLoosingPlayer1(customerServiceImplObj.getCustomerNameFromID(markedPlayer));
            
            int[] amountShare = { 20, 20, 15, 15, 15, 15 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        if (bean.getTableNo().equals("Table3") && playersToPayCount == 3)
        {
            dto.setLoosingPlayer1Amount(20);
            dto.setLoosingPlayer2Amount(30);
            dto.setLoosingPlayer3Amount(30);

            int[] amountShare = { 20, 30, 30 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        if (bean.getTableNo().equals("Table3") && playersToPayCount == 5)
        {
            dto.setLoosingPlayer1Amount(20);
            dto.setLoosingPlayer2Amount(15);
            dto.setLoosingPlayer3Amount(15);
            dto.setLoosingPlayer4Amount(15);
            dto.setLoosingPlayer5Amount(15);

            int[] amountShare = { 20, 15, 15, 15, 15 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        if (bean.getTableNo().equals("Table3") && playersToPayCount == 6)
        {
            dto.setLoosingPlayer1Amount(15);
            dto.setLoosingPlayer2Amount(15);
            dto.setLoosingPlayer3Amount(15);
            dto.setLoosingPlayer4Amount(15);
            dto.setLoosingPlayer5Amount(10);
            dto.setLoosingPlayer6Amount(10);

            int[] amountShare = { 10, 10, 15, 15, 15, 15 };
            markedPlayersSharesList = Arrays.asList(amountShare);
        }

        payingPlayers = payingPlayers + "," + markedPlayer;
        dto.setPayingPlayer(payingPlayers);

        return dto;
    }

    private boolean isDuplicateEntryFound()
    {
        Integer first = payingPlayersAmountList.get(0);
        for (Integer s : payingPlayersAmountList)
        {
            if (s != first)
                return false;
        }
        return true;
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

    public int getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

    public String getMarkedPlayer()
    {
        return markedPlayer;
    }

    public void setMarkedPlayer(String markedPlayer)
    {
        this.markedPlayer = markedPlayer;
    }

    public List<MatchDetailsDTO> getFinishMatchDTO()
    {
        return finishMatchDTO;
    }

    public void setFinishMatchDTO(List<MatchDetailsDTO> finishMatchDTO)
    {
        this.finishMatchDTO = finishMatchDTO;
    }

    public List<String> getPayingPlayersList()
    {
        return payingPlayersList;
    }

    public void setPayingPlayersList(List<String> payingPlayersList)
    {
        this.payingPlayersList = payingPlayersList;
    }

    public String getLoosingPlayer1()
    {
        return loosingPlayer1;
    }

    public void setLoosingPlayer1(String loosingPlayer1)
    {
        this.loosingPlayer1 = loosingPlayer1;
    }

    public String getLoosingPlayer2()
    {
        return loosingPlayer2;
    }

    public void setLoosingPlayer2(String loosingPlayer2)
    {
        this.loosingPlayer2 = loosingPlayer2;
    }

    public String getLoosingPlayer3()
    {
        return loosingPlayer3;
    }

    public void setLoosingPlayer3(String loosingPlayer3)
    {
        this.loosingPlayer3 = loosingPlayer3;
    }

    public String getLoosingPlayer4()
    {
        return loosingPlayer4;
    }

    public void setLoosingPlayer4(String loosingPlayer4)
    {
        this.loosingPlayer4 = loosingPlayer4;
    }

    public String getLoosingPlayer5()
    {
        return loosingPlayer5;
    }

    public void setLoosingPlayer5(String loosingPlayer5)
    {
        this.loosingPlayer5 = loosingPlayer5;
    }

    public String getLoosingPlayer6()
    {
        return loosingPlayer6;
    }

    public void setLoosingPlayer6(String loosingPlayer6)
    {
        this.loosingPlayer6 = loosingPlayer6;
    }

    public Map<String, String> getPaymentContributorsMap()
    {
        return paymentContributorsMap;
    }

    public void setPaymentContributorsMap(Map<String, String> paymentContributorsMap)
    {
        this.paymentContributorsMap = paymentContributorsMap;
    }

}
