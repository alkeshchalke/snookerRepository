package com.projectwork.action.matches;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class FinishMatchAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -8816445541003375532L;

    private HttpServletRequest request;

    private String matchNo;

    private List<String> selectPayeeList = new ArrayList<String>();

    private List<MatchDetailsDTO> finishMatchDTO = new ArrayList<MatchDetailsDTO>();

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

        if (request.getParameter("matchNo") != null)
        {
            matchNo = request.getParameter("matchNo");
            request.getSession().setAttribute("matchNoForUpdate", matchNo);

            MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

            MatchDetailsDTO dto = getMatchDetailsDTO(bean);

            String[] selectPayeeforDropdown = bean.getPlayingCustomerID().split("_");
            CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

            if (bean.getFrameType().equals("Single_Player"))
            {
                for (int i = 0; i < selectPayeeforDropdown.length; i++)
                {
                    String player = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[i]);
                    selectPayeeList.add(player);
                }

                forwardString = "SinglePlayer";
            }

            else if (bean.getFrameType().equals("Double_Player"))
            {
                String team1 = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[0]) + "_"
                        + customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[1]);
                
                String team2 = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[2]) + "_"
                        + customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[3]);

                dto.setTeamOne(team1);
                dto.setTeamTwo(team2);

                selectPayeeList.add(team1);
                selectPayeeList.add(team2);

                forwardString = "DoublePlayer";
            }
            else if (bean.getFrameType().equals("RD"))
            {
                for (int i = 0; i < selectPayeeforDropdown.length; i++)
                {
                    String player = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[i]);
                    selectPayeeList.add(player);
                }

                forwardString = "RD";
            }
            else if (bean.getFrameType().equals("Shuffle"))
            {
                for (int i = 0; i < selectPayeeforDropdown.length; i++)
                {
                    String player = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[i]);
                    selectPayeeList.add(player);
                }

                forwardString = "Shuffle";
            }
            else
            {
                for (int i = 0; i < selectPayeeforDropdown.length; i++)
                {
                    String player = customerServiceImplObj.getCustomerNameFromID(selectPayeeforDropdown[i]);
                    selectPayeeList.add(player);
                }

                forwardString = "Rummy";
            }

            finishMatchDTO.add(dto);
        }

        return forwardString;
    }

    private MatchDetailsDTO getMatchDetailsDTO(MatchDetailsBean bean)
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
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

        if (bean.getFrameType().equals("Double_Player"))
        {
            List<String> tempList = new ArrayList<String>();
            tempList = new ArrayList<String>(Arrays.asList(bean.getPlayingCustomerID().split("_")));

            String team1 = customerServiceImplObj.getCustomerNameFromID(tempList.get(0)) + "_" + customerServiceImplObj.getCustomerNameFromID(tempList.get(1));
            String team2 = customerServiceImplObj.getCustomerNameFromID(tempList.get(2)) + "_" + customerServiceImplObj.getCustomerNameFromID(tempList.get(3));

            dto.setTeamOne(team1);
            dto.setTeamTwo(team2);
        }

        String paymentAmountstring = getText("matchRate." + bean.getTableNo() + "." + bean.getFrameType());
        int paymentAmount = Integer.parseInt(paymentAmountstring);
        
        dto.setPaymentShare1(String.valueOf(paymentAmount/2));
        dto.setPaymentShare2(String.valueOf(paymentAmount/2));
        
        dto.setPaymentAmount(String.valueOf(paymentAmount));

        return dto;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<MatchDetailsDTO> getFinishMatchDTO()
    {
        return finishMatchDTO;
    }

    public void setFinishMatchDTO(List<MatchDetailsDTO> finishMatchDTO)
    {
        this.finishMatchDTO = finishMatchDTO;
    }

    public List<String> getSelectPayeeList()
    {
        return selectPayeeList;
    }

    public void setSelectPayeeList(List<String> selectPayeeList)
    {
        this.selectPayeeList = selectPayeeList;
    }

}
