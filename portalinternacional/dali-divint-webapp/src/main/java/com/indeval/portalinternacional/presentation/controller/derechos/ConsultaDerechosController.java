package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.VConsultaDerechosCapitalVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;


public class ConsultaDerechosController extends ControllerBase{
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaDerechosController.class);
	private static final String TO_ADMIN_BENEFICIARIOS = "adminBeneficiariosDerecho";	
	private ConsultaDerechosParam param = new ConsultaDerechosParam();
	private boolean consultaEjecutada;
	private boolean banderaInicio = true;	
	private ControlDerechosService derechosService;
	private ControlCatalogosService catalogosService;
	private List<SelectItem> lstEstatusDerecho;
	private List<SelectItem> lstDivisas;
	private List<SelectItem> lstBovedas;
	private Map<Long,String> mapaCustodios;
	private Map<Long,String> mapaDivisas;
	private Map<Integer,String> mapaEstados;
	private ControlBeneficiariosService beneficiariosService;
	private String claveFolio;

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
	         initEstatusDerechos();
	         initBovedas();
	         initDivisas();
		 }	
		 paramTmp = (ConsultaDerechosParam)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("parametrosConsulta");
		 if(paramTmp != null){		 
			 param = paramTmp;
			 if(param.getEmision() == null){
				 param.setEmision(new EmisionVO());
			 }
			 ejecutarConsulta();
		 }
		 if(!(isUserInRoll("INT_BEN_INDEVAL")||isUserInRoll("INT_BEN_INDEVAL_ADMIN"))) {
		  // FIX DE CAMPO INSTITUCIÒN 
		  AgenteVO agenteVO = getAgenteFirmado();		  
		  Institucion institucion = new Institucion();    
		  TipoInstitucion tipoInstitucion = new TipoInstitucion();
		  institucion.setFolioInstitucion(agenteVO.getFolio());
		  tipoInstitucion.setIdTipoInstitucion(Long.valueOf(agenteVO.getId()));
	      institucion.setTipoInstitucion(tipoInstitucion);
	      param.setInstitucion(institucion);
	      param.setClaveInstitucion(agenteVO.getId()+agenteVO.getFolio());
		 }
		 return null;
	}
	
	public void buscar(ActionEvent event) {		
		paginaVO.setOffset(0);
		if(StringUtils.isNotBlank(param.getClaveInstitucion()) && 
				(!StringUtils.isNumeric(param.getClaveInstitucion()) || param.getClaveInstitucion().length() != Constantes.SIZE_ID_FOLIO)){
			addErrorMessage("El Id Folio de la institucion debe ser numerico y de longitud 5");
			return;
		}
		ejecutarConsulta();
	}

	public void limpiar(ActionEvent event) {
		param = new ConsultaDerechosParam();
		if( paginaVO != null ) {
			if(paginaVO.getRegistros() != null) {
				paginaVO.getRegistros().clear();
				paginaVO.setRegistros(null);
			}
			paginaVO.setOffset(0);
			paginaVO.setTotalRegistros(0);
			paginaVO.setRegistrosXPag(20);
		}
		setConsultaEjecutada(false);
		banderaInicio = true;
	}
	
	public void obtenerOperaciones(ActionEvent event) {
		ejecutarConsulta();
	}
	
    public String ejecutarConsulta() {
    	
    	setPaginaVO(setDerechosUsandoVista(paginaVO, false));
        setConsultaEjecutada(true);
        return null;
    }
    
    public String consultaBeneficiarios(){
        VConsultaDerechosCapitalVO tmp = (VConsultaDerechosCapitalVO) 
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_DERECHO);
    	//Derecho tmp = (Derecho)(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_DERECHO));
    	FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(Constantes.KEY_DERECHO_SELECCIONADO, tmp);
    	FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("parametrosConsulta", param);
    	
    	return TO_ADMIN_BENEFICIARIOS;
    }
    
    public void initEstatusDerechos(){
    	mapaEstados = new HashMap<Integer, String>();
    	List<EstatusDerecho> estatusDerechos = null;
    	estatusDerechos = catalogosService.getEstatusDerecho();
    	lstEstatusDerecho = new ArrayList<SelectItem>();
    	for(EstatusDerecho estatusDerecho:estatusDerechos){
    		mapaEstados.put(estatusDerecho.getIdEstatusDerecho(), estatusDerecho.getDescEstatusDerecho());
    		lstEstatusDerecho.add(new SelectItem(estatusDerecho.getIdEstatusDerecho(), estatusDerecho.getDescEstatusDerecho()));
    	}
    }
    
    public void initDivisas(){
    	mapaDivisas = new HashMap<Long, String>();
    	Divisa[] divisas = null;
    	divisas = catalogosService.getDivisas();    	
    	lstDivisas = new ArrayList<SelectItem>();
    	for(Divisa divisa:divisas){
    		mapaDivisas.put(divisa.getIdDivisa(),divisa.getDescripcion());
    		lstDivisas.add(new SelectItem(divisa.getIdDivisa(), divisa.getDescripcion()));
    	}
    }
    
    public void initBovedas(){
    	mapaCustodios = new HashMap<Long,String>();
    	List<Object[]> bovedas = null;
    	bovedas = beneficiariosService.obtieneAllCatBic();    	
    	lstBovedas = new ArrayList<SelectItem>();
    	for( Object[] bene : bovedas){
    		mapaCustodios.put((Long)bene[0], (String)bene[1]);
    		lstBovedas.add(new SelectItem((Long)bene[0],(String)bene[1]));
    	}
    }

	/**
	 * Obtiene los derechos a mostrar en la pantalla. 
	 * @param tmpPaginaVo El objeto PaginaVO a llenar.
	 * @param all Boolean que dicta si se obtienen todos los registros de la BD o no.
	 * @return El objeto PaginaVO armado.
	 */
	private PaginaVO setDerechosUsandoVista(PaginaVO tmpPaginaVo, boolean all) {
        LOG.debug("####### Entrando a setDerechosUsandoVista()...");
	    AgenteVO agenteVO = null;
	    Institucion institucion = null;
	    TipoInstitucion tipoInstitucion = null;

	    if (tmpPaginaVo != null && tmpPaginaVo.getRegistros() != null) {
	        tmpPaginaVo.getRegistros().clear();
	        tmpPaginaVo.setRegistros(null);
	        if (all) {
	            tmpPaginaVo.setRegistrosXPag(PaginaVO.TODOS);
	        }
	    }

	    param.setInstitucion(null);
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

        if (tmpPaginaVo == null || tmpPaginaVo.getRegistros() == null || tmpPaginaVo.getRegistros().isEmpty()) {
            tmpPaginaVo.setTotalRegistros(0);
            tmpPaginaVo.setRegistros(new ArrayList<VConsultaDerechosCapitalVO>());
            return tmpPaginaVo;
	    }

        if (tmpPaginaVo.getRegistros() != null && tmpPaginaVo.getRegistros().size() > 0) {
            param.setTotalRegistrosParaReportes(tmpPaginaVo.getTotalRegistros());
            VConsultaDerechosCapitalVO unVO = (VConsultaDerechosCapitalVO) tmpPaginaVo.getRegistros().get(0);
            if (param.getIdDivisa() != null) param.setDivisa(unVO.getDivisa());
            if (param.getEmision().getIdBoveda() != null) param.setBovedaCustodio(unVO.getBovedaCustodio());
            if (param.getIdEstado() != null) param.setDescEstatusDerecho(unVO.getDescEstatusDerecho());
        }

	    return tmpPaginaVo;
	}

    public String getClaveFolio(){
    	return claveFolio;    	
    }
    
    public void setClaveFolio(String claveFolio) {
		this.claveFolio = claveFolio;
	}
   
    
    
    
    
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	public ControlDerechosService getDerechosService() {
		return derechosService;
	}

	public void setDerechosService(ControlDerechosService derechosService) {
		this.derechosService = derechosService;
	}

	public List<SelectItem> getLstEstatusDerecho() {
		return lstEstatusDerecho;
	}

	public void setLstEstatusDerecho(List<SelectItem> lstEstatusDerecho) {
		this.lstEstatusDerecho = lstEstatusDerecho;
	}

	public void setCatalogosService(ControlCatalogosService catalogosService) {
		this.catalogosService = catalogosService;
	}

	public List<SelectItem> getLstDivisas() {
		return lstDivisas;
	}

	public void setLstDivisas(List<SelectItem> lstDivisas) {
		this.lstDivisas = lstDivisas;
	}

	public List<SelectItem> getLstBovedas() {
		return lstBovedas;
	}

	public void setLstBovedas(List<SelectItem> lstBovedas) {
		this.lstBovedas = lstBovedas;
	}

	public ConsultaDerechosParam getParam() {
		return param;
	}

	public void setParam(ConsultaDerechosParam param) {
		this.param = param;
	}

	public void setBeneficiariosService(
			ControlBeneficiariosService beneficiariosService) {
		this.beneficiariosService = beneficiariosService;
	}

	public Map<Long, String> getMapaCustodios() {
		return mapaCustodios;
	}

	public Map<Long, String> getMapaDivisas() {
		return mapaDivisas;
	}

	public Map<Integer, String> getMapaEstados() {
		return mapaEstados;
	}
}
