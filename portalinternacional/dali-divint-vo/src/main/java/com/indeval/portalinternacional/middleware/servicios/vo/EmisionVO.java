package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Clase para Emision
 * @author arivera
 * @version 1.0
 */
@XStreamAlias("EmisionesCustodiosDepositantes")
public class EmisionVO implements Serializable {

    /**
     * Version Serial
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String isin;
    /**
     *
     */
    private String tv;
    /**
     *
     */
    private String emisora;
    /**
     *
     */
    private String serie;
    /**
     *
     */
    private String bicProd;
    /**
     *
     */
    private String detalleCustodio;
    /**
     *
     */
    private List<CustodioVO> custodiosDepositantes;

    /**
     * Empty constructor
     */
    public EmisionVO() {
    }

    /**
     *
     * @param isin
     * @param tv
     * @param emisora
     * @param serie
     */
    public EmisionVO(String isin, String tv, String emisora, String serie) {
        this.isin = isin;
        this.tv = tv;
        this.emisora = emisora;
        this.serie = serie;
        this.custodiosDepositantes = new ArrayList<CustodioVO>();
    }

    public String getKey() {
        return StringUtils.upperCase(this.tv) + StringUtils.upperCase(this.emisora)
                + StringUtils.upperCase(this.serie)
                + StringUtils.upperCase(StringUtils.defaultString(this.isin, "ISIN_NULO"));
    }

    public CustodioVO findCustodioVO(String bicCode) {
        for (CustodioVO custodioVO : custodiosDepositantes) {
            if (StringUtils.equalsIgnoreCase(custodioVO.getBicCode(), bicCode)) {
                return custodioVO;
            }
        }
        return null;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return isin;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv the tv to set
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora the emisora to set
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
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the custodiosDepositantes
     */
    public List<CustodioVO> getCustodiosDepositantes() {
        return custodiosDepositantes;
    }

    /**
     * @param custodiosDepositantes the custodiosDepositantes to set
     */
    public void setCustodiosDepositantes(List<CustodioVO> custodiosDepositantes) {
        this.custodiosDepositantes = custodiosDepositantes;
    }

    /**
     * @return the bicProd
     */
    public String getBicProd() {
        return bicProd;
    }

    /**
     * @param bicProd the bicProd to set
     */
    public void setBicProd(String bicProd) {
        this.bicProd = bicProd;
    }

    /**
     * @return the detalleCustodio
     */
    public String getDetalleCustodio() {
        return detalleCustodio;
    }

    /**
     * @param detalleCustodio the detalleCustodio to set
     */
    public void setDetalleCustodio(String detalleCustodio) {
        this.detalleCustodio = detalleCustodio;
    }

	@Override
	public String toString() {
		return "EmisionVO [isin=" + isin + ", tv=" + tv + ", emisora=" + emisora + ", serie=" + serie + ", bicProd="
				+ bicProd + ", detalleCustodio=" + detalleCustodio + ", custodiosDepositantes=" + custodiosDepositantes
				+ "]";
	}
    
    

}
