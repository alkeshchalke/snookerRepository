package com.projectwork.action.customer.edit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.CustomerBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.CustomerDTO;
import com.projectwork.impl.CustomerServiceImpl;

public class EditSelectedCustomerAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -8599178118519708615L;

    private HttpServletRequest request;

    String customerID;

    String customerName;

    private List<Integer> yearsList = new ArrayList<Integer>();

    private List<String> monthsList = new ArrayList<String>();

    private List<Integer> daysList = new ArrayList<Integer>();

    List<CustomerDTO> customerRecordsDTOs = new ArrayList<CustomerDTO>();

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

        if (request.getSession().getAttribute("errors") != null)
        {
            String actionError = (String)request.getSession().getAttribute("errors");

            customerID = (String)request.getSession().getAttribute("customerID");
            customerName = customerServiceImplObj.getCustomerNameFromID(customerID);

            addActionError(actionError);
            request.getSession().removeAttribute("errors");
            request.getSession().removeAttribute("customerID");
        }

        CustomerBean bean = customerServiceImplObj.getCustomerDetailsForEdit(customerName);

        CustomerDTO dto = createEditCustomerDTO(bean);

        customerRecordsDTOs.add(dto);

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

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private CustomerDTO createEditCustomerDTO(CustomerBean bean)
    {
        CustomerDTO dto = new CustomerDTO();

        dto.setCustomerID(bean.getCustomerID());
        dto.setCustomerFirstName(bean.getCustomerFirstName());
        dto.setCustomerLastName(bean.getCustomerLastName());

        String[] customerDobArray = bean.getCustomerDob().split("-");
        dto.setCustomerDobYear(customerDobArray[0]);

        String dobMonth = getCorrspondingMonth(customerDobArray[1]);
        dto.setCustomerDobMonth(dobMonth);

        int dobDate = Integer.parseInt(customerDobArray[2]);
        dto.setCustomerDobDate(String.valueOf(dobDate));

        dto.setCustomerContactNumber(bean.getCustomerContactNumber());

        return dto;
    }

    private String getCorrspondingMonth(String selectedMonth)
    {
        String monthNumber = "00";
        switch (selectedMonth)
        {
            case "01":
                monthNumber = "January";
                break;

            case "02":
                monthNumber = "February";
                break;

            case "03":
                monthNumber = "March";
                break;

            case "04":
                monthNumber = "April";
                break;

            case "05":
                monthNumber = "May";
                break;

            case "06":
                monthNumber = "June";
                break;

            case "07":
                monthNumber = "July";
                break;

            case "08":
                monthNumber = "August";
                break;

            case "09":
                monthNumber = "September";
                break;

            case "10":
                monthNumber = "October";
                break;

            case "11":
                monthNumber = "November";
                break;

            case "12":
                monthNumber = "December";
                break;
        }
        return monthNumber;
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

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public List<CustomerDTO> getCustomerRecordsDTOs()
    {
        return customerRecordsDTOs;
    }

    public void setCustomerRecordsDTOs(List<CustomerDTO> customerRecordsDTOs)
    {
        this.customerRecordsDTOs = customerRecordsDTOs;
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
