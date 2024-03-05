/**
 * Bursatec - INDEVAL
 * Portal DALI
 *
 * EstadoPaginacionDTO.java
 * 10/12/2013
 */
package com.indeval.portaldali.presentation.common.constants;

/**
 * Interface que expone las constantes para la paginación de resultados.
 * @author Pablo Balderas.
 *
 */
public interface PaginacionConstantes {

	/** Cadena que indica que se reiniciara la paginacion */
	String REINICIAR_PAGINACION = "reiniciarPaginacion";
	
	/** Accion ir al inicio */
	String IR_AL_PRINCIPIO = "irAlPrincipio";

	/** Accion retroceder rapido */
	String RETROCEDER_PAGINAS_RAPIDO = "retrocederPaginasRapido";
	
	/** Accion retroceder */
	String RETROCEDER_PAGINA = "retrocederPagina";
		
	/** Accion avanzar */
	String AVANZAR_PAGINA = "avanzarPagina";
	
	/** Accion avanzar rápido */
	String AVANZAR_PAGINAS_RAPIDO = "avanzarPaginasRapido";
	
	/** Acción ir al último */
	String IR_AL_ULTIMO = "irAlUltimo";
	
	/** Acción cambiar tamaño de la paginación */
	String TAMANIO_PAGINA_POR_REGISTRO = "tamanioPaginaPorRegistro";
	
}
