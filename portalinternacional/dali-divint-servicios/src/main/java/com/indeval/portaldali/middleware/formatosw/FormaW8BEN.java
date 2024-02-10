/**
 * 
 */
package com.indeval.portaldali.middleware.formatosw;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8BEN;

/**
 * Clase para la forma W8BEN
 * 
 * @author Rafael Ibarra
 */
/**
 * @author ribarra
 *
 */
/**
 * @author ribarra
 *
 */
@SuppressWarnings("unchecked")
public class FormaW8BEN extends FormaGeneral implements Constantes {
	
	/** Log de clase. */
	
	
	/** Tipos de tax payer id number */
	public static String tipoSSNITN = "SSN or ITIN";
	public static String tipoEIN = "EIN";
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	/** Lista de Items de propietarios */
	private List lstTipoPropietario;
	/** Tipo Propietario seleccionado */
	private Long tipoPropietario;
	/** Descripcion Tipo Propietario seleccionado */
	private String descripcionTipoPropietario;
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
	/** Indica el RFC o equivalente */
	private String rfc;
	/** Numeros de referencia */
	private String numeroReferencia;
	/** Lista para los Tipos de Tax Payer Id Number */
	private List lstTaxPayerIdNumber;
	/** Tipo de tax payer id number */
	private Long typeTaxPayerIdNumber;
	/** Primera opcion de la linea 9 ChechBox*/
	private Boolean line9a1;
	/** Primera opcion de la linea 9 Caja de texto */
	private String line9a2;
	/** Segunda opcion de la linea 9 ChechBox*/
	private Boolean line9b;
	/** Tercera opcion de la linea 9 ChechBox*/
	private Boolean line9c;
	/** Cuarta opcion de la linea 9 ChechBox*/
	private Boolean line9d;
	/** Quinta opcion de la linea 9 ChechBox*/
	private Boolean line9e;
	/** Primera opcion de la linea 10 parte 1*/
	private String line10p1;
	/** Primera opcion de la linea 10 parte 2*/
	private String line10p2;
	/** Primera opcion de la linea 10 parte 3*/
	private String line10p3;
	/** Primera opcion de la linea 10 parte 4*/
	private String line10p4;
	/** Cuarta opcion de la linea 11 ChechBox*/
	private Boolean line11;
	/**  Capacity in which acting */
	private String capacityActing;
	/** Indica el bloqueo de los campos */
	private CamposFormatoW8BEN campos = new CamposFormatoW8BEN();
	/** Indica si US id number esta deshabilitado para no validarlo*/
	private boolean disabledUsIdNumber;
	/** Indica si reference number esta deshabilitado para no validarlo*/
	private boolean disabledreferenceNumber;
	/** Indica si la seccion 10r esta deshabilitado para no validarlo*/
	private boolean disabledSection10;
	
	private String signer;
	
	private Integer fechaNacimientoDia;
	private Integer fechaNacimientoMes;
	private Integer fechaNacimientoAno;
	private Date fechaNacimiento;
	
	
	public FormaW8BEN() {
		super();
		df.setLenient(false);
	}
	
	public FormaW8BEN( Long idCuentaNombrada, Long idTipoBeneficiario, Long idInstitucion, List<Field3W8BEN> listField3 ) {
		super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
		df.setLenient(false);
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8BEN(idCuentaNombrada, idTipoBeneficiario);
		lstTipoPropietario = new ArrayList<SelectItem>();
		
		if( listField3 != null && listField3.size() > 0 ) {
			Map<Long, Boolean> mapaBloqueosCampo3 = campos.getField3();
			for(Field3W8BEN campo3 : listField3) {
				lstTipoPropietario.add(new SelectItem(campo3.getIdCampo(),campo3.getDescripcion(),campo3.getDescripcion(),mapaBloqueosCampo3.get(campo3.getIdCampo())));
			}
		}
		
		lstTaxPayerIdNumber = new ArrayList<SelectItem>();
		lstTaxPayerIdNumber.add(new SelectItem(1 ,tipoSSNITN,tipoSSNITN,true));
		lstTaxPayerIdNumber.add(new SelectItem(2 ,tipoEIN,tipoEIN,true));
		construyeReglas();
	}
	
	private void construyeReglas() {
		Long idCuentaNombrada = getCustodio();
		Long idTipoBeneficiario = getIdTipoBeneficiario();
		setPersonaFisica(false);
		// Definimos los valores por default
		disabledUsIdNumber = true;
		if( idCuentaNombrada == CN_CLEARSTREAM ) {
			numeroReferencia = "75986";
		} else if( idCuentaNombrada == CN_EUROCLEAR_BANK ) {
			numeroReferencia = "23310";
		}

		if (idTipoBeneficiario == PERSONA_FISICA_NO_EUA) {
			setPersonaFisica(true);
			tipoPropietario = new Long(1);
			line9a1 = true;
			line9c = false;
			line9d = false;
			line11 = false;
		} else if (idTipoBeneficiario == PERSONA_MORAL_NO_EUA ||
                (idTipoBeneficiario == FIDEICOMISO_SIMPLE && idCuentaNombrada == CN_DEUSTCHE_BANK) ||
                (idTipoBeneficiario == FIDEICOMISO_COMPLEJO && idCuentaNombrada == CN_DEUSTCHE_BANK)) {
			
			line9a1 = true;
			line9c = true;
			line11 = false;
		} else if (idTipoBeneficiario == SIEFORE_AFORE) {
            tipoPropietario = new Long(12);
			if( idCuentaNombrada == CN_CLEARSTREAM || idCuentaNombrada == CN_EUROCLEAR_BANK ) {
				line10p1 = "11";
				line10p2 = "0";
				line10p3 = "INTERES";
				line10p4 = "THE BENEFICIAL OWNER IS AN ORGANIZATION COSNTITUTED UNDER MEXICAN LAW OPERATED EXCLUSIVELY " +
						"TO ADMINISTER OR PROVIDE PENSION, RETIREMENT, OR OTHER EMPLOYEE BENEFITS";
			} else if( idCuentaNombrada == CN_THE_BANK_OF_NEW_YORK ||
					idCuentaNombrada == CN_JP_MORGAN ||
					idCuentaNombrada == CN_DEUSTCHE_BANK ||
					idCuentaNombrada == CN_BANK_OF_AMERICA) {
				line10p1 = "10";
				line10p2 = "0";
				line10p3 = "DIVIDENDS";
				line10p4 = "THE BENEFICIAL OWNER IS AN ORGANIZATION COSNTITUTED UNDER MEXICAN LAW OPERATED EXCLUSIVELY " +
						"TO ADMINISTER OR PROVIDE PENSION, RETIREMENT, OR OTHER EMPLOYEE BENEFITS";
			} 
			line9a1 = true;
			line9c = true;
			line11 = false;
		} else if (idTipoBeneficiario == FIDEICOMISO_COMPLEJO) {
			tipoPropietario = new Long(7);
			line9a1 = true;
			line9c = true;
			line11 = false;
		}
	}
	
	public Beneficiario construyeBO() {
		Beneficiario retorno = new Beneficiario();
		
		construyeReglas();
		construyeBOBasico(retorno);
		
		FormatoW8BEN formatoW8BEN = new FormatoW8BEN();
		
		Field3W8BEN field3W8BEN = new Field3W8BEN();
		field3W8BEN.setIdCampo(tipoPropietario);
		formatoW8BEN.setField3(field3W8BEN);
		
		formatoW8BEN.setField9OptionA(line9a1);
		if(line9a2 !=null && "W8BEN2014".equals(retorno.getTipoFormato())){
			formatoW8BEN.setField9OptionACountry(line9a2);
		}else{
			formatoW8BEN.setField9OptionACountry(getPais());
		}
		formatoW8BEN.setField9OptionB(line9b);
		formatoW8BEN.setField9OptionC(line9c);
		formatoW8BEN.setField9OptionD(line9d);
		formatoW8BEN.setField9OptionE(line9e);
		if(line10p1 != null){
			formatoW8BEN.setField10Article(line10p1.toUpperCase());
			formatoW8BEN.setDisabledSection10(false);
		}else{
			formatoW8BEN.setField10Article(line10p1);
		}
		if(line10p2 != null){
			formatoW8BEN.setField10Rate(line10p2.toUpperCase());
		}else{
			formatoW8BEN.setField10Rate(line10p2);
		}
		if(line10p3 != null){
			formatoW8BEN.setField10Type(line10p3.toUpperCase());
		}else{
			formatoW8BEN.setField10Article(line10p3);
		}
		if(line10p4 != null){
			formatoW8BEN.setField10Reasons(line10p4.toUpperCase());
		}else{
			formatoW8BEN.setField10Article(line10p4);
		}
		formatoW8BEN.setField11(line11);
		formatoW8BEN.setCapacityActing(capacityActing);
		formatoW8BEN.setDisabledDireccionPostal(isDisabledDireccionPostal());
		formatoW8BEN.setDisabledreferenceNumber(disabledreferenceNumber);
		formatoW8BEN.setDisabledUsIdNumber(disabledUsIdNumber);
		formatoW8BEN.setDisabledSection10(disabledSection10);
		formatoW8BEN.setForeignTaxIdNumb(rfc);
		if(this.signer == null && retorno.getNombreGeneral() != null){
			formatoW8BEN.setSigner(retorno.getNombreGeneral().toUpperCase());
		}else if(this.signer !=null){
			formatoW8BEN.setSigner(this.signer.toUpperCase());
		}
		if(validaFechaNacimiento()){
			formatoW8BEN.setFechaNacimiento(this.fechaNacimiento);
		}
		
		formatoW8BEN.setSigner(retorno.getNombreGeneral());
		if(!disabledUsIdNumber) {
			formatoW8BEN.setTaxpayerIdNumb(taxPayerIdNumber);
			formatoW8BEN.setTipoTaxIdNumb(typeTaxPayerIdNumber);
		}
		if(!disabledreferenceNumber || !campos.isReferenceNumbers()) {
			formatoW8BEN.setReferenceNumbers(numeroReferencia);
		}
		
		retorno.setDomicilioW8Normal(construyeDomicilioNormal());
		if(!isDisabledDireccionPostal()) {
			retorno.setDomicilioW8Correo(construyeDomicilioPostal());
		}
		
		retorno.setFormatoW8BEN(formatoW8BEN);
		
		return retorno;
	}
	
	

	public boolean validaFechaNacimiento() {
		if(  getFechaNacimientoDia()!= null &&
				getFechaNacimientoMes()!= null &&
				getFechaNacimientoAno()!= null ) {
			String mes = getFechaNacimientoMes()<10?"0"+getFechaNacimientoMes().toString():getFechaNacimientoMes().toString();
			String dia = getFechaNacimientoDia()<10?"0"+getFechaNacimientoDia().toString():getFechaNacimientoDia().toString();
			Date fechanac=null;
			try {
				 fechanac=df.parse(mes+"/"+dia+"/"+getFechaNacimientoAno().toString());
			} catch (ParseException e) {
				setFechaNacimiento(null);			
				return false;
			}
			setFechaNacimiento(fechanac);
			return true;
		}else{
			setFechaNacimiento(null);
			return false;
		}
		
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

		FormatoW8BEN formatoW8BEN = beneficiario.getFormatoW8BEN();

		Field3W8BEN field3W8BEN = formatoW8BEN.getField3();
		field3W8BEN.setIdCampo(tipoPropietario);
		formatoW8BEN.setField3(field3W8BEN);

		formatoW8BEN.setField9OptionA(line9a1);
		if(line9a2 !=null && "W8BEN2014".equals(beneficiario.getTipoFormato())){
			formatoW8BEN.setField9OptionACountry(line9a2);
		}else {
			formatoW8BEN.setField9OptionACountry(getPais());
		}
		formatoW8BEN.setField9OptionB(line9b);
		formatoW8BEN.setField9OptionC(line9c);
		formatoW8BEN.setField9OptionD(line9d);
		formatoW8BEN.setField9OptionE(line9e);
		if(line10p1 != null){
			formatoW8BEN.setField10Article(line10p1.toUpperCase());
			formatoW8BEN.setDisabledSection10(false);
		}else{
			formatoW8BEN.setField10Article(line10p1);
		}
		if(line10p2 != null){
			formatoW8BEN.setField10Rate(line10p2.toUpperCase());
		}else{
			formatoW8BEN.setField10Rate(line10p2);
		}
		if(line10p3 != null){
			formatoW8BEN.setField10Type(line10p3.toUpperCase());
		}else{
			formatoW8BEN.setField10Article(line10p3);
		}
		if(line10p4 != null){
			formatoW8BEN.setField10Reasons(line10p4.toUpperCase());
		}else{
			formatoW8BEN.setField10Article(line10p4);
		}
		formatoW8BEN.setField11(line11);
		formatoW8BEN.setCapacityActing(capacityActing);
		formatoW8BEN.setDisabledDireccionPostal(isDisabledDireccionPostal());
		formatoW8BEN.setDisabledreferenceNumber(disabledreferenceNumber);
		formatoW8BEN.setDisabledUsIdNumber(disabledUsIdNumber);
		formatoW8BEN.setDisabledSection10(disabledSection10);
		formatoW8BEN.setForeignTaxIdNumb(rfc);
		if(this.signer == null && beneficiario.getNombreGeneral() != null){
			formatoW8BEN.setSigner(beneficiario.getNombreGeneral().toUpperCase());
		}else if(this.signer !=null){
			formatoW8BEN.setSigner(this.signer.toUpperCase());
		}
		formatoW8BEN.setFechaNacimiento(fechaNacimiento);
		if(!disabledUsIdNumber) {
			formatoW8BEN.setTaxpayerIdNumb(taxPayerIdNumber);
			formatoW8BEN.setTipoTaxIdNumb(typeTaxPayerIdNumber);
		}
		if(!disabledreferenceNumber || !campos.isReferenceNumbers()) {
			formatoW8BEN.setReferenceNumbers(numeroReferencia);
		}

		beneficiario.setDomicilioW8Normal(construyeDomicilioNormal(beneficiario.getDomicilioW8Normal()));
		if(!isDisabledDireccionPostal()) {
            if(beneficiario.getDomicilioW8Correo() == null) {
                beneficiario.setDomicilioW8Correo(new Domicilio());
            }
			beneficiario.setDomicilioW8Correo(construyeDomicilioPostal(beneficiario.getDomicilioW8Correo()));
		}

		beneficiario.setFormatoW8BEN(formatoW8BEN);

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
	
	public void construyeBean(Beneficiario beneficiario, boolean llenaCamposVacios) {
		super.construyeBean(beneficiario, llenaCamposVacios);
		
		if(beneficiario.getFormatoW8BEN() != null){
			rfcBeneficiario = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
		}
		descTipoBeneficiario = beneficiario.getFormatoW8BEN().getField3().getDescripcion();
		
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8BEN(custodio, idTipoBeneficiario);
		
		tipoPropietario = beneficiario.getFormatoW8BEN().getField3().getIdCampo();
		descripcionTipoPropietario = beneficiario.getFormatoW8BEN().getField3().getDescripcion();
		if( StringUtils.isNotBlank(descripcionTipoPropietario) ) {
			descripcionTipoPropietario = descripcionTipoPropietario.toUpperCase();
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
		
		taxPayerIdNumber = beneficiario.getFormatoW8BEN().getTaxpayerIdNumb();
		typeTaxPayerIdNumber = beneficiario.getFormatoW8BEN().getTipoTaxIdNumb();
		rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
		numeroReferencia = beneficiario.getFormatoW8BEN().getReferenceNumbers();
		
		construyeReglas();
		
		line9a1 = beneficiario.getFormatoW8BEN().getField9OptionA();
		line9a2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
		line9b = beneficiario.getFormatoW8BEN().getField9OptionB();
		line9c = beneficiario.getFormatoW8BEN().getField9OptionC();
		line9d = beneficiario.getFormatoW8BEN().getField9OptionD();
		line9e = beneficiario.getFormatoW8BEN().getField9OptionE();
		
		line10p1 = beneficiario.getFormatoW8BEN().getField10Article();
		line10p2 = beneficiario.getFormatoW8BEN().getField10Rate();
		line10p3 = beneficiario.getFormatoW8BEN().getField10Type();
		line10p4 = beneficiario.getFormatoW8BEN().getField10Reasons();
		
		line11 = beneficiario.getFormatoW8BEN().getField11();
		
		capacityActing = beneficiario.getFormatoW8BEN().getCapacityActing();
		this.signer = beneficiario.getFormatoW8BEN().getSigner();
		this.fechaNacimiento = beneficiario.getFormatoW8BEN().getFechaNacimiento();
		if(this.fechaNacimiento != null){
			String[] fechanac = df.format(this.fechaNacimiento).split("/");
			setFechaNacimientoMes(Integer.valueOf(fechanac[0]));
			setFechaNacimientoDia(Integer.valueOf(fechanac[1]));
			setFechaNacimientoAno(Integer.valueOf(fechanac[2]));
		}		
		if(beneficiario.getFormatoW8BEN().getField10Article() == null){
			beneficiario.getFormatoW8BEN().setDisabledSection10(true);
			this.disabledSection10=true;
		}
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
		rfc = StringUtils.isNotBlank(rfc) ? rfc : BEN_CADENA_VACIA;
		numeroReferencia = StringUtils.isNotBlank(numeroReferencia) ? numeroReferencia : BEN_CADENA_VACIA;
		
		line9a2 = StringUtils.isNotBlank(line9a2) ? line9a2 : BEN_CADENA_VACIA;
		
		line10p1 = StringUtils.isNotBlank(line10p1) ? line10p1 : BEN_CADENA_VACIA;
		line10p2 = StringUtils.isNotBlank(line10p2) ? line10p2 : BEN_CADENA_VACIA;
		line10p3 = StringUtils.isNotBlank(line10p3) ? line10p3 : BEN_CADENA_VACIA;
		line10p4 = StringUtils.isNotBlank(line10p4) ? line10p4 : BEN_CADENA_VACIA;
		
		capacityActing = StringUtils.isNotBlank(capacityActing) ? capacityActing : BEN_CADENA_VACIA;
	}

	/**
	 * @return the lstTipoPropietario
	 */
	public List getLstTipoPropietario() {
		return lstTipoPropietario;
	}

	/**
	 * @param lstTipoPropietario the lstTipoPropietario to set
	 */
	public void setLstTipoPropietario(List lstTipoPropietario) {
		this.lstTipoPropietario = lstTipoPropietario;
	}

	/**
	 * @return the tipoPropietario
	 */
	public Long getTipoPropietario() {
		return tipoPropietario;
	}

	/**
	 * @param tipoPropietario the tipoPropietario to set
	 */
	public void setTipoPropietario(Long tipoPropietario) {
		this.tipoPropietario = tipoPropietario;
	}

	
	
	/**
	 * @param descripcionTipoPropietario the descripcionTipoPropietario to set
	 */
	public void setDescripcionTipoPropietario(String descripcionTipoPropietario) {
		this.descripcionTipoPropietario = descripcionTipoPropietario;
	}

	/**
	 * @return the descripcionTipoPropietario
	 */
	public String getDescripcionTipoPropietario() {
		return descripcionTipoPropietario;
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
	 * @param paisResidencial the paisResidencial to set
	 */
	public void setPaisResidencial(String paisResidencial) {
		this.paisResidencial = paisResidencial;
	}

	/**
	 * @return the paisResidencial
	 */
	public String getPaisResidencial() {
		return paisResidencial;
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
	 * @param paisPostal the paisPostal to set
	 */
	public void setPaisPostal(String paisPostal) {
		this.paisPostal = paisPostal;
	}

	/**
	 * @return the paisPostal
	 */
	public String getPaisPostal() {
		return paisPostal;
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
	 * @param numeroReferencia the numeroReferencia to set
	 */
	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}

	/**
	 * @return the numeroReferencia
	 */
	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	/**
	 * @param lstTaxPayerIdNumber the lstTaxPayerIdNumber to set
	 */
	public void setLstTaxPayerIdNumber(List lstTaxPayerIdNumber) {
		this.lstTaxPayerIdNumber = lstTaxPayerIdNumber;
	}

	/**
	 * @return the lstTaxPayerIdNumber
	 */
	public List getLstTaxPayerIdNumber() {
		return lstTaxPayerIdNumber;
	}

	/**
	 * @param typeTaxPayerIdNumber the typeTaxPayerIdNumber to set
	 */
	public void setTypeTaxPayerIdNumber(Long typeTaxPayerIdNumber) {
		this.typeTaxPayerIdNumber = typeTaxPayerIdNumber;
	}

	/**
	 * @return the typeTaxPayerIdNumber
	 */
	public Long getTypeTaxPayerIdNumber() {
		return typeTaxPayerIdNumber;
	}

	/**
	 * @param line9a1 the line9a1 to set
	 */
	public void setLine9a1(Boolean line9a1) {
		this.line9a1 = line9a1;
	}

	/**
	 * @return the line9a1
	 */
	public Boolean getLine9a1() {
		return line9a1;
	}

	/**
	 * @param line9a2 the line9a2 to set
	 */
	public void setLine9a2(String line9a2) {
		this.line9a2 = line9a2;
	}

	/**
	 * @return the line9a2
	 */
	public String getLine9a2() {
		return line9a2;
	}

	/**
	 * @param line9b the line9b to set
	 */
	public void setLine9b(Boolean line9b) {
		this.line9b = line9b;
	}

	/**
	 * @return the line9b
	 */
	public Boolean getLine9b() {
		return line9b;
	}

	/**
	 * @param line9c the line9c to set
	 */
	public void setLine9c(Boolean line9c) {
		this.line9c = line9c;
	}

	/**
	 * @return the line9c
	 */
	public Boolean getLine9c() {
		return line9c;
	}

	/**
	 * @param line9d the line9d to set
	 */
	public void setLine9d(Boolean line9d) {
		this.line9d = line9d;
	}

	/**
	 * @return the line9d
	 */
	public Boolean getLine9d() {
		return line9d;
	}

	/**
	 * @param line9e the line9e to set
	 */
	public void setLine9e(Boolean line9e) {
		this.line9e = line9e;
	}

	/**
	 * @return the line9e
	 */
	public Boolean getLine9e() {
		return line9e;
	}

	/**
	 * @param line10p1 the line10p1 to set
	 */
	public void setLine10p1(String line10p1) {
		this.line10p1 = line10p1;
	}

	/**
	 * @return the line10p1
	 */
	public String getLine10p1() {
		return line10p1;
	}

	/**
	 * @param line10p2 the line10p2 to set
	 */
	public void setLine10p2(String line10p2) {
		this.line10p2 = line10p2;
	}

	/**
	 * @return the line10p2
	 */
	public String getLine10p2() {
		return line10p2;
	}

	/**
	 * @param line10p3 the line10p3 to set
	 */
	public void setLine10p3(String line10p3) {
		this.line10p3 = line10p3;
	}

	/**
	 * @return the line10p3
	 */
	public String getLine10p3() {
		return line10p3;
	}

	/**
	 * @param line10p4 the line10p4 to set
	 */
	public void setLine10p4(String line10p4) {
		this.line10p4 = line10p4;
	}

	/**
	 * @return the line10p4
	 */
	public String getLine10p4() {
		return line10p4;
	}

	/**
	 * @param line11 the line11 to set
	 */
	public void setLine11(Boolean line11) {
		this.line11 = line11;
	}

	/**
	 * @return the line11
	 */
	public Boolean getLine11() {
		return line11;
	}

	/**
	 * @param capacityActing the capacityActing to set
	 */
	public void setCapacityActing(String capacityActing) {
		this.capacityActing = capacityActing;
	}

	/**
	 * @return the capacityActing
	 */
	public String getCapacityActing() {
		return capacityActing;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(CamposFormatoW8BEN campos) {
		this.campos = campos;
	}

	/**
	 * @return the campos
	 */
	public CamposFormatoW8BEN getCampos() {
		return campos;
	}

	/**
	 * @param disabledUsIdNumber the disabledUsIdNumber to set
	 */
	public void setDisabledUsIdNumber(boolean disabledUsIdNumber) {
		this.disabledUsIdNumber = disabledUsIdNumber;
	}

	/**
	 * @return the disabledUsIdNumber
	 */
	public boolean isDisabledUsIdNumber() {
		return disabledUsIdNumber;
	}

	/**
	 * @param disabledreferenceNumber the disabledreferenceNumber to set
	 */
	public void setDisabledreferenceNumber(boolean disabledreferenceNumber) {
		this.disabledreferenceNumber = disabledreferenceNumber;
	}

	/**
	 * @return the disabledreferenceNumber
	 */
	public boolean isDisabledreferenceNumber() {
		return disabledreferenceNumber;
	}
	
	/**
	 * @return the disabledSection10
	 */
	public boolean isDisabledSection10() {
		return disabledSection10;
	}

	/**
	 * @param disabledSection10 the disabledSection10 to set
	 */
	public void setDisabledSection10(boolean disabledSection10) {
		this.disabledSection10 = disabledSection10;
	}

	/**
	 * @return the signer
	 */
	public String getSigner() {
		return signer;
	}

	/**
	 * @param signer the signer to set
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}

	/**
	 * @return the fechaNacimientoDia
	 */
	public Integer getFechaNacimientoDia() {
		return fechaNacimientoDia;
	}

	/**
	 * @param fechaNacimientoDia the fechaNacimientoDia to set
	 */
	public void setFechaNacimientoDia(Integer fechaNacimientoDia) {
		this.fechaNacimientoDia = fechaNacimientoDia;
	}

	/**
	 * @return the fechaNacimientoMes
	 */
	public Integer getFechaNacimientoMes() {
		return fechaNacimientoMes;
	}

	/**
	 * @param fechaNacimientoMes the fechaNacimientoMes to set
	 */
	public void setFechaNacimientoMes(Integer fechaNacimientoMes) {
		this.fechaNacimientoMes = fechaNacimientoMes;
	}

	/**
	 * @return the fechaNacimientoAno
	 */
	public Integer getFechaNacimientoAno() {
		return fechaNacimientoAno;
	}

	/**
	 * @param fechaNacimientoAno the fechaNacimientoAno to set
	 */
	public void setFechaNacimientoAno(Integer fechaNacimientoAno) {
		this.fechaNacimientoAno = fechaNacimientoAno;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
