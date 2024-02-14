function seleccionarTodos(value, proceso){
	console.log(proceso);
	console.log('chkAll: ' + value);

	$j('input[type=checkbox].chk' + proceso).prop('checked', value);
}

function onChangeChk(proceso) {
	var checkedChkMaster = true;

	$j('input[type=checkbox].chk' + proceso).each(function(index, chk) {
		if(!$j(chk).prop('checked')) {
			checkedChkMaster = false;
		}
	});

	$j('input[type=checkbox].chkAll' + proceso).prop('checked', checkedChkMaster);
}

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "0");
	$j("#divResumen").animate({opacity:1}, 500);
	$j("#botonEditarCriterios").css("display", "inline");
}


function hasErrors(){
	var errors = document.getElementById("daliForm:validationErrors");
	return errors.value == "true";
}



