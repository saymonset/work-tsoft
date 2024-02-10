/**
 * Bursatec - Portal Internacional Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesPorcentajeRetencionTO;

/**
 * Clase de utileria para crear los querys necesarios para la consulta de historico de capitales por
 * cuenta.
 * 
 * @author Pablo Balderas
 */
public class ConsultaHistoricoCapitalesCuentaUtil {

    /** TO con los parametros para realizar la consulta */
    private ParamConsultaDetalleEjerDerCapTO parametrosConsulta;

    /** Indica el numero de divisas */
    private Integer numeroDivisas = null;

    /** Cadena con el select del query principal */
    private String selectQueryPrincipal;

    /** Condicion where */
    private String where;

    private String groupBy;

    /** Cadena con el orden para las consultas */
    private String orderBy;

    /** Lista con los parametros */
    private final List<Object> parametros;

    /** Lista con los tipos de los parametros */
    private final List<Type> tipos;

    /** Cadena para realizar el having */
    private String having;

    /**
     * Constructor de la clase
     */
    public ConsultaHistoricoCapitalesCuentaUtil() {
        this.parametros = new ArrayList<Object>();
        this.tipos = new ArrayList<Type>();
    }

    /**
     * Constructor de la clase
     * 
     * @param parametrosConsulta Objeto con los parametros de la consulta.
     * @param session Sesion de hibernate
     */
    public ConsultaHistoricoCapitalesCuentaUtil(
            final ParamConsultaDetalleEjerDerCapTO parametrosConsulta) {
        this.parametros = new ArrayList<Object>();
        this.tipos = new ArrayList<Type>();
        this.parametrosConsulta = parametrosConsulta;
        this.crearQueryPrincipal();
        this.crearCondiciones();
        this.crearOrdenamiento();
        this.crearAgrupamiento();
        this.crearHaving();
    }

    /**
     * Crea el query para obtener el total de divisas.
     * 
     * @param session Sesion de hibernate para crear el query.
     * @return Objeto con el query
     */
    public SQLQuery crearSQLQueryCuentaDivisas(final Session session) {
        StringBuffer strQuery = new StringBuffer();
        if (this.aplicaHaving()) {
            strQuery.append("select count(distinct(divisa)) as numDivisas from (");
            strQuery.append(this.selectQueryPrincipal);
            strQuery.append(this.where);
            strQuery.append(this.groupBy);
            strQuery.append(this.having);
            strQuery.append(" )");
        } else {
            strQuery.append(" select count(distinct(divisa)) as numDivisas");
            strQuery.append(" from T_DERECHO_CAPITAL_HISTORICO");
            strQuery.append(this.where);
        }
        SQLQuery query = session.createSQLQuery(strQuery.toString());
        query.setParameters(this.parametros.toArray(), this.tipos.toArray(new Type[] {}));
        query.addScalar("numDivisas", Hibernate.INTEGER);
        return query;
    }

    /**
     * Crea el query para obtener la consulta
     * 
     * @param session Sesion de hibernate para crear el query.
     * @return Objeto con el query
     */
    public SQLQuery crearSQLQueryRegistros(final Session session) {
        SQLQuery query =
                session.createSQLQuery(this.selectQueryPrincipal + this.where + this.groupBy
                        + this.having + this.orderBy);
        query.setParameters(this.parametros.toArray(), this.tipos.toArray(new Type[] {}));
        query.addScalar("idDerechoCapital", Hibernate.LONG);
        query.addScalar("idFolioInstitucion", Hibernate.STRING);
        query.addScalar("tipoValor", Hibernate.STRING);
        query.addScalar("emisora", Hibernate.STRING);
        query.addScalar("serie", Hibernate.STRING);
        query.addScalar("isin", Hibernate.STRING);
        query.addScalar("fechaCorte", Hibernate.DATE);
        query.addScalar("fechaPago", Hibernate.DATE);
        query.addScalar("cuenta", Hibernate.STRING);
        query.addScalar("porcentajeRetencionReal", Hibernate.DOUBLE);
        query.addScalar("asignacion", Hibernate.LONG);
        query.addScalar("proporcion", Hibernate.DOUBLE);
        query.addScalar("fee", Hibernate.DOUBLE);
        query.addScalar("idEstatusDerecho", Hibernate.LONG);
        query.addScalar("estadoDerecho", Hibernate.STRING);
        query.addScalar("montoBruto", Hibernate.DOUBLE);
        query.addScalar("impuestoRetenido", Hibernate.DOUBLE);
        query.addScalar("montoNeto", Hibernate.DOUBLE);
        query.addScalar("montoFee", Hibernate.DOUBLE);
        query.addScalar("divisa", Hibernate.STRING);
        query.addScalar("custodio", Hibernate.STRING);
        query.addScalar("idTipoDerecho", Hibernate.LONG);
        query.addScalar("tipoDerecho", Hibernate.STRING);
        query.setResultTransformer(new AliasToBeanResultTransformer(
                DerechoCapitalHistoricoCuenta.class));
        return query;
    }

    /**
     * Crea el query para obtener el total de registros.
     * 
     * @param session Sesion de hibernate para crear el query.
     * @return Objeto con el query
     */
    public SQLQuery crearSQLQueryTotalRegistros(final Session session) {
        String strQuery =
                "select count(*) as numRegistros from(" + this.selectQueryPrincipal + this.where
                        + this.groupBy + this.having + ")";
        SQLQuery query = session.createSQLQuery(strQuery);
        query.setParameters(this.parametros.toArray(), this.tipos.toArray(new Type[] {}));
        query.addScalar("numRegistros", Hibernate.INTEGER);
        return query;
    }

    /**
     * Crea el query para obtener los totales de los campos asignacion, monto bruto, monto fee,
     * impuesto retenido y monto neto.
     * 
     * @param session Sesion de hibernate para crear el query.
     * @return Objeto con el query
     */
    public SQLQuery crearSQLQueryTotales(final Session session) {
        StringBuffer strQuery = new StringBuffer();
        if (this.aplicaHaving()) {
            strQuery.append(" select");
            strQuery.append(" sum(asignacion) as asignacion");
            if (this.isCalcularTotales()) {
                strQuery.append(" ,sum(montoBruto) as montoBruto,");
                strQuery.append(" sum(montoFee) as montoFee,");
                strQuery.append(" sum(impuestoRetenido) as impuestoRetenido,");
                strQuery.append(" sum(montoNeto) as montoNeto");
            }
            strQuery.append(" from");
            strQuery.append("(");
            strQuery.append(this.selectQueryPrincipal + this.where + this.groupBy + this.having
                    + this.orderBy);
            strQuery.append(")");
        } else {
            strQuery.append(" select");
            strQuery.append(" sum(asignacion) as asignacion");
            if (this.isCalcularTotales()) {
                strQuery.append(" ,sum(monto_bruto) as montoBruto,");
                strQuery.append(" sum(monto_fee) as montoFee,");
                strQuery.append(" sum(impuesto_retenido) as impuestoRetenido,");
                strQuery.append(" sum(monto_fee) as montoNeto");
            }
            strQuery.append(" from T_DERECHO_CAPITAL_HISTORICO");
            strQuery.append(this.where + this.orderBy);
        }
        SQLQuery query = session.createSQLQuery(strQuery.toString());
        query.setParameters(this.parametros.toArray(), this.tipos.toArray(new Type[] {}));
        query.addScalar("asignacion", Hibernate.LONG);
        if (this.isCalcularTotales()) {
            query.addScalar("montoBruto", Hibernate.DOUBLE);
            query.addScalar("montoFee", Hibernate.DOUBLE);
            query.addScalar("impuestoRetenido", Hibernate.DOUBLE);
            query.addScalar("montoNeto", Hibernate.DOUBLE);
        }
        return query;
    }

    /**
     * Crea el query que calcula los totales por
     * 
     * @param session Session de hibernate para crear el query.
     * @return Objeto con el query.
     */
    public SQLQuery crearSQLQueryTotalesPorcentajeRetencion(final Session session) {
        StringBuilder strQuery = new StringBuilder();
        if (this.aplicaHaving()) {
            strQuery.append(" select");
            strQuery.append(" porcentajeRetencionReal as porcentajeRetencionReal,");
            strQuery.append(" sum(asignacion) as asignacion");
            if (this.isCalcularTotales()) {
                strQuery.append(" ,sum(montoBruto) as montoBruto,");
                strQuery.append(" sum(montoFee) as montoFee,");
                strQuery.append(" sum(impuestoRetenido) as impuestoRetenido,");
                strQuery.append(" sum(montoNeto) as montoNeto");
            }
            strQuery.append(" from(");
            strQuery.append(this.selectQueryPrincipal + this.where + this.groupBy + this.having
                    + this.orderBy);
            strQuery.append(")");
            strQuery.append(" group by porcentajeRetencionReal");
            strQuery.append(" order by porcentajeRetencionReal asc");
        } else {
            strQuery.append(" select");
            strQuery.append(" porcentaje_retencion_real as porcentajeRetencionReal,");
            strQuery.append(" sum(asignacion) as asignacion");
            if (this.isCalcularTotales()) {
                strQuery.append(" ,sum(MONTO_BRUTO) as montoBruto,");
                strQuery.append(" sum(MONTO_FEE) as montoFee,");
                strQuery.append(" sum(IMPUESTO_RETENIDO) as impuestoRetenido,");
                strQuery.append(" sum(MONTO_NETO) as montoNeto");
            }
            strQuery.append(" from T_DERECHO_CAPITAL_HISTORICO");
            strQuery.append(this.where);
            strQuery.append(" group by porcentaje_retencion_real");
            strQuery.append(" order by porcentaje_retencion_real asc");
        }
        SQLQuery query = session.createSQLQuery(strQuery.toString());
        query.addScalar("porcentajeRetencionReal", Hibernate.DOUBLE);
        query.addScalar("asignacion", Hibernate.LONG);
        if (this.isCalcularTotales()) {
            query.addScalar("montoBruto", Hibernate.DOUBLE);
            query.addScalar("montoFee", Hibernate.DOUBLE);
            query.addScalar("impuestoRetenido", Hibernate.DOUBLE);
            query.addScalar("montoNeto", Hibernate.DOUBLE);
        }
        query.setResultTransformer(new AliasToBeanResultTransformer(
                TotalesPorcentajeRetencionTO.class));
        query.setParameters(this.parametros.toArray(), this.tipos.toArray(new Type[] {}));
        return query;
    }

    /**
     * Indica si debe calcular los totales.
     * 
     * @return true si debe calcular totales; false en caso contrario.
     */
    public boolean isCalcularTotales() {
        return this.numeroDivisas != null && this.numeroDivisas == 1;
    }

    /**
     * Crea el select
     */
    private void crearQueryPrincipal() {
        StringBuilder strSeleccion = new StringBuilder();
        strSeleccion.append(" select");
        strSeleccion.append(" id_derecho_capital as idDerechoCapital,");
        strSeleccion.append(" id_folio_institucion as idFolioInstitucion,");
        strSeleccion.append(" clave_tipo_valor as tipoValor,");
        strSeleccion.append(" clave_pizarra as emisora,");
        strSeleccion.append(" serie as serie,");
        strSeleccion.append(" isin as isin,");
        strSeleccion.append(" fecha_corte as fechaCorte,");
        strSeleccion.append(" fecha_pago_derecho_capital as fechaPago,");
        strSeleccion.append(" cuenta as cuenta,");
        strSeleccion.append(" porcentaje_retencion_real as porcentajeRetencionReal,");
        strSeleccion.append(" proporcion as proporcion,");
        strSeleccion.append(" fee as fee,");
        strSeleccion.append(" id_estatus_derecho as idEstatusDerecho,");
        strSeleccion.append(" desc_estatus_derecho as estadoDerecho,");
        strSeleccion.append(" divisa as divisa,");
        strSeleccion.append(" detalle_custodio as custodio,");
        strSeleccion.append(" id_tipo_derecho as idTipoDerecho,");
        strSeleccion.append(" desc_tipo_derecho as tipoDerecho,");
        strSeleccion.append(" sum(asignacion) as asignacion,");
        strSeleccion.append(" sum(MONTO_BRUTO) as montoBruto,");
        strSeleccion.append(" sum(MONTO_FEE) as montoFee,");
        strSeleccion.append(" sum(IMPUESTO_RETENIDO) as impuestoRetenido,");
        strSeleccion.append(" sum(MONTO_NETO) as montoNeto");
        strSeleccion.append(" from");
        strSeleccion.append(" T_DERECHO_CAPITAL_HISTORICO");
        this.selectQueryPrincipal = strSeleccion.toString();
    }

    /**
     * Crea las condiciones
     */
    private void crearCondiciones() {
        StringBuilder strCondiciones = new StringBuilder();
        strCondiciones.append(" where 1 = 1");
        strCondiciones.append(" and asignacion > 0");
        if (StringUtils.isNotBlank(this.parametrosConsulta.getTv())) {
            strCondiciones.append(" and clave_tipo_valor = ?");
            this.parametros.add(this.parametrosConsulta.getTv());
            this.tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getEmisora())) {
            strCondiciones.append(" and clave_pizarra = ?");
            this.parametros.add(this.parametrosConsulta.getEmisora());
            this.tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getSerie())) {
            strCondiciones.append(" and serie = ?");
            this.parametros.add(this.parametrosConsulta.getSerie());
            this.tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getIsin())) {
            strCondiciones.append(" and isin = ?");
            this.parametros.add(this.parametrosConsulta.getIsin());
            this.tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getInstitucion())) {
            strCondiciones.append(" and id_folio_institucion = ?");
            this.parametros.add(this.parametrosConsulta.getInstitucion());
            this.tipos.add(new StringType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getCuenta())) {
            strCondiciones.append(" and cuenta = ?");
            this.parametros.add(this.parametrosConsulta.getCuenta());
            this.tipos.add(new StringType());
        }
        if (this.parametrosConsulta.getFechaPagoInicial() != null
                && this.parametrosConsulta.getFechaPagoFinal() != null) {
            strCondiciones.append(" and fecha_pago_derecho_capital between ? and ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaPagoInicial(), Boolean.TRUE));
            this.tipos.add(new DateType());
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaPagoFinal(), Boolean.FALSE));
            this.tipos.add(new DateType());
        } else if (this.parametrosConsulta.getFechaPagoInicial() != null) {
            strCondiciones.append(" and fecha_pago_derecho_capital >= ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaPagoInicial(), Boolean.TRUE));
            this.tipos.add(new DateType());
        } else if (this.parametrosConsulta.getFechaPagoFinal() != null) {
            strCondiciones.append(" and fecha_pago_derecho_capital <= ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaPagoFinal(), Boolean.FALSE));
            this.tipos.add(new DateType());
        }
        if (this.parametrosConsulta.getFechaCorteInicial() != null
                && this.parametrosConsulta.getFechaCorteFinal() != null) {
            strCondiciones.append(" and fecha_corte between ? and ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaCorteInicial(), Boolean.TRUE));
            this.tipos.add(new DateType());
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaCorteFinal(), Boolean.FALSE));
            this.tipos.add(new DateType());
        } else if (this.parametrosConsulta.getFechaCorteInicial() != null) {
            strCondiciones.append(" and fecha_corte >= ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaCorteInicial(), Boolean.TRUE));
            this.tipos.add(new DateType());
        } else if (this.parametrosConsulta.getFechaCorteFinal() != null) {
            strCondiciones.append(" and fecha_corte <= ?");
            this.parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    this.parametrosConsulta.getFechaCorteFinal(), Boolean.FALSE));
            this.tipos.add(new DateType());
        }
        if (this.parametrosConsulta.getIdDivisa() != null
                && this.parametrosConsulta.getIdDivisa() > 0) {
            strCondiciones.append(" and id_divisa = ?");
            this.parametros.add(this.parametrosConsulta.getIdDivisa());
            this.tipos.add(new LongType());
        }
        if (this.parametrosConsulta.getIdCustodio() != null
                && this.parametrosConsulta.getIdCustodio() > 0) {
            strCondiciones.append(" and id_catbic = ?");
            this.parametros.add(this.parametrosConsulta.getIdCustodio());
            this.tipos.add(new LongType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getProporcion())) {
            strCondiciones.append(" and proporcion = ?");
            this.parametros.add(Double.valueOf(this.parametrosConsulta.getProporcion()));
            this.tipos.add(new DoubleType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getPorcentajeRetencion())) {
            strCondiciones.append(" and PORCENTAJE_RETENCION_REAL = ?");
            this.parametros.add(Double.valueOf(this.parametrosConsulta.getPorcentajeRetencion()));
            this.tipos.add(new DoubleType());
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getCantidadTitulos())) {
            strCondiciones.append(" and asignacion = ?");
            this.parametros.add(Long.valueOf(this.parametrosConsulta.getCantidadTitulos()));
            this.tipos.add(new LongType());
        }
        if (this.parametrosConsulta.getTipoFee() != null) {
            if (this.parametrosConsulta.getTipoFee() == CatalogosConstantes.ID_CON_FEE) {
                strCondiciones.append(" and (fee is not null and fee > 0)");
            } else if (this.parametrosConsulta.getTipoFee() == CatalogosConstantes.ID_SIN_FEE) {
                strCondiciones.append(" and (fee is null or fee = 0)");
            }
        }
        if (StringUtils.isNotBlank(this.parametrosConsulta.getFee())) {
            strCondiciones.append(" and fee = ?");
            this.parametros.add(Double.valueOf(this.parametrosConsulta.getFee()));
            this.tipos.add(new DoubleType());
        }
        if (this.parametrosConsulta.getIdTipoDerecho() != null
                && this.parametrosConsulta.getIdTipoDerecho() > 0) {
            strCondiciones.append(" and id_tipo_derecho = ? ");
            this.parametros.add(this.parametrosConsulta.getIdTipoDerecho());
            this.tipos.add(new LongType());
        }
        // id_estatus_derecho
        if (this.parametrosConsulta.getIdEstadoDerecho() != null
                && this.parametrosConsulta.getIdEstadoDerecho() > 0) {
            strCondiciones.append(" and id_estatus_derecho = ? ");
            this.parametros.add(this.parametrosConsulta.getIdEstadoDerecho());
            this.tipos.add(new LongType());
        }
        this.where = strCondiciones.toString();
    }

    /**
     * Crea el bloque having en caso de que la consulta así lo requiera.
     */
    private void crearHaving() {
        StringBuilder aux = new StringBuilder();
        if (this.aplicaHaving()) {
            aux.append(" having 1=1");
            if (StringUtils.isNotBlank(this.parametrosConsulta.getMontoBruto())) {
                aux.append(" and sum(monto_bruto) = ?");
                this.parametros.add(Double.valueOf(this.parametrosConsulta.getMontoBruto()));
                this.tipos.add(new DoubleType());
            }
            if (StringUtils.isNotBlank(this.parametrosConsulta.getMontoFee())) {
                aux.append(" and sum(monto_fee) = ?");
                this.parametros.add(Double.valueOf(this.parametrosConsulta.getMontoFee()));
                this.tipos.add(new DoubleType());
            }
            if (StringUtils.isNotBlank(this.parametrosConsulta.getImpuestoRetenido())) {
                aux.append(" and sum(impuesto_retenido) = ?");
                this.parametros.add(Double.valueOf(this.parametrosConsulta.getImpuestoRetenido()));
                this.tipos.add(new DoubleType());
            }
            if (StringUtils.isNotBlank(this.parametrosConsulta.getMontoNeto())) {
                aux.append(" and sum(monto_Neto) = ?");
                this.parametros.add(Double.valueOf(this.parametrosConsulta.getMontoNeto()));
                this.tipos.add(new DoubleType());
            }
        }
        this.having = aux.toString();
    }

    /**
     * Método que indica si aplica la condicion having
     * 
     * @return true si aplica; false en caso contrario.
     */
    private boolean aplicaHaving() {
        return this.parametrosConsulta.getMontoFee() != null
                || this.parametrosConsulta.getMontoBruto() != null
                || this.parametrosConsulta.getImpuestoRetenido() != null
                || this.parametrosConsulta.getMontoNeto() != null;
    }

    /**
     * Crea el ordenamiento de las diferentes consultas
     */
    private void crearOrdenamiento() {
        StringBuilder ordenamiento = new StringBuilder();
        ordenamiento.append(" order by");
        ordenamiento.append(" id_folio_institucion,");
        ordenamiento.append(" cuenta,");
        ordenamiento.append(" isin,");
        ordenamiento.append(" id_tipo_derecho,");
        ordenamiento.append(" porcentaje_retencion_real,");
        ordenamiento.append(" id_estatus_derecho desc, ");
        ordenamiento.append(" desc_estatus_derecho desc, ");
        if (this.parametrosConsulta.getFechaPagoInicial() != null
                && this.parametrosConsulta.getFechaPagoFinal() != null
                && this.parametrosConsulta.getFechaCorteInicial() == null
                && this.parametrosConsulta.getFechaCorteFinal() == null
                || this.parametrosConsulta.getFechaPagoInicial() != null
                && this.parametrosConsulta.getFechaPagoFinal() != null
                && this.parametrosConsulta.getFechaCorteInicial() != null
                && this.parametrosConsulta.getFechaCorteFinal() != null) {
            ordenamiento.append(" fecha_pago_derecho_capital desc,");
            ordenamiento.append(" fecha_corte desc ");
        } else if (this.parametrosConsulta.getFechaCorteInicial() != null
                && this.parametrosConsulta.getFechaCorteFinal() != null
                && this.parametrosConsulta.getFechaPagoInicial() == null
                && this.parametrosConsulta.getFechaPagoFinal() == null) {
            ordenamiento.append(" fecha_corte desc,");
            ordenamiento.append(" fecha_pago_derecho_capital desc ");
        }

        this.orderBy = ordenamiento.toString();
    }

    /**
     * Crea el agrupamiento
     */
    private void crearAgrupamiento() {
        StringBuilder strAgrupamiento = new StringBuilder();
        strAgrupamiento.append(" group by");
        strAgrupamiento.append(" id_folio_institucion,");
        strAgrupamiento.append(" cuenta,");
        strAgrupamiento.append(" porcentaje_retencion_real,");
        strAgrupamiento.append(" clave_tipo_valor,");
        strAgrupamiento.append(" clave_pizarra,");
        strAgrupamiento.append(" serie,");
        strAgrupamiento.append(" isin,");
        strAgrupamiento.append(" id_tipo_derecho, ");
        strAgrupamiento.append(" desc_tipo_derecho, ");
        strAgrupamiento.append(" fecha_corte,");
        strAgrupamiento.append(" fecha_pago_derecho_capital,");
        strAgrupamiento.append(" proporcion,");
        strAgrupamiento.append(" fee,");
        strAgrupamiento.append(" id_derecho_capital,");
        strAgrupamiento.append(" id_estatus_derecho,");
        strAgrupamiento.append(" desc_estatus_derecho,");
        strAgrupamiento.append(" divisa,");
        strAgrupamiento.append(" detalle_custodio");
        this.groupBy = strAgrupamiento.toString();
    }

    /**
     * Método para obtener el atributo numeroDivisas
     * 
     * @return El atributo numeroDivisas
     */
    public Integer getNumeroDivisas() {
        return this.numeroDivisas;
    }

    /**
     * Método para establecer el atributo numeroDivisas
     * 
     * @param numeroDivisas El valor del atributo numeroDivisas a establecer.
     */
    public void setNumeroDivisas(final Integer numeroDivisas) {
        this.numeroDivisas = numeroDivisas;
    }

}
