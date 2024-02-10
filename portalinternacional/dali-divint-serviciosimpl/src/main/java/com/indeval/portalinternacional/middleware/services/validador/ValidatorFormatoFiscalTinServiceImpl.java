package com.indeval.portalinternacional.middleware.services.validador;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;
import com.indeval.portalinternacional.middleware.servicios.vo.TipoFormato;

public class ValidatorFormatoFiscalTinServiceImpl implements ValidatorFormatoFiscalTinService {
	
	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorFormatoFiscalTinServiceImpl.class);

	/** Metodo para validar si un formato esta autorizado en los ultimos 3 anios, T_BENEFICIARIOS
		@param: Beneficiario
		@return Boolean, Si lo encuentra es un false y no se cobra
						 Si no lo encuentra es true y se inserta el cobro
	*/
	public Boolean validateFormatIsAuthorized(Beneficiario beneficiario){

		return this.getDiffYears(new Date(), beneficiario.getFechaAutorizacion());
		
	}
	
	/** Metodo para validar si un formato esta autorizado en el mismo mes, T_BENEFICIARIOS
	@param: Beneficiario
	@return Boolean, Si lo encuentra es un false y no se cobra
					 Si no lo encuentra es true y se inserta el cobro
	*/
	public Boolean validateFormatIsAuthorizedSameMount(Beneficiario beneficiario){
	
		return this.isSameMount(new Date(), beneficiario.getFechaAutorizacion());
		
	}
	
	/**
	 * Metodo para validar si los campos de nombre, apellidos y rfc son null
	 */
	public Boolean validateFieldsNull(FormatoFiscal formatoFiscal){
		
		if(formatoFiscal.getNombreBeneficiario() == null
				&& formatoFiscal.getApellidoPaternoBeneficiario() == null
				&& formatoFiscal.getApellidoMaternoBeneficiario() == null
				&& formatoFiscal.getTaxID() == null)
			
			return true;
		else
			return false;
	}
	
	public Boolean getDiffYears(Date now, Date ingreso) {

	    this.log.info("Entrando a metodo :: getDiffYears");
	    
	    Calendar actual = Calendar.getInstance();
	    Calendar fechaAutorizado = Calendar.getInstance();
	    actual.setTimeInMillis(now.getTime());
	    fechaAutorizado.setTime(ingreso);
	    
	    int diffYear = actual.get(Calendar.YEAR) - fechaAutorizado.get(Calendar.YEAR);
	    
	    if(diffYear < 0)
	    	return true;
	    
	    int diffMonth = (diffYear * 12 + actual.get(Calendar.MONTH) - fechaAutorizado.get(Calendar.MONTH)) % 12;
	    int diffMonthYear = (diffYear * 12 + actual.get(Calendar.MONTH) - fechaAutorizado.get(Calendar.MONTH)) / 12;
	    int diffDay = actual.get(Calendar.DATE) - fechaAutorizado.get(Calendar.DATE);
	    
	    this.log.info("Diff Years :: " + diffMonthYear + " :: diff Month :: " + diffMonth + " :: diffDays :: " + diffDay);
	    
	    if(diffMonthYear > 3){
	    	return true;
	    }
	    else if(diffMonthYear == 3){
	    	if(diffMonth > 0 || diffDay > 0)
	    		return true;
	    	else
		    	return false;
	    }else
	    	return false;
	    
	}
	
	/**
	 * Metodo para validar si la fecha de autorizacion es del mismo mes a la fecha actual
	 * @param Fecha actual
	 * @param Fecha autorizacion
	 * @return Boolean
	 */
	public Boolean isSameMount(Date now, Date autorizacion) {

	    Calendar actual = Calendar.getInstance();
	    Calendar fechaAutorizado = Calendar.getInstance();
	    actual.setTimeInMillis(now.getTime());
	    fechaAutorizado.setTime(autorizacion);
	    
	    int diffYear = actual.get(Calendar.YEAR) - fechaAutorizado.get(Calendar.YEAR);
	    if(!(diffYear == 0))
	    	return false;
	    
	    int diffMonth = (diffYear * 12 + actual.get(Calendar.MONTH) - fechaAutorizado.get(Calendar.MONTH)) % 12;
	    
	    if(diffMonth == 0)
	    	return true;
    	else
    		return false;
	}
	
	/**
	 * Metodo para obtener el RFC de un formato
	 */
	public FormatoFiscal obtenerRFCFormato(FormatoFiscal formatoFiscalBean, Beneficiario beneficiario){
        TipoFormato tipoFormato = TipoFormato.obtenerInstancia(beneficiario.getTipoFormato());
        String rfc = null;
        String referenceNumber = null;
        switch (tipoFormato) {
            case W8BEN2014:
            // W8BEN2017 es igual que el 2014
            case W8BEN2017:
            case W8BEN:
                rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                referenceNumber = beneficiario.getFormatoW8BEN().getReferenceNumbers();
                break;

            case W9:
            	rfc = beneficiario.getFormatoW9().getSsn();
                break;

            case W8IMY:
                rfc = beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
                referenceNumber = beneficiario.getFormatoW8IMY().getReferenceNumbers();
                break;
                
            case W8BENE:
            case W8BENE2016:
            	rfc = beneficiario.getFormatoW8BENE().getForeingTIN();
            	referenceNumber = beneficiario.getFormatoW8BENE().getReferenceNumber();
                break;
                
            case W8IMY2015:
            case W8IMY2017:
            	rfc = beneficiario.getFormatoW8IMY2015().getGiin();
            	referenceNumber = beneficiario.getFormatoW8IMY2015().getReferenceNumber();
                break;
        }
    	formatoFiscalBean.setTaxID(rfc);
    	formatoFiscalBean.setReferenceNumber(referenceNumber);
    	
    	return formatoFiscalBean;
    	
	}

}