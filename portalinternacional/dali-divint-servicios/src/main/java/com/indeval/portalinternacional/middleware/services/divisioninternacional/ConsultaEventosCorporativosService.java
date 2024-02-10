package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ReglaEstado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;

public interface ConsultaEventosCorporativosService {
	public static final String TIPO_DERECHO_MERCADO_LOCAL="ML";
	public static final String TIPO_DERECHO_MERCADO_ORIGEN="MO";
	
	PaginaVO getEventosCorporativos(EventoCorporativoConsultaDTO params,PaginaVO paginaVO, boolean isParticipante) throws BusinessException;

	List<Estado> getEstadosEventoCorporativo() throws BusinessException;

	List<TipoMercado> getTiposMercado()throws BusinessException;

	List<TipoDerechoEvCo> getTiposDerecho()throws BusinessException;

	List<TipoDerechoEvCo> getTiposDerechoMO()throws BusinessException;
	
	List<TipoDerechoEvCo> getTiposDerechoML()throws BusinessException;
	
	List<TipoEvento> getTiposEvento()throws BusinessException;

	List<Custodio> getCatalogoCustodios()throws BusinessException;
	
	List<ReglaEstado> getReglasEstado() throws BusinessException;

	Integer cambiarEstado(Map<String,String>  cambios, String usuario) throws BusinessException;

	EventoCorporativo getEventoCorporativoById(Long valueOf) throws BusinessException;

	CuerpoEventoCorporativo getCuerpoEventoCorporativo(Long valueOf) throws BusinessException;
	
	TipoDerechoEvCo findTipoDerechoById(Long idTipoDerecho)throws BusinessException;	

	PaginaVO getBitacoraPorEventoCorportativo(EventoCorporativoConsultaDTO params,PaginaVO paginaVO) throws BusinessException;	

	AdjuntosEventoCorporativo getArchivoAdjuntoPorId(Long idArchivo) throws BusinessException;
	
	List<BitacoraCambiosDto> getBitacoraControlCambios(Long idEventoCorporativo) throws BusinessException;
	
	EventoCorporativoFullVistaDTO getBitacoraPorRegistro(Long idBitacoraCambio) throws BusinessException;
	
	void updateCambiosDeEstadoBitacora(String idsBitacoraCambioFalse,String idsBitacoraCambio, Long estadoHabilitado,Long estadoInhabil) throws BusinessException;

	EventoCorporativoFullVistaDTO getResumenEventoCorporativo(Long idEventoCorporativo) throws BusinessException;
	
	PaginaVO getCatalogoTiposDerecho(TipoDerechoDto params,PaginaVO paginaVO) throws BusinessException;
	
	TipoDerechoDto getTipoDerechoPorId(Long idTipoDerecho) throws  BusinessException;
	
	void saveTipoDerecho(TipoDerechoDto tipoDerechoDto) throws BusinessException;
	
	List<TipoValidacionEvco> getAllTipoValidacionEvco() throws BusinessException;
	
	List<OperadorEvco> getAllOperadorEvco() throws BusinessException;
	
	TipoValidacionEvco getTipoValidacionById(Long id) throws BusinessException;
	
	OperadorEvco getOperadorById(Long id)throws BusinessException;
	
	List<ListaDistribucion> getAllListaDistribucion()throws BusinessException;
	
	ListaDistribucion getListaDisById(Long id)throws BusinessException;

	EventoCorporativoEdicionDTO getFullEventoCorporativo(Long id)throws BusinessException;
}
