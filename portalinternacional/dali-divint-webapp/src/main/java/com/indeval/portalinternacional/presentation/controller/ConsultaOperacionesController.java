/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portaldali.persistence.modelo.TipoCuenta;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portaldali.persistence.util.constants.DaliConstants;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaOperacionesSICService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.ErrorSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleLiquidacionParcialVO;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.middleware.servicios.vo.UsuarioVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller de la Captura de Operaciones para la opci&oacute;n de Captura de
 * Traspasos.
 * 
 * @author Marcos Rivas
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConsultaOperacionesController extends ControllerBase {

	private String idTipoValor;
	private String emisora;
	private String serie;
	private String cupon;

	// Atributos de la forma
	private Date fechaConsulta;
	private String estado;
	private String operacion;
	private String folioControl;
	private SelectItem[] data;
	private DivisionInternacionalService divisionInternacionalService;
	private OperacionSic params = new OperacionSic();
	private OperacionSic detalleOperacionSic;
	private ConsultaCatalogoService consultaCatalogoService;
	
	/* 09/04/2012 modificaciones para insercion en bitacoraOper 
	 * * Servicio utilitario para la secuencia */
	private UtilService utilService;
	static final String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";
	private CuponDao cuponDao;
	private EmisionVO emisionVO = new EmisionVO();
	private String idfolio;
	private UsuarioVO usuario;

	/** Establece el n&uacte;mero de registros encontrados */
	private String numResultados;

	/** variable que indica si se va a renderear el resultado */
	private Boolean renderResult = new Boolean(false);

	/** id mensaje */
	private String idMsg;

	/** mensaje */
	private String mensaje;
	/**
	 * Acceso a la consulta de cuenta nombradda
	 */
	private CuentaNombradaDao cuentaNombradaDao = null;

	/** Indica si la seccion de autorizacion sera presentada */
	private Boolean renderAutorizacion = Boolean.TRUE;

	/** Utilizado para saber si la confirmacion ser&aacute; con mensaje o sin mensaje */
	private boolean conMensaje;
	
	private boolean conMensajeParcial;

	/** Divisa. Utilizado para realizar consultas */
	private String divisa;

	/** importe, utilizado para hacer consultas */
	private BigDecimal importe;

	/** Mantiene el id del agente */
	private String idAgente;

	/** Mantiene el folio del agente */
	private String folioAgente;

	/** Mantiene la cuenta del agente */
	private String cuentaAgente;

	/** Bandera para propiedad readOnly de caja de texto id y folio de Agente */
	private boolean readOnlyAgente;

	/** variable para el estilo del agente */
	private String styleAgente;

	private Date fechaFinalConsulta;

	private Date fechaInicialConsulta;

	private String tipoOperacion;
	
	private String origenOperacion;

	private BigInteger cantidadOperada;

	private DateUtilService dateUtilService;

	/** Indica si la consulta debe dejar bitacora o no */
	private boolean debeDejarBitacora;
	
	/**Dao de divisas*/
	private DivisaDao divisaDao;
	
	private String referenciaOperacion;
	
	/*Variables para filtros ISIN, CUSTODIO*/
	private String isin;
	private String custodio;
	private List<SelectItem> listaCustodios;
		
	/**
	 * Indica si la consulta ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaOperacionesController.class);

	private ConsultaOperacionesSICService consultaOperacionesSICService;

	private String depliq;

	private List<SelectItem> listaDepLiq;

	private SicService sicService;

    private boolean esUsuarioCambioBoveda = false;
    
    private boolean esUsuarioSic = false;

    private String folioControlParcialidad;

    private DetalleLiquidacionParcialVO detalleLiquidacionParcial;

	public ConsultaOperacionesController() {
		inicializarParams();
	}

	public void inicializarParams() {
		params.setBoveda(new Boveda());
		params.setCatBic(new CatBic());
		params.setCuentaNombrada(new CuentaNombrada());
		params.getCuentaNombrada().setInstitucion(new Institucion());
		params.getCuentaNombrada().getInstitucion().setTipoInstitucion(
				new TipoInstitucion());
		params.setEmision(new Emision());
		params.getEmision().setInstrumento(new Instrumento());
		params.getEmision().setEmisora(new Emisora());
		params.setSicDetalle(new SicDetalle());
		params.setFechaConsulta(new Date[] {});
		params.setEstatusOperacion(new EstatusOperacion());
		params.setErrorSwift(new ErrorSwift());

		cuentaAgente = new String();
		idTipoValor = new String();
		emisora = new String();
		serie = new String();
		cupon = new String();

		estado = "-1";
		divisa = "-1";
		importe = null;

		cantidadOperada = null;
		tipoOperacion = new String();
		origenOperacion = new String();

		operacion = "-1";
		tipoOperacion = "-1";
		origenOperacion= "-1";
		
		folioControl = new String();
		referenciaOperacion = new String();
		
		isin = null;
		this.depliq = "-1";
		this.custodio = "-1";
	}

	/** Inicializa la fecha actual */
	public String getInit() {

		setFechaInicialConsulta(dateUtilService.getCurrentDate());
		setFechaFinalConsulta(dateUtilService.getCurrentDate());
		idfolio = getAgenteFirmado().getId() + getAgenteFirmado().getFolio();
		inicializaCustodios();
		this.obtenerListaDepositantesLiquidadores();

		//Verificacion de si el usuario firmado contiene el rol de cambio de boveda.
		if (this.usuarioContieneRolCambioBoveda()) {
            this.esUsuarioCambioBoveda = true;
        }
		
		if (this.isUsuarioSic()) {
            this.esUsuarioSic = true;
        }

		return null;
	}

	/**
	 * ejecuta por primera vez la consulta de busqueda
	 * 
	 * @param event
	 */
	public void obtenerOperaciones() {
		paginaVO.setOffset(0);
		paginaVO.setRegistros(null);
		crearCriterios();
		ejecutarConsulta();

	}// end obtenerOperaciones

	/**
	 * M&eacute;todo que ejecuta la consulta
	 */
	public String ejecutarConsulta() {
		try {
			boolean rolSic = this.esUsuarioSic;
			paginaVO = this.divisionInternacionalService.consultaOperaciones(params, paginaVO, debeDejarBitacora, true, rolSic);
			debeDejarBitacora = false;
			consultaEjecutada = true;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			agregarMensaje(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			agregarInfoMensaje(e.getMessage());
		} catch (Throwable e) {
			log.error(e.getMessage());
			agregarMensaje(e);
		}
		return null;

	}
	
	/**
	 * Mock
	 */

	public PaginaVO obtenerResultadosMock(OperacionSic parametros, PaginaVO page) {

		OperacionSic op = new OperacionSic();

		op.setIdOperacionesSic(1L);
		op.setCuentaNombrada(new CuentaNombrada());
		op.getCuentaNombrada().setInstitucion(
				new Institucion(getAgenteFirmado()));
		op.getCuentaNombrada().setCuenta("0307");
		op.setCuentaContraparte("512");
		op.setEmision(new Emision());
		op.getEmision().setInstrumento(new Instrumento());
		op.getEmision().getInstrumento().setClaveTipoValor("I");
		op.getEmision().setEmisora(new Emisora());
		op.getEmision().getEmisora().setClavePizarra("ALFA");
		op.getEmision().setSerie("01001");
		op.getEmision().setIsin("ISMX0101252252");
		op.setCantidadTitulos(new BigInteger("352000"));
		op.setTipoOperacion("T");
		op.setFechaOperacion(new Date());
		op.setFechaLiquidacion(new Date());
		op.setDivisa("MXP");
		op.setImporte(new BigDecimal(152222.25));
		// op.setStatus("EER");
		op.setErrorSwift(new ErrorSwift());
		op.getErrorSwift().setCodigoErrorSwift("INTERNA00");
		op.getErrorSwift().setDescErrorSwift("Generic International Error 00");
		op.setStPfi548("PFIERR");
		op.setRefMsjPfi("Error de PFI");

		page.setRegistros(new ArrayList<OperacionSic>());
		page.getRegistros().add(op);

		page.setTotalRegistros(1);
		page.setValores(new HashMap<String, BigDecimal>());

		return page;
	}
	

	/**
	 * Crea el objeto de criterio de consulta
	 */
	public void crearCriterios() {

		EmisionVO emisionVo = new EmisionVO();
		emisionVo.setTv(idTipoValor);
		emisionVo.setEmisora(emisora);
		emisionVo.setSerie(serie);
		
		
//		emisionVo.setCupon(cupon);

		params.setEmision(new Emision(emisionVo));
		
		
		if (StringUtils.isNotBlank(isin)) {
			emisionVo.setIsin(StringUtils.upperCase(isin.trim()));
			params.getEmision().setIsin(StringUtils.upperCase(isin));
		}
		params.setClaveCupon(cupon);
		params.setCuentaNombrada(new CuentaNombrada(
				new AgenteVO(AgenteViewHelper.obtenerIdInstitucion(idfolio),
						AgenteViewHelper.obtenerFolioInstitucion(idfolio),
						cuentaAgente)));
		params.getCuentaNombrada().setTipoCuenta(new TipoCuenta());
		params.getCuentaNombrada().getTipoCuenta().setIdTipoCuenta(0L);
		List<CuentaNombrada> cuentas = this.cuentaNombradaDao
				.findCuentasByNumeroCuenta(params.getCuentaNombrada(), 20);

		if (cuentas.size() == 1) {
			params.setCuentaNombrada(cuentas.get(0));
		}

		params.setFechaConsulta(new Date[] { fechaInicialConsulta,
				fechaFinalConsulta });

		if (divisa != null && !"-1".equals(divisa)) {
			params.setDivisa(divisa);
		} else {
			params.setDivisa(null);
		}

		if (importe != null) {
			params.setImporte(importe);
		} else {
			params.setImporte(null);
		}

		// params.setStatus(estado);
		if (operacion != null && !"-1".equals(operacion)) {
			params.setTipoTraspaso(operacion);
		} else {
			params.setTipoTraspaso(null);
		}
		if (tipoOperacion != null && !"-1".equals(tipoOperacion)) {
			params.setTipoOperacion(tipoOperacion);
		} else {
			params.setTipoOperacion(null);
		}
		
		if (origenOperacion != null && !"-1".equals(origenOperacion)) {
			params.setOrigenPfi(new BigInteger(origenOperacion));
		} else {
			params.setOrigenPfi(null);
		}
		// long id = NumberUtils.toLong(llaveFolio, -1);
		// params.setIdOperacionesSic(id==-1?null:new Long(id));
		
		
		/* se agrega validacion al folioControl, se verifica 
		 * que sea un valor numerico antes de convertirlo a BigInteger*/
		 
		params.setFolioControl(StringUtils.isNotBlank(folioControl) && 
				StringUtils.isNumeric(folioControl) ? new BigInteger(folioControl): null);
		if (cantidadOperada != null) {
			params.setCantidadTitulos(cantidadOperada);
		} else {
			params.setCantidadTitulos(null);

		}
		if (estado != null && !"-1".equals(estado)) {
			params.getEstatusOperacion().setIdEstatusOperacion(Long.valueOf(estado));
		} else {
			params.getEstatusOperacion().setIdEstatusOperacion(null);
		}
		

		/* Modificacion, se agrega condicion para setear referencia operacion*/
		params.setReferenciaOperacion(StringUtils.isNotBlank(referenciaOperacion) ? referenciaOperacion.toUpperCase() : null);
			
		params.setCatBic(null);
		if (!custodio.equalsIgnoreCase("-1")){
			CatBic cb = new CatBic();
			cb.setDetalleCustodio(custodio.trim());
			params.setCatBic(cb);
		}

		// Adicion del parametro Depositante/Liquidador de la pantalla Mayo 2017.
        if (this.depliq != null && !"-1".equals(this.depliq)) {
            params.setDepositanteLiquidador(this.depliq);
        }
        else {
            params.setDepositanteLiquidador(null);
        }

		/* Se incluye parametro cambio de boveda para las operaciones de cambio de boveda.
		 * Solamente debe ser 1 si el usuario que esta firmado es un usuario con el rol INT_CAMBIO_BOVEDA.*/
		params.setCambioBoveda(BigInteger.ZERO);
		if (this.usuarioContieneRolCambioBoveda()) {
		    params.setCambioBoveda(BigInteger.ONE);
		    this.esUsuarioCambioBoveda = true;
		}

	}

    /**
     * Verifica si en los roles del usuario se encuentra el de Cambio de Boveda.
     * @return true si contiene el rol, false en caso contrario.
     */
    private boolean usuarioContieneRolCambioBoveda() {
        final String ROL_CAMBIO_BOVEDA = "INT_CAMBIO_BOVEDA";
        return this.isUserInRoll(ROL_CAMBIO_BOVEDA);
    }
    
    public boolean isUsuarioSic() {
    	
    	boolean usuarioSic = isUserInRoll(Constantes.ROL_INT_SIC);
    	return usuarioSic;
   
    }

	/**
	 * Limpia los resultados de la consulta y los criterios.
	 * 
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		consultaEjecutada = false;
		paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(50);
		inicializarParams();

	}

	/**
	 * M&eacute;todo que se ejecuta para liberar las operaciones
	 * 
	 * @return String cadena de navegacion a seguir
	 */
	
	/*Se agrega modificacion cuando se selecciona habilitar para guardar en 
	 * divisionInternacionalService.grabaOperacion
	 * */
	/**
	 * se modifica para no poder habilitar con  este metodo
	 */

	public void actualizar() {
		log.debug("ConsultaOperacionesController :: actualizar");
		OperacionSic[] operaciones =
			(OperacionSic[]) paginaVO.getRegistros().toArray(new OperacionSic[] {});

		
		
		List<OperacionSic> listaOperaciones = new ArrayList<OperacionSic>();

		
		for (OperacionSic operacionSic : operaciones) {

		    operacionSic.setConMensaje(conMensaje);
		    if (operacionSic.getCambioBoveda() != null && operacionSic.getCambioBoveda().equals(BigInteger.ONE)) {
		        validaMovimientosPermitidosCambioBoveda(operacionSic, listaOperaciones);
		    }
		    else {
		        validaMovimientosPermitidos(operacionSic, listaOperaciones);
		    }
		}

		if(listaOperaciones.size() > 0) {
			OperacionSic[] soloOperacionesSinHabilitar = new OperacionSic[listaOperaciones.size()];
			soloOperacionesSinHabilitar = listaOperaciones.toArray(soloOperacionesSinHabilitar);		
			try {
				this.divisionInternacionalService.actualizaOperacionSIC(soloOperacionesSinHabilitar);
				agregarInfoMensaje("La actualizaci\u00F3n fue llevada a cabo exitosamente.");
			}
			catch (BusinessException e) {
			    log.error(e.getMessage(), e);
				agregarMensaje(e);
			}
			catch (Exception e) {
				log.error(e.getMessage(), e);
				agregarMensaje(e);
			}
		}
		obtenerOperaciones();
	}

	/**
	 * Metodo para liberar parcialidades
	 */
	public void actualizarParcialidades(final ActionEvent event) {
		log.debug("ConsultaOperacionesController :: actualizarParcialidades");
		List<LiquidacionParcialMoi> listaOperacionesLiberar = new ArrayList<LiquidacionParcialMoi>();
		List<LiquidacionParcialMoi> listaOperacionesCancelar = new ArrayList<LiquidacionParcialMoi>();
		Boolean isProcessLiberar = Boolean.TRUE;
		OperacionSic operacionSic = new OperacionSic();
		if(this.folioControlParcialidad != null){
			operacionSic = divisionInternacionalService.consultaOperacionSicById(new BigInteger(this.folioControlParcialidad));
		}
		for (LiquidacionParcialMoi parcialidad : this.detalleLiquidacionParcial.getLstLiquidacionParcial()) {
			if(parcialidad.isLibera()){
				listaOperacionesLiberar.add(parcialidad);
			} else {
				listaOperacionesCancelar.add(parcialidad);
			}
		}
		
		if(listaOperacionesLiberar.size() > 0){
			operacionSic.setLibero(true);
			isProcessLiberar = this.divisionInternacionalService.actualizaParcialidadOperacionSIC(operacionSic);	
		}

		int operacionesLiberadas = 0;
		if(isProcessLiberar){
			for (LiquidacionParcialMoi liquidacionParcialMoiLiberar : listaOperacionesLiberar) {
				/** LIBERO PARCIALIDADES **/
				if(liquidacionParcialMoiLiberar.isLibera()){
				    operacionSic.setConMensaje(conMensaje);
					sicService.eliminarRegistroControlLiberacionParcialesRecepciones(operacionSic.getIdOperacionesSic(), operacionSic.getFolioControl().longValue());
					divisionInternacionalService.actualizaLiquidacionesParciales(operacionSic.getFolioControl().longValue(), liquidacionParcialMoiLiberar.getNumeroLiquidacion(), Constantes.ST_OPER_PENDIENTE_LIBERAR_PRCIAL);
					operacionesLiberadas++;
				}
			}
		} else {
			agregarInfoMensaje("La operaci\u00F3n tiene un estatus distinto para su Liberaci\u00F3n Parcial (CONFIRMADA PARCIAL/LIBERADA PARCIAL)");
			return;
		}
		if(operacionesLiberadas > 0){
			agregarInfoMensaje("La solicitud se proceso exitosamente.");
		}
		getDetalleLiqParcial();
	}
	
	public void cancelaRemanente(final ActionEvent event) {
		log.debug("ConsultaOperacionesController :: cancelaRemanente");
		if(this.folioControlParcialidad != null){
			OperacionSic operacionSic = divisionInternacionalService.consultaOperacionSicById(new BigInteger(this.folioControlParcialidad));
			
			List<LiquidacionParcialMoi> lstLiquidacionParcialMoi = divisionInternacionalService.getLiqParcialMoi(operacionSic.getFolioControl().longValue());
			for (LiquidacionParcialMoi liquidacionParcialMoi : lstLiquidacionParcialMoi) {
				if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA_PARCIAL){
					addErrorMessage("Se necesitan liberar las parcialidades para poder cancelar el remanente.");
					return;
				}
			}
			
			this.sicService.cancelaParcialidadOperacionSIC(operacionSic, dateUtilService.getCurrentDate(), null);
			getDetalleLiqParcial();
			agregarInfoMensaje("La cancelaci\u00F3n del remanente fue llevada a cabo exitosamente.");
		} else {
			addErrorMessage("El Id de la operaci\u00F3n es null.");
		}
		
	}
	
	/**
	 * Metodo para validar las actualizaciones permitidas con y sin Mensaje
	 * @param operacionSic Operaciones a validar
	 * @param listaOperaciones Lista de operaciones que seran actualizadas
	 */
	private void validaMovimientosPermitidos(OperacionSic operacionSic, List<OperacionSic> listaOperaciones  ){
		log.debug("ConsultaOperacionesController :: validaMovimientosPermitidos");
		if(!operacionSic.isHabilito()&& !operacionSic.isCancelo() &&(operacionSic.isAutorizo() ||
				operacionSic.isConfirmo() || operacionSic.isLibero()) ){
			listaOperaciones.add(operacionSic);
		}
		else{ 
			if((operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_RETENIDA ||
				operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_HABILITADA) && 
				!operacionSic.isHabilito() && operacionSic.isCancelo() && !operacionSic.isConMensaje()){
				listaOperaciones.add(operacionSic);
			}
			else if(!operacionSic.isHabilito() && operacionSic.isCancelo() && 
					!(operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_RETENIDA ||
					operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_HABILITADA)){
				listaOperaciones.add(operacionSic);
			}
			else if(operacionSic.isRegreso() && operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA){
				listaOperaciones.add(operacionSic);
			}
			else if(operacionSic.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_RETENIDA 
					&& !operacionSic.isHabilito() && operacionSic.isCancelo() && operacionSic.isConMensaje()){
					listaOperaciones.add(operacionSic);
			}
		}
	}

    /**
     * Metodo para validar las actualizaciones permitidas con y sin Mensaje en operaciones de Cambio de Boveda.
     * @param operacionSic Operaciones a validar
     * @param listaOperaciones Lista de operaciones que seran actualizadas
     */
    private void validaMovimientosPermitidosCambioBoveda(OperacionSic operacionSic, List<OperacionSic> listaOperaciones  ) {
    	log.debug("ConsultaOperacionesController :: validaMovimientosPermitidosCambioBoveda");
        try {
            //Condicional que ejecuta el codigo interno si la operacion tiene seleccionado algun check.
            if (operacionSic.isRegresoPosicion() || operacionSic.isLibero() || operacionSic.isAutorizo() || 
                operacionSic.isCancelo() || operacionSic.isConfirmo() || operacionSic.isRegreso()) {

                if (operacionSic.isLibero() || operacionSic.isCancelo() || operacionSic.isRegresoPosicion()) {
                    if (operacionSic.isLibero()) {
                        if (this.sicService.esValidoEstadoAdecuadoDeContraoperacion(operacionSic)) {
                            listaOperaciones.add(operacionSic);
                        }
                        else {
                            agregarMensaje(new BusinessException("La contraparte de la operaci\u00F3n con Referencia: " + 
                                    operacionSic.getReferenciaOperacion() + " no se encuentra en un estado v\u00E1lido!"));
                        }
                    }
                    else {
                        //Cancelar o Regresar Posicion
                        listaOperaciones.add(operacionSic);
                    }

                }
                else {
                    if (operacionSic.isAutoriza() || operacionSic.isConfirma() || operacionSic.isRegresa()) {
                        agregarMensaje(new BusinessException("Cambio de Estado No permitido para la operaci\u00F3n con Referencia: " + 
                                operacionSic.getReferenciaOperacion()));
                    }
                }
            }
        } catch (BusinessException be) {
            be.printStackTrace();
            agregarMensaje(be);
        }
    }

	/*
	 * M&eacute;todo para habilitar OperacionSic
	 */
	public void actualizarHabilitar() {
		log.debug("ConsultaOperacionesController :: actualizarHabilitar");
			List<OperacionSic> actualizarHabilitar = new ArrayList<OperacionSic>();
			OperacionSic[] operaciones = (OperacionSic[]) paginaVO.getRegistros()
					.toArray(new OperacionSic[] {}); 
			for (OperacionSic operacionSic : operaciones) {
				if (operacionSic.isHabilito() && !operacionSic.isAutorizo() && !operacionSic.isCancelo() &&
						!operacionSic.isConfirmo() && !operacionSic.isLibero()){
//					actualizaOperacion(operacionSic);
					/**
					 * Valido que tenga posicion
					 */
					Boolean isValido = Boolean.TRUE;
					if(((operacionSic.getCambioBoveda() != null && operacionSic.getCambioBoveda().intValue() == 0 && operacionSic.getTipoMensaje() != null)
							|| operacionSic.getCambioBoveda() == null) 
							&& (operacionSic.getTipoMensaje().equals(Constantes.MT_542) || operacionSic.getTipoMensaje().equals(Constantes.MT_543))){
						AgenteVO agente = new AgenteVO();
						EmisionVO emision = new EmisionVO();
						PaginaVO paginaVOPosicion = new PaginaVO(); 
						
			            agente.setId(operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
			            agente.setFolio(operacionSic.getCuentaNombrada().getInstitucion().getFolioInstitucion());
			            agente.setCuenta(operacionSic.getCuentaNombrada().getCuenta());
			            emision.setTv(operacionSic.getEmision().getInstrumento().getClaveTipoValor());
			            emision.setEmisora(operacionSic.getEmision().getEmisora().getClavePizarra());
			            emision.setSerie(operacionSic.getEmision().getSerie());
			            emision.setIsin(operacionSic.getEmision().getIsin());
			            emision.setCupon(operacionSic.getClaveCupon());
			            emision.setIdBoveda(operacionSic.getBoveda().getIdBoveda());			            
						PaginaVO paginaVOPos = consultaCatalogoService.buscarListaPosicionesNombradasDeParticipante(agente, emision, paginaVOPosicion);
						if(paginaVOPos != null && paginaVOPos.getRegistros() != null && paginaVOPos.getRegistros().size() > 0){
							log.debug("ConsultaOperacionesController :: posicion encontrada.");
							List<PosicionNombrada> lstPosicionNombrada = (List<PosicionNombrada>) paginaVOPos.getRegistros();
							PosicionNombrada posicionNombrada = lstPosicionNombrada.get(0);
							for (PosicionNombrada posicionNombradaResult : lstPosicionNombrada) {
								if(operacionSic.getBoveda().getIdBoveda() != null 
										&& posicionNombradaResult.getBoveda() != null 
										&& posicionNombradaResult.getBoveda().getIdBoveda() != null 
										&& (operacionSic.getBoveda().getIdBoveda().longValue() == posicionNombradaResult.getBoveda().getIdBoveda().longValue())){
									posicionNombrada = posicionNombradaResult;
								}
							}

							if(posicionNombrada == null || posicionNombrada.getPosicionDisponible() == null ||  posicionNombrada.getPosicionDisponible().longValue() == 0l){
								isValido = Boolean.FALSE;
								addErrorMessage("La operaci\u00F3n con Folio Control: " + operacionSic.getFolioControl().longValue() + " no puede ser habilita por falta de posici\u00F3n.");
							} else if(posicionNombrada.getPosicionDisponible() != null && (operacionSic.getCantidadTitulos().longValue() > posicionNombrada.getPosicionDisponible().longValue())){
								isValido = Boolean.FALSE;
								addErrorMessage("La operaci\u00F3n con Folio Control: " + operacionSic.getFolioControl().longValue() + " no puede ser habilita por falta de posici\u00F3n.");
							}
						} else {
							log.info("ConsultaOperacionesController ::Posicion Nombradas No Encontrada. FolioControl: " + operacionSic.getFolioControl());
							isValido = Boolean.FALSE;
							addErrorMessage("La operaci\u00F3n con Folio Control: " + operacionSic.getFolioControl().longValue() + " no puede ser habilita por falta de posici\u00F3n.");
						}
					}
					if(isValido){
						actualizarHabilitar.add(operacionSic);
					}
				}
			}	
			if(actualizarHabilitar.size() > 0){
				OperacionSic[] soloOperacionesHabilitar = new OperacionSic[actualizarHabilitar.size()];
				soloOperacionesHabilitar = actualizarHabilitar.toArray(soloOperacionesHabilitar);
				try {
					divisionInternacionalService.actualizaOperacionSIC(soloOperacionesHabilitar);
					agregarInfoMensaje("La actualizacion fue llevada a cabo exitosamente");
				}catch (BusinessException e) {
					agregarMensaje(e);
				}catch (Exception e) {
					e.printStackTrace();
					agregarMensaje(e);
				}
			} else {
				agregarInfoMensaje("No hay operaciones para habilitar.");
			}
		obtenerOperaciones();
	}
	 
	/* * Indica si la columna de liberacion se mostrara
	 * 
	 * @return
	 */
	public boolean getRenderLiberacion() { 
		return usuario.getIsUserIndeval();
	}
	
	/**
	 * Metodo que retorna el idfolio del participante selecccionado en la consulta
	 * @return el idfolio del participante
	 */
	public String getParticipanteSeleccionado(){
		String descripcion = "";
		if (idfolio != null && !StringUtils.isBlank(idfolio)) {
			descripcion = idfolio;
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
		
	/**
	 * Metodo que retorna el tipo valor selecccionado en la consulta
	 * @return el tipo valor
	 */
	public String getTipoValorSeleccionado(){		
		String descripcion ="";
		if(idTipoValor != null && !StringUtils.isBlank(idTipoValor)){
			descripcion = idTipoValor.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la cuenta del participante selecccionada en la consulta
	 * @return la cuenta del participante
	 */
	public String getCuentaSeleccionada(){		
		String descripcion = "";		
		if (cuentaAgente != null && !StringUtils.isBlank(cuentaAgente)) {
			descripcion = cuentaAgente;
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		
		return descripcion;
	}
	/**
	 * Metodo que retorna la emisora selecccionado en la consulta
	 * @return la emisora seleccionada
	 */
	public String getEmisoraSeleccionada(){
		String descripcion ="";
		if(emisora != null && !StringUtils.isBlank(emisora)){
			descripcion = emisora.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la serie selecccionado en la consulta
	 * @return la serie seleccionada
	 */
	public String getSerieSeleccionada(){
		String descripcion ="";
		if(serie != null && !StringUtils.isBlank(serie)){
			descripcion = serie.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el cupon selecccionado en la consulta
	 * @return el cupon seleccionada
	 */
	public String getCuponSeleccionado(){
		String descripcion ="";
		if(cupon != null && !StringUtils.isBlank(cupon)){
			descripcion = cupon;				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion del estatus selecccionado en la consulta
	 * @return la descripcion del estatus de la operacion
	 */
	public String getEstatusSeleccionado(){
		String descripcion = "";		
		
		if ("-1".equals(estado)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}else {
			EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
			if (estados != null && estados.length > 0) {				
				int i = 0;				
				while (i <= estados.length -1  ) {					
					if(estados[i].getIdEstatusOperacion().equals(Long.valueOf(estado))){					
						descripcion = estados[i].getDescEstatusOperacion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getDivisaSeleccionada(){
		String descripcion="";
		
		if ("-1".equals(divisa)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else {
			Divisa[] divisas = divisaDao.findDivisas();
			if (divisas != null && divisas.length > 0) {				
				int i = 0;				
				while (i <= divisas.length -1  ) {					
					if(divisas[i].getClaveAlfabetica().equals(divisa)){					
						descripcion = divisas[i].getDescripcion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
		
	}
	/**
	 * Metodo que retorna la cantidad selecccionada en la consulta
	 * @return la cantidad operada seleccionado
	 */
	public String getCantidadSeleccionada(){
		String descripcion ="";
		if(cantidadOperada != null){
			descripcion = String.valueOf(cantidadOperada);				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
	return descripcion;
	}
	
	/**
	 * Metodo que retorna el folio control selecccionado en la consulta
	 * @return folio control seleccionada
	 */
	public String getFolioControlSeleccionado(){
		String descripcion ="";
		if(folioControl != null && !StringUtils.isBlank(folioControl)){
			descripcion = folioControl;				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
	return descripcion;
	}
	
	public String getReferenciaOperacionSeleccionado(){
		String descripcion = "";
		
		if(referenciaOperacion != null && !StringUtils.isBlank(referenciaOperacion)){
			descripcion = referenciaOperacion.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getOperacionSeleccionada(){
		String descripcion = "";
		
		if ("-1".equals(operacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else if (Constantes.TIPO_MOVTO_E.equals(operacion)) {
			descripcion = Constantes.MOVTO_ENTREGA;
		} else {
			descripcion = Constantes.MOVTO_RECIBE;
		}		
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getTipoOperacionSeleccionada(){
		String descripcion = "";
		
		if ("-1".equals(tipoOperacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else if (Constantes.TRASPASO_CONTRA.equals(tipoOperacion)) {
			descripcion = Constantes.DESC_TRASPASO_CONTRA;
		}else {
			descripcion = Constantes.DESC_TRASPASO_LIBRE;
		}
				
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el origen de la operacion seleccionado
	 * @return el origen de la operacion
	 */
	public String getOrigenSeleccionado(){
		String descripcion = "";
		
		if ("-1".equals(origenOperacion)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}else if (Constantes.CERO_STRING.equals(origenOperacion)) {
			descripcion = Constantes.OPERACION_INGRESADA_POR_PORTAL;
		}else {
			descripcion = Constantes.OPERACION_INGRESADA_POR_PFI;
		}
				
		return descripcion;
	}
	
	/**
	 * Metodo que retorna fecha inicial selecccionada en la consulta
	 * @return fecha inicial seleccionada
	 */
	public String getFechaInicialSeleccionada(){
		String descripcion = "";
		if (fechaInicialConsulta != null){
			descripcion = DateUtils.format(fechaInicialConsulta, "dd/MM/yyyy");
		}	
		return descripcion;
	}
	/**
	 * Metodo que retorna fecha final selecccionada en la consulta
	 * @return fecha final seleccionada
	 */
	public String getFechaFinalSeleccionada(){
		String descripcion = "";
		if (fechaFinalConsulta != null){
			descripcion = DateUtils.format(fechaFinalConsulta, "dd/MM/yyyy");
		}		
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el importe selecccionado en la consulta
	 * @return el importe seleccionado
	 */
	public String getImporteSeleccionado(){
		String descripcion = "";
		if (importe != null){
			DecimalFormat df = new DecimalFormat(DaliConstants.FORMATO_DECIMAL);
			descripcion = df.format(Double.parseDouble(importe.toString()));
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}	
		return descripcion;
	}		
    
    /**
     * Metodo que retorna el depositante/liquidador selecccionado en la consulta
     * @return depositante/liquidador seleccionada
     */
    public String getDepositanteLiquidadorSeleccionado() {
        String descripcion = "";
        if ("-1".equals(this.depliq)) {
            descripcion = Constantes.OPCION_TODOS_CRITERIO;
        }
        else {
            descripcion = this.depliq;
        }
        return descripcion;
    }


	/**
	 * @return the idTipoValor
	 */
	public String getIdTipoValor() {
		return idTipoValor;
	}

	/**
	 * @param idTipoValor
	 *            the idTipoValor to set
	 */
	public void setIdTipoValor(String idTipoValor) {
		this.idTipoValor = idTipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the fechaConsulta
	 */
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * @param fechaConsulta
	 *            the fechaConsulta to set
	 */
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion
	 *            the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the llaveFolio
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param llaveFolio
	 *            the llaveFolio to set
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the data
	 */
	public SelectItem[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(SelectItem[] data) {
		this.data = data;
	}

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService
	 *            the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioVO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the numResultados
	 */
	public String getNumResultados() {
		return numResultados;
	}

	/**
	 * @param numResultados
	 *            the numResultados to set
	 */
	public void setNumResultados(String numResultados) {
		this.numResultados = numResultados;
	}

	/**
	 * @return the renderResult
	 */
	public Boolean getRenderResult() {
		return renderResult;
	}

	/**
	 * @param renderResult
	 *            the renderResult to set
	 */
	public void setRenderResult(Boolean renderResult) {
		this.renderResult = renderResult;
	}

	/**
	 * @return the idMsg
	 */
	public String getIdMsg() {
		return idMsg;
	}

	/**
	 * @param idMsg
	 *            the idMsg to set
	 */
	public void setIdMsg(String idMsg) {
		this.idMsg = idMsg;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the renderAutorizacion
	 */
	public Boolean getRenderAutorizacion() {
		return renderAutorizacion;
	}

	/**
	 * @param renderAutorizacion
	 *            the renderAutorizacion to set
	 */
	public void setRenderAutorizacion(Boolean renderAutorizacion) {
		this.renderAutorizacion = renderAutorizacion;
	}

	/**
	 * @return the conMensaje
	 */
	public boolean isConMensaje() {
		return conMensaje;
	}

	/**
	 * @param conMensaje
	 *            the conMensaje to set
	 */
	public void setConMensaje(boolean conMensaje) {
		this.conMensaje = conMensaje;
	}

	/**
	 * @return the conMensajeParcial
	 */
	public boolean isConMensajeParcial() {
		return conMensajeParcial;
	}

	/**
	 * @param conMensajeParcial the conMensajeParcial to set
	 */
	public void setConMensajeParcial(boolean conMensajeParcial) {
		this.conMensajeParcial = conMensajeParcial;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the idAgente
	 */
	public String getIdAgente() {
		return idAgente;
	}

	/**
	 * @param idAgente
	 *            the idAgente to set
	 */
	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}

	/**
	 * @return the folioAgente
	 */
	public String getFolioAgente() {
		return folioAgente;
	}

	/**
	 * @param folioAgente
	 *            the folioAgente to set
	 */
	public void setFolioAgente(String folioAgente) {
		this.folioAgente = folioAgente;
	}

	/**
	 * @return the cuentaAgente
	 */
	public String getCuentaAgente() {
		return cuentaAgente;
	}

	/**
	 * @param cuentaAgente
	 *            the cuentaAgente to set
	 */
	public void setCuentaAgente(String cuentaAgente) {
		this.cuentaAgente = cuentaAgente;
	}

	/**
	 * @return the readOnlyAgente
	 */
	public boolean isReadOnlyAgente() {
		return readOnlyAgente;
	}

	/**
	 * @param readOnlyAgente
	 *            the readOnlyAgente to set
	 */
	public void setReadOnlyAgente(boolean readOnlyAgente) {
		this.readOnlyAgente = readOnlyAgente;
	}

	/**
	 * @return the styleAgente
	 */
	public String getStyleAgente() {
		return styleAgente;
	}

	/**
	 * @param styleAgente
	 *            the styleAgente to set
	 */
	public void setStyleAgente(String styleAgente) {
		this.styleAgente = styleAgente;
	}

	/**
	 * @return the fechaFinalConsulta
	 */
	public Date getFechaFinalConsulta() {
		return fechaFinalConsulta;
	}

	/**
	 * @param fechaFinalConsulta
	 *            the fechaFinalConsulta to set
	 */
	public void setFechaFinalConsulta(Date fechaFinalConsulta) {
		this.fechaFinalConsulta = fechaFinalConsulta;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the cantidadOperada
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 *            the cantidadOperada to set
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public String getIdfolio() {
		return idfolio;
	}

	public OperacionSic getParams() {
		return params;
	}

	public void setParams(OperacionSic params) {
		this.params = params;
	}

	public Date getFechaInicialConsulta() {
		return fechaInicialConsulta;
	}

	public void setFechaInicialConsulta(Date fechaInicialConsulta) {
		this.fechaInicialConsulta = fechaInicialConsulta;
	}

	public void setIdfolio(String idfolio) {
		this.idfolio = idfolio;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada
	 *            the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the cuentaNombradaDao
	 */
	public CuentaNombradaDao getCuentaNombradaDao() {
		return cuentaNombradaDao;
	}

	/**
	 * @param cuentaNombradaDao
	 *            the cuentaNombradaDao to set
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}

	/**
	 * @return the divisaDao
	 */
	public DivisaDao getDivisaDao() {
		return divisaDao;
	}

	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}
	
	public OperacionSic getDetalleOperacionSic() {
		return detalleOperacionSic;
	}

	public void setDetalleOperacionSic(OperacionSic detalleOperacionSic) {
		this.detalleOperacionSic = detalleOperacionSic;
	}
	
	public String getDetalle(){
		detalleOperacionSic = new OperacionSic();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String idOperacionesSic = facesContext.getExternalContext().getRequestParameterMap().get("idOperacionesSic");
		if(StringUtils.isNotBlank(idOperacionesSic)){
			Long id = Long.valueOf(idOperacionesSic);
			detalleOperacionSic = divisionInternacionalService.consultaOperacionSicById(BigInteger.valueOf(id));
			/* Se cambian a mayusculas los datos.*/
			detalleOperacionSic.setCuentaContraparte(detalleOperacionSic.getCuentaContraparte().toUpperCase());
			detalleOperacionSic.setDescContraparte(detalleOperacionSic.getDescContraparte().toUpperCase());
			detalleOperacionSic.getSicDetalle().setBicDepLiq(detalleOperacionSic.getSicDetalle().getBicDepLiq().toUpperCase());
			detalleOperacionSic.setNomCtaBenef(detalleOperacionSic.getNomCtaBenef().toUpperCase());
			detalleOperacionSic.setNumCtaBenef(detalleOperacionSic.getNumCtaBenef().toUpperCase());
			
			if(!(detalleOperacionSic.getInstruccEspeciales()== null)){
				detalleOperacionSic.setInstruccEspeciales(detalleOperacionSic.getInstruccEspeciales().toUpperCase());	
			}
		}					
		return 	null;
	}
	
	public String getDetalleLiqParcial(){
		log.debug("ConsultaOperacionesController :: getDetalleLiqParcial");
		getPaginaVO().setOffset(0);
		getPaginaVO().setRegistrosXPag(20);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if(StringUtils.isBlank(this.folioControlParcialidad)){
			this.folioControlParcialidad = facesContext.getExternalContext().getRequestParameterMap().get("folioControlParcialidad");			
		}
		this.detalleLiquidacionParcial = new DetalleLiquidacionParcialVO();
		if(StringUtils.isNotBlank(this.folioControlParcialidad)){
			Long folioControl = Long.valueOf(this.folioControlParcialidad.replace(" ", ""));
			OperacionSic operacionSic = divisionInternacionalService.consultaOperacionSicById(BigInteger.valueOf(folioControl));
			if(operacionSic != null){
				this.detalleLiquidacionParcial.setTipoOperacionSic(operacionSic.getTipoMensaje());
				this.detalleLiquidacionParcial.setIdEstatusOperacion(operacionSic.getEstatusOperacion().getIdEstatusOperacion());
				this.detalleLiquidacionParcial.setRemanenteOperacion(operacionSic.getRemanente());
				this.detalleLiquidacionParcial.setRemanenteEfectivoOperacion(operacionSic.getRemanenteEfectivo());
			}
			
			PaginaVO pagina = divisionInternacionalService.getLiqParcialMoi(getPaginaVO(), folioControl);
			setPaginaVO(pagina);
			this.detalleLiquidacionParcial.setLstLiquidacionParcial(pagina.getRegistros());
			setConsultaEjecutada(true);
		}
		return 	null;
	}

	

	/* metodo que recibe operacionSic, transforma en GrabaOperacionParams y guarda en grabaOperacion 
	 * bitacora de operaciones  creado para actualizar la consulta de operaciones sic, cuando seleccionan la
	 * opcion habilitar pasa el objeto operacionSic seleccionado*/
	
	/** 
	/* Este metodo parace ser que no se utiliza, se se localizo ninguna invocacion
	 * @autor: Alejandro Rodriguez
	 * @param operacionSic
	 */
	
	public void actualizaOperacion(OperacionSic operacionSic){
		/*se modifica para que tome el idFolioTranspasante de la operacion resultante de la busqueda*/
		//String idFolioTraspasante= AgenteViewHelper.obtenerClaveTipoInstitucionYFolio(getAgenteFirmado().getId(),getAgenteFirmado().getFolio());
		String idFolioTraspasante = operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()+
		operacionSic.getCuentaNombrada().getInstitucion().getFolioInstitucion() ;
				
		GrabaOperacionParams params = new GrabaOperacionParams();
		
		//OperacionSic[] operaciones = {operacionSic};
		
		TraspasoLibrePagoVO tlpvo;
		BigInteger referenciaMensaje = utilService.getFolio(SEQ_REFERENCIA_MENSAJE);
		Cupon cuponTmp = cuponDao.findCuponByIdEmision(operacionSic.getEmision().getIdEmision());
				
			if (operacionSic != null) {
				params = new GrabaOperacionParams();
				tlpvo = new TraspasoLibrePagoVO();
				tlpvo.setReferenciaOperacion(operacionSic.getFolioControl().toString());
				tlpvo.setReferenciaMensaje(referenciaMensaje.toString());
				tlpvo.setCliente("");
		
				tlpvo.setIdFolioCtaReceptora(idFolioTraspasante.trim()+ operacionSic.getCuentaRecep().trim());
				
				tlpvo.setCupon(cuponTmp.getClaveCupon());
				
				tlpvo.setIdFolioCtaTraspasante("");
				tlpvo.setIdFolioCtaTraspasante(idFolioTraspasante.trim()+ operacionSic.getCuentaNombrada().getCuenta());
				tlpvo.setCantidadTitulos(operacionSic.getCantidadTitulos().longValue());
				tlpvo.setEmisora(operacionSic.getEmision().getEmisora().getClavePizarra());
				
				tlpvo.setFechaLiquidacion(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()) );
				
//				/*
//				 * se valida que la fecha de liquidacion no se anterior a la actual, si es anterior a la actula se le
//				 * setea la fecha actual
//				 */
//				if( dateUtilService.getFechaHoraCero(operacionSic.getFechaLiquidacion()).compareTo(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate())) < 0){
//					tlpvo.setFechaLiquidacion( dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()) );
//				}
//				else{
//					tlpvo.setFechaLiquidacion( dateUtilService.getFechaHoraCero(operacionSic.getFechaLiquidacion()) );
//				}
				tlpvo.setISIN(operacionSic.getEmision().getIsin());
				tlpvo.setTipoValor(cuponTmp.getEmision().getInstrumento().getClaveTipoValor());
				tlpvo.setSerie(operacionSic.getEmision().getSerie());
				tlpvo.setTipoInstruccion("T");
				tlpvo.setFechaRegistro(dateUtilService.getCurrentDate());
				
				params.setTraspasoLibrePagoVO(tlpvo);
				params.setOrigenRegistro(Constantes.ID_ORIGEN_DIV_INTERNACIONAL);
				params.setDatosAdicionales(creaMapaDatosAdicionales(operacionSic));
				divisionInternacionalService.grabaOperacion(params);
				
			}
	}
	
	/**
	 * @return the utilService
	 */
	public UtilService getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}
	/**
	 * Obtiene el valor del atributo cuponDao
	 *
	 * @return el valor del atributo cuponDao
	 */
	public CuponDao getCuponDao() {
		return cuponDao;
	}

	/**
	 * Establece el valor del atributo cuponDao
	 *
	 * @param cuponDao el valor del atributo cuponDao a establecer
	 */
	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}
	
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param emisionVO
	 *            the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}
	
	
	
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}
		
	public String getOrigenOperacion() {
		return origenOperacion;
	}

	public void setOrigenOperacion(String origenOperacion) {
		this.origenOperacion = origenOperacion;
	}
	
	
	private HashMap<String, Object> creaMapaDatosAdicionales(OperacionSic operacionSic) {
		HashMap<String, Object> datosAdicionales = new HashMap<String, Object>();
		datosAdicionales.put(Constantes.CUENTA_CONTRAPARTE_DA, operacionSic.getCuentaContraparte());
		datosAdicionales.put(Constantes.DESC_CTA_CONTRAPARTE_DA, operacionSic.getDescContraparte());
		datosAdicionales.put(Constantes.FECHA_OP_DA, operacionSic.getFechaOperacion());
		datosAdicionales.put(Constantes.FECHA_LIQ_DA, operacionSic.getFechaLiquidacion());
		datosAdicionales.put(Constantes.FECHA_NOT_DA, operacionSic.getFechaNotificacion());
		datosAdicionales.put(Constantes.NUM_CUNETA_BENEF_DA, operacionSic.getNumCtaBenef());
		datosAdicionales.put(Constantes.NOM_CUENTA_BENEF_DA, operacionSic.getNomCtaBenef());
		datosAdicionales.put(Constantes.CUSTODIO_DA, operacionSic.getCatBic().getIdCatbic());
		datosAdicionales.put(Constantes.DEPOSITANTE_DA, operacionSic.getSicDetalle().getIdSicDetalle());
		datosAdicionales.put(Constantes.INSTRUCCIONES_ESP_DA, operacionSic.getInstruccEspeciales());
		datosAdicionales.put(Constantes.TIPO_MENSAJE_DA, operacionSic.getTipoMensaje());
		datosAdicionales.put(Constantes.ESTATUS_DA, operacionSic.getEstatusOperacion().getIdEstatusOperacion());
		datosAdicionales.put(Constantes.PRECIO_DA, operacionSic.getImporte() != null ? operacionSic.getImporte() : new BigDecimal(0));
		datosAdicionales.put(Constantes.DIVISA_DA, operacionSic.getDivisa());
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
			(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SeguridadConstants.TICKET_SESION));
		return datosAdicionales;
	}

	/**
	 * Obtiene la lista de depositantes/liquidadores para el combo correspondiente.
	 */
	private void obtenerListaDepositantesLiquidadores() {
	    this.listaDepLiq = new ArrayList<SelectItem>();
	    List<String> depsliqs = this.consultaOperacionesSICService.getDepositantesLiquidadores();
        for (String dl : depsliqs) {
            this.listaDepLiq.add(new SelectItem(dl, dl));
        }
	}
	
	public void generaReportes(ActionEvent event) {
		log.debug("ConsultaOperacionesController :: generaReportes");
		if(StringUtils.isNotBlank(this.folioControlParcialidad)){
			Long folioControl = Long.valueOf(this.folioControlParcialidad);
			this.detalleLiquidacionParcial.setLstLiquidacionParcial(divisionInternacionalService.getLiqParcialMoi(folioControl));
		}

	}

	public List<SelectItem> getListaDepLiq() {
	    return this.listaDepLiq;
	}

	public String getIsin() {		
		return StringUtils.upperCase(isin);
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}
	
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}
	
	private void inicializaCustodios() {
		listaCustodios = new ArrayList<SelectItem>();
		List<String> custodios = divisionInternacionalService.listaCustodios();
		if (custodios != null && !custodios.isEmpty()) {
			for (String c : custodios) {
				if (!(c == null)) {
					listaCustodios.add(new SelectItem(c, c));
				}
			}
		}
	}

    public void setConsultaOperacionesSICService(ConsultaOperacionesSICService consultaOperacionesSICService) {
        this.consultaOperacionesSICService = consultaOperacionesSICService;
    }

    public String getDepliq() {
        return depliq;
    }

    public void setDepliq(String depliq) {
        this.depliq = depliq;
    }

    public void setSicService(SicService sicService) {
        this.sicService = sicService;
    }

    public boolean isEsUsuarioCambioBoveda() {
        return esUsuarioCambioBoveda;
    }

	/**
	 * @return the detalleLiquidacionParcial
	 */
	public DetalleLiquidacionParcialVO getDetalleLiquidacionParcial() {
		return detalleLiquidacionParcial;
	}

	/**
	 * @param detalleLiquidacionParcial the detalleLiquidacionParcial to set
	 */
	public void setDetalleLiquidacionParcial(DetalleLiquidacionParcialVO detalleLiquidacionParcial) {
		this.detalleLiquidacionParcial = detalleLiquidacionParcial;
	}

	/**
	 * @param consultaCatalogoService the consultaCatalogoService to set
	 */
	public void setConsultaCatalogoService(ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}

}
