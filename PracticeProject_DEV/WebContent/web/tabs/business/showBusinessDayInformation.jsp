
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/EndOfDayConfirmationPromptAction.action" method="post"
	theme="simple" name="endOfDayForm">

	<table style="width: 100%">
		<tr>
			<td class="fieldname">Business Day Information</td>
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

								<tr style="border: 1">
									<td height="20" width="8%" class="tableheadrow">
										<div align="center" class="tab">Business Date</div>
									</td>
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Income From Matches</div>
									</td>
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Income From Beverages</div>
									</td>
									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Total Business</div>
									</td>

									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Total Money Paid</div>
									</td>

									<td height="20" width="10%" class="tableheadrow">
										<div align="center" class="tab">Total Balance Money</div>
									</td>

								</tr>
	
								<s:iterator value="businessDayInformationDTO">
									<tr>
										<td height="20" width="8%" class="normal" align="center">
											<s:property value="businessDate" />
										</td>

										<td height="20" width="10%" class="normal" align="center">
											<span style='font-family: Arial;'>&#8377;</span> <s:property
												value="incomeFromMatches" />
										</td>

										<td height="20" width="10%" class="normal" align="center">
											<span style='font-family: Arial;'>&#8377;</span> <s:property
												value="incomeFromBeverages" />
										</td>

										<td height="20" width="10%" class="normal" align="center">
											<span style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalBusiness" />
										</td>

										<td height="20" width="10%" class="normal" align="center">
											<span style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalMoneyPaid" />
										</td>

										<td height="20" width="10%" class="normal" align="center">
											<span style='font-family: Arial;'>&#8377;</span> <s:property
												value="totalBalanceAmount" />
										</td>
									</tr>
									<tr height="35" />
									<tr>
										<td align="center" colspan=10>
										<button type="submit">
												<s:text name="button.eod" />
											</button></td>
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