package com.indeval.portaldali.middleware.services.mercadodinero.ejb;

import java.io.File;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;


@Stateless(name = "ejb.util", mappedName = "ejb.util")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(UtilServices.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UtilBean implements UtilServices {

	@Autowired
	private UtilServices utilService = null;

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return String construido a partir de la fecha/hora
	 */
	public String getLlaveFolio() {
		return utilService.getLlaveFolio();
	}

	public BigInteger getFolio(String nombreSecuencia) throws BusinessException {
		return utilService.getFolio(nombreSecuencia);
	}

	public boolean isCplexActive() {
		return utilService.isCplexActive();
	}

	/**
	 * Lee un archivo de texto y regresa la informacion en un String
	 *
	 * @param archivo
	 * @return String
	 * @throws BusinessException
	 */
	public String leeArchivo(File archivo) throws BusinessException {
		return utilService.leeArchivo(archivo);
	}

	/**
	 * Verifica que la fase sea valida para el servicio de acuerdo al mapa de
	 * fases que recibe. En caso de que no arroja una BusinessException
	 *
	 * @param fasesAbiertas
	 * @return int - Fase activa
	 * @throws BusinessException
	 */
	public int validaFase(Map fasesAbiertas) throws BusinessException {
		return utilService.validaFase(fasesAbiertas);
	}

	/**
	 * Graba informacion proporcionada en un String en el archivo de texto
	 * indicado
	 *
	 * @param archivo
	 * @param informacion
	 * @return Boolean true si grabo existosamente, false en caso contrario
	 * @throws BusinessException
	 */
	public Boolean grabaArchivo(File archivo, String informacion) throws BusinessException {
		return utilService.grabaArchivo(archivo, informacion);
	}

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
	public String validarClaveTipo(String clave, String tipo) throws BusinessException {
		return utilService.validarClaveTipo(clave, tipo);
	}

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
	public Date getLaborableDay(Date fecha, int plazo) {
		return utilService.getLaborableDay(fecha, plazo);
	}

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
	public Date agregarDiasHabiles(Date fechaInicial, int offset) throws BusinessException {
		return utilService.agregarDiasHabiles(fechaInicial, offset);
	}

	/**
	 * Modifica los dias indicados en diasXModificar.
	 *
	 * @param fecha
	 * @param campoXModificar
	 * @param cantidadAModificar
	 * @return Date
	 */
	public Date modificaFecha(Date fecha, int campoXModificar, int cantidadAModificar) {
		return utilService.modificaFecha(fecha, campoXModificar, cantidadAModificar);
	}

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
	public void validaDTONoNulo(Object parametro, String tipo) throws BusinessException {
		utilService.validaDTONoNulo(parametro, tipo);
	}

	/**
	 * @param operacion
	 * @throws BusinessException
	 */
	public void validarHoraOperacion(String operacion) throws BusinessException {
		utilService.validarHoraOperacion(operacion);
	}

	/**
	 * Este m&eacute;todo valida que el objeto recibido como par&aacute;metro es
	 * un n&acuteumero y que no exceda el patternLimit
	 *
	 * @param numero
	 * @param patternLimit
	 * @throws BusinessException
	 */
	public void validaNumber(Object numero, String patternLimit) throws BusinessException {
		utilService.validaNumber(numero, patternLimit);
	}

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
	public void validaPagina(PaginaVO paginaVO, String mensaje) throws BusinessException {
		utilService.validaPagina(paginaVO, mensaje);
	}

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
	public long dateDiff(int Y1, int M1, int D1, int Y2, int M2, int D2) {
		return utilService.dateDiff(Y1, M1, D1, Y2, M2, D2);
	}

	public long dateDiff(Date inicio, Date Fin) {
		return utilService.dateDiff(inicio, Fin);
	}


	/**
	 * Recorre el arreglo de String recibido y construye otro que contiene solo
	 * los elementos distintos de NULL y BLANK.
	 *
	 * @param array
	 * @return String[]
	 */
	public String[] parseArray(String array[]) {
		return utilService.parseArray(array);
	}

	/**
	 * Verifica si el Agente existe en el catalogo
	 *
	 * @param agenteVO
	 * @return PageVO
	 * @throws BusinessException
	 */
	public PaginaVO isAgenteRegistrado(AgenteVO agenteVO) throws BusinessException {
		return utilService.isAgenteRegistrado(agenteVO);
	}

	/**
	 * Encapsula las validaciones del AgenteVO verifica que no sea nulo, que
	 * tenga id, folio y cuenta, y que exista.
	 *
	 * @param agenteVO
	 * @throws BusinessException
	 */
	public void validaAgente(AgenteVO agenteVO) throws BusinessException {
		utilService.validaAgente(agenteVO);
	}

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
	public PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente) throws BusinessException {
		return utilService.validaAgente(agenteVO, tipoAgente);
	}

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
	public PaginaVO validaAgente(AgenteVO agenteVO, boolean conCuenta) throws BusinessException {
		return utilService.validaAgente(agenteVO, conCuenta);
	}

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
	public PaginaVO validaAgente(AgenteVO agenteVO, String tipoAgente, boolean conCuenta) throws BusinessException {
		return utilService.validaAgente(agenteVO, tipoAgente, conCuenta);
	}

	/**
	 * Convierte un objeto clob a su representación en cadena
	 *
	 * @param clob
	 * @return String
	 * @throws SQLException
	 */
	public String clobToString(Clob clob) throws SQLException {
		return utilService.clobToString(clob);
	}

	/**
	 * Convierte un objeto java.lang.Clob a java.util.map
	 *
	 * @param clob
	 * @return Map
	 * @throws SQLException
	 */
	public Map clobTOMap(Clob clob) throws SQLException {
		return utilService.clobTOMap(clob);
	}

	/**
	 * Convierte un mapa a su representaci&oacute;n xml
	 *
	 * @param mapa
	 * @return String
	 */
	public String mapaToXml(Map mapa) {
		return utilService.mapaToXml(mapa);
	}

	/**
     * Obtiene la fecha actual de la BD
     *
     * @return
     */
    public Date getCurrentDate() {
    	return utilService.getCurrentDate();
    }

    public boolean isCuponValidoParaEmision(EmisionVO emisionVO) {
    	return utilService.isCuponValidoParaEmision(emisionVO);
    }

	public TipoInstruccionDTO obtenerTipoInstruccionPorClaveOperacion(
			String clave) {
		return utilService.obtenerTipoInstruccionPorClaveOperacion(clave);
	}

	public boolean isTipoInstruccionDivisaBovedaValido(String tipoInstruccion,
			String divisa, String boveda) {
		return utilService.isTipoInstruccionDivisaBovedaValido(tipoInstruccion, divisa, boveda);
	}
	public boolean isTipoInstruccionDivisaValido(String tipoInstruccion,
			String divisa) {
		return utilService.isTipoInstruccionDivisaValido(tipoInstruccion, divisa);
	}

	public BovedaDTO buscarBovedaPorTipoCustodia(String boveda) {
		return utilService.buscarBovedaPorTipoCustodia(boveda);
	}

	public DivisaDTO obtenerDivisaPorClaveAlfavetica(String divisa) {
		return utilService.obtenerDivisaPorClaveAlfavetica(divisa);
	}

	public BovedaDTO obtenerNombreCortoBoveda(long idBoveda) {
		// TODO Auto-generated method stub
		return utilService.obtenerNombreCortoBoveda(idBoveda);
	}

}
