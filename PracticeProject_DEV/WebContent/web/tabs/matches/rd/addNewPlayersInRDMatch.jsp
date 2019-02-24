
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
	function backButtonClicked() {
		callAction('MatchTabAction.action');
	}
</script>

<script type="text/javascript">
	function validate() {
		var selectedPlayersList = document.getElementById("selectedPlayersList").value;
		
		if (selectedPlayersList == "") {
			alert("You have not selected any new players. Going back to previous page.");
		}
		return true;
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/UpdateOngoingRDMatchAction.action" method="post"
	theme="simple" onsubmit="return(validate());">

	<table style="width: 100%">
		<tr>
			<td class="fieldname">Update existing RD Match</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%">
					<tr>
						<s:iterator value="addRDPlayerDTO">
							<td align="center" style="width: 100% border: 1">
								<table style="width: 100% border: 1 align: center">
									<tr height="15" />
									<tr>
										<td height="20" width="23%" class="fieldname">
											<div align="right" class="tab">Match No : &nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal"><s:property
												value="matchNo" /> <s:hidden name="matchNo"
												value="%{matchNo}" /></td>

										<td height="20" width="47%" class="fieldname"></td>

									</tr>
									<tr height="15" />
									<tr>

										<td height="20" width="23%" class="fieldname">
											<div align="right" class="tab">Business Date :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal"><s:property
												value="businessDate" /> <s:hidden name="businessDate"
												value="%{businessDate}" /></td>
										<td height="20" width="47%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="20" width="23%" class="fieldname">
											<div align="right" class="tab">Frame Type :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal"><s:property
												value="frameType" /> <s:hidden name="frameType"
												value="%{frameType}" /></td>
										<td height="20" width="47%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="20" width="23%" class="fieldname">
											<div align="right" class="tab">Table No. : &nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal"><s:property
												value="tableNo" /> <s:hidden name="tableNo"
												value="%{tableNo}" /></td>
										<td height="20" width="47%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="20" width="23%" class="fieldname">
											<div align="right" class="tab">Current Players :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal"><s:property
												value="playingCustomerID" /> <s:hidden
												name="playingCustomerID" value="%{playingCustomerID}" /></td>
										<td height="20" width="47%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="50" width="23%" class="fieldname">
											<div align="right" class="tab">Select New Players :
												&nbsp;&nbsp;</div>
										</td>
										<td height="50" width="30%" class="normal"><s:select
												list="selectPlayerList" headerKey="-1"
												name="selectedPlayersList" theme="simple"
												id="selectedPlayersList" multiple="true" size="7">
											</s:select></td>
										<td height="20" width="47%" class="fieldname">Press
											'CTRL' button and select players.</td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="50" width="23%" class="fieldname">
											<div align="right" class="tab">
												<input type="button" id="noButton"
													value="<s:text name="button.back" />"
													onclick="backButtonClicked()" /> &nbsp;&nbsp;
											</div>
										</td>
										<td height="50" width="30%" class="normal"><button
												type="submit">
												<s:text name="button.submit" />
											</button></td>
										<td height="20" width="47%" class="fieldname"></td>
									</tr>
									<tr height="5" />
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