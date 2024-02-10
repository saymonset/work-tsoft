package com.indeval.portalinternacional.middleware.services.util;

import java.util.Comparator;

import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;

public class ConfirmacionEfectivoComparator implements Comparator<ConfirmacionEfectivo>{

	public int compare(ConfirmacionEfectivo o1, ConfirmacionEfectivo o2) {
		int resultado = 0;
		
		if(o1.getCantidad() != null && o2.getCantidad() != null){
			resultado = o1.getCantidad().compareTo(o2.getCantidad());
		}
		return resultado;
	}

}
