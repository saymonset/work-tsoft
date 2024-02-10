/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.math.BigDecimal;import java.math.BigInteger;import java.sql.SQLException;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Date;import java.util.HashMap;import java.util.List;import java.util.Map;import java.util.TreeMap;import org.apache.commons.lang.StringUtils;import org.apache.commons.lang.math.NumberUtils;import org.hibernate.lob.ClobImpl;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import com.bursatec.util.message.MessageResolver;import com.ibm.icu.util.Calendar;import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;import com.indeval.portaldali.middleware.services.util.DateUtilService;import com.indeval.portaldali.middleware.services.util.UtilService;import com.indeval.portaldali.middleware.servicios.modelo.BusinessDataException;import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;import com.indeval.portaldali.middleware.servicios.util.UtilsVO;import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;import com.indeval.portaldali.persistence.dao.common.CuponDao;import com.indeval.portaldali.persistence.dao.common.DivisaDao;import com.indeval.portaldali.persistence.dao.common.EmisionDao;import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDao;import com.indeval.portaldali.persistence.modelo.CuentaNombrada;import com.indeval.portaldali.persistence.modelo.Cupon;import com.indeval.portaldali.persistence.modelo.Divisa;import com.indeval.portaldali.persistence.modelo.Emision;import com.indeval.portaldali.persistence.modelo.Instrumento;import com.indeval.portaldali.persistence.util.DateUtils;import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;import com.indeval.portalinternacional.middleware.servicios.vo.CamposDivIntVO;import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;import com.indeval.portalinternacional.persistence.dao.CatBicDao;import com.indeval.portalinternacional.persistence.dao.FileTransferDao;import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;import com.indeval.portalinternacional.persistence.dao.SettlementDisciplineRegimeDao;import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@SuppressWarnings({"unchecked"})
public class FileTransferServiceImpl implements FileTransferService, Constantes {
	
	private static final Logger log = LoggerFactory.getLogger(FileTransferServiceImpl.class);
	
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
	private FileTransferDao fileTransferDao = null;
	
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
	private UtilService utilService = null;		/** Dao de consultas operacion sic */	private OperacionSicDao operacionSicDao;		/** Dao para Settlement Partial **/	SettlementDisciplineRegimeDao settlementDisciplineRegimeDao;
	
	/**
	 * M&eacute;todo estatico para llenar el mapa de indices
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
		mapa.put(TIPO_MOV, new Integer(10));		mapa.put(LIQ_PARCIAL, new Integer(11));
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
	
	/**
	 * Realiza la cancelaci&oacute;n de un proceso de carga
	 * @param agenteVO
	 * @param tipoProceso
	 * @throws BusinessException
	 */
	private void cancelaProcesoDivInt(AgenteVO agenteVO, String tipoProceso) throws BusinessException {
    	/* Obtiene la lista de instancias FileTransferDivInt */
    	List<FileTransfer> listaFileTransferDivInt = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(agenteVO.getId(), agenteVO.getFolio(), tipoProceso);
    	
    	log.info("Numero de registros encontrados: [" + listaFileTransferDivInt.size() + "]");
    	/* Borra los registros de la lista */
    	for (int i = 0; i < listaFileTransferDivInt.size(); i++) {
    		log.info("Borrando el registro: [" + listaFileTransferDivInt.get(i).getIdFiletransferDivint() + "]");
    		fileTransferDao.delete(listaFileTransferDivInt.get(i));
    	}
    	fileTransferDao.flush();
    }

	/**
	 * Separa una entrada del archivo por campos
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
	 * @param cadena
	 * @return String[]
	 * @throws BusinessDataException
	 * @throws BusinessException
	 */
	private String[] camposTraspasosDivInt(String cadena) throws BusinessDataException, BusinessException {

		/* Valida longitudes posibles */

		int tam = cadena.length();
		if (tam < LONGITUD_TRASP_DIV_INT_MIN 				|| tam > LONGITUD_TRASP_DIV_INT_MAX				|| (tam < LONGITUD_TRASP_DIV_INT_INTER && tam > LONGITUD_TRASP_DIV_INT_MIN)) {
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
		posiciones.add(new Integer(1));		//Liquidacion Parcial		posiciones.add(new Integer(4));
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
     * Valida el formato de una entrada del archivo
     * @param indice
     * @param campos
     * @param msgError
     * @param numCampoError
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @return boolean
     */
    private boolean validaFormato(Map<Object, Object> indice, String[] campos, StringBuilder msgError, StringBuilder numCampoError, int tam, AgenteVO agenteVO, EmisionVO emisionVO) {
        boolean noError = true;
        /* VALIDACIONES DE FORMATO */

        /** ID VENDEDOR */
        int pos = ((Integer) indice.get(ID_INST_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 2 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)) {
            agregaErrorValidacion(msgError, " del id del vendedor ", numCampoError, pos);
            noError = false;
        }
        agenteVO.setId(campos[pos]);

        /** FOLIO VENDEDOR */
        pos = ((Integer) indice.get(FOLIO_INST_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 3 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)) {
            agregaErrorValidacion(msgError, " del folio del vendedor ", numCampoError, pos);
            noError = false;
        }
        agenteVO.setFolio(campos[pos]);

        /** CUENTA VENDEDOR */
        pos = ((Integer) indice.get(CUENTA_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() < 3 || campos[pos].trim().length() > 4 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)) {
            agregaErrorValidacion(msgError, " de la cuenta del vendedor ", numCampoError, pos);
            noError = false;
        }
        agenteVO.setCuenta(campos[pos]);

        /** TV - Opcional puede capturar solo el ISIN */
        pos = ((Integer) indice.get(TV)).intValue();

        if (campos[pos].length() > 4 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del tv ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setTv(campos[pos]);

        /** EMISORA - Opcional puede capturar solo el ISIN */
        pos = ((Integer) indice.get(EMISORA)).intValue();

        if (campos[pos].length() > 7) {
            agregaErrorValidacion(msgError, " de la emisora ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setEmisora(campos[pos]);

        /** SERIE - Opcional puede capturar solo el ISIN */
        pos = ((Integer) indice.get(SERIE)).intValue();

        if (campos[pos].length() > 6) {
            agregaErrorValidacion(msgError, " de la serie ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setSerie(campos[pos]);

        /** CUPON - Opcional puede capturar solo el ISIN */
        pos = ((Integer) indice.get(CUPON)).intValue();

        if (campos[pos].length() > 4 || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " del cupon ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setCupon(campos[pos]);

        /** ISIN - Opcional si captura todos los datos de la emision (tv, emisora, serie, cupon) */
        pos = ((Integer) indice.get(ISIN)).intValue();
        //se agrega validacion para que el valor del ISIN sea obligatorio
        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 12 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del isin ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setIsin(campos[pos]);

        /** CANTIDAD */
        pos = ((Integer) indice.get(CANTIDAD)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() > 20 || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " de la cantidad ", numCampoError, pos);
            noError = false;
        }

        /** TIPO OPER */
        pos = ((Integer) indice.get(TIPO_OPER)).intValue();        String tipoOperacion = campos[pos];

        if (StringUtils.isBlank(campos[pos]) || (!campos[pos].equalsIgnoreCase(TRASPASO_LIBRE) && !campos[pos].equalsIgnoreCase(TRASPASO_CONTRA))) {
            agregaErrorValidacion(msgError, " del tipo de operacion ", numCampoError, pos);
            noError = false;
        }

        /** TIPO MOV */
        pos = ((Integer) indice.get(TIPO_MOV)).intValue();

        if (StringUtils.isBlank(campos[pos]) || (!campos[pos].equalsIgnoreCase(TIPO_MOVTO_E) && !campos[pos].equalsIgnoreCase(TIPO_MOVTO_R))) {
            agregaErrorValidacion(msgError, " del tipo de movimiento ", numCampoError, pos);
            noError = false;
        }                /** LIQUIDACION PARCIAL */        pos = ((Integer) indice.get(LIQ_PARCIAL)).intValue();        if (StringUtils.isEmpty(campos[pos]) || campos[pos].length() != 4 || (!campos[pos].equalsIgnoreCase(LIQ_PARTIAL_VALUE) && !campos[pos].equalsIgnoreCase("    "))) {            agregaErrorValidacion(msgError, " para liquidacion parcial ", numCampoError, pos);            noError = false;        }        
        /** FECHA NOTIFICACION */
		pos = ((Integer) indice.get(FECHA_NOT)).intValue();

		if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11 || !campos[pos].matches(PATRON_FECHA)) {
			agregaErrorValidacion(msgError, " de la fecha de Notificacion ", numCampoError, pos);
			noError = false;
		}

		/** FECHA LIQUIDACION */
		pos = ((Integer) indice.get(FECHA_LIQ)).intValue();

		if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11 || !campos[pos].matches(PATRON_FECHA)) {
			agregaErrorValidacion(msgError, " de la fecha de Liquidacion ", numCampoError, pos);
			noError = false;
		}

		/** FECHA OPERACION */
		pos = ((Integer) indice.get(FECHA_OPER)).intValue();

		if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11 || !campos[pos].matches(PATRON_FECHA)) {
			agregaErrorValidacion(msgError, " de la fecha de Operacion ", numCampoError, pos);
			noError = false;
		}

        /** DESC. CUSTODIO */
        pos = ((Integer) indice.get(CUSTODIO)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 35) {
            agregaErrorValidacion(msgError, " de la descripcion del custodio ", numCampoError, pos);
            noError = false;
        }

        /** CUENTA CONTRAPRTE */
        pos = ((Integer) indice.get(CUENTA_CONTRA)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 15) {
            agregaErrorValidacion(msgError, " de la cuenta contraparte ", numCampoError, pos);
            noError = false;
        }

        /** DESC. CONTRAPRTE */
        pos = ((Integer) indice.get(CONTRAPARTE)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 40) {
            agregaErrorValidacion(msgError, " de la descripcion de la contraparte ", numCampoError,
                    pos);
            noError = false;
        }

        /** NOMBRE DEPOSITANTE */
        pos = ((Integer) indice.get(DEPOSITANTE)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 35) {
            agregaErrorValidacion(msgError, " del nombre del depositante ", numCampoError, pos);
            noError = false;
        }

        /** NOMBRE CUENTA BENEFICIARIO */
        pos = ((Integer) indice.get(NOMBRE_CUENTA_BEN)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 140) {
            agregaErrorValidacion(msgError, " del nombre de la cuenta del beneficiario ",
                    numCampoError, pos);
            noError = false;
        }

        /** NUMERO CUENTA BENEFICIARIO */
        pos = ((Integer) indice.get(NUMERO_CUENTA_BEN)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 35) {
            agregaErrorValidacion(msgError, " del numero de la cuenta del beneficiario ",
                    numCampoError, pos);
            noError = false;
        }

        if (tam > LONGITUD_TRASP_DIV_INT_MIN) {

            /** IMPORTE */
            pos = ((Integer) indice.get(IMPORTE)).intValue();

            if (TRASPASO_CONTRA.equalsIgnoreCase(tipoOperacion.trim()) && (StringUtils.isBlank(campos[pos]) &&             		(campos[pos].length() != 15 || (campos[pos].indexOf(".") >= 0 && campos[pos].indexOf(".") != 12)) ||										!NumberUtils.isNumber(StringUtils.trim(campos[pos])) ||					!campos[pos].matches(PATRON_CANTIDADES))) {                agregaErrorValidacion(msgError, " el importe ", numCampoError, pos);                noError = false;            }            if(TRASPASO_LIBRE.equalsIgnoreCase(tipoOperacion.trim()) && !StringUtils.isBlank(campos[pos])){            	if((campos[pos].length() != 15 || (campos[pos].indexOf(".") >= 0 && campos[pos].indexOf(".") != 12)) ||					    					!NumberUtils.isNumber(StringUtils.trim(campos[pos])) ||    					!campos[pos].matches(PATRON_CANTIDADES)){            		agregaErrorValidacion(msgError, " el importe ", numCampoError, pos);                    noError = false;            	}            	            	            }

            /** DIVISA */
            pos = ((Integer) indice.get(DIVISA)).intValue();

            if (StringUtils.isBlank(campos[pos]) && campos[pos].length() != 3 || !campos[pos].matches(PATRON_ALFABETICO)) {
                agregaErrorValidacion(msgError, " de la divisa ", numCampoError, pos);
                noError = false;
            }
        }

        if (tam > LONGITUD_TRASP_DIV_INT_INTER) {

            /* INSTRUCCION ESPECIAL */
            pos = ((Integer) indice.get(INST_ESP)).intValue();

            if (StringUtils.isBlank(campos[pos])) {
                agregaErrorValidacion(msgError, " de las instrucciones especiales ", numCampoError,
                        pos);
                noError = false;
            }
        }
        return (noError);
    }
    
    /**
     * Agrega un mensaje al bufer de errores comunes
     * @param mensaje
     * @param buffer
     */
    private void agregaErrorComun(String mensaje, StringBuilder buffer) {
        buffer.append(mensaje);
        buffer.append(GUION);
    }

    /**
     * Convierte una fecha en notaci&oacute;n del archivo de entrada a notaci&oacute;n del modelo de datos
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
     * @param cantidad
     * @return boolean
     */
    private boolean validaCantidades(String cantidad) {
        boolean noError = true;
        BigDecimal cantidadD = null;
        if (StringUtils.isBlank(cantidad)) {
            noError = false;
        }
        else {
            try {
                cantidadD = new BigDecimal(cantidad.trim());
            }
            catch (NumberFormatException e) {
                noError = false;
            }
            if (cantidadD.compareTo(BigDecimal.ZERO) == 0) {
                noError = false;
            }
        }
        return noError;
    }

    /**
     * Realiza las validaciones de negocio para una entrada del archivo
     * @param agenteFirmado
     * @param indice
     * @param campos
     * @param msgError
     * @param numCampoError
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @param cuentaRecep
     * @return boolean
     * @throws BusinessException
     */
    private boolean validaNegocio(AgenteVO agenteFirmado, Map<Object, Object> indice, String[] campos, StringBuilder msgError, StringBuilder numCampoError, int tam, AgenteVO agenteVO, EmisionVO emisionVO, Map cuentaRecep) throws BusinessException {
        boolean noError = true;
        
        /* REGLAS DE NEGOCIO */

        Date fechaActual = dateUtilService.getCurrentDate();
        Date fechaActualHrCero = dateUtilService.getFechaHoraCero(fechaActual);

        /* Valida que la institucion del archivo corresponda a la del agente firmado */
        if (!(campos[((Integer) indice.get(ID_INST_VEND)).intValue()] + campos[((Integer) indice.get(FOLIO_INST_VEND)).intValue()]).equalsIgnoreCase(agenteFirmado.getClave())) {
            agregaErrorComun("No se pueden traspasar titulos de otra institucion", msgError);
            agregaErrorComun(((Integer) indice.get(ID_INST_VEND)).toString(), numCampoError);
            agregaErrorComun(((Integer) indice.get(FOLIO_INST_VEND)).toString(), numCampoError);
            return (false);
        }

        /* Valida que el traspasante exista */
        AgenteVO agenteTrasp = new AgenteVO(campos[((Integer)indice.get(ID_INST_VEND)).intValue()],campos[((Integer)indice.get(FOLIO_INST_VEND)).intValue()],campos[((Integer)indice.get(CUENTA_VEND)).intValue()]);
        CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteTrasp);
        agenteTrasp = ConvertBO2VO.crearAgenteVO(cuentaNombrada);
        
        if ((agenteTrasp == null) || (!agenteTrasp.tieneClave())) {
        	agregaErrorComun("No existe el traspasante o la cuenta", msgError);
        	agregaErrorComun(((Integer) indice.get(ID_INST_VEND)).toString(), numCampoError);
        	agregaErrorComun(((Integer) indice.get(FOLIO_INST_VEND)).toString(), numCampoError);
        	agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
        	noError = false;
        }
        else {
        	agenteVO.setTenencia(agenteTrasp.getTenencia());

            if (!validatorDivIntService.esCuentaValida(agenteTrasp)) {
                agregaErrorComun("La cuenta traspasante no es valida", msgError);
                agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
                noError = false;
            }
            else {
                /* Obtiene la cuenta receptora */
				String cuentaReceptora = utilDivIntService.obtieneCuentaReceptora(agenteVO);
				if (StringUtils.isBlank(cuentaReceptora)) {
                    agregaErrorComun("No fue posible obtener la cuenta receptora", msgError);
                    agregaErrorComun(((Integer) indice.get(ID_INST_VEND)).toString(), numCampoError);
                    agregaErrorComun(((Integer) indice.get(FOLIO_INST_VEND)).toString(),
                            numCampoError);
                    noError = false;
                }
				cuentaRecep.put("cuentaRecep",cuentaReceptora);
            }
        }

        /* Valida que exista la emision; si no existe, la obtiene a traves del ISIN */
        String tv = campos[((Integer) indice.get(TV)).intValue()];
        String emisora = campos[((Integer) indice.get(EMISORA)).intValue()];
        String serie = campos[((Integer) indice.get(SERIE)).intValue()];
        String cupon = campos[((Integer) indice.get(CUPON)).intValue()];
        String isin = campos[((Integer) indice.get(ISIN)).intValue()];
        Emision emision = null;
        EmisionVO emisionVOaux = null;

		int sumaDatosEmision = 0;
		sumaDatosEmision += StringUtils.isNotBlank(tv) ? 1 : 0;
		sumaDatosEmision += StringUtils.isNotBlank(emisora) ? 1 : 0;
		sumaDatosEmision += StringUtils.isNotBlank(serie) ? 1 : 0;
		sumaDatosEmision += StringUtils.isNotBlank(cupon) ? 1 : 0;
		/* Indica que no vienen los 4 datos completos */
		if(sumaDatosEmision > 0 && sumaDatosEmision < 4) {
			agregaErrorComun("Emision invalida", msgError);
			agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
			noError = false;
		} else if(sumaDatosEmision == 0 && StringUtils.isBlank(isin)) {
			agregaErrorComun("Emision invalida", msgError);
			agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
			agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
			noError = false;
		}        
		if (noError) {
			if (sumaDatosEmision == 4) {
				emisionVOaux = new EmisionVO(tv, emisora, serie, cupon);
//				emisionVOaux.setIdBoveda(LONG_ONE);
				emision = emisionDao.findEmisionLiberada(emisionVOaux);

				if (emision == null) {
					agregaErrorComun("No existe la emision", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
					noError = false;
				} else {
					if (StringUtils.isNotBlank(isin) && StringUtils.isNotBlank(emision.getIsin()) &&
							!isin.trim().equalsIgnoreCase(emision.getIsin().trim())) {
						agregaErrorComun("ISIN invalido", msgError);
						agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
						noError = false;
					}
				}
			} else {
				if (StringUtils.isNotBlank(isin)) {
					emisionVOaux = new EmisionVO(isin);
//					emisionVOaux.setIdBoveda(LONG_ONE);

					emision = emisionDao.findEmisionLiberada(emisionVOaux);
					if (emision == null) {
						agregaErrorComun("No existe la emision", msgError);
						agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
						noError = false;
					}
				} else {
					agregaErrorComun("Emision invalida", msgError);
					agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
					noError = false;
				}
			}			//Valida que no exista una operación de cambio de boveda en transito			List<OperacionSic> operacionesCambiBovedaTransito = 				operacionSicDao.findOperacionSicCambioBovedaEnTransito(emision.getIdEmision());			if(noError && operacionesCambiBovedaTransito != null && !operacionesCambiBovedaTransito.isEmpty()) {				String msgErrorCambioBoveda = 					"Existe una operacion de cambio de boveda en transito para la emision "	+											emision.getInstrumento().getClaveTipoValor() + " | " + 					emision.getEmisora().getClavePizarra() + " | " + 					emision.getSerie();				agregaErrorComun(msgErrorCambioBoveda, msgError);				agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);				agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);				agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);				agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);				noError = false;			}
			/* Valida el cupon vigente */
			if (noError && emision != null) {
				Cupon cuponAux = cuponDao.findCuponByIdEmision(emision.getIdEmision());

				if (cuponAux == null ||
						(StringUtils.isNotBlank(cupon) &&
						!StringUtils.equalsIgnoreCase(cupon, cuponAux.getClaveCupon()))
						) {
					agregaErrorComun("Error en la emision, cupon invalido", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
					noError = false;
				} else {
					emisionVO.setCuponCortado("F");
				}
			}

			if (noError) {
				/* Valida el saldo disponible (la posicion) de la emision, cuando son entregas (tipoOperacion='E') */
				Instrumento instrumento = (emision != null) ? emision.getInstrumento() : null;

				if (instrumento == null || instrumento.getMercado() == null) {
					agregaErrorComun("No existe el mercado", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					noError = false;
				} 
			}		}
		

		 /* Se valida la fecha de notificacion que trae el archivo plano, sea una fecha actual*/        String fechaNot = fechaArchivo2fechaFileTransfer(campos[((Integer) indice.get(FECHA_NOT)).intValue()]);        try {            if (simpleDateFormat.parse(fechaNot).compareTo(fechaActualHrCero) != 0) {                agregaErrorComun("Error: La fecha de notificación debe ser la actual", msgError);                agregaErrorComun(((Integer) indice.get(FECHA_NOT)).toString(), numCampoError);                noError = false;            }        }        catch (ParseException e) {            agregaErrorComun("Error: en la conversión de la fecha de notificación", msgError);            agregaErrorComun(((Integer) indice.get(FECHA_NOT)).toString(), numCampoError);            noError = false;        }        /* Se valida la fecha de liquidacion que trae el archivo plano sea habil */        String fechaLiq = fechaArchivo2fechaFileTransfer(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);        Date fechaLiqD = null;        try {        	fechaLiqD = simpleDateFormat.parse(fechaLiq);            Calendar calendarFechaLiq=Calendar.getInstance();            calendarFechaLiq.setTime(fechaLiqD);                        if (calendarFechaLiq.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || calendarFechaLiq.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){                  		agregaErrorComun("Error: La fecha de liquidación debe ser día hábil", msgError);        		agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);        		noError = false;        	}                   }        catch (ParseException e) {        	agregaErrorComun("Error: en la conversion de la fecha de liquidación", msgError);        	agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);        	noError = false;        }                                   
        /* Se valida la fecha de operacion que trae el archivo plano sea habil */        String fechaOper = fechaArchivo2fechaFileTransfer(campos[((Integer) indice.get(FECHA_OPER)).intValue()]);        Date fechaOperD = null;        try {            fechaOperD = simpleDateFormat.parse(fechaOper);            Calendar calendarFechaOper=Calendar.getInstance();            calendarFechaOper.setTime(fechaOperD);                        if (calendarFechaOper.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || calendarFechaOper.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){     	            	agregaErrorComun("Error: La fecha de operación debe ser día hábil", msgError);            	agregaErrorComun(((Integer) indice.get(FECHA_OPER)).toString(), numCampoError);            	noError = false;            }        }        catch (ParseException e) {        	agregaErrorComun("Error: En la conversión de la fecha de operación", msgError);        	agregaErrorComun(((Integer) indice.get(FECHA_OPER)).toString(), numCampoError);        	noError = false;        }        /* Se valida que la fecha de operacion sea menor o igual a la de liquidacion */        if (fechaLiqD != null && fechaOperD != null) {            if (fechaLiqD.compareTo(fechaOperD) < 0) {            	agregaErrorComun("Error: La fecha de operación debe ser menor o igual a la fecha de liquidación",msgError);                agregaErrorComun(((Integer) indice.get(FECHA_OPER)).toString(), numCampoError);                noError = false;            }        }        /* Se valida que la fecha de liquidacion sea menor a lo mas 7 dias habiles de la fecha actual de captura */        if (fechaLiqD.compareTo(dateUtilService.agregarDiasHabiles(dateUtilService.getCurrentDate(), -7)) < 0) {            agregaErrorComun("Error: La fecha de liquidaci\u00F3n no debe ser menor a 7 d\u00EDas h\u00E1biles de la fecha actual.", msgError);            agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);            noError = false;        }        /* Modificiacion 29/05/2012, se agrega validacion la fecha de operación no puede ser anterior ala fecha acutal*/        /*         * se elimina esta regla para que se permita la captura de operaciones con fecha Operacion anterior a la actual         */        /*if (fechaOperD != null) {            if (fechaOperD.compareTo(fechaActualHrCero) < 0) {            	agregaErrorComun("Error: La fecha de operación no puede ser menor a la fecha actual",msgError);                agregaErrorComun(((Integer) indice.get(FECHA_OPER)).toString(), numCampoError);                noError = false;            }        }*/

        /* Se valida que la cantidad operada sea mayor a cero */
        if (!validaCantidades(campos[((Integer) indice.get(CANTIDAD)).intValue()])) {
            agregaErrorComun("Error: La cantidad operada debe ser mayor a cero", msgError);
            agregaErrorComun(((Integer) indice.get(CANTIDAD)).toString(), numCampoError);
            noError = false;
        }
        if (emision != null) {
            /* Verificamos si los parametros ya contienen al custodio y al depositante */
        	String custodio = campos[((Integer) indice.get(CUSTODIO)).intValue()];//contiene la descripcion del custodio
        	String depositante = campos[((Integer) indice.get(DEPOSITANTE)).intValue()];
        	String tipoOper = campos[((Integer) indice.get(TIPO_OPER)).intValue()];

        	/* Validamos el tipo operacion y el custodio */ 
        	if (custodiosPredefinidos.contains(custodio.trim()) && tipoOper.equalsIgnoreCase(TRASPASO_CONTRA)) {
        		agregaErrorComun(messageResolver.getMessage("J0012"), msgError);
        		agregaErrorComun(((Integer) indice.get(CUSTODIO)).toString(), numCampoError);
        		noError = false;
        	}
        	else {//MOdificar esta condicion para contrapago
        		/* Validamos los datos del custodio y el depositante */
        		List<CatBic> catBics = null;
        		Long[] agenteVOs = null;
        		
        		agenteVOs = utilDivIntService.obtieneCatBics(emisionVOaux);
        		
        		if ((agenteVOs == null) || (agenteVOs.length == 0)) {
        			agregaErrorComun("Error con el custodio", msgError);
        			agregaErrorComun(((Integer) indice.get(CUSTODIO)).toString(), numCampoError);
        			noError = false;
        		}
        		else {
        			catBics = catBicDao.findCatBic(agenteVOs, custodio);         			//catBics = catBicDao.findCatBicByIds(agenteVOs);
            		if ((catBics == null) || (catBics.isEmpty()) || catBics.size() != 1 ) {
            			log.error("Esta vacia la lsita de CATBIC");
            			agregaErrorComun("Error con el custodio", msgError);
            			agregaErrorComun(((Integer) indice.get(CUSTODIO)).toString(), numCampoError);
            			noError = false;
            		}
            		else {
            			agenteVOs = new Long[catBics.size()];            			Boolean isLiquidacionParcial = Boolean.FALSE;
            			for (int i = 0; i < catBics.size(); i++) {log.debug("### CatBic: " + catBics.get(i).toString());
            				agenteVOs[i] = catBics.get(i).getIdCatbic();            				String bicProd = catBics.get(i).getDetalleCustodio();            				if(!bicProd.equalsIgnoreCase(custodio.trim())){            					log.error("Error la descripcion de custodio no corresponde");                    			agregaErrorComun("Error con el custodio", msgError);                    			agregaErrorComun(((Integer) indice.get(CUSTODIO)).toString(), numCampoError);                    			noError = false;            				}            				if(catBics.get(i).getCuentaNombrada().getIdCuentaNombrada() != null){            					Boolean configuracionParcial = settlementDisciplineRegimeDao.getSettlementDisciplineRegimeByIdCuentaBoveda(catBics.get(i).getCuentaNombrada().getIdCuentaNombrada(), Constantes.PARTIAL_SETTLEMENT);            					if(configuracionParcial && !isLiquidacionParcial){            						isLiquidacionParcial = Boolean.TRUE;            					}            				}
            			}            			            			String tipoMovimiento = campos[((Integer) indice.get(TIPO_MOV)).intValue()];            			if(tipoMovimiento.equals(TIPO_MOVTO_R) && !isLiquidacionParcial            					&& StringUtils.isNotEmpty(campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()])                        		&& campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()].equals(LIQ_PARTIAL_VALUE)){        					log.error("Error: El Custodio no permite Liquidaciones Parciales.");                			agregaErrorComun("Error: El Custodio no permite Liquidaciones Parciales.", msgError);                			agregaErrorComun(((Integer) indice.get(TIPO_MOV)).toString(), numCampoError);                			noError = false;            			}            			
        				SicDetalle sicDetalle = sicDetalleDao.findSicDetalle(agenteVOs, depositante);
        				if (sicDetalle == null) {
        					log.error("NO hay DEPOSITANTES");
                			agregaErrorComun("Error con el depositante", msgError);
                			agregaErrorComun(((Integer) indice.get(DEPOSITANTE)).toString(), numCampoError);
                			noError = false;
        				}
            		}
        		}
        	}
        }

        /* Si la operacion es de tipo TCP se validan el importe y la divisa */
        String tipoOper = campos[((Integer) indice.get(TIPO_OPER)).intValue()];

        if (tipoOper.equalsIgnoreCase(TRASPASO_CONTRA)) {
            if (tam < LONGITUD_TRASP_DIV_INT_INTER) {
                agregaErrorComun("Error el importe y la divisa son requeridos", msgError);
                agregaErrorComun(((Integer) indice.get(IMPORTE)).toString(), numCampoError);
                agregaErrorComun(((Integer) indice.get(DIVISA)).toString(), numCampoError);
                noError = false;
            }
            else {
                /* Se valida que el importe sea mayor a cero */
                if (!validaCantidades(campos[((Integer) indice.get(IMPORTE)).intValue()])) {
                    agregaErrorComun("Error el importe debe ser mayor a cero", msgError);
                    agregaErrorComun(((Integer) indice.get(IMPORTE)).toString(), numCampoError);
                    noError = false;
                }

                /* Se verifica que la divisa sea valida */
                String divisa = campos[((Integer) indice.get(DIVISA)).intValue()];
                Divisa[] divisas = divisaDao.findDivisas();
                boolean found = false;
                if (divisas != null) {
                	for (int i = 0; i < divisas.length; i++) {
                		if (divisa.equalsIgnoreCase(divisas[i].getClaveAlfabetica())) {
                			found = true;
                			break;
                		}
                	}
                }
                if (!found) {
                	agregaErrorComun("Error con la divisa", msgError);
                	agregaErrorComun(((Integer) indice.get(DIVISA)).toString(), numCampoError);
                	noError = false;
                }
            }
        }
        return (noError);
    }

    /**
     * Ensambla el objeto FileTransfer
     * @param fileTransfer
     * @param indice
     * @param campos
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @param cuentaRecep
     * @throws BusinessException
     */
    private void llenaFileTransferDivInt(FileTransfer fileTransfer, Map<Object, Object> indice, String[] campos, int tam, AgenteVO agenteVO, EmisionVO emisionVO, Map cuentaRecep) throws BusinessException {
    	fileTransfer.setIdInstTrasp(agenteVO.getId());
        fileTransfer.setFolioInstTrasp(agenteVO.getFolio());
        fileTransfer.setCuentaTrasp(agenteVO.getCuenta());
        fileTransfer.setIdInstRecep(agenteVO.getId());
        fileTransfer.setFolioInstRecep(agenteVO.getFolio());
       // fileTransfer.setCuentaRecep((StringUtils.isNotBlank((String) cuentaRecep.get("cuentaRecep"))) ? (String) cuentaRecep.get("cuentaRecep") : "0");
        fileTransfer.setCuentaRecep(agenteVO.getCuenta());        fileTransfer.setTv(emisionVO.getTv());
        fileTransfer.setEmisora(emisionVO.getEmisora());
        fileTransfer.setSerie(emisionVO.getSerie());
        fileTransfer.setCupon(emisionVO.getCupon());
        fileTransfer.setBajaLogica(emisionVO.getCuponCortado());
        fileTransfer.setMercado(emisionVO.getMercado());
        fileTransfer.setIsin(emisionVO.getIsin());
        fileTransfer.setCantidadTitulos(campos[((Integer) indice.get(CANTIDAD)).intValue()]);

        String tipoOper = campos[((Integer) indice.get(TIPO_OPER)).intValue()];
        String tipoMov = campos[((Integer) indice.get(TIPO_MOV)).intValue()];

        fileTransfer.setTipoOperacion(tipoOper);
        fileTransfer.setTipoMovto(tipoMov);

        if (tipoOper.equalsIgnoreCase(TRASPASO_LIBRE)) {
            if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_E)) {
                fileTransfer.setTipoMensaje(TMSJ_542);
                fileTransfer.setEstatusOper("N");                fileTransfer.setLiquidacionParcial("NO");
            }
            else if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_R)) {
                fileTransfer.setTipoMensaje(TMSJ_540);
                fileTransfer.setEstatusOper("N");                // Liquidacion Parcial                if(StringUtils.isNotEmpty(campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()])                		&& campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()].equals(LIQ_PARTIAL_VALUE)){                	fileTransfer.setLiquidacionParcial("SI");                } else {                	fileTransfer.setLiquidacionParcial("NO");                }
            }
        }
        else if (tipoOper.equalsIgnoreCase(TRASPASO_CONTRA)) {
            if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_E)) {
                fileTransfer.setTipoMensaje(TMSJ_543);
                fileTransfer.setEstatusOper("N");                fileTransfer.setLiquidacionParcial("NO");
            }
            else if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_R)) {
                fileTransfer.setTipoMensaje(TMSJ_541);
                fileTransfer.setEstatusOper("N");/*CAMBIO 04/09/2012 a nuevo para insertar en bitacora X por N*/                // Liquidacion Parcial                if(StringUtils.isNotEmpty(campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()])                		&& campos[((Integer) indice.get(LIQ_PARCIAL)).intValue()].equals(LIQ_PARTIAL_VALUE)){                	fileTransfer.setLiquidacionParcial("SI");                } else {                	fileTransfer.setLiquidacionParcial("NO");                }
            }
        }

        fileTransfer.setFechaNotificacion(campos[((Integer) indice.get(FECHA_NOT)).intValue()]);
        fileTransfer.setFechaLiquidacion(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);
        fileTransfer.setFechaOperacion(campos[((Integer) indice.get(FECHA_OPER)).intValue()]);
        fileTransfer.setDescCustodio(campos[((Integer) indice.get(CUSTODIO)).intValue()]);
        fileTransfer.setCuentaContraparte(campos[((Integer) indice.get(CUENTA_CONTRA)).intValue()]);
        fileTransfer.setDescContraparte(campos[((Integer) indice.get(CONTRAPARTE)).intValue()]);
        fileTransfer.setNombreDepositante(campos[((Integer) indice.get(DEPOSITANTE)).intValue()]);
        fileTransfer.setNombreCuentaBeneficiario(campos[((Integer) indice.get(NOMBRE_CUENTA_BEN)).intValue()]);
        fileTransfer.setNumeroCuentaBeneficiario(campos[((Integer) indice.get(NUMERO_CUENTA_BEN)).intValue()]);

        if (tam > LONGITUD_TRASP_DIV_INT_MIN) {
            fileTransfer.setImporte(campos[((Integer) indice.get(IMPORTE)).intValue()]);
            fileTransfer.setDivisa(campos[((Integer) indice.get(DIVISA)).intValue()]);
        }

        if (tam > LONGITUD_TRASP_DIV_INT_INTER) {
            fileTransfer.setInstruccEspeciales(campos[((Integer) indice.get(INST_ESP)).intValue()]);
        }

        fileTransfer.setFechaReg(dateUtilService.getCurrentDate());
    }
    
    /**
     * Llena los campos en caso de un error en el formato de la entrada
     * @param fileTransfer
     */
    private void fillErrorCaseFields(FileTransfer fileTransfer) {
        fileTransfer.setCantidadTitulos(emptyString);
        fileTransfer.setTipoMensaje(emptyString);
        fileTransfer.setTipoOperacion(emptyString);
        fileTransfer.setFechaNotificacion(emptyString);
        fileTransfer.setFechaLiquidacion(emptyString);
        fileTransfer.setFechaOperacion(emptyString);
        fileTransfer.setTipoMovto(emptyString);
        fileTransfer.setIdInstTrasp(emptyString);
        fileTransfer.setIdInstRecep(emptyString);
        fileTransfer.setFolioInstRecep(emptyString);
        fileTransfer.setFolioInstTrasp(emptyString);
        fileTransfer.setCuentaRecep(emptyString);
        fileTransfer.setCuentaTrasp(emptyString);
        fileTransfer.setEstatusOper(emptyString);
    }
    
	/**
	 * Interpreta una entrada del archivo
	 * @param agenteFirmado
	 * @param fileTransfer
	 * @param informacion
	 * @throws BusinessDataException
	 * @throws BusinessException
	 */
	private void parseaInformacion(AgenteVO agenteFirmado, FileTransfer fileTransfer, String informacion) throws BusinessDataException, BusinessException {
        if (informacion == null) {
            throw new BusinessDataException(messageResolver.getMessage("J0053", new Object[] {"informacion"}),"J0053");
        }

        boolean error = false;
        StringBuilder msgError = new StringBuilder();
        StringBuilder numCampoError = new StringBuilder();
        String cadenaErrorFormato = messageResolver.getMessage("J0051");

        int tam = informacion.length();

        /* Obtiene los campos correspondientes al layout */
        String[] campos = camposTraspasosDivInt(informacion);

        if (campos == null) {
            msgError.append(cadenaErrorFormato);
            msgError.append("en la estructura del registro. Tamaño del registro incorrecto: " + informacion.length() + "");
            msgError.append(GUION);
            fillErrorCaseFields(fileTransfer);
            error = true;
        }
        else {

            AgenteVO agenteVO = new AgenteVO();
            EmisionVO emisionVO = new EmisionVO();
            Map cuentaRecep = new HashMap();

            /* Obtiene el mapa de indices (true) correspondiente al layout */
            Map<Object, Object> indice = indicesNombresTraspasosDivInt;
            if (validaFormato(indicesNombresTraspasosDivInt, campos, msgError, numCampoError, tam, agenteVO, emisionVO)) {
                if (!validaNegocio(agenteFirmado, indicesNombresTraspasosDivInt, campos, msgError, numCampoError, tam, agenteVO, emisionVO, cuentaRecep)) {
                	error = true;
                }
                llenaFileTransferDivInt(fileTransfer, indice, campos, tam, agenteVO, emisionVO, cuentaRecep);
            }
            else {
            	fillErrorCaseFields(fileTransfer);
                error = true;
            }
        }
        if (error) {
            fileTransfer.setEstadoReg(ESTADO_ERROR);
            if (numCampoError.length() > 0) {
                fileTransfer.setCamposError(numCampoError.toString().substring(0,numCampoError.length() - 1));
            }
            fileTransfer.setError(new ClobImpl(msgError.toString().substring(0, msgError.length() - 1)));
            fileTransfer.setFechaReg(dateUtilService.getCurrentDate());
        }
        else {
            fileTransfer.setEstadoReg(ESTADO_NUEVO);
        }
    }
	    
    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#almacenaInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
     */
    public Integer almacenaInformacion(FileTransferVO fileTransferVO) throws BusinessException {

    	/* Validamos los parametros */
    	if (fileTransferVO == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO"}));
    	}
    	
    	AgenteVO agenteFirmado = fileTransferVO.getAgenteFirmado();
    	if (agenteFirmado == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.agenteFirmado"}));
    	}
        if (!agenteFirmado.tieneClave()) {
        	throw new BusinessException(messageResolver.getMessage("J0054"));
        }
        
        String tipoProceso = fileTransferVO.getTipoProceso();
        if ((tipoProceso == null) || (tipoProceso.length() == 0)) {
        	throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.tipoProceso"}));
        }
        
        String[] informacionArchivo = fileTransferVO.getInformacionArchivo();
        if ((informacionArchivo == null) || (informacionArchivo.length == 0)) {
        	throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.informacionArchivo"}));
        }
        
        Integer offset = fileTransferVO.getOffset();
        if (offset == null) {
        	throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.offset"}));
        }
        if (offset >= informacionArchivo.length) {
            throw new BusinessDataException(messageResolver.getMessage("J0045", new Object[] {Integer.toString(informacionArchivo.length) }), "J0045");
        }

        
       log.debug("Offset: [" + offset.intValue() + "]");
        /* Borra la informacion antes de almacenarla */
        if (offset.intValue() == 0) {
        	log.error("El offset es 0");
            cancelaProcesoDivInt(agenteFirmado, tipoProceso);
        } else {
        	log.error("El offset viene distinto de cero");
        }

        /* Realiza la lectura y almacenamiento de registros */
        long inicioProceso = System.currentTimeMillis();
        int ultimoRegistro = 0;
        for (int i = offset; i < informacionArchivo.length; i++) {
        	
        	/* Ensamblamos el objeto FileTransfer */
        	FileTransfer fileTransfer = new FileTransfer();
            fileTransfer.setIdInst(agenteFirmado.getId());
            fileTransfer.setFolioInst(agenteFirmado.getFolio());
            fileTransfer.setTipoReg(tipoProceso);
            fileTransfer.setConsecReg(BigInteger.valueOf(i+1));
            fileTransfer.setUsuario(fileTransferVO.getNombreUsuario());
            fileTransfer.setIdBoveda(LONG_ONE);
            
            /* Procesamos la informacion del registro del archivo */
            parseaInformacion(agenteFirmado, fileTransfer, informacionArchivo[i]);
            
            /* Almacenamos el registro de transferencia */
            fileTransferDao.save(fileTransfer);
            ultimoRegistro = i;

            long finalProceso = System.currentTimeMillis();
            if ((finalProceso - inicioProceso) > 10000) {
                break;
            }
        }
        
        /* Verificamos el numero de registros procesados */
        if ((ultimoRegistro + 1) == informacionArchivo.length) {
            return -1;
        }
        return (ultimoRegistro + 1);
    }

    /**
     * Obtiene la emisi&oacute;n por su ISIN
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
    /**     * Transforma un objeto FileTransfer a GrabaOpeacionParamns     * @param fileTransfer     * @return OperacionSic     * @throws ParseException     * @throws BusinessException     */   /* private GrabaOperacionParams  creaOperacionParams(FileTransfer fileTransfer) throws ParseException, BusinessException {    	GrabaOperacionParams params = new GrabaOperacionParams();    	return params;    }*/
        /**
     * Transforma un objeto FileTransfer a OperacionSic
     * @param fileTransfer
     * @return OperacionSic
     * @throws ParseException
     * @throws BusinessException
     */
   /* private OperacionSic creaOperacionSic(FileTransfer fileTransfer) throws ParseException, BusinessException {

    	OperacionSic operacionSIC = null;

    	Se valida el objeto FileTransfer recibido y se arma el OperacionSic 
    	if(fileTransfer != null){
	
    		operacionSIC = new OperacionSic();
    
    		operacionSIC.setTipoTraspaso(fileTransfer.getTipoOperacion());
    		operacionSIC.setTipoOperacion(fileTransfer.getTipoMovto());
    
    		Se ensambla el objeto AgenteVO con los datos del Agente Traspasante 
    		AgenteVO agenteTraspasante = new AgenteVO(fileTransfer.getIdInstTrasp(),fileTransfer.getFolioInstTrasp(), fileTransfer.getCuentaTrasp());

    		 Se recupera la CuentaNombrada 
    		CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteTraspasante);
    
    		 Se asigna la Cuenta Nombrada en el objeto OperacionSic 
    		operacionSIC.setCuentaNombrada(cuentaNombrada);
    		
    		operacionSIC.setFechaLiquidacion( simpleDateFormat.parse(fechaArchivo2fechaFileTransfer(fileTransfer.getFechaLiquidacion())) );
    		operacionSIC.setFechaNotificacion( simpleDateFormat.parse(fechaArchivo2fechaFileTransfer(fileTransfer.getFechaNotificacion())) );
    		operacionSIC.setFechaOperacion( simpleDateFormat.parse(fechaArchivo2fechaFileTransfer(fileTransfer.getFechaOperacion())) );
    
    		 Se ensambla el objeto EmisionVO 
    		EmisionVO emisionVO = new EmisionVO(fileTransfer.getTv(), fileTransfer.getEmisora(), fileTransfer.getSerie(), fileTransfer.getCupon());
    		Emision emision = emisionDao.findEmisionLiberada(emisionVO);

    		 Se settea la Emision en el objeto OperacionSic 
    		operacionSIC.setEmision(emision);
    
    		operacionSIC.setBoveda(new Boveda());
    		operacionSIC.getBoveda().setIdBoveda(fileTransfer.getIdBoveda());

    		 Recupera el objeto SICEmision  que corresponde a la emision recibida 
    		List listaSicEmision =  sicEmisionDao.findSicEmisionesByEmision(emisionVO);
    
    		 Recupera las CuentaNombrada de la lista de SICEmision y se construye una lista de AgenteVO 
    		List<Long> listaCatBics = new ArrayList<Long>();
    
    		for (Iterator<SicEmision> iterator = listaSicEmision.iterator(); iterator.hasNext();) {
    			SicEmision sicEmision = (SicEmision) iterator.next();
    			if(sicEmision != null && sicEmision.getCatBic() != null && sicEmision.getCatBic().getIdCatbic() != null) {
    				listaCatBics.add(sicEmision.getCatBic().getIdCatbic());
    			}

    		}

    		if(listaCatBics != null && !listaCatBics.isEmpty()){

    			 Se construye un arreglo de AgenteVO 
    			Long[] arregloAgenteVO = listaCatBics.toArray(new Long[0]);

    			 Se recupera y se asigna el CatBic 
    			List<CatBic> listaCatBic = catBicDao.findCatBic(arregloAgenteVO, fileTransfer.getDescCustodio());
    			operacionSIC.setCatBic(listaCatBic.get(0));
    			
    			if ((listaCatBic != null) && (!listaCatBic.isEmpty())) {
        			arregloAgenteVO = new Long[listaCatBic.size()];
        			for (int i = 0; i < listaCatBic.size(); i++) {
        				arregloAgenteVO[i] = listaCatBic.get(i).getIdCatbic();
        			}
    				 Se recupera y se asigna el objeto SicDetalle 
        			SicDetalle sicDetalle = sicDetalleDao.findSicDetalle(arregloAgenteVO, fileTransfer.getNombreDepositante());
        			operacionSIC.setSicDetalle(sicDetalle);
    			}

    		}
    
    		operacionSIC.setCuentaContraparte(fileTransfer.getCuentaContraparte());
    		operacionSIC.setDescContraparte(fileTransfer.getDescContraparte());
    		operacionSIC.setInstruccEspeciales(fileTransfer.getInstruccEspeciales());
    		operacionSIC.setNomCtaBenef(fileTransfer.getNombreCuentaBeneficiario());
    		operacionSIC.setNumCtaBenef(fileTransfer.getNumeroCuentaBeneficiario());
    		operacionSIC.setImporte(StringUtils.isNotBlank(fileTransfer.getImporte()) ? new BigDecimal(fileTransfer.getImporte().trim()) : null);
    		operacionSIC.setDivisa(fileTransfer.getDivisa());
    		operacionSIC.setCantidadTitulos(new BigInteger(fileTransfer.getCantidadTitulos().trim()));
    		operacionSIC.setCuentaRecep(fileTransfer.getCuentaRecep());
    		
    		EstatusOperacion estatusOperacion = new EstatusOperacion();    		    		if (fileTransfer.getTipoMensaje().contains(TCP_RECEPCION)) {
    			estatusOperacion.setIdEstatusOperacion(ST_OPER_PENDIENTE_AUTORIZAR);
    		}    	 	se agrega la condicion para los tlp_recepcion, se inserta el estatus como notificada    		if(fileTransfer.getTipoMensaje().contains(TLP_RECEPCION)){    			estatusOperacion.setIdEstatusOperacion(ST_OPER_NOTIFICADA);    		}
    	
    		operacionSIC.setEstatusOperacion(estatusOperacion);
    		operacionSIC.setFolioControl(utilService.getFolio(SEQ_FOLIO_CONTROL));
    	}

    	return (operacionSIC);
    }*/
    

    /**
     * Registra la informaci&oacute;n de traspasos
     * @param agenteFirmado
     * @param tipoProceso
     * @param consecProtocolo
     * @throws BusinessException
     * @throws ParseException
     */
    private void grabaInformacionTraspasosDI(AgenteVO agenteFirmado, String tipoProceso, Map consecProtocolo) throws BusinessException, ParseException {

    	/* Obtiene la lista de instancias FileTransferDivInt */
        List<FileTransfer> fileTransfers = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

        Boolean isProtocolo = null;
        
        log.error("Numero de operaciones: [" + fileTransfers.size() + "]");

        for (int i = 0; i < fileTransfers.size(); i++) {
            FileTransfer fileTransfer = fileTransfers.get(i);
            if (consecProtocolo != null && !consecProtocolo.isEmpty() && consecProtocolo.containsKey(fileTransfer.getConsecReg())) {
            	if (((Integer)consecProtocolo.get(fileTransfer.getConsecReg())).intValue() == 1) {
                    isProtocolo = Boolean.TRUE;
                }
                else {
                    isProtocolo = null;
                }
            }
            else {
                isProtocolo = Boolean.FALSE;
            }

            if (isProtocolo != null) {
                if (isProtocolo) {
                    fileTransfer.setEstadoReg(ESTADO_A_PROTOCOLO);
                    fileTransferDao.update(fileTransfer);
                }
            }
            else {
                fileTransfer.setEstadoReg(ESTADO_ERROR);
                fileTransferDao.update(fileTransfer);
            }
            /*if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_NUEVO)) {
            	obtieneEmisionByIsin(fileTransfer);             	/*CAMBIO 04/09/2012  cambiar para que inserte en t_bitacora de operaciones
            	//divisionInternacionalService.insertaOperacionSIC(creaOperacionSic(fileTransfer));            	divisionInternacionalService.grabaOperacion(creaOperacionParams(fileTransfer));            	            	fileTransfer.setEstadoReg(ESTADO_CARGADO);
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
    private List<Object> generaDatosDivInt(FileTransfer fileTransfer, String tipoProceso,CamposDivIntVO camposVO) throws BusinessException, ParseException {
    	log.debug("FileTransferServiceImpl :: generaDatosDivInt");
    	/* Obtiene lista de campos con error */
		String[] numeroCampoError = null;
		if (fileTransfer.getCamposError() != null) {
			numeroCampoError = fileTransfer.getCamposError().split("-");
			if (numeroCampoError == null) {
				throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"numeroCampoError"}));
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
//		indiceCampo = FactoryDivInt.indicesNombresTraspasosDivInt(true);
		indiceCampo = indicesNombresTraspasosDivInt;

		/* Genera el objeto de salida del servicio */
		List<Object> listaCamposArchivo = new ArrayList<Object>();

		boolean error = false;

		/* Verificacion y setteo de los datos del traspasante */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_INST_VEND)));
		CampoArchivoVO campoArchivoVO = FactoryDivInt.generaArchivoVO(
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

		/* Verificacion y setteo de los datos del receptor */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstRecep().length(), 
				ID_RECEP, error,fileTransfer.getIdInstRecep(), null);
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
		
		/* Verificacion y setteo de los atributos de la clave-valor */
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
		
		/*  Verificacion y asignacion de la cantidad */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CANTIDAD)));
		BigInteger cantidad = null;
		if (!error) {
			if(StringUtils.isNotBlank(fileTransfer.getCantidadTitulos()) &&
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

		/* Verificacion y setteo del tipo de operacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_OPER)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoOperacion().length(), 
				TIPO_OPER, error, fileTransfer.getTipoOperacion(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoOperacion(fileTransfer.getTipoOperacion());

		/* Verificacion y setteo del tipo de movimiento */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_MOV)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoMovto(), 
				TIPO_MOV, error, fileTransfer.getTipoMovto(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoMovimiento(fileTransfer.getTipoMovto());				/* Verificacion y setteo de liquidacion parcial */		error = listaCamposError.contains(String.valueOf(indiceCampo.get(LIQ_PARCIAL)));        		campoArchivoVO = FactoryDivInt.generaArchivoVO(				fileTransfer.getLiquidacionParcial(), 				LIQ_PARCIAL, error, fileTransfer.getLiquidacionParcial(), null);		listaCamposArchivo.add(campoArchivoVO);        camposVO.setLiquidacionParcial(fileTransfer.getLiquidacionParcial());        
		/* Verificacion y setteo de la fecha de notificacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_NOT)));
		Date fecha = null;
		String fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaNotificacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
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

		/* Verificacion y setteo de la fecha de liquidacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_LIQ)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaLiquidacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
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

		/* Verificacion y setteo de la fecha de operacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_OPER)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaOperacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
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

		/* Verificacion de los datos del custodio */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUSTODIO)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getDescCustodio(), 
				CUSTODIO, error, fileTransfer.getDescCustodio(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDescCustodio(fileTransfer.getDescCustodio());

		/* Verificacion de los datos de la contraparte */
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

		/* Verificacion de los datos del depositante */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(DEPOSITANTE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getNombreDepositante(), 
				DEPOSITANTE, error, fileTransfer.getNombreDepositante(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNombreDepositante(fileTransfer.getNombreDepositante());

		/* Verificacion de los datos del beneficiario */
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

		/* Verificacion de los datos del importe y divisa */
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

		/* Verificacion de las instrucciones especiales */
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

		return listaCamposArchivo;
	}

    /**
     * @param fileTransferVO
     * @return
     * @throws BusinessException
     * @throws ParseException
     */
    private TotalesProcesoVO muestraInformacionDivInt(FileTransferVO fileTransferVO) throws BusinessException, ParseException {

    	AgenteVO agenteFirmado = fileTransferVO.getAgenteFirmado();
        String tipoProceso = fileTransferVO.getTipoProceso();
        PaginaVO paginaVO = fileTransferVO.getPaginaVO();

        /* Valida parametros */
        if (!agenteFirmado.tieneClave()) {
        	throw new BusinessException(messageResolver.getMessage("J0054"));
        }
        TotalesProcesoVO totalesProcesoVO = new TotalesProcesoVO();
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        List<FileTransfer> fileTransfers = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso); 
            
        if ((fileTransfers == null) || (fileTransfers.isEmpty())) {
        	throw new BusinessDataException(messageResolver.getMessage("J0046", new Object[] {"fileTransfers" }), "J0046");
        }

        ArrayList listaRegistros = new ArrayList();
        int registrosError = 0;
        int totalregistros = 0;
        for (int i = 0; i < fileTransfers.size(); i++) {
        	FileTransfer fileTransfer = fileTransfers.get(i);
        	if ((!fileTransferVO.isSoloErrores()) || (fileTransferVO.isSoloErrores() && fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_ERROR))) {
                    
        		RegistroProcesadoVO registroProcesadoVO = new RegistroProcesadoVO();
        		CamposDivIntVO camposDivIntVO = new CamposDivIntVO();

        		registroProcesadoVO.setConsec(fileTransfer.getConsecReg());
        		registroProcesadoVO.setDatos(generaDatosDivInt(fileTransfer, tipoProceso, camposDivIntVO));
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
        		registroProcesadoVO.setEstado(fileTransfer.getEstadoReg());
        		registroProcesadoVO.setFechaRegistro(fileTransfer.getFechaReg());
        		registroProcesadoVO.setNombreUsuario(fileTransfer.getUsuario());
        		registroProcesadoVO.setCamposDivIntVO(camposDivIntVO);

        		if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_ERROR)) {
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
    
    /**
     * @param agenteFirmado
     * @param tipoProceso
     * @return
     * @throws BusinessException
     */
    private ResumenVO obtieneResumenDivInt(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException {
    	log.debug("FileTransferServiceImpl :: obtieneResumenDivInt");
    	/* Valida parametros */
    	if (!agenteFirmado.tieneClave()) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0054"));
    	}
    	
    	int registrosCargados = 0;
    	int registrosError = 0;
    	int registrosNuevos = 0;
    	int totalRegistros = 0;
    	int registrosProtocolo = 0;
    	String nombreUsuario = "";
    	Date fechaCarga = null;

    	/* Obtiene la lista de instancias FileTransferDivInt */
    	List<FileTransfer> fileTransfers = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

    	if ((fileTransfers != null) && (!fileTransfers.isEmpty())) {
    		
    		for (int i = 0; i < fileTransfers.size(); i++) {
    			FileTransfer fileTransfer = fileTransfers.get(i);

    			if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_ERROR)) {
    				registrosError++;
    			}
    			else if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_NUEVO)) {
    				registrosNuevos++;
    			}
    			else if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_CARGADO)) {
    				registrosCargados++;
    			}
    			else if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_A_PROTOCOLO)) {
    				registrosProtocolo++;
    			}

    			nombreUsuario = fileTransfer.getUsuario();
    			fechaCarga = fileTransfer.getFechaReg();
    			totalRegistros++;
    		}

    		if (totalRegistros != (registrosCargados + registrosError + registrosNuevos + registrosProtocolo)) {
    			throw new BusinessDataException(messageResolver.getMessage("J0049", new Object[] {" "}), "J0049");
    		}

    	}

    	ResumenVO resumenVO = new ResumenVO();
    	resumenVO.setAgenteFirmado(agenteFirmado);
    	resumenVO.setFechaCarga(fechaCarga);
    	resumenVO.setTipoProceso(tipoProceso);
    	resumenVO.setTotalCargados(new Integer(registrosCargados));
    	resumenVO.setTotalError(new Integer(registrosError));
    	resumenVO.setTotalNuevos(new Integer(registrosNuevos));
    	resumenVO.setTotalRegistros(new Integer(totalRegistros));
    	resumenVO.setTotalProtocolo(new Integer(registrosProtocolo));
    	resumenVO.setNombreUsuario(nombreUsuario);

    	return (resumenVO);
    }
    
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#cancelaProceso(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public void cancelaProceso(FileTransferVO fileTransferVO) throws BusinessException {
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.tipoProceso"}));
		}
		this.cancelaProcesoDivInt(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso());
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#grabaInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public void grabaInformacion(FileTransferVO fileTransferVO) throws BusinessException {
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.tipoProceso"}));
		}
		if (fileTransferVO.getConsecProtocolo() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.consecProtocolo"}));
		}
		
		try {//aqui XX
			this.grabaInformacionTraspasosDI(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso(), fileTransferVO.getConsecProtocolo());
		}
		catch (ParseException pe) {
			throw new BusinessException(pe.toString(),pe);
		} catch( Exception ex ) {
			throw new BusinessException("Error al grabar el registro",ex);
		}
	}		/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#muestraInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public TotalesProcesoVO muestraInformacion(FileTransferVO fileTransferVO) throws BusinessException {		log.debug("FileTransferServiceImpl :: muestraInformacion");
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.tipoProceso"}));
		}
		try {
			return (this.muestraInformacionDivInt(fileTransferVO));
		}
		catch (ParseException pe) {
			throw new BusinessException(pe.toString());
		}
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#obtieneResumen(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public ResumenVO obtieneResumen(FileTransferVO fileTransferVO) throws BusinessException {		log.debug("FileTransferServiceImpl :: obtieneResumen");
		/* Validamos los parametros */
		if (fileTransferVO == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO"}));
		}
		if (fileTransferVO.getAgenteFirmado() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.agenteFirmado"}));
		}
		if (fileTransferVO.getTipoProceso() == null) {
			throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"fileTransferVO.tipoProceso"}));
		}
		return (this.obtieneResumenDivInt(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso()));
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
	 * @param fileTransferDao
	 */
	public void setFileTransferDao(FileTransferDao fileTransferDao) {
		this.fileTransferDao = fileTransferDao;
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
	}	/**	 * Método para establecer el atributo operacionSicDao	 * @param operacionSicDao El valor del atributo operacionSicDao a establecer.	 */	public void setOperacionSicDao(OperacionSicDao operacionSicDao) {		this.operacionSicDao = operacionSicDao;	}	/**	 * @return the settlementDisciplineRegimeDao	 */	public SettlementDisciplineRegimeDao getSettlementDisciplineRegimeDao() {		return settlementDisciplineRegimeDao;	}	/**	 * @param settlementDisciplineRegimeDao the settlementDisciplineRegimeDao to set	 */	public void setSettlementDisciplineRegimeDao(SettlementDisciplineRegimeDao settlementDisciplineRegimeDao) {		this.settlementDisciplineRegimeDao = settlementDisciplineRegimeDao;	}
	
}
