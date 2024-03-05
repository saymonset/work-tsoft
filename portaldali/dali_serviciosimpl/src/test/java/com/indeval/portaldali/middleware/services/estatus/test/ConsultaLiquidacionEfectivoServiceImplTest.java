/**
 * 
 */
package com.indeval.portaldali.middleware.services.estatus.test;

import java.util.Date;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
//import com.indeval.portaldali.middleware.services.estatus.ConsultaLiquidacionesEfectivoService;

/**
 * @author Marcos
 *  *
 */
public class ConsultaLiquidacionEfectivoServiceImplTest extends BaseTestCase {
	
//	public void testConsultarTodosLosMovimientosEfectivo1(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		criterioConsulta.setInstitucionParticipante(inst);
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//		
//	}
//
//	public void testConsultarTodosLosMovimientosEfectivo2(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		criterioConsulta.setInstitucionParticipante(inst);
//		CuentaDTO cuenta = new CuentaDTO();
//		cuenta.setCuenta("2000");
//		criterioConsulta.setCuentaParticipante(cuenta);
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//		
//	}
//
//	public void testConsultarTodosLosMovimientosEfectivo3(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		criterioConsulta.setInstitucionParticipante(inst);
//		
//		criterioConsulta.setReferenciaOperacion("123123123");
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//	}
//	
//	public void testConsultarTodosLosMovimientosEfectivo4(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		criterioConsulta.setInstitucionParticipante(inst);
//		
//		criterioConsulta.setRol(1);
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//	}
//
//	public void testConsultarTodosLosMovimientosEfectivo5(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		Date fecha = new Date();
//
//		criterioConsulta.setInstitucionParticipante(inst);
//		criterioConsulta.setFechaLiquidacion(fecha);
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//	}
//
//	public void testConsultarTodosLosMovimientosEfectivo6(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		
//		InstitucionDTO inst = new InstitucionDTO();
//		inst.setClaveTipoInstitucion("01003");
//		CuentaDTO cuenta = new CuentaDTO();
//		cuenta.setCuenta("2000");
//		criterioConsulta.setInstitucionParticipante(inst);
//		criterioConsulta.setCuentaContraparte(cuenta);
//		
//		servicio.obtenerProyeccionConsultaLiquidacionesEfectivo(criterioConsulta,false);
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//		servicio.obtenerTotalesDeConsultaLiquidacionesEfectivo(criterioConsulta);
//		
//	}
//
//	public void testConsultaMovimientosEfectivoConDosPartes(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//
//		InstitucionDTO institucionParticipante = new InstitucionDTO();
//		institucionParticipante.setClaveTipoInstitucion("01");
//		institucionParticipante.setFolioInstitucion("003");
//		InstitucionDTO institucionContraparte = new InstitucionDTO();
//		institucionContraparte.setClaveTipoInstitucion("02");
//		institucionContraparte.setFolioInstitucion("013");
//
//		CuentaDTO cuentaDTOParticipante = new CuentaDTO();
//		CuentaDTO cuentaDTOContraparte = new CuentaDTO();
//
//		criterioConsulta.setInstitucionParticipante(institucionParticipante);
//		criterioConsulta.setInstitucionContraparte(institucionContraparte);
//		criterioConsulta.setRol(RolConstants.ROL_RECEPTOR);
//		criterioConsulta.setCuentaParticipante(cuentaDTOParticipante);
//		criterioConsulta.setCuentaContraparte(cuentaDTOContraparte);
//
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//	}
//
//	public void testConsultaMovimientosEfectivoConSoloParticipante(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		
//		InstitucionDTO institucionParticipante = new InstitucionDTO();
//		institucionParticipante.setClaveTipoInstitucion("01");
//		institucionParticipante.setFolioInstitucion("003");
//
//		CuentaDTO cuentaDTO = new CuentaDTO();
//
//		criterioConsulta.setInstitucionParticipante(institucionParticipante);
//		criterioConsulta.setRol(RolConstants.ROL_TRASPASANTE);
//		criterioConsulta.setCuentaParticipante(cuentaDTO);
//
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//	}
//
//	public void testConsultaMovimientosEfectivoConSoloContraparte(){
//		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
//		
//		InstitucionDTO institucionContraparte = new InstitucionDTO();
//		institucionContraparte.setClaveTipoInstitucion("02");
//		institucionContraparte.setFolioInstitucion("013");
//
//		CuentaDTO cuentaDTO = new CuentaDTO();
//
//		criterioConsulta.setInstitucionContraparte(institucionContraparte);
//		criterioConsulta.setRol(RolConstants.ROL_RECEPTOR);
//		criterioConsulta.setCuentaContraparte(cuentaDTO);
//
//		ConsultaLiquidacionesEfectivoService servicio = (ConsultaLiquidacionesEfectivoService)getBean("consultaLiquidacionEfectivoService");
//		servicio.consultarLiquidacionesEfectivo(criterioConsulta, null);
//	}
}