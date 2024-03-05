/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.unittest;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Clase para la generaci&oacute;n de un pojo
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class PojoMaker extends BaseDaoTestCase {
    
    /* Parametros ajustables */
    
    /* Apunta el dataSorce a Sybase o al de Oracle */
    //private static final String DATASOURCE = "dataSource";
    private static final String DATASOURCE = "dataSourceDali";
    
    /* Ajusta el nombre de la tabla a leer */
    private static final String TABLENAME = "C_TIPO_BOVEDA";
    
    /* Ajusta el nombre del paquete donde sera ubicada la entidad */
    private static final String BASE = "dali";
    
    /* Ajusta el nombre de la entidad a generar */
    private static final String ENTIDAD = "TipoBoveda";
    
    /* Parametros cliente */
    private static final String ANHIO = "2005-2006";
    private static final String CLIENTE = "Bursatec";
    
    /**
     * Inner Class MetaDataHelper
     */
    private static class MetaDataHelper {
        
        /**
         * ResultSetMetaData
         */
        private ResultSetMetaData rsmd;
        
        /** */
        private int colIdx;
        
        /**
         * @param rsmd
         * @param colIdx
         */
        public MetaDataHelper(ResultSetMetaData rsmd, int colIdx) {
            this.rsmd   = rsmd;
            this.colIdx = colIdx;
        }
        
        /**
         * @return String
         * @throws Exception
         */
        public String getClassName() throws Exception {
            String className = rsmd.getColumnClassName(colIdx);
            className = className.substring(className.lastIndexOf(".") + 1);
            if (className.equals("Timestamp")) {
                className = "Date";
            }
            return className;
        }
        
        /**
         * @return String
         * @throws Exception
         */
        public String getColumnName() throws Exception {
            return rsmd.getColumnName(colIdx).toLowerCase();
        }
        
        /**
         * @return String
         * @throws Exception
         */
        public String getFieldName() throws Exception {
            String fieldName = this.getColumnName();
            String words[] = fieldName.split("_");
            
            fieldName = words[0];
            for(int j = 1; j < words.length; j++) {
                fieldName += toUppercaseFirst(words[j]);
            }
            return fieldName;
        }
        
        /**
         * @return boolean
         * @throws Exception
         */
        public boolean isNullable() throws Exception {
            return rsmd.isNullable(colIdx) == ResultSetMetaData.columnNullable;            
        }
        
        /**
         * @param word
         * @return String
         */
        private static String toUppercaseFirst(String word) {            
            String firstLetter = word.substring(0,1);
            String rest = word.substring(1);
            assertEquals(word, firstLetter + rest);
            return firstLetter.toUpperCase() + rest;
        }
        
        /**
         * @return String
         * @throws Exception
         */
        public String getGetterName() throws Exception {
            String fieldName = this.getFieldName();
            return "get" + toUppercaseFirst(fieldName);
        }
        
        /**
         * @return String
         * @throws Exception
         */
        public String getSetterName() throws Exception {
            String fieldName = this.getFieldName();
            return "set" + toUppercaseFirst(fieldName);
        }
    };
    
    /**
     * Recupera la Metadata de la tabla
     * @param tableName
     * @return ResultSetMetaData
     * @throws Exception
     */
    public ResultSetMetaData getMetaData(String tableName) throws Exception {
        
        DataSource dataSource = (DataSource)getBean(DATASOURCE);
        
        assertNotNull(dataSource);
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from "+tableName+" where 1=2");
        ResultSet rs = ps.executeQuery();        
        assertNotNull(rs);
        ResultSetMetaData rsmd = rs.getMetaData();
        assertNotNull(rsmd);
        return rsmd;
        
    }
    
    /**
     * @throws Exception
     */
    public void testMakePojo() throws Exception {
        
        /* Parametros ajustables */
        String tableName = TABLENAME;
        String base = BASE;
        String entidad = ENTIDAD;
        
        ResultSetMetaData rsmd = getMetaData(tableName);
        PrintStream out = new PrintStream(new FileOutputStream(ENTIDAD+".java"));
        out.println("/*");
        out.println(" * Copyrigth (c) "+ ANHIO +" "+CLIENTE+". All Rights Reserved.");
        out.println(" */");
        out.println("package com.indeval.persistence.portallegado."+base+".modelo;");
        out.println();
        out.println("import java.math.BigDecimal;");
        out.println("import java.math.BigInteger;");
        out.println("import java.util.Date;");
        out.println();
        out.println("/**");
        out.println(" * @hibernate.mapping");
        out.println(" *");
        out.println(" * @hibernate.class");
        out.println(" *   table=\""+tableName+"\"");
        out.println(" *   proxy=\"com.indeval.persistence.portallegado."+base+".modelo."+entidad+"\";");
        out.println(" */");
        out.println("public class "+entidad+" {");        
        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            MetaDataHelper helper = new MetaDataHelper(rsmd, i);
            out.print("    private "+helper.getClassName()+" "+helper.getFieldName()+";");
            if (!helper.isNullable()) {
                out.print(" // key?");
            }
            out.println();
        }
        out.println();
        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            MetaDataHelper helper = new MetaDataHelper(rsmd, i);
            out.println();
            out.println("    /**");
            out.println("     * @hibernate.property");
            out.println("     *   column=\""+helper.getColumnName()+"\"");
            out.println("     *   not-null=\""+(!helper.isNullable())+"\"");
            out.println("     */");
            out.println("    public "+helper.getClassName()+" "+helper.getGetterName()+"() {");
            out.println("        return "+helper.getFieldName()+";");
            out.println("    }");
        }
        out.println();
        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            MetaDataHelper helper = new MetaDataHelper(rsmd, i);
            out.println();
            out.println("    public void "+helper.getSetterName()+"("+helper.getClassName()+" "+helper.getFieldName()+") {");
            out.println("        this."+helper.getFieldName()+" = "+helper.getFieldName()+";");
            out.println("    }");
        }
        out.println("}");
    }
    
}
