package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_INSTRUCCIONES_INT_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "T_INSTRUCCIONES_INT_SIC_SEQ", allocationSize = 1, initialValue = 1)
public class InstruccionCapitales implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_T_INSTRUCCIONES_INT_SIC")
	private Long id;
	
	@Column(name = "ID_HIST_DERECHO_INT")
	private Long idHistorico;
	
	@Column(name = "ID_TIPO_DERECHO_CAEV")
	private Long idTipoDerechoCaev;
	
	@Column(name = "LIGA")
	private Long liga;
	
	@Column(name = "TAGS_ELIMINACION")
	private String tagsEliminacion;
	
	@Column(name = "OPCIONES_MENSAJE")
	private String opcionesMensajes;
	
	public InstruccionCapitales(){}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the idTipoDerechoCaev
	 */
	public Long getIdTipoDerechoCaev() {
		return idTipoDerechoCaev;
	}

	/**
	 * @param idTipoDerechoCaev the idTipoDerechoCaev to set
	 */
	public void setIdTipoDerechoCaev(Long idTipoDerechoCaev) {
		this.idTipoDerechoCaev = idTipoDerechoCaev;
	}

	/**
	 * @return the liga
	 */
	public Long getLiga() {
		return liga;
	}

	/**
	 * @param liga the liga to set
	 */
	public void setLiga(Long liga) {
		this.liga = liga;
	}

	/**
	 * @return the tagsEliminacion
	 */
	public String getTagsEliminacion() {
		return tagsEliminacion;
	}

	/**
	 * @param tagsEliminacion the tagsEliminacion to set
	 */
	public void setTagsEliminacion(String tagsEliminacion) {
		this.tagsEliminacion = tagsEliminacion;
	}

	/**
	 * @return the opcionesMensajes
	 */
	public String getOpcionesMensajes() {
		return opcionesMensajes;
	}

	/**
	 * @param opcionesMensajes the opcionesMensajes to set
	 */
	public void setOpcionesMensajes(String opcionesMensajes) {
		this.opcionesMensajes = opcionesMensajes;
	}

}
