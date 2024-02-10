package com.indeval.portalinternacional.middleware.servicios.modelo;


import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("OperacionesConciliacionInt")
public class OperacionesConciliacionInt{
	
	public enum TipoOperacionConciliacion{
		CONCILIA("CO"),
		CONCILIACION_NACIONAL("CN"),
		REPORTE_AUDITORIA("RA"),
		REPORTE_AUDITORIA_EFECTIVO("RAE");
	
		private String tipoOperacion;
	 
	    private TipoOperacionConciliacion(String tipoOperacion) {
	    	this.tipoOperacion = tipoOperacion;
	    }

	     public String getTipoOperacion() {
	    	 return tipoOperacion;
	    }
	}
	
	
	@XStreamAlias("operacion") 
	@XStreamAsAttribute
	private String operacion;
	
	@XStreamAlias("idConciliacion") 
	private Long idConciliacion;
	
	@XStreamAlias("detalle")
	private DetalleConciliacionEfectivoIntDTO detalle;
	
	
	/**
	 * @return the accion
	 */
	public String getOperacion() {
		return operacion;
	}
	
	/**
	 * @param accion the accion to set
	 */
	public void setOperacion(TipoOperacionConciliacion operacion) {
		this.operacion = operacion.getTipoOperacion();
	}

	/**
	 * @return the idConciliacion
	 */
	public Long getIdConciliacion() {
		return idConciliacion;
	}

	/**
	 * @param idConciliacion the idConciliacion to set
	 */
	public void setIdConciliacion(Long idConciliacion) {
		this.idConciliacion = idConciliacion;
	}

	/**
	 * @return the detalle
	 */
	public DetalleConciliacionEfectivoIntDTO getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(DetalleConciliacionEfectivoIntDTO detalle) {
		this.detalle = detalle;
	}
	
}
