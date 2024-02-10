package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SaveEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class EdicionEventosCorporativosController extends ControllerBase {
	
	private static final Logger log = LoggerFactory.getLogger(EdicionEventosCorporativosController.class);
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	private SaveEventosCorporativosService saveEventosCorporativosService;
	/**
	 * Dao para el acceso a la consulta de emisiones
	 */
	private EmisionDao emisionDao = null;
	
	private String idEvco;
	private Date fechaRegistro;
	private String tipoEvento;	
	private List<SelectItem> listaTipoEvento;
	private List<SelectItem> listaTipoMercado;
	private List<SelectItem> listaCustodios = new ArrayList<SelectItem>();
	private List<SelectItem> listaTipoDerechoMO;
	private List<SelectItem> listaTipoDerechoML;
	private Date fechaCreacion;
	private Date fechaIndeval;
	private Date fechaCliente;
	private Date fechaPago;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String tipoMercado;
	private String custodio;
	private String tipoDerechoMO;
	private String tipoDerechoML;
	private String cuerpoEventoHtml;
	private String piePaginaHtml;
	private List<OpcionesDTO> listOp;
	private List<NotasDTO> listNot;
	private List<ArchivosAdjuntosEvcoDTO> listAdjuntos;
	private List<NotificacionesEvcoDTO> listNotificaciones;
	private List<ValidacionesEvcoDTO> listValidaciones;
	private ArchivosAdjuntosEvcoDTO archivoVo;
	private String numOpcion;
	private static final String ADD_ACTION = "add";
	private static final String EDIT_ACTION = "edit";
	private String tipoOperacion;
	private OpcionesDTO opcionesVo;
	private NotasDTO notasVo;
	private String txtHtmlArea;
	private boolean valDefault;
	private String fc;
	private String fi;
	private String ftp;
	private Date fechaCli;
	private String operacion;
	private String txtNota;
	private String numNota;
	private String operacionNota;
	private String operacionValidacion;
	private String validacionVal;
	private String operadorVal;
	private String cantidadVal;
	private String numValidacion;
	private ValidacionesEvcoDTO validacionesVo;
	private String horaNot;
	private String minutosNot;
	private String destinatarioNot;
	private String textoNot;
	private String fechaIncioNot;
	private String fechaFinNot;
	private String periodoNot;
	private String tipoConfiguracion;
	private String numeroDias;
	private String[] arrayDiasSem;
	private String diaMes;
	private String mesMes;
	private String numNotificacion;
	private String numOpcAccordion;
	private String operacionNotificacion;
	private NotificacionesEvcoDTO notificacionesVo;
	private EventoCorporativoEdicionDTO dto;
	private boolean banderaCreacion;
	private boolean emisionEncontrada;
	private DivisionInternacionalService divisionInternacionalService;
	private List<SelectItem> listaCustodiosBuffer =new ArrayList<SelectItem>();
	private List<Custodio> custodiosConsulta = new ArrayList<Custodio>();
	private boolean bloqueaCustodio;
	private String fileNameBorrar;
	DateFormat fechaFormateada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	DateFormat fechaFormateadaSinHora = new SimpleDateFormat("dd/MM/yyyy");
	private String estado;
	private static final String patron = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]\\s[0-9][0-9]:[0-9][0-9]";
	private static final String patronSinHora = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
	
	
	public String getInit(){
		log.info("Init de la clase EdicionEventosCoporativosController");
		//recupero el valor del id del evento, lo hago obteniendo el mapa de parametros que traigo en el request
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(null != params.get("idEvco")){
			this.idEvco = params.get("idEvco");
			//efectuo la consulta que traera la informacion del evento corporativo
			EventoCorporativoEdicionDTO evcoDto = consultaEventosCorporativosService.getFullEventoCorporativo(Long.parseLong(this.idEvco));
			this.fechaRegistro = evcoDto.getFechaRegistro();
			this.tipoEvento = evcoDto.getTipoEvento();
			this.fechaCreacion = evcoDto.getFechaCreacion();
			this.tipoValor = evcoDto.getTipoValor();
			this.emisora = evcoDto.getEmisora();
			this.isin = evcoDto.getIsin();
			this.serie = evcoDto.getSerie(); 
			this.tipoMercado = evcoDto.getTipoMercado();
			this.custodio = evcoDto.getCustodio();
			this.tipoDerechoMO = evcoDto.getTipoDerechoMO();
			this.tipoDerechoML = evcoDto.getTipoDerechoML();
			this.fechaIndeval = evcoDto.getFechaIndeval();
			this.fechaCliente = evcoDto.getFechaCliente();
			this.fechaPago = evcoDto.getFechaPago();
			this.cuerpoEventoHtml = evcoDto.getCuerpoEventoHtml();
			this.piePaginaHtml = evcoDto.getPiePaginaHtml();
			this.listOp = evcoDto.getListOpciones();
			this.listNot = evcoDto.getListNotas();
			this.listAdjuntos = evcoDto.getListaAdjuntos();
			this.listNotificaciones = evcoDto.getListaNotificaciones();
			this.listValidaciones = evcoDto.getListValidaciones();
			this.operacion = "modifica";
			this.numOpcAccordion = evcoDto.getOpcAccordion();
			this.estado=evcoDto.getEstado();
		}

		return null;
	}
	
	public void deleteNota(ActionEvent event){
		int numNot = Integer.parseInt(numNota);
		int indiceLista = numNot - 1;
		int contador = 1;
		NotasDTO notasVo = (NotasDTO)event.getComponent().getAttributes().get("objectDelete");
		if(null != notasVo.getIdNota()){
			notasVo.setBorradoLogico(true);
			listNot.set(Integer.parseInt(notasVo.getNumNota()) - 1, notasVo);
		}else{
			listNot.remove(indiceLista);
			for(NotasDTO vo : listNot){
				vo.setNumNota(String.valueOf(contador));
				contador++;
			}
		}
	}
	
	public void addNotas(ActionEvent event){
		log.info("Ingreso al metodo addNotas");
		if(ADD_ACTION.equals(operacionNota)){
			//recupero los datos mapeados del dialogo y lo ingreso a la lista que pintara el resumen de las notas capturada
			log.info("Ingreso al metodo addNotas");
			notasVo = new NotasDTO();
			notasVo.setNumNota(String.valueOf(listNot.size() + 1));
			notasVo.setCuerpoNota(txtNota);
			//opcionesVo.setNumeroOpcion(String.valueOf(listOp.size() + 1));
			listNot.add(notasVo);
		}else if(EDIT_ACTION.equals(operacionNota)){
			NotasDTO notasEditar = null;
			notasEditar = listNot.get(Integer.parseInt(numOpcion) - 1);
			notasEditar.setCuerpoNota(txtNota);
			listNot.set(Integer.parseInt(numOpcion) - 1, notasEditar);
			
		}
	}
	
	public void deleteOption(ActionEvent event){
		int numOp = Integer.parseInt(numOpcion);
		int indiceLista = numOp - 1;
		int contador = 1;
		OpcionesDTO opcionesVo = (OpcionesDTO)event.getComponent().getAttributes().get("objectDelete");
		if(null != opcionesVo.getIdOpcion()){
			//existe por lo tanto cambio la bandera del objeto
			opcionesVo.setBorradoLogico(true);
			opcionesVo.setNumeroOpcion(0);
			//añado el objeto con la propiedad cambiada
			//listOp.set(indiceLista, opcionesVo);
		}else{
			//no existe en la base de datos, ejecuto el algoritmo de borrado
			Integer numlista = getIndiceListaOpcion(numOpcion);
			listOp.remove(numlista.intValue());
			
		}
		reasignarNumeracion();
	}
	
	
	private void reasignarNumeracion(){
		int contador=1;
		for(OpcionesDTO vo : listOp){
			if(!vo.isBorradoLogico()){
				vo.setNumeroOpcion(contador);
				contador++;
			}
		}
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
			try{
				//fechas
					if(!StringUtils.isEmpty(fc) && fc.matches(patron)){
						opcionesVo.setFechaClienteOpcion(stringToDate(fc));
					}else{
						opcionesVo.setFechaClienteOpcion(null);
					}
					if(!StringUtils.isEmpty(fi) && fi.matches(patron)){
						opcionesVo.setFechaIndevalOpcion(stringToDate(fi));
					}else{
						opcionesVo.setFechaIndevalOpcion(null);
					}
					if(!StringUtils.isEmpty(ftp) && ftp.matches(patronSinHora)){
						opcionesVo.setFechaPagoOpcion(fechaFormateadaSinHora.parse(ftp));
					}else{
						opcionesVo.setFechaPagoOpcion(null);
					}
				}catch(ParseException pex){
					addErrorMessage("No se puede generar la fecha para la opci\u00F3n deseada, consulte a soporte");
				}
			opcionesVo.setDefault(valDefault);
			//opcionesVo.setFechaClienteOpcion(fechaCli);
			Integer ultimoIndice = getUltimoIndiceOpcion();
			if(ultimoIndice != null){
				opcionesVo.setNumeroOpcion(ultimoIndice+1);
			}			
			
			listOp.add(opcionesVo);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
		}else if(EDIT_ACTION.equals(tipoOperacion)){
			OpcionesDTO opcionEditar = null;

			opcionEditar= getOpcionByNumero(numOpcion);
			opcionEditar.setCuerpoOpcion(txtHtmlArea);
			if(valDefault){
				opcionEditar.setValorDefault("Opci\u00F3n por Default");
			}else{
				opcionEditar.setValorDefault("");
			}
			opcionEditar.setFechaCliente(fc);
			opcionEditar.setFechaIndeval(fi);
			opcionEditar.setFechaPago(ftp);
			try{
			//fechas
				if(!StringUtils.isEmpty(fc) && fc.matches(patron)){
					opcionEditar.setFechaClienteOpcion(stringToDate(fc));
				}else{
					opcionEditar.setFechaClienteOpcion(null);
				}
				if(!StringUtils.isEmpty(fi) && fi.matches(patron)){
					opcionEditar.setFechaIndevalOpcion(stringToDate(fi));
				}else{
					opcionEditar.setFechaIndevalOpcion(null);
				}
				if(!StringUtils.isEmpty(ftp) && ftp.matches(patronSinHora)){					
					opcionEditar.setFechaPagoOpcion(fechaFormateadaSinHora.parse(ftp));
				}else{
					opcionEditar.setFechaPagoOpcion(null);
				}
			}catch(ParseException pex){
				addErrorMessage("No se puede generar la fecha para la opci\u00F3n deseada, consulte a soporte");
			}
			
			opcionEditar.setDefault(valDefault);
			//opcionEditar.setFechaClienteOpcion(fechaCli);
			opcionEditar.setNumeroOpcion(numOpcion);
			/*if(listOp.size() > 0){
				listOp.set(Integer.parseInt(numOpcion)-1, opcionEditar);
			}else{
				listOp.set(Integer.parseInt(numOpcion) - 1, opcionEditar);
			}*/
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
		}
		limpiaCamposOpciones();
	}
	
	private OpcionesDTO getOpcionByNumero(String numOpcion){
		if(numOpcion == null){
			return null;
		}
		for(OpcionesDTO opc : listOp){
			if(opc.getNumeroOpcion().equals(numOpcion)){
				return opc;
			}
		}
		return null;
	}
	
	private Integer getIndiceListaOpcion(String numOpcion){
		if(numOpcion == null){
			return null;
		}
		Integer indice = 0;
		for(OpcionesDTO opc : listOp){
			if(opc.getNumeroOpcion().equals(numOpcion)){
				return indice;
			}
			indice++;
		}
		return null;
	}
	
	private Integer getUltimoIndiceOpcion(){
		String ultimo = null;
		if(listOp.size()==0){
			return 0;
		}
		for(OpcionesDTO opc : listOp){
			if(!opc.getNumeroOpcion().equals("0")){
				ultimo=opc.getNumeroOpcion();
			}
		}
		if(StringUtils.isNotEmpty(ultimo) && StringUtils.isNumeric(ultimo)){
			return Integer.valueOf(ultimo);
		}else{
			return 0;
		}
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
	
	public void editOption(ActionEvent event){
		log.info("Ingreso al metodo editOption");
		OpcionesDTO vo = (OpcionesDTO)event.getComponent().getAttributes().get("objectEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objectEdit", vo);
	}
	
	
	
	public void borraAdjuntos(ActionEvent event){
		ArchivosAdjuntosEvcoDTO vo = (ArchivosAdjuntosEvcoDTO)event.getComponent().getAttributes().get("objectDelete");
		int indiceLista = Integer.parseInt(vo.getNumAdjunto()) - 1;
		int contador = 1;
		
		if(null != vo.getIdAdjuntos()){
			vo.setBorradoLogico(true);
			//añado el objeto con la propiedad cambiada
			//listAdjuntos.set(indiceLista - 1, vo);
			Iterator<ArchivosAdjuntosEvcoDTO> iter = listAdjuntos.iterator();
			while(iter.hasNext()){
				ArchivosAdjuntosEvcoDTO dto= iter.next();
				if(dto.getIdAdjuntos() !=null && vo.getIdAdjuntos().equals(dto.getIdAdjuntos())){
					try{
						saveEventosCorporativosService.deleteAdjunto(vo.getIdAdjuntos());
					}catch(BusinessException bex){
						addErrorMessage(bex.getMessage());
					}
					iter.remove();
					break;
				}
			}			
		}else{
			//no existe en la base de datos, ejecuto el algoritmo de borrado
			listAdjuntos.remove(indiceLista);
			for(ArchivosAdjuntosEvcoDTO voA : listAdjuntos){
				voA.setNumAdjunto(String.valueOf(contador));
				contador++;
			}
		}
	}
	
	public void editNota(ActionEvent event){
		log.info("Ingreso al metodo editNota");
		NotasDTO vo = (NotasDTO)event.getComponent().getAttributes().get("noteEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("noteEdit", vo);
	}
	
		
	
	public void deleteValidacion(ActionEvent event){
		ValidacionesEvcoDTO vo = (ValidacionesEvcoDTO)event.getComponent().getAttributes().get("objectDelete");
		int numeroValidacion = Integer.parseInt(numValidacion);
		int indiceLista = numeroValidacion - 1;
		int contador = 1;
		
		if(null != vo.getIdValidacion()){
			vo.setBorradoLogico(true);
			listValidaciones.set(indiceLista, vo);
			for(ValidacionesEvcoDTO voIn : listValidaciones){
				if(!voIn.isBorradoLogico()){
					voIn.setStrNumValidacion(String.valueOf(contador));
					contador++;
				}
			}
		}else{
			//no existe en la base de datos, ejecuto el algoritmo de borrado
			listValidaciones.remove(vo.getPosicionLista());
			for(ValidacionesEvcoDTO voIn : listValidaciones){
				if(!voIn.isBorradoLogico()){
					voIn.setStrNumValidacion(String.valueOf(contador));
					contador++;
				}
			}
		}
	}
	
	public void setDataCombos(ActionEvent event){
		List<TipoValidacionEvco> tipoValEvco = consultaEventosCorporativosService.getAllTipoValidacionEvco();
		List<OperadorEvco> tipoOpEvco = consultaEventosCorporativosService.getAllOperadorEvco();
		//Ingreso ambas consultas en session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listTipoValEvco", tipoValEvco);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listTipoOpEvco", tipoOpEvco);
	}
	
	public void addValidaciones(ActionEvent event){
		int contador = 0;
		if(ADD_ACTION.equals(operacionValidacion)){
			validacionesVo = new ValidacionesEvcoDTO();
			//obtengo las descripciones de los elementos seleccionados en los combos
			//para la posicion 
			validacionesVo.setStrValidacion(consultaEventosCorporativosService.getTipoValidacionById(Long.parseLong(validacionVal)).getNombre());
			validacionesVo.setStrOperador(consultaEventosCorporativosService.getOperadorById(Long.parseLong(operadorVal)).getOperador());
			validacionesVo.setIdOperador(operadorVal);
			validacionesVo.setTipoValidacion(validacionVal);
			validacionesVo.setValor(Long.parseLong(cantidadVal));
			validacionesVo.setStrCantidadCapturada(cantidadVal);
			
				validacionesVo.setStrNumValidacion(String.valueOf(listValidaciones.size() + 1));
			
			listValidaciones.add(validacionesVo);
			for(ValidacionesEvcoDTO tdo : listValidaciones){
				tdo.setPosicionLista(contador);
				contador++;
			}
			log.debug("Validaciones size:",listValidaciones.size());
		}
	}
	
	public void editaNotificacion(ActionEvent event){
		NotificacionesEvcoDTO vo = (NotificacionesEvcoDTO)event.getComponent().getAttributes().get("notificacionEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("notificacionEdit", vo);
	}
	
	public void addNotificaciones(ActionEvent event){
		log.info("Ingresa al metodo addNotificaciones");
		int contador = 0;
		Map<String, Object> mapConfiguraciones = new LinkedHashMap<String, Object>();
		if(ADD_ACTION.equals(operacionNotificacion)){
			if(!StringUtils.isEmpty(periodoNot)){
				notificacionesVo = new NotificacionesEvcoDTO();
				//para la peridiocidad evaluo el valor que traemos de la vista
				switch(Integer.parseInt(periodoNot)){
				
				case 0:
					notificacionesVo.setTipoNotificacion("Cada " + minutosNot + " minuto(s)" );
					mapConfiguraciones.put("minutos", minutosNot);
					notificacionesVo.setPatronCron(generateCronExpression(0, mapConfiguraciones));
					break;
					
				case 1:
					//verifico el tipo de configuracion ingresado
					if("hora".equals(tipoConfiguracion)){
						notificacionesVo.setTipoNotificacion("Cada " + horaNot + " hora(s)");
						mapConfiguraciones.put("hora", horaNot);
					}else{
						notificacionesVo.setTipoNotificacion("Cada " + horaNot + " hora(s) " + " con " + minutosNot + " minuto(s)");
						mapConfiguraciones.put("horaHora", horaNot);
						mapConfiguraciones.put("minutosHora", minutosNot);
					}
					notificacionesVo.setPatronCron(generateCronExpression(1, mapConfiguraciones));
					break;
					
				case 2:
					if("dia".equals(tipoConfiguracion)){
						notificacionesVo.setTipoNotificacion("Cada " + numeroDias + " dia(s) \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasDias", numeroDias);
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}else{
						notificacionesVo.setTipoNotificacion("Todos los dias \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}
					notificacionesVo.setPatronCron(generateCronExpression(2, mapConfiguraciones));
					break;
					
				case 3:
					String[] arrayD = setDayName(arrayDiasSem);
					notificacionesVo.setArrayDias(arrayDiasSem);
					notificacionesVo.setTipoNotificacion("Los dias " + setArrayNames(arrayD) + " de la semana a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("semanaDias", arrayDiasSem);
					mapConfiguraciones.put("semanaHora", horaNot);
					mapConfiguraciones.put("semanaMinutos", minutosNot);
					notificacionesVo.setPatronCron(generateCronExpression(3, mapConfiguraciones));
					break;
					
				case 4:
					notificacionesVo.setTipoNotificacion("Los dias " + diaMes + " del mes " + mesMes + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
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
				notificacionesVo.setTextAdicional(textoNot);
				notificacionesVo.setStrTextoNotificacion(textoNot);
				notificacionesVo.setStrDesde(fechaIncioNot);
				notificacionesVo.setStrFechaIncio(fechaIncioNot);
				notificacionesVo.setStrHasta(fechaFinNot);
				notificacionesVo.setStrFechaFin(fechaFinNot);
				notificacionesVo.setNumNotificacion(String.valueOf(listNotificaciones.size() + 1));
				notificacionesVo.setNumOpcAcc(numOpcAccordion);
				//recupero el valor de base de datos de acuerdo a la opcion ingresada por el usuario
				notificacionesVo.setIdListaDIstribucion(destinatarioNot);
				notificacionesVo.setDestinatario(consultaEventosCorporativosService.getListaDisById(Long.parseLong(destinatarioNot)).getNombre());
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
					String cron = generateCronExpression(0, mapConfiguraciones);
					if(StringUtils.isNotEmpty(cron)){
						vo.setPatronCron(cron);
					}else{
						addErrorMessage("Patron Cron desconocido: "+cron);
					}
					break;
					
				case 1:
					if("hora".equals(tipoConfiguracion)){
						vo.setStrPeridiocidad("Cada " + horaNot + " hora(s)");
						mapConfiguraciones.put("hora", horaNot);
					}else{
						vo.setStrPeridiocidad("Cada " + horaNot + " hora(s) " + " con " + minutosNot + " minuto(s)");
						mapConfiguraciones.put("horaHora", horaNot);
						mapConfiguraciones.put("minutosHora", minutosNot);
					}
					vo.setPatronCron(generateCronExpression(1, mapConfiguraciones));
					break;
					
				case 2:
					if("dia".equals(tipoConfiguracion)){
						vo.setStrPeridiocidad("Cada " + numeroDias + " dia(s) \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasDias", numeroDias);
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}else{
						vo.setStrPeridiocidad("Todos los dias \n " + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos" );
						mapConfiguraciones.put("diasHora", horaNot);
						mapConfiguraciones.put("diasMinuto", minutosNot);
					}
					vo.setPatronCron(generateCronExpression(2, mapConfiguraciones));
					break;
				
				case 3:
					String[] arrayD = setDayName(arrayDiasSem);
					vo.setArrayDias(arrayDiasSem);
					vo.setStrPeridiocidad("Los dias " + setArrayNames(arrayD) + " de la semana a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("semanaDias", arrayDiasSem);
					mapConfiguraciones.put("semanaHora", horaNot);
					mapConfiguraciones.put("semanaMinutos", minutosNot);
					vo.setPatronCron(generateCronExpression(3, mapConfiguraciones));
					break;
				
				case 4:
					vo.setStrPeridiocidad("El d\u00EDa " + diaMes + " del mes " + mesMes + " a las " + horaNot + " hora(s) con " + minutosNot + " minutos");
					mapConfiguraciones.put("mesDia", diaMes);
					mapConfiguraciones.put("mesMes", mesMes);
					mapConfiguraciones.put("mesHora", horaNot);
					mapConfiguraciones.put("mesMinuto", minutosNot);
					vo.setPatronCron(generateCronExpression(4, mapConfiguraciones));
					break;
				}
				vo.setMinCapturado(minutosNot);
				vo.setHrCapturada(horaNot);
				vo.setStrTextoNotificacion(textoNot);
				vo.setTextAdicional(textoNot);
				vo.setArrayDias(arrayDiasSem);
				vo.setDiaCapturado(numeroDias);
				vo.setMesCapturado(mesMes);
				vo.setNumNotificacion(numNotificacion);
				vo.setNumOpcAcc(periodoNot);
				vo.setPeriodo(periodoNot);
				ListaDistribucion lista =consultaEventosCorporativosService.getListaDisById(Long.parseLong(destinatarioNot));
				vo.setStrDestinatario(lista.getNombre());
				vo.setIdListaDIstribucion(destinatarioNot);
				vo.setDestinatario(lista.getNombre());
				vo.setStrFechaFin(fechaFinNot);
				vo.setStrFechaIncio(fechaIncioNot);
				vo.setStrDesde(fechaIncioNot);
				vo.setStrHasta(fechaFinNot);
				if(vo.getPatronCron() != null){
					vo.setCronExpression(vo.getPatronCron());
				}
				
				
				listNotificaciones.set(Integer.parseInt(numNotificacion) - 1, vo);
			}
		}
		for(NotificacionesEvcoDTO dto : listNotificaciones){
			dto.setPosicionLista(contador);
			contador++;
		}
	}
	

	public void deleteNotificacion(ActionEvent event){
		int contador = 1;
		NotificacionesEvcoDTO vo = (NotificacionesEvcoDTO)event.getComponent().getAttributes().get("objectDelete");

		if(null != vo.getIdNotificacion()){
			vo.setBorradoLogico(true);
			
			for(NotificacionesEvcoDTO dto : listNotificaciones){
				if(!dto.isBorradoLogico()){
					dto.setNumNotificacion(String.valueOf(contador));
					contador++;
				}
			}
		}else{
			listNotificaciones.remove(vo.getPosicionLista());
			for(NotificacionesEvcoDTO dtoIn : listNotificaciones){
				if(!dtoIn.isBorradoLogico()){
					dtoIn.setNumNotificacion(String.valueOf(contador));
					contador++;
				}
			}
		}
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
			// 	0 15 6 5 1/12 ? *
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
	
	private String[] setDayName(String[] array){
		String[]arrayDias = new String[array.length];
		for(int i = 0; i < array.length; i++){
			if("MON".equals(array[i])){
				arrayDias[i] = "Lunes";
			}else if("TUE".equals(array[i])){
				arrayDias[i] = "Martes";
			}else if("WED".equals(array[i])){
				arrayDias[i] = "Mi\u00E9rcoles";
			}else if("THU".equals(array[i])){
				arrayDias[i] = "Jueves";
			}else if("FRI".equals(array[i])){
				arrayDias[i] = "Viernes";
			}
		}
		return arrayDias;
	}
	
	public void updateEventoCorporativo(ActionEvent e){
		boolean bandera =false;
		Long idEventoGuardado=null;
		setParams();
		dto.setListOpciones(listOp);
		dto.setListNotas(listNot);
		dto.setListaAdjuntos(listAdjuntos);
		dto.setListNotificaciones(listNotificaciones);
		dto.setListValidaciones(listValidaciones);
		
		try{
			idEventoGuardado = saveEventosCorporativosService.updateEventoCorporativo(dto);
			bandera = true;
			this.banderaCreacion=true;
		}catch(BusinessException be){
			addErrorMessage(be.getMessage());
			bandera = false;
			this.banderaCreacion=false;
		}finally{
			if(bandera){
				//limpia
				this.listAdjuntos=null;
				this.listNot=null;
				this.listOp=null;
				this.listNotificaciones=null;
				this.listValidaciones=null;
				if(idEventoGuardado != null){
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEventoGuardado", idEventoGuardado);
				}
			}
		}
		//return null;
	}
	
	public void setParams(){
		dto = new EventoCorporativoEdicionDTO();
		dto.setIdEventoCorporativo(this.idEvco);
		dto.setFechaCreacion(this.fechaCreacion);
		dto.setFechaRegistro(this.fechaRegistro);
		if(StringUtils.isNotEmpty(this.tipoValor)){
			dto.setTipoValor(this.tipoValor.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.emisora )){
			dto.setEmisora(this.emisora.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.serie )){
			dto.setSerie(this.serie.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.isin )){
			dto.setIsin(this.isin.toUpperCase());
		}		
		if (this.tipoDerechoMO != null && !this.tipoDerechoMO.equals("") && this.tipoDerechoMO.matches("-*[0-9]+")){
			dto.setTipoDerechoMO(this.tipoDerechoMO);
		}
		if (this.tipoDerechoML != null && !this.tipoDerechoML.equals("") && this.tipoDerechoML.matches("-*[0-9]+")){
			dto.setTipoDerechoML(this.tipoDerechoML);
		}
		if (this.tipoMercado != null && !this.tipoMercado.equals("") && this.tipoMercado.matches("-*[0-9]+")){
			dto.setTipoMercado(this.tipoMercado);
		}
		if (this.tipoEvento != null && !this.tipoEvento.equals("") && this.tipoEvento.matches("-*[0-9]+")){
			dto.setTipoEvento(this.tipoEvento);
		}
		dto.setFechaIndeval(this.fechaIndeval);		
		dto.setFechaCliente(this.fechaCliente);		
		dto.setFechaPago(this.fechaPago);
		dto.setPiePaginaHtml(this.piePaginaHtml);
		dto.setCuerpoEventoHtml(this.cuerpoEventoHtml);
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")){
			dto.setCustodio(this.custodio);
		}	
		dto.setUsuario(getNombreUsuarioSesionLocal());
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
						this.listaCustodios.addAll(this.listaCustodiosBuffer );
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
	
	
	private String dateToString(Date fecha){
		if(fecha != null){
			return fechaFormateada.format(fecha);
		}else{
			return null;
		}
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
	
	
	public void cambiaTipoEvento(ValueChangeEvent e){		
		setTipoEvento(e.getNewValue().toString());	
		
	}
	
	
	public String getIdEvco() {
		return idEvco;
	}


	public void setIdEvco(String idEvco) {
		this.idEvco = idEvco;
	}


	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}


	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<SelectItem> getListaTipoEvento() {
		return listaTipoEvento;
	}

	public void setListaTipoEvento(List<SelectItem> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public String getEmisora() {
		return emisora;
	}

	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public List<SelectItem> getListaTipoMercado() {
		return listaTipoMercado;
	}

	public void setListaTipoMercado(List<SelectItem> listaTipoMercado) {
		this.listaTipoMercado = listaTipoMercado;
	}

	public String getTipoMercado() {
		return tipoMercado;
	}

	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}

	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	public String getTipoDerechoMO() {
		return tipoDerechoMO;
	}

	public void setTipoDerechoMO(String tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}

	public List<SelectItem> getListaTipoDerechoMO() {
		return listaTipoDerechoMO;
	}

	public void setListaTipoDerechoMO(List<SelectItem> listaTipoDerechoMO) {
		this.listaTipoDerechoMO = listaTipoDerechoMO;
	}

	public List<SelectItem> getListaTipoDerechoML() {
		return listaTipoDerechoML;
	}

	public void setListaTipoDerechoML(List<SelectItem> listaTipoDerechoML) {
		this.listaTipoDerechoML = listaTipoDerechoML;
	}

	public String getTipoDerechoML() {
		return tipoDerechoML;
	}

	public void setTipoDerechoML(String tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}

	public Date getFechaIndeval() {
		return fechaIndeval;
	}

	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}

	public Date getFechaCliente() {
		return fechaCliente;
	}

	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getCuerpoEventoHtml() {
		return cuerpoEventoHtml;
	}

	public void setCuerpoEventoHtml(String cuerpoEventoHtml) {
		this.cuerpoEventoHtml = cuerpoEventoHtml;
	}

	public List<OpcionesDTO> getListOp() {
		return listOp;
	}

	public void setListOp(List<OpcionesDTO> listOp) {
		this.listOp = listOp;
	}

	public List<NotasDTO> getListNot() {
		return listNot;
	}

	public void setListNot(List<NotasDTO> listNot) {
		this.listNot = listNot;
	}

	public List<ArchivosAdjuntosEvcoDTO> getListAdjuntos() {
		return listAdjuntos;
	}

	public void setListAdjuntos(List<ArchivosAdjuntosEvcoDTO> listAdjuntos) {
		this.listAdjuntos = listAdjuntos;
	}

	public ArchivosAdjuntosEvcoDTO getArchivoVo() {
		return archivoVo;
	}


	public void setArchivoVo(ArchivosAdjuntosEvcoDTO archivoVo) {
		this.archivoVo = archivoVo;
	}


	public List<NotificacionesEvcoDTO> getListNotificaciones() {
		return listNotificaciones;
	}



	public void setListNotificaciones(List<NotificacionesEvcoDTO> listNotificaciones) {
		this.listNotificaciones = listNotificaciones;
	}



	public List<ValidacionesEvcoDTO> getListValidaciones() {
		return listValidaciones;
	}



	public void setListValidaciones(List<ValidacionesEvcoDTO> listValidaciones) {
		this.listValidaciones = listValidaciones;
	}



	public String getPiePaginaHtml() {
		return piePaginaHtml;
	}



	public void setPiePaginaHtml(String piePaginaHtml) {
		this.piePaginaHtml = piePaginaHtml;
	}

	public String getNumOpcion() {
		return numOpcion;
	}

	public void setNumOpcion(String numOpcion) {
		this.numOpcion = numOpcion;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public OpcionesDTO getOpcionesVo() {
		return opcionesVo;
	}

	public void setOpcionesVo(OpcionesDTO opcionesVo) {
		this.opcionesVo = opcionesVo;
	}

	public String getTxtHtmlArea() {
		return txtHtmlArea;
	}

	public void setTxtHtmlArea(String txtHtmlArea) {
		this.txtHtmlArea = txtHtmlArea;
	}

	public boolean isValDefault() {
		return valDefault;
	}

	public void setValDefault(boolean valDefault) {
		this.valDefault = valDefault;
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

	public Date getFechaCli() {
		return fechaCli;
	}

	public void setFechaCli(Date fechaCli) {
		this.fechaCli = fechaCli;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getTxtNota() {
		return txtNota;
	}

	public void setTxtNota(String txtNota) {
		this.txtNota = txtNota;
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

	public NotasDTO getNotasVo() {
		return notasVo;
	}

	public void setNotasVo(NotasDTO notasVo) {
		this.notasVo = notasVo;
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

	public String getNumValidacion() {
		return numValidacion;
	}

	public void setNumValidacion(String numValidacion) {
		this.numValidacion = numValidacion;
	}

	public ValidacionesEvcoDTO getValidacionesVo() {
		return validacionesVo;
	}

	public void setValidacionesVo(ValidacionesEvcoDTO validacionesVo) {
		this.validacionesVo = validacionesVo;
	}

	public String getHoraNot() {
		return horaNot;
	}

	public void setHoraNot(String horaNot) {
		this.horaNot = horaNot;
	}

	public String getMinutosNot() {
		return minutosNot;
	}

	public void setMinutosNot(String minutosNot) {
		this.minutosNot = minutosNot;
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

	public NotificacionesEvcoDTO getNotificacionesVo() {
		return notificacionesVo;
	}

	public void setNotificacionesVo(NotificacionesEvcoDTO notificacionesVo) {
		this.notificacionesVo = notificacionesVo;
	}

	public EventoCorporativoEdicionDTO getDto() {
		return dto;
	}

	public void setDto(EventoCorporativoEdicionDTO dto) {
		this.dto = dto;
	}

	public SaveEventosCorporativosService getSaveEventosCorporativosService() {
		return saveEventosCorporativosService;
	}

	public void setSaveEventosCorporativosService(
			SaveEventosCorporativosService saveEventosCorporativosService) {
		this.saveEventosCorporativosService = saveEventosCorporativosService;
	}
	
	protected String getNombreUsuarioSesionLocal() {
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

	/**
	 * @return the banderaCreacion
	 */
	public boolean isBanderaCreacion() {
		return banderaCreacion;
	}

	/**
	 * @param banderaCreacion the banderaCreacion to set
	 */
	public void setBanderaCreacion(boolean banderaCreacion) {
		this.banderaCreacion = banderaCreacion;
	}

	/**
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @return the validaEmision
	 */
	public boolean getEmisionEncontrada() {
		return emisionEncontrada;
	}

	/**
	 * @param validaEmision the validaEmision to set
	 */
	public void setEmisionEncontrada(boolean emisionEncontrada) {
		this.emisionEncontrada = emisionEncontrada;
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
	
	private Date stringToDate(String fecha) throws ParseException{
		if(StringUtils.isNotEmpty(fecha)){
			return fechaFormateada.parse(fecha);
		}else{
			return null;
		}		
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
	
	
}
