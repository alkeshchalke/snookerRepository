
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" />


<script>
	$(document).ready(function() {
		$('#example').DataTable({
			"paging" : false,
			"info" : false,
			"ordering" : false
		});
	});
</script>

<table id="example" class="display" style="width: 100%">
	<thead>
		<tr>
			<th>First name</th>
			<th>Last name</th>
			<th>Position</th>
			<th>Office</th>
			<th>Salary</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Tiger</td>
			<td>Nixon</td>
			<td>System Architect</td>
			<td>Edinburgh</td>
			<td>$320,800</td>
		</tr>
		<tr>
			<td>Garrett</td>
			<td>Winters</td>
			<td>Accountant</td>
			<td>Tokyo</td>
			<td>$170,750</td>
		</tr>
		<tr>
			<td>Ashton</td>
			<td>Cox</td>
			<td>Junior Technical Author</td>
			<td>San Francisco</td>
			<td>$86,000</td>
		</tr>
		<tr>
			<td>Cedric</td>
			<td>Kelly</td>
			<td>Senior Javascript Developer</td>
			<td>Edinburgh</td>
			<td>$433,060</td>
		</tr>
		<tr>
			<td>Airi</td>
			<td>Satou</td>
			<td>Accountant</td>
			<td>Tokyo</td>
			<td>$162,700</td>
		</tr>
		<tr>
			<td>Brielle</td>
			<td>Williamson</td>
			<td>Integration Specialist</td>
			<td>New York</td>
			<td>$372,000</td>
		</tr>
	</tbody>
</table>
