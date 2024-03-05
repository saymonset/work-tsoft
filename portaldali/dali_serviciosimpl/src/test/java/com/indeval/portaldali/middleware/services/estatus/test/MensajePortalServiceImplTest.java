/**
 * 
 */
package com.indeval.portaldali.middleware.services.estatus.test;

import java.util.Date;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService;

/**
 * @author Marcos
 *  *
 */
public class MensajePortalServiceImplTest extends BaseTestCase {
	
	public void testConsultarTodosLosMovimientosEfectivo1(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		criterioConsulta.setInstitucionParticipante(inst);
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
		
	}

	public void testConsultarTodosLosMovimientosEfectivo2(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		criterioConsulta.setInstitucionParticipante(inst);
		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setCuenta("2000");
		criterioConsulta.setCuentaParticipante(cuenta);
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
		
	}

	public void testConsultarTodosLosMovimientosEfectivo3(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		criterioConsulta.setInstitucionParticipante(inst);
		
		//criterioConsulta.setReferencia("123123123");
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
	}
	
	public void testConsultarTodosLosMovimientosEfectivo4(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		criterioConsulta.setInstitucionParticipante(inst);
		
		criterioConsulta.setRol(1);
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
	}

	public void testConsultarTodosLosMovimientosEfectivo5(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		Date fecha = new Date();

		criterioConsulta.setInstitucionParticipante(inst);
		criterioConsulta.setFechaLiquidacion(fecha);
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
	}

	public void testConsultarTodosLosMovimientosEfectivo6(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setCuenta("2000");
		criterioConsulta.setInstitucionParticipante(inst);
		criterioConsulta.setCuentaContraparte(cuenta);
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
		
	}

	
}
