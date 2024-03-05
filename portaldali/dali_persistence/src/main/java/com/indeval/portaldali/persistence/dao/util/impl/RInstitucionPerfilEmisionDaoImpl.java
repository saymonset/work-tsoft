/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2018 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util.impl;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.dao.util.RInstitucionPerfilEmisionDao;

/**
 * Implementacion de la interfaz RInstitucionPerfilEmisionDao
 * 
 * @author Pablo Balderas
 */
public class RInstitucionPerfilEmisionDaoImpl extends HibernateDaoSupport implements RInstitucionPerfilEmisionDao {

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.util.RInstitucionPerfilEmisionDao#validarRolRepComunPorIdInstitucionIdEmision(java.lang.Long, java.lang.Long)
	 */
	public boolean validarRolAgenteColocadorPorIdInstitucionIdEmision(final Long idInstitucion, final Long idEmision) {
		boolean resultadoValidacion = false;
		
		Integer resultados = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
    		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
    	        StringBuffer strQuery = new StringBuffer();
    	        strQuery.append(" select ");
    	        strQuery.append(" COUNT(ripe.ID_INSTITUCION_PERFIL) as RESULTADO ");
    	        strQuery.append(" from ");
    	        strQuery.append(" R_INSTITUCION_PERFIL_EMISIONES ripe ");
    	        strQuery.append(" inner join C_INSTITUCION_PERFILES cip on ripe.ID_INSTITUCION_PERFIL = cip.ID_INSTITUCION_PERFIL and cip.ID_PERFIL = 6 ");
				strQuery.append(" where ");
				strQuery.append(" ripe.ID_EMISION = " + idEmision);
				strQuery.append(" and cip.ID_INSTITUCION = " + idInstitucion);
    	        SQLQuery query = session.createSQLQuery(strQuery.toString());
    	        query.addScalar("RESULTADO", Hibernate.INTEGER);
    			return query.uniqueResult();
    		}
    	});
		
		resultadoValidacion = resultados > 0;
		return resultadoValidacion;
	}

}
