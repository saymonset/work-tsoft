/**
 * 2H Software SA de CV
 * 
 * Proyecto: Portal DALI
 * 
 * Archivo: DTOValidatorImpl.java
 *
 *
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.presentation.validation.BundleManager;
import com.indeval.portaldali.presentation.validation.CriterioDeValidacion;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Implementación del validador de DTOs con la variación de que no se detiene en el primer error y contina validando
 * la totalidad de los campos a validar.
 * Acumula los mensajes de error en un mapa donde la llave es la ruta para llegar al campo a validar.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class DTOValidatorAcumulativoImpl implements DTOValidator {
	
	/**
	 * Mapa que contiene como llaves el nombre de las propiedades a validar y
	 * como atributo una lista con los criterios de validación que deber
	 * aplicar a dicha propiedad
	 */
	private List criteriosValidacion = null;
	
	/** El nombre del resource bundle donde se encuentran los mensajes localizados de la validación */
	private String nombreBundle = null;
	
	/** Mecanismo de logging de la clase */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * método para obtener el atributo nombreBundle
	 *
	 * @return Obtiene el atributo nombreBundle.
	 */
	public String getNombreBundle() {
		return nombreBundle;
	}
	/**
	 * método para establecer el atributo nombreBundle
	 *
	 * @param nombreBundle El valor del atributo nombreBundle a establecer.
	 */
	public void setNombreBundle(String nombreBundle) {
		this.nombreBundle = nombreBundle;
	}
	/**
	 * método para obtener el atributo criteriosValidacion
	 *
	 * @return Obtiene el atributo criteriosValidacion.
	 */
	public List getCriteriosValidacion() {
		return criteriosValidacion;
	}
	/**
	 * método para establecer el atributo criteriosValidacion
	 *
	 * @param criteriosValidacion El valor del atributo criteriosValidacion a establecer.
	 */
	public void setCriteriosValidacion(List criteriosValidacion) {
		this.criteriosValidacion = criteriosValidacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.DTOValidator#validarDTO(com.hh.renapo.framework.services.dto.AbstractDTO)
	 */
	public ResultadoValidacionDTO validarDTO(Object dto) {
		
		ResultadoValidacionDTO resultado = new ResultadoValidacionDTO();
		resultado.setValido(true);
		resultado.setMensajesError(new HashMap<String, List<String>>());
		for(Iterator<CriterioDeValidacion> it = criteriosValidacion.iterator(); it.hasNext(); ) {
			
			CriterioDeValidacion criterioDeValidacion = (CriterioDeValidacion)it.next();
			Map<String, Object> parametrosCriterio = criterioDeValidacion.getParametros();
			Map<String, Object> parametros = new HashMap<String, Object>();
			for(Iterator<String> itParams = parametrosCriterio.keySet().iterator(); itParams.hasNext(); ) {
				
				String llave = (String)itParams.next();
				String nombrePropiedad = (String)parametrosCriterio.get(llave);
				Object valor = null;
				if(PropertyUtils.isReadable(dto, nombrePropiedad)){
					try {
						valor = PropertyUtils.getProperty(dto, nombrePropiedad);
					} catch (Exception e) {
						
						throw new InfrastructureException("Ocurrió un problema al leer la propiedad " + nombrePropiedad + " de la clase " + dto.getClass().getName(), e);
					} 
				} else {
					throw new InfrastructureException("No es posible leer la propiedad " + nombrePropiedad + " de la clase " + dto.getClass().getName());
				}
				parametros.put(llave, valor);
			}
			
			boolean resultadoValidacion = false;
			
			try {
				resultadoValidacion = criterioDeValidacion.validar(parametros);
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				resultado.setValido(false);
				resultado.setMensaje(criterioDeValidacion.getClass().getName() + ": " + e.getMessage());
				break;
			}
			
			if(!resultadoValidacion) {
				resultado.setValido(false);
				for (String campo : criterioDeValidacion.getParametros().keySet()) {
					if(resultado.getMensajesError().get((String)criterioDeValidacion.getParametros().get(campo)) == null){
						resultado.getMensajesError().put((String)criterioDeValidacion.getParametros().get(campo), new ArrayList<String>());
					}
					resultado.getMensajesError().get((String)criterioDeValidacion.getParametros().get(campo)).add(BundleManager.obtenerMensajeLocalizado(nombreBundle, criterioDeValidacion.getLlaveMensaje()));
				}
				
				
				
				resultado.setMensaje(BundleManager.obtenerMensajeLocalizado(nombreBundle, criterioDeValidacion.getLlaveMensaje()));
				
			}
		}
		
		return resultado;
	}

}
