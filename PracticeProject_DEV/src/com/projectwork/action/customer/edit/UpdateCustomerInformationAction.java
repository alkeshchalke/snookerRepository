package com.projectwork.action.customer.edit;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.CustomerServiceImpl;

public class UpdateCustomerInformationAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -3274619693172671909L;

    private String customerID;
    
    private String customerFirstName;

    private String customerLastName;

    private String customerDob;

    private String selectedDay;

    private String selectedMonth;

    private String selectedYear;

    private String customerContactNumber;

    private HttpServletRequest request;
    
    private String currentCustomerFirstName;
    
    private String currentCustomerLastName;

    /**
     * Accepts customer addition form and inserts into db
     * 
     * @param
     * @return success: Successful entry of new customer record in the db
     * @return error: Failure
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

        CustomerServiceImpl customerServiceObject = new CustomerServiceImpl();

        boolean isRecordInserted = false;

        selectedMonth = getCorrspondingMonthNumber();

        customerDob = selectedYear + "-" + selectedMonth + "-" + selectedDay;

        if (!isLegalDate())
        {
            request.getSession().setAttribute("errors", getText("errors.invalidBirthDate"));
            request.getSession().setAttribute("customerID", customerID);
            forwardString = RETURN_ERROR;
            return forwardString;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(FY);
        String financialYear = formatter.format(Calendar.getInstance().getTime());
        int currentYear = Integer.parseInt(financialYear);
        int underAgeLimitYear = currentYear - 18;

        if (Integer.parseInt(selectedYear) > underAgeLimitYear)
        {
            request.getSession().setAttribute("errors", getText("errors.birthDateLessThanLimit"));
            request.getSession().setAttribute("customerID", customerID);
            forwardString = RETURN_ERROR;
            return forwardString;
        }
        
        if(!(currentCustomerFirstName.equalsIgnoreCase(customerFirstName) && currentCustomerLastName.equalsIgnoreCase(customerLastName)))
        {
            String newCustomerName = customerFirstName + " " + customerLastName;
            
            String dbCustomerID = customerServiceObject.getCustomerIDFromName(newCustomerName);
            if(dbCustomerID != null)
            {
                request.getSession().setAttribute("errors", getText("errors.customerAlreadyPresent"));
                request.getSession().setAttribute("customerID", customerID);
                forwardString = RETURN_ERROR;
                return forwardString;
            }
        }

        isRecordInserted = customerServiceObject.updateCustomerInformation(customerID, customerFirstName,
                customerLastName, customerDob, customerContactNumber);

        if (isRecordInserted)
        {
            forwardString = RETURN_SUCCESS;
        }

        return forwardString;
    }

    private boolean isLegalDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        return sdf.parse(customerDob, new ParsePosition(0)) != null;
    }

    private String getCorrspondingMonthNumber()
    {
        String monthNumber = "00";
        switch (selectedMonth)
        {
            case "January":
                monthNumber = "01";
                break;

            case "February":
                monthNumber = "02";
                break;

            case "March":
                monthNumber = "03";
                break;

            case "April":
                monthNumber = "04";
                break;

            case "May":
                monthNumber = "05";
                break;

            case "June":
                monthNumber = "06";
                break;

            case "July":
                monthNumber = "07";
                break;

            case "August":
                monthNumber = "08";
                break;

            case "September":
                monthNumber = "09";
                break;

            case "October":
                monthNumber = "10";
                break;

            case "November":
                monthNumber = "11";
                break;

            case "December":
                monthNumber = "12";
                break;
        }
        return monthNumber;
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

    public String getCustomerFirstName()
    {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName)
    {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName()
    {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName)
    {
        this.customerLastName = customerLastName;
    }

    public String getCustomerDob()
    {
        return customerDob;
    }

    public void setCustomerDob(String customerDob)
    {
        this.customerDob = customerDob;
    }

    public String getCustomerContactNumber()
    {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber)
    {
        this.customerContactNumber = customerContactNumber;
    }

    public String getSelectedDay()
    {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay)
    {
        this.selectedDay = selectedDay;
    }

    public String getSelectedMonth()
    {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth)
    {
        this.selectedMonth = selectedMonth;
    }

    public String getSelectedYear()
    {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear)
    {
        this.selectedYear = selectedYear;
    }

    public String getCurrentCustomerFirstName()
    {
        return currentCustomerFirstName;
    }

    public void setCurrentCustomerFirstName(String currentCustomerFirstName)
    {
        this.currentCustomerFirstName = currentCustomerFirstName;
    }

    public String getCurrentCustomerLastName()
    {
        return currentCustomerLastName;
    }

    public void setCurrentCustomerLastName(String currentCustomerLastName)
    {
        this.currentCustomerLastName = currentCustomerLastName;
    }

}
