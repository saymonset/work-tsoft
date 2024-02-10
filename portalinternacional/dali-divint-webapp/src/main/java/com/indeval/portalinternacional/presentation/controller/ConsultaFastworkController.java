package com.indeval.portalinternacional.presentation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

//import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaFastworkService;
import com.indeval.portalinternacional.middleware.servicios.fo.FastworkMessageFO;
import com.indeval.portalinternacional.middleware.servicios.vo.FastworkMessageVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ConsultaFastworkController extends ControllerBase {

	private ConsultaFastworkService consultaFastworkService;
	
	private List<FastworkMessageVO> results;
	
	private Map<String, String> catTipoMensaje;
	
	private Map<String, String> catMoneda;
	
	private List<String> camposSwiftHeaders;
	
	private FastworkMessageFO fastworkMessagefo;
	
	private String camposswiftMostrar;
	
	private String lstTipoMensajes;
	
	private String lstMonedas;
	
	private String isoMostrado;
	
	private boolean actualizarReporte;
	
	private FastworkMessageVO isoConsultado;
	
	private Map<String, String> params;

	private final static String TITULO_RAW = "MensajeriaHistoricaRAW";
	
	private final static String TITULO_XLS = "ConsultaMensajeriaHistorica";
	
	private final static String TITULO_ISO = "MensajeriaHistoricaISO";
	
//	@PostConstruct
	private void inicializarBean() {
		this.fastworkMessagefo = new FastworkMessageFO();
		
		catTipoMensaje = new LinkedHashMap<>();
		catTipoMensaje.put("","");
		for(String tipoMensaje : lstTipoMensajes.split(",")) {
			catTipoMensaje.put(tipoMensaje, tipoMensaje);
		}
		
		catMoneda = new LinkedHashMap<>();
		catMoneda.put("","");		
		for(String moneda : lstMonedas.split(",")) {
			catMoneda.put(moneda, moneda);
		}
		
		this.paginaVO = new PaginaVO();
		this.paginaVO.setOffset(0);
		this.paginaVO.setRegistrosXPag(50);
		this.paginaVO.setRegistros(null);
		
		this.results = new ArrayList<>();
	}

	public String getInicializarPopUp() {		
		this.isoMostrado = "";
		return null;
	}
	
	public void limpiar(final ActionEvent event) {
		this.inicializarBean();
	}
	
	@SuppressWarnings("unchecked")
	public void consultaMensajes(ActionEvent e) {
		
		this.paginaVO.setOffset(0);
		
		this.paginaVO.setRegistros(null);
		
		this.results = new ArrayList<>();
		
		if(validarFiltros()) {
			
			this.paginaVO = consultaFastworkService.consultaMensajes(fastworkMessagefo, this.paginaVO, false);
			
			camposswiftMostrar = fastworkMessagefo.getCamposSwift();
			
			results = this.paginaVO.getRegistros();
			
			if(this.paginaVO.getValores() != null && this.paginaVO.getValores().containsKey("error")) {
				
				agregarMensaje(new BusinessException((String)this.paginaVO.getValores().get("error")));
				
				return;
				
			}
			
			
			if(results == null || results.isEmpty()) {
				
				agregarInfoMensaje("No se encontraron resultados con los parametros seleccionados.");
				
				return;
				
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String ejecutarConsulta() {
		
		this.paginaVO = consultaFastworkService.consultaMensajes(fastworkMessagefo, this.paginaVO, false);
		
		results = this.paginaVO.getRegistros();
		
		return null;
	}
	
	private boolean validarFiltros() {
		boolean validado = true;
		String[] campos = fastworkMessagefo.getCamposSwift().split(",");
		
		for(String campo : campos) {
			
			campo = campo.trim().replaceAll("\r", "").replaceAll("\n", "");;
			
			if(!campo.isEmpty() && !validarCampoSwift(campo)) {
			    	
		    	validado = false;
		    	
		    	agregarMensaje(new BusinessException("Un campo Swift no tienen un formato correcto."));
			}
		}
		
		return validado;
	}
	
	private boolean validarCampoSwift(String campo) {
		
	    final Pattern pattern = Pattern.compile(":[A-z0-9]{2,3}:[:]{0,1}[A-z]*");
	    
		return pattern.matcher(campo).matches();
		
	}
	
	public void validarReporte(ActionEvent e) {
		
	}
	
	public String generarReporteXls() {
			
		this.actualizarReporte = false;
		
		byte[] reporte = consultaFastworkService.generarReporte("XLS", fastworkMessagefo, this.getAgenteFirmado(), paginaVO, this.getCamposSwiftHeaders());
		
		descargarReporte(reporte, "xls", TITULO_XLS);
		
		this.actualizarReporte = true;
		
		return null;
		
	}	
	
	
	public String generarReporteTxt() {
		
		this.actualizarReporte = false;
		
		byte[] reporte = consultaFastworkService.generarReporte("TXT", fastworkMessagefo, this.getAgenteFirmado(), paginaVO, this.getCamposSwiftHeaders());
		
		descargarReporte(reporte, "zip", TITULO_RAW);
		
		this.actualizarReporte = true;
		
		return null;
		
	}
	
	public String generarReporteIsoTxt() {
		
		this.actualizarReporte = false;
		
		Map<String, FastworkMessageVO> mapValores = new HashMap<>();
		
		mapValores.put("isoConsultado", isoConsultado);
		
		paginaVO.setValores(mapValores);
		
		byte[] reporte = consultaFastworkService.generarReporte("ISO", fastworkMessagefo, this.getAgenteFirmado(), paginaVO, this.getCamposSwiftHeaders());
		
		descargarReporte(reporte, "txt", TITULO_ISO);
		
		this.actualizarReporte = true;
		
		return null;
		
	}
    
    public void descargarReporte(byte[] file, String ext, String tituloReporte) {
        HttpServletResponse response = null;
        OutputStream responseOutputStream = null;
        String fileName = tituloReporte+"."+ext;
        String contentType=ext.contentEquals("xls")?"application/vnd.ms-excel":(ext.equals("txt")?"text/plain":"application/octet-stream");
        try {
            response =(HttpServletResponse) (getFacesContext().getExternalContext().getResponse());
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + fileName + "\"");
            response.setHeader("refresh", "1");
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            responseOutputStream = response.getOutputStream();
            responseOutputStream.write(file);
            responseOutputStream.flush();
            responseOutputStream.close();
            getFacesContext().responseComplete();
        } catch (IOException e) {
        	throw new BusinessException("Ocurrio un error al generar el reporte: " + e.getMessage(), e);
        }
    }
	
	public String getIso() {
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        String dbkey =  (String)params.get("dbkey");
        
        this.isoConsultado = consultaFastworkService.consultaIso(dbkey);
        
        if(isoConsultado != null) {
        	this.isoMostrado = isoConsultado.getIso();
        }else {
    		this.isoMostrado = "No encontrado";
        }
        
		return null;
	}	
	
	public void actualizarEstadoPeticion() {
		if(this.isPeticionReporteCompleta()) {
			this.actualizarReporte = true;
		}
	}

	public void setConsultaFastworkService(ConsultaFastworkService consultaFastworkService) {
		this.consultaFastworkService = consultaFastworkService;
	}

	public List<FastworkMessageVO> getResults() {
		return results;
	}

	public void setResults(List<FastworkMessageVO> results) {
		this.results = results;
	}

	public Map<String, String> getCatTipoMensaje() {
		return catTipoMensaje;
	}

	public Map<String, String> getCatMoneda() {
		return catMoneda;
	}

	public FastworkMessageFO getFastworkMessagefo() {
		return fastworkMessagefo;
	}

	public void setLstTipoMensajes(String lstTipoMensajes) {
		this.lstTipoMensajes = lstTipoMensajes;
	}

	public void setLstMonedas(String lstMonedas) {
		this.lstMonedas = lstMonedas;
	}

	public String getIsoMostrado() {
		return isoMostrado;
	}

	public boolean isActualizarReporte() {
		return actualizarReporte;
	}

	public List<String> getCamposSwiftHeaders() {
		
		this.camposSwiftHeaders = new ArrayList<>();
		
		if(camposswiftMostrar != null && !camposswiftMostrar.isEmpty()) {
			
			String[] camposSwiftArray = camposswiftMostrar.split(",");
			
			for(String campo : camposSwiftArray) {
				
				if(!campo.trim().isEmpty() && validarCampoSwift(campo)) {
					
					this.camposSwiftHeaders.add(campo.trim().replaceAll("\r", "").replaceAll("\n", "").toUpperCase());
					
				}
			}
		}
		return camposSwiftHeaders;
	}
	
}
