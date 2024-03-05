package com.indeval.portaldali.persistence.dao.util.impl;

import java.util.Iterator;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.util.CuponDao;
import com.indeval.portaldali.persistence.model.Cupon;

public class CuponDaoImpl extends HibernateDaoSupport implements CuponDao {
	
	@SuppressWarnings("unchecked")
	public Cupon consultaCuponValidoParaEmision(EmisionVO emisionVO) {
		
		Cupon cupon = null;
		Iterator res = getHibernateTemplate().find(
				"FROM " + Cupon.class.getName() + " c WHERE  "
						+ " c.emision.serie = '" + emisionVO.getSerie() + "' and c.emision.instrumento.claveTipoValor = '" + emisionVO.getIdTipoValor() +
								"' and c.emision.emisora.clavePizarra = '" + emisionVO.getEmisora() + "' and c.idEstadoCupon = 1 and " +
										"c.claveCupon = '" + emisionVO.getCupon() + "' ").iterator();
		
		if (res.hasNext()) {
			cupon = (Cupon) res.next();
		}
		return cupon;
		
		
		/*
		Long idCupon = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ID_CUPON FROM C_CUPON C ");
		query.append("JOIN C_EMISION E ON E.ID_EMISION = C.ID_EMISION AND E.SERIE = '" + emisionVO.getSerie() + "' ");
		query.append("JOIN C_INSTRUMENTO I ON I.ID_INSTRUMENTO = E.ID_INSTRUMENTO AND I.CLAVE_TIPO_VALOR = '" + emisionVO.getIdTipoValor() + "' ");
		query.append("JOIN C_EMISORA EM ON EM.ID_EMISORA = E.ID_EMISORA AND EM.CLAVE_PIZARRA = '" + emisionVO.getEmisora() + "' ");
		query.append("WHERE C.ID_ESTADO_CUPON = 1 AND C.CLAVE_CUPON = " + emisionVO.getCupon());
		
		return idCupon;
		*/
	}

}
