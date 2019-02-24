package com.projectwork.action.customer.add;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;

public class CustomerTabAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -6443237909363409615L;

    private HttpServletRequest request;

    private List<Integer> yearsList = new ArrayList<Integer>();

    private List<String> monthsList = new ArrayList<String>();

    private List<Integer> daysList = new ArrayList<Integer>();

    /**
     * Navigates to Add Employee jsp when Add employee tab is clicked.
     * 
     * @param
     * @return success: Successful entry of new employee record in the db
     * @throws Exception
     */

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

        if(request.getSession().getAttribute("errors")!=null)
        {
            String actionError = (String) request.getSession().getAttribute("errors");
            addActionError(actionError);
            request.getSession().removeAttribute("errors");
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat(FY);
        String financialYear = formatter.format(Calendar.getInstance().getTime());

        int currentYear = Integer.parseInt(financialYear);
        int yearLimit = currentYear - 80;

        for (int i = currentYear; i >= yearLimit; i--)
        {
            yearsList.add(i);
        }

        monthsList = createMonthsList();

        for (int i = 1; i <= 31; i++)
        {
            daysList.add(i);
        }

        return RETURN_SUCCESS;
    }

    private List<String> createMonthsList()
    {
        monthsList.add("January");
        monthsList.add("February");
        monthsList.add("March");
        monthsList.add("April");
        monthsList.add("May");
        monthsList.add("June");
        monthsList.add("July");
        monthsList.add("August");
        monthsList.add("September");
        monthsList.add("October");
        monthsList.add("November");
        monthsList.add("December");

        return monthsList;
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

    public List<Integer> getYearsList()
    {
        return yearsList;
    }

    public void setYearsList(List<Integer> yearsList)
    {
        this.yearsList = yearsList;
    }

    public List<String> getMonthsList()
    {
        return monthsList;
    }

    public void setMonthsList(List<String> monthsList)
    {
        this.monthsList = monthsList;
    }

    public List<Integer> getDaysList()
    {
        return daysList;
    }

    public void setDaysList(List<Integer> daysList)
    {
        this.daysList = daysList;
    }
}
