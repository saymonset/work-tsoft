$j(document).on('ready', function () {
    $j('#posiciones').dialog(
        {
            autoOpen: false,
            height: 650,
            maxheight: 1200,
            maxWidth: 1200,
            modal: true,
            resizable: false,
            title: 'Posiciones Disponibles',
            width: 950,
            zIndex: 10003
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
    descripcionBoveda,
    alta,
    idBoveda,
    nombreCuenta) {
    $j("input[id$='cuentaTraspasante']").attr('value', cuenta);
    $j("input[id$='tipoValor']").attr('value', tv);
    $j("input[id$='emisora']").attr('value', emisora);
    $j("input[id$='serie']").attr('value', serie);
    $j("input[id$='cupon']").attr('value', cupon);
    $j("input[id$='cuentaTraspasante']").attr('value', cuenta);
    $j("input[id$='isin']").attr('value', isin);
    $j("input[id$='precioVector']").attr('value', precioVector);
    $j("input[id$='diasVigentes']").attr('value', diasVigentes);
    $j("input[id$='tenenciaTraspasante']").attr('value', tenencia);
    $j("input[id$='saldoDisponible']").attr('value', posicionDisponible);
    $j("input[id$='boveda']").attr('value', descripcionBoveda);
    $j("input[id$='alta']").attr('value', alta);
    $j("input[id$='idBoveda']").attr('value', idBoveda);
    $j("input[id$='nombreInstitucionTraspasante']").attr('value', nombreCuenta);

    $j('#posiciones').dialog('close');
    try {
        posicionSeleccionada();
    } catch (e) {

    }
}

function limpiarDatosPosicion() {
    $j("input[id$='tipoValor']").attr('value', '');
    $j("input[id$='emisora']").attr('value', '');
    $j("input[id$='serie']").attr('value', '');
    $j("input[id$='cupon']").attr('value', '');
    $j("input[id$='isin']").attr('value', '');
    $j("input[id$='precioVector']").attr('value', '');
    $j("input[id$='diasVigentes']").attr('value', '');
    $j("input[id$='tenenciaTraspasante']").attr('value', '');
    $j("input[id$='saldoDisponible']").attr('value', '0');
    $j("input[id$='boveda']").attr('value', '');
    $j("input[id$='alta']").attr('value', '');
    $j("input[id$='idBoveda']").attr('value', '');
    $j("input[id$='cantidadOperada']").attr('value', '');
    $j("input[id$='alta']").attr('value', '');
    $j("input[id$='posicionActual']").attr('value', '0');
    try {
        limpiarCustodioDepositante();
    } catch (e) {
    }

}


function mostrarPosiciones() {
    if ($j("input[id$='cuentaTraspasante']").attr('value') === undefined) {
        cuenta = '';
    } else {
        cuenta = $j("input[id$='cuentaTraspasante']").attr('value');
    }

    if ($j("input[id$='tipoValor']").attr('value') === undefined) {
        tv = '';
    } else {
        tv = $j("input[id$='tipoValor']").attr('value');
    }

    if ($j("input[id$='emisora']").attr('value') === undefined) {
        emisora = '';
    } else {
        emisora = $j("input[id$='emisora']").attr('value');
    }

    if ($j("input[id$='serie']").attr('value') === undefined) {
        serie = '';
    } else {
        serie = $j("input[id$='serie']").attr('value');
    }

    if ($j("input[id$='isin']").attr('value') === undefined) {
        isin = '';
    } else {
        isin = $j("input[id$='isin']").attr('value');
    }

    if ($j("input[id$='idFolioTraspasante']").attr('value') === undefined) {
        idFolio = '';
    } else {
        idFolio = $j("input[id$='idFolioTraspasante']").attr('value');
    }

//	if($j("select[id$='boveda']").attr('value') === undefined ){
//		idBoveda = '';
//	}else{
//		idBoveda = $j("select[id$='boveda']").attr('value');
//	}
    if ($j("input[id$='boveda']").attr('value') === undefined) {
        idBoveda = '';
    } else {
        idBoveda = "-1";
    }
    if ($j("input[id$='idBoveda']").attr('value') === undefined) {
        idBoveda = '';
    } else {
        idBoveda = $j("input[id$='idBoveda']").attr('value');
    }


    url = contextPath + '/common/mostrarPosiciones.faces';
    url += '?cuenta=' +
        cuenta
        + '&tv=' +
        tv
        + '&emisora=' +
        emisora
        + '&serie=' +
        serie
        + '&isin=' +
        isin
        + '&idFolioInstitucion=' +
        idFolio
        + '&idBoveda=' +
        idBoveda;

    $j('#posiciones').dialog('open');
    $j('#posiciones').attr('src', url);
    $j('#posiciones').css('width', '99%');

    $j('.ui-widget-overlay').on("click", function () {
        $j('#posiciones').dialog('close');
    });

}


function mostrarPosicionesInvExt() {
    if ($j("input[id$='cuentaTraspasante']").attr('value') === undefined) {
        cuenta = '';
    } else {
        cuenta = $j("input[id$='cuentaTraspasante']").attr('value');
    }

    if ($j("input[id$='tipoValor']").attr('value') === undefined) {
        tv = '';
    } else {
        tv = $j("input[id$='tipoValor']").attr('value');
    }

    if ($j("input[id$='emisora']").attr('value') === undefined) {
        emisora = '';
    } else {
        emisora = $j("input[id$='emisora']").attr('value');
    }

    if ($j("input[id$='serie']").attr('value') === undefined) {
        serie = '';
    } else {
        serie = $j("input[id$='serie']").attr('value');
    }

    if ($j("input[id$='isin']").attr('value') === undefined) {
        isin = '';
    } else {
        isin = $j("input[id$='isin']").attr('value');
    }

    if ($j("input[id$='idTraspasante']").attr('value') === undefined) {
        idFolio = '';
    } else {
        idFolio = $j("input[id$='idTraspasante']").attr('value');
    }

    if ($j("input[id$='boveda']").attr('value') === undefined) {
        idBoveda = '';
    } else {
        idBoveda = "-1";
    }


    url = contextPath + '/common/mostrarPosicionesInvExt.faces';
    url += '?cuenta=' +
        cuenta
        + '&tv=' +
        tv
        + '&emisora=' +
        emisora
        + '&serie=' +
        serie
        + '&isin=' +
        isin
        + '&idFolioInstitucion=' +
        idFolio
        + '&idBoveda=' +
        idBoveda;
    //alert(url);

    $j('#posiciones').dialog('open');
    $j('#posiciones').attr('src', url);
    $j('#posiciones').css('width', '99%');
}

function mostrarEmisionesInvExt() {
    if ($j("input[id$='cuentaTraspasante']").attr('value') === undefined) {
        cuenta = '';
    } else {
        cuenta = $j("input[id$='cuentaTraspasante']").attr('value');
    }

    if ($j("input[id$='tipoValor']").attr('value') === undefined) {
        tv = '';
    } else {
        tv = $j("input[id$='tipoValor']").attr('value');
    }

    if ($j("input[id$='emisora']").attr('value') === undefined) {
        emisora = '';
    } else {
        emisora = $j("input[id$='emisora']").attr('value');
    }

    if ($j("input[id$='serie']").attr('value') === undefined) {
        serie = '';
    } else {
        serie = $j("input[id$='serie']").attr('value');
    }

    if ($j("input[id$='isin']").attr('value') === undefined) {
        isin = '';
    } else {
        isin = $j("input[id$='isin']").attr('value');
    }

    if ($j("input[id$='idFolioTraspasante']").attr('value') === undefined) {
        idFolio = '';
    } else {
        idFolio = $j("input[id$='idFolioTraspasante']").attr('value');
    }

    if ($j("input[id$='boveda']").attr('value') === undefined) {
        idBoveda = '';
    } else {
        idBoveda = "-1";
    }


    url = contextPath + '/common/mostrarEmisionesInvExt.faces';
    url += '?cuenta=' +
        cuenta
        + '&tv=' +
        tv
        + '&emisora=' +
        emisora
        + '&serie=' +
        serie
        + '&isin=' +
        isin
        + '&idFolioInstitucion=' +
        idFolio
        + '&idBoveda=' +
        idBoveda;
    //alert(url);

    $j('#posiciones').dialog('open');
    $j('#posiciones').attr('src', url);
    $j('#posiciones').css('width', '99%');
}

function confirmarGuardar() {
    var output = "\u00bfEst\u00e1 seguro de que desea guardar la operaci\u00F3n?";
    if (!confirm(output, "titulo ventana"))
        return false;
    return true;
}

function establecerFoco(id) {
    $j("input[id$='" + id + "']").focus();
}