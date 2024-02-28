package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosTotalesInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.SaldoNombradaIntVO;
import com.indeval.portalinternacional.persistence.dao.ConsultaSaldoCustodiosDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class ConsultaSaldoCustodiosDaoImpl extends BaseDaoHibernateImpl implements ConsultaSaldoCustodiosDao {
    private static final Logger log = LoggerFactory.getLogger(ConsultaSaldoCustodiosDaoImpl.class);

    /**
     * Genera parametros para conciliacion
     * @param consultaSaldoCustodiosInDTO
     * @return DetachedCriteria
     */
    private DetachedCriteria paramsConsultaSaldoCustodios(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO){
        //Criteria
        DetachedCriteria criteria = DetachedCriteria.forClass(SaldoNombradaInt.class);
        // Realiza las uniones necesarias utilizando el método createAlias
        criteria.createAlias("divisa", "div");
        criteria.createAlias("boveda", "bov");
        criteria.createAlias("cuentaNombrada", "cue");
        criteria.createAlias("cue.institucion", "ins");
        // Agrega las restricciones utilizando el método add
        if(consultaSaldoCustodiosInDTO.getDivisaDali() != null ){
            BigInteger divisaId = new BigInteger(consultaSaldoCustodiosInDTO.getDivisaDali());
            criteria.add(Restrictions.eq("idDivisa", divisaId));
        }
        if(consultaSaldoCustodiosInDTO.getDivisaDali() != null ){
            BigInteger bovedaId = new BigInteger(consultaSaldoCustodiosInDTO.getBovedaDali());
            criteria.add(Restrictions.eq("idBoveda", bovedaId));
        }
        if(consultaSaldoCustodiosInDTO.getIdCuenta() != null ){
            BigInteger idCuenta = new BigInteger(consultaSaldoCustodiosInDTO.getIdCuenta());
            criteria.add(Restrictions.ne("idCuenta", idCuenta));
        }
        if(consultaSaldoCustodiosInDTO.getIdCuentaPopup() != null ){
            BigInteger idCuenta = new BigInteger(consultaSaldoCustodiosInDTO.getIdCuentaPopup());
            criteria.add(Restrictions.eq("idCuenta", idCuenta));
        }
        /*================PARAMETROS==========================*/
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    public ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodioTotales(final ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException {

        ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodiosTotalesInDTO =null;
        if(criteriosConsulta.getIdCuentaPopup() != null ){
            //Se genera en el popup
            consultaSaldoCustodiosTotalesInDTO = vSaldo_por_razon_socialTotal( criteriosConsulta);
        }else{
            //Principal pantalla
            consultaSaldoCustodiosTotalesInDTO = vSaldo_custodiosTotal( criteriosConsulta);
        }

        return consultaSaldoCustodiosTotalesInDTO == null ? new ConsultaSaldoCustodiosTotalesInDTO() : consultaSaldoCustodiosTotalesInDTO;
    }
    @Override
    public PaginaVO consultaSaldoCustodio(final ConsultaSaldoCustodiosInDTO criteriosConsulta,final PaginaVO paginaVO) throws BusinessException {

        PaginaVO newpaginaVO =null;
        if(criteriosConsulta.getIdCuentaPopup() != null ){
            //Se genera en el popup
            newpaginaVO = vSaldo_por_razon_social( criteriosConsulta, paginaVO);
        }else{
            //Principal pantalla
            newpaginaVO = vSaldo_custodios( criteriosConsulta, paginaVO);
        }

        return newpaginaVO;
    }

    private  PaginaVO vSaldo_custodios(final ConsultaSaldoCustodiosInDTO criteriosConsulta,final PaginaVO paginaVO) throws BusinessException {

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT v.div_descripcion AS divisa,\n" +
                "       v.bov_descripcion AS boveda, \n" +
                "       v.saldo_calculado AS idCuenta, \n" +
                "       v.saldo_calculado AS idSaldo, \n" +

                "       v.saldo_disponible AS saldoDisponible,\n" +
                "       v.saldo_no_disponible AS saldoNoDisponible \n" +

                "                                  FROM v_saldo_custodios v  WHERE 1 = 1 ");


        if(criteriosConsulta.getDivisaDali() != null  && !"-1".equalsIgnoreCase(criteriosConsulta.getDivisaDali()) ){
            sb.append("  AND v.id_div = :idDivisa");
        }
        if(criteriosConsulta.getBovedaDali() != null && !"-1".equalsIgnoreCase(criteriosConsulta.getBovedaDali() )){
            sb.append("  AND v.id_bov  = :idBoveda");
        }

        List<SaldoNombradaIntVO> resultados = (List<SaldoNombradaIntVO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);

                if(criteriosConsulta.getDivisaDali() != null  && !"-1".equalsIgnoreCase(criteriosConsulta.getDivisaDali()) ){
                    Integer divisaId = new Integer(criteriosConsulta.getDivisaDali());
                    query.setInteger("idDivisa", divisaId);
                }
                if(criteriosConsulta.getBovedaDali() != null && !"-1".equalsIgnoreCase(criteriosConsulta.getBovedaDali() )){
                    Integer bovedaId = new Integer(criteriosConsulta.getBovedaDali());
                    query.setInteger("idBoveda",bovedaId);
                }



                IntegerType it = new IntegerType();
                BigDecimalType bi = new BigDecimalType();
                DateType dt = new DateType();
                StringType st = new StringType();



                query.addScalar("divisa", st);
                query.addScalar("boveda", st);
                query.addScalar("idCuenta", bi);
                query.addScalar("idSaldo", bi);
                query.addScalar("saldoDisponible", bi);
                query.addScalar("saldoNoDisponible", bi);



                log.debug(query.toString());
                query.setResultTransformer(Transformers.aliasToBean(SaldoNombradaIntVO.class));

                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());

                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }

                return query.list();
            }
        });


        paginaVO.setRegistros(resultados);

        return paginaVO;
    }

    private  PaginaVO vSaldo_por_razon_social(final ConsultaSaldoCustodiosInDTO criteriosConsulta,final PaginaVO paginaVO) throws BusinessException {

        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT v.cuenta AS idCuenta,\n" +
                "       v.divisa AS divisa,\n" +
                "       v.boveda AS boveda,\n" +
                "        v.saldo_calculado AS idSaldo,\n" +
                "       v.saldo_disponible AS saldoDisponible,\n" +
                "       v.saldo_no_disponible AS saldoNoDisponible \n" +

                "FROM v_saldo_por_razon_social v   \n" +
                "                          WHERE  1 =1 \n" +
                "                               ");


        if(criteriosConsulta.getDivisaDali() != null  && !"-1".equalsIgnoreCase(criteriosConsulta.getDivisaDali()) ){
            sb.append("  AND v.id_divisa = :idDivisa");
        }
        if(criteriosConsulta.getBovedaDali() != null && !"-1".equalsIgnoreCase(criteriosConsulta.getBovedaDali() )){
            sb.append("  AND v.id_bov  = :idBoveda");
        }

        List<SaldoNombradaIntVO> resultados = (List<SaldoNombradaIntVO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);

                if(criteriosConsulta.getDivisaDali() != null  && !"-1".equalsIgnoreCase(criteriosConsulta.getDivisaDali()) ){
                    Integer divisaId = new Integer(criteriosConsulta.getDivisaDali());
                    query.setInteger("idDivisa", divisaId);
                }
                if(criteriosConsulta.getBovedaDali() != null && !"-1".equalsIgnoreCase(criteriosConsulta.getBovedaDali() )){
                    Integer bovedaId = new Integer(criteriosConsulta.getBovedaDali());
                    query.setInteger("idBoveda",bovedaId);
                }



                IntegerType it = new IntegerType();
                BigDecimalType bi = new BigDecimalType();
                DateType dt = new DateType();
                StringType st = new StringType();



                query.addScalar("divisa", st);
                query.addScalar("boveda", st);
                query.addScalar("idCuenta", bi);
                query.addScalar("idSaldo", bi);
                query.addScalar("saldoDisponible", bi);
                query.addScalar("saldoNoDisponible", bi);



                log.debug(query.toString());
                query.setResultTransformer(Transformers.aliasToBean(SaldoNombradaIntVO.class));

                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());

                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }

                return query.list();
            }
        });


        paginaVO.setRegistros(resultados);

        return paginaVO;
    }

    private  ConsultaSaldoCustodiosTotalesInDTO vSaldo_por_razon_socialTotal(final ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException {

        ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodiosTotalesInDTO = new ConsultaSaldoCustodiosTotalesInDTO();
        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT \n" +
                "       sum(v.saldo_calculado) AS totalSaldo,\n" +
                "       sum(v.saldo_disponible) AS totalDisponible,\n" +
                "       sum(v.saldo_no_disponible) AS totalNoDisponible \n" +
                " FROM v_saldo_por_razon_social v   \n" +
                "                          WHERE  1 =1 \n" +
                "                               ");
        ConsultaSaldoCustodiosTotalesInDTO resultados = (ConsultaSaldoCustodiosTotalesInDTO) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);
                BigDecimalType bi = new BigDecimalType();
                query.addScalar("totalSaldo", bi);
                query.addScalar("totalDisponible", bi);
                query.addScalar("totalNoDisponible", bi);
                query.setResultTransformer(Transformers.aliasToBean(ConsultaSaldoCustodiosTotalesInDTO.class));
                return query.uniqueResult();
            }
        });
        return resultados;
    }

    private  ConsultaSaldoCustodiosTotalesInDTO vSaldo_custodiosTotal(final ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException {

        ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodiosTotalesInDTO = new ConsultaSaldoCustodiosTotalesInDTO();
        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT \n" +
                "       sum(v.saldo_calculado) AS totalSaldo,\n" +
                "       sum(v.saldo_disponible) AS totalDisponible,\n" +
                "       sum(v.saldo_no_disponible) AS totalNoDisponible \n" +
                " FROM v_saldo_custodios v   \n" +
                "                          WHERE  1 =1 \n" +
                "                               ");
        ConsultaSaldoCustodiosTotalesInDTO resultados = (ConsultaSaldoCustodiosTotalesInDTO) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);
                BigDecimalType bi = new BigDecimalType();
                query.addScalar("totalSaldo", bi);
                query.addScalar("totalDisponible", bi);
                query.addScalar("totalNoDisponible", bi);
                query.setResultTransformer(Transformers.aliasToBean(ConsultaSaldoCustodiosTotalesInDTO.class));
                return query.uniqueResult();
            }
        });
        return resultados;
    }
}
