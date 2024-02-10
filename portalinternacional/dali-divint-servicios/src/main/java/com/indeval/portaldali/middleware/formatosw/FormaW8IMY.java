/**
 * 
 */
package com.indeval.portaldali.middleware.formatosw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8IMY;

/**
 * Clase para la forma W8BEN
 * 
 * @author Rafael Ibarra
 */
@SuppressWarnings("unchecked")
public class FormaW8IMY extends FormaGeneral implements Constantes {
	
	/** Log de clase. */
	
	/** Tipos de tax payer id number */
	public static String tipoSSNITN = "SSN or ITIN";
	public static String tipoEIN = "EIN";
	public static String tipoQIEIN = "QI-EIN";
	/** Lista de Items de propietarios */
	private List lstTipoEntidad;
	/** Tipo Propietario seleccionado */
	private Long tipoEntidad;
	/** Descripcion Tipo Entidad seleccionado */
	private String descripcionTipoEntidad;
	/** Indica la calle de la direccion residencial */
	private String calleResidencial;
	/** Indica el numero exterior de la direccion residencial */
	private String numeroExteriorResidencial;
	/** Indica el numero interior de la direccion residencial */
	private String numeroInteriorResidencial;
	/** Indica el codigo postal de la direccion residencial */
	private String codigoPostalResidencial;
	/** Indica la ciudad o poblado de la direccion residencial */
	private String cityTownResidencial;
	/** Indica el estado de la direccion residencial */
	private String stateProvinceResidencial;
	/** Indica la pais de la direccion residencial */
	private String paisResidencial;
	/** Indica la calle de la direccion Postal */
	private String callePostal;
	/** Indica el numero exterior de la direccion Postal */
	private String numeroExteriorPostal;
	/** Indica el numero interior de la direccion Postal */
	private String numeroInteriorPostal;
	/** Indica el codigo postal de la direccion Postal */
	private String codigoPostalPostal;
	/** Indica la ciudad o poblado de la direccion Postal */
	private String cityTownPostal;
	/** Indica el estado de la direccion Postal */
	private String stateProvincePostal;
	/** Indica la pais de la direccion postal */
	private String paisPostal;
	/** Indica el U.S. taxpayer id number */
	private String taxPayerIdNumber;
	/** Lista para los Tipos de Tax Payer Id Number */
	private List lstTaxPayerIdNumber;
	/** Tipo de tax payer id number */
	private Long typeTaxPayerIdNumber;
	/** Tipo de tax payer id number */
	private String typeTaxPayerIdNumberString;
	/** Indica el RFC o equivalente */
	private String rfc;
	/** Numeros de referencia */
	private String numeroReferencia;
	/** Primera opcion de la linea 9 ChechBox*/
	private Boolean line9a;
	/** Segunda opcion de la linea 9 ChechBox*/
	private Boolean line9b1;
	/** Segunda opcion de la linea 9 Caja de texto */
	private String line9b2;
	/** Tercera opcion de la linea 9 ChechBox*/
	private Boolean line9c1;
	/** Tercera opcion de la linea 9 Caja de texto*/
	private String line9c2;
	/** Primera opcion de la linea 10 ChechBox*/
	private Boolean line10a;
	/** Segunda opcion de la linea 10 ChechBox*/
	private Boolean line10b;
	/** Primera opcion de la linea 11 ChechBox*/
	private Boolean line11;
	/** Primera opcion de la linea 12 ChechBox*/
	private Boolean line12;
	/** Primera opcion de la linea 13 ChechBox*/
	private Boolean line13;
	/** Primera opcion de la linea 14 ChechBox*/
	private Boolean line14;
	/** Primera opcion de la linea 15 ChechBox*/
	private Boolean line15;
	/** Indica si US id number esta deshabilitado para no validarlo*/
	private boolean disabledUsIdNumber;
	/** Indica si reference number esta deshabilitado para no validarlo*/
	private boolean disabledreferenceNumber;
		/** Indica el bloqueo de los campos */
	private CamposFormatoW8IMY campos = new CamposFormatoW8IMY();
	
	public FormaW8IMY() {
		super();
	}
	
	
	public FormaW8IMY( Long idCuentaNombrada, Long idTipoBeneficiario, Long idInstitucion, List<Field3W8IMY> listField3 ) {
		super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8IMY(idCuentaNombrada, idTipoBeneficiario);
		lstTipoEntidad = new ArrayList<SelectItem>();
		
		if( listField3 != null && listField3.size() > 0 ) {
			Map<Long, Boolean> mapaBloqueosCampo3 = campos.getField3();
			for(Field3W8IMY campo3 : listField3) {
				lstTipoEntidad.add(new SelectItem(campo3.getIdCampo(),campo3.getDescripcion(),campo3.getDescripcion(),mapaBloqueosCampo3.get(campo3.getIdCampo())));
			}
		}
		
		lstTaxPayerIdNumber = new ArrayList<SelectItem>();
		lstTaxPayerIdNumber.add(new SelectItem(1 ,tipoSSNITN, tipoSSNITN, campos.isUsTaxpayerIdentificationNumberSSNorITIN()));
		lstTaxPayerIdNumber.add(new SelectItem(2 ,tipoEIN, tipoEIN, campos.isUsTaxpayerIdentificationNumberEIN()));
		lstTaxPayerIdNumber.add(new SelectItem(3, tipoQIEIN, tipoQIEIN, campos.isUsTaxpayerIdentificationNumberQIEIN()));
		construyeReglas();
	}
	
	private void construyeReglas() {
		Long idCuentaNombrada = getCustodio();
		Long idTipoBenef = getIdTipoBeneficiario();
		setPersonaFisica(false);
		// Definimos los valores por default
		if (idTipoBenef == FIDEICOMISO_SIMPLE || idTipoBenef == FIDEICOMISO_SIMPLE_W8IMY) {
			setFalseCheckBoxes();
			line15 = true;
			if( idCuentaNombrada == CN_CLEARSTREAM ) {
				numeroReferencia = "75986";
			} else if( idCuentaNombrada == CN_EUROCLEAR_BANK ) {
				numeroReferencia = "23310";
			}
		} else if (idTipoBenef == INTERMEDIARIO_CALIFICADO_CRPR
				|| idTipoBenef == INTERMEDIARIO_CALIFICADO_SRPR) {
			tipoEntidad = 1l;
			typeTaxPayerIdNumber = 3l;
			line10a = false;
			line10b = false;
			line11 = false;
			line12 = false;
			line13 = false;
			line14 = false;
			line15 = false;
			line9a = true;
		} else if (idTipoBenef == INTERMEDIARIO_NO_CALIFICADO) {
			tipoEntidad = 2l;
			line9a = false;
			line9b1 = false;
			line9c1 = false;
			line10a = true;
			line11 = false;
			line12 = false;
			line13 = false;
			line14 = false;
			line15 = false;
		} else if (idTipoBenef == SOCIEDAD_PARTNERSHIP) {
			tipoEntidad = 6l;
			setFalseCheckBoxes();
			line15 = true;
			if( idCuentaNombrada == CN_CLEARSTREAM ) {
				numeroReferencia = "75986";
			} else if( idCuentaNombrada == CN_EUROCLEAR_BANK ) {
				numeroReferencia = "23310";
			}
		} 
	}
	
	public void construyeBean(Beneficiario beneficiario, boolean llenaCamposVacios) {
		super.construyeBean(beneficiario, llenaCamposVacios);
		
		descTipoBeneficiario = beneficiario.getFormatoW8IMY().getField3().getDescripcion();
		
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8IMY(custodio, idTipoBeneficiario);
		
		tipoEntidad =  beneficiario.getFormatoW8IMY().getField3().getIdCampo();
		descripcionTipoEntidad = beneficiario.getFormatoW8IMY().getField3().getDescripcion();
		if( StringUtils.isNotBlank(descripcionTipoEntidad) ) {
			descripcionTipoEntidad = descripcionTipoEntidad.toUpperCase();
		}
		
		if(beneficiario.getDomicilioW8Normal() != null) {
			calleResidencial = beneficiario.getDomicilioW8Normal().getStreet();
			numeroExteriorResidencial =  beneficiario.getDomicilioW8Normal().getOuterNumber();
			numeroInteriorResidencial =  beneficiario.getDomicilioW8Normal().getInteriorNumber();
			codigoPostalResidencial = beneficiario.getDomicilioW8Normal().getPostalCode();
			cityTownResidencial = beneficiario.getDomicilioW8Normal().getCityTown();
			stateProvinceResidencial = beneficiario.getDomicilioW8Normal().getStateProvince();
			paisResidencial = beneficiario.getDomicilioW8Normal().getCountry();
		}
		
		if(beneficiario.getDomicilioW8Correo() != null) {
			callePostal = beneficiario.getDomicilioW8Correo().getStreet();
			numeroExteriorPostal =  beneficiario.getDomicilioW8Correo().getOuterNumber();
			numeroInteriorPostal =  beneficiario.getDomicilioW8Correo().getInteriorNumber();
			codigoPostalPostal = beneficiario.getDomicilioW8Correo().getPostalCode();
			cityTownPostal = beneficiario.getDomicilioW8Correo().getCityTown();
			stateProvincePostal = beneficiario.getDomicilioW8Correo().getStateProvince();
			paisPostal = beneficiario.getDomicilioW8Correo().getCountry();
            setDisabledDireccionPostal(false);
		}
		else {
            setDisabledDireccionPostal(true);
        }
		
		construyeReglas();
		
		taxPayerIdNumber = beneficiario.getFormatoW8IMY().getTaxpayerIdNumb();
		typeTaxPayerIdNumber = beneficiario.getFormatoW8IMY().getTipoTaxIdNumb();

        if (typeTaxPayerIdNumber != null &&
                typeTaxPayerIdNumber > 0) {
            if (typeTaxPayerIdNumber == 1) {
                typeTaxPayerIdNumberString = tipoSSNITN;
            } else if (typeTaxPayerIdNumber == 2) {
                typeTaxPayerIdNumberString = tipoEIN;
            } else if (typeTaxPayerIdNumber == 3) {
                typeTaxPayerIdNumberString = tipoQIEIN;
            }
        } else {
            typeTaxPayerIdNumberString = null;
        }
		rfc = beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
		numeroReferencia = beneficiario.getFormatoW8IMY().getReferenceNumbers();
		
		line9a = beneficiario.getFormatoW8IMY().getField9OptionA();
		line9b1 = beneficiario.getFormatoW8IMY().getField9OptionB();
		line9b2 = beneficiario.getFormatoW8IMY().getField9OptionBLine();
		line9c1 = beneficiario.getFormatoW8IMY().getField9OptionC();
		line9c2 = beneficiario.getFormatoW8IMY().getField9OptionCLine();
		
		line10a = beneficiario.getFormatoW8IMY().getField10OptionA();
		line10b = beneficiario.getFormatoW8IMY().getField10OptionB();
		
		line11 = beneficiario.getFormatoW8IMY().getField11();
		line12 = beneficiario.getFormatoW8IMY().getField12();
		line13 = beneficiario.getFormatoW8IMY().getField13();
		line14 = beneficiario.getFormatoW8IMY().getField14();
		line15 = beneficiario.getFormatoW8IMY().getField15();

        if(llenaCamposVacios) {
            ponerValoresVacios();
        }
	}
	
	private void ponerValoresVacios() {
		calleResidencial = StringUtils.isNotBlank(calleResidencial) ? calleResidencial : BEN_CADENA_VACIA;
		numeroExteriorResidencial = StringUtils.isNotBlank(numeroExteriorResidencial) ? numeroExteriorResidencial : BEN_CADENA_VACIA;
		numeroInteriorResidencial = StringUtils.isNotBlank(numeroInteriorResidencial) ? numeroInteriorResidencial : BEN_CADENA_VACIA;
		codigoPostalResidencial = StringUtils.isNotBlank(codigoPostalResidencial) ? codigoPostalResidencial : BEN_CADENA_VACIA;
		cityTownResidencial = StringUtils.isNotBlank(cityTownResidencial) ? cityTownResidencial : BEN_CADENA_VACIA;
		stateProvinceResidencial = StringUtils.isNotBlank(stateProvinceResidencial) ? stateProvinceResidencial : BEN_CADENA_VACIA;
		paisResidencial = StringUtils.isNotBlank(paisResidencial) ? paisResidencial : BEN_CADENA_VACIA;
		
		callePostal = StringUtils.isNotBlank(callePostal) ? callePostal : BEN_CADENA_VACIA;
		numeroExteriorPostal = StringUtils.isNotBlank(numeroExteriorPostal) ? numeroExteriorPostal : BEN_CADENA_VACIA;
		numeroInteriorPostal = StringUtils.isNotBlank(numeroInteriorPostal) ? numeroInteriorPostal : BEN_CADENA_VACIA;
		codigoPostalPostal = StringUtils.isNotBlank(codigoPostalPostal) ? codigoPostalPostal : BEN_CADENA_VACIA;
		cityTownPostal = StringUtils.isNotBlank(cityTownPostal) ? cityTownPostal : BEN_CADENA_VACIA;
		stateProvincePostal = StringUtils.isNotBlank(stateProvincePostal) ? stateProvincePostal : BEN_CADENA_VACIA;
		paisPostal = StringUtils.isNotBlank(paisPostal) ? paisPostal : BEN_CADENA_VACIA;
		
		taxPayerIdNumber = StringUtils.isNotBlank(taxPayerIdNumber) ? taxPayerIdNumber : BEN_CADENA_VACIA;
		typeTaxPayerIdNumberString = StringUtils.isNotBlank(typeTaxPayerIdNumberString) ? typeTaxPayerIdNumberString : BEN_CADENA_VACIA;
		rfc = StringUtils.isNotBlank(rfc) ? rfc : BEN_CADENA_VACIA;
		numeroReferencia = StringUtils.isNotBlank(numeroReferencia) ? numeroReferencia : BEN_CADENA_VACIA;
		
		line9b2 = StringUtils.isNotBlank(line9b2) ? line9b2 : BEN_CADENA_VACIA;
		line9c2 = StringUtils.isNotBlank(line9c2) ? line9c2 : BEN_CADENA_VACIA;
	}
	
	private void setFalseCheckBoxes() {
		line9a = false;
		line9b1 = false;
		line9c1 = false;
		line10a = false;
		line10b = false;
		line11 = false;
		line12 = false;
		line13 = false;
		line14 = false;
		line15 = false;
	}
	
	public Beneficiario construyeBO() {
		Beneficiario retorno = new Beneficiario();
		
		construyeReglas();
		construyeBOBasico(retorno);
		
		FormatoW8IMY formatoW8IMY = new FormatoW8IMY();
		Field3W8IMY field3W8IMY = new Field3W8IMY();
		field3W8IMY.setIdCampo(tipoEntidad);
		formatoW8IMY.setField3(field3W8IMY);
		formatoW8IMY.setTaxpayerIdNumb(taxPayerIdNumber);
		formatoW8IMY.setTipoTaxIdNumb(typeTaxPayerIdNumber);
		formatoW8IMY.setForeignTaxIdNumb(rfc);
		formatoW8IMY.setReferenceNumbers(numeroReferencia);
		formatoW8IMY.setField9OptionA(line9a);
		formatoW8IMY.setField9OptionB(line9b1);
		formatoW8IMY.setField9OptionBLine(line9b2);
		formatoW8IMY.setField9OptionC(line9c1);
		formatoW8IMY.setField9OptionCLine(line9c2);
		formatoW8IMY.setField10OptionA(line10a);
		formatoW8IMY.setField10OptionB(line10b);
		formatoW8IMY.setField11(line11);
		formatoW8IMY.setField12(line12);
		formatoW8IMY.setField13(line13);
		formatoW8IMY.setField14(line14);
		formatoW8IMY.setField15(line15);
		formatoW8IMY.setDisabledDireccionPostal(isDisabledDireccionPostal());
		
		retorno.setDomicilioW8Normal(construyeDomicilioNormal());
		if(!isDisabledDireccionPostal()) {
			retorno.setDomicilioW8Correo(construyeDomicilioPostal());
		}
		
		retorno.setFormatoW8IMY(formatoW8IMY);
		
		return retorno;
	}
	
	private Domicilio construyeDomicilioNormal() {
		Domicilio retorno = new Domicilio();
		retorno.setStreet(calleResidencial);
		retorno.setOuterNumber(numeroExteriorResidencial);
		retorno.setInteriorNumber(numeroInteriorResidencial);
		retorno.setPostalCode(codigoPostalResidencial);
		retorno.setCityTown(cityTownResidencial);
		retorno.setStateProvince(stateProvinceResidencial);
		retorno.setCountry(paisResidencial);
		return retorno;
	}
	
	private Domicilio construyeDomicilioPostal() {
		Domicilio retorno = new Domicilio();
		retorno.setStreet(callePostal);
		retorno.setOuterNumber(numeroExteriorPostal);
		retorno.setInteriorNumber(numeroInteriorPostal);
		retorno.setPostalCode(codigoPostalPostal);
		retorno.setCityTown(cityTownPostal);
		retorno.setStateProvince(stateProvincePostal);
		retorno.setCountry(paisPostal);
		return retorno;
	}

    public Beneficiario construyeBO(Beneficiario beneficiario) {

		construyeReglas();
		construyeBOBasico(beneficiario);

		FormatoW8IMY formatoW8IMY = beneficiario.getFormatoW8IMY();
		Field3W8IMY field3W8IMY = formatoW8IMY.getField3();
		field3W8IMY.setIdCampo(tipoEntidad);
		formatoW8IMY.setField3(field3W8IMY);
		formatoW8IMY.setTaxpayerIdNumb(taxPayerIdNumber);
		formatoW8IMY.setTipoTaxIdNumb(typeTaxPayerIdNumber);
		formatoW8IMY.setForeignTaxIdNumb(rfc);
		formatoW8IMY.setReferenceNumbers(numeroReferencia);
		formatoW8IMY.setField9OptionA(line9a);
		formatoW8IMY.setField9OptionB(line9b1);
		formatoW8IMY.setField9OptionBLine(line9b2);
		formatoW8IMY.setField9OptionC(line9c1);
		formatoW8IMY.setField9OptionCLine(line9c2);
		formatoW8IMY.setField10OptionA(line10a);
		formatoW8IMY.setField10OptionB(line10b);
		formatoW8IMY.setField11(line11);
		formatoW8IMY.setField12(line12);
		formatoW8IMY.setField13(line13);
		formatoW8IMY.setField14(line14);
		formatoW8IMY.setField15(line15);
		formatoW8IMY.setDisabledDireccionPostal(isDisabledDireccionPostal());

		beneficiario.setDomicilioW8Normal(construyeDomicilioNormal(beneficiario.getDomicilioW8Normal()));
		if(!isDisabledDireccionPostal()) {
            if(beneficiario.getDomicilioW8Correo() == null) {
                beneficiario.setDomicilioW8Correo(new Domicilio());
            }
			beneficiario.setDomicilioW8Correo(construyeDomicilioPostal(beneficiario.getDomicilioW8Correo()));
		}

		beneficiario.setFormatoW8IMY(formatoW8IMY);

		return beneficiario;
	}

	private Domicilio construyeDomicilioNormal(Domicilio retorno) {
		retorno.setStreet(calleResidencial);
		retorno.setOuterNumber(numeroExteriorResidencial);
		retorno.setInteriorNumber(numeroInteriorResidencial);
		retorno.setPostalCode(codigoPostalResidencial);
		retorno.setCityTown(cityTownResidencial);
		retorno.setStateProvince(stateProvinceResidencial);
		retorno.setCountry(paisResidencial);
		return retorno;
	}

	private Domicilio construyeDomicilioPostal(Domicilio retorno) {
		retorno.setStreet(callePostal);
		retorno.setOuterNumber(numeroExteriorPostal);
		retorno.setInteriorNumber(numeroInteriorPostal);
		retorno.setPostalCode(codigoPostalPostal);
		retorno.setCityTown(cityTownPostal);
		retorno.setStateProvince(stateProvincePostal);
		retorno.setCountry(paisPostal);
		return retorno;
	}

	/**
	 * @return the lstTipoEntidad
	 */
	public List getLstTipoEntidad() {
		return lstTipoEntidad;
	}

	/**
	 * @param lstTipoEntidad the lstTipoEntidad to set
	 */
	public void setLstTipoEntidad(List lstTipoEntidad) {
		this.lstTipoEntidad = lstTipoEntidad;
	}

	/**
	 * @return the tipoEntidad
	 */
	public Long getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * @param tipoEntidad the tipoEntidad to set
	 */
	public void setTipoEntidad(Long tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * @return the descripcionTipoEntidad
	 */
	public String getDescripcionTipoEntidad() {
		return descripcionTipoEntidad;
	}

	/**
	 * @param descripcionTipoEntidad the descripcionTipoEntidad to set
	 */
	public void setDescripcionTipoEntidad(String descripcionTipoEntidad) {
		this.descripcionTipoEntidad = descripcionTipoEntidad;
	}

	/**
	 * @return the calleResidencial
	 */
	public String getCalleResidencial() {
		return calleResidencial;
	}

	/**
	 * @param calleResidencial the calleResidencial to set
	 */
	public void setCalleResidencial(String calleResidencial) {
		this.calleResidencial = calleResidencial;
	}

	/**
	 * @return the numeroExteriorResidencial
	 */
	public String getNumeroExteriorResidencial() {
		return numeroExteriorResidencial;
	}

	/**
	 * @param numeroExteriorResidencial the numeroExteriorResidencial to set
	 */
	public void setNumeroExteriorResidencial(String numeroExteriorResidencial) {
		this.numeroExteriorResidencial = numeroExteriorResidencial;
	}

	/**
	 * @return the numeroInteriorResidencial
	 */
	public String getNumeroInteriorResidencial() {
		return numeroInteriorResidencial;
	}

	/**
	 * @param numeroInteriorResidencial the numeroInteriorResidencial to set
	 */
	public void setNumeroInteriorResidencial(String numeroInteriorResidencial) {
		this.numeroInteriorResidencial = numeroInteriorResidencial;
	}

	/**
	 * @return the codigoPostalResidencial
	 */
	public String getCodigoPostalResidencial() {
		return codigoPostalResidencial;
	}

	/**
	 * @param codigoPostalResidencial the codigoPostalResidencial to set
	 */
	public void setCodigoPostalResidencial(String codigoPostalResidencial) {
		this.codigoPostalResidencial = codigoPostalResidencial;
	}

	/**
	 * @return the cityTownResidencial
	 */
	public String getCityTownResidencial() {
		return cityTownResidencial;
	}

	/**
	 * @param cityTownResidencial the cityTownResidencial to set
	 */
	public void setCityTownResidencial(String cityTownResidencial) {
		this.cityTownResidencial = cityTownResidencial;
	}

	/**
	 * @return the stateProvinceResidencial
	 */
	public String getStateProvinceResidencial() {
		return stateProvinceResidencial;
	}

	/**
	 * @param stateProvinceResidencial the stateProvinceResidencial to set
	 */
	public void setStateProvinceResidencial(String stateProvinceResidencial) {
		this.stateProvinceResidencial = stateProvinceResidencial;
	}

	/**
	 * @return the paisResidencial
	 */
	public String getPaisResidencial() {
		return paisResidencial;
	}

	/**
	 * @param paisResidencial the paisResidencial to set
	 */
	public void setPaisResidencial(String paisResidencial) {
		this.paisResidencial = paisResidencial;
	}

	/**
	 * @return the callePostal
	 */
	public String getCallePostal() {
		return callePostal;
	}

	/**
	 * @param callePostal the callePostal to set
	 */
	public void setCallePostal(String callePostal) {
		this.callePostal = callePostal;
	}

	/**
	 * @return the numeroExteriorPostal
	 */
	public String getNumeroExteriorPostal() {
		return numeroExteriorPostal;
	}

	/**
	 * @param numeroExteriorPostal the numeroExteriorPostal to set
	 */
	public void setNumeroExteriorPostal(String numeroExteriorPostal) {
		this.numeroExteriorPostal = numeroExteriorPostal;
	}

	/**
	 * @return the numeroInteriorPostal
	 */
	public String getNumeroInteriorPostal() {
		return numeroInteriorPostal;
	}

	/**
	 * @param numeroInteriorPostal the numeroInteriorPostal to set
	 */
	public void setNumeroInteriorPostal(String numeroInteriorPostal) {
		this.numeroInteriorPostal = numeroInteriorPostal;
	}

	/**
	 * @return the codigoPostalPostal
	 */
	public String getCodigoPostalPostal() {
		return codigoPostalPostal;
	}

	/**
	 * @param codigoPostalPostal the codigoPostalPostal to set
	 */
	public void setCodigoPostalPostal(String codigoPostalPostal) {
		this.codigoPostalPostal = codigoPostalPostal;
	}

	/**
	 * @return the cityTownPostal
	 */
	public String getCityTownPostal() {
		return cityTownPostal;
	}

	/**
	 * @param cityTownPostal the cityTownPostal to set
	 */
	public void setCityTownPostal(String cityTownPostal) {
		this.cityTownPostal = cityTownPostal;
	}

	/**
	 * @return the stateProvincePostal
	 */
	public String getStateProvincePostal() {
		return stateProvincePostal;
	}

	/**
	 * @param stateProvincePostal the stateProvincePostal to set
	 */
	public void setStateProvincePostal(String stateProvincePostal) {
		this.stateProvincePostal = stateProvincePostal;
	}

	/**
	 * @return the paisPostal
	 */
	public String getPaisPostal() {
		return paisPostal;
	}

	/**
	 * @param paisPostal the paisPostal to set
	 */
	public void setPaisPostal(String paisPostal) {
		this.paisPostal = paisPostal;
	}

	/**
	 * @return the taxPayerIdNumber
	 */
	public String getTaxPayerIdNumber() {
		return taxPayerIdNumber;
	}

	/**
	 * @param taxPayerIdNumber the taxPayerIdNumber to set
	 */
	public void setTaxPayerIdNumber(String taxPayerIdNumber) {
		this.taxPayerIdNumber = taxPayerIdNumber;
	}

	/**
	 * @return the lstTaxPayerIdNumber
	 */
	public List getLstTaxPayerIdNumber() {
		return lstTaxPayerIdNumber;
	}

	/**
	 * @param lstTaxPayerIdNumber the lstTaxPayerIdNumber to set
	 */
	public void setLstTaxPayerIdNumber(List lstTaxPayerIdNumber) {
		this.lstTaxPayerIdNumber = lstTaxPayerIdNumber;
	}

	/**
	 * @return the typeTaxPayerIdNumber
	 */
	public Long getTypeTaxPayerIdNumber() {
		return typeTaxPayerIdNumber;
	}

	/**
	 * @param typeTaxPayerIdNumber the typeTaxPayerIdNumber to set
	 */
	public void setTypeTaxPayerIdNumber(Long typeTaxPayerIdNumber) {
		this.typeTaxPayerIdNumber = typeTaxPayerIdNumber;
	}

	/**
	 * @return the typeTaxPayerIdNumberString
	 */
	public String getTypeTaxPayerIdNumberString() {
		return typeTaxPayerIdNumberString;
	}


	/**
	 * @param typeTaxPayerIdNumberString the typeTaxPayerIdNumberString to set
	 */
	public void setTypeTaxPayerIdNumberString(String typeTaxPayerIdNumberString) {
		this.typeTaxPayerIdNumberString = typeTaxPayerIdNumberString;
	}


	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the numeroReferencia
	 */
	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	/**
	 * @param numeroReferencia the numeroReferencia to set
	 */
	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}

	/**
	 * @return the line9a
	 */
	public Boolean getLine9a() {
		return line9a;
	}

	/**
	 * @param line9a the line9a to set
	 */
	public void setLine9a(Boolean line9a) {
		this.line9a = line9a;
	}

	/**
	 * @return the line9b1
	 */
	public Boolean getLine9b1() {
		return line9b1;
	}

	/**
	 * @param line9b1 the line9b1 to set
	 */
	public void setLine9b1(Boolean line9b1) {
		this.line9b1 = line9b1;
	}

	/**
	 * @return the line9b2
	 */
	public String getLine9b2() {
		return line9b2;
	}

	/**
	 * @param line9b2 the line9b2 to set
	 */
	public void setLine9b2(String line9b2) {
		this.line9b2 = line9b2;
	}

	/**
	 * @return the line9c1
	 */
	public Boolean getLine9c1() {
		return line9c1;
	}

	/**
	 * @param line9c1 the line9c1 to set
	 */
	public void setLine9c1(Boolean line9c1) {
		this.line9c1 = line9c1;
	}

	/**
	 * @return the line9c2
	 */
	public String getLine9c2() {
		return line9c2;
	}

	/**
	 * @param line9c2 the line9c2 to set
	 */
	public void setLine9c2(String line9c2) {
		this.line9c2 = line9c2;
	}

	/**
	 * @return the line10a
	 */
	public Boolean getLine10a() {
		return line10a;
	}

	/**
	 * @param line10a the line10a to set
	 */
	public void setLine10a(Boolean line10a) {
		this.line10a = line10a;
	}

	/**
	 * @return the line10b
	 */
	public Boolean getLine10b() {
		return line10b;
	}

	/**
	 * @param line10b the line10b to set
	 */
	public void setLine10b(Boolean line10b) {
		this.line10b = line10b;
	}

	/**
	 * @return the line11
	 */
	public Boolean getLine11() {
		return line11;
	}

	/**
	 * @param line11 the line11 to set
	 */
	public void setLine11(Boolean line11) {
		this.line11 = line11;
	}

	/**
	 * @return the line12
	 */
	public Boolean getLine12() {
		return line12;
	}

	/**
	 * @param line12 the line12 to set
	 */
	public void setLine12(Boolean line12) {
		this.line12 = line12;
	}

	/**
	 * @return the line13
	 */
	public Boolean getLine13() {
		return line13;
	}

	/**
	 * @param line13 the line13 to set
	 */
	public void setLine13(Boolean line13) {
		this.line13 = line13;
	}

	/**
	 * @return the line14
	 */
	public Boolean getLine14() {
		return line14;
	}

	/**
	 * @param line14 the line14 to set
	 */
	public void setLine14(Boolean line14) {
		this.line14 = line14;
	}

	/**
	 * @return the line15
	 */
	public Boolean getLine15() {
		return line15;
	}

	/**
	 * @param line15 the line15 to set
	 */
	public void setLine15(Boolean line15) {
		this.line15 = line15;
	}

	/**
	 * @return the disabledUsIdNumber
	 */
	public boolean isDisabledUsIdNumber() {
		return disabledUsIdNumber;
	}

	/**
	 * @param disabledUsIdNumber the disabledUsIdNumber to set
	 */
	public void setDisabledUsIdNumber(boolean disabledUsIdNumber) {
		this.disabledUsIdNumber = disabledUsIdNumber;
	}

	/**
	 * @return the disabledreferenceNumber
	 */
	public boolean isDisabledreferenceNumber() {
		return disabledreferenceNumber;
	}

	/**
	 * @param disabledreferenceNumber the disabledreferenceNumber to set
	 */
	public void setDisabledreferenceNumber(boolean disabledreferenceNumber) {
		this.disabledreferenceNumber = disabledreferenceNumber;
	}

	/**
	 * @return the campos
	 */
	public CamposFormatoW8IMY getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(CamposFormatoW8IMY campos) {
		this.campos = campos;
	}
	
	
	
	

}
