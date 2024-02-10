/*
 * Copyright (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Entity
@Table(name = "C_SIC_EMISIONES", uniqueConstraints = @UniqueConstraint(columnNames = {"id_cuenta_nombrada", "id_emision" }))
@SequenceGenerator(name = "foliador", sequenceName = "ID_SIC_EMISIONES_SEQ", allocationSize = 1, initialValue = 1)
public class SicEmision implements Serializable {
    
	/**
	 * Constante de serializaci&oacute;n por default 
	 */
	private static final long serialVersionUID = (long)1;

	/**
	 * Long
	 */
	private Long idSicEmisiones;

	/**
	 * CuentaNombrada
	 */
	private CuentaNombrada cuentaNombrada;

	/**
	 * Emision
	 */
	private Emision emision;

	/**
	 * String
	 */
	private String formaOper;

	/**
	 * Date
	 */
	private Date fechaHora;

	/**
	 * String
	 */
	private String aplicacion;
	
	/**
	 * CatBic 
	 */
	private CatBic catBic;
	
	/**
	 * String 
	 */
	private String estatusRegistro;

	/* Indica si el usuario de cambio de boveda puede asignar la emision */
    private boolean asigno;

    /* Indica la peticion del usuario de cambio de boveda para asignar la emision */
    private boolean asigna;

	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_sic_emisiones", unique = true, nullable = false)
	public Long getIdSicEmisiones() {
		return idSicEmisiones;
	}

	/**
	 * @return CuentaNombrada
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA", nullable = false, insertable = true, updatable = true)
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @return Emision
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_EMISION", nullable = false, insertable = true, updatable = true)
	public Emision getEmision() {
		return emision;
	}

	/**
	 * @return String
	 */
	@Column(name = "forma_oper", unique = false, nullable = false)
	public String getFormaOper() {
		return formaOper;
	}

	/**
	 * @return Date
	 */
	@Column(name = "fecha_hora", unique = false, nullable = false)
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @return String
	 */
	@Column(name = "aplicacion", unique = false, nullable = true)
	public String getAplicacion() {
		return aplicacion;
	}
	
	/**
	 * @return CatBic
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_catbic", nullable = true, insertable = true, updatable = true)
	public CatBic getCatBic() {
		return catBic;
	}

	/**
	 * @return String
	 */
	@Column(name = "estatus_registro", nullable = true, unique = false)
	public String getEstatusRegistro() {
		return estatusRegistro;
	}

    @Transient
    public boolean isAsigno() {
        return asigno;
    }

    @Transient
    public boolean isAsigna() {
        return asigna;
    }
	
	/**
	 * @param idSicEmisiones
	 */
	public void setIdSicEmisiones(Long idSicEmisiones) {
		this.idSicEmisiones = idSicEmisiones;
	}

	/**
	 * @param emision
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	/**
	 * @param cuentaNombrada
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @param formaOper
	 */
	public void setFormaOper(String formaOper) {
		this.formaOper = StringUtils.isNotEmpty(formaOper) ? formaOper.toUpperCase() : null;
	}

	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @param catBic
	 */
	public void setCatBic(CatBic catBic) {
		this.catBic = catBic;
	}

	/**
	 * @param estatusRegistro
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

    public void setAsigno(boolean asigno) {
        this.asigno = asigno;
    }

    public void setAsigna(boolean asigna) {
        this.asigna = asigna;
    }

}
