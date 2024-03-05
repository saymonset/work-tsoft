/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 9, 2008
 *
 */
package com.indeval.portaldali.middleware.services.util.test;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * Clase utilitaria que sirve para generar un archivo xml que contiene un dataset
 * extraido de la base de datos. 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ExportDataBase {

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
        // database connection
        @SuppressWarnings("unused")
		Class driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection jdbcConnection = DriverManager.getConnection(
	        "jdbc:oracle:thin:@10.100.144.4:1521:dwbuild", 
	        "liqval_user", 
	        "liqval_user");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        
//        partialDataSet.addTable("T_POSICION_CONTROLADA", "select * from T_POSICION_CONTROLADA where ID_CUENTA in (881)");        
//        partialDataSet.addTable(" T_POSICION_TRASPASANTE", "select * from  T_POSICION_TRASPASANTE where ID_CUENTA in (1993)");        
//        partialDataSet.addTable("T_ESTADO_POSICIONES_NOMBRADA", "select * from T_ESTADO_POSICIONES_NOMBRADA where ID_POSICION in (54788)");        
//        partialDataSet.addTable("T_ESTADO_POSICIONES_CONTROLADA", "select * from T_ESTADO_POSICIONES_CONTROLADA where ID_POSICION in (54208)");                        
//        
//        partialDataSet.addTable("T_REG_CONTABLE_VAL_CONTROL", "select * from T_REG_CONTABLE_VAL_CONTROL " +
//        	"WHERE ID_POSICION in (54208, 54210, 54212, 54214, 54216, 54218, 54220, 54222, 54224, 54226) " +
//        	"AND ID_CICLO in (1050, 1927, 3313, 6459, 4699, 1000, 1495, 1676, 1689)"); 

//        
//        partialDataSet.addTable("T_SALDO_CONTROLADA", "select * from T_SALDO_CONTROLADA where ID_CUENTA in (863)");
//        partialDataSet.addTable("T_ESTADO_SALDOS_CONTROLADA", "select * from T_ESTADO_SALDOS_CONTROLADA where ID_SALDO in (14829)");
//        partialDataSet.addTable("T_SALDO_NOMBRADA", "select * from T_SALDO_NOMBRADA where ID_CUENTA in (1953)");
//        partialDataSet.addTable("T_ESTADO_SALDOS_NOMBRADA", "select * from T_ESTADO_SALDOS_NOMBRADA where ID_SALDO in (13698)");
//        
//        partialDataSet.addTable("T_REG_CONTABLE_EFE_CONTROL", 
//        	"select * from T_REG_CONTABLE_EFE_CONTROL " +T_POSICION_NOMBRADA
//        	"WHERE ID_CICLO in (1050, 1927, 3313, 6459, 4699, 1000, 1495, 1676, 1689) " +
//        	"AND ID_SALDO in (14829, 14831, 14833, 14835, 14837, 14839, 14841, 14843)");        
//        partialDataSet.addTable("T_REG_CONTABLE_EFE_NOMBRADA", 
//        	"select * from T_REG_CONTABLE_EFE_NOMBRADA " +
//        	"WHERE ID_CICLO in (1050, 1927, 3313, 6459, 4699, 1000, 1495, 1676, 1689) " +
//        	"AND ID_SALDO in (13698, 13700, 13702, 13704, 13706, 13708, 13710, 13712)");
//        
//        partialDataSet.addTable("T_CICLO_LIQUIDACION", "select * from T_CICLO_LIQUIDACION where ID_CICLO in (1050, 1927, 3313, 6459, 4699, 1000, 1495, 1676, 1689)");
//        partialDataSet.addTable("R_REG_CONT_CONT_EFEC_OP", "select * from R_REG_CONT_CONT_EFEC_OP");
//        partialDataSet.addTable("R_REG_CONT_CONT_VAL_OP", "select * from R_REG_CONT_CONT_VAL_OP");
//        partialDataSet.addTable("T_OPERACION_NOMBRADA", "select * from T_OPERACION_NOMBRADA");
//        partialDataSet.addTable("T_POS_OPERACION_NOMBRADA", "select * from T_POS_OPERACION_NOMBRADA");
//        
//        partialDataSet.addTable("T_INSTRUCCION_LIQUIDACION", "select * from T_INSTRUCCION_LIQUIDACION where ID_CICLO in (1050)");
//        partialDataSet.addTable("T_OPERACION_NOMBRADA", 
//        	"select * from T_OPERACION_NOMBRADA " +
//        	"where ID_INSTRUCCION in (1400, 1401, 1402, 1403, 1404, 1405, 1406, 1407, 1408, 1409)");
//        partialDataSet.addTable("T_POS_OPERACION_NOMBRADA", "select * from T_POS_OPERACION_NOMBRADA " +
//        	"where ID_OPERACION_NOMBRADA in (1400, 1401, 1402, 1403, 1404, 1405, 1406, 1407, 1408, 1409)");
//        partialDataSet.addTable("T_REG_CONTABLE_VAL_NOMBRADA", "select * from T_REG_CONTABLE_VAL_NOMBRADA " +
//        	"WHERE ID_OPERACION in (1400, 1401, 1402, 1403, 1404, 1405, 1406, 1407, 1408, 1409) " +
//        	"and ID_POSICION in (54788, 54790, 54792, 54794, 54796, 54798, 54800, 54802, 54804, 54806)");     
        
        partialDataSet.addTable(" T_POSICION_TRASPASANTE", "select * from  T_POSICION_TRASPASANTE " +
        	"where ID_POSICION in (61287, 57396, 61286, 61285)");
        
        partialDataSet.addTable("T_POSICION_CONTROLADA", "select * from T_POSICION_CONTROLADA " +
        	"where ID_POSICION in (56816,60697,60698,60699)");
        
        partialDataSet.addTable("T_CICLO_LIQUIDACION", "select * from T_CICLO_LIQUIDACION " +
        	"where ID_CICLO in (1,2,3,4)");
        
        partialDataSet.addTable("T_ESTADO_POSICIONES_NOMBRADA", "select * from T_ESTADO_POSICIONES_NOMBRADA " +
        	"where ID_POSICION in (61287, 57396, 61286, 61285) and ID_CICLO in (1,2,3,4,10) " +
        	"order by ID_POSICION");
        
        partialDataSet.addTable("T_ESTADO_POSICIONES_CONTROLADA", "select * from T_ESTADO_POSICIONES_CONTROLADA " +
        	"where ID_POSICION in (56816,60697,60698,60699) and ID_CICLO in (1,2,3,4,10) " +
        	"order by ID_POSICION");
        
        partialDataSet.addTable("T_INSTRUCCION_LIQUIDACION", "select * from T_INSTRUCCION_LIQUIDACION " +
        	"where ID_INSTRUCCION_LIQUIDACION in (1,2,3,4,10)");
        
        partialDataSet.addTable("T_OPERACION_NOMBRADA", "select * from T_OPERACION_NOMBRADA " +
        	"where ID_INSTRUCCION in (1,2,3,4,10) order by ID_INSTRUCCION" );
        
        partialDataSet.addTable("T_POS_OPERACION_NOMBRADA", "select * from T_POS_OPERACION_NOMBRADA " +
    		"where ID_OPERACION_NOMBRADA in (1,2,3,4,5,6,7,8,9,10,11,12,26,27,28)");        
        
        partialDataSet.addTable("T_REG_CONTABLE_VAL_NOMBRADA", 
        	"select * from T_REG_CONTABLE_VAL_NOMBRADA where " +
            "ID_OPERACION in (1,2,3,4,5,6,7,8,9,10,11,12,26,27,28) " +
            "order by ID_REGISTRO_CONTABLE,ID_OPERACION");

        partialDataSet.addTable("T_REG_CONTABLE_VAL_CONTROL", "select * from T_REG_CONTABLE_VAL_CONTROL " +
    		"where ID_REGISTRO_CONTABLE in(17415,17416,17417,17418,17419,17420,17421,17422,17423,17424,17425,17426,17427,17428,17429,17430)");
        

        
        

        
        partialDataSet.addTable("R_REG_CONT_CONT_VAL_OP", "select * from R_REG_CONT_CONT_VAL_OP " +
        	"where ID_OPERACION_NOMBRADA in (1,2,3,4,5,6,7,8,9,10,11,12,26,27,28) " +
        	"order by ID_OPERACION_NOMBRADA");
        
        
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("dataSet.xml"));
    }
	
}
