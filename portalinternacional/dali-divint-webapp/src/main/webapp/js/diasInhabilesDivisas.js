function seleccionarTodos(value, proceso) {
    console.log(proceso);
    console.log('chkAll: ' + value);

    $j('input[type=checkbox].chk' + proceso).prop('checked', value);
}

function onChangeChk(proceso) {
    var checkedChkMaster = true;

    $j('input[type=checkbox].chk' + proceso).each(function (index, chk) {
        if (!$j(chk).prop('checked')) {
            checkedChkMaster = false;
        }
    });

    $j('input[type=checkbox].chkAll' + proceso).prop('checked', checkedChkMaster);
}

function cambiaOperacion() {
    console.log("Cambiar Operacion");
    let nombreBoton;
    var tiposOperacion = document.getElementById('daliForm:lstTiposOperacion');

    if (tiposOperacion.value === '1') {
        console.log("btnActualizarOperacionRegistro");
        nombreBoton = '.btnActualizarOperacionRegistro';
    } else if (tiposOperacion.value === '2') {
        console.log("btnActualizarOperacionConsulta");
        nombreBoton = '.btnActualizarOperacionConsulta';
    } else {
        console.log("btnActualizarOperacionInicio");
        nombreBoton = '.btnActualizarOperacionInicio';
    }


    console.log("Ejecutar operacion :: " + nombreBoton);
    const buttonElement = $j(nombreBoton);
    console.log(buttonElement);

    if (buttonElement.length > 0) {
        buttonElement.click();
    } else {
        console.error("Elemento no encontrado");
    }
}

function cambiaAnio() {
    console.log("Cambiar Anio");
    let nombreBoton;

    console.log("btnActualizarAnio");
    nombreBoton = '.btnActualizarAnio';

    console.log("Ejecutar operacion :: " + nombreBoton);
    const buttonElement = $j(nombreBoton);
    console.log(buttonElement);

    if (buttonElement.length > 0) {
        buttonElement.click();
    } else {
        console.error("Elemento no encontrado");
    }
}

function modificarAnio() {
    var tiposOperacion = document.getElementById('daliForm:lstTiposOperacion');
    var anioMenu = document.getElementById('daliForm:lstAnios');
    var anioEtiqueta = document.getElementById('daliForm:etiquetaAnios');
    console.log("tipoOperacion= " + tiposOperacion.value);

    if (tiposOperacion.value === '2') {
        console.log("SI Mostrar Anio");
        anioMenu.style.visibility = 'visible';
        anioEtiqueta.style.visibility = 'visible';
    } else {
        console.log("NO Mostrar Anio");
        anioMenu.style.visibility = 'hidden';
        anioEtiqueta.style.visibility = 'hidden';
    }
}

function mostrarLoaderBuscar() {
    document.getElementById('loaderBuscar').style.display = 'block';
}

function ocultarLoaderBuscar() {
    document.getElementById('loaderBuscar').style.display = 'none';
}

function mostrarLoaderBuscarConsulta() {
    document.getElementById('loaderBuscarConsulta').style.display = 'block';
}

function ocultarLoaderBuscarConsulta() {
    document.getElementById('loaderBuscarConsulta').style.display = 'none';
}

function mostrarLoaderFirma() {
    console.log("Mostrar LoaderFirma");
    document.getElementById('loaderFirma').style.display = 'block';
}

function ocultarLoaderFirma() {
    console.log("Ocultar LoaderFirma");
    document.getElementById('loaderFirma').style.display = 'none';
}

function mostrarLoaderCargando() {
    console.log("Mostrar LoaderCargando");
    ocultarEstatusArchivo();
    document.getElementById('daliForm:loaderArchivos').style.display = 'block';
    document.getElementById('loaderCargando').style.display = 'block';
}

function ocultarLoaderCargando() {
    console.log("Ocultar LoaderCargando");
   mostrarEstatusArchivo();
    document.getElementById('daliForm:loaderArchivos').style.display = 'none';
    document.getElementById('loaderCargando').style.display = 'none';
}

function mostrarLoaderProcesando() {
    console.log("Mostrar LoaderProcesando");
    ocultarEstatusArchivo();
    document.getElementById('daliForm:loaderArchivos').style.display = 'block';
    document.getElementById('loaderProcesando').style.display = 'block';
}

function ocultarLoaderProcesando() {
    console.log("Ocultar LoaderProcesando");
    mostrarEstatusArchivo();
    document.getElementById('daliForm:loaderArchivos').style.display = 'none';
    document.getElementById('loaderProcesando').style.display = 'none';
}

function mostarEstatusArchivo() {
    console.log("Mostrar EstatusArchivo");
    var estatusArchivo = document.getElementById('daliForm:estatusArchivo');
    if (estatusArchivo !== null) {
        estatusArchivo.style.display = 'block';
    } else {
        console.error("Elemento [EstatusArchivo] no encontrado");
    }
}

function ocultarEstatusArchivo() {
    console.log("Ocultar EstatusArchivo");
    var estatusArchivo = document.getElementById('daliForm:estatusArchivo');
    if (estatusArchivo !== null) {
        estatusArchivo.style.display = 'none';
    } else {
        console.error("Elemento [EstatusArchivo] no encontrado");
    }
}


function mostrarBuscarRegistro() {
    console.log("Mostrar LoaderBuscarRegistro");
    document.getElementById('loaderBuscarRegistro').style.display = 'block';
}

function ocultarBuscarRegistro() {
    console.log("Mostrar LoaderFirma");
    document.getElementById('loaderBuscarRegistro').style.display = 'none';
}








