/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class AgentePersistence implements Serializable  {
	
	/**
     * Serializaci&oacute;n
     */
    private static final long serialVersionUID = 1L;
    
    /** tenencia = TESO */
	public static final String TESO = "TESO";
	
	/** tipoCta = PRO*/
	public static final String PRO = "PRO";
	
	/** tipoCta = TER*/
	public static final String TER = "TER";
	
    /** Cuenta =  0030*/
	public static final String  CUENTA_0030 = "0030";

    /** Cuenta =  0031*/
	public static final String  CUENTA_0031 = "0031";
	
	/** Cuenta =  54*/
	public static final String  CUENTA_54 = "54";
	
	/** Cuenta =  0070*/
	public static final String  CUENTA_0070 = "0070";
	
	/** Cuenta = 0080 */
	public static final String  CUENTA_0080 = "0080";
	
	/** Cuenta = 0081 */
	public static final String  CUENTA_0081 = "0081";

	/** Cuenta = 94 */
	public static final String  CUENTA_94 = "94";
	
    /** Cuenta = 97 */
	public static final String  CUENTA_97 = "97";

    /** Cuenta = 98 */
	public static final String  CUENTA_98 = "98";

    /** Cuenta = 99 */
	public static final String  CUENTA_99 = "99";

	/** Cuenta 0050 a 0057 */
	public static final String[] CUENTAS_0050to0057 = {"0050","0051","0052","0053",
		"0054","0055","0056","0057"};
	
    private AgentePK agentePK;

    private String uVersion;

    private String tipoCta;

    private String tipoOper;

    private String uso;

    private String aplicacion;

    private Date fechaAlta;

    private Date fechaHora;

    private Date fechaVenc;

    private String nombreCorto;

    private String procTerc;

    private String razonSocial;

    private String responsable;

    private String socioIndeval;

    private String status;

    private String tipoCuenta;

    private String tipoDepositante;

    private String tipoPortafolio;

    private String usuario;

    private BigDecimal creditoDisponible;

    private BigDecimal limiteCredito;

    private String tenencia;

    private String tipoAdmon;

    private Set valores;

    private Set valoresCapital;

    private Set traspasos;

    private String cta_efectivo;

    private String cta_valores;
    
    
    /**
     * @return the agentePK
     */
    public AgentePK getAgentePK() {
        return agentePK;
    }

    /**
     * @return the uVersion
     */
    public String getUVersion() {
        return uVersion;
    }

    /**
     * @return the tipoCta
     */
    public String getTipoCta() {
        return tipoCta;
    }

    /**
     * @return the tipoOper
     */
    public String getTipoOper() {
        return tipoOper;
    }

    /**
     * @return the uso
     */
    public String getUso() {
        return uso;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @return the fechaAlta
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @return the fechaVenc
     */
    public Date getFechaVenc() {
        return fechaVenc;
    }

    /**
     * @return the nombreCorto
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * @return the procTerc
     */
    public String getProcTerc() {
        return procTerc;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @return the socioIndeval
     */
    public String getSocioIndeval() {
        return socioIndeval;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @return the tipoDepositante
     */
    public String getTipoDepositante() {
        return tipoDepositante;
    }

    /**
     * @return the tipoPortafolio
     */
    public String getTipoPortafolio() {
        return tipoPortafolio;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the creditoDisponible
     */
    public BigDecimal getCreditoDisponible() {
        return creditoDisponible;
    }

    /**
     * @return the limiteCredito
     */
    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * @return the tenencia
     */
    public String getTenencia() {
        return tenencia;
    }

    /**
     * @return the tipoAdmon
     */
    public String getTipoAdmon() {
        return tipoAdmon;
    }

    /**
     * @return the valores
     */
    public Set getValores() {
        return valores;
    }

    /**
     * @return the valoresCapital
     */
    public Set getValoresCapital() {
        return valoresCapital;
    }

    /**
     * @return the traspasos
     */
    public Set getTraspasos() {
        return traspasos;
    }

    /**
     * Metodo que filtra el Set de valores y retorna solo los valores con saldo
     * @return Set conjunto
     */
    public Set getValoresConSaldo() {
        Set setValoresConSaldo = null;
        if(getValores() != null){
            setValoresConSaldo = new HashSet();
            for (Iterator iter = this.getValores().iterator(); iter.hasNext();) {
//                Valor valor = (Valor) iter.next();
//                if( valor != null && valor.getSaldoDisponible() != null 
//                        && valor.getSaldoDisponible().compareTo(new BigDecimal(0)) > 0){
//                    setValoresConSaldo.add(valor);
//                }
            }
        }
        
        return setValoresConSaldo;
    }
    
    /**
     * @param valores
     */
    public void setValores(Set valores) {
        this.valores = valores;
    }
    /**
     * @param valoresCapital
     */
    public void setValoresCapital(Set valoresCapital) {
        this.valoresCapital = valoresCapital;
    }
    /**
     * @param agentePK
     */
    public void setAgentePK(AgentePK agentePK) {
		this.agentePK = agentePK;
	}
	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	/**
	 * @param creditoDisponible
	 */
	public void setCreditoDisponible(BigDecimal creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}
	/**
	 * @param fechaAlta
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	/**
	 * @param fechaVenc
	 */
	public void setFechaVenc(Date fechaVenc) {
		this.fechaVenc = fechaVenc;
	}
	/**
	 * @param limiteCredito
	 */
	public void setLimiteCredito(BigDecimal limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	/**
	 * @param nombreCorto
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @param procTerc
	 */
	public void setProcTerc(String procTerc) {
		this.procTerc = procTerc;
	}
	/**
	 * @param razonSocial
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @param responsable
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	/**
	 * @param socioIndeval
	 */
	public void setSocioIndeval(String socioIndeval) {
		this.socioIndeval = socioIndeval;
	}
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param tenencia
	 */
	public void setTenencia(String tenencia) {
		this.tenencia = tenencia;
	}
	/**
	 * @param tipoAdmon
	 */
	public void setTipoAdmon(String tipoAdmon) {
		this.tipoAdmon = tipoAdmon;
	}
	/**
	 * @param tipoCta
	 */
	public void setTipoCta(String tipoCta) {
		this.tipoCta = tipoCta;
	}
	/**
	 * @param tipoCuenta
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	/**
	 * @param tipoDepositante
	 */
	public void setTipoDepositante(String tipoDepositante) {
		this.tipoDepositante = tipoDepositante;
	}
	/**
	 * @param tipoOper
	 */
	public void setTipoOper(String tipoOper) {
		this.tipoOper = tipoOper;
	}
	/**
	 * @param tipoPortafolio
	 */
	public void setTipoPortafolio(String tipoPortafolio) {
		this.tipoPortafolio = tipoPortafolio;
	}
	/**
	 * @param uso
	 */
	public void setUso(String uso) {
		this.uso = uso;
	}
	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @param version
	 */
	public void setUVersion(String version) {
		uVersion = version;
	}

    /**
     * @param traspasos
     */
    public void setTraspasos(Set traspasos) {
        this.traspasos = traspasos;
    }

	/**
	 * @return String
	 */
	public String getCta_valores() {
		return cta_valores;
	}

	/**
	 * @param cta_valores
	 */
	public void setCta_valores(String cta_valores) {
		this.cta_valores = cta_valores;
	}

	/** 
	 * @return String
	 */
	public String getCta_efectivo() {
		return cta_efectivo;
	}

	/**
	 * @param cta_efectivo
	 */
	public void setCta_efectivo(String cta_efectivo) {
		this.cta_efectivo = cta_efectivo;
	}

	/**
	 * Verifica si el tipoCta es TESO
	 * @return True solo si tipoCta = TESO. False lo contrario.
	 */
	public boolean esTipoTenenciaTESO(){
		return esTipoTenencia(TESO);
	}
	
	/**
	 * Verifica que la tenencia sea igual al parametro que se pasa
	 * @param tipoTenencia el parametro con el que se compara con el campo tenencia
	 * @return True solo si es igual a la tenencia. False lo contrario.
	 */
	private boolean esTipoTenencia(String tipoTenencia){
		if (StringUtils.isNotBlank(getTenencia()) && getTenencia().trim().equalsIgnoreCase(tipoTenencia))
			return true;
		return false;
	}
	
	/**
	 * Valida que el atributo tipoCta contenga el valor PRO%.
	 * @return True solamente si el tipoCta contiene el valor PRO%. False lo contrario.
	 */
	public boolean contieneTipoCtaPRO(){
		return esTipoCta(PRO);
	}
	
	/**
	 * Valida que el atributo tipoCta sea contenga el valor TER.
	 * @return True solament si el tipoCta contiene el valor TER. False lo contrario.
	 */
	public boolean contieneTipoCtaTER(){
		return esTipoCta(TER);
	}
	
	/**
	 * Verifica que el tipoCta sea del tipo que pasa como parametro.
	 * @param tipoCta el parametro que se compara con el campo tipoCta.
	 * @return true si y solo si el tipoCta es igual atributo tipoCta de esta instancia.
	 * False lo contrario.
	 */
	private boolean esTipoCta(String tipoCta){
		if (StringUtils.isNotBlank(getTipoCta()) && 
				getTipoCta().trim().indexOf(tipoCta) > -1)
			return true;
		return false;
		
	}
    /**
     * Valida que la cuenta de este agente comience en 54 
     * @return true solo si la cuenta comienza en 54. False lo contrario
     */
    public boolean esComienzoCuenta54(){
    	return esComienzoTerminacionCuenta(false, CUENTA_54);
    }
    
    /**
     * Valida que la cuenta de este agente termina en 94 
     * @return true solo si la cuenta termina en 94. False lo contrario
     */
    public boolean esTerminacionCuenta94(){
    	return esComienzoTerminacionCuenta(true, CUENTA_94);
    }
    
    /**
     * Valida que la cuenta de este agente termina en 97 
     * @return true solo si la cuenta termina en 97. False lo contrario
     */
    public boolean esTerminacionCuenta97(){
    	return esComienzoTerminacionCuenta(true, CUENTA_97);
    }
    
    /**
     * Valida que la cuenta de este agente termina en 98 
     * @return true solo si la cuenta termina en 98. False lo contrario
     */
    public boolean esTerminacionCuenta98(){
    	return esComienzoTerminacionCuenta(true, CUENTA_98);
    }

    /**
     * Valida que la cuenta de este agente termina en 99 
     * @return true solo si la cuenta termina en 99. False lo contrario
     */
    public boolean esTerminacionCuenta99(){
    	return esComienzoTerminacionCuenta(true, CUENTA_99);
    }

    /**
     * Valida que la cuenta de este agente sea 0030 
     * @return true solo si es 0030. False lo contrario
     */
    public boolean esCuenta0030(){
    	return esComienzoTerminacionCuenta(true, CUENTA_0030);
    }

    /**
     * Valida que la cuenta de este 0031 
     * @return true solo si la cuenta es 0031. False lo contrario
     */
    public boolean esCuenta0031(){
    	return esComienzoTerminacionCuenta(true, CUENTA_0031);
    }

    /**
     * Verifica que la cuenta sea 0050 al 0057
     * @return True si y solo si la cuenta es entre 0050 y 0057. False lo contrario.
     */
    public boolean esCuenta0050to0057(){
    	boolean flag = false;
    	for(int i = 0; i < CUENTAS_0050to0057.length; i++){
    		if(esComienzoTerminacionCuenta(true, CUENTAS_0050to0057[i])){
    			flag = true; 
    			break;
    		}
    	}
    	return flag;
    }

    /**
     * Valida que la cuenta de este agente sea 0070 
     * @return true solo si la cuenta es 0070. False lo contrario
     */
    public boolean esCuenta0070(){
    	return esComienzoTerminacionCuenta(true, CUENTA_0070);
    }

    /**
     * Valida que la cuenta de este agente sea 0080 
     * @return true solo si la cuenta es 0080. False lo contrario
     */
    public boolean esCuenta0080(){
    	return esComienzoTerminacionCuenta(true, CUENTA_0080);
    }

    /**
     * Valida que la cuenta de este agente sea 0081 
     * @return true solo si la cuenta es 0081. False lo contrario
     */
    public boolean esCuenta0081(){
    	return esComienzoTerminacionCuenta(true, CUENTA_0081);
    }

    /**
     * Valida si la cuenta de este agente termina o comienza con el n&uacute;mero de cuenta propuesto 
     * en el p&aacute;rametro
     * @param esTerminacion True verifica que la cuenta termine con el numero deseado. False 
     * verifica que comience con el numero propuesto.
     * @param numeroCuenta el n&uacute;mero de cuenta que con el que se desea validar 
     * @return boolean - true si la cuenta termina o comienza con el n&uacute;mero deseado. 
     *                   false lo contrario.
     */
    private boolean esComienzoTerminacionCuenta(boolean esTerminacion, String numeroCuenta){
    	
    	boolean flag = false;
    	if (StringUtils.isNotBlank(getAgentePK().getCuenta())){
    		if(esTerminacion){
    			if(getAgentePK().getCuenta().trim().endsWith(numeroCuenta))
    				flag = true;
    		}else{
    			if(getAgentePK().getCuenta().trim().startsWith(numeroCuenta))
    				flag = true;
    		}
    	}
    	return flag;
    }
    
    /**
     * valida que este agente sea de la misma instituci&oacute;n, es decir, que el que pasa como 
     * parametro tenga el mismo id y folio que esta instancia
     * @param agente el agente con el que se desea comparar esta instancia.
     * @return True si el idInst y folioInst son los mismos que el del agente
     * que viene como parametro. False lo contrario.
     */
    public boolean esMismaInstitucion(AgentePersistence agente){
    	boolean flag = false;
    	if(agente != null && agente.getAgentePK() != null && 
    			StringUtils.isNotBlank(agente.getAgentePK().getIdInst()) &&
    			StringUtils.isNotBlank(agente.getAgentePK().getFolioInst())){
    		if(this.getAgentePK() != null && 
    				StringUtils.isNotBlank(this.getAgentePK().getIdInst()) &&
        			StringUtils.isNotBlank(this.getAgentePK().getFolioInst())){
        		if(this.getAgentePK().getIdInst().equals(agente.getAgentePK().getIdInst()) &&
        				this.getAgentePK().getFolioInst().equals(agente.getAgentePK().getFolioInst())){
        			flag = true;
        		}
    		}
    	}
    	return flag;
    }
}
