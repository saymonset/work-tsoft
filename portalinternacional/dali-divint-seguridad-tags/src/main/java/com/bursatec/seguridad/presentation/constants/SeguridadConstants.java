/**
 * 2H Software
 * Bursatec - seguridad
 */
package com.bursatec.seguridad.presentation.constants;

/**
 * Clase de constantes con atributos refentes a la seguridad.
 * 
 * @author Emigdio Hernández
 * 
 */
public interface SeguridadConstants {
	String USR_CREDENCIAL= "usrCredencial";
	/**
	 * Nombre con el que se almacenan los datos del usuario en la sesión
	 */
	String USUARIO_SESION = "usuario";
	/**
	 * Nombre del sistema de Estado de Cuenta
	 */
	String SISTEMA_ESTADO_CUENTA = "PORTAL_DALI";
	/**
	 * Ticket de login obtenido del WS
	 */
	String TICKET_SESION = "TICKET_WS";
	
	String ATTR_ULTIMA_VALIDACION="ultimaValidacion";

	/**
	 * IDs con las facultades del usuario
	 */
	String FACULTADES_SESION = "facultades";
	/**
	 * IDs con los roles del usuario	
	 */
	String ROLES_SESION = "roles";
	
	/**
	 * Facultad para firmar operaciones
	 */
	String FACULTAD_FIRMA_OPERACION = "CON_CERTIFICADO";
	/**
	 * Llave de la institución actual en la sesión
	 */
	String INSTITUCION_ACTUAL = "institucion";
	/**
	 * VO de institución en sesión que viene directamente del WS
	 */
	String INSTITUCION_VO_ACTUAL = "institucion_vo";
	/**
	 * Identificador de la institución indeval en el WS de seguridad
	 */
	Long ID_INSTITUCION_INDEVAL = -1L;
	/**
	 * nombre del bean del web service
	 */
	String WEB_SERVICE_BEAN = "webServiceSeguridad";
	/**
	 * ID del componente de autorización por rol
	 */
	String COMPONENT_TYPE_AUTORIZACION_TAG = "IndevalAutorizacionTag";
	/**
	 * ID del componente de autorización por facultad
	 */
	String COMPONENT_TYPE_AUTORIZACION_FACULTAD_TAG = "IndevalAutorizacionFacucltadTag";
	/**
	 * Tipo de Renderer para el componente de autorización de roles
	 */
	String RENDERER_AUTORIZACION_TAG = "javax.faces.Group";

	/**
	 * ID del componente de captcha
	 */
	String COMPONENT_TYPE_CAPTCHA_TAG = "CaptchaComponentTag";
	/**
	 * Tipo de Renderer para el componente captcha
	 */
	String RENDERER_CAPTCHA_TAG = null;
	/**
	 * Llave con el que se guarda como atributo del request el ID del captcha
	 * generado para la solicitud de login
	 */
	String CAPTCHA_GENERATED_ID = "captcha_id_generated";

	/**
	 * texto de respuesta al autorizar un rol denegado
	 */
	String TIPO_RESPUESTA_DENEGADO = "denegada";
	/**
	 * texto de respuesta al autorizar un rol autorizado
	 */
	String TIPO_RESPUESTA_AUTORIZADO = "autorizada";
	/**
	 * Mensaje para ticket expirado
	 */
	String TICKET_EXPIRADO = "el ticket ha expirado";
	/**
	 * Mensaje para ticket deshabilitado
	 */
	String TICKET_DESHABILITADO = "el ticket está deshabilitado.";
	
	/**
	 * Llave del arreglo de instituciones disponibles en sesion
	 */
	String INSTITUCIONES_DISPONIBLES = "instituciones_usuario";

	/**
	 * Nombre del archivo properties que ser cargado para buscar el EJB remoto
	 */
	String PROPERTIES_FILE = "/com/bursatec/seguridad/presentation/jsp/tag/jndi.properties";

	/**
	 * Nombre del archivo de propiedades que ser cargado para buscar el EJB de
	 * seguridad
	 */
	String SEGURIDAD_PROPERTIES_FILE = "/com/bursatec/seguridad/presentation/jsp/tag/jndi_seguridad.properties";

	String CACHE_PROPERTIES_FILE = "/com/bursatec/seguridad/presentation/jsp/tag/jndi_cache.properties";
	
	/**
	 * Nombre de la propiedad donde se guarda el nombre del objeto JNDI a buscar
	 */
	String JNDI_NAME_PROPERTY = "jndiName";
	/**
	 * Nombre de la institucion indeva con permiso de ver todas las cuentas
	 */
	String NOMBRE_INSTITUCION_INDEVAL = "INDEVAL";
	
	
	/** 0 - Certificado Valido */
	int CERTIFICADO_VALIDO_VALUE = 0;

	/** 1 - Certificado Revocado */
	int CERTIFICADO_REVOCADO_VALUE = 1;

	/** 2 - Certificado Comprometido */
	int CERTIFICADO_COMPROMETIDO_VALUE = 2;

	/** 3 - Certificado CADUCO */
	int CERTIFICADO_CADUCO_VALUE = 3;

	/** 4.- Certificado Sin status */
	int CERTIFICADO_SIN_STATUS_VALUE = 4;
	
	
	/* Opciones de login en la seguridad */
    /** Login y password */
    int LOGIN_PASSWORD = 0;
    
    /** Login, password, token */
    int LOGIN_OTP = 2;
    
    /** Valor de login con firma digital */
    int LOGIN_PASSWORD_CERTIFICADO = 4;
    
    /** Autentificacion por DEFAULT que es tomada del SISTEMA asociado, puede ser cualquiera de las anteriores */
    int  DEFAULT_VALUE = 3;
}
