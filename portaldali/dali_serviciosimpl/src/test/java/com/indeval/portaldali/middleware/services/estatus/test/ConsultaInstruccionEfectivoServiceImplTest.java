/**
 * 
 */
package com.indeval.portaldali.middleware.services.estatus.test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
import com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService;

/**
 * @author Marcos
 *  *
 */
public class ConsultaInstruccionEfectivoServiceImplTest extends BaseTestCase {
	
	public void testConsultarTodosLosMovimientosEfectivo1(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		//inst.setClaveTipoInstitucion("01001");
		//criterioConsulta.setInstitucionParticipante(inst);
		
		
		Date fecha = new Date();
		String fechaString = "14102015";
		SimpleDateFormat day= new SimpleDateFormat("ddMMyyyy");
		Date fechaDate = null;
		try {
		fechaDate = day.parse(fechaString);
		System.out.println("Fecha Dado un String: " + fechaDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//criterioConsulta.setFechaLiquidacion(fechaDate);
		//criterioConsulta.setMontoVencimiento("11121213.54");
		//criterioConsulta.setReferenciaOperacion("0123");
		//EstadoPaginacionDTO estadoPaginacionDTO = new EstadoPaginacionDTO();
		
		/*estadoPaginacionDTO.setNumeroPagina(2);
		estadoPaginacionDTO.setRegistrosPorPagina(20);
		estadoPaginacionDTO.setTotalPaginas(2);
		estadoPaginacionDTO.setTotalResultados(27);
		*/
		//servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		CuentaDTO c = new CuentaDTO();
		InstitucionDTO institucionDTO = new InstitucionDTO();
		//institucionDTO.setId(2);
		//criterioConsulta.setInstitucionContraparte(institucionDTO);
		institucionDTO.setFolioInstitucion("aa");
		institucionDTO.setId(1);
		
		
		c.setCuenta("123442");
		c.setInstitucion(institucionDTO);
		criterioConsulta.setCuentaParticipante(c);
		
		
		
		criterioConsulta.setInstitucionParticipante(institucionDTO);
		//servicio.getProyeccionConsultaInstEfecIntl(criterioConsulta, null);
		servicio.getInstEfecIntl(criterioConsulta, null);	
		//servicio.aprobarRetiroEfectivoInternacionalById("2");
		//servicio.liberarRetiroEfecIntlById("2");
		
		
		///servicio.aprobarRetiroEfectivoInternacionalById("2");
		
		//criterioConsulta.setMonto("100000000");
		//Map<Object, Object> valor  = servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);

		//servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
		
	}
/*
	public void rtestConsultarTodosLosMovimientosEfectivo2(){
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

	public void rtestConsultarTodosLosMovimientosEfectivo3(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		
		InstitucionDTO inst = new InstitucionDTO();
		inst.setClaveTipoInstitucion("01003");
		criterioConsulta.setInstitucionParticipante(inst);
		
		criterioConsulta.setReferenciaOperacion("123123123");
		
		servicio.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta,false);
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
		servicio.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
	}
	
	public void rtestConsultarTodosLosMovimientosEfectivo4(){
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

	public void rtestConsultarTodosLosMovimientosEfectivo5(){
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

	public void rtestConsultarTodosLosMovimientosEfectivo6(){
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

	public void rtestConsultaMovimientosEfectivoConDosPartes(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();

		InstitucionDTO institucionParticipante = new InstitucionDTO();
		institucionParticipante.setClaveTipoInstitucion("01");
		institucionParticipante.setFolioInstitucion("003");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setClaveTipoInstitucion("02");
		institucionContraparte.setFolioInstitucion("013");

		CuentaDTO cuentaDTOParticipante = new CuentaDTO();
		CuentaDTO cuentaDTOContraparte = new CuentaDTO();

		criterioConsulta.setInstitucionParticipante(institucionParticipante);
		criterioConsulta.setInstitucionContraparte(institucionContraparte);
		criterioConsulta.setRol(RolConstants.ROL_RECEPTOR);
		criterioConsulta.setCuentaParticipante(cuentaDTOParticipante);
		criterioConsulta.setCuentaContraparte(cuentaDTOContraparte);

		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
	}

	public void rtestConsultaMovimientosEfectivoConSoloParticipante(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		
		InstitucionDTO institucionParticipante = new InstitucionDTO();
		institucionParticipante.setClaveTipoInstitucion("01");
		institucionParticipante.setFolioInstitucion("003");

		CuentaDTO cuentaDTO = new CuentaDTO();

		criterioConsulta.setInstitucionParticipante(institucionParticipante);
		criterioConsulta.setRol(RolConstants.ROL_TRASPASANTE);
		criterioConsulta.setCuentaParticipante(cuentaDTO);

		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
	}

	public void rtestConsultaMovimientosEfectivoConSoloContraparte(){
		CriterioEstatusOpEfectivoDTO criterioConsulta = new CriterioEstatusOpEfectivoDTO();
		
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setClaveTipoInstitucion("02");
		institucionContraparte.setFolioInstitucion("013");

		CuentaDTO cuentaDTO = new CuentaDTO();

		criterioConsulta.setInstitucionContraparte(institucionContraparte);
		criterioConsulta.setRol(RolConstants.ROL_RECEPTOR);
		criterioConsulta.setCuentaContraparte(cuentaDTO);

		ConsultaInstruccionesEfectivoService servicio = (ConsultaInstruccionesEfectivoService)getBean("consultaInstruccionEfectivoService");
		servicio.consultarInstruccionesEfectivo(criterioConsulta, null);
	}
*/
}