/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.formatosw;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.W8IMY2017Util;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Formato W8 IMY 2015
 * 
 * @author Abraham Morales
 * 
 */
public class FormaW8IMY2017 extends FormaGeneral implements Serializable {

    /** Id para la serializacion */
    private static final long serialVersionUID = 1L;

    // Atributos Parte I
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

    private String partIcmp6a;
    private String partIcmp6b;
    private String partIcmp6c;

    private String partIcmp7a;
    private String partIcmp7b;
    private String partIcmp7c;

    private String partIcmp8;
    private boolean partIcmp8a;
    private boolean partIcmp8b;
    private boolean partIcmp8c;
    private boolean partIcmp8d;
    private boolean partIcmp8e;

    private String partIcmp9;// giin

    private String partIcmp10;

    // Atributos Parte II
    private boolean partIIcmp11a;
    private boolean partIIcmp11b;
    private boolean partIIcmp11c;
    private boolean partIIcmp11d;
    private boolean partIIcmp11e;

    private String partIIcmp12a;
    private String partIIcmp12b;
    private String partIIcmp12c;

    private String partIIcmp13;// giin

	/** Part XXIX Certification **/
	private String certification;

    /** Indica la pais de la direccion residencial */
    private String paisResidencial;

    /** Indica quien capturo el formato */
    private String printName;

    /** Indica que el campo 4a es de solo lectura */
    private boolean readOnlyCmp4a;

    /** Indica que el campo 4h es de solo lectura */
    private boolean readOnlyCmp4h;

    /** Indica que el campo 4 b,c,d,e,f,g,i son de solo lectura */
    private boolean readOnlyCmp4;

    /** Indica que el campo 7 es de solo lectura */
    private boolean readOnlyCmp7;

    /** Indica que el campo 8 es de solo lectura */
    private boolean readOnlyCmp8;

    /** Indica que las opciones del campo 8 son de solo lectura */
    private boolean readOnlyOpcionesCmp8;

    /** Indica que el campo 10 es de solo lectura */
    private boolean readOnlyCmp10;

    /** Indica que el campo 14a es de solo lectura */
    private boolean readOnlyCmp14a;

    /** Indica que el campo 19 es de solo lectura */
    private boolean readOnlyCmp19;

    /**
     * Constructor de la clase
     */
    public FormaW8IMY2017() {}

    /**
     * Constructor de la clase
     * 
     * @param idCuentaNombrada Id de la cuenta nombrada del custodio.
     * @param idTipoBeneficiario Id del tipo de beneficiario.
     * @param idInstitucion Id de la institución.
     */
    public FormaW8IMY2017(final Long idCuentaNombrada, final Long idTipoBeneficiario,
            final Long idInstitucion) {
        super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
        // Coloca a false la bandera para deshabilitar el domicilio de correo
        this.setDisabledDireccionPostal(false);
        // Iniciliaza el campo 4
        if (Constantes.INTERMEDIARIO_CALIFICADO_CRPR == idTipoBeneficiario
                || Constantes.INTERMEDIARIO_CALIFICADO_SRPR == idTipoBeneficiario) {
            this.partIcmp4a = true;
            this.readOnlyCmp4a = true;
            this.readOnlyCmp4h = true;
            this.readOnlyCmp4 = true;
            // this.partIII = true;
            // Checamos la opcion QI-EIN del campo 8 y marcamos las demas opciones
            // como solo lectura
            this.partIcmp8a = true;
            this.readOnlyCmp8 = false;
            this.readOnlyOpcionesCmp8 = true;
            // Checamos el campo 14a y lo marcamos de solo lectura
            this.readOnlyCmp14a = true;
            // this.partIIIcmp14a = true;
        } else if (Constantes.FIDEICOMISO_SIMPLE == idTipoBeneficiario
                || Constantes.FIDEICOMISO_SIMPLE_W8IMY == idTipoBeneficiario) {
            this.partIcmp4h = true;
            this.readOnlyCmp4a = true;
            this.readOnlyCmp4h = true;
            this.readOnlyCmp4 = true;
            // Indicamos que la parte VIII es obligatoria y la llenamos
            // this.partVIII = true;
            this.readOnlyCmp19 = true;
            // this.partVIIIcmp19 = true;
            // Indicamos que el campo 8 y sus opciones son de solo lectura
            this.readOnlyCmp8 = true;
            this.readOnlyOpcionesCmp8 = true;
        } else if (BeneficiariosConstantes.OTROS_W8_IMY == idTipoBeneficiario) {
            this.readOnlyCmp4a = false;
            this.readOnlyCmp4h = false;
            this.readOnlyCmp4 = false;
            this.readOnlyCmp8 = false;
            this.readOnlyOpcionesCmp8 = false;
        }
        // Inicializa el campo 10
        if (Constantes.CN_CLEARSTREAM == idCuentaNombrada) {
            this.partIcmp10 = BeneficiariosConstantes.REF_CLEARSTREAM;
            this.readOnlyCmp10 = true;
        } else if (Constantes.CN_EUROCLEAR_BANK == idCuentaNombrada) {
            this.partIcmp10 = BeneficiariosConstantes.REF_EUROCLEAR_BANK;
            this.readOnlyCmp10 = true;
        }
        // this.partXXVIII = true;
    }


    @Override
    public Beneficiario construyeBO() {
        // Pasa a mayusculas
        W8IMY2017Util.formaAMayusculas(this);
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
        W8IMY2017Util.formaAMayusculas(this);
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
     * @see com.indeval.portaldali.middleware.formatosw.FormaGeneral#construyeBean(com.indeval.
     * portalinternacional.middleware.servicios.modelo.Beneficiario, boolean)
     */
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
        this.paisResidencial = this.partIcmp2;
        W8IMY2017Util.obtenerTipoBeneficiario(this);
        if (llenaCamposVacios) {
            this.ponerValoresVacios();
        }
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
        this.paisResidencial = this.partIcmp2;
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
     * @return the partIcmp1
     */
    public String getPartIcmp1() {
        return this.partIcmp1;
    }

    /**
     * @param partIcmp1 the partIcmp1 to set
     */
    public void setPartIcmp1(final String partIcmp1) {
        this.partIcmp1 = partIcmp1;
    }

    /**
     * @return the partIcmp2
     */
    public String getPartIcmp2() {
        return this.partIcmp2;
    }

    /**
     * @param partIcmp2 the partIcmp2 to set
     */
    public void setPartIcmp2(final String partIcmp2) {
        this.partIcmp2 = partIcmp2;
    }

    /**
     * @return the partIcmp3
     */
    public String getPartIcmp3() {
        return this.partIcmp3;
    }

    /**
     * @param partIcmp3 the partIcmp3 to set
     */
    public void setPartIcmp3(final String partIcmp3) {
        this.partIcmp3 = partIcmp3;
    }

    /**
     * @return the partIcmp4a
     */
    public boolean isPartIcmp4a() {
        return this.partIcmp4a;
    }

    /**
     * @param partIcmp4a the partIcmp4a to set
     */
    public void setPartIcmp4a(final boolean partIcmp4a) {
        this.partIcmp4a = partIcmp4a;
    }

    /**
     * @return the partIcmp4b
     */
    public boolean isPartIcmp4b() {
        return this.partIcmp4b;
    }

    /**
     * @param partIcmp4b the partIcmp4b to set
     */
    public void setPartIcmp4b(final boolean partIcmp4b) {
        this.partIcmp4b = partIcmp4b;
    }

    /**
     * @return the partIcmp4c
     */
    public boolean isPartIcmp4c() {
        return this.partIcmp4c;
    }

    /**
     * @param partIcmp4c the partIcmp4c to set
     */
    public void setPartIcmp4c(final boolean partIcmp4c) {
        this.partIcmp4c = partIcmp4c;
    }

    /**
     * @return the partIcmp4d
     */
    public boolean isPartIcmp4d() {
        return this.partIcmp4d;
    }

    /**
     * @param partIcmp4d the partIcmp4d to set
     */
    public void setPartIcmp4d(final boolean partIcmp4d) {
        this.partIcmp4d = partIcmp4d;
    }

    /**
     * @return the partIcmp4e
     */
    public boolean isPartIcmp4e() {
        return this.partIcmp4e;
    }

    /**
     * @param partIcmp4e the partIcmp4e to set
     */
    public void setPartIcmp4e(final boolean partIcmp4e) {
        this.partIcmp4e = partIcmp4e;
    }

    /**
     * @return the partIcmp4f
     */
    public boolean isPartIcmp4f() {
        return this.partIcmp4f;
    }

    /**
     * @param partIcmp4f the partIcmp4f to set
     */
    public void setPartIcmp4f(final boolean partIcmp4f) {
        this.partIcmp4f = partIcmp4f;
    }

    /**
     * @return the partIcmp4g
     */
    public boolean isPartIcmp4g() {
        return this.partIcmp4g;
    }

    /**
     * @param partIcmp4g the partIcmp4g to set
     */
    public void setPartIcmp4g(final boolean partIcmp4g) {
        this.partIcmp4g = partIcmp4g;
    }

    /**
     * @return the partIcmp4h
     */
    public boolean isPartIcmp4h() {
        return this.partIcmp4h;
    }

    /**
     * @param partIcmp4h the partIcmp4h to set
     */
    public void setPartIcmp4h(final boolean partIcmp4h) {
        this.partIcmp4h = partIcmp4h;
    }

    /**
     * @return the partIcmp4i
     */
    public boolean isPartIcmp4i() {
        return this.partIcmp4i;
    }

    /**
     * @param partIcmp4i the partIcmp4i to set
     */
    public void setPartIcmp4i(final boolean partIcmp4i) {
        this.partIcmp4i = partIcmp4i;
    }

    /**
     * @return the partIcmp5a
     */
    public boolean isPartIcmp5a() {
        return this.partIcmp5a;
    }

    /**
     * @param partIcmp5a the partIcmp5a to set
     */
    public void setPartIcmp5a(final boolean partIcmp5a) {
        this.partIcmp5a = partIcmp5a;
    }

    /**
     * @return the partIcmp5b
     */
    public boolean isPartIcmp5b() {
        return this.partIcmp5b;
    }

    /**
     * @param partIcmp5b the partIcmp5b to set
     */
    public void setPartIcmp5b(final boolean partIcmp5b) {
        this.partIcmp5b = partIcmp5b;
    }

    /**
     * @return the partIcmp5c
     */
    public boolean isPartIcmp5c() {
        return this.partIcmp5c;
    }

    /**
     * @param partIcmp5c the partIcmp5c to set
     */
    public void setPartIcmp5c(final boolean partIcmp5c) {
        this.partIcmp5c = partIcmp5c;
    }

    /**
     * @return the partIcmp5d
     */
    public boolean isPartIcmp5d() {
        return this.partIcmp5d;
    }

    /**
     * @param partIcmp5d the partIcmp5d to set
     */
    public void setPartIcmp5d(final boolean partIcmp5d) {
        this.partIcmp5d = partIcmp5d;
    }

    /**
     * @return the partIcmp5e
     */
    public boolean isPartIcmp5e() {
        return this.partIcmp5e;
    }

    /**
     * @param partIcmp5e the partIcmp5e to set
     */
    public void setPartIcmp5e(final boolean partIcmp5e) {
        this.partIcmp5e = partIcmp5e;
    }

    /**
     * @return the partIcmp5f
     */
    public boolean isPartIcmp5f() {
        return this.partIcmp5f;
    }

    /**
     * @param partIcmp5f the partIcmp5f to set
     */
    public void setPartIcmp5f(final boolean partIcmp5f) {
        this.partIcmp5f = partIcmp5f;
    }

    /**
     * @return the partIcmp5g
     */
    public boolean isPartIcmp5g() {
        return this.partIcmp5g;
    }

    /**
     * @param partIcmp5g the partIcmp5g to set
     */
    public void setPartIcmp5g(final boolean partIcmp5g) {
        this.partIcmp5g = partIcmp5g;
    }

    /**
     * @return the partIcmp5h
     */
    public boolean isPartIcmp5h() {
        return this.partIcmp5h;
    }

    /**
     * @param partIcmp5h the partIcmp5h to set
     */
    public void setPartIcmp5h(final boolean partIcmp5h) {
        this.partIcmp5h = partIcmp5h;
    }

    /**
     * @return the partIcmp5i
     */
    public boolean isPartIcmp5i() {
        return this.partIcmp5i;
    }

    /**
     * @param partIcmp5i the partIcmp5i to set
     */
    public void setPartIcmp5i(final boolean partIcmp5i) {
        this.partIcmp5i = partIcmp5i;
    }

    /**
     * @return the partIcmp5j
     */
    public boolean isPartIcmp5j() {
        return this.partIcmp5j;
    }

    /**
     * @param partIcmp5j the partIcmp5j to set
     */
    public void setPartIcmp5j(final boolean partIcmp5j) {
        this.partIcmp5j = partIcmp5j;
    }

    /**
     * @return the partIcmp5k
     */
    public boolean isPartIcmp5k() {
        return this.partIcmp5k;
    }

    /**
     * @param partIcmp5k the partIcmp5k to set
     */
    public void setPartIcmp5k(final boolean partIcmp5k) {
        this.partIcmp5k = partIcmp5k;
    }

    /**
     * @return the partIcmp5l
     */
    public boolean isPartIcmp5l() {
        return this.partIcmp5l;
    }

    /**
     * @param partIcmp5l the partIcmp5l to set
     */
    public void setPartIcmp5l(final boolean partIcmp5l) {
        this.partIcmp5l = partIcmp5l;
    }

    /**
     * @return the partIcmp5m
     */
    public boolean isPartIcmp5m() {
        return this.partIcmp5m;
    }

    /**
     * @param partIcmp5m the partIcmp5m to set
     */
    public void setPartIcmp5m(final boolean partIcmp5m) {
        this.partIcmp5m = partIcmp5m;
    }

    /**
     * @return the partIcmp5n
     */
    public boolean isPartIcmp5n() {
        return this.partIcmp5n;
    }

    /**
     * @param partIcmp5n the partIcmp5n to set
     */
    public void setPartIcmp5n(final boolean partIcmp5n) {
        this.partIcmp5n = partIcmp5n;
    }

    /**
     * @return the partIcmp5o
     */
    public boolean isPartIcmp5o() {
        return this.partIcmp5o;
    }

    /**
     * @param partIcmp5o the partIcmp5o to set
     */
    public void setPartIcmp5o(final boolean partIcmp5o) {
        this.partIcmp5o = partIcmp5o;
    }

    /**
     * @return the partIcmp5p
     */
    public boolean isPartIcmp5p() {
        return this.partIcmp5p;
    }

    /**
     * @param partIcmp5p the partIcmp5p to set
     */
    public void setPartIcmp5p(final boolean partIcmp5p) {
        this.partIcmp5p = partIcmp5p;
    }

    /**
     * @return the partIcmp5q
     */
    public boolean isPartIcmp5q() {
        return this.partIcmp5q;
    }

    /**
     * @param partIcmp5q the partIcmp5q to set
     */
    public void setPartIcmp5q(final boolean partIcmp5q) {
        this.partIcmp5q = partIcmp5q;
    }

    /**
     * @return the partIcmp5r
     */
    public boolean isPartIcmp5r() {
        return this.partIcmp5r;
    }

    /**
     * @param partIcmp5r the partIcmp5r to set
     */
    public void setPartIcmp5r(final boolean partIcmp5r) {
        this.partIcmp5r = partIcmp5r;
    }

    /**
     * @return the partIcmp5s
     */
    public boolean isPartIcmp5s() {
        return this.partIcmp5s;
    }

    /**
     * @param partIcmp5s the partIcmp5s to set
     */
    public void setPartIcmp5s(final boolean partIcmp5s) {
        this.partIcmp5s = partIcmp5s;
    }

    /**
     * @return the partIcmp5t
     */
    public boolean isPartIcmp5t() {
        return this.partIcmp5t;
    }

    /**
     * @param partIcmp5t the partIcmp5t to set
     */
    public void setPartIcmp5t(final boolean partIcmp5t) {
        this.partIcmp5t = partIcmp5t;
    }

    /**
     * @return the partIcmp5u
     */
    public boolean isPartIcmp5u() {
        return this.partIcmp5u;
    }

    /**
     * @param partIcmp5u the partIcmp5u to set
     */
    public void setPartIcmp5u(final boolean partIcmp5u) {
        this.partIcmp5u = partIcmp5u;
    }

    /**
     * @return the partIcmp5v
     */
    public boolean isPartIcmp5v() {
        return this.partIcmp5v;
    }

    /**
     * @param partIcmp5v the partIcmp5v to set
     */
    public void setPartIcmp5v(final boolean partIcmp5v) {
        this.partIcmp5v = partIcmp5v;
    }

    /**
     * @return the partIcmp5w
     */
    public boolean isPartIcmp5w() {
        return this.partIcmp5w;
    }

    /**
     * @param partIcmp5w the partIcmp5w to set
     */
    public void setPartIcmp5w(final boolean partIcmp5w) {
        this.partIcmp5w = partIcmp5w;
    }

    /**
     * @return the partIcmp5x
     */
    public boolean isPartIcmp5x() {
        return this.partIcmp5x;
    }

    /**
     * @param partIcmp5x the partIcmp5x to set
     */
    public void setPartIcmp5x(final boolean partIcmp5x) {
        this.partIcmp5x = partIcmp5x;
    }

    /**
     * @return the partIcmp5y
     */
    public boolean isPartIcmp5y() {
        return this.partIcmp5y;
    }

    /**
     * @param partIcmp5y the partIcmp5y to set
     */
    public void setPartIcmp5y(final boolean partIcmp5y) {
        this.partIcmp5y = partIcmp5y;
    }

    /**
     * @return the partIcmp6a
     */
    public String getPartIcmp6a() {
        return this.partIcmp6a;
    }

    /**
     * @param partIcmp6a the partIcmp6a to set
     */
    public void setPartIcmp6a(final String partIcmp6a) {
        this.partIcmp6a = partIcmp6a;
    }

    /**
     * @return the partIcmp6b
     */
    public String getPartIcmp6b() {
        return this.partIcmp6b;
    }

    /**
     * @param partIcmp6b the partIcmp6b to set
     */
    public void setPartIcmp6b(final String partIcmp6b) {
        this.partIcmp6b = partIcmp6b;
    }

    /**
     * @return the partIcmp6c
     */
    public String getPartIcmp6c() {
        return this.partIcmp6c;
    }

    /**
     * @param partIcmp6c the partIcmp6c to set
     */
    public void setPartIcmp6c(final String partIcmp6c) {
        this.partIcmp6c = partIcmp6c;
    }

    /**
     * @return the partIcmp7a
     */
    public String getPartIcmp7a() {
        return this.partIcmp7a;
    }

    /**
     * @param partIcmp7a the partIcmp7a to set
     */
    public void setPartIcmp7a(final String partIcmp7a) {
        this.partIcmp7a = partIcmp7a;
    }

    /**
     * @return the partIcmp7b
     */
    public String getPartIcmp7b() {
        return this.partIcmp7b;
    }

    /**
     * @param partIcmp7b the partIcmp7b to set
     */
    public void setPartIcmp7b(final String partIcmp7b) {
        this.partIcmp7b = partIcmp7b;
    }

    /**
     * @return the partIcmp7c
     */
    public String getPartIcmp7c() {
        return this.partIcmp7c;
    }

    /**
     * @param partIcmp7c the partIcmp7c to set
     */
    public void setPartIcmp7c(final String partIcmp7c) {
        this.partIcmp7c = partIcmp7c;
    }

    /**
     * @return the partIcmp8
     */
    public String getPartIcmp8() {
        return this.partIcmp8;
    }

    /**
     * @param partIcmp8 the partIcmp8 to set
     */
    public void setPartIcmp8(final String partIcmp8) {
        this.partIcmp8 = partIcmp8;
    }

    /**
     * @return the partIcmp8a
     */
    public boolean isPartIcmp8a() {
        return this.partIcmp8a;
    }

    /**
     * @param partIcmp8a the partIcmp8a to set
     */
    public void setPartIcmp8a(final boolean partIcmp8a) {
        this.partIcmp8a = partIcmp8a;
    }

    /**
     * @return the partIcmp8b
     */
    public boolean isPartIcmp8b() {
        return this.partIcmp8b;
    }

    /**
     * @param partIcmp8b the partIcmp8b to set
     */
    public void setPartIcmp8b(final boolean partIcmp8b) {
        this.partIcmp8b = partIcmp8b;
    }

    /**
     * @return the partIcmp8c
     */
    public boolean isPartIcmp8c() {
        return this.partIcmp8c;
    }

    /**
     * @param partIcmp8c the partIcmp8c to set
     */
    public void setPartIcmp8c(final boolean partIcmp8c) {
        this.partIcmp8c = partIcmp8c;
    }

    /**
     * @return the partIcmp8d
     */
    public boolean isPartIcmp8d() {
        return this.partIcmp8d;
    }

    /**
     * @param partIcmp8d the partIcmp8d to set
     */
    public void setPartIcmp8d(final boolean partIcmp8d) {
        this.partIcmp8d = partIcmp8d;
    }

    /**
     * @return the partIcmp8e
     */
    public boolean isPartIcmp8e() {
        return this.partIcmp8e;
    }

    /**
     * @param partIcmp8e the partIcmp8e to set
     */
    public void setPartIcmp8e(final boolean partIcmp8e) {
        this.partIcmp8e = partIcmp8e;
    }

    /**
     * @return the partIcmp9
     */
    public String getPartIcmp9() {
        return this.partIcmp9;
    }

    /**
     * @param partIcmp9 the partIcmp9 to set
     */
    public void setPartIcmp9(final String partIcmp9) {
        this.partIcmp9 = partIcmp9;
    }

    /**
     * @return the partIcmp10
     */
    public String getPartIcmp10() {
        return this.partIcmp10;
    }

    /**
     * @param partIcmp10 the partIcmp10 to set
     */
    public void setPartIcmp10(final String partIcmp10) {
        this.partIcmp10 = partIcmp10;
    }

    /**
     * @return the partIIcmp11a
     */
    public boolean isPartIIcmp11a() {
        return this.partIIcmp11a;
    }

    /**
     * @param partIIcmp11a the partIIcmp11a to set
     */
    public void setPartIIcmp11a(final boolean partIIcmp11a) {
        this.partIIcmp11a = partIIcmp11a;
    }

    /**
     * @return the partIIcmp11b
     */
    public boolean isPartIIcmp11b() {
        return this.partIIcmp11b;
    }

    /**
     * @param partIIcmp11b the partIIcmp11b to set
     */
    public void setPartIIcmp11b(final boolean partIIcmp11b) {
        this.partIIcmp11b = partIIcmp11b;
    }

    /**
     * @return the partIIcmp11c
     */
    public boolean isPartIIcmp11c() {
        return this.partIIcmp11c;
    }

    /**
     * @param partIIcmp11c the partIIcmp11c to set
     */
    public void setPartIIcmp11c(final boolean partIIcmp11c) {
        this.partIIcmp11c = partIIcmp11c;
    }

    /**
     * @return the partIIcmp11d
     */
    public boolean isPartIIcmp11d() {
        return this.partIIcmp11d;
    }

    /**
     * @param partIIcmp11d the partIIcmp11d to set
     */
    public void setPartIIcmp11d(final boolean partIIcmp11d) {
        this.partIIcmp11d = partIIcmp11d;
    }

    /**
     * @return the partIIcmp11e
     */
    public boolean isPartIIcmp11e() {
        return this.partIIcmp11e;
    }

    /**
     * @param partIIcmp11e the partIIcmp11e to set
     */
    public void setPartIIcmp11e(final boolean partIIcmp11e) {
        this.partIIcmp11e = partIIcmp11e;
    }

    /**
     * @return the partIIcmp12a
     */
    public String getPartIIcmp12a() {
        return this.partIIcmp12a;
    }

    /**
     * @param partIIcmp12a the partIIcmp12a to set
     */
    public void setPartIIcmp12a(final String partIIcmp12a) {
        this.partIIcmp12a = partIIcmp12a;
    }

    /**
     * @return the partIIcmp12b
     */
    public String getPartIIcmp12b() {
        return this.partIIcmp12b;
    }

    /**
     * @param partIIcmp12b the partIIcmp12b to set
     */
    public void setPartIIcmp12b(final String partIIcmp12b) {
        this.partIIcmp12b = partIIcmp12b;
    }

    /**
     * @return the partIIcmp12c
     */
    public String getPartIIcmp12c() {
        return this.partIIcmp12c;
    }

    /**
     * @param partIIcmp12c the partIIcmp12c to set
     */
    public void setPartIIcmp12c(final String partIIcmp12c) {
        this.partIIcmp12c = partIIcmp12c;
    }

    /**
     * @return the partIIcmp13
     */
    public String getPartIIcmp13() {
        return this.partIIcmp13;
    }

    /**
     * @param partIIcmp13 the partIIcmp13 to set
     */
    public void setPartIIcmp13(final String partIIcmp13) {
        this.partIIcmp13 = partIIcmp13;
    }


    /**
     * 
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
     * Método para obtener el atributo readOnlyCmp4a
     * 
     * @return El atributo readOnlyCmp4a
     */
    public boolean isReadOnlyCmp4a() {
        return this.readOnlyCmp4a;
    }

    /**
     * Método para establecer el atributo readOnlyCmp4a
     * 
     * @param readOnlyCmp4a El valor del atributo readOnlyCmp4a a establecer.
     */
    public void setReadOnlyCmp4a(final boolean readOnlyCmp4a) {
        this.readOnlyCmp4a = readOnlyCmp4a;
    }

    /**
     * Método para obtener el atributo readOnlyCmp4h
     * 
     * @return El atributo readOnlyCmp4h
     */
    public boolean isReadOnlyCmp4h() {
        return this.readOnlyCmp4h;
    }

    /**
     * Método para establecer el atributo readOnlyCmp4h
     * 
     * @param readOnlyCmp4h El valor del atributo readOnlyCmp4h a establecer.
     */
    public void setReadOnlyCmp4h(final boolean readOnlyCmp4h) {
        this.readOnlyCmp4h = readOnlyCmp4h;
    }

    /**
     * Método para obtener el atributo readOnlyCmp4
     * 
     * @return El atributo readOnlyCmp4
     */
    public boolean isReadOnlyCmp4() {
        return this.readOnlyCmp4;
    }

    /**
     * Método para establecer el atributo readOnlyCmp4
     * 
     * @param readOnlyCmp4 El valor del atributo readOnlyCmp4 a establecer.
     */
    public void setReadOnlyCmp4(final boolean readOnlyCmp4) {
        this.readOnlyCmp4 = readOnlyCmp4;
    }

    /**
     * Método para obtener el atributo readOnlyCmp10
     * 
     * @return El atributo readOnlyCmp10
     */
    public boolean isReadOnlyCmp10() {
        return this.readOnlyCmp10;
    }

    /**
     * Método para establecer el atributo readOnlyCmp10
     * 
     * @param readOnlyCmp10 El valor del atributo readOnlyCmp10 a establecer.
     */
    public void setReadOnlyCmp10(final boolean readOnlyCmp10) {
        this.readOnlyCmp10 = readOnlyCmp10;
    }

    /**
     * Método para obtener el atributo readOnlyCmp7
     * 
     * @return El atributo readOnlyCmp7
     */
    public boolean isReadOnlyCmp7() {
        return this.readOnlyCmp7;
    }

    /**
     * Método para establecer el atributo readOnlyCmp7
     * 
     * @param readOnlyCmp7 El valor del atributo readOnlyCmp7 a establecer.
     */
    public void setReadOnlyCmp7(final boolean readOnlyCmp7) {
        this.readOnlyCmp7 = readOnlyCmp7;
    }

    /**
     * Método para obtener el atributo readOnlyCmp8
     * 
     * @return El atributo readOnlyCmp8
     */
    public boolean isReadOnlyCmp8() {
        return this.readOnlyCmp8;
    }

    /**
     * Método para establecer el atributo readOnlyCmp8
     * 
     * @param readOnlyCmp8 El valor del atributo readOnlyCmp8 a establecer.
     */
    public void setReadOnlyCmp8(final boolean readOnlyCmp8) {
        this.readOnlyCmp8 = readOnlyCmp8;
    }

    /**
     * Método para obtener el atributo readOnlyOpcionesCmp8
     * 
     * @return El atributo readOnlyOpcionesCmp8
     */
    public boolean isReadOnlyOpcionesCmp8() {
        return this.readOnlyOpcionesCmp8;
    }

    /**
     * Método para establecer el atributo readOnlyOpcionesCmp8
     * 
     * @param readOnlyOpcionesCmp8 El valor del atributo readOnlyOpcionesCmp8 a establecer.
     */
    public void setReadOnlyOpcionesCmp8(final boolean readOnlyOpcionesCmp8) {
        this.readOnlyOpcionesCmp8 = readOnlyOpcionesCmp8;
    }

    /**
     * Método para obtener el atributo readOnlyCmp14a
     * 
     * @return El atributo readOnlyCmp14a
     */
    public boolean isReadOnlyCmp14a() {
        return this.readOnlyCmp14a;
    }

    /**
     * Método para establecer el atributo readOnlyCmp14a
     * 
     * @param readOnlyCmp14a El valor del atributo readOnlyCmp14a a establecer.
     */
    public void setReadOnlyCmp14a(final boolean readOnlyCmp14a) {
        this.readOnlyCmp14a = readOnlyCmp14a;
    }

    /**
     * Método para obtener el atributo readOnlyCmp19
     * 
     * @return El atributo readOnlyCmp19
     */
    public boolean isReadOnlyCmp19() {
        return this.readOnlyCmp19;
    }

    /**
     * Método para establecer el atributo readOnlyCmp19
     * 
     * @param readOnlyCmp19 El valor del atributo readOnlyCmp19 a establecer.
     */
    public void setReadOnlyCmp19(final boolean readOnlyCmp19) {
        this.readOnlyCmp19 = readOnlyCmp19;
    }

    /** @return this.partIcmp5z */
    public boolean isPartIcmp5z() {
        return this.partIcmp5z;
    }

    /** @param partIcmp5z to be set in this.partIcmp5z */
    public void setPartIcmp5z(final boolean partIcmp5z) {
        this.partIcmp5z = partIcmp5z;
    }

	/**
	 * @return the certification
	 */
	public String getCertification() {
		return certification;
	}

	/**
	 * @param certification the certification to set
	 */
	public void setCertification(String certification) {
		this.certification = certification;
	}

}
