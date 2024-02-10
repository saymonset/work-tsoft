package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="T_CAMPOS_FORMATO_MILA_ARCHIVOS")
public class FormatoMilaArchivos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6387957497329175486L;

	@Id
    @Column(name ="ID_ARCHIVOS_MILA", unique = false, nullable = false)
	private Long idArchivosFormatoMILA;
	
	@Lob
	@Column(name ="comprobante_uno", unique = false, nullable = true)
	private byte[] comprobanteUno;
    
	@Column(name ="nombre_comprobante_uno", unique = false, nullable = true)
    private String nombreComprobanteUno;
    
    @Lob
	@Column(name ="comprobante_dos", unique = false, nullable = true)
    private byte[] comprobanteDos;
    
	@Column(name ="nombre_comprobante_dos", unique = false, nullable = true)
    private String nombreComprobanteDos;

	public Long getIdArchivosFormatoMILA() {
		return idArchivosFormatoMILA;
	}

	public void setIdArchivosFormatoMILA(Long idArchivosFormatoMILA) {
		this.idArchivosFormatoMILA = idArchivosFormatoMILA;
	}

	public byte[] getComprobanteUno() {
		return comprobanteUno;
	}

	public void setComprobanteUno(byte[] comprobanteUno) {
		this.comprobanteUno = comprobanteUno;
	}

	public String getNombreComprobanteUno() {
		return nombreComprobanteUno;
	}

	public void setNombreComprobanteUno(String nombreComprobanteUno) {
		this.nombreComprobanteUno = nombreComprobanteUno;
	}

	public byte[] getComprobanteDos() {
		return comprobanteDos;
	}

	public void setComprobanteDos(byte[] comprobanteDos) {
		this.comprobanteDos = comprobanteDos;
	}

	public String getNombreComprobanteDos() {
		return nombreComprobanteDos;
	}

	public void setNombreComprobanteDos(String nombreComprobanteDos) {
		this.nombreComprobanteDos = nombreComprobanteDos;
	}
}
