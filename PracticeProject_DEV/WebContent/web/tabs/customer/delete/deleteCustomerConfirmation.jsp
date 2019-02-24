
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
	function noButtonClicked() {
		callAction('DeleteCustomerTabAction.action');
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/DeleteCustomerPersistAction.action" method="post" theme="simple"
	name="endOfDayForm">

	<table style="width: 100%">
		<tr>
			<td width="35%" class="fieldname">Delete Customer Confirmation</td>
			<td width="30%"></td>
			<td width="35%"></td>
		</tr>
		
		<tr bgcolor="#CCCCCC">
			<td width="35%"/>
			<td width="30%"/>
			<td width="35%"/>
		</tr>
		<tr height="75" />
		<tr>
			<td width="35%"></td>
			<td width="30%" class="fieldname">Are you sure you want to delete customer <s:iterator value="customerDTOList">
			<s:property value="customerName" /> <s:hidden name="customerName" value="%{customerName}" />
			</s:iterator>?</td>
			
			<td width="35%"></td>
		</tr>
			<tr height="25" />
		<tr>
			<td width="35%"></td>
			<td width="30%" class="fieldname" align="center">Press 'Yes' to delete the customer and Press 'No' to go back to previous page.</td>
			<td width="35%"></td>
		</tr>	
			<tr height="35" />
		<tr>
			<td align="right"> <button type="submit"> 	<s:text name="button.yes" /> </button> </td>
			<td width="30%"/>
			<td width="35%"><input type="button" id="noButton" value="<s:text name="button.no" />" onclick="noButtonClicked()" /></td>
		</tr>
	</table>
</s:form>

<script>
	document.getElementById("customerTab").style.textDecoration = "underline";
	document.getElementById("customerTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("deleteCustomerTab").style.backgroundColor = "#A9A9F5";
</script>