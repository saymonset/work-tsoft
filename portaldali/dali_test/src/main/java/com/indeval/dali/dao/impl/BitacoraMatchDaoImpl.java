package com.indeval.dali.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.indeval.dali.dao.BitacoraMatchDao;


public class BitacoraMatchDaoImpl  extends JdbcDaoSupport implements BitacoraMatchDao{

	public Long getIdBitacoraMatch(int folio_instruccion){
		final StringBuffer query=new StringBuffer();
		query.append("SELECT ID_BITACORA_MATCH FROM ADMIN_SEGU.T_REGISTRO_INSTRUCCIONES_MATCH where folio_instruccion="+folio_instruccion); 
		return this.getJdbcTemplate().queryForObject(query.toString(), Long.class);
	}
	
	
}
