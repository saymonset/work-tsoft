package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate.type.EncryptedStringType;

/**
 * Entidad para la vista que se usa en la consulta de beneficiarios de derechos por cuenta.
 */
@Entity
@Table(name = "V_CONS_BENEF_DER_CTA")
@TypeDefs({
    @TypeDef(name = "encryptedString5",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor5")
            }
    ),
    @TypeDef(name = "encryptedString6",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor6")
            }
    ),
    @TypeDef(name = "encryptedString7",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor7")
            }
    ),
    @TypeDef(name = "encryptedString8",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor8")
            }
    ),
    @TypeDef(name = "encryptedString9",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor9")
            }
    ),
    @TypeDef(name = "encryptedString6",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor6")
            }
    ),
    @TypeDef(name = "encryptedString10",
        typeClass = EncryptedStringType.class,
            parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor10")
            }
    ),
    @TypeDef(name = "encryptedString11",
    typeClass = EncryptedStringType.class,
        parameters = {
            @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor11")
        }
    )
})
public class VConsultaBeneficiarioDerechosCuenta implements Serializable {

    private static final long serialVersionUID = 13L;

    @Column(name = "CUENTA")
    private String cuenta;

    @Column(name = "ID_CUENTA_NOMBRADA")
    private Long idCuentaNombrada;

    @Column(name = "FOLIO_INSTITUCION")
    private String folioInstitucion;

    @Column(name = "ID_DERECHO_BENEFICIARIO")
    private Long idDerechoBeneficiario;

    @Id
    @Column(name = "ID_DERECHO_CAPITAL")
    private Long idDerechoCapital;

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;

    @Column(name = "PORCENTAJE_RETENCION")
    private Double porcentajeRetencion;

    @Column(name = "POSICION")
    private Long posicion;

    @Column(name = "UOI")
    private String uoi;

    @Column(name = "ADP_NUMBER")
    private String adpNumber;

    @Column(name = "TIPO_FORMATO")
    private String tipoFormato;

    @Column(name = "DESC_TIPO_BENEFICIARIO")
    private String descTipoBeneficiario;

    @Column(name = "ID_TIPO_INSTITUCION")
    private Long idTipoInstitucion;

    @Column(name = "ID_CAMPOS_FORMATO_W8BEN")
    private Long idCamposFormatoW8BEN;

    @Column(name = "RFC_FW8BEN")
    private String rfcFW8BEN;

    @Column(name = "ID_CAMPOS_FORMATO_W8BENE")
    private Long idCamposFormatoW8BENE;

    @Column(name = "RFC_FW8BENE")
    private String rfcFW8BENE;

    @Column(name = "ID_DOMICILIO_W8_NORMAL")
    private Long idDomicilioW8Normal;

    @Column(name = "COUNTRY_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString11")
    private String contryDomW8NORMAL;

    @Column(name = "STREET_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString5")
    private String streetDomW8NORMAL;

    @Column(name = "OUTERNUMBER_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString6")
    private String outernumberDomW8NORMAL;

    @Column(name = "INTERIORNUMBER_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString7")
    private String interiornumberDomW8NORMAL;

    @Column(name = "POSTALCODE_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString8")
    private String postalcodeDomW8NORMAL;

    @Column(name = "CITYTOWN_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString9")
    private String citytownDomW8NORMAL;

    @Column(name = "STATEPROVINCE_DOM_W8NORMAL", unique = false, nullable = true)
    @Type(type="encryptedString10")
    private String stateprovinceDomW8NORMAL;

    @Column(name = "ID_CAMPOS_FORMATO_W9")
    private Long idCamposFormatoW9;

    @Column(name = "RFC_FW9")
    private String rfcFW9;

    @Column(name = "ID_DOMICILIO_W9")
    private Long idDomicilioW9;

    @Column(name = "COUNTRY_DOM_W9", unique = false, nullable = true)
    //@Type(type="encryptedString11")
    private String contryDomW9;

    @Column(name = "STREET_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString5")
    private String streetDomW9;

    @Column(name = "OUTERNUMBER_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString6")
    private String outernumberDomW9;

    @Column(name = "INTERIORNUMBER_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString7")
    private String interiornumberDomW9;

    @Column(name = "POSTALCODE_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString8")
    private String postalcodeDomW9;

    @Column(name = "CITYTOWN_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString9")
    private String citytownDomW9;

    @Column(name = "STATEPROVINCE_DOM_W9", unique = false, nullable = true)
    @Type(type="encryptedString10")
    private String stateprovinceDomW9;

    @Column(name = "ID_CAMPOS_FORMATO_W8IMY")
    private Long idCamposFormatoW8IMY;

    @Column(name = "RFC_FW8IMY")
    private String rfcFW8IMY;

    @Column(name = "ID_CAMPOS_FORMATO_W8IMY2015")
    private Long idCamposFormatoW8IMY2015;

    @Column(name = "RFC_FW8IMY2015")
    private String rfcFW8IMY2015;

    @Column(name = "COUNTRY_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString11")
    private String contryDomW8NORMALDEW8IMY;

    @Column(name = "STREET_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString5")
    private String streetDomW8NORMALDEW8IMY;

    @Column(name = "OUTNUM_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString6")
    private String outnumDomW8NORMALDEW8IMY;

    @Column(name = "INTNUM_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString7")
    private String intnumDomW8NORMALDEW8IMY;

    @Column(name = "POSCOD_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString8")
    private String poscodDomW8NORMALDEW8IMY;

    @Column(name = "CITTOW_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString9")
    private String cittowDomW8NORMALDEW8IMY;

    @Column(name = "STAPRO_DOM_W8NORMAL_DEW8IMY", unique = false, nullable = true)
    @Type(type="encryptedString10")
    private String staproDomW8NORMALDEW8IMY;

    @Column(name = "ID_CAMPOS_FORMATO_MILA")
    private Long idCamposFormatoMILA;

    @Column(name = "RFC_FMILA")
    private String rfcFMILA;

    @Column(name = "RFC_FMILA_NUMERO_DOCUMENTO")
    private String rfcFMILANumeroDocumento;

    @Column(name = "ID_DOMICILIO_MILA")
    private Long idDomicilioMILA;

    @Column(name = "COUNTRY_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString11")
    private String contryDomMILA;

    @Column(name = "STREET_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString5")
    private String streetDomMILA;

    @Column(name = "OUTERNUMBER_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString6")
    private String outnumberDomMILA;

    @Column(name = "INTERIORNUMBER_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString7")
    private String interiornumberDomMILA;

    @Column(name = "POSTALCODE_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString8")
    private String postalcodeDomMILA;

    @Column(name = "CITYTOWN_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString9")
    private String citytownDomMILA;

    @Column(name = "STATEPROVINCE_DOM_MILA", unique = false, nullable = true)
    @Type(type="encryptedString10")
    private String stateprovinceDomMILA;

    @Column(name = "PORCENTAJE_RETENCION_CUSTODIO")
    private Double porcentajeRetencionCustodio;


    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Long getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

    public void setIdCuentaNombrada(Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
    }

    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    public Long getIdDerechoBeneficiario() {
        return idDerechoBeneficiario;
    }

    public void setIdDerechoBeneficiario(Long idDerechoBeneficiario) {
        this.idDerechoBeneficiario = idDerechoBeneficiario;
    }

    public Long getIdDerechoCapital() {
        return idDerechoCapital;
    }

    public void setIdDerechoCapital(Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Double getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(Double porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public Long getPosicion() {
        return posicion;
    }

    public void setPosicion(Long posicion) {
        this.posicion = posicion;
    }

    public String getUoi() {
        return uoi;
    }

    public void setUoi(String uoi) {
        this.uoi = uoi;
    }

    public String getAdpNumber() {
        return adpNumber;
    }

    public void setAdpNumber(String adpNumber) {
        this.adpNumber = adpNumber;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getDescTipoBeneficiario() {
        return descTipoBeneficiario;
    }

    public void setDescTipoBeneficiario(String descTipoBeneficiario) {
        this.descTipoBeneficiario = descTipoBeneficiario;
    }

    public Long getIdTipoInstitucion() {
        return idTipoInstitucion;
    }

    public void setIdTipoInstitucion(Long idTipoInstitucion) {
        this.idTipoInstitucion = idTipoInstitucion;
    }

    public Long getIdCamposFormatoW8BEN() {
        return idCamposFormatoW8BEN;
    }

    public void setIdCamposFormatoW8BEN(Long idCamposFormatoW8BEN) {
        this.idCamposFormatoW8BEN = idCamposFormatoW8BEN;
    }

    public String getRfcFW8BEN() {
        return rfcFW8BEN;
    }

    public void setRfcFW8BEN(String rfcFW8BEN) {
        this.rfcFW8BEN = rfcFW8BEN;
    }

    public Long getIdCamposFormatoW8BENE() {
        return idCamposFormatoW8BENE;
    }

    public void setIdCamposFormatoW8BENE(Long idCamposFormatoW8BENE) {
        this.idCamposFormatoW8BENE = idCamposFormatoW8BENE;
    }

    public String getRfcFW8BENE() {
        return rfcFW8BENE;
    }

    public void setRfcFW8BENE(String rfcFW8BENE) {
        this.rfcFW8BENE = rfcFW8BENE;
    }

    public Long getIdDomicilioW8Normal() {
        return idDomicilioW8Normal;
    }

    public void setIdDomicilioW8Normal(Long idDomicilioW8Normal) {
        this.idDomicilioW8Normal = idDomicilioW8Normal;
    }

    public String getContryDomW8NORMAL() {
        return contryDomW8NORMAL;
    }

    public void setContryDomW8NORMAL(String contryDomW8NORMAL) {
        this.contryDomW8NORMAL = contryDomW8NORMAL;
    }

    public String getStreetDomW8NORMAL() {
        return streetDomW8NORMAL;
    }

    public void setStreetDomW8NORMAL(String streetDomW8NORMAL) {
        this.streetDomW8NORMAL = streetDomW8NORMAL;
    }

    public String getOuternumberDomW8NORMAL() {
        return outernumberDomW8NORMAL;
    }

    public void setOuternumberDomW8NORMAL(String outernumberDomW8NORMAL) {
        this.outernumberDomW8NORMAL = outernumberDomW8NORMAL;
    }

    public String getInteriornumberDomW8NORMAL() {
        return interiornumberDomW8NORMAL;
    }

    public void setInteriornumberDomW8NORMAL(String interiornumberDomW8NORMAL) {
        this.interiornumberDomW8NORMAL = interiornumberDomW8NORMAL;
    }

    public String getPostalcodeDomW8NORMAL() {
        return postalcodeDomW8NORMAL;
    }

    public void setPostalcodeDomW8NORMAL(String postalcodeDomW8NORMAL) {
        this.postalcodeDomW8NORMAL = postalcodeDomW8NORMAL;
    }

    public String getCitytownDomW8NORMAL() {
        return citytownDomW8NORMAL;
    }

    public void setCitytownDomW8NORMAL(String citytownDomW8NORMAL) {
        this.citytownDomW8NORMAL = citytownDomW8NORMAL;
    }

    public String getStateprovinceDomW8NORMAL() {
        return stateprovinceDomW8NORMAL;
    }

    public void setStateprovinceDomW8NORMAL(String stateprovinceDomW8NORMAL) {
        this.stateprovinceDomW8NORMAL = stateprovinceDomW8NORMAL;
    }

    public Long getIdCamposFormatoW9() {
        return idCamposFormatoW9;
    }

    public void setIdCamposFormatoW9(Long idCamposFormatoW9) {
        this.idCamposFormatoW9 = idCamposFormatoW9;
    }

    public Long getIdDomicilioW9() {
        return idDomicilioW9;
    }

    public void setIdDomicilioW9(Long idDomicilioW9) {
        this.idDomicilioW9 = idDomicilioW9;
    }

    public String getRfcFW9() {
        return rfcFW9;
    }

    public void setRfcFW9(String rfcFW9) {
        this.rfcFW9 = rfcFW9;
    }

    public String getContryDomW9() {
        return contryDomW9;
    }

    public void setContryDomW9(String contryDomW9) {
        this.contryDomW9 = contryDomW9;
    }

    public String getStreetDomW9() {
        return streetDomW9;
    }

    public void setStreetDomW9(String streetDomW9) {
        this.streetDomW9 = streetDomW9;
    }

    public Long getIdCamposFormatoW8IMY() {
        return idCamposFormatoW8IMY;
    }

    public String getOuternumberDomW9() {
        return outernumberDomW9;
    }

    public void setOuternumberDomW9(String outernumberDomW9) {
        this.outernumberDomW9 = outernumberDomW9;
    }

    public String getInteriornumberDomW9() {
        return interiornumberDomW9;
    }

    public void setInteriornumberDomW9(String interiornumberDomW9) {
        this.interiornumberDomW9 = interiornumberDomW9;
    }

    public String getPostalcodeDomW9() {
        return postalcodeDomW9;
    }

    public void setPostalcodeDomW9(String postalcodeDomW9) {
        this.postalcodeDomW9 = postalcodeDomW9;
    }

    public String getCitytownDomW9() {
        return citytownDomW9;
    }

    public void setCitytownDomW9(String citytownDomW9) {
        this.citytownDomW9 = citytownDomW9;
    }

    public String getStateprovinceDomW9() {
        return stateprovinceDomW9;
    }

    public void setStateprovinceDomW9(String stateprovinceDomW9) {
        this.stateprovinceDomW9 = stateprovinceDomW9;
    }

    public void setIdCamposFormatoW8IMY(Long idCamposFormatoW8IMY) {
        this.idCamposFormatoW8IMY = idCamposFormatoW8IMY;
    }

    public String getRfcFW8IMY() {
        return rfcFW8IMY;
    }

    public void setRfcFW8IMY(String rfcFW8IMY) {
        this.rfcFW8IMY = rfcFW8IMY;
    }

    public Long getIdCamposFormatoW8IMY2015() {
        return idCamposFormatoW8IMY2015;
    }

    public void setIdCamposFormatoW8IMY2015(Long idCamposFormatoW8IMY2015) {
        this.idCamposFormatoW8IMY2015 = idCamposFormatoW8IMY2015;
    }

    public String getRfcFW8IMY2015() {
        return rfcFW8IMY2015;
    }

    public void setRfcFW8IMY2015(String rfcFW8IMY2015) {
        this.rfcFW8IMY2015 = rfcFW8IMY2015;
    }

    public String getContryDomW8NORMALDEW8IMY() {
        return contryDomW8NORMALDEW8IMY;
    }

    public void setContryDomW8NORMALDEW8IMY(String contryDomW8NORMALDEW8IMY) {
        this.contryDomW8NORMALDEW8IMY = contryDomW8NORMALDEW8IMY;
    }

    public String getStreetDomW8NORMALDEW8IMY() {
        return streetDomW8NORMALDEW8IMY;
    }

    public void setStreetDomW8NORMALDEW8IMY(String streetDomW8NORMALDEW8IMY) {
        this.streetDomW8NORMALDEW8IMY = streetDomW8NORMALDEW8IMY;
    }

    public String getOutnumDomW8NORMALDEW8IMY() {
        return outnumDomW8NORMALDEW8IMY;
    }

    public void setOutnumDomW8NORMALDEW8IMY(String outnumDomW8NORMALDEW8IMY) {
        this.outnumDomW8NORMALDEW8IMY = outnumDomW8NORMALDEW8IMY;
    }

    public String getIntnumDomW8NORMALDEW8IMY() {
        return intnumDomW8NORMALDEW8IMY;
    }

    public void setIntnumDomW8NORMALDEW8IMY(String intnumDomW8NORMALDEW8IMY) {
        this.intnumDomW8NORMALDEW8IMY = intnumDomW8NORMALDEW8IMY;
    }

    public String getPoscodDomW8NORMALDEW8IMY() {
        return poscodDomW8NORMALDEW8IMY;
    }

    public void setPoscodDomW8NORMALDEW8IMY(String poscodDomW8NORMALDEW8IMY) {
        this.poscodDomW8NORMALDEW8IMY = poscodDomW8NORMALDEW8IMY;
    }

    public String getCittowDomW8NORMALDEW8IMY() {
        return cittowDomW8NORMALDEW8IMY;
    }

    public void setCittowDomW8NORMALDEW8IMY(String cittowDomW8NORMALDEW8IMY) {
        this.cittowDomW8NORMALDEW8IMY = cittowDomW8NORMALDEW8IMY;
    }

    public String getStaproDomW8NORMALDEW8IMY() {
        return staproDomW8NORMALDEW8IMY;
    }

    public void setStaproDomW8NORMALDEW8IMY(String staproDomW8NORMALDEW8IMY) {
        this.staproDomW8NORMALDEW8IMY = staproDomW8NORMALDEW8IMY;
    }

    public Long getIdCamposFormatoMILA() {
        return idCamposFormatoMILA;
    }

    public void setIdCamposFormatoMILA(Long idCamposFormatoMILA) {
        this.idCamposFormatoMILA = idCamposFormatoMILA;
    }

    public String getRfcFMILA() {
        return rfcFMILA;
    }

    public void setRfcFMILA(String rfcFMILA) {
        this.rfcFMILA = rfcFMILA;
    }

    public String getRfcFMILANumeroDocumento() {
        return rfcFMILANumeroDocumento;
    }

    public void setRfcFMILANumeroDocumento(String rfcFMILANumeroDocumento) {
        this.rfcFMILANumeroDocumento = rfcFMILANumeroDocumento;
    }

    public Long getIdDomicilioMILA() {
        return idDomicilioMILA;
    }

    public void setIdDomicilioMILA(Long idDomicilioMILA) {
        this.idDomicilioMILA = idDomicilioMILA;
    }

    public String getContryDomMILA() {
        return contryDomMILA;
    }

    public void setContryDomMILA(String contryDomMILA) {
        this.contryDomMILA = contryDomMILA;
    }

    public String getStreetDomMILA() {
        return streetDomMILA;
    }

    public void setStreetDomMILA(String streetDomMILA) {
        this.streetDomMILA = streetDomMILA;
    }

    public String getOutnumberDomMILA() {
        return outnumberDomMILA;
    }

    public void setOutnumberDomMILA(String outnumberDomMILA) {
        this.outnumberDomMILA = outnumberDomMILA;
    }

    public String getInteriornumberDomMILA() {
        return interiornumberDomMILA;
    }

    public void setInteriornumberDomMILA(String interiornumberDomMILA) {
        this.interiornumberDomMILA = interiornumberDomMILA;
    }

    public String getPostalcodeDomMILA() {
        return postalcodeDomMILA;
    }

    public void setPostalcodeDomMILA(String postalcodeDomMILA) {
        this.postalcodeDomMILA = postalcodeDomMILA;
    }

    public String getCitytownDomMILA() {
        return citytownDomMILA;
    }

    public void setCitytownDomMILA(String citytownDomMILA) {
        this.citytownDomMILA = citytownDomMILA;
    }

    public String getStateprovinceDomMILA() {
        return stateprovinceDomMILA;
    }

    public void setStateprovinceDomMILA(String stateprovinceDomMILA) {
        this.stateprovinceDomMILA = stateprovinceDomMILA;
    }

    public Double getPorcentajeRetencionCustodio() {
        return porcentajeRetencionCustodio;
    }

    public void setPorcentajeRetencionCustodio(Double porcentajeRetencionCustodio) {
        this.porcentajeRetencionCustodio = porcentajeRetencionCustodio;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("idDerechoCapital=[" + this.idDerechoCapital.toString() + "] | ").
        append("idDerechoBeneficiario=[" + this.idDerechoBeneficiario + "] | ").
        append("cuenta=[" + this.cuenta + "] | ").toString()
        ;
    }

    /**
     * Obtiene la direccion completa para formato W8Normal
     * @return
     */
    public String getDireccionDomW8NORMAL() {
        String retorno = "";
        if (StringUtils.isNotBlank(this.getStreetDomW8NORMAL())) {
            retorno += (this.getStreetDomW8NORMAL() + " ");
        }
        if (StringUtils.isNotBlank(this.getOuternumberDomW8NORMAL())) {
            retorno += (this.getOuternumberDomW8NORMAL() + " ");
        }
        if (StringUtils.isNotBlank(this.getInteriornumberDomW8NORMAL())) {
            retorno += this.getInteriornumberDomW8NORMAL();
        }
        if (StringUtils.isNotBlank(this.getPostalcodeDomW8NORMAL())) {
            retorno += "," + this.getPostalcodeDomW8NORMAL();
        }
        if (StringUtils.isNotBlank(this.getCitytownDomW8NORMAL())) {
            retorno += "," + this.getCitytownDomW8NORMAL();
        }
        if (StringUtils.isNotBlank(this.getStateprovinceDomW8NORMAL())) {
            retorno += "," + this.getStateprovinceDomW8NORMAL();
        }
        return retorno;
    }

    /**
     * Obtiene la direccion completa para formato W9
     * @return
     */
    public String getDireccionDomW9() {
        String retorno = "";
        if (StringUtils.isNotBlank(this.getStreetDomW9())) {
            retorno += (this.getStreetDomW9() + " ");
        }
        if (StringUtils.isNotBlank(this.getOuternumberDomW9())) {
            retorno += (this.getOuternumberDomW9() + " ");
        }
        if (StringUtils.isNotBlank(this.getInteriornumberDomW9())) {
            retorno += this.getInteriornumberDomW9();
        }
        if (StringUtils.isNotBlank(this.getPostalcodeDomW9())) {
            retorno += "," + this.getPostalcodeDomW9();
        }
        if (StringUtils.isNotBlank(this.getCitytownDomW9())) {
            retorno += "," + this.getCitytownDomW9();
        }
        if (StringUtils.isNotBlank(this.getStateprovinceDomW9())) {
            retorno += "," + this.getStateprovinceDomW9();
        }
        return retorno;
    }

    /**
     * Obtiene la direccion completa para formato W8IMY
     * @return
     */
    public String getDireccionDomMila() {
        String retorno = "";
        if (StringUtils.isNotBlank(this.getStreetDomMILA())) {
            retorno += (this.getStreetDomMILA() + " ");
        }
        if (StringUtils.isNotBlank(this.getOutnumberDomMILA())) {
            retorno += (this.getOutnumberDomMILA() + " ");
        }
        if (StringUtils.isNotBlank(this.getInteriornumberDomMILA())) {
            retorno += this.getInteriornumberDomMILA();
        }
        if (StringUtils.isNotBlank(this.getPostalcodeDomMILA())) {
            retorno += "," + this.getPostalcodeDomMILA();
        }
        if (StringUtils.isNotBlank(this.getCitytownDomMILA())) {
            retorno += "," + this.getCitytownDomMILA();
        }
        if (StringUtils.isNotBlank(this.getStateprovinceDomMILA())) {
            retorno += "," + this.getStateprovinceDomMILA();
        }
        return retorno;
    }

}
