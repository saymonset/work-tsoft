/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.cfi.impl;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.modelo.to.cfi.DetalleCfiTO;
import com.indeval.portaldali.persistence.dao.cfi.CfiDao;

/**
 * Implementacion de la interfaz CfiDao
 * 
 * @author Pablo Balderas
 */
public class CfiDaoImpl extends BaseDaoHibernateImpl implements CfiDao {

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.cfi.CfiDao#findDetalleCfi(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public DetalleCfiTO findDetalleCfi(final String categoria, final String grupo, final String atributoUno, final String atributoDos,
			final String atributoTres, final String atributoCuatro) {
		
		Object[] resultados = (Object[]) this.getHibernateTemplate().execute(new HibernateCallback() {
    		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
    	        StringBuffer strQuery = new StringBuffer();
    	        strQuery.append(" select ");
    	        strQuery.append(" categoria.CLAVE as categoria, categoria.DESCRIPCION_ESP as descripcionCategoria, ");
    	        strQuery.append(" grupo.CLAVE as grupo, grupo.DESCRIPCION_ESP as descripcionGrupo, ");
    	        strQuery.append(" atributoUno.CLAVE as atributoUno, atributoUno.DESCRIPCION_ESP as descripcionAtributoUno, ");
    	        strQuery.append(" atributoDos.CLAVE as atributoDos, atributoDos.DESCRIPCION_ESP as descripcionAtributoDos, ");
    	        strQuery.append(" atributoTres.CLAVE as atributoTres, atributoTres.DESCRIPCION_ESP as descripcionAtributoTres, ");
    	        strQuery.append(" atributoCuatro.CLAVE as atributoCuatro, atributoCuatro.DESCRIPCION_ESP as descripcionAtributoCuatro ");
    	        strQuery.append(" from ");
    	        strQuery.append(" ADMIN_SEGU.C_CATEGORIA_CFI categoria ");
    	        strQuery.append(" inner join ADMIN_SEGU.C_GRUPO_CFI grupo on categoria.ID_CATEGORIA = grupo.ID_CATEGORIA ");
    	        strQuery.append(" left join ADMIN_SEGU.C_ATRIBUTO1_CFI atributoUno on categoria.ID_CATEGORIA = atributoUno.ID_CATEGORIA and grupo.ID_GRUPO = atributoUno.ID_GRUPO and atributoUno.CLAVE = '" + atributoUno + "' ");
        		strQuery.append(" left join ADMIN_SEGU.C_ATRIBUTO2_CFI atributoDos on categoria.ID_CATEGORIA = atributoDos.ID_CATEGORIA and grupo.ID_GRUPO = atributoDos.ID_GRUPO and atributoDos.CLAVE = '" + atributoDos + "' ");
				strQuery.append(" left join ADMIN_SEGU.C_ATRIBUTO3_CFI atributoTres on categoria.ID_CATEGORIA = atributoTres.ID_CATEGORIA and grupo.ID_GRUPO = atributoTres.ID_GRUPO and atributoTres.CLAVE = '" + atributoTres + "' ");
				strQuery.append(" left join ADMIN_SEGU.C_ATRIBUTO4_CFI atributoCuatro on categoria.ID_CATEGORIA = atributoCuatro.ID_CATEGORIA and grupo.ID_GRUPO = atributoCuatro.ID_GRUPO and atributoCuatro.CLAVE = '" + atributoCuatro + "' ");
				strQuery.append(" where ");
				strQuery.append(" categoria.CLAVE = '"+ categoria +"' ");
				strQuery.append(" and grupo.CLAVE = '" + grupo + "' ");
    	        SQLQuery query = session.createSQLQuery(strQuery.toString());
    	        query.addScalar("categoria", Hibernate.STRING);
    	        query.addScalar("descripcionCategoria", Hibernate.STRING);
    	        query.addScalar("grupo", Hibernate.STRING);
    	        query.addScalar("descripcionGrupo", Hibernate.STRING);
    	        query.addScalar("atributoUno", Hibernate.STRING);
    	        query.addScalar("descripcionAtributoUno", Hibernate.STRING);
    	        query.addScalar("atributoDos", Hibernate.STRING);
    	        query.addScalar("descripcionAtributoDos", Hibernate.STRING);
    	        query.addScalar("atributoTres", Hibernate.STRING);
    	        query.addScalar("descripcionAtributoTres", Hibernate.STRING);
    	        query.addScalar("atributoCuatro", Hibernate.STRING);
    	        query.addScalar("descripcionAtributoCuatro", Hibernate.STRING);
    			return query.uniqueResult();
    		}
    	});
		
		DetalleCfiTO detalleCfiTO = null;
		if(resultados != null) {
			detalleCfiTO = new DetalleCfiTO();
			detalleCfiTO.setCategoria((String) resultados[0]);
			detalleCfiTO.setDescripcionCategoria((String) resultados[1]);
			detalleCfiTO.setGrupo((String) resultados[2]);
			detalleCfiTO.setDescripcionGrupo((String) resultados[3]);
			detalleCfiTO.setAtributoUno((String) resultados[4]);
			detalleCfiTO.setDescripcionAtributoUno((String) resultados[5]);
			detalleCfiTO.setAtributoDos((String) resultados[6]);
			detalleCfiTO.setDescripcionAtributoDos((String) resultados[7]);
			detalleCfiTO.setAtributoTres((String) resultados[8]);
			detalleCfiTO.setDescripcionAtributoTres((String) resultados[9]);
			detalleCfiTO.setAtributoCuatro((String) resultados[10]);
			detalleCfiTO.setDescripcionAtributoCuatro((String) resultados[11]);
		}
		return detalleCfiTO;
	}

}
