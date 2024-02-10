/**
 * 
 */
package com.indeval.portaldali.middleware.formatosw;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoMILA;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoMilaArchivos;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileDocumentoUpload;

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
public class FormaMILA extends FormaGeneral implements Constantes {
	
	/** Log de clase. */
	
	/** Numero de identificacion **/
	private String numeroIdentificacion;
	/**Desahabilita el culculo del indentificador MILA, los custodios (id_tipo 03) tienen que proporcionarlo**/
	private boolean deshabilitaCalculoIdentificacion;
	/**Calcular el numero de identificacion, indica si el usuario prefiere que se calcule**/
	private boolean calculaNumeroIdentificacion;
	/**Codigo MILA del pais para la nacionalida del inversionista**/
	private Integer paisNacionalidad;
	/**Pais direccion**/
	private Integer paisDireccion;
	/**Sector economico **/
	private Long sectorEconomico;
	/**Tipo de documento**/
	private Long tipoDocumento;
	/**Numero de documento de identidad**/
	private String numeroDocumento;
	/**Correo email**/
	private String email;
	/**Telegono**/
	private String telefono;
	/**Fax**/
	private String fax;
	/**Caracter de la entidad **/
	private Long caracterEntidad;
	/** Codigo del estado **/
	private Long codigoEstadoEntidad;
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
	/** Numero de refrencia **/
	private String numeroReferencia;
	/** Rfc para las personas morales**/
	private String rfc;
	/**Indica si es indeval**/
	private boolean indeval;
	/** Documento uno de indentidad **/
	private FileDocumentoUpload documentoUno;
	/** Documento dos de indentidad **/
	private FileDocumentoUpload documentoDos;
	/** Nombre del documento, cuando seleccionan otros **/
	private String nombreDocumento;
	
	
	public FormaMILA() {
		super();
	}
	
	public FormaMILA( Long idCuentaNombrada, Long idTipoBeneficiario, String nombreTipoBeneficiario, String idFolioInstitucion, boolean isIndeval) {
		super(idCuentaNombrada, idTipoBeneficiario, null);
		setIndeval(isIndeval);
		construyeReglas(nombreTipoBeneficiario, idFolioInstitucion);
	}
	
	private void construyeReglas(String nombreTipoBeneficiario, String idFolioInstitucion) {
		//Se valida si es una persona física (NATURAL para MILA) o es una persona MORAL (JURIDICA)
		String nombreFomato = nombreTipoBeneficiario.toUpperCase();
		if(nombreFomato.contains("FISICA") || nombreFomato.contains("NATURAL")){
			setPersonaFisica(true);
		}
		else{
			setPersonaFisica(false);
		}
		//Es un participante extranjero el cual forzozamente debe indicar el numero de inversionista MILA
		if(idFolioInstitucion!=null && idFolioInstitucion.startsWith("03")){
			deshabilitaCalculoIdentificacion = true;
			calculaNumeroIdentificacion = false;
		}
		else if(indeval){
			deshabilitaCalculoIdentificacion = false;
			calculaNumeroIdentificacion = true;
		}
		else{
			deshabilitaCalculoIdentificacion = true;
			calculaNumeroIdentificacion = true;
		}
		construyeReglas();
	}
	
	private void construyeReglas(){
		if(personaFisica){
			setRazonSocial(null);
		}
		else{
			setNombre(null);
			setApellidoMaterno(null);
			setApellidoPaterno(null);
		}
		if(calculaNumeroIdentificacion){
			setNumeroIdentificacion(null);
		}
	}
	
	public Beneficiario construyeBO() {
		Beneficiario retorno = new Beneficiario();
		construyeReglas();
		construyeBOBasico(retorno);
		retorno.setDomicilioMILA(construyeDomicilioNormal());
		
		FormatoMILA forma  = new FormatoMILA();
		
		if(this.caracterEntidad!=null && this.caracterEntidad!=-1L){
			forma.setCaracterEntidad(new MILATipoEmpresa(this.caracterEntidad));
		}
		if(this.sectorEconomico!=null && this.sectorEconomico!=-1L){
			forma.setSectorEconomico(new MILASectorEconomico(this.sectorEconomico));
		}
		if(this.tipoDocumento!=null && this.tipoDocumento!=-1L){
			forma.setTipoDocumentoIndentidad(new MILATipoDocumento(this.tipoDocumento));
		}
		if(this.codigoEstadoEntidad!=null && this.codigoEstadoEntidad!=-1L){
			forma.setCodigoEstadoEntidad(new MILACodigoDepartamento(this.codigoEstadoEntidad));
		}
		if(this.paisNacionalidad!=null && this.paisNacionalidad!=-1L){
			forma.setPaisNacionalidad(new PaisInt(this.paisNacionalidad));
		}
		if(this.paisDireccion!=null && this.paisDireccion!=-1L){
			forma.setPaisDireccion(new PaisInt(this.paisDireccion));
			forma.setPaisResidencia(new PaisInt(this.paisDireccion));
		}
		forma.setIdentificadorMILA(this.getNumeroIdentificacion());
		forma.setNumeroDocumento(this.numeroDocumento);
		forma.setNombreDocumento(this.nombreDocumento);
		if(PaisInt.ID_PAIS_MEXICO == paisDireccion){
			forma.setResidente(true);
		}
		else{
			forma.setResidente(false);
		}
		if(personaFisica){
			forma.setRfc(rfc);
		}
		forma.setDireccionEmail(this.email);
		forma.setTelefono(this.telefono);
		forma.setFax(this.fax);
		forma.setReferencia(this.getNumeroReferencia());
		
		FormatoMilaArchivos archivos = null;
		
		if(documentoUno!=null){
			archivos = new FormatoMilaArchivos();
			archivos.setComprobanteUno(documentoUno.getData());
			archivos.setNombreComprobanteUno(documentoUno.getName());
		}
		if(documentoDos != null){
			if(archivos ==null){
				archivos = new FormatoMilaArchivos();
			}
			archivos.setComprobanteDos(documentoDos.getData());
			archivos.setNombreComprobanteDos(documentoDos.getName());
		}
		forma.setArchivos(archivos);
	    
		retorno.setFormatoMILA(forma);
		
		return retorno;
	}
	
	private Domicilio construyeDomicilioNormal() {
		return construyeDomicilioNormal(new Domicilio());
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
	

    public Beneficiario construyeBO(Beneficiario beneficiario) {

		construyeReglas();
		construyeBOBasico(beneficiario);
		beneficiario.setDomicilioMILA(construyeDomicilioNormal(beneficiario.getDomicilioMILA()));
		
		FormatoMILA forma  = beneficiario.getFormatoMILA();
		
		if(this.caracterEntidad!=null && this.caracterEntidad!=-1L){
			forma.setCaracterEntidad(new MILATipoEmpresa(this.caracterEntidad));
		}
		if(this.sectorEconomico!=null && this.sectorEconomico!=-1L){
			forma.setSectorEconomico(new MILASectorEconomico(this.sectorEconomico));
		}
		if(this.tipoDocumento!=null && this.tipoDocumento!=-1L){
			forma.setTipoDocumentoIndentidad(new MILATipoDocumento(this.tipoDocumento));
		}
		if(this.codigoEstadoEntidad!=null && this.codigoEstadoEntidad!=-1L){
			forma.setCodigoEstadoEntidad(new MILACodigoDepartamento(this.codigoEstadoEntidad));
		}
		if(this.paisNacionalidad!=null && this.paisNacionalidad!=-1L){
			forma.setPaisNacionalidad(new PaisInt(this.paisNacionalidad));
		}
		if(this.paisDireccion!=null && this.paisDireccion!=-1L){
			forma.setPaisDireccion(new PaisInt(this.paisDireccion));
			forma.setPaisResidencia(new PaisInt(this.paisDireccion));
		}
		forma.setIdentificadorMILA(this.getNumeroIdentificacion());
		forma.setNumeroDocumento(this.numeroDocumento);
		forma.setNombreDocumento(this.nombreDocumento);
		if(PaisInt.ID_PAIS_MEXICO == paisDireccion){
			forma.setResidente(true);
		}
		else{
			forma.setResidente(false);
		}
		forma.setRfc(rfc);
		forma.setDireccionEmail(this.email);
		forma.setTelefono(this.telefono);
		forma.setFax(this.fax);
		forma.setReferencia(this.getNumeroReferencia());
		
		FormatoMilaArchivos archivos = beneficiario.getFormatoMILA().getArchivos();
		
		if(archivos ==null){
			archivos = new FormatoMilaArchivos();
		}
		
		if(documentoUno!=null){
			archivos.setComprobanteUno(documentoUno.getData());
			archivos.setNombreComprobanteUno(documentoUno.getName());
		}
		if(documentoDos != null){
			archivos.setComprobanteDos(documentoDos.getData());
			archivos.setNombreComprobanteDos(documentoDos.getName());
		}
		forma.setArchivos(archivos);
		
		beneficiario.setFormatoMILA(forma);
		
		return beneficiario;
	}

	public void construyeBean(Beneficiario beneficiario, boolean llenaCamposVacios) {
		super.construyeBean(beneficiario, llenaCamposVacios);
		
		descTipoBeneficiario = beneficiario.getTipoBeneficiario().getDescTipoBeneficiario();
		if(descTipoBeneficiario.contains("FISICA") || descTipoBeneficiario.contains("NATURAL")){
			setPersonaFisica(true);
		}
		else{
			setPersonaFisica(false);
		}
		rfc = beneficiario.getFormatoMILA().getRfc();
		numeroReferencia = beneficiario.getFormatoMILA().getReferencia();
		numeroIdentificacion = beneficiario.getFormatoMILA().getIdentificadorMILA();
		numeroDocumento = beneficiario.getFormatoMILA().getNumeroDocumento();
		nombreDocumento = beneficiario.getFormatoMILA().getNombreDocumento();
		email =  beneficiario.getFormatoMILA().getDireccionEmail();
		telefono =  beneficiario.getFormatoMILA().getTelefono();
		fax = beneficiario.getFormatoMILA().getFax();
		
		if(beneficiario.getDomicilioMILA() != null) {
			calleResidencial = beneficiario.getDomicilioMILA().getStreet();
			numeroExteriorResidencial =  beneficiario.getDomicilioMILA().getOuterNumber();
			numeroInteriorResidencial =  beneficiario.getDomicilioMILA().getInteriorNumber();
			codigoPostalResidencial = beneficiario.getDomicilioMILA().getPostalCode();
			cityTownResidencial = beneficiario.getDomicilioMILA().getCityTown();
			stateProvinceResidencial = beneficiario.getDomicilioMILA().getStateProvince();
			paisResidencial = beneficiario.getDomicilioMILA().getCountry();
		}
		paisNacionalidad = beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais();
		paisDireccion = beneficiario.getFormatoMILA().getPaisDireccion().getIdPais();
		
		tipoDocumento = beneficiario.getFormatoMILA().getTipoDocumentoIndentidad().getIdTipoDocumento();
		if( beneficiario.getFormatoMILA().getCodigoEstadoEntidad()!=null){
			codigoEstadoEntidad = beneficiario.getFormatoMILA().getCodigoEstadoEntidad().getIdCodigoEstado();
		}
		if(beneficiario.getFormatoMILA().getCaracterEntidad()!=null){
			caracterEntidad = beneficiario.getFormatoMILA().getCaracterEntidad().getIdTipoEmpresa();
		}
		if(beneficiario.getFormatoMILA().getSectorEconomico()!=null){
			sectorEconomico = beneficiario.getFormatoMILA().getSectorEconomico().getIdSectorEconomico();
		}
		if(beneficiario.getFormatoMILA().getArchivos()!=null){
			if(beneficiario.getFormatoMILA().getArchivos().getComprobanteUno()!=null && beneficiario.getFormatoMILA().getArchivos().getNombreComprobanteUno()!=null){
				documentoUno = new FileDocumentoUpload();
				documentoUno.setData(beneficiario.getFormatoMILA().getArchivos().getComprobanteUno());
				documentoUno.setName(beneficiario.getFormatoMILA().getArchivos().getNombreComprobanteUno());
				documentoUno.setLength(documentoUno.getData().length);
			}
			
			if(beneficiario.getFormatoMILA().getArchivos().getComprobanteDos()!=null && beneficiario.getFormatoMILA().getArchivos().getNombreComprobanteDos()!=null){
				documentoDos = new FileDocumentoUpload();
				documentoDos.setData(beneficiario.getFormatoMILA().getArchivos().getComprobanteDos());
				documentoDos.setName(beneficiario.getFormatoMILA().getArchivos().getNombreComprobanteDos());
				documentoDos.setLength(documentoDos.getData().length);
			}
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
	 * @return
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public boolean isDeshabilitaCalculoIdentificacion() {
		return deshabilitaCalculoIdentificacion;
	}

	public void setDeshabilitaCalculoIdentificacion(
			boolean deshabilitaCalculoIdentificacion) {
		this.deshabilitaCalculoIdentificacion = deshabilitaCalculoIdentificacion;
	}

	public boolean isCalculaNumeroIdentificacion() {
		return calculaNumeroIdentificacion;
	}

	public void setCalculaNumeroIdentificacion(boolean calculaNumeroIdentificacion) {
		this.calculaNumeroIdentificacion = calculaNumeroIdentificacion;
	}

	public Integer getPaisNacionalidad() {
		return paisNacionalidad;
	}

	public void setPaisNacionalidad(Integer paisNacionalidad) {
		this.paisNacionalidad = paisNacionalidad;
	}
	public Long getSectorEconomico() {
		return sectorEconomico;
	}

	public void setSectorEconomico(Long sectorEconomico) {
		this.sectorEconomico = sectorEconomico;
	}

	public Long getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getCaracterEntidad() {
		return caracterEntidad;
	}

	public void setCaracterEntidad(Long caracterEntidad) {
		this.caracterEntidad = caracterEntidad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getPaisDireccion() {
		return paisDireccion;
	}

	public void setPaisDireccion(Integer paisDireccion) {
		this.paisDireccion = paisDireccion;
	}
	/**
	 * @return the codigoEstadoEntidad
	 */
	public Long getCodigoEstadoEntidad() {
		return codigoEstadoEntidad;
	}

	/**
	 * @param codigoEstadoEntidad the codigoEstadoEntidad to set
	 */
	public void setCodigoEstadoEntidad(Long codigoEstadoEntidad) {
		this.codigoEstadoEntidad = codigoEstadoEntidad;
	}

	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}

	public boolean isIndeval() {
		return indeval;
	}

	public void setIndeval(boolean indeval) {
		this.indeval = indeval;
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
	 * @return the documentoUno
	 */
	public FileDocumentoUpload getDocumentoUno() {
		return documentoUno;
	}

	/**
	 * @param documentoUno the documentoUno to set
	 */
	public void setDocumentoUno(FileDocumentoUpload documentoUno) {
		this.documentoUno = documentoUno;
	}

	/**
	 * @return the documentoDos
	 */
	public FileDocumentoUpload getDocumentoDos() {
		return documentoDos;
	}

	/**
	 * @param documentoDos the documentoDos to set
	 */
	public void setDocumentoDos(FileDocumentoUpload documentoDos) {
		this.documentoDos = documentoDos;
	}

	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * @param nombreDocumento the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
}