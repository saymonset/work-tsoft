/*
 *Copyright (c) 2005-2006 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.services.decretos.LiquidacionDecretosDaliService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.TesoreriaConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.dto.tesoreria.LiquidacionDecretosDTO;
import com.indeval.portaldali.presentation.dto.tesoreria.LiquidacionDecretosDetalleDTO;
import com.indeval.portaldali.presentation.util.VO2SelectItem;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.EmisionVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO;

/**
 * Controller que da soporte a la pantalla de Tesorer&iacute;a -> Estado de
 * Cuenta de Liq. por Decretos. Este controller consume servicios del
 * m&oacute;dulo Ejercicio de Derechos
 * 
 * @author Emigdio Hern&aacute;ndez
 * @version 1.0
 */
public class ConsultaEstadoCuentaLiquidacionDecretosController extends ControllerBase {

	/**
	 * Parametros de consulta
	 */
	private LiquidacionDecretosParams edoCtaLiqParams = new LiquidacionDecretosParams();

	private LiquidacionDecretosDetalleVO[] detalle = null;

	/** detalle de la amortizacion */

	/** Registros resultados del detalle de amortizacion */
	
	private List<LiquidacionDecretosDetalleAmortizacionesVO> detalleAmortizaciones = null;

	/**
	 * Servicio de tesoreria
	 */
	private TesoreriaService tesoreriaService = null;
	/**
	 * servicio para consultar la lista de tipos de ejercicio
	 */
	private LiquidacionDecretosDaliService liquidacionDecretosDaliService = null;

	private FechasUtilService fechasUtilService = null;
	/**
	 * tipos de ejercicio de ejercicio de derechos
	 */
	private SelectItem[] listaTipoEjercicio = null;
	/**
	 * Lista de divisas de ejercicio de derechos
	 */
	private SelectItem[] listaDivisas = null;
	/**
	 * Id y folio de la institucion
	 */
	private String idFolioParticipante = null;
	/**
	 * Indica si se estï¿½n desplegando resultados
	 */
	private boolean consultaEjecutada = false;

	private int indiceInstitucionActual = 0;

	private String idFolioActual = "";

	private int indiceTipoDerechoActual = 0;

	private String descripcionDerechoActual = "";
	
	private List<LiquidacionDecretosVO> datosLiquidacion=null;
	
	private List <LiquidacionDecretosDTO> resultados = null;
	
	private List <LiquidacionDecretosDetalleDTO> detalles = null;
	
	private UtilServices utilService; 
	
	private int totalTiposDerecho = 0;

	private int totalInstituciones = 0;

	private String tipoDerechoActual = null;

	private String idFolioInstitucionActual = null;

	private BigDecimal subTotalImporteCobrado = null;

	private BigDecimal subTotalImporteACobrar = null;

	private BigDecimal subTotalRemanente = null;

	private BigDecimal totalImporteCobrado = null;

	private BigDecimal totalImporteACobrar = null;

	private BigDecimal totalRemanente = null;

	private int totalResultados = 0;
	
	private boolean existenResultados = false;

	/** Indica si la consulta de decretos debe dejar bitacora o no */
	private boolean debeDejarBitacora;
	
	/** Constante que indica que es cobro */
	private final String COBROS = "COBROS";
        
        /** Agente para consultar el detalle de amortizaciones */
	private AgenteVO agente;
        
        /** Folio Fija para consultar el detalle de amortizaciones */
	private String folioFija;
        
        /** Folio Variable para consultar el detalle de amortizaciones */
	private String folioVariable;
        
        /** Id derecho para consultar el detalle de amortizaciones */
	private Integer idDerecho;
        
        /** Id tipo derecho para consultar el detalle de amortizaciones */
	private Integer idTipoDerecho;
        
        /** Id tipo Ejercicio para consultar el detalle de amortizaciones */
	private Integer idTipoEjercicio;
        
    /** Tipo Ejercicio para consultar el detalle de amortizaciones */
    private String tipoEjercicioDetAmrt;

    private List<AgenteVO> institucionesActuales;

    List<String> tiposDerechoActuales;
    
    private List <LiquidacionDecretosDTO> registrosCompletos = null;

    private Map<String, List<String>> institucionesTiposEjercicio = null;


	private String comboPaginacion = null;

	
	public void setComboPaginacion(String comboPaginacion) {
		this.comboPaginacion = comboPaginacion;
	}
	
	public String getComboPaginacion() {
		return comboPaginacion;
	}
	
	
	
	/**
	 * Inicializa el estado de los campos de consulta
	 */
	private void inicializarCampos() {
		edoCtaLiqParams = new LiquidacionDecretosParams();
		edoCtaLiqParams.setAgente(new AgenteVO());
		edoCtaLiqParams.getAgente().setId(getInstitucionActual().getClaveTipoInstitucion());
		edoCtaLiqParams.getAgente().setFolio(getInstitucionActual().getFolioInstitucion());
		edoCtaLiqParams.setTipoConsulta(TesoreriaService.COBROS);
		edoCtaLiqParams.setEmision(new EmisionVO());
		idFolioParticipante = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		edoCtaLiqParams.setTipoMoneda(TesoreriaConstantes.CLAVE_MEXICAN_PESO);
		consultaEjecutada = false;
		datosLiquidacion = null;
		resultados = null;
		registrosCompletos = null;
	}

	/**
	 * Inicializa los criteros
	 * 
	 * @return
	 */
	public String getInit() {
		inicializarCampos();
		paginacion.setRegistrosPorPagina(50);
		edoCtaLiqParams.setFechaFin(fechasUtilService.getCurrentDate());
		edoCtaLiqParams.setFechaIni(fechasUtilService.getCurrentDate());

		return null;
	}

	/**
	 * Inicializa los datos a mostrar del detalle
	 * 
	 * @return
	 */
	public String getInitDetalle() {
		LiquidacionDecretosDetalleParams paramsDetalle = new LiquidacionDecretosDetalleParams();

		paramsDetalle.setAgente(new AgenteVO(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("id"),
				FacesContext.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("folio"), FacesContext
						.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("cuenta")));
		paramsDetalle
				.setFolioFija(FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap().get(
								"folioFija"));
		paramsDetalle.setFolioVariable(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get(
						"folioVariable"));
		paramsDetalle.setIdDerecho(NumberUtils.toInt(FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("idDerecho")));
		paramsDetalle.setIdTipoDerecho(NumberUtils.toInt(FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("idTipoDerecho")));
		paramsDetalle.setIdTipoEjercicio(NumberUtils.toInt(FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("idTipoEjercicio")));

		try {
			detalle = new LiquidacionDecretosDetalleVO[] { liquidacionDecretosDaliService
					.getLiquidacionDecretosDetalle(paramsDetalle) };
			
			if(detalle.length > 0) {
				existenResultados = Boolean.TRUE;
				muestraDetalle();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e.getMessage());
		}

		return null;
	}
	
	public void muestraDetalle(){
		
		detalles=new ArrayList<LiquidacionDecretosDetalleDTO>();
		for(LiquidacionDecretosDetalleVO datos: detalle){
			LiquidacionDecretosDetalleDTO dato =new LiquidacionDecretosDetalleDTO(datos);
			
			BovedaDTO nombreCortoBov= utilService.obtenerNombreCortoBoveda(datos.getIdBovedaEfectivo());
			if(nombreCortoBov.getNombreCorto()!= null || (! StringUtils.isEmpty(nombreCortoBov.getNombreCorto()))){
				dato.setNombreCorto(nombreCortoBov.getNombreCorto());
			}
			detalles.add(dato);			
		}
	}

	/**
	 * Inicializa los datos a mostrar del detalle de amortizacion
	 * 
	 * @return este metodo no regresa nada
	 */
	@SuppressWarnings("unchecked")
	public String getInitDetalleAmortizacion() {
		try {
			LiquidacionDecretosDetalleAmortizacionesParams amortizacionParams = new LiquidacionDecretosDetalleAmortizacionesParams();
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")
                                && null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folio")
                                && null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cuenta")) {
                            setAgente(new AgenteVO(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"), 
                                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folio"), 
                                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cuenta")));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folioFija")) {
                            setFolioFija(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folioFija"));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folioVariable")) {
                            setFolioVariable(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("folioVariable"));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDerecho")) {
                            setIdDerecho(NumberUtils.toInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDerecho")));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoDerecho")) {
                            setIdTipoDerecho(NumberUtils.toInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoDerecho")));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoEjercicio")) {
                            setIdTipoEjercicio(NumberUtils.toInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoEjercicio")));
                        }
                        if (null != FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoEjercicio")) {
                            setTipoEjercicioDetAmrt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoEjercicio"));
                        }
			amortizacionParams.setAgente(getAgente());
			amortizacionParams.setFolioFija(getFolioFija());
			amortizacionParams.setFolioVariable(getFolioVariable());
			amortizacionParams.setIdDerecho(getIdDerecho());
			amortizacionParams.setIdTipoDerecho(getIdTipoDerecho());
			amortizacionParams.setIdTipoEjercicio(getIdTipoEjercicio());
			String tipoEjercicio = getTipoEjercicioDetAmrt();
			
			detalleAmortizaciones = liquidacionDecretosDaliService.getLiquidacionDecretosDetalleAmortizaciones(amortizacionParams);
			if (detalleAmortizaciones == null) {
				detalleAmortizaciones = new ArrayList<LiquidacionDecretosDetalleAmortizacionesVO>();
			}
			else {
				for (LiquidacionDecretosDetalleAmortizacionesVO decretosDetalleAmortizacionesVO : detalleAmortizaciones) {
					decretosDetalleAmortizacionesVO.setDescEjercicio(tipoEjercicio);
				}
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
		    logger.debug("\n####### ERROR=[" + e.getMessage() + "]");
			throw new BusinessException(e.getMessage());
		}
                
                return null;
	}

	public void limpiar(ActionEvent e) {
		inicializarCampos();
	}

	/**
	 * Recupera la lista de tipos de ejercicio
	 * 
	 * @return Arreglo con los tipos de ejercicio en formato de SelectItem
	 */
	public SelectItem[] getTipoEjercicio() {

		if (listaTipoEjercicio == null) {
			try {
				
				 listaTipoEjercicio = VO2SelectItem.encode(
						liquidacionDecretosDaliService.getListaTiposEjercicio(),
						"descTipoEjercicio", "cveTipoEjercicio");
			} catch (Exception e) {
				throw new BusinessException(e.getMessage(),e);
			}
		}

		return listaTipoEjercicio;
	}

	/**
	 * Avanza hacia el siguiente tipo de derecho disponible
	 * 
	 * @param ev
	 */
	public void tipoDerechoSiguiente(ActionEvent ev) {
		LiquidacionDecretosParams parametros = crearCriterio();

		if (StringUtils.isEmpty(this.idFolioParticipante)) {
			parametros.getAgente().setId(obtenerIdInstitucion(this.idFolioInstitucionActual));
			parametros.getAgente().setFolio(obtenerFolioInstitucion(this.idFolioInstitucionActual));
		}
		try {
            this.indiceTipoDerechoActual++;

            this.prepararDatosTipoDerechoActual(parametros, this.tiposDerechoActuales);

			ejecutarConsulta(parametros, true);
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrocede hacia el tipo de derecho anterior
	 * 
	 * @param ev
	 */
	public void tipoDerechoAnterior(ActionEvent ev) {
		LiquidacionDecretosParams parametros = crearCriterio();

		if (StringUtils.isEmpty(this.idFolioParticipante)) {
			parametros.getAgente().setId(obtenerIdInstitucion(this.idFolioInstitucionActual));
			parametros.getAgente().setFolio(obtenerFolioInstitucion(this.idFolioInstitucionActual));
		}
		try {
			this.indiceTipoDerechoActual--;

			this.prepararDatosTipoDerechoActual(parametros, this.tiposDerechoActuales);

			ejecutarConsulta(parametros, true);
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asigna el indice de tipo de derecho actual y el listado actual de tipos de derecho obteniendo el listado siguiente de tipos de derecho del 
	 * mapa de instituciones y tipos de ejercicios. 
	 */
	private void asignarListadoTiposDerechoEIndice() {
	    List<String> lstDerechosSiguientes = this.institucionesTiposEjercicio.get(this.idFolioInstitucionActual);
	    if (lstDerechosSiguientes.contains(this.tipoDerechoActual)) {
	        for (int i = 0; i < lstDerechosSiguientes.size(); i++) {
	            if (this.tipoDerechoActual.equals(lstDerechosSiguientes.get(i))) {
	                this.indiceTipoDerechoActual = i;
	                break;
	            }
	        }
	    }
	    else {
	        this.indiceTipoDerechoActual = 0;
	    }
        this.tiposDerechoActuales = lstDerechosSiguientes;
        this.totalTiposDerecho = this.tiposDerechoActuales.size();
	}

	/**
	 * Navega hacia la institucion siguiente en la lista de resultado de instituciones.
	 * 
	 * @param ev
	 */
	public void institucionSiguiente(ActionEvent ev) {
		LiquidacionDecretosParams parametros = crearCriterio();

		try {
		    this.indiceInstitucionActual++;

			totalInstituciones = this.institucionesActuales.size();
	        if (totalInstituciones > 0) {
	            if (this.indiceInstitucionActual >= totalInstituciones) {
	                this.indiceInstitucionActual = totalInstituciones - 1;
	            }
	            if (this.indiceInstitucionActual < 0) {
	                this.indiceInstitucionActual = 0;
	            }
	            parametros.setAgente(this.institucionesActuales.get(this.indiceInstitucionActual));
	            parametros.getAgente().setCuenta(edoCtaLiqParams.getAgente().getCuenta());
	            this.idFolioInstitucionActual = this.institucionesActuales.get(this.indiceInstitucionActual).getId() + 
	                                            this.institucionesActuales.get(this.indiceInstitucionActual).getFolio();
	        } 
	        else {
	            this.indiceInstitucionActual = -1;
	        }

	        if (this.tipoDerechoActual != null) {
                if (this.seSeleccionoTipoEjercicioEnCriterios()) {
                    armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, false);
                }
                else {
                    armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, true);
                }
	            this.asignarListadoTiposDerechoEIndice();
                this.tipoDerechoActual = this.tiposDerechoActuales.get(this.indiceTipoDerechoActual);
                parametros.setTipoEjercicio(this.tipoDerechoActual);
	        }

			ejecutarConsulta(parametros, true);
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Navega hacia la institucion anterior.
	 * 
	 * @param ev
	 */
	public void institucionAnterior(ActionEvent ev) {
		LiquidacionDecretosParams parametros = crearCriterio();

		try {
			this.indiceInstitucionActual--;

            totalInstituciones = this.institucionesActuales.size();
            if (totalInstituciones > 0) {
                if (this.indiceInstitucionActual >= totalInstituciones) {
                    this.indiceInstitucionActual = totalInstituciones - 1;
                }
                if (this.indiceInstitucionActual < 0) {
                    this.indiceInstitucionActual = 0;
                }
                parametros.setAgente(this.institucionesActuales.get(this.indiceInstitucionActual));
                parametros.getAgente().setCuenta(edoCtaLiqParams.getAgente().getCuenta());
                this.idFolioInstitucionActual = this.institucionesActuales.get(this.indiceInstitucionActual).getId() + 
                                                this.institucionesActuales.get(this.indiceInstitucionActual).getFolio();
            } 
            else {
                this.indiceInstitucionActual = -1;
            }

            if (this.tipoDerechoActual != null) {
                if (this.seSeleccionoTipoEjercicioEnCriterios()) {
                    armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, false);
                }
                else {
                    armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, true);
                }
                this.asignarListadoTiposDerechoEIndice();
                this.tipoDerechoActual = this.tiposDerechoActuales.get(this.indiceTipoDerechoActual);
                parametros.setTipoEjercicio(this.tipoDerechoActual);
            }

			ejecutarConsulta(parametros, true);
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Valida si se selecciono un tipo de ejercicio del combo de la pantalla de filtros.
	 * @return True si se selecciono, false en caso contrario.
	 */
	private boolean seSeleccionoTipoEjercicioEnCriterios() {
	    boolean seleccionado = true;
	    if (StringUtils.isEmpty(edoCtaLiqParams.getTipoEjercicio()) || "-1".equals(edoCtaLiqParams.getTipoEjercicio())) {
	        seleccionado = false;
	    }
	    return seleccionado;
	}

	/**
	 * Arma el arreglo de objetos de tipo LiquidacionDecretosDTO en base a los objetos de tipo LiquidacionDecretosVO.
	 */
    public void armarResultados() {
        this.registrosCompletos = new ArrayList<LiquidacionDecretosDTO>();
        for (LiquidacionDecretosVO datos : this.datosLiquidacion) {
            LiquidacionDecretosDTO liquidacion = new LiquidacionDecretosDTO(datos);
            this.registrosCompletos.add(liquidacion);
        }
    }

	/**
	 * Ejecuta la consulta y pagina el resultado
	 * 
	 * @param parametros
	 * @throws com.indeval.sidv.decretos.common.exception.BusinessException
	 */
	@SuppressWarnings("unchecked")
	public void ejecutarConsulta(LiquidacionDecretosParams parametros, boolean conCalculos)
           throws com.indeval.sidv.decretos.common.exception.BusinessException {
		this.datosLiquidacion = liquidacionDecretosDaliService.getLiquidacionDecretos(parametros, debeDejarBitacora);
		debeDejarBitacora = false;
		this.armarResultados();
		//ordenar los datos
		if (this.registrosCompletos != null || !this.registrosCompletos.isEmpty()) 
			Collections.sort(this.registrosCompletos, new Comparator<LiquidacionDecretosDTO>() {
				public int compare(LiquidacionDecretosDTO vo1, LiquidacionDecretosDTO vo2) {
					StringBuilder buffer1 = new StringBuilder();
					StringBuilder buffer2 = new StringBuilder();
					buffer1.append(vo1.getTipoDerecho())
						.append(vo1.getIdInst())
						.append(vo1.getCuenta())
						.append(vo1.getTv())
						.append(vo1.getEmisora())
						.append(vo1.getSerie())
						.append(vo1.getCupon())
						.append(vo1.getIsin());

					buffer2.append(vo2.getTipoDerecho())
						.append(vo2.getIdInst())
						.append(vo2.getCuenta())
						.append(vo2.getTv())
						.append(vo2.getEmisora())
                        .append(vo2.getSerie())
                        .append(vo2.getCupon())
                        .append(vo2.getIsin());

				return buffer1.toString().compareTo(buffer2.toString());
			}				
		} );

		List resultadoGrantotal = null;
		if (this.datosLiquidacion != null && !this.datosLiquidacion.isEmpty()) {
		    LiquidacionDecretosParams paramsfortots = parametros;
		    if (StringUtils.isEmpty(edoCtaLiqParams.getTipoEjercicio()) || "-1".equals(edoCtaLiqParams.getTipoEjercicio())) {
	            paramsfortots.setClaveTipoEjercicio(null);
	            paramsfortots.setTipoEjercicio(null);
	        }

		    //Para armar los totales.
		    resultadoGrantotal = liquidacionDecretosDaliService.getLiquidacionDecretosTotales(paramsfortots);
		    this.incializarEstadoPaginacion(this.registrosCompletos.size());
		    if (conCalculos) {
		        obtenerTotales(resultadoGrantotal);
		        obtenerSubTotales(this.registrosCompletos);
		    }
		}

		paginarResultados();

        this.datosLiquidacion = null;
        resultadoGrantotal = null;
	}

	/**
	 * Prepara los datos de tipo de derecho, como lo es el total de tipos de derecho, el tipo de derecho actual y se incluye en los parametros de 
	 * busqueda. 
	 * @param params Los parametros de busqueda.
	 * @param tiposDerecho Los tipos de derechos encontrados en una fase anterior. 
	 */
	private void prepararDatosTipoDerechoActual(LiquidacionDecretosParams params, List<String> tiposDerecho) {
	    this.totalTiposDerecho = tiposDerecho.size();
        if (this.totalTiposDerecho > 0) {
            if (this.indiceTipoDerechoActual >= this.totalTiposDerecho) {
                this.indiceTipoDerechoActual = this.totalTiposDerecho - 1;
            }
            if (this.indiceTipoDerechoActual < 0) {
                this.indiceTipoDerechoActual = 0;
            }
            this.tipoDerechoActual = tiposDerecho.get(this.indiceTipoDerechoActual);
            params.setTipoEjercicio(this.tipoDerechoActual);
        } 
        else {
            this.indiceTipoDerechoActual = -1;
            this.tipoDerechoActual = "";
        }
	}

    /**
     * Arma el mapa de instituciones y tipos de ejercicios con las instituciones halladas previamente.  
     */
    @SuppressWarnings("unchecked")
    private void armarMapaInstitucionesConTiposEjercicio(LiquidacionDecretosParams parametros, boolean realizaConsultaDerechosABD)
            throws com.indeval.sidv.decretos.common.exception.BusinessException {
        if (this.institucionesActuales != null && !this.institucionesActuales.isEmpty()) {
            for (AgenteVO agenteVO : this.institucionesActuales) {
                agenteVO.setCuenta(edoCtaLiqParams.getAgente().getCuenta());
                parametros.setAgente(agenteVO);
                if (realizaConsultaDerechosABD) {
                    this.tiposDerechoActuales = this.liquidacionDecretosDaliService.getTiposDerecho(parametros);
                }
                else {
                    this.tiposDerechoActuales = new ArrayList<String>();
                    this.tiposDerechoActuales.add(parametros.getTipoEjercicio());
                }

                if (this.tiposDerechoActuales != null && !this.tiposDerechoActuales.isEmpty()) {
                    this.institucionesTiposEjercicio.put(agenteVO.getClave(), this.tiposDerechoActuales);
                }
            }

            //Se asigna el agente de la lista dependiendo del indice de institucion actual, que normalmente sera 0.
            parametros.setAgente(this.institucionesActuales.get(this.indiceInstitucionActual));
            parametros.getAgente().setCuenta(edoCtaLiqParams.getAgente().getCuenta());

            //Obtener los tipos de derecho de la institucion actual del Mapa.
            this.tiposDerechoActuales = this.institucionesTiposEjercicio.get(this.idFolioInstitucionActual);

            this.prepararDatosTipoDerechoActual(parametros, this.tiposDerechoActuales);
        }
        else if (this.idFolioInstitucionActual != null) {
            parametros.setAgente(new AgenteVO(this.obtenerIdInstitucion(this.idFolioInstitucionActual), 
                                              this.obtenerFolioInstitucion(this.idFolioInstitucionActual), 
                                              edoCtaLiqParams.getAgente().getCuenta()));
            if (realizaConsultaDerechosABD) {
                this.tiposDerechoActuales = liquidacionDecretosDaliService.getTiposDerecho(parametros);
            }
            else {
                this.tiposDerechoActuales = new ArrayList<String>();
                this.tiposDerechoActuales.add(parametros.getTipoEjercicio());
            }

            if (this.tiposDerechoActuales != null && !this.tiposDerechoActuales.isEmpty()) {
                this.institucionesTiposEjercicio.put(parametros.getAgente().getClave(), this.tiposDerechoActuales);
            }

            this.prepararDatosTipoDerechoActual(parametros, this.tiposDerechoActuales);
        }
    }

    /**
     * Arma el mapa de instituciones y tipos de ejercicios con las instituciones halladas previamente, pero se hace en demanda, solo obtiene los 
     * tipos de derecho si no existe en el mapa.  
     */
    @SuppressWarnings("unchecked")
    private void armarMapaInstitucionesConTiposEjercicioOnDemand(LiquidacionDecretosParams parametros, boolean realizaConsultaDerechosABD)
            throws com.indeval.sidv.decretos.common.exception.BusinessException {
        if (this.idFolioInstitucionActual != null) {
            if (!this.institucionesTiposEjercicio.containsKey(this.idFolioInstitucionActual)) {
                if (realizaConsultaDerechosABD) {
                    this.tiposDerechoActuales = this.liquidacionDecretosDaliService.getTiposDerecho(parametros);
                }
                else {
                    this.tiposDerechoActuales = new ArrayList<String>();
                    this.tiposDerechoActuales.add(parametros.getTipoEjercicio());
                }

                if (this.tiposDerechoActuales != null && !this.tiposDerechoActuales.isEmpty()) {
                    this.institucionesTiposEjercicio.put(this.idFolioInstitucionActual, this.tiposDerechoActuales);
                }
            }
        }
    }

	/**
	 * Ejecuta la consulta de las instituciones encontradas para una consulta de
	 * liquidacion y pagina los resultados
	 */
	@SuppressWarnings("unchecked")
	private void ejecutaConsultaInstituciones(LiquidacionDecretosParams parametros) 
            throws com.indeval.sidv.decretos.common.exception.BusinessException {
	    if (this.institucionesActuales == null) {
	        this.institucionesActuales = liquidacionDecretosDaliService.getInstituciones(parametros);
	    }

	    // Asigna la institucion actual.
		totalInstituciones = this.institucionesActuales.size();
		if (totalInstituciones > 0) {
			if (this.indiceInstitucionActual >= totalInstituciones) {
			    this.indiceInstitucionActual = totalInstituciones - 1;
			}
			if (this.indiceInstitucionActual < 0) {
			    this.indiceInstitucionActual = 0;
			}
			parametros.setAgente(this.institucionesActuales.get(this.indiceInstitucionActual));
			parametros.getAgente().setCuenta(edoCtaLiqParams.getAgente().getCuenta());
			this.idFolioInstitucionActual = this.institucionesActuales.get(this.indiceInstitucionActual).getId() + 
                                            this.institucionesActuales.get(this.indiceInstitucionActual).getFolio();
		} 
		else {
		    this.indiceInstitucionActual = -1;
		}
	}

	/**
	 * Recupera la lista de Divisas
	 * 
	 * @return Arreglo con los tipos de ejercicio en formato de SelectItem
	 */
	public SelectItem[] getDivisas() {

		if (listaDivisas == null) {
			try {
				
				listaDivisas = VO2SelectItem.encode(liquidacionDecretosDaliService
						.getListaDivisa(), "descDivisa", "cveDivisa");
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaDivisas;
	}

	/**
	 * Realiza la busqueda de liquidaciones por decreto e inicializa el estado
	 * de paginacion
	 * 
	 * @param ev
	 */
	public void buscar() {
	    this.consultaEjecutada = false;
	    this.datosLiquidacion = null;
	    this.resultados=null;
	    this.registrosCompletos = null;
        this.institucionesActuales = null;
        this.tiposDerechoActuales = null;
        this.institucionesTiposEjercicio = new HashMap<String, List<String>>();
        
		try {
			
			// indica si el combo llamo la paginacion, si fue asi se manda solo
			// a ejecutar
			LiquidacionDecretosParams parametros = crearCriterio(); 

			if (validarParametros(parametros)) {
				boolean conCalculos = true;
				if ("consultaSinCalculos".equals(comboPaginacion)) {
					parametros.setTipoEjercicio(this.tipoDerechoActual);
					parametros.getAgente().setId(obtenerIdInstitucion(this.idFolioInstitucionActual));
					parametros.getAgente().setFolio(obtenerFolioInstitucion(this.idFolioInstitucionActual));
					conCalculos = false;
				}

				// Si no se elige un folio de participante
				if (StringUtils.isEmpty(this.idFolioParticipante)) {
					this.indiceInstitucionActual = 0;
					this.institucionesActuales = null;
		            this.tiposDerechoActuales = null;
					this.ejecutaConsultaInstituciones(parametros);
				} 
				else {
					//settear el valor de la institucion del participante a la institucion actual para la desplegarse en pantalla				
					this.idFolioInstitucionActual = this.idFolioParticipante;
				}

				// si esta vacio el tipo de ejercicio
                this.indiceTipoDerechoActual = 0;
				if (StringUtils.isEmpty(parametros.getTipoEjercicio())) {
				    this.armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, true);
				}
				else {
				    this.tipoDerechoActual = parametros.getTipoEjercicio();
                    this.armarMapaInstitucionesConTiposEjercicioOnDemand(parametros, false);
				}

				if (this.idFolioInstitucionActual != null && this.institucionesTiposEjercicio.containsKey(this.idFolioInstitucionActual)) {
				    //Obtener los tipos de derecho de la institucion actual del Mapa.
				    this.tiposDerechoActuales = this.institucionesTiposEjercicio.get(this.idFolioInstitucionActual);
				    //Setea los datos iniciales del tipo de derecho.
				    this.prepararDatosTipoDerechoActual(parametros, this.tiposDerechoActuales);
				}

				this.ejecutarConsulta(parametros, conCalculos);

				//this.consultaEjecutada = true;
			}
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
		}
		this.consultaEjecutada = true;
	}

	/**
	 * Valida que los parámetros de búsqueda sean correctos.
	 * @param parametros Parámetros a validar
	 * @return true si se cumplemn las validaciones; false en caso contrario.
	 */
	private boolean validarParametros(LiquidacionDecretosParams parametros) {
        boolean valido = validarFechaObligatoria(parametros.getFechaIni(), false, CamposPantallaConstantes.campoFechaInicial);
        valido = valido && validarFechaFinalVsFechaInicial(parametros.getFechaIni(), parametros.getFechaFin());
        valido = valido && validarFechaInicialRangoDias(parametros.getFechaIni(), CamposPantallaConstantes.rangoDiasConsultas);
        return valido;
	}

	/**
	 * Realiza la paginacion manual de los resultados entregados por el servicio
	 * de liquidacion de decretos
	 */
	private void paginarResultados() {
	    this.resultados = null;
		if (this.registrosCompletos != null && this.registrosCompletos.size() > 0 && paginacion.getRegistrosPorPagina() > 0) {
			int inicioPagina = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
			if (inicioPagina + 1 >= this.registrosCompletos.size()) {
				inicioPagina = this.registrosCompletos.size() - 1;
			}
			int finalPagina = inicioPagina + paginacion.getRegistrosPorPagina();
			if (finalPagina >= this.registrosCompletos.size()) {
				finalPagina = this.registrosCompletos.size();
			}
			resultados = this.registrosCompletos.subList(inicioPagina, finalPagina);
		}
	}

	/**
	 * Crea un objeto de parametros copia del objeto original del controller
	 * 
	 * @return Objeto de parametros
	 */
	private LiquidacionDecretosParams crearCriterio() {
		LiquidacionDecretosParams params = new LiquidacionDecretosParams();

		params.setAgente(new AgenteVO(this.obtenerIdInstitucion(idFolioParticipante), this.obtenerFolioInstitucion(idFolioParticipante), 
		                              edoCtaLiqParams.getAgente().getCuenta()));

		params.setEmision(edoCtaLiqParams.getEmision());
		params.getEmision().setIdTipoValor(StringUtils.trimToNull(params.getEmision().getIdTipoValor()));
		params.getEmision().setEmisora(StringUtils.trimToNull(params.getEmision().getEmisora()));
		params.getEmision().setSerie(StringUtils.trimToNull(params.getEmision().getSerie()));
		params.getEmision().setCupon(StringUtils.trimToNull(params.getEmision().getCupon()));
		if (params.getEmision().getIdTipoValor() == null && params.getEmision().getEmisora() == null && 
		    params.getEmision().getSerie() == null && params.getEmision().getCupon() == null) {
			params.setEmision(null);			
		}

		params.setFechaFin(edoCtaLiqParams.getFechaFin());
		params.setFechaIni(edoCtaLiqParams.getFechaIni());
		params.setTipoConsulta(edoCtaLiqParams.getTipoConsulta());
		if (StringUtils.isEmpty(edoCtaLiqParams.getTipoEjercicio()) || "-1".equals(edoCtaLiqParams.getTipoEjercicio())) {
			params.setTipoEjercicio(null);
		} 
		else {
			params.setTipoEjercicio(edoCtaLiqParams.getTipoEjercicio());
		}
		params.setTipoMoneda(edoCtaLiqParams.getTipoMoneda());

		return params;
	}

	/**
	 * Invoca las funciones de navegacion de pagina de la consulta principal de la pantalla.
	 * 
	 * @param e Evento generado durante la solicitud
	 */
	@SuppressWarnings("unchecked")
	public void navegarPorResultados(ActionEvent e) {
	    String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

        try {
            paginacion.getClass().getMethod(navegacion).invoke(paginacion);
        } catch (Exception ex) {
            logger.error("Error de invocacion de metodo al navegar por los resultados principales", ex);
        }

        paginarResultados();

        consultaEjecutada = true;
	}

    /**
     * Realiza el cambio de registros por pagina del combo de la vista.
     * 
     * @param ev
     */
    public void cambiarRegistrosPorPagina() {
        this.incializarEstadoPaginacion(registrosCompletos.size());
        paginarResultados();
        consultaEjecutada = true;
    }

	/**
	 * Ejecuta la consulta para los reportes de exportaciï¿½n de AmortizaciÃ³n
	 * 
	 * @param e
	 */
	public void generarReportesAmortizacion(ActionEvent event) {
		getDetalleAmortizaciones();
	}

	/**
	 * Calcula el total de importes de la lista de resultados
	 * 
	 * @param resultados
	 */
	private void obtenerTotales(List<LiquidacionDecretosVO>  datosLiquidacion) {
		double cobrado = 0;
		double porCobrar = 0;
		double remanente = 0;

		/*if (datosLiquidacion != null && datosLiquidacion.size() > 0) {
			for (LiquidacionDecretosVO vo : datosLiquidacion) {
				cobrado += vo.getImporteCobrado() != null ? vo.getImporteCobrado().doubleValue() : 0;
				porCobrar += vo.getImporteACobrar() != null ? vo.getImporteACobrar().doubleValue() : 0;
				remanente += vo.getRemanente() != null ? vo.getRemanente().doubleValue() : 0;
			}
		}*/
		if (datosLiquidacion != null && !datosLiquidacion.isEmpty()) {
		    LiquidacionDecretosVO vo = datosLiquidacion.get(0);
            cobrado = vo.getImporteCobrado() != null ? vo.getImporteCobrado().doubleValue() : 0;
            porCobrar = vo.getImporteACobrar() != null ? vo.getImporteACobrar().doubleValue() : 0;
            remanente = vo.getRemanente() != null ? vo.getRemanente().doubleValue() : 0;
        }

		totalImporteACobrar = new BigDecimal(porCobrar);
		totalImporteCobrado = new BigDecimal(cobrado);
		totalRemanente = new BigDecimal(remanente);
	}

	/**
	 * Calcula el total de importes de la lista de resultados
	 * 
	 * @param resultados
	 */
	private void obtenerSubTotales(List<LiquidacionDecretosDTO> datosLiquidacion) {
		double cobrado = 0;
		double porCobrar = 0;
		double remanente = 0;

		// iterar todas
		if (datosLiquidacion != null && datosLiquidacion.size() > 0) {
			for (LiquidacionDecretosDTO vo : datosLiquidacion) {
				cobrado += vo.getImporteCobrado() != null ? vo.getImporteCobrado().doubleValue() : 0;
				porCobrar += vo.getImporteACobrar() != null ? vo.getImporteACobrar().doubleValue() : 0;
				remanente += vo.getRemanente() != null ? vo.getRemanente().doubleValue() : 0;
			}
		}

		subTotalImporteACobrar = new BigDecimal(porCobrar);
		subTotalImporteCobrado = new BigDecimal(cobrado);
		subTotalRemanente = new BigDecimal(remanente);
	}

	/**
	 * Indica si el tipo de consulta es un cobro.
	 * @return true si es cobro, fase en caso contrario
	 */
	public boolean isCobro() {
		return (getEdoCtaLiqParams().getTipoConsulta() != null && COBROS.equals(getEdoCtaLiqParams().getTipoConsulta()));
	}
	
	
	/**
	 * @return the tesoreriaService
	 */
	public TesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	/**
	 * @param tesoreriaService
	 *            the tesoreriaService to set
	 */
	public void setTesoreriaService(TesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	/**
	 * @return the fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * @param fechasUtilService
	 *            the fechasUtilService to set
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}



	/**
	 * @return the idFolioParticipante
	 */
	public String getIdFolioParticipante() {
		return idFolioParticipante;
	}

	/**
	 * @param idFolioParticipante
	 *            the idFolioParticipante to set
	 */
	public void setIdFolioParticipante(String idFolioParticipante) {
		this.idFolioParticipante = idFolioParticipante;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada
	 *            the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the listaDivisas
	 */
	public SelectItem[] getListaDivisas() {
		return listaDivisas;
	}

	/**
	 * @param listaDivisas
	 *            the listaDivisas to set
	 */
	public void setListaDivisas(SelectItem[] listaDivisas) {
		this.listaDivisas = listaDivisas;
	}

	/**
	 * @return the listaTipoEjercicio
	 */
	public SelectItem[] getListaTipoEjercicio() {
		return listaTipoEjercicio;
	}

	/**
	 * @param listaTipoEjercicio
	 *            the listaTipoEjercicio to set
	 */
	public void setListaTipoEjercicio(SelectItem[] listaTipoEjercicio) {
		this.listaTipoEjercicio = listaTipoEjercicio;
	}

	/**
	 * @return the indiceInstitucionActual
	 */
	public int getIndiceInstitucionActual() {
		return indiceInstitucionActual;
	}

	/**
	 * @param indiceInstitucionActual
	 *            the indiceInstitucionActual to set
	 */
	public void setIndiceInstitucionActual(int indiceInstitucionActual) {
		this.indiceInstitucionActual = indiceInstitucionActual;
	}

	/**
	 * @return the idFolioActual
	 */
	public String getIdFolioActual() {
		return idFolioActual;
	}

	/**
	 * @param idFolioActual
	 *            the idFolioActual to set
	 */
	public void setIdFolioActual(String idFolioActual) {
		this.idFolioActual = idFolioActual;
	}

	/**
	 * @return the indiceTipoDerechoActual
	 */
	public int getIndiceTipoDerechoActual() {
		return indiceTipoDerechoActual;
	}

	/**
	 * @param indiceTipoDerechoActual
	 *            the indiceTipoDerechoActual to set
	 */
	public void setIndiceTipoDerechoActual(int indiceTipoDerechoActual) {
		this.indiceTipoDerechoActual = indiceTipoDerechoActual;
	}

	/**
	 * @return the descripcionDerechoActual
	 */
	public String getDescripcionDerechoActual() {
		return descripcionDerechoActual;
	}

	/**
	 * @param descripcionDerechoActual
	 *            the descripcionDerechoActual to set
	 */
	public void setDescripcionDerechoActual(String descripcionDerechoActual) {
		this.descripcionDerechoActual = descripcionDerechoActual;
	}

	/**
	 * @return the edoCtaLiqParams
	 */
	public LiquidacionDecretosParams getEdoCtaLiqParams() {
		return edoCtaLiqParams;
	}

	/**
	 * @param edoCtaLiqParams
	 *            the edoCtaLiqParams to set
	 */
	public void setEdoCtaLiqParams(LiquidacionDecretosParams edoCtaLiqParams) {
		this.edoCtaLiqParams = edoCtaLiqParams;
	}

	/**
	 * @return the totalTiposDerecho
	 */
	public int getTotalTiposDerecho() {
		return totalTiposDerecho;
	}

	/**
	 * @param totalTiposDerecho
	 *            the totalTiposDerecho to set
	 */
	public void setTotalTiposDerecho(int totalTiposDerecho) {
		this.totalTiposDerecho = totalTiposDerecho;
	}

	/**
	 * @return the totalInstituciones
	 */
	public int getTotalInstituciones() {
		return totalInstituciones;
	}

	/**
	 * @param totalInstituciones
	 *            the totalInstituciones to set
	 */
	public void setTotalInstituciones(int totalInstituciones) {
		this.totalInstituciones = totalInstituciones;
	}

	/**
	 * @return the tipoDerechoActual
	 */
	public String getTipoDerechoActual() {
		return tipoDerechoActual;
	}

	/**
	 * @param tipoDerechoActual
	 *            the tipoDerechoActual to set
	 */
	public void setTipoDerechoActual(String tipoDerechoActual) {
		this.tipoDerechoActual = tipoDerechoActual;
	}

	/**
	 * @return the idFolioInstitucionActual
	 */
	public String getIdFolioInstitucionActual() {
		return idFolioInstitucionActual;
	}

	/**
	 * @param idFolioInstitucionActual
	 *            the idFolioInstitucionActual to set
	 */
	public void setIdFolioInstitucionActual(String idFolioInstitucionActual) {
		this.idFolioInstitucionActual = idFolioInstitucionActual;
	}

	/**
	 * @return the subTotalImporteCobrado
	 */
	public BigDecimal getSubTotalImporteCobrado() {
		return subTotalImporteCobrado;
	}

	/**
	 * @param subTotalImporteCobrado
	 *            the subTotalImporteCobrado to set
	 */
	public void setSubTotalImporteCobrado(BigDecimal subTotalImporteCobrado) {
		this.subTotalImporteCobrado = subTotalImporteCobrado;
	}

	/**
	 * @return the subTotalImporteACobrar
	 */
	public BigDecimal getSubTotalImporteACobrar() {
		return subTotalImporteACobrar;
	}

	/**
	 * @param subTotalImporteACobrar
	 *            the subTotalImporteACobrar to set
	 */
	public void setSubTotalImporteACobrar(BigDecimal subTotalImporteACobrar) {
		this.subTotalImporteACobrar = subTotalImporteACobrar;
	}

	/**
	 * @return the subTotalRemanente
	 */
	public BigDecimal getSubTotalRemanente() {
		return subTotalRemanente;
	}

	/**
	 * @param subTotalRemanente
	 *            the subTotalRemanente to set
	 */
	public void setSubTotalRemanente(BigDecimal subTotalRemanente) {
		this.subTotalRemanente = subTotalRemanente;
	}

	/**
	 * @return the totalImporteCobrado
	 */
	public BigDecimal getTotalImporteCobrado() {
		return totalImporteCobrado;
	}

	/**
	 * @param totalImporteCobrado
	 *            the totalImporteCobrado to set
	 */
	public void setTotalImporteCobrado(BigDecimal totalImporteCobrado) {
		this.totalImporteCobrado = totalImporteCobrado;
	}

	/**
	 * @return the totalImporteACobrar
	 */
	public BigDecimal getTotalImporteACobrar() {
		return totalImporteACobrar;
	}

	/**
	 * @param totalImporteACobrar
	 *            the totalImporteACobrar to set
	 */
	public void setTotalImporteACobrar(BigDecimal totalImporteACobrar) {
		this.totalImporteACobrar = totalImporteACobrar;
	}

	/**
	 * @return the totalRemanente
	 */
	public BigDecimal getTotalRemanente() {
		return totalRemanente;
	}

	/**
	 * @param totalRemanente
	 *            the totalRemanente to set
	 */
	public void setTotalRemanente(BigDecimal totalRemanente) {
		this.totalRemanente = totalRemanente;
	}

	/**
	 * @return the totalResultados
	 */
	public int getTotalResultados() {
		return totalResultados;
	}

	/**
	 * @param totalResultados
	 *            the totalResultados to set
	 */
	public void setTotalResultados(int totalResultados) {
		this.totalResultados = totalResultados;
	}

	/**
	 * @return the detalle
	 */
	public LiquidacionDecretosDetalleVO[] getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle
	 *            the detalle to set
	 */
	public void setDetalle(LiquidacionDecretosDetalleVO[] detalle) {
		this.detalle = detalle;
	}
	
	public boolean isExistenResultados() {
		return existenResultados;
	}

	public void setExistenResultados(boolean existenResultados) {
		this.existenResultados = existenResultados;
	}

	public List<LiquidacionDecretosDTO> getResultadosReporte() {
		List<LiquidacionDecretosDTO> resultadosReporte = resultados;		
		return resultadosReporte;
	}

	/**
	 * Obtiene el valor del atributo detalleAmortizaciones
	 *
	 * @return el valor del atributo detalleAmortizaciones
	 */
	public List<LiquidacionDecretosDetalleAmortizacionesVO> getDetalleAmortizaciones() {
		return detalleAmortizaciones;
	}

	/**
	 * Establece el valor del atributo detalleAmortizaciones
	 *
	 * @param detalleAmortizaciones el valor del atributo detalleAmortizaciones a establecer
	 */
	public void setDetalleAmortizaciones(
			List<LiquidacionDecretosDetalleAmortizacionesVO> detalleAmortizaciones) {
		this.detalleAmortizaciones = detalleAmortizaciones;
	}

	/**
	 * @return the liquidacionDecretosDaliService
	 */
	public LiquidacionDecretosDaliService getLiquidacionDecretosDaliService() {
		return liquidacionDecretosDaliService;
	}

	/**
	 * @param liquidacionDecretosDaliService the liquidacionDecretosDaliService to set
	 */
	public void setLiquidacionDecretosDaliService(
			LiquidacionDecretosDaliService liquidacionDecretosDaliService) {
		this.liquidacionDecretosDaliService = liquidacionDecretosDaliService;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public List<LiquidacionDecretosVO> getDatosLiquidacion() {
		return datosLiquidacion;
	}

	public void setDatosLiquidacion(List<LiquidacionDecretosVO> datosLiquidacion) {
		this.datosLiquidacion = datosLiquidacion;
	}

	public List<LiquidacionDecretosDTO> getResultados() {
		return resultados;
	}

	public void setResultados(List<LiquidacionDecretosDTO> resultados) {
		this.resultados = resultados;
	}

	public List<LiquidacionDecretosDetalleDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<LiquidacionDecretosDetalleDTO> detalles) {
		this.detalles = detalles;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

    public AgenteVO getAgente() {
        return agente;
    }

    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    public String getFolioFija() {
        return folioFija;
    }

    public void setFolioFija(String folioFija) {
        this.folioFija = folioFija;
    }

    public String getFolioVariable() {
        return folioVariable;
    }

    public void setFolioVariable(String folioVariable) {
        this.folioVariable = folioVariable;
    }

    public Integer getIdDerecho() {
        return idDerecho;
    }

    public void setIdDerecho(Integer idDerecho) {
        this.idDerecho = idDerecho;
    }

    public Integer getIdTipoDerecho() {
        return idTipoDerecho;
    }

    public void setIdTipoDerecho(Integer idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    public Integer getIdTipoEjercicio() {
        return idTipoEjercicio;
    }

    public void setIdTipoEjercicio(Integer idTipoEjercicio) {
        this.idTipoEjercicio = idTipoEjercicio;
    }

    public String getTipoEjercicioDetAmrt() {
        return tipoEjercicioDetAmrt;
    }

    public void setTipoEjercicioDetAmrt(String tipoEjercicioDetAmrt) {
        this.tipoEjercicioDetAmrt = tipoEjercicioDetAmrt;
    }

    /**
     * Método para obtener el atributo parametros
     * @return El atributo parametros
     */
    public LiquidacionDecretosParams getParametros() {
        return this.edoCtaLiqParams;
    }

    public List<AgenteVO> getInstitucionesActuales() {
        return this.institucionesActuales;
    }

    public List<String> getTiposDerechoActuales() {
        return this.tiposDerechoActuales;
    }

}
