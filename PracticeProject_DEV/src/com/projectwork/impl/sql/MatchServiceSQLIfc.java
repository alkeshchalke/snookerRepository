package com.projectwork.impl.sql;

public interface MatchServiceSQLIfc
{
    String GET_BUSINESS_FRAMES_DETAILS = "SELECT MATCH_NO, BUSINESS_DATE, EMP_ID, FRAME_TYPE, TABLE_NO, "
            + " PLAYING_CUST_ID, FRAME_START_TIME, FRAME_END_TIME, PAYING_PLAYER, PAYMENT_AMOUNT, MATCH_STATUS FROM "
            + " BUSINESS_DAY_MATCHES WHERE BUSINESS_DATE = ? AND MATCH_STATUS != 'Cancelled' ORDER BY MATCH_NO";

    String GET_NEXT_SEQUENCE_NUMBER = "SELECT COUNT(*) FROM BUSINESS_DAY_MATCHES WHERE BUSINESS_DATE = ?";

    String Add_NEW_BUSINESS_SELECTED_FRAMES_RECORD = " INSERT INTO BUSINESS_DAY_MATCHES (MATCH_NO, BUSINESS_DATE, EMP_ID, "
            + " FRAME_TYPE, TABLE_NO, PLAYING_CUST_ID, PAYMENT_AMOUNT, FRAME_START_TIME, CREATION_TIME, "
            + "MODIFICATION_TIME) VALUES (?,?,?,?,?,?,?,?,?,?)";

    String ADD_NEW_PLAYERS_IN_ONGOING_RD_FRAME = " UPDATE BUSINESS_DAY_MATCHES SET PLAYING_CUST_ID = ?, "
            + " MODIFICATION_TIME = ?  WHERE MATCH_NO = ? AND BUSINESS_DATE = ?";

    String CHECK_PAIRED_RUMMY_MATCH_STATUS = "SELECT MATCH_NO, BUSINESS_DATE, FRAME_START_TIME, MATCH_STATUS FROM "
            + " BUSINESS_DAY_MATCHES WHERE FRAME_TYPE= 'Rummy' AND FRAME_START_TIME = ? AND MATCH_NO != ?";

    String UPDATE_FRAME_COMPLETION_DETAILS = " UPDATE BUSINESS_DAY_MATCHES SET FRAME_END_TIME = ?, "
            + " MODIFICATION_TIME = ?, PAYING_PLAYER = ?, PAYMENT_AMOUNT = ?, MATCH_STATUS = ?  WHERE MATCH_NO = ? "
            + " AND BUSINESS_DATE = ?";

    String GET_DETAILS_FROM_SELECTED_MATCH_NO = "SELECT MATCH_NO, BUSINESS_DATE, FRAME_TYPE, TABLE_NO, PLAYING_CUST_ID, "
            + "  FRAME_START_TIME, PAYMENT_AMOUNT FROM BUSINESS_DAY_MATCHES WHERE MATCH_NO = ? ORDER BY PLAYING_CUST_ID";

    String GET_UNAVAILABLE_TABLES_DETAILS = "SELECT TABLE_NO FROM BUSINESS_DAY_MATCHES WHERE MATCH_STATUS = ? "
            + " AND BUSINESS_DATE = ? ";

    String GET_ONGOING_MATCHES_COUNT = "SELECT COUNT(*) FROM BUSINESS_DAY_MATCHES WHERE MATCH_STATUS = 'Ongoing'";

    String GET_TABLEWISE_REPORT = "SELECT TABLE_NO, SUM(PAYMENT_AMOUNT) as tableTotalAmount FROM BUSINESS_DAY_MATCHES WHERE BUSINESS_DATE = ? "
            + " AND MATCH_STATUS != 'Cancelled' GROUP BY TABLE_NO";

}