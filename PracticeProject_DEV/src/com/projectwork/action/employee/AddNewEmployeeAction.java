package com.projectwork.action.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.EmployeeServiceImpl;

public class AddNewEmployeeAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -3274619693172671909L;

    private String employeeID;

    private String employeePassword;

    private String employeeFirstName;

    private String employeeLastName;

    private HttpServletRequest request;

    /**
     * Accepts employee addition form and inserts into db
     * 
     * @param
     * @return success: Successful entry of new employee record in the db
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
        
        EmployeeServiceImpl employeeServiceObject = new EmployeeServiceImpl();
        boolean isRecordInserted = false;

        isRecordInserted = employeeServiceObject.addNewEmployee(employeeID, employeePassword, employeeFirstName,
                employeeLastName);

        if (isRecordInserted)
        {
            return RETURN_SUCCESS;
        }
        
        return RETURN_ERROR;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
    }

    public String getEmployeePassword()
    {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword)
    {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeFirstName()
    {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName)
    {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName()
    {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName)
    {
        this.employeeLastName = employeeLastName;
    }
}
