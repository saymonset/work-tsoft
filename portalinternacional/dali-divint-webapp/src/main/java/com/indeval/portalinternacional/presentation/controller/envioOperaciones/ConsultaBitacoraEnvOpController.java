/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 *Jun 19, 2008
 */
package com.indeval.portalinternacional.presentation.controller.envioOperaciones;

import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Clase que representa la bitacora de envio de operaciones
 * 
 * @author Erik Vera Montoya
 * @version 1.0
 * 
 */
public class ConsultaBitacoraEnvOpController extends ControllerBase {

    /** Servicio para la consulta de */
    private BitacoraOperacionesDao bitacoraOpDao = null;

    /** Parametro obtenidos desde la pantalla */
    private BitacoraOperaciones params = new BitacoraOperaciones();

    /** Bandera que define el estado de la consulta */
    private boolean consultaEjecutada;

    /** Resultados de la consulta */
    private PaginaVO resultados = new PaginaVO();

    /** Servicio utilitario para obtener las fechas */
    private DateUtilService dateUtilService = null;

    /** El identificador y el folio del traspasante */
    private String idFolioTraspasante;
    
    /** Log de clase. */
    private static final Logger log = LoggerFactory.getLogger(ConsultaBitacoraEnvOpController.class);
	
    /** Constuctor */
    public String getInit() {    
        params = new BitacoraOperaciones();
        if (getAgenteFirmado() != null && getAgenteFirmado().getId() != null && getAgenteFirmado().getFolio() != null) {

            idFolioTraspasante = AgenteViewHelper.obtenerClaveTipoInstitucionYFolio(getAgenteFirmado().getId(),
                    getAgenteFirmado().getFolio());
            params.setIdTrasp(getAgenteFirmado().getId());
            params.setIdRecep(getAgenteFirmado().getId());
            params.setFolioRecep(getAgenteFirmado().getFolio());
            params.setFolioTrasp(getAgenteFirmado().getFolio());
        }
        params.setFechaConcertacion(dateUtilService.getCurrentDate());
        params.setFechaLiquidacion(dateUtilService.getCurrentDate());
        consultaEjecutada = false;
        return null;
        
    }

    /**
     * Invoca la consulta principal de la pantalla
     * 
     * @param e Evento generado durante la solicitud
     */
    public void buscarOperaciones(ActionEvent e) {

        params.setIdTrasp(AgenteViewHelper.obtenerIdInstitucion(idFolioTraspasante));
        params.setIdRecep(AgenteViewHelper.obtenerIdInstitucion(idFolioTraspasante));
        params.setFolioRecep(AgenteViewHelper.obtenerFolioInstitucion(idFolioTraspasante));
        params.setFolioTrasp(AgenteViewHelper.obtenerFolioInstitucion(idFolioTraspasante));
        params.setFechaConcertacion(params.getFechaConcertacion() != null ? params.getFechaConcertacion() : null);
        params.setFechaLiquidacion(params.getFechaLiquidacion() != null ? params.getFechaLiquidacion() : null);
        try {
        	ejecutarConsulta();			
		} catch (Exception ex) {
			log.error("Ocurrio un error:",ex);
			throw  new BusinessException(ex.getMessage());
		}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
     */
    @SuppressWarnings("unchecked")
	@Override
    public String ejecutarConsulta() {
    	verificarParametrosConsulta(params);    	    	
        resultados = bitacoraOpDao.getBitacoras(params, paginaVO);
        paginaVO = resultados;
        if (resultados.getTotalRegistros().intValue() == 0) {
			resultados.setRegistros(new ArrayList());
		}
        consultaEjecutada = true;
        return null;
    }
    
    /**
	 * Método que verifica el criterio de búsqueda de Bitacora Envio Operaciones
	 * @param params el parametro de consulta
	 */
    private void verificarParametrosConsulta(BitacoraOperaciones criterio){
		BitacoraOperaciones bitacora = criterio;
		params.setIdTrasp(AgenteViewHelper.obtenerIdInstitucion(idFolioTraspasante));
		params.setIdRecep(AgenteViewHelper.obtenerIdInstitucion(idFolioTraspasante));
		params.setFolioRecep(AgenteViewHelper.obtenerFolioInstitucion(idFolioTraspasante));
		params.setFolioTrasp(AgenteViewHelper.obtenerFolioInstitucion(idFolioTraspasante));
		//cuenta
		params.setCuentaTrasp(StringUtils.isNotEmpty(bitacora.getCuentaTrasp())  ? bitacora.getCuentaTrasp() : null);
		//TV
		params.setTv(StringUtils.isNotEmpty(bitacora.getTv())? bitacora.getTv() : null);
		//emisora
		params.setEmisora(StringUtils.isNotEmpty(bitacora.getEmisora())  ? bitacora.getEmisora() : null);
		//serie
		params.setSerie(StringUtils.isNotEmpty(bitacora.getSerie()) ? bitacora.getSerie() : null);
		//cupon
		params.setCupon(StringUtils.isNotEmpty(bitacora.getCupon()) ? bitacora.getCupon() : null);
		//estatus
		params.setEstatusRegistro(StringUtils.isNotEmpty(bitacora.getEstatusRegistro()) ? bitacora.getEstatusRegistro() : null);		
		//folio descripcion
		params.setReferenciaOperacion(StringUtils.isNotEmpty(bitacora.getReferenciaOperacion()) ? bitacora.getReferenciaOperacion() : null);
	}
    
    /**
     * Metodo que genera los reportes en PDF o XLS
     * @param El action listener que lo invoca
     */
    public void generarReportes(ActionEvent e){
    	try {
        	resultados = bitacoraOpDao.getBitacoras(params, paginaVO);			
		} catch (Exception exc) {
			log.error("Ocurrio un error:",e);
			agregarInfoMensaje(exc.getMessage());	
		}
    }
    
    /**
     * Metodo para generar el Reporte en formato PDF
     */
    public String generarPdf(){
        BitacoraOperaciones paramsConsultaGeneral = params;// variable local para obtener todos los registros
        try {
        	resultados = bitacoraOpDao.getBitacoras(paramsConsultaGeneral, paginaVO);			
		} catch (Exception e) {
			log.error("Ocurrio un error:",e);
			agregarInfoMensaje(e.getMessage());	
		}
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("reporte");
    }

    /**
     * Metodo para generar el Reporte en formato PDF
     * @throws Throwable 
     */
    public String generarXls() {
        BitacoraOperaciones paramsConsultaGeneral = params;// variable local para obtener todos los registros
        paramsConsultaGeneral.getFechaLiquidacion();
        try {
        	resultados = bitacoraOpDao.getBitacoras(paramsConsultaGeneral, paginaVO);
		} catch (Exception e) {
			log.error("Ocurrio un error:",e);
			agregarInfoMensaje(e.getMessage());
		}
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("reporte");
    }

    /**
     * método que limpia la pantalla de Consulta Envio Operaciones
     * 
     * @param e
     */
    public void limpiar(ActionEvent e) {
        params = new BitacoraOperaciones();
        if (getAgenteFirmado() != null && getAgenteFirmado().getId() != null && getAgenteFirmado().getFolio() != null) {

            idFolioTraspasante = AgenteViewHelper.obtenerClaveTipoInstitucionYFolio(getAgenteFirmado().getId(),
                    getAgenteFirmado().getFolio());
            params.setIdTrasp(getAgenteFirmado().getId());
            params.setIdRecep(getAgenteFirmado().getId());
            params.setFolioRecep(getAgenteFirmado().getFolio());
            params.setFolioTrasp(getAgenteFirmado().getFolio());
        }
        params.setFechaConcertacion(dateUtilService.getCurrentDate());
        params.setFechaLiquidacion(dateUtilService.getCurrentDate());
        consultaEjecutada = false;
        resultados = new PaginaVO();
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
     * @return the bitacoraOpDao
     */
    public BitacoraOperacionesDao getBitacoraOpDao() {
        return bitacoraOpDao;
    }

    /**
     * @param bitacoraOpDao the bitacoraOpDao to set
     */
    public void setBitacoraOpDao(BitacoraOperacionesDao bitacoraOpDao) {
        this.bitacoraOpDao = bitacoraOpDao;
    }

    /**
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(PaginaVO resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the params
     */
    public BitacoraOperaciones getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(BitacoraOperaciones params) {
        this.params = params;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * @return the idFolioTraspasante
     */
    public String getIdFolioTraspasante() {
        return idFolioTraspasante;
    }

    /**
     * @param idFolioTraspasante the idFolioTraspasante to set
     */
    public void setIdFolioTraspasante(String idFolioTraspasante) {
        this.idFolioTraspasante = idFolioTraspasante;
    }

}