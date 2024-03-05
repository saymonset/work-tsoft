/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.util.List;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * Interface que expone los métodos para el Estado de Cuenta
 * de las Sociedades de Inversión.
 * 
 * @author Rafael Ibarra Zendejas
 */
public interface EstadoCuentaSocInvDAO {
	
	@SuppressWarnings("unchecked")
	public List getListaEmisiones(AgenteVO agente, EmisionVO emision);
	
	@SuppressWarnings("unchecked")
	public List getListaEmisionesRazonSocial(AgenteVO agente, EmisionVO emision);
	
	@SuppressWarnings("unchecked")
	public List getSaldoEstadoCuentasocInv(AgenteVO agente, String emisora);
	
	@SuppressWarnings("unchecked")
	public List getLista1234(AgenteVO agente);
	
}
