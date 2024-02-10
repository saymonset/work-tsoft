/**
 * Bursatec - Portal Internacional Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;

/**
 * Utileria para la Consulta de Derechos de Beneficiarios
 * 
 * @author Pablo Balderas
 */
public class ConsultaDerechosBeneficiariosUtil {

    /**
     * Método que genera un DetachedCriteria con los parámetros de consulta capturados por el
     * usuario.
     * 
     * @param parametrosConsulta Objeto con los parámetros de consulta.
     * @return DetachedCriteria
     */
    public static DetachedCriteria paramsConsultaCapitales(
            final ParamConsultaDetalleEjerDerCapTO parametrosConsulta, final boolean agregarOrden) {
        // Criteria
        DetachedCriteria criteria = DetachedCriteria.forClass(DerechoCapitalHistorico.class);
        /* ================PARAMETROS========================== */
        criteria.add(Restrictions.isNotNull("idDerechoCapitalHistorico"));
        criteria.add(Restrictions.gt("asignacion", 0L));

        // Ordenamiento
        if (agregarOrden) {
            criteria.addOrder(Order.asc("idFolioInstitucion"));
            criteria.addOrder(Order.asc("cuenta"));
            criteria.addOrder(Order.asc("isin"));
            criteria.addOrder(Order.asc("uoi"));
            if (parametrosConsulta.getFechaPagoInicial() != null
                    && parametrosConsulta.getFechaPagoFinal() != null
                    && parametrosConsulta.getFechaCorteInicial() == null
                    && parametrosConsulta.getFechaCorteFinal() == null
                    || parametrosConsulta.getFechaPagoInicial() != null
                    && parametrosConsulta.getFechaPagoFinal() != null
                    && parametrosConsulta.getFechaCorteInicial() != null
                    && parametrosConsulta.getFechaCorteFinal() != null) {
                criteria.addOrder(Order.desc("fechaPago"));
                criteria.addOrder(Order.desc("fechaCorte"));
            } else if (parametrosConsulta.getFechaCorteInicial() != null
                    && parametrosConsulta.getFechaCorteFinal() != null
                    && parametrosConsulta.getFechaPagoInicial() == null
                    && parametrosConsulta.getFechaPagoFinal() == null) {
                criteria.addOrder(Order.desc("fechaCorte"));
                criteria.addOrder(Order.desc("fechaPago"));
            }
        }

        // Fecha de corte
        if (parametrosConsulta.getFechaCorteInicial() != null
                && parametrosConsulta.getFechaCorteFinal() != null) {
            criteria.add(Restrictions.between(
                    "fechaCorte",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaCorteInicial(), Boolean.TRUE),
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaCorteFinal(), Boolean.FALSE)));
        } else if (parametrosConsulta.getFechaCorteFinal() != null) {
            criteria.add(Restrictions.le(
                    "fechaCorte",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaCorteFinal(), Boolean.TRUE)));
        } else if (parametrosConsulta.getFechaCorteInicial() != null) {
            criteria.add(Restrictions.ge(
                    "fechaCorte",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaCorteInicial(), Boolean.FALSE)));
        }
        // Fecha de pago
        if (parametrosConsulta.getFechaPagoInicial() != null
                && parametrosConsulta.getFechaPagoFinal() != null) {
            criteria.add(Restrictions.between(
                    "fechaPago",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaPagoInicial(), Boolean.TRUE),
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaPagoFinal(), Boolean.FALSE)));
        } else if (parametrosConsulta.getFechaPagoFinal() != null) {
            criteria.add(Restrictions.le(
                    "fechaPago",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaPagoFinal(), Boolean.TRUE)));
        } else if (parametrosConsulta.getFechaPagoInicial() != null) {
            criteria.add(Restrictions.ge(
                    "fechaPago",
                    DateUtils.preparaFechaConExtremoEnSegundos(
                            parametrosConsulta.getFechaPagoInicial(), Boolean.FALSE)));
        }
        // tipoValor
        if (StringUtils.isNotBlank(parametrosConsulta.getTv())) {
            criteria.add(Restrictions.eq("tipoValor", parametrosConsulta.getTv()));
        }
        // emisora
        if (StringUtils.isNotBlank(parametrosConsulta.getEmisora())) {
            criteria.add(Restrictions.eq("emisora", parametrosConsulta.getEmisora()));
        }
        // serie
        if (StringUtils.isNotBlank(parametrosConsulta.getSerie())) {
            criteria.add(Restrictions.eq("serie", parametrosConsulta.getSerie()));
        }
        // isin
        if (StringUtils.isNotBlank(parametrosConsulta.getIsin())) {
            criteria.add(Restrictions.eq("isin", parametrosConsulta.getIsin()));
        }

        // custodio
        if (parametrosConsulta.getIdCustodio() != null && parametrosConsulta.getIdCustodio() > 0) {
            criteria.add(Restrictions.eq("idCatBic", parametrosConsulta.getIdCustodio()));
        }

        // Institucion
        if (StringUtils.isNotBlank(parametrosConsulta.getInstitucion())) {
            criteria.add(Restrictions.eq("idFolioInstitucion", parametrosConsulta.getInstitucion()));
        }
        // cuenta
        if (StringUtils.isNotBlank(parametrosConsulta.getCuenta())) {
            criteria.add(Restrictions.eq("cuenta", parametrosConsulta.getCuenta()));
        }

        // divisa
        if (parametrosConsulta.getIdDivisa() != null && parametrosConsulta.getIdDivisa() > 0) {
            criteria.add(Restrictions.eq("idDivisa", parametrosConsulta.getIdDivisa()));
        }

        // idDerechoCapital
        if (parametrosConsulta.getIdDerechoCapital() != null
                && parametrosConsulta.getIdDerechoCapital() > 0) {
            criteria.add(Restrictions.eq("idDerechoCapital",
                    parametrosConsulta.getIdDerechoCapital()));
        }

        // Porcentaje de retención
        if (StringUtils.isNotBlank(parametrosConsulta.getPorcentajeRetencion())) {
            criteria.add(Restrictions.eq("porcentajeRetencionReal",
                    Double.valueOf(parametrosConsulta.getPorcentajeRetencion())));
        }
        // Nombre o razón social
        if (StringUtils.isNotBlank(parametrosConsulta.getNombreBeneficiario())) {
            criteria.add(Restrictions.like("beneficiario",
                    "%" + parametrosConsulta.getNombreBeneficiario() + "%"));
        }
        // RFC
        if (StringUtils.isNotBlank(parametrosConsulta.getRfc())) {
            criteria.add(Restrictions.eq("rfc", parametrosConsulta.getRfc()));
        }
        // Monto bruto
        if (StringUtils.isNotBlank(parametrosConsulta.getMontoBruto())) {
            criteria.add(Restrictions.eq("montoBruto",
                    Double.valueOf(parametrosConsulta.getMontoBruto())));
        }
        // Monto neto
        if (StringUtils.isNotBlank(parametrosConsulta.getMontoNeto())) {
            criteria.add(Restrictions.eq("montoNeto",
                    Double.valueOf(parametrosConsulta.getMontoNeto())));
        }
        // Impuesto Retenido
        if (StringUtils.isNotBlank(parametrosConsulta.getImpuestoRetenido())) {
            criteria.add(Restrictions.eq("impuestoRetenido",
                    Double.valueOf(parametrosConsulta.getImpuestoRetenido())));
        }
        // Proporcion
        if (StringUtils.isNotBlank(parametrosConsulta.getProporcion())) {
            criteria.add(Restrictions.eq("proporcion",
                    Double.valueOf(parametrosConsulta.getProporcion())));
        }
        // OUI
        if (StringUtils.isNotBlank(parametrosConsulta.getUoi())) {
            criteria.add(Restrictions.eq("uoi", parametrosConsulta.getUoi()));
        }
        // Pais
        if (StringUtils.isNotBlank(parametrosConsulta.getPais())) {
            criteria.add(Restrictions.like("paisIncorporacion", "%" + parametrosConsulta.getPais()
                    + "%"));
        }
        // Cantidad títulos
        if (StringUtils.isNotBlank(parametrosConsulta.getCantidadTitulos())) {
            criteria.add(Restrictions.eq("asignacion",
                    Long.valueOf(parametrosConsulta.getCantidadTitulos())));
        }
        // Referencia custodio
        if (StringUtils.isNotBlank(parametrosConsulta.getReferenciaCustodio())) {
            criteria.add(Restrictions.eq("adp", parametrosConsulta.getReferenciaCustodio()));
        }
        // Tipo de formato
        if (StringUtils.isNotBlank(parametrosConsulta.getTipoFormato())
                && !CatalogosConstantes.VALOR_TODOS_STR.equals(parametrosConsulta.getTipoFormato())) {
            criteria.add(Restrictions.eq("tipoFormato", parametrosConsulta.getTipoFormato()));
        }
        // Tipo beneficiario
        if (parametrosConsulta.getIdTipoBeneficiario() != null
                && parametrosConsulta.getIdTipoBeneficiario() > 0) {
            criteria.add(Restrictions.eq("idTipoBeneficiario",
                    parametrosConsulta.getIdTipoBeneficiario()));
        }
        // GIIN
        if (StringUtils.isNotBlank(parametrosConsulta.getGiin())) {
            criteria.add(Restrictions.eq("giin", parametrosConsulta.getGiin()));
        }
        // Estado Formato W
        if (StringUtils.isNotBlank(parametrosConsulta.getEstadoFormatoW())) {
            criteria.add(Restrictions.eq("estadoW", parametrosConsulta.getEstadoFormatoW()));
        }
        // Reference Number
        if (StringUtils.isNotBlank(parametrosConsulta.getReferenceNumber())) {
            criteria.add(Expression.like("referenceNumber",
                    parametrosConsulta.getReferenceNumber().toLowerCase(), MatchMode.ANYWHERE)
                    .ignoreCase());
            // criteria.add(Restrictions.like("referenceNumber",
            // parametrosConsulta.getReferenceNumber().toLowerCase(), MatchMode.ANYWHERE));
        }
        // tipoDerecho
        if (parametrosConsulta.getIdTipoDerecho() != null
                && parametrosConsulta.getIdTipoDerecho() > 0) {
            criteria.add(Restrictions.eq("idTipoDerecho", parametrosConsulta.getIdTipoDerecho()));
        }
        // tipoDerecho
        if (parametrosConsulta.getIdEstadoDerecho() != null
                && parametrosConsulta.getIdEstadoDerecho() > 0) {
            criteria.add(Restrictions.eq("idEstatusDerecho",
                    parametrosConsulta.getIdEstadoDerecho()));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

}
