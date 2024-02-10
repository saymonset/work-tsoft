/**
 * @author Jacito
 */
$j(document).on('ready', function () {
    $j('#detalleRegistroConsulta').dialog(
        {
            autoOpen: false,
            height: 650,
            maxHeight: 1200,
            maxWidth: 1200,
            modal: true,
            resizable: true,
            width: 970,
            title: 'Detalles',
            zIndex: 10003
        }
    );
});


function consultasPresentarResumen() {
    $j("#divCriterios").css("display", "none");
    $j("#divResumen").css("display", "inline");
    $j("#divResumen").css("opacity", "1");
    $j("#botonEditarCriterios").css("display", "inline");
}

function consultasPresentarCriterios() {
    $j("#divCriterios").css("display", "inline");
    $j("#divResumen").css("display", "none");
    $j("#botonEditarCriterios").css("display", "none");
}


function consultasMostrarDivModal(titulo, url, height, width) {
    $j('#detalleRegistroConsulta').dialog('open');
    $j('#detalleRegistroConsulta').dialog('option', 'title', titulo);
    $j('#detalleRegistroConsulta').dialog('option', 'height', height);
    $j('#detalleRegistroConsulta').dialog('option', 'width', width);
    $j('#detalleRegistroConsulta').dialog('option', 'position', 'top');
    $j('#detalleRegistroConsulta').prop('src', url);
    $j('#detalleRegistroConsulta').css('width', '99%');

    $j('.ui-widget-overlay').on("click", function () {
        $j('#detalleRegistroConsulta').dialog('close');
    });
}
