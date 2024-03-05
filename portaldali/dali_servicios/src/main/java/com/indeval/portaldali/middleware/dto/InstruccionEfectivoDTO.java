/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * InstruccionEfectivoDTO.java
 * Mar 6, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Data Transfer Object que representa una instrucción de efectivo del MO$.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 * 
 */
/**
 * @author cperez
 *
 */
public class InstruccionEfectivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id del elemento en la base de datos
	 */
	private BigInteger idInstruccion;
	
	/** La institución receptora del efectivo */
	private InstitucionDTO institucionReceptora;

	/** La cuenta de efectivo receptora */
	private CuentaDTO cuentaReceptora;

	/** La institución traspasante del efectivo */
	private InstitucionDTO institucionTraspasante;

	/** La cuenta de efectivo traspasante */
	private CuentaDTO cuentaTraspasante;

	/** El identificador del tipo de instrucción */
	private TipoInstruccionDTO tipoInstruccion;
	
	/** El tipo de liquidacion al inicio que se incluy dentro del mensaje al tipo de liquidacion */
	private TipoLiquidacionDTO tipoLiquidacion;
	
	/** El tipo de liquidacion al vencimiento que se incluy dentro del mensaje al tipo de liquidacion */
	private TipoLiquidacionDTO liquidacionVencimiento;

	/** El identificador del tipo de mensaje */
	private TipoMensajeDTO tipoMensaje;

	/** El importe de la instrucción de efectivo */
	private BigDecimal monto;
	
	/** El importe de la instrucción de efectivo */
	private BigDecimal montoVencimiento;

	private Double tasa;
	
	/** La fecha en que se liquid la instrucción */
	private Date fechaLiquidacion;
	
	/** La fecha en que se vencio la instrucción */
	private Date fechaVencimiento;
	
	/** La fecha en que se vencio la instrucción */
	private Date fechaConcertacion;

	/** La fecha en que se autorizo la operacion */
	private Date fechaAutorizacion;
	
	/** La fecha en que se libero la operacion */
	private Date fechaLiberacion;
	
	private Date fechaAplicacion;

	///** La referencia numrica que se incluy dentro del mensaje */
	//private Long referencia;
	
	/** El plazo que se incluy dentro del mensaje */
	private Long plazo; //vfu
	
	/** La referencia numrica que se incluy dentro del mensaje */
	private String referenciaNumerica;
	
	/** La referencia Operacion que se incluy dentro del mensaje */
	private String referenciaOperacion;
	
	/** El concepto es la referencia alfanumerica que se incluyó dentro del mensaje */
	private String  concepto;	
	
	private String  claveRastreo;

	private EstadoInstruccionDTO estadoInstruccion;

	/** El Origen de la Instrucción * */
	private String origen;

	/** Tipo de Retiro (SPEI/SIAC) * */
	private String tipoRetiro;

	/** La descripción del tipo de retiro */
	private String descripcionTipoRetiro;

	/**
	 * Folio Instruccion de la Operacion
	 */
	private BigInteger folioInstruccion;

	/**
	 * Folio instruccion liquidacion
	 */
	private BigInteger folioInstLiquidacion;
	
	/**
	 * Determina el origen del registro
	 * MATCH 	= TRUE
	 * EFECTIVO = FALSE
	 */
	private boolean isModuloOrigenMatch;
	
	/**
	 * Determina el origen del registro 
	 * moi         = t_internacional_efectivo
	 * liberacion  = c_retiro_efectivo_int
	 * match       = T_REGISTRO_INSTRUCCIONES_MATCH
	 * efectivo    =
	 * 
	 */
	private String moduloOrigen;
	
	private Long idCuentaBeneficiario;
	
	private String divisa;
	
	private String boveda;
	
	/** El error de liquidación del DALI */
	private ErrorDaliDTO errorDali = new ErrorDaliDTO();

	/** referencia participante*/
	private String referenciaParticipante;
	
	/** Descripcion del estado de la operacion, no del catalogo, descricion detallada */
	private String descripcionEstado;
	
	/** folio del origen del retiro 12001 son las que van desde el portal*/
	private String folioOrigen;
	
	private String clabe;
	
	private String nombreBeneficiario;
	
	private Integer liqSpei;
	
	/**
	 * nuevos campos para manejo por paquete
	 **/
	private boolean porPaquete;

	private String referenciaPaquete;

	private String totalOperacionesPaquete;

	private String numeroOperacionPaquete;

	private String totalTitulosPaquete;

	private String totalImportePaquete;

	private boolean corregir;

	private boolean seleccionadoAutoriza;
	private boolean seleccionadoLibera;
	
	public Integer getLiqSpei() {
		return liqSpei;
	}
	public void setLiqSpei(Integer liqSpei) {
		this.liqSpei = liqSpei;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getReferenciaParticipante() {
		return referenciaParticipante;
	}

	public void setReferenciaParticipante(String referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}
	
	public boolean isModuloOrigenMatch() {
		return isModuloOrigenMatch;
	}

	public void setModuloOrigenMatch(boolean moduloOrigenMatch) {
		this.isModuloOrigenMatch = moduloOrigenMatch;
	}

	/**
	 * Obtiene el valor del atributo descripcionTipoRetiro
	 * 
	 * @return el valor del atributo descripcionTipoRetiro
	 */
	public String getDescripcionTipoRetiro() {
		return descripcionTipoRetiro;
	}

	/**
	 * Establece el valor del atributo descripcionTipoRetiro
	 * 
	 * @param descripcionTipoRetiro
	 *            el valor del atributo descripcionTipoRetiro a establecer
	 */
	public void setDescripcionTipoRetiro(String descripcionTipoRetiro) {
		this.descripcionTipoRetiro = descripcionTipoRetiro;
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

	public TipoLiquidacionDTO getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	public void setTipoLiquidacion(TipoLiquidacionDTO tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}

	
	
	public TipoLiquidacionDTO getLiquidacionVencimiento() {
		return liquidacionVencimiento;
	}

	public void setLiquidacionVencimiento(TipoLiquidacionDTO liquidacionVencimiento) {
		this.liquidacionVencimiento = liquidacionVencimiento;
	}

	/**
	 * Obtiene el campo referencia
	 * 
	 * @return referencia
	 */
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	/**
	 * Asigna el campo referencia
	 * 
	 * @param referencia
	 *            el valor de referencia a asignar
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
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
	 * Obtiene el campo cuentaReceptora
	 * 
	 * @return cuentaReceptora
	 */
	public CuentaDTO getCuentaReceptora() {
		return cuentaReceptora;
	}

	/**
	 * Asigna el campo cuentaReceptora
	 * 
	 * @param cuentaReceptora
	 *            el valor de cuentaReceptora a asignar
	 */
	public void setCuentaReceptora(CuentaDTO cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
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
	 * Obtiene el campo cuentaTraspasante
	 * 
	 * @return cuentaTraspasante
	 */
	public CuentaDTO getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * Asigna el campo cuentaTraspasante
	 * 
	 * @param cuentaTraspasante
	 *            el valor de cuentaTraspasante a asignar
	 */
	public void setCuentaTraspasante(CuentaDTO cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
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
	 * Obtiene el campo monto
	 * 
	 * @return monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Asigna el campo monto
	 * 
	 * @param monto
	 *            el valor de monto a asignar
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getMontoVencimiento() {
		return montoVencimiento;
	}

	public void setMontoVencimiento(BigDecimal montoVencimiento) {
		this.montoVencimiento = montoVencimiento;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
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
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @param estadoInstruccion
	 *            the estadoInstruccion to set
	 */
	public void setEstadoInstruccion(EstadoInstruccionDTO estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * @return the estadoInstruccion
	 */
	public EstadoInstruccionDTO getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	public String getTipoRetiro() {
		return tipoRetiro;
	}

	public void setTipoRetiro(String tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}

	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}
	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	public Long getPlazo() {
		return plazo;
	}

	public void setPlazo(Long plazo) {
		this.plazo = plazo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
	}

	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	public BigInteger getIdInstruccion() {
		return idInstruccion;
	}

	public void setIdInstruccion(BigInteger idInstruccion) {
		this.idInstruccion = idInstruccion;
	}

	public String getIdInstruccionStr() {
		return idInstruccion != null ? idInstruccion.toString() : "";
	}

	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	public ErrorDaliDTO getErrorDali() {
		return errorDali;
	}

	public void setErrorDali(ErrorDaliDTO errorDali) {
		this.errorDali = errorDali;
	}

	public String getModuloOrigen() {
		return moduloOrigen;
	}

	public void setModuloOrigen(String moduloOrigen) {
		this.moduloOrigen = moduloOrigen;
	}

	public Long getIdCuentaBeneficiario() {
		return idCuentaBeneficiario;
	}

	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public String getDivisa() {
		return divisa;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	public String getBoveda() {
		return boveda;
	}

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	public String getClaveRastreo() {
		return claveRastreo;
	}
	
	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}
	
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}
	
	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
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

	public boolean isCorregir() {
		return corregir;
	}

	public void setCorregir(boolean corregir) {
		this.corregir = corregir;
	}

	public BigInteger getFolioInstLiquidacion() {
		return folioInstLiquidacion;
	}

	public void setFolioInstLiquidacion(BigInteger folioInstLiquidacion) {
		this.folioInstLiquidacion = folioInstLiquidacion;
	}
	public boolean isSeleccionadoAutoriza() {
		return seleccionadoAutoriza;
	}
	public void setSeleccionadoAutoriza(boolean seleccionadoAutoriza) {
		this.seleccionadoAutoriza = seleccionadoAutoriza;
	}
	public boolean isSeleccionadoLibera() {
		return seleccionadoLibera;
	}
	public void setSeleccionadoLibera(boolean seleccionadoLibera) {
		this.seleccionadoLibera = seleccionadoLibera;
	}
	
}
