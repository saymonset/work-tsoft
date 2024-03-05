package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.mor.dto.ConsultaRecuperacionDTO;
import com.indeval.mor.exception.MorException;
import com.indeval.mor.vo.DetalleMorVo;
import com.indeval.mor.vo.EstadoRecuperacionMorVo;
import com.indeval.mor.vo.ModuloOrigenVo;
import com.indeval.mor.vo.MovimientoMorVo;
import com.indeval.mor.vo.PaginaVo;
import com.indeval.mor.vo.RecuperacionMorVo;
import com.indeval.mor.vo.TipoMovimientoVo;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.mor.MorEJBService;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;


@SuppressWarnings({"unchecked", "rawtypes"})
public class ConsultaRecuperacionesController extends ControllerBase{
	
	private FechasUtilService fechasUtilService = null;
	private MorEJBService morEJBService = null;
	private boolean consultaEjecutada = false;
	private boolean resultadoRegistros = false;
	private boolean regreso = false;
	private boolean muestraTablaSinDatos = false;
	private boolean muestraCriteriosSinDatos = false;
	private String idFolioParticipante = null;
	private List<DetalleMorVo> resultados;
	private ConsultaRecuperacionDTO consultaRecuperacionDTO = null;
	private List<SelectItem> estados = new ArrayList<SelectItem>();
	private List<SelectItem> moduloOrigen = new ArrayList<SelectItem>();
	private List<SelectItem> tipoMovimiento = new ArrayList<SelectItem>();
	private List<EstadoRecuperacionMorVo> listaEstados = null;
	//moduloOrigen Tipomovimiento
	private PaginaVo paginaVo;
	private RecuperacionMorVo recuperacionMorVo = null;
	private Date fechaActual = null;
	/** El numero de pagina actual de la consulta */
	private int numeroPagina = -1;
	/** El total de paginas de resultados productos de la consulta */
	private int totalPaginas = -1;
	private String idRec = null;
	/** Monto de toda las operaciones */
	private BigDecimal montoTotal = BigDecimal.ZERO;
	/** Cantidad de titulos de todas las operaciones */
	private long cantidadTotal;
	
	/**
	 * Inicializa la pantalla
	 * @return
	 */
	public String getInit() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if(facesContext.getExternalContext().getRequestServletPath().equalsIgnoreCase("/tesoreria/mor/detalleConsultaRecuperacion.faces")){
			regreso = true;
			consultaEjecutada = true;
			if (resultados != null){
				resultadoRegistros = resultados.size() > 0 ? true: false;	
			}
		}else {
			consultaRecuperacionDTO  = new ConsultaRecuperacionDTO();
			
			
			consultaRecuperacionDTO.setParticipante(getInstitucionActual().getClaveTipoInstitucion()
								+ getInstitucionActual().getFolioInstitucion());
			consultaRecuperacionDTO.setFechaInicial(fechasUtilService.getCurrentDate());
			consultaRecuperacionDTO.setFechaFinal(fechasUtilService.getCurrentDate());
			
			consultaEjecutada = false;
			resultadoRegistros = false;
			regreso = false;
			muestraCriteriosSinDatos = false;
			muestraTablaSinDatos = false;
			montoTotal = BigDecimal.ZERO;
			cantidadTotal = 0l;
				
		}
		paginacion.setRegistrosPorPagina(20);
		
		return null;
	}
	
	public void limpiar(ActionEvent e) {
		inicializarCampos();
	}
	
	/**
	 * Obtiene los registros del servicio expuesto por MOR
	 * @param e
	 */
	public void buscar(ActionEvent e) {
		consultaRecuperacionDTO.setIdEstadoRecuperacion(null);
		
		if(consultaRecuperacionDTO.getIdModuloOrigen().equals(-1) 
				|| consultaRecuperacionDTO.getIdModuloOrigen()== -1){
			consultaRecuperacionDTO.setIdModuloOrigen(null);
		}
		if(consultaRecuperacionDTO.getIdTipoMovimiento().equals(-1) 
				|| consultaRecuperacionDTO.getIdTipoMovimiento()== -1){
			consultaRecuperacionDTO.setIdTipoMovimiento(null);
		}
		
		consultaRecuperacionDTO.setTipoValor(StringUtils.upperCase(consultaRecuperacionDTO.getTipoValor()));
		consultaRecuperacionDTO.setEmisora(StringUtils.upperCase(consultaRecuperacionDTO.getEmisora()));
		consultaRecuperacionDTO.setSerie(StringUtils.upperCase(consultaRecuperacionDTO.getSerie()));
		consultaRecuperacionDTO.setCupon(StringUtils.upperCase(consultaRecuperacionDTO.getCupon()));
		
		try{
			paginaVo = morEJBService.obtenerConsultaRecuperacionPortalDali(consultaRecuperacionDTO);
			resultados = (List<DetalleMorVo>)paginaVo.getRegistros();
			
			//limpia los totales
			montoTotal = BigDecimal.ZERO;
			cantidadTotal = 0l;
			//calcula los totales de los montos
			for (DetalleMorVo detalleMorVo : resultados) {
				montoTotal = montoTotal.add(detalleMorVo.getImporte() != null ? detalleMorVo.getImporte() : BigDecimal.ZERO);
				cantidadTotal = cantidadTotal + (detalleMorVo.getCantidadTitulos() != null ? detalleMorVo.getCantidadTitulos() : 0L);
			}
			
			this.incializarEstadoPaginacion(resultados.size());
			paginarResultados();
			
			consultaRecuperacionDTO.setIdEstadoRecuperacion(-1);
			
			if( consultaRecuperacionDTO.getIdModuloOrigen()== null){
				consultaRecuperacionDTO.setIdModuloOrigen(-1);
			}
			if(consultaRecuperacionDTO.getIdTipoMovimiento()== null){
				consultaRecuperacionDTO.setIdTipoMovimiento(-1);
			}
			
			consultaEjecutada = true;
			if(resultados.size()>0){
				resultadoRegistros = true;
				muestraCriteriosSinDatos =false;
				 
			} else{
				resultadoRegistros = false;
				muestraTablaSinDatos = true;	
				muestraCriteriosSinDatos =true;
			}
		}catch(Exception e1){
			e1.printStackTrace();
			consultaEjecutada = true;
			resultadoRegistros = false;
			muestraTablaSinDatos = true;	
			muestraCriteriosSinDatos =true;
			
			throw new BusinessException(e1.getMessage(),e1);
		}
	}//end buscar
	
	/**
	 * Reinicia los campos para una pantalla nueva
	 */
	private void inicializarCampos() {
		
		consultaRecuperacionDTO.setParticipante(getInstitucionActual().getClaveTipoInstitucion()
				+ getInstitucionActual().getFolioInstitucion());
		consultaEjecutada = false;
		resultadoRegistros = false;
		muestraTablaSinDatos = false;
		muestraCriteriosSinDatos = false;
		regreso = false;
    	resultados = null;
    	paginaVo = null;
    	recuperacionMorVo = null;
		consultaRecuperacionDTO  = new ConsultaRecuperacionDTO();
		consultaRecuperacionDTO.setFechaInicial(fechasUtilService.getCurrentDate());
		consultaRecuperacionDTO.setFechaFinal(fechasUtilService.getCurrentDate());
		consultaRecuperacionDTO.setParticipante(getInstitucionActual().getClaveTipoInstitucion()
							+ getInstitucionActual().getFolioInstitucion());
	}
	
	/**
	 * Establece la navegacion
	 * @param e
	 */
	public void navegarPorResultados(ActionEvent e) {
		
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");
		try {
			paginacion.getClass().getMethod(navegacion).invoke(paginacion);

		} catch (Exception ex) {
			logger.error("Error al navega por los resultados principales",ex);
		}
		
		ejecutarConsulta();
	}
	
	public int getNumeroPagina() {
		
		if (paginaVo != null && paginaVo.getRegistrosXPag() > 0) {
			numeroPagina = (int) Math.ceil(paginaVo.getOffset().doubleValue() / paginaVo.getRegistrosXPag().doubleValue()) + 1;
			int total = getTotalPaginas();
			if(numeroPagina > total) {
				numeroPagina = total;
			}

		}
		return numeroPagina;
	}

	private void paginarResultados() {
		
		if (resultados != null && resultados.size() > 0
				&& paginacion.getRegistrosPorPagina() > 0) {
			int inicioPagina = (paginacion.getNumeroPagina() - 1)
					* paginacion.getRegistrosPorPagina();
			int finalPagina = inicioPagina + paginacion.getRegistrosPorPagina();
			if (inicioPagina + 1 >= resultados.size()) {
				inicioPagina = resultados.size() - 1;
			}
			if (finalPagina >= resultados.size()) {
				finalPagina = resultados.size();
			}
			resultados = resultados.subList(inicioPagina, finalPagina);
		}

	}
	
	@Deprecated
	public String getDetalleRecuperacion(){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		idRec = facesContext.getExternalContext().getRequestParameterMap().get("idRecuperacion");
		if(idRec != null){
//			Long id = Long.valueOf(idRec); 
//			for (DetalleMorVo e: resultados){
//				if(e.getIdRecuperacionMor().equals(id)){
//					recuperacionMorVo = new DetalleMorVo();
//					this.recuperacionMorVo = e;
//
//					break;
//				}
//			}	
		}
	     return null;
	}
	
	public String paginaPrincipal(){
		
		regreso = true;
		idRec = null;
		return "consultaRecuperacion";
	}
	
	public void generarReportes(ActionEvent ev) {
		
		try {
				consultaRecuperacionDTO.setIdEstadoRecuperacion(null);
			if(consultaRecuperacionDTO.getIdModuloOrigen().equals(-1) 
					|| consultaRecuperacionDTO.getIdModuloOrigen()== -1){
				consultaRecuperacionDTO.setIdModuloOrigen(null);
			}
			if(consultaRecuperacionDTO.getIdTipoMovimiento().equals(-1) 
					|| consultaRecuperacionDTO.getIdTipoMovimiento()== -1){
				consultaRecuperacionDTO.setIdTipoMovimiento(null);
			}
			consultaRecuperacionDTO.setTipoValor(StringUtils.upperCase(consultaRecuperacionDTO.getTipoValor()));
			consultaRecuperacionDTO.setEmisora(StringUtils.upperCase(consultaRecuperacionDTO.getEmisora()));
			consultaRecuperacionDTO.setSerie(StringUtils.upperCase(consultaRecuperacionDTO.getSerie()));
			consultaRecuperacionDTO.setCupon(StringUtils.upperCase(consultaRecuperacionDTO.getCupon()));
			paginaVo = morEJBService.obtenerConsultaRecuperacionPortalDali(consultaRecuperacionDTO);
			resultados = (List<DetalleMorVo>)paginaVo.getRegistros();
			
		} catch (MorException e) {
			e.printStackTrace();
		}
	}	
	
	
	public List<SelectItem> getEstados() {
			estados = new ArrayList<SelectItem>();
			try{
			listaEstados = morEJBService.obtenerEstadosRecuperacionMor();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(listaEstados!=null){
				for(EstadoRecuperacionMorVo e:listaEstados){
						estados.add(new SelectItem(e.getIdEstadoRecuperacionMor(), e.getDescripcionEstadoRecuperacion()));
				}
			}
			return estados;
		
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public PaginaVo getPaginaVo() {
		return paginaVo;
	}

	public void setPaginaVo(PaginaVo paginaVo) {
		this.paginaVo = paginaVo;
	}

	public RecuperacionMorVo getRecuperacionMorVo() {
		return recuperacionMorVo;
	}

	public void setRecuperacionMorVo(RecuperacionMorVo recuperacionMorVo) {
		this.recuperacionMorVo = recuperacionMorVo;
	}

	
	public int getTotalPaginas() {
		if (paginaVo != null && paginaVo.getTotalRegistros() > 0) {
			totalPaginas = (int) Math.ceil((paginaVo.getTotalRegistros().doubleValue() / paginaVo.getRegistrosXPag().doubleValue()));
		}
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
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
    
    public void avanzar(int paginas) {
    	
        int numeropPag = getNumeroPagina();
        if (paginaVo.getTotalRegistros() > 0) {
            if (numeropPag + paginas > getTotalPaginas()) {
                numeropPag = getTotalPaginas();
            } else {
                numeropPag += paginas;
            }
            paginaVo.setOffset((numeropPag - 1) * paginaVo.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();

    }
    
    public void retroceder(int paginas) {
        int numeropPag = getNumeroPagina();
        if (paginaVo.getTotalRegistros() > 0) {
            if ((numeropPag - paginas) < 1) {
                numeropPag = 1;
            } else {
                numeropPag -= paginas;
            }
        }
        paginaVo.setOffset(((numeropPag-1) * paginaVo.getRegistrosXPag()));
        ejecutarConsulta();
    }
    
    /**
     * Obtiene el numero de registros solicitados de la consulta
     * @return
     */
    public String ejecutarConsulta() {
		int inicioPagina = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
		int finalPagina = inicioPagina + paginacion.getRegistrosPorPagina();
		
		try {
			consultaRecuperacionDTO.setIdEstadoRecuperacion(null);
			
			if(consultaRecuperacionDTO.getIdModuloOrigen().equals(-1) 
					|| consultaRecuperacionDTO.getIdModuloOrigen()== -1){
				consultaRecuperacionDTO.setIdModuloOrigen(null);
			}
			if(consultaRecuperacionDTO.getIdTipoMovimiento().equals(-1) 
					|| consultaRecuperacionDTO.getIdTipoMovimiento()== -1){
				consultaRecuperacionDTO.setIdTipoMovimiento(null);
			}
			consultaRecuperacionDTO.setTipoValor(StringUtils.upperCase(consultaRecuperacionDTO.getTipoValor()));
			
			consultaRecuperacionDTO.setEmisora(StringUtils.upperCase(consultaRecuperacionDTO.getEmisora()));
			consultaRecuperacionDTO.setSerie(StringUtils.upperCase(consultaRecuperacionDTO.getSerie()));
			consultaRecuperacionDTO.setCupon(StringUtils.upperCase(consultaRecuperacionDTO.getCupon()));
			paginaVo = morEJBService.obtenerConsultaRecuperacionPortalDali(consultaRecuperacionDTO);
			resultados = (List<DetalleMorVo>)paginaVo.getRegistros();
			
			if (inicioPagina + 1 >= resultados.size()) {
				inicioPagina = resultados.size() - 1;
			}
			if (finalPagina >= resultados.size()) {
				finalPagina = resultados.size();
			}
			resultados = resultados.subList(inicioPagina, finalPagina);
		} catch (MorException e) {
			e.printStackTrace();
		}
		consultaEjecutada = true;
		if(resultados.size() > 0){
			resultadoRegistros = true;
		}
		return null;

	}
    
    public String getDescripcionEstado(){
		String descripcion = null;
		
		if(consultaRecuperacionDTO != null){
			
			if (consultaRecuperacionDTO.getIdEstadoRecuperacion() == null){
				descripcion = "Todos";
			}else{
				for (EstadoRecuperacionMorVo e: listaEstados){
					if(e.getIdEstadoRecuperacionMor().equals(consultaRecuperacionDTO.getIdEstadoRecuperacion())){
						descripcion = e.getDescripcionEstadoRecuperacion();
						
						break;
					}
				}	
			}
				
			
		}
		return descripcion;
	}
    
    public String getDescripcionModuloOrigen(){
		String descripcion = "Todos";
		
		if(consultaRecuperacionDTO != null){
			
			if (consultaRecuperacionDTO.getIdModuloOrigen() == null){
				descripcion = "Todos";
			}else{
				for (ModuloOrigenVo e: this.getListaModulosOrigen()){
					if(e.getIdModuloOrigenMor().equals(consultaRecuperacionDTO.getIdModuloOrigen())){
						descripcion = e.getNombreOrigenModuloOrigernMor();
						break;
					}
				}	
			}
				
			
		}
		return descripcion;
	}
    
    public String getDescripcionIdTipoMovimiento(){
		String descripcion = null;
		
		if(consultaRecuperacionDTO != null){
			
			if (consultaRecuperacionDTO.getIdTipoMovimiento() == null){
				descripcion = "Todos";
			}else{
				for (TipoMovimientoVo e: this.getListaTiposMovimiento() ){
					if(e.getIdTipoMovimientoMor().equals(consultaRecuperacionDTO.getIdTipoMovimiento())){
						descripcion = e.getDescripcionTipoMovimiento();
						break;
					}
				}	
			}
				
			
		}
		return descripcion;
	}
    
	public List<EstadoRecuperacionMorVo> getListaEstados() {
		if (listaEstados == null){
			listaEstados = new ArrayList<EstadoRecuperacionMorVo> ();
			listaEstados = morEJBService.obtenerEstadosRecuperacionMor();
		}
		return listaEstados;
	}

	public Date getFechaActual() {
		if (fechaActual == null ){
			fechaActual = new Date();
		}
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public boolean isResultadoRegistros() {
		return resultadoRegistros;
	}

	public void setResultadoRegistros(boolean resultadoRegistros) {
		this.resultadoRegistros = resultadoRegistros;
	}

	public String getIdRec() {
		return idRec;
	}

	public void setIdRec(String idRec) {
		this.idRec = idRec;
	}

	public boolean isRegreso() {
		return regreso;
	}

	public void setRegreso(boolean regreso) {
		this.regreso = regreso;
	}
	public boolean isMuestraTablaSinDatos() {
		return muestraTablaSinDatos;
	}

	public void setMuestraTablaSinDatos(boolean muestraTablaSinDatos) {
		this.muestraTablaSinDatos = muestraTablaSinDatos;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}
	

	
	public String getIdFolioParticipante() {
		return idFolioParticipante;
	}

	public void setIdFolioParticipante(String idFolioParticipante) {
		this.idFolioParticipante = idFolioParticipante;
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

	



	public List<DetalleMorVo> getResultados() {
		return resultados;
	}

	public void setResultados(List<DetalleMorVo> resultados) {
		this.resultados = resultados;
	}

	public MorEJBService getMorEJBService() {
		return morEJBService;
	}

	public void setMorEJBService(MorEJBService morEJBService) {
		this.morEJBService = morEJBService;
	}

	public ConsultaRecuperacionDTO getConsultaRecuperacionDTO() {
		return consultaRecuperacionDTO;
	}

	public void setConsultaRecuperacionDTO(
			ConsultaRecuperacionDTO consultaRecuperacionDTO) {
		this.consultaRecuperacionDTO = consultaRecuperacionDTO;
	}

	
	

	public void setListaEstados(List<EstadoRecuperacionMorVo> listaEstados) {
		this.listaEstados = listaEstados;
	}
	
	

	public List<SelectItem> getModuloOrigen() {
		moduloOrigen = new ArrayList<SelectItem>();
		for(ModuloOrigenVo e : morEJBService.obtenerModulosOrigen()){
			moduloOrigen.add(new SelectItem(e.getIdModuloOrigenMor(), e.getNombreOrigenModuloOrigernMor()));
		}	
		return moduloOrigen;
	}

	public void setModuloOrigen(List<SelectItem> moduloOrigen) {
		this.moduloOrigen = moduloOrigen;
	}

	public List<SelectItem> getTipoMovimiento() {
		tipoMovimiento = new ArrayList<SelectItem>();
		for(TipoMovimientoVo e : morEJBService.obtenerTiposMovimiento()){
			tipoMovimiento.add(new SelectItem(e.getIdTipoMovimientoMor(), e.getDescripcionTipoMovimiento()));
		}	
		return tipoMovimiento;
	}

	public void setTipoMovimiento(List<SelectItem> tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	

	public List<ModuloOrigenVo> getListaModulosOrigen() {
		
		return morEJBService.obtenerModulosOrigen();
	}

	public List<TipoMovimientoVo> getListaTiposMovimiento() {
		 
		return morEJBService.obtenerTiposMovimiento();
	}

	public boolean isMuestraCriteriosSinDatos() {
		return muestraCriteriosSinDatos;
	}

	public void setMuestraCriteriosSinDatos(boolean muestraCriteriosSinDatos) {
		this.muestraCriteriosSinDatos = muestraCriteriosSinDatos;
	}

	public PaginaVo buscaDummy(){
		List<RecuperacionMorVo> registros = new ArrayList<RecuperacionMorVo>();
		List<MovimientoMorVo>lstMov = new ArrayList<MovimientoMorVo>();
		PaginaVo paginaVo = new PaginaVo();
		paginaVo.setOffset(0);
		
		paginaVo.setRegistrosXPag(10);
		
		
		MovimientoMorVo mov= new MovimientoMorVo();
		mov.setCantidadTitulos(123l);
		mov.setClaveTipoInstitucionReceptora("inst recep");
		mov.setClaveTipoInstitucionTraspasante("inst trans");
		mov.setCuentaReceptor("ctaRecept");
		mov.setCuentaTraspasante("ctaTrasp");
		mov.setEstadoMovimiento("Liquidado");
		lstMov.add(mov);
		mov= new MovimientoMorVo();
		mov.setCantidadTitulos(123l);
		mov.setClaveTipoInstitucionReceptora("inst recep2");
		mov.setClaveTipoInstitucionTraspasante("inst trans2");
		mov.setCuentaReceptor("ctaRecept2");
		mov.setCuentaTraspasante("ctaTrasp2");
		mov.setEstadoMovimiento("Liquidado2");
		
		
		RecuperacionMorVo reg = new RecuperacionMorVo();
		
		reg.setBloqueada(false);
		reg.setBloqueada(false);
		reg.setCupon("0001");
		reg.setDescEdoRecuperacion("edoRecupera");
		reg.setEmisora("1M");
		reg.setDescEdoRecuperacion("DescRecupera");
		reg.setFechaCorte(fechasUtilService.getCurrentDate());
		reg.setFechaLiquidacion(fechasUtilService.getCurrentDate());
		reg.setFechaPago(fechasUtilService.getCurrentDate());
		reg.setIdEstadoRecuperacionMor(1);
		reg.setIdRecuperacionMor(2l);
		reg.setLstMovimientoMorVo(lstMov);
		
		reg.setMontoPago(BigDecimal.TEN);
		reg.setNumeroMovimientos(123l);
		reg.setNumeroTitulos(321l);
		reg.setSerie("a");
		reg.setTv("1a");
		registros.add(reg);
		reg.setLstMovimientoMorVo(lstMov);
		
		paginaVo.setRegistros(registros);
		
		
		
		
		return paginaVo;
	}

	/**
	 * @return the montoTotal
	 */
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	/**
	 * @return the cantidadTotal
	 */
	public long getCantidadTotal() {
		return cantidadTotal;
	}

	/**
	 * @param cantidadTotal the cantidadTotal to set
	 */
	public void setCantidadTotal(long cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}
	

}
