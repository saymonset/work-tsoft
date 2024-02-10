/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Clase usada en la capa de servicios para representaci&oacute;n de la informaci&oacute;n
 * del detalle de una instituci&oacute;n.
 */
public class InstitucionesVo implements Serializable {

    /** serialVersionUID */
	private static final long serialVersionUID = 20L;

    /**Id de la Instituci&oacute;n*/
	private Integer id;
    
    /**Nombre de la Instituci&oacute;n*/
    private String nombre;
    
    /**Raz&oacute;n Social de la Instituci&oacute;n*/
    private String razonSocial;    
    
    /**Folio de la Instituci&oacute;n*/
    private String folio;
    
    /**Estatus para la Instituci&oacute;n*/
    private Integer idEstadoInstitucion;    
    
    /** Fecha de alta de la institucion */
    private Date fechaAlta = null;
    //Antes fechaCreacion
    
    /** Fecha de baja de la institucion */
    private Date fechaBaja = null;
    //Antes fechaUltModificacion;
    
    /**Domicilio Fiscal*/
    private String domicilioFiscal;
    
    /**C&oacute;digo Postal*/
    private Integer codigoPostal;
    
    /**Registro Federal de Contribuyentes*/
    private String rfc;
    
    /**bandera para Instituciones que operan T&iacute;tulo Global*/
    private Boolean operaTituloGlobal;   
    
    /**Id del Pa&iacute;s*/
	private Integer idPais;
    
    /**Descripci&oacute;n del Pa&iacute;s*/
    private String paisDesc;    
    
    /**Id de la Entidad Federativa*/
    private Integer idEntidadFederativa;
    
    /**Descripci&oacute;n del Estado*/
    private String estadoDesc;
    
    /**Id de la Ciudad*/
	private Integer idCiudad;
    
    /**Descripci&oacute;n de la Ciudad*/
	private String ciudadDesc;
    
    /**Id del Tipo de Instituci&oacute;n*/
    private Integer idTipoInstitucion;  
    
    /**Id del Tipo de Instituci&oacute;n*/
	private String tipoInstitucionDesc;
    
    /**Lista de Perfiles asociados a la Instituci&oacute;n*/
    @SuppressWarnings("unchecked")
	private List perfiles;
    
    /**Clave del Tipo de Instituci&oacute;n*/
    private String claveTipoInstitucion;
    
	/** Clave CASFIM */
	private String claveCasfim;
    
    /** BIC */
    private String bic = null;
    
    /** CLABE */
    private String clabe = null;
    
    /** Indica si la institucion es interna */
    private boolean interna;
    
    /*
     * Se agregan los campos nacionalidad y configuracion de institucion
     * 12-11-2008
     */
    
    /** Nacionalidad de la instituci&oacute;n */
    private String nacionalidad = null;
    
    /** Id de la configuracion de la instituci&oacute;n */
    private Integer configuracionInstitucion = null;
    
    /** Descripcion de la configuracion de la instituci&oacute;n */
    private String descripcionConfiguracion = null;
    
    /** SPEI */
    private Integer claveSpei;

    /** SPEI del Beneficiario */
    private Integer claveSpeiBeneficiario;

    /** RFC del Beneficiario */
    private String rfcBeneficiario;

    /** Nombre del Beneficiario */
    private String nombreBeneficiario;

    /** Cuenta CLABE del Beneficiario */
    private String clabeBeneficiario;
    
    /** Bandera que indica si la institucion opera SIAC = 1, o NO opera SIAC = 0 */
    private Boolean operaSiac;
    
    /**Constructor por omisi&oacute;n*/
    public InstitucionesVo() {
        super();
	}

    /**
     * Constructor con p&aacute;rametros
     * @param id
     * @param nombre
     * @param razonSocial
     * @param folio
     * @param fechaAlta
     * @param fechaBaja
     * @param domicilioFiscal
     * @param codigoPostal
     * @param rfc
     * @param operaTituloGlobal
     * @param idPais
     * @param paisDesc
     * @param idEntidadFederativa
     * @param entidadFederativaDesc
     * @param estadoDesc
     * @param idCiudad
     * @param nombreCiudad
     * @param ciudadDesc
     * @param idTipoInstitucion
     * @param tipoInstitucionDesc
     * @param claveTipoInstitucion
     * @param claveClasfim
     * @param bic
     * @param nacionalidad
     * @param idConfiguracion
     * @param descConfiguracion 
     * @param activa
     * @param configuracionDesc
     */
    public InstitucionesVo(Integer id, String nombre, String razonSocial, String folio, Integer idEstadoInstitucion,
		Date fechaAlta, Date fechaBaja, String domicilioFiscal, Integer codigoPostal, String rfc, Boolean operaTituloGlobal,
		Integer idPais, String paisDesc, Integer idEntidadFederativa, String entidadFederativaDesc, String estadoDesc,
		Integer idCiudad, String nombreCiudad, String ciudadDesc, Integer idTipoInstitucion, String tipoInstitucionDesc,
		String claveTipoInstitucion, String claveClasfim, String bic, String nacionalidad, Integer idConfiguracion, String clabe,
		Boolean interna, String descConfiguracion, Integer claveSpei, Integer claveSpeiBeneficiario, 
                String rfcBeneficiario, String nombreBeneficiario, String clabeBeneficiario, Boolean operaSiac) {
    	this(id, nombre, razonSocial, folio,
                idEstadoInstitucion, fechaAlta, fechaBaja,
                domicilioFiscal, codigoPostal, rfc,
                operaTituloGlobal, idPais, paisDesc,
                idEntidadFederativa, entidadFederativaDesc, estadoDesc,
                idCiudad, nombreCiudad, ciudadDesc,
                idTipoInstitucion, tipoInstitucionDesc,
                claveTipoInstitucion, claveClasfim,
                bic, nacionalidad, idConfiguracion);
    	this.clabe = clabe;
    	this.interna = interna != null ? interna.booleanValue() : false;
        this.descripcionConfiguracion = descConfiguracion;
        this.claveSpei = claveSpei;
        this.claveSpeiBeneficiario = claveSpeiBeneficiario;
        this.rfcBeneficiario = rfcBeneficiario;
        this.nombreBeneficiario = nombreBeneficiario;
        this.clabeBeneficiario = clabeBeneficiario;
        this.operaSiac = operaSiac;
    }

    /**
     * Constructor con p&aacute;rametros
     * @param id
     * @param nombre
     * @param razonSocial
     * @param folio
     * @param activa
     * @param fechaAlta
     * @param fechaBaja
     * @param domicilioFiscal
     * @param codigoPostal
     * @param rfc
     * @param operaTituloGlobal
     * @param idPais
     * @param paisDesc
     * @param idEntidadFederativa
     * @param entidadFederativaDesc
     * @param estadoDesc
     * @param idCiudad
     * @param nombreCiudad
     * @param ciudadDesc
     * @param idTipoInstitucion
     * @param tipoInstitucionDesc
     * @param claveTipoInstitucion
     * @param claveClasfim
     * @param bic
     * @param nacionalidad
     * @param idConfiguracion
     */
    public InstitucionesVo(Integer id, String nombre, String razonSocial, String folio,
            Integer idEstadoInstitucion, Date fechaAlta, Date fechaBaja,
            String domicilioFiscal, Integer codigoPostal, String rfc,
            Boolean operaTituloGlobal, Integer idPais, String paisDesc,
            Integer idEntidadFederativa, String entidadFederativaDesc, String estadoDesc,
            Integer idCiudad, String nombreCiudad, String ciudadDesc,
            Integer idTipoInstitucion, String tipoInstitucionDesc,
            String claveTipoInstitucion, String claveClasfim,
            String bic, String nacionalidad, Integer idConfiguracion) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.folio = folio;
        this.idEstadoInstitucion = idEstadoInstitucion;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.domicilioFiscal = domicilioFiscal;
        this.codigoPostal = codigoPostal;
        this.rfc = rfc;
        this.operaTituloGlobal = operaTituloGlobal;
        this.idPais = idPais;
        this.paisDesc = paisDesc;
        this.idEntidadFederativa = idEntidadFederativa;
        
        if(idEntidadFederativa == null){
            this.estadoDesc = estadoDesc;
        } else{
            this.estadoDesc = entidadFederativaDesc;            
        }
        this.idCiudad = idCiudad;
        if(idCiudad == null){
            this.ciudadDesc = ciudadDesc;
        } else{
            this.ciudadDesc = nombreCiudad;
        }
        this.idTipoInstitucion = idTipoInstitucion;
        this.tipoInstitucionDesc = tipoInstitucionDesc;
        this.claveTipoInstitucion = claveTipoInstitucion;
        this.claveCasfim = claveClasfim;
        
        /* Se agregan estos campos al constructor para la consulta Instituciones.findInstitucionesVoById */
        this.bic = bic;
        this.nacionalidad = nacionalidad;
        this.configuracionInstitucion = idConfiguracion;
        
    }
    
    /**
     * Constructor con p&aacute;rametros
     * @param id     
     */
    public InstitucionesVo(Integer id) {
        super();
        this.id = id;         
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
     * @return Regresa el valor del p&aacute;rametro folio.
     */
	public String getFolio() {
		return folio;
	}

	/**
     * Establece el valor del atributo folio.
     * 
     * @param folio -El valor del atributo folio.
     */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
     * @return Regresa el valor del p&aacute;rametro id.
     */
	public Integer getId() {
		return id;
	}

	/**
     * Establece el valor del atributo id.
     * 
     * @param id -El valor del atributo id.
     */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * @return Regresa el valor del p&aacute;rametro perfiles.
     */
	@SuppressWarnings("unchecked")
	public List getPerfiles() {
		return perfiles;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro Lista de Perfiles como
     *         <code>String</code>.
     */
	/*public String getPerfilesString() {
		final StringBuffer salida = new StringBuffer();
		if (perfiles != null && !perfiles.isEmpty()) {
            int perfilesSize = perfiles.size();
            ItemVo current = null;
			for (int i = 0; i < perfilesSize; i++) {
				current =(ItemVo)perfiles.get(i);
				salida.append(current.getDescripcion());
                if(i != (perfiles.size() - 1) ){
                    salida.append(", ");
                }
			}
		}
		return salida.toString();
	}*/

	/**
     * Establece el valor del atributo perfiles.
     * 
     * @param perfiles -El valor del atributo perfiles.
     */
	@SuppressWarnings("unchecked")
	public void setPerfiles(List perfiles) {
		this.perfiles = perfiles;
	}

	/**
     * @return Regresa el valor del p&aacute;rametro perfiles.
     */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
     * Establece el valor del atributo razonSocial.
     * 
     * @param razonSocial -El valor del atributo razonSocial.
     */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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
     * @return Regresa el valor del p&aacute;rametro nombre.
     */
	public String getNombre() {
		return nombre;
	}

	/**
     * Establece el valor del atributo nombre.
     * 
     * @param nombre -El valor del atributo nombre.
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
     * @return Regresa el valor del p&aacute;rametro operaTituloGlobal.
     */
	public Boolean getOperaTituloGlobal() {
		return operaTituloGlobal;
	}


	/**
     * Establece el valor del atributo operaTituloGlobal.
     * 
     * @param operaTituloGlobal -El valor del atributo operaTituloGlobal.
     */
	public void setOperaTituloGlobal(Boolean operaTituloGlobal) {
		this.operaTituloGlobal = operaTituloGlobal;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro idEntidadFederativa.
     */
	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

    /**
     * Establece el valor del atributo idEntidadFederativa.
     * 
     * @param idEntidadFederativa -El valor del atributo idEntidadFederativa.
     */
	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro idPais.
     */
	public Integer getIdPais() {
		return idPais;
	}

    /**
     * Establece el valor del atributo idPais.
     * 
     * @param idPais -El valor del atributo idPais.
     */
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro estadoDesc.
     */
	public String getEstadoDesc() {
		return estadoDesc;
	}

    /**
     * Establece el valor del atributo estadoDesc.
     * 
     * @param estadoDesc -El valor del atributo estadoDesc.
     */
	public void setEstadoDesc(String estadoDesc) {
		this.estadoDesc = estadoDesc;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro ciudadDesc.
     */
	public String getCiudadDesc() {
		return ciudadDesc;
	}

    /**
     * Establece el valor del atributo ciudadDesc.
     * 
     * @param ciudadDesc -El valor del atributo ciudadDesc.
     */
	public void setCiudadDesc(String ciudadDesc) {
		this.ciudadDesc = ciudadDesc;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro idCiudad.
     */
	public Integer getIdCiudad() {
		return idCiudad;
	}

    /**
     * Establece el valor del atributo idCiudad.
     * 
     * @param idCiudad -El valor del atributo idCiudad.
     */
	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro idTipoInstitucion.
     */
	public Integer getIdTipoInstitucion() {
		return idTipoInstitucion;
	}

    /**
     * Establece el valor del atributo idTipoInstitucion.
     * 
     * @param idTipoInstitucion -El valor del atributo idTipoInstitucion.
     */
	public void setIdTipoInstitucion(Integer idTipoInstitucion) {
		this.idTipoInstitucion = idTipoInstitucion;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro tipoInstitucionDesc.
     */
	public String getTipoInstitucionDesc() {
		return tipoInstitucionDesc;
	}

    /**
     * Establece el valor del atributo tipoInstitucionDesc.
     * 
     * @param tipoInstitucionDesc -El valor del atributo tipoInstitucionDesc.
     */
	public void setTipoInstitucionDesc(String tipoInstitucionDesc) {
		this.tipoInstitucionDesc = tipoInstitucionDesc;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro paisDesc.
     */
	public String getPaisDesc() {
		return paisDesc;
	}

    /**
     * Establece el valor del atributo paisDesc.
     * 
     * @param paisDesc -El valor del atributo paisDesc.
     */
	public void setPaisDesc(String paisDesc) {
		this.paisDesc = paisDesc;
	}

    /**
     * @return Regresa el valor del p&aacute;rametro activa.
     */
    public Integer getIdEstadoInstitucion(){
        return idEstadoInstitucion;
    }

    /**
     * Establece el valor del atributo activa.
     * 
     * @param activa -El valor del atributo activa.
     */
    public void setIdEstadoInstitucion(Integer idEstadoInstitucion){
        this.idEstadoInstitucion = idEstadoInstitucion;
    }

    /**
     * @return Regresa el atributo claveTipoInstitucion.
     */
    public String getClaveTipoInstitucion() {
        return claveTipoInstitucion;
    }

    /**
     * Establece el valor del atributo claveTipoInstitucion.
     * 
     * @param claveTipoInstitucion
     *           	El valor del atributo claveTipoInstitucion.
     */
    public void setClaveTipoInstitucion(String claveTipoInstitucion) {
        this.claveTipoInstitucion = claveTipoInstitucion;
    }
    /**
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
	 * Obtiene el atributo configuracionInstitucion
	 *
	 * @return El atrubuto configuracionInstitucion
	 */
	public Integer getConfiguracionInstitucion() {
		return configuracionInstitucion;
	}

	/**
	 * Establece la propiedad configuracionInstitucion
	 *
	 * @param configuracionInstitucion el campo configuracionInstitucion a establecer
	 */
	public void setConfiguracionInstitucion(Integer configuracionInstitucion) {
		this.configuracionInstitucion = configuracionInstitucion;
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

	public String getDescripcionConfiguracion() {
	    return descripcionConfiguracion;
	}

	public void setDescripcionConfiguracion(String descripcionConfiguracion) {
	    this.descripcionConfiguracion = descripcionConfiguracion;
	}

	/**
	 * Obtiene el valor del atributo clabe
	 *
	 * @return el valor del atributo clabe
	 */
	public String getClabe() {
		return clabe;
	}

	/**
	 * Establece el valor del atributo clabe
	 *
	 * @param clabe el valor del atributo clabe a establecer
	 */
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public boolean isInterna() {
	    return interna;
	}

	public void setInterna(boolean interna) {
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

}
