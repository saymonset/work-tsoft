/**
 * 2H Software - Bursatec
 * <p>
 * Sistema de Consulta de Estados de Cuenta
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.indeval.portaldali.persistence.dao.common.util.QueryUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EnsambladorDTOConsultasEfectivo;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAOV2;
import com.indeval.portaldali.persistence.model.RegContEfecControlada;
import com.indeval.portaldali.persistence.model.RegContEfecControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContEfecNombrada;
import com.indeval.portaldali.persistence.model.RegContEfecNombradaHistorico;
import com.indeval.portaldali.persistence.model.SaldoControlada;
import com.indeval.portaldali.persistence.model.SaldoControladaHistorico;
import com.indeval.portaldali.persistence.model.SaldoNombrada;
import com.indeval.portaldali.persistence.model.SaldoNombradaHistorico;

/**
 * Implementación del contrato para la consulta de registros contables para
 * saldos nombrados o controlados
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class EstadoCuentaSaldosEfectivoDAOV2Impl extends HibernateDaoSupport implements EstadoCuentaSaldosEfectivoDAOV2 {

    private static final Logger logger = LoggerFactory.getLogger(EstadoCuentaSaldosEfectivoDAOV2Impl.class);

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarDivisasDeSaldosControlados(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarDivisasDeSaldosControlados(final SaldoEfectivoDTO saldoEfectivo) {
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                logger.debug("Inicia buscarDivisasDeSaldosControlados Arg = " + saldoEfectivo);
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("SELECT DISTINCT sc.idDivisa FROM " + SaldoControlada.class.getName() + " sc ");
                query.append("WHERE 1=1 ");

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sc.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sc.cuentaControlada.institucion.folioInstitucion || " + "sc.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sc.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sc.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sc.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("query: " + query.toString());
                    logger.trace("params: " + params);
                }
                QueryUtil.imprimir(query.toString(), params);
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarDivisasDeSaldosControladosHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarDivisasDeSaldosControladosHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha) {
        logger.debug("Inicia buscarDivisasDeSaldosControladosHistorico Arg = " + saldoEfectivo + " | " + fecha);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("SELECT DISTINCT sc.idDivisa FROM " + SaldoControladaHistorico.class.getName() + " sc ");
                query.append("WHERE sc.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sc.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sc.cuentaControlada.institucion.folioInstitucion || " + "sc.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sc.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sc.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sc.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                
                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarDivisasDeSaldosNombrados(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarDivisasDeSaldosNombrados(final SaldoEfectivoDTO saldoEfectivo) {
        logger.debug("Inicia buscarDivisasDeSaldosNombrado Arg = " + saldoEfectivo);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer strQuery = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                strQuery.append("SELECT DISTINCT sn.idDivisa FROM " + SaldoNombrada.class.getName() + " sn ");
                strQuery.append("WHERE 1=1 ");

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    strQuery.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    strQuery.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    strQuery.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    strQuery.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    strQuery.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }

                //Se crea el query
                Query query =
                        session.createQuery(strQuery.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                //Se ejecuta el query y se obtienen los id como Long

                QueryUtil.imprimir(query.toString(),params);
               
                List<Long> idsDivisas = DTOAssembler.transformarListaBigIntegerEnLong(query.list());


                strQuery = null;
                params = null;
                tipos = null;
                query = null;

                return idsDivisas;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarDivisasDeSaldosNombradosHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarDivisasDeSaldosNombradosHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha) {
        logger.debug("Inicia buscarDivisasDeSaldosNombradosHistorico Arg = " + saldoEfectivo + " | " + fecha);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("SELECT DISTINCT sn.idDivisa FROM " + SaldoNombradaHistorico.class.getName() + " sn ");
                query.append("WHERE sn.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarRegistrosContablesDePosicionesControladas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeSaldosControladas(final SaldoEfectivoDTO criterio) {
        logger.debug("Inicia buscarRegistrosContablesDeSaldosControladas Arg = " + criterio);
        return (List<RegistroContableSaldoControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("FROM " + RegContEfecControlada.class.getName() + " reg " + " join fetch reg.saldo " + " WHERE ");
                query.append(" trunc (reg.fecha) BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoControlada(criterio, "reg.saldo", params, tipos));
                query.append(" ORDER BY reg.saldo.divisa.idDivisa, reg.saldo.boveda.idBoveda, reg.fecha, reg.idRegistroContable ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<RegContEfecControlada> registros = hQuery.list();
                List<RegistroContableSaldoControladaDTO> resultado = new ArrayList<RegistroContableSaldoControladaDTO>();
                for (RegContEfecControlada saldo : registros) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearRegContableSaldoControladaDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                //Coloca los objetos a null
                query = null;
                params = null;
                tipos = null;
                registros = null;
                return resultado;

            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarRegistrosContablesDePosicionesControladasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeSaldosControladasHistorico(final SaldoEfectivoDTO criterio) {
        logger.debug("Inicia buscarRegistrosContablesDeSaldosControladasHistorico Arg = " + criterio);
        return (List<RegistroContableSaldoControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("FROM " + RegContEfecControladaHistorico.class.getName() + " reg " + " join fetch reg.saldo " + " WHERE ");
                query.append(" reg.fechaCreacion BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoControlada(criterio, "reg.saldo", params, tipos));
                query.append(" ORDER BY reg.saldo.divisa.idDivisa, reg.saldo.boveda.idBoveda, reg.fecha, reg.idRegistroContable ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<RegContEfecControladaHistorico> registros = hQuery.list();
                List<RegistroContableSaldoControladaDTO> resultado = new ArrayList<RegistroContableSaldoControladaDTO>();
                for (RegContEfecControladaHistorico saldo : registros) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearRegContableSaldoControladaHistoricoDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                //Coloca los objetos a null
                query = null;
                params = null;
                tipos = null;
                registros = null;
                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarRegistrosContablesDePosicionesNombradas(SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeSaldosNombradas(final SaldoEfectivoDTO criterio) {
        logger.debug("Inicia buscarRegistrosContablesDeSaldosNombradas Arg = " + criterio);
        return (List<RegistroContableSaldoNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();

                query.append("FROM " + RegContEfecNombrada.class.getName() + " reg " + " join fetch reg.saldoNombrada "
                        + " join fetch reg.operacion "
                        + " join fetch reg.operacion.instruccion "
                        + " left outer join fetch reg.operacion.cuentaEfectivoTraspasante "
                        + " left outer join fetch reg.operacion.cuentaEfectivoTraspasante.tipoCuenta "
                        + " left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion "
                        + " left outer join fetch reg.operacion.cuentaEfectivoReceptor "
                        + " left outer join fetch reg.operacion.cuentaEfectivoReceptor.tipoCuenta "
                        + " left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion "
                        + " WHERE ");

                query.append(" trunc (reg.fecha) BETWEEN ? AND ? ");

                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());

                query.append(crearCriterioSaldoNombrada(criterio, "reg.saldoNombrada", params, tipos));

                query.append(" ORDER BY reg.saldoNombrada.divisa.idDivisa, reg.saldoNombrada.boveda.idBoveda, reg.fecha, reg.idRegistroContable ");

                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));

                List<RegContEfecNombrada> saldos = hQuery.list();
                List<RegistroContableSaldoNombradaDTO> resultado = new ArrayList<RegistroContableSaldoNombradaDTO>();

                for (RegContEfecNombrada saldo : saldos) {

                    resultado.add(EnsambladorDTOConsultasEfectivo.crearRegistroContableSaldoNombradaConDatosOperacion(saldo));
                }


                query = null;
                params = null;
                tipos = null;
                hQuery = null;
                saldos = null;

                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarRegistrosContablesDePosicionesNombradasHistorico(SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeSaldosNombradasHistorico(final SaldoEfectivoDTO criterio) {
        logger.debug("Inicia buscarRegistrosContablesDeSaldosNombradasHistorico Arg = " + criterio);
        return (List<RegistroContableSaldoNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
//				query.append(" FROM " + RegContEfecNombradaHistorico.class.getName() + " reg " + " join fetch reg.saldoNombrada ");
//				query.append(" join fetch reg.operacion ");
//				query.append(" join fetch reg.operacion.instruccion ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.tipoCuenta ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.tipoCuenta ");
//				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion ");
//				query.append(" WHERE ");
//				query.append(" reg.fechaCreacion = reg.operacion.fechaCreacion ");
//				query.append(" AND trunc(reg.fecha) BETWEEN ? AND ? ");

                query.append(" from " + RegContEfecNombradaHistorico.class.getName() + " reg ");
                query.append(" join fetch reg.saldoNombradaHistorico as snh ");
                query.append(" join fetch reg.operacion as onh ");
                query.append(" join fetch reg.operacion.instruccion as inst ");
                query.append(" left outer join fetch reg.operacion.tipoOperacion ");
                query.append(" left outer join fetch reg.operacion.instruccion.tipoInstruccion ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.tipoCuenta ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.tipoCuenta ");
                query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion ");
                query.append(" where ");
                query.append(" reg.fechaCreacion = onh.fechaCreacion ");
                query.append(" and reg.fechaCreacion = snh.fechaCreacion ");
                query.append(" and reg.fechaCreacion = inst.fechaCreacion ");
                query.append(" and reg.fechaCreacion between ? and ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoNombrada(criterio, "snh", params, tipos));
                query.append(" ORDER BY snh.divisa.idDivisa, snh.boveda.idBoveda, reg.fechaCreacion, reg.idRegistroContable ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<RegContEfecNombradaHistorico> registros = hQuery.list();
                List<RegistroContableSaldoNombradaDTO> resultado = new ArrayList<RegistroContableSaldoNombradaDTO>();
                for (RegContEfecNombradaHistorico saldo : registros) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearRegistroContableSaldoNombradaConDatosOperacion(saldo));
                }

                query = null;
                params = null;
                tipos = null;
                hQuery = null;
                registros = null;
                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarSaldoControlado(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
     *      java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<SaldoEfectivoDTO> buscarSaldoControlado(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas) {
        logger.debug("Inicia buscarSaldoControlado Arg = " + saldoEfectivo + " | " + idsDivisas);
        return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();
                query.append(" FROM " + SaldoControlada.class.getName() + " sc " + " join fetch sc.boveda ");
                query.append(" join fetch sc.divisa " + " join fetch sc.cuentaControlada " + " join fetch sc.cuentaControlada.institucion ");
                query.append(" join fetch sc.cuentaControlada.institucion.tipoInstitucion " + " join fetch sc.cuentaControlada.tipoCuenta ");
                query.append("WHERE 1=1 ");
                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sc.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }
                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sc.cuentaControlada.institucion.folioInstitucion || " + "sc.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }
                // Divisa
                if (idsDivisas != null && idsDivisas.size() > 0) {
                    query.append(" and sc.idDivisa in ( ");
                    for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext(); ) {
                        Long idDivisa = it.next();
                        query.append("?");
                        params.add(new BigInteger(String.valueOf(idDivisa)));
                        tipos.add(new BigIntegerType());
                        if (it.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(" ) ");
                }
                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sc.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }
                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sc.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                //Tipo de institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
                    query.append("AND sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion())));
                    tipos.add(new BigIntegerType());
                }
                query.append(" ORDER BY sc.divisa.idDivisa, sc.boveda.idBoveda ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<SaldoControlada> saldos = hQuery.list();
                List<SaldoEfectivoDTO> resultado = new ArrayList<SaldoEfectivoDTO>();
                for (SaldoControlada saldo : saldos) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearSaldoEfectivoControladaDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                //Coloca los objetos a null
                query = null;
                params = null;
                tipos = null;
                saldos = null;
                return resultado;
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarSaldoControladoHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
     *      java.util.List,java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<SaldoEfectivoDTO> buscarSaldoControladoHistorico(final SaldoEfectivoDTO saldoEfectivo,
                                                                 final List<Long> idsDivisas, final Date fecha) {
        logger.debug("Inicia buscarSaldoControladoHistorico Arg = " + saldoEfectivo + " | " + idsDivisas + " | " + fecha);
        return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();
                query.append(" FROM " + SaldoControladaHistorico.class.getName() + " sc " + " join fetch sc.boveda ");
                query.append(" join fetch sc.divisa " + " join fetch sc.cuentaControlada " + " join fetch sc.cuentaControlada.institucion ");
                query.append(" join fetch sc.cuentaControlada.institucion.tipoInstitucion " + " join fetch sc.cuentaControlada.tipoCuenta ");
                query.append("WHERE sc.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());
                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sc.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }
                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sc.cuentaControlada.institucion.folioInstitucion || " + "sc.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }
                // Divisa
                if (idsDivisas != null && idsDivisas.size() > 0) {
                    query.append(" and sc.idDivisa in ( ");
                    for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext(); ) {
                        Long idDivisa = it.next();
                        query.append("?");
                        params.add(new BigInteger(String.valueOf(idDivisa)));
                        tipos.add(new BigIntegerType());
                        if (it.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(" ) ");
                }
                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sc.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }
                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sc.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                //Tipo de institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
                    query.append("AND sc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion())));
                    tipos.add(new BigIntegerType());
                }
                query.append(" ORDER BY sc.divisa.idDivisa, sc.boveda.idBoveda ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<SaldoControladaHistorico> saldos = hQuery.list();
                List<SaldoEfectivoDTO> resultado = new ArrayList<SaldoEfectivoDTO>();
                for (SaldoControladaHistorico saldo : saldos) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearSaldoEfectivoControladaHistoricaDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                //Coloca los objetos a null
                query = null;
                params = null;
                tipos = null;
                saldos = null;
                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarSaldoNombrado(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
     *      java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<SaldoEfectivoDTO> buscarSaldoNombrado(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas) {
        logger.debug("buscarSaldoNombrado Arg = " + saldoEfectivo + " | " + idsDivisas);
        return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();
                query.append("FROM " + SaldoNombrada.class.getName() + " sn " + " join fetch sn.boveda "
                        + " join fetch sn.divisa " + " join fetch sn.cuentaNombrada " + " join fetch sn.cuentaNombrada.institucion "
                        + " join fetch sn.cuentaNombrada.institucion.tipoInstitucion " + " join fetch sn.cuentaNombrada.tipoCuenta ");
                query.append("WHERE 1=1 ");
                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (idsDivisas != null && idsDivisas.size() > 0) {
                    query.append(" and sn.idDivisa in ( ");
                    for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext(); ) {
                        Long idDivisa = it.next();
                        query.append("?");
                        params.add(new BigInteger(String.valueOf(idDivisa)));
                        tipos.add(new BigIntegerType());
                        if (it.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(" ) ");
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }

                //Tipo de institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
                    query.append("AND sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion())));
                    tipos.add(new BigIntegerType());
                }
                query.append(" ORDER BY sn.divisa.idDivisa, sn.boveda.idBoveda ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                List<SaldoNombrada> saldos = hQuery.list();
                List<SaldoEfectivoDTO> resultado = new ArrayList<SaldoEfectivoDTO>();
                for (SaldoNombrada saldo : saldos) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearSaldoEfectivoNombradaDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                query = null;
                params = null;
                tipos = null;
                hQuery = null;
                saldos = null;
                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarSaldoNombradoHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
     *      java.util.List,java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<SaldoEfectivoDTO> buscarSaldoNombradoHistorico(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas, final Date fecha) {
        logger.debug("buscarSaldoNombradoHistorico Arg = " + saldoEfectivo + " | " + idsDivisas + " | " + fecha);
        return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("FROM " + SaldoNombradaHistorico.class.getName() + " sn " + " join fetch sn.boveda "
                        + " join fetch sn.divisa " + " join fetch sn.cuentaNombrada " + " join fetch sn.cuentaNombrada.institucion "
                        + " join fetch sn.cuentaNombrada.institucion.tipoInstitucion " + " join fetch sn.cuentaNombrada.tipoCuenta ");

                query.append("WHERE sn.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (idsDivisas != null && idsDivisas.size() > 0) {
                    query.append(" and sn.idDivisa in ( ");
                    for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext(); ) {
                        Long idDivisa = it.next();
                        query.append("?");
                        params.add(new BigInteger(String.valueOf(idDivisa)));
                        tipos.add(new BigIntegerType());
                        if (it.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(" ) ");
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }

                //Tipo de institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
                    query.append("AND sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getClaveTipoInstitucion())));
                    tipos.add(new BigIntegerType());
                }

                query.append(" ORDER BY sn.divisa.idDivisa, sn.boveda.idBoveda ");
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));

                List<SaldoNombradaHistorico> saldos = hQuery.list();
                List<SaldoEfectivoDTO> resultado = new ArrayList<SaldoEfectivoDTO>();
                for (SaldoNombradaHistorico saldo : saldos) {
                    resultado.add(EnsambladorDTOConsultasEfectivo.crearSaldoEfectivoNombradaHistoricaDTO(saldo));
                }

                QueryUtil.imprimir(query.toString(),params);
                //Coloca los objetos a null
                query = null;
                params = null;
                tipos = null;
                saldos = null;
                return resultado;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarCuentasDeEfectivoControladas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarCuentasDeEfectivoControladas(final SaldoEfectivoDTO saldoEfectivo) {
        logger.debug("buscarCuentasDeEfectivoControladas Arg = " + saldoEfectivo);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("SELECT DISTINCT sn.cuentaControlada.idCuentaControlada FROM " + SaldoControlada.class.getName() + " sn ");
                query.append("WHERE 1=1 ");

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaControlada.institucion.folioInstitucion || " + "sn.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarCuentasDeEfectivoControladasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarCuentasDeEfectivoControladasHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha) {
        logger.debug("buscarCuentasDeEfectivoControladasHistorico Arg = " + saldoEfectivo + " | " + fecha);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();
                query.append("SELECT DISTINCT sn.cuentaControlada.idCuentaControlada FROM " + SaldoControladaHistorico.class.getName() + " sn ");
                query.append("WHERE sn.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());
                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }
                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaControlada.institucion.folioInstitucion || " + "sn.cuentaControlada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }
                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }
                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }
                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaControlada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarCuentasDeEfectivoNombradas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarCuentasDeEfectivoNombradas(final SaldoEfectivoDTO saldoEfectivo) {
        logger.debug("buscarCuentasDeEfectivoNombradas Arg = " + saldoEfectivo);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                StringBuffer strQuery = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                strQuery.append("SELECT DISTINCT sn.cuentaNombrada.idCuentaNombrada FROM " + SaldoNombrada.class.getName() + " sn ");
                strQuery.append("WHERE 1 = 1 ");

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    strQuery.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    strQuery.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    strQuery.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    strQuery.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    strQuery.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }

                List<Long> resultados = null;
                Query query =
                        session.createQuery(strQuery.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                resultados = DTOAssembler.transformarListaBigIntegerEnLong(query.list());
                QueryUtil.imprimir(query.toString(),params);
                strQuery = null;
                params = null;
                tipos = null;
                query = null;
                return resultados;
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.estadocuenta.core.application.dao.EstadoCuentaSaldosEfectivoDAO#buscarCuentasDeEfectivoNombradasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public List<Long> buscarCuentasDeEfectivoNombradasHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha) {
        logger.debug("buscarCuentasDeEfectivoNombradasHistorico Arg = " + saldoEfectivo + " | " + fecha);
        return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Object> tipos = new ArrayList<Object>();

                query.append("SELECT DISTINCT sn.cuentaNombrada.idCuentaNombrada FROM " + SaldoNombradaHistorico.class.getName() + " sn ");
                query.append("WHERE sn.fechaCreacion = ? ");
                params.add(fecha);
                tipos.add(new DateType());

                // Cuenta
                if (saldoEfectivo.getCuenta().getTipoNaturaleza() != null &&
                        !saldoEfectivo.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
                    query.append("AND sn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
                    params.add(saldoEfectivo.getCuenta().getTipoNaturaleza().getId());
                    tipos.add(new StringType());
                }

                if (saldoEfectivo.getCuenta() != null && !saldoEfectivo.getCuenta().getNumeroCuenta().equals("-1")) {
                    query.append("AND (sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                            + "|| sn.cuentaNombrada.institucion.folioInstitucion || " + "sn.cuentaNombrada.cuenta)" + " = ? ");
                    params.add(saldoEfectivo.getCuenta().getNumeroCuenta());
                    tipos.add(new StringType());
                }

                // Divisa
                if (saldoEfectivo.getDivisa() != null && saldoEfectivo.getDivisa().getId() > 0) {
                    query.append("AND sn.idDivisa = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getDivisa().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Boveda
                if (saldoEfectivo.getBoveda() != null && saldoEfectivo.getBoveda().getId() > 0) {
                    query.append("AND sn.idBoveda = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getBoveda().getId())));
                    tipos.add(new BigIntegerType());
                }

                // Institucion
                if (saldoEfectivo.getCuenta().getInstitucion().getId() > 0) {
                    query.append("AND sn.cuentaNombrada.institucion.idInstitucion = ? ");
                    params.add(new BigInteger(String.valueOf(saldoEfectivo.getCuenta().getInstitucion().getId())));
                    tipos.add(new BigIntegerType());
                }
                List<BigInteger> ids =
                        session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();
                List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                ids = null;
                return resultados;
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosControlada(com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO)
     */
    public long obtenerProyeccionDeRegistrosContablesDeSaldosControlada(final SaldoEfectivoDTO criterio) {
        logger.debug("obtenerProyeccionDeRegistrosContablesDeSaldosControlada Arg = " + criterio);
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("SELECT COUNT(*) FROM " + RegContEfecControlada.class.getName() + " reg " + " WHERE ");
                query.append(" trunc(reg.fecha) BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoControlada(criterio, "reg.saldo", params, tipos));
                Number resBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                return resBD != null ? resBD.longValue() : 0;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO)
     */
    public long obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(final SaldoEfectivoDTO criterio) {
        logger.debug("obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico Arg = " + criterio);
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("SELECT COUNT(*) FROM " + RegContEfecControladaHistorico.class.getName() + " reg  WHERE ");
                query.append(" reg.fechaCreacion BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoControlada(criterio, "reg.saldo", params, tipos));
                Number resBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();

                QueryUtil.imprimir(query.toString(),params);
                //Coloca a null los objetos
                query = null;
                params = null;
                tipos = null;
                return resBD != null ? resBD.longValue() : 0;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO)
     */
    public long obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(final SaldoEfectivoDTO criterio) {
        logger.debug("obtenerProyeccionDeRegistrosContablesDeSaldosNombrada Arg = " + criterio);
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("SELECT COUNT(*) FROM " + RegContEfecNombrada.class.getName() + " reg WHERE ");
                query.append(" trunc(reg.fecha) BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(crearCriterioSaldoNombrada(criterio, "reg.saldoNombrada", params, tipos));
                Number resBD =
                        (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();

                QueryUtil.imprimir(query.toString(),params);
                query = null;
                params = null;
                tipos = null;
                return resBD != null ? resBD.longValue() : 0;
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO)
     */
    public long obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(final SaldoEfectivoDTO criterio) {
        logger.debug("obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico Arg = " + criterio);
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer query = new StringBuffer();
                ArrayList<Object> params = new ArrayList<Object>();
                ArrayList<Type> tipos = new ArrayList<Type>();
                query.append("SELECT COUNT(*) FROM " + RegContEfecNombradaHistorico.class.getName() + " reg WHERE ");
                query.append(" reg.fechaCreacion BETWEEN ? AND ? ");
                params.add(new Timestamp(criterio.getFechaInicial().getTime()));
                tipos.add(new DateType());
                params.add(new Timestamp(criterio.getFechaFinal().getTime()));
                tipos.add(new DateType());
                query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
                query.append(crearCriterioSaldoNombrada(criterio, "reg.saldoNombradaHistorico", params, tipos));
                Number resBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();
                QueryUtil.imprimir(query.toString(),params);
                query = null;
                params = null;
                tipos = null;
                return resBD != null ? resBD.longValue() : 0;
            }
        });

    }

    /**
     * Crea las condiciones de búsqueda para un query de saldos nombrados
     *
     * @param criterio Criterio de búsqueda
     * @param alias    Alias de la tabla de saldo
     * @param params   parámetros a utilizar
     * @param tipos    Tipos de datos de Hibernate a utilizar
     * @return Porción del query correspondiente a las condiciones
     */
    private String crearCriterioSaldoNombrada(SaldoEfectivoDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
        logger.debug("obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico Arg = "
                + criterio + " | " + alias + " | " + params + " | " + tipos);

        StringBuffer query = new StringBuffer();
        // Cuenta
        if (criterio.getCuenta().getTipoNaturaleza() != null &&
                !criterio.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
            query.append("AND " + alias + ".cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
            params.add(criterio.getCuenta().getTipoNaturaleza().getId());
            tipos.add(new StringType());
        }

        if (criterio.getCuenta() != null && !criterio.getCuenta().getNumeroCuenta().equals("-1")) {
            query.append("AND (" + alias + ".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion "
                    + "|| " + alias + ".cuentaNombrada.institucion.folioInstitucion || " + "" + alias + ".cuentaNombrada.cuenta)" + " = ? ");
            params.add(criterio.getCuenta().getNumeroCuenta());
            tipos.add(new StringType());
        }

        // Divisa
        if (criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
            query.append("AND " + alias + ".idDivisa = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
            tipos.add(new BigIntegerType());
        }

        // Boveda
        if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
            query.append("AND " + alias + ".idBoveda = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
            tipos.add(new BigIntegerType());
        }

        // Institucion
        if (criterio.getCuenta().getInstitucion().getId() > 0) {
            query.append("AND " + alias + ".cuentaNombrada.institucion.idInstitucion = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
            tipos.add(new BigIntegerType());
        }
        return query.toString();
    }


    /**
     * Crea las condiciones de búsqueda para un query de saldos controlados
     *
     * @param criterio Criterio de búsqueda
     * @param alias    Alias de la tabla de saldo
     * @param params   parámetros a utilizar
     * @param tipos    Tipos de datos de Hibernate a utilizar
     * @return Porción del query correspondiente a las condiciones
     */
    private String crearCriterioSaldoControlada(SaldoEfectivoDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
        logger.debug("crearCriterioSaldoControlada Arg = "
                + criterio + " | " + alias + " | " + params + " | " + tipos);

        StringBuffer query = new StringBuffer();

        // Cuenta
        if (criterio.getCuenta().getTipoNaturaleza() != null &&
                !criterio.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
            query.append("AND " + alias + ".cuentaControlada.tipoCuenta.naturalezaContable = ? ");
            params.add(criterio.getCuenta().getTipoNaturaleza().getId());
            tipos.add(new StringType());
        }

        if (criterio.getCuenta() != null && !criterio.getCuenta().getNumeroCuenta().equals("-1")) {
            query.append("AND (" + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion "
                    + "|| " + alias + ".cuentaControlada.institucion.folioInstitucion || " + alias + ".cuentaControlada.cuenta)" + " = ? ");
            params.add(criterio.getCuenta().getNumeroCuenta());
            tipos.add(new StringType());
        }

        // Divisa
        if (criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
            query.append("AND " + alias + ".idDivisa = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
            tipos.add(new BigIntegerType());
        }

        // Boveda
        if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
            query.append("AND " + alias + ".idBoveda = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
            tipos.add(new BigIntegerType());
        }

        // Institucion
        if (criterio.getCuenta().getInstitucion().getId() > 0) {
            query.append("AND " + alias + ".cuentaControlada.institucion.idInstitucion = ? ");
            params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
            tipos.add(new BigIntegerType());
        }

        return query.toString();
    }


}
