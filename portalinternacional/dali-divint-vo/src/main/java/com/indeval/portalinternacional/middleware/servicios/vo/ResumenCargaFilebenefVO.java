package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

public class ResumenCargaFilebenefVO implements Serializable{
	
    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;
    
    /** n&uacute;mero de registros correctos*/
    private Integer registrosACargar;
    
    /** n&uacute;mero de registros con error*/
    private Integer registrosConError;
    
    /** total de registros*/
    private Integer totalRegistros;
    
    /** registros con error*/
    private String xmlRegistrosConError;
    
    /** fecha en que se realizo la carga*/
    private Date fechaCarga;
    
    /** id con el que se guardo el registro*/
    private Long idCapturaBeneficiario;
    
    /** tipo de formato que se guardo*/
    private Long tipoFormato;
    
    /** clave del usuario*/
    private String usuario;
    

	public Integer getRegistrosACargar() {
		return registrosACargar;
	}

	public void setRegistrosACargar(Integer registrosACargar) {
		this.registrosACargar = registrosACargar;
	}

	public Integer getRegistrosConError() {
		return registrosConError;
	}

	public void setRegistrosConError(Integer registrosConError) {
		this.registrosConError = registrosConError;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	
	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Long getIdCapturaBeneficiario() {
		return idCapturaBeneficiario;
	}
		
	public void setIdCapturaBeneficiario(Long idCapturaBeneficiario) {
		this.idCapturaBeneficiario = idCapturaBeneficiario;
	}

	

	public Long getTipoFormato() {
		return tipoFormato;
	}

	public void setTipoFormato(Long tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getXmlRegistrosConError() {
		return xmlRegistrosConError;
	}

	public void setXmlRegistrosConError(String xmlRegistrosConError) {
		this.xmlRegistrosConError = xmlRegistrosConError;
	}

	

}
