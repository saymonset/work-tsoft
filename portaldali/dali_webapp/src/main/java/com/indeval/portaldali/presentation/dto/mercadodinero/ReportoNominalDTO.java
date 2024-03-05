/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * DTO que representa a los elementos de la pantalla de Traspasos de efectivo
 * entre cuentas control.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class ReportoNominalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El DTO que representara a la cuenta de la contraparte. */
	private CuentaDTO cuentaReceptor = new CuentaDTO();

	/** El DTO que representara a la cuenta de la contraparte. */
	private CalculoDTO calculo = new CalculoDTO();

	/** Integer relacionado al indice del radio mismoDia o fecha Valor */
	private Integer mismoDia;

	/** booleano relacionado con la compra en Reporto */
	private Boolean compraReporto;

	/**
	 * EL DTO que representa a la posicion, como son la cuenta, emision y boveda
	 * del participante.
	 */
	private PosicionDTO posicionTraspasante = new PosicionDTO();

	/** El saldo disponible */
	private Long saldoDisponible = null;

	/** La selección del combo Plazo Liquidación (horas) */
	private Integer plazoLiquidacionHoras;

	/** Los días vigentes */
	private Integer diasVigentes;

	/** mensage */
	private String mensage;

	/** El folio de la operación */
	private String folioOperacion;

	/** Fecha de concertación */
	private Date fechaConcertacion;

	/** Fecha de liquidación */
	private Date fechaLiquidacion;
	
	/** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;

	/** El efectivo con el que dispone el usuario loogeado */
	private Double netoEfectivo;

	/** ISIN */
	private String isin;

	/** El precio vector. */
	private Double precioVector;

	/**
	 * Auxiliar para el campo participante traspasante (El id y folio del
	 * participante).
	 */
	private String idFolioTraspasante;

	/**
	 * Auxiliar para el campo traspasante receptor (El id y folio del
	 * participante)..
	 */
	private String IdFolioReceptor;

	/* Campos con resultados de los calculos */

	/** Plazo representado en días */
	private Integer plazoRepDias;

	/** Tasa premio */
	private Double tasaPremio;

	/** La cantidad operada */
	private Long cantidadOperada;

	/** El precio del título */
	private Double precioTitulo;

	/** El saldo de Efectivo que resultara al restar el importe a traspasar */
	private Long simulado;

	/** Fecha de regreso */
	private Date fechaRegreso;

	/** El importe a traspasar entre las cuentas */
	private Double importe;

	/** El premio */
	private Double premio;

	/** Boveda traspasante */
	private BovedaDTO bovedaEfectivo = new BovedaDTO();

	/** Divisa del valor nominal */
	private DivisaDTO divisa = new DivisaDTO();

	/** ******* métodos getters & setters. */

	/**
	 * @return the posicionTraspasante
	 */
	public PosicionDTO getPosicionTraspasante() {
		return posicionTraspasante;
	}

	/**
	 * @param posicionTraspasante
	 *            the posicionTraspasante to set
	 */
	public void setPosicionTraspasante(PosicionDTO posicionTraspasante) {
		this.posicionTraspasante = posicionTraspasante;
	}

	/**
	 * @return the saldoDisponible
	 */
	public Long getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 *            the saldoDisponible to set
	 */
	public void setSaldoDisponible(Long saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return the mensage
	 */
	public String getMensage() {
		return mensage;
	}

	/**
	 * @param mensage
	 *            the mensage to set
	 */
	public void setMensage(String mensage) {
		this.mensage = mensage;
	}

	/**
	 * @return the folioOperacion
	 */
	public String getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * @param folioOperacion
	 *            the folioOperacion to set
	 */
	public void setFolioOperacion(String folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * @return the fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 *            the fechaConcertacion to set
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * @return the plazoLiquidacionHoras
	 */
	public Integer getPlazoLiquidacionHoras() {
		return plazoLiquidacionHoras;
	}

	/**
	 * @param plazoLiquidacionHoras
	 *            the plazoLiquidacionHoras to set
	 */
	public void setPlazoLiquidacionHoras(Integer plazoLiquidacionHoras) {
		this.plazoLiquidacionHoras = plazoLiquidacionHoras;
	}

	/**
	 * @return the netoEfectivo
	 */
	public Double getNetoEfectivo() {
		return netoEfectivo;
	}

	/**
	 * @param netoEfectivo
	 *            the netoEfectivo to set
	 */
	public void setNetoEfectivo(Double netoEfectivo) {
		this.netoEfectivo = netoEfectivo;
	}

	/**
	 * @return the calculo
	 */
	public CalculoDTO getCalculo() {
		return calculo;
	}

	/**
	 * @param calculo
	 *            the calculo to set
	 */
	public void setCalculo(CalculoDTO calculo) {
		this.calculo = calculo;
	}

	/**
	 * @return the diasVigentes
	 */
	public Integer getDiasVigentes() {
		return diasVigentes;
	}

	/**
	 * @param diasVigentes
	 *            the diasVigentes to set
	 */
	public void setDiasVigentes(Integer diasVigentes) {
		this.diasVigentes = diasVigentes;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 *            the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the precioVector
	 */
	public Double getPrecioVector() {
		return precioVector;
	}

	/**
	 * @param precioVector
	 *            the precioVector to set
	 */
	public void setPrecioVector(Double precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * @return the idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * @param idFolioTraspasante
	 *            the idFolioTraspasante to set
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * @return the mismoDia
	 */
	public Integer getMismoDia() {
		return mismoDia;
	}

	/**
	 * @param mismoDia
	 *            the mismoDia to set
	 */
	public void setMismoDia(Integer mismoDia) {
		this.mismoDia = mismoDia;
	}

	/**
	 * @return the compraReporto
	 */
	public Boolean getCompraReporto() {
		return compraReporto;
	}

	/**
	 * @param compraReporto
	 *            the compraReporto to set
	 */
	public void setCompraReporto(Boolean compraReporto) {
		this.compraReporto = compraReporto;
	}

	/**
	 * @return the plazoRepDias
	 */
	public Integer getPlazoRepDias() {
		return plazoRepDias;
	}

	/**
	 * @param plazoRepDias
	 *            the plazoRepDias to set
	 */
	public void setPlazoRepDias(Integer plazoRepDias) {
		this.plazoRepDias = plazoRepDias;
	}

	/**
	 * @return the tasaPremio
	 */
	public Double getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio
	 *            the tasaPremio to set
	 */
	public void setTasaPremio(Double tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return the cantidadOperada
	 */
	public Long getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 *            the cantidadOperada to set
	 */
	public void setCantidadOperada(Long cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return the precioTitulo
	 */
	public Double getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 *            the precioTitulo to set
	 */
	public void setPrecioTitulo(Double precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return the simulado
	 */
	public Long getSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 *            the simulado to set
	 */
	public void setSimulado(Long simulado) {
		this.simulado = simulado;
	}

	/**
	 * @return the fechaRegreso
	 */
	public Date getFechaRegreso() {
		return fechaRegreso;
	}

	/**
	 * @param fechaRegreso
	 *            the fechaRegreso to set
	 */
	public void setFechaRegreso(Date fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}

	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * @return the premio
	 */
	public Double getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 *            the premio to set
	 */
	public void setPremio(Double premio) {
		this.premio = premio;
	}

	/**
	 * @return the bovedaEfectivo
	 */
	public BovedaDTO getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @param bovedaEfectivo
	 *            the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(BovedaDTO bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * @return the divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the idFolioReceptor
	 */
	public String getIdFolioReceptor() {
		return IdFolioReceptor;
	}

	/**
	 * @param idFolioReceptor
	 *            the idFolioReceptor to set
	 */
	public void setIdFolioReceptor(String idFolioReceptor) {
		IdFolioReceptor = idFolioReceptor;
	}

	/**
	 * @return the cuentaReceptor
	 */
	public CuentaDTO getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * @param cuentaReceptor
	 *            the cuentaReceptor to set
	 */
	public void setCuentaReceptor(CuentaDTO cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

}