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
@Table(name = "C_MILA_TIPO_DOCUMENTO")
public class MILATipoDocumento implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5463912108742919060L;

	@Id
	@Column(name = "ID_TIPO_DOCUMENTO")
	private Long idTipoDocumento;
	
	@Column(name = "CODIGO_MILA")
	private Long codigoMila;
	
	@Column(name = "DESCRIPCION_MILA")
	private String descripcionMila;
	
	@Column(name = "DESCRIPCION_INDEVAL")
	private String descripcionIndeval;
	
	@Column(name = "PERSONA_FISICA")
	private boolean personaFisica;
	
	@Column(name = "ID_PAIS")
	private Integer idPais;
	

	/**
	 * 
	 */
	public MILATipoDocumento(){
		super();
	}
	
	/**
	 * @param idTipoDocumento
	 */
	public MILATipoDocumento(Long idTipoDocumento){
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * @return the idTipoDocumento
	 */
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
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

	/**
	 * @return the codigoMila
	 */
	public Long getCodigoMila() {
		return codigoMila;
	}

	/**
	 * @param codigoMila the codigoMila to set
	 */
	public void setCodigoMila(Long codigoMila) {
		this.codigoMila = codigoMila;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	/**
	 * @return the personaFisica
	 */
	public boolean isPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param personaFisica the personaFisica to set
	 */
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}
}