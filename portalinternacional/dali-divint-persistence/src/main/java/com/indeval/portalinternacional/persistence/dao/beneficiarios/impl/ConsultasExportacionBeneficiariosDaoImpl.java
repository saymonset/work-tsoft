package com.indeval.portalinternacional.persistence.dao.beneficiarios.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.persistence.dao.beneficiarios.ConsultasExportacionBeneficiariosDao;
import com.indeval.portalinternacional.persistence.dao.impl.BeneficiarioDaoImpl;

public class ConsultasExportacionBeneficiariosDaoImpl extends BaseDaoHibernateImpl implements ConsultasExportacionBeneficiariosDao {
	
	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BeneficiarioDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({"unchecked"})
    public List<Beneficiario> findBeneficiariosByFormato(final ConsultaBeneficiariosParam params) {
        log.info("Entrando a findBeneficiariosByFormato()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        
        String formato = "";
        if (StringUtils.isNotBlank(params.getFormato()) && !params.getFormato().equals("-1")) {
            if (params.getFormato().equalsIgnoreCase("W8BEN")) {
               formato = BeneficiariosConstantes.FORMATO_W8_BEN2017;
               sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
               sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
               sb.append(" 	LEFT JOIN FETCH ben.formatoW8BENE w8bene "); 
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(params.getFormato())) {
            	formato = BeneficiariosConstantes.FORMATO_W8_BEN_E_2016;
            	sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
                sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
                sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY w8imy ");
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(params.getFormato())) {
                formato = BeneficiariosConstantes.FORMATO_W8_IMY2017;
                sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
                sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
                sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY w8imy ");
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(params.getFormato())) {
                formato = BeneficiariosConstantes.FORMATO_W9;
                sb.append(" 	LEFT JOIN FETCH ben.domicilio dom ");
                sb.append(" 	LEFT JOIN FETCH ben.formatoW9 w9 ");
            }
        }        
        
        sb.append(" WHERE ben.formato = ? ");
        paramsSQL.add(formato);
        tipos.add(new LongType());
        sb.append("		AND ben.eliminado = false ");
        
        if (params.getFechaRegistroInicio() != null) {
            sb.append("	AND ben.fecha_registro >= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroInicio(),
                            true) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaRegistroInicio(), true));
        }

        if (params.getFechaRegistroFin() != null) {
            sb.append("	AND ben.fecha_registro <= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                            false) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                    false));
        }

        return (List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                log.info("Saliendo de findBeneficiariosByFormato()");
                return query.list();
            }
        });
    }
    
    @SuppressWarnings("unchecked")
	public List<Beneficiario> obtenerRegistrosW8BEN(Date fechaIni, Date fechaFin){
    	log.info("Entrando a findBeneficiariosByFormato()");
        
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
    	
    	final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT  DISTINCT ben ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domW8Normal ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domW8Mail ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN forW8ben ");
        sb.append(" 	LEFT JOIN FETCH forW8ben.field3 f3W8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH i.estadoInstitucion edoi ");
        sb.append(" where ben.tipoFormato in(?, ?, ?) ");
        sb.append(" and stb.idStatusBenef = 2 ");
        sb.append("	and ben.eliminado = false ");

        //Agrega datos para filtro de formato
        tipos.add(new StringType());
        tipos.add(new StringType());
        tipos.add(new StringType());
        paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN);
        paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN2014);
        paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN2017);

        List<Beneficiario> beneficiarios =
			(List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(final Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
                    query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
					return query.list();
				}
			});
		
		return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<Beneficiario> obtenerRegistrosW8BENE(Date fechaIni, Date fechaFin){
    	log.info("Entrando a findBeneficiariosByFormato()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        
    	sb.append("SELECT ");
    	sb.append("'' AS acount, '' AS client_id, ");
    	sb.append("(CASE WHEN tb.RAZON_SOCIAL_BENEF IS NOT NULL THEN tb.RAZON_SOCIAL_BENEF "); 
    	sb.append("  ELSE tb.NOMBRE_BENEF || ' ' || tb.APELLIDO_PATERNO_BENEF  || ' ' || tb.APELLIDO_MATERNO_BENEF END) AS name, ");
		sb.append("tb.PAIS_INCORPORACION AS org_country, ");
		sb.append("'' AS de_name, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO,4000),'(^|<partIcmp4)([a-n])(>true)',1,1,NULL,2)) AS chapter_3, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO,4000),'(^|<partIcmp5)([a-z])(>true)',1,1,NULL,2)) AS chapter_4, ");
		sb.append("tdw.STREET || ' ' || tdw.OUTER_NUMBER || ' ' || tdw.INTERIOR_NUMBER AS pr_address,  ");
		sb.append("tdw.CITY_TOWN AS pr_city,  ");
		sb.append("tdw.STATE_PROVINCE AS pr_state_prov, ");
		sb.append("tdw.POSTAL_CODE AS pr_zip, ");
		sb.append("tdw.COUNTRY AS pr_country, ");
		sb.append("'' as pr_incareof, ");
		sb.append("'' as pobox, ");
		sb.append("tdmail.STREET || ' ' || tdmail.OUTER_NUMBER || ' ' || tdmail.INTERIOR_NUMBER AS mailing_address,  ");
		sb.append("tdmail.CITY_TOWN AS mail_city,  ");
		sb.append("tdmail.STATE_PROVINCE AS mail_state_prov, ");
		sb.append("tdmail.POSTAL_CODE AS mail_zip, ");
		sb.append("tdmail.COUNTRY AS mail_country, ");
		sb.append("tcfw.US_TIN, tcfw.GIIN, tcfw.FOREING_TIN  AS foreign_tin_identifiying, tcfw.REFERENCE_NUMBER, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp4)([a-e])(>true)',1,1,NULL,2)) AS BRANCH_CHAPTER_4, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12a>)(\\S*)(</partIIcmp12a>)',1,1,NULL,2)) AS branch_address,'' AS branch_city,  ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12b>)(\\S*)(</partIIcmp12b>)',1,1,NULL,2)) AS branch_state_prov, ");
		sb.append("'' AS branch_zip, UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12c>)(\\S*)(</partIIcmp12c>)',1,1,NULL,2)) AS branch_country_cd, ");
		sb.append("'' AS branch_incareof, '' AS branch_pobox, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp13>)(\\S*)(</partIIcmp13>)',1,1,NULL,2)) AS branch_giin, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIIIcmp14a>)(\\S*)(</partIIIcmp14a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line14a, ");
		sb.append("tb.PAIS_INCORPORACION AS treaty_ountry, --'' AS SIGNATURE, '' AS CAPACITY, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIIIcmp14b>)(\\S*)(</partIIIcmp14b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line14b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIIIcmp14c>)(\\S*)(</partIIIcmp14c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line14c, ");
		sb.append("'' AS treaty_provision, '' AS treaty_rate, '' AS income_type, '' AS treaty_explanation, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIVcmp16>)(\\S*)(</partIVcmp16>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line16, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIVcmp16>)(\\S*)(</partIVcmp16a>)',1,1,NULL,2)) AS SPONSOR_NAME_16, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIVcmp17a>)(\\S*)(</partIVcmp17a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line17a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIVcmp17b>)(\\S*)(</partIVcmp17b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line17b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partVcmp18>)(\\S*)(</partVcmp18>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line18, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partVIcmp19>)(\\S*)(</partVIcmp19>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line19, ");
		sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partVIIcmp20>)(\\S*)(</partVIIcmp20>)',1,1,NULL,2)) AS SPONSOR_NAME_20, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partVIIcmp21>)(\\S*)(</partVIIcmp21>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line21, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partVIIIcmp22>)(\\S*)(</partVIIIcmp22>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line22, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partIXcmp23>)(\\S*)(</partIXcmp23>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line23, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXcmp24a>)(\\S*)(</partXcmp24a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line24a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXcmp24b>)(\\S*)(</partXcmp24b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line24b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXcmp24c>)(\\S*)(</partXcmp24c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line24c, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXcmp24d>)(\\S*)(</partXcmp24d>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line24d, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIcmp25a>)(\\S*)(</partXIcmp25a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line25a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIcmp25b>)(\\S*)(</partXIcmp25b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line25b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIcmp25c>)(\\S*)(</partXIcmp25c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line25c, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIIcmp26a)(\\S*)(</partXIIcmp26a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line26a, ");
		sb.append("'' AS IGA_COUNTRY,'' AS IGA_STATUS,'' AS IGA_GIIN, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIIIcmp27>)(\\S*)(</partXIIIcmp27>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line27, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIVcmp28a>)(\\S*)(</partXIVcmp28a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line28a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIVcmp28b>)(\\S*)(</partXIVcmp28b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line28b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29a>)(\\S*)(</partXVcmp29a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29b>)(\\S*)(</partXVcmp29b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29c>)(\\S*)(</partXVcmp29c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29c, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29d>)(\\S*)(</partXVcmp29d>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29d, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29e>)(\\S*)(</partXVcmp29e>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29e, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVcmp29f>)(\\S*)(</partXVcmp29f>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line29f, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVIcmp30>)(\\S*)(</partXVIcmp30>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line30, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVIIcmp31>)(\\S*)(</partXVIIcmp31>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line31, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXVIIIcmp32>)(\\S*)(</partXVIIIcmp32>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line32, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXIXcmp33)([a-b])(\\S*)(</partXIXcmp33a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line33, ");
		sb.append("'' AS STARTUP_DATE, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXcmp34)([a-b])(\\S*)(</partXIXcmp34a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line34, ");
		sb.append("'' AS BANKRUPTCY_DATE, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXIcmp35)([a-b])(\\S*)(</partXXIcmp35a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line35, ");
		sb.append("'' AS DET_LETTER_DATE, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXIIcmp36>)(\\S*)(</partXXIIcmp36>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line36, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXIIIcmp37)([a-b])(\\S*)(</partXXIIIcmp37a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line37a, ");
		sb.append("'' AS SEC_EXCHANGE, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXIIIcmp37)([c-d])(\\S*)(</partXXIIIcmp37c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line37b, ");
		sb.append("'' AS AFFIL_NAME, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXIVcmp38>)(\\S*)(</partXXIVcmp38>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line38, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVcmp39>)(\\S*)(</partXXVcmp39>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line39, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVIcmp40a>)(\\S*)(</partXXVIcmp40a>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line40a, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVIcmp40b>)(\\S*)(</partXXVIcmp40b>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line40b, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVIcmp40c>)(\\S*)(</partXXVIcmp40c>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line40c, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVIIcmp41>)(\\S*)(</partXXVIIcmp41>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line41, ");
		sb.append("'' AS SPONSOR_NAME_42A, ");
		sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO ,4000),'(^|<partXXVIIIcmp43>)(\\S*)(</partXXVIIIcmp43>)',1,1,NULL,2)= 'true' THEN '1' ELSE '' END ) AS line_43, ");
		sb.append("'' AS SIGNATURE, '' AS PRINT_NAME, to_char(tb.FECHA_AUTORIZACION,'MM-dd-yyyy','NLS_DATE_LANGUAGE=english') AS DATE_SIGNED, ");
		sb.append("'' AS CAPACITY, '' AS ACCOUNT_ENTITY_NAME, '' AS PERMANENT_COUNTRY_CODES, '' AS ORGANIZATION_COUNTRY_CODE, '' AS ADDRESS_COUNTRY_CODES, '' AS PHONE_NUMBERS,  ");
		sb.append("'' AS STANDING_INSTRUCTION_COUNTRY_CODES, '' AS IS_US_PERSON, '' AS ACCOUNT_USTIN, '' AS ACCOUNT_FOREIGN_TIN, '' AS PREEXISTING_ACCOUNT, ");
		sb.append("cb.ABREVIACION AS custodio, ins.CLAVE_TIPO_INSTITUCION || ins.FOLIO_INSTITUCION AS id_folio, tb.UOI, tb.ADP ");
		sb.append("FROM ");
		sb.append("T_BENEFICIARIOS tb ");
		sb.append("LEFT JOIN C_CUENTA_NOMBRADA cn ON tb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada ");
		sb.append("RIGHT JOIN C_CATBIC cb ON cb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada ");
		sb.append("JOIN (SELECT ci.ID_INSTITUCION, cti.CLAVE_TIPO_INSTITUCION, ci.FOLIO_INSTITUCION  ");
		sb.append("FROM C_INSTITUCION ci JOIN C_TIPO_INSTITUCION cti ON ci.ID_TIPO_INSTITUCION = cti.ID_TIPO_INSTITUCION) INS ON cn.ID_INSTITUCION = ins.ID_INSTITUCION ");
		sb.append("LEFT JOIN T_CAMPOS_FORMATO_W8BENE tcfw ON tb.ID_CAMPOS_FORMATO_W8BENE  = tcfw.ID_CAMPOS_FORMATO_W8BENE ");
		sb.append("LEFT JOIN T_DOMICILIOS_W tdw ON tb.ID_DOMICILIO_W8_NORMAL  = tdw.ID_DOMICILIO_W   ");
		sb.append("LEFT JOIN T_DOMICILIOS_W tdmail ON tb.ID_DOMICILIO_W8_CORREO = tdmail.ID_DOMICILIO_W   ");
		sb.append("WHERE ");
		sb.append("tb.ID_STATUS_BENEF  = 2 AND TB.TIPO_FORMATO = 'W8BENE2016' ");
		
		if (fechaIni != null) {
	            sb.append("	AND tb.fecha_registro >= ? ");
	            log.debug("Fecha REgistro Final: ["
	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaIni,
	                            true) + "]");
	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
	            		fechaIni, true));
    	}

		if (fechaFin != null) {
	            sb.append("	AND tb.fecha_registro <= ? ");
	            log.debug("Fecha REgistro Final: ["
	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
	                            false) + "]");
	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
	                    false));
        }
		
		sb.append("ORDER BY tb.FECHA_FORMATO DESC ");
		
		List<Object> lstResult = this.jdbcTemplate.queryForList(sb.toString(), paramsSQL.toArray(new Object[0]));
		return null;
    }

    @SuppressWarnings("unchecked")
	public List<Beneficiario> obtenerRegistrosW8IMY(Date fechaIni, Date fechaFin){
    	log.info("Entrando a findBeneficiariosByFormato()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        
    	sb.append("SELECT ");
    	sb.append("'' AS acount_number, '' AS client_id, ");
    	sb.append("'' AS ACCOUNT_ENTITY_NAME, '' AS ACCOUNT_USTIN, '' AS ADDRESS_COUNTRY_CODES, '' AS AFFIL_NAME, '' AS BANKRUPTCY_DATE, "); 
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp4)([a-i])(>true)',1,1,NULL,2)) AS chapter_3, ");
    	sb.append("'' AS branch_zip, ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12a>)(\\S*)(</partIIcmp12a>)',1,1,NULL,2)) AS branch_address, ");
    	sb.append("'' AS branch_city, ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12c>)(\\S*)(</partIIcmp12c>)',1,1,NULL,2)) AS branch_branch_country, ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIIcmp11a)([a-e])(>true)',1,1,NULL,2)) AS branch_chapter_4, ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp13>)(\\S*)(</partIIcmp13>)',1,1,NULL,2)) AS branch_giin, ");
    	sb.append("'' AS branch_incareof, '' AS branch_pobox, ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^<partIIcmp12b>)(\\S*)(</partIIcmp12b>)',1,1,NULL,2)) AS branch_state_prov, ");
    	sb.append("'' as CITIZENSHIP_COUNTRY_CODES, ");
    	sb.append("to_char(tb.FECHA_AUTORIZACION,'MM-dd-yyyy','NLS_DATE_LANGUAGE=english') AS date_signed, '' AS de_name, ");
    	sb.append(" ");
    	sb.append("UPPER(REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp5)([a-z])(>true)',1,1,NULL,2)) AS chapter_4, ");
    	sb.append("'' AS IGA_COUNTRY, '' AS IGA_STATUS, '' AS IGA_TRUSTEE_CLASSIFICATION, '' AS IS_US_PERSON,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIII>)(\\S*)(</partIII>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line14, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIVcmp15a>)(\\S*)(</partIVcmp15a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line15A, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIVcmp15b>)(\\S*)(</partIVcmp15b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line15B, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIVcmp15c>)(\\S*)(</partIVcmp15c>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line15C, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIVcmp15d>)(\\S*)(</partIVcmp15d>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line15D, ");
    	sb.append("''AS line15E, '' AS line15F, '' AS line15G, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVcmp16a>)(\\S*)(</partVcmp16a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line16A, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVIcmp17a>)(\\S*)(</partVIcmp17a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line17A, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVIcmp17b>)(\\S*)(</partVIcmp17b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line17B, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVIcmp17c>)(\\S*)(</partVIcmp17c>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line17C, ");
    	sb.append("'' AS line17D, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVIIcmp18>)(\\S*)(</partVIIcmp18>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line18A, ");
    	sb.append("'' AS line18B, '' AS line18C, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partVIIcmp19>)(\\S*)(</partVIIcmp19>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line19A,  ");
    	sb.append("'' AS line19B, '' AS line19C, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIXcmp20>)(\\S*)(</partIXcmp20>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line20, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXcmp21a>)(\\S*)(</partXcmp21a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line21A, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXcmp21b>)(\\S*)(</partXcmp21b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line21B, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIcmp22a>)(\\S*)(</partXIcmp22a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line22,  ");
    	sb.append("'' as line23B, '' as line23C, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIIIcmp24>)(\\S*)(</partXIIIcmp24>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line24A,  ");
    	sb.append("'' AS line24B, '' AS line24C,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIVcmp25a>)(\\S*)(</partXIVcmp25a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line25, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXVcmp26>)(\\S*)(</partXVcmp26>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line26, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXVIcmp27b>)(\\S*)(</partXVIcmp27b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line27B, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXVIIcmp28>)(\\S*)(</partXVIIcmp28>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line28, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXVIIIcmp29a>)(\\S*)(</partXVIIIcmp29a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line29, ");
    	sb.append("'' AS line30_model1, '' AS line30_model2,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIXcmp30a>)(\\S*)(</partXIXcmp30a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line30A, "); 
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIXcmp30b>)(\\S*)(</partXIXcmp30b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line30B,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXIXcmp30c>)(\\S*)(</partXIXcmp30c>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line30C,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXcmp31>)(\\S*)(</partXXcmp31>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line31,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXIcmp32a>)(\\S*)(</partXXIcmp32a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line32, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXIIcmp33a>)(\\S*)(</partXXIIcmp33a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line33A, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXIIcmp33b>)(\\S*)(</partXXIIcmp33b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line33B, ");
    	sb.append("'' AS line33C, '' AS line33D, '' AS line33E, '' AS line33F,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXIcmp32a>)(\\S*)(</partXXIcmp32a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line34, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXIVcmp35>)(\\S*)(</partXXIVcmp35>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line35, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXVcmp36>)(\\S*)(</partXXVcmp36>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line36, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXVIcmp37>)(\\S*)(</partXXVIcmp37>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line37A, ");
    	sb.append("'' AS line37B,  ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXVIIcmp38>)(\\S*)(</partXXVIIcmp38>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line38, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partXXVIIcmp39>)(\\S*)(</partXXVIIcmp39>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS line39, ");
    	sb.append("'' AS line40, '' AS line42,  ");
    	sb.append("tdmail.CITY_TOWN AS mail_city, tdmail.COUNTRY AS mail_country_cd, tdmail.STATE_PROVINCE AS mail_state_prov, tdmail.POSTAL_CODE AS mail_zip, ");
    	sb.append("tdmail.STREET || ' ' || tdmail.OUTER_NUMBER || ' ' || tdmail.INTERIOR_NUMBER AS mailing_address, ");
    	sb.append("(CASE WHEN tb.RAZON_SOCIAL_BENEF IS NOT NULL THEN tb.RAZON_SOCIAL_BENEF  ");
    	sb.append("ELSE tb.NOMBRE_BENEF || ' ' || tb.APELLIDO_PATERNO_BENEF  || ' ' || tb.APELLIDO_MATERNO_BENEF END) AS name, ");
    	sb.append("tb.PAIS_INCORPORACION AS org_ountry, tcfw.GIIN AS part_i_GIIN, '' AS PERMANENT_COUNTRY_CODES, '' AS PHONE_NUMBERS,  ");
    	sb.append("tdw.STREET || ' ' || tdw.OUTER_NUMBER || ' ' || tdw.INTERIOR_NUMBER AS pr_address, tdw.CITY_TOWN AS pr_city, tdw.COUNTRY AS pr_country,  ");
    	sb.append("'' AS pr_incareof, '' AS pr_pobox, tdw.STATE_PROVINCE AS pr_state_prov, tdw.POSTAL_CODE AS pr_zip,	 ");
    	sb.append("'' as PREEXISTING_ACCOUNT, tcfw.PRINT_NAME, '' AS QDD_CLASSIFICATION, tcfw.REFERENCE_NUMBER AS reference_number, '' AS SEC_EXCHANGE, '' AS signature, '' AS SPONSOR_NAME,  ");
    	sb.append("'' as STANDING_INSTRUCTION_COUNTRY_CODES, '' AS STARTUP_DATE, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp8d>)(\\S*)(</partIcmp8d>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS tin_type_ein, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp8a>)(\\S*)(</partIcmp8a>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS tin_type_qi_ein, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp8e>)(\\S*)(</partIcmp8e>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS tin_type_ssn, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp8b>)(\\S*)(</partIcmp8b>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS tin_type_wp_ein, ");
    	sb.append("(CASE WHEN REGEXP_SUBSTR(DBMS_LOB.SUBSTR(tcfw.CAMPOS_FORMATO, 4000), '(^|<partIcmp8c>)(\\S*)(</partIcmp8c>)',1,1,NULL,2) = 'true' THEN '1' ELSE '' END) AS tin_type_wt_ein,  ");
    	sb.append("tb.PAIS_INCORPORACION AS treaty_country,  ");
    	sb.append("tcfw.US_TIN AS us_tin,  ");
    	sb.append("cb.ABREVIACION AS custodio, ins.CLAVE_TIPO_INSTITUCION || ins.FOLIO_INSTITUCION AS id_folio, tb.UOI, tb.ADP  ");
    	sb.append("FROM  ");
    	sb.append("T_BENEFICIARIOS tb  ");
    	sb.append("LEFT JOIN C_CUENTA_NOMBRADA cn ON tb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada  ");
    	sb.append("RIGHT JOIN C_CATBIC cb ON cb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada ");
    	sb.append("JOIN (SELECT ci.ID_INSTITUCION, cti.CLAVE_TIPO_INSTITUCION, ci.FOLIO_INSTITUCION  ");
    	sb.append("	FROM C_INSTITUCION ci JOIN C_TIPO_INSTITUCION cti ON ci.ID_TIPO_INSTITUCION = cti.ID_TIPO_INSTITUCION) INS ON cn.ID_INSTITUCION = ins.ID_INSTITUCION  ");
    	sb.append("LEFT JOIN T_CAMPOS_FORMATO_W8IMY2015 tcfw ON tb.ID_CAMPOS_FORMATO_W8IMY2015  = tcfw.ID_CAMPOS_FORMATO_W8IMY2015  ");
    	sb.append("LEFT JOIN T_DOMICILIOS_W tdw ON tb.ID_DOMICILIO_W8_NORMAL  = tdw.ID_DOMICILIO_W   ");
    	sb.append("LEFT JOIN T_DOMICILIOS_W tdmail ON tb.ID_DOMICILIO_W8_CORREO = tdmail.ID_DOMICILIO_W");
		sb.append("WHERE ");
		sb.append("tb.ID_STATUS_BENEF  = 2 AND TB.TIPO_FORMATO = 'W8IMY2017' ");
		
		if (fechaIni != null) {
	            sb.append("	AND tb.fecha_registro >= ? ");
	            log.debug("Fecha REgistro Final: ["
	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaIni,
	                            true) + "]");
	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
	            		fechaIni, true));
    	}

		if (fechaFin != null) {
	            sb.append("	AND tb.fecha_registro <= ? ");
	            log.debug("Fecha REgistro Final: ["
	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
	                            false) + "]");
	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
	                    false));
        }
		
		sb.append("ORDER BY tb.FECHA_FORMATO DESC ");
		
		List<Object> lstResult = this.jdbcTemplate.queryForList(sb.toString(), paramsSQL.toArray(new Object[0]));
		return null;
    }
    
    @SuppressWarnings("unchecked")
   	public List<Beneficiario> obtenerRegistrosW9(Date fechaIni, Date fechaFin){
       	log.info("Entrando a findBeneficiariosByFormato()");
           final StringBuilder sb = new StringBuilder();
           final List<Object> paramsSQL = new ArrayList<Object>();
           
       	sb.append("SELECT ");
       	sb.append("(CASE WHEN tb.ID_TIPO_BENEFICIARIO = 4 THEN tb.NOMBRE_BENEF || ' ' || tb.APELLIDO_PATERNO_BENEF  || ' ' || tb.APELLIDO_MATERNO_BENEF  ");
       	sb.append("     WHEN tb.ID_TIPO_BENEFICIARIO = 11 THEN tb.RAZON_SOCIAL_BENEF  ");
       	sb.append("     ELSE '' END) AS name, '' AS de_name, ");
       	sb.append("	 tcfw.TAX_CLASSIFICATION AS FEDERAL_TAX_CLASSIFICATION, tcfw.ID_EXEMPT_PAYEE_CODE  AS exempt_payee_code, tcfw.ID_EXEMPTION_FATCA_CODE AS exempt_fatca_reporting_code, ");
       	sb.append("	 tdw.STREET || ' ' || tdw.OUTER_NUMBER || ' ' || tdw.INTERIOR_NUMBER AS pr_address, tdw.CITY_TOWN AS pr_city, tdw.STATE_PROVINCE AS pr_state_prov, tdw.POSTAL_CODE AS pr_zip, ");
       	sb.append("	 tdw.COUNTRY AS pr_country, '' AS REFERENCE_NUMBER,  ");
       	sb.append("	 tcfw.SSN, tcfw.EMPLOYER_ID_NUMBER AS EIN, '' AS SIGNATURE, to_char(tb.FECHA_AUTORIZACION,'Mm-dd-yyyy','NLS_DATE_LANGUAGE=english') AS date_signed, ");
       	sb.append("	 cb.ABREVIACION AS custodio, ins.CLAVE_TIPO_INSTITUCION || ins.FOLIO_INSTITUCION AS id_folio, tb.UOI, tb.ADP ");
       	sb.append("FROM  ");
       	sb.append("	T_BENEFICIARIOS tb join T_DOMICILIOS_W tdw ON tb.ID_DOMICILIO_W9 = tdw.ID_DOMICILIO_W ");
       	sb.append("	LEFT JOIN T_CAMPOS_FORMATO_W9 tcfw ON tb.ID_CAMPOS_FORMATO_W9 = tcfw.ID_CAMPOS_FORMATO_W9 ");
       	sb.append("	LEFT JOIN C_CUENTA_NOMBRADA cn ON tb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada  ");
       	sb.append("	RIGHT JOIN C_CATBIC cb ON cb.ID_CUENTA_NOMBRADA = cn.id_cuenta_nombrada ");
       	sb.append("	JOIN (SELECT ci.ID_INSTITUCION, cti.CLAVE_TIPO_INSTITUCION, ci.FOLIO_INSTITUCION  ");
       	sb.append("				FROM C_INSTITUCION ci JOIN C_TIPO_INSTITUCION cti ON ci.ID_TIPO_INSTITUCION = cti.ID_TIPO_INSTITUCION) INS ON cn.ID_INSTITUCION = ins.ID_INSTITUCION ");
   		sb.append("WHERE ");
   		sb.append("tb.ID_STATUS_BENEF  = 2 AND TB.TIPO_FORMATO = 'W9' ");
   		
   		if (fechaIni != null) {
   	            sb.append("	AND tb.fecha_registro >= ? ");
   	            log.debug("Fecha REgistro Final: ["
   	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaIni,
   	                            true) + "]");
   	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
   	            		fechaIni, true));
       	}

   		if (fechaFin != null) {
   	            sb.append("	AND tb.fecha_registro <= ? ");
   	            log.debug("Fecha REgistro Final: ["
   	                    + DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
   	                            false) + "]");
   	            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(fechaFin,
   	                    false));
           }
   		
   		sb.append("ORDER BY tb.FECHA_FORMATO DESC ");
   		
   		List<Object> lstResult = this.jdbcTemplate.queryForList(sb.toString(), paramsSQL.toArray(new Object[0]));
   		return null;
    }
    
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
