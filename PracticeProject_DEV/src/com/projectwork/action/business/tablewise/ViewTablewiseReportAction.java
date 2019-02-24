package com.projectwork.action.business.tablewise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.MatchDetailsDTO;
import com.projectwork.impl.LogTimeServiceImpl;
import com.projectwork.impl.MatchesServiceImpl;

public class ViewTablewiseReportAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -6226264236195298860L;

    private HttpServletRequest request;

    private List<MatchDetailsDTO> tablewiseBusinessDayInformationDTO = new ArrayList<MatchDetailsDTO>();

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

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        String businessDate = logTimeServiceImplObj.getActiveBusinessDate();

        MatchesServiceImpl matchesServiceImplObj = new MatchesServiceImpl();

        List<MatchDetailsBean> businessDayCustomerRecords = matchesServiceImplObj
                .getTablewiseBusinessDayReport(businessDate);

        Iterator<MatchDetailsBean> iterator = businessDayCustomerRecords.iterator();

        while (iterator.hasNext())
        {
            MatchDetailsDTO dto = getMatchDetailsDTO(iterator.next());
            tablewiseBusinessDayInformationDTO.add(dto);
        }

        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private MatchDetailsDTO getMatchDetailsDTO(MatchDetailsBean bean)
    {
        MatchDetailsDTO dto = new MatchDetailsDTO();

        dto.setTableNo(bean.getTableNo());
        dto.setPaymentAmount(String.valueOf(bean.getPaymentAmount()));

        return dto;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public List<MatchDetailsDTO> getTablewiseBusinessDayInformationDTO()
    {
        return tablewiseBusinessDayInformationDTO;
    }

    public void setTablewiseBusinessDayInformationDTO(List<MatchDetailsDTO> tablewiseBusinessDayInformationDTO)
    {
        this.tablewiseBusinessDayInformationDTO = tablewiseBusinessDayInformationDTO;
    }

}
