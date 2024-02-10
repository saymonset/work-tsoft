package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileDocumentoUpload;

/**
 * Servlet implementation class ObtieneFormatoW8BEN
 */
public class ObtieneFormatoMILA extends HttpServlet implements Constantes{
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName="java:global/dali_divint_services/dali_divint_services_ejb/ejb.controlBeneficiariosService!com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService")
	private ControlBeneficiariosService controlBeneficiariosService;
    /** Mapa que contiene la lista de custodios para la tabla */
	private Map<Long, String> mapaCustodios;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map<Long, String> mapaTipoBeneficiario;
	
	private static final Logger logger = LoggerFactory.getLogger(ObtieneFormatoMILA.class);
	
	DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	private Map<Long, Integer> versionArchivo;
	
	@Override
	public void init() throws ServletException {
		 versionArchivo = new HashMap<Long, Integer>(3);
		 inicializaCustodios();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String funcion = request.getParameter("funcion");
		
		if(!StringUtils.isBlank(funcion)){
			funcion = funcion.trim();
			if("descargarDocumento".equals(funcion)){
				try{
					procesaDescargaDocumento(request, response);
				} catch(ServletException ex){
					logger.error(ex.getMessage());
				} catch(IOException ex){
					logger.error(ex.getMessage());
				} catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
			else if("generarFormatoMila".equals(funcion)){
				try{
					procesaFormatoMila(request, response);
				} catch(ServletException ex){
					logger.error(ex.getMessage());
				} catch(IOException ex){
					logger.error(ex.getMessage());
				} catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
			else if("generarFormatoMilaCustodio".equals(funcion)){
				try{
					procesaFormatoMilaCustodio(request, response);
				} catch(ServletException ex){
					logger.error(ex.getMessage());
				} catch(IOException ex){
					logger.error(ex.getMessage());
				} catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
			else{
				logger.error("Error, la funcion solicitada no existe funcion=: [" + funcion + "]");
			}
		}
		else{
			logger.error("Error al obtener el tipo de solicitud deseada funcion=: [" + funcion + "]");
		}
	}
	
	private void procesaDescargaDocumento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreDocumento = request.getParameter("nombreDocumento");
		@SuppressWarnings("unchecked")
		List<FileDocumentoUpload> files = (List<FileDocumentoUpload>)request.getSession(false).getAttribute("listaArchivosBeneficiariosMila");  
		
		if(StringUtils.isBlank(nombreDocumento)|| files==null || files.size()==0){
			logger.error("Error, los parametros para descargar el archivo no son correctos nombreDocumento=: [" + nombreDocumento + "]");
			logger.error("Error, los parametros para descargar el archivo no son correctos files=: [" + files + "]");
		}
		else{
			FileDocumentoUpload archivo = null;
			for (FileDocumentoUpload fileDocumentoUpload : files) {
				if(fileDocumentoUpload.getName().equals(nombreDocumento)){
					archivo = fileDocumentoUpload;
					break;
				}
			}
			if(archivo!=null){
				response.setHeader("Expires", "0");
		        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		        response.setHeader("Pragma", "public");
		        response.setHeader("Content-Disposition", "attachment; filename=" +  URLEncoder.encode(nombreDocumento,"UTF-8"));
		        response.setContentType(archivo.getMime());
		        OutputStream out = response.getOutputStream();
		        out.write(archivo.getData());
		        out.close();
			}
			else{
				logger.error("Error, no existe el archivo especificado nombreDocumento=: [" + nombreDocumento + "]");
			}
		}
	}
	
	private void procesaFormatoMila(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( controlBeneficiariosService == null ) {
			logger.error("No se puede obtener el servicio");
			return;
		}
		String idBeneficiario = request.getParameter("idBeneficiario");
		
		if( StringUtils.isNotBlank(idBeneficiario) &&
				StringUtils.isNumeric(idBeneficiario)) {
			Long id = Long.valueOf(idBeneficiario);
			Beneficiario beneficiario = null;
			try {
				beneficiario = controlBeneficiariosService.consultaBeneficiarioByIdEliminados(id);
			} catch (Exception e) {
				logger.error("Error al obtener Beneficiario",e);
				return;
			}
			if( beneficiario !=  null ) {
				obtieneBeneficiarios(response, Arrays.asList(new Beneficiario[]{beneficiario}));
			} 
			else {
				logger.error("Error al obtener el id del Beneficiario: [" + idBeneficiario + "]");
			}
		}
	}
	
	private void procesaFormatoMilaCustodio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<Beneficiario> beneficarios = (List<Beneficiario>) request.getAttribute("beneficiarios");
		try {
			if( beneficarios != null) {
				obtieneBeneficiarios(response, beneficarios);
			}
		 } catch (Exception e) {
				logger.error("Error al generar el archivo txt  para el beneficiario", e);
		}
	}
	
	
	
	private void obtieneBeneficiarios(HttpServletResponse response, List<Beneficiario> beneficiarios) throws UnsupportedEncodingException {
		String nombreArchivo = "ERROR.out";
		List<String> registrosFormato = null;
		if (beneficiarios != null && !beneficiarios.isEmpty()) {
			Long idCustodio = beneficiarios.get(0).getIdCuentaNombrada();
			List<Beneficiario> beneficiariosMila = new ArrayList<Beneficiario>();
			for (Beneficiario beneficiario : beneficiarios) {
				if (beneficiario.getTipoFormato().equals("MILA")) {
					beneficiariosMila.add(beneficiario);
				}	
			}
			if(!beneficiariosMila.isEmpty()) {
				Integer version = versionArchivo.get(idCustodio);
				if (version == null) {
					version = 0;
				}
				version = version+1;
				versionArchivo.put(idCustodio, version);
				nombreArchivo = generaNombreArchivo(idCustodio, version);
				registrosFormato = new ArrayList<String>(beneficiarios.size()+1);
				registrosFormato.add(generaCadenaCabeceraBenefMila(beneficiariosMila.size(), nombreArchivo));
				int numeroBeneficiario = 1;
				for (Beneficiario beneficiario : beneficiariosMila) {
					registrosFormato.add(generaCadenaDatosBenef(beneficiario, numeroBeneficiario++));
				}
				
			}	
					
		}	
		response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(nombreArchivo, "UTF-8"));
        response.setContentType("text/plain");
        
        try {
        	OutputStream out = response.getOutputStream();

        	final PrintStream printStream = new PrintStream(out);
        	if(registrosFormato != null) {
        		for (String registro : registrosFormato) {
    				printStream.print(registro);
    			}
        	}	
        	printStream.close();
        		
		} catch (Exception e) {
			logger.error("Error al generar el archivo txt  para el beneficiario", e);
		}
		
		
	}
	
	private String generaNombreArchivo(Long idCustodio, int version) {
		StringBuffer nombreArchivo = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MILA_INT100_CABECERA);
		nombreArchivo.append(CODIGO_ARCHIVO_MILA_INT100)
			.append(PREFIJO_DE_DEPOSITO_INDEVAL)
			.append(obtieneprefijoDestinoBenef(idCustodio))
			.append(sdf.format(new Date()))
			.append(INDICADOR_EXT_DATOS_ENTRADA_ARCHIVO_MILA)
			.append(StringUtils.leftPad(String.valueOf(version), 2, "0"));
		return nombreArchivo.toString();
	}
	private String generaCadenaCabeceraBenefMila(Integer cantidadTotalBenef, String nombreArchivo) {
		StringBuffer cadenaCabeceraMila = new StringBuffer();
		SimpleDateFormat sdfFechaAut = new SimpleDateFormat(DATE_FORMAT_MILA_FECHA_AUT);
		cadenaCabeceraMila.append(StringUtils.leftPad("1", 8, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils.leftPad(TIPO_REGISTRO_CABECERA_MILA, 2, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						//Nombre del archivo MILA
						.append(MARCADOR_CADENA_MILA)
						.append(nombreArchivo)
						.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						//Indicador de entrada mila I
						.append(MARCADOR_CADENA_MILA)
						.append(INDICADOR_DATOS_ENTRADA_ARCHIVO_MILA)
						.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						//fecha autorizacion
						.append(sdfFechaAut.format(new Date()))
						.append(SEPARADOR_ARCHIVO_MILA)
						// total de beneficiarios
						.append(StringUtils.leftPad(String.valueOf(cantidadTotalBenef + 1), 8, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						// total de beneficiarios
						.append(StringUtils.leftPad(cantidadTotalBenef.toString(), 6, "0"))
						.append(SALTO_LINEA_ARCHIVO_MILA); 
		return cadenaCabeceraMila.toString();
						
	}
	
	private String generaCadenaDatosBenef(Beneficiario beneficiario, Integer numeroBeneficiario) {
		StringBuffer cadenaBeneficiario = new StringBuffer();
		Integer numeroRegistro = Integer.valueOf(numeroBeneficiario.intValue()+1);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MILA_INT100_CABECERA);
		cadenaBeneficiario.append(StringUtils.leftPad(numeroRegistro.toString(), 8, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(TIPO_REGISTRO_02_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(sdf.format(new Date()))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(beneficiario.getFormatoMILA().getIdentificadorMILA())
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(MARCADOR_CADENA_MILA)
						.append(convierteTipoBenefMila(beneficiario.getPersonaFisica().booleanValue()))
						.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils.leftPad(beneficiario.getFormatoMILA().getTipoDocumentoIndentidad().getCodigoMila().toString(), 8, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(MARCADOR_CADENA_MILA)
						.append(beneficiario.getFormatoMILA().getNumeroDocumento())
						.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(MARCADOR_CADENA_MILA);
						if(beneficiario.getPersonaFisica()){
							 cadenaBeneficiario.append(beneficiario.getNombres())
							.append(" ")
							.append(beneficiario.getApellidoPaterno());
							if(!StringUtils.isBlank(beneficiario.getApellidoMaterno())){
								cadenaBeneficiario.append(" ")
								.append(beneficiario.getApellidoMaterno());
							}
						}
						else{
							 cadenaBeneficiario.append(beneficiario.getRazonSocial());
						}
						cadenaBeneficiario.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils.leftPad(beneficiario.getFormatoMILA().getPaisNacionalidad().getCodigoMila().toString(), 10, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(MARCADOR_CADENA_MILA)
						.append(convierteValorResidente(beneficiario.getFormatoMILA().getResidente()))
						.append(MARCADOR_CADENA_MILA)
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(obtieneDomicilioBenef(beneficiario))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils.leftPad(beneficiario.getFormatoMILA().getPaisResidencia().getCodigoMila().toString(), 10, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils
								.leftPad(beneficiario.getFormatoMILA().getCodigoEstadoEntidad() != null ? 
										beneficiario.getFormatoMILA().getCodigoEstadoEntidad().getIdCodigoEstado().toString() : "33", 10, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						
						.append(StringUtils
								.leftPad(beneficiario.getFormatoMILA().getCodigoEstadoEntidad() != null ? 
										beneficiario.getFormatoMILA().getCodigoEstadoEntidad().getIdCodigoEstado().toString() : "33", 10, "0"))
						.append(SEPARADOR_ARCHIVO_MILA)
						.append(generaDatosPersonaMoral(beneficiario))
						.append(generaDatosOpcionales(beneficiario))
						// TODO revisar con el usuario que valor poner para el PARTICIPANTE COLOMBIANO CREADOR de tipo numerico
						.append(SALTO_LINEA_ARCHIVO_MILA); 
		
		return cadenaBeneficiario.toString(); 
	}
	
	private String obtieneDomicilioBenef(Beneficiario beneficiario) {
		StringBuffer cadenaBeneficiarioDom = new StringBuffer();
		cadenaBeneficiarioDom.append(MARCADOR_CADENA_MILA)
			.append(beneficiario.getDomicilioMILA().getStreet())
			.append(" ")
			.append(beneficiario.getDomicilioMILA().getOuterNumber())
			.append(StringUtils.isNotBlank(beneficiario.getDomicilioMILA().getInteriorNumber()) ? " " + beneficiario.getDomicilioMILA().getInteriorNumber() : "")
			.append(SEPARADOR_ARCHIVO_MILA)
			.append(" ")
			.append(beneficiario.getDomicilioMILA().getCityTown())
			.append(SEPARADOR_ARCHIVO_MILA)
			.append(" ")
			.append(beneficiario.getDomicilioMILA().getPostalCode())
			.append(MARCADOR_CADENA_MILA);
		return cadenaBeneficiarioDom.toString();
	}
	
	private String generaDatosPersonaMoral(Beneficiario beneficiario) {
		StringBuffer cadenaBenefMoral = new StringBuffer();
		if (!beneficiario.getPersonaFisica()) {
			cadenaBenefMoral.append(StringUtils.leftPad(beneficiario.getFormatoMILA().getCaracterEntidad().getIdTipoEmpresa().toString(),10,"0"))
							.append(SEPARADOR_ARCHIVO_MILA)
							.append(StringUtils.leftPad(beneficiario.getFormatoMILA().getSectorEconomico().getIdSectorEconomico().toString(),10,"0"));
		}
		else {
			cadenaBenefMoral.append(SEPARADOR_ARCHIVO_MILA);
		}
		cadenaBenefMoral.append(SEPARADOR_ARCHIVO_MILA);
		return cadenaBenefMoral.toString();
		
	}
	
	private String generaDatosOpcionales(Beneficiario beneficiario) {
		StringBuffer datosOpcionales = new StringBuffer();
		if (beneficiario.getFormatoMILA().getDireccionEmail() != null) {
			datosOpcionales.append(MARCADOR_CADENA_MILA)
							.append(beneficiario.getFormatoMILA().getDireccionEmail())
							.append(MARCADOR_CADENA_MILA);
		}
		datosOpcionales.append(SEPARADOR_ARCHIVO_MILA);
		if (beneficiario.getFormatoMILA().getTelefono() != null) {
			datosOpcionales.append(MARCADOR_CADENA_MILA)
							.append(beneficiario.getFormatoMILA().getTelefono())
							.append(MARCADOR_CADENA_MILA);
		}
		datosOpcionales.append(SEPARADOR_ARCHIVO_MILA);
		if (beneficiario.getFormatoMILA().getFax() != null) {
			datosOpcionales.append(MARCADOR_CADENA_MILA)
							.append(beneficiario.getFormatoMILA().getFax())
							.append(MARCADOR_CADENA_MILA);
		}
		datosOpcionales.append(SEPARADOR_ARCHIVO_MILA);
		return datosOpcionales.toString();
	}
	
	private String obtieneprefijoDestinoBenef(Long idCustodio) {
		String custodio = mapaCustodios.get(idCustodio);
		return StringUtils.substring(custodio, 0, 3);
		
	}
	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {
		List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();
		mapaCustodios = new HashMap<Long,String>();
		mapaTipoBeneficiario = new HashMap<Long, String>(1);

		for(Object[] custodioActual : lista) {
            mapaCustodios.put((Long)custodioActual[0], (String)custodioActual[1]);
		}
		
		List<CustodioTipoBenef> listaTiposBeneficiarios = controlBeneficiariosService.getListaCustodioTipoBenef();
		//Agregamos todos los custodios que tienen dado de alta el mismo formato
		for (CustodioTipoBenef tipos : listaTiposBeneficiarios) {
				mapaTipoBeneficiario.put(tipos.getTipoBeneficiario().getIdTipoBeneficiario(), tipos.getTipoBeneficiario().getDescTipoBeneficiario().toUpperCase());
		}
		
	}
	
	private String convierteTipoBenefMila(boolean esPersonaFisica) {
		if (esPersonaFisica) {
			return TIPO_PERSONA_FISICA_NATURAL_MILA;
		}
		else {
			return TIPO_PERSONA_FISICA_JURIDICO_MILA;
		}
	}
	
	private String convierteValorResidente(boolean esResidente) {
		if (esResidente) {
			return "S";
		}
		else {
			return "N";
		}
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			doGet(request, response);
		} catch(ServletException ex){
			logger.error(ex.getMessage());
		} catch(IOException ex){
			logger.error(ex.getMessage());
		} catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 */
	public Map<Long, String> getMapaCustodios() {
		return mapaCustodios;
	}

	/**
	 * 
	 * @param mapaCustodios
	 */
	public void setMapaCustodios(Map<Long, String> mapaCustodios) {
		this.mapaCustodios = mapaCustodios;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Long, String> getMapaTipoBeneficiario() {
		return mapaTipoBeneficiario;
	}

	/**
	 * 
	 * @param mapaTipoBeneficiario
	 */
	public void setMapaTipoBeneficiario(Map<Long, String> mapaTipoBeneficiario) {
		this.mapaTipoBeneficiario = mapaTipoBeneficiario;
	}
	
	

}
