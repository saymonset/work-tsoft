/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 *
 * 10/09/2012
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Josue Hernandez Ramirez
 * @version 1.0
 *
 */
@XStreamAlias("mensajeSic")
public class EstadoMensajeSic {
	
	
	public enum TipoAccion{CAMBIO_ESTADO("CE"), CAMBIO_ESTADO_CAMBIO_BOVEDA("CB");
	 private String tipoAccion;
	 
	    private TipoAccion(String tipoAccion) {
	    this.tipoAccion = tipoAccion;
	    }

	     public String getTipoAccion() {
	    return tipoAccion;
	    }
	}
	
	public enum TipoEstado{AUTORIZADO("AU"), CONFIRMADO("CF"), LIBERAR("LB"), HABILITAR("HA"), CANCELADO("CA"),
	                       CANCEL_SIST("CS"), PENDIENTE_REGRESO("PR"), REGRESO_POSICION("RP"), RETENIDO("RT"), LIBERAR_PARCIAL("LP"), CANCELADO_REMANENTE("CR"),
	                       CANCELADO_PARCIAL("CP"), CONFIRMADA_PARCIAL("COP");
	
		private String tipoEstado;
		
		private TipoEstado(String tipoEstado){
			this.tipoEstado=tipoEstado;
		}
		public String getTipoEstado(){
			return tipoEstado;
		}		
	}
	@XStreamAlias("accion")	
	private String accion;
	
	@XStreamAlias("estado")	
	private String estado;
	
	@XStreamAlias("folioControl")	
	private String folioControl;
	
	@XStreamAlias("id")	
	private String id;
	
	@XStreamAlias("parcialidad")	
	private String parcialidad;
	
	@XStreamAlias("fechaLiquidacion")
	private String fechaLiquidacion;
	
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

	public String getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the parcialidad
	 */
	public String getParcialidad() {
		return parcialidad;
	}

	/**
	 * @param parcialidad the parcialidad to set
	 */
	public void setParcialidad(String parcialidad) {
		this.parcialidad = parcialidad;
	}

}
