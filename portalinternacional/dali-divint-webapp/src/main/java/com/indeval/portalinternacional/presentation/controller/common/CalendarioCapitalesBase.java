package com.indeval.portalinternacional.presentation.controller.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DescFechasAdIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.SubTipoDerechoIntSic;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO;

public abstract class CalendarioCapitalesBase extends ControllerBase  {
	
	private static SimpleDateFormat FORMATO_FECHA_HH_MI_PANTALLA = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	 /** Listas  para llenar combos*/
    private List<SelectItem> listaCustodios;
    private List<SelectItem> listaEstatus;
    private List<SelectItem> listaDivisa;
    private List<SelectItem> listaTipoPagoCAEV;
    private List<String> caevs = new ArrayList<String>();
	private Set<String> caevsSelected;
    private List<SelectItem> listaTipoPagoCAMV;
    private List<SelectItem> listaBovedas;
    private List<SelectItem> listaDestinatarios;
    private List<SelectItem> listaInterimFechasNETT;
    private List<SelectItem> listaTipoMensaje;
    private List<SelectItem> listaEstadoEmision;
    private List<SelectItem> listaACKAMH;
    private List<SelectItem> listaACKBolsas;
    private List<SelectItem> listaEstatusSistema;
    private List<SelectItem> listaEstatusRegistro;
    private List<SelectItem> listaSubTipos;
    private List<SelectItem> listaPaises;
    private Map<String,String> mapaSubtipos;
    private Map<String,String> mapaDescFechasAd;
    private Map<Long,String> mapaImagenesEstadosEnvio;
    private Map<Integer,String> mapaImagenesEnvioMensajes;
    private Map<String, Long> mapaCaevs = new HashMap<String, Long>();
    private List<Long> idsCaevs;
    
    /** Filtro */
    protected ConsultaCapitalesParamsTO calendario = new ConsultaCapitalesParamsTO();
    
    /** Filtro Envio Mensajes */
    protected EnvioCapitalesParamsTO calendarioEnvio = new EnvioCapitalesParamsTO();
    
    /** Filtro Consulta Emisiones */
    protected ConsultaEmisionesCalendarioParamsTO calendarioEmisiones = new ConsultaEmisionesCalendarioParamsTO();
    
    /** Servicios de negocio */
    protected DivisionInternacionalService divisionInternacionalService;
    protected CapitalesDistribucionService capitalesDistribucionService;
    protected ConciliacionEfectivoIntService conciliacionEfectivoIntService;
        
    public abstract String ejecutarConsulta();
    
    /**
     * Datos para cargar el combo de divisas
     * @return
     */
    public List<SelectItem> getDivisas() {
        if (this.listaDivisa == null || this.listaDivisa.isEmpty()) {
        	Divisa[] listaDivisas = capitalesDistribucionService.findAllDivisas();
        	this.listaDivisa = new ArrayList<SelectItem>();
        	if(listaDivisas != null){
        		for (int i = 0; i < listaDivisas.length; i++) {
        			this.listaDivisa.add(new SelectItem(listaDivisas[i].getClaveAlfabetica(),listaDivisas[i].getDescripcion()));
                 }
        	}else{
        		this.listaDivisa.add(new SelectItem("-2", "VACIO"));        		
        	}
         
        }        
        return this.listaDivisa;
    }
 
   /**
    * Datos para cargar el combo de custodios
    * @return
    */
	public List<SelectItem> getCustodios() {
		if (this.listaCustodios == null || this.listaCustodios.isEmpty()) {
			List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
			this.listaCustodios = new ArrayList<SelectItem>();
			if (custodios != null) {
				for (Custodio custodio : custodios) {
					this.listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));
				}
			} else {
				this.listaCustodios.add(new SelectItem("-2", "VACIO"));
			}
		}		
		return this.listaCustodios;
	}
	/**
	 * Datos para cargar el combo de custodiosEmision
	 * @return
	 */
	public List<SelectItem> getCustodiosEmision() {
		if (this.listaCustodios == null || this.listaCustodios.isEmpty()) {
			List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
			this.listaCustodios = new ArrayList<SelectItem>();
			if (custodios != null) {
				for (Custodio custodio : custodios) {
					this.listaCustodios.add(new SelectItem(custodio.getDescripcion(), custodio.getDescripcion()));
				}
			} else {
				this.listaCustodios.add(new SelectItem("-2", "VACIO"));
			}
		}		
		return this.listaCustodios;
	}
	 
    /**
     * Datos para cargar el combo de Estados de mensajes
     */ 
    public List<SelectItem> getEstados() {
        if (this.listaEstatus == null || this.listaEstatus.isEmpty()) {
        	List<EstadoMensajeIntSic> estados = capitalesDistribucionService.obtieneEstadosDerechoIntSic();
        	this.listaEstatus = new ArrayList<SelectItem>();
            if (estados != null) {
                for (EstadoMensajeIntSic estado : estados) {
                	this.listaEstatus.add(new SelectItem(estado.getIdEstadoMensajeIntSic().toString(), estado.getNombreEstado()));
                }
            } else {
            	this.listaEstatus.add(new SelectItem("-2", "VACIO"));
            }
        }                
        return this.listaEstatus;
    }
    
	/**
	 * Obtiene la consulta de Bovedas
	 * 
	 */
	public List<SelectItem> getBovedas(){
		if(this.listaBovedas == null || this.listaBovedas.isEmpty()) {
			List<Boveda> bovedas = divisionInternacionalService.consultaBovedas(DivisionInternacionalService.BOVEDA_VALORES_INTERNACIONAL);
			this.listaBovedas = new ArrayList<SelectItem>();
	    	if(bovedas != null){    		
		    	for (Boveda boveda : bovedas){
		    		this.listaBovedas.add(new SelectItem(boveda.getIdBoveda().toString(),boveda.getDescripcion()));	
		    	}
	    	}else{
	    		this.listaBovedas.add(new SelectItem("-2","VACIO"));
	    	}
		}	    	
    	return this.listaBovedas;
    }  
    
   /**
    * Datos para cargar el combo tipo de Pago CAEV
    * @return
    */
    public List<SelectItem> getTiposPagoCAEV() {
        if (this.listaTipoPagoCAEV == null || this.listaTipoPagoCAEV.isEmpty()) {
        	this.listaTipoPagoCAEV = getTiposPagoMensaje(true);   
        	caevsSelected = new HashSet<String>();

        }
        return this.listaTipoPagoCAEV;
    }
    
    /**
     * Datos para cargar el combo tipo de Pago CAMV
     * @return
     */
    public List<SelectItem> getTiposPagoCAMV() {
        if (this.listaTipoPagoCAMV == null || this.listaTipoPagoCAMV.isEmpty()) {
        	this.listaTipoPagoCAMV = getTiposPagoMensaje(false);         	
        }
        return this.listaTipoPagoCAMV;
    }
    
    /**
     * Datos para cargar el combo Destinatarios
     * @return
     */
    public List<SelectItem> getDestinatarios() {
        if (this.listaDestinatarios == null || this.listaDestinatarios.isEmpty()) {
        	List<DestinatarioIntSic> destinatarios = capitalesDistribucionService.obtieneDestinatariosIntSicByEstado(Long.valueOf(1));
        	this.listaDestinatarios = new ArrayList<SelectItem>();
            if (destinatarios != null) {
                for (DestinatarioIntSic destinatario : destinatarios) {
                	this.listaDestinatarios.add(new SelectItem(String.valueOf(destinatario.getBiccode()), destinatario.getNombre()));
                }
            } else {
            	this.listaDestinatarios.add(new SelectItem("-2", "VACIO"));
            }
        }                
        return this.listaDestinatarios;
    } 
    
    /**
     * Datos para cargar los combos Fechas Adicionales, FEE y Asset Manager
     * @return
     */
    public List<SelectItem> getInterimFechasFEE(){
    	if(listaInterimFechasNETT == null || listaInterimFechasNETT.isEmpty()){
    		listaInterimFechasNETT =  new ArrayList<SelectItem>();
    	
    		listaInterimFechasNETT.add(new SelectItem("0", "Con Valor"));
    		listaInterimFechasNETT.add(new SelectItem("1", "Sin Valor"));
    	}
    	return listaInterimFechasNETT;
    }
    
    /**
     * Datos para cargar el combo Tipo Mensaje
     * @return
     */
    public List<SelectItem> getTipoMensajeCombo(){
    	if(listaTipoMensaje == null || listaTipoMensaje.isEmpty()){
    		listaTipoMensaje =  new ArrayList<SelectItem>();
    	
    		listaTipoMensaje.add(new SelectItem("564", "564"));    		
    		listaTipoMensaje.add(new SelectItem("566", "566"));
    		listaTipoMensaje.add(new SelectItem("567", "567"));
    		listaTipoMensaje.add(new SelectItem("568", "568"));
    	}
    	return listaTipoMensaje;
    }
    
    /**
     * Datos para cargar el combo Estado de la Emision
     * @return
     */
    public List<SelectItem> getEstadoEmisionCombo(){
    	if(listaEstadoEmision == null || listaEstadoEmision.isEmpty()){
    		listaEstadoEmision =  new ArrayList<SelectItem>();
    	
    		listaEstadoEmision.add(new SelectItem("LISTADA", "LISTADA"));
    		listaEstadoEmision.add(new SelectItem("DESLISTADA", "DESLISTADA"));
    		listaEstadoEmision.add(new SelectItem("MILA", "MILA"));    	
    		listaEstadoEmision.add(new SelectItem("FULL LISTED", "FULL LISTED"));    	
    		listaEstadoEmision.add(new SelectItem("DEUDA", "DEUDA"));    	
    	}
    	return listaEstadoEmision;
    }
    
    /**
     * Datos para cargar el combo Estatus Sistema
     * @return
     */
    public List<SelectItem> getEstatusSistemaCombo(){
    	if(listaEstatusSistema == null || listaEstatusSistema.isEmpty()){
    		listaEstatusSistema =  new ArrayList<SelectItem>();
    		
    		listaEstatusSistema.add(new SelectItem("VIGENTE", "VIGENTE"));
    		listaEstatusSistema.add(new SelectItem("CANCELADO", "CANCELADO"));
    		listaEstatusSistema.add(new SelectItem("SIN ESTATUS", "SIN ESTATUS"));    	
    	}
    	return listaEstatusSistema;
    }
    
    /**
     * Datos para cargar el combo EstatusE
     * @return
     */
    public List<SelectItem> getEstatusRegistroCombo(){
    	if(listaEstatusRegistro == null || listaEstatusRegistro.isEmpty()){
    		listaEstatusRegistro =  new ArrayList<SelectItem>();
    		
    		listaEstatusRegistro.add(new SelectItem("BAJA", "BAJA"));
    		listaEstatusRegistro.add(new SelectItem("AUTORIZADA", "AUTORIZADA"));
    		listaEstatusRegistro.add(new SelectItem("REGISTRADA", "REGISTRADA"));    	
    		listaEstatusRegistro.add(new SelectItem("LIBERADA", "LIBERADA"));    	
    		listaEstatusRegistro.add(new SelectItem("ELIMINADA", "ELIMINADA"));    	
    		listaEstatusRegistro.add(new SelectItem("PREAUTORIZADA", "PREAUTORIZADA"));    	
    	}
    	return listaEstatusRegistro;
    }
	
    /**
     * Datos para cargar el combo ACK AMH
     * @return
     */
    public List<SelectItem> getACKAMHS(){
    	if(listaACKAMH == null || listaACKAMH.isEmpty()){
    		listaACKAMH =  new ArrayList<SelectItem>();
    	
    		listaACKAMH.add(new SelectItem("0", "Enviado"));
    		listaACKAMH.add(new SelectItem("1", "Confirmado"));
    		listaACKAMH.add(new SelectItem("2", "Error NACK"));
    			
    	}
    	return listaACKAMH;
    }
    
    /**
     * Datos para cargar el combo ACK Bolsas
     * @return
     */
    public List<SelectItem> getACKBolsas(){
    	if(listaACKBolsas == null || listaACKBolsas.isEmpty()){
    		listaACKBolsas =  new ArrayList<SelectItem>();
  
    		listaACKBolsas.add(new SelectItem("0", "Enviado"));
    		listaACKBolsas.add(new SelectItem("1", "Confirmado"));
    		listaACKBolsas.add(new SelectItem("2", "Error NACK"));
    			
    	}
    	return listaACKBolsas;
    }
    
    /**
     * Datos para cargar el combo de paises en consulta emisiones
     * @return
     */
    public List<SelectItem> getPaises(){
    	if (this.listaPaises == null || this.listaPaises.isEmpty()) {
			List<PaisInt> paises = conciliacionEfectivoIntService.consultaPaises();
			this.listaPaises = new ArrayList<SelectItem>();
			if (paises != null) {
				for (PaisInt pais : paises) {
					this.listaPaises.add(new SelectItem(pais.getNombrePais(), pais.getNombrePais()));
				}
			} else {
				this.listaPaises.add(new SelectItem("-2", "VACIO"));
			}
		}		
    	return listaPaises;
    }
   
        
    /**
     * Auxiliar para determinar el tipo de pago solicitado
     * @return
     */
    private List<SelectItem> getTiposPagoMensaje(final Boolean isCAEV) {
        List<TipoPagoInt> tipos = divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
        List<SelectItem> listaPagos = new ArrayList<SelectItem>();
        caevs.add("TODOS");	
        if (tipos != null) {
            for (TipoPagoInt tp : tipos) {
                listaPagos.add(new SelectItem(tp.getId().toString(), tp.getClavePago()));             
             if(isCAEV){
                	mapaCaevs.put(tp.getClavePago(), tp.getId());
                	  caevs.add(tp.getClavePago());	
             }
              
            }
        } else {
            listaPagos.add(new SelectItem("-2", "VACIO"));
        }
        return listaPagos;
    }
    
    /**
     * Datos para cargar el combo Subtipo Derecho
     * @return
     */
	public List<SelectItem> getSubTipoDerechos() {
		if(this.listaSubTipos == null || this.listaSubTipos.isEmpty()){
			List<SubTipoDerechoIntSic> subTipoDerecho = capitalesDistribucionService.obtenSubTipoDerechos();
			this.listaSubTipos = new ArrayList<SelectItem>();
		    this.mapaSubtipos = new HashMap<String,String>();
			if (subTipoDerecho != null) {
				for (SubTipoDerechoIntSic sub : subTipoDerecho) {
					mapaSubtipos.put(sub.getQualifier(), sub.getDescripcion());
					listaSubTipos.add(new SelectItem(sub.getQualifier(), sub.getQualifier()));
				}
			} else {
				listaSubTipos.add(new SelectItem("-2", "VACIO"));
			}
		}			
		return listaSubTipos;
	}
	
	/**
	 * Datos para cargar el mapa Descripcion Fechas Ad
	 * @return
	 */
	public void cargaMapaDescFechasAd() {
		if(this.mapaDescFechasAd == null){
			List<DescFechasAdIntSic> descFechasAd = capitalesDistribucionService.getDescFechasAd();			
			this.mapaDescFechasAd = new HashMap<String,String>();
			if (descFechasAd != null) {
				for (DescFechasAdIntSic desc : descFechasAd) {
					mapaDescFechasAd.put(desc.getQualifier(), desc.getDescripcion());
					
				}
			} 		
		}
	}
	
	/**
	 * Icicializa el los valores del mapa ImagenesEstadosEnvio
	 */
	public void cargaMapaImagenesEstadosEnvio() {
		if(this.mapaImagenesEstadosEnvio == null){
			this.mapaImagenesEstadosEnvio = new HashMap<Long,String>();
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("1"), "confirmado.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("2"), "enviado.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("3"), "notificado.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("4"), "error_Reenvio.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("5"), "error_MOI.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("6"), "error_Nack.png");
			this.mapaImagenesEstadosEnvio.put(Long.valueOf("7"), "error_AMH.png");			
		}				
	}
	
	/**
	 * Icicializa el los valores del mapa ImagenesEnvioMensajes
	 */
	public void cargaMapaImagenesEnvioMensajes() {
		if(this.mapaImagenesEnvioMensajes== null){
			this.mapaImagenesEnvioMensajes = new HashMap<Integer,String>();
			this.mapaImagenesEnvioMensajes.put(0, "enviado.png");
			this.mapaImagenesEnvioMensajes.put(1, "confirmado.png");
			this.mapaImagenesEnvioMensajes.put(2, "error_Nack.png");
				
		}				
	}
	
	public List<Long> cargaListaIdsCaevs(){
		idsCaevs = new ArrayList<Long>();
		if(caevsSelected.contains("TODOS")){
			return null;
		}
		for(String caev: caevsSelected){
		idsCaevs.add(mapaCaevs.get(caev));
		}
		return idsCaevs;
	}
        
    /**
     * Regresa el valor de la divisa seleccionada
     * @return
     */
    public String getSelectedDivisa() {
		String resultado = this.getSelected(this.calendario.getDivisa(), this.listaDivisa);
		return resultado == null ? "TODAS" : resultado;  		
	}
	
    /**
     * Regresa el valor de TipoPagoCAMV seleccionado
     * @return
     */
	public String getSelectedTipoPagoCAMV() {
		String resultado = this.getSelected(this.calendario.getTipoPagoCAMV(), this.listaTipoPagoCAMV);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor de TipoPagoCAEV seleccionado
	 * @return
	 */
	public String getSelectedTipoPagoCAEV() {
		String resultado = this.getSelected(this.calendario.getTipoPagoCAEV(), this.listaTipoPagoCAEV);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor del Estado seleccionado
	 * @return
	 */
	public String getSelectedEstado() {
		String resultado = this.getSelected(this.calendario.getEstado(), this.listaEstatus);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor del Custodio seleccionado
	 * @return
	 */
	public String getSelectedCustodio() {
		String resultado = this.getSelected(this.calendario.getCustodio(), this.listaCustodios);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor de la Boveda seleccionada
	 * @return
	 */
	public String getSelectedBoveda() {
		String resultado = this.getSelected(this.calendario.getBoveda(), this.listaBovedas);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Fechas Adicionales
	 * @return
	 */
	public String getSelectedFechasAdicionales(){
		String resultado = getSelected(calendario.getFechasAdicionales(), listaInterimFechasNETT);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Asset Manager de Consulta Emisiones
	 */
	public String getSelectedEmisiones(){
		String resultado = getSelected(calendarioEmisiones.getAssetManager(), listaInterimFechasNETT);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en importe NETT
	 * @return
	 */
	public String getSelectedNett(){
		String resultado = getSelected(calendario.getNett(), listaInterimFechasNETT);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en importe NETT
	 * @return
	 */
	public String getSelectedFee(){
		String resultado = getSelected(calendario.getFee(), listaInterimFechasNETT);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Tipo mensaje
	 * @return
	 */
	public String getSelectedTipoMensaje(){
		String resultado = getSelected(calendario.getTipoMensaje(), listaTipoMensaje);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Estado Emision
	 * @return
	 */
	public String getSelectedEstadoEmision(){
		String resultado = getSelected(calendario.getEstadoEmision(), listaEstadoEmision);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa el valor seleccionado en Destinatario
	 */
	public String getSelectedDestinatario(){
		String resultado = getSelected(calendarioEnvio.getDestinatario(), listaDestinatarios);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa el valor seleccionado en ACKAMH
	 */
	public String getSelectedACKAMH(){
		String resultado = getSelected(calendarioEnvio.getACKAMH(), listaACKAMH);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en ACKBolsas
	 */
	public String getSelectedACKBolsas(){
		String resultado = getSelected(calendarioEnvio.getACKBolsa(), listaACKBolsas);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa el valor seleccionado en Estatus Sistema
	 */
	public String getSelectedEstatusSistema(){
		String resultado = getSelected(calendarioEmisiones.getEstatusSistema(), listaEstatusSistema);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa el valor seleccionado en Estatus Registro
	 */
	public String getSelectedEstatusRegistro(){
		String resultado = getSelected(calendarioEmisiones.getEstatusEmision(), listaEstatusRegistro);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Estado Emision
	 * @return
	 */
	public String getSelectedEstadoEmisiones(){
		String resultado = getSelected(calendarioEmisiones.getListada(), listaEstadoEmision);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa el valor seleccionado en el combo Subtipo Derecho
	 * @return
	 */
	public String getSelectedSubTipoDerecho(){
		String resultado = getSelected(calendario.getSubTipoDerecho(), listaSubTipos);
		return resultado == null ? "TODOS" : resultado;
	}
	/**
	 * Regresa la descripci&oacute;n del valor en el combo seleccionado
	 * @param key
	 * @param lista
	 * @return
	 */
	private String getSelected(final String key, final List<SelectItem> lista) {
		String resultado = null;
		for (SelectItem item : lista) {
			if (key != null && item.getValue().equals(key)) {
				resultado = item.getLabel();
				break;
			}
		}
		return resultado;
	}
	
	/**
	 * Regresa el valor del Custodio seleccionado
	 * @return
	 */
	public String getSelectedCustodioEmisiones() {
		String resultado = this.getSelected(this.calendarioEmisiones.getDetalleCustodio(), this.listaCustodios);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor del Pais seleccionado
	 * @return
	 */
	public String getSelectedPais() {
		String resultado = this.getSelected(this.calendarioEmisiones.getPaisOrigen(), this.listaPaises);
		return resultado == null ? "TODOS" : resultado;
	}
	
	
	/**
	 * Agrega o resta d&iacute;as a la fecha dada
	 * @param fechaParam
	 * @param dias
	 * @return
	 */
	protected Date modificaFecha(Date fechaParam, int dias) {
		Calendar cal = Calendar.getInstance();
		Date fecha = new Date();
		cal.setTime(fechaParam);
		cal.add(Calendar.DAY_OF_MONTH, dias);
		fecha = cal.getTime();
		return fecha;
	}
	
	/**
	 * Calcula la diferencia en d&iacute;as entre dos fechas
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return Diferencia en d&iacute;as
	 */
	protected int calcularDiferenciaFechasEnDias(Date fechaInicial, Date fechaFinal) {
		Long diferencia = fechaFinal.getTime() - fechaInicial.getTime();
		Long difDias = diferencia / (24 * 60 * 60 * 1000);
		return difDias.intValue();
	}
    
    /**
  	 * @param divisionInternacionalService the divisionInternacionalService to set
  	 */
  	public void setDivisionInternacionalService(DivisionInternacionalService divisionInternacionalService) {
  		this.divisionInternacionalService = divisionInternacionalService;
  	}

  	/**
  	 * @param capitalesDistribucionService the capitalesDistribucionService to set
  	 */
  	public void setCapitalesDistribucionService(CapitalesDistribucionService capitalesDistribucionService) {
  		this.capitalesDistribucionService = capitalesDistribucionService;
  	}
  	
  
	/**
	 * @param conciliacionEfectivoIntService the conciliacionEfectivoIntService to set
	 */
	public void setConciliacionEfectivoIntService(ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * @return the listaCustodios
	 */
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the listaEstatus
	 */
	public List<SelectItem> getListaEstatus() {
		return listaEstatus;
	}

	/**
	 * @param listaEstatus the listaEstatus to set
	 */
	public void setListaEstatus(List<SelectItem> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	/**
	 * @return the listaDivisa
	 */
	public List<SelectItem> getListaDivisa() {
		return listaDivisa;
	}

	/**
	 * @param listaDivisa the listaDivisa to set
	 */
	public void setListaDivisa(List<SelectItem> listaDivisa) {
		this.listaDivisa = listaDivisa;
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
	 * @return the caevs
	 */
	public List<String> getCaevs() {
		return caevs;
	}

	/**
	 * @param caevs the caevs to set
	 */
	public void setCaevs(List<String> caevs) {
		this.caevs = caevs;
	}

	/**
	 * @return the listaTipoPagoCAMV
	 */
	public List<SelectItem> getListaTipoPagoCAMV() {
		return listaTipoPagoCAMV;
	}

	/**
	 * @param listaTipoPagoCAMV the listaTipoPagoCAMV to set
	 */
	public void setListaTipoPagoCAMV(List<SelectItem> listaTipoPagoCAMV) {
		this.listaTipoPagoCAMV = listaTipoPagoCAMV;
	}

	public List<SelectItem> getListaBovedas() {
		return listaBovedas;
	}

	public void setListaBovedas(List<SelectItem> listaBovedas) {
		this.listaBovedas = listaBovedas;
	}

	public List<SelectItem> getListaDestinatarios() {
		return listaDestinatarios;
	}

	public void setListaDestinatarios(List<SelectItem> listaDestinatarios) {
		this.listaDestinatarios = listaDestinatarios;
	}

	/**
	 * @return the listaInterimFechasNETT
	 */
	public List<SelectItem> getListaInterimFechasNETT() {
		return listaInterimFechasNETT;
	}

	/**
	 * @param listaInterimFechasNETT the listaInterimFechasNETT to set
	 */
	public void setListaInterimFechasNETT(List<SelectItem> listaInterimFechasNETT) {
		this.listaInterimFechasNETT = listaInterimFechasNETT;
	}

	public List<SelectItem> getListaTipoMensaje() {
		return listaTipoMensaje;
	}

	public void setListaTipoMensaje(List<SelectItem> listaTipoMensaje) {
		this.listaTipoMensaje = listaTipoMensaje;
	}

	public List<SelectItem> getListaEstadoEmision() {
		return listaEstadoEmision;
	}

	public void setListaEstadoEmision(List<SelectItem> listaEstadoEmision) {
		this.listaEstadoEmision = listaEstadoEmision;
	}

	/**
	 * @return the listaACKAMH
	 */
	public List<SelectItem> getListaACKAMH() {
		return listaACKAMH;
	}

	/**
	 * @param listaACKAMH the listaACKAMH to set
	 */
	public void setListaACKAMH(List<SelectItem> listaACKAMH) {
		this.listaACKAMH = listaACKAMH;
	}

	/**
	 * @return the listaACKBolsas
	 */
	public List<SelectItem> getListaACKBolsas() {
		return listaACKBolsas;
	}

	/**
	 * @param listaACKBolsas the listaACKBolsas to set
	 */
	public void setListaACKBolsas(List<SelectItem> listaACKBolsas) {
		this.listaACKBolsas = listaACKBolsas;
	}

	/**
	 * @return the listaEstatusSistema
	 */
	public List<SelectItem> getListaEstatusSistema() {
		return listaEstatusSistema;
	}

	/**
	 * @param listaEstatusSistema the listaEstatusSistema to set
	 */
	public void setListaEstatusSistema(List<SelectItem> listaEstatusSistema) {
		this.listaEstatusSistema = listaEstatusSistema;
	}

	/**
	 * @return the listaEstatusE
	 */
	public List<SelectItem> getListaEstatusE() {
		return listaEstatusRegistro;
	}

	/**
	 * @param listaEstatusE the listaEstatusE to set
	 */
	public void setListaEstatusE(List<SelectItem> listaEstatusE) {
		this.listaEstatusRegistro = listaEstatusE;
	}

	/**
	 * @return the listaSubTipos
	 */
	public List<SelectItem> getListaSubTipos() {
		return listaSubTipos;
	}

	/**
	 * @param listaSubTipos the listaSubTipos to set
	 */
	public void setListaSubTipos(List<SelectItem> listaSubTipos) {
		this.listaSubTipos = listaSubTipos;
	}

	/**
	 * @return the mapaSubtipos
	 */
	public Map<String, String> getMapaSubtipos() {
		return mapaSubtipos;
	}

	/**
	 * @param mapaSubtipos the mapaSubtipos to set
	 */
	public void setMapaSubtipos(Map<String, String> mapaSubtipos) {
		this.mapaSubtipos = mapaSubtipos;
	}

	/**
	 * @return the mapaDescFechasAd
	 */
	public Map<String, String> getMapaDescFechasAd() {
		return mapaDescFechasAd;
	}

	/**
	 * @param mapaDescFechasAd the mapaDescFechasAd to set
	 */
	public void setMapaDescFechasAd(Map<String, String> mapaDescFechasAd) {
		this.mapaDescFechasAd = mapaDescFechasAd;
	}

	/**
	 * @return the mapaImagenesEstadosEnvio
	 */
	public Map<Long, String> getMapaImagenesEstadosEnvio() {			
		return mapaImagenesEstadosEnvio;
	}

	/**
	 * @param mapaImagenesEstadosEnvio the mapaImagenesEstadosEnvio to set
	 */
	public void setMapaImagenesEstadosEnvio(Map<Long, String> mapaImagenesEstadosEnvio) {
		this.mapaImagenesEstadosEnvio = mapaImagenesEstadosEnvio;
	}

	/**
	 * @return the mapaImagenesEnvioMensajes
	 */
	public Map<Integer, String> getMapaImagenesEnvioMensajes() {
		return mapaImagenesEnvioMensajes;
	}

	/**
	 * @param mapaImagenesEnvioMensajes the mapaImagenesEnvioMensajes to set
	 */
	public void setMapaImagenesEnvioMensajes(Map<Integer, String> mapaImagenesEnvioMensajes) {
		this.mapaImagenesEnvioMensajes = mapaImagenesEnvioMensajes;
	}

	/**
	 * @return the caevsSelected
	 */
	public Set<String> getCaevsSelected() {
		return caevsSelected;
	}

	/**
	 * @param caevsSelected the caevsSelected to set
	 */
	public void setCaevsSelected(Set<String> caevsSelected) {
		this.caevsSelected = caevsSelected;
	}

	/**
	 * @return the mapacCaevs
	 */
	public Map<String, Long> getMapacCaevs() {
		return mapaCaevs;
	}

	/**
	 * @param mapacCaevs the mapacCaevs to set
	 */
	public void setMapacCaevs(Map<String, Long> mapacCaevs) {
		this.mapaCaevs = mapacCaevs;
	}
	
	
	public  String getFechaFormato(Date fecha){
		return fecha != null ? FORMATO_FECHA_HH_MI_PANTALLA.format(fecha):"";					
	}
 
	
	
	
    
}
