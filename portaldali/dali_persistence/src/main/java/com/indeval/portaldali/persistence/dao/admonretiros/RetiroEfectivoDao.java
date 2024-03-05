/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admonretiros;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.persistence.model.RetiroEfectivoInternacional;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre el catalogo
 * de retiros 
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface RetiroEfectivoDao {

	public int autoriza(long idOperacionEfectivo, String usuario, String serie, String isoFirmado);
	public int libera(long idOperacionEfectivo, String usuario, String serie, String isoFirmado);
	public RetiroEfectivo findById(long idOperacionEfectivo);
	public RetiroEfectivo findByReferencia(String referenciaOperacion);
	public int count(CriterioEstatusOpEfectivoDTO criterioConsulta);
	public List<RetiroEfectivo> find(final CriterioEstatusOpEfectivoDTO criterioConsulta);
	public Number sumImporte(final CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	/**
	 * Consulta los retiros por id
	 * @param id objeto Long que identifica un retiro
	 * @return objeto tipo RetiroEfectivoInternacional
	 */
	RetiroEfectivoInternacional getRetiro(BigInteger id);
	
	/**
	 * Almacena un nuevo registro de retiro de efectivo
	 * @param  RetiroEfectivoInternacional
	 */
	void save(RetiroEfectivoInternacional retiro);
	
	/**
	 * Almacena un nuevo registro de retiro de efectivo nacional
	 * @param  RetiroEfectivoInternacional
	 */
	void save(RetiroEfectivo retiro);
	
	/**
	 * Actualiza un retiro
	 * @param  RetiroEfectivoInternacional
	 */
	void saveOrUpdate(RetiroEfectivoInternacional retiro);
	
	/**
	 * Consulta los retiros por id
	 * @param referenciaOperacion la referencia operacion del retiro
	 * @param esNacional define si es nacional o no
	 * @return objeto tipo RetiroEfectivoInternacionalDTO o RetiroEfectivoDTO
	 */
	public Object getRetiro(String referenciaOperacion, boolean esNacional);
}
