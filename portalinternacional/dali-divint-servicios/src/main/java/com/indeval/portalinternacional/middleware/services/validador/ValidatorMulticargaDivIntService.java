package com.indeval.portalinternacional.middleware.services.validador;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;

public interface ValidatorMulticargaDivIntService {
			
	/**Servicio para validar el nombre del archivo a cargar
	 * @param String
	 * @return true si existe el nombre de este Archivo, false si no existe el nombre del Archivo
	 * */
	public boolean esNombreArchivoValido(String nombreArchivo);
	
	/**Servicio para validar que el VO MulticargaVO tenga la informaci&oacute;n necesaria para la carga
	 * @param  MulticargaVO
	 * @throws BusinessException
	 * */
	public void validaMulticargaVO(MulticargaVO multicargaVO)throws BusinessException;
	
	/**
	 * servicio para validar que el Beneficiario no tenga un Adp asignado
	 * @param Beneficiario
	 * @return true si ya tiene asignado un Adp, false si no tiene asignado un Adp	 
	 * */
	public boolean esBeneficiarioConAdpAsignado(Beneficiario beneficiario);
	
	/**
	 * servicio para validar si el Beneficiario tiene un estado diferente de autorizado
	 * @param Beneficiario
	 * @return true si no esta autorizado, false si esta autorizado
	 * */
	public boolean esBeneficiarioDiferenteAutorizado(Beneficiario beneficiarioAux);
	
	/**
	 * servicio para validar si el nombre del custodio es diferente Deutsche
	 * @param String nombre del custodio
	 * @return true si el valor del nombre es diferente de  Deutsche, false si es Deutsche  
	 * */
	public boolean esBeneficiarioDiferenteDeutsche(String nombreCustodio);
	
	/**
	 * Regresa el nombre del custodio al que pertenece el Beneficiario buscando por su cuenta nombrada
	 * @param Beneficiario
	 * @return String
	 * */
	public String nombreCustodioPorCuentaNombrada(Beneficiario beneficiarioAux);
	
	/**
	 * servicio para validar si el Beneficiario cuenta con la instituci&oacute;n 
	 * @param Beneficiario beneficiario, Long idInstitucionOrigen
	 * @return regresa true si el beneficiario cuenta con la instituci&oacute;n, false si no cuenta con la instituci&oacute;n
	 * */
	public boolean validaInstitucionBeneficiario(Beneficiario beneficiarioAux, Long idInstitucionOrigen);
	
	/**
	 * servicio para validar si el uoi es nulo 
	 * @param String uoi
	 * @return regresa true si el valor del uoi es nulo, false si es diferente de nulo
	 * */
	public boolean esUoiNulo(String uoi);
	
	/**
	 * servicio para validar si el adp es nulo 
	 * @param String adp
	 * @return regresa true si el valor del adp es nulo, false si es diferente de nulo
	 * */
	public boolean esAdpNulo(String adp);
	
	/**
	 * servicio para validar si la clave instituci&oacute;n es nulo 
	 * @param String claveInstitucion
	 * @return regresa true si el valor del adp es nulo, false si es diferente de nulo
	 * */
	public boolean esClaveInstitucionNula(String claveInstitucion);
	
	/**
	 * servicio para validar si el formato del uoi es Incorrecto 
	 * @param String uoi
	 * @return regresa true si el valor del uoi tiene formato Incorrecto, false si es correcto
	 * */
	public boolean esFormatoUoiIncorrecto(String uoi);
	
	/**
	 * servicio para validar si el formato del adp es Incorrecto 
	 * @param String adp
	 * @return regresa true si el valor del adp tiene formato Incorrecto, false si es correcto
	 * */
	public boolean esFormatoAdpIncorrecto(String adp);
	
	/**
	 * servicio para validar si el formato de la clave instituci&oacute;n es Incorrecto 
	 * @param String claveInstitucion
	 * @return regresa true si el valor de la clave instituci&oacute;n tiene formato Incorrecto, false si es correcto
	 * */
	public boolean esFormatoClaveInstitucionIncorrecto(String claveInstitucion);
	
}
