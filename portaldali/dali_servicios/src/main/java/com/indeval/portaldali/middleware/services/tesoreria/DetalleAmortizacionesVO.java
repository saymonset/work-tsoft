/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DetalleAmortizacionesVO extends LiquidacionDecretosDetalleAmortizacionesVO {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	private String idContraparte;

	private String folioContraparte;

	private BigDecimal importe;

	private BigInteger titulos;

	private BigInteger operacion;

	private String folioLiquidacion;

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getFolioContraparte()
	 */
	public String getFolioContraparte() {
		return folioContraparte;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setFolioContraparte(java.lang.String)
	 */
	public void setFolioContraparte(String folioContraparte) {
		this.folioContraparte = folioContraparte;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getFolioLiquidacion()
	 */
	public String getFolioLiquidacion() {
		return folioLiquidacion;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setFolioLiquidacion(java.math.BigInteger)
	 */
	public void setFolioLiquidacion(String folioLiquidacion) {
		this.folioLiquidacion = folioLiquidacion;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getIdContraparte()
	 */
	public String getIdContraparte() {
		return idContraparte;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setIdContraparte(java.lang.String)
	 */
	public void setIdContraparte(String idContraparte) {
		this.idContraparte = idContraparte;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getImporte()
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setImporte(java.math.BigDecimal)
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getOperacion()
	 */
	public BigInteger getOperacion() {
		return operacion;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setOperacion(java.math.BigInteger)
	 */
	public void setOperacion(BigInteger operacion) {
		this.operacion = operacion;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#getTitulos()
	 */
	public BigInteger getTitulos() {
		return titulos;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesVO#setTitulos(java.math.BigInteger)
	 */
	public void setTitulos(BigInteger titulos) {
		this.titulos = titulos;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof DetalleAmortizacionesVO)))
			return false;

		return true;
	}

}
