/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.Map;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author javiles
 *
 */
public interface ValidatorDivIntService {

    /**
     * Valida el traspaso en base a las cuentas y la emision extranjera
     * 
     * @param traspasante
     * @param receptor
     * @param emisionVO
     * @param mensaje - Almacena el mensaje que hace invalido el traspaso
     * @return boolean
     * @throws BusinessException
     */
    boolean esTraspasoValido(AgenteVO traspasante, AgenteVO receptor,
            EmisionVO emisionVO, StringBuilder mensaje) throws BusinessException;
    
    /**
     * Valida que la cuenta del agente no sea de tesoreria, y no sea 0030,
     * 0031,0050 a 0057, 0070, 0080 ni 0081.
     * 
     * @param agenteVO
     * @return boolean
     * @throws BusinessException 
     */
    boolean esCuentaValida(AgenteVO agenteVO) throws BusinessException;

    
    /**
     * Verifica si el custodio es valido
     * @param tipoMensaje
     * @param descCustodio 
     * @return boolean
     * @throws BusinessException
     */
    boolean esCustodioValido(String tipoMensaje, String descCustodio) throws BusinessException;
    
    /**
     * Realiza las validaciones necesarias para insertar una operacion del SIC
     * 
     * @param operacionSic
     * @throws BusinessException
     */
    void validaOperacionSicCaptura(OperacionSic operacionSic) throws BusinessException;
    
    /**
     * Realiza las validaciones necesarias para consultar operaciones del SIC
     * 
     * @param operacionSic
     * @throws BusinessException
     */
    void validaOperacionSicConsulta(OperacionSic operacionSic) throws BusinessException;
    
    /**
     * Realiza las validaciones necesarias para insertar un trapaso libre de pago
     * a traves del servicio grabaOperacion().
     * 
     * @param tlpVO
     * @throws BusinessException
     */
    void validaTraspasoLibrePagoVO(TraspasoLibrePagoVO tlpVO) throws BusinessException;
    
    /**
     * Verifica el formato de los campos recibido en el FileTransfer 
     * @param indice
     * @param campos
     * @param msgError
     * @param numCampoError
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @return boolean
     */
    boolean validaFormato(Map<Object, Object> indice, String[] campos,
            StringBuffer msgError, StringBuffer numCampoError, int tam);
    
    /**
     * @param cantidad
     * @return boolean
     */
    boolean validaCantidades(String cantidad);
    
    /**
     * Lleva a cabo las validaciones hacer consultas en la tabla de
     * Beneficiario en  el metodo ControlBeneficiariosServiceImpl.consultaBeneficiarios()
     * @param beneficiario
     */
    void validaBeneficiarioConsulta(Beneficiario beneficiario) throws BusinessException ;

	/**
	 * Valida un statement
	 *
	 * @param statement Statement a validar
	 * @throws BusinessException En caso de error de validacion
	 */
	public void validaStatementDivint(StatementDivint statement) throws BusinessException;
    
    /**
     * Valida una institucion
     * 
     * @param Long idInstitucion
     * @throws BusinessException
     */
    public void validaInstitucion(Long idInstitucion) throws BusinessException;
    
 }
