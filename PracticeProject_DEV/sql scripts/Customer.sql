
DROP TABLE CUSTOMER;

CREATE TABLE CUSTOMER(
	CUST_ID 				VARCHAR(14) NOT NULL,
	CUST_FIRST_NAME			VARCHAR(30) NOT NULL,
	CUST_LAST_NAME			VARCHAR(30) NOT NULL,
	ENCRYPTED_PWD			VARCHAR(50) NOT NULL,
	CUST_DOB				VARCHAR(10),
	CONTACT_NO				VARCHAR(30),
	IS_PRESENT_NOW			VARCHAR(2) default 'N',
	IS_PLAYING_NOW			VARCHAR(2) default 'N',
	ACTIVE_CUST_ENTRY_NO	VARCHAR(14),
	CUST_BEV_TOTAL_BILL		INTEGER			DEFAULT 0,
	CUST_MATCHES_TOTAL_BILL	INTEGER			DEFAULT 0,
	CUST_PAID_BILL			INTEGER			DEFAULT 0,
	CREATION_TIME			TIMESTAMP,
	MODIFICATION_TIME		TIMESTAMP
	);

ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY (CUST_ID);

--COMMENT ON TABLE CUSTOMER  IS 'CUSTOMER';

Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust1','Saurabh','Chalke','dWBvaWds','1992-03-06',1234567890,'N',to_timestamp('24-DEC-17 03.10.49.465000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 03.10.49.465000000 PM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust2','Test','User','mWdpZg==','2017-12-10',1234567890,'N',to_timestamp('24-DEC-17 03.10.49.465000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('16-DEC-17 06.22.52.909000000 PM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust3','First','Customer','d5lnmCU=','2017-12-04',122121212,'N',to_timestamp('24-DEC-17 03.10.49.465000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 03.10.49.465000000 PM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust4','Kshitija','Customer','b2dsbZhtbnU=','2017-12-03',122121212,'N',to_timestamp('24-DEC-17 03.13.42.027000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 03.13.42.027000000 PM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust5','asd','asd','dWhn','2017-12-11',122121212,'N',to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust6','aas','add','dWhn','2017-12-11',122121212,'N',to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust7','asd','asd','dWhn','2017-12-11',122121212,'N',to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'));
Insert into CUSTOMER (CUST_ID,CUST_FIRST_NAME,CUST_LAST_NAME,ENCRYPTED_PWD,CUST_DOB,CONTACT_NO,IS_PLAYING_NOW,CREATION_TIME,MODIFICATION_TIME) values ('cust8','aas','add','dWhn','2017-12-11',122121212,'N',to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('24-DEC-17 10.29.08.423000000 AM','DD-MON-RR HH.MI.SS.FF AM'));
