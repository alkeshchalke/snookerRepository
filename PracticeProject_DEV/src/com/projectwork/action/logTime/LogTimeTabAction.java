package com.projectwork.action.logTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.AddNewLogTimeDTO;
import com.projectwork.dto.LogTimeDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class LogTimeTabAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    private static final long serialVersionUID = 8402900469763599313L;

    private HttpServletRequest request;

    private List<LogTimeDTO> businessDayUsersDTOs;

    private List<AddNewLogTimeDTO> newUserLogTimeDTOs;

    LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();

    List<String> customerList = new ArrayList<String>();

    List<String> custStatusList = new ArrayList<String>();

    private String custName;

    private String custStat;
    
    private static Logger logger = Logger.getLogger(LogTimeTabAction.class);

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

        request.getSession().removeAttribute("playersFromSelectedMatchList");
        request.getSession().removeAttribute("matchNoForUpdate");
        
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String businessDate = formatter.format(Calendar.getInstance().getTime());

        logger.info("The value for eodIncorrectStatus flag is :" + request.getSession().getAttribute("EODStatusIncorrect"));
        
        if(request.getSession().getAttribute("EODStatusIncorrect") != null)
        {
            String eodIncorrectStatus = (String)request.getSession().getAttribute("EODStatusIncorrect");
            
            if(eodIncorrectStatus.equalsIgnoreCase("true"))
            {
                MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();
                Integer ongoingMatchesCount = matchesServiceImplObj.getOngoingMatchesCount();
                
                logger.info("The number of matches currently in progress are :" + ongoingMatchesCount);
                
                LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
                Integer umarkedOutTimeCount = logTimeServiceImplObj.getUnmarkedOutTimeCount();
                
                logger.info("The number of customers whose out time is not marked are :" + ongoingMatchesCount);
                
                if(ongoingMatchesCount > 0 || umarkedOutTimeCount > 0)
                {
                    businessDate = logTimeServiceImplObj.getActiveBusinessDate();
                }
            }
        }

        List<LogTimeBean> businessDayUsersList = logTimeServiceImplObj.getBusinessDayUsersList(businessDate);

        businessDayUsersDTOs = new ArrayList<LogTimeDTO>();

        Iterator<LogTimeBean> iterator = businessDayUsersList.iterator();

        while (iterator.hasNext())
        {
            LogTimeDTO dto = getLogTimeDTO(iterator.next());
            businessDayUsersDTOs.add(dto);
        }

        newUserLogTimeDTOs = new ArrayList<AddNewLogTimeDTO>();
        AddNewLogTimeDTO newdto = createNewUserLogTimeDT(businessDate);
        newUserLogTimeDTOs.add(newdto);

        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        customerList = customerServiceImplObj.populateCustomerList();
        custStatusList = getListOfCustomerStatuses();

        return RETURN_SUCCESS;
    }

    private List<String> getListOfCustomerStatuses()
    {
        List<String> testcustStatusList = new ArrayList<String>();

        testcustStatusList.add("Completed");
        testcustStatusList.add("Present");

        return testcustStatusList;
    }

    private AddNewLogTimeDTO createNewUserLogTimeDT(String businessDate)
    {
        AddNewLogTimeDTO dto = new AddNewLogTimeDTO();

        dto.setBusinessDate(businessDate);

        String nextSequenceNumber = logTimeServiceImplObj.getNextIndexSequenceNumber(businessDate);
        dto.setCustEntryNo(nextSequenceNumber);
        dto.setEmployeeID((String)request.getSession().getAttribute(USER_NAME));

        return dto;
    }

    private LogTimeDTO getLogTimeDTO(LogTimeBean bean)
    {
        LogTimeDTO dto = new LogTimeDTO();

        dto.setCustEntryNo(bean.getCustEntryNo());
        dto.setBusinessDate(bean.getBusinessDate());

        dto.setEmployeeID(bean.getEmployeeID());
        dto.setCustomerID(bean.getCustomerID());
        
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        String customerName = customerServiceImplObj.getCustomerNameFromID(bean.getCustomerID());
        dto.setCustName(customerName);

        SimpleDateFormat newformatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        dto.setInTime(newformatter.format(bean.getInTime().getTime()));

        int totalBill = bean.getCustBeveragesTotalBill() + bean.getCustMatchesTotalBill();
        dto.setTotalBill(totalBill);

        dto.setPaidStatus("Not Paid");
        dto.setCustStatus("Present");

        if (bean.getOutTime() != null)
        {
            dto.setOutTime(newformatter.format(bean.getOutTime().getTime()));
            dto.setCustStatus("Completed");
        }

        int paidBill = bean.getCustPaidBill();

        if (totalBill > 0 && totalBill == paidBill)
        {
            dto.setPaidStatus("Paid");
        }

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

    public List<LogTimeDTO> getBusinessDayUsersDTOs()
    {
        return businessDayUsersDTOs;
    }

    public void setBusinessDayUsersDTOs(List<LogTimeDTO> businessDayUsersDTOs)
    {
        this.businessDayUsersDTOs = businessDayUsersDTOs;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public List<String> getCustomerList()
    {
        return customerList;
    }

    public void setCustomerList(List<String> customerList)
    {
        this.customerList = customerList;
    }

    public List<AddNewLogTimeDTO> getNewUserLogTimeDTOs()
    {
        return newUserLogTimeDTOs;
    }

    public void setNewUserLogTimeDTOs(List<AddNewLogTimeDTO> newUserLogTimeDTOs)
    {
        this.newUserLogTimeDTOs = newUserLogTimeDTOs;
    }

    public List<String> getCustStatusList()
    {
        return custStatusList;
    }

    public void setCustStatusList(List<String> custStatusList)
    {
        this.custStatusList = custStatusList;
    }

    public String getCustStat()
    {
        return custStat;
    }

    public void setCustStat(String custStat)
    {
        this.custStat = custStat;
    }
}
