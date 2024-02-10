package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

@Entity
@Table(name = "T_FILETRANSFER_CAPTURA_BENE")
@SequenceGenerator(name = "foliador", sequenceName = "ID_FILE_CAPTURA_BENE_SEQ", allocationSize = 1, initialValue = 1)
public class FileTransferCapturaBeneficiario implements Serializable,Constantes{
	
	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idFileTransferCapturaBene;
	
	private String idInstitucion;
	
	private String folioInstitucion;
	
	private String usuario;
		
	private Date fechaRegistro;
	
	private TipoFormatoBeneficiario tipoFormatoBene;
	
	private Integer numeroRegistrosCorrectos;
	
	private Integer numeroRegistrosError;
	
	private Integer totalRegistros;
	
	private String registrosError;
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name ="ID_FILE_CAPTURA_BENE", unique = true, nullable = false)
	public Long getIdFileTransferCapturaBene() {
		return idFileTransferCapturaBene;
	}
	
	@Column(name ="ID_INSTITUCION", unique = false, nullable = false)
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	@Column(name ="FOLIO_INSTITUCION", unique = false, nullable = false)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	
	@Column(name ="USUARIO", unique = false)
	public String getUsuario() {
		return usuario;
	}
	
	
	@Column(name ="FECHA_REGISTRO", unique = false, nullable = false)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_FORMATO", nullable = false)
	public TipoFormatoBeneficiario getTipoFormatoBene() {
		return tipoFormatoBene;
	}
	
	@Column(name ="NUMERO_REGISTROS_CORRECTOS")
	public Integer getNumeroRegistrosCorrectos() {
		return numeroRegistrosCorrectos;
	}
	
	@Column(name ="NUMERO_REGISTROS_CON_ERROR")
	public Integer getNumeroRegistrosError() {
		return numeroRegistrosError;
	}
	
	@Column(name ="TOTAL_REGISTROS")
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	
	@Column(name="REGISTROS_ERROR", unique = false, nullable = true)
	public String getRegistrosError() {
		return registrosError;
	}
	
	public void setIdFileTransferCapturaBene(Long idFileTransferCapturaBene) {
		this.idFileTransferCapturaBene = idFileTransferCapturaBene;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setTipoFormatoBene(TipoFormatoBeneficiario tipoFormatoBene) {
		this.tipoFormatoBene = tipoFormatoBene;
	}

	public void setNumeroRegistrosCorrectos(Integer numeroRegistrosCorrectos) {
		this.numeroRegistrosCorrectos = numeroRegistrosCorrectos;
	}

	public void setNumeroRegistrosError(Integer numeroRegistrosError) {
		this.numeroRegistrosError = numeroRegistrosError;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public void setRegistrosError(String registrosError) {
		this.registrosError = registrosError;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileTransferCapturaBeneficiario)) {return false;}

		FileTransferCapturaBeneficiario other=(FileTransferCapturaBeneficiario)obj;
		
		boolean isEquals = false;

		isEquals=EqualsBuilder.reflectionEquals(this, other);
		
		return isEquals;
	}
}
