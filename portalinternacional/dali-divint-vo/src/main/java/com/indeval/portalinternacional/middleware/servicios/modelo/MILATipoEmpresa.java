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
@Table(name = "C_MILA_TIPO_EMPRESA")
public class MILATipoEmpresa implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5463912108742919060L;

	@Id
	@Column(name = "ID_TIPO_EMPRESA")
	private Long idTipoEmpresa;
	
	@Column(name = "DESCRIPCION_MILA")
	private String descripcionMila;
	
	@Column(name = "DESCRIPCION_INDEVAL")
	private String descripcionIndeval;
	
	

	public MILATipoEmpresa() {
		super();
	}
	
	public MILATipoEmpresa(Long idTipoEmpresa) {
		super();
		this.idTipoEmpresa = idTipoEmpresa;
	}



	/**
	 * @return the idTipoEmprsa
	 */
	public Long getIdTipoEmpresa() {
		return idTipoEmpresa;
	}

	/**
	 * @param idTipoEmprsa the idTipoEmprsa to set
	 */
	public void setIdTipoEmpresa(Long idTipoEmpresa) {
		this.idTipoEmpresa = idTipoEmpresa;
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