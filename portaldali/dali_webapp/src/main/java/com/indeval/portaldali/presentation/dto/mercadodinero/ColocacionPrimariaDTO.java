/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 11/04/2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * Data Transfer Object que representa los elemento de la pantalla de Colocacion
 * Primaria de las Capturas de Operaciones
 * 
 * @author Marcos Rivas
 * @version 1.0
 */
public class ColocacionPrimariaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Integer relacionado al indice del radio mismoDia o fecha Valor */
	private Integer mismoDia;

	/** booleano relacionado con la compra en Directo */
	private Boolean compraDirecto;

	/** Posicion asociada al Traspasante y su Emision */
	private PosicionDTO posicionTraspasante = new PosicionDTO();

	/** Cuenta asociada al Receptor */
	private CuentaDTO cuentaReceptor = new CuentaDTO();

	/** Integer Asociada a los dias Vigentes */
	private Integer diasVigentes;

	/** BigDecimal asociada a l precio Vector */
	private Double precioVector;

	/** Integer asociada al Saldo Disponible */
	private Long saldoDisponible;

	/** BigInteger sociada al Simulado */
	private Long simulado;

	/** BigDecimal asociada al neto Efectivo */
	private Double netoEfectivo;

	/** Date asociada a la fecha de Concertacion */
	private Date fechaConcertacion;
	
	/** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;
	
	/** Date asociada a la fecha de Liquidacion */
	private Date fechaLiquidacion;

	/** Cadena asociada a la Cantidad Operada */
	private Long cantidadOperada;

	/** Integer asociado al valor del combo de valorEn */
	private DivisaDTO valorEn;
	
	/** La boveda de efectivo */
	private BovedaDTO bovedaEfectivo;

	/** Cadena asociada al precio de Titulo */
	private Double precioTitulo;

	/** Cadena asociada al importe */
	private Double importe;

	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;

	/** El identificador y el folio del receptor */
	private String idFolioReceptor;

	/** Integer asociado al valor del combo de Plazo Liquidacion (hrs) */
	private Integer plazoLiquidacionHoras;

	/**
	 * Obtiene el campo mismoDia
	 * @return  mismoDia
	 */
	public Integer getMismoDia() {
		return mismoDia;
	}

	/**
	 * Asigna el campo mismoDia
	 * @param mismoDia el valor de mismoDia a asignar
	 */
	public void setMismoDia(Integer mismoDia) {
		this.mismoDia = mismoDia;
	}

	/**
	 * Obtiene el campo compraDirecto
	 * @return  compraDirecto
	 */
	public Boolean getCompraDirecto() {
		return compraDirecto;
	}

	/**
	 * Asigna el campo compraDirecto
	 * @param compraDirecto el valor de compraDirecto a asignar
	 */
	public void setCompraDirecto(Boolean compraDirecto) {
		this.compraDirecto = compraDirecto;
	}

	/**
	 * Obtiene el campo posicionTraspasante
	 * @return  posicionTraspasante
	 */
	public PosicionDTO getPosicionTraspasante() {
		return posicionTraspasante;
	}

	/**
	 * Asigna el campo posicionTraspasante
	 * @param posicionTraspasante el valor de posicionTraspasante a asignar
	 */
	public void setPosicionTraspasante(PosicionDTO posicionTraspasante) {
		this.posicionTraspasante = posicionTraspasante;
	}

	/**
	 * Obtiene el campo cuentaReceptor
	 * @return  cuentaReceptor
	 */
	public CuentaDTO getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * Asigna el campo cuentaReceptor
	 * @param cuentaReceptor el valor de cuentaReceptor a asignar
	 */
	public void setCuentaReceptor(CuentaDTO cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
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
	 * Obtiene el campo precioVector
	 * @return  precioVector
	 */
	public Double getPrecioVector() {
		return precioVector;
	}

	/**
	 * Asigna el campo precioVector
	 * @param precioVector el valor de precioVector a asignar
	 */
	public void setPrecioVector(Double precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * Obtiene el campo saldoDisponible
	 * @return  saldoDisponible
	 */
	public Long getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * Asigna el campo saldoDisponible
	 * @param saldoDisponible el valor de saldoDisponible a asignar
	 */
	public void setSaldoDisponible(Long saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * Obtiene el campo simulado
	 * @return  simulado
	 */
	public Long getSimulado() {
		return simulado;
	}

	/**
	 * Asigna el campo simulado
	 * @param simulado el valor de simulado a asignar
	 */
	public void setSimulado(Long simulado) {
		this.simulado = simulado;
	}

	/**
	 * Obtiene el campo netoEfectivo
	 * @return  netoEfectivo
	 */
	public Double getNetoEfectivo() {
		return netoEfectivo;
	}

	/**
	 * Asigna el campo netoEfectivo
	 * @param netoEfectivo el valor de netoEfectivo a asignar
	 */
	public void setNetoEfectivo(Double netoEfectivo) {
		this.netoEfectivo = netoEfectivo;
	}

	/**
	 * Obtiene el campo fechaConcertacion
	 * @return  fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Asigna el campo fechaConcertacion
	 * @param fechaConcertacion el valor de fechaConcertacion a asignar
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * Obtiene el campo cantidadOperada
	 * @return  cantidadOperada
	 */
	public Long getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * Asigna el campo cantidadOperada
	 * @param cantidadOperada el valor de cantidadOperada a asignar
	 */
	public void setCantidadOperada(Long cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * Obtiene el campo valorEn
	 * @return  valorEn
	 */
	public DivisaDTO getValorEn() {
		return valorEn;
	}

	/**
	 * Asigna el campo valorEn
	 * @param valorEn el valor de valorEn a asignar
	 */
	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
	}

	/**
	 * Obtiene el campo precioTitulo
	 * @return  precioTitulo
	 */
	public Double getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * Asigna el campo precioTitulo
	 * @param precioTitulo el valor de precioTitulo a asignar
	 */
	public void setPrecioTitulo(Double precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * Obtiene el campo importe
	 * @return  importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * Asigna el campo importe
	 * @param importe el valor de importe a asignar
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * Obtiene el campo idFolioTraspasante
	 * @return  idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * Asigna el campo idFolioTraspasante
	 * @param idFolioTraspasante el valor de idFolioTraspasante a asignar
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * Obtiene el campo idFolioReceptor
	 * @return  idFolioReceptor
	 */
	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	/**
	 * Asigna el campo idFolioReceptor
	 * @param idFolioReceptor el valor de idFolioReceptor a asignar
	 */
	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	/**
	 * Obtiene el campo plazoLiquidacionHoras
	 * @return  plazoLiquidacionHoras
	 */
	public Integer getPlazoLiquidacionHoras() {
		return plazoLiquidacionHoras;
	}

	/**
	 * Asigna el campo plazoLiquidacionHoras
	 * @param plazoLiquidacionHoras el valor de plazoLiquidacionHoras a asignar
	 */
	public void setPlazoLiquidacionHoras(Integer plazoLiquidacionHoras) {
		this.plazoLiquidacionHoras = plazoLiquidacionHoras;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	} 

	/**
	 * Obtiene el campo bovedaEfectivo
	 * @return  bovedaEfectivo
	 */
	public BovedaDTO getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * Asigna el campo bovedaEfectivo
	 * @param bovedaEfectivo el valor de bovedaEfectivo a asignar
	 */
	public void setBovedaEfectivo(BovedaDTO bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}
	

	/** ******* métodos getters & setters. */

}
