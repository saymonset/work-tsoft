/**
 * 
 */
package com.indeval.persistence.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
import com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO;

/**
 * @author antonio
 * 
 */
public class TestMatchEstatusOperacionesDAO extends
		BaseDaoTestCase  {
	
	/** Log de clase */
    private Logger logger = LoggerFactory.getLogger(TestMatchEstatusOperacionesDAO.class);
    
    /**
     * Prueba la consulta de estatus de operaciones con operación.
     */
    public void testConsultaEstatusOperacionesConOperacion() {
    	EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = (EstatusOperacionesMatchDAO)applicationContext.getBean("estatusOperacionesMatchDAO");
    	
    	CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
    	EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
    	
    	criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
    	criterio.getEstadoInstruccion().setIdEstadoInstruccion(-1);
    	
    	criterio.setMercado(new MercadoDTO());
    	criterio.getMercado().setId(-1);
    	
    	criterio.setRol(0);
    	
    	criterio.getEmision().getTipoValor().setId(-1);
    	criterio.getEmision().getEmisora().setId(-1);
    	
    	criterio.setTipoMensaje(new TipoMensajeDTO());
    	
    	criterio.getTipoMensaje().setIdTipoMensaje(-1);
    	
    	paginacion.setNumeroPagina(1);
    	paginacion.setRegistrosPorPagina(10);
    	
    	criterio.setInstitucionParticipante(new InstitucionDTO());
    	criterio.getInstitucionParticipante().setClaveTipoInstitucion("02");
    	criterio.getInstitucionParticipante().setFolioInstitucion("013");
    	criterio.getInstitucionParticipante().setId(59);
    	
    	List<OperacionValorMatchDTO> lista = estatusOperacionesMatchDAO.consultarInstruccionesMatchConOperacion(criterio, paginacion);
    	
    	assertTrue(lista != null && lista.size() > 0);
    	
    }
    
    /**
     * Prueba la consulta de estatus de operaciones sin operación.
     */
    public void testConsultaEstatusOperacionesSinOperacion() {
    	EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = (EstatusOperacionesMatchDAO)applicationContext.getBean("estatusOperacionesMatchDAO");
    	
    	CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
    	EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
    	
    	criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
    	criterio.getEstadoInstruccion().setIdEstadoInstruccion(-1);
    	
    	criterio.setMercado(new MercadoDTO());
    	criterio.getMercado().setId(-1);
    	
    	criterio.setRol(0);
    	
    	criterio.getEmision().getTipoValor().setId(-1);
    	criterio.getEmision().getEmisora().setId(-1);
    	
    	criterio.setTipoMensaje(new TipoMensajeDTO());
    	
    	criterio.getTipoMensaje().setIdTipoMensaje(-1);
    	
    	paginacion.setNumeroPagina(1);
    	paginacion.setRegistrosPorPagina(10);
    	
    	criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
    	criterio.getEstadoInstruccion().setIdEstadoInstruccion(-1);
    	criterio.setInstitucionParticipante(new InstitucionDTO());
    	//criterio.getInstitucionParticipante().setClaveTipoInstitucion("02");
    	//criterio.getInstitucionParticipante().setFolioInstitucion("003");
    	//criterio.setCantidad("1681888");
    	
    	criterio.getInstitucionParticipante().setId(80);
    	
    	criterio.setCuentaParticipante(new CuentaDTO());
    	
    	criterio.getCuentaParticipante().setCuenta("9120");
    	
    	criterio.setInstitucionContraparte(new InstitucionDTO());
    	criterio.getInstitucionContraparte().setId(-1);
    	//criterio.getInstitucionContraparte().setClaveTipoInstitucion("01");
    	
    	//criterio.setMonto("416608.17");
    	
    	long total = estatusOperacionesMatchDAO.obtenerProyeccionConsultaInstruccionesMatchSinOperacion(criterio);
    	
    	List<OperacionValorMatchDTO> lista = estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacion(criterio, paginacion);
    	
    	assertTrue(lista != null && lista.size() > 0);
    }
    
    /**
     * Prueba la consulta de match
     */
    public void testConsultaMatch() {
    	
    	EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = (EstatusOperacionesMatchDAO)applicationContext.getBean("estatusOperacionesMatchDAO");
    	
    	CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
    	EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
    	
    	criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
    	criterio.getEstadoInstruccion().setIdEstadoInstruccion(-1);
    	
    	criterio.setMercado(new MercadoDTO());
    	criterio.getMercado().setId(-1);
    	
    	criterio.setRol(RolConstants.ROL_TRASPASANTE);
    	
    	criterio.getEmision().getTipoValor().setId(-1);
    	criterio.getEmision().getEmisora().setId(-1);
    	//criterio.getEmision().getEmisora().setId(712);
    	//criterio.getEmision().getEmisora().setDescripcion("FINAMEX");
    	
    	//criterio.getEmision().getSerie().setSerie("O");
    	//criterio.getEmision().getTipoValor().setClaveTipoValor("1");
    	//criterio.getEmision().getTipoValor().setId(105);
    	
    	
    	criterio.setTipoMensaje(new TipoMensajeDTO());
    	
    	criterio.getTipoMensaje().setIdTipoMensaje(-1);
    	
    	//paginacion.setNumeroPagina(1);
    	//paginacion.setRegistrosPorPagina(20);
    	//criterio.setFolioControl("8410");
    	criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
    	criterio.getEstadoInstruccion().setIdEstadoInstruccion(-1);
    	criterio.setInstitucionParticipante(new InstitucionDTO());
    	criterio.getInstitucionParticipante().setClaveTipoInstitucion("01");
    	criterio.getInstitucionParticipante().setFolioInstitucion("006");
    	//criterio.setCantidad("1681888");
    	
    	criterio.getInstitucionParticipante().setId(6);
    	
    	criterio.setInstitucionContraparte(new InstitucionDTO());
    	criterio.getInstitucionContraparte().setId(-1);
    	//criterio.getInstitucionContraparte().setClaveTipoInstitucion("01");
    	//criterio.getInstitucionContraparte().setFolioInstitucion("009");
    	//criterio.getInstitucionParticipante().setId(-1);
    	
    	//criterio.setFechaConcertacion(new Date());
    	criterio.setFechaLiquidacion(new Date());
    	
//    	criterio.setCuentaParticipante(new CuentaDTO());
//    	criterio.getCuentaParticipante().setCuenta("9803");

//    	criterio.setCuentaContraparte(new CuentaDTO());
//    	criterio.getCuentaContraparte().setCuenta("0307");

    	long registros = estatusOperacionesMatchDAO.obtenerProyeccionConsultaOperacionesValor(criterio);
    	
    	Collection<OperacionValorMatchDTO> lista = estatusOperacionesMatchDAO.consultarOperacionesValor(criterio, null);
    	
    	List<OperacionValorMatchDTO> operaciones = estatusOperacionesMatchDAO.buscarParcialidades(criterio, null);
    	
    	assertTrue(lista != null && lista.size() > 0);
    }
    
    public void testBuscarMatchPorId() {
    	EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = (EstatusOperacionesMatchDAO)applicationContext.getBean("estatusOperacionesMatchDAO");
    	
    	List<OperacionValorMatchDTO> operaciones = estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacionPorId(3498L);
    	
    	assertTrue(operaciones != null && operaciones.size() > 0);
    }
    
		
}
