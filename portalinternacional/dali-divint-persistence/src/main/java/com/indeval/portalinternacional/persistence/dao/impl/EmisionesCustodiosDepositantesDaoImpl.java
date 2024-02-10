package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portalinternacional.middleware.servicios.dto.CustodiosDepositantesDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.EmisionDataBaseVO;
import com.indeval.portalinternacional.persistence.dao.EmisionesCustodiosDepositantesDao;

public class EmisionesCustodiosDepositantesDaoImpl extends BaseDaoHibernateImpl implements EmisionesCustodiosDepositantesDao {
														   
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(EmisionesCustodiosDepositantesDaoImpl.class);
    
	/**
	 * Realiza la consulta con paginaci&oacute;n de emisiones custodios depositantes
	 * @param dto
	 * @param paginaVO
	 * @return PaginaVO
	 */
    @SuppressWarnings("unchecked")
	public PaginaVO consultarEmisionesCustodiosDepositantes(CustodiosDepositantesDto dto, PaginaVO paginaVO) {
    	log.info("####### Entrando a consultarEmisionesCustodiosDepositantes()...");
    	
    	if (dto == null) {
			throw new IllegalArgumentException("El objeto de parametros para la consulta es nulo");
		}
    	
    	if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
			paginaVO.setTotalRegistros(countFindEmisionesCustodiosDepositantes(dto, paginaVO));
			if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
				paginaVO.setRegistros(consuEmisionesCustodiosDepositantes(dto, paginaVO));
			} else {
				paginaVO.setRegistros(new ArrayList<EmisionDataBaseVO>());
			}
		} else {
			// si debes de obtener la totalidad de la consulta
			List<EmisionDataBaseVO> registros = consuEmisionesCustodiosDepositantes(dto, paginaVO);
			paginaVO.setTotalRegistros(registros.size());
			paginaVO.setRegistros(registros);
		}
        return paginaVO;
    }
    
    
    /**
	 * Realiza la consulta con paginaci&oacute;n de emisiones custodios depositantes
	 * @param dto
	 * @param paginaVO
	 * @return List<EmisionDataBaseVO>
	 */
    @SuppressWarnings("unchecked")
   	private List<EmisionDataBaseVO> consuEmisionesCustodiosDepositantes(CustodiosDepositantesDto dto, final PaginaVO paginaVO) {
       	log.info("####### Entrando DAO a consuEmisionesCustodiosDepositantes()...");	
       	final StringBuffer sql = new StringBuffer();
           final ArrayList<Object> params = new ArrayList<Object>();
           final ArrayList<Type> tipos = new ArrayList<Type>();
        
           sql.append("SELECT new " + EmisionDataBaseVO.class.getName() + " (");
           sql.append("o.emision.isin, ");
           sql.append("o.emision.instrumento.claveTipoValor, ");
           sql.append("o.emision.emisora.clavePizarra, ");
           sql.append("o.emision.serie, ");
           sql.append("o.catBic.bicProd, ");
           sql.append("o.catBic.detalleCustodio, ");
           sql.append("p.bicDepLiq, ");
           sql.append("p.depLiq, ");
           sql.append("o.catBic.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion, ");
           sql.append("o.catBic.cuentaNombrada.institucion.folioInstitucion, ");
           sql.append("o.catBic.cuentaNombrada.cuenta) ");
           sql.append("FROM " + SicEmision.class.getName() + " o, " + SicDetalle.class.getName() + " p ");
           sql.append("WHERE o.estatusRegistro = 'VIGENTE' ");
           sql.append("AND o.catBic.idCatbic = p.catBic.idCatbic ");  
           sql.append("AND o.emision.idEstatusEmision = 3 ");  
           
           if (StringUtils.isNotBlank(dto.getTv())) {
           	sql.append(" AND o.emision.instrumento.claveTipoValor = ? ");
           	params.add(dto.getTv().toUpperCase());
               tipos.add(new StringType());
           }
           if (StringUtils.isNotBlank(dto.getEmisora())) {
           	sql.append(" AND o.emision.emisora.clavePizarra = ? ");
           	params.add(dto.getEmisora().toUpperCase());
               tipos.add(new StringType());
           }
           if (StringUtils.isNotBlank(dto.getSerie())) {
           	sql.append(" AND o.emision.serie = ? ");
           	params.add(dto.getSerie().toUpperCase());
               tipos.add(new StringType());
           }
           if (StringUtils.isNotBlank(dto.getIsin())) {
           	sql.append(" AND o.emision.isin = ? ");
           	params.add(dto.getIsin().toUpperCase());
               tipos.add(new StringType());
           }
           if (StringUtils.isNotBlank(dto.getBicProd())) {
           	sql.append(" AND o.catBic.bicProd LIKE ? ");
           	params.add("%" +dto.getBicProd().trim().toUpperCase() + "%");
               tipos.add(new StringType());
           }
           if (StringUtils.isNotBlank(dto.getDetalleCustodio())) {
           	sql.append(" AND o.catBic.detalleCustodio LIKE ? ");
           	params.add("%" +dto.getDetalleCustodio().trim().toUpperCase() + "%");
               tipos.add(new StringType());
           }
           
           sql.append("ORDER BY o.emision.instrumento.claveTipoValor, ");
           sql.append("o.emision.emisora.clavePizarra, ");
           sql.append("o.emision.serie, ");
           sql.append("p.depLiq ");
           
           if (log.isDebugEnabled()) {
           	log.debug("Query EmisionesCustodiosDepositantes -> " + sql.toString());
           }
                  
           List<EmisionDataBaseVO> retorno = (List<EmisionDataBaseVO>) this.getHibernateTemplate().execute(new HibernateCallback() {
   			public Object doInHibernate(Session session) throws HibernateException, SQLException {
   				Query query = session.createQuery(sql.toString());
   				query.setParameters(params.toArray(new Object[0]), tipos.toArray(new Type[0]));
   				
   				if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
   					query.setFirstResult(paginaVO.getOffset());
   					query.setMaxResults(paginaVO.getRegistrosXPag());
   				}		
   				
   				return query.list();
   			}
   		});

           return retorno;
       }
    
    /**
	 * Realiza la consulta de cuantos registros hay de emisiones custodios depositantes
	 * @param dto
	 * @param paginaVO
	 * @return int Cantidad de registros
	 */
	private int countFindEmisionesCustodiosDepositantes(CustodiosDepositantesDto dto, PaginaVO paginaVO) {
		Long retorno = 0L;
		
		log.info("####### Entrando DAO a count - FindEmisionesCustodiosDepositantes()...");
       	log.info("####### "+ dto.toString()); 
		
		final StringBuffer sql = new StringBuffer();
        final ArrayList<Object> params = new ArrayList<Object>();
        final ArrayList<Type> tipos = new ArrayList<Type>();
		
		sql.append("SELECT COUNT(*) ");
        sql.append("FROM " + SicEmision.class.getName() + " o, " + SicDetalle.class.getName() + " p ");
        sql.append("WHERE o.estatusRegistro = 'VIGENTE' ");
        sql.append("AND o.catBic.idCatbic = p.catBic.idCatbic ");  
        sql.append("AND o.emision.idEstatusEmision = 3 ");  
        
        if (StringUtils.isNotBlank(dto.getTv())) {
        	sql.append(" AND o.emision.instrumento.claveTipoValor = ? ");
        	params.add(dto.getTv().toUpperCase());
            tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(dto.getEmisora())) {
        	sql.append(" AND o.emision.emisora.clavePizarra = ? ");
        	params.add(dto.getEmisora().toUpperCase());
            tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(dto.getSerie())) {
        	sql.append(" AND o.emision.serie = ? ");
        	params.add(dto.getSerie().toUpperCase());
            tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(dto.getIsin())) {
        	sql.append(" AND o.emision.isin = ? ");
        	params.add(dto.getIsin().toUpperCase());
            tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(dto.getBicProd())) {
        	sql.append(" AND o.catBic.bicProd LIKE ? ");
        	params.add("%" +dto.getBicProd().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(dto.getDetalleCustodio())) {
        	sql.append(" AND o.catBic.detalleCustodio LIKE ? ");
        	params.add("%" +dto.getDetalleCustodio().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }
        
        if (log.isDebugEnabled()) {
           	log.debug("Query  -> " + sql.toString());
        }
		
		retorno = (Long) this.getHibernateTemplate().execute(new HibernateCallback() {
   			public Object doInHibernate(Session session) throws HibernateException, SQLException {
   				Query query = session.createQuery(sql.toString());
   				query.setParameters(params.toArray(new Object[0]), tipos.toArray(new Type[0]));   			  				
   				return query.uniqueResult();
   			}
   		});
				
		return retorno.intValue();
	}
    
    
	/**
	 * Consulta del proyecto Despachador de Consultas
	 * @param dto
	 * @param paginaVO
	 * @return List
	 */
    public List consultarEmisionesCustodiosDepositantes(final CustodiosDepositantesDto dto, final String estatusRegistro) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                StringBuilder strBuffQuery = new StringBuilder("SELECT new " + EmisionDataBaseVO.class.getName() + "(");
                strBuffQuery.append("o.emision.isin, ");
                strBuffQuery.append("o.emision.instrumento.claveTipoValor, ");
                strBuffQuery.append("o.emision.emisora.clavePizarra, ");
                strBuffQuery.append("o.emision.serie, ");
                strBuffQuery.append("o.catBic.bicProd, ");
                strBuffQuery.append("o.catBic.detalleCustodio, ");
                strBuffQuery.append("p.bicDepLiq, ");
                strBuffQuery.append("p.depLiq, ");
                strBuffQuery.append("o.catBic.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion, ");
                strBuffQuery.append("o.catBic.cuentaNombrada.institucion.folioInstitucion, ");
                strBuffQuery.append("o.catBic.cuentaNombrada.cuenta ) ");
                strBuffQuery.append("FROM SicEmision o, ");
                strBuffQuery.append("SicDetalle p ");
                strBuffQuery.append("WHERE o.estatusRegistro = 'VIGENTE' ");
                strBuffQuery.append("AND o.catBic.idCatbic = p.catBic.idCatbic ");
                strBuffQuery.append("AND o.emision.idEstatusEmision = 3 ");

                if (dto != null && StringUtils.isNotBlank(dto.getTv())) {
                    strBuffQuery.append(" AND o.emision.instrumento.claveTipoValor = :tipoValor ");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getEmisora())) {
                    strBuffQuery.append(" AND o.emision.emisora.clavePizarra = :claveEmisor ");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getSerie())) {
                    strBuffQuery.append(" AND o.emision.serie = :serie ");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getIsin())) {
                    strBuffQuery.append(" AND o.emision.isin = :isin ");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getBicProd())) {
                    strBuffQuery.append(" AND o.catBic.bicProd LIKE :bicProd ");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getDetalleCustodio())) {
                    strBuffQuery.append(" AND o.catBic.detalleCustodio LIKE :detalleCustodio ");
                }
                strBuffQuery.append("ORDER BY o.emision.instrumento.claveTipoValor, ");
                strBuffQuery.append("o.emision.emisora.clavePizarra, ");
                strBuffQuery.append("o.emision.serie ");
                
                if (log.isDebugEnabled()) {
                    log.debug("Query EmisionesCustodiosDepositantes - " + strBuffQuery.toString());
                }
                Query query = session.createQuery(strBuffQuery.toString());
               
                if (dto != null && StringUtils.isNotBlank(dto.getTv())) {
                    query.setString("tipoValor", dto.getTv());
                }
                if (dto != null && StringUtils.isNotBlank(dto.getEmisora())) {
                    query.setString("claveEmisor", dto.getEmisora());
                }
                if (dto != null && StringUtils.isNotBlank(dto.getSerie())) {
                    query.setString("serie", dto.getSerie());
                }
                if (dto != null && StringUtils.isNotBlank(dto.getIsin())) {
                    query.setString("isin", StringUtils.trim(dto.getIsin()));
                }
                if (dto != null && StringUtils.isNotBlank(dto.getBicProd())) {
                    query.setString("bicProd", "%" + StringUtils.upperCase(StringUtils.trim(dto.getBicProd())) + "%");
                }
                if (dto != null && StringUtils.isNotBlank(dto.getDetalleCustodio())) {
                    query.setString("detalleCustodio", "%" + StringUtils.upperCase(StringUtils.trim(dto.getDetalleCustodio())) + "%");
                }
                return query.list();

            }
        });
    }
    
    

}
