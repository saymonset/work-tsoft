/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 24/02/2008
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoLibrePagoDTO;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Backing bean para el servicio a la clase de fondeo de valores
 * 
 * @author Rafael Ibarra
 * 
 */
public class FondeoOperacionesBean extends ControllerBase {
	
	/**
	 * Servicio para la consulta del registro de Intruccion operacion Val 
	 */
	private ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService;
	
	/**
	 * Representa la operacion actual
	 */
	private OperacionValorMatchDTO operacion;
	
	/**
	 * Representa el saldo actual de la operacion
	 */
	private BigInteger saldoActual;
	
	/**
	 * Representa el saldo pendiente
	 */
	private BigInteger saldoPendiente;
	
	/**
	 * Representa el saldo fondeado en la pantalla
	 */
	private BigInteger saldoFondeado;
	
	/**
	 * Lista de posiciones disponibles para fondeo
	 */
	private List<PosicionDTO> posicionesDisponibles;
	
	/**
	 * Indica la posicion selecciondad para la colocacióndel saldo
	 */
	private long posicionSelectedColocaSaldo;
	
	/** 
	 * Validador de la Captura Operaciones Reporto Nominal Opcion Mismo Dia 
	 */
    private DTOValidator validadorTraspasoLibrePago;
	
    
    /**
     * Servicio de cosultas generales
     */
    private ConsultaCatalogosFacade consultaCatalogosFacade; 
    
    /** 
     * Servicio helper para la captura de operaciones 
     */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper;
    
    /**
     * Servicio para Mercado de Capitales
     */
    private MercadoCapitalesService mercadoCapitalesService;
    
    /**
     * Servicio para Mercado de Capitales
     */
    private MercadoDineroService mercadoDineroService;
    
    /** 
     * Servicio para sacar folio de una secuencia 
     */
    private UtilServices utilService;
    
    /** 
     * Servicio de negocio relacionado con el envo de operaciones 
     */
    private EnvioOperacionesService envioOperacionesService;
    
	public String getInit() {
		logger.info("Entrando al metodo FondeoOperacionesBean.getInit");
		saldoFondeado = BigInteger.valueOf(0);
		BigInteger idOperacion = BigInteger.valueOf(0);
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idOp = params.get("idOperacion");
		
		if( StringUtils.isNotBlank(idOp) ) {
			
			try {
				idOperacion = new BigInteger(idOp);
			} catch (Exception e) {
				idOperacion = BigInteger.valueOf(0);
				logger.error("Error al convertir el parametro del Id de Operacion");
			}
			
		}
		
		if( idOperacion != null && idOperacion.compareTo(BigInteger.ZERO) > 0 ) {
			List<OperacionValorMatchDTO> lista = consultaEstatusOperacionesMatchService.consultarInstruccionOperacionValorPorId(idOperacion.longValue());
			if( lista != null && lista.size() > 0 ) {
				operacion = lista.get(0);
				saldoActual = getSaldoDisponible();
				saldoPendiente = BigInteger.valueOf(operacion.getCantidadTitulos()).subtract(saldoActual);
				posicionesDisponibles = consultarPosicionesDisponibles();
			}
		}
		
		return null;
	}
	
	public void calculaTotal(ActionEvent event) {
		logger.info("Entrando al metodo FondeoOperacionesBean.calculaTotal");
		
		calculaTotal();
	}
	
	
	
	public boolean isPosicionFondeoMenor() {
		if( saldoFondeado.compareTo(saldoPendiente) < 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void colocaSaldo() {
		logger.info("Entrando al metodo FondeoOperacionesBean.colocaSaldo");
		
		PosicionDTO seleccion = null;
		BigInteger diferencia = getSaldoDiferencia();
		if( diferencia.compareTo(BigInteger.ZERO) > 0 ) {
		
			for( PosicionDTO pos : posicionesDisponibles ) {
				if( pos.getIdPosicion() == posicionSelectedColocaSaldo ) {
					seleccion = pos;
					break;
				}
			}
			
			if( seleccion != null ) {
				
				BigInteger saldoDisponible = seleccion.getPosicionDisponibleNumerica().toBigInteger();
				
				logger.info("Diferencia: [" + diferencia + "]");
				logger.info("Disponible: [" + saldoDisponible + "]");
				
				int comparacion = diferencia.compareTo(saldoDisponible);
				
				logger.info("Compare: [" + comparacion + "]");
				
				// El saldo por fondear es mayor del saldo disponible de esta cuenta
				if( comparacion > 0 ) {
					seleccion.setPosicionFondeo(saldoDisponible);
				// El saldo por fondear es menor o igual del saldo disponible de esta cuenta
				} else {
					seleccion.setPosicionFondeo(diferencia);
				}
				
				logger.info("Posicion fondeo: [" + seleccion.getPosicionFondeo() + "]");
				
				calculaTotal();
			} else {
				logger.info("NO encontro concidencias");
			}
			
		} else {
			logger.info("No es necesario sumar de esta cuenta.");
		}
		
		posicionSelectedColocaSaldo = 0;
	}
	
	private void calculaTotal() {
		logger.info("Entrando al metodo FondeoOperacionesBean.calculaTotal privado");
		saldoFondeado = BigInteger.valueOf(0);
		
		for( PosicionDTO posicion : posicionesDisponibles ) {
			if( posicion.getPosicionFondeo() == null ) {
				posicion.setPosicionFondeo(BigInteger.valueOf(0));
			}
			
			if( posicion.isPosicionFondeoMayor() ) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"La cantidad a fondear desde la cuenta [" + posicion.getCuenta().getCuenta() + "] no puede ser mayor que la posicion disponible",
						"La cantidad a fondear desde la cuenta [" + posicion.getCuenta().getCuenta() + "] no puede ser mayor que la posicion disponible");
				facesContext.addMessage(null, message);
			}
			
			saldoFondeado = saldoFondeado.add(posicion.getPosicionFondeo());
		}
	}
	
	public void limpiaSaldos(ActionEvent event) {
		logger.info("Entrando al metodo FondeoOperacionesBean.limpiaSaldos");
		
		for( PosicionDTO posicion : posicionesDisponibles ) {
			if( posicion != null ) {
				posicion.setPosicionFondeo(BigInteger.valueOf(0));
			}
		}
		saldoFondeado = BigInteger.valueOf(0);
	}
	
	public void guardaOperaciones(ActionEvent event) {
		logger.info("Entrando al metodo FondeoOperacionesBean.guardaOperaciones");
		
		for( PosicionDTO posicion : posicionesDisponibles ) {
			if( posicion != null && 
					posicion.getPosicionFondeo() != null &&
					posicion.getPosicionFondeo().compareTo(BigInteger.ZERO) > 0 ) {
				
				TraspasoLibrePagoDTO traspasoLibrePago = new TraspasoLibrePagoDTO();
				
				traspasoLibrePago.setCantidadOperada(posicion.getPosicionFondeo().longValue());
				traspasoLibrePago.setCuentaReceptor(operacion.getCuentaNombradaTraspasante());
				traspasoLibrePago.setDiasVigentes(0);
				traspasoLibrePago.setDivisa(new DivisaDTO());
				traspasoLibrePago.getDivisa().setId(0);
				traspasoLibrePago.setFechaConcertacion(utilService.getCurrentDate());
				traspasoLibrePago.setIdFolioReceptor(posicion.getCuenta().getInstitucion().getClaveTipoInstitucion()+posicion.getCuenta().getInstitucion().getFolioInstitucion());
				traspasoLibrePago.setIdFolioTraspasante(operacion.getInstitucionTraspasante().getClaveTipoInstitucion()+operacion.getInstitucionTraspasante().getFolioInstitucion());
				traspasoLibrePago.setIsin(operacion.getEmision().getIsin());
				traspasoLibrePago.setNetoEfectivo(0.0);
				traspasoLibrePago.setBoveda(operacion.getBovedaDTO());
				PosicionDTO posicionTraspasante = new PosicionDTO();
				posicionTraspasante.setBoveda(new BovedaDTO());
				posicionTraspasante.getBoveda().setNombreCorto(operacion.getBoveda());
				posicionTraspasante.setCuenta(posicion.getCuenta());
				posicionTraspasante.setCupon(operacion.getCupon());
				posicionTraspasante.setEmision(operacion.getEmision());
				posicionTraspasante.getEmision().setCupon(operacion.getCupon().getClaveCupon());
				traspasoLibrePago.setPosicionTraspasante(posicionTraspasante);
				 
				procesarDatos(traspasoLibrePago);
			}
		}
		
	}
	
	private void procesarDatos(TraspasoLibrePagoDTO traspasoLibrePago) {
		logger.info("Entrando al metodo FondeoOperacionesBean.procesarDatos");
		boolean valido = validarDTO(traspasoLibrePago);
		if (valido) {
			String mercado = traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado();
			if( mercado != null ) {
				if(mercado.trim().equalsIgnoreCase("MC")) {
					calcularYRegistrarOperacionCapitales(traspasoLibrePago);
				} else if(mercado.trim().equalsIgnoreCase("MD") ||
        				mercado.trim().equalsIgnoreCase("PG") ||
        				mercado.trim().equalsIgnoreCase("PB")) {
					calcularYRegistrarOperacionDinero(traspasoLibrePago);
				}
			}
		}
	}
	
	private boolean validarDTO(TraspasoLibrePagoDTO traspasoLibrePago) {
        ResultadoValidacionDTO resultadao = null;
        resultadao = validadorTraspasoLibrePago.validarDTO(traspasoLibrePago);
        if (!resultadao.isValido()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
        }
        return resultadao.isValido();
    }
	
	private void calcularYRegistrarOperacionCapitales(TraspasoLibrePagoDTO traspasoLibrePago) {
		logger.info("Entrando al metodo FondeoOperacionesBean.calcularYRegistrarOperacionCapitales");

		// buscar la divisa por id
        traspasoLibrePago.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId()));
        
        GetCapturaTraspasoParams registraOperacionParams = servicesCapturaOperacionViewHelper.construyeYCalculaOperacionCapitales(traspasoLibrePago);

        if(traspasoLibrePago != null) {
        	String mercado = traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado();
        	if( mercado != null ) {
        		String folioAsignado = null;
        		
        		folioAsignado = mercadoCapitalesService.businessRulesCapturaTraspaso(registraOperacionParams);
		        
		        if (!existeErrorEnInvocacion()) {
		        	TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
		
		            vo = servicesCapturaOperacionViewHelper.inicializaTraspasoContrapagoVO(registraOperacionParams);
		            vo.setReferenciaOperacion(folioAsignado);
		            vo.setTipoInstruccion(registraOperacionParams.getCveReporto());
		            vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
		            vo.setBoveda(null != traspasoLibrePago.getBoveda() ? traspasoLibrePago.getBoveda().getNombreCorto():null);
		            
		            logger.info("Traspaso: [" + ToStringBuilder.reflectionToString(vo) + "]");
		            
		            enviarOperacionABitacora(vo, folioAsignado);
		        }
        	}
        }
    }
	
	
	private void calcularYRegistrarOperacionDinero( TraspasoLibrePagoDTO traspasoLibrePago ) {
		logger.info("Entrando al metodo FondeoOperacionesBean.calcularYRegistrarOperacionDinero");

        // buscar la divisa por id
        traspasoLibrePago.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId()));

        RegistraOperacionParams registraOperacionParams = servicesCapturaOperacionViewHelper.construyeYCalculaOperacionDinero(traspasoLibrePago);
        String folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
        if (!existeErrorEnInvocacion()) {
        	TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

            vo = servicesCapturaOperacionViewHelper.inicializaTraspasoLibrePagoVO(registraOperacionParams);
            vo.setReferenciaOperacion(folioAsignado);
            vo.setTipoInstruccion(registraOperacionParams.getClaveReporto());
            vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
            vo.setBoveda(null != traspasoLibrePago.getBoveda() ? traspasoLibrePago.getBoveda().getNombreCorto():null);

            logger.info("Traspaso: [" + ToStringBuilder.reflectionToString(vo) + "]");
            enviarOperacionABitacora(vo, folioAsignado);
        }
    }
	
	private void enviarOperacionABitacora(TraspasoLibrePagoVO vo, String folioAsignado) {
		String folioControl = vo.getReferenciaOperacion();
		
		logger.info("Traspaso: [\n" + ToStringBuilder.reflectionToString(vo,ToStringStyle.MULTI_LINE_STYLE) + "\n" +
				"Folio Asignado: " + folioAsignado + "\n]");
		
    	envioOperacionesService.grabaOperacion(vo, folioControl, false, null, null, null);
        if (!existeErrorEnInvocacion()) {
            mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
        }
	}
	
	private List<PosicionDTO> consultarPosicionesDisponibles() {
		AgenteVO agente = new AgenteVO();
		EmisionVO emision = new EmisionVO();
		
		emision.setIdTipoValor(operacion.getEmision().getTipoValor().getClaveTipoValor());
		emision.setEmisora(operacion.getEmision().getEmisora().getDescripcion());
		emision.setSerie(operacion.getEmision().getSerie().getSerie());
				
		CuentaDTO cuentaTraspasante = operacion.getCuentaNombradaTraspasante();
		agente.setId(cuentaTraspasante.getInstitucion().getClaveTipoInstitucion());
		agente.setFolio(cuentaTraspasante.getInstitucion().getFolioInstitucion());
		agente.setCuenta(cuentaTraspasante.getCuenta());
		
		return consultaEstatusOperacionesMatchService.getListaPosiciones(agente, emision);
	}

	private BigInteger getSaldoDisponible() {
		if( operacion != null ) {
			AgenteVO agente = new AgenteVO();
			CuentaDTO cuentaTraspasante = operacion.getCuentaNombradaTraspasante();
			agente.setId(cuentaTraspasante.getInstitucion().getClaveTipoInstitucion());
			agente.setFolio(cuentaTraspasante.getInstitucion().getFolioInstitucion());
			agente.setCuenta(cuentaTraspasante.getCuenta());
			EmisionVO emision = new EmisionVO();
			EmisionDTO emisionDto = operacion.getEmision();
			emision.setIdTipoValor(emisionDto.getTipoValor().getClaveTipoValor());
			emision.setEmisora(emisionDto.getEmisora().getDescripcion());
			emision.setSerie(emisionDto.getSerie().getSerie());
			emision.setCupon(operacion.getCupon().getClaveCupon());
			emision.setIsin(emisionDto.getIsin());
			BigInteger bovedaId = BigInteger.valueOf(0);
			if( operacion.getBovedaDTO() != null ) {
				bovedaId = BigInteger.valueOf(operacion.getBovedaDTO().getId());
			}
			
			return consultaEstatusOperacionesMatchService.getSaldoActual(agente, emision, bovedaId);
			
		}
		return BigInteger.valueOf(0);
	}
	
	public BigInteger getSaldoDiferencia() {
		if( saldoPendiente == null ) {
			saldoPendiente = BigInteger.valueOf(0);
		} 
		
		if( saldoFondeado == null ) {
			saldoFondeado = BigInteger.valueOf(0);
		} 
		
		return saldoPendiente.subtract(saldoFondeado);
	}

	/**
	 * @param consultaEstatusOperacionesMatchService the consultaEstatusOperacionesMatchService to set
	 */
	public void setConsultaEstatusOperacionesMatchService(
			ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService) {
		this.consultaEstatusOperacionesMatchService = consultaEstatusOperacionesMatchService;
	}

	/**
	 * @return the operacion
	 */
	public OperacionValorMatchDTO getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(OperacionValorMatchDTO operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the saldoActual
	 */
	public BigInteger getSaldoActual() {
		return saldoActual;
	}

	/**
	 * @param saldoActual the saldoActual to set
	 */
	public void setSaldoActual(BigInteger saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * @param saldoPendiente the saldoPendiente to set
	 */
	public void setSaldoPendiente(BigInteger saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	/**
	 * @return the saldoPendiente
	 */
	public BigInteger getSaldoPendiente() {
		return saldoPendiente;
	}

	/**
	 * @return the posicionesDisponibles
	 */
	public List<PosicionDTO> getPosicionesDisponibles() {
		return posicionesDisponibles;
	}

	/**
	 * @param posicionesDisponibles the posicionesDisponibles to set
	 */
	public void setPosicionesDisponibles(List<PosicionDTO> posicionesDisponibles) {
		this.posicionesDisponibles = posicionesDisponibles;
	}

	/**
	 * @return the saldoFondeado
	 */
	public BigInteger getSaldoFondeado() {
		return saldoFondeado;
	}

	/**
	 * @param saldoFondeado the saldoFondeado to set
	 */
	public void setSaldoFondeado(BigInteger saldoFondeado) {
		this.saldoFondeado = saldoFondeado;
	}

	/**
	 * @return the posicionSelectedColocaSaldo
	 */
	public long getPosicionSelectedColocaSaldo() {
		return posicionSelectedColocaSaldo;
	}

	/**
	 * @param posicionSelectedColocaSaldo the posicionSelectedColocaSaldo to set
	 */
	public void setPosicionSelectedColocaSaldo(long posicionSelectedColocaSaldo) {
		this.posicionSelectedColocaSaldo = posicionSelectedColocaSaldo;
	}

	/**
	 * @param validadorTraspasoLibrePago the validadorTraspasoLibrePago to set
	 */
	public void setValidadorTraspasoLibrePago(
			DTOValidator validadorTraspasoLibrePago) {
		this.validadorTraspasoLibrePago = validadorTraspasoLibrePago;
	}

	/**
	 * @param consultaCatalogosFacade the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * @param servicesCapturaOperacionViewHelper the servicesCapturaOperacionViewHelper to set
	 */
	public void setServicesCapturaOperacionViewHelper(
			ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * @param mercadoCapitalesService the mercadoCapitalesService to set
	 */
	public void setMercadoCapitalesService(
			MercadoCapitalesService mercadoCapitalesService) {
		this.mercadoCapitalesService = mercadoCapitalesService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @param envioOperacionesService the envioOperacionesService to set
	 */
	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * @param mercadoDineroService the mercadoDineroService to set
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}
}
