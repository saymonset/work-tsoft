/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author César Hernández
 *
 */
public class BitacoraConfirmacionIntController extends ControllerBase {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfirmacionesIntController.class);
	
	/** Servicio de confirmaciones */
	private ConfirmacionIntService confirmacionIntService;

	private Map<String, String> params;
	
	private BitacoraConfirmacion bitacoraConfirmacion;

	public String getInit() {
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsultaBitacora();
        
		return null;
	}
	
	/**
	 * Realiza la la consulta de la bitacora de confirmaciones
	 * @return
	 */
	public String ejecutarConsultaBitacora() {
		Long idConfirmacion = null;
		
		if(params.get("idConfirmacion") != null ){
			idConfirmacion = Long.valueOf(params.get("idConfirmacion"));
		}
		
		if(idConfirmacion != null){
			bitacoraConfirmacion = confirmacionIntService.consultaBitacoraConfirmacion(idConfirmacion);
		}
		
		return null;
	}
	
	/**
	 * generar un pdf a partir de la información
	 */
	private void exportFile() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response =(HttpServletResponse)context.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition","attachment;filename=mensajeSwift.pdf");
                
        try {
                response.getOutputStream().write(getSwiftToPdf().toByteArray());
                response.getOutputStream().flush();
                response.getOutputStream().close();
                context.responseComplete();
        } catch (IOException e) {
        	logger.debug("No se puede procesar por un error de I/O: ",e);
        } catch (DocumentException e) {			
        	logger.debug("El documento de reporte swift no esta bien formado: ",e);
		}
	} 
	
	private ByteArrayOutputStream getSwiftToPdf() throws DocumentException{
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Document doc = new Document(PageSize.LETTER);
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;
		docWriter = PdfWriter.getInstance(doc, baosPDF);
		doc.addTitle("mensajeConfirmacion");
		doc.open();			
		doc.add(new Paragraph(
				"==========="+ "Confirmacion | idConfirmacion: "+bitacoraConfirmacion.getIdConfirmacion()+" | "+formatDateToMxnTimeZone(bitacoraConfirmacion.getFechaRecepcion(),null)+" | ================\n"));
		doc.add(new Paragraph(bitacoraConfirmacion.getMensaje()));
		doc.add(new Paragraph("====================================FIN MENSAJE ========================\n"));
		doc.newPage();
		doc.close();
		docWriter.close();
		return baosPDF;
	}
	
	private String formatDateToMxnTimeZone(Date fecha, String format){
		if(format == null || format.equals("")){
			format="dd/MM/yyyy HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		return sdf.format(fecha);
	}
	
	public void generarReportes(ActionEvent evt){	
		exportFile();		
	}
	
	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the bitacoraConfirmacion
	 */
	public BitacoraConfirmacion getBitacoraConfirmacion() {
		return bitacoraConfirmacion;
	}

	/**
	 * @param bitacoraConfirmacion the bitacoraConfirmacion to set
	 */
	public void setBitacoraConfirmacion(BitacoraConfirmacion bitacoraConfirmacion) {
		this.bitacoraConfirmacion = bitacoraConfirmacion;
	}

	/**
	 * @return the confirmacionIntService
	 */
	public ConfirmacionIntService getConfirmacionIntService() {
		return confirmacionIntService;
	}

	/**
	 * @param confirmacionIntService the confirmacionIntService to set
	 */
	public void setConfirmacionIntService(
			ConfirmacionIntService confirmacionIntService) {
		this.confirmacionIntService = confirmacionIntService;
	}
}
