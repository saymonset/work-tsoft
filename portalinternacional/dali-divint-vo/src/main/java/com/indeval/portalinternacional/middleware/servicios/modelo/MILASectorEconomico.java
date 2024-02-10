package com.indeval.portalinternacional.middleware.servicios.modelo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author arodriguez
 * 
 */
@Entity
@Table(name = "C_MILA_SECTOR_ECONOMICO")
public class MILASectorEconomico implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5463912108742919060L;

	@Id
	@Column(name = "ID_SECTOR_ECONOMICO")
	private Long idSectorEconomico;
	
	@Column(name = "DESCRIPCION_MILA")
	private String descripcionMila;
	
	@Column(name = "DESCRIPCION_INDEVAL")
	private String descripcionIndeval;

	
	public MILASectorEconomico() {
		super();
	}

	public MILASectorEconomico(Long idSectorEconomico) {
		super();
		this.idSectorEconomico = idSectorEconomico;
	}

	/**
	 * @return the idSectorEconomico
	 */
	public Long getIdSectorEconomico() {
		return idSectorEconomico;
	}

	/**
	 * @param idSectorEconomico the idSectorEconomico to set
	 */
	public void setIdSectorEconomico(Long idSectorEconomico) {
		this.idSectorEconomico = idSectorEconomico;
	}

	/**
	 * @return the descripcionMila
	 */
	public String getDescripcionMila() {
		return descripcionMila;
	}

	/**
	 * @param descripcionMila the descripcionMila to set
	 */
	public void setDescripcionMila(String descripcionMila) {
		this.descripcionMila = descripcionMila;
	}

	/**
	 * @return the descripcionIndeval
	 */
	public String getDescripcionIndeval() {
		return descripcionIndeval;
	}

	/**
	 * @param descripcionIndeval the descripcionIndeval to set
	 */
	public void setDescripcionIndeval(String descripcionIndeval) {
		this.descripcionIndeval = descripcionIndeval;
	}
}