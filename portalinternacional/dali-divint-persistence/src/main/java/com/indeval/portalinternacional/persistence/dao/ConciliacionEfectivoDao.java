package com.indeval.portalinternacional.persistence.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;

public interface ConciliacionEfectivoDao extends BaseDao {
	
	PaginaVO consultaConciliacionEfectivoInt(ConciliacionEfectivoIntDTO params, PaginaVO pagina);
	
	Object[] obtieneTotalesConciliacion(ConciliacionEfectivoIntDTO params, String tipo);
	
	PaginaVO consultaDetalleConciliacionEfectivoInt(DetalleConciliacionEfectivoIntDTO params, PaginaVO pagina);
	
	BigDecimal obtieneSumaCreditoDebito(final DetalleConciliacionEfectivoIntDTO params, String tipo);
	
	List<BitacoraConciliacionEfectivoInt> getBitacoraConciliacionEfectivoInt(String idConciliacion);

	List<String> consultaBicCodes();
	
	List<Divisa> consultaDivisas(Set<String> bicCodes);
	
	List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas);
	
	List<ComentarioEfectivoInt> findComentariosByIdDetalle(Long idDetalle);
	
	/**
	 * Consulta todas las cuentas de efectivo
	 * @return
	 */
	PaginaVO consultaCuentasEfectivo(final PaginaVO paginaVO, final CuentaEfectivoIntDTO dto) throws BusinessException;
	
	/**
	 * Consulta los paises del catalogo
	 * @return
	 */
	List<PaisInt> consultaPaises();
	
	/**
	 * Verifica si una cuenta existe con la combinacion BIC Code, divisa y cuenta
	 * @param bicCode
	 * @param divisa
	 * @param cuenta
	 * @return
	 */
	boolean existeCuenta(final String bicCode, final Long idDivisa, final String cuenta, final Long idCuenta);
	
	/**
	 * Verifica si una cuenta 950 existe con la combinacion BIC Code, divisa y cuenta950
	 * @param bicCode
	 * @param divisa
	 * @param cuenta
	 * @return
	 */
	boolean existeCuenta950(final String bicCode, final Long idDivisa, final String cuenta950, final Long idCuenta);
	
	BigDecimal consultaSaldoInicialCuenta(Set<String> bicCodes, String divisa, Set<String> cuentas);
	
	BigDecimal consultaSaldoAcumuladoHistorico(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha);
	
	BigDecimal consultaSaldoMensaje(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha, String balance);
	
	List<Boveda> consultaBovedasEfectivo();
}