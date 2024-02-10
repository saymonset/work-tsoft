/*
 * Copyright (c) 2005-2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.VConsultaDerechosCapitalVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Backing bean que exporta la consulta de derechos a PDF y Excel
 * @author jayalam
 *
 */
public class ConsultaDerechosExportacionController extends ControllerBase{
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaDerechosExportacionController.class);
	
	private ConsultaDerechosParam param = new ConsultaDerechosParam();
	private PaginaVO paginaReportes;
	
	private ControlDerechosService derechosService;
	
	private boolean banderaInicio = true;
	private boolean consultaEjecutada;
	
	private List<SelectItem> lstEstatusDerecho;
	private List<SelectItem> lstBovedas;
	private List<SelectItem> lstDivisas;

	/**
	 * Inicializa el combo de estados, bovedas y divisas
	 * @return
	 */
	public String getInit() {
		ConsultaDerechosParam paramTmp = null;
		 if(banderaInicio) {
			 param.setEmision(new EmisionVO());
			 setConsultaEjecutada(false);
			 paginaVO.setRegistrosXPag(20);
	         banderaInicio = false;
		 }	
		 paramTmp = (ConsultaDerechosParam) 
		            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("parametrosConsulta");
		 if(paramTmp != null){		 
			 param = paramTmp;
			 if(param.getEmision() == null){
				 param.setEmision(new EmisionVO());
			 }
			 ejecutarConsulta();
		 }
		 return null;
	}

	private void generarReportes(boolean pdf) {
	    LOG.debug("####### Entrando a generarReportes()...");
	    LOG.info("####### Generando " + (pdf ? "PDF..." : "XLS..." ));
		getInit();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ConsultaDerechosController derechosBean = 
            (ConsultaDerechosController)
                FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "consultaDerechosBean");
        this.param = derechosBean.getParam();
		
		paginaReportes = new PaginaVO();
		if (pdf) {
		    // Anteriormente se tenia que los registros por pagina eran los que la consulta originalmente traia, esto es,
		    // el valor: param.getTotalRegistrosParaReportes(), pero se decidio que se limitara tambien al valor maximo a exportar.
	        paginaReportes.setRegistrosXPag(Constantes.MAX_REGISTROS_EXPORTAR);
		}
		else {
	        paginaReportes.setRegistrosXPag(Constantes.MAX_REGISTROS_EXPORTAR);
		}
	    paginaReportes.setOffset(0);
	    paginaReportes.setTotalRegistros(param.getTotalRegistrosParaReportes());
	    paginaReportes = setDerechosUsandoVista(paginaReportes, true);
	}

    public void generarReportePDF(ActionEvent event) {
        LOG.debug("####### Entrando a generarReportePDF()...");
        this.generarReportes(true);
    }

    public void generarReporteXLS(ActionEvent event) {
        LOG.debug("####### Entrando a generarReporteXLS()...");
        this.generarReportes(false);
    }

    /**
     * Obtiene los derechos a mostrar en la reporte. 
     * @param tmpPaginaVo El objeto PaginaVO a llenar.
     * @param all Boolean que dicta si se obtienen todos los registros de la BD o no.
     * @return El objeto PaginaVO armado.
     */
    private PaginaVO setDerechosUsandoVista(PaginaVO tmpPaginaVo, boolean all) {
        LOG.debug("####### Entrando a setDerechosUsandoVista()...");
        reiniciarEstadoPeticion();
        AgenteVO agenteVO = null;
        Institucion institucion = null;
        TipoInstitucion tipoInstitucion = null;

        if (tmpPaginaVo != null && tmpPaginaVo.getRegistros() != null) {
            tmpPaginaVo.getRegistros().clear();
            tmpPaginaVo.setRegistros(null);
        }

        if(!isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
            LOG.debug("####### Es usuario indeval");
            agenteVO = getAgenteFirmado();
            institucion = new Institucion();            
            institucion.setFolioInstitucion(agenteVO.getFolio());
            tipoInstitucion = new TipoInstitucion();
            tipoInstitucion.setIdTipoInstitucion(Long.valueOf(agenteVO.getId()));
            institucion.setTipoInstitucion(tipoInstitucion);
            param.setInstitucion(institucion);
        }
        else if (StringUtils.isNotBlank(param.getClaveInstitucion()) && 
                 param.getClaveInstitucion().trim().length() == Constantes.SIZE_ID_FOLIO && 
                 StringUtils.isNumeric(param.getClaveInstitucion().substring(0, Constantes.SIZE_ID)) && 
                 StringUtils.isNumeric(param.getClaveInstitucion().substring(Constantes.SIZE_ID, Constantes.SIZE_ID_FOLIO))) {
            LOG.debug("####### Consultando por id folio");
            institucion = new Institucion();                        
            tipoInstitucion = new TipoInstitucion();
            tipoInstitucion.setIdTipoInstitucion(Long.valueOf(param.getClaveInstitucion().substring(0, Constantes.SIZE_ID)));
            institucion.setTipoInstitucion(tipoInstitucion);
            institucion.setFolioInstitucion(param.getClaveInstitucion().substring(Constantes.SIZE_ID,Constantes.SIZE_ID_FOLIO));
            param.setInstitucion(institucion);
        }

        LOG.info("####### Consultando los derechos....");
        tmpPaginaVo = derechosService.getDerechosUsandoVista(param, tmpPaginaVo, all);
        LOG.info("####### Consulta terminada");
        LOG.debug("####### tmpPaginaVo tamanio registros=[" + tmpPaginaVo.getRegistros().size() + "]");

        if (tmpPaginaVo == null || tmpPaginaVo.getRegistros() == null || tmpPaginaVo.getRegistros().isEmpty()) {
            tmpPaginaVo.setTotalRegistros(0);
            tmpPaginaVo.setRegistros(new ArrayList<VConsultaDerechosCapitalVO>());
            return tmpPaginaVo;
        }

        return tmpPaginaVo;
    }

	public PaginaVO getPaginaReportes() {
		return paginaReportes;
	}

	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
	}
	
	public ConsultaDerechosParam getParam() {
		return param;
	}

	public void setParam(ConsultaDerechosParam param) {
		this.param = param;
	}
	
	public ControlDerechosService getDerechosService() {
		return derechosService;
	}

	public void setDerechosService(ControlDerechosService derechosService) {
		this.derechosService = derechosService;
	}
	
	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}
    
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}
	
	public List<SelectItem> getLstEstatusDerecho() {
		return lstEstatusDerecho;
	}

	public void setLstEstatusDerecho(List<SelectItem> lstEstatusDerecho) {
		this.lstEstatusDerecho = lstEstatusDerecho;
	}
	
	public List<SelectItem> getLstBovedas() {
		return lstBovedas;
	}

	public void setLstBovedas(List<SelectItem> lstBovedas) {
		this.lstBovedas = lstBovedas;
	}
	
	public List<SelectItem> getLstDivisas() {
		return lstDivisas;
	}

	public void setLstDivisas(List<SelectItem> lstDivisas) {
		this.lstDivisas = lstDivisas;
	}



}
