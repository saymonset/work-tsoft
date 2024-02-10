package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EnvioLegislacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.AssetManagerEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioHistoricoDerechosCapitalesVo;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DescFechasAdIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EmisionAssetManagerVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioSicXML;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.SubTipoDerechoIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoIntRep;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioHistoricoDerechosExterno;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VEmisionesCalendario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EstadoEnvioMoi;
import com.indeval.portalinternacional.persistence.dao.capitales.CapitalesDistribucionDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

public class CapitalesDistribucionServiceImpl implements CapitalesDistribucionService {
		
	private static final Logger log = LoggerFactory.getLogger(CapitalesDistribucionServiceImpl.class);
	
	/**
	 * Dao para el manejo de divisas
	 */
	private DivisaDao divisaDao;
	
	/**
	 * Dao para el manejo de datos requeridos en Capitales de Distribuci&oacute;n
	 */
	private CapitalesDistribucionDao capitalesDistribucionDao;
	
	/**
	 * Dao para el manejo de las Bovedas
	 */
	private BovedaDao bovedaDao;
	
	/**
	 * JMS para notificaci&oacute;n al sistema MOI del envio legislaci&oacute;n SIC
	 */
	private JmsTemplate jmsTemplateAutorizaLegSic;	
	
	/**
	 * Tipo Valor permitidos en la consulta de Derechos
	 */
	private String tipoValorLegislacionSic;
	
	/**
	 * Mapa con los tipo valor permitidos en calendario derechos legislacion SIC
	 */
	private Map<String,String> tipoValorLegislacionSicMap;
	
	private String tagsEliminacionSic;
	
	private Map<String,String> tagsEliminacionSicMap;
	
	/**
	 * 
	 */
	private static Object BLOQUE_SINCRONO = String.class;
	
	private static Object BLOQUE_SINCRONO_NARRATIVA = String.class;
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaCalendarioCapitales(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, java.lang.String)
	 */
	public PaginaVO consultaCalendarioCapitales(final ConsultaCapitalesParamsTO params, final PaginaVO paginaVO) {
		PaginaVO pagina = capitalesDistribucionDao.consultaCalendarioCapitales(params, paginaVO);
	
		if (params.isXLS() && pagina.getTotalRegistros() > 0) {
			List<VCalendarioDerechoIntRep> listaReportes = (List<VCalendarioDerechoIntRep>) pagina.getRegistros();
			for (VCalendarioDerechoIntRep reporte : listaReportes) {
				if (reporte.getIdCalendario() != null) {
					String narrativa = capitalesDistribucionDao.getNarrativasReporte((reporte.getIdCalendario()));
					reporte.setNarrativa(narrativa);
				}
			}
		}
		return pagina;
	}
    
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaHistoricoCapitales(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoParamsTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
	public PaginaVO consultaHistoricoCapitales(final ConsultaCapitalesParamsTO params, final PaginaVO paginaVO, String origen) {
		if("ADMIN".equals(origen)){
		return capitalesDistribucionDao.consultaHistoricoCapitales(params, paginaVO);
		}
		else{
			PaginaVO pagina = capitalesDistribucionDao.consultaHistoricoCapitalesExterno(params, paginaVO);
			if (params.isXLS() && pagina.getTotalRegistros() > 0) {
				List<VCalendarioHistoricoDerechosExterno> listaReportes = (List<VCalendarioHistoricoDerechosExterno>) pagina.getRegistros();
				for (VCalendarioHistoricoDerechosExterno reporte : listaReportes) {
					if (reporte.getIdCalendario() != null) {
						String narrativa = capitalesDistribucionDao.getNarrativasReporte((reporte.getIdCalendario()));
						reporte.setNarrativa(narrativa);
					}
				}
			}
			return pagina; 
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#findAllDivisas()
	 */
	public Divisa[] findAllDivisas() throws BusinessException{
		return divisaDao.findDivisas();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#getBovedas()
	 */
	public Boveda[] getBovedas() throws BusinessException{
		return bovedaDao.findBovedas(null,null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#obtieneEstadosDerechoIntSic()
	 */
	public List<EstadoMensajeIntSic> obtieneEstadosDerechoIntSic()throws BusinessException {
		return capitalesDistribucionDao.getCatalogoEstadoMensajeIntSic();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#obtieneDestinatariosIntSicByEstado(java.lang.Long)
	 */
	public List<DestinatarioIntSic> obtieneDestinatariosIntSicByEstado(Long estado) throws BusinessException {
		return capitalesDistribucionDao.getDestinatariosIntSicByEstado(estado);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaAutorizacionesCalendarioCapitales(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaAutorizacionesCalendarioCapitales(EnvioLegislacionSicDTO filtroConsulta,PaginaVO paginaVO) throws BusinessException {
		return capitalesDistribucionDao.consultaAutorizacionesCalendarioCapitales(filtroConsulta,paginaVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#procesaEnvioLegMoi(java.util.List)
	 */
	public void procesaEnvioLegMoi(List<EnvioLegislacionSicDTO> listaEnvios)throws BusinessException{
		if(listaEnvios != null && !listaEnvios.isEmpty()){
			List<EnvioLegislacionSic> listaEnvioSic = new ArrayList<EnvioLegislacionSic>();
			for(EnvioLegislacionSicDTO envioDto:listaEnvios){				
				final EnvioLegislacionSic envio = new EnvioLegislacionSic();
				envio.setIdHistoricoSic(envioDto.getIdHistorico());
				envio.setIdCalendario(envioDto.getIdCalendario());
				envio.setDestinatario(envioDto.getDestinatario());
				envio.setUsuario(envioDto.getUsuario());
				envio.setFechaCreacion(new Date());				
				envio.setEstadoEnvioMoi(EstadoEnvioMoi.POR_AUTORIZAR.ordinal());
				
				listaEnvioSic.add(envio);
			}
			capitalesDistribucionDao.guardaEnvioLegMoi(listaEnvioSic);
		}
			
				
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#procesaAutorizacionEnvioMoiSic(java.util.List)
	 */
	public List<Long>  procesaAutorizacionEnvioMoiSic(List<EnvioLegislacionSicDTO> listaEnvios)throws BusinessException{
		List<Long> msjsProcesadosAnterioridad = Collections.emptyList();
		if(listaEnvios != null && !listaEnvios.isEmpty()){			
			msjsProcesadosAnterioridad = new ArrayList<Long>();
			synchronized (BLOQUE_SINCRONO) {				
				for(EnvioLegislacionSicDTO envioDto:listaEnvios){									
					EnvioLegislacionSic envioLegislacionSic=null;
					if(envioDto.getEstado() != null && 
							(envioDto.getEstado().compareTo(EstadoEnvioMoi.AUTORIZADO.ordinal()) == 0 || 
								envioDto.getEstado().compareTo(EstadoEnvioMoi.CANCELADO.ordinal()) == 0)){
						EstadoEnvioMoi estadoDto = EstadoEnvioMoi.values()[envioDto.getEstado()];
						envioLegislacionSic = capitalesDistribucionDao.getEnvioLegislacionSicById(envioDto.getIdEnvio());
						if(validaEstadoEnvioLegislacion(envioLegislacionSic)){									
							if(envioDto.getEstado().compareTo(EstadoEnvioMoi.AUTORIZADO.ordinal()) == 0){							
								Integer narrativas = actualizaOrigenNarrativa(envioLegislacionSic.getIdHistoricoSic());
								log.info("Narrativas actualizadas: "+narrativas);
								enviaMensajeLegislacionMoiSic(envioLegislacionSic,envioDto.getUsuarioAutoriza());										
							}
								actualizaRegistroEnvioSic(envioLegislacionSic,estadoDto,envioDto.getUsuarioAutoriza());
						}else if(envioLegislacionSic != null){							
								msjsProcesadosAnterioridad.add(envioLegislacionSic.getIdHistoricoSic());
						}										
					}				
				}
				
			}
		}
		return msjsProcesadosAnterioridad;
	}
	
	/**
	 * Actualiza a Enviada(Origen = E) la narrativa que sea ingresada por usuario (Origen = U)
	 * @param idHistorico
	 * @return
	 * @throws BusinessException
	 */
	private Integer actualizaOrigenNarrativa(Long idHistorico)throws BusinessException{
		int regreso = 0;
		regreso = capitalesDistribucionDao.actualizaOrigenNarrativaSicByIdHistorico(idHistorico);
		capitalesDistribucionDao.flush();		
		return regreso; 
	}
	
	/**
	 * valida que el registro no sea nulo y que se encuentre en estado Por Autorizar
	 * @param envioLegislacionSic
	 * @return
	 */
	private boolean validaEstadoEnvioLegislacion(EnvioLegislacionSic envioLegislacionSic){
		return (envioLegislacionSic != null && Integer.valueOf(EstadoEnvioMoi.POR_AUTORIZAR.ordinal()).compareTo(envioLegislacionSic.getEstadoEnvioMoi())==0);
	}
	
	/**
	 * Actualiza el registro EnvioLegislacionSic
	 * @param envioLegislacionSic
	 * @param nuevoEstado
	 * @param usuarioSession
	 */
	private void actualizaRegistroEnvioSic(EnvioLegislacionSic envioLegislacionSic,EstadoEnvioMoi nuevoEstado,String usuarioSession){		
		envioLegislacionSic.setUsuarioAutoriza(usuarioSession);
		envioLegislacionSic.setFechaActualizacion(new Date());
		envioLegislacionSic.setEstadoEnvioMoi(nuevoEstado.ordinal());
		capitalesDistribucionDao.update(envioLegislacionSic);
		
	}
	
	/** 
	 * Envia el mensaje EnvioSicXML al sistema MOI
	 * @param envioLegislacionSic
	 * @param usuarioSession
	 */
	private void enviaMensajeLegislacionMoiSic(EnvioLegislacionSic envioLegislacionSic, String usuarioSession){
		XStream xstream = new XStream();		
        Annotations.configureAliases(xstream, EnvioSicXML.class);
        
        EnvioSicXML envioSic = new EnvioSicXML();
        envioSic.setDestinatarios(envioLegislacionSic.getDestinatario());
        envioSic.setIdCalendario(envioLegislacionSic.getIdCalendario().toString());
        envioSic.setIdHistorico(envioLegislacionSic.getIdHistoricoSic().toString());
        envioSic.setUsuario(usuarioSession);
        
        final String msgLegislacionSicXml = xstream.toXML(envioSic);
        
        this.jmsTemplateAutorizaLegSic.send(new MessageCreator() {
            public Message createMessage(final Session session) throws JMSException {
                final Message msg = session.createTextMessage(msgLegislacionSicXml);
                return msg;
            }
        });        
	}
	
	/**
	 * @return the tipoValorLegislacionSicMap
	 */
	public Map<String, String> getTipoValorLegislacionSicMap() {
		if(tipoValorLegislacionSicMap == null){
			Map<String,String> mapa = new HashMap<String,String>();
			String[] tiposValor = tipoValorLegislacionSic.split(",");
			for(String tv : tiposValor){
				mapa.put(tv, tv);
			}
			this.tipoValorLegislacionSicMap = mapa;
		}
		
		return tipoValorLegislacionSicMap;
	}
	

	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	public void setCapitalesDistribucionDao(CapitalesDistribucionDao capitalesDistribucionDao) {
		this.capitalesDistribucionDao = capitalesDistribucionDao;
	}

	public void setBovedaDao(BovedaDao bovedaDao) {
		this.bovedaDao = bovedaDao;
	}

	/**
	 * @param jmsTemplateAutorizaLegSic the jmsTemplateAutorizaLegSic to set
	 */
	public void setJmsTemplateAutorizaLegSic(JmsTemplate jmsTemplateAutorizaLegSic) {
		this.jmsTemplateAutorizaLegSic = jmsTemplateAutorizaLegSic;
	}
	
	/**
	  * Devuelve un long  del idHistorico Maximo de un idCalendario
	  * @param idCalendario
	  * @throws BusinessException
	  */
	public Long consultaUltimoHistoricoCalendario(Long idCalendario) throws BusinessException {
		return capitalesDistribucionDao.consultaUltimoHistoricoCalendario(idCalendario);
		
	}

	/**
	 * @return the tipoValorLegislacionSic
	 */
	public String getTipoValorLegislacionSic() {
		return tipoValorLegislacionSic;
	}

	/**
	 * @param tipoValorLegislacionSic the tipoValorLegislacionSic to set
	 */
	public void setTipoValorLegislacionSic(String tipoValorLegislacionSic) {
		this.tipoValorLegislacionSic = tipoValorLegislacionSic;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaEnviadosCalendarioCapitales(com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEnviadosCalendarioCapitales(EnvioCapitalesParamsTO params, PaginaVO paginaVO) throws BusinessException {
		return capitalesDistribucionDao.consultaEnviadosCalendarioCapitales(params, paginaVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaEnviosCalendario(boolean, java.lang.Long, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEnviosCalendario(boolean bandera, Long id, PaginaVO paginaVo){
		 return capitalesDistribucionDao.consultaEnviosCalendario(bandera, id, paginaVo);
	 }
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaNarrativasForIdCalendarioOrIdHistorico(java.lang.Long, java.lang.Boolean)
	 */
	public List<NarrativaCapitalesVO> consultaNarrativasForIdCalendarioOrIdHistorico(Long id,Boolean isCalendario)throws BusinessException{
		return capitalesDistribucionDao.getNarrativasForIdCalendarioOrIdHist(id, isCalendario);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#guardarNarrrativa(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO)
	 */
	public void guardarNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException{
		if(nuevaNarrativa != null){
			synchronized(BLOQUE_SINCRONO_NARRATIVA){
				Long idTemp = this.capitalesDistribucionDao.getIdNarrativaByIdHist(nuevaNarrativa.getFolioMensaje());
				if(idTemp != null){
					throw new BusinessException("Ya existe una narrativa ingresada por el usuario.");
				}
				NarrativaCapitales narrativa = getConvertNarrativa(nuevaNarrativa,Boolean.TRUE);
				this.capitalesDistribucionDao.save(narrativa);
			}			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#actualizaNarrrativa(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO)
	 */
	public void actualizaNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException{
		if(nuevaNarrativa != null){
			NarrativaCapitales narrativa = getConvertNarrativa(nuevaNarrativa,Boolean.FALSE);						
			this.capitalesDistribucionDao.update(narrativa);			
		}
	}
	
	/**
	 * convierte un objeto NarrativaCapitalesVO a una entidad NarrativaCapitales
	 * @param nuevaNarrativa
	 * @param isNuevo
	 * @return
	 */
	private NarrativaCapitales getConvertNarrativa(NarrativaCapitalesVO nuevaNarrativa,boolean isNuevo){
		NarrativaCapitales narrativaEntity = new NarrativaCapitales();				
		narrativaEntity.setIdCalendario(nuevaNarrativa.getFolioIndeval());
		narrativaEntity.setIdHistorico(nuevaNarrativa.getFolioMensaje());
		narrativaEntity.setNarrativa(nuevaNarrativa.getNarrativa());
		narrativaEntity.setOrigen("U");
		narrativaEntity.setUsuario(nuevaNarrativa.getUsuario());;
		narrativaEntity.setId(isNuevo ? null : nuevaNarrativa.getIdNarrativa());
			
		return narrativaEntity;
	}
	
	/**
	 * Regresa un NarrativaCapitalesVO filtrando por idHistorico
	 * @param idHistorico
	 * @return
	 * @throws BusinessException
	 */
	public NarrativaCapitalesVO getCalendarioHistoricoById(Long idHistorico)throws BusinessException{
		CalendarioHistoricoDerechosCapitalesVo historico = this.capitalesDistribucionDao.getCalendarioHistoricoVoById(idHistorico);		
		NarrativaCapitalesVO narrativa = null;
		if(historico!=null){
			narrativa = new NarrativaCapitalesVO();
			narrativa.setFolioIndeval(historico.getIdCalendario());
			narrativa.setFolioMensaje(historico.getId());			
		}
		return narrativa;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaEmisionesCalendarioCapitales(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEmisionesCalendarioCapitales(ConsultaEmisionesCalendarioParamsTO params, PaginaVO paginaVo) throws BusinessException {
		return capitalesDistribucionDao.consultaEmisionesCalendarioCapitales(params, paginaVo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaEmisionPorId(java.lang.Long)
	 */
	public VEmisionesCalendario consultaEmisionPorId(Long idEmision) throws BusinessException {
		
		return capitalesDistribucionDao.consultaEmisionPorId(idEmision);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#guardaAssetManager(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EmisionAssetManagerVO)
	 */
	public void guardaAssetManager(EmisionAssetManagerVO emisionAssetManagerVO) {
		AssetManagerEmision asset = new AssetManagerEmision();
		asset.setIdEmision(emisionAssetManagerVO.getIdEmision());
    	asset.setIsin(emisionAssetManagerVO.getIsin());
    	asset.setDescripcion(emisionAssetManagerVO.getAssetManagerDesc());
    	asset.setId(emisionAssetManagerVO.getIdAssetManager());
		capitalesDistribucionDao.guardaAssetManager(asset);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#assetPorId(java.lang.Long)
	 */
	public AssetManagerEmision assetPorId(Long idEmision) throws BusinessException {
		return capitalesDistribucionDao.assetPorId(idEmision);
	}
	

	public void eliminaAssetManager(EmisionAssetManagerVO emisionAssetManagerVO) {
		AssetManagerEmision asset = new AssetManagerEmision();
		asset.setId(emisionAssetManagerVO.getIdAssetManager());
		capitalesDistribucionDao.eliminaAssetManager(asset);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#eliminaNarrrativa(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO)
	 */
	public void eliminaNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException{
		if(nuevaNarrativa != null){
			NarrativaCapitales narrativa = getConvertNarrativa(nuevaNarrativa,Boolean.FALSE);						
			this.capitalesDistribucionDao.delete(narrativa);				
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#obtenSubTipoDerechos()
	 */
	public List<SubTipoDerechoIntSic> obtenSubTipoDerechos() throws BusinessException {
	   return capitalesDistribucionDao.obtenSubTipoDerechos();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaInstruccionByIdHist(java.lang.Long)
	 */
	public InstruccionCapitalesVO consultaInstruccionByIdHist(Long id) throws BusinessException{
		return this.capitalesDistribucionDao.getInstruccionByIdHist(id);
	}

	/**
	 * @param tagsEliminacionSic the tagsEliminacionSic to set
	 */
	public void setTagsEliminacionSic(String tagsEliminacionSic) {
		this.tagsEliminacionSic = tagsEliminacionSic;
	}

	/**
	 * @return the tagsEliminacionSicMap
	 */
	public Map<String, String> getTagsEliminacionSicMap() {
		if(tagsEliminacionSicMap == null){
			Map<String,String> mapa = new HashMap<String,String>();
			String[] tagsEliminacion = tagsEliminacionSic.split(",");
			for(String tag : tagsEliminacion){
				mapa.put(tag, tag);
			}
			this.tagsEliminacionSicMap = mapa;
		}
		
		return tagsEliminacionSicMap;
	}

	/**
	 * @param tagsEliminacionSicMap the tagsEliminacionSicMap to set
	 */
	public void setTagsEliminacionSicMap(Map<String, String> tagsEliminacionSicMap) {
		this.tagsEliminacionSicMap = tagsEliminacionSicMap;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaVCalendarioByIdCalendario(java.lang.Long)
	 */
	public VCalendarioDerechoInt consultaVCalendarioByIdCalendario(Long idCalendario) throws BusinessException{
		return this.capitalesDistribucionDao.getVCalendarioByIdCalendario(idCalendario);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#eliminaInstruccion(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO)
	 */
	public void eliminaInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException{
		if(nuevaInstruccion != null){
			InstruccionCapitales instruccion = getConvertInstruccion(nuevaInstruccion,Boolean.FALSE);						
			this.capitalesDistribucionDao.delete(instruccion);				
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#guardarInstruccion(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO)
	 */
	public void guardarInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException{
		if(nuevaInstruccion != null){
			InstruccionCapitales instruccion = getConvertInstruccion(nuevaInstruccion,Boolean.TRUE);
			this.capitalesDistribucionDao.save(instruccion);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#actualizaInstruccion(com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO)
	 */
	public void actualizaInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException{
		if(nuevaInstruccion != null){
			InstruccionCapitales instruccion = getConvertInstruccion(nuevaInstruccion,Boolean.FALSE);						
			this.capitalesDistribucionDao.update(instruccion);			
		}
	}
	
	/**
	 * convierte un objeto InstruccionCapitalesVO a una entidad InstruccionCapitales
	 * @param nuevaInstruccion
	 * @param isNuevo
	 * @return
	 */
	private InstruccionCapitales getConvertInstruccion(InstruccionCapitalesVO nuevaInstruccion,boolean isNuevo){
		InstruccionCapitales instruccionEntity = new InstruccionCapitales();				
		instruccionEntity.setIdHistorico(nuevaInstruccion.getIdHistorico());
		instruccionEntity.setIdTipoDerechoCaev(nuevaInstruccion.getIdTipoDerechoCaev());
		instruccionEntity.setLiga(nuevaInstruccion.getLiga());
		instruccionEntity.setOpcionesMensajes(nuevaInstruccion.getOpcionesMensajes());
		instruccionEntity.setTagsEliminacion(nuevaInstruccion.getTagsEliminacion());
		instruccionEntity.setId(isNuevo ? null : nuevaInstruccion.getId());			
		return instruccionEntity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaEmisionAssetManagerForIdEmision(java.lang.Long, java.lang.Long)
	 */
	public EmisionAssetManagerVO consultaEmisionAssetManagerForIdEmision(Long idEmision,Long idCatbic)throws BusinessException{
		return this.capitalesDistribucionDao.getEmisionAssetManagerForIdEmision(idEmision,idCatbic);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#existeCalendario(java.lang.Long)
	 */
	public boolean existeCalendario(Long idCalendario) throws BusinessException{
		boolean existeCalendario = false;
		Long idTemp = this.capitalesDistribucionDao.getCalendarioById(idCalendario);
		if(idTemp!=null){
			existeCalendario = true;
		}
		return existeCalendario;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaMensajeSwiftById(java.lang.Long)
	 */
	public String consultaMensajeSwiftById(Long idBitacora)throws BusinessException{
		return this.capitalesDistribucionDao.getBitacoraMensajeSwiftById(idBitacora);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#consultaNarrativasEnviadasPorIdCalendario(java.lang.Long)
	 */
	public List<NarrativaCapitalesVO> consultaNarrativasEnviadasPorIdCalendario(Long idCalendario)throws BusinessException{
		return capitalesDistribucionDao.getNarrativasEnviadasForIdCalendario(idCalendario);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService#getDescFechasAd()
	 */
	public List<DescFechasAdIntSic> getDescFechasAd() throws BusinessException {
		return capitalesDistribucionDao.getDescFechasAd();
	}
}
