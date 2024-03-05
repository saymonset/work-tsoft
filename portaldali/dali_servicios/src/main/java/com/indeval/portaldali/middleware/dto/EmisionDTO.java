/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object que representa una emisión.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class EmisionDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private SerieDTO serie = new SerieDTO();
	
	
	/** El tipo de valor de la emisión */
	private TipoValorDTO tipoValor = new TipoValorDTO();
	
	/** La emisora asociada con la emisión */
	private EmisoraDTO emisora = new EmisoraDTO();
	
	/** El cupón de la emisión */
	private String cupon = null;
	
	/** El id de la emisión */
	private long id;
	
	/** El ISIN de la emisión */
	private String isin;
	
	/** El valor nominal de la emisión */
	private double valorNominal = 0;
	/**
	 * días vigentes de la emisión
	 */
	private Integer diasVigentes = null;
	/**
	 * Fecha de vencimiento de la emisión
	 */
	private Date fechaVencimiento = null;
	/** Divisa del valor nominal */
	private DivisaDTO divisa = new DivisaDTO();
	
	private BovedaDTO boveda = new BovedaDTO();
	
	/**
	 * Constructor de la clase
	 */
	public EmisionDTO(){}
	
	/**
	 * Constructor de la clase
	 * @param id Id de la emisión
	 */
	public EmisionDTO(long id) {
		super();
		this.id = id;
	}



	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}
	/**
	 * método para obtener el atributo serie
	 * 
	 * @return the serie
	 */
	public SerieDTO getSerie() {
		return serie;
	}

	/**
	 * método para establecer el atributo serie
	 * 
	 * @param serie the serie to set
	 */
	public void setSerie(SerieDTO serie) {
		this.serie = serie;
	}

	/**
	 * método para obtener el atributo tipoValor
	 * 
	 * @return the tipoValor
	 */
	public TipoValorDTO getTipoValor() {
		return tipoValor;
	}

	/**
	 * método para establecer el atributo tipoValor
	 * 
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(TipoValorDTO tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * método para obtener el atributo emisora
	 * 
	 * @return the emisora
	 */
	public EmisoraDTO getEmisora() {
		return emisora;
	}

	/**
	 * método para establecer el atributo emisora
	 * 
	 * @param emisora the emisora to set
	 */
	public void setEmisora(EmisoraDTO emisora) {
		this.emisora = emisora;
	}

	/**
	 * método para obtener el atributo id
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * método para establecer el atributo id
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * método para obtener el atributo isin
	 * 
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * método para establecer el atributo isin
	 * 
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Obtiene el campo cupon
	 * @return  cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * Asigna el valor del campo cupon
	 * @param cupon el valor de cupon a asignar
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el campo valorNominal
	 * @return  valorNominal
	 */
	public double getValorNominal() {
		return valorNominal;
	}

	/**
	 * Asigna el valor del campo valorNominal
	 * @param valorNominal el valor de valorNominal a asignar
	 */
	public void setValorNominal(double valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * Obtiene el atributo divisa
	 *
	 * @return El atrubuto divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Establece la propiedad divisa
	 *
	 * @param divisa el campo divisa a establecer
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el campo diasVigentes
	 * @return  diasVigentes
	 */
	public Integer getDiasVigentes() {
		return diasVigentes;
	}

	/**
	 * Asigna el campo diasVigentes
	 * @param diasVigentes el valor de diasVigentes a asignar
	 */
	public void setDiasVigentes(Integer diasVigentes) {
		this.diasVigentes = diasVigentes;
	}

	/**
	 * Obtiene el campo fechaVencimiento
	 * @return  fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * Asigna el campo fechaVencimiento
	 * @param fechaVencimiento el valor de fechaVencimiento a asignar
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	@Override
	public String toString() {
		return "EmisionDTO{" +
				"serie=" + serie +
				", tipoValor=" + tipoValor +
				", emisora=" + emisora +
				", cupon='" + cupon + '\'' +
				", id=" + id +
				", isin='" + isin + '\'' +
				", valorNominal=" + valorNominal +
				", diasVigentes=" + diasVigentes +
				", fechaVencimiento=" + fechaVencimiento +
				", divisa=" + divisa +
				", boveda=" + boveda +
				'}';
	}
}