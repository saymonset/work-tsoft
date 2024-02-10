// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.ibm.icu.math.BigDecimal;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.persistence.dao.ConsultaSaldoCustodiosDao;
import com.indeval.portalinternacional.persistence.dao.MovimientoEfectivoInternacionalDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class consultaSaldoCustodiosDaoImpl extends BaseDaoHibernateImpl implements ConsultaSaldoCustodiosDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(consultaSaldoCustodiosDaoImpl.class);

	@Override
	public PaginaVO getMovimientosEfectivoInternacional(CriteriosConsultaMovEfeDivExtVO criteriosConsulta, PaginaVO paginaVO) {
		return null;
	}

	@Override
	public Long getFolioControl() {
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<SaldoCustodio> consultaGeneral(final Divisa div, final Boveda boveda){

		List<SaldoCustodio> retorno= null;
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM SaldoCustodio sc");
		if (boveda.getDescripcion() != "TODAS"){
			sb.append(" where sc.divisa = :div and sc.boveda = :boveda");
		}else{
			sb.append(" where sc.divisa = :div");
		}
		try{
			retorno = (List<SaldoCustodio>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					query.setString("div",div.getDescripcion());

					if (boveda.getDescripcion()!="TODAS") {
						query.setString("boveda", boveda.getDescripcion());
					}
					return query.list();
				}
			});
		}catch (Exception e){
			LOG.info(e.toString());
		}
		return retorno;
	}

	public List<SaldoPorCuenta> consultaPorCuenta(Divisa div, Boveda boveda){
		List<SaldoPorCuenta> retorno= null;
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM SaldoPorCuenta spc");
		sb.append(" where sc.divisa = :div and sc.boveda = :boveda");
		try{
			retorno = (List<SaldoPorCuenta>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					return query.list();
				}
			});
		}catch (Exception e){
			LOG.info(e.toString());
		}
		return retorno;
	}


	
}
