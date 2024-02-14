package com.indeval.portalinternacional.presentation.controller;

import java.util.Date;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BitacoraFileTransferService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class BitacoraFileTransferExportacionController extends ControllerBase {
	
	private String usuarioRegistro;
	private String usuarioAutorizacion;
	private Date fechaInicialProcesamiento;
	private Date fechaFinalProcesamiento;
	private Date fechaProcesamiento;
	private PaginaVO resultados;
	private BitacoraFileTransferVO bitacoraFileTransferVO = new BitacoraFileTransferVO();
	
	private BitacoraFileTransferService bitacoraFileTransferService;
	private DateUtilService dateUtilService;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BitacoraFileTransferExportacionController.class);
	
	public BitacoraFileTransferExportacionController () {
	
		
		
	}
	
	public void crearFiltros() {
		
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		BitacoraFileTransferController consultaBean = (BitacoraFileTransferController)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "bitacoraFileTransferBean");
		this.bitacoraFileTransferVO = consultaBean.getBitacoraFileTransferVO();
		
		if (bitacoraFileTransferVO.getUsRegistro() != null && !bitacoraFileTransferVO.getUsRegistro().isEmpty())
			this.usuarioRegistro = consultaBean.getUsuarioRegistro();
		else
			this.usuarioRegistro = "-";
		
		if (bitacoraFileTransferVO.getUsAutoriza() != null && !bitacoraFileTransferVO.getUsAutoriza().isEmpty())
			this.usuarioAutorizacion = consultaBean.getUsuarioAutorizacion();
		else 
			this.usuarioAutorizacion = "-";
	
		if (bitacoraFileTransferVO.getFechaProcesamiento() != null) {
			
			if (bitacoraFileTransferVO.getFechaProcesamiento()[0] != null && bitacoraFileTransferVO.getFechaProcesamiento()[1] != null) {
				
				this.fechaInicialProcesamiento = consultaBean.getFechaInicialProcesamiento();
				this.fechaFinalProcesamiento = consultaBean.getFechaFinalProcesamiento();
				
			}else {
				
				this.fechaInicialProcesamiento = null;
				this.fechaFinalProcesamiento = null;
				
			}
			
		}else {
			
			this.fechaInicialProcesamiento = null;
			this.fechaFinalProcesamiento = null;
			
		}
		
	}
	
	public String getInit() {

		
		
		return null;
		
	}
	
	public void generarReportes (ActionEvent e) {
		
		crearFiltros();
		reiniciarEstadoPeticion();
		try {
            this.resultados = new PaginaVO();
            this.resultados.setOffset(0);
            Integer registrosXPag = Constantes.MAX_REGISTROS_EXPORTAR;
            this.resultados.setRegistrosXPag(registrosXPag);
            this.resultados =
                    this.bitacoraFileTransferService.consultarFileTransfer(bitacoraFileTransferVO, paginaVO, false);
            this.resultados.setTotalRegistros(this.resultados.getRegistros().size());
		}
		catch (BusinessException exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
		}
		catch (Exception exc) {
			log.error("Ocurrio un error:",exc);
			agregarInfoMensaje(exc.getMessage());
		}
		catch (Throwable exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
		}	
		
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

	public PaginaVO getResultados() {
		return resultados;
	}

	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
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
