/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * Apr 30, 2008
 */
package com.indeval.portaldali.presentation.dto.traspasosadministrativos;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * DTO que representa a los elementos de la pantalla de Traspasos Administrativos.
 * 
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class TraspasosAdministrativosDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Posicion asociada al Traspasante y su Emision */
    private PosicionDTO posicionTraspasante = new PosicionDTO();

    /** El DTO que representara a la cuenta de la contraparte. */
    private CuentaDTO cuentaReceptor = new CuentaDTO();

    /** El identificador y el folio del traspasante */
    private String idFolioTraspasante;

    /** El identificador y el folio del receptor */
    private String idFolioReceptor;

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

    /** Integer asociado al valor del combo de Plazo Liquidacion (hrs) */
    private Integer plazoLiquidacionHoras;

    /** Date asociada a la fecha de Concertacion */
    private Date fechaConcertacion;
    
    /** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;

    /** Cadena asociada a la Cantidad Operada */
    private Long cantidadOperada;

    /** Integer asociado al valor del combo de valorEn */
    private DivisaDTO divisa;

    /** Cadena asociada al precio de Titulo */
    private Double precioTitulo;

    /** Cadena asociada al importe */
    private Double importe;

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
     * Obtiene el valor del campo divisa
     * 
     * @return el valor de divisa
     */
    public DivisaDTO getDivisa() {
        return divisa;
    }

    /**
     * Asigna el campo divisa
     * 
     * @param divisa el valor de divisa a asignar
     */
    public void setDivisa(DivisaDTO divisa) {
        this.divisa = divisa;
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

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

}
