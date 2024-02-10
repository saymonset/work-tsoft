package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;

public class ReporteServiceImpl implements ReporteService {
	private DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private DateFormat dff = new SimpleDateFormat("ddMMyyyy_hh_mm_ss");
	 enum TipoCelda{ ENCABEZADO,NORMAL,NUMERO };
	
	private String ruta;
	private static final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);
	
	public boolean generaReporteConciliacionAuditoria(List<DetalleConciliacionEfectivoInt> listaDetalleConciliacion) {
		
		boolean reporteGenerado = false;
		
		try {
			System.out.println("ruta:" + ruta);
			FileOutputStream fileOut = new FileOutputStream(ruta + "/CE_CN_RA_" + dff.format(Calendar.getInstance().getTime()) + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Reporte Conciliación Efectivo Auditoría");
			
			for(int i=0; i<15;i++){
				worksheet.autoSizeColumn(i);
			}
			
			//titulo
			HSSFRow titulo = worksheet.createRow(0);
			HSSFCell celltitulo = titulo.createCell(0);
			celltitulo.setCellValue("Reporte Conciliación Efectivo Auditoría");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont f = workbook.createFont();
			f.setFontHeightInPoints((short) 16);			
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle.setFont(f);
			celltitulo.setCellStyle(cellStyle);
			//info
			HSSFRow InstTitulo = worksheet.createRow(2);
			HSSFCell cellInstTitulo = InstTitulo.createCell(0);
			cellInstTitulo.setCellValue("Institución: ");
			cellStyle = workbook.createCellStyle();
			f = workbook.createFont();						
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle.setFont(f);
			cellInstTitulo.setCellStyle(cellStyle);
			HSSFCell cellInstData = InstTitulo.createCell(1);
			cellInstData.setCellValue("12 001 S.D.INDEVAL,S.A. DE C.V.-SOCIOS");
			
			//fecha
			HSSFRow fechaTitulo = worksheet.createRow(3);
			HSSFCell cellfechaTitulo = fechaTitulo.createCell(0);
			cellfechaTitulo.setCellValue("Fecha: ");
			cellStyle = workbook.createCellStyle();
			f = workbook.createFont();						
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle.setFont(f);
			cellfechaTitulo.setCellStyle(cellStyle);
			HSSFCell cellFechaData = fechaTitulo.createCell(1);
			cellFechaData.setCellValue(sdf.format(Calendar.getInstance().getTime()));
			
			int rown = 6;			
			HSSFRow encabezado = worksheet.createRow(rown-1);
			//tabla encabezados
			createCell(workbook, encabezado, 0, "Folio Conciliación", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 1, "Referencia Mensaje", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 2, "Fecha de Balance", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 3, "Fecha Crédito Débito", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 4, "Entry Date", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 5, "Tipo Transacción/ Código Verificación" , TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 6, "Custodio/ BIC Code", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 7, "Cuenta", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 8, "Divisa", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 9, "Crédito", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 10, "Débito", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 11, "Referencia", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 12, "Supplementary", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 13, "Comentarios", TipoCelda.ENCABEZADO);
			createCell(workbook, encabezado, 14, "Usuario", TipoCelda.ENCABEZADO);
			
			for(DetalleConciliacionEfectivoInt det : listaDetalleConciliacion){
				HSSFRow row = worksheet.createRow(rown);
				
				createCell(workbook, row, 0, det.getConciliacionEfectivo().getFolioConciliacion(), TipoCelda.NORMAL);
				createCell(workbook, row, 1, det.getConciliacionEfectivo().getReferencia(), TipoCelda.NORMAL);
				createCell(workbook, row, 2, det.getConciliacionEfectivo().getFechaBalance(), TipoCelda.NORMAL);
				createCell(workbook, row, 3, det.getFechaValor(), TipoCelda.NORMAL);
				createCell(workbook, row, 4, det.getFechaRegistro(), TipoCelda.NORMAL);
				
				String tipoTransaccion = "";
				String codigoIdentificacion = "";
				
				if(det.getTipoTransaccionEfectivo() != null ){
					tipoTransaccion = det.getTipoTransaccionEfectivo().getTipoTransaccion();
				}
				if(det.getCodigoIdentificacionEfectivo() != null){
					codigoIdentificacion = det.getCodigoIdentificacionEfectivo().getCodigoIdentificacion();
				}
				createCell(workbook, row, 5, tipoTransaccion + "/" + codigoIdentificacion, TipoCelda.NORMAL);
				createCell(workbook, row, 6, det.getConciliacionEfectivo().getBicCode(), TipoCelda.NORMAL);
				createCell(workbook, row, 7, det.getConciliacionEfectivo().getCuenta(), TipoCelda.NORMAL);
				createCell(workbook, row, 8, det.getConciliacionEfectivo().getDivisa(), TipoCelda.NORMAL);
				
				
				if(det.getTipo().equals("C")){
					createCell(workbook, row, 9, det.getMonto(), TipoCelda.NUMERO);
				} else {
					createCell(workbook, row, 9, "", TipoCelda.NORMAL);
				}
				
				if(det.getTipo().equals("D")){
					createCell(workbook, row, 10, det.getMonto(), TipoCelda.NUMERO);
				} else {
					createCell(workbook, row, 10, "", TipoCelda.NORMAL);
				}
				
				createCell(workbook, row, 11, det.getReferenciaCuentahabiente(), TipoCelda.NORMAL);
				createCell(workbook, row, 12, det.getDetallesSuplementarios(), TipoCelda.NORMAL);
				
				if(det.getListaComentarioEfectivo() != null && det.getListaComentarioEfectivo().size() > 0){
					createCell(workbook, row, 13, det.getListaComentarioEfectivo().get(0).getComentario(), TipoCelda.NORMAL);
					createCell(workbook, row, 14, det.getListaComentarioEfectivo().get(0).getUsuario(), TipoCelda.NORMAL);
				}
				rown++;
			}

			workbook.write(fileOut);
			
			fileOut.flush();
			fileOut.close();
			reporteGenerado = true;
		} catch (Exception e) {
			log.error("Ocurrió un error al generar el reporte de auditoria.", e);
			reporteGenerado = false;
		}
		return reporteGenerado;
	}
	private void createCell(HSSFWorkbook workbook,HSSFRow row, int pos, Object val,TipoCelda tipo){
		HSSFCellStyle cellStyle = null;
		if (val != null){
			HSSFCell cell = row.createCell(pos);
			if(val instanceof Date){
				cell.setCellValue(sdf.format(val));				
			}else{
				cell.setCellValue(val.toString());
			}
			
			switch (tipo) {
			case ENCABEZADO:
				cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				HSSFFont f = workbook.createFont();						
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				cellStyle.setFont(f);
				break;
			case NUMERO:
				NumberFormat formatter = new DecimalFormat("#,###,###0.#########");
				cell.setCellValue((formatter.format(val)));
				break;
			default:
				break;
			}
			
			if(cellStyle != null){
				cell.setCellStyle(cellStyle);
			}
		}		
	}
	
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
}
