/**
 * 
 */
package com.indeval.portaldali.presentation.util;

import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.presentation.common.constants.PaginacionConstantes;

/**
 * Clase de utileria para paginar sobre una lista de objetos.
 * 
 * @author Pablo Balderas.
 * @version 1.0
 */
public class PaginadorUtil {

	/**
	 * Realiza la paginación por divisas en la pantalla.
	 * @param event Evento generado.
	 */
	public static void navegarPaginacion(String accion, EstadoPaginacionDTO estadoPaginacion) {
		try {
			//Indica que no se debe reiniciar la paginación
			estadoPaginacion.getMetaDatos().put(PaginacionConstantes.REINICIAR_PAGINACION,false);
			//Busca la acción a ejecutar
			if (PaginacionConstantes.IR_AL_PRINCIPIO.equalsIgnoreCase(accion)) {
				estadoPaginacion.irAlPrincipio();
			}
			else if (PaginacionConstantes.RETROCEDER_PAGINAS_RAPIDO.equalsIgnoreCase(accion)) {
				estadoPaginacion.retrocederPaginasRapido();
			}
			else if (PaginacionConstantes.RETROCEDER_PAGINA.equalsIgnoreCase(accion)) {
				estadoPaginacion.retrocederPagina();
			}
			else if (PaginacionConstantes.AVANZAR_PAGINA.equalsIgnoreCase(accion)) {
				estadoPaginacion.avanzarPagina();
			}
			else if (PaginacionConstantes.AVANZAR_PAGINAS_RAPIDO.equalsIgnoreCase(accion)) {
				estadoPaginacion.avanzarPaginasRapido();
			}
			else if (PaginacionConstantes.IR_AL_ULTIMO.equalsIgnoreCase(accion)) {
				estadoPaginacion.irAlUltimo();
			}
		} 
		catch (Exception e) {
			throw new BusinessException("Ocurrio un error al paginar por divisa", e);
		}
	}
	
	/**
	 * Realiza la paginación por divisas en la pantalla.
	 * @param event Evento generado.
	 */
	public static void navegarPaginacion(String accion, EstadoPaginacionExtended estadoPaginacion) {
		try {
			//Indica que no se debe reiniciar la paginación
			estadoPaginacion.getMetaDatos().put(PaginacionConstantes.REINICIAR_PAGINACION,false);
			//Busca la acción a ejecutar
			if (PaginacionConstantes.IR_AL_PRINCIPIO.equalsIgnoreCase(accion)) {
				estadoPaginacion.irAlPrincipio();
			}
			else if (PaginacionConstantes.RETROCEDER_PAGINAS_RAPIDO.equalsIgnoreCase(accion)) {
				estadoPaginacion.retrocederPaginasRapido();
			}
			else if (PaginacionConstantes.RETROCEDER_PAGINA.equalsIgnoreCase(accion)) {
				estadoPaginacion.retrocederPagina();
			}
			else if (PaginacionConstantes.AVANZAR_PAGINA.equalsIgnoreCase(accion)) {
				estadoPaginacion.avanzarPagina();
			}
			else if (PaginacionConstantes.AVANZAR_PAGINAS_RAPIDO.equalsIgnoreCase(accion)) {
				estadoPaginacion.avanzarPaginasRapido();
			}
			else if (PaginacionConstantes.IR_AL_ULTIMO.equalsIgnoreCase(accion)) {
				estadoPaginacion.irAlUltimo();
			}
		} 
		catch (Exception e) {
			throw new BusinessException("Ocurrio un error al paginar por divisa", e);
		}
	}

	/**
	 * Calcula el numero de paginas y los indices inicial y final para paginar sobre una lista
	 * de resultados. 
	 * @param estadoPaginacion DTO con el estado de la paginación.
	 */
	public static void calcularPaginacion(EstadoPaginacionExtended estadoPaginacion) {		
		int indiceInicial = 0;
		int indiceFinal = 0;
		int numeroPaginas = 0;
		//Calcula el número de páginas
		if (estadoPaginacion.getTotalResultados() % estadoPaginacion.getRegistrosPorPagina() == 0) {
			numeroPaginas = estadoPaginacion.getTotalResultados() / estadoPaginacion.getRegistrosPorPagina();
		} 
		else {
			numeroPaginas = (estadoPaginacion.getTotalResultados() / estadoPaginacion.getRegistrosPorPagina()) + 1;
		}
		//Calcula el indice inicial
		indiceInicial = estadoPaginacion.getRegistrosPorPagina() * (estadoPaginacion.getNumeroPagina() - 1);
        if (indiceInicial < 0) {
          indiceInicial = 0;
        }
        //Calcula el indice final
        indiceFinal = indiceInicial + estadoPaginacion.getRegistrosPorPagina();
        if (indiceFinal > estadoPaginacion.getTotalResultados()) {
        	indiceFinal = estadoPaginacion.getTotalResultados();
        }
        //Establece el numero de paginas
        estadoPaginacion.setTotalPaginas(numeroPaginas);
        //Establece los indices en el estado de la paginacion
        estadoPaginacion.setIndice(indiceInicial);
        estadoPaginacion.setSubindice(indiceFinal);
	}

}
