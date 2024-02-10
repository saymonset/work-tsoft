/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao;

/**
 * @author César Hernández
 *
 */
public class ConfirmacionesIntDaoImpl extends BaseDaoHibernateImpl implements ConfirmacionesIntDao {
	
	private final int CUENTA_COMERCIAL = 1;
	private final int CUENTA_CUSTODIA = 2;

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#consultaConfirmaciones(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO)
	 */
	@SuppressWarnings("unchecked")
	public PaginaVO consultaConfirmaciones(final ConfirmacionIntDTO confirmacionIntDTO, final PaginaVO paginaVO) throws BusinessException {
		
		final List<String> cuentas = obtieneCuentas(confirmacionIntDTO); 
		
		List<ConfirmacionEfectivo> confirmaciones = (List<ConfirmacionEfectivo>)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public List<ConfirmacionEfectivo> doInHibernate(Session session) throws HibernateException, SQLException {
		        		
		        		Criteria criteria = session.createCriteria(ConfirmacionEfectivo.class, "ce");
		        		
		        		creaCriterio(confirmacionIntDTO, criteria);
		        		
		        		if(cuentas != null){
		        			if(cuentas.size() > 0){
		        				criteria.add(Restrictions.in("ce.cuenta", cuentas));
		        			}
		        		}
		        		
		        		if(paginaVO != null && paginaVO.getRegistrosXPag() != PaginaVO.TODOS){
		        			criteria.setFirstResult(paginaVO.getOffset());
		        			criteria.setMaxResults(paginaVO.getRegistrosXPag());
		        			criteria.setFetchSize(paginaVO.getRegistrosXPag());
		        		}
		        		
		        		criteria.addOrder(Order.desc("idConfirmacion"));
		        		
		        		return criteria.list();
		        	}
				});
		
			if(confirmaciones != null){
				paginaVO.setRegistros(confirmaciones);
				
				Long totalConfirmaciones = (Long)
						this.getHibernateTemplate().execute(new HibernateCallback() {
				        	public Long doInHibernate(Session session) throws HibernateException, SQLException {
				        		
				        		Criteria criteria = session.createCriteria(ConfirmacionEfectivo.class, "ce");
				        		
				        		criteria.setProjection(Projections.rowCount());
				        		creaCriterio(confirmacionIntDTO, criteria);
				        		
				        		if(cuentas != null){
				        			if(cuentas.size() > 0){
				        				criteria.add(Restrictions.in("ce.cuenta", cuentas));
				        			}
				        		}

				        		return Long.valueOf(criteria.uniqueResult().toString());
				        	}
						});
				
				paginaVO.setTotalRegistros(totalConfirmaciones.intValue());
			}
			
			
			return paginaVO;
	}
	
	/**
	 * Crea el criterio para la consulta en criteria
	 * @param criterio
	 * @param criteria
	 */
	private void creaCriterio(ConfirmacionIntDTO criterio, Criteria criteria){
		Conjunction con1 = new Conjunction();
		Conjunction con2 = new Conjunction();
		
		boolean hayConjunto1 = false;
		boolean hayConjunto2 = false;
		
		if(criterio.getBicCodes() != null && criterio.getBicCodes().size() > 0 && !criterio.getBicCodes().contains("TODOS")){
			con1.add(Restrictions.in("ce.bicCode", criterio.getBicCodes()));
			hayConjunto1 = true;
		}
		
		if(criterio.getDivisas() != null && criterio.getDivisas().size() > 0 && !criterio.getDivisas().contains("TODAS")){
			con1.add(Restrictions.in("ce.divisa", criterio.getDivisas()));
			hayConjunto1 = true;
		}
		
		if(criterio.getCuentas() != null && criterio.getCuentas().size() > 0 && !criterio.getCuentas().contains("TODAS")){
			con1.add(Restrictions.in("ce.cuenta", criterio.getCuentas()));
			hayConjunto1 = true;
		}
		
		//if(criterio.getMtEspecifico() == null || criterio.getMtEspecifico().equals("")){
			List<String> mensajes = obtieneTiposMensajeNO99(criterio.getTipoMensajes());
			if(criterio.getTipoMensajes() != null && criterio.getTipoMensajes().size() > 0 && !criterio.getTipoMensajes().contains("TODOS") 
					&& !mensajes.isEmpty()){
				
				hayConjunto1 = true;
				
				if(mensajes.contains("X90")){
					mensajes = limpiaMensaje90(mensajes);
					if(mensajes.size() == 0){
						con1.add(Restrictions.like("ce.tipoMensaje", "%90"));
					}
					else{
						con1.add(Restrictions.or(Restrictions.like("ce.tipoMensaje", "%90"), Restrictions.in("ce.tipoMensaje", mensajes)));
					}
				}
				else{
					if(!mensajes.isEmpty()){
						con1.add(Restrictions.in("ce.tipoMensaje", mensajes));
					}
				}
			}
			
			//Subconjunto 2
			List<String> mensajes99 = obtieneTiposMensaje99(criterio.getTipoMensajes());
			if(!mensajes99.isEmpty()){
				hayConjunto2 = true;
				
				if(criterio.getBicCodes() != null && criterio.getBicCodes().size() > 0 && !criterio.getBicCodes().contains("TODOS")){
					con2.add(Restrictions.in("ce.bicCode", criterio.getBicCodes()));
				}
				
				if(criterio.getTipoMensajes() != null && criterio.getTipoMensajes().size() > 0 && !criterio.getTipoMensajes().contains("TODOS") 
						&& !mensajes99.isEmpty()){
					if(mensajes99.contains("X99")){
						con2.add(Restrictions.like("ce.tipoMensaje", "%99"));
					}
					else{
						mensajes99 = limpiaMensaje99(mensajes99);
						con2.add(Restrictions.in("ce.tipoMensaje", mensajes99));
					}
				}
			}
		/*}
		else{
			criteria.add(Restrictions.eq("ce.tipoMensaje", criterio.getMtEspecifico()));
		}*/
		
		/** Por el tipo de mensaje credito, debito o todas */
		if(criterio.getTipoNotificacion() == ConfirmacionIntDTO.TIPO_NOTIFICACION_DEBITO){
			criteria.add(Restrictions.or(Restrictions.like("ce.tipoMensaje", "%90"), Restrictions.eq("ce.tipoMensaje", "900")));
		}
		else{
			if(criterio.getTipoNotificacion() == ConfirmacionIntDTO.TIPO_NOTIFICACION_CREDITO){
				criteria.add(Restrictions.eq("ce.tipoMensaje", "910"));
			}
		}
		
		if(hayConjunto1 && hayConjunto2){
			criteria.add(Restrictions.or(con1, con2));
		}
		else{
			if(hayConjunto1 && !hayConjunto2){
				criteria.add(con1);
			}
			else{
				if(!hayConjunto1 && hayConjunto2){
					criteria.add(con2);
				}
			}
		}
		
		if(criterio.getFechaRecepcionMensajeInicio() != null && criterio.getFechaRecepcionMensajeFin() != null){
			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionMensajeInicio(), true)));
			criteria.add(Restrictions.le("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionMensajeFin(), false)));
		} else if(criterio.getFechaRecepcionMensajeInicio() != null){
			criteria.add(Restrictions.le("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionMensajeInicio(), false)));
		} else if(criterio.getFechaRecepcionMensajeFin() != null){
			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionMensajeFin(), true)));
		}
		
		if(criterio.getFechaCreditoDebitoInicio() != null && criterio.getFechaCreditoDebitoFin() != null){
			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaCreditoDebitoInicio(), true)));
			criteria.add(Restrictions.le("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaCreditoDebitoFin(), false)));
		} else if(criterio.getFechaCreditoDebitoInicio() != null){
			criteria.add(Restrictions.le("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaCreditoDebitoInicio(), false)));
		} else if(criterio.getFechaCreditoDebitoFin() != null){
			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaCreditoDebitoFin(), true)));
		}
		
		if(criterio.getReferenciaMensaje() != null && !criterio.getReferenciaMensaje().equals("")){
			criteria.add(Restrictions.eq("ce.referenciaMensaje", criterio.getReferenciaMensaje()));
		}
		
		if(criterio.getRelatedReference() != null && !criterio.getRelatedReference().equals("")){
			criteria.add(Restrictions.eq("ce.relatedReference", criterio.getRelatedReference()));
		}
		
		if(criterio.getIdFolio() != null && !criterio.getIdFolio().equals("")){
			criteria.add(Restrictions.eq("ce.idFolio", criterio.getIdFolio()));
		}
		
		if(criterio.getIsin() != null && !criterio.getIsin().equals("")){
			criteria.add(Restrictions.eq("ce.comentariosDali", criterio.getIsin()));
		}
		
		if(criterio.getFechaRecepcionDaliInicio() != null && criterio.getFechaRecepcionDaliFin() != null){
			criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionDaliInicio(), true)));
			criteria.add(Restrictions.le("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionDaliFin(), false)));
		} else if(criterio.getFechaRecepcionDaliInicio() != null){
			criteria.add(Restrictions.le("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionDaliInicio(), false)));
		} else if(criterio.getFechaRecepcionDaliFin() != null){
			criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(criterio.getFechaRecepcionDaliFin(), true)));
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<String> obtieneCuentas(final ConfirmacionIntDTO confirmacionIntDTO){
		List<String> cuentas = (List<String>)
		this.getHibernateTemplate().execute(new HibernateCallback() {
        	public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
        		List<Integer> tipoCuentas = new ArrayList<Integer>();
        		if(confirmacionIntDTO.isCuentasCustodia()){
        			tipoCuentas.add(CUENTA_CUSTODIA);
        		}
        		if(confirmacionIntDTO.isCuentasComercial()){
        			tipoCuentas.add(CUENTA_COMERCIAL);
        		}
        		
        		if(tipoCuentas.size() > 0){
	        		Criteria cuentasCriteria = session.createCriteria(CuentaEfectivoInt.class);
	        		cuentasCriteria.setProjection(Projections.property("cuenta").as("cuentas"));
	        		cuentasCriteria.add(Restrictions.eq("activo", Boolean.TRUE));
	        		
	        		if(tipoCuentas.size() > 0){
	        			cuentasCriteria.add(Restrictions.in("tipoCuenta", tipoCuentas));
	        		}
	        		
	        		return cuentasCriteria.list();
        		}
        		else{
        			return null;
        		}
        		
        	}
		});
		
		return cuentas;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#consultaBitacoraConfirmacion(java.lang.Long)
	 */
	public BitacoraConfirmacion consultaBitacoraConfirmacion(final Long idConfirmacion){
		BitacoraConfirmacion bitacora = (BitacoraConfirmacion)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public BitacoraConfirmacion doInHibernate(Session session) throws HibernateException, SQLException {
		        		
		        		Criteria criteria = session.createCriteria(BitacoraConfirmacion.class, "b");
		        		criteria.add(Restrictions.eq("b.idConfirmacion", idConfirmacion));
		        		
		        		return (BitacoraConfirmacion)criteria.uniqueResult();
		        	}
				});
		
		return bitacora;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#agregaActualizaComentariosDali(java.lang.Long, java.lang.String)
	 */
	public void agregaActualizaComentariosDali(final Long idConfirmacion, final String comentariosDali) {
		final StringBuffer hql = new StringBuffer();
		
		hql.append("UPDATE " + ConfirmacionEfectivo.class.getName() + " ");
		hql.append("SET comentariosDali = :comentariosDali ");
		hql.append("WHERE idConfirmacion = :idConfirmacion ");
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql.toString());				
				query.setLong("idConfirmacion", idConfirmacion);	
				query.setString("comentariosDali", comentariosDali);	
				return query.executeUpdate();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#agregaActualizaIdFolio(java.lang.Long, java.lang.String)
	 */
	public void agregaActualizaIdFolio(final Long idConfirmacion, final String idFolio) {
		final StringBuffer hql = new StringBuffer();
		
		hql.append("UPDATE " + ConfirmacionEfectivo.class.getName() + " ");
		hql.append("SET idFolio = :idFolio ");
		hql.append("WHERE idConfirmacion = :idConfirmacion ");
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql.toString());				
				query.setLong("idConfirmacion", idConfirmacion);	
				query.setString("idFolio", idFolio);	
				return query.executeUpdate();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#obtieneSumaTotalCreditos(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO)
	 */
	public Double obtieneSumaTotalCreditos(final ConfirmacionIntDTO dto) {
		final List<String> cuentas = obtieneCuentas(dto); 
		
		Double total = (Double)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public Double doInHibernate(Session session) throws HibernateException, SQLException {
		        		
		        		Criteria criteria = session.createCriteria(ConfirmacionEfectivo.class, "ce");
		        		
		        		criteria.setProjection(Projections.sum("ce.cantidad"));
		        		if(dto.getBicCodes() != null && dto.getBicCodes().size() > 0 && !dto.getBicCodes().contains("TODOS")){
		        			criteria.add(Restrictions.in("ce.bicCode", dto.getBicCodes()));
		        		}
		        		
		        		if(dto.getDivisas() != null && dto.getDivisas().size() > 0 && !dto.getDivisas().contains("TODAS")){
		        			criteria.add(Restrictions.in("ce.divisa", dto.getDivisas()));
		        		}
		        		
		        		if(dto.getCuentas() != null && dto.getCuentas().size() > 0 && !dto.getCuentas().contains("TODAS")){
		        			criteria.add(Restrictions.in("ce.cuenta", dto.getCuentas()));
		        		}
		        		
		        		criteria.add(Restrictions.like("ce.tipoMensaje", "910"));
		        		
		        		if(dto.getFechaRecepcionMensajeInicio() != null && dto.getFechaRecepcionMensajeFin() == null){
		        			criteria.add(Restrictions.lt("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeInicio(), false)));
		        		}
		        		
		        		if(dto.getFechaRecepcionMensajeFin() != null && dto.getFechaRecepcionMensajeInicio() == null){
		        			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeFin(), true)));
		        		}
		        		
		        		if(dto.getFechaRecepcionMensajeInicio() != null && dto.getFechaRecepcionMensajeFin() != null){
		        			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeInicio(), true)));
		        			criteria.add(Restrictions.lt("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeFin(), false)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoInicio() != null && dto.getFechaCreditoDebitoFin() == null){
		        			criteria.add(Restrictions.lt("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoInicio(), false)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoFin() != null && dto.getFechaCreditoDebitoInicio() == null){
		        			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoFin(), true)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoInicio() != null && dto.getFechaCreditoDebitoFin() != null){
		        			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoInicio(), true)));
		        			criteria.add(Restrictions.lt("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoFin(), false)));
		        		}
		        		
		        		if(dto.getReferenciaMensaje() != null && !dto.getReferenciaMensaje().equals("")){
		        			criteria.add(Restrictions.eq("ce.referenciaMensaje", dto.getReferenciaMensaje()));
		        		}
		        		
		        		if(dto.getRelatedReference() != null && !dto.getRelatedReference().equals("")){
		        			criteria.add(Restrictions.eq("ce.relatedReference", dto.getRelatedReference()));
		        		}
		        		
		        		if(dto.getIdFolio() != null && !dto.getIdFolio().equals("")){
		        			criteria.add(Restrictions.eq("ce.idFolio", dto.getIdFolio()));
		        		}
		        		
		        		if(dto.getIsin() != null && !dto.getIsin().equals("")){
		        			criteria.add(Restrictions.eq("ce.comentariosDali", dto.getIsin()));
		        		}
		        		
		        		if(dto.getFechaRecepcionDaliInicio() != null && dto.getFechaRecepcionDaliFin() == null){
		        			criteria.add(Restrictions.lt("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliInicio(), false)));
		        		}
		        		
		        		if(dto.getFechaRecepcionDaliFin() != null && dto.getFechaRecepcionDaliInicio() == null){
		        			criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliFin(), true)));
		        		}
		        		
		        		if(dto.getFechaRecepcionDaliInicio() != null && dto.getFechaRecepcionDaliFin() != null){
		        			criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliInicio(), true)));
		        			criteria.add(Restrictions.lt("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliFin(), false)));
		        		}
		        		
		        		if(cuentas != null){
		        			if(cuentas.size() > 0){
		        				criteria.add(Restrictions.in("ce.cuenta", cuentas));
		        			}
		        		}

		        		Object cantidad = criteria.uniqueResult();
		        		
		        		cantidad = (cantidad == null) ? "0.0" : cantidad;
		        		
		        		return Double.valueOf(cantidad.toString());
		        	}
				});
		
		return total;
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao#obtieneSumaTotalDebitos(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO)
	 */
	public Double obtieneSumaTotalDebitos(final ConfirmacionIntDTO dto) {
		final List<String> cuentas = obtieneCuentas(dto); 
		
		Double total = (Double)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public Double doInHibernate(Session session) throws HibernateException, SQLException {
		        		
		        		Criteria criteria = session.createCriteria(ConfirmacionEfectivo.class, "ce");
		        		
		        		criteria.setProjection(Projections.sum("ce.cantidad"));
		        		if(dto.getBicCodes() != null && dto.getBicCodes().size() > 0 && !dto.getBicCodes().contains("TODOS")){
		        			criteria.add(Restrictions.in("ce.bicCode", dto.getBicCodes()));
		        		}
		        		
		        		if(dto.getDivisas() != null && dto.getDivisas().size() > 0 && !dto.getDivisas().contains("TODAS")){
		        			criteria.add(Restrictions.in("ce.divisa", dto.getDivisas()));
		        		}
		        		
		        		if(dto.getCuentas() != null && dto.getCuentas().size() > 0 && !dto.getCuentas().contains("TODAS")){
		        			criteria.add(Restrictions.in("ce.cuenta", dto.getCuentas()));
		        		}
		        		
		        		if(!dto.getTipoMensajes().contains("TODOS")){
			        		List<String> mensajes = obtieneTiposMensajeNO99(dto.getTipoMensajes());
			        		if(mensajes.contains("910")){
			        			for(int inx = 0; inx < mensajes.size(); inx++){
			        				if(mensajes.get(inx).equals("910")){
			        					mensajes.remove(inx);
			        					break;
			        				}
			        			}
			        		}
			        			
		        			if(mensajes.contains("X90")){
		        				mensajes = limpiaMensaje90(mensajes);
		        				if(mensajes.size() == 0){
		        					criteria.add(Restrictions.like("ce.tipoMensaje", "%90"));
		        				}
		        				else{
		        					criteria.add(Restrictions.or(Restrictions.like("ce.tipoMensaje", "%90"), Restrictions.in("ce.tipoMensaje", mensajes)));
		        				}
		        			}
		        			else{
		        				if(!mensajes.isEmpty()){
		        					criteria.add(Restrictions.in("ce.tipoMensaje", mensajes));
		        				}
		        			}
		        		}
		        		else{
		        			criteria.add(Restrictions.or(Restrictions.like("ce.tipoMensaje", "%90"), Restrictions.eq("ce.tipoMensaje", "900")));
		        		}
	        			
		        		if(dto.getFechaRecepcionMensajeInicio() != null && dto.getFechaRecepcionMensajeFin() == null){
		        			criteria.add(Restrictions.lt("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeInicio(), false)));
		        		}
		        		
		        		if(dto.getFechaRecepcionMensajeFin() != null && dto.getFechaRecepcionMensajeInicio() == null){
		        			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeFin(), true)));
		        		}
		        		
		        		if(dto.getFechaRecepcionMensajeInicio() != null && dto.getFechaRecepcionMensajeFin() != null){
		        			criteria.add(Restrictions.ge("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeInicio(), true)));
		        			criteria.add(Restrictions.lt("ce.fechaEmision", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionMensajeFin(), false)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoInicio() != null && dto.getFechaCreditoDebitoFin() == null){
		        			criteria.add(Restrictions.lt("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoInicio(), false)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoFin() != null && dto.getFechaCreditoDebitoInicio() == null){
		        			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoFin(), true)));
		        		}
		        		
		        		if(dto.getFechaCreditoDebitoInicio() != null && dto.getFechaCreditoDebitoFin() != null){
		        			criteria.add(Restrictions.ge("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoInicio(), true)));
		        			criteria.add(Restrictions.lt("ce.fechaCreditoDebito", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaCreditoDebitoFin(), false)));
		        		}
	        			
	        			if(dto.getReferenciaMensaje() != null && !dto.getReferenciaMensaje().equals("")){
	        				criteria.add(Restrictions.eq("ce.referenciaMensaje", dto.getReferenciaMensaje()));
	        			}
	        			
	        			if(dto.getRelatedReference() != null && !dto.getRelatedReference().equals("")){
	        				criteria.add(Restrictions.eq("ce.relatedReference", dto.getRelatedReference()));
	        			}
	        			
	        			if(dto.getIdFolio() != null && !dto.getIdFolio().equals("")){
	        				criteria.add(Restrictions.eq("ce.idFolio", dto.getIdFolio()));
	        			}
	        			
	        			if(dto.getIsin() != null && !dto.getIsin().equals("")){
	        				criteria.add(Restrictions.eq("ce.comentariosDali", dto.getIsin()));
	        			}
	        			
	        			if(dto.getFechaRecepcionDaliInicio() != null && dto.getFechaRecepcionDaliFin() == null){
	        				criteria.add(Restrictions.lt("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliInicio(), false)));
	        			}
	        			
	        			if(dto.getFechaRecepcionDaliFin() != null && dto.getFechaRecepcionDaliInicio() == null){
	        				criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliFin(), true)));
	        			}
	        			
	        			if(dto.getFechaRecepcionDaliInicio() != null && dto.getFechaRecepcionDaliFin() != null){
	        				criteria.add(Restrictions.ge("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliInicio(), true)));
	        				criteria.add(Restrictions.lt("ce.fechaRecepcionDali", DateUtils.preparaFechaConExtremoEnSegundos(dto.getFechaRecepcionDaliFin(), false)));
	        			}
		        		
		        		if(cuentas != null){
		        			if(cuentas.size() > 0){
		        				criteria.add(Restrictions.in("ce.cuenta", cuentas));
		        			}
		        		}

		        		Object cantidad = criteria.uniqueResult();
		        		
		        		cantidad = (cantidad == null) ? "0.0" : cantidad;
		        		
		        		return Double.valueOf(cantidad.toString());
		        	}
				});
		
		return total;
	}
	
	/**
	 * obtiene el calendario de derechos por isin
	 */
	public Long consultaCalendarioDerechosByIsin(final String isin) {
		final StringBuilder sb = new StringBuilder();		
		sb.append("select count(a.idCalendario) FROM " + CalendarioDerechos.class.getName() + " a ");
		sb.append("where a.isin = :isin");	
		
		Long total = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("isin", isin);
				return query.uniqueResult();
			}
		});
		return total;
	}

	/**
	 * Quita los tipos de mensajes *99
	 * @param list
	 * @return
	 */
	private List<String> obtieneTiposMensajeNO99(List<String> list){ 
		List<String> tipoMensaje99 = new ArrayList<String>();
		
		for(String s : list){
			if(s.length() == 3 && !s.substring(1,3).contains("99")){
				tipoMensaje99.add(s);
			} else if(s.length() < 3){
				tipoMensaje99.add(s);
			}
		}
		
		return tipoMensaje99;
	}
	
	/**
	 * Borra de la lista los mensajes X90
	 * @param list
	 * @return
	 */
	private List<String> limpiaMensaje90(List<String> list){
		List<String> l = new ArrayList<String>();
		
		if(list.contains("X90")){
			for(int inx = 0; inx < list.size(); inx++){
				if(list.get(inx).equals("900") || list.get(inx).equals("910")){
					l.add(list.get(inx));
				}
			}
		}
			
		return l;
	}
	
	/**
	 * Quita los tipos de mensajes *90, 910 y 900. Regresa puros mensajes 99
	 * @param list
	 * @return
	 */
	private List<String> obtieneTiposMensaje99(List<String> list){ 
		List<String> tipoMensaje99 = new ArrayList<String>();
		
		for(String s : list){
			if(s.length() == 3 && s.substring(1,3).contains("99")){
				tipoMensaje99.add(s);
			} else if(s.length() < 3){
				tipoMensaje99.add(s);
			}
				
		}
		
		return tipoMensaje99;
	}
	
	/**
	 * Borra de la lista los mensajes X90
	 * @param list
	 * @return
	 */
	private List<String> limpiaMensaje99(List<String> list){
		if(list.contains("X99")){
			for(int inx = 0; inx < list.size(); inx++){
				if(list.get(inx).equals("X99")){
					list.remove(inx);
					return list;
				}
			}
		}
			
		return list;
	}
}
