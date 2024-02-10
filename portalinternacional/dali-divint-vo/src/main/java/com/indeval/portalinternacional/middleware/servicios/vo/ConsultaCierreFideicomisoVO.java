/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 */
public class ConsultaCierreFideicomisoVO implements Serializable, Constantes {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;

	/**
	 * CuentaNombrada 
	 */
	private CuentaNombrada cuentaNombrada;
	
	/**
	 * List<MovimientoFideicomisoVO> 
	 */
	private List<MovimientoFideicomisoVO> movimientosFideicomiso;
	
	/**
	 * List<String> 
	 */
	private List<String> nombreTotal;

	/**
	 * List<BigInteger>
	 */
	private List<BigInteger> numeroDepositosRetiros;
	
	/**
	 * List<BigInteger> 
	 */
	private List<BigInteger> numeroTitulosOperados;
	
	/**
	 * BigInteger 
	 */
	private BigInteger totalTitulosOperados;
	
	/**
	 * String 
	 */
	private String nombreSaldo;
	
	/**
	 * BigInteger 
	 */
	private BigInteger totalDepositosRetiros;
	
	/**
	 * BigInteger 
	 */
	private BigInteger saldoFinalDepositosRetiros;
	
	/**
	 * String
	 */
	private String nombrePosicion;
	
	/**
	 * Long 
	 */
	private Long totalEmisionesPosicion;
	
	/**
	 * BigInteger 
	 */
	private BigInteger saldoFinalPosicion;
	
	/**
	 * String
	 */
	private String nombreArqueo;
	
	/**
	 * Long
	 */
	private Long totalEmisionesArqueo;
	
	/**
	 * BigInteger 
	 */
	private BigInteger saldoFinalArqueo;
	
	
	/**
	 * Constructor por omisi&oacute;n
	 */
	public ConsultaCierreFideicomisoVO() {
		super();
	}

	/**
	 * Constructor sobrecargado
	 * @param cuentaNombrada
	 */
	public ConsultaCierreFideicomisoVO(CuentaNombrada cuentaNombrada) {
		super();
		this.cuentaNombrada = cuentaNombrada;
		if (cuentaNombrada != null) {
			this.nombreSaldo = cuentaNombrada.getNombreCuenta();
			this.nombrePosicion = cuentaNombrada.getNombreCuenta();
			AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(cuentaNombrada);
			if (agenteVO != null 
			        && StringUtils.isNotBlank(agenteVO.getClave()) 
			        && StringUtils.isNotBlank(agenteVO.getCuenta()) 
			        && (agenteVO.getClave().trim() + agenteVO.getCuenta().trim()).equals(VITRO)) {
			    this.nombreArqueo = "97,94";
                        } else {
                            this.nombreArqueo = "98,97,54";
                        }
		}		
		this.movimientosFideicomiso = new ArrayList<MovimientoFideicomisoVO>();
		this.nombreTotal = new ArrayList<String>(2);
		this.numeroDepositosRetiros = new ArrayList<BigInteger>(2);
		this.numeroTitulosOperados = new ArrayList<BigInteger>(2);
	}

	/**
	 * @return CuentaNombrada
	 */
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @param cuentaNombrada
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @return List<MovimientoFideicomisoVO>
	 */
	public List<MovimientoFideicomisoVO> getMovimientosFideicomiso() {
		return movimientosFideicomiso;
	}

	/**
	 * @param movimientosFideicomiso
	 */
	public void setMovimientosFideicomiso(List<MovimientoFideicomisoVO> movimientosFideicomiso) {
		this.movimientosFideicomiso = movimientosFideicomiso;
	}

	/**
	 * @return String
	 */
	public String getNombreSaldo() {
		return nombreSaldo;
	}

	/**
	 * @param nombreSaldo
	 */
	public void setNombreSaldo(String nombreSaldo) {
		this.nombreSaldo = nombreSaldo;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getTotalDepositosRetiros() {
		return totalDepositosRetiros;
	}

	/**
	 * @param totalDepositosRetiros
	 */
	public void setTotalDepositosRetiros(BigInteger totalDepositosRetiros) {
		this.totalDepositosRetiros = totalDepositosRetiros;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getSaldoFinalDepositosRetiros() {
		return saldoFinalDepositosRetiros;
	}

	/**
	 * @param saldoFinalDepositosRetiros
	 */
	public void setSaldoFinalDepositosRetiros(BigInteger saldoFinalDepositosRetiros) {
		this.saldoFinalDepositosRetiros = saldoFinalDepositosRetiros;
	}

	/**
	 * @return String
	 */
	public String getNombrePosicion() {
		return nombrePosicion;
	}

	/**
	 * @param nombrePosicion
	 */
	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}

	/**
	 * @return Integer
	 */
	public Long getTotalEmisionesPosicion() {
		return totalEmisionesPosicion;
	}

	/**
	 * @param totalEmisionesPosicion
	 */
	public void setTotalEmisionesPosicion(Long totalEmisionesPosicion) {
		this.totalEmisionesPosicion = totalEmisionesPosicion;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getSaldoFinalPosicion() {
		return saldoFinalPosicion;
	}

	/**
	 * @param saldoFinalPosicion
	 */
	public void setSaldoFinalPosicion(BigInteger saldoFinalPosicion) {
		this.saldoFinalPosicion = saldoFinalPosicion;
	}

	/**
	 * @return String
	 */
	public String getNombreArqueo() {
		return nombreArqueo;
	}

	/**
	 * @param nombreArqueo
	 */
	public void setNombreArqueo(String nombreArqueo) {
		this.nombreArqueo = nombreArqueo;
	}

	/**
	 * @return Integer
	 */
	public Long getTotalEmisionesArqueo() {
		return totalEmisionesArqueo;
	}

	/**
	 * @param totalEmisionesArqueo
	 */
	public void setTotalEmisionesArqueo(Long totalEmisionesArqueo) {
		this.totalEmisionesArqueo = totalEmisionesArqueo;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getSaldoFinalArqueo() {
		return saldoFinalArqueo;
	}

	/**
	 * @param saldoFinalArqueo
	 */
	public void setSaldoFinalArqueo(BigInteger saldoFinalArqueo) {
		this.saldoFinalArqueo = saldoFinalArqueo;
	}

	/**
	 * @return List<String>
	 */
	public List<String> getNombreTotal() {
		return nombreTotal;
	}

	/**
	 * @param nombreTotal
	 */
	public void setNombreTotal(List<String> nombreTotal) {
		this.nombreTotal = nombreTotal;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getTotalTitulosOperados() {
		return totalTitulosOperados;
	}

	/**
	 * @param totalTitulosOperados
	 */
	public void setTotalTitulosOperados(BigInteger totalTitulosOperados) {
		this.totalTitulosOperados = totalTitulosOperados;
	}

	/**
	 * @return List<BigInteger>
	 */
	public List<BigInteger> getNumeroDepositosRetiros() {
		return numeroDepositosRetiros;
	}

	/**
	 * @param numeroDepositosRetiros
	 */
	public void setNumeroDepositosRetiros(List<BigInteger> numeroDepositosRetiros) {
		this.numeroDepositosRetiros = numeroDepositosRetiros;
	}

	/**
	 * @return List<BigInteger>
	 */
	public List<BigInteger> getNumeroTitulosOperados() {
		return numeroTitulosOperados;
	}

	/**
	 * @param numeroTitulosOperados
	 */
	public void setNumeroTitulosOperados(List<BigInteger> numeroTitulosOperados) {
		this.numeroTitulosOperados = numeroTitulosOperados;
	}

	/**
	 * M&eacute;todo sobrecargado para efectos de ordenamiento
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return (false);
		}
		if (!(o instanceof ConsultaCierreFideicomisoVO)) {
			return (false);
		}
		if (this.cuentaNombrada == null) {
			return (false);
		}
		return (this.cuentaNombrada.equals(((ConsultaCierreFideicomisoVO)o).getCuentaNombrada()));
	}
	
	/**
	 * M&eacute;todo sobrecargado para efectos de ordenamiento
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.cuentaNombrada == null) {
			return (super.hashCode());
	
		}
		return (this.cuentaNombrada.hashCode());
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[ ");
		stringBuilder.append("nombreArqueo: "+this.nombreArqueo+" ");
		stringBuilder.append("totalEmisionesArqueo: "+this.totalEmisionesArqueo+" ");
		stringBuilder.append("saldoFinalArqueo: "+this.saldoFinalArqueo+"\n\r");
		stringBuilder.append("nombrePosicion: "+this.nombrePosicion+" ");
		stringBuilder.append("totalEmisionesPosicion: "+this.totalEmisionesPosicion+" ");
		stringBuilder.append("saldoFinalPosicion: "+this.saldoFinalPosicion+"\n\r");
		stringBuilder.append("totalDepositosRetiros: "+this.totalDepositosRetiros+" ");
		stringBuilder.append("saldoFinalDepositosRetiros: "+this.saldoFinalDepositosRetiros+"\n\r");
		stringBuilder.append("cuentaNombrada: "+this.cuentaNombrada+" ");
		stringBuilder.append("movimientosFideicomisos: "+this.movimientosFideicomiso+"\n\r");
		stringBuilder.append("nombreTotal: "+this.nombreTotal+" ");
		stringBuilder.append("numeroDepositosRetiros: "+this.numeroDepositosRetiros+" ");
		stringBuilder.append("numeroTitulosOperados: "+this.numeroTitulosOperados+" ]");
		return (stringBuilder.toString());
	}
}
