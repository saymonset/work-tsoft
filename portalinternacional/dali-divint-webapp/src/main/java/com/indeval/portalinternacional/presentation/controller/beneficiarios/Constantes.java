package com.indeval.portalinternacional.presentation.controller.beneficiarios;

public interface Constantes {
	/*
	 * Constantes para el formato W9
	 */
	public static final String SOCIAL_NUMBER1 = "topmostSubform[0].Page1[0].SSN[0].f1_11[0]";
	public static final String SOCIAL_NUMBER2 = "topmostSubform[0].Page1[0].SSN[0].f1_12[0]";
	public static final String SOCIAL_NUMBER3 = "topmostSubform[0].Page1[0].SSN[0].f1_13[0]";
	public static final String EMPLOYER_IDENTIFI1 = "topmostSubform[0].Page1[0].EmployerID[0].f1_14[0]";
	public static final String EMPLOYER_IDENTIFI2 = "topmostSubform[0].Page1[0].EmployerID[0].f1_15[0]";
	
    /*
     * Constantes para los check del formato W9
     */
	public static final String FORMATOW9_EXEMPTPAYEE = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[0]";
    public static final String INDIVIDUAL = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[0]";
    public static final String C_CORPORATION = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[1]";
    public static final String S_CORPORATION = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[2]";
    public static final String PARTNERSHIP = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[3]";
    public static final String TRUST_ESTATE = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[4]";
    public static final String LIMITED_LIABILITY_COMPANY = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[5]";
    public static final String OTHER = "topmostSubform[0].Page1[0].FederalClassification[0].c1_7[0]";

    /*
     * Valores de los check para mostrarlo seleccionado
     */
    public static final String FORMATOW9_EXEMPTPAYEE_VALUE = "8";
    public static final String INDIVIDUAL_VALUE = "1";
    public static final String C_CORPORATION_VALUE = "2";
    public static final String S_CORPORATION_VALUE = "3";
    public static final String PARTNERSHIP_VALUE = "4";
    public static final String TRUST_ESTATE_VALUE = "5";
    public static final String LIMITED_LIABILITY_COMPANY_VALUE = "6";
    public static final String OTHER_VALUE = "7";
    
    /*
     *Constantes para la modificacion del porcentaje de Retencion 
     */
    public static final String PATRON_PORCENTAJE_RETENCION = "^(?!\\.?$)\\d{0,3}(\\.\\d{0,2})?$";    
    public static final Double VALOR_MAXIMO_PORCENTAJE_RETENCION = 100.00;
    public static final Double VALOR_MINIMO_PORCENTAJE_RETENCION = 0.00;
    
    /*
     * Constantes para el formato de MILA
     */
    public static final String SEPARADOR_ARCHIVO_MILA =",";
    public static final String MARCADOR_CADENA_MILA = "\"";
    public static final String SALTO_LINEA_ARCHIVO_MILA = "\n";
    
    public static final String TIPO_REGISTRO_CABECERA_MILA = "1";
    public static final String CODIGO_ARCHIVO_MILA_INT100 = "INT100";
    public static final String PREFIJO_DE_DEPOSITO_DECEVAL = "DEC";
    public static final String PREFIJO_DE_DEPOSITO_DCV = "DCV";
    public static final String PREFIJO_DE_DEPOSITO_CAVALI = "CAV";
    public static final String PREFIJO_DE_DEPOSITO_INDEVAL = "IND";
    public static final String DATE_FORMAT_MILA_INT100_CABECERA = "yyyyMMdd";
    public static final String INDICADOR_EXT_DATOS_ENTRADA_ARCHIVO_MILA = ".E";
    public static final String INDICADOR_DATOS_ENTRADA_ARCHIVO_MILA = "I";
    public static final String DATE_FORMAT_MILA_FECHA_AUT = "yyyyMMddhhmmss";
    public static final String TIPO_REGISTRO_02_MILA = "02";
    public static final String TIPO_PERSONA_FISICA_NATURAL_MILA = "N";
    public static final String TIPO_PERSONA_FISICA_JURIDICO_MILA = "J";
    
}