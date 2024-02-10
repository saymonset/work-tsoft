/*
 * Copyright (c) 2005-2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.modelo.TipoCuenta;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portaldali.persistence.util.constants.DaliConstants;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.ExportacionConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.ErrorSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Backing bean que exporta la consulta de operaciones SIC a PDF o Excel 
 * @author jayalam
 *
 */
public class ConsultaOperacionesExportacionController extends ControllerBase {

	private String idTipoValor;
	private String emisora;
	private String serie;
	private String cupon;
	
	private String operacion;
	private String folioControl;
	
	/** Divisa. Utilizado para realizar consultas */
	private String divisa;
	
	private String origenOperacion;
	
	/** importe, utilizado para hacer consultas */
	private BigDecimal importe;

	private String isin;
	private String custodio;
		
	/**
	 * Resultados de la consulta de reportes
	 */
	private PaginaVO resultados;
	private String estado;
	
	/**
	 * Par&#225;metros de la consulta
	 */
	private OperacionSic params = new OperacionSic();
	
	/**Dao de divisas*/
	private DivisaDao divisaDao;
	
	/**
	 * Acceso a la consulta de cuenta nombradda
	 */
	private CuentaNombradaDao cuentaNombradaDao = null;
	
	private String referenciaOperacion;
	
	private BigInteger cantidadOperada;
	
	private Date fechaInicialConsulta;
	private Date fechaFinalConsulta;
	
	private String tipoOperacion;

	
	/**
	 * Servicio de divisi&#243;n internacional
	 */
	private DivisionInternacionalService divisionInternacionalService;
	
	/**
	 * IDFolio
	 */
	private String idfolio;
	
	/** Mantiene la cuenta del agente */
	private String cuentaAgente;
	
	/**
	 * Indica si la consulta ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaOperacionesExportacionController.class);
	
	public ConsultaOperacionesExportacionController() {
		inicializarParams();
	}
	
	public void inicializarParams() {
		params.setBoveda(new Boveda());
		params.setCatBic(new CatBic());
		params.setCuentaNombrada(new CuentaNombrada());
		params.getCuentaNombrada().setInstitucion(new Institucion());
		params.getCuentaNombrada().getInstitucion().setTipoInstitucion(
				new TipoInstitucion());
		params.setEmision(new Emision());
		params.getEmision().setInstrumento(new Instrumento());
		params.getEmision().setEmisora(new Emisora());
		params.setSicDetalle(new SicDetalle());
		params.setFechaConsulta(new Date[] {});
		params.setEstatusOperacion(new EstatusOperacion());
		params.setErrorSwift(new ErrorSwift());

		cuentaAgente = new String();
		idTipoValor = new String();
		emisora = new String();
		serie = new String();
		cupon = new String();

		estado = "-1";
		divisa = "-1";
		importe = null;

		cantidadOperada = null;
		tipoOperacion = new String();
		origenOperacion = new String();

		operacion = "-1";
		tipoOperacion = "-1";
		origenOperacion= "-1";
		
		folioControl = new String();
		referenciaOperacion = new String();
		
		isin = null;
		
		
	}
	
	/**
	 * Limpia los resultados de la consulta y los criterios.
	 * 
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		consultaEjecutada = false;
		paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(50);
		inicializarParams();

	}
	
	/**
	 * Crea el objeto de criterio de consulta
	 */
	public void crearCriterios() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ConsultaOperacionesController consultaBean = 
				(ConsultaOperacionesController)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "consultaOperacionesBean");
		this.params = consultaBean.getParams();
		this.idfolio = consultaBean.getIdfolio();
		this.cuentaAgente = consultaBean.getCuentaAgente();
		this.idTipoValor = consultaBean.getIdTipoValor();
		this.emisora = consultaBean.getEmisora();
		this.serie = consultaBean.getSerie();
		this.cupon = consultaBean.getCupon();
		this.origenOperacion = consultaBean.getOrigenOperacion();
		this.estado = consultaBean.getEstado();
		this.divisa = consultaBean.getDivisa();
		this.importe = consultaBean.getImporte();
		this.isin = consultaBean.getIsin();
		this.custodio = consultaBean.getCustodio();
		this.cantidadOperada = consultaBean.getCantidadOperada();
		this.fechaInicialConsulta = consultaBean.getFechaInicialConsulta();
		this.fechaFinalConsulta = consultaBean.getFechaFinalConsulta();
		this.operacion = consultaBean.getOperacion();
		this.tipoOperacion = consultaBean.getTipoOperacion();
		this.referenciaOperacion = consultaBean.getReferenciaOperacion();
		this.folioControl = consultaBean.getFolioControl();


		EmisionVO emisionVo = new EmisionVO();
		emisionVo.setTv(idTipoValor);
		emisionVo.setEmisora(emisora);
		emisionVo.setSerie(serie);
		
		
//		emisionVo.setCupon(cupon);

		params.setEmision(new Emision(emisionVo));
		
		
		if (StringUtils.isNotBlank(isin)) {
			emisionVo.setIsin(StringUtils.upperCase(isin.trim()));
			params.getEmision().setIsin(StringUtils.upperCase(isin));
		}
		params.setClaveCupon(cupon);
		params.setCuentaNombrada(new CuentaNombrada(
				new AgenteVO(AgenteViewHelper.obtenerIdInstitucion(idfolio),
						AgenteViewHelper.obtenerFolioInstitucion(idfolio),
						cuentaAgente)));
		params.getCuentaNombrada().setTipoCuenta(new TipoCuenta());
		params.getCuentaNombrada().getTipoCuenta().setIdTipoCuenta(0L);
		List<CuentaNombrada> cuentas = this.cuentaNombradaDao
				.findCuentasByNumeroCuenta(params.getCuentaNombrada(), 20);

		if (cuentas.size() == 1) {
			params.setCuentaNombrada(cuentas.get(0));
		}

		params.setFechaConsulta(new Date[] { fechaInicialConsulta,
				fechaFinalConsulta });

		if (divisa != null && !"-1".equals(divisa)) {
			params.setDivisa(divisa);
		} else {
			params.setDivisa(null);
		}

		if (importe != null) {
			params.setImporte(importe);
		} else {
			params.setImporte(null);
		}

		// params.setStatus(estado);
		if (operacion != null && !"-1".equals(operacion)) {
			params.setTipoTraspaso(operacion);
		} else {
			params.setTipoTraspaso(null);
		}
		if (tipoOperacion != null && !"-1".equals(tipoOperacion)) {
			params.setTipoOperacion(tipoOperacion);
		} else {
			params.setTipoOperacion(null);
		}
		
		if (origenOperacion != null && !"-1".equals(origenOperacion)) {
			params.setOrigenPfi(new BigInteger(origenOperacion));
		} else {
			params.setOrigenPfi(null);
		}
		// long id = NumberUtils.toLong(llaveFolio, -1);
		// params.setIdOperacionesSic(id==-1?null:new Long(id));
		
		
		/* se agrega validacion al folioControl, se verifica 
		 * que sea un valor numerico antes de convertirlo a BigInteger*/
		 
		params.setFolioControl(StringUtils.isNotBlank(folioControl) && 
				StringUtils.isNumeric(folioControl) ? new BigInteger(folioControl): null);
		if (cantidadOperada != null) {
			params.setCantidadTitulos(cantidadOperada);
		} else {
			params.setCantidadTitulos(null);

		}
		if (estado != null && !"-1".equals(estado)) {
			params.getEstatusOperacion().setIdEstatusOperacion(Long.valueOf(estado));
		} else {
			params.getEstatusOperacion().setIdEstatusOperacion(null);
		}
		

		/* Modificacion, se agrega condicion para setear referencia operacion*/
		params.setReferenciaOperacion(StringUtils.isNotBlank(referenciaOperacion) ? referenciaOperacion.toUpperCase() : null);
			
		params.setCatBic(null);
		if (!custodio.equalsIgnoreCase("-1")){
			CatBic cb = new CatBic();
			cb.setDetalleCustodio(custodio.trim());
			params.setCatBic(cb);
		}
	}


	
	/**
	 * Genera los reportes en formato PDF o Excel
	 * @param e ActionListener que lo invoca
	 */
	public void generarReportes(ActionEvent e) {
		inicializarParams();
		crearCriterios();
		reiniciarEstadoPeticion();
		try {
            this.resultados = new PaginaVO();
            this.resultados.setOffset(0);
            //Integer registrosXPag = tipoReporte.equals(GENERAR_EXCEL) ? Constantes.MAX_REGISTROS_EXPORTAR : null; 
            Integer registrosXPag = Constantes.MAX_REGISTROS_EXPORTAR;
            this.resultados.setRegistrosXPag(registrosXPag);
            this.resultados =
                    this.divisionInternacionalService.consultaOperaciones(this.params, this.resultados, false, false, false);
            this.resultados.setTotalRegistros(this.resultados.getRegistros().size());
            consultaEjecutada = true;
		}
		catch (BusinessException exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
		}
		catch (Exception exc) {
			log.error("Ocurrio un error:",exc);
			agregarInfoMensaje(exc.getMessage());
		}
		catch (Throwable exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
		}		
	}
	
	/**
	 * Metodo que retorna el idfolio del participante selecccionado en la consulta
	 * @return el idfolio del participante
	 */
	public String getParticipanteSeleccionado(){
		String descripcion = "";
		if (idfolio != null && !StringUtils.isBlank(idfolio)) {
			descripcion = idfolio;
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la cuenta del participante selecccionada en la consulta
	 * @return la cuenta del participante
	 */
	public String getCuentaSeleccionada(){		
		String descripcion = "";		
		if (cuentaAgente != null && !StringUtils.isBlank(cuentaAgente)) {
			descripcion = cuentaAgente;
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el tipo valor selecccionado en la consulta
	 * @return el tipo valor
	 */
	public String getTipoValorSeleccionado(){		
		String descripcion ="";
		if(idTipoValor != null && !StringUtils.isBlank(idTipoValor)){
			descripcion = idTipoValor.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la emisora selecccionado en la consulta
	 * @return la emisora seleccionada
	 */
	public String getEmisoraSeleccionada(){
		String descripcion ="";
		if(emisora != null && !StringUtils.isBlank(emisora)){
			descripcion = emisora.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la serie selecccionado en la consulta
	 * @return la serie seleccionada
	 */
	public String getSerieSeleccionada(){
		String descripcion ="";
		if(serie != null && !StringUtils.isBlank(serie)){
			descripcion = serie.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el cupon selecccionado en la consulta
	 * @return el cupon seleccionada
	 */
	public String getCuponSeleccionado(){
		String descripcion ="";
		if(cupon != null && !StringUtils.isBlank(cupon)){
			descripcion = cupon;				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el origen de la operacion seleccionado
	 * @return el origen de la operacion
	 */
	public String getOrigenSeleccionado(){
		String descripcion = "";
		
		if ("-1".equals(origenOperacion)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}else if (Constantes.CERO_STRING.equals(origenOperacion)) {
			descripcion = Constantes.OPERACION_INGRESADA_POR_PORTAL;
		}else {
			descripcion = Constantes.OPERACION_INGRESADA_POR_PFI;
		}
				
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion del estatus selecccionado en la consulta
	 * @return la descripcion del estatus de la operacion
	 */
	public String getEstatusSeleccionado(){
		String descripcion = "";		
		
		if ("-1".equals(estado)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}else {
			EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
			if (estados != null && estados.length > 0) {				
				int i = 0;				
				while (i <= estados.length -1  ) {					
					if(estados[i].getIdEstatusOperacion().equals(Long.valueOf(estado))){					
						descripcion = estados[i].getDescEstatusOperacion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getDivisaSeleccionada(){
		String descripcion="";
		
		if ("-1".equals(divisa)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else {
			Divisa[] divisas = divisaDao.findDivisas();
			if (divisas != null && divisas.length > 0) {				
				int i = 0;				
				while (i <= divisas.length -1  ) {					
					if(divisas[i].getClaveAlfabetica().equals(divisa)){					
						descripcion = divisas[i].getDescripcion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
		
	}
	
	/**
	 * Metodo que retorna el importe selecccionado en la consulta
	 * @return el importe seleccionado
	 */
	public String getImporteSeleccionado(){
		String descripcion = "";
		if (importe != null){
			DecimalFormat df = new DecimalFormat(DaliConstants.FORMATO_DECIMAL);
			descripcion = df.format(Double.parseDouble(importe.toString()));
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}	
		return descripcion;
	}		
	
	/**
	 * Metodo que retorna la cantidad selecccionada en la consulta
	 * @return la cantidad operada seleccionado
	 */
	public String getCantidadSeleccionada(){
		String descripcion ="";
		if(cantidadOperada != null){
			descripcion = String.valueOf(cantidadOperada);				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
	return descripcion;
	}
	
	/**
	 * Metodo que retorna fecha inicial selecccionada en la consulta
	 * @return fecha inicial seleccionada
	 */
	public Object getFechaInicialSeleccionada() {	
		return fechaInicialConsulta != null ? fechaInicialConsulta : ExportacionConstantes.TODAS;
	}
	
	/**
	 * Metodo que retorna fecha final selecccionada en la consulta
	 * @return fecha final seleccionada
	 */
	public Object getFechaFinalSeleccionada() {
		return fechaFinalConsulta != null ? fechaFinalConsulta : ExportacionConstantes.TODAS;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getOperacionSeleccionada(){
		String descripcion = "";
		
		if ("-1".equals(operacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else if (Constantes.TIPO_MOVTO_E.equals(operacion)) {
			descripcion = Constantes.MOVTO_ENTREGA;
		} else {
			descripcion = Constantes.MOVTO_RECIBE;
		}		
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getTipoOperacionSeleccionada(){
		String descripcion = "";
		
		if ("-1".equals(tipoOperacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}else if (Constantes.TRASPASO_CONTRA.equals(tipoOperacion)) {
			descripcion = Constantes.DESC_TRASPASO_CONTRA;
		}else {
			descripcion = Constantes.DESC_TRASPASO_LIBRE;
		}
				
		return descripcion;
	}
	
	public String getReferenciaOperacionSeleccionado(){
		String descripcion = "";
		
		if(referenciaOperacion != null && !StringUtils.isBlank(referenciaOperacion)){
			descripcion = referenciaOperacion.toUpperCase();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna el folio control selecccionado en la consulta
	 * @return folio control seleccionada
	 */
	public String getFolioControlSeleccionado(){
		String descripcion ="";
		if(folioControl != null && !StringUtils.isBlank(folioControl)){
			descripcion = folioControl;				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
	return descripcion;
	}

	
	/**
	 * Obtiene el valor de Servicio de divisi&#243;n internacional
	 * @return El Servicio de divisi&#243;n internacional
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService
	 *            the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}
	
	public OperacionSic getParams() {
		return params;
	}

	public void setParams(OperacionSic params) {
		this.params = params;
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
	 * Devuelve el valor del campo resultados
	 * 
	 * @return resultados
	 */
	public PaginaVO getResultados() {
		return resultados;
	}

	/**
	 * Asigna resultados al campo resultados
	 * 
	 * @param resultados
	 *            el resultados que se asigna
	 */
	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}
	
	/**
	 * Obtiene el valor de la propiedad <code>idFolio</code>
	 * @return El IDFolio
	 */
	public String getIdfolio() {
		return idfolio;
	}
	
	/**
	 * Modifica el valor de la propiedad <code>idFolio</code>
	 * @param idfolio El IDFolio
	 */
	public void setIdfolio(String idfolio) {
		this.idfolio = idfolio;
	}
	
	/**
	 * @return the cuentaAgente
	 */
	public String getCuentaAgente() {
		return cuentaAgente;
	}

	/**
	 * @param cuentaAgente
	 *            the cuentaAgente to set
	 */
	public void setCuentaAgente(String cuentaAgente) {
		this.cuentaAgente = cuentaAgente;
	}

	/**
	 * @return the idTipoValor
	 */
	public String getIdTipoValor() {
		return idTipoValor;
	}

	/**
	 * @param idTipoValor
	 *            the idTipoValor to set
	 */
	public void setIdTipoValor(String idTipoValor) {
		this.idTipoValor = idTipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}
	
	public String getOrigenOperacion() {
		return origenOperacion;
	}

	public void setOrigenOperacion(String origenOperacion) {
		this.origenOperacion = origenOperacion;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
	/**
	 * @return the divisaDao
	 */
	public DivisaDao getDivisaDao() {
		return divisaDao;
	}

	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}
	
	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
    
	public String getIsin() {		
		return StringUtils.upperCase(isin);
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}
	
	/**
	 * @return the cantidadOperada
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 *            the cantidadOperada to set
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	public Date getFechaInicialConsulta() {
		return fechaInicialConsulta;
	}

	public void setFechaInicialConsulta(Date fechaInicialConsulta) {
		this.fechaInicialConsulta = fechaInicialConsulta;
	}
	
	/**
	 * @return the fechaFinalConsulta
	 */
	public Date getFechaFinalConsulta() {
		return fechaFinalConsulta;
	}

	/**
	 * @param fechaFinalConsulta
	 *            the fechaFinalConsulta to set
	 */
	public void setFechaFinalConsulta(Date fechaFinalConsulta) {
		this.fechaFinalConsulta = fechaFinalConsulta;
	}
	
	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion
	 *            the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}
	
	/**
	 * @return the llaveFolio
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param llaveFolio
	 *            the llaveFolio to set
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}
	
	/**
	 * @return the cuentaNombradaDao
	 */
	public CuentaNombradaDao getCuentaNombradaDao() {
		return cuentaNombradaDao;
	}

	/**
	 * @param cuentaNombradaDao
	 *            the cuentaNombradaDao to set
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}


}
