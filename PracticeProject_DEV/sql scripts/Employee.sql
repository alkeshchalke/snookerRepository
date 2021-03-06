
DROP TABLE EMPLOYEE;

CREATE TABLE EMPLOYEE(
	EMP_ID 					VARCHAR(14) NOT NULL,
	EMP_FIRST_NAME			VARCHAR(30) NOT NULL,
	EMP_LAST_NAME			VARCHAR(30) NOT NULL,
	ENCRYPTED_PWD			VARCHAR(50) NOT NULL,
	CREATION_TIME			TIMESTAMP,
	MODIFICATION_TIME		TIMESTAMP
	);

ALTER TABLE EMPLOYEE ADD CONSTRAINT EMPLOYEE_PK PRIMARY KEY (EMP_ID);

COMMENT ON TABLE EMPLOYEE  IS 'EMPLOYEE';

INSERT INTO EMPLOYEE (EMP_ID, EMP_FIRST_NAME, EMP_LAST_NAME, ENCRYPTED_PWD) 
VALUES ('alkesh', 'Alkesh', 'Chalke', 'dWBvaWds');
