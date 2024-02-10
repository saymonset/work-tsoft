// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EstadoPaginacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** El número de página actual de la consulta */
	private int numeroPagina = -1;
	
	/** La cantidad de registros que serán presentados en una página de resultados */
	private int registrosPorPagina = -1;
	
	/** El total de resultados producto de la consulta */
	private int totalResultados = -1;
	
	/** El total de páginas de resultados productos de la consulta */
	private int totalPaginas = -1;
	/**
	 * Mapa con informaci&oacute;n adicional para el mapeo.
	 */
	private final Map<String,Object> metaDatos = new HashMap<String,Object>();

	
	
	/**
	 * Actualiza la información de la paginación al avanzar el número de páginas solicitadas.
	 * 
	 * @param paginas el número de páginas a avanzar.
	 */
	public void avanzar(int paginas) {
		
		if(totalResultados > 0) {
			if((numeroPagina + paginas) > totalPaginas) {
				numeroPagina = totalPaginas;
			} else {
				numeroPagina += paginas; 
			}
		}
	}
	
	/**
	 * Actualiza la información de la paginación al retroceder el número de páginas solicitadas.
	 * 
	 * @param paginas el número páginas a retroceder.
	 */
	public void retroceder(int paginas) {
		if(totalResultados > 0) {
			if((numeroPagina - paginas) < 1) {
				numeroPagina = 1;
			} else {
				numeroPagina -= paginas; 
			}
		}
	}
	
	/**
	 * Actualiza la información de la paginación al establecer la primera página de resultados como la página actual.
	 */
	public void irAlPrincipio() {
		if(totalResultados > 0) {
			numeroPagina = 1;
		}
	}
	
	public void irAlUltimo() {
		if(totalResultados > 0) {
			numeroPagina = totalPaginas;
		}
	}
	
	
	
	
	public boolean isIrAlPrimero() {

		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	
	public boolean isIrAlUltimo() {
		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	
	public boolean isAvanzar() {
		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	
	public boolean isAvanzarRapido() {
		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	
	public boolean isRetroceder() {
		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	
	public boolean isRetrocederRapido() {
		boolean resultado = false;

		if (getTotalResultados() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}
	
	
	public void avanzarPagina() {
		avanzar(1);
	}

	
	public void avanzarPaginasRapido() {
		avanzar(3);
	}
	public void retrocederPaginasRapido() {
		retroceder(3);
	}
	
	public void retrocederPagina() {
		retroceder(1);
	}

	/**
	 * @return the numeroPagina
	 */
	public int getNumeroPagina() {
		return numeroPagina;
	}

	/**
	 * @param numeroPagina the numeroPagina to set
	 */
	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	/**
	 * @return the registrosPorPagina
	 */
	public int getRegistrosPorPagina() {
		return registrosPorPagina;
	}

	/**
	 * @param registrosPorPagina the registrosPorPagina to set
	 */
	public void setRegistrosPorPagina(int registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	/**
	 * @return the totalResultados
	 */
	public int getTotalResultados() {
		return totalResultados;
	}

	/**
	 * @param totalResultados the totalResultados to set
	 */
	public void setTotalResultados(int totalResultados) {
		this.totalResultados = totalResultados;
	}

	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}

	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	/**
	 * Mapa con informaci&oacute;n adicional para paginar.
	 * @return Mapa con informaci&oacute;n adicional.
	 */
	public Map<String, Object> getMetaDatos() {
		return metaDatos;
	}
	/***
	 * Obtiene el estado paginaci&oacute;n de un mapa y si no existe lo crea y lo asigna al mapa.
	 * @param key Llave para obtener el estado de paginac&oacute;n.
	 * @param estadosPaginacionMap Mapa donde se obtiene o asigna el estado de paginac&oacute;n. 
	 * @return El estado de paginac&oacute;n obtenido o generado.
	 */
	public static <K> EstadoPaginacionDTO getEstadoPaginacionFromMap(K key, Map<K,EstadoPaginacionDTO> estadosPaginacionMap) {
		EstadoPaginacionDTO estadoPaginacion = estadosPaginacionMap.get(key);
		if (estadoPaginacion == null) { 
			estadoPaginacion = new EstadoPaginacionDTO();
			estadoPaginacion.setRegistrosPorPagina(50);				
			estadoPaginacion.setNumeroPagina(1);
			estadoPaginacion.setTotalPaginas(0);
			estadoPaginacion.setTotalResultados(0);
			estadoPaginacion.getMetaDatos().put("reiniciarPaginacion",Boolean.TRUE);
			estadosPaginacionMap.put(key, estadoPaginacion);
		}
		return estadoPaginacion;
	}
	
}
