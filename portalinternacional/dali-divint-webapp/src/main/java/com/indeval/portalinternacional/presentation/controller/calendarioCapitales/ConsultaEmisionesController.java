package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioDerechosCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EmisionAssetManagerVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO;
import com.indeval.portalinternacional.presentation.controller.common.CalendarioCapitalesBase;


/** * Controlador para la pantalla de Consulta de Emisiones (consultaEmsiones.xhtml)
 * 
 * @author Omar Gtz
 * 
 */
public class ConsultaEmisionesController extends CalendarioCapitalesBase {

	private static final Logger log = LoggerFactory.getLogger(ConsultaEmisionesController.class);

 

    private boolean consultaEjecutada;

    private int totalPaginas = 1;

    private boolean banderaBitacoraConsulta = false;

    private int totalRegistros = 0;
    
    private int totalRegistrosReportes = 0;
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    
    private String idsDestinatarios;
    
    Boveda boveda;
    
    List<CalendarioDerechosCapitales> registros;
    
    private Map<String, String> tipoValorPermitidos;
    
    private boolean admin = false;
    
    /** Parametros enviados por el Request */
   	private Map<String, String> params = new HashMap<String, String>();
   	
   	private Long idEmision = null;
   	
   	private Long idCatbic = null;
   	
   	private String isin = null;
   	
   	private String descripcionAsset = null;
   	
   	private EmisionAssetManagerVO vEmisionesCalendario = null;
   	
    /** Patron cadena */
    Pattern pattern = Pattern.compile("([a-z]|[A-Z]|\\s)+");
   	

    public ConsultaEmisionesController() {

    }

    /**
     * Inicializa el bean
     * 
     * @return
     */
    public String getInit() {
        if (this.paginaVO == null) {
            this.paginaVO = new PaginaVO();
        }
        limpiarFiltro();
        admin = true;
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        banderaBitacoraConsulta = false;
        try{
        if(tipoValorPermitidos == null){
        	tipoValorPermitidos = capitalesDistribucionService.getTipoValorLegislacionSicMap();
        }
        }catch(Exception e){
        	log.error("Ocurrio un error:",e);
    		this.addErrorMessage("Error al Iniciar la pantalla.");
        }
        return null;
    }
    
    /**
     *Inicializa la ventana emergente del Asset Manager de la emision
     * 
     */
    public String getInitPopUp() {
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsulta();
		return null;
	}
       
    /**
     * Buscar las emisiones
     * 
     * @param evt
     */
    public void buscarDerechos(final ActionEvent evt) {     
    	this.getPaginaVO().setOffset(0);
    	this.getPaginaVO().setRegistrosXPag(50);
    	this.getPaginaVO().setRegistros(null);
        try{
        this.ejecutarConsulta();
        }catch (BusinessException be) {
			log.error("Error al consultar:", be);
			addErrorMessage("Ocurri\u00F3 un error al consultar.");
		}

    }
    
    /**
     * Buscar las emisiones al guardar el asset manager
     * 
     * @param evt
     */
    public void buscarEmisiones(final ActionEvent evt) {     
        this.getPaginaVO().setRegistros(null);        
        try{
        this.ejecutarConsulta();
        }catch (BusinessException be) {
			log.error("Error al consultar:", be);
			addErrorMessage("Ocurri\u00F3 un error al consultar.");
		}

    }

    /**
     * Genera los reportes de Consulta de Emisiones
     * 
     * @param evt
     */
	public void generarReportes(ActionEvent evt) {
		try {
			this.reiniciarEstadoPeticion();
			paginaReportes = new PaginaVO();
			paginaReportes.setOffset(0);
			paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
			paginaReportes = capitalesDistribucionService.consultaEmisionesCalendarioCapitales(calendarioEmisiones,
					paginaReportes);
			this.totalRegistrosReportes = paginaReportes.getTotalRegistros();
		} catch (Exception e) {
			log.error("Error al generar reporte:", e);
			addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
		}

	}
	
    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion. Este metodo es un
     * overide de la clasmerge y e padre
     */
	
    @Override
    public String ejecutarConsulta() {
		limpiaIdentificadores();
		try {
			if (params.get("folioEmision") != null && params.get("isin") != null) {
				idEmision = Long.valueOf(params.get("folioEmision"));				
				isin = params.get("isin");
				idCatbic = StringUtils.isNotBlank(params.get("idCatbic")) ? Long.valueOf(params.get("idCatbic")):null;
				calendarioEmisiones.setIdEmision(idEmision);
				vEmisionesCalendario = capitalesDistribucionService.consultaEmisionAssetManagerForIdEmision(idEmision,idCatbic);								
				if (vEmisionesCalendario != null) {
					descripcionAsset = vEmisionesCalendario.getAssetManagerDesc();
				}

			} else {
				paginaVO = capitalesDistribucionService.consultaEmisionesCalendarioCapitales(calendarioEmisiones,
						paginaVO);
				log.info(("Numero de Registros" + paginaVO.getRegistros().size()));

				this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

				if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
					this.totalPaginas++;
				}
				this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;
				totalRegistros = paginaVO.getTotalRegistros();

			}
		} 

		catch (Exception e) {
			log.error("Error al consultar:", e);
			addErrorMessage("Ocurri\u00F3 un error al realizar la consulta.");
		}
		this.setConsultaEjecutada(true);
		return null;
	}


    
    /**
     * Guarda el comentario capturado por el usuario para el asset de la emision
     *
     */
    public void guardarAsset(ActionEvent event){
    	try{
    		if(this.vEmisionesCalendario != null){
    			this.vEmisionesCalendario.setAssetManagerDesc(this.descripcionAsset);
    			this.capitalesDistribucionService.guardaAssetManager(this.vEmisionesCalendario);
    			agregarInfoMensaje("Guardado exitoso: "+this.descripcionAsset);
    	    	vEmisionesCalendario = capitalesDistribucionService.consultaEmisionAssetManagerForIdEmision(idEmision,idCatbic);								
    			if (vEmisionesCalendario != null) {
    				descripcionAsset = vEmisionesCalendario.getAssetManagerDesc();
    			}
    		}	
	    	
    	}catch (Exception e) {
			log.error("Error al guardar emision:", e);
			addErrorMessage("Ocurri\u00F3 un error al guardar la emisi\u00F3n.");
    	}    	
    }
    
    /**
     * Elimina el comentario capturado por el usuario para el asset de la emision
     *
     */
	public void eliminarAsset(ActionEvent evt) {
		try {			
			if (this.vEmisionesCalendario != null && this.vEmisionesCalendario.getIdAssetManager()!=null) {			
				this.capitalesDistribucionService.eliminaAssetManager(this.vEmisionesCalendario);
				agregarInfoMensaje("Borrado exitoso");
			}
			this.descripcionAsset = null;
		} catch (Exception e) {
			log.error("Error al borrar emision:", e);
			addErrorMessage("Ocurri\u00F3 un error al borrar la emisi\u00F3n.");
		}

	}

    /**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

    /**
     * Limpia todos los campos
     * 
     * @param evt
     */
    public void limpiar(ActionEvent evt) {
        this.banderaBitacoraConsulta = false;        
        limpiarFiltro();
        if (this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);

        this.setConsultaEjecutada(false);
    }
    
    private void limpiarFiltro(){
    	limpiaIdentificadores();
    	this.calendarioEmisiones.setTipoValor(null);
    	this.calendarioEmisiones.setEmisora(null);
    	this.calendarioEmisiones.setSerie(null);
    	this.calendarioEmisiones.setIsin(null);
    	this.calendarioEmisiones.setRazonSocial(null);
    	this.calendarioEmisiones.setDetalleCustodio("-1");
    	this.calendarioEmisiones.setFechaAltaInicio(null);
    	this.calendarioEmisiones.setFechaAltaFin(null);
    	this.calendarioEmisiones.setFechaBajaInicio(null);
    	this.calendarioEmisiones.setFechaBajaFin(null);
    	this.calendarioEmisiones.setFechaModificacionInicio(null);
    	this.calendarioEmisiones.setFechaModificacionFin(null);
    	this.calendarioEmisiones.setPaisOrigen("-1");
    	this.calendarioEmisiones.setEstatusEmision("-1");
    	this.calendarioEmisiones.setListada(null);
    	this.calendarioEmisiones.setAssetManager("-1");
    	this.calendarioEmisiones.setEstatusSistema("-1");
    	
    }
    
    /**
     * Limpia el valor asignado por los combos
     */
    private void limpiaIdentificadores(){
    	this.calendarioEmisiones.setCustodioInt(null);
    }
     
    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(final boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

  

    /**
     * @return the totalPaginas
     */
    @Override
    public int getTotalPaginas() {
        return this.totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    @Override
    public void setTotalPaginas(final int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * @return the totalRegistros
     */
    public int getTotalRegistros() {
        return this.totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(final int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isBanderaBitacoraConsulta() {
        return this.banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(final boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
    }

    /**
     * @return the calendario
     */
    public ConsultaEmisionesCalendarioParamsTO getCalendarioEmisiones() {
        return this.calendarioEmisiones;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendarioEmisiones(final ConsultaEmisionesCalendarioParamsTO calendarioEmisiones) {
        this.calendarioEmisiones = calendarioEmisiones;
    }

    /**
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    /**
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }
    
    /**
	 * @return the idsDestinatarios
	 */
	public String getIdsDestinatarios() {
		return idsDestinatarios;
	}

	/**
	 * @param idsDestinatarios the idsDestinatarios to set
	 */
	public void setIdsDestinatarios(String idsDestinatarios) {
		this.idsDestinatarios = idsDestinatarios;
	}
		
	/**
	 * @return the tipoValorPermitidos
	 */
	public Map<String, String> getTipoValorPermitidos() {
		return tipoValorPermitidos;
	}

	/**
	 * @param tipoValorPermitidos the tipoValorPermitidos to set
	 */
	public void setTipoValorPermitidos(Map<String, String> tipoValorPermitidos) {
		this.tipoValorPermitidos = tipoValorPermitidos;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the totalRegistrosReportes
	 */
	public int getTotalRegistrosReportes() {
		return totalRegistrosReportes;
	}

	/**
	 * @param totalRegistrosReportes the totalRegistrosReportes to set
	 */
	public void setTotalRegistrosReportes(int totalRegistrosReportes) {
		this.totalRegistrosReportes = totalRegistrosReportes;
	}


	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the descripcionAsset
	 */
	public String getDescripcionAsset() {
		return descripcionAsset;
	}

	/**
	 * @param descripcionAsset the descripcionAsset to set
	 */
	public void setDescripcionAsset(String descripcionAsset) {
		this.descripcionAsset = descripcionAsset;
	}

	/**
	 * @return the vEmisionesCalendario
	 */
	public EmisionAssetManagerVO getvEmisionesCalendario() {
		return vEmisionesCalendario;
	}

	/**
	 * @param vEmisionesCalendario the vEmisionesCalendario to set
	 */
	public void setvEmisionesCalendario(EmisionAssetManagerVO vEmisionesCalendario) {
		this.vEmisionesCalendario = vEmisionesCalendario;
	}
}
