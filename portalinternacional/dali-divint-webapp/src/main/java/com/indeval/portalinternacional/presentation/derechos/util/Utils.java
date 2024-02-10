package com.indeval.portalinternacional.presentation.derechos.util;

import java.math.BigDecimal;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;

public class Utils {

	public static Float calculaProporcion(FactorSimulacionMav simulacionMav){
    	BigDecimal numerador = null;
    	BigDecimal divisor = null;
    	Float proporcionCalculada = null;
    	BigDecimal proporcion = null;
    	BigDecimal porcentajeRet = null;
    	BigDecimal fee = null;
    	BigDecimal proporcionRes = null;
    	if(simulacionMav.getFee() == null){
    		proporcionCalculada = simulacionMav.getProporcion();
    	}else{
    		proporcion = new BigDecimal(simulacionMav.getProporcion());
    		porcentajeRet = new BigDecimal(simulacionMav.getPorcentajeRetencion().floatValue()/100);
    		fee = new BigDecimal(simulacionMav.getFee());
    		numerador = (proporcion.subtract((proporcion.multiply(porcentajeRet)))).subtract(fee);
    		divisor = Constantes.BIG_DECIMAL_UNO.subtract(porcentajeRet);     		
    		proporcionRes = numerador.divide(divisor,Constantes.PRECICION_PROPORCION,BigDecimal.ROUND_UP);
    		proporcionCalculada = Float.valueOf(proporcionRes.floatValue());
    	}
    	
    	return proporcionCalculada;
    }
}
