package com.indeval.portaldali.persistence.dao.ingresos.impl;

import java.util.List;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.ingresos.IngresosDao;
import com.indeval.portaldali.persistence.model.ConsultaIngresos;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.InstitucionLibreConsultas;
import com.indeval.portaldali.persistence.model.Sistema;

public class IngresosDaoImpl extends BaseDaoHibernateImpl implements
		IngresosDao {

	@SuppressWarnings("unchecked")
	public Sistema getSistemaPorNombre(String nombre) {
		StringBuffer query = new StringBuffer();
		query.append(" FROM " + Sistema.class.getName() + " s ");
		query.append(" WHERE s.nombre = '" + nombre + "' ");
		
		List<Sistema> listado = getHibernateTemplate().find(query.toString());
		
		if ( listado != null && listado.size() == 1 ) {
			return listado.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Institucion getInstitucionPorIdFolio(String idFolio) {
		StringBuffer query = new StringBuffer();
		query.append(" FROM " + Institucion.class.getName() + " i ");
		query.append(" 	JOIN FETCH i.tipoInstitucion ");
		query.append(" WHERE i.tipoInstitucion.claveTipoInstitucion || i.folioInstitucion = '" + idFolio + "' ");
		query.append(" 	AND i.idEstadoInstitucion = 1 ");
		
		List<Institucion> listado = getHibernateTemplate().find(query.toString());
		
		if ( listado != null && listado.size() == 1 ) {
			return listado.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ConsultaIngresos getConsultaIngresosPorNombre(String nombre) {
		StringBuffer query = new StringBuffer();
		query.append(" FROM " + ConsultaIngresos.class.getName() + " c ");
		query.append(" 	JOIN FETCH c.sistema ");
		query.append(" WHERE c.nombre = '" + nombre + "' ");
		
		List<ConsultaIngresos> listado = getHibernateTemplate().find(query.toString());
		
		if ( listado != null && listado.size() == 1 ) {
			return listado.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean debeDejarBitacoraInstitucion(String idFolio) {
		StringBuffer query = new StringBuffer();
		query.append(" FROM " + InstitucionLibreConsultas.class.getName() + " ilc ");
		query.append(" 	JOIN FETCH ilc.institucion i ");
		query.append(" 	JOIN FETCH i.tipoInstitucion ti ");
		query.append(" WHERE ti.claveTipoInstitucion || i.folioInstitucion = '" + idFolio + "' ");
		
		List<ConsultaIngresos> listado = getHibernateTemplate().find(query.toString());
		
		if ( listado != null && listado.size() > 0 ) {
			return false;
		}
		return true;
	}

}
