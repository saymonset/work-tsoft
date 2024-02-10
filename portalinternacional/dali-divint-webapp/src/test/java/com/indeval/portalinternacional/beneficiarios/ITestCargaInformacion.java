/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.beneficiarios;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;

/**
 * Clase para
 * @author Rafael Ibarra Zendejas
 */
public class ITestCargaInformacion extends BaseTestCase {

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    private NumberFormat nf = NumberFormat.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ControlBeneficiariosService controlBeneficiariosService;
    private InstitucionDao institucionDao;
    private Map<Integer, String> mapaConversion = new HashMap<Integer, String>();
    private Map<String, Long> mapaConversionCustodio = new HashMap<String, Long>();
    private Map<String, Long> mapaConversionTipo = new HashMap<String, Long>();
    private static int COL_IDFOLIO = 0;
    private static int COL_CUSTODIO = 1;
    private static int COL_REFERENCIA_CUSTODIO = 2;
    private static int COL_NOMBRE = 3;
    private static int COL_CALLE = 4;
    private static int COL_COLONIA = 5;
    private static int COL_CIUDAD = 6;
    private static int COL_ESTADO = 7;
    private static int COL_CODIGO_POSTAL = 8;
    private static int COL_DIA = 9;
    private static int COL_MES = 10;
    private static int COL_ANIO = 11;
    private static int COL_USTAXPAYER_ID_NUMBER = 12;
    private static int COL_TIPO_FORMATO = 13;
    private static int COL_TIPO_ENTIDAD = 14;
    private static int COL_TIPO_BENEFICIARIO = 15;
    private static int COL_RFC = 16;
    private List<Field3W8BEN> listField3;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (controlBeneficiariosService == null) {
            controlBeneficiariosService = (ControlBeneficiariosService) applicationContext.getBean("controlBeneficiariosServiceEJB");
        }
        if (institucionDao == null) {
            institucionDao = (InstitucionDao) applicationContext.getBean("institucionDao");
        }
        mapaConversion.put(COL_IDFOLIO, "idFolio");
        mapaConversion.put(COL_CUSTODIO, "custodio");
        mapaConversion.put(COL_REFERENCIA_CUSTODIO, "referenciaCustodio");
        mapaConversion.put(COL_NOMBRE, "nombre");
        mapaConversion.put(COL_CALLE, "calle");
        mapaConversion.put(COL_COLONIA, "colonia");
        mapaConversion.put(COL_CIUDAD, "ciudad");
        mapaConversion.put(COL_ESTADO, "estado");
        mapaConversion.put(COL_CODIGO_POSTAL, "codigoPostal");
        mapaConversion.put(COL_DIA, "dia");
        mapaConversion.put(COL_MES, "mes");
        mapaConversion.put(COL_ANIO, "anio");
        mapaConversion.put(COL_USTAXPAYER_ID_NUMBER, "usIdNumber");
        mapaConversion.put(COL_TIPO_FORMATO, "tipoFormato");
        mapaConversion.put(COL_TIPO_ENTIDAD, "tipoEntidad");
        mapaConversion.put(COL_TIPO_BENEFICIARIO, "tipoBeneficiario");
        mapaConversion.put(COL_RFC, "rfc");

        mapaConversionCustodio.put("DEUTSCHE", Constantes.CN_DEUSTCHE_BANK);
        mapaConversionCustodio.put("BONY", Constantes.CN_THE_BANK_OF_NEW_YORK);

        mapaConversionTipo.put("individual", Constantes.PERSONA_FISICA_NO_EUA);
        mapaConversionTipo.put("corporation", Constantes.PERSONA_MORAL_NO_EUA);
        mapaConversionTipo.put("trust", Constantes.FIDEICOMISO_SIMPLE);
        mapaConversionTipo.put("afore", Constantes.SIEFORE_AFORE);

        listField3 = controlBeneficiariosService.getField3W8BEN();

        nf.setParseIntegerOnly(true);
        nf.setGroupingUsed(false);
    }

    public void testCargaInformacion() throws Exception {
        log.info("INICIANDO la carga");
        Date fechaInicio = new Date();
        int primeraFila = 2001;
        int ultimaFila =  9974;

        int contTotal = 0;
        int guardadosExito = 0;
        int errores = 0;
        int noCargados = 0;
        int erroresCarga = 0;

//        FileInputStream fis = new FileInputStream("O:\\Proyectos\\DivInt\\Inversionistas\\BaseDatosW8BENsinIDFOLIO.xls");
        FileInputStream fis = new FileInputStream("/soft/projects/DivInt/BaseDatosW8BENDefinitiva.xls");
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        String valorCadena = null;
        Long tipoBeneficiario = null;
        Double valorNumerico = null;
        String formato = null;
        Long idCustodio = null;
        Long idCustodioTemp = null;
        FormaW8BEN forma = null;
        Set<Integer> llavesMapaConversion = mapaConversion.keySet();

        for (int i = primeraFila; i <= ultimaFila; i++) {
            log.info("-");
            contTotal++;
            row = sheet.getRow(i - 1);
            RegistroExcelVO reg = new RegistroExcelVO();
            for (Integer idColumna : llavesMapaConversion) {
                cell = row.getCell(idColumna.shortValue());
                if (cell != null) {
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        valorCadena = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(valorCadena)) {
                            BeanUtils.setProperty(reg, mapaConversion.get(idColumna), valorCadena);
                        }
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        valorNumerico = cell.getNumericCellValue();
                        BeanUtils.setProperty(reg, mapaConversion.get(idColumna), nf.format(valorNumerico));
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        BeanUtils.setProperty(reg, mapaConversion.get(idColumna), null);
                    } else {
                        log.error("Tipo de Dato no soportado para FILA-COLUMNA: [" + i + "- " + mapaConversion.get(idColumna) + " - " + cell.getCellType() + "]");
                        reg.setTipoFormato(null);
                        erroresCarga++;
                        break;
                    }
                }
            }
//			log.info(reg.toString());
//
//            log.info("Registros con errores: " + erroresCarga + " de " + contTotal);

            formato = "W8BEN";
            idCustodio = null;
            idCustodioTemp = null;
            String cadenaCustodio = reg.getCustodio().trim().toUpperCase();
            if (cadenaCustodio.contains("/")) {
                String cadenas[] = cadenaCustodio.replaceAll(" ", "").split("/");
                idCustodio = mapaConversionCustodio.get(cadenas[0]);
                idCustodioTemp = mapaConversionCustodio.get(cadenas[1]);
            } else {
                idCustodio = mapaConversionCustodio.get(reg.getCustodio().trim().toUpperCase());
            }
            tipoBeneficiario = mapaConversionTipo.get(reg.getTipoBeneficiario().trim().toLowerCase());

            if (idCustodio != null && idCustodio > 0 && tipoBeneficiario != null && tipoBeneficiario > 0) {
                forma = new FormaW8BEN(idCustodio, tipoBeneficiario, null, listField3);
                forma.setFormato(formato.toUpperCase());
                forma.setPersonaFisica(tipoBeneficiario == Constantes.PERSONA_FISICA_NO_EUA ? true : false);
                forma.setDisabledDireccionPostal(true);
                if (StringUtils.isBlank(reg.getUsIdNumber())) {
                    forma.setDisabledUsIdNumber(true);
                } else {
                    forma.setDisabledUsIdNumber(false);
                    forma.setTaxPayerIdNumber(reg.getUsIdNumber());
                }
                if (StringUtils.isBlank(reg.getReferenciaCustodio())) {
                    forma.setDisabledreferenceNumber(true);
                } else {
                    forma.setDisabledreferenceNumber(false);
                    forma.setNumeroReferencia(reg.getReferenciaCustodio());
                }
                if (forma.isPersonaFisica()) {
                    parteNombre(reg.getNombre(), forma);
                } else {
                    forma.setRazonSocial(reg.getNombre());
                    forma.setCapacityActing(".");
                }
                forma.setPais("MEXICO");
                try {
                    forma.setFechaFormato(formatoFecha.parse(reg.getDia().trim() + "-" + reg.getMes().trim() + "-" + reg.getAnio().trim()));
                } catch (Exception exception) {
                    errores++;
                    log.error("***Error en la fecha formato: FILA: [" + i + "] " + exception.getMessage());
                    continue;
                }
                forma.setCalleResidencial(resuelveDatoVacio(reg.getCalle() + " " + resuelveDatoVacio(reg.getColonia())));
                forma.setNumeroExteriorResidencial(".");
                forma.setCityTownResidencial(resuelveDatoVacio(reg.getCiudad()));
                forma.setStateProvinceResidencial(resuelveDatoVacio(reg.getEstado()));
                forma.setCodigoPostalResidencial(resuelveDatoVacio(reg.getCodigoPostal()));
                forma.setPaisResidencial("MEXICO");
                if (StringUtils.isNotBlank(reg.getRfc())) {
                    forma.setRfc(reg.getRfc().trim().toUpperCase());
                } else {
                    forma.setRfc(".");
                }
                Beneficiario beneficiario = forma.construyeBO();
                beneficiario.getFormatoW8BEN().setField9OptionB(Boolean.FALSE);
                beneficiario.getFormatoW8BEN().setField9OptionE(Boolean.FALSE);
                if (!forma.isPersonaFisica()) {
                    beneficiario.getFormatoW8BEN().setField9OptionD(Boolean.FALSE);
                }
                if (tipoBeneficiario.equals(Constantes.PERSONA_MORAL_NO_EUA) || tipoBeneficiario.equals(Constantes.FIDEICOMISO_SIMPLE)) {
                    beneficiario.getFormatoW8BEN().getField3().setIdCampo(2l);
                }

                Beneficiario beneficiarioTemp = null;
                if (idCustodioTemp != null && idCustodioTemp > 0) {
                    beneficiarioTemp = forma.construyeBO();
                    beneficiarioTemp.setIdCuentaNombrada(idCustodioTemp);
                    beneficiarioTemp.getFormatoW8BEN().setField9OptionB(Boolean.FALSE);
                    beneficiarioTemp.getFormatoW8BEN().setField9OptionE(Boolean.FALSE);
                    if (!forma.isPersonaFisica()) {
                        beneficiarioTemp.getFormatoW8BEN().setField9OptionD(Boolean.FALSE);
                    }
                    if (tipoBeneficiario.equals(Constantes.PERSONA_MORAL_NO_EUA) || tipoBeneficiario.equals(Constantes.FIDEICOMISO_SIMPLE)) {
                        beneficiarioTemp.getFormatoW8BEN().getField3().setIdCampo(2l);
                    }
                }

                Long idInstitucion = null;
                Long idInstitucionSec = null;
//                if (StringUtils.isNotBlank(reg.getIdFolio()) && reg.getIdFolio().trim().length() <= 6) {
//                    Institucion ins = institucionDao.findInstitucionByClaveFolio(reg.getIdFolio().replaceAll(" ", ""));
//                    if (ins != null) {
//                        idInstitucion = ins.getIdInstitucion();
//                    } else {
//                        idInstitucion = null;
//                    }
//                } else {
//                    if (StringUtils.isNotBlank(reg.getIdFolio()) && reg.getIdFolio().trim().contains("/")) {
//                        String instituciones[] = reg.getIdFolio().trim().replaceAll(" ", "").split("/");
//                        Iinstitucion ins = institucionDao.findInstitucionByClaveFolio(instituciones[0]);
//                        if (ins != null) {
//                            idInstitucion = ins.getIdInstitucion();
//                        } else {
//                            idInstitucion = null;
//                        }
//                        Institucion insSec = institucionDao.findInstitucionByClaveFolio(instituciones[1]);
//                        if (insSec != null) {
//                            idInstitucionSec = insSec.getIdInstitucion();
//                        } else {
//                            idInstitucionSec = null;
//                        }
//                    } else {
//                        log.error("Institucion invalida [" + reg.getIdFolio() + "]");
//                        errores++;
//                        continue;
//                    }
//                }

                try {
                	
                    Long idBenef = controlBeneficiariosService.insertaBeneficiario(beneficiario, idInstitucion);
                    Long idBenefSec = null;
                    if(beneficiarioTemp != null) {
                        idBenefSec = controlBeneficiariosService.insertaBeneficiario(beneficiarioTemp, idInstitucion);
                    }
                    log.info("Beneficiario guardado con extio: [" + reg.getNombre() + "][" + i + "]");
                    controlBeneficiariosService.activarBeneficiario(idBenef);
                    if(beneficiarioTemp != null) {
                        controlBeneficiariosService.activarBeneficiario(idBenefSec);
                    }
                    log.info("Beneficiario Activado con extio: [" + reg.getNombre() + "][" + i + "]");

                    if (idInstitucionSec != null && idInstitucionSec > 0) {
                        try {
                            controlBeneficiariosService.agregaBeneficiarioInstitucion(idBenef, idInstitucionSec);
                            if(beneficiarioTemp != null) {
                                controlBeneficiariosService.agregaBeneficiarioInstitucion(idBenefSec, idInstitucionSec);
                            }
                        } catch (Exception businessException) {
                            errores++;
                            log.error("***Error: FILA: [" + i + "] Error al agregar la institucion al beneficiairo [" + idBenef + "] [" + idInstitucion + "-" + idInstitucionSec + "]" + businessException.getMessage());
                            continue;
                        }
                        log.info("Beneficiario agregado a institucion: [" + idBenef + "] [" + idInstitucion + "-" + idInstitucionSec + "]");
                    }

//                    
                    guardadosExito++;
                } catch (Exception exception) {
                    errores++;
                    log.error("***Error: FILA: [" + i + "]" + exception.getMessage());
                }

            } else {
                log.info("Este formato[" + formato + "] y tipoBeneficiario[" + tipoBeneficiario + "] y custodio [" + reg.getCustodio() + "] no se procesa FILA: [" + i + "]");
                noCargados++;
            }
        }

        fis.close();
        Date fechaFin = new Date();
        Long diff = fechaFin.getTime() - fechaInicio.getTime();

        int minutos = (int) (diff / 60000);
        int segundos = (int) (diff / 1000 % 60);

        log.info("FIN de la carga");
        log.info("Tiempo de ejecuacion: [" + minutos + " m " + segundos + " s] - [" + diff + "ms]");
        log.info("Total: [" + contTotal + "]");
        log.info("EXITO: [" + guardadosExito + "]");
        log.info("Errores: [" + errores + "]");
        log.info("No cargados: [" + noCargados + "]");
    }

    private void parteNombre(String nombre, FormaGeneral forma) {
        if (StringUtils.isNotBlank(nombre)) {
            nombre = nombre.replaceAll("   ", " ");
            nombre = nombre.replaceAll("  ", " " );
            String nombres[] = nombre.split(" ");
            if (nombres.length >= 2) {
                if (nombres.length == 2) {
                    forma.setNombre(nombres[0]);
                    forma.setApellidoPaterno(nombres[1]);
                    forma.setApellidoMaterno(".");
                }
                if (nombres.length == 3) {
                    forma.setNombre(nombres[0]);
                    forma.setApellidoPaterno(nombres[1]);
                    forma.setApellidoMaterno(nombres[2]);
                } else if (nombres.length == 4) {
                    forma.setNombre(nombres[0] + " " + nombres[1]);
                    forma.setApellidoPaterno(nombres[2]);
                    forma.setApellidoMaterno(nombres[3]);
                } else if (nombres.length == 5) {
                    forma.setNombre(nombres[0] + " " + nombres[1]);
                    forma.setApellidoPaterno(nombres[2] + " " + nombres[3]);
                    forma.setApellidoMaterno(nombres[4]);
                } else if (nombres.length == 6) {
                    forma.setNombre(nombres[0] + " " + nombres[1]);
                    forma.setApellidoPaterno(nombres[2] + " " + nombres[3]);
                    forma.setApellidoMaterno(nombres[4] + " " + nombres[5]);
                } else if (nombres.length >= 7) {
                    forma.setApellidoPaterno(nombres[nombres.length-4] + " " + nombres[nombres.length-3]);
                    forma.setApellidoMaterno(nombres[nombres.length-2] + " " + nombres[nombres.length-1]);
                    StringBuilder nombreBen = new StringBuilder();
                    for(int i = 0; i < (nombres.length-4); i++) {
                         nombreBen.append(nombres[i] + " ");
                    }
                    forma.setNombre(nombreBen.toString());
                }
            }
        }
    }

    private String resuelveDatoVacio(String dato) {
        if (StringUtils.isBlank(dato)) {
            return ".";
        }
        return dato.trim().toUpperCase();
    }
}

