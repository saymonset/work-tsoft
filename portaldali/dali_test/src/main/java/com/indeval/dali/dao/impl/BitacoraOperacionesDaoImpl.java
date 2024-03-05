package com.indeval.dali.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.indeval.dali.dao.BitacoraOperacionesDao;


public class BitacoraOperacionesDaoImpl  extends JdbcDaoSupport implements BitacoraOperacionesDao{

	public String getEstatusRegistro(int folio_control){
		final StringBuffer query=new StringBuffer();
		query.append("SELECT estatus_registro FROM ADMIN_SEGU.T_BITACORA_OPERACIONES where folio_control="+folio_control); 
		return this.getJdbcTemplate().queryForObject(query.toString(), String.class);
	}
	
	
}
