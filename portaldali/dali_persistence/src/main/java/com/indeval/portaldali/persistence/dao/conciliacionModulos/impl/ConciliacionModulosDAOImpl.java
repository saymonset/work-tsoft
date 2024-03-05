package com.indeval.portaldali.persistence.dao.conciliacionModulos.impl;

import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CANTIDAD_TITULOS;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CUENTA_INSTITUCION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CUENTA_BANCO_TRABAJO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CUENTA_RECEPTORA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CUENTA_TRASPASANTE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.CUPON;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.DIVISA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.EMISORA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.ESTADO_OPERACION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.ESTADO_OPERACION_LIQ;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FECHA_LIQUIDACION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FECHA_REGISTRO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_CONTROL;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_BANCO_TRABAJO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_INSTITUCION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_INSTRUCCION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_PRELIQUIDADOR;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_RECEPTORA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_TRASPASANTE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_USUARIO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_INSTRUCCION_LIQUIDACION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.ID_INSTRUCCION_EFECTIVO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.ID_MODULO_ORIGEN;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.ID_BITACORA_ADAPTADOR_ENTRADA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.IMPORTE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.MONTO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.NOMBRE_BANCO_TRABAJO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.NOMBRE_INSTITUCION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.NOMBRE_RECEPTORA;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.NOMBRE_TRASPASANTE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.PARTICIPANTE_SIN_NOTIFICAR;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.PRECIO_TITULO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.REF_OPERACION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.SERIE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TIPO_DERECHO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TIPO_INSTRUCCION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TIPO_MENSAJE;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TIPO_MOVIMIENTO;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TIPO_OPERACION;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.TV;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.FOLIO_TENEDOR;
import static com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum.NOMBRE_TENEDOR;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriteriosConciliacionModulosDTO;
import com.indeval.portaldali.persistence.dao.conciliacionModulos.ConciliacionModulosDAO;

public class ConciliacionModulosDAOImpl extends BaseDaoHibernateImpl implements ConciliacionModulosDAO {
	
	private static final String MARCA_CRITERIOS = "@CRITERIOS"; 

	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMatch(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  BA.ID_BITACORA_ADAPTADOR						             AS \"").append(ID_BITACORA_ADAPTADOR_ENTRADA.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        T.NOMBRE_CORTO                                              AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        CT.CUENTA                                                   AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        R.NOMBRE_CORTO                                              AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        CR.CUENTA                                                   AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.TIPO_OPERACION                                           AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.TIPO_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        BA.REFERENCIA_OPERACION                                     AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.FECHA_RECEPCION                                          AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        BA.TV                                                       AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.EMISORA                                                  AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.SERIE                                                    AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.CUPON                                                    AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        BA.NUMERO_TITULOS                                           AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        BA.IMPORTE                                                  AS \"").append(MONTO.getPropiedadDto()).append("\", ");  		
		consulta.append("        BA.DIVISA                                                   AS \"").append(DIVISA.getPropiedadDto()).append("\" ");  
		consulta.append("FROM T_BITACORA_ADAPTADOR BA, C_CUENTA_NOMBRADA CT, C_CUENTA_NOMBRADA CR, C_INSTITUCION T, C_INSTITUCION R "); 
		consulta.append("WHERE   BA.ID_CUENTA_TRASPASANTE = CT.ID_CUENTA_NOMBRADA(+) "); 
		consulta.append("    AND CT.ID_INSTITUCION = T.ID_INSTITUCION(+) "); 
		consulta.append("    AND BA.ID_CUENTA_RECEPTOR = CR.ID_CUENTA_NOMBRADA(+) "); 
		consulta.append("    AND CR.ID_INSTITUCION = R.ID_INSTITUCION(+) "); 
		consulta.append("    AND BA.TIPO_MENSAJE IN ('540', '541', '542', '543') ");
		consulta.append("    AND BA.TIPO_OPERACION NOT IN ('Retiro', 'Deposito') ");
		consulta.append("    AND BA.XML NOT LIKE '%<cuentaBenefFinal>%' ");
		consulta.append("    AND BA.FECHA_RECEPCION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND BA.FECHA_RECEPCION < TRUNC(CURRENT_DATE+1) ");
		consulta.append("    AND NOT EXISTS (SELECT * FROM T_REGISTRO_INSTRUCCIONES_MATCH M WHERE M.FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("                    AND (BA.REFERENCIA_OPERACION = M.FOLIO_INSTRUCCION OR BA.REFERENCIA_OPERACION = M.FOLIO_MATCH) ");
		consulta.append("                    AND CT.CUENTA = M.CUENTA_TRASPASANTE AND CR.CUENTA = M.CUENTA_RECEPTOR) ");
		consulta.append("    AND NOT EXISTS (SELECT * FROM T_REGISTRO_INSTRUCCIONES_TLP T WHERE T.FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("                    AND BA.REFERENCIA_OPERACION = T.FOLIO_INSTRUCCION ");
		consulta.append("                    AND CT.CUENTA = T.CUENTA_TRASPASANTE AND CR.CUENTA = T.CUENTA_RECEPTOR) ");
		consulta.append("    AND NOT EXISTS (SELECT * FROM T_REGISTRO_INVALIDAS I WHERE I.FECHA_HORA_REGISTRO >= TRUNC(CURRENT_DATE) ");
		consulta.append("                    AND BA.REFERENCIA_OPERACION = I.FOLIO_INSTRUCCION ");
		consulta.append("                    AND CT.CUENTA = I.CUENTA_TRASPASANTE AND CR.CUENTA = I.CUENTA_RECEPTOR) ");
        consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY BA.FECHA_RECEPCION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.AE_MATCH, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMos(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  BA.ID_BITACORA_ADAPTADOR						             AS \"").append(ID_BITACORA_ADAPTADOR_ENTRADA.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        T.NOMBRE_CORTO                                              AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        R.NOMBRE_CORTO                                              AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.TIPO_OPERACION                                           AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.TIPO_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        BA.REFERENCIA_OPERACION                                     AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.FECHA_RECEPCION                                          AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        BA.IMPORTE                                                  AS \"").append(MONTO.getPropiedadDto()).append("\", ");  		
		consulta.append("        BA.DIVISA                                                   AS \"").append(DIVISA.getPropiedadDto()).append("\" ");  
		consulta.append("FROM T_BITACORA_ADAPTADOR BA, C_CUENTA_NOMBRADA CT, C_CUENTA_NOMBRADA CR, C_INSTITUCION T, C_INSTITUCION R "); 
		consulta.append("WHERE   BA.ID_CUENTA_TRASPASANTE = CT.ID_CUENTA_NOMBRADA(+) "); 
		consulta.append("    AND CT.ID_INSTITUCION = T.ID_INSTITUCION(+) "); 
		consulta.append("    AND BA.ID_CUENTA_RECEPTOR = CR.ID_CUENTA_NOMBRADA(+) "); 
		consulta.append("    AND CR.ID_INSTITUCION = R.ID_INSTITUCION(+) "); 
		consulta.append("    AND BA.TIPO_MENSAJE IN ('103', '202') "); 
		consulta.append("    AND BA.FECHA_RECEPCION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND BA.FECHA_RECEPCION < TRUNC(CURRENT_DATE+1) "); 
		consulta.append("    AND (BA.REFERENCIA_OPERACION, BA.ID_CUENTA_TRASPASANTE, BA.ID_CUENTA_RECEPTOR) NOT IN ");
		consulta.append("    (SELECT REFERENCIA_OPERACION, ID_CUENTA_TRASPASANTE, ID_CUENTA_RECEPTORA FROM T_INSTRUCCION_EFECTIVO "); 
		consulta.append("     WHERE FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) AND (ID_TIPO_INSTRUCCION IN ( 30, 33 ) OR ID_ESTADO_INSTRUCCION = 10)) "); 
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY BA.FECHA_RECEPCION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.AE_MOS, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMav(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  BA.ID_BITACORA_ADAPTADOR						             	 AS \"").append(ID_BITACORA_ADAPTADOR_ENTRADA.getPropiedadDto()).append("\", ");
//		consulta.append("        LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION  	 AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", ");
//		consulta.append("        LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION     AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
        consulta.append("        NVL(LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION, ");
        consulta.append("        LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION)     AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", "); 
		consulta.append("        NVL(T.NOMBRE_CORTO, R.NOMBRE_CORTO)                             AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(CT.CUENTA, CR.CUENTA)                                       AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        BA.TIPO_OPERACION                                               AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.TIPO_MENSAJE                                                 AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        BA.REFERENCIA_OPERACION                                         AS \"").append(REF_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        BA.FECHA_RECEPCION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        BA.FECHA_LIQUIDACION                                            AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        BA.TV                                                           AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.EMISORA                                                      AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.SERIE                                                        AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        BA.CUPON                                                        AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        BA.NUMERO_TITULOS                                               AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_BITACORA_ADAPTADOR BA, C_CUENTA_NOMBRADA CT, C_CUENTA_NOMBRADA CR, C_INSTITUCION T, C_INSTITUCION R ");
		consulta.append("WHERE   BA.ID_CUENTA_TRASPASANTE = CT.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND CT.ID_INSTITUCION = T.ID_INSTITUCION(+) ");
		consulta.append("    AND BA.ID_CUENTA_RECEPTOR = CR.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND CR.ID_INSTITUCION = R.ID_INSTITUCION(+) ");
		consulta.append("    AND (BA.TIPO_MENSAJE IN ('500', '564', '566') ");
		consulta.append("        OR BA.TIPO_OPERACION IN ('Retiro', 'Deposito')) ");
		consulta.append("    AND BA.FECHA_RECEPCION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND BA.FECHA_RECEPCION < TRUNC(CURRENT_DATE+1) ");
		consulta.append("    AND BA.REFERENCIA_OPERACION NOT IN (SELECT REFERENCIA_OPERACION FROM T_MENSAJE_XML ");
		consulta.append("                                        WHERE FECHA_RECEPCION >= TRUNC(CURRENT_DATE) AND FECHA_RECEPCION < TRUNC(CURRENT_DATE+1)) ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY BA.FECHA_RECEPCION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.AE_MAV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMatchMov(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  DISTINCT MATCH.ID_BITACORA_MATCH, ");
		consulta.append("        MATCH.ID_FOLIO_TRASPASANTE      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.CUENTA_TRASPASANTE        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.ID_FOLIO_RECEPTOR         AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.CUENTA_RECEPTOR           AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.ESTADO_INSTRUCCION        AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.TIPO_OPERACION            AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.TIPO_MENSAJE              AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        MATCH.FECHA_HORA_RECEPCION      AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.TIPO_VALOR                AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        MATCH.EMISORA                   AS \"").append(EMISORA.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.SERIE                     AS \"").append(SERIE.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.CUPON                     AS \"").append(CUPON.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.CANTIDAD_TITULOS          AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.PRECIO_TITULO             AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MATCH.IMPORTE                   AS \"").append(MONTO.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.DIVISA                    AS \"").append(DIVISA.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.FOLIO_INSTRUCCION         AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", "); 
		consulta.append("        MATCH.FOLIO_CONTROL             AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\" ");		
		consulta.append("FROM T_REGISTRO_INSTRUCCIONES_MATCH MATCH, C_CUPON C, C_EMISION EN, C_EMISORA EA, C_INSTRUMENTO I ");
		consulta.append("WHERE   C.ID_EMISION = EN.ID_EMISION ");
		consulta.append("    AND EN.ID_EMISORA = EA.ID_EMISORA ");
		consulta.append("    AND EN.ID_INSTRUMENTO = I.ID_INSTRUMENTO ");
		consulta.append("    AND MATCH.CUPON = C.CLAVE_CUPON ");
		consulta.append("    AND MATCH.EMISORA = EA.CLAVE_PIZARRA ");
		consulta.append("    AND MATCH.SERIE = EN.SERIE ");
		consulta.append("    AND MATCH.TIPO_VALOR = I.CLAVE_TIPO_VALOR ");
		consulta.append("    AND MATCH.ESTADO_INSTRUCCION = 'CON_MATCH' ");
		consulta.append("    AND MATCH.TIPO_MENSAJE IN ('542', '543') ");
		consulta.append("    AND (EN.FECHA_LIMITE_MOVIMIENTOS IS NULL ");
		consulta.append("         OR EN.FECHA_LIMITE_MOVIMIENTOS >= TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND MATCH.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MATCH.FOLIO_INSTRUCCION NOT IN (SELECT FOLIO_INSTRUCCION_RECEPTORA FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND MATCH.FOLIO_MATCH NOT IN (SELECT FOLIO_INSTRUCCION_RECEPTORA FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND MATCH.FOLIO_INSTRUCCION NOT IN (SELECT FOLIO_INSTRUCCION_TRASPASANTE FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND MATCH.FOLIO_MATCH NOT IN (SELECT FOLIO_INSTRUCCION_TRASPASANTE FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND MATCH.FOLIO_INSTRUCCION NOT IN (SELECT REFERENCIA_OPERACION FROM T_BITACORA_ADAPTADOR_SALIDA WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("                                        AND TIPO_MENSAJE = '548' AND MODULO_ORIGEN = 'MOV' AND ID_SUBTIPO_MENSAJE = 11) ");
		consulta.append("    AND MATCH.FOLIO_MATCH NOT IN (SELECT REFERENCIA_OPERACION FROM T_BITACORA_ADAPTADOR_SALIDA WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("                                  AND TIPO_MENSAJE = '548' AND MODULO_ORIGEN = 'MOV' AND ID_SUBTIPO_MENSAJE = 11) ");
		consulta.append("UNION ");
		consulta.append("SELECT  DISTINCT TLP.ID_BITACORA_MATCH, ");
		consulta.append("        TLP.ID_FOLIO_TRASPASANTE      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.CUENTA_TRASPASANTE        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.ID_FOLIO_RECEPTOR         AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.CUENTA_RECEPTOR           AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.ESTADO_INSTRUCCION        AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.TIPO_OPERACION            AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.TIPO_MENSAJE              AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        TLP.FECHA_HORA_RECEPCION      AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.TIPO_VALOR                AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        TLP.EMISORA                   AS \"").append(EMISORA.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.SERIE                     AS \"").append(SERIE.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.CUPON                     AS \"").append(CUPON.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.CANTIDAD_TITULOS          AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.PRECIO_TITULO             AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        TLP.IMPORTE                   AS \"").append(MONTO.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.DIVISA                    AS \"").append(DIVISA.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.FOLIO_INSTRUCCION         AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", "); 
		consulta.append("        TLP.FOLIO_CONTROL             AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_REGISTRO_INSTRUCCIONES_TLP TLP, C_CUPON C, C_EMISION EN, C_EMISORA EA, C_INSTRUMENTO I ");
		consulta.append("WHERE   C.ID_EMISION = EN.ID_EMISION ");
		consulta.append("    AND EN.ID_EMISORA = EA.ID_EMISORA ");
		consulta.append("    AND EN.ID_INSTRUMENTO = I.ID_INSTRUMENTO ");
		consulta.append("    AND TLP.CUPON = C.CLAVE_CUPON ");
		consulta.append("    AND TLP.EMISORA = EA.CLAVE_PIZARRA ");
		consulta.append("    AND TLP.SERIE = EN.SERIE ");
		consulta.append("    AND TLP.TIPO_VALOR = I.CLAVE_TIPO_VALOR ");
		consulta.append("    AND (EN.FECHA_LIMITE_MOVIMIENTOS IS NULL ");
		consulta.append("         OR EN.FECHA_LIMITE_MOVIMIENTOS >= TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND TLP.ESTADO_INSTRUCCION = 'NO_REQUIERE_MATCH' ");
		consulta.append("    AND TLP.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("	 AND TLP.FOLIO_INSTRUCCION NOT IN (SELECT FOLIO_INSTRUCCION_RECEPTORA FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND TLP.FOLIO_INSTRUCCION NOT IN (SELECT FOLIO_INSTRUCCION_TRASPASANTE FROM T_INSTRUCCION_OPERACION_VAL WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("    AND TLP.FOLIO_INSTRUCCION NOT IN (SELECT REFERENCIA_OPERACION FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("                                      WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) AND TIPO_MENSAJE = '548' ");
		consulta.append("                                      AND MODULO_ORIGEN = 'MOV' AND ID_SUBTIPO_MENSAJE = 11) ");
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\" "); 
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MATCH_MOV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMovSlv(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                       AS \"").append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        C_TRA.CUENTA                                                    AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        C_REC.CUENTA                                                    AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(BT.ID_TIPO_INSTITUCION, 2, '0') || BT.FOLIO_INSTITUCION    AS \"").append(FOLIO_BANCO_TRABAJO.getPropiedadDto()).append("\", ");  
		consulta.append("        BT.NOMBRE_CORTO                                                 AS \"").append(NOMBRE_BANCO_TRABAJO.getPropiedadDto()).append("\", "); 
		consulta.append("        C_BT.CUENTA                                                     AS \"").append(CUENTA_BANCO_TRABAJO.getPropiedadDto()).append("\", "); 
		consulta.append("        T_INS.NOMBRE_CORTO                                              AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                  AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
		consulta.append("        MOV.FECHA_CONCERTACION                                          AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
		consulta.append("        INO.CLAVE_TIPO_VALOR                                            AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                               AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                       AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                 AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.PRECIO_TITULO                                               AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.CANTIDAD_TITULOS                                            AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.IMPORTE                                                     AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_CONTROL                                               AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                               AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\" ");   
		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_INSTITUCION BT, C_CUENTA_NOMBRADA C_TRA, ");  
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_CUENTA_NOMBRADA C_BT, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, C_CUPON CUP, ");  
		consulta.append("     C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV "); 
		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");  
		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");  
		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION(+) ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");  
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");  
		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");  
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");  
		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");  
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");  
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");  
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION NOT IN (2, 10, 13, 18, 20) ");
		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MOV.ID_INSTRUCCION_OPERACION_VAL NOT IN (SELECT FOLIO_INSTRUCCION_LIQUIDACION FROM T_INSTRUCCION_LIQUIDACION "); 
		consulta.append("                                                 WHERE FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE)) ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY MOV.FECHA_CONCERTACION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MOV_SLV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMosSlv(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  DISTINCT MOS.ID_INSTRUCCION_EFECTIVO                       AS \"").append(ID_INSTRUCCION_EFECTIVO.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                              AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                  AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");  
		consulta.append("        T_MEN.CLAVE_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_REGISTRO                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_LIQUIDACION                                           AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FOLIO_INSTRUCCION                                           AS \"").append(FOLIO_INSTRUCCION.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.REFERENCIA_OPERACION                                        AS \"").append(REF_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.MONTO                                                       AS \"").append(IMPORTE.getPropiedadDto()).append("\", ");
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_EFECTIVO MOS, C_INSTITUCION TRA, C_INSTITUCION REC, C_TIPO_INSTRUCCION T_INS, ");  
		consulta.append("     C_ESTADO_INSTRUCCION E_INS, C_TIPO_MENSAJE T_MEN, C_DIVISA DIV ");
		consulta.append("WHERE   MOS.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");  
		consulta.append("    AND MOS.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION(+) ");
		consulta.append("    AND MOS.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");  
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");  
		consulta.append("    AND MOS.ID_TIPO_MENSAJE = T_MEN.ID_TIPO_MENSAJE ");
		consulta.append("    AND MOS.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION NOT IN (13, 18, 20, 2, 10) ");
		consulta.append("    AND ( ");
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (30, 50) ");
		consulta.append("                AND MOS.ID_CUENTA_TRASPASANTE IS NOT NULL ");
		consulta.append("                AND MOS.ID_CUENTA_RECEPTORA IS NOT NULL ");
		consulta.append("            ) ");
		consulta.append("            OR "); 
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (29, 32, 33) "); 
		consulta.append("            ) ");
		consulta.append("        ) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION < TRUNC(CURRENT_DATE + 1) ");
		consulta.append("    AND MOS.FOLIO_INST_LIQUIDACION NOT IN (SELECT FOLIO_INSTRUCCION_LIQUIDACION FROM T_INSTRUCCION_LIQUIDACION ");
		consulta.append("                                           WHERE TRUNC(FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE)) ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY MOS.FECHA_REGISTRO ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MOS_SLV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMavSlv(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        COALESCE(TID.CLAVE_TIPO_INSTITUCION, TIR.CLAVE_TIPO_INSTITUCION, TIDD.CLAVE_TIPO_INSTITUCION, TIDC.CLAVE_TIPO_INSTITUCION) || ");
		consulta.append("        COALESCE(ID.FOLIO_INSTITUCION, IR.FOLIO_INSTITUCION, IDD.FOLIO_INSTITUCION, IDC.FOLIO_INSTITUCION)  AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        COALESCE(ID.NOMBRE_CORTO, IR.NOMBRE_CORTO, IDD.NOMBRE_CORTO, IDC.NOMBRE_CORTO)                      AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(CD.CUENTA, CR.CUENTA)                                                                           AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        DECODE(D.ID_DEPOSITO, NULL, DECODE(R.ID_RETIRO, NULL, 'DERECHO', 'RETIRO'), 'DEPOSITO')             AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", ");
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        CASE ");
		consulta.append("           WHEN D.ID_DEPOSITO IS NOT NULL THEN ");
		consulta.append("               SUM(D.NUMERO_TITULOS) ");
		consulta.append("           WHEN R.ID_RETIRO IS NOT NULL THEN ");
		consulta.append("               SUM(R.NUMERO_TITULOS) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION NOT IN (1, 3, 6, 7, 11, 13, 15, 24) THEN ");
		consulta.append("               SUM(PDDC.NUMERO_TIT_RESULTADO_ORIGEN) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION IN (3, 7, 11, 13, 15) THEN ");
		consulta.append("               SUM(PDDC.NUMERO_TIT_PRODUCTO) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION IN (6) THEN ");
		consulta.append("               SUM(PDC.NUMERO_TIT_DISMINUIR_ORIGEN) ");
		consulta.append("           WHEN PDD.ID_DERECHO_DEUDA IS NOT NULL THEN ");
		consulta.append("               SUM(PDD.NUMERO_TITULOS_AMORTIZAR) + SUM(PDD.NUMERO_TITULOS_AMORTIZAR_ANT) ");
		consulta.append("           ELSE ");
		consulta.append("               0 ");
		consulta.append("        END                                                                                                 AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        CASE ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL THEN ");
		consulta.append("               SUM(PDDC.TOTAL_EFECTIVO_RECIBIR) ");
		consulta.append("           WHEN PDD.ID_DERECHO_DEUDA IS NOT NULL THEN ");
		consulta.append("               SUM(PDDD.TOTAL_EFECTIVO_RECIBIR) + SUM(PDD.TOTAL_AMORTIZACION_TITULOS) + SUM(PDD.TOTAL_AMORTIZACION_TITULOS_ANT) + SUM(PDD.TOTAL_AMORTIZACION_CAPITAL) + SUM(PDD.TOTAL_AMORTIZACION_CAPITAL_ANT) ");
		consulta.append("           ELSE ");
		consulta.append("               0 ");
		consulta.append("        END                                                                                                 AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, ");
		consulta.append("     T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, T_DEPOSITOS D, T_RETIROS R, T_OPERACIONES_DERECHOS OD, ");
		consulta.append("     C_OPERACIONES_LIQUIDACION OL, C_INSTITUCION ID, C_TIPO_INSTITUCION TID, C_CUENTA_NOMBRADA CD, C_INSTITUCION IR, ");
		consulta.append("     C_TIPO_INSTITUCION TIR, C_CUENTA_NOMBRADA CR, C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, ");
		consulta.append("     T_DERECHOS_DEUDA DD, T_PRODUCTOS_DERECHO_CAPITAL PDC, T_PRODUCTOS_DERECHO_DEUDA PDD, ");
		consulta.append("     T_PRODUCTOS_DERECHO PDDC, T_PRODUCTOS_DERECHO PDDD, C_INSTITUCION IDD, C_TIPO_INSTITUCION TIDD, C_INSTITUCION IDC, ");
		consulta.append("     C_TIPO_INSTITUCION TIDC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND D.FOLIO_DEPOSITO(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND R.FOLIO_RETIRO(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND OD.ID_DERECHO_DEUDA = DD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND DC.ID_DERECHO_CAPITAL = PDC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND DD.ID_DERECHO_DEUDA = PDD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND IDD.ID_INSTITUCION(+) = DD.ID_INSTITUCION ");
		consulta.append("    AND TIDD.ID_TIPO_INSTITUCION(+) = IDD.ID_TIPO_INSTITUCION ");
		consulta.append("    AND PDC.ID_PRODUCTO_DERECHO = PDDC.ID_PRODUCTO_DERECHO(+) ");
		consulta.append("    AND PDD.ID_PRODUCTO_DERECHO = PDDD.ID_PRODUCTO_DERECHO(+) ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND ID.ID_INSTITUCION(+) = D.ID_INSTITUCION ");
		consulta.append("    AND CD.ID_CUENTA_NOMBRADA(+) = D.ID_CUENTA ");
		consulta.append("    AND TID.ID_TIPO_INSTITUCION(+) = ID.ID_TIPO_INSTITUCION ");
		consulta.append("    AND IR.ID_INSTITUCION(+) = R.ID_INSTITUCION ");
		consulta.append("    AND CR.ID_CUENTA_NOMBRADA(+) = R.ID_CUENTA ");
		consulta.append("    AND TIR.ID_TIPO_INSTITUCION(+) = IR.ID_TIPO_INSTITUCION ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION(+) NOT IN (8, 9, 20) ");
		consulta.append("    AND TRUNC(IMS.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_LIQUIDACION  NOT IN (SELECT IL.FOLIO_INSTRUCCION_LIQUIDACION FROM T_INSTRUCCION_LIQUIDACION IL ");
		consulta.append("                                                   WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("GROUP BY EFO.DESC_ESTATUS_FOLIO_OPERACION, TID.CLAVE_TIPO_INSTITUCION, TIR.CLAVE_TIPO_INSTITUCION, ID.FOLIO_INSTITUCION, ");
		consulta.append("         IR.FOLIO_INSTITUCION, ID.NOMBRE_CORTO, IR.NOMBRE_CORTO, IMS.FOLIO_PRELIQUIDADOR, TV.CLAVE_TIPO_VALOR, OP.DESC_EMISORA, ");
		consulta.append("         OP.SERIE, OP.CUPON, D.ID_DEPOSITO, R.ID_RETIRO, OL.DESC_OPERACION_LIQUIDACION, PDC.ID_DERECHO_CAPITAL, ");
		consulta.append("         PDD.ID_DERECHO_DEUDA, CD.CUENTA, CR.CUENTA, TIDD.CLAVE_TIPO_INSTITUCION, IDD.FOLIO_INSTITUCION, IDD.NOMBRE_CORTO, ");
		consulta.append("         TIDC.CLAVE_TIPO_INSTITUCION, IDC.FOLIO_INSTITUCION, IDC.NOMBRE_CORTO, OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("UNION ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        TIDC.CLAVE_TIPO_INSTITUCION || IDC.FOLIO_INSTITUCION                                                AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        IDC.NOMBRE_CORTO                                                                                    AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                                                                AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        'DERECHO'                                                                                           AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", ");
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        SUM(AC.TITULOS)                                                                                     AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        0                                                                                                   AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, ");
		consulta.append("     T_OPERACIONES_DERECHOS OD, C_OPERACIONES_LIQUIDACION OL, C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, ");
		consulta.append("     C_INSTITUCION IDC, C_TIPO_INSTITUCION TIDC, T_BLOQUES BL, T_ACCIONES AC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND OP.ID_OPERACION = BL.ID_OPERACION ");
		consulta.append("    AND BL.ID_BLOQUE = AC.ID_BLOQUE ");
		consulta.append("    AND BL.ID_TIPO_BLOQUE = 2 ");
		consulta.append("    AND BL.ID_CLAVE_OPERACION_BLOQUE = 7 ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION IN (8, 9) ");
		consulta.append("    AND TRUNC(IMS.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_LIQUIDACION  NOT IN (SELECT IL.FOLIO_INSTRUCCION_LIQUIDACION FROM T_INSTRUCCION_LIQUIDACION IL ");
		consulta.append("                                                   WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append("GROUP BY EFO.DESC_ESTATUS_FOLIO_OPERACION, TIDC.CLAVE_TIPO_INSTITUCION, IDC.FOLIO_INSTITUCION, IDC.NOMBRE_CORTO, ");
		consulta.append("         IMS.FOLIO_PRELIQUIDADOR, TV.CLAVE_TIPO_VALOR, OP.DESC_EMISORA, OP.SERIE, OP.CUPON, OL.DESC_OPERACION_LIQUIDACION ");
		consulta.append("UNION ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(TIDC.CLAVE_TIPO_INSTITUCION, TIDD.CLAVE_TIPO_INSTITUCION) || ");
		consulta.append("        NVL(IDC.FOLIO_INSTITUCION, IDD.FOLIO_INSTITUCION)                                                   AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(IDC.NOMBRE_CORTO, IDD.NOMBRE_CORTO)                                                             AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                                                                AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        'DERECHO'                                                                                           AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", ");
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        AC.TITULOS                                                                                          AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        0                                                                                                   AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, ");
		consulta.append("     T_OPERACIONES_DERECHOS OD, C_OPERACIONES_LIQUIDACION OL, C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, ");
		consulta.append("     C_INSTITUCION IDC, C_TIPO_INSTITUCION TIDC, T_DERECHOS_DEUDA DD, C_INSTITUCION IDD, C_TIPO_INSTITUCION TIDD, ");
		consulta.append("     T_BLOQUES BL, T_ACCIONES AC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND OD.ID_DERECHO_DEUDA = DD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND IDD.ID_INSTITUCION(+) = DD.ID_INSTITUCION ");
		consulta.append("    AND TIDD.ID_TIPO_INSTITUCION(+) = IDD.ID_TIPO_INSTITUCION ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND OP.ID_OPERACION = BL.ID_OPERACION ");
		consulta.append("    AND BL.ID_BLOQUE = AC.ID_BLOQUE ");
		consulta.append("    AND BL.ID_TIPO_BLOQUE = 2 ");
		consulta.append("    AND BL.ID_CLAVE_OPERACION_BLOQUE = 7 ");
		consulta.append("    AND AC.CLAVE_INSTITUCION_RECEPTORA IS NULL ");
		consulta.append("    AND AC.FOLIO_INSTITUCION_RECEPTORA IS NULL ");
		consulta.append("    AND AC.CUENTA_RECEPTORA IS NULL ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION = 20 ");
		consulta.append("    AND TRUNC(IMS.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MMS.FOLIO_INSTRUCCION_LIQUIDACION  NOT IN (SELECT IL.FOLIO_INSTRUCCION_LIQUIDACION FROM T_INSTRUCCION_LIQUIDACION IL ");
		consulta.append("                                                   WHERE FECHA_LIQUIDACION = TRUNC(CURRENT_DATE)) ");
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\" ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MAV_SLV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMav(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        EIL.CLAVE_ESTADO_INSTRUCCION                                                                        AS \"").append(ESTADO_OPERACION_LIQ.getPropiedadDto()).append("\", "); 
		consulta.append("        COALESCE(TID.CLAVE_TIPO_INSTITUCION, TIR.CLAVE_TIPO_INSTITUCION, TIDD.CLAVE_TIPO_INSTITUCION, TIDC.CLAVE_TIPO_INSTITUCION) || ");
		consulta.append("        COALESCE(ID.FOLIO_INSTITUCION, IR.FOLIO_INSTITUCION, IDD.FOLIO_INSTITUCION, IDC.FOLIO_INSTITUCION)  AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        COALESCE(ID.NOMBRE_CORTO, IR.NOMBRE_CORTO, IDD.NOMBRE_CORTO, IDC.NOMBRE_CORTO)                      AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(CD.CUENTA, CR.CUENTA)                                                                           AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MAX(IL.FECHA_APLICACION)                                                                            AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        DECODE(D.ID_DEPOSITO, NULL, DECODE(R.ID_RETIRO, NULL, 'DERECHO', 'RETIRO'), 'DEPOSITO')             AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", "); 
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        CASE ");
		consulta.append("           WHEN D.ID_DEPOSITO IS NOT NULL THEN ");
		consulta.append("               SUM(D.NUMERO_TITULOS) ");
		consulta.append("           WHEN R.ID_RETIRO IS NOT NULL THEN ");
		consulta.append("               SUM(R.NUMERO_TITULOS) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION NOT IN (1, 3, 6, 7, 11, 13, 15, 24) THEN ");
		consulta.append("               SUM(PDDC.NUMERO_TIT_RESULTADO_ORIGEN) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION IN (3, 7, 11, 13, 15) THEN ");
		consulta.append("               SUM(PDDC.NUMERO_TIT_PRODUCTO) ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL AND OD.ID_OPERACION_LIQUIDACION IN (6) THEN ");
		consulta.append("               SUM(PDC.NUMERO_TIT_DISMINUIR_ORIGEN) ");
		consulta.append("           WHEN PDD.ID_DERECHO_DEUDA IS NOT NULL THEN ");
		consulta.append("               SUM(PDD.NUMERO_TITULOS_AMORTIZAR) + SUM(PDD.NUMERO_TITULOS_AMORTIZAR_ANT) ");
		consulta.append("           ELSE ");
		consulta.append("               0 ");
		consulta.append("       END                                                                                                  AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("       CASE ");
		consulta.append("           WHEN PDC.ID_DERECHO_CAPITAL IS NOT NULL THEN ");
		consulta.append("               SUM(PDDC.TOTAL_EFECTIVO_RECIBIR) ");
		consulta.append("           WHEN PDD.ID_DERECHO_DEUDA IS NOT NULL THEN ");
		consulta.append("               SUM(PDDD.TOTAL_EFECTIVO_RECIBIR) + SUM(PDD.TOTAL_AMORTIZACION_TITULOS) + SUM(PDD.TOTAL_AMORTIZACION_TITULOS_ANT) + SUM(PDD.TOTAL_AMORTIZACION_CAPITAL) + SUM(PDD.TOTAL_AMORTIZACION_CAPITAL_ANT) ");
		consulta.append("           ELSE ");
		consulta.append("               0 ");
		consulta.append("        END                                                                                                 AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, ");
		consulta.append("     T_DEPOSITOS D, T_RETIROS R, T_OPERACIONES_DERECHOS OD, C_OPERACIONES_LIQUIDACION OL, C_INSTITUCION ID, ");
		consulta.append("     C_TIPO_INSTITUCION TID, C_CUENTA_NOMBRADA CD, C_INSTITUCION IR, C_TIPO_INSTITUCION TIR, C_CUENTA_NOMBRADA CR, ");
		consulta.append("     C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, T_DERECHOS_DEUDA DD, T_PRODUCTOS_DERECHO_CAPITAL PDC, ");
		consulta.append("     T_PRODUCTOS_DERECHO_DEUDA PDD, T_PRODUCTOS_DERECHO PDDC, T_PRODUCTOS_DERECHO PDDD, T_INSTRUCCION_LIQUIDACION IL, ");
		consulta.append("     C_ESTADO_INSTRUCCION EIL, C_INSTITUCION IDD, C_TIPO_INSTITUCION TIDD, C_INSTITUCION IDC, C_TIPO_INSTITUCION TIDC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION_LIQUIDACION = MMS.FOLIO_INSTRUCCION_LIQUIDACION ");
		consulta.append("    AND EIL.ID_ESTADO_INSTRUCCION = IL.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND D.FOLIO_DEPOSITO(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND R.FOLIO_RETIRO(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND OD.ID_DERECHO_DEUDA = DD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND DC.ID_DERECHO_CAPITAL = PDC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND DD.ID_DERECHO_DEUDA = PDD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND IDD.ID_INSTITUCION(+) = DD.ID_INSTITUCION ");
		consulta.append("    AND TIDD.ID_TIPO_INSTITUCION(+) = IDD.ID_TIPO_INSTITUCION ");
		consulta.append("    AND PDC.ID_PRODUCTO_DERECHO = PDDC.ID_PRODUCTO_DERECHO(+) ");
		consulta.append("    AND PDD.ID_PRODUCTO_DERECHO = PDDD.ID_PRODUCTO_DERECHO(+) ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND ID.ID_INSTITUCION(+) = D.ID_INSTITUCION ");
		consulta.append("    AND CD.ID_CUENTA_NOMBRADA(+) = D.ID_CUENTA ");
		consulta.append("    AND TID.ID_TIPO_INSTITUCION(+) = ID.ID_TIPO_INSTITUCION ");
		consulta.append("    AND IR.ID_INSTITUCION(+) = R.ID_INSTITUCION ");
		consulta.append("    AND CR.ID_CUENTA_NOMBRADA(+) = R.ID_CUENTA ");
		consulta.append("    AND TIR.ID_TIPO_INSTITUCION(+) = IR.ID_TIPO_INSTITUCION ");
		consulta.append("    AND IL.ID_MODULO_ORIGEN = 3 ");
		consulta.append("    AND IL.ID_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION(+) NOT IN (8, 9, 20) ");
		consulta.append("    AND IL.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND OP.ID_ESTATUS_FOLIO_OPERACION <> 2 ");
		consulta.append("    AND IL.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IMS.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION NOT IN (SELECT DISTINCT FOLIO_INSTRUCCION FROM T_INSTRUCCION_LIQUIDACION WHERE FECHA_LIQUIDACION  = TRUNC(CURRENT_DATE) ");
		consulta.append("                                     AND ID_ESTADO_INSTRUCCION NOT IN (6,9)) ");
		consulta.append("GROUP BY EFO.DESC_ESTATUS_FOLIO_OPERACION, TID.CLAVE_TIPO_INSTITUCION, TIR.CLAVE_TIPO_INSTITUCION, ID.FOLIO_INSTITUCION, IR.FOLIO_INSTITUCION, ");
		consulta.append("         ID.NOMBRE_CORTO, IR.NOMBRE_CORTO, IMS.FOLIO_PRELIQUIDADOR, TV.CLAVE_TIPO_VALOR, OP.DESC_EMISORA, OP.SERIE, OP.CUPON, D.ID_DEPOSITO, ");
		consulta.append("         R.ID_RETIRO, OL.DESC_OPERACION_LIQUIDACION, PDC.ID_DERECHO_CAPITAL, PDD.ID_DERECHO_DEUDA, EIL.CLAVE_ESTADO_INSTRUCCION, CD.CUENTA, ");
		consulta.append("         CR.CUENTA, TIDD.CLAVE_TIPO_INSTITUCION, IDD.FOLIO_INSTITUCION, IDD.NOMBRE_CORTO, TIDC.CLAVE_TIPO_INSTITUCION, IDC.FOLIO_INSTITUCION, ");
		consulta.append("         IDC.NOMBRE_CORTO, OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("UNION ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        EIL.CLAVE_ESTADO_INSTRUCCION                                                                        AS \"").append(ESTADO_OPERACION_LIQ.getPropiedadDto()).append("\", "); 
		consulta.append("        TIDC.CLAVE_TIPO_INSTITUCION || IDC.FOLIO_INSTITUCION                                                AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        IDC.NOMBRE_CORTO                                                                                    AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                                                                AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MAX(IL.FECHA_APLICACION)                                                                            AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        'DERECHO'                                                                                           AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", "); 
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        SUM(AC.TITULOS)                                                                                     AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        0                                                                                                   AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, ");
		consulta.append("     T_OPERACIONES_DERECHOS OD, C_OPERACIONES_LIQUIDACION OL, C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, ");
		consulta.append("     T_INSTRUCCION_LIQUIDACION IL, C_ESTADO_INSTRUCCION EIL, C_INSTITUCION IDC, C_TIPO_INSTITUCION TIDC, ");
		consulta.append("     T_BLOQUES BL, T_ACCIONES AC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION_LIQUIDACION = MMS.FOLIO_INSTRUCCION_LIQUIDACION ");
		consulta.append("    AND EIL.ID_ESTADO_INSTRUCCION = IL.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND OP.ID_OPERACION = BL.ID_OPERACION ");
		consulta.append("    AND BL.ID_BLOQUE = AC.ID_BLOQUE ");
		consulta.append("    AND BL.ID_TIPO_BLOQUE = 2 ");
		consulta.append("    AND BL.ID_CLAVE_OPERACION_BLOQUE = 7 ");
		consulta.append("    AND IL.ID_MODULO_ORIGEN = 3 ");
		consulta.append("    AND IL.ID_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION IN (8, 9) ");
		consulta.append("    AND IL.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND OP.ID_ESTATUS_FOLIO_OPERACION <> 2 ");
		consulta.append("    AND IL.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IMS.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION NOT IN (SELECT DISTINCT FOLIO_INSTRUCCION FROM T_INSTRUCCION_LIQUIDACION WHERE FECHA_LIQUIDACION  = TRUNC(CURRENT_DATE) ");
		consulta.append("                                     AND ID_ESTADO_INSTRUCCION NOT IN (6,9)) ");
		consulta.append("GROUP BY EFO.DESC_ESTATUS_FOLIO_OPERACION, EIL.CLAVE_ESTADO_INSTRUCCION, TIDC.CLAVE_TIPO_INSTITUCION, IDC.FOLIO_INSTITUCION, ");
		consulta.append("         IDC.NOMBRE_CORTO, IMS.FOLIO_PRELIQUIDADOR, TV.CLAVE_TIPO_VALOR, OP.DESC_EMISORA, OP.SERIE, OP.CUPON, OL.DESC_OPERACION_LIQUIDACION ");
		consulta.append("UNION ");
		consulta.append("SELECT  EFO.DESC_ESTATUS_FOLIO_OPERACION                                                                    AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        EIL.CLAVE_ESTADO_INSTRUCCION                                                                        AS \"").append(ESTADO_OPERACION_LIQ.getPropiedadDto()).append("\", "); 
		consulta.append("        NVL(TIDC.CLAVE_TIPO_INSTITUCION, TIDD.CLAVE_TIPO_INSTITUCION) || ");
		consulta.append("        NVL(IDC.FOLIO_INSTITUCION, IDD.FOLIO_INSTITUCION)                                                   AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(IDC.NOMBRE_CORTO, IDD.NOMBRE_CORTO)                                                             AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                                                                AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");;
		consulta.append("        IMS.FOLIO_PRELIQUIDADOR                                                                             AS \"").append(FOLIO_PRELIQUIDADOR.getPropiedadDto()).append("\", ");
		consulta.append("        TV.CLAVE_TIPO_VALOR                                                                                 AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        OP.DESC_EMISORA                                                                                     AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        OP.SERIE                                                                                            AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(OP.CUPON, 4, '0')                                                                              AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MAX(IL.FECHA_APLICACION)                                                                            AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        'DERECHO'                                                                                           AS \"").append(TIPO_MOVIMIENTO.getPropiedadDto()).append("\", "); 
		consulta.append("        OL.DESC_OPERACION_LIQUIDACION                                                                       AS \"").append(TIPO_DERECHO.getPropiedadDto()).append("\", ");
		consulta.append("        AC.TITULOS                                                                                          AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        0                                                                                                   AS \"").append(MONTO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCIONES_MAV_SLV IMS, T_MENSAJES_MAV_SLV MMS, T_OPERACIONES OP, T_FOLIOS_PRELIQUIDADOR FP, C_INSTRUMENTO TV, ");
		consulta.append("     T_OPERACIONES_DERECHOS OD, C_OPERACIONES_LIQUIDACION OL, C_ESTATUS_FOLIO_OPERACION EFO, T_DERECHOS_CAPITAL DC, ");
		consulta.append("     T_DERECHOS_DEUDA DD, T_INSTRUCCION_LIQUIDACION IL, C_ESTADO_INSTRUCCION EIL, C_INSTITUCION IDC, C_TIPO_INSTITUCION TIDC, ");
		consulta.append("     C_INSTITUCION IDD, C_TIPO_INSTITUCION TIDD, T_BLOQUES BL, T_ACCIONES AC ");
		consulta.append("WHERE   MMS.ID_INSTRUCCION_MAV_SLV = IMS.ID_INSTRUCCION_MAV_SLV ");
		consulta.append("    AND OP.ID_OPERACION = IMS.ID_OPERACION ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION_LIQUIDACION = MMS.FOLIO_INSTRUCCION_LIQUIDACION ");
		consulta.append("    AND EIL.ID_ESTADO_INSTRUCCION = IL.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND FP.ID_FOLIO_PRELIQUIDADOR = OP.ID_FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND TV.ID_INSTRUMENTO = OP.ID_INSTRUMENTO ");
		consulta.append("    AND FP.ID_ESTATUS_FOLIO_OPERACION = EFO.ID_ESTATUS_FOLIO_OPERACION ");
		consulta.append("    AND OD.FOLIO_OPERACION(+) = FP.FOLIO_PRELIQUIDADOR ");
		consulta.append("    AND OD.ID_DERECHO_CAPITAL = DC.ID_DERECHO_CAPITAL(+) ");
		consulta.append("    AND OD.ID_DERECHO_DEUDA = DD.ID_DERECHO_DEUDA(+) ");
		consulta.append("    AND IDC.ID_INSTITUCION(+) = DC.ID_INSTITUCION ");
		consulta.append("    AND TIDC.ID_TIPO_INSTITUCION(+) = IDC.ID_TIPO_INSTITUCION ");
		consulta.append("    AND IDD.ID_INSTITUCION(+) = DD.ID_INSTITUCION ");
		consulta.append("    AND TIDD.ID_TIPO_INSTITUCION(+) = IDD.ID_TIPO_INSTITUCION ");
		consulta.append("    AND OL.ID_OPERACION_LIQUIDACION(+) = OD.ID_OPERACION_LIQUIDACION ");
		consulta.append("    AND OP.ID_OPERACION = BL.ID_OPERACION ");
		consulta.append("    AND BL.ID_BLOQUE = AC.ID_BLOQUE ");
		consulta.append("    AND BL.ID_TIPO_BLOQUE = 2 ");
		consulta.append("    AND BL.ID_CLAVE_OPERACION_BLOQUE = 7 ");
		consulta.append("    AND AC.CLAVE_INSTITUCION_RECEPTORA IS NULL ");
		consulta.append("    AND AC.FOLIO_INSTITUCION_RECEPTORA IS NULL ");
		consulta.append("    AND AC.CUENTA_RECEPTORA IS NULL ");
		consulta.append("    AND IL.ID_MODULO_ORIGEN = 3 ");
		consulta.append("    AND IL.ID_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND OD.ID_OPERACION_LIQUIDACION = 20 ");
		consulta.append("    AND IL.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND OP.ID_ESTATUS_FOLIO_OPERACION <> 2 ");
		consulta.append("    AND IL.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IMS.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION NOT IN (SELECT DISTINCT FOLIO_INSTRUCCION FROM T_INSTRUCCION_LIQUIDACION WHERE FECHA_LIQUIDACION  = TRUNC(CURRENT_DATE) ");
		consulta.append("                                     AND ID_ESTADO_INSTRUCCION NOT IN (6,9)) ");
		consulta.append("GROUP BY EFO.DESC_ESTATUS_FOLIO_OPERACION, EIL.CLAVE_ESTADO_INSTRUCCION, TIDC.CLAVE_TIPO_INSTITUCION, IDC.FOLIO_INSTITUCION, ");
		consulta.append("         IDC.NOMBRE_CORTO, IMS.FOLIO_PRELIQUIDADOR, TV.CLAVE_TIPO_VALOR, OP.DESC_EMISORA, OP.SERIE, OP.CUPON, ");
		consulta.append("         TIDD.CLAVE_TIPO_INSTITUCION, IDD.FOLIO_INSTITUCION, IDD.NOMBRE_CORTO, OL.DESC_OPERACION_LIQUIDACION, AC.TITULOS ");
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\" ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.SLV_MAV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMov(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		
		consulta.append("SELECT  IL.FOLIO_INSTRUCCION_LIQUIDACION 								 AS \"").append(FOLIO_INSTRUCCION_LIQUIDACION.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        C_TRA.CUENTA                                                    AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        C_REC.CUENTA                                                    AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        TI.NOMBRE_CORTO                                                 AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");
		consulta.append("        EI.CLAVE_ESTADO_INSTRUCCION                                     AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");
		consulta.append("        EIL.CLAVE_ESTADO_INSTRUCCION                                    AS \"").append(ESTADO_OPERACION_LIQ.getPropiedadDto()).append("\", ");
		consulta.append("        IL.FECHA_APLICACION                                             AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        INO.CLAVE_TIPO_VALOR                                            AS \"").append(TV.getPropiedadDto()).append("\", ");
		consulta.append("        EMA.CLAVE_PIZARRA                                               AS \"").append(EMISORA.getPropiedadDto()).append("\", ");
		consulta.append("        EMN.SERIE                                                       AS \"").append(SERIE.getPropiedadDto()).append("\", ");
		consulta.append("        CUP.CLAVE_CUPON                                                 AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        IOV.PRECIO_TITULO                                               AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        IOV.CANTIDAD_TITULOS                                            AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\", ");
		consulta.append("        IOV.IMPORTE                                                     AS \"").append(MONTO.getPropiedadDto()).append("\", ");
		consulta.append("        IOV.FOLIO_CONTROL                                               AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");
		consulta.append("        IOV.FOLIO_INSTRUCCION_TRASPASANTE                               AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_LIQUIDACION IL, C_TIPO_INSTRUCCION TI, C_ESTADO_INSTRUCCION EIL, C_ESTADO_INSTRUCCION EI, ");
		consulta.append("     T_INSTRUCCION_OPERACION_VAL IOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, ");
		consulta.append("     C_DIVISA DIV ");
		consulta.append("WHERE   IOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND IOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND IOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND IOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND TI.ID_TIPO_INSTRUCCION = IL.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND EIL.ID_ESTADO_INSTRUCCION = IL.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND EI.ID_ESTADO_INSTRUCCION = IOV.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION_LIQUIDACION = IOV.ID_INSTRUCCION_OPERACION_VAL ");
		consulta.append("    AND IOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND IOV.ID_CUPON = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
		consulta.append("    AND ID_MODULO_ORIGEN  = 1 ");
		consulta.append("    AND IL.ID_INSTRUCCION_ORIGEN IS NULL ");
		consulta.append("    AND IL.ID_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND IL.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND IOV.ID_ESTADO_INSTRUCCION NOT IN (6,9) ");
		consulta.append("    AND TRUNC(IL.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND IOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY IL.FECHA_APLICACION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.SLV_MOV, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMos(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT  IL.FOLIO_INSTRUCCION_LIQUIDACION 								 AS \"").append(FOLIO_INSTRUCCION_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");    
		consulta.append("        TI.NOMBRE_CORTO                                                 AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");    
		consulta.append("        EI.CLAVE_ESTADO_INSTRUCCION                                     AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");  
		consulta.append("        EIL.CLAVE_ESTADO_INSTRUCCION                                    AS \"").append(ESTADO_OPERACION_LIQ.getPropiedadDto()).append("\", ");   
		consulta.append("        T_MEN.CLAVE_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");  
		consulta.append("        IL.FECHA_APLICACION                                             AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");  
		consulta.append("        IL.FOLIO_INSTRUCCION                                            AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");    
		consulta.append("        IE.MONTO                                                        AS \"").append(IMPORTE.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\" ");  
		consulta.append("FROM T_INSTRUCCION_LIQUIDACION IL, C_TIPO_INSTRUCCION TI, C_ESTADO_INSTRUCCION EIL, C_ESTADO_INSTRUCCION EI, ");
		consulta.append("     T_INSTRUCCION_EFECTIVO IE, C_INSTITUCION TRA, C_INSTITUCION REC, C_TIPO_MENSAJE T_MEN, C_DIVISA DIV ");
		consulta.append("WHERE   TI.ID_TIPO_INSTRUCCION = IL.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND EIL.ID_ESTADO_INSTRUCCION = IL.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND IE.ID_ESTADO_INSTRUCCION = EI.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND IE.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND IE.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION(+) ");
		consulta.append("    AND IE.ID_TIPO_MENSAJE = T_MEN.ID_TIPO_MENSAJE ");
		consulta.append("    AND IE.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND IL.FOLIO_INSTRUCCION_LIQUIDACION = IE.FOLIO_INST_LIQUIDACION ");
		consulta.append("    AND IL.ID_MODULO_ORIGEN = 2 ");
		consulta.append("    AND IL.ID_INSTRUCCION_ANTERIOR IS NULL ");
		consulta.append("    AND IL.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND IE.ID_ESTADO_INSTRUCCION NOT IN (6,9) ");
		consulta.append("    AND TRUNC(IL.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND TRUNC(IE.FECHA_LIQUIDACION) = TRUNC(CURRENT_DATE) ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY IL.FECHA_APLICACION ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.SLV_MOS, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMovAs(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        C_TRA.CUENTA                                                        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        NVL(LPAD(BT.ID_TIPO_INSTITUCION, 2, '0') || BT.FOLIO_INSTITUCION, ");  
		consulta.append("            LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION) AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        NVL(BT.NOMBRE_CORTO, REC.NOMBRE_CORTO)                              AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        NVL(C_BT.CUENTA, C_REC.CUENTA)                                      AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
		consulta.append("        'TRASPASANTE'                                                       AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION(+) ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION NOT IN (54, 55) ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6, 9) ");
		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND (MOV.FOLIO_INSTRUCCION_TRASPASANTE, MOV.ID_CUENTA_NOMBRADA_TRASPASANTE, NVL(MOV.ID_CUENTA_BANCO_TRABAJO, MOV.ID_CUENTA_NOMBRADA_RECEPTORA), MOV.REFERENCIA_TRASPASANTE) NOT IN ");
		consulta.append("        (SELECT REFERENCIA_OPERACION, ID_CUENTA_TRASPASANTE, ID_CUENTA_RECEPTOR, FOLIO_PARTICIPANTE FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("         WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) AND FECHA_CREACION < TRUNC(CURRENT_DATE+1) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
		consulta.append("         AND MODULO_ORIGEN='MOV') ");
		consulta.append("UNION ");
//		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
//        consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
//		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
//		consulta.append("        TRA.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
//		consulta.append("        C_TRA.CUENTA                                                        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");   
//		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION      AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        REC.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        C_REC.CUENTA                                                        AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
//		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
//		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
//		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
//		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
//		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
//		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
//		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
//		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
//		consulta.append("        'TRASPASANTE'                                                       AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
//		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
//		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
//		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
//		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
//		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
//		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
//		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION(+) ");
//		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
//		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
//		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA(+) ");
//		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
//		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
//		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
//		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
//		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
//		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
//		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
//		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6, 9) ");
//		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
//		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = 55 ");
//		consulta.append("    AND NOT EXISTS ");
//		consulta.append("        (SELECT * FROM T_BITACORA_ADAPTADOR_SALIDA S ");
//		consulta.append("         WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) AND FECHA_CREACION < TRUNC(CURRENT_DATE+1) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
//		consulta.append("         AND MODULO_ORIGEN='MOV' AND TIPO_OPERACION = 'EVEX' AND S.ID_CUENTA_TRASPASANTE = MOV.ID_CUENTA_NOMBRADA_TRASPASANTE ");
//		consulta.append("         AND (S.REFERENCIA_OPERACION = MOV.FOLIO_INSTRUCCION_TRASPASANTE OR TO_CHAR(FOLIO_PARTICIPANTE) = MOV.FOLIO_INSTRUCCION_TRASPASANTE)) ");
//		consulta.append("UNION ");
		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
        consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
		consulta.append("        NVL(LPAD(BT.ID_TIPO_INSTITUCION, 2, '0') || BT.FOLIO_INSTITUCION, ");
		consulta.append("            LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION) AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        NVL(BT.NOMBRE_CORTO, TRA.NOMBRE_CORTO)                              AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        NVL(C_BT.CUENTA, C_TRA.CUENTA)                                      AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION      AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        REC.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        C_REC.CUENTA                                                        AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
		consulta.append("        'RECEPTOR'                                                          AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION(+) ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION NOT IN (54, 55) ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND (MOV.FOLIO_INSTRUCCION_RECEPTORA, NVL(MOV.ID_CUENTA_BANCO_TRABAJO, MOV.ID_CUENTA_NOMBRADA_TRASPASANTE), MOV.ID_CUENTA_NOMBRADA_RECEPTORA, MOV.REFERENCIA_RECEPTOR) NOT IN ");
		consulta.append("        (SELECT REFERENCIA_OPERACION, ID_CUENTA_TRASPASANTE, ID_CUENTA_RECEPTOR, FOLIO_PARTICIPANTE FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("         WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) AND FECHA_CREACION < TRUNC(CURRENT_DATE+1) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
		consulta.append("         AND MODULO_ORIGEN='MOV') ");
		consulta.append("UNION ");
//		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
//        consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
//		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
//		consulta.append("        TRA.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
//		consulta.append("        C_TRA.CUENTA                                                        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");   
//		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION      AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        REC.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        C_REC.CUENTA                                                        AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
//		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
//		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
//		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
//		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
//		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
//		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
//		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
//		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
//		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
//		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
//		consulta.append("        'RECEPTOR'                                                          AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
//		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
//		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
//		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
//		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
//		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
//		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
//		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION(+) ");
//		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
//		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
//		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA(+) ");
//		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
//		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
//		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
//		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
//		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
//		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
//		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
//		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = 54 ");
//		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6,9) ");
//		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
//		consulta.append("    AND NOT EXISTS ");
//		consulta.append("        (SELECT * FROM T_BITACORA_ADAPTADOR_SALIDA S ");
//		consulta.append("         WHERE FECHA_CREACION >= TRUNC(CURRENT_DATE) AND FECHA_CREACION < TRUNC(CURRENT_DATE+1) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
//		consulta.append("         AND MODULO_ORIGEN='MOV' AND TIPO_OPERACION = 'RVEX' AND S.ID_CUENTA_RECEPTOR = MOV.ID_CUENTA_NOMBRADA_RECEPTORA ");
//		consulta.append("         AND (S.REFERENCIA_OPERACION = MOV.FOLIO_INSTRUCCION_RECEPTORA OR TO_CHAR(FOLIO_PARTICIPANTE) = MOV.FOLIO_INSTRUCCION_RECEPTORA)) ");
//		consulta.append("UNION ");
		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
        consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
		consulta.append("        LPAD(BT.ID_TIPO_INSTITUCION, 2, '0') || BT.FOLIO_INSTITUCION        AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        BT.NOMBRE_CORTO                                                     AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        C_BT.CUENTA                                                         AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION      AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        REC.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        C_REC.CUENTA                                                        AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
		consulta.append("        'TRASPASANTE'                                                       AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
        consulta.append("    AND MOV.ID_TIPO_INSTRUCCION NOT IN (54, 55) ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6, 9) ");
		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND NOT EXISTS ");
		consulta.append("        (SELECT * FROM T_BITACORA_ADAPTADOR_SALIDA S WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
		consulta.append("         AND MODULO_ORIGEN='MOV' AND S.REFERENCIA_OPERACION = MOV.FOLIO_INSTRUCCION_TRASPASANTE ");
		consulta.append("         AND S.ID_CUENTA_RECEPTOR = MOV.ID_CUENTA_NOMBRADA_RECEPTORA AND S.ID_CUENTA_TRASPASANTE = MOV.ID_CUENTA_BANCO_TRABAJO) ");
		consulta.append("UNION ");
		consulta.append("SELECT  DISTINCT MOV.ID_INSTRUCCION_OPERACION_VAL                           AS \""). append(ID_MODULO_ORIGEN.getPropiedadDto()).append("\", ");
        consulta.append("        MOV.ID_TIPO_INSTRUCCION, ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION      AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                    AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        C_TRA.CUENTA                                                        AS \"").append(CUENTA_TRASPASANTE.getPropiedadDto()).append("\", ");   
		consulta.append("        LPAD(BT.ID_TIPO_INSTITUCION, 2, '0') || BT.FOLIO_INSTITUCION        AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        BT.NOMBRE_CORTO                                                     AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        C_BT.CUENTA                                                         AS \"").append(CUENTA_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                                  AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                      AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");   
		consulta.append("        MOV.FECHA_CONCERTACION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");  
		consulta.append("        INO.CLAVE_TIPO_VALOR                                                AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                                   AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                           AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                     AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.PRECIO_TITULO                                                   AS \"").append(PRECIO_TITULO.getPropiedadDto()).append("\", ");
		consulta.append("        MOV.CANTIDAD_TITULOS                                                AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\", ");  
		consulta.append("        DIV.CLAVE_ALFABETICA                                                AS \"").append(DIVISA.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.IMPORTE                                                         AS \"").append(MONTO.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_CONTROL                                                   AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");  
		consulta.append("        MOV.FOLIO_INSTRUCCION_TRASPASANTE                                   AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");   
		consulta.append("        'RECEPTOR'                                                          AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_INSTRUCCION_OPERACION_VAL MOV, C_INSTITUCION TRA, C_INSTITUCION REC, C_CUENTA_NOMBRADA C_TRA, ");
		consulta.append("     C_CUENTA_NOMBRADA C_REC, C_TIPO_INSTRUCCION T_INS, C_ESTADO_INSTRUCCION E_INS, ");
		consulta.append("     C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, C_INSTRUMENTO INO, C_DIVISA DIV, ");
		consulta.append("     C_INSTITUCION BT, C_CUENTA_NOMBRADA C_BT ");
		consulta.append("WHERE   MOV.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_INSTITUCION_BANCO_TRABAJO = BT.ID_INSTITUCION ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_TRASPASANTE = C_TRA.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_NOMBRADA_RECEPTORA = C_REC.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_CUENTA_BANCO_TRABAJO = C_BT.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND MOV.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOV.ID_DIVISA = DIV.ID_DIVISA(+) ");
		consulta.append("    AND MOV.ID_CUPON = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
        consulta.append("    AND MOV.ID_TIPO_INSTRUCCION NOT IN (54, 55) ");
		consulta.append("    AND MOV.ID_ESTADO_INSTRUCCION IN (6,9) ");
		consulta.append("    AND MOV.FECHA_LIQUIDACION = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND NOT EXISTS ");
		consulta.append("        (SELECT * FROM T_BITACORA_ADAPTADOR_SALIDA S WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND TIPO_MENSAJE IN ('544', '545', '546', '547') ");
		consulta.append("         AND MODULO_ORIGEN='MOV' AND S.REFERENCIA_OPERACION = MOV.FOLIO_INSTRUCCION_RECEPTORA ");
		consulta.append("         AND S.ID_CUENTA_RECEPTOR = MOV.ID_CUENTA_BANCO_TRABAJO AND S.ID_CUENTA_TRASPASANTE = MOV.ID_CUENTA_NOMBRADA_TRASPASANTE) ");                                                                   
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\" ");
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MOV_AS, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMosAs(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  DISTINCT MOS.ID_INSTRUCCION_EFECTIVO                       	 AS \"").append(ID_INSTRUCCION_EFECTIVO.getPropiedadDto()).append("\", ");
		consulta.append("		 DECODE(MOS.ID_TIPO_INSTRUCCION, 30, 'TRASPASANTE', NULL)        AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                              AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                  AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");  
		consulta.append("        T_MEN.CLAVE_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_REGISTRO                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_LIQUIDACION                                           AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.REFERENCIA_PARTICIPANTE                                     AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.REFERENCIA_OPERACION                                        AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.MONTO                                                       AS \"").append(IMPORTE.getPropiedadDto()).append("\", ");
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\",  ");
		consulta.append("        MOS.ID_TIPO_INSTRUCCION ");
		consulta.append("FROM T_INSTRUCCION_EFECTIVO MOS, C_INSTITUCION TRA, C_INSTITUCION REC, C_TIPO_INSTRUCCION T_INS, ");
		consulta.append("     C_ESTADO_INSTRUCCION E_INS, C_TIPO_MENSAJE T_MEN, C_DIVISA DIV, C_PARTICIPANTE_PFI PFT ");
		consulta.append("WHERE   MOS.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOS.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOS.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOS.ID_TIPO_MENSAJE = T_MEN.ID_TIPO_MENSAJE ");
		consulta.append("    AND MOS.ID_DIVISA = DIV.ID_DIVISA ");
		consulta.append("    AND MOS.ID_INSTITUCION_TRASPASANTE = PFT.ID_PARTICIPANTE ");
		consulta.append("    AND PFT.ENVIA_EFECTIVO = 'S' ");
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION IN (6, 9) ");
		consulta.append("    AND ( ");
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (30) ");
		consulta.append("                AND MOS.ID_CUENTA_TRASPASANTE IS NOT NULL ");
		consulta.append("                AND MOS.ID_CUENTA_RECEPTORA IS NOT NULL ");
		consulta.append("            ) ");
		consulta.append("            OR ");
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (33) ");
		consulta.append("            ) ");
		consulta.append("        ) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION < TRUNC(CURRENT_DATE + 1) ");
		consulta.append("    AND (MOS.REFERENCIA_OPERACION, MOS.ID_CUENTA_TRASPASANTE, MOS.ID_CUENTA_RECEPTORA, MOS.ID_INSTITUCION_TRASPASANTE) NOT IN ");
		consulta.append("        (SELECT REFERENCIA_OPERACION, ID_CUENTA_TRASPASANTE, ID_CUENTA_RECEPTOR, ID_PARTICIPANTE FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("         WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND TIPO_MENSAJE = '900' ");
		consulta.append("         AND MODULO_ORIGEN='MOS') ");
		consulta.append("UNION ");
		consulta.append("SELECT  DISTINCT MOS.ID_INSTRUCCION_EFECTIVO                       	 AS \"").append(ID_INSTRUCCION_EFECTIVO.getPropiedadDto()).append("\", ");
		consulta.append("        DECODE(MOS.ID_TIPO_INSTRUCCION, 30, 'RECEPTOR', NULL)           AS \"").append(PARTICIPANTE_SIN_NOTIFICAR.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION  AS \"").append(FOLIO_TRASPASANTE.getPropiedadDto()).append("\", "); 
		consulta.append("        TRA.NOMBRE_CORTO                                                AS \"").append(NOMBRE_TRASPASANTE.getPropiedadDto()).append("\", ");  
		consulta.append("        LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION  AS \"").append(FOLIO_RECEPTORA.getPropiedadDto()).append("\", ");
		consulta.append("        REC.NOMBRE_CORTO                                                AS \"").append(NOMBRE_RECEPTORA.getPropiedadDto()).append("\", ");  
		consulta.append("        T_INS.NOMBRE_CORTO                                              AS \"").append(TIPO_INSTRUCCION.getPropiedadDto()).append("\", ");  
		consulta.append("        E_INS.CLAVE_ESTADO_INSTRUCCION                                  AS \"").append(ESTADO_OPERACION.getPropiedadDto()).append("\", ");  
		consulta.append("        T_MEN.CLAVE_MENSAJE                                             AS \"").append(TIPO_MENSAJE.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_REGISTRO                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.FECHA_LIQUIDACION                                           AS \"").append(FECHA_LIQUIDACION.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.REFERENCIA_PARTICIPANTE                                     AS \"").append(FOLIO_USUARIO.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.REFERENCIA_OPERACION                                        AS \"").append(FOLIO_CONTROL.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.MONTO                                                       AS \"").append(IMPORTE.getPropiedadDto()).append("\", ");
		consulta.append("        DIV.CLAVE_ALFABETICA                                            AS \"").append(DIVISA.getPropiedadDto()).append("\", ");
		consulta.append("        MOS.ID_TIPO_INSTRUCCION ");
		consulta.append("FROM T_INSTRUCCION_EFECTIVO MOS, C_INSTITUCION TRA, C_INSTITUCION REC, C_TIPO_INSTRUCCION T_INS, ");
		consulta.append("     C_ESTADO_INSTRUCCION E_INS, C_TIPO_MENSAJE T_MEN, C_DIVISA DIV, C_PARTICIPANTE_PFI PFR ");
		consulta.append("WHERE   MOS.ID_INSTITUCION_TRASPASANTE = TRA.ID_INSTITUCION ");
		consulta.append("    AND MOS.ID_INSTITUCION_RECEPTORA = REC.ID_INSTITUCION ");
		consulta.append("    AND MOS.ID_TIPO_INSTRUCCION = T_INS.ID_TIPO_INSTRUCCION ");
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION = E_INS.ID_ESTADO_INSTRUCCION ");
		consulta.append("    AND MOS.ID_TIPO_MENSAJE = T_MEN.ID_TIPO_MENSAJE ");
		consulta.append("    AND MOS.ID_DIVISA = DIV.ID_DIVISA ");
		consulta.append("    AND MOS.ID_INSTITUCION_RECEPTORA = PFR.ID_PARTICIPANTE ");
		consulta.append("    AND PFR.ENVIA_EFECTIVO = 'S' ");
		consulta.append("    AND MOS.ID_ESTADO_INSTRUCCION IN (6, 9) ");
		consulta.append("    AND ( ");
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (30) ");
		consulta.append("                AND MOS.ID_CUENTA_TRASPASANTE IS NOT NULL ");
		consulta.append("                AND MOS.ID_CUENTA_RECEPTORA IS NOT NULL ");
		consulta.append("            ) ");
		consulta.append("            OR ");
		consulta.append("            ( ");
		consulta.append("                MOS.ID_TIPO_INSTRUCCION IN (29) ");
		consulta.append("            ) ");
		consulta.append("        ) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MOS.FECHA_LIQUIDACION < TRUNC(CURRENT_DATE + 1) ");
		consulta.append("    AND (MOS.REFERENCIA_OPERACION, MOS.ID_CUENTA_TRASPASANTE, MOS.ID_CUENTA_RECEPTORA, DECODE(MOS.ID_INSTITUCION_RECEPTORA, 229, 375, MOS.ID_INSTITUCION_RECEPTORA)) NOT IN ");
		consulta.append("        (SELECT REFERENCIA_OPERACION, ID_CUENTA_TRASPASANTE, ID_CUENTA_RECEPTOR, ID_PARTICIPANTE FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("         WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND TIPO_MENSAJE IN ('910') ");
		consulta.append("         AND MODULO_ORIGEN='MOS') ");
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\" "); 
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MOS_AS, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConciliacionModulosDTO> obtenRegistrosConciliacionMavAs(final CriteriosConciliacionModulosDTO criterios) {
		final StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT * FROM ( ");
		consulta.append("SELECT  NULL                                                            AS \"").append(FOLIO_TENEDOR.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                            AS \"").append(NOMBRE_TENEDOR.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION, ");
		consulta.append("        LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION)     AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", "); 
		consulta.append("        NVL(T.NOMBRE_CORTO, R.NOMBRE_CORTO)                             AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NVL(CT.CUENTA, CR.CUENTA)                                       AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        AE.TIPO_OPERACION                                               AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        AE.REFERENCIA_OPERACION                                         AS \"").append(REF_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        MX.FECHA_RECEPCION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        AE.TV                                                           AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        AE.EMISORA                                                      AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        AE.SERIE                                                        AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        AE.CUPON                                                        AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        AE.NUMERO_TITULOS                                               AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_BITACORA_ADAPTADOR AE, T_MENSAJE_XML MX, C_CUENTA_NOMBRADA CT, C_CUENTA_NOMBRADA CR, ");
		consulta.append("     C_INSTITUCION T, C_INSTITUCION R, T_DEPOSITOS DP, T_RETIROS RT ");
		consulta.append("WHERE   AE.REFERENCIA_OPERACION = MX.REFERENCIA_OPERACION ");
		consulta.append("    AND AE.ID_CUENTA_TRASPASANTE = CT.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND CT.ID_INSTITUCION = T.ID_INSTITUCION(+) ");
		consulta.append("    AND AE.ID_CUENTA_RECEPTOR = CR.ID_CUENTA_NOMBRADA(+) ");
		consulta.append("    AND CR.ID_INSTITUCION = R.ID_INSTITUCION(+) ");
		consulta.append("    AND MX.ID_REGISTRO = DP.ID_DEPOSITO(+) ");
		consulta.append("    AND MX.ID_REGISTRO = RT.ID_RETIRO(+) ");
		consulta.append("    AND (AE.TIPO_MENSAJE = '500' ");
		consulta.append("         OR AE.TIPO_OPERACION IN ('Retiro', 'Deposito')) ");
		consulta.append("    AND MX.ESTADO = 'S' ");
		consulta.append("    AND MX.ID_REGISTRO <> 0 ");
		consulta.append("    AND MX.ID_TIPO_OPERACION IN (1, 2, 3) ");
		consulta.append("    AND NVL(RT.FECHA_LIBERACION, NVL(DP.FECHA_LIBERACION, NVL(AE.FECHA_LIQUIDACION, AE.FECHA_RECEPCION))) >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND NVL(RT.FECHA_LIBERACION, NVL(DP.FECHA_LIBERACION, NVL(AE.FECHA_LIQUIDACION, AE.FECHA_RECEPCION))) < TRUNC(CURRENT_DATE+1) ");
		consulta.append("    AND MX.FECHA_RECEPCION >= TRUNC(CURRENT_DATE) ");
		consulta.append("    AND MX.FECHA_RECEPCION < TRUNC(CURRENT_DATE+1) ");
		consulta.append("    AND ( ");
		consulta.append("         MX.REFERENCIA_OPERACION, ");
		consulta.append("         DECODE(MX.ID_TIPO_OPERACION, 1, ' ', AE.TV), ");
		consulta.append("         DECODE(MX.ID_TIPO_OPERACION, 1, ' ', AE.EMISORA), ");
		consulta.append("         DECODE(MX.ID_TIPO_OPERACION, 1, ' ', AE.SERIE), ");
		consulta.append("         DECODE(MX.ID_TIPO_OPERACION, 1, ' ', AE.CUPON) ");
		consulta.append("        ) ");
		consulta.append("        NOT IN ");
		consulta.append("            (SELECT REFERENCIA_OPERACION, NVL(TV, ' '), NVL(EMISORA, ' '), NVL(SERIE, ' '), NVL(CUPON, ' ') ");
		consulta.append("             FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("             WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND (TIPO_MENSAJE = '501' ");
		consulta.append("             OR TIPO_OPERACION IN ('CONFIRMACION_DEPOSTIO_VALORES', 'CONFIRMACION_RETIRO_VALORES'))) ");
		consulta.append("UNION ");
		consulta.append("SELECT  LPAD(IT.ID_TIPO_INSTITUCION, 2, '0') || IT.FOLIO_INSTITUCION    AS \"").append(FOLIO_TENEDOR.getPropiedadDto()).append("\", ");
		consulta.append("        IT.NOMBRE_CORTO                                                 AS \"").append(NOMBRE_TENEDOR.getPropiedadDto()).append("\", ");
		consulta.append("        LPAD(INS.ID_TIPO_INSTITUCION, 2, '0') || INS.FOLIO_INSTITUCION  AS \"").append(FOLIO_INSTITUCION.getPropiedadDto()).append("\", "); 
		consulta.append("        INS.NOMBRE_CORTO                                                AS \"").append(NOMBRE_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        NULL                                                            AS \"").append(CUENTA_INSTITUCION.getPropiedadDto()).append("\", ");
		consulta.append("        'Registro'                                                      AS \"").append(TIPO_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        MX.REFERENCIA_OPERACION                                         AS \"").append(REF_OPERACION.getPropiedadDto()).append("\", "); 
		consulta.append("        MX.FECHA_RECEPCION                                              AS \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\", ");
		consulta.append("        INO.CLAVE_TIPO_VALOR                                            AS \"").append(TV.getPropiedadDto()).append("\", ");  
		consulta.append("        EMA.CLAVE_PIZARRA                                               AS \"").append(EMISORA.getPropiedadDto()).append("\", ");  
		consulta.append("        EMN.SERIE                                                       AS \"").append(SERIE.getPropiedadDto()).append("\", ");  
		consulta.append("        CUP.CLAVE_CUPON                                                 AS \"").append(CUPON.getPropiedadDto()).append("\", ");
		consulta.append("        DD.TITULOS_AMORTIZAR + DD.TITULOS_AMORTIZAR_ANTICIPADO          AS \"").append(CANTIDAD_TITULOS.getPropiedadDto()).append("\" ");
		consulta.append("FROM T_MENSAJE_XML MX, T_DERECHOS_DEUDA DD, C_INSTITUCION INS, C_CUPON CUP, C_EMISION EMN, C_EMISORA EMA, ");
		consulta.append("     C_INSTRUMENTO INO, T_PRODUCTOS_DERECHO_DEUDA PDD, T_PRODUCTOS_DERECHO PD, C_CUENTA_NOMBRADA CT, ");
		consulta.append("     C_INSTITUCION IT ");
		consulta.append("WHERE   MX.ID_REGISTRO = DD.ID_DERECHO_DEUDA ");
		consulta.append("    AND DD.ID_INSTITUCION = INS.ID_INSTITUCION ");
		consulta.append("    AND DD.ID_CUPON_ORIGEN = CUP.ID_CUPON ");
		consulta.append("    AND CUP.ID_EMISION = EMN.ID_EMISION ");
		consulta.append("    AND EMN.ID_EMISORA = EMA.ID_EMISORA ");
		consulta.append("    AND EMN.ID_INSTRUMENTO = INO.ID_INSTRUMENTO ");
		consulta.append("    AND DD.ID_DERECHO_DEUDA = PDD.ID_DERECHO_DEUDA ");
		consulta.append("    AND PDD.ID_PRODUCTO_DERECHO = PD.ID_PRODUCTO_DERECHO ");
		consulta.append("    AND PD.ID_CUENTA = CT.ID_CUENTA_NOMBRADA ");
		consulta.append("    AND CT.ID_INSTITUCION = IT.ID_INSTITUCION ");
		consulta.append("    AND MX.ESTADO = 'S' ");
		consulta.append("    AND MX.ID_REGISTRO <> 0 ");
		consulta.append("    AND MX.ID_TIPO_OPERACION = 5 ");
		consulta.append("    AND DD.FECHA_PAGO_DERECHO_DEUDA = TRUNC(CURRENT_DATE) ");
		consulta.append("    AND (MX.REFERENCIA_OPERACION, IT.ID_INSTITUCION) NOT IN ");
		consulta.append("        (SELECT REFERENCIA_OPERACION, ID_PARTICIPANTE FROM T_BITACORA_ADAPTADOR_SALIDA ");
		consulta.append("         WHERE TRUNC(FECHA_CREACION) = TRUNC(CURRENT_DATE) AND TIPO_MENSAJE = '566') ");
		consulta.append(") WHERE ROWNUM>= 0 ");
		consulta.append(MARCA_CRITERIOS).append(" ");
		consulta.append("ORDER BY \"").append(FECHA_REGISTRO.getPropiedadDto()).append("\" "); 
		
		return (List<ConciliacionModulosDTO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = obtenQueryConfigurado(consulta, FlujoConciliacionModulosEnum.MAV_AS, criterios, session);
								
				return query.list();
			}
			
		});
	}
	
	private SQLQuery obtenQueryConfigurado(StringBuilder consulta, FlujoConciliacionModulosEnum flujo, CriteriosConciliacionModulosDTO criterios, Session session) {
		List<Object> valoresFiltros = this.configuraCriteriosConsulta(consulta, flujo, criterios);		
		SQLQuery query = session.createSQLQuery(consulta.toString());
		query.setResultTransformer(Transformers.aliasToBean(ConciliacionModulosDTO.class));
		this.asignaParametrosQuery(query, valoresFiltros);
		this.asignaEscalaresCamposConsulta(query, flujo);
		
		return query;
	}
	
	private void asignaEscalaresCamposConsulta(SQLQuery query, FlujoConciliacionModulosEnum flujo) {
		for(CampoConciliacionModulosEnum campo : CampoConciliacionModulosEnum.getCamposPorFlujo(flujo)) {
			query.addScalar(campo.getPropiedadDto(), campo.getTipoHibernate());
		}
	}
	
	private List<Object> configuraCriteriosConsulta(StringBuilder consulta, FlujoConciliacionModulosEnum flujo, CriteriosConciliacionModulosDTO criterios) {
		List<Object> valoresFiltros = new ArrayList<>();
		StringBuilder strCriteriosFiltrar = new StringBuilder();
		
		if(criterios.getIdFolioParticipante() != null && !criterios.getIdFolioParticipante().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroFolioTras()).append("= ? ");
			valoresFiltros.add(criterios.getIdFolioParticipante());
		}
		
		if(criterios.getCuentaParticipante() != null && !criterios.getCuentaParticipante().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroCuentaTras()).append("= ? ");
			valoresFiltros.add(criterios.getCuentaParticipante());
		}
		
		if(criterios.getIdFolioContraparte() != null && !criterios.getIdFolioContraparte().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroFolioRece()).append("= ? ");
			valoresFiltros.add(criterios.getIdFolioContraparte());
		}
		
		if(criterios.getCuentaContraparte() != null && !criterios.getCuentaContraparte().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroCuentaRece()).append("= ? ");
			valoresFiltros.add(criterios.getCuentaContraparte());
		}
		
		if(flujo.getCampos().getCampoFiltroTv() != null && criterios.getTipoValor() != null && !criterios.getTipoValor().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroTv()).append("= ? ");
			valoresFiltros.add(criterios.getTipoValor());
		}
		
		if(flujo.getCampos().getCampoFiltroEmisora() != null && criterios.getEmisora() != null && !criterios.getEmisora().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroEmisora()).append("= ? ");
			valoresFiltros.add(criterios.getEmisora());
		}
		
		if(flujo.getCampos().getCampoFiltroSerie() != null && criterios.getSerie() != null && !criterios.getSerie().trim().isEmpty()) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroSerie()).append("= ? ");
			valoresFiltros.add(criterios.getSerie());
		}
		
		if(flujo.getCampos().getCampoFiltroTipoInstr() != null && criterios.getTipoInstruccion() != null && criterios.getTipoInstruccion().compareTo(0L) > 0) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroTipoInstr()).append("= ? ");
			valoresFiltros.add(criterios.getTipoInstruccion());
		}
		
		if(flujo.getCampos().getCampoFiltroTipoOper() != null && criterios.getTipoOperacion() != null && criterios.getTipoOperacion().compareTo(0L) > 0) {
			strCriteriosFiltrar.append(" AND ").append(flujo.getCampos().getCampoFiltroTipoOper()).append("= ? ");
			valoresFiltros.add(criterios.getTipoOperacion());
		}
		
		int indexMarca = consulta.lastIndexOf(MARCA_CRITERIOS);
		consulta.replace(indexMarca, indexMarca + MARCA_CRITERIOS.length(), strCriteriosFiltrar.toString());
		
		return valoresFiltros;
	}
	
	private void asignaParametrosQuery(Query query, List<Object> valoresFiltros) {
		int contadorParametros = 0;
		
		for(Object valorFiltro : valoresFiltros) {
			query.setParameter(contadorParametros++, valorFiltro);
		}
	}

}
