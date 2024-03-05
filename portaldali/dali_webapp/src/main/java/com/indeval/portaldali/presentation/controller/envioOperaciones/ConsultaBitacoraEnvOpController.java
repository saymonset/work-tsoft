package com.indeval.portaldali.presentation.controller.envioOperaciones;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaDivisa;

public class ConsultaBitacoraEnvOpController extends ControllerBase {
	
	/** Servicio para la consulta de */
	private EnvioOperacionesService envioOperacionesService;

	/** Parametro obtenidos desde la pantalla */
	private BitacoraOperaciones params = new BitacoraOperaciones();

	/** Bandera que define el estado de la consulta */
	private boolean consultaEjecutada;

	/** Resultados de la consulta */
	private PaginaVO resultados = new PaginaVO();

	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;
	
	private BovedaDTO bovedaEfectivoDTO;

	private BovedaDTO bovedaValorDTO;

	/** Constructor */
	public String getInit() {
		params = new BitacoraOperaciones();
		if ( !isUsuarioIndeval() ) {
			
			params.setIdTrasp(getInstitucionActual().getClaveTipoInstitucion());
			params.setIdRecep(getInstitucionActual().getClaveTipoInstitucion());
			params.setFolioRecep(getInstitucionActual().getFolioInstitucion());
			params.setFolioTrasp(getInstitucionActual().getFolioInstitucion());
		}
		
		idFolioTraspasante = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		
		params.setFechaConcertacion(new Date());
		params.setFechaLiquidacion(new Date());
		consultaEjecutada = false;
		return null;
	}
	
	/**
	 * Invoca la consulta principal de la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void buscarOperaciones(ActionEvent e) {
		if (logger.isTraceEnabled()) {
			logger.trace("Inicia busqueda con params " + params);
			logger.trace("divisa " + criterioDivisa.getOpcionSeleccionada());
			logger.trace("BovedaEfectivoDTO " + getBovedaEfectivoDTO());
			logger.trace("BovedaValorDTO " + getBovedaValorDTO());
		}
		params.setIdTrasp(obtenerIdInstitucion(idFolioTraspasante));
		params.setIdRecep(obtenerIdInstitucion(idFolioTraspasante));
		params.setFolioRecep(obtenerFolioInstitucion(idFolioTraspasante));
		params.setFolioTrasp(obtenerFolioInstitucion(idFolioTraspasante));
		if (criterioDivisa.getOpcionSeleccionada().getClaveAlfabetica() == null) {
			params.setDivisa("TODAS");
		} else {
			params.setDivisa(criterioDivisa.getOpcionSeleccionada()
					.getClaveAlfabetica());
		}
		if (getBovedaEfectivoDTO() == null || getBovedaEfectivoDTO().getId() == -1L) {
			params.setBovedaEfectivo("TODAS");
		} else {
			params.setBovedaEfectivo(String.valueOf(getBovedaEfectivoDTO().getId()));
		}
		if (getBovedaValorDTO() == null || getBovedaValorDTO().getId() == -1L) {
			params.setBoveda("TODAS");
		} else {
			params.setBoveda(String.valueOf(getBovedaValorDTO().getId()));
		}

		ejecutarConsulta();

	}

	/**
	 * @see
	 * com.indeval.portalinternacional.presentation.controller.common.ControllerBase
	 * #ejecutarConsulta()
	 */
	@SuppressWarnings("rawtypes")
	public String ejecutarConsulta() {
		verificarParametrosConsulta(params);
		resultados = envioOperacionesService.getBitacoras(params, paginaVO);
		getPaginaNotBlank();
		paginaVO = resultados;
		if( resultados.getTotalRegistros().intValue() == 0 ) {
			resultados.setRegistros(new ArrayList());
		}
		consultaEjecutada = true;
		return null;
	}
	
	/**
	 * Método que verifica el criterio de búsqueda de Bitacora Envio Operaciones
	 * @param params el parametro de consulta
	 */
	private void verificarParametrosConsulta(BitacoraOperaciones criterio){
		BitacoraOperaciones bitacora = criterio;
		params.setIdTrasp(obtenerIdInstitucion(idFolioTraspasante));
		params.setIdRecep(obtenerIdInstitucion(idFolioTraspasante));
		params.setFolioRecep(obtenerFolioInstitucion(idFolioTraspasante));
		params.setFolioTrasp(obtenerFolioInstitucion(idFolioTraspasante));
		//cuenta
		params.setCuentaTrasp(StringUtils.isNotEmpty(bitacora.getCuentaTrasp())  ? bitacora.getCuentaTrasp() : null);
		//TV
		params.setTv(StringUtils.isNotEmpty(bitacora.getTv())? bitacora.getTv().toUpperCase() : null);		
		//emisora
		params.setEmisora(StringUtils.isNotEmpty(bitacora.getEmisora())  ? bitacora.getEmisora().toUpperCase() : null);
		//serie
		params.setSerie(StringUtils.isNotEmpty(bitacora.getSerie()) ? bitacora.getSerie().toUpperCase() : null);
		//cupon
		params.setCupon(StringUtils.isNotEmpty(bitacora.getCupon()) ? bitacora.getCupon() : null);
		//estatus
		params.setEstatusRegistro(StringUtils.isNotEmpty(bitacora.getEstatusRegistro()) ? bitacora.getEstatusRegistro() : null);		
		//folio descripcion
		params.setReferenciaMensaje(StringUtils.isNotEmpty(bitacora.getReferenciaMensaje()) ? bitacora.getReferenciaMensaje() : null);		
	}


	/**
	 * M&eacute;todo para generar un reporte con formato XLS o PDF
	 * 
	 */
	@SuppressWarnings("rawtypes")
    public void generarReportes(ActionEvent e) {
		final BitacoraOperaciones paramsConsultaGeneral = params;
		PaginaVO paginaBusqueda = new PaginaVO();
		paginaBusqueda.setRegistrosXPag(PaginaVO.TODOS);
		resultados = envioOperacionesService.getBitacoras(paramsConsultaGeneral, paginaBusqueda);
		getPaginaNotBlank();
		if( resultados.getTotalRegistros().intValue() == 0 ) {
			resultados.setRegistros(new ArrayList());
		}
	}

	/**
	 * método que limpia la pantalla de Consulta Envio Operaciones
	 * 
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		params = new BitacoraOperaciones();
		if (getInstitucionActual() != null
				&& getInstitucionActual().getClaveTipoInstitucion() != null
				&& getInstitucionActual().getFolioInstitucion() != null) {

			idFolioTraspasante = getInstitucionActual()
					.getClaveTipoInstitucion()
					+ getInstitucionActual().getFolioInstitucion();
			params.setIdTrasp(getInstitucionActual().getClaveTipoInstitucion());
			params.setIdRecep(getInstitucionActual().getClaveTipoInstitucion());
			params.setFolioRecep(getInstitucionActual().getFolioInstitucion());
			params.setFolioTrasp(getInstitucionActual().getFolioInstitucion());
		}
		params.setFechaConcertacion(new Date());
		params.setFechaLiquidacion(new Date());
		consultaEjecutada = false;
		resultados = new PaginaVO();
		bovedaEfectivoDTO = null;
		bovedaValorDTO = null;
		criterioDivisa.setOpcionSeleccionada(null);
		criterioDivisa.getOpcionSeleccionada().setClaveAlfabetica(null);
		params.setDivisa("TODAS");
		
		
	}

	/** Objeto que representa la pagina a consultar */
	public PaginaVO paginaVO = new PaginaVO();

	/** El número de página actual de la consulta */
	private int numeroPagina = -1;

	/**
	 * La cantidad de registros que serán presentados en una página de
	 * resultados
	 */
	private int registrosPorPagina = -1;

	/** El total de páginas de resultados productos de la consulta */
	private int totalPaginas = -1;

	/**
	 * El criterio de divisa seleccionada utilizada para la consulta
	 */
	private ConsultaDivisa criterioDivisa;
	
	public void setRegistrosXPag(Integer regs) {
		paginaVO.setRegistrosXPag(regs);
	}

	/**
	 * Actualiza la información de la paginación al avanzar el número de páginas
	 * solicitadas.
	 * 
	 * @param paginas
	 *            el número de páginas a avanzar.
	 */
	public void avanzar(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if (numeropPag + paginas > getTotalPaginas()) {
				numeropPag = getTotalPaginas();
			} else {
				numeropPag += paginas;
			}
			paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag());
		}
		ejecutarConsulta();

	}

	/**
	 * Actualiza la información de la paginación al retroceder el número de
	 * páginas solicitadas.
	 * 
	 * @param paginas
	 *            el número páginas a retroceder.
	 */
	public void retroceder(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if ((numeropPag - paginas) < 1) {
				numeropPag = 1;
			} else {
				numeropPag -= paginas;
			}
		}
		paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag());
		ejecutarConsulta();
	}

	/**
	 * Actualiza la información de la paginación al establecer la primera página
	 * de resultados como la página actual.
	 */
	public String irPrimeraPagina() {

		if (paginaVO.getTotalRegistros() > 0) {
			numeroPagina = 1;
		}
		paginaVO.setOffset(0);
		ejecutarConsulta();
		return null;
	}

	/**
	 * Actualiza la información de la paginación al establecer la última página
	 * de resultados como la página actual.
	 */
	public String irUltimaPagina() {
		getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			paginaVO.setOffset((getTotalPaginas() - 1)
					* paginaVO.getRegistrosXPag());
		}
		ejecutarConsulta();
		return null;
	}

	public boolean isIrAlPrimero() {

		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isIrAlUltimo() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isAvanzar() {
		boolean resultado = false;
		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isAvanzarRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isRetroceder() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isRetrocederRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public String avanzarPagina() {
		avanzar(1);
		return null;
	}

	public String avanzarPaginasRapido() {
		avanzar(3);
		return null;
	}

	public String retrocederPaginasRapido() {
		retroceder(3);
		return null;
	}

	public String retrocederPagina() {
		retroceder(1);
		return null;
	}

	/**
	 * @return the paginaVO
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * @param paginaVO
	 *            the paginaVO to set
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	/**
	 * @return the registrosPorPagina
	 */
	public int getRegistrosPorPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {
			registrosPorPagina = paginaVO.getRegistrosXPag();
		}
		return registrosPorPagina;
	}

	/**
	 * @param registrosPorPagina
	 *            the registrosPorPagina to set
	 */
	public void setRegistrosPorPagina(int registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	/**
	 * @return the numeroPagina
	 */
	public int getNumeroPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {

			if (paginaVO.getOffset() == 0) {
				numeroPagina = 1;
			} else {
				numeroPagina = ((int) Math.ceil(paginaVO.getOffset()
						.doubleValue()
						/ paginaVO.getRegistrosXPag().doubleValue())) + 1;
			}

		} else if (paginaVO != null && paginaVO.getTotalRegistros() <= 0) {
			numeroPagina = 0;
		}
		return numeroPagina;
	}

	/**
	 * @param numeroPagina
	 *            the numeroPagina to set
	 */
	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		if (paginaVO != null && paginaVO.getTotalRegistros() > 0) {
			totalPaginas = (int) Math
					.ceil((paginaVO.getTotalRegistros().doubleValue() / paginaVO
							.getRegistrosXPag().doubleValue()));
		} else if (paginaVO != null && paginaVO.getTotalRegistros() <= 0) {
			totalPaginas = 0;
		}
		return totalPaginas;
	}

	/**
	 * @param totalPaginas
	 *            the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public void getPaginaNotBlank() {

		if (resultados == null) {
			resultados = new PaginaVO();
		}
		if (resultados.getOffset() == null) {
			resultados.setOffset(0);
		}
		if (resultados.getTotalRegistros() == null) {
			resultados.setTotalRegistros(0);
		}
		
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
	 * @return the resultados
	 */
	public PaginaVO getResultados() {
		return resultados;
	}

	/**
	 * @param resultados
	 *            the resultados to set
	 */
	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}

	/**
	 * @return the params
	 */
	public BitacoraOperaciones getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(BitacoraOperaciones params) {
		this.params = params;
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

	public String obtenerIdInstitucion(String idFolioInstitucion) {
		return StringUtils.substring(idFolioInstitucion, 0, 2);
	}

	/**
	 * método que obtiene el folioInstitucion a partir del idFolioInstitucion
	 * 
	 * @param idFolioInstitucion
	 * @return folioInstitucion
	 */
	public String obtenerFolioInstitucion(String idFolioInstitucion) {
		return StringUtils.substring(idFolioInstitucion, 2, 5);
	}

	/**
	 * @param envioOperacionesService the envioOperacionesService to set
	 */
	public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * @return the envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	public ConsultaDivisa getCriterioDivisa() {
		return criterioDivisa;
	}

	public void setCriterioDivisa(final ConsultaDivisa criterioDivisa) {
		this.criterioDivisa = criterioDivisa;
	}

	public BovedaDTO getBovedaEfectivoDTO() {
		return bovedaEfectivoDTO;
	}

	public void setBovedaEfectivoDTO(BovedaDTO bovedaEfectivoDTO) {
		this.bovedaEfectivoDTO = bovedaEfectivoDTO;
	}

	public BovedaDTO getBovedaValorDTO() {
		return bovedaValorDTO;
	}

	public void setBovedaValorDTO(BovedaDTO bovedaValorDTO) {
		this.bovedaValorDTO = bovedaValorDTO;
	}

}
