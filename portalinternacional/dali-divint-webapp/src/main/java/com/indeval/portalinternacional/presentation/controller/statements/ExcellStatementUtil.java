/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.statements;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.excell.vo.CargaExcellVO;
import com.indeval.portalinternacional.middleware.servicios.excell.vo.CargaZipVO;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;

/**
 * Implementacion del servicio para Statements
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ExcellStatementUtil {

    /** Log de clase. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    /** Servicio de Statements */
    private StatementsDivintService statementsDivintService;
    /** Listado de Sinonmos de personas fisicas */
    private List<String> listaPersonasFisicas;
    /** Listado de Sinonmos de personas morales */
    private List<String> listaPersonasMorales;
    /** Constante de CIEN */
    private static BigDecimal CIEN;
    /** Formateador para fecha */
    private DateFormat dateFormat;
    /** Formateador de la fecha */
    private SimpleDateFormat simpleDateFormat;
    /** Institucion para asignar por defualt a los beneficiarios */
    private String institucionIndeval = "12001";

    /** Constructor por omision */
    public ExcellStatementUtil() {
        super();
        listaPersonasFisicas = new ArrayList<String>();
        listaPersonasFisicas.add("INDIVIDUAL");
        listaPersonasMorales = new ArrayList<String>();
        listaPersonasMorales.add("CORPORATION");
        CIEN = new BigDecimal(100);
        CIEN = CIEN.setScale(2);
        dateFormat = DateFormat.getDateInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    }

    /**
     * Metodo para guardar un archivo ZIP
     * @param inputStream Input Stream del ZIP a cargar
     * @return	Objeto con los resultados de la carga
     * @throws Exception En caso de error al procesar el ZIP
     */
    public CargaZipVO guardaZip(InputStream inputStream, String nombreZip, boolean simular) throws Exception {
        log.info("Entrando a ExcellStatementUtil.guardaZip");
        CargaZipVO retorno = new CargaZipVO();
        int totales = 0;
        try {
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry = null;
            String nombreArchivo = null;
            CargaExcellVO<StatementDivintVO> cargaExcel = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                nombreArchivo = StringUtils.trimToEmpty(zipEntry.getName());
                if (!zipEntry.isDirectory() &&
                        StringUtils.upperCase(nombreArchivo).endsWith(".XLS")) {
                    log.debug("Nombre del archivo a cargar: [" + nombreArchivo + "]");
                    try {
                        cargaExcel = cargaExcel(zipInputStream, nombreArchivo, nombreZip, simular);
                        cargaExcel.setNombreArchivo(nombreArchivo);
                        totales++;
                        retorno.getResultados().add(cargaExcel);
                    } catch (Exception exception) {
                        log.error("Error al abrir el archivo: [" + StringUtils.upperCase(nombreArchivo) + "]", exception);
                        retorno.getErrores().put(StringUtils.upperCase(nombreArchivo), exception.getMessage());
                    }
                } else {
                    retorno.getErrores().put(StringUtils.upperCase(nombreArchivo), "Formato de Archivo no compatible");
                    log.error("Archivo no soportado para la carga: [" + StringUtils.upperCase(nombreArchivo) + "] del ZIP [" + nombreZip + "]");
                }
                zipInputStream.closeEntry();
            }
        } catch (Exception exception) {
            log.error("Error al abrir el archivo", exception);
            retorno.getErrores().put("ZIP", exception.getMessage());
        } finally {
            retorno.setTotalArchivos(totales);
            inputStream.close();
        }
        return retorno;
    }

    /**
     * Metodo para guardar un arvhico excel independiente
     * @param inputStream Input Stream del archivo Excel a cargar
     * @return	Objeto con los resultados de la carga
     * @throws Exception En caso de error al procesar el archivo
     */
    public CargaExcellVO<StatementDivintVO> cargaExcel(InputStream inputStream, String nombreArchivo, 
            String nombreZip, boolean simular) throws Exception {
        log.info("Entrando a ExcellStatementUtil.cargaExcel");
        CargaExcellVO<StatementDivintVO> retorno = new CargaExcellVO<StatementDivintVO>();

        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        int totalRegistros = 0, registrosError = 0, registrosOk = 0;

        try {
            wb = new HSSFWorkbook(POIFSFileSystem.createNonClosingInputStream(inputStream));
            sheet = wb.getSheetAt(0);
            int primeraFila = this.getFirstRow(sheet);
            log.debug("Primera Fila: [" + primeraFila + "]");
            int ultimaFila = sheet.getLastRowNum();
            log.debug("Ultima Fila: [" + ultimaFila + "]");
            StatementDivintVO vo = null;
            int numeroColumnaReal = 0;

            for (int i = primeraFila+1; i <= ultimaFila; i++) {
                numeroColumnaReal = i + 1;
                log.debug("Fila reconocida: [" + numeroColumnaReal + "]");
                row = sheet.getRow(i);//comienza con renglon cero.
                if (row != null	) 
                {
                    vo = convertRowToStatement(row);
                    vo.setArchivoOrigen(nombreArchivo);
                    vo.setArchivoZip(nombreZip);
                    try {//Se agrega condición para validar que exista una emision
                    	if(StringUtils.isBlank(getColumnAsString(row.getCell(1)))){
                    		throw new BusinessException(" Emisi\u00F3n inv\u00E1lida");
                    	}
                        if(!simular) {
                            statementsDivintService.guardaStatement(vo);
                        }
                        else {
                            statementsDivintService.validaStatement(vo);
                        }
                        registrosOk++;
                        retorno.getResultados().add(vo);
                    } catch (BusinessException businessException) {
                        registrosError++;
                        retorno.getErrores().put(numeroColumnaReal, businessException.getMessage());
                        log.error("Error de negocio al guardar el registro[" + numeroColumnaReal + "]", businessException);
                    } catch (EJBException ejbException) {
                        registrosError++;
                        String mensajeError = getBussinesErrorMessage(ejbException);
                        retorno.getErrores().put(numeroColumnaReal, mensajeError);
                        log.error("Error al guardar el registro[" + numeroColumnaReal + "]", ejbException);
                    } catch (Exception exception) {
                        registrosError++;
                        retorno.getErrores().put(numeroColumnaReal, exception.toString());
                        log.error("Error al guardar el registro[" + numeroColumnaReal + "]", exception);
                    } finally {
                        totalRegistros++;
                    }
                }
            }
        } catch (IOException ex) {
            log.error("Error al abrir el archivo", ex);
            throw new BusinessException("Error al abrir el archivo ",ex);
        }

        retorno.setTotal(totalRegistros);
        retorno.setCargados(registrosOk);
        retorno.setNoCargados(registrosError);
        return retorno;
    }

    private StatementDivintVO convertRowToStatement(HSSFRow row) {
        StatementDivintVO retorno = new StatementDivintVO();
        //Datos del custodio
        String custodio= getColumnAsString(row.getCell(0));
        retorno.setCustodio(StringUtils.trim(custodio));
       
        
        // Datos de la emision
        String emision = getColumnAsString(row.getCell(1));
        String[] partesEmision = StringUtils.split(emision);
        if (partesEmision != null) {
           if (partesEmision.length == 3 || partesEmision.length == 4) {
        	   
                retorno.setTv(partesEmision[0]);
                retorno.setEmisora(partesEmision[1]);
                retorno.setSerie(partesEmision[2]);
            } else if (partesEmision.length == 2) {
                retorno.setTv(partesEmision[0]);
                retorno.setEmisora(partesEmision[1]);
            }
        }
        // Fecha de Pago
        Date fechaPago = getColumnAsDate(row.getCell(2));
        retorno.setFechaPago(fechaPago);
        // Fecha de Regsitro
        Date fechaRegsitro = getColumnAsDate(row.getCell(3));
        retorno.setFechaRegistro(fechaRegsitro);
        // ISIN
        String isin = getColumnAsString(row.getCell(4));
        retorno.setIsin(isin);
        // IdFolio
        String idFolio = getColumnAsString(row.getCell(5));
        if (StringUtils.isNotBlank(idFolio)) {
            idFolio = StringUtils.deleteWhitespace(idFolio);
            if (idFolio.length() > 5) {
                retorno.setIdFolio(idFolio.substring(0, 5));
            } else {
                retorno.setIdFolio(idFolio);
            }
        } else {
            retorno.setIdFolio(institucionIndeval);
        }
        // ADP
        String adp = getColumnAsString(row.getCell(6));
        retorno.setAdp(adp);
        // Nombre
        String nombre = getColumnAsString(row.getCell(7));
        retorno.setNombre(nombre);
        // Direccion
        String direccion = getColumnAsString(row.getCell(8));
        retorno.setDireccion(direccion);
        // Tax Payer Id Number
        String taxPayerNumber = getColumnAsString(row.getCell(9));
        retorno.setTaxPayerNumber(taxPayerNumber);
        // Formato
        String formato = getColumnAsString(row.getCell(10));
        if(StringUtils.isNotBlank(formato)) {
            char caracter = '\0';
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < formato.length(); i++) {
                caracter = formato.charAt(i);
                if(StringUtils.isAlphanumeric( Character.toString(caracter) )) {
                    sb.append(caracter);
                }
            }
            formato = StringUtils.upperCase(sb.toString());
        }
        retorno.setFormato(formato);
        // Status
        String status = getColumnAsString(row.getCell(11));
        retorno.setStatusOwner(status);
        // Tipo Beneficiario
        String tipoBeneficiario = transformaCadena(getColumnAsString(row.getCell(12)));
        if (StringUtils.isNotBlank(tipoBeneficiario)) {
            if (StringUtils.equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN, retorno.getFormato())) {
                if (listaPersonasFisicas.contains(tipoBeneficiario)) {
                    retorno.setTipoBeneficiario("Persona Fisica extranjera a EUA");
                } else if (listaPersonasMorales.contains(tipoBeneficiario)) {
                    retorno.setTipoBeneficiario("Persona Moral extranjera a EUA");
                } else if ( StringUtils.equalsIgnoreCase("Tax-exempt organization", tipoBeneficiario) ||
                        StringUtils.equalsIgnoreCase("Tax exempt organization", tipoBeneficiario) ) {
                    retorno.setTipoBeneficiario("Persona Moral extranjera a EUA");
                }
            } else if (StringUtils.equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W9, retorno.getFormato())) {
                if (listaPersonasFisicas.contains(tipoBeneficiario)) {
                    retorno.setTipoBeneficiario("Persona Fisica de EUA");
                } else if (listaPersonasMorales.contains(tipoBeneficiario)) {
                    retorno.setTipoBeneficiario("Persona Moral de EUA");
                }
            } else if (StringUtils.equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY, retorno.getFormato())) {
                retorno.setTipoBeneficiario(getColumnAsString(row.getCell(12)));
            }
        }
        // Pais
        String pais = getColumnAsString(row.getCell(13));
        retorno.setPais(pais);
        // Porcentaje
        BigDecimal porcentaje = getColumnAsBigDecimal(row.getCell(14), 5);
        log.info("Porcentaje1: [" + porcentaje + "]");
        if (porcentaje != null) {
            porcentaje = porcentaje.multiply(CIEN);
            porcentaje = porcentaje.setScale(1, RoundingMode.HALF_UP);
        } else {
            // Se va a obtener como cadena
            String porcentajeString = getColumnAsString(row.getCell(14));
            String[] cadenas;
            if(StringUtils.isNotBlank(porcentajeString)) {
                porcentajeString = StringUtils.replace(porcentajeString, "%", " ");
                cadenas = porcentajeString.split(" ");
                if(cadenas.length > 0 &&
                        StringUtils.isNumeric(cadenas[0])) {
                    porcentaje = new BigDecimal(cadenas[0]);
                }
            }
        }
        retorno.setPorcentajeRetencion(porcentaje);
        // Titulos
        Long titulos = getColumnAsLong(row.getCell(15));
        retorno.setNumeroTitulos(titulos);
        // RFC
        String rfc = getColumnAsString(row.getCell(16));
        retorno.setRfc(rfc);
        // Proporcion
        BigDecimal proporcion = getColumnAsBigDecimal(row.getCell(17), 6);
        retorno.setProporcion(proporcion);
        // Dividendo
        BigDecimal dividendo = getColumnAsBigDecimal(row.getCell(18), 2);
        retorno.setDividendo(dividendo);
        // Impuesto
        BigDecimal impuesto = getColumnAsBigDecimal(row.getCell(19), 2);
        retorno.setImpuesto(impuesto);
        // Dividendo Neto
        BigDecimal dividendoNeto = getColumnAsBigDecimal(row.getCell(20), 2);
        retorno.setDividendoNeto(dividendoNeto);

        return retorno;
    }

    private String transformaCadena(String cadena) {
        return StringUtils.trim(StringUtils.upperCase(cadena));
    }

    private String getColumnAsString(HSSFCell cell) {
        String retorno = null;
        if (cell != null && (cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
            retorno = StringUtils.trimToNull(cell.getRichStringCellValue().getString());
        }
        return retorno;
    }

    private BigDecimal getColumnAsBigDecimal(HSSFCell cell, int scale) {
        BigDecimal retorno = null;
        if (cell != null && (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
            retorno = new BigDecimal(cell.getNumericCellValue());
            if (retorno != null) {
                retorno = retorno.setScale(scale, RoundingMode.HALF_UP);
            }
        }
        return retorno;
    }

    private Long getColumnAsLong(HSSFCell cell) {
        Long retorno = null;
        if (cell != null && (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
            retorno = new Double(cell.getNumericCellValue()).longValue();
        }
        return retorno;
    }

    private Date getColumnAsDate(HSSFCell cell) {
        Date retorno = null;
        if (cell != null && (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
            try {
                retorno = cell.getDateCellValue();
            } catch (IllegalStateException ex) {
                log.debug("No se pudo obtener la Fecha como numerico se procede a obtenerlo como Cadena");
                String fecha = getColumnAsString(cell);
                try {
                    retorno = simpleDateFormat.parse(fecha);
                } catch (ParseException ex1) {
                    log.debug("No se pudo obtener la Fecha como cadena con formato dd/mm/yy se procede a obtenerlo con DateFormat");
                    try {
                        retorno = dateFormat.parse(fecha);
                    } catch (ParseException ex2) {
                        log.error("Error al obtener la fecha con formato dd/mm/yy", ex2);
                    }
                }

            }
        }
        return retorno;
    }

    private int getFirstRow(HSSFSheet sheet) {
        int retorno = 0;

        boolean securityEncontrado = false;
        Cell cell1;
        for (Row row : sheet) {
            cell1 = null;
            if (row != null) {
                cell1 = row.getCell(0);
                if (securityEncontrado && cell1 != null && cell1.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
                    retorno = row.getRowNum();
                    break;
                }
                if (!securityEncontrado && cell1 != null && cell1.getCellType() == HSSFCell.CELL_TYPE_STRING &&
                        StringUtils.equalsIgnoreCase("SECURITY", cell1.getRichStringCellValue().getString())) {
                    // Una vez que la ha encuentra la celda sigue iterando hasta que ya no se regresan
                    // celdas vacias porque en los reportes las celdas de los headers tiene merge
                    securityEncontrado = true;
                }
            }
        }

        return retorno;
    }

    private String getBussinesErrorMessage(EJBException exception) {
        String retorno = null;
        if (exception.getCause() != null && exception.getCause() instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception.getCause();
            retorno = businessException.getMessage();
        } else if (exception.getCausedByException() != null && exception.getCausedByException() instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception.getCausedByException();
            retorno = businessException.getMessage();
        }
        return retorno;
    }

    /**
     * Servicio de Statements
     * @param statementsDivintService the statementsDivintService to set
     */
    public void setStatementsDivintService(StatementsDivintService statementsDivintService) {
        this.statementsDivintService = statementsDivintService;
    }
}
