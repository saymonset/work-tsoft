/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCatalogoService.java
 * Apr 24, 2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.math.BigDecimal;

/**
 * Interfaz que define el contrato del servicio de negocio que permite realizar búsquedas a los catálogos usados
 * en la capa de vista y que no requieren un contexto transaccional de EJB
 * @author Emigdio Hernández
 *
 */
public interface ConsultaCatalogoService {
	
	
	 /**
	  * Invoca el DAO de consulta de saldo para obtener el saldo de efectivo de un participante
	  * @param idUsuarioFirmado Clave tipo institución del usuario
	  * @param folioUsuarioFirmado Folio de la institución del usuario
	  * @param tipoCuenta Tipo de cuenta a consultar
	  * @return BigDecimal con el saldo efectivo
 	  */
	 public BigDecimal getMontoNetoEfectivo(String idUsuarioFirmado, String folioUsuarioFirmado, String tipoCuenta);
}
