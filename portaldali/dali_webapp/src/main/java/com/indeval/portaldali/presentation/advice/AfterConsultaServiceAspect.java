/**
 * Bursatec - INDEVAL
 * Portal DALI
 *
 * AfterThrowingWebAspect.java
 * May 15, 2008
 */
package com.indeval.portaldali.presentation.advice;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.dto.BitacoraIngresosConsulta;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaSaldoPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.advice.BitacoraConsultaService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Implementación de un aspecto interceptor para los servicios de
 * consulta que dejan bitacora.
 * 
 * @author Rafael Ibarra
 * @version 1.0
 * 
 */
@Aspect
public class AfterConsultaServiceAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/** Servicio para el envio del mensaje a Sonic */
	private BitacoraConsultaService bitacoraConsultaService;
	
	/** Indica el mapeo de los nombres de los metodos de consulta
	 * de proyeccion con el nombre de la consulta */
	private Map<String, String> mapaProyecciones;
	
	/**
	 * Intercepta los servicios para guardar la bitacora de las consultas
	 * 
	 * @param returnValue
	 */
	@AfterReturning(
			pointcut="(execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService.obtenerProyeccionDeConsultaDePosiciones(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaSaldosEfectivoService.obtenerProyeccionConsultaSaldosEfectivo(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesNombradas(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesControladas(..))  || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService.buscarDivisasDeSaldosNombrados(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService.buscarDivisasDeSaldosControlados(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService.obtenerProyeccionConsultaOperacionesValor(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService.consultarOperacionesMiscelaneaFiscal(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService.obtenerProyeccionConsultaInstruccionesEfectivo(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService.obtenerProyeccionDeMovimientosDeValor(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosEfectivoService.consultarMovimientosDeEfectivo(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService.archivoConciliacion(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService.consultarMovimientosArchivoConciliacion(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.modelo.CatalogoService.getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService.getSaldoEmisionSociedadesInversionRazonSocial(..)) || " +
					"execution(* com.indeval.portaldali.middleware.services.decretos.LiquidacionDecretosDaliService.getLiquidacionDecretos(..))) && " +
					"args(..,debeDejarLog)",
			returning="returnValue")
	public void afterReturningProyeccionServices(JoinPoint joinPoint, Object returnValue, Boolean debeDejarLog) {
		log.debug("Entrando al metodo AfterThrowingWebAspect.afterReturningProyeccionServices");
		BitacoraIngresosConsulta bitacora = null;
		/* Generacion y Envio del mensaje */
		try {
			if( debeDejarLog != null && debeDejarLog ) {
				bitacora = procesaDatosUsuario(FacesContext.getCurrentInstance());
				procesaDatosProyeccionServicio(bitacora, joinPoint, returnValue);
				bitacoraConsultaService.enviaMensajes(bitacora);
			}
		} catch( Exception ex ) {
			if( bitacora != null ) {
				log.error("ERROR AL MANDAR EL MENSAJE: ["
						+ ToStringBuilder.reflectionToString(bitacora,
								ToStringStyle.MULTI_LINE_STYLE) + "]", ex);
			} else {
				log.error("ERROR AL MANDAR EL MENSAJE", ex);
			}
		}
		
	}
	
	
	
	/**
	 * Metodo que toma el contexto Faces y el StaticPart del PointCut
	 * para formar el objeto y obtener el ticket y la institucion actual
	 * 
	 * @param FacesContext facesContext
	 * @return BitacoraIngresosConsulta
	 */
	private BitacoraIngresosConsulta procesaDatosUsuario(FacesContext facesContext) 
			throws BusinessException {
		BitacoraIngresosConsulta bitacora = new BitacoraIngresosConsulta();
		
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		/*Obtiene el ticket de la sesion*/
		String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
		if( StringUtils.isNotBlank(ticket) ) {
			bitacora.setTicket(ticket);
		} else {
			throw new BusinessException("No se puede obtener el ticket");
		}
		/*Obtiene la institucion primaria*/
		UsuarioDTO user = (UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION);
		if( user != null && user.getInstitucionPrimaria() != null && 
				StringUtils.isNotBlank(user.getInstitucionPrimaria().getClave()) && 
				StringUtils.isNotBlank(user.getInstitucionPrimaria().getFolio()) ) {
			bitacora.setInstitucionPrimaria(user.getInstitucionPrimaria().getClave() + user.getInstitucionPrimaria().getFolio());
		} else {
			throw new BusinessException("No se puede obtener la institucion primaria");
		}
		/*Obtiene la institucion seleccionada*/
		InstitucionVO institucionVO = (InstitucionVO) session.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);
		if( institucionVO != null ) {
			bitacora.setInstitucion(institucionVO.getClave() + institucionVO.getFolio());
		} else {
			throw new BusinessException("No se puede obtener la institucion seleccionada");
		}
		
		return bitacora;
	}
	
	/**
	 * Metodo que toma el contexto Faces y el StaticPart del PointCut
	 * para formar el objeto y obtener el ticket y la institucion actual
	 * 
	 * @param BitacoraIngresosConsulta bitacora
	 * @param JoinPoint.StaticPart staticPart
	 * @param Object returnValue
	 */
	@SuppressWarnings("unchecked")
	private void procesaDatosProyeccionServicio(BitacoraIngresosConsulta bitacora,
			JoinPoint joinPoint, Object returnValue)
			throws BusinessException {
		
		/* Se obtienen los datos dependiendo del tipo de consulta */
		Signature signature = joinPoint.getStaticPart().getSignature();
		String firmaMetodo = signature.getName();
		
		if ( StringUtils.isNotBlank(firmaMetodo) ) {
			firmaMetodo = firmaMetodo.trim();
			bitacora.setRepresentaTotal(true);
			
			String nombreConsulta = mapaProyecciones.get(firmaMetodo);
			
			if( StringUtils.isNotBlank(nombreConsulta) ) {
				bitacora.setNombreConsulta(nombreConsulta);
			} else {
				throw new BusinessException("No hay ninguna coincidencia del metodo");
			}
			
			/* Se obtienen los numeros de registros */
			Number numeroRegistros = null;

			if( firmaMetodo.equalsIgnoreCase("buscarEmisionesDePosicionesNombradas") || 
					firmaMetodo.equalsIgnoreCase("buscarEmisionesDePosicionesControladas") ||
					firmaMetodo.equalsIgnoreCase("buscarDivisasDeSaldosNombrados") || 
					firmaMetodo.equalsIgnoreCase("buscarDivisasDeSaldosControlados") || 
					firmaMetodo.equalsIgnoreCase("getSaldoEmisionSociedadesInversionRazonSocial") ||
					firmaMetodo.equalsIgnoreCase("getLiquidacionDecretos")) {
				List objeto = (List) returnValue;
				numeroRegistros = new Long(objeto != null ? objeto.size() : 0);
				log.debug("Numero de registros: [" + numeroRegistros + "]");
			} else if( firmaMetodo.equalsIgnoreCase("consultarOperacionesMiscelaneaFiscal")) {
				EstadoPaginacionDTO estadoPaginacion =
						(EstadoPaginacionDTO) (joinPoint.getArgs()[1]);
				numeroRegistros = new Long(estadoPaginacion.getTotalResultados());
				log.debug("Numero de registros: [" + numeroRegistros + "]");
			} else if( firmaMetodo.equalsIgnoreCase("consultarMovimientosDeEfectivo") ) {
				numeroRegistros = new Long(procesarResultadoMovimientosEfectivo((List<EstadoCuentaEfectivoPorDivisaDTO>)returnValue));
			} else if( firmaMetodo.equalsIgnoreCase("archivoConciliacion")  || 
					firmaMetodo.equalsIgnoreCase("getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision") ||
					firmaMetodo.equalsIgnoreCase("consultarMovimientosArchivoConciliacion") ) {
				PaginaVO pagina = (PaginaVO)returnValue;
				if( pagina != null && pagina.getTotalRegistros() != null ) {
					numeroRegistros = new Long(pagina.getTotalRegistros().longValue());
				}
			} else {
				numeroRegistros = (Number)returnValue;
			}
			
			if( numeroRegistros != null ) {
				bitacora.setNumeroRegistros(numeroRegistros.longValue());
			} else {
				throw new BusinessException("No se pudo obtener el numero de registros");
			}
			
		} else {
			throw new BusinessException("No se puede obtener la firma del metodo");
		}
		
	}
	
	/**
	 * Metodo auxiliar para el calculo de los registros de movimeintos 
	 * de Efectivo
	 * @param List<EstadoCuentaEfectivoPorDivisaDTO>
	 * @return long
	 * 			Numero de Registros Totales
	 */
	private long procesarResultadoMovimientosEfectivo(
			List<EstadoCuentaEfectivoPorDivisaDTO> returnValue) {
		long numeroRegistros = 0;
		if( returnValue != null ) {
			for(EstadoCuentaEfectivoPorDivisaDTO efectivoPorDivisa : returnValue) {
				if( efectivoPorDivisa != null ) {
					for( DetalleEstadoCuentaSaldoPorBovedaDTO saldoPorBoveda : efectivoPorDivisa.getRegistrosContablesPorBoveda() ) {
						if( saldoPorBoveda.getRegistrosContablesNombradas() != null ) {
							numeroRegistros += saldoPorBoveda.getRegistrosContablesNombradas().size();
						}
						if( saldoPorBoveda.getRegistrosContablesControladas() != null ) {
							numeroRegistros += saldoPorBoveda.getRegistrosContablesControladas().size();
						}
					}
				}
			}
		}
		return numeroRegistros;
	}
	
	/**
	 * @param bitacoraConsultaService the bitacoraConsultaService to set
	 */
	public void setBitacoraConsultaService(
			BitacoraConsultaService bitacoraConsultaService) {
		this.bitacoraConsultaService = bitacoraConsultaService;
	}

	/**
	 * @param mapaProyecciones the mapaProyecciones to set
	 */
	public void setMapaProyecciones(Map<String, String> mapaProyecciones) {
		this.mapaProyecciones = mapaProyecciones;
	}
	
}
