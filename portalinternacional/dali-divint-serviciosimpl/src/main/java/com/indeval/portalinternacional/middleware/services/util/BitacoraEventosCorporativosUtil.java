/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AtributosEmbebidosEvcoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraXMLEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
/**
 * 
 *
 */
public class BitacoraEventosCorporativosUtil {
	
	private ConsultaEventosCorporativosDao consultaEventosCorporativosDao;
	private DTOAssembleEvCorp dtoAssemblerEvCorp;
	private final static Logger log = LoggerFactory.getLogger(BitacoraEventosCorporativosUtil.class); 
	private CompararObjetosEventosCorporativos compararaObjetos;
	
	public BitacoraEventosCorporativosUtil() {
		log.info("INICIALIZAR:"+BitacoraEventosCorporativosUtil.class);
		compararaObjetos= new CompararObjetosEventosCorporativos();
	}
	
	
	
	public EventoCorporativoFullVistaDTO leerListaXMLBitacoraEvco(List<BitacoraCambiosEvco> listBitacora){		
		XStream xs = XmlUtils.getXStreamCData(new String[]{"cuerpo","piePagina","descripcion"});
		Annotations.configureAliases(xs, BitacoraXMLEvco.class,OpcionesDTO.class,NotasDTO.class,ArchivosAdjuntosEvcoDTO.class);
	
		List<BitacoraXMLEvco> bitacoraCompleta = new ArrayList<BitacoraXMLEvco>();
		for (BitacoraCambiosEvco bitacora : listBitacora){							
			BitacoraXMLEvco bitacoraEvco = new BitacoraXMLEvco();	
			try{

				bitacoraEvco = (BitacoraXMLEvco) xs.fromXML(bitacora.getFormatoOriginal());	
				bitacoraCompleta.add(bitacoraEvco);
				
			}catch(Exception e)
			{
				log.info("HUBO UN ERROR AL CONVERTIR LOS XML En la bitacora:"  + e);
			}

		}
	

		//ultima modificacion obteniendo datos generales.
		EventoCorporativoFullVistaDTO bitacoraDTO= new EventoCorporativoFullVistaDTO();
				
		if(bitacoraCompleta != null && bitacoraCompleta.size() > 0)
		{
			//lleno el objeto que va hacia la vista
			bitacoraDTO=getEventoCorporativoFullDTO(bitacoraCompleta);
		}
		

		return bitacoraDTO;
	}
	
	
	
	public EventoCorporativoFullVistaDTO getEventoCorporativoFullDTO(List<BitacoraXMLEvco> bitacoraListPara)
	{
		EventoCorporativoFullVistaDTO bitacoraDTO= new EventoCorporativoFullVistaDTO();
			
		BitacoraXMLEvco bitacoraUltimoBean=	bitacoraListPara.get((bitacoraListPara.size()-1));
				
		bitacoraDTO=getAtributosGeneralesEvco(bitacoraUltimoBean);			
		//bitacoraDTO.setCuerpoEnVistaLista(compararaObjetos.getDiferenciasBitacoraEvco(bitacoraListPara,getAtributosGenericosEvco( bitacoraUltimoBean)));
		bitacoraDTO.setCuerpoEnVistaLista(compararaObjetos.getDiferenciasBitacoraEvco(bitacoraListPara,getAtributosGenericosEvco( bitacoraListPara.get(0))));
				
		return bitacoraDTO;
	}

	
	public static Date getDateOfString(String fecha)
	{
		if(StringUtils.isEmpty(fecha)){
			return null;
		}
		SimpleDateFormat formatterXML = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat formatterResumen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = null;

		try {	 
			 date = formatterXML.parse(fecha);	
		} catch (ParseException e) {
			if(fecha == null)
			{
				date=null;
			}
			else
			{
				 try {
					date = formatterResumen.parse(fecha);
				} catch (ParseException e1) {
					
					log.debug("Ocurrio un error:","No se puede parsear la fecha "+fecha);
				}
			}
		}
		
		
		return date;
	}
	
	public static String dateToString(Date fecha){		
		if(fecha != null){
			DateFormat fechaFormateada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			return fechaFormateada.format(fecha);
		}else{
			return null;
		}
	}
	

	
	
	
	
	/*Metodo para convertir objeto a XML
	 * 
	 * 
	 * 
	 */
	public BitacoraXMLEvco crearBitacoraEvco(EventoCorporativo evco, 
									CuerpoEventoCorporativo cuerpoParam,
									List<NotasEventoCorporativo> notaParam,
									List<OpcionesEventoCorporativo> opcParam,
									List<AdjuntosEventoCorporativo> adjParam
									)
	{

        BitacoraXMLEvco bitacoraXmlEvco= new BitacoraXMLEvco();
    	List<NotasEventoCorporativo> notasList			= notaParam;
    	List<OpcionesEventoCorporativo> opcionesList	= opcParam;
    	List<AdjuntosEventoCorporativo> adjuntosList	= adjParam;
    	CuerpoEventoCorporativo cuerpo= cuerpoParam;

        bitacoraXmlEvco.setIdEventoCorporativo(evco.getIdEventoCorporativo());
        bitacoraXmlEvco.setFechaCreacion(evco.getFechaCreacion()+"");
        bitacoraXmlEvco.setFechaRegistro(evco.getFechaRegistro()+"");
        bitacoraXmlEvco.setTipoValor(evco.getTipoValor());
        bitacoraXmlEvco.setEmisora(evco.getEmisora());
        bitacoraXmlEvco.setSerie(evco.getSerie());
        bitacoraXmlEvco.setIsin(evco.getIsin());
        bitacoraXmlEvco.setFechaIndeval(evco.getFechaIndeval()+"");
        bitacoraXmlEvco.setFechaCliente(evco.getFechaCliente()+"");
        bitacoraXmlEvco.setFechaPago(evco.getFechaPago()+"");
        bitacoraXmlEvco.setPrioridad(evco.getPrioridad());		     
        bitacoraXmlEvco.setFechaActualizacion(evco.getFechaActualizacion());

			bitacoraXmlEvco.setTipoDerechoMO(evco.getTipoDerechoMO());	

			bitacoraXmlEvco.setTipoDerechoML(evco.getTipoDerechoML());	

	    	bitacoraXmlEvco.setTipoMercado(evco.getTipoMercado());	
	
	    	bitacoraXmlEvco.setTipoEvento(evco.getTipoEvento());
    	
		    bitacoraXmlEvco.setEstado(evco.getEstado());

		    bitacoraXmlEvco.setCustodio(dtoAssemblerEvCorp.convertirCustodioEntitytoDto(evco.getCustodio()));		  

		 List<NotasDTO> notasVec = null;
		 List<OpcionesDTO> opcionesVec = null;			
		 List<ArchivosAdjuntosEvcoDTO> archivosVec = null;	
	    
	    
	    if(cuerpo != null)
	    {
		    bitacoraXmlEvco.setCuerpoEvento(cuerpo);
	    }
	    else
	    {
	    	bitacoraXmlEvco.setCuerpoEvento(new CuerpoEventoCorporativo());
	    }
	    
	    if(notasList != null)
	    {
	    	notasVec= new ArrayList<NotasDTO>();	       
	        for (NotasEventoCorporativo nota : notasList){
	        	if(nota != null){
		        	NotasDTO notaDto= new NotasDTO();
		        	notaDto.setId(nota.getIdNota());
		        	notaDto.setDescripcion(nota.getCuerpo());
		        	notasVec.add(notaDto);
	        	}
	        	
			}
		    bitacoraXmlEvco.setNotasVec(notasVec);
	    }
	    
	    // bitacorar opciones
	    if(opcionesList != null){
	    	opcionesVec= new ArrayList<OpcionesDTO>();
	        int cont=0;
	        for (OpcionesEventoCorporativo opcion : opcionesList)
	        {
	        	if(opcion != null && !opcion.getBorrado()){
		        	OpcionesDTO opcionDTO = new OpcionesDTO();
		        	opcionDTO.setBorradoLogico(opcion.getBorrado());
		        	opcionDTO.setIdOpcion(opcion.getIdOpcion());
		        	opcionDTO.setIdEventoCorporativo(opcion.getIdEventoCorporativo());
		        	opcionDTO.setFechaPago(opcion.getFechaPago()+"");
		        	opcionDTO.setFechaIndeval(opcion.getFechaIndeval()+"");
		        	opcionDTO.setFechaCliente(opcion.getFechaCliente()+"");
		        	opcionDTO.setCuerpo(opcion.getCuerpo());
		        	opcionDTO.setOpcDefault(opcion.getOpcDefault());
		        	opcionesVec.add(opcionDTO);
		        	cont++;
	        	}
			}
	        if(cont > 0){
	        	bitacoraXmlEvco.setOpcionesVec(opcionesVec);
	        }
	    }
	    
	    //bitacorar adjuntos
	    if(adjuntosList != null)
	    {
	    	archivosVec= new ArrayList<ArchivosAdjuntosEvcoDTO>();
	        int cont=0;
	        for (AdjuntosEventoCorporativo adjunto : adjuntosList)
	        {	
	        	if(!adjunto.getBorrado().equals("1")){
		        	ArchivosAdjuntosEvcoDTO adjuntoDto= new ArchivosAdjuntosEvcoDTO();
		        	
		        	adjuntoDto.setBorrado(adjunto.getBorrado());
		        	adjuntoDto.setDescripcion(adjunto.getDescripcion());
		        	adjuntoDto.setIdAdjuntos(adjunto.getIdAdjuntos());
		        	adjuntoDto.setIdEventoCorporativo(adjunto.getIdEventoCorporativo());	
		        	archivosVec.add(adjuntoDto);
		        	cont++;
	        	}
			}
	        if(cont > 0){
	        	bitacoraXmlEvco.setArchivosVec(archivosVec);
	        }
	    }
	    
	    return bitacoraXmlEvco;	
	}


	
	public void insertarBitacora(BitacoraXMLEvco bitacoraXmlEvco,String usuario, Long estado, boolean invisible )
	{
				
		BitacoraCambiosEvco bitacoraSave= new BitacoraCambiosEvco();
		bitacoraSave.setFechaModificacion(new Date());
		bitacoraSave.setFormatoOriginal(createXml(bitacoraXmlEvco));
		if(invisible){
			bitacoraSave.setVisible(new Long(1));
		}else{
			bitacoraSave.setVisible(new Long(0));
		}
		bitacoraSave.setUsuario(usuario);
		bitacoraSave.setIdEventoCorporativo(bitacoraXmlEvco.getIdEventoCorporativo());

		
		Long edoRegistrado=new Long(1);
		if(estado.equals(edoRegistrado))
		{
				
			List<BitacoraCambiosEvco> bitacoraList	=consultaEventosCorporativosDao.getBitacoraControlCambios(bitacoraXmlEvco.getIdEventoCorporativo());
			
			if(bitacoraList != null && bitacoraList.size() > 0)
			{				

				
					BitacoraCambiosEvco bitacoraRegUnico=bitacoraList.get(0);
					bitacoraRegUnico.setFormatoOriginal(createXml(bitacoraXmlEvco));
					bitacoraRegUnico.setUsuario(usuario);
					bitacoraRegUnico.setFechaModificacion(new Date());

					consultaEventosCorporativosDao.saveWithFlush(bitacoraRegUnico);
				
			}
			else
			{	
				consultaEventosCorporativosDao.saveWithFlush(bitacoraSave);
			}
		
		}
		else
		{
			consultaEventosCorporativosDao.saveWithFlush(bitacoraSave);
		}

	}

	
	
	public String createXml(BitacoraXMLEvco bitacoraEvco)
	{
		XStream xs = XmlUtils.getXStreamCData(new String[]{"cuerpo","piePagina","descripcion","piePagina"});							
		Annotations.configureAliases(xs, BitacoraXMLEvco.class);
		Annotations.configureAliases(xs, OpcionesDTO.class);
		Annotations.configureAliases(xs, NotasDTO.class);
		Annotations.configureAliases(xs, ArchivosAdjuntosEvcoDTO.class);
		
		String xmlEvco=xs.toXML(bitacoraEvco).toString();
		
		
		return xmlEvco;
	}
	

	
	public EventoCorporativoFullVistaDTO getAtributosGeneralesEvco(BitacoraXMLEvco bitacoraUltimoBean)
	{
		EventoCorporativoFullVistaDTO bitacoraDTO= new EventoCorporativoFullVistaDTO();
		bitacoraDTO.setIdEventoCorporativo(bitacoraUltimoBean.getIdEventoCorporativo().toString());
		bitacoraDTO.setFechaCreacion(getDateOfString(bitacoraUltimoBean.getFechaCreacion()));
		bitacoraDTO.setFechaRegistro(getDateOfString(bitacoraUltimoBean.getFechaRegistro()));
		bitacoraDTO.setTipoValor(bitacoraUltimoBean.getTipoValor());
		bitacoraDTO.setEmisora(bitacoraUltimoBean.getEmisora());
		bitacoraDTO.setSerie(bitacoraUltimoBean.getSerie());
		bitacoraDTO.setIsin(bitacoraUltimoBean.getIsin());
		bitacoraDTO.setTipoDerechoMO( bitacoraUltimoBean.getTipoDerechoMO()==null?"":bitacoraUltimoBean.getTipoDerechoMO().getTipoDerecho());
		bitacoraDTO.setTipoDerechoML( bitacoraUltimoBean.getTipoDerechoML()==null?"":bitacoraUltimoBean.getTipoDerechoML().getTipoDerecho());
		bitacoraDTO.setFechaActualizacion(bitacoraUltimoBean.getFechaActualizacion());
		bitacoraDTO.setTipoMercado(bitacoraUltimoBean.getTipoMercado().getDescripcion());
						
		
		bitacoraDTO.setFechaCliente(getDateOfString(bitacoraUltimoBean.getFechaCliente()));
		bitacoraDTO.setFechaIndeval(getDateOfString(bitacoraUltimoBean.getFechaIndeval()));
		bitacoraDTO.setFechaPago(getDateOfString(bitacoraUltimoBean.getFechaPago()));
						
		bitacoraDTO.setCustodio(bitacoraUltimoBean.getCustodio().getDescripcion());
		bitacoraDTO.setTipoEvento(bitacoraUltimoBean.getTipoEvento().getDescripcion());
		return bitacoraDTO;
	}
	
	
	
	public AtributosEmbebidosEvcoDto getAtributosGenericosEvco(BitacoraXMLEvco bitacoraBean)
	{
		AtributosEmbebidosEvcoDto bitacoraVariante= new AtributosEmbebidosEvcoDto();
		bitacoraVariante.setCuerpoEventoHtml(bitacoraBean.getCuerpoEvento().getCuerpo());
		bitacoraVariante.setPiePaginaHtml(bitacoraBean.getCuerpoEvento().getPiePagina());			
		bitacoraVariante.setFechaActualizacion(bitacoraBean.getFechaActualizacion());
		if(bitacoraBean.getOpcionesVec() != null){
			for (OpcionesDTO opcionesDto : bitacoraBean.getOpcionesVec()){
				if(opcionesDto != null){
					OpcionesEventoCorporativo opcionesEvco= new OpcionesEventoCorporativo();					
					opcionesEvco.setCuerpo(opcionesDto.getCuerpo());
					opcionesEvco.setFechaCliente(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDto.getFechaCliente()));
					opcionesEvco.setFechaIndeval(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDto.getFechaIndeval()));
					opcionesEvco.setFechaPago(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDto.getFechaPago()));
					opcionesEvco.setOpcDefault(opcionesDto.getOpcDefault());
					bitacoraVariante.getOpcionesList().add(opcionesEvco);
				}
			}
		}
		if(bitacoraBean.getNotasVec() != null){
			for (NotasDTO notasEvco : bitacoraBean.getNotasVec()){
				if(notasEvco != null){
					bitacoraVariante.getNotasList().add(notasEvco.getDescripcion());
				}
			} 
		}
		if(bitacoraBean.getArchivosVec() != null){
			for (ArchivosAdjuntosEvcoDTO archivosEvco : bitacoraBean.getArchivosVec()){	
				if(archivosEvco != null){		
					AdjuntosEventoCorporativo adjunto= consultaEventosCorporativosDao.getArchivoAdjuntoPorId(archivosEvco.getIdAdjuntos());
					if(adjunto != null){
						bitacoraVariante.getArchivosAdjuntos().add(dtoAssemblerEvCorp.convertirAdjuntoEntityToDto(adjunto));
					}
				}
			}
		}
		return bitacoraVariante;
	}
	
	
	
	/**
	 * @param consultaEventosCorporativosDao the consultaEventosCorporativosDao to set
	 */
	public void setConsultaEventosCorporativosDao(
			ConsultaEventosCorporativosDao consultaEventosCorporativosDao) {
		this.consultaEventosCorporativosDao = consultaEventosCorporativosDao;
	}

    /**
     * @param dtoAssemblerEvCorp the dtoAssemblerEvCorp to set
     */
    public void setDtoAssemblerEvCorp(DTOAssembleEvCorp dtoAssemblerEvCorp) {
        this.dtoAssemblerEvCorp = dtoAssemblerEvCorp;
    }	
}
