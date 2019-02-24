
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript" language="javascript">
	function validate()
	{
		
		var orgteafieldjs = '<%=session.getAttribute("orgteafield")%>';
		var orgcoffeefieldjs = '<%=session.getAttribute("orgcoffeefield")%>';
		var orgbottlefieldjs = '<%=session.getAttribute("orgbottlefield")%>';
		var orgcolddrinkfieldjs = '<%=session.getAttribute("orgcolddrinkfield")%>';

		var jspteafield = document.getElementById("teafield").value;
		var jspcoffeefield = document.getElementById("coffeefield").value;
		var jspbottlefield = document.getElementById("bottlefield").value;
		var jspcolddrinkfield = document.getElementById("colddrinkfield").value;
		
		if (jspteafield < orgteafieldjs) {
			alert('Quantity for Tea can not be decreased.');
			document.updateCustBevForm.teafield.value = orgteafieldjs;
			return false;
		}
		if (jspcoffeefield < orgcoffeefieldjs) {
			alert('Quantity for Coffee can not be decreased.');
			return false;
		}
		if (jspbottlefield < orgbottlefieldjs) {
			alert('Quantity for Bottle can not be decreased.');
			return false;
		}
		if (jspcolddrinkfield < orgcolddrinkfieldjs) {
			alert('Quantity for Cold Drink can not be decreased.');
			return false;
		}
		
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

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/UpdateCustomerBeveragesAction.action"
	form="updateCustBevForm" method="post" theme="simple"
	onsubmit="return(validate());">

	<table style="width: 80%">
		<tr>
			<td class="fieldname">Customer Bill Details</td>
		</tr>
		<tr bgcolor="#CCCCCC" style="width: 100%">
			<td></td>
		</tr>
					<tr height="15" />
					<tr>
						<td class="fieldname" style="text-align: center">Beverages Details</td>
					</tr>
					<tr height="15" />
					<s:iterator value="customerBeverageDetailsDTOsList">
						<tr>
							<td height="20" width="15%" class="fieldname">
								<div align="right">Cust Entry No. : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp;<s:property
									value="custEntryNo" /></td>

							<td height="20" width="15%" class="fieldname">
								<div align="right">Business Date : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp;<s:property
									value="businessDate" /></td>

							<td height="20" width="15%" class="fieldname">
								<div align="right">Customer Name : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp;<s:property
									value="customerID" /></td>
						</tr>
						<tr height="5" />

						<tr>
							<td height="20" width="15%" class="fieldname">
								<div align="right">Tea : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:textfield
									id="teafield" name="teafield" size="5" maxlength="2"
									theme="simple" align="center" value="%{teaQty}" /></td>

							<td height="20" width="15%" class="fieldname">
								<div align="right">Coffee : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:textfield
									id="coffeefield" name="coffeefield" size="5" maxlength="2"
									theme="simple" align="center" value="%{coffeeQty}" /></td>

							<td height="20" width="15%" class="fieldname">
								<div align="right">Bottle : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:textfield
									id="bottlefield" name="bottlefield" size="5" maxlength="2"
									theme="simple" align="center" value="%{bottleQty}" /></td>
						</tr>
						<tr height="5" />
						<tr>
							<td height="20" width="15%" class="fieldname">
								<div align="right">Cold-Drink : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:textfield
									id="colddrinkfield" name="colddrinkfield" size="5"
									maxlength="2" theme="simple" align="center"
									value="%{colddrinkQty}" />
							</td>
							<td height="20" width="15%" class="redFontTable"><div align="right"><b>Total Bev Bill: </b>&nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="redFontTable">&nbsp;&nbsp;<s:property
									value="totalBill" /></td>
							<td height="20" width="15%" class="fieldname">
								<div align="right">&nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp;</td>
						</tr>

						<tr height="15" />

						<tr>
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal"></td>
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal">
								<input type="button" value="<s:text name="button.updateBeverages" />" 
							 onclick="return(validate());" />
							</td>
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal"></td>
						</tr>
					</s:iterator>

					<tr height="25" />
					<tr>
						<td class="fieldname" style="text-align: center">Matches Details</td>
					</tr>
					<tr height="15" />
					<s:iterator value="customerMatchDetailsDTOsList">
								
						<tr>
							<td height="20" width="15%" class="fieldname">
								<div align="right">Match No : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:property
									value="matchNo" /></td>

							<td height="20" width="15%" class="fieldname">
								<div align="right">Frame Type : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:property
									value="frameType" />
							</td>
							<td height="20" width="15%" class="fieldname">
								<div align="right">Match Bill : &nbsp;&nbsp;</div>
							</td>
							<td height="20" width="10%" class="normal">&nbsp;&nbsp; <s:property
									value="custMatchTotalBill" />
							</td>
						</tr>
						</s:iterator>	
						<tr height="5" />
						 <s:hidden name="custEntryNo" value="%{custEntryNo}" />
						 <s:hidden name="customerID" value="%{customerID}" />
						<s:iterator value="customerBillDetailsDTOList">
						<tr>
							<td height="20" width="15%" class="redFontTable"></td>
							<td height="20" width="10%" class="redFontTable"></td>
							<td height="20" width="15%" class="redFontTable"><div align="right"><b>Total Match Bill :</b> &nbsp;&nbsp;</div> </td>
							<td height="20" width="10%" class="redFontTable">&nbsp;&nbsp;<s:property value="custMatchTotalBill" /> </td>
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal"></td>
						</tr>
						<tr height="15" />
						<tr>
							<td height="20" width="15%" class="greenFontTable"><div align="right"><b>Final Bill: </b></div></td>
							<td height="20" width="10%" class="fieldname">&nbsp;&nbsp;<s:property value="custTotalBill" /> </td>

							<td height="20" width="15%" class="greenFontTable"><div align="right"><b>Paid Bill :</b> &nbsp;&nbsp;</div> </td>
							<td height="20" width="10%" class="fieldname">&nbsp;&nbsp;<s:property value="custPaidBill" /> </td>

							<td height="20" width="15%" class="greenFontTable"><div align="right"><b>Remaining Balance: </b></div></td>
							<td height="20" width="10%" class="fieldname">&nbsp;&nbsp;<s:property value="custRemainingBalance" /> </td>
						</tr>
						
						<tr height="15" />
						<tr>
							<td height="20" width="15%" class="greenFontTable"></td>
							<td height="20" width="10%" class="greenFontTable">
							<s:textfield id="totalCustBill" name="totalCustBill" size="5" maxlength="4" theme="simple" align="center" 
								value="%{custRemainingBalance}" /></td>
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal">
							<s:if test='%{custRemainingBalance != 0}'>
							<input type="button" value="<s:text name="button.payBill" />" 
							 onclick="submitPayBill('<s:property value="custEntryNo" />');" />
							 </s:if>
							 </td>
							
							<td height="20" width="15%" class="fieldname"></td>
							<td height="20" width="10%" class="normal"></td>
						</tr>
					<tr height="35" />
					</s:iterator>
	</table>
</s:form>

<script>
    document.getElementById("logTimeTab").style.textDecoration = "underline";
	document.getElementById("logTimeTab").style.backgroundColor = "#9DB1ED";
</script>
