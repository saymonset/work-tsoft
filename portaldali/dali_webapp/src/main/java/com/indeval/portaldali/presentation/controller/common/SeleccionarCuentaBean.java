/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * SeleccionarPosicionBean.java
 * 17/04/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;


/**
 * Backing bean para atender las solicitudes de búsqueda de cuentas
 * 
 * @author Emigdio Hernández
 * 
 */
public class SeleccionarCuentaBean extends ControllerBase{
	
	/**
	 * Acceso a la consulta de catálogos 
	 * 
	 */
	ConsultaCatalogosFacade consultaCatalogos = null;

	
	
	
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo
	 * de cuentas del campo traspasante.
	 * @param prefijo Criterio de búsqueda
	 * @return Lista con las cuentas
	 */
	public List<CuentaDTO> buscarCuentaTraspasante(Object prefijo) {
		CuentaDTO criterio = new CuentaDTO();
		List<CuentaDTO> resultados = new ArrayList<CuentaDTO>();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		InstitucionDTO institucion = null;
		criterio.setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.setTipoTenencia(new TipoTenenciaDTO());
		criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
		if(!StringUtils.isEmpty(params.get("nombre_id_folio_traspasante")) && 
				!StringUtils.isEmpty(params.get(params.get("nombre_id_folio_traspasante")))){
			institucion = consultaCatalogos.buscarInstitucionPorIdFolio(params.get(params.get("nombre_id_folio_traspasante")));
		}
		if(institucion != null){
			criterio.setInstitucion(institucion);
			criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + prefijo.toString().replace('*','%'));
			resultados = consultaCatalogos.getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
		}else{
			CuentaDTO sinOpciones = new CuentaDTO();
			sinOpciones.setNombreCuenta("Debe seleccionar primero una instituci\u00F3n traspasante");
			sinOpciones.setDescripcion(" ");
			sinOpciones.setCuenta(" ");
			resultados.add(sinOpciones);
		}
		
		if(resultados.size() == 0){
			CuentaDTO sinOpciones = new CuentaDTO();
			sinOpciones.setNombreCuenta("Sin coincidencias");
			sinOpciones.setDescripcion(" ");
			sinOpciones.setCuenta(" ");
			resultados.add(sinOpciones);
		}
		
		
		return resultados;
	}
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo
	 * de cuentas del campo receptor.
	 * @param prefijo Criterio de búsqueda
	 * @return Lista con las cuentas
	 */
	public List<CuentaDTO> buscarCuentaReceptor(Object prefijo) {
		CuentaDTO criterio = new CuentaDTO();
		List<CuentaDTO> resultados = new ArrayList<CuentaDTO>();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		InstitucionDTO institucion = null;
		criterio.setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.setTipoTenencia(new TipoTenenciaDTO());
		criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
		if(!StringUtils.isEmpty(params.get("nombre_id_folio_receptor")) && 
				!StringUtils.isEmpty(params.get(params.get("nombre_id_folio_receptor")))){
			institucion = consultaCatalogos.buscarInstitucionPorIdFolio(params.get(params.get("nombre_id_folio_receptor")));
		}
		if(institucion != null){
			criterio.setInstitucion(institucion);
			criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + prefijo.toString().replace('*','%'));
			resultados = consultaCatalogos.getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
		}else{
			CuentaDTO sinOpciones = new CuentaDTO();
			sinOpciones.setNombreCuenta("Debe seleccionar primero una instituci\u00F3n receptora");
			sinOpciones.setDescripcion(" ");
			sinOpciones.setCuenta(" ");
			resultados.add(sinOpciones);
		}
		if(resultados.size() == 0){
			CuentaDTO sinOpciones = new CuentaDTO();
			sinOpciones.setNombreCuenta("Sin coincidencias");
			sinOpciones.setDescripcion(" ");
			sinOpciones.setCuenta(" ");
			resultados.add(sinOpciones);
		}
		
		
		
		return resultados;
		
	}
	
	/**
	 * Busca instituciones en el catálogo cuyo nombre corto comience con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link InstitucionDTO} con todas
	 *         las coincidencias encontradas.
	 */
	public List<InstitucionDTO> buscarParticipante(Object value) {
		List<InstitucionDTO> resultado = null;
		List<TipoInstitucionDTO> institucion = new ArrayList<TipoInstitucionDTO>();
		List<InstitucionDTO> listaInstituciones = null;
		String prefijoAjustado = "";

		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
			listaInstituciones = new ArrayList<InstitucionDTO>();

			institucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(prefijoAjustado);
			resultado = consultaCatalogos.getConsultaInstitucionService().buscarInstitucionesPorPrefijo(prefijoAjustado);

			if (!institucion.isEmpty()) {

				InstitucionDTO ins = new InstitucionDTO();
				ins.setId(new Long(100) + institucion.get(0).getId());
				ins.setClaveTipoInstitucion(institucion.get(0).getClaveTipoInstitucion());
				ins.setNombreCorto(institucion.get(0).getDescripcion());

				listaInstituciones.add(ins);
			}

			for (InstitucionDTO institucionDTO : resultado) {
				listaInstituciones.add(institucionDTO);
			}
		}

		return listaInstituciones;
	}
	
	/**
	 * Obtiene el campo consultaCatalogos
	 * @return  consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogos
	 * @param consultaCatalogos el valor de consultaCatalogos a asignar
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	
}
