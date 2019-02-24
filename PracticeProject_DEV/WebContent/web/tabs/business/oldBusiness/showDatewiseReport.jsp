
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
	function submitTheForm() {
		var newAction = 'ExportOldBusinessReportAction.action';
		callAction(newAction);
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/ExportOldBusinessReportAction.action" method="post"
	theme="simple" name="companyForm">

	<table style="width: 100%">
		<tr>
			<td class="fieldname" align="center">Show Old Business Transactions Report</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%">
					<tr>
						<td align="center"></td>
					</tr>
					<tr>
						<td>
							<table
								style="width: 98% border: 1 frame: HSIDES rules: ROWS bordercolorlight: BLACK bordercolor: #CCCCCC cellspacing: 1 align: center">

								<tr>
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Business Date</div>
									</td>
									
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Customer Name</div>
									</td>
									
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Entry Number</div>
									</td>
									<!-- <td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">In-Time</div>
									</td>
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Out-Time</div>
									</td> -->
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
									<td width="10%"></td>
								</tr>

								<s:iterator value="oldBusinessDayTransactionRecords">
									<tr>
										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="businessDate" /></td>
												
										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="customerName" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custEntryNo" /></td>
												
										<%-- <td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="inTime" /></td>
												
										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="outTime" /></td> --%>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custMatchesTotalBill" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custBeveragesTotalBill" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custTotalBill" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custPaidBill" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><s:property
												value="custRemainingBill" /></td>
										<td width="10%"></td>
									</tr>
								</s:iterator>
								<tr height="25" />
								<tr>
									<td width="20%" align="center"></td>
									<td width="10%" align="center"></td>
									<td width="10%" align="center"></td>
									
									<td width="10%" align="center"><input type="button"
										value="<s:text name="button.export.pdf" />"
										onclick="submitTheForm();" /></td>
										
									<td width="10%" align="center"></td>
										
									<td width="10%" align="center"></td>
									<td width="10%" align="center"></td>
									<td width="20%" align="center"></td>
								</tr>

								<tr height=25 />

								 <s:iterator value="oldBusinessDayInformationDTO">
									<tr>
										<td width="20%"></td>
										<td width="20%" align="center"></td>
										<td height="20" width="10%" class="fieldname" align="center"
											style="border: 1px solid #ccc;">Total Bill:</td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><span
											style='font-family: Arial;'>&#8377;</span> <s:property
												value="incomeFromMatches" /> <s:hidden
												name="incomeFromMatches" value="%{incomeFromMatches}" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><span
											style='font-family: Arial;'>&#8377;</span> <s:property
												value="incomeFromBeverages" /> <s:hidden
												name="incomeFromBeverages" value="%{incomeFromBeverages}" />
										</td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><span
											style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalBusiness" /> <s:hidden name="totalBusiness"
												value="%{totalBusiness}" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><span
											style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalMoneyPaid" /> <s:hidden name="totalMoneyPaid"
												value="%{totalMoneyPaid}" /></td>

										<td height="20" width="10%" class="normal" align="center"
											style="border: 1px solid #ccc;"><span
											style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalBalanceAmount" /> <s:hidden
												name="totalBalanceAmount" value="%{totalBalanceAmount}" />
										</td>
										
									</tr>
								</s:iterator> 

							</table>
							<p>&nbsp;</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>

<script>
	document.getElementById("eodTab").style.textDecoration = "underline";
	document.getElementById("eodTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("oldBusinessReport").style.backgroundColor = "#A9A9F5";
</script>

