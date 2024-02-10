function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");
	$j("#botonEditarCriterios").css("display", "inline");
}

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}