/**
 * 
 */
package com.indeval.portaldali.middleware.formatosw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Forma General
 * 
 * @author Rafael Ibarra
 */
public class FormaGeneral {

    /** Id del Beneficiario qeu corresponda */
    public Long idBeneficiario;
    /** Campo que indica el nombre del beneficiario */
    public String nombre;
    /** Campo que indica el apellido paterno del beneficiario */
    public String apellidoPaterno;
    /** Campo que indica el apellido paterno del beneficiario */
    public String apellidoMaterno;
    /** Campo que indica el apellido paterno del beneficiario */
    public String razonSocial;
    /** Campo que indica el apellido paterno del beneficiario */
    public boolean personaFisica;
    /** Indica si esta activo o no */
    public boolean preAutoriza;
    /** Indica si esta activo o no */
    public boolean activar;
    /** Indica si esta activo o no */
    private boolean cancelar;
    /** Indica se elimna el registro o no */
    private boolean eliminar;
    /** Campo que indica el pais de incorporacion del beneficiario */
    public String pais;
    /** Tipo de Beneficiario */
    public Long idTipoBeneficiario;
    /** Descripcion del tipo */
    public String descTipoBeneficiario;
    /** Descripcion del status */
    public String descStatusBeneficiario;
    /** Custodio indicado por cuenta nombrada */
    public Long custodio;
    /** Institucion a la que pertenece el usuario */
    public Long idInstitucion;
    /** Fecha del registro */
    public Date fechaRegistro;
    /** Fecha del formato */
    public Date fechaFormato;
    /** Tipo de Formato */
    public String formato;
    /** Instituciones */
    public List<String> institucion;
    /** Cadena con las instituciones separadas por saltos de linea */
    private String instituciones;
    /** Porcentaje de Retencion */
    private Double porcentajeRetencion;
    /** Inentificador unico del sistema */
    private String uoi;
    /** Campo de la subcuenta asignada por le custodio */
    private String adp;

    private String activo;
    
    /** Vigencia del formato */
    public Date fechaVigenciaFormato;
    
    /** RFC **/
    public String rfcBeneficiario;

    /** Indica si la direccion Postal esta deshabilitada para no validarla */
    private boolean disabledDireccionPostal;
    
    /** beneficialOwnerId **/
    private String beneficialOwnerId;

    public FormaGeneral() {

    }

    public FormaGeneral(final Long custodio, final Long idTipoBeneficiario, final Long idInstitucion) {
        this.setCustodio(custodio);
        this.setIdTipoBeneficiario(idTipoBeneficiario);
        this.setIdInstitucion(idInstitucion);
    }

    public void construyeBOBasico(final Beneficiario retorno) {
        retorno.setTipoFormato(this.getFormato());
        retorno.setPersonaFisica(this.personaFisica);
        if (this.personaFisica) {
            retorno.setNombres(StringUtils.trim(this.nombre));
            retorno.setApellidoPaterno(StringUtils.trim(this.apellidoPaterno));
            retorno.setApellidoMaterno(StringUtils.trim(this.apellidoMaterno));
        } else {
            retorno.setRazonSocial(StringUtils.trim(this.razonSocial));
        }
        TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
        tipoBeneficiario.setIdTipoBeneficiario(this.idTipoBeneficiario);
        retorno.setTipoBeneficiario(tipoBeneficiario);
        retorno.setPaisIncorporacion(this.pais);
        retorno.setIdCuentaNombrada(this.custodio);
        // Institucion institucion = new Institucion();
        // institucion.setIdInstitucion(idInstitucion);
        // List<Institucion> lista = new ArrayList<Institucion>();
        // lista.add(institucion);
        // retorno.setInstitucion(lista);
        retorno.setFechaFormato(this.fechaFormato);

        if (this.activo != null) {
            if (this.activo.equals("1")) {
                retorno.setActivo(true);
            } else {
                if (this.activo.equals("0")) {
                    retorno.setActivo(false);
                } else {
                    retorno.setActivo(null);
                }
            }
        } else {
            retorno.setActivo(null);
        }
    }

    public Beneficiario construyeBO() {
        return null;
    }

    public Beneficiario construyeBO(final Beneficiario beneficiario) {
        return null;
    }

    public void construyeBean(final Beneficiario beneficiario, final boolean llenaCamposVacios) {
    	if(beneficiario.getFormatoW8BENE() != null){
        	this.rfcBeneficiario = beneficiario.getFormatoW8BENE().getForeingTIN();
    	}
        this.idBeneficiario = beneficiario.getIdBeneficiario();
        this.nombre = beneficiario.getNombres();
        this.apellidoPaterno = beneficiario.getApellidoPaterno();
        this.apellidoMaterno = beneficiario.getApellidoMaterno();
        this.razonSocial = beneficiario.getRazonSocial();
        this.personaFisica =
                beneficiario.getPersonaFisica() == null ? false : beneficiario.getPersonaFisica();
        this.fechaFormato = beneficiario.getFechaFormato();
        this.fechaRegistro = beneficiario.getFechaRegistro();
        this.pais = beneficiario.getPaisIncorporacion();
        this.formato = beneficiario.getTipoFormato();
        this.idTipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
        this.custodio = beneficiario.getIdCuentaNombrada();
        this.descStatusBeneficiario = beneficiario.getStatusBenef().getDescStatusBenef();
        if (beneficiario.getInstitucion() != null && !beneficiario.getInstitucion().isEmpty()) {
            this.institucion = new ArrayList<String>();
            for (Institucion inst : beneficiario.getInstitucion()) {
                this.institucion.add(inst.getClaveInstitucion() + " - " + inst.getNombreCorto());
            }
        }
        this.instituciones = beneficiario.getNombresInstituciones();
        this.porcentajeRetencion = beneficiario.getPorcentajeRetencion();
        this.uoi = beneficiario.getUoiNumber();
        //beneficialOwnerId
        this.beneficialOwnerId =  StringUtils.isNotBlank(beneficiario.getBeneficialOwnerId()) ? beneficiario.getBeneficialOwnerId() : "---";
        if (llenaCamposVacios) {
            this.adp =
                    StringUtils.isNotBlank(beneficiario.getAdp()) ? beneficiario.getAdp() : "---";
        } else {
            this.adp = beneficiario.getAdp();
        }

        if (beneficiario.getActivo() != null) {
            this.activo =
                    beneficiario.getActivo().booleanValue() ? "1" : !beneficiario.getActivo()
                            .booleanValue() ? "0" : "-1";
        }

        // activo = (beneficiario.getActivo() != null) ? beneficiario.getActivo().toString() : null;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(final Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    /**
     * @param apellidoPaterno the apellidoPaterno to set
     */
    public void setApellidoPaterno(final String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    /**
     * @param apellidoMaterno the apellidoMaterno to set
     */
    public void setApellidoMaterno(final String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return this.razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(final String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the personaFisica
     */
    public boolean isPersonaFisica() {
        return this.personaFisica;
    }

    /**
     * @param personaFisica the personaFisica to set
     */
    public void setPersonaFisica(final boolean personaFisica) {
        this.personaFisica = personaFisica;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return this.pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(final String pais) {
        this.pais = pais;
    }

    /**
     * @return the idTipoBeneficiario
     */
    public Long getIdTipoBeneficiario() {
        return this.idTipoBeneficiario;
    }

    /**
     * @param idTipoBeneficiario the idTipoBeneficiario to set
     */
    public void setIdTipoBeneficiario(final Long idTipoBeneficiario) {
        this.idTipoBeneficiario = idTipoBeneficiario;
    }

    /**
     * @return the descTipoBeneficiario
     */
    public String getDescTipoBeneficiario() {
        return this.descTipoBeneficiario;
    }

    /**
     * @return the descStatusBeneficiario
     */
    public String getDescStatusBeneficiario() {
        return this.descStatusBeneficiario;
    }

    /**
     * @param descTipoBeneficiario the descTipoBeneficiario to set
     */
    public void setDescTipoBeneficiario(final String descTipoBeneficiario) {
        this.descTipoBeneficiario = descTipoBeneficiario;
    }

    /**
     * @param descStatusBeneficiario the descStatusBeneficiario to set
     */
    public void setDescStatusBeneficiario(final String descStatusBeneficiario) {
        this.descStatusBeneficiario = descStatusBeneficiario;
    }

    /**
     * @return the custodio
     */
    public Long getCustodio() {
        return this.custodio;
    }

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(final Long custodio) {
        this.custodio = custodio;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(final Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the idInstitucion
     */
    public Long getIdInstitucion() {
        return this.idInstitucion;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(final Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @param fechaFormato the fechaFormato to set
     */
    public void setFechaFormato(final Date fechaFormato) {
        this.fechaFormato = fechaFormato;
    }

    /**
     * @return the fechaFormato
     */
    public Date getFechaFormato() {
        return this.fechaFormato;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return this.formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(final String formato) {
        this.formato = formato;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final List<String> institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the institucion
     */
    public List<String> getInstitucion() {
        return this.institucion;
    }

    /**
     * Regresa el nombre concatenado
     * 
     * @return nombre completo
     */
    public String getNombreCompleto() {
        if (this.personaFisica) {
            String retorno = "";
            if (StringUtils.isNotBlank(this.nombre)) {
                retorno += this.nombre.trim() + " ";
            }
            if (StringUtils.isNotBlank(this.apellidoPaterno)) {
                retorno += this.apellidoPaterno.trim() + " ";
            }
            if (StringUtils.isNotBlank(this.apellidoMaterno)) {
                retorno += this.apellidoMaterno.trim();
            }
            return retorno;
        } else {
            return null;
        }
    }

    /**
	 * @return the preAutoriza
	 */
	public boolean isPreAutoriza() {
		return preAutoriza;
	}

	/**
	 * @param preAutoriza the preAutoriza to set
	 */
	public void setPreAutoriza(boolean preAutoriza) {
		this.preAutoriza = preAutoriza;
	}

	/**
     * @return the activar
     */
    public boolean isActivar() {
        return this.activar;
    }

    /**
     * @param activar the activar to set
     */
    public void setActivar(final boolean activar) {
        this.activar = activar;
    }

    /**
     * Indica si esta activo o no
     * 
     * @return the cancelar
     */
    public boolean isCancelar() {
        return this.cancelar;
    }

    /**
     * Indica si esta activo o no
     * 
     * @param cancelar the cancelar to set
     */
    public void setCancelar(final boolean cancelar) {
        this.cancelar = cancelar;
    }

    /**
     * Indica se elimna el registro o no
     * 
     * @return the eliminar
     */
    public boolean isEliminar() {
        return this.eliminar;
    }

    /**
     * Indica se elimna el registro o no
     * 
     * @param eliminar the eliminar to set
     */
    public void setEliminar(final boolean eliminar) {
        this.eliminar = eliminar;
    }

    /**
     * Cadena con las instituciones separadas por saltos de linea
     * 
     * @return the instituciones
     */
    public String getInstituciones() {
        return this.instituciones;
    }

    /**
     * Cadena con las instituciones separadas por saltos de linea
     * 
     * @param instituciones the instituciones to set
     */
    public void setInstituciones(final String instituciones) {
        this.instituciones = instituciones;
    }

    /**
     * Porcentaje de Retencion
     * 
     * @return the porcentajeRetencion
     */
    public Double getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    /**
     * Porcentaje de Retencion
     * 
     * @param porcentajeRetencion the porcentajeRetencion to set
     */
    public void setPorcentajeRetencion(final Double porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    /**
     * Inentificador unico del sistema
     * 
     * @return the uoi
     */
    public String getUoi() {
        return this.uoi;
    }

    /**
     * Inentificador unico del sistema
     * 
     * @param uoi the uoi to set
     */
    public void setUoi(final String uoi) {
        this.uoi = uoi;
    }

    public String getAdp() {
        return this.adp;
    }

    public void setAdp(final String adp) {
        this.adp = adp;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return this.activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(final String activo) {
        this.activo = activo;
    }

    /**
     * Método para obtener el atributo disabledDireccionPostal
     * 
     * @return El atributo disabledDireccionPostal
     */
    public boolean isDisabledDireccionPostal() {
        return this.disabledDireccionPostal;
    }

    /**
     * Método para establecer el atributo disabledDireccionPostal
     * 
     * @param disabledDireccionPostal El valor del atributo disabledDireccionPostal a establecer.
     */
    public void setDisabledDireccionPostal(final boolean disabledDireccionPostal) {
        this.disabledDireccionPostal = disabledDireccionPostal;
    }

    /**
	 * @return the fechaVigenciaFormato
	 */
	public Date getFechaVigenciaFormato() {
		return fechaVigenciaFormato;
	}

	/**
	 * @param fechaVigenciaFormato the fechaVigenciaFormato to set
	 */
	public void setFechaVigenciaFormato(Date fechaVigenciaFormato) {
		this.fechaVigenciaFormato = fechaVigenciaFormato;
	}

	/**
	 * @return the rfcBeneficiario
	 */
	public String getRfcBeneficiario() {
		return rfcBeneficiario;
	}

	/**
	 * @param rfcBeneficiario the rfcBeneficiario to set
	 */
	public void setRfcBeneficiario(String rfcBeneficiario) {
		this.rfcBeneficiario = rfcBeneficiario;
	}
	/**
	 * 
	 * @return beneficialOwnerId
	 */
	public String getBeneficialOwnerId() {
		return beneficialOwnerId;
	}
	/**
	 *
	 * @param beneficialOwnerId the beneficialOwnerId to set
	 */
	public void setBeneficialOwnerId(String beneficialOwnerId) {
		this.beneficialOwnerId = beneficialOwnerId;
	}

	/**
     * Obtiene el tipo de formato a mostrar en la pantalla.
     * 
     * @return Cadena con el tipo de formato.
     */
    public String getFormatoPantalla() {
        String tipoFormatoPantalla = null;
        /*
         * if(BeneficiariosConstantes.FORMATO_W8_IMY.equals(formato) ||
         * BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(formato) ||
         * BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(formato)) tipoFormatoPantalla =
         * BeneficiariosConstantes.FORMATO_W8_IMY;
         * 
         * else if(BeneficiariosConstantes.FORMATO_W8_BEN.equals(formato) ||
         * BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(formato)) tipoFormatoPantalla =
         * BeneficiariosConstantes.FORMATO_W8_BEN; else
         */
        if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) {
            tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_IMY2016;
        } else {
            tipoFormatoPantalla = this.formato;
        }

        return tipoFormatoPantalla;
    }
}
