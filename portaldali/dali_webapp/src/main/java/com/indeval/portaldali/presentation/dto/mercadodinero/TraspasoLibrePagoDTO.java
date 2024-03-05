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
 * DTO que representa a los elementos de la pantalla de captura de operaciones
 * para el tipo de operacion Traspaso libre de pago.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class TraspasoLibrePagoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El DTO que representara a la cuenta de la contraparte. */
	private CuentaDTO cuentaReceptor = new CuentaDTO();

	/**
	 * EL DTO que representa a la posicion, como son la cuenta, emision y boveda
	 * del participante.
	 */
	private PosicionDTO posicionTraspasante = new PosicionDTO();

	/** El DTO que representara a la cuenta de la contraparte. */
	private CalculoDTO calculo = new CalculoDTO();

	/** El identificador del tipo de Operacion */
	private long idTipoOperacion = 0;

	/** El saldo disponible */
	private Long saldoDisponible = null;

	/** El saldo de Efectivo que resultara al restar el importe a traspasar */
	private Long simulado;

	/** El folio de la operación */
	private String folioOperacion;

	/** Fecha de concertación */
	private Date fechaConcertacion;

	/** Fecha de liquidacion */
	private Date fechaLiquidacion;
	
	/** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;
	
	/** Plazo de Liquidacion */
	private Integer plazoLiquidacionHoras;
	
	/** indica si la operacion es mismo dia */
	private Integer mismoDia;	

	/** La cantidad operada */
	private Long cantidadOperada;

	/** Los días vigentes */
	private Integer diasVigentes;

	/** ISIN */
	private String isin;

	/** El precio vector. */
	private Double precioVector;

	/** El efectivo con el que dispone el usuario loogeado */
	private Double netoEfectivo;

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

	/** Divisa del valor nominal */
	private DivisaDTO divisa = new DivisaDTO();
	
	/** Boveda del traspasante */
	private BovedaDTO boveda = new BovedaDTO();

	/** Indica si es mensaje de venta 0 o compra 1 */
	private boolean compra = false;

	/** métodos getters & setters */

	/**
	 * @return the idTipoOperacion
	 */
	public long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion
	 *            the idTipoOperacion to set
	 */
	public void setIdTipoOperacion(long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
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
	 * 
	 * obtiene la Fecha en la que se liquidara el tlp
	 * 
	 * @return
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * 
	 * @param fechaLiquidacion
	 * 		  asigna Fecha en la que se liquidara el tlp
	 */	
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	public Integer getPlazoLiquidacionHoras() {
		return plazoLiquidacionHoras;
	}

	public void setPlazoLiquidacionHoras(Integer plazoLiquidacionHoras) {
		this.plazoLiquidacionHoras = plazoLiquidacionHoras;
	}

	public Integer getMismoDia() {
		return mismoDia;
	}

	public void setMismoDia(Integer mismoDia) {
		this.mismoDia = mismoDia;
	}
	
	/**
	 * @return the compra
	 */
	public boolean isCompra() {
		return compra;
	}

	/**
	 * @param compra the compra to set
	 */
	public void setCompra(boolean compra) {
		this.compra = compra;
	}
}