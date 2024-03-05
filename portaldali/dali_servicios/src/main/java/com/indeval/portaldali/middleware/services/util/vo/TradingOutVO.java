/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Objeto con los valores de entrada al trading de MD
 * 
 * @author Fco. Agustin Calderon
 */
public class TradingOutVO implements Serializable {
	/**
	 * N&uacute;mero de serie de la clase
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID o Clave de Instituci&oacute;n Compradora
	 */
	private String idInstitucionCompradora;

	/**
	 * ID o Clave de Instituci&oacute;n Vendedora
	 */
	private String idInstitucionVendedora;

	/**
	 * Folio de la Instituci&oacute;n Compradora
	 */
	private String folioInstitucionCompradora;

	/**
	 * matchRequeridoId
	 */
	private String matchRequeridoId;

	/**
	 * Folio de la Intituci&oacute;n Vendedora
	 */
	private String folioInstitucionVendedora;

	/**
	 * Dias plazo
	 */
	private String diasPlazo;

	/**
	 * Tipo de operaci&oacute;n
	 */
	private String tipoOperacion;

	/**
	 * Tipo valor
	 */
	private String tipoValor;

	/**
	 * Emisora
	 */
	private String emisora;

	/**
	 * Serie
	 */
	private String serie;

	/**
	 * Cup&oacute;n
	 */
	private String cupon;

	/**
	 * Fecha Reporto
	 */
	private Date fechaReporto;

	/**
	 * Precio T&iacute;tulo
	 */
	private BigDecimal precioTitulo;

	/**
	 * Tasa Negociada
	 */
	private BigDecimal tasaNegociada;

	/**
	 * Fecha de Liquidaci&oacute;n
	 */
	private Date fechaLiquidacion;

	/**
	 * Folio Usuario, par&aacute;metro enviado por el participante como folio
	 * identificador
	 */
	private String folioUsuario;

	/**
	 * Tasa Referencia
	 */
	private BigDecimal tasaReferencia;

	/**
	 * Cantidad T&iacute;tulos Operar
	 */
	private BigInteger cantidadTitulosOp;

	/**
	 * Cuenta del Comprador
	 */
	private String cuentaComprador;

	/**
	 * Cuenta Vendedora
	 */
	private String cuentaVendedora;

	/**
	 * Origen de la Transacci&oacute;n
	 */
	private String origenTransac;

	/**
	 * Importe
	 */
	private BigDecimal importe;

	// /**
	// * ID del Banco de Trabajo
	// */
	// private String idBancoTrabajo;
	// /**
	// * Folio del Banco de Trabajo
	// */
	// private String folioBancoTrabajo;
	// /**
	// * Cuenta de Banco de Trabajo
	// */
	// private String cuentaBancoTrabajo;
	/**
	 * tipo de mensaje 15022 que origina la instrucci&oacute;n
	 */
	private String tipoMensaje;

	/**
	 * fecha en la que la operaci&oacute;n es concertada
	 */
	private String fechaConcertacion;

	/**
	 * folio del cargo
	 */
	private String idFolioCargo;

	/**
	 * status de la operaci&oacute;n
	 */
	private String status;

	/**
	 * folio identificador
	 */
	private String folioControl;

	/**
	 * fecha y hora de recepci&oacute;n
	 */
	private String fechaHoraRegistro;

	/**
	 * tipo de la divisa
	 */
	private String divisa;

	/**
	 * folio del usuario traspasante 540, 541
	 */
	private String folioUsuarioTraspasante;

	/**
	 * folio del usuario receptor 542, 543
	 */
	private String folioUsuarioReceptor;

	/**
	 * id de la institucion remitente
	 */
	private String idInstitucionRemitente;

	/**
	 * folio de la institucion remitente
	 */
	private String folioInstitucionRemitente;

	// private String isin;
	//
	// private String boveda;
	//
	// private String bic_comprador;
	//
	// private String bic_vendedor;
	//
	// private String bic_bancoTrabajo;
	//
	// private String funcionDelMensaje;
	//
	// private String referenciaPreviaCancelacion;
	//
	// private String casfim_Comprador;
	//
	// private String casfim_Vendedor;
	//
	// private String casfim_bancoTrabajo;
	//
	// private String mensaje;

	/**
	 * estado, para operaciones de MC
	 * 
	 * @deprecated
	 */
	private String estado;

	/**
	 * referencia Participante que origina la instrucci&oacute;n
	 * 
	 * @deprecated
	 */
	private String referenciaParticipante;

	// public String getBic_bancoTrabajo() {
	// return bic_bancoTrabajo;
	// }
	// public void setBic_bancoTrabajo(String bic_bancoTrabajo) {
	// this.bic_bancoTrabajo = bic_bancoTrabajo;
	// }
	// public String getBic_comprador() {
	// return bic_comprador;
	// }
	// public void setBic_comprador(String bic_comprador) {
	// this.bic_comprador = bic_comprador;
	// }
	// public String getBic_vendedor() {
	// return bic_vendedor;
	// }
	// public void setBic_vendedor(String bic_vendedor) {
	// this.bic_vendedor = bic_vendedor;
	// }
	// public String getBoveda() {
	// return boveda;
	// }
	// public void setBoveda(String boveda) {
	// this.boveda = boveda;
	// }
	// public String getCasfim_bancoTrabajo() {
	// return casfim_bancoTrabajo;
	// }
	// public void setCasfim_bancoTrabajo(String casfim_bancoTrabajo) {
	// this.casfim_bancoTrabajo = casfim_bancoTrabajo;
	// }
	// public String getCasfim_Comprador() {
	// return casfim_Comprador;
	// }
	// public void setCasfim_Comprador(String casfim_Comprador) {
	// this.casfim_Comprador = casfim_Comprador;
	// }
	// public String getCasfim_Vendedor() {
	// return casfim_Vendedor;
	// }
	// public void setCasfim_Vendedor(String casfim_Vendedor) {
	// this.casfim_Vendedor = casfim_Vendedor;
	// }
	// public String getFuncionDelMensaje() {
	// return funcionDelMensaje;
	// }
	// public void setFuncionDelMensaje(String funcionDelMensaje) {
	// this.funcionDelMensaje = funcionDelMensaje;
	// }
	// public String getIsin() {
	// return isin;
	// }
	// public void setIsin(String isin) {
	// this.isin = isin;
	// }
	// public String getReferenciaPreviaCancelacion() {
	// return referenciaPreviaCancelacion;
	// }
	// public void setReferenciaPreviaCancelacion(String
	// referenciaPreviaCancelacion) {
	// this.referenciaPreviaCancelacion = referenciaPreviaCancelacion;
	// }

	/**
	 * @return Returns the cantidadTitulosOp.
	 */
	public BigInteger getCantidadTitulosOp() {
		return cantidadTitulosOp;
	}

	/**
	 * @param cantidadTitulosOp
	 *            The cantidadTitulosOp to set.
	 */
	public void setCantidadTitulosOp(BigInteger cantidadTitulosOp) {
		this.cantidadTitulosOp = cantidadTitulosOp;
	}

	/**
	 * @return Returns the cuentaComprador.
	 */
	public String getCuentaComprador() {
		return cuentaComprador;
	}

	/**
	 * @param cuentaComprador
	 *            The cuentaComprador to set.
	 */
	public void setCuentaComprador(String cuentaComprador) {
		this.cuentaComprador = cuentaComprador;
	}

	/**
	 * @return Returns the cuentaVendedora.
	 */
	public String getCuentaVendedora() {
		return cuentaVendedora;
	}

	/**
	 * @param cuentaVendedora
	 *            The cuentaVendedora to set.
	 */
	public void setCuentaVendedora(String cuentaVendedora) {
		this.cuentaVendedora = cuentaVendedora;
	}

	/**
	 * @return Returns the cupon.
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            The cupon to set.
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return Returns the diasPlazo.
	 */
	public String getDiasPlazo() {
		return diasPlazo;
	}

	/**
	 * @param diasPlazo
	 *            The diasPlazo to set.
	 */
	public void setDiasPlazo(String diasPlazo) {
		this.diasPlazo = diasPlazo;
	}

	/**
	 * @return Returns the emisora.
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            The emisora to set.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return Returns the fechaLiquidacion.
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 *            The fechaLiquidacion to set.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return Returns the fechaReporto.
	 */
	public Date getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * @param fechaReporto
	 *            The fechaReporto to set.
	 */
	public void setFechaReporto(Date fechaReporto) {
		this.fechaReporto = fechaReporto;
	}

	/**
	 * @return Returns the folioInstitucionCompradora.
	 */
	public String getFolioInstitucionCompradora() {
		return folioInstitucionCompradora;
	}

	/**
	 * @param folioInstitucionCompradora
	 *            The folioInstitucionCompradora to set.
	 */
	public void setFolioInstitucionCompradora(String folioInstitucionCompradora) {
		this.folioInstitucionCompradora = folioInstitucionCompradora;
	}

	/**
	 * @return Returns the folioInstitucionVendedora.
	 */
	public String getFolioInstitucionVendedora() {
		return folioInstitucionVendedora;
	}

	/**
	 * @param folioInstitucionVendedora
	 *            The folioInstitucionVendedora to set.
	 */
	public void setFolioInstitucionVendedora(String folioInstitucionVendedora) {
		this.folioInstitucionVendedora = folioInstitucionVendedora;
	}

	/**
	 * @return Returns the folioUsuario.
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}

	/**
	 * @param folioUsuario
	 *            The folioUsuario to set.
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	/**
	 * @return Returns the idInstitucionCompradora.
	 */
	public String getIdInstitucionCompradora() {
		return idInstitucionCompradora;
	}

	/**
	 * @param idInstitucionCompradora
	 *            The idInstitucionCompradora to set.
	 */
	public void setIdInstitucionCompradora(String idInstitucionCompradora) {
		this.idInstitucionCompradora = idInstitucionCompradora;
	}

	/**
	 * @return Returns the idInstitucionVendedora.
	 */
	public String getIdInstitucionVendedora() {
		return idInstitucionVendedora;
	}

	/**
	 * @param idInstitucionVendedora
	 *            The idInstitucionVendedora to set.
	 */
	public void setIdInstitucionVendedora(String idInstitucionVendedora) {
		this.idInstitucionVendedora = idInstitucionVendedora;
	}

	/**
	 * @return Returns the precioTitulo.
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 *            The precioTitulo to set.
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return Returns the serie.
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            The serie to set.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return Returns the tasaNegociada.
	 */
	public BigDecimal getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * @param tasaNegociada
	 *            The tasaNegociada to set.
	 */
	public void setTasaNegociada(BigDecimal tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * @return Returns the tasaReferencia.
	 */
	public BigDecimal getTasaReferencia() {
		return tasaReferencia;
	}

	/**
	 * @param tasaReferencia
	 *            The tasaReferencia to set.
	 */
	public void setTasaReferencia(BigDecimal tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * @return Returns the tipoOperacion.
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            The tipoOperacion to set.
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return Returns the tipoValor.
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor
	 *            The tipoValor to set.
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	// /**
	// * @return Returns the cuentaBancoTrabajo.
	// */
	// public String getCuentaBancoTrabajo() {
	// return cuentaBancoTrabajo;
	// }
	// /**
	// * @param cuentaBancoTrabajo The cuentaBancoTrabajo to set.
	// */
	// public void setCuentaBancoTrabajo(String cuentaBancoTrabajo) {
	// this.cuentaBancoTrabajo = cuentaBancoTrabajo;
	// }
	// /**
	// * @return Returns the folioBancoTrabajo.
	// */
	// public String getFolioBancoTrabajo() {
	// return folioBancoTrabajo;
	// }
	// /**
	// * @param folioBancoTrabajo The folioBancoTrabajo to set.
	// */
	// public void setFolioBancoTrabajo(String folioBancoTrabajo) {
	// this.folioBancoTrabajo = folioBancoTrabajo;
	// }
	// /**
	// * @return Returns the idBancoTrabajo.
	// */
	// public String getIdBancoTrabajo() {
	// return idBancoTrabajo;
	// }
	// /**
	// * @param idBancoTrabajo The idBancoTrabajo to set.
	// */
	// public void setIdBancoTrabajo(String idBancoTrabajo) {
	// this.idBancoTrabajo = idBancoTrabajo;
	// }
	/**
	 * @return Returns the importe.
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            The importe to set.
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return Returns the origenTransac.
	 */
	public String getOrigenTransac() {
		return origenTransac;
	}

	/**
	 * @param origenTransac
	 *            The origenTransac to set.
	 */
	public void setOrigenTransac(String origenTransac) {
		this.origenTransac = origenTransac;
	}

	/**
	 * @return the folioInstitucionRemitente
	 */
	public String getFolioInstitucionRemitente() {
		return folioInstitucionRemitente;
	}

	/**
	 * @param folioInstitucionRemitente
	 *            the folioInstitucionRemitente to set
	 */
	public void setFolioInstitucionRemitente(String folioInstitucionRemitente) {
		this.folioInstitucionRemitente = folioInstitucionRemitente;
	}

	/**
	 * @return the idInstitucionRemitente
	 */
	public String getIdInstitucionRemitente() {
		return idInstitucionRemitente;
	}

	/**
	 * @param idInstitucionRemitente
	 *            the idInstitucionRemitente to set
	 */
	public void setIdInstitucionRemitente(String idInstitucionRemitente) {
		this.idInstitucionRemitente = idInstitucionRemitente;
	}

	/**
	 * @return Returns the tipoMensaje.
	 */
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje
	 *            The tipoMensaje to set.
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return Returns the matchRequeridoId.
	 */
	public String getMatchRequeridoId() {
		return matchRequeridoId;
	}

	/**
	 * @param matchRequeridoId
	 *            The matchRequeridoId to set.
	 */
	public void setMatchRequeridoId(String matchRequeridoId) {
		this.matchRequeridoId = matchRequeridoId;
	}

	/**
	 * @return String
	 */
	public String getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(String fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * @return String
	 */
	public String getIdFolioCargo() {
		return idFolioCargo;
	}

	/**
	 * @param idFolioCargo
	 */
	public void setIdFolioCargo(String idFolioCargo) {
		this.idFolioCargo = idFolioCargo;
	}

	/**
	 * @return String
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String
	 */
	public String getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}

	/**
	 * @param fechaHoraRegistro
	 */
	public void setFechaHoraRegistro(String fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	/**
	 * @return String
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return String
	 */
	public String getFolioUsuarioReceptor() {
		return folioUsuarioReceptor;
	}

	/**
	 * @return String
	 */
	public String getFolioUsuarioTraspasante() {
		return folioUsuarioTraspasante;
	}

	/**
	 * @param folioUsuarioReceptor
	 */
	public void setFolioUsuarioReceptor(String folioUsuarioReceptor) {
		this.folioUsuarioReceptor = folioUsuarioReceptor;
	}

	/**
	 * @param folioUsuarioTraspasante
	 */
	public void setFolioUsuarioTraspasante(String folioUsuarioTraspasante) {
		this.folioUsuarioTraspasante = folioUsuarioTraspasante;
	}

	// public String getMensaje() {
	// return mensaje;
	// }
	//
	// public void setMensaje(String mensaje) {
	// this.mensaje = mensaje;
	// }

	/**
	 * @return Returns the estado.
	 * @deprecated
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 * @deprecated
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return String
	 * @deprecated
	 */
	public String getReferenciaParticipante() {
		return referenciaParticipante;
	}

	/**
	 * @param referenciaParticipante
	 * @deprecated
	 */
	public void setReferenciaParticipante(String referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}
}