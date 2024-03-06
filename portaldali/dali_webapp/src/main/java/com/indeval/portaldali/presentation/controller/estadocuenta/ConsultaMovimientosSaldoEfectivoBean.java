/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 3, 2008
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;


import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaSaldoPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DefinicionDetallesMovimientosHelper;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaMovimientosEfectivo;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean que da soporte a las operaciones realizadas en la pantalla de
 * consulta del estado de cuenta de movimientos que refleja los incrementos y
 * decrementos en los saldos de cuentas de efectivo.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class ConsultaMovimientosSaldoEfectivoBean extends BackingBeanBase {

	/**
	 * Modelo de consulta que proporciona acceso a la consulta del estado de
	 * cuenta de movimientos de saldos en efectivo
	 */
	private ConsultaMovimientosEfectivo consultaMovimientos = null;
	/**
	 * Helper que contiene la definición de la operación que corresonde al tipo
	 * de instrucción de un registro contable.
	 */
	private DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper = null;
	/**
	 * Indica si se debe de restaurar la pagicación de los resultados
	 * principales de la página
	 */
	private boolean restaurarPaginacionResultados = true;
	/**
	 * Fachada para la consulta de catálogos
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;

	/** Contiene el papel mercado por el cual se filtra el mercado */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/** La emisora seleccionada */
	private String emisoraSeleccionada;
	
	/** El tipo de valor seleccionado */
	private TipoValorDTO instrumentoSeleccionado;


	/**
	 * Indica si la consulta principal ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;
	/**
	 * Nombre de la institución contraparte capturada en pantalla
	 */
	private String institucionContraparteSeleccionada = null;
	/**
	 * resultados temporales de la consulta
	 */
	List<EstadoCuentaEfectivoPorDivisaDTO> resultados = null;

	/** Total de registros contables encontrados */
	private Long totalResultados = null;

	/** Total de abono */
	private BigDecimal totalAbono;

	/** Total de cargo */
	private BigDecimal totalCargo;

	/**
	 * Almacena la clave y folio de la institucion
	 */
	private String folioClaveInstitucion;

	/** La cuenta de valores de la contraparte */
	private String cuentaValoresContraparte;

	/** Lista de Tipos de Retiros para el combo*/
	private List opcionesTipoRetiro;

	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucionConsultada = null;

	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucionConsultadaContraparte = null;

	/**
	 * Los resultados de la consulta de movimientos de cuentas controladas para
	 * el reporte en formato XLS
	 */
	private List<RegistroContableSaldoControladaDTO> resultadosControladasReporteXls = new ArrayList<RegistroContableSaldoControladaDTO>();

	/**
	 * Los resultados de la consulta de movimientos de cuentas nombradas para el
	 * reporte en formato XLS
	 */
	private List<RegistroContableSaldoNombradaDTO> resultadosNombradasReporteXls = new ArrayList<RegistroContableSaldoNombradaDTO>();

	/** DAO para la consulta del catálogo de tipos de instrucción */
	private TipoInstruccionDaliDAO tipoInstruccionDaliDAO;
	
	/**
	 * Inicializa los criterios de consulta
	 * 
	 * @param e
	 *            ActionEvent generado durante la solicitud
	 */
	public void limpiar(ActionEvent e) {
		inicializarCriterios();
	}

	/**
	 * Obtiene el valor del atributo mercadoSeleccionado
	 * 
	 * @return el valor del atributo mercadoSeleccionado
	 */
	public MercadoDTO getMercadoSeleccionado() {
		return consultaMovimientos.getMercadoSeleccionado();
	}

	/**
	 * Establece el valor del atributo mercadoSeleccionado
	 * 
	 * @param mercadoSeleccionado
	 *            el valor del atributo mercadoSeleccionado a establecer
	 */
	public void setMercadoSeleccionado(MercadoDTO mercadoSeleccionado) {

		if (DaliConstants.ID_MERCADO_DINERO != mercadoSeleccionado.getId()) {
			consultaMovimientos.setMercadoSeleccionado(consultaCatalogos.getConsultaMercadoService().buscarMercadoPorId(mercadoSeleccionado.getId()));
		} else {
			consultaMovimientos.setMercadoSeleccionado(mercadoSeleccionado);
			consultaMovimientos.getMercadoSeleccionado().setDescripcion(DaliConstants.DESCRIPCION_MERCADO_DINERO);
		}
	}
	
	/**
	 * Asigna el participante predeterminado en la consulta.
	 * 
	 * @return <code>null</code>, este método no requiere de un valor de retorno.
	 */
	public String getInitParticipante() {
		
		folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();

		return null;
	}
	
	/**
	 * Se ejecutan acciones de inicialización de la consulta, este método se
	 * invoca antes de pintar cualquier componente de la pantalla.
	 * 
	 * @return null
	 */
	public String getInit() {

		FacesContext ctx = FacesContext.getCurrentInstance();
		if (getInstitucionActual() != null) {
			consultaMovimientos.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
			// Reiniciar el criterio de naturaleza para el usuario participante
			// para dejar por default la naturaleza : Pasivo
			if (isUsuarioIndeval()) {
				TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
				pasivo.setId(TipoNaturalezaDTO.PASIVO);
				consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(pasivo);
			} else {
				folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			}
		}

		if (ctx.getRenderResponse()) {
			if (isConsultaEjecutada()) {

				consultaMovimientos.setDivisaSeleccionada(null);
				consultaMovimientos.setBovedaSeleccionada(null);
				consultaMovimientos.setCuentaSeleccionada(null);
				consultaMovimientos.recibirNotificacionResultados(null);
				consultaMovimientos.getCriterioDivisa().reestablecerEstadoPaginacion();
			}
			consultaMovimientos.getOpcionSeleccionada();
		}
		if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {
			// si es la primera vez que se visita la página
			inicializarCriterios();
		}

		return null;
	}

	/**
	 * Atiene el evento de cambio de elemento seleccionado del criterio de papel
	 * mercado.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectPapelMercado(ActionEvent e) {
		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setDescripcion("TODOS");
		if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_TODOS)) {
			mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		} else {
			if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_DINERO)) {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_DINERO);
			} else {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_CAPITALES);
			}
		}
		consultaMovimientos.getCriterioEmision().getTipoValor().setMercado(mercadosTodos);
		consultaMovimientos.getCriterioEmision().getTipoValor().setId(DaliConstants.VALOR_COMBO_TODOS);
		consultaMovimientos.getCriterioEmision().getTipoValor().setDescripcion(DaliConstants.DESCRIPCION_TODOS);

	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del tipo de valor.
	 * Reinicia la opción seleccionada del criterio serie
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectTipoValor(ActionEvent e) {

//		if (consultaMovimientos.getCriterioEmision().getTipoValor() != null && StringUtils.isNotBlank(consultaMovimientos.getCriterioEmision().getTipoValor().getClaveTipoValor())) {
//			TipoValorDTO tipoValor = consultaCatalogos.getConsultaTipoValorService().buscarTipoDeValorPorClave(
//					consultaMovimientos.getCriterioEmision().getTipoValor().getClaveTipoValor());
//			if(tipoValor != null) {
//				consultaMovimientos.getCriterioEmision().getTipoValor().setId(tipoValor.getId());
//				consultaMovimientos.getCriterioEmision().getTipoValor().setClaveTipoValor(tipoValor.getClaveTipoValor());
//				consultaMovimientos.getCriterioEmision().getTipoValor().setDescripcion(tipoValor.getDescripcion());
//				consultaMovimientos.getCriterioEmision().getTipoValor().setMercado(consultaMovimientos.getMercadoSeleccionado());
//			}
//		}
		consultaMovimientos.getCriterioEmision().setSerie(new SerieDTO());
		consultaMovimientos.getCriterioEmision().getSerie().setId(-1);
		consultaMovimientos.getCriterioEmision().getSerie().setDescripcion(DaliConstants.DESCRIPCION_TODOS);
		consultaMovimientos.getCriterioEmision().getSerie().setEmisora(consultaMovimientos.getCriterioEmision().getEmisora());
		consultaMovimientos.getCriterioEmision().getSerie().setTipoValor(consultaMovimientos.getCriterioEmision().getTipoValor());
	}

	/**
	 * Obtiene el atributo emisoraSeleccionada
	 * 
	 * @return el emisoraSeleccionada
	 */
	public String getEmisoraSeleccionada() {
		return emisoraSeleccionada;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescripcionTipoValor() {
		String descripcion = DaliConstants.DESCRIPCION_TODOS;
		if(consultaMovimientos.getCriterioEmision().getTipoValor() != null
				&& StringUtils.isNotBlank(consultaMovimientos.getCriterioEmision().getTipoValor().getClaveTipoValor())) {
			TipoValorDTO tipoValor = consultaCatalogos.getConsultaTipoValorService().buscarTipoDeValorPorClave(
					consultaMovimientos.getCriterioEmision().getTipoValor().getClaveTipoValor());
			if(tipoValor != null) {
				descripcion = tipoValor.getClaveTipoValor();
			}
		}
		if(StringUtils.isEmpty(descripcion)) descripcion = DaliConstants.DESCRIPCION_TODOS;
		return descripcion;
	}
	
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de valor.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de valor encontrados
	 */
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(
				consultaMovimientos.getCriterioEmision().getTipoValor().getMercado(), String.valueOf(prefijo));
	}

	
	/**
	 * 
	 * @return
	 */
	public String getDescripcionSerie() {
		String descripcion = DaliConstants.DESCRIPCION_TODOS;
		if(consultaMovimientos.getCriterioEmision().getSerie() != null) {
			descripcion = consultaMovimientos.getCriterioEmision().getSerie().getSerie();
		}
		
		if(descripcion.equals(String.valueOf(DaliConstants.VALOR_COMBO_TODOS))) {
			descripcion = DaliConstants.DESCRIPCION_TODOS; 
		}
		
		return descripcion;
	}
	
	/**
	 * Establece el atributo emisoraSeleccionada
	 * 
	 * @param emisoraSeleccionada
	 *            el emisoraSeleccionada a establecer
	 */
	public void setEmisoraSeleccionada(String emisoraSeleccionada) {
		this.emisoraSeleccionada = emisoraSeleccionada; 
		
		if (StringUtils.isNotBlank(emisoraSeleccionada)) {
			consultaMovimientos.getCriterioEmision().setEmisora(consultaCatalogos.buscarEmisoraPorNombreCorto(emisoraSeleccionada)); 
		} else {
			consultaMovimientos.getCriterioEmision().setEmisora(new EmisoraDTO());
			consultaMovimientos.getCriterioEmision().getEmisora().setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			consultaMovimientos.getCriterioEmision().getEmisora().setId(DaliConstants.VALOR_COMBO_TODOS); 
		}
	}

	
	public TipoValorDTO getInstrumentoSeleccionado() {
		return instrumentoSeleccionado;
	}

	public void setInstrumentoSeleccionado(TipoValorDTO instrumentoSeleccionado) {
		this.instrumentoSeleccionado = instrumentoSeleccionado;
		
		if (instrumentoSeleccionado != null && StringUtils.isNotBlank(instrumentoSeleccionado.getClaveTipoValor())) {
			TipoValorDTO tipoValor = consultaCatalogos.getConsultaTipoValorService().buscarTipoDeValorPorClave( instrumentoSeleccionado.getClaveTipoValor());
			if(tipoValor != null) {
				consultaMovimientos.getCriterioEmision().getTipoValor().setId(tipoValor.getId());
				consultaMovimientos.getCriterioEmision().getTipoValor().setClaveTipoValor(tipoValor.getClaveTipoValor());
				consultaMovimientos.getCriterioEmision().getTipoValor().setDescripcion(tipoValor.getDescripcion());
				consultaMovimientos.getCriterioEmision().getTipoValor().setMercado(consultaMovimientos.getMercadoSeleccionado());
			}
		} else{
			TipoValorDTO tipoValor = new TipoValorDTO();
			tipoValor.setId(DaliConstants.VALOR_COMBO_TODOS);
			tipoValor.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			consultaMovimientos.getCriterioEmision().setTipoValor(tipoValor); 
			consultaMovimientos.getCriterioEmision().getTipoValor().setMercado(consultaMovimientos.getMercadoSeleccionado());
		}
	}
	
	
	/**
	 * Busca emisoras en el catálogo cuya descripción comiencen con el prefijo
	 * proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link EmisoraDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object value) {
		List<EmisoraDTO> emisoras = new ArrayList<EmisoraDTO>();
		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}
		if (value != null) {
			emisoras = consultaCatalogos.getConsultaEmisoraService().buscarEmisorasPorPrefijo(prefijoAjustado);
		}
		return emisoras;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Serie
	 * 
	 * @return Lista con las opciones válidas del criterio de Serie
	 */
	public List<SelectItem> getOpcionesComboSerie() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		SerieDTO serieTodos = new SerieDTO();
		serieTodos.setSerie("-1");
		serieTodos.setDescripcion("TODOS");

		opcionesCombo.add(new SelectItem(serieTodos, serieTodos.getDescripcion()));

		for (SerieDTO nat : consultaCatalogos.getConsultaEmisionService().buscarSeries(consultaMovimientos.getCriterioEmision().getSerie())) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Serie
	 * 
	 * @return Lista con las opciones válidas del criterio de Serie
	 */
	public List<SelectItem> getOpcionesComboTipoRetiro() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();


		opcionesCombo.add(new SelectItem("-1", "TODOS"));

		if( opcionesTipoRetiro != null && opcionesTipoRetiro.size() > 0 ) {
			for( Object opcion : opcionesTipoRetiro ) {
				opcionesCombo.add(new SelectItem((String)opcion, (String)opcion));
			}
		}

		return opcionesCombo;
	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del criterio de
	 * mercado . Reinicia la opción seleccionada del tipo de valor y serie.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectMercado(ActionEvent e) {

		consultaMovimientos.getCriterioEmision().getTipoValor().setId(DaliConstants.VALOR_COMBO_TODOS);
		consultaMovimientos.getCriterioEmision().getTipoValor().setDescripcion(DaliConstants.DESCRIPCION_TODOS);
		consultaMovimientos.getCriterioEmision().getTipoValor().setMercado(consultaMovimientos.getMercadoSeleccionado());
		consultaMovimientos.getCriterioEmision().setSerie(new SerieDTO());
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Mercado
	 * 
	 * @return Lista con las opciones válidas del criterio de Mercado
	 */
	public List<SelectItem> getOpcionesComboMercado() {
		List<SelectItem> resultado = null;
		if (StringUtils.isBlank(papelMercado) || DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = consultaCatalogos.getSelectItemMercadoTodos();
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = consultaCatalogos.getSelectItemsDeMercadoDinero();
			} else {
				resultado = consultaCatalogos.getSelectItemsDeMercadoCapitales();
			}
		}
		return resultado;
	}

	/**
	 * Obtiene la descripción del atributo papelMercado.
	 * 
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = DaliConstants.DESCRIPCION_MERCADO_DINERO;
			} else {
				resultado = DaliConstants.DESCRIPCION_MERCADO_CAPITALES;
			}
		}

		return resultado;
	}

	/**
	 * Obtiene el valor del atributo papelMercado
	 * 
	 * @return el valor del atributo papelMercado
	 */
	public String getPapelMercado() {
		return papelMercado;
	}

	/**
	 * Establece el valor del atributo papelMercado
	 * 
	 * @param papelMercado
	 *            el valor del atributo papelMercado a establecer
	 */
	public void setPapelMercado(String papelMercado) {
		this.papelMercado = papelMercado;
	}

	/**
	 * Obtiene el valor del atributo cuentaValoresContraparte
	 * 
	 * @return el valor del atributo cuentaValoresContraparte
	 */
	public String getCuentaValoresContraparte() {
		return StringUtils.isBlank(cuentaValoresContraparte) ? "TODAS" : cuentaValoresContraparte;
	}

	/**
	 * Establece el valor del atributo cuentaValoresContraparte
	 * 
	 * @param cuentaValoresContraparte
	 *            el valor del atributo cuentaValoresContraparte a establecer
	 */
	public void setCuentaValoresContraparte(String cuentaValoresContraparte) {
		this.cuentaValoresContraparte = cuentaValoresContraparte;
		CuentaDTO cuenta = null;
		if (StringUtils.isNotBlank(cuentaValoresContraparte) && StringUtils.isNotBlank(institucionContraparteSeleccionada)) {

			cuenta = consultaCatalogos.buscarCuentaPorNumeroCuenta(institucionContraparteSeleccionada + cuentaValoresContraparte);
		}
		consultaMovimientos.setCriterioCuentaValoresContraparte(cuenta);
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<TipoInstruccionDTO> buscarTipoInstruccionPorPrefijo(Object prefijo) {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccionPorIdsEfectivo(prefijo.toString());
	}

	/**
	 * Inicializa los criterios de búsqueda
	 */
	private void inicializarCriterios() {
		setConsultaEjecutada(false);
		consultaMovimientos.setCriterioFechaInicial(new Date());
		consultaMovimientos.setCriterioFechaFinal(new Date());
		consultaMovimientos.setBusquedaFechaAplicacion(false);
		consultaMovimientos.setBusquedaFechaConcertacion(false);
		this.cuentaValoresContraparte=null;

		consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);

		consultaMovimientos.setCriterioCuentaContraparte(new CuentaEfectivoDTO());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCustodia(consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getTipoCustodia());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCuenta(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getOpcionSeleccionada());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoNaturaleza(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().getOpcionSeleccionada());
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(-1);
		consultaMovimientos.getCriterioCuentaContraparte().setInstitucion(institucion);
		consultaMovimientos.getCriterioCuentaContraparte().setDescripcion("TODAS");
		consultaMovimientos.getCriterioCuentaContraparte().setNumeroCuenta("-1");

		consultaMovimientos.getCriterioBoveda().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioDivisa().setOpcionSeleccionada(MXP);
		consultaMovimientos.getCriterioTipoInstruccion().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioTipoOperacion().setOpcionSeleccionada(null);
		consultaMovimientos.setCriterioFolioInstruccion(null);

		consultaMovimientos.setCriterioEmision(new EmisionDTO());

		consultaMovimientos.setCriterioRolContraparte("-1");
		consultaMovimientos.setCriterioRolParticipante("-1");
		institucionContraparteSeleccionada = null;
		
		papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;
		emisoraSeleccionada = null;
		
		consultaMovimientos.getCriterioBoveda().reestablecerEstadoPaginacion();
		consultaMovimientos.getCriterioDivisa().reestablecerEstadoPaginacion();
		consultaMovimientos.getCriterioCuenta().reestablecerEstadoPaginacion();
		consultaMovimientos.reestablecerEstadoPaginacion();

		if (consultaMovimientos.getCriterioCuenta().isTodos()) {
			consultaMovimientos.getCriterioCuenta().toggleTodos();
		}

		if (consultaMovimientos.getCriterioDivisa().isTodos()) {
			consultaMovimientos.getCriterioDivisa().toggleTodos();
		}
		if (!consultaMovimientos.getCriterioBoveda().isTodos()) {
			consultaMovimientos.getCriterioBoveda().toggleTodos();
		}

		consultaMovimientos.setImporte(BigDecimal.valueOf(0));

		consultaMovimientos.setTipoRetiro("-1");
		
		emisoraSeleccionada = null;
		
		instrumentoSeleccionado = new TipoValorDTO();
		

	}

	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

		if (navegacion != null && navegacion.length() > 0) {
			try {
				consultaMovimientos.getCriterioDivisa().getClass().getMethod(navegacion).invoke(consultaMovimientos.getCriterioDivisa());
			} catch (Exception ex) {
				logger.error("Error de invocación de método al navegar por los resultados principales", ex);
			}
			restaurarPaginacionResultados = false;
		}
	}

	/**
	 * Prepara los criterios para la ejecución de la consulta de resultados del
	 * estado de cuenta de movimientos de saldos en efectivo dependiendo de los
	 * valores de criterios seleccionados.
	 * 
	 * @param e
	 *            ActionEvent generado durante la petición
	 */
	public void buscarMovimientos() {
		boolean fechasValidas = 
			validarFechaObligatoria(consultaMovimientos.getCriterioFechaInicial(), false, CamposPantallaConstantes.campoFechaInicial) &&
			validarFechaObligatoria(consultaMovimientos.getCriterioFechaFinal(), false, CamposPantallaConstantes.campoFechaFinal);
		fechasValidas = fechasValidas && validarFechaFinalVsFechaInicial(
			consultaMovimientos.getCriterioFechaInicial(), consultaMovimientos.getCriterioFechaFinal());
		if(fechasValidas) {
			consultaMovimientos.setDivisaSeleccionada(null);
			consultaMovimientos.setBovedaSeleccionada(null);
			consultaMovimientos.setCuentaSeleccionada(null);
			
			if (consultaMovimientos.getCriterioCuenta().isTodos()) {
				consultaMovimientos.getCriterioCuenta().toggleTodos();
			}
			if (consultaMovimientos.getCriterioDivisa().isTodos()) {
				consultaMovimientos.getCriterioDivisa().toggleTodos();
			}
			if (!consultaMovimientos.getCriterioBoveda().isTodos()) {
				consultaMovimientos.getCriterioBoveda().toggleTodos();
			}
			
			consultaMovimientos.getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
			consultaMovimientos.getCriterioCuenta().getEstadoPaginacion().setTotalResultados(0);
			
			consultaMovimientos.getCriterioDivisa().getEstadoPaginacion().setNumeroPagina(1);
			consultaMovimientos.getCriterioDivisa().getEstadoPaginacion().setTotalResultados(0);
			
			consultaMovimientos.recibirNotificacionResultados(null);
			
			if (folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
				consultaMovimientos.getCuentaSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
			
			consultaMovimientos.getCriterioCuenta().reestablecerEstadoPaginacion();
			
			consultaMovimientos.getCriterioDivisa().reestablecerEstadoPaginacion();
			consultaMovimientos.getCriterioBoveda().reestablecerEstadoPaginacion();
			
			consultaMovimientos.reestablecerEstadoPaginacion();
			
			consultaMovimientos.setColumnaOrdenada(null);
			consultaMovimientos.setOrdenAscendente(true);
			
			setConsultaEjecutada(true);
			
			totalResultados = null;
			logger.trace("Sale de buscarMovimientos");
		}
		else {
			consultaEjecutada = false;
		}
	}

	/**
	 * Obtiene una lista con las páginas que actualmente retorna la consulta
	 * principal
	 * 
	 * @return Lista de páginas obtenidas
	 */
	public List<Integer> getListaPaginasResultado() {
		List<Integer> paginas = new ArrayList<Integer>();

		FacesContext ctx = FacesContext.getCurrentInstance();
		// Si se estn mostrando resultados de la consulta
		if (ctx.getRenderResponse() && isConsultaEjecutada()) {
			// se llena una lista con las páginas de la consulta
			for (int i = 1; i <= consultaMovimientos.getCriterioDivisa().getEstadoPaginacion().getTotalPaginas(); i++) {
				paginas.add(i);
			}
		}

		return paginas;
	}

	/**
	 * Inicializa los resultados de la consulta
	 * 
	 * @return null
	 */
	public String getInitConsulta() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx.getRenderResponse()) {
			resultados = consultaMovimientos.getPaginaDeResultados();
			consultaMovimientos.setDebeDejarBitacora(false);
			if (TipoCuentaConstants.TIPO_NOMBRADA.equals(consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getTipoCuenta().getId())) {
				asignarPantallaDetalle(resultados);
			}
			int numeroRegistrosEncontrados = 0;
			for( EstadoCuentaEfectivoPorDivisaDTO porDivisa : resultados ) {
				for( DetalleEstadoCuentaSaldoPorBovedaDTO porBoveda : porDivisa.getRegistrosContablesPorBoveda() ) {
					if( porBoveda.getRegistrosContablesControladas() != null ) {
						numeroRegistrosEncontrados += porBoveda.getRegistrosContablesControladas().size();
					}
					if( porBoveda.getRegistrosContablesNombradas() != null ) {
						numeroRegistrosEncontrados += porBoveda.getRegistrosContablesNombradas().size();
					}
				}
			}
			totalResultados = new Long(numeroRegistrosEncontrados);
			obtenerTotales(resultados);

			//Le indica al garbage collector que puede pasar.
			pasarGarbageCollector();
		}
		else {
			resultados = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
		}
		return null;
	}

	/**
	 * Calcula el total de abono y cargo de la lista de resultados
	 */
	private void obtenerTotales(List<EstadoCuentaEfectivoPorDivisaDTO> resultados) {
		BigDecimal abono = new BigDecimal(0.0);
		BigDecimal cargo = new BigDecimal(0.0);

		if ("NOMBRADA".equals(consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getOpcionSeleccionada().getDescripcion())) {
			if (resultados != null && resultados.size() > 0) {
				for (EstadoCuentaEfectivoPorDivisaDTO estadoCuentaEfectivoPorDivisaDTO : resultados) {
					for (DetalleEstadoCuentaSaldoPorBovedaDTO detalle : estadoCuentaEfectivoPorDivisaDTO.getRegistrosContablesPorBoveda()) {
						for (RegistroContableSaldoNombradaDTO nombrada : detalle.getRegistrosContablesNombradas()) {
							abono = abono.add(nombrada.getCantidadRecepcion() != null ? nombrada.getCantidadRecepcion() : new BigDecimal(0.0));
							cargo = cargo.add(nombrada.getCantidadEntrega() != null ? nombrada.getCantidadEntrega() : new BigDecimal(0.0));
						}
					}
				}
			}
		} else {
			if (resultados != null && resultados.size() > 0) {
				for (EstadoCuentaEfectivoPorDivisaDTO estadoCuentaEfectivoPorDivisaDTO : resultados) {
					for (DetalleEstadoCuentaSaldoPorBovedaDTO detalle : estadoCuentaEfectivoPorDivisaDTO.getRegistrosContablesPorBoveda()) {
						for (RegistroContableSaldoControladaDTO controlada : detalle.getRegistrosContablesControladas()) {
							abono = abono.add(controlada.getCantidadRecepcion() != null ? controlada.getCantidadRecepcion() : new BigDecimal(0.0));
							cargo = cargo.add(controlada.getCantidadEntrega() != null ? controlada.getCantidadEntrega() : new BigDecimal(0.0));
						}
					}
				}
			}
		}

		totalAbono = abono;
		totalCargo = cargo;

	}

	private void obtenerTotaleRegistros(List<RegistroContableSaldoNombradaDTO> resultados) {
		BigDecimal abono = new BigDecimal(0.0);
		BigDecimal cargo = new BigDecimal(0.0);
		
		for (RegistroContableSaldoNombradaDTO registroContableSaldoNombradaDTO : resultados) {
			abono = abono.add(registroContableSaldoNombradaDTO.getCantidadRecepcion() != null ? registroContableSaldoNombradaDTO.getCantidadRecepcion() : new BigDecimal(0.0));
			cargo = cargo.add(registroContableSaldoNombradaDTO.getCantidadEntrega() != null ? registroContableSaldoNombradaDTO.getCantidadEntrega() : new BigDecimal(0.0));
			
		}
		totalAbono = abono;
		totalCargo = cargo;	
	}

	

	/**
	 * Libera los recursos asociados a la consulta
	 * 
	 * @return null
	 */
	public String getLimpiarConsulta() {
		resultados = null;
		return null;
	}

	/**
	 * Realiza la consulta al controlador de consulta de resultados del estado
	 * de cuenta de movimientos de saldos en efectivo. La consulta es realiza
	 * siempre y cuando el ciclo de vida de la solicitud est en la fase de
	 * respuesta al cliente.
	 * 
	 * @return Lista con los resultados de la consulta
	 */
	public List<EstadoCuentaEfectivoPorDivisaDTO> getResultadosMovimientosSaldoEfectivo() {

		return resultados;
	}

	/**
	 * Recorre los resultados de la consulta de estado de cuenta y asigna a cada
	 * registro contable la ruta de la pantalla de detalle que debe mostrar.
	 * 
	 * @param listaEmisiones
	 *            Lista de registros contable agrupados por emisión y bóveda.
	 */

	private void asignarPantallaDetalle(List<EstadoCuentaEfectivoPorDivisaDTO> listaEmisiones) {
		DefinicionDetalleMovimientoDTO definicionDetalle = null;

		for (EstadoCuentaEfectivoPorDivisaDTO estadoCuentaPosicionPorDivisaDTO : listaEmisiones) {
			for (DetalleEstadoCuentaSaldoPorBovedaDTO detalleEstadoCuentaSaldoPorBovedaDTO : estadoCuentaPosicionPorDivisaDTO.getRegistrosContablesPorBoveda()) {
				if (detalleEstadoCuentaSaldoPorBovedaDTO.getRegistrosContablesNombradas() != null) {

					for (RegistroContableSaldoNombradaDTO registroContableNombradaDTO : detalleEstadoCuentaSaldoPorBovedaDTO.getRegistrosContablesNombradas()) {
						definicionDetalle = definicionDetallesMovimientosHelper.buscarDefinicionDetalleMovimiento(registroContableNombradaDTO
								.getIdTipoTipoInstruccion());
						if (definicionDetalle != null) {
							registroContableNombradaDTO.setRutaPantallaDetalle(definicionDetalle.getRutaPantallaDetalleMovimiento());
							registroContableNombradaDTO.setAltoPantallaDetalle(definicionDetalle.getAltoPantalla());
							registroContableNombradaDTO.setAnchoPantallaDetalle(definicionDetalle.getAnchoPantalla());
						}

					}

				}
			}
		}

	}

	/**
	 * Obtiene las opciones para los tipos de cuentas
	 * 
	 * @return Lista de SelectItem con los tipos de cuentas existentes en la
	 *         base
	 */
	public List<SelectItem> getOpcionesComboTipoCuenta() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoCuentaDTO nat : consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones disponibles en el catalogo de bovedas
	 * 
	 * @return Lista de SelectItem con las bovedas existentes en la base
	 */
	public List<SelectItem> getOpcionesComboBoveda() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		BovedaDTO bovedaTodos = new BovedaDTO();
		bovedaTodos.setId(-1);
		bovedaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(bovedaTodos, bovedaTodos.getDescripcion()));

		for (BovedaDTO boveda : consultaMovimientos.getCriterioBoveda().getResultados()) {
			opcionesCombo.add(new SelectItem(boveda, boveda.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Naturaleza
	 * 
	 * @return Lista con las opciones válidas del criterio de Naturaleza
	 */
	public List<SelectItem> getOpcionesComboNaturaleza() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoNaturalezaDTO nat : this.consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones disponibles en el catalogo de divisas
	 * 
	 * @return Lista de SelectItem con las divisas existentes en la base
	 */
	public List<SelectItem> getOpcionesComboDivisa() {
		return consultaCatalogos.getSelectItemsTipoDivisa();
	}

	/**
	 * Obtiene las opciones a mostrar en el combo de cuenta del participante
	 * 
	 * @return Lista de {@link SelectItem} que sirve para llenar el combo de
	 *         cuentas del participante
	 */
	public List<SelectItem> getOpcionesComboCuentaParticipante() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		CuentaEfectivoDTO cuentaTodos = new CuentaEfectivoDTO();
		cuentaTodos.setNumeroCuenta("-1");
		cuentaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

		for (CuentaEfectivoDTO cta : consultaMovimientos.getCriterioCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(cta, cta.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio tipo de instruccion
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo de
	 *         instrucción
	 */
	public List<SelectItem> getOpcionesComboTipoInstruccion() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoInstruccionDTO todosDto = new TipoInstruccionDTO();
		todosDto.setIdTipoInstruccion(-1L);
		todosDto.setNombreCorto("TODOS");

		opcionesCombo.add(new SelectItem(todosDto, todosDto.getNombreCorto()));

		for (TipoInstruccionDTO dto : consultaMovimientos.getCriterioTipoInstruccion().getResultados()) {
			opcionesCombo.add(new SelectItem(dto, dto.getIdTipoInstruccion() + " - " + dto.getNombreCorto()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio tipo de operación
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo de operación
	 */
	public List<SelectItem> getOpcionesComboTipoOperacion() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoOperacionDTO todosDto = new TipoOperacionDTO();
		todosDto.setId(-1L);
		todosDto.setClaveTipoOperacion("TODOS");

		opcionesCombo.add(new SelectItem(todosDto, todosDto.getClaveTipoOperacion()));

		for (TipoOperacionDTO dto : consultaMovimientos.getCriterioTipoOperacion().getResultados()) {
			opcionesCombo.add(new SelectItem(dto, dto.getClaveTipoOperacion()));
		}

		return opcionesCombo;
	}

	/**
	 * Reestablece a un valor nulo la opción seleccionada de la cuenta del
	 * participante.
	 * 
	 * @param e
	 */
	public void cambioSelectTipoCuenta(ActionEvent e) {
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
		consultaMovimientos.setCriterioCuentaContraparte(new CuentaEfectivoDTO());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCustodia(consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getTipoCustodia());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCuenta(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getOpcionSeleccionada());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoNaturaleza(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().getOpcionSeleccionada());
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(-1);
		consultaMovimientos.getCriterioCuentaContraparte().setInstitucion(institucion);
		consultaMovimientos.getCriterioCuentaContraparte().setDescripcion("TODAS");
		consultaMovimientos.getCriterioCuentaContraparte().setNumeroCuenta("-1");
	}

	/**
	 * Reestablece a un valor nulo la opción seleccionada de la cuenta del
	 * participante.
	 * 
	 * @param e
	 */
	public void cambioSelectNaturaleza(ActionEvent e) {
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
		consultaMovimientos.setCriterioCuentaContraparte(new CuentaEfectivoDTO());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCustodia(consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getTipoCustodia());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoCuenta(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getOpcionSeleccionada());
		consultaMovimientos.getCriterioCuentaContraparte().setTipoNaturaleza(
				consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().getOpcionSeleccionada());
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(-1);
		consultaMovimientos.getCriterioCuentaContraparte().setInstitucion(institucion);
		consultaMovimientos.getCriterioCuentaContraparte().setDescripcion("TODAS");
		consultaMovimientos.getCriterioCuentaContraparte().setNumeroCuenta("-1");
	}

	/**
	 * Obtiene el campo consultaEjecutada
	 * 
	 * @return consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * Asigna el valor del campo consultaEjecutada
	 * 
	 * @param consultaEjecutada
	 *            el valor de consultaEjecutada a asignar
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * Obtiene el campo consultaMovimientos
	 * 
	 * @return consultaMovimientos
	 */
	public ConsultaMovimientosEfectivo getConsultaMovimientos() {
		return consultaMovimientos;
	}

	/**
	 * Asigna el valor del campo consultaMovimientos
	 * 
	 * @param consultaMovimientos
	 *            el valor de consultaMovimientos a asignar
	 */
	public void setConsultaMovimientos(ConsultaMovimientosEfectivo consultaMovimientos) {
		this.consultaMovimientos = consultaMovimientos;
	}

	/**
	 * Obtiene el campo cuentaContraparteSeleccionada
	 * 
	 * @return cuentaContraparteSeleccionada
	 */
	public String getCuentaSeleccionada() {
		String cuenta = null;
		List<CuentaEfectivoDTO> resultados = null;
		if (getInstitucionActual().getId() > 0) {
			resultados = consultaMovimientos.getCriterioCuenta().getResultados();
		}

		// Si existe solo una cuenta, colocarla como predeterminada
		if (resultados != null && resultados.size() == 1) {
			consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
		}
		cuenta = consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS" : consultaMovimientos
				.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();
		// Si existe institución activa se deben de omitir los caracteres del
		// folio y el tipo de institución
		if (getInstitucionActual().getId() > 0) {
			cuenta = cuenta.replace(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion(), StringUtils.EMPTY);
		}
		return cuenta;
	}

	/**
	 * Asigna el valor del campo cuentaContraparteSeleccionada
	 * 
	 * @param cuentaContraparteSeleccionada
	 *            el valor de cuentaContraparteSeleccionada a asignar
	 */
	public void setCuentaSeleccionada(String cuentaSeleccionada) {
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
		if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
			if (!"TODAS".equals(cuentaSeleccionada) && !"TODA".equals(cuentaSeleccionada)) {
				if (!isUsuarioIndeval()) {
					cuentaSeleccionada = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				} else {
					cuentaSeleccionada = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
				CuentaDTO cuenta = new CuentaDTO();
				cuenta.setNumeroCuenta(cuentaSeleccionada);
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(cuentaSeleccionada);
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(cuentaSeleccionada);
			}

		}

	}

	public void convierteValor(ActionEvent event) {
		
	}

	/**
	 * Busca cuentas en el catálogo cuyo número de cuenta comiencen con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link CuentaDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<CuentaEfectivoDTO> buscarCuentasPorPrefijo(Object value) {
		List<CuentaEfectivoDTO> cuentas = new ArrayList<CuentaEfectivoDTO>();

		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}

		if (value != null && consultaMovimientos.getCriterioCuenta().getIdInstitucion() != -1) {

			CuentaEfectivoDTO criterio = consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada();
			if (!isUsuarioIndeval()) {
				criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
			} else {
				criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
			}
			
			if(folioClaveInstitucion.length() > 2) {
				cuentas = consultaMovimientos.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}
				
			for (CuentaEfectivoDTO cuentaDTO : cuentas) {
				cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
			}

		}
		return cuentas;
	}

	/**
	 * Busca las cuentas nombradas de valores cuyo número de cuenta comienza con
	 * el prefijo proporcionado.
	 * 
	 * @param value
	 *            el prefijo a consultar.
	 * @return una lista con las cuentas que cumplen con el criterio de
	 *         consulta.
	 */
	public List<CuentaDTO> buscarCuentasValoresPorPrefijo(Object value) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		String prefijoAjustado = "";

		if (value != null && StringUtils.isNotBlank(institucionContraparteSeleccionada)) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
			CuentaDTO criterio = new CuentaDTO();
			criterio.setTipoTenencia(new TipoTenenciaDTO());
			criterio.getTipoTenencia().setTipoCuenta(consultaMovimientos.getCriterioCuenta().getCriterioTipoCuenta().getOpcionSeleccionada());
			criterio.getTipoTenencia().setTipoNaturaleza(consultaMovimientos.getCriterioCuenta().getCriterioTipoNaturaleza().getOpcionSeleccionada());
			criterio.getTipoTenencia().setTipoCustodia("V");
			criterio.setNumeroCuenta(institucionContraparteSeleccionada + prefijoAjustado);
			cuentas = consultaCatalogos.getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
		}

		return cuentas;
	}

	/**
	 * Obtiene el campo institucionContraparteSeleccionada
	 * 
	 * @return institucionContraparteSeleccionada
	 */
	public String getInstitucionContraparteSeleccionada() {
		return institucionContraparteSeleccionada != null ? institucionContraparteSeleccionada : "TODAS";
	}

	/**
	 * Asigna el valor del campo institucionContraparteSeleccionada
	 * 
	 * @param institucionContraparteSeleccionada
	 *            el valor de institucionContraparteSeleccionada a asignar
	 */
	public void setInstitucionContraparteSeleccionada(String institucionContraparteSeleccionada) {
		this.institucionContraparteSeleccionada = institucionContraparteSeleccionada;

		if (institucionContraparteSeleccionada.length() >= 5) {
			this.institucionContraparteSeleccionada = institucionContraparteSeleccionada.substring(0, 5);

			institucionConsultadaContraparte = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.institucionContraparteSeleccionada);
			if (institucionConsultadaContraparte != null) {
				consultaMovimientos.getCriterioCuentaContraparte().getInstitucion().setId(institucionConsultadaContraparte.getId());

			}
		} else {
			consultaMovimientos.getCriterioCuentaContraparte().getInstitucion().setId(-1);
			if (institucionContraparteSeleccionada.length() == 2) {
				consultaMovimientos.getCriterioCuentaContraparte().getInstitucion().setClaveTipoInstitucion(institucionContraparteSeleccionada);
			}
		}
	}

	/**
	 * Obtiene el campo definicionDetallesMovimientosHelper
	 * 
	 * @return definicionDetallesMovimientosHelper
	 */
	public DefinicionDetallesMovimientosHelper getDefinicionDetallesMovimientosHelper() {
		return definicionDetallesMovimientosHelper;
	}

	/**
	 * Asigna el valor del campo definicionDetallesMovimientosHelper
	 * 
	 * @param definicionDetallesMovimientosHelper
	 *            el valor de definicionDetallesMovimientosHelper a asignar
	 */
	public void setDefinicionDetallesMovimientosHelper(DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper) {
		this.definicionDetallesMovimientosHelper = definicionDetallesMovimientosHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.BackingBeanBase#llenarParametros()
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(ReportesConstants.CUENTA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.NATURALEZA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getTipoNaturaleza().getDescripcion());
		parametros.put(ReportesConstants.TIPO_CUENTA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getTipoCuenta().getDescripcion());
		parametros.put(ReportesConstants.ROL_PARTICIPANTE_PARAMETER, consultaMovimientos.getDescripcionRolParticipante());
		parametros.put(ReportesConstants.ROL_CONTRAPARTE_PARAMETER, consultaMovimientos.getDescripcionRolContraparte());

		parametros.put(ReportesConstants.DIVISA_PARAMETER, consultaMovimientos.getCriterioDivisa().getOpcionSeleccionada().getDescripcion());

		parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaMovimientos.getCriterioBoveda().getOpcionSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.FECHA_INICIAL_PARAMETER, consultaMovimientos.getCriterioFechaInicial());
		parametros.put(ReportesConstants.FECHA_FINAL_PARAMETER, consultaMovimientos.getCriterioFechaFinal());

		parametros.put(ReportesConstants.TIPO_INSTRUCCION_PARAMETER, consultaMovimientos.getCriterioTipoInstruccion().getOpcionSeleccionada().getNombreCorto());
		parametros.put(ReportesConstants.TIPO_OPERACION_PARAMETER, consultaMovimientos.getCriterioTipoOperacion().getOpcionSeleccionada()
				.getClaveTipoOperacion());
		parametros.put(ReportesConstants.FOLIO_PARAMETER, consultaMovimientos.getCriterioFolioInstruccion());

		return parametros;
	}

	/**
	 * método para generar un reporte con formato XLS o PDF
	 * 
	 * @return cadena de para ejecutar el action para genera el reporte
	 */
	public void generarReportes(ActionEvent e) {
		reiniciarEstadoPeticion();
		String reporte = (String) e.getComponent().getAttributes().get("reporte");

		if ("movimientosEfectivoExcel".equals(reporte) || "movimientosEfectivoTexto".equals(reporte)) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(consultaMovimientos.getCuentaSeleccionada().getTipoCuenta().getId())) {
				resultadosControladasReporteXls = consultaMovimientos.getResultadosControladasSinAgrupar();
				if( resultadosControladasReporteXls != null)
	    		{
					System.out.println("resultadosControladasReporteXls : " + resultadosControladasReporteXls.size());
	    		}
			} else {
				resultadosNombradasReporteXls = consultaMovimientos.getResultadosNombradasSinAgrupar();
				if( resultadosNombradasReporteXls != null)
	    		{
					System.out.println("resultadosNombradasReporteXls : " + resultadosNombradasReporteXls.size());
	    		}
			}
			if(resultadosNombradasReporteXls!=null){
				totalResultados = new Long(resultadosNombradasReporteXls.size());
				obtenerTotaleRegistros(resultadosNombradasReporteXls);
			}	
		} else {
			resultados = consultaMovimientos.getResultados();
			if(resultados!=null){
				if( resultados != null)
	    		{
					System.out.println("resultados Movimientos saldo efectivo : " + resultados.size());
	    		}
				int numeroRegistrosEncontrados = 0;				
				for( EstadoCuentaEfectivoPorDivisaDTO porDivisa : resultados ) {
					for( DetalleEstadoCuentaSaldoPorBovedaDTO porBoveda : porDivisa.getRegistrosContablesPorBoveda() ) {
						if( porBoveda.getRegistrosContablesControladas() != null ) {
							numeroRegistrosEncontrados += porBoveda.getRegistrosContablesControladas().size();
		}
						if( porBoveda.getRegistrosContablesNombradas() != null ) {
							numeroRegistrosEncontrados += porBoveda.getRegistrosContablesNombradas().size();
						}
					}
				}			
				totalResultados = new Long(numeroRegistrosEncontrados);
				obtenerTotales(resultados);
			}		
		}		
		consultaMovimientos.setDebeDejarBitacora(false);		

		//Le indica al garbage collector que puede pasar.
		pasarGarbageCollector();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		return (Collection<? extends Object>) consultaMovimientos.getResultados();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
	 */
	@Override
	protected String getNombreReporte() {

		return ReportesConstants.REPORTE_CONSULTA_MOVIMIENTOS_SALDOS;
	}

	/**
	 * Obtiene el valor del atributo restaurarPaginacionResultados
	 * 
	 * @return el valor del atributo restaurarPaginacionResultados
	 */
	public boolean isRestaurarPaginacionResultados() {
		return restaurarPaginacionResultados;
	}

	/**
	 * Establece el valor del atributo restaurarPaginacionResultados
	 * 
	 * @param restaurarPaginacionResultados
	 *            el valor del atributo restaurarPaginacionResultados a
	 *            establecer.
	 */
	public void setRestaurarPaginacionResultados(boolean restaurarPaginacionResultados) {
		this.restaurarPaginacionResultados = restaurarPaginacionResultados;
	}

	/**
	 * Obtiene el valor del atributo consultaCatalogos
	 * 
	 * @return el valor del atributo consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Establece el valor del atributo consultaCatalogos
	 * 
	 * @param consultaCatalogos
	 *            el valor del atributo consultaCatalogos a establecer.
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Obtiene el valor del atributo totalResultados
	 * 
	 * @return el valor del atributo totalResultados
	 */
	public Long getTotalResultados() {
		return totalResultados;
	}

	/**
	 * Establece el valor del atributo totalResultados
	 * 
	 * @param totalResultados
	 *            el valor del atributo totalResultados a establecer.
	 */
	public void setTotalResultados(Long totalResultados) {
		this.totalResultados = totalResultados;
	}

	/**
	 * Obtiene el valor del atributo totalAbono
	 * 
	 * @return el valor del atributo totalAbono
	 */
	public BigDecimal getTotalAbono() {
		return totalAbono;
	}

	/**
	 * Establece el valor del atributo totalAbono
	 * 
	 * @param totalAbono
	 *            el valor del atributo totalAbono a establecer.
	 */
	public void setTotalAbono(BigDecimal totalAbono) {
		this.totalAbono = totalAbono;
	}

	/**
	 * Obtiene el valor del atributo totalCargo
	 * 
	 * @return el valor del atributo totalCargo
	 */
	public BigDecimal getTotalCargo() {
		return totalCargo;
	}

	/**
	 * Establece el valor del atributo totalCargo
	 * 
	 * @param totalCargo
	 *            el valor del atributo totalCargo a establecer.
	 */
	public void setTotalCargo(BigDecimal totalCargo) {
		this.totalCargo = totalCargo;
	}

	/**
	 * Obtiene el valor del atributo resultadosControladasReporteXls
	 * 
	 * @return el valor del atributo resultadosControladasReporteXls
	 */
	public List<RegistroContableSaldoControladaDTO> getResultadosControladasReporteXls() {
		return resultadosControladasReporteXls;
	}

	/**
	 * Establece el valor del atributo resultadosControladasReporteXls
	 * 
	 * @param resultadosControladasReporteXls
	 *            el valor del atributo resultadosControladasReporteXls a
	 *            establecer.
	 */
	public void setResultadosControladasReporteXls(List<RegistroContableSaldoControladaDTO> resultadosControladasReporteXls) {
		this.resultadosControladasReporteXls = resultadosControladasReporteXls;
	}

	/**
	 * Obtiene el valor del atributo resultadosNombradasReporteXls
	 * 
	 * @return el valor del atributo resultadosNombradasReporteXls
	 */
	public List<RegistroContableSaldoNombradaDTO> getResultadosNombradasReporteXls() {
		return resultadosNombradasReporteXls;
	}

	/**
	 * Establece el valor del atributo resultadosNombradasReporteXls
	 * 
	 * @param resultadosNombradasReporteXls
	 *            el valor del atributo resultadosNombradasReporteXls a
	 *            establecer.
	 */
	public void setResultadosNombradasReporteXls(List<RegistroContableSaldoNombradaDTO> resultadosNombradasReporteXls) {
		this.resultadosNombradasReporteXls = resultadosNombradasReporteXls;
	}

	/**
	 * Obtiene el valor del atributo folioClaveInstitucion
	 * 
	 * @return el valor del atributo folioClaveInstitucion
	 */
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	/**
	 * Establece el valor del atributo folioClaveInstitucion
	 * 
	 * @param folioClaveInstitucion
	 *            el valor del atributo folioClaveInstitucion a establecer.
	 */
	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;

		if (folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0, 5);

			institucionConsultada = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if (institucionConsultada != null) {
				consultaMovimientos.getCriterioCuenta().setIdInstitucion(institucionConsultada.getId());

			}
		} else {
			consultaMovimientos.getCriterioCuenta().setIdInstitucion(-1);
			if (folioClaveInstitucion.length() == 2) {
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
	}

	/**
	 * Obtiene el valor del atributo institucionConsultada
	 * 
	 * @return el valor del atributo institucionConsultada
	 */
	public InstitucionDTO getInstitucionConsultada() {
		return institucionConsultada;
	}

	/**
	 * Establece el valor del atributo institucionConsultada
	 * 
	 * @param institucionConsultada
	 *            el valor del atributo institucionConsultada a establecer.
	 */
	public void setInstitucionConsultada(InstitucionDTO institucionConsultada) {
		this.institucionConsultada = institucionConsultada;
	}

	/**
	 * Obtiene el valor del atributo resultados
	 * 
	 * @return el valor del atributo resultados
	 */
	public List<EstadoCuentaEfectivoPorDivisaDTO> getResultados() {
		return resultados;
	}

	/**
	 * Establece el valor del atributo resultados
	 * 
	 * @param resultados
	 *            el valor del atributo resultados a establecer
	 */
	public void setResultados(List<EstadoCuentaEfectivoPorDivisaDTO> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccionDaliDAO
	 * 
	 * @return el valor del atributo tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * Establece el valor del atributo tipoInstruccionDaliDAO
	 * 
	 * @param tipoInstruccionDaliDAO
	 *            el valor del atributo tipoInstruccionDaliDAO a establecer
	 */
	public void setTipoInstruccionDAO(TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}

	public InstitucionDTO getInstitucionConsultadaContraparte() {
		return institucionConsultadaContraparte;
	}

	public void setInstitucionConsultadaContraparte(InstitucionDTO institucionConsultadaContraparte) {
		this.institucionConsultadaContraparte = institucionConsultadaContraparte;
	}

	/**
	 * @param opcionesTipoRetiro the opcionesTipoRetiro to set
	 */
	public void setOpcionesTipoRetiro(List opcionesTipoRetiro) {
		this.opcionesTipoRetiro = opcionesTipoRetiro;
	}
}
