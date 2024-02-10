package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService;
//import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
//import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoInt;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author javier.perez
 * Controller para la pantalla detalleInstrucciones.xhtml
 *
 */
public class InstruccionCapiltalesController extends ControllerBase {
	
	private static final Logger LOG = LoggerFactory.getLogger(InstruccionCapiltalesController.class);
	
	private InstruccionCapitalesVO instruccion;
	
	private InstruccionCapitalesVO instruccionGuardar;
	
	private List<SelectItem> listaTipoPagoCAEV;
	
	private List<SelectItem> listaTagsEliminacion;
	
	private List<SelectItem> listaTagsOpciones;
	
	private String liga;
	
	private String[] tagsSeleccionados;
	
	private String[] tagsOpcionesIso;
			
	//private String caev;
	
	private String mensajeLiga;
	
	private boolean muestraMensaje;
	
	private boolean banderaInicio;
	
	private String isinTemp;
	
	private Long idHistorico;
	
	private Long idBitacora;
	
	private boolean isinValidoRequerido;
	
	private static final String SEPARADOR_OPCIONES = ":13A::CAON//";
	
	private static final int LONGITUD_DIGITOS_OPCION = 3;
			
	  /** Patron numerico */
    private Pattern pattern = Pattern.compile("[0-9]*");
    
    private boolean contieneTags;
    
    private boolean contieneOpciones;
        
    /** servicios **/
	//private DivisionInternacionalService divisionInternacionalService;
    private CapitalesDistribucionService capitalesDistribucionService;
    
    private String mensajeDeslistada;
    
    private boolean sufrioModificacion;    
	
    public InstruccionCapiltalesController(){}
	
    /**
     * m&eacute;todo inicial de la pantalla
     * @return
     */
	public String getInit(){		
		try{
			if(!this.banderaInicio){				
				limpiarDatos();
				consultaInicial(true);				
				if(this.instruccion != null){
					asignaValoresOriginales();
				}
				this.banderaInicio = true;				
		
			}else{
				consultaInicial(false);
					if(this.instruccion != null){
						asignaValoresOriginales();
					}
			}					
		}catch(Exception e){
			LOG.error("Error al realizar la consulta: ",e);
			addErrorMessage("Error al recibir los par\u00E1metros, consulte con su administrador");
		}					
		return null;
	}
	
	/**
	 * Lee el mensaje Iso y toma los valores de las opciones que contiene
	 */
	public List<SelectItem> getOpcionesMensaje(){
		if(this.listaTagsOpciones == null || this.listaTagsOpciones.isEmpty()){
			String mensaje = capitalesDistribucionService.consultaMensajeSwiftById(this.idBitacora);
			if(mensaje != null){
				this.listaTagsOpciones = new ArrayList<SelectItem>();
		        while (tieneCadenaBuscada(mensaje, SEPARADOR_OPCIONES)) {
		        	mensaje = getNuevaCadena(mensaje, SEPARADOR_OPCIONES);
		        	String valor = mensaje.substring(0, LONGITUD_DIGITOS_OPCION);
		        	this.listaTagsOpciones.add(new SelectItem(Integer.valueOf(valor).toString(),Integer.valueOf(valor).toString()));		        	 
		        }
		        if(listaTagsOpciones.isEmpty()){
		        	this.listaTagsOpciones.add(new SelectItem("-1","VACIO"));
		        	this.contieneOpciones=true;
		        }else{
		        	this.contieneOpciones=false;
		        }		        
			}
		}
		return listaTagsOpciones;
		
	}
	
    /**
     * Revisa si en una cadena existe la secuencia que busca     
     * @param cadenaLarga
     * @param cadenaBuscada
     * @return
     */
    public static boolean tieneCadenaBuscada(String cadenaLarga, String cadenaBuscada) {
            boolean tieneCadena = false;
            if (cadenaLarga != null) {
                    tieneCadena = cadenaLarga.contains(cadenaBuscada);
            }
            return tieneCadena;
    }
    
    /**
     * Quita la cadena buscada y la regresa recortada     
     * @param cadenaLarga
     * @return
     */
    public static String getNuevaCadena(String cadenaLarga, String separador) {
            int posicionInicioDigitos = cadenaLarga.indexOf(separador) + separador.length();
            String cadenaNueva = cadenaLarga.substring(posicionInicioDigitos);

            return cadenaNueva;
    }
	
	/**
	 * Asigna los valores recuperados en la consulta a los filtros del formulario
	 */
	private void asignaValoresOriginales(){
		this.liga = this.instruccion.getLiga() != null ? this.instruccion.getLiga().toString() : "";
		this.tagsSeleccionados = this.instruccion.getTagsEliminacion() != null ? convierteStringToArray(this.instruccion.getTagsEliminacion()):null;
		this.tagsOpcionesIso = this.instruccion.getOpcionesMensajes() != null ? convierteStringToArray(this.instruccion.getOpcionesMensajes()):null;
		
		//this.caev = this.instruccion.getIdTipoDerechoCaev() != null ? this.instruccion.getIdTipoDerechoCaev().toString() : "-1"; 
	}
	
	
	private String [] convierteStringToArray(String cadena){
		String [] cadenaRsultado = null;
		if(cadena.contains(",")){
			cadenaRsultado = cadena.split(",");
		}else{
			cadenaRsultado = new String[1];
			cadenaRsultado[0] = cadena;
		}
		return cadenaRsultado;
	}
	
	private void consultaInicial(boolean isInicio) throws BusinessException{		
		if(isInicio){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			this.idHistorico = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("idHistorico"));
			this.idBitacora = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("idBitacora"));
		    LOG.info("************* VALOR ID HISTORICO RECUPERADO: "+idHistorico);
		    LOG.info("************* VALOR ID BITACORA RECUPERADO: "+idBitacora);
		}
		if(this.idHistorico != null){
			this.instruccion = capitalesDistribucionService.consultaInstruccionByIdHist(this.idHistorico);
		}
	    	
	}
	
	private boolean validaInformacionIncial(){
		boolean eliminaRegistro = false;
		if(this.instruccion.getLiga() == null && 
				this.instruccion.getOpcionesMensajes() == null && 
				this.instruccion.getTagsEliminacion() == null){
			this.capitalesDistribucionService.eliminaInstruccion(this.instruccion);			
			eliminaRegistro = true;
		}
		return eliminaRegistro;
	}
	
	private void limpiarDatos(){
		this.liga = "";
		this.tagsSeleccionados = null;
		this.tagsOpcionesIso = null;		
		//this.caev = "-1";
		this.mensajeLiga = "";
		this.muestraMensaje = false;
		this.isinValidoRequerido =true;
		this.isinTemp="";
	}
	
	
  /**
    * Datos para cargar el combo tipo de Pago CAEV
    * @return
    */
    public List<SelectItem> getTagsEliminacion() {
        if (this.listaTagsEliminacion == null || this.listaTagsEliminacion.isEmpty()) {
        	Map<String,String> tagsEliminacion = this.capitalesDistribucionService.getTagsEliminacionSicMap();
        	listaTagsEliminacion = new ArrayList<SelectItem>();
        	if(tagsEliminacion != null){        		
        		for (Map.Entry<String, String> tag : tagsEliminacion.entrySet()){
        			listaTagsEliminacion.add(new SelectItem(tag.getKey().trim(),tag.getValue().trim()));
        		}
        		this.contieneTags = false;
        	}else{
        		listaTagsEliminacion.add(new SelectItem("-2","VACIO"));
        		this.contieneTags = true;
        	}        	        	        	
        }
        return this.listaTagsEliminacion;
    }
    
    /**
     * m&eacute;todo que toma el evento de pantalla
     * elimina la instrucci&oacute;n actual
     * @param event
     */
    public void eliminarInstruccion(ActionEvent event){
    	if(this.instruccion != null){
    		try{
    			this.capitalesDistribucionService.eliminaInstruccion(this.instruccion);        	
        		this.instruccion = null;    		
        		limpiarDatos();
        		addMessage("Proceso ejecutado de manera Exitosa");
        		this.mensajeLiga = "";
        		this.sufrioModificacion = true;
    		}catch(Exception e){
    			LOG.error("Error al eliminar registro: ",e);
    			addErrorMessage("Error al intentar eliminar el registro.");    			
    		}
    		
    	}
    }
    
    /**
     * m&eacute;todo que toma el evento de pantalla
     * Guarda o actualiza la instrucci&oacute;n
     * @param event
     */
    public void guardarIntruccion(ActionEvent event){
    	this.mensajeDeslistada = "";
    	this.sufrioModificacion = false;
    	if(this.isinValidoRequerido){
    	   	try {    	   		
        		validaParametrosEntrada();        		       		
        		if(this.instruccion != null){        			
        			actualizaIntruccion();   			
            	}else{
            		guardaInstruccion();
            	}
    		} catch (BusinessException e) {
    			LOG.error("Error al guardar instruccion: ",e);
    			addErrorMessage(e.getMessage());	    	
    		}catch(Exception e){
    			LOG.error("Error al guardar instruccion: ",e);
    			addErrorMessage("Error al intentar guardar el registro.");
    		}
    	}
    }
    
    /**
     * Guarda una nueva Instrucci&oacute;n
     * @throws BusinessException
     */
    private void guardaInstruccion()throws BusinessException{
    	this.instruccion = new InstruccionCapitalesVO();            		
		asignavalores();
		if(validaNuevoRegistro()){
			this.capitalesDistribucionService.guardarInstruccion(this.instruccion);
			this.sufrioModificacion = true;
			addMessage("Proceso ejecutado de manera Exitosa."+this.mensajeDeslistada);
    		this.instruccion = null;
		}else{
			addWarnMessage("No se ingresaron valores.");
		}          
    }
    
    /**
     * Actualiza la Instrucci&oacute;n
     * @throws BusinessException
     */
    private void actualizaIntruccion()throws BusinessException{
    	asignarNuevosValores(); 
    	if(!this.instruccion.equals(this.instruccionGuardar)){    		
    		asignavalores();
    		if(!validaInformacionIncial()){				
				this.capitalesDistribucionService.actualizaInstruccion(this.instruccion);				
			}
			this.sufrioModificacion = true;				
    		this.instruccion = null;
			addMessage("Proceso ejecutado de manera Exitosa."+this.mensajeDeslistada);
		}else{
			addWarnMessage("No se modificar\u00F3n los valores iniciales.");
		}
    }
    
    /**
     * m&eacute;todo que toma el evento de pantalla
     * para limpiar el formulario y los mensajes
     * @param event
     */
    public void limpiar(ActionEvent event){
    	if(this.instruccion != null){
    		this.mensajeLiga="";
    		this.muestraMensaje = false;
    		this.isinTemp="";
    		this.isinValidoRequerido =true;
    		asignaValoresOriginales();    		
    	}else{
    		limpiarDatos();
    	}  	
    }
    
    /**
     * Agrega los valores para ser procesado por el servicio
     */
    private void asignavalores(){   	    	
    	this.instruccion.setLiga(StringUtils.isNotBlank(this.liga) ? Long.valueOf(this.liga):null);
    	this.instruccion.setTagsEliminacion(convierteArrayToString(this.tagsSeleccionados));
    	this.instruccion.setOpcionesMensajes(convierteArrayToString(this.tagsOpcionesIso));
//    	this.instruccion.setIdTipoDerechoCaev(this.caev != null && 
//    			!("-1".equals(this.caev) || "-2".equals(this.caev)) ? Long.valueOf(this.caev) : null);
    	this.instruccion.setIdHistorico(this.idHistorico);
    }
    
    /**
     * agreaga al objeto los valores ingresados por el usuario
     */
    private void asignarNuevosValores(){
    	this.instruccionGuardar = new InstruccionCapitalesVO();
    	this.instruccionGuardar.setLiga(StringUtils.isNotBlank(this.liga) ? Long.valueOf(this.liga):null);
    	this.instruccionGuardar.setTagsEliminacion(convierteArrayToString(this.tagsSeleccionados));
    	this.instruccionGuardar.setOpcionesMensajes(convierteArrayToString(this.tagsOpcionesIso)); 
    }
    
    /**
     * valida que de los nuevos 3 valores tenga por lo menos
     * uno un valor
     * @return
     */
    private boolean validaNuevoRegistro(){
    	return this.instruccion.getLiga() != null || 
    			this.instruccion.getOpcionesMensajes() != null || 
    			this.instruccion.getTagsEliminacion() != null;
    }
    
    /**
     * convierte un array a una cadena con los 
     * valores concatenados por coma
     * @param array
     * @return
     */
    private String convierteArrayToString(String [] array){
    	String regreso = null;
    	if(array != null && array.length > 0){
    		for(int i= 0;i < array.length;i++){
    			if(i==0){
    				regreso = array[i];
    			}else{
    				regreso += ","+array[i];
    			}
    		}
    	}
    	return regreso;
    }
    
    /**
     * valida los par&aacute;metros de entrada
     * Lanza excepci&oacute;n sino cumple las reglas
     * @throws BusinessException
     */
    private void validaParametrosEntrada()throws BusinessException{
    	if(StringUtils.isNotBlank(this.liga)){
    		this.liga = this.liga.trim();
    		if(!this.pattern.matcher(this.liga).matches() || !existeCalendario(Long.valueOf(this.liga.trim()))){
    			throw new BusinessException("Valor de la Liga con Formato Incorrecto o no Existe");
    		}
    	}
    }
    
    /**
     * consulta y valida que exista el calendario dado
     * true si existe, false de lo contrario
     * @param value
     * @return
     */
    private boolean existeCalendario(Long value){
    	boolean isValida = false;
    	VCalendarioDerechoInt calendarioTemp = this.capitalesDistribucionService.consultaVCalendarioByIdCalendario(value);
    	if(calendarioTemp != null){
    		if(calendarioTemp.getIsin()!=null){
    			isValida = true;
    			if(!"LISTADA".equals(calendarioTemp.getvEmisionesCalendario())){
    				this.mensajeDeslistada = " Se lig\u00F3 a un derecho con emisi\u00F3n DESLISTADA";
    			}
    		}
    	}
    	return isValida;
    		
    }
    
    
    
    /**
     * Toma el evento de la pantalla
     * para buscar el Isin dependiendo el valor de la liga
     * @param event
     */
    public void buscaIsin(ActionEvent event){
 		this.isinTemp = null;
		this.isinValidoRequerido = true;
		this.mensajeLiga = "";
		this.muestraMensaje = false;
    	if(StringUtils.isNotBlank(this.liga) && StringUtils.isNumeric(this.liga)){
    		try {
    			VCalendarioDerechoInt calendarioTemp = this.capitalesDistribucionService.consultaVCalendarioByIdCalendario(Long.valueOf(this.liga.trim()));
    			if(calendarioTemp != null){
    				this.isinTemp =  calendarioTemp.getIsin();
    				if(this.isinTemp != null){    					
    					if("LISTADA".equals(calendarioTemp.getvEmisionesCalendario())){
    						this.mensajeLiga = "ISIN: "+this.isinTemp;
    					}else{
    						this.mensajeLiga = "ISIN: "+this.isinTemp+", se lig\u00F3 a un derecho con emisi\u00F3n DESLISTADA";
    					}
    				}else{
    					this.mensajeLiga = "ISIN Inexistente";
        				this.isinValidoRequerido = false;
    				}
    			}else{
    				this.mensajeLiga = "ISIN Inexistente";
    				this.isinValidoRequerido = false;
    			}	
    			this.muestraMensaje = true;    	
			} catch (Exception e) {
				LOG.error("Error al consultar ISIN: ",e);
				addErrorMessage("Error al consultar ISIN, consulte con su administrador");
			}
    	} 
    }
  /**
    * Datos para cargar el combo tipo de Pago CAEV
    * @return
    */
    //se comenta para la segunda fases
//    public List<SelectItem> getTiposPagoCAEV() {
//        if (this.listaTipoPagoCAEV == null || this.listaTipoPagoCAEV.isEmpty()) {
//        	this.listaTipoPagoCAEV = getTiposPagoMensaje(true);        	
//        }
//        return this.listaTipoPagoCAEV;
//    }
    
    /**
     * Auxiliar para determinar el tipo de pago solicitado
     * @return
     */
//    private List<SelectItem> getTiposPagoMensaje(final Boolean isCAEV) {
//        List<TipoPagoInt> tipos = divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
//        List<SelectItem> listaPagos = new ArrayList<SelectItem>();
//        if (tipos != null) {
//            for (TipoPagoInt tp : tipos) {
//                listaPagos.add(new SelectItem(tp.getId().toString(), tp.getClavePago()));
//            }
//        } else {
//            listaPagos.add(new SelectItem("-2", "VACIO"));
//        }
//        return listaPagos;
//    }

	/**
	 * @return the instruccion
	 */
	public InstruccionCapitalesVO getInstruccion() {
		return instruccion;
	}

	/**
	 * @param instruccion the instruccion to set
	 */
	public void setInstruccion(InstruccionCapitalesVO instruccion) {
		this.instruccion = instruccion;
	}

	/**
	 * @return the listaTipoPagoCAEV
	 */
	public List<SelectItem> getListaTipoPagoCAEV() {
		return listaTipoPagoCAEV;
	}

	/**
	 * @param listaTipoPagoCAEV the listaTipoPagoCAEV to set
	 */
	public void setListaTipoPagoCAEV(List<SelectItem> listaTipoPagoCAEV) {
		this.listaTipoPagoCAEV = listaTipoPagoCAEV;
	}

	/**
	 * @return the listaTagsEliminacion
	 */
	public List<SelectItem> getListaTagsEliminacion() {
		return listaTagsEliminacion;
	}

	/**
	 * @param listaTagsEliminacion the listaTagsEliminacion to set
	 */
	public void setListaTagsEliminacion(List<SelectItem> listaTagsEliminacion) {
		this.listaTagsEliminacion = listaTagsEliminacion;
	}

	/**
	 * @return the tagsSeleccionados
	 */
	public String[] getTagsSeleccionados() {
		return tagsSeleccionados;
	}

	/**
	 * @param tagsSeleccionados the tagsSeleccionados to set
	 */
	public void setTagsSeleccionados(String[] tagsSeleccionados) {
		this.tagsSeleccionados = tagsSeleccionados;
	}

	/**
	 * @return the liga
	 */
	public String getLiga() {
		return liga;
	}

	/**
	 * @param liga the liga to set
	 */
	public void setLiga(String liga) {
		this.liga = liga;
	}


	/**
	 * @return the caev
	 */
//	public String getCaev() {
//		return caev;
//	}

	/**
	 * @param caev the caev to set
	 */
//	public void setCaev(String caev) {
//		this.caev = caev;
//	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
//	public void setDivisionInternacionalService(DivisionInternacionalService divisionInternacionalService) {
//		this.divisionInternacionalService = divisionInternacionalService;
//	}

	/**
	 * @param capitalesDistribucionService the capitalesDistribucionService to set
	 */
	public void setCapitalesDistribucionService(CapitalesDistribucionService capitalesDistribucionService) {
		this.capitalesDistribucionService = capitalesDistribucionService;
	}

	/**
	 * @return the mensajeLiga
	 */
	public String getMensajeLiga() {
		return mensajeLiga;
	}

	/**
	 * @param mensajeLiga the mensajeLiga to set
	 */
	public void setMensajeLiga(String mensajeLiga) {
		this.mensajeLiga = mensajeLiga;
	}

	/**
	 * @return the muestraMensaje
	 */
	public boolean isMuestraMensaje() {
		return muestraMensaje;
	}

	/**
	 * @param muestraMensaje the muestraMensaje to set
	 */
	public void setMuestraMensaje(boolean muestraMensaje) {
		this.muestraMensaje = muestraMensaje;
	}

	/**
	 * @return the banderaInicio
	 */
	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	/**
	 * @param banderaInicio the banderaInicio to set
	 */
	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	/**
	 * @return the isinTemp
	 */
	public String getIsinTemp() {
		return isinTemp;
	}

	/**
	 * @param isinTemp the isinTemp to set
	 */
	public void setIsinTemp(String isinTemp) {
		this.isinTemp = isinTemp;
	}

	/**
	 * @return the idHistorico
	 */
	public Long getIdHistorico() {
		return idHistorico;
	}

	/**
	 * @param idHistorico the idHistorico to set
	 */
	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	/**
	 * @return the isinValidoRequerido
	 */
	public boolean isIsinValidoRequerido() {
		return isinValidoRequerido;
	}

	/**
	 * @param isinValidoRequerido the isinValidoRequerido to set
	 */
	public void setIsinValidoRequerido(boolean isinValidoRequerido) {
		this.isinValidoRequerido = isinValidoRequerido;
	}

	/**
	 * @return the sufrioModificacion
	 */
	public boolean isSufrioModificacion() {
		return sufrioModificacion;
	}

	/**
	 * @param sufrioModificacion the sufrioModificacion to set
	 */
	public void setSufrioModificacion(boolean sufrioModificacion) {
		this.sufrioModificacion = sufrioModificacion;
	}

	/**
	 * @return the instruccionGuardar
	 */
	public InstruccionCapitalesVO getInstruccionGuardar() {
		return instruccionGuardar;
	}

	/**
	 * @param instruccionGuardar the instruccionGuardar to set
	 */
	public void setInstruccionGuardar(InstruccionCapitalesVO instruccionGuardar) {
		this.instruccionGuardar = instruccionGuardar;
	}

	/**
	 * @return the tagsOpcionesIso
	 */
	public String[] getTagsOpcionesIso() {
		return tagsOpcionesIso;
	}

	/**
	 * @param tagsOpcionesIso the tagsOpcionesIso to set
	 */
	public void setTagsOpcionesIso(String[] tagsOpcionesIso) {
		this.tagsOpcionesIso = tagsOpcionesIso;
	}

	/**
	 * @return the idBitacora
	 */
	public Long getIdBitacora() {
		return idBitacora;
	}

	/**
	 * @param idBitacora the idBitacora to set
	 */
	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	/**
	 * @return the listaTagsOpciones
	 */
	public List<SelectItem> getListaTagsOpciones() {
		return listaTagsOpciones;
	}

	/**
	 * @param listaTagsOpciones the listaTagsOpciones to set
	 */
	public void setListaTagsOpciones(List<SelectItem> listaTagsOpciones) {
		this.listaTagsOpciones = listaTagsOpciones;
	}

	/**
	 * @return the contieneTags
	 */
	public boolean isContieneTags() {
		return contieneTags;
	}

	/**
	 * @param contieneTags the contieneTags to set
	 */
	public void setContieneTags(boolean contieneTags) {
		this.contieneTags = contieneTags;
	}

	/**
	 * @return the contieneOpciones
	 */
	public boolean isContieneOpciones() {
		return contieneOpciones;
	}

	/**
	 * @param contieneOpciones the contieneOpciones to set
	 */
	public void setContieneOpciones(boolean contieneOpciones) {
		this.contieneOpciones = contieneOpciones;
	}
}
