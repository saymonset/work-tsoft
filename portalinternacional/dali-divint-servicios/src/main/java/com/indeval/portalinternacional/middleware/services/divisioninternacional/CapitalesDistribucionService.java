package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.AssetManagerEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DescFechasAdIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EmisionAssetManagerVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.SubTipoDerechoIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VEmisionesCalendario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO;

public interface CapitalesDistribucionService {
	
	/**
	 * Realiza la consulta con paginaci&oacute;n del calendario de legislaci&oacute;n
	 * @param params
	 * @param paginaVO
	 * @return
	 */
    PaginaVO consultaCalendarioCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO);
    
    /**
     * Realiza la consulta con paginaci&oacute;n del calendario hist&oacute;rico de legislaci&oacute;n
     * @param params
     * @param paginaVO
     * @return
     */
    PaginaVO consultaHistoricoCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO, String origen);   
    
    /**
	  * Regresa un arreglo con los valores en el catalogo de divisas
	  * @return
	  */
	 Divisa[] findAllDivisas() throws BusinessException;
	 
	 /**
	  * Regresa los estados del mensaje internacional SIC
	  * @return
	  * @throws BusinessException
	  */
	 List<EstadoMensajeIntSic> obtieneEstadosDerechoIntSic()throws BusinessException;
	 
	 /**
	  * Regresa arreglo con las bovedas disponibles
	  * @return
	  * @throws BusinessException
	  */
	 Boveda[] getBovedas() throws BusinessException;
	 
	 /**
	  * Regresa los destinatarios para el mensaje internacional SIC
	  * filtrando por estado (1 = Activo, 0 = Anactivo)
	  * @param estado
	  * @return
	  * @throws BusinessException
	  */
	 List<DestinatarioIntSic> obtieneDestinatariosIntSicByEstado(Long estado) throws BusinessException;
	 
	 /**
	  * Regresa los mensajes enviados a MOI filtrando por las condiciones dadas, 
	  * sino cuenta con filtro regresa las que se encuentren en estado "Por Autorizar" 
	  *
	  * @param PaginaVO
	  * @param EnvioLegislacionSicDTO
	  * @return PaginaVO
	  * @throws BusinessException
	  */
	 PaginaVO consultaAutorizacionesCalendarioCapitales(EnvioLegislacionSicDTO filtroConsulta,PaginaVO paginaVO) throws BusinessException;

	 /**
	  * Procesa la informacion para guardar los envios SIC al modulo MOI
	  * @param listaEnvios	 
	  * @throws BusinessException
	  */
	 void procesaEnvioLegMoi(List<EnvioLegislacionSicDTO> listaEnvios)throws BusinessException;
	 
	 /**
	  * Procesa el evento de autorizar y enviar el mensaje al sistema MOI o cancelar el envio
	  * @param listaEnvios
	  * @return Ids de envios que no se pudieron actualizar/enviar 
	  * @throws BusinessException
	  */
	  List<Long> procesaAutorizacionEnvioMoiSic(List<EnvioLegislacionSicDTO> listaEnvios)throws BusinessException;
	  
	  /**
	   * Regresa los TV permitidos en legislaci&oacute;n SIC
	   * @return
	   */
	  Map<String, String> getTipoValorLegislacionSicMap();
	 
	 /**
	  * Devuelve un long  del idHistorico Maximo de un idCalendario
	  * @param idCalendario
	  * @throws BusinessException
	  */
	 Long consultaUltimoHistoricoCalendario(Long idCalendario)throws BusinessException;
	 
	 /**
	  * Regresa los mensajes que se han Enviado a Bolsas
	  *
	  * @param PaginaVO
	  * @return PaginaVO
	  * @throws BusinessException
	  */
	 PaginaVO consultaEnviadosCalendarioCapitales(EnvioCapitalesParamsTO params, PaginaVO paginaVO) throws BusinessException;
	 
	 /**
	  * Regresa los envios de derechos se envia una bandera para indicar de que pantalla se invoco el servicio
	  *
	  * @param boolean, id
	  * @return EnvioIntSic
	  * @throws BusinessException
	  */
	PaginaVO consultaEnviosCalendario(boolean bandera, Long id, PaginaVO paginaVo)  throws BusinessException;
	
	 /**
	  * Regresa las emsiones para saber si estan listados los derechos
	  *
	  * @param boolean, id
	  * @return EnvioIntSic
	  * @throws BusinessException
	  */
	PaginaVO consultaEmisionesCalendarioCapitales(ConsultaEmisionesCalendarioParamsTO params, PaginaVO paginaVo)  throws BusinessException;

	/**
	 * Regresa una lista de NarrativasCapitalesVO filtrando 
	 * por idCalendario(bandera = true) o idHistorico (bandera = false)
	 * @param id
	 * @param isCalendario
	 * @return
	 * @throws BusinessException
	 */
	List<NarrativaCapitalesVO> consultaNarrativasForIdCalendarioOrIdHistorico(Long id,Boolean isCalendario)throws BusinessException;
	
	/**
	 * Guarda una narrativa 
	 * @param nuevaNarrativa
	 * @throws BusinessException
	 */
	void guardarNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException;
	
	/**
	 * Actualiza una narrativa
	 * @param nuevaNarrativa
	 * @throws BusinessException
	 */
	void actualizaNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException;
	
	/**
	 * Regresa un NarrativaCapitalesVO filtrando por idHistorico
	 * @param idHistorico
	 * @return
	 * @throws BusinessException
	 */
	NarrativaCapitalesVO getCalendarioHistoricoById(Long idHistorico)throws BusinessException;
	
	 /**
	  * Busca una emision en la vista emisiones calendarios por su id
	  *
	  * @param Long, idEmision
	  * @return 
	  * @throws BusinessException
	  */
	VEmisionesCalendario consultaEmisionPorId(Long idEmision) throws BusinessException ;
	
	/**
	 * Guarda el asset manager de la emision
	 * @param asset
	 * @throws BusinessException
	 */
	void guardaAssetManager(EmisionAssetManagerVO asset) throws BusinessException;
	
	
	 /**
	  * Regresa el asset por su idEmision
	  *
	  * @param Long, idEmision
	  * @return AssetManager Emision
	  * @throws BusinessException
	  */
	AssetManagerEmision assetPorId(Long idEmision) throws BusinessException;
	
	/**
	 * Elimina el asset manager de la emision
	 * @param emisionAssetManagerVO
	 * @throws BusinessException
	 */
	void eliminaAssetManager(EmisionAssetManagerVO emisionAssetManagerVO) throws BusinessException;
	
	/**
	 * Elimina la Narrativa indicada
	 * @param nuevaNarrativa
	 * @throws BusinessException
	 */
	void eliminaNarrrativa(NarrativaCapitalesVO nuevaNarrativa)throws BusinessException;
	
	/**
	 * Consulta el catalogo de Subtipo Derechos Int Sic
	 * @param nuevaNarrativa
	 * @throws BusinessException
	 */
	List<SubTipoDerechoIntSic> obtenSubTipoDerechos()throws BusinessException;

	/**
	 * Regresa la instrucci&oacute;n asignada al mensaje Hit&oacute;rico de legislaci&oacute;n SIC
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	InstruccionCapitalesVO consultaInstruccionByIdHist(Long id) throws BusinessException;
	
	  /**
	   * Regresa los Tags de Eliminaci&oacute;n SIC
	   * @return
	   */
	  Map<String, String> getTagsEliminacionSicMap();
	  
	  /**
	   * Consulta VCalendario filtrando por IdCalendario
	   * @param idCalendario
	   * @return
	   * @throws BusinessException
	   */
	  VCalendarioDerechoInt consultaVCalendarioByIdCalendario(Long idCalendario) throws BusinessException;
	  
	  /**
	   * Elimina la Instrucci&oacute;n
	   * @param nuevaInstruccion
	   * @throws BusinessException
	   */
	  void eliminaInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException;
	  
	  /**
	   * Genera la Instrucci&oacute;n
	   * @param nuevaInstruccion
	   * @throws BusinessException
	   */
	  void guardarInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException;
	  
	  /**
	   * Actualiza la Instrucci&oacute;n
	   * @param nuevaInstruccion
	   * @throws BusinessException
	   */
	  void actualizaInstruccion(InstruccionCapitalesVO nuevaInstruccion)throws BusinessException;
	  
	  /**
	   * Valida que exista el calendario con el id proporcionado
	   * @param idCalendario
	   * @return
	   * @throws BusinessException
	   */
	  boolean existeCalendario(Long idCalendario) throws BusinessException;
	  
	  /**
	   * Regresa una lista de narrativas enviadas filtradas por idCalendario
	   * @param idCalendario
	   * @return
	   * @throws BusinessException
	   */
	  List<NarrativaCapitalesVO> consultaNarrativasEnviadasPorIdCalendario(Long idCalendario)throws BusinessException;
	  
	  /**
	   * Obtiene la lista de descripciones para fechas adicionales
	   * @return  List<DescFechasAdIntSic>
	   * @throws BusinessException
	   */
	 List<DescFechasAdIntSic> getDescFechasAd() throws BusinessException;
	 
	 /**
	  * regresa EmisionAssetManagerVO filtrando por idEmision y catbic
	  * @param idEmision
	  * @param idConsecutivoView
	  * @return
	  * @throws BusinessException
	  */
	 EmisionAssetManagerVO consultaEmisionAssetManagerForIdEmision(Long idEmision,Long idCatbic)throws BusinessException;
	 
	 /**
	  * Consulta el mensaje Swift (Legislaci&oacute;n) de la bit&aacute;cora por id
	  * @param id
	  * @return
	  * @throws BusinessException
	  */
	 String consultaMensajeSwiftById(Long id)throws BusinessException;
	 
	 
}
