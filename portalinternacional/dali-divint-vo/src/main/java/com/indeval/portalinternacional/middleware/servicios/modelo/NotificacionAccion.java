package com.indeval.portalinternacional.middleware.servicios.modelo;


import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("NotificacionAccion")
public class NotificacionAccion{
	
	public enum TipoAccion{CAMBIO_ESTADO("CE"), MENSAJE_RETIRO("MR");
	 private String tipoAccion;
	 
	    private TipoAccion(String tipoAccion) {
	    this.tipoAccion = tipoAccion;
	    }

	     public String getTipoAccion() {
	    return tipoAccion;
	    }
	}
	
	public enum TipoEstado{REGISTRADO("RG"), AUTORIZADO("AU"),
		PREVIO("PV"),CONFIRMADO("CF"),PRELIQUIDADO("PL"),
		LIQUIDADO("LI"),CANCELADO("CA"),REVERSAL("RV"),
		CORREGIDO("CO"),SUSPENDIDO("SU");
	
		private String tipoEstado;
		
		private TipoEstado(String tipoEstado){
			this.tipoEstado=tipoEstado;
		}
		public String getTipoEstado(){
			return tipoEstado;
		}		
	}
	@XStreamAlias("accion") 
	@XStreamAsAttribute
	private String accion;
	
	@XStreamAlias("estado") 
	@XStreamAsAttribute
	private String estado;
	
	@XStreamImplicit(itemFieldName="derecho")
    private List<DerechoMensaje> derechos;
	
	
	public NotificacionAccion(){
		derechos=new ArrayList<DerechoMensaje>();
	}
	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}
	
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(TipoAccion accion) {
		this.accion = accion.getTipoAccion();
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(TipoEstado estado) {
		this.estado = estado.getTipoEstado();
	}
	/**
	 * @return the ids
	 */
	public void addDerecho(DerechoMensaje derecho){
		derechos.add(derecho);
	}
	
	public void addCalendarioDerecho(CalendarioDerechos cal){
		DerechoMensaje der = new DerechoMensaje();
        der.setId(cal.getIdCalendario());
        der.setEmisora(cal.getEmisora());
        der.setSerie(cal.getSerie());
        der.setCupon(cal.getCupon());
        der.setIsin(cal.getIsin());
        der.setFechaCorte(cal.getFechaCorte());
        der.setFechaPago(cal.getFechaPago());
        addDerecho(der);
	}

}
