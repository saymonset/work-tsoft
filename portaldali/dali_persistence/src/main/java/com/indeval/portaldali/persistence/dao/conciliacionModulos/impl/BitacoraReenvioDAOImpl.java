package com.indeval.portaldali.persistence.dao.conciliacionModulos.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.conciliacionModulos.BitacoraReenvioDAO;
import com.indeval.portaldali.persistence.model.BitacoraEstadoReenvio;
import com.indeval.portaldali.persistence.model.BitacoraReenvio;

public class BitacoraReenvioDAOImpl extends BaseDaoHibernateImpl implements BitacoraReenvioDAO {

	@Override
	public void save(BitacoraReenvio bitacoraReenvio){
		super.save(bitacoraReenvio);
	}

	@Override
	public void save(BitacoraEstadoReenvio bitacoraEstadoReenvio) {
		super.save(bitacoraEstadoReenvio);
	}

	@Override
	public void save(List<BitacoraEstadoReenvio> lstBitacoraEstadoReenvio) {
		for(BitacoraEstadoReenvio bitacora : lstBitacoraEstadoReenvio) {
			this.save(bitacora);
		}
	}
	
	@Override
	public BitacoraEstadoReenvio getEstadoReenvioByPk(String folioOperacion) {
		BitacoraEstadoReenvio estadoreenvio = (BitacoraEstadoReenvio) super.getByPk(BitacoraEstadoReenvio.class, folioOperacion);
		getSession().evict(estadoreenvio);
		return estadoreenvio;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraEstadoReenvio> getEstadosReenvioByPk(List<String> folioOperacion) {
		String queryString = "Select b from BitacoraEstadoReenvio b WHERE folioOperacion in (:foliosOperacion)";
		List<BitacoraEstadoReenvio> estadosreenvio = (List<BitacoraEstadoReenvio>) this.getSession().createQuery(queryString)
		    	.setParameterList("foliosOperacion", folioOperacion)
		    	.list();
		for(BitacoraEstadoReenvio estadoreenvio:estadosreenvio) {
			getSession().evict(estadoreenvio);
		}
		return estadosreenvio;
	}

	@Override
	public void updateEstadoReenvio(String folioOperacion, boolean isProcesado) {
		String queryString = "UPDATE BitacoraEstadoReenvio SET procesado=:IsProcesado WHERE folioOperacion=:folioOperacion";
		this.getSession().createQuery(queryString)
	    	.setParameter("IsProcesado", isProcesado)
	    	.setParameter("folioOperacion", folioOperacion)
	    	.executeUpdate();
	}

	@Override
	public void updateEstadosReenvio(List<String> foliosOperacion, boolean isProcesado) {
		String queryString = "UPDATE BitacoraEstadoReenvio SET procesado=:IsProcesado WHERE folioOperacion in (:foliosOperacion)";
		this.getSession().createQuery(queryString)
    		.setParameter("IsProcesado", isProcesado)
	    	.setParameterList("foliosOperacion", foliosOperacion)
	    	.executeUpdate();
	}
	
}
