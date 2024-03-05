/**
 * 2H Software - Bursatec
 *
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 5, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.DivisaBoveda;
import com.indeval.portaldali.persistence.model.TipoBoveda;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.model.TipoInstruccionBoveda;
import com.indeval.portaldali.persistence.model.TipoInstruccionDivisa;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con el
 * catálogo de bóvedas.
 *
 * @author Sandra Flores Arrieta
 *
 */
public class BovedaDaliDAOImpl extends HibernateDaoSupport implements BovedaDaliDAO {


	private List<Long> boveda;


	@SuppressWarnings("unchecked")
	public List<BovedaDTO> buscarBovedas(final EstadoPaginacionDTO estadoPaginacion) {

		return (List<BovedaDTO>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Boveda.class.getName() + " d " +
				" order by d.descripcion");

				if (estadoPaginacion != null) {
					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				List<Boveda> consulta = query.list();
				List<BovedaDTO> resultadosConsulta = new ArrayList<BovedaDTO>();
				for (Boveda boveda : consulta) {
					resultadosConsulta.add(DTOAssembler.crearBovedaDTO(boveda));
				}
				return resultadosConsulta;
			}
		});
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.dao.BovedaDAO#buscarBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<BovedaDTO> buscarBovedasPorTipoCustodia(final BovedaDTO boveda, final EstadoPaginacionDTO estadoPaginacion) {



		return (List<BovedaDTO> )getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Boveda.class);

				if (boveda != null && boveda.getClaveTipoBoveda() != null) {

					if (boveda.getClaveTipoBoveda().equals("V")) {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

						);
					} else {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.NACIONAL_EFECTIVO })

						);
					}

				}

				criteria.addOrder(Order.asc("descripcion"));

				if (estadoPaginacion != null) {
					criteria.setFirstResult((estadoPaginacion.getNumeroPagina() * estadoPaginacion.getRegistrosPorPagina()) - 1);
					criteria.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				Iterator bovedasBO = criteria.list().iterator();
				List<BovedaDTO> resultado = new ArrayList<BovedaDTO>();
				BovedaDTO bovedaDTO = null;
				while (bovedasBO.hasNext()) {

					bovedaDTO = DTOAssembler.crearBovedaDTO((Boveda) bovedasBO.next());

					bovedaDTO.setClaveTipoBoveda(boveda.getClaveTipoBoveda());
					resultado.add(bovedaDTO);
				}

				return resultado;
			}
		});
	}
	
	/*busqueda por Nombre Corto recibe un String y devuelve un bovedaDTO 
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public BovedaDTO buscarBovedaPorTipoCustodia(final BovedaDTO boveda) {
		
		//return (BovedaDTO )getHibernateTemplate().executeFind(new HibernateCallback(){
		return (BovedaDTO )getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Boveda.class);

				if (boveda != null && boveda.getClaveTipoBoveda() != null) {

					if (boveda.getClaveTipoBoveda().equals("V")) {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

						);
					} else {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.NACIONAL_EFECTIVO }));
						
					}

				}
				if (StringUtils.isNotBlank(boveda.getNombreCorto())){
				     criteria.add(Expression.eq("nombreCorto", boveda.getNombreCorto()));
				}
				 

				//criteria.addOrder(Order.asc("descripcion"));				

				Iterator bovedasBO = criteria.list().iterator();
				List<BovedaDTO> resultado = new ArrayList<BovedaDTO>();
				BovedaDTO bovedaDTO = null;
				while (bovedasBO.hasNext()) {

					bovedaDTO = DTOAssembler.crearBovedaDTO((Boveda) bovedasBO.next());

					//bovedaDTO.setClaveTipoBoveda(boveda.getClaveTipoBoveda());
					resultado.add(bovedaDTO);
				}
                if(resultado.size()>0){
                	return resultado.get(0);
                }
                return bovedaDTO;
			}
		});
		
		//return null;
		
	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.dao.BovedaDAO#consultarBovedaPorId(long)
	 */
	public BovedaDTO consultarBovedaPorId(long idBoveda) {
		BovedaDTO b = DTOAssembler.crearBovedaDTO((Boveda) getHibernateTemplate().get(Boveda.class, new BigInteger(String.valueOf(idBoveda))));

		return b;
	}




	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.dao.BovedaDAO#obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(final BovedaDTO boveda) {


		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(Boveda.class);

				if (boveda != null && boveda.getClaveTipoBoveda() != null) {

					if (boveda.getClaveTipoBoveda().equals("V")) {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

						);
					} else {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.NACIONAL_EFECTIVO })

						);
					}

				}

				criteria.setProjection(Projections.rowCount());

				return criteria.list().get(0);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.dao.BovedaDAO#buscarBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO,
	 *      java.util.Set,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<BovedaDTO> buscarBovedasPorTipoCustodia(final BovedaDTO boveda, final List<Long> idsBoveda, final EstadoPaginacionDTO estadoPaginacion) {


		return (List<BovedaDTO>)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Boveda.class);

				if (boveda != null && boveda.getClaveTipoBoveda() != null) {

					if (boveda.getClaveTipoBoveda().equals("V")) {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

						);
					} else {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.NACIONAL_EFECTIVO })

						);
					}

				}

				List<BovedaDTO> resultado = new ArrayList<BovedaDTO>();

				if (idsBoveda.size() > 0) {

					List<BigInteger> listaBig = DTOAssembler.transformarListaLongEnBigInteger(idsBoveda);

					criteria.add(Expression.in("idBoveda", listaBig.toArray()));
					criteria.addOrder(Order.asc("descripcion"));

					if (estadoPaginacion != null) {
						criteria.setFirstResult((estadoPaginacion.getNumeroPagina() * estadoPaginacion.getRegistrosPorPagina()) - 1);
						criteria.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					}

					Iterator bovedasBO = criteria.list().iterator();

					BovedaDTO bovedaDTO = null;
					while (bovedasBO.hasNext()) {

						bovedaDTO = DTOAssembler.crearBovedaDTO((Boveda) bovedasBO.next());

						bovedaDTO.setClaveTipoBoveda(boveda.getClaveTipoBoveda());
						resultado.add(bovedaDTO);
					}

				}

				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.dao.BovedaDAO#obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(final BovedaDTO boveda, final List<Long> idsBoveda) {

		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Boveda.class);
				int res = 0;
				if (boveda != null && boveda.getClaveTipoBoveda() != null) {

					if (boveda.getClaveTipoBoveda().equals("V")) {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

						);
					} else {
						criteria.add(Expression.in("tipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.NACIONAL_EFECTIVO })

						);
					}

				}
				if (idsBoveda.size() > 0) {

					List<BigInteger> listaBig = DTOAssembler.transformarListaLongEnBigInteger(idsBoveda);

					criteria.add(Expression.in("idBoveda", listaBig.toArray()));
					criteria.setProjection(Projections.rowCount());
					res = (Integer) criteria.list().get(0);
				}

				return res;
			}
		});
	}
	public List<Long> getBoveda() {
		return boveda;
	}
	public void setBoveda(List<Long> boveda) {
		this.boveda = boveda;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO#obtenerBovedasPorDivisa(com.indeval.portaldali.persistence.model.Divisa)
	 */
	@SuppressWarnings("unchecked")
	public List<BigInteger> obtenerBovedasPorDivisa(final DivisaDTO divisaDTO){
		return (List<BigInteger>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(" FROM " + DivisaBoveda.class.getName() + " d where d.id.idDivisa  = "+ divisaDTO.getId() +" ");
				List<DivisaBoveda> consulta = query.list();
				List<BigInteger> resultadosConsulta = new ArrayList<BigInteger>();
				for (DivisaBoveda divisaBoveda : consulta) {
					resultadosConsulta.add(divisaBoveda.getId().getIdBoveda());
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO#obtenerBovedasPorTipoInstruccion(com.indeval.portaldali.middleware.dto.TipoInstruccionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<BigInteger> obtenerBovedasPorTipoInstruccion(final TipoInstruccionDTO tipoInstruccionDTO){
		return (List<BigInteger>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(" FROM " + TipoInstruccionBoveda.class.getName() + " t "
					+ "where t.id.idTipoInstruccion = " + tipoInstruccionDTO.getIdTipoInstruccion() + " ");
				List<TipoInstruccionBoveda> consulta = query.list();
				List<BigInteger> resultadosConsulta = new ArrayList<BigInteger>();
				for (TipoInstruccionBoveda tipoInstruccionBoveda : consulta) {
					resultadosConsulta.add(tipoInstruccionBoveda.getId().getIdBoveda());
				}
				return resultadosConsulta;
			}
		});
	}
}
