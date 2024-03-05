/**
 * 
 */
package com.indeval.portaldali.presentation.controller.mercadocapitales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.EmisoraVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.util.EstadoCuentaSocInvHelper;

/**
 * @author enavarrete
 * 
 */
public class EstadoCuentaSocInvController extends ControllerBase {

	/** Indica si ya se ejecuto el reportes */
	private boolean reporteListo = false;

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	private MercadoCapitalesService mercadoCapitalesService; 
	
	private String claveInstitucion;
	
	private AgenteVO agente;
	
	private EmisionVO emision;
	
	private EmisoraVO emisoraActual;
	
	@SuppressWarnings("unchecked")
	private List listaSaldosReporte;
	
	@SuppressWarnings("unchecked")
	private List saldosEmisiones;
	
	/** Indica si debe dejar bitacora o no */
	private boolean debeDejarBitacora;
	
	
	
	
	public String getInit(){
		
		logger.info("Entrando a EstadoCuentaSocInvController");
		agente = new AgenteVO();
		emision = new EmisionVO();
		if( !isUsuarioIndeval() ) {
			claveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			agente.setRazon(getInstitucionActual().getNombreCorto());
		}
		
		return null;
		
	}
	
	
	
	public void validaInstitucion(ActionEvent event) {
		logger.info("Entrando a EstadoCuentaSocInvController.validaInstitucion");
		recuperaAgente(claveInstitucion);
	}

	public void recuperaAgente(String idFolio) {
		if (StringUtils.isNotBlank(idFolio) &&
				idFolio.length() == 5 &&
				StringUtils.isNumeric(idFolio)) {
			InstitucionDTO inst = consultaCatalogosFacade
					.buscarInstitucionPorIdFolio(idFolio);
			agente = new AgenteVO();
			if (inst != null) {
				agente.setRazon(inst.getNombreCorto());
			} else {
				agente.setRazon(null);
			}
		} else {
			agente.setRazon(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List recuperaEmisiones( Object event ) {
		if( validaCustodio() ) {
			AgenteVO agenteInt = new AgenteVO(claveInstitucion.substring(0,2),claveInstitucion.substring(2));
			return mercadoCapitalesService.getListaEmisionSociedadesInversion(agenteInt, emision);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void consultar() {
		logger.info("Entrando a EstadoCuentaSocInvController.consultar");
		if ( !validaCustodio() ) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Custodio Inv\u00E1lido", "Custodio Inv\u00E1lido");
			FacesContext.getCurrentInstance().addMessage(null, message);
			limpiar();
		} else {
			AgenteVO agenteInt = new AgenteVO(claveInstitucion.substring(0,2),claveInstitucion.substring(2));
			List listaEmisoras = mercadoCapitalesService.getListaEmisionSociedadesInversionRazonSocial(agenteInt, emision);
			paginaVO.setOffset(0);
			paginaVO.setRegistros(listaEmisoras);
			paginaVO.setTotalRegistros(listaEmisoras.size());
			paginaVO.setRegistrosXPag(1);
			ejecutarConsulta();
			reporteListo = true;
		}
	}

	public void limpiar() {
		logger.info("Entrando a EstadoCuentaSocInvController.limpiar");
		agente = new AgenteVO();
		emision = new EmisionVO();
		if( !isUsuarioIndeval() ) {
			claveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			agente.setRazon(getInstitucionActual().getNombreCorto());
		} else {
			claveInstitucion = "";
		}
		reporteListo = false;
		paginaVO = new PaginaVO();
	}
	
	private boolean validaCustodio() {
		if( StringUtils.isNotBlank(claveInstitucion) &&
				claveInstitucion.length() == 5 &&
				StringUtils.isNumeric(claveInstitucion) ) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void generarReportes(ActionEvent event) {
		logger.info("Entrando a EstadoCuentaSocInvController.generarReportes");
		reiniciarEstadoPeticion();
		AgenteVO agenteInt = new AgenteVO(claveInstitucion.substring(0,2),claveInstitucion.substring(2));
		List listaEmisorasReporte = 
			mercadoCapitalesService.getListaEmisionSociedadesInversionRazonSocial(agenteInt, emision);
		listaSaldosReporte = new ArrayList();
		if( listaEmisorasReporte != null && listaEmisorasReporte.size() > 0 ) {
			for( Iterator it = listaEmisorasReporte.iterator(); it.hasNext(); ) {
				EmisoraVO emisora = (EmisoraVO)it.next();
				EstadoCuentaSocInvHelper edoCuenta = new EstadoCuentaSocInvHelper();
				edoCuenta.setEmisora(emisora);
				List saldosEmisiones2 = mercadoCapitalesService.
					getSaldoEmisionSociedadesInversionRazonSocial(agenteInt, emisora.getClavePizarra(),false);
				edoCuenta.setSaldos(saldosEmisiones2);
				listaSaldosReporte.add( edoCuenta );
			}
		}
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
	@SuppressWarnings("unused")
	private int totalPaginas = -1;

	public void setRegistrosXPag(Integer regs) {
		paginaVO.setRegistrosXPag(regs);
	}

	public String ejecutarConsulta() {
		logger.info("Entrando a EstadoCuentaSocInvController.ejecutarConsulta");
		if ( validaCustodio() && paginaVO.getRegistros().size() > 0 ) {
			setEmisoraActual((EmisoraVO)paginaVO.getRegistros().get(getNumeroPagina()-1)); 
			AgenteVO agenteInt = new AgenteVO(claveInstitucion.substring(0,2),claveInstitucion.substring(2));
			saldosEmisiones = mercadoCapitalesService.
				getSaldoEmisionSociedadesInversionRazonSocial(agenteInt, getEmisoraActual().getClavePizarra(),debeDejarBitacora);
			debeDejarBitacora = false;
		}
		
		return null;
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
		if( paginaVO != null &&
				paginaVO.getRegistros() != null ) {
			return paginaVO.getRegistros().size();
		}
		return 0;
	}

	/**
	 * @param totalPaginas
	 *            the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	/**
	 * @param reporteListo the reporteListo to set
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
	 * @param consultaCatalogosFacade the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * @param claveInstitucion the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @return the agente
	 */
	public AgenteVO getAgente() {
		return agente;
	}

	/**
	 * @param agente the agente to set
	 */
	public void setAgente(AgenteVO agente) {
		this.agente = agente;
	}

	/**
	 * @param emision the emision to set
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return the emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param mercadoCapitalesService the mercadoCapitalesService to set
	 */
	public void setMercadoCapitalesService(
			MercadoCapitalesService mercadoCapitalesService) {
		this.mercadoCapitalesService = mercadoCapitalesService;
	}

	/**
	 * @param emisoraActual the emisoraActual to set
	 */
	public void setEmisoraActual(EmisoraVO emisoraActual) {
		this.emisoraActual = emisoraActual;
	}

	/**
	 * @return the emisoraActual
	 */
	public EmisoraVO getEmisoraActual() {
		return emisoraActual;
	}

	/**
	 * @param saldosEmisiones the saldosEmisiones to set
	 */
	@SuppressWarnings("unchecked")
	public void setSaldosEmisiones(List saldosEmisiones) {
		this.saldosEmisiones = saldosEmisiones;
	}

	/**
	 * @return the saldosEmisiones
	 */
	@SuppressWarnings("unchecked")
	public List getSaldosEmisiones() {
		return saldosEmisiones;
	}

	public int getListaSaldosSize() {
		if( saldosEmisiones != null ) {
			return saldosEmisiones.size();
		}
		return 0;
	}

	/**
	 * @return the listaSaldosReporte
	 */
	@SuppressWarnings("unchecked")
	public List getListaSaldosReporte() {
		return listaSaldosReporte;
	}

	/**
	 * @param listaSaldosReporte the listaSaldosReporte to set
	 */
	@SuppressWarnings("unchecked")
	public void setListaSaldosReporte(List listaSaldosReporte) {
		this.listaSaldosReporte = listaSaldosReporte;
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
