package com.indeval.portalinternacional.persistence.dao.capitales;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EnvioLegislacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.AssetManagerEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioHistoricoDerechosCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioHistoricoDerechosCapitalesVo;
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

public interface CapitalesDistribucionDao extends BaseDao {
	
	/**
	 * Realiza la consulta con paginaci&oacute;n del calendario de legislaci&oacute;n
	 * @param paginaVO
	 * @param params
	 */
	
	 PaginaVO consultaCalendarioCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO);
	 
	/**
	* Realiza la consulta con paginaci&oacute;n del calendario hist&oacute;rico de legislaci&oacute;n
	* @param paginaVO
	* @param params
	*/
	    
	 PaginaVO consultaHistoricoCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO);
	 
	 /**
	  * Realiza la consulta con paginaci&oacute;n del calendario hist&oacute;rico externo de legislaci&oacute;n
	  * @param paginaVO
	  * @param params
	  */
	 
	 PaginaVO consultaHistoricoCapitalesExterno(ConsultaCapitalesParamsTO params, PaginaVO paginaVO);
	 
	 /**
	  * Regresa el cat&aacute;logo de estados de mensajer&iacute;a Internacional SIC
	  * @return
	  */
	 List<EstadoMensajeIntSic> getCatalogoEstadoMensajeIntSic();
	 	 
	 /**
	  * Regresa el cat&aacute;logo de destinatarios de mensajer&iacute;a Internacional SIC
	  * filtrando por estado (1 = Activo, 0 = Inactivo)
	  * @param estado
	  * @return
	  */
	 List<DestinatarioIntSic> getDestinatariosIntSicByEstado(Long estado);
	 
	 /**
	  * Regresa los mensajes enviados al sistema MOI filtrando por las condiciones dadas, 
	  * sino tiene filtros regresa los envios en estado "Por Autorizar"
	  * 
	  *@param PaginaVO
	  *@param EnvioLegislacionSicDTO
	  * @return PaginaVO
	  */
	 PaginaVO consultaAutorizacionesCalendarioCapitales(EnvioLegislacionSicDTO filtroConsulta,PaginaVO paginaVO);

	 /**
	  * Guarda una lista de envios SIC para el MOI
	  * @param enviosMoi
	  * @throws BusinessException
	  */
	 void guardaEnvioLegMoi(List<EnvioLegislacionSic> enviosMoi)throws BusinessException;
	 
	 /**
	  * Regresa el registro del envio legislacion SIC filtrando por id 
	  * @param idEnvio
	  * @return
	  * @throws BusinessException
	  */
	 EnvioLegislacionSic getEnvioLegislacionSicById(Long idEnvio)throws BusinessException;
	 
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
	PaginaVO consultaEnviosCalendario(boolean bandera, Long id, PaginaVO paginaVO)  throws BusinessException;
	
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
	List<NarrativaCapitalesVO> getNarrativasForIdCalendarioOrIdHist(Long id,boolean isCalendario) throws BusinessException;
	
	/**
	 * Regresa un CalendarioHistorico filtrando por idHistorico
	 * @param idHistorico
	 * @return
	  * @return EnvioIntSic
	 * @throws BusinessException
	 */
	CalendarioHistoricoDerechosCapitales getCalendarioHistoricoById(final Long idHistorico)throws BusinessException;
	
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
	  *
	  * @param Long, idEmision
	  * @return 
	  * @throws BusinessException
	  */
	void guardaAssetManager(AssetManagerEmision asset) throws BusinessException;
	
	
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
	  *
	  * @param Long, idEmision
	  * @return 
	  * @throws BusinessException
	  */
	void eliminaAssetManager(AssetManagerEmision asset) throws BusinessException;
	
	/**
	 * Consulta el catalogo de Subtipo Derechos Int Sic
	 * @param nuevaNarrativa
	 * @throws BusinessException
	 */
	List<SubTipoDerechoIntSic> obtenSubTipoDerechos()throws BusinessException;

	/** Regresa la Instrucci&oacute;n para el mensaje hist&oacute;rico de legislaci&oacute;n
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	InstruccionCapitalesVO getInstruccionByIdHist(final Long id) throws BusinessException;
	
	/**
	 * Regresa VCalendarioDerechoInt filtrando por idCalendario
	 * @param idCalendario
	 * @return
	 * @throws BusinessException
	 */
	VCalendarioDerechoInt getVCalendarioByIdCalendario(final Long idCalendario) throws BusinessException;
	
	/**
	 * Regresa el id del calendario si existe
	 * @param idCalendario
	 * @return
	 * @throws BusinessException
	 */
	Long getCalendarioById(final Long idCalendario) throws BusinessException;
	
	/**
	 * Actualiza el origen de las narrativas con Origen U (ingresadas por el usuario) 
	 * por el valor E (Enviadas) del hist&oacute;rico correspondiente
	 * @param idHistorico
	 * @return
	 * @throws BusinessException
	 */
	Integer actualizaOrigenNarrativaSicByIdHistorico(final Long idHistorico)throws BusinessException;
	
	/**
	 * Regresa una lista de narrativas que ya fueron enviadas, filtrando por el idCalendario
	 * @param idCalendario
	 * @return
	 * @throws BusinessException
	 */
	List<NarrativaCapitalesVO> getNarrativasEnviadasForIdCalendario(final Long idCalendario) throws BusinessException;
	
	/**
	   * Obtiene la lista de descripciones para fechas adicionales
	   * @return  List<DescFechasAdIntSic>
	   * @throws BusinessException
	   */
	 List<DescFechasAdIntSic> getDescFechasAd() throws BusinessException;
	 
	 /**
	  * Regresa las narrativas enviadas concatenadas en un String
	  * @param idCalendario
	  * @return
	  * @throws BusinessException
	  */
	 String getNarrativasReporte(final Long idCalendario) throws BusinessException;
	 
	 /**
	  * Regresa la emisi&oacute;n con su asset filtrando por el idEmision y idCatbic 
	  * @param idEmision
	  * @param idConsecutivoView
	  * @return
	  * @throws BusinessException
	  */
	 EmisionAssetManagerVO getEmisionAssetManagerForIdEmision(final Long idEmision,final Long idCatbic) throws BusinessException;
	 
	 /**
	  * Regresa el Id de la primer narrativa que encuentre con el idHistorico y con Origen U
	  * Se espera s&oacute;lo un valor de regreso o ninguno
	  * @param idHistorico
	  * @return
	  * @throws BusinessException
	  */
	 Long getIdNarrativaByIdHist(final Long idHistorico) throws BusinessException;
	 
	 /**
	  * Regresa el valor del mensaje del mensaje Iso (Legislaci&oacute;n)
	  * @param idBitacora
	  * @return
	  */
	 String getBitacoraMensajeSwiftById(final Long idBitacora)throws BusinessException;
	 
		/**
		 * Regresa un CalendarioHistorico filtrando por idHistorico
		 * @param idHistorico
		 * @return
		  * @return EnvioIntSic
		 * @throws BusinessException
		 */
	 CalendarioHistoricoDerechosCapitalesVo getCalendarioHistoricoVoById(final Long idHistorico) throws BusinessException;
		
}
