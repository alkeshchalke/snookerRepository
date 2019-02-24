
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<!--<link rel="stylesheet" type="text/css" href="/styles/datatables.min.css"/>
<script type="text/javascript" src="js/datatables.min.js"></script>-->

<script src="js/datatable/jquery.min.js"></script>
<script src="js/datatable/jquery.dataTables.min.js"></script>
<link href="/js/datatable/jquery.dataTables.min.css" rel="stylesheet" />

<script type="text/javascript">
	function validate()
	{
		var newAction = 'UpdateCustomerBeveragesAction.action';
		callAction(newAction);
		return true;
	}
</script>

<script>
	function submitPayBill(custEntryNo) {
		
			var entered = document.getElementById("totalCustBill").value;
			var expected = '<%=request.getAttribute("custRemainingBalance")%>';

		if (entered > expected) {
			alert('Amount can not be increased.');
			document.updateCustBevForm.totalCustBill.value = expected;
			return false;
		}

		var newAction = 'PayCustomerCurrentBillAction.action';
		callAction(newAction);
	}
</script>

<script>
	$(document).ready(function() {
		$('#matchInfo').DataTable({
			"paging" : false,
			"info" : false,
			"ordering" : false
		});
		$('#beverageInfo').DataTable({
			"paging" : false,
			"searching" : false,
			"info" : false,
			"ordering" : false
		});
		$('#matchBillInfo').DataTable({
			"paging" : false,
			"searching" : false,
			"info" : false,
			"ordering" : false
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#teafieldAddButton').click(function() {
			var counter = $('#teafield').val();
			counter++;
			$('#teafield').val(counter);
		});
		$('#teafieldMinusButton').click(function() {
			var counter = $('#teafield').val();
			counter--;
			if(counter < 0)
			{
				alert('Quantity for Tea can not be less than 0');
				return false; 
			}
			$('#teafield').val(counter);
		});
		$('#coffeefieldAddButton').click(function() {
			var counter = $('#coffeefield').val();
			counter++;
			$('#coffeefield').val(counter);
		});
		$('#coffeefieldMinusButton').click(function() {
			var counter = $('#coffeefield').val();
			counter--;
			if(counter < 0)
			{
				alert('Quantity for Coffee can not be less than 0');
				return false; 
			}
			$('#coffeefield').val(counter);
		});
		$('#bottlefieldAddButton').click(function() {
			var counter = $('#bottlefield').val();
			counter++;
			$('#bottlefield').val(counter);
		});
		$('#bottlefieldMinusButton').click(function() {
			var counter = $('#bottlefield').val();
			counter--;
			if(counter < 0)
			{
				alert('Quantity for Bottle can not be less than 0');
				return false; 
			}
			$('#bottlefield').val(counter);
		});
		$('#colddrinkfieldAddButton').click(function() {
			var counter = $('#colddrinkfield').val();
			counter++;
			$('#colddrinkfield').val(counter);
		});
		$('#colddrinkfieldMinusButton').click(function() {
			var counter = $('#colddrinkfield').val();
			counter--;
			if(counter < 0)
			{
				alert('Quantity for Cold Drink can not be less than 0');
				return false; 
			}
			$('#colddrinkfield').val(counter);
		});

	});
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/UpdateCustomerBeveragesAction.action"
	form="updateCustBevForm" method="post" theme="simple"
	onsubmit="return(validate());">

	<s:hidden name="custEntryNo" value="%{custEntryNo}" />
	<s:hidden name="customerID" value="%{customerID}" />
	<br>
	<br>
	<div class="fieldname" style="text-align: center">Matches Details</div>
	<br>
	<table id="matchInfo" border="1" class="display"
		style="width: 75%;">
		<thead>
			<tr>
				<th>Cust Entry No</th>
				<th>Match No</th>
				<th>Employee ID</th>
				<th>Frame Type</th>
				<th>Table Number</th>
				<th>Frame Start Time</th>
				<th>Frame End Time</th>
				<th>Frame Bill</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="customerMatchDetailsDTOsList">
				<tr>
					<td align="center"><s:property value="custEntryNo" /></td>
					<td align="center"><s:property value="matchNo" /></td>
					<td align="center"><s:property value="employeeID" /></td>
					<td align="center"><s:property value="frameType" /></td>
					<td align="center"><s:property value="tableNo" /></td>
					<td align="center"><s:property value="frameStartTime" /></td>
					<td align="center"><s:property value="frameEndTime" /></td>
					<td align="center"><s:property value="custMatchTotalBill" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br>
	<br>
	<div class="fieldname" style="text-align: center">Beverages
		Details</div>
	<br>
	<br>
	<table id="beverageInfo" border="1" class="cell-border"
		style="width: 70%;">
		<thead>
			<tr>
				<th>Cust Entry No</th>
				<th>Tea</th>
				<th>Coffee</th>
				<th>Bottle</th>
				<th>Cold-Drink</th>
				<th>Total Bev Bill</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="customerBeverageDetailsDTOsList">
				<tr>
					<td align="center"><s:property value="custEntryNo" /></td>
					<td align="center"><s:property value="teaQty" /></td>
					<td align="center"><s:property value="coffeeQty" /></td>
					<td align="center"><s:property value="bottleQty" /></td>
					<td align="center"><s:property value="colddrinkQty" /></td>
					<td align="center"><s:property value="totalBill" /></td>
				</tr>
			</s:iterator>
			
			<s:iterator value="customerCurrentBeverageDetailsDTOsList">
				<tr>
					<td align="center"><s:property value="custEntryNo" /></td>
					
					<td align="center">
					<input type="Button" id='teafieldMinusButton' value="-" />
					<s:textfield id="teafield" name="teafield"
							size="2" maxlength="2" readonly="true" theme="simple"
							align="center" value="%{teaQty}" />
							<input type="Button" id='teafieldAddButton' value="+" />
							<input type="button" value="<s:text name="button.modify" />"
								onclick="return(validate());" /> 
							</td>

					<td align="center">
					<input type="Button" id='coffeefieldMinusButton' value="-" />
					<s:textfield id="coffeefield"
							name="coffeefield" size="2" maxlength="2" readonly="true"
							theme="simple" align="center" value="%{coffeeQty}" /> 
							<input type="Button" id='coffeefieldAddButton' value="+" />
							<input type="button" value="<s:text name="button.modify" />"
								onclick="return(validate());" />
						</td>

					<td align="center">
					<input type="Button" id='bottlefieldMinusButton' value="-" />
					<s:textfield id="bottlefield"
							name="bottlefield" size="2" maxlength="2" readonly="true"
							theme="simple" align="center" value="%{bottleQty}" /> 
							<input type="Button" id='bottlefieldAddButton' value="+" />
							<input type="button" value="<s:text name="button.modify" />"
								onclick="return(validate());" />
						</td>

					<td align="center">
					<input type="Button" id='colddrinkfieldMinusButton' value="-" />
					<s:textfield id="colddrinkfield"
							name="colddrinkfield" size="2" maxlength="2" readonly="true"
							theme="simple" align="center" value="%{colddrinkQty}" /> 
							<input type="Button" id='colddrinkfieldAddButton' value="+" />
							<input type="button" value="<s:text name="button.modify" />"
								onclick="return(validate());" />
						</td>

					<td align="center"><s:property value="totalBill" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<br>
	<br>
	<div class="fieldname" style="text-align: center">Billing Details</div>
	<br>
	<br>
	<table id="matchBillInfo" border="1" class="display" style="width: 60%">
		<thead>
			<tr>
				<th>Total Match Bill</th>
				<th>Total Bev Bill</th>
				<th>Final Bill</th>
				<th>Paid Bill</th>
				<th>Remaining Bill</th>
				<th>Pay Bill</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="customerBillDetailsDTOList">
				<tr>
					<td align="center"><s:property value="incomeFromMatches" /></td>
					<td align="center"><s:property value="incomeFromBeverages" /></td>
					<td align="center"><s:property value="totalBusiness" /></td>
					<td align="center"><s:property value="totalMoneyPaid" /></td>
					<td align="center"><s:property value="totalBalanceAmount" /></td>
					<td align="center"><s:textfield id="totalCustBill"
							name="totalCustBill" size="5" maxlength="4" theme="simple"
							align="center" value="%{totalBalanceAmount}" /> <s:if
							test='%{totalBalanceAmount != 0}'>
							<input type="button" value="<s:text name="button.payBill" />"
								onclick="submitPayBill('<s:property value="custEntryNo" />');" />
						</s:if></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:form>

<script>
	document.getElementById("logTimeTab").style.textDecoration = "underline";
	document.getElementById("logTimeTab").style.backgroundColor = "#9DB1ED";
</script>
