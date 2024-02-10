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

import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.W8IMY2015Util;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.FileTransferBenefConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo4W8IMY;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo5W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8IMY2015;

/**
 * Servicio de negocio para los procesos de file transfer de formatos W8BEN-E.
 * 
 * @author Pablo Balderas
 *
 */
public class FileTransferCapturaBenefW8IMY2015ServiceImpl extends FileTransferCapturaBenefServiceImpl<FileTransferFormaW8IMY2015> {

	/** Servicio para parsear el formato a xml y viceversa */
	private FormatoW8Service formatoW8Service;
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#guardaBeneficiarios(java.lang.String, java.lang.Long)
	 */
	public int guardaBeneficiarios(String claveUsuario, Long tipoFormato) throws BusinessException {
		Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
		List<FileTransferFormaW8IMY2015> listaRegistrosCorrectos = null;
		int numeroBeneficiariosGuardados = 0;
		listaRegistrosCorrectos =  consultaRegistrosCorrectos(claveUsuario);
		Beneficiario beneficiario = null;
		if(tipoFormato == Constantes.FORMATO_W8IMY){
			if(listaRegistrosCorrectos != null && !listaRegistrosCorrectos.isEmpty()){
				cuentaConRegistrosCorrectos = Boolean.TRUE;
				for(FileTransferFormaW8IMY2015 fileTransferFormaW8IMY: listaRegistrosCorrectos){
					Long idInstitucion = fileTransferFormaW8IMY.getIdInstitucion();
					beneficiario = obtenerBeneficiario(fileTransferFormaW8IMY );					
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
	public FileTransferFormaW8IMY2015 convertRowToFileTransferForma(HSSFRow row) {
		FileTransferFormaW8IMY2015 forma = new FileTransferFormaW8IMY2015();
		forma.setTipoFormato(transformaCadena(getColumnAsString(row.getCell(0))));
		forma.setTipoBeneficiario(transformaCadena(getColumnAsString(row.getCell(1))));
		forma.setClaveCustodio(transformaCadena(getColumnAsString(row.getCell(2))));
		forma.setClaveInstitucion(transformaCadena(getColumnAsString(row.getCell(3))));
		forma.setFechaFormato(transformaCadena(getColumnAsString(row.getCell(4))));
		forma.setEstado(transformaCadena(getColumnAsString(row.getCell(5))));
		forma.setCampo1(transformaCadena(getColumnAsString(row.getCell(6))));
		forma.setCampo2(transformaCadena(getColumnAsString(row.getCell(7))));
		forma.setCampo3(transformaCadena(getColumnAsString(row.getCell(8))));
		forma.setCampo4(transformaCadena(getColumnAsString(row.getCell(9))));		
		forma.setCampo5(transformaCadena(getColumnAsString(row.getCell(10))));
		forma.setCampo6a(transformaCadena(getColumnAsString(row.getCell(11))));
		forma.setCampo6b(transformaCadena(getColumnAsString(row.getCell(12))));
		forma.setCampo6c(transformaCadena(getColumnAsString(row.getCell(13))));
		forma.setCampo7a(transformaCadena(getColumnAsString(row.getCell(14))));
		forma.setCampo7b(transformaCadena(getColumnAsString(row.getCell(15))));
		forma.setCampo7c(transformaCadena(getColumnAsString(row.getCell(16))));
		forma.setCampo8(transformaCadena(getColumnAsString(row.getCell(17))));
		forma.setCampo8a(transformaCadena(getColumnAsString(row.getCell(18))));
		forma.setCampo8b(transformaCadena(getColumnAsString(row.getCell(19))));
		forma.setCampo8c(transformaCadena(getColumnAsString(row.getCell(20))));
		forma.setCampo8d(transformaCadena(getColumnAsString(row.getCell(21))));
		forma.setCampo8e(transformaCadena(getColumnAsString(row.getCell(22))));
		forma.setCampo9(transformaCadena(getColumnAsString(row.getCell(23))));
		forma.setCampo10(transformaCadena(getColumnAsString(row.getCell(24))));
		forma.setCampo11a(transformaCadena(getColumnAsString(row.getCell(25))));
		forma.setCampo11b(transformaCadena(getColumnAsString(row.getCell(26))));
		forma.setCampo11c(transformaCadena(getColumnAsString(row.getCell(27))));
		forma.setCampo11d(transformaCadena(getColumnAsString(row.getCell(28))));
		forma.setCampo11e(transformaCadena(getColumnAsString(row.getCell(29))));
		forma.setCampo12a(transformaCadena(getColumnAsString(row.getCell(30))));
		forma.setCampo12b(transformaCadena(getColumnAsString(row.getCell(31))));
		forma.setCampo12c(transformaCadena(getColumnAsString(row.getCell(32))));
		forma.setCampo13(transformaCadena(getColumnAsString(row.getCell(33))));
		forma.setCertification(transformaCadena(getColumnAsString(row.getCell(34))));
/*		forma.setCampo4Anexo1(transformaCadena(getColumnAsString(row.getCell(34))));
		forma.setCampo4Anexo2(transformaCadena(getColumnAsString(row.getCell(35))));
		forma.setCampo4Anexo3(transformaCadena(getColumnAsString(row.getCell(36))));
		forma.setCampo4Anexo4(transformaCadena(getColumnAsString(row.getCell(37))));
		forma.setCampo4Anexo5(transformaCadena(getColumnAsString(row.getCell(38))));
		forma.setCampo4Anexo6(transformaCadena(getColumnAsString(row.getCell(39))));
		forma.setCampo4Anexo7(transformaCadena(getColumnAsString(row.getCell(40))));
		forma.setCampo4Anexo8(transformaCadena(getColumnAsString(row.getCell(41))));
		forma.setCampo4Anexo9(transformaCadena(getColumnAsString(row.getCell(42))));
		forma.setCampo4Anexo10(transformaCadena(getColumnAsString(row.getCell(43))));
		forma.setCampo4Anexo11(transformaCadena(getColumnAsString(row.getCell(44))));
		forma.setCampo5Anexo1(transformaCadena(getColumnAsString(row.getCell(45))));
		forma.setCampo5Anexo2(transformaCadena(getColumnAsString(row.getCell(46))));
		forma.setCampo5Anexo3(transformaCadena(getColumnAsString(row.getCell(47))));
		forma.setCampo5Anexo4(transformaCadena(getColumnAsString(row.getCell(48))));
		forma.setCampo5Anexo5(transformaCadena(getColumnAsString(row.getCell(49))));
		forma.setCampo5Anexo6(transformaCadena(getColumnAsString(row.getCell(50))));
*/
		return forma;
	}

	/**
	 * Método que obtiene un objeto Beneficiario de un objeto FileTransferFormaW8IMY2015.
	 * @param FileTransferFormaW8IMY2015 Objeto que representa un registro del proceso de file transfer.
	 * @return Objeto Beneficiario con los datos obtenidos del proceso file transfer.
	 */
	private Beneficiario obtenerBeneficiario(FileTransferFormaW8IMY2015 fileTransferForma) {
		Beneficiario beneficiario = null;
		//Valida que el objeto no sea nulo
		if(fileTransferForma != null) {
			FormaW8IMY2015 forma = obtenerFormaW8IMY2015(fileTransferForma);
			beneficiario = forma.construyeBO();
			//Valida los apendices de la forma
			W8IMY2015Util.validarPartesFormato(forma);
    		//Obtiene el xml del formato y lo coloca en el beneficiario
    		String formaXML = formatoW8Service.generarXmlCamposFormato(forma);
    		FormatoW8IMY2015 formatoW8IMY2015 = new FormatoW8IMY2015(null, formaXML,
        			((FormaW8IMY2015) forma).getPartIcmp8(),
        			((FormaW8IMY2015) forma).getPartIcmp10(),
        			((FormaW8IMY2015) forma).getPartIcmp9(),
        			((FormaW8IMY2015) forma).getPrintName());
        		beneficiario.setFormatoW8IMY2015(formatoW8IMY2015);
    		//Coloca los domicilios y valida
    		beneficiario.setDomicilioW8Normal(obtenerDomicilio(fileTransferForma));
    		beneficiario.setDomicilioW8Correo(obtenerDomicilioCorreo(fileTransferForma));
    		W8IMY2015Util.validarDomiciliosBeneficiario(beneficiario);
		}
		return beneficiario;
	}
	
	/**
	 * Obtiene un objeto FormaW8IMY2015 a partir de un objeto FileTransferFormaW8IMY2015.
	 * @param fileTransferForma Objeto para llenar la forma FormaW8IMY2015.
	 * @return Objeto FormaW8IMY2015 generado.
	 */
	private FormaW8IMY2015 obtenerFormaW8IMY2015(FileTransferFormaW8IMY2015 fileTransferForma) {
		FormaW8IMY2015 forma = null;
		if(fileTransferForma != null) {
			//Crea el formato con el id de cuenta nombrada del custodio, el tipo de beneficio y la institución.
			forma = new FormaW8IMY2015(
				fileTransferForma.getIdCuentaNombrada(),
				fileTransferForma.getIdTipoBeneficiario(),
				fileTransferForma.getIdInstitucion());
			//Coloca el tipo de formato
			forma.setFormato(BeneficiariosConstantes.FORMATO_W8_IMY2015);
			//Coloca la fecha del formato y la fecha del registro
			forma.setFechaFormato(fileTransferForma.getDateFechaFormato());
			forma.setFechaRegistro(new Date(System.currentTimeMillis()));
			//Coloca el estado del beneficiario
			if(BeneficiariosConstantes.ESTADO_ACTIVO.equals(fileTransferForma.getEstado())) {
				forma.setActivo(BeneficiariosConstantes.ID_ESTADO_ACTIVO);
			}
			else if(BeneficiariosConstantes.ESTADO_INACTIVO.equals(fileTransferForma.getEstado())) {
				forma.setActivo(BeneficiariosConstantes.ID_ESTADO_INACTIVO);
			}
			//Llena los campos 1, 2 y 3
			forma.setPartIcmp1(fileTransferForma.getCampo1());
			forma.setPartIcmp2(fileTransferForma.getCampo2());
			forma.setPartIcmp3(fileTransferForma.getCampo3());
			//Llena el campo 4 y sus apendices
			obtenerValorCampo4(forma, fileTransferForma);
			//Llena el campo 5 y sus apendices
			obtenerValorCampo5(forma, fileTransferForma);
			//Valida si debe prender la bandera de domicilio de correo deshabilitado
			if(StringUtils.isBlank(fileTransferForma.getCampo7a()) &&
					StringUtils.isBlank(fileTransferForma.getCampo7b()) &&
					StringUtils.isBlank(fileTransferForma.getCampo7c())) {
				forma.setReadOnlyCmp7(true);
			}
			//Llena el campo 8
			forma.setPartIcmp8(fileTransferForma.getCampo8());
			forma.setPartIcmp8a(obtenerBooleano(fileTransferForma.getCampo8a()));
			forma.setPartIcmp8b(obtenerBooleano(fileTransferForma.getCampo8b()));
			forma.setPartIcmp8c(obtenerBooleano(fileTransferForma.getCampo8c()));
			forma.setPartIcmp8d(obtenerBooleano(fileTransferForma.getCampo8d()));
			forma.setPartIcmp8e(obtenerBooleano(fileTransferForma.getCampo8e()));
			//Llena el campo 9
			forma.setPartIcmp9(fileTransferForma.getCampo9());
			//Llena el campo 10
			if(!(FileTransferBenefConstantes.CLEARSTREAM_BANKING.equals(fileTransferForma.getClaveCustodio()) ||
					FileTransferBenefConstantes.EUROCLEAR_BANK.equals(fileTransferForma.getClaveCustodio()))) {
				forma.setPartIcmp10(fileTransferForma.getCampo10());
			}
			//Llena el campo 11
			obtenerValorCampo11(forma, fileTransferForma);
			//Llena el campo 12 y 13
			forma.setPartIIcmp12a(fileTransferForma.getCampo12a());
			forma.setPartIIcmp12b(fileTransferForma.getCampo12b());
			forma.setPartIIcmp12c(fileTransferForma.getCampo12c());
			forma.setPartIIcmp13(fileTransferForma.getCampo13());
		}
		return forma;
	}

	/**
	 * Obtiene el domicilio asociado al campo 6
	 * @param fileTransferForma Objeto de donde se extraen los datos
	 */
	private Domicilio obtenerDomicilio(FileTransferFormaW8IMY2015 fileTransferForma) {
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
			FileTransferFormaW8IMY2015 fileTransferForma) {
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
	private void obtenerValorCampo4(FormaW8IMY2015 forma, FileTransferFormaW8IMY2015 fileTransferForma) {
		if(OpcionesCampo4W8IMY.A.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4a(true);
			forma.setPartIII(true);
			forma.setPartIIIcmp14a(obtenerBooleano(fileTransferForma.getCampo4Anexo1()));
			forma.setPartIIIcmp14b(obtenerBooleano(fileTransferForma.getCampo4Anexo2()));
			forma.setPartIIIcmp14c(obtenerBooleano(fileTransferForma.getCampo4Anexo3()));
			forma.setPartIIIcmp14c1(fileTransferForma.getCampo4Anexo4());
			forma.setPartIIIcmp14d(obtenerBooleano(fileTransferForma.getCampo4Anexo5()));
			forma.setPartIIIcmp14d1(fileTransferForma.getCampo4Anexo6());
			forma.setPartIIIcmp14e(obtenerBooleano(fileTransferForma.getCampo4Anexo7()));
			forma.setPartIIIcmp14e1(fileTransferForma.getCampo4Anexo8());
			forma.setPartIIIcmp14ei(obtenerBooleano(fileTransferForma.getCampo4Anexo9()));
			forma.setPartIIIcmp14eii(obtenerBooleano(fileTransferForma.getCampo4Anexo10()));
			forma.setPartIIIcmp14f(obtenerBooleano(fileTransferForma.getCampo4Anexo11()));
		}
		else if(OpcionesCampo4W8IMY.B.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4b(true);
			forma.setPartIV(true);
			forma.setPartIVcmp15a(obtenerBooleano(fileTransferForma.getCampo4Anexo1()));
			forma.setPartIVcmp15b(obtenerBooleano(fileTransferForma.getCampo4Anexo2()));
			forma.setPartIVcmp15c(obtenerBooleano(fileTransferForma.getCampo4Anexo3()));
			forma.setPartIVcmp15d(obtenerBooleano(fileTransferForma.getCampo4Anexo4()));
		}
		else if(OpcionesCampo4W8IMY.C.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4c(true);
			completarParteV(forma, fileTransferForma);
		}
		else if(OpcionesCampo4W8IMY.D.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4d(true);
			forma.setPartVI(true);
			forma.setPartVIcmp17a(obtenerBooleano(fileTransferForma.getCampo4Anexo1()));
			forma.setPartVIcmp17b(obtenerBooleano(fileTransferForma.getCampo4Anexo2()));
			forma.setPartVIcmp17c(obtenerBooleano(fileTransferForma.getCampo4Anexo3()));
		}
		else if(OpcionesCampo4W8IMY.E.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4e(true);
			completarParteVII(forma);
		}
		else if(OpcionesCampo4W8IMY.F.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4f(true);
			completarParteVII(forma);
		}
		else if(OpcionesCampo4W8IMY.G.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4g(true);
			completarParteVIII(forma);
		}
		else if(OpcionesCampo4W8IMY.H.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4h(true);
			completarParteVIII(forma);
		}
		else if(OpcionesCampo4W8IMY.I.toString().equals(fileTransferForma.getCampo4())) {
			forma.setPartIcmp4i(true);
			completarParteVIII(forma);
		}
	}

	/**
	 * Obtiene el valor del campo 5 y completa los apendices de acuerdo a la opción selecionada.
	 * @param forma Forma donde se setean los valores.
	 * @param fileTransferForma Objeto de donde se toman los datos.
	 */
	private void obtenerValorCampo5(FormaW8IMY2015 forma, FileTransferFormaW8IMY2015 fileTransferForma) {
		if(OpcionesCampo5W8IMY.A.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5a(true);
			forma.setPartIX(true);
			forma.setPartIXcmp20(true);
		}
		else if(OpcionesCampo5W8IMY.B.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5b(true);
		}
		else if(OpcionesCampo5W8IMY.C.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5c(true);
		}
		else if(OpcionesCampo5W8IMY.D.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5d(true);
		}
		else if(OpcionesCampo5W8IMY.E.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5e(true);
		}
		else if(OpcionesCampo5W8IMY.F.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5f(true);
			completarParteV(forma, fileTransferForma);
		}
		else if(OpcionesCampo5W8IMY.G.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5g(true);
			forma.setPartX(true);
			forma.setPartXcmp21a(fileTransferForma.getCampo5Anexo1());
			forma.setPartXcmp21b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartXcmp21c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
		}
		else if(OpcionesCampo5W8IMY.H.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5h(true);
			forma.setPartXII(true);
			forma.setPartXIIcmp23(true);
		}
		else if(OpcionesCampo5W8IMY.I.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5i(true);
			forma.setPartXIII(true);
			forma.setPartXIIIcmp24(true);
		}
		else if(OpcionesCampo5W8IMY.J.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5j(true);
			forma.setPartXIV(true);
			forma.setPartXIVcmp25a(fileTransferForma.getCampo5Anexo1());
			forma.setPartXIVcmp25b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
		}
		else if(OpcionesCampo5W8IMY.K.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5k(true);
			forma.setPartXV(true);
			forma.setPartXVcmp26(true);
		}
		else if(OpcionesCampo5W8IMY.L.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5l(true);
			forma.setPartXI(true);
			forma.setPartXIcmp22a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXIcmp22b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartXIcmp22c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
		}
		else if(OpcionesCampo5W8IMY.M.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5m(true);
			forma.setPartXVI(true);
			forma.setPartXVIcmp27a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXVIcmp27b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartXVIcmp27c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
		}
		else if(OpcionesCampo5W8IMY.N.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5n(true);
			forma.setPartXVII(true);
			forma.setPartXVIIcmp28(true);
		}
		else if(OpcionesCampo5W8IMY.O.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5o(true);
			forma.setPartXVIII(true);
			forma.setPartXVIIIcmp29a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXVIIIcmp29b(fileTransferForma.getCampo5Anexo2());
			forma.setPartXVIIIcmp29c(fileTransferForma.getCampo5Anexo3());
			forma.setPartXVIIIcmp29d(fileTransferForma.getCampo5Anexo4());
		}
		else if(OpcionesCampo5W8IMY.P.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5p(true);
			forma.setPartXIX(true);
			forma.setPartXIXcmp30a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXIXcmp30b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartXIXcmp30c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
			forma.setPartXIXcmp30d(obtenerBooleano(fileTransferForma.getCampo5Anexo4()));
			forma.setPartXIXcmp30e(obtenerBooleano(fileTransferForma.getCampo5Anexo5()));
			forma.setPartXIXcmp30f(obtenerBooleano(fileTransferForma.getCampo5Anexo6()));
		}
		else if(OpcionesCampo5W8IMY.Q.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5q(true);
			forma.setPartXX(true);
			forma.setPartXXcmp31(true);
		}
		else if(OpcionesCampo5W8IMY.R.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5r(true);
			forma.setPartXXI(true);
			forma.setPartXXIcmp32a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXXIcmp32b(fileTransferForma.getCampo5Anexo2());
		}
		else if(OpcionesCampo5W8IMY.S.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5s(true);
			forma.setPartXXII(true);
			forma.setPartXXIIcmp33a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXXIIcmp33b(fileTransferForma.getCampo5Anexo2());
		}
		else if(OpcionesCampo5W8IMY.T.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5t(true);
			forma.setPartXXIII(true);
			forma.setPartXXIIIcmp34a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartXXIIIcmp34a1(fileTransferForma.getCampo5Anexo2());
			forma.setPartXXIIIcmp34b(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
			forma.setPartXXIIIcmp34b1(fileTransferForma.getCampo5Anexo4());
			forma.setPartXXIIIcmp34b2(fileTransferForma.getCampo5Anexo5());
		}
		else if(OpcionesCampo5W8IMY.U.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5u(true);
			forma.setPartXXIV(true);
			forma.setPartXXIVcmp35(true);
		}
		else if(OpcionesCampo5W8IMY.V.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5v(true);
			forma.setPartXXV(true);
			forma.setPartXXVcmp36(true);
		}
		else if(OpcionesCampo5W8IMY.W.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5w(true);
			forma.setPartXXVI(true);
			forma.setPartXXVIcmp37(true);
		}
		else if(OpcionesCampo5W8IMY.X.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5x(true);
		}
		else if(OpcionesCampo5W8IMY.Y.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartIcmp5y(true);
			forma.setPartXXVII(true);
			forma.setPartXXVIIcmp38(fileTransferForma.getCampo5Anexo1());
			forma.setPartXXVIIcmp39(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
		}
	}

	/**
	 * Completa la parte V de la forma
	 * @param forma Forma donde se va a setear el valor.
	 * @param fileTransferForma Objeto de donde se toman los datos.
	 * @param campo true si debe llenar 
	 */
	private void completarParteV(FormaW8IMY2015 forma, FileTransferFormaW8IMY2015 fileTransferForma) {
		forma.setPartV(true);
		if(FileTransferBenefConstantes.OTROS_W8IMY.equals(fileTransferForma.getTipoBeneficiario()) &&
				OpcionesCampo4W8IMY.C.toString().equals(fileTransferForma.getCampo4()) &&
				OpcionesCampo5W8IMY.F.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartVcmp16a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartVcmp16b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartVcmp16c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
		}
		else if(OpcionesCampo4W8IMY.C.toString().equals(fileTransferForma.getCampo4()) &&
				!OpcionesCampo5W8IMY.F.toString().equals(fileTransferForma.getCampo5())) {
			forma.setPartVcmp16a(obtenerBooleano(fileTransferForma.getCampo4Anexo1()));
			forma.setPartVcmp16b(obtenerBooleano(fileTransferForma.getCampo4Anexo2()));
			forma.setPartVcmp16c(obtenerBooleano(fileTransferForma.getCampo4Anexo3()));			
		}
		else {
			forma.setPartVcmp16a(obtenerBooleano(fileTransferForma.getCampo5Anexo1()));
			forma.setPartVcmp16b(obtenerBooleano(fileTransferForma.getCampo5Anexo2()));
			forma.setPartVcmp16c(obtenerBooleano(fileTransferForma.getCampo5Anexo3()));
		}
	}
	
	/**
	 * Completa la parte VII de la forma
	 * @param forma Forma donde se va a setear el valor.
	 */
	private void completarParteVII(FormaW8IMY2015 forma) {
		forma.setPartVII(true);
		forma.setPartVIIcmp18(true);
	}
	
	/**
	 * Completa la parte VIII de la forma
	 * @param forma Forma donde se va a setear el valor.
	 */
	private void completarParteVIII(FormaW8IMY2015 forma) {
		forma.setPartVIII(true);
		forma.setPartVIIIcmp19(true);
	}
	
	/**
	 * Obtiene el valor del campo 11.
	 * @param forma Forma donde se va a setear el valor.
	 * @param campo11 Valor en el file transfer.
	 */
	private void obtenerValorCampo11(FormaW8IMY2015 forma, FileTransferFormaW8IMY2015 fileTransferForma) {
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