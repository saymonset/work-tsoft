/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSicView;
import com.indeval.portalinternacional.middleware.servicios.modelo.VBitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.vo.HistorialBitacoraOperacionesSICDTO;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class OperacionSicDaoImpl extends BaseDaoHibernateImpl implements OperacionSicDao, Constantes {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(OperacionSicDaoImpl.class);

    private static final BigInteger CAMBIO_BOVEDA = BigInteger.ONE;

    private static final String TIPO_MENSAJE_ENTREGA = "542";

    private static final String TIPO_MENSAJE_RECEPCION = "540";
    
    /** Definicion de la variable para queries JDBC */
    private JdbcTemplate jdbcTemplate;

    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperaciones(OperacionSic, PaginaVO)
     */
    public PaginaVO findOperaciones(OperacionSic operacionSic, PaginaVO paginaVO, Boolean obtenerTotalReg) {

        log.info("Entrando a findOperaciones()");
        if (operacionSic == null) {
            throw new IllegalArgumentException("No existen parametros para la consulta");
        }
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
        
        DetachedCriteria detachedCriteria = null;
        
        if (obtenerTotalReg){
	        detachedCriteria = this.obtieneCriteriaFindOpsView(operacionSic);
	        detachedCriteria.setProjection(Projections.rowCount());
	        final List listaCount = this.getHibernateTemplate().findByCriteria(detachedCriteria);
	        if (listaCount == null || listaCount.isEmpty()) {
	            throw new IllegalArgumentException("Error al tratar de obtener el total de registros");
	        }
	        paginaVO.setTotalRegistros((Integer) listaCount.get(0));
        }

        boolean continuarConsulta = !obtenerTotalReg || 
        		(paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0);
        
        if (continuarConsulta) {
            detachedCriteria = this.obtieneCriteriaFindOpsView(operacionSic);
            detachedCriteria.addOrder(Order.desc("fechaHoraAlta")).addOrder(Order.desc("folioControl"));
            paginaVO.setRegistros(this.getHibernateTemplate().findByCriteria(detachedCriteria, paginaVO.getOffset(),
                    paginaVO.getRegistrosXPag()));
        }
        return paginaVO;
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperacionByFolioControl(java.math.BigInteger)
     */
    public OperacionSic findOperacionByFolioControl(BigInteger folioControl) {
        
        log.info("Entrando a findOperacionByFolioControl(BigInteger) :: folioControl: " + folioControl);
        
        if (folioControl == null || folioControl.compareTo(BigInteger.valueOf(0)) == 0) {
            throw new IllegalArgumentException("Error en el folio recibido");
        }
        
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setFolioControl(folioControl);
        DetachedCriteria detachedCriteria = obtieneCriteriaFindOperaciones(operacionSic);
        detachedCriteria = obtieneCriteriaFindOperaciones(operacionSic);
        
        List listaRegistros = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (listaRegistros == null || listaRegistros.isEmpty()) {
            return null;
        }
        
        return (OperacionSic) listaRegistros.get(0);
    }

    /**
     * @param operacionSic
     * @return
     */
    private DetachedCriteria obtieneCriteriaFindOpsView(final OperacionSic operacionSic) {

        log.info("Entrando a obtieneCriteriaFindOpsView()");
        AgenteVO agenteVO = new AgenteVO();
        if (operacionSic.getCuentaNombrada() != null) {
            agenteVO = ConvertBO2VO.crearAgenteVO(operacionSic.getCuentaNombrada());
        }
        EmisionVO emisionVO = new EmisionVO();
        if (operacionSic.getEmision() != null) {
            emisionVO = ConvertBO2VO.crearEmisionVO(operacionSic.getEmision());
        }
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSicView.class);
        if (StringUtils.isNotBlank(agenteVO.getId())) {
            detachedCriteria.add(Restrictions.eq("id", agenteVO.getId()));
        }
        if (StringUtils.isNotBlank(agenteVO.getFolio())) {
            detachedCriteria.add(Restrictions.eq("folio", agenteVO.getFolio()));
        }
        if (StringUtils.isNotBlank(operacionSic.getClaveCupon())) {
            detachedCriteria.add(Restrictions.eq("cupon", operacionSic.getClaveCupon()));
        }
        if (StringUtils.isNotBlank(agenteVO.getCuenta())) {
            detachedCriteria.add(Restrictions.eq("cuenta", agenteVO.getCuenta()));
        }
        if (emisionVO != null) {
            if (StringUtils.isNotBlank(emisionVO.getTv())) {
                detachedCriteria.add(Restrictions.eq("tipoValor", emisionVO.getTv()));
            }
            if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
                detachedCriteria.add(Restrictions.eq("emisora", emisionVO.getEmisora()));
            }
            if (StringUtils.isNotBlank(emisionVO.getSerie())) {
                detachedCriteria.add(Restrictions.eq("serie", emisionVO.getSerie()));
            }
            if (StringUtils.isNotBlank(emisionVO.getIsin())) {
                detachedCriteria.add(Restrictions.eq("isin", emisionVO.getIsin()));
            }
        }
        if (operacionSic.getEstatusOperacion() != null
                && operacionSic.getEstatusOperacion().getIdEstatusOperacion() != null) {
            detachedCriteria.add(Restrictions.eq("pkIdEstatusOperacion", operacionSic.getEstatusOperacion()
                    .getIdEstatusOperacion()));
        }
        if (StringUtils.isNotBlank(operacionSic.getDivisa())) {
            detachedCriteria.add(Restrictions.eq("divisa", operacionSic.getDivisa()));
        }
        if (operacionSic.getImporte() != null && operacionSic.getImporte().doubleValue() > 0.0) {
            detachedCriteria.add(Restrictions.eq("importe",
                    operacionSic.getImporte().setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        if (operacionSic.getCantidadTitulos() != null && operacionSic.getCantidadTitulos().intValue() > 0) {
            detachedCriteria.add(Restrictions.eq("cantidadOperada", operacionSic.getCantidadTitulos()));
        }
        if (operacionSic.getReferenciaOperacion() != null
                && StringUtils.isNotBlank(operacionSic.getReferenciaOperacion())) {
            detachedCriteria.add(Restrictions.eq("referenciaOperacion", operacionSic.getReferenciaOperacion()));
        }
        if (operacionSic.getFechaConsulta() != null && operacionSic.getFechaConsulta()[0] != null
                && operacionSic.getFechaConsulta()[1] != null) {
            final Date[] fechas =
                    DateUtils.preparaIntervaloFechas(operacionSic.getFechaConsulta()[0],
                            operacionSic.getFechaConsulta()[1]);
            if (fechas == null || fechas.length != 2 || fechas[0] == null || fechas[1] == null) {
                throw new IllegalArgumentException("Error al intentar obtener el rango de fechas");
            }
            detachedCriteria.add(Restrictions.between("fechaActualizacion", fechas[0], fechas[1]));
        }
        if (StringUtils.isNotBlank(operacionSic.getTipoTraspaso())) {
            if (TIPO_MOVTO_E.equalsIgnoreCase(operacionSic.getTipoTraspaso())) {
                detachedCriteria.add(Restrictions.in("operacion", new Object[] {TLP_DESC_ENTREGA, TCP_DESC_ENTREGA}));
            } else {
                detachedCriteria.add(Restrictions
                        .in("operacion", new Object[] {TLP_DESC_RECEPCION, TCP_DESC_RECEPCION}));
            }
        }
        if (StringUtils.isNotBlank(operacionSic.getTipoOperacion())) {
            if (TRASPASO_LIBRE.equalsIgnoreCase(operacionSic.getTipoOperacion())) {
                detachedCriteria.add(Restrictions.in("operacion", new Object[] {TLP_DESC_ENTREGA, TLP_DESC_RECEPCION}));
            } else {
                detachedCriteria.add(Restrictions.in("operacion", new Object[] {TCP_DESC_ENTREGA, TCP_DESC_RECEPCION}));
            }
        }
        if (operacionSic.getOrigenPfi() != null) {
            detachedCriteria.add(Restrictions.eq("origenPfi", operacionSic.getOrigenPfi()));
        }
        if (operacionSic.getFolioControl() != null) {
            detachedCriteria.add(Restrictions.eq("folioControl", operacionSic.getFolioControl()));
        }
        if (operacionSic.getIdOperacionesSic() != null) {
            detachedCriteria.add(Restrictions.eq("pkIdOperacionesSic", operacionSic.getIdOperacionesSic()));
        }
        if (operacionSic.getCatBic() != null && operacionSic.getCatBic().getDetalleCustodio() != null) {
            detachedCriteria.add(Restrictions.eq("descripcionCustodio", operacionSic.getCatBic().getDetalleCustodio()));
        }

        //Junio 2107 - Adicion de la propiedad cambio de boveda cuando el usuario que consulta tiene el rol CAMBIO_BOVEDA.
        if (operacionSic.getCambioBoveda() != null && operacionSic.getCambioBoveda().equals(CAMBIO_BOVEDA)) {
            detachedCriteria.add(Restrictions.eq("cambioBoveda", CAMBIO_BOVEDA));
        }
        else {
            detachedCriteria.add(Restrictions.eq("cambioBoveda", BigInteger.ZERO));
        }

        //Adicion de la propiedad depositanteLiquidador por anexarse a los parametros de busqueda de la pantalla.
        if (operacionSic.getDepositanteLiquidador() != null) {
            detachedCriteria.add(Restrictions.eq("depositanteLiquidador", operacionSic.getDepositanteLiquidador().trim()));
        }

        return detachedCriteria;
    }

    /**
     * Obtiene el criterio de busqueda correspondiente a los filtros proporcionados
     * 
     * @param operacionSic
     * @return DetachedCriteria
     */
    private DetachedCriteria obtieneCriteriaFindOperaciones(OperacionSic operacionSic) {
        
        log.info("OperacionSicDaoImpl :: Entrando a obtieneCriteriaFindOperaciones()");
        
        AgenteVO agenteVO = new AgenteVO();
        if (operacionSic.getCuentaNombrada() != null) {
            agenteVO = ConvertBO2VO.crearAgenteVO(operacionSic.getCuentaNombrada());
        }
        EmisionVO emisionVO = new EmisionVO();
        if (operacionSic.getEmision() != null) {
            emisionVO =  ConvertBO2VO.crearEmisionVO(operacionSic.getEmision());
        }
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSic.class)
            .createAlias("cuentaNombrada", "cn")
            .createAlias("cn.institucion", "i")
            .createAlias("i.tipoInstitucion", "ti")
            .createAlias("emision", "e")
            .createAlias("e.instrumento", "in")
            .createAlias("e.emisora", "em")
            .createAlias("estatusOperacion", "stOp")
            .createAlias("catBic", "cb");
            
        if (StringUtils.isNotBlank(agenteVO.getId())) {
            detachedCriteria.add(Restrictions.eq("ti.claveTipoInstitucion", agenteVO.getId()));
        }
        
        if (StringUtils.isNotBlank(agenteVO.getFolio())) {
            detachedCriteria.add(Restrictions.eq("i.folioInstitucion", agenteVO.getFolio()));
        }
        
        /* Filtramos por cupon */
        if (StringUtils.isNotBlank(operacionSic.getClaveCupon())) {
        	DetachedCriteria subQueryCupon = DetachedCriteria.forClass(Cupon.class);
        	subQueryCupon.add(Restrictions.eq("claveCupon", operacionSic.getClaveCupon()));
        	subQueryCupon.add(Restrictions.eq("estadoCupon.idEstatusCupon",ESTATUS_CUPON_VIGENTE));
        	subQueryCupon.setProjection(Projections.property("idEmision"));
        	detachedCriteria.add(Property.forName("e.idEmision").in(subQueryCupon));
        }
        
        if (StringUtils.isNotBlank(agenteVO.getCuenta())) {
            detachedCriteria.add(Restrictions.eq("cn.cuenta", agenteVO.getCuenta()));
        }
        if (emisionVO != null) {
            if (StringUtils.isNotBlank(emisionVO.getTv())) {
                detachedCriteria.add(Restrictions.eq("in.claveTipoValor", emisionVO.getTv()));
            }
            if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
                detachedCriteria.add(Restrictions.eq("em.clavePizarra", emisionVO.getEmisora()));
            }
            if (StringUtils.isNotBlank(emisionVO.getSerie())) {
                detachedCriteria.add(Restrictions.eq("e.serie", emisionVO.getSerie()));
            }
            if(StringUtils.isNotBlank(emisionVO.getIsin())){
            	detachedCriteria.add(Restrictions.eq("e.isin", emisionVO.getIsin()));
            }
        }
        if (operacionSic.getEstatusOperacion() != null 
                && operacionSic.getEstatusOperacion().getIdEstatusOperacion() != null) {
            detachedCriteria.add(
                    Restrictions.eq("estatusOperacion.idEstatusOperacion", 
                            operacionSic.getEstatusOperacion().getIdEstatusOperacion()));
        }
        if (StringUtils.isNotBlank(operacionSic.getDivisa())) {
            detachedCriteria.add(Restrictions.eq("divisa", operacionSic.getDivisa()));
        }
        if ((operacionSic.getImporte() != null) && (operacionSic.getImporte().doubleValue() > (double)0.0)) {
            detachedCriteria.add(Restrictions.eq("importe", operacionSic.getImporte().setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        if ((operacionSic.getCantidadTitulos() != null) && (operacionSic.getCantidadTitulos().intValue() > 0)) {
            detachedCriteria.add(Restrictions.eq("cantidadTitulos", operacionSic.getCantidadTitulos()));
        }
        /* 20/11/2012 se agrega condicion para nuevo filtro    */
        if(operacionSic.getReferenciaOperacion() != null && StringUtils.isNotBlank(operacionSic.getReferenciaOperacion())){
        	detachedCriteria.add(Restrictions.eq("referenciaOperacion", operacionSic.getReferenciaOperacion()));
        }
        Date[] fechas = null;
        if (operacionSic.getFechaConsulta() != null && operacionSic.getFechaConsulta()[0] != null && operacionSic.getFechaConsulta()[1] != null) {
            fechas = DateUtils.preparaIntervaloFechas(operacionSic.getFechaConsulta()[0], operacionSic.getFechaConsulta()[1]);
            if (fechas == null || fechas.length != 2 || fechas[0] == null || fechas[1] == null) {
                throw new IllegalArgumentException("Error al intentar obtener el rango de fechas");
            }
        }
        if (StringUtils.isNotBlank(operacionSic.getTipoTraspaso())) {
            if (TIPO_MOVTO_E.equalsIgnoreCase(operacionSic.getTipoTraspaso())) {
                detachedCriteria.add(Restrictions.in("tipoMensaje", new Object[]{TLP_ENTREGA, TCP_ENTREGA}));
            }
            else {
                detachedCriteria.add(Restrictions.in("tipoMensaje", new Object[]{TLP_RECEPCION, TCP_RECEPCION}));
            }
        }
        if (StringUtils.isNotBlank(operacionSic.getTipoOperacion())) {
            if (TRASPASO_LIBRE.equalsIgnoreCase(operacionSic.getTipoOperacion())) {
                detachedCriteria.add(Restrictions.in("tipoMensaje", new Object[]{TLP_ENTREGA, TLP_RECEPCION}));
            }
            else {
                detachedCriteria.add(Restrictions.in("tipoMensaje", new Object[]{TCP_ENTREGA, TCP_RECEPCION}));
            }
        }
        /** se agrega el origenPfi al criterio de busqueda*/
        if(operacionSic.getOrigenPfi() != null){
        	detachedCriteria.add(Restrictions.eq("origenPfi", operacionSic.getOrigenPfi()));
        }
        if (operacionSic.getFolioControl() != null) {
            detachedCriteria.add(Restrictions.eq("folioControl", operacionSic.getFolioControl()));
        }
        
        if(operacionSic.getIdOperacionesSic() != null){
        	detachedCriteria.add(Restrictions.eq("idOperacionesSic", operacionSic.getIdOperacionesSic()));
        }
        
        if(operacionSic.getCatBic() != null){
        	if (operacionSic.getCatBic().getDetalleCustodio() != null)
        		detachedCriteria.add(Restrictions.eq("cb.detalleCustodio", operacionSic.getCatBic().getDetalleCustodio()));
        }
        
        /* Filtra la consulta por estatus y fecha correspondiente a cada estatus */
        if (fechas != null) {
            if (operacionSic.getEstatusOperacion() == null 
                    || operacionSic.getEstatusOperacion().getIdEstatusOperacion() == null) {
                detachedCriteria.add(
                    Restrictions.disjunction()
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_PENDIENTE_AUTORIZAR, ST_OPER_NOTIFICADA, ST_OPER_PENDIENTE,ST_OPER_RETENIDA}))
                                .add(Restrictions.between("fechaNotificacion", fechas[0], fechas[1])))
                        .add(Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIADA))
                                .add(Restrictions.between("fecha520", fechas[0], fechas[1])))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_CONFIRMADA, ST_OPER_CONFIRMADA_PARCIAL}))
                                .add(Restrictions.disjunction()
                                        .add(Restrictions.between("fecha530", fechas[0], fechas[1]))
                                        .add(Restrictions.between("fecha572", fechas[0], fechas[1]))))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_ENVIO_CANCELACION, ST_OPER_CANCELADA, ST_OPER_CANCEL_SIST, ST_OPER_REMANENTE_CANCELADO, ST_OPER_ENVIO_CANCELACION_REMANENTE}))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1])))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_MENSAJE_LIBERACION, ST_OPER_ENVIO_LIBERACION, ST_OPER_PENDIENTE_LIBERAR, ST_OPER_PENDIENTE_LIBERAR_PRCIAL, ST_OPER_LIBERADA, ST_OPER_LIBERADA_PARCIAL, ST_OPER_HABILITADA}))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1])))
                );
            }
            else {
                if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_AUTORIZAR) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_AUTORIZAR))
                                .add(Restrictions.between("fechaNotificacion", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_NOTIFICADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_NOTIFICADA))
                                .add(Restrictions.between("fechaNotificacion", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIADA))
                                .add(Restrictions.between("fecha520", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CONFIRMADA))
                                .add(Restrictions.disjunction()
                                        .add(Restrictions.between("fecha530", fechas[0], fechas[1]))
                                        .add(Restrictions.between("fecha572", fechas[0], fechas[1])))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA_PARCIAL) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CONFIRMADA_PARCIAL))
                                .add(Restrictions.disjunction()
                                        .add(Restrictions.between("fecha530", fechas[0], fechas[1]))
                                        .add(Restrictions.between("fecha572", fechas[0], fechas[1])))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE))
                                .add(Restrictions.between("fechaNotificacion", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_CANCELACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_CANCELACION))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCEL_SIST) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CANCEL_SIST))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCELADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CANCELADA))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_REMANENTE_CANCELADO) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_REMANENTE_CANCELADO))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_CANCELACION_REMANENTE) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_CANCELACION_REMANENTE))
                                .add(Restrictions.between("fecha592", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_MENSAJE_LIBERACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_MENSAJE_LIBERACION))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_LIBERACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_LIBERACION))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_LIBERAR))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR_PRCIAL) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_LIBERAR_PRCIAL))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                }
                else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_LIBERADA))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                    /**se agrega filtro para las operaciones en estatus Retenidas*/
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA_PARCIAL) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_LIBERADA_PARCIAL))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                    /**se agrega filtro para las operaciones en estatus Retenidas*/
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_RETENIDA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_RETENIDA))
                                .add(Restrictions.between("fechaNotificacion", fechas[0], fechas[1]))
                    );
                    /**se agrega filtro para las operaciones en estatus Habilitadas*/
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_HABILITADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_HABILITADA))
                                .add(Restrictions.between("fechaHora", fechas[0], fechas[1]))
                    );
                }
            }
        }
        else {
        	if (operacionSic.getEstatusOperacion() == null 
                    || operacionSic.getEstatusOperacion().getIdEstatusOperacion() == null) {

                detachedCriteria.add(
                    Restrictions.disjunction()
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_PENDIENTE_AUTORIZAR, ST_OPER_NOTIFICADA, ST_OPER_PENDIENTE,ST_OPER_RETENIDA})))
                        .add(Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIADA)))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_CONFIRMADA, ST_OPER_CONFIRMADA_PARCIAL})))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_ENVIO_CANCELACION, ST_OPER_CANCELADA, ST_OPER_CANCEL_SIST, ST_OPER_REMANENTE_CANCELADO, ST_OPER_ENVIO_CANCELACION_REMANENTE})))
                        .add(Restrictions.conjunction().add(Restrictions.in("stOp.idEstatusOperacion", new Object[]{ST_OPER_MENSAJE_LIBERACION, ST_OPER_ENVIO_LIBERACION, ST_OPER_PENDIENTE_LIBERAR, ST_OPER_PENDIENTE_LIBERAR_PRCIAL, ST_OPER_LIBERADA, ST_OPER_LIBERADA_PARCIAL, ST_OPER_HABILITADA})))
                );
            }
            else {
                if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_AUTORIZAR) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_AUTORIZAR))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_NOTIFICADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_NOTIFICADA))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIADA))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CONFIRMADA))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA_PARCIAL) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CONFIRMADA_PARCIAL))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_CANCELACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_CANCELACION))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCEL_SIST) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CANCEL_SIST))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCELADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_CANCELADA))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_MENSAJE_LIBERACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_MENSAJE_LIBERACION))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_LIBERACION) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_LIBERACION))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_LIBERAR))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR_PRCIAL) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_PENDIENTE_LIBERAR_PRCIAL))
                    );
                }
                else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_LIBERADA))
                    );/**se agrega filtro para las operaciones en estatus Retenidas*/
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_RETENIDA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_RETENIDA))
                    );/**se agrega filtro para las operaciones en estatus Habilitadas*/
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_HABILITADA) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_HABILITADA))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_REMANENTE_CANCELADO) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_REMANENTE_CANCELADO))
                    );
                } else if (operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_CANCELACION_REMANENTE) {
                    detachedCriteria.add(
                            Restrictions.conjunction().add(Restrictions.eq("stOp.idEstatusOperacion", ST_OPER_ENVIO_CANCELACION_REMANENTE))
                    );
                }
            }
        }
        return (detachedCriteria); 
        
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperacionByIdOperacion(java.lang.Long)
     */
    public OperacionSic findOperacionByIdOperacion(Long idOperacion){
    	if (idOperacion == null || idOperacion.compareTo(Long.valueOf(0)) == 0) {
    		throw new IllegalArgumentException("Error en el folio recibido");
        }
        
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setIdOperacionesSic(idOperacion);
        DetachedCriteria detachedCriteria = obtieneCriteriaFindOperaciones(operacionSic);
        detachedCriteria = obtieneCriteriaFindOperaciones(operacionSic);
        List listaRegistros = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (listaRegistros == null || listaRegistros.isEmpty()) {
            return null;
        }
        
        return (OperacionSic) listaRegistros.get(0);
        
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperacionesPorListaDeFolioControl(List, com.indeval.portalinternacional.persistence.dao.PaginaVO)
     */
    public PaginaVO findOperacionesPorListaDeFolioControl(List<BigInteger> foliosControl, PaginaVO paginaVO) {
        log.info("####### Entrando a findOperacionesPorListaDeFolioControl()...");

        if (foliosControl == null) {
            throw new IllegalArgumentException("No existen parametros para la consulta");
        }

        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        DetachedCriteria detachedCriteria = this.obtieneCriteriaFindOperacionesPorListaDeFolioControl(foliosControl);
      
        detachedCriteria.setProjection(Projections.rowCount());
        List listaCount = this.getHibernateTemplate().findByCriteria(detachedCriteria);
        if (listaCount == null || listaCount.isEmpty()) {
            throw new IllegalArgumentException("Error al tratar de obtener el total de registros");
        }
        paginaVO.setTotalRegistros((Integer) listaCount.get(0));        
        if (paginaVO.getTotalRegistros() != null || paginaVO.getTotalRegistros().intValue() > 0) {
            detachedCriteria = this.obtieneCriteriaFindOperacionesPorListaDeFolioControl(foliosControl);          
            detachedCriteria.addOrder(Order.desc("folioControl"));
            Map valores = new HashMap<String, List>();
            valores.put("totalRegistros",  this.getHibernateTemplate().findByCriteria(detachedCriteria));
            paginaVO.setValores(valores);
            paginaVO.setRegistros(this.getHibernateTemplate().findByCriteria(detachedCriteria, paginaVO.getOffset(), 
                                                                             paginaVO.getRegistrosXPag()));          
        }        
        return paginaVO;
    }
    

    /**
     * Obtiene el criterio de busqueda correspondiente al listado de folios de control proporcionado.
     * 
     * @param foliosControl
     * @return DetachedCriteria
     */
    private DetachedCriteria obtieneCriteriaFindOperacionesPorListaDeFolioControl(List<BigInteger> foliosControl) {
        log.info("####### Entrando a obtieneCriteriaFindOperacionesPorListaDeFolioControl()...");

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSic.class)
            .createAlias("cuentaNombrada", "cn")
            .createAlias("cn.institucion", "i")
            .createAlias("i.tipoInstitucion", "ti")
            .createAlias("emision", "e")
            .createAlias("e.instrumento", "in")
            .createAlias("e.emisora", "em")
            .createAlias("estatusOperacion", "stOp")            
            .createAlias("catBic", "cb");

        detachedCriteria.add(Restrictions.in("folioControl", foliosControl));
        return (detachedCriteria); 
    }
    
    /**
     * Obtiene los registros en bitacora operaciones que su estado sea por Autorizar.
     * 
     * @author Marco Edgar Valencia Arana, KODE
     * @param foliosControl la lista de folios control a buscar
     * @return bitacorasEncontradas lista de registros bitacora
     */
    public List<BitacoraOperacionesSic> obtieneBitacoraOperacionSic(List<BigInteger> foliosControl){
    	 if (foliosControl == null) {
             throw new IllegalArgumentException("No existen parametros para la consulta");
         }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BitacoraOperacionesSic.class);
        detachedCriteria.add(Restrictions.eq("idEstatusBitacora", new Long(1)));
        if(!foliosControl.isEmpty())
        	detachedCriteria.add(Restrictions.in("folioControl", foliosControl));
//        detachedCriteria.add(Restrictions.isNull("numeroLiquidacion"));
        List<BitacoraOperacionesSic> bitacoraEncontradas = this.getHibernateTemplate().findByCriteria(detachedCriteria);
    	return bitacoraEncontradas;
    }
    
    public List<BitacoraOperacionesSic> obtieneBitacoraOperacionSic(Long folioControl, Long numeroLiquidacion){
	   	 if (folioControl == null) {
	         throw new IllegalArgumentException("No existen parametros para la consulta");
	     }
	    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BitacoraOperacionesSic.class);
	    detachedCriteria.add(Restrictions.eq("idEstatusBitacora", new Long(1)));
	    detachedCriteria.add(Restrictions.eq("folioControl", new BigInteger(folioControl.toString())));
	    detachedCriteria.add(Restrictions.eq("numeroLiquidacion", numeroLiquidacion));
	    List<BitacoraOperacionesSic> bitacoraEncontradas = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return bitacoraEncontradas;
    }
    
    
    /**
     * Obtiene los registros del historial de bitacora SIC ya sea de Cambio de Boveda o no.
     * @param El objeto con los parametros de busqueda
     * @return bitacorasEncontradas El objeto donde se regresaran los registros encontrados
     * @return esParaCambioDeBoveda Boolean que indica si la consulta se requiere para Camibio de Boveda (true), en caso
     * contrario false.
     */
    public PaginaVO obtenerHistorialBitacoraOperacionesSic(HistorialBitacoraOperacionesSICDTO historial, PaginaVO paginaVO, 
                                                           boolean esParaCambioDeBoveda) {
        log.info("\n\n####### Entrando a obtenerHistorialBitacoraOperacionesSic()...");

        if (historial == null) {
            throw new IllegalArgumentException("No existen parametros para la consulta");
        }

        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        DetachedCriteria detachedCriteria = this.obtieneCriteriaHistorialBitacoraSIC(historial, esParaCambioDeBoveda);

        detachedCriteria.setProjection(Projections.rowCount());
        List listaCount = this.getHibernateTemplate().findByCriteria(detachedCriteria);
        if (listaCount == null || listaCount.isEmpty()) {
            throw new IllegalArgumentException("Error al tratar de obtener el total de registros");
        }        
        paginaVO.setTotalRegistros((Integer) listaCount.get(0));
        if (paginaVO.getTotalRegistros() != null || paginaVO.getTotalRegistros().intValue() > 0) {
            detachedCriteria = this.obtieneCriteriaHistorialBitacoraSIC(historial, esParaCambioDeBoveda);          
            detachedCriteria.addOrder(Order.desc("folioControl"));
            Map valores = new HashMap<String, List>();
            valores.put("totalRegistros",  this.getHibernateTemplate().findByCriteria(detachedCriteria));
            paginaVO.setValores(valores);
            paginaVO.setRegistros(this.getHibernateTemplate().findByCriteria(detachedCriteria, paginaVO.getOffset(), 
                    paginaVO.getRegistrosXPag()));          
        }        
        return paginaVO;
    }

    /**
     * Obtiene el Criteria basandose en los parametros que se reciben.
     * @param historialboolean El objeto donde vienen los parametros seleccionados por el usuario.
     * @param esParaCambioDeBoveda Boolean que indica si la consulta se requiere para Camibio de Boveda (true), en caso
     * contrario false.
     * @return El DetachedCriteria armado.
     */
    private DetachedCriteria obtieneCriteriaHistorialBitacoraSIC(HistorialBitacoraOperacionesSICDTO historial, 
                                                                 boolean esParaCambioDeBoveda) {
        log.info("\n\n####### Entrando a obtieneCriteriaHistorialBitacoraSIC()...");

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VBitacoraOperacionesSic.class);
        if (esParaCambioDeBoveda) {
            detachedCriteria.add(Restrictions.eq("cambioBoveda", 1));
        }
        else {
            detachedCriteria.add(Restrictions.eq("cambioBoveda", 0));
        }
        if (historial.getFolioControl() != null) {
            detachedCriteria.add(Restrictions.eq("folioControl", historial.getFolioControl()));
        }
        if (historial.getFechaInAut() != null) {
            detachedCriteria.add(Restrictions.between("fechaSolicitudAutorizador", historial.getFechaInAut(), historial.getFechafinAut()));
        }
        if (historial.getFechaInOp() != null) {
            detachedCriteria.add(Restrictions.between("fechaSolicitudOperador", historial.getFechaInOp(), historial.getFechaFinOp()));
        }
        if (!historial.getDetalleCustodio().equals("-1")) {
            detachedCriteria.add(Restrictions.eq("detalleCustodio", historial.getDetalleCustodio()));
        }
        if (!historial.getOperacion().equals("-1") || !historial.getTipoOperacion().equals("-1")) {
            if (!historial.getOperacion().equals("-1")) {
                if (historial.getOperacion().equals("E")) {
                    detachedCriteria.add(Restrictions.like("operacion","ENTREGA",MatchMode.ANYWHERE));
                }
                else {
                    detachedCriteria.add(Restrictions.like("operacion","RECEPCION",MatchMode.ANYWHERE));
                }
            }
            if (!historial.getTipoOperacion().equals("-1")) {
                if (historial.getTipoOperacion().equals("TLP")) {
                    detachedCriteria.add(Restrictions.like("operacion","LIBRE",MatchMode.ANYWHERE));
                }
                else {
                    detachedCriteria.add(Restrictions.like("operacion","CONTRA",MatchMode.ANYWHERE));
                }
            }  
        }
        if (!historial.getEstatusAnterior().equals("-1")) {
            detachedCriteria.add(Restrictions.eq("idEstatusOperacionAnterior", new BigDecimal(historial.getEstatusAnterior())));
        }
        if (!historial.getEstatusNuevo().equals("-1")) {
            detachedCriteria.add(Restrictions.eq("idEstatusOperacionNuevo", new BigDecimal(historial.getEstatusNuevo())));
        }
        return (detachedCriteria); 
    }   

	//**
    public List<String> findCustodiosinOperacionSic(){
    	
    	final StringBuilder sb = new StringBuilder();

		sb.append(" SELECT  " );
		sb.append(" DISTINCT os.catBic.detalleCustodio ");
		sb.append(" FROM " + OperacionSic.class.getName() + " os ");
//		sb.append("	WHERE 1=1 ");
//		sb.append(" GROUP BY os.catBic.detalleCustodio ");
		sb.append("	ORDER BY os.catBic.detalleCustodio ");

		List<String> custodios = (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});

		if (custodios == null) {
			custodios = new ArrayList<String>();
		}
		
    	
    	return custodios;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#getContraoperacionRecepcionEstadoAdecuado(Long)
     */
    public List<OperacionSic> getContraoperacionRecepcionEstadoAdecuado(Long idEmision) {
        return this.getContraoperacionEstadoAdecuado(idEmision, true);
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#getContraoperacionEntregaEstadoAdecuado(Long)
     */
    public List<OperacionSic> getContraoperacionEntregaEstadoAdecuado(Long idEmision) {
        return this.getContraoperacionEstadoAdecuado(idEmision, false);
    }



    /**
     * Obtiene la(s) operacion(es) de contra operacion de Entrega y Recepcion segun el parametro seValidaContraDeEntrega.
     * @param idEmision
     * @param seValidaContraDeEntrega
     * @return
     */
    private List<OperacionSic> getContraoperacionEstadoAdecuado(final Long idEmision, final boolean seValidaContraDeEntrega) {
        List<OperacionSic> operaciones = (List<OperacionSic>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSic.class);
				detachedCriteria.add(Restrictions.eq("emision.idEmision", idEmision));
				detachedCriteria.add(Restrictions.eq("cambioBoveda", CAMBIO_BOVEDA));
		        if (seValidaContraDeEntrega) {
		        	detachedCriteria.add(Restrictions.eq("tipoMensaje", TIPO_MENSAJE_RECEPCION));
		        	detachedCriteria.add(Restrictions.eq("estatusOperacion.idEstatusOperacion", Constantes.ST_OPER_CONFIRMADA));
		        }
		        else {
		        	detachedCriteria.add(Restrictions.eq("tipoMensaje", TIPO_MENSAJE_ENTREGA));
		        	detachedCriteria.add(Restrictions.eq("estatusOperacion.idEstatusOperacion", Constantes.ST_OPER_LIBERADA));
		        }
		        detachedCriteria.add(Restrictions.ge("fechaAlta", DateUtils.preparaFechaConExtremoEnSegundos(new Date(), true)));
		        detachedCriteria.add(Restrictions.lt("fechaAlta", DateUtils.preparaFechaConExtremoEnSegundos(new Date(), false)));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
            }
        });
        return operaciones;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#obtenerOperacionDeEntrega(Long)
     */
    public List<OperacionSic> obtenerOperacionDeEntrega(Long idEmision) {
        List<OperacionSic> operaciones = null;
        final StringBuilder sb = new StringBuilder();
        String fechaHoy = DateUtils.format(new Date(), "dd/MM/yyyy");

        sb.append(" FROM " + OperacionSic.class.getName() + " opssic ");
        sb.append(" WHERE opssic.emision.idEmision = " + idEmision);
        sb.append(" AND opssic.tipoMensaje = " + TIPO_MENSAJE_ENTREGA);
        sb.append(" AND opssic.estatusOperacion.idEstatusOperacion not in (" + 
                  Constantes.ST_OPER_CANCELADA + "," + Constantes.ST_OPER_CANCEL_SIST + "," + 
                  Constantes.ST_OPER_EN_RECHAZO + "," + Constantes.ST_OPER_RECHAZADA + ") ");
        sb.append(" AND opssic.cambioBoveda = " + CAMBIO_BOVEDA);
        sb.append(" AND trunc(opssic.fechaAlta) = TO_DATE('" + fechaHoy + "', 'dd/MM/yyyy') ");
        sb.append(" ORDER BY opssic.idOperacionesSic DESC ");

        operaciones = (List<OperacionSic>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.list();
            }
        });

        return operaciones;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperacionSicCambioBovedaEnTransito(java.lang.Long, java.lang.String, java.lang.Long[])
     */
    public List<OperacionSic> findOperacionSicCambioBovedaEnTransito(final Long idEmision) {
		return (List<OperacionSic>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<OperacionSic> doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSic.class, "operacionSic");
				detachedCriteria.add(Restrictions.eq("operacionSic.emision.idEmision", idEmision));
				detachedCriteria.add(Restrictions.not(Restrictions.in("operacionSic.estatusOperacion.idEstatusOperacion", Constantes.ESTATUS_PERMITIDOS_OPER_SIC)));
				detachedCriteria.add(Restrictions.eq("operacionSic.cambioBoveda", new BigInteger("1")));
				detachedCriteria.add(Restrictions.or(
					Restrictions.eq("operacionSic.tipoMensaje", Constantes.MT_542), 
					Restrictions.eq("operacionSic.tipoMensaje", Constantes.MT_540)));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return (List<OperacionSic>) criteria.list();
			}
		});	
    }
    
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#findOperacionSicCambioBovedaEnTransito(java.lang.Long, java.lang.String)
     */
    public List<OperacionSic> findOperacionSicCambioBovedaEnTransito(final Long idEmision, final String tipoMensaje) {
		return (List<OperacionSic>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<OperacionSic> doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OperacionSic.class, "operacionSic");
				detachedCriteria.add(Restrictions.eq("operacionSic.emision.idEmision", idEmision));
				detachedCriteria.add(Restrictions.not(Restrictions.in("operacionSic.estatusOperacion.idEstatusOperacion", Constantes.ESTATUS_PERMITIDOS_OPER_SIC)));
				detachedCriteria.add(Restrictions.eq("operacionSic.cambioBoveda", new BigInteger("1")));
				detachedCriteria.add(Restrictions.eq("operacionSic.tipoMensaje", tipoMensaje));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return (List<OperacionSic>) criteria.list();
			}
		});	
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#eliminarRegistroControlLiberacionRecepciones(long, long)
     */
    public void eliminarRegistroControlLiberacionRecepciones(long idOperacionSic, long folioControl) {
        log.info("\n\n####### Entrando a eliminarRegistroControlLiberacionRecepciones()...");

        final StringBuffer stringSQL = new StringBuffer();

        stringSQL.append(" DELETE FROM T_CONTROL_LIBERACION_INT ");
        stringSQL.append(" WHERE ID_OPERACIONES_SIC = " + idOperacionSic);
        stringSQL.append(" AND FOLIO_CONTROL = " + folioControl);

        this.getJdbcTemplate().update(stringSQL.toString());
    } 

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.OperacionSicDao#getDataToCompareForUpdate(java.lang.Long)
     */
    public List<Object> getDataToCompareForUpdate(Long idOpSic) {
        List<Object> l = null;
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        OperacionSic os = (OperacionSic) s.load(OperacionSic.class, idOpSic);
        if (os != null) {
            l = new ArrayList<Object>();
            l.add(os.getEstatusOperacion().getIdEstatusOperacion());
            l.add(os.getVersion());
        }
        os = null;
        s.close();
        return l;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public Long findOperacionesParcialesPendientes(final Long idCuentaNombrada) {
		log.debug("OperacionSicDaoImpl :: findOperacionesParcialesPendientes: " + idCuentaNombrada);
		Long total = 0l; 
		
    	final StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(os.idOperacionesSic) as total" );
		sb.append(" FROM " + OperacionSic.class.getName() + " os ");
		sb.append("	WHERE os.estatusOperacion.idEstatusOperacion IN ('18','19','22') ");
		sb.append("	AND os.catBic.cuentaNombrada.idCuentaNombrada = " + idCuentaNombrada);

		List result = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
		
		if(result != null && result.size() == 1){
			total = (Long) result.get(0);
		}

		return total;
	}
}
