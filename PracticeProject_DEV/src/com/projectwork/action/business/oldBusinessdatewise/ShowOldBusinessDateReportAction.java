package com.projectwork.action.business.oldBusinessdatewise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.BusinessDayInformationBean;
import com.projectwork.bean.DateWiseReportBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.impl.LogTimeServiceImpl;

public class ShowOldBusinessDateReportAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 6999862958664588275L;

    private HttpServletRequest request;

    private String businessDate;

    private List<DateWiseReportBean> oldBusinessDayTransactionRecords = new ArrayList<DateWiseReportBean>();
    
    private List<BusinessDayInformationDTO> oldBusinessDayInformationDTO = new ArrayList<BusinessDayInformationDTO>();

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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        Date bdate = (Date)formatter.parse(businessDate);

        businessDate = formatter.format(bdate);

        SimpleDateFormat newformatter = new SimpleDateFormat(DATE_FORMAT);
        newformatter.setLenient(false);
        businessDate = newformatter.format(bdate);

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        oldBusinessDayTransactionRecords = logTimeServiceImplObj.getDatewiseCustomerRecords(businessDate);

        request.getSession().setAttribute("oldBusinessDayTransactionRecords", oldBusinessDayTransactionRecords);
        
        BusinessDayInformationBean bean = logTimeServiceImplObj.getBusinessDayInformationDetails(businessDate);
        oldBusinessDayInformationDTO = createBusinessDayInformationDTO(bean);

        request.getSession().setAttribute("oldBusinessDayInformationDTO", oldBusinessDayInformationDTO);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }
    
    private List<BusinessDayInformationDTO> createBusinessDayInformationDTO(BusinessDayInformationBean bean)
    {
        BusinessDayInformationDTO dto = new BusinessDayInformationDTO();

        dto.setBusinessDate(bean.getBusinessDate());
        dto.setIncomeFromMatches(String.valueOf(bean.getIncomeFromMatches()));
        dto.setIncomeFromBeverages(String.valueOf(bean.getIncomeFromBeverages()));
        dto.setTotalBusiness(String.valueOf(bean.getTotalBusiness()));
        dto.setTotalMoneyPaid(String.valueOf(bean.getTotalMoneyPaid()));
        dto.setTotalBalanceAmount(String.valueOf(bean.getTotalBalanceAmount()));

        oldBusinessDayInformationDTO.add(dto);

        return oldBusinessDayInformationDTO;
    }
    
    private String formatAmount(int i)
    {
        Double myDouble = (double)i;
        String s = String.format(Locale.UK, "%1.2f", Math.abs(myDouble));
        s = s.replaceAll("(.+)(...\\...)", "$1,$2");
        while (s.matches("\\d{3,},.+"))
        {
            s = s.replaceAll("(\\d+)(\\d{2},.+)", "$1,$2");
        }
        return myDouble < 0 ? ("-" + s) : s;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public String getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(String businessDate)
    {
        this.businessDate = businessDate;
    }

    public List<DateWiseReportBean> getOldBusinessDayTransactionRecords()
    {
        return oldBusinessDayTransactionRecords;
    }

    public void setOldBusinessDayTransactionRecords(List<DateWiseReportBean> oldBusinessDayTransactionRecords)
    {
        this.oldBusinessDayTransactionRecords = oldBusinessDayTransactionRecords;
    }

    public List<BusinessDayInformationDTO> getOldBusinessDayInformationDTO()
    {
        return oldBusinessDayInformationDTO;
    }

    public void setOldBusinessDayInformationDTO(List<BusinessDayInformationDTO> oldBusinessDayInformationDTO)
    {
        this.oldBusinessDayInformationDTO = oldBusinessDayInformationDTO;
    }

}
