package com.indeval.portalinternacional.presentacion.controller.conteoAutomaticoSaldo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoParams;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ConteoAutomaticoSaldosController extends ControllerBase {
	
	
	private DivisionInternacionalService divisionInternacionalService = null;
	
	private Date fechaConsulta = null;
	
	private BigDecimal saldoInicialFideicomisoBanamex = null;
	
	private BigDecimal saldoInicialFideicomisoInbur = null;
	
	private BigDecimal saldoInicialFideicomisoNafin = null;
	
	private BigDecimal saldoInicialFideicomisoVitro = null;
	
	private boolean consultaEjecutada = Boolean.FALSE;
	
	private ConsultaCierreFideicomisoVO fideicomisoActual;
	
	private String participante = null;
	
		
	
	public String getInit() {
		
		fechaConsulta = new Date();
				
		return "";
	}
	
	/**
	 * Limpia los resultados de la consulta y los criterios.
	 * 
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		
		fechaConsulta = new Date();
		saldoInicialFideicomisoBanamex = null;
		saldoInicialFideicomisoInbur = null;
		saldoInicialFideicomisoNafin = null;
		saldoInicialFideicomisoVitro = null;
		
		consultaEjecutada = Boolean.FALSE;
		paginaVO = new PaginaVO();
		fideicomisoActual = new ConsultaCierreFideicomisoVO();
		
	}
	
	public void buscar(ActionEvent e) {
		
		if(fechaConsulta == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fecha no debe ir vac\u00EDa", "La fecha no debe ir vac\u00EDa"));
		}
		else {
			ConsultaCierreFideicomisoParams params = new ConsultaCierreFideicomisoParams(); 
			params.setFechaConsulta(fechaConsulta);
			params.setSaldoInicialBanamex(saldoInicialFideicomisoBanamex != null ? saldoInicialFideicomisoBanamex : new BigDecimal("0"));
			params.setSaldoInicialInbur(saldoInicialFideicomisoInbur != null ? saldoInicialFideicomisoInbur : new BigDecimal("0"));
			params.setSaldoInicialNafin(saldoInicialFideicomisoNafin != null ? saldoInicialFideicomisoNafin : new BigDecimal("0"));
			params.setSaldoInicialVitro(saldoInicialFideicomisoVitro!= null ? saldoInicialFideicomisoVitro : new BigDecimal("0"));
			List<ConsultaCierreFideicomisoVO> resultados = divisionInternacionalService.getListaCierreFideicomiso(params);
			
			paginaVO = new PaginaVO();
			
			if(resultados != null && !resultados.isEmpty() && resultados.size() > 0) {
				
				paginaVO.setOffset(0);
				paginaVO.setRegistros(resultados);
				paginaVO.setRegistrosXPag(1);
				paginaVO.setTotalRegistros(resultados.size());
			}
			else {
				
				paginaVO.setOffset(0);
				paginaVO.setRegistros(null);
			}
				
			consultaEjecutada = Boolean.TRUE;
			ejecutarConsulta();
		}
	}
	
	public String ejecutarConsulta() {
		
		if( paginaVO.getRegistros() != null && paginaVO.getRegistros().size() > 0 ) {
			
			fideicomisoActual = (ConsultaCierreFideicomisoVO)paginaVO.getRegistros().get(paginaVO.getOffset());
			if(fideicomisoActual.getCuentaNombrada().getInstitucion() != null) {
				participante = fideicomisoActual.getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() +
								fideicomisoActual.getCuentaNombrada().getInstitucion().getFolioInstitucion();
			}
			
		} else {
			fideicomisoActual = new ConsultaCierreFideicomisoVO();
			
		}
		return null;
	}
	/**
	 * M&eacute;todo que genera los reportes en PDF o XLS
	 * 
	 * @param El
	 *            action listener que lo invoca
	 */
	public void generarReportes(ActionEvent e) {
		


		reiniciarEstadoPeticion();
		if(fechaConsulta == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fecha no debe ir vac\u00EDa", "La fecha no debe ir vac\u00EDa"));
		}
		else {
			ConsultaCierreFideicomisoParams params = new ConsultaCierreFideicomisoParams(); 
			params.setFechaConsulta(fechaConsulta);
			params.setSaldoInicialBanamex(saldoInicialFideicomisoBanamex != null ? saldoInicialFideicomisoBanamex : new BigDecimal("0"));
			params.setSaldoInicialInbur(saldoInicialFideicomisoInbur != null ? saldoInicialFideicomisoInbur : new BigDecimal("0"));
			params.setSaldoInicialNafin(saldoInicialFideicomisoNafin != null ? saldoInicialFideicomisoNafin : new BigDecimal("0"));
			params.setSaldoInicialVitro(saldoInicialFideicomisoVitro!= null ? saldoInicialFideicomisoVitro : new BigDecimal("0"));
			List<ConsultaCierreFideicomisoVO> resultados = divisionInternacionalService.getListaCierreFideicomiso(params);
			
			paginaVO.setRegistros(null);
			if(resultados != null && !resultados.isEmpty() && resultados.size() > 0) {
				//paginaVO.setOffset(0);
				paginaVO.setRegistros(resultados);
				paginaVO.setRegistrosXPag(1);
				paginaVO.setTotalRegistros(resultados.size());
			}
		
				
			consultaEjecutada = Boolean.TRUE;
		}
	}	
	

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}


	public BigDecimal getSaldoInicialFideicomisoBanamex() {
		return saldoInicialFideicomisoBanamex;
	}


	public void setSaldoInicialFideicomisoBanamex(
			BigDecimal saldoInicialFideicomisoBanamex) {
		this.saldoInicialFideicomisoBanamex = saldoInicialFideicomisoBanamex;
	}


	public BigDecimal getSaldoInicialFideicomisoInbur() {
		return saldoInicialFideicomisoInbur;
	}


	public void setSaldoInicialFideicomisoInbur(
			BigDecimal saldoInicialFideicomisoInbur) {
		this.saldoInicialFideicomisoInbur = saldoInicialFideicomisoInbur;
	}


	public BigDecimal getSaldoInicialFideicomisoNafin() {
		return saldoInicialFideicomisoNafin;
	}


	public void setSaldoInicialFideicomisoNafin(
			BigDecimal saldoInicialFideicomisoNafin) {
		this.saldoInicialFideicomisoNafin = saldoInicialFideicomisoNafin;
	}


	public BigDecimal getSaldoInicialFideicomisoVitro() {
		return saldoInicialFideicomisoVitro;
	}


	public void setSaldoInicialFideicomisoVitro(
			BigDecimal saldoInicialFideicomisoVitro) {
		this.saldoInicialFideicomisoVitro = saldoInicialFideicomisoVitro;
	}

	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}


	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public ConsultaCierreFideicomisoVO getFideicomisoActual() {
		return fideicomisoActual;
	}

	public void setFideicomisoActual(ConsultaCierreFideicomisoVO fideicomisoActual) {
		this.fideicomisoActual = fideicomisoActual;
	}

	public String getParticipante() {
		return participante;
	}

	public void setParticipante(String participante) {
		this.participante = participante;
	}

}
