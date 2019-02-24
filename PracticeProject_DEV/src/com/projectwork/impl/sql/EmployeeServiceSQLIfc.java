package com.projectwork.impl.sql;

public interface EmployeeServiceSQLIfc
{
    String VALIDATE_USER = "SELECT COUNT(*) FROM EMPLOYEE WHERE EMP_ID =? AND ENCRYPTED_PWD =?";

    String CHECK_USER = " SELECT COUNT(*) FROM EMPLOYEE WHERE EMP_ID =? ";

    String CREATE_EMPLOYEE_QUERY = " INSERT INTO EMPLOYEE (EMP_ID, ENCRYPTED_PWD, EMP_FIRST_NAME, EMP_LAST_NAME, CREATION_TIME, "
            + "MODIFICATION_TIME) VALUES (?,?,?,?,?,?)";

}
