package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDao;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposDivIntVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.portalinternacional.persistence.dao.FileTransferDao;


@SuppressWarnings({"unchecked"})
public class FileTransferInversionExtranjeraServiceImpl implements FileTransferService, Constantes {
	
	private static final Logger log = LoggerFactory.getLogger(FileTransferInversionExtranjeraServiceImpl.class);
	
	/**
	 * List<Integer> 
	 */
	private static final List<Integer> posiciones = new ArrayList<Integer>();
	
	/**
	 * Map<Object,Object> 
	 */
	private static final Map<Object, Object> indicesNombresTraspasosDivIntInversionExtranjera = obtieneIndicesInvercionExtranjera();
	
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
	 * PosicionNombradaDao 
	 */
	private PosicionNombradaDao posicionNombradaDao = null;
	
	/**
	 * ValidatorDivIntService
	 */
	private ValidatorDivIntService validatorDivIntService = null;
	
	/**
	 * EmisionDao 
	 */
	private EmisionDao emisionDao = null;
	
	/**
	 * CuponDao 
	 */
	private CuponDao cuponDao = null;
	
	/**
	 * UtilService
	 */
	private UtilService utilService = null;
	
	/**
	 * Variable que define si es una compra
	 */
	private Boolean isCompra = null;
	
	/**
	 * M&eacute;todo estatico para llenar el mapa de indices
	 * @return Map<Object, Object>
	 */
	
	private static Map<Object,Object> obtieneIndicesInvercionExtranjera(){
		Map <Object,Object> mapa = new HashMap();
		
		mapa.put(FECHA_LIQ, new Integer(0));
		mapa.put(CLAVE_TRASP , new Integer(1));
		mapa.put(CUENTA_VEND, new Integer(2));
		mapa.put(CLAVE_RECEP, new Integer(3));
		mapa.put(CUENTA_RECEP, new Integer(4));
		mapa.put(TV, new Integer(5));
		mapa.put(EMISORA, new Integer(6));
		mapa.put(SERIE, new Integer(7));
		mapa.put(CUPON, new Integer(8));
		mapa.put(ISIN, new Integer(9));
		mapa.put(TIPO_OPER, new Integer(10));
		mapa.put(CANTIDAD, new Integer(11));
		
		return mapa;
	}
	
	/**
	 * Realiza la cancelaci&oacute;n de un proceso de carga
	 * @param agenteVO
	 * @param tipoProceso
	 * @throws BusinessException
	 */
	private void cancelaProcesoDivInt(AgenteVO agenteVO, String tipoProceso) throws BusinessException {
    	List<FileTransfer> listaFileTransferDivInt = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(agenteVO.getId(), agenteVO.getFolio(), tipoProceso);
    	/* Borra los registros de la lista */
    	for (int i = 0; i < listaFileTransferDivInt.size(); i++) {
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
        return campos;
    }
    
	/**
	 * Llena los indicies requeridos y separa en campos una entrada del archivo
	 * @param cadena
	 * @return String[]
	 * @throws BusinessDataException
	 * @throws BusinessException
	 */
	private String[] camposTraspasosDivInt(String cadena) throws BusinessDataException, BusinessException {

		/* Valida longitud permitida */

		int tam = cadena.length();
		if (tam != LONGITUD_TRASP_INVERSION_EXTRANJERA) {
			return null;
		}
		posiciones.clear();
		posiciones.add(new Integer(11));
		posiciones.add(new Integer(5));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(5));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(7));
		posiciones.add(new Integer(6));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(12));
		posiciones.add(new Integer(2));
		posiciones.add(new Integer(20));
		
		return obtieneCampos(cadena);
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
        
    	/** FECHA LIQUIDACION */
		int pos = ((Integer) indice.get(FECHA_LIQ)).intValue();
		if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11 || !campos[pos].matches(PATRON_FECHA)) {
			agregaErrorValidacion(msgError, " de la fecha de Liquidación ", numCampoError, pos);
			noError = false;
		}
		
        /**CLAVE TRASPASANTE*/
		pos =((Integer) indice.get(CLAVE_TRASP)).intValue();
		if(StringUtils.isBlank(campos[pos])|| campos[pos].length() != 5 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)){
			agregaErrorValidacion(msgError, " de la clave del Traspasante", numCampoError, pos);
            noError = false;
		}
		agenteVO.setId(campos[pos].substring(0, 2));
		agenteVO.setFolio(campos[pos].substring(2));
		
		/**CUENTA TRASPASANTE*/
		pos =((Integer) indice.get(CUENTA_VEND)).intValue();
		if(StringUtils.isBlank(campos[pos])|| campos[pos].length() != 4 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)){
			agregaErrorValidacion(msgError, " de la cuenta del Traspasante", numCampoError, pos);
            noError = false;
		}
		agenteVO.setCuenta(campos[pos]);
		
		/**CLAVE RECEPTOR*/
		pos =((Integer) indice.get(CLAVE_RECEP)).intValue();
		if(StringUtils.isBlank(campos[pos])|| campos[pos].length() != 5 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)){
			agregaErrorValidacion(msgError, " de la clave del Receptor", numCampoError, pos);
            noError = false;
		}
		
		/**CUENTA RECEPTOR*/
		pos =((Integer) indice.get(CUENTA_RECEP)).intValue();
		if(StringUtils.isBlank(campos[pos])|| campos[pos].length() != 4 || !campos[pos].matches(PATRON_NUMERICO_SIN_ESPACIO)){
			agregaErrorValidacion(msgError, " de la cuenta del Receptor", numCampoError, pos);
            noError = false;
		}
       
        /** TV */
        pos = ((Integer) indice.get(TV)).intValue();
        if (campos[pos].length() > 4 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del tv ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setTv(campos[pos]);

        /** EMISORA  */
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
            agregaErrorValidacion(msgError, " del cupón ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setCupon(campos[pos]);
        
        /** ISIN - Opcional si captura todos los datos de la emision (tv, emisora, serie, cupon) */
        pos = ((Integer) indice.get(ISIN)).intValue();
        if (campos[pos].length() != 12 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del isin ", numCampoError, pos);
            noError = false;
        }
        emisionVO.setIsin(campos[pos]);
        
        /** TIPO OPER */
        pos = ((Integer) indice.get(TIPO_OPER)).intValue();
        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() > 2) {
            agregaErrorValidacion(msgError, " del tipo de operación ", numCampoError, pos);
            noError = false;
        }
     						
        /** CANTIDAD */
        pos = ((Integer) indice.get(CANTIDAD)).intValue();
        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() > 20 || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " de la cantidad ", numCampoError, pos);
            noError = false;
        }
        return noError;
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
            elementosFecha.add(meses.get(fechaArchivo.substring(3, 6).toUpperCase()) != null ? meses
                            .get(fechaArchivo.substring(3, 6).toUpperCase()) : mesesEsp
                            .get(fechaArchivo.substring(3, 6).toUpperCase()));
            elementosFecha.add(fechaArchivo.substring(7));
            return elementosFecha.get(1) + "/" + elementosFecha.get(0) + "/"+ elementosFecha.get(2);           
        }
        return "";
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
    private boolean validaNegocio(AgenteVO agenteFirmado, Map<Object, Object> indice, String[] campos, StringBuilder msgError, 
    		StringBuilder numCampoError, int tam, AgenteVO agenteVO, EmisionVO emisionVO, Map cuentaRecep)throws BusinessException {					
        
    	boolean noError = true;
        
    	/* REGLAS DE NEGOCIO */
        
    	Date fechaActual = dateUtilService.getCurrentDate();
        Date fechaActualHrCero = dateUtilService.getFechaHoraCero(fechaActual);
        
        /** Valida que la institucion del archivo corresponda a la del agente firmado, ya sea el traspasante o el receptor*/ 
        if (!(campos[((Integer) indice.get(CLAVE_TRASP)).intValue()].equalsIgnoreCase(agenteFirmado.getClave()) || 
        		campos[((Integer) indice.get(CLAVE_RECEP)).intValue()].equalsIgnoreCase(agenteFirmado.getClave()))) {
            agregaErrorComun("La institución firmada no existe en el archivo", msgError);
            agregaErrorComun(((Integer) indice.get(CLAVE_TRASP)).toString(), numCampoError);
            agregaErrorComun(((Integer) indice.get(CLAVE_RECEP)).toString(), numCampoError);
            noError = false;
        }
        /** Valida si es una compra*/
        if( campos[((Integer) indice.get(CLAVE_RECEP)).intValue()].equalsIgnoreCase(agenteFirmado.getClave()) && 
        		!campos[((Integer) indice.get(CLAVE_TRASP)).intValue()].equalsIgnoreCase(agenteFirmado.getClave())){
        	isCompra = true;
        }else{
        	isCompra = false;
        }
					
        /** Valida que el traspasante exista */
        AgenteVO agenteTrasp = new AgenteVO(campos[((Integer)indice.get(CLAVE_TRASP)).intValue()].substring(0, 2)
        		,campos[((Integer)indice.get(CLAVE_TRASP)).intValue()].substring(2),campos[((Integer)indice.get(CUENTA_VEND)).intValue()]);
        CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteTrasp);
        agenteTrasp = ConvertBO2VO.crearAgenteVO(cuentaNombrada);
        
        if ((agenteTrasp == null) || (!agenteTrasp.tieneClave())) {
        	agregaErrorComun("No existe el traspasante o la cuenta", msgError);
        	agregaErrorComun(((Integer) indice.get(CLAVE_TRASP)).toString(), numCampoError);
        	agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
        	noError = false;
        }
        else {
            if (!validatorDivIntService.esCuentaValida(agenteTrasp)) {
                agregaErrorComun("La cuenta traspasante no es valida", msgError);
                agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
                noError = false;
            }
        }
          
        /**Valida que el receptor exista */
        AgenteVO agenteReceptor = new AgenteVO(campos[((Integer)indice.get(CLAVE_RECEP)).intValue()].substring(0, 2),
        		campos[((Integer)indice.get(CLAVE_RECEP)).intValue()].substring(2),campos[((Integer)indice.get(CUENTA_RECEP)).intValue()]);
        CuentaNombrada cuenta = cuentaNombradaDao.findCuenta(agenteReceptor);
        agenteReceptor = ConvertBO2VO.crearAgenteVO(cuenta);
        
        if ((agenteReceptor == null) || (!agenteReceptor.tieneClave())) {
        	agregaErrorComun("No existe el receptor o la cuenta", msgError);
        	agregaErrorComun(((Integer) indice.get(CLAVE_RECEP)).toString(), numCampoError);
        	agregaErrorComun(((Integer) indice.get(CUENTA_RECEP)).toString(), numCampoError);
        	noError = false;
        }
        else {
            if (!validatorDivIntService.esCuentaValida(agenteReceptor)) {
                agregaErrorComun("La cuenta receptor no es valida", msgError);
                agregaErrorComun(((Integer) indice.get(CUENTA_RECEP)).toString(), numCampoError);
                noError = false;
            }       
        } 
        /** se valida que no sea el mismo participante y la misma cuenta el traspaso */
        if(esMismaCuenta(agenteTrasp,agenteReceptor)){
        	agregaErrorComun("No se pueden realizar traspasos a la misma cuenta",msgError);
        	agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
        	agregaErrorComun(((Integer) indice.get(CUENTA_RECEP)).toString(), numCampoError);
        	noError = false;
        }
        /** Valida que exista la emision*/
        boolean tieneIsin = false;
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
				emision = emisionDao.findEmisionLiberada(emisionVOaux);
				if (emision == null) {
					agregaErrorComun("No existe la emisión", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
					noError = false;
				}else{
					if(StringUtils.isNotBlank(isin) && StringUtils.isNotBlank(emision.getIsin()) &&
							!isin.trim().equalsIgnoreCase(emision.getIsin().trim())){
						agregaErrorComun("ISIN inválido", msgError);
				    	agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
				    	noError = false;
					}
				}
			}else{
				if(StringUtils.isNotBlank(isin)){
					emisionVOaux = new EmisionVO(isin);
					emision = emisionDao.findEmisionLiberada(emisionVOaux);
					if (emision == null) {
						agregaErrorComun("No existe la emisión", msgError);
						agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
						noError = false;
					}
					else{
						tieneIsin = true;
					}
				}else{
					agregaErrorComun("Emisión inválida", msgError);
					agregaErrorComun(((Integer) indice.get(ISIN)).toString(), numCampoError);
					noError = false;
				}
			}
			
			/** Valida el cupon vigente */
			if (noError && emision != null) {
				Cupon cuponAux = cuponDao.findCuponByIdEmision(emision.getIdEmision());

				if (cuponAux == null ||
						(StringUtils.isNotBlank(cupon) &&
						!StringUtils.equalsIgnoreCase(cupon, cuponAux.getClaveCupon()))
						) {
					agregaErrorComun("Error en la emisión, cupón inválido", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(EMISORA)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(SERIE)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(CUPON)).toString(), numCampoError);
					noError = false;
				} else {
					emisionVO.setCuponCortado("F");
				}
			}
			if (noError){
				/**Valida los traspasos permitidos */
				EmisionVO emisionVOauxiliar = null;
				if(tieneIsin){
					emisionVOauxiliar = new EmisionVO(isin);
				}
				else{
					emisionVOauxiliar = new EmisionVO(tv, emisora, serie, cupon);
				}
				emision = emisionDao.findEmision(emisionVOauxiliar);
				emisionVOauxiliar = ConvertBO2VO.crearEmisionVO(emision);
				if(esTraspasoValidoInversionExtranjera(agenteTrasp, agenteReceptor,emisionVOauxiliar)){
					String mensajeError = "Error: No esta permitido hacer un traspaso de cuenta: ["+
							agenteTrasp.getCuenta()+"] con clave de alta: ["+emisionVOauxiliar.getEmisionExtra()+
							"] hacia una cuenta: ["+agenteReceptor.getCuenta()+"]";
					agregaErrorComun(mensajeError, msgError);
					agregaErrorComun(((Integer) indice.get(CUENTA_VEND)).toString(), numCampoError);
					agregaErrorComun(((Integer) indice.get(CUENTA_RECEP)).toString(), numCampoError);
					noError = false;	
				}
				
				/** Valida el saldo disponible (la posicion) de la emision */
				Instrumento instrumento = (emision != null) ? emision.getInstrumento() : null;
				if (instrumento == null || instrumento.getMercado() == null) {
					agregaErrorComun("No existe el mercado", msgError);
					agregaErrorComun(((Integer) indice.get(TV)).toString(), numCampoError);
					noError = false;
				}
			}
		} 
		 
		/**Valida el tipo de operacion*/
		String tipoOperacion = campos[((Integer) indice.get(TIPO_OPER)).intValue()].trim();
		if (!tipoOperacion.equalsIgnoreCase(TIPO_OPER_T)	
				&& !tipoOperacion.equalsIgnoreCase(TIPO_OPER_TL)) {
			agregaErrorComun("Tipo de operación indefinido", msgError);
			agregaErrorComun(((Integer) indice.get(TIPO_OPER)).toString(), numCampoError);
			noError = false;
		}
		 /** Se valida la fecha de liquidacion que trae el archivo plano sea habil */
		 String fechaLiq = fechaArchivo2fechaFileTransfer(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);
	     Date fechaLiqD = null;
	     try {
	        fechaLiqD = simpleDateFormat.parse(fechaLiq);
	        if (dateUtilService.esInhabil(fechaLiqD)) {
	        	agregaErrorComun("La fecha de liquidación debe ser día hábil", msgError);
		    	agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);
		    	noError = false;
	        }
	     }
	     catch (ParseException e) {
	        agregaErrorComun("Error en la conversión de la fecha de liquidación", msgError);
		    agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);
		    e.printStackTrace();
		    noError = false;
	        }
	     
	     /**Valida que la fecha liquidacion no sea una fecha anterior a la actual*/
	     if(fechaActualHrCero.compareTo(fechaLiqD) > 0){	 
	    	 agregaErrorComun("La fecha de liquidación no debe ser anterior a la fecha actual", msgError);
	    	 agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);
	    	 noError = false;
	    }
	     
	     /**Valida que la fecha liquidacion no tenga mas de 8 dias habiles de plazo*/
	     Date fechaPlazoMaximo = dateUtilService.agregarDiasHabiles(fechaActualHrCero,VALOR_MAXIMO_DIAS);
	     if(fechaPlazoMaximo.compareTo(fechaLiqD) < 0){
	    	 agregaErrorComun("La fecha de liquidación no puede ser mayor a 8 días hábiles", msgError);
	    	 agregaErrorComun(((Integer) indice.get(FECHA_LIQ)).toString(), numCampoError);
	    	 noError = false;
	     }
		
	     /** Valida el valor de la cantidad */
		 if ((new BigDecimal(campos[((Integer) indice.get(CANTIDAD)).intValue()].trim())).compareTo(BIG_DECIMAL_ZERO) <= 0) {
			 agregaErrorComun("La cantidad del traspaso debe ser mayor que cero", msgError);
	    	 agregaErrorComun(((Integer) indice.get(CANTIDAD)).toString(), numCampoError);
	    	 noError = false;
		 } else if ((new BigDecimal(campos[((Integer) indice.get(CANTIDAD)).intValue()].trim())).compareTo(new BigDecimal("99999999999")) > 0) {
			 agregaErrorComun("La cantidad del traspaso no debe ser mayor a 99999999999", msgError);
	    	 agregaErrorComun(((Integer) indice.get(CANTIDAD)).toString(), numCampoError);
	    	 noError = false;
		 }
		  
    return noError;
}
    /**
     * Validacion de traspaso Valido para el FileTransferInvercionExtranjera
     */    
    
    public boolean esTraspasoValidoInversionExtranjera(AgenteVO traspasante, AgenteVO receptor,EmisionVO emisionVO){     
    	//Para cuentas con terminacion 99 o comienzo 54 
        if (receptor.esTerminacionCuenta98() || receptor.esTerminacionCuenta94()
        		 || (receptor.esTerminacionCuenta97() && emisionVO.esLIBR())) {
        	 if (emisionVO.esLIBR() || emisionVO.esADR() || (emisionVO.esVIVI() 
        			 && traspasante.esTerminacionCuenta99() && receptor.esTerminacionCuenta94())) {
                    return true;
             }
         }
         if (traspasante.esTerminacionCuenta98() || traspasante.esTerminacionCuenta94()
                 || (traspasante.esTerminacionCuenta97() && emisionVO.esLIBR())) {
            if (receptor.esTerminacionCuenta99() || receptor.esComienzoCuenta54()) {
            	if (emisionVO.esLIBR() || emisionVO.esADR() || (emisionVO.esVIVI() 
                       && traspasante.esTerminacionCuenta94() && receptor.esTerminacionCuenta99())) {
                    return true;
                }
             }
         }
            
             //Para cuentas con terminacion 98 o comienzo 54 
         if (receptor.esTerminacionCuenta99() || receptor.esTerminacionCuenta94()
               || (receptor.esTerminacionCuenta97() && emisionVO.esCPOS())) {
             if (emisionVO.esCPOS() || emisionVO.esADCP() || emisionVO.esBADC() || emisionVO.esIADC() 
                    || emisionVO.esGADC() || (emisionVO.esVIVI() && receptor.esTerminacionCuenta99())) {
                    return true;
             }
         }
         if (traspasante.esTerminacionCuenta99() || traspasante.esTerminacionCuenta94()
               || (traspasante.esTerminacionCuenta97() && emisionVO.esCPOS())) {
        	 if (receptor.esTerminacionCuenta98() || receptor.esComienzoCuenta54()) {
        		 if (emisionVO.esCPOS() || emisionVO.esADCP() || emisionVO.esBADC() || emisionVO.esIADC() 
                        || emisionVO.esGADC() || (emisionVO.esVIVI() 
                                && traspasante.esTerminacionCuenta99())) {
                        return true;
                 }
             }
         }
         return false;
    }
    
    /**
     * metodo para validar si es el mismo participante y la misma cuenta en el Traspaso
     * @param AgenteVO traspasante y AgenteVO receptor
     * @return false si son diferentes ya sea el participante o la cuenta, true si son iguales tanto el participante como la cuenta
     */ 
    private boolean esMismaCuenta(AgenteVO traspasante, AgenteVO receptor){
    	
    	log.info("Entrando a ValidatorDivIntServiceImpl.esMismaCuenta");
    	
    	return ( (traspasante != null && traspasante.tieneClave() && StringUtils.isNotBlank(traspasante.getCuenta())) && 
    			(receptor != null && receptor.tieneClave() && StringUtils.isNotBlank(receptor.getCuenta()) ) &&
    			(traspasante.getClave().equals(receptor.getClave()) && traspasante.getCuenta().equals(receptor.getCuenta()))); 
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
        fileTransfer.setIdInstRecep(campos[((Integer) indice.get(CLAVE_RECEP)).intValue()].toString().substring(0,2));
        fileTransfer.setFolioInstRecep(campos[((Integer) indice.get(CLAVE_RECEP)).intValue()].toString().substring(2));
        fileTransfer.setCuentaRecep(campos[((Integer) indice.get(CUENTA_RECEP))].toString());
        fileTransfer.setTv(emisionVO.getTv());
        fileTransfer.setEmisora(emisionVO.getEmisora());
        fileTransfer.setSerie(emisionVO.getSerie());
        fileTransfer.setCupon(emisionVO.getCupon());
        fileTransfer.setBajaLogica(emisionVO.getCuponCortado());
        fileTransfer.setMercado(emisionVO.getMercado());
        fileTransfer.setIsin(emisionVO.getIsin());
        fileTransfer.setCantidadTitulos(campos[((Integer) indice.get(CANTIDAD)).intValue()]);
        fileTransfer.setTipoOperacion(campos[((Integer) indice.get(TIPO_OPER)).intValue()]);        
        if (isCompra){
        	fileTransfer.setTipoMensaje(TMSJ_540);
            fileTransfer.setEstatusOper("N");
        }
        else{
        	fileTransfer.setTipoMensaje(TMSJ_542);
            fileTransfer.setEstatusOper("N");
        } 
        fileTransfer.setTipoMovto(TIPO_MOVTO_E);
        fileTransfer.setFechaNotificacion(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);
        fileTransfer.setFechaLiquidacion(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);
        fileTransfer.setFechaOperacion(campos[((Integer) indice.get(FECHA_LIQ)).intValue()]);
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
            Map<Object, Object> indice = indicesNombresTraspasosDivIntInversionExtranjera;
            if (validaFormato(indicesNombresTraspasosDivIntInversionExtranjera, campos, msgError, numCampoError, tam, agenteVO, emisionVO)) {
                if (!validaNegocio(agenteFirmado, indicesNombresTraspasosDivIntInversionExtranjera, campos, msgError, numCampoError, tam, agenteVO, emisionVO, cuentaRecep)) {
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
  
	
	public Integer almacenaInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
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
        }
    }
    
    /**
     * @param fileTransfer
     * @param tipoProceso
     * @param camposVO
     * @return lista de objetos CampoArchivoVO
     * @throws BusinessException
     * @throws ParseException
     */
    private List<Object> generaDatosDivInt(FileTransfer fileTransfer, String tipoProceso,CamposDivIntVO camposVO) throws BusinessException, ParseException {
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
		indiceCampo = indicesNombresTraspasosDivIntInversionExtranjera;

		/* Genera el objeto de salida del servicio */
		List<Object> listaCamposArchivo = new ArrayList<Object>();

		boolean error = false;
		
		/** Verificacion y setteo de la fecha de liquidacion */
		Date fecha = null;
		String fechaFormateada = null;
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_LIQ)));
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaLiquidacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);	
			}
		}
		int tamFechaLiquidacion = fileTransfer.getFechaLiquidacion() != null ? 
				fileTransfer.getFechaLiquidacion().length() : 0; 
				CampoArchivoVO campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaLiquidacion, 
				FECHA_LIQ, error, fecha != null ? fecha : fileTransfer.getFechaLiquidacion(), 
						"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaLiquidacion(fecha);

		/** Verificacion y setteo de los datos del traspasante */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CLAVE_TRASP)));
		 campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstTrasp().length() + (fileTransfer.getFolioInstTrasp().length()), 
				CLAVE_TRASP, error, fileTransfer.getIdInstTrasp() + fileTransfer.getFolioInstTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstTrasp(fileTransfer.getIdInstTrasp());
		camposVO.setFolioInstTrasp(fileTransfer.getFolioInstTrasp());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaTrasp().length(), 
				CUENTA_VEND, error, fileTransfer.getCuentaTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaTrasp(fileTransfer.getCuentaTrasp());

		/** Verificacion y setteo de los datos del receptor */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CLAVE_RECEP)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstRecep().length() + (fileTransfer.getFolioInstRecep().length()), 
				CLAVE_RECEP, error,fileTransfer.getIdInstRecep() + fileTransfer.getFolioInstRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstRecep(fileTransfer.getIdInstRecep());
		camposVO.setFolioInstRecep(fileTransfer.getFolioInstRecep());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_RECEP)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaRecep().length(), 
				CUENTA_RECEP, error, fileTransfer.getCuentaRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaRecep(fileTransfer.getCuentaRecep());
		
		/** Verificacion y setteo de los atributos de la clave-valor */
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
		
		/**Verificacion y setteo del ISIN*/
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ISIN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIsin(),
				ISIN, error, fileTransfer.getIsin(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIsin(fileTransfer.getIsin());
		
		/** Verificacion y setteo del tipo de operacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_OPER)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoOperacion().length(), 
				TIPO_OPER, error, fileTransfer.getTipoOperacion(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoOperacion(fileTransfer.getTipoOperacion());
		
		/**  Verificacion y asignacion de la cantidad */
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
		camposVO.setTipoMensaje(fileTransfer.getTipoMensaje());
		camposVO.setEstatus(fileTransfer.getEstatusOper());
		camposVO.setBajaLogica(fileTransfer.getBajaLogica());
		camposVO.setMercado(fileTransfer.getMercado());
		camposVO.setTipoFileTransfer(TIPO_FILE_TRANSFER);
		camposVO.setTipoMovimiento(fileTransfer.getTipoMovto());
		Date fechaActual = dateUtilService.getCurrentDate();
        Date fechaActualHrCero = dateUtilService.getFechaHoraCero(fechaActual);
		camposVO.setFechaNotificacion(fechaActualHrCero);
		

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
        totalesProcesoVO.setTipoFileTransfer(TIPO_FILE_TRANSFER);
        
        return totalesProcesoVO;
	}
    
    /**
     * @param agenteFirmado
     * @param tipoProceso
     * @return
     * @throws BusinessException
     */
    private ResumenVO obtieneResumenDivInt(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException {
    	
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

    	return resumenVO;
    }
    
    
	public void cancelaProceso(FileTransferVO fileTransferVO)
			throws BusinessException {
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

	public void grabaInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
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
		
		try {
			this.grabaInformacionTraspasosDI(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso(), fileTransferVO.getConsecProtocolo());
		}
		catch (ParseException pe) {
		
			throw new BusinessException(pe.toString(),pe);
			
		} catch( Exception ex ) {
			throw new BusinessException("Error al grabar el registro",ex);
		}
		
	}

	public TotalesProcesoVO muestraInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
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

	public ResumenVO obtieneResumen(FileTransferVO fileTransferVO)
			throws BusinessException {
		// TODO Auto-generated method stub
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
		return this.obtieneResumenDivInt(fileTransferVO.getAgenteFirmado(), fileTransferVO.getTipoProceso());
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
	 * @param utilService
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	public Boolean getIsCompra() {
		return isCompra;
	}

	public void setIsCompra(Boolean isCompra) {
		this.isCompra = isCompra;
	}
	
}
