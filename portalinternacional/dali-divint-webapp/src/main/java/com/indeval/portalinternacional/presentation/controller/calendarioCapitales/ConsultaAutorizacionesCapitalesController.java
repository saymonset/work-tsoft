package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para el pop up de Autorizaciones (autorizacionesCalendarioCapitalesPopUp.xhtml)
 * @author ogutierrez
 *
 */
public class ConsultaAutorizacionesCapitalesController extends ControllerBase {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaAutorizacionesCapitalesController.class);
	
	private CapitalesDistribucionService capitalesDistribucionService;
	
	 private boolean consultaEjecutada;

	 private int totalPaginas = 1;

	 private int totalRegistros = 0;
	 
	 /** Pagina para los reportes */
	 private PaginaVO paginaReportes;
	 
	 private boolean banderaBitacoraConsulta = false;
	 
	 private String idsEnviosMoi;
	 
	 private List<Long> idMensajesError;
	 
	    
	/**
	 * M&eacute;todo de inico de la pantalla
	 * @return
	 */
	public String getInit(){
		 if (this.paginaVO == null) {
	            this.paginaVO = new PaginaVO();
	        }
	        paginaVO.setRegistrosXPag(50);
	        paginaVO.setOffset(0);
	        this.getPaginaVO().setRegistros(null);
	        ejecutarConsulta();
	        
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
	 */
	@Override
	public String ejecutarConsulta(){
		try{
			paginaVO = capitalesDistribucionService.consultaAutorizacionesCalendarioCapitales(null,paginaVO);
			LOG.info(("Numero de Registros" + paginaVO.getRegistros().size()));
			
			this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();        
	        if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
	            this.totalPaginas++;
	        }
	        this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;
	        totalRegistros = paginaVO.getTotalRegistros();	        		
    	}catch(Exception e){
    		LOG.error("Error al consultar:",e);
    		addErrorMessage("Ocurri\u00F3 un error al realizar la consulta.");
    	}finally{
    		this.consultaEjecutada = true;
    	}	
		return null;
	}
	
	/**
	 * Toma el evento de pantalla, Autorizar/Cancelar 
	 * @param evt
	 */
	public void ejecutarAutorizacionMensaje(ActionEvent evt){
		String ids = getIdsEnviosMoi();
		try{
			List<EnvioLegislacionSicDTO>  cambios = formateaAutorizacionEnvio(ids);
			if(!cambios.isEmpty()){			
				getPaginaVO().setRegistros(null);
				Collections.sort(cambios);
				this.idMensajesError = capitalesDistribucionService.procesaAutorizacionEnvioMoiSic(cambios);
				if(idMensajesError.isEmpty()){
					agregarInfoMensaje("El proceso se realiz\u00f3 de manera exitosa");
				}else{
					addErrorMessage("Los mensajes con los siguientes folios ya se procesaron con anterioridad: "+ StringUtils.join(this.idMensajesError.toArray(), ','));
				}					
				getInit();
			}
		}catch(Exception e){
			LOG.error("Error al autorizar:",e);
			addErrorMessage("Ocurri\u00f3 un error en el proceso, intente m\u00E1s tarde o comun\u00CDquese con el operador");
			return;
		}
		this.idsEnviosMoi = null;
	}
	
	/**
	 * Forma una lista con eventos a realizar
	 * @param enviosProcesar
	 * @return
	 */
	private List<EnvioLegislacionSicDTO>  formateaAutorizacionEnvio(String enviosProcesar){
		List<EnvioLegislacionSicDTO> listaDto = Collections.emptyList();
		if(StringUtils.isNotEmpty(enviosProcesar)){
			listaDto = new ArrayList<EnvioLegislacionSicDTO>(); 
			String[] buff = enviosProcesar.split("\\|");
			String usuario = getNombreUsuarioSesion();
			for (String linea : buff){
				String[] datos = linea.split(":");								
				if(datos.length == 2){
					String ids = datos[0];
					int posicionFinalIdEnvio = ids.indexOf("his_");
					String idEnvio= ids.substring(4,posicionFinalIdEnvio);
					int posicionFinalIdHistorico = ids.indexOf("des_");
					String idHistorico = ids.substring((posicionFinalIdEnvio + 4),posicionFinalIdHistorico);					
					String evento = datos[1];					
					if(StringUtils.isNotEmpty(idEnvio) && !evento.equals("-1")){
						EnvioLegislacionSicDTO envioDto = new EnvioLegislacionSicDTO();
						envioDto.setIdEnvio(Long.valueOf(idEnvio));
						envioDto.setIdHistorico(Long.valueOf(idHistorico));
						envioDto.setEstado(Integer.valueOf(evento));
						envioDto.setUsuarioAutoriza(usuario);						
						listaDto.add(envioDto);
					}
				}
			}			 
		}
		return listaDto;
	}

	/**
	 * @return the capitalesDistribucionService
	 */
	public CapitalesDistribucionService getCapitalesDistribucionService() {
		return capitalesDistribucionService;
	}

	/**
	 * @param capitalesDistribucionService the capitalesDistribucionService to set
	 */
	public void setCapitalesDistribucionService(CapitalesDistribucionService capitalesDistribucionService) {
		this.capitalesDistribucionService = capitalesDistribucionService;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}

	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the paginaReportes
	 */
	public PaginaVO getPaginaReportes() {
		return paginaReportes;
	}

	/**
	 * @param paginaReportes the paginaReportes to set
	 */
	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
	}

	/**
	 * @return the banderaBitacoraConsulta
	 */
	public boolean isBanderaBitacoraConsulta() {
		return banderaBitacoraConsulta;
	}

	/**
	 * @param banderaBitacoraConsulta the banderaBitacoraConsulta to set
	 */
	public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
		this.banderaBitacoraConsulta = banderaBitacoraConsulta;
	}

	/**
	 * @return the idsEnviosMoi
	 */
	public String getIdsEnviosMoi() {
		return idsEnviosMoi;
	}

	/**
	 * @param idsEnviosMoi the idsEnviosMoi to set
	 */
	public void setIdsEnviosMoi(String idsEnviosMoi) {
		this.idsEnviosMoi = idsEnviosMoi;
	}
	/**
	 * @return the idMensajesError
	 */
	public List<Long> getIdMensajesError() {
		return idMensajesError;
	}
	/**
	 * @param idMensajesError the idMensajesError to set
	 */
	public void setIdMensajesError(List<Long> idMensajesError) {
		this.idMensajesError = idMensajesError;
	}

}
