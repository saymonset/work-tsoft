/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaInstruccionesEfectivoDAOImpl.java
 * Mar 7, 2008
 */
package com.indeval.portaldali.persistence.dao.estatus.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.lob.ClobImpl;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.util.QueryUtil;
import com.indeval.portaldali.persistence.dao.estatus.ConsultaInstruccionesEfectivoDAO;
import com.indeval.portaldali.persistence.model.InstruccionEfectivo;
import com.indeval.portaldali.persistence.model.InstruccionEfectivoSimple;
import com.indeval.portaldali.persistence.model.InternacionalEfectivo;
import com.indeval.portaldali.persistence.model.MensajeBean;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.persistence.model.RetiroEfectivoInternacional;
import com.indeval.portaldali.persistence.util.TipoPagoConstants;

/**
 * Implementación del DAO para realizar las consultas de instrucciones de
 * efectivo.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 * 
 */
public class ConsultaInstruccionesEfectivoDAOImpl extends HibernateDaoSupport
		implements ConsultaInstruccionesEfectivoDAO {
	
	private DateUtilsDao dateUtilsDao;
	
	private Logger logger = LoggerFactory
			.getLogger(ConsultaInstruccionesEfectivoDAOImpl.class);
	// private EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = null;
	/**
	 * Mapeo de los tipos de operación con el tipo de instrucción.
	 */
	private HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion;

	/*
	 * public EstatusOperacionesMatchDAO getEstatusOperacionesMatchDAO() {
	 * return estatusOperacionesMatchDAO; }
	 */
	/*
	 * public void setEstatusOperacionesMatchDAO( EstatusOperacionesMatchDAO
	 * estatusOperacionesMatchDAO) { this.estatusOperacionesMatchDAO =
	 * estatusOperacionesMatchDAO; }
	 */

	@SuppressWarnings("unchecked")
	public List<InstruccionEfectivoDTO> consultarInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta,
			final EstadoPaginacionDTO estadoPaginacion) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		final StringBuffer query = setBaseQueryConsultarInstruccionesEfectivo();

		crearCriteriosConsultaInstruccionesEfectivo(criterioConsulta, query,params, tipos);

		query.append(" ORDER BY instruccion.fechaLiquidacion desc,instruccion.id ");

		return (List<InstruccionEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<InstruccionEfectivoDTO> instrucciones = new ArrayList<InstruccionEfectivoDTO>();

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

						if (estadoPaginacion != null) {
							hQuery.setMaxResults(estadoPaginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(estadoPaginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion
									.getRegistrosPorPagina()));
						}

						List<InstruccionEfectivo> instruccionesEfectivo = hQuery.list();

						for (InstruccionEfectivo instruccionEfectivo : instruccionesEfectivo) {
							instrucciones.add(DTOAssembler.crearInstruccionEfectivoDTO(instruccionEfectivo, dateUtilsDao.getDateFechaDB()));
						}

						return instrucciones;
					}
				});
	}

	/**
	 * Crea los criterios necesarios para ejecutar la consulta de instrucciones
	 * de efectivo.
	 * 
	 * @param criterioConsulta
	 *            el DTO con los criterios proporcionados por el usuario.
	 * @param query
	 *            el buffer que contendr la representación en cadena de la
	 *            consulta.
	 * @param params
	 *            una lista en la cual colocar los parámetros para realizar la
	 *            consulta.
	 * @param tipos
	 *            un arreglo con los tipos de datos de cada parámetro.
	 */
	private void crearCriteriosConsultaInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta, StringBuffer query,
			ArrayList<Object> params, ArrayList<Type> tipos) {
		// Asegurar que siempre haya algo al principio de las condiciones

		if (criterioConsulta == null){
			query.append(" 1 = 1 ");
			return;
			
		}
		
		// FECHA LIQUIDACION
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append(" trunc(instruccion.fechaLiquidacion) = ? ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		} else {
			query.append(" 1 = 1 ");
		}

		int numeroParametros = 0;
		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que
									// venga
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append("( ");
			}
			if (isRolTraspasante(criterioConsulta)
					|| isRolAmbos(criterioConsulta)) {
				query
						.append(" instruccion.institucionTraspasante.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(unSoloAgente
						.getInstitucion().getId())));
				numeroParametros += 1;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append("OR ");
			}
			if (isRolReceptor(criterioConsulta) || isRolAmbos(criterioConsulta)) {
				query
						.append("  ( instruccion.institucionReceptora.idInstitucion = ?  AND instruccion.idTipoMensaje <> 5 ) ");
				params.add(new BigInteger(String.valueOf(unSoloAgente
						.getInstitucion().getId())));
				numeroParametros += 1;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		} else if (tieneAmbosAgentes(criterioConsulta)) { // Vienen ambos
															// agentes
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append("( ");
			}
			if (isRolAmbos(criterioConsulta)
					|| isRolTraspasante(criterioConsulta)) {
				query
						.append("  instruccion.institucionTraspasante.idInstitucion = ? ");
				query
						.append("AND instruccion.institucionReceptora.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getInstitucionParticipante().getId())));
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getInstitucionContraparte().getId())));
				numeroParametros += 2;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append("OR ");
			}

			if (isRolAmbos(criterioConsulta) || isRolReceptor(criterioConsulta)) {
				query
						.append("  instruccion.institucionTraspasante.idInstitucion = ? ");
				query
						.append(" AND (instruccion.institucionReceptora.idInstitucion = ?  AND instruccion.idTipoMensaje <> 5 )");
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getCuentaContraparte().getInstitucion().getId())));
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getCuentaParticipante().getInstitucion().getId())));
				numeroParametros += 2;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		}

		for (int i = 0; i < numeroParametros; i++) {
			tipos.add(new BigIntegerType());
		}

		// log.debug("Consulta: ***[" + query.toString() + "]***");

		// REFERENCIA
		// se agrego la validacion de que el dato sea numerico
		if (StringUtils.isNotEmpty(criterioConsulta.getReferenciaOperacion())
				&& criterioConsulta.getReferenciaOperacion() != null 
				&& StringUtils.isNumeric(criterioConsulta.getReferenciaOperacion())) {
			query.append("AND instruccion.referenciaOperacion = ? ");
			params.add(criterioConsulta.getReferenciaOperacion());
			tipos.add(new StringType());
		}

		// TIPO DE INSTRUCCION
		if (criterioConsulta.getTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.tipoInstruccion.idTipoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getTipoInstruccion().getIdTipoInstruccion())));
			tipos.add(new BigIntegerType());
		}

		// tipodeAbono TIPO DE ABONO
		if (criterioConsulta.getTipoLiquidacion() != null
				&& criterioConsulta.getTipoLiquidacion().getIdTipoLiq() != null
				&& criterioConsulta.getTipoLiquidacion().getIdTipoLiq()
						.longValue() > DaliConstants.VALOR_COMBO_TODOS) {
			query.append("AND instruccion.tipoLiquidacion.idTipoLiq = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getTipoLiquidacion().getIdTipoLiq())));
			tipos.add(new BigIntegerType());
		}

		BigDecimal bigDecimal = new BigDecimal("0");
		// IMPORTE
		if (criterioConsulta.getMonto() != null) {
			bigDecimal = new BigDecimal("0"
					+ criterioConsulta.getMonto().trim());
			if (bigDecimal.compareTo(bigDecimal.ZERO) == 1) {
				query.append("AND instruccion.monto = ? ");
				params.add(new BigDecimal("0" + criterioConsulta.getMonto()));
				tipos.add(new BigDecimalType());
			}

		}

		// PLAZO
		if (criterioConsulta.getPlazo() != null
				&& Integer.valueOf("0" + criterioConsulta.getPlazo()) > 0) {
			query.append("AND instruccion.plazo = ? ");
			params.add(new BigInteger(criterioConsulta.getPlazo()));
			tipos.add(new BigIntegerType());
		}
		
		// DIVISA
		if (criterioConsulta.getDivisa() != null
				&&  criterioConsulta.getDivisa().getId() > 0) {
			query.append("AND instruccion.divisa.claveAlfabetica = ? ");
			params.add(criterioConsulta.getDivisa().getClaveAlfabetica());
			tipos.add(new StringType());
		}
		
		// BOVEDA
		if (criterioConsulta.getBoveda() != null
				&&  criterioConsulta.getBoveda().getId() > 0) {
			query.append("AND instruccion.boveda.nombreCorto = ? ");
			params.add(criterioConsulta.getBoveda().getNombreCorto());
			tipos.add(new StringType());
		}

		// FECHA LIQUIDACION.
		if (criterioConsulta.getFechaVencimiento() != null) {
			query.append(" and trunc(instruccion.fechaVencimiento) = ? ");
			params.add(criterioConsulta.getFechaVencimiento());
			tipos.add(new DateType());
		}

		// LIQUIDACION AL VENCIMIENTO.
		if (criterioConsulta.getLiquidacionVencimiento() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq()
						.longValue() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.liquidacionVencimiento.idTipoLiq = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getLiquidacionVencimiento().getIdTipoLiq())));
			tipos.add(new BigIntegerType());
		}

		// MONTO VENCIMIENTO.
		if (criterioConsulta.getMontoVencimiento() != null) {
			bigDecimal = new BigDecimal("0"
					+ criterioConsulta.getMontoVencimiento().trim());
			if (bigDecimal.compareTo(bigDecimal.ZERO) == 1) {
				query.append("AND instruccion.montoVencimiento = ? ");
				params.add(new BigDecimal("0"
						+ criterioConsulta.getMontoVencimiento()));
				tipos.add(new BigDecimalType());
			}
		}

		// /////
		if (criterioConsulta.getEstadoInstruccion() != null
				&& criterioConsulta.getEstadoInstruccion()
						.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.estadoInstruccion.idEstadoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getEstadoInstruccion().getIdEstadoInstruccion())));
			tipos.add(new BigIntegerType());
		}
		
		
		if (!StringUtils.isEmpty(criterioConsulta.getTipoRetiro()) &&
				NumberUtils.isNumber(criterioConsulta.getTipoRetiro()) &&
				Integer.parseInt(criterioConsulta.getTipoRetiro())>0) {
			
			Integer retiro= Integer.parseInt(criterioConsulta.getTipoRetiro());
			
			//los BCOM se estan manejando como SPEI, el tipo retiro "3" en la pantalla solo es referencia para encontrar los RETE que son BCOM
			if(retiro == 3){
				query.append(" AND instruccion.tipoMensajeCat.claveTipoMensaje = '103' ");
			}
			//cuando seleccionen SPEI no debe mostrar los 103, solo el resto de los retiros 
			else if(retiro == 1){
				query.append(" AND instruccion.tipoMensajeCat.claveTipoMensaje != '103' ");
			}
			
			//se quita la referencia 3 que viene de la pantalla, la tabla a consultar no conoce el tipo 3 aun
			retiro = retiro==3? 1 : retiro;
			
			String tipoDeposito = "";
			
			if (retiro==1) {
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI
						+ ", ";
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI + ", ";
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.TERCERO_A_TERCERO_SPEI;				
				
			} else if (retiro==2) {				
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.TRASPASO_FONDOS_SIAC;			
			}
			
			query.append(" AND ");
			
			if(StringUtils.isNotEmpty(tipoDeposito)){
				query.append(" ( ");
			}
			
			query.append(" instruccion.tipoRetiro.idTipoRetiro = ? ");
			params.add(retiro);
			tipos.add(new IntegerType());
			
			if(StringUtils.isNotEmpty(tipoDeposito)){			
				query.append(" OR instruccion.idTipoPago IN ( " + tipoDeposito
						+ " ) ) ");
			}				
		}
		
		// referencia paquete
		if (!StringUtils.isEmpty(criterioConsulta.getReferenciaPaquete())) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" upper(trim(instruccion.referenciaPaquete)) = upper(trim(?)) "
					+ "and (instruccion.estadoInstruccion.claveEstadoInstruccion not in ('SM','EP','FV') "
					//+ "and not (instruccion.estadoInstruccion.claveEstadoInstruccion = 'PE' "
					+ "and instruccion.fechaLiquidacion < current_date())");
			params.add(criterioConsulta.getReferenciaPaquete());
			tipos.add(new StringType());
		}
	}

	
	
	/**
	 * Crea los criterios necesarios para ejecutar la consulta de instrucciones
	 * de efectivo.
	 * 
	 * @param criterioConsulta
	 *            el DTO con los criterios proporcionados por el usuario.
	 * @param query
	 *            el buffer que contendr la representación en cadena de la
	 *            consulta.
	 * @param params
	 *            una lista en la cual colocar los parámetros para realizar la
	 *            consulta.
	 * @param tipos
	 *            un arreglo con los tipos de datos de cada parámetro.
	 */
	private void crearCriteriosConsultaInstrEfecIntl(
			CriterioEstatusOpEfectivoDTO criterioConsulta, StringBuffer query,
			ArrayList<Object> params, ArrayList<Type> tipos) {
		// Asegurar que siempre haya algo al principio de las condiciones

		// FECHA LIQUIDACION
		/*
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append(" trunc(instruccion.fechaLiquidacion) = ? ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		} else {
			query.append(" 1 = 1 ");
		}
		*/
		query.append(" 1 = 1 ");
		
		int numeroParametros = 0;
		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que
									// venga
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append("( ");
			}
			if (isRolTraspasante(criterioConsulta)
					|| isRolAmbos(criterioConsulta)) {
				query
						.append(" instruccion.institucion.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(unSoloAgente
						.getInstitucion().getId())));
				numeroParametros += 1;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append("OR ");
			}
			if (isRolReceptor(criterioConsulta) || isRolAmbos(criterioConsulta)) {
				query
						.append("  instruccion.institucion.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(unSoloAgente
						.getInstitucion().getId())));
				numeroParametros += 1;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		} else if (tieneAmbosAgentes(criterioConsulta)) { // Vienen ambos
															// agentes
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append("( ");
			}
			if (isRolAmbos(criterioConsulta)
					|| isRolTraspasante(criterioConsulta)) {
				query
						.append("  instruccion.institucion.idInstitucion = ? ");
				query
						.append("AND instruccion.institucion.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getInstitucionParticipante().getId())));
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getInstitucionContraparte().getId())));
				numeroParametros += 2;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append("OR ");
			}

			if (isRolAmbos(criterioConsulta) || isRolReceptor(criterioConsulta)) {
				query
						.append("  instruccion.institucion.idInstitucion = ? ");
				query
						.append("AND instruccion.institucion.idInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getCuentaContraparte().getInstitucion().getId())));
				params.add(new BigInteger(String.valueOf(criterioConsulta
						.getCuentaParticipante().getInstitucion().getId())));
				numeroParametros += 2;
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		}

		for (int i = 0; i < numeroParametros; i++) {
			tipos.add(new BigIntegerType());
		}

		// REFERENCIA
		//se agrego la validacion de que el dato sea numerico
		if (StringUtils.isNotEmpty(criterioConsulta.getReferenciaOperacion())
				&& criterioConsulta.getReferenciaOperacion() != null
				&& StringUtils.isNumeric(criterioConsulta.getReferenciaOperacion())) {
			/*
			query.append("AND instruccion.referenciaOperacion = ? ");
			params.add(criterioConsulta.getReferenciaOperacion());
			tipos.add(new StringType());
			*/
		}

		// TIPO DE INSTRUCCION
		if (criterioConsulta.getTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			
		}

      
	
		// FECHA VALOR.
		if (criterioConsulta.getFechaValor() != null) {
			query.append(" and trunc(instruccion.fechaValor) = ? ");
			params.add(criterioConsulta.getFechaValor());
			tipos.add(new DateType());
		}
		// FECHA ENVIO.
		if (criterioConsulta.getFechaEnvio() != null) {
			query.append(" and trunc(instruccion.fechaEnvio) = ? ");
			params.add(criterioConsulta.getFechaEnvio());
			tipos.add(new DateType());
		}
		// FECHA AUTORIZACION.
		if (criterioConsulta.getFechaAutorizacion() != null) {
			query.append(" and trunc(instruccion.fechaAutorizacion) = ? ");
			params.add(criterioConsulta.getFechaAutorizacion());
			tipos.add(new DateType());
		}
		// FECHA liberacion.
		if (criterioConsulta.getFechaLiberacion() != null) {
			query.append(" and trunc(instruccion.fechaLiberacion) = ? ");
			params.add(criterioConsulta.getFechaLiberacion());
			tipos.add(new DateType());
		}
		/*
		// LIQUIDACION AL VENCIMIENTO.
		if (criterioConsulta.getLiquidacionVencimiento() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq()
						.longValue() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.liquidacionVencimiento.idTipoLiq = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getLiquidacionVencimiento().getIdTipoLiq())));
			tipos.add(new BigIntegerType());
		}
		*/

		/*
		// MONTO VENCIMIENTO.
		if (criterioConsulta.getMontoVencimiento() != null) {
			bigDecimal = new BigDecimal("0"
					+ criterioConsulta.getMontoVencimiento().trim());
			if (bigDecimal.compareTo(bigDecimal.ZERO) == 1) {
				query.append("AND instruccion.montoVencimiento = ? ");
				params.add(new BigDecimal("0"
						+ criterioConsulta.getMontoVencimiento()));
				tipos.add(new BigDecimalType());
			}
		}
		*/

		
		// boveda
		if (criterioConsulta.getBoveda() != null
				&& criterioConsulta.getBoveda().getId() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}
		
		
		// Divisa
		if (criterioConsulta.getDivisa() != null
				&& criterioConsulta.getDivisa().getId() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.divisa.idDivisa = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta.getDivisa().getId())));
			tipos.add(new BigIntegerType());
		}
		else {
			query.append("AND instruccion.divisa.idDivisa <> 1 "); //Eliminamos la Mexican Peso, solo internacionales.
			//params.add(new BigInteger(String.valueOf(criterioConsulta.getDivisa().getId())));
			//tipos.add(new BigIntegerType());
		}
		
		
		
		// /////
		if (criterioConsulta.getEstadoInstruccion() != null
				&& criterioConsulta.getEstadoInstruccion()
						.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			query
					.append("AND instruccion.estadoInstruccion.idEstadoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterioConsulta
					.getEstadoInstruccion().getIdEstadoInstruccion())));
			tipos.add(new BigIntegerType());
		}
		
		
		
		if (!StringUtils.isEmpty(criterioConsulta.getTipoRetiro())
				&& !criterioConsulta.getTipoRetiro().equalsIgnoreCase("TODOS")) {
			String tipoRetiro = criterioConsulta.getTipoRetiro().toUpperCase();
			String tipoDeposito = "";
			if (tipoRetiro.equalsIgnoreCase("SPEI")) {
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI
						+ ", ";
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI + ", ";
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.TERCERO_A_TERCERO_SPEI;
			} else if (tipoRetiro.equalsIgnoreCase("SIAC")) {
				tipoDeposito = tipoDeposito
						+ TipoPagoConstants.TRASPASO_FONDOS_SIAC;
			}

			query.append(" AND (instruccion.tipoRetiro = ? ");
			query.append(" OR instruccion.idTipoPago IN ( " + tipoDeposito
					+ " ) ) ");
			params.add(tipoRetiro);
			tipos.add(new StringType());
		}
	}

	
	
	// private CuentaDTO tieneUnAgente(CriterioEstatusOpEfectivoDTO criterio) {
	// if (criterio.getCuentaParticipante() != null
	// && criterio.getCuentaParticipante().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS
	// && criterio.getCuentaContraparte().getIdCuenta() ==
	// DaliConstants.VALOR_COMBO_TODOS ) {
	// return criterio.getCuentaParticipante();
	// }
	// else if(criterio.getCuentaParticipante() != null
	// && criterio.getCuentaParticipante().getIdCuenta() ==
	// DaliConstants.VALOR_COMBO_TODOS
	// && criterio.getCuentaContraparte()!=null
	// && criterio.getCuentaContraparte().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS ) {
	// return criterio.getCuentaContraparte();
	// }
	//
	// return null;
	// }
	//
	// private boolean tieneAmbosAgentes(CriterioEstatusOpEfectivoDTO criterio)
	// {
	// if (criterio.getCuentaParticipante() !=null
	// && criterio.getCuentaParticipante().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS
	// && criterio.getCuentaContraparte() != null
	// && criterio.getCuentaContraparte().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS ) {
	// return true;
	// }
	//
	// return false;
	// }

	private CuentaDTO tieneUnAgente(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getInstitucionParticipante() != null && criterio.getInstitucionContraparte() == null) {
			return criterio.getCuentaParticipante();
		} else if (criterio.getInstitucionContraparte() != null && criterio.getInstitucionParticipante() == null) {
			return criterio.getCuentaContraparte();
		}
		return null;
	}

	private boolean tieneAmbosAgentes(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getInstitucionContraparte() != null
				&& criterio.getInstitucionParticipante() != null) {
			return true;
		}

		return false;
	}

	// private boolean isAgenteFirmado(CriterioEstatusOpEfectivoDTO criterio) {
	// if( criterio.getCuentaParticipante() !=null &&
	// criterio.getCuentaParticipante().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS &&
	// criterio.getCuentaContraparte()!=null &&
	// criterio.getCuentaContraparte().getIdCuenta() ==
	// DaliConstants.VALOR_COMBO_TODOS ) {
	// return true;
	// }
	// return false;
	// }
	//	
	// private boolean isContraparte(CriterioEstatusOpEfectivoDTO criterio) {
	// if( criterio.getCuentaParticipante()!=null &&
	// criterio.getCuentaParticipante().getIdCuenta() ==
	// DaliConstants.VALOR_COMBO_TODOS &&
	// criterio.getCuentaContraparte()!=null &&
	// criterio.getCuentaContraparte().getIdCuenta() >
	// DaliConstants.VALOR_COMBO_TODOS ) {
	// return true;
	// }
	// return false;
	// }

	private boolean isRolTraspasante(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getRol() == 1) {
			return true;
		}
		return false;
	}

	private boolean isRolReceptor(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getRol() == 2) {
			return true;
		}
		return false;
	}

	private boolean isRolAmbos(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getRol() == 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.persistence.dao.estatus.
	 * ConsultaInstruccionesEfectivoDAO
	 * #obtenerProyeccionConsultaInstruccionesEfectivo
	 * (com.indeval.portaldali.middleware
	 * .dto.criterio.CriterioEstatusOpEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaRetiroEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT COUNT(*) FROM "
				+ RetiroEfectivo.class.getName() + " liberacion  WHERE ");
		
		setCriteriosQueryRetiroEfectivoNal(query, params,	tipos, criterioConsulta);

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number registros = (Number) hQuery.uniqueResult();
						return registros.intValue();
					}
				});
	}
	
	
	public int obtenerProyeccionConsultaMensajeBean(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT COUNT(*) FROM "
				+ MensajeBean.class.getName() + " match WHERE ");
		
		crearCriteriosQueryConsultaEnModuloMatchSinOperacion(query, params,
				tipos, criterioConsulta);

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number registros = (Number) hQuery.uniqueResult();
						return registros.intValue();
					}
				});
	}

	
	
	public int obtenerProyeccionConsultaInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT COUNT(*) FROM "
				+ InstruccionEfectivo.class.getName() + " instruccion WHERE ");
		crearCriteriosConsultaInstruccionesEfectivo(criterioConsulta, query,params, tipos);

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number registros = (Number) hQuery.uniqueResult();
						return registros.intValue();
					}
				});
	}

	
	public int getProyeccionConsultaInstEfecIntl(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT COUNT(*) FROM "
				+ RetiroEfectivoInternacional.class.getName() + " liberacion WHERE ");
		
		setCriteriosQueryRetiroEfectivoIntl( query,		params, tipos,criterioConsulta);
		

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number registros = (Number) hQuery.uniqueResult();
						return registros.intValue();
					}
				});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.persistence.dao.estatus.
	 * ConsultaInstruccionesEfectivoDAO
	 * #obtenerTotalesDeConsultaInstruccionesEfectivo
	 * (com.indeval.portaldali.middleware
	 * .dto.criterio.CriterioEstatusOpEfectivoDTO, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> obtenerTotalesDeConsultaInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT NVL(SUM(instruccion.monto),0) FROM "
				+ InstruccionEfectivo.class.getName() + " instruccion WHERE ");
		crearCriteriosConsultaInstruccionesEfectivo(criterioConsulta, query,
				params, tipos);
		return (Map<Object, Object>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number totalMonto = (Number) hQuery.uniqueResult();
						Map<Object, Object> resultados = new HashMap<Object, Object>();
						resultados.put(TOTAL_IMPORTE, totalMonto);
						return resultados;
					}
				});
	}
	
	/**
	 * Devuelve el monto total de las instrucciones de efectivo internacional consultas 
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getTotDeConsInstEfecIntlMoi(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT SUM(moi.monto) FROM "
				+ InternacionalEfectivo.class.getName() + " moi WHERE ");
		setCriteriosQueryRetiroFondosIntlEnMoi(query,	params, tipos,criterioConsulta);
		
				
		return (Map<Object, Object>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number totalMonto = (Number) hQuery.uniqueResult();
						Map<Object, Object> resultados = new HashMap<Object, Object>();						
						resultados.put(TOTAL_IMPORTE_MOI, totalMonto);
						return resultados;
					}
				});
	}

	
	
	/**
	 * Devuelve el monto total de las instrucciones de efectivo internacional consultas 
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getTotDeConsInstEfecIntl(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("SELECT SUM(liberacion.importeTraspaso) FROM "
				+ RetiroEfectivoInternacional.class.getName() + " liberacion WHERE ");
		setCriteriosQueryRetiroEfectivoIntl(query,	params, tipos,criterioConsulta);
		
				
		return (Map<Object, Object>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						Number totalMonto = (Number) hQuery.uniqueResult();
						Map<Object, Object> resultados = new HashMap<Object, Object>();						
						resultados.put(TOTAL_IMPORTE_INTL, totalMonto);
						return resultados;
					}
				});
	}
	/*
	 * @author: fernando Vazquez Ulloa (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.persistence.dao.estatus.
	 * ConsultaInstruccionesEfectivoDAO
	 * #obtenerTotalesDeConsultaInstruccionesEfectivo
	 * (com.indeval.portaldali.middleware
	 * .dto.criterio.CriterioEstatusOpEfectivoDTO, java.util.Map)
	 */
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> obtenerTotalesDeConsultaRetiroEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		
		query.append("SELECT NVL(SUM(liberacion.importeTraspaso),0) FROM "
				+ RetiroEfectivo.class.getName() + " liberacion WHERE ");
		setCriteriosQueryRetiroEfectivoNal(query, params,	tipos, criterioConsulta);
		return (Map<Object, Object>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						String totalMonto = hQuery.uniqueResult().toString();
						Double valor = new Double(0);
						if (totalMonto != null) {
							valor = new Double(totalMonto);
						} else {
							valor = new Double(0);
						}
						Map<Object, Object> resultados = new HashMap<Object, Object>();
						resultados.put(TOTAL_IMPORTE_NAL, valor);
						return resultados;
					}
				});
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> obtenerTotalesDeConsultaInstruccionesEfectivoMatch(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append("SELECT NVL(SUM(match.importe),0) FROM "
				+ MensajeBean.class.getName() + " match WHERE ");
		
		crearCriteriosQueryConsultaEnModuloMatchSinOperacion(query, params,
				tipos, criterioConsulta);
		return (Map<Object, Object>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						String totalMonto = (String) hQuery.uniqueResult();
						Double valor = new Double(0);
						if (totalMonto != null) {
							valor = new Double(totalMonto);
						} else {
							valor = new Double(0);
						}
						Map<Object, Object> resultados = new HashMap<Object, Object>();
						resultados.put(TOTAL_IMPORTE_MATCH, valor);
						return resultados;
					}
				});
	}

	/**
	 * Método que permite leer los registros con match del módulo del match.
	 * 
	 * @author Fernando vazquez Ulloa 23-10-2009 Se agrega el folio leido
	 */
	@SuppressWarnings("unchecked")
	public List<InstruccionEfectivoDTO> consultarInstruccionesEnModuloMatchSinMatch(
			final CriterioEstatusOpEfectivoDTO criterio,
			final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + MensajeBean.class.getName() + " match ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		crearCriteriosQueryConsultaEnModuloMatchSinOperacion(query, params,
				tipos, criterio);
		params.remove(0);

		
		//getHibernateTemplate().saveOrUpdate();
		
		return (List<InstruccionEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<InstruccionEfectivoDTO> resultados = new ArrayList<InstruccionEfectivoDTO>();

						for (Object bo : resultadosBO) {
							InstruccionEfectivoDTO operacionValor = DTOAssembler
									.crearOperacionEfectivoByMensajeBean((MensajeBean) bo, dateUtilsDao.getDateFechaDB());
							resultados.add(operacionValor);
						}// FOR

						return resultados;
					}
				});
	}


	/**
	 * Método que permite leer los registros de retiro de efectivo internacional
	 * 
	 * @author Fernando vazquez Ulloa 2009-11-09
	 */
	@SuppressWarnings("unchecked")
	public List<InstruccionEfectivoDTO> consultarInstRetiroEfecNal(final CriterioEstatusOpEfectivoDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + RetiroEfectivo.class.getName() + " liberacion ");
		query.append("	    join fetch liberacion.estado ");
		query.append("	    join fetch liberacion.boveda ");
		query.append("	    join fetch liberacion.divisa ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		setCriteriosQueryRetiroEfectivoNal(query, params, tipos, criterio);
		params.remove(0);

		
		//getHibernate2Template().saveOrUpdate();
		
		return (List<InstruccionEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<InstruccionEfectivoDTO> resultados = new ArrayList<InstruccionEfectivoDTO>();

						for (Object bo : resultadosBO) {
							InstruccionEfectivoDTO operacionValor = DTOAssembler.setOperacionEfectivoByOperacionEfectivoNal((RetiroEfectivo) bo);
							Map <String, Object> map = criterio.getMapBCOM();
							operacionValor.setTipoMensaje((TipoMensajeDTO)map.get("tipoMensaje"));
							operacionValor.setTipoInstruccion((TipoInstruccionDTO)map.get("tipoInstruccion"));
							resultados.add(operacionValor);
						}// FOR

						return resultados;
					}
				});
	}
	
	
	/**
	 * Método que permite leer los registros de retiro de efectivo internacional
	 * 
	 * @author Fernando vazquez Ulloa 2009-11-09
	 */
	@SuppressWarnings("unchecked")
	public List<InstruccionEfectivoDTO> consultarInstRetiroEfecIntl(CriterioEstatusOpEfectivoDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + RetiroEfectivoInternacional.class.getName() + " liberacion ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		setCriteriosQueryRetiroEfectivoIntl(query, params, tipos, criterio);
		params.remove(0);

		
		//getHibernate2Template().saveOrUpdate();
		
		return (List<InstruccionEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<InstruccionEfectivoDTO> resultados = new ArrayList<InstruccionEfectivoDTO>();

						for (Object bo : resultadosBO) {
							InstruccionEfectivoDTO operacionValor = DTOAssembler.setOperacionEfectivoByOperacionEfectivoIntl((RetiroEfectivoInternacional) bo);
							resultados.add(operacionValor);
						}// FOR

						return resultados;
					}
				});
	}

	
	/**
	 * Método que permite leer los registros de retiro de efectivo internacional
	 * 
	 * @author Fernando vazquez Ulloa 2009-11-10
	 */
	@SuppressWarnings("unchecked")
	public List<RetiroEfectivoInternacionalDTO> consultarInstRetiroEfecIntlRetREI(CriterioEstatusOpEfectivoDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + RetiroEfectivoInternacional.class.getName() + " liberacion ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		setCriteriosQueryRetiroEfectivoIntl(query, params, tipos, criterio);
		params.remove(0);
		
		return (List<RetiroEfectivoInternacionalDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<RetiroEfectivoInternacionalDTO> resultados = new ArrayList<RetiroEfectivoInternacionalDTO>();

						for (Object bo : resultadosBO) {
							RetiroEfectivoInternacionalDTO operacionValor = DTOAssembler.creaRetiroEfectivoInternacionalDTOByRetiroEfectivoInternacional((RetiroEfectivoInternacional) bo);
							resultados.add(operacionValor);
						}

						return resultados;
					}
				});
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RetiroEfectivoDTO> consultarRetiroEfectivo(CriterioEstatusOpEfectivoDTO criterio, final EstadoPaginacionDTO paginacion) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + RetiroEfectivo.class.getName() + " liberacion ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		setCriteriosQueryRetiroEfectivoNal(query, params, tipos, criterio);
		params.remove(0);
		
		return (List<RetiroEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<RetiroEfectivoDTO> resultados = new ArrayList<RetiroEfectivoDTO>();

						for (Object bo : resultadosBO) {
							RetiroEfectivoDTO operacionValor = DTOAssembler.setRetiroEfectivoDTOByRetiroEfectivo((RetiroEfectivo) bo);
							resultados.add(operacionValor);
						}

						return resultados;
					}
				});
	}
	
	
	/**
	 * Método que permite aprobar los retiros de efectivo internaiconal por ID
	 * 
	 * @author Fernando vazquez Ulloa 2009-11-09 
	 */
	public void aprobarRetiroEfectivoInternacionalById(String id) {
		 Object[] params = new Object[] {new Date()};
		  getHibernateTemplate().bulkUpdate(
                  "update " + RetiroEfectivoInternacional.class.getName() + " set estado.idEstadoInstruccion = 23, fechaAutorizacion = ? "
                          + " where idRetiroEfectivoInt = " + id ,params );
		

	}

	public void aprobarRetiroEfectivoById(String id, Map<String, Object> datosFirma) {
		 Object[] params = new Object[] {(Date)datosFirma.get("fecha"), new ClobImpl((String)datosFirma.get("firma")) };
		  getHibernateTemplate().bulkUpdate(
                "update " + RetiroEfectivo.class.getName() 
                        + " set estado.idEstadoInstruccion = 23, "
                        + " fechaAutorizacion = ?, "
                 		 + " autorizacionFirmada = ?, " 
                 		 + " usuarioAutorizacion = '" + (String)datosFirma.get("usuario")+"', "
                 		 + " serieAutorizacion = '" + (String)datosFirma.get("serie")+"' "
                        + " where idRetiroEfectivoNal = " + id ,params );

	}
	
	/**
	 * Método que permite liberar los retiros de efectivo internaiconal por ID
	 * 
	 * @author Fernando vazquez Ulloa 2009-11-10
	 */
	public void liberarRetiroEfecIntlById(String id) {
		 Object[] params = new Object[] {new Date()};
		  getHibernateTemplate().bulkUpdate(
                 "update " + RetiroEfectivoInternacional.class.getName() + " set estado.idEstadoInstruccion = 24, fechaLiberacion = ? "
                         + " where idRetiroEfectivoInt = " + id ,params );
		
	}

	
	public void liberarRetiroEfectivoById(String id,Map<String, Object> datosFirma) {
		 Object[] params = new Object[] {(Date)datosFirma.get("fecha"), (Date)datosFirma.get("fecha"), new ClobImpl((String)datosFirma.get("firma")) };
		  getHibernateTemplate().bulkUpdate(
                 "update " + RetiroEfectivo.class.getName() 
                         + " set estado.idEstadoInstruccion = 24, "
                         + " fechaLiberacion = ?, "
                         + " fechaEnvio = ?, "
                  		 + " liberacionFirmada = ?, " 
                  		 + " usuarioLiberacion = '" + (String)datosFirma.get("usuario")+"', "
                  		 + " serieLiberacion = '" + (String)datosFirma.get("serie")+"' "
                         + " where idRetiroEfectivoNal = " + id ,params );
		
	}
	
	
	/**
	 * Método que permite leer los registros de retiros de fondos internacional
	 * sin comprobacion
	 * 
	 * @author Fernando vazquez Ulloa 06-11-2009 Se agrega el folio leido
	 */
	@SuppressWarnings("unchecked")
	public List<RetiroEfectivoInternacionalDTO> getListRetiroFondosIntlSinComprobacion(
			final CriterioEstatusOpEfectivoDTO criterio,
			final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + RetiroEfectivoInternacional.class.getName() + " liberacion ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		params.add(new Date());
		
		setCriteriosQueryRetiroEfectivoIntl(query, params, tipos, criterio);
		params.remove(0);

		
		
		
		return (List<RetiroEfectivoInternacionalDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						
						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

						List<Object> resultadosBO = hQuery.list();
						List<RetiroEfectivoInternacionalDTO> resultados = new ArrayList<RetiroEfectivoInternacionalDTO>();
						
						for (Object bo : resultadosBO) {

							RetiroEfectivoInternacionalDTO operacionValor = DTOAssembler.creaRetiroEfectivoInternacionalDTOByRetiroEfectivoInternacional((RetiroEfectivoInternacional) bo);
							resultados.add(operacionValor);
							
						}// FOR

						return resultados;
					}
				});
	}

	/**
	 * Método que permite leer los registros de retiros de fondos internacional
	 * sin comprobacion
	 * 
	 * @author Fernando vazquez Ulloa 06-11-2009 Se agrega el folio leido
	 */
	@SuppressWarnings("unchecked")
	public List<RetiroEfectivoInternacionalDTO> getListRetiroFondosIntlEnMoi(
			final CriterioEstatusOpEfectivoDTO criterio,
			final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append(" FROM " + InternacionalEfectivo.class.getName() + " moi ");
		query.append(" WHERE  "); // para activar el where y permitir todos, los
									// filtros vienen adelante
		
		params.add(new Date());
		setCriteriosQueryRetiroFondosIntlEnMoi(query, params, tipos, criterio);
		params.remove(0);
        
		
		
		return (List<RetiroEfectivoInternacionalDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query hQuery = session.createQuery(query.toString());
						hQuery.setParameters(params.toArray(), tipos
								.toArray(new Type[] {}));

						if (paginacion != null) {
							hQuery.setMaxResults(paginacion
									.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion
									.getRegistrosPorPagina());
							hQuery.setFirstResult(((paginacion
									.getNumeroPagina() - 1) * paginacion
									.getRegistrosPorPagina()));
						}

					
						
						List<Object> resultadosBO = hQuery.list();
						
						
						List<RetiroEfectivoInternacionalDTO> resultados = new ArrayList<RetiroEfectivoInternacionalDTO>();

						for (Object bo : resultadosBO) {
						
							RetiroEfectivoInternacionalDTO operacionValor = DTOAssembler
									.setOperacionEfecIntlByOperacionEfecIntlEnMoi((InternacionalEfectivo) bo);
							resultados.add(operacionValor);
						}// FOR

						return resultados;
					}
				});
	}

	@SuppressWarnings("unchecked")
	public List<InstruccionEfectivoDTO> consultarReteAprobadas() {
		final StringBuffer query = this.setBaseQueryConsultarInstruccionesEfectivo();

		// RETE LI-Aprobado
		query.append(" instruccion.tipoInstruccion.idTipoInstruccion = 33");
		query.append(" AND instruccion.estadoInstruccion.idEstadoInstruccion = 6");
		query.append(" AND instruccion.liqSpei != 1");
		query.append(" AND instruccion.folioInstruccion IN");
		query.append(" (SELECT sub.folioInstruccion FROM " + InstruccionEfectivo.class.getName() + " sub");
		query.append(" GROUP BY sub.folioInstruccion HAVING count(*) = 1)");
		query.append(" ORDER BY instruccion.fechaLiquidacion desc, instruccion.id");

		return (List<InstruccionEfectivoDTO>) getHibernateTemplate().execute(
			new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<InstruccionEfectivoDTO> instrucciones = new ArrayList<InstruccionEfectivoDTO>();
					Query hQuery = session.createQuery(query.toString());
					List<InstruccionEfectivo> instruccionesEfectivo = hQuery.list();

					for (InstruccionEfectivo instruccionEfectivo : instruccionesEfectivo) {
						instrucciones.add(DTOAssembler.crearInstruccionEfectivoDTO(instruccionEfectivo, dateUtilsDao.getDateFechaDB()));
					}

					return instrucciones;
				}
			});
	}
	
	/**
	 * Construye el query con los criterios necesarios para consultar las
	 * instrucciones de match según los parámetros de criterios enviados.
	 * 
	 * @author fernando vazquez ulloa 20-10-2009
	 * @param query
	 *            Query donde se pone el resultado
	 * @param params
	 *            Lista de parámetros
	 * @param tipos
	 *            Lista de tipos
	 * @param criterio
	 *            Criterios a utilizar
	 */
	private void crearCriteriosQueryConsultaEnModuloMatchSinOperacion(
			StringBuffer query, ArrayList<Object> params,
			ArrayList<Type> tipos, CriterioEstatusOpEfectivoDTO criterioConsulta) {

		if (criterioConsulta == null){
			query.append(" 1 = 1 ");
			return;
		}
		
		// Asegurar que siempre haya algo al principio de las condiciones
		// FECHA LIQUIDACION
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append(" trunc(match.fechaLiquidacion) = ? ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		} else {
			query.append(" 1 = 1 ");
		}

		// ID
		if (StringUtils.isNotEmpty(criterioConsulta.getId())
				&& criterioConsulta.getId() != null) {
			query.append("AND match.idInstruccion = ? ");
			params.add(new BigInteger(criterioConsulta.getId()));
			tipos.add(new BigIntegerType());
		}

		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que
									// venga
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolTraspasante(criterioConsulta)
					|| isRolAmbos(criterioConsulta)) {
				query.append(" match.idFolioTraspasante  = ? ");
				params.add(String.valueOf(unSoloAgente.getInstitucion()
						.getClaveTipoInstitucion()
						+ unSoloAgente.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}
			if (isRolReceptor(criterioConsulta) || isRolAmbos(criterioConsulta)) {
				query.append("  match.idFolioReceptor = ? ");
				params.add(String.valueOf(unSoloAgente.getInstitucion()
						.getClaveTipoInstitucion()
						+ unSoloAgente.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ) ");
			}
		} else if (tieneAmbosAgentes(criterioConsulta)) { // Vienen ambos
															// agentes
			query.append(" AND ");
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolAmbos(criterioConsulta)
					|| isRolTraspasante(criterioConsulta)) {
				query.append("  match.idFolioTraspasante = ? ");
				query.append(" AND match.idFolioReceptor = ? ");

				params.add(String.valueOf(criterioConsulta
						.getInstitucionParticipante().getClaveTipoInstitucion()
						+ criterioConsulta.getInstitucionParticipante()
								.getFolioInstitucion()));

				params.add(String.valueOf(criterioConsulta
						.getInstitucionContraparte().getClaveTipoInstitucion()
						+ criterioConsulta.getInstitucionContraparte()
								.getFolioInstitucion()));
				tipos.add(new StringType());
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}

			if (isRolAmbos(criterioConsulta) || isRolReceptor(criterioConsulta)) {
				query.append("  match.idFolioTraspasante = ? ");
				query.append(" AND match.idFolioReceptor = ? ");
				params.add(String.valueOf(criterioConsulta
						.getCuentaParticipante().getInstitucion()
						.getClaveTipoInstitucion()
						+ criterioConsulta.getCuentaParticipante()
								.getInstitucion().getFolioInstitucion()));

				params.add(String.valueOf(criterioConsulta
						.getCuentaContraparte().getInstitucion()
						.getClaveTipoInstitucion()
						+ criterioConsulta.getCuentaContraparte()
								.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		}

		// REFERENCIA OPERACION
		// se agrego la validacion de que el dato sea numerico
		if (StringUtils.isNotEmpty(criterioConsulta.getReferenciaOperacion())
				&& criterioConsulta.getReferenciaOperacion() != null
				&& StringUtils.isNumeric(criterioConsulta.getReferenciaOperacion())) {
			query.append("AND match.folioInstruccion = ? ");
			params.add(criterioConsulta.getReferenciaOperacion());
			tipos.add(new StringType());
		}

		// TIPO DE INSTRUCCION
		if (criterioConsulta.getTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			if (criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() == 52) { // CALL
				query.append("AND match.tipoOperacionInstruccion = ? ");

				params.add("TEFV");
				tipos.add(new StringType());
			} else {
				query.append("AND match.tipoOperacionInstruccion = ? ");

				params.add("N/A"); // Solo funcionara para los de tipo CALL en
									// la tabla del Match
				tipos.add(new StringType());
			}

		}

		// tipodeAbono TIPO DE ABONO
		if (criterioConsulta.getTipoLiquidacion() != null
				&& criterioConsulta.getTipoLiquidacion().getIdTipoLiq() != null
				&& criterioConsulta.getTipoLiquidacion().getIdTipoLiq()
						.longValue() > DaliConstants.VALOR_COMBO_TODOS) {
			query.append("AND match.tipoLiquidacionInicio = ? ");

			params.add(criterioConsulta.getTipoLiquidacion()
					.getTipoLiquidacion().trim());// D,S,R
			tipos.add(new StringType());
		}

		BigDecimal bigDecimal = new BigDecimal("0");
		// IMPORTE
		if (criterioConsulta.getMonto() != null) {
			bigDecimal = new BigDecimal("0"
					+ criterioConsulta.getMonto().trim());
			if (bigDecimal.compareTo(bigDecimal.ZERO) == 1) {
				query.append("AND match.importe = ? ");
				params.add(criterioConsulta.getMonto().toString());
				tipos.add(new StringType());
			}
		}
		// PLAZO
		if (criterioConsulta.getPlazo() != null
				&& Integer.valueOf("0" + criterioConsulta.getPlazo()) > 0) {
			query.append("AND match.plazo = ? ");
			params.add(new BigInteger(criterioConsulta.getPlazo()));
			tipos.add(new BigIntegerType());
		}

		// FECHA LIQUIDACION.
		if (criterioConsulta.getFechaVencimiento() != null) {
			query.append(" and trunc(match.fechaReporto) = ? ");
			params.add(criterioConsulta.getFechaVencimiento());
			tipos.add(new DateType());
		}

		// LIQUIDACION AL VENCIMIENTO.
		if (criterioConsulta.getLiquidacionVencimiento() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq() != null
				&& criterioConsulta.getLiquidacionVencimiento().getIdTipoLiq()
						.longValue() > DaliConstants.VALOR_COMBO_TODOS) {
			query.append("AND match.tipoLiquidacionVencimiento = ? ");

			params.add(String.valueOf(criterioConsulta
					.getLiquidacionVencimiento().getTipoLiquidacion()));// D,S,R
			tipos.add(new StringType());
		}

		// MONTO VENCIMIENTO.
		if (criterioConsulta.getMontoVencimiento() != null
				&& criterioConsulta.getMontoVencimiento().trim().length() > 0) {
			query.append("AND match.importeIntereses = ? ");
			params.add(criterioConsulta.getMontoVencimiento().toString());
			tipos.add(new StringType());
		}
		// ///// EnModuloMatchSinOperacion
		query.append("AND match.estadoMensajeString not in (?,?,?) ");// eliminamos los de
															// con match para
															// cualquier filtro.
		params.add("CON_MATCH");
		tipos.add(new StringType());
		params.add("CANCELAR_NO_APLICADO");
		tipos.add(new StringType());
		params.add("CANCELAR_APLICADO");
		tipos.add(new StringType()); 

		if (criterioConsulta.getEstadoInstruccion() != null
				&& criterioConsulta.getEstadoInstruccion()
						.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			if (criterioConsulta.getEstadoInstruccion()
					.getIdEstadoInstruccion() == 0) {// CON_MATCH
				query.append("AND match.estadoMensajeString = ? ");
				params.add("CON_MATCH");
				tipos.add(new StringType());
			} else if (criterioConsulta.getEstadoInstruccion()
					.getIdEstadoInstruccion() == 1) {// SIN_MATCH
				query.append("AND match.estadoMensajeString in ( ? , ? , ?) ");

				params.add("SIN_MATCH");
				tipos.add(new StringType());
				params.add("PRE_MATCH");
				tipos.add(new StringType());
				params.add("POSIBLE_MATCH");
				tipos.add(new StringType());
			} else {// para que discrimine si se coloca un valor diferente a todos y a los considerados.
				query.append("AND match.estadoMensajeString = '").append(criterioConsulta.getEstadoInstruccion().getIdEstadoInstruccion()).append("'");
			}

		}

		// Mensajes validos para el match
		query.append("and  match.tipoMensaje in ( ?, ? ) ");
		params.add("321B");
		tipos.add(new StringType());
		params.add("321L");
		tipos.add(new StringType());

		// fecha liquidación
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append("and  trunc(match.fechaLiquidacion) = ? ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		}

	}

	/**
	 * Construye el query con los criterios necesarios para consultar las
	 * instrucciones de retiros de fondos internacionales según los parámetros
	 * de criterios enviados.
	 * 
	 * @author fernando vazquez ulloa 06-11-2009
	 * @param query
	 *            Query donde se pone el resultado
	 * @param params
	 *            Lista de parámetros
	 * @param tipos
	 *            Lista de tipos
	 * @param criterio
	 *            Criterios a utilizar
	 */
	private void setCriteriosQueryRetiroEfectivoIntl(StringBuffer query,
			ArrayList<Object> params, ArrayList<Type> tipos,
			CriterioEstatusOpEfectivoDTO criterioConsulta) {

		// Asegurar que siempre haya algo al principio de las condiciones
		// FECHA LIQUIDACION
		
		query.append(" 1 = 1 ");
		
		// ID
		if (StringUtils.isNotEmpty(criterioConsulta.getId())
				&& criterioConsulta.getId() != null) {
			query.append("AND liberacion.idRetiroEfectivoInt = ? ");
			params.add(new BigInteger(criterioConsulta.getId()));
			tipos.add(new BigIntegerType());
		}

		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que
									// venga
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)  || isRolReceptor(criterioConsulta) ){
			query.append(" AND ");
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)) {
				
				query.append(" liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");
				params.add(String.valueOf(unSoloAgente.getInstitucion().getClaveTipoInstitucion() + unSoloAgente.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}
			if (isRolReceptor(criterioConsulta) || isRolAmbos(criterioConsulta)) {
				query.append(" liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");
				params.add(String.valueOf(unSoloAgente.getInstitucion().getClaveTipoInstitucion() + unSoloAgente.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
				//query.append("  match.idFolioReceptor = ? ");
				//query.append(" moi.idInstitucionTraspasante.tipoInstitucion.claveTipoInstitucion || moi.idInstitucionTraspasante.folioInstitucion  = ? ");
				//params.add(String.valueOf(unSoloAgente.getInstitucion().getClaveTipoInstitucion() + unSoloAgente.getInstitucion().getFolioInstitucion()));
				//tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ) ");
			}
		} else if (tieneAmbosAgentes(criterioConsulta)) { // Vienen ambos agentes
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)  || isRolReceptor(criterioConsulta) ){
				query.append(" AND ");
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolAmbos(criterioConsulta)
					|| isRolTraspasante(criterioConsulta)) {
				//query.append(" match.idFolioTraspasante = ? ");
				query.append("  liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");
				params.add(String.valueOf(criterioConsulta.getInstitucionParticipante().getClaveTipoInstitucion() + criterioConsulta.getInstitucionParticipante()								.getFolioInstitucion()));
				tipos.add(new StringType());

				//query.append(" AND match.idFolioReceptor = ? ");
				//query.append("   and moi.idInstitucionReceptora.tipoInstitucion.claveTipoInstitucion || moi.idInstitucionReceptora.folioInstitucion  = ? ");
				//
				//params.add(String.valueOf(criterioConsulta.getInstitucionContraparte().getClaveTipoInstitucion()	+ criterioConsulta.getInstitucionContraparte()								.getFolioInstitucion()));
				//tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}
			if (isRolAmbos(criterioConsulta) || isRolReceptor(criterioConsulta)) {
				//query.append("  match.idFolioTraspasante = ? ");
				//query.append("  and match.idFolioReceptor = ? ");				
				query.append("   liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");				
				params.add(String.valueOf(criterioConsulta						.getCuentaParticipante().getInstitucion()						.getClaveTipoInstitucion()						+ criterioConsulta.getCuentaParticipante()								.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());

				//query.append("   and moi.idInstitucionReceptora.tipoInstitucion.claveTipoInstitucion || moi.idInstitucionReceptora.folioInstitucion  = ? ");
				//params.add(String.valueOf(criterioConsulta						.getCuentaContraparte().getInstitucion()						.getClaveTipoInstitucion()						+ criterioConsulta.getCuentaContraparte()								.getInstitucion().getFolioInstitucion()));
				//tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		}
        
		
		
		// FECHA VALOR
		if (criterioConsulta.getFechaValor() != null) {
			query.append("and  trunc(liberacion.fechaValor) = ? ");
			params.add(criterioConsulta.getFechaValor());
			tipos.add(new DateType());
		}
		
		
		// FECHA EMVIO
		if (criterioConsulta.getFechaEnvio() != null) {
			query.append("and  trunc(liberacion.fechaEnvio) = ? ");
			params.add(criterioConsulta.getFechaEnvio());
			tipos.add(new DateType());
		}
		
		// FECHA AUTORIZACION
		if (criterioConsulta.getFechaAutorizacion() != null) {
			query.append("and  trunc(liberacion.fechaAutorizacion) = ? ");
			params.add(criterioConsulta.getFechaAutorizacion());
			tipos.add(new DateType());
		}
		
		// FECHA LIBERACIOJ
		if (criterioConsulta.getFechaLiberacion() != null) {
			query.append("and  trunc(liberacion.fechaLiberacion) = ? ");
			params.add(criterioConsulta.getFechaLiberacion());
			tipos.add(new DateType());
		}
		
		//ESTADO INSTRUCCION		
		query.append("AND liberacion.estado.idEstadoInstruccion in (19,23,24) ");
		
		if (criterioConsulta.getEstadoInstruccion() != null
				&& criterioConsulta.getEstadoInstruccion()
						.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.estado.idEstadoInstruccion = ? ");
			params.add(new BigInteger(  String.valueOf(criterioConsulta.getEstadoInstruccion().getIdEstadoInstruccion())));
			tipos.add(new BigIntegerType());
			}
		
		//DIVISAS
		
		if (criterioConsulta.getDivisa() != null
				&& criterioConsulta.getDivisa().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.divisa.idDivisa = ? ");
			//params.add(criterioConsulta.getDivisa().getId());
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getDivisa().getId()).toString()) );
			tipos.add(new BigIntegerType());
			}

		//BOVEDA
		if (criterioConsulta.getBoveda() != null
				&& criterioConsulta.getBoveda().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.boveda.idBoveda = ? ");			
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getBoveda().getId()).toString()) );
			tipos.add(new BigIntegerType());
			}

		
		

	}
	
	
	private void setCriteriosQueryRetiroEfectivoNal(StringBuffer query,
			ArrayList<Object> params, ArrayList<Type> tipos,
			CriterioEstatusOpEfectivoDTO criterioConsulta) {

		// Asegurar que siempre haya algo al principio de las condiciones
		// FECHA LIQUIDACION
		
		query.append(" 1 = 1 ");
		
		if (criterioConsulta == null){
			return;
		}
		
		// ID
		if (StringUtils.isNotEmpty(criterioConsulta.getId())
				&& criterioConsulta.getId() != null) {
			query.append("AND liberacion.idRetiroEfectivoNal = ? ");
			params.add(new BigInteger(criterioConsulta.getId()));
			tipos.add(new BigIntegerType());
		}
		
		
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append("AND trunc(liberacion.fechaCreacion) = trunc(?) ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		}
		
		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que
									// venga
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)  || isRolReceptor(criterioConsulta) ){
				query.append(" AND ");
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)) {
				
				query.append(" liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");
				params.add(String.valueOf(unSoloAgente.getInstitucion().getClaveTipoInstitucion() + unSoloAgente.getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}
			if (isRolReceptor(criterioConsulta) || isRolAmbos(criterioConsulta)) {
				
				query.append("   liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");				
				params.add(String.valueOf(criterioConsulta.getCuentaParticipante().getInstitucion().getClaveTipoInstitucion()+ criterioConsulta.getCuentaParticipante().getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
				
				query.append(" AND  liberacion.idInstReceptor.tipoInstitucion.claveTipoInstitucion || liberacion.idInstReceptor.folioInstitucion  = ? ");
				params.add(String.valueOf(criterioConsulta.getCuentaParticipante().getInstitucion().getClaveTipoInstitucion()+ criterioConsulta.getCuentaParticipante().getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());		
								
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ) ");
			}
		} else if (tieneAmbosAgentes(criterioConsulta)) { // Vienen ambos agentes
			if (isRolTraspasante(criterioConsulta) 	|| isRolAmbos(criterioConsulta)  || isRolReceptor(criterioConsulta) ){
				query.append(" AND ");
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" ( ");
			}
			if (isRolAmbos(criterioConsulta)
					|| isRolTraspasante(criterioConsulta)) {
				//query.append(" match.idFolioTraspasante = ? ");
				query.append("  liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");
				params.add(String.valueOf(criterioConsulta.getInstitucionParticipante().getClaveTipoInstitucion() + criterioConsulta.getInstitucionParticipante().getFolioInstitucion()));
				tipos.add(new StringType());
				
				query.append(" AND  liberacion.idInstReceptor.tipoInstitucion.claveTipoInstitucion || liberacion.idInstReceptor.folioInstitucion  = ? ");
				params.add(String.valueOf(criterioConsulta.getInstitucionContraparte().getClaveTipoInstitucion() + criterioConsulta.getInstitucionContraparte().getFolioInstitucion()));
				tipos.add(new StringType());
				
			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(" OR ");
			}
			if (isRolAmbos(criterioConsulta) || isRolReceptor(criterioConsulta)) {
				//query.append("  match.idFolioTraspasante = ? ");
				//query.append("  and match.idFolioReceptor = ? ");				
				query.append("   liberacion.institucion.tipoInstitucion.claveTipoInstitucion || liberacion.institucion.folioInstitucion  = ? ");				
				params.add(String.valueOf(criterioConsulta.getCuentaParticipante().getInstitucion().getClaveTipoInstitucion()+ criterioConsulta.getCuentaParticipante().getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());
				
				query.append(" AND  liberacion.idInstReceptor.tipoInstitucion.claveTipoInstitucion || liberacion.idInstReceptor.folioInstitucion  = ? ");
				params.add(String.valueOf(criterioConsulta.getCuentaParticipante().getInstitucion().getClaveTipoInstitucion()+ criterioConsulta.getCuentaParticipante().getInstitucion().getFolioInstitucion()));
				tipos.add(new StringType());


			}
			if (isRolAmbos(criterioConsulta)) {
				query.append(") ");
			}
		}
        
		
		// REFERENCIA OPERACION
		// se agrego la validacion de que el dato sea numerico
		if (StringUtils.isNotEmpty(criterioConsulta.getReferenciaOperacion())
				&& criterioConsulta.getReferenciaOperacion() != null
				&& StringUtils.isNumeric(criterioConsulta.getReferenciaOperacion())) {
			query.append("AND liberacion.referenciaOperacion = ? ");
			params.add(criterioConsulta.getReferenciaOperacion());
			tipos.add(new StringType());
		}
		
		
		// FECHA VALOR
		/*
		if (criterioConsulta.getFechaValor() != null) {
			query.append("and  trunc(liberacion.fechaValor) = ? ");
			params.add(criterioConsulta.getFechaValor());
			tipos.add(new DateType());
		}
		*/
		
		// FECHA EMVIO
		if (criterioConsulta.getFechaEnvio() != null) {
			query.append("and  trunc(liberacion.fechaEnvio) = ? ");
			params.add(criterioConsulta.getFechaEnvio());
			tipos.add(new DateType());
		}
		
		// FECHA AUTORIZACION
		if (criterioConsulta.getFechaAutorizacion() != null) {
			query.append("and  trunc(liberacion.fechaAutorizacion) = ? ");
			params.add(criterioConsulta.getFechaAutorizacion());
			tipos.add(new DateType());
		}
		
		// FECHA LIBERACIOJ
		if (criterioConsulta.getFechaLiberacion() != null) {
			query.append("and  trunc(liberacion.fechaLiberacion) = ? ");
			params.add(criterioConsulta.getFechaLiberacion());
			tipos.add(new DateType());
		}
		
		// FECHA REGISTRO
		if (criterioConsulta.getFechaRegistro() != null) {
			query.append("and  trunc(liberacion.fechaCreacion) = ? ");
			params.add(criterioConsulta.getFechaRegistro());
			tipos.add(new DateType());
		}
		
		//ESTADO INSTRUCCION		
		query.append("AND liberacion.estado.idEstadoInstruccion in (19,23,2) ");
		
		
		if (criterioConsulta.getEstadoInstruccion() != null
				&& criterioConsulta.getEstadoInstruccion()
						.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.estado.idEstadoInstruccion = ? ");
			params.add(new BigInteger(  String.valueOf(criterioConsulta.getEstadoInstruccion().getIdEstadoInstruccion())));
			tipos.add(new BigIntegerType());
			}
		
		//DIVISAS
		
		if (criterioConsulta.getDivisa() != null
				&& criterioConsulta.getDivisa().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.divisa.idDivisa = ? ");
			//params.add(criterioConsulta.getDivisa().getId());
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getDivisa().getId()).toString()) );
			tipos.add(new BigIntegerType());
			}

		//BOVEDA
		if (criterioConsulta.getBoveda() != null
				&& criterioConsulta.getBoveda().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND liberacion.boveda.idBoveda = ? ");			
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getBoveda().getId()).toString()) );
			tipos.add(new BigIntegerType());
			}

		
		

	}

	/**
	 * Construye el query con los criterios necesarios para consultar las
	 * instrucciones de retiros de fondos internacionales en el MOI según los
	 * parámetros de criterios enviados.
	 * 
	 * @author fernando vazquez ulloa 06-11-2009
	 * @param query
	 *            Query donde se pone el resultado
	 * @param params
	 *            Lista de parámetros
	 * @param tipos
	 *            Lista de tipos
	 * @param criterio
	 *            Criterios a utilizar
	 */
	private void setCriteriosQueryRetiroFondosIntlEnMoi(StringBuffer query,
			ArrayList<Object> params, ArrayList<Type> tipos,
			CriterioEstatusOpEfectivoDTO criterioConsulta) {

		// Asegurar que siempre haya algo al principio de las condiciones
		// FECHA LIQUIDACION
		if (criterioConsulta.getFechaLiquidacion() != null) {
			query.append(" trunc(moi.fechaLiquidacion) = ? ");
			params.add(criterioConsulta.getFechaLiquidacion());
			tipos.add(new DateType());
		} else {
			query.append(" 1 = 1 ");
		}

		// ID
		if (StringUtils.isNotEmpty(criterioConsulta.getId())
				&& criterioConsulta.getId() != null) {
			query.append("AND moi.idInternacionalEfectivo = ? ");
			params.add(new BigInteger(criterioConsulta.getId()));
			tipos.add(new BigIntegerType());
		}

		CuentaDTO unSoloAgente = tieneUnAgente(criterioConsulta);
		// ROL
		if (unSoloAgente != null) { // Solo viene una agente y obtenemos el que									// venga
				if (unSoloAgente.getInstitucion().getClaveTipoInstitucion()!= null && unSoloAgente.getInstitucion().getFolioInstitucion() != null){

						if (unSoloAgente.getInstitucion().getClaveTipoInstitucion()!= null){
							query.append(" and moi.idInstitucionTraspasante.tipoInstitucion.claveTipoInstitucion  = ? ");
							params.add(String.valueOf(unSoloAgente.getInstitucion().getClaveTipoInstitucion() ));
							tipos.add(new StringType());
						}
						if (unSoloAgente.getInstitucion().getFolioInstitucion() != null){
							query.append(" and moi.idInstitucionTraspasante.folioInstitucion  = ? ");
							params.add(String.valueOf(unSoloAgente.getInstitucion().getFolioInstitucion()));
							tipos.add(new StringType());
						}
				}
			}
		 
			
		
		
		// REFERENCIA OPERACION
		// se agrego la validacion de que el dato sea numerico
		if (StringUtils.isNotEmpty(criterioConsulta.getReferenciaOperacion())
				&& criterioConsulta.getReferenciaOperacion() != null
				&& StringUtils.isNumeric(criterioConsulta.getReferenciaOperacion()))  {
			query.append("AND moi.referenciaOperacion = ? ");
			params.add(criterioConsulta.getReferenciaOperacion());
			tipos.add(new StringType());
		}

		
		
		
		// TIPO DE INSTRUCCION
		/*
		if (criterioConsulta.getTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() != null
				&& criterioConsulta.getTipoInstruccion().getIdTipoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {			
				query.append("AND moi.idTipoInstruccion.idTipoInstruccion = ? ");
				params.add(new BigInteger(criterioConsulta.getTipoInstruccion().getIdTipoInstruccion().toString())); 
				tipos.add(new BigIntegerType());
		}
		*/
		
		
	
		// FECHA VALOR
		if (criterioConsulta.getFechaValor() != null) {
			query.append("and  trunc(moi.fechaValor) = ? ");
			params.add(criterioConsulta.getFechaValor());
			tipos.add(new DateType());
		}
		
		// FECHA ENVIO - REGISTRO
		if (criterioConsulta.getFechaEnvio() != null) {
			query.append("and  trunc(moi.fechaRegistro) = ? ");
			params.add(criterioConsulta.getFechaEnvio());
			tipos.add(new DateType());
		}
		

		//DIVISAS
		
		if (criterioConsulta.getDivisa() != null && criterioConsulta.getDivisa().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND moi.idDivisa.id = ? ");
			//params.add(criterioConsulta.getDivisa().getId());
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getDivisa().getId()).toString()) );
			tipos.add(new BigIntegerType());
			}
		
		//BOVEDA
		
		if (criterioConsulta.getBoveda() != null && criterioConsulta.getBoveda().getId() > DaliConstants.VALOR_COMBO_TODOS) {			
			query.append("AND moi.idBoveda.id = ? ");
			//params.add(criterioConsulta.getBoveda().getId());
			params.add( new  BigInteger(Long.valueOf(criterioConsulta.getBoveda().getId()).toString()));
			tipos.add(new BigIntegerType());
			}
		
		}
		





	/**
	 * Obtiene el tipo de operación equivalente a un tipo de instrucción.
	 * 
	 * @author Sacada del ejemplo de valores.
	 * @param tipoInstruccion
	 *            el tipo de instrucción del cual se desea obtener la
	 *            equivalencia.
	 * @return una cadena con el tipo de operación equivalente a un tipo de
	 *         instrucción.
	 */
	private String obtenerTipoDeOperacionEquivalenteATipoInstruccion(
			String tipoInstruccion) {
		String tipoOperacion = null;

		for (String llave : mapeoOperacionInstruccion.keySet()) {
			if (mapeoOperacionInstruccion.get(llave).getNombreCorto().equals(
					tipoInstruccion)) {
				tipoOperacion = llave;
				if (tipoOperacion.equals("TEFV")) {
					tipoOperacion = "TEFV";
				}
			}
		}

		return tipoOperacion;
	}

	private StringBuffer setBaseQueryConsultarInstruccionesEfectivo() {
		final StringBuffer query = new StringBuffer();

		query.append("FROM " + InstruccionEfectivo.class.getName() + " instruccion ");
		query.append("	left join fetch instruccion.institucionTraspasante ");
		query.append("	left join fetch instruccion.institucionReceptora ");
		query.append("	left join fetch instruccion.institucionTraspasante.tipoInstitucion ");
		query.append("	left join fetch instruccion.institucionReceptora.tipoInstitucion ");
		query.append("	left join fetch instruccion.cuentaTraspasante ");
		query.append("	left join fetch instruccion.cuentaReceptora ");
		query.append("	left join fetch instruccion.cuentaTraspasante.institucion ");
		query.append("	left join fetch instruccion.cuentaReceptora.institucion ");
		query.append("	left join fetch instruccion.cuentaTraspasante.institucion.tipoInstitucion ");
		query.append("	left join fetch instruccion.cuentaReceptora.institucion.tipoInstitucion ");
		query.append("	left join fetch instruccion.tipoInstruccion ");
		query.append("	left join fetch instruccion.tipoLiquidacion ");
		query.append("	left join fetch instruccion.estadoInstruccion ");
		query.append("	left join fetch instruccion.tipoPago ");
		query.append("	left join fetch instruccion.tipoRetiro ");
		query.append("	left join fetch instruccion.divisa ");
		query.append("	left join fetch instruccion.boveda ");
		
		query.append("WHERE ");

		return query;
	}
	
	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	@Override
	public InstruccionEfectivo getById(BigInteger idInstruccion) {
		return (InstruccionEfectivo) getHibernateTemplate().get(InstruccionEfectivo.class, idInstruccion);
	}

	@Override
	public InstruccionEfectivoSimple getByFolioInstruccion(BigInteger folioInstruccion) {
		StringBuffer query = new StringBuffer();
		InstruccionEfectivoSimple result = null;
		query.append("FROM " + InstruccionEfectivoSimple.class.getName() + " instruccion ");
		query.append("WHERE instruccion.folioInstruccion = " + folioInstruccion);
		
		@SuppressWarnings("unchecked")
		List<InstruccionEfectivoSimple> lista = getHibernateTemplate().find(query.toString());
		if (lista != null && lista.size() > 0) {
			result = lista.get(0);
		}
		
		return result;
	}

}
