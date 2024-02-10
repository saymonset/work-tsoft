/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author kode-
 *
 */
public class CrearTiposDerechoController extends ControllerBase{
	
	@SuppressWarnings("unused")
	private Pattern pattern;
	private static final Logger log = LoggerFactory.getLogger(CrearTiposDerechoController.class);
	private TipoDerechoDto tipoParam;
	private String tipoDerecho;
	private boolean activo;
	private String tipo;
	private String idTipoDerecho;
	
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	private boolean cierraDialog;
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit()
	{
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
	    this.idTipoDerecho = facesContext.getExternalContext().getRequestParameterMap().get("idTipoDerecho")==null?this.idTipoDerecho:facesContext.getExternalContext().getRequestParameterMap().get("idTipoDerecho");
	    
	    if(this.idTipoDerecho.equals("0") || this.idTipoDerecho.equals("") )
	    {
		    this.activo=true;
		    this.tipo="";
		    this.tipoDerecho="";
			this.idTipoDerecho="";
	    	
	    }
	    else
	    {
		    this.tipoParam =	consultaEventosCorporativosService.getTipoDerechoPorId(Long.parseLong(idTipoDerecho));
		    this.activo=tipoParam.getActivo().equals("0")?true:false;
		    this.tipo=tipoParam.getTipo();
		    this.tipoDerecho=tipoParam.getTipoDerecho();
			this.idTipoDerecho= tipoParam.getIdTipoDerecho();
	    	
	    }

		
		
	    return "";
	}

	/**
	 * Genera los reportes de Consulta de Emisiones
	 */
	public void actualizarRegistro()
	{	

		if(validaDatos()){
			tipoParam= new TipoDerechoDto();
			tipoParam.setIdTipoDerecho(this.idTipoDerecho.equals("0")?null:this.idTipoDerecho);
			tipoParam.setActivo(this.activo==true?"0":"1");
			tipoParam.setTipo(this.tipo);
			if(this.tipoDerecho != null){
				tipoParam.setTipoDerecho(this.tipoDerecho);
			}
			
		    consultaEventosCorporativosService.saveTipoDerecho(tipoParam);
		    this.cierraDialog=true;
		}else{
			this.cierraDialog=false;
		}
	}

	
	
	private boolean validaDatos()
	{
		String camposRequeridos = "Revise los campos requeridos por favor.";

		if (this.tipoDerecho.trim().equals(""))
		{
			addErrorMessage("Debe escribir el nombre del Tipo Derecho. " + camposRequeridos);
			return false;
		}
		
		return true;
	}
	/**
	 * @return the tipoParam
	 */
	public TipoDerechoDto getTipoParam() {
		return tipoParam;
	}


	/**
	 * @param tipoParam the tipoParam to set
	 */
	public void setTipoParam(TipoDerechoDto tipoParam) {
		this.tipoParam = tipoParam;
	}


	/**
	 * @return the tipoDerecho
	 */
	public String getTipoDerecho() {
		return tipoDerecho;
	}


	/**
	 * @param tipoDerecho the tipoDerecho to set
	 */
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}


	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}


	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * @return the idTipoDerecho
	 */
	public String getIdTipoDerecho() {
		return idTipoDerecho;
	}


	/**
	 * @param idTipoDerecho the idTipoDerecho to set
	 */
	public void setIdTipoDerecho(String idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}
	/**
	 * @return the consultaEventosCorporativosService
	 */
	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}

	/**
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}

	/**
	 * @return the cierraDialog
	 */
	public boolean getCierraDialog() {
		return cierraDialog;
	}

	/**
	 * @param cierraDialog the cierraDialog to set
	 */
	public void setCierraDialog(boolean cierraDialog) {
		this.cierraDialog = cierraDialog;
	}

}
