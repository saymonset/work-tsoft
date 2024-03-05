package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;

public class InstruccionOperacionValDAOImpl extends HibernateDaoSupport implements InstruccionOperacionValDAO {

	@Override
	public InstruccionOperacionVal findById(long id) {
		return (InstruccionOperacionVal)getSession().get(InstruccionOperacionVal.class, BigInteger.valueOf(id) );
	}

	@Override
	public void actualizaPlazo(long idInstruccionOperacionVal, int plazoReporto) {
		
		Query query = getSession().getNamedQuery("InstruccionOperacionVal.actualizaPlazoReporto");
		
		query.setParameter("plazoReporto", BigInteger.valueOf(plazoReporto));
		query.setParameter("idInstruccionOperacionVal", BigInteger.valueOf(idInstruccionOperacionVal) );
		
		
		query.executeUpdate();
	}
	
	
}
