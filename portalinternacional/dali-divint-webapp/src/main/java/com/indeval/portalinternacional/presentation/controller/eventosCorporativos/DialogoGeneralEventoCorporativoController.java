package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.services.validador.ValidarCamposEventosCorporativosImp;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class DialogoGeneralEventoCorporativoController extends ControllerBase {
	
	private static final Logger log = LoggerFactory.getLogger(DialogoGeneralEventoCorporativoController.class);
	//definicion de propiedades mapeadas en la vista
	//Defino la lista que va a permitir renderear la captura en la tabla que muestra el resumen
	private OpcionesDTO opcionesVo;
	private NotasDTO notasVo;
	private List<OpcionesDTO> listOp = new ArrayList<OpcionesDTO>();
	private List<NotasDTO> listNot = new ArrayList<NotasDTO>(); 
	private String txtHtmlArea; //mapeamos el textarea que contiene el texto capturado en formato HTML
	private boolean valDefault; //mapeamos el valor del checkbox Default
	private String txtOpcionHtml;
	private Date fechaCli;
	private String fc;
	private String fi;
	private String ftp;
	private Date fechaIn;
	private Date fechaTiPa;
	private String numOpcion;
	private String tipoOperacion;
	private String txtNota;
	//Para la edicion de notificaciones, dividimos en secciones
	//Numero de opcion del acordeon
	private String numOpcAcc;
	//Seccion configuracion por minutos
	private String minConfMinutos;
	
	//Seccion configuracion por horas 
	private String hrsCadaHr; //campo de captura para las horas cada _ Hrs
	private boolean boolCada = true; //campo checkbox para elegir la opcion de captura cada _ Hrs
	private boolean boolDesde; // campo checkbox para elegir la opcion de captura desde _ _
	private String hrCapDesde; //campo tipo spin que captura las horas en la opcion desde _ _
	private String minCapDesde; //campo de tipo spin que captura los minutos en la opcion desde _ _
	
	//Seccion configuracion por dias
	private boolean boolCadaDias;
	private String diasCapDias;
	private boolean boolTodos;
	private String hrCapDias;
	private String minCapDias;
	
	//Seccion configuracion por semana
	private String[] arrayDias;
	private String hrSemana;
	private String minSemana;
	
	//Seccion configuracion por mes
	private String diaMes;
	private String mesMes;
	private String hrMes;
	private String minMes;
	
	//Seccion general
	private String strDestinatario;
	private String strTextoNotificacion;
	private Date fchInicio;
	private Date fchFin;
	
	//Lista para combo de lista de distribuciones
	private List<SelectItem> listDistribucion;
	
	//Listas para los combos de validaciones
	private List<SelectItem> listValidaciones;
	private List<SelectItem> listOperadores;
	
	private String validacion;
	private String operador;
	private String valorLista;
	private String txtValor;
	private boolean validacionHecha=true;
	private static final String patron = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]\\s[0-9][0-9]:[0-9][0-9]";
	
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	
	private ValidarCamposEventosCorporativosImp validaDialogoForm;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public DialogoGeneralEventoCorporativoController(){
		
	}
	
	public String getInit(){
		log.info("Init en clase DialogoGeneralEventoCorporativoController");
		try{
			OpcionesDTO voSession = (OpcionesDTO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objectEdit");
			NotasDTO notasSession = (NotasDTO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("noteEdit");
			NotificacionesEvcoDTO notificacionesSession = (NotificacionesEvcoDTO)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("notificacionEdit");
			if(null != voSession){
				if(StringUtils.isNotEmpty(voSession.getValorDefault())){
					this.valDefault = true;
				}
				this.txtHtmlArea = voSession.getCuerpoOpcion();
				this.fechaCli = stringToDate(voSession.getFechaCliente());
				this.fechaIn = stringToDate(voSession.getFechaIndeval());
				this.fechaTiPa = stringToDate(voSession.getFechaPago());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
			}
			
			if(null != notasSession){
				this.txtNota = notasSession.getCuerpoNota();
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("noteEdit");
			}
			
			if(null != notificacionesSession){
				//pregunto si el objeto tiene asociado un id de notificacion, si es asi, el objeto viene de base de datos
				if(null != notificacionesSession.getIdNotificacion()){
					//obtengo los valores a mapear en el dialogo en base al cronexpression que traigo de la base de datos
					setValueNotificaciones(Integer.parseInt(notificacionesSession.getNumOpcAcc()), notificacionesSession.getCronExpression());
				}else{
					//obtengo el numero de opcion seleccionado
					this.numOpcAcc = notificacionesSession.getNumOpcAcc();
					
					if("0".equals(numOpcAcc)){
						this.minConfMinutos = notificacionesSession.getMinCapturado();
					}else if("1".equals(numOpcAcc)){
						if("hora".equals(notificacionesSession.getPeriodo())){
							notificacionesSession.setMinCapturado("");
							this.hrsCadaHr = notificacionesSession.getHrCapturada();
							this.boolCada = true;
						}else{
							notificacionesSession.setMinCapturado("");
							this.boolDesde = true;
							this.hrCapDesde = notificacionesSession.getHrCapturada();
							this.minCapDesde = notificacionesSession.getMinCapturado();
						}
					}else if("2".equals(numOpcAcc)){
						if("dia".equals(notificacionesSession.getPeriodo())){
							this.boolCadaDias = true;
							this.diasCapDias = notificacionesSession.getDiaCapturado();
						}else{
							this.boolTodos = true;
							this.hrCapDias = notificacionesSession.getHrCapturada();
							this.minCapDias = notificacionesSession.getMinCapturado();
						}
					}else if("3".equals(numOpcAcc)){
						this.arrayDias = notificacionesSession.getArrayDias();
						this.hrSemana = notificacionesSession.getHrCapturada();
						this.minSemana = notificacionesSession.getMinCapturado();
					}else if("4".equals(numOpcAcc)){
						this.diaMes = notificacionesSession.getDiaCapturado();
						this.mesMes = notificacionesSession.getMesCapturado();
						this.hrMes = notificacionesSession.getHrCapturada();
						this.minMes = notificacionesSession.getMinCapturado();
					}
				}
				//datos comunes
				this.valorLista = notificacionesSession.getIdListaDIstribucion();
				this.strTextoNotificacion = notificacionesSession.getStrTextoNotificacion();
				if(!StringUtils.isEmpty(notificacionesSession.getStrFechaIncio()) && notificacionesSession.getStrFechaIncio().matches(patron)){
					this.fchInicio = stringToDate(notificacionesSession.getStrFechaIncio());
				}
				if(!StringUtils.isEmpty(notificacionesSession.getStrFechaFin()) && notificacionesSession.getStrFechaFin().matches(patron)){
					this.fchFin = stringToDate(notificacionesSession.getStrFechaFin());
				}
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("notificacionEdit");
				this.validacion = "-1";
				this.operador = "-1";
			}
		}catch(ParseException pe){
			agregarMensaje(new BusinessException("Error en formato de fecha"));
		}finally{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("notificacionEdit");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("noteEdit");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("objectEdit");
		}
		return "";
	}
	
	public void setValueNotificaciones(int numOpcion, String cronExpression){
		Pattern pattern = null;
		Matcher matcher = null;
		
		switch(numOpcion){
		
		//configuracion por minutos  	0 0/25 * 1/1 * ? *
		case 0:
			pattern = Pattern.compile("(0)\\/\\d+");
			matcher = pattern.matcher(cronExpression);
			
			if(matcher.find()){
				this.minConfMinutos = matcher.group().substring(matcher.group().indexOf("/") + 1,matcher.group().length());
			}
			break;
		
		//configuracion por minutos
		//dos tipos: captura el numero total de horas  	0 0 0/15 1/1 * ? *
		// captura horas y minutos 						0 12 12 1/1 * ? *
		case 1:
			pattern = Pattern.compile("(0)\\/\\d+");
			matcher = pattern.matcher(cronExpression);
			
			if(matcher.find()){
				this.hrsCadaHr = matcher.group().substring(matcher.group().indexOf("/") + 1, matcher.group().length());
				this.boolCada = true;
			}else{
				this.boolDesde = true;
				String array [] = matcher.group().split(" ");
				this.hrCapDesde = array[1];
				this.minCapDesde = array[0];
			}
			break;
		
		}
	}
	
	public List<SelectItem> getTipoOperadoresEvco(){
		List<OperadorEvco> operadores = consultaEventosCorporativosService.getAllOperadorEvco();
		this.listOperadores = new ArrayList<SelectItem>();
		if(operadores != null){
			for(OperadorEvco vo : operadores){
				this.listOperadores.add(new SelectItem(vo.getIdoperador().toString(), vo.getOperador()));
			}
		}else{
			this.listOperadores.add(new SelectItem("-2","VACIO"));
		}
		return this.listOperadores;
	}
	
	
	public List<SelectItem> getTipoValidacionesEvco(){
		List<TipoValidacionEvco> validaciones = consultaEventosCorporativosService.getAllTipoValidacionEvco();
		this.listValidaciones = new ArrayList<SelectItem>();
		if(validaciones != null){
			for(TipoValidacionEvco vo : validaciones){
				this.listValidaciones.add(new SelectItem(vo.getIdtipoValidacion().toString(), vo.getNombre()));
			}
		}else{
			this.listValidaciones.add(new SelectItem("-2","VACIO"));
		}
		return this.listValidaciones;
	}
		public void validaNotas()
	{
		
		try{
			
			validaDialogoForm.validaNotasEvco(this.txtNota);
		}
		catch (BusinessException e) {

			agregarMensaje(e);
		}

	}
	
	
	public void validaOpciones()
	{
		this.validacionHecha=true;

		try{
			
			validaDialogoForm.validaOpcionesEvco(this.txtHtmlArea,this.fechaTiPa,this.fechaIn,this.fechaCli);
		}
		catch (BusinessException e) {
			this.validacionHecha=false;
			agregarMensaje(e);
		}
	}
	
	public void validaNotificaciones()
	{
		this.validacionHecha=true;

		try{
			
			validaDialogoForm.validaNotificacionesEvco(this.strTextoNotificacion,this.valorLista, this.fchInicio,this.fchFin);
		}
		catch (BusinessException e) {
			this.validacionHecha=false;
			agregarMensaje(e);
		}
		
	}
	public void validarValidaciones()
	{
		this.validacionHecha=true;
		log.info("VALIDANDO Validaciones::: CONTORLLER  ");

		try{
			
			validaDialogoForm.validarValidacionesEvco(this.validacion,this.operador, this.txtValor);
		}
		catch (BusinessException e) {
			this.validacionHecha=false;
			agregarMensaje(e);
		}
	}
	
	public List<SelectItem> getListaDistribucionEvco(){
		List<ListaDistribucion> distribucion = consultaEventosCorporativosService.getAllListaDistribucion(); 
		this.listDistribucion = new ArrayList<SelectItem>();
		if(distribucion != null){
			for(ListaDistribucion vo : distribucion){
				this.listDistribucion.add(new SelectItem(vo.getIdLista().toString(), vo.getNombre()));
			}
		}else{
			this.listDistribucion.add(new SelectItem("-2","VACIO"));
		}
		return this.listDistribucion;
	}

	
	private Date stringToDate(String fecha) throws ParseException{
		
		Date date=null;
		if(fecha != null && !fecha.equals(""))
		{
			 date = format.parse(fecha);
		}
		return date;
	}

	public OpcionesDTO getOpcionesVo() {
		return opcionesVo;
	}

	public void setOpcionesVo(OpcionesDTO opcionesVo) {
		this.opcionesVo = opcionesVo;
	}

	public List<OpcionesDTO> getListOp() {
		return listOp;
	}

	public void setListOp(List<OpcionesDTO> listOp) {
		this.listOp = listOp;
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

	public Date getFechaCli() {
		return fechaCli;
	}

	public void setFechaCli(Date fechaCli) {
		this.fechaCli = fechaCli;
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

	public String getTxtOpcionHtml() {
		return txtOpcionHtml;
	}

	public void setTxtOpcionHtml(String txtOpcionHtml) {
		this.txtOpcionHtml = txtOpcionHtml;
	}

	public List<NotasDTO> getListNot() {
		return listNot;
	}

	public void setListNot(List<NotasDTO> listNot) {
		this.listNot = listNot;
	}

	public NotasDTO getNotasVo() {
		return notasVo;
	}

	public void setNotasVo(NotasDTO notasVo) {
		this.notasVo = notasVo;
	}

	public String getTxtNota() {
		return txtNota;
	}

	public void setTxtNota(String txtNota) {
		this.txtNota = txtNota;
	}

	public String getMinConfMinutos() {
		return minConfMinutos;
	}

	public void setMinConfMinutos(String minConfMinutos) {
		this.minConfMinutos = minConfMinutos;
	}

	public String getNumOpcAcc() {
		return numOpcAcc;
	}

	public void setNumOpcAcc(String numOpcAcc) {
		this.numOpcAcc = numOpcAcc;
	}

	public String getHrsCadaHr() {
		return hrsCadaHr;
	}

	public void setHrsCadaHr(String hrsCadaHr) {
		this.hrsCadaHr = hrsCadaHr;
	}

	public String getStrDestinatario() {
		return strDestinatario;
	}

	public void setStrDestinatario(String strDestinatario) {
		this.strDestinatario = strDestinatario;
	}

	public String getStrTextoNotificacion() {
		return strTextoNotificacion;
	}

	public void setStrTextoNotificacion(String strTextoNotificacion) {
		this.strTextoNotificacion = strTextoNotificacion;
	}

	public Date getFchInicio() {
		return fchInicio;
	}

	public void setFchInicio(Date fchInicio) {
		this.fchInicio = fchInicio;
	}

	public Date getFchFin() {
		return fchFin;
	}

	public void setFchFin(Date fchFin) {
		this.fchFin = fchFin;
	}

	public boolean isBoolCada() {
		return boolCada;
	}

	public void setBoolCada(boolean boolCada) {
		this.boolCada = boolCada;
	}

	public boolean isBoolDesde() {
		return boolDesde;
	}

	public void setBoolDesde(boolean boolDesde) {
		this.boolDesde = boolDesde;
	}

	public String getHrCapDesde() {
		return hrCapDesde;
	}

	public void setHrCapDesde(String hrCapDesde) {
		this.hrCapDesde = hrCapDesde;
	}

	public String getMinCapDesde() {
		return minCapDesde;
	}

	public void setMinCapDesde(String minCapDesde) {
		this.minCapDesde = minCapDesde;
	}

	public boolean isBoolCadaDias() {
		return boolCadaDias;
	}

	public void setBoolCadaDias(boolean boolCadaDias) {
		this.boolCadaDias = boolCadaDias;
	}

	public String getDiasCapDias() {
		return diasCapDias;
	}

	public void setDiasCapDias(String diasCapDias) {
		this.diasCapDias = diasCapDias;
	}

	public boolean isBoolTodos() {
		return boolTodos;
	}

	public void setBoolTodos(boolean boolTodos) {
		this.boolTodos = boolTodos;
	}

	public String getHrCapDias() {
		return hrCapDias;
	}

	public void setHrCapDias(String hrCapDias) {
		this.hrCapDias = hrCapDias;
	}

	public String getMinCapDias() {
		return minCapDias;
	}

	public void setMinCapDias(String minCapDias) {
		this.minCapDias = minCapDias;
	}

	public String[] getArrayDias() {
		return arrayDias;
	}

	public void setArrayDias(String[] arrayDias) {
		this.arrayDias = arrayDias;
	}

	public String getHrSemana() {
		return hrSemana;
	}

	public void setHrSemana(String hrSemana) {
		this.hrSemana = hrSemana;
	}

	public String getMinSemana() {
		return minSemana;
	}

	public void setMinSemana(String minSemana) {
		this.minSemana = minSemana;
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

	public String getHrMes() {
		return hrMes;
	}

	public void setHrMes(String hrMes) {
		this.hrMes = hrMes;
	}

	public String getMinMes() {
		return minMes;
	}

	public void setMinMes(String minMes) {
		this.minMes = minMes;
	}

	public List<SelectItem> getListValidaciones() {
		return listValidaciones;
	}

	public void setListValidaciones(List<SelectItem> listValidaciones) {
		this.listValidaciones = listValidaciones;
	}

	public List<SelectItem> getListOperadores() {
		return listOperadores;
	}

	public void setListOperadores(List<SelectItem> listOperadores) {
		this.listOperadores = listOperadores;
	}

	public String getValidacion() {
		return validacion;
	}

	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(String txtValor) {
		this.txtValor = txtValor;
	}

	public List<SelectItem> getListDistribucion() {
		return listDistribucion;
	}

	public void setListDistribucion(List<SelectItem> listDistribucion) {
		this.listDistribucion = listDistribucion;
	}

	public String getValorLista() {
		return valorLista;
	}

	public void setValorLista(String valorLista) {
		this.valorLista = valorLista;
	}

	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}

	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}
	
	public boolean isValidacionHecha() {
		return validacionHecha;
	}	
	
	public void setValidacionHecha(boolean validacionHecha) {
		this.validacionHecha = validacionHecha;
	}

	/**
	 * @return the validaDialogoForm
	 */
	public ValidarCamposEventosCorporativosImp getValidaDialogoForm() {
		return validaDialogoForm;
	}

	/**
	 * @param validaDialogoForm the validaDialogoForm to set
	 */
	public void setValidaDialogoForm(
			ValidarCamposEventosCorporativosImp validaDialogoForm) {
		this.validaDialogoForm = validaDialogoForm;
	}
}
