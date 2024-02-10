/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.formatosw;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.W8BENEUtil;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Formato W8BEN-E
 * 
 * @author Pablo Balderas
 */
public class FormaW8BENE extends FormaGeneral implements Serializable {

    /** Id para la serialización */
    private static final long serialVersionUID = 2018434817399473188L;

    private String partIcmp1;
    private String partIcmp2;
    private String partIcmp3;
    private boolean partIcmp4a;
    private boolean partIcmp4b;
    private boolean partIcmp4c;
    private boolean partIcmp4d;
    private boolean partIcmp4e;
    private boolean partIcmp4f;
    private boolean partIcmp4g;
    private boolean partIcmp4h;
    private boolean partIcmp4i;
    private boolean partIcmp4j;
    private boolean partIcmp4k;
    private boolean partIcmp4l;
    private boolean partIcmp4m;
    // actualizacion 2016
    private boolean partIcmp4n;

    private boolean partIcmp5a;
    private boolean partIcmp5b;
    private boolean partIcmp5c;
    private boolean partIcmp5d;
    private boolean partIcmp5e;
    private boolean partIcmp5f;
    private boolean partIcmp5g;
    private boolean partIcmp5h;
    private boolean partIcmp5i;
    private boolean partIcmp5j;
    private boolean partIcmp5k;
    private boolean partIcmp5l;
    private boolean partIcmp5m;
    private boolean partIcmp5n;
    private boolean partIcmp5o;
    private boolean partIcmp5p;
    private boolean partIcmp5q;
    private boolean partIcmp5r;
    private boolean partIcmp5s;
    private boolean partIcmp5t;
    private boolean partIcmp5u;
    private boolean partIcmp5v;
    private boolean partIcmp5w;
    private boolean partIcmp5x;
    private boolean partIcmp5y;
    private boolean partIcmp5z;
    private boolean partIcmp5aa;
    private boolean partIcmp5ab;
    private boolean partIcmp5ac;
    private boolean partIcmp5ad;
    private boolean partIcmp5ae;
    private boolean partIcmp5af;

    private String partIcmp6a;
    private String partIcmp6b;
    private String partIcmp6c;
    private String partIcmp7a;
    private String partIcmp7b;
    private String partIcmp7c;
    private String partIcmp8;

    private boolean partIcmp9a1;
    private String partIcmp9a2;
    private boolean partIcmp9b1;
    private String partIcmp9b2;

    private String partIcmp10;

    private boolean partIIcmp11a;
    private boolean partIIcmp11b;
    private boolean partIIcmp11c;
    private boolean partIIcmp11d;
    private boolean partIIcmp11e;

    private String partIIcmp12a;
    private String partIIcmp12b;
    private String partIIcmp12c;
    private String partIIcmp13;

    private boolean partIII;
    private boolean partIIIcmp14a;
    private String partIIIcmp14a1;
    private boolean partIIIcmp14b;
    // actualizacion 2016
    private boolean partIIIcmp14ba;
    private boolean partIIIcmp14bb;
    private boolean partIIIcmp14bc;
    private boolean partIIIcmp14bd;
    private boolean partIIIcmp14be;
    private boolean partIIIcmp14bf;
    private boolean partIIIcmp14bg;
    private boolean partIIIcmp14bh;
    private boolean partIIIcmp14bi;
    private boolean partIIIcmp14bj;
    private String partIIIcmp14bk;

    private boolean partIIIcmp14c;
    private String partIIIcmp15a;
    private String partIIIcmp15b;
    private String partIIIcmp15c;
    private String partIIIcmp15d1;
    private String partIIIcmp15d2;
    private String partIIIcmp15d3;

    private boolean partIV;
    private String partIVcmp16;
    private String partIVcmp16a;
    private boolean partIVcmp17a;
    private boolean partIVcmp17b;

    private boolean partV;
    private boolean partVcmp18;

    private boolean partVI;
    private boolean partVIcmp19;

    private boolean partVII;
    private String partVIIcmp20;
    private boolean partVIIcmp21;

    private boolean partVIII;
    private boolean partVIIIcmp22;

    private boolean partIX;
    private boolean partIXcmp23;

    private boolean partX;
    private boolean partXcmp24a;
    private boolean partXcmp24b;
    private boolean partXcmp24c;
    private boolean partXcmp24d;

    private boolean partXI;
    private boolean partXIcmp25a;
    private boolean partXIcmp25b;
    private boolean partXIcmp25c;

    private boolean partXII;
    private boolean partXIIcmp26a;
    private String partXIIcmp26b;

    private boolean partXIIcmp26ba;
    private boolean partXIIcmp26bb;

    private String partXIIcmp26c;
    private String partXIIcmp26d;
    private String partXIIcmp26e;

    private boolean partXIII;
    private boolean partXIIIcmp27;

    private boolean partXIV;
    private boolean partXIVcmp28a;
    private boolean partXIVcmp28b;

    private boolean partXV;
    private boolean partXVcmp29a;
    private boolean partXVcmp29b;
    private boolean partXVcmp29c;
    private boolean partXVcmp29d;
    private boolean partXVcmp29e;
    private boolean partXVcmp29f;

    private boolean partXVI;
    private boolean partXVIcmp30;

    private boolean partXVII;
    private boolean partXVIIcmp31;

    private boolean partXVIII;
    private boolean partXVIIIcmp32;

    private boolean partXIX;
    private boolean partXIXcmp33a;
    private String partXIXcmp33b;

    private boolean partXX;
    private boolean partXXcmp34a;
    private String partXXcmp34b;

    private boolean partXXI;
    private boolean partXXIcmp35a;
    private String partXXIcmp35b;

    private boolean partXXII;
    private boolean partXXIIcmp36;

    private boolean partXXIII;
    private boolean partXXIIIcmp37a;
    private String partXXIIIcmp37b;
    private boolean partXXIIIcmp37c;
    private String partXXIIIcmp37d;
    private String partXXIIIcmp37e;

    private boolean partXXIV;
    private boolean partXXIVcmp38;

    private boolean partXXV;
    private boolean partXXVcmp39;

    private boolean partXXVI;
    private boolean partXXVIcmp40a;
    private boolean partXXVIcmp40b;
    private boolean partXXVIcmp40c;

    private boolean partXXVII;
    private boolean partXXVIIcmp41;

    private boolean partXXVIII;
    private String partXXVIIIcmp42;
    // actualizacion 2016
    private String partXXVIIIcmp42_2;
    private boolean partXXVIIIcmp43;

    private boolean partXXIX;


    /** Indica si es SIEFORE-AFORE */
    private boolean SIEFORE_AFORE;

    /** Indica la pais de la direccion residencial */
    private String paisResidencial;

    /** Indica quien capturo el formato */
    private String printName;

    /** Indica si debe habilitar o deshabilitar el campo 7 */
    private boolean inhabilitarPartIcmp7;

    /** Banderas que indican si se habilitan o deshabilitan las opciones del campo 4 */
    private boolean esCmp4a;
    private boolean esCmp4b;
    private boolean esCmp4c;
    private boolean esCmp4d;
    private boolean esCmp4e;
    private boolean esCmp4f;
    private boolean esCmp4g;
    private boolean esCmp4h;
    private boolean esCmp4i;
    private boolean esCmp4j;
    private boolean esCmp4k;
    // actualizacion 2016
    private boolean esCmp4n;

    /** Indica que el campo 9b es de sólo lectura */
    private boolean readOnlyCmp9b;

    /** Indica que el campo 14 es de sólo lectura */
    private boolean readOnlyCmp14;

    /** Indica que el campo 15c es de sólo lectura */
    private boolean readOnlyCmp15c;


    // act2016 part 30
    private String partXXIXnoma;
    private String partXXIXnomb;
    private String partXXIXnomc;
    private String partXXIXnomd;
    private String partXXIXnome;
    private String partXXIXnomf;
    private String partXXIXnomg;
    private String partXXIXnomh;
    private String partXXIXnomi;

    private String partXXIXadra;
    private String partXXIXadrb;
    private String partXXIXadrc;
    private String partXXIXadrd;
    private String partXXIXadre;
    private String partXXIXadrf;
    private String partXXIXadrg;
    private String partXXIXadrh;
    private String partXXIXadri;

    private String partXXIXtina;
    private String partXXIXtinb;
    private String partXXIXtinc;
    private String partXXIXtind;
    private String partXXIXtine;
    private String partXXIXtinf;
    private String partXXIXting;
    private String partXXIXtinh;
    private String partXXIXtini;
    private boolean partXXX;

    /**
     * Constructor de la clase
     */
    public FormaW8BENE() {}

    /**
     * Constructor de la clase
     * 
     * @param idCuentaNombrada Id de la cuenta nombrada del custodio.
     * @param idTipoBeneficiario Id del tipo de beneficiario.
     * @param idInstitucion Id de la institución.
     */
    public FormaW8BENE(final Long idCuentaNombrada, final Long idTipoBeneficiario,
            final Long idInstitucion) {
        super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
        // TODO: parteIcmp4n ver reglas
        // Inicializa el campo 4 de la parte I
        if (Constantes.PERSONA_MORAL_NO_EUA == idTipoBeneficiario) {
            this.partIcmp4a = true;
            this.habilitarDeshabilitarOpcionesCampo4(true);
        } else if (Constantes.SIEFORE_AFORE == idTipoBeneficiario) {
            this.SIEFORE_AFORE = true;
            this.partIcmp4j = true;
            this.habilitarDeshabilitarOpcionesCampo4(true);
            this.partIIIcmp15b = Constantes.CERO_STRING;
            this.partIIIcmp15d1 = BeneficiariosConstantes.LEYENDA_AFORE_W8BENE_1;
            this.partIIIcmp15d2 = BeneficiariosConstantes.LEYENDA_AFORE_W8BENE_2;
        } else if (Constantes.FIDEICOMISO_COMPLEJO == idTipoBeneficiario) {
            this.partIcmp4f = true;
            this.habilitarDeshabilitarOpcionesCampo4(true);
        } else if (BeneficiariosConstantes.OTROS_W8_BEN_E == idTipoBeneficiario) {
            this.habilitarDeshabilitarOpcionesCampo4(false);
        }
        // Inicializa el campo 10 de la parte I
        if (Constantes.CN_CLEARSTREAM == idCuentaNombrada) {
            this.partIcmp10 = BeneficiariosConstantes.REF_CLEARSTREAM;
        } else if (Constantes.CN_EUROCLEAR_BANK == idCuentaNombrada) {
            this.partIcmp10 = BeneficiariosConstantes.REF_EUROCLEAR_BANK;
        }
        // Inicializa el campo 14 y obliga a capturar Foreing TIN
        if (!(BeneficiariosConstantes.OTROS_W8_BEN_E == idTipoBeneficiario)) {
            this.readOnlyCmp14 = true;
            this.partIIIcmp14a = true;
            this.partIIIcmp14b = true;
            this.partIcmp9b1 = true;
            this.readOnlyCmp9b = true;
        }
        // Inicializa el campo 29
        this.partXXIX = true;
    }

    /**
     * Habilita o deshabilita las opciones del campo 4.
     * 
     * @param opcion true para deshabilitar; false para habilitar.
     */
    public void habilitarDeshabilitarOpcionesCampo4(final boolean opcion) {
        this.esCmp4a = opcion;
        this.esCmp4b = opcion;
        this.esCmp4c = opcion;
        this.esCmp4d = opcion;
        this.esCmp4e = opcion;
        this.esCmp4f = opcion;
        this.esCmp4g = opcion;
        this.esCmp4h = opcion;
        this.esCmp4i = opcion;
        this.esCmp4j = opcion;
        this.esCmp4k = opcion;
        this.esCmp4n = opcion;
    }

    @Override
    public Beneficiario construyeBO() {
        // Pasa a mayusculas
        W8BENEUtil.formaAMayusculas(this);
        // Crea el beneficiario
        Beneficiario beneficiario = new Beneficiario();
        this.construyeBOBasico(beneficiario);
        // Copia los campos de la forma
        this.copiarCampos();
        // Nombre del formato
        beneficiario.setTipoFormato(this.formato);
        // Tipo de beneficiario
        TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
        tipoBeneficiario.setIdTipoBeneficiario(this.idTipoBeneficiario);
        beneficiario.setTipoBeneficiario(tipoBeneficiario);
        // Indica que es persona moral
        beneficiario.setPersonaFisica(Boolean.FALSE);
        // Coloca la razón social
        beneficiario.setRazonSocial(this.razonSocial);
        // Cuenta Nombrada, esta dada por el custodio
        beneficiario.setIdCuentaNombrada(this.custodio);
        // Pais de incorporacion
        beneficiario.setPaisIncorporacion(this.pais);
        // Fecha del formato
        beneficiario.setFechaFormato(this.fechaFormato);
        // Fecha de registro
        beneficiario.setFechaRegistro(this.fechaRegistro);
        // Regresa el beneficiario
        return beneficiario;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portaldali.middleware.formatosw.FormaGeneral#construyeBO(com.indeval.
     * portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    @Override
    public Beneficiario construyeBO(final Beneficiario beneficiario) {
        // Pasa a mayusculas
        W8BENEUtil.formaAMayusculas(this);
        this.construyeBOBasico(beneficiario);
        // Copia los campos de la forma
        this.copiarCampos();
        // Nombre del formato
        beneficiario.setTipoFormato(this.formato);
        // Tipo de beneficiario
        TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
        tipoBeneficiario.setIdTipoBeneficiario(this.idTipoBeneficiario);
        beneficiario.setTipoBeneficiario(tipoBeneficiario);
        // Indica que es persona moral
        beneficiario.setPersonaFisica(Boolean.FALSE);
        // Coloca la razón social
        beneficiario.setRazonSocial(this.razonSocial);
        // Cuenta Nombrada, esta dada por el custodio
        beneficiario.setIdCuentaNombrada(this.custodio);
        // Pais de incorporacion
        beneficiario.setPaisIncorporacion(this.pais);
        // Fecha del formato
        beneficiario.setFechaFormato(this.fechaFormato);
        // Fecha de registro
        beneficiario.setFechaRegistro(this.fechaRegistro);
        // Regresa el beneficiario
        return beneficiario;
    }


    @Override
    public void construyeBean(final Beneficiario beneficiario, final boolean llenaCamposVacios) {
        super.construyeBean(beneficiario, llenaCamposVacios);
        // Domicilios
        if (beneficiario.getDomicilioW8Normal() != null) {
        	if(beneficiario.getOrigen()!=null && !beneficiario.getOrigen().equals(BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI)) {
        		this.partIcmp6a = beneficiario.getDomicilioW8Normal().getStreet() +" "+ 
                		(StringUtils.isNotBlank(beneficiario.getDomicilioW8Normal().getOuterNumber()) ? beneficiario.getDomicilioW8Normal().getOuterNumber() : Constantes.BEN_CADENA_VACIA);
                this.partIcmp6b = beneficiario.getDomicilioW8Normal().getCityTown() +" "+ 
                		(StringUtils.isNotBlank(beneficiario.getDomicilioW8Normal().getStateProvince()) ? beneficiario.getDomicilioW8Normal().getStateProvince() : Constantes.BEN_CADENA_VACIA) 
                		+ " "+ (StringUtils.isNotBlank(beneficiario.getDomicilioW8Normal().getPostalCode()) ? beneficiario.getDomicilioW8Normal().getPostalCode() : Constantes.BEN_CADENA_VACIA);
                this.partIcmp6c = beneficiario.getDomicilioW8Normal().getCountry();
        	}else {
        		 this.partIcmp6a = beneficiario.getDomicilioW8Normal().getStreet();
                 this.partIcmp6b = beneficiario.getDomicilioW8Normal().getCityTown();
                 this.partIcmp6c = beneficiario.getDomicilioW8Normal().getCountry();
        	}
        }
        if (beneficiario.getDomicilioW8Correo() != null) {
        	if(beneficiario.getOrigen()!=null && !beneficiario.getOrigen().equals(BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI)) {
        		this.partIcmp7a = beneficiario.getDomicilioW8Correo().getStreet() +" "+ 
                		(StringUtils.isNotBlank(beneficiario.getDomicilioW8Correo().getOuterNumber()) ? beneficiario.getDomicilioW8Correo().getOuterNumber() : Constantes.BEN_CADENA_VACIA);
                this.partIcmp7b = beneficiario.getDomicilioW8Correo().getCityTown() +" "+ 
                		(StringUtils.isNotBlank(beneficiario.getDomicilioW8Correo().getStateProvince()) ? beneficiario.getDomicilioW8Correo().getStateProvince() : Constantes.BEN_CADENA_VACIA)
                		+ " "+ (StringUtils.isNotBlank(beneficiario.getDomicilioW8Correo().getPostalCode()) ? beneficiario.getDomicilioW8Correo().getPostalCode() : Constantes.BEN_CADENA_VACIA);
                this.partIcmp7c = beneficiario.getDomicilioW8Correo().getCountry();
        	}else {
        		this.partIcmp7a = beneficiario.getDomicilioW8Correo().getStreet();
                this.partIcmp7b = beneficiario.getDomicilioW8Correo().getCityTown();
                this.partIcmp7c = beneficiario.getDomicilioW8Correo().getCountry();
        	}
        }
        // País residencial
        this.paisResidencial = this.partIIIcmp14a1;
        W8BENEUtil.obtenerTipoBeneficiario(this);
        if (llenaCamposVacios) {
            this.ponerValoresVacios();
        }
    }

    /**
     * Método que coloca valores por omisión cuando los campos son vacios.
     */
    private void ponerValoresVacios() {
        this.partIcmp7a =
                StringUtils.isNotBlank(this.partIcmp7a) ? this.partIcmp7a
                        : Constantes.BEN_CADENA_VACIA;
        this.partIcmp7b =
                StringUtils.isNotBlank(this.partIcmp7b) ? this.partIcmp7b
                        : Constantes.BEN_CADENA_VACIA;
        this.partIcmp7c =
                StringUtils.isNotBlank(this.partIcmp7c) ? this.partIcmp7c
                        : Constantes.BEN_CADENA_VACIA;
    }

    /**
     * Copia los campos de la forma a los del beneficiario.
     */
    private void copiarCampos() {
        // Razón social
        this.razonSocial = this.partIcmp1;
        // País de incorporación u organización
        this.pais = this.partIcmp2;
        // País residencial
        this.paisResidencial = this.partIIIcmp14a1;
    }

    /**
     * Método para obtener el atributo partIcmp1
     * 
     * @return El atributo partIcmp1
     */
    public String getPartIcmp1() {
        return this.partIcmp1;
    }

    /**
     * Método para establecer el atributo partIcmp1
     * 
     * @param partIcmp1 El valor del atributo partIcmp1 a establecer.
     */
    public void setPartIcmp1(final String partIcmp1) {
        this.partIcmp1 = partIcmp1;
    }

    /**
     * Método para obtener el atributo partIcmp2
     * 
     * @return El atributo partIcmp2
     */
    public String getPartIcmp2() {
        return this.partIcmp2;
    }

    /**
     * Método para establecer el atributo partIcmp2
     * 
     * @param partIcmp2 El valor del atributo partIcmp2 a establecer.
     */
    public void setPartIcmp2(final String partIcmp2) {
        this.partIcmp2 = partIcmp2;
    }

    /**
     * Método para obtener el atributo partIcmp3
     * 
     * @return El atributo partIcmp3
     */
    public String getPartIcmp3() {
        return this.partIcmp3;
    }

    /**
     * Método para establecer el atributo partIcmp3
     * 
     * @param partIcmp3 El valor del atributo partIcmp3 a establecer.
     */
    public void setPartIcmp3(final String partIcmp3) {
        this.partIcmp3 = partIcmp3;
    }

    /**
     * Método para obtener el atributo partIcmp4a
     * 
     * @return El atributo partIcmp4a
     */
    public boolean isPartIcmp4a() {
        return this.partIcmp4a;
    }

    /**
     * Método para establecer el atributo partIcmp4a
     * 
     * @param partIcmp4a El valor del atributo partIcmp4a a establecer.
     */
    public void setPartIcmp4a(final boolean partIcmp4a) {
        this.partIcmp4a = partIcmp4a;
    }

    /**
     * Método para obtener el atributo partIcmp4b
     * 
     * @return El atributo partIcmp4b
     */
    public boolean isPartIcmp4b() {
        return this.partIcmp4b;
    }

    /**
     * Método para establecer el atributo partIcmp4b
     * 
     * @param partIcmp4b El valor del atributo partIcmp4b a establecer.
     */
    public void setPartIcmp4b(final boolean partIcmp4b) {
        this.partIcmp4b = partIcmp4b;
    }

    /**
     * Método para obtener el atributo partIcmp4c
     * 
     * @return El atributo partIcmp4c
     */
    public boolean isPartIcmp4c() {
        return this.partIcmp4c;
    }

    /**
     * Método para establecer el atributo partIcmp4c
     * 
     * @param partIcmp4c El valor del atributo partIcmp4c a establecer.
     */
    public void setPartIcmp4c(final boolean partIcmp4c) {
        this.partIcmp4c = partIcmp4c;
    }

    /**
     * Método para obtener el atributo partIcmp4d
     * 
     * @return El atributo partIcmp4d
     */
    public boolean isPartIcmp4d() {
        return this.partIcmp4d;
    }

    /**
     * Método para establecer el atributo partIcmp4d
     * 
     * @param partIcmp4d El valor del atributo partIcmp4d a establecer.
     */
    public void setPartIcmp4d(final boolean partIcmp4d) {
        this.partIcmp4d = partIcmp4d;
    }

    /**
     * Método para obtener el atributo partIcmp4e
     * 
     * @return El atributo partIcmp4e
     */
    public boolean isPartIcmp4e() {
        return this.partIcmp4e;
    }

    /**
     * Método para establecer el atributo partIcmp4e
     * 
     * @param partIcmp4e El valor del atributo partIcmp4e a establecer.
     */
    public void setPartIcmp4e(final boolean partIcmp4e) {
        this.partIcmp4e = partIcmp4e;
    }

    /**
     * Método para obtener el atributo partIcmp4f
     * 
     * @return El atributo partIcmp4f
     */
    public boolean isPartIcmp4f() {
        return this.partIcmp4f;
    }

    /**
     * Método para establecer el atributo partIcmp4f
     * 
     * @param partIcmp4f El valor del atributo partIcmp4f a establecer.
     */
    public void setPartIcmp4f(final boolean partIcmp4f) {
        this.partIcmp4f = partIcmp4f;
    }

    /**
     * Método para obtener el atributo partIcmp4g
     * 
     * @return El atributo partIcmp4g
     */
    public boolean isPartIcmp4g() {
        return this.partIcmp4g;
    }

    /**
     * Método para establecer el atributo partIcmp4g
     * 
     * @param partIcmp4g El valor del atributo partIcmp4g a establecer.
     */
    public void setPartIcmp4g(final boolean partIcmp4g) {
        this.partIcmp4g = partIcmp4g;
    }

    /**
     * Método para obtener el atributo partIcmp4h
     * 
     * @return El atributo partIcmp4h
     */
    public boolean isPartIcmp4h() {
        return this.partIcmp4h;
    }

    /**
     * Método para establecer el atributo partIcmp4h
     * 
     * @param partIcmp4h El valor del atributo partIcmp4h a establecer.
     */
    public void setPartIcmp4h(final boolean partIcmp4h) {
        this.partIcmp4h = partIcmp4h;
    }

    /**
     * Método para obtener el atributo partIcmp4i
     * 
     * @return El atributo partIcmp4i
     */
    public boolean isPartIcmp4i() {
        return this.partIcmp4i;
    }

    /**
     * Método para establecer el atributo partIcmp4i
     * 
     * @param partIcmp4i El valor del atributo partIcmp4i a establecer.
     */
    public void setPartIcmp4i(final boolean partIcmp4i) {
        this.partIcmp4i = partIcmp4i;
    }

    /**
     * Método para obtener el atributo partIcmp4j
     * 
     * @return El atributo partIcmp4j
     */
    public boolean isPartIcmp4j() {
        return this.partIcmp4j;
    }

    /**
     * Método para establecer el atributo partIcmp4j
     * 
     * @param partIcmp4j El valor del atributo partIcmp4j a establecer.
     */
    public void setPartIcmp4j(final boolean partIcmp4j) {
        this.partIcmp4j = partIcmp4j;
    }

    /**
     * Método para obtener el atributo partIcmp4k
     * 
     * @return El atributo partIcmp4k
     */
    public boolean isPartIcmp4k() {
        return this.partIcmp4k;
    }

    /**
     * Método para establecer el atributo partIcmp4k
     * 
     * @param partIcmp4k El valor del atributo partIcmp4k a establecer.
     */
    public void setPartIcmp4k(final boolean partIcmp4k) {
        this.partIcmp4k = partIcmp4k;
    }

    /**
     * Método para obtener el atributo partIcmp4l
     * 
     * @return El atributo partIcmp4l
     */
    public boolean isPartIcmp4l() {
        return this.partIcmp4l;
    }

    /**
     * Método para establecer el atributo partIcmp4l
     * 
     * @param partIcmp4l El valor del atributo partIcmp4l a establecer.
     */
    public void setPartIcmp4l(final boolean partIcmp4l) {
        this.partIcmp4l = partIcmp4l;
    }

    /**
     * Método para obtener el atributo partIcmp4m
     * 
     * @return El atributo partIcmp4m
     */
    public boolean isPartIcmp4m() {
        return this.partIcmp4m;
    }

    /**
     * Método para establecer el atributo partIcmp4m
     * 
     * @param partIcmp4m El valor del atributo partIcmp4m a establecer.
     */
    public void setPartIcmp4m(final boolean partIcmp4m) {
        this.partIcmp4m = partIcmp4m;
    }

    /**
     * Método para obtener el atributo partIcmp5a
     * 
     * @return El atributo partIcmp5a
     */
    public boolean isPartIcmp5a() {
        return this.partIcmp5a;
    }

    /**
     * Método para establecer el atributo partIcmp5a
     * 
     * @param partIcmp5a El valor del atributo partIcmp5a a establecer.
     */
    public void setPartIcmp5a(final boolean partIcmp5a) {
        this.partIcmp5a = partIcmp5a;
    }

    /**
     * Método para obtener el atributo partIcmp5b
     * 
     * @return El atributo partIcmp5b
     */
    public boolean isPartIcmp5b() {
        return this.partIcmp5b;
    }

    /**
     * Método para establecer el atributo partIcmp5b
     * 
     * @param partIcmp5b El valor del atributo partIcmp5b a establecer.
     */
    public void setPartIcmp5b(final boolean partIcmp5b) {
        this.partIcmp5b = partIcmp5b;
    }

    /**
     * Método para obtener el atributo partIcmp5c
     * 
     * @return El atributo partIcmp5c
     */
    public boolean isPartIcmp5c() {
        return this.partIcmp5c;
    }

    /**
     * Método para establecer el atributo partIcmp5c
     * 
     * @param partIcmp5c El valor del atributo partIcmp5c a establecer.
     */
    public void setPartIcmp5c(final boolean partIcmp5c) {
        this.partIcmp5c = partIcmp5c;
    }

    /**
     * Método para obtener el atributo partIcmp5d
     * 
     * @return El atributo partIcmp5d
     */
    public boolean isPartIcmp5d() {
        return this.partIcmp5d;
    }

    /**
     * Método para establecer el atributo partIcmp5d
     * 
     * @param partIcmp5d El valor del atributo partIcmp5d a establecer.
     */
    public void setPartIcmp5d(final boolean partIcmp5d) {
        this.partIcmp5d = partIcmp5d;
    }

    /**
     * Método para obtener el atributo partIcmp5e
     * 
     * @return El atributo partIcmp5e
     */
    public boolean isPartIcmp5e() {
        return this.partIcmp5e;
    }

    /**
     * Método para establecer el atributo partIcmp5e
     * 
     * @param partIcmp5e El valor del atributo partIcmp5e a establecer.
     */
    public void setPartIcmp5e(final boolean partIcmp5e) {
        this.partIcmp5e = partIcmp5e;
    }

    /**
     * Método para obtener el atributo partIcmp5f
     * 
     * @return El atributo partIcmp5f
     */
    public boolean isPartIcmp5f() {
        return this.partIcmp5f;
    }

    /**
     * Método para establecer el atributo partIcmp5f
     * 
     * @param partIcmp5f El valor del atributo partIcmp5f a establecer.
     */
    public void setPartIcmp5f(final boolean partIcmp5f) {
        this.partIcmp5f = partIcmp5f;
    }

    /**
     * Método para obtener el atributo partIcmp5g
     * 
     * @return El atributo partIcmp5g
     */
    public boolean isPartIcmp5g() {
        return this.partIcmp5g;
    }

    /**
     * Método para establecer el atributo partIcmp5g
     * 
     * @param partIcmp5g El valor del atributo partIcmp5g a establecer.
     */
    public void setPartIcmp5g(final boolean partIcmp5g) {
        this.partIcmp5g = partIcmp5g;
    }

    /**
     * Método para obtener el atributo partIcmp5h
     * 
     * @return El atributo partIcmp5h
     */
    public boolean isPartIcmp5h() {
        return this.partIcmp5h;
    }

    /**
     * Método para establecer el atributo partIcmp5h
     * 
     * @param partIcmp5h El valor del atributo partIcmp5h a establecer.
     */
    public void setPartIcmp5h(final boolean partIcmp5h) {
        this.partIcmp5h = partIcmp5h;
    }

    /**
     * Método para obtener el atributo partIcmp5i
     * 
     * @return El atributo partIcmp5i
     */
    public boolean isPartIcmp5i() {
        return this.partIcmp5i;
    }

    /**
     * Método para establecer el atributo partIcmp5i
     * 
     * @param partIcmp5i El valor del atributo partIcmp5i a establecer.
     */
    public void setPartIcmp5i(final boolean partIcmp5i) {
        this.partIcmp5i = partIcmp5i;
    }

    /**
     * Método para obtener el atributo partIcmp5j
     * 
     * @return El atributo partIcmp5j
     */
    public boolean isPartIcmp5j() {
        return this.partIcmp5j;
    }

    /**
     * Método para establecer el atributo partIcmp5j
     * 
     * @param partIcmp5j El valor del atributo partIcmp5j a establecer.
     */
    public void setPartIcmp5j(final boolean partIcmp5j) {
        this.partIcmp5j = partIcmp5j;
    }

    /**
     * Método para obtener el atributo partIcmp5k
     * 
     * @return El atributo partIcmp5k
     */
    public boolean isPartIcmp5k() {
        return this.partIcmp5k;
    }

    /**
     * Método para establecer el atributo partIcmp5k
     * 
     * @param partIcmp5k El valor del atributo partIcmp5k a establecer.
     */
    public void setPartIcmp5k(final boolean partIcmp5k) {
        this.partIcmp5k = partIcmp5k;
    }

    /**
     * Método para obtener el atributo partIcmp5l
     * 
     * @return El atributo partIcmp5l
     */
    public boolean isPartIcmp5l() {
        return this.partIcmp5l;
    }

    /**
     * Método para establecer el atributo partIcmp5l
     * 
     * @param partIcmp5l El valor del atributo partIcmp5l a establecer.
     */
    public void setPartIcmp5l(final boolean partIcmp5l) {
        this.partIcmp5l = partIcmp5l;
    }

    /**
     * Método para obtener el atributo partIcmp5m
     * 
     * @return El atributo partIcmp5m
     */
    public boolean isPartIcmp5m() {
        return this.partIcmp5m;
    }

    /**
     * Método para establecer el atributo partIcmp5m
     * 
     * @param partIcmp5m El valor del atributo partIcmp5m a establecer.
     */
    public void setPartIcmp5m(final boolean partIcmp5m) {
        this.partIcmp5m = partIcmp5m;
    }

    /**
     * Método para obtener el atributo partIcmp5n
     * 
     * @return El atributo partIcmp5n
     */
    public boolean isPartIcmp5n() {
        return this.partIcmp5n;
    }

    /**
     * Método para establecer el atributo partIcmp5n
     * 
     * @param partIcmp5n El valor del atributo partIcmp5n a establecer.
     */
    public void setPartIcmp5n(final boolean partIcmp5n) {
        this.partIcmp5n = partIcmp5n;
    }

    /**
     * Método para obtener el atributo partIcmp5o
     * 
     * @return El atributo partIcmp5o
     */
    public boolean isPartIcmp5o() {
        return this.partIcmp5o;
    }

    /**
     * Método para establecer el atributo partIcmp5o
     * 
     * @param partIcmp5o El valor del atributo partIcmp5o a establecer.
     */
    public void setPartIcmp5o(final boolean partIcmp5o) {
        this.partIcmp5o = partIcmp5o;
    }

    /**
     * Método para obtener el atributo partIcmp5p
     * 
     * @return El atributo partIcmp5p
     */
    public boolean isPartIcmp5p() {
        return this.partIcmp5p;
    }

    /**
     * Método para establecer el atributo partIcmp5p
     * 
     * @param partIcmp5p El valor del atributo partIcmp5p a establecer.
     */
    public void setPartIcmp5p(final boolean partIcmp5p) {
        this.partIcmp5p = partIcmp5p;
    }

    /**
     * Método para obtener el atributo partIcmp5q
     * 
     * @return El atributo partIcmp5q
     */
    public boolean isPartIcmp5q() {
        return this.partIcmp5q;
    }

    /**
     * Método para establecer el atributo partIcmp5q
     * 
     * @param partIcmp5q El valor del atributo partIcmp5q a establecer.
     */
    public void setPartIcmp5q(final boolean partIcmp5q) {
        this.partIcmp5q = partIcmp5q;
    }

    /**
     * Método para obtener el atributo partIcmp5r
     * 
     * @return El atributo partIcmp5r
     */
    public boolean isPartIcmp5r() {
        return this.partIcmp5r;
    }

    /**
     * Método para establecer el atributo partIcmp5r
     * 
     * @param partIcmp5r El valor del atributo partIcmp5r a establecer.
     */
    public void setPartIcmp5r(final boolean partIcmp5r) {
        this.partIcmp5r = partIcmp5r;
    }

    /**
     * Método para obtener el atributo partIcmp5s
     * 
     * @return El atributo partIcmp5s
     */
    public boolean isPartIcmp5s() {
        return this.partIcmp5s;
    }

    /**
     * Método para establecer el atributo partIcmp5s
     * 
     * @param partIcmp5s El valor del atributo partIcmp5s a establecer.
     */
    public void setPartIcmp5s(final boolean partIcmp5s) {
        this.partIcmp5s = partIcmp5s;
    }

    /**
     * Método para obtener el atributo partIcmp5t
     * 
     * @return El atributo partIcmp5t
     */
    public boolean isPartIcmp5t() {
        return this.partIcmp5t;
    }

    /**
     * Método para establecer el atributo partIcmp5t
     * 
     * @param partIcmp5t El valor del atributo partIcmp5t a establecer.
     */
    public void setPartIcmp5t(final boolean partIcmp5t) {
        this.partIcmp5t = partIcmp5t;
    }

    /**
     * Método para obtener el atributo partIcmp5u
     * 
     * @return El atributo partIcmp5u
     */
    public boolean isPartIcmp5u() {
        return this.partIcmp5u;
    }

    /**
     * Método para establecer el atributo partIcmp5u
     * 
     * @param partIcmp5u El valor del atributo partIcmp5u a establecer.
     */
    public void setPartIcmp5u(final boolean partIcmp5u) {
        this.partIcmp5u = partIcmp5u;
    }

    /**
     * Método para obtener el atributo partIcmp5v
     * 
     * @return El atributo partIcmp5v
     */
    public boolean isPartIcmp5v() {
        return this.partIcmp5v;
    }

    /**
     * Método para establecer el atributo partIcmp5v
     * 
     * @param partIcmp5v El valor del atributo partIcmp5v a establecer.
     */
    public void setPartIcmp5v(final boolean partIcmp5v) {
        this.partIcmp5v = partIcmp5v;
    }

    /**
     * Método para obtener el atributo partIcmp5w
     * 
     * @return El atributo partIcmp5w
     */
    public boolean isPartIcmp5w() {
        return this.partIcmp5w;
    }

    /**
     * Método para establecer el atributo partIcmp5w
     * 
     * @param partIcmp5w El valor del atributo partIcmp5w a establecer.
     */
    public void setPartIcmp5w(final boolean partIcmp5w) {
        this.partIcmp5w = partIcmp5w;
    }

    /**
     * Método para obtener el atributo partIcmp5x
     * 
     * @return El atributo partIcmp5x
     */
    public boolean isPartIcmp5x() {
        return this.partIcmp5x;
    }

    /**
     * Método para establecer el atributo partIcmp5x
     * 
     * @param partIcmp5x El valor del atributo partIcmp5x a establecer.
     */
    public void setPartIcmp5x(final boolean partIcmp5x) {
        this.partIcmp5x = partIcmp5x;
    }

    /**
     * Método para obtener el atributo partIcmp5y
     * 
     * @return El atributo partIcmp5y
     */
    public boolean isPartIcmp5y() {
        return this.partIcmp5y;
    }

    /**
     * Método para establecer el atributo partIcmp5y
     * 
     * @param partIcmp5y El valor del atributo partIcmp5y a establecer.
     */
    public void setPartIcmp5y(final boolean partIcmp5y) {
        this.partIcmp5y = partIcmp5y;
    }

    /**
     * Método para obtener el atributo partIcmp5z
     * 
     * @return El atributo partIcmp5z
     */
    public boolean isPartIcmp5z() {
        return this.partIcmp5z;
    }

    /**
     * Método para establecer el atributo partIcmp5z
     * 
     * @param partIcmp5z El valor del atributo partIcmp5z a establecer.
     */
    public void setPartIcmp5z(final boolean partIcmp5z) {
        this.partIcmp5z = partIcmp5z;
    }

    /**
     * Método para obtener el atributo partIcmp5aa
     * 
     * @return El atributo partIcmp5aa
     */
    public boolean isPartIcmp5aa() {
        return this.partIcmp5aa;
    }

    /**
     * Método para establecer el atributo partIcmp5aa
     * 
     * @param partIcmp5aa El valor del atributo partIcmp5aa a establecer.
     */
    public void setPartIcmp5aa(final boolean partIcmp5aa) {
        this.partIcmp5aa = partIcmp5aa;
    }

    /**
     * Método para obtener el atributo partIcmp5ab
     * 
     * @return El atributo partIcmp5ab
     */
    public boolean isPartIcmp5ab() {
        return this.partIcmp5ab;
    }

    /**
     * Método para establecer el atributo partIcmp5ab
     * 
     * @param partIcmp5ab El valor del atributo partIcmp5ab a establecer.
     */
    public void setPartIcmp5ab(final boolean partIcmp5ab) {
        this.partIcmp5ab = partIcmp5ab;
    }

    /**
     * Método para obtener el atributo partIcmp5ac
     * 
     * @return El atributo partIcmp5ac
     */
    public boolean isPartIcmp5ac() {
        return this.partIcmp5ac;
    }

    /**
     * Método para establecer el atributo partIcmp5ac
     * 
     * @param partIcmp5ac El valor del atributo partIcmp5ac a establecer.
     */
    public void setPartIcmp5ac(final boolean partIcmp5ac) {
        this.partIcmp5ac = partIcmp5ac;
    }

    /**
     * Método para obtener el atributo partIcmp5ad
     * 
     * @return El atributo partIcmp5ad
     */
    public boolean isPartIcmp5ad() {
        return this.partIcmp5ad;
    }

    /**
     * Método para establecer el atributo partIcmp5ad
     * 
     * @param partIcmp5ad El valor del atributo partIcmp5ad a establecer.
     */
    public void setPartIcmp5ad(final boolean partIcmp5ad) {
        this.partIcmp5ad = partIcmp5ad;
    }

    /**
     * Método para obtener el atributo partIcmp5ae
     * 
     * @return El atributo partIcmp5ae
     */
    public boolean isPartIcmp5ae() {
        return this.partIcmp5ae;
    }

    /**
     * Método para establecer el atributo partIcmp5ae
     * 
     * @param partIcmp5ae El valor del atributo partIcmp5ae a establecer.
     */
    public void setPartIcmp5ae(final boolean partIcmp5ae) {
        this.partIcmp5ae = partIcmp5ae;
    }

    /**
     * Método para obtener el atributo partIcmp6a
     * 
     * @return El atributo partIcmp6a
     */
    public String getPartIcmp6a() {
        return this.partIcmp6a;
    }

    /**
     * Método para establecer el atributo partIcmp6a
     * 
     * @param partIcmp6a El valor del atributo partIcmp6a a establecer.
     */
    public void setPartIcmp6a(final String partIcmp6a) {
        this.partIcmp6a = partIcmp6a;
    }

    /**
     * Método para obtener el atributo partIcmp6b
     * 
     * @return El atributo partIcmp6b
     */
    public String getPartIcmp6b() {
        return this.partIcmp6b;
    }

    /**
     * Método para establecer el atributo partIcmp6b
     * 
     * @param partIcmp6b El valor del atributo partIcmp6b a establecer.
     */
    public void setPartIcmp6b(final String partIcmp6b) {
        this.partIcmp6b = partIcmp6b;
    }

    /**
     * Método para obtener el atributo partIcmp6c
     * 
     * @return El atributo partIcmp6c
     */
    public String getPartIcmp6c() {
        return this.partIcmp6c;
    }

    /**
     * Método para establecer el atributo partIcmp6c
     * 
     * @param partIcmp6c El valor del atributo partIcmp6c a establecer.
     */
    public void setPartIcmp6c(final String partIcmp6c) {
        this.partIcmp6c = partIcmp6c;
    }

    /**
     * Método para obtener el atributo partIcmp7a
     * 
     * @return El atributo partIcmp7a
     */
    public String getPartIcmp7a() {
        return this.partIcmp7a;
    }

    /**
     * Método para establecer el atributo partIcmp7a
     * 
     * @param partIcmp7a El valor del atributo partIcmp7a a establecer.
     */
    public void setPartIcmp7a(final String partIcmp7a) {
        this.partIcmp7a = partIcmp7a;
    }

    /**
     * Método para obtener el atributo partIcmp7b
     * 
     * @return El atributo partIcmp7b
     */
    public String getPartIcmp7b() {
        return this.partIcmp7b;
    }

    /**
     * Método para establecer el atributo partIcmp7b
     * 
     * @param partIcmp7b El valor del atributo partIcmp7b a establecer.
     */
    public void setPartIcmp7b(final String partIcmp7b) {
        this.partIcmp7b = partIcmp7b;
    }

    /**
     * Método para obtener el atributo partIcmp7c
     * 
     * @return El atributo partIcmp7c
     */
    public String getPartIcmp7c() {
        return this.partIcmp7c;
    }

    /**
     * Método para establecer el atributo partIcmp7c
     * 
     * @param partIcmp7c El valor del atributo partIcmp7c a establecer.
     */
    public void setPartIcmp7c(final String partIcmp7c) {
        this.partIcmp7c = partIcmp7c;
    }

    /**
     * Método para obtener el atributo partIcmp8
     * 
     * @return El atributo partIcmp8
     */
    public String getPartIcmp8() {
        return this.partIcmp8;
    }

    /**
     * Método para establecer el atributo partIcmp8
     * 
     * @param partIcmp8 El valor del atributo partIcmp8 a establecer.
     */
    public void setPartIcmp8(final String partIcmp8) {
        this.partIcmp8 = partIcmp8;
    }

    /**
     * Método para obtener el atributo partIcmp9a1
     * 
     * @return El atributo partIcmp9a1
     */
    public boolean isPartIcmp9a1() {
        return this.partIcmp9a1;
    }

    /**
     * Método para establecer el atributo partIcmp9a1
     * 
     * @param partIcmp9a1 El valor del atributo partIcmp9a1 a establecer.
     */
    public void setPartIcmp9a1(final boolean partIcmp9a1) {
        this.partIcmp9a1 = partIcmp9a1;
    }

    /**
     * Método para obtener el atributo partIcmp9a2
     * 
     * @return El atributo partIcmp9a2
     */
    public String getPartIcmp9a2() {
        return this.partIcmp9a2;
    }

    /**
     * Método para establecer el atributo partIcmp9a2
     * 
     * @param partIcmp9a2 El valor del atributo partIcmp9a2 a establecer.
     */
    public void setPartIcmp9a2(final String partIcmp9a2) {
        this.partIcmp9a2 = partIcmp9a2;
    }

    /**
     * Método para obtener el atributo partIcmp9b1
     * 
     * @return El atributo partIcmp9b1
     */
    public boolean isPartIcmp9b1() {
        return this.partIcmp9b1;
    }

    /**
     * Método para establecer el atributo partIcmp9b1
     * 
     * @param partIcmp9b1 El valor del atributo partIcmp9b1 a establecer.
     */
    public void setPartIcmp9b1(final boolean partIcmp9b1) {
        this.partIcmp9b1 = partIcmp9b1;
    }

    /**
     * Método para obtener el atributo partIcmp9b2
     * 
     * @return El atributo partIcmp9b2
     */
    public String getPartIcmp9b2() {
        return this.partIcmp9b2;
    }

    /**
     * Método para establecer el atributo partIcmp9b2
     * 
     * @param partIcmp9b2 El valor del atributo partIcmp9b2 a establecer.
     */
    public void setPartIcmp9b2(final String partIcmp9b2) {
        this.partIcmp9b2 = partIcmp9b2;
    }

    /**
     * Método para obtener el atributo partIcmp10
     * 
     * @return El atributo partIcmp10
     */
    public String getPartIcmp10() {
        return this.partIcmp10;
    }

    /**
     * Método para establecer el atributo partIcmp10
     * 
     * @param partIcmp10 El valor del atributo partIcmp10 a establecer.
     */
    public void setPartIcmp10(final String partIcmp10) {
        this.partIcmp10 = partIcmp10;
    }

    /**
     * Método para obtener el atributo partIIcmp11a
     * 
     * @return El atributo partIIcmp11a
     */
    public boolean isPartIIcmp11a() {
        return this.partIIcmp11a;
    }

    /**
     * Método para establecer el atributo partIIcmp11a
     * 
     * @param partIIcmp11a El valor del atributo partIIcmp11a a establecer.
     */
    public void setPartIIcmp11a(final boolean partIIcmp11a) {
        this.partIIcmp11a = partIIcmp11a;
    }

    /**
     * Método para obtener el atributo partIIcmp11b
     * 
     * @return El atributo partIIcmp11b
     */
    public boolean isPartIIcmp11b() {
        return this.partIIcmp11b;
    }

    /**
     * Método para establecer el atributo partIIcmp11b
     * 
     * @param partIIcmp11b El valor del atributo partIIcmp11b a establecer.
     */
    public void setPartIIcmp11b(final boolean partIIcmp11b) {
        this.partIIcmp11b = partIIcmp11b;
    }

    /**
     * Método para obtener el atributo partIIcmp11c
     * 
     * @return El atributo partIIcmp11c
     */
    public boolean isPartIIcmp11c() {
        return this.partIIcmp11c;
    }

    /**
     * Método para establecer el atributo partIIcmp11c
     * 
     * @param partIIcmp11c El valor del atributo partIIcmp11c a establecer.
     */
    public void setPartIIcmp11c(final boolean partIIcmp11c) {
        this.partIIcmp11c = partIIcmp11c;
    }

    /**
     * Método para obtener el atributo partIIcmp11d
     * 
     * @return El atributo partIIcmp11d
     */
    public boolean isPartIIcmp11d() {
        return this.partIIcmp11d;
    }

    /**
     * Método para establecer el atributo partIIcmp11d
     * 
     * @param partIIcmp11d El valor del atributo partIIcmp11d a establecer.
     */
    public void setPartIIcmp11d(final boolean partIIcmp11d) {
        this.partIIcmp11d = partIIcmp11d;
    }

    /**
     * Método para obtener el atributo partIIcmp11e
     * 
     * @return El atributo partIIcmp11e
     */
    public boolean isPartIIcmp11e() {
        return this.partIIcmp11e;
    }

    /**
     * Método para establecer el atributo partIIcmp11e
     * 
     * @param partIIcmp11e El valor del atributo partIIcmp11e a establecer.
     */
    public void setPartIIcmp11e(final boolean partIIcmp11e) {
        this.partIIcmp11e = partIIcmp11e;
    }

    /**
     * Método para obtener el atributo partIIcmp12a
     * 
     * @return El atributo partIIcmp12a
     */
    public String getPartIIcmp12a() {
        return this.partIIcmp12a;
    }

    /**
     * Método para establecer el atributo partIIcmp12a
     * 
     * @param partIIcmp12a El valor del atributo partIIcmp12a a establecer.
     */
    public void setPartIIcmp12a(final String partIIcmp12a) {
        this.partIIcmp12a = partIIcmp12a;
    }

    /**
     * Método para obtener el atributo partIIcmp12b
     * 
     * @return El atributo partIIcmp12b
     */
    public String getPartIIcmp12b() {
        return this.partIIcmp12b;
    }

    /**
     * Método para establecer el atributo partIIcmp12b
     * 
     * @param partIIcmp12b El valor del atributo partIIcmp12b a establecer.
     */
    public void setPartIIcmp12b(final String partIIcmp12b) {
        this.partIIcmp12b = partIIcmp12b;
    }

    /**
     * Método para obtener el atributo partIIcmp12c
     * 
     * @return El atributo partIIcmp12c
     */
    public String getPartIIcmp12c() {
        return this.partIIcmp12c;
    }

    /**
     * Método para establecer el atributo partIIcmp12c
     * 
     * @param partIIcmp12c El valor del atributo partIIcmp12c a establecer.
     */
    public void setPartIIcmp12c(final String partIIcmp12c) {
        this.partIIcmp12c = partIIcmp12c;
    }

    /**
     * Método para obtener el atributo partIIcmp13
     * 
     * @return El atributo partIIcmp13
     */
    public String getPartIIcmp13() {
        return this.partIIcmp13;
    }

    /**
     * Método para establecer el atributo partIIcmp13
     * 
     * @param partIIcmp13 El valor del atributo partIIcmp13 a establecer.
     */
    public void setPartIIcmp13(final String partIIcmp13) {
        this.partIIcmp13 = partIIcmp13;
    }

    /**
     * Método para obtener el atributo partIII
     * 
     * @return El atributo partIII
     */
    public boolean isPartIII() {
        return this.partIII;
    }

    /**
     * Método para establecer el atributo partIII
     * 
     * @param partIII El valor del atributo partIII a establecer.
     */
    public void setPartIII(final boolean partIII) {
        this.partIII = partIII;
    }

    /**
     * Método para obtener el atributo partIIIcmp14a
     * 
     * @return El atributo partIIIcmp14a
     */
    public boolean isPartIIIcmp14a() {
        return this.partIIIcmp14a;
    }

    /**
     * Método para establecer el atributo partIIIcmp14a
     * 
     * @param partIIIcmp14a El valor del atributo partIIIcmp14a a establecer.
     */
    public void setPartIIIcmp14a(final boolean partIIIcmp14a) {
        this.partIIIcmp14a = partIIIcmp14a;
    }

    /**
     * Método para obtener el atributo partIIIcmp14a1
     * 
     * @return El atributo partIIIcmp14a1
     */
    public String getPartIIIcmp14a1() {
        return this.partIIIcmp14a1;
    }

    /**
     * Método para establecer el atributo partIIIcmp14a1
     * 
     * @param partIIIcmp14a1 El valor del atributo partIIIcmp14a1 a establecer.
     */
    public void setPartIIIcmp14a1(final String partIIIcmp14a1) {
        this.partIIIcmp14a1 = partIIIcmp14a1;
    }

    /**
     * Método para obtener el atributo partIIIcmp14b
     * 
     * @return El atributo partIIIcmp14b
     */
    public boolean isPartIIIcmp14b() {
        return this.partIIIcmp14b;
    }

    /**
     * Método para establecer el atributo partIIIcmp14b
     * 
     * @param partIIIcmp14b El valor del atributo partIIIcmp14b a establecer.
     */
    public void setPartIIIcmp14b(final boolean partIIIcmp14b) {
        this.partIIIcmp14b = partIIIcmp14b;
    }

    /**
     * Método para obtener el atributo partIIIcmp14c
     * 
     * @return El atributo partIIIcmp14c
     */
    public boolean isPartIIIcmp14c() {
        return this.partIIIcmp14c;
    }

    /**
     * Método para establecer el atributo partIIIcmp14c
     * 
     * @param partIIIcmp14c El valor del atributo partIIIcmp14c a establecer.
     */
    public void setPartIIIcmp14c(final boolean partIIIcmp14c) {
        this.partIIIcmp14c = partIIIcmp14c;
    }

    /**
     * Método para obtener el atributo partIIIcmp15a
     * 
     * @return El atributo partIIIcmp15a
     */
    public String getPartIIIcmp15a() {
        return this.partIIIcmp15a;
    }

    /**
     * Método para establecer el atributo partIIIcmp15a
     * 
     * @param partIIIcmp15a El valor del atributo partIIIcmp15a a establecer.
     */
    public void setPartIIIcmp15a(final String partIIIcmp15a) {
        this.partIIIcmp15a = partIIIcmp15a;
    }

    /**
     * Método para obtener el atributo partIIIcmp15b
     * 
     * @return El atributo partIIIcmp15b
     */
    public String getPartIIIcmp15b() {
        return this.partIIIcmp15b;
    }

    /**
     * Método para establecer el atributo partIIIcmp15b
     * 
     * @param partIIIcmp15b El valor del atributo partIIIcmp15b a establecer.
     */
    public void setPartIIIcmp15b(final String partIIIcmp15b) {
        this.partIIIcmp15b = partIIIcmp15b;
    }

    /**
     * Método para obtener el atributo partIIIcmp15c
     * 
     * @return El atributo partIIIcmp15c
     */
    public String getPartIIIcmp15c() {
        return this.partIIIcmp15c;
    }

    /**
     * Método para establecer el atributo partIIIcmp15c
     * 
     * @param partIIIcmp15c El valor del atributo partIIIcmp15c a establecer.
     */
    public void setPartIIIcmp15c(final String partIIIcmp15c) {
        this.partIIIcmp15c = partIIIcmp15c;
    }

    /**
     * Método para obtener el atributo partIIIcmp15d
     * 
     * @return El atributo partIIIcmp15d
     */
    public String getPartIIIcmp15d1() {
        return this.partIIIcmp15d1;
    }

    /**
     * Método para establecer el atributo partIIIcmp15d
     * 
     * @param partIIIcmp15d El valor del atributo partIIIcmp15d a establecer.
     */
    public void setPartIIIcmp15d2(final String partIIIcmp15d2) {
        this.partIIIcmp15d2 = partIIIcmp15d2;
    }

    /**
     * Método para obtener el atributo partIIIcmp15d
     * 
     * @return El atributo partIIIcmp15d
     */
    public String getPartIIIcmp15d2() {
        return this.partIIIcmp15d2;
    }

    /**
     * Método para establecer el atributo partIIIcmp15d
     * 
     * @param partIIIcmp15d El valor del atributo partIIIcmp15d a establecer.
     */
    public void setPartIIIcmp15d1(final String partIIIcmp15d1) {
        this.partIIIcmp15d1 = partIIIcmp15d1;
    }

    /**
     * Método para obtener el atributo partIV
     * 
     * @return El atributo partIV
     */
    public boolean isPartIV() {
        return this.partIV;
    }

    /**
     * Método para establecer el atributo partIV
     * 
     * @param partIV El valor del atributo partIV a establecer.
     */
    public void setPartIV(final boolean partIV) {
        this.partIV = partIV;
    }

    /**
     * Método para obtener el atributo partIVcmp16
     * 
     * @return El atributo partIVcmp16
     */
    public String getPartIVcmp16() {
        return this.partIVcmp16;
    }

    /**
     * Método para establecer el atributo partIVcmp16
     * 
     * @param partIVcmp16 El valor del atributo partIVcmp16 a establecer.
     */
    public void setPartIVcmp16(final String partIVcmp16) {
        this.partIVcmp16 = partIVcmp16;
    }

    /**
     * Método para obtener el atributo partIVcmp17a
     * 
     * @return El atributo partIVcmp17a
     */
    public boolean isPartIVcmp17a() {
        return this.partIVcmp17a;
    }

    /**
     * Método para establecer el atributo partIVcmp17a
     * 
     * @param partIVcmp17a El valor del atributo partIVcmp17a a establecer.
     */
    public void setPartIVcmp17a(final boolean partIVcmp17a) {
        this.partIVcmp17a = partIVcmp17a;
    }

    /**
     * Método para obtener el atributo partIVcmp17b
     * 
     * @return El atributo partIVcmp17b
     */
    public boolean isPartIVcmp17b() {
        return this.partIVcmp17b;
    }

    /**
     * Método para establecer el atributo partIVcmp17b
     * 
     * @param partIVcmp17b El valor del atributo partIVcmp17b a establecer.
     */
    public void setPartIVcmp17b(final boolean partIVcmp17b) {
        this.partIVcmp17b = partIVcmp17b;
    }

    /**
     * Método para obtener el atributo partV
     * 
     * @return El atributo partV
     */
    public boolean isPartV() {
        return this.partV;
    }

    /**
     * Método para establecer el atributo partV
     * 
     * @param partV El valor del atributo partV a establecer.
     */
    public void setPartV(final boolean partV) {
        this.partV = partV;
    }

    /**
     * Método para obtener el atributo partVcmp18
     * 
     * @return El atributo partVcmp18
     */
    public boolean isPartVcmp18() {
        return this.partVcmp18;
    }

    /**
     * Método para establecer el atributo partVcmp18
     * 
     * @param partVcmp18 El valor del atributo partVcmp18 a establecer.
     */
    public void setPartVcmp18(final boolean partVcmp18) {
        this.partVcmp18 = partVcmp18;
    }

    /**
     * Método para obtener el atributo partVI
     * 
     * @return El atributo partVI
     */
    public boolean isPartVI() {
        return this.partVI;
    }

    /**
     * Método para establecer el atributo partVI
     * 
     * @param partVI El valor del atributo partVI a establecer.
     */
    public void setPartVI(final boolean partVI) {
        this.partVI = partVI;
    }

    /**
     * Método para obtener el atributo partVIcmp19
     * 
     * @return El atributo partVIcmp19
     */
    public boolean isPartVIcmp19() {
        return this.partVIcmp19;
    }

    /**
     * Método para establecer el atributo partVIcmp19
     * 
     * @param partVIcmp19 El valor del atributo partVIcmp19 a establecer.
     */
    public void setPartVIcmp19(final boolean partVIcmp19) {
        this.partVIcmp19 = partVIcmp19;
    }

    /**
     * Método para obtener el atributo partVII
     * 
     * @return El atributo partVII
     */
    public boolean isPartVII() {
        return this.partVII;
    }

    /**
     * Método para establecer el atributo partVII
     * 
     * @param partVII El valor del atributo partVII a establecer.
     */
    public void setPartVII(final boolean partVII) {
        this.partVII = partVII;
    }

    /**
     * Método para obtener el atributo partVIIcmp20
     * 
     * @return El atributo partVIIcmp20
     */
    public String getPartVIIcmp20() {
        return this.partVIIcmp20;
    }

    /**
     * Método para establecer el atributo partVIIcmp20
     * 
     * @param partVIIcmp20 El valor del atributo partVIIcmp20 a establecer.
     */
    public void setPartVIIcmp20(final String partVIIcmp20) {
        this.partVIIcmp20 = partVIIcmp20;
    }

    /**
     * Método para obtener el atributo partVIIcmp21
     * 
     * @return El atributo partVIIcmp21
     */
    public boolean isPartVIIcmp21() {
        return this.partVIIcmp21;
    }

    /**
     * Método para establecer el atributo partVIIcmp21
     * 
     * @param partVIIcmp21 El valor del atributo partVIIcmp21 a establecer.
     */
    public void setPartVIIcmp21(final boolean partVIIcmp21) {
        this.partVIIcmp21 = partVIIcmp21;
    }

    /**
     * Método para obtener el atributo partVIII
     * 
     * @return El atributo partVIII
     */
    public boolean isPartVIII() {
        return this.partVIII;
    }

    /**
     * Método para establecer el atributo partVIII
     * 
     * @param partVIII El valor del atributo partVIII a establecer.
     */
    public void setPartVIII(final boolean partVIII) {
        this.partVIII = partVIII;
    }

    /**
     * Método para obtener el atributo partVIIIcmp22
     * 
     * @return El atributo partVIIIcmp22
     */
    public boolean isPartVIIIcmp22() {
        return this.partVIIIcmp22;
    }

    /**
     * Método para establecer el atributo partVIIIcmp22
     * 
     * @param partVIIIcmp22 El valor del atributo partVIIIcmp22 a establecer.
     */
    public void setPartVIIIcmp22(final boolean partVIIIcmp22) {
        this.partVIIIcmp22 = partVIIIcmp22;
    }

    /**
     * Método para obtener el atributo partIX
     * 
     * @return El atributo partIX
     */
    public boolean isPartIX() {
        return this.partIX;
    }

    /**
     * Método para establecer el atributo partIX
     * 
     * @param partIX El valor del atributo partIX a establecer.
     */
    public void setPartIX(final boolean partIX) {
        this.partIX = partIX;
    }

    /**
     * Método para obtener el atributo partIXcmp23
     * 
     * @return El atributo partIXcmp23
     */
    public boolean isPartIXcmp23() {
        return this.partIXcmp23;
    }

    /**
     * Método para establecer el atributo partIXcmp23
     * 
     * @param partIXcmp23 El valor del atributo partIXcmp23 a establecer.
     */
    public void setPartIXcmp23(final boolean partIXcmp23) {
        this.partIXcmp23 = partIXcmp23;
    }

    /**
     * Método para obtener el atributo partX
     * 
     * @return El atributo partX
     */
    public boolean isPartX() {
        return this.partX;
    }

    /**
     * Método para establecer el atributo partX
     * 
     * @param partX El valor del atributo partX a establecer.
     */
    public void setPartX(final boolean partX) {
        this.partX = partX;
    }

    /**
     * Método para obtener el atributo partXcmp24a
     * 
     * @return El atributo partXcmp24a
     */
    public boolean isPartXcmp24a() {
        return this.partXcmp24a;
    }

    /**
     * Método para establecer el atributo partXcmp24a
     * 
     * @param partXcmp24a El valor del atributo partXcmp24a a establecer.
     */
    public void setPartXcmp24a(final boolean partXcmp24a) {
        this.partXcmp24a = partXcmp24a;
    }

    /**
     * Método para obtener el atributo partXcmp24b
     * 
     * @return El atributo partXcmp24b
     */
    public boolean isPartXcmp24b() {
        return this.partXcmp24b;
    }

    /**
     * Método para establecer el atributo partXcmp24b
     * 
     * @param partXcmp24b El valor del atributo partXcmp24b a establecer.
     */
    public void setPartXcmp24b(final boolean partXcmp24b) {
        this.partXcmp24b = partXcmp24b;
    }

    /**
     * Método para obtener el atributo partXcmp24c
     * 
     * @return El atributo partXcmp24c
     */
    public boolean isPartXcmp24c() {
        return this.partXcmp24c;
    }

    /**
     * Método para establecer el atributo partXcmp24c
     * 
     * @param partXcmp24c El valor del atributo partXcmp24c a establecer.
     */
    public void setPartXcmp24c(final boolean partXcmp24c) {
        this.partXcmp24c = partXcmp24c;
    }

    /**
     * Método para obtener el atributo partXcmp24d
     * 
     * @return El atributo partXcmp24d
     */
    public boolean isPartXcmp24d() {
        return this.partXcmp24d;
    }

    /**
     * Método para establecer el atributo partXcmp24d
     * 
     * @param partXcmp24d El valor del atributo partXcmp24d a establecer.
     */
    public void setPartXcmp24d(final boolean partXcmp24d) {
        this.partXcmp24d = partXcmp24d;
    }

    /**
     * Método para obtener el atributo partXI
     * 
     * @return El atributo partXI
     */
    public boolean isPartXI() {
        return this.partXI;
    }

    /**
     * Método para establecer el atributo partXI
     * 
     * @param partXI El valor del atributo partXI a establecer.
     */
    public void setPartXI(final boolean partXI) {
        this.partXI = partXI;
    }

    /**
     * Método para obtener el atributo partXIcmp25a
     * 
     * @return El atributo partXIcmp25a
     */
    public boolean isPartXIcmp25a() {
        return this.partXIcmp25a;
    }

    /**
     * Método para establecer el atributo partXIcmp25a
     * 
     * @param partXIcmp25a El valor del atributo partXIcmp25a a establecer.
     */
    public void setPartXIcmp25a(final boolean partXIcmp25a) {
        this.partXIcmp25a = partXIcmp25a;
    }

    /**
     * Método para obtener el atributo partXIcmp25b
     * 
     * @return El atributo partXIcmp25b
     */
    public boolean isPartXIcmp25b() {
        return this.partXIcmp25b;
    }

    /**
     * Método para establecer el atributo partXIcmp25b
     * 
     * @param partXIcmp25b El valor del atributo partXIcmp25b a establecer.
     */
    public void setPartXIcmp25b(final boolean partXIcmp25b) {
        this.partXIcmp25b = partXIcmp25b;
    }

    /**
     * Método para obtener el atributo partXIcmp25c
     * 
     * @return El atributo partXIcmp25c
     */
    public boolean isPartXIcmp25c() {
        return this.partXIcmp25c;
    }

    /**
     * Método para establecer el atributo partXIcmp25c
     * 
     * @param partXIcmp25c El valor del atributo partXIcmp25c a establecer.
     */
    public void setPartXIcmp25c(final boolean partXIcmp25c) {
        this.partXIcmp25c = partXIcmp25c;
    }

    /**
     * Método para obtener el atributo partXII
     * 
     * @return El atributo partXII
     */
    public boolean isPartXII() {
        return this.partXII;
    }

    /**
     * Método para establecer el atributo partXII
     * 
     * @param partXII El valor del atributo partXII a establecer.
     */
    public void setPartXII(final boolean partXII) {
        this.partXII = partXII;
    }

    /**
     * Método para obtener el atributo partXIIcmp26a
     * 
     * @return El atributo partXIIcmp26a
     */
    public boolean isPartXIIcmp26a() {
        return this.partXIIcmp26a;
    }

    /**
     * Método para establecer el atributo partXIIcmp26a
     * 
     * @param partXIIcmp26a El valor del atributo partXIIcmp26a a establecer.
     */
    public void setPartXIIcmp26a(final boolean partXIIcmp26a) {
        this.partXIIcmp26a = partXIIcmp26a;
    }

    /**
     * Método para obtener el atributo partXIIcmp26b
     * 
     * @return El atributo partXIIcmp26b
     */
    public String getPartXIIcmp26b() {
        return this.partXIIcmp26b;
    }

    /**
     * Método para establecer el atributo partXIIcmp26b
     * 
     * @param partXIIcmp26b El valor del atributo partXIIcmp26b a establecer.
     */
    public void setPartXIIcmp26b(final String partXIIcmp26b) {
        this.partXIIcmp26b = partXIIcmp26b;
    }

    /**
     * Método para obtener el atributo partXIIcmp26c
     * 
     * @return El atributo partXIIcmp26c
     */
    public String getPartXIIcmp26c() {
        return this.partXIIcmp26c;
    }

    /**
     * Método para establecer el atributo partXIIcmp26c
     * 
     * @param partXIIcmp26c El valor del atributo partXIIcmp26c a establecer.
     */
    public void setPartXIIcmp26c(final String partXIIcmp26c) {
        this.partXIIcmp26c = partXIIcmp26c;
    }

    /**
     * Método para obtener el atributo partXIIcmp26d
     * 
     * @return El atributo partXIIcmp26d
     */
    public String getPartXIIcmp26d() {
        return this.partXIIcmp26d;
    }

    /**
     * Método para establecer el atributo partXIIcmp26d
     * 
     * @param partXIIcmp26d El valor del atributo partXIIcmp26d a establecer.
     */
    public void setPartXIIcmp26d(final String partXIIcmp26d) {
        this.partXIIcmp26d = partXIIcmp26d;
    }

    /**
     * Método para obtener el atributo partXIII
     * 
     * @return El atributo partXIII
     */
    public boolean isPartXIII() {
        return this.partXIII;
    }

    /**
     * Método para establecer el atributo partXIII
     * 
     * @param partXIII El valor del atributo partXIII a establecer.
     */
    public void setPartXIII(final boolean partXIII) {
        this.partXIII = partXIII;
    }

    /**
     * Método para obtener el atributo partXIIIcmp27
     * 
     * @return El atributo partXIIIcmp27
     */
    public boolean isPartXIIIcmp27() {
        return this.partXIIIcmp27;
    }

    /**
     * Método para establecer el atributo partXIIIcmp27
     * 
     * @param partXIIIcmp27 El valor del atributo partXIIIcmp27 a establecer.
     */
    public void setPartXIIIcmp27(final boolean partXIIIcmp27) {
        this.partXIIIcmp27 = partXIIIcmp27;
    }

    /**
     * Método para obtener el atributo partXIV
     * 
     * @return El atributo partXIV
     */
    public boolean isPartXIV() {
        return this.partXIV;
    }

    /**
     * Método para establecer el atributo partXIV
     * 
     * @param partXIV El valor del atributo partXIV a establecer.
     */
    public void setPartXIV(final boolean partXIV) {
        this.partXIV = partXIV;
    }

    /**
     * Método para obtener el atributo partXIVcmp28a
     * 
     * @return El atributo partXIVcmp28a
     */
    public boolean isPartXIVcmp28a() {
        return this.partXIVcmp28a;
    }

    /**
     * Método para establecer el atributo partXIVcmp28a
     * 
     * @param partXIVcmp28a El valor del atributo partXIVcmp28a a establecer.
     */
    public void setPartXIVcmp28a(final boolean partXIVcmp28a) {
        this.partXIVcmp28a = partXIVcmp28a;
    }

    /**
     * Método para obtener el atributo partXIVcmp28b
     * 
     * @return El atributo partXIVcmp28b
     */
    public boolean isPartXIVcmp28b() {
        return this.partXIVcmp28b;
    }

    /**
     * Método para establecer el atributo partXIVcmp28b
     * 
     * @param partXIVcmp28b El valor del atributo partXIVcmp28b a establecer.
     */
    public void setPartXIVcmp28b(final boolean partXIVcmp28b) {
        this.partXIVcmp28b = partXIVcmp28b;
    }

    /**
     * Método para obtener el atributo partXV
     * 
     * @return El atributo partXV
     */
    public boolean isPartXV() {
        return this.partXV;
    }

    /**
     * Método para establecer el atributo partXV
     * 
     * @param partXV El valor del atributo partXV a establecer.
     */
    public void setPartXV(final boolean partXV) {
        this.partXV = partXV;
    }

    /**
     * Método para obtener el atributo partXVcmp29a
     * 
     * @return El atributo partXVcmp29a
     */
    public boolean isPartXVcmp29a() {
        return this.partXVcmp29a;
    }

    /**
     * Método para establecer el atributo partXVcmp29a
     * 
     * @param partXVcmp29a El valor del atributo partXVcmp29a a establecer.
     */
    public void setPartXVcmp29a(final boolean partXVcmp29a) {
        this.partXVcmp29a = partXVcmp29a;
    }

    /**
     * Método para obtener el atributo partXVcmp29b
     * 
     * @return El atributo partXVcmp29b
     */
    public boolean isPartXVcmp29b() {
        return this.partXVcmp29b;
    }

    /**
     * Método para establecer el atributo partXVcmp29b
     * 
     * @param partXVcmp29b El valor del atributo partXVcmp29b a establecer.
     */
    public void setPartXVcmp29b(final boolean partXVcmp29b) {
        this.partXVcmp29b = partXVcmp29b;
    }

    /**
     * Método para obtener el atributo partXVcmp29c
     * 
     * @return El atributo partXVcmp29c
     */
    public boolean isPartXVcmp29c() {
        return this.partXVcmp29c;
    }

    /**
     * Método para establecer el atributo partXVcmp29c
     * 
     * @param partXVcmp29c El valor del atributo partXVcmp29c a establecer.
     */
    public void setPartXVcmp29c(final boolean partXVcmp29c) {
        this.partXVcmp29c = partXVcmp29c;
    }

    /**
     * Método para obtener el atributo partXVcmp29d
     * 
     * @return El atributo partXVcmp29d
     */
    public boolean isPartXVcmp29d() {
        return this.partXVcmp29d;
    }

    /**
     * Método para establecer el atributo partXVcmp29d
     * 
     * @param partXVcmp29d El valor del atributo partXVcmp29d a establecer.
     */
    public void setPartXVcmp29d(final boolean partXVcmp29d) {
        this.partXVcmp29d = partXVcmp29d;
    }

    /**
     * Método para obtener el atributo partXVcmp29e
     * 
     * @return El atributo partXVcmp29e
     */
    public boolean isPartXVcmp29e() {
        return this.partXVcmp29e;
    }

    /**
     * Método para establecer el atributo partXVcmp29e
     * 
     * @param partXVcmp29e El valor del atributo partXVcmp29e a establecer.
     */
    public void setPartXVcmp29e(final boolean partXVcmp29e) {
        this.partXVcmp29e = partXVcmp29e;
    }

    /**
     * Método para obtener el atributo partXVcmp29f
     * 
     * @return El atributo partXVcmp29f
     */
    public boolean isPartXVcmp29f() {
        return this.partXVcmp29f;
    }

    /**
     * Método para establecer el atributo partXVcmp29f
     * 
     * @param partXVcmp29f El valor del atributo partXVcmp29f a establecer.
     */
    public void setPartXVcmp29f(final boolean partXVcmp29f) {
        this.partXVcmp29f = partXVcmp29f;
    }

    /**
     * Método para obtener el atributo partXVI
     * 
     * @return El atributo partXVI
     */
    public boolean isPartXVI() {
        return this.partXVI;
    }

    /**
     * Método para establecer el atributo partXVI
     * 
     * @param partXVI El valor del atributo partXVI a establecer.
     */
    public void setPartXVI(final boolean partXVI) {
        this.partXVI = partXVI;
    }

    /**
     * Método para obtener el atributo partXVIcmp30
     * 
     * @return El atributo partXVIcmp30
     */
    public boolean isPartXVIcmp30() {
        return this.partXVIcmp30;
    }

    /**
     * Método para establecer el atributo partXVIcmp30
     * 
     * @param partXVIcmp30 El valor del atributo partXVIcmp30 a establecer.
     */
    public void setPartXVIcmp30(final boolean partXVIcmp30) {
        this.partXVIcmp30 = partXVIcmp30;
    }

    /**
     * Método para obtener el atributo partXVII
     * 
     * @return El atributo partXVII
     */
    public boolean isPartXVII() {
        return this.partXVII;
    }

    /**
     * Método para establecer el atributo partXVII
     * 
     * @param partXVII El valor del atributo partXVII a establecer.
     */
    public void setPartXVII(final boolean partXVII) {
        this.partXVII = partXVII;
    }

    /**
     * Método para obtener el atributo partXVIcmp31
     * 
     * @return El atributo partXVIcmp31
     */
    public boolean isPartXVIIcmp31() {
        return this.partXVIIcmp31;
    }

    /**
     * Método para establecer el atributo partXVIcmp31
     * 
     * @param partXVIIcmp31 El valor del atributo partXVIcmp31 a establecer.
     */
    public void setPartXVIIcmp31(final boolean partXVIIcmp31) {
        this.partXVIIcmp31 = partXVIIcmp31;
    }

    /**
     * Método para obtener el atributo partXVIII
     * 
     * @return El atributo partXVIII
     */
    public boolean isPartXVIII() {
        return this.partXVIII;
    }

    /**
     * Método para establecer el atributo partXVIII
     * 
     * @param partXVIII El valor del atributo partXVIII a establecer.
     */
    public void setPartXVIII(final boolean partXVIII) {
        this.partXVIII = partXVIII;
    }

    /**
     * Método para obtener el atributo partXVIIIcmp32
     * 
     * @return El atributo partXVIIIcmp32
     */
    public boolean isPartXVIIIcmp32() {
        return this.partXVIIIcmp32;
    }

    /**
     * Método para establecer el atributo partXVIIIcmp32
     * 
     * @param partXVIIIcmp32 El valor del atributo partXVIIIcmp32 a establecer.
     */
    public void setPartXVIIIcmp32(final boolean partXVIIIcmp32) {
        this.partXVIIIcmp32 = partXVIIIcmp32;
    }

    /**
     * Método para obtener el atributo partXIX
     * 
     * @return El atributo partXIX
     */
    public boolean isPartXIX() {
        return this.partXIX;
    }

    /**
     * Método para establecer el atributo partXIX
     * 
     * @param partXIX El valor del atributo partXIX a establecer.
     */
    public void setPartXIX(final boolean partXIX) {
        this.partXIX = partXIX;
    }

    /**
     * Método para obtener el atributo partXIXcmp33a
     * 
     * @return El atributo partXIXcmp33a
     */
    public boolean isPartXIXcmp33a() {
        return this.partXIXcmp33a;
    }

    /**
     * Método para establecer el atributo partXIXcmp33a
     * 
     * @param partXIXcmp33a El valor del atributo partXIXcmp33a a establecer.
     */
    public void setPartXIXcmp33a(final boolean partXIXcmp33a) {
        this.partXIXcmp33a = partXIXcmp33a;
    }

    /**
     * Método para obtener el atributo partXIXcmp33b
     * 
     * @return El atributo partXIXcmp33b
     */
    public String getPartXIXcmp33b() {
        return this.partXIXcmp33b;
    }

    /**
     * Método para establecer el atributo partXIXcmp33b
     * 
     * @param partXIXcmp33b El valor del atributo partXIXcmp33b a establecer.
     */
    public void setPartXIXcmp33b(final String partXIXcmp33b) {
        this.partXIXcmp33b = partXIXcmp33b;
    }

    /**
     * Método para obtener el atributo partXX
     * 
     * @return El atributo partXX
     */
    public boolean isPartXX() {
        return this.partXX;
    }

    /**
     * Método para establecer el atributo partXX
     * 
     * @param partXX El valor del atributo partXX a establecer.
     */
    public void setPartXX(final boolean partXX) {
        this.partXX = partXX;
    }

    /**
     * Método para obtener el atributo partXXcmp34a
     * 
     * @return El atributo partXXcmp34a
     */
    public boolean isPartXXcmp34a() {
        return this.partXXcmp34a;
    }

    /**
     * Método para establecer el atributo partXXcmp34a
     * 
     * @param partXXcmp34a El valor del atributo partXXcmp34a a establecer.
     */
    public void setPartXXcmp34a(final boolean partXXcmp34a) {
        this.partXXcmp34a = partXXcmp34a;
    }

    /**
     * Método para obtener el atributo partXXcmp34b
     * 
     * @return El atributo partXXcmp34b
     */
    public String getPartXXcmp34b() {
        return this.partXXcmp34b;
    }

    /**
     * Método para establecer el atributo partXXcmp34b
     * 
     * @param partXXcmp34b El valor del atributo partXXcmp34b a establecer.
     */
    public void setPartXXcmp34b(final String partXXcmp34b) {
        this.partXXcmp34b = partXXcmp34b;
    }

    /**
     * Método para obtener el atributo partXXI
     * 
     * @return El atributo partXXI
     */
    public boolean isPartXXI() {
        return this.partXXI;
    }

    /**
     * Método para establecer el atributo partXXI
     * 
     * @param partXXI El valor del atributo partXXI a establecer.
     */
    public void setPartXXI(final boolean partXXI) {
        this.partXXI = partXXI;
    }

    /**
     * Método para obtener el atributo partXXIcmp35a
     * 
     * @return El atributo partXXIcmp35a
     */
    public boolean isPartXXIcmp35a() {
        return this.partXXIcmp35a;
    }

    /**
     * Método para establecer el atributo partXXIcmp35a
     * 
     * @param partXXIcmp35a El valor del atributo partXXIcmp35a a establecer.
     */
    public void setPartXXIcmp35a(final boolean partXXIcmp35a) {
        this.partXXIcmp35a = partXXIcmp35a;
    }

    /**
     * Método para obtener el atributo partXXIcmp35b
     * 
     * @return El atributo partXXIcmp35b
     */
    public String getPartXXIcmp35b() {
        return this.partXXIcmp35b;
    }

    /**
     * Método para establecer el atributo partXXIcmp35b
     * 
     * @param partXXIcmp35b El valor del atributo partXXIcmp35b a establecer.
     */
    public void setPartXXIcmp35b(final String partXXIcmp35b) {
        this.partXXIcmp35b = partXXIcmp35b;
    }

    /**
     * Método para obtener el atributo partXXII
     * 
     * @return El atributo partXXII
     */
    public boolean isPartXXII() {
        return this.partXXII;
    }

    /**
     * Método para establecer el atributo partXXII
     * 
     * @param partXXII El valor del atributo partXXII a establecer.
     */
    public void setPartXXII(final boolean partXXII) {
        this.partXXII = partXXII;
    }

    /**
     * Método para obtener el atributo partXXIIcmp36
     * 
     * @return El atributo partXXIIcmp36
     */
    public boolean isPartXXIIcmp36() {
        return this.partXXIIcmp36;
    }

    /**
     * Método para establecer el atributo partXXIIcmp36
     * 
     * @param partXXIIcmp36 El valor del atributo partXXIIcmp36 a establecer.
     */
    public void setPartXXIIcmp36(final boolean partXXIIcmp36) {
        this.partXXIIcmp36 = partXXIIcmp36;
    }

    /**
     * Método para obtener el atributo partXXIII
     * 
     * @return El atributo partXXIII
     */
    public boolean isPartXXIII() {
        return this.partXXIII;
    }

    /**
     * Método para establecer el atributo partXXIII
     * 
     * @param partXXIII El valor del atributo partXXIII a establecer.
     */
    public void setPartXXIII(final boolean partXXIII) {
        this.partXXIII = partXXIII;
    }

    /**
     * Método para obtener el atributo partXXIIIcmp37a
     * 
     * @return El atributo partXXIIIcmp37a
     */
    public boolean isPartXXIIIcmp37a() {
        return this.partXXIIIcmp37a;
    }

    /**
     * Método para establecer el atributo partXXIIIcmp37a
     * 
     * @param partXXIIIcmp37a El valor del atributo partXXIIIcmp37a a establecer.
     */
    public void setPartXXIIIcmp37a(final boolean partXXIIIcmp37a) {
        this.partXXIIIcmp37a = partXXIIIcmp37a;
    }

    /**
     * Método para obtener el atributo partXXIIIcmp37b
     * 
     * @return El atributo partXXIIIcmp37b
     */
    public String getPartXXIIIcmp37b() {
        return this.partXXIIIcmp37b;
    }

    /**
     * Método para establecer el atributo partXXIIIcmp37b
     * 
     * @param partXXIIIcmp37b El valor del atributo partXXIIIcmp37b a establecer.
     */
    public void setPartXXIIIcmp37b(final String partXXIIIcmp37b) {
        this.partXXIIIcmp37b = partXXIIIcmp37b;
    }

    /**
     * Método para obtener el atributo partXXIIIcmp37c
     * 
     * @return El atributo partXXIIIcmp37c
     */
    public boolean isPartXXIIIcmp37c() {
        return this.partXXIIIcmp37c;
    }

    /**
     * Método para establecer el atributo partXXIIIcmp37c
     * 
     * @param partXXIIIcmp37c El valor del atributo partXXIIIcmp37c a establecer.
     */
    public void setPartXXIIIcmp37c(final boolean partXXIIIcmp37c) {
        this.partXXIIIcmp37c = partXXIIIcmp37c;
    }

    /**
     * Método para obtener el atributo partXXIIIcmp37d
     * 
     * @return El atributo partXXIIIcmp37d
     */
    public String getPartXXIIIcmp37d() {
        return this.partXXIIIcmp37d;
    }

    /**
     * Método para establecer el atributo partXXIIIcmp37d
     * 
     * @param partXXIIIcmp37d El valor del atributo partXXIIIcmp37d a establecer.
     */
    public void setPartXXIIIcmp37d(final String partXXIIIcmp37d) {
        this.partXXIIIcmp37d = partXXIIIcmp37d;
    }

    /**
     * Método para obtener el atributo partXXIIIcmp37e
     * 
     * @return El atributo partXXIIIcmp37e
     */
    public String getPartXXIIIcmp37e() {
        return this.partXXIIIcmp37e;
    }

    /**
     * Método para establecer el atributo partXXIIIcmp37e
     * 
     * @param partXXIIIcmp37e El valor del atributo partXXIIIcmp37e a establecer.
     */
    public void setPartXXIIIcmp37e(final String partXXIIIcmp37e) {
        this.partXXIIIcmp37e = partXXIIIcmp37e;
    }

    /**
     * Método para obtener el atributo partXXIV
     * 
     * @return El atributo partXXIV
     */
    public boolean isPartXXIV() {
        return this.partXXIV;
    }

    /**
     * Método para establecer el atributo partXXIV
     * 
     * @param partXXIV El valor del atributo partXXIV a establecer.
     */
    public void setPartXXIV(final boolean partXXIV) {
        this.partXXIV = partXXIV;
    }

    /**
     * Método para obtener el atributo partXXIVcmp38
     * 
     * @return El atributo partXXIVcmp38
     */
    public boolean isPartXXIVcmp38() {
        return this.partXXIVcmp38;
    }

    /**
     * Método para establecer el atributo partXXIVcmp38
     * 
     * @param partXXIVcmp38 El valor del atributo partXXIVcmp38 a establecer.
     */
    public void setPartXXIVcmp38(final boolean partXXIVcmp38) {
        this.partXXIVcmp38 = partXXIVcmp38;
    }

    /**
     * Método para obtener el atributo partXXV
     * 
     * @return El atributo partXXV
     */
    public boolean isPartXXV() {
        return this.partXXV;
    }

    /**
     * Método para establecer el atributo partXXV
     * 
     * @param partXXV El valor del atributo partXXV a establecer.
     */
    public void setPartXXV(final boolean partXXV) {
        this.partXXV = partXXV;
    }

    /**
     * Método para obtener el atributo partXXVcmp39
     * 
     * @return El atributo partXXVcmp39
     */
    public boolean isPartXXVcmp39() {
        return this.partXXVcmp39;
    }

    /**
     * Método para establecer el atributo partXXVcmp39
     * 
     * @param partXXVcmp39 El valor del atributo partXXVcmp39 a establecer.
     */
    public void setPartXXVcmp39(final boolean partXXVcmp39) {
        this.partXXVcmp39 = partXXVcmp39;
    }

    /**
     * Método para obtener el atributo partXXVI
     * 
     * @return El atributo partXXVI
     */
    public boolean isPartXXVI() {
        return this.partXXVI;
    }

    /**
     * Método para establecer el atributo partXXVI
     * 
     * @param partXXVI El valor del atributo partXXVI a establecer.
     */
    public void setPartXXVI(final boolean partXXVI) {
        this.partXXVI = partXXVI;
    }

    /**
     * Método para obtener el atributo partXXVIcmp40a
     * 
     * @return El atributo partXXVIcmp40a
     */
    public boolean isPartXXVIcmp40a() {
        return this.partXXVIcmp40a;
    }

    /**
     * Método para establecer el atributo partXXVIcmp40a
     * 
     * @param partXXVIcmp40a El valor del atributo partXXVIcmp40a a establecer.
     */
    public void setPartXXVIcmp40a(final boolean partXXVIcmp40a) {
        this.partXXVIcmp40a = partXXVIcmp40a;
    }

    /**
     * Método para obtener el atributo partXXVIcmp40b
     * 
     * @return El atributo partXXVIcmp40b
     */
    public boolean isPartXXVIcmp40b() {
        return this.partXXVIcmp40b;
    }

    /**
     * Método para establecer el atributo partXXVIcmp40b
     * 
     * @param partXXVIcmp40b El valor del atributo partXXVIcmp40b a establecer.
     */
    public void setPartXXVIcmp40b(final boolean partXXVIcmp40b) {
        this.partXXVIcmp40b = partXXVIcmp40b;
    }

    /**
     * Método para obtener el atributo partXXVIcmp40c
     * 
     * @return El atributo partXXVIcmp40c
     */
    public boolean isPartXXVIcmp40c() {
        return this.partXXVIcmp40c;
    }

    /**
     * Método para establecer el atributo partXXVIcmp40c
     * 
     * @param partXXVIcmp40c El valor del atributo partXXVIcmp40c a establecer.
     */
    public void setPartXXVIcmp40c(final boolean partXXVIcmp40c) {
        this.partXXVIcmp40c = partXXVIcmp40c;
    }

    /**
     * Método para obtener el atributo partXXVII
     * 
     * @return El atributo partXXVII
     */
    public boolean isPartXXVII() {
        return this.partXXVII;
    }

    /**
     * Método para establecer el atributo partXXVII
     * 
     * @param partXXVII El valor del atributo partXXVII a establecer.
     */
    public void setPartXXVII(final boolean partXXVII) {
        this.partXXVII = partXXVII;
    }

    /**
     * Método para obtener el atributo partXXVIIcmp41
     * 
     * @return El atributo partXXVIIcmp41
     */
    public boolean isPartXXVIIcmp41() {
        return this.partXXVIIcmp41;
    }

    /**
     * Método para establecer el atributo partXXVIIcmp41
     * 
     * @param partXXVIIcmp41 El valor del atributo partXXVIIcmp41 a establecer.
     */
    public void setPartXXVIIcmp41(final boolean partXXVIIcmp41) {
        this.partXXVIIcmp41 = partXXVIIcmp41;
    }

    /**
     * Método para obtener el atributo partXXVIII
     * 
     * @return El atributo partXXVIII
     */
    public boolean isPartXXVIII() {
        return this.partXXVIII;
    }

    /**
     * Método para establecer el atributo partXXVIII
     * 
     * @param partXXVIII El valor del atributo partXXVIII a establecer.
     */
    public void setPartXXVIII(final boolean partXXVIII) {
        this.partXXVIII = partXXVIII;
    }

    /**
     * Método para obtener el atributo partXXVIIIcmp42
     * 
     * @return El atributo partXXVIIIcmp42
     */
    public String getPartXXVIIIcmp42() {
        return this.partXXVIIIcmp42;
    }

    /**
     * Método para establecer el atributo partXXVIIIcmp42
     * 
     * @param partXXVIIIcmp42 El valor del atributo partXXVIIIcmp42 a establecer.
     */
    public void setPartXXVIIIcmp42(final String partXXVIIIcmp42) {
        this.partXXVIIIcmp42 = partXXVIIIcmp42;
    }

    /**
     * Método para obtener el atributo partXXVIIIcmp43
     * 
     * @return El atributo partXXVIIIcmp43
     */
    public boolean isPartXXVIIIcmp43() {
        return this.partXXVIIIcmp43;
    }

    /**
     * Método para establecer el atributo partXXVIIIcmp43
     * 
     * @param partXXVIIIcmp43 El valor del atributo partXXVIIIcmp43 a establecer.
     */
    public void setPartXXVIIIcmp43(final boolean partXXVIIIcmp43) {
        this.partXXVIIIcmp43 = partXXVIIIcmp43;
    }

    /**
     * Método para obtener el atributo partXXIX
     * 
     * @return El atributo partXXIX
     */
    public boolean isPartXXIX() {
        return this.partXXIX;
    }

    /**
     * Método para establecer el atributo partXXIX
     * 
     * @param partXXIX El valor del atributo partXXIX a establecer.
     */
    public void setPartXXIX(final boolean partXXIX) {
        this.partXXIX = partXXIX;
    }


    /**
     * @return
     * @throws ObjectStreamException
     */
    private Object writeReplace() throws ObjectStreamException {

        Field[] attributes = this.getClass().getDeclaredFields();

        // Setear como vacos los datos nulos
        for (Field field : attributes) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                try {
                    String fieldType = field.getGenericType().toString();
                    if (fieldType.indexOf("String") != -1) {
                        if (PropertyUtils.getSimpleProperty(this, field.getName()) == null) {
                            PropertyUtils.setSimpleProperty(this, field.getName(), "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return this;

    }

    /**
     * Método para obtener el atributo sIEFORE_AFORE
     * 
     * @return El atributo sIEFORE_AFORE
     */
    public boolean isSIEFORE_AFORE() {
        return this.SIEFORE_AFORE;
    }

    /**
     * Método para establecer el atributo sIEFORE_AFORE
     * 
     * @param sIEFORE_AFORE El valor del atributo sIEFORE_AFORE a establecer.
     */
    public void setSIEFORE_AFORE(final boolean sIEFORE_AFORE) {
        this.SIEFORE_AFORE = sIEFORE_AFORE;
    }

    /**
     * Método para obtener el atributo paisResidencial
     * 
     * @return El atributo paisResidencial
     */
    public String getPaisResidencial() {
        return this.paisResidencial;
    }

    /**
     * Método para establecer el atributo paisResidencial
     * 
     * @param paisResidencial El valor del atributo paisResidencial a establecer.
     */
    public void setPaisResidencial(final String paisResidencial) {
        this.paisResidencial = paisResidencial;
    }

    /**
     * Método para obtener el atributo printName
     * 
     * @return El atributo printName
     */
    public String getPrintName() {
        return this.printName;
    }

    /**
     * Método para establecer el atributo printName
     * 
     * @param printName El valor del atributo printName a establecer.
     */
    public void setPrintName(final String printName) {
        this.printName = printName;
    }

    /**
     * Método para obtener el atributo esCmp4a
     * 
     * @return El atributo esCmp4a
     */
    public boolean isEsCmp4a() {
        return this.esCmp4a;
    }

    /**
     * Método para establecer el atributo esCmp4a
     * 
     * @param esCmp4a El valor del atributo esCmp4a a establecer.
     */
    public void setEsCmp4a(final boolean esCmp4a) {
        this.esCmp4a = esCmp4a;
    }

    /**
     * Método para obtener el atributo esCmp4b
     * 
     * @return El atributo esCmp4b
     */
    public boolean isEsCmp4b() {
        return this.esCmp4b;
    }

    /**
     * Método para establecer el atributo esCmp4b
     * 
     * @param esCmp4b El valor del atributo esCmp4b a establecer.
     */
    public void setEsCmp4b(final boolean esCmp4b) {
        this.esCmp4b = esCmp4b;
    }

    /**
     * Método para obtener el atributo esCmp4c
     * 
     * @return El atributo esCmp4c
     */
    public boolean isEsCmp4c() {
        return this.esCmp4c;
    }

    /**
     * Método para establecer el atributo esCmp4c
     * 
     * @param esCmp4c El valor del atributo esCmp4c a establecer.
     */
    public void setEsCmp4c(final boolean esCmp4c) {
        this.esCmp4c = esCmp4c;
    }

    /**
     * Método para obtener el atributo esCmp4d
     * 
     * @return El atributo esCmp4d
     */
    public boolean isEsCmp4d() {
        return this.esCmp4d;
    }

    /**
     * Método para establecer el atributo esCmp4d
     * 
     * @param esCmp4d El valor del atributo esCmp4d a establecer.
     */
    public void setEsCmp4d(final boolean esCmp4d) {
        this.esCmp4d = esCmp4d;
    }

    /**
     * Método para obtener el atributo esCmp4e
     * 
     * @return El atributo esCmp4e
     */
    public boolean isEsCmp4e() {
        return this.esCmp4e;
    }

    /**
     * Método para establecer el atributo esCmp4e
     * 
     * @param esCmp4e El valor del atributo esCmp4e a establecer.
     */
    public void setEsCmp4e(final boolean esCmp4e) {
        this.esCmp4e = esCmp4e;
    }

    /**
     * Método para obtener el atributo esCmp4f
     * 
     * @return El atributo esCmp4f
     */
    public boolean isEsCmp4f() {
        return this.esCmp4f;
    }

    /**
     * Método para establecer el atributo esCmp4f
     * 
     * @param esCmp4f El valor del atributo esCmp4f a establecer.
     */
    public void setEsCmp4f(final boolean esCmp4f) {
        this.esCmp4f = esCmp4f;
    }

    /**
     * Método para obtener el atributo esCmp4g
     * 
     * @return El atributo esCmp4g
     */
    public boolean isEsCmp4g() {
        return this.esCmp4g;
    }

    /**
     * Método para establecer el atributo esCmp4g
     * 
     * @param esCmp4g El valor del atributo esCmp4g a establecer.
     */
    public void setEsCmp4g(final boolean esCmp4g) {
        this.esCmp4g = esCmp4g;
    }

    /**
     * Método para obtener el atributo esCmp4h
     * 
     * @return El atributo esCmp4h
     */
    public boolean isEsCmp4h() {
        return this.esCmp4h;
    }

    /**
     * Método para establecer el atributo esCmp4h
     * 
     * @param esCmp4h El valor del atributo esCmp4h a establecer.
     */
    public void setEsCmp4h(final boolean esCmp4h) {
        this.esCmp4h = esCmp4h;
    }

    /**
     * Método para obtener el atributo esCmp4i
     * 
     * @return El atributo esCmp4i
     */
    public boolean isEsCmp4i() {
        return this.esCmp4i;
    }

    /**
     * Método para establecer el atributo esCmp4i
     * 
     * @param esCmp4i El valor del atributo esCmp4i a establecer.
     */
    public void setEsCmp4i(final boolean esCmp4i) {
        this.esCmp4i = esCmp4i;
    }

    /**
     * Método para obtener el atributo esCmp4j
     * 
     * @return El atributo esCmp4j
     */
    public boolean isEsCmp4j() {
        return this.esCmp4j;
    }

    /**
     * Método para establecer el atributo esCmp4j
     * 
     * @param esCmp4j El valor del atributo esCmp4j a establecer.
     */
    public void setEsCmp4j(final boolean esCmp4j) {
        this.esCmp4j = esCmp4j;
    }

    /**
     * Método para obtener el atributo esCmp4k
     * 
     * @return El atributo esCmp4k
     */
    public boolean isEsCmp4k() {
        return this.esCmp4k;
    }

    /**
     * Método para establecer el atributo esCmp4k
     * 
     * @param esCmp4k El valor del atributo esCmp4k a establecer.
     */
    public void setEsCmp4k(final boolean esCmp4k) {
        this.esCmp4k = esCmp4k;
    }

    /**
     * Método para obtener el atributo inhabilitarPartIcmp7
     * 
     * @return El atributo inhabilitarPartIcmp7
     */
    public boolean isInhabilitarPartIcmp7() {
        return this.inhabilitarPartIcmp7;
    }

    /**
     * Método para establecer el atributo inhabilitarPartIcmp7
     * 
     * @param inhabilitarPartIcmp7 El valor del atributo inhabilitarPartIcmp7 a establecer.
     */
    public void setInhabilitarPartIcmp7(final boolean inhabilitarPartIcmp7) {
        this.inhabilitarPartIcmp7 = inhabilitarPartIcmp7;
    }

    /**
     * Método para obtener el atributo readOnlyCmp14
     * 
     * @return El atributo readOnlyCmp14
     */
    public boolean isReadOnlyCmp14() {
        return this.readOnlyCmp14;
    }

    /**
     * Método para establecer el atributo readOnlyCmp14
     * 
     * @param readOnlyCmp14 El valor del atributo readOnlyCmp14 a establecer.
     */
    public void setReadOnlyCmp14(final boolean readOnlyCmp14) {
        this.readOnlyCmp14 = readOnlyCmp14;
    }

    /**
     * Método para obtener el atributo readOnlyCmp15c
     * 
     * @return El atributo readOnlyCmp15c
     */
    public boolean isReadOnlyCmp15c() {
        return this.readOnlyCmp15c;
    }

    /**
     * Método para establecer el atributo readOnlyCmp15c
     * 
     * @param readOnlyCmp15c El valor del atributo readOnlyCmp15c a establecer.
     */
    public void setReadOnlyCmp15c(final boolean readOnlyCmp15c) {
        this.readOnlyCmp15c = readOnlyCmp15c;
    }

    /**
     * Método para obtener el atributo readOnlyCmp9b
     * 
     * @return El atributo readOnlyCmp9b
     */
    public boolean isReadOnlyCmp9b() {
        return this.readOnlyCmp9b;
    }

    /**
     * Método para establecer el atributo readOnlyCmp9b
     * 
     * @param readOnlyCmp9b El valor del atributo readOnlyCmp9b a establecer.
     */
    public void setReadOnlyCmp9b(final boolean readOnlyCmp9b) {
        this.readOnlyCmp9b = readOnlyCmp9b;
    }

    /** @return this.partIcmp4n */
    public boolean isPartIcmp4n() {
        return this.partIcmp4n;
    }

    /** @param partIcmp4n to be set in this.partIcmp4n */
    public void setPartIcmp4n(final boolean partIcmp4n) {
        this.partIcmp4n = partIcmp4n;
    }

    /** @return this.esCmp4n */
    public boolean isEsCmp4n() {
        return this.esCmp4n;
    }

    /** @param esCmp4n to be set in this.esCmp4n */
    public void setEsCmp4n(final boolean esCmp4n) {
        this.esCmp4n = esCmp4n;
    }

    /** @return this.partIcmp5af */
    public boolean isPartIcmp5af() {
        return this.partIcmp5af;
    }

    /** @param partIcmp5af to be set in this.partIcmp5af */
    public void setPartIcmp5af(final boolean partIcmp5af) {
        this.partIcmp5af = partIcmp5af;
    }

    /** @return this.partIIIcmp14ba */
    public boolean isPartIIIcmp14ba() {
        return this.partIIIcmp14ba;
    }

    /** @param partIIIcmp14ba to be set in this.partIIIcmp14ba */
    public void setPartIIIcmp14ba(final boolean partIIIcmp14ba) {
        this.partIIIcmp14ba = partIIIcmp14ba;
    }

    /** @return this.partIIIcmp14bb */
    public boolean isPartIIIcmp14bb() {
        return this.partIIIcmp14bb;
    }

    /** @param partIIIcmp14bb to be set in this.partIIIcmp14bb */
    public void setPartIIIcmp14bb(final boolean partIIIcmp14bb) {
        this.partIIIcmp14bb = partIIIcmp14bb;
    }

    /** @return this.partIIIcmp14bc */
    public boolean isPartIIIcmp14bc() {
        return this.partIIIcmp14bc;
    }

    /** @param partIIIcmp14bc to be set in this.partIIIcmp14bc */
    public void setPartIIIcmp14bc(final boolean partIIIcmp14bc) {
        this.partIIIcmp14bc = partIIIcmp14bc;
    }

    /** @return this.partIIIcmp14bd */
    public boolean isPartIIIcmp14bd() {
        return this.partIIIcmp14bd;
    }

    /** @param partIIIcmp14bd to be set in this.partIIIcmp14bd */
    public void setPartIIIcmp14bd(final boolean partIIIcmp14bd) {
        this.partIIIcmp14bd = partIIIcmp14bd;
    }

    /** @return this.partIIIcmp14be */
    public boolean isPartIIIcmp14be() {
        return this.partIIIcmp14be;
    }

    /** @param partIIIcmp14be to be set in this.partIIIcmp14be */
    public void setPartIIIcmp14be(final boolean partIIIcmp14be) {
        this.partIIIcmp14be = partIIIcmp14be;
    }

    /** @return this.partIIIcmp14bf */
    public boolean isPartIIIcmp14bf() {
        return this.partIIIcmp14bf;
    }

    /** @param partIIIcmp14bf to be set in this.partIIIcmp14bf */
    public void setPartIIIcmp14bf(final boolean partIIIcmp14bf) {
        this.partIIIcmp14bf = partIIIcmp14bf;
    }

    /** @return this.partIIIcmp14bg */
    public boolean isPartIIIcmp14bg() {
        return this.partIIIcmp14bg;
    }

    /** @param partIIIcmp14bg to be set in this.partIIIcmp14bg */
    public void setPartIIIcmp14bg(final boolean partIIIcmp14bg) {
        this.partIIIcmp14bg = partIIIcmp14bg;
    }

    /** @return this.partIIIcmp14bh */
    public boolean isPartIIIcmp14bh() {
        return this.partIIIcmp14bh;
    }

    /** @param partIIIcmp14bh to be set in this.partIIIcmp14bh */
    public void setPartIIIcmp14bh(final boolean partIIIcmp14bh) {
        this.partIIIcmp14bh = partIIIcmp14bh;
    }

    /** @return this.partIIIcmp14bi */
    public boolean isPartIIIcmp14bi() {
        return this.partIIIcmp14bi;
    }

    /** @param partIIIcmp14bi to be set in this.partIIIcmp14bi */
    public void setPartIIIcmp14bi(final boolean partIIIcmp14bi) {
        this.partIIIcmp14bi = partIIIcmp14bi;
    }

    /** @return this.partIIIcmp14bj */
    public boolean isPartIIIcmp14bj() {
        return this.partIIIcmp14bj;
    }

    /** @param partIIIcmp14bj to be set in this.partIIIcmp14bj */
    public void setPartIIIcmp14bj(final boolean partIIIcmp14bj) {
        this.partIIIcmp14bj = partIIIcmp14bj;
    }

    /** @return this.partIIIcmp14bk */
    public String getPartIIIcmp14bk() {
        return this.partIIIcmp14bk;
    }

    /** @param partIIIcmp14bk to be set in this.partIIIcmp14bk */
    public void setPartIIIcmp14bk(final String partIIIcmp14bk) {
        this.partIIIcmp14bk = partIIIcmp14bk;
    }

    /** @return this.partIVcmp16a */
    public String getPartIVcmp16a() {
        return this.partIVcmp16a;
    }

    /** @param partIVcmp16a to be set in this.partIVcmp16a */
    public void setPartIVcmp16a(final String partIVcmp16a) {
        this.partIVcmp16a = partIVcmp16a;
    }

    /** @return this.partXIIcmp26ba */
    public boolean isPartXIIcmp26ba() {
        return this.partXIIcmp26ba;
    }

    /** @param partXIIcmp26ba to be set in this.partXIIcmp26ba */
    public void setPartXIIcmp26ba(final boolean partXIIcmp26ba) {
        this.partXIIcmp26ba = partXIIcmp26ba;
    }

    /** @return this.partXIIcmp26bb */
    public boolean isPartXIIcmp26bb() {
        return this.partXIIcmp26bb;
    }

    /** @param partXIIcmp26bb to be set in this.partXIIcmp26bb */
    public void setPartXIIcmp26bb(final boolean partXIIcmp26bb) {
        this.partXIIcmp26bb = partXIIcmp26bb;
    }

    /** @return this.partXXVIIIcmp42_2 */
    public String getPartXXVIIIcmp42_2() {
        return this.partXXVIIIcmp42_2;
    }

    /** @param partXXVIIIcmp42_2 to be set in this.partXXVIIIcmp42_2 */
    public void setPartXXVIIIcmp42_2(final String partXXVIIIcmp42_2) {
        this.partXXVIIIcmp42_2 = partXXVIIIcmp42_2;
    }

    /** @return this.partXXIXnoma */
    public String getPartXXIXnoma() {
        return this.partXXIXnoma;
    }

    /** @param partXXIXnoma to be set in this.partXXIXnoma */
    public void setPartXXIXnoma(final String partXXIXnoma) {
        this.partXXIXnoma = partXXIXnoma;
    }

    /** @return this.partXXIXnomb */
    public String getPartXXIXnomb() {
        return this.partXXIXnomb;
    }

    /** @param partXXIXnomb to be set in this.partXXIXnomb */
    public void setPartXXIXnomb(final String partXXIXnomb) {
        this.partXXIXnomb = partXXIXnomb;
    }

    /** @return this.partXXIXnomc */
    public String getPartXXIXnomc() {
        return this.partXXIXnomc;
    }

    /** @param partXXIXnomc to be set in this.partXXIXnomc */
    public void setPartXXIXnomc(final String partXXIXnomc) {
        this.partXXIXnomc = partXXIXnomc;
    }

    /** @return this.partXXIXnomd */
    public String getPartXXIXnomd() {
        return this.partXXIXnomd;
    }

    /** @param partXXIXnomd to be set in this.partXXIXnomd */
    public void setPartXXIXnomd(final String partXXIXnomd) {
        this.partXXIXnomd = partXXIXnomd;
    }

    /** @return this.partXXIXnome */
    public String getPartXXIXnome() {
        return this.partXXIXnome;
    }

    /** @param partXXIXnome to be set in this.partXXIXnome */
    public void setPartXXIXnome(final String partXXIXnome) {
        this.partXXIXnome = partXXIXnome;
    }

    /** @return this.partXXIXnomf */
    public String getPartXXIXnomf() {
        return this.partXXIXnomf;
    }

    /** @param partXXIXnomf to be set in this.partXXIXnomf */
    public void setPartXXIXnomf(final String partXXIXnomf) {
        this.partXXIXnomf = partXXIXnomf;
    }

    /** @return this.partXXIXnomg */
    public String getPartXXIXnomg() {
        return this.partXXIXnomg;
    }

    /** @param partXXIXnomg to be set in this.partXXIXnomg */
    public void setPartXXIXnomg(final String partXXIXnomg) {
        this.partXXIXnomg = partXXIXnomg;
    }

    /** @return this.partXXIXnomh */
    public String getPartXXIXnomh() {
        return this.partXXIXnomh;
    }

    /** @param partXXIXnomh to be set in this.partXXIXnomh */
    public void setPartXXIXnomh(final String partXXIXnomh) {
        this.partXXIXnomh = partXXIXnomh;
    }

    /** @return this.partXXIXnomi */
    public String getPartXXIXnomi() {
        return this.partXXIXnomi;
    }

    /** @param partXXIXnomi to be set in this.partXXIXnomi */
    public void setPartXXIXnomi(final String partXXIXnomi) {
        this.partXXIXnomi = partXXIXnomi;
    }

    /** @return this.partXXIXadra */
    public String getPartXXIXadra() {
        return this.partXXIXadra;
    }

    /** @param partXXIXadra to be set in this.partXXIXadra */
    public void setPartXXIXadra(final String partXXIXadra) {
        this.partXXIXadra = partXXIXadra;
    }

    /** @return this.partXXIXadrb */
    public String getPartXXIXadrb() {
        return this.partXXIXadrb;
    }

    /** @param partXXIXadrb to be set in this.partXXIXadrb */
    public void setPartXXIXadrb(final String partXXIXadrb) {
        this.partXXIXadrb = partXXIXadrb;
    }

    /** @return this.partXXIXadrc */
    public String getPartXXIXadrc() {
        return this.partXXIXadrc;
    }

    /** @param partXXIXadrc to be set in this.partXXIXadrc */
    public void setPartXXIXadrc(final String partXXIXadrc) {
        this.partXXIXadrc = partXXIXadrc;
    }

    /** @return this.partXXIXadrd */
    public String getPartXXIXadrd() {
        return this.partXXIXadrd;
    }

    /** @param partXXIXadrd to be set in this.partXXIXadrd */
    public void setPartXXIXadrd(final String partXXIXadrd) {
        this.partXXIXadrd = partXXIXadrd;
    }

    /** @return this.partXXIXadre */
    public String getPartXXIXadre() {
        return this.partXXIXadre;
    }

    /** @param partXXIXadre to be set in this.partXXIXadre */
    public void setPartXXIXadre(final String partXXIXadre) {
        this.partXXIXadre = partXXIXadre;
    }

    /** @return this.partXXIXadrf */
    public String getPartXXIXadrf() {
        return this.partXXIXadrf;
    }

    /** @param partXXIXadrf to be set in this.partXXIXadrf */
    public void setPartXXIXadrf(final String partXXIXadrf) {
        this.partXXIXadrf = partXXIXadrf;
    }

    /** @return this.partXXIXadrg */
    public String getPartXXIXadrg() {
        return this.partXXIXadrg;
    }

    /** @param partXXIXadrg to be set in this.partXXIXadrg */
    public void setPartXXIXadrg(final String partXXIXadrg) {
        this.partXXIXadrg = partXXIXadrg;
    }

    /** @return this.partXXIXadrh */
    public String getPartXXIXadrh() {
        return this.partXXIXadrh;
    }

    /** @param partXXIXadrh to be set in this.partXXIXadrh */
    public void setPartXXIXadrh(final String partXXIXadrh) {
        this.partXXIXadrh = partXXIXadrh;
    }

    /** @return this.partXXIXadri */
    public String getPartXXIXadri() {
        return this.partXXIXadri;
    }

    /** @param partXXIXadri to be set in this.partXXIXadri */
    public void setPartXXIXadri(final String partXXIXadri) {
        this.partXXIXadri = partXXIXadri;
    }

    /** @return this.partXXIXtina */
    public String getPartXXIXtina() {
        return this.partXXIXtina;
    }

    /** @param partXXIXtina to be set in this.partXXIXtina */
    public void setPartXXIXtina(final String partXXIXtina) {
        this.partXXIXtina = partXXIXtina;
    }

    /** @return this.partXXIXtinb */
    public String getPartXXIXtinb() {
        return this.partXXIXtinb;
    }

    /** @param partXXIXtinb to be set in this.partXXIXtinb */
    public void setPartXXIXtinb(final String partXXIXtinb) {
        this.partXXIXtinb = partXXIXtinb;
    }

    /** @return this.partXXIXtinc */
    public String getPartXXIXtinc() {
        return this.partXXIXtinc;
    }

    /** @param partXXIXtinc to be set in this.partXXIXtinc */
    public void setPartXXIXtinc(final String partXXIXtinc) {
        this.partXXIXtinc = partXXIXtinc;
    }

    /** @return this.partXXIXtind */
    public String getPartXXIXtind() {
        return this.partXXIXtind;
    }

    /** @param partXXIXtind to be set in this.partXXIXtind */
    public void setPartXXIXtind(final String partXXIXtind) {
        this.partXXIXtind = partXXIXtind;
    }

    /** @return this.partXXIXtine */
    public String getPartXXIXtine() {
        return this.partXXIXtine;
    }

    /** @param partXXIXtine to be set in this.partXXIXtine */
    public void setPartXXIXtine(final String partXXIXtine) {
        this.partXXIXtine = partXXIXtine;
    }

    /** @return this.partXXIXtinf */
    public String getPartXXIXtinf() {
        return this.partXXIXtinf;
    }

    /** @param partXXIXtinf to be set in this.partXXIXtinf */
    public void setPartXXIXtinf(final String partXXIXtinf) {
        this.partXXIXtinf = partXXIXtinf;
    }

    /** @return this.partXXIXting */
    public String getPartXXIXting() {
        return this.partXXIXting;
    }

    /** @param partXXIXting to be set in this.partXXIXting */
    public void setPartXXIXting(final String partXXIXting) {
        this.partXXIXting = partXXIXting;
    }

    /** @return this.partXXIXtinh */
    public String getPartXXIXtinh() {
        return this.partXXIXtinh;
    }

    /** @param partXXIXtinh to be set in this.partXXIXtinh */
    public void setPartXXIXtinh(final String partXXIXtinh) {
        this.partXXIXtinh = partXXIXtinh;
    }

    /** @return this.partXXIXtini */
    public String getPartXXIXtini() {
        return this.partXXIXtini;
    }

    /** @param partXXIXtini to be set in this.partXXIXtini */
    public void setPartXXIXtini(final String partXXIXtini) {
        this.partXXIXtini = partXXIXtini;
    }

    /** @return this.partXXX */
    public boolean isPartXXX() {
        return this.partXXX;
    }

    /** @param partXXX to be set in this.partXXX */
    public void setPartXXX(final boolean partXXX) {
        this.partXXX = partXXX;
    }

    /** @return this.partIIIcmp15d3 */
    public String getPartIIIcmp15d3() {
        return this.partIIIcmp15d3;
    }

    /** @param partIIIcmp15d3 to be set in this.partIIIcmp15d3 */
    public void setPartIIIcmp15d3(final String partIIIcmp15d3) {
        this.partIIIcmp15d3 = partIIIcmp15d3;
    }

    /** @return this.partXIIcmp26e */
    public String getPartXIIcmp26e() {
        return this.partXIIcmp26e;
    }

    /** @param partXIIcmp26e to be set in this.partXIIcmp26e */
    public void setPartXIIcmp26e(final String partXIIcmp26e) {
        this.partXIIcmp26e = partXIIcmp26e;
    }

	@Override
	public String toString() {
		return "FormaW8BENE [partIcmp1=" + partIcmp1 + ", partIcmp2=" + partIcmp2 + ", partIcmp3=" + partIcmp3
				+ ", partIcmp4a=" + partIcmp4a + ", partIcmp4b=" + partIcmp4b + ", partIcmp4c=" + partIcmp4c
				+ ", partIcmp4d=" + partIcmp4d + ", partIcmp4e=" + partIcmp4e + ", partIcmp4f=" + partIcmp4f
				+ ", partIcmp4g=" + partIcmp4g + ", partIcmp4h=" + partIcmp4h + ", partIcmp4i=" + partIcmp4i
				+ ", partIcmp4j=" + partIcmp4j + ", partIcmp4k=" + partIcmp4k + ", partIcmp4l=" + partIcmp4l
				+ ", partIcmp4m=" + partIcmp4m + ", partIcmp4n=" + partIcmp4n + ", partIcmp5a=" + partIcmp5a
				+ ", partIcmp5b=" + partIcmp5b + ", partIcmp5c=" + partIcmp5c + ", partIcmp5d=" + partIcmp5d
				+ ", partIcmp5e=" + partIcmp5e + ", partIcmp5f=" + partIcmp5f + ", partIcmp5g=" + partIcmp5g
				+ ", partIcmp5h=" + partIcmp5h + ", partIcmp5i=" + partIcmp5i + ", partIcmp5j=" + partIcmp5j
				+ ", partIcmp5k=" + partIcmp5k + ", partIcmp5l=" + partIcmp5l + ", partIcmp5m=" + partIcmp5m
				+ ", partIcmp5n=" + partIcmp5n + ", partIcmp5o=" + partIcmp5o + ", partIcmp5p=" + partIcmp5p
				+ ", partIcmp5q=" + partIcmp5q + ", partIcmp5r=" + partIcmp5r + ", partIcmp5s=" + partIcmp5s
				+ ", partIcmp5t=" + partIcmp5t + ", partIcmp5u=" + partIcmp5u + ", partIcmp5v=" + partIcmp5v
				+ ", partIcmp5w=" + partIcmp5w + ", partIcmp5x=" + partIcmp5x + ", partIcmp5y=" + partIcmp5y
				+ ", partIcmp5z=" + partIcmp5z + ", partIcmp5aa=" + partIcmp5aa + ", partIcmp5ab=" + partIcmp5ab
				+ ", partIcmp5ac=" + partIcmp5ac + ", partIcmp5ad=" + partIcmp5ad + ", partIcmp5ae=" + partIcmp5ae
				+ ", partIcmp5af=" + partIcmp5af + ", partIcmp6a=" + partIcmp6a + ", partIcmp6b=" + partIcmp6b
				+ ", partIcmp6c=" + partIcmp6c + ", partIcmp7a=" + partIcmp7a + ", partIcmp7b=" + partIcmp7b
				+ ", partIcmp7c=" + partIcmp7c + ", partIcmp8=" + partIcmp8 + ", partIcmp9a1=" + partIcmp9a1
				+ ", partIcmp9a2=" + partIcmp9a2 + ", partIcmp9b1=" + partIcmp9b1 + ", partIcmp9b2=" + partIcmp9b2
				+ ", partIcmp10=" + partIcmp10 + ", partIIcmp11a=" + partIIcmp11a + ", partIIcmp11b=" + partIIcmp11b
				+ ", partIIcmp11c=" + partIIcmp11c + ", partIIcmp11d=" + partIIcmp11d + ", partIIcmp11e=" + partIIcmp11e
				+ ", partIIcmp12a=" + partIIcmp12a + ", partIIcmp12b=" + partIIcmp12b + ", partIIcmp12c=" + partIIcmp12c
				+ ", partIIcmp13=" + partIIcmp13 + ", partIII=" + partIII + ", partIIIcmp14a=" + partIIIcmp14a
				+ ", partIIIcmp14a1=" + partIIIcmp14a1 + ", partIIIcmp14b=" + partIIIcmp14b + ", partIIIcmp14ba="
				+ partIIIcmp14ba + ", partIIIcmp14bb=" + partIIIcmp14bb + ", partIIIcmp14bc=" + partIIIcmp14bc
				+ ", partIIIcmp14bd=" + partIIIcmp14bd + ", partIIIcmp14be=" + partIIIcmp14be + ", partIIIcmp14bf="
				+ partIIIcmp14bf + ", partIIIcmp14bg=" + partIIIcmp14bg + ", partIIIcmp14bh=" + partIIIcmp14bh
				+ ", partIIIcmp14bi=" + partIIIcmp14bi + ", partIIIcmp14bj=" + partIIIcmp14bj + ", partIIIcmp14bk="
				+ partIIIcmp14bk + ", partIIIcmp14c=" + partIIIcmp14c + ", partIIIcmp15a=" + partIIIcmp15a
				+ ", partIIIcmp15b=" + partIIIcmp15b + ", partIIIcmp15c=" + partIIIcmp15c + ", partIIIcmp15d1="
				+ partIIIcmp15d1 + ", partIIIcmp15d2=" + partIIIcmp15d2 + ", partIIIcmp15d3=" + partIIIcmp15d3
				+ ", partIV=" + partIV + ", partIVcmp16=" + partIVcmp16 + ", partIVcmp16a=" + partIVcmp16a
				+ ", partIVcmp17a=" + partIVcmp17a + ", partIVcmp17b=" + partIVcmp17b + ", partV=" + partV
				+ ", partVcmp18=" + partVcmp18 + ", partVI=" + partVI + ", partVIcmp19=" + partVIcmp19 + ", partVII="
				+ partVII + ", partVIIcmp20=" + partVIIcmp20 + ", partVIIcmp21=" + partVIIcmp21 + ", partVIII="
				+ partVIII + ", partVIIIcmp22=" + partVIIIcmp22 + ", partIX=" + partIX + ", partIXcmp23=" + partIXcmp23
				+ ", partX=" + partX + ", partXcmp24a=" + partXcmp24a + ", partXcmp24b=" + partXcmp24b
				+ ", partXcmp24c=" + partXcmp24c + ", partXcmp24d=" + partXcmp24d + ", partXI=" + partXI
				+ ", partXIcmp25a=" + partXIcmp25a + ", partXIcmp25b=" + partXIcmp25b + ", partXIcmp25c=" + partXIcmp25c
				+ ", partXII=" + partXII + ", partXIIcmp26a=" + partXIIcmp26a + ", partXIIcmp26b=" + partXIIcmp26b
				+ ", partXIIcmp26ba=" + partXIIcmp26ba + ", partXIIcmp26bb=" + partXIIcmp26bb + ", partXIIcmp26c="
				+ partXIIcmp26c + ", partXIIcmp26d=" + partXIIcmp26d + ", partXIIcmp26e=" + partXIIcmp26e
				+ ", partXIII=" + partXIII + ", partXIIIcmp27=" + partXIIIcmp27 + ", partXIV=" + partXIV
				+ ", partXIVcmp28a=" + partXIVcmp28a + ", partXIVcmp28b=" + partXIVcmp28b + ", partXV=" + partXV
				+ ", partXVcmp29a=" + partXVcmp29a + ", partXVcmp29b=" + partXVcmp29b + ", partXVcmp29c=" + partXVcmp29c
				+ ", partXVcmp29d=" + partXVcmp29d + ", partXVcmp29e=" + partXVcmp29e + ", partXVcmp29f=" + partXVcmp29f
				+ ", partXVI=" + partXVI + ", partXVIcmp30=" + partXVIcmp30 + ", partXVII=" + partXVII
				+ ", partXVIIcmp31=" + partXVIIcmp31 + ", partXVIII=" + partXVIII + ", partXVIIIcmp32=" + partXVIIIcmp32
				+ ", partXIX=" + partXIX + ", partXIXcmp33a=" + partXIXcmp33a + ", partXIXcmp33b=" + partXIXcmp33b
				+ ", partXX=" + partXX + ", partXXcmp34a=" + partXXcmp34a + ", partXXcmp34b=" + partXXcmp34b
				+ ", partXXI=" + partXXI + ", partXXIcmp35a=" + partXXIcmp35a + ", partXXIcmp35b=" + partXXIcmp35b
				+ ", partXXII=" + partXXII + ", partXXIIcmp36=" + partXXIIcmp36 + ", partXXIII=" + partXXIII
				+ ", partXXIIIcmp37a=" + partXXIIIcmp37a + ", partXXIIIcmp37b=" + partXXIIIcmp37b + ", partXXIIIcmp37c="
				+ partXXIIIcmp37c + ", partXXIIIcmp37d=" + partXXIIIcmp37d + ", partXXIIIcmp37e=" + partXXIIIcmp37e
				+ ", partXXIV=" + partXXIV + ", partXXIVcmp38=" + partXXIVcmp38 + ", partXXV=" + partXXV
				+ ", partXXVcmp39=" + partXXVcmp39 + ", partXXVI=" + partXXVI + ", partXXVIcmp40a=" + partXXVIcmp40a
				+ ", partXXVIcmp40b=" + partXXVIcmp40b + ", partXXVIcmp40c=" + partXXVIcmp40c + ", partXXVII="
				+ partXXVII + ", partXXVIIcmp41=" + partXXVIIcmp41 + ", partXXVIII=" + partXXVIII + ", partXXVIIIcmp42="
				+ partXXVIIIcmp42 + ", partXXVIIIcmp42_2=" + partXXVIIIcmp42_2 + ", partXXVIIIcmp43=" + partXXVIIIcmp43
				+ ", partXXIX=" + partXXIX + ", SIEFORE_AFORE=" + SIEFORE_AFORE + ", paisResidencial=" + paisResidencial
				+ ", printName=" + printName + ", inhabilitarPartIcmp7=" + inhabilitarPartIcmp7 + ", esCmp4a=" + esCmp4a
				+ ", esCmp4b=" + esCmp4b + ", esCmp4c=" + esCmp4c + ", esCmp4d=" + esCmp4d + ", esCmp4e=" + esCmp4e
				+ ", esCmp4f=" + esCmp4f + ", esCmp4g=" + esCmp4g + ", esCmp4h=" + esCmp4h + ", esCmp4i=" + esCmp4i
				+ ", esCmp4j=" + esCmp4j + ", esCmp4k=" + esCmp4k + ", esCmp4n=" + esCmp4n + ", readOnlyCmp9b="
				+ readOnlyCmp9b + ", readOnlyCmp14=" + readOnlyCmp14 + ", readOnlyCmp15c=" + readOnlyCmp15c
				+ ", partXXIXnoma=" + partXXIXnoma + ", partXXIXnomb=" + partXXIXnomb + ", partXXIXnomc=" + partXXIXnomc
				+ ", partXXIXnomd=" + partXXIXnomd + ", partXXIXnome=" + partXXIXnome + ", partXXIXnomf=" + partXXIXnomf
				+ ", partXXIXnomg=" + partXXIXnomg + ", partXXIXnomh=" + partXXIXnomh + ", partXXIXnomi=" + partXXIXnomi
				+ ", partXXIXadra=" + partXXIXadra + ", partXXIXadrb=" + partXXIXadrb + ", partXXIXadrc=" + partXXIXadrc
				+ ", partXXIXadrd=" + partXXIXadrd + ", partXXIXadre=" + partXXIXadre + ", partXXIXadrf=" + partXXIXadrf
				+ ", partXXIXadrg=" + partXXIXadrg + ", partXXIXadrh=" + partXXIXadrh + ", partXXIXadri=" + partXXIXadri
				+ ", partXXIXtina=" + partXXIXtina + ", partXXIXtinb=" + partXXIXtinb + ", partXXIXtinc=" + partXXIXtinc
				+ ", partXXIXtind=" + partXXIXtind + ", partXXIXtine=" + partXXIXtine + ", partXXIXtinf=" + partXXIXtinf
				+ ", partXXIXting=" + partXXIXting + ", partXXIXtinh=" + partXXIXtinh + ", partXXIXtini=" + partXXIXtini
				+ ", partXXX=" + partXXX + "]";
	}
}
