/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.util.BitacoraEventosCorporativosUtil;
import com.indeval.portalinternacional.middleware.services.util.DTOAssembleEvCorp;
import com.indeval.portalinternacional.middleware.services.util.XmlUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraEstadosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEvCoMensaje;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesXml;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ReglaEstado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvco;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;
import com.indeval.portalinternacional.persistence.dao.CalendarioEmisionesDeudaExtDao;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;
import com.thoughtworks.xstream.XStream;
/**
 * @author lmunoz
 *
 */
public class ConsultaEventosCorporativosServiceImpl implements ConsultaEventosCorporativosService {
	@SuppressWarnings("unused")
	private MessageResolver errorResolver;
	private ConsultaEventosCorporativosDao consultaEventosCorporativosDao;
	private CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao;
	private DTOAssembleEvCorp dtoAssemblerEvCorp;
	private BitacoraEventosCorporativosUtil bitacoraUtilerias;
	private JmsTemplate jmsTemplateMoiNotificacionesEvCo;
						

	private static final Logger log = LoggerFactory.getLogger(ConsultaEventosCorporativosServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService#getEventosCorporativos(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, boolean)
	 */
	public PaginaVO getEventosCorporativos(EventoCorporativoConsultaDTO params, PaginaVO paginaVO,
			boolean isParticipante) throws BusinessException {
		
		return consultaEventosCorporativosDao.getEventosCorporativos(params, paginaVO, isParticipante);
	}

	
	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService#getBitacoraCambiosEventoCorporativo(java.lang.Long)
	 */
	public List<Estado> getEstadosEventoCorporativo()
			throws BusinessException {
		
		return consultaEventosCorporativosDao.getEstadosEventoCorporativo();
	}

	public List<TipoMercado> getTiposMercado() throws BusinessException {
		
		return consultaEventosCorporativosDao.getTiposMercado();
	}

	public List<TipoDerechoEvCo> getTiposDerecho() throws BusinessException {
		
		return consultaEventosCorporativosDao.getTiposDerecho();
	}

	public List<TipoDerechoEvCo> getTiposDerechoMO() throws BusinessException {
		
		return consultaEventosCorporativosDao.getTiposDerechoMO();
	}

	public List<TipoDerechoEvCo> getTiposDerechoML() throws BusinessException {
		
		return consultaEventosCorporativosDao.getTiposDerechoML();
	}

	public List<TipoEvento> getTiposEvento() throws BusinessException {
		
		return consultaEventosCorporativosDao.getTiposEvento();
	}

	public List<Custodio> getCatalogoCustodios() throws BusinessException {
		
		return calendarioEmisionesDeudaExtDao.getCatalogoCustodios();
	}
	
	public List<ReglaEstado> getReglasEstado() throws BusinessException{
		return consultaEventosCorporativosDao.getReglasEstado();
	}
	
	/**
	 * @param consultaEventosCorporativosDao the consultaEventosCorporativosDao to set
	 */
	public void setConsultaEventosCorporativosDao(
			ConsultaEventosCorporativosDao consultaEventosCorporativosDao) {
		this.consultaEventosCorporativosDao = consultaEventosCorporativosDao;
	}

	

//--------------Cambio de Estado---------------------------------------
	/**
	 * Algoritmo de cambio de estado
	 * 
	 */
	public Integer cambiarEstado(Map<String,String> cambios, String usuario) throws BusinessException {
		Integer modificaciones = 0;
		for(String idevco: cambios.keySet()){
			Long id = Long.valueOf(idevco);				
			Long nuevoEstado = Long.valueOf(cambios.get(idevco));
			//obtiene bitacora
			BitacoraEstadosEvco beevco = construyeBitacoraCambioEstado(id, nuevoEstado, usuario);
			//realiza cambio de estado
			//consultaEventosCorporativosDao.actualizaEstado(id, nuevoEstado);
			EventoCorporativo evco = (EventoCorporativo)consultaEventosCorporativosDao.getByPk(EventoCorporativo.class, id);
			Estado estado = (Estado)consultaEventosCorporativosDao.getByPk(Estado.class, nuevoEstado);
			evco.setEstado(estado);
			evco.setActualizado(false);
			evco.setPrioridad(0);
			//consultaEventosCorporativosDao.flush();
			cambioEstadoReglasNegocio(id,nuevoEstado);	
			//guardar bitacora
			consultaEventosCorporativosDao.save(evco);			
			consultaEventosCorporativosDao.save(beevco);
			consultaEventosCorporativosDao.flush();
		}		
		return modificaciones;
	}
	
	
	private BitacoraEstadosEvco construyeBitacoraCambioEstado(Long idevco, Long nuevoEstado,String usr){
		BitacoraEstadosEvco beevco = new BitacoraEstadosEvco();
		EventoCorporativo evco = (EventoCorporativo)consultaEventosCorporativosDao.getByPk(EventoCorporativo.class, idevco);
		Long original=evco.getEstado().getIdEstado();
		beevco.setIdEventoCorporativo(idevco);
		beevco.setIdEstadoInicial(original);
		beevco.setIdEstadoFinal(nuevoEstado);
		beevco.setUsuario(usr);
		beevco.setFechaModificacion(Calendar.getInstance().getTime());		
		return beevco;
	}
	
	private EventoCorporativo cambioEstadoReglasNegocio(Long id, Long nuevoEstado) {
		EventoCorporativo evco = (EventoCorporativo)consultaEventosCorporativosDao.getByPk(EventoCorporativo.class, id);
		if(evco != null){
			//en cualquier cambio de estado el campo actualizado y prioridad pasan  a inactivo falso
			evco.setActualizado(false);
			evco.setPrioridad(0);
			//cuando el evento pasa a liquidado se quita la vigencia
			if(Constantes.ESTADO_LIQUIDADO_EVCO.equals(nuevoEstado)){				
				consultaEventosCorporativosDao.borraInfoAdjuntosEvCo(id);
				//enviaMensajeNotificacionesLiquidado(id); //se retiro de los requerimientos
			}
		}
		//consultaEventosCorporativosDao.saveWithFlush(evco);
		return evco;
	}

	//TODO: refactorizar este metodo y el del servicio de Save en otro servicio
	private void enviaMensajeNotificacionesLiquidado(Long idEventoCorporativo) {
		log.debug("enviar notificacion evco Liquidacion");
		//Obten los xml
		NotificacionesXml notifxml = new NotificacionesXml();
		notifxml.setIdEventoCorporativo(idEventoCorporativo);
		
		List<NotificacionEventoCorporativo> notificacionList = consultaEventosCorporativosDao.getNotificacionesPorEvco(idEventoCorporativo);
		if(notificacionList != null && notificacionList.size() > 0){
			List<NotificacionEvCoMensaje> listnotif = new ArrayList<NotificacionEvCoMensaje>();
			
			for(NotificacionEventoCorporativo msg :notificacionList){
				NotificacionEvCoMensaje notifmsg = new NotificacionEvCoMensaje();
				notifmsg.setAccion("BO");
				notifmsg.setCronexpression(msg.getCronexpresion());
				notifmsg.setFechaFin(msg.getFechaFin());
				notifmsg.setFechaInicio(msg.getFechaInicio());
				notifmsg.setIdNotificacionEvCo(msg.getIdNotificacion());
				//verificar el objeto de la lista de distribucion
				notifmsg.setLista(msg.getListaDistribucion().getIdLista().toString());
				notifmsg.setMensaje(msg.getMensaje());
				//notifmsg.setNumNotificacion(msg.getNumNotificacion());
				
				//manda la notificacion a no vigente
				msg.setVigente(0l);
				
				
				listnotif.add(notifmsg);
			}
			notifxml.setNotificaciones(listnotif);
			XStream xstream = new XStream();
			XmlUtils.setAtributosXStream(xstream, NotificacionesXml.class, NotificacionEvCoMensaje.class);	
			final String mensaje = xstream.toXML(notifxml);
			
			//enviar
			this.jmsTemplateMoiNotificacionesEvCo.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					log.info("=======================>   MENSAJE A Notificacion EvCo MOI:\n");
					Message msg = session.createTextMessage(mensaje);
					log.debug(msg.toString());
					return msg;
				}
			});	
			
			log.debug("borrado notificaciones por Liquidacion");
		}
	}
	
	//--------------Fin Cambio de Estado---------------------------------------	
	
	public EventoCorporativo getEventoCorporativoById(Long folio)
			throws BusinessException {		
		return (EventoCorporativo) consultaEventosCorporativosDao.getByPk(EventoCorporativo.class, folio);
	}

	public CuerpoEventoCorporativo getCuerpoEventoCorporativo(Long folio)
			throws BusinessException {
		return consultaEventosCorporativosDao.getCuerpoEventoCorporativo(folio); 		
	}

   

    public TipoDerechoEvCo findTipoDerechoById(Long idTipoDerecho)
            throws BusinessException {
        return consultaEventosCorporativosDao.findTipoDerechoById(idTipoDerecho);
    }

	
	


	/**
	 * @param bitacoraUtilerias the bitacoraUtilerias to set
	 */
	public void setBitacoraUtilerias(
			BitacoraEventosCorporativosUtil bitacoraUtilerias) {
		this.bitacoraUtilerias = bitacoraUtilerias;
	}

	public PaginaVO getBitacoraPorEventoCorportativo(EventoCorporativoConsultaDTO params, PaginaVO paginaVO)
			throws BusinessException {
		
		PaginaVO	paginaVOTmp=consultaEventosCorporativosDao.getBitacoraPorEventoCorportativo(params,paginaVO);
		
		if(paginaVOTmp.getRegistros() != null ){

	    			@SuppressWarnings("unchecked")
	    			EventoCorporativoFullVistaDTO bitacora= bitacoraUtilerias.leerListaXMLBitacoraEvco(paginaVOTmp.getRegistros());
					List<EventoCorporativoFullVistaDTO> bitacoraParseadaList= new ArrayList<EventoCorporativoFullVistaDTO>();
					bitacoraParseadaList.add(bitacora);
		    		
	    		paginaVO.setRegistros(bitacoraParseadaList); 	    		
		}
		
		return paginaVOTmp;
	}
	
	
	public AdjuntosEventoCorporativo getArchivoAdjuntoPorId(Long idArchivo)
			throws BusinessException {
					AdjuntosEventoCorporativo adjunto= consultaEventosCorporativosDao.getArchivoAdjuntoPorId(idArchivo);
		return adjunto;
	}
	
	public List<BitacoraCambiosDto> getBitacoraControlCambios(Long idEventoCorporativo) throws BusinessException{
		List<BitacoraCambiosDto> listaBitacora= new ArrayList<BitacoraCambiosDto>();

		listaBitacora=dtoAssemblerEvCorp.convertirEvcoToDto(consultaEventosCorporativosDao.getBitacoraControlCambios(idEventoCorporativo));
		final Long bitacoraVisible=new Long(1);
		for (BitacoraCambiosDto bitacoraCambiosDto : listaBitacora)
		{
			bitacoraCambiosDto.setVisibleCheck(false);
			if(bitacoraCambiosDto.getVisible() != null)
			{
				if(bitacoraCambiosDto.getVisible().equals(bitacoraVisible) == true)
				{			
					bitacoraCambiosDto.setVisibleCheck(true);				
				}
			}

		}
		
		return listaBitacora;
	}
	
	public EventoCorporativoFullVistaDTO getBitacoraPorRegistro(Long idBitacoraCambios) throws BusinessException{


		List<BitacoraCambiosEvco> bitacoraLista =consultaEventosCorporativosDao.getBitacoraPorRegistro(idBitacoraCambios);
		EventoCorporativoFullVistaDTO bitacora= null;
		
		if(bitacoraLista != null)
		{
			 bitacora= bitacoraUtilerias.leerListaXMLBitacoraEvco(bitacoraLista);
		}
		
		return bitacora;
	}
	
	
	
	public void updateCambiosDeEstadoBitacora(String idsBitacoraCambioFalse, String idsBitacoraCambio, Long estadoHabilitado,Long estadoInhabil)
			throws BusinessException {
		
		String[] idsRegistro= idsBitacoraCambio.split(",");
		String[] idsRegistroFalse= idsBitacoraCambioFalse.split(",");
		
		for (String id : idsRegistro)
		{	try
			{
				consultaEventosCorporativosDao.updateCambiosDeEstadoBitacora(Long.parseLong(id),estadoHabilitado);	
			}
			catch(NumberFormatException  e)
			{e.printStackTrace();}
		}

		for (String id : idsRegistroFalse)
		{
			try{
				consultaEventosCorporativosDao.updateCambiosDeEstadoBitacora(Long.parseLong(id),estadoInhabil);	
			}
			catch(NumberFormatException  e)
			{e.printStackTrace();}
		}
	}
	
	public EventoCorporativoEdicionDTO getFullEventoCorporativo(Long id) throws BusinessException {
		EventoCorporativoEdicionDTO dto = new EventoCorporativoEdicionDTO();
		//obtengo el evento corporativo
		EventoCorporativo eventoCorporativo = consultaEventosCorporativosDao.getResumenCorporativo(id);
		//obtengo los objetos asociados al evento
		List<OpcionesEventoCorporativo> opcionesList =consultaEventosCorporativosDao.getOpcionesPorEvco(id);
		List<NotasEventoCorporativo> notasList =consultaEventosCorporativosDao.getNotasPorEvco(id);
		List<AdjuntosEventoCorporativo> adjuntosList = consultaEventosCorporativosDao.getAdjuntosPorEvcoNoData(id);
		List<NotificacionEventoCorporativo> notificacionesList =consultaEventosCorporativosDao.getNotificacionesPorEvco(id);
		List<ValidacionesEvco> validacionesList =consultaEventosCorporativosDao.getValidacionesPorEvco(id);
		CuerpoEventoCorporativo cuerpoEvco = consultaEventosCorporativosDao.getCuerpoEventoCorporativo(id);
		//vacio el contenido en el objeto que voy a manejar en la vista
		dto.setIdEventoCorporativo(String.valueOf(id));
		dto.setFechaRegistro(eventoCorporativo.getFechaRegistro());
		dto.setFechaCreacion(eventoCorporativo.getFechaCreacion());
		dto.setTipoValor(eventoCorporativo.getTipoValor());
		dto.setEmisora(eventoCorporativo.getEmisora());
		dto.setSerie(eventoCorporativo.getSerie());
		dto.setIsin(eventoCorporativo.getIsin());
		dto.setTipoDerechoMO(eventoCorporativo.getTipoDerechoMO() != null ? eventoCorporativo.getTipoDerechoMO().getIdTipoDerecho().toString() : "");
		dto.setTipoDerechoML(eventoCorporativo.getTipoDerechoML() != null ? eventoCorporativo.getTipoDerechoML().getIdTipoDerecho().toString() : "");
		dto.setEstado(eventoCorporativo.getEstado().getIdEstado().toString());
		dto.setFechaIndeval(eventoCorporativo.getFechaIndeval());
		dto.setFechaCliente(eventoCorporativo.getFechaCliente());
		dto.setFechaPago(eventoCorporativo.getFechaPago());
		dto.setCustodio(eventoCorporativo.getCustodio().getId().toString());
		dto.setTipoEvento(eventoCorporativo.getTipoEvento().getIdTipoEvento().toString());
		dto.setCuerpoEventoHtml(cuerpoEvco.getCuerpo());
		dto.setPiePaginaHtml(cuerpoEvco.getPiePagina());
		dto.setTipoMercado(eventoCorporativo.getTipoMercado().getIdTipoMercado().toString());
		dto.setListOpciones(dtoAssemblerEvCorp.convertirListaOpcionesEntityToDto(opcionesList));
		dto.setListNotas(dtoAssemblerEvCorp.convertirListaNotasToDto(notasList));
		dto.setListAdjuntos(dtoAssemblerEvCorp.convertirListaAdjuntosEntityToDto(adjuntosList));
		dto.setListNotificaciones(dtoAssemblerEvCorp.convertirNotificacionesEntityToDto(notificacionesList));
		dto.setListValidaciones(dtoAssemblerEvCorp.convertirValidacionesEntityToDto(validacionesList));
		dto.setListaNotificaciones(dtoAssemblerEvCorp.setNotificacionEntityToVo(notificacionesList));
		dto.setListaAdjuntos(dtoAssemblerEvCorp.setArchivoAdjuntoEntityToVo(adjuntosList));		
		return dto;
	}

	@SuppressWarnings("unchecked")
	public EventoCorporativoFullVistaDTO getResumenEventoCorporativo(Long idEventoCorporativo) throws BusinessException{

		EventoCorporativoConsultaDTO params = new EventoCorporativoConsultaDTO();
		params.setIdEventoCorporativo(idEventoCorporativo.toString());
	
		PaginaVO	paginaVOTmp=consultaEventosCorporativosDao.getBitacoraPorEventoCorportativo(params,new PaginaVO());
		EventoCorporativoFullVistaDTO bitacora= null;	

		List<BitacoraCambiosEvco> listBitacora = paginaVOTmp.getRegistros();
		if(listBitacora != null && listBitacora.size() > 0)
		{
			List<BitacoraCambiosEvco> registroUnico= new ArrayList<BitacoraCambiosEvco>();
			BitacoraCambiosEvco bitacoraEvco=listBitacora.get(listBitacora.size()-1);
			registroUnico.add(bitacoraEvco);			
			bitacora= bitacoraUtilerias.leerListaXMLBitacoraEvco(registroUnico);
		List<AdjuntosEventoCorporativo> adjuntosList =consultaEventosCorporativosDao.getAdjuntosPorEvco(idEventoCorporativo);
		List<NotificacionEventoCorporativo> notificacionesList =consultaEventosCorporativosDao.getNotificacionesPorEvco(idEventoCorporativo);
		List<ValidacionesEvco> validacionesList =consultaEventosCorporativosDao.getValidacionesPorEvco(idEventoCorporativo);

		if(bitacora.getCuerpoEnVistaLista() != null && bitacora.getCuerpoEnVistaLista().size() > 0)
			{	bitacora.getCuerpoEnVistaLista().get(0).setArchivosAdjuntos(dtoAssemblerEvCorp.convertirListaAdjuntosEntityToDto(adjuntosList));			
				bitacora.getCuerpoEnVistaLista().get(0).setNotificacionesList(dtoAssemblerEvCorp.convertirNotificacionesEntityToDto(notificacionesList));
				bitacora.getCuerpoEnVistaLista().get(0).setValidacionesList(dtoAssemblerEvCorp.convertirValidacionesEntityToDto(validacionesList));
		}
		}
		return bitacora;
	}
	
	
	public PaginaVO getCatalogoTiposDerecho(TipoDerechoDto params,
			PaginaVO paginaVO) throws BusinessException {
		// TODO Auto-generated method stub
		return consultaEventosCorporativosDao.getCatalogoTiposDerecho(params, paginaVO);
	}

	public List<TipoValidacionEvco> getAllTipoValidacionEvco() throws BusinessException {
		return consultaEventosCorporativosDao.getAllTipoValidacionEvco();
	}

	public List<OperadorEvco> getAllOperadorEvco() throws BusinessException {
		return consultaEventosCorporativosDao.getAllOperadorEvco();
	}

	public TipoDerechoDto getTipoDerechoPorId(Long idTipoDerecho)
			throws BusinessException {
		// TODO Auto-generated method stub
		log.info("Entra a getTipoDerechoPorId");
		TipoDerechoEvCo tipoDerechoEvco =consultaEventosCorporativosDao.findTipoDerechoById(idTipoDerecho);		
		
		return dtoAssemblerEvCorp.setTipoDerechoEvcoToDto(tipoDerechoEvco);
	}
	
	public void saveTipoDerecho(TipoDerechoDto tipoDerechoDto)
			throws BusinessException {			  
		  TipoDerechoEvCo tipoDerechoEvco= 	dtoAssemblerEvCorp.setTipoDerechoDtoToEvco(tipoDerechoDto);
		consultaEventosCorporativosDao.saveWithFlush(tipoDerechoEvco);

	}
	public void setDtoAssemblerEvCorp(DTOAssembleEvCorp dtoAssemblerEvCorp) {
		this.dtoAssemblerEvCorp = dtoAssemblerEvCorp;
	}

	public DTOAssembleEvCorp getDtoAssemblerEvCorp() {
		return dtoAssemblerEvCorp;
	}

	public TipoValidacionEvco getTipoValidacionById(Long id)throws BusinessException {
		return consultaEventosCorporativosDao.getTipoValidacionById(id);
	}

	public OperadorEvco getOperadorById(Long id) throws BusinessException {
		return consultaEventosCorporativosDao.getOperadorById(id);
	}

	public List<ListaDistribucion> getAllListaDistribucion() throws BusinessException {
		return consultaEventosCorporativosDao.getAllListaDistribucion();
	}

	public ListaDistribucion getListaDisById(Long id) throws BusinessException {
		return consultaEventosCorporativosDao.getListaDisById(id);
	}

	/**
	 * @param calendarioEmisionesDeudaExtDao the calendarioEmisionesDeudaExtDao to set
	 */
	public void setCalendarioEmisionesDeudaExtDao(
			CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao) {
		this.calendarioEmisionesDeudaExtDao = calendarioEmisionesDeudaExtDao;
	}

	/**
	 * @param jmsTemplateMoiNotificacionesEvCo the jmsTemplateMoiNotificacionesEvCo to set
	 */
	public void setJmsTemplateMoiNotificacionesEvCo(
			JmsTemplate jmsTemplateMoiNotificacionesEvCo) {
		this.jmsTemplateMoiNotificacionesEvCo = jmsTemplateMoiNotificacionesEvCo;
	}
}


