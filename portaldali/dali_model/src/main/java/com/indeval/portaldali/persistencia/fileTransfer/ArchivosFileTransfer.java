/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistencia.fileTransfer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Modelo que representa la tabla T_ARCHIVOS_FILE_TRANSFER
 * 
 * @author Pablo Balderas
 */
@Entity
@Table(name="T_ARCHIVOS_FILE_TRANSFER")
@SequenceGenerator(name = "secuencia", sequenceName = "SEQ_T_ARCHIVOS_FILE_TRANSFER", allocationSize = 1, initialValue = 1)
public class ArchivosFileTransfer implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -4929786125239477451L;

	@Id
	@Column(name="ID_ARCHIVO_FILE_TRANSFER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
	private Long idArchivoFileTransfer; 
	
	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="ID_INSTITUCION")
	private Long idInstitucion;
	
	@Column(name="FECHA_CARGA")
	private Date fechaCarga;
	
	@Column(name="REGISTROS")
	private String registros;

	/**
	 * Método para obtener el atributo idArchivoFileTransfer
	 * @return El atributo idArchivoFileTransfer
	 */
	public Long getIdArchivoFileTransfer() {
		return idArchivoFileTransfer;
	}

	/**
	 * Método para establecer el atributo idArchivoFileTransfer
	 * @param idArchivoFileTransfer El valor del atributo idArchivoFileTransfer a establecer.
	 */
	public void setIdArchivoFileTransfer(Long idArchivoFileTransfer) {
		this.idArchivoFileTransfer = idArchivoFileTransfer;
	}

	/**
	 * Método para obtener el atributo usuario
	 * @return El atributo usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer el atributo usuario
	 * @param usuario El valor del atributo usuario a establecer.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método para obtener el atributo idInstitucion
	 * @return El atributo idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Método para establecer el atributo idInstitucion
	 * @param idInstitucion El valor del atributo idInstitucion a establecer.
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Método para obtener el atributo fechaCarga
	 * @return El atributo fechaCarga
	 */
	public Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * Método para establecer el atributo fechaCarga
	 * @param fechaCarga El valor del atributo fechaCarga a establecer.
	 */
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	/**
	 * Método para obtener el atributo registros
	 * @return El atributo registros
	 */
	public String getRegistros() {
		return registros;
	}

	/**
	 * Método para establecer el atributo registros
	 * @param registros El valor del atributo registros a establecer.
	 */
	public void setRegistros(String registros) {
		this.registros = registros;
	}
}
