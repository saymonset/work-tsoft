package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
* The persistent class for the T_ASSET_MANAGER_INT_SIC database table.
*
*/
@Entity
@Table(name="T_ASSET_MANAGER_INT_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "ASSET_MANAGER_INT_SIC_SEQ", allocationSize = 1, initialValue = 1)
public class AssetManagerEmision implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5942726251650633333L;
   
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	 @Column(name = "ID_ASSET_MANAGER")
	 private Long id;
	 
	 
	 @Column(name = "ID_EMISION")
	 private Long idEmision;
	 
	 @Column(name = "DESCRIPCION")
     private String descripcion;
	 
	 @Column(name = "ISIN")
	 private String isin;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	

	/**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
     
	 
	
	 
	 
}
