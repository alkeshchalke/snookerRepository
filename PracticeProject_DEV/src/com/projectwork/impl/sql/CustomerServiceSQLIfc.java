package com.projectwork.impl.sql;

public interface CustomerServiceSQLIfc
{
    String CREATE_CUSTOMER_QUERY = " INSERT INTO CUSTOMER (CUST_ID, ENCRYPTED_PWD, CUST_FIRST_NAME, CUST_LAST_NAME, CUST_DOB, CONTACT_NO, CREATION_TIME, "
            + " MODIFICATION_TIME) VALUES (?,?,?,?,?,?,?,?)";

    String UPDATE_CUSTOMER_INFORMATION = "UPDATE CUSTOMER SET CUST_FIRST_NAME = ?, CUST_LAST_NAME = ?, CUST_DOB = ?, CONTACT_NO = ?, "
            + " MODIFICATION_TIME = ? WHERE CUST_ID = ?";
    
    String DELETE_CUSTOMER_INFORMATION = "DELETE FROM CUSTOMER WHERE CUST_ID = ?";

    String POPULATE_CUSTOMER_DATA = "SELECT CUST_ID, CUST_FIRST_NAME, CUST_LAST_NAME, CUST_DOB, CONTACT_NO FROM CUSTOMER "
            + " WHERE CUST_FIRST_NAME = ? AND CUST_LAST_NAME = ? ORDER BY CUST_FIRST_NAME, CUST_LAST_NAME";

    String VALIDATE_CUSTOMER = "SELECT COUNT(*) FROM CUSTOMER WHERE CUST_ID =?";
    
    String CHECK_EOD_COMPLETION_STATUS = "SELECT COUNT(*) FROM CUSTOMER WHERE IS_PRESENT_NOW = 'Y' OR IS_PLAYING_NOW = 'Y' "
            + " OR ACTIVE_CUST_ENTRY_NO = ?";

    String GET_ACTIVE_CUSTOMER_ENTRY_NO = "SELECT ACTIVE_CUST_ENTRY_NO FROM CUSTOMER WHERE CUST_ID = ?";

    String GET_CUSTOMER_BILL_DETAILS = "SELECT CUST_BEV_TOTAL_BILL, CUST_MATCHES_TOTAL_BILL, CUST_PAID_BILL "
            + " FROM CUSTOMER WHERE CUST_ID = ? ";

    String POPULATE_ALL_CUSTOMER_LIST_QUERY = "SELECT CUST_FIRST_NAME, CUST_LAST_NAME FROM CUSTOMER ORDER BY CUST_FIRST_NAME, CUST_LAST_NAME";

    String POPULATE_CUSTOMERNAME_LIST_QUERY = "SELECT CUST_FIRST_NAME, CUST_LAST_NAME FROM CUSTOMER WHERE IS_PRESENT_NOW = 'N' ORDER BY CUST_FIRST_NAME, CUST_LAST_NAME";

    String POPULATE_AVAILABLE_CUSTOMER_LIST_QUERY = "SELECT CUST_FIRST_NAME, CUST_LAST_NAME FROM CUSTOMER WHERE IS_PRESENT_NOW = 'Y' ORDER BY "
            + " CUST_FIRST_NAME, CUST_LAST_NAME";

    String POPULATE_NONPLAYING_CUSTOMER_LIST = "SELECT CUST_ID, CUST_FIRST_NAME, CUST_LAST_NAME FROM CUSTOMER WHERE IS_PLAYING_NOW = 'N' "
            + "AND IS_PRESENT_NOW = 'Y' ORDER BY CUST_FIRST_NAME, CUST_LAST_NAME";

    String CHECK_IF_CUSTOMER_PLAYING_NOW = "SELECT COUNT(*) FROM CUSTOMER WHERE IS_PLAYING_NOW = 'Y' AND CUST_ID = ? ";

    String UPDATE_CUSTOMER_PRESENT_STATUS = "UPDATE CUSTOMER SET IS_PRESENT_NOW = ?, ACTIVE_CUST_ENTRY_NO = ?, "
            + " MODIFICATION_TIME = ?  WHERE CUST_ID = ? ";

    String UPDATE_CUSTOMER_BILL = "UPDATE CUSTOMER SET CUST_BEV_TOTAL_BILL = ?, CUST_MATCHES_TOTAL_BILL = ?, "
            + " CUST_PAID_BILL = ?, MODIFICATION_TIME = ? WHERE CUST_ID = ? ";
    
    String UPDATE_CUSTOMER_BEVERAGE_BILL = "UPDATE CUSTOMER SET CUST_BEV_TOTAL_BILL = ?, MODIFICATION_TIME = ? WHERE CUST_ID = ? ";

    String GET_CUSTOMER_ID_FROM_NAME = "SELECT CUST_ID FROM CUSTOMER WHERE CUST_FIRST_NAME = ? AND CUST_LAST_NAME=?";

    String GET_CUSTOMER_NAME_FROM_ID = "SELECT CUST_FIRST_NAME, CUST_LAST_NAME FROM CUSTOMER WHERE CUST_ID = ?";
}
