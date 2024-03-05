/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TraspasoMiscelaneaFiscalDTO.java
 * Apr 24, 2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * DTO que representa una operación de traspaso de miscelanea fiscal.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TraspasoMiscelaneaFiscalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** El DTO que representara a la cuenta de la contraparte. */
    private CuentaDTO cuentaReceptor = new CuentaDTO();

    /**
     * EL DTO que representa a la posicion, como son la cuenta, emision y boveda del participante.
     */
    private PosicionDTO posicionTraspasante = new PosicionDTO();

    /** El identificador del tipo de Operacion */
    private long idTipoOperacion = 0;

    /** El saldo disponible de la posición seleccionada */
    private Double saldoDisponible = null;

    /** El saldo actual de la posición seleccionada */
    private Double saldoActual = null;

    /** Cantidad */
    private Long cantidadOperada = null;

    /** Fecha de adquisición */
    private Date fechaAdquisicion = null;
    
    /** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;

    /** Precio de adquisición */
    private Double precioAdquisicion;

    /** El cliente */
    private String cliente;

    /** El RFC  CURP */
    private String rfcCurp;

    /** Extranjero */
    private Boolean extranjero;

    /** ISIN */
    private String isin;

    /** El precio vector */
    private Double precioVector;

    /** Indica si se trata de una recepción */
    private Boolean recepcion;

    /** El identificador y el folio del traspasante */
    private String idFolioTraspasante;

    /** El identificador y el folio del receptor */
    private String idFolioReceptor;

    /** Integer asociado al valor del combo de valorEn */
    private DivisaDTO valorEn;

    /** boolean asociado a aceptar cargo */
    private Boolean aceptaCargo;
    
    /** Costo promedio actualizado */
    private Double costoPromedio;
    
    /**
     * Obtiene el campo cuentaReceptor
     * 
     * @return cuentaReceptor
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
     * Obtiene el campo posicionTraspasante
     * 
     * @return posicionTraspasante
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
     * Obtiene el campo idTipoOperacion
     * 
     * @return idTipoOperacion
     */
    public long getIdTipoOperacion() {
        return idTipoOperacion;
    }

    /**
     * Asigna el campo idTipoOperacion
     * 
     * @param idTipoOperacion el valor de idTipoOperacion a asignar
     */
    public void setIdTipoOperacion(long idTipoOperacion) {
        this.idTipoOperacion = idTipoOperacion;
    }

    /**
     * Obtiene el campo saldoDisponible
     * 
     * @return saldoDisponible
     */
    public Double getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * Asigna el campo saldoDisponible
     * 
     * @param saldoDisponible el valor de saldoDisponible a asignar
     */
    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * Obtiene el campo saldoActual
     * 
     * @return saldoActual
     */
    public Double getSaldoActual() {
        return saldoActual;
    }

    /**
     * Asigna el campo saldoActual
     * 
     * @param saldoActual el valor de saldoActual a asignar
     */
    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    /**
     * Obtiene el campo cantidad
     * 
     * @return cantidad
     */
    public Long getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * Asigna el campo cantidad
     * 
     * @param cantidad el valor de cantidad a asignar
     */
    public void setCantidadOperada(Long cantidad) {
        this.cantidadOperada = cantidad;
    }

    /**
     * Obtiene el campo fechaAdquisicion
     * 
     * @return fechaAdquisicion
     */
    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    /**
     * Asigna el campo fechaAdquisicion
     * 
     * @param fechaAdquisicion el valor de fechaAdquisicion a asignar
     */
    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /**
     * Obtiene el campo precioAdquisicion
     * 
     * @return precioAdquisicion
     */
    public Double getPrecioAdquisicion() {
        return precioAdquisicion;
    }

    /**
     * Asigna el campo precioAdquisicion
     * 
     * @param precioAdquisicion el valor de precioAdquisicion a asignar
     */
    public void setPrecioAdquisicion(Double precioAdquisicion) {
        this.precioAdquisicion = precioAdquisicion;
    }

    /**
     * Obtiene el campo cliente
     * 
     * @return cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Asigna el campo cliente
     * 
     * @param cliente el valor de cliente a asignar
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el campo rfcCurp
     * 
     * @return rfcCurp
     */
    public String getRfcCurp() {
        return rfcCurp;
    }

    /**
     * Asigna el campo rfcCurp
     * 
     * @param rfcCurp el valor de rfcCurp a asignar
     */
    public void setRfcCurp(String rfcCurp) {
        if (rfcCurp != null && StringUtils.isNotEmpty(rfcCurp)) {
            this.rfcCurp = rfcCurp.toUpperCase();
        } else {
            this.rfcCurp = rfcCurp;
        }
    }

    /**
     * Obtiene el campo extranjero
     * 
     * @return extranjero
     */
    public Boolean getExtranjero() {
        return extranjero;
    }

    /**
     * Asigna el campo extranjero
     * 
     * @param extranjero el valor de extranjero a asignar
     */
    public void setExtranjero(Boolean extranjero) {
        this.extranjero = extranjero;
    }

    /**
     * Obtiene el campo isin
     * 
     * @return isin
     */
    public String getIsin() {
        return isin;
    }

    /**
     * Asigna el campo isin
     * 
     * @param isin el valor de isin a asignar
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * Obtiene el campo precioVector
     * 
     * @return precioVector
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
     * Obtiene el campo recepcion
     * 
     * @return recepcion
     */
    public Boolean getRecepcion() {
        return recepcion;
    }

    /**
     * Asigna el campo recepcion
     * 
     * @param recepcion el valor de recepcion a asignar
     */
    public void setRecepcion(Boolean recepcion) {
        this.recepcion = recepcion;
    }

    /**
     * Obtiene el campo idFolioTraspasante
     * 
     * @return idFolioTraspasante
     */
    public String getIdFolioTraspasante() {
        return idFolioTraspasante;
    }

	/**
	 * Asigna el campo idFolioTraspasante.
	 * <p>
	 * El idFolioTraspasante consta de la concatenaci&oacute;n de
	 * ClaveTipoInstitucion y del FolioInstitucion correspondientes a la
	 * Instituci&oacute;n Traspasante.
	 * 
	 * @param idFolioTraspasante
	 *            el valor de idFolioTraspasante a asignar
	 */
    public void setIdFolioTraspasante(String idFolioTraspasante) {
        this.idFolioTraspasante = idFolioTraspasante;
    }

    /**
     * Obtiene el campo idFolioReceptor
     * 
     * @return idFolioReceptor
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
     * Obtiene el valor del campo aceptaCargpo
     * 
     * @return el valor de aceptaCargpo
     */
    public Boolean getAceptaCargo() {
        return aceptaCargo;
    }

    /**
     * Asigna el campo aceptaCargpo
     * 
     * @param aceptaCargpo el valor de aceptaCargpo a asignar
     */
    public void setAceptaCargo(Boolean aceptaCargpo) {
        this.aceptaCargo = aceptaCargpo;
    }

	/**
	 * @return the costoPromedio
	 */
	public Double getCostoPromedio() {
		return costoPromedio;
	}

	/**
	 * @param costoPromedio the costoPromedio to set
	 */
	public void setCostoPromedio(Double costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

}
