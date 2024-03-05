/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.MercadoDaliDAO;
import com.indeval.portaldali.persistence.model.Mercado;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con el
 * catálogo de mercados.
 * 
 * @author José Antonio Huizar Moreno
 *
 */
public class MercadoDaliDAOImpl extends HibernateDaoSupport implements MercadoDaliDAO {
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.MercadoDAO#buscarMercados()
	 */
	@SuppressWarnings("unchecked")
	public List<MercadoDTO> buscarMercados() {
		
		List<Mercado> mercados =  this.getHibernateTemplate().find("FROM " + Mercado.class.getName() + " m  ORDER BY m.descripcion");
		
		Iterator<Mercado> mercadosBO  = mercados.iterator();
		
		List<MercadoDTO> resultado = new ArrayList<MercadoDTO>();
				
		while(mercadosBO.hasNext()){
			
			resultado.add(DTOAssembler.crearMercadoDTO((Mercado)mercadosBO.next()));
		}
		return resultado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.MercadoDAO#buscarMercadoPorId(long)
	 */
	public MercadoDTO buscarMercadoPorId(long idMercado) {
		return DTOAssembler.crearMercadoDTO((Mercado)getHibernateTemplate().get(Mercado.class, new BigInteger(String.valueOf(idMercado))));
	} 
	
}
