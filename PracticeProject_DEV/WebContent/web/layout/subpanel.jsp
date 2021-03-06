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

<table id="tabbedDiv" cellspacing="3" cellpadding="0" width="48%">
	<tr>
		<td id="addCustomertab" width="25%" align="center" class="settingsPanelDiv" class="settingsPanelDiv">
				<a href="<s:url value="Customer.action"/>"><s:text name="customerSubtab.add.label" /></a>
		</td>
		
		<td id="editCustomerTab" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="EditCustomerTabAction.action"/>"><s:text name="customerSubtab.edit.label" /></a>
		</td>
		
		<td id="deleteCustomerTab" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="DeleteCustomerTabAction.action"/>"><s:text name="customerSubtab.delete.label" /></a>
		</td>
		
		<td id="viewCustomerTab" width="25%" align="center" class="settingsPanelDiv">
				<a href="<s:url value="SearchForCustomerAction.action"/>"><s:text name="customerSubtab.view.label" /></a>
		</td>
</tr>
</table>
<script>
setTabbedPanelWidth();
</script> 