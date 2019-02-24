package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.projectwork.bean.CustomerMatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.sql.CustomerMatchesServiceSQLIfc;

public class CustomerMatchesServiceImpl extends DatabaseConnectionServiceImpl
        implements TestProjectConstantsIfc, CustomerMatchesServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(CustomerMatchesServiceImpl.class);

    /**
     * This method will add new record for every customer match.
     * 
     * @param String
     * @param String
     * @return true: if record is inserted successfully.
     */

    public boolean addNewCustomerMatchRecord(String custEntryNo, String matchNo, String businessDate,
            String playingCustomerID, String frameType)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isNewMatchRecordInserted = false;

        try
        {
            logger.info("CustomerMatchesServiceImpl >> addNewCustomerMatchRecord");

            logger.info("Adding new customer match record for: Customer Entry Number : " + custEntryNo
                    + " Match Number : " + matchNo + " Business Date: " + businessDate + " Frame Type : " + frameType + " Playing Customers : "
                    + playingCustomerID);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(ADD_CUSTOMER_MATCH_RECORD);
            int n = 1;
            ps.setString(n++, custEntryNo);
            ps.setString(n++, matchNo);
            ps.setString(n++, businessDate);
            ps.setString(n++, playingCustomerID);
            ps.setString(n++, frameType);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isNewMatchRecordInserted = true;

                logger.info("Record inserted successfully..");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while inserting new Customer-Match record." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while inserting new Customer-Match record." + e.getMessage());
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
        return isNewMatchRecordInserted;
    }

    /**
     * This method will get list of Customers playing in a particular match
     * 
     * @param String matchNo
     * @return String: Comma separated list of Customers.
     */

    public String getCurrentlyPlayingCustomerListFromMatchNo(String matchNo)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        String listofPlayingCustomers = "";

        String sql = null;

        try
        {
            logger.info("CustomerMatchesServiceImpl >> getCurrentlyPlayingCustomerMapFromMatchNo");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_CUSTOMER_MATCH_RECORD;

            ps = con.prepareStatement(sql);
            ps.setString(1, matchNo);

            rs = ps.executeQuery();

            while (rs.next())
            {
                listofPlayingCustomers = listofPlayingCustomers + rs.getString("CUST_ID") + "','";
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting list of Customers playing in a particular match."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting list of Customers playing in a particular match."
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

        listofPlayingCustomers = listofPlayingCustomers.replaceAll("'$", "");
        listofPlayingCustomers = listofPlayingCustomers.replaceAll(",$", "");

        return listofPlayingCustomers;
    }

    /**
     * This method will add match bill for losing player.
     * 
     * @param String matchNo
     * @param String payee
     * @param int custMatchTotalBill
     * @return true: if match bill update is successful.
     */

    public boolean updateMatchPayeeBill(String matchNo, String payee, int custMatchTotalBill)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        String sql = null;

        boolean isMatchPayeeUpdated = false;

        try
        {
            logger.info("CustomerMatchesServiceImpl >> updateMatchPayeeBill");
            
            logger.info("Updating match payee bill record for: Match Number : " + matchNo + " Payee : "
                    + payee + " Match Bill : "+ custMatchTotalBill);

            con = getConnection();
            stmt = con.createStatement();

            sql = UPDATE_PAYEE_BILL_RECORD;

            ps = con.prepareStatement(sql);
            int n = 1;
            ps.setInt(n++, custMatchTotalBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, payee);
            ps.setString(n++, matchNo);

            rs = ps.executeUpdate();

            if (rs > 0)
            {
                isMatchPayeeUpdated = true;
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while adding match bill for losing player." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while adding match bill for losing player." + e.getMessage());
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

        return isMatchPayeeUpdated;
    }

    /**
     * This method will List of all matches that a particular customers has
     * played since log time.
     * 
     * @param String
     * @return List<CustomerMatchDetailsBean>: List of type
     *         CustomerMatchDetailsBean
     */

    public List<CustomerMatchDetailsBean> getCustomerMatchDetails(String custEntryNo)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<CustomerMatchDetailsBean> customerMatchDetailsList = new ArrayList<CustomerMatchDetailsBean>();

        try
        {
            logger.info("CustomerMatchesServiceImpl >> getCustomerMatchDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_ALL_CURRENT_MATCHES_RECORD);
            int n = 1;
            ps.setString(n++, custEntryNo);

            rs = ps.executeQuery();

            while (rs.next())
            {
                CustomerMatchDetailsBean bean = new CustomerMatchDetailsBean();

                bean.setCustEntryNo(custEntryNo);
                bean.setMatchNo(rs.getString("MATCH_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setPlayingCustomerID(rs.getString("CUST_ID"));
                bean.setFrameType(rs.getString("FRAME_TYPE"));
                bean.setCustMatchTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));

                customerMatchDetailsList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while making list of all matches that a particular customers has played since log time."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while making list of all matches that a particular customers has played since log time."
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

        return customerMatchDetailsList;
    }

    /**
     * This method will List of all matches that a particular customers has for
     * current business date
     * 
     * @param String customerID
     * @param String businessDate
     * @return List<CustomerMatchDetailsBean>: List of type
     *         CustomerMatchDetailsBean
     */

    public List<CustomerMatchDetailsBean> getCustomerBusinessDayDetails(String customerID, String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<CustomerMatchDetailsBean> customerMatchDetailsList = new ArrayList<CustomerMatchDetailsBean>();

        try
        {
            logger.info("CustomerMatchesServiceImpl >> getCustomerBusinessDayDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_TODAYS_MATCHES_RECORD);
            int n = 1;
            ps.setString(n++, customerID);
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();

            while (rs.next())
            {
                CustomerMatchDetailsBean bean = new CustomerMatchDetailsBean();

                bean.setCustEntryNo(rs.getString("CUST_ENTRY_NO"));
                bean.setMatchNo(rs.getString("MATCH_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setPlayingCustomerID(rs.getString("CUST_ID"));
                bean.setFrameType(rs.getString("FRAME_TYPE"));
                bean.setCustMatchTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));
                bean.setEmployeeID(rs.getString("EMP_ID"));
                bean.setTableNo(rs.getString("TABLE_NO"));
                bean.setFrameStartTime(getJAVADate(rs.getTimestamp("FRAME_START_TIME")));
                bean.setFrameEndTime(getJAVADate(rs.getTimestamp("FRAME_END_TIME")));
                bean.setMatchStatus(rs.getString("MATCH_STATUS"));

                customerMatchDetailsList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while making list of all matches that a particular customers has played today."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while making list of all matches that a particular customers has played today."
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

        return customerMatchDetailsList;
    }

}
