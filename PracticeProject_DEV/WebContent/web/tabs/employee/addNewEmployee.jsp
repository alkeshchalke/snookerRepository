<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<sx:head />

<script type="text/javascript">

function validate()
{
	//	Empty Values check starts
	
   if( document.employeeForm.employeeID.value == "" )
   {
     alert( "Please Enter Employee ID" );
     document.employeeForm.employeeID.focus() ;
     return false;
   } 
   
   if( document.employeeForm.employeePassword.value == "" )
   {
     alert( "Please Enter Employee Password" );
     document.employeeForm.employeePassword.focus() ;
     return false;
   }
   
   if( document.employeeForm.employeeFirstName.value == "" )
   {
     alert( "Please Enter Employee First Name" );
     document.employeeForm.employeeFirstName.focus() ;
     return false;
   }
   
   if( document.employeeForm.employeeLastName.value == "" )
   {
     alert( "Please Enter Employee Last Name" );
     document.employeeForm.employeeLastName.focus() ;
     return false;
   }
   
	//	Empty Values check ends
   
   return( true );
}

</script>

<s:form action="/AddNewEmployeeAction.action" method="post"
	theme="simple" onsubmit="return(validate());" name="employeeForm" >
	
<table width="100%" border="0" cellspacing="3" cellpadding="3" align="center"
	height="100%">
	<tr height="35" />
	
	<tr>
		<td width="45%" align="right" class="fieldname"><s:text name="employee.employeeID.label"/> : <s:textfield name="employeeID"
					size="20" maxlength="20" value="" onkeypress="return RestrictSpace(event);"/></td>
					<td width="10%"/>
		<td width="9%" align="right" class="fieldname"><s:text name="employee.password.label"/> : </td>
		<td width="31%"><s:password name="employeePassword"
					size="20" maxlength="20" value="" onkeypress="return RestrictSpace(event);"/></td>
	</tr>
	<tr height="15" />
	
	<tr>
		<td width="45%" align="right" class="fieldname"><s:text name="employee.firstName.label"/> : <s:textfield name="employeeFirstName"
					size="20" maxlength="20" value="" onkeypress="return RestrictSpace(event);"/></td>
					<td width="10%"/>
		<td width="9%" align="right" class="fieldname"><s:text name="employee.lastName.label"/> : </td>
		<td width="31%"><s:textfield name="employeeLastName"
					size="20" maxlength="20" value="" onkeypress="return RestrictSpace(event);"/></td>
	</tr>
	<tr height="35" />
	
	<tr >
		<td colspan="4" align="center"><button type="submit">
				<s:text name="button.addEmployee"/>
			</button></td>
	</tr>
	<tr height="15" />
</table>
</s:form>

<script>
    document.getElementById("employeeTab").style.textDecoration = "underline";
	document.getElementById("employeeTab").style.backgroundColor = "#9DB1ED";
</script>