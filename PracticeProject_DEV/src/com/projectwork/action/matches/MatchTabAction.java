package com.projectwork.action.matches;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.AddNewMatchDetailsDTO;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class MatchTabAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = 1743920568631060216L;

    private HttpServletRequest request;

    private List<String> selectFramesList = new ArrayList<String>();

    private List<String> selectTableList = new ArrayList<String>();

    private List<String> currentlyNonPlayingCustomerList = new ArrayList<String>();

    private List<MatchDetailsDTO> ongoingMatchesDTOs = new ArrayList<MatchDetailsDTO>();

    private List<AddNewMatchDetailsDTO> newMatchDetailsDTOs = new ArrayList<AddNewMatchDetailsDTO>();

    MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();

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

        removeAllSessionAttributes();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String businessDate = formatter.format(Calendar.getInstance().getTime());

        if (request.getSession().getAttribute("EODStatusIncorrect") != null)
        {
            String eodIncorrectStatus = (String)request.getSession().getAttribute("EODStatusIncorrect");
            if (eodIncorrectStatus.equalsIgnoreCase("true"))
            {
                MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
                Integer ongoingMatchesCount = matchesServiceImplObj.getOngoingMatchesCount();

                LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
                Integer umarkedOutTimeCount = logTimeServiceImplObj.getUnmarkedOutTimeCount();

                if (ongoingMatchesCount > 0 && umarkedOutTimeCount > 0)
                {
                    businessDate = logTimeServiceImplObj.getActiveBusinessDate();
                }
            }
        }

        List<MatchDetailsBean> ongoingMatchesList = matchesServiceImplObj.getBusinessDayMatchesList(businessDate);

        Iterator<MatchDetailsBean> iterator = ongoingMatchesList.iterator();

        while (iterator.hasNext())
        {
            MatchDetailsDTO dto = getMatchDetailsDTO(iterator.next());
            ongoingMatchesDTOs.add(dto);
        }

        // Populate list of Customers who are not currently playing any match.

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        currentlyNonPlayingCustomerList = customerServiceImplObj.populateAvailableCustomerList();

        if (currentlyNonPlayingCustomerList.isEmpty())
        {
            addActionError(getText("errors.noPlayerAvailable"));
        }

        else if (currentlyNonPlayingCustomerList.size() < 2)
        {
            Integer size = currentlyNonPlayingCustomerList.size();
            String sizestr = size.toString();
            String playerLimit = "2".toString();
            addActionError(getText("errors.playerAvailableCriteriaError", sizestr, playerLimit));
        }

        // Creating a dto for a new match

        AddNewMatchDetailsDTO newdto = createNewMatchDetailsDTO(businessDate);
        newMatchDetailsDTOs.add(newdto);

        forwardString = RETURN_SUCCESS;
        return forwardString;
    }

    private AddNewMatchDetailsDTO createNewMatchDetailsDTO(String businessDate)
    {
        AddNewMatchDetailsDTO dto = new AddNewMatchDetailsDTO();

        String matchSequenceNumber = matchesServiceImplObj.getNextMatchSequenceNumber(businessDate);
        dto.setMatchNo(matchSequenceNumber);
        dto.setBusinessDate(businessDate);
        dto.setEmployeeID((String)request.getSession().getAttribute(USER_NAME));

        if (!currentlyNonPlayingCustomerList.isEmpty())
        {
            int availablePlayers = currentlyNonPlayingCustomerList.size();

            if (availablePlayers >= 2)
            {
                selectFramesList.add("Single_Player");
                selectFramesList.add("Rummy");
                selectFramesList.add("Shuffle");
                selectFramesList.add("RD");
            }
            if (availablePlayers >= 4)
            {
                selectFramesList.add("Double_Player");
            }
        }

        dto.setFrameType(selectFramesList);

        // List of currently available tables

        List<String> unAvailableTablesList = matchesServiceImplObj.getUnavailableTableDetails();
        selectTableList = getListOfTables(unAvailableTablesList);

        dto.setTableNo(selectTableList);

        return dto;
    }

    private List<String> getListOfTables(List<String> unAvailableTablesList)
    {
        List<String> testtableList = new ArrayList<String>();

        testtableList.add("Table1");
        testtableList.add("Table2");
        testtableList.add("Table3");

        if (!unAvailableTablesList.isEmpty())
        {
            for (String removetable : unAvailableTablesList)
            {
                testtableList.remove(removetable);
            }
        }

        return testtableList;
    }

    private MatchDetailsDTO getMatchDetailsDTO(MatchDetailsBean bean)
    {
        MatchDetailsDTO dto = new MatchDetailsDTO();

        dto.setMatchNo(bean.getMatchNo());
        dto.setBusinessDate(bean.getBusinessDate());

        dto.setEmployeeID(bean.getEmployeeID());
        dto.setFrameType(bean.getFrameType());

        dto.setTableNo(bean.getTableNo());

        String[] listOfPlayingCustomerIDs = bean.getPlayingCustomerID().split("_");

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        String playingCustomers = "";

        for (int i = 0; i < listOfPlayingCustomerIDs.length; i++)
        {
            String playerName = customerServiceImplObj.getCustomerNameFromID(listOfPlayingCustomerIDs[i]);
            playingCustomers = playingCustomers + playerName + ",";
        }

        playingCustomers = playingCustomers.substring(0, playingCustomers.lastIndexOf(","));
        dto.setPlayingCustomerID(playingCustomers);

        SimpleDateFormat newformatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        dto.setFrameStartTime(newformatter.format(bean.getFrameStartTime().getTime()));

        String matchStatus = "Ongoing";
        if (bean.getFrameEndTime() != null)
        {
            matchStatus = bean.getMatchStatus();
            dto.setFrameEndTime(newformatter.format(bean.getFrameEndTime().getTime()));

            if (matchStatus.equalsIgnoreCase("Cancelled"))
            {
                dto.setFrameEndTime("");
            }
        }

        dto.setPaymentAmount(String.valueOf(bean.getPaymentAmount()));
        dto.setMatchStatus(matchStatus);

        if (matchStatus.equals("Completed"))
        {
            List<String> tempList = new ArrayList<String>();
            tempList = new ArrayList<String>(Arrays.asList(bean.getPayingPlayer().split("_")));

            String payingPlayers = "";

            for (String currentPlayer : tempList)
            {
                String playerName = customerServiceImplObj.getCustomerNameFromID(currentPlayer);
                payingPlayers = payingPlayers + playerName + ",";
            }

            payingPlayers = payingPlayers.substring(0, payingPlayers.lastIndexOf(","));
            dto.setPayingPlayer(payingPlayers);
        }

        List<String> dropdownPlayingCustomerIDList = new ArrayList<String>();

        if (bean.getFrameType().equals("Double_Player") && matchStatus == "Ongoing")
        {
            String playersFromSelectedMatchString = bean.getPlayingCustomerID();
            List<String> tempList = new ArrayList<String>();
            tempList = new ArrayList<String>(Arrays.asList(playersFromSelectedMatchString.split("_")));

            String team1 = customerServiceImplObj.getCustomerNameFromID(tempList.get(0)) + "_"
                    + customerServiceImplObj.getCustomerNameFromID(tempList.get(1));
            String team2 = customerServiceImplObj.getCustomerNameFromID(tempList.get(2)) + "_"
                    + customerServiceImplObj.getCustomerNameFromID(tempList.get(3));

            dropdownPlayingCustomerIDList.add(team1);
            dropdownPlayingCustomerIDList.add(team2);

        }
        else
        {
            String[] myList = bean.getPlayingCustomerID().split("_");
            dropdownPlayingCustomerIDList.add(customerServiceImplObj.getCustomerNameFromID(myList[0]));
            dropdownPlayingCustomerIDList.add(customerServiceImplObj.getCustomerNameFromID(myList[1]));
        }

        dto.setDropdownPlayingCustomerID(dropdownPlayingCustomerIDList);

        return dto;
    }
    
    public void removeAllSessionAttributes()
    {
        request.getSession().removeAttribute("businessDayCustomerRecordsDTOs");
        request.getSession().removeAttribute("businessDayInformationDTO");
        request.getSession().removeAttribute("customerID");
        request.getSession().removeAttribute("custEntryNo");
        request.getSession().removeAttribute("orgteafield");
        request.getSession().removeAttribute("orgcoffeefield");
        request.getSession().removeAttribute("orgbottlefield");
        request.getSession().removeAttribute("orgcolddrinkfield");
        request.getSession().removeAttribute("matchNoForUpdate");
        request.getSession().removeAttribute("playersToPayCount");
        request.getSession().removeAttribute("matchNumberforRummy");
        request.getSession().removeAttribute("payingPlayersAmountList");
        request.getSession().removeAttribute("paymentContributorsMap");
        request.getSession().removeAttribute("markedPlayersList");
        request.getSession().removeAttribute("payingPlayersList");
        request.getSession().removeAttribute("currentDropdownIndex");
        request.getSession().removeAttribute("markedPlayersSharesList");
        request.getSession().removeAttribute("AddNewMatchDetailsDTO");
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<String> getSelectFramesList()
    {
        return selectFramesList;
    }

    public void setSelectFramesList(List<String> selectFramesList)
    {
        this.selectFramesList = selectFramesList;
    }

    public List<MatchDetailsDTO> getOngoingMatchesDTOs()
    {
        return ongoingMatchesDTOs;
    }

    public void setOngoingMatchesDTOs(List<MatchDetailsDTO> ongoingMatchesDTOs)
    {
        this.ongoingMatchesDTOs = ongoingMatchesDTOs;
    }

    public List<AddNewMatchDetailsDTO> getNewMatchDetailsDTOs()
    {
        return newMatchDetailsDTOs;
    }

    public void setNewMatchDetailsDTOs(List<AddNewMatchDetailsDTO> newMatchDetailsDTOs)
    {
        this.newMatchDetailsDTOs = newMatchDetailsDTOs;
    }

    public List<String> getSelectTableList()
    {
        return selectTableList;
    }

    public void setSelectTableList(List<String> selectTableList)
    {
        this.selectTableList = selectTableList;
    }

    public List<String> getCurrentlyNonPlayingCustomerList()
    {
        return currentlyNonPlayingCustomerList;
    }

    public void setCurrentlyNonPlayingCustomerList(List<String> currentlyNonPlayingCustomerList)
    {
        this.currentlyNonPlayingCustomerList = currentlyNonPlayingCustomerList;
    }

}
