package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.projectwork.impl.sql.EmployeeServiceSQLIfc;

public class LoginServiceImpl extends DatabaseConnectionServiceImpl implements EmployeeServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

    /**
     * This method will validate user credentials and will navigate to main
     * screen if validation is successful.
     * @param String
     * @param String
     * @return true: if validation is successful.
     * @throws Exception
     */

    public boolean validateLoginCredentials(String loginid, String password)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;

        boolean isUserPresent = false;

        String encryptedPassword = getDBLevelEncryptedString(password);

        try
        {
            logger.info("LoginServiceImpl >> validateLoginCredentials");

            con = getConnection();
            stmt = con.createStatement();

            sql = VALIDATE_USER;
            ps = con.prepareStatement(sql);

            ps.setString(1, loginid);
            ps.setString(2, encryptedPassword);

            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count <= 0)
            {
                logger.error("Failed to determine if user " + loginid + " exits.");
            }
            else
            {
                logger.info("Validation for user : " + loginid + " is successful");
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
     * This method will check if user is present in database.
     * 
     * @param String
     * @return true: if user is present.
     * @throws Exception
     */

    public boolean isUserPresent(String loginid)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        Statement stmt = null;
        String sql = null;

        boolean isUserPresent = false;

        try
        {
            logger.info("LoginServiceImpl >> isUserPresent");

            con = getConnection();
            stmt = con.createStatement();

            sql = CHECK_USER;
            ps = con.prepareStatement(sql);

            ps.setString(1, loginid);

            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count <= 0)
            {
                logger.error("User " + loginid + " does not exist.");
            }
            else
            {
                logger.info("User : " + loginid + " is not present in the database");
                isUserPresent = true;
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while validating user " + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Failed to determine if user " + loginid + " exits.");
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
        return isUserPresent;
    }
}
