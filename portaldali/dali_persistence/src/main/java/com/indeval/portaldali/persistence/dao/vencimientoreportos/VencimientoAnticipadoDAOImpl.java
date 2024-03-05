package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.model.VencimientoAnticipado;

public class VencimientoAnticipadoDAOImpl extends HibernateDaoSupport implements VencimientoAnticipadoDAO {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void save(VencimientoAnticipado vencimiento) {
		logger.debug("save");
		getHibernateTemplate().persist(vencimiento);
		
	}
	
	public void update(VencimientoAnticipado vencimiento){
		logger.debug("update");
		getHibernateTemplate().update(vencimiento);
	}
	
	public VencimientoAnticipado findById(long id) {
		logger.debug("findById");
		return (VencimientoAnticipado)getSession().get(VencimientoAnticipado.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VencimientoAnticipado> findByInstruccionAndStatus(long idInstruccion, String status) {
		logger.debug("findByInstruccionAndStatus");
		
		Query query = getSession().getNamedQuery("VencimientoAnticipado.findByInstruccionAndStatus");
		
		query.setParameter("idInstruccionOperacion", idInstruccion);
		query.setParameter("estatusSolicitud", status);
		
		
		return (List<VencimientoAnticipado>)query.list();
	}
	
	
}
