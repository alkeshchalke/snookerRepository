<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>

<head>
<sx:head />
</head>
<script>
	$(function() {
		$("#ShowOldBusinessDateReportAction_businessDate").datepicker({
			dateFormat : '<s:text name="jquery.dateFormat" />'
		});
	});
</script>

<script type="text/javascript">
	function validate() {
		var startDate = document.datewiseBusinessForm.businessDate.value;

		if (startDate == "") {
			alert("Please Enter Business Date");
			return false;
		}

		return (true);
	}
</script>

<center>
	<font color="red"><s:actionerror /></font>
</center>

<s:form action="/ShowOldBusinessDateReportAction.action" method="post"
	theme="simple" onsubmit="return(validate());"
	name="datewiseBusinessForm">

	<table width="100%" border="0" cellspacing="3" cellpadding="3"
		align="center" height="100%">
		<tr height="35" />

	 	<tr/>

		<tr>
			<td width="50%" align="right" class="fieldname"><s:text
					name="businessDate.label" />:</td>
			<td width="50%"><s:textfield styleClass="data" name="businessDate"
					size="10" maxLength="18" tabindex="5" /></td>
		</tr>
		<tr height="35" />
		<tr/>
		<tr height="35" />

		<tr>
			<td colspan="4" align="center"><button type="submit">
					<s:text name="button.search" />
				</button></td>
		</tr>
		<tr height="15" />
	</table>
</s:form>

<script>
    document.getElementById("eodTab").style.textDecoration = "underline";
	document.getElementById("eodTab").style.backgroundColor = "#9DB1ED";
	document.getElementById("oldBusinessReport").style.backgroundColor = "#A9A9F5";
</script>
