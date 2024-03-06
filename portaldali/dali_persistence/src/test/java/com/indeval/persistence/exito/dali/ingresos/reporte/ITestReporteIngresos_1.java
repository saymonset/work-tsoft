/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos.reporte;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.indeval.persistence.unittest.BaseDaoTestCase;

/**
 * Rafael Ibarra
 */
public class ITestReporteIngresos_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestReporteIngresos_1.class);
    
    private JdbcTemplate jdbcTemplate;
    
    private static String tab = "\t";
    
    private static String linea = "\n";
    
    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmmss");
    
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        jdbcTemplate = (JdbcTemplate) getBean("jdbcTemplate");
    }
	
	
	public void testGetReporte() throws Exception {
		log.info("Inicio: [" + new Date() + "]");
		assertNotNull("JDBC Template nulo", jdbcTemplate);
		
		Date fechaInicio = sdf.parse("28122009 000001");
		Date fechaFinal = sdf.parse("31122009 235959");
		
		String queryTotales = "select i.id_institucion id, ti.clave_tipo_institucion || i.folio_institucion as idFolio ," +
			"i.nombre_corto nombre, co.id_consulta, co.nombre consulta, "  + 
			"count(i.nombre_corto) as total_consultas, sum(l.numero_registros) as total_registros "  + 
			"from t_log_consultas_ingresos l "  + 
			"inner join c_consultas_ingresos co on l.id_consulta = co.id_consulta "  + 
			"inner join c_institucion i on l.id_institucion = i.id_institucion "  + 
			"inner join c_tipo_institucion ti on i.id_tipo_institucion = ti.id_tipo_institucion "  + 
			"where l.fecha_hora_registro BETWEEN TO_DATE('" + sdf.format(fechaInicio) + "','DDMMYYYY HH24MISS') " +
					"AND TO_DATE('" + sdf.format(fechaFinal) + "','DDMMYYYY HH24MISS') "  + 
				"and l.id_tipo_consulta = 1 "  +
			"group by i.id_institucion, ti.clave_tipo_institucion || i.folio_institucion, i.nombre_corto, co.id_consulta, co.nombre "  + 
			"order by i.nombre_corto, total_consultas desc " ;
		
		log.info("Query: [" + queryTotales + "]");
		List<LinkedHashMap> listTotales = jdbcTemplate.queryForList(queryTotales);
		
		assertFalse("JDBC Template nulo", listTotales == null || listTotales.isEmpty());
		
		log.info("Tamaño: [" + listTotales.size() + "]");
		
		
		
		calcularRegistrosGratis(listTotales,fechaInicio,fechaFinal);
		
		FileOutputStream fos = new FileOutputStream("o:\\RepDic28-31.txt");
		
		// Encabezados
		LinkedHashMap elemento = listTotales.get(0);
		Set<String> llaves = elemento.keySet();
		for( String llave : llaves ) {
			fos.write(llave.getBytes());
			fos.write(tab.getBytes());
		}
//		fos.write("Registro Gratis".getBytes());
		fos.write(linea.getBytes());
		
		// Datos
		for(LinkedHashMap item : listTotales) {
			for( String llave : llaves ) {
				fos.write(String.valueOf(item.get(llave)).getBytes());
				fos.write(tab.getBytes());
//				log.info("llave: [" + llave + "]");
			}
//			fos.write(String.valueOf(item.get("gratis")).getBytes());
			fos.write(linea.getBytes());
		}
		
		fos.close();
		log.info("Fin: [" + new Date() + "]");
	}


	private void calcularRegistrosGratis(List<LinkedHashMap> listTotales,
			Date fechaInicio, Date fechaFinal) {
		Date fechaActual = fechaInicio;
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMyyyy HHmmss");
		String queryGratis = "select SUM(numero) from ( " +
				"select l.numero_registros numero " +
				"from t_log_consultas_ingresos l " +
				"where l.fecha_hora_registro BETWEEN TO_DATE(?,'DDMMYYYY HH24MISS') AND TO_DATE(?,'DDMMYYYY HH24MISS') " +
				"  and l.id_tipo_consulta = 1 and l.id_consulta = ? and l.id_institucion = ? " +
				"ORDER BY numero_registros DESC) " +
				"where rownum < 3";
		
		Object params[] = new Object[4];
		while( fechaActual.before(fechaFinal) ) {
			log.info("Fecha a calcular: [" + sdf2.format(fechaActual) + "]");
			log.info("Inicio FECHA: [" + new Date() + "]");
			
			for(LinkedHashMap item : listTotales) {
				Long idConsulta = ((BigDecimal)item.get("ID_CONSULTA")).longValue();
				Long idInstitucion = ((BigDecimal)item.get("ID")).longValue();
				params[0] = sdf3.format(horaInicioFin(fechaActual, true));
				params[1] = sdf3.format(horaInicioFin(fechaActual, false));
				params[2] = idConsulta;
				params[3] = idInstitucion;
				
//				log.info("Ejecutar: [" + idConsulta + "-" + idInstitucion + "]");
				
				Long registrosGratisLong = jdbcTemplate.queryForLong(queryGratis, params);
				
				if( item.get("gratis") == null ) {
					item.put("gratis",new Long(0));
				}
				
				registrosGratisLong = registrosGratisLong == null ? 0 : registrosGratisLong; 
				
				item.put("gratis", ((Long)item.get("gratis")) + registrosGratisLong );
			}
			log.info("FIN FECHA: [" + new Date() + "]");
			fechaActual = DateUtils.add(fechaActual, Calendar.DAY_OF_MONTH, 1);
		}
	}

	private Date horaInicioFin(Date hora, boolean inicio) {
		if (hora == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(hora);

		if (inicio) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}

		return cal.getTime();
	}

//	private void calcularRegistrosGratis(List<LinkedHashMap> listTotales,
//			Date fechaInicio, Date fechaFinal) {
//		Date fechaActual = fechaInicio;
//		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMyyyy");
//		String queryGratis = "select sum(l.numero_registros) "  +
//		"from t_log_consultas_ingresos l "  +
//		"where trunc(l.fecha_hora_registro) = trunc(TO_DATE(?,'DDMMYYYY')) "  +
//		"and l.id_tipo_consulta = 1 and l.id_consulta = ? and l.id_institucion = ? and l.fecha_hora_registro in ( "  +
//		"select MIN(l.fecha_hora_registro) "  +
//		"from t_log_consultas_ingresos l "  +
//		"where trunc(l.fecha_hora_registro) = trunc(TO_DATE(?,'DDMMYYYY')) "  +
//		"and l.id_tipo_consulta = 1 and l.id_consulta = ? and l.id_institucion = ? "  +
//		"UNION "  +
//		"select MAX(l.fecha_hora_registro) "  +
//		"from t_log_consultas_ingresos l "  +
//		"where trunc(l.fecha_hora_registro) = trunc(TO_DATE(?,'DDMMYYYY')) "  +
//		"and l.id_tipo_consulta = 1 and l.id_consulta = ? and l.id_institucion = ?) ";
//
//		Object params[] = new Object[9];
//		while( fechaActual.before(fechaFinal) ) {
//			log.info("Fecha a calcular: [" + sdf2.format(fechaActual) + "]");
//			log.info("Inicio FECHA: [" + new Date() + "]");
//
//			for(LinkedHashMap item : listTotales) {
//				Long idConsulta = ((BigDecimal)item.get("ID_CONSULTA")).longValue();
//				Long idInstitucion = ((BigDecimal)item.get("ID")).longValue();
//				params[0] = sdf3.format(fechaActual);
//				params[1] = idConsulta;
//				params[2] = idInstitucion;
//				params[3] = sdf3.format(fechaActual);
//				params[4] = idConsulta;
//				params[5] = idInstitucion;
//				params[6] = sdf3.format(fechaActual);
//				params[7] = idConsulta;
//				params[8] = idInstitucion;
//
////				log.info("Ejecutar: [" + idConsulta + "-" + idInstitucion + "]");
//
//				Long registrosGratisLong = jdbcTemplate.queryForLong(queryGratis, params);
//
//				if( item.get("gratis") == null ) {
//					item.put("gratis",new Long(0));
//				}
//
//				registrosGratisLong = registrosGratisLong == null ? 0 : registrosGratisLong;
//
//				item.put("gratis", ((Long)item.get("gratis")) + registrosGratisLong );
//			}
//			log.info("FIN FECHA: [" + new Date() + "]");
//			fechaActual = DateUtils.add(fechaActual, Calendar.DAY_OF_MONTH, 1);
//		}
//	}
	
	

}
