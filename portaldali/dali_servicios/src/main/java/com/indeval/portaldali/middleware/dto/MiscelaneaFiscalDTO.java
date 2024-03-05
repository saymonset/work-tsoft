/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 25, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * DTO para almacenar los datos de una operación Miscelanea Fiscal
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@XStreamAlias("mensajeValores")
public class MiscelaneaFiscalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * El identificador de este registro en la BD.
	 */

	private Long idMiscelaneaFiscal;

	/**
	 * El identificador de la operación a la cual está ligada esta
	 * información
	 */
	private Long idInstruccionOperacionVal;

	/**
	 * El curp ó rfc de la operación.
	 */
	@XStreamAlias("rfcCurp")
	private String curpRfc;

	/**
	 * El cliente de la operación.
	 */
	@XStreamAlias("cliente")
	private String cliente;

	/**
	 * El precio de la adquisición de la operación.
	 */
	@XStreamAlias("precioAdquisicion")
	private Double precioAdquisicion;

	/**
	 * La fecha de adquisición de la operación.
	 */
	@XStreamAlias("fechaAdquisicion")
	private Date fechaAdquisicion;

	/**
	 * La fecha de liquidación de la operación.
	 */
	@XStreamAlias("fechaLiquidacion")
	private Date fechaLiquidacion;

	@XStreamAlias("referenciaParticipante")
	private String referenciaParticipante;

	@XStreamAlias("idInstitucionTraspasante")
	private BigInteger idInstitucionTraspasante;

	@XStreamAlias("folioInstitucionTraspasante")
	private String folioInstitucionTraspasante;

	@XStreamAlias("cuentaTraspasante")
	private String cuentaTraspasante;

	@XStreamAlias("folioInstitucionReceptor")
	private String folioInstitucionReceptor;

	@XStreamAlias("cuentaReceptor")
	private String cuentaReceptor;

	@XStreamAlias("tipoOperacion")
	private String tipoOperacion;

	@XStreamAlias("tipoValor")
	private String tipoValor;

	@XStreamAlias("emisora")
	private String emisora;

	@XStreamAlias("serie")
	private String serie;

	@XStreamAlias("cupon")
	private String cupon;

	@XStreamAlias("importe")
	private Double importe;

	@XStreamAlias("diasPlazo")
	private String diasPlazo;

	@XStreamAlias("tasaReferencia")
	private String tasaReferencia;

	@XStreamAlias("numeroTitulos")
	private String numeroTitulos;

	@XStreamAlias("tipoMensaje")
	private String tipoMensaje;

	@XStreamAlias("funcionDelMensaje")
	private String funcionDelMensaje;

	@XStreamAlias("fechaHoraRegistro")
	private String fechaHoraRegistro;

	@XStreamAlias("origenTransac")
	private String origenTransac;

	@XStreamAlias("matchRequeridoId")
	private String matchRequeridoId;

	@XStreamAlias("mensaje")
	private String mensaje;

	@XStreamAlias("folioControl")
	private String folioControl;

	@XStreamAlias("idFolioCredencial")
	private String idFolioCredencial;

	@XStreamAlias("idInstitucionReceptor")
	private String idInstitucionReceptor;

	@XStreamAlias("referenciaOperacion")
	private String referenciaOperacion;

	@XStreamAlias("bovedaValores")
	private String boveda;

	@XStreamAlias("bovedaEfectivo")
	private String bovedaEfectivo;
	
	@XStreamAlias("fechaHoraCierreOper")
	private String fechaHoraCierreOper;
	
	@XStreamAlias("fechaHoraEncolamiento")
	private String fechaHoraEncolamiento;
	
	@XStreamAlias("fechaActualizacion")
	private String fechaActualizacion;
	
	@XStreamAlias("isin")
	private String isin;
	
	/**
	 * El precio de la adquisición de la operación.
	 */
	@XStreamAlias("costoPromActualizado")
	private Double costoPromedio;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Obtiene el valor del atributo idMiscelaneaFiscal
	 * 
	 * @return el valor del atributo idMiscelaneaFiscal
	 */
	public Long getIdMiscelaneaFiscal() {
		return idMiscelaneaFiscal;
	}

	/**
	 * Establece el valor del atributo idMiscelaneaFiscal
	 * 
	 * @param idMiscelaneaFiscal
	 *            el valor del atributo idMiscelaneaFiscal a establecer
	 */
	public void setIdMiscelaneaFiscal(Long idMiscelaneaFiscal) {
		this.idMiscelaneaFiscal = idMiscelaneaFiscal;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionOperacionVal
	 * 
	 * @return el valor del atributo idInstruccionOperacionVal
	 */
	public Long getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo idInstruccionOperacionVal
	 * 
	 * @param idInstruccionOperacionVal
	 *            el valor del atributo idInstruccionOperacionVal a establecer
	 */
	public void setIdInstruccionOperacionVal(Long idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo curpRfc
	 * 
	 * @return el valor del atributo curpRfc
	 */
	public String getCurpRfc() {
		return curpRfc;
	}

	/**
	 * Establece el valor del atributo curpRfc
	 * 
	 * @param curpRfc
	 *            el valor del atributo curpRfc a establecer
	 */
	public void setCurpRfc(String curpRfc) {
		this.curpRfc = curpRfc;
	}

	/**
	 * Obtiene el valor del atributo cliente
	 * 
	 * @return el valor del atributo cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * Establece el valor del atributo cliente
	 * 
	 * @param cliente
	 *            el valor del atributo cliente a establecer
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * Obtiene el valor del atributo precioAdquisicion
	 * 
	 * @return el valor del atributo precioAdquisicion
	 */
	public Double getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * Establece el valor del atributo precioAdquisicion
	 * 
	 * @param precioAdquisicion
	 *            el valor del atributo precioAdquisicion a establecer
	 */
	public void setPrecioAdquisicion(Double precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}

	/**
	 * Obtiene el valor del atributo fechaAdquisicion
	 * 
	 * @return el valor del atributo fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * Establece el valor del atributo fechaAdquisicion
	 * 
	 * @param fechaAdquisicion
	 *            el valor del atributo fechaAdquisicion a establecer
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	/**
	 * Obtiene el valor del atributo fechaLiquidacion
	 * 
	 * @return el valor del atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece el valor del atributo fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            el valor del atributo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the referenciaParticipante
	 */
	public String getReferenciaParticipante() {
		return referenciaParticipante;
	}

	/**
	 * @param referenciaParticipante
	 *            the referenciaParticipante to set
	 */
	public void setReferenciaParticipante(String referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}

	/**
	 * @return the idInstitucionTraspasante
	 */
	public BigInteger getIdInstitucionTraspasante() {
		return idInstitucionTraspasante;
	}

	/**
	 * @param idInstitucionTraspasante
	 *            the idInstitucionTraspasante to set
	 */
	public void setIdInstitucionTraspasante(BigInteger idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}

	/**
	 * @return the folioInstitucionTraspasante
	 */
	public String getFolioInstitucionTraspasante() {
		return folioInstitucionTraspasante;
	}

	/**
	 * @param folioInstitucionTraspasante
	 *            the folioInstitucionTraspasante to set
	 */
	public void setFolioInstitucionTraspasante(
			String folioInstitucionTraspasante) {
		this.folioInstitucionTraspasante = folioInstitucionTraspasante;
	}

	/**
	 * @return the cuentaTraspasante
	 */
	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * @param cuentaTraspasante
	 *            the cuentaTraspasante to set
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * @return the folioInstitucionReceptor
	 */
	public String getFolioInstitucionReceptor() {
		return folioInstitucionReceptor;
	}

	/**
	 * @param folioInstitucionReceptor
	 *            the folioInstitucionReceptor to set
	 */
	public void setFolioInstitucionReceptor(String folioInstitucionReceptor) {
		this.folioInstitucionReceptor = folioInstitucionReceptor;
	}

	/**
	 * @return the cuentaReceptor
	 */
	public String getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * @param cuentaReceptor
	 *            the cuentaReceptor to set
	 */
	public void setCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor
	 *            the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * @return the diasPlazo
	 */
	public String getDiasPlazo() {
		return diasPlazo;
	}

	/**
	 * @param diasPlazo
	 *            the diasPlazo to set
	 */
	public void setDiasPlazo(String diasPlazo) {
		this.diasPlazo = diasPlazo;
	}

	/**
	 * @return the tasaReferencia
	 */
	public String getTasaReferencia() {
		return tasaReferencia;
	}

	/**
	 * @param tasaReferencia
	 *            the tasaReferencia to set
	 */
	public void setTasaReferencia(String tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * @return the numeroTitulos
	 */
	public String getNumeroTitulos() {
		return numeroTitulos;
	}

	/**
	 * @param numeroTitulos
	 *            the numeroTitulos to set
	 */
	public void setNumeroTitulos(String numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	/**
	 * @return the tipoMensaje
	 */
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje
	 *            the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return the funcionDelMensaje
	 */
	public String getFuncionDelMensaje() {
		return funcionDelMensaje;
	}

	/**
	 * @param funcionDelMensaje
	 *            the funcionDelMensaje to set
	 */
	public void setFuncionDelMensaje(String funcionDelMensaje) {
		this.funcionDelMensaje = funcionDelMensaje;
	}

	/**
	 * @return the fechaHoraRegistro
	 */
	public String getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}

	/**
	 * @param fechaHoraRegistro
	 *            the fechaHoraRegistro to set
	 */
	public void setFechaHoraRegistro(String fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	/**
	 * @return the origenTransac
	 */
	public String getOrigenTransac() {
		return origenTransac;
	}

	/**
	 * @param origenTransac
	 *            the origenTransac to set
	 */
	public void setOrigenTransac(String origenTransac) {
		this.origenTransac = origenTransac;
	}

	/**
	 * @return the matchRequeridoId
	 */
	public String getMatchRequeridoId() {
		return matchRequeridoId;
	}

	/**
	 * @param matchRequeridoId
	 *            the matchRequeridoId to set
	 */
	public void setMatchRequeridoId(String matchRequeridoId) {
		this.matchRequeridoId = matchRequeridoId;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 *            the folioControl to set
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the idFolioCredencial
	 */
	public String getIdFolioCredencial() {
		return idFolioCredencial;
	}

	/**
	 * @param idFolioCredencial
	 *            the idFolioCredencial to set
	 */
	public void setIdFolioCredencial(String idFolioCredencial) {
		this.idFolioCredencial = idFolioCredencial;
	}

	/**
	 * @return the idInstitucionReceptor
	 */
	public String getIdInstitucionReceptor() {
		return idInstitucionReceptor;
	}

	/**
	 * @param idInstitucionReceptor
	 *            the idInstitucionReceptor to set
	 */
	public void setIdInstitucionReceptor(String idInstitucionReceptor) {
		this.idInstitucionReceptor = idInstitucionReceptor;
	}

	/**
	 * @return the referenciaOperacion
	 */
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	/**
	 * @param referenciaOperacion
	 *            the referenciaOperacion to set
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	/**
	 * @return the boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the bovedaEfectivo
	 */
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @param bovedaEfectivo
	 *            the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
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

	public String getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(String fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	public String getFechaHoraEncolamiento() {
		return fechaHoraEncolamiento;
	}

	public void setFechaHoraEncolamiento(String fechaHoraEncolamiento) {
		this.fechaHoraEncolamiento = fechaHoraEncolamiento;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
