package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.HashMap;
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
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO;
import com.indeval.portalinternacional.presentation.controller.common.CalendarioCapitalesBase;


/** * Controlador para la pantalla de Envio Calendario Capitales (envioCalendarioCapitales.xhtml)
 * 
 * @author Omar Gtz
 * 
 */
public class EnvioMensajesCapitalesController extends CalendarioCapitalesBase {

	private static final Logger log = LoggerFactory.getLogger(EnvioMensajesCapitalesController.class);

    private boolean consultaEjecutada;

    private int totalPaginas = 1;

    private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;

    private int totalRegistros = 0;
    
    /** Patron numerico */
    private Pattern pattern = Pattern.compile("-*[0-9]+");
    
    /** Parametros enviados por el Request */
 	private Map<String, String> params = new HashMap<String, String>();
    
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    private int totalRegistrosReportes = 0;
    private String idsDestinatarios;
  
    public EnvioMensajesCapitalesController() {

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
        cargaMapaImagenesEstadosEnvio();
        cargaMapaImagenesEnvioMensajes();
        limpiarFiltro();
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        banderaBitacoraConsulta = false;
        return null;
    }

    /**
     * Inicializa la pantalla emergente de envios que es invocada desde Calendario e Historico
     * 
     * 
     * @return
     */
    public String getInitPop() {
       try{
       inicializaPagina();
       cargaMapaImagenesEstadosEnvio();
       cargaMapaImagenesEnvioMensajes();
       Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
       
       for (String key : keys) {
           params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
       }
       FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
       ejecutarConsulta();
       } catch(BusinessException be){
    		log.error("Ocurrio un error:",be);
    		this.addErrorMessage("Error al Iniciar la pantalla.");
       }
        return null;
    }
       
    /**
     * Buscar las emisiones
     * 
     * @param evt
     */
    public void buscarDerechos(final ActionEvent evt) {
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);
        this.getPaginaVO().setRegistros(null);
        this.ejecutarConsulta();

    }

    /**
     * Genera los reportes de Envios Calendario Capitales
     * 
     * @param evt
     */
	public void generarReportes(ActionEvent evt) {
		try {
			reiniciarEstadoPeticion();
			paginaReportes = new PaginaVO();
			paginaReportes.setOffset(0);
			paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
			paginaReportes = capitalesDistribucionService.consultaEnviadosCalendarioCapitales(calendarioEnvio,
					paginaReportes);
			this.totalRegistrosReportes = paginaReportes.getTotalRegistros();
		} catch (Exception e) {
			log.error("Error al generar reporte:", e);
			addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
		}

	}

    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion. Este metodo es un
     * overide de la clasmerge y el padre
     */
    @Override
	public String ejecutarConsulta() {
		try{
		this.limpiaIdentificadores();
    	this.setParams();
		if (params.get("folio") != null && params.get("bandera") != null) {
			Long id = Long.valueOf(params.get("folio"));
			boolean bandera = Boolean.valueOf(params.get("bandera"));
			paginaVO = capitalesDistribucionService.consultaEnviosCalendario(bandera, id, paginaVO);
		} else {
			paginaVO = capitalesDistribucionService.consultaEnviadosCalendarioCapitales(calendarioEnvio, paginaVO);
		}
		log.info(("Numero de Registros" + paginaVO.getRegistros().size()));		
		this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

		if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
			this.totalPaginas++;
		}
		this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;
		totalRegistros = paginaVO.getTotalRegistros();
		}catch(BusinessException be){
			log.error("Error al consultar:",be);
    		addErrorMessage(be.getMessage());	
		} catch(Exception e){
			log.error("Error al consultar:",e);	
        	addErrorMessage("Ocurri\u00F3 un error al realizar la consulta.");	
		}

		this.setConsultaEjecutada(true);
		return null;
	}

    private void setParams() throws BusinessException{
     
    	 if (StringUtils.isNotBlank(this.calendarioEnvio.getACKAMH()) && this.pattern.matcher(this.calendarioEnvio.getACKAMH()).matches()) {
             this.calendarioEnvio.setACKAMHInt(Integer.valueOf(this.calendarioEnvio.getACKAMH()));
         }
    	 if (StringUtils.isNotBlank(this.calendarioEnvio.getACKBolsa()) && this.pattern.matcher(this.calendarioEnvio.getACKBolsa()).matches()) {
    		 this.calendarioEnvio.setACKBolsaInt(Integer.valueOf(this.calendarioEnvio.getACKBolsa()));
    	 }
    	 validaNumericos();
    }
    
	private void validaNumericos() throws BusinessException {
		if (StringUtils.isNotBlank(this.calendarioEnvio.getFolioIndevalCadena())) {
			if (this.pattern.matcher(this.calendarioEnvio.getFolioIndevalCadena()).matches()) {
				this.calendarioEnvio.setFolioIndeval(Long.valueOf(this.calendarioEnvio.getFolioIndevalCadena()));
			} else {
				this.calendarioEnvio.setFolioIndevalCadena(null);
				throw new BusinessException("Folio Indeval Inv\u00E1lido");
			}
		}
		if (StringUtils.isNotBlank(this.calendarioEnvio.getFolioMensajeCadena())) {
			if (this.pattern.matcher(this.calendarioEnvio.getFolioMensajeCadena()).matches()) {
				this.calendarioEnvio.setFolioMensajeIndeval(Long.valueOf(this.calendarioEnvio.getFolioMensajeCadena()));
			} else {
				this.calendarioEnvio.setFolioMensajeCadena(null);
				throw new BusinessException("Folio Mensaje Inv\u00E1lido");
			}
		}
		if (StringUtils.isNotBlank(this.calendarioEnvio.getConsecutivoCadena())) {
			if (this.pattern.matcher(this.calendarioEnvio.getConsecutivoCadena()).matches()) {
				this.calendarioEnvio.setConsecutivo(Long.valueOf(this.calendarioEnvio.getConsecutivoCadena()));
			} else {
				this.calendarioEnvio.setConsecutivoCadena(null);
				throw new BusinessException("Consecutivo Inv\u00E1lido");
			}
		}
		if (StringUtils.isNotBlank(this.calendarioEnvio.getIdEnviosCadena())) {
			if (this.pattern.matcher(this.calendarioEnvio.getIdEnviosCadena()).matches()) {
				this.calendarioEnvio.setIdEnvios(Long.valueOf(this.calendarioEnvio.getIdEnviosCadena()));
			} else {
				this.calendarioEnvio.setIdEnviosCadena(null);
				throw new BusinessException("Id Env\u00cdo Inv\u00E1lido");
			}
		}
	}
  

    /**
     * Limpia todos los campos
     * 
     * @param evt
     */
    public void limpiar(ActionEvent evt) {
        this.banderaBitacoraConsulta = false;        
        limpiarFiltro();
        if (this.resultados != null) {
            this.resultados.getRegistros().clear();
        }
        if (this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);

        this.setConsultaEjecutada(false);
    }
    
    private void limpiarFiltro(){
     	limpiaIdentificadores();                        
        this.calendarioEnvio.setFechaEnvioInicio(null);
        this.calendarioEnvio.setFechaEnvioFin(null);
        this.calendarioEnvio.setFechaModificacionInicio(null);
        this.calendarioEnvio.setFechaModificacionFin(null);
        this.calendarioEnvio.setDestinatario("-1");
        this.calendarioEnvio.setACKAMH("-1");
        this.calendarioEnvio.setACKBolsa("-1");
        this.calendarioEnvio.setUsuario(null);        
        this.calendarioEnvio.setTipoValor(null);
        this.calendarioEnvio.setEmisora(null);
        this.calendarioEnvio.setSerie(null);
        this.calendarioEnvio.setIsin(null);
        this.calendarioEnvio.setFolioIndevalCadena(null);
        this.calendarioEnvio.setFolioMensajeCadena(null);
        this.calendarioEnvio.setConsecutivoCadena(null);
        this.calendarioEnvio.setIdEnviosCadena(null);
    }
    
    /**
     * Limpia el valor asignado por los combos
     */
    private void limpiaIdentificadores(){       	
    	this.calendarioEnvio.setFolioIndeval(null);
        this.calendarioEnvio.setFolioMensajeIndeval(null);
        this.calendarioEnvio.setConsecutivo(null);
        this.calendarioEnvio.setIdEnvios(null);
        this.calendarioEnvio.setACKAMHInt(null);
        this.calendarioEnvio.setACKBolsaInt(null);
    }

    
    /**
     * Inicializa los valores para la paginacion
     * 
     * 
     */
	 public void inicializaPagina(){
		 if (this.paginaVO == null) {
	            this.paginaVO = new PaginaVO();
	        }        
	        this.paginaVO.setRegistrosXPag(50);
	        this.paginaVO.setOffset(0);
	        this.banderaBitacoraConsulta = false;
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
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return this.resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(final PaginaVO resultados) {
        this.resultados = resultados;
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
    public ConsultaCapitalesParamsTO getCalendario() {
        return this.calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendario(final ConsultaCapitalesParamsTO calendario) {
        this.calendario = calendario;
    }
   
    /**
     * @return the calendarioEnvio
     */
    public EnvioCapitalesParamsTO getCalendarioEnvio() {
    	return this.calendarioEnvio;
    }
    
    /**
     * @param calendarioEnvio the calendarioEnvio to set
     */
    public void setCalendarioEnvio(final EnvioCapitalesParamsTO calendarioEnvio) {
    	this.calendarioEnvio = calendarioEnvio;
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
	
}
