/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.pagosReferenciados;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.pagosReferenciados.PagosReferenciadosService;
import com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado;
import com.indeval.portaldali.persistencia.pagosReferenciados.BitacoraPagosReferenciados;
import com.indeval.portaldali.presentation.controller.common.PaginacionBean;

/**
 * Controlador para la consulta de la bitacora de pagos referenciados.
 * 
 * @author Pablo Balderas
 */
public class ConsultaPagosReferenciadosBean extends PaginacionBean {

	/** Servicio de pagos referenciados */
	private PagosReferenciadosService pagosReferenciadosService;

	/** Lista de resultados */
	private List<BitacoraPagosReferenciados> resultados;

	/** Parametros de busqueda */
	private ParamConsultaBitacoraPagoReferenciado parametrosBusqueda;
	
	/** Lista con los estatus de pago referenciado */
	private List<SelectItem> catalogoEstatusPagoReferenciado;
	
    /** Indica si la consulta ha sido ejecutada */
    private boolean consultaEjecutada = false;
	
    /** Indica si la pantalla ya esta inicializada */
    private boolean banderaInicio = false;
    
	/** Estatus reconocido */ 
	final private String PR_ESTATUS_RECONOCIDO = "RE";
	
	/** Estatus no reconocido */
	final private String PR_ESTATUS_NO_RECONOCIDO = "NE";
	
	/** Estatus invalido */
	final private String PR_ESTATUS_INVALIDO = "IV";
	
	
	/**
	 * Inicializa la consulta.
	 * @return null
	 */
	public String getInit() {
        if(!banderaInicio) {
            consultaEjecutada = false;
            resultados = null;
            parametrosBusqueda = new ParamConsultaBitacoraPagoReferenciado();
            inicializarCatalogoEstatusPagoReferenciado();
            banderaInicio = true;
            this.paginaVO.setRegistrosXPag(20);
        }
		return null;
	}
	
    /**
     * Limpia todos los criterios de busqueda y los resultados previos.
     * @param event Evento generado por faces.
     */
    public void limpiar(final ActionEvent event) {
        banderaInicio = false;
        getInit();
        if( paginaVO != null ) {
            if(paginaVO.getRegistros() != null) {
                paginaVO.getRegistros().clear();
                paginaVO.setRegistros(null);
            }
            paginaVO.setOffset(0);
            paginaVO.setTotalRegistros(0);
            paginaVO.setRegistrosXPag(20);
        }
    }
	
	
    /**
     * Método que ejecuta la consulta.
     * @param event Evento generado por faces.
     */
    public void consultar(final ActionEvent event) {
        try {
            paginaVO.setOffset(0);
            ejecutarConsulta();
            consultaEjecutada = true;
        }
        catch(BusinessException e) {
            consultaEjecutada = false;
            addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
	
    /**
     * Método utilizado para la paginación de resultados.
     * @param event Evento generado por faces.
     */
    public void paginarResultados(final ActionEvent event) {
        ejecutarConsulta();
    }

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.common.PaginacionBean#ejecutarConsulta()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String ejecutarConsulta() {
		paginaVO.setRegistros(null);
		paginaVO = pagosReferenciadosService.findBitacoraPagosReferenciados(false, paginaVO, parametrosBusqueda);
		if(paginaVO != null && paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()) {
			resultados = (List<BitacoraPagosReferenciados>) paginaVO.getRegistros();
		}
		else {
			resultados = new ArrayList<BitacoraPagosReferenciados>();
		}
		return null;
	}
	
    /**
     * Indica si la consulta arrojo resultados.
     * @return true si hay resultados; false en otro caso.
     */
    public boolean isConsultaConResultados() {
        return this.resultados != null && !this.resultados.isEmpty();
    }

	/**
	 * Inicializa el catalogo de estatus de los pagos referenciados
	 */
	private void inicializarCatalogoEstatusPagoReferenciado() {
		catalogoEstatusPagoReferenciado = new ArrayList<SelectItem>();
		catalogoEstatusPagoReferenciado.add(new SelectItem(PR_ESTATUS_RECONOCIDO, PR_ESTATUS_RECONOCIDO));
		catalogoEstatusPagoReferenciado.add(new SelectItem(PR_ESTATUS_NO_RECONOCIDO, PR_ESTATUS_NO_RECONOCIDO));
		catalogoEstatusPagoReferenciado.add(new SelectItem(PR_ESTATUS_INVALIDO, PR_ESTATUS_INVALIDO));
	}

	/**
	 * Método para establecer el atributo pagosReferenciadosService
	 * @param pagosReferenciadosService El valor del atributo pagosReferenciadosService a establecer.
	 */
	public void setPagosReferenciadosService(PagosReferenciadosService pagosReferenciadosService) {
		this.pagosReferenciadosService = pagosReferenciadosService;
	}

	/**
	 * Método para obtener el atributo resultados
	 * @return El atributo resultados
	 */
	public List<BitacoraPagosReferenciados> getResultados() {
		return resultados;
	}


	/**
	 * Método para establecer el atributo resultados
	 * @param resultados El valor del atributo resultados a establecer.
	 */
	public void setResultados(List<BitacoraPagosReferenciados> resultados) {
		this.resultados = resultados;
	}


	/**
	 * Método para obtener el atributo parametrosBusqueda
	 * @return El atributo parametrosBusqueda
	 */
	public ParamConsultaBitacoraPagoReferenciado getParametrosBusqueda() {
		return parametrosBusqueda;
	}


	/**
	 * Método para establecer el atributo parametrosBusqueda
	 * @param parametrosBusqueda El valor del atributo parametrosBusqueda a establecer.
	 */
	public void setParametrosBusqueda(ParamConsultaBitacoraPagoReferenciado parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	/**
	 * Método para obtener el atributo consultaEjecutada
	 * @return El atributo consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * Método para establecer el atributo consultaEjecutada
	 * @param consultaEjecutada El valor del atributo consultaEjecutada a establecer.
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * Método para obtener el atributo banderaInicio
	 * @return El atributo banderaInicio
	 */
	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	/**
	 * Método para establecer el atributo banderaInicio
	 * @param banderaInicio El valor del atributo banderaInicio a establecer.
	 */
	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	/**
	 * Método para obtener el atributo catalogoEstatusPagoReferenciado
	 * @return El atributo catalogoEstatusPagoReferenciado
	 */
	public List<SelectItem> getCatalogoEstatusPagoReferenciado() {
		return catalogoEstatusPagoReferenciado;
	}

	/**
	 * Método para establecer el atributo catalogoEstatusPagoReferenciado
	 * @param catalogoEstatusPagoReferenciado El valor del atributo catalogoEstatusPagoReferenciado a establecer.
	 */
	public void setCatalogoEstatusPagoReferenciado(List<SelectItem> catalogoEstatusPagoReferenciado) {
		this.catalogoEstatusPagoReferenciado = catalogoEstatusPagoReferenciado;
	}
	
}
