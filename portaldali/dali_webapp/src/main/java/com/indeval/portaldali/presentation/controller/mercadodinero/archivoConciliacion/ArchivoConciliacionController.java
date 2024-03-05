/**
 * 
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.archivoConciliacion;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.model.TipoAccion;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * @author enavarrete
 * 
 */
public class ArchivoConciliacionController extends ControllerBase {

	private String claveInstitucion;
	private AgenteVO agente;
	private EmisionVO emision;
	private BovedaDTO boveda;
	private List<ArchivoConciliacionVO> listaTxt;
	private List listaDetalle;
	private ConciliacionService conciliacionService;
	private boolean reporteListo = false;
	private BigInteger posicionSelected;
	private ArchivoConciliacionMovimientosController archivoConciliacionMovimientosBean;
	private ConsultaMovimientosValorService consultaMovimientosValorService;
	private EmisionVO emisionSelected;
	private String cuentaSelected;
	private int idBovedaSelected;
	private BigInteger posicionInicialDetalle;
	private BigInteger posicionFinalDetalle;
	private boolean debeDejarBitacora;
	

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	/** Identificadores del mercado a filtrar*/    
	private List<Long> idMercadoDinero = null;
	/**Dao para la obtención de la emisión*/
	private EmisionDaliDAO emisionDaliDAO = null;
	/**Servicio para la obtencion de la posicion*/
	private ConsultaPosicionService consultaPosicionService;
	
	/** Servicio para obtener la fecha actual */
	private UtilServices utilService;
	
	public String getInitForm() {
		logger.debug("Entrando a ArchivoConciliacionController");
		agente = new AgenteVO();
		emision = new EmisionVO();
		if( !isUsuarioIndeval() ) {
			claveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			agente.setNombreCorto(getInstitucionActual().getNombreCorto());
		}
		return null;
	}
	
	/**
     * Asigna las opciones predeterminadas para cuando se carga la página por primerva vez.
     * 
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {
         
    	if(claveInstitucion == null || StringUtils.isEmpty(claveInstitucion)){
    		claveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
    	}
        
        //obtener los datos de la posicion
        obtenerDatosPosicion();
        return null;
    }

	public void validaInstitucion(ActionEvent event) {
		logger.debug("Entrando a ArchivoConciliacionController.validaInstitucion");
		if (StringUtils.isNotBlank(claveInstitucion) &&
				claveInstitucion.length() == 5 ) {
			InstitucionDTO inst = consultaCatalogosFacade
					.buscarInstitucionPorIdFolio(claveInstitucion);
			agente = new AgenteVO();
			if (inst != null) {
				agente.setNombreCorto(inst.getNombreCorto());
			} else {
				agente.setNombreCorto(null);
				claveInstitucion = null;
			}
		}

	}

	public void validaCuenta(ActionEvent event) {
		logger.debug("Entrando a ArchivoConciliacionController.validaCuenta");
		if (StringUtils.isNotBlank(claveInstitucion) &&
				claveInstitucion.length() == 5 &&
				StringUtils.isNotBlank(agente.getCuenta()) &&
				agente.getCuenta().length() == 4) {
			InstitucionDTO inst = consultaCatalogosFacade
					.buscarInstitucionPorIdFolio(claveInstitucion);
			if (inst != null) {
				agente.setNombreCorto(inst.getNombreCorto());

				if (StringUtils.isNotBlank(agente.getCuenta()) &&
						agente.getCuenta().length() == 4 ) {
					CuentaDTO cnt = consultaCatalogosFacade
							.buscarCuentaPorNumeroCuentaNullSiNoExiste(claveInstitucion
									+ agente.getCuenta());
					if (cnt != null) {
						agente.setTenencia(cnt.getTipoTenencia()
								.getClaveTipoCuenta());
					} else {
						agente.setTenencia(null);
						agente.setCuenta(null);
					}
				}
			} else {
				agente.setNombreCorto(null);
				claveInstitucion = null;
			}
		}
	}

	public void consultar() {
		logger.debug("Entrando a ArchivoConciliacionController.consultar");

		if ( StringUtils.isBlank(claveInstitucion)
				|| !StringUtils.isNumeric(claveInstitucion)
				&& claveInstitucion.length() != 5) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Institucion invalida", "Institucion invalida");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			paginaVO.setOffset(0);
			ejecutarConsulta();
			reporteListo = true;
		}
	}

	@SuppressWarnings("unchecked")
	public String generaTxt() {
		logger.debug("Entrando a ArchivoConciliacionController.generaTxt");
		PaginaVO paginaReporte = new PaginaVO() ;
		paginaReporte.setRegistrosXPag(PaginaVO.TODOS);
		
		if( isUsuarioIndeval() ) {
			if (StringUtils.isNotBlank(claveInstitucion)
					&& StringUtils.isNumeric(claveInstitucion)
					&& claveInstitucion.length() == 5) {
				agente.setId(claveInstitucion.substring(0, 2));
				agente.setFolio(claveInstitucion.substring(2));
			}
		} else {
			agente.setId(getInstitucionActual().getClaveTipoInstitucion());
			agente.setFolio(getInstitucionActual().getFolioInstitucion());
		}
		
		if( boveda != null && boveda.getId() >= 0 ) {
			paginaReporte.getValores().put( ConciliacionService.BOVEDA, new BigInteger(String.valueOf(boveda.getId())) );
		}
		
		logger.debug("Agente: [" + agente.getId() + agente.getFolio() + "-"
				+ agente.getCuenta() + "]");
		logger.debug("Emision: [" + emision.getIdTipoValor() + "-"
				+ emision.getEmisora() + "-" + emision.getSerie() + "-"
				+ emision.getCupon() + "-" + "]");
		
		paginaReporte = conciliacionService.archivoConciliacion(agente, emision, paginaReporte, debeDejarBitacora);
		
		debeDejarBitacora = false;
		
		if( paginaReporte != null && paginaReporte.getRegistros() != null ) {
			listaTxt = paginaReporte.getRegistros();
		}
		
		return "generaArchivoConciliacionTxt";
	}

	public void limpiar(ActionEvent event) {
		logger.debug("Entrando a ArchivoConciliacionController.limpiar");
		agente = new AgenteVO();
		emision = new EmisionVO();
		claveInstitucion = "";
		boveda = new BovedaDTO();
		reporteListo = false;
		if( !isUsuarioIndeval() ) {
			claveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			agente.setNombreCorto(getInstitucionActual().getNombreCorto());
		}
	}
	
	/**
	 *  Action para mostrar Detalle de Operaciones
	 * 
	 * @return navegacion
	 */
	public String recuperarDetalle() {
		
		logger.debug("Entrando a ArchivoConciliacionController.recuperarDetalle");
		
		generaDetalle(true);
		
		emisionSelected = new EmisionVO();
		
		return "mostrarDetalle";
	}
	
	private void generaDetalle(boolean debeDejarBitacoraDetalle) {
		
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(0);
		if (idBovedaSelected  >= 0) {
			bovedaDTO.setId(idBovedaSelected);
		}
		
		AgenteVO agenteSelected = new AgenteVO();
		agenteSelected.setId(agente.getId());
		agenteSelected.setFolio(agente.getFolio());
		if( StringUtils.isNotBlank(cuentaSelected) ) {
			agenteSelected.setCuenta(cuentaSelected);
		}
		
		listaDetalle = getResultadosNombradasSinAgrupar(agenteSelected,emisionSelected,bovedaDTO,debeDejarBitacoraDetalle);
		
		posicionInicialDetalle = new BigInteger("0");
		try {
			posicionInicialDetalle = conciliacionService.getPosicionInicial(
					agenteSelected, emisionSelected, BigInteger.valueOf(bovedaDTO.getId()));
		} catch (Exception e) {
			logger.error("Error al convertir la posicion");
		}
		
		posicionFinalDetalle = calcularSaldoFinal(posicionInicialDetalle);
		
	}
		
	public void recuperaDetalleTxt(ActionEvent event) {
		generaDetalle(false);
	}
	
	@SuppressWarnings("unchecked")
	private BigInteger calcularSaldoFinal(BigInteger posicionInicial) {
		BigInteger posicionFinal = BigInteger.ZERO;
		
		if( posicionInicial != null ) {
			posicionFinal = new BigInteger(posicionInicial.toString());
		}
		
		logger.debug("Posicion Inicial dentro del metodo: [" + posicionInicial + "]");
		
		if( listaDetalle != null && listaDetalle.size() > 0 ) {
			logger.debug("Tamaño de la lista: [" + listaDetalle.size() + "]");
			for( RegistroContablePosicionNombradaDTO registro : (List<RegistroContablePosicionNombradaDTO>)listaDetalle ) {
				if( registro != null ) {
					registro.setPosicionInicial(posicionFinal);
					if( registro.getTipoAccion().getId() == TipoAccion.ABONO_ACTIVO_VALUE ||
							registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE ) {
						posicionFinal = posicionFinal.add(registro.getCantidad());
						logger.debug("Cantidad a sumar: [" + registro.getCantidad() + "]");
					} else if( registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE ||
							registro.getTipoAccion().getId() == TipoAccion.CARGO_PASIVO_VALUE ) {
						posicionFinal = posicionFinal.subtract(registro.getCantidad());
						logger.debug("Cantidad a restar: [" + registro.getCantidad() + "]");
					} else {
						logger.debug("No sumo ni resto: [" + registro.getCantidad() + "]");
					}
					registro.setPosicionFinal(posicionFinal);
				}
			}
		} else {
			logger.debug("No hay elementos en la lista");
		}
		
		return posicionFinal;
	}

	/**
	 * Metodo para la obtencion de la Posicion a partir
	 * de los campos idFolioParticiante, TV, Emisora, Serie.
	 * 
	 * return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String obtenerDatosPosicion(){
		//primero se obtiene la Posicion
		PosicionDTO posicion = null;
		if (StringUtils.isNotEmpty(agente.getCuenta())
				&& StringUtils.isNotEmpty(emision.getIdTipoValor())
				&& StringUtils.isNotEmpty(emision.getEmisora())
				&& StringUtils.isNotEmpty(emision.getSerie())) {
			PosicionDTO criterio = new PosicionDTO();
			
			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
			criterio.getCuenta().setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() 
					+ getInstitucionActual().getFolioInstitucion() + agente.getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(claveInstitucion);
			
			if (institucion != null ) {
				//cuenta
				if (StringUtils.isNotEmpty(agente.getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + agente.getCuenta());					
				}else {
					criterio.getCuenta().setCuenta("-1");
				}
				
				//TV
				criterio.getEmision().setTipoValor(consultaCatalogosFacade.buscarTipoValorPorClave(emision.getIdTipoValor()));
				//Emisora
				criterio.getEmision().setEmisora(consultaCatalogosFacade.buscarEmisoraPorNombreCorto(emision.getEmisora()));
				criterio.getEmision().setSerie(new SerieDTO());
				//Serie
				criterio.getEmision().getSerie().setSerie(emision.getSerie().trim());
				
				if (boveda != null && boveda.getId()>0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(boveda.getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1",DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());
				
				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO(); 
				orden.setColumna("sortCuenta");
				
				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero.toArray(new Long[]{}));
				
				if (listaPosiciones != null  && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					//obtencion de los datos para ser desplegado en pantalla
					agente.setCuenta(posicion.getCuenta().getCuenta());
					emision.setEmisora(posicion.getEmision().getEmisora().getDescripcion());
					emision.setCupon(posicion.getEmision().getCupon());
					emision.setIsin(posicion.getEmision().getIsin());
					emision.setIdTipoValor(posicion.getEmision().getTipoValor().getClaveTipoValor());
					boveda = new BovedaDTO();
					boveda.setId(posicion.getBoveda().getId());
														
				} else {
					
					emision.setCupon(null);
					emision.setIsin(null);
					boveda = null;
				}
			}
			
		}
		
		if (posicion == null && StringUtils.isNotEmpty(emision.getIdTipoValor())
				&& StringUtils.isNotEmpty(emision.getEmisora())
				&& StringUtils.isNotEmpty(emision.getSerie())) {
			
			EmisionDTO criterio = new EmisionDTO();			
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(emision.getIdTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(emision.getEmisora());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(emision.getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);
			
			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				//pasar los datos al objeto de la pantalla
				
				emision.setIdTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				emision.setEmisora(listaEmisiones.get(0).getEmisora().getDescripcion());
				emision.setIsin(listaEmisiones.get(0).getIsin());
				emision.setSerie(listaEmisiones.get(0).getSerie().getSerie());
				emision.setCupon(listaEmisiones.get(0).getCupon());

			} else {
				emision.setCupon(null);
				emision.setIsin(null);
				boveda = null;
			}
			
		}				
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<RegistroContablePosicionNombradaDTO> getResultadosNombradasSinAgrupar(
			AgenteVO agente, EmisionVO emisionVO, BovedaDTO bovedaDTO, boolean debeDejarBitacoraDetalle) {

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();
		// cuenta contraparte
		CuentaDTO cuentaContraparte = new CuentaDTO();
		TipoTenenciaDTO tipoTenenciaDTO = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturalezaDTO = new TipoNaturalezaDTO();
		InstitucionDTO institucionDTO = new InstitucionDTO();
		institucionDTO.setId(-1);
		tipoNaturalezaDTO.setId("P");
		TipoCuentaDTO tipoCuentaDTO = new TipoCuentaDTO();
		tipoCuentaDTO.setId("N");
		tipoTenenciaDTO.setIdTipoCuenta(-1);
		tipoTenenciaDTO.setTipoCustodia("V");
		tipoTenenciaDTO.setTipoNaturaleza(tipoNaturalezaDTO);
		tipoTenenciaDTO.setTipoCuenta(tipoCuentaDTO);
		cuentaContraparte.setTipoTenencia(tipoTenenciaDTO);
		cuentaContraparte.setNumeroCuenta("-1");
		cuentaContraparte.setInstitucion(institucionDTO);
		criterio.setCuentaContraparte(cuentaContraparte);

		CuentaDTO cuenta = new CuentaDTO();
		InstitucionDTO institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(agente.getId() + agente.getFolio());
		if(institucion == null) {
			institucion = new InstitucionDTO();
			institucion.setId(-1);
		}
		if (StringUtils.isNotBlank(agente.getCuenta())) {
			cuenta.setNumeroCuenta(agente.getId() + agente.getFolio()
					+ agente.getCuenta());
		} else {
			cuenta.setNumeroCuenta("-1");
		}
		cuenta.setInstitucion(institucion);
		cuenta.setTipoTenencia(tipoTenenciaDTO);
		criterio.setCuenta(cuenta);

		// Emision falta id emisora y id tipovalor
		EmisionDTO emisionDTO = new EmisionDTO();
		if (StringUtils.isNotBlank(emisionVO.getIsin())) {
			emisionDTO.setIsin(emisionVO.getIsin());
		}

		SerieDTO serieDTO = new SerieDTO();
		if (StringUtils.isNotBlank(emisionVO.getSerie())) {
			serieDTO.setSerie(emisionVO.getSerie());
		}
		emisionDTO.setSerie(serieDTO);

		EmisoraDTO emisoraDTO = new EmisoraDTO();
		
		emisoraDTO = consultaCatalogosFacade.buscarEmisoraPorNombreCorto(emisionVO.getEmisora());
		
		emisionDTO.setEmisora(emisoraDTO);

		TipoValorDTO tipoValorDTO = new TipoValorDTO();
		
		tipoValorDTO = consultaCatalogosFacade.buscarTipoValorPorClave(emisionVO.getIdTipoValor());
		
		emisionDTO.setTipoValor(tipoValorDTO);

		criterio.setEmision(emisionDTO);

		criterio.setBoveda(bovedaDTO);

		Date fecha = utilService.getCurrentDate();
		Date fechaInicio = getFechaHoraCero(fecha);
		Date fechaFin = getFechaHoraFinDia(fecha);
		criterio.setFechaInicial(fechaInicio);
		criterio.setFechaFinal(fechaFin);
		criterio.setBusquedaFechaAplicacion(false);
		criterio.setBusquedaFechaConcertacion(false);
		// Tipo de Instruccion
		TipoInstruccionDTO tipoInstruccionDTO = new TipoInstruccionDTO();
		tipoInstruccionDTO.setIdTipoInstruccion((long) 0);
		criterio.setTipoInstruccion(tipoInstruccionDTO);
		// Tipo de Operacion
		TipoOperacionDTO tipoOperacionDTO = new TipoOperacionDTO();
		tipoOperacionDTO.setId((long) 0);
		criterio.setTipoOperacion(tipoOperacionDTO);
		// Folio Instruccion
		criterio.setFolioInstruccion(null);
		// Otros
		criterio.setOrdenarPorTipoDeInstruccion(false);
		criterio.setRolContraparte(RolCuentaConstants.AMBOS);
		criterio.setRolParticipante(RolCuentaConstants.AMBOS);

		PaginaVO paginaTemp = new PaginaVO();
		paginaTemp.setRegistrosXPag(PaginaVO.TODOS);

		paginaTemp = consultaMovimientosValorService
				.consultarMovimientosArchivoConciliacion(criterio, null, paginaTemp, debeDejarBitacoraDetalle);
		
		return paginaTemp.getRegistros();
	}
	
	public Date getFechaHoraCero(Date fecha) {

		logger.debug("Entrando a DateUtilsDaoImpl.getFechaHoraCero()");
		logger.debug("La fecha recibida es: [" + fecha + "]");

		if (fecha != null) {
			Calendar calFecha = new GregorianCalendar();
			calFecha.setTime(fecha);
			calFecha.set(Calendar.HOUR_OF_DAY, 0);
			calFecha.set(Calendar.MINUTE, 0);
			calFecha.set(Calendar.SECOND, 0);
			calFecha.set(Calendar.MILLISECOND, 0);
			logger.debug("FechaHoraCero : [" + calFecha.getTime() + "]");
			fecha = calFecha.getTime();
		}
		return fecha;
	}

	public Date getFechaHoraFinDia(Date fecha) {

		logger.debug("Entrando al metodo getFechaHoraFinDia()");
		logger.debug("La fecha recibida es: [" + fecha + "]");

		if (fecha != null) {
			Calendar calFecha = new GregorianCalendar();
			calFecha.setTime(fecha);
			int year = calFecha.get(Calendar.YEAR);
			int month = calFecha.get(Calendar.MONTH);
			int day = calFecha.get(Calendar.DAY_OF_MONTH);
			Calendar cal = new GregorianCalendar(year, month, day, 23, 59, 59);
			logger.debug("FechaHoraFinDia : [" + cal.getTime() + "]");
			fecha = cal.getTime();
		}
		return fecha;
	}
	
	/**
	 * @return the agente
	 */
	public AgenteVO getAgente() {
		return agente;
	}

	/**
	 * @param agente
	 *            the agente to set
	 */
	public void setAgente(AgenteVO agente) {
		this.agente = agente;
	}

	/**
	 * @return the emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @param claveInstitucion
	 *            the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * @return the consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * @param consultaCatalogosFacade
	 *            the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/** Objeto que representa la pagina a consultar */
	public PaginaVO paginaVO = new PaginaVO();

	/** El número de página actual de la consulta */
	private int numeroPagina = -1;

	/**
	 * La cantidad de registros que serán presentados en una página de
	 * resultados
	 */
	private int registrosPorPagina = -1;

	/** El total de páginas de resultados productos de la consulta */
	private int totalPaginas = -1;

	public void setRegistrosXPag(Integer regs) {
		paginaVO.setRegistrosXPag(regs);
	}

	@SuppressWarnings("unchecked")
	public String ejecutarConsulta() {
		logger.debug("Entrando a ArchivoConciliacionController.ejecutarConsulta");
		if( isUsuarioIndeval() ) {
			if (StringUtils.isNotBlank(claveInstitucion)
					&& StringUtils.isNumeric(claveInstitucion)
					&& claveInstitucion.length() == 5) {
				agente.setId(claveInstitucion.substring(0, 2));
				agente.setFolio(claveInstitucion.substring(2));
			}
		} else {
			agente.setId(getInstitucionActual().getClaveTipoInstitucion());
			agente.setFolio(getInstitucionActual().getFolioInstitucion());
		}
		
		if( boveda != null && boveda.getId() >= 0 ) {
			paginaVO.getValores().put( ConciliacionService.BOVEDA, new BigInteger(String.valueOf(boveda.getId())) );
		}
		
		paginaVO = conciliacionService.archivoConciliacion(agente, emision, paginaVO, debeDejarBitacora);
		debeDejarBitacora = false;
		getPaginaNotBlank();
		
		return null;
	}

	/**
	 * Actualiza la información de la paginación al avanzar el número de páginas
	 * solicitadas.
	 * 
	 * @param paginas
	 *            el número de páginas a avanzar.
	 */
	public void avanzar(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if (numeropPag + paginas > getTotalPaginas()) {
				numeropPag = getTotalPaginas();
			} else {
				numeropPag += paginas;
			}
			paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag());
		}
		ejecutarConsulta();

	}

	/**
	 * Actualiza la información de la paginación al retroceder el número de
	 * páginas solicitadas.
	 * 
	 * @param paginas
	 *            el número páginas a retroceder.
	 */
	public void retroceder(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if ((numeropPag - paginas) < 1) {
				numeropPag = 1;
			} else {
				numeropPag -= paginas;
			}
		}
		paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag());
		ejecutarConsulta();
	}

	/**
	 * Actualiza la información de la paginación al establecer la primera página
	 * de resultados como la página actual.
	 */
	public String irPrimeraPagina() {

		if (paginaVO.getTotalRegistros() > 0) {
			numeroPagina = 1;
		}
		paginaVO.setOffset(0);
		ejecutarConsulta();
		return null;
	}

	/**
	 * Actualiza la información de la paginación al establecer la última página
	 * de resultados como la página actual.
	 */
	public String irUltimaPagina() {
		getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			paginaVO.setOffset((getTotalPaginas() - 1)
					* paginaVO.getRegistrosXPag());
		}
		ejecutarConsulta();
		return null;
	}

	public boolean isIrAlPrimero() {

		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isIrAlUltimo() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isAvanzar() {
		boolean resultado = false;
		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isAvanzarRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isRetroceder() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public boolean isRetrocederRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	public String avanzarPagina() {
		avanzar(1);
		return null;
	}

	public String avanzarPaginasRapido() {
		avanzar(3);
		return null;
	}

	public String retrocederPaginasRapido() {
		retroceder(3);
		return null;
	}

	public String retrocederPagina() {
		retroceder(1);
		return null;
	}

	/**
	 * @return the paginaVO
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * @param paginaVO
	 *            the paginaVO to set
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	/**
	 * @return the registrosPorPagina
	 */
	public int getRegistrosPorPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {
			registrosPorPagina = paginaVO.getRegistrosXPag();
		}
		return registrosPorPagina;
	}

	/**
	 * @param registrosPorPagina
	 *            the registrosPorPagina to set
	 */
	public void setRegistrosPorPagina(int registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	/**
	 * @return the numeroPagina
	 */
	public int getNumeroPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {

			if (paginaVO.getOffset() == 0) {
				numeroPagina = 1;
			} else {
				numeroPagina = ((int) Math.ceil(paginaVO.getOffset()
						.doubleValue()
						/ paginaVO.getRegistrosXPag().doubleValue())) + 1;
			}

		} else if (paginaVO != null && paginaVO.getTotalRegistros() <= 0) {
			numeroPagina = 0;
		}
		return numeroPagina;
	}

	/**
	 * @param numeroPagina
	 *            the numeroPagina to set
	 */
	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		if (paginaVO != null && (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros() > 0) &&
				(paginaVO.getRegistrosXPag() != null && paginaVO.getRegistrosXPag() > 0)) {
			totalPaginas = (int) Math
					.ceil((paginaVO.getTotalRegistros().doubleValue() / paginaVO
							.getRegistrosXPag().doubleValue()));
		} else if (paginaVO != null && paginaVO.getTotalRegistros() <= 0) {
			totalPaginas = 0;
		}
		return totalPaginas;
	}

	/**
	 * @param totalPaginas
	 *            the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public void getPaginaNotBlank() {

		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		if (paginaVO.getOffset() == null ) {
			paginaVO.setOffset(0);
		}
		if(paginaVO.getTotalRegistros() == null){
			paginaVO.setTotalRegistros(0);
		}
		
	}

	/**
	 * @param listaTxt
	 *            the listaTxt to set
	 */
	public void setListaTxt(List<ArchivoConciliacionVO> listaTxt) {
		this.listaTxt = listaTxt;
	}

	/**
	 * @return the listaTxt
	 */
	public List<ArchivoConciliacionVO> getListaTxt() {
		return listaTxt;
	}

	/**
	 * @param conciliacionService
	 *            the conciliacionService to set
	 */
	public void setConciliacionService(ConciliacionService conciliacionService) {
		this.conciliacionService = conciliacionService;
	}

	/**
	 * @return the conciliacionService
	 */
	public ConciliacionService getConciliacionService() {
		return conciliacionService;
	}

	/**
	 * @param reporteListo the reporteListo to set
	 */
	public void setReporteListo(boolean reporteListo) {
		this.reporteListo = reporteListo;
	}

	/**
	 * @return the reporteListo
	 */
	public boolean isReporteListo() {
		return reporteListo;
	}

	/**
	 * @param archivoConciliacionMovimientosBean the archivoConciliacionMovimientosBean to set
	 */
	public void setArchivoConciliacionMovimientosBean(
			ArchivoConciliacionMovimientosController archivoConciliacionMovimientosBean) {
		this.archivoConciliacionMovimientosBean = archivoConciliacionMovimientosBean;
	}

	/**
	 * @return the archivoConciliacionMovimientosBean
	 */
	public ArchivoConciliacionMovimientosController getArchivoConciliacionMovimientosBean() {
		return archivoConciliacionMovimientosBean;
	}

	/**
	 * @param listaDetalle the listaDetalle to set
	 */
	public void setListaDetalle(List listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	/**
	 * @return the listaDetalle
	 */
	public List getListaDetalle() {
		return listaDetalle;
	}

	/**
	 * @param posicionSelected the posicionSelected to set
	 */
	public void setPosicionSelected(BigInteger posicionSelected) {
		this.posicionSelected = posicionSelected;
	}

	/**
	 * @return the posicionSelected
	 */
	public BigInteger getPosicionSelected() {
		return posicionSelected;
	}

	/**
	 * Obtiene el valor del atributo idMercadoDinero
	 *
	 * @return el valor del atributo idMercadoDinero
	 */
	public List<Long> getIdMercadoDinero() {
		return idMercadoDinero;
	}

	/**
	 * Establece el valor del atributo idMercadoDinero
	 *
	 * @param idMercadoDinero el valor del atributo idMercadoDinero a establecer
	 */
	public void setIdMercadoDinero(List<Long> idMercadoDinero) {
		this.idMercadoDinero = idMercadoDinero;
	}

	/**
	 * Obtiene el valor del atributo emisionDaliDAO
	 *
	 * @return el valor del atributo emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 *
	 * @param emisionDaliDAO el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo consultaPosicionService
	 *
	 * @return el valor del atributo consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaPosicionService
	 *
	 * @param consultaPosicionService el valor del atributo consultaPosicionService a establecer
	 */
	public void setConsultaPosicionService(
			ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return the consultaMovimientosValorService
	 */
	public ConsultaMovimientosValorService getConsultaMovimientosValorService() {
		return consultaMovimientosValorService;
	}

	/**
	 * @param consultaMovimientosValorService the consultaMovimientosValorService to set
	 */
	public void setConsultaMovimientosValorService(
			ConsultaMovimientosValorService consultaMovimientosValorService) {
		this.consultaMovimientosValorService = consultaMovimientosValorService;
	}

	/**
	 * @param emisionSelected the emisionSelected to set
	 */
	public void setEmisionSelected(EmisionVO emisionSelected) {
		this.emisionSelected = emisionSelected;
	}

	/**
	 * @return the emisionSelected
	 */
	public EmisionVO getEmisionSelected() {
		if( emisionSelected == null ) {
			emisionSelected = new EmisionVO();
		}
		return emisionSelected;
	}

	/**
	 * @param cuentaSelected the cuentaSelected to set
	 */
	public void setCuentaSelected(String cuentaSelected) {
		this.cuentaSelected = cuentaSelected;
	}

	/**
	 * @return the cuentaSelected
	 */
	public String getCuentaSelected() {
		return cuentaSelected;
	}

	/**
	 * @param idBovedaSelected the idBovedaSelected to set
	 */
	public void setIdBovedaSelected(int idBovedaSelected) {
		this.idBovedaSelected = idBovedaSelected;
	}

	/**
	 * @return the idBovedaSelected
	 */
	public int getIdBovedaSelected() {
		return idBovedaSelected;
	}

	/**
	 * @return the posicionInicialDetalle
	 */
	public BigInteger getPosicionInicialDetalle() {
		return posicionInicialDetalle;
	}

	/**
	 * @return the posicionFinalDetalle
	 */
	public BigInteger getPosicionFinalDetalle() {
		return posicionFinalDetalle;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}
	
}
