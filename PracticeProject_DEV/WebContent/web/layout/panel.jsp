<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<style> 
.panelDiv {
    border: 1px solid;
    padding: 1px;
    background: #F5C2C2;
    border-top-right-radius: 1em;
    border-top-left-radius: 1em;
}

a:hover {
	text-decoration: none;
	color: #1963a9;
	text-decoration: underline;
}
</style>

<table cellspacing="3" cellpadding="0" width="100%">
	<tr>
		<td id="logTimeTab" width="10%" align="center" class="panelDiv"><div>
			<a href="<s:url value="/LogTimeTabAction.action"/>"><s:text name="logTimeTab.label" /></a></div>
		</td>
		
		<td id="matchTab" width="10%" align="center" class="panelDiv"><div>
			<a href="<s:url value="/MatchTabAction.action"/>"><s:text name="matchTab.label" /></a></div>
		</td>
		
		<td id="customerTab" width="10%" align="center" class="panelDiv"><div>
			<a href="<s:url value="/Customer.action"/>"><s:text name="customerTab.label" /></a></div>
		</td>
		
		<td id="employeeTab" width="10%" align="center" class="panelDiv"><div>
			<a href="<s:url value="/Employee.action"/>"><s:text name="employeeTab.label" /></a></div>
		</td>
		
		<td width="50%" align="center"><div>
			</div>
		</td>
		
		<td id="eodTab" width="10%" align="center" class="panelDiv"><div>
			<a href="<s:url value="/ViewTodayReportAction.action"/>"><s:text name="eodTab.label" /></a></div>
		</td>
	</tr>
</table>
<script>
setTabbedPanelWidth();
</script>