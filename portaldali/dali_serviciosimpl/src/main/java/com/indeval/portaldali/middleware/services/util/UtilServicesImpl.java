/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.mercadodinero.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.dao.util.CuponDao;
import com.indeval.portaldali.persistence.dao.util.SecuenciaDaliDao;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Implementacio&acute;n para el UtilService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilServicesImpl implements UtilServices, Constantes {

    /** Define ACTIVO */
    public static final String ACTIVO = "X";

    /** Define PROCOVAL */
    public static final String PROCOVAL = "PROCOVAL";

    /** Define el modo de acceso al archivo - Lectura */
    public static final String MODO_LECTURA = "r";

    /** Define el modo de acceso al archivo - Lectura-Escritura */
    public static final String MODO_ESCRITURA = "rw";

    /** Log de clase. */
    private Logger logger = LoggerFactory.getLogger(UtilServicesImpl.class);

    /** Bean de acceso a datos de DiaInhabil */
    private DiaInhabilDaliDao diaInhabilDaliDao;

    /** Bean de acceso a cupo dao*/
    private CuponDao cuponDao;

	/** DAO utilizado para consultar bóvedas de valor y de efectivo */
	private BovedaDaliDAO bovedaDAO;

	/** DAO utilizado para consultar divisas */
	private DivisaDaliDAO divisaDAO;

//    /** Bean de acceso a AplicacionDao */
//    private AplicacionDao aplicacionDao;
//
//    /** Bean de acceso a ObtenerFaseService */
//    private ObtenerFaseService obtenerFaseService;

    /** Mapeo de los tipos de operación con el tipo de instruccion. */
    private HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion;

    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;

    /** Bean para cuentaNombradaDaliDao */
    private CuentaNombradaDaliDao cuentaNombradaDaliDao;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

//    /** Bean de acceso a datos de Parametro - Valpre-e */
//    private ParametroDao parametroDao;

    /** Bean para el acceso a la secuencia_bitacora */
    private SecuenciaDaliDao secuenciaDaliDao;

	/**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#isAgenteRegistrado(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
     */
    public PaginaVO isAgenteRegistrado(AgenteVO agenteVO) throws BusinessException {
        logger.trace("Entrando a UtilServicesImpl.isAgenteRegistrado()");
        PageVO pageVO = 
        	cuentaNombradaDaliDao.getCuentaNombrada(UtilsDaliVO.parseAgenteVO2AgentePK(agenteVO), new PageVO());
        if(pageVO == null || pageVO.getRegistros() == null || pageVO.getRegistros().isEmpty()) {
            throw new BusinessException(errorResolver.getMessage("JBR-014", 
            	new Object[]{agenteVO.getId() + agenteVO.getFolio()}));
        }
        return UtilsDaliVO.getPaginaVO(pageVO);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaAgente(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
     */
    public void validaAgente(AgenteVO agenteVO) throws BusinessException {

        logger.info("Entrando a UtilServicesImpl.validaAgente()");
		this.validaAgente(agenteVO, UtilsDaliVO.BLANK);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaAgente(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
     */
    public PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente) throws BusinessException {

		logger.trace("Entrando a UtilServicesImpl.validaAgente()");

		return this.validaAgente(agenteVO, tipoAgente, true);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaAgente(com.indeval.portaldali.middleware.services.modelo.AgenteVO, boolean)
     */
    public PaginaVO validaAgente(AgenteVO agenteVO, boolean conCuenta) throws BusinessException {

		logger.trace("Entrando a UtilServicesImpl.validaAgente()");

        return this.validaAgente(agenteVO, UtilsDaliVO.BLANK, conCuenta);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaAgente(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String, boolean)
     */
    public PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente, boolean conCuenta) throws BusinessException {

		logger.trace("Entrando a UtilServicesImpl.validaAgente()");

		this.validaDTONoNulo(agenteVO, " agente " + tipoAgente);
		agenteVO.tieneClaveValida();
		if(conCuenta){
			if (StringUtils.isBlank(agenteVO.getCuenta())) {
				throw new BusinessException(errorResolver.getMessage("J0076"));
			}
		}
		else {
			agenteVO.setCuenta(null);
		}

		// Se valida la existencia del agente
		return this.isAgenteRegistrado(agenteVO);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#getLlaveFolio()
     */
    public String getLlaveFolio() {

        logger.trace("Entrando a UtilServicesImpl.getLlaveFolio()");

        StringBuffer llave = new StringBuffer("");
        GregorianCalendar cFechaHoy = new GregorianCalendar();
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.YEAR) - 2000));
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.MONTH) + 1));
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.DAY_OF_MONTH)));
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.HOUR_OF_DAY)));
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.MINUTE)));
        llave.append(this.intToFormatedString(cFechaHoy.get(Calendar.SECOND)));
        return llave.toString();
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#getFolio(java.lang.String)
     */
    public BigInteger getFolio(String nombreSecuencia) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.getFolio()");

        BigInteger idFolio = UtilsDaliVO.BIG_INTEGER_ZERO;

        try {
            idFolio = secuenciaDaliDao.getConsecutivo(nombreSecuencia);
            logger.info( String.format("secuencia: [%s] folio: [%d]", nombreSecuencia, idFolio.longValue()) );
        }
        catch (SQLException e) {
            logger.error(" ERROR AL GENERAR EL FOLIO " +  e.getMessage());
            throw new BusinessException(e.getMessage());
        }catch(Exception e) {
        	logger.error(e.getMessage(), e);
            throw new BusinessException(" ERROR AL GENERAR EL FOLIO " + e.getMessage());
        }
        
        return idFolio;

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#agregarDiasHabiles(java.util.Date, int)
     */
    public Date agregarDiasHabiles(Date fechaInicial, int offset) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.agregarDiasHabiles()");

        this.validaDTONoNulo(fechaInicial, " Fecha Inicial ");

        return diaInhabilDaliDao.agregaDiasHabiles(dateUtilsDao.getFechaHoraCero(fechaInicial), offset);

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#modificaFecha(java.util.Date, int, int)
     */
    public Date modificaFecha(Date fecha, int campoXModificar, int cantidadAModificar) {

        logger.trace("Entrando a UtilServicesImpl.modificaFecha()");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(campoXModificar, cantidadAModificar);
        return calendar.getTime();
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#isCplexActive()
     */
    public boolean isCplexActive() {

        logger.trace("Entrando a UtilServicesImpl.isCplexActive()");

//        String status = this.getAplicacionDao().getStatusDeAplicacion(PROCOVAL);
//        log.debug("status: [" + status + "]");
//
//        return StringUtils.isNotBlank(status) ? ACTIVO.equalsIgnoreCase(status) : false;
        return false;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#leeArchivo(java.io.File)
     */
    public String leeArchivo(File archivo) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.leeArchivo()");

        /* Genera el objeto para la lectura */
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(archivo, MODO_LECTURA);
        }
        catch (FileNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

        StringBuffer lectura = new StringBuffer();
        String linea = "";
        try {
            while ((linea = randomAccessFile.readLine()) != null) {
                lectura.append(linea);
                lectura.append("\n");
            }
        }
        catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }

        return lectura.toString();
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#grabaArchivo(java.io.File, java.lang.String)
     */
    public Boolean grabaArchivo(File archivo, String informacion) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.grabaArchivo()");

        /* Se graba el registro con error */
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(archivo, MODO_ESCRITURA);
        }
        catch (FileNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

        try {
            randomAccessFile.writeBytes(informacion);
        }
        catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }

        return Boolean.TRUE;

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaFase(java.util.Map)
     */
    public int validaFase(Map fasesAbiertas) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.validaFase()");

        this.validaDTONoNulo(fasesAbiertas, "El servicio no pudo determinar la fase");
        UtilsLog.logClaveValor(fasesAbiertas);

        //int fase = obtenerFaseService.obtenerFase();
        //log.debug("La fase activa es: [" + fase + "]");

//        if (!fasesAbiertas.containsKey(Integer.toString(fase))) {
//            throw new BusinessException(
//                    errorResolver.getMessage("J1000", new Object[] {"", "" + fase }));
//        }

        //return fase;
        return 0;

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validarClaveTipo(java.lang.String, java.lang.String)
     */
    public String validarClaveTipo(String clave, String tipo) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.validarClaveTipo()");

        if (StringUtils.isBlank(clave)) {
            logger.debug("El campo " + tipo + "es NULL o Vacio!!!");
            throw new BusinessException(errorResolver.getMessage("J0012", new Object[] {tipo }));
        }
        return clave.trim();
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#getLaborableDay(java.util.Date, int)
     */
    public Date getLaborableDay(Date fecha, int plazo) {

        logger.trace("Entrando a UtilServicesImpl.getLaborableDay()");

        if (diaInhabilDaliDao.esInhabil(fecha)) {
            if(plazo==0){
                plazo=1;
            }
            return getLaborableDay(modificaFecha(fecha, Calendar.DATE, plazo), plazo);
        }
        return fecha;

    }

    /**
     * Recibe un entero y regresa un string con un cero a la izquierda en caso
     * de ser menor a 10
     */
    private String intToFormatedString(int entero) {

        logger.trace("Entrando a UtilServicesImpl.intToFormatedString()");

        StringBuffer formatedString = new StringBuffer("");
        if (entero < 10) {
            formatedString.append("0");
        }
        formatedString.append(entero);
        return formatedString.toString();
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaDTONoNulo(java.lang.Object, java.lang.String)
     */
    public void validaDTONoNulo(Object parametro, String tipo) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.validaDTONoNulo()");

        if (parametro == null) {
            StringBuffer strTipo = new StringBuffer("El objeto ");
            if (StringUtils.isBlank(tipo)) {
                strTipo.append(" de parametro ");
            }
            else {
                strTipo.append(tipo);
            }
            throw new BusinessException(
                    errorResolver.getMessage("J0019", (Object) strTipo.toString()));
        }
    }

    /**
     * Recupera la hora actual y valida contra la hora tope de la operacion indicada en el
     * parametro.
     *
     * @param operacion
     *
     * @throws BusinessException
     * @throws BusinessDataException
     */
    public void validarHoraOperacion(String operacion)
                               throws BusinessException {

        logger.trace("Entrando a MercadoDineroServiceImpl.validarHoraOperacion()");

        if (StringUtils.isBlank(operacion)) {

            throw new BusinessException(errorResolver.getMessage("J0014"));

        }

        Calendar auxiliar = Calendar.getInstance();

        // Se recupera en un Calendar la hora actual
        Date horaActual = dateUtilsDao.getDateFechaDB();
        Calendar calHoraActual = Calendar.getInstance();
        auxiliar.setTime(horaActual);
        calHoraActual.set(Calendar.HOUR_OF_DAY, auxiliar.get(Calendar.HOUR_OF_DAY));
        calHoraActual.set(Calendar.MINUTE, auxiliar.get(Calendar.MINUTE));
        calHoraActual.set(Calendar.SECOND, 0);
        calHoraActual.set(Calendar.MILLISECOND, 0);
        logger.debug(calHoraActual.toString());

        // Se recupera en un Calendar la hora tope configurada para la operacion
        //Parametro parametro = this.parametroDao.getByPK(operacion);
        //String horaTope = parametro.getValor();
        String horaTope = "";
        StringTokenizer stk = new StringTokenizer(horaTope, ":");

        Calendar calHoraTope = Calendar.getInstance();
        auxiliar.setTime(horaActual);
        calHoraTope.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stk.nextToken().trim()));
        calHoraTope.set(Calendar.MINUTE, Integer.parseInt(stk.nextToken().trim()));
        calHoraTope.set(Calendar.SECOND, 0);
        calHoraTope.set(Calendar.MILLISECOND, 0);
        logger.debug(calHoraTope.toString());

        // Se compara la hora actual con la hora tope
        if (calHoraActual.after(calHoraTope)) {

            throw new BusinessDataException(
                errorResolver.getMessage("J0016"), "J0016");

        }

    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#validaNumber(java.lang.Object, java.lang.String)
     */
    public void validaNumber(Object numero, String patternLimit) throws BusinessException {

        logger.trace("Entrando a UtilServicesImpl.validaNumber()");

        int valid = 0;
        BigDecimal limit = new BigDecimal(patternLimit);
        Object maximLimit = new Object();

        /* Se construyen los objetos numericos correspondientes
         * y se compara el numero con el patron limite */
        logger.debug("numero.class["+numero.getClass()+"]");
        if (numero instanceof Integer) {

            Integer numeroTipado = UtilsDaliVO.INTEGER_ZERO;
            Integer limitInteger = UtilsDaliVO.INTEGER_ZERO;

            try {
                numeroTipado = (Integer) numero;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                throw new BusinessException(errorResolver.getMessage("J0064"));
            }
            try {
                limitInteger = Integer.valueOf(limit.toBigInteger().toString());
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                throw new BusinessException(errorResolver.getMessage("J0065"));
            }
            valid = numeroTipado.compareTo(limitInteger - 1);
            maximLimit = limitInteger - 1;
        }
        else if (numero instanceof BigInteger) {
            BigInteger numeroTipado = (BigInteger) numero;
            logger.debug("Valor Limite = [" + limit + "]");
            BigInteger limitBigInteger = limit.add(UtilsDaliVO.BIG_DECIMAL_UNO.negate()).toBigInteger();
            logger.debug("Valor Limite menos 1 = [" + limitBigInteger + "]");
            valid = numeroTipado.compareTo(limitBigInteger);
            maximLimit = limitBigInteger;
        }
        else if (numero instanceof Double) {
            Double numeroTipado = (Double) numero;
            Double limitDouble = new Double(limit.doubleValue());
            valid = numeroTipado.compareTo(limitDouble);
            maximLimit = limitDouble;
        }
        else if (numero instanceof BigDecimal) {
            BigDecimal numeroTipado = (BigDecimal) numero;
            logger.debug("numeroTipado["+numeroTipado+"]");
            logger.debug("limit["+limit+"]");
            valid = numeroTipado.compareTo(limit);
            logger.debug("valid["+valid+"]");
            maximLimit = limit;
        }
        /* Si el objeto recibido no es un numero se arroja la excepcion */
        else {
            throw new BusinessException(errorResolver.getMessage("J0064"));
        }

        logger.debug("Valor Maximo = [" + maximLimit + "]");

        /* Si el numero recibido excede el limite se arroja la excepcion */
        if(valid > 0){
            throw new BusinessException(
                    errorResolver.getMessage("J0063", new Object[] {maximLimit.toString()}));
        }

    }

    /**
     * Valida que la pagina no sea nula y que la misma tenga registros
     * @param paginaVO
     * @param mensaje Mensaje personalizado de la excepci&oacute;n. Si es null o vacio,
     *                se utiliza el mensaje predeterminado: "No se han encontrado registros
     *                coincidentes para los criterios de b\u00fasqueda seleccionados."
     * @throws BusinessException Excepcio&oacute;n arrojada si no hay registros
     */
    public void validaPagina(PaginaVO paginaVO, String mensaje) throws BusinessException {

        logger.trace("Entrando a CatalogoServiceImpl.validaPage()");

        if(paginaVO == null || paginaVO.getRegistros() == null || paginaVO.getRegistros().isEmpty()){

            if(StringUtils.isBlank(mensaje)) {
                throw new BusinessException(errorResolver.getMessage("J9999"));
            }
            else {
                throw new BusinessException(errorResolver.getMessage(mensaje));
            }

        }

    }



    public long dateDiff(Date fechaInicio, Date fechaFin) {

    	Calendar calendarIni=Calendar.getInstance();
    	calendarIni.setTime(fechaInicio);

    	Calendar calendarFin=Calendar.getInstance();
    	calendarFin.setTime(fechaFin);

    	return dateDiff(calendarIni.get(Calendar.YEAR),calendarIni.get(Calendar.MONTH),calendarIni.get(Calendar.DAY_OF_MONTH)
    			,calendarFin.get(Calendar.YEAR),calendarFin.get(Calendar.MONTH),calendarFin.get(Calendar.DAY_OF_MONTH) );


	}


    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#dateDiff(int, int, int, int, int, int)
     */
    public long dateDiff(int Y1,int M1,int D1,int Y2,int M2,int D2){

        logger.info("Entrando a UtilServicesImpl.dateDiff()");

        GregorianCalendar date1=new GregorianCalendar(Y1,M1,D1);
        GregorianCalendar date2=new GregorianCalendar(Y2,M2,D2);
        long difms=date2.getTimeInMillis() - date1.getTimeInMillis();

        long difd= difms < 0 ?0:(difms +(com.indeval.portaldali.middleware.services.util.util.Constantes.MILLISECONDXDAY / 4)) / Constantes.MILLISECONDXDAY;

        return difd;
    }





    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#parseArray(java.lang.String[])
     */
    public String[] parseArray(String array[]) {

        logger.info("Entrando a UtilServicesImpl.parseArray()");

        List<String> preResult = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && StringUtils.isNotBlank(array[i])) {
                preResult.add(array[i].trim());
            }
        }
        if (preResult.size() == 0) {
            return null;
        }
        String[] result = new String[preResult.size()];
        for (int j = 0; j < preResult.size(); j++) {
            result[j] = preResult.get(j).toString();
        }
        return result;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#clobToString(java.sql.Clob)
     */
    public String clobToString(Clob clob) throws SQLException {

        logger.info("Entrando a UtilServicesImpl.clobToString()");

        StringBuffer buffer = null;
        if (clob != null) {
            char[] mensaje = new char[(int) clob.length()];
            buffer = new StringBuffer("");
            try {
                clob.getCharacterStream().read(mensaje);
                buffer.append(mensaje);
                if(buffer != null ){
              	  return buffer.toString();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
		return null;

	}

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#clobTOMap(java.sql.Clob)
     */
    public Map clobTOMap(Clob clob) throws SQLException {

        logger.info("Entrando a UtilServicesImpl.clobTOMap()");

        XStream stream = new XStream(new DomDriver());
		stream.alias("mapa", Map.class);
		String x = this.clobToString(clob);

		Map resultado = (HashMap) stream.fromXML(x);
		return resultado;

	}

    /**
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#mapaToXml(java.util.Map)
     */
    public String mapaToXml(Map mapa) {

        logger.info("Entrando a UtilServicesImpl.mapaToXml()");

		XStream stream = new XStream();
		stream.alias("mapa", HashMap.class);
		return stream.toXML(mapa);

	}


    /**
     * Inyeccion del bean errorResolver
     *
     * @param errorResolver
     *            Referencia del bean.
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

	/**
	 * @param diaInhabilDaliDao the diaInhabilDaliDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}

    /**
     * @param cuentaNombradaDaliDao the cuentaNombradaDaliDao to set
     */
    public void setCuentaNombradaDao(CuentaNombradaDaliDao cuentaNombradaDaliDao) {
        this.cuentaNombradaDaliDao = cuentaNombradaDaliDao;
    }

    /**
     * @param dateUtilsDao the dateUtilsDao to set
     */
    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
        this.dateUtilsDao = dateUtilsDao;
    }

//    /**
//     * @param parametroDao the parametroDao to set
//     */
//    public void setParametroDao(ParametroDao parametroDao) {
//        this.parametroDao = parametroDao;
//    }

    /**
     * @param secuenciaDaliDao the secuenciaDaliDao to set
     */
    public void setSecuenciaDaliDao(SecuenciaDaliDao secuenciaDaliDao) {
        this.secuenciaDaliDao = secuenciaDaliDao;
    }

    public CuponDao getCuponDao() {
		return cuponDao;
	}

	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}

	/**
     * @see com.indeval.portaldali.middleware.services.util.FechasUtilService#getCurrentDate()
     */
    public Date getCurrentDate() {
        return dateUtilsDao.getDateFechaDB();
    }

    public boolean isCuponValidoParaEmision(EmisionVO emisionVO) {
    	boolean cuponValido = Boolean.FALSE;

    	Cupon cupon = cuponDao.consultaCuponValidoParaEmision(emisionVO);

    	if(cupon != null) {
    		cuponValido = Boolean.TRUE;
    	}

    	return cuponValido;

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#obtenerTipoInstruccionPorClaveOperacion(java.lang.String)
     */
    public TipoInstruccionDTO obtenerTipoInstruccionPorClaveOperacion(String clave){
    	TipoInstruccionDTO tipoInstruccionDTO = null;

    	for (String llave : mapeoOperacionInstruccion.keySet()) {
			if (mapeoOperacionInstruccion.get(llave).getNombreCorto().equals(clave)) {
				tipoInstruccionDTO = mapeoOperacionInstruccion.get(llave);
				break;
			}
    	}
    	return tipoInstruccionDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#isTipoInstruccionDivisaBovedaValido(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean isTipoInstruccionDivisaBovedaValido(String tipoInstruccion, String divisa, String boveda){
    	boolean isValido = false;
    	TipoInstruccionDTO tipoInstruccionDTO = this.obtenerTipoInstruccionPorClaveOperacion(tipoInstruccion);
    	List<DivisaDTO>divisas = divisaDAO.buscarDivisasPorTipoInstruccion(null, BigInteger.valueOf(tipoInstruccionDTO.getIdTipoInstruccion()));
    	DivisaDTO divisaDTO = divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa);
    	//Valida que la relacion TipoInstruccion - Divisa se cumpla
    	if (divisaDTO != null && divisas.contains(divisaDTO)){
    		BovedaDTO criterio = new BovedaDTO();
    		criterio.setClaveTipoBoveda(TipoCustodiaConstants.EFECTIVO);
    		List<BigInteger> idsBovDiv = bovedaDAO.obtenerBovedasPorDivisa(divisaDTO);
    		List<BigInteger> idsBovIns = bovedaDAO.obtenerBovedasPorTipoInstruccion(tipoInstruccionDTO);
    		List<Long> idsBovedas = new ArrayList<Long>();
    		for (BigInteger idBov : idsBovDiv) {
    			// Valida que las relaciones Divisa - Boveda y TipoInstruccion - Boveda se cumplan
    			if(idsBovIns.contains(idBov)){
    				idsBovedas.add(idBov.longValue());
    			}
    		}

    		//Obtiene las Bovedas
    		for (BovedaDTO bovedaDTO : bovedaDAO.buscarBovedasPorTipoCustodia(criterio, idsBovedas, null)){
    			if(bovedaDTO.getNombreCorto().equalsIgnoreCase(boveda)){
    				isValido = true;
    				break;
    			}
    		}

    	}
    	return isValido;
    }

    
    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.middleware.services.util.UtilServices#isTipoInstruccionDivisaValido(java.lang.String, java.lang.String)
     */
    public boolean isTipoInstruccionDivisaValido(String tipoInstruccion, String divisa){
    	boolean isValido = false;
    	TipoInstruccionDTO tipoInstruccionDTO = this.obtenerTipoInstruccionPorClaveOperacion(tipoInstruccion);
    	List<DivisaDTO>divisas = divisaDAO.buscarDivisasPorTipoInstruccion(null, BigInteger.valueOf(tipoInstruccionDTO.getIdTipoInstruccion()));
    	DivisaDTO divisaDTO = divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa);
    	if (divisaDTO != null && divisas.contains(divisaDTO)){
    		isValido = true;
    	}
    		return isValido;
    }


	/**
	 * Obtiene el valor del atributo mapeoOperacionInstruccion.
	 *
	 * @return el valor del atributo mapeoOperacionInstruccion.
	 */
	public HashMap<String, TipoInstruccionDTO> getMapeoOperacionInstruccion() {
		return mapeoOperacionInstruccion;
	}

	/**
	 * Establece el valor del atributo mapeoOperacionInstruccion.
	 *
	 * @param mapeoOperacionInstruccion el valor del atributo mapeoOperacionInstruccion a establecer.
	 */
	public void setMapeoOperacionInstruccion(
			HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion) {
		this.mapeoOperacionInstruccion = mapeoOperacionInstruccion;
	}

	/**
	 * Obtiene el valor del atributo bovedaDAO.
	 *
	 * @return el valor del atributo bovedaDAO.
	 */
	public BovedaDaliDAO getBovedaDAO() {
		return bovedaDAO;
	}

	/**
	 * Establece el valor del atributo bovedaDAO.
	 *
	 * @param bovedaDAO el valor del atributo bovedaDAO a establecer.
	 */
	public void setBovedaDAO(BovedaDaliDAO bovedaDAO) {
		this.bovedaDAO = bovedaDAO;
	}

	/**
	 * Obtiene el valor del atributo divisaDAO.
	 *
	 * @return el valor del atributo divisaDAO.
	 */
	public DivisaDaliDAO getDivisaDAO() {
		return divisaDAO;
	}

	/**
	 * Establece el valor del atributo divisaDAO.
	 *
	 * @param divisaDAO el valor del atributo divisaDAO a establecer.
	 */
	public void setDivisaDAO(DivisaDaliDAO divisaDAO) {
		this.divisaDAO = divisaDAO;
	}
    /**metodo utilizado en Encolador DepositoEfectivo para buscar datos existentesen bada
     * */
	public BovedaDTO buscarBovedaPorTipoCustodia(String boveda) {
		BovedaDTO bovedaDato=new BovedaDTO();
		bovedaDato.setNombreCorto(boveda);
		return bovedaDAO.buscarBovedaPorTipoCustodia(bovedaDato);
	}

	public DivisaDTO obtenerDivisaPorClaveAlfavetica(String divisa) {
		return divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa);
	}

	public BovedaDTO obtenerNombreCortoBoveda(long idBoveda) {
		// TODO Auto-generated method stub
		return bovedaDAO.consultarBovedaPorId(idBoveda);
	}

}
