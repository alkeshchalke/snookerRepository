package com.projectwork.action.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.BusinessDayInformationBean;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.dto.LogTimeDTO;
import com.projectwork.impl.CustomerServiceImpl;
import com.projectwork.impl.LogTimeServiceImpl;

public class ViewHistoricalReportAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = 8562664745268288087L;

    private HttpServletRequest request;

    private List<LogTimeDTO> businessDayCustomerRecordsDTOs = new ArrayList<LogTimeDTO>();

    private List<BusinessDayInformationDTO> businessDayInformationDTO = new ArrayList<BusinessDayInformationDTO>();

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
        // Code to mark check out time for all users and need confirmation on
        // what to do for pending money.

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();

        List<LogTimeBean> businessDayCustomerRecords = logTimeServiceImplObj.getAllDayCustomerRecords();

        Iterator<LogTimeBean> iterator = businessDayCustomerRecords.iterator();

        while (iterator.hasNext())
        {
            LogTimeDTO dto = getLogTimeDTO(iterator.next());
            businessDayCustomerRecordsDTOs.add(dto);
        }

        request.getSession().setAttribute("businessDayCustomerRecordsDTOs", businessDayCustomerRecordsDTOs);

        BusinessDayInformationBean bean = logTimeServiceImplObj.getAllDayInformationDetails();
        businessDayInformationDTO = createBusinessDayInformationDTO(bean);

        request.getSession().setAttribute("businessDayInformationDTO", businessDayInformationDTO);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private LogTimeDTO getLogTimeDTO(LogTimeBean bean)
    {
        LogTimeDTO dto = new LogTimeDTO();

        dto.setCustomerID(bean.getCustomerID());
        
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
        String custName= customerServiceImplObj.getCustomerNameFromID(bean.getCustomerID()); 
        dto.setCustName(custName);
        
        dto.setCustBeveragesTotalBill(bean.getCustBeveragesTotalBill());
        dto.setCustMatchesTotalBill(bean.getCustMatchesTotalBill());

        int totalBill = bean.getCustBeveragesTotalBill() + bean.getCustMatchesTotalBill();
        dto.setTotalBill(totalBill);

        dto.setCustPaidBill(bean.getCustPaidBill());
        dto.setCustRemainingBill(bean.getCustRemainingBill());

        return dto;
    }

    private List<BusinessDayInformationDTO> createBusinessDayInformationDTO(BusinessDayInformationBean bean)
    {
        BusinessDayInformationDTO dto = new BusinessDayInformationDTO();

        dto.setIncomeFromMatches(String.valueOf(bean.getIncomeFromMatches()));
        dto.setIncomeFromBeverages(String.valueOf(bean.getIncomeFromBeverages()));
        dto.setTotalBusiness(String.valueOf(bean.getTotalBusiness()));
        dto.setTotalMoneyPaid(String.valueOf(bean.getTotalMoneyPaid()));
        dto.setTotalBalanceAmount(String.valueOf(bean.getTotalBalanceAmount()));

        businessDayInformationDTO.add(dto);

        return businessDayInformationDTO;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<LogTimeDTO> getBusinessDayCustomerRecordsDTOs()
    {
        return businessDayCustomerRecordsDTOs;
    }

    public void setBusinessDayCustomerRecordsDTOs(List<LogTimeDTO> businessDayCustomerRecordsDTOs)
    {
        this.businessDayCustomerRecordsDTOs = businessDayCustomerRecordsDTOs;
    }

    public List<BusinessDayInformationDTO> getBusinessDayInformationDTO()
    {
        return businessDayInformationDTO;
    }

    public void setBusinessDayInformationDTO(List<BusinessDayInformationDTO> businessDayInformationDTO)
    {
        this.businessDayInformationDTO = businessDayInformationDTO;
    }
}
