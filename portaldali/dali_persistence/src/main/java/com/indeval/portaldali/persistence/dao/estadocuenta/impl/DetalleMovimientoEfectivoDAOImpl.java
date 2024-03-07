/**
 * 2H Software - Bursatec
 * <p>
 * Sistema de Consulta de Estados de Cuenta
 * <p>
 * Dec 28, 2007
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.persistence.dao.common.util.QueryUtil;
import com.indeval.portaldali.persistence.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO;


/**
 * Implementacion de la interface de acceso a la base de datos para las
 * consultas de los detalles de movimientos de efectivo
 *
 * @author Pablo Julian Balderas Mendez
 */
public class DetalleMovimientoEfectivoDAOImpl extends HibernateDaoSupport implements DetalleMovimientoEfectivoDAO {

    private final Logger logger = LoggerFactory.getLogger(DetalleMovimientoEfectivoDAOImpl.class);

    /**
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO#consultarDetalleMovimientoEfectivo(long, boolean, long) 
     */
    public DetalleMovimientoEfectivoDTO consultarDetalleMovimientoEfectivo(
            long idRegistroContable, final boolean isHistorico, long idFolioInstLiquidacion) {
        try {
            logger.debug("consultarDetalleMovimientoEfectivo :: " +
                    "[idRegistroContable: " + idRegistroContable + "] | " +
                    "[isHistorico: " + isHistorico + "] | " +
                    "[idFolioInstLiquidacion: " + idFolioInstLiquidacion + "] ");
            
            final StringBuffer query = new StringBuffer();
            final ArrayList<Object> params = new ArrayList<Object>();
            final ArrayList<Object> tipos = new ArrayList<Object>();
            String nombreTabla = (isHistorico) ? RegContEfecNombradaHistorico.class.getName() : RegContEfecNombrada.class.getName();

            query.append("SELECT registro, registro.operacion.instruccion.instruccionEfectivo.tipoRetiro.nombreCorto, ");
            query.append(" registro.operacion.instruccion.instruccionEfectivo.idTipoPago, ");
            query.append(" registro.operacion.instruccion.instruccionEfectivo.folioInstLiquidacion, ");
            query.append(" registro.operacion.instruccion.instruccionEfectivo.concepto, ");
            query.append(" registro.operacion.instruccion.instruccionEfectivo.referenciaOperacion ");
            query.append(" FROM " + nombreTabla + " registro ");
            query.append(" join fetch registro.operacion ");
            query.append(" join fetch registro.operacion.tipoOperacion ");
            query.append(" join fetch registro.operacion.instruccion ");
            query.append(" join fetch registro.operacion.instruccion.tipoInstruccion ");
            query.append(" left outer join registro.operacion.instruccion.instruccionEfectivo ");
            query.append(" left join registro.operacion.instruccion.instruccionEfectivo.tipoRetiro ");

            if (isHistorico) {
                query.append(" join fetch registro.saldoNombradaHistorico ");
                query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada ");
                query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.institucion ");
                query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.institucion.tipoInstitucion ");
                query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.tipoCuenta ");
                query.append(" join fetch registro.saldoNombradaHistorico.boveda ");
                query.append(" join fetch registro.saldoNombradaHistorico.divisa ");
            } else {
                query.append(" join fetch registro.saldoNombrada ");
                query.append(" join fetch registro.saldoNombrada.cuentaNombrada ");
                query.append(" join fetch registro.saldoNombrada.cuentaNombrada.institucion ");
                query.append(" join fetch registro.saldoNombrada.cuentaNombrada.institucion.tipoInstitucion ");
                query.append(" join fetch registro.saldoNombrada.cuentaNombrada.tipoCuenta ");
                query.append(" join fetch registro.saldoNombrada.boveda ");
                query.append(" join fetch registro.saldoNombrada.divisa ");
            }

            query.append(" WHERE ");

            query.append(" registro.idRegistroContable = ? ");
            params.add(new BigInteger(String.valueOf(idRegistroContable)));
            tipos.add(new BigIntegerType());

            if (isHistorico) {
                query.append(" AND registro.fechaCreacion = registro.operacion.fechaCreacion ");
            }

            return (DetalleMovimientoEfectivoDTO) getHibernateTemplate().execute(new HibernateCallback() {
                @SuppressWarnings("rawtypes")
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO = null;

                    Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                    QueryUtil.imprimir(hQuery.getQueryString(), params);
                    List listaResultados = hQuery.list();


                    if (listaResultados != null && listaResultados.size() > 0) {
                        Object[] array = (Object[]) listaResultados.get(0);
                        if (isHistorico) {
                            logger.info("Termino el query: [" + array[0] + "-" + array[1] + "-" + array[2] + "]");
                            detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
                                    (RegContEfecNombradaHistorico) array[0],
                                    (String) array[1],
                                    (Integer) array[2],
                                    (String) array[3],
                                    (String) array[4]);
                        } else {
                            detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
                                    (RegContEfecNombrada) array[0],
                                    (String) array[1],
                                    (Integer) array[2],
                                    (String) array[3],
                                    (String) array[4]);
                        }
                    }

                    return detalleMovimientoEfectivoDTO;
                }
            });
        } catch (Exception ex) {
            return consultaDetalleEfectivoPorRegitroFolio(
                    idRegistroContable, idFolioInstLiquidacion, isHistorico);
        }
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO#buscarIdRegistroContableValorNombradoDeOperacionDVP(long,
     * long, boolean)
     */
    @SuppressWarnings("unchecked")
    public long buscarIdRegistroContableValorNombradoDeOperacionDVP(
            long idRegistroContable, long idInstitucion, final boolean isHistorico) {
                logger.debug("buscarIdRegistroContableValorNombradoDeOperacionDVP");
        
        final StringBuffer query = new StringBuffer();
        final ArrayList<Object> params = new ArrayList<Object>();
        final ArrayList<Object> tipos = new ArrayList<Object>();
        String selectFromTabla1 = (isHistorico)
                ? "	SELECT reg.idRegistroContableHistorico FROM " + RegContValNombradaHistorico.class.getName()
                : "	SELECT reg.idRegistroContable FROM " + RegContValNombrada.class.getName();
        String nombreTabla2 = (isHistorico) ? RegContEfecNombradaHistorico.class.getName() : RegContEfecNombrada.class.getName();

        query.append("SELECT (");
        query.append(selectFromTabla1 + " reg ");
        query.append("	WHERE reg.idOperacion = regc.idOperacion ");
        if (isHistorico) {
            query.append("	and reg.fechaCreacion = regc.fechaCreacion ");
            query.append("	and reg.posicion.cuentaNombrada.idInstitucion = regc.saldoNombradaHistorico.cuentaNombrada.idInstitucion ");
        } else {
            query.append("	and reg.posicion.cuentaNombrada.idInstitucion = regc.saldoNombrada.cuentaNombrada.idInstitucion ");
        }
        query.append("	and rownum<2) from " + nombreTabla2 + " regc ");
        query.append(" where regc.idRegistroContable = ?");

        params.add(new BigInteger(String.valueOf(idRegistroContable)));
        tipos.add(new BigIntegerType());

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                long idRegistro = 0;
                Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                QueryUtil.imprimir(hQuery.getQueryString(), params);
                List<BigInteger> listaResultados = hQuery.list();
                if (listaResultados != null && listaResultados.size() > 0) {
                    idRegistro = listaResultados.get(0) != null ? listaResultados.get(0).longValue() : 0;
                }

                return idRegistro;
            }
        });
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO#consultaDetalleEfectivoPorRegitroFolio(long, long, boolean)
     */
    @Override
    public DetalleMovimientoEfectivoDTO consultaDetalleEfectivoPorRegitroFolio(
            long idRegistroContable, long idFolioInstLiquidacion, boolean isHistorico) {
                logger.debug("buscarInformacionRegistro :: " + idRegistroContable + " | " + idFolioInstLiquidacion + " | " + isHistorico);
                return obtenerDetalleDB(idRegistroContable, idFolioInstLiquidacion, isHistorico);
    }


    private DetalleMovimientoEfectivoDTO obtenerDetalleDB(
            long idRegistroContable, long idFolioInstLiquidacion, boolean isHistorico) {
        logger.debug("Obtener Resultados:: " +
                "[idRegistroContable - " + idRegistroContable + "] :: " +
                "[idFolioInstLiquidacion - " + idFolioInstLiquidacion + "] ");

        String query = cargaQueryConsultaDetalle(idRegistroContable, idFolioInstLiquidacion, isHistorico);

        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {

                DetalleDB detalleDB = cargaDetalleDB(isHistorico, row);

                logger.trace(detalleDB.toString());

                DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO = null;


                if (isHistorico) {
                    RegContEfecNombradaHistorico resultado = cargaRegContEfecNombradaHistorico(detalleDB, isHistorico);
                    detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
                            resultado, detalleDB.retiroNombreCorto,
                            detalleDB.instruccionEfectivoIdTipoPago);
                } else {
                    RegContEfecNombrada resultado = cargaRegContEfecNombrada(detalleDB, isHistorico);
                    detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
                            resultado, detalleDB.retiroNombreCorto,
                            detalleDB.instruccionEfectivoIdTipoPago);
                }

                detalleMovimientoEfectivoDTO.setConcepto(detalleDB.concepto);
                detalleMovimientoEfectivoDTO.setReferenciaOperacion(detalleDB.referenciaOperacion);
                logger.debug("Referencia encontrada :: ");
                logger.trace(detalleMovimientoEfectivoDTO.toString());
                return detalleMovimientoEfectivoDTO;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String cargaQueryConsultaDetalle(
            long idRegistroContable, long idFolioInstLiquidacion, boolean isHistorico) {

        String nombreTablaRegistro = (isHistorico) ? "T_REG_CONTABLE_EFE_NOMBRADA_H" : "T_REG_CONTABLE_EFE_NOMBRADA";
        String nombreCampoResgistroContable = (isHistorico) ? "ID_REGISTRO_CONTABLE_H" : "ID_REGISTRO_CONTABLE";

        String nombreTablaOperacion = (isHistorico) ? "T_OPERACION_NOMBRADA_H" : "T_OPERACION_NOMBRADA";
        String nombreCampoOperacion = (isHistorico) ? "ID_OPERACION_H" : "ID_OPERACION";

        String nombreTablaInstruccion = (isHistorico) ? "T_INSTRUCCION_LIQUIDACION_H" : "T_INSTRUCCION_LIQUIDACION";
        String nombreCampoInstruccion = (isHistorico) ? "ID_INSTRUCCION_LIQUIDACION_H" : "ID_INSTRUCCION_LIQUIDACION";

        String nombreTablaSaldoNombrada = (isHistorico) ? "T_SALDO_NOMBRADA_H" : "T_SALDO_NOMBRADA";
        String nombreCampoSaldoNombrada = (isHistorico) ? "ID_SALDO_NOMBRADA_H" : "ID_SALDO";


        String query = "\n" +
                "SELECT DISTINCT registro.*, tipoRetiro.nombre_Corto,\n" +
                "       instruccionEfectivo.id_Tipo_Pago, \n" +
                "       instruccionEfectivo.FOLIO_INST_LIQUIDACION, \n" +
                "       operacion.ID_TIPO_OPERACION,\n" +
                "       operacion.ID_INSTRUCCION, instruccion.FOLIO_INSTRUCCION,\n" +
                "       operacion.FOLIO_OPERACION, \n" +
                "       instruccionEfectivo.CONCEPTO, \n" +
                "       instruccionEfectivo.REFERENCIA_OPERACION \n" +
                "FROM " + nombreTablaRegistro + " registro\n" +
                "INNER JOIN " + nombreTablaOperacion + " operacion ON registro.ID_OPERACION = operacion." + nombreCampoOperacion + "\n" +
                "INNER JOIN C_TIPO_OPERACION tipoOperacion ON operacion.ID_TIPO_OPERACION = tipoOperacion.ID_TIPO_OPERACION\n" +
                "INNER JOIN " + nombreTablaInstruccion + " instruccion ON operacion.ID_INSTRUCCION = instruccion." + nombreCampoInstruccion + "\n" +
                "INNER JOIN C_TIPO_INSTRUCCION tipoInstruccion ON instruccion.ID_TIPO_INSTRUCCION = tipoInstruccion.ID_TIPO_INSTRUCCION\n" +
                "LEFT OUTER JOIN T_INSTRUCCION_EFECTIVO instruccionEfectivo ON instruccion.ID_TIPO_INSTRUCCION = instruccionEfectivo.ID_TIPO_INSTRUCCION \n" +
                "                                                          AND instruccion.FOLIO_INSTRUCCION = instruccionEfectivo.FOLIO_INSTRUCCION\n" +
                "LEFT JOIN C_TIPO_RETIRO tipoRetiro ON tipoRetiro.ID_TIPO_RETIRO = instruccionEfectivo.ID_TIPO_RETIRO\n" +
                "INNER JOIN " + nombreTablaSaldoNombrada + " saldoNombrada ON registro.ID_SALDO = saldoNombrada." + nombreCampoSaldoNombrada + "\n" +
                "INNER JOIN C_CUENTA_NOMBRADA cuentaNombrada ON saldoNombrada.ID_CUENTA = cuentaNombrada.ID_CUENTA_NOMBRADA\n" +
                "INNER JOIN C_INSTITUCION institucion ON cuentaNombrada.ID_INSTITUCION = institucion.ID_INSTITUCION\n" +
                "INNER JOIN C_TIPO_INSTITUCION tipoInstitucion ON  tipoInstitucion.ID_TIPO_INSTITUCION = institucion.ID_TIPO_INSTITUCION\n" +
                "INNER JOIN C_TIPO_CUENTA tipoCuenta ON cuentaNombrada.ID_TIPO_CUENTA = tipoCuenta.ID_TIPO_CUENTA\n" +
                "INNER JOIN C_BOVEDA boveda ON boveda.ID_BOVEDA = saldoNombrada.ID_BOVEDA\n" +
                "INNER JOIN C_DIVISA divisa ON divisa.ID_DIVISA = saldoNombrada.ID_DIVISA\n" +
                "WHERE registro." + nombreCampoResgistroContable + " =  " + idRegistroContable + " \n" +
                "      AND instruccionEfectivo.FOLIO_INST_LIQUIDACION = " + idFolioInstLiquidacion + "\n";
        return query;
    }

    private DetalleDB cargaDetalleDB(boolean isHistorico, Object[] row) {
        logger.debug("cargaRegistroDTO");

        BigInteger idRegistroContable = new BigInteger(((BigDecimal) row[0]).toString());
        BigInteger idSaldo = new BigInteger(((BigDecimal) row[1]).toString());
        BigDecimal idTipoAccion = (BigDecimal) row[2];
        BigDecimal importe = (BigDecimal) row[3];
        Date fecha = new Date(((Timestamp) row[4]).getTime());
        BigInteger idCiclo = new BigInteger(((BigDecimal) row[5]).toString());
        BigInteger idOperacion = new BigInteger(((BigDecimal) row[6]).toString());
        BigInteger idCuenta = new BigInteger(((BigDecimal) row[7]).toString());
        BigInteger cargoA = new BigInteger(((BigDecimal) row[8]).toString());
        BigInteger idRegContableControlada = new BigInteger(((BigDecimal) row[9]).toString());

        int i = 9;
        Date fechaCreacion = null;
        if (isHistorico) {
            i++;
            fechaCreacion = new Date(((Timestamp) row[i]).getTime());
        }
        i++;
        String retiroNombreCorto = (String) row[i];
        i++;
        Integer instruccionEfectivoIdTipoPago = null;
        if (row[i] != null) {
            instruccionEfectivoIdTipoPago = ((BigInteger) row[i]).intValue();
        }
        i++;
        BigInteger instruccionEfectivoFolioInstLiquidacion = new BigInteger(((BigDecimal) row[i]).toString());
        i++;
        BigInteger idTipoOperacion = new BigInteger(((BigDecimal) row[i]).toString());
        i++;
        BigInteger idInstruccion = new BigInteger(((BigDecimal) row[i]).toString());
        i++;
        BigInteger folioInstruccion = new BigInteger(((BigDecimal) row[i]).toString());
        i++;
        BigInteger folioOperacion = new BigInteger(((BigDecimal) row[i]).toString());
        i++;
        String concepto =  row[i]!=null ? row[i].toString():"";
        i++;
        String referenciaOperacion =  row[i]!=null ? row[i].toString():"";

        return new DetalleDB(idRegistroContable, idSaldo, idTipoAccion, importe, fecha, idCiclo, idOperacion,
                idCuenta, cargoA, idRegContableControlada, fechaCreacion, retiroNombreCorto,
                instruccionEfectivoIdTipoPago, instruccionEfectivoFolioInstLiquidacion, idTipoOperacion,
                idInstruccion, folioInstruccion, folioOperacion, concepto, referenciaOperacion);

    }

    private RegContEfecNombradaHistorico cargaRegContEfecNombradaHistorico(DetalleDB detalle, boolean isHistorico) {
        logger.debug("cargaRegContEfecNombradaHistorico");
        logger.trace(detalle.toString());

        RegContEfecNombradaHistorico resultado = new RegContEfecNombradaHistorico();
        resultado.setIdRegContableControlada(detalle.idRegistroContable);
        resultado.setIdSaldo(detalle.idSaldo);

        SaldoNombradaHistorico saldo = (SaldoNombradaHistorico) obtenerSaldoNombrada(detalle.idSaldo, isHistorico);
        resultado.setSaldoNombradaHistorico(saldo);

        resultado.setTipoAccion(TipoAccion.fromInt(detalle.idTipoAccion.intValue()));
        resultado.setImporte(detalle.importe);
        resultado.setFecha(detalle.fecha);
        resultado.setIdCiclo(detalle.idCiclo);
        resultado.setIdOperacion(detalle.idOperacion);

        OperacionNombradaHistorico operacion = new OperacionNombradaHistorico();
        operacion.setIdOperacion(detalle.idOperacion);
        operacion.setFolioOperacion(detalle.folioOperacion);
        operacion.setIdTipoOperacion(detalle.idTipoOperacion);
        operacion.setTipoOperacion(obtenerEntity(detalle.idTipoOperacion, TipoOperacion.class));

        InstruccionLiquidacionHistorico instruccion =
                (InstruccionLiquidacionHistorico) obtenerInstruccionLiquidacion(detalle.idInstruccion, isHistorico);
        operacion.setInstruccion(instruccion);

        resultado.setOperacion(operacion);

        resultado.setIdCuenta(detalle.idCuenta);
        resultado.setCargoA(detalle.cargoA);
        resultado.setIdRegContableControlada(detalle.idRegContableControlada);
        resultado.setFechaCreacion(detalle.fechaCreacion);


        return resultado;
    }

    private RegContEfecNombrada cargaRegContEfecNombrada(DetalleDB detalle, boolean isHistorico) {
        logger.debug("cargaRegContEfecNombrada");
        logger.trace(detalle.toString());

        RegContEfecNombrada resultado = new RegContEfecNombrada();
        resultado.setIdRegContableControlada(detalle.idRegistroContable);
        resultado.setIdSaldo(detalle.idSaldo);

        SaldoNombrada saldo = (SaldoNombrada) obtenerSaldoNombrada(detalle.idSaldo, isHistorico);
        resultado.setSaldoNombrada(saldo);

        resultado.setTipoAccion(TipoAccion.fromInt(detalle.idTipoAccion.intValue()));
        resultado.setImporte(detalle.importe);
        resultado.setFecha(detalle.fecha);
        resultado.setIdCiclo(detalle.idCiclo);
        resultado.setIdOperacion(detalle.idOperacion);

        OperacionNombrada operacion = new OperacionNombrada();
        operacion.setIdOperacion(detalle.idOperacion);
        operacion.setFolioOperacion(detalle.folioOperacion);
        operacion.setIdTipoOperacion(detalle.idTipoOperacion);
        operacion.setTipoOperacion(obtenerEntity(detalle.idTipoOperacion, TipoOperacion.class));


        InstruccionLiquidacion instruccion =
                (InstruccionLiquidacion) obtenerInstruccionLiquidacion(detalle.idInstruccion, isHistorico);
        operacion.setInstruccion(instruccion);

        resultado.setOperacion(operacion);

        resultado.setIdCuenta(detalle.idCuenta);
        resultado.setCargoA(detalle.cargoA);
        resultado.setIdRegContableControlada(detalle.idRegContableControlada);

        return resultado;
    }

    private Object obtenerSaldoNombrada(final BigInteger idSaldo, boolean isHistorico) {
        logger.debug("obtenerSaldoNombrada :: " + idSaldo);

        Object saldo = null;

        if (isHistorico) {
            saldo = obtenerEntity(idSaldo, SaldoNombradaHistorico.class);

        } else {
            saldo = obtenerEntity(idSaldo, SaldoNombrada.class);
        }

        BigInteger idDivisa = (isHistorico ? ((SaldoNombradaHistorico) saldo).getIdDivisa() : ((SaldoNombrada) saldo).getIdDivisa());
        BigInteger idBoveda = (isHistorico ? ((SaldoNombradaHistorico) saldo).getIdBoveda() : ((SaldoNombrada) saldo).getIdBoveda());
        BigInteger idCuenta = (isHistorico ? ((SaldoNombradaHistorico) saldo).getIdCuenta() : ((SaldoNombrada) saldo).getIdCuenta());

        CuentaNombrada cuentaNombrada = obtenerEntity(idCuenta, CuentaNombrada.class);
        Divisa divisa = obtenerEntity(idDivisa, Divisa.class);
        Boveda boveda = obtenerEntity(idBoveda, Boveda.class);

        cuentaNombrada.setInstitucion(obtenerEntity(cuentaNombrada.getIdInstitucion(), Institucion.class));
        cuentaNombrada.getInstitucion().setTipoInstitucion(
                obtenerEntity(cuentaNombrada.getInstitucion().getIdTipoInstitucion(), TipoInstitucion.class));

        if (isHistorico) {
            ((SaldoNombradaHistorico) saldo).setCuentaNombrada(cuentaNombrada);
            ((SaldoNombradaHistorico) saldo).setDivisa(divisa);
            ((SaldoNombradaHistorico) saldo).setBoveda(boveda);
        } else {
            ((SaldoNombrada) saldo).setCuentaNombrada(cuentaNombrada);
            ((SaldoNombrada) saldo).setDivisa(divisa);
            ((SaldoNombrada) saldo).setBoveda(boveda);
        }


        return saldo;
    }


    private Object obtenerInstruccionLiquidacion(final BigInteger idInstruccion, boolean isHistorico) {
        logger.debug("obtenerInstruccionLiquidacion :: " + idInstruccion);

        String query = cargarQueryConsultaInstruccion(idInstruccion, isHistorico);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {

                InstruccionDB instruccionDB = cargaInstruccionDB(isHistorico, row);

                logger.trace(instruccionDB.toString());

                if (isHistorico) {
                    return cargaInstruccionLiquidacionHistorico(instruccionDB);
                } else {
                    return cargaInstruccionLiquidacion(instruccionDB);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String cargarQueryConsultaInstruccion(BigInteger idInstruccion, boolean isHistorico) {
        String nombreTablaInstruccion = (isHistorico) ? "T_INSTRUCCION_LIQUIDACION_H" : "T_INSTRUCCION_LIQUIDACION";
        String nombreCampoInstruccion = (isHistorico) ? "ID_INSTRUCCION_LIQUIDACION_H" : "ID_INSTRUCCION_LIQUIDACION";

        String query = "\n" +
                " SELECT DISTINCT instruccion.*\n" +
                " FROM " + nombreTablaInstruccion + " instruccion\n" +
                " WHERE instruccion." + nombreCampoInstruccion + " = " + idInstruccion;
        return query;
    }

    private InstruccionDB cargaInstruccionDB(boolean isHistorico, Object[] row) {
        logger.debug("cargaInstruccionDB");

        BigInteger idInstruccionLiquidacion = new BigInteger(((BigDecimal) row[0]).toString());
        BigInteger folioInstruccion = new BigInteger(((BigDecimal) row[1]).toString());
        BigInteger folioInstruccionLiquidacion = new BigInteger(((BigDecimal) row[2]).toString());
        BigInteger idInstruccionOrigen = null;
        if (row[3] != null) {
            new BigInteger(((BigDecimal) row[3]).toString());
        }
        BigInteger idTipoInstruccion = new BigInteger(((BigDecimal) row[4]).toString());
        BigInteger idInstruccionAnterior = null;
        if (row[5] != null) {
            new BigInteger(((BigDecimal) row[5]).toString());
        }
        BigInteger idModuloOrigen = new BigInteger(((BigDecimal) row[6]).toString());
        BigInteger idEstadoInstruccion = new BigInteger(((BigDecimal) row[7]).toString());
        Date fechaLiquidacion = new Date(((Timestamp) row[8]).getTime());
        Date fechaLimiteLiquidacion = new Date(((Timestamp) row[9]).getTime());
        Date fechaConcertacion = new Date(((Timestamp) row[10]).getTime());
        BigInteger idCiclo = null;
        if (row[11] != null) {
            new BigInteger(((BigDecimal) row[11]).toString());
        }
        Date fechaAplicacion = null;
        if (row[12] != null) {
            new Date(((Timestamp) row[12]).getTime());
        }
        BigInteger bloqueada = null;
        if (row[13] != null) {
            new BigInteger(((BigDecimal) row[13]).toString());
        }
        BigInteger bancoTrabajo = null;
        if (row[14] != null) {
            new BigInteger(((BigDecimal) row[14]).toString());
        }
        BigInteger pendientePosicion = null;
        if (row[15] != null) {
            new BigInteger(((BigDecimal) row[15]).toString());
        }
        BigInteger pendienteSaldo = null;
        if (row[16] != null) {
            new BigInteger(((BigDecimal) row[16]).toString());
        }
        BigInteger idClasificacion = null;
        if (row[17] != null) {
            new BigInteger(((BigDecimal) row[17]).toString());
        }
        BigInteger sobregiro = null;
        if (row[18] != null) {
            new BigInteger(((BigDecimal) row[18]).toString());
        }
        BigInteger prioridad = null;
        if (row[19] != null) {
            new BigInteger(((BigDecimal) row[19]).toString());
        }
        int i = 19;

        Date fechaCreacion = null;
        if (isHistorico) {
            i++;
            fechaCreacion = new Date(((Timestamp) row[i]).getTime());
        }
        i++;
        BigInteger idTipoInstruccionOrigen = null;
        if (row[i] != null) {
            new BigInteger(((BigDecimal) row[i]).toString());
        }
        i++;
        BigInteger multiBovedaDivisa = null;
        if (row[i] != null) {
            new BigInteger(((BigDecimal) row[i]).toString());
        }
        i++;
        BigInteger idOperacionOrigen = null;
        if (row[i] != null) {
            new BigInteger(((BigDecimal) row[i]).toString());
        }

        return new InstruccionDB(idInstruccionLiquidacion, folioInstruccion, folioInstruccionLiquidacion,
                idInstruccionOrigen, idTipoInstruccion, idInstruccionAnterior, idModuloOrigen, idEstadoInstruccion,
                fechaLiquidacion, fechaLimiteLiquidacion, fechaConcertacion, idCiclo, fechaAplicacion, bloqueada,
                bancoTrabajo, pendientePosicion, pendienteSaldo, idClasificacion, sobregiro, prioridad, fechaCreacion,
                idTipoInstruccionOrigen, multiBovedaDivisa, idOperacionOrigen);
    }

    private InstruccionLiquidacionHistorico cargaInstruccionLiquidacionHistorico(InstruccionDB instruccionDB) {
        logger.debug("cargaInstruccionLiquidacionHistorico");
        logger.trace(instruccionDB.toString());

        InstruccionLiquidacionHistorico resultado = new InstruccionLiquidacionHistorico();

        resultado.setIdInstruccionLiquidacion(instruccionDB.idInstruccionLiquidacion);
        resultado.setFolioInstruccion(instruccionDB.folioInstruccion);
        resultado.setFolioInstruccionLiquidacion(instruccionDB.folioInstruccionLiquidacion);
        resultado.setIdInstruccionLiquidacionOrigen(instruccionDB.idInstruccionOrigen);

        resultado.setIdTipoInstruccion(instruccionDB.idTipoInstruccion);
        TipoInstruccion tipoInstruccion = obtenerEntity(instruccionDB.idTipoInstruccion, TipoInstruccion.class);
        resultado.setTipoInstruccion(tipoInstruccion);

        resultado.setIdInstruccionLiquidacionAnterior(instruccionDB.idInstruccionAnterior);
        resultado.setIdModuloNegocio(instruccionDB.idModuloOrigen);
        resultado.setIdEstadoInstruccion(instruccionDB.idEstadoInstruccion);
        resultado.setEstadoInstruccion(EstadoInstruccion.fromInt(instruccionDB.idEstadoInstruccion.intValue()));
        resultado.setFechaLiquidacion(instruccionDB.fechaLiquidacion);
        resultado.setFechaLimiteLiquidacion(instruccionDB.fechaLimiteLiquidacion);
        resultado.setFechaConcertacion(instruccionDB.fechaConcertacion);
        resultado.setIdCiclo(instruccionDB.idCiclo);
        resultado.setFechaAplicacion(instruccionDB.fechaAplicacion);
        resultado.setBloqueada(instruccionDB.bloqueada.equals(1));
        resultado.setBancoTrabajo(instruccionDB.bancoTrabajo.equals(1));
        resultado.setFechaCreacion(instruccionDB.fechaCreacion);

        return resultado;
    }

    private InstruccionLiquidacion cargaInstruccionLiquidacion(InstruccionDB instruccionDB) {
        logger.debug("cargaInstruccionLiquidacion");
        logger.trace(instruccionDB.toString());

        InstruccionLiquidacion resultado = new InstruccionLiquidacion();

        resultado.setIdInstruccionLiquidacion(instruccionDB.idInstruccionLiquidacion);
        resultado.setFolioInstruccion(instruccionDB.folioInstruccion);
        resultado.setFolioInstruccionLiquidacion(instruccionDB.folioInstruccionLiquidacion);
        resultado.setIdInstruccionLiquidacionOrigen(instruccionDB.idInstruccionOrigen);

        resultado.setIdTipoInstruccion(instruccionDB.idTipoInstruccion);
        TipoInstruccion tipoInstruccion = obtenerEntity(instruccionDB.idTipoInstruccion, TipoInstruccion.class);
        resultado.setTipoInstruccion(tipoInstruccion);

        resultado.setIdInstruccionLiquidacionAnterior(instruccionDB.idInstruccionAnterior);
        resultado.setIdModuloNegocio(instruccionDB.idModuloOrigen);
        resultado.setIdEstadoInstruccion(instruccionDB.idEstadoInstruccion);
        resultado.setEstadoInstruccion(EstadoInstruccion.fromInt(instruccionDB.idEstadoInstruccion.intValue()));
        resultado.setFechaLiquidacion(instruccionDB.fechaLiquidacion);
        resultado.setFechaLimiteLiquidacion(instruccionDB.fechaLimiteLiquidacion);
        resultado.setFechaConcertacion(instruccionDB.fechaConcertacion);
        resultado.setIdCiclo(instruccionDB.idCiclo);
        resultado.setFechaAplicacion(instruccionDB.fechaAplicacion);
        resultado.setBloqueada(instruccionDB.bloqueada != null && instruccionDB.bloqueada.equals(1));
        resultado.setBancoTrabajo(instruccionDB.bancoTrabajo != null && instruccionDB.bancoTrabajo.equals(1));
        return resultado;
    }

    @SuppressWarnings("unchecked")
    private <T> T obtenerEntity(final BigInteger id, final Class<T> entityClass) {
        logger.debug("obtenerEntity :: " + id + " :: " + entityClass.getName());
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.get(entityClass, id);
            }
        });
    }

    private List<Object[]> ejecutarQuery(String query) {
        logger.debug("Ejecutar :: \n" + query);
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);
        List<Object[]> lstResult = sqlQuery.list();
        return lstResult;
    }

    class DetalleDB {
        BigInteger idRegistroContable;
        BigInteger idSaldo;
        BigDecimal idTipoAccion;
        BigDecimal importe;
        Date fecha;
        BigInteger idCiclo;
        BigInteger idOperacion;
        BigInteger idCuenta;
        BigInteger cargoA;
        BigInteger idRegContableControlada;
        Date fechaCreacion;
        String retiroNombreCorto;
        Integer instruccionEfectivoIdTipoPago;
        BigInteger instruccionEfectivoFolioInstLiquidacion;
        BigInteger idTipoOperacion;
        BigInteger idInstruccion;
        BigInteger folioInstruccion;
        BigInteger folioOperacion;

        String concepto ;
        String referenciaOperacion;

        public DetalleDB(BigInteger idRegistroContable, BigInteger idSaldo, BigDecimal idTipoAccion,
                         BigDecimal importe, Date fecha, BigInteger idCiclo, BigInteger idOperacion,
                         BigInteger idCuenta, BigInteger cargoA, BigInteger idRegContableControlada,
                         Date fechaCreacion, String retiroNombreCorto, Integer instruccionEfectivoIdTipoPago,
                         BigInteger instruccionEfectivoFolioInstLiquidacion, BigInteger idTipoOperacion,
                         BigInteger idInstruccion, BigInteger folioInstruccion, BigInteger folioOperacion,
                         String concepto, String referenciaOperacion) {
            this.idRegistroContable = idRegistroContable;
            this.idSaldo = idSaldo;
            this.idTipoAccion = idTipoAccion;
            this.importe = importe;
            this.fecha = fecha;
            this.idCiclo = idCiclo;
            this.idOperacion = idOperacion;
            this.idCuenta = idCuenta;
            this.cargoA = cargoA;
            this.idRegContableControlada = idRegContableControlada;
            this.fechaCreacion = fechaCreacion;
            this.retiroNombreCorto = retiroNombreCorto;
            this.instruccionEfectivoIdTipoPago = instruccionEfectivoIdTipoPago;
            this.instruccionEfectivoFolioInstLiquidacion = instruccionEfectivoFolioInstLiquidacion;
            this.idTipoOperacion = idTipoOperacion;
            this.idInstruccion = idInstruccion;
            this.folioInstruccion = folioInstruccion;
            this.folioOperacion = folioOperacion;
            this.concepto = concepto;
            this.referenciaOperacion = referenciaOperacion;
        }

        @Override
        public String toString() {
            return "DetalleDB {"
                    + "ID_REGISTRO_CONTABLE [" + idRegistroContable + "] :: "
                    + "ID_SALDO [" + idSaldo + "] :: "
                    + "ID_TIPO_ACCION [" + idTipoAccion + "] :: "
                    + "IMPORTE [" + importe + "] :: "
                    + "FECHA [" + fecha + "] :: "
                    + "ID_CICLO [" + idCiclo + "] :: "
                    + "ID_OPERACION [" + idOperacion + "] :: "
                    + "ID_CUENTA [" + idCuenta + "] :: "
                    + "CARGO_A [" + cargoA + "] :: "
                    + "ID_REG_CONTABLE_CONTROLADA [" + idRegContableControlada + "] :: "
                    + "FECHA_CREACION [" + fechaCreacion + "] :: "
                    + "NOMBRE_CORTO [" + retiroNombreCorto + "] :: "
                    + "ID_TIPO_PAGO [" + instruccionEfectivoIdTipoPago + "] :: "
                    + "FOLIO_INST_LIQUIDACION [" + instruccionEfectivoFolioInstLiquidacion + "] :: "
                    + "ID_TIPO_OPERACION [" + idTipoOperacion + "] :: "
                    + "ID_INSTRUCCION [" + idInstruccion + "] :: "
                    + "FOLIO_INSTRUCCION [" + folioInstruccion + "] :: "
                    + "FOLIO_OPERACION [" + folioOperacion + "] :: "
                    + "CONCEPTO [" + concepto + "] :: "
                    + "REFERENCIA_OPERACION [" + referenciaOperacion + "] :: "
                    + '}';
        }
    }

    class InstruccionDB {
        BigInteger idInstruccionLiquidacion;
        BigInteger folioInstruccion;
        BigInteger folioInstruccionLiquidacion;
        BigInteger idInstruccionOrigen;
        BigInteger idTipoInstruccion;
        BigInteger idInstruccionAnterior;
        BigInteger idModuloOrigen;
        BigInteger idEstadoInstruccion;
        Date fechaLiquidacion;
        Date fechaLimiteLiquidacion;
        Date fechaConcertacion;
        BigInteger idCiclo;
        Date fechaAplicacion;
        BigInteger bloqueada;
        BigInteger bancoTrabajo;
        BigInteger pendientePosicion;
        BigInteger pendienteSaldo;
        BigInteger idClasificacion;
        BigInteger sobregiro;
        BigInteger prioridad;
        Date fechaCreacion;
        BigInteger idTipoInstruccionOrigen;
        BigInteger multiBovedaDivisa;
        BigInteger idOperacionOrigen;

        public InstruccionDB(BigInteger idInstruccionLiquidacion, BigInteger folioInstruccion,
                             BigInteger folioInstruccionLiquidacion, BigInteger idInstruccionOrigen,
                             BigInteger idTipoInstruccion, BigInteger idInstruccionAnterior, BigInteger idModuloOrigen,
                             BigInteger idEstadoInstruccion, Date fechaLiquidacion, Date fechaLimiteLiquidacion,
                             Date fechaConcertacion, BigInteger idCiclo, Date fechaAplicacion, BigInteger bloqueada,
                             BigInteger bancoTrabajo, BigInteger pendientePosicion, BigInteger pendienteSaldo,
                             BigInteger idClasificacion, BigInteger sobregiro, BigInteger prioridad, Date fechaCreacion,
                             BigInteger idTipoInstruccionOrigen, BigInteger multiBovedaDivisa, BigInteger idOperacionOrigen) {
            this.idInstruccionLiquidacion = idInstruccionLiquidacion;
            this.folioInstruccion = folioInstruccion;
            this.folioInstruccionLiquidacion = folioInstruccionLiquidacion;
            this.idInstruccionOrigen = idInstruccionOrigen;
            this.idTipoInstruccion = idTipoInstruccion;
            this.idInstruccionAnterior = idInstruccionAnterior;
            this.idModuloOrigen = idModuloOrigen;
            this.idEstadoInstruccion = idEstadoInstruccion;
            this.fechaLiquidacion = fechaLiquidacion;
            this.fechaLimiteLiquidacion = fechaLimiteLiquidacion;
            this.fechaConcertacion = fechaConcertacion;
            this.idCiclo = idCiclo;
            this.fechaAplicacion = fechaAplicacion;
            this.bloqueada = bloqueada;
            this.bancoTrabajo = bancoTrabajo;
            this.pendientePosicion = pendientePosicion;
            this.pendienteSaldo = pendienteSaldo;
            this.idClasificacion = idClasificacion;
            this.sobregiro = sobregiro;
            this.prioridad = prioridad;
            this.fechaCreacion = fechaCreacion;
            this.idTipoInstruccionOrigen = idTipoInstruccionOrigen;
            this.multiBovedaDivisa = multiBovedaDivisa;
            this.idOperacionOrigen = idOperacionOrigen;
        }

        @Override
        public String toString() {
            return "InstruccionDB{" +
                    "idInstruccionLiquidacion=" + idInstruccionLiquidacion +
                    ", folioInstruccion=" + folioInstruccion +
                    ", folioInstruccionLiquidacion=" + folioInstruccionLiquidacion +
                    ", idInstruccionOrigen=" + idInstruccionOrigen +
                    ", idTipoInstruccion=" + idTipoInstruccion +
                    ", idInstruccionAnterior=" + idInstruccionAnterior +
                    ", idModuloOrigen=" + idModuloOrigen +
                    ", idEstadoInstruccion=" + idEstadoInstruccion +
                    ", fechaLiquidacion=" + fechaLiquidacion +
                    ", fechaLimiteLiquidacion=" + fechaLimiteLiquidacion +
                    ", fechaConcertacion=" + fechaConcertacion +
                    ", idCiclo=" + idCiclo +
                    ", fechaAplicacion=" + fechaAplicacion +
                    ", bloqueada=" + bloqueada +
                    ", bancoTrabajo=" + bancoTrabajo +
                    ", pendientePosicion=" + pendientePosicion +
                    ", pendienteSaldo=" + pendienteSaldo +
                    ", idClasificacion=" + idClasificacion +
                    ", sobregiro=" + sobregiro +
                    ", prioridad=" + prioridad +
                    ", fechaCreacion=" + fechaCreacion +
                    ", idTipoInstruccionOrigen=" + idTipoInstruccionOrigen +
                    ", multiBovedaDivisa=" + multiBovedaDivisa +
                    ", idOperacionOrigen=" + idOperacionOrigen +
                    '}';
        }
    }
}
