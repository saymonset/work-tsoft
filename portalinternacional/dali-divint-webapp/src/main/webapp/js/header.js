diasSemana = new Array('Domingo','Lunes','Martes','Mi&eacute;rcoles','Jueves','Viernes','S&aacute;bado');

meses = new Array('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre');

$j(document).ready(function () {
	$j("#cambiarInstitucionLink").click(function () {
		if (document.getElementById("cambiarInstitucionForm").style.display == "block") {
			$j("#cambiarInstitucionForm").fadeOut("fast");
		} else {
			$j("#cambiarInstitucionForm").fadeIn("fast");
		}
		return false;
	});
});

function dibujarContenidoFecha(){
				
						fechaStr = diasSemana[fechaActual.getDay()] + "," +dosDigitos(fechaActual.getDate()) + " de " + 
						meses[fechaActual.getMonth()]+ " de " + fechaActual.getFullYear() + " | " + dosDigitos(fechaActual.getHours()) + ":" + dosDigitos(fechaActual.getMinutes()) + ":" + dosDigitos(fechaActual.getSeconds());
						$j(".fechaActual").html(fechaStr);
						fechaServidor+=1000;
						fechaActual.setTime(fechaServidor);
						
}

function dosDigitos(_v)
{
	  if(_v<10){
	  	_v="0"+_v;
	  }
	return _v;
}



function desplegarFecha(){
				
						dibujarContenidoFecha();
						if(timer){
							clearInterval(timer);
							timer=null;
						}
 						timer=setInterval("dibujarContenidoFecha()",1000);
						
}

