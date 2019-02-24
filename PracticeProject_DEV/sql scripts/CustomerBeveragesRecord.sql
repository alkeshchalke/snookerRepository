
DROP TABLE CUSTOMER_BEVERAGES_RECORD;

CREATE TABLE CUSTOMER_BEVERAGES_RECORD(
	CUST_ENTRY_NO			VARCHAR(14)   	NOT NULL,
	CUST_ID					VARCHAR(14),	
	BUSINESS_DATE			VARCHAR(10),
	TEA_QTY 				INTEGER			DEFAULT 0,
	COFFEE_QTY 				INTEGER			DEFAULT 0,
	BOTTLE_QTY 				INTEGER			DEFAULT 0,
	COLDDRINK_QTY			INTEGER			DEFAULT 0,
	CUST_BEV_TOTAL_BILL		INTEGER			DEFAULT 0,
	CREATION_TIME			TIMESTAMP,
	MODIFICATION_TIME		TIMESTAMP
	);

ALTER TABLE CUSTOMER_BEVERAGES_RECORD ADD CONSTRAINT CUSTOMER_BEVERAGES_RECORD_PK PRIMARY KEY (CUST_ENTRY_NO);

COMMENT ON TABLE BEVERAGES  IS 'CUSTOMER_BEVERAGES_RECORD';

