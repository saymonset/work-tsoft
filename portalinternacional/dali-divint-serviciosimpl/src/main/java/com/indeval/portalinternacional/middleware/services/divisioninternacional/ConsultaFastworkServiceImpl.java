package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ReporteServiceImpl.TipoCelda;
import com.indeval.portalinternacional.middleware.servicios.fo.FastworkMessageFO;
import com.indeval.portalinternacional.middleware.servicios.vo.FastworkMessageVO;
import com.indeval.portalinternacional.persistence.dao.FastworkDao;

public class ConsultaFastworkServiceImpl implements ConsultaFastworkService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private FastworkDao fastworkDao;

	private final static String TITULO_RAW = "MensajeriaHistoricaRAW";
	private final static String TITULO_XLS = "ConsultaMensajeriaHistorica";
	private final static String HEADER_XLS = "Consulta Mensajería Histórica";

	@Override
	public PaginaVO consultaMensajes(FastworkMessageFO fastworkMessagefo, PaginaVO paginaVO, boolean reporte) {
		return fastworkDao.consultaMensajes(fastworkMessagefo, paginaVO, reporte);
	}

	@Override
	public FastworkMessageVO consultaIso(String dbkey) {
		return fastworkDao.consultaIso(dbkey);
	}

	public void setFastworkDao(FastworkDao fastworkDao) {
		this.fastworkDao = fastworkDao;
	}

	@Override
	public byte[] generarReporte(String ext, FastworkMessageFO fastworkMessagefo, AgenteVO agenteFirmado,
			PaginaVO paginaReportes, List<String> swiftHeaders) {
		byte[] file = null;
		switch(ext.toUpperCase()) {
		case "TXT":
			file = generarReporteTxt(fastworkMessagefo, paginaReportes);
				break;
		case "XLS":
			file = generarReporteXls(fastworkMessagefo, agenteFirmado, paginaReportes, swiftHeaders);
				break;
		case "ISO":
			file = generarReporteIsoTxt(fastworkMessagefo, agenteFirmado, paginaReportes, swiftHeaders);
				break;
		}

		return file;
	}

	@SuppressWarnings("unchecked")
	private byte[] generarReporteXls(FastworkMessageFO fastworkMessagefo, AgenteVO agenteFirmado,
			PaginaVO paginaReportes, List<String> swiftHeaders) {

		HSSFColor colorImpG;
		HSSFCellStyle cellStyleCellEmcabezado;
		HSSFFont fontCellEncabezado;
		HSSFCellStyle cellStyleCellNormalG;
		HSSFCellStyle cellStyleCellNormal;
		HSSFFont fontCellNormal;

		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();

		byte[] fileContent = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			colorImpG = setColor(workbook, (byte) 235, (byte) 235, (byte) 235);
			HSSFCellStyle cellStyleName = workbook.createCellStyle();
			HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
			HSSFCellStyle cellStyleBold = workbook.createCellStyle();
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyleCellEmcabezado = workbook.createCellStyle();
			fontCellEncabezado = workbook.createFont();
			HSSFColor colorE = setColor(workbook, (byte) 102, (byte) 102, (byte) 153);
			cellStyleCellEmcabezado.setFillForegroundColor(colorE.getIndex());
			cellStyleCellEmcabezado.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			fontCellEncabezado.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fontCellEncabezado.setColor(HSSFColor.WHITE.index);
			fontCellEncabezado.setFontHeightInPoints((short) 9);
			cellStyleCellEmcabezado.setFont(fontCellEncabezado);

			fontCellNormal = workbook.createFont();
			fontCellNormal.setFontHeightInPoints((short) 9);

			cellStyleCellNormalG = workbook.createCellStyle();
			cellStyleCellNormalG.setFont(fontCellNormal);
			cellStyleCellNormalG.setFillForegroundColor(colorImpG.getIndex());
			cellStyleCellNormalG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			cellStyleCellNormal = workbook.createCellStyle();
			cellStyleCellNormal.setFont(fontCellNormal);

			HSSFFont f22 = workbook.createFont();
			f22.setFontHeightInPoints((short) 22);
			f22.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyleName.setFont(f22);
			
			HSSFFont f12 = workbook.createFont();
			f12.setFontHeightInPoints((short) 12);
			f12.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyleTitle.setFont(f12);
			
			HSSFFont f10Bold = workbook.createFont();
			f10Bold.setFontHeightInPoints((short) 10);
			f10Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyleBold.setFont(f10Bold);

			int p = 1;
			int ini = 0;
			int reg = 10000;
			int fin = 0;

			paginaReportes = this.consultaMensajes(fastworkMessagefo, paginaReportes, true);
			BigDecimal totalRegistros = new BigDecimal(paginaReportes.getRegistros().size());
			Integer totalRegistrosInt = totalRegistros.intValue();
			while (fin < totalRegistrosInt) {
				log.debug("pagina " + p);
				paginaReportes.setOffset(ini);
				fin = (fin + reg) <= totalRegistrosInt ? (fin + reg) : totalRegistrosInt;
				List<FastworkMessageVO> registros = new ArrayList<>();
				registros = paginaReportes.getRegistros().subList(ini, fin);
				ini = fin;

				HSSFSheet worksheet = workbook.createSheet(TITULO_XLS + p);
				p++;
				int rown = 0;
				HSSFRow titulo = worksheet.createRow(rown);
				HSSFCell celltitulo = titulo.createCell(0);
				celltitulo.setCellValue(HEADER_XLS);
				celltitulo.setCellStyle(cellStyleName);
				
				HSSFFont f10 = workbook.createFont();
				f10 = workbook.createFont();
				f10.setFontHeightInPoints((short) 10);
				cellStyle.setFont(f10);

				// Institución
				++rown;
				HSSFRow instTitulo = worksheet.createRow(++rown);
				HSSFCell cellInstTitulo = instTitulo.createCell(0);
				cellInstTitulo.setCellValue("Institución: ");
				cellInstTitulo.setCellStyle(cellStyle);
				HSSFCell cellInstData = instTitulo.createCell(1);
				cellInstData.setCellValue(
						agenteFirmado.getId() + " " + agenteFirmado.getFolio() + " - " + agenteFirmado.getRazon());

				// Fecha
				HSSFRow fecha = worksheet.createRow(++rown);
				HSSFCell cellFecha = fecha.createCell(0);
				cellFecha.setCellValue("Fecha: ");
				cellFecha.setCellStyle(cellStyle);
				HSSFCell cellFechaData = fecha.createCell(1);
				cellFechaData.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				boolean rowColor = false;
				int celln = 0;

				//Filtros
				++rown;
				HSSFRow criteriosConsulta = worksheet.createRow(++rown);
				HSSFCell cellFiltrosTitulo = criteriosConsulta.createCell(0);
				cellFiltrosTitulo.setCellValue("Filtros");
				cellFiltrosTitulo.setCellStyle(cellStyleTitle);
				celln=0;
				++rown;
				HSSFRow criterios = worksheet.createRow(++rown);
				createCell(workbook, criterios, celln, "ISIN", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "BIC CODE", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Monto", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Tipo Mensaje", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Moneda", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Fecha Inicial", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Fecha Final", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, criterios, ++celln, "Buscar", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				celln=0;
				
				HSSFRow criteriosDatos = worksheet.createRow(++rown);
				String isin=fastworkMessagefo.getIsin()!=null?fastworkMessagefo.getIsin():"";
				createCell(workbook, criteriosDatos, celln, isin, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				String bicCode=fastworkMessagefo.getBiccode()!=null?fastworkMessagefo.getBiccode():"";
				createCell(workbook, criteriosDatos, ++celln, bicCode, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				BigDecimal monto=fastworkMessagefo.getMonto()!=null?fastworkMessagefo.getMonto():null;
				createCell(workbook, criteriosDatos, ++celln, monto, TipoCelda.NUMERO,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				String tipoMensaje=fastworkMessagefo.getTipoMensajesFiltroDesc()!=null?(fastworkMessagefo.getTipoMensajesFiltroDesc().isEmpty()?"TODAS":fastworkMessagefo.getTipoMensajesFiltroDesc()):"";
				createCell(workbook, criteriosDatos, ++celln, tipoMensaje, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				String moneda=fastworkMessagefo.getMonedaFiltroDesc()!=null?(fastworkMessagefo.getMonedaFiltroDesc().isEmpty()?"TODAS":fastworkMessagefo.getMonedaFiltroDesc()):"";
				createCell(workbook, criteriosDatos, ++celln, moneda, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				Date fechaInicio=fastworkMessagefo.getFechaInicial();
				createCell(workbook, criteriosDatos, ++celln, fechaInicio, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				Date fechaFinal=fastworkMessagefo.getFechaFinal();
				createCell(workbook, criteriosDatos, ++celln, fechaFinal, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				String buscar=fastworkMessagefo.getBuscar()!=null?fastworkMessagefo.getBuscar():"";
				createCell(workbook, criteriosDatos, ++celln, buscar, TipoCelda.NORMAL,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);

				//Total Registros
				++rown;
				++rown;
				celln=0;
				HSSFRow totalRegistrosHeader = worksheet.createRow(++rown);
				createCell(workbook, totalRegistrosHeader, celln, "Total Registros", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				HSSFRow totalRegistrosValor = worksheet.createRow(++rown);
				createCell(workbook, totalRegistrosValor, celln, totalRegistros, TipoCelda.NUMERO,rown,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);

				//Datos
				++rown;
				HSSFRow datos = worksheet.createRow(++rown);
				HSSFCell cellDatos = datos.createCell(0);
				cellDatos.setCellValue("Datos ");
				cellDatos.setCellStyle(cellStyleTitle);
				
				++rown;
				celln=0;
				HSSFRow datosCabeceras = worksheet.createRow(++rown);
				createCell(workbook, datosCabeceras, celln, "Fecha Registro", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, datosCabeceras, ++celln, "BIC Code", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, datosCabeceras, ++celln, "ISIN", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				createCell(workbook, datosCabeceras, ++celln, "Tipo Mensaje", TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				
				for(String swiftHeader:swiftHeaders) {
					createCell(workbook, datosCabeceras, ++celln, swiftHeader, TipoCelda.ENCABEZADO, 0, rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
				}
				
				rowColor=true;
				int rowNum=1;
				for(FastworkMessageVO registroDatos : registros) {
					celln=0;
					HSSFRow row = worksheet.createRow(++rown);
					createCell(workbook, row, celln, registroDatos.getFechaRegistro(), TipoCelda.NORMAL,rowNum,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
					createCell(workbook, row, ++celln, registroDatos.getBicCode(), TipoCelda.NORMAL,rowNum,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
					createCell(workbook, row, ++celln, registroDatos.getIsin(), TipoCelda.NORMAL,rowNum,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
					createCell(workbook, row, ++celln, registroDatos.getTipoMensaje(), TipoCelda.NORMAL,rowNum,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);

					if(registroDatos.getCamposSwift() != null) {
						for(String swiftValue:registroDatos.getCamposSwift()) {
							createCell(workbook, row, ++celln, swiftValue.equals("&nbsp;")?"":swiftValue, TipoCelda.NORMAL,rowNum,rowColor, cellStyleCellEmcabezado,cellStyleCellNormalG,cellStyleCellNormal);
						}
					}
				}
				rowColor=false;

			}
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			fileContent = fileOut.toByteArray();
		} catch (Exception e) {
			log.error("ERROR generarArchivoXls " + e.getMessage());
			e.printStackTrace();
		} 

		return fileContent;
	}
	
	@SuppressWarnings("unchecked")
	private byte[] generarReporteTxt(FastworkMessageFO fastworkMessagefo, PaginaVO paginaReportes) {
		BufferedWriter out = null;
		String timeLog = String.valueOf(System.currentTimeMillis());
		File logFile = new File(timeLog);
		byte[] fileContent = null;
		try{
			out = new BufferedWriter(new FileWriter(logFile));
			out.write("\r\n");	
			paginaReportes = this.consultaMensajes(fastworkMessagefo, paginaReportes, true);
			List<FastworkMessageVO> registros = paginaReportes.getRegistros();
			Long count = 0L;
			for(FastworkMessageVO registroDatos : registros) {
				out.write("DbKey=" + registroDatos.getDbkey() + " " + "Correspondent=" + registroDatos.getBicCode() + " " + "MsgType=" + registroDatos.getTipoMensaje() + "\r\n");
				out.write(registroDatos.getIsoReporte());		
				out.write("\r\n");	
				out.write("\r\n");
				count++;
				out.flush();
			}		
			fileContent = Files.readAllBytes(logFile.toPath());
			

			out.close();
		}catch (Exception e) {
			log.error("ERROR generarArchivoTxt "+e.getMessage());
			e.printStackTrace();
		}
		return  zipFile(fileContent, "txt") ;
	}	
	
	private byte[] generarReporteIsoTxt(FastworkMessageFO fastworkMessagefo, AgenteVO agenteFirmado,
			PaginaVO paginaReportes, List<String> swiftHeaders) {
		FastworkMessageVO messageVO = (FastworkMessageVO) paginaReportes.getValores().get("isoConsultado");
		String iso = messageVO.getIso();
		BufferedWriter out = null;
		String timeLog = String.valueOf(System.currentTimeMillis());
		File logFile = new File(timeLog);
		byte[] fileContent = null;
		try{
			out = new BufferedWriter(new FileWriter(logFile));
			out.write(iso);	
			out.flush();	
			fileContent = Files.readAllBytes(logFile.toPath());
			out.close();
		}catch (Exception e) {
			log.error("ERROR generarArchivoTxt "+e.getMessage());
			e.printStackTrace();
		}

		return  fileContent;
	}	
	
	private byte[]  zipFile(byte[] bytesFile, String ext ){
		byte[] byteZip = null;
		File f = new File(TITULO_RAW+"."+"zip");
		try{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		out.setLevel(Deflater.BEST_COMPRESSION);
		ZipEntry e = new ZipEntry(TITULO_RAW+"."+ext);
		out.putNextEntry(e);
		out.write(bytesFile, 0, bytesFile.length);
		out.closeEntry();
		out.flush();
		out.close();
		byteZip= Files.readAllBytes(f.toPath());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(f.exists()){
				f.delete();
			}
		}
		return byteZip;
	}

	private HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
			hssfColor = palette.findColor(r, g, b);
			if (hssfColor == null) {
				palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g, b);
				hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
			}
		} catch (Exception e) {
			log.error("ERROR setColor " + e.getMessage());
		}

		return hssfColor;
	}

	private void createCell(HSSFWorkbook workbook,HSSFRow row, int pos, Object val,TipoCelda tipo, int rowNum, boolean rowColor, HSSFCellStyle cellStyleCellEmcabezado, HSSFCellStyle cellStyleCellNormalG, HSSFCellStyle cellStyleCellNormal){
		HSSFCell cell = row.createCell(pos);


		if (val != null && !val.equals("null")){
			if(val instanceof Date){
				String fechaString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((Date)val);
				cell.setCellValue(fechaString);				
			}else{
				cell.setCellValue(val.toString());
			}

			switch (tipo) {
			case ENCABEZADO:
				cell.setCellStyle(cellStyleCellEmcabezado);
				break;
			case NUMERO:
				NumberFormat cf = NumberFormat.getNumberInstance();
				if(val != null){
					double dVal = ((BigDecimal)val).doubleValue();
					cell.setCellValue((cf.format(dVal)));
				}
				break;
			default:

				break;
			}
		}else{
			cell.setCellValue("");
		}
		if(rowColor){
			if(rowNum %2 == 0){				

				cell.setCellStyle(cellStyleCellNormalG);
			}
			else{
				cell.setCellStyle(cellStyleCellNormal);
			}
		}

	}

}
