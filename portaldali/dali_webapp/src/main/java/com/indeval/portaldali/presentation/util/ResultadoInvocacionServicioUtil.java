/**
 * 2H Software  - Bursatec
 * Portal DALI
 */
package com.indeval.portaldali.presentation.util;

/**
 * Clase para el acceso a ThreadLocal para colocar la bandera de terminación correcta de servicio de Negocio.
 * Esta clase es usada únicamente para las llamadas a servicios Remotos que tienen un interceptor para envolver las excepciones de negocio.
 * El interceptor utiliza esta clase para colocar el resultado de la invocación ya que la excepción no es tirada por el interceptor , sino el 
 * interceptor coloca un mensaje de JSF e indica por medio del thread local que el servicio fall en su invocación.
 * @author Emigdio Hernández
 *
 */
public class ResultadoInvocacionServicioUtil {
	  /**
	   * Clase interna para el acceso a thread local
	   * @author Emigdio Hernández.
	   *
	   */
	  private static class ThreadLocalResultado extends ThreadLocal<Boolean> {
		/**
		 * Valor inicial de la invocación, un valor verdadero indica que la invocación termino con error
		 * un valor falso indica que no existen errores
		 */
	    public Boolean initialValue() {
	      return new Boolean(false);
	    }
	  }
	  /**
	   * Clase privada de acceso a thread local
	   */
	  private static ThreadLocalResultado resultadoInvocacion = new ThreadLocalResultado();
	  /**
	   * Verifica la bandera de error
	   * @return True si existe error en la invocación, false en tro caso.
	   */
	  public static Boolean existeError() {
	    return  resultadoInvocacion.get();
	  }
	  /**
	   * Asigna el valor de resultado de invocación.
	   * @param valor
	   */
	  public static void  setExisteError(Boolean valor){
		  resultadoInvocacion.set(valor);
	  }
		

}
