package com.indeval.dali.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.indeval.dali.constans.TipoCuenta;
import com.indeval.dali.dao.CuentaNombradaDao;


public class CuentaNombradaDaoImpl  extends JdbcDaoSupport implements CuentaNombradaDao{

	public List<String> findCuentas(TipoCuenta tipoCuenta,Integer idInstitucion){
		final StringBuffer query=new StringBuffer();
		
		query.append(" SELECT CUENTA FROM ADMIN_SEGU.C_CUENTA_NOMBRADA CN"); 
		query.append(" INNER JOIN ADMIN_SEGU.C_INSTITUCION IC ON CN.ID_INSTITUCION = IC.ID_INSTITUCION");
		query.append(" INNER JOIN ADMIN_SEGU.C_TIPO_CUENTA TCN ON TCN.ID_TIPO_CUENTA = CN.ID_TIPO_CUENTA");
		query.append(" WHERE");
		query.append(" IC.ID_INSTITUCION = "+idInstitucion);
		query.append(" AND TCN.ID_TIPO_CUENTA = "+tipoCuenta.getId_tipo_cuenta());
		query.append(" AND CN.ID_CUENTA_ESTADO = 1");
		
		
		return this.getJdbcTemplate().queryForList(query.toString(),String.class);
	}
	
}
