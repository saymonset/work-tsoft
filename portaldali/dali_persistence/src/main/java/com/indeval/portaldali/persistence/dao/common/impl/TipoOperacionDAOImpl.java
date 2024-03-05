/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoOperacionDAO;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.model.TipoOperacion;
import com.indeval.portaldali.persistence.util.Constantes;

/**
 * Implementación del DAO de consulta del catálogo de Tipos de Operación.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class TipoOperacionDAOImpl extends HibernateDaoSupport implements
		TipoOperacionDAO {	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.TipoOperacionDAO#buscarTipoOperaciconPorId(long)
	 */
	public TipoOperacionDTO buscarTipoOperaciconPorId(long id) {
		return DTOAssembler.crearTipoOperacion((TipoOperacion)getHibernateTemplate().get(TipoOperacion.class,
				new BigInteger(String.valueOf(id))));
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.TipoOperacionDAO#buscatTipoOperacion()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoOperacionDTO> buscatTiposOperacion(String tiposCustodia) {
	
		Iterator resultados = getHibernateTemplate().find("FROM  "+TipoOperacion.class.getName()+
				" t WHERE t.maneja in ("+tiposCustodia+
				") AND t.claveTipoOperacion NOT IN ( '"
				+Constantes.BLOQUEO_TITULOS+"', '"+Constantes.DESBLOQUEO_TITULOS+"', '"+Constantes.ENTREGA_CONTRAENTREGA+"', '"
				+Constantes.BLOQUEO_EFECTIVO+"', '"+Constantes.DESBLOQUEO_EFECTIVO+"')ORDER BY t.claveTipoOperacion ").iterator();
		List<TipoOperacionDTO> resDTOs = new ArrayList<TipoOperacionDTO>();
		while(resultados.hasNext()){
			resDTOs.add(DTOAssembler.crearTipoOperacion((TipoOperacion)resultados.next()));
		}
		return resDTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoOperacionDTO> buscarTiposDeOperacion() {
		Iterator resultados = getHibernateTemplate().find("FROM " + TipoOperacion.class.getName() + " t ORDER BY t.claveTipoOperacion").iterator();
		List<TipoOperacionDTO> resultadosDTO = new ArrayList<TipoOperacionDTO>();

		while (resultados.hasNext()) {
			resultadosDTO.add(DTOAssembler.crearTipoOperacion((TipoOperacion) resultados.next()));
		}
		return resultadosDTO;
	}

}
