package com.indeval.portalinternacional.middleware.services.validador;

import java.util.Date;

import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;

public interface ValidatorFormatoFiscalTinService {
	
	/** Metodo para validar si un formato esta autorizado en los ultimos 3 anios, T_BENEFICIARIOS
	 * @param: Beneficiario
	 * @return Boolean
	 */
	public Boolean validateFormatIsAuthorized(Beneficiario beneficiario);
    
	/**
	 * Metodo para obtener el RFC de un formato
	 * @param formatoFiscalBean
	 * @param beneficiario
	 * @retrun FormatoFiscal
	 */
    public FormatoFiscal obtenerRFCFormato(FormatoFiscal formatoFiscalBean, Beneficiario beneficiario);
    
    /**
     * Metodo para comprar 2 fechas
     * @param now
     * @param autorizacion
     * @return
     */
    public Boolean getDiffYears(Date now, Date autorizacion);

}
