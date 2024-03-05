/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaRegistroVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO agente;

    private AgenteVO contraparte;

    private BigInteger entradas;

    private BigInteger salidas;

    private BigInteger folio;

    private String tipoOperacion;

    private String hora;

    private String origen;

    private String aplicacion;

    private String movimiento;

    private String descripcion;

    private EmisionVO emisionVO;

    private AgenteVO agenteFirmado;

    private BigDecimal precioPorTitulo;

    /** Constructor */
    public EstadoCuentaRegistroVO() {
        this.entradas = BIG_INTEGER_ZERO;
        this.salidas = BIG_INTEGER_ZERO;
        this.folio = BIG_INTEGER_ZERO;
        this.precioPorTitulo = BIG_DECIMAL_ZERO;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return String
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param aplicacion
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getContraparte() {
        return contraparte;
    }

    /**
     * @param contraparte
     */
    public void setContraparte(AgenteVO contraparte) {
        this.contraparte = contraparte;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getEntradas() {
        return entradas;
    }

    /**
     * @param entradas
     */
    public void setEntradas(BigInteger entradas) {
        this.entradas = entradas;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getFolio() {
        return folio;
    }

    /**
     * @param folio
     */
    public void setFolio(BigInteger folio) {
        this.folio = folio;
    }

    /**
     * @return String
     */
    public String getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento
     */
    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return String
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getSalidas() {
        return salidas;
    }

    /**
     * @param salidas
     */
    public void setSalidas(BigInteger salidas) {
        this.salidas = salidas;
    }

    /**
     * @return String
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return String
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado
     */
    public void setAgenteFirmado(AgenteVO agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * @param agente
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * Retorna 'true' si el atributo agente firmado tiene el mismo
     * id+folio+cuenta que el atributo contraparte. En caso contrario retorna
     * 'false'
     * 
     * @return boolean
     */
    public boolean esContraparte() {
        boolean esContraparte = false;
        if (this.agenteFirmado != null) {
            esContraparte = this.agenteFirmado.equals(this.contraparte);
        }
        return esContraparte;
    }

    /**
     * Retorna 'true' si el atributo agente firmado tiene el mismo
     * id+folio+cuenta que el atributo agente. En caso contrario retorna 'false'
     * 
     * @return boolean
     */
    public boolean esTraspasante() {
        boolean esTraspasante = false;
        if (this.agenteFirmado != null) {
            esTraspasante = this.agenteFirmado.equals(this.agente);
        }
        return esTraspasante;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPrecioPorTitulo() {
        return precioPorTitulo;
    }

    /**
     * @param precioPorTitulo
     */
    public void setPrecioPorTitulo(BigDecimal precioPorTitulo) {
        this.precioPorTitulo = clonaBigDecimal(precioPorTitulo);
    }

    /**
     * Retorna una nueva instancia de EstadoCuentaRegistroVO con los valores de
     * los atributos del elemento original
     * 
     * @return EstadoCuentaRegistroVO
     */
    public EstadoCuentaRegistroVO clona() {
        EstadoCuentaRegistroVO clon = new EstadoCuentaRegistroVO();
        clon.setAgente(this.agente);
        clon.setAgenteFirmado(this.agenteFirmado);
        clon.setAplicacion(this.aplicacion);
        clon.setContraparte(this.contraparte);
        clon.setDescripcion(this.descripcion);
        clon.setEmisionVO(this.emisionVO);
        clon.setEntradas(this.entradas);
        clon.setFolio(this.folio);
        clon.setHora(this.hora);
        clon.setMovimiento(this.movimiento);
        clon.setOrigen(this.origen);
        clon.setSalidas(this.salidas);
        clon.setTipoOperacion(this.tipoOperacion);
        return clon;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}
