package com.indeval.portaldali.presentation.controller.conciliacionModulos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosDTO;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosExcelDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriteriosConciliacionModulosDTO;
import com.indeval.portaldali.middleware.services.conciliacionModulos.ConciliacionModulosService;
import com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

public class ConciliacionModulosController extends ControllerBase {
	
	private static final String TAB_SPEI_MOS = "SPEI_MOS";
	private static final String ROL_REENVIO = "INDEVAL_REENVIO";
	private static final String ROL_REENVIO_RETE = "INDEVAL_REENVIO_RETE";

	private ConciliacionModulosService conciliacionModulosService;
	private ConsultaInstruccionesEfectivoService consultaInstruccionesEfectivoService;
	private CriteriosConciliacionModulosDTO criterios = new CriteriosConciliacionModulosDTO();
	private FlujoConciliacionModulosEnum[] flujos = FlujoConciliacionModulosEnum.values();
	private List<ConciliacionModulosDTO> resultados;
	private List<InstruccionEfectivoDTO> resultadosReteSiac;
	private String tabActiva;
	private boolean mostrarConciliacion = false;
	private boolean rolReenviar = false;
	private boolean rolReenviarRete = false;
	private ConsultaCatalogosFacade consultaCatalogos = null;
	private String cadenaResultado = null;

	@SuppressWarnings("unchecked")
	public String getInit() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Map<String, String> rolesSesion = (Map<String, String>) session.getAttribute(SeguridadConstants.ROLES_SESION);
		rolReenviar = rolesSesion.containsValue(ROL_REENVIO);
		rolReenviarRete = rolesSesion.containsValue(ROL_REENVIO_RETE);
		return null;
	}
	
	public void generaConciliacion(boolean nuevaBusqueda) {		
		if(nuevaBusqueda) {
			this.tabActiva = this.flujos[0].name();
			this.criterios.setIdTab(this.flujos[0].getId());
		} else {
			this.tabActiva = FlujoConciliacionModulosEnum.obtenPorId(this.criterios.getIdTab()).name();
		}

		if (this.tabActiva.equals(TAB_SPEI_MOS)) {
			this.resultadosReteSiac = this.consultaInstruccionesEfectivoService.consultarReteAprobadas();
		} else {
			this.resultados = this.conciliacionModulosService.obtenConciliacionModulos(this.criterios);
		}
		this.mostrarConciliacion = true;
	}
	
	public void generaConciliacionExcel() {
        try {
        	reiniciarEstadoPeticion();
        	
        	this.criterios.setInstitucionActual(getInstitucionActual());
        	ConciliacionModulosExcelDTO conciliacionModulosExcelDTO = this.conciliacionModulosService.generaExcelConciliacionModulos(this.criterios);
            
            if(conciliacionModulosExcelDTO.isReporteVacio()) {
            	addMessage("La conciliaci\u00f3n no arroj\u00f3 datos para exportar al archivo de Excel.", FacesMessage.SEVERITY_WARN);
            	return;
            }
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename=conciliacionModulos.xls");
            ServletOutputStream out = response.getOutputStream();
            
            out.write(conciliacionModulosExcelDTO.getBytesReporte());
            
            out.flush();
            out.close();
        } catch(IOException e) {
            logger.error("Error al generar el reporte de conciliación de modulos INDEVAL en Excel", e);
        } 
        
        FacesContext faces = FacesContext.getCurrentInstance();
        faces.responseComplete();  
        asignarEstadoPeticionReporteCompleta();
	}

	public void reenviaRete() {
		List<InstruccionEfectivoDTO> retes = new ArrayList<>();
		for(InstruccionEfectivoDTO registro : this.resultadosReteSiac) {
			if(registro.isCorregir()) {
				retes.add(registro);
			}
		}
		logger.info("SE REENVIARAN: " + retes.size() + " RETE");
		cadenaResultado = conciliacionModulosService.reenviaRete(retes, obtenerUsuarioSesion().getClaveUsuario(), obtenerIpRemota());
		addMessage(cadenaResultado, FacesMessage.SEVERITY_INFO);
	}
	
	public void reenviaMensajes() {
		List<ConciliacionModulosDTO> reenvios = new ArrayList<>();
		
		for(ConciliacionModulosDTO registro : this.resultados) {
			if(registro.isCorregir()) {
				reenvios.add(registro);
			}
		}
		
		this.conciliacionModulosService.reenviaMensajes(reenvios, FlujoConciliacionModulosEnum.obtenPorId(this.criterios.getIdTab()), obtenerUsuarioSesion().getClaveUsuario());
	}
	
	public void actualizarListaOperaciones() {
		List<ConciliacionModulosDTO> listaAuxiliar = new ArrayList<>();
		listaAuxiliar.addAll(resultados);
		for(ConciliacionModulosDTO registro : listaAuxiliar) {
			if(registro.isCorregir()) {
				this.resultados.remove(registro);
			}
		}
	}

	public void actualizarListaRete() {
	}

	public void limpiar(ActionEvent e) {
		this.criterios = new CriteriosConciliacionModulosDTO();
		this.mostrarConciliacion = false;
	}
	
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(null, String.valueOf(prefijo));

	}

	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object prefijo) {
		return consultaCatalogos.buscarEmisorasPorPrefijo(prefijo);
	}
	
	public List<InstitucionDTO> buscarParticipante(Object prefijo) {
		return consultaCatalogos.buscarParticipantePorPrefijo(prefijo);
	}
	
	public List<CuentaDTO> buscarCuentasParticipantePorPrefijo(Object prefijo) {
		return consultaCatalogos.buscarCuentasDeInstitucionPorPrefijo(this.criterios.getIdFolioParticipante(), (String) prefijo);
	}

	public List<CuentaDTO> buscarCuentasContrapartePorPrefijo(Object prefijo) {
		return consultaCatalogos.buscarCuentasDeInstitucionPorPrefijo(this.criterios.getIdFolioContraparte(), (String) prefijo);
	}
	
	public List<SelectItem> getTiposInstruccion() {
		return consultaCatalogos.getSelectItemsTiposInstruccion();
	}
	
	public List<SelectItem> getTiposOperacion() {
		return consultaCatalogos.getSelectItemsTiposOperacion();
	}

	public void setConciliacionModulosService(ConciliacionModulosService conciliacionModulosService) {
		this.conciliacionModulosService = conciliacionModulosService;
	}

	public void setConsultaInstruccionesEfectivoService(
			ConsultaInstruccionesEfectivoService consultaInstruccionesEfectivoService) {
		this.consultaInstruccionesEfectivoService = consultaInstruccionesEfectivoService;
	}

	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	public CriteriosConciliacionModulosDTO getCriterios() {
		return criterios;
	}

	public void setCriterios(CriteriosConciliacionModulosDTO criterios) {
		this.criterios = criterios;
	}

	public List<ConciliacionModulosDTO> getResultados() {
		return resultados;
	}

	public List<InstruccionEfectivoDTO> getResultadosReteSiac() {
		return resultadosReteSiac;
	}

	public boolean isMostrarConciliacion() {
		return mostrarConciliacion;
	}

	public String getTabActiva() {
		return tabActiva;
	}

	public FlujoConciliacionModulosEnum[] getFlujos() {
		return flujos;
	}

	public boolean isRolReenviar() {
		return rolReenviar;
	}

	public boolean isRolReenviarRete() {
		return rolReenviarRete;
	}

	public String getCadenaResultado() {
		return cadenaResultado;
	}

}
