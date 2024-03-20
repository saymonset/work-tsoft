/**
 * @author Jacito
 */

function consultasPresentarResumen() {
    console.log("consultasPresentarResumen");
    $j("#divCriterios").css("display", "none");
    $j("#divResumen").css("display", "inline");
    $j("#divResumen").css("opacity", "1");
    $j("#botonEditarCriterios").css("display", "inline");
}

function consultasPresentarCriterios() {
    console.log("consultasPresentarCriterios");
    $j("#divCriterios").css("display", "inline");
    $j("#divResumen").css("display", "none");
    $j("#botonEditarCriterios").css("display", "none");
}


function consultasPresentarRegistro() {
    console.log("consultasPresentarRegistro");
    $j("#divResumen").css("display", "inline");
    $j("#divResumen").css("opacity", "1");
}

function seleccionarTodos(value, proceso){
    console.log(proceso);
    console.log('chkAll: ' + value);

    $j('input[type=checkbox].chk' + proceso).prop('checked', value);
}

function onChangeChk(proceso) {
    var checkedChkMaster = true;

    $j('input[type=checkbox].chk' + proceso).each(function(index, chk) {
        if(!$j(chk).prop('checked')) {
            checkedChkMaster = false;
        }
    });

    $j('input[type=checkbox].chkAll' + proceso).prop('checked', checkedChkMaster);
}


function formatInput(input) {
    // Si el campo está vacío, establece el valor por defecto
    if (!input.value) {
        input.value = 'HH:MM';
    }
}

function validateInput(input) {
    // Obtén el valor actual del campo de entrada
    let inputValue = input.value;

    // Elimina caracteres no permitidos (excepto los dos puntos)
    inputValue = inputValue.replace(/[^\d:]/g, '');

    // Dividir la entrada en horas y minutos
    const parts = inputValue.split(':');
    let hours = parts[0] || '00';
    let minutes = parts[1] || '00';

    // Asegurarse de que las horas estén en el rango de 00 a 09
    hours = hours.padStart(2, '0');

    // Formatear la entrada con dos puntos
    const formattedInput = `${hours}:${minutes}`;

    // Actualizar el valor del campo de entrada
    input.value = formattedInput;
}





