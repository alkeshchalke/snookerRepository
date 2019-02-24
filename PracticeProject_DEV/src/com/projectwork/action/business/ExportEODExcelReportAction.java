package com.projectwork.action.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.BusinessDayInformationDTO;
import com.projectwork.dto.LogTimeDTO;

public class ExportEODExcelReportAction extends ActionSupport implements TestProjectConstantsIfc, ServletRequestAware
{

    /**
     * 
     */
    private static final long serialVersionUID = -339257907436785969L;

    private HttpServletRequest request;

    private InputStream fileInputStream;

    private List<LogTimeDTO> businessDayCustomerRecordsDTOs = new ArrayList<LogTimeDTO>();

    private List<BusinessDayInformationDTO> businessDayInformationDTO = new ArrayList<BusinessDayInformationDTO>();

    private String fileName;

    private String incomeFromMatches;

    private String incomeFromBeverages;

    private String totalBusiness;

    private String totalMoneyPaid;

    private String totalBalanceAmount;

    private static Logger logger = Logger.getLogger(ExportEODExcelReportAction.class);

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

        businessDayCustomerRecordsDTOs = (List<LogTimeDTO>)request.getSession()
                .getAttribute("businessDayCustomerRecordsDTOs");

        businessDayInformationDTO = (List<BusinessDayInformationDTO>)request.getSession()
                .getAttribute("businessDayInformationDTO");

        LogTimeDTO dto = new LogTimeDTO();
        BusinessDayInformationDTO businessDTO = new BusinessDayInformationDTO();

        businessDTO = businessDayInformationDTO.get(0);

        dto.setCustomerID("Total Bill");
        dto.setCustMatchesTotalBill(Integer.parseInt(businessDTO.getIncomeFromMatches()));
        dto.setCustBeveragesTotalBill(Integer.parseInt(businessDTO.getIncomeFromBeverages()));
        dto.setTotalBill(Integer.parseInt(businessDTO.getTotalBusiness()));
        dto.setCustPaidBill(Integer.parseInt(businessDTO.getTotalMoneyPaid()));
        dto.setCustRemainingBill(Integer.parseInt(businessDTO.getTotalBalanceAmount()));

        businessDayCustomerRecordsDTOs.add(dto);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");

        int rowCount = 0;
        String[] headers = new String[] { "Customer Name", "Bill From Matches", "Bill From Beverages",
                "Total Bill Amount", "Total Money Paid", "Total Balance Money" };

        Row headerRow = sheet.createRow(++rowCount);

        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop((short)6); // double lines border
        headerStyle.setBorderBottom((short)1); // single line border
        headerStyle.setBorderLeft((short)1);
        headerStyle.setBorderRight((short)1);
        XSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short)11);
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);

        for (int rn = 0; rn < headers.length; rn++)
        {
            Cell headerCell = headerRow.createCell(rn+1);
            headerCell.setCellValue(headers[rn]);
            headerCell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(rn+1);
        }

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom((short)1); // single line border
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short)10);
        cellFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);

        for (LogTimeDTO currentRow : businessDayCustomerRecordsDTOs)
        {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 1;

            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getCustomerID());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getCustMatchesTotalBill());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getCustBeveragesTotalBill());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getTotalBill());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getCustPaidBill());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(columnCount++);
            cell.setCellValue(currentRow.getCustRemainingBill());
            cell.setCellStyle(cellStyle);
        }

        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT);
            String businessDate = formatter.format(Calendar.getInstance().getTime());

            SimpleDateFormat timeFormatter = new SimpleDateFormat(TIMEFORMAT);
            String currentTime = timeFormatter.format(Calendar.getInstance().getTime());

            String exportFileName = "Report_" + businessDate + "_" + currentTime + ".xls";
            String exportReportPath = request.getSession().getServletContext().getRealPath("/") + File.separator
                    + exportFileName;

            FileOutputStream outputStream = new FileOutputStream(exportReportPath);
            workbook.write(outputStream);

            fileInputStream = new FileInputStream(new File(exportReportPath));
            fileName = new File(exportReportPath).getName();

            workbook.close();
        }
        catch (FileNotFoundException e)
        {
            logger.error("FileNotFoundException occurred");
        }
        catch (IOException e)
        {
            logger.error("IOException occurred");
        }

        forwardString = RETURN_SUCCESS;

        return forwardString;
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

    public List<BusinessDayInformationDTO> getBusinessDayInformationDTO()
    {
        return businessDayInformationDTO;
    }

    public void setBusinessDayInformationDTO(List<BusinessDayInformationDTO> businessDayInformationDTO)
    {
        this.businessDayInformationDTO = businessDayInformationDTO;
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
}
