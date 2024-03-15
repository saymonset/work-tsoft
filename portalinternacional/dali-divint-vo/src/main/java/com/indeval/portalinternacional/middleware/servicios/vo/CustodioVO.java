package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
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

    /***************************************************************************************************************************
     *  Mapeo del entity @Table(name = "C_CUSTODIO")
     */
    private Integer id;//ID_CUSTODIO
    private String nombreCorto;//NOMBRE_CORTO
    private String descripcion;//DESCRIPCION
    private CatBic idCatBic;//ID_CATBIC
    private String codigoBanco;//CODIGO_BANCO
    private Integer participante;//PARTICIPANTE
    private Integer factorCalculado;//DIVISOR_FACTOR_CALCULADO

    /**
     *
     */
    public CustodioVO() {
    }
    public CustodioVO(Custodio custodio) {
        this();
        if (custodio!= null){
            this.id = custodio.getId();
            this.nombreCorto =         custodio.getNombreCorto();
            this.descripcion =         custodio.getDescripcion();
            this.idCatBic =        custodio.getIdCatBic();
            this.codigoBanco =        custodio.getCodigoBanco();
            this.participante =         custodio.getParticipante();
            this.factorCalculado = custodio.getFactorCalculado();// ID_CUSTODIO
        }
    }

    public CustodioVO(Integer id, String nombreCorto, String descripcion, CatBic idCatBic, String codigoBanco, Integer participante, Integer factorCalculado) {
        this.id = id;
        this.nombreCorto = nombreCorto;
        this.descripcion = descripcion;
        this.idCatBic = idCatBic;
        this.codigoBanco = codigoBanco;
        this.participante = participante;
        this.factorCalculado = factorCalculado;
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


    /***************************************************************************************************************************
     *  Mapeo del entity @Table(name = "C_CUSTODIO")
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CatBic getIdCatBic() {
        return idCatBic;
    }

    public void setIdCatBic(CatBic idCatBic) {
        this.idCatBic = idCatBic;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public Integer getParticipante() {
        return participante;
    }

    public void setParticipante(Integer participante) {
        this.participante = participante;
    }

    public Integer getFactorCalculado() {
        return factorCalculado;
    }

    public void setFactorCalculado(Integer factorCalculado) {
        this.factorCalculado = factorCalculado;
    }
}
