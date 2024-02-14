package com.indeval.portalinternacional.presentation.controller;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BitacoraFileTransferService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.controller.common.DetalleFileTransferController;

public class DetalleFileTransferExportacionController extends ControllerBase {
	
	private Long idFileTransferDivisas;
	private PaginaVO resultados;
	private BitacoraFileTransferService bitacoraFileTransferService;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(DetalleFileTransferExportacionController.class);
	
	public DetalleFileTransferExportacionController () {
		
		
		
	}
	
	public void generarReportes (ActionEvent e) {
		
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		DetalleFileTransferController detalleBean = (DetalleFileTransferController)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "detalleFileTransferDivisasBean");
		
		idFileTransferDivisas = detalleBean.getIdFileTransferDivisasInt();
		
		try {
            this.resultados = new PaginaVO();
            this.resultados.setOffset(0);
            Integer registrosXPag = Constantes.MAX_REGISTROS_EXPORTAR;
            this.resultados.setRegistrosXPag(registrosXPag);
            this.resultados = this.bitacoraFileTransferService.consultarDetalleFileTransfer(idFileTransferDivisas, paginaVO, false);
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
	
	public Long getIdFileTransferDivisas() {
		return idFileTransferDivisas;
	}

	public void setIdFileTransferDivisas(Long idFileTransferDivisas) {
		this.idFileTransferDivisas = idFileTransferDivisas;
	}

	public PaginaVO getResultados() {
		return resultados;
	}

	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}

	public BitacoraFileTransferService getBitacoraFileTransferService() {
		return bitacoraFileTransferService;
	}

	public void setBitacoraFileTransferService(BitacoraFileTransferService bitacoraFileTransferService) {
		this.bitacoraFileTransferService = bitacoraFileTransferService;
	}
	
}
