
/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios.exportacionBeneficiarios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios.ExportacionBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.util.ArchivosCSVUtil;
import com.indeval.portalinternacional.presentation.util.ArchivosUtil;
import com.indeval.portalinternacional.presentation.util.ConstantesArchivos;

/**
 * @author Pablo Balderas
 *
 */
public class ExportacionBeneficiariosController extends ControllerBase {

    /** Lista con los diferentes formatos para los diferentes */
    private List<SelectItem> formatosBeneficiarios;
	
	/** Formato a exportar */
	private String formato;
    
	/** Fecha de inciio para la exportacion */
	private Date fechaInicioExportacion;
	
	/** Fecha de fin para la exportacion */
	private Date fechaFinExportacion;
	
    /** Bandera que indica el inicio */
    private boolean banderaInicio = true;
	
    /** Total de registros exportados */
    private Long totalRegistrosExportados;
    
    /** Servicio para realizar la exportacion */
    private ExportacionBeneficiariosService exportacionBeneficiariosService;
    
    /** Numero de registros por archivo */
    private static final int REGISTROS_POR_ARCHIVO = 5000;
    
    /**
     * Inicializa la pantalla.
     * 
     * @return nulo
     */
    public String getInit() {
        if (this.banderaInicio) {
            initListaFormatos();
            banderaInicio = false;
        }
        return null;
    }
	
    /**
     * Metodo que limpia la pantalla
     * @return
     */
    public String limpiar() {
        this.banderaInicio = true;
        initListaFormatos();
        formato = null;
        fechaInicioExportacion = null;
        fechaFinExportacion = null;
        return BeneficiariosConstantes.PANTALLA_EXPORTACION_BENEFICIARIOS;
    }
    
    /**
     * Realiza la exportacion de beneficiarios
     * @param event Evento de 
     */
    public void exportarBeneficiarios(final ActionEvent event) {
    	
    	
    	//Valida que el parametro formato haya sido capturado
    	if(StringUtils.isBlank(formato) || "-1".equals(formato)) {
    		this.addErrorMessage("El campo Formato es un dato obligatorio.");
    		return;
    	}
    	
    	//Valida las fechas
    	if((null == fechaInicioExportacion && null != fechaFinExportacion) || 
    			(null != fechaInicioExportacion && null == fechaFinExportacion)) {
    		this.addErrorMessage("La fecha de carga debe incluir fecha de inicio y fecha de fin.");
    		return;
    	}
    	
    	//Prepara las fechas
    	java.sql.Date fechaInicio = 
    		(null != fechaInicioExportacion) ? new java.sql.Date(fechaInicioExportacion.getTime()) : null;
    	java.sql.Date fechaFin =
			(null != fechaFinExportacion) ? new java.sql.Date(fechaFinExportacion.getTime()) : null;
    	
    	//Ejecuta la llamada al servicio
    	List<String> registros = this.exportacionBeneficiariosService.obtenerRegistrosLayauts(formato, fechaInicio, fechaFin);
    	
    	//Si la consulta arroja resultados, continua el proceso
    	if(null != registros && !registros.isEmpty()) {
        	//Coloca en pantalla un mensaje con el numero de registros exportados
        	this.addMessage("Numero de registros exportados: " + registros.size());
        	//Separa la lista de resultados en sublistas
        	List<List<String>> registrosSeparados = ListUtils.partition(registros, REGISTROS_POR_ARCHIVO);

        	//Crea un directorio temporal donde colocar los archivos csv
        	StringBuilder directorioTemporal = new StringBuilder();
        	directorioTemporal.append(System.getProperty(ConstantesArchivos.JAVA_IO_TMPDIR));
    		directorioTemporal.append(ArchivosCSVUtil.crearNombreDirectorio(formato));
    		
        	if(!ArchivosUtil.existeArchivoDirectorio(directorioTemporal.toString())) {
        		ArchivosUtil.crearDirectorio(directorioTemporal.toString());
        	}
        	
        	//Recorre la lista de registros separados
        	for (List<String> list : registrosSeparados) {
    			//Por cada lista, se crea un archivo temporal CSV
        		ArchivosCSVUtil.crearArchivoCSV(directorioTemporal.toString(), list, headers(formato));
    		}
        	
        	//Se genera el archivo zip
        	ByteArrayOutputStream baos =
        		ArchivosUtil.comprimirDirectorioAsByteArrayOutputStream(Paths.get(directorioTemporal.toString()));
        	
        	//Coloca el archivo en la respuesta
            HttpServletResponse response = null;
            OutputStream responseOutputStream = null;
            try {
                response = (HttpServletResponse) (getFacesContext().getExternalContext().getResponse());
                response.reset();
                response.setHeader("refresh", "1");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + formato + ConstantesArchivos.EXTENSION_ARCHIVO_ZIP + "\"");
                response.setContentType(ConstantesArchivos.CONTENT_TYPE_ZIP);
                //response.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("ISO_8859_1");
                responseOutputStream = response.getOutputStream();
                responseOutputStream.write(baos.toByteArray());
                responseOutputStream.flush();
                responseOutputStream.close();
                getFacesContext().responseComplete();
            }
            catch (IOException e) {
                e.printStackTrace();
                addErrorMessage("Ocurrio un error al generar el archivo de exportacion de beneficiarios.");
            }    		
    	}
    	else {
        	this.addMessage("No existen registros a exportar con los parametros utilizados.");
    	}
    }

    /**
     * Inicializa la lista de formatos
     */
    private void initListaFormatos() {
        formatosBeneficiarios = new ArrayList<SelectItem>();
        formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN, BeneficiariosConstantes.FORMATO_W8_BEN));
        formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN_E, BeneficiariosConstantes.FORMATO_W8_BEN_E));
        formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY, BeneficiariosConstantes.FORMATO_W8_IMY));
        formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W9, BeneficiariosConstantes.FORMATO_W9));
    }

    private String headers(String formato) {
    	String headers = "";
		//StringBuilder para cada registro
		StringBuilder columnHeader = null;
		//W8-BEN
		if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(formato)) {
			columnHeader = new StringBuilder();
			columnHeader.append("ACCOUNT_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CLIENT_ID" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NAME1" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ORG_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_INCAREOF" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_POBOX" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAILING_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("US_TIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("FOREIGN_TAX_IDENTIFYING" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NOFTIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DATE_OF_BIRTH" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INCOME_TYPE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_EXPLANATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_PROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_RATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("REFERENCE_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SIGNATURE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PRINT_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CAPACITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DATE_SIGNED" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BO_IND" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INSTITUCION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CUSTODIO" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("U.O.I." + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ADP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			headers = columnHeader.toString();
		}
		else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(formato)) {
			columnHeader = new StringBuilder();
			columnHeader.append("ACCOUNT_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CLIENT_ID" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CHAPTER_3" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CHAPTER_4" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NAME1" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DE_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("HYBRID_TREATY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ORG_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_INCAREOF" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_POBOX" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAILING_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("US_TIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_SSN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("FOREIGN_TAX_IDENTIFYING" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NOFTIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PART_I_GIIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LOB_OTHERPROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LOB_PROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INCOME_TYPE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_EXPLANATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_PROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_RATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("REFERENCE_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_INCAREOF" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_POBOX" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH__ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_GIIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SIGNATURE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PRINT_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CAPACITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DATE_SIGNED" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DET_LETTER_DATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("AFFIL_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BANKRUPTCY_DATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_GIIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_STATUS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_TRUSTEE_CLASSIFICATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SPONSOR_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("STARTUP_DATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SEC_EXCHANGE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE9A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE9B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE14A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE14B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE14C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE22" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE23" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE25A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE25B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE25C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE26" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE26_MODEL1" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE26_MODEL2" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE27" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE28A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE28B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29F" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE31" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE32" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE34" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE35" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE36" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE37A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE37B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE38" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE39" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE40A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE40B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE40C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE41" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE43" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INSTITUCION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CUSTODIO" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("U.O.I." + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ADP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			headers = columnHeader.toString();
        }
		else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(formato)) {
			columnHeader = new StringBuilder();
			columnHeader.append("ACCOUNT_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CLIENT_ID" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CHAPTER_3" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CHAPTER_4" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NAME1" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DE_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ORG_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_INCAREOF" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_POBOX" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAILING_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("MAIL_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("US_TIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_QI_EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_SSN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_WP_EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TIN_TYPE_WT_EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NOFTIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PART_I_GIIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("TREATY_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LOB_OTHERPROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LOB_PROVISION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("REFERENCE_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_INCAREOF" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_POBOX" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH__ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BRANCH_GIIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SIGNATURE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PRINT_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DATE_SIGNED" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("QDD_CLASSIFICATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("AFFIL_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("BANKRUPTCY_DATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_COUNTRY_CD" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_STATUS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("IGA_TRUSTEE_CLASSIFICATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SPONSOR_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("STARTUP_DATE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SEC_EXCHANGE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE14" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15F" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15G" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15H" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE15I" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE16A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE17E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE18F" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE19F" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE20" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE21E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE22" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE23B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE23C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE24C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE25" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE26" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE27B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE28" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE29" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30_MODEL1" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30_MODEL2" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE30C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE31" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE32" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33C" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33D" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33E" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE33F" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE34" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE35" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE36" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE37A" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE37B" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE38" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE39" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE40" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("LINE42" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INSTITUCION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CUSTODIO" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("U.O.I." + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ADP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			headers = columnHeader.toString();
        }
		
		else if (BeneficiariosConstantes.FORMATO_W9.equals(formato)) {
			columnHeader = new StringBuilder();
			columnHeader.append("ACCOUNT_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CLIENT_ID" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DE_NAME" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("FEDERAL_TAX_CLASSIFICATION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("EXEMPT_PAYEE_CODE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("EXEMPT_FATCA_REPORTING_CODE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ADDRESS" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_CITY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_STATE_PROV" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_ZIP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("PR_COUNTRY" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("REFERENCE_NUMBER" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SSN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("EIN" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("SIGNATURE" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("DATE_SIGNED" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("INSTITUCION" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("CUSTODIO" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("U.O.I." + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			columnHeader.append("ADP" + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
			headers = columnHeader.toString();
        }
    	return  headers;
    }
	/**
	 * Metodo para obtener el atributo formatosBeneficiarios
	 * @return El atributo formatosBeneficiarios
	 */
	public List<SelectItem> getFormatosBeneficiarios() {
		return formatosBeneficiarios;
	}

	/**
	 * Metodo para establecer el atributo formatosBeneficiarios
	 * @param formatosBeneficiarios El valor del atributo formatosBeneficiarios a establecer.
	 */
	public void setFormatosBeneficiarios(List<SelectItem> formatosBeneficiarios) {
		this.formatosBeneficiarios = formatosBeneficiarios;
	}

	/**
	 * Metodo para obtener el atributo fechaInicioExportacion
	 * @return El atributo fechaInicioExportacion
	 */
	public Date getFechaInicioExportacion() {
		return fechaInicioExportacion;
	}

	/**
	 * Metodo para establecer el atributo fechaInicioExportacion
	 * @param fechaInicioExportacion El valor del atributo fechaInicioExportacion a establecer.
	 */
	public void setFechaInicioExportacion(Date fechaInicioExportacion) {
		this.fechaInicioExportacion = fechaInicioExportacion;
	}

	/**
	 * Metodo para obtener el atributo fechaFinExportacion
	 * @return El atributo fechaFinExportacion
	 */
	public Date getFechaFinExportacion() {
		return fechaFinExportacion;
	}

	/**
	 * Metodo para establecer el atributo fechaFinExportacion
	 * @param fechaFinExportacion El valor del atributo fechaFinExportacion a establecer.
	 */
	public void setFechaFinExportacion(Date fechaFinExportacion) {
		this.fechaFinExportacion = fechaFinExportacion;
	}

	/**
	 * Metodo para obtener el atributo banderaInicio
	 * @return El atributo banderaInicio
	 */
	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	/**
	 * Metodo para establecer el atributo banderaInicio
	 * @param banderaInicio El valor del atributo banderaInicio a establecer.
	 */
	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	/**
	 * Metodo para obtener el atributo formato
	 * @return El atributo formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * Metodo para establecer el atributo formato
	 * @param formato El valor del atributo formato a establecer.
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * Metodo para obtener el atributo totalRegistrosExportados
	 * @return El atributo totalRegistrosExportados
	 */
	public Long getTotalRegistrosExportados() {
		return totalRegistrosExportados;
	}

	/**
	 * Metodo para establecer el atributo totalRegistrosExportados
	 * @param totalRegistrosExportados El valor del atributo totalRegistrosExportados a establecer.
	 */
	public void setTotalRegistrosExportados(Long totalRegistrosExportados) {
		this.totalRegistrosExportados = totalRegistrosExportados;
	}

	/**
	 * Metodo para establecer el atributo exportacionBeneficiariosService
	 * @param exportacionBeneficiariosService El valor del atributo exportacionBeneficiariosService a establecer.
	 */
	public void setExportacionBeneficiariosService(ExportacionBeneficiariosService exportacionBeneficiariosService) {
		this.exportacionBeneficiariosService = exportacionBeneficiariosService;
	}
	
}
