package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import java.util.regex.Pattern;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SaveEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoAltaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvcoDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Luis R Munoz
 *
 */
public class CapturaEventosCorporativosController extends ControllerBase{
	//propiedades que mapeamos del dialogo de captura de opciones
	private String txtHtmlAreaOpc; //mapeamos el textarea que contiene el texto capturado en formato HTML
	private boolean valDefault; //mapeamos el valor del checkbox Default
	private Date fechaCli;
	private String fc;
	private String fi;
	private String ftp;
	private Date fechaIn;
	private Date fechaTiPa;
	private String numOpcion;
	private String numNota;
	private String tipoOperacion;
	private String txtHtmlArea;
	private String txtOpcionHtml;
	private String txtNota;
	private String operacionNota;
	private String tipoConfiguracion;
	private String numeroDias;
	private String[] arrayDiasSem;
	private String diaMes;
	private String mesMes;
	private String numNotificacion;
	private String numOpcAccordion;
	private String operacionNotificacion;
	//Defino la lista que va a permitir renderear la captura en la tabla que muestra el resumen
//	private OpcionesEventoCorporativoVO opcionesVo;
	private OpcionesDTO opcionesVo;
	private List<OpcionesDTO> listOp = new ArrayList<OpcionesDTO>();
	private List<NotasDTO> listNot = new ArrayList<NotasDTO>();
	private static final String ADD_ACTION = "add";
	private static final String EDIT_ACTION = "edit";
	/**
	 * Dao para el acceso a la consulta de emisiones
	 */
	private EmisionDao emisionDao = null;	
	private HtmlPanelGrid gridAdjuntos;
	private String nombreAnclaOpcion;
	private String nombreAnclaNota;
	//variables del dialogo
	private String txtCuerpoOpcion;
	private String txtChkDefault;
	private String minutosNot;
	private String destinatarioNot;
	private String textoNot;
	private String fechaIncioNot;
	private String fechaFinNot;
	private String horaNot;
	private String periodoNot;
	private NotificacionesEvcoDTO notificacionesVo;
	private List<NotificacionesEvcoDTO> listNotificaciones = new ArrayList<NotificacionesEvcoDTO>();
	
	//seccion validaciones
	private String operacionValidacion;
	private String validacionVal;
	private String operadorVal;
	private String cantidadVal;
	private List<ValidacionesEvcoDTO> listValidaciones = new ArrayList<ValidacionesEvcoDTO>();
	private String numValidacion;
	
	Map<String, Object> mapResponse;
	Map<String, String> mapNotas;
	Map<String, UploadedFile> mapAdjuntos;
	List<OpcionesDTO> listOpciones;
	OpcionesDTO vo;
	NotasDTO notasVo;
	ArchivosAdjuntosEvcoDTO archivoVo;
	ValidacionesEvcoDTO validacionesVo;
	Map<String,OpcionesDTO> mapOpciones;
	Map<String, NotasDTO> mapNotasEvco;
	Map<String, ArchivosAdjuntosEvcoDTO> mapAdjuntosEvco;
	Map<String, NotificacionesEvcoDTO> mapNotificacionesEvco;
	Map<String, ValidacionesEvcoDTO> mapValidacionesEvco;
	List<ArchivosAdjuntosEvcoDTO> listAdjuntos;
	
	private UploadedFile archivo;
	
	
//	private Pattern pattern; 
	private static final Logger log = LoggerFactory.getLogger(CapturaEventosCorporativosController.class);
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	private SaveEventosCorporativosService saveEventosCorporativosService;
	
	
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaEstatus;	
	private List<SelectItem> listaTipoMercado;
	private List<SelectItem> listaTipoDerechoMO;
	private List<SelectItem> listaTipoDerechoML;
	private List<SelectItem> listaTipoEvento;

	
	private EventoCorporativoAltaDTO altaEvCo;
	
	private String idEventoCorporativo;
	private Date fechaCreacion;	
	private Date fechaRegistro;	
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String tipoDerechoMO;
	private String tipoDerechoML;
	private String tipoMercado;
	private String estado;
	private Date fechaIndeval;	
	private Date fechaCliente;	
	private Date fechaPago;	
	private String custodio;	
	private String tipoEvento;	
	private String piePaginaHtml;
	private String piePaginaText;
	private String cuerpoEventoHtml;
	private String cuerpoEventoText;	
	private boolean isEdicion;
	private String operacion;
	private int elementos;
	private int pivote;
	private int notifContador;
	private int valiContador;
	private static final String patron = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]\\s[0-9][0-9]:[0-9][0-9]";
	private static final String patronSinHora = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat formatSinHora = new SimpleDateFormat("dd/MM/yyyy");

	private boolean banderaCreacion;
	private boolean emisionEncontrada; 
	private DivisionInternacionalService divisionInternacionalService;
	private List<SelectItem> listaCustodiosBuffer =new ArrayList<SelectItem>();
	private List<Custodio> custodiosConsulta = new ArrayList<Custodio>();
	private boolean bloqueaCustodio;
	private String fileNameBorrar;
	
    
    public CapturaEventosCorporativosController(){
    	//getRequestParamEvCo();
    	this.mapResponse = new LinkedHashMap<String, Object>();
    	this.mapNotas = new LinkedHashMap<String, String>();
    	this.elementos = 0;
    	this.mapAdjuntos = new LinkedHashMap<String, UploadedFile>();
    	this.listAdjuntos = new ArrayList<ArchivosAdjuntosEvcoDTO>();
    	this.mapAdjuntosEvco = new LinkedHashMap<String, ArchivosAdjuntosEvcoDTO>();
    	this.pivote = 0;
    	this.notifContador = 0;
    	this.valiContador = 0;
    	
    }
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		getRequestParamEvCo();
		if(this.isEdicion){
			if(paginaVO == null)
				paginaVO = new PaginaVO();
			
			
			this.idEventoCorporativo=null;
			this.fechaCreacion=null;			
			this.fechaRegistro=null;			
			this.tipoValor="";
			this.emisora="";
			this.serie="";
			this.isin="";
			this.tipoDerechoMO="-1";
			this.tipoDerechoML="-1";
			this.tipoMercado="-1";
			this.tipoEvento="-1";
			this.estado="-1";
			this.fechaIndeval=null;			
			this.fechaCliente=null;			
			this.fechaPago=null;			
			this.custodio="-1";
			this.banderaCreacion=false;
		}
		this.operacion = "captura";
		inicializaCustodios();
		
		inicializaEstatus();
		inicializaTipoMercado();
		inicializaTiposDerecho();
		inicializaTiposEvento();
		
		return null;
	}
	
	
	public void addOpciones(ActionEvent event){
		log.info("Ingreso al metodo addOpciones");
		if(ADD_ACTION.equals(tipoOperacion)){
			//recupero los datos mapeados del dialogo y lo ingreso a la lista que pintara el resumen de las opciones capturadas
			log.info("Ingreso al metodo addOpciones");
			opcionesVo = new OpcionesDTO();
			opcionesVo.setCuerpoOpcion(txtHtmlArea);

			if(valDefault){
				opcionesVo.setValorDefault("Opci\u00F3n por Default");
			}
			opcionesVo.setFechaCliente(fc);
			opcionesVo.setFechaIndeval(fi);
			opcionesVo.setFechaPago(ftp);

			opcionesVo.setDefault(valDefault);
			opcionesVo.setFechaClienteOpcion(fechaCli);
			opcionesVo.setNumeroOpcion(String.valueOf(listOp.size() + 1));
			listOp.add(opcionesVo);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
		}else if(EDIT_ACTION.equals(tipoOperacion)){
			OpcionesDTO opcionEditar = null;
			opcionEditar = listOp.get(Integer.parseInt(numOpcion) - 1);
			opcionEditar.setCuerpoOpcion(txtHtmlArea);
			if(valDefault){
				opcionEditar.setValorDefault("Opci\u00F3n por Default");
			}else{
				opcionEditar.setValorDefault("");
			}
			opcionEditar.setFechaCliente(fc);
			opcionEditar.setFechaIndeval(fi);
			opcionEditar.setFechaPago(ftp);

			opcionEditar.setDefault(valDefault);
			opcionEditar.setFechaClienteOpcion(fechaCli);
			opcionEditar.setNumeroOpcion(numOpcion);
			listOp.set(Integer.parseInt(numOpcion) - 1, opcionEditar);
		}
		limpiaCamposOpciones();
	}
	
	public void limpiaCamposOpciones(){
		this.txtHtmlArea=null;
		this.fc=null;
		this.fi=null;
		this.ftp=null;
		this.valDefault=false;
		this.fechaCli=null;
		this.numOpcion=null;
	}
	
	public void addNotificaciones(ActionEvent event){
		log.info("Ingresa al metodo addNotificaciones");
		Map<String, Object> mapConfiguraciones = new LinkedHashMap<String, Object>();
		if(ADD_ACTION.equals(operacionNotificacion)){
			if(!StringUtils.isEmpty(periodoNot)){
				notificacionesVo = new NotificacionesEvcoDTO();
				//para la peridiocidad evaluo el valor que traemos de la vista
				switch(Integer.parseInt(periodoNot)){
				
				case 0:
					notificacionesVo.setStrPeridiocidad("Cada " + minutosNot + " minuto(s)" );
					mapConfiguraciones.put("minutos", minutosNot);
					notificacionesVo.setPatronCron(generateCronExpression(0, mapConfiguraciones));
					break;
					
				case 1:
					//verifico el tipo de configuracion ingresado
					if("hora".equals(tipoConfiguracion)){
						notificacionesVo.setStrPeridiocidad("Cada " + horaNot + " hora(s)");
						mapConfiguraciones.put("hora", horaNot);
					}else{
						notificacionesVo.setStrPeridiocidad("Cada " + horaNot + " hora(s) " + " con " + minutosNot + " minuto(s)");
						mapConfiguraciones.put("horaHora", horaNot);
						mapConfiguraciones.put("minutosHora", minutosNot);
					}
					notificacionesVo.setPatronCron(generateCronExpression(1, mapConfiguraciones));
					break;
					
				case 2:
					if("dia".equals(tipoConfiguracion)){
						notificacionesVo.setStrPeridiocidad("Cada " + numeroDias + " dia(s) \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasDias", numeroDias);
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}else{
						notificacionesVo.setStrPeridiocidad("Todos los dias \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}
					notificacionesVo.setPatronCron(generateCronExpression(2, mapConfiguraciones));
					break;
					
				case 3:
					String[] arrayD = setDayName(arrayDiasSem);
					notificacionesVo.setArrayDias(arrayDiasSem);
					notificacionesVo.setStrPeridiocidad("Los dias " + setArrayNames(arrayD) + " de la semana a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("semanaDias", arrayDiasSem);
					mapConfiguraciones.put("semanaHora", horaNot);
					mapConfiguraciones.put("semanaMinutos", minutosNot);
					notificacionesVo.setPatronCron(generateCronExpression(3, mapConfiguraciones));
					break;
					
				case 4:
					notificacionesVo.setStrPeridiocidad("Los dias " + diaMes + " del mes " + mesMes + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("mesDia", diaMes);
					mapConfiguraciones.put("mesMes", mesMes);
					mapConfiguraciones.put("mesHora", horaNot);
					mapConfiguraciones.put("mesMinuto", minutosNot);
					notificacionesVo.setPatronCron(generateCronExpression(4, mapConfiguraciones));
				}
				notificacionesVo.setMesCapturado(mesMes);
				notificacionesVo.setHrCapturada(horaNot);
				notificacionesVo.setDiaCapturado(numeroDias);
				notificacionesVo.setPeriodo(tipoConfiguracion);
				notificacionesVo.setMinCapturado(minutosNot);
				notificacionesVo.setStrTextoNotificacion(textoNot);
				notificacionesVo.setStrFechaIncio(fechaIncioNot);
				notificacionesVo.setStrFechaFin(fechaFinNot);
				notificacionesVo.setNumNotificacion(String.valueOf(listNotificaciones.size() + 1));
				notificacionesVo.setNumOpcAcc(numOpcAccordion);
				//recupero el valor de base de datos de acuerdo a la opcion ingresada por el usuario
				notificacionesVo.setIdListaDIstribucion(destinatarioNot);
				notificacionesVo.setStrDestinatario(consultaEventosCorporativosService.getListaDisById(Long.parseLong(destinatarioNot)).getNombre());
				notificacionesVo.setNumOpcAcc(periodoNot);
				listNotificaciones.add(notificacionesVo);
			}
		}else if(EDIT_ACTION.equals(operacionNotificacion)){
			if(!StringUtils.isEmpty(periodoNot)){
				NotificacionesEvcoDTO vo = null;
				vo = listNotificaciones.get(Integer.parseInt(numNotificacion) - 1);
			
				switch(Integer.parseInt(periodoNot)){
				
				case 0:
					vo.setStrPeridiocidad("Cada " + minutosNot + " minuto(s)" );
					mapConfiguraciones.put("minutos", minutosNot);
					vo.setMinCapturado(minutosNot);
					vo.setPatronCron(generateCronExpression(0, mapConfiguraciones));
					break;
					
				case 1:
					if("hora".equals(tipoConfiguracion)){
						vo.setPeriodo(tipoConfiguracion);
						vo.setStrPeridiocidad("Cada " + horaNot + " hora(s)");
						vo.setHrCapturada(horaNot);
						mapConfiguraciones.put("hora", horaNot);
					}else{
						vo.setStrPeridiocidad("Cada " + horaNot + " hora(s) " + " con " + minutosNot + " minuto(s)");
						vo.setHrCapturada(horaNot);
						vo.setMinCapturado(minutosNot);
						mapConfiguraciones.put("horaHora", horaNot);
						mapConfiguraciones.put("minutosHora", minutosNot);
					}
					vo.setPatronCron(generateCronExpression(1, mapConfiguraciones));
					break;
					
				case 2:
					if("dia".equals(tipoConfiguracion)){
						vo.setPeriodo(tipoConfiguracion);
						vo.setDiaCapturado(numeroDias);
						vo.setHrCapturada(horaNot);
						vo.setMinCapturado(minutosNot);
						vo.setStrPeridiocidad("Cada " + numeroDias + " dia(s) \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasDias", numeroDias);
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}else{
						vo.setHrCapturada(horaNot);
						vo.setMinCapturado(minutosNot);
						vo.setStrPeridiocidad("Todos los dias \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}
					vo.setPatronCron(generateCronExpression(2, mapConfiguraciones));
					break;
				
				case 3:
					String[] arrayD = setDayName(arrayDiasSem);
					vo.setArrayDias(arrayDiasSem);
					vo.setHrCapturada(horaNot);
					vo.setMinCapturado(minutosNot);
					vo.setStrPeridiocidad("Los dias " + setArrayNames(arrayD) + " de la semana a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("semanaDias", arrayDiasSem);
					mapConfiguraciones.put("semanaHora", horaNot);
					mapConfiguraciones.put("semanaMinutos", minutosNot);
					vo.setPatronCron(generateCronExpression(3, mapConfiguraciones));
					break;
				
				case 4:
					vo.setDiaCapturado(diaMes);
					vo.setMesCapturado(mesMes);
					vo.setHrCapturada(horaNot);
					vo.setMinCapturado(minutosNot);
					vo.setStrPeridiocidad("Los dias " + diaMes + " del mes " + mesMes + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("mesDia", diaMes);
					mapConfiguraciones.put("mesMes", mesMes);
					mapConfiguraciones.put("mesHora", horaNot);
					mapConfiguraciones.put("mesMinuto", minutosNot);
					vo.setPatronCron(generateCronExpression(4, mapConfiguraciones));
					break;
				}

				vo.setStrTextoNotificacion(textoNot);
				vo.setArrayDias(arrayDiasSem);
				//vo.setDiaCapturado(numeroDias);
				vo.setMesCapturado(mesMes);
				vo.setNumNotificacion(numNotificacion);
				vo.setNumOpcAcc(periodoNot);
				vo.setPeriodo(tipoConfiguracion);
				vo.setStrDestinatario(consultaEventosCorporativosService.getListaDisById(Long.parseLong(destinatarioNot)).getNombre());
				vo.setStrFechaFin(fechaFinNot);
				vo.setStrFechaIncio(fechaIncioNot);
				listNotificaciones.set(Integer.parseInt(numNotificacion) - 1, vo);
			}
		}
	}
	
	public void deleteNotificacion(ActionEvent event){
		int numNot = Integer.parseInt(numNotificacion);
		int indiceList = numNot - 1;
		int contador = 1;
		
		if(listNotificaciones.size() == 1){
			listNotificaciones = new ArrayList<NotificacionesEvcoDTO>();
		}else{
			listNotificaciones.remove(indiceList);
			for(NotificacionesEvcoDTO vo : listNotificaciones){
				vo.setNumNotificacion(String.valueOf(contador));
				contador++;
			}
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
	}
	
	public void editaNotificacion(ActionEvent event){
		NotificacionesEvcoDTO vo = (NotificacionesEvcoDTO)event.getComponent().getAttributes().get("notificacionEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("notificacionEdit", vo);
	}
	
	private String[] setDayName(String[] array){
		String[]arrayDias = new String[array.length];
		for(int i = 0; i < array.length; i++){
			if("MON".equals(array[i])){
				arrayDias[i] = "Lunes";
			}else if("TUE".equals(array[i])){
				arrayDias[i] = "Martes";
			}else if("WED".equals(array[i])){
				arrayDias[i] = "Miercoles";
			}else if("THU".equals(array[i])){
				arrayDias[i] = "Jueves";
			}else if("FRI".equals(array[i])){
				arrayDias[i] = "Viernes";
			}
		}
		return arrayDias;
	}
	//TODO:generar un refactor de estos metodos comunes con la modificacion
	private String generateCronExpression(int option, Map<String, Object> map){
		StringBuilder sb = new StringBuilder();
		
		//generamos el cronexpression en base a la captura de datos en la vista
		switch(option){
		
		//cronexpression para minutos
		case 0:
			// 	0 0/45 * 1/1 * ? *
			sb.append("0 ").append("0/" + convierteANumero(map.get("minutos"))).append(" * 1/1 * ? *");
			break;
			
		case 1:
			// 	0 0 0/12 1/1 * ? *
			if(map.containsKey("hora")){
				sb.append("0 0 0/" + convierteANumero(map.get("hora")) + " 1/1 * ? *");
			}else{
				//0 8 12 1/1 * ? *
				sb.append("0 " + convierteANumero(map.get("minutosHora")) + " " + convierteANumero(map.get("horaHora")) + " 1/1 * ? *");
			}
			break;
			
		case 2:
			if(map.containsKey("diasDias")){
				//0 14 9 1/15 * ? *
				sb.append("0 " + convierteANumero(map.get("diasMinuto")) + " " + convierteANumero(map.get("diasHora")) + " 1/" + convierteANumero(map.get("diasDias")) + " * ? *");
			}else{
				//0 14 12 ? * MON-FRI *
				sb.append("0 " + convierteANumero(map.get("diasMinuto")) + " " + convierteANumero(map.get("diasHora")) + " ? * MON-FRI *");
			}
			break;
			
		case 3:
			// 	0 19 7 ? * MON,WED,THU *
			sb.append("0 " + convierteANumero(map.get("semanaMinutos")) + " " + convierteANumero(map.get("semanaHora")) + " ? * " + setArrayNames((String[]) map.get("semanaDias")) + " *");
			break;
			
		case 4:
			// 	0 15 6 5 12 ? *
			sb.append("0 " + convierteANumero(map.get("mesMinuto")) + " " + convierteANumero(map.get("mesHora")) + " " + convierteANumero(map.get("mesDia")) + " " + convierteANumero(map.get("mesMes")) + " ? *");
			break;
		
		}
		return sb.toString();
	}
	
	private String convierteANumero(Object ob){
		if(ob != null && StringUtils.isNumeric(ob.toString())){
			return Integer.valueOf(ob.toString()).toString();
		}else if(ob!=null){
			log.debug("No se pudo convertir a numero: "+ob.toString());
			return ob.toString();
		}else{
			log.debug("No se pudo convertir a numero: ");
			return null;
		}
		
	}
	
	private String setArrayNames(String[] array){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < array.length; i++){
			sb.append(array[i]).append(",");
		}
		return sb.toString().substring(0, sb.toString().lastIndexOf(","));
	}
	
/** ******************************************************************* VALIDACIONES **************************** **/
	
	public void setDataCombos(ActionEvent event){
		List<TipoValidacionEvco> tipoValEvco = consultaEventosCorporativosService.getAllTipoValidacionEvco();
		List<OperadorEvco> tipoOpEvco = consultaEventosCorporativosService.getAllOperadorEvco();
		//Ingreso ambas consultas en session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listTipoValEvco", tipoValEvco);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listTipoOpEvco", tipoOpEvco);
	}
	
	public void addValidaciones(ActionEvent event){
		if(ADD_ACTION.equals(operacionValidacion)){
			validacionesVo = new ValidacionesEvcoDTO();
			//obtengo las descripciones de los elementos seleccionados en los combos
			//para la posicion 
			/*validacionesVo.setStrValidacion(consultaEventosCorporativosService.getTipoValidacionById(Long.parseLong(validacionVal)).getNombre());
			validacionesVo.setIdValidacion(Long.parseLong(validacionVal));
			validacionesVo.setStrOperador(consultaEventosCorporativosService.getOperadorById(Long.parseLong(operadorVal)).getOperador());
			validacionesVo.setIdOperador(operadorVal);
			validacionesVo.setStrCantidadCapturada(cantidadVal);
			*/
			validacionesVo.setStrValidacion(consultaEventosCorporativosService.getTipoValidacionById(Long.parseLong(validacionVal)).getNombre());
			validacionesVo.setStrOperador(consultaEventosCorporativosService.getOperadorById(Long.parseLong(operadorVal)).getOperador());
			validacionesVo.setIdOperador(operadorVal);
			validacionesVo.setTipoValidacion(validacionVal);
			validacionesVo.setValor(Long.parseLong(cantidadVal));
			validacionesVo.setStrCantidadCapturada(cantidadVal);
			validacionesVo.setStrNumValidacion(String.valueOf(listValidaciones.size() + 1));
			listValidaciones.add(validacionesVo);
		}
	}
	
	public void deleteValidacion(ActionEvent event){
		int numeroValidacion = Integer.parseInt(numValidacion);
		int indiceList = numeroValidacion - 1;
		int contador = 1;
		
		if(listValidaciones.size() == 1){
			listValidaciones = new ArrayList<ValidacionesEvcoDTO>();
		}else{
			listValidaciones.remove(indiceList);
			for(ValidacionesEvcoDTO vo : listValidaciones){
				vo.setStrNumValidacion(String.valueOf(contador));
				contador++;
			}
		}
	}
	
	public void editValidacion(ActionEvent event){
		ValidacionesEvcoDTO vo = (ValidacionesEvcoDTO)event.getComponent().getAttributes().get("validacionObject");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("validacionObject", vo);
	}
	
/** ****************************************************************** NOTAS ************************************* **/
	
	public void addNotas(ActionEvent event){
		log.info("Ingreso al metodo addNotas");
		if(ADD_ACTION.equals(operacionNota)){
			//recupero los datos mapeados del dialogo y lo ingreso a la lista que pintara el resumen de las notas capturadas
			log.info("Ingreso al metodo addNotas");
			notasVo = new NotasDTO();
			notasVo.setCuerpoNota(txtNota);
			//opcionesVo.setNumeroOpcion(String.valueOf(listOp.size() + 1));
			notasVo.setNumNota(String.valueOf(listNot.size() + 1));
			listNot.add(notasVo);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("noteEdit");
		}else if(EDIT_ACTION.equals(operacionNota)){
			NotasDTO notasEditar = null;
			notasEditar = listNot.get(Integer.parseInt(numOpcion) - 1);
			notasEditar.setCuerpoNota(txtNota);
			listNot.set(Integer.parseInt(numOpcion) - 1, notasEditar);
		}
	}
	
	public void editNota(ActionEvent event){
		log.info("Ingreso al metodo editNota");
		NotasDTO vo = (NotasDTO)event.getComponent().getAttributes().get("noteEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("noteEdit", vo);
	}
	
	public void deleteNota(ActionEvent event){
		log.info("Ingreso al metodo deleteNota");
		int numNot = Integer.parseInt(numNota);
		int indiceLista = numNot - 1;
		int contador = 1;
		
		if(listNot.size() == 1){
			listNot = new ArrayList<NotasDTO>();
		}else{
			listNot.remove(indiceLista);
			for(NotasDTO vo : listNot){
				vo.setNumNota(String.valueOf(contador));
				contador++;
			}
		}
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("noteEdit");
	}
	
	
	public void editOption(ActionEvent event){
		log.info("Ingreso al metodo editOption");
		OpcionesDTO vo = (OpcionesDTO)event.getComponent().getAttributes().get("objectEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objectEdit", vo);
	}
	
	public void deleteOption(ActionEvent event){
		//borro de la lista de opciones la que selecciono en la vista
		log.info("Ingreso al metodo deleteOption");
		int numOp = Integer.parseInt(numOpcion);
		int indiceLista = numOp - 1;
		int contador = 1;
		
		if(listOp.size() == 1){
			listOp = new ArrayList<OpcionesDTO>();
		}else{
			listOp.remove(indiceLista);
			for(OpcionesDTO vo : listOp){
				vo.setNumeroOpcion(String.valueOf(contador));
				contador++;
			}
			
		}
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
	}
	
	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarDerechos(ActionEvent evt)
	{
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	private void setParams() {
		altaEvCo = new EventoCorporativoAltaDTO();
		
		altaEvCo.setIdEventoCorporativo(this.idEventoCorporativo);
		altaEvCo.setFechaCreacion(Calendar.getInstance().getTime() );		
		altaEvCo.setFechaRegistro(this.fechaRegistro);		
		if(StringUtils.isNotEmpty(this.tipoValor) ){
			altaEvCo.setTipoValor(this.tipoValor.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.emisora )){
			altaEvCo.setEmisora(this.emisora.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.serie )){
			altaEvCo.setSerie(this.serie.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.isin )){
			altaEvCo.setIsin(this.isin.toUpperCase());
		}		
		if (this.tipoDerechoMO != null && !this.tipoDerechoMO.equals("") && this.tipoDerechoMO.matches("-*[0-9]+")){
			altaEvCo.setTipoDerechoMO(this.tipoDerechoMO);
		}
		if (this.tipoDerechoML != null && !this.tipoDerechoML.equals("") && this.tipoDerechoML.matches("-*[0-9]+")){
			altaEvCo.setTipoDerechoML(this.tipoDerechoML);
		}
		if (this.tipoMercado != null && !this.tipoMercado.equals("") && this.tipoMercado.matches("-*[0-9]+")){
			altaEvCo.setTipoMercado(this.tipoMercado);
		}
		if (this.estado != null && !this.estado.equals("") && this.estado.matches("-*[0-9]+")){
			altaEvCo.setEstado(this.estado);
		}
		if (this.tipoEvento != null && !this.tipoEvento.equals("") && this.tipoEvento.matches("-*[0-9]+")){
			altaEvCo.setTipoEvento(this.tipoEvento);
		}
		log.debug("Fecha LImite Indeval en padre " + fechaIndeval);
		altaEvCo.setFechaIndeval(this.fechaIndeval);		
		altaEvCo.setFechaCliente(this.fechaCliente);		
		altaEvCo.setFechaPago(this.fechaPago);		
		altaEvCo.setPiePaginaHtml(this.piePaginaHtml);
		altaEvCo.setCuerpoEventoHtml(this.cuerpoEventoHtml);
		
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")){
			altaEvCo.setCustodio(this.custodio);
		}		
		
	}
	
	
	
	public String saveEventoCorporativo(){
		log.info("Ingreso al metodo saveEventoCorporativo del Controller");
		boolean bandera = false;
		setParams();
		mapOpciones = new LinkedHashMap<String, OpcionesDTO>();
		mapNotasEvco = new LinkedHashMap<String, NotasDTO>();
		mapNotificacionesEvco = new LinkedHashMap<String, NotificacionesEvcoDTO>();
		mapValidacionesEvco = new LinkedHashMap<String, ValidacionesEvcoDTO>();
		Long idEventoGuardado = null;
		int contadorOpc = 0;
		int contadorNot = 0;
		try{

			for(OpcionesDTO opcionesEvento : listOp){
				vo = new OpcionesDTO();
				vo.setCuerpoOpcion(opcionesEvento.getCuerpoOpcion());
				vo.setDefault(opcionesEvento.isDefault());
				if(!StringUtils.isEmpty(opcionesEvento.getFechaCliente()) && opcionesEvento.getFechaCliente().matches(patron)){
					vo.setFechaClienteOpcion(stringToDate(opcionesEvento.getFechaCliente()));
				}
				if(!StringUtils.isEmpty(opcionesEvento.getFechaIndeval()) && opcionesEvento.getFechaIndeval().matches(patron)){
					vo.setFechaIndevalOpcion(stringToDate(opcionesEvento.getFechaIndeval()));
				}
				if(!StringUtils.isEmpty(opcionesEvento.getFechaPago()) && opcionesEvento.getFechaPago().matches(patronSinHora)){
					vo.setFechaPagoOpcion(stringToDateSinHora(opcionesEvento.getFechaPago()));
				}
				mapOpciones.put(Constantes.OPCION_PREFIX + contadorOpc, vo);
				contadorOpc++;
			}

			for(NotasDTO notasEvento : listNot){
				notasVo = new NotasDTO();
				notasVo.setCuerpoNota(notasEvento.getCuerpoNota());
				mapNotasEvco.put(Constantes.NOTA_PREFIX + contadorNot, notasVo);
				contadorNot++;
			}

			for(ArchivosAdjuntosEvcoDTO vo : getListAdjuntos()){
				mapAdjuntosEvco.put(Constantes.ADJUNTOS_PREFIX + pivote, vo);
				pivote++;
			}
			
			for(NotificacionesEvcoDTO vo : listNotificaciones){
				mapNotificacionesEvco.put(Constantes.NOTIFICACIONES_PREFIX + notifContador , vo);
				notifContador++;
			}
			
			for(ValidacionesEvcoDTO vo : listValidaciones){
				mapValidacionesEvco.put(Constantes.VALIDACIONES_PREFIX + valiContador, vo);
				valiContador++;
			}

			altaEvCo.setMapOpciones(mapOpciones);
			altaEvCo.setMapNotas(mapNotasEvco);
			altaEvCo.setMapAdjuntos(mapAdjuntosEvco);
			altaEvCo.setMapNotificaciones(mapNotificacionesEvco);
			altaEvCo.setMapValidaciones(mapValidacionesEvco);
			altaEvCo.setUsuarioAlta(getNombreUsuarioSesionLocal());
			idEventoGuardado=saveEventosCorporativosService.saveEventoCorporativo(altaEvCo);
			bandera = true;
			this.banderaCreacion=true;
		}catch(ParseException pe){
			bandera = false;
			this.banderaCreacion=false;			
			agregarMensaje(new BusinessException("Error en formato de fechas"));
		}
		catch (BusinessException e) {
			bandera = false;
			this.banderaCreacion=false;			
			agregarMensaje(e);
		}
		finally{
			if(bandera){
				this.mapOpciones = null;
				this.mapNotasEvco = null;
				this.listOp = null;
				this.listNot = null;
				setListAdjuntos(null);
				mapAdjuntosEvco=null;
				if(idEventoGuardado != null){
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEventoGuardado", idEventoGuardado);
				}
			}
			if(mapAdjuntosEvco != null){
				mapAdjuntosEvco.clear();
			}
		}
		return null;
	}
	
	
	private Date stringToDate(String fecha) throws ParseException{
		if(StringUtils.isNotEmpty(fecha)){
			return format.parse(fecha);
		}else{
			return null;
		}		
	}
	
	private Date stringToDateSinHora(String fecha) throws ParseException{
		if(StringUtils.isNotEmpty(fecha)){
			return formatSinHora.parse(fecha);
		}else{
			return null;
		}		
	}
	private String dateToString(Date fecha){
		if(fecha != null){
			return format.format(fecha);
		}else{
			return null;
		}
	}
	
	
	
	public void valueChangeMethod(ValueChangeEvent e){

	
	this.tipoEvento=e.getNewValue().toString();
	
	if(this.tipoEvento.equals("2"))
	{
		this.listOp=new ArrayList<OpcionesDTO>();
	}
	log.info("");
	
	}
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{    		
        this.idEventoCorporativo=null;
		this.fechaCreacion=null;		
		this.fechaRegistro=null;		
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.tipoDerechoMO="-1";
		this.tipoDerechoML="-1";
		this.tipoMercado="-1";
		this.tipoEvento="-1";
		this.estado="-1";
		this.fechaIndeval=null;		
		this.fechaCliente=null;		
		this.fechaPago=null;		
		this.custodio="-1";		
	}
	/**
	 * Obtiene la consulta de Estados
	 */
	public List<SelectItem> getEstados(){
		if(this.listaEstatus != null && this.listaEstatus.size() > 0) {
			return this.listaEstatus;
		}
		List<Estado> estados = consultaEventosCorporativosService.getEstadosEventoCorporativo();
		this.listaEstatus = new ArrayList<SelectItem>();
    	if(estados != null){
	    	for (Estado estado:estados){	
	    		log.debug("ESTADO EVCO: "+estado.getIdEstado());
	    		this.listaEstatus.add(new SelectItem(estado.getIdEstado().toString(),estado.getEstado()));	    		
	    	}
    	}else{
    		this.listaEstatus.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaEstatus;
    }
	
	
		
	/**
	 * 
	 */
	public List<SelectItem> getCustodios(){	
		if(this.listaCustodios != null && this.listaCustodios.size() > 0){
			return this.listaCustodios;
		}
    	List<Custodio> custodios = consultaEventosCorporativosService.getCatalogoCustodios();
    	this.listaCustodios = new ArrayList<SelectItem>();
    	if( custodios != null){
	    	for (Custodio custodio : custodios){ 	    			    			
	    			this.listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));	    		
	    	}
	    	this.custodiosConsulta = custodios;
    	}else{
    		this.listaCustodios.add(new SelectItem("-2","VACIO"));
    	}
    	
    	this.listaCustodiosBuffer.clear();
    	this.listaCustodiosBuffer.addAll(this.listaCustodios);
    	return this.listaCustodios;
    }
	
	public List<SelectItem> getTiposMercado() {
		if(this.listaTipoMercado != null && this.listaTipoMercado.size() > 0) {
			return this.listaTipoMercado;
		}
		List<TipoMercado> mercados = consultaEventosCorporativosService.getTiposMercado();
    	this.listaTipoMercado = new ArrayList<SelectItem>();
    	if(mercados != null){
	    	for (TipoMercado mercado:mercados){	    		
	    		this.listaTipoMercado.add(new SelectItem(mercado.getIdTipoMercado().toString(),mercado.getDescripcion()));	    		
	    	}
    	}else{
    		this.listaTipoMercado.add(new SelectItem("-2","VACIO"));
    	}
    	
    	return this.listaTipoMercado;
	}
	
	

	public List<SelectItem> getTiposDerechoMO() {
		if(this.listaTipoDerechoMO != null && this.listaTipoDerechoMO.size() > 0) {
			return this.listaTipoDerechoMO;
		}
		List<TipoDerechoEvCo> derechos = consultaEventosCorporativosService.getTiposDerechoMO();
		this.listaTipoDerechoMO = new ArrayList<SelectItem>();
    	if(derechos != null){
	    	for (TipoDerechoEvCo derecho:derechos){
	    		if(derecho.getInactivo().equals(0l)){
	    			this.listaTipoDerechoMO.add(new SelectItem(derecho.getIdTipoDerecho().toString(),derecho.getTipoDerecho()));
	    		}
	    	}
    	}else{
    		this.listaTipoDerechoMO.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoDerechoMO;
	}
	
	public List<SelectItem> getTiposDerechoML() {
		if(this.listaTipoDerechoML != null && this.listaTipoDerechoML.size() > 0) {
			return this.listaTipoDerechoML;
		}
		List<TipoDerechoEvCo> derechos = consultaEventosCorporativosService.getTiposDerechoML();
		this.listaTipoDerechoML = new ArrayList<SelectItem>();
    	if(derechos != null){
	    	for (TipoDerechoEvCo derecho:derechos){	 
	    		if(derecho.getInactivo().equals(0l)){
	    			this.listaTipoDerechoML.add(new SelectItem(derecho.getIdTipoDerecho().toString(),derecho.getTipoDerecho()));
	    		}
	    	}
    	}else{
    		this.listaTipoDerechoML.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoDerechoML;
	}
	
	public List<SelectItem> getTiposEvento() {
		if(this.listaTipoEvento != null && this.listaTipoEvento.size() > 0) {
			return this.listaTipoEvento;
		}
		    		
		List<TipoEvento> eventos = consultaEventosCorporativosService.getTiposEvento();
		this.listaTipoEvento = new ArrayList<SelectItem>();
    	if(eventos != null){
	    	for (TipoEvento evento:eventos){	    		
	    		this.listaTipoEvento.add(new SelectItem(evento.getIdTipoEvento().toString(),evento.getDescripcion()));	    		
	    	}
    	}else{
    		this.listaTipoEvento.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoEvento;
	}
	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {		
		listaCustodios =getCustodios();		
	}
	
	/**
	 * Inicializa lista de estatus de emisiones
	 */
	private void inicializaEstatus() {		
		getEstados();	
	}
	
	private void inicializaTiposDerecho() {
		getTiposDerechoMO();
		getTiposDerechoML();
		
	}
	
	private void inicializaTipoMercado() {
		listaTipoMercado=getTiposMercado();
		
	}
	
	private void inicializaTiposEvento() {
		listaTipoEvento=getTiposEvento();
		
	}

	/**
	 * @return the listaCustodios
	 */
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	

	/**
	 * @return the listaEstatus
	 */
	public List<SelectItem> getListaEstatus() {
		return listaEstatus;
	}
	
	
	public String getSelectedCustodio(){
		String resultado = getSelected(getCustodio() ,this.listaCustodios);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedEstado(){
		String resultado = getSelected(getEstado() ,this.listaEstatus);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoDerechoMO(){
		String resultado = getSelected(getTipoDerechoMO() ,this.listaTipoDerechoMO);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoDerechoML(){
		String resultado = getSelected(getTipoDerechoML() ,this.listaTipoDerechoML);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedMercado(){
		String resultado = getSelected(getTipoMercado() ,this.listaTipoMercado);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoEvento(){
		String resultado = getSelected(getTipoEvento() ,this.listaTipoEvento);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	private String getSelected(String key, List<SelectItem> lista){
		String resultado = null;
		for(SelectItem item : lista){
			if(key != null && item.getValue().equals(key))
				resultado=item.getLabel();
		}
		return resultado;		
	}

	
	/**
	 * Obtiene el evento si es una edicion
	 */
	private void getRequestParamEvCo(){
		this.isEdicion=false;
	    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    if(request instanceof HttpServletRequest){
	            String folio =((HttpServletRequest) request).getParameter("folio");
	            if(StringUtils.isNotEmpty(folio) && StringUtils.isNumeric(folio)){	            	
	            	EventoCorporativo evco =this.consultaEventosCorporativosService.getEventoCorporativoById(Long.valueOf(folio));
	            	if(evco !=null ){            		
	            		//general
	            		this.isEdicion=true;
	            		this.emisora=evco.getEmisora();
	            		this.serie=evco.getSerie();
	            		this.tipoValor=evco.getTipoValor();
	            		this.isin=evco.getIsin();
	            		setCustodio(evco.getCustodio().getId().toString());
	            		setTipoDerechoML(evco.getTipoDerechoML().getIdTipoDerecho().toString());
	            		setTipoDerechoMO(evco.getTipoDerechoMO().getIdTipoDerecho().toString());
	            		setTipoEvento(evco.getTipoEvento().getIdTipoEvento().toString());
	            		setTipoMercado(evco.getTipoMercado().getIdTipoMercado().toString());
	            		//cuerpo
	            		CuerpoEventoCorporativo cuerpoEventoCorporativo = this.consultaEventosCorporativosService.getCuerpoEventoCorporativo(Long.valueOf(folio));
	            		this.piePaginaHtml = cuerpoEventoCorporativo.getCuerpo();	            		
	            		this.cuerpoEventoHtml = cuerpoEventoCorporativo.getPiePagina();
	            		
	            		
	            	}
	            }	            
	    }
	}

	public void getBuscaEmision(ActionEvent ae){
		getBuscaEmision();
		
	}
	public void buscaEmision(ActionEvent ae){
		getBuscaEmision();
		
	}
	public String buscaEmision(){
		return getBuscaEmision();
		
	}
	
	
	public String getBuscaEmision(){
		if((StringUtils.isNotEmpty(this.tipoValor) && 
				StringUtils.isNotEmpty(this.emisora) &&
				StringUtils.isNotEmpty(this.serie) ) ||
				(StringUtils.isNotEmpty(this.isin))){
			
			EmisionVO criterio = new EmisionVO();
			criterio.setTv(this.tipoValor);
			criterio.setEmisora(this.emisora);
			criterio.setSerie(this.serie);
			criterio.setIsin(this.isin);
			criterio.setCupon("0000");
			List<Long> listaIds = new ArrayList<Long>();
			listaIds.add(new Long(1));
			listaIds.add(new Long(2));
			listaIds.add(new Long(3));
			criterio.setListaIdEstatusEmision(listaIds);		
	
			Emision emisionModel = emisionDao.findEmision(criterio);
			if(emisionModel != null){
				this.tipoValor = emisionModel.getInstrumento().getClaveTipoValor() != null ?
								emisionModel.getInstrumento().getClaveTipoValor() : "";
				this.emisora = emisionModel.getEmisora().getClavePizarra() != null ? 
								emisionModel.getEmisora().getClavePizarra() : "";
				this.serie = emisionModel.getSerie() != null ? emisionModel.getSerie() : "";
				this.isin = emisionModel.getIsin() != null ? emisionModel.getIsin() : "";
				
				
				CatBic[] custodios = divisionInternacionalService.obtieneCustodios(criterio);
				List<String> clavesBIC= new ArrayList<String>();
				if(custodios != null){
					for(CatBic catbic : custodios ){
						if(StringUtils.isNotEmpty(catbic.getBicProd())){
							String claveBic = catbic.getBicProd().substring(0, 4);
							clavesBIC.add(claveBic);
						}
					}
					if(clavesBIC.size() > 0){
						this.listaCustodios.clear();
						//cambiar combo de custodio						
						for(String cvebic : clavesBIC){
							for(Custodio cust : this.custodiosConsulta){
								if(cust.getCodigoBanco().equals(cvebic)){
									this.listaCustodios.add(new SelectItem(cust.getId().toString(), cust.getDescripcion()));
								}
							}
						}
						//setear el custodio si solo hay uno 
						if(this.listaCustodios.size() == 1){
							setCustodio(this.listaCustodios.get(0).getValue().toString());
							this.bloqueaCustodio=true;
						}
					}else{
						this.listaCustodios.clear();
						this.listaCustodios.addAll(this.listaCustodiosBuffer  );
						this.bloqueaCustodio=false;
					}
				}else{
					this.listaCustodios.clear();
					this.listaCustodios.addAll(this.listaCustodiosBuffer );
					this.bloqueaCustodio=false;
				}
				
				this.emisionEncontrada=true;
			}else{
				if(this.listaCustodios != null){
					this.listaCustodios.clear();
					this.listaCustodios.addAll(this.listaCustodiosBuffer );
				}
				this.emisionEncontrada=false;
				this.bloqueaCustodio=false;
			}
		}
		return null;
	}
	
	
	/**
	 * añade archivo adjunto
	 * @param event
	 */
	public void listenerAdjuntos(UploadEvent event){
		//implementando logica para llenar el objeto que llevaremos a la capa de persistencia
		UploadItem item = event.getUploadItem();
		boolean procesar=true;
		for(ArchivosAdjuntosEvcoDTO dto : listAdjuntos){
			if(item.getFileName().equals(dto.getDescripcion())){
				procesar=false;
				addErrorMessage("El nombre del archivo ya existe");
				break;
			}
		}
		if(procesar){
			archivoVo = new ArchivosAdjuntosEvcoDTO();
			archivoVo.setArchivo(item.getFile());
			archivoVo.setDescripcion(item.getFileName());
			archivoVo.setBorrado("1");
			listAdjuntos.add(archivoVo);
		}
	}
	/**
	 * Borrra los archivos del FILE UPLOAD
	 * @param event
	 */
	public void clearUploadData(ActionEvent event) {
        try {
            // test if a single file was cleared....
            if (this.fileNameBorrar != null && !"".equals(fileNameBorrar)) {
                log.debug("Removing single fileName=" + fileNameBorrar);
                Iterator<ArchivosAdjuntosEvcoDTO> iter = listAdjuntos.iterator();
                while(iter.hasNext()){
                	ArchivosAdjuntosEvcoDTO dto = iter.next();
                	if(dto.getDescripcion().equals(this.fileNameBorrar)){
                		iter.remove();
                		break;
                	}
                }                
            }else {
                // remove all files form fileUploadBean...
            	listAdjuntos.clear();
            }
        } catch (Exception e) {
            addErrorMessage("No se pueden Borrar los archivos adjuntos");
        }
    }   
	
	/**
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}
	/**
	 * @param saveEventosCorporativosService the saveEventosCorporativosService to set
	 */
	public void setSaveEventosCorporativosService(
			SaveEventosCorporativosService saveEventosCorporativosService) {
		this.saveEventosCorporativosService = saveEventosCorporativosService;
	}
	/**
	 * @return the listaTipoMercado
	 */
	public List<SelectItem> getListaTipoMercado() {
		return listaTipoMercado;
	}
	/**
	 * @param listaTipoMercado the listaTipoMercado to set
	 */
	public void setListaTipoMercado(List<SelectItem> listaTipoMercado) {
		this.listaTipoMercado = listaTipoMercado;
	}
	/**
	 * @return the listaTipoDerechoMO
	 */
	public List<SelectItem> getListaTipoDerechoMO() {
		return listaTipoDerechoMO;
	}
	/**
	 * @param listaTipoDerechoMO the listaTipoDerechoMO to set
	 */
	public void setListaTipoDerechoMO(List<SelectItem> listaTipoDerechoMO) {
		this.listaTipoDerechoMO = listaTipoDerechoMO;
	}
	/**
	 * @return the listaTipoDerechoML
	 */
	public List<SelectItem> getListaTipoDerechoML() {
		return listaTipoDerechoML;
	}
	/**
	 * @param listaTipoDerechoML the listaTipoDerechoML to set
	 */
	public void setListaTipoDerechoML(List<SelectItem> listaTipoDerechoML) {
		this.listaTipoDerechoML = listaTipoDerechoML;
	}
	
	/**
	 * @return the idEventoCorporativo
	 */
	public String getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(String idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the fechaCreacionInicio
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacionInicio the fechaCreacionInicio to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	/**
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}
	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}
	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}
	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}
	/**
	 * @return the tipoDerechoMO
	 */
	public String getTipoDerechoMO() {
		return tipoDerechoMO;
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(String tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}
	/**
	 * @return the tipoDerechoML
	 */
	public String getTipoDerechoML() {
		return tipoDerechoML;
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(String tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}
	/**
	 * @return the tipoMercado
	 */
	public String getTipoMercado() {
		return tipoMercado;
	}
	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaIndevalInicio
	 */
	public Date getFechaIndeval() {
		return fechaIndeval;
	}
	/**
	 * @param fechaIndevalInicio the fechaIndevalInicio to set
	 */
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	
	/**
	 * @return the fechaClienteInicio
	 */
	public Date getFechaCliente() {
		return fechaCliente;
	}
	/**
	 * @param fechaClienteInicio the fechaClienteInicio to set
	 */
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	
	/**
	 * @return the fechaPagoInicio
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPagoInicio the fechaPagoInicio to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}
	
	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}
	/**
	 * @param listaEstatus the listaEstatus to set
	 */
	public void setListaEstatus(List<SelectItem> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}
	/**
	 * @return the listaTipoEvento
	 */
	public List<SelectItem> getListaTipoEvento() {
		return listaTipoEvento;
	}
	/**
	 * @param listaTipoEvento the listaTipoEvento to set
	 */
	public void setListaTipoEvento(List<SelectItem> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
	}
	/**
	 * @return the tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	/**
	 * @return the cuerpoEventoHtml
	 */
	public String getCuerpoEventoHtml() {
		return cuerpoEventoHtml;
	}
	/**
	 * @param cuerpoEventoHtml the cuerpoEventoHtml to set
	 */
	public void setCuerpoEventoHtml(String cuerpoEventoHtml) {
		this.cuerpoEventoHtml = cuerpoEventoHtml;
	}
	/**
	 * @return the cuerpoEventoText
	 */
	public String getCuerpoEventoText() {
		return cuerpoEventoText;
	}
	/**
	 * @param cuerpoEventoText the cuerpoEventoText to set
	 */
	public void setCuerpoEventoText(String cuerpoEventoText) {
		this.cuerpoEventoText = cuerpoEventoText;
	}
	/**
	 * @return the piePaginaHtml
	 */
	public String getPiePaginaHtml() {
		return piePaginaHtml;
	}
	/**
	 * @param piePaginaHtml the piePaginaHtml to set
	 */
	public void setPiePaginaHtml(String piePaginaHtml) {
		this.piePaginaHtml = piePaginaHtml;
	}
	/**
	 * @return the piePaginaText
	 */
	public String getPiePaginaText() {
		return piePaginaText;
	}
	/**
	 * @param piePaginaText the piePaginaText to set
	 */
	public void setPiePaginaText(String piePaginaText) {
		this.piePaginaText = piePaginaText;
	}
	/**
	 * @return the isEdicion
	 */
	public boolean isEdicion() {
		return isEdicion;
	}
	/**
	 * @param isEdicion the isEdicion to set
	 */
	public void setEdicion(boolean isEdicion) {
		this.isEdicion = isEdicion;
	}
	
	public Map<String, Object> getMapResponse() {
		if(null == mapResponse){
			mapResponse = new HashMap<String, Object>();
		}
		return mapResponse;
	}
	
	public void setMapResponse(Map<String, Object> mapResponse) {
		this.mapResponse = mapResponse;
	}
	
	public Map<String, String> getMapNotas() {
		return mapNotas;
	}
	
	public void setMapNotas(Map<String, String> mapNotas) {
		this.mapNotas = mapNotas;
	}
	
	public OpcionesDTO getVo() {
		return vo;
	}
	
	public void setVo(OpcionesDTO vo) {
		this.vo = vo;
	}
	
	public UploadedFile getArchivo() {
		return archivo;
	}
	
	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}
	
	public HtmlPanelGrid getGridAdjuntos() {
		return gridAdjuntos;
	}
	
	public void setGridAdjuntos(HtmlPanelGrid gridAdjuntos) {
		this.gridAdjuntos = gridAdjuntos;
	}
	
	public int getElementos() {
		return elementos;
	}
	
	public void setElementos(int elementos) {
		this.elementos = elementos;
	}
	
	public Map<String, UploadedFile> getMapAdjuntos() {
		return mapAdjuntos;
	}
	
	public void setMapAdjuntos(Map<String, UploadedFile> mapAdjuntos) {
		this.mapAdjuntos = mapAdjuntos;
	}
	
	public List<ArchivosAdjuntosEvcoDTO> getListAdjuntos() {
		return listAdjuntos;
	}
	
	public void setListAdjuntos(List<ArchivosAdjuntosEvcoDTO> listAdjuntos) {
		this.listAdjuntos = listAdjuntos;
	}
	
	public String getNombreAnclaNota() {
		return nombreAnclaNota;
	}
	
	public void setNombreAnclaNota(String nombreAnclaNota) {
		this.nombreAnclaNota = nombreAnclaNota;
	}
	public String getNombreAnclaOpcion() {
		return nombreAnclaOpcion;
	}
	
	public void setNombreAnclaOpcion(String nombreAnclaOpcion) {
		this.nombreAnclaOpcion = nombreAnclaOpcion;
	}
	
	public String getTxtCuerpoOpcion() {
		return txtCuerpoOpcion;
	}
	
	public void setTxtCuerpoOpcion(String txtCuerpoOpcion) {
		this.txtCuerpoOpcion = txtCuerpoOpcion;
	}
	
	public String getTxtChkDefault() {
		return txtChkDefault;
	}
	
	public void setTxtChkDefault(String txtChkDefault) {
		this.txtChkDefault = txtChkDefault;
	}
	
	public String getMinutosNot() {
		return minutosNot;
	}
	
	public void setMinutosNot(String minutosNot) {
		this.minutosNot = minutosNot;
	}
	public List<NotificacionesEvcoDTO> getListNotificaciones() {
		return listNotificaciones;
	}
	
	public void setListNotificaciones(List<NotificacionesEvcoDTO> listNotificaciones) {
		this.listNotificaciones = listNotificaciones;
	}
	
	public String getDestinatarioNot() {
		return destinatarioNot;
	}
	
	public void setDestinatarioNot(String destinatarioNot) {
		this.destinatarioNot = destinatarioNot;
	}
	
	public String getTextoNot() {
		return textoNot;
	}
	
	public void setTextoNot(String textoNot) {
		this.textoNot = textoNot;
	}
	
	public String getFechaIncioNot() {
		return fechaIncioNot;
	}
	
	public void setFechaIncioNot(String fechaIncioNot) {
		this.fechaIncioNot = fechaIncioNot;
	}
	
	public String getFechaFinNot() {
		return fechaFinNot;
	}
	
	public void setFechaFinNot(String fechaFinNot) {
		this.fechaFinNot = fechaFinNot;
	}
	
	public String getHoraNot() {
		return horaNot;
	}
	
	public void setHoraNot(String horaNot) {
		this.horaNot = horaNot;
	}
	
	public String getTxtHtmlAreaOpc() {
		return txtHtmlAreaOpc;
	}
	
	public void setTxtHtmlAreaOpc(String txtHtmlAreaOpc) {
		this.txtHtmlAreaOpc = txtHtmlAreaOpc;
	}
	
	public boolean isValDefault() {
		return valDefault;
	}
	
	public void setValDefault(boolean valDefault) {
		this.valDefault = valDefault;
	}
	
	public Date getFechaCli() {
		return fechaCli;
	}
	
	public void setFechaCli(Date fechaCli) {
		this.fechaCli = fechaCli;
	}
	
	public Date getFechaIn() {
		return fechaIn;
	}
	
	public void setFechaIn(Date fechaIn) {
		this.fechaIn = fechaIn;
	}
	
	public Date getFechaTiPa() {
		return fechaTiPa;
	}
	
	public void setFechaTiPa(Date fechaTiPa) {
		this.fechaTiPa = fechaTiPa;
	}
	
	public List<OpcionesDTO> getListOp() {
		return listOp;
	}
	
	public void setListOp(List<OpcionesDTO> listOp) {
		this.listOp = listOp;
	}
	
	public String getFc() {
		return fc;
	}
	
	public void setFc(String fc) {
		this.fc = fc;
	}
	
	public String getFi() {
		return fi;
	}
	
	public void setFi(String fi) {
		this.fi = fi;
	}
	public String getFtp() {
		return ftp;
	}
	
	public void setFtp(String ftp) {
		this.ftp = ftp;
	}
	
	public String getNumOpcion() {
		return numOpcion;
	}
	
	public void setNumOpcion(String numOpcion) {
		this.numOpcion = numOpcion;
	}
	
	
	public String getNombreUsuarioSesionLocal() {
   	 String nombreUsuario = null;
   	 FacesContext ctx = FacesContext.getCurrentInstance();
        Object obj = ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.USUARIO_SESION);
        if(obj instanceof UsuarioDTO){
       	 UsuarioDTO usuarioDTO = (UsuarioDTO)obj;
       	 if(usuarioDTO != null && usuarioDTO.getClaveUsuario() != null){
       		 nombreUsuario = usuarioDTO.getClaveUsuario();
       	 }
        }
   	return nombreUsuario;
	}
	
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	
	public String getTxtHtmlArea() {
		return txtHtmlArea;
	}
	
	public void setTxtHtmlArea(String txtHtmlArea) {
		this.txtHtmlArea = txtHtmlArea;
	}
	
	public String getTxtOpcionHtml() {
		return txtOpcionHtml;
	}
	
	public void setTxtOpcionHtml(String txtOpcionHtml) {
		this.txtOpcionHtml = txtOpcionHtml;
	}
	
	public String getTxtNota() {
		return txtNota;
	}
	
	public void setTxtNota(String txtNota) {
		this.txtNota = txtNota;
	}
	
	public List<NotasDTO> getListNot() {
		return listNot;
	}
	
	public void setListNot(List<NotasDTO> listNot) {
		this.listNot = listNot;
	}
	
	public String getNumNota() {
		return numNota;
	}
	
	public void setNumNota(String numNota) {
		this.numNota = numNota;
	}
	
	public String getOperacionNota() {
		return operacionNota;
	}
	
	public void setOperacionNota(String operacionNota) {
		this.operacionNota = operacionNota;
	}
	
	public String getPeriodoNot() {
		return periodoNot;
	}
	
	public void setPeriodoNot(String periodoNot) {
		this.periodoNot = periodoNot;
	}
	
	public String getTipoConfiguracion() {
		return tipoConfiguracion;
	}
	
	public void setTipoConfiguracion(String tipoConfiguracion) {
		this.tipoConfiguracion = tipoConfiguracion;
	}
	
	public String getNumeroDias() {
		return numeroDias;
	}
	
	public void setNumeroDias(String numeroDias) {
		this.numeroDias = numeroDias;
	}
	
	public String[] getArrayDiasSem() {
		return arrayDiasSem;
	}
	
	public void setArrayDiasSem(String[] arrayDiasSem) {
		this.arrayDiasSem = arrayDiasSem;
	}
	
	public String getDiaMes() {
		return diaMes;
	}
	
	public void setDiaMes(String diaMes) {
		this.diaMes = diaMes;
	}
	
	public String getMesMes() {
		return mesMes;
	}
	
	public void setMesMes(String mesMes) {
		this.mesMes = mesMes;
	}
	
	public String getNumNotificacion() {
		return numNotificacion;
	}
	
	public void setNumNotificacion(String numNotificacion) {
		this.numNotificacion = numNotificacion;
	}
	
	public String getNumOpcAccordion() {
		return numOpcAccordion;
	}
	
	public void setNumOpcAccordion(String numOpcAccordion) {
		this.numOpcAccordion = numOpcAccordion;
	}
	
	public String getOperacionNotificacion() {
		return operacionNotificacion;
	}
	
	public void setOperacionNotificacion(String operacionNotificacion) {
		this.operacionNotificacion = operacionNotificacion;
	}
	public String getOperacionValidacion() {
		return operacionValidacion;
	}
	public void setOperacionValidacion(String operacionValidacion) {
		this.operacionValidacion = operacionValidacion;
	}
	public String getValidacionVal() {
		return validacionVal;
	}
	public void setValidacionVal(String validacionVal) {
		this.validacionVal = validacionVal;
	}
	public String getOperadorVal() {
		return operadorVal;
	}
	public void setOperadorVal(String operadorVal) {
		this.operadorVal = operadorVal;
	}
	public String getCantidadVal() {
		return cantidadVal;
	}
	public void setCantidadVal(String cantidadVal) {
		this.cantidadVal = cantidadVal;
	}
	public List<ValidacionesEvcoDTO> getListValidaciones() {
		return listValidaciones;
	}
	public void setListValidaciones(
			List<ValidacionesEvcoDTO> listValidaciones) {
		this.listValidaciones = listValidaciones;
	}
	public String getNumValidacion() {
		return numValidacion;
	}
	public void setNumValidacion(String numValidacion) {
		this.numValidacion = numValidacion;
	}
	public Map<String, NotificacionesEvcoDTO> getMapNotificacionesEvco() {
		return mapNotificacionesEvco;
	}
	public void setMapNotificacionesEvco(
			Map<String, NotificacionesEvcoDTO> mapNotificacionesEvco) {
		this.mapNotificacionesEvco = mapNotificacionesEvco;
	}
	public int getNotifContador() {
		return notifContador;
	}
	public void setNotifContador(int notifContador) {
		this.notifContador = notifContador;
	}
	public int getValiContador() {
		return valiContador;
	}
	public void setValiContador(int valiContador) {
		this.valiContador = valiContador;
	}
	public Map<String, ValidacionesEvcoDTO> getMapValidacionesEvco() {
		return mapValidacionesEvco;
	}
	public void setMapValidacionesEvco(
			Map<String, ValidacionesEvcoDTO> mapValidacionesEvco) {
		this.mapValidacionesEvco = mapValidacionesEvco;
	}
	public boolean isBanderaCreacion() {
		return banderaCreacion;
	}
	public void setBanderaCreacion(boolean banderaCreacion) {
		this.banderaCreacion = banderaCreacion;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	/**
	 * @return the emisionEncontrada
	 */
	public boolean isEmisionEncontrada() {
		return emisionEncontrada;
	}
	/**
	 * @param emisionEncontrada the emisionEncontrada to set
	 */
	public void setEmisionEncontrada(boolean emisionEncontrada) {
		this.emisionEncontrada = emisionEncontrada;
	}
	/**
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}
	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}
	/**
	 * @return the bloqueaCustodio
	 */
	public boolean isBloqueaCustodio() {
		return bloqueaCustodio;
	}
	/**
	 * @param bloqueaCustodio the bloqueaCustodio to set
	 */
	public void setBloqueaCustodio(boolean bloqueaCustodio) {
		this.bloqueaCustodio = bloqueaCustodio;
	}
	/**
	 * @return the fileNameBorrar
	 */
	public String getFileNameBorrar() {
		return fileNameBorrar;
	}
	/**
	 * @param fileNameBorrar the fileNameBorrar to set
	 */
	public void setFileNameBorrar(String fileNameBorrar) {
		this.fileNameBorrar = fileNameBorrar;
	}
	
}