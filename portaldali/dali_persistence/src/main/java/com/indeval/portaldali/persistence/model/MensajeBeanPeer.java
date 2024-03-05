/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Clase utilizada como contenedor de constantes representativas de los campos que tiene un mensaje.
 *
 * @author Alejandro Rodríguez Consejo
 */
public interface MensajeBeanPeer {

    /** Tipo de operaci&oacute;n. */
    String TIPO_OPERACION = "tipoOperacion";

    /** Tipo de mensaje. */
    String TIPO_MENSAJE = "tipoMensaje";

    /** Cantidad de t&iacute;tulos. */
    String CANTIDAD_TITULOS_OP = "cantidadTitulosOp";

    /** Cuenta del receptor. */
    String CUENTA_COMPRADOR = "cuentaComprador";

    /** Cuenta del traspasante. */
    String CUENTA_VENDEDORA = "cuentaVendedora";

    /** Campo de la emisi&oacute;n : Cupon. */
    String CUPON = "cupon";

    /** Campo de la emisi&oacute;n : Emisora. */
    String EMISORA = "emisora";

    /** Fecha en la que fue generada la instrucci&oacute;n. */
    String FECHA_HORA = "fechaHoraRegistro";

    /** Fecha en que debe ser liquidada la transacci&oacute;n. */
    String FECHA_LIQUIDACION = "fechaLiquidacion";

    /** fecha de vencimiento. */
    String FECHA_REPORTO = "fechaReporto";

    /** Fecha en la que se pact&oacute; la transacci&oacute;n entre los participantes. */
    String FECHA_CONCERTACION = "fechaConcertacion";

    /** Folio que se asigna a la operaci&oacute;n. */
    String FOLIO_CONTROL = "folioControl";

    /** Folio de la instituci&oacute;n receptora. */
    String FOLIO_INSTITUCION_COMPRADORA = "folioInstitucionCompradora";

    /** Folio de la instituci&oacute;n traspasante. */
    String FOLIO_INSTITUCION_VENDEDORA = "folioInstitucionVendedora";

    /** Folio asignado a la instrucci&oacute;n por el participante. */
    String FOLIO_USUARIO = "folioUsuario";

    /** Identificador de la instituci&oacute;n receptora. */
    String ID_INSTITUCION_COMPRADORA = "idInstitucionCompradora";

    /** Identificador de la instituci&oacute;n traspasante. */
    String ID_INSTITUCION_VENDEDORA = "idInstitucionVendedora";

    /** Monto a liquidar. */
    String IMPORTE = "importe";

    /** Campo de la emisi&oacute;n : Isin. */
    String ISIN = "isin";

    /** Identificador de la instituci&oacute;n receptora. */
    String LIQUIDADOR = "liquidador";

    /** specifica si la instrucci&oacute;n requiere match. */
    String MATCH_REQUERIDO = "matchRequeridoId";

    /** Precio unitario. */
    String PRECIO_TITULO = "precioTitulo";

    /** Campo de la emisi&oacute;n : Serie. */
    String SERIE = "serie";

    /** Estado de la operaci&oacute;n. */
    String STATUS_OPERACION = "statusOperacion";

    /** Campo de la emisi&oacute;n : Tipo de valor. */
    String TIPO_VALOR = "tipoValor";

    /** Medio de entrega del mensaje. */
    String ORIGEN = "origenTransac";

    /**
     * Porcentaje aplicado para la determinaci&oacute;n de rendimientos o precios durante la operaci&oacute;n.
     */
    String TASA_NEGOCIADA = "tasaNegociada";

    /** Porcentaje tomado como base para llegar a la tasa real de pago. */
    String TASA_REFERENCIA = "tasaReferencia";

    /** Mensaje original utilizado para enviar la copia. */
    String MENSAJE = "mensaje";

    /** Liquidador para las transacciones. */
    String LIQUIDADOR_INDEVAL = "INDEVAL";
    
    /** REFERENCIA - Referencia del mensaje de cancelaci&oacute;n. */
    String REFERENCIA_CANCELACION = "referenciaPreviaCancelacion";    
    
    /** Funcion del mensaje, sirve para cancelacion */
    String FUNCION_MENSAJE = "funcionMensajes";    
    
    /** Constante para el indicar SI */
    Character SI = 'S';
    
    /** Constante para el indicar NO */
    Character NO = 'N';
    
}
