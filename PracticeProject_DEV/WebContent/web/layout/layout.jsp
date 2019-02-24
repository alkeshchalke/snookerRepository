<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>

<link rel="stylesheet" href="styles/app_styles_en.css" />

<script type="text/javascript" src="js/scripts.js"></script>

<link rel="stylesheet" href="js/jquery-ui.css" />

<style type="text/css">
html, body {
	overflow: auto;
}
</style>
</head>

<SCRIPT type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>

<body
	style="margin: 0; text: black; padding: 0; border: 0;" onload="noBack();"
    onpageshow="if (event.persisted) noBack();" onunload="">

	<table border="0" width="100%" height="600px" cellspacing="0"
		cellpadding="0" margin="0">
		<tr height="17%">
			<td><tiles:insertAttribute name="header" /></td>
		</tr>
		<tr height="2%">
			<td valign=top width="100%" bgcolor="#CEF6F5"><tiles:insertAttribute
					name="tabbedpanel" /></td>
		</tr>
		<tr height="0.5%">
			<td valign=top class="navbarbg" width="100%"><tiles:insertAttribute
					name="subpanel" /></td>
		</tr>
		<tr height="81%" width="100%">
			<!-- <td valign=top class="normal" width="100%" bgcolor="#BFF8AC" > -->
				<td valign=top class="normal" width="100%">
			<!--background="${pageContext.servletContext.contextPath}/images/watermark.jpg"
			style="background-size: contain; background-repeat:no-repeat; background-position: center;"> -->
			
				<table class="tableoutline" width="100%" height="99%">
					<tr>
						<td valign=top><tiles:insertAttribute name="body" /></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr height="10%">
			<td><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>
</html>

