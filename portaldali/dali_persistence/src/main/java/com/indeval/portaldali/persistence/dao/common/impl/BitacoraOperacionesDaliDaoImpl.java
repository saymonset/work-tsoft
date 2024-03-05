/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDaliDao;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;
import com.indeval.portaldali.persistence.util.UtilFecha;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class BitacoraOperacionesDaliDaoImpl extends BaseDaoHibernateImpl implements 
		BitacoraOperacionesDaliDao {
	

    /**
     * Log de clase.
     */
    private static final Logger logger = 
        LoggerFactory.getLogger(BitacoraOperacionesDaliDaoImpl.class);
    
    /** Constante para cero */
	private static final Integer UNO = Integer.valueOf(1);

	/** Constante para uno */
	private static final Integer CERO = Integer.valueOf(0);
    
    /**
     * @see com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDaliDao#merge(com.indeval.persistence.portallegado.dinero.modelo.BitacoraOperaciones)
     */
    public BitacoraOperaciones merge(BitacoraOperaciones bitacoraOperaciones) {
        
        logger.debug("Entrando a BitacoraOperacionesDaliDaoImpl.merge()");
        return (BitacoraOperaciones) getHibernateTemplate().merge(bitacoraOperaciones);
    }

	/** 
	 * @see com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDaliDao#getBitacoras(com.indeval.portaldali.persistence.model.BitacoraOperaciones, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
	 */
	public PaginaVO getBitacoras(BitacoraOperaciones bitacoraOperaciones,
			PaginaVO paginaVO) {
		logger.debug("Entrando a BitacoraOperacionesDaliDaoImpl.getBitacoras(BitacoraOperaciones, PaginaVO)");

		if (bitacoraOperaciones == null) {
			throw new BusinessException(
					"El objeto bitacoraOperaciones es nulo.");
		}

		if (bitacoraOperaciones.tieneidFolioTrasp()) {
			bitacoraOperaciones.setIdRecep(bitacoraOperaciones.getIdTrasp());
			bitacoraOperaciones.setFolioRecep(bitacoraOperaciones
					.getFolioTrasp());
		} else if (bitacoraOperaciones.tieneidFolioRecep()) {
			bitacoraOperaciones.setIdTrasp(bitacoraOperaciones.getIdRecep());
			bitacoraOperaciones.setFolioTrasp(bitacoraOperaciones
					.getFolioRecep());
		}

		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		
		Long totalRegistros = (Long) this.getGeneraPaginaBitacora(
				bitacoraOperaciones, "COUNT", paginaVO);

		if (totalRegistros != null && totalRegistros.intValue() > 0) {
			paginaVO = (PaginaVO) this.getGeneraPaginaBitacora(
					bitacoraOperaciones, "SELECT", paginaVO);
			paginaVO.setTotalRegistros(new Integer(totalRegistros.intValue()));
			paginaVO.setValores(obtieneValores(paginaVO.getRegistros()));
		} else {
			paginaVO.setTotalRegistros(new Integer(0));
		}

		return paginaVO;
	}

	/**
	 * @param bitacoraOperaciones
	 * @param funcionSQL
	 * @param paginaVO
	 * @return Object
	 */
	private Object getGeneraPaginaBitacora(
			final BitacoraOperaciones bitacoraOperaciones,
			final String funcionSQL, final PaginaVO paginaVO) {

		return this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				StringBuffer buffer = new StringBuffer();
				Map<String, Comparable> params = new HashMap<String, Comparable>();
				Object resultado = null;

				if (funcionSQL.trim().equalsIgnoreCase("COUNT")) {
					buffer.append(" select count(*) ");
				}

				buffer.append(" from BitacoraOperaciones bo ");
				buffer.append(" where 1=1 ");

				if (bitacoraOperaciones
						.getFolioControl()!= null) {
					buffer
							.append(" and bo.folioControl =:folioControl ");
					params.put("folioControl", bitacoraOperaciones
							.getFolioControl());
				}

				if (StringUtils.isNotEmpty(bitacoraOperaciones
						.getReferenciaOperacion())) {
					buffer
							.append(" and bo.referenciaOperacion =:referenciaOperacion ");
					params.put("referenciaOperacion", bitacoraOperaciones
							.getReferenciaOperacion());
				}

				if (bitacoraOperaciones.getFechaConcertacion() != null) {
					Date[] fechasConcertacion = {
							UtilFecha.preparaFechaConExtremoEnSegundos(
									bitacoraOperaciones.getFechaConcertacion(),
									true),
							UtilFecha.preparaFechaConExtremoEnSegundos(
									bitacoraOperaciones.getFechaConcertacion(),
									false) };
					buffer
							.append(" and bo.fechaConcertacion  between :fechaConcertacion1 and :fechaConcertacion2 ");
					params.put("fechaConcertacion1", fechasConcertacion[0]);
					params.put("fechaConcertacion2", fechasConcertacion[1]);
				}

				if (bitacoraOperaciones.getFechaLiquidacion() != null) {
					Date[] fechasLiquidacion = {
							UtilFecha.preparaFechaConExtremoEnSegundos(
									bitacoraOperaciones.getFechaLiquidacion(),
									true),
							UtilFecha.preparaFechaConExtremoEnSegundos(
									bitacoraOperaciones.getFechaLiquidacion(),
									false) };
					buffer
							.append(" and bo.fechaLiquidacion between :fechaLiquidacion1 and :fechaLiquidacion2 ");
					params.put("fechaLiquidacion1", fechasLiquidacion[0]);
					params.put("fechaLiquidacion2", fechasLiquidacion[1]);
				}
				if (StringUtils.isNotEmpty(bitacoraOperaciones
						.getEstatusRegistro())) {
					buffer.append(" and bo.estatusRegistro =:estatusRegistro ");
					params.put("estatusRegistro", bitacoraOperaciones
							.getEstatusRegistro().trim());
				}

				if (StringUtils.isNotEmpty(bitacoraOperaciones.getTv())) {
					buffer.append(" and TRIM(bo.tv) = :tv ");
					params.put("tv", bitacoraOperaciones.getTv().trim());
				}
				if (StringUtils.isNotEmpty(bitacoraOperaciones.getEmisora())) {
					buffer.append(" and TRIM(bo.emisora) = :emisora ");
					params.put("emisora", bitacoraOperaciones.getEmisora()
							.trim());
				}
				if (StringUtils.isNotEmpty(bitacoraOperaciones.getSerie())) {
					buffer.append(" and TRIM(bo.serie) = :serie ");
					params.put("serie", bitacoraOperaciones.getSerie().trim());
				}
				if (StringUtils.isNotEmpty(bitacoraOperaciones.getCupon())) {
					buffer.append(" and TRIM(bo.cupon) = :cupon ");
					params.put("cupon", bitacoraOperaciones.getCupon().trim());
				}
				logger.trace("bitacoraOperaciones: " + bitacoraOperaciones);
				// XXX este metodo parece no estar funcionando... if (StringUtils.isNotEmpty(bitacoraOperaciones.getDivisa())) {
				if (bitacoraOperaciones.getDivisa() != null && !"TODAS".equals(bitacoraOperaciones.getDivisa())) {
						buffer.append(" and TRIM(bo.divisa) = :divisa ");
						params.put("divisa", bitacoraOperaciones.getDivisa().trim());
				}
				if (bitacoraOperaciones.getBoveda() != null && !"TODAS".equals(bitacoraOperaciones.getBoveda())) {
						buffer.append(" and TRIM(bo.boveda) = :boveda ");
						params.put("boveda", bitacoraOperaciones.getBoveda().trim());
				}
				if (bitacoraOperaciones.getBovedaEfectivo() != null && !"TODAS".equals(bitacoraOperaciones.getBovedaEfectivo())) {
						buffer.append(" and TRIM(bo.bovedaEfectivo) = :bovedaEfectivo ");
						params.put("bovedaEfectivo", bitacoraOperaciones.getBovedaEfectivo().trim());
				}

				//vienen ambos idfolio y cuenta
				if(bitacoraOperaciones.tieneidFolioRecep() && StringUtils.isNotEmpty(bitacoraOperaciones.getCuentaTrasp())){
					buffer.append(" and ( (bo.idTrasp =:idTrasp and bo.folioTrasp =:folioTrasp and bo.cuentaTrasp =:cuentaTrasp ) ");
					params.put("idTrasp", bitacoraOperaciones.getIdTrasp());					
					params.put("folioTrasp", bitacoraOperaciones.getFolioTrasp());			
					params.put("cuentaTrasp", bitacoraOperaciones.getCuentaTrasp().trim());
					
					buffer.append(" or  (bo.idRecep =:idRecep  and bo.folioRecep =:folioRecep  and  bo.cuentaRecep =:cuentaRecep ))");
					params.put("idRecep", bitacoraOperaciones.getIdRecep());
					params.put("folioRecep", bitacoraOperaciones.getFolioRecep());
					params.put("cuentaRecep", bitacoraOperaciones.getCuentaTrasp().trim());
				
					
				}else if (bitacoraOperaciones.tieneidFolioRecep()) {
					//solo se tiene idfolio
					buffer.append(" and (( bo.idTrasp =:idTrasp ");
					params.put("idTrasp", bitacoraOperaciones.getIdTrasp());
					buffer.append(" and bo.folioTrasp =:folioTrasp ");
					params.put("folioTrasp", bitacoraOperaciones.getFolioTrasp());
					buffer.append("  ) or ");										
									buffer.append(" ( bo.idRecep =:idRecep ");
					params.put("idRecep", bitacoraOperaciones.getIdRecep());
					buffer.append(" and bo.folioRecep =:folioRecep  ");
					params.put("folioRecep", bitacoraOperaciones.getFolioRecep());			
					buffer.append(" ))");				
			
				}else if(StringUtils.isNotEmpty(bitacoraOperaciones.getCuentaTrasp())){
					//solo se tiene la cuenta
					buffer.append(" and ( bo.cuentaRecep =:cuentaRecep ");
					buffer.append(" or bo.cuentaTrasp =:cuentaTrasp )");
					params.put("cuentaRecep", bitacoraOperaciones
							.getCuentaTrasp().trim());
					params.put("cuentaTrasp", bitacoraOperaciones
							.getCuentaTrasp().trim());
				}
				

				if (funcionSQL.trim().equalsIgnoreCase("SELECT")) {
					buffer.append(" order by bo.referenciaOperacion desc");
				}

				Query q = session.createQuery(buffer.toString());
				q.setProperties(params);
				
				
				if(logger.isTraceEnabled()) {
					logger.trace("qry: " + q.getQueryString());
					logger.trace("params: " + params);
				}
				
				if (funcionSQL.trim().equalsIgnoreCase("COUNT")) {
					resultado = q.uniqueResult();
				}

				if (funcionSQL.trim().equalsIgnoreCase("SELECT")) {
					if (paginaVO != null) {
						if (paginaVO.getOffset() != null
								&& paginaVO.getOffset().intValue() >= 0) {
							q.setFirstResult(paginaVO.getOffset().intValue());
						}
						if (paginaVO.getRegistrosXPag() != null
								&& paginaVO.getRegistrosXPag().intValue() > 0) {
							q.setMaxResults(paginaVO.getRegistrosXPag()
									.intValue());
						}
						paginaVO.setRegistros(q.list());
					}
					resultado = paginaVO;
				}

				return resultado;
			}

		});
	}
	
	/**
	 * @param registros
	 * @returnSELECT * FROM T_BITACORA_OPERACIONES WHERE FOLIO_CONTROL = '139063' 
	 */
	private Map<String, Number> obtieneValores(
			List<BitacoraOperaciones> registros) {
		
		if (registros == null) {
			throw new BusinessException("No existe la lista de registros");
		}

		int ne = 0;
		int en = 0;
		int ack = 0;
		int nak = 0;
		int fv = 0;
		int liq = 0;
		BigDecimal totalImporte = new BigDecimal("0");
		BigInteger totalTitulos = new BigInteger("0");

		for (Iterator<BitacoraOperaciones> iter = registros.iterator(); iter.hasNext();) {
			BitacoraOperaciones bitacoraOperaciones = iter.next();
			if (bitacoraOperaciones.getMonto() != null) {
				totalImporte = totalImporte.add(bitacoraOperaciones.getMonto());
			}
			if( bitacoraOperaciones.getCantidadTitulos() != null ) {
				totalTitulos = totalTitulos.add(bitacoraOperaciones.getCantidadTitulos());
			}
			if (StringUtils
					.isNotEmpty(bitacoraOperaciones.getEstatusRegistro())) {
				if (NO_ENVIADA.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())) {
					ne++;
				}
				if (ENVIADA.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())) {
					en++;
				}
				if (ACKNOWLEGDE.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())) {
					ack++;
				}
				if (NOT_ACKNOWLEGDE.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())) {
					nak++;
				}
				if (FALTANTE_VALORES.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())) {
					fv++;
				}
				if (LIQUIDADA_PORTAL.equalsIgnoreCase(bitacoraOperaciones
						.getEstatusRegistro().trim())
						|| LIQUIDADA_SAVAR.equalsIgnoreCase(bitacoraOperaciones
								.getEstatusRegistro().trim())) {
					liq++;
				}
			}
		}

		Map<String, Number> valores = new HashMap<String, Number>();
		valores.put(NO_ENVIADA, Integer.valueOf(ne));
		valores.put(ENVIADA, Integer.valueOf(en));
		valores.put(ACKNOWLEGDE, Integer.valueOf(ack));
		valores.put(NOT_ACKNOWLEGDE, Integer.valueOf(nak));
		valores.put(FALTANTE_VALORES, Integer.valueOf(fv));
		valores.put(LIQUIDADA_PORTAL, Integer.valueOf(liq));
		valores.put(TOTAL_IMPORTE, totalImporte);
		valores.put(TOTAL_TITULOS, totalTitulos);
		
		return valores;
	}

}
