
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<table style="width: 100%">
	<tr>
		<td class="fieldname" align="center">Show Table wise Business Report</td>
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
					<td align="center">
						<table>
						<tr height="40"/>
							<tr>
								<td height="40" width="10%" />
								<td height="40" width="30%" class="tableheadrow">
									<div align="center" class="tab">Table Name</div>
								</td>
								<td height="40" width="30%" class="tableheadrow">
									<div align="center" class="tab">Total Business</div>
								</td>
								<td height="40" width="10%" />
							</tr>

							<s:iterator value="tablewiseBusinessDayInformationDTO">
								<tr>
									<td height="40" width="10%" />
									<td height="40" width="30%" class="normal" align="center"
										style="border: 1px solid #ccc;"><s:property
											value="tableNo" /></td>

									<td height="40" width="30%" class="normal" align="center"
										style="border: 1px solid #ccc;"><s:property
											value="paymentAmount" /></td>
									<td height="40" width="10%" />
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

<script>
	document.getElementById("eodTab").style.textDecoration = "underline";
	document.getElementById("eodTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("datewiseReport").style.backgroundColor = "#A9A9F5";
</script>
