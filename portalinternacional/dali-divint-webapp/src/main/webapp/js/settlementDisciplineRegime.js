checkAutoriza = new Array();
iAutoriza = 0;

$j(document).on('ready',function(){
	$j('#detalle').dialog(
			{ 
				autoOpen:false,
				height:650,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);
});

function autorizaCustodiosCsdr(campo){
	for(var i=0 ; i < iAutoriza ; i++ ){
		if( checkAutoriza[i].attr('disabled') === undefined || checkAutoriza[i].attr('disabled') == false ){
			checkAutoriza[i].attr('checked',campo.checked);
		}
	}
}