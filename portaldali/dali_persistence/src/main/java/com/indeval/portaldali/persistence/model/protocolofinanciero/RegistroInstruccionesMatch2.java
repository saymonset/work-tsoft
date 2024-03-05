/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model.protocolofinanciero;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * @author Sergio Mena
 *
 */

 @Entity
 @Table(name="T_REGISTRO_INSTRUCCIONES_MATCH")
 @NamedQueries({
	@NamedQuery(name="RegistroInstruccionesMatch2.getRegistroInstruccion", query="select m FROM RegistroInstruccionesMatch2 m WHERE m.idBitacoraMatch = ?"),
	@NamedQuery(name="RegistroInstruccionesMatch2.updateRegistroInstruccion", query="UPDATE RegistroInstruccionesMatch2 set estadoInstruccion = ? " +
			         "WHERE ((idFolioReceptor = ? and tipoMensaje = ?) or (idFolioTraspasante = ? and  tipoMensaje = ? )) and idBitacoraMatch = ? " +
			         "and estadoInstruccion != ? and (trim(expira) != ?  or expira is null) ")
 })
public class RegistroInstruccionesMatch2 {
    private Long idBitacoraMatch; // key?
    private String folioInstruccion;
    private String folioMatch;
    private String estadoInstruccion;
    private String tipoOperacion;
    private String tipoMensaje;
    private Date fechaHoraRecepcion;
    private Date fechaLiquidacion;
    private String mensaje;
    private String estadoProceso;
    private BigDecimal numeroProceso;
    private Integer optlock;
    private String expira;
    private String cuentaTraspasante;
    private String idFolioReceptor;
    private String cuentaReceptor;
    private String idFolioTraspasante;
    private String tipoValor;
    private String emisora;
    private String serie;
    private String cupon;
    private Date fechaReporto;
    private String precioTitulo;
    private String tasaNegociada;
    private String tasaReferencia;
    private String cantidadTitulos;
    private String importe;
    private String folioControl;
    private Date fechaHoraRegistro;
    private String confirmacion;
    private Integer referenciaParticipante;

     @Id
     @Column(name ="id_bitacora_match", unique = true, nullable = false, insertable = true, updatable = true, scale = 0)
    public Long getIdBitacoraMatch() {
        return idBitacoraMatch;
    }

     @Column(name ="folio_instruccion", unique = false, nullable = true, insertable = true, updatable = true)
    public String getFolioInstruccion() {
        return folioInstruccion;
    }

     @Column(name ="folio_match", unique = false, nullable = true, insertable = true, updatable = true)
    public String getFolioMatch() {
        return folioMatch;
    }

     @Column(name ="estado_instruccion", unique = false, nullable = true, insertable = true, updatable = true)
    public String getEstadoInstruccion() {
        return estadoInstruccion;
    }

     @Column(name ="tipo_operacion", unique = false, nullable = true, insertable = true, updatable = true)
    public String getTipoOperacion() {
        return tipoOperacion;
    }

     @Column(name ="tipo_mensaje", unique = false, nullable = true, insertable = true, updatable = true)
    public String getTipoMensaje() {
        return tipoMensaje;
    }

     @Column(name ="fecha_hora_recepcion", unique = false, nullable = true, insertable = false, updatable = true)
    public Date getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

     @Column(name ="fecha_liquidacion", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }
     
     @Lob
     @Column(name ="mensaje", unique = false, nullable = true, insertable = true, updatable = true)
    public String getMensaje() {
        return mensaje;
    }

     @Column(name ="estado_proceso", unique = false, nullable = true, insertable = true, updatable = true)
    public String getEstadoProceso() {
        return estadoProceso;
    }

     @Column(name ="numero_proceso", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getNumeroProceso() {
        return numeroProceso;
    }
     
     @Version
     @Column(name ="optlock")
    public Integer getOptlock() {
        return optlock;
    }

     @Column(name ="expira", unique = false, nullable = true, insertable = true, updatable = true)
    public String getExpira() {
        return expira;
    }

     @Column(name ="cuenta_traspasante", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCuentaTraspasante() {
        return cuentaTraspasante;
    }

     @Column(name ="id_folio_receptor", unique = false, nullable = true, insertable = true, updatable = false)
    public String getIdFolioReceptor() {
        return idFolioReceptor;
    }

     @Column(name ="cuenta_receptor", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCuentaReceptor() {
        return cuentaReceptor;
    }

     @Column(name ="id_folio_traspasante", unique = false, nullable = true, insertable = true, updatable = false)
    public String getIdFolioTraspasante() {
        return idFolioTraspasante;
    }

     @Column(name ="tipo_valor", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTipoValor() {
        return tipoValor;
    }

     @Column(name ="emisora", unique = false, nullable = true, insertable = true, updatable = false)
    public String getEmisora() {
        return emisora;
    }

     @Column(name ="serie", unique = false, nullable = true, insertable = true, updatable = false)
    public String getSerie() {
        return serie;
    }

     @Column(name ="cupon", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCupon() {
        return cupon;
    }

     @Column(name ="fecha_reporto", unique = false, nullable = true, insertable = true, updatable = false)
    public Date getFechaReporto() {
        return fechaReporto;
    }

     @Column(name ="precio_titulo", unique = false, nullable = true, insertable = true, updatable = false)
    public String getPrecioTitulo() {
        return precioTitulo;
    }

     @Column(name ="tasa_negociada", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTasaNegociada() {
        return tasaNegociada;
    }

     @Column(name ="tasa_referencia", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTasaReferencia() {
        return tasaReferencia;
    }

     @Column(name ="cantidad_titulos", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCantidadTitulos() {
        return cantidadTitulos;
    }

     @Column(name ="importe", unique = false, nullable = true, insertable = true, updatable = false)
    public String getImporte() {
        return importe;
    }

     @Column(name ="folio_control", unique = false, nullable = true, insertable = true, updatable = false)
    public String getFolioControl() {
        return folioControl;
    }

     @Column(name ="fecha_hora_registro", unique = false, nullable = true, insertable = true, updatable = false)
    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }
     
     @Column(name ="confirmacion", unique = false, nullable = true, insertable = true, updatable = true)
     public String getConfirmacion() {
         return confirmacion;
     }

     @Column(name ="referencia_participante", unique = false, nullable = false, insertable = false, updatable = false)
	public Integer getReferenciaParticipante() {
		return referenciaParticipante;
	}

	public void setIdBitacoraMatch(Long idBitacoraMatch) {
        this.idBitacoraMatch = idBitacoraMatch;
    }

    public void setFolioInstruccion(String folioInstruccion) {
        this.folioInstruccion = folioInstruccion;
    }

    public void setFolioMatch(String folioMatch) {
        this.folioMatch = folioMatch;
    }

    public void setEstadoInstruccion(String estadoInstruccion) {
        this.estadoInstruccion = estadoInstruccion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public void setNumeroProceso(BigDecimal numeroProceso) {
        this.numeroProceso = numeroProceso;
    }

    public void setOptlock(Integer optlock) {
        this.optlock = optlock;
    }

    public void setExpira(String expira) {
        this.expira = expira;
    }

    public void setCuentaTraspasante(String cuentaTraspasante) {
        this.cuentaTraspasante = cuentaTraspasante;
    }

    public void setIdFolioReceptor(String idFolioReceptor) {
        this.idFolioReceptor = idFolioReceptor;
    }

    public void setCuentaReceptor(String cuentaReceptor) {
        this.cuentaReceptor = cuentaReceptor;
    }

    public void setIdFolioTraspasante(String idFolioTraspasante) {
        this.idFolioTraspasante = idFolioTraspasante;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public void setFechaReporto(Date fechaReporto) {
        this.fechaReporto = fechaReporto;
    }

    public void setPrecioTitulo(String precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    public void setTasaNegociada(String tasaNegociada) {
        this.tasaNegociada = tasaNegociada;
    }

    public void setTasaReferencia(String tasaReferencia) {
        this.tasaReferencia = tasaReferencia;
    }

    public void setCantidadTitulos(String cantidadTitulos) {
        this.cantidadTitulos = cantidadTitulos;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    } 

    public void setFolioControl(String folioControl) {
        this.folioControl = folioControl;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
    
    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

	/**
	 * @param referenciaParticipante the referenciaParticipante to set
	 */
	public void setReferenciaParticipante(Integer referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}
}
