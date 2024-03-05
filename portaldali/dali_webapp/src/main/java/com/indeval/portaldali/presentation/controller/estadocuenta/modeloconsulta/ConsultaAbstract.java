/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta;

import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Implementa la funcionalidad bsica predeterminada para una consulta sencilla
 * de un sólo catálogo. Provee los métodos necesarios para el manejo de una
 * consulta que no cuenta con criterios de filtrado. Esta clase incluye
 * parametrización para que las clases de consulta c
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * @created 30-nov-2007 02:17:33 p.m.
 */
public abstract class ConsultaAbstract<T> implements Consulta<T> {

	/**
	 * indica si se seleccionaron todos los elementos de la lista de opciones
	 */
	protected boolean todos = true;
	/**
	 * indica si el combo permitir la opción de seleccionar todos como parte de
	 * las opciones a elegir
	 */
	protected boolean permiteTodos = false;
	/**
	 * paginador actual utilizado en la consulta
	 */
	protected EstadoPaginacionDTO estadoPaginacion = new EstadoPaginacionDTO();

	/**
	 * Representa el objeto seleccionado del conjunto de resultados productos de
	 * esta consulta
	 */
	protected T opcionSeleccionada = null;

	/**
	 * Indica si los resultados de la consulta deberán ser paginados.
	 */
	protected boolean debePaginar = false;
	/**
	 * Indica el tamaño de la página que la consulta debe mostrar, de forma predeterminada se muestra un resultado
	 * por página
	 */
	protected int resultadosPorPagina = 1;
	/**
	 * Nombre de la  columna que est actualmente ordenada 
	 */
	protected String columnaOrdenada = null;
	/**
	 * Indica si la conslta se ordena de forma ascendente
	 */
	protected boolean ordenAscendente = true;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#avanzar(int)
	 */
	public void avanzarPagina() {
		estadoPaginacion.avanzar(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#avanzarRapido()
	 */
	public void avanzarPaginasRapido() {
		estadoPaginacion.avanzar(3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#irAlPrincipio()
	 */
	public void irAlPrincipio() {
		estadoPaginacion.irAlPrincipio();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#irAlUltimo()
	 */
	public void irAlUltimo() {
		estadoPaginacion.irAlUltimo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isIrAlPrimero()
	 */
	public boolean isIrAlPrimero() {

		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isIrAlUltimo()
	 */
	public boolean isIrAlUltimo() {
		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() < estadoPaginacion
					.getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isAvanzar()
	 */
	public boolean isAvanzar() {
		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() < estadoPaginacion
					.getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isAvanzarRapido()
	 */
	public boolean isAvanzarRapido() {
		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() < estadoPaginacion
					.getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isRetroceder()
	 */
	public boolean isRetroceder() {
		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isRetrocederRapido()
	 */
	public boolean isRetrocederRapido() {
		boolean resultado = false;

		if (estadoPaginacion != null
				&& estadoPaginacion.getTotalResultados() > 0) {
			if (estadoPaginacion.getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isTodos()
	 */
	public boolean isTodos() {
		return todos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setTodos(boolean)
	 */
	public void toggleTodos() {
		todos = !todos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#retroceder(int)
	 */
	public void retrocederPagina() {
		estadoPaginacion.retroceder(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isPermiteTodos()
	 */
	public boolean isPermiteTodos() {
		return permiteTodos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setPermiteTodos(boolean)
	 */
	public void setPermiteTodos(boolean permiteTodos) {
		this.permiteTodos = permiteTodos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getResultados()
	 */
	public abstract List<T> getResultados();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getPaginaDeResultados()
	 */
	public abstract List<T> getPaginaDeResultados();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getEstadoPaginacion()
	 */
	public EstadoPaginacionDTO getEstadoPaginacion() {

		EstadoPaginacionDTO estadoPaginacionDTO = null;

		if (this.estadoPaginacion != null
				&& this.estadoPaginacion.getTotalResultados() == -1) {
			estadoPaginacionDTO = obtenerInformacionPaginacion();
			this.setEstadoPaginacion(estadoPaginacionDTO);
		} else {
			estadoPaginacionDTO = estadoPaginacion;
		}

		return estadoPaginacionDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#reestablecerEstadoPaginacion()
	 */
	public void reestablecerEstadoPaginacion() {

		this.estadoPaginacion = null;
		this.estadoPaginacion = obtenerInformacionPaginacion();
	}

	/**
	 * Obtiene la información de la paginación de la consulta.
	 * 
	 * @return el estado actual de la paginación de la consulta.
	 */
	private EstadoPaginacionDTO obtenerInformacionPaginacion() {
		EstadoPaginacionDTO estadoPaginacionDTO = new EstadoPaginacionDTO();

		estadoPaginacionDTO.setTotalResultados((int)obtenerProyeccionConsulta());
		if (estadoPaginacionDTO.getTotalResultados() > 0) {
			estadoPaginacionDTO.setNumeroPagina(1);
			estadoPaginacionDTO.setRegistrosPorPagina(resultadosPorPagina);
			estadoPaginacionDTO.setTotalPaginas((int)Math.ceil((double)estadoPaginacionDTO
					.getTotalResultados()/(double)resultadosPorPagina));
		} else {
			estadoPaginacionDTO.setNumeroPagina(0);
			estadoPaginacionDTO.setRegistrosPorPagina(0);
			estadoPaginacionDTO.setTotalPaginas(0);
		}

		return estadoPaginacionDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setEstadoPaginacion(com.indeval.estadocuenta.presentation.model.PaginadorDTO)
	 */
	public void setEstadoPaginacion(EstadoPaginacionDTO estaadoPaginacion) {
		this.estadoPaginacion = estaadoPaginacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#retrocederRapido()
	 */
	public void retrocederPaginasRapido() {
		estadoPaginacion.retroceder(3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#isDebePaginar()
	 */
	public boolean isDebePaginar() {
		return debePaginar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setDebePaginar(boolean)
	 */
	public void setDebePaginar(boolean debePaginar) {
		this.debePaginar = debePaginar;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#obtenerProyeccionConsulta()
	 */
	public long obtenerProyeccionConsulta() {
		
		return 0L;
	}

	/**
	 * Obtiene el campo resultadosPorPagina
	 * @return  resultadosPorPagina
	 */
	public int getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	/**
	 * Asigna el valor del campo resultadosPorPagina
	 * @param resultadosPorPagina el valor de resultadosPorPagina a asignar
	 */
	public void setResultadosPorPagina(int resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}
	/**
	 * Obtiene la configuración actual de ordenamiento para la consulta
	 * @return
	 */
	public CriterioOrdenamientoDTO getCriterioOrden(){
		CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
		orden.setColumna(this.getColumnaOrdenada());
		orden.setAscendente(this.isOrdenAscendente());
		return orden;
	}

	/**
	 * Obtiene el campo columnaOrdenada
	 * @return  columnaOrdenada
	 */
	public String getColumnaOrdenada() {
		return columnaOrdenada;
	}

	/**
	 * Asigna el valor del campo columnaOrdenada
	 * @param columnaOrdenada el valor de columnaOrdenada a asignar
	 */
	public void setColumnaOrdenada(String columnaOrdenada) {
		this.columnaOrdenada = columnaOrdenada;
	}

	/**
	 * Obtiene el campo ordenAscendente
	 * @return  ordenAscendente
	 */
	public boolean isOrdenAscendente() {
		return ordenAscendente;
	}

	/**
	 * Asigna el valor del campo ordenAscendente
	 * @param ordenAscendente el valor de ordenAscendente a asignar
	 */
	public void setOrdenAscendente(boolean ordenAscendente) {
		this.ordenAscendente = ordenAscendente;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void recibirNotificacionResultados(List resultados){
		
	}
}