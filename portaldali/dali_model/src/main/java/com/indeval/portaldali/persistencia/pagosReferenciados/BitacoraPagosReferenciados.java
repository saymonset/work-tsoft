/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistencia.pagosReferenciados;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Pablo Balderas
 *
 */
@Entity
@Table(name = "T_BITACORA_PAGO_REFERENCIADO")
@SequenceGenerator(name = "secuencia", sequenceName = "SEQ_T_BITACORA_PAGO_REFER", allocationSize = 1, initialValue = 1)
public class BitacoraPagosReferenciados implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = 6547515236652817115L;

	/** Id de la bitacora */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @Column(name = "ID_BITACORA_PAGO_REFERENCIADO")
	private Long idBitacoraPagoReferenciado;
	
	/** Traspasante */
    @Column(name = "TRASPASANTE", updatable = false)
	private String traspasante;
	
	/** Receptor */
    @Column(name = "RECEPTOR", updatable = false)
	private String receptor;
	
	/** Folio preliquidador */
    @Column(name = "FOLIO_PRELIQUIDADOR", updatable = false)
	private String folioPreliquidador;
	
	/** Folio Instruccion */
    @Column(name = "FOLIO_INSTRUCCION", updatable = false)
	private String folioInstruccion;
	
	/** Importe */
    @Column(name = "IMPORTE", updatable = false)
	private BigDecimal importe;
	
	/** Boveda */
    @Column(name = "BOVEDA", updatable = false)
	private String boveda;
	
	/** Divisa */
    @Column(name = "DIVISA", updatable = false)
	private String divisa;
	
	/**Concepto  */
    @Column(name = "CONCEPTO", updatable = false)
	private String concepto;
	
	/** Clave rastreo */
    @Column(name = "CLAVE_RASTREO", updatable = false)
	private String claveRastreo;
	
	/** Referencia numerica */
    @Column(name = "REFERENCIA_NUMERICA", updatable = false)
	private String referenciaNumerica;
	
	/** Fecha de registro */
    @Column(name = "FECHA_REGISTRO", updatable = false)
	private Date fechaRegistro;
	
	/** Estatus */
    @Column(name = "ESTATUS", updatable = false)
	private String estatus;
	
	/** Monto a pagar */
    @Column(name = "PAGO", updatable = false)
	private BigDecimal pago;
	
	/** Diferencia entre el monto y el monto a pagar */
    @Column(name = "REMANENTE", updatable = false)
	private BigDecimal remanente;

	/**
	 * Método para obtener el atributo idBitacoraPagoReferenciado
	 * @return El atributo idBitacoraPagoReferenciado
	 */
	public Long getIdBitacoraPagoReferenciado() {
		return idBitacoraPagoReferenciado;
	}

	/**
	 * Método para establecer el atributo idBitacoraPagoReferenciado
	 * @param idBitacoraPagoReferenciado El valor del atributo idBitacoraPagoReferenciado a establecer.
	 */
	public void setIdBitacoraPagoReferenciado(Long idBitacoraPagoReferenciado) {
		this.idBitacoraPagoReferenciado = idBitacoraPagoReferenciado;
	}

	/**
	 * Método para obtener el atributo traspasante
	 * @return El atributo traspasante
	 */
	public String getTraspasante() {
		return traspasante;
	}

	/**
	 * Método para establecer el atributo traspasante
	 * @param traspasante El valor del atributo traspasante a establecer.
	 */
	public void setTraspasante(String traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * Método para obtener el atributo receptor
	 * @return El atributo receptor
	 */
	public String getReceptor() {
		return receptor;
	}

	/**
	 * Método para establecer el atributo receptor
	 * @param receptor El valor del atributo receptor a establecer.
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	/**
	 * Método para obtener el atributo folioPreliquidador
	 * @return El atributo folioPreliquidador
	 */
	public String getFolioPreliquidador() {
		return folioPreliquidador;
	}

	/**
	 * Método para establecer el atributo folioPreliquidador
	 * @param folioPreliquidador El valor del atributo folioPreliquidador a establecer.
	 */
	public void setFolioPreliquidador(String folioPreliquidador) {
		this.folioPreliquidador = folioPreliquidador;
	}

	/**
	 * Método para obtener el atributo folioInstruccion
	 * @return El atributo folioInstruccion
	 */
	public String getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Método para establecer el atributo folioInstruccion
	 * @param folioInstruccion El valor del atributo folioInstruccion a establecer.
	 */
	public void setFolioInstruccion(String folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Método para obtener el atributo importe
	 * @return El atributo importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * Método para establecer el atributo importe
	 * @param importe El valor del atributo importe a establecer.
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * Método para obtener el atributo boveda
	 * @return El atributo boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * Método para establecer el atributo boveda
	 * @param boveda El valor del atributo boveda a establecer.
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * Método para obtener el atributo divisa
	 * @return El atributo divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * Método para establecer el atributo divisa
	 * @param divisa El valor del atributo divisa a establecer.
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * Método para obtener el atributo concepto
	 * @return El atributo concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * Método para establecer el atributo concepto
	 * @param concepto El valor del atributo concepto a establecer.
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * Método para obtener el atributo claveRastreo
	 * @return El atributo claveRastreo
	 */
	public String getClaveRastreo() {
		return claveRastreo;
	}

	/**
	 * Método para establecer el atributo claveRastreo
	 * @param claveRastreo El valor del atributo claveRastreo a establecer.
	 */
	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	/**
	 * Método para obtener el atributo referenciaNumerica
	 * @return El atributo referenciaNumerica
	 */
	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}

	/**
	 * Método para establecer el atributo referenciaNumerica
	 * @param referenciaNumerica El valor del atributo referenciaNumerica a establecer.
	 */
	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	/**
	 * Método para obtener el atributo fechaRegistro
	 * @return El atributo fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Método para establecer el atributo fechaRegistro
	 * @param fechaRegistro El valor del atributo fechaRegistro a establecer.
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Método para obtener el atributo estatus
	 * @return El atributo estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * Método para establecer el atributo estatus
	 * @param estatus El valor del atributo estatus a establecer.
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * Método para obtener el atributo pago
	 * @return El atributo pago
	 */
	public BigDecimal getPago() {
		return pago;
	}

	/**
	 * Método para establecer el atributo pago
	 * @param pago El valor del atributo pago a establecer.
	 */
	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	/**
	 * Método para obtener el atributo remanente
	 * @return El atributo remanente
	 */
	public BigDecimal getRemanente() {
		return remanente;
	}

	/**
	 * Método para establecer el atributo remanente
	 * @param remanente El valor del atributo remanente a establecer.
	 */
	public void setRemanente(BigDecimal remanente) {
		this.remanente = remanente;
	}
    
}
