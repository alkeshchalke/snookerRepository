package com.projectwork.action.matches.rummy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.MatchesServiceImpl;

public class ShowPlayerDiffContributionPageAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 8881414461459074175L;

    private HttpServletRequest request;

    private List<MatchDetailsDTO> finishMatchDTO = new ArrayList<MatchDetailsDTO>();

    private HashMap<String, String> paymentContributorsMap = new HashMap<String, String>();

    private List<String> payingPlayersList = new ArrayList<String>();

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

        String matchNo = null;
        matchNo = (String)request.getSession().getAttribute("matchNumberforRummy");

        if (matchNo == null)
        {
            return forwardString;
        }

        paymentContributorsMap = (HashMap<String, String>)request.getSession().getAttribute("paymentContributorsMap");

        if (paymentContributorsMap == null)
        {
            paymentContributorsMap = new HashMap<String, String>();
        }
        List<Integer> payingPlayersAmountList = (List<Integer>)request.getSession()
                .getAttribute("payingPlayersAmountList");

        payingPlayersList = (List<String>)request.getSession().getAttribute("payingPlayersList");

        request.getSession().removeAttribute("matchNumberforRummy");
        request.getSession().removeAttribute("paymentContributorsMap");
        request.getSession().removeAttribute("payingPlayersList");
        request.getSession().removeAttribute("payingPlayersAmountList");

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
        MatchDetailsBean bean = matchesServiceImplObj.getDetailsFromSelectedMatch(matchNo);

        MatchDetailsDTO dto = getMatchDetailsDTO(bean);
        finishMatchDTO.add(dto);

        for (int i = 0; i < payingPlayersList.size(); i++)
        {
            paymentContributorsMap.put(payingPlayersList.get(i), String.valueOf(payingPlayersAmountList.get(i)));
        }

        paymentContributorsMap = sortByValues(paymentContributorsMap);
        
        request.getSession().setAttribute("paymentContributorsMap", paymentContributorsMap);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private HashMap<String, String> sortByValues(HashMap<String, String> map)
    {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry)it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
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
        
        dto.setPaymentAmount(String.valueOf(bean.getPaymentAmount()));

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

    public HashMap<String, String> getPaymentContributorsMap()
    {
        return paymentContributorsMap;
    }

    public void setPaymentContributorsMap(HashMap<String, String> paymentContributorsMap)
    {
        this.paymentContributorsMap = paymentContributorsMap;
    }

    public List<String> getPayingPlayersList()
    {
        return payingPlayersList;
    }

    public void setPayingPlayersList(List<String> payingPlayersList)
    {
        this.payingPlayersList = payingPlayersList;
    }
}
