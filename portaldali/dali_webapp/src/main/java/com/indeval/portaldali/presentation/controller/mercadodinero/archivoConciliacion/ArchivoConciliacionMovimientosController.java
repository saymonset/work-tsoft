/**
 * 
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.archivoConciliacion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * @author enavarrete
 * 
 */
public class ArchivoConciliacionMovimientosController extends ControllerBase {

	private AgenteVO agente;
	private EmisionVO emision;
	private BovedaDTO boveda;
	private List<ArchivoConciliacionVO> listaTxt;
	private boolean reporteListo = false;
	private PaginaVO paginaReporte;
	private ConsultaMovimientosValorService consultaMovimientosValorService;
	private ConsultaCatalogosFacade consultaCatalogosFacade;
	private UtilServices utilService;
	private boolean debeDejarBitacora;
	
	

	

	@SuppressWarnings("unchecked")
	public String generaTxt() {
		logger
				.info("Entrando a ArchivoConciliacionMovimientosController.generaTxt");
		PaginaVO paginaReporte = new PaginaVO();
		paginaReporte.setRegistrosXPag(PaginaVO.TODOS);

		paginaReporte.getValores().put(ConciliacionService.MOVIMIENTOS,
				Boolean.TRUE);

		logger.debug("Agente: [" + agente.getId() + agente.getFolio() + "-"
				+ agente.getCuenta() + "]");
		logger.debug("Emision: [" + emision.getIdTipoValor() + "-"
				+ emision.getEmisora() + "-" + emision.getSerie() + "-"
				+ emision.getCupon() + "-" + "]");

		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(0);
		if (boveda != null && boveda.getId() >= 0) {
			bovedaDTO.setId(boveda.getId());
		}

		paginaReporte = getResultadosNombradasSinAgrupar(agente,emision,bovedaDTO,paginaReporte);

		if (paginaReporte != null && paginaReporte.getRegistros() != null) {
			listaTxt = paginaReporte.getRegistros();
		}

		return "generaArchivoConciliacionMovimientosTxt";
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

	public void setRegistrosXPag(Integer regs) {
		paginaVO.setRegistrosXPag(regs);
	}

	public String consultarMovimientos() {
		logger.info("Entrando a ArchivoConciliacionMovimientosController.consultarMovimientos");
		paginaVO.setOffset(0);
		ejecutarConsulta();
		return "mostrarMovimiento";
	}

	public String ejecutarConsulta() {
		logger.info("Entrando a ArchivoConciliacionMovimientosController.ejecutarConsulta");

		reporteListo = true;

		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(0);
		if (boveda != null && boveda.getId() >= 0) {
			bovedaDTO.setId(boveda.getId());
		}

		paginaVO = getResultadosNombradasSinAgrupar(agente,emision,bovedaDTO,paginaVO);
		
		getPaginaNotBlank();

		return null;
	}

	private PaginaVO getResultadosNombradasSinAgrupar(
			AgenteVO agente, EmisionVO emisionVO, BovedaDTO bovedaDTO, PaginaVO paginaTemp) {

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();
		// cuenta contraparte
		CuentaDTO cuentaContraparte = new CuentaDTO();
		TipoTenenciaDTO tipoTenenciaDTO = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturalezaDTO = new TipoNaturalezaDTO();
		InstitucionDTO institucionDTO = new InstitucionDTO();
		institucionDTO.setId(-1);
		tipoNaturalezaDTO.setId("P");
		TipoCuentaDTO tipoCuentaDTO = new TipoCuentaDTO();
		tipoCuentaDTO.setId("N");
		tipoTenenciaDTO.setIdTipoCuenta(-1);
		tipoTenenciaDTO.setTipoCustodia("V");
		tipoTenenciaDTO.setTipoNaturaleza(tipoNaturalezaDTO);
		tipoTenenciaDTO.setTipoCuenta(tipoCuentaDTO);
		cuentaContraparte.setTipoTenencia(tipoTenenciaDTO);
		cuentaContraparte.setNumeroCuenta("-1");
		cuentaContraparte.setInstitucion(institucionDTO);
		criterio.setCuentaContraparte(cuentaContraparte);

		CuentaDTO cuenta = new CuentaDTO();
		InstitucionDTO institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(agente.getId() + agente.getFolio());
		if(institucion == null) {
			institucion = new InstitucionDTO();
			institucion.setId(-1);
		}
		if (StringUtils.isNotBlank(agente.getCuenta())) {
			cuenta.setNumeroCuenta(agente.getId() + agente.getFolio()
					+ agente.getCuenta());
		} else {
			cuenta.setNumeroCuenta("-1");
		}
		cuenta.setInstitucion(institucion);
		cuenta.setTipoTenencia(tipoTenenciaDTO);
		criterio.setCuenta(cuenta);

		// Emision falta id emisora y id tipovalor
		EmisionDTO emisionDTO = new EmisionDTO();
		if (StringUtils.isNotBlank(emisionVO.getIsin())) {
			emisionDTO.setIsin(emisionVO.getIsin());
		}

		SerieDTO serieDTO = new SerieDTO();
		if (StringUtils.isNotBlank(emisionVO.getSerie())) {
			serieDTO.setSerie(emisionVO.getSerie());
		}
		emisionDTO.setSerie(serieDTO);

		EmisoraDTO emisoraDTO = new EmisoraDTO();
		
		emisoraDTO = consultaCatalogosFacade.buscarEmisoraPorNombreCorto(emisionVO.getEmisora());
		
		emisionDTO.setEmisora(emisoraDTO);

		TipoValorDTO tipoValorDTO = new TipoValorDTO();
		
		tipoValorDTO = consultaCatalogosFacade.buscarTipoValorPorClave(emisionVO.getIdTipoValor());
		
		emisionDTO.setTipoValor(tipoValorDTO);

		criterio.setEmision(emisionDTO);

		criterio.setBoveda(bovedaDTO);

		Date fecha = utilService.getCurrentDate();
		Date fechaInicio = getFechaHoraCero(fecha);
		Date fechaFin = getFechaHoraFinDia(fecha);
		criterio.setFechaInicial(fechaInicio);
		criterio.setFechaFinal(fechaFin);
		criterio.setBusquedaFechaAplicacion(false);
		criterio.setBusquedaFechaConcertacion(false);
		// Tipo de Instruccion
		TipoInstruccionDTO tipoInstruccionDTO = new TipoInstruccionDTO();
		tipoInstruccionDTO.setIdTipoInstruccion((long) 0);
		criterio.setTipoInstruccion(tipoInstruccionDTO);
		// Tipo de Operacion
		TipoOperacionDTO tipoOperacionDTO = new TipoOperacionDTO();
		tipoOperacionDTO.setId((long) 0);
		criterio.setTipoOperacion(tipoOperacionDTO);
		// Folio Instruccion
		criterio.setFolioInstruccion(null);
		// Otros
		criterio.setOrdenarPorTipoDeInstruccion(false);
		criterio.setRolContraparte(RolCuentaConstants.AMBOS);
		criterio.setRolParticipante(RolCuentaConstants.AMBOS);

		paginaTemp = consultaMovimientosValorService
				.consultarMovimientosArchivoConciliacion(criterio, null, paginaTemp, debeDejarBitacora);
		
		debeDejarBitacora = false;
		
		return paginaTemp;
	}

	/**
	 * @return the consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * @param consultaCatalogosFacade the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	public Date getFechaHoraCero(Date fecha) {

		logger.info("Entrando a DateUtilsDaoImpl.getFechaHoraCero()");
		logger.debug("La fecha recibida es: [" + fecha + "]");

		if (fecha != null) {
			Calendar calFecha = new GregorianCalendar();
			calFecha.setTime(fecha);
			calFecha.set(Calendar.HOUR_OF_DAY, 0);
			calFecha.set(Calendar.MINUTE, 0);
			calFecha.set(Calendar.SECOND, 0);
			calFecha.set(Calendar.MILLISECOND, 0);
			logger.debug("FechaHoraCero : [" + calFecha.getTime() + "]");
			fecha = calFecha.getTime();
		}
		return fecha;
	}

	public Date getFechaHoraFinDia(Date fecha) {

		logger.debug("Entrando al metodo getFechaHoraFinDia()");
		logger.debug("La fecha recibida es: [" + fecha + "]");

		if (fecha != null) {
			Calendar calFecha = new GregorianCalendar();
			calFecha.setTime(fecha);
			int year = calFecha.get(Calendar.YEAR);
			int month = calFecha.get(Calendar.MONTH);
			int day = calFecha.get(Calendar.DAY_OF_MONTH);
			Calendar cal = new GregorianCalendar(year, month, day, 23, 59, 59);
			logger.debug("FechaHoraFinDia : [" + cal.getTime() + "]");
			fecha = cal.getTime();
		}
		return fecha;
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

		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		if (paginaVO.getOffset() == null) {
			paginaVO.setOffset(0);
		}
		if (paginaVO.getTotalRegistros() == null) {
			paginaVO.setTotalRegistros(0);
		}

	}

	/**
	 * @param listaTxt
	 *            the listaTxt to set
	 */
	public void setListaTxt(List<ArchivoConciliacionVO> listaTxt) {
		this.listaTxt = listaTxt;
	}

	/**
	 * @return the listaTxt
	 */
	public List<ArchivoConciliacionVO> getListaTxt() {
		return listaTxt;
	}

	/**
	 * @param reporteListo
	 *            the reporteListo to set
	 */
	public void setReporteListo(boolean reporteListo) {
		this.reporteListo = reporteListo;
	}

	/**
	 * @return the reporteListo
	 */
	public boolean isReporteListo() {
		return reporteListo;
	}

	/**
	 * @return the agente
	 */
	public AgenteVO getAgente() {
		return agente;
	}

	/**
	 * @param agente
	 *            the agente to set
	 */
	public void setAgente(AgenteVO agente) {
		this.agente = agente;
	}

	/**
	 * @return the emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @param paginaReporte
	 *            the paginaReporte to set
	 */
	public void setPaginaReporte(PaginaVO paginaReporte) {
		this.paginaReporte = paginaReporte;
	}

	/**
	 * @return the paginaReporte
	 */
	public PaginaVO getPaginaReporte() {
		return paginaReporte;
	}

	/**
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * @param consultaMovimientosValorService
	 *            the consultaMovimientosValorService to set
	 */
	public void setConsultaMovimientosValorService(
			ConsultaMovimientosValorService consultaMovimientosValorService) {
		this.consultaMovimientosValorService = consultaMovimientosValorService;
	}

	/**
	 * @return the consultaMovimientosValorService
	 */
	public ConsultaMovimientosValorService getConsultaMovimientosValorService() {
		return consultaMovimientosValorService;
	}

	/**
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

}
