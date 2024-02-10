/**
 * CMMV - Portal Internacional
 * Copyrigth (c) 2022 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;

/**
 * Clase utilitaria para la exportacion de los beneficiarios con formato W8IMY
 * 
 * @author Pablo Balderas
 */
public class ExportacionW8IMY {

	/**
	 * Metodo que procesa la lista de beneficiarios para obtener los datos a exportar.
	 * @param beneficiarios Lista con los beneficiarios a procesar.
	 * @param mapaCustodios Mapa de con la informacion de los custodios.
	 * @param formatoW8Service Servicio para obtener los formatos.
	 * @return Lista de cadenas con los datos procesados.
	 */
	public static List<String> procesarBeneficiariosW8IMY(
			List<Beneficiario> beneficiarios, 
			Map<Long, String> mapaCustodios, FormatoW8Service formatoW8Service) {

		//Lista de registros procesados
		ArrayList<String> registrosProcesados = new ArrayList<>();
		//StringBuilder para cada registro
		StringBuilder registroCVS = null;
		//Recorre la lista de beneficiarios
		for (Beneficiario beneficiario : beneficiarios) {
			 if(StringUtils.isNotBlank(beneficiario.getOrigen()) && 
		                BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI.equals(beneficiario.getOrigen())) {
				//Inicializa el objeto
					registroCVS = new StringBuilder();
					//Cadenas auxiliares
					String aux;
					//ACCOUNT_NUMBER
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//CLIENT_ID
					aux = StringUtils.isNotBlank(beneficiario.getUoiNumber()) ? beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
					registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//Inicializa la forma
					switch (beneficiario.getTipoFormato()) {
						case BeneficiariosConstantes.FORMATO_W8_IMY2015:
							FormaW8IMY2015 formaW8IMY2015 = formatoW8Service.obtenerCamposFormatoW8IMY2015(
								beneficiario.getFormatoW8IMY2015().getIdCamposFormato());
							formaW8IMY2015.construyeBean(beneficiario, true);
							//CHAPTER_3
							registroCVS.append(obtenerValorParte1Campo4(formaW8IMY2015) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							//CHAPTER_4	
							registroCVS.append(obtenerValorParte1Campo5(formaW8IMY2015) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							//NAME1
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp1()) ? formaW8IMY2015.getPartIcmp1().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//DE_NAME
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp3()) ? formaW8IMY2015.getPartIcmp3().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//ORG_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp2()) ? formaW8IMY2015.getPartIcmp2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp6a()) ? formaW8IMY2015.getPartIcmp6a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp6b()) ? formaW8IMY2015.getPartIcmp6b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp6c()) ? formaW8IMY2015.getPartIcmp6c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_INCAREOF
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_POBOX
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAILING_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp7a()) ? formaW8IMY2015.getPartIcmp7a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp7b()) ? formaW8IMY2015.getPartIcmp7b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp7c()) ? formaW8IMY2015.getPartIcmp7c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//US_TIN
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp8()) ? formaW8IMY2015.getPartIcmp8().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_EIN
			    			aux = formaW8IMY2015.isPartIcmp8d() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_QI_EIN	
			    			aux = formaW8IMY2015.isPartIcmp8a() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_SSN	
			    			aux = formaW8IMY2015.isPartIcmp8e() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_WP_EIN	
			    			aux = formaW8IMY2015.isPartIcmp8b() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_WT_EIN
			    			aux = formaW8IMY2015.isPartIcmp8c() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//NOFTIN
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PART_I_GIIN
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp9()) ? formaW8IMY2015.getPartIcmp9().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TREATY_COUNTRY_CD
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//LOB_OTHERPROVISION
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//LOB_PROVISION
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//REFERENCE_NUMBER
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIcmp10()) ? formaW8IMY2015.getPartIcmp10().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIIcmp12a()) ? formaW8IMY2015.getPartIIcmp12a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_INCAREOF
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_POBOX	
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIIcmp12b()) ? formaW8IMY2015.getPartIIcmp12b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH__ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIIcmp12c()) ? formaW8IMY2015.getPartIIcmp12c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_GIIN
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPartIIcmp13()) ? formaW8IMY2015.getPartIIcmp13().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//SIGNATURE
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PRINT_NAME	
			    			aux = StringUtils.isNotBlank(formaW8IMY2015.getPrintName()) ? formaW8IMY2015.getPrintName().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							break;
						case BeneficiariosConstantes.FORMATO_W8_IMY2017:
							FormaW8IMY2017 formaW8IMY2017 = formatoW8Service.obtenerCamposFormatoW8IMY2017(
								beneficiario.getFormatoW8IMY2015().getIdCamposFormato());
							formaW8IMY2017.construyeBean(beneficiario, true);
							//CHAPTER_3
							registroCVS.append(obtenerValorParte1Campo4(formaW8IMY2017) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							//CHAPTER_4	
							registroCVS.append(obtenerValorParte1Campo5(formaW8IMY2017) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							//NAME1
							aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp1()) ? formaW8IMY2017.getPartIcmp1().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//DE_NAME
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp3()) ? formaW8IMY2017.getPartIcmp3().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//ORG_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp2()) ? formaW8IMY2017.getPartIcmp2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp6a()) ? formaW8IMY2017.getPartIcmp6a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp6b()) ? formaW8IMY2017.getPartIcmp6b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp6c()) ? formaW8IMY2017.getPartIcmp6c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_INCAREOF
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_POBOX
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PR_ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAILING_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp7a()) ? formaW8IMY2017.getPartIcmp7a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp7b()) ? formaW8IMY2017.getPartIcmp7b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp7c()) ? formaW8IMY2017.getPartIcmp7c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//MAIL_ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//US_TIN	
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp8()) ? formaW8IMY2017.getPartIcmp8().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_EIN
			    			aux = formaW8IMY2017.isPartIcmp8d() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_QI_EIN	
			    			aux = formaW8IMY2017.isPartIcmp8a() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_SSN	
			    			aux = formaW8IMY2017.isPartIcmp8e() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_WP_EIN	
			    			aux = formaW8IMY2017.isPartIcmp8b() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//TIN_TYPE_WT_EIN
			    			aux = formaW8IMY2017.isPartIcmp8c() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//NOFTIN
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PART_I_GIIN	
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp9()) ? formaW8IMY2017.getPartIcmp9().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS); 
			    			//TREATY_COUNTRY_CD
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//LOB_OTHERPROVISION
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//LOB_PROVISION	
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//REFERENCE_NUMBER
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIcmp10()) ? formaW8IMY2017.getPartIcmp10().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_ADDRESS
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIIcmp12a()) ? formaW8IMY2017.getPartIIcmp12a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_INCAREOF
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_POBOX	
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_STATE_PROV
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_CITY
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIIcmp12b()) ? formaW8IMY2017.getPartIIcmp12b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH__ZIP
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_COUNTRY_CD
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIIcmp12c()) ? formaW8IMY2017.getPartIIcmp12c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//BRANCH_GIIN
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPartIIcmp13()) ? formaW8IMY2017.getPartIIcmp13().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//SIGNATURE
			    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			    			//PRINT_NAME	
			    			aux = StringUtils.isNotBlank(formaW8IMY2017.getPrintName()) ? formaW8IMY2017.getPrintName().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
			    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
							break;
						default:
							break;
					}
					//DATE_SIGNED
					registroCVS.append((null != beneficiario.getFechaFormato()) ?
						DateFormatUtils.format(beneficiario.getFechaFormato(), "MM/dd/yyyy")  + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS:
							StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//QDD_CLASSIFICATION
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//AFFIL_NAME
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//BANKRUPTCY_DATE
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//IGA_COUNTRY_CD
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//IGA_STATUS
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//IGA_TRUSTEE_CLASSIFICATION
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//SPONSOR_NAME
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//STARTUP_DATE
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//SEC_EXCHANGE
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE14
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15F
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15G
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15H
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE15I
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE16A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE17A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE17B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE17C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE17D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE17E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE18F
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE19F
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE20
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE21A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE21B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE21C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE21D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE21E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE22
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE23B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE23C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE24A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE24B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE24C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE25
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE26
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE27B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE28
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE29
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE30_MODEL1
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE30_MODEL2
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE30A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE30B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE30C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE31
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE32
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33C
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33D
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33E
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE33F
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE34
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE35
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE36
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE37A
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE37B
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE38
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE39
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE40
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//LINE42
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					
					//Institucion
					Set<Institucion> instituciones = beneficiario.getInstitucion();
					if(null != instituciones && !instituciones.isEmpty()) {
						Institucion i = instituciones.iterator().next();
						registroCVS.append(i.getTipoInstitucion().getClaveTipoInstitucion() + i.getFolioInstitucion() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					}
					else {				
						registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					}
					//Custodio
					registroCVS.append(mapaCustodios.get(beneficiario.getIdCuentaNombrada()).replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//U.O.I.
					aux = StringUtils.isNotBlank(beneficiario.getUoiNumber()) ? beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
					registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//ADP
					aux = StringUtils.isNotBlank(beneficiario.getAdp()) ? beneficiario.getAdp().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
					registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					
					//Agrega a registros procesados
					registrosProcesados.add(registroCVS.toString());
			 }
		}
		return registrosProcesados;
		
	}
	
	/**
	 * Obtiene el valor de Parte I - Campo 4
	 * @param forma Objeto con el formato
	 * @return Valor de acuerdo al diccionario de Markit
	 */
	private static String obtenerValorParte1Campo4(FormaGeneral forma) {
		String valor = StringUtils.EMPTY;
		if(forma instanceof FormaW8IMY2015) {
			FormaW8IMY2015 forma2015 = (FormaW8IMY2015) forma;
			if(forma2015.isPartIcmp4a())
				valor = "8";
			else if(forma2015.isPartIcmp4b())
				valor = "9";
			else if(forma2015.isPartIcmp4c())
				valor = "44";
			else if(forma2015.isPartIcmp4d())
				valor = "16";
			else if(forma2015.isPartIcmp4e())
				valor = "10";
			else if(forma2015.isPartIcmp4f())
				valor = "23";
			else if(forma2015.isPartIcmp4g())
				valor = "11";
			else if(forma2015.isPartIcmp4h())
				valor = "24";
			else if(forma2015.isPartIcmp4i())
				valor = "25";
		}
		else if(forma instanceof FormaW8IMY2017) {
			FormaW8IMY2017 forma2017 = (FormaW8IMY2017) forma;
			if(forma2017.isPartIcmp4a())
				valor = "8";
			else if(forma2017.isPartIcmp4b())
				valor = "9";
			else if(forma2017.isPartIcmp4c())
				valor = "44";
			else if(forma2017.isPartIcmp4d())
				valor = "16";
			else if(forma2017.isPartIcmp4e())
				valor = "10";
			else if(forma2017.isPartIcmp4f())
				valor = "23";
			else if(forma2017.isPartIcmp4g())
				valor = "11";
			else if(forma2017.isPartIcmp4h())
				valor = "24";
			else if(forma2017.isPartIcmp4i())
				valor = "25";
		}
		return valor;
	}

	/**
	 * Obtiene el valor de Parte I - Campo 5
	 * @param forma Objeto con el formato
	 * @return Valor de acuerdo al diccionario de Markit
	 */
	private static String obtenerValorParte1Campo5(FormaGeneral forma) {
		String valor = StringUtils.EMPTY;
		if(forma instanceof FormaW8IMY2015) {
			FormaW8IMY2015 forma2015 = (FormaW8IMY2015) forma;
			if(forma2015.isPartIcmp5a()) valor = "1";
			if(forma2015.isPartIcmp5b()) valor = "2";
			if(forma2015.isPartIcmp5b()) valor = "3";
			if(forma2015.isPartIcmp5d()) valor = "4";
			if(forma2015.isPartIcmp5e()) valor = "5";
			if(forma2015.isPartIcmp5f()) valor = "18";
			if(forma2015.isPartIcmp5g()) valor = "6";
			if(forma2015.isPartIcmp5h()) valor = "7";
			if(forma2015.isPartIcmp5i()) valor = "8";
			if(forma2015.isPartIcmp5j()) valor = "9";
			if(forma2015.isPartIcmp5k()) valor = "10";
			if(forma2015.isPartIcmp5l()) valor = "11";
			if(forma2015.isPartIcmp5m()) valor = "12";
			if(forma2015.isPartIcmp5n()) valor = "38";
			if(forma2015.isPartIcmp5o()) valor = "13";
			if(forma2015.isPartIcmp5p()) valor = "16";
			if(forma2015.isPartIcmp5q()) valor = "19";
			if(forma2015.isPartIcmp5r()) valor = "20";
			if(forma2015.isPartIcmp5s()) valor = "21";
			if(forma2015.isPartIcmp5t()) valor = "24";
			if(forma2015.isPartIcmp5u()) valor = "25";
			if(forma2015.isPartIcmp5v()) valor = "26";
			if(forma2015.isPartIcmp5w()) valor = "27";
			if(forma2015.isPartIcmp5x()) valor = "42";
			if(forma2015.isPartIcmp5y()) valor = "43";
		}
		else if(forma instanceof FormaW8IMY2017) {
			FormaW8IMY2017 forma2017 = (FormaW8IMY2017) forma;
			if(forma2017.isPartIcmp5a()) valor = "1";
			if(forma2017.isPartIcmp5b()) valor = "2";
			if(forma2017.isPartIcmp5c()) valor = "3";
			if(forma2017.isPartIcmp5d()) valor = "4";
			if(forma2017.isPartIcmp5e()) valor = "5";
			if(forma2017.isPartIcmp5f()) valor = "18";
			if(forma2017.isPartIcmp5g()) valor = "6";
			if(forma2017.isPartIcmp5h()) valor = "7";
			if(forma2017.isPartIcmp5i()) valor = "8";
			if(forma2017.isPartIcmp5j()) valor = "9";
			if(forma2017.isPartIcmp5k()) valor = "10";
			if(forma2017.isPartIcmp5l()) valor = "44";
			if(forma2017.isPartIcmp5m()) valor = "11";
			if(forma2017.isPartIcmp5n()) valor = "12";
			if(forma2017.isPartIcmp5o()) valor = "38";
			if(forma2017.isPartIcmp5p()) valor = "13";
			if(forma2017.isPartIcmp5q()) valor = "16";
			if(forma2017.isPartIcmp5r()) valor = "19";
			if(forma2017.isPartIcmp5s()) valor = "20";
			if(forma2017.isPartIcmp5t()) valor = "21";
			if(forma2017.isPartIcmp5u()) valor = "24";
			if(forma2017.isPartIcmp5v()) valor = "25";
			if(forma2017.isPartIcmp5w()) valor = "26";
			if(forma2017.isPartIcmp5x()) valor = "27";
			if(forma2017.isPartIcmp5y()) valor = "42";
			if(forma2017.isPartIcmp5z()) valor = "43";
		}
		return valor;
	}
	
}
