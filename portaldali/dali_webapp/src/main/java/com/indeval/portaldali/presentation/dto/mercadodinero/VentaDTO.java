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
 * DTO que representa a los elementos de la pantalla de captura de operaciones para el tipo de operacion Venta.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class VentaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Integer relacionado al indice del radio mismoDia o fecha Valor */
    private Integer mismoDia;

    /** booleano relacionado con la compra en Directo */
    private Boolean compraDirecto;

    /** Posicion asociada al Traspasante y su Emision */
    private PosicionDTO posicionTraspasante = new PosicionDTO();

    /** El DTO que representara a la cuenta de la contraparte. */
    private CuentaDTO cuentaReceptor = new CuentaDTO();

    /** El número de días vigentes */
    private Integer diasVigentes;

    /** El precio Vector */
    private Double precioVector;

    /** El saldo disponible */
    private Long saldoDisponible = null;

    /** El saldo que resultara al realizar la operación */
    private Long simulado = null;

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
    
    /** Boveda de efectivo */
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
     * Obtiene el valor del campo mismoDia
     * 
     * @return el valor de mismoDia
     */
    public Integer getMismoDia() {
        return mismoDia;
    }

    /**
     * Asigna el campo mismoDia
     * 
     * @param mismoDia el valor de mismoDia a asignar
     */
    public void setMismoDia(Integer mismoDia) {
        this.mismoDia = mismoDia;
    }

    /**
     * Obtiene el valor del campo compraDirecto
     * 
     * @return el valor de compraDirecto
     */
    public Boolean getCompraDirecto() {
        return compraDirecto;
    }

    /**
     * Asigna el campo compraDirecto
     * 
     * @param compraDirecto el valor de compraDirecto a asignar
     */
    public void setCompraDirecto(Boolean compraDirecto) {
        this.compraDirecto = compraDirecto;
    }

    /**
     * Obtiene el valor del campo posicionTraspasante
     * 
     * @return el valor de posicionTraspasante
     */
    public PosicionDTO getPosicionTraspasante() {
        return posicionTraspasante;
    }

    /**
     * Asigna el campo posicionTraspasante
     * 
     * @param posicionTraspasante el valor de posicionTraspasante a asignar
     */
    public void setPosicionTraspasante(PosicionDTO posicionTraspasante) {
        this.posicionTraspasante = posicionTraspasante;
    }

    /**
     * Obtiene el valor del campo cuentaReceptor
     * 
     * @return el valor de cuentaReceptor
     */
    public CuentaDTO getCuentaReceptor() {
        return cuentaReceptor;
    }

    /**
     * Asigna el campo cuentaReceptor
     * 
     * @param cuentaReceptor el valor de cuentaReceptor a asignar
     */
    public void setCuentaReceptor(CuentaDTO cuentaReceptor) {
        this.cuentaReceptor = cuentaReceptor;
    }

    /**
     * Obtiene el valor del campo diasVigentes
     * 
     * @return el valor de diasVigentes
     */
    public Integer getDiasVigentes() {
        return diasVigentes;
    }

    /**
     * Asigna el campo diasVigentes
     * 
     * @param diasVigentes el valor de diasVigentes a asignar
     */
    public void setDiasVigentes(Integer diasVigentes) {
        this.diasVigentes = diasVigentes;
    }

    /**
     * Obtiene el valor del campo precioVector
     * 
     * @return el valor de precioVector
     */
    public Double getPrecioVector() {
        return precioVector;
    }

    /**
     * Asigna el campo precioVector
     * 
     * @param precioVector el valor de precioVector a asignar
     */
    public void setPrecioVector(Double precioVector) {
        this.precioVector = precioVector;
    }

    /**
     * Obtiene el valor del campo saldoDisponible
     * 
     * @return el valor de saldoDisponible
     */
    public Long getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * Asigna el campo saldoDisponible
     * 
     * @param saldoDisponible el valor de saldoDisponible a asignar
     */
    public void setSaldoDisponible(Long saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * Obtiene el valor del campo simulado
     * 
     * @return el valor de simulado
     */
    public Long getSimulado() {
        return simulado;
    }

    /**
     * Asigna el campo simulado
     * 
     * @param simulado el valor de simulado a asignar
     */
    public void setSimulado(Long simulado) {
        this.simulado = simulado;
    }

    /**
     * Obtiene el valor del campo netoEfectivo
     * 
     * @return el valor de netoEfectivo
     */
    public Double getNetoEfectivo() {
        return netoEfectivo;
    }

    /**
     * Asigna el campo netoEfectivo
     * 
     * @param netoEfectivo el valor de netoEfectivo a asignar
     */
    public void setNetoEfectivo(Double netoEfectivo) {
        this.netoEfectivo = netoEfectivo;
    }

    /**
     * Obtiene el valor del campo fechaConcertacion
     * 
     * @return el valor de fechaConcertacion
     */
    public Date getFechaConcertacion() {
        return fechaConcertacion;
    }

    /**
     * Asigna el campo fechaConcertacion
     * 
     * @param fechaConcertacion el valor de fechaConcertacion a asignar
     */
    public void setFechaConcertacion(Date fechaConcertacion) {
        this.fechaConcertacion = fechaConcertacion;
    }

    /**
     * Obtiene el valor del campo cantidadOperada
     * 
     * @return el valor de cantidadOperada
     */
    public Long getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * Asigna el campo cantidadOperada
     * 
     * @param cantidadOperada el valor de cantidadOperada a asignar
     */
    public void setCantidadOperada(Long cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * Obtiene el valor del campo valorEn
     * 
     * @return el valor de valorEn
     */
    public DivisaDTO getValorEn() {
        return valorEn;
    }

    /**
     * Asigna el campo valorEn
     * 
     * @param valorEn el valor de valorEn a asignar
     */
    public void setValorEn(DivisaDTO valorEn) {
        this.valorEn = valorEn;
    }
     

    /**
     * Obtiene el valor del campo bovedaEfectivo
     * 
     * @return el valor de bovedaEfectivo
     */
	public BovedaDTO getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
     * Asigna el campo bovedaEfectivo
     * 
     * @param bovedaEfectivo el valor de bovedaEfectivo a asignar
     */
	public void setBovedaEfectivo(BovedaDTO bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
     * Obtiene el valor del campo precioTitulo
     * 
     * @return el valor de precioTitulo
     */
    public Double getPrecioTitulo() {
        return precioTitulo;
    }

    /**
     * Asigna el campo precioTitulo
     * 
     * @param precioTitulo el valor de precioTitulo a asignar
     */
    public void setPrecioTitulo(Double precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    /**
     * Obtiene el valor del campo importe
     * 
     * @return el valor de importe
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Asigna el campo importe
     * 
     * @param importe el valor de importe a asignar
     */
    public void setImporte(Double importe) {
        this.importe = importe;
    }

    /**
     * Obtiene el valor del campo idFolioTraspasante
     * 
     * @return el valor de idFolioTraspasante
     */
    public String getIdFolioTraspasante() {
        return idFolioTraspasante;
    }

    /**
     * Asigna el campo idFolioTraspasante
     * 
     * @param idFolioTraspasante el valor de idFolioTraspasante a asignar
     */
    public void setIdFolioTraspasante(String idFolioTraspasante) {
        this.idFolioTraspasante = idFolioTraspasante;
    }

    /**
     * Obtiene el valor del campo idFolioReceptor
     * 
     * @return el valor de idFolioReceptor
     */
    public String getIdFolioReceptor() {
        return idFolioReceptor;
    }

    /**
     * Asigna el campo idFolioReceptor
     * 
     * @param idFolioReceptor el valor de idFolioReceptor a asignar
     */
    public void setIdFolioReceptor(String idFolioReceptor) {
        this.idFolioReceptor = idFolioReceptor;
    }

    /**
     * Obtiene el valor del campo plazoLiquidacionHoras
     * 
     * @return el valor de plazoLiquidacionHoras
     */
    public Integer getPlazoLiquidacionHoras() {
        return plazoLiquidacionHoras;
    }

    /**
     * Asigna el campo plazoLiquidacionHoras
     * 
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

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

}