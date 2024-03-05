/**
 * 2H Software - Bursatec
 * <p>
 * Sistema de Consulta de Estados de Cuenta
 * <p>
 * Dec 18, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Objeto de transferencia de datos para encapsular la información referente al
 * estado de cuenta de saldo de efectivo.
 *
 * @author Pablo Julián Balderas Méndez
 *
 */
public class RegistroContableSaldoNombradaDTO implements Serializable {

    /** Default serial version id */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del registro contable que se registró en una operación de
     * un saldo
     */
    private long idRegistroContable;

    /** Indica si el registro proviene del histórico */
    private boolean historico;

    private long cargoA = 0;

    private long folioInstruccion = 0;

    /** Fecha de registro de la operación */
    private Date fechaLiquidacion;

    /** Tipo de acción: Cargo o Abono */
    private TipoAccionDTO tipoAccion;

    /** Tipo de operación */
    private TipoOperacionDTO tipoOperacion;

    /** Descripción del registro contable */
    private String descripcion = null;
    /**
     * Descripción larga del movimiento
     */
    private String descripcionLarga = null;
    /** Nombre y cuenta de la contraparte de la operación */
    private String contraparte;

    /** El número de cuenta de la contraparte */
    private String cuentaContraparte;

    /** La institución contraparte */
    private InstitucionDTO institucionContraparte;

    /** La operación nombrada relacionada con este registro contable */
    private OperacionPosicionDTO operacion;

    /** Cantidad de posiciones recibidas en la operación */
    private BigDecimal cantidadRecepcion;

    /** Cantidad de posiciones entregadas en la operación */
    private BigDecimal cantidadEntrega;

    /** Cantidad que se registró en la operación */
    private BigDecimal cantidad;

    /** Valor del saldo disponible de la operación */
    private BigDecimal saldoDisponible;

    /** Valor del saldo no disponible de la operación */
    private BigDecimal saldoNoDisponible;

    /** Valor de la suma del saldo disponible y no disponible de la operación */
    private BigDecimal saldoTotal;

    /** Saldo de efectivo correspondiente a la operación */
    private SaldoEfectivoDTO saldoEfectivo;

    /** Fecha inicial para la búsqueda de operaciones */
    private Date fechaInicial;

    /** Fecha final para la búsqueda de operaciones */
    private Date fechaFinal;
    /**
     * Identificador del tipo de instrucción asociado a la operación del
     * registro contable
     */
    private long idTipoTipoInstruccion;
    /**
     * Ruta de la pantalla que se utilzar para mostrar el detalle del
     * movimiento, null en caso de que no tenga pantalla de detalle.
     */
    private String rutaPantallaDetalle = null;
    /**
     * Ancho de la pantalla de detalle
     */
    private String anchoPantallaDetalle = null;
    /**
     * Alto de la pantalla de detalle
     */
    private String altoPantallaDetalle = null;

    /** La clave del tipo de institución del participante */
    private String claveTipoInstitucionParticipante;

    /** El folio de la institución del participante */
    private String folioInstitucionParticipante;

    /** El número de cuenta del participante */
    private String numeroCuentaParticipante;

    /** La clave del tipo de institución del participante */
    private String claveTipoInstitucionContraparte;

    /** El folio de la institución del participante */
    private String folioInstitucionContraparte;

    /** El número de cuenta del participante */
    private String numeroCuentaContraparte;

    /** Folio de intruccion de liquidacion */
    private BigInteger idFolioLiquidacion;

    /**
     * Obtiene el atributo idRegistroContable
     *
     * @return El atrubuto idRegistroContable
     */
    public long getIdRegistroContable() {
        return idRegistroContable;
    }

    /**
     * Obtiene el valor del atributo claveTipoInstitucionContraparte
     *
     * @return el valor del atributo claveTipoInstitucionContraparte
     */
    public String getClaveTipoInstitucionContraparte() {
        return claveTipoInstitucionContraparte;
    }

    /**
     * Establece el valor del atributo claveTipoInstitucionContraparte
     *
     * @param claveTipoInstitucionContraparte
     *            el valor del atributo claveTipoInstitucionContraparte a
     *            establecer
     */
    public void setClaveTipoInstitucionContraparte(String claveTipoInstitucionContraparte) {
        this.claveTipoInstitucionContraparte = claveTipoInstitucionContraparte;
    }

    /**
     * Obtiene el valor del atributo folioInstitucionContraparte
     *
     * @return el valor del atributo folioInstitucionContraparte
     */
    public String getFolioInstitucionContraparte() {
        return folioInstitucionContraparte;
    }

    /**
     * Establece el valor del atributo folioInstitucionContraparte
     *
     * @param folioInstitucionContraparte
     *            el valor del atributo folioInstitucionContraparte a establecer
     */
    public void setFolioInstitucionContraparte(String folioInstitucionContraparte) {
        this.folioInstitucionContraparte = folioInstitucionContraparte;
    }

    /**
     * Obtiene el valor del atributo claveTipoInstitucionParticipante
     *
     * @return el valor del atributo claveTipoInstitucionParticipante
     */
    public String getClaveTipoInstitucionParticipante() {
        return claveTipoInstitucionParticipante;
    }

    /**
     * Establece el valor del atributo claveTipoInstitucionParticipante
     *
     * @param claveTipoInstitucionParticipante
     *            el valor del atributo claveTipoInstitucionParticipante a
     *            establecer
     */
    public void setClaveTipoInstitucionParticipante(String claveTipoInstitucionParticipante) {
        this.claveTipoInstitucionParticipante = claveTipoInstitucionParticipante;
    }

    /**
     * Obtiene el valor del atributo folioInstitucionParticipante
     *
     * @return el valor del atributo folioInstitucionParticipante
     */
    public String getFolioInstitucionParticipante() {
        return folioInstitucionParticipante;
    }

    /**
     * Establece el valor del atributo folioInstitucionParticipante
     *
     * @param folioInstitucionParticipante
     *            el valor del atributo folioInstitucionParticipante a
     *            establecer
     */
    public void setFolioInstitucionParticipante(String folioInstitucionParticipante) {
        this.folioInstitucionParticipante = folioInstitucionParticipante;
    }

    /**
     * Obtiene el valor del atributo numeroCuentaParticipante
     *
     * @return el valor del atributo numeroCuentaParticipante
     */
    public String getNumeroCuentaParticipante() {
        return numeroCuentaParticipante;
    }

    /**
     * Establece el valor del atributo numeroCuentaParticipante
     *
     * @param numeroCuentaParticipante
     *            el valor del atributo numeroCuentaParticipante a establecer
     */
    public void setNumeroCuentaParticipante(String numeroCuentaParticipante) {
        this.numeroCuentaParticipante = numeroCuentaParticipante;
    }

    /**
     * Obtiene el valor del atributo numeroCuentaContraparte
     *
     * @return el valor del atributo numeroCuentaContraparte
     */
    public String getNumeroCuentaContraparte() {
        return numeroCuentaContraparte;
    }

    /**
     * Establece el valor del atributo numeroCuentaContraparte
     *
     * @param numeroCuentaContraparte
     *            el valor del atributo numeroCuentaContraparte a establecer
     */
    public void setNumeroCuentaContraparte(String numeroCuentaContraparte) {
        this.numeroCuentaContraparte = numeroCuentaContraparte;
    }

    /**
     * Obtiene el valor del atributo operacion
     *
     * @return el valor del atributo operacion
     */
    public OperacionPosicionDTO getOperacion() {
        return operacion;
    }

    /**
     * Establece el valor del atributo operacion
     *
     * @param operacion
     *            el valor del atributo operacion a establecer.
     */
    public void setOperacion(OperacionPosicionDTO operacion) {
        this.operacion = operacion;
    }

    /**
     * Establece la propiedad idRegistroContable
     *
     * @param idRegistroContable
     *            el campo idRegistroContable a establecer
     */
    public void setIdRegistroContable(long idRegistroContable) {
        this.idRegistroContable = idRegistroContable;
    }

    /**
     * Obtiene el atributo fecha
     *
     * @return El atrubuto fecha
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * Establece la propiedad fecha
     *
     * @param fecha
     *            el campo fecha a establecer
     */
    public void setFechaLiquidacion(Date fecha) {
        this.fechaLiquidacion = fecha;
    }

    /**
     * Obtiene el atributo tipoAccion
     *
     * @return El atrubuto tipoAccion
     */
    public TipoAccionDTO getTipoAccion() {
        return tipoAccion;
    }

    /**
     * Establece la propiedad tipoAccion
     *
     * @param tipoAccion
     *            el campo tipoAccion a establecer
     */
    public void setTipoAccion(TipoAccionDTO tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    /**
     * Obtiene el atributo tipoOperacion
     *
     * @return El atrubuto tipoOperacion
     */
    public TipoOperacionDTO getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Establece la propiedad tipoOperacion
     *
     * @param tipoOperacion
     *            el campo tipoOperacion a establecer
     */
    public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * Obtiene el atributo contraparte
     *
     * @return El atrubuto contraparte
     */
    public String getContraparte() {
        return contraparte;
    }

    /**
     * Establece la propiedad contraparte
     *
     * @param contraparte
     *            el campo contraparte a establecer
     */
    public void setContraparte(String contraparte) {
        this.contraparte = contraparte;
    }

    /**
     * Obtiene el valor del atributo institucionContraparte
     *
     * @return el valor del atributo institucionContraparte
     */
    public InstitucionDTO getInstitucionContraparte() {
        return institucionContraparte;
    }

    /**
     * Establece el valor del atributo institucionContraparte
     *
     * @param institucionContraparte
     *            el valor del atributo institucionContraparte a establecer
     */
    public void setInstitucionContraparte(InstitucionDTO institucionContraparte) {
        this.institucionContraparte = institucionContraparte;
    }

    /**
     * Obtiene el atributo saldoEfectivo
     *
     * @return El atrubuto saldoEfectivo
     */
    public SaldoEfectivoDTO getSaldoEfectivo() {
        return saldoEfectivo;
    }

    /**
     * Establece la propiedad saldoEfectivo
     *
     * @param saldoEfectivo
     *            el campo saldoEfectivo a establecer
     */
    public void setSaldoEfectivo(SaldoEfectivoDTO saldoEfectivo) {
        this.saldoEfectivo = saldoEfectivo;
    }

    /**
     * Obtiene el atributo fechaInicial
     *
     * @return El atrubuto fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Establece la propiedad fechaInicial
     *
     * @param fechaInicial
     *            el campo fechaInicial a establecer
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * Obtiene el atributo fechaFinal
     *
     * @return El atrubuto fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Establece la propiedad fechaFinal
     *
     * @param fechaFinal
     *            el campo fechaFinal a establecer
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Obtiene el valor del atributo descripcion
     *
     * @return el valor del atributo descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el valor del atributo descripcion
     *
     * @param descripcion
     *            el valor del atributo descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el campo folioInstruccion
     *
     * @return folioInstruccion
     */
    public long getFolioInstruccion() {
        return folioInstruccion;
    }

    /**
     * Asigna el valor del campo folioInstruccion
     *
     * @param folioInstruccion
     *            el valor de folioInstruccion a asignar
     */
    public void setFolioInstruccion(long folioInstruccion) {
        this.folioInstruccion = folioInstruccion;
    }

    /**
     * Obtiene el campo cuentaContraparte
     *
     * @return cuentaContraparte
     */
    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    /**
     * Asigna el valor del campo cuentaContraparte
     *
     * @param cuentaContraparte
     *            el valor de cuentaContraparte a asignar
     */
    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    /**
     * Obtiene el campo idTipoTipoInstruccion
     *
     * @return idTipoTipoInstruccion
     */
    public long getIdTipoTipoInstruccion() {
        return idTipoTipoInstruccion;
    }

    /**
     * Asigna el valor del campo idTipoTipoInstruccion
     *
     * @param idTipoTipoInstruccion
     *            el valor de idTipoTipoInstruccion a asignar
     */
    public void setIdTipoTipoInstruccion(long idTipoTipoInstruccion) {
        this.idTipoTipoInstruccion = idTipoTipoInstruccion;
    }

    /**
     * Obtiene el campo rutaPantallaDetalle
     *
     * @return rutaPantallaDetalle
     */
    public String getRutaPantallaDetalle() {
        return rutaPantallaDetalle;
    }

    /**
     * Asigna el valor del campo rutaPantallaDetalle
     *
     * @param rutaPantallaDetalle
     *            el valor de rutaPantallaDetalle a asignar
     */
    public void setRutaPantallaDetalle(String rutaPantallaDetalle) {
        this.rutaPantallaDetalle = rutaPantallaDetalle;
    }

    /**
     * Obtiene el campo anchoPantallaDetalle
     *
     * @return anchoPantallaDetalle
     */
    public String getAnchoPantallaDetalle() {
        return anchoPantallaDetalle;
    }

    /**
     * Asigna el valor del campo anchoPantallaDetalle
     *
     * @param anchoPantallaDetalle
     *            el valor de anchoPantallaDetalle a asignar
     */
    public void setAnchoPantallaDetalle(String anchoPantallaDetalle) {
        this.anchoPantallaDetalle = anchoPantallaDetalle;
    }

    /**
     * Obtiene el campo altoPantallaDetalle
     *
     * @return altoPantallaDetalle
     */
    public String getAltoPantallaDetalle() {
        return altoPantallaDetalle;
    }

    /**
     * Asigna el valor del campo altoPantallaDetalle
     *
     * @param altoPantallaDetalle
     *            el valor de altoPantallaDetalle a asignar
     */
    public void setAltoPantallaDetalle(String altoPantallaDetalle) {
        this.altoPantallaDetalle = altoPantallaDetalle;
    }

    /**
     * Obtiene el campo descripcionLarga
     *
     * @return descripcionLarga
     */
    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    /**
     * Asigna el valor del campo descripcionLarga
     *
     * @param descripcionLarga
     *            el valor de descripcionLarga a asignar
     */
    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    /**
     * @return the cantidadRecepcion
     */
    public BigDecimal getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    /**
     * @param cantidadRecepcion the cantidadRecepcion to set
     */
    public void setCantidadRecepcion(BigDecimal cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
    }

    /**
     * @return the cantidadEntrega
     */
    public BigDecimal getCantidadEntrega() {
        return cantidadEntrega;
    }

    /**
     * @param cantidadEntrega the cantidadEntrega to set
     */
    public void setCantidadEntrega(BigDecimal cantidadEntrega) {
        this.cantidadEntrega = cantidadEntrega;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the saldoDisponible
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible the saldoDisponible to set
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * @return the saldoNoDisponible
     */
    public BigDecimal getSaldoNoDisponible() {
        return saldoNoDisponible;
    }

    /**
     * @param saldoNoDisponible the saldoNoDisponible to set
     */
    public void setSaldoNoDisponible(BigDecimal saldoNoDisponible) {
        this.saldoNoDisponible = saldoNoDisponible;
    }

    /**
     * @return the saldoTotal
     */
    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    /**
     * @param saldoTotal the saldoTotal to set
     */
    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    /**
     * @return the cargoA
     */
    public long getCargoA() {
        return cargoA;
    }

    /**
     * @param cargoA the cargoA to set
     */
    public void setCargoA(long cargoA) {
        this.cargoA = cargoA;
    }

    /**
     * @return the historico
     */
    public boolean isHistorico() {
        return historico;
    }

    /**
     * @param historico the historico to set
     */
    public void setHistorico(boolean historico) {
        this.historico = historico;
    }

    /**
     * Obtiene el campo idFolioLiquidacion
     *
     * @return idFolioLiquidacion
     */
    public BigInteger getIdFolioLiquidacion() {
        return idFolioLiquidacion;
    }

    /**
     * Asigna el valor del campo idFolioLiquidacion
     *
     * @param idFolioLiquidacion
     *            el valor de idFolioLiquidacion a asignar
     */
    public void setIdFolioLiquidacion(BigInteger idFolioLiquidacion) {
        this.idFolioLiquidacion = idFolioLiquidacion;
    }
}
