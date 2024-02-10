
var $j = jQuery.noConflict();

function OpcionMenu() {
	var descripcion = "";
	var ruta = "";
	var rutaIcono = "";
	var subopciones = Array();
}

function copiarOpcionMenu(opcionMenu) {
	var copiaOpcion = new OpcionMenu();
	copiaOpcion.descripcion = opcionMenu.descripcion;
	copiaOpcion.ruta = opcionMenu.ruta;
	copiaOpcion.rutaIcono = opcionMenu.rutaIcono;
	copiaOpcion.subopciones = null;
	return copiaOpcion;
}

function generarMenuPrimerNivel(opciones) {
	
	var elementoRaiz = $j("<ul></ul>");
	
	elementoRaiz.attr("class", "MM");
	elementoRaiz.attr("id", "indevalMenuBar");
	
	var i = 0;
	
	for (i = 0; i < opciones.length; i++) {
		var opcion = opciones[i];
		var elementoMenu = $j("<li></li>");
		var linkElementoMenu = $j("<a></a>");
		
		linkElementoMenu.attr("href","javascript:void(0);");
		linkElementoMenu.text(opcion.descripcion);
		elementoMenu.append(linkElementoMenu);
		
		if (opcion.subopciones !== undefined && opcion.subopciones !== null && opcion.subopciones.length !== undefined && opcion.subopciones.length > 0) {
			elementoMenu.append(generarSubmenu(opcion.subopciones));
		} 
		
		
		elementoRaiz.append(elementoMenu);
	}
	
	return elementoRaiz;
}

function generarSubmenu(opciones) {

	var elementoRaiz = $j("<ul></ul>");
	
	var i = 0;
	
	for (i = 0; i < opciones.length; i++) {
	
		var opcion = opciones[i];
		var elementoMenu = $j("<li></li>");
		
		var linkElementoMenu = $j("<a></a>");
		
		if(opcion.rutaIcono !== undefined && opcion.rutaIcono !== "") {
			
			var icono = $j("<img></img>");
			icono.attr("src", opcion.rutaIcono);
			icono.attr("border", 0);
			
			linkElementoMenu.append(icono);
		}
		
		linkElementoMenu.text(opcion.descripcion);
		elementoMenu.append(linkElementoMenu);
		
		if (opcion.subopciones !== undefined && opcion.subopciones !== null && opcion.subopciones.length !== undefined && opcion.subopciones.length > 0) {
		
			elementoMenu.append(generarSubmenu(opcion.subopciones,""));
		} else {
			
			if(opcion.ruta !== undefined && opcion.ruta !== "") {
				
				linkElementoMenu.attr("href", opcion.ruta);
				
			} else {
				linkElementoMenu.attr("href", "javascript:void(0);");
			}
		}
		
		elementoRaiz.append(elementoMenu);
	}
	return elementoRaiz;
}


function configurarMenu() {
	$j("#menuContainer").after(generarMenuPrimerNivel(opcionesMenu[0].subopciones));	
}


