package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CodigoIdentificacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;

public interface ConciliacionEfectivoIntService {
	
	/**
	 * Consulta conciliaciones de efectivo
	 * @param conciliacion
	 * @param paginaVO
	 * @return PaginaVO
	 */
	PaginaVO consultaConciliacionEfectivoInt(ConciliacionEfectivoIntDTO conciliacion, PaginaVO paginaVO);
	
	Object[] obtieneTotalesConciliacion(ConciliacionEfectivoIntDTO params, String tipo);
	
	/**
	 * Consulta el detalle de conciliaciones de efectivo
	 * @param detalle
	 * @param paginaVO
	 * @return PaginaVO
	 */
	PaginaVO consultaDetalleConciliacionEfectivoInt(DetalleConciliacionEfectivoIntDTO detalle, PaginaVO paginaVO);
	
	BigDecimal obtieneSumaCreditoDebito(final DetalleConciliacionEfectivoIntDTO params, String tipo);
	
	/**
	 * Obtiene los mensajes de conciliacion de efectivo relativos
	 * a una conciliacion
	 * @param idConciliacion
	 * @return List<BitacoraConciliacionEfectivoInt> lista con los mensajes
	 */
	List<BitacoraConciliacionEfectivoInt> getBitacoraConciliacionEfectivoInt(String idConciliacion);
	
	/**
	 * Obtiene una lista de bicCodes
	 * @return List<String>
	 */
	List<String> consultaBicCodes();
	
	/**
	 * Obtiene una lista de divisas
	 * @param bicCodes
	 * @return List<Divisa>
	 */
	List<Divisa> consultaDivisas(Set<String> bicCodes);
	
	/**
	 * Obtiene una lista de cuentas
	 * @param bicCodes
	 * @param divisas
	 * @return List<String>
	 */
	List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas);
	
	/**
	 * Obtiene una lista de codigos
	 * @return List<CodigoIdentificacionEfectivoInt>
	 */
	List<CodigoIdentificacionEfectivoInt> consultaCodigosIdentificacion();
	
	/**
	 * Obtiene un listado de comentarios por idDetalle
	 * @param idDetalle
	 * @return List<ComentarioEfectivoInt>
	 */
	List<ComentarioEfectivoInt> consultaComentarios(Long idDetalle);
	
	/**
	 * Guarda un comentario
	 * @param comentario
	 */
	void guardarComentario(ComentarioEfectivoInt comentario);
	
	/**
	 * Actualiza si tiene comentarios el detalle de la conciliacion
	 * @param idDetalle
	 */
	void actualizaComentarioDetalle(Long idDetalle);
	
	/**
	 * Obtiene el catalogo cuentas completo
	 * @return
	 */
	PaginaVO consultaCuentas(final PaginaVO paginaVO, CuentaEfectivoIntDTO dto);
	
	/**
	 * Obtiene los paises
	 * @return
	 */
	List<PaisInt> consultaPaises();
	
	/**
	 * Consulta la divisa por ID
	 * @param id
	 * @return
	 */
	Divisa consultaDivisaPorPk(Long id);
	
	/**
	 * Consulta boveda por Pk
	 * @param id
	 * @return
	 */
	Boveda consultaBovedaPorPk(Long id);
	
	/**
	 * Consulta pais por Pk
	 * @param id
	 * @return
	 */
	PaisInt consultaPaisPorPk(Integer id);
	
	/**
	 * Guarda la cuenta efectivo
	 * @param cuenta
	 */
	void guardaCuentaEfectivoInt(CuentaEfectivoInt cuenta);
	
	/**
	 * Consulta una cuenta efectivo por la llave primaria
	 * @param id
	 * @return
	 */
	CuentaEfectivoInt consultaCuentaEfectivoIntPorPk(Long id);
	
	/**
	 * Actualiza una cuenta de efectivo
	 * @param cuenta
	 */
	void actualizaCuentaEfectivoInt(CuentaEfectivoInt cuenta);
	
	/**
	 * Verifica si existe una cuenta con la combinacion de BIC Code, divisa y cuenta
	 * @param bicCode
	 * @param idDivisa
	 * @param cuenta
	 * @return
	 */
	boolean existeCuenta(String bicCode, Long idDivisa, String cuenta, Long idCuenta);
	
	/**
	 * Verifica si existe una cuenta 950 con la combinacion de BIC Code, divisa y cuenta
	 * @param bicCode
	 * @param idDivisa
	 * @param cuenta
	 * @return
	 */
	boolean existeCuenta950(String bicCode, Long idDivisa, String cuenta950, Long idCuenta);
	
	BigDecimal consultaSaldoInicialCuenta(Set<String> bicCodes, String divisa, Set<String> cuentas);
	
	BigDecimal consultaSaldoAcumuladoHistorico(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha);
	
	BigDecimal consultaSaldoMensaje(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha, String balance);
	
	void ordenarDetalleConciliacion(List<DetalleConciliacionEfectivoInt> listaDetalleConciliacion);
	
	List<SelectItem> findDivisas();
	
	List<Boveda> consultaBovedasEfectivo();
	
	boolean generaReporteAuditoria(DetalleConciliacionEfectivoIntDTO detalle);
}