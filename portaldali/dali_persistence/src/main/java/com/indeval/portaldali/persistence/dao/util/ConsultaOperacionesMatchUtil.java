/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.model.operacionesMatch.ConsultaInstruccionesMatchSinOperacion;

/**
 * Utileria para la consulta de Operaciones y Match
 * 
 * @author Pablo Balderas
 *
 */
public class ConsultaOperacionesMatchUtil {

	/** Lista con los parametros */
	private List<Object> parametros;
	
	/** Lista con los tipos de los parametros */
	private List<Type> tipos;
	
	public static DetachedCriteria crearCriteria() {
		DetachedCriteria d = DetachedCriteria.forClass(null);
		
		return d;
	}
	
	
	/**
	 * Metodo que crea el query para realizar la consulta de instrucciones match sin operacion.
	 * @param session Sesion de hibernate para crear el query.
	 * @param criterio Objeto con los parametros para realizar la consulta.
	 * @return Query generado.
	 */
	public SQLQuery crearQueryConsultaInstruccionesMatchSinOperacion(Session session, CriterioMatchOperacionesDTO criterio) {
		try {
			StringBuilder strQuery = new StringBuilder();
			parametros = new ArrayList<Object>();
			tipos = new ArrayList<Type>();
			SQLQuery query = session.createSQLQuery(strQuery.toString());
			strQuery.append("select ");
			strQuery.append("FOLIO_CONTROL as folioControl, ");
			strQuery.append("POR_PAQUETE as porPaquete, ");
			strQuery.append("REFERENCIA_PAQUETE as referenciaPaquete, ");
			strQuery.append("FOLIO_ORIGEN as folioOrigen, ");
			strQuery.append("TIPO_OPERACION_INSTRUCCION as nombreCortoTipoInstruccion, ");
			strQuery.append("ORIGEN as origen, ");
			strQuery.append("TV as tv, ");
			strQuery.append("EMISORA as emisora, ");
			strQuery.append("SERIE as serie, ");
			strQuery.append("CUPON as cupon, ");
			strQuery.append("BOVEDA_VALORES as bovedaValores, ");
			strQuery.append("CLAVE_ESTADO_INSTRUCCION_CAT as claveEstadoInstruccionCat, ");
			strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE as idFolioInstitucionTraspasante, ");
			strQuery.append("CUENTA_TRASPASANTE as cuentaTraspasante, ");
			strQuery.append("ID_FOLIO_INSTIT_RECEPTORA as idFolioInstitucionReceptora, ");
			strQuery.append("CUENTA_RECEPTORA as cuentaReceptora, ");
			strQuery.append("PRECIO_TITULO as precioTitulo, ");
			strQuery.append("FECHA_REPORTO as fechaReporto, ");
			strQuery.append("CLAVE_TIPO_MENSAJE_CAT as claveTipoMensajeCat, ");
			strQuery.append("FECHA_HORA_REGISTRO as fechaConcertacion, ");
			strQuery.append("FECHA_LIQUIDACION as fechaLiquidacion, ");
			strQuery.append("CANTIDAD_TITULOS as cantidadTitulos, ");
			strQuery.append("IMPORTE as importe, ");
			strQuery.append("BOVEDA_EFECTIVO as bovedaEfectivo, ");
			strQuery.append("TASA_NEGOCIADA as tasaNegociada, "); 
			strQuery.append("PLAZO_REPORTO as plazoReporto, ");
			strQuery.append("CLAVE_DIVISA as claveDivisa ");
			strQuery.append("from V_CONS_INSTR_MATCH_SIN_OPER ");
			strQuery.append("where 1 = 1 ");
			
			//Tipo de mercado
			if (criterio.getMercado() != null && criterio.getMercado().getId() != DaliConstants.VALOR_COMBO_TODOS) {
				if (criterio.getMercado().getId() == DaliConstants.ID_MERCADO_DINERO) {
					strQuery.append("and ID_MERCADO IN (?,?) ");
					parametros.add(DaliConstants.ID_MERCADO_PAPEL_BANCARIO);
					tipos.add(new LongType());
					parametros.add(DaliConstants.ID_MERCADO_PAPEL_GUBER);
					tipos.add(new LongType());
				}
				else {
					strQuery.append("and ID_MERCADO = ? ");
					parametros.add(new BigInteger(String.valueOf(criterio.getMercado().getId())));
					tipos.add(new BigIntegerType());
				}
			}
			//Divisa
			if (criterio.getDivisa()!= null && criterio.getDivisa().getId() != DaliConstants.VALOR_COMBO_TODOS &&
					StringUtils.isNotEmpty(criterio.getDivisa().getClaveAlfabetica())) {
				strQuery.append("and CLAVE_DIVISA = ? ");
				parametros.add(criterio.getDivisa().getClaveAlfabetica());
				tipos.add(new StringType());
			}
			//Boveda efectivo
			if (criterio.getBovedaEfectivo()!= null && criterio.getBovedaEfectivo().getId() != DaliConstants.VALOR_COMBO_TODOS &&
					StringUtils.isNotEmpty(criterio.getBovedaEfectivo().getNombreCorto()) ) {
				strQuery.append("and BOVEDA_EFECTIVO = ? ");
				parametros.add(criterio.getBovedaEfectivo().getNombreCorto());
				tipos.add(new StringType());
			}
			//Boveda valores
			if (criterio.getBovedaValores()!= null && criterio.getBovedaValores().getId() != DaliConstants.VALOR_COMBO_TODOS &&
					StringUtils.isNotEmpty(criterio.getBovedaValores().getNombreCorto()) ) {
				strQuery.append("and BOVEDA_VALORES = ? ");
				parametros.add(criterio.getBovedaValores().getNombreCorto());
				tipos.add(new StringType());
			}
			// Folio usuario
			if (StringUtils.isNotBlank(criterio.getFolioUsuario())) {
				strQuery.append("and FOLIO_INSTRUCCION = ? ");
				parametros.add(criterio.getFolioUsuario());
				tipos.add(new StringType());
			}
			//Fecha Concertacion (registro)
			if (criterio.getFechaConcertacion() != null) {
				strQuery.append("and FECHA_HORA_REGISTRO = ? ");
				parametros.add(criterio.getFechaConcertacion());
				tipos.add(new DateType());
			}
			//Fecha liquidacion
			if (criterio.getFechaLiquidacion() != null) {
				strQuery.append("and FECHA_LIQUIDACION = ? ");
				parametros.add(criterio.getFechaLiquidacion());
				tipos.add(new DateType());
			}
			//Tipo de Valor
			if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null &&
					criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {
				strQuery.append("and TV = ? ");
				parametros.add(criterio.getEmision().getTipoValor().getClaveTipoValor());
				tipos.add(new StringType());
			}
			//Emisora
			if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null &&
					criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS) {
				strQuery.append("and EMISORA = ? ");
				parametros.add(criterio.getEmision().getEmisora().getDescripcion());
				tipos.add(new StringType());
			}
			//Serie
			if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null &&
					StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())) {
				strQuery.append("and SERIE = ? ");
				parametros.add(criterio.getEmision().getSerie().getSerie());
				tipos.add(new StringType());
			}
			//Remitente
			if (criterio.isRemitente()) {
				strQuery.append("and MENSAJE_XML like ? ");
				parametros.add("%<FolioInstitucion>" + criterio.getInstitucionParticipante().getFolioInstitucion() + "</FolioInstitucion>%<IdInstitucion>" +
						criterio.getInstitucionParticipante().getClaveTipoInstitucion() + "</IdInstitucion>%");
				tipos.add(new StringType());
			}
			//Origen
			if (StringUtils.isNotBlank(criterio.getOrigen())) {
				strQuery.append("and ORIGEN = ? ");
				parametros.add(criterio.getOrigen());
				tipos.add(new StringType());
			}
			//Cantidad de titulos
			if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
				strQuery.append("and CANTIDAD_TITULOS = ? ");
				parametros.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
				tipos.add(new LongType());
			}
			//Monto
			if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
				strQuery.append("and TO_CHAR(to_number(IMPORTE),'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
				parametros.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
				tipos.add(new BigDecimalType());
			}

//			// referencia paquete
//			if (!StringUtils.isEmpty(criterio.getReferenciaPaquete())) {
//				//TODO
//				strQuery.append(" upper(trim(match.referenciaPaquete)) = upper(trim(?)) "
//						+ "and (match.estadoMensajeString not in ('SIN_MATCH', 'CON_MATCH',"
//						+ "'SIN_MATCH', 'CANCELADA', 'CANCELAR_APLICADO', "
//						+ "'CANCELAR_NO_APLICADO', 'POSIBLE_MATCH') "
//						+ "and FECHA_LIQUIDACION < current_date()))");
//				parametros.add(criterio.getReferenciaPaquete());
//				tipos.add(new StringType());
//			}
			
			//Folio control
			if(StringUtils.isNotBlank(criterio.getFolioControl())) {
				strQuery.append("and FOLIO_CONTROL = ? ");
				parametros.add(criterio.getFolioControl());
				tipos.add(new StringType());
			}
			
			query.setParameters(parametros.toArray(), tipos.toArray(new Type[] {}));
			query.addScalar("folioControl", Hibernate.LONG);
			query.addScalar("porPaquete", Hibernate.BOOLEAN); 
			query.addScalar("referenciaPaquete", Hibernate.STRING);
			query.addScalar("folioOrigen", Hibernate.LONG);
			query.addScalar("nombreCortoTipoInstruccion", Hibernate.STRING);
			query.addScalar("origen", Hibernate.STRING);
			query.addScalar("tv", Hibernate.STRING); 
			query.addScalar("emisora", Hibernate.STRING);
			query.addScalar("serie", Hibernate.STRING);
			query.addScalar("cupon", Hibernate.STRING);
			query.addScalar("bovedaValores", Hibernate.STRING);
			query.addScalar("claveEstadoInstruccionCat", Hibernate.STRING);
			query.addScalar("idFolioInstitucionTraspasante", Hibernate.STRING);
			query.addScalar("cuentaTraspasante", Hibernate.STRING);
			query.addScalar("idFolioInstitucionReceptora", Hibernate.STRING);
			query.addScalar("cuentaReceptora", Hibernate.STRING);
			query.addScalar("precioTitulo", Hibernate.DOUBLE);
			query.addScalar("fechaReporto", Hibernate.DATE);
			query.addScalar("claveTipoMensajeCat", Hibernate.STRING);
			query.addScalar("fechaConcertacion", Hibernate.DATE);
			query.addScalar("fechaLiquidacion", Hibernate.DATE);
			query.addScalar("cantidadTitulos", Hibernate.LONG);
			query.addScalar("importe", Hibernate.STRING); 
			query.addScalar("bovedaEfectivo", Hibernate.STRING);
			query.addScalar("tasaNegociada", Hibernate.DOUBLE); 
			query.addScalar("plazoReporto", Hibernate.LONG);
			query.addScalar("claveDivisa", Hibernate.STRING);
			query.setResultTransformer(new AliasToBeanResultTransformer(ConsultaInstruccionesMatchSinOperacion.class));			
			return query;
		}
		catch(Exception e) {
			throw new BusinessException("");
		}
		finally {
			parametros.clear();
			parametros = null;
			tipos.clear();
			tipos = null;
		}
	}
	
	
	
	
}
