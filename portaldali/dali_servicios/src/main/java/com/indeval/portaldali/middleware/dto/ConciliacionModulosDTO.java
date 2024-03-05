package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConciliacionModulosDTO implements Serializable {

	private static final long serialVersionUID = -1701254353888970010L;

	private boolean corregir;
	private Long idBitacoraAdaptadorEntrada;
	private Long folioInstruccionLiquidacion;
	private Long idInstruccionEfectivo;
	private Long idModuloOrigen;
	private String folioParticipante;
	private String nombreParticipante;
	private String cuentaParticipante;
	private String folioContraparte;
	private String nombreContraparte;
	private String cuentaContraparte;
	private String folioBancoTrabajo;
	private String nombreBancoTrabajo;
	private String cuentaBancoTrabajo;
	private String tipoInstruccion;
	private String tipoOperacion;
	private String tipoDerecho;
	private String estadoOperacion;
	private String estadoOperacionLiq;
	private String tipoMensaje;
	private String tipoMovimiento;
	private Date fechaRegistro;
	private Date fechaLiquidacion;
	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private Long cantidadTitulos;
	private BigDecimal precioTitulo;
	private BigDecimal importe;
	private String divisa;	
	private String folioControl;
	private String folioInstruccion;
	private String folioPreliquidador;
	private String claveRastreo;
	private String referenciaOperacion;
	private String folioUsuario;
	private String participanteSinNotif;

	public boolean isCorregir() {
		return corregir;
	}

	public Long getIdBitacoraAdaptadorEntrada() {
		return idBitacoraAdaptadorEntrada;
	}

	public void setIdBitacoraAdaptadorEntrada(Long idBitacoraAdaptadorEntrada) {
		this.idBitacoraAdaptadorEntrada = idBitacoraAdaptadorEntrada;
	}

	public Long getFolioInstruccionLiquidacion() {
		return folioInstruccionLiquidacion;
	}

	public void setFolioInstruccionLiquidacion(Long folioInstruccionLiquidacion) {
		this.folioInstruccionLiquidacion = folioInstruccionLiquidacion;
	}

	public Long getIdInstruccionEfectivo() {
		return idInstruccionEfectivo;
	}

	public void setIdInstruccionEfectivo(Long idInstruccionEfectivo) {
		this.idInstruccionEfectivo = idInstruccionEfectivo;
	}

	public Long getIdModuloOrigen() {
		return idModuloOrigen;
	}

	public void setIdModuloOrigen(Long idModuloOrigen) {
		this.idModuloOrigen = idModuloOrigen;
	}

	public void setCorregir(boolean corregir) {
		this.corregir = corregir;
	}

	public String getFolioParticipante() {
		return folioParticipante;
	}

	public void setFolioParticipante(String folioParticipante) {
		this.folioParticipante = folioParticipante;
	}

	public String getNombreParticipante() {
		return nombreParticipante;
	}

	public void setNombreParticipante(String nombreParticipante) {
		this.nombreParticipante = nombreParticipante;
	}

	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	public String getFolioContraparte() {
		return folioContraparte;
	}

	public void setFolioContraparte(String folioContraparte) {
		this.folioContraparte = folioContraparte;
	}

	public String getNombreContraparte() {
		return nombreContraparte;
	}

	public void setNombreContraparte(String nombreContraparte) {
		this.nombreContraparte = nombreContraparte;
	}

	public String getCuentaContraparte() {
		return cuentaContraparte;
	}

	public void setCuentaContraparte(String cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	public String getFolioBancoTrabajo() {
		return folioBancoTrabajo;
	}

	public void setFolioBancoTrabajo(String folioBancoTrabajo) {
		this.folioBancoTrabajo = folioBancoTrabajo;
	}

	public String getNombreBancoTrabajo() {
		return nombreBancoTrabajo;
	}

	public void setNombreBancoTrabajo(String nombreBancoTrabajo) {
		this.nombreBancoTrabajo = nombreBancoTrabajo;
	}

	public String getCuentaBancoTrabajo() {
		return cuentaBancoTrabajo;
	}

	public void setCuentaBancoTrabajo(String cuentaBancoTrabajo) {
		this.cuentaBancoTrabajo = cuentaBancoTrabajo;
	}

	public String getTipoInstruccion() {
		return tipoInstruccion;
	}

	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getTipoDerecho() {
		return tipoDerecho;
	}

	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}

	public String getEstadoOperacion() {
		return estadoOperacion;
	}

	public void setEstadoOperacion(String estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	public String getEstadoOperacionLiq() {
		return estadoOperacionLiq;
	}

	public void setEstadoOperacionLiq(String estadoOperacionLiq) {
		this.estadoOperacionLiq = estadoOperacionLiq;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public String getEmisora() {
		return emisora;
	}

	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCupon() {
		return cupon;
	}

	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	public String getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	public Long getCantidadTitulos() {
		return cantidadTitulos;
	}

	public void setCantidadTitulos(Long cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getFolioInstruccion() {
		return folioInstruccion;
	}

	public void setFolioInstruccion(String folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public String getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public String getFolioUsuario() {
		return folioUsuario;
	}

	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFolioPreliquidador() {
		return folioPreliquidador;
	}

	public void setFolioPreliquidador(String folioPreliquidador) {
		this.folioPreliquidador = folioPreliquidador;
	}

	public String getParticipanteSinNotif() {
		return participanteSinNotif;
	}

	public void setParticipanteSinNotif(String participanteSinNotif) {
		this.participanteSinNotif = participanteSinNotif;
	}

}
