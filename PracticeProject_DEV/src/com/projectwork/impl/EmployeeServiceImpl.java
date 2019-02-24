package com.projectwork.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.projectwork.impl.sql.EmployeeServiceSQLIfc;

public class EmployeeServiceImpl extends DatabaseConnectionServiceImpl implements EmployeeServiceSQLIfc
{
    private static Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    /**
     * This method will Add new employee record.
     * 
     * @param String
     * @param String
     * @param String
     * @param String
     * @return boolean
     * @throws Exception
     */

    public boolean addNewEmployee(String employeeID, String employeePassword, String employeeFirstName,
            String employeeLastName)
    {
        boolean isRecordInserted = false;
        PreparedStatement ps = null;
        int rs = 0;
        Connection con = null;
        Statement stmt = null;

        String sql = null;

        String encryptedPassword = getDBLevelEncryptedString(employeePassword);

        try
        {
            logger.info("EmployeeServiceImpl >> addNewEmployee");

            logger.info("Adding new employee record for: Employee ID : " + employeeID + " First Name: "
                    + employeeFirstName + " Last Name : " + employeeLastName);

            con = getConnection();
            stmt = con.createStatement();

            sql = CREATE_EMPLOYEE_QUERY;
            ps = con.prepareStatement(sql);
            int n = 1;

            ps.setString(n++, employeeID);
            ps.setString(n++, encryptedPassword);
            ps.setString(n++, employeeFirstName);
            ps.setString(n++, employeeLastName);
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(n++, new Timestamp(System.currentTimeMillis()));

            rs = ps.executeUpdate();

            if (rs == 1)
            {
                isRecordInserted = true;
                logger.info("Record for Employee inserted successfully.");
            }
            else
            {
                logger.info("Record for Employee could not be inserted.");
            }
        }

        catch (SQLException se)
        {
            logger.error("SQL Exception occured while adding new employee " + se.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Exception occured while adding new employee " + e.getMessage());
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
}
