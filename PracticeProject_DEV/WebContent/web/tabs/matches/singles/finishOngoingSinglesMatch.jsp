
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	function validate() {
		var payingPlayer = document.getElementById("payingPlayer").value;
		if (payingPlayer == -1) {
			alert("Select the Paying player");
			return false;
		}
		return true;
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/UpdateSinglesFrameCompletionDetailsAction.action"
	method="post" theme="simple" onsubmit="return(validate());">

	<table style="width: 100%">
		<tr>
			<td class="fieldname">Singles Match Finish Details</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%">
					<tr>
						<s:iterator value="finishMatchDTO">
							<td align="center" style="width: 100%">
								<table style="width: 100% border: 1 align: center">
									<tr height="5" />
									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Match No : &nbsp;&nbsp;</div><s:hidden name="matchNo"
												value="%{matchNo}" />
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="matchNo" /></td>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Business Date :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="businessDate" /></td>
									</tr>
									<tr height="5" />

									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Frame Type :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="frameType" /></td>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Table No. : &nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="tableNo" /></td>
									</tr>
									<tr height="5" />

									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Frame Start Time :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="frameStartTime" /></td>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Frame End Time :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;<s:property
												value="frameEndTime" /></td>
									</tr>
									<tr height="5" />


									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Losing Player :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;
										<s:select id="payingPlayer" name="payingPlayer" headerKey="-1" headerValue="Paying Player"
													list="selectPayeeList" theme="simple" /></td>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Total Amount :
												&nbsp;&nbsp;</div>
										</td><%-- <s:hidden name="paymentAmount" value="%{payShare1}" /> --%>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp;
										<s:textfield
												styleClass="data" name="paymentAmount" size="4" maxLength="4"
												value="%{paymentAmount}" />
										</td>
									</tr>
									
									<tr height="35" />

									<tr>
										<td colspan="4" height="20" width="100%" class="normal"
											align="center">
											<button type="submit">
												<s:text name="button.submit" />
											</button>
										</td>
									</tr>
									<tr height="5" />

									<%-- 	<tr>
										<td height="20" width="100%" class="normal" align="center">
											<button type="submit">
												<s:text name="button.submit" />
											</button>
										</td>
									</tr> --%>
								</table>
								<p>&nbsp;</p>
							</td>
						</s:iterator>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>

<script>
    document.getElementById("matchTab").style.textDecoration = "underline";
	document.getElementById("matchTab").style.backgroundColor = "#9DB1ED";
</script>