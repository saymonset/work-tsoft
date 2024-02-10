package com.indeval.portalinternacional.presentation.controller;

import java.util.Date;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BitacoraFileTransferService;

@SuppressWarnings("unchecked")
public class BitacoraFileTransferController extends ControllerBase {
	
	private String usuarioRegistro;
	private String usuarioAutorizacion;
	private Date fechaInicialProcesamiento;
	private Date fechaFinalProcesamiento;
	private Date fechaProcesamiento;
	private boolean debeDejarBitacora;
	private boolean consultaEjecutada = false;
	private BitacoraFileTransferVO bitacoraFileTransferVO = new BitacoraFileTransferVO();
	
	private BitacoraFileTransferService bitacoraFileTransferService;
	private DateUtilService dateUtilService;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BitacoraFileTransferController.class);
	
	public BitacoraFileTransferController() {
		
		inicializarParametros();
		
	}
	
	public void inicializarParametros() {
		
		bitacoraFileTransferVO.setFechaProcesamiento(new Date[] {});
		usuarioRegistro = new String();
		usuarioAutorizacion = new String();
		
	}
	
	public String getInit() {

		setFechaInicialProcesamiento(dateUtilService.getCurrentDate());
		setFechaFinalProcesamiento(dateUtilService.getCurrentDate());
		
		return null;
		
	}
	
	public String ejecutarConsulta () {
		
		try {
			paginaVO = this.bitacoraFileTransferService.consultarFileTransfer(bitacoraFileTransferVO, paginaVO, true);
			debeDejarBitacora = false;
			consultaEjecutada = true;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			agregarMensajeWarn(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			agregarMensaje(e);
		} catch (Throwable e) {
			log.error(e.getMessage());
			agregarMensaje(e);
		}
		
		return null;
		
	}
	
	public void crearFiltros() {
		
		if (StringUtils.isNotBlank(usuarioRegistro)) {
			bitacoraFileTransferVO.setUsRegistro(StringUtils.trim(usuarioRegistro));
		}
		
		if (StringUtils.isNotBlank(usuarioAutorizacion)) {
			bitacoraFileTransferVO.setUsAutoriza(StringUtils.trim(usuarioAutorizacion));
		}

		bitacoraFileTransferVO.setFechaProcesamiento(new Date[] { fechaInicialProcesamiento, fechaFinalProcesamiento });

	}
	
	public void obtenerRegistros () {
		
		paginaVO.setOffset(0);
		paginaVO.setRegistros(null);
		crearFiltros();
		ejecutarConsulta();
		
	}
	
	public void limpiar(ActionEvent e) {
		
		consultaEjecutada = false;
		paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(50);
		inicializarParametros();

	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getUsuarioAutorizacion() {
		return usuarioAutorizacion;
	}

	public void setUsuarioAutorizacion(String usuarioAutorizacion) {
		this.usuarioAutorizacion = usuarioAutorizacion;
	}

	public Date getFechaInicialProcesamiento() {
		return fechaInicialProcesamiento;
	}

	public void setFechaInicialProcesamiento(Date fechaInicialProcesamiento) {
		this.fechaInicialProcesamiento = fechaInicialProcesamiento;
	}

	public Date getFechaFinalProcesamiento() {
		return fechaFinalProcesamiento;
	}

	public void setFechaFinalProcesamiento(Date fechaFinalProcesamiento) {
		this.fechaFinalProcesamiento = fechaFinalProcesamiento;
	}

	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public BitacoraFileTransferVO getBitacoraFileTransferVO() {
		return bitacoraFileTransferVO;
	}

	public void setBitacoraFileTransferVO(BitacoraFileTransferVO bitacoraFileTransferVO) {
		this.bitacoraFileTransferVO = bitacoraFileTransferVO;
	}

	public BitacoraFileTransferService getBitacoraFileTransferService() {
		return bitacoraFileTransferService;
	}

	public void setBitacoraFileTransferService(BitacoraFileTransferService bitacoraFileTransferService) {
		this.bitacoraFileTransferService = bitacoraFileTransferService;
	}

	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}
	
}
