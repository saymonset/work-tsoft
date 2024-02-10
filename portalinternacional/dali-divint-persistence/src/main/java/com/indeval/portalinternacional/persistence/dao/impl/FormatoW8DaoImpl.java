/**
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.constantes.ConstantesCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.CamposW8Ben;
import com.indeval.portalinternacional.middleware.servicios.modelo.CamposW8BenE;
import com.indeval.portalinternacional.middleware.servicios.modelo.CamposW8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.CamposW8IMY2015;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2017;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CamposW;
import com.indeval.portalinternacional.persistence.dao.FormatoW8Dao;

/**
 * DAO para T_CAMPOS_FORMATO_W8BENE y T_CAMPOS_FORMATO_W8IMY2015
 * 
 * @author Abraham Morales
 * 
 */
public class FormatoW8DaoImpl extends BaseDaoHibernateImpl implements FormatoW8Dao {
	
	private static final Logger LOG = LoggerFactory.getLogger(FormatoW8DaoImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.FormatoW8Dao#findCamposFormatoW8BeneById(
     * java.lang.Long)
     */
    public FormatoW8BENE findCamposFormatoW8BeneById(final Long idCamposFormato) {
        return (FormatoW8BENE) this.getByPk(FormatoW8BENE.class, idCamposFormato);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.FormatoW8Dao#findCamposFormatoW8Imy2015ById
     * (java.lang.Long)
     */
    public FormatoW8IMY2015 findCamposFormatoW8Imy2015ById(final Long idCamposFormato) {
        return (FormatoW8IMY2015) this.getByPk(FormatoW8IMY2015.class, idCamposFormato);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.FormatoW8Dao#findCamposFormatoW8Imy2017ById
     * (java.lang.Long)
     */
    public FormatoW8IMY2017 findCamposFormatoW8Imy2017ById(final Long idCamposFormato) {

        return (FormatoW8IMY2017) this.getByPk(FormatoW8IMY2017.class, idCamposFormato);
    }
    
	@Override
	public CamposW findCamposFormatoW(final Long idBeneficiario, final String tipoFormato) {
		LOG.debug("findCamposFormatoW");
		Object res=  getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) {
                final String stringSQL = generaQueryFormatoW(tipoFormato);
                if (idBeneficiario == null || tipoFormato == null || stringSQL == null) {
                	return null;
                }
                final SQLQuery query = session.createSQLQuery(stringSQL.toString());
                LOG.debug("findCamposFormatoW :: query :: " + query.toString());
                LOG.debug("findCamposFormatoW :: idBeneficiario :: " + idBeneficiario);
                
                query.setLong("idBeneficiario", idBeneficiario);
                setRestriccionesFormatoW(query);
                LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW END");
               
                return query.uniqueResult();
            }
            
			private void setRestriccionesFormatoW(SQLQuery query) {
	            LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW");
	            
				query.addScalar("rfc", Hibernate.STRING);
				if (ConstantesCapitales.FORMATO_W8_BEN.equals(tipoFormato)){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_W8_BEN :: " + tipoFormato);
					
					query.addScalar("TAXPAYER_ID_NUMB", Hibernate.STRING);
					query.addScalar("FOREIGN_TAX_ID_NUMB", Hibernate.STRING);
					query.addScalar("REFERENCE_NUMBERS", Hibernate.STRING);
					query.addScalar("FECHA_NACIMIENTO", Hibernate.DATE);
					query.addScalar("ID_CAMPOS_FORMATO_W8BEN", Hibernate.LONG);
					query.addEntity(CamposW8Ben.class);					
				}else if (ConstantesCapitales.FORMATO_W8_BEN2014.equals(tipoFormato) ||
						(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_BEN_PREFIX))){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato FORMATO_W8_BEN2014, FORMATO_W8_BEN_PREFIX :: " + tipoFormato);
					
					query.addScalar("TAXPAYER_ID_NUMB", Hibernate.STRING);
					query.addScalar("FOREIGN_TAX_ID_NUMB", Hibernate.STRING);
					query.addScalar("REFERENCE_NUMBERS", Hibernate.STRING);
					query.addScalar("FECHA_NACIMIENTO", Hibernate.DATE);
					query.addScalar("ID_CAMPOS_FORMATO_W8BEN", Hibernate.LONG);
					query.addEntity(CamposW8Ben.class);
				}else if (ConstantesCapitales.FORMATO_W8_BEN_E.equals(tipoFormato)
						|| ConstantesCapitales.FORMATO_W8_BEN_E2016.equals(tipoFormato) ||
						(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_BEN_E_PREFIX))){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_W8_BEN_E, FORMATO_W8_BEN_E2016, FORMATO_W8_BEN_E_PREFIX :: " + tipoFormato);
					
					query.addScalar("US_TIN", Hibernate.STRING);
					query.addScalar("FOREING_TIN", Hibernate.STRING);
					query.addScalar("REFERENCE_NUMBER", Hibernate.STRING);
					query.addScalar("GIIN", Hibernate.STRING);
					query.addScalar("ID_CAMPOS_FORMATO_W8BENE", Hibernate.LONG);
					query.addEntity(CamposW8BenE.class);
				}else if (ConstantesCapitales.FORMATO_W9.equals(tipoFormato)){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_W9 :: " + tipoFormato);
					
					query.addScalar("exemptPayeeCode", Hibernate.STRING);
					query.addScalar("ssn", Hibernate.STRING);
					query.addScalar("exemptionFromFatcaRepCode", Hibernate.STRING);					
					query.setResultTransformer(Transformers.aliasToBean(CamposW.class));
				}else if (ConstantesCapitales.FORMATO_W8_IMY.equals(tipoFormato)){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_W8_IMY :: " + tipoFormato);
					
					query.addScalar("TAXPAYER_ID_NUMB", Hibernate.STRING);
					query.addScalar("FOREIGN_TAX_ID_NUMB", Hibernate.STRING);
					query.addScalar("REFERENCE_NUMBERS", Hibernate.STRING);					
					query.addScalar("ID_CAMPOS_FORMATO_W8IMY", Hibernate.LONG);
					query.addEntity(CamposW8IMY.class);
				}else if (ConstantesCapitales.FORMATO_W8_IMY2015.equals(tipoFormato) ||
						ConstantesCapitales.FORMATO_W8_IMY2017.equals(tipoFormato) ||
						(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_IMY_PREFIX))){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_W8_IMY2015, FORMATO_W8_IMY2017, FORMATO_W8_IMY_PREFIX :: " + tipoFormato);
					
					query.addScalar("US_TIN", Hibernate.STRING);
					query.addScalar("REFERENCE_NUMBER", Hibernate.STRING);
					query.addScalar("GIIN", Hibernate.STRING);					
					query.addScalar("ID_CAMPOS_FORMATO_W8IMY2015", Hibernate.LONG);
					query.addEntity(CamposW8IMY2015.class);
				}else if (ConstantesCapitales.FORMATO_MILA.equals(tipoFormato)){
					LOG.debug("findCamposFormatoW :: setRestriccionesFormatoW :: tipoFormato, FORMATO_MILA :: " + tipoFormato);
					
					query.setResultTransformer(Transformers.aliasToBean(CamposW.class));
				}
					
			}
		});
		
		LOG.debug("RESULT ## ");
		if(res != null){
			if (res instanceof CamposW){
				CamposW cw = (CamposW) res;
				LOG.debug("RESULT :: A :: " + cw.toString());
				return (CamposW) res;
			}
			for(Object obj : ((Object[])res)){
				if(obj instanceof CamposW){
					CamposW cw = (CamposW) obj;
					LOG.debug("RESULT :: B :: " + cw.toString());
					return (CamposW)obj;
				}
			}
		}
		return null;
	}
	
	private String generaQueryFormatoW(String tipoFormato){
		final StringBuffer stringSQL = new StringBuffer();       
      
        if (ConstantesCapitales.FORMATO_W8_BEN.equals(tipoFormato)){
        	stringSQL
        	.append(" Select cf.foreign_tax_id_numb as rfc")   
        	.append(" , cf.TAXPAYER_ID_NUMB  ")
        	.append(" , cf.FOREIGN_TAX_ID_NUMB  ")
        	.append(" , cf.REFERENCE_NUMBERS  ")
        	.append(" , cf.FECHA_NACIMIENTO  ")
        	.append(" , cf.ID_CAMPOS_FORMATO_W8BEN  ")        	
            .append(" FROM T_BENEFICIARIOS ben, T_CAMPOS_FORMATO_W8BEN cf ")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W8BEN = cf.ID_CAMPOS_FORMATO_W8BEN ");
            
		}else if (ConstantesCapitales.FORMATO_W8_BEN2014.equals(tipoFormato) ||
				(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_BEN_PREFIX))){
			stringSQL
        	.append(" Select cf.foreign_tax_id_numb as rfc ") 
        	.append(" , cf.TAXPAYER_ID_NUMB  ")
        	.append(" , cf.FOREIGN_TAX_ID_NUMB  ")
        	.append(" , cf.REFERENCE_NUMBERS  ")
        	.append(" , cf.FECHA_NACIMIENTO  ")
        	.append(" , cf.ID_CAMPOS_FORMATO_W8BEN  ")   
            .append(" FROM T_BENEFICIARIOS ben, T_CAMPOS_FORMATO_W8BEN cf ")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W8BEN = cf.ID_CAMPOS_FORMATO_W8BEN ");
            
		}else if (ConstantesCapitales.FORMATO_W8_BEN_E.equals(tipoFormato) ||
				ConstantesCapitales.FORMATO_W8_BEN_E2016.equals(tipoFormato) || 
				(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_BEN_E_PREFIX))){
			stringSQL
        	.append(" Select cf.FOREING_TIN as rfc ")   
        	.append(" , cf.US_TIN  ")
        	.append(" , cf.FOREING_TIN  ")
        	.append(" , cf.REFERENCE_NUMBER  ")
        	.append(" , cf.GIIN  ")
        	.append(" , cf.ID_CAMPOS_FORMATO_W8BENE  ")   
            .append(" FROM T_BENEFICIARIOS ben, T_CAMPOS_FORMATO_W8BENE cf")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W8BENE = cf.ID_CAMPOS_FORMATO_W8BENE ");
			
		}else if (ConstantesCapitales.FORMATO_W9.equals(tipoFormato)){
			stringSQL
        	.append(" Select cf.ssn as rfc ")    
        	.append(" , pay.FATCA_CODE as exemptPayeeCode  ")
        	.append(" , fat.FATCA_CODE as exemptionFromFatcaRepCode  ")        	
        	.append(" , CASE when cf.SSN is not null THEN cf.SSN else cf.EMPLOYER_ID_NUMBER END as ssn ")
        	//.append(" , cf.ID_CAMPOS_FORMATO_W9  ")
            .append(" FROM T_BENEFICIARIOS ben, T_CAMPOS_FORMATO_W9 cf LEFT JOIN C_exempt_payee_w9 pay on pay.ID_EXEMPT_PAYEE_W9 = cf.ID_EXEMPT_PAYEE_CODE LEFT JOIN c_exemption_fatca_w9 fat on fat.ID_EXEMPTION_FATCA_W9   =cf.ID_EXEMPTION_FATCA_CODE ")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W9 = cf.ID_CAMPOS_FORMATO_W9  ")
            //.append(" and pay.ID_EXEMPT_PAYEE_W9 (+) = cf.ID_EXEMPT_PAYEE_CODE ")
            //.append(" and fat.ID_EXEMPTION_FATCA_W9 (+) = cf.ID_EXEMPTION_FATCA_CODE  ")
            ;
			
		}else if (ConstantesCapitales.FORMATO_W8_IMY.equals(tipoFormato)){
			stringSQL
        	.append(" Select CASE WHEN cf.taxpayer_id_numb is not null THEN cf.taxpayer_id_numb  else cf.foreign_tax_id_numb END as rfc ")
        	.append(" , cf.TAXPAYER_ID_NUMB  ")
        	.append(" , cf.FOREIGN_TAX_ID_NUMB  ")
        	.append(" , cf.REFERENCE_NUMBERS  ")        	
        	.append(" , cf.ID_CAMPOS_FORMATO_W8IMY  ") 
        	
            .append(" FROM T_CAMPOS_FORMATO_W8IMY cf, T_BENEFICIARIOS ben")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W8IMY = cf.ID_CAMPOS_FORMATO_W8IMY ");
						
		}else if (ConstantesCapitales.FORMATO_W8_IMY2015.equals(tipoFormato)||
				ConstantesCapitales.FORMATO_W8_IMY2017.equals(tipoFormato) ||
				(tipoFormato != null && tipoFormato.trim().startsWith(ConstantesCapitales.FORMATO_W8_IMY_PREFIX))){
			stringSQL
        	.append(" Select cf.us_tin as rfc ")         	
        	.append(" , cf.US_TIN  ")
        	.append(" , cf.REFERENCE_NUMBER  ")
        	.append(" , cf.GIIN  ")        	
        	.append(" , cf.ID_CAMPOS_FORMATO_W8IMY2015  ") 
        	
            .append(" FROM T_CAMPOS_FORMATO_W8IMY2015 cf, T_BENEFICIARIOS ben")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_W8IMY2015 = cf.ID_CAMPOS_FORMATO_W8IMY2015 ");
							
		}else if (ConstantesCapitales.FORMATO_MILA.equals(tipoFormato)){
			stringSQL
        	.append(" Select cf.rfc as rfc ")        	 
            .append(" FROM T_CAMPOS_FORMATO_MILA cf, T_BENEFICIARIOS ben")
            .append(" WHERE ben.ID_CAMPOS_FORMATO_MILA = cf.ID_CAMPOS_FORMATO_MILA ");
			
		}else{
			return null;
		}
        
        stringSQL.append(" AND ben.ID_BENEFICIARIO = :idBeneficiario ");
        return stringSQL.toString();
	}

}
