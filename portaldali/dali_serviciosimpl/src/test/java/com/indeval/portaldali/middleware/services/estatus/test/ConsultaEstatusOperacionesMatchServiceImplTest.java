/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstatusOperacionesMatchServiceImplTest.java
 * 24/03/2008
 */
package com.indeval.portaldali.middleware.services.estatus.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;

/**
 * Clase de prueba unitaria para el servicio de Consulta de Estatus y Match de Operaciones
 * @author Emigdio Hernández
 *
 */
public class ConsultaEstatusOperacionesMatchServiceImplTest extends
		BaseTestCase {

	public void testConsultaSoloOperacionesRolAmbos(){
		ConsultaEstatusOperacionesMatchService service = 
			(ConsultaEstatusOperacionesMatchService)applicationContext.getBean("consultaEstatusOperacionesMatchService");
		
		CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
		
		criterio.setInstitucionParticipante(new InstitucionDTO());
		
		criterio.getInstitucionParticipante().setId(2);
		criterio.setMonto("100");
		criterio.setRol(RolConstants.ROL_AMBOS);
		
		criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		criterio.getEstadoInstruccion().setIdEstadoInstruccion(6);
		
		long numeroResultados = service.obtenerProyeccionConsultaOperacionesValor(criterio,false);
		
		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(1);
		paginacion.setRegistrosPorPagina(50);
		paginacion.setTotalPaginas(1);
		paginacion.setTotalResultados((int)numeroResultados);
		Map<Object,Object> resultadosExtra = new HashMap<Object, Object>();
		List<OperacionValorMatchDTO> resultados =  service.consultarOperacionesValor(criterio, paginacion, resultadosExtra);
		assertTrue(numeroResultados > 0);
		assertNotNull(resultadosExtra);
		assertNotNull(resultados);
	}
	
	public void testConsultaSoloOperacionesRolTraspasante(){
		ConsultaEstatusOperacionesMatchService service = 
			(ConsultaEstatusOperacionesMatchService)applicationContext.getBean("consultaEstatusOperacionesMatchService");
		
		CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
		
		criterio.setInstitucionParticipante(new InstitucionDTO());
		
		criterio.getInstitucionParticipante().setId(104);
		criterio.setMonto("100");
		criterio.setRol(RolConstants.ROL_TRASPASANTE);
		
		criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		criterio.getEstadoInstruccion().setIdEstadoInstruccion(6);
		
		long numeroResultados = service.obtenerProyeccionConsultaOperacionesValor(criterio,false);
		
		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(1);
		paginacion.setRegistrosPorPagina(50);
		paginacion.setTotalPaginas(1);
		paginacion.setTotalResultados((int)numeroResultados);
		Map<Object,Object> resultadosExtra = new HashMap<Object, Object>();
		List<OperacionValorMatchDTO> resultados =  service.consultarOperacionesValor(criterio, paginacion, resultadosExtra);
		assertTrue(numeroResultados > 0);
		assertNotNull(resultadosExtra);
		assertNotNull(resultados);
	}
	
	public void testConsultaSoloOperacionesRolReceptor(){
		ConsultaEstatusOperacionesMatchService service = 
			(ConsultaEstatusOperacionesMatchService)applicationContext.getBean("consultaEstatusOperacionesMatchService");
		
		CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
		
		criterio.setInstitucionParticipante(new InstitucionDTO());
		
		criterio.getInstitucionParticipante().setId(78);
		criterio.setMonto("100");
		criterio.setRol(RolConstants.ROL_RECEPTOR);
		
		criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		criterio.getEstadoInstruccion().setIdEstadoInstruccion(6);
		
		long numeroResultados = service.obtenerProyeccionConsultaOperacionesValor(criterio,false);
		
		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(1);
		paginacion.setRegistrosPorPagina(50);
		paginacion.setTotalPaginas(1);
		paginacion.setTotalResultados((int)numeroResultados);
		Map<Object,Object> resultadosExtra = new HashMap<Object, Object>();
		List<OperacionValorMatchDTO> resultados =  service.consultarOperacionesValor(criterio, paginacion, resultadosExtra);
		assertTrue(numeroResultados > 0);
		assertNotNull(resultadosExtra);
		assertNotNull(resultados);
	}
	
	
	public void testConsultaUnRegistroRolAmbos(){
		ConsultaEstatusOperacionesMatchService service = 
			(ConsultaEstatusOperacionesMatchService)applicationContext.getBean("consultaEstatusOperacionesMatchService");
		
		CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
		
		criterio.setInstitucionParticipante(new InstitucionDTO());
		
		criterio.getInstitucionParticipante().setId(104);
		criterio.setMonto("100");
		criterio.setCantidad("1");
		criterio.setRol(RolConstants.ROL_AMBOS);
		
		
		criterio.setEmision(new EmisionDTO());
		criterio.getEmision().setTipoValor(new TipoValorDTO());
		criterio.getEmision().getTipoValor().setMercado(new MercadoDTO());
		criterio.getEmision().getTipoValor().getMercado().setId(3);
		criterio.getEmision().getTipoValor().setId(32);
		criterio.getEmision().setEmisora(new EmisoraDTO());
		criterio.getEmision().getEmisora().setId(333);
		criterio.getEmision().setSerie(new SerieDTO());
		criterio.getEmision().getSerie().setSerie("100624");
		criterio.setTipoInstruccion(new TipoInstruccionDTO());
		criterio.getTipoInstruccion().setIdTipoInstruccion(22L);
		criterio.setFechaConcertacion(FormatUtil.stringToDate("06/03/2008"));
		criterio.setFechaLiquidacion(FormatUtil.stringToDate("06/03/2008"));
		
		criterio.setCuentaParticipante(new CuentaDTO());
		criterio.getCuentaParticipante().setIdCuenta(1689);
		
		criterio.setCuentaContraparte(new CuentaDTO());
		criterio.getCuentaContraparte().setIdCuenta(965);
		
		criterio.setFolioUsuario("54330600088");
		criterio.setFolioControl("3093");
		criterio.setTipoMensaje(new TipoMensajeDTO());
		criterio.getTipoMensaje().setIdTipoMensaje(4);
		
		long numeroResultados = service.obtenerProyeccionConsultaOperacionesValor(criterio,false);
		
		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(1);
		paginacion.setRegistrosPorPagina(50);
		paginacion.setTotalPaginas(1);
		paginacion.setTotalResultados((int)numeroResultados);
		Map<Object,Object> resultadosExtra = new HashMap<Object, Object>();
		List<OperacionValorMatchDTO> resultados =  service.consultarOperacionesValor(criterio, paginacion, resultadosExtra);
		assertTrue(numeroResultados > 0);
		assertNotNull(resultadosExtra);
		assertNotNull(resultados);
		
		if(resultados.size()>0){
			assertNotNull(service.
					consultarInstruccionOperacionValorPorId(resultados.get(0).getIdInstruccionOperacionVal()));
			//service.consultarParcialidadesDeInstruccionLiquidacion(resultados.get(0).getIdInstruccionOperacionVal());
		}
	}
	
	public void testConsultaParcialidades(){
		ConsultaEstatusOperacionesMatchService service = 
			(ConsultaEstatusOperacionesMatchService)applicationContext.getBean("consultaEstatusOperacionesMatchService");
		
		CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
		
		criterio.setInstitucionParticipante(new InstitucionDTO());
		
		criterio.getInstitucionParticipante().setId(1);
		
		criterio.setRol(RolConstants.ROL_AMBOS);
		
		
		criterio.setEmision(new EmisionDTO());
		criterio.getEmision().setTipoValor(new TipoValorDTO());
		criterio.getEmision().getTipoValor().setMercado(new MercadoDTO());
		criterio.getEmision().getTipoValor().getMercado().setId(-1);
		criterio.getEmision().getTipoValor().setId(-1);
		criterio.getEmision().setEmisora(new EmisoraDTO());
		criterio.getEmision().getEmisora().setId(-1);
		criterio.getEmision().setSerie(new SerieDTO());
		
		criterio.setTipoInstruccion(new TipoInstruccionDTO());
		criterio.getTipoInstruccion().setIdTipoInstruccion(-1L);
		//criterio.setFechaConcertacion(FormatUtil.stringToDate("06/03/2008"));
		//criterio.setFechaLiquidacion(FormatUtil.stringToDate("06/03/2008"));
		
		//criterio.setCuentaParticipante(new CuentaDTO());
		//criterio.getCuentaParticipante().setIdCuenta(1689);
		
		//criterio.setCuentaContraparte(new CuentaDTO());
		//criterio.getCuentaContraparte().setIdCuenta(965);
		
		//criterio.setFolioUsuario("54330600088");
		//criterio.setFolioControl("3093");
		//criterio.setTipoMensaje(new TipoMensajeDTO());
		//criterio.getTipoMensaje().setIdTipoMensaje(4);
		criterio.setFolioControl("14047");
		
		long numeroResultados = service.obtenerProyeccionConsultaOperacionesValor(criterio,false);
		
		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(1);
		paginacion.setRegistrosPorPagina(50);
		paginacion.setTotalPaginas(1);
		paginacion.setTotalResultados((int)numeroResultados);
		Map<Object,Object> resultadosExtra = new HashMap<Object, Object>();
		List<OperacionValorMatchDTO> resultados =  service.consultarOperacionesValor(criterio, paginacion, resultadosExtra);
		assertTrue(numeroResultados > 0);
		assertNotNull(resultadosExtra);
		assertNotNull(resultados);
		
		if(resultados.size()>0){
			//assertNotNull(service.consultarParcialidadesDeInstruccionLiquidacion(resultados.get(0).getIdInstruccionOperacionVal()));
		}
	}
	
}
