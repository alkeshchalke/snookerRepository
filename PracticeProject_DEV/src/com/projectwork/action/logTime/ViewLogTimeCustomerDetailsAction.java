package com.projectwork.action.logTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.BusinessDayInformationBean;
import com.projectwork.bean.CustomerBeverageDetailsBean;
import com.projectwork.bean.CustomerMatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.dto.CustomerBeverageDetailsDTO;
import com.projectwork.dto.CustomerMatchDetailsDTO;
import com.projectwork.impl.BeveragesServiceImpl;
import com.projectwork.impl.CustomerMatchesServiceImpl;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class ViewLogTimeCustomerDetailsAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -176851714012875528L;

    private HttpServletRequest request;

    private String custEntryNo;

    private String customerName;

    private String customerID;

    List<CustomerBeverageDetailsDTO> customerBeverageDetailsDTOsList;
    
    List<CustomerBeverageDetailsDTO> customerCurrentBeverageDetailsDTOsList;

    List<CustomerMatchDetailsDTO> customerMatchDetailsDTOsList;

    List<BusinessDayInformationDTO> customerBillDetailsDTOList;

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

        // if the user has clicked hyper link on the first page then he will get
        // below values from the url.

        custEntryNo = request.getParameter("custEntryNo");
        customerName = request.getParameter("custName");

        // if the user is being redirected after updating beverage details or
        // after paying the bill then these values should be picked up from
        // Session.

        if (custEntryNo == null && customerName == null)
        {
            if (request.getSession().getAttribute("custEntryNo") != null)
            {
                custEntryNo = (String)request.getSession().getAttribute("custEntryNo");
                customerID = (String)request.getSession().getAttribute("customerID");
            }

            request.getSession().removeAttribute("custEntryNo");
        }

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        String businessDate = logTimeServiceImplObj.getActiveBusinessDate();

        // Get Matches Details for selected customer

        CustomerMatchesServiceImpl customerMatchesServiceImplObj = new CustomerMatchesServiceImpl();

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

        if (customerName != null)
        {
            customerID = customerServiceImplObj.getCustomerIDFromName(customerName);
        }

        List<CustomerMatchDetailsBean> customerMatchDetails = customerMatchesServiceImplObj
                .getCustomerBusinessDayDetails(customerID, businessDate);
        
        customerMatchDetailsDTOsList = new ArrayList<CustomerMatchDetailsDTO>();

        // Store the match details in the Match DTO

        Iterator<CustomerMatchDetailsBean> iterator = customerMatchDetails.iterator();

        while (iterator.hasNext())
        {
            CustomerMatchDetailsDTO dto = getCustomerMatchDetailsDTO(iterator.next());
            customerMatchDetailsDTOsList.add(dto);
        }

        // Get Beverages Details for selected customer

        BeveragesServiceImpl beveragesServiceImplObject = new BeveragesServiceImpl();

        List<CustomerBeverageDetailsBean> customerBeverageDetailsBeanList = beveragesServiceImplObject
                .getCustomerBeveragesDetails(customerID, businessDate);

        customerBeverageDetailsDTOsList = new ArrayList<CustomerBeverageDetailsDTO>();
        customerCurrentBeverageDetailsDTOsList = new ArrayList<CustomerBeverageDetailsDTO>();

        String activeCustomerEntryNo = customerServiceImplObj.getActiveCustomerEntryNoFromID(customerID);
        
        // Store the Beverages details in the Beverages DTO

        Iterator<CustomerBeverageDetailsBean> customerBeverageDetailsIterator = customerBeverageDetailsBeanList.iterator();
        
        while (customerBeverageDetailsIterator.hasNext())
        {
            CustomerBeverageDetailsBean bean = customerBeverageDetailsIterator.next();
            CustomerBeverageDetailsDTO dto = getCustomerBeverageDetailsDTO(bean);
            
            if (!bean.getCustEntryNo().equals(activeCustomerEntryNo))
            {
                dto.setCustomerEntryOld("yes");
                customerBeverageDetailsDTOsList.add(dto);
            }
            else
            {
                customerCurrentBeverageDetailsDTOsList.add(dto);
            }
            
        }

        // Retrieve all Beverage and Match details for the customer for the current business date

        BusinessDayInformationBean businessDayInformationBean = logTimeServiceImplObj.getCustomerTodaysTotalBillDetails(customerID, businessDate);

        if (businessDayInformationBean != null)
        {
            customerBillDetailsDTOList = new ArrayList<BusinessDayInformationDTO>();
            BusinessDayInformationDTO dto = createCustomerBillDetailsDTOList(businessDayInformationBean);
            customerBillDetailsDTOList.add(dto);

            request.setAttribute("custRemainingBalance", dto.getTotalBalanceAmount());
        }

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private BusinessDayInformationDTO createCustomerBillDetailsDTOList(BusinessDayInformationBean bean)
    {
        BusinessDayInformationDTO dto = new BusinessDayInformationDTO();

        dto.setBusinessDate(bean.getBusinessDate());
        dto.setIncomeFromMatches(String.valueOf(bean.getIncomeFromMatches()));
        dto.setIncomeFromBeverages(String.valueOf(bean.getIncomeFromBeverages()));
        dto.setTotalBusiness(String.valueOf(bean.getTotalBusiness()));
        dto.setTotalMoneyPaid(String.valueOf(bean.getTotalMoneyPaid()));
        dto.setTotalBalanceAmount(String.valueOf(bean.getTotalBalanceAmount()));

        return dto;
    }

    private CustomerMatchDetailsDTO getCustomerMatchDetailsDTO(CustomerMatchDetailsBean bean)
    {
        CustomerMatchDetailsDTO dto = new CustomerMatchDetailsDTO();

        dto.setCustEntryNo(bean.getCustEntryNo());
        dto.setMatchNo(bean.getMatchNo());
        dto.setBusinessDate(bean.getBusinessDate());
        dto.setCustomerID(bean.getPlayingCustomerID());
        dto.setFrameType(bean.getFrameType());

        dto.setEmployeeID(bean.getEmployeeID());
        dto.setTableNo(bean.getTableNo());

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
        else
        {
            dto.setFrameEndTime("Ongoing");
        }
        
        dto.setMatchStatus(matchStatus);

        dto.setCustMatchTotalBill(String.valueOf(bean.getCustMatchTotalBill()));
        return dto;
    }

    private CustomerBeverageDetailsDTO getCustomerBeverageDetailsDTO(CustomerBeverageDetailsBean bean)
    {
        CustomerBeverageDetailsDTO dto = new CustomerBeverageDetailsDTO();

        dto.setBusinessDate(bean.getBusinessDate());

        dto.setCustEntryNo(bean.getCustEntryNo());
        
        dto.setCustomerEntryOld("no");
        
        dto.setCustomerID(customerID);
        dto.setTeaQty(Integer.toString(bean.getTeaQty()));
        dto.setCoffeeQty(Integer.toString(bean.getCoffeeQty()));
        dto.setBottleQty(Integer.toString(bean.getBottleQty()));
        dto.setColddrinkQty(Integer.toString(bean.getColddrinkQty()));

        request.getSession().setAttribute("orgteafield", Integer.toString(bean.getTeaQty()));
        request.getSession().setAttribute("orgcoffeefield", Integer.toString(bean.getCoffeeQty()));
        request.getSession().setAttribute("orgbottlefield", Integer.toString(bean.getBottleQty()));
        request.getSession().setAttribute("orgcolddrinkfield", Integer.toString(bean.getColddrinkQty()));

        dto.setTotalBill(Integer.toString(bean.getTotalBill()));

        return dto;
    }

    public List<CustomerBeverageDetailsDTO> getCustomerBeverageDetailsDTOsList()
    {
        return customerBeverageDetailsDTOsList;
    }

    public void setCustomerBeverageDetailsDTOsList(List<CustomerBeverageDetailsDTO> customerBeverageDetailsDTOsList)
    {
        this.customerBeverageDetailsDTOsList = customerBeverageDetailsDTOsList;
    }

    public List<CustomerBeverageDetailsDTO> getCustomerCurrentBeverageDetailsDTOsList()
    {
        return customerCurrentBeverageDetailsDTOsList;
    }

    public void setCustomerCurrentBeverageDetailsDTOsList(
            List<CustomerBeverageDetailsDTO> customerCurrentBeverageDetailsDTOsList)
    {
        this.customerCurrentBeverageDetailsDTOsList = customerCurrentBeverageDetailsDTOsList;
    }

    public String getCustEntryNo()
    {
        return custEntryNo;
    }

    public void setCustEntryNo(String custEntryNo)
    {
        this.custEntryNo = custEntryNo;
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

    public List<CustomerMatchDetailsDTO> getCustomerMatchDetailsDTOsList()
    {
        return customerMatchDetailsDTOsList;
    }

    public void setCustomerMatchDetailsDTOsList(List<CustomerMatchDetailsDTO> customerMatchDetailsDTOsList)
    {
        this.customerMatchDetailsDTOsList = customerMatchDetailsDTOsList;
    }

    public List<BusinessDayInformationDTO> getCustomerBillDetailsDTOList()
    {
        return customerBillDetailsDTOList;
    }

    public void setCustomerBillDetailsDTOList(List<BusinessDayInformationDTO> customerBillDetailsDTOList)
    {
        this.customerBillDetailsDTOList = customerBillDetailsDTOList;
    }
}
