package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.fo.FastworkMessageFO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FastworkMessage;
import com.indeval.portalinternacional.middleware.servicios.vo.FastworkMessageVO;
import com.indeval.portalinternacional.persistence.dao.FastworkDao;

public class FastworkDaoImpl extends BaseDaoHibernateImpl implements FastworkDao {
	
	private final Long maxResuts = 10000L;

	@SuppressWarnings("unchecked")
	@Override
	public PaginaVO consultaMensajes(final FastworkMessageFO fastworkMessagefo, PaginaVO paginaVO, final boolean reporte) {
	
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset():null;		
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag():null;
		
		//Comienza query
		StringBuilder messageQuery = new StringBuilder();
		messageQuery.append("FROM " + FastworkMessage.class.getName() + " fm ");
		messageQuery.append("WHERE ");
		messageQuery.append("1=1 ");
		
		//Filtros
		
		if(fastworkMessagefo.getIsin() != null && !fastworkMessagefo.getIsin().isEmpty()) {
			messageQuery.append("AND fm.isin like '%" + fastworkMessagefo.getIsin() + "%' ");
		}
		
		if(fastworkMessagefo.getBiccode() != null && !fastworkMessagefo.getBiccode().isEmpty()) {
			messageQuery.append("AND fm.bicCode like '%" + fastworkMessagefo.getBiccode() + "%' ");
		}
		
		if(fastworkMessagefo.getTipoMensajesForQuery() != null && !fastworkMessagefo.getTipoMensajesForQuery().isEmpty()) {
			messageQuery.append("AND ");
			messageQuery.append("fm.tipoMensaje IN (" + fastworkMessagefo.getTipoMensajesForQuery() + ") ");
		}
		
		if(fastworkMessagefo.getMonedaForQuery() != null && !fastworkMessagefo.getMonedaForQuery().isEmpty()) {
			messageQuery.append("AND ("
					+ "fm.currency19ASETT IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19AESTT IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BCHAR IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BPRIN IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BCINL IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BGRSS IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BNETT IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BTAXR IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BENTL IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BNRAT IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BPSTA IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency19BFTCA IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency92FCHAR IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency92FINTP IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency92FGRSS IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency92FNETT IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency32A IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency32B IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency60F IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency62F IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ "OR fm.currency64 IN (" + fastworkMessagefo.getMonedaForQuery() + ") "
					+ ") ");
		}
		
		if(fastworkMessagefo.getMonto() != null) {
			messageQuery.append("AND ("
					+ "fm.amount19ASETT = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19AESTT = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BCHAR = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BPRIN = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BCINL = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BGRSS = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BNETT = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BTAXR = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BENTL = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BNRAT = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BPSTA = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount19BFTCA = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount92FCHAR = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount92FINTP = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount92FGRSS = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount92FNETT = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount32A = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount32B = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount60F = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount62F = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ " OR fm.amount64 = '" + fastworkMessagefo.getMonto().toString() + "'"
					+ ") ");
		}
		
		if(fastworkMessagefo.getFechaInicial() != null) {
			if(fastworkMessagefo.getFechaFinal() != null) {
				messageQuery.append("AND fm.dateTime BETWEEN ? AND ? ");
			}else {
				messageQuery.append("AND fm.dateTime >= ? ");
			}
		}else {
			if(fastworkMessagefo.getFechaFinal() != null) {
				messageQuery.append("AND fm.dateTime <= ? ");
			}			
		}

		
		if(fastworkMessagefo.getBuscar() != null && !fastworkMessagefo.getBuscar().isEmpty()) {
			messageQuery.append("AND dbms_lob.instr (fm.message,utl_raw.cast_to_raw ('" + fastworkMessagefo.getBuscar().toUpperCase().replace("'", "''") + "'),1,1) > 0 ");
		}
		
		final String strCountQuery = messageQuery.toString();

		//Se hace conteo para paginación
		if(paginaVO.getRegistros() == null) {
			
	    	Long totalRegistros = (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
	    		
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					
					Query query = session.createQuery("select count(*) " + strCountQuery.toString());
					
					if(fastworkMessagefo.getFechaInicial() != null) {
						
						if(fastworkMessagefo.getFechaFinal() != null) {
							
							query.setDate(0, fastworkMessagefo.getFechaInicial());
							
							query.setDate(1, fastworkMessagefo.getFechaFinal());
							
						}else {
							
							query.setDate(0, fastworkMessagefo.getFechaInicial());
						}
					}else {
						if(fastworkMessagefo.getFechaFinal() != null) {
							
							query.setDate(0, fastworkMessagefo.getFechaFinal());
							
						}
					}
					return query.uniqueResult();
					
	    		}
			});
	    	
	    	paginaVO.setTotalRegistros(totalRegistros.intValue());
		}
		
		if(paginaVO.getTotalRegistros() == 0) {
			return paginaVO;
		}
		messageQuery.append("ORDER BY fm.dateTime DESC ");
		
		final String strQuery = messageQuery.toString();
		final int totalRegistrosValor = paginaVO.getTotalRegistros();
		
		//Se consultan los registros
		@SuppressWarnings("unchecked")
		List<FastworkMessage> resultadoQuery = (List<FastworkMessage>) this.getHibernateTemplate().execute(new HibernateCallback(){
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(strQuery.toString());	

				if(fastworkMessagefo.getFechaInicial() != null) {
					
					if(fastworkMessagefo.getFechaFinal() != null) {
						
						query.setDate(0, fastworkMessagefo.getFechaInicial());
						
						query.setDate(1, fastworkMessagefo.getFechaFinal());
						
					}else {
						
						query.setDate(0, fastworkMessagefo.getFechaInicial());
						
					}
				}else {
					
					if(fastworkMessagefo.getFechaFinal() != null) {
						
						query.setDate(0, fastworkMessagefo.getFechaFinal());
						
					}
				}
				
				if(!reporte) {
					
					query.setMaxResults(regxpag).setFirstResult(offset).setFetchSize(regxpag);
					
				}else {
					
					if(totalRegistrosValor > maxResuts) {
						
						query.setMaxResults(maxResuts.intValue()).setFetchSize(maxResuts.intValue());
						
					}	
					
				}
				
				return query.list();
			}
		});
		
		//Deslindamos objetos de la conexión a BD
		for(FastworkMessage message:resultadoQuery) {
			
			getSession().evict(message);
			
		}
		
		List<FastworkMessageVO> listaResultados = FastworkMessageVO.getlistaResultados(resultadoQuery, fastworkMessagefo.getCamposSwift());
		
		paginaVO.setRegistros(listaResultados);
		
		return paginaVO;
	}
	
	public FastworkMessageVO consultaIso(String dbkey) {
		PaginaVO paginaVO = new PaginaVO();
		//Comienza query
		StringBuilder messageQuery = new StringBuilder();
		messageQuery.append("FROM " + FastworkMessage.class.getName() + " fm ");
		messageQuery.append("WHERE ");
		messageQuery.append("fm.dbkey = '" + dbkey + "'");
		
		final String strQuery = messageQuery.toString();
		//Se consultan los registros
		@SuppressWarnings("unchecked")
		FastworkMessage resultadoQuery = (FastworkMessage) this.getHibernateTemplate().execute(new HibernateCallback(){
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(strQuery.toString());	
				
				return query.uniqueResult();
			}
		});
		
		getSession().evict(resultadoQuery);

		FastworkMessageVO resultado = new FastworkMessageVO(resultadoQuery);
		
		return resultado;
		
	}

}
