/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cata&acute;logo de sectores segu&acute;n ha sido definido por Banco de Me&acute;xico e INDEVAL.
 * 
 * C_SECTOR
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_SECTOR")
public class Sector implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del sector.
	 */
	@Id
	@Column(name = "ID_SECTOR")
	private BigInteger idSector;
//	/**
//	 * Clave del sector.
//	 */
//	@Column(name = "CLAVE_SECTOR")
//	private String claveSector;
	/**
	 * Descripci&oacute;n del sector.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Identificador secuencial para el Sector.
	 * @return BigInteger
	 */
	public BigInteger getIdSector() {
		return idSector;
	}

	/**
	 * Identificador secuencial para el Sector.
	 * @param idSector
	 */
	public void setIdSector(BigInteger idSector) {
		this.idSector = idSector;
	}

//	/**
//	 * Nu&acute;mero de identificacio&acute;n del sector.
//	 * @return String
//	 */
//	public String getClaveSector() {
//		return claveSector;
//	}
//
//	/**
//	 * Nu&acute;mero de identificacio&acute;n del sector.
//	 * @param claveSector
//	 */
//	public void setClaveSector(String claveSector) {
//		this.claveSector = claveSector;
//	}

	/**
	 * Descripcio&acute;n del sector.
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripcio&acute;n del sector.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}