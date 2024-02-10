package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroAdp;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroMultiempresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.Registros;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Entity
@Table(name = "T_REGISTROS_BENEFICIARIOS")
@SequenceGenerator(name = "foliador", sequenceName = "ID_REG_BENE_SEQ", allocationSize = 1, initialValue = 1)
public class RegistrosBeneficiarios implements Serializable, Constantes{

	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idRegistrosBene;
	
	private String usuario;
	
	private TipoFormatoBeneficiario tipoFormatoBene;
	
	private String registrosCorrectos;
		
	
			
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="foliador")
	@Column(name="ID_REGISTROS_BENE", unique = true, nullable = false)
	public Long getIdRegistrosBene() {
		return idRegistrosBene;
	}
	
	@Column(name="USUARIO", unique = true, nullable = false)	
	public String getUsuario() {
		return usuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_FORMATO", nullable = false)
	public TipoFormatoBeneficiario getTipoFormatoBene() {
		return tipoFormatoBene;
	}
	
	@Column(name="REGISTROS_CORRECTOS", unique = false, nullable = true)
	public String getRegistrosCorrectos() {
		return registrosCorrectos;
	}			
	
	public void setIdRegistrosBene(Long idRegistrosBene) {
		this.idRegistrosBene = idRegistrosBene;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setTipoFormatoBene(TipoFormatoBeneficiario tipoFormatoBene) {
		this.tipoFormatoBene = tipoFormatoBene;
	}

	public void setRegistrosCorrectos(String registrosCorrectos) {
		this.registrosCorrectos = registrosCorrectos;
	}

			
	
			
}
