package com.projectwork.action.matches.doubles;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.AddNewMatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;

public class AddSecondDoublesTeamAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 8842906695249773371L;

    private HttpServletRequest request;

    private String matchNo;
    
    private String businessDate;
    
    private String selectedFrameType;

    private String selectedTableNo;

    private String player1;

    private String player2;
    
    private List<AddNewMatchDetailsDTO> newMatchDetailsDTOs = new ArrayList<AddNewMatchDetailsDTO>();
    
    private List<String> selectPlayerList = new ArrayList<String>();

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

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        selectPlayerList = customerServiceImplObj.getCurrentlyNonPlayingCustomerList();
        
        AddNewMatchDetailsDTO newdto = new AddNewMatchDetailsDTO();
        
        newdto = createDoublesMatchDetailsDTO();
        
        newMatchDetailsDTOs.add(newdto);
        
        forwardString = RETURN_SUCCESS;

        return forwardString;
    }
    
    private AddNewMatchDetailsDTO createDoublesMatchDetailsDTO()
    {
        AddNewMatchDetailsDTO dto = new AddNewMatchDetailsDTO();

        dto.setMatchNo(matchNo);
        dto.setBusinessDate(businessDate);
        dto.setSelectedFrameType(selectedFrameType);
        dto.setSelectedTableNo(selectedTableNo);
        
        dto.setPlayerOne(player1);
        dto.setPlayerTwo(player2);

        selectPlayerList.remove(player1);
        selectPlayerList.remove(player2);

        return dto;
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

    public String getSelectedFrameType()
    {
        return selectedFrameType;
    }

    public void setSelectedFrameType(String selectedFrameType)
    {
        this.selectedFrameType = selectedFrameType;
    }

    public String getSelectedTableNo()
    {
        return selectedTableNo;
    }

    public void setSelectedTableNo(String selectedTableNo)
    {
        this.selectedTableNo = selectedTableNo;
    }

    public List<AddNewMatchDetailsDTO> getNewMatchDetailsDTOs()
    {
        return newMatchDetailsDTOs;
    }

    public void setNewMatchDetailsDTOs(List<AddNewMatchDetailsDTO> newMatchDetailsDTOs)
    {
        this.newMatchDetailsDTOs = newMatchDetailsDTOs;
    }

    public List<String> getSelectPlayerList()
    {
        return selectPlayerList;
    }

    public void setSelectPlayerList(List<String> selectPlayerList)
    {
        this.selectPlayerList = selectPlayerList;
    }
}
