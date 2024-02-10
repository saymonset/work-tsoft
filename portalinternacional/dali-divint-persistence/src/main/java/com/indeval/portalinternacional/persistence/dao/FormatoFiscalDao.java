package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;

public interface FormatoFiscalDao extends BaseDao {
	
	/**
	 * Valida si el registro ya existe en tabla: T_FORMATOS_FISCALES_ING
	 * @param formatoFiscalBean
	 * @return Long
	 */
	public Long validateExistsFormatoFiscalTin(FormatoFiscal formatoFiscalBean);
	
	/**
	 * Valida si el tin ya existe en tabla: T_FORMATOS_FISCALES_ING
	 * @param formatoFiscalBean
	 * @return FormatoFiscal
	 */
	public FormatoFiscal getTinByBeneficiciarioInstitutionTin(FormatoFiscal formatoFiscalBean);
	
	/**
	 * Metodo para obtener lista de fecha de operacion
	 * @param FormatoFiscal
	 * @return List<FormatoFiscal>
	 */
	public List<FormatoFiscal> getListDateOperation(final FormatoFiscal formatoFiscal);
	
	/**
	 * Busca un formato fiscal
	 * @param idFormatoFiscal
	 * @return FormatoFiscal
	 */
	public FormatoFiscal findFormatoFiscal(FormatoFiscal formatoFiscal);
	
    /**
     * Metodo para obtener las instituciones asignadas a un beneficiario
     * @param idBeneficiario
     * @param idInstitucion
     * @return
     */
    public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion);
    
    /**
     * Metodo para actualizar un formato fiscal
     * @param formatoFiscal
     * @return
     */
    public void updateExtraerFormato(FormatoFiscal formatoFiscal);

}
