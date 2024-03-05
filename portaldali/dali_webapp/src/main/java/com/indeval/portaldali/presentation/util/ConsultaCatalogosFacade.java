/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCatalogosFacade.java
 * 29/02/2008
 */
package com.indeval.portaldali.presentation.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaBovedaService;
import com.indeval.portaldali.middleware.services.common.ConsultaCuentaService;
import com.indeval.portaldali.middleware.services.common.ConsultaDivisaService;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisionService;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisoraService;
import com.indeval.portaldali.middleware.services.common.ConsultaErroresLiquidacionService;
import com.indeval.portaldali.middleware.services.common.ConsultaEstadoInstruccionService;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.middleware.services.common.ConsultaMercadoService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoInstitucionService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoInstruccionService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoLiquidacionService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoMensajeService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaSaldosEfectivoService;

/**
 * Clase que permite el acceso a la consulta de los catálogos utilizados en los
 * criterios y permite la construcción de los elementos que se mostrarán en los
 * combo - box
 *
 * @author Emigdio HernándezconsultaTipoInstitucionService
 *
 */
public class ConsultaCatalogosFacade {

	private final Logger logger = LoggerFactory.getLogger(ConsultaCatalogosFacade.class);
	/**
	 * Servicio para el acceso a la consula de mercados
	 */
	private ConsultaMercadoService consultaMercadoService = null;

	/** Servicio para la consulta de emisiones */
	private ConsultaEmisionService consultaEmisionService = null;
	/**
	 * Servicio de acceso al catálogo de estados de instrucción
	 */
	private ConsultaEstadoInstruccionService consultaEstadoInstruccionService = null;
	/**
	 * Sericio de acceso al catálogo de estados de instrucción
	 */
	private ConsultaBovedaService consultaBovedaService = null;
	/**
	 * Servicio para el acceso al catálogo de tipos de instruccion
	 */
	private ConsultaTipoInstruccionService consultaTipoInstruccionService = null;

	private ConsultaTipoLiquidacionService consultaTipoLiquidacionService = null;
	/**
	 * Servicio para el acceso a la consulta de cuentas
	 */
	private ConsultaCuentaService consultaCuentaService = null;
	/**
	 * Servicio para el acceso a la consulta de instrumentos según el tipo de
	 * mercado
	 */
	private ConsultaTipoValorService consultaTipoValorService = null;
	/**
	 * Servicio para el acceso a la consulta de emisoras
	 */
	private ConsultaEmisoraService consultaEmisoraService = null;
	/**
	 * Servicio para el acceso al catálogo de tipos de mensaje
	 */
	private ConsultaTipoMensajeService consultaTipoMensajeService = null;
	/**
	 * Consulta de divisas
	 */
	private ConsultaDivisaService consultaDivisaService = null;

	/**
	 * Acceso a la consulta de instituciones
	 */
	private ConsultaInstitucionService consultaInstitucionService = null;
	/**
	 * Servicio para la consulta del saldo
	 */
	private ConsultaSaldosEfectivoService consultaSaldoEfectivoService = null;

	/** Servicio para la consulta de los errores de liquidación del DALI */
	private ConsultaErroresLiquidacionService consultaErroresLiquidacionService = null;

	/** Servicio para la consulta del tipo de instituciones */
	private ConsultaTipoInstitucionService consultaTipoInstitucionService = null;

	/**
	 * Contiene las claves alfabticas de las divisas que deben ir en primer
	 * lugar en el combo de divisas
	 */
	private List<String> divisasPrincipales = null;

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de mercado
	 * preparados en elementos del tipo {@link SelectItem} para desplegar las
	 * opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsMercado() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		mercadosTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(mercadosTodos, mercadosTodos.getDescripcion()));

		for (MercadoDTO nat : consultaMercadoService.buscarMercados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getClaveMercado()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene los elementos para el combo que contiene los mercados
	 *
	 * @return los elementos para el combo que contiene los mercados
	 */
	public List<SelectItem> getSelectItemsPapelMercado() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		opcionesCombo.add(new SelectItem(DaliConstants.PAPEL_MERCADO_TODOS, "TODOS"));
		opcionesCombo.add(new SelectItem(DaliConstants.PAPEL_MERCADO_DINERO, "MD"));
		opcionesCombo.add(new SelectItem(DaliConstants.PAPEL_MERCADO_CAPITALES, "MC"));

		return opcionesCombo;
	}

	/**
	 * Obtiene una lista con un SelectItem para representar todos los mercados.
	 *
	 * @return una lista con un SelectItem para representar todos los mercados.
	 */
	public List<SelectItem> getSelectItemMercadoTodos() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		mercadosTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(mercadosTodos, mercadosTodos.getDescripcion()));

		return opcionesCombo;
	}

	/**
	 * Obtiene los mercados agrupados por el mercado de dinero.
	 *
	 * @return los mercados agrupados por el mercado de dinero.
	 */
	public List<SelectItem> getSelectItemsDeMercadoDinero() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setId(DaliConstants.ID_MERCADO_DINERO);
		mercadosTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(mercadosTodos, mercadosTodos.getDescripcion()));

		MercadoDTO mercadoPapelGuber = new MercadoDTO();
		mercadoPapelGuber.setId(DaliConstants.ID_MERCADO_PAPEL_GUBER);
		mercadoPapelGuber.setDescripcion("PG");

		opcionesCombo.add(new SelectItem(mercadoPapelGuber, mercadoPapelGuber.getDescripcion()));

		MercadoDTO mercadoPapelBancario = new MercadoDTO();
		mercadoPapelBancario.setId(DaliConstants.ID_MERCADO_PAPEL_BANCARIO);
		mercadoPapelBancario.setDescripcion("PB");

		opcionesCombo.add(new SelectItem(mercadoPapelBancario, mercadoPapelBancario.getDescripcion()));

		return opcionesCombo;
	}

	/**
	 * Obtiene los mercados agrupados por el mercado de capitales
	 *
	 * @return los mercados agrupados por el mercado de capitales
	 */
	public List<SelectItem> getSelectItemsDeMercadoCapitales() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setId(DaliConstants.ID_MERCADO_CAPITALES);
		mercadosTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(mercadosTodos, mercadosTodos.getDescripcion()));

		return opcionesCombo;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de errores de
	 * liquidación del DALI preparados en elementos de tipo {@link SelectItem}
	 * para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar.
	 */
	public List<SelectItem> getSelectItemsErrorDali() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		opcionesCombo.add(new SelectItem("", "TODOS"));

		for (ErrorDaliDTO error : consultaErroresLiquidacionService.buscarErroresLiquidacion()) {
			opcionesCombo.add(new SelectItem(error.getClaveError(), error.getClaveError(), error.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Busca un error en el catálogo de errores por medio de su identificador.
	 *
	 * @param idError
	 *            el identificador del error a consultar.
	 * @return el objeto {@link ErrorDaliDTO} que corresponde al identificador
	 *         proporcionado.
	 */
	public ErrorDaliDTO buscarErrorLiquidacionPorClaveError(String claveError) {
		return consultaErroresLiquidacionService.buscarErrorLiquidacionPorClaveError(claveError);
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de estado de
	 * instruccions preparados en elementos del tipo {@link SelectItem} para
	 * desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsEstadoInstruccion() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		EstadoInstruccionDTO estado = new EstadoInstruccionDTO();
		estado.setIdEstadoInstruccion(DaliConstants.VALOR_COMBO_TODOS);
		estado.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(estado, estado.getDescripcion()));

		for (EstadoInstruccionDTO dto : consultaEstadoInstruccionService.consultarTodosLosEstadosInstruccion()) {
			opcionesCombo.add(new SelectItem(dto, dto.getClaveEstadoInstruccion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de estado de
	 * instruccions preparados en elementos del tipo {@link SelectItem} para
	 * desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsTiposMensaje() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoMensajeDTO dtoTodos = new TipoMensajeDTO();
		dtoTodos.setIdTipoMensaje(DaliConstants.VALOR_COMBO_TODOS);
		dtoTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(dtoTodos, dtoTodos.getDescripcion()));

		for (TipoMensajeDTO dto : consultaTipoMensajeService.consultarTodosLosTiposMensaje()) {
			if (dto.getClaveTipoMensaje().equalsIgnoreCase("540") || dto.getClaveTipoMensaje().equalsIgnoreCase("541") ||
					dto.getClaveTipoMensaje().equalsIgnoreCase("542") || dto.getClaveTipoMensaje().equalsIgnoreCase("543")){
				opcionesCombo.add(new SelectItem(dto, dto.getClaveTipoMensaje() + " - " + dto.getDescripcion()));
			}
		}

		return opcionesCombo;
	}
	
	public List<SelectItem> getSelectItemsTiposInstruccion() {
		List<SelectItem> selectItems = new ArrayList<>();
		selectItems.add(new SelectItem(0L, "TODOS"));
		
		for(TipoInstruccionDTO tipoInstruccion : this.consultaTipoInstruccionService.buscarTiposDeInstruccion()) {
			selectItems.add(new SelectItem(tipoInstruccion.getIdTipoInstruccion(), tipoInstruccion.getNombreCorto()));
		}
		
		return selectItems;
	}
	
	public List<SelectItem> getSelectItemsTiposOperacion() {
		List<SelectItem> selectItems = new ArrayList<>();
		selectItems.add(new SelectItem(0L, "TODOS"));
		
		return selectItems;
	}

	/**
	 * Consulta el catálogo de mercados en busca de un mercado en específico, en
	 * caso de no econtrarlo retorna un objeto Mercado DTO con la clave -1 y la
	 * descripción TODOS
	 *
	 * @param id
	 * @return
	 */
	public MercadoDTO buscarMercadoPorId(long id) {
		MercadoDTO dto = null;

		dto = consultaMercadoService.buscarMercadoPorId(id);

		if (dto == null) {
			dto = new MercadoDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveMercado("TODOS");
			dto.setDescripcion("");
		}
		return dto;

	}

	/**
	 * Consulta el catálogo de estados de instruccion en busca de un estado en
	 * específico, en caso de no econtrarlo retorna un objeto
	 * EstadoInstruccionDTO con la clave -1 y la descripción TODOS
	 *
	 * @param id
	 * @return
	 */
	public EstadoInstruccionDTO buscarEstadoInstruccionPorId(long id) {
		EstadoInstruccionDTO dto = null;

		dto = consultaEstadoInstruccionService.consultarEstadoInstruccionPorId(id);

		if (dto == null) {
			dto = new EstadoInstruccionDTO();
			dto.setIdEstadoInstruccion(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveEstadoInstruccion("TODOS");
			dto.setDescripcion("TODOS");
		}
		return dto;

	}

	public EstadoInstruccionDTO buscarEstadoInstruccionPorClave(String claveEstadoInstruccion){
		EstadoInstruccionDTO dto = null;

		dto = consultaEstadoInstruccionService.consultarEstadoInstruccionPorClave(claveEstadoInstruccion);

		if (dto == null){
			dto = new EstadoInstruccionDTO();
			dto.setIdEstadoInstruccion(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveEstadoInstruccion("TODOS");
			dto.setDescripcion("TODOS");
		}

		return dto;
	}

	/**
	 * Consulta el catálogo de tipos de valor en busca de un tipo de valor en
	 * específico, en caso de no econtrarlo retorna un objeto TipoValorDTO con
	 * la clave -1 y la descripción TODOS
	 *
	 * @param id
	 * @return
	 */
	public TipoValorDTO buscarTipoValorPorClave(String clave) {
		TipoValorDTO dto = null;

		dto = consultaTipoValorService.buscarTipoDeValorPorClave(clave != null ? clave.toUpperCase() : clave);

		if (dto == null) {
			dto = new TipoValorDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveTipoValor(clave);
			if (StringUtils.isEmpty(clave)) {
				dto.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			} else {
				dto.setDescripcion(clave != null ? clave.toUpperCase() : clave);
			}

		}
		return dto;

	}

	/**
	 * Consulta el catálogo de tipos de instrucción en busca de un tipo de
	 * instrucción en específico, en caso de no econtrarlo retorna un objeto
	 * TipoInstruccionDTO con la clave -1 y la descripción TODOS
	 *
	 * @param id
	 * @return
	 */
	public TipoInstruccionDTO buscarTipoInstruccionPorClave(String clave) {
		TipoInstruccionDTO dto = null;

		dto = consultaTipoInstruccionService.buscarTipoDeInstruccionPorClave(clave != null ? clave.toUpperCase() : clave);

		if (dto == null) {
			dto = new TipoInstruccionDTO();
			dto.setIdTipoInstruccion((long) DaliConstants.VALOR_COMBO_TODOS);
			dto.setNombreCorto(clave);
			if (StringUtils.isEmpty(clave)) {
				dto.setDescripcion("TODOS");
			} else {
				dto.setDescripcion(clave);
			}

		}
		return dto;

	}
	/**
	 * Consulta el catálogo de tipos de instrucción en busca de un tipo de
	 * instrucción en específico, en caso de no econtrarlo retorna un objeto
	 * TipoInstruccionDTO con la clave -1 y la descripción TODOS
	 *
	 * @param id
	 * @return
	 */
	public TipoLiquidacionDTO buscarTipoLiquidacionPorClave(String clave) {
		TipoLiquidacionDTO dto = null;


		dto = consultaTipoLiquidacionService.buscarTipoDeLiquidacionPorClave(clave != null ? clave.toUpperCase() : clave);

		if (dto == null) {
			dto = new TipoLiquidacionDTO();
			dto.setIdTipoLiq( BigInteger.valueOf((long) DaliConstants.VALOR_COMBO_TODOS));
			dto.setNombreCorto(clave);
			if (StringUtils.isEmpty(clave)) {
				dto.setDescripcion("TODOS");
			} else {
				dto.setDescripcion(clave);
			}

		}
		return dto;

	}





	/**
	 * Consulta el catálogo de tipos de mensaje en busca de un tipo de mensaje
	 * en específico, en caso de no econtrarlo retorna un objeto TipoMensajeDTO
	 * con la clave -1 y la descripción TODOS
	 *
	 * @param id
	 *            Identificador del tipo de mensaje
	 * @return
	 */
	public TipoMensajeDTO buscarTipoMensajePorId(int id) {
		TipoMensajeDTO dto = null;

		dto = consultaTipoMensajeService.consultaTipoMensajePorId(id);

		if (dto == null) {
			dto = new TipoMensajeDTO();
			dto.setIdTipoMensaje(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveTipoMensaje("");
			dto.setDescripcion("TODOS");

		}
		return dto;

	}

	/**
	 * Consulta el catálogo de emisoras por una emisora en específico basado en
	 * el nombre corto de la emisora. Si no se localiza una emisora se retorna
	 * un DTO con id -1 y la descripción TODOS
	 *
	 * @param nombre
	 *            Nombre de la emisora
	 * @return EmisoraDTO localizada
	 */
	public EmisoraDTO buscarEmisoraPorNombreCorto(String nombre) {
		EmisoraDTO dto = null;

		dto = consultaEmisoraService.buscarEmisoraPorDescripcion(nombre != null ? nombre.toUpperCase() : nombre);

		if (dto == null) {
			dto = new EmisoraDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion(nombre != null ? nombre.toUpperCase() : nombre);

		}
		return dto;

	}

	/**
	 * Consulta el catálogo de cuentas nombradas de valor por una cuenta en
	 * específico basado en el numero de cuenta completo Si no se localiza una
	 * cuenta se retorna un DTO con id -1 y la descripción TODOS
	 *
	 * @param cuenta
	 *            número de cuenta completo
	 * @return CuentaDTO la cuenta localizada
	 */
	public CuentaDTO buscarCuentaPorNumeroCuenta(String cuenta) {
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(DaliConstants.VALOR_COMBO_TODOS);
		CuentaDTO criterio = new CuentaDTO();
		criterio.setInstitucion(institucion);
		criterio.setTipoTenencia(new TipoTenenciaDTO());
		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.setNumeroCuenta(cuenta);

		CuentaDTO dto = consultaCuentaService.buscarCuentaPorNumeroCuenta(criterio);

		if (dto == null) {
			dto = new CuentaDTO();
			dto.setIdCuenta(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("TODOS");
			dto.setNumeroCuenta(cuenta);

		}
		return dto;

	}

	/**
	 * Consulta el catálogo de cuentas nombradas de valor por una cuenta en
	 * específico basado en el numero de cuenta completo Si no se localiza una
	 * cuenta se retorna un DTO nulo
	 *
	 * @param cuenta
	 *            número de cuenta completo
	 * @return CuentaDTO la cuenta localizada, nulo en caso de no lozalizarlo
	 */
	public CuentaDTO buscarCuentaPorNumeroCuentaNullSiNoExiste(String cuenta) {
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(DaliConstants.VALOR_COMBO_TODOS);
		CuentaDTO criterio = new CuentaDTO();
		criterio.setInstitucion(institucion);
		criterio.setTipoTenencia(new TipoTenenciaDTO());
		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.setNumeroCuenta(cuenta);

		CuentaDTO dto = null;

		dto = consultaCuentaService.buscarCuentaPorNumeroCuenta(criterio);

		return dto;

	}

	/**
	 * Consulta el catálogo de cuentas nombradas de valor por una cuenta en
	 * específico basado en el numero de cuenta Si no se localiza una cuenta se
	 * retorna un DTO con id -1 y la descripción TODOS
	 *
	 * @param cuenta
	 *            número de cuenta completo
	 * @return CuentaDTO la cuenta localizada
	 */
	public CuentaDTO buscarCuentaParticipantePorNumeroCuenta(InstitucionDTO participante, String cuenta) {

		CuentaDTO criterio = new CuentaDTO();
		criterio.setInstitucion(participante);
		criterio.setTipoTenencia(new TipoTenenciaDTO());
		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.setNumeroCuenta(participante.getClaveTipoInstitucion() + participante.getFolioInstitucion() + cuenta);

		CuentaDTO dto = null;

		dto = consultaCuentaService.buscarCuentaPorNumeroCuenta(criterio);

		if (dto == null) {
			dto = new CuentaDTO();
			dto.setIdCuenta(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("TODOS");
			dto.setCuenta(cuenta);

		}
		return dto;

	}

	/**
	 * Busca una institución basado en la clave de tipo de institución y el
	 * folio de la misma
	 *
	 * @param idFolio
	 *            Cadena con la clave del tipo de institución y el folio
	 * @return Institución encontrada, null en caso de no localizarla
	 */
	public InstitucionDTO buscarInstitucionPorIdFolio(String idFolio) {
		InstitucionDTO institucion = null;
		if (!StringUtils.isEmpty(idFolio)) {
			institucion = consultaInstitucionService.buscarInstitucionPorClaveYFolio(idFolio);
		}

		return institucion;
	}

	/**
	 * Busca una institución basado en la clave de tipo de institución y el
	 * folio de la misma
	 *
	 * @param idFolio
	 *            Cadena con la clave del tipo de institución y el folio
	 * @return Institución encontrada, Un nuevo dto de institución en caso de no
	 *         localizarla
	 */
	public InstitucionDTO buscarInstitucionPorIdNoNulaFolio(String idFolio) {
		InstitucionDTO institucion = null;
		if (!StringUtils.isEmpty(idFolio)) {
			institucion = consultaInstitucionService.buscarInstitucionPorClaveYFolio(idFolio);
		}
		if (institucion == null) {
			institucion = new InstitucionDTO();
		}

		return institucion;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de divisa
	 * preparados en elementos del tipo {@link SelectItem} para desplegar las
	 * opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsTipoDivisa() {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		DivisaDTO dto = new DivisaDTO();
		dto.setId(DaliConstants.VALOR_COMBO_TODOS);
		dto.setDescripcion("TODAS");

		List<SelectItem> divisasEspeciales = new ArrayList<SelectItem>();
		divisasEspeciales.add(new SelectItem(dto, dto.getDescripcion()));
		SelectItem item = null;
		for (DivisaDTO divisas : consultaDivisaService.consultarDivisas(null)) {
			item = new SelectItem(divisas, divisas.getDescripcion());
			if (divisasPrincipales != null && divisasPrincipales.contains(divisas.getClaveAlfabetica())) {
				divisasEspeciales.add(item);
			} else {
				opcionesSelect.add(item);
			}

		}

		divisasEspeciales.addAll(opcionesSelect);

		return divisasEspeciales;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de Bovedas y
	 * filtra solo las de tipo valor preparados en elementos del tipo
	 * {@link SelectItem} para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsBovedasValor() {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		BovedaDTO dto = new BovedaDTO();
		dto.setId(DaliConstants.VALOR_COMBO_TODOS);
		dto.setDescripcion("Selecciona una Boveda");
		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(TipoCustodiaConstants.VALORES);

		opcionesSelect.add(new SelectItem(dto, dto.getDescripcion()));

		for (BovedaDTO bovedas : consultaBovedaService.consultarBovedasPorTipoDeCustodia(criterio, null)) {
			opcionesSelect.add(new SelectItem(bovedas, bovedas.getDescripcion()));
		}
		return opcionesSelect;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de Bovedas y
	 * filtra solo las de tipo valor preparados en elementos del tipo
	 * {@link SelectItem} para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsBovedasEfectivo() {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		BovedaDTO dto = new BovedaDTO();
		dto.setId(DaliConstants.VALOR_COMBO_TODOS);
		dto.setDescripcion("Selecciona una Boveda");
		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(TipoCustodiaConstants.EFECTIVO);

		opcionesSelect.add(new SelectItem(dto, dto.getDescripcion()));

		for (BovedaDTO bovedas : consultaBovedaService.consultarBovedasPorTipoDeCustodia(criterio, null)) {
			opcionesSelect.add(new SelectItem(bovedas, bovedas.getDescripcion()));
		}
		return opcionesSelect;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de Bovedas y
	 * filtra solo las de tipo valor preparados en elementos del tipo
	 * {@link SelectItem} para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsBovedasEfectivoPorDivisaTipoInstruccion(DivisaDTO divisaDTO, TipoInstruccionDTO tipoInstruccionDTO) {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		BovedaDTO dto = new BovedaDTO();
		dto.setId(DaliConstants.VALOR_COMBO_TODOS);
		dto.setDescripcion("Selecciona una Boveda");
		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(TipoCustodiaConstants.EFECTIVO);

		opcionesSelect.add(new SelectItem(dto, dto.getDescripcion()));

		// Obtiene los ID de las bovedas relacionadas a una divisa
		List<BigInteger> idsBovDiv = consultaBovedaService.obtenerBovedasPorDivisa(divisaDTO);
		List<BigInteger> idsBovIns = consultaBovedaService.obtenerBovedasPorTipoInstruccion(tipoInstruccionDTO);
		List<Long> idsBovedas = new ArrayList<Long>();
		for (BigInteger idBov : idsBovDiv) {
			if(idsBovIns.contains(idBov)){
				idsBovedas.add(idBov.longValue());
			}
		}

		//Obtiene las Bovedas
		for (BovedaDTO bovedas : consultaBovedaService.buscarBovedasPorTipoCustodia(criterio, idsBovedas, null)){
			opcionesSelect.add(new SelectItem(bovedas, bovedas.getDescripcion()));
		}
		return opcionesSelect;
	}

	/**
	 * Obtiene una lista con los elementos del catálogo de tipos de Bovedas y
	 * filtra solo las de tipo valor preparados en elementos del tipo
	 * {@link SelectItem} para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsBovedasEfectivoPorDivisaTipoInstruccionCorto(DivisaDTO divisaDTO, TipoInstruccionDTO tipoInstruccionDTO) {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(TipoCustodiaConstants.EFECTIVO);

		// Obtiene los ID de las bovedas relacionadas a una divisa
		List<BigInteger> idsBovDiv = consultaBovedaService.obtenerBovedasPorDivisa(divisaDTO);
		List<BigInteger> idsBovIns = consultaBovedaService.obtenerBovedasPorTipoInstruccion(tipoInstruccionDTO);
		List<Long> idsBovedas = new ArrayList<Long>();
		for (BigInteger idBov : idsBovDiv) {
			if(idsBovIns.contains(idBov)){
				idsBovedas.add(idBov.longValue());
			}
		}

		//Obtiene las Bovedas
		for (BovedaDTO bovedas : consultaBovedaService.buscarBovedasPorTipoCustodia(criterio, idsBovedas, null)){
			opcionesSelect.add(new SelectItem(bovedas.getId(), bovedas.getNombreCorto()));
		}
		return opcionesSelect;
	}


	/**
	 * Muestra una lista con plazos de Liquidacion en Horas habiles para las
	 * operaciones de mercado de capitales.
	 *
	 * @param listaPlazoLiquidacionHoras
	 *            La lista con los plazo de Liquidacion en Horas.
	 */
	public List<SelectItem> getListaPlazoLiquidacionHoras() {
			List<SelectItem> elementos = new ArrayList<SelectItem>();

			elementos.add(new SelectItem(24, "24 hrs."));
			elementos.add(new SelectItem(48, "48 hrs."));
			elementos.add(new SelectItem(72, "72 hrs."));
			elementos.add(new SelectItem(96, "96 hrs."));
			elementos.add(new SelectItem(120, "120 hrs."));
			elementos.add(new SelectItem(144, "144 hrs."));
			elementos.add(new SelectItem(168, "168 hrs."));
			elementos.add(new SelectItem(192, "192 hrs."));
		return elementos;
	}

	/**
	 * Muestra una lista con plazos de Liquidacion en Horas habiles para las
	 * operaciones de mercado de dinero.
	 *
	 * @param listaPlazoLiquidacionHoras
	 *            La lista con los plazo de Liquidacion en Horas.
	 */
	public List<SelectItem> getListaPlazoLiquidacionHorasDinero() {
		List<SelectItem> elementos = new ArrayList<SelectItem>();

		elementos.add(new SelectItem(24, "24 hrs."));
		elementos.add(new SelectItem(48, "48 hrs."));
		elementos.add(new SelectItem(72, "72 hrs."));
		elementos.add(new SelectItem(96, "96 hrs."));
		elementos.add(new SelectItem(120, "120 hrs."));
		elementos.add(new SelectItem(144, "144 hrs."));
		elementos.add(new SelectItem(168, "168 hrs."));
		elementos.add(new SelectItem(192, "192 hrs."));

		return elementos;
	}



	/**
	 * Muestra una lista con plazos de Liquidacion en Horas habiles para la
	 * operacion
	 *
	 * @return
	 */
	public DivisaDTO buscarDivisaPorId(long id) {
		DivisaDTO dto = null;

		dto = consultaDivisaService.consultarDivisaPorId(id);

		if (dto == null) {
			dto = new DivisaDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setClaveAlfabetica("TODOS");
			dto.setDescripcion("TODOS");
		}
		return dto;
	}

	/**
	 * Obtiene las opciones disponibles en el catalogo de divisas filtrados por el TipoInstruccion
	 *
	 * @return Lista de SelectItem con las divisas existentes en la base
	 */
	public List<SelectItem> getOpcionesComboDivisaPorTipoInstruccion(TipoInstruccionDTO tipoInstruccionDTO) {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for(DivisaDTO divisa :consultaDivisaService.consultarDivisasPorTipoInstruccion(null, BigInteger.valueOf(tipoInstruccionDTO.getIdTipoInstruccion()))) {
			opcionesCombo.add(new SelectItem(divisa, divisa.getDescripcion()));
		}
		return opcionesCombo;
	}

	/**
	 * Muestra una lista con plazos de Liquidacion en Horas habiles para la
	 * operacion
	 *
	 * @return
	 */
	public BovedaDTO buscarBovedaPorId(long id) {
		BovedaDTO dto = null;
		dto = consultaBovedaService.consultarBovedaPorId(id);
		if (dto == null) {
			dto = new BovedaDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("TODOS");
			dto.setNombreCorto("TODOS");
			dto.setClaveTipoBoveda("N/A");
		}
		return dto;
	}

	/**
	 * Muestra una lista con las opciones para establecer el momento en que se
	 * realizara la operación
	 *
	 * @return
	 */
	public List<SelectItem> getListaMismoDiaFechaValor() {
		List<SelectItem> elementos = new ArrayList<SelectItem>();

		elementos.add(new SelectItem(0, "Mismo día (MD)"));
		elementos.add(new SelectItem(1, "Fecha valor (FV)"));

		return elementos;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo
	 *
	 * @param idFolioParticipante
	 * @return Saldo del participante, null en caso de no existir el
	 *         participante
	 */
	public Double getSaldoNetoEfectivo(String idFolioParticipante) {
		logger.debug("ConsultaCatalogosFacade :: getSaldoNetoEfectivo");
		Double saldo = null;

		BovedaDTO boveda = new BovedaDTO();
		boveda.setId(BovedaDTO.BOVEDA_BANXICO);
		
		DivisaDTO divisa = new DivisaDTO();
		divisa.setId(DivisaDTO.DIVISA_PESO_MX);

		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		criterio.setBoveda(boveda);
		criterio.setDivisa(divisa);
		
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo
	 *
	 * @param idFolioParticipante
	 * @param idBoveda
	 * @return Saldo del participante, null en caso de no existir el
	 *         participante
	 */
	public Double getSaldoNetoEfectivoPorBoveda(String idFolioParticipante, Long idBoveda) {
		logger.debug("ConsultaCatalogosFacade :: getSaldoNetoEfectivoPorBoveda");
		Double saldo = null;
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		// crear objeto con el id de la boveda
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(idBoveda);
		criterio.setBoveda(bovedaDTO);
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo
	 *
	 * @param idFolioParticipante
	 * @param idBoveda
	 * @return Saldo del participante, null en caso de no existir el
	 *         participante
	 */
	public Double getSaldoNetoEfectivoPorBovedaDivisa(String idFolioParticipante, Long idBoveda, Long idDivisa) {
		logger.debug("ConsultaCatalogosFacade :: getSaldoNetoEfectivoPorBovedaDivisa");
		Double saldo = null;
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		// crear objeto con el id de la boveda
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(idBoveda);
		criterio.setBoveda(bovedaDTO);
		// crear objeto con el id de la divisa
		DivisaDTO divisaDTO = new DivisaDTO();
		divisaDTO.setId(idDivisa);
		criterio.setDivisa(divisaDTO);
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo por medio de idfolio del participante y del id de la bóveda
	 *
	 * @param idFolioParticipante ,
	 *            idBoveda
	 * @return Saldo del participante, null en caso de no existir el
	 *         participante
	 */
	public Double getSaldoNetoEfectivo(String idFolioParticipante, Long idBoveda) {
		logger.debug("ConsultaCatalogosFacade :: getSaldoNetoEfectivo");
		Double saldo = null;
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	/**
	 * Obtiene la cuenta cocntrolada de efectivo participante
	 *
	 * @param idFolioParticipante
	 * @return Objeto de cuenta de efectivo
	 */
	public SaldoEfectivoDTO getSaldoControladaEfectivo(String idFolioParticipante) {
		logger.debug("ConsultaCatalogosFacade :: getSaldoControladaEfectivo");
		SaldoEfectivoDTO saldo = null;
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0);
		} else {
			saldo = null;
		}
		return saldo;
	}

	public BigDecimal getSaldoEfectivoBovedaDivisa( String id, String folio, BigInteger idBoveda, BigInteger idDivisa ) {
		logger.info("Datos enviados: [" + id + folio + "-" + idBoveda + "-" + idDivisa + "]");
		return consultaSaldoEfectivoService.getSaldoEfectivo(id, folio, idBoveda, idDivisa);
	}
	
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object prefijo) {
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		return this.consultaEmisoraService.buscarEmisorasPorPrefijo(prefijoAjustado);

	}
	
	public List<InstitucionDTO> buscarParticipantePorPrefijo(Object prefijo) {
		List<InstitucionDTO> resultado = null;
		List<TipoInstitucionDTO> institucion = new ArrayList<TipoInstitucionDTO>();
		List<InstitucionDTO> listaInstituciones = null;
		String prefijoAjustado = "";

		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).trim().replace("*", "%");
			listaInstituciones = new ArrayList<InstitucionDTO>();

			institucion = this.consultaTipoInstitucionService.consultaTipoInstitucionPorPrefijo(prefijoAjustado);
			resultado = this.consultaInstitucionService.buscarInstitucionesPorPrefijo(prefijoAjustado);

			if (!institucion.isEmpty()) {

				InstitucionDTO ins = new InstitucionDTO();
				ins.setId(new Long(100) + institucion.get(0).getId());
				ins.setClaveTipoInstitucion(institucion.get(0).getClaveTipoInstitucion());
				ins.setNombreCorto(institucion.get(0).getDescripcion());

				listaInstituciones.add(ins);
			}

			for (InstitucionDTO institucionDTO : resultado) {
				listaInstituciones.add(institucionDTO);
			}
		}

		return listaInstituciones;
	}
	
	public List<CuentaDTO> buscarCuentasDeInstitucionPorPrefijo(String idFolioInstitucion, String prefijoCuenta) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		if (StringUtils.isNotEmpty(idFolioInstitucion) && StringUtils.isNotEmpty(prefijoCuenta)) {
			InstitucionDTO institucion = this.buscarInstitucionPorIdFolio(idFolioInstitucion);
			if (institucion != null) {
				CuentaDTO criterio = new CuentaDTO();
				criterio.setInstitucion(institucion);
				criterio.setTipoTenencia(new TipoTenenciaDTO());

				criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
				criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
				criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
				criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
				criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
				String prefijoAjustado = "";
				if (prefijoCuenta != null) {
					prefijoAjustado = String.valueOf(prefijoCuenta).replace('*', '%');
				}
				criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + prefijoAjustado);

				cuentas = this.consultaCuentaService.buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}
		}

		return cuentas;
	}

	/**
	 * Obtiene el campo consultaMercadoService
	 *
	 * @return consultaMercadoService
	 */
	public ConsultaMercadoService getConsultaMercadoService() {
		return consultaMercadoService;
	}

	/**
	 * Obtiene el valor del atributo consultaEmisionService
	 *
	 * @return el valor del atributo consultaEmisionService
	 */
	public ConsultaEmisionService getConsultaEmisionService() {
		return consultaEmisionService;
	}

	/**
	 * Establece el valor del atributo consultaEmisionService
	 *
	 * @param consultaEmisionService
	 *            el valor del atributo consultaEmisionService a establecer
	 */
	public void setConsultaEmisionService(ConsultaEmisionService consultaEmisionService) {
		this.consultaEmisionService = consultaEmisionService;
	}

	/**
	 * Asigna el campo consultaMercadoService
	 *
	 * @param consultaMercadoService
	 *            el valor de consultaMercadoService a asignar
	 */
	public void setConsultaMercadoService(ConsultaMercadoService consultaMercadoService) {
		this.consultaMercadoService = consultaMercadoService;
	}

	/**
	 * Obtiene el campo consultaEstadoInstruccionService
	 *
	 * @return consultaEstadoInstruccionService
	 */
	public ConsultaEstadoInstruccionService getConsultaEstadoInstruccionService() {
		return consultaEstadoInstruccionService;
	}

	/**
	 * Asigna el campo consultaEstadoInstruccionService
	 *
	 * @param consultaEstadoInstruccionService
	 *            el valor de consultaEstadoInstruccionService a asignar
	 */
	public void setConsultaEstadoInstruccionService(ConsultaEstadoInstruccionService consultaEstadoInstruccionService) {
		this.consultaEstadoInstruccionService = consultaEstadoInstruccionService;
	}

	/**
	 * Obtiene el campo consultaTipoInstruccionService
	 *
	 * @return consultaTipoInstruccionService
	 */
	public ConsultaTipoInstruccionService getConsultaTipoInstruccionService() {
		return consultaTipoInstruccionService;
	}

	/**
	 * Asigna el campo consultaTipoInstruccionService
	 *
	 * @param consultaTipoInstruccionService
	 *            el valor de consultaTipoInstruccionService a asignar
	 */
	public void setConsultaTipoInstruccionService(ConsultaTipoInstruccionService consultaTipoInstruccionService) {
		this.consultaTipoInstruccionService = consultaTipoInstruccionService;
	}

	/**
	 * Obtiene el campo consultaCuentaService
	 *
	 * @return consultaCuentaService
	 */
	public ConsultaTipoLiquidacionService getConsultaTipoLiquidacionService() {
		return consultaTipoLiquidacionService;
	}

	public void setConsultaTipoLiquidacionService(
			ConsultaTipoLiquidacionService consultaTipoLiquidacionService) {
		this.consultaTipoLiquidacionService = consultaTipoLiquidacionService;
	}





	public ConsultaCuentaService getConsultaCuentaService() {
		return consultaCuentaService;
	}

	/**
	 * Asigna el campo consultaCuentaService
	 *
	 * @param consultaCuentaService
	 *            el valor de consultaCuentaService a asignar
	 */
	public void setConsultaCuentaService(ConsultaCuentaService consultaCuentaService) {
		this.consultaCuentaService = consultaCuentaService;
	}

	/**
	 * Obtiene el campo consultaTipoValorService
	 *
	 * @return consultaTipoValorService
	 */
	public ConsultaTipoValorService getConsultaTipoValorService() {
		return consultaTipoValorService;
	}

	/**
	 * Asigna el campo consultaTipoValorService
	 *
	 * @param consultaTipoValorService
	 *            el valor de consultaTipoValorService a asignar
	 */
	public void setConsultaTipoValorService(ConsultaTipoValorService consultaTipoValorService) {
		this.consultaTipoValorService = consultaTipoValorService;
	}

	/**
	 * Obtiene el campo consultaEmisoraService
	 *
	 * @return consultaEmisoraService
	 */
	public ConsultaEmisoraService getConsultaEmisoraService() {
		return consultaEmisoraService;
	}

	/**
	 * Asigna el campo consultaEmisoraService
	 *
	 * @param consultaEmisoraService
	 *            el valor de consultaEmisoraService a asignar
	 */
	public void setConsultaEmisoraService(ConsultaEmisoraService consultaEmisoraService) {
		this.consultaEmisoraService = consultaEmisoraService;
	}

	/**
	 * Obtiene el campo consultaTipoMensajeService
	 *
	 * @return consultaTipoMensajeService
	 */
	public ConsultaTipoMensajeService getConsultaTipoMensajeService() {
		return consultaTipoMensajeService;
	}

	/**
	 * Asigna el campo consultaTipoMensajeService
	 *
	 * @param consultaTipoMensajeService
	 *            el valor de consultaTipoMensajeService a asignar
	 */
	public void setConsultaTipoMensajeService(ConsultaTipoMensajeService consultaTipoMensajeService) {
		this.consultaTipoMensajeService = consultaTipoMensajeService;
	}

	/**
	 * Obtiene el campo consultaDivisaService
	 *
	 * @return consultaDivisaService
	 */
	public ConsultaDivisaService getConsultaDivisaService() {
		return consultaDivisaService;
	}

	/**
	 * Asigna el campo consultaDivisaService
	 *
	 * @param consultaDivisaService
	 *            el valor de consultaDivisaService a asignar
	 */
	public void setConsultaDivisaService(ConsultaDivisaService consultaDivisaService) {
		this.consultaDivisaService = consultaDivisaService;
	}

	/**
	 * Obtiene el campo consultaInstitucionService
	 *
	 * @return consultaInstitucionService
	 */
	public ConsultaInstitucionService getConsultaInstitucionService() {
		return consultaInstitucionService;
	}

	/**
	 * Asigna el campo consultaInstitucionService
	 *
	 * @param consultaInstitucionService
	 *            el valor de consultaInstitucionService a asignar
	 */
	public void setConsultaInstitucionService(ConsultaInstitucionService consultaInstitucionService) {
		this.consultaInstitucionService = consultaInstitucionService;
	}

	/**
	 * Obtiene el campo consultaBovedaService
	 *
	 * @return consultaBovedaService
	 */
	public ConsultaBovedaService getConsultaBovedaService() {
		return consultaBovedaService;
	}

	/**
	 * Obtiene el valor del atributo consultaErroresLiquidacionService
	 *
	 * @return el valor del atributo consultaErroresLiquidacionService
	 */
	public ConsultaErroresLiquidacionService getConsultaErroresLiquidacionService() {
		return consultaErroresLiquidacionService;
	}

	/**
	 * Establece el valor del atributo consultaErroresLiquidacionService
	 *
	 * @param consultaErroresLiquidacionService
	 *            el valor del atributo consultaErroresLiquidacionService a
	 *            establecer.
	 */
	public void setConsultaErroresLiquidacionService(ConsultaErroresLiquidacionService consultaErroresLiquidacionService) {
		this.consultaErroresLiquidacionService = consultaErroresLiquidacionService;
	}

	/**
	 * Asigna el campo consultaBovedaService
	 *
	 * @param consultaBovedaService
	 *            el valor de consultaBovedaService a asignar
	 */
	public void setConsultaBovedaService(ConsultaBovedaService consultaBovedaService) {
		this.consultaBovedaService = consultaBovedaService;
	}

	/**
	 * Obtiene el campo consultaSaldoEfectivoService
	 *
	 * @return consultaSaldoEfectivoService
	 */
	public ConsultaSaldosEfectivoService getConsultaSaldoEfectivoService() {
		return consultaSaldoEfectivoService;
	}

	/**
	 * Asigna el campo consultaSaldoEfectivoService
	 *
	 * @param consultaSaldoEfectivoService
	 *            el valor de consultaSaldoEfectivoService a asignar
	 */
	public void setConsultaSaldoEfectivoService(ConsultaSaldosEfectivoService consultaSaldoEfectivoService) {
		this.consultaSaldoEfectivoService = consultaSaldoEfectivoService;
	}

	/**
	 * @return the divisasPrincipales
	 */
	public List<String> getDivisasPrincipales() {
		return divisasPrincipales;
	}

	/**
	 * @param divisasPrincipales
	 *            the divisasPrincipales to set
	 */
	public void setDivisasPrincipales(List<String> divisasPrincipales) {
		this.divisasPrincipales = divisasPrincipales;
	}

	/**
	 * Obtiene el valor del atributo consultaTipoInstitucionService
	 *
	 * @return el valor del atributo consultaTipoInstitucionService
	 */
	public ConsultaTipoInstitucionService getConsultaTipoInstitucionService() {
		return consultaTipoInstitucionService;
	}

	/**
	 * Establece el valor del atributo consultaTipoInstitucionService
	 *
	 * @param consultaTipoInstitucionService
	 *            el valor del atributo consultaTipoInstitucionService a
	 *            establecer
	 */
	public void setConsultaTipoInstitucionService(ConsultaTipoInstitucionService consultaTipoInstitucionService) {
		this.consultaTipoInstitucionService = consultaTipoInstitucionService;
	}

	public List<EstadoInstruccionDTO> buscarEstadosInstruccionPorIds(String prefijo){
		return consultaEstadoInstruccionService.buscarEstadosInstruccionPorIds(prefijo);
	}
}
