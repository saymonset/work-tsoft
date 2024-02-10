package com.indeval.portalinternacional.middleware.services.validador;


import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.MulticargaInternacional;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.MulticargaInternacionalDao;

@SuppressWarnings({"unchecked"})
public class ValidatorMulticargaDivIntServiceImpl implements ValidatorMulticargaDivIntService, Constantes{
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ValidatorMulticargaDivIntServiceImpl.class);
    
	/** Dao Multicarga Internacional*/	
	private MulticargaInternacionalDao multicargaInternacionalDao;
	
	/** Dao para las consultas de catBic */
	private CatBicDao catBicDao;
	
	/** Dao para las consultas de instituciones */
	private InstitucionDao institucionDao;
	
	/** Resolvedor de Mensajes */
    private MessageResolver errorResolver;
	
    /** Patron del formato del uoi*/
    private Pattern patronUoi = Pattern.compile(PATRON_UOI);
    
    /** Patron del formato del adp*/
    private Pattern patronAdp = Pattern.compile(PATRON_ADP);
    
    /** Patron del formato Institucion id + folio */
    private Pattern patronInstitucion = Pattern.compile(PATRON_NUMERICO_SIN_ESPACIO);
	   
	
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esNombreArchivoValido(String)
     */
	public boolean esNombreArchivoValido(String nombreArchivo)throws BusinessException{				
		log.info("Entrando a validaNombreArchivo()");
		boolean nombreAsignado = false;
		log.debug("Se ejecuta la consulta Multicarga Internacional por nombre del Archivo");
		MulticargaInternacional multicargaInternacional = multicargaInternacionalDao.consultaByNombreArchivo(nombreArchivo);
		if(multicargaInternacional != null){
			nombreAsignado = true;
		}
		return nombreAsignado;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#validaMulticargaVO(MulticargaVO)
     */
	public void validaMulticargaVO(MulticargaVO multicargaVO)throws BusinessException{
		log.info("Entrando a validaMulticargaVO()");
		
		if(multicargaVO== null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Objeto"));
		}
		if(multicargaVO.getArchivo() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Archivo"));
		}
		if(multicargaVO.getEstadoRegistro() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "Los Registros"));			
		}
				
		if(multicargaVO.getNombreArchivo() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Nombre del Archivo"));
		}
		if(multicargaVO.getTipoOperacion() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Tipo Operación"));
		}
		if(multicargaVO.getUsuarioResponsable() == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El Usuario Responsable"));
		}
		if(multicargaVO.getTipoOperacion().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
			if(multicargaVO.getIdInstitucionDestino() == null || multicargaVO.getClaveInstitucionDestino() == null){
				throw new BusinessException(errorResolver.getMessage("J0053",(Object) "La clave Institución"));
			}else if (((Institucion) institucionDao.getByPk(Institucion.class, multicargaVO.getIdInstitucionDestino())) == null) {
				throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la Institución Destino"));
			}											
		}	
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esBeneficiarioConAdpAsignado(Beneficiario)
     */
	public boolean esBeneficiarioConAdpAsignado(Beneficiario beneficiarioAux){
		boolean beneficiarioConAdp = false;
		if(StringUtils.isNotBlank(beneficiarioAux.getAdp())){
			beneficiarioConAdp = true;
		}
		return beneficiarioConAdp;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esBeneficiarioDiferenteAutorizado(Beneficiario)
     */
	public boolean esBeneficiarioDiferenteAutorizado(Beneficiario beneficiarioAux){
		boolean estadoNoAutorizado = false;
		if(beneficiarioAux.getStatusBenef().getIdStatusBenef().longValue() != STATUS_BENEFICIARIO_AUTORIZADO){
			estadoNoAutorizado = true;
		}
		return estadoNoAutorizado;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esBeneficiarioDiferenteDeutsche(String)
     */
	public boolean esBeneficiarioDiferenteDeutsche(String nombreCustodio){
		boolean beneficiarioDiferenteDeutsche = true;
		if(nombreCustodio.equalsIgnoreCase(DEUTSCHE)){
			beneficiarioDiferenteDeutsche = false;
		}
		return beneficiarioDiferenteDeutsche;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#nombreCustodioPorCuentaNombrada(Beneficiario)
     */
	public String nombreCustodioPorCuentaNombrada(Beneficiario beneficiarioAux){		
		Long idCuentaNombrada = beneficiarioAux.getIdCuentaNombrada();
		String nombreCustodio = null;
		Map<Long,String> mapaCustodios  = catBicDao.findCatBicByNameForMulticarga();	
		if(mapaCustodios != null){
			nombreCustodio = mapaCustodios.get(idCuentaNombrada);
		}			
		return nombreCustodio;		
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#validaInstitucionBeneficiario(Beneficiario,Long)
     */
	public boolean validaInstitucionBeneficiario(Beneficiario beneficiarioAux, Long idInstitucionOrigen){
		boolean beneficiarioConInstitucion = false;
		Set<Institucion> instituciones = beneficiarioAux.getInstitucion();
		if(instituciones != null && !instituciones.isEmpty()){
			for(Institucion institucionAux : instituciones) {
				if(institucionAux.getIdInstitucion().compareTo(idInstitucionOrigen) == 0){
					beneficiarioConInstitucion = true;
					break;
				}
			}
		}		
		return beneficiarioConInstitucion;
	}
	
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esUoiNulo(String)
    */
	public boolean esUoiNulo(String uoi){
		boolean isNull = false;
		if(uoi == null){
			isNull = true;
		}		
		return isNull;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esAdpNulo(String)
    */
	public boolean esAdpNulo(String adp){
		boolean isNull = false;
		if(adp == null){
			isNull = true;
		}		
		return isNull;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esClaveInstitucionNula(String)
    */
	public boolean esClaveInstitucionNula(String claveInstitucion){
		boolean isNull = false;
		if(claveInstitucion == null){
			isNull = true;
		}		
		return isNull;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esFormatoUoiIncorrecto(String)
    */
	public boolean esFormatoUoiIncorrecto(String uoi){
		boolean formatoIncorrecto = false;		
		if(uoi.trim().length() > LONGITUD_MAXIMA_VALOR_UOI || uoi.trim().length() < LONGITUD_MINIMA_VALOR_UOI || !patronUoi.matcher(uoi).matches()){
			formatoIncorrecto = true;
		}
		return formatoIncorrecto;
	}
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esFormatoAdpIncorrecto(String)
    */
	public boolean esFormatoAdpIncorrecto(String adp){
		boolean formatoIncorrecto = false;
		if(adp.trim().length() > LONGITUD_MAXIMA_VALOR_ADP || adp.trim().length() < LONGITUD_MINIMA_VALOR_ADP || !patronAdp.matcher(adp).matches()){
			formatoIncorrecto = true;
		}
		return formatoIncorrecto;		
	}
	
	
	/**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService#esFormatoClaveInstitucionIncorrecto(String)
    */
	public boolean esFormatoClaveInstitucionIncorrecto(String claveInstitucion){
		boolean formatoIncorrecto = false;
		if(claveInstitucion.trim().length() != LONGITUD_INSTITUCION || !patronInstitucion.matcher(claveInstitucion).matches()){
			formatoIncorrecto = true;
		}
		return formatoIncorrecto;
	}
	
	
	
	/**se inyectan las dependencias*/
	public void setMulticargaInternacionalDao(
			MulticargaInternacionalDao multicargaInternacionalDao) {
		this.multicargaInternacionalDao = multicargaInternacionalDao;
	}
				
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}
	
	
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}
}
