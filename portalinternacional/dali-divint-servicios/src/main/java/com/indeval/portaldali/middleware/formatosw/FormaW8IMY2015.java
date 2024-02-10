/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.formatosw;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.W8IMY2015Util;
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
public class FormaW8IMY2015 extends FormaGeneral implements Serializable {

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
	
	private String partIcmp9;
	
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

	private String partIIcmp13;

	// Atributos Parte III
	private boolean partIII;
	private boolean partIIIcmp14a;
	private boolean partIIIcmp14b;
	private boolean partIIIcmp14c;
	private String  partIIIcmp14c1;
	private boolean partIIIcmp14d;
	private String  partIIIcmp14d1;
	private boolean partIIIcmp14e;
	private String  partIIIcmp14e1;
	private boolean partIIIcmp14ei;
	private boolean partIIIcmp14eii;
	private boolean partIIIcmp14f;

	// Atributos Parte IV
	private boolean partIV;
	private boolean partIVcmp15a;
	private boolean partIVcmp15b;
	private boolean partIVcmp15c;
	private boolean partIVcmp15d;

	// Atributos Parte V
	private boolean partV;
	private boolean partVcmp16a;
	private boolean partVcmp16b;
	private boolean partVcmp16c;

	// Atributos Parte VI
	private boolean partVI;
	private boolean partVIcmp17a;
	private boolean partVIcmp17b;
	private boolean partVIcmp17c;

	// Atributos Parte VII
	private boolean partVII;
	private boolean partVIIcmp18;

	// Atributos Parte VIII
	private boolean partVIII;
	private boolean partVIIIcmp19;
	
	// Atributos Parte IX
	private boolean partIX;
	private boolean partIXcmp20;
	
	// Atributos Parte X
	private boolean partX;
	private String  partXcmp21a;
	private boolean partXcmp21b;
	private boolean partXcmp21c;
	
	// Atributos Parte XI
	private boolean partXI;
	private boolean partXIcmp22a;
	private boolean partXIcmp22b;
	private boolean partXIcmp22c;
	
	// Atributos Parte XII
	private boolean partXII;
	private boolean partXIIcmp23;

	// Atributos Parte XIII
	private boolean partXIII;
	private boolean partXIIIcmp24;

	// Atributos Parte XIV
	private boolean partXIV;
	private String  partXIVcmp25a;
	private boolean partXIVcmp25b;

	// Atributos Parte XV
	private boolean partXV;
	private boolean partXVcmp26;

	// Atributos Parte XVI
	private boolean partXVI;
	private boolean partXVIcmp27a;
	private boolean partXVIcmp27b;
	private boolean partXVIcmp27c;
	
	// Atributos Parte XVII
	private boolean partXVII;
	private boolean partXVIIcmp28;
	
	// Atributos Parte XVIII
	private boolean partXVIII;
	private boolean partXVIIIcmp29a;
	private String  partXVIIIcmp29b;
	private String  partXVIIIcmp29c;
	private String  partXVIIIcmp29d;
	
	// Atributos Parte XIX
	private boolean partXIX;
	private boolean partXIXcmp30a;
	private boolean partXIXcmp30b;
	private boolean partXIXcmp30c;
	private boolean partXIXcmp30d;
	private boolean partXIXcmp30e;
	private boolean partXIXcmp30f;
	
	// Atributos Parte XX
	private boolean partXX;
	private boolean partXXcmp31;

	// Atributos Parte XXI
	private boolean partXXI;
	private boolean partXXIcmp32a;
	private String  partXXIcmp32b;

	// Atributos Parte XXII
	private boolean partXXII;
	private boolean partXXIIcmp33a;
	private String  partXXIIcmp33b;

	// Atributos Parte XXIII
	private boolean partXXIII;
	private boolean partXXIIIcmp34a;
	private String  partXXIIIcmp34a1;
	private boolean partXXIIIcmp34b;
	private String  partXXIIIcmp34b1;
	private String  partXXIIIcmp34b2;
	
	// Atributos Parte XXIV
	private boolean partXXIV;
	private boolean partXXIVcmp35;
	
	// Atributos Parte XXV
	private boolean partXXV;
	private boolean partXXVcmp36;
	
	// Atributos Parte XXVI
	private boolean partXXVI;
	private boolean partXXVIcmp37;
	
	// Atributos Parte XXVII
	private boolean partXXVII;
	private String  partXXVIIcmp38;
	private boolean partXXVIIcmp39;

	// Atributos Parte XXVIII
	private boolean partXXVIII;
	
	// Atributos Parte XXIX
	private String partXXIX;

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
	public FormaW8IMY2015() {}
	
	/**
	 * Constructor de la clase
	 * @param idCuentaNombrada Id de la cuenta nombrada del custodio.
	 * @param idTipoBeneficiario Id del tipo de beneficiario.
	 * @param idInstitucion Id de la institución.
	 */
	public FormaW8IMY2015(Long idCuentaNombrada, Long idTipoBeneficiario, Long idInstitucion) {
		super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
		//Coloca a false la bandera para deshabilitar el domicilio de correo
		setDisabledDireccionPostal(false);
		//Iniciliaza el campo 4
		if(Constantes.INTERMEDIARIO_CALIFICADO_CRPR == idTipoBeneficiario || 
				Constantes.INTERMEDIARIO_CALIFICADO_SRPR == idTipoBeneficiario) {
			partIcmp4a = true;
			readOnlyCmp4a = true;
			readOnlyCmp4h = true;
			readOnlyCmp4 = true;
			partIII = true;
			//Checamos la opcion QI-EIN del campo 8 y marcamos las demas opciones
			//como solo lectura
			partIcmp8a = true;
			readOnlyCmp8 = false;
			readOnlyOpcionesCmp8 = true;
			//Checamos el campo 14a y lo marcamos de solo lectura
			readOnlyCmp14a = true;
			partIIIcmp14a = true;
		}
		else if(Constantes.FIDEICOMISO_SIMPLE == idTipoBeneficiario || Constantes.FIDEICOMISO_SIMPLE_W8IMY == idTipoBeneficiario) {
			partIcmp4h = true;
			readOnlyCmp4a = true;
			readOnlyCmp4h = true;
			readOnlyCmp4 = true;
			//Indicamos que la parte VIII es obligatoria y la llenamos
			partVIII = true;
			readOnlyCmp19 = true;
			partVIIIcmp19 = true;
			//Indicamos que el campo 8 y sus opciones son de solo lectura
			readOnlyCmp8 = true;
			readOnlyOpcionesCmp8 = true;
		}
		else if(BeneficiariosConstantes.OTROS_W8_IMY == idTipoBeneficiario) {
			readOnlyCmp4a = false;
			readOnlyCmp4h = false;
			readOnlyCmp4 = false;
			readOnlyCmp8 = false;
			readOnlyOpcionesCmp8 = false;
		}
		//Inicializa el campo 10
		if(Constantes.CN_CLEARSTREAM == idCuentaNombrada) {
			partIcmp10 = BeneficiariosConstantes.REF_CLEARSTREAM;
			readOnlyCmp10 = true;
		}
		else if(Constantes.CN_EUROCLEAR_BANK == idCuentaNombrada) {
			partIcmp10 = BeneficiariosConstantes.REF_EUROCLEAR_BANK;
			readOnlyCmp10 = true;
		}
		partXXVIII = true;
	}
	
	
	@Override
	public Beneficiario construyeBO() {
		//Pasa a mayusculas
		W8IMY2015Util.formaAMayusculas(this);
		//Crea el beneficiario
		Beneficiario beneficiario = new Beneficiario();
		construyeBOBasico(beneficiario);
		//Copia los campos de la forma
		copiarCampos();
		//Nombre del formato
		beneficiario.setTipoFormato(formato);
		//Tipo de beneficiario
		TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
		tipoBeneficiario.setIdTipoBeneficiario(idTipoBeneficiario);
		beneficiario.setTipoBeneficiario(tipoBeneficiario);
		//Indica que es persona moral
		beneficiario.setPersonaFisica(Boolean.FALSE);
		//Coloca la razón social
		beneficiario.setRazonSocial(razonSocial);
		//Cuenta Nombrada, esta dada por el custodio
		beneficiario.setIdCuentaNombrada(custodio);
		//Pais de incorporacion
		beneficiario.setPaisIncorporacion(pais);
		//Fecha del formato
		beneficiario.setFechaFormato(fechaFormato);
		//Fecha de registro
		beneficiario.setFechaRegistro(fechaRegistro);
		//Regresa el beneficiario
		return beneficiario;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.formatosw.FormaGeneral#construyeBO(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
    public Beneficiario construyeBO(Beneficiario beneficiario) {
		//Pasa a mayusculas
    	W8IMY2015Util.formaAMayusculas(this);
		construyeBOBasico(beneficiario);
		//Copia los campos de la forma
		copiarCampos();
		//Nombre del formato
		beneficiario.setTipoFormato(formato);
		//Tipo de beneficiario
		TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
		tipoBeneficiario.setIdTipoBeneficiario(idTipoBeneficiario);
		beneficiario.setTipoBeneficiario(tipoBeneficiario);
		//Indica que es persona moral
		beneficiario.setPersonaFisica(Boolean.FALSE);
		//Coloca la razón social
		beneficiario.setRazonSocial(razonSocial);
		//Cuenta Nombrada, esta dada por el custodio
		beneficiario.setIdCuentaNombrada(custodio);
		//Pais de incorporacion
		beneficiario.setPaisIncorporacion(pais);
		//Fecha del formato
		beneficiario.setFechaFormato(fechaFormato);
		//Fecha de registro
		beneficiario.setFechaRegistro(fechaRegistro);
		//Regresa el beneficiario
		return beneficiario;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.formatosw.FormaGeneral#construyeBean(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario, boolean)
	 */
	@Override
	public void construyeBean(Beneficiario beneficiario, boolean llenaCamposVacios) {
		super.construyeBean(beneficiario, llenaCamposVacios);
		//Domicilios
		if(beneficiario.getDomicilioW8Normal() != null) {			
			this.partIcmp6a = beneficiario.getDomicilioW8Normal().getStreet();
			this.partIcmp6b = beneficiario.getDomicilioW8Normal().getCityTown();
			this.partIcmp6c = beneficiario.getDomicilioW8Normal().getCountry();
		}
		if(beneficiario.getDomicilioW8Correo() != null) {			
			this.partIcmp7a = beneficiario.getDomicilioW8Correo().getStreet();
			this.partIcmp7b = beneficiario.getDomicilioW8Correo().getCityTown();
			this.partIcmp7c = beneficiario.getDomicilioW8Correo().getCountry();
		}
		//País residencial
		paisResidencial = partIcmp2;
		W8IMY2015Util.obtenerTipoBeneficiario(this);
		if(llenaCamposVacios) {
            ponerValoresVacios();
        }
	}
	
	/**
	 * Copia los campos de la forma a los del beneficiario.
	 */
	private void copiarCampos() {
		//Razón social
		razonSocial = partIcmp1;
		//País de incorporación u organización
		pais = partIcmp2;
		//País residencial
		paisResidencial = partIcmp2;
	}
	
	/**
	 * Método que coloca valores por omisión cuando los campos son vacios.
	 */
	private void ponerValoresVacios() {
		partIcmp7a = StringUtils.isNotBlank(partIcmp7a) ? partIcmp7a : Constantes.BEN_CADENA_VACIA;
		partIcmp7b = StringUtils.isNotBlank(partIcmp7b) ? partIcmp7b : Constantes.BEN_CADENA_VACIA;
		partIcmp7c = StringUtils.isNotBlank(partIcmp7c) ? partIcmp7c : Constantes.BEN_CADENA_VACIA;
	}
	
	/**
	 * @return the partIcmp1
	 */
	public String getPartIcmp1() {
		return partIcmp1;
	}
	/**
	 * @param partIcmp1 the partIcmp1 to set
	 */
	public void setPartIcmp1(String partIcmp1) {
		this.partIcmp1 = partIcmp1;
	}
	/**
	 * @return the partIcmp2
	 */
	public String getPartIcmp2() {
		return partIcmp2;
	}
	/**
	 * @param partIcmp2 the partIcmp2 to set
	 */
	public void setPartIcmp2(String partIcmp2) {
		this.partIcmp2 = partIcmp2;
	}
	/**
	 * @return the partIcmp3
	 */
	public String getPartIcmp3() {
		return partIcmp3;
	}
	/**
	 * @param partIcmp3 the partIcmp3 to set
	 */
	public void setPartIcmp3(String partIcmp3) {
		this.partIcmp3 = partIcmp3;
	}
	/**
	 * @return the partIcmp4a
	 */
	public boolean isPartIcmp4a() {
		return partIcmp4a;
	}
	/**
	 * @param partIcmp4a the partIcmp4a to set
	 */
	public void setPartIcmp4a(boolean partIcmp4a) {
		this.partIcmp4a = partIcmp4a;
	}
	/**
	 * @return the partIcmp4b
	 */
	public boolean isPartIcmp4b() {
		return partIcmp4b;
	}
	/**
	 * @param partIcmp4b the partIcmp4b to set
	 */
	public void setPartIcmp4b(boolean partIcmp4b) {
		this.partIcmp4b = partIcmp4b;
	}
	/**
	 * @return the partIcmp4c
	 */
	public boolean isPartIcmp4c() {
		return partIcmp4c;
	}
	/**
	 * @param partIcmp4c the partIcmp4c to set
	 */
	public void setPartIcmp4c(boolean partIcmp4c) {
		this.partIcmp4c = partIcmp4c;
	}
	/**
	 * @return the partIcmp4d
	 */
	public boolean isPartIcmp4d() {
		return partIcmp4d;
	}
	/**
	 * @param partIcmp4d the partIcmp4d to set
	 */
	public void setPartIcmp4d(boolean partIcmp4d) {
		this.partIcmp4d = partIcmp4d;
	}
	/**
	 * @return the partIcmp4e
	 */
	public boolean isPartIcmp4e() {
		return partIcmp4e;
	}
	/**
	 * @param partIcmp4e the partIcmp4e to set
	 */
	public void setPartIcmp4e(boolean partIcmp4e) {
		this.partIcmp4e = partIcmp4e;
	}
	/**
	 * @return the partIcmp4f
	 */
	public boolean isPartIcmp4f() {
		return partIcmp4f;
	}
	/**
	 * @param partIcmp4f the partIcmp4f to set
	 */
	public void setPartIcmp4f(boolean partIcmp4f) {
		this.partIcmp4f = partIcmp4f;
	}
	/**
	 * @return the partIcmp4g
	 */
	public boolean isPartIcmp4g() {
		return partIcmp4g;
	}
	/**
	 * @param partIcmp4g the partIcmp4g to set
	 */
	public void setPartIcmp4g(boolean partIcmp4g) {
		this.partIcmp4g = partIcmp4g;
	}
	/**
	 * @return the partIcmp4h
	 */
	public boolean isPartIcmp4h() {
		return partIcmp4h;
	}
	/**
	 * @param partIcmp4h the partIcmp4h to set
	 */
	public void setPartIcmp4h(boolean partIcmp4h) {
		this.partIcmp4h = partIcmp4h;
	}
	/**
	 * @return the partIcmp4i
	 */
	public boolean isPartIcmp4i() {
		return partIcmp4i;
	}
	/**
	 * @param partIcmp4i the partIcmp4i to set
	 */
	public void setPartIcmp4i(boolean partIcmp4i) {
		this.partIcmp4i = partIcmp4i;
	}
	/**
	 * @return the partIcmp5a
	 */
	public boolean isPartIcmp5a() {
		return partIcmp5a;
	}
	/**
	 * @param partIcmp5a the partIcmp5a to set
	 */
	public void setPartIcmp5a(boolean partIcmp5a) {
		this.partIcmp5a = partIcmp5a;
	}
	/**
	 * @return the partIcmp5b
	 */
	public boolean isPartIcmp5b() {
		return partIcmp5b;
	}
	/**
	 * @param partIcmp5b the partIcmp5b to set
	 */
	public void setPartIcmp5b(boolean partIcmp5b) {
		this.partIcmp5b = partIcmp5b;
	}
	/**
	 * @return the partIcmp5c
	 */
	public boolean isPartIcmp5c() {
		return partIcmp5c;
	}
	/**
	 * @param partIcmp5c the partIcmp5c to set
	 */
	public void setPartIcmp5c(boolean partIcmp5c) {
		this.partIcmp5c = partIcmp5c;
	}
	/**
	 * @return the partIcmp5d
	 */
	public boolean isPartIcmp5d() {
		return partIcmp5d;
	}
	/**
	 * @param partIcmp5d the partIcmp5d to set
	 */
	public void setPartIcmp5d(boolean partIcmp5d) {
		this.partIcmp5d = partIcmp5d;
	}
	/**
	 * @return the partIcmp5e
	 */
	public boolean isPartIcmp5e() {
		return partIcmp5e;
	}
	/**
	 * @param partIcmp5e the partIcmp5e to set
	 */
	public void setPartIcmp5e(boolean partIcmp5e) {
		this.partIcmp5e = partIcmp5e;
	}
	/**
	 * @return the partIcmp5f
	 */
	public boolean isPartIcmp5f() {
		return partIcmp5f;
	}
	/**
	 * @param partIcmp5f the partIcmp5f to set
	 */
	public void setPartIcmp5f(boolean partIcmp5f) {
		this.partIcmp5f = partIcmp5f;
	}
	/**
	 * @return the partIcmp5g
	 */
	public boolean isPartIcmp5g() {
		return partIcmp5g;
	}
	/**
	 * @param partIcmp5g the partIcmp5g to set
	 */
	public void setPartIcmp5g(boolean partIcmp5g) {
		this.partIcmp5g = partIcmp5g;
	}
	/**
	 * @return the partIcmp5h
	 */
	public boolean isPartIcmp5h() {
		return partIcmp5h;
	}
	/**
	 * @param partIcmp5h the partIcmp5h to set
	 */
	public void setPartIcmp5h(boolean partIcmp5h) {
		this.partIcmp5h = partIcmp5h;
	}
	/**
	 * @return the partIcmp5i
	 */
	public boolean isPartIcmp5i() {
		return partIcmp5i;
	}
	/**
	 * @param partIcmp5i the partIcmp5i to set
	 */
	public void setPartIcmp5i(boolean partIcmp5i) {
		this.partIcmp5i = partIcmp5i;
	}
	/**
	 * @return the partIcmp5j
	 */
	public boolean isPartIcmp5j() {
		return partIcmp5j;
	}
	/**
	 * @param partIcmp5j the partIcmp5j to set
	 */
	public void setPartIcmp5j(boolean partIcmp5j) {
		this.partIcmp5j = partIcmp5j;
	}
	/**
	 * @return the partIcmp5k
	 */
	public boolean isPartIcmp5k() {
		return partIcmp5k;
	}
	/**
	 * @param partIcmp5k the partIcmp5k to set
	 */
	public void setPartIcmp5k(boolean partIcmp5k) {
		this.partIcmp5k = partIcmp5k;
	}
	/**
	 * @return the partIcmp5l
	 */
	public boolean isPartIcmp5l() {
		return partIcmp5l;
	}
	/**
	 * @param partIcmp5l the partIcmp5l to set
	 */
	public void setPartIcmp5l(boolean partIcmp5l) {
		this.partIcmp5l = partIcmp5l;
	}
	/**
	 * @return the partIcmp5m
	 */
	public boolean isPartIcmp5m() {
		return partIcmp5m;
	}
	/**
	 * @param partIcmp5m the partIcmp5m to set
	 */
	public void setPartIcmp5m(boolean partIcmp5m) {
		this.partIcmp5m = partIcmp5m;
	}
	/**
	 * @return the partIcmp5n
	 */
	public boolean isPartIcmp5n() {
		return partIcmp5n;
	}
	/**
	 * @param partIcmp5n the partIcmp5n to set
	 */
	public void setPartIcmp5n(boolean partIcmp5n) {
		this.partIcmp5n = partIcmp5n;
	}
	/**
	 * @return the partIcmp5o
	 */
	public boolean isPartIcmp5o() {
		return partIcmp5o;
	}
	/**
	 * @param partIcmp5o the partIcmp5o to set
	 */
	public void setPartIcmp5o(boolean partIcmp5o) {
		this.partIcmp5o = partIcmp5o;
	}
	/**
	 * @return the partIcmp5p
	 */
	public boolean isPartIcmp5p() {
		return partIcmp5p;
	}
	/**
	 * @param partIcmp5p the partIcmp5p to set
	 */
	public void setPartIcmp5p(boolean partIcmp5p) {
		this.partIcmp5p = partIcmp5p;
	}
	/**
	 * @return the partIcmp5q
	 */
	public boolean isPartIcmp5q() {
		return partIcmp5q;
	}
	/**
	 * @param partIcmp5q the partIcmp5q to set
	 */
	public void setPartIcmp5q(boolean partIcmp5q) {
		this.partIcmp5q = partIcmp5q;
	}
	/**
	 * @return the partIcmp5r
	 */
	public boolean isPartIcmp5r() {
		return partIcmp5r;
	}
	/**
	 * @param partIcmp5r the partIcmp5r to set
	 */
	public void setPartIcmp5r(boolean partIcmp5r) {
		this.partIcmp5r = partIcmp5r;
	}
	/**
	 * @return the partIcmp5s
	 */
	public boolean isPartIcmp5s() {
		return partIcmp5s;
	}
	/**
	 * @param partIcmp5s the partIcmp5s to set
	 */
	public void setPartIcmp5s(boolean partIcmp5s) {
		this.partIcmp5s = partIcmp5s;
	}
	/**
	 * @return the partIcmp5t
	 */
	public boolean isPartIcmp5t() {
		return partIcmp5t;
	}
	/**
	 * @param partIcmp5t the partIcmp5t to set
	 */
	public void setPartIcmp5t(boolean partIcmp5t) {
		this.partIcmp5t = partIcmp5t;
	}
	/**
	 * @return the partIcmp5u
	 */
	public boolean isPartIcmp5u() {
		return partIcmp5u;
	}
	/**
	 * @param partIcmp5u the partIcmp5u to set
	 */
	public void setPartIcmp5u(boolean partIcmp5u) {
		this.partIcmp5u = partIcmp5u;
	}
	/**
	 * @return the partIcmp5v
	 */
	public boolean isPartIcmp5v() {
		return partIcmp5v;
	}
	/**
	 * @param partIcmp5v the partIcmp5v to set
	 */
	public void setPartIcmp5v(boolean partIcmp5v) {
		this.partIcmp5v = partIcmp5v;
	}
	/**
	 * @return the partIcmp5w
	 */
	public boolean isPartIcmp5w() {
		return partIcmp5w;
	}
	/**
	 * @param partIcmp5w the partIcmp5w to set
	 */
	public void setPartIcmp5w(boolean partIcmp5w) {
		this.partIcmp5w = partIcmp5w;
	}
	/**
	 * @return the partIcmp5x
	 */
	public boolean isPartIcmp5x() {
		return partIcmp5x;
	}
	/**
	 * @param partIcmp5x the partIcmp5x to set
	 */
	public void setPartIcmp5x(boolean partIcmp5x) {
		this.partIcmp5x = partIcmp5x;
	}
	/**
	 * @return the partIcmp5y
	 */
	public boolean isPartIcmp5y() {
		return partIcmp5y;
	}
	/**
	 * @param partIcmp5y the partIcmp5y to set
	 */
	public void setPartIcmp5y(boolean partIcmp5y) {
		this.partIcmp5y = partIcmp5y;
	}
	/**
	 * @return the partIcmp6a
	 */
	public String getPartIcmp6a() {
		return partIcmp6a;
	}
	/**
	 * @param partIcmp6a the partIcmp6a to set
	 */
	public void setPartIcmp6a(String partIcmp6a) {
		this.partIcmp6a = partIcmp6a;
	}
	/**
	 * @return the partIcmp6b
	 */
	public String getPartIcmp6b() {
		return partIcmp6b;
	}
	/**
	 * @param partIcmp6b the partIcmp6b to set
	 */
	public void setPartIcmp6b(String partIcmp6b) {
		this.partIcmp6b = partIcmp6b;
	}
	/**
	 * @return the partIcmp6c
	 */
	public String getPartIcmp6c() {
		return partIcmp6c;
	}
	/**
	 * @param partIcmp6c the partIcmp6c to set
	 */
	public void setPartIcmp6c(String partIcmp6c) {
		this.partIcmp6c = partIcmp6c;
	}
	/**
	 * @return the partIcmp7a
	 */
	public String getPartIcmp7a() {
		return partIcmp7a;
	}
	/**
	 * @param partIcmp7a the partIcmp7a to set
	 */
	public void setPartIcmp7a(String partIcmp7a) {
		this.partIcmp7a = partIcmp7a;
	}
	/**
	 * @return the partIcmp7b
	 */
	public String getPartIcmp7b() {
		return partIcmp7b;
	}
	/**
	 * @param partIcmp7b the partIcmp7b to set
	 */
	public void setPartIcmp7b(String partIcmp7b) {
		this.partIcmp7b = partIcmp7b;
	}
	/**
	 * @return the partIcmp7c
	 */
	public String getPartIcmp7c() {
		return partIcmp7c;
	}
	/**
	 * @param partIcmp7c the partIcmp7c to set
	 */
	public void setPartIcmp7c(String partIcmp7c) {
		this.partIcmp7c = partIcmp7c;
	}
	/**
	 * @return the partIcmp8
	 */
	public String getPartIcmp8() {
		return partIcmp8;
	}
	/**
	 * @param partIcmp8 the partIcmp8 to set
	 */
	public void setPartIcmp8(String partIcmp8) {
		this.partIcmp8 = partIcmp8;
	}
	/**
	 * @return the partIcmp8a
	 */
	public boolean isPartIcmp8a() {
		return partIcmp8a;
	}
	/**
	 * @param partIcmp8a the partIcmp8a to set
	 */
	public void setPartIcmp8a(boolean partIcmp8a) {
		this.partIcmp8a = partIcmp8a;
	}
	/**
	 * @return the partIcmp8b
	 */
	public boolean isPartIcmp8b() {
		return partIcmp8b;
	}
	/**
	 * @param partIcmp8b the partIcmp8b to set
	 */
	public void setPartIcmp8b(boolean partIcmp8b) {
		this.partIcmp8b = partIcmp8b;
	}
	/**
	 * @return the partIcmp8c
	 */
	public boolean isPartIcmp8c() {
		return partIcmp8c;
	}
	/**
	 * @param partIcmp8c the partIcmp8c to set
	 */
	public void setPartIcmp8c(boolean partIcmp8c) {
		this.partIcmp8c = partIcmp8c;
	}
	/**
	 * @return the partIcmp8d
	 */
	public boolean isPartIcmp8d() {
		return partIcmp8d;
	}
	/**
	 * @param partIcmp8d the partIcmp8d to set
	 */
	public void setPartIcmp8d(boolean partIcmp8d) {
		this.partIcmp8d = partIcmp8d;
	}
	/**
	 * @return the partIcmp8e
	 */
	public boolean isPartIcmp8e() {
		return partIcmp8e;
	}
	/**
	 * @param partIcmp8e the partIcmp8e to set
	 */
	public void setPartIcmp8e(boolean partIcmp8e) {
		this.partIcmp8e = partIcmp8e;
	}
	/**
	 * @return the partIcmp9
	 */
	public String getPartIcmp9() {
		return partIcmp9;
	}
	/**
	 * @param partIcmp9 the partIcmp9 to set
	 */
	public void setPartIcmp9(String partIcmp9) {
		this.partIcmp9 = partIcmp9;
	}
	/**
	 * @return the partIcmp10
	 */
	public String getPartIcmp10() {
		return partIcmp10;
	}
	/**
	 * @param partIcmp10 the partIcmp10 to set
	 */
	public void setPartIcmp10(String partIcmp10) {
		this.partIcmp10 = partIcmp10;
	}
	/**
	 * @return the partIIcmp11a
	 */
	public boolean isPartIIcmp11a() {
		return partIIcmp11a;
	}
	/**
	 * @param partIIcmp11a the partIIcmp11a to set
	 */
	public void setPartIIcmp11a(boolean partIIcmp11a) {
		this.partIIcmp11a = partIIcmp11a;
	}
	/**
	 * @return the partIIcmp11b
	 */
	public boolean isPartIIcmp11b() {
		return partIIcmp11b;
	}
	/**
	 * @param partIIcmp11b the partIIcmp11b to set
	 */
	public void setPartIIcmp11b(boolean partIIcmp11b) {
		this.partIIcmp11b = partIIcmp11b;
	}
	/**
	 * @return the partIIcmp11c
	 */
	public boolean isPartIIcmp11c() {
		return partIIcmp11c;
	}
	/**
	 * @param partIIcmp11c the partIIcmp11c to set
	 */
	public void setPartIIcmp11c(boolean partIIcmp11c) {
		this.partIIcmp11c = partIIcmp11c;
	}
	/**
	 * @return the partIIcmp11d
	 */
	public boolean isPartIIcmp11d() {
		return partIIcmp11d;
	}
	/**
	 * @param partIIcmp11d the partIIcmp11d to set
	 */
	public void setPartIIcmp11d(boolean partIIcmp11d) {
		this.partIIcmp11d = partIIcmp11d;
	}
	/**
	 * @return the partIIcmp11e
	 */
	public boolean isPartIIcmp11e() {
		return partIIcmp11e;
	}
	/**
	 * @param partIIcmp11e the partIIcmp11e to set
	 */
	public void setPartIIcmp11e(boolean partIIcmp11e) {
		this.partIIcmp11e = partIIcmp11e;
	}
	/**
	 * @return the partIIcmp12a
	 */
	public String getPartIIcmp12a() {
		return partIIcmp12a;
	}
	/**
	 * @param partIIcmp12a the partIIcmp12a to set
	 */
	public void setPartIIcmp12a(String partIIcmp12a) {
		this.partIIcmp12a = partIIcmp12a;
	}
	/**
	 * @return the partIIcmp12b
	 */
	public String getPartIIcmp12b() {
		return partIIcmp12b;
	}
	/**
	 * @param partIIcmp12b the partIIcmp12b to set
	 */
	public void setPartIIcmp12b(String partIIcmp12b) {
		this.partIIcmp12b = partIIcmp12b;
	}
	/**
	 * @return the partIIcmp12c
	 */
	public String getPartIIcmp12c() {
		return partIIcmp12c;
	}
	/**
	 * @param partIIcmp12c the partIIcmp12c to set
	 */
	public void setPartIIcmp12c(String partIIcmp12c) {
		this.partIIcmp12c = partIIcmp12c;
	}
	/**
	 * @return the partIIcmp13
	 */
	public String getPartIIcmp13() {
		return partIIcmp13;
	}
	/**
	 * @param partIIcmp13 the partIIcmp13 to set
	 */
	public void setPartIIcmp13(String partIIcmp13) {
		this.partIIcmp13 = partIIcmp13;
	}
	/**
	 * @return the partIII
	 */
	public boolean isPartIII() {
		return partIII;
	}
	/**
	 * @param partIII the partIII to set
	 */
	public void setPartIII(boolean partIII) {
		this.partIII = partIII;
	}
	/**
	 * @return the partIIIcmp14a
	 */
	public boolean isPartIIIcmp14a() {
		return partIIIcmp14a;
	}
	/**
	 * @param partIIIcmp14a the partIIIcmp14a to set
	 */
	public void setPartIIIcmp14a(boolean partIIIcmp14a) {
		this.partIIIcmp14a = partIIIcmp14a;
	}
	/**
	 * @return the partIIIcmp14b
	 */
	public boolean isPartIIIcmp14b() {
		return partIIIcmp14b;
	}
	/**
	 * @param partIIIcmp14b the partIIIcmp14b to set
	 */
	public void setPartIIIcmp14b(boolean partIIIcmp14b) {
		this.partIIIcmp14b = partIIIcmp14b;
	}
	/**
	 * @return the partIIIcmp14c
	 */
	public boolean isPartIIIcmp14c() {
		return partIIIcmp14c;
	}
	/**
	 * @param partIIIcmp14c the partIIIcmp14c to set
	 */
	public void setPartIIIcmp14c(boolean partIIIcmp14c) {
		this.partIIIcmp14c = partIIIcmp14c;
	}
	/**
	 * @return the partIIIcmp14c1
	 */
	public String getPartIIIcmp14c1() {
		return partIIIcmp14c1;
	}
	/**
	 * @param partIIIcmp14c1 the partIIIcmp14c1 to set
	 */
	public void setPartIIIcmp14c1(String partIIIcmp14c1) {
		this.partIIIcmp14c1 = partIIIcmp14c1;
	}
	/**
	 * @return the partIIIcmp14d
	 */
	public boolean isPartIIIcmp14d() {
		return partIIIcmp14d;
	}
	/**
	 * @param partIIIcmp14d the partIIIcmp14d to set
	 */
	public void setPartIIIcmp14d(boolean partIIIcmp14d) {
		this.partIIIcmp14d = partIIIcmp14d;
	}
	/**
	 * @return the partIIIcmp14d1
	 */
	public String getPartIIIcmp14d1() {
		return partIIIcmp14d1;
	}
	/**
	 * @param partIIIcmp14d1 the partIIIcmp14d1 to set
	 */
	public void setPartIIIcmp14d1(String partIIIcmp14d1) {
		this.partIIIcmp14d1 = partIIIcmp14d1;
	}
	/**
	 * @return the partIIIcmp14e
	 */
	public boolean isPartIIIcmp14e() {
		return partIIIcmp14e;
	}
	/**
	 * @param partIIIcmp14e the partIIIcmp14e to set
	 */
	public void setPartIIIcmp14e(boolean partIIIcmp14e) {
		this.partIIIcmp14e = partIIIcmp14e;
	}
	/**
	 * @return the partIIIcmp14e1
	 */
	public String getPartIIIcmp14e1() {
		return partIIIcmp14e1;
	}
	/**
	 * @param partIIIcmp14e1 the partIIIcmp14e1 to set
	 */
	public void setPartIIIcmp14e1(String partIIIcmp14e1) {
		this.partIIIcmp14e1 = partIIIcmp14e1;
	}
	/**
	 * @return the partIIIcmp14ei
	 */
	public boolean isPartIIIcmp14ei() {
		return partIIIcmp14ei;
	}
	/**
	 * @param partIIIcmp14ei the partIIIcmp14ei to set
	 */
	public void setPartIIIcmp14ei(boolean partIIIcmp14ei) {
		this.partIIIcmp14ei = partIIIcmp14ei;
	}
	/**
	 * @return the partIIIcmp14eii
	 */
	public boolean isPartIIIcmp14eii() {
		return partIIIcmp14eii;
	}
	/**
	 * @param partIIIcmp14eii the partIIIcmp14eii to set
	 */
	public void setPartIIIcmp14eii(boolean partIIIcmp14eii) {
		this.partIIIcmp14eii = partIIIcmp14eii;
	}
	/**
	 * @return the partIIIcmp14f
	 */
	public boolean isPartIIIcmp14f() {
		return partIIIcmp14f;
	}
	/**
	 * @param partIIIcmp14f the partIIIcmp14f to set
	 */
	public void setPartIIIcmp14f(boolean partIIIcmp14f) {
		this.partIIIcmp14f = partIIIcmp14f;
	}
	/**
	 * @return the partIV
	 */
	public boolean isPartIV() {
		return partIV;
	}
	/**
	 * @param partIV the partIV to set
	 */
	public void setPartIV(boolean partIV) {
		this.partIV = partIV;
	}
	/**
	 * @return the partIVcmp15a
	 */
	public boolean isPartIVcmp15a() {
		return partIVcmp15a;
	}
	/**
	 * @param partIVcmp15a the partIVcmp15a to set
	 */
	public void setPartIVcmp15a(boolean partIVcmp15a) {
		this.partIVcmp15a = partIVcmp15a;
	}
	/**
	 * @return the partIVcmp15b
	 */
	public boolean isPartIVcmp15b() {
		return partIVcmp15b;
	}
	/**
	 * @param partIVcmp15b the partIVcmp15b to set
	 */
	public void setPartIVcmp15b(boolean partIVcmp15b) {
		this.partIVcmp15b = partIVcmp15b;
	}
	/**
	 * @return the partIVcmp15c
	 */
	public boolean isPartIVcmp15c() {
		return partIVcmp15c;
	}
	/**
	 * @param partIVcmp15c the partIVcmp15c to set
	 */
	public void setPartIVcmp15c(boolean partIVcmp15c) {
		this.partIVcmp15c = partIVcmp15c;
	}
	/**
	 * @return the partIVcmp15d
	 */
	public boolean isPartIVcmp15d() {
		return partIVcmp15d;
	}
	/**
	 * @param partIVcmp15d the partIVcmp15d to set
	 */
	public void setPartIVcmp15d(boolean partIVcmp15d) {
		this.partIVcmp15d = partIVcmp15d;
	}
	/**
	 * @return the partV
	 */
	public boolean isPartV() {
		return partV;
	}
	/**
	 * @param partV the partV to set
	 */
	public void setPartV(boolean partV) {
		this.partV = partV;
	}
	/**
	 * @return the partVcmp16a
	 */
	public boolean isPartVcmp16a() {
		return partVcmp16a;
	}
	/**
	 * @param partVcmp16a the partVcmp16a to set
	 */
	public void setPartVcmp16a(boolean partVcmp16a) {
		this.partVcmp16a = partVcmp16a;
	}
	/**
	 * @return the partVcmp16b
	 */
	public boolean isPartVcmp16b() {
		return partVcmp16b;
	}
	/**
	 * @param partVcmp16b the partVcmp16b to set
	 */
	public void setPartVcmp16b(boolean partVcmp16b) {
		this.partVcmp16b = partVcmp16b;
	}
	/**
	 * @return the partVcmp16c
	 */
	public boolean isPartVcmp16c() {
		return partVcmp16c;
	}
	/**
	 * @param partVcmp16c the partVcmp16c to set
	 */
	public void setPartVcmp16c(boolean partVcmp16c) {
		this.partVcmp16c = partVcmp16c;
	}
	/**
	 * @return the partVI
	 */
	public boolean isPartVI() {
		return partVI;
	}
	/**
	 * @param partVI the partVI to set
	 */
	public void setPartVI(boolean partVI) {
		this.partVI = partVI;
	}
	/**
	 * @return the partVIcmp17a
	 */
	public boolean isPartVIcmp17a() {
		return partVIcmp17a;
	}
	/**
	 * @param partVIcmp17a the partVIcmp17a to set
	 */
	public void setPartVIcmp17a(boolean partVIcmp17a) {
		this.partVIcmp17a = partVIcmp17a;
	}
	/**
	 * @return the partVIcmp17b
	 */
	public boolean isPartVIcmp17b() {
		return partVIcmp17b;
	}
	/**
	 * @param partVIcmp17b the partVIcmp17b to set
	 */
	public void setPartVIcmp17b(boolean partVIcmp17b) {
		this.partVIcmp17b = partVIcmp17b;
	}
	/**
	 * @return the partVIcmp17c
	 */
	public boolean isPartVIcmp17c() {
		return partVIcmp17c;
	}
	/**
	 * @param partVIcmp17c the partVIcmp17c to set
	 */
	public void setPartVIcmp17c(boolean partVIcmp17c) {
		this.partVIcmp17c = partVIcmp17c;
	}
	/**
	 * @return the partVII
	 */
	public boolean isPartVII() {
		return partVII;
	}
	/**
	 * @param partVII the partVII to set
	 */
	public void setPartVII(boolean partVII) {
		this.partVII = partVII;
	}
	/**
	 * @return the partVIIcmp18
	 */
	public boolean isPartVIIcmp18() {
		return partVIIcmp18;
	}
	/**
	 * @param partVIIcmp18 the partVIIcmp18 to set
	 */
	public void setPartVIIcmp18(boolean partVIIcmp18) {
		this.partVIIcmp18 = partVIIcmp18;
	}
	/**
	 * @return the partVIII
	 */
	public boolean isPartVIII() {
		return partVIII;
	}
	/**
	 * @param partVIII the partVIII to set
	 */
	public void setPartVIII(boolean partVIII) {
		this.partVIII = partVIII;
	}
	/**
	 * @return the partVIIIcmp19
	 */
	public boolean isPartVIIIcmp19() {
		return partVIIIcmp19;
	}
	/**
	 * @param partVIIIcmp19 the partVIIIcmp19 to set
	 */
	public void setPartVIIIcmp19(boolean partVIIIcmp19) {
		this.partVIIIcmp19 = partVIIIcmp19;
	}
	/**
	 * @return the partIX
	 */
	public boolean isPartIX() {
		return partIX;
	}
	/**
	 * @param partIX the partIX to set
	 */
	public void setPartIX(boolean partIX) {
		this.partIX = partIX;
	}
	/**
	 * @return the partIXcmp20
	 */
	public boolean isPartIXcmp20() {
		return partIXcmp20;
	}
	/**
	 * @param partIXcmp20 the partIXcmp20 to set
	 */
	public void setPartIXcmp20(boolean partIXcmp20) {
		this.partIXcmp20 = partIXcmp20;
	}
	/**
	 * @return the partX
	 */
	public boolean isPartX() {
		return partX;
	}
	/**
	 * @param partX the partX to set
	 */
	public void setPartX(boolean partX) {
		this.partX = partX;
	}
	/**
	 * @return the partXcmp21a
	 */
	public String getPartXcmp21a() {
		return partXcmp21a;
	}
	/**
	 * @param partXcmp21a the partXcmp21a to set
	 */
	public void setPartXcmp21a(String partXcmp21a) {
		this.partXcmp21a = partXcmp21a;
	}
	/**
	 * @return the partXcmp21b
	 */
	public boolean isPartXcmp21b() {
		return partXcmp21b;
	}
	/**
	 * @param partXcmp21b the partXcmp21b to set
	 */
	public void setPartXcmp21b(boolean partXcmp21b) {
		this.partXcmp21b = partXcmp21b;
	}
	/**
	 * @return the partXcmp21c
	 */
	public boolean isPartXcmp21c() {
		return partXcmp21c;
	}
	/**
	 * @param partXcmp21c the partXcmp21c to set
	 */
	public void setPartXcmp21c(boolean partXcmp21c) {
		this.partXcmp21c = partXcmp21c;
	}
	/**
	 * @return the partXI
	 */
	public boolean isPartXI() {
		return partXI;
	}
	/**
	 * @param partXI the partXI to set
	 */
	public void setPartXI(boolean partXI) {
		this.partXI = partXI;
	}
	/**
	 * @return the partXIcmp22a
	 */
	public boolean isPartXIcmp22a() {
		return partXIcmp22a;
	}
	/**
	 * @param partXIcmp22a the partXIcmp22a to set
	 */
	public void setPartXIcmp22a(boolean partXIcmp22a) {
		this.partXIcmp22a = partXIcmp22a;
	}
	/**
	 * @return the partXIcmp22b
	 */
	public boolean isPartXIcmp22b() {
		return partXIcmp22b;
	}
	/**
	 * @param partXIcmp22b the partXIcmp22b to set
	 */
	public void setPartXIcmp22b(boolean partXIcmp22b) {
		this.partXIcmp22b = partXIcmp22b;
	}
	/**
	 * @return the partXIcmp22c
	 */
	public boolean isPartXIcmp22c() {
		return partXIcmp22c;
	}
	/**
	 * @param partXIcmp22c the partXIcmp22c to set
	 */
	public void setPartXIcmp22c(boolean partXIcmp22c) {
		this.partXIcmp22c = partXIcmp22c;
	}
	/**
	 * @return the partXII
	 */
	public boolean isPartXII() {
		return partXII;
	}
	/**
	 * @param partXII the partXII to set
	 */
	public void setPartXII(boolean partXII) {
		this.partXII = partXII;
	}
	/**
	 * @return the partXIIcmp23
	 */
	public boolean isPartXIIcmp23() {
		return partXIIcmp23;
	}
	/**
	 * @param partXIIcmp23 the partXIIcmp23 to set
	 */
	public void setPartXIIcmp23(boolean partXIIcmp23) {
		this.partXIIcmp23 = partXIIcmp23;
	}
	/**
	 * @return the partXIII
	 */
	public boolean isPartXIII() {
		return partXIII;
	}
	/**
	 * @param partXIII the partXIII to set
	 */
	public void setPartXIII(boolean partXIII) {
		this.partXIII = partXIII;
	}
	/**
	 * @return the partXIIIcmp24
	 */
	public boolean isPartXIIIcmp24() {
		return partXIIIcmp24;
	}
	/**
	 * @param partXIIIcmp24 the partXIIIcmp24 to set
	 */
	public void setPartXIIIcmp24(boolean partXIIIcmp24) {
		this.partXIIIcmp24 = partXIIIcmp24;
	}
	/**
	 * @return the partXIV
	 */
	public boolean isPartXIV() {
		return partXIV;
	}
	/**
	 * @param partXIV the partXIV to set
	 */
	public void setPartXIV(boolean partXIV) {
		this.partXIV = partXIV;
	}
	/**
	 * @return the partXIVcmp25a
	 */
	public String getPartXIVcmp25a() {
		return partXIVcmp25a;
	}
	/**
	 * @param partXIVcmp25a the partXIVcmp25a to set
	 */
	public void setPartXIVcmp25a(String partXIVcmp25a) {
		this.partXIVcmp25a = partXIVcmp25a;
	}
	/**
	 * @return the partXIVcmp25b
	 */
	public boolean isPartXIVcmp25b() {
		return partXIVcmp25b;
	}
	/**
	 * @param partXIVcmp25b the partXIVcmp25b to set
	 */
	public void setPartXIVcmp25b(boolean partXIVcmp25b) {
		this.partXIVcmp25b = partXIVcmp25b;
	}
	/**
	 * @return the partXV
	 */
	public boolean isPartXV() {
		return partXV;
	}
	/**
	 * @param partXV the partXV to set
	 */
	public void setPartXV(boolean partXV) {
		this.partXV = partXV;
	}
	/**
	 * @return the partXVcmp26
	 */
	public boolean isPartXVcmp26() {
		return partXVcmp26;
	}
	/**
	 * @param partXVcmp26 the partXVcmp26 to set
	 */
	public void setPartXVcmp26(boolean partXVcmp26) {
		this.partXVcmp26 = partXVcmp26;
	}
	/**
	 * @return the partXVI
	 */
	public boolean isPartXVI() {
		return partXVI;
	}
	/**
	 * @param partXVI the partXVI to set
	 */
	public void setPartXVI(boolean partXVI) {
		this.partXVI = partXVI;
	}
	/**
	 * @return the partXVIcmp27a
	 */
	public boolean isPartXVIcmp27a() {
		return partXVIcmp27a;
	}
	/**
	 * @param partXVIcmp27a the partXVIcmp27a to set
	 */
	public void setPartXVIcmp27a(boolean partXVIcmp27a) {
		this.partXVIcmp27a = partXVIcmp27a;
	}
	/**
	 * @return the partXVIcmp27b
	 */
	public boolean isPartXVIcmp27b() {
		return partXVIcmp27b;
	}
	/**
	 * @param partXVIcmp27b the partXVIcmp27b to set
	 */
	public void setPartXVIcmp27b(boolean partXVIcmp27b) {
		this.partXVIcmp27b = partXVIcmp27b;
	}
	/**
	 * @return the partXVIcmp27c
	 */
	public boolean isPartXVIcmp27c() {
		return partXVIcmp27c;
	}
	/**
	 * @param partXVIcmp27c the partXVIcmp27c to set
	 */
	public void setPartXVIcmp27c(boolean partXVIcmp27c) {
		this.partXVIcmp27c = partXVIcmp27c;
	}
	/**
	 * @return the partXVII
	 */
	public boolean isPartXVII() {
		return partXVII;
	}
	/**
	 * @param partXVII the partXVII to set
	 */
	public void setPartXVII(boolean partXVII) {
		this.partXVII = partXVII;
	}
	/**
	 * @return the partXVIIcmp28
	 */
	public boolean isPartXVIIcmp28() {
		return partXVIIcmp28;
	}
	/**
	 * @param partXVIIcmp28 the partXVIIcmp28 to set
	 */
	public void setPartXVIIcmp28(boolean partXVIIcmp28) {
		this.partXVIIcmp28 = partXVIIcmp28;
	}
	/**
	 * @return the partXVIII
	 */
	public boolean isPartXVIII() {
		return partXVIII;
	}
	/**
	 * @param partXVIII the partXVIII to set
	 */
	public void setPartXVIII(boolean partXVIII) {
		this.partXVIII = partXVIII;
	}
	/**
	 * @return the partXVIIIcmp29a
	 */
	public boolean isPartXVIIIcmp29a() {
		return partXVIIIcmp29a;
	}
	/**
	 * @param partXVIIIcmp29a the partXVIIIcmp29a to set
	 */
	public void setPartXVIIIcmp29a(boolean partXVIIIcmp29a) {
		this.partXVIIIcmp29a = partXVIIIcmp29a;
	}
	/**
	 * @return the partXVIIIcmp29b
	 */
	public String getPartXVIIIcmp29b() {
		return partXVIIIcmp29b;
	}
	/**
	 * @param partXVIIIcmp29b the partXVIIIcmp29b to set
	 */
	public void setPartXVIIIcmp29b(String partXVIIIcmp29b) {
		this.partXVIIIcmp29b = partXVIIIcmp29b;
	}
	/**
	 * @return the partXVIIIcmp29c
	 */
	public String getPartXVIIIcmp29c() {
		return partXVIIIcmp29c;
	}
	/**
	 * @param partXVIIIcmp29c the partXVIIIcmp29c to set
	 */
	public void setPartXVIIIcmp29c(String partXVIIIcmp29c) {
		this.partXVIIIcmp29c = partXVIIIcmp29c;
	}
	/**
	 * @return the partXVIIIcmp29d
	 */
	public String getPartXVIIIcmp29d() {
		return partXVIIIcmp29d;
	}
	/**
	 * @param partXVIIIcmp29d the partXVIIIcmp29d to set
	 */
	public void setPartXVIIIcmp29d(String partXVIIIcmp29d) {
		this.partXVIIIcmp29d = partXVIIIcmp29d;
	}
	/**
	 * @return the partXIX
	 */
	public boolean isPartXIX() {
		return partXIX;
	}
	/**
	 * @param partXIX the partXIX to set
	 */
	public void setPartXIX(boolean partXIX) {
		this.partXIX = partXIX;
	}
	/**
	 * @return the partXIXcmp30a
	 */
	public boolean isPartXIXcmp30a() {
		return partXIXcmp30a;
	}
	/**
	 * @param partXIXcmp30a the partXIXcmp30a to set
	 */
	public void setPartXIXcmp30a(boolean partXIXcmp30a) {
		this.partXIXcmp30a = partXIXcmp30a;
	}
	/**
	 * @return the partXIXcmp30b
	 */
	public boolean isPartXIXcmp30b() {
		return partXIXcmp30b;
	}
	/**
	 * @param partXIXcmp30b the partXIXcmp30b to set
	 */
	public void setPartXIXcmp30b(boolean partXIXcmp30b) {
		this.partXIXcmp30b = partXIXcmp30b;
	}
	/**
	 * @return the partXIXcmp30c
	 */
	public boolean isPartXIXcmp30c() {
		return partXIXcmp30c;
	}
	/**
	 * @param partXIXcmp30c the partXIXcmp30c to set
	 */
	public void setPartXIXcmp30c(boolean partXIXcmp30c) {
		this.partXIXcmp30c = partXIXcmp30c;
	}
	/**
	 * @return the partXIXcmp30d
	 */
	public boolean isPartXIXcmp30d() {
		return partXIXcmp30d;
	}
	/**
	 * @param partXIXcmp30d the partXIXcmp30d to set
	 */
	public void setPartXIXcmp30d(boolean partXIXcmp30d) {
		this.partXIXcmp30d = partXIXcmp30d;
	}
	/**
	 * @return the partXIXcmp30e
	 */
	public boolean isPartXIXcmp30e() {
		return partXIXcmp30e;
	}
	/**
	 * @param partXIXcmp30e the partXIXcmp30e to set
	 */
	public void setPartXIXcmp30e(boolean partXIXcmp30e) {
		this.partXIXcmp30e = partXIXcmp30e;
	}
	/**
	 * @return the partXIXcmp30f
	 */
	public boolean isPartXIXcmp30f() {
		return partXIXcmp30f;
	}
	/**
	 * @param partXIXcmp30f the partXIXcmp30f to set
	 */
	public void setPartXIXcmp30f(boolean partXIXcmp30f) {
		this.partXIXcmp30f = partXIXcmp30f;
	}
	/**
	 * @return the partXX
	 */
	public boolean isPartXX() {
		return partXX;
	}
	/**
	 * @param partXX the partXX to set
	 */
	public void setPartXX(boolean partXX) {
		this.partXX = partXX;
	}
	/**
	 * @return the partXXcmp31
	 */
	public boolean isPartXXcmp31() {
		return partXXcmp31;
	}
	/**
	 * @param partXXcmp31 the partXXcmp31 to set
	 */
	public void setPartXXcmp31(boolean partXXcmp31) {
		this.partXXcmp31 = partXXcmp31;
	}
	/**
	 * @return the partXXI
	 */
	public boolean isPartXXI() {
		return partXXI;
	}
	/**
	 * @param partXXI the partXXI to set
	 */
	public void setPartXXI(boolean partXXI) {
		this.partXXI = partXXI;
	}
	/**
	 * @return the partXXIcmp32a
	 */
	public boolean isPartXXIcmp32a() {
		return partXXIcmp32a;
	}
	/**
	 * @param partXXIcmp32a the partXXIcmp32a to set
	 */
	public void setPartXXIcmp32a(boolean partXXIcmp32a) {
		this.partXXIcmp32a = partXXIcmp32a;
	}
	/**
	 * @return the partXXIcmp32b
	 */
	public String getPartXXIcmp32b() {
		return partXXIcmp32b;
	}
	/**
	 * @param partXXIcmp32b the partXXIcmp32b to set
	 */
	public void setPartXXIcmp32b(String partXXIcmp32b) {
		this.partXXIcmp32b = partXXIcmp32b;
	}
	/**
	 * @return the partXXII
	 */
	public boolean isPartXXII() {
		return partXXII;
	}
	/**
	 * @param partXXII the partXXII to set
	 */
	public void setPartXXII(boolean partXXII) {
		this.partXXII = partXXII;
	}
	/**
	 * @return the partXXIIcmp33a
	 */
	public boolean isPartXXIIcmp33a() {
		return partXXIIcmp33a;
	}
	/**
	 * @param partXXIIcmp33a the partXXIIcmp33a to set
	 */
	public void setPartXXIIcmp33a(boolean partXXIIcmp33a) {
		this.partXXIIcmp33a = partXXIIcmp33a;
	}
	/**
	 * @return the partXXIIcmp33b
	 */
	public String getPartXXIIcmp33b() {
		return partXXIIcmp33b;
	}
	/**
	 * @param partXXIIcmp33b the partXXIIcmp33b to set
	 */
	public void setPartXXIIcmp33b(String partXXIIcmp33b) {
		this.partXXIIcmp33b = partXXIIcmp33b;
	}
	/**
	 * @return the partXXIII
	 */
	public boolean isPartXXIII() {
		return partXXIII;
	}
	/**
	 * @param partXXIII the partXXIII to set
	 */
	public void setPartXXIII(boolean partXXIII) {
		this.partXXIII = partXXIII;
	}
	/**
	 * @return the partXXIIIcmp34a
	 */
	public boolean isPartXXIIIcmp34a() {
		return partXXIIIcmp34a;
	}
	/**
	 * @param partXXIIIcmp34a the partXXIIIcmp34a to set
	 */
	public void setPartXXIIIcmp34a(boolean partXXIIIcmp34a) {
		this.partXXIIIcmp34a = partXXIIIcmp34a;
	}
	/**
	 * @return the partXXIIIcmp34a1
	 */
	public String getPartXXIIIcmp34a1() {
		return partXXIIIcmp34a1;
	}
	/**
	 * @param partXXIIIcmp34a1 the partXXIIIcmp34a1 to set
	 */
	public void setPartXXIIIcmp34a1(String partXXIIIcmp34a1) {
		this.partXXIIIcmp34a1 = partXXIIIcmp34a1;
	}
	/**
	 * @return the partXXIIIcmp34b
	 */
	public boolean isPartXXIIIcmp34b() {
		return partXXIIIcmp34b;
	}
	/**
	 * @param partXXIIIcmp34b the partXXIIIcmp34b to set
	 */
	public void setPartXXIIIcmp34b(boolean partXXIIIcmp34b) {
		this.partXXIIIcmp34b = partXXIIIcmp34b;
	}
	/**
	 * @return the partXXIIIcmp34b1
	 */
	public String getPartXXIIIcmp34b1() {
		return partXXIIIcmp34b1;
	}
	/**
	 * @param partXXIIIcmp34b1 the partXXIIIcmp34b1 to set
	 */
	public void setPartXXIIIcmp34b1(String partXXIIIcmp34b1) {
		this.partXXIIIcmp34b1 = partXXIIIcmp34b1;
	}
	/**
	 * @return the partXXIIIcmp34b2
	 */
	public String getPartXXIIIcmp34b2() {
		return partXXIIIcmp34b2;
	}
	/**
	 * @param partXXIIIcmp34b2 the partXXIIIcmp34b2 to set
	 */
	public void setPartXXIIIcmp34b2(String partXXIIIcmp34b2) {
		this.partXXIIIcmp34b2 = partXXIIIcmp34b2;
	}
	/**
	 * @return the partXXIV
	 */
	public boolean isPartXXIV() {
		return partXXIV;
	}
	/**
	 * @param partXXIV the partXXIV to set
	 */
	public void setPartXXIV(boolean partXXIV) {
		this.partXXIV = partXXIV;
	}
	/**
	 * @return the partXXIVcmp35
	 */
	public boolean isPartXXIVcmp35() {
		return partXXIVcmp35;
	}
	/**
	 * @param partXXIVcmp35 the partXXIVcmp35 to set
	 */
	public void setPartXXIVcmp35(boolean partXXIVcmp35) {
		this.partXXIVcmp35 = partXXIVcmp35;
	}
	/**
	 * @return the partXXV
	 */
	public boolean isPartXXV() {
		return partXXV;
	}
	/**
	 * @param partXXV the partXXV to set
	 */
	public void setPartXXV(boolean partXXV) {
		this.partXXV = partXXV;
	}
	/**
	 * @return the partXXVcmp36
	 */
	public boolean isPartXXVcmp36() {
		return partXXVcmp36;
	}
	/**
	 * @param partXXVcmp36 the partXXVcmp36 to set
	 */
	public void setPartXXVcmp36(boolean partXXVcmp36) {
		this.partXXVcmp36 = partXXVcmp36;
	}
	/**
	 * @return the partXXVI
	 */
	public boolean isPartXXVI() {
		return partXXVI;
	}
	/**
	 * @param partXXVI the partXXVI to set
	 */
	public void setPartXXVI(boolean partXXVI) {
		this.partXXVI = partXXVI;
	}
	/**
	 * @return the partXXVIcmp37
	 */
	public boolean isPartXXVIcmp37() {
		return partXXVIcmp37;
	}
	/**
	 * @param partXXVIcmp37 the partXXVIcmp37 to set
	 */
	public void setPartXXVIcmp37(boolean partXXVIcmp37) {
		this.partXXVIcmp37 = partXXVIcmp37;
	}
	/**
	 * @return the partXXVII
	 */
	public boolean isPartXXVII() {
		return partXXVII;
	}
	/**
	 * @param partXXVII the partXXVII to set
	 */
	public void setPartXXVII(boolean partXXVII) {
		this.partXXVII = partXXVII;
	}
	/**
	 * @return the partXXVIIcmp38
	 */
	public String getPartXXVIIcmp38() {
		return partXXVIIcmp38;
	}
	/**
	 * @param partXXVIIcmp38 the partXXVIIcmp38 to set
	 */
	public void setPartXXVIIcmp38(String partXXVIIcmp38) {
		this.partXXVIIcmp38 = partXXVIIcmp38;
	}
	/**
	 * @return the partXXVIIcmp39
	 */
	public boolean isPartXXVIIcmp39() {
		return partXXVIIcmp39;
	}
	/**
	 * @param partXXVIIcmp39 the partXXVIIcmp39 to set
	 */
	public void setPartXXVIIcmp39(boolean partXXVIIcmp39) {
		this.partXXVIIcmp39 = partXXVIIcmp39;
	}
	/**
	 * @return the partXXVIII
	 */
	public boolean isPartXXVIII() {
		return partXXVIII;
	}
	/**
	 * @param partXXVIII the partXXVIII to set
	 */
	public void setPartXXVIII(boolean partXXVIII) {
		this.partXXVIII = partXXVIII;
	}

	/**
	 *  
	 * @return
	 * @throws ObjectStreamException
	 */
	private Object writeReplace() throws ObjectStreamException {

        Field [] attributes =  this.getClass().getDeclaredFields();

        // Setear como vacos los datos nulos
        for (Field field : attributes) {
        	if (!field.getName().equalsIgnoreCase("serialVersionUID")){
	        	try {
	            	String fieldType = field.getGenericType().toString();
	            	if (fieldType.indexOf("String")!=-1) {
	            		if (PropertyUtils.getSimpleProperty(this, field.getName())==null){
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
	 * @return El atributo printName
	 */
	public String getPrintName() {
		return printName;
	}

	/**
	 * Método para establecer el atributo printName
	 * @param printName El valor del atributo printName a establecer.
	 */
	public void setPrintName(String printName) {
		this.printName = printName;
	}

	/**
	 * Método para obtener el atributo paisResidencial
	 * @return El atributo paisResidencial
	 */
	public String getPaisResidencial() {
		return paisResidencial;
	}

	/**
	 * Método para establecer el atributo paisResidencial
	 * @param paisResidencial El valor del atributo paisResidencial a establecer.
	 */
	public void setPaisResidencial(String paisResidencial) {
		this.paisResidencial = paisResidencial;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp4a
	 * @return El atributo readOnlyCmp4a
	 */
	public boolean isReadOnlyCmp4a() {
		return readOnlyCmp4a;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp4a
	 * @param readOnlyCmp4a El valor del atributo readOnlyCmp4a a establecer.
	 */
	public void setReadOnlyCmp4a(boolean readOnlyCmp4a) {
		this.readOnlyCmp4a = readOnlyCmp4a;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp4h
	 * @return El atributo readOnlyCmp4h
	 */
	public boolean isReadOnlyCmp4h() {
		return readOnlyCmp4h;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp4h
	 * @param readOnlyCmp4h El valor del atributo readOnlyCmp4h a establecer.
	 */
	public void setReadOnlyCmp4h(boolean readOnlyCmp4h) {
		this.readOnlyCmp4h = readOnlyCmp4h;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp4
	 * @return El atributo readOnlyCmp4
	 */
	public boolean isReadOnlyCmp4() {
		return readOnlyCmp4;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp4
	 * @param readOnlyCmp4 El valor del atributo readOnlyCmp4 a establecer.
	 */
	public void setReadOnlyCmp4(boolean readOnlyCmp4) {
		this.readOnlyCmp4 = readOnlyCmp4;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp10
	 * @return El atributo readOnlyCmp10
	 */
	public boolean isReadOnlyCmp10() {
		return readOnlyCmp10;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp10
	 * @param readOnlyCmp10 El valor del atributo readOnlyCmp10 a establecer.
	 */
	public void setReadOnlyCmp10(boolean readOnlyCmp10) {
		this.readOnlyCmp10 = readOnlyCmp10;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp7
	 * @return El atributo readOnlyCmp7
	 */
	public boolean isReadOnlyCmp7() {
		return readOnlyCmp7;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp7
	 * @param readOnlyCmp7 El valor del atributo readOnlyCmp7 a establecer.
	 */
	public void setReadOnlyCmp7(boolean readOnlyCmp7) {
		this.readOnlyCmp7 = readOnlyCmp7;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp8
	 * @return El atributo readOnlyCmp8
	 */
	public boolean isReadOnlyCmp8() {
		return readOnlyCmp8;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp8
	 * @param readOnlyCmp8 El valor del atributo readOnlyCmp8 a establecer.
	 */
	public void setReadOnlyCmp8(boolean readOnlyCmp8) {
		this.readOnlyCmp8 = readOnlyCmp8;
	}

	/**
	 * Método para obtener el atributo readOnlyOpcionesCmp8
	 * @return El atributo readOnlyOpcionesCmp8
	 */
	public boolean isReadOnlyOpcionesCmp8() {
		return readOnlyOpcionesCmp8;
	}

	/**
	 * Método para establecer el atributo readOnlyOpcionesCmp8
	 * @param readOnlyOpcionesCmp8 El valor del atributo readOnlyOpcionesCmp8 a establecer.
	 */
	public void setReadOnlyOpcionesCmp8(boolean readOnlyOpcionesCmp8) {
		this.readOnlyOpcionesCmp8 = readOnlyOpcionesCmp8;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp14a
	 * @return El atributo readOnlyCmp14a
	 */
	public boolean isReadOnlyCmp14a() {
		return readOnlyCmp14a;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp14a
	 * @param readOnlyCmp14a El valor del atributo readOnlyCmp14a a establecer.
	 */
	public void setReadOnlyCmp14a(boolean readOnlyCmp14a) {
		this.readOnlyCmp14a = readOnlyCmp14a;
	}

	/**
	 * Método para obtener el atributo readOnlyCmp19
	 * @return El atributo readOnlyCmp19
	 */
	public boolean isReadOnlyCmp19() {
		return readOnlyCmp19;
	}

	/**
	 * Método para establecer el atributo readOnlyCmp19
	 * @param readOnlyCmp19 El valor del atributo readOnlyCmp19 a establecer.
	 */
	public void setReadOnlyCmp19(boolean readOnlyCmp19) {
		this.readOnlyCmp19 = readOnlyCmp19;
	}

	/**
	 * @return the partXXIX
	 */
	public String getPartXXIX() {
		return partXXIX;
	}

	/**
	 * @param partXXIX the partXXIX to set
	 */
	public void setPartXXIX(String partXXIX) {
		this.partXXIX = partXXIX;
	}

}
