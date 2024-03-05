package com.indeval.portaldali.presentation.controller.consultaMiscelaneaFiscal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Clase (Bean) que realiza la consulta de Miscelanea Fiscal
 * 
 * @author Cesar Hernandez
 *
 */
public class ConsultaMiscelaneaFiscalBean extends ControllerBase {
	private boolean consultaEjecutada = false;
	private boolean editarTraspasante = Boolean.TRUE;
	private Boolean guardaBitacoraConsulta = Boolean.FALSE;

	private List<OperacionValorMatchDTO> listaMiscelaneaFiscal = null;

	private List<OperacionValorMatchDTO> listaMiscelaneaFiscalReportes = null;

	private CriterioMatchOperacionesDTO criterio;

	private EstadoPaginacionDTO estadoPaginacion = new EstadoPaginacionDTO();

	private ConsultaEstatusOperacionesMatchService consultaOperacionesMatchService;

	private String idFolioTraspasante = "-1";
	private String idFolioContraparte = "";

	private String sRol = "";

	private int totalRegistros;
	/** Contiene el mercado por el cual se realizará la consulta */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/**
	 * Acceso a la consulta de catálogos desde la capa de vista
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;

	/**
	 * Inicializa el Bean
	 * 
	 * @return null
	 */
	public String getInit() {
		guardaBitacoraConsulta = Boolean.FALSE;
		if (idFolioTraspasante.equals("-1")) {
			idFolioTraspasante = getInstitucionActual()
					.getClaveTipoInstitucion()
					+ getInstitucionActual().getFolioInstitucion();
		}
		editarTraspasante = isUsuarioIndeval();

		if (idFolioContraparte.equals("-1")) {
			idFolioContraparte = getInstitucionActual()
					.getClaveTipoInstitucion()
					+ getInstitucionActual().getFolioInstitucion();
		}
		if (criterio == null) {
			criterio = new CriterioMatchOperacionesDTO();
		}

		// participante
		if (criterio.getInstitucionParticipante() == null) {
			criterio.setInstitucionParticipante(new InstitucionDTO());
		}

		criterio.setInstitucionParticipante(consultaCatalogos
				.buscarInstitucionPorIdFolio(getIdFolioTraspasante()));

		if (criterio.getCuentaParticipante() == null) {
			criterio.setCuentaParticipante(new CuentaDTO());
		} else {
			if (criterio.getCuentaParticipante().getCuenta() != null) {
				CuentaDTO cnt = consultaCatalogos
						.buscarCuentaPorNumeroCuentaNullSiNoExiste(getIdFolioTraspasante()
								+ criterio.getCuentaParticipante().getCuenta());
				if (cnt != null) {
					criterio.getCuentaParticipante().setNombreCuenta(
							cnt.getNombreCuenta());
				} else {
					// criterio.getCuentaParticipante().setNumeroCuenta(StringUtils.EMPTY);
					criterio.getCuentaParticipante().setNombreCuenta(
							StringUtils.EMPTY);
				}
			}
		}

		// contraparte
		if (criterio.getInstitucionContraparte() == null) {
			criterio.setInstitucionContraparte(new InstitucionDTO());
		}

		if (StringUtils.isNotBlank(getIdFolioContraparte())) {
			criterio.setInstitucionContraparte(consultaCatalogos
					.buscarInstitucionPorIdFolio(getIdFolioContraparte()));
		}

		if (criterio.getCuentaContraparte() == null) {
			criterio.setCuentaContraparte(new CuentaDTO());
		} else {
			if (criterio.getCuentaContraparte().getCuenta() != null) {
				CuentaDTO cnt = consultaCatalogos
						.buscarCuentaPorNumeroCuentaNullSiNoExiste(getIdFolioContraparte()
								+ criterio.getCuentaContraparte().getCuenta());
				if (cnt != null) {
					criterio.getCuentaContraparte().setNombreCuenta(
							cnt.getNombreCuenta());
				} else {
					criterio.getCuentaContraparte().setNombreCuenta(
							StringUtils.EMPTY);
				}
			}
		}

		if (criterio.getEmision() == null) {
			criterio.setEmision(new EmisionDTO());
		} else {
			if (criterio.getEmision().getTipoValor() == null)
				criterio.getEmision().setTipoValor(new TipoValorDTO());

			if (criterio.getEmision().getSerie() == null)
				criterio.getEmision().setSerie(new SerieDTO());

			if (criterio.getEmision().getEmisora() == null)
				criterio.getEmision().setEmisora(new EmisoraDTO());
		}

		if (criterio.getEstadoInstruccion() == null) {
			criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		}

		if (criterio.getTipoInstruccion() == null)
			criterio.setTipoInstruccion(new TipoInstruccionDTO());

		criterio.getTipoInstruccion().setIdTipoInstruccion((long) 38);// TLPF

		if (!isConsultaEjecutada()) {
			criterio.setFechaInicioPeriodo(new Date());
			criterio.setFechaFinPeriodo(new Date());
		}

		return null;
	}

	/**
	 * Regresa los resultados de la consulta
	 * 
	 * @return
	 */
	public List<OperacionValorMatchDTO> getResultadosMiscelaneaFiscal() {
		if (listaMiscelaneaFiscal == null) {
			listaMiscelaneaFiscal = new ArrayList<OperacionValorMatchDTO>();
		}
		return listaMiscelaneaFiscal;
	}

	/**
	 * Action de buscar miscelanaeas fiscal
	 * 
	 * @param evt
	 */
	public void buscarMiscelaneaFiscal() {
		if (validarFechaFinalVsFechaInicial(criterio.getFechaInicioPeriodo(),
				criterio.getFechaFinPeriodo())) {
			if (estadoPaginacion == null) {
				estadoPaginacion = new EstadoPaginacionDTO();
			} else {
				estadoPaginacion.setNumeroPagina(1);

				if (estadoPaginacion.getRegistrosPorPagina() <= 0)
					estadoPaginacion.setRegistrosPorPagina(50);
				if (estadoPaginacion.getNumeroPagina() <= 0)
					estadoPaginacion.setNumeroPagina(1);
			}

			switch (criterio.getRol()) {
			case 0:
				setSRol("AMBOS");
				break;
			case 1:
				setSRol("TRASPASANTE");
				break;
			case 2:
				setSRol("RECEPTOR");
				break;
			}

			setConsultaEjecutada(true);
			ejecutarConsulta();
		} else {
			consultaEjecutada = false;
		}
	}

	/**
	 * Limpia los filtros de busqueda
	 * 
	 * @param evt
	 */
	public void limpiar(ActionEvent evt) {
		setIdFolioTraspasante("-1");
		setIdFolioContraparte("");
		guardaBitacoraConsulta = Boolean.FALSE;
		if (criterio.getCuentaParticipante() != null) {
			criterio.getCuentaParticipante().setCuenta(StringUtils.EMPTY);
			criterio.getCuentaParticipante().setNombreCuenta(StringUtils.EMPTY);
		}

		if (criterio.getCuentaContraparte() != null) {
			criterio.getCuentaContraparte().setCuenta(StringUtils.EMPTY);
			criterio.getCuentaContraparte().setNombreCuenta(StringUtils.EMPTY);
		}

		if (criterio.getEmision() != null) {
			if (criterio.getEmision().getTipoValor() != null
					&& criterio.getEmision().getTipoValor().getClaveTipoValor() != null)
				criterio.getEmision().getTipoValor()
						.setClaveTipoValor(StringUtils.EMPTY);
			if (criterio.getEmision().getEmisora() != null)
				criterio.getEmision().getEmisora()
						.setDescripcion(StringUtils.EMPTY);
			if (criterio.getEmision().getSerie() != null)
				criterio.getEmision().getSerie().setSerie(StringUtils.EMPTY);
		}

		if (criterio.getEstadoInstruccion() != null) {
			criterio.getEstadoInstruccion().setClaveEstadoInstruccion(
					StringUtils.EMPTY);
		}

		if (criterio.getMercado() != null) {
			criterio.setMercado(new MercadoDTO());
			setPapelMercado(StringUtils.EMPTY);
		}

		criterio.setFechaInicioPeriodo(null);
		criterio.setFechaFinPeriodo(null);
		criterio.setRol(0);
		setSRol(StringUtils.EMPTY);

		setConsultaEjecutada(false);

		estadoPaginacion = new EstadoPaginacionDTO();
	}

	/**
	 * Setea valores a los objetos y ejecuta la consulta de Miscelanea Fiscal
	 */
	private void ejecutarConsulta() {
		boolean ejecuta = true;

		if (isUsuarioIndeval() && StringUtils.isBlank(getIdFolioTraspasante())) {
			criterio.setInstitucionParticipante(new InstitucionDTO());
			criterio.getInstitucionParticipante().setId(-1);
		} else {
			criterio.setInstitucionParticipante(consultaCatalogos
					.buscarInstitucionPorIdFolio(getIdFolioTraspasante()));
			if (criterio.getInstitucionParticipante() == null) {
				mensajeUsuario = "La Instituci\u00f3n capturada no existe";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								mensajeUsuario, mensajeUsuario));

				ejecuta = false;
			}
		}

		if (StringUtils.isBlank(getIdFolioContraparte())) {
			criterio.setInstitucionContraparte(new InstitucionDTO());
			criterio.getInstitucionContraparte().setId(-1);
		} else {
			criterio.setInstitucionContraparte(consultaCatalogos
					.buscarInstitucionPorIdFolio(getIdFolioContraparte()));
			if (criterio.getInstitucionContraparte() == null) {
				mensajeUsuario = "La Instituci\u00f3n capturada no existe";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								mensajeUsuario, mensajeUsuario));

				ejecuta = false;
			}
		}

		if (criterio != null) {
			// cuenta participante
			if (criterio.getCuentaParticipante() != null) {
				if (criterio.getCuentaParticipante().getCuenta() != null
						&& criterio.getCuentaParticipante().getNombreCuenta() != null)
					if (criterio.getCuentaParticipante().getCuenta()
							.equals(StringUtils.EMPTY)
							&& criterio.getCuentaParticipante()
									.getNombreCuenta()
									.equals(StringUtils.EMPTY))
						criterio.setCuentaParticipante(null);
			}

			// cuenta contraparte
			if (criterio.getCuentaContraparte() != null) {
				if (criterio.getCuentaContraparte().getCuenta() != null
						&& criterio.getCuentaContraparte().getNombreCuenta() != null)
					if (criterio.getCuentaContraparte().getCuenta()
							.equals(StringUtils.EMPTY)
							&& criterio.getCuentaContraparte()
									.getNombreCuenta()
									.equals(StringUtils.EMPTY))
						criterio.setCuentaContraparte(null);
			}

			// emision
			if (criterio.getEmision() != null) {
				if (criterio.getEmision().getTipoValor() != null) {
					if (criterio.getEmision().getTipoValor()
							.getClaveTipoValor() != null) {
						if (StringUtils.isBlank(criterio.getEmision()
								.getTipoValor().getClaveTipoValor())) {
							criterio.getEmision().setTipoValor(null);
						} else {
							TipoValorDTO tipoValor = consultaCatalogos
									.buscarTipoValorPorClave(criterio
											.getEmision().getTipoValor()
											.getClaveTipoValor().toUpperCase());
							if (tipoValor.getId() != DaliConstants.VALOR_COMBO_TODOS) {
								criterio.getEmision().setTipoValor(tipoValor);
							} else {
								mensajeUsuario = "El Tipo Valor capturado no existe";
								FacesContext
										.getCurrentInstance()
										.addMessage(
												null,
												new FacesMessage(
														FacesMessage.SEVERITY_ERROR,
														mensajeUsuario,
														mensajeUsuario));
								ejecuta = false;
							}
						}
					}
				}

				if (criterio.getEmision().getSerie() != null)
					if (criterio.getEmision().getSerie().getSerie()
							.equals(StringUtils.EMPTY))
						criterio.getEmision().setSerie(null);

				if (criterio.getEmision().getEmisora() != null)
					if (criterio.getEmision().getEmisora().getDescripcion()
							.equals(StringUtils.EMPTY))
						criterio.getEmision().setEmisora(null);
					else {
						criterio.getEmision()
								.setEmisora(
										consultaCatalogos
												.buscarEmisoraPorNombreCorto(criterio
														.getEmision()
														.getEmisora()
														.getDescripcion()
														.toUpperCase()));

						if (criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_TODOS) {
							ejecuta = false;
						}
					}

			}
			// estado instruccion
			if (criterio.getEstadoInstruccion() != null) {
				if (criterio.getEstadoInstruccion().getClaveEstadoInstruccion()
						.equals(StringUtils.EMPTY))
					criterio.setEstadoInstruccion(null);
				else {
					criterio.getEstadoInstruccion().setIdEstadoInstruccion(
							consultaCatalogos.buscarEstadoInstruccionPorClave(
									criterio.getEstadoInstruccion()
											.getClaveEstadoInstruccion())
									.getIdEstadoInstruccion());

					if (criterio.getEstadoInstruccion()
							.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
						ejecuta = false;
					}
				}
			}

			// boveda
			if (criterio.getBovedaValores() != null
					&& criterio.getBovedaValores().getId() > 0) {
				criterio.setBovedaValores(consultaCatalogos
						.buscarBovedaPorId(criterio.getBovedaValores().getId()));
			}

			// mercado
			if (DaliConstants.ID_MERCADO_DINERO != criterio.getMercado()
					.getId()) {
				criterio.setMercado(consultaCatalogos
						.buscarMercadoPorId(criterio.getMercado().getId()));
			} else {
				criterio.getMercado().setClaveMercado(
						DaliConstants.DESCRIPCION_MERCADO_DINERO);
			}

		}// if criterio != null

		if (ejecuta) {
			listaMiscelaneaFiscal = consultaOperacionesMatchService
					.consultarOperacionesMiscelaneaFiscal(criterio,
							estadoPaginacion, guardaBitacoraConsulta);
			guardaBitacoraConsulta = Boolean.FALSE;
		} else {
			listaMiscelaneaFiscal.clear();
		}

		getInit();
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<EstadoInstruccionDTO> buscarEstadoInstruccionPorPrefijo(
			Object prefijo) {
		return consultaCatalogos.buscarEstadosInstruccionPorIds(prefijo
				.toString());
	}

	public void generarReportes(ActionEvent evt) {
		reiniciarEstadoPeticion();
		if (criterio != null) {
			// cuenta
			if (criterio.getCuentaParticipante() != null) {
				if (criterio.getCuentaParticipante().getNumeroCuenta() != null
						&& criterio.getCuentaParticipante().getNombreCuenta() != null)
					if (criterio.getCuentaParticipante().getNumeroCuenta()
							.equals(StringUtils.EMPTY)
							&& criterio.getCuentaParticipante()
									.getNombreCuenta()
									.equals(StringUtils.EMPTY))
						criterio.setCuentaParticipante(null);
			}

			// emision
			if (criterio.getEmision() != null) {
				if (criterio.getEmision().getTipoValor() != null)
					if (criterio.getEmision().getTipoValor()
							.getClaveTipoValor() != null)
						if (criterio.getEmision().getTipoValor()
								.getClaveTipoValor().equals(StringUtils.EMPTY))
							criterio.getEmision().setTipoValor(null);
						else
							criterio.getEmision().setTipoValor(
									consultaCatalogos
											.buscarTipoValorPorClave(criterio
													.getEmision()
													.getTipoValor()
													.getClaveTipoValor()
													.toUpperCase()));

				if (criterio.getEmision().getSerie() != null)
					if (criterio.getEmision().getSerie().getSerie()
							.equals(StringUtils.EMPTY))
						criterio.getEmision().setSerie(null);

				if (criterio.getEmision().getEmisora() != null)
					if (criterio.getEmision().getEmisora().getDescripcion()
							.equals(StringUtils.EMPTY))
						criterio.getEmision().setEmisora(null);
					else {
						criterio.getEmision()
								.setEmisora(
										consultaCatalogos
												.buscarEmisoraPorNombreCorto(criterio
														.getEmision()
														.getEmisora()
														.getDescripcion()
														.toUpperCase()));
					}

			}

			// estado instruccion
			if (criterio.getEstadoInstruccion() != null) {
				if (criterio.getEstadoInstruccion().getClaveEstadoInstruccion()
						.equals(StringUtils.EMPTY))
					criterio.setEstadoInstruccion(null);
				else {
					criterio.getEstadoInstruccion().setIdEstadoInstruccion(
							consultaCatalogos.buscarEstadoInstruccionPorClave(
									criterio.getEstadoInstruccion()
											.getClaveEstadoInstruccion())
									.getIdEstadoInstruccion());
				}
			}

			// boveda
			if (criterio.getBovedaValores() != null
					&& criterio.getBovedaValores().getId() > 0) {
				criterio.setBovedaValores(consultaCatalogos
						.buscarBovedaPorId(criterio.getBovedaValores().getId()));
			}

			// mercado
			if (DaliConstants.ID_MERCADO_DINERO != criterio.getMercado()
					.getId()) {
				criterio.setMercado(consultaCatalogos
						.buscarMercadoPorId(criterio.getMercado().getId()));
			} else {
				criterio.getMercado().setClaveMercado(
						DaliConstants.DESCRIPCION_MERCADO_DINERO);
			}
		}// if criterio != null

		listaMiscelaneaFiscalReportes = consultaOperacionesMatchService
				.consultarOperacionesMiscelaneaFiscal(criterio, null, Boolean.FALSE);

		totalRegistros = listaMiscelaneaFiscalReportes.size();

		getInit();
	}

	/**
	 * Obtiene los elementos SelectItem para el combo de mercado
	 *
	 * @return los elementos SelectItem para el combo de mercado
	 */
	public List<SelectItem> getSelectItemsMercado() {
		List<SelectItem> resultado = null;

		if (StringUtils.isBlank(papelMercado)
				|| DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = consultaCatalogos.getSelectItemMercadoTodos();
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = consultaCatalogos.getSelectItemsDeMercadoDinero();
			} else {
				resultado = consultaCatalogos
						.getSelectItemsDeMercadoCapitales();
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
	 * Obtiene la descripción del atributo papelMercado.
	 *
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado2() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				if (criterio.getMercado() != null
						&& criterio.getMercado().getClaveMercado() != null
						&& DaliConstants.ID_MERCADO_DINERO == criterio
								.getMercado().getId()) {
					resultado = DaliConstants.DESCRIPCION_TODOS;
				} else {
					resultado = criterio.getMercado().getClaveMercado();
				}
			} else {
				resultado = DaliConstants.DESCRIPCION_TODOS;
			}
		}
		return resultado;
	}

	/**
	 * Obtiene la descripción del atributo papelMercado.
	 *
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercadoTitle2() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				if (criterio.getMercado() != null
						&& criterio.getMercado().getClaveMercado() != null
						&& DaliConstants.ID_MERCADO_DINERO == criterio
								.getMercado().getId()) {
					resultado = DaliConstants.DESCRIPCION_TODOS;
				} else {
					resultado = criterio.getMercado().getDescripcion();
				}
			} else {
				resultado = DaliConstants.DESCRIPCION_TODOS;
			}
		}

		return resultado;
	}

	/**
	 * Obtiene de la sesión Web del usuario la institución actual del
	 * participante.
	 * 
	 * @return DTO con los datos de la institución.
	 */
	public InstitucionDTO getInstitucionActual() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		InstitucionDTO institucionActual = (InstitucionDTO) ((HttpSession) ctx
				.getExternalContext().getSession(false))
				.getAttribute(SeguridadConstants.INSTITUCION_ACTUAL);

		return institucionActual;
	}

	public String avanzarPagina() {
		estadoPaginacion.avanzar(1);

		ejecutarConsulta();

		return null;
	}

	public String irPrimeraPagina() {
		estadoPaginacion.irAlPrincipio();

		ejecutarConsulta();

		return null;
	}

	public String retrocederPagina() {
		estadoPaginacion.retrocederPagina();

		ejecutarConsulta();

		return null;
	}

	public String irUltimaPagina() {
		estadoPaginacion.irAlUltimo();

		ejecutarConsulta();

		return null;
	}

	public String avanzarPaginasRapido() {
		estadoPaginacion.avanzarPaginasRapido();

		ejecutarConsulta();

		return null;
	}

	public String retrocederPaginasRapido() {
		estadoPaginacion.retrocederPaginasRapido();

		ejecutarConsulta();

		return null;
	}

	/**
	 * Indica si existen resultados para la consulta.
	 * @return true si la condicion se cumple.
	 */
	public boolean isExistenResultadosConsulta() {
		return listaMiscelaneaFiscal != null && !listaMiscelaneaFiscal.isEmpty();
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
	 * @return the listaMiscelaneaFiscal
	 */
	public List<OperacionValorMatchDTO> getListaMiscelaneaFiscal() {
		return listaMiscelaneaFiscal;
	}

	/**
	 * @param listaMiscelaneaFiscal
	 *            the listaMiscelaneaFiscal to set
	 */
	public void setListaMiscelaneaFiscal(
			List<OperacionValorMatchDTO> listaMiscelaneaFiscal) {
		this.listaMiscelaneaFiscal = listaMiscelaneaFiscal;
	}

	/**
	 * @return the criterio
	 */
	public CriterioMatchOperacionesDTO getCriterio() {
		return criterio;
	}

	/**
	 * @param criterio
	 *            the criterio to set
	 */
	public void setCriterio(CriterioMatchOperacionesDTO criterio) {
		this.criterio = criterio;
	}

	/**
	 * @return the estadoPaginacion
	 */
	public EstadoPaginacionDTO getEstadoPaginacion() {
		return estadoPaginacion;
	}

	/**
	 * @param estadoPaginacion
	 *            the estadoPaginacion to set
	 */
	public void setEstadoPaginacion(EstadoPaginacionDTO estadoPaginacion) {
		this.estadoPaginacion = estadoPaginacion;
	}

	/**
	 * @return the consultaOperacionesMatchService
	 */
	public ConsultaEstatusOperacionesMatchService getConsultaOperacionesMatchService() {
		return consultaOperacionesMatchService;
	}

	/**
	 * @param consultaOperacionesMatchService
	 *            the consultaOperacionesMatchService to set
	 */
	public void setConsultaOperacionesMatchService(
			ConsultaEstatusOperacionesMatchService consultaOperacionesMatchService) {
		this.consultaOperacionesMatchService = consultaOperacionesMatchService;
	}

	/**
	 * @return the idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * @param idFolioTraspasante
	 *            the idFolioTraspasante to set
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * @return the consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * @param consultaCatalogos
	 *            the consultaCatalogos to set
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * @return the sRol
	 */
	public String getSRol() {
		return sRol;
	}

	/**
	 * @param rol
	 *            the sRol to set
	 */
	public void setSRol(String rol) {
		sRol = rol;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros
	 *            the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the editarTraspasante
	 */
	public boolean isEditarTraspasante() {
		return editarTraspasante;
	}

	/**
	 * @param editarTraspasante
	 *            the editarTraspasante to set
	 */
	public void setEditarTraspasante(boolean editarTraspasante) {
		this.editarTraspasante = editarTraspasante;
	}

	public Boolean isGuardaBitacoraConsulta() {
		return guardaBitacoraConsulta;
	}

	public void setGuardaBitacoraConsulta(Boolean guardaBitacoraConsulta) {
		this.guardaBitacoraConsulta = guardaBitacoraConsulta;
	}

	/**
	 * @return the idFolioContraparte
	 */
	public String getIdFolioContraparte() {
		return idFolioContraparte;
	}

	/**
	 * @param idFolioContraparte
	 *            the idFolioContraparte to set
	 */
	public void setIdFolioContraparte(String idFolioContraparte) {
		this.idFolioContraparte = idFolioContraparte;
	}

	/**
	 * @return the listaMiscelaneaFiscalReportes
	 */
	public List<OperacionValorMatchDTO> getListaMiscelaneaFiscalReportes() {
		return listaMiscelaneaFiscalReportes;
	}

	/**
	 * @param listaMiscelaneaFiscalReportes
	 *            the listaMiscelaneaFiscalReportes to set
	 */
	public void setListaMiscelaneaFiscalReportes(
			List<OperacionValorMatchDTO> listaMiscelaneaFiscalReportes) {
		this.listaMiscelaneaFiscalReportes = listaMiscelaneaFiscalReportes;
	}

	/**
	 * @return the papelMercado
	 */
	public String getPapelMercado() {
		return papelMercado;
	}

	/**
	 * @param papelMercado
	 *            the papelMercado to set
	 */
	public void setPapelMercado(String papelMercado) {
		this.papelMercado = papelMercado;
	}
}