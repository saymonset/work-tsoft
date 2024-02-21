package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.persistence.dao.ConsultaSaldoCustodiosDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class ConsultaSaldoCustodiosDaoImpl extends BaseDaoHibernateImpl implements ConsultaSaldoCustodiosDao {



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

    @Override
    public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO, PaginaVO pagina) throws BusinessException {
        try {
            if(pagina == null){
                pagina = new PaginaVO();
            }
            final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;
            final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;

            final DetachedCriteria criteria=paramsConsultaSaldoCustodios(consultaSaldoCustodiosInDTO);

            //Callback
            HibernateCallback hibernateCallback = new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Criteria crit = criteria.getExecutableCriteria(session);
                    if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
                        crit.setMaxResults(regxpag);
                        crit.setFetchSize(regxpag);
                        crit.setFirstResult(offset);
                    }
                    return crit.list();
                }
            };
            //Ejecucion
            @SuppressWarnings("unchecked")
            List<SaldoNombradaInt> saldoCustodios = (List<SaldoNombradaInt>)this.getHibernateTemplate().executeFind(hibernateCallback);
            if(saldoCustodios != null){
                pagina.setRegistros(saldoCustodios);
            }
            final DetachedCriteria criteriaSum = paramsConsultaSaldoCustodios(consultaSaldoCustodiosInDTO);
            Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    criteriaSum.setProjection(Projections.rowCount());
                    Criteria crit = criteriaSum.getExecutableCriteria(session);
                    return crit.uniqueResult();
                }

            });
            pagina.setTotalRegistros(tam);
        }catch (Exception e){
            System.out.println("e.toString() = " + e.toString());
        }

        return pagina;
    }
}
