/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta;

import java.util.List;

import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Define el contrato que deben de cumplir las clases que representan una
 * consulta a la base de datos. Define la funcionalidad para utilizar la
 * consulta como un criterio de búsqueda de otras consultas o como un conjunto
 * de resultados para mostrat en una tabla paginada. Esta interface est
 * parametrizada para que las clases que la implementen puedan definir el tipo
 * de DTO que utilizarn com oresultado de sus operaciones
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface Consulta<T> {

	/**
	 * Avanza el cursor que define el resultado actual 1 posición adelante del
	 * resultado actual
	 */
	void avanzarPagina();

	/**
	 * Recorre la posición del resultado actual para que indique el principio de
	 * la lista de resultados
	 */
	void irAlPrincipio();

	/**
	 * Recorre la posición del resultado actual para que indique la última
	 * posición de la lista de resultados
	 */
	void irAlUltimo();

	/**
	 * Indica si esta consulta actualmente soporta la operacion ir al primero en
	 * base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación ir al primero en base
	 *         al estado de la paginación.
	 */
	boolean isIrAlPrimero();
	
	/**
	 * Indica si esta consulta actualmente soporta la operación retroceder rpido en base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación retroceder rpido en base al estado de la paginación.
	 */
	boolean isRetrocederRapido();
	
	/**
	 * Indica si esta consulta actualmente soporta la operación retroceder en base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación retroceder en base al estado de la paginación.
	 */
	boolean isRetroceder();
	
	/**
	 * Indica si esta consulta actualmente soporta la operación avanzar en base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación avanzar en base al estado de la paginación.
	 */
	boolean isAvanzar();
	
	/** 
	 * Indica si esta consulta actualmente soporta la operación avanzar rpido en base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación avanzar rpido en base al estado de la paginación.
	 */
	boolean isAvanzarRapido();
	
	/** 
	 * Indica si esta consulta actualmente soporta la operación ir al último registro en base al estado de la paginación.
	 * 
	 * @return <code>true</code> si tiene sentido invocar la operación ir al último registro en base al estado de la paginación.
	 */
	boolean isIrAlUltimo();
	
	/**
	 * Indica si la consulta debe considerar todos los valores. Este valor se
	 * utiliza cuando la consulta se ocupa para llenar una lista de valores que
	 * incluye un valor que representa todas las opciones dispinibles.
	 * 
	 * @return true cuando se debe de considerar todos los resultados obtenidos,
	 *         false en otro caso.
	 */
	boolean isTodos();

	/**
	 * Recorre el indicador de elemento seleccionado actual 1 posición hacia
	 * atrs, si la posición final del indicador despus de realizar la
	 * operación es menor a cero entonces el indicador de elemento actual se
	 * deja apuntando al primer elemento de los resultados, si no existen
	 * resultados se deja en -1
	 */
	void retrocederPagina();

	/**
	 * Obtiene la opción actualmente seleccionada del conjunto de resultados de
	 * la consulta.
	 * 
	 * @return El objeto que contiene los valores del resultado
	 */
	T getOpcionSeleccionada();

	/**
	 * Asigna el objeto que actualmente est seleccionado del conjunto de
	 * resultados producto de esta consulta.
	 * 
	 * @param opcionSeleccionada
	 *            el valor de opcionSeleccionada a asignar
	 */
	void setOpcionSeleccionada(T opcionSeleccionada);

	/**
	 * Asigna o desasigna un indicador que significa que se desean seleccionar todos los
	 * resultados de la consulta. Generalmente indica que el criterio de
	 * búsqueda que arroja esta consulta no ser tomado en cuanta para filtrar
	 * alguna consulta padre. El estado inicial del atributo "todos" es falso.
	 */
	void toggleTodos();

	/**
	 * Indica si como parte de los valores que se pueden elegir para la consulta
	 * se va a incluir una opción para que el usuario pueda elegir "Todos" los
	 * valores del criterio de filtrado.
	 * 
	 * @param val
	 *            True para cuando se deba agragar al conjunto de opciones
	 *            elegibles un primer valor que indique que se desean
	 *            seleccionar todos los valores de la consulta.
	 */
	void setPermiteTodos(boolean val);

	/**
	 * Obtiene el valor que indica que se va a agregar una opción adicional en
	 * el combo de búsqueda para que se pueda permitir elegir todos los valores
	 * de la consulta.
	 * 
	 * @return true para cuando se deba colocar la opción para permitir
	 *         seleccionar todos los valores que regresa la consulta.
	 */
	boolean isPermiteTodos();

	/**
	 * Obtiene un conjunto de resultados en forma de objetos
	 * <code>ResultadoDTO</code>, este método es utilizado para obtener los
	 * resultados de la consulta en crudo, la diferencia con el método
	 * <code>getOpciones</code>, es que ste último obtiene los resultados
	 * como un conjunto de opciones seleccionables utilizadas como criterio para
	 * una búsqueda ms compleja.
	 * 
	 * @return Lista con el conjunto de resultados de la consulta, los
	 *         resultados son colocados en una lista de objetos que representan
	 *         el resultadao de la consulta de la clase <code>T</code>
	 */
	List<T> getResultados();

	/**
	 * Obtiene un conjunto de resultados en forma de objetos
	 * <code>ResultadoDTO</code>, este método es utilizado para obtener los
	 * resultados de la consulta en crudo, la diferencia con el método
	 * <code>getOpciones</code>, es que ste último obtiene los resultados
	 * como un conjunto de opciones seleccionables utilizadas como criterio para
	 * una búsqueda ms compleja. Estos resultados consideran la configuración
	 * actual del paginador para generar una ventana de resultados.
	 * 
	 * @return Lista con el conjunto de resultados de la consulta, los
	 *         resultados son puestos generlmente en objetos representan un
	 *         resultado de la consulta
	 */
	List<T> getPaginaDeResultados();

	/**
	 * Asigna el paginador actual para la consulta.
	 * 
	 * @param paginador
	 *            Paginador a asignar
	 */
	void setEstadoPaginacion(EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene el paginado actual para la consulta
	 * 
	 * @return Paginador actual
	 */
	EstadoPaginacionDTO getEstadoPaginacion();
	
	/**
	 * Avanza el cursor actual de la consulta n posiciones.
	 */
	void avanzarPaginasRapido();
	
	/**
	 * Retrocede el cursor actual de la consulta n posiciones.
	 */
	void retrocederPaginasRapido();
	
	/** Indica si los resultados de la consulta deberán ser paginados */
	boolean isDebePaginar();
	
	/** 
	 * Establece si los resultados de la consulta deberán ser paginados
	 * 
	 * @param debePaginar <code>true</code> si los resultados deberán ser paginados. <code>false</code> en otro caso.
	 */
	void setDebePaginar(boolean debePaginar);
	
	/**
	 * Reestablece el estado de la paginación de una consulta.
	 */
	void reestablecerEstadoPaginacion();
	
	/**
	 * Obtiene la proyección del número de resultados que serán obtenidos de esta consulta.
	 * 
	 * @return la proyección del número de resultados que serán obtenidos de esta consulta.
	 */
	long obtenerProyeccionConsulta();
	/**
	 * Recibe una notificación una consulta que depende del criterio de esta consulta.
	 * Pasa como parámetro los resultados obtenidos de la consulta dependiente.
	 * @param resultados Resultados obtenidos de la consulta dependiente
	 */
	@SuppressWarnings("unchecked")
	void recibirNotificacionResultados(List resultados);

}