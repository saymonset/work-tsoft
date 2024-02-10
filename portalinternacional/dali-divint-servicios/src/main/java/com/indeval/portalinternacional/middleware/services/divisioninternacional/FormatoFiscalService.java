package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;

public interface FormatoFiscalService {
	
	/**
	 * Prepara lo datos para un formato fiscal
	 * @param beneficiario
	 * @return
	 */
	public List<FormatoFiscal> prepareFormatoFiscalTin(Beneficiario beneficiario);
	
	/**
	 * Prepara lo datos para un formato fiscal cuando se asigna una institucion
	 * @param beneficiario
	 * @param institucion
	 * @return
	 */
	public FormatoFiscal prepareTinCobroInstitucion(Beneficiario beneficiario, Institucion institucionAsignada);

	/**
	 * Prepara lo datos para un formato fiscal cuando se elimina una institucion
	 * @param beneficiario
	 * @param institucion
	 * @return
	 */
	public FormatoFiscal prepareTinCobroByBeneficiarioIdInstitucion(Beneficiario beneficiario, Long idInstitucion);
	
	/**
	 * Valida los tipos validos para formato fiscal
	 * @param listFormatoFiscal
	 * @return
	 */
	public List<FormatoFiscal> validateFormatosFiscales(List<FormatoFiscal> listFormatoFiscal);
	
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param List<FormatoFiscal> listFormatoFiscalTin
     * @param Beneficiario beneficiario
     */
    public void saveFormatoFiscalTin(List<FormatoFiscal> listFormatoFiscalTin, Beneficiario beneficiario);
    
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param FormatoFiscal listFormatoFiscalTin
     */
    public void guardaTinFormatoFiscal(FormatoFiscal formatoFiscalTin);
    
    /**
     * Metodo para actualizar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param List<FormatoFiscal> listFormatoFiscalTin
     */
    public void updateTinFormato(FormatoFiscal formatoFiscalTin);
    
    /**
     * Metodo para actualizar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param FormatoFiscal listFormatoFiscalTin
     */
    public void actualizaFormatoFiscal(FormatoFiscal formatoFiscalTin);
    
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * cuando al asignar una institucion se selecciona un check para generar TIN de cobro
     * @param List<FormatoFiscal> listFormatoFiscalTin
     */
    public void generarTinCobro(FormatoFiscal formatoFiscal, Boolean isCobro);

    /**
     * Metodo para validar si el formato fiscal Tin ya fue insertado
     * @param formatoFiscalBean
     * @return Boolean
     */
    public Boolean validateExistsFormatoFiscalTin(FormatoFiscal formatoFiscalBean);
    
    /**
     * Metodo para obtener las instituciones asignadas a un beneficiario
     * @param idBeneficiario
     * @param idInstitucion
     * @return
     */
    public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion);
    
    /**
     * Metodo para actualizar cobro en tabla: T_BENEFICIARIOS_INSTITUCION
     * @param formatoFiscalTin
     */
	public void actualizaBeneficiarioInstitucion(FormatoFiscal formatoFiscalTin);

}
