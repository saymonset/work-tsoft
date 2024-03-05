			
$j(document).on('ready',function(){
		$j('#posiciones').dialog(
				{ 
					autoOpen:false,
					height:650,
					maxheight:1200,
					maxWidth:1200,
					modal: true,
					resizable: false,
					title:'Posiciones Disponibles',
					width:950,
					zIndex:10003
				 }
			);
	});
	function seleccionarPosicion(
	cuenta,
	tv,
	emisora,
	serie,
	cupon,
	isin,
	posicionDisponible,
	posicionNoDisponible,
	precioVector,
	diasVigentes,
	tenencia,
	idBoveda,
	nombreCuenta){
		if(cuenta != '') {
			$j("input[id$='cuentaTraspasante']").prop('value',cuenta);
			$j("input[id$='tenenciaTraspasante']").prop('value',tenencia);
			$j("input[id$='nombreInstitucionTraspasante']").prop('value',nombreCuenta);
		}
		$j("input[id$='tipoValor']").prop('value',tv);
		$j("input[id$='emisora']").prop('value',emisora);
		$j("input[id$='serie']").prop('value',serie);
		$j("input[id$='cupon']").prop('value',cupon);
		$j("input[id$='isin']").prop('value',isin);
		$j("input[id$='precioVector']").prop('value',precioVector);
		$j("input[id$='diasVigentes']").prop('value',diasVigentes);
		$j("input[id$='saldoDisponible']").prop('value',posicionDisponible);
		$j("select[id$='boveda']").prop('value',idBoveda);
		$j('#posiciones').dialog('close');
		
	}

	function limpiarDatosPosicion(mostrarEncabezadoBovedas){		
		$j("input[id$='cuentaTraspasante']").prop('value','');
		$j("input[id$='tipoValor']").prop('value','');
		$j("input[id$='emisora']").prop('value','');
		$j("input[id$='serie']").prop('value','');
		$j("input[id$='cupon']").prop('value','');
		$j("input[id$='cuentaTraspasante']").prop('value','');
		$j("input[id$='isin']").prop('value','');
		$j("input[id$='precioVector']").prop('value','');
		$j("input[id$='diasVigentes']").prop('value','');
		$j("input[id$='tenenciaTraspasante']").prop('value','');
		$j("input[id$='saldoDisponible']").prop('value','');
		if($j("select[id$='boveda']") !=null){
			$j("select[id$='boveda']").prop('value','-1'); // No muestra
		}	
		$j("input[id$='saldoActual']").prop('value','');
		$j("input[id$='simulado']").prop('value','');
		$j("input[id$='nombreInstitucionTraspasante']").prop('value','');
	}
	function mostrarPosiciones(mercado){
		if($j("input[id$='cuentaTraspasante']").prop('value') === undefined ){
			cuenta = '';
		}else{
			cuenta = $j("input[id$='cuentaTraspasante']").prop('value');
		}
		
		if($j("input[id$='tipoValor']").prop('value') === undefined ){
			tv = '';
		}else{
			tv = $j("input[id$='tipoValor']").prop('value');
		}
		
		if($j("input[id$='emisora']").prop('value') === undefined ){
			emisora = '';
		}else{
			emisora = $j("input[id$='emisora']").prop('value');
		}
		
		if($j("input[id$='serie']").prop('value') === undefined ){
			serie = '';
		}else{
			serie = $j("input[id$='serie']").prop('value');
		}
		
		if($j("input[id$='isin']").prop('value') === undefined ){
			isin = '';
		}else{
			isin = $j("input[id$='isin']").prop('value');
		}
		
		if($j("input[id$='cupon']").prop('value') === undefined ){
			cupon = '';
		}else{
			cupon = $j("input[id$='cupon']").prop('value');
		}
		
		if($j("input[id$='idFolioTraspasante']").prop('value') === undefined ){
			idFolio = '';
		}else{
			idFolio = $j("input[id$='idFolioTraspasante']").prop('value');
		}
		
		if($j("select[id$='boveda']").prop('value') === undefined ){
			idBoveda = '';
		}else{
			idBoveda = $j("select[id$='boveda']").prop('value');
		}
		
		 
		url = contextPath+'/common/mostrarPosiciones'+mercado+'.faces';
		url += '?cuenta=' 
		+ cuenta
		+'&tv='+
		tv
		+'&emisora='+
		emisora
		+'&serie='+
		serie
		+'&isin='+
		isin
		+'&cupon='+
		cupon
		+'&idFolioInstitucion='+
		idFolio
		+'&idBoveda='+
		idBoveda;
		
		//tb_show('Posiciones Disponibles',encodeURI(url),'');
		/*
		* Seeccion de inicializacion de la ventana modal
		*/
			
		
			$j('#posiciones').dialog('open');
			$j('#posiciones').prop('src',url);
			$j('#posiciones').css('width','99%');
			
			$j('.ui-widget-overlay') .on("click", function() {
			    $j('#posiciones').dialog('close');
			});
		
		
	}

	function validaExtranjero() {

		if($j("input[id$='extranjero']").prop('checked') === true ){
			$j("input[id$='rfcCurp']").prop('value','EXT790101NI4');
			$j("input[id$='rfcCurp']").prop('readonly',true);
		}
		else {
			$j("input[id$='rfcCurp']").prop('value','');
			$j("input[id$='rfcCurp']").prop('readonly',false);
		}
		
	}
	function mostrarposiciones(titulo,url){
		$j('#posiciones').dialog('open');
		$j('#posiciones').dialog('option','title',titulo);
		$j('#posiciones').prop('src',url);
		$j('#posiciones').css('width','99%');
		$j('.ui-widget-overlay') .on("click", function() {
		    $j('#posiciones').dialog('close');
		});
	}

	