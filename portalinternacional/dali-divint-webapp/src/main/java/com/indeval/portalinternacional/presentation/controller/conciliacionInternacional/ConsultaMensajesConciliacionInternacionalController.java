/**
 * Bursatec - INDEVAL Portal DALI
 * 
 * Ago 12, 2011
 */
package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Controller mostrar los mensajes swift del calendario de emisiones de deuda ext
 * 
 * @author Luis Roberto Munoz
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConsultaMensajesConciliacionInternacionalController extends ControllerBase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaMensajesConciliacionInternacionalController.class);
	
	/** Servicio  */
	private DivisionInternacionalService divisionInternacionalService;
	/** Parametros enviados por el Request */
	private Map<String, String> params;
	
	private List<BitacoraMensajeConciliacionInt> resultados;
	
	
	
	
	public ConsultaMensajesConciliacionInternacionalController() {
		
	}
	
	public String ejecutarConsulta() {
		Long id=null;
		if(params.get("idConciliacion")!=null ){
			id=Long.valueOf(params.get("idConciliacion"));
		}
		if(id != null){
			resultados=divisionInternacionalService.consultaBitacoraMensajeConciliacionInt(id);
		}
		return null;
	}
	
	

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la pagina por
	 * primerva vez.
	 * 
	 * @return nulo, este metodo no requiere retornar un valor
	 */
	public String getInit() {
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsulta();
		return null;
	}

	/**
	 * Genera los reportes de Consulta de Emisiones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt){	
		exportFile();		
	}
	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
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
	 * @return the resultados
	 */
	public List<BitacoraMensajeConciliacionInt> getResultados() {
		return resultados;
	}

	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(List<BitacoraMensajeConciliacionInt> resultados) {
		this.resultados = resultados;
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
                log.debug("No se puede procesar por un error de I/O: ",e);
        } catch (DocumentException e) {			
        	 log.debug("El documento de reporte swift no esta bien formado: ",e);
		}
	} 
	
	private ByteArrayOutputStream getSwiftToPdf() throws DocumentException{
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Document doc = new Document(PageSize.LETTER);
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;
		docWriter = PdfWriter.getInstance(doc, baosPDF);
		doc.addTitle("mensajeSwiftConciliacion");
		doc.open();
		for(BitacoraMensajeConciliacionInt bms : this.resultados){
			
			doc.add(new Paragraph(
					"==========="+ "Statement of Holdings | idConciliacion: "+bms.getConciliacion()+" | "+formatDateToMxnTimeZone(bms.getFechaRecepcion(),null)+" | ================\n"));
			doc.add(new Paragraph(bms.getMensaje()));
			doc.add(new Paragraph("====================================FIN MENSAJE ========================\n"));
			doc.newPage();
		}
		doc.close();
		docWriter.close();
		return baosPDF;
	}
	
	private Date dateToMxnTimeZone(Date fecha){
		Calendar cal = Calendar.getInstance();
		   cal.setTime(fecha);		   		   
		   cal.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		   return cal.getTime();
	}
	private String formatDateToMxnTimeZone(Date fecha, String format){
		if(format == null || format.equals("")){
			format="dd/MM/yyyy HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		return sdf.format(fecha);
	}
}
