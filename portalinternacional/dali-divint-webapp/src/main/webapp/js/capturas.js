


function removeApplet(){
	   var divApplet=document.getElementById('firmaDivId');
	   var divItems=null;
	   
	   if(divApplet != null && divApplet.childNodes !=null){
	   		divItems=divApplet.childNodes;	   	      
		   for(var j=0; j < divItems.length ; j++){
			   divApplet.removeChild(divItems[j]);
		   }	
	   }
}
