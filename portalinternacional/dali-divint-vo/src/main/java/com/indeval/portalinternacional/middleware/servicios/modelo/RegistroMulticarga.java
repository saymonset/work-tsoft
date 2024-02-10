package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.hibernate.annotations.ForeignKey;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroAdp;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroMultiempresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.Registros;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.DetalleRegistroErrorAdpVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.DetalleRegistroErrorMultiempresaVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Entity
@Table(name = "T_REGISTROS_MULTICARGA")
@SequenceGenerator(name = "foliador",sequenceName = "ID_REG_MULTICARGA_SEQ",initialValue = 1, allocationSize = 1)
public class RegistroMulticarga implements Serializable, Constantes{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * valor del identificador del registro multicarga
	 * */
	private Long idRegistroMulticarga;
	
	/**
	 * valor del identificador de multicarga internacional
	 * */
	private Long idMulticargaInternacional;
		
	/**
	 * valor del identificador del tipoOperacionMulticarga  
	 * */	
	private TipoOperacionMulticarga tipoOperacionMulticarga;
	
	/**
	 * contiene los registros cargados
	 * */
	private String registrosCargados;
	
	/**
	 * contiene los registros con error al momento de la autorizaci&oacute;n
	 * */
	private String registrosErrorAutorizar;
	
	/**
	 * variable para guardar la lista transformada
	 * */
	private List registros;

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="foliador")
	@Column(name="ID_REGISTROS_MULTICARGA", unique = true, nullable = false)
	public Long getIdRegistroMulticarga() {
		return idRegistroMulticarga;
	}
	
	@Column(name="ID_MULTICARGA_INTERNACIONAL")	
	public Long getIdMulticargaInternacional() {
		return idMulticargaInternacional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_OPERACION_MULTICARGA", nullable = false)
	public TipoOperacionMulticarga getTipoOperacionMulticarga() {
		return tipoOperacionMulticarga;
	}
	
	@Column(name="REGISTROS_CARGADOS", unique = false, nullable = true)
	public String getRegistrosCargados() {
		return registrosCargados;
	}
	
	@Column(name="REGISTROS_ERROR_AUTORIZAR", unique = false, nullable = true)
	public String getRegistrosErrorAutorizar() {
		return registrosErrorAutorizar;
	}
	
	@Transient
	public List getListaRegistros(){
		if(this.registros != null){
			return this.registros;
		}
		else{
			List listaRegistros = null;
			if(registrosCargados != null){			
				XStream xstream = new XStream(new DomDriver());
				Registros registros = null;
				if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_ADP){
					Annotations.configureAliases(xstream, RegistroAdp.class);				
				}else if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
					Annotations.configureAliases(xstream, RegistroMultiempresa.class);
				}
				Annotations.configureAliases(xstream, Registros.class);
				registros = (Registros) xstream.fromXML(registrosCargados);
				if(registros != null && registros.getListaRegistros() != null && !registros.getListaRegistros().isEmpty()){
					listaRegistros = registros.getListaRegistros();
				}
			}
			this.registros = listaRegistros;
			return this.registros;
		}	
		
	}
	

	public void setListaRegistros(List listaRegistros){		
		if(listaRegistros != null && !listaRegistros.isEmpty()){
			Registros registros = new Registros();
			XStream xstream = new XStream();
			if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_ADP){
				Annotations.configureAliases(xstream, RegistroAdp.class);				
			}else if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
				Annotations.configureAliases(xstream, RegistroMultiempresa.class);
			}
			registros.setListaRegistros(listaRegistros);
			Annotations.configureAliases(xstream, Registros.class);
			String registrosXml = xstream.toXML(registros);
			if(StringUtils.isNotBlank(registrosXml)){				
				setRegistrosCargados(registrosXml);
			}
			
		}
	
	}
	
	
	public void setListaRegistrosError(List listaRegistros){		
		if(listaRegistros != null && !listaRegistros.isEmpty()){
			Registros registros = new Registros();
			XStream xstream = new XStream();
			if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_ADP){
				Annotations.configureAliases(xstream, DetalleRegistroErrorAdpVO.class);				
			}else if(tipoOperacionMulticarga.getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
				Annotations.configureAliases(xstream, DetalleRegistroErrorMultiempresaVO.class);
			}
			registros.setListaRegistros(listaRegistros);
			Annotations.configureAliases(xstream, Registros.class);
			String registrosXml = xstream.toXML(registros);
			if(StringUtils.isNotBlank(registrosXml)){				
				setRegistrosErrorAutorizar(registrosXml);
			}
			
		}
	
	}
	
	public void setIdRegistroMulticarga(Long idRegistroMulticarga) {
		this.idRegistroMulticarga = idRegistroMulticarga;
	}
	public void setIdMulticargaInternacional(Long idMulticargaInternacional) {
		this.idMulticargaInternacional = idMulticargaInternacional;
	}

	
	public void setRegistrosCargados(String registrosCargados) {
		this.registrosCargados = registrosCargados;
	}
	
	public void setRegistrosErrorAutorizar(String registrosErrorAutorizar) {
		this.registrosErrorAutorizar = registrosErrorAutorizar;
	}
	public void setTipoOperacionMulticarga(
			TipoOperacionMulticarga tipoOperacionMulticarga) {
		this.tipoOperacionMulticarga = tipoOperacionMulticarga;
	}
}
