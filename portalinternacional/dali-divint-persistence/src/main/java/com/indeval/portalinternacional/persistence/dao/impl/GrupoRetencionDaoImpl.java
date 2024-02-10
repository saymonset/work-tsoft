package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.GrupoRetencion;
import com.indeval.portalinternacional.persistence.dao.GrupoRetencionDao;

public class GrupoRetencionDaoImpl  extends BaseDaoHibernateImpl implements GrupoRetencionDao{

	public List<GrupoRetencion> getGruposRetencionByIdDerecho(Long idDerechoCapital) {	
		final StringBuilder sb = new StringBuilder();
		List<Object> paramsSQL = new ArrayList<Object>();		
		sb.append("SELECT	GR	");
		sb.append("FROM		"+ GrupoRetencion.class.getName() + " GR ");
		sb.append("WHERE	GR.derecho.idDerechoCapital = ? ");
		paramsSQL.add(idDerechoCapital);
				
		return this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[]{}));		
	}

}
