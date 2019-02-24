https://github.com/alkeshchalke/snookerRepository.git

http://localhost:8080/PracticeProject

select * from beverages;
select * from CUSTOMER order by modification_time desc;
select * from business order by modification_time desc;
select * from customer_beverages_record order by modification_time desc;
select * from CUSTOMER_MATCHES_RECORD order by modification_time desc;


delete from business;
delete from customer_beverages_record;

update CUSTOMER set is_present_now='N';
update CUSTOMER set is_playing_now='N';
update CUSTOMER set active_cust_entry_no=null; 

commit;
<s:property value="custMatchTotalBill" />
<s:hidden name="payingPlayer" value="%{payingPlayer}" />


delete from business where business_date='2018-01-06';
delete from business_day_matches where business_date='2018-01-06';
delete from CUSTOMER_MATCHES_RECORD where business_date='2018-01-06';
update CUSTOMER set is_present_now='N', is_playing_now='N', active_cust_entry_no=null; commit;

select * from CUSTOMER_MATCHES_RECORD order by modification_time desc;


SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
String businessDate = formatter.format(Calendar.getInstance().getTime());

/**
     * This method will 
     * @param String
     * @param String
     * @return true: if validation is successful.
     */


1> Home Page Status- Present

