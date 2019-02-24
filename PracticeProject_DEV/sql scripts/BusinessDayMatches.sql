DROP TABLE BUSINESS_DAY_MATCHES;

CREATE TABLE BUSINESS_DAY_MATCHES(
	MATCH_NO 				VARCHAR(14)   	NOT NULL,
	BUSINESS_DATE			VARCHAR(10)   	NOT NULL,
	EMP_ID					VARCHAR(14),
	FRAME_TYPE				VARCHAR(14),
	TABLE_NO				VARCHAR(14),
	PLAYING_CUST_ID			VARCHAR(240),
	FRAME_START_TIME		TIMESTAMP,
	FRAME_END_TIME			TIMESTAMP       NULL,
	PAYING_PLAYER			VARCHAR(240),
	PAYMENT_AMOUNT			INTEGER DEFAULT 0,
	MATCH_STATUS			VARCHAR(14) DEFAULT 'Ongoing',
	CREATION_TIME			TIMESTAMP,
	MODIFICATION_TIME		TIMESTAMP
	);

ALTER TABLE BUSINESS_DAY_MATCHES ADD CONSTRAINT BUSINESS_DAY_MATCHES_PK PRIMARY KEY (MATCH_NO, BUSINESS_DATE);

--COMMENT ON TABLE BUSINESS_DAY_MATCHES  IS 'BUSINESS_DAY_MATCHES';