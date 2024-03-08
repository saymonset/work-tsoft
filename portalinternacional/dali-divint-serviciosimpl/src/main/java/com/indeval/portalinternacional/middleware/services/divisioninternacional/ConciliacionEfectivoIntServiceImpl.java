package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.util.DetalleConciliacionEfectivoComparator;
import com.indeval.portalinternacional.middleware.services.util.Utils;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CodigoIdentificacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionesConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionesConciliacionInt.TipoOperacionConciliacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

/**
 * @author lmunoz
 *
 */
public class ConciliacionEfectivoIntServiceImpl implements
		ConciliacionEfectivoIntService {
	
	private static final Logger log = LoggerFactory.getLogger(ConciliacionEfectivoIntServiceImpl.class);
	
	private ConciliacionEfectivoDao conciliacionEfectivoDao;
	
	private DivisaDao divisaDao;
	
	private JmsTemplate jmsTemplateMoiConciliaciones;
	
	public PaginaVO consultaConciliacionEfectivoInt(ConciliacionEfectivoIntDTO params, PaginaVO pagina) {
		return conciliacionEfectivoDao.consultaConciliacionEfectivoInt(params, pagina);
	}
	
	public Object[] obtieneTotalesConciliacion(ConciliacionEfectivoIntDTO params, String tipo) {
		return conciliacionEfectivoDao.obtieneTotalesConciliacion(params, tipo);
	}
	
	public PaginaVO consultaDetalleConciliacionEfectivoInt(DetalleConciliacionEfectivoIntDTO params, PaginaVO pagina) {
		return conciliacionEfectivoDao.consultaDetalleConciliacionEfectivoInt(params, pagina);
	}
	
	public BigDecimal obtieneSumaCreditoDebito(DetalleConciliacionEfectivoIntDTO params, String tipo) {
		return conciliacionEfectivoDao.obtieneSumaCreditoDebito(params, tipo);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#getBitacoraConciliacionEfectivoInt(java.lang.String)
	 */
	public List<BitacoraConciliacionEfectivoInt> getBitacoraConciliacionEfectivoInt(
			String idConciliacion) {
		return conciliacionEfectivoDao.getBitacoraConciliacionEfectivoInt(idConciliacion);
	}
	
	public List<String> consultaBicCodes(){
		return conciliacionEfectivoDao.consultaBicCodes();
	}
	
	public List<Divisa> consultaDivisas(Set<String> bicCodes) {
		return conciliacionEfectivoDao.consultaDivisas(bicCodes);
	}
	
	public List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas) {
		return conciliacionEfectivoDao.consultaCuentas(bicCodes, divisas);
	}
	
	@SuppressWarnings("unchecked")
	public List<CodigoIdentificacionEfectivoInt> consultaCodigosIdentificacion() {
		return conciliacionEfectivoDao.findAll(CodigoIdentificacionEfectivoInt.class.getName());
	}
	
	public List<ComentarioEfectivoInt> consultaComentarios(Long idDetalle) {
		return conciliacionEfectivoDao.findComentariosByIdDetalle(idDetalle);
	}
	
	public void guardarComentario(ComentarioEfectivoInt comentario) {
		conciliacionEfectivoDao.save(comentario);
	}
	
	public void actualizaComentarioDetalle(Long idDetalle){
		DetalleConciliacionEfectivoInt detalle = (DetalleConciliacionEfectivoInt)conciliacionEfectivoDao.getByPk(DetalleConciliacionEfectivoInt.class, idDetalle);
		
		if(detalle != null){
			detalle.setConComentarios(true);
			conciliacionEfectivoDao.update(detalle);
		}
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaCuentas()
	 */
	public PaginaVO consultaCuentas(final PaginaVO paginaVO, CuentaEfectivoIntDTO dto) {
		return conciliacionEfectivoDao.consultaCuentasEfectivo(paginaVO, dto);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaPaises()
	 */
	public List<PaisInt> consultaPaises() {
		return conciliacionEfectivoDao.consultaPaises();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaDivisaPorId(java.lang.Long)
	 */
	public Divisa consultaDivisaPorPk(Long id) {
		return (Divisa)conciliacionEfectivoDao.getByPk(Divisa.class, id);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaBovedaPorPk(java.lang.Long)
	 */
	public Boveda consultaBovedaPorPk(Long id) {
		return (Boveda)conciliacionEfectivoDao.getByPk(Boveda.class, id);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaPaisPorPk(java.lang.Long)
	 */
	public PaisInt consultaPaisPorPk(Integer id) {
		return (PaisInt)conciliacionEfectivoDao.getByPk(PaisInt.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#guardaCuentaEfectivoInt(com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt)
	 */
	public void guardaCuentaEfectivoInt(CuentaEfectivoInt cuenta) {
		conciliacionEfectivoDao.save(cuenta);
		conciliacionEfectivoDao.flush();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#consultaCuentaEfectivoIntPorPk(java.lang.Long)
	 */
	public CuentaEfectivoInt consultaCuentaEfectivoIntPorPk(Long id) {
		return (CuentaEfectivoInt)conciliacionEfectivoDao.getByPk(CuentaEfectivoInt.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#actualizaCuentaEfectivoInt(com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt)
	 */
	public void actualizaCuentaEfectivoInt(CuentaEfectivoInt cuenta) {
		conciliacionEfectivoDao.update(cuenta);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#existeCuenta(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public boolean existeCuenta(String bicCode, Long idDivisa, String cuenta, Long idCuenta) {
		return conciliacionEfectivoDao.existeCuenta(bicCode, idDivisa, cuenta, idCuenta);
	}

	@Override
	public boolean existeCuenta950(String bicCode, Long idDivisa, String cuenta950, Long idCuenta) {
		return conciliacionEfectivoDao.existeCuenta950(bicCode, idDivisa, cuenta950, idCuenta);
	}
	
	public BigDecimal consultaSaldoInicialCuenta(Set<String> bicCodes, String divisa, Set<String> cuentas) {
		return conciliacionEfectivoDao.consultaSaldoInicialCuenta(bicCodes, divisa, cuentas);
	}

	public BigDecimal consultaSaldoAcumuladoHistorico(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha) {
		return conciliacionEfectivoDao.consultaSaldoAcumuladoHistorico(bicCodes, divisa, cuentas, fecha);
	}
	
	public BigDecimal consultaSaldoMensaje(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha, String balance) {
		return conciliacionEfectivoDao.consultaSaldoMensaje(bicCodes, divisa, cuentas, fecha, balance);
	}
	
	public void ordenarDetalleConciliacion(List<DetalleConciliacionEfectivoInt> listaDetalleConciliacion) {
		
		if(listaDetalleConciliacion != null){
			List<DetalleConciliacionEfectivoInt> listaDetalleConciliacionOrdenada = new ArrayList<DetalleConciliacionEfectivoInt>(listaDetalleConciliacion);
			
			Collections.sort(listaDetalleConciliacionOrdenada, new DetalleConciliacionEfectivoComparator());
			List<DetalleConciliacionEfectivoInt> listaDetalleConciliacionOrdenadaB = new ArrayList<DetalleConciliacionEfectivoInt>(listaDetalleConciliacionOrdenada);
			
			double cantidad1 = 0;
			double cantidad2 = 0;
			Double cantidadB = null;
			DetalleConciliacionEfectivoInt detalleConciliacionB = null;
			
			for(DetalleConciliacionEfectivoInt detalleConciliacion : listaDetalleConciliacionOrdenada){
				if(detalleConciliacion.getMonto() != null && detalleConciliacion.getHash() == null){
					cantidad1 = detalleConciliacion.getMonto().doubleValue() - .01;
					cantidad2 = detalleConciliacion.getMonto().doubleValue() + .01;
				
					for (Iterator<DetalleConciliacionEfectivoInt> i = listaDetalleConciliacionOrdenadaB.iterator(); i.hasNext();) {
						detalleConciliacionB = (DetalleConciliacionEfectivoInt)i.next();
						cantidadB = detalleConciliacionB.getMonto().doubleValue();
						
						if(cantidadB != null && Utils.betweenInclusive(cantidadB, cantidad1, cantidad2) && detalleConciliacion != detalleConciliacionB){
							if(!detalleConciliacion.getTipo().equals(detalleConciliacionB.getTipo())){
								log.debug("pareja:" + detalleConciliacion.getMonto() + "==" + cantidadB);
								detalleConciliacion.setHash(1);
								detalleConciliacionB.setHash(1);
								
								listaDetalleConciliacionOrdenadaB.remove(detalleConciliacion);
								listaDetalleConciliacionOrdenadaB.remove(detalleConciliacionB);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService#findDivisas()
	 */
	public List<SelectItem> findDivisas() {
		Divisa[] listaDivisas = divisaDao.findDivisas();
    	List<SelectItem> listaSelectDivisas = new ArrayList<SelectItem>();
    	for (int i=0; i<listaDivisas.length; i++){
			listaSelectDivisas.add(new SelectItem(listaDivisas[i].getIdDivisa().toString(), listaDivisas[i].getClaveAlfabetica() + " - " + listaDivisas[i].getDescripcion()));
    	}
    	
    	return listaSelectDivisas;
	}
	
	public boolean generaReporteAuditoria(DetalleConciliacionEfectivoIntDTO detalle) {
		boolean enviado = true;
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, OperacionesConciliacionInt.class);
		
		OperacionesConciliacionInt op = new OperacionesConciliacionInt();
		op.setOperacion(TipoOperacionConciliacion.REPORTE_AUDITORIA_EFECTIVO);
		op.setDetalle(detalle);
		
		final String msgRetiroXml = xstream.toXML(op);
		log.debug("XMLConciliacion====>"+msgRetiroXml);
		
		try {
			jmsTemplateMoiConciliaciones.send(new MessageCreator(){
	            public Message createMessage(Session session) throws JMSException {
	                final Message msg = session.createTextMessage(msgRetiroXml);
	                return msg;
	            }
	        });
		} catch(JmsException e){
			log.error("Error al enviar el reporte", e);
			enviado = false;
		}
		return enviado;
	}
	
	public List<Boveda> consultaBovedasEfectivo() {
		return conciliacionEfectivoDao.consultaBovedasEfectivo();
	}

	/**
	 * @return the conciliacionEfectivoDao
	 */
	public ConciliacionEfectivoDao getConciliacionEfectivoDao() {
		return conciliacionEfectivoDao;
	}

	/**
	 * @param conciliacionEfectivoDao the conciliacionEfectivoDao to set
	 */
	public void setConciliacionEfectivoDao(
			ConciliacionEfectivoDao conciliacionEfectivoDao) {
		this.conciliacionEfectivoDao = conciliacionEfectivoDao;
	}

	/**
	 * @return the divisaDao
	 */
	public DivisaDao getDivisaDao() {
		return divisaDao;
	}

	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * @return the jmsTemplateMoiConciliaciones
	 */
	public JmsTemplate getJmsTemplateMoiConciliaciones() {
		return jmsTemplateMoiConciliaciones;
	}

	/**
	 * @param jmsTemplateMoiConciliaciones the jmsTemplateMoiConciliaciones to set
	 */
	public void setJmsTemplateMoiConciliaciones(
			JmsTemplate jmsTemplateMoiConciliaciones) {
		this.jmsTemplateMoiConciliaciones = jmsTemplateMoiConciliaciones;
	}
}