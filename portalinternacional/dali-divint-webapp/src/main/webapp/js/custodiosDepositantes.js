$j(document).on('ready',function(){
	
});

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
}

function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");
	//$j("#divResumen").css("opacity", "0");
	//$j("#divResumen").animate({opacity:1}, 1000);
	$j("#botonEditarCriterios").css("display", "inline");
}