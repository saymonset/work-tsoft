/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.FileTransferBenefConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.StatusBeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao;

/**
 * @author César Hernández
 *
 */

public abstract class ValidatorBeneficiario<T extends FileTransferForma> implements ValidatorBeneficiarioService<T> {
	
	
	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorBeneficiario.class);
	
	/** Bean de acceso a validatorService */
    private ValidatorService validatorService;
    
    /** Resolvedor de Mensajes */
    private MessageResolver errorResolver;
    
    /** Dao para obtener la instituci&oacute;n*/
    private InstitucionDao institucionDao;
    
    /** Dao para obtener el catbic*/
    private CatBicDao catBicDao;
    
    /** describe el tipo de beneficiario*/
    private TipoBeneficiarioDao tipoBeneficiarioDao;
    
    /** Dao para obtener el status del beneficiario*/
    private StatusBeneficiarioDao statusBeneficiarioDao;
            
    /**  Dao para manipular al Beneficiario*/
    private BeneficiarioDao beneficiarioDao;
    
    /** Mapa con los valores de los meses*/
    private Map<String,String> mesesFechaFileTransfer;
    
    /** Mapa con los valores TypeTaxPayer de W9*/
    private Map<String,Long> mapeoW9TypeTaxPayer;
    
    /** Mapa con los tipos de beneficiarios permitidos para W9*/
    private Map<String,Boolean> mapeoTipoBeneficairioValidoW9;
    
    /** Mapa con los tipos de estado del Beneficiario*/
    private Map<String,Boolean>mapeoTipoEstadoBeneficiario;
    
    /** Mapa con los tipos de beneficiarios validos */
    private Map<String, Long> tiposBeneficiarios;
    
    /** Mapa con los custodios validos */
    private Map<String, Long> custodios;
    
    /**Servicio para el control de beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    
    private Pattern patronNumerico = Pattern.compile(Constantes.PATRON_NUMERICO_SIN_ESPACIO);
    
    private Pattern patronAlfabetico = Pattern.compile(Constantes.PATRON_ALFABETICO);
    
    private Pattern patronAlfanumerico = Pattern.compile(Constantes.PATRON_ALFANUMERICO_SIN_ESPACIO);
        
    private Pattern patronFechaFormato = Pattern.compile(Constantes.PATRON_FECHA_FORMATO);
    
    private Pattern patronSsn = Pattern.compile(Constantes.PATRON_SSN);
    
    private Pattern patronEmployedId = Pattern.compile(Constantes.PATRON_EMPLOYED_IDENTIFICATION_NUMBER);
    
    private Pattern patronRFCSimple = Pattern.compile(Constantes.PATRON_RFC_SIMPLE);
    
    private Pattern patronPorcentaje = Pattern.compile(Constantes.PATRON_PORCENTAJE);
    
    /**
	 * SimpleDateFormat 
	 */
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	
	private static final SimpleDateFormat simpleDateFormatRFC = new SimpleDateFormat("yyMMdd");

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService#validaFormatoW(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public abstract void validaFormatoW(Beneficiario beneficiario);

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService#validaDomicilioBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio, java.lang.String)
	 */
	public abstract void validaDomicilioBeneficiario(Domicilio domicilio, String tipoDomicilio) throws BusinessException;
	
	/**
	 * Valida los campos del beneficiario para la captura del mismo
	 */
	public void validaCapturaBeneficiario(Beneficiario beneficiario, Long idInstitucion) throws BusinessException {
	    	
    	
		
    	validatorService.validaDTONoNulo(beneficiario, "Beneficiario");
		validatorService.validaDTONoNulo(beneficiario.getIdCuentaNombrada(), " Custodio");
		
		if(beneficiario.getIdCuentaNombrada()==null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el ID del custodio"));
		}
		
		validatorService.validaDTONoNulo(beneficiario.getFechaFormato(), "Fecha de Formato");
		
		if(beneficiario.getActivo() == null && idInstitucion != null){
			throw new BusinessException(errorResolver.getMessage("J0104", new Object[] {}));
		}
		
		/* Validacion de los nombres */
		String nombre = null, etiqueta = null;
		if( beneficiario.getPersonaFisica() ) {
			if(beneficiario.getTipoFormato().equals("MILA")){
				validatorService.validarClaveTipo(beneficiario.getNombres(), " Nombre ");
				validatorService.validarClaveTipo(beneficiario.getApellidoPaterno(), " Apellido Paterno ");
				if(beneficiario.getFormatoMILA().getPaisNacionalidad()!=null &&
						beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais()!=null
						&& beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais().intValue()==PaisInt.ID_PAIS_MEXICO){
					validatorService.validarClaveTipo(beneficiario.getApellidoMaterno(), " Apellido Materno ");
				}
				etiqueta = " Nombre ";
			}
			else{
				validatorService.validarClaveTipo(beneficiario.getNombres(), " First Name ");
				validatorService.validarClaveTipo(beneficiario.getApellidoPaterno(), " Last Name ");
				validatorService.validarClaveTipo(beneficiario.getApellidoMaterno(), " Second Last Name ");
				etiqueta = " First Name ";
			}
			nombre = beneficiario.getNombres();
			
		}
		else {
			if(BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())) {
				validatorService.validarClaveTipo(beneficiario.getRazonSocial(), " Nombre ");
				etiqueta = " Nombre ";
			}
			else {
				validatorService.validarClaveTipo(beneficiario.getRazonSocial(), " Razon Social ");
				etiqueta = " Razon Social ";
			}
			nombre = beneficiario.getRazonSocial();
		}
		if(nombre.trim().startsWith("_")) {
			Object []obj = new Object[]{etiqueta}; 
			throw new BusinessException(errorResolver.getMessage("J0097", obj));
		}
		
		/* Campos que son String */
		validatorService.validarClaveTipo(beneficiario.getTipoFormato(), "tipo Formato");
    	validatorService.validaDTONoNulo(beneficiario.getStatusBenef(), "StatusBeneficiario");
    	validatorService.validaDTONoNulo(beneficiario.getTipoBeneficiario(), "TipoBeneficiario");
    	validatorService.validaDTONoNulo(beneficiario.getIdCuentaNombrada(), "Custodio");
    	if( !beneficiario.getTipoFormato().equals("W9") ) {
    		validatorService.validarClaveTipo(beneficiario.getPaisIncorporacion(), " Pais de Incorporacion ");
    	}
    	
    	if(beneficiario.getStatusBenef().getIdStatusBenef()==null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el ID del Status Beneficiario"));
		}
    	
		if(beneficiario.getTipoBeneficiario().getIdTipoBeneficiario()==null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el ID del tipo de beneficiario"));
		}
    	
		/* Se hacen validaciones de los objetos de las relaciones*/
		if (((TipoBeneficiario) tipoBeneficiarioDao.getByPk(TipoBeneficiario.class, 
				beneficiario.getTipoBeneficiario().getIdTipoBeneficiario())) == null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el Tipo de Beneficiario"));
		}
		if (idInstitucion != null && ((Institucion) institucionDao.getByPk(Institucion.class, idInstitucion)) == null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la Institucion"));
		}
		
		if( beneficiario.getIdCuentaNombrada() != null &&
				beneficiario.getIdCuentaNombrada() > 0 ) {
			if( catBicDao.findCatBicByCuentaNombrada(beneficiario.getIdCuentaNombrada()) <= 0 ) {
				throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el Custodio"));
			}
		} else {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el Custodio"));
		}
		
		if ( ((StatusBeneficiario) statusBeneficiarioDao.getByPk(StatusBeneficiario.class, 
				beneficiario.getStatusBenef().getIdStatusBenef())) == null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el status del beneficiario"));
		}
    }
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService#validaVOFiletransferBeneficiario(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO)
	 */
	public void validaVOFiletransferBeneficiario(FileTransferCapturaBenefVO fileTransferCapturaBenefVO) throws BusinessException{
		
		if(fileTransferCapturaBenefVO== null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Objeto"));
		}
		if(fileTransferCapturaBenefVO.getArchivo() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Archivo"));
		}
		if(fileTransferCapturaBenefVO.getTipoFormato() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Tipo Formato"));			
		}
				
		if(fileTransferCapturaBenefVO.getClaveUsuario() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Usuario Responsable"));
		}
		if(fileTransferCapturaBenefVO.getFolio() == null || fileTransferCapturaBenefVO.getId() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "La Institución"));
		}								
	}
	
	public void agregaMensajeError(T fileTransferFormaW,List<T> listaRegistrosErrorAux,String nombreCampo, int tipoMensaje){					
		String mensajeError = null;
		switch (tipoMensaje) {
		case Constantes.MENSAJE_CAMPO_NULO:
			mensajeError = getErrorResolver().getMessage("J0105", (Object)nombreCampo);
			break;
		case Constantes.MENSAJE_LONGITUD_INVALIDO:
			mensajeError = getErrorResolver().getMessage("J0108", (Object)nombreCampo);
			break;	
		case Constantes.MENSAJE_FORMATO_INCORRECTO:			
			mensajeError = getErrorResolver().getMessage("J0106", (Object)nombreCampo);
			break;
		case Constantes.MENSAJE_CAMPO_INVALIDO:
			mensajeError = getErrorResolver().getMessage("J0107", (Object)nombreCampo);
			break;
		case Constantes.MENSAJE_INHABILITADO:
			mensajeError = getErrorResolver().getMessage("J0150", (Object)nombreCampo);
			break;
		default:
			break;
		}
		fileTransferFormaW.setNumeroRegistro(fileTransferFormaW.getNumeroRegistro());
		fileTransferFormaW.setEstadoRegistro(Constantes.ESTADO_ERROR);
		fileTransferFormaW.setDescripcionError(mensajeError);
		listaRegistrosErrorAux.add(fileTransferFormaW);
	}
	
	/**
	 * Método que valida los campos comunes de los file transfer de formatos W.
	 * @param fileTransferFormaW Fila a ser validada
	 * @param idTipoFormato Tipo de formato que se esta validando.
	 * @param listaRegistrosErrorAux Lista con los registros erroneos.
	 * @return true si la fila es valida; false en caso contrario.
	 */
	public boolean validarCamposComunes(T fileTransferFormaW, int idTipoFormato, List<T> listaRegistrosErrorAux) {
		boolean valido = true;
		// 1. Valida que el tipo de formato haya sido capturado y corresponda a la operación.
		if(StringUtils.isBlank(fileTransferFormaW.getTipoFormato())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_TIPO_FORMATO, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido) {
			switch (idTipoFormato) {
				case BeneficiariosConstantes.ID_FORMATO_W8_BEN_E:
					valido = BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(fileTransferFormaW.getTipoFormato());
					break;
				case BeneficiariosConstantes.ID_FORMATO_W8_IMY:
					valido = BeneficiariosConstantes.FORMATO_W8_IMY.equals(fileTransferFormaW.getTipoFormato());
				default:
					break;
			}
			if(!valido)
				agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_TIPO_FORMATO, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		// 2. Valida que el tipo de beneficiario haya sido capturado y este en la lista de permitidos
		if(valido && StringUtils.isBlank(fileTransferFormaW.getTipoBeneficiario())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_TIPO_BENEFICIARIO, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido && !tiposBeneficiarios.containsKey(fileTransferFormaW.getTipoBeneficiario())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_TIPO_BENEFICIARIO, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		// 3. Valida que el custodio haya sido capturado y este en la lista de permitidos
		if(valido && StringUtils.isBlank(fileTransferFormaW.getClaveCustodio())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_CAMPO_NULO);
		}
		/** Valido la abreviacion del custodio en tabla: C_CATBIC **/
		if(valido && !custodios.containsKey(fileTransferFormaW.getClaveCustodio())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_CAMPO_INVALIDO);
		}

/*		CatBic catBic = catBicDao.findAbreviacionByCustodio(fileTransferFormaW.getClaveCustodio()); 
		if(valido && (catBic == null || catBic.getAbreviacionCustodio() == null)){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.ABREVIACION_CUSTODIO, Constantes.MENSAJE_CAMPO_INVALIDO);			
		}
*/
		//Validacion de custodio activo en bd
		if(valido && !controlBeneficiariosService.esCatBicActivo(custodios.get(fileTransferFormaW.getClaveCustodio()))){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_INHABILITADO);
		}
/*		
		if(valido && (catBic == null || catBic.getActivo() == null || catBic.getActivo() == 0)){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_INHABILITADO);			
		}
*/		
		// 4. Valida que la institucion haya sido capturada, sea numérica y sea de máximo 5 digitos 
		if(valido && StringUtils.isBlank(fileTransferFormaW.getClaveInstitucion())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_ID_INSTITUCION, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido && !getPatronNumerico().matcher(fileTransferFormaW.getClaveInstitucion()).matches()) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_ID_INSTITUCION, Constantes.MENSAJE_CAMPO_INVALIDO);			
		}
		if(valido && fileTransferFormaW.getClaveInstitucion().length() > Constantes.LONGITUD_INSTITUCION){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_ID_INSTITUCION, Constantes.MENSAJE_LONGITUD_INVALIDO);			
		}
		// 5. Valida que la fecha haya sido capturada y tenga el formato correcto
		if(valido && StringUtils.isBlank(fileTransferFormaW.getFechaFormato())) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_FECHA_FORMATO, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido && fileTransferFormaW.getFechaFormato().length() > Constantes.LONGITUD_FECHA_FORMATO){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_FECHA_FORMATO, Constantes.MENSAJE_LONGITUD_INVALIDO);			
		}
		if(valido && !getPatronFechaFormato().matcher(fileTransferFormaW.getFechaFormato()).matches()){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_FECHA_FORMATO, Constantes.MENSAJE_FORMATO_INCORRECTO);			
		}
		// 6. Valida que el estado del registro haya sido capturado y que sea ACTIVO o INACTIVO
		if(valido && StringUtils.isBlank(fileTransferFormaW.getEstado())){
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_ESTADO, Constantes.MENSAJE_CAMPO_NULO);			
		}
		if(valido && !(BeneficiariosConstantes.ESTADO_ACTIVO.equals(fileTransferFormaW.getEstado()) || 
				BeneficiariosConstantes.ESTADO_INACTIVO.equals(fileTransferFormaW.getEstado()))) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, Constantes.W9_ESTADO, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		return valido;
	}

	/**
	 * Método que valida que la cadena no sea nula o vacia y que no sobrepase la longitud indicada.
	 * @param cadena Cadena a validar
	 * @param longitud Longitud a validar
	 * @param fileTransferFormaW Forma para el error
	 * @param listaRegistrosErrorAux Lista de errrores
	 * @param nombreCampo NOmbre del campo.
	 * @return true si la validación es correcta; false en caso contrario
	 */
	public boolean validarCadenaObligatorioLongitud(String cadena, int longitud, T fileTransferFormaW,
			List<T> listaRegistrosErrorAux,String nombreCampo) {
		boolean valido = true;
		if(StringUtils.isBlank(cadena)) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, nombreCampo, Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(cadena.length() > longitud) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, nombreCampo, Constantes.MENSAJE_LONGITUD_INVALIDO);
		}
		return valido;
	}
	
	
	/**
	 * Método que valida que la cadena no sea nula o vacia y que no sobrepase la longitud indicada.
	 * @param cadena Cadena a validar
	 * @param longitud Longitud a validar
	 * @param fileTransferFormaW Forma para el error
	 * @param listaRegistrosErrorAux Lista de errrores
	 * @param nombreCampo NOmbre del campo.
	 * @return true si la validación es correcta; false en caso contrario
	 */
	public boolean validarLongitudCadena(String cadena, int longitud, T fileTransferFormaW,
			List<T> listaRegistrosErrorAux,String nombreCampo) {
		boolean valido = true;
		if(StringUtils.isNotBlank(cadena) && cadena.length() > longitud) {
			valido = false;
			agregaMensajeError(fileTransferFormaW, listaRegistrosErrorAux, nombreCampo, Constantes.MENSAJE_LONGITUD_INVALIDO);
		}
		return valido;
	}
	
	/**
	 * Método que valida un campo del file transfer que representa un booleano.
	 * @param valorCampo Cadena con el campo a validar.
	 * @return true si el campo es igual a la cadena YES, NO, vacio o nulo; false en caso contrario.
	 */
	public boolean validaCampoBooleano(String valorCampo) {
		boolean campoValido = true;
		if(StringUtils.isNotBlank(valorCampo)) {
			campoValido = FileTransferBenefConstantes.YES.equalsIgnoreCase(valorCampo) ||
				FileTransferBenefConstantes.NO.equalsIgnoreCase(valorCampo);
		}
		return campoValido;
	}	
	
	
	/**
	 * Método que valida un campo del file transfer que representa un booleano.
	 * @param valorCampo Cadena con el campo a validar.
	 * @return true si el campo no es vacio y contiene la cadena yes indistintamente; false en caso contrario.
	 */
	public boolean validaCampoBooleanoVerdadero(String valorCampo) {
		return StringUtils.isNotBlank(valorCampo) && FileTransferBenefConstantes.YES.equalsIgnoreCase(valorCampo);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService#esCampoFileTransferBeneficiarioNulo(java.lang.String)
	 */
	public boolean esCampoFileTransferBeneficiarioNulo(String campo) {
		return StringUtils.isBlank(campo);
	}
	
	/**
	 * @param validatorService the validatorService to set
	 */
	public void setValidatorService(ValidatorService validatorService) {
		this.validatorService = validatorService;
	}

	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}
	
	/**
	 * @param institucionDao the institucionDao to set
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	/**
	 * @param catBicDao the catBicDao to set
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}

	/**
	 * @param tipoBeneficiarioDao the tipoBeneficiarioDao to set
	 */
	public void setTipoBeneficiarioDao(TipoBeneficiarioDao tipoBeneficiarioDao) {
		this.tipoBeneficiarioDao = tipoBeneficiarioDao;
	}

	/**
	 * @param statusBeneficiarioDao the statusBeneficiarioDao to set
	 */
	public void setStatusBeneficiarioDao(StatusBeneficiarioDao statusBeneficiarioDao) {
		this.statusBeneficiarioDao = statusBeneficiarioDao;
	}

	/**
	 * @return the errorResolver
	 */
	protected MessageResolver getErrorResolver() {
		return errorResolver;
	}
	
	/**
	 * @return the validatorService
	 */
	protected ValidatorService getValidatorService() {
		return validatorService;
	}

	/**
	 * @return the institucionDao
	 */
	protected InstitucionDao getInstitucionDao() {
		return institucionDao;
	}

	/**
	 * @return the catBicDao
	 */
	protected CatBicDao getCatBicDao() {
		return catBicDao;
	}

	/**
	 * @return the tipoBeneficiarioDao
	 */
	protected TipoBeneficiarioDao getTipoBeneficiarioDao() {
		return tipoBeneficiarioDao;
	}

	/**
	 * @return the statusBeneficiarioDao
	 */
	protected StatusBeneficiarioDao getStatusBeneficiarioDao() {
		return statusBeneficiarioDao;
	}

	protected Map<String,String> getMesesFechaFileTransfer() {
		return mesesFechaFileTransfer;
	}

	protected BeneficiarioDao getBeneficiarioDao() {
		return beneficiarioDao;
	}

	protected Map<String,Long> getMapeoW9TypeTaxPayer() {
		return mapeoW9TypeTaxPayer;
	}

	protected Map<String, Boolean> getMapeoTipoBeneficairioValidoW9() {
		return mapeoTipoBeneficairioValidoW9;
	}

	protected Map<String, Boolean> getMapeoTipoEstadoBeneficiario() {
		return mapeoTipoEstadoBeneficiario;
	}

	public void setMesesFechaFileTransfer(Map<String,String> mesesFechaFileTransfer) {
		this.mesesFechaFileTransfer = mesesFechaFileTransfer;
	}

	public void setBeneficiarioDao(BeneficiarioDao beneficiarioDao) {
		this.beneficiarioDao = beneficiarioDao;
	}

	public void setMapeoW9TypeTaxPayer(Map<String,Long> mapeoW9TypeTaxPayer) {
		this.mapeoW9TypeTaxPayer = mapeoW9TypeTaxPayer;
	}

	public void setMapeoTipoBeneficairioValidoW9(
			Map<String, Boolean> mapeoTipoBeneficairioValidoW9) {
		this.mapeoTipoBeneficairioValidoW9 = mapeoTipoBeneficairioValidoW9;
	}

	public void setMapeoTipoEstadoBeneficiario(
			Map<String, Boolean> mapeoTipoEstadoBeneficiario) {
		this.mapeoTipoEstadoBeneficiario = mapeoTipoEstadoBeneficiario;
	}
	
	
	public boolean esRegistroFileTransferValido(
			T fileTransferForma,
			List<T> listaRegistrosErrorAux) {
		throw new UnsupportedOperationException("Metodo no soportado");
	}
	public boolean validaReglasNegocioBeneficiarioFiletransfer(
			T fileTransferFormaW9,
			List<T> listaRegistrosErrorAux,
			String idClaveInstitucion, boolean isIndeval) {
		throw new UnsupportedOperationException("Metodo no soportado");
	}

	public Pattern getPatronNumerico() {
		return patronNumerico;
	}

	public Pattern getPatronAlfabetico() {
		return patronAlfabetico;
	}

	public Pattern getPatronAlfanumerico() {
		return patronAlfanumerico;
	}

	public Pattern getPatronFechaFormato() {
		return patronFechaFormato;
	}

	public Pattern getPatronSsn() {
		return patronSsn;
	}

	public Pattern getPatronEmployedId() {
		return patronEmployedId;
	}

	public static SimpleDateFormat getSimpledateformat() {
		simpleDateFormat.setLenient(false);
		return simpleDateFormat;
	}
	
	
	public Pattern getPatronRFCSimple() {
		return patronRFCSimple;
	}
	
	
	

	public static SimpleDateFormat getSimpledateformatrfc() {
		simpleDateFormatRFC.setLenient(false);
		return simpleDateFormatRFC;
	}

	public static Logger getLog() {
		return log;
	}

	
	public Pattern getPatronPorcentaje() {
		return patronPorcentaje;
	}

	/**
     * Convierte una fecha en notaci&oacute;n del archivo de entrada a notaci&oacute;n del modelo de datos
     * @param fechaFormato
     * @return String
     */
    public String convierteFechaArchivoFileTransfer(String fechaFormato) {    		                              	
    	String fechaConFormato = getMesesFechaFileTransfer().get(fechaFormato.substring(0, 3).toUpperCase()) + "/" + fechaFormato.substring(4, 6) + "/" + fechaFormato.substring(7);                      
        if(fechaConFormato.length() != (Constantes.LONGITUD_FECHA_FORMATO_2)){
        	fechaConFormato =  null;
        }            
        return fechaConFormato;
    }
    
    public void init() {
		List<Object[]> listCatbic = new ArrayList<Object[]>(catBicDao.findCatBicByNameEntity());
		if(listCatbic != null && !listCatbic.isEmpty()){
			custodios = new HashMap<String,Long>();
			for( Object[] catBic : listCatbic ) {
				if((String)catBic[5] == null){
					custodios.put(((Long)catBic[0]).toString(), (Long)catBic[0]);
				} else { 
					custodios.put((String)catBic[5], (Long)catBic[0]);
				}
			}			
		}
    }
    
    public void release() {
        	
    }

	/**
	 * Método para obtener el atributo tiposBeneficiarios
	 * @return El atributo tiposBeneficiarios
	 */
	public Map<String, Long> getTiposBeneficiarios() {
		return tiposBeneficiarios;
	}

	/**
	 * Método para establecer el atributo tiposBeneficiarios
	 * @param tiposBeneficiarios El valor del atributo tiposBeneficiarios a establecer.
	 */
	public void setTiposBeneficiarios(Map<String, Long> tiposBeneficiarios) {
		this.tiposBeneficiarios = tiposBeneficiarios;
	}

	/**
	 * Método para obtener el atributo tiposCustodios
	 * @return El atributo tiposCustodios
	 */
	public Map<String, Long> getCustodios() {
		return custodios;
	}

	/**
	 * Método para establecer el atributo tiposCustodios
	 * @param tiposCustodios El valor del atributo tiposCustodios a establecer.
	 */
	public void setCustodios(Map<String, Long> custodios) {
		this.custodios = custodios;
	}

	
	public ControlBeneficiariosService getControlBeneficiariosService() {
		return controlBeneficiariosService;
	}

	public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}
	
	
	
}