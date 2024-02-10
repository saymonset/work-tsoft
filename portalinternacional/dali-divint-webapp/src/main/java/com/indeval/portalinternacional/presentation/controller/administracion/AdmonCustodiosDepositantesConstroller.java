/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 8, 2008
 */
package com.indeval.portalinternacional.presentation.controller.administracion;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.Calendar;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.AltaCustodioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCustodiosVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Administracion de Custodios y Depositantes
 *
 * @author Erik Vera Montoya.
 * @version 1.0
 *
 */
public class AdmonCustodiosDepositantesConstroller extends ControllerBase {
	
	/**Cadena de idFolio para Agente*/
	private String claveParticipante;
	
	/**Cadena para la cuenta de Agente*/
	private String cuentaParticipante;
	
	/**Cadena para nombre Corto del Agente*/
	private String nombreCortoParticipante;
	
	/**Servicio para la administracion de custodios y depositantes */
	private DivisionInternacionalService divisionInternacionalService;
	
	/**Servicio para traer los datos del participante*/
	private ConsultaCatalogoService consultaCatalogoService;
	
	/**Variable para indicar que ya se ejecuto la consulta*/
	private boolean consultaEjecutada;
	
	/**Objeto para indicar el custodio seleccionado*/
	private ConsultaCustodiosVO custodioActual;
	
	/** Objecto con los datos del custodio a registrar */
	private AltaCustodioVO custodioAlta;
	
	/**Cadena de idFolio para Agente a registrar*/
	private String claveParticipanteAlta;
	
	/**Cadena para la cuenta de Agente a registrar*/
	private String cuentaParticipanteAlta;
	
	/**Cadena para nombre Corto del Agente a registrar*/
	private String nombreCortoParticipanteAlta;
	
	/** ID para regresar a la pantalla de consulta */
	private String PANTALLA_CONSULTA = "regresaAdmonCustDep";	
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AdmonCustodiosDepositantesConstroller.class);
	
	/**Beans para paginacion de Depositantes Liquidadores y Emisiones*/
	private AdmonCustodiosDepositantesDepLiqConstroller depositantesLiqBean;
	private AdmonCustodiosDepositantesEmiConstroller emisionesBean;
	
	/**Bean para la consulta de Emisiones*/
	
	private AdmonCustodiosDepositantesConsultaEmiController consultaEmisionesBean;
	
	/**Bean para la consulta de Depositantes*/
	private AdmonCustodiosDepositantesConsultaDepLiqController consultaDepositantesBean;

	private String idEmisioeditar;
    
    /** 
     * Cadena para agregar un Tipo Valor al Custodio 
     */
    private String strElementoAgregar = "";
    
    /** 
     * Objeto utilizado para eliminar elementos del litado de Tipos Valor del Custodio
     */
    private Long idElementoSeleccionado = new Long(-1);
    
    /** 
     * Lista con los elementos que se pueden eliminar del listado de Tipos Valor del Custodio
     */
    private List<SelectItem> listadoElementos = new ArrayList<SelectItem>();
    
    /** 
     * Cadena para agregar el porcentaje de un formato 
     */
    private String strElementoPorcentajeAgregar = "";
    
    /** 
     * Objeto utilizado para eliminar elementos del litado de Formatos del Custodio
     */
    private Long idElementoFormatoSeleccionado = new Long(-1);
    
    /** 
     * Lista con los elementos de Formatos del Custodio
     */
    private List<SelectItem> listadoElementosFormato = new ArrayList<SelectItem>();
    
    /** 
     * Objeto utilizado para almacenar el elemento del select de tipos de formato
     */
    private Long idSelectFormatoSeleccionado = new Long(-1);

    /**
     * Bandera para verificar si la pantalla se encuentra en modo edicion de custodio. La bandera se usa
     * para ver si en el metodo limpiarForma() se debe limpiar el listado de elementos de tipos valor de
     * custodio al seleccionar el boton Limpiar.
     */
    private boolean modoConsultaCustodio = false;
    
	public void validaParticipante(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.validaParticipante");
		AgenteVO agente = construyeAgente();
		List<ConsultaCustodiosVO> lista = divisionInternacionalService.consultaCustodios(agente);
		if (lista != null && !lista.isEmpty() && lista.size()==1) {
			ConsultaCustodiosVO custodio = lista.get(0);
				if (custodio.getCatBic()!=null && custodio.getCatBic().getCuentaNombrada()!= null && custodio.getCatBic().getCuentaNombrada().getRazonSocialCuenta() != null) {
					nombreCortoParticipante=custodio.getCatBic().getCuentaNombrada().getRazonSocialCuenta();
					log.info("INSTITUCION: [" + nombreCortoParticipante + "]");
				} else {
					log.info("NO HAY Razon Social");
				}
		}		
	}
	
	public void validaParticipanteAlta(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.validaParticipanteAlta");
		if( isIdFolioValidoAlta() ) {
			Institucion ins = consultaCatalogoService.findInstitucionByClaveFolio(claveParticipanteAlta);
			if( ins != null ) {
				nombreCortoParticipanteAlta = ins.getRazonSocial();
				log.info("INSTITUCION: [" + nombreCortoParticipanteAlta + "]");
			} else {
				log.info("NO HAY INSTITUCION");
			}
		}
	}
	
	public void buscarCustodios(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.buscarCustodios");
		
		AgenteVO agenteVO = construyeAgente();
		List<ConsultaCustodiosVO> lista = divisionInternacionalService.consultaCustodios(agenteVO);
		
		paginaVO = new PaginaVO();
		if( lista != null && lista.size() > 0 ) {
			log.info("Registro encontrados: [" + lista.size() + "]");
			paginaVO.setOffset(0);
			paginaVO.setRegistros(lista);
			paginaVO.setRegistrosXPag(1);
			paginaVO.setTotalRegistros(lista.size());
		}
		
		ejecutarConsulta();
		consultaEjecutada = true;
	}
	
	public String ejecutarConsulta() {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.ejecutarConsuta");
		if( paginaVO.getRegistros() != null && paginaVO.getRegistros().size() > 0 ) {
			custodioActual = (ConsultaCustodiosVO)paginaVO.getRegistros().get(paginaVO.getOffset());			
			log.info("Tam de depositantes: [" + custodioActual.getSicDetalles().size() + "]");
			depositantesLiqBean.setLista(custodioActual.getSicDetalles());
			log.info("Tam de emisiones: [" + custodioActual.getSicEmisiones().size() + "]");
			emisionesBean.setLista(custodioActual.getSicEmisiones());
			this.listadoElementos = this.armarListadoElementos();
            this.listadoElementosFormato = this.armarListadoElementosFormato();
		} else {
			custodioActual = new ConsultaCustodiosVO();
			depositantesLiqBean.limpiar();
			emisionesBean.limpiar();
            this.listadoElementos = new ArrayList<SelectItem>();
            this.idElementoSeleccionado = new Long(-1);
		}
		return null;
	}

    /**
     * Arma el listado de elementos a mostrar en la caja de seleccion de Formatos de custodio.
     * @return Una lista de SelectItem a mostrar.
     */
    private List<SelectItem> armarListadoElementosFormato() {
        List<SelectItem> elems = new ArrayList<SelectItem>();
        if (this.custodioActual != null && this.custodioActual.getFormatosCustodio() != null) {
            for (String formato : this.custodioActual.getFormatosCustodio()) {
                String[] sep = formato.split("-");
                elems.add(new SelectItem(Long.valueOf(sep[0]), sep[1] + "-" + (Float.valueOf(sep[2])).toString()));
            }
        }

        return elems;
    }

	/**
	 * Arma el listado de elementos a mostrar en la caja de seleccion de Tipos Valor de custodio.
	 * @return Una lista de SelectItem a mostrar.
	 */
	private List<SelectItem> armarListadoElementos() {
	    List<SelectItem> elems = new ArrayList<SelectItem>();
	    if (this.custodioActual != null && this.custodioActual.getTiposValorCustodio() != null) {
	        for (String tv : this.custodioActual.getTiposValorCustodio()) {
                elems.add(new SelectItem(Calendar.getInstance().getTime().getTime(), tv));
            }
	    }

	    return elems;
	}

	/**
	 * M&eacute;todo que genera los reportes en PDF o XLS
	 * 
	 * @param El action listener que lo invoca
	 */
	public void generarReportes(ActionEvent e) {

		reiniciarEstadoPeticion();
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.ejecutarConsuta");
		if( paginaVO.getRegistros() != null && paginaVO.getRegistros().size() > 0 ) {
			
			if(custodioActual.getSicDetalles().isEmpty() && custodioActual.getSicEmisiones().isEmpty()) {
				AgenteVO agenteVO = construyeAgente();
				List<ConsultaCustodiosVO> lista = divisionInternacionalService.consultaCustodios(agenteVO);
				int indice = paginaVO.getOffset();
				custodioActual = lista.get(indice);
			}
			
			
			
			//custodioActual = (ConsultaCustodiosVO)paginaVO.getRegistros().get(paginaVO.getOffset());
			log.info("Tam de depositantes: [" + custodioActual.getSicDetalles().size() + "]");
			depositantesLiqBean.setLista(custodioActual.getSicDetalles());
			log.info("Tam de emisiones: [" + custodioActual.getSicEmisiones().size() + "]");
			emisionesBean.setLista(custodioActual.getSicEmisiones());
		} else {
			custodioActual = new ConsultaCustodiosVO();
			depositantesLiqBean.limpiar();
			emisionesBean.limpiar();
		}			
	}
	
	public void nuevoCustodio(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.nuevoCustodio");
		this.modoConsultaCustodio = false;
		// Limpia la forma
		limpiarForma(null);
		
		/*if( isIdFolioValido() ) {
			claveParticipanteAlta = claveParticipante;
			nombreCortoParticipanteAlta = nombreCortoParticipante;
		}
		if( isCuentaValida() ) {
			cuentaParticipanteAlta = cuentaParticipante;
		}*/
	}
	/**
	 * Metodo que enlaza la accion de editar un depositante con referencia 
	 * al custodio actual
	 * @param event
	 */
	public void editarCustodio(ActionEvent event){
		claveParticipanteAlta = custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() +  custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion();
		cuentaParticipanteAlta = custodioActual.getCatBic().getCuentaNombrada().getCuenta();
		nombreCortoParticipanteAlta = custodioActual.getCatBic().getCuentaNombrada().getRazonSocialCuenta();
		this.modoConsultaCustodio = true;
	}
	
	/**
	 * Metodo que edita el custodio actual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cambiarCustodio(){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.cambiarCustodio");
		try {

			AltaCustodioVO altaCustodioVO = new AltaCustodioVO();
			altaCustodioVO.setProduccion(custodioActual.getCatBic().getBicProd());
			altaCustodioVO.setEntrenamiento(custodioActual.getCatBic().getBicPrueba());
			altaCustodioVO.setCuentaIndeval(custodioActual.getCatBic().getCuentaIndeval());
			altaCustodioVO.setPais(custodioActual.getCatBic().getPais());
			altaCustodioVO.setMercado(custodioActual.getCatBic().getMercado());
			altaCustodioVO.setMoneda(custodioActual.getCatBic().getMoneda());
			AgenteVO custodio = new AgenteVO();
			custodio = construyeAgenteAlta();
			altaCustodioVO.setCustodio(custodio);
			altaCustodioVO.setEstatus(custodioActual.getCatBic().getStatus());
            altaCustodioVO.setDetalleCustodio(custodioActual.getCatBic().getDetalleCustodio());
            altaCustodioVO.setAbreviacion(custodioActual.getAbreviacionCustodio());
            altaCustodioVO.setNombreCorto(custodioActual.getNombreCorto());
            altaCustodioVO.setFactorCalculado(custodioActual.getFactorCalculado());
            altaCustodioVO.setListaTiposValorCustodio(this.obtenerTiposValorCustodio());
            altaCustodioVO.setListaFormatosCustodio(this.obtenerFormatosCustodio());
			divisionInternacionalService.updateCustodio(altaCustodioVO, custodioActual.getCatBic());
		} catch(BusinessException be) {
			log.error("Ocurrio un error:",be);
			agregarMensaje(be);
			return null; //Para que saque la misma pantalla de registro
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;	
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de registro
		}

        this.listadoElementos = new ArrayList<SelectItem>();
        this.strElementoAgregar = "";
        this.idElementoSeleccionado = new Long(-1);
        this.listadoElementosFormato = new ArrayList<SelectItem>();
        this.strElementoPorcentajeAgregar = "";
        this.idElementoFormatoSeleccionado = new Long(-1);
        this.idSelectFormatoSeleccionado = new Long(-1);
        this.modoConsultaCustodio = false;

		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	/**
	 * Metodo que cancela el depositante actual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cancelarCustodio(){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.cancelarCustodio");
		try {
			divisionInternacionalService.cancelaCustodio(custodioActual.getCatBic());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de consulta
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de consulta
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	
	public String guardarCustodio() {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.guardarCustodio");
		
		AgenteVO agenteVO = construyeAgenteAlta();
		log.info("Agente a guardar: [" + agenteVO.getId() + agenteVO.getFolio() + 
				"-" + agenteVO.getCuenta() + "]");
		custodioAlta.setCustodio(agenteVO);
		custodioAlta.setListaTiposValorCustodio(this.obtenerTiposValorCustodio());
        custodioAlta.setListaFormatosCustodio(this.obtenerFormatosCustodio());
		try {
			divisionInternacionalService.altaCustodio(custodioAlta);
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de registro
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;	
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de registro
		}
		
		// Para que rehaga la consulta con los parametros del alta
		claveParticipante = claveParticipanteAlta;
		cuentaParticipante = cuentaParticipanteAlta;
        this.listadoElementos = new ArrayList<SelectItem>();
        this.strElementoAgregar = "";
        this.idElementoSeleccionado = new Long(-1);
        this.listadoElementosFormato = new ArrayList<SelectItem>();
        this.strElementoPorcentajeAgregar = "";
        this.idElementoFormatoSeleccionado = new Long(-1);
        this.idSelectFormatoSeleccionado = new Long(-1);
        this.modoConsultaCustodio = false;
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}

    /**
     * Obtiene las cadenas de formatos que se asignaran al custodio.
     * @return El listado de formatos
     */
    private List<String> obtenerFormatosCustodio() {
        List<String> formatos = new ArrayList<String>();
        if (this.listadoElementosFormato != null && !this.listadoElementosFormato.isEmpty()) {
            for (SelectItem item : this.listadoElementosFormato) {
                formatos.add(item.getValue() + "-" + item.getLabel());
            }
        }

        return formatos;
    }

	/**
	 * Obtiene las cadenas de tipos valor que se asignaran al custodio.
	 * @return El listado de tv's
	 */
	private List<String> obtenerTiposValorCustodio() {
	    List<String> tvs = new ArrayList<String>();
	    if (this.listadoElementos != null && !this.listadoElementos.isEmpty()) {
	        for (SelectItem item : this.listadoElementos) {
                tvs.add((String) item.getLabel());
            }
	    }

	    return tvs;
	}

	public void nuevoDepositante(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.nuevoDepositante");
		
		depositantesLiqBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		depositantesLiqBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		depositantesLiqBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		
		depositantesLiqBean.setDepositante(new SicDetalle());
	}
	/**
	 * Metodo que enlaza la accion de insertar depositantes
	 * al custodio actual
	 * @param event
	 */
	public String guardarDepositante() {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.guardarDepositante");
		SicDetalle dep = depositantesLiqBean.getDepositante();
		try {			
			divisionInternacionalService.altaDepositante(custodioActual.getCatBic(), dep.getBicDepLiq(), dep.getIdDepLiq(), dep.getDepLiq());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de registro
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de registro
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	/**
	 * Metodo que enlaza la accion de editar un depositante con referencia 
	 * al custodio actual
	 * @param event
	 */
	public void editarDepositante(ActionEvent event){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.editarDepositante");
		
		depositantesLiqBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		depositantesLiqBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		depositantesLiqBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		
	}
	/**
	 * Metodo que edita el depositante actual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cambiarDepositante(){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.cambiarDepositante");		
		try {			
			divisionInternacionalService.updateDepositante(custodioActual.getCatBic(), depositantesLiqBean.getDetalleActual().getBicDepLiq(), depositantesLiqBean.getDetalleActual().getIdDepLiq(), depositantesLiqBean.getDetalleActual().getDepLiq(),depositantesLiqBean.getDetalleActual());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de registro
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de registro
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	
	/**
	 * Metodo que cancela el depositante actual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cancelarDepositante(){
		try {
			divisionInternacionalService.cancelaDepositante(depositantesLiqBean.getDetalleActual());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de consulta
		} catch(Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de consulta
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	
	public void nuevaEmision(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.nuevaEmision");
		
		emisionesBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		emisionesBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		emisionesBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		
		emisionesBean.setEmisionVO(new EmisionVO());
		emisionesBean.setFormaOperar(null);
	}
	/**
	 * Metodo que enlaza la accion de buscar emisiones con referencia 
	 * al custodio actual
	 * @param event
	 */
	public void buscarEmision(ActionEvent event){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.buscarEmision");
		//consultaDepositantesBean = new AdmonCustodiosDepositantesConsultaDepLiqController();
		consultaEmisionesBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		consultaEmisionesBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		consultaEmisionesBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		EmisionVO emision = new EmisionVO();
		consultaEmisionesBean.setEmisionVO(emision);
		consultaEmisionesBean.setFormaOperar(null);
		consultaEmisionesBean.setCustodioActual(custodioActual);
		consultaEmisionesBean.setConsultaEmisionesEjecutada(Boolean.FALSE);		
		PaginaVO pagina = new PaginaVO();
		pagina.setOffset(0);
		pagina.setRegistrosXPag(50);
		consultaEmisionesBean.setPaginaVO(pagina);	
	}
	
	/**
	 * Metodo que enlaza la accion de editar una emision con referencia 
	 * al custodio actual
	 * @param event
	 */
	public void editarEmision(ActionEvent event){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.nuevaEmision");
		
		emisionesBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		emisionesBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		emisionesBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		
		emisionesBean.getEmisionActual();
	}
	
	/**
	 * Metodo que enlaza la accion de buscar depositantes con referencia 
	 * al custodio actual
	 * @param event
	 */
	public void buscarDepositantes(ActionEvent event){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.buscarDepositantes");
		consultaDepositantesBean.setClaveParticipante(
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
					+
				custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getFolioInstitucion());
		consultaDepositantesBean.setNombreCortoParticipante(custodioActual.getCatBic().getCuentaNombrada().getInstitucion().getRazonSocial());
		consultaDepositantesBean.setCuentaParticipante(custodioActual.getCatBic().getCuentaNombrada().getCuenta());
		
		consultaDepositantesBean.setDepositante(new SicDetalle());		
		consultaDepositantesBean.setCustodioActual(custodioActual);
		consultaDepositantesBean.setConsultaDepLiqEjecutada(Boolean.FALSE);
		PaginaVO pagina = new PaginaVO();
		pagina.setOffset(0);
		pagina.setRegistrosXPag(50);
		consultaDepositantesBean.setPaginaVO(pagina);
	}
	public String guardarEmision() {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.guardarEmision");
		EmisionVO emisionVO = emisionesBean.getEmisionVO();
		log.info("Emision a guardar: [" + emisionVO.getTv() + "-" + 
				emisionVO.getEmisora() + "-" + emisionVO.getSerie() + "]");
		String formaDeOperar = emisionesBean.getFormaOperar();
	       log.info("Forma de Operar: [" + formaDeOperar + "]");
        try {
            divisionInternacionalService.altaEmision(emisionVO, custodioActual.getCatBic(), formaDeOperar);
        } catch (BusinessException be) {
            log.error("Error al guardar la emision", be);
            agregarMensaje(be);
            return null; //Para que saque la misma pantalla de registro
        } catch (Exception e) {
            log.error("Error al guardar la emision", e);
            agregarMensaje(new BusinessException(e.getMessage()));
            return null;
        } catch (Throwable ex) {
            log.error("Error al guardar la emision", ex);
            agregarMensaje(ex);
            return null; //Para que saque la misma pantalla de registro
        }
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	/**
	 * Metodo que edita la emisionactual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cambiarEmision(){
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.cambiarEmision");		
		try {			
			EmisionVO emision = new EmisionVO();
			emision.setTv(emisionesBean.getEmisionActual().getEmision().getInstrumento().getClaveTipoValor());
			emision.setEmisora(emisionesBean.getEmisionActual().getEmision().getEmisora().getClavePizarra());
			emision.setSerie(emisionesBean.getEmisionActual().getEmision().getSerie());			
			divisionInternacionalService.updateSicEmision(emision, emisionesBean.getEmisionActual().getCatBic(), emisionesBean.getEmisionActual(), emisionesBean.getEmisionActual().getFormaOper());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de registro
		} catch (Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de registro
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	/**
	 * Metodo que cancela  la emisionactual
	 * @return la pantalla de Registro o la pantalla de registro
	 */
	public String cancelarEmision(){
		try {
			divisionInternacionalService.cancelaSicEmision(emisionesBean.getEmisionActual());
		} catch(BusinessException be) {
		    log.error("Ocurrio un error:",be);
		    agregarMensaje(be);
		    return null; //Para que saque la misma pantalla de consulta
		} catch (Exception e) {
                    log.error("Ocurrio un error:",e);
                    agregarMensaje(new BusinessException(e.getMessage()));
                    return null;
		} catch(Throwable ex) {
		    log.error("Ocurrio un error:",ex);
		    agregarMensaje(ex);
		    return null; //Para que saque la misma pantalla de consulta
		}
		
		buscarCustodios(null);
		
		return PANTALLA_CONSULTA;
	}
	public void cancelar(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.cancelar");
        this.listadoElementos = new ArrayList<SelectItem>();
        this.strElementoAgregar = "";
        this.idElementoSeleccionado = new Long(-1);
        this.listadoElementosFormato = new ArrayList<SelectItem>();
        this.strElementoPorcentajeAgregar = "";
        this.idElementoFormatoSeleccionado = new Long(-1);
        this.idSelectFormatoSeleccionado = new Long(-1);
        this.modoConsultaCustodio = false;
		buscarCustodios(event);
	}
	
	public void limpiar(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.limpiar");
		limpiaDatosParticipante();
		paginaVO = new PaginaVO();
		consultaEjecutada = false;
		custodioActual = new ConsultaCustodiosVO();
		depositantesLiqBean.limpiar();
		emisionesBean.limpiar();
	}
	
	public void limpiarForma(ActionEvent event) {
		claveParticipanteAlta = null;
		cuentaParticipanteAlta = null;
		nombreCortoParticipanteAlta = null;
		custodioAlta = new AltaCustodioVO();
        this.strElementoAgregar = "";
        this.idElementoSeleccionado = new Long(-1);
        this.strElementoPorcentajeAgregar = "";
        this.idElementoFormatoSeleccionado = new Long(-1);
        this.idSelectFormatoSeleccionado = new Long(-1);
		if (!this.modoConsultaCustodio) {
		    this.listadoElementos = new ArrayList<SelectItem>();
            this.listadoElementosFormato = new ArrayList<SelectItem>();
		}
	}
    
    /**
     * Metodo que agrega un Tipo Valor para la relacion TipoValor-Custodio.
     */
    public void agregarTipoValorAction(ActionEvent event) {
        log.info("\n\n####### Elemento a agregar=[" + this.strElementoAgregar + "]");

        if (!StringUtils.isBlank(this.strElementoAgregar)) {
            this.strElementoAgregar = this.strElementoAgregar.toUpperCase();
            if (this.listadoElementos != null && this.listadoElementos.size() > 0) {
                boolean duplicado = false;
                for (SelectItem selectItem : this.listadoElementos) {
                    if (this.strElementoAgregar.trim().equals(selectItem.getLabel())) {
                        duplicado = true;
                        break;
                    }
                }
                if (!duplicado) {
                    this.validarYAlmacenarTipoValor(this.strElementoAgregar.trim());
                }
                else {
                    agregarMensaje(new BusinessException("El Tipo Valor se duplic\u00F3"));
                }
            }
            else {
                this.listadoElementos = new ArrayList<SelectItem>();
                this.validarYAlmacenarTipoValor(this.strElementoAgregar.trim());
            }
        }

        this.strElementoAgregar = new String();
    }

    /**
     * Valida la existencia del tipo valor en la BD y almacena el tipo valor en el listado de elementos correspondiente.
     * @param tv El tipo valor a validar y almacenar.
     */
    private void validarYAlmacenarTipoValor(String tv) {
        try {
            boolean existeTipoValor = this.divisionInternacionalService.validarExistenciaTipoValor(tv);
            if (existeTipoValor) {
                this.listadoElementos.add(new SelectItem(Calendar.getInstance().getTime().getTime(), tv));
            }
            else {
                agregarMensaje(new BusinessException("El Tipo Valor no existe"));
            }
        } catch(BusinessException be) {
            log.error("Ocurrio un error:",be);
            agregarMensaje(be);
        } catch(Exception e) {
            log.error("Ocurrio un error:",e);
            agregarMensaje(new BusinessException(e.getMessage()));
        } catch(Throwable ex) {
            log.error("Ocurrio un error:",ex);
            agregarMensaje(ex);
        }
    }

    /**
     * Metodo que elimina un Tipo Valor para la relacion TipoValor-Custodio.
     */
    public void eliminarTipoValorAction(ActionEvent event) {
        log.info("\n\n####### Elemento a eliminar=[" + this.idElementoSeleccionado + "]");
        if (this.idElementoSeleccionado != null && this.idElementoSeleccionado.longValue() > 0) {
            if (this.listadoElementos != null && this.listadoElementos.size() > 0) {
                for (SelectItem selectItem : this.listadoElementos) {
                    Long valueId = (Long) selectItem.getValue();
                    if (valueId.compareTo(this.idElementoSeleccionado) == 0) {
                        this.listadoElementos.remove(selectItem);
                        break;
                    }
                }
            } 
        }
    }
    
    /**
     * Metodo que agrega un Formato para la relacion Formato-Custodio.
     */
    public void agregarFormatoAction(ActionEvent event) {
        log.info("\n\n####### Elemento a agregar=[" + this.idSelectFormatoSeleccionado + "]");
        log.info("\n\n####### % a agregar=[" + this.strElementoPorcentajeAgregar + "]");

        if (!StringUtils.isBlank(this.strElementoPorcentajeAgregar)) {
                TipoBeneficiario tipoBenef = this.divisionInternacionalService.getTipoBeneficiarioById(this.idSelectFormatoSeleccionado);
                String elFormato = tipoBenef != null ? tipoBenef.getFormato() : "";
                String porcentajeReal = (Float.valueOf(this.strElementoPorcentajeAgregar)).toString();
                if (!StringUtils.isBlank(elFormato)) {
                    elFormato = elFormato + "-" + porcentajeReal;
                }
                if (this.listadoElementosFormato != null && this.listadoElementosFormato.size() > 0) {
                    boolean duplicado = false;
                    for (SelectItem selectItem : this.listadoElementosFormato) {
                        if (this.idSelectFormatoSeleccionado.equals((Long)selectItem.getValue()) && 
                            elFormato.equals(selectItem.getLabel())) {
                          duplicado = true;
                          break;
                        }
                    }
                    if (!duplicado) {
                        this.listadoElementosFormato.add(new SelectItem(this.idSelectFormatoSeleccionado, elFormato));
                    }
                    else {
                        agregarMensaje(new BusinessException("El Formato se duplic\u00F3"));
                    }
                }
                else {
                    this.listadoElementosFormato = new ArrayList<SelectItem>();
                    this.listadoElementosFormato.add(new SelectItem(this.idSelectFormatoSeleccionado, elFormato));
                }
                tipoBenef = null;
        }

        this.strElementoPorcentajeAgregar = "";
    }

    /**
     * Metodo que elimina un Formato para la relacion Formato-Custodio.
     */
    public void eliminarFormatoAction(ActionEvent event) {
        log.info("\n\n####### Elemento a eliminar=[" + this.idElementoFormatoSeleccionado + "]");
        if (this.idElementoFormatoSeleccionado != null && this.idElementoFormatoSeleccionado.longValue() > 0) {
            if (this.listadoElementosFormato != null && this.listadoElementosFormato.size() > 0) {
                for (SelectItem selectItem : this.listadoElementosFormato) {
                    Long valueId = (Long) selectItem.getValue();
                    if (valueId.compareTo(this.idElementoFormatoSeleccionado) == 0) {
                        this.listadoElementosFormato.remove(selectItem);
                        break;
                    }
                }
            } 
        }

        this.strElementoPorcentajeAgregar = "";
    }
 	
	private AgenteVO construyeAgente() {
		AgenteVO retorno =  new AgenteVO();
		if( isIdFolioValido() ) {
			retorno.setId(claveParticipante.substring(0,2));
			retorno.setFolio(claveParticipante.substring(2));
		}
		if( isCuentaValida() ) {
			retorno.setCuenta(cuentaParticipante);
		}
		return retorno;
	}
 	
	private AgenteVO construyeAgenteAlta() {
		AgenteVO retorno =  new AgenteVO();
		if( isIdFolioValidoAlta() ) {
			retorno.setId(claveParticipanteAlta.substring(0,2));
			retorno.setFolio(claveParticipanteAlta.substring(2));
		}
		if( isCuentaValidaAlta() ) {
			retorno.setCuenta(cuentaParticipanteAlta);
		}
		return retorno;
	}
	
	private boolean isIdFolioValidoAlta() {
		if( StringUtils.isNotBlank(claveParticipanteAlta) &&
				claveParticipanteAlta.length() == 5 &&
				StringUtils.isNumeric(claveParticipanteAlta) ) {
			return true;
		}
		return false;
	}
	
	private boolean isCuentaValidaAlta() {
		if( StringUtils.isNotBlank(cuentaParticipanteAlta) &&
				cuentaParticipanteAlta.length() == 4 &&
				StringUtils.isNumeric(cuentaParticipanteAlta) ) {
			return true; 
		}
		return false;
	}
	
	private boolean isIdFolioValido() {
		if( StringUtils.isNotBlank(claveParticipante) &&
				claveParticipante.length() == 5 &&
				StringUtils.isNumeric(claveParticipante) ) {
			return true;
		}
		return false;
	}
	
	private boolean isCuentaValida() {
		if( StringUtils.isNotBlank(cuentaParticipante) &&
				cuentaParticipante.length() == 4 &&
				StringUtils.isNumeric(cuentaParticipante) ) {
			return true; 
		}
		return false;
	}
	
	private void limpiaDatosParticipante() {
		claveParticipante = null;
		cuentaParticipante = null;
		nombreCortoParticipante = null;
	}
	
	/**
	 * Ejecuta las actividades necesarias de inicializaci&oacute;n de la pantalla.
	 * 
	 * @return <code>null</code>. No es necesario un valor dev retorno.
	 */
	public String getInit() {
		log.info("Entrando al constructor AdmonCustodiosDepositantesConstroller");
		limpiaDatosParticipante();
		return null;
	}

	/**
	 * @param claveParticipante the claveParticipante to set
	 */
	public void setClaveParticipante(String claveParticipante) {
		this.claveParticipante = claveParticipante;
	}

	/**
	 * @return the claveParticipante
	 */
	public String getClaveParticipante() {
		return claveParticipante;
	}

	/**
	 * @param cuentaParticipante the cuentaParticipante to set
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * @return the cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * @param nombreCortoParticipante the nombreCortoParticipante to set
	 */
	public void setNombreCortoParticipante(String nombreCortoParticipante) {
		this.nombreCortoParticipante = nombreCortoParticipante;
	}

	/**
	 * @return the nombreCortoParticipante
	 */
	public String getNombreCortoParticipante() {
		return nombreCortoParticipante;
	}

	/**
	 * Establece el valor del atributo divisionInternacionalService
	 *
	 * @param divisionInternacionalService el valor del atributo divisionInternacionalService a establecer
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}
	
	/**
	 * @param consultaCatalogoService the consultaCatalogoService to set
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}
	
	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param custodioActual the custodioActual to set
	 */
	public void setCustodioActual(ConsultaCustodiosVO custodioActual) {
		this.custodioActual = custodioActual;
	}

	/**
	 * @return the custodioActual
	 */
	public ConsultaCustodiosVO getCustodioActual() {
		return custodioActual;
	}

	/**
	 * @param depositantesLiqBean the depositantesLiqBean to set
	 */
	public void setDepositantesLiqBean(AdmonCustodiosDepositantesDepLiqConstroller depositantesLiqBean) {
		this.depositantesLiqBean = depositantesLiqBean;
	}

	/**
	 * @return the depositantesLiqBean
	 */
	public AdmonCustodiosDepositantesDepLiqConstroller getDepositantesLiqBean() {
		return depositantesLiqBean;
	}

	/**
	 * @param emisionesBean the emisionesBean to set
	 */
	public void setEmisionesBean(AdmonCustodiosDepositantesEmiConstroller emisionesBean) {
		this.emisionesBean = emisionesBean;
	}

	/**
	 * @return the emisionesBean
	 */
	public AdmonCustodiosDepositantesEmiConstroller getEmisionesBean() {
		return emisionesBean;
	}

	/**
	 * @return the custodioAlta
	 */
	public AltaCustodioVO getCustodioAlta() {
		return custodioAlta;
	}

	/**
	 * @param custodioAlta the custodioAlta to set
	 */
	public void setCustodioAlta(AltaCustodioVO custodioAlta) {
		this.custodioAlta = custodioAlta;
	}

	/**
	 * @return the claveParticipanteAlta
	 */
	public String getClaveParticipanteAlta() {
		return claveParticipanteAlta;
	}

	/**
	 * @param claveParticipanteAlta the claveParticipanteAlta to set
	 */
	public void setClaveParticipanteAlta(String claveParticipanteAlta) {
		this.claveParticipanteAlta = claveParticipanteAlta;
	}

	/**
	 * @return the cuentaParticipanteAlta
	 */
	public String getCuentaParticipanteAlta() {
		return cuentaParticipanteAlta;
	}

	/**
	 * @param cuentaParticipanteAlta the cuentaParticipanteAlta to set
	 */
	public void setCuentaParticipanteAlta(String cuentaParticipanteAlta) {
		this.cuentaParticipanteAlta = cuentaParticipanteAlta;
	}

	/**
	 * @return the nombreCortoParticipanteAlta
	 */
	public String getNombreCortoParticipanteAlta() {
		return nombreCortoParticipanteAlta;
	}

	/**
	 * @param nombreCortoParticipanteAlta the nombreCortoParticipanteAlta to set
	 */
	public void setNombreCortoParticipanteAlta(String nombreCortoParticipanteAlta) {
		this.nombreCortoParticipanteAlta = nombreCortoParticipanteAlta;
	}

	/**
	 * Obtiene el valor del atributo consultaEmisionesBean
	 *
	 * @return el valor del atributo consultaEmisionesBean
	 */
	public AdmonCustodiosDepositantesConsultaEmiController getConsultaEmisionesBean() {
		return consultaEmisionesBean;
	}

	/**
	 * Establece el valor del atributo consultaEmisionesBean
	 *
	 * @param consultaEmisionesBean el valor del atributo consultaEmisionesBean a establecer
	 */
	public void setConsultaEmisionesBean(
			AdmonCustodiosDepositantesConsultaEmiController consultaEmisionesBean) {
		this.consultaEmisionesBean = consultaEmisionesBean;
	}

	/**
	 * Obtiene el valor del atributo consultaDepositantesBean
	 *
	 * @return el valor del atributo consultaDepositantesBean
	 */
	public AdmonCustodiosDepositantesConsultaDepLiqController getConsultaDepositantesBean() {
		return consultaDepositantesBean;
	}

	/**
	 * Establece el valor del atributo consultaDepositantesBean
	 *
	 * @param consultaDepositantesBean el valor del atributo consultaDepositantesBean a establecer
	 */
	public void setConsultaDepositantesBean(
			AdmonCustodiosDepositantesConsultaDepLiqController consultaDepositantesBean) {
		this.consultaDepositantesBean = consultaDepositantesBean;
	}



	/**
	 * Obtiene el valor del atributo idEmisioeditar
	 *
	 * @return el valor del atributo idEmisioeditar
	 */
	public String getIdEmisioeditar() {
		return idEmisioeditar;
	}

	/**
	 * Establece el valor del atributo idEmisioeditar
	 *
	 * @param idEmisioeditar el valor del atributo idEmisioeditar a establecer
	 */
	public void setIdEmisioeditar(String idEmisioeditar) {
		this.idEmisioeditar = idEmisioeditar;
	}

    public String getStrElementoAgregar() {
        return strElementoAgregar;
    }

    public void setStrElementoAgregar(String strElementoAgregar) {
        this.strElementoAgregar = strElementoAgregar;
    }

    public Long getIdElementoSeleccionado() {
        return idElementoSeleccionado;
    }

    public void setIdElementoSeleccionado(Long idElementoSeleccionado) {
        this.idElementoSeleccionado = idElementoSeleccionado;
    }

    public List<SelectItem> getListadoElementos() {
        return listadoElementos;
    }

    public void setListadoElementos(List<SelectItem> listadoElementos) {
        this.listadoElementos = listadoElementos;
    }

    public String getStrElementoPorcentajeAgregar() {
        return strElementoPorcentajeAgregar;
    }

    public void setStrElementoPorcentajeAgregar(String strElementoPorcentajeAgregar) {
        this.strElementoPorcentajeAgregar = strElementoPorcentajeAgregar;
    }

    public Long getIdElementoFormatoSeleccionado() {
        return idElementoFormatoSeleccionado;
    }

    public void setIdElementoFormatoSeleccionado(Long idElementoFormatoSeleccionado) {
        this.idElementoFormatoSeleccionado = idElementoFormatoSeleccionado;
    }

    public List<SelectItem> getListadoElementosFormato() {
        return listadoElementosFormato;
    }

    public void setListadoElementosFormato(List<SelectItem> listadoElementosFormato) {
        this.listadoElementosFormato = listadoElementosFormato;
    }

    public Long getIdSelectFormatoSeleccionado() {
        return idSelectFormatoSeleccionado;
    }

    public void setIdSelectFormatoSeleccionado(Long idSelectFormatoSeleccionado) {
        this.idSelectFormatoSeleccionado = idSelectFormatoSeleccionado;
    }

}
