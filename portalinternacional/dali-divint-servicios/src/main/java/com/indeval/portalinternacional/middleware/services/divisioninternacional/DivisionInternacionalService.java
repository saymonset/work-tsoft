/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.AltaCustodioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoParams;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaFideicomisosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.middleware.servicios.vo.HistorialBitacoraOperacionesSICDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalArqueoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author <a href="mailto:julio@itbrain.com.mx">Julio G&oacute;mez Resendiz.</a> 2006
 */
public interface DivisionInternacionalService {
	final Integer BOVEDA_EFECTIVO_INTERNACIONAL=2;
	final Integer BOVEDA_VALORES_INTERNACIONAL=3;
    /**
     * Graba la operacion en la tabla BITACORA_OPERACIONES (BitacoraOperaciones)
     *
     * @param grabaOperacionParams
     * @throws BusinessException
     */
    public void grabaOperacion(GrabaOperacionParams grabaOperacionParams) throws BusinessException;


    /**
     * Realiza validaciones en la captura de traspasos de inversion extranjera de Div. Int.
     *
     * @param tlpVO
     * @return BigInteger Folio de liquidacion de la operacion
     * @throws BusinessException
     */
    public BigInteger businessRulesCapturaTraspaso(TraspasoLibrePagoVO tlpVO) throws BusinessException;

    /**
     * Consulta las operaciones del SIC
     * @param operacionSic
     * @param paginaVO
     * @param rol
     *
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO consultaOperaciones(OperacionSic operacionSic, PaginaVO paginaVO,
    		Boolean debeDejarLog, Boolean obtenerTotalReg, Boolean rolSic) throws BusinessException;

    /**
     * Consulta las operaciones del SIC dado un listado de folios de control.
     * @param foliosControl
     * @param paginaVO
     *
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO consultaOperacionesPorFoliosControl(List<BigInteger> foliosControl, PaginaVO paginaVO) throws BusinessException;

    /**
     * Captura las operaciones de valores de
     * los usuarios que reciben o entregan en el extranjero. Utiliza el SP
     * catalogo..UP_libera_city adem&aacute;s guarda informaci&oacute;n en la tabla
     * catalogo..mensaje_sic
     *
     * @param operacionSic
     * @return String Folio de la operaci&oacute;n
     * @throws BusinessException
     */
    public OperacionSic businessRulesCapturaOperacion(OperacionSic operacionSic) throws BusinessException;

    /**
     * Inserta un registro en catalogo..oper_sic, dados los valores en el objeto OperacionSICVO.
     * @param operacionSic
     * @throws BusinessException
     */
    public void insertaOperacionSIC(OperacionSic operacionSic) throws BusinessException;

    /**
     * Actualiza el registro de la operacion correspondiente a la liberacion o autorizacion
     * @param operacionSic
     *
     * @return int - Numero de regsitro actualizados
     * @throws BusinessException
     */
    public int actualizaOperacionSIC(OperacionSic[] operacionSic) throws BusinessException;
    
    /**
     * Actualiza el registro de la operacion correspondiente a la liberacion de la parcialidad
     * @param operacionSic
     *
     * @return Boolean
     * @throws BusinessException
     */
    public Boolean actualizaParcialidadOperacionSIC(OperacionSic operacionSic) throws BusinessException;

    /**
     * Obtiene un arreglo de instancias de CatBic que representan a los custodios de la emision
     * proporcionada
     *
     * @param emisionVO
     * @return CatBic[]
     * @throws BusinessException
     */
    public CatBic[] obtieneCustodios(EmisionVO emisionVO) throws BusinessException;

    /**
     * Obtiene un arreglo de instancias de CatBic que representan a los custodios de la emision
     * proporcionada
     * @param emisionVO La representacion de la emision que se usa para obtener el arreglo de instancias de CatBic.
     * @return El arreglo de instancias de CatBic.
     * @throws BusinessException En caso de error.
     */
    CatBic[] obtieneCustodioEnBaseAEmision(EmisionVO emisionVO) throws BusinessException;

    /**
     * Obtiene un arreglo de instancias de SicDetalle que representan a los depositantes/liquidadores
     * correspondientes al agente proporcionado
     *
     * @param catBic
     * @return SicDetalle[]
     * @throws BusinessException
     */
    public SicDetalle[] obtieneDepositantes(CatBic catBic) throws BusinessException;

    /**
     * Obtiene los estatus de las operaciones del SIC
     *
     * @return EstatusOperacion[]
     * @throws BusinessException
     */
    public EstatusOperacion[] obtieneEstatusOperacion() throws BusinessException;

    
    /**metodo para traer listado de custodios que se encuentran en OperacionSic*/
    
    public  List<String> listaCustodios()  throws BusinessException; 
    /**
     * Obtiene una lista de emisiones Fideicomiso para un instituci&oacute;n en la fecha proporcionada
     * @param agenteVO
     * @param fechaConsulta
     * @return EmisionVO[]
     * @throws BusinessException
     */
    public EmisionVO[] getListaEmisionesFideicomisoArqueo(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException;

    /**
     * Obtiene el arqueo de valores para una institucion y una lista de sus emisiones en la fecha proporcionada
     * @param agenteVO
     * @param emisionVO
     * @param totalArqueoVO
     * @param fechaConsulta
     * @param isExport
     * @return ArqueoVO
     * @throws BusinessException
     */
    public ArqueoVO getArqueoValores(AgenteVO agenteVO, EmisionVO emisionVO, TotalArqueoVO totalArqueoVO, Date fechaConsulta, Boolean isExport) throws BusinessException;

    /**
     * Obtiene emisiones que correspondan a los del agente en sesi&oacute;n; con la alta CPOS, ADCP,
     * IADC, BADC, VIVI y GADC, con las terminaciones de cuenta en 54, 94, 97
     * y 98; cuyo saldo disponible sea mayor que cero y tengan un cup&oacute;n vigente
     * @param emisionVO
     * @param paginaVO
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO getTopeCirculanteFidecomiso(EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException;

    /**
     * Consulta las emisiones y los depositantes de un custodio
     * @param agenteVO
     * @return List<ConsultaCustodiosVO>
     * @throws BusinessException
     */
    public List<ConsultaCustodiosVO> consultaCustodios(AgenteVO agenteVO) throws BusinessException;

    /**
     * Realiza el alta de un custodio
     * @param altaCustodioVO
     * @throws BusinessException
     */
    public void altaCustodio(AltaCustodioVO altaCustodioVO) throws BusinessException;

    /**
     * Realiza el alta de un depositante para el cutodio proporcionado
     * @param CatBic
     * @param bicDepLiq
     * @param idDepLiq
     * @throws BusinessException
     */
    public void altaDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq) throws BusinessException;

    /**
     * Realiza el alta de una emisi&oacute;n SIC para el custodio proporcionado
     * @param emisionVO
     * @param CatBic
     * @param formaDeOperar
     * @throws BusinessException
     */
    public void altaEmision(EmisionVO emisionVO, CatBic catBic, String formaDeOperar) throws BusinessException;

    /**
     * @param fechaConsulta
     * @return List<ConsultaCierreFideicomisoVO>
     * @throws BusinessException
     */
    public List<ConsultaCierreFideicomisoVO> getListaCierreFideicomiso(ConsultaCierreFideicomisoParams consultaCierreFideicomisoParams) throws BusinessException;

    /**
     * Obtiene la lista de emisiones SIC correspondientes a los criterios de b&uacute;squeda proporcionados
     * @param catBic
     * @param emisionVO
     * @param formaDeOperar
     * @param paginaVO
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO getListaSicEmisiones(CatBic catBic, EmisionVO emisionVO, String formaDeOperar, PaginaVO paginaVO) throws BusinessException;

    /**
     * Obtiene la lista de emisiones SIC correspondiente a los criterios de busqueda proporcionados para la consulta de Emisiones
     * @param catBic  criterio para el Custodio
     * @param emisionVO  criterios con los datos de la Emision
     * @param paginaVO  objeto de paginacion
     * @return
     * @throws BusinessException
     */
    public PaginaVO getListaEmisionesCustodio(CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException;

    /**
     * Obtiene la lista de detalles SIC correspondientes a los criterios de b&uacute;squeda proporcionados
     * @param catBic
     * @param emisionVO
     * @param bicDepLiq
     * @param idDepLiq
     * @param paginaVO
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO getListaSicDetalle(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Actualiza a un registro de custodio
	 * @param altaCustodioVO
	 * @param catBic
	 * @throws BusinessException
	 */
	public void updateCustodio(AltaCustodioVO altaCustodioVO, CatBic catBic) throws BusinessException;

	/**
	 * Actualiza a un registro de depositante
	 * @param catBic
	 * @param bicDepLiq
	 * @param idDepLiq
	 * @param depLiq
	 * @param sicDetalle
	 * @throws BusinessException
	 */
	public void updateDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, SicDetalle sicDetalle) throws BusinessException;

	/**
	 * Actualiza a un registro de SIC emisi&oacute;n
	 * @param emisionVO
	 * @param catBic
	 * @param sicEmision
	 * @param formaDeOperar
	 * @throws BusinessException
	 */
	public void updateSicEmision(EmisionVO emisionVO, CatBic catBic, SicEmision sicEmision, String formaDeOperar) throws BusinessException;

	/**
	 * Cancela (borrado l&oacute;gico) el registro de custodio proporcionado
	 * @param catBic
	 * @throws BusinessException
	 */
	public void cancelaCustodio(CatBic catBic) throws BusinessException;

	/**
	 * Cancela (borrado l&oacute;gico) el registro de depositante proporcionado
	 * @param sicDetalle
	 * @throws BusinessException
	 */
	public void cancelaDepositante(SicDetalle sicDetalle) throws BusinessException;

	/**
	 * Cancel (borrado l&oacute;gico) el registro de SIC emisi&oacute;n proporcionado
	 * @param sicEmision
	 * @throws BusinessException
	 */
	public void cancelaSicEmision(SicEmision sicEmision) throws BusinessException;

	/**
	 * Realiza la consulta de Fideicomisos para el agente y la fecha proporcionados
	 * @param agenteVO
	 * @param fechaConsulta
	 * @return List<ConsultaFideicomisosVO>
	 * @throws BusinessException
	 */
	public List<ConsultaFideicomisosVO> consultaFideicomisos(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException;

	/**
	 * Obtiene todos los custodios
	 * @return List<Object[]>
	 * @throws BusinessException
	 */
	public List<Object[]> obtieneAllCatBic() throws BusinessException;

	/**
	 * Obtiene todos los estados de las emisiones
	 * @return List<EstatusEmisionesDTO>
	 * @throws BusinessException
	 */
	public List<EstatusEmisionesDTO> obtieneEmisionesEstatus() throws BusinessException;

	/**
	 * Obtiene un objeto de Estatus de Emisiones
	 * @param idEstatusEmisiones
	 * @return
	 * @throws BusinessException
	 */
	public EstatusEmisionesDTO obtenerEstatusEmisionByPk(BigInteger idEstatusEmisiones) throws BusinessException;

	/**
	 * Realiza la consulta de emisiones
	 * @param estatusEmisionesDTO
	 * @param catBic
	 * @param emisionVO
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	public PaginaVO findSicEmisionesByEmisionAndCustodio(EstatusEmisionesDTO estatusEmisionesDTO,
			CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO, Boolean debeDejarLog) throws BusinessException;

    /**
     * Realiza la consulta de emisiones que tienen Posicion Cero.
     * @param estatusEmisionesDTO
     * @param catBic
     * @param emisionVO
     * @param paginaVO
     * @return
     * @throws BusinessException
     */
    public PaginaVO findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO estatusEmisionesDTO,
                                                                     CatBic catBic, 
                                                                     EmisionVO emisionVO, 
                                                                     PaginaVO paginaVO) throws BusinessException;

	/**
	 * Consulta el calendario de derechos
	 * @param regxpag
	 * @param offset
	 * @param dto
	 * @return CalendarioDerechosDTO de la consulta
	 * @throws BusinessException
	 */
	public PaginaVO consultaCalendarioDerechos(CalendarioEmisionesDeudaExtDTO params, PaginaVO pagina) throws BusinessException;

	/**
	 * Consulta el calendario de derechos
	 * @param regxpag
	 * @param offset
	 * @param dto
	 * @return CalendarioDerechosDTO de la consulta
	 * @throws BusinessException
	 */
	public CalendarioDerechos consultaCalendarioDerechosById(Long id) throws BusinessException;

	/**
	 * Consulta los estados de derechos Internacionales

	 * @throws BusinessException
	 */
	public List<EstadoDerechoInt> obtieneEstadosDerechoInt()throws BusinessException;

	/**
	 * Consulta los tipos de pago Internacional

	 * @throws BusinessException
	 */
	public List<TipoPagoInt> obtieneTiposPagoInt(Boolean isCAEV)throws BusinessException;

	/**
	 * Consulta el catalogo de custodios

	 * @throws BusinessException
	 */
	public List<Custodio> obtieneCatalogoCustodios()throws BusinessException;

	/**
	 *
	 * @param ids
	 * @param nuevoEstado
	 * @return
	 * @throws BusinessException
	 */
	public Integer actualizarEstadosDerechoInt(Set<Long> ids, Integer nuevoEstado, boolean esSu)throws BusinessException;
	/**
	 *
	 * @param id
	 * @return
	 */
	public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwift(Long id)throws BusinessException;
	/**
	 * obtiene el catalogo de Bovedas
	 * @param soloInternacional solo obtiene las bovedas de internacional
	 * @return
	 * @throws BusinessException
	 */
	public List<Boveda> consultaBovedas(Integer tipoBoveda)throws BusinessException;

	/**
	 * manda la instruccion para que se cree el mensaje de retiro de efectivo en el MOI
	 * @param idCalendario
	 * @param idBoveda
	 */
	public void instruyeMensajeRetiro(Long idCalendario, Long idBoveda);

	/**
		 * Consulta OperacionesSic por el Id parametro
		 * @param id
		 * @return
		 */
	public OperacionSic consultaOperacionSicById(BigInteger id)throws BusinessException;
	
	/**
	 * Consulta los estados de derechos Internacionales	
	 * @throws BusinessException
	 */
	public List<Control> obtieneEstadosMensajeria(String id)throws BusinessException;

	/**
	 * Consulta Conciliaciones Internacionales
	 * @param conciliacion
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	public PaginaVO consultaConciliacion(ConciliacionIntDTO conciliacion, PaginaVO paginaVO)throws BusinessException;
	/**
	 * manda un mensaje al moi para ejecutar la conciliacion
	 * @param idConciliacion
	 */
	public void instruyeConciliacion(Long idConciliacion)throws BusinessException;

	/**
	 * consulta el  Detalle Conciliacion
	 * @param detalleConciliacion
	 * @param paginaVO
	 * @return paginaVO valueListHandler
	 */
	public PaginaVO consultaDetalleConciliacion(
			DetalleConciliacionIntDTO detalleConciliacion, PaginaVO paginaVO)throws BusinessException;

	/**
	 * Consulta los mensajes swift de una conciliacion
	 * @param id
	 * @return
	 */
	public List<BitacoraMensajeConciliacionInt> consultaBitacoraMensajeConciliacionInt(Long id)throws BusinessException;

	/**
	 * Realiza el cambio de boveda a las emisiones que no tienen posicion desde la pantalla de consulta de emisiones.
	 * @param listaEmisiones El listado de emisiones a cambiar.
	 * @param idBoveda La boveda a asignar.
	 * @throws BusinessException 
	 */
	void realizarCambioDeBoveda(List<SicEmision> listaEmisiones, long idBoveda) throws BusinessException;


	public void generaReporteAuditoriaConciliacion(Long idConciliacion)throws BusinessException;
	
	public Map<String,String> getDerechosAutomatizadosDeudaMap();
	
	public List<String> insertaCambioEstatusOperador(List<BitacoraOperacionesSic> bitacoraInsert,List<BitacoraOperacionesSic> bitacoraUpdate);
	
	public  List<String> cancelaSolicitudBitacoraSic(List<BitacoraOperacionesSic> bitacorasCanceladas);
	
	public PaginaVO consultaOperacionesPorFoliosControlAutorizador(List<BigInteger> foliosControl, PaginaVO paginaVO);
	
    public void actualizaOperacionSICAutorizador(List<OperacionSic> operacionesSic,String cveAutorizo);
    
    public void actualizaOperacionSICAutorizadorParcialidades(List<LiquidacionParcialMoi> listaLiquidacionesParciales, String cveAutorizo, OperacionSic operacionSic);
    
    public List<String> actualizaMotivoCambioBitacoraSic(List<BitacoraOperacionesSic> motivosActualizadosLst);

    /**
     * Servicio que consulta el historial de bitacora operaciones SIC, que no son Cambio de Boveda
     * @param hist El objeto con los parametros de busqueda
     * @param paginaVO El objeto donde incluir el resultado
     * @param seAplicaCambioDeBoveda True indica que la consulta se lanzo desde cambio de boveda, false en caso contrario
     * @return El objeto PaginaVO con los registros encontrados
     * @throws BusinessException En carro de error
     */
    public PaginaVO obtieneHistorialBitacoraSIC(HistorialBitacoraOperacionesSICDTO hist, PaginaVO paginaVO, boolean seAplicaCambioDeBoveda) throws BusinessException;
    
    public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwiftByHist(Long id)	throws BusinessException;

    /**
     * Valida la existencia de un tipo valor en la BD.
     * @param tv El tipo valor a verificar en la BD.
     * @return true si existe, false en caso contrario.
     * @throws BusinessException en caso de error.
     */
    boolean validarExistenciaTipoValor(String tv) throws BusinessException;

    /**
     * Obtiene el listado de tipos de beneficiarios de la tabla C_TIPOS_BENEFICIARIOS
     * @return El listado de objetos
     * @throws BusinessException En caso de error
     */
    List<TipoBeneficiario> getTiposBeneficiario() throws BusinessException;

    /**
     * Obtiene un objeto TipoBeneficiario por id, de la tabla C_TIPOS_BENEFICIARIOS
     * @param id El id del registro a obtener
     * @return El objeto del registro encontrado
     * @throws BusinessException En caso de errror
     */
    TipoBeneficiario getTipoBeneficiarioById(Long id) throws BusinessException;

    /**
     * Validacion de que la nueva boveda en la recepcion de un cambio de boveda ya se encuentre en la emision 
     * y en la relacion de la emision con las bovedas (tabla R_EMISION_BOVEDA). 
     * @param idEmision El id de la emision a verificar
     * @param idBoveda El id de la boveda a verificar
     * @return True en caso de que ya se encuentre el cambio en C_EMISIO y en R_EMISION_BOVEDA, false en caso contrario
     * @throws BusinessException En caso de error
     */
    boolean validarNuevaBovedaEnEmision(Long idEmision, Long idBoveda) throws BusinessException;

    /**
     * Valida la existencia de posicion en la cuenta 5001 de Indeval de la tabla T_POSICION_NOMBRADA.
     * @param idOperacionSic El id de la operacion sic de entrega para obtener la posicion total del cambio de boveda
     * @param idBoveda El id de la boveda para obtener la posicion disponible de la cuenta 5001 de Indeval
     * @param idEmision El id de la emmision para obtener la posicion disponible de la cuenta 5001 de Indeval
     * @return True si la posicion total de la operacion de cambio de boveda coincide con la posicion disponible de la cuenta 5001 de Indeval
     * @throws BusinessException En carro de error
     */
    boolean existePosicionEnCuenta5001Indeval(Long idOperacionSic, Long idBoveda, Long idEmision) throws BusinessException;

    /**
     * Realiza la actualizacion de estado de la operacion sic de cambio de boveda y a su vez, si es necesario,
     * instruye el el cambio de estado a MOI enviando un mensaje de cambio de estado de cambio de boveda. 
     * @param operacionesSic El listado de operaciones sic a cambiar de estado
     * @param autorizo El usuario que autoriza los cambios de estado de los cambios de boveda
     * @throws BusinessException En carro de error
     */
    void actualizaOperacionSICCambioBovedaAutorizador(List<OperacionSic> operacionesSic, String autorizo) throws BusinessException;
    
    /**
     * Metodo para obtener detalle de las liquidaciones parciales
     * @param folioControl
     * @return
     */
    public List<LiquidacionParcialMoi> getLiqParcialMoi(Long folioControl);
    
    /**
     * Metodo para obtener detalle de las liquidaciones parciales
     * @param paginaVO
     * @param folioControl
     * @return
     */
    public PaginaVO getLiqParcialMoi(PaginaVO paginaVO, Long folioControl);
    
    /**
     * Metodo para obtener detalle de las liquidaciones parciales con bitacora de cambio de estatus
     * @param paginaVO
     * @param folioControl
     * @return
     */
    public PaginaVO getLiqParcialMoiWithBitacora(PaginaVO paginaVO, List<BigInteger> folioControl);
    
    /**
     * Metodo para obtener detalle de las liquidaciones parciales con bitacora de cambio de estatus
     * @param paginaVO
     * @param folioControl
     * @return
     */
    public PaginaVO getLiqParcialMoiChangeStatus(PaginaVO paginaVO, Long folioControl);
    
    /**
     * Metodo para actualizar bandera para liquidar liquidaciones parciales
     * @param folioControl
     * @param numeroLiquidacion
     * @param idEstatusOperacion
     */
    public void actualizaLiquidacionesParciales(Long folioControl, Long numeroLiquidacion, Long idEstatusOperacion);
    
    /**
     * Metodo para obtener operaciones parciales en estatus: Confirmada Parcial y Liberada Parcial
     * @param idCuentaNombrada
     * @return
     */
    public Long findOperacionesParcialesPendientes(Long idCuentaNombrada);
    
    public void updateStatusOperacionWithParcialidad(List<LiquidacionParcialMoi> lstLiquidacionParcialMoi, OperacionSic operacionSic); 

}
