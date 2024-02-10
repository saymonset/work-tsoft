package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author javier.perez
 * Entidad para la Vista  V_HISTORICO_NARRATIVA_INT
 *
 */
@Entity
@Table(name="V_HISTORICO_NARRATIVA_INT")
public class VHistoricoNarrativas implements Serializable {
	
	private static final long serialVersionUID = 5858581646477894518L;
	
	@Id
	@Column(name="ID_NARRATIVA_INT")
	private Long idNarrativa;
	
	@Column(name = "NARRATIVA")
	private String narrativa;
	
	@Column(name="ID_HIST_DERECHO_INT")
	private Long idHistorico;
	
	@Column(name="FECHA_HORA_RECEPCION")
	private Date fechaHoraRecepcion;

	/**
	 * @return the idNarrativa
	 */
	public Long getIdNarrativa() {
		return idNarrativa;
	}

	/**
	 * @param idNarrativa the idNarrativa to set
	 */
	public void setIdNarrativa(Long idNarrativa) {
		this.idNarrativa = idNarrativa;
	}

	/**
	 * @return the narrativa
	 */
	public String getNarrativa() {
		return narrativa;
	}

	/**
	 * @param narrativa the narrativa to set
	 */
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
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
	 * @return the fechaHoraRecepcion
	 */
	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}

	/**
	 * @param fechaHoraRecepcion the fechaHoraRecepcion to set
	 */
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
	}
}
