package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;
import com.indeval.portalinternacional.persistence.dao.FormatoFiscalDao;

public class FormatoFiscalDaoImpl extends BaseDaoHibernateImpl implements FormatoFiscalDao {
	
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FormatoFiscalDaoImpl.class);
        
	public Long validateExistsFormatoFiscalTin(final FormatoFiscal params) {
		
		final String countConsulta = createValidateExistsResistryFormatoFisalTinQuery(params, "count");
		
		log.info("FormatoFiscalDaoImpl ::: TipoFormato :: " + params.getTipoFormato()
		+ " :: Nombre :: " + params.getNombreBeneficiario()
		+ " :: ApellidoPaterno :: " + params.getApellidoPaternoBeneficiario()
		+ " :: ApellidoMaterno :: " + params.getApellidoMaternoBeneficiario()
		+ " :: RazonSocial :: " + params.getRazonSocialBeneficiario()
		+ " :: Institucion :: " + params.getInstitucionID()
		+ " :: TipoInstitucion :: " + params.getTipoInstitucionID());

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(countConsulta);
				query = createValidateExistsResistryFormatoFisalTinQueryParams(params, query);
				return query.uniqueResult();
			}
		});
        
	}
	
	@SuppressWarnings("unchecked")
	public List<FormatoFiscal> getListDateOperation(final FormatoFiscal params) {
		
		final String countConsulta = createValidateExistsResistryFormatoFisalTinQuery(params, "list");
		
		List<FormatoFiscal> listDateOperation = (List<FormatoFiscal>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(countConsulta);
				query = createValidateExistsResistryFormatoFisalTinQueryParams(params, query);
				return query.list();
			}
		});
		
		return listDateOperation;
        
	}
	
	private String createValidateExistsResistryFormatoFisalTinQuery(FormatoFiscal params, String tipo){
		
        StringBuilder sb = new StringBuilder();

        if(tipo.equals("count"))
        	sb.append(" SELECT COUNT(*)");
        else if(tipo.equals("list"))
        	sb.append(" SELECT ff ");
        
        sb.append(" FROM " + FormatoFiscal.class.getName() + " ff");
        sb.append("	WHERE ff.formatoFiscalesID is not null");
        
        if (params.getTipoFormato() != null) {
            sb.append("	AND TRIM(REPLACE(ff.tipoFormato, ' ', '')) = :tipoFormato");
        }
        	
        if (params.getNombreBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.nombreBeneficiario, ' ', '')) = :nombreBeneficiario");
        }/*else{
        	sb.append("	AND ff.tipoFormato IS NULL");
        }*/
        
        if (params.getApellidoPaternoBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.apellidoPaternoBeneficiario, ' ', '')) = :apellidoPaterno");
        }/*else{
        	sb.append("	AND ff.apellidoPaternoBeneficiario IS NULL");
        }*/

        if (params.getApellidoMaternoBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.apellidoMaternoBeneficiario, ' ', '')) = :apellidoMaterno");
        }/*else{
        	sb.append("	AND ff.apellidoMaternoBeneficiario IS NULL");
        }*/
        
        if (params.getRazonSocialBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.razonSocialBeneficiario, ' ', '')) = :razonSocial");
        }/*else{
        	sb.append("	AND ff.razonSocialBeneficiario IS NULL");
        }*/
        
        if (params.getTaxID() != null) {
            sb.append("	AND TRIM(REPLACE(ff.taxID, ' ', '')) = :taxID");
        }else{
        	sb.append("	AND ff.taxID IS NULL");
        }

        if (params.getTipoInstitucionID() != null) {
            sb.append("	AND ff.tipoInstitucionID = :tipoInstitucionID");
        }

        if (params.getFolioInstitucion() != null) {
            sb.append("	AND ff.folioInstitucion = :folioInstitucion");
        }
/*        
        if (params.getCuentaNombrada() != null) {
            sb.append("	AND ff.cuentaNombrada = :cuentaNombrada");
        }      

        if (params.getApellidoPaternoBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.apellidoPaternoBeneficiario, ' ', '')) = :apellidoPaterno");
        }

        if (params.getApellidoMaternoBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.apellidoMaternoBeneficiario, ' ', '')) = :apellidoMaterno");
        }
        
        if (params.getRazonSocialBeneficiario() != null) {
            sb.append("	AND TRIM(REPLACE(ff.razonSocialBeneficiario, ' ', '')) = :razonSocial");
        }

        if (params.getTipoInstitucionID() != null) {
            sb.append("	AND ff.tipoInstitucionID = :tipoInstitucionID");
        }
*/
        
        sb.append(" ORDER BY ff.fechaAutorizacion DESC");

        log.debug("Query : [" + sb.toString() + "]");
        return sb.toString();
        
	}
	
	private Query createValidateExistsResistryFormatoFisalTinQueryParams(FormatoFiscal params, Query query){

        if (params.getTipoFormato() != null) {
    		query.setParameter("tipoFormato", params.getTipoFormato().replace(" ", ""));
        }
        
        if (params.getNombreBeneficiario() != null) {
        	query.setParameter("nombreBeneficiario", params.getNombreBeneficiario().replace(" ", ""));
        }
        
        if (params.getApellidoPaternoBeneficiario() != null) {
        	query.setParameter("apellidoPaterno", params.getApellidoPaternoBeneficiario().replace(" ", ""));
        }

        if (params.getApellidoMaternoBeneficiario() != null) {
        	query.setParameter("apellidoMaterno", params.getApellidoMaternoBeneficiario().replace(" ", ""));
        }
        
        if (params.getRazonSocialBeneficiario() != null) {
        	query.setParameter("razonSocial", params.getRazonSocialBeneficiario().replace(" ", ""));
        }
        
        if (params.getTaxID() != null) {
        	query.setParameter("taxID", params.getTaxID().replace(" ", ""));
        }
        
        if (params.getTipoInstitucionID() != null) {
        	query.setParameter("tipoInstitucionID", params.getTipoInstitucionID());
        }
        
        if(params.getFolioInstitucion() != null){
        	query.setParameter("folioInstitucion", params.getFolioInstitucion());
        }
/*        
        if (params.getTaxID() != null) {
        	query.setParameter("taxID", params.getTaxID().replace(" ", ""));
        }
        
        if (params.getInstitucionID() != null) {
        	query.setParameter("institucionID", params.getInstitucionID());
        }

        if (params.getCuentaNombrada() != null) {
        	query.setParameter("cuentaNombrada", params.getCuentaNombrada());
        }
*/
		log.info("queryFinal :: " + query.getQueryString());
        return query;
	}

	public FormatoFiscal findFormatoFiscal(FormatoFiscal formatoFiscal) {
		log.info("FormatoFiscalDaoImpl :: findFormatoFiscal");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + FormatoFiscal.class.getName() + " formato ");
        sb.append(" WHERE formato.beneficiarioID = ? ");
        paramsSQL.add(formatoFiscal.getBeneficiarioID());
        tipos.add(new LongType());
        sb.append("		AND formato.institucionID = ? ");
        paramsSQL.add(formatoFiscal.getInstitucionID());
        tipos.add(new LongType());
        sb.append("		AND formato.folioInstitucion = ? ");
        paramsSQL.add(formatoFiscal.getFolioInstitucion());
        tipos.add(new StringType());
        
        return (FormatoFiscal) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
	}

	public FormatoFiscal getTinByBeneficiciarioInstitutionTin(FormatoFiscal formatoFiscalBean) {
		log.info("FormatoFiscalDaoImpl :: getTinByBeneficiciarioInstitutionTin");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + FormatoFiscal.class.getName() + " formato ");
        sb.append(" WHERE formato.beneficiarioID = ? ");
        paramsSQL.add(formatoFiscalBean.getBeneficiarioID());
        tipos.add(new LongType());
        sb.append("		AND formato.institucionID = ? ");
        paramsSQL.add(formatoFiscalBean.getInstitucionID());
        tipos.add(new LongType());
        sb.append("		AND formato.folioInstitucion = ? ");
        paramsSQL.add(formatoFiscalBean.getFolioInstitucion());
        tipos.add(new StringType());
        
        sb.append("		AND formato.uoi = ? ");
        paramsSQL.add(formatoFiscalBean.getUoi());
        tipos.add(new StringType());
        
        if(!StringUtils.isBlank(formatoFiscalBean.getNombreBeneficiario()) && formatoFiscalBean.getNombreBeneficiario() != null){
            sb.append("		AND formato.nombreBeneficiario = ? ");
            paramsSQL.add(formatoFiscalBean.getNombreBeneficiario());
            tipos.add(new StringType());
        }
        
        if(!StringUtils.isBlank(formatoFiscalBean.getApellidoPaternoBeneficiario()) && formatoFiscalBean.getApellidoPaternoBeneficiario() != null){
	        sb.append("		AND formato.apellidoPaternoBeneficiario = ? ");
	        paramsSQL.add(formatoFiscalBean.getApellidoPaternoBeneficiario());
	        tipos.add(new StringType());
        }
        
        if(!StringUtils.isBlank(formatoFiscalBean.getApellidoMaternoBeneficiario()) && formatoFiscalBean.getApellidoMaternoBeneficiario() != null){
	        sb.append("		AND formato.apellidoMaternoBeneficiario = ? ");
	        paramsSQL.add(formatoFiscalBean.getApellidoMaternoBeneficiario());
	        tipos.add(new StringType());
        }
        
        if(!StringUtils.isBlank(formatoFiscalBean.getRazonSocialBeneficiario()) && formatoFiscalBean.getRazonSocialBeneficiario() != null){
	        sb.append("		AND formato.razonSocialBeneficiario = ? ");
	        paramsSQL.add(formatoFiscalBean.getRazonSocialBeneficiario());
	        tipos.add(new StringType());
        }
        
        if(!StringUtils.isBlank(formatoFiscalBean.getTaxID()) && formatoFiscalBean.getTaxID() != null){
	        sb.append("		AND formato.taxID = ? ");
	        paramsSQL.add(formatoFiscalBean.getTaxID());
	        tipos.add(new StringType());
        }
        
        
        return (FormatoFiscal) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
	}
	
	public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion){
		log.info("FormatoFiscalDaoImpl :: getBeneficiarioInstitucion");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + BeneficiarioInstitucion.class.getName() + " benefInst ");
        sb.append(" WHERE benefInst.beneficiario = ? ");
        paramsSQL.add(idBeneficiario);
        tipos.add(new LongType());
        sb.append("		AND benefInst.institucion = ? ");
        paramsSQL.add(idInstitucion);
        tipos.add(new LongType());
        
        return (BeneficiarioInstitucion) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });		
	}
	
	public void updateExtraerFormato(final FormatoFiscal formatoFiscal) {
		final StringBuffer sql = new StringBuffer();
		
		sql.append(" UPDATE " + FormatoFiscal.class.getName() + " ");
		sql.append(" SET extraer = :extraer ");
		sql.append(" WHERE formatoFiscalesID = :formatoFiscalesID ");
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sql.toString());
				query.setBoolean("extraer", formatoFiscal.getExtraer());
				query.setLong("formatoFiscalesID", formatoFiscal.getFormatoFiscalesID());
				return query.executeUpdate();
			}
		});
		
	}

}
