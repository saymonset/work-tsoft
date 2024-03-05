/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoFvVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface EnvioOperacionesService {

	public String generaIso(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, Date fechaHoraCierreOper, Boolean esConfirmacion);

	public String generaIso(RetiroEfectivoInternacionalDTO REI);

	public String generaIso(RetiroEfectivoDTO Rete);



	/**
	 * Envia al protocolo la operacion correspondiente a traspasos contra pago
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 *            true - Indica que es una operacion de compra (para hacer
	 *            match)
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public void enviaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException;

	/**
	 * Envia al protocolo la operacion correspondiente a traspasos libres de
	 * pago
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 *            true - Indica que es una operacion de compra (para hacer
	 *            match)
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public void enviaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException;

	/**
	 * Retorna un BitacoraVOTotales con los registros de la bitacora para una
	 * determinada instituci&oacute;n de acuerdo a los filtros enviados.
	 *
	 * @param bitacoraVO
	 * @return PaginaVO .
	 * @throws BusinessException
	 */
	public PaginaVO getMensajeBitacora(BitacoraVOParams bitacoraVO) throws BusinessException;

	/**
	 * Retorna un arreglo con los registros de la bitacora de Match para una
	 * determinada instituci&oacute;n de acuerdo a los filtros enviados.
	 *
	 * @param bitacoraVO
	 * @param bitacoraVO
	 *            Parametro de entrada, debe llevar setteados los atributos:
	 *            'idTraspasante' y 'folioTraspasante' || 'idReceptor' y
	 *            'folioReceptor'
	 * @return BitacoraMatchVO[]
	 * @throws BusinessException
	 */
	BitacoraMatchVO[] getMensajeBitacoraMatch(BitacoraMatchParams bitacoraVO) throws BusinessException;

	/**
	 * Solo se utliza para realizar la prueba de recepci&oacute;n de mensajes a
	 * trav&eacute;s de iniciaListener()
	 *
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public void recibeOperacion() throws ProtocoloFinancieroException, BusinessException;

	/**
	 * Envia operaciones de traspasos contra pago sin generar la referencia del
	 * mensaje ni grabar en la bitacora
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 *            true - Indica que la operacion es una compra (para hacer
	 *            match)
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public void enviaOperacionSimple(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException;

	/**
	 * Envia operaciones de traspasos libres de pago sin generar la referencia
	 * del mensaje ni grabar en la bitacora
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 *            true - Indica que la operacion es una compra (para hacer
	 *            match)
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public void enviaOperacionSimple(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException;

//	/**
//	 * Reenvia las operaciones correspondientes a traspasos contra pago
//	 *
//	 * @param vo
//	 * @param folioControl
//	 * @param cola
//	 * @param isCompra
//	 *            true - Indica que la operacion es una compra (para hacer
//	 *            match)
//	 * @throws ProtocoloFinancieroException
//	 * @throws BusinessException
//	 */
//	public void reenviaOperacion(TraspasoContraPagoVO vo, String folioControl, int cola, boolean isCompra) throws ProtocoloFinancieroException,
//			BusinessException;
//
//	/**
//	 * Reenvia las operaciones correspondientes a traspasos libres de pago
//	 *
//	 * @param vo
//	 * @param folioControl
//	 * @param cola
//	 * @param isCompra
//	 *            true - Indica que la operacion es una compra (para hacer
//	 *            match)
//	 * @throws ProtocoloFinancieroException
//	 * @throws BusinessException
//	 */
//	public void reenviaOperacion(TraspasoLibrePagoVO vo, String folioControl, int cola, boolean isCompra) throws ProtocoloFinancieroException,
//			BusinessException;

//	/**
//	 * Metodo utilizado para la consulta de la bitacora operaciones pero con
//	 * solo el filtro de estatus de registro. este metodo solo obtiene las
//	 * operaciones con fecha de concertacion a la fecha actual
//	 *
//	 * @param estatusRegistro
//	 * @param origen
//	 * @return List
//	 * @throws BusinessException
//	 */
//	List getMensajeBitacoraXEstatusRegistro(String estatusRegistro, String origen) throws BusinessException;

	/**
	 * Obtiene los totales de estatus registro de bitacora
	 *
	 * @param bitacoraVO
	 * @return BitacoraVOTotales .
	 * @throws BusinessException
	 */
	BitacoraVOTotales getTotalesBitacora(BitacoraVOParams bitacoraVO) throws BusinessException;

	/**
	 * Graba operaciones contra pago para ser enviadas al protocolo por el
	 * dispatcher
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @throws BusinessException
	 */
    void grabaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra,
            HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;


    void grabaOperacion(RetiroEfectivoDTO vo, HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;


	/**
	 * Graba operaciones contra pago para ser enviadas al protocolo por el
	 * dispatcher
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @throws BusinessException
	 */
    void grabaOperacion(RetiroEfectivoInternacionalDTO vo, String folioControl, boolean isCompra,
            HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;


	/**
	 * Graba operaciones contra pago para ser enviadas al protocolo por el
	 * dispatcher
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @throws BusinessException
	 */
    void grabaOperacion(TraspasoEfectivoFvVO  vo, String folioControl, boolean isCompra,
            HashMap datosAdicionales, String origenRegistro, String isoFirmado,boolean isCancelacion) throws BusinessException;

	/**
	 * Graba operaciones libres de pago para ser enviadas al protocolo por el
	 * dispatcher
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 * @param datosAdicionales
	 * @param origenRegistro
     * @param isoFirmado
	 * @throws BusinessException
	 */
    void grabaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra,
            HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;

	/**
	 * Graba operaciones contra pago para ser enviadas al protocolo por el
	 * dispatcher - No se pide folioControl
	 *
	 * @param vo
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @return BigInteger
	 * @throws BusinessException
	 */
    BigInteger grabaOperacion(TraspasoContraPagoVO vo, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;

	/**
	 * Graba operaciones libres de pago para ser enviadas al protocolo por el
	 * dispatcher - No se pide folioControl
	 *
	 * @param vo
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @return BigInteger
	 * @throws BusinessException
	 */
    BigInteger grabaOperacion(TraspasoLibrePagoVO vo, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;

	/**
//	 * Indica si la institucion porporcionada utiliza el protocolo financiero
//	 * indeval (PFI)
//	 *
//	 * @param agenteFirmado
//	 * @return boolean true - si la institucion opera con protocolo
//	 * @throws BusinessException
//	 */
//	boolean validaInstitucionPFI(AgenteVO agenteFirmado) throws BusinessException;

	/**
	 * Confirma (registra) una operaci&oacute;n match.
	 * @param idBitacoraMatch
	 * @param agenteFirmado
	 * @return BigInteger
	 * @throws BusinessException
	 */
	BigInteger confirmaOperacionMatch(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, HashMap datosAdicionales, String isoFirmado, Date fechaHoraCierreOper) throws BusinessException;

	/**
	 * Marca como cancelada o expirada una operacion en Match de acuerdo al
	 * parametro operacion recibido (true = expira, false= cancela )
	 *
	 * @param idBitacoraMatch
	 * @param agenteFirmado
	 * @param operacion
	 * @return int
	 * @throws BusinessException
	 */
	BigInteger actualizaEstadoInstruccionExpira(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, HashMap datosAdicionales, String isoFirmado) throws BusinessException;

//	/**
//	 * Graba operaciones contra pago para ser enviadas al protocolo por el
//	 * dispatcher
//	 *
//	 * @param vo
//	 * @param folioControl
//	 * @param isCompra
//	 * @throws BusinessException
//	 */
//	void grabaOperacionSybase(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws BusinessException;

//	/**
//	 * Graba operaciones libres de pago para ser enviadas al protocolo por el
//	 * dispatcher
//	 *
//	 * @param vo
//	 * @param folioControl
//	 * @param isCompra
//	 * @throws BusinessException
//	 */
//	void grabaOperacionSybase(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws BusinessException;

//	/**
//	 * Graba operaciones contra pago para ser enviadas al protocolo por el
//	 * dispatcher - No se pide folioControl
//	 *
//	 * @param vo
//	 * @param isCompra
//	 * @return BigInteger
//	 * @throws BusinessException
//	 */
//	BigInteger grabaOperacionSybase(TraspasoContraPagoVO vo, boolean isCompra) throws BusinessException;

//	/**
//	 * Graba operaciones libres de pago para ser enviadas al protocolo por el
//	 * dispatcher - No se pide folioControl
//	 *
//	 * @param vo
//	 * @param isCompra
//	 * @return BigInteger
//	 * @throws BusinessException
//	 */
//	BigInteger grabaOperacionSybase(TraspasoLibrePagoVO vo, boolean isCompra) throws BusinessException;

	/**
	 * Indica si la institucion porporcionada utiliza el protocolo financiero
	 * indeval (PFI)
	 *
	 * @param agenteFirmado
	 * @return boolean true - si la institucion opera con protocolo
	 * @throws BusinessException
	 */

	/**
	 * Graba operaciones de retiro de efectivo para ser enviadas al protocolo
	 * por el dispatcher
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
	 * @throws BusinessException
	 */
    void grabaOperacion(RetiroEfectivoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;

	/**
     * Graba operaciones de traspaso de efectivo para ser enviadas al protocolo por el dispatcher
	 * @param traspasoEfectivoVO
	 * @param folioControl
	 * @param isCompra
     * @param datosAdicionales
     * @param origenRegistro
     * @param isoFirmado
     * @throws BusinessException
     */
    void grabaOperacion(TraspasoEfectivoVO traspasoEfectivoVO, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado) throws BusinessException;

    /**
     * Crea el iso correspondiente a los datos proporcionados
     *
     * @param vo
     * @param isCompra
     * @return String - ISO valido
	 * @throws BusinessException
	 */
    public String creaISO(Object vo, boolean isCompra, boolean esConfirmacion) throws BusinessException;

    public PaginaVO getBitacoras( Object bitacoraOperaciones, PaginaVO paginaVO  );

    /**
     * @param depositoDivisa
     */
    public void grabaOperacion(DepositoDivisaDTO depositoDivisa, Map<String, Object> datosAdicionales, String isoFirmado);

}
