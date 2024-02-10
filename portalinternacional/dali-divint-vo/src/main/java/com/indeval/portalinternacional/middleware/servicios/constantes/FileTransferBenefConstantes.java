/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Interfaz que expone las constantes utilizadas en el proceso de file transfer de formatos W.
 * 
 * @author Pablo Balderas
 *
 */
public interface FileTransferBenefConstantes {
	
	/** Indica que la longitud máxima de una cadena es 11 */
	int LONGITUD_11 = 11;
	
	/** Indica que la longitud máxima de una cadena es 19 */
	int LONGITUD_19 = 19;
	
	/** Indica que la longitud máxima de una cadena es 22 */
	int LONGITUD_22 = 22;
	
	/** Indica que la longitud máxima de una cadena es 33 */
	int LONGITUD_33 = 33;
	
	/** Indica que la longitud máxima de una cadena es 40 */
	int LONGITUD_40 = 40;
	
	/** Indica que la longitud máxima de una cadena es 50 */
	int LONGITUD_50 = 50;

	/** Indica que la longitud máxima de una cadena es 100 */
	int LONGITUD_100 = 100;
	
	/** Indica que la longitud máxima de una cadena es 150 */
	int LONGITUD_150 = 150;
	
	/** Indica que la longitud máxima de una cadena es 250 */
	int LONGITUD_250 = 250;
	
	/** Clave utilizada en filetransfer para CLEARSTREAM BANKING */
	String CLEARSTREAM_BANKING = "CEDE";
	
	/** Clave utilizada en filetransfer para DEUTSCHE BANK AG, NY */
	String DEUTSCHE_BANK = "DEUT";
	
	/** Clave utilizada en filetransfer para EUROCLEAR BANK */
	String EUROCLEAR_BANK = "MGTC";
	
	/** Clave utilizada en filetransfer para JP MORGAN */
	String JP_MORGAN = "CHAS";
	
	/** Clave utilizada en filetransfer para THE BANK OF NEW YORK */
	String BANK_OF_NEW_YORK = "IRVT";
	
	/** Clave utilizada en filetransfer para BANK OF AMERICA MERILL LYNCH */
	String BANK_OF_AMERICA_MERRIL_LYNCH = "BOFA";
	
	/** Clave utilizada en filetransfer para CITIBANK, NA. */
	String CITI = "CITI";

	/** Tipo de beneficio Fideicomiso complejo */
	String FIDEICOMISO_COMPLEJO = "FC";

	/** Tipo de beneficio Siefore/Afore */
	String SIEFORE_AFORE = "SA";
	
	/** Tipo de beneficio Persona Moral Extranjera a EUA */
	String PERSONA_MORAL_EXTRANJERA_EUA = "PMEEUA";
	
	/** Tipo de beneficio Otros BEN-E */
	String OTROS_W8BENE = "OTBENE";
	
	/** Tipo de beneficiario Fideicomiso Simple */
	String FIDEICOMISO_SIMPLE = "FS";
	
	/** Tipo de beneficiario Fideicomiso Simple W8IMY */
	String FIDEICOMISO_SIMPLE_W8 = "FSW8";
	
	/** Tipo de beneficiario Intermediario Calificado Con RPR  */
	String INT_CALIF_CON_RPR = "ICCRPR";
	
	/** Tipo de beneficiario Intermediario Calificado Sin RPR */
	String INT_CALIF_SIN_RPR = "ICSRPR";
	
	/** Tipo de beneficiario Otros W8IMY  */
	String OTROS_W8IMY = "OTIMY";
	
	/** Valor para si */
	String YES = "YES";
	
	/** Valor para no */
	String NO = "NO";
	
	/** Nombre de los campos del file transfer para los formatos W8BEN-E y W8IMY */
	String NAME_ORGANIZATION = "1 Name of organization";	
	String NAME_INDIVIDUAL_ORGANIZATION = "1 Name of individual or organization";
	String COUNTRY_INCORPORATION = "2 Country of incorporation";
	String NAME_OF_DISREGARDED_ENTITY = "3 Name of disregarded entity";
	String CHAPTER_3_STATUS = "4 Chapter 3 Status";
	String COMPLETE_PART_III = "4. Complete Part III";
	String CHAPTER_4_STATUS = "5 Chapter 4 Status";
	String CHAPTER_4_STATUS_ANEXOS = "5 Chapter 4 Status - Anexo";
	String PERMANENT_RESIDENCE_ADDRESS = "6a Permanent residence address";
	String CITY_TOWN_6B = "6b City or town";
	String COUNTRY_6C = "6c Country";
	String MAILING_ADDRESS = "7a Mailing address";
	String CITY_TOWN_7B = "7b City or town";
	String COUNTRY_7C = "7c Country";
	String US_TIN = "8 U.S. TIN";
	String QI_EIN = "8 QI_EIN";
	String WP_EIN = "8 WP_EIN";
	String WT_EIN = "8 WT_EIN";
	String EIN = "8 EIN";
	String SSN_ITIN = "8 SSN or ITIN";
	String GIIN = "9 GIIN";
	String GIIN_9a = "9a GIIN";
	String FOREING_TIN = "9b Foreing TIN";
	String REFERENCE_NUMBER = "10 Reference number";
	String CHAPTER_4_STATUS_11 = "11 Chapter 4 Status";
	String ADDRESS_BRANCH = "12a Address of branch";
	String ADDRESS_DISREGARDED_ENTITY_BRANCH = "12a Address of disregarded entity or branch";
	String CITY_TOWN_12B = "12b City or town";
	String COUNTRY_12C = "12c Country";
	String GIIN_13 = "13 GIIN";
	String CLAIM_TAX_TREATY_BENEFITS_A = "14a";
	String CLAIM_TAX_TREATY_BENEFITS_B = "14b";
	String CLAIM_TAX_TREATY_BENEFITS_C = "14c";
	String SPECIAL_RATES_CONDITIONS_ARTICLE = "15 Special rates and conditions - Article";
	String SPECIAL_RATES_CONDITIONS_RATE_WITHHOLDING = "15 Special rates and conditions - % rate withholding";
	String SPECIAL_RATES_CONDITIONS_TYPE_INCOME = "15 Special rates and conditions - Type of income";
	String SPECIAL_RATES_CONDITIONS_REASON_1 = "15 Special rates and conditions - Reasons 1";
	String SPECIAL_RATES_CONDITIONS_REASON_2 = "15 Special rates and conditions - Reasons 2";
	String PRINT_NAME = "Print Name";
	String PART_XXIX = "Part XXIX - Certification";
	String CAMPO_1 = "Campo 1";
	String CAMPO_2 = "Campo 2";
	String CAMPO_3 = "Campo 3";
	String CAMPO_4 = "Campo 4";
	String CAMPO_5 = "Campo 5";
	String CAMPO_6 = "Campo 6";
	String CAMPO_7 = "Campo 7";
	String CAMPO_8 = "Campo 8";
	String CAMPO_9 = "Campo 9";
	String CHAPTER_3_STATUS_CAMPO1 = "4 Chapter 3 Status Campo1";
	String CHAPTER_3_STATUS_CAMPO2 = "4 Chapter 3 Status Campo2";
	String CHAPTER_3_STATUS_CAMPO3 = "4 Chapter 3 Status Campo3";
	String CHAPTER_3_STATUS_CAMPO4 = "4 Chapter 3 Status Campo4";
	String CHAPTER_3_STATUS_CAMPO5 = "4 Chapter 3 Status Campo5";
	String CHAPTER_3_STATUS_CAMPO6 = "4 Chapter 3 Status Campo6";
	String CHAPTER_3_STATUS_CAMPO7 = "4 Chapter 3 Status Campo7";
	String CHAPTER_3_STATUS_CAMPO8 = "4 Chapter 3 Status Campo8";
	String CHAPTER_3_STATUS_CAMPO9 = "4 Chapter 3 Status Campo9";
	String CHAPTER_3_STATUS_CAMPO10 = "4 Chapter 3 Status Campo10";
	String CHAPTER_3_STATUS_CAMPO11 = "4 Chapter 3 Status Campo11";
	String CHAPTER_4_STATUS_CAMPO1 = "5 Chapter 4 Status Campo1";
	String CHAPTER_4_STATUS_CAMPO2 = "5 Chapter 4 Status Campo2";
	String CHAPTER_4_STATUS_CAMPO3 = "5 Chapter 4 Status Campo3";
	String CHAPTER_4_STATUS_CAMPO4 = "5 Chapter 4 Status Campo4";
	String CHAPTER_4_STATUS_CAMPO5 = "5 Chapter 4 Status Campo5";
	String CHAPTER_4_STATUS_CAMPO6 = "5 Chapter 4 Status Campo6";
	
	/** Valores para cuando se valida GIIN **/
	String Participating_FF = "B";
	String Reporting_Model_1_FFI = "C";
	String Reporting_Model_2_FFI = "D";
	
	/** Nombre del archivo con el resumen del file transfer W9 */
	String ARCHIVO_RESUMEN_W9 = "filetransferXls1";
	
	/** Nombre del archivo con el resumen del file transfer W8BEN */
	String ARCHIVO_RESUMEN_W8BEN = "filetransferXls2";
	
	/** Nombre del archivo con el resumen del file transfer W8BENE */
	String ARCHIVO_RESUMEN_W8BENE = "resumenFileTransferW8BENE";
	
	/** Nombre del archivo con el resumen del file transfer W8IMY */
	String ARCHIVO_RESUMEN_W8IMY = "resumenFileTransferW8IMY";
}