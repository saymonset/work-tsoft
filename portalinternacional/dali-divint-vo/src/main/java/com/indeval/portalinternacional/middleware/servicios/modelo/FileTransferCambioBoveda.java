/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferDetalleVO;

/**
 * Clase que representa un registro de la tabla 'T_FILETRANSFER_CAMBIOBOVEDA'.
 * 
 */
@Entity
@Table(name = "T_FILETRANSFER_CAMBIOBOVEDA")
@SequenceGenerator(name = "foliador", sequenceName = "ID_FILETRANSFER_CB_SEQ", allocationSize = 1, initialValue = 1)
public class FileTransferCambioBoveda implements Serializable {

	/**
	 * Version de la clase para serializar
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Propiedad que representa el id del Proceso
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_file_transfer", unique = true, nullable = false)
	private Integer idFileTransfer;

	@Column(name = "id_folio", unique = false, nullable = false)
	private String idFolio;

	@Column(name = "estado", unique = false, nullable = false)
	private String estado;

	@Column(name = "usuario_registro", unique = false, nullable = false)
    private String usuarioRegistro;

	@Column(name = "usuario_modifico", unique = false, nullable = false)
    private String usuarioModifico;

	@Column(name = "fecha_registro", unique = false, nullable = false)
    private Date fechaRegistro;

	@Column(name = "fecha_modifico", unique = false, nullable = false)
    private Date fechaModifico;

	@Column(name = "xml_original", unique = false, nullable = true)
	private String xmlOriginal;

	@Column(name = "xml_error", unique = false, nullable = true)
    private String xmlError;

    @Column(name = "xml_procesadas", unique = false, nullable = true)
    private String xmlProcesadas;

	@Column(name = "validados_exito", unique = false, nullable = false)
    private Integer validadosExito;

	@Column(name = "validados_error", unique = false, nullable = false)
    private Integer validadosError;

	@Column(name = "procesados_exito", unique = false, nullable = true)
    private Integer procesadosExito;

	@Column(name = "procesados_error", unique = false, nullable = true)
    private Integer procesadosError;

    @Transient
    private List<String> lineasError;
    
    @Transient
    private List<FileTransferDetalleVO> operaciones;
    
    @Transient
    private boolean erroresSintaxis;

    @Transient
    private String usrCredencial;

    public Integer getIdFileTransfer() {
        return idFileTransfer;
    }

    public void setIdFileTransfer(Integer idFileTransfer) {
        this.idFileTransfer = idFileTransfer;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getXmlOriginal() {
        return xmlOriginal;
    }

    public void setXmlOriginal(String xmlOriginal) {
        this.xmlOriginal = xmlOriginal;
    }

    public String getXmlError() {
        return xmlError;
    }

    public void setXmlError(String xmlError) {
        this.xmlError = xmlError;
    }

    public String getXmlProcesadas() {
        return xmlProcesadas;
    }

    public void setXmlProcesadas(String xmlProcesadas) {
        this.xmlProcesadas = xmlProcesadas;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(String usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaModifico() {
        return fechaModifico;
    }

    public void setFechaModifico(Date fechaModifico) {
        this.fechaModifico = fechaModifico;
    }

	public List<String> getLineasError() {
		return lineasError;
	}

	public void setLineasError(List<String> lineasError) {
		this.lineasError = lineasError;
	}

	public List<FileTransferDetalleVO> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(List<FileTransferDetalleVO> operaciones) {
		this.operaciones = operaciones;
	}

	public boolean isErroresSintaxis() {
		return erroresSintaxis;
	}


	public void setErroresSintaxis(boolean erroresSintaxis) {
		this.erroresSintaxis = erroresSintaxis;
	}

    public Integer getValidadosExito() {
        return validadosExito;
    }

    public void setValidadosExito(Integer validadosExito) {
        this.validadosExito = validadosExito;
    }

    public Integer getValidadosError() {
        return validadosError;
    }

    public void setValidadosError(Integer validadosError) {
        this.validadosError = validadosError;
    }

    public Integer getProcesadosExito() {
        return procesadosExito;
    }

    public void setProcesadosExito(Integer procesadosExito) {
        this.procesadosExito = procesadosExito;
    }

    public Integer getProcesadosError() {
        return procesadosError;
    }

    public void setProcesadosError(Integer procesadosError) {
        this.procesadosError = procesadosError;
    }

    public String getUsrCredencial() {
        return usrCredencial;
    }

    public void setUsrCredencial(String usrCredencial) {
        this.usrCredencial = usrCredencial;
    }

}
