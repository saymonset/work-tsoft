package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
@Entity
@Table(name = "C_CUSTODIO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_ID_CUSTODIO", allocationSize = 1, initialValue = 1)
public class Custodio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//ID_CUSTODIO
	private String nombreCorto;//NOMBRE_CORTO
	private String descripcion;//DESCRIPCION
	private CatBic idCatBic;//ID_CATBIC
	private String codigoBanco;//CODIGO_BANCO
	private Integer participante;//PARTICIPANTE
    private Integer factorCalculado;//DIVISOR_FACTOR_CALCULADO
	public Custodio() {
		
	}
	
	
	/**
	 * @param id
	 * @param nombreCorto
	 * @param descripcion
	 * @param idCatBic
	 */
	public Custodio(Integer id, String nombreCorto, String descripcion,
			CatBic idCatBic) {
		this.id = id;
		this.nombreCorto = nombreCorto;
		this.descripcion = descripcion;
		this.idCatBic = idCatBic;
	}


	/**
	 * @return the id
	 */
	@Id		
	@Column(name = "ID_CUSTODIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the nombreCorto
	 */
	@Column(name = "NOMBRE_CORTO")
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION")
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
	 * @return the idCatBic
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATBIC", unique = false, nullable = true)
	public CatBic getIdCatBic() {
		return idCatBic;
	}


	/**
	 * @param idCatBic the idCatBic to set
	 */
	public void setIdCatBic(CatBic idCatBic) {
		this.idCatBic = idCatBic;
	}
	
	
	/**
	 * @return the codigoBanco
	 */
	@Column(name = "CODIGO_BANCO")
	public String getCodigoBanco() {
		return codigoBanco;
	}


	/**
	 * @param codigoBanco the codigoBanco to set
	 */
	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}


	/**
	 * @return the participante
	 */
	@Column(name = "PARTICIPANTE")
	public Integer getParticipante() {
		return participante;
	}


	/**
	 * @param participante the participante to set
	 */
	public void setParticipante(Integer participante) {
		this.participante = participante;
	}

    /**
     * @return the factor calculado
     */
    @Column(name = "DIVISOR_FACTOR_CALCULADO")
	public Integer getFactorCalculado() {
        return factorCalculado;
    }

    /**
     * @param factorCalculado the factorCalculado to set
     */
    public void setFactorCalculado(Integer factorCalculado) {
        this.factorCalculado = factorCalculado;
    }


    @Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof Custodio)) {return false;}

		Custodio other=(Custodio)obj;
		
		boolean isEquals = false;

		isEquals=EqualsBuilder.reflectionEquals(this, other);
		
		return isEquals;
	}
}
