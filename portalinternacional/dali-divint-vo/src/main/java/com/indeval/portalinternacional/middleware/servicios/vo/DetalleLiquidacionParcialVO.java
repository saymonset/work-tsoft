package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;

public class DetalleLiquidacionParcialVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String tipoOperacionSic;
    private Long idEstatusOperacion;
    private BigInteger remanenteOperacion;
    private BigDecimal remanenteEfectivoOperacion;
	private List<LiquidacionParcialMoi> lstLiquidacionParcial;

	/**
	 * 
	 */
	public DetalleLiquidacionParcialVO() {
		super();
	}
	/**
	 * @param tipoOperacionSic
	 * @param idEstatusOperacion
	 * @param remanenteOperacion
	 * @param remanenteEfectivoOperacion
	 * @param paginaVOReporte
	 * @param lstLiquidacionParcial
	 */
	public DetalleLiquidacionParcialVO(String tipoOperacionSic, Long idEstatusOperacion, BigInteger remanenteOperacion,
			BigDecimal remanenteEfectivoOperacion, List<LiquidacionParcialMoi> lstLiquidacionParcial) {
		super();
		this.tipoOperacionSic = tipoOperacionSic;
		this.idEstatusOperacion = idEstatusOperacion;
		this.remanenteOperacion = remanenteOperacion;
		this.remanenteEfectivoOperacion = remanenteEfectivoOperacion;
		this.lstLiquidacionParcial = lstLiquidacionParcial;
	}
	/**
	 * @return the tipoOperacionSic
	 */
	public String getTipoOperacionSic() {
		return tipoOperacionSic;
	}
	/**
	 * @param tipoOperacionSic the tipoOperacionSic to set
	 */
	public void setTipoOperacionSic(String tipoOperacionSic) {
		this.tipoOperacionSic = tipoOperacionSic;
	}
	/**
	 * @return the idEstatusOperacion
	 */
	public Long getIdEstatusOperacion() {
		return idEstatusOperacion;
	}
	/**
	 * @param idEstatusOperacion the idEstatusOperacion to set
	 */
	public void setIdEstatusOperacion(Long idEstatusOperacion) {
		this.idEstatusOperacion = idEstatusOperacion;
	}
	/**
	 * @return the remanenteOperacion
	 */
	public BigInteger getRemanenteOperacion() {
		return remanenteOperacion;
	}
	/**
	 * @param remanenteOperacion the remanenteOperacion to set
	 */
	public void setRemanenteOperacion(BigInteger remanenteOperacion) {
		this.remanenteOperacion = remanenteOperacion;
	}
	/**
	 * @return the remanenteEfectivoOperacion
	 */
	public BigDecimal getRemanenteEfectivoOperacion() {
		return remanenteEfectivoOperacion;
	}
	/**
	 * @param remanenteEfectivoOperacion the remanenteEfectivoOperacion to set
	 */
	public void setRemanenteEfectivoOperacion(BigDecimal remanenteEfectivoOperacion) {
		this.remanenteEfectivoOperacion = remanenteEfectivoOperacion;
	}
	/**
	 * @return the lstLiquidacionParcial
	 */
	public List<LiquidacionParcialMoi> getLstLiquidacionParcial() {
		return lstLiquidacionParcial;
	}
	/**
	 * @param lstLiquidacionParcial the lstLiquidacionParcial to set
	 */
	public void setLstLiquidacionParcial(List<LiquidacionParcialMoi> lstLiquidacionParcial) {
		this.lstLiquidacionParcial = lstLiquidacionParcial;
	}

}
