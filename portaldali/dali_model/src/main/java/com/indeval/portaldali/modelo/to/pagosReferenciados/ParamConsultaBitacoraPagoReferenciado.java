/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.modelo.to.pagosReferenciados;

import java.io.Serializable;
import java.util.Date;

/**
 * Transfer object que representa los paramatros de consulta de la bitacora de pagos referenciados.
 * 
 * @author Pablo Balderas
 */
public class ParamConsultaBitacoraPagoReferenciado implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -813567456361330581L;
	
	/** Parametro folio preliquidador */
	private String folioPreliquidador;
	
	/** Parametro folio instruccion */
	private String folioInstruccion;
	
	/** Parametro importe */
	private String importe;
	
	/** Paramatro clave de rastreo */
	private String claveRastreo;
	
	/** Parametro fecha de registro inicial */
	private Date fechaRegistroInicial;
	
	/** Parametro fecha de registro final */
	private Date fechaRegistroFinal;
	
	/** Parametro estatus */
	private String estatus;
	
	/**
	 * Método para obtener el atributo folioPreliquidador
	 * @return El atributo folioPreliquidador
	 */
	public String getFolioPreliquidador() {
		return folioPreliquidador;
	}

	/**
	 * Método para establecer el atributo folioPreliquidador
	 * @param folioPreliquidador El valor del atributo folioPreliquidador a establecer.
	 */
	public void setFolioPreliquidador(String folioPreliquidador) {
		this.folioPreliquidador = folioPreliquidador;
	}

	/**
	 * Método para obtener el atributo folioInstruccion
	 * @return El atributo folioInstruccion
	 */
	public String getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Método para establecer el atributo folioInstruccion
	 * @param folioInstruccion El valor del atributo folioInstruccion a establecer.
	 */
	public void setFolioInstruccion(String folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Método para obtener el atributo importe
	 * @return El atributo importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * Método para establecer el atributo importe
	 * @param importe El valor del atributo importe a establecer.
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * Método para obtener el atributo claveRastreo
	 * @return El atributo claveRastreo
	 */
	public String getClaveRastreo() {
		return claveRastreo;
	}

	/**
	 * Método para establecer el atributo claveRastreo
	 * @param claveRastreo El valor del atributo claveRastreo a establecer.
	 */
	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	/**
	 * Método para obtener el atributo fechaRegistroInicial
	 * @return El atributo fechaRegistroInicial
	 */
	public Date getFechaRegistroInicial() {
		return fechaRegistroInicial;
	}

	/**
	 * Método para establecer el atributo fechaRegistroInicial
	 * @param fechaRegistroInicial El valor del atributo fechaRegistroInicial a establecer.
	 */
	public void setFechaRegistroInicial(Date fechaRegistroInicial) {
		this.fechaRegistroInicial = fechaRegistroInicial;
	}

	/**
	 * Método para obtener el atributo fechaRegistroFinal
	 * @return El atributo fechaRegistroFinal
	 */
	public Date getFechaRegistroFinal() {
		return fechaRegistroFinal;
	}

	/**
	 * Método para establecer el atributo fechaRegistroFinal
	 * @param fechaRegistroFinal El valor del atributo fechaRegistroFinal a establecer.
	 */
	public void setFechaRegistroFinal(Date fechaRegistroFinal) {
		this.fechaRegistroFinal = fechaRegistroFinal;
	}

	/**
	 * Método para obtener el atributo estatus
	 * @return El atributo estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * Método para establecer el atributo estatus
	 * @param estatus El valor del atributo estatus a establecer.
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
		
}
