package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioCalendarioParticipante;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoIntBR;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoValorCalendarioInternacional;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;

/**
 * @author lmunoz
 *
 */ 
 class CalendarioEmisionesDeudaExtHelper {
	 
	enum TipoCalendario{PARTICIPANTES,INDEVAL,HOJA_TRABAJO,}
	 
	String filtroCapitales;
	
	public String getFiltroCapitales() {
		return filtroCapitales;
	}

	public void setFiltroCapitales(String filtroCapitales) {
		this.filtroCapitales = filtroCapitales;
	}

	public CalendarioEmisionesDeudaExtHelper(){
				
	}
	
	public CalendarioInternacionalQuery createCalendarioIntSQL(CalendarioEmisionesDeudaExtDTO params){
		CalendarioInternacionalQuery query = new CalendarioInternacionalQuery();
		//enunciado del select
		query.setSelectEnunciado("select distinct cal ");
		//enunciado para el counter 
		query.setSelectCounterEnunciado("select distinct count(*) ");		
		
		//reglas
		switch(tipoCalendario(params)){
			case INDEVAL: 				
				fromCalendarioIndeval(query);
				construyeParams(query,params);
				whereCalendarioIndeval(query,params);
				orderCalendarioIndeval(query);
				break;
			case PARTICIPANTES: 						
				fromCalendarioParticipantes(query);
				construyeParams(query,params);
				whereParticipantes(query,params);
				orderParticipantes(query);
				break;
			case HOJA_TRABAJO:				
				fromCalendarioIndeval(query);
				construyeParams(query,params);
				whereHojaTrabajo(query,params);
				whereCalendarioIndeval(query,params);
				orderHojaTrabajo(query);
				break;
		}		
		return query;
	}
	
	/**
	 * Configura reglas de negocio para tipos de calendario
	 * @param params
	 * @return enum Tipo de Calendario
	 */
	private TipoCalendario tipoCalendario(CalendarioEmisionesDeudaExtDTO params){
		if(params.isCalendarioIndeval() ){
			if (params.isHojaDeTrabajo()){
				return TipoCalendario.HOJA_TRABAJO;
			}else{
				return TipoCalendario.INDEVAL;
			}
		}else{
			return TipoCalendario.PARTICIPANTES;
		}
	}
	/*===========================================
	 * Metodos FROM
	 *============================================*/
	private void fromCalendarioIndeval(CalendarioInternacionalQuery query){
		query.setEnunciado(" FROM " + CalendarioDerechos.class.getName() + " cal ");		
	}
	private void fromCalendarioParticipantes(CalendarioInternacionalQuery query){
		query.setEnunciado(" FROM " + CalendarioDerechos.class.getName() + " cal ");
		query.setEnunciado(" , "+TipoPagoIntBR.class.getName()+" as tpbr ");
	}
	
	/*============================================
	 * Metodos WHERE
	 *============================================*/
	
	private void whereHojaTrabajo(CalendarioInternacionalQuery query, CalendarioEmisionesDeudaExtDTO params){
		//No liquidados
		query.setEnunciado(" AND ((cal.estado.id =6 AND cal.prioridad <> 0) OR cal.estado.id not in(6,7) ) ");			
		
		//fechaCorte de hoy para atras
		query.setEnunciado(" AND (cal.fechaPago <= ? OR ( cal.prioridad <> 0) ) ");			
		try {
			query.setParamSql(now(),new TimestampType());								
		} catch (Exception e) {
			throw new BusinessException("FECHAPAGO no es un una fecha dd-MM-yyyy");
		}
		//solo clearstream y euroclear: TEMPORAL
		if(params.getCustodio()== null ||  params.getCustodio()<1){
			//query.setEnunciado(" AND cal.custodio.id in(1,2) ");
			// se modifican los valores fijos del idCustodio permitidos, ahora toma los valores de la tabla R_CUSTODIO_CAL_PART_INT
			query.setEnunciado(" AND cal.custodio.id in(SELECT custodioCalPart.idCustodio FROM  "+ CustodioCalendarioParticipante.class.getName() +" custodioCalPart) ");
		}
	}
	
	private void whereParticipantes(CalendarioInternacionalQuery query, CalendarioEmisionesDeudaExtDTO params){
		//para los participantes
		
			//solo se permiten mostrar las combinaciones definidas en R_CAMV_CAEV			
		query.setEnunciado(" AND cal.tipoPagoCAMV.id = tpbr.idCamv ");
		query.setEnunciado(" AND cal.tipoPagoCAEV.id = tpbr.idCaev ");
		//query.setEnunciado(" AND cal.tipoValor in ('D1','D2','D3','D7','D8','JI') ");
		// se modifican los valores fijos del tipoValor permitidos, ahora toma los valores de la tabla R_TV_CALENDARIO_INT
		query.setEnunciado(" AND cal.tipoValor in (SELECT tvCalendario.tipoValor FROM  "+ TipoValorCalendarioInternacional.class.getName() +" tvCalendario) ");
			//solo clearstream y euroclear: TEMPORAL
		//query.setEnunciado(" AND cal.custodio.id in(1,2) ");
		// se modifican los valores fijos del idCustodio permitidos, ahora toma los valores de la tabla R_CUSTODIO_CAL_PART_INT
		query.setEnunciado(" AND cal.custodio.id in(SELECT custodioCalPart.idCustodio FROM  "+ CustodioCalendarioParticipante.class.getName() +" custodioCalPart) ");
		
		if(params.isAll()){			
			query.setEnunciado(" AND ((cal.estado.id =6  AND cal.fechaPago >= ? )");			
			//fechaCorte de hoy para atras
			query.setEnunciado(" or (cal.estado.id =7 AND cal.fechaPago >= ? ) or (cal.estado.id =10 AND cal.fechaPago >= ? ) or cal.estado.id in(1,2,3,4,5)) ");			
			try {
				query.setParamSql(daysFromNow(-7),new TimestampType());	
				query.setParamSql(daysFromNow(-7),new TimestampType());
				query.setParamSql(daysFromNow(-7),new TimestampType());
									
			} catch (Exception e) {
				throw new BusinessException("FECHAPAGO no es un una fecha dd-MM-yyyy");
			}
		}else{
			query.setEnunciado(" AND cal.estado.id in(1,2,3,4,5,6,7,10)	 ");
		}
	}
	
	private void whereCalendarioIndeval(CalendarioInternacionalQuery query, CalendarioEmisionesDeudaExtDTO params){
		//para los operadores del calendario
		String filtroCapitalesEditado = this.editarFiltroCapitales(this.filtroCapitales);
		query.setEnunciado(" AND ( cal.tipoValor NOT IN (" + filtroCapitalesEditado + ") OR  ( cal.horaEnvio is null OR cal.horaRecepcion is null ) )");
		if ( params.isAll() ) {
			//solo clearstream y euroclear: TEMPORAL			
			//query.setEnunciado(" AND cal.custodio.id in(1,2) ");
			// se modifican los valores fijos del idCustodio permitidos, ahora toma los valores de la tabla R_CUSTODIO_CAL_PART_INT
			query.setEnunciado(" AND cal.custodio.id in(SELECT custodioCalPart.idCustodio FROM  "+ CustodioCalendarioParticipante.class.getName() +" custodioCalPart) ");
		}
	}

	private String editarFiltroCapitales(String filtroCapitales) {
        String[] listaFiltro = filtroCapitales.split(",");
		String filtroCapitalesEditado = "";
		for (String filtro : listaFiltro) {
			filtroCapitalesEditado += "'" + filtro + "',";
		}
		filtroCapitalesEditado = filtroCapitalesEditado.substring(0, filtroCapitalesEditado.length() -1 );
		return filtroCapitalesEditado;
	}
	
	private void construyeParams( CalendarioInternacionalQuery query, CalendarioEmisionesDeudaExtDTO params) {
		
		query.setEnunciado(" WHERE cal.idCalendario is not null ");		

		if(params.getIdCalendario()!=null &&  params.getIdCalendario()>-1){
			query.setEnunciado(" AND cal.idCalendario = ? ");
			query.setParamSql(params.getIdCalendario(), new LongType());			
		}
		
		if(params.getCupon()!=null && !params.getCupon().equals("")){
			query.setEnunciado(" AND cal.cupon = ? ");
			query.setParamSql(params.getCupon(),new StringType());			
		}
		if(params.getCustodio()!=null &&  params.getCustodio()>0){
			query.setEnunciado(" AND cal.custodio.id = ? ");
			query.setParamSql(params.getCustodio(),new IntegerType());			
		}
		if(params.getDivisa()!=null && !params.getDivisa().equals("")){
			query.setEnunciado(" AND cal.divisa = ? ");
			query.setParamSql(params.getDivisa(),new StringType());			
		}
		if(params.getEmisora()!=null && !params.getEmisora().equals("")){
			query.setEnunciado(" AND cal.emisora = ? ");
			query.setParamSql(params.getEmisora(),new StringType());			
		}
		if(params.getEstado()!=null && params.getEstado()>0){
			query.setEnunciado(" AND cal.estado.id = ? ");
			query.setParamSql(params.getEstado(),new IntegerType());			
		}
		if(params.getFechaCorte()!=null ){
			query.setEnunciado(" AND cal.fechaCorte between ? and ? ");			
			try {
				query.setParamSql(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorte(), true), new TimestampType());			
				
				query.setParamSql(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorte(), false), new TimestampType());
				
			} catch (Exception e) {
				throw new BusinessException("FECHACORTE no es un una fecha dd-MM-yyyy");
			}
		}
		if(params.getFechaPago()!=null ){
			query.setEnunciado(" AND cal.fechaPago between ? and ? ");	
			try {
				query.setParamSql(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPago(), true), new TimestampType());
				
				query.setParamSql(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPago(), false), new TimestampType());
				
			} catch (Exception e) {
				throw new BusinessException("FECHAPAGO no es un una fecha dd-MM-yyyy");
			}
		}
		
		if(params.getTipoValor()!=null && !params.getTipoValor().equals("")){			
				query.setEnunciado(" AND cal.tipoValor = ? ");
				query.setParamSql(params.getTipoValor(),new StringType());			
		}
		if(params.getIsin()!=null && !params.getIsin().equals("")){
			query.setEnunciado(" AND cal.isin = ? ");
			query.setParamSql(params.getIsin(),new StringType());			
		}
		if(params.getRefCustodio()!=null && !params.getRefCustodio().equals("")){
			query.setEnunciado(" AND cal.referencia = ? ");
			query.setParamSql(params.getRefCustodio(),new StringType());
			
		}
		
		if(params.getSerie()!=null && !params.getSerie().equals("")){
			query.setEnunciado(" AND cal.serie = ? ");
			query.setParamSql(params.getSerie(), new StringType());			
		}
		if(params.getTipoPagoCAMV()!=null && params.getTipoPagoCAMV() >= 0){
			query.setEnunciado(" AND cal.tipoPagoCAMV.id = ? ");
			query.setParamSql(params.getTipoPagoCAMV(), new IntegerType());
			
		}
		if(params.getTipoPagoCAEV()!=null && params.getTipoPagoCAEV() > 0){
			query.setEnunciado(" AND cal.tipoPagoCAEV.id = ? ");
			query.setParamSql(params.getTipoPagoCAEV(),new IntegerType());			
		}
		if(params.getEstadoMensajeria()!=null && params.getEstadoMensajeria() > 0){
			query.setEnunciado(" AND cal.control.idControl = ? ");
			query.setParamSql(params.getEstadoMensajeria(),new IntegerType());			
		}
	}

	/*===========================================
	 * Metodos ORDER BY
	 *============================================*/
	private void orderHojaTrabajo(CalendarioInternacionalQuery query){
		query.setEnunciado(" ORDER BY cal.prioridad desc, cal.fechaPago desc, cal.estado.id desc, cal.fechaCorte desc, cal.idCalendario desc ");
	}	
	private void orderCalendarioIndeval(CalendarioInternacionalQuery query){
		query.setEnunciado(" ORDER BY cal.prioridad desc, cal.fechaCorte desc, cal.estado.id desc, cal.idCalendario desc ");
	}	
	private void orderParticipantes(CalendarioInternacionalQuery query){
		query.setEnunciado(" ORDER BY cal.fechaCorte desc, cal.fechaPago desc, cal.idCalendario desc ");
	}	
	
	/*==============================================
	 * Utilidades
	 *==============================================*/
	
	private Date now(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	private Date daysFromNow(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
}
