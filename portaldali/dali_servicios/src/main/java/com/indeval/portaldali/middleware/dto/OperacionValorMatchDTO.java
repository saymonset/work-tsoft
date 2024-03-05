/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * OperacionMatchDTO.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * DTO que representa los datos de una operación de valor / match
 *
 * @author Emigdio Hernández
 *
 */
public class OperacionValorMatchDTO implements Serializable {

	private static final long serialVersionUID = -5208733701485953412L;

	private boolean puedeConfirmar = false;

	private BigInteger idBitacoraMatch = null;

	private Long idInstruccionOperacionVal;

	private boolean puedeCancelar = false;

	private InstitucionDTO institucionReceptora;

	private InstitucionDTO institucionTraspasante;

	private InstitucionDTO institucionBancoTrabajo;

	private InstitucionDTO institucionRemitente;

	private TipoInstruccionDTO tipoInstruccion;

	private Long plazoReporto;

	private Long plazo;

	private String origenReceptor;

	private Long idEmisionPendiente;

	private CuentaDTO cuentaNombradaReceptora;

	private CuentaDTO cuentaNombradaTraspasante;

	private CuentaDTO cuentaNombradaBancoTrabajo;

	private TipoMensajeDTO tipoMensaje;

	private BovedaDTO bovedaDTO;

	private BovedaDTO bovedaEfectivoDTO;

	private String boveda;

	private String bovedaEfectivo;

	private EstadoInstruccionDTO estadoInstruccion;

	private CuponDTO cupon;

	private EstadoInstruccionDTO estadoInstruccionEnvio;

	private DivisaDTO divisaDTO;

	private String divisa;

	private EmisionDTO emision;

	/** El error de liquidación del DALI */
	private ErrorDaliDTO errorDali = new ErrorDaliDTO();

	private double precioTitulo;

	private double tasaNegociada;

	private long cantidadTitulos;

	private double importe;

	private Date fechaLiquidacion;

	private Date fechaConcertacion;

	private String folioInstruccionTraspasante;

	private String folioInstruccionReceptora;

	private String folioOrigen;

	private String folioControl;

	private int requiereMatch;

	private long idInstruccionAnterior;

	private long idInstruccionSiguiente;

	private String folioUsuario = null;

	private String origen;

	private String origenTraspasante;

	private String tasaReferencia;

	private double interesesGenerados;

	private boolean tieneParcialidades = false;

	private Date fechaIntereses;

	private Date fechaReporto;
	
	private Date fechaHoraCierreOperTra;
	
	private Date fechaHoraCierreOperRec;
	
	private Date fechaHoraEncolamientoTra;
	
	private Date fechaHoraEncolamientoRec;

	private String mensajeXmlMatch = null;

	/**
	 * nuevos campos para manejo por paquete
	 **/
	private boolean porPaquete;
	
	private String referenciaPaquete;
	
	private String totalOperacionesPaquete;
	
	private String numeroOperacionPaquete;
	
	private String totalTitulosPaquete;
	
	private String totalImportePaquete;
	
	/**
	 * Los datos de la operación miscelanea fiscal.
	 */
	private MiscelaneaFiscalDTO miscelaneaFiscal = null;

	/**
	 * Indica si esta operación tiene datos extra de una operación de
	 * miscelanea fiscal
	 */
	private boolean conMiscelaneaFiscal = false;

	/**
	 * Indica si esta operacion tiene un registro en la tabla del MOV
	 */
	private boolean conOperacion = false;

	/**
	 * Bandera que indica si la instruccion de liquidacion es un banco de
	 * trabajo
	 */
	private boolean instruccionLiquidacionBancoTrabajo;

	/** ID de la instruccion de liquidacion origen (padre) */
	private BigInteger idInstruccionLiquidacionOrigen;

	/** ID de la instruccion de liquidacion */
	private BigInteger idInstruccionLiquidacion;

	/**
	 * Bandera que indica si tiene boveda de efectivo*/
	private boolean tieneBovedaEfectivo;
	
	/**
	 * Obtiene el valor del atributo errorDali
	 *
	 * @return el valor del atributo errorDali
	 */
	public ErrorDaliDTO getErrorDali() {
		return errorDali;
	}

	/**
	 * Obtiene el valor del atributo conMiscelaneaFiscal
	 *
	 * @return el valor del atributo conMiscelaneaFiscal
	 */
	public boolean isConMiscelaneaFiscal() {
		return conMiscelaneaFiscal;
	}

	/**
	 * Obtiene el valor del atributo puedeCancelar
	 *
	 * @return el valor del atributo puedeCancelar
	 */
	public boolean isPuedeCancelar() {
		return puedeCancelar;
	}

	/**
	 * Establece el valor del atributo puedeCancelar
	 *
	 * @param puedeCancelar
	 *            el valor del atributo puedeCancelar a establecer
	 */
	public void setPuedeCancelar(boolean puedeCancelar) {
		this.puedeCancelar = puedeCancelar;
	}

	/**
	 * Establece el valor del atributo conMiscelaneaFiscal
	 *
	 * @param conMiscelaneaFiscal
	 *            el valor del atributo conMiscelaneaFiscal a establecer
	 */
	public void setConMiscelaneaFiscal(boolean conMiscelaneaFiscal) {
		this.conMiscelaneaFiscal = conMiscelaneaFiscal;
	}

	/**
	 * Obtiene el valor del atributo miscelaneaFiscal
	 *
	 * @return el valor del atributo miscelaneaFiscal
	 */
	public MiscelaneaFiscalDTO getMiscelaneaFiscal() {
		return miscelaneaFiscal;
	}

	/**
	 * Establece el valor del atributo miscelaneaFiscal
	 *
	 * @param miscelaneaFiscal
	 *            el valor del atributo miscelaneaFiscal a establecer
	 */
	public void setMiscelaneaFiscal(MiscelaneaFiscalDTO miscelaneaFiscal) {
		this.miscelaneaFiscal = miscelaneaFiscal;
	}

	/**
	 * @return the origenTraspasante
	 */
	public String getOrigenTraspasante() {
		return origenTraspasante;
	}

	/**
	 * @param origenTraspasante
	 *            the origenTraspasante to set
	 */
	public void setOrigenTraspasante(String origenTraspasante) {
		this.origenTraspasante = origenTraspasante;
	}

	/**
	 * Establece el valor del atributo errorDali
	 *
	 * @param errorDali
	 *            el valor del atributo errorDali a establecer.
	 */
	public void setErrorDali(ErrorDaliDTO errorDali) {
		this.errorDali = errorDali;
	}

	/**
	 * Obtiene el campo idInstruccionOperacionVal
	 *
	 * @return idInstruccionOperacionVal
	 */
	public Long getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}

	/**
	 * Asigna el campo idInstruccionOperacionVal
	 *
	 * @param idInstruccionOperacionVal
	 *            el valor de idInstruccionOperacionVal a asignar
	 */
	public void setIdInstruccionOperacionVal(Long idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}

	/**
	 * Obtiene el campo institucionReceptora
	 *
	 * @return institucionReceptora
	 */
	public InstitucionDTO getInstitucionReceptora() {
		return institucionReceptora;
	}

	/**
	 * Asigna el campo institucionReceptora
	 *
	 * @param institucionReceptora
	 *            el valor de institucionReceptora a asignar
	 */
	public void setInstitucionReceptora(InstitucionDTO institucionReceptora) {
		this.institucionReceptora = institucionReceptora;
	}

	/**
	 * Obtiene el campo institucionTraspasante
	 *
	 * @return institucionTraspasante
	 */
	public InstitucionDTO getInstitucionTraspasante() {
		return institucionTraspasante;
	}

	/**
	 * Asigna el campo institucionTraspasante
	 *
	 * @param institucionTraspasante
	 *            el valor de institucionTraspasante a asignar
	 */
	public void setInstitucionTraspasante(InstitucionDTO institucionTraspasante) {
		this.institucionTraspasante = institucionTraspasante;
	}

	/**
	 * Obtiene el campo institucionBancoTrabajo
	 *
	 * @return institucionBancoTrabajo
	 */
	public InstitucionDTO getInstitucionBancoTrabajo() {
		return institucionBancoTrabajo;
	}

	/**
	 * Asigna el campo institucionBancoTrabajo
	 *
	 * @param institucionBancoTrabajo
	 *            el valor de institucionBancoTrabajo a asignar
	 */
	public void setInstitucionBancoTrabajo(
			InstitucionDTO institucionBancoTrabajo) {
		this.institucionBancoTrabajo = institucionBancoTrabajo;
	}

	/**
	 * Obtiene el campo tipoInstruccion
	 *
	 * @return tipoInstruccion
	 */
	public TipoInstruccionDTO getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Asigna el campo tipoInstruccion
	 *
	 * @param tipoInstruccion
	 *            el valor de tipoInstruccion a asignar
	 */
	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Obtiene el campo plazoReporto
	 *
	 * @return plazoReporto
	 */
	public Long getPlazoReporto() {
		if (fechaReporto != null && fechaLiquidacion != null) {
			Calendar inicio = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			inicio.setTime(this.fechaLiquidacion);
			fin.setTime(this.fechaReporto);
			// Se toman las fechas sin hora
			GregorianCalendar date1 = new GregorianCalendar(inicio
					.get(Calendar.YEAR), inicio.get(Calendar.MONTH), inicio
					.get(Calendar.DAY_OF_MONTH));
			GregorianCalendar date2 = new GregorianCalendar(fin
					.get(Calendar.YEAR), fin.get(Calendar.MONTH), fin
					.get(Calendar.DAY_OF_MONTH));
			// Se restan entre ellas en milisegundos
			long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
			// Se le suma un cuarto de un día para evitar que el redondeo le
			// quite un día
			difms = difms
					+ (com.indeval.portaldali.middleware.services.util.util.Constantes.MILLISECONDXDAY / 4);
			// Se divide entre los milisegundos de un dia
			long difd = difms
					/ com.indeval.portaldali.middleware.services.util.util.Constantes.MILLISECONDXDAY;
			this.plazoReporto = difd;
		}
		return this.plazoReporto;
	}

	/**
	 * Asigna el campo plazoReporto
	 *
	 * @param plazoReporto
	 *            el valor de plazoReporto a asignar
	 */
	public void setPlazoReporto(Long plazoReporto) {
		this.plazoReporto = plazoReporto;
	}

	/**
	 * Obtiene el campo origenReceptor
	 *
	 * @return origenReceptor
	 */
	public String getOrigenReceptor() {
		return origenReceptor;
	}

	/**
	 * Asigna el campo origenReceptor
	 *
	 * @param origenReceptor
	 *            el valor de origenReceptor a asignar
	 */
	public void setOrigenReceptor(String origenReceptor) {
		this.origenReceptor = origenReceptor;
	}

	/**
	 * Obtiene el campo idEmisionPendiente
	 *
	 * @return idEmisionPendiente
	 */
	public Long getIdEmisionPendiente() {
		return idEmisionPendiente;
	}

	/**
	 * Asigna el campo idEmisionPendiente
	 *
	 * @param idEmisionPendiente
	 *            el valor de idEmisionPendiente a asignar
	 */
	public void setIdEmisionPendiente(Long idEmisionPendiente) {
		this.idEmisionPendiente = idEmisionPendiente;
	}

	/**
	 * Obtiene el campo cuentaNombradaReceptora
	 *
	 * @return cuentaNombradaReceptora
	 */
	public CuentaDTO getCuentaNombradaReceptora() {
		return cuentaNombradaReceptora;
	}

	/**
	 * Asigna el campo cuentaNombradaReceptora
	 *
	 * @param cuentaNombradaReceptora
	 *            el valor de cuentaNombradaReceptora a asignar
	 */
	public void setCuentaNombradaReceptora(CuentaDTO cuentaNombradaReceptora) {
		this.cuentaNombradaReceptora = cuentaNombradaReceptora;
	}

	/**
	 * Obtiene el campo cuentaNombradaTraspasante
	 *
	 * @return cuentaNombradaTraspasante
	 */
	public CuentaDTO getCuentaNombradaTraspasante() {
		return cuentaNombradaTraspasante;
	}

	/**
	 * Asigna el campo cuentaNombradaTraspasante
	 *
	 * @param cuentaNombradaTraspasante
	 *            el valor de cuentaNombradaTraspasante a asignar
	 */
	public void setCuentaNombradaTraspasante(CuentaDTO cuentaNombradaTraspasante) {
		this.cuentaNombradaTraspasante = cuentaNombradaTraspasante;
	}

	/**
	 * Obtiene el campo cuentaNombradaBancoTrabajo
	 *
	 * @return cuentaNombradaBancoTrabajo
	 */
	public CuentaDTO getCuentaNombradaBancoTrabajo() {
		return cuentaNombradaBancoTrabajo;
	}

	/**
	 * Asigna el campo cuentaNombradaBancoTrabajo
	 *
	 * @param cuentaNombradaBancoTrabajo
	 *            el valor de cuentaNombradaBancoTrabajo a asignar
	 */
	public void setCuentaNombradaBancoTrabajo(
			CuentaDTO cuentaNombradaBancoTrabajo) {
		this.cuentaNombradaBancoTrabajo = cuentaNombradaBancoTrabajo;
	}

	/**
	 * Obtiene el campo tipoMensaje
	 *
	 * @return tipoMensaje
	 */
	public TipoMensajeDTO getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * Asigna el campo tipoMensaje
	 *
	 * @param tipoMensaje
	 *            el valor de tipoMensaje a asignar
	 */
	public void setTipoMensaje(TipoMensajeDTO tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * Obtiene el campo estadoInstruccion
	 *
	 * @return estadoInstruccion
	 */
	public EstadoInstruccionDTO getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * Asigna el campo estadoInstruccion
	 *
	 * @param estadoInstruccion
	 *            el valor de estadoInstruccion a asignar
	 */
	public void setEstadoInstruccion(EstadoInstruccionDTO estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * Obtiene el campo cupon
	 *
	 * @return cupon
	 */
	public CuponDTO getCupon() {
		return cupon;
	}

	/**
	 * Asigna el campo cupon
	 *
	 * @param cupon
	 *            el valor de cupon a asignar
	 */
	public void setCupon(CuponDTO cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el campo estadoInstruccionEnvio
	 *
	 * @return estadoInstruccionEnvio
	 */
	public EstadoInstruccionDTO getEstadoInstruccionEnvio() {
		return estadoInstruccionEnvio;
	}

	/**
	 * Asigna el campo estadoInstruccionEnvio
	 *
	 * @param estadoInstruccionEnvio
	 *            el valor de estadoInstruccionEnvio a asignar
	 */
	public void setEstadoInstruccionEnvio(
			EstadoInstruccionDTO estadoInstruccionEnvio) {
		this.estadoInstruccionEnvio = estadoInstruccionEnvio;
	}

	/**
	 * Obtiene el valor del atributo folioOrigen
	 *
	 * @return el valor del atributo folioOrigen
	 */
	public String getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * Establece el valor del atributo folioOrigen
	 *
	 * @param folioOrigen
	 *            el valor del atributo folioOrigen a establecer.
	 */
	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	/**
	 * Obtiene el campo precioTitulo
	 *
	 * @return precioTitulo
	 */
	public double getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * Asigna el campo precioTitulo
	 *
	 * @param precioTitulo
	 *            el valor de precioTitulo a asignar
	 */
	public void setPrecioTitulo(double precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * Obtiene el campo tasaNegociada
	 *
	 * @return tasaNegociada
	 */
	public double getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * Asigna el campo tasaNegociada
	 *
	 * @param tasaNegociada
	 *            el valor de tasaNegociada a asignar
	 */
	public void setTasaNegociada(double tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * Obtiene el campo cantidadTitulos
	 *
	 * @return cantidadTitulos
	 */
	public long getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * Asigna el campo cantidadTitulos
	 *
	 * @param cantidadTitulos
	 *            el valor de cantidadTitulos a asignar
	 */
	public void setCantidadTitulos(long cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * Obtiene el campo importe
	 *
	 * @return importe
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Asigna el campo importe
	 *
	 * @param importe
	 *            el valor de importe a asignar
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}

	/**
	 * Obtiene el campo fechaLiquidacion
	 *
	 * @return fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Asigna el campo fechaLiquidacion
	 *
	 * @param fechaLiquidacion
	 *            el valor de fechaLiquidacion a asignar
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene el campo fechaConcertacion
	 *
	 * @return fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Asigna el campo fechaConcertacion
	 *
	 * @param fechaConcertacion
	 *            el valor de fechaConcertacion a asignar
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Obtiene el campo folioInstruccionTraspasante
	 *
	 * @return folioInstruccionTraspasante
	 */
	public String getFolioInstruccionTraspasante() {
		return folioInstruccionTraspasante;
	}

	/**
	 * Asigna el campo folioInstruccionTraspasante
	 *
	 * @param folioInstruccionTraspasante
	 *            el valor de folioInstruccionTraspasante a asignar
	 */
	public void setFolioInstruccionTraspasante(
			String folioInstruccionTraspasante) {
		this.folioInstruccionTraspasante = folioInstruccionTraspasante;
	}

	/**
	 * Obtiene el campo folioInstruccionReceptora
	 *
	 * @return folioInstruccionReceptora
	 */
	public String getFolioInstruccionReceptora() {
		return folioInstruccionReceptora;
	}

	/**
	 * Asigna el campo folioInstruccionReceptora
	 *
	 * @param folioInstruccionReceptora
	 *            el valor de folioInstruccionReceptora a asignar
	 */
	public void setFolioInstruccionReceptora(String folioInstruccionReceptora) {
		this.folioInstruccionReceptora = folioInstruccionReceptora;
	}

	/**
	 * Obtiene el campo folioControl
	 *
	 * @return folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * Asigna el campo folioControl
	 *
	 * @param folioControl
	 *            el valor de folioControl a asignar
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * Obtiene el campo requiereMatch
	 *
	 * @return requiereMatch
	 */
	public int getRequiereMatch() {
		return requiereMatch;
	}

	/**
	 * Asigna el campo requiereMatch
	 *
	 * @param requiereMatch
	 *            el valor de requiereMatch a asignar
	 */
	public void setRequiereMatch(int requiereMatch) {
		this.requiereMatch = requiereMatch;
	}

	/**
	 * Obtiene el campo idInstruccionAnterior
	 *
	 * @return idInstruccionAnterior
	 */
	public long getIdInstruccionAnterior() {
		return idInstruccionAnterior;
	}

	/**
	 * Asigna el campo idInstruccionAnterior
	 *
	 * @param idInstruccionAnterior
	 *            el valor de idInstruccionAnterior a asignar
	 */
	public void setIdInstruccionAnterior(long idInstruccionAnterior) {
		this.idInstruccionAnterior = idInstruccionAnterior;
	}

	/**
	 * Obtiene el campo idInstruccionSiguiente
	 *
	 * @return idInstruccionSiguiente
	 */
	public long getIdInstruccionSiguiente() {
		return idInstruccionSiguiente;
	}

	/**
	 * Asigna el campo idInstruccionSiguiente
	 *
	 * @param idInstruccionSiguiente
	 *            el valor de idInstruccionSiguiente a asignar
	 */
	public void setIdInstruccionSiguiente(long idInstruccionSiguiente) {
		this.idInstruccionSiguiente = idInstruccionSiguiente;
	}

	/**
	 * Obtiene el campo origen
	 *
	 * @return origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Asigna el campo origen
	 *
	 * @param origen
	 *            el valor de origen a asignar
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Obtiene el campo tasaReferencia
	 *
	 * @return tasaReferencia
	 */
	public String getTasaReferencia() {
		return tasaReferencia;
	}

	/**
	 * Asigna el campo tasaReferencia
	 *
	 * @param tasaReferencia
	 *            el valor de tasaReferencia a asignar
	 */
	public void setTasaReferencia(String tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * Obtiene el campo interesesGenerados
	 *
	 * @return interesesGenerados
	 */
	public double getInteresesGenerados() {
		return interesesGenerados;
	}

	/**
	 * Asigna el campo interesesGenerados
	 *
	 * @param interesesGenerados
	 *            el valor de interesesGenerados a asignar
	 */
	public void setInteresesGenerados(double interesesGenerados) {
		this.interesesGenerados = interesesGenerados;
	}

	/**
	 * Obtiene el campo fechaIntereses
	 *
	 * @return fechaIntereses
	 */
	public Date getFechaIntereses() {
		return fechaIntereses;
	}

	/**
	 * Asigna el campo fechaIntereses
	 *
	 * @param fechaIntereses
	 *            el valor de fechaIntereses a asignar
	 */
	public void setFechaIntereses(Date fechaIntereses) {
		this.fechaIntereses = fechaIntereses;
	}

	/**
	 * Obtiene el campo emision
	 *
	 * @return emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Asigna el campo emision
	 *
	 * @param emision
	 *            el valor de emision a asignar
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el campo folioUsuario
	 *
	 * @return folioUsuario
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}

	/**
	 * Asigna el campo folioUsuario
	 *
	 * @param folioUsuario
	 *            el valor de folioUsuario a asignar
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	/**
	 * Obtiene el campo conOperacion
	 *
	 * @return conOperacion
	 */
	public boolean isConOperacion() {
		return conOperacion;
	}

	/**
	 * Asigna el campo conOperacion
	 *
	 * @param conOperacion
	 *            el valor de conOperacion a asignar
	 */
	public void setConOperacion(boolean conOperacion) {
		this.conOperacion = conOperacion;
	}

	/**
	 * Obtiene el campo idBitacoraMatch
	 *
	 * @return idBitacoraMatch
	 */
	public BigInteger getIdBitacoraMatch() {
		return idBitacoraMatch;
	}

	/**
	 * Asigna el campo idBitacoraMatch
	 *
	 * @param idBitacoraMatch
	 *            el valor de idBitacoraMatch a asignar
	 */
	public void setIdBitacoraMatch(BigInteger idBitacoraMatch) {
		this.idBitacoraMatch = idBitacoraMatch;
	}

	/**
	 * Obtiene el campo fechaReporto
	 *
	 * @return fechaReporto
	 */
	public Date getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * Asigna el campo fechaReporto
	 *
	 * @param fechaReporto
	 *            el valor de fechaReporto a asignar
	 */
	public void setFechaReporto(Date fechaReporto) {
		this.fechaReporto = fechaReporto;
	}

	public Date getFechaHoraCierreOperTra() {
		return fechaHoraCierreOperTra;
	}

	public void setFechaHoraCierreOperTra(Date fechaHoraCierreOperTra) {
		this.fechaHoraCierreOperTra = fechaHoraCierreOperTra;
	}

	public Date getFechaHoraCierreOperRec() {
		return fechaHoraCierreOperRec;
	}

	public void setFechaHoraCierreOperRec(Date fechaHoraCierreOperRec) {
		this.fechaHoraCierreOperRec = fechaHoraCierreOperRec;
	}

	public Date getFechaHoraEncolamientoTra() {
		return fechaHoraEncolamientoTra;
	}

	public void setFechaHoraEncolamientoTra(Date fechaHoraEncolamientoTra) {
		this.fechaHoraEncolamientoTra = fechaHoraEncolamientoTra;
	}

	public Date getFechaHoraEncolamientoRec() {
		return fechaHoraEncolamientoRec;
	}

	public void setFechaHoraEncolamientoRec(Date fechaHoraEncolamientoRec) {
		this.fechaHoraEncolamientoRec = fechaHoraEncolamientoRec;
	}

	/**
	 * Obtiene el campo tieneParcialidades
	 *
	 * @return tieneParcialidades
	 */
	public boolean isTieneParcialidades() {
		return tieneParcialidades;
	}

	/**
	 * Asigna el campo tieneParcialidades
	 *
	 * @param tieneParcialidades
	 *            el valor de tieneParcialidades a asignar
	 */
	public void setTieneParcialidades(boolean tieneParcialidades) {
		this.tieneParcialidades = tieneParcialidades;
	}

	/**
	 * Obtiene el campo mensajeXmlMatch
	 *
	 * @return mensajeXmlMatch
	 */
	public String getMensajeXmlMatch() {
		return mensajeXmlMatch;
	}

	/**
	 * Asigna el campo mensajeXmlMatch
	 *
	 * @param mensajeXmlMatch
	 *            el valor de mensajeXmlMatch a asignar
	 */
	public void setMensajeXmlMatch(String mensajeXmlMatch) {
		this.mensajeXmlMatch = mensajeXmlMatch;
	}

	/**
	 * Obtiene el campo puedeConfirmar
	 *
	 * @return puedeConfirmar
	 */
	public boolean isPuedeConfirmar() {
		return puedeConfirmar;
	}

	/**
	 * Asigna el campo puedeConfirmar
	 *
	 * @param puedeConfirmar
	 *            el valor de puedeConfirmar a asignar
	 */
	public void setPuedeConfirmar(boolean puedeConfirmar) {
		this.puedeConfirmar = puedeConfirmar;
	}

	/**
	 * Obtiene el campo institucionRemitente
	 *
	 * @return institucionRemitente
	 */
	public InstitucionDTO getInstitucionRemitente() {
		return institucionRemitente;
	}

	/**
	 * Asigna el campo institucionRemitente
	 *
	 * @param institucionRemitente
	 *            el valor de institucionRemitente a asignar
	 */
	public void setInstitucionRemitente(InstitucionDTO institucionRemitente) {
		this.institucionRemitente = institucionRemitente;
	}

	/**
	 * Obtiene el campo plazo
	 *
	 * @return plazo
	 */
	public Long getPlazo() {
		return plazo;
	}

	/**
	 * Asigna el campo plazo
	 *
	 * @param plazo
	 *            el valor de plazo a asignar
	 */
	public void setPlazo(Long plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return the instruccionLiquidacionBancoTrabajo
	 */
	public boolean isInstruccionLiquidacionBancoTrabajo() {
		return instruccionLiquidacionBancoTrabajo;
	}

	/**
	 * @param instruccionLiquidacionBancoTrabajo
	 *            the instruccionLiquidacionBancoTrabajo to set
	 */
	public void setInstruccionLiquidacionBancoTrabajo(
			boolean instruccionLiquidacionBancoTrabajo) {
		this.instruccionLiquidacionBancoTrabajo = instruccionLiquidacionBancoTrabajo;
	}

	/**
	 * @return the idInstruccionLiquidacionOrigen
	 */
	public BigInteger getIdInstruccionLiquidacionOrigen() {
		return idInstruccionLiquidacionOrigen;
	}

	/**
	 * @param idInstruccionLiquidacionOrigen
	 *            the idInstruccionLiquidacionOrigen to set
	 */
	public void setIdInstruccionLiquidacionOrigen(
			BigInteger idInstruccionLiquidacionOrigen) {
		this.idInstruccionLiquidacionOrigen = idInstruccionLiquidacionOrigen;
	}

	/**
	 * @return the idInstruccionLiquidacion
	 */
	public BigInteger getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}

	/**
	 * @param idInstruccionLiquidacion
	 *            the idInstruccionLiquidacion to set
	 */
	public void setIdInstruccionLiquidacion(BigInteger idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}

	/**
	 * @return the boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @return the bovedaEfectivo
	 */
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @param bovedaEfectivo the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the divisaDTO
	 */
	public DivisaDTO getDivisaDTO() {
		return divisaDTO;
	}

	/**
	 * @param divisaDTO the divisaDTO to set
	 */
	public void setDivisaDTO(DivisaDTO divisaDTO) {
		this.divisaDTO = divisaDTO;
	}

	/**
	 * @return the bovedaDTO
	 */
	public BovedaDTO getBovedaDTO() {
		return bovedaDTO;
	}

	/**
	 * @return the bovedaEfectivoDTO
	 */
	public BovedaDTO getBovedaEfectivoDTO() {
		return bovedaEfectivoDTO;
	}

	/**
	 * @param bovedaDTO the bovedaDTO to set
	 */
	public void setBovedaDTO(BovedaDTO bovedaDTO) {
		this.bovedaDTO = bovedaDTO;
	}

	/**
	 * @param bovedaEfectivoDTO the bovedaEfectivoDTO to set
	 */
	public void setBovedaEfectivoDTO(BovedaDTO bovedaEfectivoDTO) {
		this.bovedaEfectivoDTO = bovedaEfectivoDTO;
	}

	/**
	 * Obtiene una cadena con las claves de tipoInstruccion y Divisa separadas por un guion
	 * @return	La cadena concatenada.
	 */
	public String getLlaveTipoInstruccionDivisa(){
		return (tipoInstruccion.getNombreCorto() + "-" + divisa);
	}
	
	public boolean isPorPaquete() {
		return porPaquete;
	}
	
	public void setPorPaquete(boolean porPaquete) {
		this.porPaquete = porPaquete;
	}
	
	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	public String getTotalOperacionesPaquete() {
		return totalOperacionesPaquete;
	}

	public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	public String getNumeroOperacionPaquete() {
		return numeroOperacionPaquete;
	}

	public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	public String getTotalTitulosPaquete() {
		return totalTitulosPaquete;
	}

	public void setTotalTitulosPaquete(String totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}

	public String getTotalImportePaquete() {
		return totalImportePaquete;
	}

	public void setTotalImportePaquete(String totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if (obj instanceof OperacionValorMatchDTO) {
			OperacionValorMatchDTO operacion = (OperacionValorMatchDTO) obj;
			if (StringUtils.isNotBlank(operacion.getFolioControl())
					&& StringUtils.isNotBlank(this.folioControl)
					&& operacion.getFolioControl().equals(this.folioControl)) {
				resultado = true;
			}
		} else {
			resultado = super.equals(obj);
		}
		return resultado;
	}

	public boolean isTieneBovedaEfectivo() {
		return tieneBovedaEfectivo;
	}

	public void setTieneBovedaEfectivo(boolean tieneBovedaEfectivo) {
		this.tieneBovedaEfectivo = tieneBovedaEfectivo;
	}

	
	public Date getFechaAdquisicion()
	{
		Date regresa = null;
		if(miscelaneaFiscal != null && emision != null)
		{
			if(emision.getTipoValor().getMercado().getId() != 1)//para capitales se limpia
			{
				regresa = miscelaneaFiscal.getFechaAdquisicion();
			}
		}
		
		return regresa;
	}
}
