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
@Table(name = "T_NARRATIVA_INT_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "T_NARRATIVA_INT_SEQ", allocationSize = 1, initialValue = 1)
public class NarrativaCapitales implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_NARRATIVA_INT")
	private Long id;
	
	@Column(name = "NARRATIVA")
	private String narrativa;
	
	@Column(name = "ORIGEN")
	private String origen;
	
	@Column(name = "ID_CALENDARIO_INT")
	private Long idCalendario;
	
	@Column(name = "ID_HIST_DERECHO_INT")
	private Long idHistorico;
	
	@Column(name = "USUARIO")
	private String usuario;

	public NarrativaCapitales() {
		super();		
	}

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
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the idCalendario
	 */
	public Long getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	

}
