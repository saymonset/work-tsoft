/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.indeval.portaldali.constantes.OrdenamientoConsulta;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombrada;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombradaH;

/**
 * Utileria para la consulta de posicion.
 * 
 * @author Pablo Balderas
 */
public final class ConsultaPosicionNombradaUtil {

	/** Id de opcion todos en los combos */
	private static final String ID_TODOS = "-1"; 

	/**
	 * Metodo que crea el DetachedCriteria para la consulta de posicion nombrada.
	 * @param criteriosBusqueda Criterios para realizar la consulta.
	 * @param esHistorica Indica si es consulta historica.
	 * @param orden Indica el orden de la consulta.
	 * @return DetachedCriteria
	 */
	public static DetachedCriteria crearDetachedCriteriaPosicionNombrada(PosicionDTO criteriosBusqueda, boolean esHistorica, CriterioOrdenamientoDTO orden) {
		DetachedCriteria criteria = crearDetachedCriteria(esHistorica);
		agregarCondiciones(criteria, criteriosBusqueda, esHistorica);
		agregarOrdenamiento(criteria, orden, esHistorica);
		return criteria;
	}
	
	/**
	 * Metodo que crea el DetachedCriteria para obtener la proyeccion de la consulta de posicion nombrada.
	 * @param criteriosBusqueda Criterios para realizar la consulta.
	 * @param esHistorica Indica si es consulta historica.
	 * @return DetachedCriteria
	 */
	public static DetachedCriteria crearDetachedCriteriaProyeccionPosicionNombrada(PosicionDTO criteriosBusqueda, boolean esHistorica) {
		DetachedCriteria criteria = crearDetachedCriteria(esHistorica);
		agregarCondiciones(criteria, criteriosBusqueda, esHistorica);
		criteria.setProjection(Projections.rowCount());
		return criteria;
	}
	
	/**
	 * Metodo que crea el DetachedCriteria para obtener los totales de la posicion nombrada .
	 * @param criteriosBusqueda Criterios para realizar la consulta.
	 * @param esHistorica Indica si es consulta historica.
	 * @return DetachedCriteria
	 */
	public static DetachedCriteria crearDetachedCriteriaTotalesPosicionNombrada(PosicionDTO criteriosBusqueda, boolean esHistorica) {
		DetachedCriteria criteria = crearDetachedCriteria(esHistorica);
		agregarCondiciones(criteria, criteriosBusqueda, esHistorica);
		agregarProyeccion(criteria);
		return criteria;
	}	
	
	/**
	 * Crea el detachedCriteria dependiendo si es consulta intradia o historica.
	 * @param esHistorica Bandera que indica si es consulta historica.
	 * @return DetachedCriteria
	 */
	private static DetachedCriteria crearDetachedCriteria(boolean esHistorica) {
		DetachedCriteria criteria = null;
		if(esHistorica) {
			criteria = DetachedCriteria.forClass(VPosicionNombradaH.class);
		}
		else {
			criteria = DetachedCriteria.forClass(VPosicionNombrada.class);
		}
		return criteria;
	}
	
	/**
	 * Metodo que agrega las condiciones al DetachedCriteria para la consulta de poscion nombrada.
	 * @param criteria criterio donde se agregaran las condiciones
	 * @param criteriosBusqueda DTO con los parametros de la consulta
	 * @param esHistorica Indica si es consulta historica
	 * @param orden DTO con el ordenamiento
	 * @return DetachedCriteria
	 */
	private static void agregarCondiciones(DetachedCriteria criteria, PosicionDTO criteriosBusqueda, boolean esHistorica) {
		if(criteriosBusqueda != null) {
			if(esHistorica) {
				criteria.add(Restrictions.eq("fecha", criteriosBusqueda.getFechaFinal()));
			}
			//Naturaleza contable
			criteria.add(Restrictions.eq("naturalezaContable", criteriosBusqueda.getCuenta().getTipoTenencia().getTipoNaturaleza().getId()));
			//Naturaleza Proceso Liquidacion
			criteria.add(Restrictions.eq("naturalezaProcLiq", criteriosBusqueda.getCuenta().getTipoTenencia().getTipoCuenta().getId()));
			//Posicion no disponible
			if(criteriosBusqueda.isNoDisponible()) {
				criteria.add(Restrictions.gt("posicionNoDisponible", 0L));
			}
			//Boveda
			if (criteriosBusqueda.getBoveda() != null && criteriosBusqueda.getBoveda().getId() > 0) {
				criteria.add(Restrictions.eq("idBoveda", criteriosBusqueda.getBoveda().getId()));
			}
			//Emision
			if(criteriosBusqueda.getEmision() != null) {				
				if (criteriosBusqueda.getEmision().getTipoValor() != null) {
					if(StringUtils.isNotBlank(criteriosBusqueda.getEmision().getTipoValor().getClaveTipoValor())) {
						criteria.add(Restrictions.eq("tv", criteriosBusqueda.getEmision().getTipoValor().getClaveTipoValor().toUpperCase()));
					}
					//Mercado y papel
					if (criteriosBusqueda.getEmision().getTipoValor().getMercado() != null
							&& criteriosBusqueda.getEmision().getTipoValor().getMercado().getId() > 0) {
						if (DaliConstants.ID_MERCADO_DINERO != criteriosBusqueda.getEmision().getTipoValor().getMercado().getId()) {
							criteria.add(Restrictions.eq("idMercado", criteriosBusqueda.getEmision().getTipoValor().getMercado().getId()));
						}
						else {
							criteria.add(Restrictions.in(
								"idMercado", new Object[]{DaliConstants.ID_MERCADO_PAPEL_BANCARIO, DaliConstants.ID_MERCADO_PAPEL_GUBER}));
						}
					}
				}
				if (criteriosBusqueda.getEmision().getEmisora() != null &&
						StringUtils.isNotBlank(criteriosBusqueda.getEmision().getEmisora().getDescripcion()) &&
						!DaliConstants.DESCRIPCION_TODAS.equals(criteriosBusqueda.getEmision().getEmisora().getDescripcion())) {
					criteria.add(Restrictions.eq("emisora", criteriosBusqueda.getEmision().getEmisora().getDescripcion().toUpperCase()));
				}
				if (criteriosBusqueda.getEmision().getSerie() != null &&
						StringUtils.isNotBlank(criteriosBusqueda.getEmision().getSerie().getSerie()) &&
						!ID_TODOS.equals(criteriosBusqueda.getEmision().getSerie().getSerie())) {
					criteria.add(Restrictions.eq("serie", criteriosBusqueda.getEmision().getSerie().getSerie().toUpperCase()));
				}
				if (StringUtils.isNotBlank(criteriosBusqueda.getEmision().getIsin())) {
					criteria.add(Restrictions.eq("isin", criteriosBusqueda.getEmision().getIsin().toUpperCase()));
				}
			}
			// Institucion y cuenta
			if(criteriosBusqueda.getCuenta() != null) {
				if (criteriosBusqueda.getCuenta().getInstitucion().getId() > 0) {
					criteria.add(Restrictions.eq("idInstitucion", criteriosBusqueda.getCuenta().getInstitucion().getId()));
				}
				else if (criteriosBusqueda.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
					criteria.add(Restrictions.eq("claveTipoInstitucion", criteriosBusqueda.getCuenta().getInstitucion().getClaveTipoInstitucion()));
				}
				if (StringUtils.isNotBlank(criteriosBusqueda.getCuenta().getNumeroCuenta()) &&
						!ID_TODOS.equals(criteriosBusqueda.getCuenta().getNumeroCuenta())) {
//					criteria.add(Restrictions.eq("cuenta", criteriosBusqueda.getCuenta().getCuenta()));
					criteria.add(Restrictions.eq("idFolioCuenta", criteriosBusqueda.getCuenta().getNumeroCuenta()));
				}
				if (criteriosBusqueda.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
					criteria.add(Restrictions.eq("idTipoCuenta", criteriosBusqueda.getCuenta().getTipoTenencia().getIdTipoCuenta()));
				}
				if (criteriosBusqueda.getCuenta().getTipoTenencia().getTipoCustodia() != null &&
						!ID_TODOS.equals(criteriosBusqueda.getCuenta().getTipoTenencia().getTipoCustodia())) {
					criteria.add(Restrictions.eq("tipoCustodia", criteriosBusqueda.getCuenta().getTipoTenencia().getTipoCustodia()));
				}
			}
		}
	}
	
	/**
	 * Agrega las proyecciones necesarias para obtener los totales de la consulta de posicion nombrada.
	 * @param criteria DetachedCriteria
	 */
	private static void agregarProyeccion(DetachedCriteria criteria) {
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.sum("posicion"));
		projList.add(Projections.sum("posicionDisponible"));
		projList.add(Projections.sum("posicionNoDisponible"));
		projList.add(Projections.sum("valuacion"));		
		criteria.setProjection(projList);
	}
	
	/**
	 * Agrega el ordenamiento al DetachedCriteria.
	 * @param criteria DetachedCriteria
	 * @param orden Objeto con el ordenamiento a agregar
	 */
	private static void agregarOrdenamiento(DetachedCriteria criteria, CriterioOrdenamientoDTO orden, boolean esHistorica) {
		if (orden != null && orden.getColumna() != null) {
			if (OrdenamientoConsulta.SORT_CUENTA.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("ordenCuenta"));
				else
					criteria.addOrder(Order.desc("ordenCuenta"));
			}
			else if (OrdenamientoConsulta.SORT_EMISORA.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("emisora"));
				else
					criteria.addOrder(Order.desc("emisora"));
			}
			else if (OrdenamientoConsulta.SORT_BOVEDA.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("idBoveda"));
				else
					criteria.addOrder(Order.desc("idBoveda"));
			}
			else if (OrdenamientoConsulta.SORT_TV.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("tv"));
				else
					criteria.addOrder(Order.desc("tv"));
			}
			else if (OrdenamientoConsulta.SORT_POSICION.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("posicion"));
				else
					criteria.addOrder(Order.desc("posicion"));
			}
			else if(OrdenamientoConsulta.SORT_SERIE.getOrdenamiento().equals(orden.getColumna())) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("serie"));
				else
					criteria.addOrder(Order.desc("serie"));
			}
		}
		else {
			if(esHistorica) {
				if(orden.isAscendente())
					criteria.addOrder(Order.asc("ordenDefault"));
				else
					criteria.addOrder(Order.desc("ordenDefault"));
			}
			else {
				criteria.addOrder(Order.asc("ordenDefault"));
				criteria.addOrder(Order.asc("serie"));
				criteria.addOrder(Order.asc("cupon"));
			}
		}
		if(esHistorica) {
			criteria.addOrder(Order.asc("idPosicion"));
		}
	}
}
