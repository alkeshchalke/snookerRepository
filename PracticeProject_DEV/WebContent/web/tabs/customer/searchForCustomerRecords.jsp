
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=false" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.3.0.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.3.1.min.js"></script>
<link rel="stylesheet" href="/styles/jquery.mobile-1.3.0.css">
<link rel="stylesheet" href="/styles/jquery.mobile-1.3.1.min.css">
<link rel="stylesheet" href="/styles/jquery-ui-1.10.4.css">

<script type="text/javascript">
	function validate() {
		/* var frames = document.companyForm.frames.value;

		if (frames == "") {
			alert("Please Enter Frames");
			return false;
		} */

		return (true);
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>
<s:form action="/ViewSelectedCustomerRecords.action" method="post"
	theme="simple" name="searchCustomerForm" onsubmit="return(validate());">

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
					<tr height="65" />
					<tr>
						<td height="20" width="40%"/>
						<td height="20" width="10%"><s:select
								list="customerList" headerKey="-1" headerValue="Customer Name"
								name="customerID" theme="simple"></s:select></td>

						<td height="20" width="10%" class="normal" align="center">
							<button type="submit">
								<s:text name="button.search" />
							</button>
						</td>
						<td height="20" width="40%"/>
					</tr>
					<tr height="65" />			
				</table>
			</td>
		</tr>
		<tr height="65" />
	</table>
</s:form>

<script>
    document.getElementById("customerTab").style.textDecoration = "underline";
	document.getElementById("customerTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("viewCustomerTab").style.backgroundColor = "#A9A9F5";
</script>
