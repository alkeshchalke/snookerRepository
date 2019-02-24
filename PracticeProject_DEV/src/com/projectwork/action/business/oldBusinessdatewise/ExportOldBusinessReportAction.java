package com.projectwork.action.business.oldBusinessdatewise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.bean.DateWiseReportBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.impl.LogTimeServiceImpl;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ExportOldBusinessReportAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{
    /**
     * 
     */
    private static final long serialVersionUID = -4634055808100988132L;

    private HttpServletRequest request;

    private InputStream fileInputStream;

    List<DateWiseReportBean> oldBusinessDayTransactionRecords = new ArrayList<DateWiseReportBean>();
    
    private List<BusinessDayInformationDTO> oldBusinessDayInformationDTO = new ArrayList<BusinessDayInformationDTO>();

    private String fileName;

    private String incomeFromMatches;

    private String incomeFromBeverages;

    private String totalBusiness;

    private String totalMoneyPaid;

    private String totalBalanceAmount;

    private static Logger logger = Logger.getLogger(ExportOldBusinessReportAction.class);

    @SuppressWarnings("unchecked")
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

        oldBusinessDayTransactionRecords = (List<DateWiseReportBean>)request.getSession().getAttribute("oldBusinessDayTransactionRecords");
        
        oldBusinessDayTransactionRecords.add(createBlankBean());
        
        oldBusinessDayInformationDTO = (List<BusinessDayInformationDTO>)request.getSession().getAttribute("oldBusinessDayInformationDTO");
        
        DateWiseReportBean bean = new DateWiseReportBean();
        BusinessDayInformationDTO businessDTO = new BusinessDayInformationDTO();
        
        businessDTO = oldBusinessDayInformationDTO.get(0);

        bean.setCustomerName("Total");
        bean.setCustMatchesTotalBill(Integer.parseInt(businessDTO.getIncomeFromMatches()));
        bean.setCustBeveragesTotalBill(Integer.parseInt(businessDTO.getIncomeFromBeverages()));
        bean.setCustTotalBill(Integer.parseInt(businessDTO.getTotalBusiness()));
        bean.setCustPaidBill(Integer.parseInt(businessDTO.getTotalMoneyPaid()));
        bean.setCustRemainingBill(Integer.parseInt(businessDTO.getTotalBalanceAmount()));
        
        oldBusinessDayTransactionRecords.add(bean);

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();

        JasperReportBuilder report = logTimeServiceImplObj.exportOldBusinessDayReport(oldBusinessDayTransactionRecords);

        if (report == null)
        {
            return RETURN_ERROR;
        }

        else
        {
            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT);
                String businessDate = formatter.format(Calendar.getInstance().getTime());

                SimpleDateFormat timeFormatter = new SimpleDateFormat(TIMEFORMAT);
                String currentTime = timeFormatter.format(Calendar.getInstance().getTime());

                String exportFileName = "Report_" + businessDate + "_" + currentTime + ".pdf";
                String exportReportPath = request.getSession().getServletContext().getRealPath("/") + File.separator + exportFileName;
                report.toPdf(new FileOutputStream(exportReportPath));
                fileInputStream = new FileInputStream(new File(exportReportPath));

                fileName = new File(exportReportPath).getName();

            }
            catch (DRException e)
            {
                logger.error("DRException occurred");
            }
            catch (FileNotFoundException e)
            {
                logger.error("FileNotFoundException occurred");
            }
        }
        forwardString = RETURN_SUCCESS;

        return forwardString;
    }

    private DateWiseReportBean createBlankBean()
    {
        DateWiseReportBean blankBean = new DateWiseReportBean();
        blankBean.setCustomerName("*****************************************************");
        return blankBean;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest)
    {
        this.request = httpServletRequest;
    }

    public InputStream getFileInputStream()
    {
        return fileInputStream;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


    public String getIncomeFromMatches()
    {
        return incomeFromMatches;
    }

    public void setIncomeFromMatches(String incomeFromMatches)
    {
        this.incomeFromMatches = incomeFromMatches;
    }

    public String getIncomeFromBeverages()
    {
        return incomeFromBeverages;
    }

    public void setIncomeFromBeverages(String incomeFromBeverages)
    {
        this.incomeFromBeverages = incomeFromBeverages;
    }

    public String getTotalBusiness()
    {
        return totalBusiness;
    }

    public void setTotalBusiness(String totalBusiness)
    {
        this.totalBusiness = totalBusiness;
    }

    public String getTotalMoneyPaid()
    {
        return totalMoneyPaid;
    }

    public void setTotalMoneyPaid(String totalMoneyPaid)
    {
        this.totalMoneyPaid = totalMoneyPaid;
    }

    public String getTotalBalanceAmount()
    {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(String totalBalanceAmount)
    {
        this.totalBalanceAmount = totalBalanceAmount;
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
