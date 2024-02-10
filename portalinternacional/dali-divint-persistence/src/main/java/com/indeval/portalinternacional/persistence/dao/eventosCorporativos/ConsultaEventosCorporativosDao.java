package com.indeval.portalinternacional.persistence.dao.eventosCorporativos;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ReglaEstado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvco;

public interface ConsultaEventosCorporativosDao extends BaseDao {
	PaginaVO getEventosCorporativos(EventoCorporativoConsultaDTO params, PaginaVO paginaVO, boolean isParticipante) throws BusinessException;
	
//	void getAvisoEventoCorporativoIndeval(Long idEvCorp) throws BusinessException;
//	
//	void getAvisoEventoCorporativoParticipante(Long idEvCorp) throws BusinessException;
//	
//	void getBitacoraCambiosEventoCorporativo(Long idEvCorp) throws BusinessException;

	List<Estado> getEstadosEventoCorporativo() throws BusinessException;

	List<TipoMercado> getTiposMercado()throws BusinessException;

	List<TipoDerechoEvCo> getTiposDerecho()throws BusinessException;

	List<TipoDerechoEvCo> getTiposDerechoMO()throws BusinessException;
	
	List<TipoDerechoEvCo> getTiposDerechoML()throws BusinessException;
	
	List<TipoEvento> getTiposEvento() throws BusinessException;
	
	List<ReglaEstado> getReglasEstado() throws BusinessException;

	Integer actualizaEstado(Long idevco, Long estado)throws BusinessException;

	CuerpoEventoCorporativo getCuerpoEventoCorporativo(Long folio)throws BusinessException;	

	TipoDerechoEvCo findTipoDerechoById(Long idTipoDerecho)throws BusinessException;
	
	TipoMercado findTipoMercadoById(Long idTipoMercado)throws BusinessException;
	
	TipoEvento findTipoEventoById(Long idTipoEvento)throws BusinessException;
	
	Estado findEstadoById(Long idEstado)throws BusinessException;
	
	Custodio findCustodioById(Long idCustodio)throws BusinessException;
	
	Long findSequenceValue()throws BusinessException;

	PaginaVO getBitacoraPorEventoCorportativo(EventoCorporativoConsultaDTO params, PaginaVO paginaVO) throws BusinessException;	
	
	AdjuntosEventoCorporativo getArchivoAdjuntoPorId(Long idArchivo) throws BusinessException;
	
	List<BitacoraCambiosEvco> getBitacoraControlCambios(Long idEventoCorporativo) throws BusinessException;
	
	List<BitacoraCambiosEvco> getBitacoraPorRegistro(Long idBitacoraCambio) throws BusinessException;
	
	void updateCambiosDeEstadoBitacora(Long idBitacoraCambio, Long estado) throws BusinessException;

	List<NotasEventoCorporativo> getNotasPorEvco(Long idEvco) throws BusinessException;
	
	List<OpcionesEventoCorporativo> getOpcionesPorEvco(Long idEvco) throws BusinessException;
	
	List<AdjuntosEventoCorporativo> getAdjuntosPorEvco(Long idEvco) throws BusinessException;
	
	List<AdjuntosEventoCorporativo> getAdjuntosPorEvcoNoData(Long idEvco) throws BusinessException;
	
	EventoCorporativo getResumenCorporativo(Long idEventoCorporativoParam)throws BusinessException;
	
	List<NotificacionEventoCorporativo> getNotificacionesPorEvco(Long idEvco) throws BusinessException;
	
	List<ValidacionesEvco> getValidacionesPorEvco(Long idEvco) throws BusinessException;
	
	PaginaVO getCatalogoTiposDerecho(TipoDerechoDto params,PaginaVO paginaVO) throws BusinessException;	
	
	void saveWithFlush(Object params)throws BusinessException;
	
	List<TipoValidacionEvco> getAllTipoValidacionEvco() throws BusinessException;
	
	List<OperadorEvco> getAllOperadorEvco() throws BusinessException;
	
	TipoValidacionEvco getTipoValidacionById(Long id) throws BusinessException;
	
	OperadorEvco getOperadorById(Long id)throws BusinessException;
	
	List<ListaDistribucion> getAllListaDistribucion()throws BusinessException;
	
	ListaDistribucion getListaDisById(Long id)throws BusinessException;

	void borraInfoAdjuntosEvCo(Long id)throws BusinessException;

	void deleteAdjuntoLogico(Long idAdjuntos);
}
