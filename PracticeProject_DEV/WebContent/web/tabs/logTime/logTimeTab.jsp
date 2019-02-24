
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	function validate() {
		var custName = document.getElementById("custName").value;
		if (custName == -1) {
			alert("Please Select a Customer from drop down..");
			return false;
		}  
		var newAction = 'LogTimeTabAddRecordAction.action';
		callAction(newAction);
		return (true);
	}
</script>

<script>
	function setOutTime(custEntryNo, customerID) {
		document.getElementById('otButton').style.visibility = 'hidden';
		var newAction = 'UpdateBusinessCustomerAction.action?custEntryNo=' + custEntryNo
				+ '&customerID=' + customerID;
		callAction(newAction);
	}
</script>
<script>
	function submitPayBill(custEntryNo, customerID, totalBill) {
			 var newAction = 'PayCustomerCurrentBillAction.action?custEntryNo=' + custEntryNo
					+ '&customerID=' + customerID + '&totalCustBill=' + totalBill;
		callAction(newAction); 
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/LogTimeTabAddRecordAction.action" method="post"
	theme="simple" name="companyForm" onsubmit="return(validate());">

	<table width="100%">
		<tr>
			<td width="100%" class="fieldname">List of Customers Logged in</td>
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
							<div align="center" class="tab">Customer Entry No.</div>
						</td>

						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Customer Name</div>
						</td>
						<td height="20" width="15%" class="tableheadrow">
							<div align="center" class="tab">In-Time</div>
						</td>
						<td height="20" width="15%" class="tableheadrow">
							<div align="center" class="tab">Out-Time</div>
						</td>

						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab">Payment Status</div>
						</td>

						<td height="20" width="5%" class="tableheadrow">
							<div align="center" class="tab">Total Bill</div>
						</td>

						<td height="20" width="10%" class="tableheadrow">
							<div align="center" class="tab"></div>
						</td>
					</tr>

					<s:iterator value="businessDayUsersDTOs" var="user">
						<tr>
							<td height="20" width="10%" class="normal" align="center">
							<%-- <s:if test="%{outTime != null}"> --%>
									<s:property value="custEntryNo" />
								<%-- </s:if> <s:else>
									<s:url action="ViewLogTimeCustomerDetailsAction.action"
										var="urlTag" escapeAmp="false">
										<s:param name="custEntryNo">
											<s:property value="custEntryNo" />
										</s:param>
										<s:param name="customerID">
											<s:property value="customerID" />
										</s:param>
									</s:url>
									<a href="<s:property value="#urlTag" />"><s:property
											value="custEntryNo" /></a>
								</s:else> --%>
								</td>

							<td height="20" width="10%" class="normal" align="center">
							<s:if test="%{outTime != null}">
							<s:property value="custName" /></s:if> <s:else>
									<s:url action="ViewLogTimeCustomerDetailsAction.action"
										var="urlTag" escapeAmp="false">
										<s:param name="custEntryNo">
											<s:property value="custEntryNo" />
										</s:param>
										<s:param name="custName">
											<s:property value="custName" />
										</s:param>
									</s:url>
									<a href="<s:property value="#urlTag" />"><s:property value="custName" /></a>
								</s:else></td>
							<td height="20" width="15%" class="normal" align="center"><s:property
									value="inTime" /></td>

							<td height="20" width="15%" class="normal" align="center"><s:if
									test="%{outTime != null}">
									<s:property value="outTime" />
								</s:if> <s:else>
									<input type="button" id="otButton"
										value="<s:text name="button.setEndTime" />"
										onclick="setOutTime('<s:property value="custEntryNo"/>',
													'<s:property value="customerID" />');" />
								</s:else></td>

							<td height="20" width="5%" class="normal" align="center"><s:if
									test="%{totalBill != 0}">
									<s:property value="paidStatus" />
								</s:if></td>

							<td height="20" width="10%" class="normal" align="center"><s:property
									value="totalBill" /></td>

							<td height="20" width="10%" class="normal" align="center">
							<s:if test="%{#session.EODStatusIncorrect != 'true'}">
									<s:if test='%{paidStatus == "Not Paid" && totalBill != 0}'>
										<input type="button" value="<s:text name="button.payBill" />"
											onclick="submitPayBill('<s:property value="custEntryNo" />', '<s:property value="customerID" />' ,
												'<s:property value="totalBill" />' )" />
									</s:if>
								</s:if></td>
						</tr>
					</s:iterator>
					<s:if test="%{#session.EODStatusIncorrect != 'true'}">
						<s:iterator value="newUserLogTimeDTOs">
							<tr>
								<td height="20" width="10%" class="normal" align="center">
									<s:property value="custEntryNo" /> <s:hidden
										name="custEntryNo" value="%{custEntryNo}" />
									<s:hidden name="businessDate" value="%{businessDate}" />
								</td>

								<td height="20" width="10%" class="normal" align="center">
									<s:select list="customerList" headerKey="-1"
										headerValue="Customer Name" id="custName" name="custName"
										theme="simple"></s:select> 
								</td>

								<td height="20" width="15%" class="normal" align="center">
								<input type="button" value="<s:text name="button.add" />"
									onclick="return(validate());" />
								</td>

								<td height="20" width="15%" class="normal" align="center">
								</td>

								<td height="20" width="10%" class="normal" align="center">
								</td>

								<td height="20" width="5%" class="normal" align="center"></td>
								<td height="20" width="10%" class="normal" align="center">
								</td>
							</tr>
						</s:iterator>
					</s:if>
				</table>
			</td>
		</tr>
	</table>
</s:form>

<script>
    document.getElementById("logTimeTab").style.textDecoration = "underline";
	document.getElementById("logTimeTab").style.backgroundColor = "#9DB1ED";
</script>
