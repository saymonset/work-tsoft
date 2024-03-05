/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * @hibernate.mapping
 *
 * @hibernate.class 
 *   table="T_FILETRANSFER"
 *   proxy="com.indeval.persistence.portaldali.dali.modelo.FileTransfer";
 */
@Entity
@Table(name="T_FILETRANSFER")
public class FileTransfer {
    
	
	@EmbeddedId
    private FileTransferPK fileTransferPK; // PK
	@Column(name ="fecha_reg")
    private Date fechaReg;
	@Column(name ="cadena")
    private String cadena;
	@Column(name ="estado")
    private String estado;
	@Column(name ="error")
    private String error;
	@Column(name ="usuario")
    private String usuario;
	@Column(name ="campos_error")
    private String camposError;

    
    /**
     * @hibernate.property
     *   column="fecha_reg"
     *   not-null="false"
     */
    public Date getFechaReg() {
        return fechaReg;
    }

    /**
     * @hibernate.property
     *   column="cadena"
     *   not-null="false"
     */
    public String getCadena() {
        return cadena;
    }

    /**
     * @hibernate.property
     *   column="estado"
     *   not-null="false"
     */
    public String getEstado() {
        return StringUtils.isNotBlank(estado) ? estado.trim() : estado;
    }

    /**
     * @hibernate.property
     *   column="error"
     *   not-null="false"
     */
    public String getError() {
        return StringUtils.isNotBlank(error) ? error.trim() : error;
    }

    /**
     * @hibernate.property
     *   column="usuario"
     *   not-null="false"
     */
    public String getUsuario() {
        return StringUtils.isNotBlank(usuario) ? usuario.trim() : usuario;
    }

    /**
     * @hibernate.property
     *   column="campos_error"
     *   not-null="false"
     */
    public String getCamposError() {
        return StringUtils.isNotBlank(camposError) ? camposError.trim() : camposError;
    }

    /**
     * @param fechaReg
     */
    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    /**
     * @param cadena
     */
    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    /**
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param camposError
     */
    public void setCamposError(String camposError) {
        this.camposError = camposError;
    }

   

	public FileTransferPK getFileTransferPK() {
		return fileTransferPK;
	}

	public void setFileTransferPK(FileTransferPK fileTransferPK) {
		this.fileTransferPK = fileTransferPK;
	}
    
}
