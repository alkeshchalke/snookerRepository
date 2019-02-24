<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<head>
<sx:head />
</head>
<script type="text/javascript">
	function validate() {
		//	Empty Values check starts

 		if (document.customerForm.customerID.value == "") {
			alert("Please Enter customer ID");
			document.customerForm.customerID.focus();
			return false;
		}
 		
		if (document.customerForm.customerFirstName.value == "") {
			alert("Please Enter customer First Name");
			document.customerForm.customerFirstName.focus();
			return false;
		}

		if (document.customerForm.customerLastName.value == "") {
			alert("Please Enter customer Last Name");
			document.customerForm.customerLastName.focus();
			return false;
		} 
		
		if (document.customerForm.selectedDay.value == -1) {
			alert("Please Select Date");
			document.customerForm.selectedDay.focus();
			return false;
		}
		if (document.customerForm.selectedMonth.value == -1) {
			alert("Please Select Month");
			document.customerForm.selectedMonth.focus();
			return false;
		}
		if (document.customerForm.selectedYear.value == -1) {
			alert("Please Select Year");
			document.customerForm.selectedYear.focus();
			return false;
		}
		if (document.customerForm.customerContactNumber.value == "") {
			alert("Please Enter Contact Number");
			document.customerForm.customerContactNumber.focus();
			return false;
		}
		
		if (document.customerForm.customerPassword.value != document.customerForm.reentercustomerPassword.value) {
			alert("The passwords do not match. Please re enter the passwords.");
			return false;
		}
		
		var newAction = 'AddNewCustomerAction.action';
		callAction(newAction);
		
		return (true);
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>

<s:form action="/AddNewCustomerAction.action" method="post"
	theme="simple" onsubmit="return(validate());" name="customerForm">

	<table width="100%" border="0" cellspacing="3" cellpadding="3"
		align="center" height="100%">
		<tr height="35" />

		<tr>
			<td width="100%" align="center" class="fieldname"><s:text
					name="customer.customerID.label" /> : <s:textfield
					name="customerID" size="20" maxlength="20" value="%{customerID}"
					onkeypress="return RestrictSpace(event);" /></td>
		<tr height="10" />

		<tr>
			<td width="45%" align="center" class="fieldname"><s:text
					name="customer.firstName.label" /> : <s:textfield
					name="customerFirstName" size="20" maxlength="20" value=""
					onkeypress="return RestrictSpace(event);" /></td>
		</tr>
		<tr height="10" />

		<tr>
			<td width="100%" align="center" class="fieldname"><s:text
					name="customer.lastName.label" /> : <s:textfield
					name="customerLastName" size="20" maxlength="20" value=""
					onkeypress="return RestrictSpace(event);" /></td>
		</tr>
		<tr height="10" />

		<tr>
			<td width="100%" align="center" class="fieldname"><s:text
					name="customer.dob.label" /> : 
					<s:select list="daysList" headerKey="-1" headerValue="Select Day" id="selectedDay"
					name="selectedDay" theme="simple"></s:select>
					
					<s:select list="monthsList" headerKey="-1" headerValue="Select Month" id="selectedMonth"
					name="selectedMonth" theme="simple"></s:select>
					
					<s:select list="yearsList" headerKey="-1" headerValue="Select Year" id="selectedYear"
					name="selectedYear" theme="simple"></s:select>
					</td>
		</tr>
		<tr height="10" />

		<tr>
			<td width="100%" align="center" class="fieldname"><s:text
					name="customer.contactNumber.label" /> : <s:textfield
					name="customerContactNumber" size="20" maxlength="10" value=""
					onkeypress="return RestrictSpace(event);" /></td>
		</tr>

		<tr height="15" />

		<tr>
			<td colspan="4" align="center"><button type="submit">
					<s:text name="button.addCustomer" />
				</button></td>
		</tr>
		<tr height="15" />
	</table>
</s:form>

<script>
    document.getElementById("customerTab").style.textDecoration = "underline";
	document.getElementById("customerTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("addCustomertab").style.backgroundColor = "#A9A9F5";
</script>