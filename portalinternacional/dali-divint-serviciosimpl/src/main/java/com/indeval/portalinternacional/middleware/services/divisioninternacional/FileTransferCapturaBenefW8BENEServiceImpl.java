/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.getColumnAsString;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.transformaCadena;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.W8BENEUtil;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.FileTransferBenefConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo4BENE;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo5BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BENE;

/**
 * Servicio de negocio para los procesos de file transfer de formatos W8BEN-E.
 * 
 * @author Pablo Balderas
 *
 */
public class FileTransferCapturaBenefW8BENEServiceImpl extends FileTransferCapturaBenefServiceImpl<FileTransferFormaW8BENE> {

	/** Servicio para parsear el formato a xml y viceversa */
	private FormatoW8Service formatoW8Service;
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#guardaBeneficiarios(java.lang.String, java.lang.Long)
	 */
	public int guardaBeneficiarios(String claveUsuario, Long tipoFormato) throws BusinessException {
		Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
		List<FileTransferFormaW8BENE> listaRegistrosCorrectos = null;
		int numeroBeneficiariosGuardados = 0;
		listaRegistrosCorrectos =  consultaRegistrosCorrectos(claveUsuario);
		Beneficiario beneficiario = null;
		if(tipoFormato == Constantes.FORMATO_W8BENE){
			if(listaRegistrosCorrectos != null && !listaRegistrosCorrectos.isEmpty()){
				cuentaConRegistrosCorrectos = Boolean.TRUE;
				for(FileTransferFormaW8BENE fileTransferFormaW8BENE: listaRegistrosCorrectos){
					Long idInstitucion = fileTransferFormaW8BENE.getIdInstitucion();
					beneficiario = obtenerBeneficiario(fileTransferFormaW8BENE );					
					getControlBeneficiariosService().insertaBeneficiario(beneficiario,idInstitucion);									
					numeroBeneficiariosGuardados++;
				}
			}
			eliminaCargaExistente(claveUsuario,cuentaConRegistrosCorrectos);
		}				
		return numeroBeneficiariosGuardados;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefServiceImpl#convertRowToFileTransferForma(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	@Override
	public FileTransferFormaW8BENE convertRowToFileTransferForma(HSSFRow row) {
		FileTransferFormaW8BENE forma = new FileTransferFormaW8BENE();
		forma.setTipoFormato(transformaCadena(getColumnAsString(row.getCell(0))));
		forma.setTipoBeneficiario(transformaCadena(getColumnAsString(row.getCell(1))));
		forma.setClaveCustodio(transformaCadena(getColumnAsString(row.getCell(2))));
		forma.setClaveInstitucion(transformaCadena(getColumnAsString(row.getCell(3))));
		forma.setFechaFormato(transformaCadena(getColumnAsString(row.getCell(4))));
		forma.setEstado(transformaCadena(getColumnAsString(row.getCell(5))));
		forma.setCampo1(transformaCadena(getColumnAsString(row.getCell(6))));
		forma.setCampo2(transformaCadena(getColumnAsString(row.getCell(7))));
		forma.setCampo3(transformaCadena(getColumnAsString(row.getCell(8))));
		forma.setCampo4a(transformaCadena(getColumnAsString(row.getCell(9))));
		forma.setCampo4b(transformaCadena(getColumnAsString(row.getCell(10))));
		forma.setCampo5(transformaCadena(getColumnAsString(row.getCell(11))));
		forma.setCampo6a(transformaCadena(getColumnAsString(row.getCell(12))));
		forma.setCampo6b(transformaCadena(getColumnAsString(row.getCell(13))));
		forma.setCampo6c(transformaCadena(getColumnAsString(row.getCell(14))));
		forma.setCampo7a(transformaCadena(getColumnAsString(row.getCell(15))));
		forma.setCampo7b(transformaCadena(getColumnAsString(row.getCell(16))));
		forma.setCampo7c(transformaCadena(getColumnAsString(row.getCell(17))));
		forma.setCampo8(transformaCadena(getColumnAsString(row.getCell(18))));
		forma.setCampo9a(transformaCadena(getColumnAsString(row.getCell(19))));
		forma.setCampo9b(transformaCadena(getColumnAsString(row.getCell(20))));
		forma.setCampo10(transformaCadena(getColumnAsString(row.getCell(21))));
		forma.setCampo11a(transformaCadena(getColumnAsString(row.getCell(22))));
		forma.setCampo11b(transformaCadena(getColumnAsString(row.getCell(23))));
		forma.setCampo11c(transformaCadena(getColumnAsString(row.getCell(24))));
		forma.setCampo11d(transformaCadena(getColumnAsString(row.getCell(25))));
		forma.setCampo11e(transformaCadena(getColumnAsString(row.getCell(26))));
		forma.setCampo12a(transformaCadena(getColumnAsString(row.getCell(27))));
		forma.setCampo12b(transformaCadena(getColumnAsString(row.getCell(28))));
		forma.setCampo12c(transformaCadena(getColumnAsString(row.getCell(29))));
		forma.setCampo13(transformaCadena(getColumnAsString(row.getCell(30))));
		forma.setCampo14a(transformaCadena(getColumnAsString(row.getCell(31))));
		forma.setCampo14b(transformaCadena(getColumnAsString(row.getCell(32))));
		forma.setCampo14c(transformaCadena(getColumnAsString(row.getCell(33))));
		forma.setCampo15a(transformaCadena(getColumnAsString(row.getCell(34))));
		forma.setCampo15b(transformaCadena(getColumnAsString(row.getCell(35))));
		forma.setCampo15c(transformaCadena(getColumnAsString(row.getCell(36))));
		forma.setCampo15d(transformaCadena(getColumnAsString(row.getCell(37))));
		forma.setCampo15e(transformaCadena(getColumnAsString(row.getCell(38))));
		forma.setPrintName(transformaCadena(getColumnAsString(row.getCell(39))));
		forma.setAnexo1(transformaCadena(getColumnAsString(row.getCell(40))));
		forma.setAnexo2(transformaCadena(getColumnAsString(row.getCell(41))));
		forma.setAnexo3(transformaCadena(getColumnAsString(row.getCell(42))));
		forma.setAnexo4(transformaCadena(getColumnAsString(row.getCell(43))));
		forma.setAnexo5(transformaCadena(getColumnAsString(row.getCell(44))));
		forma.setAnexo6(transformaCadena(getColumnAsString(row.getCell(45))));
		return forma;
	}

	
	/**
	 * Método que obtiene un objeto Beneficiario de un objeto FileTransferFormaW8BENE.
	 * @param fileTransferFormaW8BENE Objeto que representa un registro del proceso de file transfer.
	 * @return Objeto Beneficiario con los datos obtenidos del proceso file transfer.
	 */
	private Beneficiario obtenerBeneficiario(FileTransferFormaW8BENE fileTransferForma) {
		Beneficiario beneficiario = null;
		//Valida que el objeto no sea nulo
		if(fileTransferForma != null) {
			FormaW8BENE forma = obtenerFormaW8BENE(fileTransferForma);
			beneficiario = forma.construyeBO();
			//Valida los apendices de la forma
    		W8BENEUtil.verificarPartesFormato(forma);
    		//Obtiene el xml del formato y lo coloca en el beneficiario
    		String formaXML = formatoW8Service.generarXmlCamposFormato(forma);
    		FormatoW8BENE formatoW8BENE = new FormatoW8BENE(
    			null, formaXML,
    			forma.getPartIcmp8(),
    			forma.getPartIcmp9b2(),
    			forma.getPartIcmp10(),
    			forma.getPartIcmp9a2(),
    			forma.getPrintName());
    		beneficiario.setFormatoW8BENE(formatoW8BENE);
    		//Coloca los domicilios y valida
    		beneficiario.setDomicilioW8Normal(obtenerDomicilio(fileTransferForma));
    		beneficiario.setDomicilioW8Correo(obtenerDomicilioCorreo(fileTransferForma));
    		W8BENEUtil.validarDomiciliosBeneficiario(beneficiario);
		}
		return beneficiario;
	}
	
	/**
	 * 
	 * @param fileTransferForma
	 * @return
	 */
	private FormaW8BENE obtenerFormaW8BENE(FileTransferFormaW8BENE fileTransferForma) {
		FormaW8BENE forma = null;
		if(fileTransferForma != null) {
			forma = new FormaW8BENE();
			forma.setFormato(BeneficiariosConstantes.FORMATO_W8_BEN_E);
			//Coloca el custodio, el tipo de beneficio y la institución
			forma.setCustodio(fileTransferForma.getIdCuentaNombrada());
			forma.setIdTipoBeneficiario(fileTransferForma.getIdTipoBeneficiario());
			forma.setIdInstitucion(fileTransferForma.getIdInstitucion());
			forma.setFechaFormato(fileTransferForma.getDateFechaFormato());
			forma.setFechaRegistro(new Date(System.currentTimeMillis()));
			if(BeneficiariosConstantes.ESTADO_ACTIVO.equals(fileTransferForma.getEstado())) {
				forma.setActivo(BeneficiariosConstantes.ID_ESTADO_ACTIVO);
			}
			else if(BeneficiariosConstantes.ESTADO_INACTIVO.equals(fileTransferForma.getEstado())) {
				forma.setActivo(BeneficiariosConstantes.ID_ESTADO_INACTIVO);
			}
			//Si el tipo de beneficio es diferente de OTROS(W8BEN-E), inicializamos las siguientes banderas de la forma
			if(! FileTransferBenefConstantes.OTROS_W8BENE.equals(fileTransferForma.getTipoBeneficiario())) {
				forma.setReadOnlyCmp14(true);
				forma.setPartIIIcmp14a(true);
				forma.setPartIIIcmp14c(true);
				forma.setPartIcmp9b1(true);
				forma.setReadOnlyCmp9b(true);
			}
			//Llena la parte I
			forma.setPartIcmp1(fileTransferForma.getCampo1());
			forma.setPartIcmp2(fileTransferForma.getCampo2());
			forma.setPartIcmp3(fileTransferForma.getCampo3());
			obtenerValorCampo4_a_k(forma, fileTransferForma);
			obtenerValorCampo4_l_m(forma, fileTransferForma.getCampo4b());
			obtenerValorCampo5(forma, fileTransferForma);

			if(StringUtils.isBlank(fileTransferForma.getCampo7a()) &&
					StringUtils.isBlank(fileTransferForma.getCampo7b()) &&
					StringUtils.isBlank(fileTransferForma.getCampo7c())) {
				forma.setInhabilitarPartIcmp7(true);
			}
			
			forma.setPartIcmp8(fileTransferForma.getCampo8());
			
			if(StringUtils.isNotBlank(fileTransferForma.getCampo9a())) {
				forma.setPartIcmp9a1(true);
				forma.setPartIcmp9a2(fileTransferForma.getCampo9a());
			}
			if(StringUtils.isNotBlank(fileTransferForma.getCampo9b())) {
				forma.setPartIcmp9b1(true);
				forma.setPartIcmp9b2(fileTransferForma.getCampo9b());
			}
			
			if(FileTransferBenefConstantes.CLEARSTREAM_BANKING.equals(fileTransferForma.getClaveCustodio())) {
				forma.setPartIcmp10(BeneficiariosConstantes.REF_CLEARSTREAM);
			}
			else if(FileTransferBenefConstantes.EUROCLEAR_BANK.equals(fileTransferForma.getClaveCustodio())) {
				forma.setPartIcmp10(BeneficiariosConstantes.REF_EUROCLEAR_BANK);
			}
			else {
				forma.setPartIcmp10(fileTransferForma.getCampo10());
			}
			
			//Llena la Parte II
			obtenerValorCampo11(forma, fileTransferForma);
			forma.setPartIIcmp12a(fileTransferForma.getCampo12a());
			forma.setPartIIcmp12b(fileTransferForma.getCampo12b());
			forma.setPartIIcmp12c(fileTransferForma.getCampo12c());
			forma.setPartIIcmp13(fileTransferForma.getCampo13());
			
			//Llena la Parte III
			forma.setPartIIIcmp14a(obtenerBooleano(fileTransferForma.getCampo14a()));
			forma.setPartIIIcmp14a1(fileTransferForma.getCampo2());
			forma.setPartIIIcmp14b(obtenerBooleano(fileTransferForma.getCampo14b()));
			forma.setPartIIIcmp14c(obtenerBooleano(fileTransferForma.getCampo14c()));
			forma.setPartIIIcmp15a(fileTransferForma.getCampo15a());
			forma.setPartIIIcmp15b(fileTransferForma.getCampo15b());
			if(StringUtils.equalsIgnoreCase(fileTransferForma.getCampo15a(), BeneficiariosConstantes.VALOR_DIVIDENDS)) {
				forma.setPartIIIcmp15c(BeneficiariosConstantes.DIVIDENDS);
				forma.setReadOnlyCmp15c(true);
			}
			else if(StringUtils.equalsIgnoreCase(fileTransferForma.getCampo15a(), BeneficiariosConstantes.VALOR_INTEREST)) {
				forma.setPartIIIcmp15c(BeneficiariosConstantes.INTEREST);
				forma.setReadOnlyCmp15c(true);
			}
			else {
				forma.setPartIIIcmp15c(fileTransferForma.getCampo15c());
				forma.setReadOnlyCmp15c(false);
			}
			if(FileTransferBenefConstantes.SIEFORE_AFORE.equals(fileTransferForma.getTipoBeneficiario())) {
				forma.setSIEFORE_AFORE(true);
				forma.setPartIIIcmp15d1(BeneficiariosConstantes.LEYENDA_AFORE_W8BENE_1);
				forma.setPartIIIcmp15d2(BeneficiariosConstantes.LEYENDA_AFORE_W8BENE_2);
			}
			else {
				forma.setPartIIIcmp15d1(fileTransferForma.getCampo15d());
				forma.setPartIIIcmp15d2(fileTransferForma.getCampo15e());
			}
			//Acepta los terminos y colocamos quien captura
			forma.setPartXXIX(true);
			forma.setPrintName(fileTransferForma.getPrintName());
		}
		return forma;
	}

	/**
	 * Obtiene el domicilio asociado al campo 6
	 * @param fileTransferForma Objeto de donde se extraen los datos
	 */
	private Domicilio obtenerDomicilio(FileTransferFormaW8BENE fileTransferForma) {
		Domicilio domicilio = new Domicilio();
		domicilio.setStreet(fileTransferForma.getCampo6a());
		domicilio.setCityTown(fileTransferForma.getCampo6b());
		domicilio.setCountry(fileTransferForma.getCampo6c());
		return domicilio;
	}

	/**
	 * Obtiene el domicilio asociado al campo 7
	 * @param fileTransferForma Objeto de donde se extraen los datos
	 */
	private Domicilio obtenerDomicilioCorreo(
			FileTransferFormaW8BENE fileTransferForma) {
		Domicilio domicilioCorreo = null;
		if(StringUtils.isNotBlank(fileTransferForma.getCampo7a()) &&
				StringUtils.isNotBlank(fileTransferForma.getCampo7b()) &&
				StringUtils.isNotBlank(fileTransferForma.getCampo7c())) {
			domicilioCorreo = new Domicilio();
			domicilioCorreo.setStreet(fileTransferForma.getCampo7a());
			domicilioCorreo.setCityTown(fileTransferForma.getCampo7b());
			domicilioCorreo.setCountry(fileTransferForma.getCampo7c());
		}
		return domicilioCorreo;
	}

	
	/**
	 * Obtiene el valor del campo 4 opciones a-k.
	 * @param forma Forma donde se va a setear el valor.
	 * @param campo4 Valor en el file transfer.
	 */
	private void obtenerValorCampo4_a_k(FormaW8BENE forma, FileTransferFormaW8BENE fileTransferForma) {
		
		forma.habilitarDeshabilitarOpcionesCampo4(true);
		
		if(FileTransferBenefConstantes.PERSONA_MORAL_EXTRANJERA_EUA.equals(fileTransferForma.getTipoBeneficiario())) {
			forma.setPartIcmp4a(true);
		}
		else if(FileTransferBenefConstantes.SIEFORE_AFORE.equals(fileTransferForma.getTipoBeneficiario())) {
			forma.setPartIcmp4j(true);
		}
		else if(FileTransferBenefConstantes.FIDEICOMISO_COMPLEJO.equals(fileTransferForma.getTipoBeneficiario())) {
			forma.setPartIcmp4f(true);
		}
		else if(FileTransferBenefConstantes.OTROS_W8BENE.equals(fileTransferForma.getTipoBeneficiario())) {
			forma.habilitarDeshabilitarOpcionesCampo4(false);
			//Obtiene el valor y lo coloca en la forma
			if(OpcionesCampo4BENE.A.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4a(true);
			}
			else if(OpcionesCampo4BENE.B.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4b(true);
			}
			else if(OpcionesCampo4BENE.C.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4c(true);
			}
			else if(OpcionesCampo4BENE.D.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4d(true);
			}
			else if(OpcionesCampo4BENE.E.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4e(true);
			}
			else if(OpcionesCampo4BENE.F.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4f(true);
			}
			else if(OpcionesCampo4BENE.G.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4g(true);
			}
			else if(OpcionesCampo4BENE.H.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4h(true);
			}
			else if(OpcionesCampo4BENE.I.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4i(true);
			}
			else if(OpcionesCampo4BENE.J.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4j(true);
			}
			else if(OpcionesCampo4BENE.K.toString().equals(fileTransferForma.getCampo4a())) {
				forma.setPartIcmp4k(true);
			}
		}
	}
	
	/**
	 * Obtiene el valor del campo 4 opciones l y m.
	 * @param forma Forma donde se va a setear el valor.
	 * @param campo4 Valor en el file transfer.
	 */
	private void obtenerValorCampo4_l_m(FormaW8BENE forma, String campo4) {
		if(StringUtils.isNotBlank(campo4)) {
			if(FileTransferBenefConstantes.YES.equalsIgnoreCase(campo4)) {
				forma.setPartIcmp4l(true);
				forma.setPartIcmp4m(false);
			}
			else {
				forma.setPartIcmp4l(false);
				forma.setPartIcmp4m(true);
			}
		}
		else {
			forma.setPartIcmp4l(false);
			forma.setPartIcmp4m(false);	
		}
	}
	
	/**
	 * Obtiene el valor del campo 5 y completa los apendices de acuerdo a la opción selecionada.
	 * @param forma Forma donde se setean los valores.
	 * @param fileTransferForma Objeto de donde se toman los datos.
	 */
	private void obtenerValorCampo5(FormaW8BENE forma, FileTransferFormaW8BENE fileTransferForma) {
		if(OpcionesCampo5BENE.A.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5a(true);
		}
		if(OpcionesCampo5BENE.B.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5b(true);
		}
		if(OpcionesCampo5BENE.C.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5c(true);
		}
		if(OpcionesCampo5BENE.D.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5d(true);
		}
		if(OpcionesCampo5BENE.E.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5e(true);
		}
		if(OpcionesCampo5BENE.F.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5f(true);
			forma.setPartIV(true);
			forma.setPartIVcmp16(fileTransferForma.getAnexo1());
			forma.setPartIVcmp17a(obtenerBooleano(fileTransferForma.getAnexo2()));
			forma.setPartIVcmp17b(obtenerBooleano(fileTransferForma.getAnexo3()));
		}
		else if(OpcionesCampo5BENE.G.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5g(true);
			forma.setPartV(true);
			forma.setPartVcmp18(true);
		}
		else if(OpcionesCampo5BENE.H.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5h(true);
			forma.setPartVI(true);
			forma.setPartVIcmp19(true);
		}
		else if(OpcionesCampo5BENE.I.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5i(true);
			forma.setPartVII(true);
			forma.setPartVIIcmp20(fileTransferForma.getAnexo1());
			forma.setPartVIIcmp21(obtenerBooleano(fileTransferForma.getAnexo2()));
		}
		else if(OpcionesCampo5BENE.J.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5j(true);
			forma.setPartVIII(true);
			forma.setPartVIIIcmp22(true);
		}
		else if(OpcionesCampo5BENE.K.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5k(true);
			forma.setPartIX(true);
			forma.setPartIXcmp23(true);
		}
		else if(OpcionesCampo5BENE.L.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5l(true);
			forma.setPartX(true);
			forma.setPartXcmp24a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXcmp24b(obtenerBooleano(fileTransferForma.getAnexo2()));
			forma.setPartXcmp24c(obtenerBooleano(fileTransferForma.getAnexo3()));
			forma.setPartXcmp24d(obtenerBooleano(fileTransferForma.getAnexo4()));
		}
		else if(OpcionesCampo5BENE.M.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5m(true);
			forma.setPartXI(true);
			forma.setPartXIcmp25a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXIcmp25b(obtenerBooleano(fileTransferForma.getAnexo2()));
			forma.setPartXIcmp25c(obtenerBooleano(fileTransferForma.getAnexo3()));
		}
		else if(OpcionesCampo5BENE.N.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5n(true);
			forma.setPartXII(true);
			forma.setPartXIIcmp26a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXIIcmp26b(fileTransferForma.getAnexo2());
			forma.setPartXIIcmp26c(fileTransferForma.getAnexo3());
			forma.setPartXIIcmp26d(fileTransferForma.getAnexo4());
		}
		else if(OpcionesCampo5BENE.O.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5o(true);
			forma.setPartXIII(true);
			forma.setPartXIIIcmp27(true);
		}
		else if(OpcionesCampo5BENE.P.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5p(true);
			forma.setPartXIV(true);
			forma.setPartXIVcmp28a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXIVcmp28b(obtenerBooleano(fileTransferForma.getAnexo2()));
		}
		else if(OpcionesCampo5BENE.Q.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5q(true);
			forma.setPartXV(true);
			forma.setPartXVcmp29a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXVcmp29b(obtenerBooleano(fileTransferForma.getAnexo2()));
			forma.setPartXVcmp29c(obtenerBooleano(fileTransferForma.getAnexo3()));
			forma.setPartXVcmp29d(obtenerBooleano(fileTransferForma.getAnexo4()));
			forma.setPartXVcmp29e(obtenerBooleano(fileTransferForma.getAnexo5()));
			forma.setPartXVcmp29f(obtenerBooleano(fileTransferForma.getAnexo6()));
		}
		else if(OpcionesCampo5BENE.R.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5r(true);
			forma.setPartXVI(true);
			forma.setPartXVIcmp30(true);
		}
		else if(OpcionesCampo5BENE.S.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5s(true);
			forma.setPartXVII(true);
			forma.setPartXVIIcmp31(true);
		}
		else if(OpcionesCampo5BENE.T.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5t(true);
			forma.setPartXVIII(true);
			forma.setPartXVIIIcmp32(true);
		}
		else if(OpcionesCampo5BENE.U.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5u(true);
			forma.setPartXIX(true);
			forma.setPartXIXcmp33a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXIXcmp33b(fileTransferForma.getAnexo2());
		}
		else if(OpcionesCampo5BENE.V.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5v(true);
			forma.setPartXX(true);
			forma.setPartXXcmp34a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXXcmp34b(fileTransferForma.getAnexo2());
		}
		else if(OpcionesCampo5BENE.W.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5w(true);
			forma.setPartXXI(true);
			forma.setPartXXIcmp35a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXXIcmp35b(fileTransferForma.getAnexo2());
		}
		else if(OpcionesCampo5BENE.X.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5x(true);
			forma.setPartXXII(true);
			forma.setPartXXIIcmp36(true);
		}
		else if(OpcionesCampo5BENE.Y.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5y(true);
			forma.setPartXXIII(true);
			forma.setPartXXIIIcmp37a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXXIIIcmp37b(fileTransferForma.getAnexo2());
			forma.setPartXXIIIcmp37c(obtenerBooleano(fileTransferForma.getAnexo3()));
			forma.setPartXXIIIcmp37d(fileTransferForma.getAnexo4());
			forma.setPartXXIIIcmp37e(fileTransferForma.getAnexo5());
		}
		else if(OpcionesCampo5BENE.Z.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5z(true);
			forma.setPartXXIV(true);
			forma.setPartXXIVcmp38(true);
		}
		else if(OpcionesCampo5BENE.AA.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5aa(true);
			forma.setPartXXV(true);
			forma.setPartXXVcmp39(true);
		}
		else if(OpcionesCampo5BENE.AB.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5ab(true);
			forma.setPartXXVI(true);
			forma.setPartXXVIcmp40a(obtenerBooleano(fileTransferForma.getAnexo1()));
			forma.setPartXXVIcmp40b(obtenerBooleano(fileTransferForma.getAnexo2()));
			forma.setPartXXVIcmp40c(obtenerBooleano(fileTransferForma.getAnexo3()));
		}
		else if(OpcionesCampo5BENE.AC.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5ac(true);
			forma.setPartXXVII(true);
			forma.setPartXXVIIcmp41(true);
		}
		else if(OpcionesCampo5BENE.AD.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5ad(true);
		}
		else if(OpcionesCampo5BENE.AE.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5ae(true);
			forma.setPartXXVIII(true);
			forma.setPartXXVIIIcmp42(fileTransferForma.getAnexo1());
			forma.setPartXXVIIIcmp43(obtenerBooleano(fileTransferForma.getAnexo2()));
		}
	}
	
	/**
	 * Obtiene el valor del campo 11.
	 * @param forma Forma donde se va a setear el valor.
	 * @param campo11 Valor en el file transfer.
	 */
	private void obtenerValorCampo11(FormaW8BENE forma, FileTransferFormaW8BENE fileTransferForma) {
		//Obtiene el valor y lo coloca en la forma
		forma.setPartIIcmp11a(obtenerBooleano(fileTransferForma.getCampo11a()));
		forma.setPartIIcmp11b(obtenerBooleano(fileTransferForma.getCampo11b()));
		forma.setPartIIcmp11c(obtenerBooleano(fileTransferForma.getCampo11c()));
		forma.setPartIIcmp11d(obtenerBooleano(fileTransferForma.getCampo11d()));
		forma.setPartIIcmp11e(obtenerBooleano(fileTransferForma.getCampo11e()));
	}
	
	/**
	 * Obtiene el valor booleano de la cadena que recibe como parametro.
	 * @param campo Parametro
	 * @return true si el campo no es nulo y es igual a la literal YES; false en caso contrario
	 */
	private boolean obtenerBooleano(String campo) {
		return StringUtils.isNotBlank(campo) && FileTransferBenefConstantes.YES.equalsIgnoreCase(campo);
	}

	/**
	 * Método para establecer el atributo formatoW8Service
	 * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
	 */
	public void setFormatoW8Service(FormatoW8Service formatoW8Service) {
		this.formatoW8Service = formatoW8Service;
	}
	
}
