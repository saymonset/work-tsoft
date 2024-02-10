/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO;

/**
 * Interface para validar diferentes tipos de formato
 * 
 * @author César Hernández
 *
 */
public interface ValidatorBeneficiarioService<T>{
	
	/**
	 * Valida las reglas de formato y negocio del formato que implementa el método
	 * @param beneficiario
	 * @throws BusinessException
	 */
	public void validaFormatoW(Beneficiario beneficiario) throws BusinessException;
	
	/**
	 * Valida las reglas del Domicilio del formato que implementa el método
	 * @param domicilio
	 * @param tipoDomicilio
	 * @throws BusinessException
	 */
	public void validaDomicilioBeneficiario(Domicilio domicilio, String tipoDomicilio) throws BusinessException;
	
	/**
	 * valida las reglas para cada formato de Beneficiario al momento de ser guardado
	 * @param beneficiario
	 * @param idInstitucion
	 * @throws BusinessException
	 * */
	public void validaCapturaBeneficiario(Beneficiario beneficiario, Long idInstitucion) throws BusinessException;
	
	/**
	 * valida que el VO y todos sus campos contenga la informaci&oacute;n requerida
	 * @param FileTransferCapturaBenefVO
	 * @throws BusinessException
	 */
	public void validaVOFiletransferBeneficiario(FileTransferCapturaBenefVO fileTransferCapturaBenefVO) throws BusinessException;
	
	/**
	 * valida si el campo  de tipo String es nulo o esta en blanco
	 * @param String campo
	 * @return boolean, true es es nulo o no tiene infomraci&oacute;n, false de lo contrario
	 */	
	public boolean esCampoFileTransferBeneficiarioNulo(String campo);
	
	/**
	 *valida que los campos del Registro contengan infomraci&oacute;n 
	 * @param fileTransferFormaW9
	 * @param listaRegistrosErrorAux
	 * @return true si todos los campos contienen informaci&oacute;n, false si uno o mas no contiene informaci&oacute;n
	 */
	public boolean esRegistroFileTransferValido(T fileTransferForma,List<T> listaRegistrosErrorAux);
	
	/**
	 *valida las reglas de negocio dependiendo el tipo de formato 
	 * @param FileTransferFormaW9 fileTransferFormaW9
	 * @param List<FileTransferFormaW9> listaRegistrosErrorAux
	 * @param String idClaveInstitucion
	 * @param boolean isIndeval
	 * @return tru si pasa todas las validaciones, false si no pasa alguna regla
	 */
	public boolean validaReglasNegocioBeneficiarioFiletransfer(T fileTransferFormaW9,List<T> listaRegistrosErrorAux,String idClaveInstitucion,boolean isIndeval);
	
	/**
	 * Metodo que inicializa el validador 
	 */
	public void init();
	
	/**
	 * Metodo que libera objetos que utilice el validador para una validacion
	 */
	public void release();
}
