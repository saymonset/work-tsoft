package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.PosicionLiquidacion;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class AgregarBenefDerechoController  extends ControllerBase{
	
	private static final Logger LOG = LoggerFactory.getLogger(AgregarBenefDerechoController.class);
	
	private boolean beneficiarioExistente;
	private Long idDerecho;
	private Integer idEstatusDerecho;
	private Long idCuentaNombrada;
	private ControlBeneficiariosService beneficiariosService;
	private ControlDerechosService derechosService;
	private String strBeneficiarios;
	private List<BeneficiarioDerecho> lstBeneficiarios;	
	private long posicionAsignada;
	private long posicionCuenta;
	private long totalAsignacionBenef;
	private Long idEmision;
	private boolean beficiariosAgregados = false;
	private String message;
	private String asignacion;
	private String asignacionAux;
	private String porcentajeDefault;
    /** Institucion seleccionada */
    private String idFolioInstitucion;
    /** Institucion seleccionada */
    private String nombreInstitucion;
    
    private String tipoValor;
    private String emisora;
    private String serie;
    private String fechaCorte;
    private String claveInstitucion;
    private String cuenta;
	
	private List<AsignacionBeneficiarioVO> detalleCarga;
	
	public String getInit() {
		
		if(idDerecho == null){
			LOG.debug("Recuperando parametros del request....");
			strBeneficiarios = "";
			FacesContext facesContext = FacesContext.getCurrentInstance();						
			idDerecho = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_ID_DERECHO));
			idEstatusDerecho = Integer.valueOf(facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_ID_ESTATUS_DERECHO));
			idCuentaNombrada = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_ID_CUENTA_NOMBRADA));
			idEmision = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_ID_EMISION));
			porcentajeDefault = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_PORCENTAJE_DEFAULT);
			tipoValor = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_TIPO_VALOR);
			emisora = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_EMISORA);
			serie = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_SERIE);
			fechaCorte = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_FECHA_CORTE);
			claveInstitucion = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_CLAVE_INSTITUCION);
			cuenta = facesContext.getExternalContext().getRequestParameterMap().get(Constantes.KEY_CUENTA);
			getBeneficiariosAgregados();
			LOG.debug("Parametros recuperados.");
		}
				
        if (!this.isInstitucionIndeval()) {
            this.idFolioInstitucion =
                    this.getAgenteFirmado().getId() + this.getAgenteFirmado().getFolio();
            this.nombreInstitucion = this.getAgenteFirmado().getNombreCorto();
        }
		beneficiarioExistente = false;
		return null;
	}
	
	/**
	 * Elimina un beneficiario, si este existia en BD lo elimina y si no solo lo elimina de la lista
	 * @param event
	 */
	public void eliminarBeneficiarioAgregado(ActionEvent event){
		BeneficiarioDerecho derechoSelected = null;
		Integer index = (Integer)event.getComponent().getAttributes().get(Constantes.KEY_INDEX);
		LOG.debug("indice a eliminar de la lista ["+index+"]");
		derechoSelected = lstBeneficiarios.get(index.intValue());
		if(derechoSelected.getIdDerechoBeneficiario() != null){
			LOG.debug("Eliminando un beneficiario existente en BD...");
			posicionAsignada -= derechoSelected.getPosicion().longValue();
			derechosService.deleteBeneficiarioDerecho(derechoSelected.getIdDerechoBeneficiario());
		}else{
			LOG.debug("Eliminando un beneficiario recien agregado...");
			if(StringUtils.isBlank(derechoSelected.getError())){
				totalAsignacionBenef -= derechoSelected.getPosicion().longValue();
			}			
		}
		lstBeneficiarios.remove(index.intValue());
	}
	
	
	/**
	 * Realiza el procesamiento de los beneficiarios agregados en el text area de la pagina, realiza validaciones de negocio
	 * @param event
	 */
	public void procesarBeneficiarios(ActionEvent event){
		Collection<BeneficiarioDerecho> beneficiariosTmp = null;
		totalAsignacionBenef = 0;
		StringTokenizer tokenizer = null;
		BeneficiarioDerecho beneficiario  = null;
		String srtBeneficiario = null;
		String asignacion = null;
		long posicionActualUoi = 0;
		Map<String,BeneficiarioDerecho> mapBeneficiarios = null;
		
		if(StringUtils.isBlank(strBeneficiarios)){
			addErrorMessage("Los beneficiarios son requeridos.");
			return;
		}
		
		/** Agrego validacion del tamanio de string **/
		byte[] strBeneficiariosBytes = null;
		try {
			strBeneficiariosBytes = strBeneficiarios.getBytes("UTF-8");
			Double sizeString = new Double(strBeneficiariosBytes.length)/Constantes.TAMANIO_KB;
			LOG.debug("sizeString :: " + sizeString);
			if(sizeString > Constantes.SIZE_BENEFICIARIO_DERECHO){
				DecimalFormat df = new DecimalFormat("#.00");
	        	addErrorMessage("El contenido del texto es: " + df.format(sizeString) + " Kb, debe ser menor a: " + Constantes.SIZE_BENEFICIARIO_DERECHO + " Kb");
	        	return;
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error("Ocurrio un error:",e);
		}
		
		tokenizer = new StringTokenizer(strBeneficiarios,Constantes.STRING_SCAPE_NEW_LINE);			
		mapBeneficiarios = new HashMap<String, BeneficiarioDerecho>();
		while(tokenizer.hasMoreTokens()){			
			srtBeneficiario = tokenizer.nextToken();
			LOG.debug("Linea a procesar ["+srtBeneficiario+"]");
			if(StringUtils.isNotBlank(srtBeneficiario)){
				srtBeneficiario = srtBeneficiario.trim();
				if(srtBeneficiario.indexOf(Constantes.STRING_SCAPE_TAB) > 0){
					srtBeneficiario = srtBeneficiario.replaceAll(Constantes.STRING_SCAPE_TAB, " ");
				}
				if(srtBeneficiario.indexOf(' ') > 0){
					beneficiario = new BeneficiarioDerecho();
					beneficiario.setUoi((srtBeneficiario.substring(0,srtBeneficiario.indexOf(' ')).trim()).toUpperCase());
					if(StringUtils.isNotBlank(beneficiario.getUoi()) && beneficiario.getUoi().length() > Constantes.SIZE_OF_UOI){
						beneficiario.setError("El UOI no puede ser de longitud mayor a "+Constantes.SIZE_OF_UOI);
					}
					asignacion = (srtBeneficiario.substring(srtBeneficiario.indexOf(' '),srtBeneficiario.length())).trim();					
					asignacion = asignacion.replaceAll(",", "");
					if(StringUtils.isNumeric(asignacion)){
						if(asignacion.length() > Constantes.SIZE_OF_POSICION){
							beneficiario.setError("La longitud de la asignacion no puede ser mayor a "+Constantes.SIZE_OF_POSICION);
						}else{
							beneficiario.setPosicion(Long.valueOf(asignacion));
							totalAsignacionBenef += beneficiario.getPosicion();
						}
						if(mapBeneficiarios.containsKey(beneficiario.getUoi()) && beneficiario.getPosicion() != null){
							posicionActualUoi = mapBeneficiarios.get(beneficiario.getUoi()).getPosicion().longValue();
							mapBeneficiarios.get(beneficiario.getUoi()).setPosicion(Long.valueOf(posicionActualUoi+Long.parseLong(asignacion)));
						}else{
							mapBeneficiarios.put(beneficiario.getUoi(), beneficiario);
						}						
					}
				}else{
					if(srtBeneficiario != null && StringUtils.isNotBlank(srtBeneficiario)){
						beneficiario = new BeneficiarioDerecho();
						beneficiario.setUoi(srtBeneficiario);
						beneficiario.setError("No se procesara beneficiario ["+srtBeneficiario+"] ya que no tiene asignacion");
						mapBeneficiarios.put(beneficiario.getUoi(), beneficiario);
					}
					LOG.info("No se procesara la line ["+srtBeneficiario+"] ya que no tiene asignacion");
				}
			}
		}
				
		getBeneficiariosAgregados();
		if(posicionCuenta < (totalAsignacionBenef+posicionAsignada)){
			totalAsignacionBenef = 0;
			addErrorMessage("La asignacion total a beneficiarios no puede ser mayor a ["+posicionCuenta+"]");						
		}else{
			beneficiariosTmp = mapBeneficiarios.values();			
			validaBeneficiarios(beneficiariosTmp);			
			lstBeneficiarios = lstBeneficiarios == null?new ArrayList<BeneficiarioDerecho>():lstBeneficiarios;
			lstBeneficiarios.addAll(beneficiariosTmp);
		}
		
	}
	
	/**
	 * Realiza la insercion o actualizacion de los beneficiarios en BD, solo los que no contengan errores
	 * @param event
	 */
	public void guardarBenefDerecho(ActionEvent event){
		List<DerechoBeneficiario> lstDerechosBenef = null;
		DerechoBeneficiario derechoBeneficiario = null;
		CuentaNombrada cuentaNombrada = null;	
		EstatusDerecho estatusDerecho = null;
		Beneficiario beneficiario = null;
		
		LOG.debug("Guardando beneficiarios.....");
		if(lstBeneficiarios != null && !lstBeneficiarios.isEmpty()){
			lstDerechosBenef = new ArrayList<DerechoBeneficiario>();
			for(BeneficiarioDerecho beneficiarioDerecho : lstBeneficiarios){
				if(beneficiarioDerecho != null && StringUtils.isBlank(beneficiarioDerecho.getError())){					
					derechoBeneficiario = new DerechoBeneficiario();
					if(beneficiarioDerecho.getIdDerechoBeneficiario() == null){
						LOG.debug("El beneficiario se insertara ["+beneficiarioDerecho.getIdBeneficiario()+"]");
						cuentaNombrada = new CuentaNombrada();
						estatusDerecho = new EstatusDerecho();
						beneficiario = new Beneficiario();
						cuentaNombrada.setIdCuentaNombrada(idCuentaNombrada);		
						estatusDerecho.setIdEstatusDerecho(idEstatusDerecho);
						beneficiario.setIdBeneficiario(beneficiarioDerecho.getIdBeneficiario());										
						derechoBeneficiario.setIdDerechoCapital(idDerecho);
						derechoBeneficiario.setCuentaNombrada(cuentaNombrada);
						derechoBeneficiario.setEstatusDerecho(estatusDerecho);
						derechoBeneficiario.setBeneficiario(beneficiario);
						derechoBeneficiario.setProcesado(Boolean.FALSE);
					}else{
						LOG.debug("El beneficiario solo se actualizara id ["+beneficiarioDerecho.getIdDerechoBeneficiario()+"]");
						derechoBeneficiario.setIdDerechoBeneficiario(beneficiarioDerecho.getIdDerechoBeneficiario());
					}
					derechoBeneficiario.setAsignacion(beneficiarioDerecho.getPosicion());
					lstDerechosBenef.add(derechoBeneficiario);
				}
			}
			derechosService.agregaBeneficiariosDerecho(lstDerechosBenef);
			beficiariosAgregados = true;
			message = "Se agregaron los beneficiarios con exito";
			if((posicionCuenta-(totalAsignacionBenef+posicionAsignada)) > 0){
				message += ",\nFalta por asignar ["+(posicionCuenta-(totalAsignacionBenef+posicionAsignada))+"] la cual sera asiganda al % de default del ["+porcentajeDefault+"]";
			}
			LOG.debug("Beneficiarios guardados.");
		}		
	}
	
	public void modificarAsignacionBeneficiario(ActionEvent event){
		BeneficiarioDerecho derechoSelected = null;
		long posicionAsignadaTmp = posicionAsignada;	
		long totalAsignacionBenefTmp = totalAsignacionBenef;
		Integer index = null;		
		long posicionTotal = 0;
		long asignacionTmp = 0;
		long posicionActual = 0;
		
		LOG.debug("asignacion a actualizar ["+asignacionAux+"]");
		
		if(StringUtils.isBlank(asignacionAux) || !StringUtils.isNumeric(asignacionAux)){
			asignacion = null;
			addErrorMessage("La asignacionn es requerida y debe ser numerica.");
			return;
		}
		index = (Integer)event.getComponent().getAttributes().get(Constantes.KEY_INDEX);		
		derechoSelected = lstBeneficiarios.get(index.intValue());				
		posicionActual = derechoSelected.getPosicion().longValue();		
		if(derechoSelected.getIdDerechoBeneficiario() != null){
			posicionAsignadaTmp -= posicionActual;
		}else{
			totalAsignacionBenefTmp -= posicionActual; 
		}
		
		asignacionTmp = Long.valueOf(asignacionAux);								
		posicionTotal = posicionCuenta - (posicionAsignadaTmp+totalAsignacionBenefTmp);		
		if((posicionTotal-asignacionTmp) < 0){	
			asignacion = null;
			addErrorMessage("La asignacion no puede se mayor a ["+posicionCuenta+"]");
			return;
		}		
		LOG.debug("actializando la posicion del beneficiario....");
		derechoSelected.setPosicion(Long.valueOf(asignacionTmp));
		if(derechoSelected.getIdDerechoBeneficiario() != null){
			LOG.debug("Se actualizara la asignacion a un beneficiario existente....");
			posicionAsignada = posicionAsignada-posicionActual+asignacionTmp;
		}else{
			LOG.debug("Se actualizara la asignacion a un beneficiario nuevo....");
			totalAsignacionBenef = totalAsignacionBenef-posicionActual+asignacionTmp;; 
		}
		asignacion = null;
		LOG.debug("posicicion actualizada");		
	}
	
	public void changeAsignacion(ValueChangeEvent changeEvent){
		
		if(!StringUtils.isBlank((String)changeEvent.getNewValue())){
			asignacionAux = (String)changeEvent.getNewValue();
		}
	
	}

	/**
	 * Realiza las validaciones de negocio para el beneficiario
	 * @param beneficiarios
	 */
	private void validaBeneficiarios(Collection<BeneficiarioDerecho> beneficiarios){
		Long cuentaNombradaCustodio = null;
		AgenteVO agenteVO = null;
		Beneficiario beneficiario = null;	
		String prefixMessage = "El beneficiario con UOI";
		
		LOG.debug("Validando beneficiarios....");
		cuentaNombradaCustodio = derechosService.getCuentaNombradaOfCustodio(idEmision);
		if(beneficiarios != null && !beneficiarios.isEmpty()){
			for(BeneficiarioDerecho tmp:beneficiarios){
				if(StringUtils.isBlank(tmp.getError())){
					if(!isUsuarioRolIndeval()){
						LOG.debug("Buscando beneficiario por id folio..");
			    		agenteVO = getAgenteFirmado();
			    		beneficiario = beneficiariosService.consultaBeneficiarioUoiInst(tmp.getUoi(),null,Long.valueOf(agenteVO.getId()),agenteVO.getFolio());    		
			    	}else{
			    		LOG.debug("Buscando beneficiario por uoi");
			    		beneficiario = beneficiariosService.consultaBeneficiarioUoiInst(tmp.getUoi(),null,null,null);
			    	}							
					
					if(beneficiario == null){
						tmp.setError(prefixMessage+" ["+tmp.getUoi()+"] no existe");
					}else if(cuentaNombradaCustodio.longValue() != beneficiario.getIdCuentaNombrada()){					
						tmp.setError(prefixMessage+" ["+tmp.getUoi()+"] no corresponde con el custodio de la emision.");
					}else if(beneficiario.getStatusBenef().getIdStatusBenef().longValue() != 2){
						tmp.setError(prefixMessage+" ["+tmp.getUoi()+"] se encuentra "+beneficiario.getStatusBenef().getDescStatusBenef());
					}else if(derechosService.existeBeneficiarioDerecho(beneficiario.getIdBeneficiario(), idCuentaNombrada,idDerecho)){
						tmp.setError(prefixMessage+" ["+tmp.getUoi()+"] ya existe como beneficiario del derecho.");
					}else{
						beneficiario = derechosService.getBeneficiarioCuentaByUoi(tmp.getUoi(), idCuentaNombrada);					
						if(beneficiario == null){
							tmp.setError("El beneficiario con UOI ["+tmp.getUoi()+"] no tiene asignada la institución de la cuenta.");
						}else{
							tmp.setIdBeneficiario(beneficiario.getIdBeneficiario());
							tmp.setPorcentajeRetencion(beneficiario.getPorcentajeRetencion());
						}					
					}
					if(StringUtils.isNotBlank(tmp.getError())){
						totalAsignacionBenef -= tmp.getPosicion().longValue();
					}
				}
			}
		}
		LOG.debug("Beneficiarios validados");
	}
	
	private void getBeneficiariosAgregados(){
		PaginaVO tmpPagnaVo = null;
		posicionAsignada = 0;
		BeneficiarioDerecho beneficiarioDerecho = null;
		List<DerechoBeneficiario> benefAgregados = derechosService.getBenefDerechoXCuenta(idDerecho,new Long[]{idCuentaNombrada});		
		if(benefAgregados != null && !benefAgregados.isEmpty()){
			lstBeneficiarios = new ArrayList<BeneficiarioDerecho>();
			for(DerechoBeneficiario tmp:benefAgregados){						
				beneficiarioDerecho = new BeneficiarioDerecho();
				beneficiarioDerecho.setUoi(tmp.getBeneficiario().getUoiNumber());
				beneficiarioDerecho.setPorcentajeRetencion(tmp.getBeneficiario().getPorcentajeRetencion());
				beneficiarioDerecho.setPosicion(tmp.getAsignacion());
				beneficiarioDerecho.setIdDerechoBeneficiario(tmp.getIdDerechoBeneficiario());
				posicionAsignada += tmp.getAsignacion().longValue();
				lstBeneficiarios.add(beneficiarioDerecho);
			}
		}else{
			lstBeneficiarios = null;
		}
		
		if(posicionCuenta <= 0){
			tmpPagnaVo = derechosService.getCuentasDerecho(idDerecho, tmpPagnaVo);
			if(tmpPagnaVo != null && tmpPagnaVo.getRegistros() != null && !tmpPagnaVo.getRegistros().isEmpty()){
				for(PosicionLiquidacion posicion : (List<PosicionLiquidacion>)tmpPagnaVo.getRegistros()){
					if(posicion.getCuenta().getIdCuentaNombrada().longValue() == idCuentaNombrada.longValue()){
						posicionCuenta = posicion.getSaldo().longValue();
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Mensaje que se mostrara en el alert cuando se guarden los cambios
	 * @return
	 */
	public String getMessageConfirm(){
		String message = null;
		
		if((posicionCuenta-(posicionAsignada+totalAsignacionBenef)) > 0){
			message = "Faltan por asignar "+(posicionCuenta-(posicionAsignada+totalAsignacionBenef))+" titulos, estos tendran un % de retencion del "+porcentajeDefault;
		}else{
			message = "Estas seguro que deseas asignar los beneficiarios";
		}
		
		return message;
	}
	
	/**
	 * Regresa la lista que contiene los registros cargados, son los registros para el llenado del archivo excel
	 */
	public void detalleCargaDerechoBeneficiario(ActionEvent e) {
		this.detalleCarga = null;
		this.detalleCarga = new ArrayList<AsignacionBeneficiarioVO>();
		for (BeneficiarioDerecho beneficiario : lstBeneficiarios) {
//			if(beneficiario.getError() != null && StringUtils.isNotEmpty(beneficiario.getError())) {
				AsignacionBeneficiarioVO beanAsignacion = new AsignacionBeneficiarioVO();
				beanAsignacion.setFolioID(this.claveInstitucion);
				beanAsignacion.setCuenta(this.cuenta);
				beanAsignacion.setTipoValor(this.tipoValor);
				beanAsignacion.setEmisora(this.emisora);
				beanAsignacion.setSerie(this.serie);
				beanAsignacion.setFechaCorteString(this.fechaCorte);
				beanAsignacion.setUoi(beneficiario.getUoi());
				beanAsignacion.setAsignacion(beneficiario.getPosicion());
				beanAsignacion.setPorcentajeRetencion(beneficiario.getPorcentajeRetencion());
				beanAsignacion.setError(beneficiario.getError());
				this.detalleCarga.add(beanAsignacion);
//			}
		}
	}

	public boolean isBeneficiarioExistente() {
		return beneficiarioExistente;
	}

	public void setBeneficiarioExistente(boolean beneficiarioExistente) {
		this.beneficiarioExistente = beneficiarioExistente;
	}	

	public ControlBeneficiariosService getBeneficiariosService() {
		return beneficiariosService;
	}

	public void setBeneficiariosService(
			ControlBeneficiariosService beneficiariosService) {
		this.beneficiariosService = beneficiariosService;
	}

	public ControlDerechosService getDerechosService() {
		return derechosService;
	}

	public void setDerechosService(ControlDerechosService derechosService) {
		this.derechosService = derechosService;
	}

	public String getStrBeneficiarios() {
		return strBeneficiarios;
	}

	public void setStrBeneficiarios(String strBeneficiarios) {
		this.strBeneficiarios = strBeneficiarios;
	}

	public List<BeneficiarioDerecho> getLstBeneficiarios() {
		return lstBeneficiarios;
	}

	public void setLstBeneficiarios(List<BeneficiarioDerecho> lstBeneficiarios) {
		this.lstBeneficiarios = lstBeneficiarios;
	}

	public boolean isBeficiariosAgregados() {
		return beficiariosAgregados;
	}

	public void setBeficiariosAgregados(boolean beficiariosAgregados) {
		this.beficiariosAgregados = beficiariosAgregados;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public long getTotalAsignacion() {
		return (posicionAsignada+totalAsignacionBenef);		
	}

	/**
	 * @return the detalleCarga
	 */
	public List<AsignacionBeneficiarioVO> getDetalleCarga() {
		return detalleCarga;
	}

	/**
	 * @param detalleCarga the detalleCarga to set
	 */
	public void setDetalleCarga(List<AsignacionBeneficiarioVO> detalleCarga) {
		this.detalleCarga = detalleCarga;
	}

	/**
	 * @return the idFolioInstitucion
	 */
	public String getIdFolioInstitucion() {
		return idFolioInstitucion;
	}

	/**
	 * @param idFolioInstitucion the idFolioInstitucion to set
	 */
	public void setIdFolioInstitucion(String idFolioInstitucion) {
		this.idFolioInstitucion = idFolioInstitucion;
	}

	/**
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * @param nombreInstitucion the nombreInstitucion to set
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param claveInstitucion the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}
	
}
