
var colores = new Array(54)
colores[0] = new Array("#330000", "#FFFFFF");
colores[1] = new Array("#330066", "#FFFFFF");
colores[2] = new Array("#3300CC", "#FFFFFF"); 
colores[3] = new Array("#333300", "#FFFFFF");
colores[4] = new Array("#333366", "#FFFFFF"); 
colores[5] = new Array("#3333CC", "#FFFFFF");
colores[6] = new Array("#336600", "#FFFFFF"); 
colores[7] = new Array("#336666", "#FFFFFF"),
colores[8] = new Array("#3366CC", "#FFFFFF"); 
colores[9] = new Array("#339900", "#000000");
colores[10] = new Array("#339966", "#000000"), 
colores[11] = new Array("#3399CC", "#000000"); 
colores[12] = new Array("#33CC00", "#000000"); 
colores[13] = new Array("#33CC66", "#000000"); 
colores[14] = new Array("#33CCCC", "#000000"); 
colores[15] = new Array("#33FF00", "#000000");
colores[16] = new Array("#33FF66", "#000000");
colores[17] = new Array("#33FFCC", "#000000"); 
colores[18] = new Array("#990000", "#FFFFFF"); 
colores[19] = new Array("#990066", "#FFFFFF"); 
colores[20] = new Array("#9900CC", "#FFFFFF"); 
colores[21] = new Array("#993300", "#FFFFFF");
colores[22] = new Array("#993366", "#FFFFFF");
colores[23] = new Array("#9933CC", "#FFFFFF"); 
colores[24] = new Array("#996600", "#FFFFFF"); 
colores[25] = new Array("#996666", "#FFFFFF"); 
colores[26] = new Array("#9966CC", "#FFFFFF"); 
colores[27] = new Array("#999900", "#000000"); 
colores[28] = new Array("#999966", "#000000"); 
colores[29] = new Array("#9999CC", "#000000"); 
colores[30] = new Array("#99CC00", "#000000"); 
colores[31] = new Array("#99CC66", "#000000"); 
colores[32] = new Array("#99CCCC", "#000000"); 
colores[33] = new Array("#99FF00", "#000000"); 
colores[34] = new Array("#99FF66", "#000000"); 
colores[35] = new Array("#99FFCC", "#000000"); 
colores[36] = new Array("#CC0000", "#FFFFFF"); 
colores[37] = new Array("#CC0066", "#FFFFFF"); 
colores[38] = new Array("#CC00CC", "#FFFFFF"); 
colores[39] = new Array("#CC3300", "#FFFFFF"); 
colores[40] = new Array("#CC3366", "#FFFFFF"); 
colores[41] = new Array("#CC33CC", "#FFFFFF"); 
colores[42] = new Array("#CC6600", "#FFFFFF"); 
colores[43] = new Array("#CC6666", "#FFFFFF"); 
colores[44] = new Array("#CC66CC", "#FFFFFF"); 
colores[45] = new Array("#CC9900", "#000000"); 
colores[46] = new Array("#CC9966", "#000000"); 
colores[47] = new Array("#CC99CC", "#000000"); 
colores[48] = new Array("#CCCC00", "#000000");
colores[49] = new Array("#CCCC66", "#000000"); 
colores[50] = new Array("#CCCCCC", "#000000"); 
colores[51] = new Array("#CCFF00", "#000000");
colores[52] = new Array("#CCFF66", "#000000"); 
colores[53] = new Array("#CCFFCC", "#000000");

/**
 * Va reduciendo el numero que recibe para poder seleccionar un color del array de colores
 * @param numero
 * @param id
 * @returns {String}
 */
function seleccionaColor(numero, id){
	var color = "";
	
	numero = parseInt(numero);
	
	if(numero > 999){
		numero = numero % 999;
		numero = parseInt(numero);
		numero = seleccionaColor(numero, id)
	}
	else{
		if(numero > 99){
			numero = numero % 99;
			numero = parseInt(numero);
			numero = seleccionaColor(numero, id)
		}
		else{
			numero = ajustaColor(numero);
		}
	}
	
	color = colores[numero][0];
	document.getElementById(id).style.backgroundColor = colores[numero][0];//color;
	document.getElementById(id).style.color = colores[numero][1];
	
	if(colores[numero][1] == "#000000"){
		document.getElementById(id).style.fontWeight = 600; 
	}
	
	return color;
}

/**
 * Ajusta el número recibido al rango(número de colores) que se tiene
 * @param numero
 * @returns
 */
function ajustaColor(numero){
	numero = parseInt(numero);
	if(numero < colores.length){
		return numero;
	}
	else{
		numero = numero - colores.length;
		return ajustaColor(numero);
	}
}