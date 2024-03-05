/**
Valida que se capture un numero decimal con determinada cantidad de decimales y enteros
onKeyPress="return validaDecimalNum(this, numEnteros, numDecimales, event);"  
**/
function validaDecimalNumValues(obj, enteros, decimales, e){	
	if(!e) e = window.event;
    if (e.keyCode) keyPressed = e.keyCode;
    else if (e.which) keyPressed = e.which;
    else return true;

	var text = obj.value;
    var hasDot=false;   
    var dotIndex=text.indexOf(".");
    if(dotIndex>-1){  
	    hasDot = true;
    }	                    
    //Si se ha capturado un punto, dejar la rutina
    if(text != null && hasDot && keyPressed == 46){
   		return false;
    }
   
    //Validar si no se capturo algun caracter especial
    result = validaNum(e);
    if(result == false)
	   return 	false;

   
    //Simular el resultado insertando el caracter en la posicion del cursor
    curPos = caretPos(obj);
    if(curPos == -1){
   	   //El cursor esta en la ultima posicion
	   text += String.fromCharCode(keyPressed);
    }else{
   	   //El cursor esta enmedio de la cadena y no es la tecla de delete
	   //window.status = text.substring(0, curPos - 1) + "-" + text.substring(curPos - 1);
	   text = text.substring(0, curPos - 1) + String.fromCharCode(keyPressed) + text.substring(curPos - 1);
    }
   
    //Determinar si se cumplieron las condiciones
    parts = text.split(".");
   
    if(parts[0].length > enteros){
         return false;
    }
    
    if(parts.length == 2 && parts[1].length > decimales){
   	    return false;
    }
   
	return true;
}

/* Se usa en el evento onKeyPress, para permitir solo numeros positivos con punto decimal
   Caracteres: 0 al 9 y . (punto) */
function validaNum(e)
{
    //By JLI, usar asi onKeyPress="return funcion(event);"
    //Keycode tiene el numero de combinacion de la tecla (Tecla+[Mayus|Acento])
    //en netscape 4 se utiliza which.
    //Si la propiedad no existe en el browser se deja pasar la tecla para evitar bloquear el control
    //(no usar con keyUp, por que keycode trae el valor de cada tecla presionada como shift, mayus, enter)
    if(!e) e = window.event;
    if (e.keyCode) keyPressed = e.keyCode;
      else if (e.which) keyPressed = e.which;
             else return true;

        if ( (keyPressed == 46) ||  //Punto
             (keyPressed ==  8) ||  //BACKSPACE
             (keyPressed ==  9) ||  //TAB
             (keyPressed == 13) ||  //ENTER
             (e.charCode == 0) || //Flechas,supr
             (e.charCode == 127) || //delete
             ((keyPressed >= 48) && (keyPressed <= 57)) )
                return true;
        else
            return false;
}

/**
Obtiene la posicion del cursor en un input. 
*/
function caretPos(obj){
	var i=obj.value.length+1;
	if (obj.createTextRange){
		theCaret = document.selection.createRange().duplicate();
		while ( theCaret.parentElement() == obj && theCaret.move("character",1)==1 ){
			--i;
		}
	}
	return i==obj.value.length+1?-1:i;
}