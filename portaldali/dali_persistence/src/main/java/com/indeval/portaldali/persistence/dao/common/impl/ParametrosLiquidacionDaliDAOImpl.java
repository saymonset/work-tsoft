/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 5, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.ParametrosLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.ParametrosLiquidacionDaliDAO;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.ParametrosLiquidacion;
import com.indeval.portaldali.persistence.model.TipoBoveda;

/**
 * 
 * 
 * 
 * @author 
 * 
 */
public class ParametrosLiquidacionDaliDAOImpl extends HibernateDaoSupport implements ParametrosLiquidacionDaliDAO {

	
	public ParametrosLiquidacionDTO buscarParametroByID(final BigInteger id) {
		return (ParametrosLiquidacionDTO) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Criteria criteria = session.createCriteria(ParametrosLiquidacion.class);			
				criteria.add(Expression.eq("idConfiguracion", id));
				
				ParametrosLiquidacionDTO param=DTOAssembler.crearParametrosLiquidacion((ParametrosLiquidacion)criteria.uniqueResult());				
				return param;				
				}						
		});
	}
}
