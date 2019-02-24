package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.projectwork.bean.CustomerBean;
import com.projectwork.bean.CustomerBeverageDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.sql.CustomerServiceSQLIfc;

public class CustomerServiceImpl extends DatabaseConnectionServiceImpl
        implements TestProjectConstantsIfc, CustomerServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    /**
     * This method will add a new Customer.
     * 
     * @param String
     * @param String
     * @return true: if customer addition is successful.
     */

    public boolean addNewCustomer(String customerID, String customerPassword, String customerFirstName,
            String customerLastName, String customerDob, String customerContactNumber)
    {
        boolean isRecordInserted = false;
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        String sql = null;

        // String encryptedPassword =
        // getDBLevelEncryptedString(customerPassword);

        try
        {
            logger.info("CustomerServiceImpl >> addNewcustomer");

            logger.info("Adding new customer record for: Customer ID : " + customerID + " First Name : "
                    + customerFirstName + " Last Name : " + customerLastName + " Date of Birth : " + customerDob
                    + " Contact Number : " + customerContactNumber);

            con = getConnection();
            stmt = con.createStatement();

            sql = CREATE_CUSTOMER_QUERY;
            ps = con.prepareStatement(sql);
            int n = 1;

            ps.setString(n++, customerID);
            // ps.setString(n++, encryptedPassword);
            ps.setString(n++, "");
            ps.setString(n++, customerFirstName);
            ps.setString(n++, customerLastName);
            ps.setString(n++, customerDob);
            ps.setString(n++, customerContactNumber);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isRecordInserted = true;
                logger.info("Record for Customer inserted successfully.");
            }
            else
            {
                logger.info("Record for Customer could not be inserted.");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while adding new customer " + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while adding new customer " + e.getMessage());
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

        return isRecordInserted;
    }

    /**
     * This method will update existing Customer information.
     * 
     * @param String
     * @param String
     * @return true: if customer modification is successful.
     */

    public boolean updateCustomerInformation(String customerID, String customerFirstName, String customerLastName,
            String customerDob, String customerContactNumber)
    {
        boolean isRecordInserted = false;
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> updateCustomerInformation");

            logger.info("Updating Customer information for: Customer ID : " + customerID + customerFirstName
                    + " Last Name : " + customerLastName + " Date of Birth : " + customerDob + " Contact Number : "
                    + customerContactNumber);

            con = getConnection();
            stmt = con.createStatement();

            sql = UPDATE_CUSTOMER_INFORMATION;
            ps = con.prepareStatement(sql);
            ps.setString(1, customerID);
            int n = 1;

            ps.setString(n++, customerFirstName);
            ps.setString(n++, customerLastName);
            ps.setString(n++, customerDob);
            ps.setString(n++, customerContactNumber);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, customerID);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isRecordInserted = true;
                logger.info(
                        "Record for Customer " + customerFirstName + " " + customerLastName + " upadted successfully.");
            }
            else
            {
                logger.info(
                        "Record for Customer " + customerFirstName + " " + customerLastName + " could not be updated.");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating Customer " + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating Customer " + e.getMessage());
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

        return isRecordInserted;
    }

    /**
     * This method will delete existing Customer.
     * 
     * @param String customerName
     * @return true: if customer deletion is successful.
     */

    public boolean deleteCustomer(String customerName)
    {
        boolean isRecordDeleted = false;
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        String sql = null;

        String customerID = getCustomerIDFromName(customerName);

        try
        {
            logger.info("CustomerServiceImpl >> deleteCustomer");

            logger.info("Deleting Customer record for: " + customerName);

            con = getConnection();
            stmt = con.createStatement();

            sql = DELETE_CUSTOMER_INFORMATION;
            ps = con.prepareStatement(sql);
            ps.setString(1, customerID);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isRecordDeleted = true;
                logger.info("Record for Customer " + customerName + " deleted successfully.");
            }
            else
            {
                logger.info("Record for Customer " + customerName + " deleted could not be updated.");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while deleting Customer " + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while deleting Customer " + e.getMessage());
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

        return isRecordDeleted;
    }

    /**
     * This method will get the customer information based on first and last
     * name.
     * 
     * @param String Customer name: First Name and Last Name.
     * @return CustomerBean: CustomerBean object with Customer information.
     */

    public CustomerBean getCustomerDetailsForEdit(String customerName)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        CustomerBean bean = new CustomerBean();

        String[] customerNameArray = customerName.split(" ");

        String firstName = customerNameArray[0];
        String lastName = customerNameArray[1];

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getCustomerDetailsForEdit");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_CUSTOMER_DATA;
            ps = con.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            rs = ps.executeQuery();

            if (rs.next())
            {
                bean.setCustomerID(rs.getString("CUST_ID"));
                bean.setCustomerFirstName(rs.getString("CUST_FIRST_NAME"));
                bean.setCustomerLastName(rs.getString("CUST_LAST_NAME"));
                bean.setCustomerDob(rs.getString("CUST_DOB"));
                bean.setCustomerContactNumber(rs.getString("CONTACT_NO"));
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting the Customer ID from name." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting the Customer ID from name." + e.getMessage());
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
     * This method will check if the customer currently being added is already
     * part of the system.
     * 
     * @param String Customer ID.
     * @return true: if customer is already part of the system.
     */

    public boolean validateIfCustomerAlreadyAdded(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;

        boolean isUserPresent = false;

        try
        {
            logger.info("CustomerServiceImpl >> validateIfCustomerAlreadyAdded");

            con = getConnection();
            stmt = con.createStatement();

            sql = VALIDATE_CUSTOMER;
            ps = con.prepareStatement(sql);

            ps.setString(1, customerID);

            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count <= 0)
            {
                logger.error("Failed to determine if user " + customerID + " exits.");
            }
            else
            {
                logger.info("Validation for user : " + customerID + " is successful");
                isUserPresent = true;
            }

        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while validating user " + se.getMessage());
        }

        catch (Exception e)
        {
            logger.error("Exception occured while validating user " + e.getMessage());
        }

        finally
        {
            try
            {
                rs.close();
                stmt.close();
                con.close();
            }
            catch (Exception e)
            {
                logger.error("Exception in closing DB resources");
            }
        }
        return isUserPresent;
    }

    /**
     * This method will populate list of all Customers IDs in the system.
     * 
     * @return List<String>: List of all Customer Names.
     */

    public List<String> populateAllCustomersList()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<String> customerNamesList = new ArrayList<String>();

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> populateAllCustomersList");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_ALL_CUSTOMER_LIST_QUERY;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                String name = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
                customerNamesList.add(name);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching Customer details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching Customer details" + e.getMessage());
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

        return customerNamesList;
    }

    /**
     * This method will populate list of all Customers IDs who are not present
     * at the moment.
     * 
     * @return List<String>: List of all Customer Names.
     */

    public List<String> populateCustomerList()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<String> customerIDList = new ArrayList<String>();

        String sql = null;
        String name = null;

        try
        {
            logger.info("CustomerServiceImpl >> populateCustomerList");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_CUSTOMERNAME_LIST_QUERY;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                name = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
                customerIDList.add(name);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching Customer details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching Customer details" + e.getMessage());
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

        return customerIDList;
    }

    /**
     * This method will populate list of all Customers IDs who are present at
     * the moment.
     * 
     * @return List<String>: List of all Customer Names.
     */

    public List<String> populateAvailableCustomerList()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        List<String> customerIDList = new ArrayList<String>();

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> populateAvailableCustomerList");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_AVAILABLE_CUSTOMER_LIST_QUERY;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                String customerName = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
                customerIDList.add(customerName);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching Customer details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching Customer details" + e.getMessage());
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

        return customerIDList;
    }

    /**
     * This method will update presence status of the Customers.
     * 
     * @param String custEntryNo
     * @param String customerID
     * @param String status (Y or N)
     * @return true: if record update is successful.
     */

    public boolean updateCustomerPresenceStatus(String custEntryNo, String customerID, String status)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordUpdated = false;

        try
        {
            logger.info("CustomerServiceImpl >> updateCustomerPresenceStatus");

            logger.info("Updating Customer Presence Status for: Customer Entry Number : " + custEntryNo
                    + " Customer ID : " + customerID + " Status : " + status);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_PRESENT_STATUS);
            int n = 1;
            ps.setString(n++, status);
            ps.setString(n++, custEntryNo);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, customerID);

            rs = ps.executeUpdate();
            if (rs == 1)
            {
                isRecordUpdated = true;
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating customer presence status." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating customer presence status." + e.getMessage());
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
     * This method will populate the list of all customers who are present but
     * are not playing currently.
     * 
     * @return List<String>: list of all customers who are present but are not
     *         playing currently.
     */

    public List<String> getCurrentlyNonPlayingCustomerList()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String customerName = null;

        List<String> currentlyNonPlayingCustomerList = new ArrayList<String>();

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> populateCustomerList");

            con = getConnection();
            stmt = con.createStatement();

            sql = POPULATE_NONPLAYING_CUSTOMER_LIST;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                customerName = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
                currentlyNonPlayingCustomerList.add(customerName);
            }
        }

        catch (SQLException se)
        {
            logger.error(
                    "SQL Exception occured while getting list of all customers who are present but are not playing currently."
                            + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error(
                    "Exception occured while getting list of all customers who are present but are not playing currently."
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
        return currentlyNonPlayingCustomerList;
    }

    /**
     * This method will update the status of currently playing customers
     * 
     * @param String match number
     * @param String
     * @return true: if validation is successful.
     */

    public boolean updateCurrentlyPlayingCustomerStatus(String matchNo, String playingCustomers, String status)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        boolean isAllCustomerStatusUpdated = false;

        String listofPlayingCustomersForQuery = playingCustomers.replaceAll("_", "','");

        try
        {
            logger.info("CustomerServiceImpl >> updateCurrentlyPlayingCustomerStatus");

            logger.info("Updating Customer Playing Status for: Customers : " + listofPlayingCustomersForQuery
                    + " Status : " + status);

            con = getConnection();
            stmt = con.createStatement();

            String sql = " UPDATE CUSTOMER SET IS_PLAYING_NOW = '" + status + "' WHERE CUST_ID in ('"
                    + listofPlayingCustomersForQuery + "')";

            ps = con.prepareStatement(sql);
            rs = ps.executeUpdate();

            if (rs > 0)
            {
                isAllCustomerStatusUpdated = true;
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

        return isAllCustomerStatusUpdated;
    }

    /**
     * This method will mark playing status of currently active customers.
     * 
     * @param String
     * @param String
     * @return true: if customer status modification is successful.
     */

    public boolean updateCurrentlyPlayingCustomerStatus(String matchNo, String status)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        boolean isAllCustomerStatusUpdated = false;

        CustomerMatchesServiceImpl customerMatchesServiceImplObj = new CustomerMatchesServiceImpl();
        String listofPlayingCustomersForQuery = customerMatchesServiceImplObj
                .getCurrentlyPlayingCustomerListFromMatchNo(matchNo);

        try
        {
            logger.info("CustomerServiceImpl >> populateCustomerList");

            logger.info("Updating playing status of currently active customers : Playing Customers : "
                    + listofPlayingCustomersForQuery + " Status : " + status);

            con = getConnection();
            stmt = con.createStatement();

            String sql = " UPDATE CUSTOMER SET IS_PLAYING_NOW = '" + status + "' WHERE CUST_ID in ('"
                    + listofPlayingCustomersForQuery + ")";

            ps = con.prepareStatement(sql);
            rs = ps.executeUpdate();

            if (rs > 0)
            {
                isAllCustomerStatusUpdated = true;
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

        return isAllCustomerStatusUpdated;
    }

    /**
     * This method will get customer entry numbers.
     * 
     * @param String listofPlayingCustomers : List of playing customers whose
     *            entry numbers are to be determined.
     * @return Map<String, String>: Customer_ID - Customer_Entry_No pairs.
     */

    public Map<String, String> getActiveCustomerEntryDetails(String listofPlayingCustomers)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        Map<String, String> currentlyPlayingCustomerMap = new HashMap<String, String>();
        String listofPlayingCustomersForQuery = listofPlayingCustomers.replaceAll("_", "','");

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getActiveCustomerEntryDetails");

            con = getConnection();
            stmt = con.createStatement();

            sql = "SELECT ACTIVE_CUST_ENTRY_NO, CUST_ID FROM CUSTOMER WHERE IS_PRESENT_NOW = 'Y' AND CUST_ID in ('"
                    + listofPlayingCustomersForQuery + "') ORDER BY CUST_ID";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                currentlyPlayingCustomerMap.put(rs.getString("CUST_ID"), rs.getString("ACTIVE_CUST_ENTRY_NO"));
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
        return currentlyPlayingCustomerMap;
    }

    /**
     * This method will count the number of customers whose out time is not
     * marked even after end of day.
     * 
     * @return Integer: the number of customers whose out time is not marked
     */

    public Integer getUnmarkedOutTimesCount()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        Integer count = 0;

        try
        {
            logger.info("CustomerServiceImpl >> getUnmarkedOutTimesCount");

            con = getConnection();
            stmt = con.createStatement();

            sql = CHECK_EOD_COMPLETION_STATUS;
            ps = con.prepareStatement(sql);
            ps.setString(1, null);

            rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
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
     * This method will get customer entry number based on ID.
     * 
     * @param String customerID
     * @return String: customer entry number
     */

    public String getActiveCustomerEntryNoFromID(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        String activeCustomerEntryNo = null;

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getActiveCustomerEntryNoFromID");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_ACTIVE_CUSTOMER_ENTRY_NO;
            ps = con.prepareStatement(sql);
            ps.setString(1, customerID);

            rs = ps.executeQuery();

            if (rs.next())
            {
                activeCustomerEntryNo = rs.getString("ACTIVE_CUST_ENTRY_NO");
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
        return activeCustomerEntryNo;
    }

    /**
     * This method will get bill details for a customer based on ID.
     * 
     * @param String customerID
     * @return CustomerBean: CustomerBean object with Customer information.
     */

    public CustomerBean getCustomerBillDetails(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        CustomerBean bean = null;

        try
        {
            logger.info("CustomerServiceImpl >> getCustomerBillDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_BILL_DETAILS);
            int n = 1;
            ps.setString(n++, customerID);

            rs = ps.executeQuery();

            if (rs.next())
            {
                bean = new CustomerBean();

                bean.setCustomerID(customerID);
                bean.setCustBeverageTotalBill(rs.getInt("CUST_BEV_TOTAL_BILL"));
                bean.setCustMatchTotalBill(rs.getInt("CUST_MATCHES_TOTAL_BILL"));
                bean.setCustPaidBill(rs.getInt("CUST_PAID_BILL"));
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
     * This method will update bill details for a customer.
     * 
     * @param String customerID
     * @param int newBevBill
     * @param int newMatchBill
     * @param int newPaidBill
     * @return true: if bill modification is successful.
     */

    public boolean updateCustomerBill(String customerID, int newBevBill, int newMatchBill, int newPaidBill)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isCustomerBillUpdated = false;

        // Get existing total match bill from Business table and add new bill
        // into it.

        CustomerBean bean = getCustomerBillDetails(customerID);

        if (bean != null)
        {
            newBevBill += bean.getCustBeverageTotalBill();
            newMatchBill += bean.getCustMatchTotalBill();
            newPaidBill += bean.getCustPaidBill();
        }

        try
        {
            logger.info("CustomerServiceImpl >> updateCustomerBill");

            logger.info("Updating Payment Details for: Customer ID : " + customerID + " Beverages Bill : " + newBevBill
                    + " Match Bill : " + newMatchBill + " Paid Bill : " + newPaidBill);

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_BILL);
            int n = 1;
            ps.setInt(n++, newBevBill);
            ps.setInt(n++, newMatchBill);
            ps.setInt(n++, newPaidBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, customerID);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isCustomerBillUpdated = true;
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

        return isCustomerBillUpdated;
    }

    /**
     * This method will update beverage bill details for a customer.
     * 
     * @param String customerID
     * @param int newBevBill
     * @param int newMatchBill
     * @param int newPaidBill
     * @return true: if bill modification is successful.
     */

    public boolean updateCustomerBeverageBill(String customerID, int newBevBill,
            CustomerBeverageDetailsBean existingBeverageBean)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isCustomerBillUpdated = false;

        // Get existing total match bill from Business table and add new bill
        // into it.

        CustomerBean bean = getCustomerBillDetails(customerID);

        if (bean != null)
        {
            if (existingBeverageBean.getTotalBill() <= newBevBill)
            {
                newBevBill = bean.getCustBeverageTotalBill() + newBevBill;
            }
            else
            {
                newBevBill = bean.getCustBeverageTotalBill() - newBevBill;
            }
        }

        try
        {
            logger.info("CustomerServiceImpl >> updateCustomerBill");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(UPDATE_CUSTOMER_BEVERAGE_BILL);
            int n = 1;
            ps.setInt(n++, newBevBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, customerID);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isCustomerBillUpdated = true;
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

        return isCustomerBillUpdated;
    }

    /**
     * This method will check if the customer is part of any unfinished match.
     * 
     * @param String customerID
     * @return true: if customer is part of any unfinished match.
     */

    public boolean isCustomerMatchUnfinished(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        int count = 0;
        boolean isCustomerMatchUnfinished = false;

        try
        {
            logger.info("CustomerServiceImpl >> isCustomerMatchUnfinished");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(CHECK_IF_CUSTOMER_PLAYING_NOW);
            int n = 1;
            ps.setString(n++, customerID);

            rs = ps.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
                if (count > 0)
                {
                    isCustomerMatchUnfinished = true;
                }
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

        return isCustomerMatchUnfinished;
    }

    /**
     * This method will check if the system has any customer associated with any
     * older date's customer entry number.
     * 
     * @return List<String>: List of Customer IDs
     */

    public List<String> getPreviosUnmarkedCustomersList()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        List<String> previosUnmarkedCustomersList = new ArrayList<String>();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String businessDate = formatter.format(Calendar.getInstance().getTime());

        logger.info("CustomerServiceImpl >> getPreviosUnmarkedCustomersList");
        logger.info("Business Date is : " + businessDate);

        businessDate = "C" + businessDate.replace("-", "");

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getPreviosUnmarkedCustomersList");

            con = getConnection();
            stmt = con.createStatement();

            sql = "SELECT CUST_ID FROM CUSTOMER WHERE ACTIVE_CUST_ENTRY_NO NOT LIKE '" + businessDate
                    + "%' ORDER BY CUST_ID";
            logger.info("SQL Query :" + sql);
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                previosUnmarkedCustomersList.add(rs.getString("CUST_ID"));
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting list of all customers who were unmarked previous day."
                    + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting list of all customers who were unmarked previous day."
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
        return previosUnmarkedCustomersList;
    }

    /**
     * This method will get the customer id based on first and last name.
     * 
     * @param String Customer name: First Name and Last Name.
     * @return String: Permanent Customer ID for this customer.
     */

    public String getCustomerIDFromName(String customerName)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        String customerID = null;

        String[] customerNameArray = customerName.split(" ");

        String firstName = customerNameArray[0];
        String lastName = customerNameArray[1];

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getCustomerIDFromName");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_CUSTOMER_ID_FROM_NAME;
            ps = con.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            rs = ps.executeQuery();

            if (rs.next())
            {
                customerID = rs.getString("CUST_ID");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting the Customer ID from name." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting the Customer ID from name." + e.getMessage());
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
        return customerID;
    }

    /**
     * This method will get the customer name based on ID.
     * 
     * @param String Customer name: First Name and Last Name.
     * @return String: Permanent Customer ID for this customer.
     */

    public String getCustomerNameFromID(String customerID)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        String customerName = null;

        String sql = null;

        try
        {
            logger.info("CustomerServiceImpl >> getCustomerNameFromID");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_CUSTOMER_NAME_FROM_ID;
            ps = con.prepareStatement(sql);
            ps.setString(1, customerID);

            rs = ps.executeQuery();

            if (rs.next())
            {
                customerName = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while getting the Customer ID from name." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while getting the Customer ID from name." + e.getMessage());
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
        return customerName;
    }

}
