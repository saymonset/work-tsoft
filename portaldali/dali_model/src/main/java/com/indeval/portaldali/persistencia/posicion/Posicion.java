/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistencia.posicion;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Clase que representa la vista para consultar la posicion nombrada.
 * 
 * @author Pablo Balderas
 */
public class Posicion implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -27260264040968577L;

	/** Id de la posicion */
	private Long idPosicion;
	
	/** Id de la posicion historica */
	private Long idPosicionHistorica;
	
	/** Fecha de la posicion historica */
	private Date fecha;
		
	/** Id de la cuenta, nombrada o controlada */
	private Long idCuenta;

	/** Id de la boveda */
	private Long idBoveda;
	
	/** Ide del cupon */
	private Long idCupon;

	/** Id de la emision */
	private Long idEmision;

	/** Id de la institucion ligada a la cuenta */
	private Long idInstitucion;
	
	/** Clave tipo de institucion */
	private String claveTipoInstitucion;
	
	/** Folio institucion */
	private String folioInstitucion;
	
	/** Cuenta nombrada o controlada */
	private String cuenta;
	
	/** Id del tipo de cuenta */
	private Long idTipoCuenta;
	
	/** Tipo de custodia */ 
	private String tipoCustodia;
	
	/** Naturaleza contable */
	private String naturalezaContable;
	
	/** Naturaleza del proceso de liquidacion */
	private String naturalezaProcLiq;
	
	/** Boveda */
	private String boveda;
	
	/** TV */
	private String tv;
	
	/** Emisora */
	private String emisora;
	
	/** Serie */
	private String serie;
	
	/** Cupon */
	private String cupon;
	
	/** Isin */
	private String isin;
	
	/** Cfi */
	private String cfi;
	
	/** Id mercado */
	private Long idMercado;
	
	/** Clave mercado */
	private String claveMercado;
	
	/** Posicion disponible */
	private Long posicionDisponible;
	
	/** Posicion no disponible */
	private Long posicionNoDisponible;
	
	/** Posicion */
	private Long posicion;	
	
	/** Valor nominal */
	private Double valorNominal;
	
	/** Valuacion */
	private Double valuacion;

	/** Cadena para ordenamiento */
	private String ordenCuenta;
	
	/** Cadena para ordenamiento */
	private String ordenDefault;
	
	/** Campo para realizar traspasos de valores en bloque */
	private Long posicionFondeo = 0L;
	
	/** Cadena con el id/folio de la institución y la cuenta */
	private String idFolioCuenta;

	/**
	 * Método para obtener el atributo idPosicion
	 * @return El atributo idPosicion
	 */
	public Long getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Método para establecer el atributo idPosicion
	 * @param idPosicion El valor del atributo idPosicion a establecer.
	 */
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Método para obtener el atributo idPosicionHistorica
	 * @return El atributo idPosicionHistorica
	 */
	public Long getIdPosicionHistorica() {
		return idPosicionHistorica;
	}

	/**
	 * Método para establecer el atributo idPosicionHistorica
	 * @param idPosicionHistorica El valor del atributo idPosicionHistorica a establecer.
	 */
	public void setIdPosicionHistorica(Long idPosicionHistorica) {
		this.idPosicionHistorica = idPosicionHistorica;
	}

	/**
	 * Método para obtener el atributo fecha
	 * @return El atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer el atributo fecha
	 * @param fecha El valor del atributo fecha a establecer.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método para obtener el atributo idCuenta
	 * @return El atributo idCuenta
	 */
	public Long getIdCuenta() {
		return idCuenta;
	}

	/**
	 * Método para establecer el atributo idCuenta
	 * @param idCuenta El valor del atributo idCuenta a establecer.
	 */
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	/**
	 * Método para obtener el atributo idBoveda
	 * @return El atributo idBoveda
	 */
	public Long getIdBoveda() {
		return idBoveda;
	}

	/**
	 * Método para establecer el atributo idBoveda
	 * @param idBoveda El valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * Método para obtener el atributo idCupon
	 * @return El atributo idCupon
	 */
	public Long getIdCupon() {
		return idCupon;
	}

	/**
	 * Método para establecer el atributo idCupon
	 * @param idCupon El valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(Long idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * Método para obtener el atributo idEmision
	 * @return El atributo idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * Método para establecer el atributo idEmision
	 * @param idEmision El valor del atributo idEmision a establecer.
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * Método para obtener el atributo idInstitucion
	 * @return El atributo idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Método para establecer el atributo idInstitucion
	 * @param idInstitucion El valor del atributo idInstitucion a establecer.
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Método para obtener el atributo institucion
	 * @return El atributo institucion
	 */
	public String getInstitucion() {
		String institucion = null;
		if(StringUtils.isNotBlank(claveTipoInstitucion) && StringUtils.isNotBlank(folioInstitucion)) {
			institucion = claveTipoInstitucion + folioInstitucion;
		}
		return institucion;
	}

	/**
	 * Método para obtener el atributo cuenta
	 * @return El atributo cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * Método para establecer el atributo cuenta
	 * @param cuenta El valor del atributo cuenta a establecer.
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * Método para obtener el atributo idTipoCuenta
	 * @return El atributo idTipoCuenta
	 */
	public Long getIdTipoCuenta() {
		return idTipoCuenta;
	}

	/**
	 * Método para establecer el atributo idTipoCuenta
	 * @param idTipoCuenta El valor del atributo idTipoCuenta a establecer.
	 */
	public void setIdTipoCuenta(Long idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	/**
	 * Método para obtener el atributo naturalezaContable
	 * @return El atributo naturalezaContable
	 */
	public String getNaturalezaContable() {
		return naturalezaContable;
	}

	/**
	 * Método para establecer el atributo naturalezaContable
	 * @param naturalezaContable El valor del atributo naturalezaContable a establecer.
	 */
	public void setNaturalezaContable(String naturalezaContable) {
		this.naturalezaContable = naturalezaContable;
	}

	/**
	 * Método para obtener el atributo naturalezaProcLiq
	 * @return El atributo naturalezaProcLiq
	 */
	public String getNaturalezaProcLiq() {
		return naturalezaProcLiq;
	}

	/**
	 * Método para establecer el atributo naturalezaProcLiq
	 * @param naturalezaProcLiq El valor del atributo naturalezaProcLiq a establecer.
	 */
	public void setNaturalezaProcLiq(String naturalezaProcLiq) {
		this.naturalezaProcLiq = naturalezaProcLiq;
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
	 * Método para obtener el atributo tv
	 * @return El atributo tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Método para establecer el atributo tv
	 * @param tv El valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Método para obtener el atributo emisora
	 * @return El atributo emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Método para establecer el atributo emisora
	 * @param emisora El valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Método para obtener el atributo serie
	 * @return El atributo serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Método para establecer el atributo serie
	 * @param serie El valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Método para obtener el atributo cupon
	 * @return El atributo cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * Método para establecer el atributo cupon
	 * @param cupon El valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * Método para obtener el atributo isin
	 * @return El atributo isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Método para establecer el atributo isin
	 * @param isin El valor del atributo isin a establecer.
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Método para obtener el atributo idMercado
	 * @return El atributo idMercado
	 */
	public Long getIdMercado() {
		return idMercado;
	}

	/**
	 * Método para establecer el atributo idMercado
	 * @param idMercado El valor del atributo idMercado a establecer.
	 */
	public void setIdMercado(Long idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * Método para obtener el atributo posicionDisponible
	 * @return El atributo posicionDisponible
	 */
	public Long getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Método para establecer el atributo posicionDisponible
	 * @param posicionDisponible El valor del atributo posicionDisponible a establecer.
	 */
	public void setPosicionDisponible(Long posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Método para obtener el atributo posicionNoDisponible
	 * @return El atributo posicionNoDisponible
	 */
	public Long getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Método para establecer el atributo posicionNoDisponible
	 * @param posicionNoDisponible El valor del atributo posicionNoDisponible a establecer.
	 */
	public void setPosicionNoDisponible(Long posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
	}

	/**
	 * Método para obtener el atributo posicion
	 * @return El atributo posicion
	 */
	public Long getPosicion() {
		return posicion;
	}

	/**
	 * Método para establecer el atributo posicion
	 * @param posicion El valor del atributo posicion a establecer.
	 */
	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}

	/**
	 * Método para obtener el atributo valorNominal
	 * @return El atributo valorNominal
	 */
	public Double getValorNominal() {
		return valorNominal;
	}

	/**
	 * Método para establecer el atributo valorNominal
	 * @param valorNominal El valor del atributo valorNominal a establecer.
	 */
	public void setValorNominal(Double valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * Método para obtener el atributo valuacion
	 * @return El atributo valuacion
	 */
	public Double getValuacion() {
		return valuacion;
	}

	/**
	 * Método para establecer el atributo valuacion
	 * @param valuacion El valor del atributo valuacion a establecer.
	 */
	public void setValuacion(Double valuacion) {
		this.valuacion = valuacion;
	}

	/**
	 * Método para obtener el atributo ordenCuenta
	 * @return El atributo ordenCuenta
	 */
	public String getOrdenCuenta() {
		return ordenCuenta;
	}

	/**
	 * Método para establecer el atributo ordenCuenta
	 * @param ordenCuenta El valor del atributo ordenCuenta a establecer.
	 */
	public void setOrdenCuenta(String ordenCuenta) {
		this.ordenCuenta = ordenCuenta;
	}

	/**
	 * Método para obtener el atributo ordenDefault
	 * @return El atributo ordenDefault
	 */
	public String getOrdenDefault() {
		return ordenDefault;
	}

	/**
	 * Método para establecer el atributo ordenDefault
	 * @param ordenDefault El valor del atributo ordenDefault a establecer.
	 */
	public void setOrdenDefault(String ordenDefault) {
		this.ordenDefault = ordenDefault;
	}

	/**
	 * Método para obtener el atributo claveTipoInstitucion
	 * @return El atributo claveTipoInstitucion
	 */
	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	/**
	 * Método para establecer el atributo claveTipoInstitucion
	 * @param claveTipoInstitucion El valor del atributo claveTipoInstitucion a establecer.
	 */
	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	/**
	 * Método para obtener el atributo tipoCustodia
	 * @return El atributo tipoCustodia
	 */
	public String getTipoCustodia() {
		return tipoCustodia;
	}

	/**
	 * Método para establecer el atributo tipoCustodia
	 * @param tipoCustodia El valor del atributo tipoCustodia a establecer.
	 */
	public void setTipoCustodia(String tipoCustodia) {
		this.tipoCustodia = tipoCustodia;
	}

	/**
	 * Método para obtener el atributo posicionFondeo
	 * @return El atributo posicionFondeo
	 */
	public Long getPosicionFondeo() {
		return posicionFondeo;
	}

	/**
	 * Método para establecer el atributo posicionFondeo
	 * @param posicionFondeo El valor del atributo posicionFondeo a establecer.
	 */
	public void setPosicionFondeo(Long posicionFondeo) {
		this.posicionFondeo = posicionFondeo;
	}

	/**
	 * Método para obtener el atributo claveMercado
	 * @return El atributo claveMercado
	 */
	public String getClaveMercado() {
		return claveMercado;
	}

	/**
	 * Método para establecer el atributo claveMercado
	 * @param claveMercado El valor del atributo claveMercado a establecer.
	 */
	public void setClaveMercado(String claveMercado) {
		this.claveMercado = claveMercado;
	}

	/**
	 * Método para obtener el atributo folioInstitucion
	 * @return El atributo folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * Método para establecer el atributo folioInstitucion
	 * @param folioInstitucion El valor del atributo folioInstitucion a establecer.
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * Método para obtener el atributo idFolioCuenta
	 * @return El atributo idFolioCuenta
	 */
	public String getIdFolioCuenta() {
		return idFolioCuenta;
	}

	/**
	 * Método para establecer el atributo idFolioCuenta
	 * @param idFolioCuenta El valor del atributo idFolioCuenta a establecer.
	 */
	public void setIdFolioCuenta(String idFolioCuenta) {
		this.idFolioCuenta = idFolioCuenta;
	}

	/**
	 * Método para obtener el atributo cfi
	 * @return El atributo cfi
	 */
	public String getCfi() {
		return cfi;
	}

	/**
	 * Método para establecer el atributo cfi
	 * @param cfi El valor del atributo cfi a establecer.
	 */
	public void setCfi(String cfi) {
		this.cfi = cfi;
	}

}
