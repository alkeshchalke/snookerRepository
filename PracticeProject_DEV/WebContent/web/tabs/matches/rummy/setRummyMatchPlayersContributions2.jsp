
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	function validate() {
		if (selectedPlayersList == "") {
			alert("You have not selected any new players. Going back to previous page.");
		}
		return true;
	}
</script>

<script>
	function cancelButtonClicked() {
		var newAction = 'MatchTabAction.action';
		callAction(newAction);
	}
</script>

<script>
	function markThePlayer() {
		var currentDropdownIndex = '<%=session.getAttribute("currentDropdownIndex")%>';
		var playerListSizeNum = parseInt(currentDropdownIndex);
		var playertoUpdate = "";

		if (playerListSizeNum == 0) {
			var markedPlayer1 = document.getElementById("markedPlayer1").value;
			if (markedPlayer1 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer1;
		}
		
		if (playerListSizeNum == 1 ) {
			var markedPlayer2 = document.getElementById("markedPlayer2").value;
			if (markedPlayer2 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer2;
		}

		if (playerListSizeNum == 2) {
			var markedPlayer3 = document.getElementById("markedPlayer3").value;
			if (markedPlayer3 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer3;
		}
		
		if (playerListSizeNum == 3) {
			var markedPlayer4 = document.getElementById("markedPlayer4").value;
			if (markedPlayer4 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer4;
		}
		
		if (playerListSizeNum == 4) {
			var markedPlayer5 = document.getElementById("markedPlayer5").value;
			if (markedPlayer5 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer5;
		}
		
		if (playerListSizeNum == 5) {
			var markedPlayer6 = document.getElementById("markedPlayer6").value;
			if (markedPlayer6 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer6;
		}
		
		if (playerListSizeNum == 6 ) {
			var markedPlayer7 = document.getElementById("markedPlayer7").value;
			if (markedPlayer7 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer7;
		}

		if (playerListSizeNum == 7) {
			var markedPlayer8 = document.getElementById("markedPlayer8").value;
			if (markedPlayer8 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer8;
		}
		
		if (playerListSizeNum == 8) {
			var markedPlayer9 = document.getElementById("markedPlayer9").value;
			if (markedPlayer9 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer9;
		}
		
		if (playerListSizeNum == 9) {
			var markedPlayer10 = document.getElementById("markedPlayer10").value;
			if (markedPlayer10 == "-1") {
				return false;
			}
			playertoUpdate=markedPlayer10;
		}
		
		callAction('DistributeAmountInLoosingPlayersAction.action?markedPlayer='+playertoUpdate);
	}
</script>

<%
Integer i = (Integer)request.getSession().getAttribute("currentDropdownIndex");
Integer totalPlayers = (Integer)request.getSession().getAttribute("playersToPayCount");
%>
<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/UpdateRummyFrameCompletionDetailsAction.action"
	method="post" theme="simple" onsubmit="return(validate());">

	<table style="width: 100%">
		<tr>
			<td class="fieldname">Rummy Match Finish Details</td>
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
									<tr height="20" />
									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Match No : &nbsp;&nbsp;</div>
											<s:hidden name="matchNo" value="%{matchNo}" />
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
									<tr height="10" />

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
									<tr height="10" />

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
									<tr height="10" />

									<tr>
										<td height="20" width="20%" class="tableheadrow">
											<!-- <div align="right" class="tab">Winning Players :
												&nbsp;&nbsp;</div> -->
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp; 
										<%-- <s:property value="payingPlayer" /> --%>
												</td>
										<td height="20" width="20%" class="tableheadrow">
											<div align="right" class="tab">Total Amount :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp; <s:property
												value="paymentAmount" /> <s:hidden name="paymentAmount"
												value="%{paymentAmount}" />
										</td>
									</tr>
									<tr height="10" />

									<tr>
										<td height="20" width="20%" colspan="4" class="tableheadrow">
											<div align="center" class="tab">Loosing Player's Contribution:
												&nbsp;&nbsp;</div>
										</td>
									</tr>
									<tr height="10" />
									<tr>
										<td height="20" width="20%">
											<div align="right" class="tab">
												<span style='font-family: Arial;'>&#8377;</span>&nbsp;<s:property value="loosingPlayer1Amount" />
											</div>
										</td>
										<td height="20" width="30%" class="normal">&nbsp;&nbsp; <s:property
												value="loosingPlayer1" />
										</td>
										<td height="20" width="20%">
											<% if(i == 0){%> <s:select list="loosingPlayersList"
												headerKey="-1" headerValue="Select a Player"
												name="markedPlayer1" theme="simple" id="markedPlayer1">
												<s:hidden name="markedPlayer1" value="%{markedPlayer1}" />
											</s:select>
											<%}%>
										</td>
										<td height="20" width="30%" class="normal">
											<% if(i == 0){%> <input type="button" id="confirmButton"
											value="<s:text name="button.confirm" />"
											onclick="markThePlayer()" /> <%}%>
										</td>
									</tr>

									<s:if test="%{loosingPlayer2Amount != 0}">
										<tr>
											<td height="20" width="20%">
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer2Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer2" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 1){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer2" theme="simple" id="markedPlayer2">
												</s:select> <%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 1){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer3Amount != 0}">
										<tr>
											<td height="20" width="20%">
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer3Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer3" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 2){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer3" theme="simple" id="markedPlayer3">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 2){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>

									</s:if>

									<s:if test="%{loosingPlayer4Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer4Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer4" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 3){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer4" theme="simple" id="markedPlayer4">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 3){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer5Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer5Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer5" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 4){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer5" theme="simple" id="markedPlayer5">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 4){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer6Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer6Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer6" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 5){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer6" theme="simple" id="markedPlayer6">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 5){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer7Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer7Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer7" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 6){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer7" theme="simple" id="markedPlayer7">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 6){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer8Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer8Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer8" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 7){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer8" theme="simple" id="markedPlayer8">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 7){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>

									</s:if>

									<s:if test="%{loosingPlayer9Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer9Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer9" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 8){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer9" theme="simple" id="markedPlayer9">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 8){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>

									<s:if test="%{loosingPlayer10Amount != 0}">
										<tr>
											<td height="20" width="20%" >
												<div align="right" class="tab">
													<span style='font-family: Arial;'>&#8377;</span>&nbsp;
													<s:property value="loosingPlayer10Amount" />
												</div>
											</td>
											<td height="20" width="30%" class="normal">&nbsp;&nbsp;
												<s:property value="loosingPlayer10" />
											</td>
											<td height="20" width="20%" >
												<% if(i == 9){%> <s:select list="loosingPlayersList"
													headerKey="-1" headerValue="Select a Player"
													name="markedPlayer10" theme="simple" id="markedPlayer10">
												</s:select>
												<%}%>
											</td>
											<td height="20" width="30%" class="normal">
												<% if(i == 9){%> <input type="button" id="confirmButton"
												value="<s:text name="button.confirm" />"
												onclick="markThePlayer()" /> <%}%>
											</td>
										</tr>
									</s:if>
									<tr height="20" />

									<tr>
										<td colspan="2" height="20" width="50%" class="normal"
											align="right">
											<input type="button" id="cancelButton"
												value="<s:text name="button.cancelMatch" />"
												onclick="cancelButtonClicked()" /> 
										</td>
										<td colspan="2" height="20" width="50%" class="normal"
											align="left"><% if(i == totalPlayers){%>
											<button type="submit">
												<s:text name="button.submit" />
											</button> <%}%>
										</td>
									</tr>
									<tr height="10" />
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