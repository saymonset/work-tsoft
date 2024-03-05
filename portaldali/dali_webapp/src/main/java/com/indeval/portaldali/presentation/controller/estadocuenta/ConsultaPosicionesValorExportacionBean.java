/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.math.BigDecimal;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistencia.posicion.Posicion;
import com.indeval.portaldali.persistencia.posicion.VPosicionControlada;
import com.indeval.portaldali.persistencia.posicion.VPosicionControladaH;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombrada;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombradaH;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaDePosiciones;

/**
 * Controlador para la exportación de los reportes de la Consulta de Posicion.
 * 
 * @author Pablo Balderas
 */
public class ConsultaPosicionesValorExportacionBean extends BackingBeanBase {

    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "consultaPosicionesBean";
	
    /** Bean con los datos para la consulta */
    private ConsultaPosicionesValorBean bean;
    
	/** Resultados de la consulta */
	private List<Posicion> resultados = null;
	
	/** Mapa con los totales */
	private TotalesPosicionTO resumenPosicionConsulta = null;
	
	/** Objeto que representa un esquema de consulta */
	private ConsultaDePosiciones consultaPosiciones = null;
	
	/** Almacena la clave y folio de la institucion */
	private String folioClaveInstitucion;
	
	/** Constante de subtotal */
	private final String SUBTOTAL = "SUBTOTAL";
	
	/**
	 * Constructor de la clase.
	 */
	public ConsultaPosicionesValorExportacionBean() {
		//Obtine el bean para sacar los parametros de busqueda
		if(bean == null) {			
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			bean = (ConsultaPosicionesValorBean) FacesContext.getCurrentInstance().getApplication().
				getELResolver().getValue(elContext, null, this.BEAN_CONSULTA);
		}
	}
	
	/**
	 * Genera los reportes.
	 * @param e Evento de faces
	 */
	public void generarReportes(ActionEvent e) {
		reiniciarEstadoPeticion();
		folioClaveInstitucion = bean.getFolioClaveInstitucion();
		consultaPosiciones = bean.getConsultaPosiciones();
		//Obtiene la consulta sin paginacion
		resultados = consultaPosiciones.consultarPosicion(false);
		//Obtiene los totales calculados en la consulta
		if("C".equals(consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getOpcionSeleccionada().getId())) {
			resumenPosicionConsulta = new TotalesPosicionTO();
			resumenPosicionConsulta.setPosicionTotal(bean.getResumenPosicionConsulta().getPosicionTotal());
		}
		else {			
			resumenPosicionConsulta = bean.getResumenPosicionConsulta();
		}
		Posicion resumenTipoValor = null;
		String tipoValorEnTurno = null;
		BigDecimal subtotalPosicion = new BigDecimal("0.0");
		BigDecimal subtotalDisponible = new BigDecimal("0.0");
		BigDecimal subtotalNoDisponible = new BigDecimal("0.0");
		BigDecimal subtotalValuacion = new BigDecimal("0.0");
		
		for(int i=0; i<resultados.size(); i++) {
			Posicion posicion = resultados.get(i);
			if(posicion instanceof VPosicionControlada || posicion instanceof VPosicionControladaH) {
				posicion.setValuacion(0D);
			}
			if(tipoValorEnTurno == null) {
				tipoValorEnTurno = posicion.getTv();
			}
			if(!posicion.getTv().equals(tipoValorEnTurno)) {
				resumenTipoValor = new Posicion();
				resumenTipoValor.setCuenta(SUBTOTAL);
				resumenTipoValor.setTv(tipoValorEnTurno);
				resumenTipoValor.setEmisora(StringUtils.EMPTY);
				resumenTipoValor.setPosicion(subtotalPosicion.longValue());
				resumenTipoValor.setPosicionDisponible(subtotalDisponible.longValue());
				resumenTipoValor.setPosicionNoDisponible(subtotalNoDisponible.longValue());
				resumenTipoValor.setValorNominal(0D);
				resumenTipoValor.setValuacion(subtotalValuacion.doubleValue());
				
				subtotalPosicion = new BigDecimal("0.0");
				subtotalNoDisponible = new BigDecimal("0.0");
				subtotalDisponible = new BigDecimal("0.0");
				subtotalValuacion = new BigDecimal("0.0");
				
				resultados.add(i++, resumenTipoValor);
				tipoValorEnTurno = posicion.getTv();
			}
			subtotalPosicion = subtotalPosicion.add(new BigDecimal(posicion.getPosicion()));
			if(posicion instanceof VPosicionNombrada || posicion instanceof VPosicionNombradaH) {
				subtotalDisponible = subtotalDisponible.add(new BigDecimal(posicion.getPosicionDisponible()));
				subtotalNoDisponible = subtotalNoDisponible.add(new BigDecimal(posicion.getPosicionNoDisponible()));
			}
			subtotalValuacion = subtotalValuacion.add(new BigDecimal(posicion.getValuacion()));
		}
		resumenTipoValor = new Posicion();
		resumenTipoValor.setCuenta(SUBTOTAL);
		resumenTipoValor.setTv(tipoValorEnTurno);
		resumenTipoValor.setEmisora(StringUtils.EMPTY);
		resumenTipoValor.setPosicion(subtotalPosicion.longValue());
		resumenTipoValor.setPosicionDisponible(subtotalDisponible.longValue());
		resumenTipoValor.setPosicionNoDisponible(subtotalNoDisponible.longValue());
		resumenTipoValor.setValorNominal(0D);
		resumenTipoValor.setValuacion(subtotalValuacion.doubleValue());
		resultados.add(resumenTipoValor);
	}

	/**
	 * Obtiene la descripción del atributo papelMercado.
	 * 
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado() {
		return bean.getDescripcionPapelMercado();
	}
	
	/**
	 * Método para obtener el atributo resultados
	 * @return El atributo resultados
	 */
	public List<Posicion> getResultados() {
		return resultados;
	}

	/**
	 * Método para establecer el atributo resultados
	 * @param resultados El valor del atributo resultados a establecer.
	 */
	public void setResultados(List<Posicion> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Método para obtener el atributo consultaPosiciones
	 * @return El atributo consultaPosiciones
	 */
	public ConsultaDePosiciones getConsultaPosiciones() {
		return consultaPosiciones;
	}

	/**
	 * Método para establecer el atributo consultaPosiciones
	 * @param consultaPosiciones El valor del atributo consultaPosiciones a establecer.
	 */
	public void setConsultaPosiciones(ConsultaDePosiciones consultaPosiciones) {
		this.consultaPosiciones = consultaPosiciones;
	}

	/**
	 * Método para obtener el atributo folioClaveInstitucion
	 * @return El atributo folioClaveInstitucion
	 */
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	/**
	 * Método para establecer el atributo folioClaveInstitucion
	 * @param folioClaveInstitucion El valor del atributo folioClaveInstitucion a establecer.
	 */
	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;
	}

	/**
	 * Método para obtener el atributo resumenPosicionConsulta
	 * @return El atributo resumenPosicionConsulta
	 */
	public TotalesPosicionTO getResumenPosicionConsulta() {
		return resumenPosicionConsulta;
	}

	/**
	 * Método para establecer el atributo resumenPosicionConsulta
	 * @param resumenPosicionConsulta El valor del atributo resumenPosicionConsulta a establecer.
	 */
	public void setResumenPosicionConsulta(TotalesPosicionTO resumenPosicionConsulta) {
		this.resumenPosicionConsulta = resumenPosicionConsulta;
	}
	
}
