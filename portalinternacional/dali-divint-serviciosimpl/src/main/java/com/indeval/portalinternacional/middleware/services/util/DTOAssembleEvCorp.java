package com.indeval.portalinternacional.middleware.services.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CustodioDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoAltaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;

public class DTOAssembleEvCorp {
    
    private ConsultaEventosCorporativosDao consultaEventosCorporativosDao;
	private InterpretExpressionCron cronUtil;
	private final static Logger log = LoggerFactory.getLogger(DTOAssembleEvCorp.class);
    private static final Integer PRIORIDAD_INSERCION = 0;
    private static final Integer PRIORIDAD_EDICION = 1;
    private final DateFormat fechaFormateada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    private FileInputStream fileInputStream;
    
    
    public EventoCorporativo setEventoCorporativo(EventoCorporativoAltaDTO param){
        log.info("Method ::::::: setEventoCorporativo");
        EventoCorporativo eventoCorporativo = new EventoCorporativo();
        TipoDerechoEvCo tipoDerechoEvcoMo = null;
        TipoDerechoEvCo tipoDerechoEvCoMl = null;
        TipoMercado tipoMercado = null;
        TipoEvento tipoEvento = null;
        Estado estado = null;
        Custodio custodio = null;

        eventoCorporativo.setFechaCreacion(param.getFechaCreacion());
        eventoCorporativo.setFechaRegistro(param.getFechaRegistro());
        eventoCorporativo.setTipoValor(param.getTipoValor());
        eventoCorporativo.setEmisora(param.getEmisora());
        eventoCorporativo.setSerie(param.getSerie());
        eventoCorporativo.setIsin(param.getIsin());

        tipoDerechoEvcoMo = consultaEventosCorporativosDao.findTipoDerechoById(Long.parseLong(param.getTipoDerechoMO()));
        tipoDerechoEvCoMl = consultaEventosCorporativosDao.findTipoDerechoById(Long.parseLong(param.getTipoDerechoML()));
        eventoCorporativo.setTipoDerechoMO(tipoDerechoEvcoMo);
        eventoCorporativo.setTipoDerechoML(tipoDerechoEvCoMl);

        tipoMercado = consultaEventosCorporativosDao.findTipoMercadoById(Long.parseLong(param.getTipoMercado()));
        eventoCorporativo.setTipoMercado(tipoMercado);

        tipoEvento = consultaEventosCorporativosDao.findTipoEventoById(Long.parseLong(param.getTipoEvento()));
        eventoCorporativo.setTipoEvento(tipoEvento);
        
        estado = consultaEventosCorporativosDao.findEstadoById(Long.parseLong("1"));
        eventoCorporativo.setEstado(estado);
        eventoCorporativo.setFechaIndeval(param.getFechaIndeval());
        eventoCorporativo.setFechaCliente(param.getFechaCliente());
        eventoCorporativo.setFechaPago(param.getFechaPago());
        eventoCorporativo.setFechaRegistro(param.getFechaRegistro());
        eventoCorporativo.setPrioridad(PRIORIDAD_INSERCION);

        custodio = consultaEventosCorporativosDao.findCustodioById(Long.parseLong(param.getCustodio()));
        eventoCorporativo.setCustodio(custodio);
        eventoCorporativo.setFechaActualizacion(Calendar.getInstance().getTime());
        return eventoCorporativo;
    }
    
    public CuerpoEventoCorporativo setCuerpoEventoCorporativo(EventoCorporativoAltaDTO param, Long idEventoCorporativo){
    	log.debug("Method ::::::::: setCuerpoEventoCorporativo");
        CuerpoEventoCorporativo cuerpoEventoCorporativo = new CuerpoEventoCorporativo();
        log.debug("Id del evento recuperado en el cuerpo evento corporativo " + idEventoCorporativo);
        
        cuerpoEventoCorporativo.setCuerpo(param.getCuerpoEventoHtml());
        cuerpoEventoCorporativo.setPiePagina(param.getPiePaginaHtml());
        cuerpoEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
        return cuerpoEventoCorporativo;
    }
    
    public OpcionesEventoCorporativo setOpcionesEventoCorporativo(OpcionesDTO vo, Long idEventoCorporativo){//EventoCorporativoAltaDTO param){
    	log.debug("Method :::::::::: setOpcionesEventoCorporativo");
    	OpcionesEventoCorporativo opcionesEventoCorporativo = null;
        log.debug("Id del evento corporativo recuperado en las opciones del evento " + idEventoCorporativo);
        
        //seteo los valores al objeto
        opcionesEventoCorporativo = new OpcionesEventoCorporativo();
        opcionesEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
        opcionesEventoCorporativo.setCuerpo(vo.getCuerpoOpcion());
        opcionesEventoCorporativo.setFechaCliente(vo.getFechaClienteOpcion());
        opcionesEventoCorporativo.setFechaIndeval(vo.getFechaIndevalOpcion());
        opcionesEventoCorporativo.setFechaPago(vo.getFechaPagoOpcion());
        opcionesEventoCorporativo.setOpcDefault(new Long(vo.isDefault()?"1":"0"));
        opcionesEventoCorporativo.setBorrado(false);
        
    	return opcionesEventoCorporativo;
    }
    
    public NotasEventoCorporativo setNotasEventoCorporativo(NotasDTO vo, Long idEventoCorporativo){
    	log.debug("Method ::::::::: setNotasEventoCorporativo");
    	NotasEventoCorporativo notasEventoCorporativo = null;
    	
    	//seteo los valores al objeto
    	notasEventoCorporativo = new NotasEventoCorporativo();
    	notasEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
    	notasEventoCorporativo.setCuerpo(vo.getCuerpoNota());
    	return notasEventoCorporativo;
    }
    
    public AdjuntosEventoCorporativo setAdjuntosEventoCorporativo(ArchivosAdjuntosEvcoDTO vo, Long idEventoCorporativo){
    	log.debug("Method :::::::::: setAdjuntosEventoCorporativo");
    	AdjuntosEventoCorporativo adjuntosEventoCorporativo = null;
    	
    	//seteo los valores al objeto
    	adjuntosEventoCorporativo = new AdjuntosEventoCorporativo();
    	try {
    		adjuntosEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
    		adjuntosEventoCorporativo.setDescripcion(vo.getDescripcion());
			//No entra borrado
			adjuntosEventoCorporativo.setBorrado("0");
			if(vo.getIdAdjuntos()!= null){
				adjuntosEventoCorporativo.setIdAdjuntos(vo.getIdAdjuntos());
    		}
    		if(vo.getArchivo() != null){
    			adjuntosEventoCorporativo.setArchivo(convertFileToByte(vo.getArchivo()));
    		}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return adjuntosEventoCorporativo;
    }
    
    private byte[] convertFileToByte(File file) throws IOException{
    	byte[] fileByte = new byte[(int) file.length()];
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	fileInputStream = new FileInputStream(file);
    	
    	for (int readNum; (readNum = fileInputStream.read(fileByte)) != -1;) {
    		bos.write(fileByte, 0, readNum);
    	}
    	return bos.toByteArray();
    }
  public TipoDerechoDto setTipoDerechoEvcoToDto(TipoDerechoEvCo  tipoDerechoEvco){
    	log.debug("Method ::::::::: setTipoDerechoEvcoToDto");

    	TipoDerechoDto  tipoDerechoDto= new TipoDerechoDto();
    	tipoDerechoDto.setIdTipoDerecho(tipoDerechoEvco.getIdTipoDerecho().toString());
    	tipoDerechoDto.setTipoDerecho(tipoDerechoEvco.getTipoDerecho());
    	tipoDerechoDto.setTipo(tipoDerechoEvco.getTipo());
    	tipoDerechoDto.setActivo(tipoDerechoEvco.getInactivo().toString());  	
    	
    	return tipoDerechoDto;
    }
    
    public TipoDerechoEvCo setTipoDerechoDtoToEvco(TipoDerechoDto tipoDerechoDto){
    	log.debug("Method ::::::::: setTipoDerechoEvcoToDto");
    	TipoDerechoEvCo  tipoDerechoEvco= new TipoDerechoEvCo();
    	if(tipoDerechoDto.getIdTipoDerecho() != null && !tipoDerechoDto.getIdTipoDerecho().equals(""))
		{
	    	tipoDerechoEvco.setIdTipoDerecho(new Long(tipoDerechoDto.getIdTipoDerecho()));	
		}

    	tipoDerechoEvco.setTipoDerecho(tipoDerechoDto.getTipoDerecho());
    	tipoDerechoEvco.setTipo(tipoDerechoDto.getTipo());
    	tipoDerechoEvco.setInactivo(new Long(tipoDerechoDto.getActivo()));  
    	return tipoDerechoEvco;
    }
    
	/*
	 * METODOS  DE CONVERSIONES DTO TO ENTITY OR  ENTITY TO DTO
	 */	
	public CustodioDto convertirCustodioEntitytoDto(Custodio custodioParam)
	{

    	CustodioDto custodioDto= new CustodioDto();
    	custodioDto.setId(custodioParam.getId());
    	custodioDto.setDescripcion(custodioParam.getDescripcion());
    	custodioDto.setCodigoBanco(custodioParam.getCodigoBanco());
    	custodioDto.setNombreCorto(custodioParam.getNombreCorto());
    	custodioDto.setParticipante(custodioParam.getParticipante());	    

	    
	    return custodioDto;
	}
	
	/*Convertir objeto entity a objeto Dto
	 * 
	 * 
	 */
	public List<BitacoraCambiosDto> convertirEvcoToDto(List<BitacoraCambiosEvco> listBitacora)
	{
		List<BitacoraCambiosDto> listaBitacoraDto= new ArrayList<BitacoraCambiosDto>();
		for (BitacoraCambiosEvco bitacoraCambiosEvco : listBitacora)
		{
			
			BitacoraCambiosDto bitacoraDto= new BitacoraCambiosDto();
			

			bitacoraDto.setFechaModificacion(bitacoraCambiosEvco.getFechaModificacion());
			bitacoraDto.setFormatoOriginal(bitacoraCambiosEvco.getFormatoOriginal());
			bitacoraDto.setIdBitacoraCambios(bitacoraCambiosEvco.getIdBitacoraCambios());
			bitacoraDto.setIdEventoCorporativo(bitacoraCambiosEvco.getIdEventoCorporativo());
			bitacoraDto.setUsuario(bitacoraCambiosEvco.getUsuario());
			bitacoraDto.setVisible(bitacoraCambiosEvco.getVisible());
			
			listaBitacoraDto.add(bitacoraDto);
		}
		
		
		return listaBitacoraDto;
	}
	/*Convertir objeto entity a objeto Dto
	 * 
	 * 
	 */
	public ArchivosAdjuntosEvcoDTO convertirAdjuntoEntityToDto(AdjuntosEventoCorporativo adjuntoEvco)
	{
		ArchivosAdjuntosEvcoDTO adjuntoDto= new ArchivosAdjuntosEvcoDTO();
        	adjuntoDto.setBorrado(adjuntoEvco.getBorrado());
        	adjuntoDto.setDescripcion(adjuntoEvco.getDescripcion());
        	adjuntoDto.setIdAdjuntos(adjuntoEvco.getIdAdjuntos());
        	adjuntoDto.setIdEventoCorporativo(adjuntoEvco.getIdEventoCorporativo());
        	adjuntoDto.setArchivoAdjunto(adjuntoEvco.getArchivo());

		
		
		return adjuntoDto;
	}
	public List<OpcionesDTO> convertirListaOpcionesEntityToDto(List<OpcionesEventoCorporativo> listOpciones){
		List<OpcionesDTO> listOpc = new ArrayList<OpcionesDTO>();
		OpcionesDTO vo = null;
		for(OpcionesEventoCorporativo opc : listOpciones){
			vo = new OpcionesDTO();
			vo.setCuerpoOpcion(opc.getCuerpo());
			vo.setDefault(opc.getOpcDefault().intValue() == 1 ? true : false);
			vo.setValorDefault(vo.isDefault() ? "Opcion por Default" : "");
			vo.setFechaClienteOpcion(opc.getFechaCliente());
			vo.setFechaIndevalOpcion(opc.getFechaIndeval());
			vo.setFechaPagoOpcion(opc.getFechaPago());
			vo.setFechaCliente(dateToString(opc.getFechaCliente()));
			vo.setFechaIndeval(dateToString(opc.getFechaIndeval()));
			vo.setFechaPago(dateToString(opc.getFechaPago()));
			vo.setIdOpcion(opc.getIdOpcion());
			vo.setBorradoLogico(false);
			vo.setNumeroOpcion(String.valueOf(listOpc.size() + 1));
			listOpc.add(vo);
		}
		return listOpc;
	}
	
	private String dateToString(Date fecha){
		if(fecha!=null){
		return fechaFormateada.format(fecha);
		}else{
			return null;
		}
	}
	private Date StringToDate(String fecha) throws ParseException{
		if(StringUtils.isNotEmpty(fecha)){
			return fechaFormateada.parse(fecha);
		}else{
			return null;
		}
	}
	public List<NotasDTO> convertirListaNotasToDto(List<NotasEventoCorporativo> listaNotas){
		List<NotasDTO> listNotas = new ArrayList<NotasDTO>();
		NotasDTO vo = null;
		for(NotasEventoCorporativo notas : listaNotas){
			vo = new NotasDTO();
			vo.setCuerpoNota(notas.getCuerpo());
			vo.setIdNota(notas.getIdNota().toString());
			vo.setNumNota(String.valueOf(listNotas.size() + 1));
			listNotas.add(vo);
		}
		return listNotas;
	}
	/*Convertir objeto entity a objeto Dto
	 * 
	 * 
	 */
	public List<ArchivosAdjuntosEvcoDTO> convertirListaAdjuntosEntityToDto(List<AdjuntosEventoCorporativo> listaAdjuntos)
	{
		List<ArchivosAdjuntosEvcoDTO> listaAdjuntoDto= new ArrayList<ArchivosAdjuntosEvcoDTO>();
		
	    for (AdjuntosEventoCorporativo adjuntoEvco : listaAdjuntos)
		{
	    	ArchivosAdjuntosEvcoDTO adjuntoDto= new ArchivosAdjuntosEvcoDTO();
        	adjuntoDto.setBorrado(adjuntoEvco.getBorrado());
        	adjuntoDto.setDescripcion(adjuntoEvco.getDescripcion());
        	adjuntoDto.setIdAdjuntos(adjuntoEvco.getIdAdjuntos());
        	adjuntoDto.setIdEventoCorporativo(adjuntoEvco.getIdEventoCorporativo());
        	adjuntoDto.setArchivoAdjunto(adjuntoEvco.getArchivo());
        	adjuntoDto.setNumAdjunto(String.valueOf(listaAdjuntos.size() + 1));
        	listaAdjuntoDto.add(adjuntoDto);
		}	
		return listaAdjuntoDto;
	}
	
	
	public List<ArchivosAdjuntosEvcoDTO> setArchivoAdjuntoEntityToVo(List<AdjuntosEventoCorporativo> listaAdjuntos){
		List<ArchivosAdjuntosEvcoDTO> listaAdjuntoDto = new ArrayList<ArchivosAdjuntosEvcoDTO>();
		
		for(AdjuntosEventoCorporativo adjuntoEvco : listaAdjuntos){
			ArchivosAdjuntosEvcoDTO adjuntoDto = new ArchivosAdjuntosEvcoDTO();
			adjuntoDto.setBorrado(adjuntoEvco.getBorrado());
        	adjuntoDto.setDescripcion(adjuntoEvco.getDescripcion());
        	adjuntoDto.setIdAdjuntos(adjuntoEvco.getIdAdjuntos());
        	adjuntoDto.setIdEventoCorporativo(adjuntoEvco.getIdEventoCorporativo());
        	adjuntoDto.setArchivoAdjunto(adjuntoEvco.getArchivo());
        	adjuntoDto.setNumAdjunto(String.valueOf(listaAdjuntos.size() + 1));
        	listaAdjuntoDto.add(adjuntoDto);
		}
		
		return listaAdjuntoDto;
	}
	
	/*Convertir objeto entity a objeto Dto
	 * 
	 * 
	 */
	public List<NotificacionesEvcoDTO> convertirNotificacionesEntityToDto(List<NotificacionEventoCorporativo> listaNotificaciones)
	{
		List<NotificacionesEvcoDTO> listaNotificacionesDto= new ArrayList<NotificacionesEvcoDTO>();
		
	    for (NotificacionEventoCorporativo notificacionEvco : listaNotificaciones)
		{
			
			NotificacionesEvcoDTO notificacionDto= new NotificacionesEvcoDTO();
			notificacionDto.setDesde(BitacoraEventosCorporativosUtil.getDateOfString(notificacionEvco.getFechaInicio().toString()));
			notificacionDto.setDestinatario(notificacionEvco.getListaDistribucion().getNombre());
			notificacionDto.setHasta(BitacoraEventosCorporativosUtil.getDateOfString(notificacionEvco.getFechaFin().toString()));
			notificacionDto.setHora(cronUtil.getHourOfCronExpresion(notificacionEvco.getCronexpresion()));
			notificacionDto.setTextAdicional(notificacionEvco.getMensaje());
			notificacionDto.setTipoNotificacion(cronUtil.getTypeOfCronExpresion(notificacionEvco.getCronexpresion()));

			notificacionDto.setNumNotificacion(String.valueOf(listaNotificacionesDto.size() + 1));
			notificacionDto.setStrDesde(dateToString(notificacionEvco.getFechaInicio()));
			notificacionDto.setStrHasta(dateToString(notificacionEvco.getFechaFin()));
			notificacionDto.setIdNotificacion(notificacionEvco.getIdNotificacion().toString());
			notificacionDto.setNumOpcAcc(notificacionEvco.getTipoNotificacion());
			notificacionDto.setPosicionLista(listaNotificacionesDto.size());
			notificacionDto.setCronExpression(notificacionEvco.getCronexpresion());
			listaNotificacionesDto.add(notificacionDto);
		}
		
		
		return listaNotificacionesDto;
	}
	
	public List<NotificacionesEvcoDTO> setNotificacionEntityToVo(List<NotificacionEventoCorporativo> listNotificaciones){
		List<NotificacionesEvcoDTO> listVo = new ArrayList<NotificacionesEvcoDTO>();
		NotificacionesEvcoDTO vo = null;
		for(NotificacionEventoCorporativo evco : listNotificaciones){
			vo = new NotificacionesEvcoDTO();
			vo.setDesde(BitacoraEventosCorporativosUtil.getDateOfString(evco.getFechaInicio().toString()));
			vo.setDestinatario(evco.getListaDistribucion().getNombre());
			vo.setHasta(BitacoraEventosCorporativosUtil.getDateOfString(evco.getFechaFin().toString()));
			vo.setHora(cronUtil.getHourOfCronExpresion(evco.getCronexpresion()));
			vo.setTextAdicional(evco.getMensaje());
			vo.setTipoNotificacion(cronUtil.getTypeOfCronExpresion(evco.getCronexpresion()));
			vo.setNumNotificacion(String.valueOf(listVo.size() + 1));
			vo.setStrDesde(dateToString(evco.getFechaInicio()));
			vo.setStrHasta(dateToString(evco.getFechaFin()));
			vo.setIdNotificacion(evco.getIdNotificacion().toString());
			vo.setNumOpcAcc(evco.getTipoNotificacion());
			vo.setPosicionLista(listNotificaciones.size());
			vo.setIdListaDIstribucion(evco.getListaDistribucion().getIdLista().toString());
			vo.setStrTextoNotificacion(evco.getMensaje());
			vo.setStrFechaIncio(BitacoraEventosCorporativosUtil.dateToString(evco.getFechaInicio()));
			vo.setStrFechaFin(BitacoraEventosCorporativosUtil.dateToString(evco.getFechaFin()));
			vo.setCronExpression(evco.getCronexpresion());
			listVo.add(vo);
		}
		return listVo;
	}
	
	
	public List<ValidacionesEvcoDTO> convertirValidacionesEntityToDto(List<ValidacionesEvco> listaValidaciones)
	{
		List<ValidacionesEvcoDTO> listaValidacionesDto= new ArrayList<ValidacionesEvcoDTO>();
		
	    for (ValidacionesEvco validacionEvco : listaValidaciones)
		{
			ValidacionesEvcoDTO validacionDto= new ValidacionesEvcoDTO();
			validacionDto.setIdValidacion(validacionEvco.getIdValidacion());
			//validacionesVo.setStrValidacion(consultaEventosCorporativosService.getTipoValidacionById(Long.parseLong(validacionVal)).getNombre());
			validacionDto.setStrOperador(consultaEventosCorporativosDao.getOperadorById(validacionEvco.getOperador().getIdoperador()).getOperador());
			validacionDto.setStrValidacion(consultaEventosCorporativosDao.getTipoValidacionById(validacionEvco.getTipoValidacion().getIdtipoValidacion()).getNombre());
			validacionDto.setIdEventoCorporativo(validacionEvco.getIdEventoCorporativo());
			validacionDto.setValor(validacionEvco.getValor());
			validacionDto.setOperador(validacionEvco.getOperador().getOperador());
			validacionDto.setTipoValidacion(validacionEvco.getTipoValidacion().getNombre());
			validacionDto.setStrNumValidacion(String.valueOf(listaValidacionesDto.size() + 1));
			listaValidacionesDto.add(validacionDto);
		}		
		
		return listaValidacionesDto;
	}
    
	/**
	 * @param cronUtil the cronUtil to set
	 */
	public void setCronUtil(InterpretExpressionCron cronUtil) {
		this.cronUtil = cronUtil;
	}

    /**
     * @param consultaEventosCorporativosDao the consultaEventosCorporativosDao to set
     */
    public void setConsultaEventosCorporativosDao(
            ConsultaEventosCorporativosDao consultaEventosCorporativosDao) {
        this.consultaEventosCorporativosDao = consultaEventosCorporativosDao;
    }
    /**
     * 
     * @param notvo
     * @param idEventoCorporativo
     * @param num
     * @return
     * @throws ParseException
     */
	public NotificacionEventoCorporativo setNotificacionesEventoCorporativo(
			NotificacionesEvcoDTO notvo,
			Long idEventoCorporativo) throws ParseException {
		NotificacionEventoCorporativo notif = new NotificacionEventoCorporativo();
		notif.setCronexpresion(notvo.getPatronCron());
		notif.setFechaInicio(notvo.getFechaInicio());
		notif.setFechaFin(notvo.getFechaFin());
		notif.setIdEventoCorporativo(idEventoCorporativo);
		notif.setTipoNotificacion(notvo.getNumOpcAcc());
		//TODO SOLO PARA PRUEBAS, CAMBIAR LOS CAMPOS POR BOOLEANOS
		notif.setProcesado(Long.parseLong("0"));
		notif.setVigente(Long.parseLong("1"));
		//lista distrib
		if( StringUtils.isAlphanumeric( notvo.getIdListaDIstribucion() )){
			ListaDistribucion listadist = (ListaDistribucion)consultaEventosCorporativosDao.getByPk(ListaDistribucion.class, Long.valueOf(notvo.getIdListaDIstribucion()));
			notif.setListaDistribucion(listadist);
		}
		notif.setMensaje(notvo.getStrTextoNotificacion());
		return notif;
	}
	/**
	 * Validaciones
	 * @param valiVO
	 * @param idEventoCorporativo
	 * @return
	 */
	public ValidacionesEvco setValidacionesEventoCorporativo(
			ValidacionesEvcoDTO valiVO,
			Long idEventoCorporativo) {
		ValidacionesEvco vevco = new ValidacionesEvco();
		vevco.setIdEventoCorporativo(idEventoCorporativo);
		if(StringUtils.isNotEmpty(valiVO.getIdOperador()) && StringUtils.isNumeric(valiVO.getIdOperador())){
			OperadorEvco op = (OperadorEvco)consultaEventosCorporativosDao.getByPk(OperadorEvco.class, 
					Long.valueOf(valiVO.getIdOperador()));
			vevco.setOperador(op);
		}
		if(StringUtils.isNotEmpty(valiVO.getTipoValidacion()) && StringUtils.isNumeric(valiVO.getTipoValidacion())){
			TipoValidacionEvco tval = (TipoValidacionEvco) consultaEventosCorporativosDao.getByPk(TipoValidacionEvco.class, 
					Long.valueOf(valiVO.getTipoValidacion()));
			vevco.setTipoValidacion(tval);
		}
		if(StringUtils.isNotEmpty(valiVO.getStrCantidadCapturada())&& StringUtils.isNumeric(valiVO.getStrCantidadCapturada())){
			vevco.setValor(Long.valueOf(valiVO.getStrCantidadCapturada()));
		}
		return vevco;
	}
	
	/**
	 * modifica un evento corporativo
	 * @param param
	 * @param evco
	 */
	public void updateEventoCorporativo(EventoCorporativoEdicionDTO param,
			EventoCorporativo evco) {
		log.info("Method ::::::: updateEventoCorporativo");        
        TipoDerechoEvCo tipoDerechoEvcoMo = null;
        TipoDerechoEvCo tipoDerechoEvCoMl = null;
        TipoMercado tipoMercado = null;
        TipoEvento tipoEvento = null;        
        Custodio custodio = null;

        //evco.setFechaCreacion(param.getFechaCreacion());
        evco.setFechaRegistro(param.getFechaRegistro());
        evco.setTipoValor(param.getTipoValor());
        evco.setEmisora(param.getEmisora());
        evco.setSerie(param.getSerie());
        evco.setIsin(param.getIsin());

        tipoDerechoEvcoMo = consultaEventosCorporativosDao.findTipoDerechoById(Long.valueOf(param.getTipoDerechoMO()));
        tipoDerechoEvCoMl = consultaEventosCorporativosDao.findTipoDerechoById(Long.valueOf(param.getTipoDerechoML()));
        evco.setTipoDerechoMO(tipoDerechoEvcoMo);
        evco.setTipoDerechoML(tipoDerechoEvCoMl);

        tipoMercado = consultaEventosCorporativosDao.findTipoMercadoById(Long.valueOf(param.getTipoMercado()));
        evco.setTipoMercado(tipoMercado);

        tipoEvento = consultaEventosCorporativosDao.findTipoEventoById(Long.valueOf(param.getTipoEvento()));
        evco.setTipoEvento(tipoEvento);
        
       
        evco.setFechaIndeval(param.getFechaIndeval());
        evco.setFechaCliente(param.getFechaCliente());
        evco.setFechaPago(param.getFechaPago());
        evco.setFechaRegistro(param.getFechaRegistro());
        //para que flote a los primeros lugares se elimina cuando cambia de estado
        evco.setPrioridad(PRIORIDAD_EDICION);
        //que se muestre la leyenda de actualizado se elimina cuando cambia de estado
        evco.setActualizado(true);

        custodio = consultaEventosCorporativosDao.findCustodioById(Long.valueOf(param.getCustodio()));
        evco.setCustodio(custodio);
        evco.setFechaActualizacion(Calendar.getInstance().getTime());        		
	}

	public void updateOpcionesEventoCorporativo(
			OpcionesEventoCorporativo opcionesEventoCorporativo,
			OpcionesDTO vo, EventoCorporativo evco) throws ParseException {
		log.debug("Method :::::::::: updateOpcionesEventoCorporativo");        
        //seteo los valores al objeto        
        opcionesEventoCorporativo.setIdEventoCorporativo(evco.getIdEventoCorporativo());
        opcionesEventoCorporativo.setCuerpo(vo.getCuerpoOpcion());
        opcionesEventoCorporativo.setFechaCliente(vo.getFechaClienteOpcion());
        opcionesEventoCorporativo.setFechaIndeval(vo.getFechaIndevalOpcion());
        opcionesEventoCorporativo.setFechaPago(vo.getFechaPagoOpcion());
        opcionesEventoCorporativo.setOpcDefault(new Long(vo.isDefault()?"1":"0"));
        opcionesEventoCorporativo.setBorrado(false);	
	}

	public void updateNotasEventoCorporativo(
			NotasEventoCorporativo notasEventoCorporativo, NotasDTO vo,
			Long idEventoCorporativo) {
		log.debug("Method ::::::::: setNotasEventoCorporativo");    	
    	notasEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
    	notasEventoCorporativo.setCuerpo(vo.getCuerpoNota());		
	}

	public AdjuntosEventoCorporativo setAdjuntosEventoCorporativoEdicion(
			ArchivosAdjuntosEvcoDTO adjuntoDto, Long idEventoCorporativo) {
		log.debug("Method :::::::::: setAdjuntosEventoCorporativo");
    	AdjuntosEventoCorporativo adjuntosEventoCorporativo = null;
    	
    	//seteo los valores al objeto
    	adjuntosEventoCorporativo = new AdjuntosEventoCorporativo();
    	try {
    		adjuntosEventoCorporativo.setIdEventoCorporativo(idEventoCorporativo);
			adjuntosEventoCorporativo.setArchivo(adjuntoDto.getArchivoAdjunto());
			adjuntosEventoCorporativo.setDescripcion(adjuntoDto.getDescripcion());
			adjuntosEventoCorporativo.setBorrado(adjuntoDto.getBorrado());
		} catch (Exception e) {
			throw new BusinessException("Error al modificar archivo adjunto ");
		}
    	return adjuntosEventoCorporativo;
	}

	public ValidacionesEvco setValidacionesEventoCorporativoEdicion(
			ValidacionesEvcoDTO valdto, Long idEventoCorporativo) {
		ValidacionesEvco vevco = new ValidacionesEvco();
		vevco.setIdEventoCorporativo(idEventoCorporativo);
		if(StringUtils.isNotEmpty(valdto.getOperador()) && StringUtils.isNumeric(valdto.getOperador())){
			OperadorEvco op = (OperadorEvco)consultaEventosCorporativosDao.getByPk(OperadorEvco.class, 
					Long.valueOf(valdto.getOperador()));
			vevco.setOperador(op);
		}
		if(valdto.getIdValidacion() != null){
			TipoValidacionEvco tval = (TipoValidacionEvco) consultaEventosCorporativosDao.getByPk(TipoValidacionEvco.class, 
					valdto.getIdValidacion());
			vevco.setTipoValidacion(tval);
		}
		if(valdto.getValor() != null){
			vevco.setValor(valdto.getValor());
		}
		return vevco;		
	}

	public void updateNotificacion(NotificacionEventoCorporativo notif,
			NotificacionesEvcoDTO vo) throws ParseException {
		if(vo.getPatronCron() != null){
			notif.setCronexpresion(vo.getPatronCron());
		}
		notif.setFechaInicio(vo.getFechaInicio());
		notif.setFechaFin(vo.getFechaFin());		
		notif.setTipoNotificacion(vo.getNumOpcAcc());
		//TODO SOLO PARA PRUEBAS, CAMBIAR LOS CAMPOS POR BOOLEANOS
		notif.setProcesado(Long.parseLong("0"));
		notif.setVigente(Long.parseLong("1"));
		//lista distrib
		if( StringUtils.isAlphanumeric( vo.getIdListaDIstribucion() )){
			ListaDistribucion listadist = (ListaDistribucion)consultaEventosCorporativosDao.getByPk(ListaDistribucion.class, Long.valueOf(vo.getIdListaDIstribucion()));
			notif.setListaDistribucion(listadist);
		}
		notif.setMensaje(vo.getStrTextoNotificacion());		
	}
}
