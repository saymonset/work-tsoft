/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValorTotalVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private Map tipoValor;

    private String[] tvs;

    private String cuentaActual;

    private PaginaVO pagina;

    private BigInteger totalSaldoDisponible;

    private BigDecimal totalValuacionMercado;

    private BigDecimal totalValuacionNominal;

    /** Total de registros con cupon cortado "Firme" */
    private BigInteger totalRegCuponCortadoF;

    private BigInteger granTotalSaldoDisponible;

    private BigDecimal granTotalValuacionMercado;

    private BigDecimal granTotalValuacionNominal;

    /**
     * @return Returns the cuentaActual.
     */
    public String getCuentaActual() {
        return cuentaActual;
    }

    /**
     * @param cuentaActual
     *            The cuentaActual to set.
     */
    public void setCuentaActual(String cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    /**
     * @return Returns the granTotalSaldoDisponible.
     */
    public BigInteger getGranTotalSaldoDisponible() {
        return granTotalSaldoDisponible;
    }

    /**
     * @param granTotalSaldoDisponible
     *            The granTotalSaldoDisponible to set.
     */
    public void setGranTotalSaldoDisponible(BigInteger granTotalSaldoDisponible) {
        this.granTotalSaldoDisponible = clonaBigInteger(granTotalSaldoDisponible);
    }

    /**
     * @return Returns the granTotalValuacionMercado.
     */
    public BigDecimal getGranTotalValuacionMercado() {
        return granTotalValuacionMercado;
    }

    /**
     * @param granTotalValuacionMercado
     *            The granTotalValuacionMercado to set.
     */
    public void setGranTotalValuacionMercado(BigDecimal granTotalValuacionMercado) {
        this.granTotalValuacionMercado = clonaBigDecimal(granTotalValuacionMercado);
    }

    /**
     * @return Returns the granTotalValuacionNominal.
     */
    public BigDecimal getGranTotalValuacionNominal() {
        return granTotalValuacionNominal;
    }

    /**
     * @param granTotalValuacionNominal
     *            The granTotalValuacionNominal to set.
     */
    public void setGranTotalValuacionNominal(BigDecimal granTotalValuacionNominal) {
        this.granTotalValuacionNominal = clonaBigDecimal(granTotalValuacionNominal);
    }

    /**
     * @return Returns the totalRegCuponCortadoF.
     */
    public BigInteger getTotalRegCuponCortadoF() {
        return totalRegCuponCortadoF;
    }

    /**
     * @param totalRegCuponCortadoF
     *            The totalRegCuponCortadoF to set.
     */
    public void setTotalRegCuponCortadoF(BigInteger totalRegCuponCortadoF) {
        this.totalRegCuponCortadoF = clonaBigInteger(totalRegCuponCortadoF);
    }

    /**
     * @return Returns the totalSaldoDisponible.
     */
    public BigInteger getTotalSaldoDisponible() {
        return totalSaldoDisponible;
    }

    /**
     * @param totalSaldoDisponible
     *            The totalSaldoDisponible to set.
     */
    public void setTotalSaldoDisponible(BigInteger totalSaldoDisponible) {
        this.totalSaldoDisponible = clonaBigInteger(totalSaldoDisponible);
    }

    /**
     * @return Returns the totalValuacionMercado.
     */
    public BigDecimal getTotalValuacionMercado() {
        return totalValuacionMercado;
    }

    /**
     * @param totalValuacionMercado
     *            The totalValuacionMercado to set.
     */
    public void setTotalValuacionMercado(BigDecimal totalValuacionMercado) {
        this.totalValuacionMercado = clonaBigDecimal(totalValuacionMercado);
    }

    /**
     * @return Returns the totalValuacionNominal.
     */
    public BigDecimal getTotalValuacionNominal() {
        return totalValuacionNominal;
    }

    /**
     * @param totalValuacionNominal
     *            The totalValuacionNominal to set.
     */
    public void setTotalValuacionNominal(BigDecimal totalValuacionNominal) {
        this.totalValuacionNominal = clonaBigDecimal(totalValuacionNominal);
    }

    /**
     * @return PaginaVO
     */
    public PaginaVO getPagina() {
        return pagina;
    }

    /**
     * @param pagina
     */
    public void setPagina(PaginaVO pagina) {
        this.pagina = pagina;
    }

    /**
     * @return the tvs
     */
    public String[] getTvs() {
        return tvs;
    }

    /**
     * @param tvs
     *            the tvs to set
     */
    public void setTvs(String[] tvs) {
        this.tvs = tvs;
    }

    /**
     * @return Map
     */
    public Map getTipoValor() {
        return tipoValor;
    }

    /**
     * @param tipoValor
     */
    public void setTipoValor(Map tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
