package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CodigoIdentificacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;

@Stateless(name = "ejb.conciliacionEfectivoIntService", mappedName = "ejb.conciliacionEfectivoIntService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ConciliacionEfectivoIntService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ConciliacionEfectivoIntServiceEJB implements ConciliacionEfectivoIntService, Serializable {
	
	@Autowired
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	
	public PaginaVO consultaConciliacionEfectivoInt(
			ConciliacionEfectivoIntDTO conciliacion, PaginaVO paginaVO) {
		return conciliacionEfectivoIntService.consultaConciliacionEfectivoInt(conciliacion, paginaVO);
	}
	
	public PaginaVO consultaDetalleConciliacionEfectivoInt(
			DetalleConciliacionEfectivoIntDTO detalle, PaginaVO paginaVO) {
		return conciliacionEfectivoIntService.consultaDetalleConciliacionEfectivoInt(detalle, paginaVO);
	}

	public List<BitacoraConciliacionEfectivoInt> getBitacoraConciliacionEfectivoInt(
			Long idConciliacion) {
		return conciliacionEfectivoIntService.getBitacoraConciliacionEfectivoInt(idConciliacion);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> consultaBicCodes() {
		return conciliacionEfectivoIntService.consultaBicCodes();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Divisa> consultaDivisas(Set<String> bicCodes) {
		return conciliacionEfectivoIntService.consultaDivisas(bicCodes);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas) {
		return conciliacionEfectivoIntService.consultaCuentas(bicCodes, divisas);
	}

	public List<CodigoIdentificacionEfectivoInt> consultaCodigosIdentificacion() {
		return conciliacionEfectivoIntService.consultaCodigosIdentificacion();
	}
	
	public List<ComentarioEfectivoInt> consultaComentarios(Long idDetalle) {
		return conciliacionEfectivoIntService.consultaComentarios(idDetalle);
	}
	
	public void guardarComentario(ComentarioEfectivoInt comentario) {
		conciliacionEfectivoIntService.guardarComentario(comentario);
	}
	
	public void actualizaComentarioDetalle(Long idDetalle) {
		conciliacionEfectivoIntService.actualizaComentarioDetalle(idDetalle);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaCuentas()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaCuentas(final PaginaVO paginaVO, CuentaEfectivoIntDTO dto) {
		return conciliacionEfectivoIntService.consultaCuentas(paginaVO, dto);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaPaises()
	 */
	public List<PaisInt> consultaPaises() {
		return conciliacionEfectivoIntService.consultaPaises();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaDivisaPorPk(java.lang.Long)
	 */
	public Divisa consultaDivisaPorPk(Long id) {
		return conciliacionEfectivoIntService.consultaDivisaPorPk(id);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaBovedaPorPk(java.lang.Long)
	 */
	public Boveda consultaBovedaPorPk(Long id) {
		return conciliacionEfectivoIntService.consultaBovedaPorPk(id);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaPaisPorPk(java.lang.Long)
	 */
	public PaisInt consultaPaisPorPk(Integer id) {
		return conciliacionEfectivoIntService.consultaPaisPorPk(id);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#guardaCuentaEfectivoInt(com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt)
	 */
	public void guardaCuentaEfectivoInt(CuentaEfectivoInt cuenta) {
		conciliacionEfectivoIntService.guardaCuentaEfectivoInt(cuenta);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaCuentaEfectivoIntPorPk(java.lang.Long)
	 */
	public CuentaEfectivoInt consultaCuentaEfectivoIntPorPk(Long id) {
		return conciliacionEfectivoIntService.consultaCuentaEfectivoIntPorPk(id);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#actualizaCuentaEfectivoInt(com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt)
	 */
	public void actualizaCuentaEfectivoInt(CuentaEfectivoInt cuenta) {
		conciliacionEfectivoIntService.actualizaCuentaEfectivoInt(cuenta);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#existeCuenta(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public boolean existeCuenta(String bicCode, Long idDivisa, String cuenta, Long idCuenta) {
		return conciliacionEfectivoIntService.existeCuenta(bicCode, idDivisa, cuenta, idCuenta);
	}
	
	public boolean existeCuenta950(String bicCode, Long idDivisa, String cuenta950, Long idCuenta) {
		return conciliacionEfectivoIntService.existeCuenta950(bicCode, idDivisa, cuenta950, idCuenta);
	}
	
	public BigDecimal consultaSaldoInicialCuenta(Set<String> bicCodes, String divisa, Set<String> cuentas) {
		return conciliacionEfectivoIntService.consultaSaldoInicialCuenta(bicCodes, divisa, cuentas);
	}

	public BigDecimal consultaSaldoAcumuladoHistorico(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha) {
		return conciliacionEfectivoIntService.consultaSaldoAcumuladoHistorico(bicCodes, divisa, cuentas, fecha);
	}
	
	public BigDecimal consultaSaldoMensaje(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha, String balance) {
		return conciliacionEfectivoIntService.consultaSaldoMensaje(bicCodes, divisa, cuentas, fecha, balance);
	}
	
	public void ordenarDetalleConciliacion(List<DetalleConciliacionEfectivoInt> listaDetalleConciliacion) {
		conciliacionEfectivoIntService.ordenarDetalleConciliacion(listaDetalleConciliacion);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#findDivisas()
	 */
	public List<SelectItem> findDivisas() {
		return conciliacionEfectivoIntService.findDivisas();
	}
	
	public List<Boveda> consultaBovedasEfectivo() {
		return conciliacionEfectivoIntService.consultaBovedasEfectivo();
	}
	
	public boolean generaReporteAuditoria(DetalleConciliacionEfectivoIntDTO detalle) {
		return conciliacionEfectivoIntService.generaReporteAuditoria(detalle);
	}
	
	public Object[] obtieneTotalesConciliacion(ConciliacionEfectivoIntDTO params, String tipo) {
		return conciliacionEfectivoIntService.obtieneTotalesConciliacion(params, tipo);
	}
	
	public BigDecimal obtieneSumaCreditoDebito(DetalleConciliacionEfectivoIntDTO params, String tipo) {
		return conciliacionEfectivoIntService.obtieneSumaCreditoDebito(params, tipo);
	}

	/**
	 * @return the conciliacionEfectivoIntService
	 */
	public ConciliacionEfectivoIntService getConciliacionEfectivoIntService() {
		return conciliacionEfectivoIntService;
	}

	/**
	 * @param conciliacionEfectivoIntService the conciliacionEfectivoIntService to set
	 */
	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}
}