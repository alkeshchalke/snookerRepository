package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.projectwork.bean.MatchDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.sql.MatchServiceSQLIfc;

public class MatchesServiceImpl extends DatabaseConnectionServiceImpl
        implements TestProjectConstantsIfc, MatchServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(MatchesServiceImpl.class);

    /**
     * This method will update out time for the Customer.
     * 
     * @param String custEntryNo
     * @return true: if update is successful.
     */

    public List<MatchDetailsBean> getBusinessDayMatchesList(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<MatchDetailsBean> ongoingMatchesList = new ArrayList<MatchDetailsBean>();

        try
        {
            logger.info("MatchesServiceImpl >> getOngoingMatchesList");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_BUSINESS_FRAMES_DETAILS);
            ps.setString(1, businessDate);

            rs = ps.executeQuery();
            while (rs.next())
            {
                MatchDetailsBean bean = new MatchDetailsBean();

                bean.setMatchNo(rs.getString("MATCH_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setEmployeeID(rs.getString("EMP_ID"));
                bean.setFrameType(rs.getString("FRAME_TYPE"));
                bean.setTableNo(rs.getString("TABLE_NO"));
                bean.setPlayingCustomerID(rs.getString("PLAYING_CUST_ID"));
                bean.setPaymentAmount(rs.getInt("PAYMENT_AMOUNT"));
                bean.setFrameStartTime(getJAVADate(rs.getTimestamp("FRAME_START_TIME")));
                bean.setFrameEndTime(getJAVADate(rs.getTimestamp("FRAME_END_TIME")));
                bean.setPayingPlayer(rs.getString("PAYING_PLAYER"));
                bean.setMatchStatus(rs.getString("MATCH_STATUS"));

                ongoingMatchesList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching beverages details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching beverages details" + e.getMessage());
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
        return ongoingMatchesList;
    }

    /**
     * This method will return the match sequence number for the next match.
     * 
     * @param String
     * @return String: Match Sequence Number.
     */

    public String getNextMatchSequenceNumber(String businessDate)
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
            logger.info("MatchesServiceImpl >> getNextIndexSequenceNumber");

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

                sequenceNumber = "M" + sequenceNumberBusinessDate + countString;
                logger.info("Match Number to be used is : " + sequenceNumber);
            }
            else
            {
                sequenceNumber = "M" + sequenceNumberBusinessDate + "000";
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
     * This method will add a new match record.
     * 
     * @param String
     * @return true: if match addition is successful.
     */

    public boolean addNewMatchRecord(String matchNo, String businessDate, String employeeID, String frameType,
            String tableNo, String playingCustomers, int paymentAmount)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isNewMatchRecordInserted = false;

        try
        {
            logger.info("MatchesServiceImpl >> addNewMatchRecord");

            logger.info("Adding new match record for: Match Number : " + matchNo + " Business Date: " + businessDate
                    + " Employee ID : " + employeeID + " Frame Type : " + frameType + " Table No : " + tableNo
                    + " Playing Customers : " + playingCustomers + " Payment Amount : " + paymentAmount);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(Add_NEW_BUSINESS_SELECTED_FRAMES_RECORD);
            int n = 1;
            ps.setString(n++, matchNo);
            ps.setString(n++, businessDate);
            ps.setString(n++, employeeID);
            ps.setString(n++, frameType);
            ps.setString(n++, tableNo);
            ps.setString(n++, playingCustomers);
            ps.setInt(n++, paymentAmount);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
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
            logger.error("SQL Exception occured while fetching beverages details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching beverages details" + e.getMessage());
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
     * This method will add a new player in an ongoing RD match.
     * 
     * @param MatchDetailsBean
     * @return true: if player addition is successful.
     */

    public boolean addNewPlayersInOngoingRDMatch(MatchDetailsBean bean)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordUpdated = false;

        try
        {
            logger.info("MatchesServiceImpl >> addNewPlayersInOngoingRDMatch");

            logger.info("Adding new player in ongoing RD match for: Match Number : " + bean.getMatchNo()
                    + " Business Date: " + bean.getBusinessDate() + " Playing Customers : "
                    + bean.getPlayingCustomerID());

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(ADD_NEW_PLAYERS_IN_ONGOING_RD_FRAME);
            int n = 1;
            ps.setString(n++, bean.getPlayingCustomerID());
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, bean.getMatchNo());
            ps.setString(n++, bean.getBusinessDate());

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isRecordUpdated = true;
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating the RD match." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating the RD match." + e.getMessage());
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
     * This method will mark frame completion details.
     * 
     * @param MatchDetailsBean
     * @return true: if frame completion is successful.
     */

    public boolean updateFrameCompletionDetails(MatchDetailsBean bean)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordUpdated = false;

        try
        {
            logger.info("MatchesServiceImpl >> updateFrameCompletionDetails");

            logger.info("Updating frame completion details for: Match Number : " + bean.getMatchNo()
                    + " Business Date: " + bean.getBusinessDate() + " Paying Player : " + bean.getPayingPlayer()
                    + " Payment Amount : " + bean.getPaymentAmount() + " Match Status : " + bean.getMatchStatus());

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_FRAME_COMPLETION_DETAILS);
            int n = 1;
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, bean.getPayingPlayer());
            ps.setInt(n++, bean.getPaymentAmount());
            ps.setString(n++, bean.getMatchStatus());
            ps.setString(n++, bean.getMatchNo());
            ps.setString(n++, bean.getBusinessDate());

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
     * This method will get match details based on match number.
     * 
     * @param String matchNo
     * @return MatchDetailsBean: MatchDetailsBean object.
     */

    public MatchDetailsBean getDetailsFromSelectedMatch(String matchNo)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        MatchDetailsBean bean = null;

        try
        {
            logger.info("MatchesServiceImpl >> getPlayersFromSelectedMatchList");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_DETAILS_FROM_SELECTED_MATCH_NO);
            int n = 1;
            ps.setString(n++, matchNo);

            rs = ps.executeQuery();
            while (rs.next())
            {
                bean = new MatchDetailsBean();

                bean.setMatchNo(rs.getString("MATCH_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setFrameType(rs.getString("FRAME_TYPE"));
                bean.setTableNo(rs.getString("TABLE_NO"));
                bean.setPlayingCustomerID(rs.getString("PLAYING_CUST_ID"));
                bean.setFrameStartTime(getJAVADate(rs.getTimestamp("FRAME_START_TIME")));
                bean.setPaymentAmount(rs.getInt("PAYMENT_AMOUNT"));
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching beverages details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching beverages details" + e.getMessage());
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
     * This method will get list of all tables which are currently occupied.
     * 
     * @return List<String>: list of all tables which are currently occupied
     */

    public List<String> getUnavailableTableDetails()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        List<String> unAvailableTablesList = new ArrayList<String>();

        try
        {
            logger.info("MatchesServiceImpl >> getUnavailableTableDetails");

            con = getConnection();
            stmt = con.createStatement();

            LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
            String businessDate = logTimeServiceImplObj.getActiveBusinessDate();

            ps = con.prepareStatement(GET_UNAVAILABLE_TABLES_DETAILS);
            int n = 1;
            ps.setString(n++, "Ongoing");
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();
            while (rs.next())
            {
                unAvailableTablesList.add(rs.getString("TABLE_NO"));
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching beverages details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching beverages details" + e.getMessage());
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
        return unAvailableTablesList;
    }

    /**
     * This method will get count of all matches currently in progress.
     * 
     * @return Integer: count of all matches currently in progress.
     */

    public Integer getOngoingMatchesCount()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        Integer count = 0;

        try
        {
            logger.info("MatchesServiceImpl >> getOngoingMatchesCount");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_ONGOING_MATCHES_COUNT);
            rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching ongoing matches details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching ongoing matches details" + e.getMessage());
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
        return count;
    }

    /**
     * This method will get table wise report for the business date.
     * 
     * @param String custEntryNo
     * @return true: if update is successful.
     */

    public List<MatchDetailsBean> getTablewiseBusinessDayReport(String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<MatchDetailsBean> ongoingMatchesList = new ArrayList<MatchDetailsBean>();

        try
        {
            logger.info("MatchesServiceImpl >> getTablewiseBusinessDayReport");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_TABLEWISE_REPORT);
            ps.setString(1, businessDate);

            rs = ps.executeQuery();
            while (rs.next())
            {
                MatchDetailsBean bean = new MatchDetailsBean();

                bean.setTableNo(rs.getString("TABLE_NO"));
                bean.setPaymentAmount(Integer.parseInt(rs.getString("tableTotalAmount")));

                ongoingMatchesList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting tablewise report details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting tablewise report details" + e.getMessage());
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

        return ongoingMatchesList;
    }

    /**
     * This method will check if the paird rummy match is finished or in
     * progress
     * 
     * @param String custEntryNo
     * @return true: if update is successful.
     */

    public boolean checkPairedRummyMatchStatus(MatchDetailsBean bean)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        Calendar frameStartTime = bean.getFrameStartTime();
        Timestamp timestamp = new Timestamp(frameStartTime.getTimeInMillis());

        boolean isPairedMatchInProgress = false;

        try
        {
            logger.info("MatchesServiceImpl >> getTablewiseBusinessDayReport");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(CHECK_PAIRED_RUMMY_MATCH_STATUS);
            ps.setTimestamp(1, timestamp);
            ps.setString(2, bean.getMatchNo());

            rs = ps.executeQuery();
            while (rs.next())
            {
                if (rs.getString("MATCH_STATUS") == "Ongoing")
                {
                    isPairedMatchInProgress = true;
                }
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting tablewise report details." + se.getMessage());

        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting tablewise report details" + e.getMessage());
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

        return isPairedMatchInProgress;

    }
}