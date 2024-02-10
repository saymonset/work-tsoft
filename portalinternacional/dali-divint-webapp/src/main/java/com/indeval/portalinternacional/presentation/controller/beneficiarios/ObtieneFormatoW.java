package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.services.util.W8BENEUtil;
import com.indeval.portalinternacional.middleware.services.util.W8IMY2015Util;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Servlet implementation class ObtieneFormatoW8BEN
 */
public class ObtieneFormatoW extends HttpServlet implements Constantes {
    private static final long serialVersionUID = 1L;
    private static final String CHECKBOX_CHECKED = "Yes";
    private static final String CHECKBOX_UNCHECKED = "No";

    @EJB(mappedName="java:global/dali_divint_services/dali_divint_services_ejb/ejb.controlBeneficiariosService!com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService")
    private ControlBeneficiariosService controlBeneficiariosService;

    private static final Logger logger = LoggerFactory.getLogger(ObtieneFormatoW.class);

    private final Map<String, String> mapeoFormatoW8BEN;
    private final Map<Integer, String> mapeoField3W8BEN;
    private final Map<String, String> mapeoFormatoW8IMY;
    private final Map<Integer, String> mapeoField3W8IMY;
    private final Map<Integer, String> mapeoUSIdNumberW8IMY;
    private final Map<String, String> mapeoFormatoW9;
    private final Map<Integer, String> mapeoField3W9;
    private final Map<Integer, Integer> correlacionField3W9;
    private final Map<String, String> mapeoFormatoW8BEN2014;
    private final Map<String, String> mapeoFormatoW8BENE;
    private final Map<String, String> mapeoFormatoW8BENE2016;
    private final Map<String, String> mapeoFormatoW8Imy2015;
    private final FormatoW8Service formatoW8Service;
    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
    private final HashMap<String, String> mapeoFormatoW8BEN2017;

    /**
     * @see HttpServlet#HttpServlet()
     */
    @SuppressWarnings("unchecked")
    public ObtieneFormatoW() {
        super();
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        this.mapeoFormatoW8BEN =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8BEN");
        this.mapeoField3W8BEN =
                (HashMap<Integer, String>) applicationContext.getBean("mapeoField3W8BEN");
        this.mapeoFormatoW8IMY =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8IMY");
        this.mapeoField3W8IMY =
                (HashMap<Integer, String>) applicationContext.getBean("mapeoField3W8IMY");
        this.mapeoUSIdNumberW8IMY =
                (HashMap<Integer, String>) applicationContext.getBean("mapeoUSIdNumberW8IMY");
        this.mapeoFormatoW9 =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW9");
        this.mapeoField3W9 = (HashMap<Integer, String>) applicationContext.getBean("mapeoField3W9");
        this.correlacionField3W9 =
                (HashMap<Integer, Integer>) applicationContext.getBean("correlacionField3W9");
        this.mapeoFormatoW8BEN2014 =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8BEN2014");
        this.mapeoFormatoW8BEN2017 =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8BEN2017");
        this.mapeoFormatoW8BENE =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8BENE");
        this.mapeoFormatoW8Imy2015 =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8Imy2015");
        this.formatoW8Service = (FormatoW8Service) applicationContext.getBean("formatoW8Service");
        this.mapeoFormatoW8BENE2016 =
                (HashMap<String, String>) applicationContext.getBean("mapeoFormatoW8BENE2016");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = null;
        try{
            out = response.getOutputStream();        	
        } catch(Exception ex){
        	logger.error(ex.getMessage());
        }


        if (this.controlBeneficiariosService == null) {
            logger.error("No se puede obtener el servicio");
            return;
        }
        String idBeneficiario = request.getParameter("idBeneficiario");

        if (StringUtils.isNotBlank(idBeneficiario) && StringUtils.isNumeric(idBeneficiario)) {
            Long id = null;
            Beneficiario beneficiario = null;
            try {
            	id = Long.valueOf(idBeneficiario);
                beneficiario =
                        this.controlBeneficiariosService.consultaBeneficiarioByIdEliminados(id);
            } catch (Exception e) {
                logger.error("Error al obtener Beneficiario", e);
                return;
            }
            if (beneficiario != null) {
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + beneficiario.getTipoFormato() + "-"
                                + beneficiario.getUoiNumber() + ".pdf");
                response.setContentType("application/pdf");

                try {
                    if (StringUtils.isNotBlank(beneficiario.getTipoFormato())) {
                        if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN")) {
                            this.procesaFormatoW8BEN(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8IMY")) {
                            this.procesaFormatoW8IMY(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W9")) {
                            this.procesaFormatoW9(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN2014")) {
                            this.procesaFormatoW8BEN2014(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN2017")) {
                            this.procesaFormatoW8BEN2017(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BENE")) {
                            this.procesaFormatoW8BENE(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8IMY2015")) {
                            this.procesaFormatoW8Imy2015(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8IMY2017")) {
                            this.procesaFormatoW8Imy2017(beneficiario, out);
                        } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BENE2016")) {
                            this.procesaFormatoW8BENE2016(beneficiario, out);
                        } else {
                            logger.error("Nombre de Formato Invalido: ["
                                    + beneficiario.getIdBeneficiario() + "- "
                                    + beneficiario.getIdBeneficiario() + "]");
                        }
                    } else {
                        logger.error("Formato Invalido: [" + beneficiario.getIdBeneficiario() + "]");
                    }
                } catch (Exception e) {
                    logger.error(
                            "Error al generar el PDF para el beneficiario: ["
                                    + beneficiario.getIdBeneficiario() + "]", e);
                }
            }
        } else {
            logger.error("Error al obtener el id del Beneficiario: [" + idBeneficiario + "]");
        }


    }

    private void procesaFormatoW8Imy2017(final Beneficiario beneficiario, final OutputStream out) {
        // TODO Auto-generated method stub

    }

    private void procesaFormatoW8BEN(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {
        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8ben.pdf");
        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            this.procesaMapaCamposCadena(beneficiario, campos, this.mapeoFormatoW8BEN);

            String field10Reasons = null;
            try {
                field10Reasons =
                        BeanUtils.getNestedProperty(beneficiario, "formatoW8BEN.field10Reasons");
            } catch (Exception e) {
                field10Reasons = null;
                logger.error("LLave no encontrada en Beneficiario: [formatoW8BEN.field10Reasons]");
            }
            if (StringUtils.isNotBlank(field10Reasons)) {
                int longitud = field10Reasons.length();
                int limite1 = 40, limite2 = 130, limite3 = 220;
                if (longitud >= limite1) {
                    campos.setField("f1-13", field10Reasons.substring(0, limite1));
                    if (longitud >= limite2) {
                        campos.setField("f1-16", field10Reasons.substring(limite1, limite2));
                        if (longitud >= limite3) {
                            campos.setField("f1-17", field10Reasons.substring(limite2, limite3));
                        } else {
                            campos.setField("f1-17", field10Reasons.substring(limite2));
                        }
                    } else {
                        campos.setField("f1-16", field10Reasons.substring(limite1));
                    }
                } else {
                    campos.setField("f1-13", field10Reasons);
                }
            } else {
                logger.error("Valor nulo: [formatoW8BEN.field10Reasons]");
            }

            this.procesaMapaCamposEntero(beneficiario, campos, this.mapeoField3W8BEN,
                    "formatoW8BEN.field3.idCampo");

            stamp1.close();
            reader.close();
        }
    }

    private void procesaFormatoW8IMY(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {

        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8imy.pdf");
        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            this.procesaMapaCamposCadena(beneficiario, campos, this.mapeoFormatoW8IMY);

            this.procesaMapaCamposEntero(beneficiario, campos, this.mapeoField3W8IMY,
                    "formatoW8IMY.field3.idCampo");

            this.procesaMapaCamposEntero(beneficiario, campos, this.mapeoUSIdNumberW8IMY,
                    "formatoW8IMY.tipoTaxIdNumb");

            String field9bLine = null;
            try {
                field9bLine =
                        BeanUtils.getNestedProperty(beneficiario, "formatoW8IMY.field9OptionBLine");
            } catch (Exception e) {
                field9bLine = null;
                logger.error("LLave no encontrada en Beneficiario: [formatoW8IMY.field9OptionBLine]");
            }
            if (StringUtils.isNotBlank(field9bLine)) {
                int longitud = field9bLine.length();
                int limite1 = 45, limite2 = 115;
                if (longitud >= limite1) {
                    campos.setField("f1-11", field9bLine.substring(0, limite1));
                    if (longitud >= limite2) {
                        campos.setField("f1-12", field9bLine.substring(limite1, limite2));
                    } else {
                        campos.setField("f1-12", field9bLine.substring(limite1));
                    }
                } else {
                    campos.setField("f1-11", field9bLine);
                }
            } else {
                logger.error("Valor nulo: [formatoW8IMY.field9OptionBLine]");
            }

            String field9cLine = null;
            try {
                field9cLine =
                        BeanUtils.getNestedProperty(beneficiario, "formatoW8IMY.field9OptionCLine");
            } catch (Exception e) {
                field9cLine = null;
                logger.error("LLave no encontrada en Beneficiario: [formatoW8IMY.field9OptionCLine]");
            }
            if (StringUtils.isNotBlank(field9cLine)) {
                int longitud = field9cLine.length();
                int limite1 = 8, limite2 = 75;
                if (longitud >= limite1) {
                    campos.setField("f1-13", field9cLine.substring(0, limite1));
                    if (longitud >= limite2) {
                        campos.setField("f1-14", field9cLine.substring(limite1, limite2));
                    } else {
                        campos.setField("f1-14", field9cLine.substring(limite1));
                    }
                } else {
                    campos.setField("f1-13", field9cLine);
                }
            } else {
                logger.error("Valor nulo: [formatoW8IMY.field9OptionCLine]");
            }

            stamp1.close();
            reader.close();
        }
    }

    private void procesaFormatoW9(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {

        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw9.pdf");
        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            this.procesaMapaCamposCadena(beneficiario, campos, this.mapeoFormatoW9);

            this.procesaMapaCamposEntero(beneficiario, campos, this.mapeoField3W9,
                    "formatoW9.typeTaxPayer.idCampo", this.correlacionField3W9);

            if (beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == 4) {
                if (StringUtils.isNotBlank(beneficiario.getFormatoW9().getSsn())) {
                    String ssnList[] = beneficiario.getFormatoW9().getSsn().split("-");
                    if (ssnList != null && ssnList.length == 3) {
                        campos.setField(Constantes.SOCIAL_NUMBER1, ssnList[0]);
                        campos.setField(Constantes.SOCIAL_NUMBER2, ssnList[1]);
                        campos.setField(Constantes.SOCIAL_NUMBER3, ssnList[2]);
                    } else {
                        logger.error("Error al parsear el ssn para persona fisica de EUA");
                    }
                } else {
                    logger.error("Error al obtener el ssn para persona fisica de EUA");
                }
            } else {
                if (StringUtils.isNotBlank(beneficiario.getFormatoW9().getEmployerIdNumb())) {
                    String einList[] = beneficiario.getFormatoW9().getEmployerIdNumb().split("-");
                    if (einList != null && einList.length == 2) {
                        campos.setField(Constantes.EMPLOYER_IDENTIFI1, einList[0]);
                        campos.setField(Constantes.EMPLOYER_IDENTIFI2, einList[1]);
                    } else {
                        logger.error("Error al parsear el ein para persona fisica de EUA");
                    }
                } else {
                    logger.error("Error al obtener el ein para persona fisica de EUA");
                }
            }

            stamp1.close();
            reader.close();
        }
    }

    private void procesaMapaCamposCadena(final Beneficiario beneficiario, final AcroFields campos,
            final Map<String, String> mapeoCampos) throws Exception {
        Set<String> llaves = mapeoCampos.keySet();
        for (String llave : llaves) {
            String idCampoPdf = mapeoCampos.get(llave);
            String valor = null;
            try {
                valor = BeanUtils.getNestedProperty(beneficiario, llave);
            } catch (Exception e) {
                valor = null;
                logger.debug("LLave no encontrada en Beneficiario: [" + llave + "]");
                continue;
            }

            if (StringUtils.isNotBlank(valor)) {
                if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_TEXT) {
                    campos.setField(idCampoPdf, valor);
                } else if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_CHECKBOX) {
                    if (valor.equalsIgnoreCase("true")) {
                        if (idCampoPdf.equals(Constantes.FORMATOW9_EXEMPTPAYEE)) {
                            campos.setField(idCampoPdf, Constantes.FORMATOW9_EXEMPTPAYEE_VALUE);
                        } else {
                            campos.setField(idCampoPdf, "Yes");
                        }
                    } else {
                        campos.setField(idCampoPdf, "No");
                    }
                }
            } else {
                logger.debug("Valor nulo: [" + llave + "]");
            }
        }
    }

    private void procesaFormatoW8BEN2014(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {

        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8ben2014.pdf");
        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            this.procesaMapaCamposCadena(beneficiario, campos, this.mapeoFormatoW8BEN2014);

            String field10Reasons = null;
            try {
                field10Reasons =
                        BeanUtils.getNestedProperty(beneficiario, "formatoW8BEN.field10Reasons");
            } catch (Exception e) {
                field10Reasons = null;
                logger.error("LLave no encontrada en Beneficiario: [formatoW8BEN.field10Reasons]");
            }

            if (beneficiario.getFormatoW8BEN().getFechaNacimiento() != null) {
                campos.setField("topmostSubform[0].Page1[0].p1-t12[0]",
                        this.df.format(beneficiario.getFormatoW8BEN().getFechaNacimiento()));
            }

            if (StringUtils.isNotBlank(field10Reasons)) {
                int longitud = field10Reasons.length();
                int limite1 = 40, limite2 = 130, limite3 = 220;
                if (longitud >= limite1) {
                    campos.setField("topmostSubform[0].Page1[0].p1-t18[0]",
                            field10Reasons.substring(0, limite1));
                    if (longitud >= limite2) {
                        campos.setField("topmostSubform[0].Page1[0].p1-t19[0]",
                                field10Reasons.substring(limite1, limite2));
                        if (longitud >= limite3) {
                            campos.setField("topmostSubform[0].Page1[0].p1-t20[0]",
                                    field10Reasons.substring(limite2, limite3));
                        } else {
                            campos.setField("topmostSubform[0].Page1[0].p1-t20[0]",
                                    field10Reasons.substring(limite2));
                        }
                    } else {
                        campos.setField("topmostSubform[0].Page1[0].p1-t19[0]",
                                field10Reasons.substring(limite1));
                    }
                } else {
                    campos.setField("topmostSubform[0].Page1[0].p1-t18[0]", field10Reasons);
                }
            } else {
                logger.error("Valor nulo: [formatoW8BEN.field10Reasons]");
            }

            // procesaMapaCamposEntero(beneficiario, campos, mapeoField3W8BEN,
            // "formatoW8BEN.field3.idCampo");

            stamp1.close();
            reader.close();
        }
    }

    private void procesaFormatoW8BEN2017(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {


        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8ben2017.pdf");

        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            this.procesaMapaCamposCadena(beneficiario, campos, this.mapeoFormatoW8BEN2017);

            String field10Reasons = null;
            try {
                field10Reasons =
                        BeanUtils.getNestedProperty(beneficiario, "formatoW8BEN.field10Reasons");
            } catch (Exception e) {
                field10Reasons = null;
                logger.error("LLave no encontrada en Beneficiario: [formatoW8BEN.field10Reasons]");
            }

            if (beneficiario.getFormatoW8BEN().getFechaNacimiento() != null) {
                campos.setField("topmostSubform[0].Page1[0].p1-t12[0]",
                        this.df.format(beneficiario.getFormatoW8BEN().getFechaNacimiento()));
            }

            if (StringUtils.isNotBlank(field10Reasons)) {
                int longitud = field10Reasons.length();
                int limite1 = 9, limite2 = 100, limite3 = 200;
                if (longitud >= limite1) {
                    campos.setField("topmostSubform[0].Page1[0].p1-t18[0]",
                            field10Reasons.substring(0, limite1));
                    if (longitud >= limite2) {
                        campos.setField("topmostSubform[0].Page1[0].p1-t19[0]",
                                field10Reasons.substring(limite1, limite2));
                        if (longitud >= limite3) {
                            campos.setField("topmostSubform[0].Page1[0].p1-t20[0]",
                                    field10Reasons.substring(limite2, limite3));
                        } else {
                            campos.setField("topmostSubform[0].Page1[0].p1-t20[0]",
                                    field10Reasons.substring(limite2));
                        }
                    } else {
                        campos.setField("topmostSubform[0].Page1[0].p1-t19[0]",
                                field10Reasons.substring(limite1));
                    }
                } else {
                    campos.setField("topmostSubform[0].Page1[0].p1-t18[0]", field10Reasons);
                }
            } else {
                logger.error("Valor nulo: [formatoW8BEN.field10Reasons]");
            }

            stamp1.close();
            reader.close();
        }
    }

    /**
     * Genera formato PDF para W8 BENE
     * 
     * @param beneficiario
     * @param out
     * @throws Exception
     */
    private void procesaFormatoW8BENE(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {
        FormaW8BENE forma =
                this.formatoW8Service.obtenerCamposFormato(beneficiario.getFormatoW8BENE()
                        .getCamposFormato());
        forma.construyeBean(beneficiario, false);

        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8bene.pdf");

        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            String idCampoPdf = null;
            String valor = null;
            for (String key : this.mapeoFormatoW8BENE.keySet()) {
                idCampoPdf = this.mapeoFormatoW8BENE.get(key);
                valor = BeanUtils.getNestedProperty(forma, key);

                if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_TEXT) {
                    campos.setField(idCampoPdf, valor);
                } else if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_CHECKBOX) {
                    if (valor.equalsIgnoreCase("true")) {
                        campos.setField(idCampoPdf, CHECKBOX_CHECKED);
                    } else {
                        campos.setField(idCampoPdf, CHECKBOX_UNCHECKED);
                    }
                }
            }

            // Parte I-4
            String checkVal = null;
            for (String key : W8BENEUtil.w8BeneParteI4Campos.keySet()) {
                checkVal = BeanUtils.getNestedProperty(forma, key);
                if (checkVal.equalsIgnoreCase("true")) {
                    campos.setField("c1_001", W8BENEUtil.w8BeneParteI4Campos.get(key));
                    campos.setField(W8BENEUtil.w8BeneParteI4Valores.get(key),
                            W8BENEUtil.w8BeneParteI4Campos.get(key));
                    break;
                }
            }

            if (forma.isPartIcmp4l()) {
                campos.setField("c1_002", CHECKBOX_CHECKED);
                campos.setField("topmostSubform[0].Page1[0].c1_002[0]", CHECKBOX_CHECKED);
            }

            if (forma.isPartIcmp4m()) {
                campos.setField("c1_002", CHECKBOX_UNCHECKED);
                campos.setField("topmostSubform[0].Page1[0].c1_002[1]", CHECKBOX_UNCHECKED);
            }

            // Parte I-5
            checkVal = null;
            for (String key : W8BENEUtil.w8BeneParteI5Campos.keySet()) {
                checkVal = BeanUtils.getNestedProperty(forma, key);
                if (checkVal.equalsIgnoreCase("true")) {
                    campos.setField("c1_003", W8BENEUtil.w8BeneParteI5Campos.get(key));
                    campos.setField(W8BENEUtil.w8BeneParteI5Valores.get(key),
                            W8BENEUtil.w8BeneParteI5Campos.get(key));
                    break;
                }
            }

            String esGiin = forma.isPartIcmp9a1() ? "1" : "";
            campos.setField("c1_004", esGiin);

            String esTin = forma.isPartIcmp9b1() ? "2" : "";
            campos.setField("c1_005", esTin);

            // Parte II
            String campoII11a = forma.isPartIIcmp11a() ? "1" : "";
            campos.setField("c2_001", campoII11a);

            String campoII11d = forma.isPartIIcmp11d() ? "3" : "";
            campos.setField("c2_002", campoII11d);

            String campoII11b = forma.isPartIIcmp11b() ? "2" : "";
            campos.setField("c2_003", campoII11b);

            String campoII11e = forma.isPartIIcmp11e() ? "4" : "";
            campos.setField("c2_004", campoII11e);

            String campoII11c = forma.isPartIIcmp11c() ? "2" : "";
            campos.setField("c2_005", campoII11c);

            // Parte IV
            campos.setField("topmostSubform[0].Page2[0].f2_011[0]", forma.getPartIVcmp16());

            String campoIV17a = forma.isPartIVcmp17a() ? "1" : "";
            String campoIV17b = forma.isPartIVcmp17b() ? "2" : "";

            campos.setField("topmostSubform[0].Page2[0].c2_009[0]", campoIV17a);
            campos.setField("topmostSubform[0].Page2[0].c2_010[0]", campoIV17b);

            // Parte X
            String campoX24a = forma.isPartXcmp24a() ? CHECKBOX_CHECKED : CHECKBOX_UNCHECKED;
            campos.setField("topmostSubform[0].Page3[0].c3_005[0]", campoX24a);

            String campoX24b = forma.isPartXcmp24b() ? "1" : "";
            campos.setField("topmostSubform[0].Page4[0].c4_001[0]", campoX24b);

            String campoX24c = forma.isPartXcmp24c() ? "2" : "";
            campos.setField("topmostSubform[0].Page4[0].c4_002[0]", campoX24c);

            String campoX24d = forma.isPartXcmp24d() ? "2" : "";
            campos.setField("topmostSubform[0].Page4[0].c4_003[0]", campoX24d);

            // Parte XI
            String campoXI25a = forma.isPartXIcmp25a() ? CHECKBOX_CHECKED : CHECKBOX_UNCHECKED;
            campos.setField("topmostSubform[0].Page4[0].c4_004[0]", campoXI25a);

            String campoXI25b = forma.isPartXIcmp25b() ? "1" : "";
            campos.setField("topmostSubform[0].Page4[0].c4_005[0]", campoXI25b);

            String campoXI25c = forma.isPartXIcmp25c() ? "2" : "";
            campos.setField("topmostSubform[0].Page4[0].c4_006[0]", campoXI25c);

            // Parte XIV
            String campoXIV28a = forma.isPartXIVcmp28a() ? "1" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_002[0]", campoXIV28a);

            String campoXIV28b = forma.isPartXIVcmp28b() ? "2" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_003[0]", campoXIV28b);

            // Parte XV
            String campoXV29a = forma.isPartXVcmp29a() ? "1" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_004[0]", campoXV29a);

            String campoXV29b = forma.isPartXVcmp29b() ? "2" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_005[0]", campoXV29b);

            String campoXV29c = forma.isPartXVcmp29c() ? "3" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_006[0]", campoXV29c);

            String campoXV29d = forma.isPartXVcmp29d() ? "4" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_007[0]", campoXV29d);

            String campoXV29e = forma.isPartXVcmp29e() ? "5" : "";
            campos.setField("topmostSubform[0].Page5[0].c5_008[0]", campoXV29e);

            String campoXV29f = forma.isPartXVcmp29f() ? "6" : "";
            campos.setField("topmostSubform[0].Page6[0].c5_009[0]", campoXV29f);

            // Parte XXIII
            String campoXXIII37a = forma.isPartXXIIIcmp37a() ? "1" : "";
            campos.setField("topmostSubform[0].Page7[0].c7_001[0]", campoXXIII37a);

            String campoXXIII37c = forma.isPartXXIIIcmp37c() ? "2" : "";
            campos.setField("topmostSubform[0].Page7[0].c7_002[0]", campoXXIII37c);

            campos.setField("topmostSubform[0].Page7[0].f7_001[0]", forma.getPartXXIIIcmp37b());
            campos.setField("topmostSubform[0].Page7[0].f7_002[0]", forma.getPartXXIIIcmp37d());
            campos.setField("topmostSubform[0].Page7[0].f7_003[0]", forma.getPartXXIIIcmp37e());

            // Parte XXVI
            String campoXXVI40a = forma.isPartXXVIcmp40a() ? CHECKBOX_CHECKED : CHECKBOX_UNCHECKED;
            campos.setField("topmostSubform[0].Page7[0].c7_005[0]", campoXXVI40a);

            String campoXXVI40b = forma.isPartXXVIcmp40b() ? "1" : "";
            campos.setField("topmostSubform[0].Page7[0].c7_006[0]", campoXXVI40b);

            String campoXXVI40c = forma.isPartXXVIcmp40c() ? "2" : "";
            campos.setField("topmostSubform[0].Page7[0].c7_007[0]", campoXXVI40c);

            // Parte XXIX
            String campoXXIX = forma.isPartXXIX() ? "2" : "";
            campos.setField("topmostSubform[0].Page8[0].c8_001[0]", campoXXIX);

            stamp1.close();
            reader.close();
        }
    }

    /**
     * Genera formato PDF para W8 BENE
     * 
     * @param beneficiario
     * @param out
     * @throws Exception
     */
    private void procesaFormatoW8BENE2016(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {
        FormaW8BENE forma =
                this.formatoW8Service.obtenerCamposFormato(beneficiario.getFormatoW8BENE()
                        .getCamposFormato());
        forma.construyeBean(beneficiario, false);

        InputStream inputStream =
                ObtieneFormatoW.class.getResourceAsStream("/util/fw8bene2016.pdf");

        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            String field = null;
            String valor = null;
            for (String key : this.mapeoFormatoW8BENE2016.keySet()) {
                field = this.mapeoFormatoW8BENE2016.get(key);
                valor = BeanUtils.getNestedProperty(forma, key);
                // Viene como field:valor
                String[] fieldVal = field.split(":");

                if (campos.getFieldType(fieldVal[0]) == AcroFields.FIELD_TYPE_TEXT) {
                    campos.setField(fieldVal[0], valor);
                } else if (campos.getFieldType(fieldVal[0]) == AcroFields.FIELD_TYPE_CHECKBOX) {
                    if (valor.equalsIgnoreCase("true")) {
                        campos.setField(fieldVal[0], fieldVal[1]);
                    }
                }
            }
            stamp1.close();
            reader.close();
        }
    }


    /**
     * @param formato
     * @param out
     * @throws Exception
     */
    private void procesaFormatoW8Imy2015(final Beneficiario beneficiario, final OutputStream out)
            throws Exception {
        FormaW8IMY2015 forma =
                this.formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario
                        .getFormatoW8IMY2015().getCamposFormato());
        forma.construyeBean(beneficiario, false);

        InputStream inputStream = ObtieneFormatoW.class.getResourceAsStream("/util/fw8imy2015.pdf");

        if (inputStream == null) {
            logger.error("Error al obtener el archivo");
            return;
        }
        PdfReader reader = new PdfReader(inputStream);
        if (reader != null) {
            PdfStamper stamp1 = new PdfStamper(reader, out);
            AcroFields campos = stamp1.getAcroFields();

            String idCampoPdf = null;
            String valor = null;
            for (String key : this.mapeoFormatoW8Imy2015.keySet()) {
                idCampoPdf = this.mapeoFormatoW8Imy2015.get(key);
                valor = BeanUtils.getNestedProperty(forma, key);

                if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_TEXT) {
                    campos.setField(idCampoPdf, valor);
                } else if (campos.getFieldType(idCampoPdf) == AcroFields.FIELD_TYPE_CHECKBOX) {
                    if (valor.equalsIgnoreCase("true")) {
                        campos.setField(idCampoPdf, CHECKBOX_CHECKED);
                    } else {
                        campos.setField(idCampoPdf, CHECKBOX_UNCHECKED);
                    }
                }
            }

            // Parte I-4
            String checkVal = null;
            for (String key : W8IMY2015Util.w8ImyParteI4Campos.keySet()) {
                checkVal = BeanUtils.getNestedProperty(forma, key);
                if (checkVal.equalsIgnoreCase("true")) {
                    campos.setField(W8IMY2015Util.w8ImyParteI4Valores.get(key),
                            W8IMY2015Util.w8ImyParteI4Campos.get(key));
                }
            }

            // Parte I-5
            checkVal = null;
            for (String key : W8IMY2015Util.w8ImyParteI5Campos.keySet()) {
                checkVal = BeanUtils.getNestedProperty(forma, key);
                if (checkVal.equalsIgnoreCase("true")) {
                    campos.setField(W8IMY2015Util.w8ImyParteI5Valores.get(key),
                            W8IMY2015Util.w8ImyParteI5Campos.get(key));
                }
            }

            // Parte I-8
            if (forma.isPartIcmp8a()) {
                campos.setField("topmostSubform[0].Page1[0].c1_035[0]", "2");
            }
            if (forma.isPartIcmp8b()) {
                campos.setField("topmostSubform[0].Page1[0].c1_036[0]", "3");
            }
            if (forma.isPartIcmp8c()) {
                campos.setField("topmostSubform[0].Page1[0].c1_037[0]", "4");
            }
            if (forma.isPartIcmp8d()) {
                campos.setField("topmostSubform[0].Page1[0].c1_038[0]", "4");
            }
            if (forma.isPartIcmp8e()) {
                campos.setField("topmostSubform[0].Page1[0].c1_039[0]", "5");
            }

            // Parte II

            if (forma.isPartIIcmp11a()) {
                campos.setField("topmostSubform[0].Page2[0].PartII[0].c2_001[0]", "2");
            }
            if (forma.isPartIIcmp11b()) {
                campos.setField("topmostSubform[0].Page2[0].PartII[0].c2_003[0]", "3");
            }
            if (forma.isPartIIcmp11c()) {
                campos.setField("topmostSubform[0].Page2[0].PartII[0].c2_005[0]", "3");
            }
            if (forma.isPartIIcmp11d()) {
                campos.setField("topmostSubform[0].Page2[0].c2_002[0]", "2");
            }
            if (forma.isPartIIcmp11e()) {
                campos.setField("topmostSubform[0].Page2[0].c2_004[0]", "3");
            }

            // Parte III
            if (forma.isPartIIIcmp14a()) {
                campos.setField("topmostSubform[0].Page2[0].c2_006[0]", "1");
            }
            if (forma.isPartIIIcmp14b()) {
                campos.setField("topmostSubform[0].Page2[0].c2_007[0]", "1");
            }
            if (forma.isPartIIIcmp14c()) {
                campos.setField("topmostSubform[0].Page2[0].c2_008[0]", "2");
            }
            if (forma.isPartIIIcmp14d()) {
                campos.setField("topmostSubform[0].Page2[0].c2_009[0]", "2");
            }
            if (forma.isPartIIIcmp14e()) {
                campos.setField("topmostSubform[0].Page2[0].c2_010[0]", "2");
            }
            if (forma.isPartIIIcmp14ei()) {
                campos.setField("topmostSubform[0].Page2[0].c2_011[0]", "2");
            }
            if (forma.isPartIIIcmp14eii()) {
                campos.setField("topmostSubform[0].Page2[0].c2_012[0]", "2");
            }
            if (forma.isPartIIIcmp14f()) {
                campos.setField("topmostSubform[0].Page2[0].c2_013[0]", "2");
            }

            // Parte IV
            if (forma.isPartIVcmp15a()) {
                campos.setField("topmostSubform[0].Page2[0].c2_014[0]", "1");
            }
            if (forma.isPartIVcmp15b()) {
                campos.setField("topmostSubform[0].Page2[0].c2_015[0]", "1");
            }
            if (forma.isPartIVcmp15c()) {
                campos.setField("topmostSubform[0].Page2[0].c2_016[0]", "2");
            }
            if (forma.isPartIVcmp15d()) {
                campos.setField("topmostSubform[0].Page2[0].c2_017[0]", "2");
            }

            // Parte V
            if (forma.isPartVcmp16a()) {
                campos.setField("topmostSubform[0].Page3[0].c3_001[0]", "1");
            }
            if (forma.isPartVcmp16b()) {
                campos.setField("topmostSubform[0].Page3[0].c3_002[0]", "1");
            }
            if (forma.isPartVcmp16c()) {
                campos.setField("topmostSubform[0].Page3[0].c3_003[0]", "2");
            }

            // Parte VI
            if (forma.isPartVIcmp17a()) {
                campos.setField("topmostSubform[0].Page3[0].c3_004[0]", "1");
            }
            if (forma.isPartVIcmp17b()) {
                campos.setField("topmostSubform[0].Page3[0].c3_005[0]", "1");
            }
            if (forma.isPartVIcmp17c()) {
                campos.setField("topmostSubform[0].Page3[0].c3_006[0]", "2");
            }

            // Parte VII
            if (forma.isPartVIIcmp18()) {
                campos.setField("topmostSubform[0].Page3[0].c3_007[0]", "1");
            }

            // Parte VIII
            if (forma.isPartVIIIcmp19()) {
                campos.setField("topmostSubform[0].Page3[0].c3_008[0]", "1");
            }

            // Parte IX
            if (forma.isPartIXcmp20()) {
                campos.setField("topmostSubform[0].Page3[0].c3_009[0]", "1");
            }

            // Parte X
            if (forma.isPartXcmp21b()) {
                campos.setField("topmostSubform[0].Page3[0].c3_010[0]", "1");
            }
            if (forma.isPartXcmp21c()) {
                campos.setField("topmostSubform[0].Page3[0].c3_011[0]", "1");
            }

            // Parte XI
            if (forma.isPartXIcmp22a()) {
                campos.setField("topmostSubform[0].Page4[0].c3_001[0]", "1");
            }
            if (forma.isPartXIcmp22b()) {
                campos.setField("topmostSubform[0].Page4[0].c4_002[0]", "1");
            }
            if (forma.isPartXIcmp22c()) {
                campos.setField("topmostSubform[0].Page4[0].c4_003[0]", "2");
            }

            // Parte XII
            if (forma.isPartXIIcmp23()) {
                campos.setField("topmostSubform[0].Page4[0].c4_004[0]", "2");
            }

            // Parte XIII
            if (forma.isPartXIIIcmp24()) {
                campos.setField("topmostSubform[0].Page4[0].c4_005[0]", "2");
            }

            // Parte XIV
            if (forma.isPartXIVcmp25b()) {
                campos.setField("topmostSubform[0].Page5[0].c5_001[0]", "2");
            }

            // Parte XV
            if (forma.isPartXVcmp26()) {
                campos.setField("topmostSubform[0].Page5[0].c5_002[0]", "2");
            }

            // Parte XVI
            if (forma.isPartXVIcmp27a()) {
                campos.setField("topmostSubform[0].Page5[0].c5_003[0]", "2");
            }
            if (forma.isPartXVIcmp27b()) {
                campos.setField("topmostSubform[0].Page5[0].c5_004[0]", "1");
            }
            if (forma.isPartXVIcmp27c()) {
                campos.setField("topmostSubform[0].Page5[0].c5_005[0]", "2");
            }

            // Parte XVII
            if (forma.isPartXVIIcmp28()) {
                campos.setField("topmostSubform[0].Page5[0].c5_006[0]", "2");
            }

            // Parte XVIII
            if (forma.isPartXVIIIcmp29a()) {
                campos.setField("topmostSubform[0].Page6[0].c6_001[0]", "2");
            }

            // Parte XIX
            if (forma.isPartXIXcmp30a()) {
                campos.setField("topmostSubform[0].Page6[0].c6_002[0]", "2");
            }
            if (forma.isPartXIXcmp30b()) {
                campos.setField("topmostSubform[0].Page6[0].c6_003[0]", "2");
            }
            if (forma.isPartXIXcmp30c()) {
                campos.setField("topmostSubform[0].Page6[0].c6_004[0]", "2");
            }
            if (forma.isPartXIXcmp30d()) {
                campos.setField("topmostSubform[0].Page6[0].c6_005[0]", "2");
            }
            if (forma.isPartXIXcmp30e()) {
                campos.setField("topmostSubform[0].Page6[0].c6_006[0]", "2");
            }
            if (forma.isPartXIXcmp30f()) {
                campos.setField("topmostSubform[0].Page6[0].c6_007[0]", "2");
            }

            // Parte XX
            if (forma.isPartXXcmp31()) {
                campos.setField("topmostSubform[0].Page7[0].c7_001[0]", "2");
            }

            // Parte XXI
            if (forma.isPartXXIcmp32a()) {
                campos.setField("topmostSubform[0].Page7[0].c7_002[0]", "2");
            }

            // Parte XXII
            if (forma.isPartXXIIcmp33a()) {
                campos.setField("topmostSubform[0].Page7[0].c7_003[0]", "2");
            }

            // Parte XXIII
            if (forma.isPartXXIIIcmp34a()) {
                campos.setField("topmostSubform[0].Page7[0].c7_004[0]", "2");
            }
            if (forma.isPartXXIIIcmp34b()) {
                campos.setField("topmostSubform[0].Page7[0].c7_005[0]", "2");
            }

            // Parte XXIV
            if (forma.isPartXXIVcmp35()) {
                campos.setField("topmostSubform[0].Page7[0].c7_006[0]", "2");
            }

            // Parte XXV
            if (forma.isPartXXVcmp36()) {
                campos.setField("topmostSubform[0].Page7[0].c7_007[0]", "2");
            }

            // Parte XXVI
            if (forma.isPartXXVIcmp37()) {
                campos.setField("topmostSubform[0].Page8[0].c8_001[0]", "2");
            }

            stamp1.close();
            reader.close();
        }
    }

    private void procesaMapaCamposEntero(final Beneficiario beneficiario, final AcroFields campos,
            final Map<Integer, String> mapeoCampos, final String nombreCampo) throws Exception {
        Integer field3 = null;
        try {
            String field3String = BeanUtils.getNestedProperty(beneficiario, nombreCampo);
            field3 = Integer.valueOf(field3String);
        } catch (Exception e) {
            field3 = null;
            logger.error("LLave no encontrada en Beneficiario: [" + nombreCampo + "]");
        }

        if (field3 != null) {
            if (mapeoCampos.get(field3) != null) {
                String valor = this.checkFormatoW9(mapeoCampos.get(field3));
                campos.setField(mapeoCampos.get(field3), valor);
            }
        }
    }

    private void procesaMapaCamposEntero(final Beneficiario beneficiario, final AcroFields campos,
            final Map<Integer, String> mapeoCampos, final String nombreCampo,
            final Map<Integer, Integer> correlacionTipos) throws Exception {
        Integer field3 = null;
        try {
            String field3String = BeanUtils.getNestedProperty(beneficiario, nombreCampo);
            field3 = Integer.valueOf(field3String);
        } catch (Exception e) {
            field3 = null;
            logger.error("LLave no encontrada en Beneficiario: [" + nombreCampo + "]");
        }

        if (field3 != null) {
            if (mapeoCampos.get(field3) != null) {
                String valor = this.checkFormatoW9(mapeoCampos.get(field3));
                campos.setField(mapeoCampos.get(field3), valor);
                campos.setField("c1_1", correlacionTipos.get(field3).toString());
            }
        }
    }


    /*
     * regresa el valor de 3W9 para insertar le valor seleccionado en el radio del formato W9
     */
    private String checkFormatoW9(final String dato) {
        if (dato.equals(Constantes.INDIVIDUAL)) {
            return Constantes.INDIVIDUAL_VALUE;
        }
        if (dato.equals(Constantes.C_CORPORATION)) {
            return Constantes.C_CORPORATION_VALUE;
        }
        if (dato.equals(Constantes.S_CORPORATION)) {
            return Constantes.S_CORPORATION_VALUE;
        }
        if (dato.equals(Constantes.PARTNERSHIP)) {
            return Constantes.PARTNERSHIP_VALUE;
        }
        if (dato.equals(Constantes.TRUST_ESTATE)) {
            return Constantes.TRUST_ESTATE_VALUE;
        }
        if (dato.equals(Constantes.LIMITED_LIABILITY_COMPANY)) {
            return Constantes.LIMITED_LIABILITY_COMPANY_VALUE;
        }
        if (dato.equals(Constantes.OTHER)) {
            return Constantes.OTHER_VALUE;
        }
        return "Yes";
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try{
        	this.doGet(request, response);     	
        } catch(Exception ex){
        	logger.error(ex.getMessage());
        }
    }

}
