function callAction(theNewAction) {
	var theForm = document.forms[0];
	theForm.action = theNewAction;
	theForm.submit();
}

function RestrictSpace(evt) {
	var charCode = (evt.which) ? evt.which : evt.keyCode

	if (charCode == 32)
		return false;

	return true;
}


function loadFirstPage() {
	var temp = document.body.clientHeight - (0.2 * document.body.clientWidth);
	document.getElementById('meterDisplay').style.height= temp;
	document.getElementById('historicalReport').style.height= temp;
	document.getElementById('graph').style.height= temp;
}

function clearForm() 
{
	for (var n = 0; n < document.forms.length; n++) {
		for (var i = 0; i < document.forms[n].elements.length; i++) {
			if (document.forms[n].elements[i].type == 'text') {
				document.forms[n].elements[i].value = '';
			}
			if (document.forms[n].elements[i].type == 'password') {
				document.forms[n].elements[i].value = '';
			}
		}
	}
}

function forgotPassword() 
{
	window.location = '<s:url value="forgotPassword.action">';
}

function setTabbedPanelWidth() 
{
	var temp = 0.98 * screen.width;
	document.getElementById('tabbedDiv').style.width = temp/2;
	document.getElementById('meterTab').style.width = 0.14 * temp/2;
	document.getElementById('histReportTab').style.width = 0.18 * temp/2;
	document.getElementById('graphTab').style.width = 0.14 * temp/2;
}

function clear(obj)
{
	obj.value='';
}

