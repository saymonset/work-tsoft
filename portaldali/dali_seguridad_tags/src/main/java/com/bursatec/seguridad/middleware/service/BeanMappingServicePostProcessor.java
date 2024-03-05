/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.middleware.service;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.springframework.remoting.jaxrpc.JaxRpcServicePostProcessor;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.bursatec.seguridad.vo.RespuestaVO;
import com.bursatec.seguridad.vo.RolesUsuarioVO;
import com.bursatec.seguridad.vo.SistemaVO;
import com.bursatec.seguridad.vo.TicketInfoVO;

/**
 * Registra el mapeo de los VO al utilizar el servicio Web de Seguridad con Spring
 * @author Emigdio Hernández
 *
 */
public class BeanMappingServicePostProcessor implements JaxRpcServicePostProcessor {

	/**
	 * Default encoding style URI, as suggested by the JAX-RPC javadoc:
	 * "http://schemas.xmlsoap.org/soap/encoding/"
	 * @see javax.xml.rpc.encoding.TypeMappingRegistry#register
	 */
	public static final String DEFAULT_ENCODING_STYLE_URI = "http://schemas.xmlsoap.org/soap/encoding/";

	/**
	 * Default namespace to use for custom XML types.
	 * @see javax.xml.rpc.encoding.TypeMapping#register
	 */
	public static final String DEFAULT_TYPE_NAMESPACE_URI = "java:com.bursatec.seguridad.vo";

	/** TODO: DOCUMENTAME! */
	private String encodingStyleUri = DEFAULT_ENCODING_STYLE_URI;
	
	/** TODO: DOCUMENTAME! */
	private String typeNamespaceUri = DEFAULT_TYPE_NAMESPACE_URI;

	
	/* (non-Javadoc)
	 * @see org.springframework.remoting.jaxrpc.JaxRpcServicePostProcessor#postProcessJaxRpcService(javax.xml.rpc.Service)
	 */
	
	public void postProcessJaxRpcService(Service service) {
		
		TypeMappingRegistry registry = service.getTypeMappingRegistry();
		TypeMapping mapping = registry.createTypeMapping();
		registerBeanMapping(mapping, SistemaVO.class, "SistemaVO");
		registerBeanMapping(mapping, InstitucionVO.class, "InstitucionVO");
		registerBeanMapping(mapping, RolesUsuarioVO.class, "RolesUsuarioVO");
		registerBeanMapping(mapping, SistemaVO.class, "SistemaVO");
		registerBeanMapping(mapping, RespuestaVO.class, "RespuestaVO");
		registerBeanMapping(mapping, TicketInfoVO.class, "TicketInfoVO");
		
		typeNamespaceUri = "java:com.bursatec.seguridad.persistence.model";
		registerBeanMapping(mapping, UsuarioDTO.class, "Usuario");
		registry.register(this.encodingStyleUri, mapping);

		
	}
	
	/**
	 * TODO: DOCUMENTAME!
	 * 
	 * @param mapping
	 * @param type
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	protected void registerBeanMapping(TypeMapping mapping, Class type, String name) {
		QName xmlType = new QName(this.typeNamespaceUri, name);
		mapping.register(type, xmlType,
		    new BeanSerializerFactory(type, xmlType),
		    new BeanDeserializerFactory(type, xmlType));
	}

	/**
	 * Obtiene el campo encodingStyleUri
	 * @return  encodingStyleUri
	 */
	public String getEncodingStyleUri() {
		return encodingStyleUri;
	}

	/**
	 * Asigna el valor del campo encodingStyleUri
	 * @param encodingStyleUri el valor de encodingStyleUri a asignar
	 */
	public void setEncodingStyleUri(String encodingStyleUri) {
		this.encodingStyleUri = encodingStyleUri;
	}

	/**
	 * Obtiene el campo typeNamespaceUri
	 * @return  typeNamespaceUri
	 */
	public String getTypeNamespaceUri() {
		return typeNamespaceUri;
	}

	/**
	 * Asigna el valor del campo typeNamespaceUri
	 * @param typeNamespaceUri el valor de typeNamespaceUri a asignar
	 */
	public void setTypeNamespaceUri(String typeNamespaceUri) {
		this.typeNamespaceUri = typeNamespaceUri;
	}

	
}
