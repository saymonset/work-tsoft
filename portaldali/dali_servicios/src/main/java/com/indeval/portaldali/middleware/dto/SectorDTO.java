/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;



/**
 * Cata&acute;logo de sectores segu&acute;n ha sido definido por Banco de Me&acute;xico e INDEVAL.
 * 
 * C_SECTOR
 * 
 * @author FERNANDO VAZQUEZ ULLOA
 * @version 1.0
 */


public class SectorDTO implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del sector.
	 */
	
	
	private BigInteger idSector;
//	/**
//	 * Clave del sector.
//	 */
//	
//	private String claveSector;
	/**
	 * Descripci&oacute;n del sector.
	 */
	
	private String descripcion;

	/**
	 * Identificador secuencial para el Sector.
	 * 
	 */
	public BigInteger getIdSector() {
		return idSector;
	}

	/**
	 * Identificador secuencial para el Sector.
	 * 
	 */
	public void setIdSector(BigInteger idSector) {
		this.idSector = idSector;
	}

//	/**
//	 * Nu&acute;mero de identificacio&acute;n del sector.
//	 * 
//	 */
//	public String getClaveSector() {
//		return claveSector;
//	}
//
//	/**
//	 * Nu&acute;mero de identificacio&acute;n del sector.
//	 * 
//	 */
//	public void setClaveSector(String claveSector) {
//		this.claveSector = claveSector;
//	}

	/**
	 * Descripcio&acute;n del sector.
	 * 
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripcio&acute;n del sector.
	 * 
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}