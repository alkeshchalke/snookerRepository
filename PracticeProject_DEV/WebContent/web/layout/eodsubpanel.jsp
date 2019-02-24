<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.projectwork.constants.TestProjectConstantsIfc" %>

<style> 
.settingsPanelDiv {
    border: 1px solid;
    font-size: 11pt;
	padding: 1px;
    background: #F5C1AF;
    border-top-right-radius: 1em;
    border-top-left-radius: 1em;
    border-bottom-right-radius: 1em;
    border-bottom-left-radius: 1em;
}

a:hover {
	text-decoration: none;
	color: #1963a9;
	text-decoration: underline;
}
</style>

<table id="tabbedDiv" cellspacing="3" cellpadding="0" width="50%" align="right">
	<tr>
		
		<td id="todayReport" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="ViewTodayReportAction.action"/>"><s:text name="eodsubtab.todayreports.label" /></a>
		</td>
		<td id="datewiseReport" width="25%" align="center" class="settingsPanelDiv" class="settingsPanelDiv">
				<a href="<s:url value="ViewTablewiseReportAction.action"/>"><s:text name="eodsubtab.tablereports.label" /></a>
		</td>
		<td id="historyReport" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="ViewHistoricalReportAction.action"/>"><s:text name="eodsubtab.historyreports.label" /></a>
		</td>
		<td id="oldBusinessReport" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="PrepareOldBusinessReportScreenAction.action"/>"><s:text name="eodsubtab.oldBusinessreports.label" /></a>
		</td>
		
</tr>
</table>
<script>
setTabbedPanelWidth();
</script> 