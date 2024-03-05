/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistencia.posicion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la vista para consultar la posicion nombrada.
 * 
 * @author Pablo Balderas
 */
@Entity
@Table(name="V_POSICION_NOMBRADA_H")
public class VPosicionNombradaH extends Posicion implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -27260264040968577L;

	/**
	 * Método para obtener el atributo idPosicionHistorica
	 * @return El atributo idPosicionHistorica
	 */
	@Id
	@Column(name="ID_POSICION_NOMBRADA_H")
	public Long getIdPosicionHistorica() {
		return super.getIdPosicionHistorica();
	}

	/**
	 * Método para establecer el atributo idPosicionHistorica
	 * @param idPosicionHistorica El valor del atributo idPosicionHistorica a establecer.
	 */
	public void setIdPosicionHistorica(Long idPosicionHistorica) {
		super.setIdPosicionHistorica(idPosicionHistorica);
	}

	/**
	 * Método para obtener el atributo fecha
	 * @return El atributo fecha
	 */
	@Column(name="FECHA_CREACION")
	public Date getFecha() {
		return super.getFecha();
	}

	/**
	 * Método para establecer el atributo fecha
	 * @param fecha El valor del atributo fecha a establecer.
	 */
	public void setFecha(Date fecha) {
		super.setFecha(fecha);
	}
	
	/**
	 * Método para obtener el atributo idPosicion
	 * @return El atributo idPosicion
	 */
	@Column(name = "ID_POSICION_NOMBRADA")
	public Long getIdPosicion() {
		return super.getIdPosicion();
	}

	/**
	 * Método para establecer el atributo idPosicion
	 * @param idPosicion El valor del atributo idPosicion a establecer.
	 */
	public void setIdPosicion(Long idPosicion) {
		super.setIdPosicion(idPosicion);
	}
		
	/**
	 * Método para obtener el atributo idCuenta
	 * @return El atributo idCuenta
	 */
	@Column(name = "ID_CUENTA_NOMBRADA")
	public Long getIdCuenta() {
		return super.getIdCuenta();
	}

	/**
	 * Método para establecer el atributo idCuenta
	 * @param idCuenta El valor del atributo idCuenta a establecer.
	 */
	public void setIdCuenta(Long idCuenta) {
		super.setIdCuenta(idCuenta);
	}

	/**
	 * Método para obtener el atributo idBoveda
	 * @return El atributo idBoveda
	 */
	@Column(name = "ID_BOVEDA")
	public Long getIdBoveda() {
		return super.getIdBoveda();
	}

	/**
	 * Método para establecer el atributo idBoveda
	 * @param idBoveda El valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(Long idBoveda) {
		super.setIdBoveda(idBoveda);
	}
	
	/**
	 * Método para obtener el atributo idCupon
	 * @return El atributo idCupon
	 */
	@Column(name = "ID_CUPON")
	public Long getIdCupon() {
		return super.getIdCupon();
	}

	/**
	 * Método para establecer el atributo idCupon
	 * @param idCupon El valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(Long idCupon) {
		super.setIdCupon(idCupon);
	}

	/**
	 * Método para obtener el atributo idEmision
	 * @return El atributo idEmision
	 */
	@Column(name = "ID_EMISION")
	public Long getIdEmision() {
		return super.getIdEmision();
	}

	/**
	 * Método para establecer el atributo idEmision
	 * @param idEmision El valor del atributo idEmision a establecer.
	 */
	public void setIdEmision(Long idEmision) {
		super.setIdEmision(idEmision);
	}
	
	/**
	 * Método para obtener el atributo idInstitucion
	 * @return El atributo idInstitucion
	 */
	@Column(name = "ID_INSTITUCION")
	public Long getIdInstitucion() {
		return super.getIdInstitucion();
	}

	/**
	 * Método para establecer el atributo idInstitucion
	 * @param idInstitucion El valor del atributo idInstitucion a establecer.
	 */
	public void setIdInstitucion(Long idInstitucion) {
		super.setIdInstitucion(idInstitucion);
	}
	
	/**
	 * Método para obtener el atributo claveTipoInstitucion
	 * @return El atributo claveTipoInstitucion
	 */
	@Column(name = "CLAVE_TIPO_INSTITUCION")
	public String getClaveTipoInstitucion() {
		return super.getClaveTipoInstitucion();
	}

	/**
	 * Método para establecer el atributo claveTipoInstitucion
	 * @param claveTipoInstitucion El valor del atributo claveTipoInstitucion a establecer.
	 */
	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		super.setClaveTipoInstitucion(claveTipoInstitucion);
	}
	
	/**
	 * Método para obtener el atributo folioInstitucion
	 * @return El atributo folioInstitucion
	 */
	@Column(name="FOLIO_INSTITUCION")
	public String getFolioInstitucion() {
		return super.getFolioInstitucion();
	}

	/**
	 * Método para establecer el atributo folioInstitucion
	 * @param folioInstitucion El valor del atributo folioInstitucion a establecer.
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		super.setFolioInstitucion(folioInstitucion);
	}
	
	/**
	 * Método para obtener el atributo cuenta
	 * @return El atributo cuenta
	 */
	@Column(name = "CUENTA")
	public String getCuenta() {
		return super.getCuenta();
	}

	/**
	 * Método para establecer el atributo cuenta
	 * @param cuenta El valor del atributo cuenta a establecer.
	 */
	public void setCuenta(String cuenta) {
		super.setCuenta(cuenta);
	}
	
	/**
	 * Método para obtener el atributo idTipoCuenta
	 * @return El atributo idTipoCuenta
	 */
	@Column(name = "ID_TIPO_CUENTA")
	public Long getIdTipoCuenta() {
		return super.getIdTipoCuenta();
	}

	/**
	 * Método para establecer el atributo idTipoCuenta
	 * @param idTipoCuenta El valor del atributo idTipoCuenta a establecer.
	 */
	public void setIdTipoCuenta(Long idTipoCuenta) {
		super.setIdTipoCuenta(idTipoCuenta);
	}
	
	/**
	 * Método para obtener el atributo tipoCustodia
	 * @return El atributo tipoCustodia
	 */
	@Column(name = "TIPO_CUSTODIA")
	public String getTipoCustodia() {
		return super.getTipoCustodia();
	}

	/**
	 * Método para establecer el atributo tipoCustodia
	 * @param tipoCustodia El valor del atributo tipoCustodia a establecer.
	 */
	public void setTipoCustodia(String tipoCustodia) {
		super.setTipoCustodia(tipoCustodia);
	}
	
	/**
	 * Método para obtener el atributo naturalezaContable
	 * @return El atributo naturalezaContable
	 */
	@Column(name = "NATURALEZA_CONTABLE")
	public String getNaturalezaContable() {
		return super.getNaturalezaContable();
	}

	/**
	 * Método para establecer el atributo naturalezaContable
	 * @param naturalezaContable El valor del atributo naturalezaContable a establecer.
	 */
	public void setNaturalezaContable(String naturalezaContable) {
		super.setNaturalezaContable(naturalezaContable);
	}
	
	/**
	 * Método para obtener el atributo naturalezaProcLiq
	 * @return El atributo naturalezaProcLiq
	 */
	@Column(name = "NATURALEZA_PROC_LIQ")
	public String getNaturalezaProcLiq() {
		return super.getNaturalezaProcLiq();
	}

	/**
	 * Método para establecer el atributo naturalezaProcLiq
	 * @param naturalezaProcLiq El valor del atributo naturalezaProcLiq a establecer.
	 */
	public void setNaturalezaProcLiq(String naturalezaProcLiq) {
		super.setNaturalezaProcLiq(naturalezaProcLiq);
	}
	
	/**
	 * Método para obtener el atributo boveda
	 * @return El atributo boveda
	 */
	@Column(name = "BOVEDA")
	public String getBoveda() {
		return super.getBoveda();
	}

	/**
	 * Método para establecer el atributo boveda
	 * @param boveda El valor del atributo boveda a establecer.
	 */
	public void setBoveda(String boveda) {
		super.setBoveda(boveda);
	}
	
	/**
	 * Método para obtener el atributo tv
	 * @return El atributo tv
	 */
	@Column(name = "TV")
	public String getTv() {
		return super.getTv();
	}

	/**
	 * Método para establecer el atributo tv
	 * @param tv El valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		super.setTv(tv);
	}
	
	/**
	 * Método para obtener el atributo emisora
	 * @return El atributo emisora
	 */
	@Column(name = "EMISORA")
	public String getEmisora() {
		return super.getEmisora();
	}

	/**
	 * Método para establecer el atributo emisora
	 * @param emisora El valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		super.setEmisora(emisora);
	}
	
	/**
	 * Método para obtener el atributo serie
	 * @return El atributo serie
	 */
	@Column(name = "SERIE")
	public String getSerie() {
		return super.getSerie();
	}

	/**
	 * Método para establecer el atributo serie
	 * @param serie El valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		super.setSerie(serie);
	}
	
	/**
	 * Método para obtener el atributo cupon
	 * @return El atributo cupon
	 */
	@Column(name = "CUPON")
	public String getCupon() {
		return super.getCupon();
	}

	/**
	 * Método para establecer el atributo cupon
	 * @param cupon El valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		super.setCupon(cupon);
	}
	
	/**
	 * Método para obtener el atributo isin
	 * @return El atributo isin
	 */
	@Column(name = "ISIN")
	public String getIsin() {
		return super.getIsin();
	}

	/**
	 * Método para establecer el atributo isin
	 * @param isin El valor del atributo isin a establecer.
	 */
	public void setIsin(String isin) {
		super.setIsin(isin);
	}
	
	/**
	 * Método para obtener el atributo cfi
	 * @return El atributo cfi
	 */
	@Column(name = "CFI")
	public String getCfi() {
		return super.getCfi();
	}

	/**
	 * Método para establecer el atributo cfi
	 * @param cfi El valor del atributo cfi a establecer.
	 */
	public void setCfi(String cfi) {
		super.setCfi(cfi);
	}
	
	/**
	 * Método para obtener el atributo idMercado
	 * @return El atributo idMercado
	 */
	@Column(name = "ID_MERCADO")
	public Long getIdMercado() {
		return super.getIdMercado();
	}

	/**
	 * Método para establecer el atributo idMercado
	 * @param idMercado El valor del atributo idMercado a establecer.
	 */
	public void setIdMercado(Long idMercado) {
		super.setIdMercado(idMercado);
	}
	
	/**
	 * Método para obtener el atributo claveMercado
	 * @return El atributo claveMercado
	 */
	@Column(name = "CLAVE_MERCADO")
	public String getClaveMercado() {
		return super.getClaveMercado();
	}

	/**
	 * Método para establecer el atributo claveMercado
	 * @param claveMercado El valor del atributo claveMercado a establecer.
	 */
	public void setClaveMercado(String claveMercado) {
		super.setClaveMercado(claveMercado);
	}
	
	/**
	 * Método para obtener el atributo posicion
	 * @return El atributo posicion
	 */
	@Column(name = "POSICION")
	public Long getPosicion() {
		return super.getPosicion();
	}

	/**
	 * Método para establecer el atributo posicion
	 * @param posicion El valor del atributo posicion a establecer.
	 */
	public void setPosicion(Long posicion) {
		super.setPosicion(posicion);
	}
	
	/**
	 * Método para obtener el atributo posicionDisponible
	 * @return El atributo posicionDisponible
	 */
	@Column(name = "POSICION_DISPONIBLE")
	public Long getPosicionDisponible() {
		return super.getPosicionDisponible();
	}

	/**
	 * Método para establecer el atributo posicionDisponible
	 * @param posicionDisponible El valor del atributo posicionDisponible a establecer.
	 */
	public void setPosicionDisponible(Long posicionDisponible) {
		super.setPosicionDisponible(posicionDisponible);
	}
	
	/**
	 * Método para obtener el atributo posicionNoDisponible
	 * @return El atributo posicionNoDisponible
	 */
	@Column(name = "POSICION_NO_DISPONIBLE")
	public Long getPosicionNoDisponible() {
		return super.getPosicionNoDisponible();
	}

	/**
	 * Método para establecer el atributo posicionNoDisponible
	 * @param posicionNoDisponible El valor del atributo posicionNoDisponible a establecer.
	 */
	public void setPosicionNoDisponible(Long posicionNoDisponible) {
		super.setPosicionNoDisponible(posicionNoDisponible);
	}
	
	/**
	 * Método para obtener el atributo valorNominal
	 * @return El atributo valorNominal
	 */
	@Column(name = "VALOR_NOMINAL")
	public Double getValorNominal() {
		return super.getValorNominal();
	}

	/**
	 * Método para establecer el atributo valorNominal
	 * @param valorNominal El valor del atributo valorNominal a establecer.
	 */
	public void setValorNominal(Double valorNominal) {
		super.setValorNominal(valorNominal);
	}
	
	/**
	 * Método para obtener el atributo valuacion
	 * @return El atributo valuacion
	 */
	@Column(name = "VALUACION")
	public Double getValuacion() {
		return super.getValuacion();
	}

	/**
	 * Método para establecer el atributo valuacion
	 * @param valuacion El valor del atributo valuacion a establecer.
	 */
	public void setValuacion(Double valuacion) {
		super.setValuacion(valuacion);
	}

	/**
	 * Método para obtener el atributo ordenCuenta
	 * @return El atributo ordenCuenta
	 */
	@Column(name = "ordenCuenta")
	public String getOrdenCuenta() {
		return super.getOrdenCuenta();
	}

	/**
	 * Método para establecer el atributo ordenCuenta
	 * @param ordenCuenta El valor del atributo ordenCuenta a establecer.
	 */
	public void setOrdenCuenta(String ordenCuenta) {
		super.setOrdenCuenta(ordenCuenta);
	}

	/**
	 * Método para obtener el atributo ordenDefault
	 * @return El atributo ordenDefault
	 */
	@Column(name = "ordenDefault")
	public String getOrdenDefault() {
		return super.getOrdenDefault();
	}

	/**
	 * Método para establecer el atributo ordenDefault
	 * @param ordenDefault El valor del atributo ordenDefault a establecer.
	 */
	public void setOrdenDefault(String ordenDefault) {
		super.setOrdenDefault(ordenDefault);
	}
	
	/**
	 * Método para obtener el atributo idFolioCuenta
	 * @return El atributo idFolioCuenta
	 */
	@Column(name="idFolioCuenta")
	public String getIdFolioCuenta() {
		return super.getIdFolioCuenta();
	}

	/**
	 * Método para establecer el atributo idFolioCuenta
	 * @param idFolioCuenta El valor del atributo idFolioCuenta a establecer.
	 */
	public void setIdFolioCuenta(String idFolioCuenta) {
		super.setIdFolioCuenta(idFolioCuenta);
	}
}
