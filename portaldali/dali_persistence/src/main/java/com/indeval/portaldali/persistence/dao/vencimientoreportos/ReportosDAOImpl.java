package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.criterio.CriterioVencimientoReportosDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

public class ReportosDAOImpl extends HibernateDaoSupport implements ReportosDAO {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ResourceBundle rb = ResourceBundle.getBundle("sql/reportos");

	private final String COUNT_REPORTOS = rb.getString("COUNT_REPORTOS");
	private final String FIND_REPORTOS  = rb.getString("FIND_REPORTOS");
	
	private DateUtilsDao dateUtilsDao;

	
	@Override
	public long countReportos(final CriterioVencimientoReportosDTO criterio) {
		logger.trace("countReportos");
		return (long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuilder sqlString = new StringBuilder(COUNT_REPORTOS);
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Type> tipos = new ArrayList<Type>();

				
				crearCriteriosConsultaReportos(sqlString, params, tipos, criterio);

				SQLQuery sqlQuery = session.createSQLQuery(sqlString.toString());
				sqlQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				return ((Number)sqlQuery.uniqueResult()).longValue();
			}
		});
	}

	
	@Override 
	@SuppressWarnings("unchecked")
	public List<ConsultaOperacionesMatch> findReportos(CriterioVencimientoReportosDTO criterio) {
		logger.trace("findReportos");
		
		final StringBuilder sqlString = new StringBuilder(FIND_REPORTOS);
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		
		crearCriteriosConsultaReportos(sqlString, params, tipos, criterio);
		
		
		sqlString.append(" ORDER BY iov.fecha_liquidacion + iov.plazo_reporto ASC NULLS LAST, iov.id_instruccion_operacion_val ");
		
		return (List<ConsultaOperacionesMatch>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            	
            	SQLQuery sqlQuery = session.createSQLQuery(sqlString.toString());
            	sqlQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
            	
                sqlQuery.addScalar("idInstruccionOperacion", Hibernate.LONG);
                sqlQuery.addScalar("cantidadTitulos", Hibernate.LONG);
                sqlQuery.addScalar("cuentaReceptora", Hibernate.STRING);
                sqlQuery.addScalar("cuentaTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("claveDivisa", Hibernate.STRING);
                sqlQuery.addScalar("idEstadoInstruccionCat", Hibernate.LONG);
                sqlQuery.addScalar("claveEstadoInstruccionCat", Hibernate.STRING);
                sqlQuery.addScalar("descEstadoInstruccionCat", Hibernate.STRING);
                sqlQuery.addScalar("fechaConcertacion", Hibernate.TIMESTAMP);
                sqlQuery.addScalar("fechaLiquidacion", Hibernate.TIMESTAMP);
                sqlQuery.addScalar("folioControl", Hibernate.LONG);
                sqlQuery.addScalar("folioInstruccionReceptora", Hibernate.STRING);
                sqlQuery.addScalar("folioInstruccionTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("importe", Hibernate.DOUBLE); 
                sqlQuery.addScalar("interesesGenerados", Hibernate.DOUBLE); 
                sqlQuery.addScalar("idInstitucionTraspasante", Hibernate.LONG);
                sqlQuery.addScalar("cveTipoInstitucionTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("idFolioInstitucionTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("nombreCortoInstitTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("nombreCuentaTraspasante", Hibernate.STRING);
                sqlQuery.addScalar("idInstitucionReceptora", Hibernate.LONG);
                sqlQuery.addScalar("cveTipoInstitucionReceptora", Hibernate.STRING);
                sqlQuery.addScalar("idFolioInstitucionReceptora", Hibernate.STRING);
                sqlQuery.addScalar("nombreCortoInstitReceptora", Hibernate.STRING);
                sqlQuery.addScalar("nombreCuentaReceptora", Hibernate.STRING);
                sqlQuery.addScalar("plazoReporto", Hibernate.LONG);
                sqlQuery.addScalar("fechaVencimiento", Hibernate.DATE);
                sqlQuery.addScalar("precioTitulo", Hibernate.DOUBLE);
                sqlQuery.addScalar("tasaNegociada", Hibernate.DOUBLE); 
                sqlQuery.addScalar("idMercado", Hibernate.LONG);
                sqlQuery.addScalar("tv", Hibernate.STRING); 
                sqlQuery.addScalar("emisora", Hibernate.STRING);
                sqlQuery.addScalar("serie", Hibernate.STRING);
                sqlQuery.addScalar("isin", Hibernate.STRING);
                sqlQuery.addScalar("cupon", Hibernate.STRING);
                sqlQuery.addScalar("idVencimientoAnticipado", Hibernate.LONG);
                sqlQuery.addScalar("idInstitucionSolicitud", Hibernate.LONG);
                sqlQuery.addScalar("idInstitucionAutoriza", Hibernate.LONG);
                
                sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(ConsultaOperacionesMatch.class));
                
                return sqlQuery.list();
            }
        });
	}

	private void crearCriteriosConsultaReportos(StringBuilder query,  List<Object> params, List<Type> types, CriterioVencimientoReportosDTO criterio) {
		logger.trace("crearCriteriosConsultaReportos");
		
		if(criterio.getIdInstitucionRestringeResultados() > 0 ) {
			query.append(" AND (iov.id_institucion_traspasante = ? OR iov.id_institucion_receptora = ?) ");
			params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionRestringeResultados() ))); types.add(new BigIntegerType());
			params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionRestringeResultados() ))); types.add(new BigIntegerType());
		}
		
		// 
		if(criterio.getFechaReportoIni()!=null) {
			query.append(" AND TRUNC(iov.fecha_liquidacion + iov.plazo_reporto) >= ? ");
			params.add(criterio.getFechaReportoIni());
            types.add(new DateType());
		}
		
		// 
		if(criterio.getFechaReportoFin()!=null) {
			query.append(" AND TRUNC(iov.fecha_liquidacion + iov.plazo_reporto) <= ? ");
			params.add(criterio.getFechaReportoFin());
			types.add(new DateType());
		}
		
		if( criterio.getIdInstitucionParticipante() > 0 && 
				criterio.getIdInstitucionParticipante() == criterio.getIdInstitucionContraparte()) {
			query.append(" AND (iov.id_institucion_traspasante = ? AND iov.id_institucion_receptora = ?) ");
			params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionParticipante() ))); types.add(new BigIntegerType());
			params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionContraparte() ))); types.add(new BigIntegerType());
			
		}else {
			
			// INSTITUCION TRASPASANTE
			if (criterio.getIdInstitucionParticipante() > 0 ) {
				query.append(" AND (iov.id_institucion_traspasante = ? OR iov.id_institucion_receptora = ?) ");
				params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionParticipante() ))); types.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionParticipante() ))); types.add(new BigIntegerType());
	        } 
			
			// ID INSTITUCION RECEPTORA
	        if (criterio.getIdInstitucionContraparte() > 0) {
	        	query.append(" AND (iov.id_institucion_traspasante = ? OR iov.id_institucion_receptora = ?) ");
				params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionContraparte() ))); types.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf( criterio.getIdInstitucionContraparte() ))); types.add(new BigIntegerType());
	        } 
		}
		
		if(StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante()) && 
				StringUtils.equals(criterio.getClaveTipoInstitucionParticipante(), criterio.getClaveTipoInstitucionContraparte())) {
			query.append(" AND (tinst.clave_tipo_institucion = ? AND tinsr.clave_tipo_institucion = ?) ");
			params.add(criterio.getClaveTipoInstitucionParticipante()); types.add(new StringType());
			params.add(criterio.getClaveTipoInstitucionContraparte()); types.add(new StringType());
		}else {
			// TIPO INSTITUCION TRASPASANTE
	        if (StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())){
	            query.append(" AND (tinst.clave_tipo_institucion = ? OR tinsr.clave_tipo_institucion = ?) ");
	            params.add(criterio.getClaveTipoInstitucionParticipante()); types.add(new StringType());
	            params.add(criterio.getClaveTipoInstitucionParticipante()); types.add(new StringType());
	        }
	        
	        // TIPO INSTITUCION RECEPTORA
	        if (StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())){
	            query.append(" AND (tinst.clave_tipo_institucion = ? OR tinsr.clave_tipo_institucion = ?) ");
	            params.add(criterio.getClaveTipoInstitucionContraparte()); types.add(new StringType());
	            params.add(criterio.getClaveTipoInstitucionContraparte()); types.add(new StringType());
	        }
		}
		
		if(StringUtils.isNotBlank(criterio.getCuentaParticipante()) &&
				StringUtils.equals(criterio.getCuentaParticipante(), criterio.getCuentaContraparte())) {
			query.append(" AND (cnt.cuenta = ? AND cnr.cuenta = ?) ");
			params.add(criterio.getCuentaParticipante()); types.add(new StringType());
			params.add(criterio.getCuentaContraparte()); types.add(new StringType());
		}else {
			// CUENTA TRASPASANTE
	        if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
	            query.append(" AND (cnt.cuenta = ? OR cnr.cuenta = ?) ");
	            params.add(criterio.getCuentaParticipante()); types.add(new StringType());
	            params.add(criterio.getCuentaParticipante()); types.add(new StringType());
	        }
	        
	        // CUENTA RECEPTORA
		    if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
		        query.append(" AND (cnr.cuenta = ? OR cnt.cuenta = ?) ");
		        params.add(criterio.getCuentaContraparte()); types.add(new StringType());
		        params.add(criterio.getCuentaContraparte()); types.add(new StringType());
		    }
		}
	    
		// TIPO_VALOR
		if (criterio.getIdTipoValor() > 0) {
            query.append(" AND instr.id_instrumento = ? "); 
            params.add(new BigInteger(String.valueOf(criterio.getIdTipoValor())));
            types.add(new BigIntegerType());
        }

        // EMISORA
        if (criterio.getIdEmisora() > 0) {
            query.append(" AND em.id_emisora = ? "); 
            params.add(new BigInteger(String.valueOf(criterio.getIdEmisora())));
            types.add(new BigIntegerType());
        }

        // SERIE
        if (!StringUtils.isEmpty(criterio.getSerie())) {
            query.append(" AND e.serie = ? "); 
            params.add(criterio.getSerie());
            types.add(new StringType());
        }
        
        
        logger.debug("query " + query);
        logger.debug("params " + params);
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}
}
