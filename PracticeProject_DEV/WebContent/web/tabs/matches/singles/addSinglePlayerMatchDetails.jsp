
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	function validate() {
		var player1 = document.getElementById("player1").value;
		var player2 = document.getElementById("player2").value;
		
		if (player1 == -1) {
			alert("Select First Player.");
			return false;
		}
		
		if (player2 == -1) {
			alert("Select Second Player.");
			return false;
		}

		return (true);
	}
</script>

<script>
	function updateNextDropdown(value) {
		var select1 = document.getElementById("player1");
		var select2 = document.getElementById("player2");
		
		for (i = 0; i < select2.length; i++) {
			if (select2.options[i].value == value) {
				select2.remove(i);
			}
		}
	}
</script>
<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/AddNewSinglesMatchAction.action" method="post" theme="simple"
	name="companyForm" onsubmit="return(validate());">

<table style="width: 100%">
		<tr>
			<td class="fieldname">Add New Singles Match Details</td>
		</tr>
		<tr bgcolor="#CCCCCC">
			<td></td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%">
					<tr>
						<s:iterator value="newMatchDetailsDTOs">
							<td align="center" style="width: 100% border: 1" >
								<table style="width: 100% border: 1 align: center">
									<tr height="15" />
									<tr>
										<td height="20" width="33%" class="fieldname">
											<div align="right" class="tab">Match No : &nbsp;&nbsp;</div>
										</td>
										<td height="20" width="40%" class="normal">
										<s:property value="matchNo" /> <s:hidden name="matchNo" value="%{matchNo}" /></td>
										
										<td height="20" width="27%" class="fieldname"></td>
										
										</tr> <tr height="15" /> <tr>
										
										<td height="20" width="33%" class="fieldname">
											<div align="right" class="tab">Business Date :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="40%" class="normal">
										<s:property value="businessDate" /> <s:hidden name="businessDate" value="%{businessDate}" /></td>
										<td height="20" width="27%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td height="20" width="33%" class="fieldname">
											<div align="right" class="tab">Frame Type :
												&nbsp;&nbsp;</div>
										</td>
										<td height="20" width="40%" class="normal">
										<s:property value="selectedFrameType" /> <s:hidden name="frameType" value="%{selectedFrameType}" />	</td>
										<td height="20" width="27%" class="fieldname"></td>
									</tr> 
									<tr height="15" /> 
									
									<tr>
									<td height="20" width="33%" class="fieldname">
											<div align="right" class="tab">Table No. : &nbsp;&nbsp;</div>
										</td>
										<td height="20" width="40%" class="normal">
										<s:property value="selectedTableNo" /> <s:hidden name="tableNo" value="%{selectedTableNo}" /></td>
										<td height="20" width="27%" class="fieldname"></td>
									</tr>
									<tr height="15" />
									
									<tr>
										<td height="50" width="33%" class="fieldname">
											<div align="right" class="tab">Select Player 1 :
												&nbsp;&nbsp;</div>
										</td>
										<td height="50" width="40%" class="normal"><s:select list="selectPlayerList" headerKey="-1"
												headerValue="Select Player 1" name="player1" theme="simple"
												id="player1" onchange="updateNextDropdown(this.value)"></s:select></td>
										<td height="20" width="27%" class="fieldname"></td>
									</tr>
									<tr height="15" />
									
									<tr>
										<td height="50" width="33%" class="fieldname">
											<div align="right" class="tab">Select Player 2 :
												&nbsp;&nbsp;</div>
										</td>
										<td height="50" width="40%" class="normal"><s:select list="selectPlayerList" headerKey="-1"
												headerValue="Select Player 2" name="player2" theme="simple"
												id="player2"></s:select></td>
										<td height="20" width="27%" class="fieldname"></td>
									</tr>
									<tr height="15" />

									<tr>
										<td colspan="2" height="20" width="53%" class="normal"
											align="center">
											<button type="submit"><s:text name="button.submit" /></button>
										</td>
										<td height="20" width="27%" class="fieldname"></td>
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