/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSocInvDAO;
import com.indeval.portaldali.persistence.model.InstitucionPerfilEmision;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.util.Constantes;

/**
 * Implementación del servicio para el Estado de Cuenta
 * de las Sociedades de Inversión.
 * 
 * @author Rafael Ibarra Zendejas
 * 
 */
public class EstadoCuentaSocInvDAOImpl extends HibernateDaoSupport implements EstadoCuentaSocInvDAO, Constantes {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(EstadoCuentaSocInvDAOImpl.class);

	private boolean validaAgente(AgenteVO agente) {
		if( agente != null ) {
			String id = agente.getId();
			String folio = agente.getFolio();
			if( StringUtils.isNotBlank(id) &&
					id.length() == 2 &&
					StringUtils.isNumeric(id) &&
					StringUtils.isNotBlank(folio) &&
					folio.length() == 3 &&
					StringUtils.isNumeric(folio)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List getListaEmisiones(final AgenteVO agente, final EmisionVO emision) {
		if( validaAgente(agente) ) {
			List retorno = (List)getHibernateTemplate().execute(new HibernateCallback() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer sb = new StringBuffer("");
					List<Object> params = new ArrayList<Object>();
					List<Type> types = new ArrayList<Type>();
					
					sb.append("SELECT DISTINCT pn.cupon.emision.emisora.clavePizarra ");
					sb.append("FROM " + PosicionNombrada.class.getName() +" pn ");
					sb.append("WHERE pn.cupon.estadoCupon.idEstatusCupon = ? ");
					params.add(VIGENTE);
					types.add(new BigIntegerType());
					sb.append("		AND pn.posicionDisponible > 0 ");
					sb.append("		AND pn.cupon.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("		AND pn.cuentaNombrada.tipoCuenta.naturalezaContable = 'P' ");
					
					sb.append("		AND pn.cupon.emision.emisora.idEmisora IN ");
					sb.append(" 		(");
					sb.append("				SELECT DISTINCT(ipe.emision.emisora.idEmisora) ");
					sb.append("				FROM " + InstitucionPerfilEmision.class.getName() + " ipe ");
					sb.append("				WHERE ipe.institucionPerfil.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
					sb.append("					AND ipe.institucionPerfil.institucion.folioInstitucion = ? ");
					sb.append("					AND ipe.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("					AND ipe.institucionPerfil.perfil = 2 ");
					sb.append("			 )");
					params.add( agente.getId() );
					types.add( new StringType() );
					params.add( agente.getFolio() );
					types.add( new StringType() );
					
					if( emision != null ) {
						if( StringUtils.isNotBlank(emision.getIdTipoValor()) ) {
							String tv = emision.getIdTipoValor() + "%";
							tv = tv.replaceAll("\\*", "%");
							sb.append("		AND pn.cupon.emision.instrumento.claveTipoValor LIKE ? ");
							params.add( tv );
							types.add( new StringType() );
						}
						
						if( StringUtils.isNotBlank(emision.getEmisora()) ) {
							String emisora = emision.getEmisora() + "%";
							emisora = emisora.replaceAll("\\*", "%");
							sb.append("		AND pn.cupon.emision.emisora.clavePizarra LIKE ? ");
							params.add( emisora );
							types.add( new StringType() );
						}
					}
					
					sb.append("ORDER BY pn.cupon.emision.emisora.clavePizarra "); 
					
					Query query = session.createQuery(sb.toString());
                	
                    return  query.setParameters(params.toArray(), types.toArray(new Type[]{})).list();
				}
				
			});
			return retorno;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List getListaEmisionesRazonSocial(final AgenteVO agente, final EmisionVO emision) {
		if( validaAgente(agente) ) {
			List retorno = (List)getHibernateTemplate().execute(new HibernateCallback() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer sb = new StringBuffer("");
					List<Object> params = new ArrayList<Object>();
					List<Type> types = new ArrayList<Type>();
					
					sb.append("SELECT DISTINCT pn.cupon.emision.emisora.idEmisora, ");
					sb.append("		pn.cupon.emision.emisora.clavePizarra, ");
					sb.append("		pn.cupon.emision.emisora.razonSocial ");
					sb.append("FROM " + PosicionNombrada.class.getName() +" pn ");
					sb.append("WHERE pn.cupon.estadoCupon.idEstatusCupon = ? ");
					params.add(VIGENTE);
					types.add(new BigIntegerType());
					sb.append("		AND pn.posicionDisponible > 0 ");
					sb.append("		AND pn.cupon.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("		AND pn.cuentaNombrada.tipoCuenta.naturalezaContable = 'P' ");
					
					sb.append("		AND pn.cupon.emision.emisora.idEmisora IN ");
					sb.append(" 		(");
					sb.append("				SELECT DISTINCT(ipe.emision.emisora.idEmisora) ");
					sb.append("				FROM " + InstitucionPerfilEmision.class.getName() + " ipe ");
					sb.append("				WHERE ipe.institucionPerfil.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
					sb.append("					AND ipe.institucionPerfil.institucion.folioInstitucion = ? ");
					sb.append("					AND ipe.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("					AND ipe.institucionPerfil.perfil = 2 ");
					sb.append("			 )");
					params.add( agente.getId() );
					types.add( new StringType() );
					params.add( agente.getFolio() );
					types.add( new StringType() );
					
					if( emision != null ) {
						if( StringUtils.isNotBlank(emision.getIdTipoValor()) ) {
							String tv = emision.getIdTipoValor();
							sb.append("		AND pn.cupon.emision.instrumento.claveTipoValor = ? ");
							params.add( tv );
							types.add( new StringType() );
						}
						
						if( StringUtils.isNotBlank(emision.getEmisora()) ) {
							String emisora = emision.getEmisora();
							sb.append("		AND pn.cupon.emision.emisora.clavePizarra = ? ");
							params.add( emisora );
							types.add( new StringType() );
						}
					}
					
					sb.append("ORDER BY pn.cupon.emision.emisora.clavePizarra ");
					
					Query query = session.createQuery(sb.toString());
                	
                    return  query.setParameters(params.toArray(), types.toArray(new Type[]{})).list();
				}
				
			});
			return retorno;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List getSaldoEstadoCuentasocInv(final AgenteVO agente, final String emisora) {
		if( validaAgente(agente) && StringUtils.isNotBlank(emisora) ) {
			List retorno = (List)getHibernateTemplate().execute(new HibernateCallback() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer sb = new StringBuffer("");
					List<Object> params = new ArrayList<Object>();
					List<Type> types = new ArrayList<Type>();
					
					sb.append("SELECT pn.cupon.emision.instrumento.claveTipoValor, ");
					sb.append("		pn.cupon.emision.emisora.clavePizarra, ");
					sb.append("		pn.cupon.emision.serie, ");
					sb.append(" 	pn.cupon.claveCupon, ");
					sb.append(" 	SUM(CASE pn.cuentaNombrada.tipoCuenta.tipoTenencia WHEN 'C' THEN pn.posicionDisponible ELSE 0 END), ");
					sb.append(" 	SUM(CASE pn.cuentaNombrada.tipoCuenta.tipoTenencia WHEN 'E' THEN pn.posicionDisponible ELSE 0 END), ");
					sb.append(" 	SUM(pn.posicionDisponible) ");
					sb.append("FROM " + PosicionNombrada.class.getName() +" pn ");
					sb.append("WHERE pn.cupon.estadoCupon.idEstatusCupon = ? ");
					params.add(VIGENTE);
					types.add(new BigIntegerType());
					sb.append("		AND pn.posicionDisponible > 0 ");
					sb.append("		AND pn.cupon.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("		AND pn.cuentaNombrada.tipoCuenta.naturalezaContable = 'P' ");
					
					sb.append(" 	AND pn.cupon.emision.idEmision IN ( ");
					sb.append("				SELECT DISTINCT(ipe.emision.idEmision) ");
					sb.append("				FROM " + InstitucionPerfilEmision.class.getName() + " ipe ");
					sb.append("				WHERE ipe.institucionPerfil.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
					sb.append("					AND ipe.institucionPerfil.institucion.folioInstitucion = ? ");
					sb.append("					AND ipe.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("					AND ipe.institucionPerfil.perfil = 2 ");
					sb.append("			 ) ");
					params.add( agente.getId() );
					types.add( new StringType() );
					params.add( agente.getFolio() );
					types.add( new StringType() );
				
					sb.append("		AND pn.cupon.emision.emisora.clavePizarra = ? ");
					params.add( emisora );
					types.add( new StringType() );
					
					sb.append("GROUP BY pn.cupon.emision.instrumento.claveTipoValor, ");
					sb.append("		pn.cupon.emision.emisora.clavePizarra, ");
					sb.append("		pn.cupon.emision.serie, ");
					sb.append(" 	pn.cupon.claveCupon ");

					Query query = session.createQuery(sb.toString());
                	
                    return  query.setParameters(params.toArray(), types.toArray(new Type[]{})).list();
				}
				
			});
			return retorno;
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getLista1234(final AgenteVO agente) {
		logger.info("*****Entrando a este DAO");
		if( validaAgente(agente) ) {
			List retorno = (List)getHibernateTemplate().execute(new HibernateCallback() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer sb = new StringBuffer("");
					List<Object> params = new ArrayList<Object>();
					List<Type> types = new ArrayList<Type>();
					
					sb.append("SELECT DISTINCT ipe.emision.emisora.clavePizarra ");
					sb.append("FROM " + InstitucionPerfilEmision.class.getName() + " ipe ");
					sb.append("WHERE ipe.institucionPerfil.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
					sb.append("		AND ipe.institucionPerfil.institucion.folioInstitucion = ? ");
					sb.append("		AND ipe.emision.instrumento.claveTipoValor IN ('5','51','52','53','54') ");
					sb.append("		AND ipe.institucionPerfil.perfil = 2 ");
					sb.append("ORDER BY ipe.emision.emisora.clavePizarra ");
					
					params.add( agente.getId() );
					types.add( new StringType() );
					params.add( agente.getFolio() );
					types.add( new StringType() );
					
					Query query = session.createQuery(sb.toString());
                	
                    return  query.setParameters(params.toArray(), types.toArray(new Type[]{})).list();
				}
				
			});
			return retorno;
		}
		return null;
	}
}
