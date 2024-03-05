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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Objeto de transferencia de datos para encapsular la información referente al
 * estado de cuenta de posición de valores.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class RegistroContablePosicionNombradaDTO implements Serializable {

	/** Default serial version id */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del registro contable que se registró en una operación de
	 * una posición
	 */
	private long idRegistroContable;

	/** Posición correspondiente a la operación que se lleva a cabo */
	private PosicionDTO posicion;

	/** Indica si el registro contable proviene del histórico */
	private boolean historico = false;

	/** Fecha de registro de la operación */
	private Date fecha;

	/** Fecha inicial para la búsqueda de operaciones */
	private Date fechaInicial;

	/** Fecha final para la búsqueda de operaciones */
	private Date fechaFinal;

	/** Tipo de acción: Cargo o Abono */
	private TipoAccionDTO tipoAccion;

	/** Tipo de operación */
	private TipoOperacionDTO tipoOperacion;

	/** Nombre y cuenta del participante de la operación */
	private String participante;

	/** La operación a la que pertenece este registro contable */
	private OperacionPosicionDTO operacion;

	/** Nombre y cuenta de la contraparte de la operación */
	private String contraparte;
	/** número de cuenta de la contraparte */
	private String cuentaContraparte;

	/** Folio de la instrucción a la que pertenece la operación */
	private long folio;

	/** Cantidad de posiciones recibidas en la operación */
	private BigInteger cantidadRecepcion;

	/** Cantidad de posiciones entregadas en la operación */
	private BigInteger cantidadEntrega;

	/** El precio por titulo entregado en la operación */
	private double precio;

	/** Cantidad de posiciones registradas en la operación */
	private BigInteger cantidad;

	/** Valor de la posición disponible de la operación */
	private BigInteger posicionDisponible;

	/** Valor de la posición no disponible de la operación */
	private BigInteger posicionNoDisponible;

	/** Valor de la suma de la posición disponible y no disponible */
	private BigInteger posicionTotal;

	/** Indica si el cargo se realiza al disponible o al no disponible */
	private Long cargoA;

	/** El identificador del detalle de la posición y la operación */
	private Long idPosicionOperacionNombrada;

	/** El cupón relacionado con la operación y la posición */
	private CuponDTO cupon;

	/** La descripción del registro contable */
	private String descripcion;
	/**
	 * Descripción completa del registro
	 */
	private String descripcionLarga;

	/** Folio de la operación a la que pertenece esta operación */
	private Long folioOperacion;

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
	private String claveTipoInstitucionContraparte;

	/** El folio de la institución del participante */
	private String folioInstitucionContraparte;

	/** El número de cuenta del participante */
	private String numeroCuentaContraparte;

	/** La clave del tipo de institución del participante */
	private String claveTipoInstitucionParticipante;

	/** El folio de la institución del participante */
	private String folioInstitucionParticipante;

	/** El número de cuenta del participante */
	private String numeroCuentaParticipante;

	/**
	 * Indicador de la posicion inicial antes del movimiento
	 */
	private BigInteger posicionInicial;
	
	/**
	 * Indicador de la posicion final despues del movimiento
	 */
	private BigInteger posicionFinal;

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
	 * Obtiene el valor del atributo historico
	 * 
	 * @return el valor del atributo historico
	 */
	public boolean isHistorico() {
		return historico;
	}

	/**
	 * Establece el valor del atributo historico
	 * 
	 * @param historico
	 *            el valor del atributo historico a establecer
	 */
	public void setHistorico(boolean historico) {
		this.historico = historico;
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
	 * Obtiene el valor del atributo cargoA
	 * 
	 * @return el valor del atributo cargoA
	 */
	public Long getCargoA() {
		return cargoA;
	}

	/**
	 * Establece el valor del atributo cargoA
	 * 
	 * @param cargoA
	 *            el valor del atributo cargoA a establecer
	 */
	public void setCargoA(Long cargoA) {
		this.cargoA = cargoA;
	}

	/**
	 * Obtiene el valor del atributo precio
	 * 
	 * @return el valor del atributo precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Establece el valor del atributo precio
	 * 
	 * @param precio
	 *            el valor del atributo precio a establecer
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
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
	 *            el valor del atributo operacion a establecer
	 */
	public void setOperacion(OperacionPosicionDTO operacion) {
		this.operacion = operacion;
	}

	/**
	 * Obtiene el valor del atributo folioOperacion
	 * 
	 * @return el valor del atributo folioOperacion
	 */
	public Long getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * @return the claveTipoInstitucionContraparte
	 */
	public String getClaveTipoInstitucionContraparte() {
		return claveTipoInstitucionContraparte;
	}

	/**
	 * @param claveTipoInstitucionContraparte
	 *            the claveTipoInstitucionContraparte to set
	 */
	public void setClaveTipoInstitucionContraparte(String claveTipoInstitucionContraparte) {
		this.claveTipoInstitucionContraparte = claveTipoInstitucionContraparte;
	}

	/**
	 * @return the folioInstitucionContraparte
	 */
	public String getFolioInstitucionContraparte() {
		return folioInstitucionContraparte;
	}

	/**
	 * @param folioInstitucionContraparte
	 *            the folioInstitucionContraparte to set
	 */
	public void setFolioInstitucionContraparte(String folioInstitucionContraparte) {
		this.folioInstitucionContraparte = folioInstitucionContraparte;
	}

	/**
	 * @return the numeroCuentaContraparte
	 */
	public String getNumeroCuentaContraparte() {
		return numeroCuentaContraparte;
	}

	/**
	 * @param numeroCuentaContraparte
	 *            the numeroCuentaContraparte to set
	 */
	public void setNumeroCuentaContraparte(String numeroCuentaContraparte) {
		this.numeroCuentaContraparte = numeroCuentaContraparte;
	}

	/**
	 * Establece el valor del atributo folioOperacion
	 * 
	 * @param folioOperacion
	 *            el valor del atributo folioOperacion a establecer
	 */
	public void setFolioOperacion(Long folioOperacion) {
		this.folioOperacion = folioOperacion;
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
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public CuponDTO getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer
	 */
	public void setCupon(CuponDTO cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo idPosicionOperacionNombrada
	 * 
	 * @return el valor del atributo idPosicionOperacionNombrada
	 */
	public Long getIdPosicionOperacionNombrada() {
		return idPosicionOperacionNombrada;
	}

	/**
	 * Establece el valor del atributo idPosicionOperacionNombrada
	 * 
	 * @param idPosicionOperacionNombrada
	 *            el valor del atributo idPosicionOperacionNombrada a
	 *            establecer.
	 */
	public void setIdPosicionOperacionNombrada(Long idPosicionOperacionNombrada) {
		this.idPosicionOperacionNombrada = idPosicionOperacionNombrada;
	}

	/**
	 * Obtiene el valor del atributo idRegistroContable
	 * 
	 * @return el valor del atributo idRegistroContable
	 */
	public long getIdRegistroContable() {
		return idRegistroContable;
	}

	/**
	 * Establece el valor del atributo idRegistroContable
	 * 
	 * @param id
	 *            el valor del atributo idRegistroContable a establecer.
	 */
	public void setIdRegistroContable(long idRegistroContable) {
		this.idRegistroContable = idRegistroContable;
	}

	/**
	 * Obtiene el valor del atributo posicion
	 * 
	 * @return el valor del atributo posicion
	 */
	public PosicionDTO getPosicion() {
		return posicion;
	}

	/**
	 * Establece el valor del atributo posicion
	 * 
	 * @param posicion
	 *            el valor del atributo posicion a establecer.
	 */
	public void setPosicion(PosicionDTO posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor del atributo fecha
	 * 
	 * @param fecha
	 *            el valor del atributo fecha a establecer.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del atributo fechaInicial
	 * 
	 * @return el valor del atributo fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Establece el valor del atributo fechaInicial
	 * 
	 * @param fechaInicial
	 *            el valor del atributo fechaInicial a establecer.
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el valor del atributo fechaFinal
	 * 
	 * @return el valor del atributo fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Establece el valor del atributo fechaFinal
	 * 
	 * @param fechaFinal
	 *            el valor del atributo fechaFinal a establecer.
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Obtiene el valor del atributo tipoAccion
	 * 
	 * @return el valor del atributo tipoAccion
	 */
	public TipoAccionDTO getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * Establece el valor del atributo tipoAccion
	 * 
	 * @param tipoAccion
	 *            el valor del atributo tipoAccion a establecer.
	 */
	public void setTipoAccion(TipoAccionDTO tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	/**
	 * Obtiene el valor del atributo tipoOperacion
	 * 
	 * @return el valor del atributo tipoOperacion
	 */
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Establece el valor del atributo tipoOperacion
	 * 
	 * @param tipoOperacion
	 *            el valor del atributo tipoOperacion a establecer.
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el valor del atributo participante
	 * 
	 * @return el valor del atributo participante
	 */
	public String getParticipante() {
		return participante;
	}

	/**
	 * Establece el valor del atributo participante
	 * 
	 * @param participante
	 *            el valor del atributo participante a establecer.
	 */
	public void setParticipante(String participante) {
		this.participante = participante;
	}

	/**
	 * Obtiene el valor del atributo contraparte
	 * 
	 * @return el valor del atributo contraparte
	 */
	public String getContraparte() {
		return contraparte;
	}

	/**
	 * Establece el valor del atributo contraparte
	 * 
	 * @param contraparte
	 *            el valor del atributo contraparte a establecer.
	 */
	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * Obtiene el valor del atributo folio
	 * 
	 * @return el valor del atributo folio
	 */
	public long getFolio() {
		return folio;
	}

	/**
	 * Establece el valor del atributo folio
	 * 
	 * @param folio
	 *            el valor del atributo folio a establecer.
	 */
	public void setFolio(long folio) {
		this.folio = folio;
	}

	/**
	 * Obtiene el valor del atributo cantidadRecepcion
	 * 
	 * @return el valor del atributo cantidadRecepcion
	 */
	public BigInteger getCantidadRecepcion() {
		return cantidadRecepcion;
	}

	/**
	 * Establece el valor del atributo cantidadRecepcion
	 * 
	 * @param cantidadRecepcion
	 *            el valor del atributo cantidadRecepcion a establecer
	 */
	public void setCantidadRecepcion(BigInteger cantidadRecepcion) {
		this.cantidadRecepcion = cantidadRecepcion;
	}

	/**
	 * Obtiene el valor del atributo cantidadEntrega
	 * 
	 * @return el valor del atributo cantidadEntrega
	 */
	public BigInteger getCantidadEntrega() {
		return cantidadEntrega;
	}

	/**
	 * Establece el valor del atributo cantidadEntrega
	 * 
	 * @param cantidadEntrega
	 *            el valor del atributo cantidadEntrega a establecer
	 */
	public void setCantidadEntrega(BigInteger cantidadEntrega) {
		this.cantidadEntrega = cantidadEntrega;
	}

	/**
	 * Obtiene el valor del atributo cantidad
	 * 
	 * @return el valor del atributo cantidad
	 */
	public BigInteger getCantidad() {
		return cantidad;
	}

	/**
	 * Establece el valor del atributo cantidad
	 * 
	 * @param cantidad
	 *            el valor del atributo cantidad a establecer
	 */
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el valor del atributo posicionDisponible
	 * 
	 * @return el valor del atributo posicionDisponible
	 */
	public BigInteger getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Establece el valor del atributo posicionDisponible
	 * 
	 * @param posicionDisponible
	 *            el valor del atributo posicionDisponible a establecer
	 */
	public void setPosicionDisponible(BigInteger posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionNoDisponible
	 * 
	 * @return el valor del atributo posicionNoDisponible
	 */
	public BigInteger getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Establece el valor del atributo posicionNoDisponible
	 * 
	 * @param posicionNoDisponible
	 *            el valor del atributo posicionNoDisponible a establecer
	 */
	public void setPosicionNoDisponible(BigInteger posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionTotal
	 * 
	 * @return el valor del atributo posicionTotal
	 */
	public BigInteger getPosicionTotal() {
		return posicionTotal;
	}

	/**
	 * Establece el valor del atributo posicionTotal
	 * 
	 * @param posicionTotal
	 *            el valor del atributo posicionTotal a establecer
	 */
	public void setPosicionTotal(BigInteger posicionTotal) {
		this.posicionTotal = posicionTotal;
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
	 * @return the posicionInicial
	 */
	public BigInteger getPosicionInicial() {
		return posicionInicial;
	}

	/**
	 * @param posicionInicial the posicionInicial to set
	 */
	public void setPosicionInicial(BigInteger posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	/**
	 * @return the posicionFinal
	 */
	public BigInteger getPosicionFinal() {
		return posicionFinal;
	}

	/**
	 * @param posicionFinal the posicionFinal to set
	 */
	public void setPosicionFinal(BigInteger posicionFinal) {
		this.posicionFinal = posicionFinal;
	}
}
