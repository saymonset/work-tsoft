/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 17, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Objeto de transferencia de datos para encapsular la información referente al 
 * detalle de un registro contable de posición de valores nombrados.
 * 
 * @author Pablo Julián Balderas Méndez
 * @version 1.0
 *
 */
public class DetalleMovimientoValorDTO implements Serializable{

	/** Default serial version id */
	private static final long serialVersionUID = 1L;
	
	/** Identificador del registro contable que se registró en una operación de una posición */
	private long idRegistroContable;	
	
	/** Fecha de registro del registro contable */
	private Date fecha;	
	
	/** Nombre y cuenta del participante */
	private String participante;
	
	/** Nombre y cuenta del traspasante de la operación */
	private String traspasante;
	
	/** Nombre y cuenta de la contraparte */
	private String contraparte;
	
	/** Nombre y cuenta de la receptor de la operación */
	private String receptor;	
	
	/** La descripción del registro contable */
	private String descripcion;	
	
	/** Folio de la operación a la que pertenece esta operación */
	private Long folioOperacion;
	
	/** Posición correspondiente a la operación que se lleva a cabo */
	private PosicionDTO posicion;	
	
	/** Fecha de registro de la operación */
	private Date fechaAplicacion;
	
	/** Fecha de concertación de la operación */
	private Date fechaConcertacion;
	
	/** Fecha de liquidación de la operación */
	private Date fechaLiquidacion;	
	
	/** Indica si el participante es receptor o traspasante */
	private boolean participanteReceptor = false;
	
	/** Indica si el registro representa el detalle de las garantías de un préstamo */
	private boolean detalleGarantias = false;
	
	/** Indica si el registro contiene información de la contraparte */
	private boolean sinContraparte = false;
	
	/** Tipo de operación */
	private TipoOperacionDTO tipoOperacion;	
	 
	/** Cantidad de posiciones que se registró en la operación */
	private double cantidad;
	
	/** Cantidad entregada en la operación */
	private BigInteger entrega;
	
	/** La cantidad total entregada */
	private double totalEntrega;
	
	/** Cantidad pendiente de entregar en la operación */
	private double pendiente;
	
	/** Custodio */
	private String custodio;
	
	/** Precio nominal del valor */
	private double precio;
	
	/** Importe del valor. Se calcula multiplicando el precio nominal por la cantidad operada */
	private double importe;
	
	/** Importe del valor pendiente por entregar */
	private double importeTotalPendiente;
	
	/** Importe del valor pendiente por pagar */
	private double importeTotalPagado;
	
	/** La descripción de la bóveda de efectivo */
	private String descripcionBovedaEfectivo;
	
	/** La desrcripción de la divisa */
	private String descripcionDivisa;
	
	/** La lista de los detallesParcialidad de la parcialidad para los movimientos DVP */
	private List<DetalleParcialidadDTO> detallesParcialidad;
	
	/** La lista de los detalles de un préstamo para los movimientos DVD */
	private List<RegistroContablePosicionNombradaDTO> detallesGarantias;
	
	/** El DTO con los datos del préstamo para los movimientos DVD */
	private RegistroContablePosicionNombradaDTO detallePrestamo;

	/**
	 * Obtiene el valor del atributo sinContraparte
	 * 
	 * @return el valor del atributo sinContraparte
	 */
	public boolean isSinContraparte() {
		return sinContraparte;
	}

	/**
	 * Establece el valor del atributo sinContraparte
	 *
	 * @param sinContraparte el valor del atributo sinContraparte a establecer
	 */
	public void setSinContraparte(boolean sinContraparte) {
		this.sinContraparte = sinContraparte;
	}

	/**
	 * Obtiene el campo totalEntrega
	 * @return  totalEntrega
	 */
	public double getTotalEntrega() {
		return totalEntrega;
	}

	/**
	 * Obtiene el valor del atributo descripcionBovedaEfectivo
	 * 
	 * @return el valor del atributo descripcionBovedaEfectivo
	 */
	public String getDescripcionBovedaEfectivo() {
		return descripcionBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo descripcionBovedaEfectivo
	 *
	 * @param descripcionBovedaEfectivo el valor del atributo descripcionBovedaEfectivo a establecer
	 */
	public void setDescripcionBovedaEfectivo(String descripcionBovedaEfectivo) {
		this.descripcionBovedaEfectivo = descripcionBovedaEfectivo;
	}

	/**
	 * Obtiene el valor del atributo descripcionDivisa
	 * 
	 * @return el valor del atributo descripcionDivisa
	 */
	public String getDescripcionDivisa() {
		return descripcionDivisa;
	}

	/**
	 * Obtiene el valor del atributo importeTotalPendiente
	 * 
	 * @return el valor del atributo importeTotalPendiente
	 */
	public double getImporteTotalPendiente() {
		return importeTotalPendiente;
	}

	/**
	 * Establece el valor del atributo importeTotalPendiente
	 *
	 * @param importeTotalPendiente el valor del atributo importeTotalPendiente a establecer
	 */
	public void setImporteTotalPendiente(double importeTotalPendiente) {
		this.importeTotalPendiente = importeTotalPendiente;
	}

	/**
	 * Obtiene el valor del atributo importeTotalPagado
	 * 
	 * @return el valor del atributo importeTotalPagado
	 */
	public double getImporteTotalPagado() {
		return importeTotalPagado;
	}

	/**
	 * Establece el valor del atributo importeTotalPagado
	 *
	 * @param importeTotalPagado el valor del atributo importeTotalPagado a establecer
	 */
	public void setImporteTotalPagado(double importeTotalPagado) {
		this.importeTotalPagado = importeTotalPagado;
	}

	/**
	 * Establece el valor del atributo descripcionDivisa
	 *
	 * @param descripcionDivisa el valor del atributo descripcionDivisa a establecer
	 */
	public void setDescripcionDivisa(String descripcionDivisa) {
		this.descripcionDivisa = descripcionDivisa;
	}

	/**
	 * Asigna el valor del campo totalEntrega
	 * @param totalEntrega el valor de totalEntrega a asignar
	 */
	public void setTotalEntrega(double totalEntrega) {
		this.totalEntrega = totalEntrega;
	}

	/**
	 * Obtiene el valor del atributo detalleGarantias
	 * 
	 * @return el valor del atributo detalleGarantias
	 */
	public boolean isDetalleGarantias() {
		return detalleGarantias;
	}

	/**
	 * Establece el valor del atributo detalleGarantias
	 *
	 * @param detalleGarantias el valor del atributo detalleGarantias a establecer
	 */
	public void setDetalleGarantias(boolean detalleGarantias) {
		this.detalleGarantias = detalleGarantias;
	}

	/**
	 * Obtiene el valor del atributo detallesGarantias
	 * 
	 * @return el valor del atributo detallesGarantias
	 */
	public List<RegistroContablePosicionNombradaDTO> getDetallesGarantias() {
		return detallesGarantias;
	}

	/**
	 * Establece el valor del atributo detallesGarantias
	 *
	 * @param detallesGarantias el valor del atributo detallesGarantias a establecer
	 */
	public void setDetallesGarantias(List<RegistroContablePosicionNombradaDTO> detallesGarantias) {
		this.detallesGarantias = detallesGarantias;
	}

	/**
	 * Obtiene el valor del atributo detallePrestamo
	 * 
	 * @return el valor del atributo detallePrestamo
	 */
	public RegistroContablePosicionNombradaDTO getDetallePrestamo() {
		return detallePrestamo;
	}

	/**
	 * Establece el valor del atributo detallePrestamo
	 *
	 * @param detallePrestamo el valor del atributo detallePrestamo a establecer
	 */
	public void setDetallePrestamo(RegistroContablePosicionNombradaDTO detallePrestamo) {
		this.detallePrestamo = detallePrestamo;
	}

	/**
	 * Obtiene el valor del atributo detallesParcialidad
	 * 
	 * @return el valor del atributo detallesParcialidad
	 */
	public List<DetalleParcialidadDTO> getDetallesParcialidad() {
		return detallesParcialidad;
	}

	/**
	 * Establece el valor del atributo detallesParcialidad
	 *
	 * @param detallesParcialidad el valor del atributo detallesParcialidad a establecer
	 */
	public void setDetallesParcialidad(List<DetalleParcialidadDTO> detallesParcialidad) {
		this.detallesParcialidad = detallesParcialidad;
	}

	/**
	 * Obtiene el valor del atributo participanteReceptor
	 * 
	 * @return el valor del atributo participanteReceptor
	 */
	public boolean isParticipanteReceptor() {
		return participanteReceptor;
	}

	/**
	 * Establece el valor del atributo participanteReceptor
	 *
	 * @param participanteReceptor el valor del atributo participanteReceptor a establecer
	 */
	public void setParticipanteReceptor(boolean participanteReceptor) {
		this.participanteReceptor = participanteReceptor;
	}

	/**
	 * Obtiene el atributo idRegistroContable
	 *
	 * @return El atrubuto idRegistroContable
	 */
	public long getIdRegistroContable() {
		return idRegistroContable;
	}

	/**
	 * Establece la propiedad idRegistroContable
	 *
	 * @param idRegistroContable el campo idRegistroContable a establecer
	 */
	public void setIdRegistroContable(long idRegistroContable) {
		this.idRegistroContable = idRegistroContable;
	}

	/**
	 * Obtiene el atributo fecha
	 *
	 * @return El atrubuto fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la propiedad fecha
	 *
	 * @param fecha el campo fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el atributo traspasante
	 *
	 * @return El atrubuto traspasante
	 */
	public String getTraspasante() {
		return traspasante;
	}

	/**
	 * Establece la propiedad traspasante
	 *
	 * @param traspasante el campo traspasante a establecer
	 */
	public void setTraspasante(String participante) {
		this.traspasante = participante;
	}

	/**
	 * Obtiene el atributo receptor
	 *
	 * @return El atrubuto receptor
	 */
	public String getReceptor() {
		return receptor;
	}

	/**
	 * Establece la propiedad receptor
	 *
	 * @param receptor el campo receptor a establecer
	 */
	public void setReceptor(String contraparte) {
		this.receptor = contraparte;
	}

	/**
	 * Obtiene el atributo descripcion
	 *
	 * @return El atrubuto descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la propiedad descripcion
	 *
	 * @param descripcion el campo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el atributo folioOperacion
	 *
	 * @return El atrubuto folioOperacion
	 */
	public Long getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * Establece la propiedad folioOperacion
	 *
	 * @param folioOperacion el campo folioOperacion a establecer
	 */
	public void setFolioOperacion(Long folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * Obtiene el atributo posicion
	 *
	 * @return El atrubuto posicion
	 */
	public PosicionDTO getPosicion() {
		return posicion;
	}

	/**
	 * Establece la propiedad posicion
	 *
	 * @param posicion el campo posicion a establecer
	 */
	public void setPosicion(PosicionDTO posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el atributo fechaConcertacion
	 *
	 * @return El atrubuto fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Establece la propiedad fechaConcertacion
	 *
	 * @param fechaConcertacion el campo fechaConcertacion a establecer
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Obtiene el atributo fechaLiquidacion
	 *
	 * @return El atrubuto fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece la propiedad fechaLiquidacion
	 *
	 * @param fechaLiquidacion el campo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
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
	 * @param tipoOperacion el campo tipoOperacion a establecer
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el atributo cantidad
	 *
	 * @return El atrubuto cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la propiedad cantidad
	 *
	 * @param cantidad el campo cantidad a establecer
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el valor del atributo entrega
	 *
	 * @return el valor del atributo entrega
	 */
	public BigInteger getEntrega() {
		return entrega;
	}

	/**
	 * Establece el valor del atributo entrega
	 *
	 * @param entrega el valor del atributo entrega a establecer.
	 */
	public void setEntrega(BigInteger entrada) {
		this.entrega = entrada;
	}

	/**
	 * Obtiene el atributo participante
	 *
	 * @return El atrubuto participante
	 */
	public String getParticipante() {
		return participante;
	}

	/**
	 * Establece la propiedad participante
	 *
	 * @param participante el campo participante a establecer
	 */
	public void setParticipante(String participante) {
		this.participante = participante;
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
	 * @param contraparte el campo contraparte a establecer
	 */
	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * Obtiene el valor del atributo custodio
	 *
	 * @return el valor del atributo custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * Establece el valor del atributo custodio
	 *
	 * @param custodio el valor del atributo custodio a establecer.
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * Obtiene el atributo precio
	 *
	 * @return El atrubuto precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Establece la propiedad precio
	 *
	 * @param precio el campo precio a establecer
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene el atributo importe
	 *
	 * @return El atrubuto importe
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Establece la propiedad importe
	 *
	 * @param importe el campo importe a establecer
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}

	/**
	 * Obtiene el atributo fechaAplicacion
	 *
	 * @return El atrubuto fechaAplicacion
	 */
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}

	/**
	 * Establece la propiedad fechaAplicacion
	 *
	 * @param fechaAplicacion el campo fechaAplicacion a establecer
	 */
	public void setFechaAplicacion(Date fechaRegistro) {
		this.fechaAplicacion = fechaRegistro;
	}

	/**
	 * Obtiene el valor del atributo pendiente
	 * 
	 * @return el valor del atributo pendiente
	 */
	public double getPendiente() {
		return pendiente;
	}

	/**
	 * Establece el valor del atributo pendiente
	 *
	 * @param pendiente el valor del atributo pendiente a establecer
	 */
	public void setPendiente(double pendiente) {
		this.pendiente = pendiente;
	}

}
