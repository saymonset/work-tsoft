package com.indeval.dali.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.indeval.dali.dao.InstitucionDao;


public class InstitucionDaoImpl extends JdbcDaoSupport implements InstitucionDao{
	@SuppressWarnings("deprecation")
	public int getIdInstitucionByCve(String cveInstitucion) throws Exception{
		if(cveInstitucion.length()!=5)
			throw new Exception("Clave de institucion incorrecta");
		String id_tipo_institucion=cveInstitucion.substring(0, 2);
		String folio_institucion=cveInstitucion.substring(2);
		String sql="SELECT ID_INSTITUCION FROM ADMIN_SEGU.C_INSTITUCION WHERE ID_TIPO_INSTITUCION="+id_tipo_institucion+" AND FOLIO_INSTITUCION='"+folio_institucion+"'";
		return this.getJdbcTemplate().queryForInt(sql);
		
	}
}
