package com.projectwork.action.logTime;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.BeveragesServiceImpl;

public class UpdateCustomerBeveragesAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware, SessionAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 2115417546474812665L;

    private HttpServletRequest request;

    String custEntryNo;

    String updateCustomer;

    String customerID;

    int teafield;

    int coffeefield;

    int bottlefield;

    int colddrinkfield;

    @SuppressWarnings("rawtypes")
    Map m;

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

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

        BeveragesServiceImpl beveragesServiceImplObj = new BeveragesServiceImpl();

        beveragesServiceImplObj.updateCustomerBeveragesQtyDetails(custEntryNo, customerID, teafield, coffeefield,
                bottlefield, colddrinkfield);

        request.getSession().setAttribute("custEntryNo", custEntryNo);
        request.getSession().setAttribute("customerID", customerID);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    public String getCustEntryNo()
    {
        return custEntryNo;
    }

    public void setCustEntryNo(String custEntryNo)
    {
        this.custEntryNo = custEntryNo;
    }

    public String getUpdateCustomer()
    {
        return updateCustomer;
    }

    public void setUpdateCustomer(String updateCustomer)
    {
        this.updateCustomer = updateCustomer;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public int getTeafield()
    {
        return teafield;
    }

    public void setTeafield(int teafield)
    {
        this.teafield = teafield;
    }

    public int getCoffeefield()
    {
        return coffeefield;
    }

    public void setCoffeefield(int coffeefield)
    {
        this.coffeefield = coffeefield;
    }

    public int getBottlefield()
    {
        return bottlefield;
    }

    public void setBottlefield(int bottlefield)
    {
        this.bottlefield = bottlefield;
    }

    public int getColddrinkfield()
    {
        return colddrinkfield;
    }

    public void setColddrinkfield(int colddrinkfield)
    {
        this.colddrinkfield = colddrinkfield;
    }

    @SuppressWarnings("rawtypes")
    public void setSession(Map m)
    {
        this.m = m;
    }
}
