/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.indeval.portaldali.persistence.modelo.TipoInstitucion;

/**
 * Clase que representa un registro de la tabla 'C_INSTITUCION'.
 *  
 */
@Entity
@Table(name = "C_INSTITUCION")
@SequenceGenerator(name = "foliador", sequenceName = "C_INSTITUCION_ID_SEQ", allocationSize = 1, initialValue = 1)
@Cache (usage=CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name="Instituciones.findByTipoYFolio", 
            query="from Instituciones it inner join fetch it.tipoInstitucion ti where ti.idTipoInstitucion = :idTipoInstitucion and trim(it.folio) = trim(:folio)")
public class Instituciones implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    //private int hashValue = 0;

    /**
     * Propiedad que representa el id de la instituci&oacute;n
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_INSTITUCION", unique = true, nullable = false)
    private Integer idInstitucion;

    /**
     * Propiedad que representa el id de la instituci&oacute;n
     */
    /*@Column(name = "ID_TIPO_INSTITUCION", unique = true, nullable = false, insert=false, update=false)
    private Integer idTipoInstitucion;*/

    /**
     * Propiedad que representa la asociacion con tipo de institucion
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_INSTITUCION", nullable = false)
    private TipoInstitucion tipoInstitucion;

    /**
     * Propiedad que representa la descripci&oacute;n de la instituci&oacute;n
     */
    @Column(name = "NOMBRE_CORTO", unique = false, nullable = false)
    private String nombre;

    /**
     * Propiedad que representa la raz&oacute;n social de la instituci&oacute;n
     */
    @Column(name = "RAZON_SOCIAL", unique = false, nullable = false)
    private String razonSocial;

    /**
     * Propiedad que representa el folio de la institucion
     */
    @Column(name = "FOLIO_INSTITUCION", unique = false, nullable = false)
    private String folio;

    /**
     * Propiedad que representa el estatus de la institucion
     */
    @Column(name = "ID_ESTADO_INSTITUCION", unique = false, nullable = false)
    private Integer idEstadoInstitucion;

    /**
     * Fecha de alta de la institucion
     */
    @Column(name = "FECHA_ALTA", unique = false, nullable = false)
    private Date fechaAlta = null;

    /**
     * Fecha de alta de la institucion
     */
    @Column(name = "FECHA_CREACION", unique = false, nullable = false)
    private Date fechaCreacion;

    /**
     * Fecha de baja de la institucion
     */
    @Column(name = "FECHA_BAJA", unique = false, nullable = false)
    private Date fechaBaja = null;
    
    /**
     * Fecha de ultima modificacion de la institucion
     */
    @Column(name = "FECHA_ULT_MODIFICACION", unique = false, nullable = false)
    private Date fechaUltModificacion;
    
    /**
     * Propiedad que representa el domicilio fiscal de la institucion
     */
    @Column(name = "DOMICILIO_FISCAL", unique = false, nullable = false)
    private String domicilioFiscal;

    /**
     * Propiedad que representa el codigo postal de la institucion
     */
    @Column(name = "CODIGO_POSTAL", unique = false, nullable = false)
    private Integer codigoPostal;

    /**
     * Propiedad que representa el rfc de la institucion
     */
    @Column(name = "RFC", unique = false, nullable = false)
    private String rfc;

    /**
     * Propiedad que representa el tituloGlobal de la institucion
     */
    @Column(name = "PROCESA_TITULO_GLOBAL", unique = false, nullable = false)
    private Boolean opTituloGlobal;

    /**
     * Propiedad que representa el identificador del entidadFederativa de la institucion
     */
    @Column(name = "DESC_ESTADO", unique = false, nullable = false)
    private String descEstado;

    /**
     * Propiedad que representa el identificador de la cuidad de la institucion
     */
    @Column(name = "DESC_CIUDAD", unique = false, nullable = false)
    private String descCiudad;
    
    /**
     * Propiedad que representa el identificador de la cuidad de la institucion
     */
    @Column(name = "CLAVE_CASFIM", unique = false, nullable = false)
    private String claveCasfim;
    
    /**
     * Nacionalidad de la instituci&oacute;n
     */
    @Column(name = "NACIONALIDAD", unique = false, nullable = false)
    private String nacionalidad = null;
    
    /** 
     * BIC 
     */
    @Column(name = "BIC", unique = false, nullable = false)
    private String bic = null;
    
    /**
     * 
     */
    @Column(name = "INTERNA", unique = false, nullable = false)
    private Boolean interna;
      
    /**
     * 
     */
    @Column(name = "CLAVE_SPEI", unique = false, nullable = false)
    private Integer claveSpei;
    
    /**
     * 
     */
    @Column(name = "CLAVE_SPEI_BENEFICIARIO", unique = false, nullable = false)
    private Integer claveSpeiBeneficiario;
    
    /**
     * 
     */
    @Column(name = "RFC_BENEFICIARIO", unique = false, nullable = false)
    private String rfcBeneficiario;
    
    /**
     * 
     */
    @Column(name = "NOMBRE_BENEFICIARIO", unique = false, nullable = false)
    private String nombreBeneficiario;
    
    /**
     * 
     */
    @Column(name = "CUENTA_CLABE_BENEFICIARIO", unique = false, nullable = false)
    private String clabeBeneficiario;
    
    /**
     * 
     */
    @Column(name = "OPERA_SIAC", unique = false, nullable = false)
    private Boolean operaSiac;
    
    /**
     * 
     */
    @Column(name = "ESTATUS_INSTITUCION", unique = false, nullable = false)
    private Integer estatusInstitucion;
    
    /**
     * 
     */
    @Column(name = "ORIGEN", unique = false, nullable = false)
    private String origen;
    
    
    /**
     * Simple constructor of Instituciones instances.
     */
    public Instituciones(){
        super();
    }

    /**
     * Constructor of Instituciones instances given a simple primary key.
     * @param idInstitucion
     */
    public Instituciones(Integer idInstitucion){
        this.idInstitucion = idInstitucion;
    }
    
    /**
     * @return Regresa el valor del p&aacute;rametro nombre.
     */
   public String getNombre(){
        return nombre;
    }
   
   /**
     * Establece el valor del atributo nombre.
     * 
     * @param nombre -El valor del atributo nombre.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * @return Regresa el valor del p&aacute;rametro folio.
     */
    public String getFolio(){
        return folio;
    }
    
    /**
     * Establece el valor del atributo folio.
     * 
     * @param folio -El valor del atributo folio.
     */
    public void setFolio(String folio){
        this.folio = folio;
    }
    
    /**
     * @return Regresa el valor del p&aacute;rametro idInstitucion.
     */
    public Integer getIdInstitucion(){
        return idInstitucion;
    }
    
    /**
     * Establece el valor del atributo idInstitucion.
     * 
     * @param idInstitucion -El valor del atributo idInstitucion.
     */
    public void setIdInstitucion(Integer idInstitucion){
        //this.hashValue = 0;
        this.idInstitucion = idInstitucion;
    }

    public TipoInstitucion getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(TipoInstitucion tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    /**
     * @return Regresa el valor del p&aacute;rametro razonSocial.
     */
    public String getRazonSocial(){
        return razonSocial;
    }
    
    /**
     * Establece el valor del atributo razonSocial.
     * 
     * @param razonSocial -El valor del atributo razonSocial.
     */
    public void setRazonSocial(String razonSocial){
        this.razonSocial = razonSocial;
    }
    
    /**
     * @return Regresa el valor del p&aacute;rametro codigoPostal.
     */
	public Integer getCodigoPostal() {
		return codigoPostal;
	}
	
    /**
     * Establece el valor del atributo codigoPostal.
     * 
     * @param codigoPostal -El valor del atributo codigoPostal.
     */
	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro descCiudad.
     */
	public String getDescCiudad() {
		return descCiudad;
	}
	
    /**
     * Establece el valor del atributo descCiudad.
     * 
     * @param descCiudad -El valor del atributo descCiudad.
     */
	public void setDescCiudad(String descCiudad) {
		this.descCiudad = descCiudad;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro descEstado.
     */
	public String getDescEstado() {
		return descEstado;
	}
	
    /**
     * Establece el valor del atributo descEstado.
     * 
     * @param descEstado -El valor del atributo descEstado.
     */
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro domicilioFiscal.
     */
	public String getDomicilioFiscal() {
		return domicilioFiscal;
	}
	
    /**
     * Establece el valor del atributo domicilioFiscal.
     * 
     * @param domicilioFiscal -El valor del atributo domicilioFiscal.
     */
	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro idEstadoInstitucion.
     */
	public Integer getIdEstadoInstitucion() {
		return idEstadoInstitucion;
	}
	
    /**
     * Establece el valor del atributo idEstadoInstitucion.
     * 
     * @param idEstadoInstitucion -El valor del atributo idEstadoInstitucion.
     */
	public void setIdEstadoInstitucion(Integer idEstadoInstitucion) {
		this.idEstadoInstitucion = idEstadoInstitucion;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro opTituloGlobal.
     */
	public Boolean isOpTituloGlobal() {
		return opTituloGlobal;
	}
	
    /**
     * Establece el valor del atributo opTituloGlobal.
     * 
     * @param opTituloGlobal -El valor del atributo opTituloGlobal.
     */
	public void setOpTituloGlobal(Boolean opTituloGlobal) {
		this.opTituloGlobal = opTituloGlobal;
	}
	
    /**
     * @return Regresa el valor del p&aacute;rametro rfc.
     */
	public String getRfc() {
		return rfc;
	}
	
    /**
     * Establece el valor del atributo rfc.
     * 
     * @param rfc -El valor del atributo rfc.
     */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs){
        if (this == rhs){
            return true;
        }
        if (!(rhs instanceof Instituciones)){
            return false;
        }
        Instituciones that = (Instituciones) rhs;
        if (this.getIdInstitucion() == null || that.getIdInstitucion() == null){
            return false;
        }
        return (this.getIdInstitucion().equals(that.getIdInstitucion()));
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    /*public int hashCode(){
        if (this.hashValue == 0){
            int result = 17;
            int edIdInstitucionValue = (this.getIdInstitucion() == null) ? 0
                    : this.getIdInstitucion().hashCode();
            result = result * 37 + edIdInstitucionValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }*/
    
    /**
     * @return Regresa el valor del p&aacute;rametro cuentasSet.
     */
	/*public Set getCuentasSet() {
		return cuentasSet;
	}*/
	
    /**
     * Establece el valor del atributo cuentasSet.
     * 
     * @param cuentasSet -El valor del atributo cuentasSet.
     */
	/*public void setCuentasSet(Set cuentasSet) {
		this.cuentasSet = cuentasSet;
	}*/

	/**
	 * Obtiene el valor del atributo claveCasfim.
	 * 
	 * @return Regresa el atributo claveCasfim.
	 */
	public String getClaveCasfim() {
		return claveCasfim;
	}
	/**
	 * Establece el valor del atributo claveCasfim.
	 * 
	 * @param claveCasfim
	 *           	El valor del atributo claveCasfim.
	 */
	public void setClaveCasfim(String claveCasfim) {
		this.claveCasfim = claveCasfim;
	}

	/**
	 * Obtiene el atributo nacionalidad
	 *
	 * @return El atrubuto nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * Establece la propiedad nacionalidad
	 *
	 * @param nacionalidad el campo nacionalidad a establecer
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * Obtiene el atributo opTituloGlobal
	 *
	 * @return El atrubuto opTituloGlobal
	 */
	public Boolean getOpTituloGlobal() {
		return opTituloGlobal;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Boolean getInterna() {
	    return interna;
	}

	public void setInterna(Boolean interna) {
	    this.interna = interna;
	}

        public String getClabeBeneficiario() {
            return clabeBeneficiario;
        }

        public void setClabeBeneficiario(String clabeBeneficiario) {
            this.clabeBeneficiario = clabeBeneficiario;
        }

        public Integer getClaveSpei() {
            return claveSpei;
        }

        public void setClaveSpei(Integer claveSpei) {
            this.claveSpei = claveSpei;
        }

        public Integer getClaveSpeiBeneficiario() {
            return claveSpeiBeneficiario;
        }

        public void setClaveSpeiBeneficiario(Integer claveSpeiBeneficiario) {
            this.claveSpeiBeneficiario = claveSpeiBeneficiario;
        }

        /*public int getHashValue() {
            return hashValue;
        }

        public void setHashValue(int hashValue) {
            this.hashValue = hashValue;
        }*/

        public String getNombreBeneficiario() {
            return nombreBeneficiario;
        }

        public void setNombreBeneficiario(String nombreBeneficiario) {
            this.nombreBeneficiario = nombreBeneficiario;
        }

        public String getRfcBeneficiario() {
            return rfcBeneficiario;
        }

        public void setRfcBeneficiario(String rfcBeneficiario) {
            this.rfcBeneficiario = rfcBeneficiario;
        }

    public Boolean getOperaSiac() {
        return operaSiac;
    }

    public void setOperaSiac(Boolean operaSiac) {
        this.operaSiac = operaSiac;
    }

    public Integer getEstatusInstitucion() {
        return estatusInstitucion;
    }

    public void setEstatusInstitucion(Integer estatusInstitucion) {
        this.estatusInstitucion = estatusInstitucion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
        
}
