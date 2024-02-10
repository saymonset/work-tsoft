package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.SaldoNombrada;
import com.indeval.portalinternacional.middleware.servicios.modelo.RBovedaValEf;
import com.indeval.portalinternacional.persistence.dao.NombradaSaldoDao;

@SuppressWarnings({"unchecked"})
public class NombradaSaldoDaoImpl extends BaseDaoHibernateImpl implements NombradaSaldoDao {

	private static final Logger log = LoggerFactory.getLogger(NombradaSaldoDaoImpl.class);
	
	/** Definicion de la variable para queries JDBC */
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public RBovedaValEf getEfectivoByIdValores(final Long idValores) {

	   RBovedaValEf bovedaEfectivo = null;
	
       DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RBovedaValEf.class);
       detachedCriteria.add(Restrictions.eq("idValores", idValores));
      
       List<RBovedaValEf> bovedasEfectivo = this.getHibernateTemplate().findByCriteria(detachedCriteria);
       bovedaEfectivo = bovedasEfectivo.get(0);
       
   	   return bovedaEfectivo;
		
	}

	@Override
	public CuentaNombrada getCuentaNombradaById(final Long idCuentaNombrada) {
		
		CuentaNombrada cuentaNombrada = null;
		
	    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CuentaNombrada.class);
	    detachedCriteria.add(Restrictions.eq("idCuentaNombrada", idCuentaNombrada));
	      
	    List<CuentaNombrada> cuentasNombradas = this.getHibernateTemplate().findByCriteria(detachedCriteria);
	    cuentaNombrada = cuentasNombradas.get(0);
	       
	   	return cuentaNombrada;
		
	}

	@Override
	public CuentaNombrada getCuentaNombradaByInstitucionAndCuenta(final Long idInstitucion, final String cuenta) {
	
		final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<CuentaNombrada> cuentasNombradas = null;
        CuentaNombrada cuentaNombrada = null;

        sb.append(" SELECT CCN ");
        sb.append(" FROM " + CuentaNombrada.class.getName() + " CCN ");
        sb.append("			JOIN FETCH CCN.institucion I ");
        sb.append(" WHERE CCN.cuenta = ? ");
        paramsSQL.add(cuenta);
        sb.append(" AND I.idInstitucion = " + idInstitucion);

        cuentasNombradas = this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        if (cuentasNombradas != null && !cuentasNombradas.isEmpty()) {
        	cuentaNombrada = cuentasNombradas.get(0);
        }

        return cuentaNombrada;
		
	}

	@Override
	public List<SaldoNombrada> getSaldoNombradaByCuentaNombradaAndBovedaAndDivisa(final Long idCuentaNombrada,
			final Long idBoveda, final Long idDivisa) {
		
		final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<SaldoNombrada> saldosNombradas = null;

        sb.append(" SELECT TSN ");
        sb.append(" FROM " + SaldoNombrada.class.getName() + " TSN ");
        sb.append("			JOIN FETCH TSN.cuentaNombrada CCN ");
        sb.append("			JOIN FETCH TSN.boveda CB ");
        sb.append("			JOIN FETCH TSN.divisa CD ");
        sb.append(" WHERE CCN.idCuentaNombrada = ? ");
        paramsSQL.add(idCuentaNombrada);
        sb.append(" AND CB.idBoveda = " + idBoveda);
        sb.append(" AND CD.idDivisa = " + idDivisa);

        saldosNombradas = this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        return saldosNombradas;
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
