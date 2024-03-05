/*
 *Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Controller de las pantalla de consulta de Cuentas
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class ConsultaCuentaController extends ControllerBase {
	
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade;
	
	/** DTO para pasar los datos de la pantalla */
	private CriterioCuentaEfectivoDTO consulta = new CriterioCuentaEfectivoDTO();
	
	/** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;
    
    /** Consulta ejecutada */
	private boolean consultaEjecutada = false;
	
	/** Lista con los resultados de la consulta */
	private List<CuentaRetiroEfectivoDTO> resultados = null;
	
	/** Total de registros encontrados en la consulta */
	private int totalRegistros = 0;
	
	/** Conjunto de isos Firmados */
	private List<String> isosFirmados = null;
	
	/** Conjunto de isos sin firmar */
	private List<String> isosSinFirmar = null;
	
	/** Conjunto de hash de isos */
	private List<String> hashIso = null;
	
	/** Total de operaciones a firmar */
	private int totalOperaciones = 0;
	
	/** Accion realizada */
	private String accionRealizada = "NDF";
	
	/** define si se trata de cuentas nacionales(false) o internacionales(true), default=internacionales */
    private boolean sonCuentasNacional;
    
    /** indica si las modificaciones aplican o no */
    private boolean aplicaModificaciones;
    
    /** horario de modificacion */
    private String horarioModificacion;
    
    /** cambio de estado exitoso */
    private boolean cambioEdosGlobalOk;

	/** Bandera que indica si se debe ejecutar el init o no */
    private boolean bloqueaInicio;
    
	
	
	/**
	 * Inicializa los datos de la pagina.
	 * 
	 * @return <code>null</code>.
	 */
	public String getInit() {
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		InstitucionDTO inst = new InstitucionDTO();
		if (consulta.getIdFolioUsuario() == null) {
			consulta.setIdFolioUsuario(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		}
		if (consulta.getIdFolioUsuario() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(consulta.getIdFolioUsuario());
			if (inst != null) {
				consulta.setInstitucion(inst);
			}
		}
		
		consulta.setFechaCreacion(new Date());
		
		Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String sonCuentasNacional2=map.get("sonCuentasNacional");
        String idDivisa=map.get("idDivisa");
		DivisaDTO divisa = idDivisa==null?null:consultaCatalogosFacade.buscarDivisaPorId(Long.parseLong(idDivisa));
		consulta.setValorEn(divisa);
        
        sonCuentasNacional = Boolean.parseBoolean(StringUtils.isEmpty(sonCuentasNacional2)?"false":sonCuentasNacional2);

        //se colocan temporalmente estos valores para que quede por default pesos mexicanos hasta que se libere internacional
        sonCuentasNacional = true;
        consulta.setValorEn(MXP);
        
		return null;
	}
	
	/**
	 * Genera la lista de estados para el combo
	 * 
	 * @return lista de estados 
	 */
	public List<SelectItem> getEstados(){
	
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();
		opcionesSelect.add(new SelectItem(""+DaliConstants.VALOR_COMBO_TODOS,"Todos"));
		
		for (EstadosCuentaDTO estado: admonCuentasService.getEstadosCuenta()) {
			if(sonCuentasNacional&&estado.getId()!=26){ //aprobada no aplica para nacional
				opcionesSelect.add(new SelectItem(""+estado.getId(), estado.getDescripcion()));
			}
		}	

		return opcionesSelect;
	}
	
	/**
	 * Define si debe hablitarse la seccion de intermediario o no
	 * @param el evento 
	 */
	public void limpiar(ActionEvent event) {

		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		consulta = new CriterioCuentaEfectivoDTO();
		resultados = null;
		consultaEjecutada=false;
		
		isosSinFirmar=new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		
		getInit();
	}
	
	/**
	 * Busca las cuentas a mostrar segun los criterios seleccionados
	 */
	public void buscarCuentas(ActionEvent event){
		logger.debug("en buscar cuentas...");

		consultaEjecutada = true;
		DivisaDTO divisa = consultaCatalogosFacade.buscarDivisaPorId(consulta.getValorEn().getId());
		consulta.setValorEn(divisa);
		consulta.setNombreBeneficiario(StringUtils.upperCase(consulta.getNombreBeneficiario()));	
		
		EstadosCuentaDTO estado = admonCuentasService.getEstadoPorId(Long.parseLong(consulta.getEstadoCuenta()));
		consulta.setEstadoCuentaDto(estado);
		
		logger.debug("consulta.getIdFolioUsuario():"+consulta.getIdFolioUsuario());
		
		incializarEstadoPaginacion(admonCuentasService.obtenerProyeccionCuentas(consulta, true, false));
		
		resultados = admonCuentasService.buscarCuentas(consulta, paginacion, true, false);
			
		
//		resultados = sonCuentasNacional?
//				 		admonCuentasService.buscarCuentas(consulta, paginacion, true, false)
//						:admonCuentasService.buscarCuentas(consulta, paginacion, false, false);
				 		
				 		
		/*
		Map<String, Object> datosModificar = admonCuentasService.revisarModificacionesHabilitadas();
		horarioModificacion = (String)datosModificar.get("mensaje");
		//se comenta este codigo hasta que entre la modificacion de cuentas liberadas y aplique el horario.
		//por ahora, la bandera estara siempre en true.
		//aplicaModificaciones = ((Boolean)datosModificar.get("aplicaModificaciones")).booleanValue();
		 * 
		 */
		aplicaModificaciones = true; 

		totalRegistros = resultados.size();

	}
	
	/**
	 * autorizar Cuentas 
	 */
	public String autorizarCuentas(){	
		logger.debug("en autorizar cuentas nuevo**...");
		accionRealizada = "AU";
		
		cambiarEstadoCuenta("daliForm:cuentasIdActualizar");
		return null;
	}

	
	/**
	 * Liberar cuentas 
	 */
	public String liberarCuentas(){	
		logger.debug("en Liberar cuentas...");
		accionRealizada = "LA";
		cambiarEstadoCuenta("daliForm:cuentasIdLiberar");		
		return null;
	}
	
	
	/**
	 * aprobar cuentas 
	 */
	public String aprobarCuentas(ActionEvent event){
		logger.debug("en aprobar cuentas...");
		accionRealizada = "AP";
		cambiarEstadoCuenta("daliForm:cuentasIdAprobar");
		return null;
	}
	
	/**
	 * cancelar cuentas 
	 */
	public String cancelarCuentas(){		
		logger.debug("en cancelar cuentas...");
		accionRealizada = "CA";
		cambiarEstadoCuenta("daliForm:cuentasIdCancelar");
		if(!cambioEdosGlobalOk){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Al menos una de las cuentas seleccionadas no ha sido cancelada. Tiene movimientos pendientes.", "Al menos una de las cuentas seleccionadas no ha sido cancelada. Tiene movimientos pendientes."));
		}
		return null;
	}
	
	/**
	 * Busca el id de la cuenta en la lista de resultados
	 * @param id cuenta retiro a buscar 
	 */
	private CuentaRetiroEfectivoDTO buscarIdCuentaRetiro(String id){		
		return admonCuentasService.getCuentaNacionalByCuentaRetiroNoEnMOS(Long.parseLong(id));		
	}
	
	/**
	 * Cambiar estado de cuenta, tomando en cuenta la firma.
	 * @param  
	 */
	private void cambiarEstadoCuenta(String idObjetoRecuperar){
		//Se agrega validación extra para saber si tiene la facultad CON_FIRMA
		//03-Febrero-2015 Pablo Balderas
		if(isUsuarioConFacultadFirmar()) {
			//Valida la vigencia del certificado
			//18-Agosto-2014 Pablo Balderas
			try {				
				validarVigenciaCertificado();
				Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				String idCuentas=map.get(idObjetoRecuperar);
				//vereifica que los seleccionados esten en el estado diferente al nuevo estado, sino se saca de los seleccionados 
				idCuentas = !StringUtils.isEmpty(idCuentas)? verificarSeleccionados(idCuentas):idCuentas;
				if(StringUtils.isEmpty(idCuentas)){
		    		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Es necesario seleccionar al menos un registro", "Es necessario seleccionar al menos un registro"));
		    		 mensajeUsuario = "Es necesario seleccionar al menos un registro";
		 			 isosSinFirmar=new ArrayList<String>();
					 isosFirmados = new ArrayList<String>();
					 hashIso = new ArrayList<String>();
					 cambioEdosGlobalOk = true;
					 //refrescar para que las banderas que se hayan puesto y quitado se eliminen de los objetos
				}
				else{
					String numeroSerie = map.get("numeroSerie");
					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		    		UsuarioDTO usuario= session!=null?(UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION):null;
					isosSinFirmar=new ArrayList<String>();
					isosFirmados = new ArrayList<String>();
					hashIso = new ArrayList<String>();
					cambioEdosGlobalOk = true;
					Date fecha = new Date();
					StringTokenizer tokenizer = new StringTokenizer(idCuentas, ",");
					int numSelec = tokenizer.countTokens();
					int var=1;
					while(tokenizer.hasMoreElements()){
						String id=tokenizer.nextToken();
						if (StringUtils.isNotEmpty(numeroSerie)){ //viene la firma
							String isoSinFirmar=map.get("isoSinFirmar"+var);
							String firma =map.get("isoFirmado"+var);
							StringBuilder isoCompleto= 
								new StringBuilder(isoSinFirmar).append("\n")
									.append(numeroSerie).append("\n")
									.append("{SHA1withRSA}")
									.append(firma);
							var++;
							Map<String, Object> datosFirma = new HashMap<String, Object>(0);
							datosFirma.put("firma", isoCompleto.toString());
							datosFirma.put("fecha", fecha);
							datosFirma.put("usuario", usuario.getClaveUsuario());
							datosFirma.put("serie", numeroSerie);
							//con un error queda la bandera en false
							cambioEdosGlobalOk = admonCuentasService.cambiarEstadoCuenta(Long.parseLong(id), accionRealizada, datosFirma);
							numSelec = 0;
							//no era... quitar supongo... tengo que actualizar el che campo de ids, pero como?
				 			isosSinFirmar=new ArrayList<String>();
							isosFirmados = new ArrayList<String>();
							hashIso = new ArrayList<String>();
							//se refreseca la consulta
							resultados = sonCuentasNacional?
							 		admonCuentasService.buscarCuentas(consulta, paginacion, true, false)
									:admonCuentasService.buscarCuentas(consulta, paginacion, false, false);
						}
						else{//generando el iso
							isosSinFirmar.add(armaDatosFirma(id));					
						}
					}
					totalOperaciones =isosSinFirmar.size();			
				}
			}
			catch(BusinessException businessException) {
	    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
	    		cambioEdosGlobalOk = true;
	    	}
		}
		else {
			addMessageFromProperties("msgErrorSinCertificado", FacesMessage.SEVERITY_ERROR);
			cambioEdosGlobalOk = true;
		}
	}
	
	/**
	 * Verifica si el id debe o no estar en los seleccionados
	 */
	public String verificarSeleccionados(String idCuentas){
		StringTokenizer tokenizer = new StringTokenizer(idCuentas, ",");
		while(tokenizer.hasMoreElements()){
			String id=tokenizer.nextToken();
			CuentaRetiroEfectivoDTO cuentadto = buscarIdCuentaRetiro(id);
			if((accionRealizada.equals("AU") && !cuentadto.getEstadoActual().equals("RE"))
					||(accionRealizada.equals("LA") && !cuentadto.getEstadoActual().equals("AU"))
					||(accionRealizada.equals("CA") && cuentadto.getEstadoActual().equals("CA")
									)){
				idCuentas = idCuentas.replace(","+id, "");
			}
		}
		return idCuentas;
	}
	
	
	/**
	 * Invoca las funciones de navegacion de pagina de la consulta principal de
	 * la pantalla
	 * @param e Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");
		
		try {
			paginacion.getClass().getMethod(navegacion).invoke(paginacion);
			consultaEjecutada = true;
			resultados = sonCuentasNacional?
			 		admonCuentasService.buscarCuentas(consulta, paginacion, true, false)
					:admonCuentasService.buscarCuentas(consulta, paginacion, false, false);
			totalRegistros = resultados.size();
		} catch (Exception ex) {
			logger.error("Error de invocacion de metodo al navegar por los resultados principales", ex);
		}
	}
	
	/**
	 * Genera las consultas para llenar los reportes de la pantalla
	 * @param e
	 */
	public void generarReportes(ActionEvent e) {
		resultados = sonCuentasNacional?
		 		admonCuentasService.buscarCuentas(consulta, null, true, false)
				:admonCuentasService.buscarCuentas(consulta, null, false, false);
		totalRegistros = resultados.size();
	}
	
	/**
	 * Busca la institucion apartir del ID-Folio
	 * 
	 * @param event
	 */
	public void buscarInstitucionParticipante(ActionEvent event) {
		logger.debug("set 2 - (new)");
		consulta.setInstitucion(new InstitucionDTO());
		if(consulta.getIdFolioUsuario() != null) {
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(consulta.getIdFolioUsuario());
			if(i !=  null) {
				logger.debug("set 3 - (idFolioUsuario != null)");
				consulta.setInstitucion(i);
			}
		}
	}
	
	/**
	 * Busca una lista de participantes cuyo id-folio comience con la cadena de
	 * criterio recibido
	 * 
	 * @param prefijo
	 *            Cadena de criterio id-folio
	 * @return Lista de instituciones recuperadas
	 */
	public List<InstitucionDTO> buscarParticipante(Object prefijo) {
		List<InstitucionDTO> instituciones  = new ArrayList<InstitucionDTO>();
		if (prefijo != null) {
			String prefijoAjustado = String.valueOf(prefijo).trim().replace("*", "%");
			instituciones = consultaCatalogosFacade.getConsultaInstitucionService()
				.buscarInstitucionesPorPrefijo(prefijoAjustado);
		}
		return instituciones;
	}
	
	/**
	 * Arma los datos completos para la firma electronica
	 * @param idCuenta id de la cuenta a firmar
	 * @return iso armado
	 */
	private String armaDatosFirma(String idCuenta){
		StringBuffer datosFirma = new StringBuffer("");
		
		CuentaRetiroEfectivoDTO cuentadto = buscarIdCuentaRetiro(idCuenta);
		if(accionRealizada.equals("AU")){
			datosFirma.append(sonCuentasNacional?
								"\nAUTORIZAR CUENTA DE FOLIO:"+cuentadto.getIdCuentaPorInstitucion()
								:"\nAUTORIZAR CUENTA DE NOMBRE:'"+cuentadto.getNombreCorto()+"'");
		}else if(accionRealizada.equals("LA")){
			datosFirma.append(sonCuentasNacional?
								"\nLIBERAR CUENTA DE FOLIO:"+cuentadto.getIdCuentaPorInstitucion()
								:"\nLIBERAR CUENTA DE NOMBRE:'"+cuentadto.getNombreCorto()+"'");
		}else if(accionRealizada.equals("AP")){
			datosFirma.append(sonCuentasNacional?
								"\nAPROBAR CUENTA DE FOLIO:"+cuentadto.getIdCuentaPorInstitucion()
								:"\nAPROBAR CUENTA DE NOMBRE:'"+cuentadto.getNombreCorto()+"'");
		}else if(accionRealizada.equals("CA")){
			datosFirma.append(sonCuentasNacional?
								"\nCANCELAR CUENTA DE FOLIO:"+cuentadto.getIdCuentaPorInstitucion()
								:"\nCANCELAR CUENTA DE NOMBRE:'"+cuentadto.getNombreCorto()+"'");
		}
		
		datosFirma.append("\nTRASPASANTE: ")
			.append(cuentadto.getInstitucion().getClaveTipoInstitucion() + cuentadto.getInstitucion().getFolioInstitucion())
			.append(" ")
			.append(cuentadto.getInstitucion().getNombreCorto());
		datosFirma.append("\nRECEPTOR: ")
			.append(cuentadto.getInstitucionBeneficiario().getClaveTipoInstitucion()+ cuentadto.getInstitucionBeneficiario().getFolioInstitucion())
			.append(" ")
			.append(cuentadto.getInstitucionBeneficiario().getNombreCorto());;
		datosFirma.append("\nCUENTA BENEFICIARIO: ").append(cuentadto.getCuentaBeneficiario());
		datosFirma.append("\nNOMBRE BENEFICIARIO: ").append(cuentadto.getNombreBeneficiario());
		datosFirma.append("\nMONTO MAXIMO MENSUAL: ").append(cuentadto.getMontoMaximoMensual()==null?"SIN LIMITE":cuentadto.getMontoMaximoMensual());
		datosFirma.append("\nMONTO MAXIMO DIARIO: ").append(cuentadto.getMontoMaximoDiario()==null?"SIN LIMITE":cuentadto.getMontoMaximoDiario());
		datosFirma.append("\nMONTO MAXIMO TRANSACCION: ").append(cuentadto.getMontoMaximoXTran()==null?"SIN LIMITE":cuentadto.getMontoMaximoXTran());
		datosFirma.append("\nMOVIMIENTOS MAXIMOS POR MES: ").append(cuentadto.getNumMaximoMovsXMes()==null?"SIN LIMITE":cuentadto.getNumMaximoMovsXMes());
		datosFirma.append("\n");
		return datosFirma.toString();
	}

	public String getResetBloqueaInicio() {
		bloqueaInicio = false;
		return null;
	}
	
	public AdministracionCuentasRetiroService getAdmonCuentasService() {
		return admonCuentasService;
	}

	public void setAdmonCuentasService(
			AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	public CriterioCuentaEfectivoDTO getConsulta() {
		return consulta;
	}

	public void setConsulta(CriterioCuentaEfectivoDTO consulta) {
		this.consulta = consulta;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	@SuppressWarnings("unchecked")
	public List getResultados() {
		return resultados;
	}

	@SuppressWarnings("unchecked")
	public void setResultados(List resultados) {
		this.resultados = resultados;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}

	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	public String getAccionRealizada() {
		return accionRealizada;
	}

	public void setAccionRealizada(String accionRealizada) {
		this.accionRealizada = accionRealizada;
	}

	public boolean isAplicaModificaciones() {
		return aplicaModificaciones;
	}

	public void setAplicaModificaciones(boolean aplicaModificaciones) {
		this.aplicaModificaciones = aplicaModificaciones;
	}

	public String getHorarioModificacion() {
		return horarioModificacion;
	}

	public void setHorarioModificacion(String horarioModificacion) {
		this.horarioModificacion = horarioModificacion;
	}

	public boolean isCambioEdosGlobalOk() {
		return cambioEdosGlobalOk;
	}

	public void setCambioEdosGlobalOk(boolean cambioEdosGlobalOk) {
		this.cambioEdosGlobalOk = cambioEdosGlobalOk;
	}

	public boolean isBloqueaInicio() {
		return bloqueaInicio;
	}

	public void setBloqueaInicio(boolean bloqueaInicio) {
		this.bloqueaInicio = bloqueaInicio;
	}
}