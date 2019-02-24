package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.projectwork.bean.CustomerBeverageDetailsBean;
import com.projectwork.constants.TestProjectConstantsIfc;
import com.projectwork.impl.sql.BeveragesServiceSQLIfc;

public class BeveragesServiceImpl extends DatabaseConnectionServiceImpl
        implements TestProjectConstantsIfc, BeveragesServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(BeveragesServiceImpl.class);

    /**
     * This method will populate item names and prices from Beverage table
     * 
     * @return Map<String, Integer>: A map with Item name - Item Price mapping
     */
    public Map<String, Integer> getBeveragesDetails()
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;

        Map<String, Integer> beveragesDetailMap = new HashMap<String, Integer>();

        String sql = null;

        try
        {
            logger.info("BeveragesServiceImpl >> getBeveragesDetails");

            con = getConnection();
            stmt = con.createStatement();

            sql = GET_BEVERAGES_DETAILS;
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                beveragesDetailMap.put(rs.getString("ITEM_NAME"), rs.getInt("ITEM_PRICE"));
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

        return beveragesDetailMap;
    }

    /**
     * This method add new record whenever a new customer is logged in for a
     * Business day.
     * 
     * @param String custEntryNo: Generated unique customer entry number.
     * @param String businessDate: Business Date
     * @return true: if Beverage record is added successfully.
     */
    public boolean addNewCustomerBeverageRecord(String custEntryNo, String customerID)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isRecordInserted = false;

        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        String businessDate = logTimeServiceImplObj.getActiveBusinessDate();

        try
        {
            logger.info("LogTimeServiceImpl >> addNewCustomerBeverageRecord");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(ADD_CUSTOMER_BEVERAGE_DETAILS);
            int n = 1;
            ps.setString(n++, custEntryNo);
            ps.setString(n++, customerID);
            ps.setString(n++, businessDate);
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
            logger.error("SQL Exception occured while adding customer beverages details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while adding customer beverages details" + e.getMessage());
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
     * This method will populate beverages record for a selected customer.
     * 
     * @param String custEntryNo: Generated unique customer entry number.
     * @return CustomerBeverageDetailsBean:
     */
    public List<CustomerBeverageDetailsBean> getCustomerBeveragesDetails(String customerID, String businessDate)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        
        List<CustomerBeverageDetailsBean> customerBeverageDetailsBeanList = new ArrayList<CustomerBeverageDetailsBean>();

        try
        {
            logger.info("BeveragesServiceImpl >> getCustomerBeveragesDetails");

            con = getConnection();
            stmt = con.createStatement();

            ps = con.prepareStatement(GET_CUSTOMER_BEVERAGE_DETAILS);
            int n = 1;
            ps.setString(n++, customerID);
            ps.setString(n++, businessDate);

            rs = ps.executeQuery();

            while (rs.next())
            {
                CustomerBeverageDetailsBean bean = new CustomerBeverageDetailsBean();

                bean.setCustEntryNo(rs.getString("CUST_ENTRY_NO"));
                bean.setBusinessDate(rs.getString("BUSINESS_DATE"));
                bean.setTeaQty(rs.getInt("TEA_QTY"));
                bean.setCoffeeQty(rs.getInt("COFFEE_QTY"));
                bean.setBottleQty(rs.getInt("BOTTLE_QTY"));
                bean.setColddrinkQty(rs.getInt("COLDDRINK_QTY"));
                bean.setTotalBill(rs.getInt("CUST_BEV_TOTAL_BILL"));

                if (rs.getInt("TEA_QTY") > 0 || rs.getInt("COFFEE_QTY") > 0 || rs.getInt("BOTTLE_QTY") > 0
                        || rs.getInt("COLDDRINK_QTY") > 0)
                {
                    Map<String, Integer> beveragesDetailMap = getBeveragesDetails();
                    int teaBill = beveragesDetailMap.get("Tea") * rs.getInt("TEA_QTY");
                    int coffeeBill = beveragesDetailMap.get("Coffee") * rs.getInt("COFFEE_QTY");
                    int bottleBill = beveragesDetailMap.get("Bottle") * rs.getInt("BOTTLE_QTY");
                    int coldDrinkBill = beveragesDetailMap.get("ColdDrink") * rs.getInt("COLDDRINK_QTY");

                    int totalBill = teaBill + coffeeBill + bottleBill + coldDrinkBill;
                    bean.setTotalBill(totalBill);
                }
                
                customerBeverageDetailsBeanList.add(bean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while fetching customer beverages details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while fetching customer beverages details" + e.getMessage());
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

        return customerBeverageDetailsBeanList;
    }

    /**
     * This method will update any beverages quantity for a selected customer.
     * 
     * @param String custEntryNo: Generated unique customer entry number.
     * @return CustomerBeverageDetailsBean:
     */
    public boolean updateCustomerBeveragesQtyDetails(String custEntryNo, String customerID, int teaQty, int coffeeQty,
            int bottleQty, int colddrinkQty)
    {
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;
        boolean isUpdateSuccessful = false;
        
        LogTimeServiceImpl logTimeServiceImplObj = new LogTimeServiceImpl();
        String businessDate = logTimeServiceImplObj.getActiveBusinessDate();
        
        List<CustomerBeverageDetailsBean> customerBeverageDetailsBeanList = getCustomerBeveragesDetails(customerID, businessDate);
        
        CustomerBeverageDetailsBean existingBeverageBean = customerBeverageDetailsBeanList.get(0);

        try
        {
            logger.info("BeveragesServiceImpl >> updateCustomerBeveragesQtyDetails");
            
            logger.info("Updating beverage record for: Customer Entry Number : " + custEntryNo
            + " Customer ID : " + customerID + " Tea Qty : " + teaQty + " Coffee Qty : " + coffeeQty
            + " Bottle Qty : " + bottleQty + " Cold Drink Qty : " + colddrinkQty);

            con = getConnection();
            stmt = con.createStatement();

            Map<String, Integer> beveragesDetailMap = getBeveragesDetails();
            int teaBill = beveragesDetailMap.get("Tea") * teaQty;
            int coffeeBill = beveragesDetailMap.get("Coffee") * coffeeQty;
            int bottleBill = beveragesDetailMap.get("Bottle") * bottleQty;
            int coldDrinkBill = beveragesDetailMap.get("ColdDrink") * colddrinkQty;

            int totalBevBill = teaBill + coffeeBill + bottleBill + coldDrinkBill;

            ps = con.prepareStatement(UPDATE_CUSTOMER_BEVERAGES_DETAILS);
            int n = 1;
            ps.setInt(n++, teaQty);
            ps.setInt(n++, coffeeQty);
            ps.setInt(n++, bottleQty);
            ps.setInt(n++, colddrinkQty);
            ps.setInt(n++, totalBevBill);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setString(n++, custEntryNo);

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isUpdateSuccessful = true;

                // Set the same beverages total amount in Business table

                
                logTimeServiceImplObj.updateCustomerPaymentDetailsOnBeverageMod(custEntryNo, totalBevBill, 0, 0);

                // Set the same beverages total amount in Customer table

                CustomerServiceImpl customerServiceImplObj = new CustomerServiceImpl();
                customerServiceImplObj.updateCustomerBeverageBill(customerID, totalBevBill, existingBeverageBean);
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while updating beverages details." + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while updating beverages details" + e.getMessage());
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

        return isUpdateSuccessful;
    }

}
