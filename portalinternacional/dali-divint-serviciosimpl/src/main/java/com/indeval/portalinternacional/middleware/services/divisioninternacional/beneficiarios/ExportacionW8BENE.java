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

import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;

/**
 * @author Gustavo Vazquez
 *
 */
public class ExportacionW8BENE {

	/**
	 * Metodo que procesa la lista de beneficiarios para obtener los datos a exportar.
	 * @param beneficiarios
	 * @param mapaCustodios
	 * @param formatoW8Service
	 * @return Lista
	 */
	public static List<String> procesarBeneficiariosW8BENE(List<Beneficiario> beneficiarios, 
			Map<Long, String> mapaCustodios,
			FormatoW8Service formatoW8Service) {
		
		//Lista de registros procesados
		ArrayList<String> registrosProcesados = new ArrayList<>();
		//StringBuilder para cada registro
		StringBuilder registroCVS = null;
		//Recorre la lista de beneficiarios
		for (Beneficiario beneficiario : beneficiarios) {
			//Valida que el origen del beneficiario sea Dali
			if(StringUtils.isNotBlank(beneficiario.getOrigen()) && 
				BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI.equals(beneficiario.getOrigen())) {
				//Inicializa el objeto
				registroCVS = new StringBuilder();
				//Cadenas auxiliares
				String aux;
				StringBuilder sbAux;
				//Obtiene los campos del xml
				FormaW8BENE forma =
					formatoW8Service.obtenerCamposFormatoW8BENE(beneficiario.getFormatoW8BENE().getIdCamposFormatoW8bene());
	            forma.construyeBean(beneficiario, true);
			
	            //Valida que el objeto que guarda la forma no sea nuelo
	            if(null != forma) {
	    			//ACCOUNT_NUMBER
	                registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//CLIENT_ID            
	                registroCVS.append(beneficiario.getUoiNumber() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	                //CHAPTER_3
	                registroCVS.append(entityTypes(forma) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//CHAPTER_4
	                registroCVS.append(fatcaStatus(forma) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//NAME1
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp1()) ? forma.getPartIcmp1().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//DE_NAME//
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp3()) ? forma.getPartIcmp3().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//HYBRID_TREATY
	    			//Si las 2 banderas son falsas
	    			if(!forma.isPartIcmp4l() && !forma.isPartIcmp4m()) {
	    				aux = StringUtils.EMPTY;
	    			}
	    			//Si la primer bandera es verdadera y la segunda falsa, coloca Yes
	    			if(forma.isPartIcmp4l() && !forma.isPartIcmp4m()) {
	    				aux = BeneficiariosConstantes.YES;
	    			}
	    			//Si la primer bandera es falsa y la segunda verdadera, coloca No
	    			if(!forma.isPartIcmp4l() && forma.isPartIcmp4m()) {
	    				aux = BeneficiariosConstantes.NO;
	    			}
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//ORG_COUNTRY_CD	
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp2()) ? forma.getPartIcmp2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_ADDRESS	
	    			registroCVS.append(beneficiario.getDomicilioW8Normal().getCalleNumeros().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_CITY
	    			registroCVS.append(beneficiario.getDomicilioW8Normal().getCityTown().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_COUNTRY_CD	
	    			registroCVS.append(beneficiario.getDomicilioW8Normal().getCountry() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_INCAREOF
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_POBOX	
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_STATE_PROV	
	    			registroCVS.append(beneficiario.getDomicilioW8Normal().getStateProvince().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PR_ZIP	
	    			registroCVS.append(beneficiario.getDomicilioW8Normal().getPostalCode() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			
	    			if(null != beneficiario.getDomicilioW8Correo()) {
	    				//MAILING_ADDRESS
	    				registroCVS.append(beneficiario.getDomicilioW8Correo().getCalleNumeros().replace(",", StringUtils.EMPTY)  + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_CITY
	    				registroCVS.append(beneficiario.getDomicilioW8Correo().getCityTown().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_COUNTRY_CD
	    				registroCVS.append(beneficiario.getDomicilioW8Correo().getCountry() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_STATE_PROV
	    				registroCVS.append(beneficiario.getDomicilioW8Correo().getStateProvince().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_ZIP
	    				registroCVS.append(beneficiario.getDomicilioW8Correo().getPostalCode() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			}
	    			else {
	    				//MAILING_ADDRESS
	    				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_CITY
	    				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_COUNTRY_CD
	    				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_STATE_PROV
	    				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    				//MAIL_ZIP
	    				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			}

	    			//US_TIN
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp8()) ? forma.getPartIcmp8() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TIN_TYPE_EIN
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TIN_TYPE_SSN
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//FOREIGN_TAX_IDENTIFYING
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp9b2()) ? forma.getPartIcmp9b2() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//NOFTIN
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PART_I_GIIN
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp9a2()) ? forma.getPartIcmp9a2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TREATY_COUNTRY_CD
	    			aux = StringUtils.isNotBlank(forma.getPartIIIcmp14a1()) ? forma.getPartIIIcmp14a1().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LOB_OTHERPROVISION
	    			aux = StringUtils.isNotBlank(forma.getPartIIIcmp14bk()) ? forma.getPartIIIcmp14bk().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LOB_PROVISION
	    			registroCVS.append(lobProvision(forma) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//INCOME_TYPE
	    			aux = StringUtils.isNotBlank(forma.getPartIIIcmp15c()) ? forma.getPartIIIcmp15c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TREATY_EXPLANATION
	    			sbAux =  new StringBuilder();
	    			sbAux.append((StringUtils.isNotBlank(forma.getPartIIIcmp15d1()) ? forma.getPartIIIcmp15d1().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO);
	    			sbAux.append((StringUtils.isNotBlank(forma.getPartIIIcmp15d2()) ? forma.getPartIIIcmp15d2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO);
	    			sbAux.append((StringUtils.isNotBlank(forma.getPartIIIcmp15d3()) ? forma.getPartIIIcmp15d3().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO);
	    			registroCVS.append(sbAux.toString() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TREATY_PROVISION
	    			aux = StringUtils.isNotBlank(forma.getPartIIIcmp15a()) ? forma.getPartIIIcmp15a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TREATY_RATE
	    			aux = StringUtils.isNotBlank(forma.getPartIIIcmp15b()) ? forma.getPartIIIcmp15b() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//REFERENCE_NUMBER
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp10()) ? forma.getPartIcmp10() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TODO PENDIENTES DEFINICION - Quitar comentario cuando se definan
	    			//BRANCH_ADDRESS
	    			aux = StringUtils.isNotBlank(forma.getPartIIcmp12a()) ? forma.getPartIIcmp12a().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_INCAREOF
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_POBOX
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_STATE_PROV
	    			aux = StringUtils.isNotBlank(forma.getPartIIcmp12b()) ? forma.getPartIIcmp12b().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_CITY
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH__ZIP
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_COUNTRY_CD
	    			aux = StringUtils.isNotBlank(forma.getPartIIcmp12c()) ? forma.getPartIIcmp12c().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BRANCH_GIIN
	    			aux = StringUtils.isNotBlank(forma.getPartIIcmp13()) ? forma.getPartIIcmp13().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//TODO PENDIENTES DEFINICION - Quitar comentario cuando se definan
	    			//SIGNATURE
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//PRINT_NAME
	    			aux = StringUtils.isNotBlank(forma.getPrintName()) ? forma.getPrintName().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//CAPACITY
	    			aux = forma.isPartXXX() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//DATE_SIGNED
	    			registroCVS.append((null != beneficiario.getFechaFormato()) ?
						DateFormatUtils.format(beneficiario.getFechaFormato(), "MM/dd/yyyy") + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS:
							StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			
	    			//TODO PENDIENTES DEFINICION - Quitar comentario cuando se definan
	    			
	    			//DET_LETTER_DATE
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//AFFIL_NAME
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//BANKRUPTCY_DATE
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//IGA_COUNTRY_CD
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//IGA_GIIN
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
	    			//LINE9A
	    		     aux = StringUtils.isNotBlank(forma.getPartIcmp9a2()) ? forma.getPartIcmp9a2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY; 
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE9B 
	    			aux = StringUtils.isNotBlank(forma.getPartIcmp9b2()) ? forma.getPartIcmp9b2().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY; 
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE14A
	    			aux = forma.isPartIIIcmp14a() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
	    			registroCVS.append(aux+ BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE14B
	    			aux = forma.isPartIIIcmp14b() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE14C
	    			aux = forma.isPartIIIcmp14c() ? BeneficiariosConstantes.YES : BeneficiariosConstantes.NO;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE17A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE17B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE18
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE19
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE21
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE22
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE23
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE24A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE24B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE24C
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE24D
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE25A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE25B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE25C
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE26
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE26_MODEL1
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE26_MODEL2
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE27
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE28A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE28B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29C
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29D
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29E
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE29F
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE30
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE31
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE32
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE33
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
	    			//LINE40A
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE40B
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE40C
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE41
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//LINE43
	    			registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			
	    			//TODO PENDIENTES DEFINICION - Quitar comentario cuando se definan
	    			
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
	    			aux = StringUtils.isNotBlank(beneficiario.getUoiNumber()) ? beneficiario.getUoiNumber() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			//ADP
	    			aux = StringUtils.isNotBlank(beneficiario.getAdp()) ? beneficiario.getAdp() : StringUtils.EMPTY;
	    			registroCVS.append(aux + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
	    			
	    			//Agrega a registros procesados
	    			registrosProcesados.add(registroCVS.toString());
	            }				
			}
		}
		return registrosProcesados;
	}
	
	private static String  entityTypes(FormaW8BENE forma) {
		String idEntityTypes = StringUtils.EMPTY;
		if(forma.isPartIcmp4a()) {
        	idEntityTypes ="2";
        }
        else if(forma.isPartIcmp4b()) {
        	idEntityTypes ="5";
        }
        else if(forma.isPartIcmp4c()) {
        	idEntityTypes ="3";
        }
        else if(forma.isPartIcmp4d()) {
        	idEntityTypes ="20";
        }
        else if(forma.isPartIcmp4e()) {
        	idEntityTypes ="18";
        }
        else if(forma.isPartIcmp4f()) {
        	idEntityTypes ="19";
        }
        else if(forma.isPartIcmp4g()) {
        	idEntityTypes ="4";
        }
        else if(forma.isPartIcmp4h()) {
        	idEntityTypes ="7";
        }
        else if(forma.isPartIcmp4i()) {
        	idEntityTypes ="6";
        }
        else if(forma.isPartIcmp4j()) {
        	idEntityTypes ="14";
        }
        else if(forma.isPartIcmp4k()) {
        	idEntityTypes ="13";
        }
        else if(forma.isPartIcmp4l()) {
        	idEntityTypes ="12";
        }
        else //NONE
        	idEntityTypes ="43";
		
		return idEntityTypes;
	}
	
	private static String  fatcaStatus (FormaW8BENE forma) {
		String idFatcaStatus = StringUtils.EMPTY;
		 if(forma.isPartIcmp5a()) {
         	idFatcaStatus = "1";
         }
			 else if(forma.isPartIcmp5b()) {
         	idFatcaStatus = "2";
         }
			 else if(forma.isPartIcmp5c()) {
         	idFatcaStatus = "3";
         }
			 else if(forma.isPartIcmp5d()) {
         	idFatcaStatus = "4";
         }
			 else if(forma.isPartIcmp5e()) {
         	idFatcaStatus = "5";
         }
			 else if(forma.isPartIcmp5f()) {
         	idFatcaStatus = "6";
         }
			 else if(forma.isPartIcmp5g()) {
         	idFatcaStatus = "7";
         }
			 else if(forma.isPartIcmp5h()) {
         	idFatcaStatus = "8";
         }
			 else if(forma.isPartIcmp5i()) {
         	idFatcaStatus = "9";
         }
			 else if(forma.isPartIcmp5j()) {
         	idFatcaStatus = "10";
         }
			 else if(forma.isPartIcmp5k()) {
         	idFatcaStatus = "44";
         }
			 else if(forma.isPartIcmp5l()) {
         	idFatcaStatus = "11";
         }
			 else if(forma.isPartIcmp5m()) {
         	idFatcaStatus = "12";
         }
			 else if(forma.isPartIcmp5n()) {
         	idFatcaStatus = "13";
         }
			 else if(forma.isPartIcmp5o()) {
         	idFatcaStatus = "14";
         }
			 else if(forma.isPartIcmp5p()) {
         	idFatcaStatus = "15";
         }
			 else if(forma.isPartIcmp5q()) {
         	idFatcaStatus = "16";
         }
			 else if(forma.isPartIcmp5r()) {
         	idFatcaStatus = "17";
         }
			 else if(forma.isPartIcmp5s()) {
         	idFatcaStatus = "18";
         }
			 else if(forma.isPartIcmp5t()) {
         	idFatcaStatus = "19";
         }
			 else if(forma.isPartIcmp5u()) {
         	idFatcaStatus = "20";
         }
			 else if(forma.isPartIcmp5v()) {
         	idFatcaStatus = "21";
         }
			 else if(forma.isPartIcmp5w()) {
         	idFatcaStatus = "22";
         }
			 else if(forma.isPartIcmp5x()) {
         	idFatcaStatus = "23";
         }
			 else if(forma.isPartIcmp5y()) {
         	idFatcaStatus = "24";
         }
			 else if(forma.isPartIcmp5z()) {
         	idFatcaStatus = "25";
         }
			 else if(forma.isPartIcmp5aa()) {
         	idFatcaStatus = "26";
         }
			 else if(forma.isPartIcmp5ab()) {
         	idFatcaStatus = "27";
         }
			 else if(forma.isPartIcmp5ac()) {
         	idFatcaStatus = "41";
         }
			 else if(forma.isPartIcmp5ad()) {
         	idFatcaStatus = "42";
         }
			 else if(forma.isPartIcmp5ae()) {
				idFatcaStatus = "43";
			 }
			 else if(forma.isPartIcmp5af()) {
			   idFatcaStatus = "87";
          }else //None
         	idFatcaStatus = "99";
		return idFatcaStatus;  
	}
	private static String  lobProvision (FormaW8BENE forma) {
		String idLobprovision = StringUtils.EMPTY;
		if(forma.isPartIIIcmp14ba()) {
			idLobprovision = "2";
		}
		else if(forma.isPartIIIcmp14bb()) {
			idLobprovision = "7";
		}
		else if(forma.isPartIIIcmp14bc()) {
			idLobprovision = "3";
		}
		else if(forma.isPartIIIcmp14bd()) {
			idLobprovision = "8";
		}
		else if(forma.isPartIIIcmp14be()) {
			idLobprovision = "4";
		}
		else if(forma.isPartIIIcmp14bf()) {
			idLobprovision = "9";
		}
		else if(forma.isPartIIIcmp14bg()) {
			idLobprovision = "5";
		}
		else if(forma.isPartIIIcmp14bh()) {
			idLobprovision = "10";
		}
		else if(forma.isPartIIIcmp14bi()) {
			idLobprovision = "6";
		}
		else //Other
			idLobprovision = "11";
		return idLobprovision;
	}
	
}
