/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.pagosReferenciados;

import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.constantes.ConstantesComunes;
import com.indeval.portaldali.middleware.services.pagosReferenciados.PagosReferenciadosService;
import com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado;
import com.indeval.portaldali.persistencia.pagosReferenciados.BitacoraPagosReferenciados;
import com.indeval.portaldali.presentation.controller.common.PaginacionBean;

/**
 * Controlador para generar los reportes de la consulta de pagos referenciados.
 * 
 * @author Pablo Balderas
 */
public class ConsultaPagosReferenciadosExportacionBean extends PaginacionBean {

	/** Servicio de pagos referenciados */
	private PagosReferenciadosService pagosReferenciadosService;

	/** Parametros de busqueda */
	private ParamConsultaBitacoraPagoReferenciado parametrosBusqueda;
	
    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "consultaPagosReferenciados";
	
	/** Lista de resultados */
	private List<BitacoraPagosReferenciados> resultados;

	/** Constructor de la clase */
	public ConsultaPagosReferenciadosExportacionBean() {}
	
    /**
     * Método que realiza la exportación de la consulta a un documento PDF.
     * @param e Acción generada por faces.
     */
    public void generarExportacionPDF(final ActionEvent e) {
        this.generarExportacion();
    }

    /**
     * Método que realiza la exportación de la consulta a un documento XLS.
     * @param e Acción generada por faces.
     */
    public void generarExportacionXLS(final ActionEvent e) {
        this.generarExportacion();
    }
    
    /**
     * Método que genera la exportación.
     */
    @SuppressWarnings("unchecked")
	private void generarExportacion() {
        try {
            this.reiniciarEstadoPeticion();
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ConsultaPagosReferenciadosBean consultaPagosReferenciadosBean =
            	(ConsultaPagosReferenciadosBean) FacesContext.getCurrentInstance().getApplication().
                    getELResolver().getValue(elContext, null, this.BEAN_CONSULTA);
            parametrosBusqueda = consultaPagosReferenciadosBean.getParametrosBusqueda();
            //Prepara el objeto de paginación
            paginaVO.setOffset(0);
            paginaVO.setRegistros(null);
//            paginaVO.setRegistrosXPag(ExportacionConstantes.MAX_REGISTROS_EXPORTAR);
            paginaVO.setTotalRegistros(consultaPagosReferenciadosBean.getPaginaVO().getTotalRegistros());
            //Realiza la consulta
    		paginaVO = pagosReferenciadosService.findBitacoraPagosReferenciados(true, paginaVO, parametrosBusqueda);
    		if(paginaVO != null && paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()) {
    			resultados = (List<BitacoraPagosReferenciados>) paginaVO.getRegistros();
    		}
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }
    
    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public String getDescParamFolioPreliquidador() {
		return StringUtils.isNotBlank(parametrosBusqueda.getFolioPreliquidador()) ?
			parametrosBusqueda.getFolioPreliquidador() : ConstantesComunes.TODO;
	}

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public String getDescParamFolioInstruccion() {
		return StringUtils.isNotBlank(parametrosBusqueda.getFolioInstruccion()) ?
			parametrosBusqueda.getFolioInstruccion() : ConstantesComunes.TODO;
	}

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public String getDescParamImporte() {
		return StringUtils.isNotBlank(parametrosBusqueda.getImporte()) ?
			parametrosBusqueda.getImporte() : ConstantesComunes.TODO;
	}

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public String getDescParamClaveRastreo() {
		return StringUtils.isNotBlank(parametrosBusqueda.getImporte()) ?
				parametrosBusqueda.getImporte() : ConstantesComunes.TODO;
	}
	
    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public Object getDescParamFechaRegistroInicial() {
        return parametrosBusqueda.getFechaRegistroInicial() != null ?
            parametrosBusqueda.getFechaRegistroInicial() : ConstantesComunes.TODO;
	}
	

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public Object getDescParamFechaRegistroFinal() {
        return parametrosBusqueda.getFechaRegistroFinal() != null ?
            parametrosBusqueda.getFechaRegistroFinal() : ConstantesComunes.TODO;
	}

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; cadena todo en caso contrario
     */
	public String getDescParamEstatus() {
		return StringUtils.isNotBlank(parametrosBusqueda.getEstatus()) ?
			parametrosBusqueda.getEstatus() : ConstantesComunes.TODO;
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
	 * Método para establecer el atributo pagosReferenciadosService
	 * @param pagosReferenciadosService El valor del atributo pagosReferenciadosService a establecer.
	 */
	public void setPagosReferenciadosService(PagosReferenciadosService pagosReferenciadosService) {
		this.pagosReferenciadosService = pagosReferenciadosService;
	}
    

	
}
