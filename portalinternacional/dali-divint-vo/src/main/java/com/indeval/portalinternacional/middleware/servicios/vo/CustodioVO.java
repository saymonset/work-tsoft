package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Clase para Custodio
 * @author arivera
 * @version 1.0
 */
@XStreamAlias("CustodiosDepositantes")
public class CustodioVO implements Serializable {

    /**
     * Version Serial
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String bicCode;
    /**
     *
     */
    private String detalleCustodio;
    /**
     *
     */
    private String idInstitucion;
    /**
     *
     */
    private String folioInstitucion;
    /**
     *
     */
    private String cuenta;
    /**
     *
     */
    private List<DepositanteVO> depositantes;

    /**
     *
     */
    public CustodioVO() {
    }

    /**
     *
     * @param bicCode
     * @param detalleCustodio
     */
    public CustodioVO(String bicCode, String detalleCustodio, String idInstitucion, String folioInstitucion, String cuenta) {
        this.bicCode = bicCode;
        this.detalleCustodio = detalleCustodio;
        this.idInstitucion = idInstitucion;
        this.folioInstitucion = folioInstitucion;
        this.cuenta = cuenta;
        this.depositantes = new ArrayList<DepositanteVO>();
    }

    public DepositanteVO findDepositanteVO(String bicCodeDepositante) {
        for (DepositanteVO depositanteVO : depositantes) {
            if (StringUtils.equalsIgnoreCase(depositanteVO.getBicCodeDepositante(), bicCodeDepositante)) {
                return depositanteVO;
            }
        }
        return null;
    }

    /**
     * @return the bicCode
     */
    public String getBicCode() {
        return bicCode;
    }

    /**
     * @param bicCode the bicCode to set
     */
    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
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

    /**
     * @return the depositantes
     */
    public List<DepositanteVO> getDepositantes() {
        return depositantes;
    }

    /**
     * @param depositantes the depositantes to set
     */
    public void setDepositantes(List<DepositanteVO> depositantes) {
        this.depositantes = depositantes;
    }

    /**
     * @return the idInstitucion
     */
    public String getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the folioInstitucion
     */
    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    /**
     * @param folioInstitucion the folioInstitucion to set
     */
    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

	@Override
	public String toString() {
		return "CustodioVO [bicCode=" + bicCode + ", detalleCustodio=" + detalleCustodio + ", idInstitucion="
				+ idInstitucion + ", folioInstitucion=" + folioInstitucion + ", cuenta=" + cuenta + ", depositantes="
				+ depositantes + "]";
	}
    
    

}
