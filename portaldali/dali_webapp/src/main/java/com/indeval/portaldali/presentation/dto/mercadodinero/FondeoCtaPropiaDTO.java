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
 * Data Transfer Object que representa los elemento de la pantalla de Fondeo a
 * Cuenta Propia de las Capturas de Operaciones
 * 
 * @author Marcos Rivas
 * @version 1.0
 */
public class FondeoCtaPropiaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Posicion asociada al Traspasante y su Emision */
	private PosicionDTO posicionTraspasante = new PosicionDTO();

	/** Cuenta asociada al Receptor */
	private CuentaDTO cuentaReceptor = new CuentaDTO();

	/** Cadena Asociada a los dias Vigentes */
	private Integer diasVigentes;

	/** Cadena asociada a l precio Vector */
	private Double precioVector;

	/** Cadena asociada al Saldo Disponible */
	private Long saldoDisponible;

	/** Cadena sociada al Simulado */
	private Long simulado;

	/** cadena asociada al neto Efectivo */
	private Double netoEfectivo;

	/** Cadena asociada a la fecha de Concertacion */
	private Date fechaConcertacion;
	
	/** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;

	/** Cadena asociada a la Cantidad Operada */
	private Long cantidadOperada;

	/** Integer asociado al valor del combo de valorEn */
	private DivisaDTO valorEn;
	
	/** La boveda del traspasante */
	private BovedaDTO boveda;

	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;

	/** El identificador y el folio del receptor */
	private String idFolioReceptor;

	/** Double asociada al importe */
	private Double importe;
	
	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

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

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
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
	 * @return the valorEn
	 */
	public DivisaDTO getValorEn() {
		return valorEn;
	}

	/**
	 * @param valorEn the valorEn to set
	 */
	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
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
	 * @return the idFolioReceptor
	 */
	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	/**
	 * @param idFolioReceptor
	 *            the idFolioReceptor to set
	 */
	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	/**
	 * @return the boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}
	
	

}

