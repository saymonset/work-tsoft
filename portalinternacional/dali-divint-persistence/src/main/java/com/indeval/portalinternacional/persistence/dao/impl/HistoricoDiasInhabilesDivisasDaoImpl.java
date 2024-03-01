// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.dao.HistoricoDiasInhabilesDivisasDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoricoDiasInhabilesDivisasDaoImpl extends BaseDaoHibernateImpl implements HistoricoDiasInhabilesDivisasDao {
    private static final Logger log = LoggerFactory.getLogger(HistoricoDiasInhabilesDivisasDaoImpl.class);

    Integer[] estadosValidos = {1, 2};



    public List<HistoricoDiasInhabilesDivisas> obtenerTodo(final String creador, final Integer anio) {
        log.debug("obtenerTodosHistoricoDiasInhabilesDivisas");
        return (List<HistoricoDiasInhabilesDivisas>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                final StringBuffer stringSQL = new StringBuffer();
                stringSQL.append(" FROM HistoricoDiasInhabilesDivisas hdid ");
                stringSQL.append(" WHERE hdid.estatus IN (:estados) ");
                if (creador != null) {
                    stringSQL.append(" AND hdid.creador= :creador ");
                }
                if (anio != null) {
                    stringSQL.append(" AND hdid.idHistorico IN( ");
                    stringSQL.append("SELECT DISTINCT(did.idHistoricoDiasInhabilesDivisas) ");
                    stringSQL.append("FROM  DiasInhabilesDivisas did ");
                    stringSQL.append("WHERE EXTRACT(YEAR FROM did.diaInhabil) = :anio)");
                }
                log.debug(stringSQL.toString());

                final Query query = session.createQuery(stringSQL.toString());
                query.setParameterList("estados", estadosValidos);
                if (creador != null) {
                    query.setString("creador", creador);
                }
                if (anio != null) {
                    query.setInteger("anio", anio);
                }

                return query.list();
            }
        });
    }

    public Long ultimoID() {

        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT MAX(HDI.idHistorico) ");
        sb.append(" FROM  HistoricoDiasInhabilesDivisas HDI");

        log.debug("Query : [" + sb.toString() + "]");

        Long ultimoID = (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.uniqueResult();
            }
        });

        log.debug("Ultimo ID: " + ultimoID);

        return ultimoID;
    }

    @Override
    public void updateHistorico(final Long idHistorico, final Integer cambioEstado, String usuarioChecker) {
        log.debug("updateHistorico" +
                ":: idHistorico [" + idHistorico + "]" +
                ":: cambioEstado [" + cambioEstado + "]" +
                ":: usuarioChecker [" + usuarioChecker + "]");
        HistoricoDiasInhabilesDivisas historico = (HistoricoDiasInhabilesDivisas) getByPk(HistoricoDiasInhabilesDivisas.class, idHistorico);
        if (historico != null) {
            historico.setUsuarioChecker(usuarioChecker);
            historico.setEstatus(cambioEstado);
            historico.setFechaUltModificacion(new Date());
            update(historico);

            final StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE  " + DiasInhabilesDivisas.class.getName() + " did \n");
            sb.append(" SET    did.estatus = :estatusCambio, \n");
            sb.append("        did.fechaUltModificacion = :fechaUltModificacion \n");
            sb.append(" WHERE  did.estatus IN (:estados)  \n");
            sb.append("    AND did.idHistoricoDiasInhabilesDivisas = :idHistorico");

            int registrosModificados = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(final Session session) throws HibernateException,
                        SQLException {
                    Query query = session.createQuery(sb.toString());
                    query.setParameterList("estados", estadosValidos);
                    query.setLong("estatusCambio", cambioEstado);
                    query.setDate("fechaUltModificacion", new Date());
                    query.setLong("idHistorico", idHistorico);
                    return query.executeUpdate();
                }
            });

            log.debug("Registros actualizados :: " + registrosModificados);
        }
    }


}