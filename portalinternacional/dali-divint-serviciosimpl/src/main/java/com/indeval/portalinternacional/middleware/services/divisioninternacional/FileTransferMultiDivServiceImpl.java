
/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import com.bursatec.util.message.MessageResolver;
import com.ibm.icu.util.Calendar;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.*;
import com.indeval.portaldali.persistence.modelo.*;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDetalleDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.*;
import com.indeval.portalinternacional.persistence.dao.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
@SuppressWarnings({"unchecked"})
public class FileTransferMultiDivServiceImpl implements FileTransferMultiDivService, Constantes {

	private static final Logger log = LoggerFactory.getLogger(FileTransferMultiDivServiceImpl.class);

	/**
	 * AgenteVO[]
	 */
	private static final AgenteVO[] arregloAgenteVOType = new AgenteVO[0];

	/**
	 * List<Integer>
	 */
	private static final List<Integer> posiciones = new ArrayList<Integer>();

	/**
	 * Map<Object,Object>
	 */
	private static final Map<Object, Object> indicesNombresTraspasosDivInt = fillIndicesNombresTraspasosDivIntTrue();

	/**
	 * Long
	 */
	private static final Long LONG_ONE = new Long(1);

	/**
	 * String
	 */
	private static final String emptyString = new String(" ");

	/**
	 * SimpleDateFormat
	 */
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * List<String>
	 */
	private List<String> custodiosPredefinidos = null;

	/**
	 * MessageResolver
	 */
	private MessageResolver messageResolver = null;

	/**
	 * FileTransferDao
	 */
	private FileTransferMultiDao fileTransferMultiDao = null;

	/**
	 * DateUtilService
	 */
	private DateUtilService dateUtilService = null;

	/**
	 * CuentaNombradaDao
	 */
	private CuentaNombradaDao cuentaNombradaDao = null;

	/**
	 * ValidatorDivIntService
	 */
	private ValidatorDivIntService validatorDivIntService = null;

	/**
	 * UtilDivIntService
	 */
	private UtilDivIntService utilDivIntService = null;

	/**
	 * EmisionDao
	 */
	private EmisionDao emisionDao = null;

	/**
	 * PosicionNombradaDao
	 */
	private PosicionNombradaDao posicionNombradaDao = null;

	/**
	 * CuponDao
	 */
	private CuponDao cuponDao = null;

	/**
	 * SicEmisionDao
	 */
	private SicEmisionDao sicEmisionDao = null;

	/**
	 * CatBicDao
	 */
	private CatBicDao catBicDao = null;

	/**
	 * SicDetalleDao
	 */
	private SicDetalleDao sicDetalleDao = null;

	/**
	 * DivisaDao
	 */
	private DivisaDao divisaDao = null;

	/**
	 * DivisionInternacionalService
	 */
	private DivisionInternacionalService divisionInternacionalService = null;

	/**
	 * UtilService
	 */
	private UtilService utilService = null;

	/**
	 * Dao de consultas operacion sic
	 */
	private OperacionSicDao operacionSicDao;

	/**
	 * Dao para Settlement Partial
	 **/
	SettlementDisciplineRegimeDao settlementDisciplineRegimeDao;

	BovedaDao bovedaDao = null;

	InstitucionDao institucionDao = null;

	DivisaDaliDao divisaDaliDao = null;

	/**
	 * i
	 * M&eacute;todo estatico para llenar el mapa de indices
	 *
	 * @return Map<Object, Object>
	 */
	private static Map<Object, Object> fillIndicesNombresTraspasosDivIntTrue() {
		Map<Object, Object> mapa = new HashMap<Object, Object>();

		mapa.put(ID_INST_VEND, new Integer(0));
		mapa.put(FOLIO_INST_VEND, new Integer(1));
		mapa.put(CUENTA_VEND, new Integer(2));
		mapa.put(TV, new Integer(3));
		mapa.put(EMISORA, new Integer(4));
		mapa.put(SERIE, new Integer(5));
		mapa.put(CUPON, new Integer(6));
		mapa.put(ISIN, new Integer(7));
		mapa.put(CANTIDAD, new Integer(8));
		mapa.put(TIPO_OPER, new Integer(9));
		mapa.put(TIPO_MOV, new Integer(10));
		mapa.put(LIQ_PARCIAL, new Integer(11));
		mapa.put(FECHA_NOT, new Integer(12));
		mapa.put(FECHA_LIQ, new Integer(13));
		mapa.put(FECHA_OPER, new Integer(14));
		mapa.put(CUSTODIO, new Integer(15));
		mapa.put(CUENTA_CONTRA, new Integer(16));
		mapa.put(CONTRAPARTE, new Integer(17));
		mapa.put(DEPOSITANTE, new Integer(18));
		mapa.put(NOMBRE_CUENTA_BEN, new Integer(19));
		mapa.put(NUMERO_CUENTA_BEN, new Integer(20));
		mapa.put(IMPORTE, new Integer(21));
		mapa.put(DIVISA, new Integer(22));
		mapa.put(INST_ESP, new Integer(23));

		return (mapa);
	}

	private static String NUM = "NUM";
	private static String NUO = "NUO";
	private static String STO = "STO";
	private static String STM = "STM";
	private static String BDM = "BDM";
	private static String BDO = "BDO";

	/**
	 * Realiza la cancelaci&oacute;n de un proceso de carga
	 *
	 * @param agenteVO
	 * @param tipoProceso
	 * @throws BusinessException
	 */
//	private void cancelaProcesoDivInt(AgenteVO agenteVO, String tipoProceso) throws BusinessException {
//		/* Obtiene la lista de instancias FileTransferDivInt */
//		List<FileTransfer> listaFileTransferDivInt = fileTransferMultiDao.findFileTransferDivIntByIdFolioTipoReg(agenteVO.getId(), agenteVO.getFolio(), tipoProceso);
//
//		log.info("Numero de registros encontrados: [" + listaFileTransferDivInt.size() + "]");
//		/* Borra los registros de la lista */
//		for (int i = 0; i < listaFileTransferDivInt.size(); i++) {
//			log.info("Borrando el registro: [" + listaFileTransferDivInt.get(i).getIdFiletransferDivint() + "]");
//			fileTransferMultiDao.delete(listaFileTransferDivInt.get(i));
//		}
//		fileTransferMultiDao.flush();
//	}
	private void cancelaProcesoDivInt(Long idFileTransfer, AgenteVO agenteVO, String tipoProceso) throws BusinessException {
		/* Obtiene la lista de instancias FileTransferDivInt */
		List<FileTransferDetalleDivisas> listaFileTransferDivInt = fileTransferMultiDao.findFileTransferDetalleByIdFileTransfer(idFileTransfer);
		log.info("Numero de registros encontrados: [" + listaFileTransferDivInt.size() + "]");
		/* Borra los registros de la lista */
		for (int i = 0; i < listaFileTransferDivInt.size(); i++) {
			log.info("Borrando el registro: [" + listaFileTransferDivInt.get(i).getIdFileTransferDetalleDivisasInt() + "]");
			fileTransferMultiDao.delete(listaFileTransferDivInt.get(i));
		}

		fileTransferMultiDao.flush();
		log.debug("Eliminar encabezado de filetransfer");
		FileTransferDivisas ftDivisas = null;

		ftDivisas = (FileTransferDivisas) fileTransferMultiDao.findFileTransferByIdFileTransfer(idFileTransfer);

		fileTransferMultiDao.delete(ftDivisas);
		//fileTransferMultiDao.flush();

		log.debug("FileTransfer eliminado con éxito");
	}


	/**
	 * Separa una entrada del archivo por campos
	 *
	 * @param cadena
	 * @return String[]
	 * @throws BusinessException
	 */
	private String[] obtieneCampos(String cadena) throws BusinessException {
		String campo = null;
		int posIni = 0;
		int posFin = 0;
		String[] campos = new String[posiciones.size()];
		for (int i = 0; i < posiciones.size(); i++) {
			posFin = posIni + ((Integer) posiciones.get(i)).intValue();
			campo = cadena.substring(posIni, posFin);
			if (StringUtils.isNotBlank(campo)) {
				campo = campo.toUpperCase();
			}
			campos[i] = campo;
			posIni = posFin;
		}
		return (campos);
	}

	/**
	 * Llena los indicies requeridos y separa en campos una entrada del archivo
	 *
	 * @param cadena
	 * @return String[]
	 * @throws BusinessDataException
	 * @throws BusinessException
	 */
	private String[] camposTraspasosDivInt(String cadena) throws BusinessDataException, BusinessException {

		/* Valida longitudes posibles */

		int tam = cadena.length();
		if (tam < LONGITUD_TRASP_DIV_INT_MIN
				|| tam > LONGITUD_TRASP_DIV_INT_MAX
				|| (tam < LONGITUD_TRASP_DIV_INT_INTER && tam > LONGITUD_TRASP_DIV_INT_MIN)) {
			return (null);
		}

		posiciones.clear();
		posiciones.add(new Integer(2));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(7));
		posiciones.add(new Integer(6));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(12));
		posiciones.add(new Integer(20));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(1));
		//Liquidacion Parcial
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(11));
		posiciones.add(new Integer(11));
		posiciones.add(new Integer(11));
		posiciones.add(new Integer(35));
		posiciones.add(new Integer(15));
		posiciones.add(new Integer(40));
		posiciones.add(new Integer(35));
		posiciones.add(new Integer(140));
		posiciones.add(new Integer(35));

		if (tam > LONGITUD_TRASP_DIV_INT_MIN) {
			posiciones.add(new Integer(15));
			posiciones.add(new Integer(3));
		}
		if (tam > LONGITUD_TRASP_DIV_INT_INTER) {
			int ultpos = tam - LONGITUD_TRASP_DIV_INT_INTER;
			posiciones.add(new Integer(ultpos));
		}
		return (obtieneCampos(cadena));
	}

	/**
	 * Agrega el mensaje de error de validaci&oacute;n al bufer de mensajes
	 *
	 * @param msgError
	 * @param mensaje
	 * @param numCampoError
	 * @param pos
	 */
	private void agregaErrorValidacion(StringBuilder msgError, String mensaje, StringBuilder numCampoError, int pos) {
		msgError.append(messageResolver.getMessage("J0051"));
		msgError.append(mensaje);
		msgError.append(GUION);
		numCampoError.append(String.valueOf(pos + 1));
		numCampoError.append(GUION);
	}

	/**
	 * Valida la fechas del archivo
	 *
	 * @param fecha
	 * @return boolean
	 */
	private boolean fechaValida(String fecha) {
		String[] fechaSeparada = fecha.split("-");
		if (fechaSeparada.length != 3 || fechaSeparada[0].length() != 2
				|| !fechaSeparada[0].matches(PATRON_NUMERICO) || fechaSeparada[1].length() != 3
				|| !fechaSeparada[1].matches(PATRON_ALFABETICO) || fechaSeparada[2].length() != 4
				|| !fechaSeparada[2].matches(PATRON_NUMERICO)) {
			return false;
		}
		return true;
	}


	/**
	 * Valida los tipos de dato y obligatoriedad de los registros de un archivo.
	 * NUM- numérico mandatorio
	 * NUO-numerico opcional
	 * STO- String opcional
	 * STM- String mandatorio
	 * BDM- BigDecimal mandatorio
	 * BDO-BigDecimal opcional
	 *
	 * @param mapAtrib
	 * @param mapValAtrib
	 * @param registro
	 * @param msgError
	 * @param numCampoError
	 * @param agenteVO
	 * @return
	 */
	private boolean validaFormatoMD(Map<Integer, String> mapAtrib, Map<Integer, String> mapValAtrib, RegistroExcelMultiDivVO registro, StringBuilder msgError, StringBuilder numCampoError,
									AgenteVO agenteVO) {
		boolean noError = true;
		/* VALIDACIONES DE FORMATO */
		Set<Integer> llavesMapaConversion = mapAtrib.keySet();
		String valor = null;

		for (Integer idColumna : llavesMapaConversion) {

			String atrib = mapAtrib.get(idColumna);
			String tipoDat = mapValAtrib.get(idColumna);


			try {
				valor = BeanUtils.getProperty(registro, atrib);

				if (tipoDat.compareTo(NUM) == 0) {

					if (valor == null || (valor != null && !valor.matches(PATRON_NUMERICO_SIN_ESPACIO))) {
						agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
						noError = false;

					}
				} else if (tipoDat.compareTo(NUO) == 0) {
					if (valor != null && !valor.matches(PATRON_NUMERICO_SIN_ESPACIO)) {
						agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
						noError = false;
					}
				} else if (tipoDat.compareTo(STO) == 0) {

					if (valor != null && !valor.matches(PATRON_ALFANUMERICO)) {
						agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
						noError = false;
					}

				} else if (tipoDat.compareTo(STM) == 0) {

					if (valor == null || valor.isEmpty() || !valor.matches(PATRON_ALFANUMERICO)) {
						agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
						noError = false;
					}

				} else if (tipoDat.compareTo(BDM) == 0) {
					Pattern pattern = Pattern.compile(PATRON_BIGDECIMAL);

					if (valor == null)
						valor = "";

					Matcher matcher = pattern.matcher(valor);

					if (valor == null || valor.isEmpty() || (valor != null && !matcher.find())) {
						agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
						noError = false;
					}
				} else if (tipoDat.compareTo(BDO) == 0) {
					Pattern pattern = Pattern.compile(PATRON_BIGDECIMAL);

					if (valor != null && !valor.isEmpty()) {
						Matcher matcher = pattern.matcher(valor);

						if (!matcher.find()) {
							agregaErrorValidacion(msgError, atrib + " " + tipoDat, numCampoError, idColumna);
							noError = false;
						}
					}

				} else {
					throw new BusinessException("No se puede identificar el tipo de dato para la colunmna " + atrib);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}

		}
		return (noError);
	}

	/**
	 * @param agenteFirmado
	 * @param regis
	 * @param msgError
	 * @param numCampoError
	 * @param agenteVO
	 * @param bovedas
	 * @param divisas
	 * @param instituciones
	 * @return
	 * @throws BusinessException
	 */
	private boolean validaNegocioMD(AgenteVO agenteFirmado,
									RegistroExcelMultiDivVO regis, StringBuilder msgError,
									StringBuilder numCampoError,
									AgenteVO agenteVO,
									Map bovedas,
									Map divisas,
									Map instituciones) throws BusinessException {
		boolean noError = true;
		boolean almenosUno = false;

		BigDecimal depositos = BigDecimal.valueOf(0); //depAjuste + MtoBruto
		BigDecimal retiros = BigDecimal.valueOf(0); //retencion + retAjuste + comision
		BigDecimal saldo = new BigDecimal(0); //depositos - retiros

		//NOTA: montos en el bean no deben ser negativos

		BigDecimal depAjuste = new BigDecimal(0);
		BigDecimal retencion = new BigDecimal(0);
		BigDecimal retAjuste = new BigDecimal(0);
		BigDecimal comision = new BigDecimal(0);
		//BigDecimal mtoBruto = new BigDecimal(0);
		BigDecimal mtoNeto = new BigDecimal(0);

		//Verificar que venga al menos uno de los montos
		if (regis.getDepAjuste() != null || regis.getRetAjuste() != null
				|| regis.getRetencion() != null || regis.getComision() != null) {
			almenosUno = true;
		} else {
			agregaErrorComun("El registro debe contener al menos un valor en los montos", msgError);
			almenosUno = false;
			return (false);
		}

		//verificar que no sea el mismo participante que transfiere/adeua
		if (new BigDecimal(regis.getMtoNeto()).compareTo(new BigDecimal("0")) < 0 && regis.getClaveTipoInst().concat(regis.getIdFolio()).compareTo("12001") == 0) {
			agregaErrorComun("La cuenta receptora es igual a la cuenta emisora", msgError);
			noError = false;
		}

		//si vienen nulo setear a cero para hacer sumas y restas
		depAjuste = regis.getDepAjuste() != null ? new BigDecimal(regis.getDepAjuste()) : new BigDecimal(0);
		retencion = regis.getRetencion() != null ? new BigDecimal(regis.getRetencion()) : new BigDecimal(0);
		retAjuste = regis.getRetAjuste() != null ? new BigDecimal(regis.getRetAjuste()) : new BigDecimal(0);
		comision = regis.getComision() != null ? new BigDecimal(regis.getComision()) : new BigDecimal(0);
		//	mtoBruto = regis.getMtoBruto() != null ? new BigDecimal(regis.getMtoBruto()) : new BigDecimal(0);
		mtoNeto = regis.getMtoNeto() != null ? new BigDecimal(regis.getMtoNeto()) : new BigDecimal(0);

		//suma de positivos
		depositos = depositos.add(depAjuste);

		//suma de negativos
		retiros = retiros.add(retencion).add(retAjuste).add(comision);

		//positivos menos negativos
		saldo = depositos.subtract(retiros);

		//verificación total debe ser igual al resultado de positivos - negativos
		if (saldo.compareTo(mtoNeto) != 0) {
			agregaErrorComun("Error en el monto neto, no corresponde con los movimientos en el registro", msgError);
			noError = false;
		}

		//obtener boveda

		if (bovedas == null) {
			bovedas = new HashMap();
		}

		if (divisas == null) {
			divisas = new HashMap();
		}

		Boveda b = null;
		Divisa divisa = null;
		Institucion institucion = null;
		log.debug("Inicio boveda " + regis.getBoveda().substring(0, 2));
		if (regis.getBoveda() == null || regis.getBoveda().isEmpty() || regis.getBoveda().substring(0, 2).compareTo("E-") != 0) {
			String mensaje = "";
			if (regis.getBoveda() == null || regis.getBoveda().isEmpty()) {
				mensaje = "El valor de la bóveda está vacío";
			} else if (regis.getBoveda().substring(0, 2).compareTo("E-") != 0) {
				mensaje = String.format("La bóveda proporcionada (%s) no es de efectivo ", regis.getBoveda());
			} else {
				mensaje = "La bóveda no es correcta";
			}
			agregaErrorComun(mensaje, msgError);
			noError = false;
		} else if (!bovedas.containsKey(regis.getBoveda())) {
			Boveda[] lisbo = bovedaDao.findBovedas(null, regis.getBoveda());
			if (lisbo != null && lisbo.length > 0) {
				b = lisbo[0];
				bovedas.put(b.getNombreCorto(), b);
			} else {
				agregaErrorComun(String.format("La bóveda proporcionada (%s) no existe", regis.getBoveda()), msgError);
				noError = false;
			}

		} else {
			b = (Boveda) bovedas.get(regis.getBoveda());

		}
		regis.setOboveda(b);

		//obtener institucion
		if (!instituciones.containsKey(regis.getClaveTipoInst().concat(regis.getIdFolio()))) {
			institucion = this.institucionDao.findInstitucionByClaveFolio(regis.getClaveTipoInst().concat(regis.getIdFolio()));
			if (institucion != null) {
				regis.setOinstitucion(institucion);
				instituciones.put(regis.getClaveTipoInst().concat(regis.getIdFolio()), institucion);

			} else {
				agregaErrorComun(String.format("Institucion proporcionada(%s) no existe", regis.getClaveTipoInst().concat(regis.getIdFolio())), msgError);
				noError = false;
			}

		} else {
			institucion = (Institucion) instituciones.get(regis.getClaveTipoInst().concat(regis.getIdFolio()));
		}

		regis.setOinstitucion(institucion);

		regis.setClaveTipoInst(institucion.getTipoInstitucion().getClaveTipoInstitucion());
		regis.setIdFolio(institucion.getFolioInstitucion());

		//obtener divisa
		if (!divisas.containsKey(regis.getDivisa())) {
			divisa = divisaDaliDao.findDivisaByClaveAlfabetica(regis.getDivisa());
			if (divisa == null) {
				agregaErrorComun(String.format("Divisa proporcionada (%s) no existe.", regis.getDivisa()), msgError);
				noError = false;
			} else {
				divisas.put(divisa.getClaveAlfabetica(), divisa);
			}

		} else {
			divisa = (Divisa) divisas.get(regis.getDivisa());
		}

		regis.setOdivisa(divisa);

		return (noError);
	}

	/**
	 * Agrega un mensaje al bufer de errores comunes
	 *
	 * @param mensaje
	 * @param buffer
	 */
	private void agregaErrorComun(String mensaje, StringBuilder buffer) {
		buffer.append(mensaje);
		buffer.append(GUION);
	}

	/**
	 * Convierte una fecha en notaci&oacute;n del archivo de entrada a notaci&oacute;n del modelo de datos
	 *
	 * @param fechaArchivo
	 * @return String
	 */
	private String fechaArchivo2fechaFileTransfer(String fechaArchivo) {

		if (StringUtils.isNotBlank(fechaArchivo)) {
			Map meses = new HashMap();
			meses.put("JAN", String.valueOf("01"));
			meses.put("FEB", String.valueOf("02"));
			meses.put("MAR", String.valueOf("03"));
			meses.put("APR", String.valueOf("04"));
			meses.put("MAY", String.valueOf("05"));
			meses.put("JUN", String.valueOf("06"));
			meses.put("JUL", String.valueOf("07"));
			meses.put("AUG", String.valueOf("08"));
			meses.put("SEP", String.valueOf("09"));
			meses.put("OCT", String.valueOf("10"));
			meses.put("NOV", String.valueOf("11"));
			meses.put("DEC", String.valueOf("12"));
			Map mesesEsp = new HashMap();
			mesesEsp.put("ENE", String.valueOf("01"));
			mesesEsp.put("ABR", String.valueOf("04"));
			mesesEsp.put("AGO", String.valueOf("08"));
			mesesEsp.put("DIC", String.valueOf("12"));
			List elementosFecha = new ArrayList();
			elementosFecha.add(fechaArchivo.substring(0, 2));
			elementosFecha
					.add(meses.get(fechaArchivo.substring(3, 6).toUpperCase()) != null ? meses
							.get(fechaArchivo.substring(3, 6).toUpperCase()) : mesesEsp
							.get(fechaArchivo.substring(3, 6).toUpperCase()));
			elementosFecha.add(fechaArchivo.substring(7));
			return elementosFecha.get(1) + "/" + elementosFecha.get(0) + "/"
					+ elementosFecha.get(2);
		}

		return ("");
	}

	/**
	 * Valida las cantidades numericas
	 *
	 * @param cantidad
	 * @return boolean
	 */
	private boolean validaCantidades(String cantidad) {
		boolean noError = true;
		BigDecimal cantidadD = null;
		if (StringUtils.isBlank(cantidad)) {
			noError = false;
		} else {
			try {
				cantidadD = new BigDecimal(cantidad.trim());
			} catch (NumberFormatException e) {
				noError = false;
			}
			if (cantidadD.compareTo(BigDecimal.ZERO) == 0) {
				noError = false;
			}
		}
		return noError;
	}

	/**
	 * Ensambla el objeto FileTransfer
	 *
	 * @throws BusinessException
	 */

	private void llenaFileTransferDivIntMD(FileTransferDetalleDivisas fileTransfer,
										   AgenteVO agenteVO,
										   RegistroExcelMultiDivVO regis) throws BusinessException {

		fileTransfer.setIdFileTransferDivisas(regis.getIdFileTransferMultidivisas());

		if (regis.getOboveda() != null)
			fileTransfer.setBoveda(regis.getOboveda());

		if (regis.getOdivisa() != null)
			fileTransfer.setDivisa(regis.getOdivisa());

		if (regis.getRetencion() != null && !regis.getRetencion().isEmpty())
			fileTransfer.setRetencion(new BigDecimal(regis.getRetencion()));

		if (regis.getComision() != null && !regis.getComision().isEmpty())
			fileTransfer.setComision(new BigDecimal(regis.getComision()));

		if (regis.getDepAjuste() != null && !regis.getDepAjuste().isEmpty())
			fileTransfer.setDepositoAjuste(new BigDecimal(regis.getDepAjuste()));

		if (regis.getRetAjuste() != null && !regis.getRetAjuste().isEmpty())
			fileTransfer.setRetiroAjuste(new BigDecimal(regis.getRetAjuste()));

		if (regis.getMtoNeto() != null && !regis.getMtoNeto().isEmpty())
			fileTransfer.setMontoNeto(new BigDecimal(regis.getMtoNeto()));

		fileTransfer.setConcepto(regis.getConcepto());
		fileTransfer.setReferencia(regis.getReferencia());
		fileTransfer.setEstatusDetalle(regis.getEstatusDivisas());
		fileTransfer.setComentarios(regis.getComentarios());

		if (regis.getOinstitucion() != null) {
			fileTransfer.setFolioInstitucion(regis.getOinstitucion().getFolioInstitucion());
			fileTransfer.setIdInstitucion(regis.getOinstitucion().getIdInstitucion());
			fileTransfer.setClaveTipoInstitucion(regis.getOinstitucion().getTipoInstitucion().getClaveTipoInstitucion());

		}

		fileTransfer.setFechaRegistro(Calendar.getInstance().getTime());


	}

	private void FileTransferDivIntMD2RegisExcel(FileTransferDetalleDivisas fileTransfer,
												 AgenteVO agenteVO,
												 RegistroExcelMultiDivVO regis) throws BusinessException {

		regis.setIdFileTransferMultidivisas(fileTransfer.getIdFileTransferDivisas());
		if (fileTransfer.getBoveda() != null) {
			regis.setOboveda(fileTransfer.getBoveda());
			regis.setBoveda(fileTransfer.getBoveda().getNombreCorto());
		}
		if (fileTransfer.getDivisa() != null) {
			regis.setOdivisa(fileTransfer.getDivisa());
			regis.setDivisa(fileTransfer.getDivisa().getClaveAlfabetica());
		}

		if (fileTransfer.getRetencion() != null) {
			regis.setRetencion(fileTransfer.getRetencion().toString());
		}

		if (fileTransfer.getComision() != null) {
			regis.setComision(fileTransfer.getComision().toString());
		}
		if (fileTransfer.getDepositoAjuste() != null) {
			regis.setDepAjuste(fileTransfer.getDepositoAjuste().toString());
		}
		if (fileTransfer.getRetiroAjuste() != null) {
			regis.setRetAjuste(fileTransfer.getRetiroAjuste().toString());
		}
		if (fileTransfer.getMontoNeto() != null) {
			regis.setMtoNeto(fileTransfer.getMontoNeto().toString());
		}
		regis.setConcepto(fileTransfer.getConcepto());
		regis.setReferencia(fileTransfer.getReferencia());
		regis.setEstatusDivisas(fileTransfer.getEstatusDetalle());
		regis.setComentarios(fileTransfer.getComentarios());
		regis.setClaveTipoInst(fileTransfer.getClaveTipoInstitucion());
		regis.setIdFolio(fileTransfer.getFolioInstitucion());
		regis.setFechaReg(fileTransfer.getFechaRegistro());
		regis.setIdInstitucion(fileTransfer.getIdInstitucion());
		regis.setClaveTipoInst(fileTransfer.getClaveTipoInstitucion());

	}

	/**
	 * Llena los campos en caso de un error en el formato de la entrada
	 *
	 * @param fileTransfer
	 */
	/**
	 * En caso de error llena el máximo de valores en el registro
	 *
	 * @param fileTransfer
	 * @param regis
	 */
	private void fillErrorCaseFields(FileTransferDetalleDivisas fileTransfer, RegistroExcelMultiDivVO regis) {
		log.error("Inicio fillErrorCaseFields " + Calendar.getInstance().getTime());
		fileTransfer.setIdFileTransferDivisas(regis.getIdFileTransferMultidivisas());
		if (regis.getOboveda() != null) {
			fileTransfer.setBoveda(regis.getOboveda());
		} else {
			log.debug("fillErroCaseFields boveda null");
		}
		if (regis.getOdivisa() != null)
			fileTransfer.setDivisa(regis.getOdivisa());
		else
			log.debug("fillErroCaseFields divisa null");

		if (regis.getRetencion() != null)
			fileTransfer.setRetencion(new BigDecimal(regis.getRetencion()));
		else
			log.debug("fillErroCaseFields retencion null");

		if (regis.getComision() != null)
			fileTransfer.setComision(new BigDecimal(regis.getComision()));
		else
			log.debug("fillErroCaseFields comision null");
		if (regis.getDepAjuste() != null)
			fileTransfer.setDepositoAjuste(new BigDecimal(regis.getDepAjuste()));
		else
			log.debug("fillErroCaseFields ajuste deposito null");

		if (regis.getRetAjuste() != null)
			fileTransfer.setRetiroAjuste(new BigDecimal(regis.getRetAjuste()));
		else
			log.debug("fillErroCaseFields ajuste retiro null");
		if (regis.getMtoNeto() != null)
			fileTransfer.setMontoNeto(new BigDecimal(regis.getMtoNeto()));
		else
			log.debug("fillErroCaseFields monto neto null");
		if (regis.getConcepto() != null)
			fileTransfer.setConcepto(regis.getConcepto());
		else
			log.debug("fillErroCaseFields concepto null");

		if (regis.getReferencia() != null)
			fileTransfer.setReferencia(regis.getReferencia());
		else
			log.debug("fillErroCaseFields referencia es null");

		if (regis.getEstatusDivisas() != null)
			fileTransfer.setEstatusDetalle(regis.getEstatusDivisas());
		else
			log.debug("fillErroCaseFields estatus divisas null");

		if (regis.getComentarios() != null)
			fileTransfer.setComentarios(regis.getComentarios());
		else
			log.debug("fillErroCaseFields comentarios null");

		if (regis.getClaveTipoInst() != null)
			fileTransfer.setClaveTipoInstitucion(regis.getClaveTipoInst());
		else
			log.debug("fillErroCaseFields clave tipo institucion null");

		if (regis.getIdFolio() != null)
			fileTransfer.setFolioInstitucion(regis.getIdFolio());
		else
			log.debug("fillErroCaseFields folio institucion null");

		fileTransfer.setFechaRegistro(Calendar.getInstance().getTime());

		if (regis.getIdInstitucion() != null)
			fileTransfer.setIdInstitucion(regis.getIdInstitucion());
		else
			log.debug("fillErroCaseFields idInstitucion null");

		log.error("fin fillErrorCaseFields " + Calendar.getInstance().getTime());
	}


	/**
	 * Implementación validacion y llenado de file transfer
	 *
	 * @param agenteFirmado
	 * @param fileTransfer
	 * @param informacion
	 * @param mapaAtrib
	 * @param mapaValAtrib
	 * @throws BusinessDataException
	 * @throws BusinessException
	 */
	private void parseaInformacionMD(AgenteVO agenteFirmado, FileTransferDetalleDivisas fileTransfer,
									 RegistroExcelMultiDivVO informacion,
									 Map<Integer, String> mapaAtrib,
									 Map<Integer, String> mapaValAtrib,
									 Map<String, Boveda> bovedas,
									 Map<String, Divisa> divisas,
									 Map<String, Institucion> instituciones
	) throws BusinessDataException, BusinessException {
		//objeto viene vacio
		if (informacion == null) {
			throw new BusinessDataException(messageResolver.getMessage("J0053", new Object[]{"informacion"}), "J0053");
		}

		boolean error = false;
		StringBuilder msgError = new StringBuilder();
		StringBuilder numCampoError = new StringBuilder();
		String cadenaErrorFormato = messageResolver.getMessage("J0051");

		int tam = 0;

		/* Obtiene los campos correspondientes al layout */
		RegistroExcelMultiDivVO registro = informacion; //aqui ya tenemos mapeado porque es un objeto

		if (registro == null) {
			msgError.append(cadenaErrorFormato);
			msgError.append("El registro está vacío ");
			msgError.append(GUION);
			//fillErrorCaseFields(fileTransfer);
			error = true;
		} else {

			AgenteVO agenteVO = new AgenteVO();
			EmisionVO emisionVO = new EmisionVO();


			if (validaFormatoMD(mapaAtrib, mapaValAtrib, registro, msgError, numCampoError, agenteVO)) {
				if (!validaNegocioMD(agenteFirmado, registro, msgError, numCampoError, agenteVO, bovedas, divisas, instituciones)) {
					error = true;
				}
				llenaFileTransferDivIntMD(fileTransfer, agenteVO, registro);
			} else {
				fillErrorCaseFields(fileTransfer, registro);
				error = true;
			}
		}
		if (error) {
			fileTransfer.setEstatusDetalle(12L); //error
			if (numCampoError.length() > 0) {
				fileTransfer.setCamposError(numCampoError.toString().substring(0, numCampoError.length() - 1));
			}
			fileTransfer.setError(new ClobImpl(msgError.toString().substring(0, msgError.length() - 1)));
			//fileTransfer.setFechaRegistro(dateUtilService.getCurrentDate());
			fileTransfer.setFechaRegistro(Calendar.getInstance().getTime());
		} else {
			fileTransfer.setEstatusDetalle(0L);
		}
	}


	/**
	 * Obtiene la emisi&oacute;n por su ISIN
	 *
	 * @param fileTransfer
	 * @throws BusinessException
	 */
	private void obtieneEmisionByIsin(FileTransfer fileTransfer) throws BusinessException {
		if (fileTransfer != null && (StringUtils.isBlank(fileTransfer.getTv()) || StringUtils.isBlank(fileTransfer.getEmisora())
				|| StringUtils.isBlank(fileTransfer.getSerie()) || StringUtils.isBlank(fileTransfer.getCupon())) && StringUtils.isNotBlank(fileTransfer.getIsin())) {
			Emision emision = emisionDao.findEmisionLiberada(new EmisionVO(fileTransfer.getIsin()));
			if (emision != null) {
				Cupon cupon = cuponDao.findCuponByIdEmision(emision.getIdEmision());
				fileTransfer.setTv(emision.getInstrumento().getClaveTipoValor());
				fileTransfer.setEmisora(emision.getEmisora().getClavePizarra());
				fileTransfer.setSerie(emision.getSerie());
				fileTransfer.setCupon(cupon.getClaveCupon());
			}
		}
	}

	/**
	 * Registra la informaci&oacute;n de traspasos
	 *
	 * @param agenteFirmado
	 * @param tipoProceso
	 * @param consecProtocolo
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private void grabaInformacionTraspasosDI(AgenteVO agenteFirmado, String tipoProceso, Map consecProtocolo) throws BusinessException, ParseException {

		/* Obtiene la lista de instancias FileTransferDivInt */
		List<FileTransfer> fileTransfers = fileTransferMultiDao.findFileTransferDivIntByIdFolioTipoReg(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

		Boolean isProtocolo = null;

		log.error("Numero de operaciones: [" + fileTransfers.size() + "]");

		for (int i = 0; i < fileTransfers.size(); i++) {
			FileTransfer fileTransfer = fileTransfers.get(i);
			if (consecProtocolo != null && !consecProtocolo.isEmpty() && consecProtocolo.containsKey(fileTransfer.getConsecReg())) {
				if (((Integer) consecProtocolo.get(fileTransfer.getConsecReg())).intValue() == 1) {
					isProtocolo = Boolean.TRUE;
				} else {
					isProtocolo = null;
				}
			} else {
				isProtocolo = Boolean.FALSE;
			}

			if (isProtocolo != null) {
				if (isProtocolo) {
					fileTransfer.setEstadoReg(ESTADO_A_PROTOCOLO);
					fileTransferMultiDao.update(fileTransfer);
				}
			} else {
				fileTransfer.setEstadoReg(ESTADO_ERROR);
				fileTransferMultiDao.update(fileTransfer);
			}
            /*if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_NUEVO)) {
            	obtieneEmisionByIsin(fileTransfer); 
            	/*CAMBIO 04/09/2012  cambiar para que inserte en t_bitacora de operaciones
            	//divisionInternacionalService.insertaOperacionSIC(creaOperacionSic(fileTransfer));
            	divisionInternacionalService.grabaOperacion(creaOperacionParams(fileTransfer));
            	
            	fileTransfer.setEstadoReg(ESTADO_CARGADO);
            	fileTransferDao.update(fileTransfer);
            }*/

		}
	}

	/**
	 * @param fileTransfer
	 * @param tipoProceso
	 * @param camposVO
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private List<Object> generaDatosDivInt(FileTransferDetalleDivisas fileTransfer,
										   String tipoProceso,
										   CamposDivIntVO camposVO,
										   Map<Integer, String> mapa) throws BusinessException, ParseException {
		log.debug("FileTransferMultiDivServiceImpl :: generaDatosDivInt");
		/* Obtiene lista de campos con error */
		String[] numeroCampoError = null;
		if (fileTransfer.getCamposError() != null) {
			numeroCampoError = fileTransfer.getCamposError().split("-");
			if (numeroCampoError == null) {
				throw new BusinessException(messageResolver.getMessage("J0053", new Object[]{"numeroCampoError"}));
			}
		}
		List<String> listaCamposError = new ArrayList<String>();

		for (int i = 0; numeroCampoError != null && i < numeroCampoError.length; i++) {
			if (StringUtils.isNotBlank(numeroCampoError[i])
					&& !listaCamposError.contains(numeroCampoError[i])) {
				listaCamposError.add(numeroCampoError[i]);
			}
		}

		/* Obtiene el mapa de indices de campos */
		Map<Object, Object> indiceCampo = new TreeMap<Object, Object>();

		indiceCampo = indicesNombresTraspasosDivInt;

		/* Genera el objeto de salida del servicio */
		List<Object> listaCamposArchivo = new ArrayList<Object>();

		boolean error = false;

		String valor = "";
		Set<Integer> llavesMapaConversion = mapa.keySet();

		RegistroExcelMultiDivVO re = new RegistroExcelMultiDivVO();
		FileTransferDivIntMD2RegisExcel(fileTransfer, null, re);
		for (Integer idColumna : llavesMapaConversion) {
			String atrib = mapa.get(idColumna);
			String tipoDat = mapa.get(idColumna);
			error = listaCamposError.contains(String.valueOf(idColumna));

			try {
				valor = BeanUtils.getProperty(re, atrib);
				if (valor == null)
					valor = "";

				CampoArchivoVO campoArchivoVO = FactoryDivInt.generaArchivoVO(
						valor.length(),
						atrib, error, valor, null);
				listaCamposArchivo.add(campoArchivoVO);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}

		}

		/* Verificacion y setteo de los datos del traspasante */


		/*CampoArchivoVO campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstTrasp().length(),
				ID_INST_VEND, error, fileTransfer.getIdInstTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstTrasp(fileTransfer.getIdInstTrasp());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FOLIO_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getFolioInstTrasp().length(),
				FOLIO_INST_VEND, error, fileTransfer.getFolioInstTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFolioInstTrasp(fileTransfer.getFolioInstTrasp());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaTrasp().length(),
				CUENTA_VEND, error, fileTransfer.getCuentaTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaTrasp(fileTransfer.getCuentaTrasp());

//		 Verificacion y setteo de los datos del receptor
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstRecep().length(),
				ID_RECEP, error, fileTransfer.getIdInstRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstRecep(fileTransfer.getIdInstRecep());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FOLIO_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getFolioInstRecep().length(),
				FOLIO_RECEP, error, fileTransfer.getFolioInstRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFolioInstRecep(fileTransfer.getFolioInstRecep());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaRecep().length(),
				CUENTA_RECEP, error, fileTransfer.getCuentaRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaRecep(fileTransfer.getCuentaRecep());

//		 Verificacion y setteo de los atributos de la clave-valor
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TV)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTv(),
				TV, error, fileTransfer.getTv(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(EMISORA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getEmisora(),
				EMISORA, error, fileTransfer.getEmisora(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(SERIE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getSerie(),
				SERIE, error, fileTransfer.getSerie(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUPON)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCupon(),
				CUPON, error, fileTransfer.getCupon(), null);
		listaCamposArchivo.add(campoArchivoVO);

		camposVO.setTv(fileTransfer.getTv());
		camposVO.setEmisora(fileTransfer.getEmisora());
		camposVO.setSerie(fileTransfer.getSerie());
		camposVO.setCupon(fileTransfer.getCupon());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ISIN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIsin(),
				ISIN, error, fileTransfer.getIsin(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIsin(fileTransfer.getIsin());

//		  Verificacion y asignacion de la cantidad
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CANTIDAD)));
		BigInteger cantidad = null;
		if (!error) {
			if (StringUtils.isNotBlank(fileTransfer.getCantidadTitulos()) &&
					StringUtils.isNumeric(fileTransfer.getCantidadTitulos().trim())) {
				cantidad = new BigInteger(fileTransfer.getCantidadTitulos().trim());
			}
		}
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCantidadTitulos().length(),
				CANTIDAD, error, cantidad != null ? cantidad : fileTransfer.getCantidadTitulos(),
				"###################0");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCantidad(cantidad);

		 Verificacion y setteo del tipo de operacion
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_OPER)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoOperacion().length(),
				TIPO_OPER, error, fileTransfer.getTipoOperacion(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoOperacion(fileTransfer.getTipoOperacion());

		 Verificacion y setteo del tipo de movimiento
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_MOV)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoMovto(),
				TIPO_MOV, error, fileTransfer.getTipoMovto(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoMovimiento(fileTransfer.getTipoMovto());

//		 Verificacion y setteo de liquidacion parcial
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(LIQ_PARCIAL)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getLiquidacionParcial(),
				LIQ_PARCIAL, error, fileTransfer.getLiquidacionParcial(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setLiquidacionParcial(fileTransfer.getLiquidacionParcial());

//		 Verificacion y setteo de la fecha de notificacion
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_NOT)));
		Date fecha = null;
		String fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaNotificacion());
			if (StringUtils.isNotBlank(fechaFormateada)) {
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);
			}
		}
		int tamFechaNotificacion = fileTransfer.getFechaNotificacion() != null ?
				fileTransfer.getFechaNotificacion().length() : 0;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaNotificacion,
				FECHA_NOT, error, fecha != null ? fecha : fileTransfer.getFechaNotificacion(),
				"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaNotificacion(fecha);

//		 Verificacion y setteo de la fecha de liquidacion
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_LIQ)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaLiquidacion());
			if (StringUtils.isNotBlank(fechaFormateada)) {
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);
			}
		}
		int tamFechaLiquidacion = fileTransfer.getFechaLiquidacion() != null ?
				fileTransfer.getFechaLiquidacion().length() : 0;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaLiquidacion,
				FECHA_LIQ, error, fecha != null ? fecha : fileTransfer.getFechaLiquidacion(),
				"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaLiquidacion(fecha);

//		 Verificacion y setteo de la fecha de operacion
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_OPER)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaOperacion());
			if (StringUtils.isNotBlank(fechaFormateada)) {
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);
			}
		}
		int tamFechaOperacion = fileTransfer.getFechaOperacion() != null ?
				fileTransfer.getFechaOperacion().length() : 0;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaOperacion,
				FECHA_OPER, error, fecha != null ? fecha : fileTransfer.getFechaOperacion(),
				"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaOperacion(fecha);

//		 Verificacion de los datos del custodio
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUSTODIO)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getDescCustodio(),
				CUSTODIO, error, fileTransfer.getDescCustodio(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDescCustodio(fileTransfer.getDescCustodio());

//		 Verificacion de los datos de la contraparte
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_CONTRA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaContraparte(),
				CUENTA_CONTRA, error, fileTransfer.getCuentaContraparte(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaContraparte(fileTransfer.getCuentaContraparte());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CONTRAPARTE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getDescContraparte(),
				CONTRAPARTE, error, fileTransfer.getDescContraparte(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDescContraparte(fileTransfer.getDescContraparte());

//		 Verificacion de los datos del depositante
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(DEPOSITANTE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getNombreDepositante(),
				DEPOSITANTE, error, fileTransfer.getNombreDepositante(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNombreDepositante(fileTransfer.getNombreDepositante());

//		 Verificacion de los datos del beneficiario
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(NOMBRE_CUENTA_BEN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getNombreCuentaBeneficiario(),
				NOMBRE_CUENTA_BEN, error, fileTransfer.getNombreCuentaBeneficiario(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNombreCuentaBeneficiario(fileTransfer.getNombreCuentaBeneficiario());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(NUMERO_CUENTA_BEN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(fileTransfer.getNumeroCuentaBeneficiario(),
				NUMERO_CUENTA_BEN, error, fileTransfer.getNumeroCuentaBeneficiario(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNumeroCuentaBeneficiario(fileTransfer.getNumeroCuentaBeneficiario());

//		Verificacion de los datos del importe y divisa
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(IMPORTE)));
		BigDecimal importe = null;
		if (!error && StringUtils.isNotBlank(fileTransfer.getImporte())
				&& NumberUtils.isNumber(StringUtils.trim(fileTransfer.getImporte()))) {
			importe = new BigDecimal(fileTransfer.getImporte().trim());
		}
		int tam = StringUtils.isNotBlank(fileTransfer.getImporte()) ?
				fileTransfer.getImporte().length() : 15;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tam,
				IMPORTE, error, importe != null ? importe : fileTransfer.getImporte(),
				"###############0.0##");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setImporte(importe);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(DIVISA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(3,
				DIVISA, error, fileTransfer.getDivisa(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDivisa(fileTransfer.getDivisa());

//		 Verificacion de las instrucciones especiales
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(INST_ESP)));
		tam = fileTransfer.getInstruccEspeciales() != null ?
				fileTransfer.getInstruccEspeciales().length() : 0;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tam,
				INST_ESP, error, fileTransfer.getInstruccEspeciales(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setInstrucEsp(fileTransfer.getInstruccEspeciales());

		camposVO.setTipoMensaje(fileTransfer.getTipoMensaje());
		camposVO.setEstatus(fileTransfer.getEstatusOper());
		camposVO.setBajaLogica(fileTransfer.getBajaLogica());
		camposVO.setMercado(fileTransfer.getMercado());
*/
		return listaCamposArchivo;
	}

	/**
	 * @param fileTransferVO
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private TotalesProcesoVO muestraInformacionDivInt(Long idFileTransfer,
													  FileTransferVO fileTransferVO, Map<Integer, String> mapa) throws BusinessException, ParseException {

		AgenteVO agenteFirmado = fileTransferVO.getAgenteFirmado();
		String tipoProceso = fileTransferVO.getTipoProceso();
		PaginaVO paginaVO = fileTransferVO.getPaginaVO();

		/* Valida parametros */
		if (!agenteFirmado.tieneClave()) {
			throw new BusinessException(messageResolver.getMessage("J0054"));
		}
		TotalesProcesoVO totalesProcesoVO = new TotalesProcesoVO();
		paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

		List<FileTransferDetalleDivisas> fileTransfers = fileTransferMultiDao.findFileTransferDetalleByIdFileTransfer(idFileTransfer);

		if ((fileTransfers == null) || (fileTransfers.isEmpty())) {
			throw new BusinessDataException(messageResolver.getMessage("J0046", new Object[]{"fileTransfers"}), "J0046");
		}

		ArrayList listaRegistros = new ArrayList();
		int registrosError = 0;
		int totalregistros = 0;
		for (int i = 0; i < fileTransfers.size(); i++) {
			FileTransferDetalleDivisas fileTransfer = fileTransfers.get(i);
			if ((!fileTransferVO.isSoloErrores()) || (fileTransferVO.isSoloErrores() && fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_ERROR) == 0)) {

				RegistroProcesadoVO registroProcesadoVO = new RegistroProcesadoVO();
				CamposDivIntVO camposDivIntVO = new CamposDivIntVO();

				registroProcesadoVO.setConsec(new BigInteger(fileTransfer.getIdFileTransferDetalleDivisasInt().toString()));
				registroProcesadoVO.setDatos(generaDatosDivInt(fileTransfer, tipoProceso, camposDivIntVO, mapa));
				String[] errores = registroProcesadoVO.getMensajesError();
				registroProcesadoVO.setMostrarRegistro(true);
				if (errores != null && errores.length > 0) {
					for (String error : errores) {
						if (StringUtils.isNotBlank(error) &&
								StringUtils.containsIgnoreCase(error, "Formato incorrecto en la estructura del registro")) {
							registroProcesadoVO.setMostrarRegistro(false);
						}
					}
				}

				if (registroProcesadoVO.getDatos() == null) {
					registroProcesadoVO.setDatos((List<Object>) new ArrayList<Object>());
				}
				String mensajeError = null;
				try {
					mensajeError = utilService.clobToString(fileTransfer.getError());
				} catch (SQLException e) {
					throw new BusinessException(e.getMessage(), e);
				}
				registroProcesadoVO.setMensajesError(mensajeError != null ? mensajeError.split("-") : null);
				registroProcesadoVO.setEstado(fileTransfer.getEstatusDetalle().toString());
				registroProcesadoVO.setFechaRegistro(fileTransfer.getFechaRegistro());
				registroProcesadoVO.setNombreUsuario(fileTransfer.getFileTransferDivisas().getUsuarioRegistro());
				registroProcesadoVO.setCamposDivIntVO(camposDivIntVO);

				if (fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_ERROR) == 0) {
					registrosError++;
				}

				totalregistros++;
				listaRegistros.add(registroProcesadoVO);
			}
		}
		paginaVO.extraerSublist(listaRegistros);

		totalesProcesoVO.setPaginaVO(paginaVO);
		totalesProcesoVO.setRegistrosACargar(new Integer(totalregistros - registrosError));
		totalesProcesoVO.setRegistrosConError(new Integer(registrosError));

		return (totalesProcesoVO);
	}


	private ResumenVO obtieneResumenDivInt(Long idFileTransfer, AgenteVO agenteFirmado) throws BusinessException {
		log.debug("FileUploadMultiDivServiceImpl :: obtieneResumenDivInt");
		/* Valida parametros */
		if (!agenteFirmado.tieneClave()) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0054"));
		}

		int registrosCargados = 0;
		int registrosError = 0;
		int registrosNuevos = 0;
		int totalRegistros = 0;
		int registrosaAutorizados = 0;
		String nombreUsuario = "";
		Date fechaCarga = null;

		/* Obtiene la lista de instancias FileTransferDivInt */
		List<FileTransferDetalleDivisas> fileTransfers = fileTransferMultiDao.findFileTransferDetalleByIdFileTransfer(idFileTransfer);

		//.findFileTransferDivIntByIdFolioTipoReg(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

		if ((fileTransfers != null) && (!fileTransfers.isEmpty())) {

			for (int i = 0; i < fileTransfers.size(); i++) {
				FileTransferDetalleDivisas fileTransfer = fileTransfers.get(i);

				if (fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_ERROR) == 0) {
					registrosError++;
				} else if (fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_REGISTRADO) == 0) {
					registrosNuevos++;
				} else if (fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_CARGANDO) == 0) {
					registrosCargados++;
				} else if (fileTransfer.getEstatusDetalle().compareTo(FILE_TRANSFER_AUTORIZADO) == 0) {
					registrosaAutorizados++;
				}

				nombreUsuario = fileTransfer.getFileTransferDivisas().getUsuarioRegistro();
				fechaCarga = fileTransfer.getFileTransferDivisas().getFechaRegistro();
				totalRegistros++;
			}

			if (totalRegistros != (registrosCargados + registrosError + registrosNuevos + registrosaAutorizados)) {
				throw new BusinessDataException(messageResolver.getMessage("J0049", new Object[]{" "}), "J0049");
			}

		}

		ResumenVO resumenVO = new ResumenVO();
		resumenVO.setAgenteFirmado(agenteFirmado);
		resumenVO.setFechaCarga(fechaCarga);
		resumenVO.setTipoProceso(FILE_TRANSFER_TIPO_PROCESO);
		resumenVO.setTotalCargados(new Integer(registrosCargados));
		resumenVO.setTotalError(new Integer(registrosError));
		resumenVO.setTotalNuevos(new Integer(registrosNuevos));
		resumenVO.setTotalRegistros(new Integer(totalRegistros));
		resumenVO.setTotalProtocolo(new Integer(registrosaAutorizados));
		resumenVO.setNombreUsuario(nombreUsuario);

		return (resumenVO);
	}

	/**
	 * @see FileTransferService#cancelaProceso(FileTransferVO)
	 */
//	public void cancelaProceso(FileTransferVO fileTransferVO) throws BusinessException {
//		/* Validamos los parametros */
//		if (fileTransferVO == null) {
//			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
//		}
//		if (fileTransferVO.getAgenteFirmado() == null) {
//			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
//		}
//		if (fileTransferVO.getTipoProceso() == null) {
//			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.tipoProceso"}));
//		}
//		this.cancelaProcesoDivInt(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso());
//	}
	public void cancelaProceso(Long idFileTransfer, FileTransferVO fileTransferVO) throws BusinessException {
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.tipoProceso"}));
		}

		this.cancelaProcesoDivInt(idFileTransfer, fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso());
	}

	/**
	 * @see FileTransferService#grabaInformacion(FileTransferVO)
	 */
	public void grabaInformacion(FileTransferVO fileTransferVO) throws BusinessException {
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.tipoProceso"}));
		}
		if (fileTransferVO.getConsecProtocolo() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.consecProtocolo"}));
		}

		try {//aqui XX
			this.grabaInformacionTraspasosDI(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso(), fileTransferVO.getConsecProtocolo());
		} catch (ParseException pe) {
			throw new BusinessException(pe.toString(), pe);
		} catch (Exception ex) {
			throw new BusinessException("Error al grabar el registro", ex);
		}
	}

	/**
	 * @see FileTransferService#muestraInformacion(FileTransferVO)
	 */
	public TotalesProcesoVO muestraInformacion(Long idFileTransfer, FileTransferVO fileTransferVO, Map<Integer, String> mapa) throws BusinessException {
		log.debug("FileTransferServiceImpl :: muestraInformacion");
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.tipoProceso"}));
		}
		try {
			return (this.muestraInformacionDivInt(idFileTransfer, fileTransferVO, mapa));
		} catch (ParseException pe) {
			throw new BusinessException(pe.toString());
		}
	}

	/**
	 * @see FileTransferService#obtieneResumen(FileTransferVO)
	 */
	public ResumenVO obtieneResumen(Long idFileTransfer, FileTransferVO fileTransferVO) throws BusinessException {
		log.debug("FileUploadMultiDivServiceImpl :: obtieneResumen");
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
		}

		return (this.obtieneResumenDivInt(idFileTransfer, fileTransferVO.getAgenteFirmado()));
	}


	public Integer almacenaInformacionExcelMD(Long idFileTransfer, FileTransferVO fileTransferVO,
											  Map<Integer, String> mapaAtrib,
											  Map<Integer, String> mapaValAtrib,
											  Map<String, Boveda> bovedas,
											  Map<String, Divisa> divisas,
											  Map<String, Institucion> instituciones) throws BusinessException {

		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO"}));
		}

		AgenteVO agenteFirmado = fileTransferVO.getAgenteFirmado();
		if (agenteFirmado == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.agenteFirmado"}));
		}
		if (!agenteFirmado.tieneClave()) {
			throw new BusinessException(messageResolver.getMessage("J0054"));
		}

		String tipoProceso = fileTransferVO.getTipoProceso();
		if ((tipoProceso == null) || (tipoProceso.length() == 0)) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.tipoProceso"}));
		}


		List<RegistroExcelMultiDivVO> informacionArchivo = fileTransferVO.getRowsArchivo();
		if ((informacionArchivo == null) || (informacionArchivo.size() == 0)) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.informacionArchivo"}));
		}

		Integer offset = fileTransferVO.getOffset();
		if (offset == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[]{"fileTransferVO.offset"}));
		}
		if (offset >= informacionArchivo.size()) {
			throw new BusinessDataException(messageResolver.getMessage("J0045", new Object[]{Integer.toString(informacionArchivo.size())}), "J0045");
		}


		log.debug("Offset: [" + offset.intValue() + "]");
		/* Borra la informacion antes de almacenarla */
		if (offset.intValue() == 0) {
			log.error("El offset es 0");
			cancelaProcesoDivInt(idFileTransfer, agenteFirmado, tipoProceso);
		} else {
			log.error("El offset viene distinto de cero");
		}

		/* Realiza la lectura y almacenamiento de registros */
		long inicioProceso = System.currentTimeMillis();
		int ultimoRegistro = 0;
		for (int i = offset; i < informacionArchivo.size(); i++) {

			FileTransferDetalleDivisas fileTransfer = new FileTransferDetalleDivisas();
			fileTransfer.setIdFileTransferDivisas(idFileTransfer);
			informacionArchivo.get(i).setIdFileTransferMultidivisas(idFileTransfer);
			parseaInformacionMD(agenteFirmado, fileTransfer, informacionArchivo.get(i),
					mapaAtrib, mapaValAtrib, bovedas, divisas, instituciones);

			/* Almacenamos el registro de transferencia */
			fileTransferMultiDao.save(fileTransfer);
			ultimoRegistro = i;

			long finalProceso = System.currentTimeMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}
		}

		/* Verificamos el numero de registros procesados */
		if ((ultimoRegistro + 1) == informacionArchivo.size()) {
			return -1;
		}
		return (ultimoRegistro + 1);
	}

	@Override
	public List<FileTransferDivisas> findFileTransferDivIntByUsuario(String cveUsuario) {
		List<FileTransferDivisas> lis = new ArrayList<>();
		lis = this.fileTransferMultiDao.findFileTransferDivIntByUsuario(cveUsuario);
		return lis;
	}

	/* Mutators */

	/**
	 * @param custodiosPredefinidos
	 */
	public void setCustodiosPredefinidos(List<String> custodiosPredefinidos) {
		this.custodiosPredefinidos = custodiosPredefinidos;
	}

	/**
	 * @param messageResolver
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

	/**
	 * @param fileTransferMultiDao
	 */
	public void setFileTransferMultiDao(FileTransferMultiDao fileTransferMultiDao) {
		this.fileTransferMultiDao = fileTransferMultiDao;
	}

	/**
	 * @param dateUtilService
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @param cuentaNombradaDao
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}

	/**
	 * @param validatorDivIntService
	 */
	public void setValidatorDivIntService(
			ValidatorDivIntService validatorDivIntService) {
		this.validatorDivIntService = validatorDivIntService;
	}

	/**
	 * @param utilDivIntService
	 */
	public void setUtilDivIntService(UtilDivIntService utilDivIntService) {
		this.utilDivIntService = utilDivIntService;
	}

	/**
	 * @param emisionDao
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @param posicionNombradaDao
	 */
	public void setPosicionNombradaDao(PosicionNombradaDao posicionNombradaDao) {
		this.posicionNombradaDao = posicionNombradaDao;
	}

	/**
	 * @param cuponDao
	 */
	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}

	/**
	 * @param sicEmisionDao
	 */
	public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
		this.sicEmisionDao = sicEmisionDao;
	}

	/**
	 * @param catBicDao
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}

	/**
	 * @param sicDetalleDao
	 */
	public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
		this.sicDetalleDao = sicDetalleDao;
	}

	/**
	 * @param divisaDao
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * @param divisionInternacionalService
	 */
	public void setDivisionInternacionalService(DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @param utilService
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	/**
	 * Método para establecer el atributo operacionSicDao
	 *
	 * @param operacionSicDao El valor del atributo operacionSicDao a establecer.
	 */
	public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
		this.operacionSicDao = operacionSicDao;
	}

	/**
	 * @return the settlementDisciplineRegimeDao
	 */
	public SettlementDisciplineRegimeDao getSettlementDisciplineRegimeDao() {
		return settlementDisciplineRegimeDao;
	}

	/**
	 * @param settlementDisciplineRegimeDao the settlementDisciplineRegimeDao to set
	 */
	public void setSettlementDisciplineRegimeDao(SettlementDisciplineRegimeDao settlementDisciplineRegimeDao) {
		this.settlementDisciplineRegimeDao = settlementDisciplineRegimeDao;
	}


	public void setBovedaDao(BovedaDao bovedaDao) {
		this.bovedaDao = bovedaDao;
	}

	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	public void setDivisaDaliDao(DivisaDaliDao divisaDaliDao) {
		this.divisaDaliDao = divisaDaliDao;
	}
}
