package com.projectwork.action.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.BusinessDayInformationBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.impl.LogTimeServiceImpl;

public class ShowBusinessDayInformationAction extends ActionSupport
        implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = 7592475277963655313L;

    private HttpServletRequest request;

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

        if (request.getSession().getAttribute("errors") != null)
        {
            String actionError = (String)request.getSession().getAttribute("errors");
            addActionError(actionError);
            request.getSession().removeAttribute("errors");
        }

        // Get total business day transactions information.

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        
        String businessDate = logTimeServiceImplObj.getActiveBusinessDate();
        BusinessDayInformationBean bean = logTimeServiceImplObj.getBusinessDayInformationDetails(businessDate);
        businessDayInformationDTO = createBusinessDayInformationDTO(bean);

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private List<BusinessDayInformationDTO> createBusinessDayInformationDTO(BusinessDayInformationBean bean)
    {
        BusinessDayInformationDTO dto = new BusinessDayInformationDTO();

        dto.setBusinessDate(bean.getBusinessDate());
        dto.setIncomeFromMatches(formatAmount(bean.getIncomeFromMatches()));
        dto.setIncomeFromBeverages(formatAmount(bean.getIncomeFromBeverages()));
        dto.setTotalBusiness(formatAmount(bean.getTotalBusiness()));
        dto.setTotalMoneyPaid(formatAmount(bean.getTotalMoneyPaid()));
        dto.setTotalBalanceAmount(formatAmount(bean.getTotalBalanceAmount()));

        businessDayInformationDTO.add(dto);

        return businessDayInformationDTO;
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

    public List<BusinessDayInformationDTO> getBusinessDayInformationDTO()
    {
        return businessDayInformationDTO;
    }

    public void setBusinessDayInformationDTO(List<BusinessDayInformationDTO> businessDayInformationDTO)
    {
        this.businessDayInformationDTO = businessDayInformationDTO;
    }
}
