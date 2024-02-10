/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.BitacoraEventosCorporativosUtil;
import com.indeval.portalinternacional.middleware.services.util.DTOAssembleEvCorp;
import com.indeval.portalinternacional.middleware.services.util.XmlUtils;
import com.indeval.portalinternacional.middleware.services.validador.ValidarCamposEventosCorporativosImp;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Guardable;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraXMLEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoAltaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEvCoMensaje;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesXml;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;
import com.thoughtworks.xstream.XStream;

/**
 * @author eperez
 *
 */
public class SaveEventosCorporativosServiceImpl implements
		SaveEventosCorporativosService {
	@SuppressWarnings("unused")
	private MessageResolver errorResolver;
	private ConsultaEventosCorporativosDao consultaEventosCorporativosDao;
	private DTOAssembleEvCorp dtoAssemblerEvCorp;
	private BitacoraEventosCorporativosUtil bitacoraUtilerias;
	private ValidarCamposEventosCorporativosImp validadorEvco;
	private JmsTemplate jmsTemplateMoiNotificacionesEvCo;
	

	private static final Logger log = LoggerFactory.getLogger(SaveEventosCorporativosServiceImpl.class);


    public Long saveEventoCorporativo(EventoCorporativoAltaDTO params)
            throws BusinessException {
    	
        EventoCorporativo eventoCorporativo = null;
        CuerpoEventoCorporativo cuerpoEventoCorporativo = null;
        Long idEventoCorporativo = null;
               
    	List<NotasEventoCorporativo> notasList			= new ArrayList<NotasEventoCorporativo>();
    	List<OpcionesEventoCorporativo> opcionesList	= new ArrayList<OpcionesEventoCorporativo>();
    	List<AdjuntosEventoCorporativo> adjuntosList	= new ArrayList<AdjuntosEventoCorporativo>();
    	List<NotificacionEventoCorporativo> notificacionList	= new ArrayList<NotificacionEventoCorporativo>();
    	List<ValidacionesEvco> validacionesList = new ArrayList<ValidacionesEvco>();
    	try{
    	

	        eventoCorporativo = dtoAssemblerEvCorp.setEventoCorporativo(params);       
    		validadorEvco.validaCamposGenerales(eventoCorporativo);
	        consultaEventosCorporativosDao.save(eventoCorporativo);
	        log.debug("ID EVENTO CORPORATIVO ================  "+eventoCorporativo.getIdEventoCorporativo());
	        idEventoCorporativo = eventoCorporativo.getIdEventoCorporativo();
	        
	        //Cuerpo
	        cuerpoEventoCorporativo = dtoAssemblerEvCorp.setCuerpoEventoCorporativo(params, idEventoCorporativo);
	        validadorEvco.validaCuerpoEvco(cuerpoEventoCorporativo);
	        
	        
    		validadorEvco.validaTamanioListaOpcionesEvco(eventoCorporativo, params.getMapOpciones());
    		
	        if(params.getMapOpciones() != null &&  params.getMapOpciones().size() > 0){
	        	validadorEvco.validaOpcionesDefault(new ArrayList<OpcionesDTO>(params.getMapOpciones().values())); 
		        for(int i = 0; i < params.getMapOpciones().size(); i++){
		        	OpcionesEventoCorporativo opcionesEventoCorporativo = dtoAssemblerEvCorp.setOpcionesEventoCorporativo(params.getMapOpciones().get("opc_"+i), idEventoCorporativo);
		        	opcionesEventoCorporativo.setNumeroOpcion(i);
		        	//consultaEventosCorporativosDao.save(opcionesEventoCorporativo);
		        	opcionesList.add(opcionesEventoCorporativo);
		        }
	        }
	        if(params.getMapNotas()!= null && params.getMapNotas().size() > 0){
		        for(String llave : params.getMapNotas().keySet()){
		        	NotasEventoCorporativo notasEventoCorporativo = dtoAssemblerEvCorp.setNotasEventoCorporativo(params.getMapNotas().get(llave), idEventoCorporativo);
		        	//consultaEventosCorporativosDao.save(notasEventoCorporativo);		        	
		        	notasList.add(notasEventoCorporativo);
		        }
	        }
	        if(params.getMapAdjuntos()!= null  && params.getMapAdjuntos().size() > 0){
		        for(String llave : params.getMapAdjuntos().keySet()){
		        	AdjuntosEventoCorporativo adjuntosEventoCorporativo = dtoAssemblerEvCorp.setAdjuntosEventoCorporativo(params.getMapAdjuntos().get(llave), idEventoCorporativo);
		        	//consultaEventosCorporativosDao.save(adjuntosEventoCorporativo);
		        	adjuntosList.add(adjuntosEventoCorporativo);
		        }
	        }  
	        
	        if(params.getMapNotificaciones()!= null  && params.getMapNotificaciones().size() > 0){
		        for(String llave : params.getMapNotificaciones().keySet()){
		        	NotificacionEventoCorporativo notificacionEventoCorporativo = dtoAssemblerEvCorp.setNotificacionesEventoCorporativo(params.getMapNotificaciones().get(llave), idEventoCorporativo);
		        	notificacionEventoCorporativo.setOperacionARealizar(Constantes.EVCO_AGREGAR_ACCION);
		        	//consultaEventosCorporativosDao.save(notificacionEventoCorporativo);
		        	notificacionList.add(notificacionEventoCorporativo);
		        }		        
	       }
	        
	        
	        if(params.getMapValidaciones() != null && params.getMapValidaciones().size() > 0){
		        for(String llave : params.getMapValidaciones().keySet()){
		        	ValidacionesEvco validacionesEvco = dtoAssemblerEvCorp.setValidacionesEventoCorporativo(params.getMapValidaciones().get(llave), idEventoCorporativo);
		        	//consultaEventosCorporativosDao.save(validacionesEvco);
		        	validacionesList.add(validacionesEvco);
		        }
	        }
	        
	      //Salvar Evento
	        consultaEventosCorporativosDao.save(cuerpoEventoCorporativo);
	        guardarLista(opcionesList);
	    	guardarLista(notasList);
	    	guardarLista(validacionesList);
	    	guardarLista(notificacionList);
	    	guardarLista(adjuntosList); 
	    	
	        String usuario=params.getUsuarioAlta();
	        Long estado= eventoCorporativo.getEstado().getIdEstado();
	        BitacoraXMLEvco bitacoraXmlEvco  =bitacoraUtilerias.crearBitacoraEvco(eventoCorporativo,cuerpoEventoCorporativo,notasList,opcionesList,adjuntosList);
	        bitacoraUtilerias.insertarBitacora( bitacoraXmlEvco, usuario,  estado , false);
	       	       
	        consultaEventosCorporativosDao.flush();
	        
	        //manda notificaciones al moi
	        enviaMensajeNotificaciones(notificacionList, idEventoCorporativo);	        
	        
	        return idEventoCorporativo;
	        
    	}catch(ParseException pe){
    		throw new BusinessException("fecha Invalida",pe);
    	}
    }
    

    private void enviaMensajeNotificaciones(List<NotificacionEventoCorporativo> notificacionList,Long idEventoCorporativo) {
		log.debug("enviar notificacion evco");
		//Obten los xml
		if(notificacionList != null && notificacionList.size() > 0 && idEventoCorporativo != null){
			NotificacionesXml notifxml = new NotificacionesXml();
			notifxml.setIdEventoCorporativo(idEventoCorporativo);
			List<NotificacionEvCoMensaje> listnotif = new ArrayList<NotificacionEvCoMensaje>();
			
			for(NotificacionEventoCorporativo msg :notificacionList){
				NotificacionEvCoMensaje notifmsg = new NotificacionEvCoMensaje();
				notifmsg.setAccion(msg.getOperacionARealizar());
				notifmsg.setCronexpression(msg.getCronexpresion());
				notifmsg.setFechaFin(msg.getFechaFin());
				notifmsg.setFechaInicio(msg.getFechaInicio());
				notifmsg.setIdNotificacionEvCo(msg.getIdNotificacion());
				//verificar el objeto de la lista de distribucion
				notifmsg.setLista(msg.getListaDistribucion().getIdLista().toString());
				notifmsg.setMensaje(msg.getMensaje());
				//notifmsg.setNumNotificacion(msg.getNumNotificacion());
				
				listnotif.add(notifmsg);
			}
			if(listnotif != null && listnotif.size() > 0){
				notifxml.setNotificaciones(listnotif);
				XStream xstream = new XStream();
				XmlUtils.setAtributosXStream(xstream, NotificacionesXml.class, NotificacionEvCoMensaje.class);	
				final String mensaje = xstream.toXML(notifxml);
				//log.debug("xml------>>> +\n"+mensaje);
				//enviar
				this.jmsTemplateMoiNotificacionesEvCo.send(new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						log.info("=======================>   MENSAJE A Notificacion EvCo MOI:\n");
						Message msg = session.createTextMessage(mensaje);
						log.debug(msg.toString());
						return msg;
					}
				});
			}
		}
	}

    /**
     * Modificacion de Evento corporativo<br/>
     * METODO PRINCIPAL QUE CONTIENE EL ALGORITMO GENERAL DE MODIFICACION
     * @param params
     * @throws BusinessException
     */
    public Long updateEventoCorporativo(EventoCorporativoEdicionDTO params) throws BusinessException {
    	Long idevco=null;    
    	try{
    		idevco=Long.valueOf(params.getIdEventoCorporativo());
    	}catch(Exception ex){
    		throw new BusinessException("El folio del evento corporativo no es numerico, contacte a soporte folio: "+params.getIdEventoCorporativo());
    	}
    	//valida DTO
    	
    	//modifica Evento datos generales
    	EventoCorporativo evco = (EventoCorporativo)consultaEventosCorporativosDao.getByPk(EventoCorporativo.class,idevco);    	 
    	if(evco == null){
    		throw new BusinessException("El folio del evento corporativo no existe");
    	}
    	dtoAssemblerEvCorp.updateEventoCorporativo(params,evco);
    	validadorEvco.validaCamposGenerales(evco);
    	//modifica cuerpo
    	CuerpoEventoCorporativo cuerpo = (CuerpoEventoCorporativo)consultaEventosCorporativosDao.getCuerpoEventoCorporativo(idevco);
    	cuerpo.setCuerpo(params.getCuerpoEventoHtml());    	
    	cuerpo.setPiePagina(params.getPiePaginaHtml());
    	validadorEvco.validaCuerpoEvco(cuerpo);
    	//modifica opciones
    	List<OpcionesEventoCorporativo> opcionesList = null;
    	
    	if(params.getListOpciones() != null){
    		validadorEvco.validaTamanioListaOpcionesEvco(evco, params.getListOpciones());
    		if(Constantes.TIPO_EVCORP_OPCIONAL.equals( evco.getTipoEvento().getCodigo())){
    			validadorEvco.validaOpcionesDefault(params.getListOpciones());    			
    		}
    		opcionesList = modificaOpciones(params.getListOpciones(), evco);
    	}
    	
    	//modifica Notas
    	List<NotasEventoCorporativo> notasList = null;
    	if(params.getListNotas() != null){
    		notasList = modificaNotas(params.getListNotas(),evco);
    	}
    	
    	//modifica Notificaciones
    	List<NotificacionEventoCorporativo> notificacionList = null;
	    try{
	    	if(params.getListNotificaciones() != null){
	    		notificacionList = modificaNotificaciones(params.getListNotificaciones(), evco);
	    	}
    	}catch(ParseException pex){
    		throw new BusinessException("Error al ingresar una fecha en una notificación, revise las fechas inicio y fin");
    	}
	    
    	//modifica Validaciones
    	List<ValidacionesEvco> validacionesList = null;
    	if(params.getListValidaciones() != null){    		
    		validacionesList = modificaValidaciones(params.getListValidaciones(),evco);
    	}
    	//modifica archivos adjuntos
    	List<AdjuntosEventoCorporativo> adjuntosList = null;
    	if(params.getListaAdjuntos() != null){
    		adjuntosList = modificaAdjuntos(params.getListaAdjuntos(),evco);
    	}
    	validadorEvco.validaAdjuntos(adjuntosList);
    	//------------------GUARDAR---------------------------
    	consultaEventosCorporativosDao.save(evco);
    	consultaEventosCorporativosDao.save(cuerpo);    	
    	guardarLista(opcionesList);
    	guardarLista(notasList);
    	guardarLista(validacionesList);
    	guardarLista(notificacionList);
    	guardarListaSelectiva(adjuntosList);    	
    	consultaEventosCorporativosDao.flush();
    	
    	//------------------FIN GUARDAR-----------------------
    	//----------------Bitacora---------------------------
    	String usuario=params.getUsuario();
        Long estado= evco.getEstado().getIdEstado();
        BitacoraXMLEvco bitacoraXmlEvco  =bitacoraUtilerias.crearBitacoraEvco(evco,cuerpo,notasList,opcionesList,adjuntosList);
        bitacoraUtilerias.insertarBitacora( bitacoraXmlEvco, usuario,  estado, true );
    	//Notificaciones al MOI
        enviaMensajeNotificaciones(notificacionList, evco.getIdEventoCorporativo());
        
        return evco.getIdEventoCorporativo();
    }
    
	private List<NotificacionEventoCorporativo> modificaNotificaciones (
			List<NotificacionesEvcoDTO> listaNotificaciones,
			EventoCorporativo evco) throws ParseException,BusinessException {
		
		List<NotificacionEventoCorporativo> lista = null;
		if(listaNotificaciones != null && listaNotificaciones.size() > 0 ){
			lista = new ArrayList<NotificacionEventoCorporativo>();
		}else{
			return null;
		}
		/*Notificaciones
		 * 1)IdNotificacion != null && isBorradoLogico
		 * 		Borrar
		 * 2)IdNotificacion != null && !isBorradoLogico
		 * 		Modificar
		 * 3)IdNotificacion == null && !isBorradoLogico
		 * 		Agregar
		 */
		
		for(NotificacionesEvcoDTO vo : listaNotificaciones){
			if(StringUtils.isNotBlank(vo.getIdNotificacion()) && StringUtils.isNumeric(vo.getIdNotificacion())  && vo.isBorradoLogico()){
				//caso1
				NotificacionEventoCorporativo notif = (NotificacionEventoCorporativo)consultaEventosCorporativosDao.getByPk(NotificacionEventoCorporativo.class, Long.valueOf(vo.getIdNotificacion()));
				notif.setOperacionARealizar(Constantes.EVCO_BORRAR_ACCION);	
				notif.setVigente(0l);
				lista.add(notif);
			}else if(StringUtils.isNotBlank(vo.getIdNotificacion()) && StringUtils.isNumeric(vo.getIdNotificacion()) && !vo.isBorradoLogico()){
				//caso 2
				NotificacionEventoCorporativo notif = (NotificacionEventoCorporativo)consultaEventosCorporativosDao.getByPk(NotificacionEventoCorporativo.class, Long.valueOf(vo.getIdNotificacion()));
				if(comparaNotificaciones(notif,vo)){ //si la notificacion ya paso no se toma en cuenta, solo si hubo un cambio.
					dtoAssemblerEvCorp.updateNotificacion(notif,vo);
					notif.setOperacionARealizar(Constantes.EVCO_MODIFICAR_ACCION);				
					lista.add(notif);
				}
			}else if((vo.getIdNotificacion() == null || StringUtils.isBlank(vo.getIdNotificacion())) && !vo.isBorradoLogico()){
				//caso3				
				NotificacionEventoCorporativo notif = null;				
					notif = dtoAssemblerEvCorp.setNotificacionesEventoCorporativo(vo, evco.getIdEventoCorporativo());
					notif.setOperacionARealizar(Constantes.EVCO_AGREGAR_ACCION);
					lista.add(notif);				
			}
		}
		return lista;
	}


	private boolean comparaNotificaciones(NotificacionEventoCorporativo notif,
			NotificacionesEvcoDTO vo) throws BusinessException {
		Long now = Calendar.getInstance().getTimeInMillis();
		try {
			//la notificacion ya paso, no la tomes en cuenta
			if(vo.getFechaFin() != null && vo.getFechaFin().getTime() < now){
				return false;
			}
			/*hay cambio
			 * con cualquier dato que cambie que lo tome en cuenta(true)
			 * cronexpression
			 * fecha inicio
			 * fecha fin
			 * texto
			 * lista distribucion
			 * 
			 */
			if(notif.getCronexpresion().equals(vo.getCronExpression()) &&
					notif.getMensaje().equals(vo.getStrTextoNotificacion()) &&
					notif.getFechaInicio().getTime()==vo.getFechaInicio().getTime() &&
					notif.getFechaFin().getTime()==vo.getFechaFin().getTime() &&
					notif.getListaDistribucion().getIdLista().equals(Long.valueOf(vo.getIdListaDIstribucion() ))
					){//todos son iguales, entonces no hay cambio
				return false;
			}else{
				return true;
			}
			
			
		} catch (ParseException e) {
			throw new BusinessException("La fecha fin de la Notificacion es invalida");
		}
	}
//	private boolean equalDatesNotificacion(Date dateVo, Date dateNotif){
//		
//	}
	
	private List<ValidacionesEvco> modificaValidaciones(
			List<ValidacionesEvcoDTO> listValidaciones, EventoCorporativo evco) {    	
    	
    	List<ValidacionesEvco> validacionesList = null;
    	if(listValidaciones != null && listValidaciones.size() > 0){
    		validacionesList = new ArrayList<ValidacionesEvco>();
    	}else{
    		return null;
    	}
    	/*Validaciones Modificacion
    	 * 1)IdValidacion != null && isBorradoLogico
    	 * 		borra
    	 * 2)IdValidacion == null && !isBorradoLogico
    	 * 		agrega
    	 */
    	for(ValidacionesEvcoDTO  valdto : listValidaciones){
    		if(valdto.getIdValidacion() != null && valdto.isBorradoLogico()){
    			//caso1
    			ValidacionesEvco val = (ValidacionesEvco)consultaEventosCorporativosDao.getByPk(ValidacionesEvco.class, valdto.getIdValidacion());
    			if(val!=null){
    				consultaEventosCorporativosDao.delete(val);
    			}
    		}else if(valdto.getIdValidacion() == null && !valdto.isBorradoLogico()){
    			//caso2
    			ValidacionesEvco validacionesEvco = dtoAssemblerEvCorp.setValidacionesEventoCorporativo(valdto, evco.getIdEventoCorporativo());
    			validacionesList.add(validacionesEvco);
    		}
    	}
		return validacionesList;
	}


	private List<AdjuntosEventoCorporativo> modificaAdjuntos(
			List<ArchivosAdjuntosEvcoDTO> listAdjuntos, EventoCorporativo evco) {
    	List<AdjuntosEventoCorporativo> adjuntosList =null;
    	if(listAdjuntos != null && listAdjuntos.size() > 0){
    		adjuntosList = new ArrayList<AdjuntosEventoCorporativo>();
    	}else{
    		return null;
    	}
    	/*Modifica Adjuntos
    	 * 1)IdAdjuntos != null && isBorradoLogico
    	 * 		Borrar adjunto
    	 * 
    	 * 2)IdAdjuntos == null && !isBorradoLogico
    	 * 		Agregar Adjunto
    	 */
    	for(ArchivosAdjuntosEvcoDTO adjuntoDto : listAdjuntos){
    		if(adjuntoDto.getIdAdjuntos() != null && adjuntoDto.isBorradoLogico()){
    			//caso1
    			//consultaEventosCorporativosDao.deleteAdjuntoLogico(adjuntoDto.getIdAdjuntos());
//    			AdjuntosEventoCorporativo adj = (AdjuntosEventoCorporativo) consultaEventosCorporativosDao.getByPk(AdjuntosEventoCorporativo.class, adjuntoDto.getIdAdjuntos());
//    			if(adj != null){
//    				consultaEventosCorporativosDao.delete(adj);
//    			}
    			deleteAdjunto(adjuntoDto.getIdAdjuntos());
    		}else if(adjuntoDto.getIdAdjuntos() == null && !adjuntoDto.isBorradoLogico()){
    			//caso2
	        	AdjuntosEventoCorporativo adjuntosEventoCorporativo = dtoAssemblerEvCorp.setAdjuntosEventoCorporativo(adjuntoDto, evco.getIdEventoCorporativo());    	
	        	adjuntosList.add(adjuntosEventoCorporativo);
    		}else if(adjuntoDto.getIdAdjuntos() != null && !adjuntoDto.isBorradoLogico()){
    			//caso2
	        	AdjuntosEventoCorporativo adjuntosEventoCorporativo = dtoAssemblerEvCorp.setAdjuntosEventoCorporativo(adjuntoDto, evco.getIdEventoCorporativo());
	        	adjuntosEventoCorporativo.setGuardable(false);
	        	adjuntosList.add(adjuntosEventoCorporativo);
    		}
        }
		return adjuntosList;
	}


	/**
     * Genera los casos para la modificacion de notas
     * @param listNotas
     * @param evco
     * @return
     */
    private List<NotasEventoCorporativo> modificaNotas(
			List<NotasDTO> listNotas, EventoCorporativo evco) {    	
    	List<NotasEventoCorporativo> lista = null;
    	if(listNotas != null && listNotas.size() > 0){
    		lista = new ArrayList<NotasEventoCorporativo>();
    	}else{
    		return null;
    	}
    	/*Modifica Notas
    	 * 1)si tiene un idNota != null && notavo.isBorradologico
    	 * 		se borra
    	 * 2)si tiene un idNota != null && !notavo.isBorradologico
    	 * 		se sobreescribe
    	 * 3)si tiene un idNota == null
    	 * 		es nuevo
    	 */
    	for(NotasDTO notavo : listNotas){
    		if(StringUtils.isNotEmpty(notavo.getIdNota()) && 
    				StringUtils.isNumeric(notavo.getIdNota()) && 
    				notavo.isBorradoLogico()){
    			//caso 1
    			NotasEventoCorporativo nota = (NotasEventoCorporativo)consultaEventosCorporativosDao.getByPk(NotasEventoCorporativo.class, Long.valueOf(notavo.getIdNota()));
    			if(nota != null){
    				consultaEventosCorporativosDao.delete(nota);
    			}
    		}else if(StringUtils.isNotEmpty(notavo.getIdNota()) && 
    				StringUtils.isNumeric(notavo.getIdNota()) && 
    				!notavo.isBorradoLogico()){
    			//caso 2
    			NotasEventoCorporativo nota = (NotasEventoCorporativo)consultaEventosCorporativosDao.getByPk(NotasEventoCorporativo.class, Long.valueOf(notavo.getIdNota()));
    			dtoAssemblerEvCorp.updateNotasEventoCorporativo(nota, notavo, evco.getIdEventoCorporativo());
    			lista.add(nota);
    			
    		}else if(StringUtils.isBlank(notavo.getIdNota()) && !notavo.isBorradoLogico()){
    			//caso3
    			NotasEventoCorporativo nota = dtoAssemblerEvCorp.setNotasEventoCorporativo(notavo, evco.getIdEventoCorporativo());
    			lista.add(nota);
    		}
    	}
    	
		return lista;
	}


	/**
     * Guarda los elementos de una lista
     * @param opcionesList
     */
    @SuppressWarnings("rawtypes")
	private void guardarLista(List opcionesList) {
    	if(opcionesList != null && opcionesList.size() > 0){
			for(Object opc : opcionesList){
				consultaEventosCorporativosDao.save(opc);
			}		
    	}
	}
    
    
    private void guardarListaSelectiva(List<? extends Guardable> opcionesList) {
    	if(opcionesList != null && opcionesList.size() > 0){
			for(Guardable opc : opcionesList){
				if(opc.esGuardable()){
					consultaEventosCorporativosDao.save(opc);
				}
			}		
    	}
	}

	/**
     * Ayuda a updateEventoCorporativo a generar la lista de opciones a modificar
     * @param listOpciones
     * @param evco
     * @return List<OpcionesEventoCorporativo>
     */
	private List<OpcionesEventoCorporativo> modificaOpciones(
			List<OpcionesDTO> listOpciones, EventoCorporativo evco ) {
		/*-------------------------Modifica Opciones---------------------------------------
    	* 4 casos:
    	* 1) si el evento cambio a mandatorio.
    	* 		eliminar todas las opciones    	* 
    	* 2)si  tiene un idOpcion != null y isBorradoLogico = true
    	* 		se borra el registro de la base
    	* 3)si tiene un idOpcion != null y isBorradoLogico=false
    	* 		Se modifica la opcion
    	* 4)si tiene un idOpcion==null y isBorradoLogico=false
    	* 		se añade la opcion como nueva
    	* 
    	* Se agregan a una lista para el salvado dependiendo del caso
    	*/
		List<OpcionesEventoCorporativo> opcionesList	= null;
		//caso 1
		if(Constantes.TIPO_EVCORP_MANDATORIO.equals( evco.getTipoEvento().getCodigo())){
			opcionesList=consultaEventosCorporativosDao.getOpcionesPorEvco(evco.getIdEventoCorporativo());
			if(opcionesList != null){
				for(int i=0; i<opcionesList.size() ; i++){
					opcionesList.get(i).setOperacionARealizar(Constantes.EVCO_BORRAR_ACCION);
					opcionesList.get(i).setBorrado(true);
				}
			}
			return opcionesList;
		}
		
		//puede contener opciones
		if(listOpciones != null && listOpciones.size() > 0){
			opcionesList = new ArrayList<OpcionesEventoCorporativo>();
		}
    	int indiceOp=0;
    	for(OpcionesDTO opcionVO : listOpciones){   		
    		
    		if(opcionVO.getIdOpcion() !=null  && opcionVO.isBorradoLogico()){
    			//caso 2
    			OpcionesEventoCorporativo opcionesEventoCorporativo = 
    					(OpcionesEventoCorporativo)consultaEventosCorporativosDao.getByPk(OpcionesEventoCorporativo.class, Long.valueOf(opcionVO.getIdOpcion()));
    			opcionesEventoCorporativo.setOperacionARealizar(Constantes.EVCO_BORRAR_ACCION);
    			opcionesEventoCorporativo.setBorrado(true);
    			opcionesList.add(opcionesEventoCorporativo);
    			
    		}else if(opcionVO.getIdOpcion() !=null && !opcionVO.isBorradoLogico()){ 
    			//caso 3
    			OpcionesEventoCorporativo opcionesEventoCorporativo = 
    					(OpcionesEventoCorporativo)consultaEventosCorporativosDao.getByPk(OpcionesEventoCorporativo.class, Long.valueOf(opcionVO.getIdOpcion()));
    			try {
					dtoAssemblerEvCorp.updateOpcionesEventoCorporativo(opcionesEventoCorporativo, opcionVO, evco);
				} catch (ParseException e) {
					throw new BusinessException("Error al transformar una fecha de la opción "+(indiceOp+1)+". por favor contacte a soporte");
				}
    			opcionesEventoCorporativo.setOperacionARealizar(Constantes.EVCO_MODIFICAR_ACCION);
    			opcionesEventoCorporativo.setNumeroOpcion(++indiceOp);
    			opcionesList.add(opcionesEventoCorporativo);
    			
    		}else if(opcionVO.getIdOpcion() == null && !opcionVO.isBorradoLogico()){ 
    			//caso 4
    			OpcionesEventoCorporativo opcionesEventoCorporativo = dtoAssemblerEvCorp.setOpcionesEventoCorporativo(opcionVO, evco.getIdEventoCorporativo());
    			opcionesEventoCorporativo.setOperacionARealizar(Constantes.EVCO_AGREGAR_ACCION);
    			opcionesEventoCorporativo.setNumeroOpcion(++indiceOp);
    			opcionesList.add(opcionesEventoCorporativo);
    		}
    	}
    	/*-------------------------fin Modifica Opciones---------------------------------------*/
		return opcionesList;
	}

	public void deleteAdjunto(Long idAdjuntos) throws BusinessException {
		if(idAdjuntos== null){
			throw new BusinessException("No se encontró el archivo adjunto para eliminar, llame a soporte");
		}
		AdjuntosEventoCorporativo adj = (AdjuntosEventoCorporativo) consultaEventosCorporativosDao.getByPk(AdjuntosEventoCorporativo.class, idAdjuntos);
		if(adj != null){
			consultaEventosCorporativosDao.delete(adj);
		}		
	}
	/**
     * @param dtoAssemblerEvCorp the dtoAssemblerEvCorp to set
     */
    public void setDtoAssemblerEvCorp(DTOAssembleEvCorp dtoAssemblerEvCorp) {
        this.dtoAssemblerEvCorp = dtoAssemblerEvCorp;
    }
	/**
	 * @param bitacoraUtilerias the bitacoraUtilerias to set
	 */
	public void setBitacoraUtilerias(
			BitacoraEventosCorporativosUtil bitacoraUtilerias) {
		this.bitacoraUtilerias = bitacoraUtilerias;
	}
    public void buildEntity(Map<String, Object> map){
    }
	/**
	 * @param consultaEventosCorporativosDao the consultaEventosCorporativosDao to set
	 */
	public void setConsultaEventosCorporativosDao(
			ConsultaEventosCorporativosDao consultaEventosCorporativosDao) {
		this.consultaEventosCorporativosDao = consultaEventosCorporativosDao;
	}

	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}


	/**
	 * @param validadorEvco the validadorEvco to set
	 */
	public void setValidadorEvco(ValidarCamposEventosCorporativosImp validadorEvco) {
		this.validadorEvco = validadorEvco;
	}


	/**
	 * @param jmsTemplateMoiNotificacionesEvCo the jmsTemplateMoiNotificacionesEvCo to set
	 */
	public void setJmsTemplateMoiNotificacionesEvCo(
			JmsTemplate jmsTemplateMoiNotificacionesEvCo) {
		this.jmsTemplateMoiNotificacionesEvCo = jmsTemplateMoiNotificacionesEvCo;
	}


	
	
	
	
}


