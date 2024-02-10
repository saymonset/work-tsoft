/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;

/**
 * @author Rafael Ibarra Zendejas
 * 
 */
@Entity
@Table(name = "T_BENEFICIARIOS")
@SequenceGenerator(name = "foliador", sequenceName = "ID_BENEFICIARIOS_SEQ", allocationSize = 1,
        initialValue = 1)
public class Beneficiario implements Serializable {

    /**
     * Constante de serializacion por default
     */
    private static final long serialVersionUID = 1L;

    private Long idBeneficiario;

    private String tipoFormato;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String razonSocial;

    private Boolean personaFisica;

    private Date fechaAutorizacion;

    private Date fechaCambio;

    private Date fechaFormato;

    private Date fechaRegistro;

    private StatusBeneficiario statusBenef;

    private TipoBeneficiario tipoBeneficiario;

    private Set<Institucion> institucion;

    private String paisIncorporacion;

    private String uoiNumber;

    private Long idCuentaNombrada;

    private Domicilio domicilioW8Normal;

    private Domicilio domicilioW8Correo;

    private Domicilio domicilioW9;

    private Domicilio domicilioMILA;

    private FormatoW8BEN formatoW8BEN;

    private FormatoW8IMY formatoW8IMY;

    private FormatoW9 formatoW9;

    private FormatoMILA formatoMILA;

    private Double porcentajeRetencion;

    private Boolean eliminado;

    private Date fechaEliminado;

    private String adp;

    private Boolean activo;

    private FormatoW8BENE formatoW8BENE;

    private FormatoW8IMY2015 formatoW8IMY2015; // tambien funciona para el formato 2017

    // private FormatoW8IMY2017 formatoW8IMY2017;
    
    private Date fechaVigenciaFormato;
    
    /**
     * Intermediario Calificado Dic-2022
     */
    private String origen;
    private String beneficialOwnerId;

    /**
     * @return Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "id_beneficiario", unique = true, nullable = false)
    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    /**
     * @return String
     */
    @Column(name = "tipo_formato", unique = false, nullable = false)
    public String getTipoFormato() {
        return this.tipoFormato;
    }

    /**
     * @return String
     */
    @Column(name = "NOMBRE_BENEF", unique = false, nullable = true)
    public String getNombres() {
        return this.nombres;
    }

    /**
     * @return the appellidoPaterno
     */
    @Column(name = "APELLIDO_PATERNO_BENEF", unique = false, nullable = true)
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    /**
     * @return the appellidoMaterno
     */
    @Column(name = "APELLIDO_MATERNO_BENEF", unique = false, nullable = true)
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    /**
     * @return the razonSocial
     */
    @Column(name = "RAZON_SOCIAL_BENEF", unique = false, nullable = true)
    public String getRazonSocial() {
        return this.razonSocial;
    }

    /**
     * @return the personaFisica
     */
    @Column(name = "PERSONA_FISICA", unique = false, nullable = false)
    public Boolean getPersonaFisica() {
        return this.personaFisica;
    }

    /**
     * @return Date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_autorizacion", unique = false, nullable = true)
    public Date getFechaAutorizacion() {
        return this.fechaAutorizacion;
    }

    /**
     * @return Date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_cambio", unique = false, nullable = true)
    public Date getFechaCambio() {
        return this.fechaCambio;
    }

    /**
     * @return Date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_formato", unique = false, nullable = true)
    public Date getFechaFormato() {
        return this.fechaFormato;
    }

    /**
     * @return Date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", unique = false, nullable = true)
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }

    /**
     * @return StatusBeneficiario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_benef", nullable = false)
    public StatusBeneficiario getStatusBenef() {
        return this.statusBenef;
    }

    /**
     * @return TipoBeneficiario
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_beneficiario", nullable = false)
    public TipoBeneficiario getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    /**
     * @return DomicilioW8
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_domicilio_w8_correo", nullable = true)
    public Domicilio getDomicilioW8Correo() {
        return this.domicilioW8Correo;
    }

    /**
     * @return DomicilioW8
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_domicilio_w8_normal", nullable = true)
    public Domicilio getDomicilioW8Normal() {
        return this.domicilioW8Normal;
    }

    /**
     * @return DomicilioW9
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_domicilio_w9", nullable = true)
    public Domicilio getDomicilioW9() {
        return this.domicilioW9;
    }

    /**
     * @return domicilioMila
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_domicilio_mila", nullable = true)
    public Domicilio getDomicilioMILA() {
        return this.domicilioMILA;
    }



    /**
     * @return Institucion
     */
    @ManyToMany(targetEntity = Institucion.class, fetch = FetchType.EAGER)
    @JoinTable(name = "T_BENEFICIARIOS_INSTITUCION", joinColumns = @JoinColumn(
            name = "ID_BENEFICIARIO", nullable = true, updatable = false, insertable = false),
            inverseJoinColumns = @JoinColumn(name = "ID_INSTITUCION", nullable = true,
                    updatable = false, insertable = false))
    public Set<Institucion> getInstitucion() {
        return this.institucion;
    }

    /**
     * @return FormatoW8BEN
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campos_formato_w8ben", nullable = true)
    public FormatoW8BEN getFormatoW8BEN() {
        return this.formatoW8BEN;
    }

    /**
     * @return FormatoW8IMY
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campos_formato_w8imy", nullable = true)
    public FormatoW8IMY getFormatoW8IMY() {
        return this.formatoW8IMY;
    }

    /**
     * @return FormatoW9
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campos_formato_w9", nullable = true)
    public FormatoW9 getFormatoW9() {
        return this.formatoW9;
    }

    /**
     * @return FormatoMILA
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_campos_formato_mila", nullable = true)
    public FormatoMILA getFormatoMILA() {
        return this.formatoMILA;
    }

    /**
     * @return String
     */
    @Column(name = "pais_incorporacion", unique = false, nullable = true)
    public String getPaisIncorporacion() {
        return this.paisIncorporacion;
    }

    /**
     * @return the uoiNumber
     */
    @Column(name = "UOI", unique = true, nullable = true)
    public String getUoiNumber() {
        return this.uoiNumber;
    }

    /**
     * @return the cuentaNombrada
     */
    @Column(name = "ID_CUENTA_NOMBRADA", nullable = false, unique = false)
    public Long getIdCuentaNombrada() {
        return this.idCuentaNombrada;
    }

    @Column(name = "PORCENTAJE_RETENCION", nullable = true, unique = false)
    public Double getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    @Column(name = "ELIMINADO", nullable = false, unique = false)
    public Boolean getEliminado() {
        return this.eliminado;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ELIMINADO", unique = false, nullable = true)
    public Date getFechaEliminado() {
        return this.fechaEliminado;
    }

    @Column(name = "ADP", nullable = true, unique = false)
    public String getAdp() {
        return this.adp;
    }

    /**
     * @return the activo
     */
    @Column(name = "ACTIVO", nullable = true, unique = false)
    public Boolean getActivo() {
        return this.activo;
    }

    /**
     * @return the formatoW8BENE
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_campos_formato_w8bene", nullable = true)
    public FormatoW8BENE getFormatoW8BENE() {
        return this.formatoW8BENE;
    }

    /**
     * @return the formatoW8IMY2015
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_campos_formato_w8imy2015", nullable = true)
    public FormatoW8IMY2015 getFormatoW8IMY2015() {
        return this.formatoW8IMY2015;
    }

    /**
	 * @return the fechaVigenciaFormato
	 */
    @Transient
	public Date getFechaVigenciaFormato() {
		return fechaVigenciaFormato;
	}

	/**
	 * @param fechaVigenciaFormato the fechaVigenciaFormato to set
	 */
	public void setFechaVigenciaFormato(Date fechaVigenciaFormato) {
		this.fechaVigenciaFormato = fechaVigenciaFormato;
	}

	@Transient
    public String getNombreGeneral() {
        if (this.personaFisica) {
            String retorno = "";
            if (StringUtils.isNotBlank(this.nombres)) {
                retorno += this.nombres.trim() + " ";
            }
            if (StringUtils.isNotBlank(this.apellidoPaterno)) {
                retorno += this.apellidoPaterno.trim() + " ";
            }
            if (StringUtils.isNotBlank(this.apellidoMaterno)) {
                retorno += this.apellidoMaterno.trim();
            }
            return retorno;
        } else {
            return this.razonSocial;
        }
    }

    @Transient
    public String getNombresInstituciones() {
        StringBuilder sb = new StringBuilder();
        if (this.institucion != null && !this.institucion.isEmpty()) {
            for (Institucion i : this.institucion) {
                sb.append(i.getTipoInstitucion().getClaveTipoInstitucion()
                        + i.getFolioInstitucion() + " - " + i.getNombreCorto() + "\n");
            }
        }
        return sb.toString();
    }

    @Transient
    public String getNombresInstitucionesConPipe() {
        StringBuilder sb = new StringBuilder();
        if (this.institucion != null && !this.institucion.isEmpty()) {
            for (Institucion i : this.institucion) {
                sb.append(i.getTipoInstitucion().getClaveTipoInstitucion()
                        + i.getFolioInstitucion() + " - " + i.getNombreCorto() + " | ");
            }
        }
        return sb.toString();
    }

    /**
     * @param idBeneficiario
     */
    public void setIdBeneficiario(final Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @param tipoFormato
     */
    public void setTipoFormato(final String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    /**
     * @param nombreBenef
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    /**
     * @param appellidoPaterno the appellidoPaterno to set
     */
    public void setApellidoPaterno(final String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @param appellidoMaterno the appellidoMaterno to set
     */
    public void setApellidoMaterno(final String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(final String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @param personaFisica the personaFisica to set
     */
    public void setPersonaFisica(final Boolean personaFisica) {
        this.personaFisica = personaFisica;
    }

    /**
     * @param fechaAutorizacion
     */
    public void setFechaAutorizacion(final Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    /**
     * 
     * @param fechaCambio
     */
    public void setFechaCambio(final Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /**
     * @param fechaFormato
     */
    public void setFechaFormato(final Date fechaFormato) {
        this.fechaFormato = fechaFormato;
    }

    /**
     * @param fechaRegistro
     */
    public void setFechaRegistro(final Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @param statusBenef
     */
    public void setStatusBenef(final StatusBeneficiario statusBenef) {
        this.statusBenef = statusBenef;
    }

    /**
     * @param tipoBeneficiario
     */
    public void setTipoBeneficiario(final TipoBeneficiario tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @param domicilioW8Correo
     */
    public void setDomicilioW8Correo(final Domicilio domicilioW8Correo) {
        this.domicilioW8Correo = domicilioW8Correo;
    }

    /**
     * @param domicilioW8Normal
     */
    public void setDomicilioW8Normal(final Domicilio domicilioW8Normal) {
        this.domicilioW8Normal = domicilioW8Normal;
    }

    /**
     * @param domicilioW9
     */
    public void setDomicilioW9(final Domicilio domicilioW9) {
        this.domicilioW9 = domicilioW9;
    }

    /**
     * @param domicilioW9
     */
    public void setDomicilioMILA(final Domicilio domicilioMila) {
        this.domicilioMILA = domicilioMila;
    }

    /**
     * @param institucion
     */
    public void setInstitucion(final Set<Institucion> institucion) {
        this.institucion = institucion;
    }

    /**
     * 
     * @param formatoW8BEN
     */
    public void setFormatoW8BEN(final FormatoW8BEN formatoW8BEN) {
        this.formatoW8BEN = formatoW8BEN;
    }

    /**
     * 
     * @param formatoW8IMY
     */
    public void setFormatoW8IMY(final FormatoW8IMY formatoW8IMY) {
        this.formatoW8IMY = formatoW8IMY;
    }

    /**
     * 
     * @param formatoW9
     */
    public void setFormatoW9(final FormatoW9 formatoW9) {
        this.formatoW9 = formatoW9;
    }

    /**
     * 
     * @param formatoMILA
     */
    public void setFormatoMILA(final FormatoMILA formatoMILA) {
        this.formatoMILA = formatoMILA;
    }

    /**
     * @param paisIncorporacion
     */
    public void setPaisIncorporacion(final String paisIncorporacion) {
        this.paisIncorporacion = paisIncorporacion;
    }

    /**
     * @param uoiNumber the uoiNumber to set
     */
    public void setUoiNumber(final String uoiNumber) {
        this.uoiNumber = uoiNumber;
    }

    /**
     * @param cuentaNombrada the cuentaNombrada to set
     */
    public void setIdCuentaNombrada(final Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
    }

    /**
     * @param porcentajeRetencion the porcentajeRetencion to set
     */
    public void setPorcentajeRetencion(final Double porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    /**
     * @param eliminado the eliminado to set
     */
    public void setEliminado(final Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setFechaEliminado(final Date fechaEliminado) {
        this.fechaEliminado = fechaEliminado;
    }

    public void setAdp(final String adp) {
        this.adp = adp;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(final Boolean activo) {
        this.activo = activo;
    }

    /**
     * @param formatoW8BENE the formatoW8BENE to set
     */
    public void setFormatoW8BENE(final FormatoW8BENE formatoW8BENE) {
        this.formatoW8BENE = formatoW8BENE;
    }

    /**
     * @param formatoW8IMY2015 the formatoW8IMY2015 to set
     */
    public void setFormatoW8IMY2015(final FormatoW8IMY2015 formatoW8IMY2015) {
        this.formatoW8IMY2015 = formatoW8IMY2015;
    }

	@Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23).append(this.idBeneficiario).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = false;
        if (obj instanceof Beneficiario) {
            Beneficiario beneficiario = (Beneficiario) obj;
            isEqual =
                    new EqualsBuilder().append(this.idBeneficiario,
                            beneficiario.getIdBeneficiario()).isEquals();
        }
        return isEqual;
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder =
                new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                        .append("Id Beneficiario", this.idBeneficiario)
                        .append("Nombre", this.nombres)
                        .append("fecha registro", this.fechaRegistro)
                        .append("Tipo Formato", this.tipoFormato)
                        .append("Tipo Beneficiario",
                                this.tipoBeneficiario.getDescTipoBeneficiario())
                        .append("Status Beneficiario", this.statusBenef.getDescStatusBenef());
        if (this.institucion != null && this.institucion.size() > 0) {
            for (Institucion ins : this.institucion) {
                toStringBuilder.append("Institucion", ins.getTipoInstitucion()
                        .getClaveTipoInstitucion() + ins.getFolioInstitucion());
            }
        }

        return toStringBuilder.toString();
    }

    /**
     * Obtiene el tipo de formato a mostrar en la pantalla.
     * 
     * @return Cadena con el tipo de formato.
     */
    @Transient
    public String getTipoFormatoPantalla() {
        String tipoFormatoPantalla = null;

        /*
         * if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(this.tipoFormato) ||
         * BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.tipoFormato) ||
         * BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.tipoFormato)) {
         * tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_IMY; } else if
         * (BeneficiariosConstantes.FORMATO_W8_BEN.equals(this.tipoFormato) ||
         * BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(this.tipoFormato)) {
         * tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_BEN; } else {
         * tipoFormatoPantalla = this.tipoFormato; }
         */
        if(BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.tipoFormato)){
            tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_IMY2016;
        }else{
            tipoFormatoPantalla = this.tipoFormato;
        }
        return tipoFormatoPantalla;
    }

	/**
	 * Metodo para obtener el atributo origen
	 * @return El atributo origen
	 */
    @Column(name = "ORIGEN", unique = false, nullable = true)
	public String getOrigen() {
		return origen;
	}

	/**
	 * Metodo para establecer el atributo origen
	 * @param origen El valor del atributo origen a establecer.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Metodo para obtener el atributo beneficialOwnerId
	 * @return El atributo beneficialOwnerId
	 */
	@Column(name = "BENEFICIAL_OWNER_ID", unique = false, nullable = true)
	public String getBeneficialOwnerId() {
		return beneficialOwnerId;
	}

	/**
	 * Metodo para establecer el atributo beneficialOwnerId
	 * @param beneficialOwnerId El valor del atributo beneficialOwnerId a establecer.
	 */
	public void setBeneficialOwnerId(String beneficialOwnerId) {
		this.beneficialOwnerId = beneficialOwnerId;
	}


}
