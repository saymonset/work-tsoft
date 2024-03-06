/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.dao.common.EmisoraDaliDAO;
import com.indeval.portaldali.persistence.model.Emisora;


/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con el
 * catálogo de emisoras.
 * 
 * @author Emigdio Hernández
 */
public class EmisoraDaliDAOImpl extends HibernateDaoSupport implements EmisoraDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisoraDAO#buscarEmisoras()
	 */
	@SuppressWarnings("unchecked")
	public List<EmisoraDTO> buscarEmisoras() {

		List<Emisora> emisoras = this.getHibernateTemplate().find(
				"FROM " + Emisora.class.getName()
						+ " emisora ORDER BY emisora.descripcion");
		List<EmisoraDTO> resultado = new ArrayList<EmisoraDTO>();
		for (Emisora emisora : emisoras) {
			resultado.add(generarDTOEquivalente(emisora));
		}
		return resultado;
	}

	/**
	 * Genera un DTO equivalente del BO proporcionado como parámetro.
	 * 
	 * @param emisoraBO
	 *            el BO en base al cual se generar el DTO.
	 * @return un objeto {@link EmisoraDTO} el cual es equivalente al BO
	 *         proporcionado.
	 */
	private EmisoraDTO generarDTOEquivalente(Emisora emisoraBO) {

		EmisoraDTO emisoraDTO = new EmisoraDTO();
		emisoraDTO.setId(emisoraBO.getIdEmisora().longValue());
		emisoraDTO.setDescripcion(emisoraBO.getDescripcion());
		return emisoraDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisoraDAO#buscarEmisoraPorId(long)
	 */
	@SuppressWarnings("unchecked")
	public EmisoraDTO buscarEmisoraPorId(long idEmisora) {

		Iterator res = this.getHibernateTemplate().find(
				"FROM " + Emisora.class.getName()
				+ " emisora WHERE emisora.idEmisora = ?", new BigInteger(String.valueOf(idEmisora))).iterator();
		if(res.hasNext()){
			return DTOAssembler.crearEmisoraDTO((Emisora)res.next() );
		}
		return null;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisoraDAO#buscarEmisorasPorPrefijo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(String prefijo) {
		
		if(prefijo.contains("%%")){
		prefijo=prefijo.replace("%", "*");}
		
		this.getHibernateTemplate().setMaxResults(DaliConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);
		List<Emisora> emisoras = this
				.getHibernateTemplate()
				.find(
						"FROM "
								+ Emisora.class.getName()
								+ " emisora WHERE UPPER(emisora.descripcion) LIKE UPPER(?) ORDER BY emisora.descripcion",
						prefijo + "%");
		List<EmisoraDTO> resultado = new ArrayList<EmisoraDTO>();
		for (Emisora emisora : emisoras) {
			resultado.add(generarDTOEquivalente(emisora));
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisoraDAO#buscarEmisoraPorDescripcion(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public EmisoraDTO buscarEmisoraPorDescripcion(String descripcion) {

		EmisoraDTO resultado = null;

		List<Emisora> emisoras = this.getHibernateTemplate()
				.find(
						"FROM " + Emisora.class.getName()
								+ " emisora WHERE emisora.descripcion = ?",
						descripcion != null ? descripcion.toUpperCase() : descripcion);
		
		if(emisoras != null && emisoras.size() > 0) {
			resultado = generarDTOEquivalente(emisoras.get(0));
		}

		return resultado;
	}
}
