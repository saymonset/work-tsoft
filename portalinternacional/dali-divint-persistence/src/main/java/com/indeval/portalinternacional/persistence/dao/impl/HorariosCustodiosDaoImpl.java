// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import com.indeval.portalinternacional.persistence.dao.HorariosCustodiosDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HorariosCustodiosDaoImpl extends BaseDaoHibernateImpl implements HorariosCustodiosDao {
    private static final Logger log = LoggerFactory.getLogger(HorariosCustodiosDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<HorariosCustodiosDto> findAllByIdDivisa(final Integer idDivisa) {
        return (List<HorariosCustodiosDto>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(HorariosCustodios.class);

                if (idDivisa != null) {
                    criteria.add(Expression.in("idDivisa", new Object[]{idDivisa, idDivisa}));

                    Iterator horariosCustodiosBO = criteria.list().iterator();
                    List<HorariosCustodiosDto> resultado = new ArrayList<HorariosCustodiosDto>();
                    HorariosCustodiosDto horariosCustodiosDto = null;
                    while (horariosCustodiosBO.hasNext()) {
                        HorariosCustodios horariosCustodios = (HorariosCustodios) horariosCustodiosBO.next();
                        horariosCustodiosDto = DTOAssembler.crearHorariosCustodiosDTO(horariosCustodios);
                        resultado.add(horariosCustodiosDto);
                    }

                    return resultado;
                }
                return null;

            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getHorarioInicialYHorarioFinalPorIdCustodio(final Integer idCustodio) {
        return (List<Object[]>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(HorariosCustodios.class);

                criteria.add(Restrictions.eq("idCustodio", idCustodio));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("horarioInicial"))
                        .add(Projections.property("horarioFinal")));

                List<Object[]> resultados = criteria.list();

                return resultados;
            }
        });
    }

    @Override
    public HorariosCustodiosDto salvarHorarioCustodio(HorariosCustodiosDto horariosCustodios) {
        log.info("salvarHorarioCustodio :: " + horariosCustodios);
        HorariosCustodios cHorariosCustodios = new HorariosCustodios();
        int id = ultimoID().intValue() + 1;
        cHorariosCustodios.setIdHorariosCustodios(id);
        cHorariosCustodios.setIdDivisa(horariosCustodios.getIdDivisa());
        cHorariosCustodios.setIdCustodio(horariosCustodios.getIdCustodio());
        cHorariosCustodios.setHorarioInicial(horariosCustodios.getHorarioInicial());
        cHorariosCustodios.setHorarioFinal(horariosCustodios.getHorarioFinal());
        cHorariosCustodios.setFechaCreacion(horariosCustodios.getFechaCreacion());
        cHorariosCustodios.setFechaUltModificacion(horariosCustodios.getFechaUltModificacion());
        cHorariosCustodios.setEstatus(horariosCustodios.getEstatus());
        cHorariosCustodios.setCreador(horariosCustodios.getCreador());
        log.debug("Salvar :: " + cHorariosCustodios);

        save(cHorariosCustodios);

        cHorariosCustodios = (HorariosCustodios) getByPk(
                HorariosCustodios.class, cHorariosCustodios.getIdHorariosCustodios());
        log.debug("Horario Custodio Registrado :: " + cHorariosCustodios);

        HorariosCustodiosDto horariosCustodiosDto = DTOAssembler.crearHorariosCustodiosDTO(cHorariosCustodios);
        log.debug("Horario Custodio Cargado :: " + horariosCustodiosDto.toString());

        Divisa divisa = obtenerDivisa(horariosCustodiosDto.getIdDivisa());
        Custodio custodio = obtenerCustodio(horariosCustodiosDto.getIdCustodio());
        log.debug(divisa.getIdDivisa() + ".- " + divisa.getClaveAlfabetica() + " :: " + divisa.getDescripcion());
        log.debug(custodio.getId() + ".- " + custodio.getNombreCorto() + " :: " + custodio.getDescripcion());

        horariosCustodiosDto.setNombreDivisa(divisa.getClaveAlfabetica());
        horariosCustodiosDto.setNombreCustodio(custodio.getDescripcion());

        return horariosCustodiosDto;
    }

    public Divisa obtenerDivisa(int idDivisa) {
        log.debug("obtenerDivisa :: " + idDivisa);
        return (Divisa) getByPk(Divisa.class, new Long(idDivisa));
    }

    public Custodio obtenerCustodio(int idCustodio) {
        log.debug("obtenerCustodio :: " + idCustodio);
        return (Custodio) getByPk(Custodio.class, idCustodio);
    }

    public Integer ultimoID() {

        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT MAX(HC.idHorariosCustodios) ");
        sb.append(" FROM  HorariosCustodios HC");

        log.debug("Query : [" + sb.toString() + "]");

        Integer ultimoID = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.uniqueResult();
            }
        });

        log.debug("Ultimo ID: " + ultimoID);

        return ultimoID;
    }

    @Override
    public PaginaVO getHorariosCustodios(
            final CriteriosConsultaHorariosCustodiosVO criteriosConsulta, final PaginaVO paginaVO) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT HC.ID_HORARIOS_CUSTODIO idHorariosCustodios, HC.ID_DIVISA idDivisa, HC.ID_CUSTODIO idCustodio, \n");
        sb.append("       SUBSTR(HC.HORARIO_INICIAL,0,5) horarioInicial, SUBSTR(HC.HORARIO_FINAL,0,5) horarioFinal,\n");
        sb.append("       HC.CREADOR creador, HC.FECHA_CREACION fechaCreacion, HC.ESTATUS estatus, HC.USUARIO_CHEKER usuarioChecker, \n");
        sb.append("       C.NOMBRE_CORTO custodioNombre, C.DESCRIPCION custodioDescripcion,\n");
        sb.append("       D.CLAVE_ALFABETICA divisaClave, D.DESCRIPCION divisaDescripcion \n");
        sb.append("FROM C_HORARIOS_CUSTODIOS HC, \n");
        sb.append("     C_CUSTODIO C, C_DIVISA D\n");
        sb.append("WHERE HC.ID_CUSTODIO = C.ID_CUSTODIO\n");
        sb.append("  AND HC.ID_DIVISA = D.ID_DIVISA");

        if (criteriosConsulta.getFechaCreacion() != null) {
            sb.append("  AND TO_CHAR(HC.FECHA_CREACION, 'YYYY-MM-DD') = TO_CHAR(:fechaCreacion, 'YYYY-MM-DD') ");
        }
        if (criteriosConsulta.getIdDivisa() != null) {
            sb.append("  AND HC.ID_DIVISA = :idDivisa");
        }
        if (criteriosConsulta.getIdCustodio() != null) {
            sb.append("  AND HC.ID_CUSTODIO = :idCustodio ");
        }

        List<HorariosCustodiosVO> resultados = (List<HorariosCustodiosVO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);

                if (criteriosConsulta.getFechaCreacion() != null) {
                    query.setDate("fechaCreacion", criteriosConsulta.getFechaCreacion());
                }
                if (criteriosConsulta.getIdDivisa() != null) {
                    query.setInteger("idDivisa", criteriosConsulta.getIdDivisa());
                }
                if (criteriosConsulta.getIdCustodio() != null) {
                    query.setInteger("idCustodio", criteriosConsulta.getIdCustodio());
                }

                IntegerType it = new IntegerType();
                DateType dt = new DateType();
                StringType st = new StringType();


                query.addScalar("idHorariosCustodios", it);
                query.addScalar("idDivisa", it);
                query.addScalar("idCustodio", it);
                query.addScalar("horarioInicial", st);
                query.addScalar("horarioFinal", st);
                query.addScalar("creador", st);
                query.addScalar("fechaCreacion", dt);
                query.addScalar("estatus", it);
                query.addScalar("usuarioChecker", st);
                query.addScalar("custodioNombre", st);
                query.addScalar("custodioDescripcion", st);
                query.addScalar("divisaClave", st);
                query.addScalar("divisaDescripcion", st);


                log.debug(query.toString());
                log.debug(criteriosConsulta.toString());
                query.setResultTransformer(Transformers.aliasToBean(HorariosCustodiosVO.class));

                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());

                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }

                return query.list();
            }
        });

        for (HorariosCustodiosVO horariosCustodiosVO : resultados) {
            log.debug(horariosCustodiosVO.toString());
        }

        paginaVO.setRegistros(resultados);

        return paginaVO;
    }

    @Override
    public HorariosCustodiosDto updateHorariosCustodios(Integer idHorarioCustodio, Integer cambioEstado, String usuarioChecker) {
        log.debug("updateHorariosCustodios " +
                ":: idHorarioCustodio [" + idHorarioCustodio + "]" +
                ":: cambioEstado [" + cambioEstado + "]" +
                ":: usuarioChecker [" + usuarioChecker + "]");
        HorariosCustodios horariosCustodios = (HorariosCustodios) getByPk(HorariosCustodios.class, idHorarioCustodio);
        if (horariosCustodios != null) {
            horariosCustodios.setUsuarioChecker(usuarioChecker);
            horariosCustodios.setEstatus(cambioEstado);
            horariosCustodios.setFechaUltModificacion(new Date());
            update(horariosCustodios);

            horariosCustodios = (HorariosCustodios) getByPk(HorariosCustodios.class, idHorarioCustodio);
            HorariosCustodiosDto horariosCustodiosDto = DTOAssembler.crearHorariosCustodiosDTO(horariosCustodios);
            log.debug("Horario Custodio Actualizado :: " + horariosCustodiosDto.toString());
            return horariosCustodiosDto;
        }
        return null;
    }

}
