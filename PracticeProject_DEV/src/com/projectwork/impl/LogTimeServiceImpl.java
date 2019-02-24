package com.projectwork.impl;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.projectwork.bean.BusinessDayInformationBean;
import com.projectwork.bean.DateWiseReportBean;
import com.projectwork.bean.LogTimeBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.dto.LogTimeDTO;
import com.projectwork.impl.sql.LogTimeServiceSQLIfc;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;

public class LogTimeServiceImpl extends DatabaseConnectionServiceImpl
        implements TestProjectConstantsIfc, LogTimeServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(LogTimeServiceImpl.class);

    /**
     * This method will get list of all users to be displayed on the log time
     * page for current business date.
     * 
     * @param String business date
     * @return List<LogTimeBean>: Containing all information for every user.
     */

    public List<LogTimeBean> getBusinessDayUsersList(String businessDate) throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<LogTimeBean> businessDayUsersList = new ArrayList<LogTimeBean>();

        try
        {
            logger.info("LogTimeServiceImpl >> getBusinessDayUsersList");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_BUSINESS_DETAILS);
            ps.setString(1, businessDate);

            rs = ps.executeQuery();
            while (rs.next())
            {
                LogTimeBean bean = new LogTimeBean();

                bean.setCustEntryNo(rs.getString("CUST_ENTRY_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setEmployeeID(rs.getString("EMP_ID"));
                bean.setCustomerID(rs.getString("CUST_ID"));
                bean.setInTime(getJAVADate(rs.getTimestamp("CUST_IN_TIME")));
                bean.setOutTime(getJAVADate(rs.getTimestamp("CUST_OUT_TIME")));
                bean.setCustBeveragesTotalBill(rs.getInt("CUST_BEV_TOTAL_BILL"));
                bean.setCustMatchesTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));
                bean.setCustPaidBill(rs.getInt("CUST_PAID_BILL"));
                bean.setCustStatus(rs.getString("CUST_STATUS"));
                bean.setPaidStatus(rs.getString("PAYMENT_STATUS"));
                bean.setCreationTime(getJAVADate(rs.getTimestamp("CREATION_TIME")));

                businessDayUsersList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting business day users list." + se.getMessage());
            throw new SQLException();

        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting business day users list." + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return businessDayUsersList;
    }

    /**
     * This method will get list of all Bills for a particular Customer. It will
     * be used to pay up Customer Bills from Customer History section.
     * 
     * @param String Customer ID
     * @return List<LogTimeBean>: Containing all information for every user.
     */

    public List<LogTimeBean> getBusinessCustomerBillDetails(String customerID) throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<LogTimeBean> businessCustomerBillDetails = new ArrayList<LogTimeBean>();

        try
        {
            logger.info("LogTimeServiceImpl >> getBusinessCustomerBillDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_BUSINESS_CUSTOMER_UNPAID_BILL_DETAILS);
            ps.setString(1, customerID);

            rs = ps.executeQuery();
            while (rs.next())
            {
                LogTimeBean bean = new LogTimeBean();

                bean.setCustEntryNo(rs.getString("CUST_ENTRY_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setCustomerID(rs.getString("CUST_ID"));
                bean.setCustBeveragesTotalBill(rs.getInt("CUST_BEV_TOTAL_BILL"));
                bean.setCustMatchesTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));
                bean.setCustPaidBill(rs.getInt("CUST_PAID_BILL"));

                int remainingBill = rs.getInt("CUST_BEV_TOTAL_BILL") + rs.getInt("CUST_MATCHES_TOTAL_BILL")
                        - rs.getInt("CUST_PAID_BILL");
                bean.setCustRemainingBill(remainingBill);

                businessCustomerBillDetails.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching payment details." + se.getMessage());
            throw new SQLException();

        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching payment details." + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return businessCustomerBillDetails;
    }

    /**
     * This method will get next index number for adding a new log time record.
     * 
     * @param String business date
     * @return String: Next index sequence number
     */

    public String getNextIndexSequenceNumber(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        String sequenceNumber = null;
        Integer count = 0;
        String countString = null;
        String sequenceNumberBusinessDate = businessDate.replaceAll("-", "");

        try
        {
            logger.info("LogTimeServiceImpl >> getNextIndexSequenceNumber");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_NEXT_SEQUENCE_NUMBER;
            ps = con.prepareStatement(sql);
            ps.setString(1, businessDate);

            rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
                if (count < 10)
                {
                    countString = "00" + count;
                }
                else if (count >= 10 && count < 100)
                {
                    countString = "0" + count;
                }
                else
                {
                    countString = count.toString();
                }

                sequenceNumber = "C" + sequenceNumberBusinessDate + countString;
                logger.info("Customer Sequence Number to be used is : " + sequenceNumber);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching the sequence number." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching the sequence number." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return sequenceNumber;
    }

    /**
     * This method will get active business date.
     * 
     * @return String: Active Business Date.
     */

    public String getActiveBusinessDate()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        String businessDate = null;

        try
        {
            logger.info("LogTimeServiceImpl >> getActiveBusinessDate");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_ACTIVE_BUSINESS_DATE;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next())
            {
                businessDate = rs.getString(1);
            }
            else
            {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                businessDate = formatter.format(Calendar.getInstance().getTime());
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching the active business date." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching the active business date." + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return businessDate;
    }

    /**
     * This method will add a new business day log time record.
     * 
     * @param String
     * @return true: if record insertion is successful.
     */

    public boolean addNewBusinessRecord(LogTimeBean bean)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordInserted = false;

        try
        {
            logger.info("LogTimeServiceImpl >> addNewBusinessRecord");

            logger.info("Adding new business record for: Customer Entry Number : " + bean.getCustEntryNo()
                    + " Business Date: " + bean.getBusinessDate() + " Employee ID : " + bean.getEmployeeID()
                    + " Customer ID : " + bean.getCustomerID());

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(Add_NEW_BUSINESS_RECORD);
            int n = 1;
            ps.setString(n++, bean.getCustEntryNo());
            ps.setString(n++, bean.getBusinessDate());
            ps.setString(n++, bean.getEmployeeID());
            ps.setString(n++, bean.getCustomerID());
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isRecordInserted = true;

                logger.info("Record inserted successfully..");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while inserting new business record." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while inserting new business record." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return (isRecordInserted);
    }

    /**
     * This method will update out time for the Customer.
     * 
     * @param String custEntryNo
     * @return true: if update is successful.
     */

    public boolean updateCustomerOutTime(String custEntryNo)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordUpdated = false;

        try
        {
            logger.info("LogTimeServiceImpl >> updateCustomerOutTime");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_OUT_TIME);
            int n = 1;
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isRecordUpdated = true;
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating customer out time." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating customer out time." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return isRecordUpdated;
    }

    /**
     * This method will update payment status if Customer bill is paid up.
     * 
     * @param String custEntryNo
     * @param String businessDate
     * @param String paidStatus (Y, N)
     * @return true: if validation is successful.
     */

    public boolean updatePaymentStatus(String custEntryNo, String businessDate, String paidStatus)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordUpdated = false;

        try
        {
            logger.info("LogTimeServiceImpl >> updatePaymentStatus");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_PAYMENT_STATUS);
            int n = 1;
            ps.setString(n++, paidStatus);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);
            ps.setString(n++, businessDate);

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isRecordUpdated = true;
                logger.info("Record number " + custEntryNo + " on business date " + businessDate
                        + " updated successfully with Payment status as " + paidStatus);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating Payment status." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating Payment status." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return isRecordUpdated;
    }

    /**
     * This method will get billing details for the selected customer.
     * 
     * @param String
     * @param String
     * @return true: if validation is successful.
     */

    public List<LogTimeBean> viewSelectedCustomerRecords(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<LogTimeBean> selectedCustomerRecordsList = new ArrayList<LogTimeBean>();

        String sql = null;

        try
        {
            logger.info("LogTimeServiceImpl >> viewSelectedCustomerRecords");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_CUSTOMER_DETAILS_QUERY;
            ps = con.prepareStatement(sql);
            ps.setString(1, customerID);

            rs = ps.executeQuery();

            while (rs.next())
            {
                LogTimeBean bean = new LogTimeBean();

                bean.setCustomerID(rs.getString("CUST_ID"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));

                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setCustBeveragesTotalBill(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setCustMatchesTotalBill(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setCustPaidBill(Integer.parseInt(rs.getString("paidBill")));

                bean.setCustRemainingBill(totalBusiness - totalMoneyPaid);

                selectedCustomerRecordsList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching billing details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching billing details for the selected customer" + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return selectedCustomerRecordsList;
    }

    /**
     * This method will update payment details for the selected customer.
     * 
     * @param String custEntryNo
     * @param int
     * @param int
     * @param int
     * @return true: if update is successful.
     */
    public boolean updateCustomerPaymentDetailsOnBeverageMod(String custEntryNo, int newBevBill, int newMatchBill,
            int newPaidBill)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isCustomerBillPaid = false;

        LogTimeBean bean = getCustomerTotalBillDetails(custEntryNo);

        if (bean != null)
        {
            newMatchBill += bean.getCustMatchesTotalBill();
            newPaidBill += bean.getCustPaidBill();
        }

        try
        {
            logger.info("LogTimeServiceImpl >> updateCustomerPaymentDetailsOnBeverageMod");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_BILL_DETAILS);
            int n = 1;
            ps.setInt(n++, newBevBill);
            ps.setInt(n++, newMatchBill);
            ps.setInt(n++, newPaidBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isCustomerBillPaid = true;

                int totalBill = newMatchBill + newBevBill;
                if (totalBill == newPaidBill)
                {
                    updatePaymentStatus(bean.getCustEntryNo(), bean.getBusinessDate(), "Paid");
                }

            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating payment details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while updating payment details for the selected customer." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return isCustomerBillPaid;
    }

    /**
     * This method will update payment details for the selected customer.
     * 
     * @param String custEntryNo
     * @param int
     * @param int
     * @param int
     * @return true: if update is successful.
     */
    public boolean updateCustomerPaymentDetails(String custEntryNo, int newBevBill, int newMatchBill, int newPaidBill)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isCustomerBillPaid = false;

        LogTimeBean bean = getCustomerTotalBillDetails(custEntryNo);

        if (bean != null)
        {
            newBevBill += bean.getCustBeveragesTotalBill();
            newMatchBill += bean.getCustMatchesTotalBill();
            newPaidBill += bean.getCustPaidBill();
        }

        try
        {
            logger.info("LogTimeServiceImpl >> updateCustomerPaymentDetails");

            logger.info("Updating Payment Details for: Customer Entry Number : " + custEntryNo + " Beverages Bill : "
                    + newBevBill + " Match Bill : " + newMatchBill + " Paid Bill : " + newPaidBill);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_BILL_DETAILS);
            int n = 1;
            ps.setInt(n++, newBevBill);
            ps.setInt(n++, newMatchBill);
            ps.setInt(n++, newPaidBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isCustomerBillPaid = true;

                int totalBill = newMatchBill + newBevBill;
                if (totalBill == newPaidBill)
                {
                    updatePaymentStatus(bean.getCustEntryNo(), bean.getBusinessDate(), "Paid");
                }

            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating payment details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while updating payment details for the selected customer." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return isCustomerBillPaid;
    }

    /**
     * This method will update old payment details for the selected customer.
     * 
     * @param String custEntryNo
     * @param int newpaidBill
     * @return true: if update is successful.
     */
    public boolean updateCustomerHistoryPaymentDetails(String custEntryNo, int newPaidBill)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isCustomerBillPaid = false;

        LogTimeBean bean = getCustomerTotalBillDetails(custEntryNo);

        if (bean != null)
        {
            newPaidBill += bean.getCustPaidBill();
        }

        try
        {
            logger.info("LogTimeServiceImpl >> updateCustomerHistoryPaymentDetails");

            logger.info("Updating old payment details for: Customer Entry Number : " + custEntryNo + " Paid Bill : "
                    + newPaidBill);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_BILL_DETAILS);
            int n = 1;
            ps.setInt(n++, bean.getCustBeveragesTotalBill());
            ps.setInt(n++, bean.getCustMatchesTotalBill());
            ps.setInt(n++, newPaidBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isCustomerBillPaid = true;
                updatePaymentStatus(custEntryNo, bean.getBusinessDate(), "Paid");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating old payment details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while updating old payment details for the selected customer." + e.getMessage());
        }

        finally
        {
            try
            {
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return isCustomerBillPaid;
    }

    /**
     * This method will get all todays payment details for the selected
     * customer.
     * 
     * @param String custEntryNo
     * @return LogTimeBean
     */
    public LogTimeBean getCustomerTotalBillDetails(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        LogTimeBean bean = new LogTimeBean();

        try
        {
            logger.info("LogTimeServiceImpl >> getCustomerTotalBillDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_PAYMENT_DETAILS);
            int n = 1;
            ps.setString(n++, customerID);

            rs = ps.executeQuery();

            while (rs.next())
            {
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setCustBeveragesTotalBill(rs.getInt("CUST_BEV_TOTAL_BILL"));
                bean.setCustMatchesTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));
                bean.setCustPaidBill(rs.getInt("CUST_PAID_BILL"));
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating old payment details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while updating old payment details for the selected customer." + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return bean;
    }

    /**
     * This method will get all payment details for the selected customer.
     * 
     * @param String custEntryNo
     * @return LogTimeBean
     */
    public BusinessDayInformationBean getCustomerTodaysTotalBillDetails(String customerID, String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        BusinessDayInformationBean bean = new BusinessDayInformationBean();

        try
        {
            logger.info("LogTimeServiceImpl >> getCustomerTodaysTotalBillDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_TODAYS_CUSTOMER_PAYMENT_DETAILS);
            int n = 1;
            ps.setString(n++, customerID);
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();

            while (rs.next())
            {
                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setIncomeFromBeverages(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setIncomeFromMatches(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setTotalBusiness(totalBusiness);
                bean.setTotalMoneyPaid(totalMoneyPaid);

                bean.setTotalBalanceAmount(totalBusiness - totalMoneyPaid);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating old payment details for the selected customer."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while updating old payment details for the selected customer." + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return bean;
    }

    /**
     * This method will get summation of current business date's customer wise
     * transaction details.
     * 
     * @return BusinessDayInformationBean
     */
    public BusinessDayInformationBean getBusinessDayInformationDetails(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        BusinessDayInformationBean bean = null;

        try
        {
            logger.info("LogTimeServiceImpl >> getBusinessDayInformationDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_TOTAL_CUSTOMER_BILL_DETAILS);
            int n = 1;
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();

            while (rs.next())
            {
                bean = new BusinessDayInformationBean();

                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setBusinessDate(businessDate);

                bean.setIncomeFromBeverages(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setIncomeFromMatches(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setTotalBusiness(totalBusiness);
                bean.setTotalMoneyPaid(totalMoneyPaid);

                bean.setTotalBalanceAmount(totalBusiness - totalMoneyPaid);
            }
        }
        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while fetching summation of current business date's transaction details."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching summation of current business date's transaction details."
                    + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return bean;
    }

    /**
     * This method will get summation of all customer wise transaction details
     * till date.
     * 
     * @return BusinessDayInformationBean
     */
    public BusinessDayInformationBean getAllDayInformationDetails()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        BusinessDayInformationBean bean = null;

        try
        {
            logger.info("LogTimeServiceImpl >> getAllDayInformationDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_ALL_CUSTOMER_BILL_DETAILS);

            rs = ps.executeQuery();

            while (rs.next())
            {
                bean = new BusinessDayInformationBean();

                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setIncomeFromBeverages(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setIncomeFromMatches(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setTotalBusiness(totalBusiness);
                bean.setTotalMoneyPaid(totalMoneyPaid);

                bean.setTotalBalanceAmount(totalBusiness - totalMoneyPaid);
            }
        }
        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while fetching summation of all customer wise transaction details till date."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while fetching summation of all customer wise transaction details till date."
                            + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return bean;
    }

    /**
     * This method will get all customer wise transaction details for active
     * business date.
     * 
     * @return List<LogTimeBean>: Containing all information for every user.
     */
    public List<LogTimeBean> getBusinessDayCustomerRecords(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        List<LogTimeBean> businessDayCustomerRecords = new ArrayList<LogTimeBean>();

        try
        {
            logger.info("LogTimeServiceImpl >> getBusinessDayCustomerRecords");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_TODAY_REPORT);

            int n = 1;
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();

            while (rs.next())
            {
                LogTimeBean bean = new LogTimeBean();

                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setCustomerID(rs.getString("CUST_ID"));

                bean.setCustBeveragesTotalBill(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setCustMatchesTotalBill(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setCustPaidBill(Integer.parseInt(rs.getString("paidBill")));

                bean.setCustRemainingBill(totalBusiness - totalMoneyPaid);

                businessDayCustomerRecords.add(bean);
            }
        }
        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while getting all customer wise transaction details for active business date."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while getting all customer wise transaction details for active business date."
                            + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return businessDayCustomerRecords;
    }

    /**
     * This method will get all customer wise transaction details depending on
     * selected start and end dates
     * 
     * @return List<DateWiseReportBean>: Containing all information for every
     *         user.
     */
    public List<DateWiseReportBean> getDatewiseCustomerRecords(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;

        List<DateWiseReportBean> businessDayCustomerRecords = new ArrayList<DateWiseReportBean>();
        CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();

        try
        {
            logger.info("LogTimeServiceImpl >> getDatewiseCustomerRecords");

            con = getConnection();
            stmt = con.createStatement();

            sql = "SELECT BUSINESS.BUSINESS_DATE, BUSINESS.CUST_ENTRY_NO, BUSINESS.CUST_ID, BUSINESS.CUST_BEV_TOTAL_BILL, "
                    + " BUSINESS.CUST_MATCHES_TOTAL_BILL, BUSINESS.CUST_PAID_BILL FROM  BUSINESS, CUSTOMER_MATCHES_RECORD "
                    + " WHERE BUSINESS.CUST_ENTRY_NO = CUSTOMER_MATCHES_RECORD.CUST_ENTRY_NO "
                    + " AND BUSINESS.BUSINESS_DATE = CUSTOMER_MATCHES_RECORD.BUSINESS_DATE AND "
                    + " BUSINESS.BUSINESS_DATE = STR_TO_DATE('" + businessDate
                    + "', '%Y-%m-%d') GROUP BY BUSINESS.BUSINESS_DATE, BUSINESS.CUST_ENTRY_NO "
                    + " ORDER BY BUSINESS.CUST_ID, " + " CUSTOMER_MATCHES_RECORD.MATCH_NO";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                DateWiseReportBean bean = new DateWiseReportBean();

                bean.setBusinessDate(rs.getString("BUSINESS.BUSINESS_DATE"));
                bean.setCustEntryNo(rs.getString("BUSINESS.CUST_ENTRY_NO"));
                bean.setCustomerID(rs.getString("BUSINESS.CUST_ID"));

                String customerName = customerServiceImplObj.getCustomerNameFromID(rs.getString("BUSINESS.CUST_ID"));
                bean.setCustomerName(customerName);

                int totalBusiness = Integer.parseInt(rs.getString("BUSINESS.CUST_BEV_TOTAL_BILL"))
                        + Integer.parseInt(rs.getString("BUSINESS.CUST_MATCHES_TOTAL_BILL"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("BUSINESS.CUST_PAID_BILL"));

                bean.setCustBeveragesTotalBill(Integer.parseInt(rs.getString("BUSINESS.CUST_BEV_TOTAL_BILL")));
                bean.setCustMatchesTotalBill(Integer.parseInt(rs.getString("BUSINESS.CUST_MATCHES_TOTAL_BILL")));

                bean.setCustTotalBill(totalBusiness);

                bean.setCustPaidBill(Integer.parseInt(rs.getString("BUSINESS.CUST_PAID_BILL")));

                bean.setCustRemainingBill(totalBusiness - totalMoneyPaid);

                businessDayCustomerRecords.add(bean);
            }
        }
        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while getting all customer wise transaction details for active business date."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while getting all customer wise transaction details for active business date."
                            + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return businessDayCustomerRecords;
    }

    /**
     * This method will get all customer wise transaction details till date.
     * 
     * @return List<LogTimeBean>: Containing all information for every user.
     */
    public List<LogTimeBean> getAllDayCustomerRecords()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        List<LogTimeBean> businessDayCustomerRecords = new ArrayList<LogTimeBean>();

        try
        {
            logger.info("LogTimeServiceImpl >> getAllDayCustomerRecords");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_ALLDAY_REPORT);

            rs = ps.executeQuery();

            while (rs.next())
            {
                LogTimeBean bean = new LogTimeBean();

                int totalBusiness = Integer.parseInt(rs.getString("totalBeverageBill"))
                        + Integer.parseInt(rs.getString("totalMatchBill"));
                int totalMoneyPaid = Integer.parseInt(rs.getString("paidBill"));

                bean.setCustomerID(rs.getString("CUST_ID"));

                bean.setCustBeveragesTotalBill(Integer.parseInt(rs.getString("totalBeverageBill")));
                bean.setCustMatchesTotalBill(Integer.parseInt(rs.getString("totalMatchBill")));
                bean.setCustPaidBill(Integer.parseInt(rs.getString("paidBill")));

                bean.setCustRemainingBill(totalBusiness - totalMoneyPaid);

                businessDayCustomerRecords.add(bean);
            }
        }
        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting all customer wise transaction details till date."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting all customer wise transaction details till date."
                    + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return businessDayCustomerRecords;
    }

    /**
     * This method will get count of all previous day customers whose out-time
     * is not marked.
     * 
     * @return Integer
     */
    public Integer getUnmarkedOutTimeCount()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        Integer umarkedOutTimeCount = 0;

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String businessDate = formatter.format(Calendar.getInstance().getTime());

        try
        {
            logger.info("LogTimeServiceImpl >> getUnmarkedOutTimeCount");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_UNMARKED_OUTTIME_COUNT);
            ps.setString(1, businessDate);

            rs = ps.executeQuery();

            if (rs.next())
            {
                umarkedOutTimeCount = rs.getInt(1);
            }
        }
        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching unmarked outtime details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching unmarked outtime details" + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (SQLException e)
            {
                logger.error("Exception in closing DB resources");
            }
        }

        return umarkedOutTimeCount;
    }

    /**
     * This method will generate the report to be exported in PDF format.
     * 
     * @param List<LogTimeDTO>: Desired payment related information for the
     *            customers.
     * @return JasperReportBuilder
     */
    public JasperReportBuilder exportEODReport(List<LogTimeDTO> businessDayCustomerRecordsDTOs)
    {
        logger.info("LogTimeServiceImpl >> exportEODReport");

        JasperReportBuilder report = null;

        report = DynamicReports.report();

        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);

        StyleBuilder titleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY).setHorizontalAlignment(HorizontalAlignment.CENTER);

        report.columns(
                Columns.column("Customer Name", "custName", DataTypes.stringType())
                        .setHorizontalAlignment(HorizontalAlignment.LEFT),
                Columns.column("Bill From Matches", "custMatchesTotalBill", DataTypes.integerType()),
                Columns.column("Bill From Beverages", "custBeveragesTotalBill", DataTypes.integerType()),
                Columns.column("Total Bill", "totalBill", DataTypes.integerType()),
                Columns.column("Total Money Paid", "custPaidBill", DataTypes.integerType()),
                Columns.column("Total Balance Money", "custRemainingBill", DataTypes.integerType()))
                .title(Components.text("EOD Report Results").setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setStyle(titleStyle))
                .pageFooter(Components.pageXofY()).setDataSource(businessDayCustomerRecordsDTOs);

        return report;
    }

    /**
     * This method will generate the report to be exported in PDF format.
     * 
     * @param List<LogTimeDTO>: Desired payment related information for the
     *            customers.
     * @return JasperReportBuilder
     */
    public JasperReportBuilder exportOldBusinessDayReport(List<DateWiseReportBean> oldBusinessDayTransactionRecords)
    {
        logger.info("LogTimeServiceImpl >> exportOldBusinessDayReport");

        JasperReportBuilder report = null;

        report = DynamicReports.report();

        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);

        StyleBuilder titleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY).setHorizontalAlignment(HorizontalAlignment.CENTER);

        report.columns(
                Columns.column("Customer Name", "customerName", DataTypes.stringType())
                        .setHorizontalAlignment(HorizontalAlignment.LEFT),
                Columns.column("Bill From Matches", "custMatchesTotalBill", DataTypes.integerType()),
                Columns.column("Bill From Beverages", "custBeveragesTotalBill", DataTypes.integerType()),
                Columns.column("Total Bill", "custTotalBill", DataTypes.integerType()),
                Columns.column("Total Money Paid", "custPaidBill", DataTypes.integerType()),
                Columns.column("Total Balance Money", "custRemainingBill", DataTypes.integerType()))
                .title(Components.text("EOD Report Results").setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setStyle(titleStyle))
                .pageFooter(Components.pageXofY()).setDataSource(oldBusinessDayTransactionRecords);

        return report;
    }

}
