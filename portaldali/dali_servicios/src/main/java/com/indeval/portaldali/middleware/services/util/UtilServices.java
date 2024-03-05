/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import java.io.File;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Interface para el UtilService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface UtilServices {

	/**
	 * @return String construido a partir de la fecha/hora
	 */
	String getLlaveFolio();

	/**
	 * Recupera el folio para una operacion.
	 *
	 * @param nombreSecuencia
	 * @return BigInteger
	 * @throws BusinessException
	 */
	BigInteger getFolio(String nombreSecuencia) throws BusinessException;

	/**
	 * Verifica si el Sistema CPLEX esta ejecutandose.
	 *
	 * La validacion se efectua con la siguiente instruccion SQL:
	 *
	 * <pre>
	 * select status from catalogo..aplicacion
	 * where aplicacion='PROCOVAL'
	 * </pre>
	 *
	 * Si el estatus es 'X' significa que est corriendo el CPLEX.
	 *
	 *
	 * @return true si CPLEX esta ejecutandose, false de lo contrario.
	 */
	boolean isCplexActive();

	/**
	 * Lee un archivo de texto y regresa la informacion en un String
	 *
	 * @param archivo
	 * @return String
	 * @throws BusinessException
	 */
	String leeArchivo(File archivo) throws BusinessException;

	/**
	 * Verifica que la fase sea valida para el servicio de acuerdo al mapa de
	 * fases que recibe. En caso de que no arroja una BusinessException
	 *
	 * @param fasesAbiertas
	 * @return int - Fase activa
	 * @throws BusinessException
	 */
	int validaFase(Map fasesAbiertas) throws BusinessException;

	/**
	 * Graba informacion proporcionada en un String en el archivo de texto
	 * indicado
	 *
	 * @param archivo
	 * @param informacion
	 * @return Boolean true si grabo existosamente, false en caso contrario
	 * @throws BusinessException
	 */
	Boolean grabaArchivo(File archivo, String informacion) throws BusinessException;

	/**
	 * Valida que el parametro 'clave' que recibe no sea nulo y que no venga
	 * vacio. , en caso de serlo, lanzar&aacute; una BusinessException indicando
	 * el mensaje: "Error: {tipo}, es(son) campo(s) requerido(s), verifique.".
	 *
	 * @param clave
	 *            El objeto STRING a validar.
	 * @param tipo
	 *            El nombre del campo a validar
	 * @return String El trim() del valor del String, si todo es valido.
	 * @throws BusinessException
	 *
	 */
	String validarClaveTipo(String clave, String tipo) throws BusinessException;

	/**
	 * M&eacute;todo que regresa el d&iacute;a (previo/siguiente) laborable a la
	 * fecha que es recibida como argumento
	 *
	 * @param fecha
	 * @param plazo
	 *            si es negativo regresa los dias previos
	 * @return Date
	 * @throws Exception
	 */
	Date getLaborableDay(Date fecha, int plazo);

	/**
	 * Regresa una fecha igual a la fecha enviada como parametro mas el numero
	 * de dias h&aacute;biles
	 *
	 * @param fechaInicial -
	 *            Date
	 * @param offset -
	 *            int
	 * @return Date
	 * @throws BusinessException
	 */
	Date agregarDiasHabiles(Date fechaInicial, int offset) throws BusinessException;

	/**
	 * Modifica los dias indicados en diasXModificar.
	 *
	 * @param fecha
	 * @param campoXModificar
	 * @param cantidadAModificar
	 * @return Date
	 */
	Date modificaFecha(Date fecha, int campoXModificar, int cantidadAModificar);

	/**
	 * Este m&eacute;todo valida que el objeto recibido como par&aacute;metro no
	 * sea nulo, en caso de serlo, lanzar&aacute; una BusinessException
	 * indicando el mensaje: "El objeto {tipo}/de parametro , tiene(n) un valor
	 * NULL o vac&iacute;o".
	 *
	 * @param parametro
	 * @param tipo
	 * @throws BusinessException
	 */
	void validaDTONoNulo(Object parametro, String tipo) throws BusinessException;

	/**
	 * @param operacion
	 * @throws BusinessException
	 */
	void validarHoraOperacion(String operacion) throws BusinessException;

	/**
	 * Este m&eacute;todo valida que el objeto recibido como par&aacute;metro es
	 * un n&acuteumero y que no exceda el patternLimit
	 *
	 * @param numero
	 * @param patternLimit
	 * @throws BusinessException
	 */
	void validaNumber(Object numero, String patternLimit) throws BusinessException;

	/**
	 * Valida que la pagina no sea nula y que la misma tenga registros
	 *
	 * @param paginaVO
	 * @param mensaje
	 *            Mensaje personalizado de la excepci&oacute;n. Si es null o
	 *            vacio, se utiliza el mensaje predeterminado: "No se han
	 *            encontrado registros coincidentes para los criterios de
	 *            b\u00fasqueda seleccionados."
	 * @throws BusinessException
	 *             Excepcio&oacute;n arrojada si no hay registros
	 */
	public void validaPagina(PaginaVO paginaVO, String mensaje) throws BusinessException;
	
	/**
	 * busca una bovedaDTO por nombre_Corto 
	 * @param nombreCorto de la boveda
	 * @return bovedaDTO      
	 */
	public BovedaDTO buscarBovedaPorTipoCustodia(String boveda);
	
	public BovedaDTO obtenerNombreCortoBoveda(long idBoveda);
	
	/**
	 * 
	 * busca una divisaDTO por claveAlfavetica 
	 * @param claveAlfavetica de la divisa
	 * @return divisaDTO      
	 *
	 */
	public DivisaDTO obtenerDivisaPorClaveAlfavetica(String divisa);

	/**
	 * Calcula la diferencia en d&iacute;as entre dos fechas.
	 *
	 * @param Y1
	 *            a&ntilde;o 1
	 * @param M1
	 *            mes 1
	 * @param D1
	 *            d&iacute;a 1
	 * @param Y2
	 *            a&ntilde;o 2
	 * @param M2
	 *            mes 2
	 * @param D2
	 *            d&iacute;a 2
	 * @return long
	 */
	long dateDiff(int Y1, int M1, int D1, int Y2, int M2, int D2);

	/**
	 *
	 * @param inicio
	 * @param Fin
	 * @return
	 */
	long dateDiff(Date inicio , Date Fin);

	/**
	 * Recorre el arreglo de String recibido y construye otro que contiene solo
	 * los elementos distintos de NULL y BLANK.
	 *
	 * @param array
	 * @return String[]
	 */
	public String[] parseArray(String array[]);

	/**
	 * Verifica si el Agente existe en el catalogo
	 *
	 * @param agenteVO
	 * @return PageVO
	 * @throws BusinessException
	 */
	PaginaVO isAgenteRegistrado(AgenteVO agenteVO) throws BusinessException;

	/**
	 * Encapsula las validaciones del AgenteVO verifica que no sea nulo, que
	 * tenga id, folio y cuenta, y que exista.
	 *
	 * @param agenteVO
	 * @throws BusinessException
	 */
	void validaAgente(AgenteVO agenteVO) throws BusinessException;

	/**
	 * Encapsula las validaciones del AgenteVO verifica que no sea nulo, que
	 * tenga id, folio y cuenta, y que exista. Permite identificar si el agente
	 * es traspasante o receptor
	 *
	 * @param agenteVO
	 * @param tipoAgente
	 *            Permite identificar si el agente es traspasante o receptor
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente) throws BusinessException;

	/**
	 * Encapsula las validaciones del AgenteVO verifica que no sea nulo, que
	 * tenga id y folio (opcionalmente la cuenta) y que exista.
	 *
	 * @param agenteVO
	 * @param conCuenta
	 *            true = Indica que se verifique la cuenta false = Indica que no
	 *            se verifique la cuenta
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO validaAgente(AgenteVO agenteVO, boolean conCuenta) throws BusinessException;

	/**
	 * Encapsula las validaciones del AgenteVO verifica que no sea nulo, que
	 * tenga id y folio (opcionalmente la cuenta) y que exista. Permite
	 * identificar si el agente es traspasante o receptor
	 *
	 * @param agenteVO
	 * @param tipoAgente
	 *            Permite identificar si el agente es traspasante o receptor
	 * @param conCuenta
	 *            true = Indica que se verifique la cuenta false = Indica que no
	 *            se verifique la cuenta
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente, boolean conCuenta) throws BusinessException;

	/**
	 * Convierte un objeto clob a su representación en cadena
	 *
	 * @param clob
	 * @return String
	 * @throws SQLException
	 */
	String clobToString(Clob clob) throws SQLException;

	/**
	 * Convierte un objeto java.lang.Clob a java.util.map
	 *
	 * @param clob
	 * @return Map
	 * @throws SQLException
	 */
	Map clobTOMap(Clob clob) throws SQLException;

	/**
	 * Convierte un mapa a su representaci&oacute;n xml
	 *
	 * @param mapa
	 * @return String
	 */
	String mapaToXml(Map mapa);

	/**
     * Obtiene la fecha actual de la BD
     *
     * @return
     */
    public Date getCurrentDate();

    /**
     * Regresa true o false si el cupón es valido para la emisión.
     * @param emisionDTO
     * @return
     */
    public boolean isCuponValidoParaEmision(EmisionVO emisionVO);

    /**
     * Obtiene el TipoInstruccion del mapa inyectado, dada una clave de operacion
     * @param clave		la clave de operacion a buscar
     * @return		el TipoInstruccion correspondiente, null si no se encuentra.
     */
    TipoInstruccionDTO obtenerTipoInstruccionPorClaveOperacion(String clave);

    /**
     * Indica si la combinacion TipoInstruccion - Divisa - Boveda es valida
     * @param tipoInstruccion	Clave Operacion del Tipo de Instruccion
     * @param divisa			Clave alfabetica de la divisa
     * @param boveda			Nombre Corto de la Boveda
     * @return			true si la combinacion es valida, false de lo contrario
     */
    boolean isTipoInstruccionDivisaBovedaValido(String tipoInstruccion, String divisa, String boveda);
    
    /**
     * Indica si la combinacion TipoInstruccion - Divisa es valida
     * @param tipoInstruccion   Clace Operacion del Tipo de Intruccion
     * @param divisa			Clave alfabetica de la divisa
     * return 			true si la combinacion es valida, false de lo contrario 
     */
    boolean isTipoInstruccionDivisaValido(String tipoInstruccion, String divisa);
    
    

}
