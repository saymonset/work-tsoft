/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class Valor {
	
	private String uVersion;
	
	private ValorPK valorPk;
	
	private String aplicacion;
	
	private BigDecimal cantidadAcciones;
	
	private String emisionExtr;
	
	private BigDecimal ejerDia;
	
	private Date fechaHora;
	
	private BigDecimal saldoDisponible;
	
	private BigDecimal saldoIncluyeOp;
	
	private BigDecimal saldoInicialDia;
	
	private BigDecimal saldoReporto;
	
	private String usuario;
	
	private BigDecimal valoresPrestados;
	
	private BigDecimal valEnPrestamo;
	
	private BigDecimal saldoTesoreria;
	
	private String areaTrabajo;
	
	private String nombreUsuario;
	
	private String ipAddress;
	
    private EmisionPersistence emision;
    
    private Set traspasos;
    
    private AgentePersistence agente;
    
    private InstrumentoVO instrumento;
    
    private Set vendedorMdintrans;
    
    private Set compradorMdintrans;
    
	/**
     * @return the uVersion
     */
    public String getUVersion() {
        return uVersion;
    }

    /**
     * @return the valorPk
     */
    public ValorPK getValorPk() {
        return valorPk;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @return the cantidadAcciones
     */
    public BigDecimal getCantidadAcciones() {
        return cantidadAcciones;
    }

    /**
     * @return the emisionExtr
     */
    public String getEmisionExtr() {
        return emisionExtr;
    }

    /**
     * @return the ejerDia
     */
    public BigDecimal getEjerDia() {
        return ejerDia;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @return the saldoDisponible
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @return the saldoIncluyeOp
     */
    public BigDecimal getSaldoIncluyeOp() {
        return saldoIncluyeOp;
    }

    /**
     * @return the saldoInicialDia
     */
    public BigDecimal getSaldoInicialDia() {
        return saldoInicialDia;
    }

    /**
     * @return the saldoReporto
     */
    public BigDecimal getSaldoReporto() {
        return saldoReporto;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the valoresPrestados
     */
    public BigDecimal getValoresPrestados() {
        return valoresPrestados;
    }

    /**
     * @return the valEnPrestamo
     */
    public BigDecimal getValEnPrestamo() {
        return valEnPrestamo;
    }

    /**
     * @return the saldoTesoreria
     */
    public BigDecimal getSaldoTesoreria() {
        return saldoTesoreria;
    }

    /**
     * @return the areaTrabajo
     */
    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @return the emision
     */
    public EmisionPersistence getEmision() {
        return emision;
    }

    /**
     * @return the traspasos
     */
    public Set getTraspasos() {
        return traspasos;
    }

    /**
     * @return the agente
     */
    public AgentePersistence getAgente() {
        return agente;
    }

    /**
     * @return the instrumento
     */
    public InstrumentoVO getInstrumento() {
        return instrumento;
    }

    /**
     * @return the vendedorMdintrans
     */
    public Set getVendedorMdintrans() {
        return vendedorMdintrans;
    }

    /**
     * @return the compradorMdintrans
     */
    public Set getCompradorMdintrans() {
        return compradorMdintrans;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(EmisionPersistence emision) {
        this.emision = emision;
    }

    /**
     * @param agente the agente to set
     */
    public void setAgente(AgentePersistence agente) {
        this.agente = agente;
    }

    /**
     * @param instrumento the instrumento to set
     */
    public void setInstrumento(InstrumentoVO instrumento) {
        this.instrumento = instrumento;
    }

    /**
     * @param cuenta
     */
    public void setCuenta(String cuenta) {
		valorPk.setCuenta(cuenta);
	}

	/**
	 * @param folioInst
	 */
	public void setFolioInst(String folioInst) {
		valorPk.setFolioInst(folioInst);
	}

	/**
	 * @param idInst
	 */
	public void setIdInst(String idInst) {
		valorPk.setIdInst(idInst);
	}

	/**
	 * @param traspasos
	 */
	public void setTraspasos(Set traspasos) {
		this.traspasos = traspasos;
	}

	/**
	 * @param valorPk
	 */
	public void setValorPk(ValorPK valorPk) {
		this.valorPk = valorPk;
	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	
	/**
	 * @param areaTrabajo
	 */
	public void setAreaTrabajo(String areaTrabajo) {
		this.areaTrabajo = areaTrabajo;
	}
	
	/**
	 * @param cantidadAcciones
	 */
	public void setCantidadAcciones(BigDecimal cantidadAcciones) {
		this.cantidadAcciones = cantidadAcciones;
	}
	
	/**
	 * @param ejerDia
	 */
	public void setEjerDia(BigDecimal ejerDia) {
		this.ejerDia = ejerDia;
	}
	
	/**
	 * @param emisionExtr
	 */
	public void setEmisionExtr(String emisionExtr) {
		this.emisionExtr = emisionExtr;
	}
	
	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	/**
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * @param nombreUsuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}
	
	/**
	 * @param saldoIncluyeOp
	 */
	public void setSaldoIncluyeOp(BigDecimal saldoIncluyeOp) {
		this.saldoIncluyeOp = saldoIncluyeOp;
	}
	
	/**
	 * @param saldoInicialDia
	 */
	public void setSaldoInicialDia(BigDecimal saldoInicialDia) {
		this.saldoInicialDia = saldoInicialDia;
	}
	
	/**
	 * @param saldoReporto
	 */
	public void setSaldoReporto(BigDecimal saldoReporto) {
		this.saldoReporto = saldoReporto;
	}
	
	/**
	 * @param saldoTesoreria
	 */
	public void setSaldoTesoreria(BigDecimal saldoTesoreria) {
		this.saldoTesoreria = saldoTesoreria;
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
	 * @param valEnPrestamo
	 */
	public void setValEnPrestamo(BigDecimal valEnPrestamo) {
		this.valEnPrestamo = valEnPrestamo;
	}
	
	/**
	 * @param valoresPrestados
	 */
	public void setValoresPrestados(BigDecimal valoresPrestados) {
		this.valoresPrestados = valoresPrestados;
	}
	
    /**
     * @param compradorMdintrans
     */
    public void setCompradorMdintrans(Set compradorMdintrans) {
        this.compradorMdintrans = compradorMdintrans;
    }

    /**
     * @param vendedorMdintrans
     */
    public void setVendedorMdintrans(Set vendedorMdintrans) {
        this.vendedorMdintrans = vendedorMdintrans;
    }
    
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return getValorPk().toString();
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.toString().hashCode();        
    }

}
