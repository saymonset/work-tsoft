package com.indeval.portaldali.middleware.dto;

/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.Map;


/**
 * Cata&acute;logo con los campos para los retiros de efectivo nacionales
 * 
 * 
 * @author Fernando Vázquez ulloa
 * @version 1.0
 */

public class RetiroEfectivoDTO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** identificador de cuenta */
	private Long idRetiroEfectivoNal;

	/** estado de la cuenta */
	private EstadoInstruccionCatDTO estado;

	/** divisa de la cuenta */
	private DivisaDTO divisa;

	/** institucion que crea la cuenta */
	private InstitucionDTO institucion;

	/** boveda correspondiente */
	private BovedaDTO boveda;

	private Long idCuentaBeneficiario;

	/* se carga fuera del Assembler sobre demanda. */
	private String cuentaBeneficiarioFinal;

	/** importe traspaso */
	private BigDecimal importeTraspaso;

	/** concepto de pago */
	private String conceptoPago;

	/** fecha creacion */
	private Date fechaCreacion;

	/** fecha aprobacion */
	private Date fechaAutorizacion;

	/** fecha liberacion */
	private Date fechaLiberacion;

	/** fecha envio a PFI */
	private Date fechaEnvio;

	/** institucion receptora */
	private InstitucionDTO idInstReceptor;

	/** referencia */
	private String referencia;

	/** referencia */
	private String referenciaOperacion;

	/** referencia */
	private String referenciaMensaje;

	/** cuenta de retiro relacionada */
	private CuentaRetiroEfectivoDTO cuentaRetiroEfectivo;

	/** el iso firmado */
	private Map<String, Object> datosFirmas;

	/** nombre del beneficiario */
	private String nombreBeneficiario;

	/** cuenta clabe del beneficiario */
	private String cuentaBeneficiario;

	private String origen;
	private TipoInstruccionDTO tipoInstruccion;
	private String tipoOperacion;
	private String cuentaEmisor;
	private Clob datosAdicionales;
	private String usuarioCreacion;
	private String serieCreacion;
	private Clob creacionFirmada;
	private String usuarioAutorizacion;
	private String serieAutorizacion;
	private String usuarioLiberacion;
	private String serieLiberacion;
	
	public RetiroEfectivoDTO() {
		super();
	}

	public Long getIdRetiroEfectivoNal() {
		return idRetiroEfectivoNal;
	}

	public void setIdRetiroEfectivoNal(Long idRetiroEfectivoNal) {
		this.idRetiroEfectivoNal = idRetiroEfectivoNal;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	public BigDecimal getImporteTraspaso() {
		return importeTraspaso;
	}

	public void setImporteTraspaso(BigDecimal importeTraspaso) {
		this.importeTraspaso = importeTraspaso;
	}

	public String getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public EstadoInstruccionCatDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoInstruccionCatDTO estado) {
		this.estado = estado;
	}

	public InstitucionDTO getIdInstReceptor() {
		return idInstReceptor;
	}

	public void setIdInstReceptor(InstitucionDTO idInstReceptor) {
		this.idInstReceptor = idInstReceptor;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public CuentaRetiroEfectivoDTO getCuentaRetiroEfectivo() {
		return cuentaRetiroEfectivo;
	}

	public void setCuentaRetiroEfectivo(CuentaRetiroEfectivoDTO cuentaRetiroEfectivo) {
		this.cuentaRetiroEfectivo = cuentaRetiroEfectivo;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	public Long getIdCuentaBeneficiario() {
		return idCuentaBeneficiario;
	}

	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}

	public String getCuentaBeneficiarioFinal() {
		return cuentaBeneficiarioFinal;
	}

	public void setCuentaBeneficiarioFinal(String cuentaBeneficiarioFinal) {
		this.cuentaBeneficiarioFinal = cuentaBeneficiarioFinal;
	}

	public Map<String, Object> getDatosFirmas() {
		return datosFirmas;
	}

	public void setDatosFirmas(Map<String, Object> datosFirmas) {
		this.datosFirmas = datosFirmas;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public TipoInstruccionDTO getTipoInstruccion() {
		return tipoInstruccion;
	}

	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Clob getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setDatosAdicionales(Clob datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getSerieCreacion() {
		return serieCreacion;
	}

	public void setSerieCreacion(String serieCreacion) {
		this.serieCreacion = serieCreacion;
	}

	public Clob getCreacionFirmada() {
		return creacionFirmada;
	}

	public void setCreacionFirmada(Clob creacionFirmada) {
		this.creacionFirmada = creacionFirmada;
	}

	public String getCuentaEmisor() {
		return cuentaEmisor;
	}

	public void setCuentaEmisor(String cuentaEmisor) {
		this.cuentaEmisor = cuentaEmisor;
	
	}

	public String getUsuarioAutorizacion() {
		return usuarioAutorizacion;
	}

	public void setUsuarioAutorizacion(String usuarioAutorizacion) {
		this.usuarioAutorizacion = usuarioAutorizacion;
	}

	public String getSerieAutorizacion() {
		return serieAutorizacion;
	}

	public void setSerieAutorizacion(String serieAutorizacion) {
		this.serieAutorizacion = serieAutorizacion;
	}

	public String getUsuarioLiberacion() {
		return usuarioLiberacion;
	}

	public void setUsuarioLiberacion(String usuarioLiberacion) {
		this.usuarioLiberacion = usuarioLiberacion;
	}

	public String getSerieLiberacion() {
		return serieLiberacion;
	}

	public void setSerieLiberacion(String serieLiberacion) {
		this.serieLiberacion = serieLiberacion;
	}
}
