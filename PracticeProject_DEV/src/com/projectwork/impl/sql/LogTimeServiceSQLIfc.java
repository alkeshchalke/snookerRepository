package com.projectwork.impl.sql;

public interface LogTimeServiceSQLIfc
{
    String GET_BUSINESS_DETAILS = "SELECT CUST_ENTRY_NO, BUSINESS_DATE, EMP_ID, CUST_ID, CUST_IN_TIME, "
            + " CUST_OUT_TIME, CUST_BEV_TOTAL_BILL, CUST_MATCHES_TOTAL_BILL, CUST_PAID_BILL, CUST_STATUS, PAYMENT_STATUS, "
            + " CREATION_TIME FROM BUSINESS WHERE BUSINESS_DATE = ? ORDER BY CUST_ENTRY_NO";
    
    String GET_BUSINESS_CUSTOMER_UNPAID_BILL_DETAILS = "SELECT CUST_ENTRY_NO, BUSINESS_DATE, CUST_ID, CUST_BEV_TOTAL_BILL, "
            + " CUST_MATCHES_TOTAL_BILL, CUST_PAID_BILL FROM BUSINESS WHERE CUST_ID = ? AND PAYMENT_STATUS = 'NotPaid' "
            + " ORDER BY CUST_ENTRY_NO";

    String GET_NEXT_SEQUENCE_NUMBER = "SELECT COUNT(*) FROM BUSINESS WHERE BUSINESS_DATE = ?";

    String GET_ACTIVE_BUSINESS_DATE = "SELECT MAX(BUSINESS_DATE) FROM BUSINESS ORDER BY BUSINESS_DATE DESC";

    String Add_NEW_BUSINESS_RECORD = "INSERT INTO BUSINESS (CUST_ENTRY_NO, BUSINESS_DATE, EMP_ID, CUST_ID, CUST_IN_TIME, "
            + " CREATION_TIME, MODIFICATION_TIME) VALUES (?,?,?,?,?,?,?)";

    String UPDATE_CUSTOMER_OUT_TIME = "UPDATE BUSINESS SET CUST_OUT_TIME = ?, MODIFICATION_TIME =?  WHERE CUST_ENTRY_NO = ? ";

    String UPDATE_PAYMENT_STATUS = "UPDATE BUSINESS SET PAYMENT_STATUS = ?, MODIFICATION_TIME =?  WHERE CUST_ENTRY_NO = ? AND "
            + " BUSINESS_DATE = ?";

    String POPULATE_CUSTOMER_DETAILS_QUERY = "SELECT CUST_ID, BUSINESS_DATE, SUM(CUST_BEV_TOTAL_BILL) AS totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) AS totalMatchBill, SUM(CUST_PAID_BILL) AS paidBill FROM BUSINESS WHERE CUST_ID=? "
            + " GROUP BY CUST_ID, BUSINESS_DATE ORDER BY CUST_ID,BUSINESS_DATE";

    String GET_CUSTOMER_PAYMENT_DETAILS = "SELECT BUSINESS_DATE, CUST_BEV_TOTAL_BILL, CUST_MATCHES_TOTAL_BILL, CUST_PAID_BILL "
            + " FROM BUSINESS WHERE CUST_ENTRY_NO = ? ";
    
    String GET_TODAYS_CUSTOMER_PAYMENT_DETAILS = "SELECT SUM(CUST_BEV_TOTAL_BILL) as totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) as totalMatchBill, SUM(CUST_PAID_BILL) as paidBill FROM BUSINESS "
            + " WHERE CUST_ID = ? AND BUSINESS_DATE = ?";

    String UPDATE_CUSTOMER_BILL_DETAILS = "UPDATE BUSINESS SET CUST_BEV_TOTAL_BILL = ?, CUST_MATCHES_TOTAL_BILL = ?, "
            + " CUST_PAID_BILL = ?, MODIFICATION_TIME =? WHERE CUST_ENTRY_NO = ? ";

    String GET_TOTAL_CUSTOMER_BILL_DETAILS = "SELECT SUM(CUST_BEV_TOTAL_BILL) as totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) as totalMatchBill, SUM(CUST_PAID_BILL) as paidBill FROM BUSINESS "
            + " WHERE BUSINESS_DATE = ?";
    
    String GET_ALL_CUSTOMER_BILL_DETAILS = "SELECT SUM(CUST_BEV_TOTAL_BILL) as totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) as totalMatchBill, SUM(CUST_PAID_BILL) as paidBill FROM BUSINESS ";

    String GET_CUSTOMER_TODAY_REPORT = "SELECT CUST_ID, SUM(CUST_BEV_TOTAL_BILL) as totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) as totalMatchBill, SUM(CUST_PAID_BILL) as paidBill FROM BUSINESS WHERE "
            + " BUSINESS_DATE = ? GROUP BY CUST_ID ORDER BY CUST_ID";
    
    String GET_CUSTOMER_ALLDAY_REPORT = "SELECT CUST_ID, SUM(CUST_BEV_TOTAL_BILL) as totalBeverageBill, "
            + " SUM(CUST_MATCHES_TOTAL_BILL) as totalMatchBill, SUM(CUST_PAID_BILL) as paidBill FROM BUSINESS "
            + " GROUP BY CUST_ID ORDER BY CUST_ID";

    String GET_UNMARKED_OUTTIME_COUNT = "SELECT COUNT(*) FROM BUSINESS WHERE CUST_OUT_TIME IS NULL AND "
            + " BUSINESS_DATE != ? ORDER BY CUST_ENTRY_NO";
}
