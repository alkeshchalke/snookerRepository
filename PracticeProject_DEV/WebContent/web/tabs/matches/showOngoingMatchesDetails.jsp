
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
	function optionsToAddPlayerInOngoingRDMatch(matchNo) {
		var newAction = 'OptionsToAddPlayerInOngoingRDMatchAction.action?matchNo='
				+ matchNo;
		callAction(newAction);
	}
</script>

<script>
	function setMatchStatusFinished(matchNo) {
		var newAction = 'FinishMatchAction.action?matchNo=' + matchNo;
		callAction(newAction);
	}
</script>

<script>
	function setMatchStatusCancelled(matchNo) {
		var newAction = 'CancelMatchAction.action?matchNo=' + matchNo;
		callAction(newAction);
	}
</script>

<script>
	function submitNewForm(matchNo, businessDate) {
		var selectedFrameType = document.getElementById("selectedFrameType").value;
		if (selectedFrameType == "-1") {
			alert("Please Select Frame Type");
			return false;
		}

		var selectedTableNo = document.getElementById("selectedTableNo").value;
		if (selectedTableNo == "-1") {
			alert("Please Select Table Number");
			return false;
		}

		var newAction = 'ShowMatchOptionsFromSelectedFrameAction.action?matchNo='
				+ matchNo + '&businessDate=' + businessDate;
		callAction(newAction);
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>

<s:if test="hasActionMessages()">
	<div class="welcome">
		<s:actionmessage />
	</div>
</s:if>

<s:form action="/FinishMatchAction.action" method="post" theme="simple"
	name="currentMatchForm">

	<table width="100%">
		<tr>
			<td width="100%" class="fieldname">Ongoing Matches</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%" class="tableoutline">
					<tr>
						<td height="30" width="5%" class="tableheadrow">
							<div align="center" class="tab">Match No.</div>
						</td>

						<td height="30" width="5%" class="tableheadrow">
							<div align="center" class="tab">Table No.</div>
						</td>

						<td height="30" width="5%" class="tableheadrow">
							<div align="center" class="tab">Frame Type</div>
						</td>

						<td height="30" width="15%" class="tableheadrow">
							<div align="center" class="tab">Players</div>
						</td>
						<td height="30" width="7%" class="tableheadrow">
							<div align="center" class="tab">Frame Start Time</div>
						</td>
						<td height="30" width="7%" class="tableheadrow">
							<div align="center" class="tab">Frame End Time</div>
						</td>

						<td height="30" width="4%" class="tableheadrow">
							<div align="center" class="tab">Payee</div>
						</td>

						<td height="30" width="4%" class="tableheadrow">
							<div align="center" class="tab">Amount</div>
						</td>

						<td height="30" width="5%" class="tableheadrow">
							<div align="center" class="tab">Match Status</div>
						</td>

						<td height="30" width="4%" class="tableheadrow">
							<div align="center" class="tab">Action</div>
						</td>
					</tr>

					<s:iterator value="ongoingMatchesDTOs" var="user" status="id">
						<tr height="40">
							<td height="30" width="5%" class="normal" align="center"><s:property
									value="matchNo" /> <s:hidden name="matchNo" value="%{matchNo}" />
							</td>

							<td height="30" width="5%" class="normal" align="center"><s:property
									value="tableNo" /> <s:hidden name="tableNo" value="%{tableNo}" />
							</td>

							<td height="30" width="5%" class="normal" align="center"><s:property
									value="frameType" /> <s:hidden name="frameType"
									value="%{frameType}" /></td>

							<td height="30" width="15%" class="normal" align="center"><s:property
									value="playingCustomerID" /> <s:if
									test="%{frameType == 'RD' && matchStatus == 'Ongoing'}">
									<input type="button" value="<s:text name="button.add" />"
										onclick="optionsToAddPlayerInOngoingRDMatch('<s:property value="matchNo" />')" />
								</s:if></td>

							<td height="30" width="7%" class="normal" align="center"><s:property
									value="frameStartTime" /></td>

							<td height="30" width="7%" class="normal" align="center"><s:property
									value="frameEndTime" /> <s:if
									test="%{matchStatus == 'Ongoing'}">
									<input type="button"
										value="<s:text name="button.cancelMatch" />"
										onclick="setMatchStatusCancelled('<s:property value="matchNo" />')" />
								</s:if></td>

							<td height="30" width="4%" class="normal" align="center"><s:if
									test="%{payingPlayer != null}">
									<s:property value="payingPlayer" />
								</s:if></td>

							<td height="30" width="4%" class="normal" align="center"><s:property
									value="paymentAmount" /></td>

							<td height="30" width="5%" class="normal" align="center"><s:property
									value="matchStatus" /></td>

							<td height="30" width="4%" class="normal" align="center"><s:if
									test="%{matchStatus == 'Ongoing'}">
									<input type="button"
										value="<s:text name="button.finishMatch" />"
										onclick="setMatchStatusFinished('<s:property value="matchNo" />')" />
								</s:if></td>
						</tr>
					</s:iterator>
					<s:iterator value="newMatchDetailsDTOs">
						<tr>
							<td height="30" width="5%" class="normal" align="center"><s:property
									value="matchNo" /></td>

							<td height="30" width="5%" class="normal" align="center"><s:select
									list="selectTableList" headerKey="-1" headerValue="Table No"
									id="selectedTableNo" name="selectedTableNo" theme="simple"></s:select></td>

							<td height="30" width="5%" class="normal" align="center"><s:select
									list="selectFramesList" headerKey="-1" headerValue="Frame Type"
									id="selectedFrameType" name="selectedFrameType" theme="simple"></s:select>
							</td>

							<td height="30" width="15%" class="normal" align="center"><s:select
									list="currentlyNonPlayingCustomerList" id="custList"
									headerKey="-1" headerValue="Available Players" theme="simple"></s:select>
							</td>

							<td height="30" width="7%" class="normal" align="center">
							<s:if
									test="%{#session.EODStatusIncorrect != 'true'}">
									<input type="button" value="<s:text name="button.add" />"
										onclick="submitNewForm('<s:property value="matchNo" />', '<s:property value="businessDate" />')" />
								</s:if>
							
							</td>
							<td height="30" width="7%" class="normal" align="center"></td>

							<td height="30" width="4%" class="normal" align="center"></td>

							<td height="30" width="4%" class="normal" align="center"></td>

							<td height="30" width="5%" class="normal" align="center"></td>

							<td height="30" width="4%" class="normal" align="center"></td>
						</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</table>
</s:form>

<script>
	document.getElementById("matchTab").style.textDecoration = "underline";
	document.getElementById("matchTab").style.backgroundColor = "#9DB1ED";
</script>
