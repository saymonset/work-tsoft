package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MailService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.PosicionCuentaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.VConsultaDerechosCapitalVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.derechos.util.Utils;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.GrupoRetencionInternacionalDto;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;

public class ConsultaTablaRetencionController extends ControllerBase{

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaTablaRetencionController.class);
    private ConsultaDerechosParam param;
    private boolean banderaInicio = true;
    private VConsultaDerechosCapitalVO derechoSeleccionado;
    private MailService mailService;
    private String proporcion;
    private List<Retencion> tablaRetenciones;
    private ControlDerechosService derechosService;
    private ControlCatalogosService catalogosService;
    private static final String ADMIN_BENEFICIARIOS_DERECHOS = "adminBeneficiariosDerecho";
    private String porcentajeRetencion;
    private String fee;
    private PaginaVO paginaReportes;
    private boolean requiereBeneficiarios;
    private static final Float PORCENTAJE_DEFAULT = Float.valueOf(30);
    private static final Float PORCENTAJE_DEFAULT_CERO = Float.valueOf(0);
    private static final float PORCENTAJE_MAXIMO = 99.999999f;
    private AdminCatalogosBenefService adminCatalogosBenefService;
    private Map<String, List<Retencion>> totaleXInstitucion;
    private Map<String, Retencion> subtotaleXInstitucion;
    private Map<Float,Retencion> subtotalesXPorcentaje;
    private Retencion total;
    private BigInteger totalTitulos;
    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private Map<String,String> parchePorcentajeRetencionXFecha;


    public String getInit() {
        if (this.banderaInicio) {
            this.banderaInicio = false;
            this.derechoSeleccionado = (VConsultaDerechosCapitalVO)
                    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_DERECHO_SELECCIONADO);
            this.requiereBeneficiarios = this.derechosService.requireBeneficiariosDerechoOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                                                    this.derechoSeleccionado.getIdEmision());
            this.param = (ConsultaDerechosParam)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("parametrosConsulta");
            this.ejecutarConsulta();
        }

        return null;
    }

   public String pagarDerecho(){
        LOG.info("Pagando derecho con id ["+this.derechoSeleccionado.getIdDerechoCapital()+"]");
        try {
            this.derechosService.pagarDerecho(this.derechoSeleccionado.getIdDerechoCapital());
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
            this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_PAGADO.longValue());
            this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
            String mensajeDerechoPagado = Constantes.ETQ_INI_STRONG + Constantes.MENSAJE_DERECHO_PAGADO + this.derechoSeleccionado.getIdDerechoCapital() + Constantes.ETQ_FIN_STRONG;
            this.mailService.sendMail(mensajeDerechoPagado);
            this.ejecutarConsulta();
            this.addMessage("El derecho se ha actualizado exitosamente");
            LOG.info("El derecho se pago correctamente");
            
            /** Se envia derecho al MAV **/
            LOG.info("Se envia derecho el MAV");
            //Ahora se procede a enviar la simulacion a MAV.
            FactorSimulacionMav factorSimulacionMav = null;
            SimulacionDerechoInternacionalDto dto = null;

            factorSimulacionMav = this.derechosService.getFactorSimulacion(this.derechoSeleccionado.getIdDerechoCapital());
            dto = new SimulacionDerechoInternacionalDto();
            dto.setProporcion(Utils.calculaProporcion(factorSimulacionMav));
            dto.setIdDerechoCapital(this.derechoSeleccionado.getIdDerechoCapital());
            dto.setGruposRetencion(this.createGruposRetencionMav(factorSimulacionMav));

            try {
                LOG.info("####### Llamando al EJB que envia simulacion al MAV...");
                this.derechosService.enviarSimulacionDerechoCapitalInter(dto);
                LOG.info("####### La simulacion se realizo con exito...");
            } catch (BusinessException e) {
                LOG.error(e.getMessage());
                this.addErrorMessage("Env\u00edo a MAV: " + e.getMessage());
                e.printStackTrace();
            } catch (EJBException e) {
                LOG.error(e.getMessage());
                if (e.getCausedByException().getCause() != null) {
                    this.addErrorMessage("Env\u00edo a MAV: " + e.getCausedByException().getCause().getMessage());
                }
                else {
                    this.addErrorMessage("Env\u00edo a MAV: " + e.getCausedByException().getMessage());
                }
                e.printStackTrace();
            }
            
            LOG.info("Derecho enviado al MAV");
            LOG.info("Comenzando a general excel");
            byte[] fichero = generateFileForSendEmail();
            try {
                LOG.info("Preparando envio de email :: " + fichero);
                this.mailService.sendMail(fichero, generaMessage(), Constantes.SUBJECT, Constantes.CODIFICACION, this.derechoSeleccionado.getIdDerechoCapital(),
                		this.derechoSeleccionado.getEmision().getIsin(), this.derechoSeleccionado.getFechaPago());
                LOG.info("Envio de email finalizado");
            } catch(Exception ex){
            	LOG.error("Ocurrio un error en el envio de email...");
            	ex.getCause();
            	ex.getMessage();
            }
            
        } catch (BusinessException e) {
            LOG.error(e.getMessage());
            this.addErrorMessage(e.getMessage());
        }
        return null;
    }
    
    /**
     * Metodo para configurar y almacenar en el excel
     * @return
     */
    public byte[] generateFileForSendEmail(){
    	LOG.debug("Comenzando a generar excel para enviar por correo");
    	HSSFWorkbook libro = new HSSFWorkbook();
    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] reporte = null;
		
		try {
			LOG.debug("{} Comenzando el llenado de encabezados de la tabla de retencion en el reporte en Excel");
			this.llenaHojaTablaRetencion(libro, libro.createSheet("Tabla de Retencion"), this.tablaRetenciones);
			LOG.debug("{} Concluyo el llenado de la hoja de tabla de retencion en el reporte en Excel...");
			libro.write(outStream);
			return outStream.toByteArray();
		} catch(Exception e) {
			LOG.error("{} Se presento un error inesperado al generar el reporte", e);
		} finally {
			try {
				outStream.close();
			} catch (IOException e) {
				LOG.debug("{} No se logro cerrar el ByteArrayOutputStream con que se genero el arreglo de bytes del reporte");
			}
		}
		return reporte;
    }
	
    public String generaMessage(){
    	//Se adjunta el reporte con la Tabla de Retencion del Derecho TV, EMISORA, SERIE, ISIN, FECHA PAGO:
    	String mensaje = Constantes.MENSAJE + this.derechoSeleccionado.getIdDerechoCapital() + "<br />";
    	mensaje = mensaje + Constantes.ETQ_INI_STRONG + Constantes.EMISORA + ": " + Constantes.ETQ_FIN_STRONG + this.derechoSeleccionado.getEmision().getEmisora() + Constantes.SALTO_LINEA;
    	mensaje = mensaje + Constantes.ETQ_INI_STRONG + Constantes.SERIE + ": " + Constantes.ETQ_FIN_STRONG + this.derechoSeleccionado.getEmision().getSerie() + Constantes.SALTO_LINEA;
    	mensaje = mensaje + Constantes.ETQ_INI_STRONG + Constantes.ISIN + ": " + Constantes.ETQ_FIN_STRONG + this.derechoSeleccionado.getEmision().getIsin() + Constantes.SALTO_LINEA;
    	
    	SimpleDateFormat dfNombreArchivo = new SimpleDateFormat("dd/MM/yyyy");
    	mensaje = mensaje + Constantes.ETQ_INI_STRONG + Constantes.FECHA_CORTE + ": " + Constantes.ETQ_FIN_STRONG + dfNombreArchivo.format(this.derechoSeleccionado.getFechaCorte()) + Constantes.SALTO_LINEA;
    	mensaje = mensaje + Constantes.ETQ_INI_STRONG + Constantes.FECHA_PAGO + ": " + Constantes.ETQ_FIN_STRONG + dfNombreArchivo.format(this.derechoSeleccionado.getFechaPago());
    	
    	return mensaje;
    }
    
    /**
     * Metodo para insertar los valores
     * @param hoja
     * @param tablaRetenciones
     * @param estilos
     */
	private void llenaHojaTablaRetencion(HSSFWorkbook libro, HSSFSheet hoja, List<Retencion> tablaRetenciones) {
		
		int index = 0;
		HSSFRow fila = hoja.createRow(index);
		
		/** ESTILOS **/
        // Se crea estilo celda
        HSSFCellStyle estiloCabecera = libro.createCellStyle(); 
		estiloCabecera.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		estiloCabecera.setFillBackgroundColor((IndexedColors.BLUE_GREY.getIndex()));
        
		HSSFFont font = libro.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short)10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloCabecera.setFont(font);
        
        HSSFCellStyle styleMoneda = libro.createCellStyle();
        styleMoneda.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleMoneda.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("$#,##0.00;-$#,##0.00"));
        
        HSSFCellStyle styleNumerico = libro.createCellStyle();
        styleNumerico.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleNumerico.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("#,##0;-#,##0"));
        
        HSSFCellStyle estiloFecha = libro.createCellStyle();
		estiloFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloFecha.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
		
		/** Se creea titulo del reporte **/
		fila.createCell(0).setCellValue("Consulta Tabla de Retenci\u00F3n del Derecho");
		fila.getCell(0).setCellStyle(estiloCabecera);
		
		index++;
		index++;
		fila = hoja.createRow(index);
		fila.createCell(0).setCellValue("Instituci\u00F3n:");
		fila.createCell(1).setCellValue(this.getAgenteFirmado().getId() + " " + this.getAgenteFirmado().getFolio() + " " + this.getAgenteFirmado().getRazon());
		
		index++;
		SimpleDateFormat dfFechaEstandar = new SimpleDateFormat("EEEE, dd 'de' MMMMM 'de' yyyy | HH:mm:ss", new Locale("es", "MX"));
		Date fechaHoy = new Date();
		String strFechaArchivo = dfFechaEstandar.format(fechaHoy);
		fila = hoja.createRow(index);
		fila.createCell(0).setCellValue("Fecha:");
		fila.createCell(1).setCellValue(strFechaArchivo);
		
		index++;
		index++;
		fila = hoja.createRow(index);
		fila.createCell(0).setCellValue("CRITERIOS CONSULTA");
		fila.getCell(0).setCellStyle(estiloCabecera);
		
        /** Se coloca valor de criterios de busqueda **/
		index++;
		index++;
		fila = hoja.createRow(index);
		fila.createCell(0).setCellValue("T.V.");
		fila.createCell(1).setCellValue("Divisa");
		fila.createCell(2).setCellValue("Emisora");
		fila.createCell(3).setCellValue("B\u00F3veda");
		fila.createCell(4).setCellValue("Serie");
		fila.createCell(5).setCellValue("Estado");
		fila.createCell(6).setCellValue("Cup\u00F3n");
		fila.createCell(7).setCellValue("Fecha de Corte Inicio");
		fila.createCell(8).setCellValue("ISIN");
		fila.createCell(9).setCellValue("Fecha de Pago");
		fila.createCell(10).setCellValue("Proporci\u00F3n");
        for(int j = 0; j<=10; j++){
        	fila.getCell(j).setCellStyle(estiloCabecera);
        }

		index++;
		fila = hoja.createRow(index);
		fila.createCell(0).setCellValue(this.derechoSeleccionado.getEmision().getTv());
		fila.createCell(1).setCellValue(this.derechoSeleccionado.getDivisa());
		fila.createCell(2).setCellValue(this.derechoSeleccionado.getEmision().getEmisora());
		fila.createCell(3).setCellValue(this.derechoSeleccionado.getBovedaCustodio());
		fila.createCell(4).setCellValue(this.derechoSeleccionado.getEmision().getSerie());
		fila.createCell(5).setCellValue(this.derechoSeleccionado.getDescEstatusDerecho());
		fila.createCell(6).setCellValue(this.derechoSeleccionado.getEmision().getCupon());
		fila.createCell(7).setCellValue(this.derechoSeleccionado.getFechaCorte());
		fila.getCell(7).setCellStyle(estiloFecha);
		
		fila.createCell(8).setCellValue(this.derechoSeleccionado.getEmision().getIsin());
		fila.createCell(9).setCellValue(this.derechoSeleccionado.getFechaPago());
		fila.getCell(9).setCellStyle(estiloFecha);
		
		fila.createCell(10).setCellValue(this.derechoSeleccionado.getProporcionStr());

		/** Se agregan los registros **/
		index++;
		index++;
		fila = hoja.createRow(index);
		/** Se crean los encabezados **/
		fila.createCell(0).setCellValue("Instituci\u00F3n");
		fila.createCell(1).setCellValue("Cuenta");
		fila.createCell(2).setCellValue("% de Retenci\u00F3n");
		fila.createCell(3).setCellValue("Cantidad de T\u00EDtulos");
		fila.createCell(4).setCellValue("Monto Bruto");
		fila.createCell(5).setCellValue("Impuesto Retenido");
		fila.createCell(6).setCellValue("Monto Neto");
		fila.createCell(7).setCellValue("Estado");
		
        for(int j = 0; j<=7; j++){
        	fila.getCell(j).setCellStyle(estiloCabecera);
        }
		
		/** Se crea el contenido **/
		index++;
		for (Retencion retencion : tablaRetenciones) {
			fila = hoja.createRow(index);
			
			fila.createCell(0).setCellValue(retencion.getClaveInstitucion() + " " + retencion.getNombreInstitucion());
			fila.createCell(1).setCellValue(retencion.getCuenta());
			
			fila.createCell(2).setCellValue((retencion.getPorcentajeRetencion() != null) ? retencion.getPorcentajeRetencion().toString() : null);
			fila.getCell(2).setCellStyle(styleNumerico);
			
			fila.createCell(3).setCellValue(retencion.getAsignacion());
			fila.getCell(3).setCellStyle(styleNumerico);
			
			fila.createCell(4).setCellValue((retencion.getMonto() != null) ? retencion.getMonto().doubleValue() : null);
			fila.getCell(4).setCellStyle(styleMoneda);
			
			fila.createCell(5).setCellValue((retencion.getMontoRetenido() != null) ? retencion.getMontoRetenido().doubleValue() : null);
			fila.getCell(5).setCellStyle(styleMoneda);
			
			fila.createCell(6).setCellValue((retencion.getMontoPagado() != null) ? retencion.getMontoPagado().doubleValue() : null);
			fila.getCell(6).setCellStyle(styleMoneda);
			
			fila.createCell(7).setCellValue(this.derechoSeleccionado.getDescEstatusDerecho());
			
			index++;
		}
		
		index++;
		/** Se agregan los subtotales **/
		List<Float> porcentaje = this.getPorcentajeKeys();
		for (Float porcentajeList : porcentaje) {
			LOG.debug("porcentajeList :: " + porcentajeList);
			fila = hoja.createRow(index);
			Iterator iter = this.subtotalesXPorcentaje.entrySet().iterator();
			while(iter.hasNext()){
				
				Map.Entry<Float,Retencion> entry = (Entry<Float, Retencion>) iter.next();
				
				fila.createCell(0).setCellValue("SubTotal");
				fila.getCell(0).setCellStyle(estiloCabecera);
				
				fila.createCell(1).setCellValue("");
				fila.createCell(2).setCellValue(porcentajeList);
				fila.getCell(2).setCellStyle(styleNumerico);
				
				fila.createCell(3).setCellValue(this.subtotalesXPorcentaje.get(entry.getKey()).getAsignacion());
				fila.getCell(3).setCellStyle(styleNumerico);
				
				fila.createCell(4).setCellValue((this.subtotalesXPorcentaje.get(entry.getKey()).getMonto() != null) ? this.subtotalesXPorcentaje.get(entry.getKey()).getMonto().doubleValue() : null);
				fila.getCell(4).setCellStyle(styleMoneda);
				
				fila.createCell(5).setCellValue((this.subtotalesXPorcentaje.get(entry.getKey()).getMontoRetenido() != null) ? this.subtotalesXPorcentaje.get(entry.getKey()).getMontoRetenido().doubleValue() : null);
				fila.getCell(5).setCellStyle(styleMoneda);
				
				fila.createCell(6).setCellValue((this.subtotalesXPorcentaje.get(entry.getKey()).getMontoPagado() != null) ? this.subtotalesXPorcentaje.get(entry.getKey()).getMontoPagado().doubleValue() : null);
				fila.getCell(6).setCellStyle(styleMoneda);
			}
		}
		
		/** Se agregan los totales **/
		index++;
		index++;
		fila = hoja.createRow(index);
		
		fila.createCell(0).setCellValue("TOTAL");
		fila.getCell(0).setCellStyle(estiloCabecera);
		
		fila.createCell(1).setCellValue("");
		fila.createCell(2).setCellValue("");
		fila.createCell(3).setCellValue(this.totalTitulos.longValue());
		fila.getCell(3).setCellStyle(styleNumerico);
		fila.getCell(3).setCellStyle(styleNumerico);
		
		fila.createCell(4).setCellValue((this.total.getMonto() != null) ? this.total.getMonto().doubleValue() : null);
		fila.getCell(4).setCellStyle(styleMoneda);
		
		fila.createCell(5).setCellValue((this.total.getMontoRetenido() != null) ? this.total.getMontoRetenido().doubleValue() : null);
		fila.getCell(5).setCellStyle(styleMoneda);
		
		fila.createCell(6).setCellValue((this.total.getMontoPagado() != null) ? this.total.getMontoPagado().doubleValue() : null);
		fila.getCell(6).setCellStyle(styleMoneda);
		
	}

    /**
     * Confirmacion del derecho.
     * @return
     */
    public String confirmarDerecho() {
        LOG.info("Confirmando derecho con id [" + this.derechoSeleccionado.getIdDerechoCapital() + "]");
        try{
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(), Constantes.ID_ESTATUS_DERECHO_CONFIRMADO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
    	} catch(Exception ex){
    		this.addErrorMessage("Ocurrio un error en el derecho cuando se ha confirmado: " + ex.getMessage());
    		return null;
    	}   
        
        this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_CONFIRMADO.longValue());
        this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
        this.ejecutarConsulta();
        this.addMessage("El derecho se ha confirmado exitosamente");
        LOG.info("El derecho se ha confirmado exitosamente");
        return null;
    }

    public String regresar(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(Constantes.KEY_DERECHO_SELECCIONADO, this.derechoSeleccionado);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("parametrosConsulta", this.param);
        return ADMIN_BENEFICIARIOS_DERECHOS;
    }

    /**
     * Autorizacion del derecho.
     * @return
     */
    public String autorizarDerecho() {
        LOG.info("####### Autorizando el derecho con id [" + this.derechoSeleccionado.getIdDerechoCapital() + "]");

        //Primero se realiza la actualizacion del estado del derecho.
        try {
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(), Constantes.ID_ESTATUS_DERECHO_AUTORIZADO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
    	} catch(Exception ex){
    		this.addErrorMessage("Ocurrio un error en el derecho cuando se ha autorizado: " + ex.getMessage());
    		return null;
    	}       

        this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_AUTORIZADO.longValue());
        this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
        this.ejecutarConsulta();
        this.addMessage("El derecho se ha autorizado exitosamente");
        LOG.info("####### El derecho se ha autorizado exitosamente");
        return null;
    }

    public String simulacionMav(){
        FactorSimulacionMav factorSimulacionMav = null;
        String error = null;

        error = this.validaCampos();
        if(StringUtils.isNotBlank(error)){
            this.addErrorMessage(error);
            return null;
        }

        factorSimulacionMav = new FactorSimulacionMav();
        factorSimulacionMav.setIdDerechoCapital(this.derechoSeleccionado.getIdDerechoCapital());
        factorSimulacionMav.setProporcion(Float.valueOf(this.proporcion));
        if(StringUtils.isNotBlank(this.fee)){
            factorSimulacionMav.setFee(Float.valueOf(this.fee));
        }
        if(StringUtils.isNotBlank(this.porcentajeRetencion)){
            factorSimulacionMav.setPorcentajeRetencion(Float.valueOf(this.porcentajeRetencion));
        }
        this.derechosService.guardaFactorSimulacion(factorSimulacionMav);
        if(!this.derechosService.existeBeneficiarioDefault(this.derechoSeleccionado.getIdDerechoCapital())){
            this.derechosService.agregaBeneficiarioDefault(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_PREVIO);
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_PREVIO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
        } else{
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_PREVIO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
        }

        if(!this.derechosService.existeBeneficiarioDefault(this.derechoSeleccionado.getIdDerechoCapital())){
            this.derechosService.agregaBeneficiarioDefault(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_PREVIO);
        }
        this.derechoSeleccionado.setProporcion(factorSimulacionMav.getProporcion());
        this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_PREVIO.longValue());
        this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
        this.ejecutarConsulta();
        this.addMessage("El previo del derecho se calculo exitosamente");
        LOG.info("El previo del derecho se calculo exitosamente");
        return null;
    }

    @Override
    public String ejecutarConsulta(){
    	Long tiempoConsulta=System.currentTimeMillis();
        AgenteVO agenteVO = null;
        Double porcentajeTabla = null;
        FactorSimulacionMav factorSimulacionMav = null;
        Long[] idsCuentas = null;
        List<VConsultaBeneficiarioDerechosCuenta> lstBeneficiarios = null;
        TreeMap<Long, PosicionCuenta> totalesXCuenta = null;
        Map<Long, Map<Float, Long>> tablaDistribucion = null;
        Map<Float, Long> totalXPorcentaje = null;
        PosicionCuenta tmp = null;
        Long total = null;
        Iterator<Float> iter = null;
        Retencion retencion = null;
        Float porcentajeRetencionTmp = null;
        BigDecimal posicionTmp = null;
        BigDecimal proporcionTmp = null;
        BigDecimal porcentajeRetTmp = null;
        BigDecimal montoSinFee = null;
        Float porcentajeNoAsignado = null;
        Float proporcionDerecho = null;
        TreeMap<String, Retencion> listSort = null;

        factorSimulacionMav = this.derechosService.getFactorSimulacion(this.derechoSeleccionado.getIdDerechoCapital());
        if (factorSimulacionMav != null) {
            proporcionDerecho = Utils.calculaProporcion(factorSimulacionMav);
            this.proporcion = factorSimulacionMav.getProporcion().toString();
            if (factorSimulacionMav.getFee() != null) {
                this.fee = Constantes.DECIMAL_FORMAT_FEE.format(factorSimulacionMav.getFee());
            }
            if (factorSimulacionMav.getPorcentajeRetencion() != null) {
                this.porcentajeRetencion = Constantes.DECIMAL_FORMAT_PORCENTAJE_RET.format(factorSimulacionMav.getPorcentajeRetencion());
            }
        }
        else {
            proporcionDerecho = this.derechoSeleccionado.getProporcion();
            this.proporcion = Constantes.DECIMAL_FORMAT_PROPORCION.format(proporcionDerecho);
        }

        if (!this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
            agenteVO = this.getAgenteFirmado();
            LOG.info("\n\n####### Consultando beneficiarios por id folio....\n\n");
            this.setPaginaVO(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),
                                                                    Integer.valueOf(agenteVO.getId()), agenteVO.getFolio(), 
                                                                    this.paginaVO));
        }
        else {
            LOG.info("\n\n####### Consultando todos los beneficiarios...\n\n");
            this.setPaginaVO(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                              this.paginaVO));
        }

        if (this.paginaVO != null && this.paginaVO.getRegistros() != null && !this.paginaVO.getRegistros().isEmpty()) {
            totalesXCuenta = new TreeMap<Long, PosicionCuenta>();
            tablaDistribucion = new HashMap<Long, Map<Float,Long>>();
            for (PosicionCuentaVO posicion:(List<PosicionCuentaVO>)this.paginaVO.getRegistros()) {
                tmp = new PosicionCuenta();
                tmp.transformVO(posicion);
                totalesXCuenta.put(tmp.getIdCuentaNombrada(), tmp);
                tablaDistribucion.put(tmp.getIdCuentaNombrada(), null);
            }

            idsCuentas = totalesXCuenta.keySet().toArray(new Long[]{});
//            lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
//                                                                                     idsCuentas);
            lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizadoSimplificado(this.derechoSeleccionado.getIdDerechoCapital(), 
                    idsCuentas,null,null);

            
            
            if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
                for (VConsultaBeneficiarioDerechosCuenta derechoBeneficiario : lstBeneficiarios) {
                    totalXPorcentaje = tablaDistribucion.get(derechoBeneficiario.getIdCuentaNombrada());
                    totalXPorcentaje = totalXPorcentaje == null? new HashMap<Float, Long>() : totalXPorcentaje;

                    if (this.derechoSeleccionado.getIdTipoDerecho().intValue() == Constantes.ID_DERECHO_DISTRIBUCION && 
                        this.adminCatalogosBenefService.tieneEmisionPorcentajeCeroOptimizado(this.derechoSeleccionado.getIdEmision())) {
                        LOG.info("\n\n####### Se pondra el porcentaje de retencion a 0 ya que es distribucion...\n\n");
                        porcentajeTabla = Constantes.CERO_DOUBLE;
                    }
                    else {
                        LOG.info("\n\n####### Se pondra el porcentaje de retencion del beneficiario...\n\n");
                        porcentajeTabla = derechoBeneficiario.getPorcentajeRetencion();
                    }

                    if (totalXPorcentaje.containsKey(porcentajeTabla.floatValue())) {
                        total = totalXPorcentaje.get(porcentajeTabla.floatValue());
                        total +=  derechoBeneficiario.getPosicion();
                    }
                    else {
                        total =  derechoBeneficiario.getPosicion();
                    }
                    totalXPorcentaje.put(porcentajeTabla.floatValue(), total);
                    tablaDistribucion.put(derechoBeneficiario.getIdCuentaNombrada(), totalXPorcentaje);
                }
            }
        }

        if (this.requiereBeneficiarios) {
            porcentajeNoAsignado = this.derechosService.getPorcentajeRetDefaultOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                                          this.derechoSeleccionado.getIdEmision());
            if (porcentajeNoAsignado != null) {
                //Parche para porcentaje de retencion por default por intervalo de fechas
                porcentajeNoAsignado = this.parchaPorcentajeRetencionDefault(this.derechoSeleccionado, porcentajeNoAsignado);
            }
        }

        listSort = new TreeMap<String, Retencion>();
        this.tablaRetenciones = new ArrayList<Retencion>();
        for(PosicionCuenta posicionCuenta:totalesXCuenta.values()){
        	Long porcentajeAsignado = 0L;
            if(tablaDistribucion.get(posicionCuenta.getIdCuentaNombrada()) != null){
                iter = tablaDistribucion.get(posicionCuenta.getIdCuentaNombrada()).keySet().iterator();
                while(iter.hasNext()){
                    LOG.debug("\n\n####### Entro al while...\n\n");
                    porcentajeRetencionTmp = iter.next();
                    retencion = new Retencion();
                    retencion.setAsignacion(tablaDistribucion.get(posicionCuenta.getIdCuentaNombrada()).get(porcentajeRetencionTmp));
                    retencion.setCuenta(posicionCuenta.getCuenta());
                    retencion.setIdCuentaNombrada(posicionCuenta.getIdCuentaNombrada());
                    retencion.setPorcentajeRetencion(Float.valueOf(porcentajeRetencionTmp.floatValue()));
                    retencion.setNombreInstitucion(posicionCuenta.getNombreInstitucion());
                    retencion.setClaveInstitucion(posicionCuenta.getClaveInstitucion());
                    if(proporcionDerecho != null && proporcionDerecho.floatValue() > 0){
                        posicionTmp = new BigDecimal(retencion.getAsignacion());
                        proporcionTmp = new BigDecimal(proporcionDerecho.toString());

                        // Actualizacion de la forma de realizar los calculos, se realiza de la misma forma que en MAV
                        // para que los valores salgan iguales.
                        BigDecimal porcRetDeRet = new BigDecimal(retencion.getPorcentajeRetencion());
                        porcentajeRetTmp = porcRetDeRet.movePointLeft(2);
                        BigDecimal porcentajeRetDif = new BigDecimal(1).subtract(porcentajeRetTmp);

                        BigDecimal montoBruto = posicionTmp.multiply(proporcionTmp);
                        retencion.setMonto(montoBruto.setScale(2, RoundingMode.HALF_UP));

                        BigDecimal impuestoRetenido = montoBruto.multiply(porcentajeRetTmp);
                        retencion.setMontoRetenido(impuestoRetenido.setScale(2, RoundingMode.HALF_UP));

                        BigDecimal montoNeto = porcentajeRetDif.multiply(montoBruto);
                        retencion.setMontoPagado(montoNeto.setScale(2, RoundingMode.HALF_UP));

                        //limpieza de variables
                        porcRetDeRet = null;
                        porcentajeRetDif = null;
                        montoBruto = null;
                        impuestoRetenido = null;
                        montoNeto = null;
                        if(!this.requiereBeneficiarios && factorSimulacionMav != null && factorSimulacionMav.getFee() != null){
                            montoSinFee =  posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getProporcion().floatValue()));
                            retencion.setMontoRetenido(montoSinFee.multiply(porcentajeRetTmp));
                            retencion.setMontoFee(posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getFee().floatValue())));
                            retencion.setMonto(posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getProporcion().floatValue())));
                        }
                    }else{
                        retencion.setMonto(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                        retencion.setMontoRetenido(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                        retencion.setMontoPagado(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                        retencion.setMontoFee(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                    }
                    listSort.put(retencion.getClaveInstitucion()+retencion.getCuenta()+retencion.getPorcentajeRetencion(), retencion);
                    posicionCuenta.setPosicion(posicionCuenta.getPosicion()-retencion.getAsignacion());
                    if(porcentajeNoAsignado != null && (porcentajeNoAsignado.longValue() == retencion.getPorcentajeRetencion().longValue())){
                        porcentajeAsignado = porcentajeAsignado + retencion.getAsignacion();
                    }
                }
            }

            if(posicionCuenta.getPosicion().longValue() > 0){
                retencion = new Retencion();
//                retencion.setAsignacion(posicionCuenta.getPosicion());
                retencion.setAsignacion(posicionCuenta.getPosicion() + porcentajeAsignado);
                retencion.setCuenta(posicionCuenta.getCuenta());
                retencion.setIdCuentaNombrada(posicionCuenta.getIdCuentaNombrada());

                if(this.requiereBeneficiarios){
                    LOG.info("\n\n####### Se requiere beneficiarios...\n\n");
                    if(porcentajeNoAsignado != null){
                        LOG.info("\n\n####### Asignado el porcentaje de retencion de [" + porcentajeNoAsignado + "] del custodio\n\n");
                        retencion.setPorcentajeRetencion(porcentajeNoAsignado);
                    }
                    else{
                        LOG.info("\n\n####### Asignado el porcentaje de retencion de [" + PORCENTAJE_DEFAULT + "]\n\n");
                        retencion.setPorcentajeRetencion(PORCENTAJE_DEFAULT);
                    }
                }
                else{
                    if(factorSimulacionMav != null && factorSimulacionMav.getPorcentajeRetencion() != null) {
                        retencion.setPorcentajeRetencion(factorSimulacionMav.getPorcentajeRetencion());
                    }
                    else{
                        retencion.setPorcentajeRetencion(PORCENTAJE_DEFAULT_CERO);
                    }
                }

                retencion.setNombreInstitucion(posicionCuenta.getNombreInstitucion());
                retencion.setClaveInstitucion(posicionCuenta.getClaveInstitucion());
                if(proporcionDerecho != null && proporcionDerecho.floatValue() > 0){
                    posicionTmp = new BigDecimal(retencion.getAsignacion().longValue());
                    proporcionTmp = new BigDecimal(proporcionDerecho.toString());

                    // Actualizacion de la forma de realizar los calculos, se realiza de la misma forma que en MAV
                    // para que los valores salgan iguales.
                    BigDecimal porcRetDeRet = new BigDecimal(retencion.getPorcentajeRetencion());
                    porcentajeRetTmp = porcRetDeRet.movePointLeft(2);
                    BigDecimal porcentajeRetDif = new BigDecimal(1).subtract(porcentajeRetTmp);

                    BigDecimal montoBruto = posicionTmp.multiply(proporcionTmp);
                    retencion.setMonto(montoBruto.setScale(2, RoundingMode.HALF_UP));

                    BigDecimal impuestoRetenido = montoBruto.multiply(porcentajeRetTmp);
                    retencion.setMontoRetenido(impuestoRetenido.setScale(2, RoundingMode.HALF_UP));

                    BigDecimal montoNeto = porcentajeRetDif.multiply(montoBruto);
                    retencion.setMontoPagado(montoNeto.setScale(2, RoundingMode.HALF_UP));

                    //limpieza de variables
                    porcRetDeRet = null;
                    porcentajeRetDif = null;
                    montoBruto = null;
                    impuestoRetenido = null;
                    montoNeto = null;
                    if(!this.requiereBeneficiarios && factorSimulacionMav != null && factorSimulacionMav.getFee() != null){
                        montoSinFee =  posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getProporcion().floatValue()));
                        retencion.setMontoRetenido(montoSinFee.multiply(porcentajeRetTmp));
                        retencion.setMontoFee(posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getFee().floatValue())));
                        retencion.setMonto(posicionTmp.multiply(new BigDecimal(factorSimulacionMav.getProporcion().floatValue())));
                    }
                }
                else{
                    retencion.setMonto(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                    retencion.setMontoRetenido(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                    retencion.setMontoPagado(com.indeval.portaldali.middleware.servicios.util.constants.Constantes.BIG_DECIMAL_ZERO);
                }
                listSort.put(retencion.getClaveInstitucion()+retencion.getCuenta()+retencion.getPorcentajeRetencion(), retencion);
            }
        }
        if(listSort != null && !listSort.isEmpty()){
            this.tablaRetenciones.addAll(listSort.values());
            this.calculaTotales();
        }

        LOG.info("Tiempo de consulta :"+(System.currentTimeMillis()-tiempoConsulta)+" milisegundos");
        return null;
    }

    /**
     * PARCHA EL Porcentaje de retencion por default para que se pueda poner por un cierto rango de fechas
     * Utiliza un mapa que setea porcentajes de retencion por default dependiendo de la fecha de la tabla T_HORARIOS_BENEFICIARIO
    KEY: debe ser el nombre que tenga el custodio en C_CATBIC
    VALUE:[fecha a partir de la cual hacia atras validara fecha_corte]|[porcentaje de retencion]
    si son varias fechas, va delimitado por un ";" y entre fecha y porcentaje por un "|"
    Ejemplo:  key="JP MORGAN" value="05/05/2016|32;02/05/2016|25;30/04/2016|28;"
     *****NOTA IMPORTANTE: PONER FECHAS EN ESTRICTO ORDEN DESCENDENTE es decir, de mayor a menor****
     *el mapa se encuentra en applicationContext.xml
     * @param derecho
     */
    private Float parchaPorcentajeRetencionDefault(final VConsultaDerechosCapitalVO derecho,final Float porcentajeRetNoAsignado) {
        Float porcentajeRet=porcentajeRetNoAsignado;
        if (this.parchePorcentajeRetencionXFecha != null && derecho != null && derecho.getFechaPago() != null){
            String porcentajeFecha = this.parchePorcentajeRetencionXFecha.get(derecho.getBovedaCustodio());
            if(porcentajeFecha != null){
                String[] fechasPorc = porcentajeFecha.split(";");
                for (String fc : fechasPorc){
                    String[] fp = fc.split("\\|");
                    if(fp != null && fp.length == 2){
                        try {
                            Date menorAFecha= this.df.parse(fp[0]);
                            if(!StringUtils.isNumeric(fp[1])){
                                throw new ParseException("Valor del parche de porcentaje de retencion por fecha No es un numero",0);
                            }
                            if(derecho.getFechaPago().compareTo(menorAFecha) <= 0){
                                porcentajeRet= Float.valueOf(fp[1]);
                            }
                        } catch (ParseException e) {
                            LOG.error("NO SE PUDO PARSEAR UNA FECHA PARA CAMBIO DE PORCENTAJE RETENCION CAPITALES idDerechoCapital:"+derecho.getIdDerechoCapital(),e);

                        }
                    }
                }
            }
        }
        return porcentajeRet;
    }

    private List<GrupoRetencionInternacionalDto> createGruposRetencionMav(final FactorSimulacionMav factorSimulacionMav){
        List<GrupoRetencionInternacionalDto> internacionalDtos = null;
        GrupoRetencionInternacionalDto tmp = null;
        if(this.tablaRetenciones != null && !this.tablaRetenciones.isEmpty()){
            internacionalDtos = new ArrayList<GrupoRetencionInternacionalDto>();
            for(Retencion  retencion:this.tablaRetenciones){
                tmp = new GrupoRetencionInternacionalDto();
                tmp.setAsignacion(retencion.getAsignacion());
                tmp.setIdCuentaNombrada(retencion.getIdCuentaNombrada());
                if(this.requiereBeneficiarios){
                    tmp.setPorcentajeRetencion(retencion.getPorcentajeRetencion());
                }else{
                    tmp.setPorcentajeRetencion(factorSimulacionMav.getPorcentajeRetencion());
                }
                internacionalDtos.add(tmp);
            }
        }
        return internacionalDtos;
    }

    private String getDescripcionEstatus(final Integer idEstatus){
        String descEstatus = null;
        List<EstatusDerecho> estatusDerechos = this.catalogosService.getEstatusDerecho();
        for(EstatusDerecho estatusDerecho:estatusDerechos){
            if(estatusDerecho.getIdEstatusDerecho().intValue() == idEstatus.intValue()){
                descEstatus = estatusDerecho.getDescEstatusDerecho();
                break;
            }
        }
        return descEstatus;
    }

    public void generarReportes(final ActionEvent event) {
        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);
        this.paginaReportes.setTotalRegistros(this.tablaRetenciones.size());
        this.paginaReportes.setRegistros(this.tablaRetenciones);
    }

    public String cambiarAPrevio(){
        LOG.info("Cambiar a previo del derecho con id [" + this.derechoSeleccionado.getIdDerechoCapital() + "]");
    	try{
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_PREVIO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
    	} catch(Exception ex){
    		this.addErrorMessage("Ocurrio un error en el derecho cuando ha cambiado a previo: " + ex.getMessage());
    		return null;
    	}

        this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_PREVIO.longValue());
        this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
        this.ejecutarConsulta();
        this.addMessage("El derecho se ha cambiado a previo exitosamente");
        LOG.info("El derecho se ha cambiado a previo exitosamente");
        return null;
    }

    public String cambiarAAutorizado(){
        LOG.info("Cambiar a Autorizado el derecho con id [" + this.derechoSeleccionado.getIdDerechoCapital() + "]");
    	try {
            this.derechosService.actualizarEstatusDerecho(this.derechoSeleccionado.getIdDerechoCapital(),Constantes.ID_ESTATUS_DERECHO_AUTORIZADO, Boolean.FALSE);
            this.derechosService.generaMensajeHistoricoCapitales(this.derechoSeleccionado.getIdDerechoCapital());
    	} catch(Exception ex){
    		this.addErrorMessage("Ocurrio un error en el derecho cuando ha cambiado a autorizado: " + ex.getMessage());
    		return null;
    	}

        this.derechoSeleccionado.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_AUTORIZADO.longValue());
        this.derechoSeleccionado.setDescEstatusDerecho(this.getDescripcionEstatus(this.derechoSeleccionado.getIdEstatusDerecho().intValue()));
        this.ejecutarConsulta();
        this.addMessage("El derecho se ha cambiado a autorizado exitosamente");
        LOG.info("El derecho se ha cambiado a autorizado exitosamente");
        return null;
    }

    private String validaCampos(){
        String error = null;
        Float feeTmp = null;
        Float proporcionTmp = null;
        Float porTmp = null;

        if(StringUtils.isBlank(this.proporcion)){
            error = "La proporcion es requerida y debe ser numerica";
        }else{
            try {
                proporcionTmp = Float.valueOf(this.proporcion);
            } catch (NumberFormatException e) {
                error = "La proporcion debe ser numerica";
            }
        }
        
        if(StringUtils.isBlank(error) && !this.requiereBeneficiarios){
            if(StringUtils.isBlank(this.porcentajeRetencion)){
                error = "El porcentaje de retencion es requerido y debe ser entero";
            }else{
                try {
                    porTmp = Float.valueOf(this.porcentajeRetencion);
                    if(porTmp.floatValue() > PORCENTAJE_MAXIMO){
                        error = "El porcentaje de retencion no debe ser mayor a "+PORCENTAJE_MAXIMO;
                    }
                } catch (NumberFormatException e) {
                    error = "El porcentaje de retencion debe ser numerico";
                }
            }
            if(StringUtils.isBlank(error) && !StringUtils.isBlank(this.fee)){
                try {
                    feeTmp = Float.valueOf(this.fee);
                    error = this.validaFee(feeTmp, proporcionTmp, porTmp);
                } catch (NumberFormatException e) {
                    error = "El FEE debe ser numerico";
                }
            }
        }
        return error;
    }

    private String validaFee(final Float fee,final Float porporcion,final Float porcentaje){
        String error = null;
        BigDecimal proporcionBig = new BigDecimal(porporcion.floatValue());
        BigDecimal feeBig = new BigDecimal(fee.floatValue());
        BigDecimal porcentajeBig = new BigDecimal(porcentaje).divide(Constantes.CIEN_BIGDECIMAL, 4, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal factorComparar = null;
        factorComparar = proporcionBig.multiply(Constantes.BIG_DECIMAL_UNO.subtract(porcentajeBig));
        if(feeBig.compareTo(factorComparar) > 0){
            error = "El Fee no puede ser mayor a "+Constantes.DECIMAL_FORMAT_PROPORCION.format(factorComparar);
        }
        return error;
    }

    private void calculaTotales(){
        List<Retencion> lstTmp = null;
        Map<String,Retencion> lstRetTmp = null;
        Map<String, Map<String,Retencion>> totaleXInstitucionTmp = new TreeMap<String, Map<String,Retencion>>();
        this.subtotaleXInstitucion = new HashMap<String, Retencion>();
        this.subtotalesXPorcentaje = new HashMap<Float, Retencion>();
        this.total = null;
        this.totalTitulos = null;
        for(Retencion tmp:this.tablaRetenciones){
            if(totaleXInstitucionTmp.containsKey(tmp.getClaveInstitucion())){
                lstRetTmp = totaleXInstitucionTmp.get(tmp.getClaveInstitucion());
            }else{
                lstRetTmp = new TreeMap<String, Retencion>();
            }
            lstRetTmp.put(tmp.getClaveInstitucion()+tmp.getCuenta()+tmp.getPorcentajeRetencion(), tmp);
            totaleXInstitucionTmp.put(tmp.getClaveInstitucion(), lstRetTmp);
            this.addSubtotalesPorcentaje(tmp);
            this.addSubtotalesXInstitucion(tmp);
            this.addTotal(tmp);
        }
        this.totaleXInstitucion = new TreeMap<String, List<Retencion>>();
        for(String inst : totaleXInstitucionTmp.keySet()){
            lstTmp = new ArrayList<Retencion>();
            lstTmp.addAll(totaleXInstitucionTmp.get(inst).values());
            this.totaleXInstitucion.put(inst, lstTmp);
        }
    }

    private void addSubtotalesPorcentaje(final Retencion retencion){
        Retencion tmpRetencion = null;
        this.subtotalesXPorcentaje = this.subtotalesXPorcentaje == null?new TreeMap<Float, Retencion>():this.subtotalesXPorcentaje;
        if(this.subtotalesXPorcentaje.containsKey(retencion.getPorcentajeRetencion())){
            tmpRetencion = this.subtotalesXPorcentaje.get(retencion.getPorcentajeRetencion());
            tmpRetencion.setAsignacion(Long.valueOf(tmpRetencion.getAsignacion().longValue()+retencion.getAsignacion().longValue()));
            tmpRetencion.setMonto(tmpRetencion.getMonto().add(retencion.getMonto()));
            if(retencion.getMontoFee() != null ){
                if(tmpRetencion.getMontoFee() == null){
                    tmpRetencion.setMontoFee(BigDecimal.ZERO);
                }
                tmpRetencion.setMontoFee(tmpRetencion.getMontoFee().add(retencion.getMontoFee()));
            }
            tmpRetencion.setMontoPagado(tmpRetencion.getMontoPagado().add(retencion.getMontoPagado()));
            tmpRetencion.setMontoRetenido(tmpRetencion.getMontoRetenido().add(retencion.getMontoRetenido()));
        }else{
            tmpRetencion = new Retencion();
            tmpRetencion.setAsignacion(retencion.getAsignacion());
            tmpRetencion.setMonto(retencion.getMonto());
            tmpRetencion.setMontoFee(retencion.getMontoFee());
            tmpRetencion.setMontoPagado(retencion.getMontoPagado());
            tmpRetencion.setMontoRetenido(retencion.getMontoRetenido());
        }

        this.subtotalesXPorcentaje.put(retencion.getPorcentajeRetencion(), tmpRetencion);
    }

    private void addSubtotalesXInstitucion(final Retencion retencion){
        Retencion tmp = null;
        this.subtotaleXInstitucion = this.subtotaleXInstitucion == null?new HashMap<String, Retencion>():this.subtotaleXInstitucion;
        if(this.subtotaleXInstitucion.containsKey(retencion.getClaveInstitucion())){
            tmp = this.subtotaleXInstitucion.get(retencion.getClaveInstitucion());
            tmp.setAsignacion(Long.valueOf(tmp.getAsignacion().longValue()+retencion.getAsignacion().longValue()));
            if( retencion.getMontoFee() != null){
                //cuando acumula y el tmp monto fee es nulo se genera un error, se inicializa en cero
                if(tmp.getMontoFee() == null ){
                    tmp.setMontoFee(BigDecimal.ZERO);
                }
                tmp.setMontoFee(tmp.getMontoFee().add(retencion.getMontoFee()));
            }
            tmp.setMonto(tmp.getMonto().add(retencion.getMonto()));
            tmp.setMontoPagado(tmp.getMontoPagado().add(retencion.getMontoPagado()));
            tmp.setMontoRetenido(tmp.getMontoRetenido().add(retencion.getMontoRetenido()));
        }else{
            tmp = new Retencion();
            tmp.setAsignacion(retencion.getAsignacion());
            tmp.setMonto(retencion.getMonto());
            tmp.setMontoFee(retencion.getMontoFee());
            tmp.setMontoPagado(retencion.getMontoPagado());
            tmp.setMontoRetenido(retencion.getMontoRetenido());
        }
        this.subtotaleXInstitucion.put(retencion.getClaveInstitucion(), tmp);
    }

    private void addTotal(final Retencion retencion){
        BigInteger titulos = new BigInteger(retencion.getAsignacion().toString());
        if(this.total == null){
            this.total = new Retencion();
            this.total.setMonto(retencion.getMonto());
            this.total.setMontoFee(retencion.getMontoFee());
            this.total.setMontoPagado(retencion.getMontoPagado());
            this.total.setMontoRetenido(retencion.getMontoRetenido());
        }else{
            this.total.setMonto(this.total.getMonto().add(retencion.getMonto()));
            if(this.total.getMontoFee() != null && retencion.getMontoFee() != null){
                this.total.setMontoFee(this.total.getMontoFee().add(retencion.getMontoFee()));
            }
            this.total.setMontoPagado(this.total.getMontoPagado().add(retencion.getMontoPagado()));
            this.total.setMontoRetenido(this.total.getMontoRetenido().add(retencion.getMontoRetenido()));
        }
        this.totalTitulos = this.totalTitulos == null?titulos:this.totalTitulos.add(titulos);

    }

    public boolean isBanderaInicio() {
        return this.banderaInicio;
    }

    public void setBanderaInicio(final boolean banderaInicio) {
        this.banderaInicio = banderaInicio;
    }

    public VConsultaDerechosCapitalVO getDerechoSeleccionado() {
        return this.derechoSeleccionado;
    }

    public void setDerechoSeleccionado(final VConsultaDerechosCapitalVO derechoSeleccionado) {
        this.derechoSeleccionado = derechoSeleccionado;
    }

    public String getProporcion() {
        return this.proporcion;
    }

    public void setProporcion(final String proporcion) {
        this.proporcion = proporcion;
    }

    public List<Retencion> getTablaRetenciones() {
        return this.tablaRetenciones;
    }

    public void setTablaRetenciones(final List<Retencion> tablaRetenciones) {
        this.tablaRetenciones = tablaRetenciones;
    }

    public void setDerechosService(final ControlDerechosService derechosService) {
        this.derechosService = derechosService;
    }

    public void setCatalogosService(final ControlCatalogosService catalogosService) {
        this.catalogosService = catalogosService;
    }

    public String getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    public void setPorcentajeRetencion(final String porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(final String fee) {
        this.fee = fee;
    }

    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

    public boolean isRequiereBeneficiarios() {
        return this.requiereBeneficiarios;
    }

    public void setMailService(final MailService mailService) {
        this.mailService = mailService;
    }

    public void setAdminCatalogosBenefService(
            final AdminCatalogosBenefService adminCatalogosBenefService) {
        this.adminCatalogosBenefService = adminCatalogosBenefService;
    }

    public Map<String, List<Retencion>> getTotaleXInstitucion() {
        return this.totaleXInstitucion;
    }

    public Map<Float, Retencion> getSubtotalesXPorcentaje() {
        return this.subtotalesXPorcentaje;
    }

    public Retencion getTotal() {
        return this.total;
    }

    public BigInteger getTotalTitulos() {
        return this.totalTitulos;
    }
    public List<String> getItemKeys() {
        List<String> keys = new ArrayList<String>();
        keys.addAll(this.totaleXInstitucion.keySet());
        return keys;
    }

    public List<Float> getPorcentajeKeys() {
        List<Float> keys = new ArrayList<Float>();
        keys.addAll(this.subtotalesXPorcentaje.keySet());
        return keys;
    }

    public Map<String, Retencion> getSubtotaleXInstitucion() {
        return this.subtotaleXInstitucion;
    }

    /** @return this.parchePorcentajeRetencionXFecha */
    public Map<String, String> getParchePorcentajeRetencionXFecha() {
        return this.parchePorcentajeRetencionXFecha;
    }

    /** @param parchePorcentajeRetencionXFecha to be set in this.parchePorcentajeRetencionXFecha */
    public void setParchePorcentajeRetencionXFecha(final Map<String, String> parchePorcentajeRetencionXFecha) {
        this.parchePorcentajeRetencionXFecha = parchePorcentajeRetencionXFecha;
    }

}