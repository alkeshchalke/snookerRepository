
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript" language="javascript">
	function validate()
	{
		var entered = document.getElementById("paymentAmt").value;
		
		var expected = '<%=request.getAttribute("finalTotalPayableAmount")%>';

		if (entered > expected) {
			alert('Amount can not be increased.');
			document.payHistoryBillForm.paymentAmt.value = expected;
			return false;
		}
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/PayCustomerHistoryBillsAction.action" method="post"
	theme="simple" name="payHistoryBillForm" onsubmit="return(validate());">

<table width="100%">
		<tr>
			<td width="100%" class="fieldname">Customer Details</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%" class="tableoutline">
					<tr>
						<td align="center"></td>
					</tr>

					<tr>
						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Customer Name</div>
						</td>
						<td height="20" width="8%" class="tableheadrow">
							<div align="center" class="tab">Business Date</div>
						</td>
						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Bill From Matches</div>
						</td>
						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Bill From Beverages</div>
						</td>
						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Total Bill</div>
						</td>

						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Total Money Paid</div>
						</td>

						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Total Balance Money</div>
						</td>
					</tr>

					<s:iterator value="customerRecordsDTOs">
						<tr>
							<td height="20" width="5%" class="normal" align="center"><s:property
									value="customerID" /></td>

							<td height="20" width="8%" class="normal" align="center"><s:property
									value="businessDate" /></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="custMatchesTotalBill" /></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="custBeveragesTotalBill" /></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="totalBill" /></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="custPaidBill" /></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="custRemainingBill" /></td>
						</tr>
					</s:iterator>
					<s:hidden name="customerID" value="%{customerID}" />
					<tr>
						<td height="20" width="5%" class="normal" align="center"></td>

						<td height="20" width="8%" class="normal" align="center"></td>

						<td height="20" width="10%" class="normal" align="right">
							Payable Bill :</td>

						<td height="20" width="10%" class="normal" align="left"><s:textfield
								id="paymentAmt" name="paymentAmt" size="6" maxlength="6"
								theme="simple" align="center"
								value="%{#request.finalTotalPayableAmount}" /></td>

						<td height="20" width="10%" class="normal" align="center">
							<button type="submit">
								<s:text name="button.payBill" />
							</button>
						</td>

						<td height="20" width="10%" class="normal" align="center"></td>

						<td height="20" width="10%" class="normal" align="center"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>

<script>
	document.getElementById("customerTab").style.textDecoration = "underline";
	document.getElementById("customerTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("viewCustomerTab").style.backgroundColor = "#A9A9F5";

	
</script>