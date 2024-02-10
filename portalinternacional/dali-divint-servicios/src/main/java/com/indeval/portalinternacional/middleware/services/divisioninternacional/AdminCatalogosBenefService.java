package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import javax.ejb.Remote;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;

@Remote
public interface AdminCatalogosBenefService {

	/**
	 * Recupera la lista de emisiones que no requieren beneficiarios
	 * @return
	 */
	List<ExcepcionEmisionBenef> getEmisionesSinBenef();
	
	/**
	 * Recupera la lista de horarios para agregar beneficiarios
	 * @return
	 */
	List<HorarioBeneficiario> getHorariosCustodios();
	
	/**
	 * Elimina la emision, de las emisiones que no requieren beneficiarios
	 * @param emisionBenef
	 */
	void eliminarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef);
	
	/**
	 * Elimina el horario, de las lista de horarios de los custodios
	 * @param horarioBeneficiario
	 */
	void eliminarHorario(HorarioBeneficiario horarioBeneficiario);
	
	/**
	 * Inserta una nueva emision sin beneficiarios
	 * @param emisionBenef
	 */
	void agregarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef)throws BusinessException;
	/**
	 * Inserta un nuevo horario del custodio
	 * @param horarioBeneficiario
	 */
	void agregarHorario(HorarioBeneficiario horarioBeneficiario)throws BusinessException;
	/**
	 * Actualiza una emision sin beneficiarios
	 * @param emisionBenef
	 */
	void actualizarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef)throws BusinessException;
	
	/**
	 * actualiza un horario del custodio
	 * @param horarioBeneficiario
	 */
	void actualizarHorario(HorarioBeneficiario horarioBeneficiario)throws BusinessException;
	
	boolean tieneEmisionPorcentajeCero(Long idEmisionDerecho);

	/**
	 * Define si la emision tiene porcentaje cero, hace lo mismo que el servicio tieneEmisionPorcentajeCero(Long),
	 * solo que no ocupa las entidades de hibernate para realizar la consulta a C_SIC_EMISIONES.
	 * @param idEmisionDerecho
	 * @return
	 */
	boolean tieneEmisionPorcentajeCeroOptimizado(Long idEmisionDerecho);

}
