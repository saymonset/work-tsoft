/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Interfaz que expone las constantes utilizadas en los módulos de beneficiarios.
 * 
 * @author Pablo Balderas
 * 
 */
public interface BeneficiariosConstantes {

    String CADENA_VACIA = "";

    /** Nombre del formato W9 */
    String FORMATO_W9 = "W9";

    /** Id del formato W9 */
    int ID_FORMATO_W9 = 1;

    /** Nombre del formato W8BEN */
    String FORMATO_W8_BEN = "W8BEN";

    /** Id del fortmato W8BEN */
    int ID_FORMATO_W8_BEN = 2;

    /** Nombre del formato W8 BEN-E */
    String FORMATO_W8_BEN_E = "W8BENE";

    /** Nombre del formato W8 BEN-E */
    String FORMATO_W8_BEN_E_2016 = "W8BENE2016";

    /** Id del formato W8BEN-E */
    int ID_FORMATO_W8_BEN_E = 3;

    /** Nombre del formato W8 BEN 2014 */
    String FORMATO_W8_BEN2014 = "W8BEN2014";

    /** Nombre del formato W8 BEN 2014 */
    String FORMATO_W8_BEN2017 = "W8BEN2017";

    /** Nombre del formato W8 IMY */
    String FORMATO_W8_IMY = "W8IMY";

    /** Id del formato W8IMY */
    int ID_FORMATO_W8_IMY = 4;

    /** Nombre del formato W8 IMY 2015 */
    String FORMATO_W8_IMY2015 = "W8IMY2015";

    /** Nombre del formato W8 IMY 2017 */
    String FORMATO_W8_IMY2017 = "W8IMY2017";

    /** Nombre del formato W8 IMY 2017 */
    String FORMATO_W8_IMY2016 = "W8IMY2016";

    /** Nombre del formato MILA */
    String FORMATO_MILA = "MILA";

    /** Referencia CLEARSTREAM */
    String REF_CLEARSTREAM = "75986";

    /** Referencia EUROCLEAR_BANK */
    String REF_EUROCLEAR_BANK = "23310";

    /** Referencia LEYENDA AFORES-SIEFORE */
    String LEYENDA_AFORE_W8BENE_1 = "The B.O. is an organization";
    String LEYENDA_AFORE_W8BENE_2 =
            " constituted under mexican law operated exclusively to administer or provide pension, retirment , or other employee benefits.";

    /** Tipo de beneficiario Otros(W8BEN-E) */
    long OTROS_W8_BEN_E = 15;

    /** Tipo de beneficiario Otros(W8IMY) */
    long OTROS_W8_IMY = 16;

    /** Valor DIVIDENDS */
    String VALOR_DIVIDENDS = "10";

    /** DIVIDENDS */
    String DIVIDENDS = "DIVIDENDS";

    /** Valor INTEREST */
    String VALOR_INTEREST = "11";

    /** INTEREST */
    String INTEREST = "INTEREST";

    /** Id estado activo */
    String ID_ESTADO_ACTIVO = "1";

    /** Estado del registro activo */
    String ESTADO_ACTIVO = "ACTIVO";

    /** Id estado inactivo */
    String ID_ESTADO_INACTIVO = "0";

    /** Estado del registro inactivo */
    String ESTADO_INACTIVO = "INACTIVO";
    /** Estado del registro previo */
    Long ESTADO_DERECHO_PREVIO = 2l;
    /** Estado del registro inactivo */
    Long ESTADO_DERECHO_AUTORIZADO = 3l;
    /** Estado del registro inactivo */
    Long ESTADO_DERECHO_CONFIRMADO = 4l;
    /** Estado del registro inactivo */
    Long ESTADO_DERECHO_PAGADO = 5l;

    /** Estado del registro previo */
    String ESTADO_DERECHO_PREVIO_LABEL = "PREVIO";
    /** Estado del registro inactivo */
    String ESTADO_DERECHO_AUTORIZADO_LABEL = "AUTORIZADO";
    /** Estado del registro inactivo */
    String ESTADO_DERECHO_CONFIRMADO_LABEL = "CONFIRMADO";
    /** Estado del registro inactivo */
    String ESTADO_DERECHO_PAGADO_LABEL = "PAGADO";

    /** Estado del registro inactivo */
    Long TIPO_DERECHO_DIVIDENDO_EFECTIVO = 1l;
    /** Estado del registro inactivo */
    Long TIPO_DERECHO_DISTRIBUCION = 18l;
    /** Estado del registro inactivo */
    String TIPO_DERECHO_DIVIDENDO_EFECTIVO_LABEL = "Dividendo en Efectivo";
    /** Estado del registro inactivo */
    String TIPO_DERECHO_DISTRIBUCION_LABEL = "Distribución";
    
    /** DESCRIPCION STATUS BENEFICIARIO **/
    String STATUS_BENEFICIARIO_AUTORIZADO = "AUTORIZADO";
    String STATUS_BENEFICIARIO_VENCIDO = "VENCIDO";
    String STATUS_BENEFICIARIO_CANCELADO = "CANCELADO";

    /** Pantallas de redireccion */
    String PANTALLA_EXPORTACION_BENEFICIARIOS = "exportacionBeneficiarios";
    
    /** Separador de las columnas para el archivo CVS */
    String SEPARADOR_COLUMNAS_CVS = ",";
    
    /** Espacio en blanco */
    String CADENA_ESPACIO_BLANCO = " ";
    
    /** Cadena con si en ingles */
    String YES = "Yes";
    
    /** Cadena No */
    String NO = "No";
    
    /** Indica que el origen del beneficiario es Dali */
    String ORIGEN_BENEFICIARIO_DALI = "DALI";
}
