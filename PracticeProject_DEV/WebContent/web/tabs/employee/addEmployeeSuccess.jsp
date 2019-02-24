<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<br />
<br />
<br />

<center>
	<font color="blue">Employee Added successfully..</font>
</center>
<br />
<br />
<br />
<br />

<s:form action="Employee.action" method="post" theme="simple">
<center>
<h3>Click on this button to go back to previous page..</h3>
</center>
<br />
<br />
<br />
<br />
<center>
<s:submit method="execute" align="center" />
</center>
</s:form>
<script>
    document.getElementById("employeeTab").style.textDecoration = "underline";
	document.getElementById("employeeTab").style.backgroundColor = "#9DB1ED";
</script>