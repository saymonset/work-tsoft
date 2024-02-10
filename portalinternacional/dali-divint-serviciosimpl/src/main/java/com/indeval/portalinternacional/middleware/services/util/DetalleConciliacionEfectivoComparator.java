package com.indeval.portalinternacional.middleware.services.util;

import java.util.Comparator;

import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;

public class DetalleConciliacionEfectivoComparator implements Comparator<DetalleConciliacionEfectivoInt>{

	public int compare(DetalleConciliacionEfectivoInt o1, DetalleConciliacionEfectivoInt o2) {
		int resultado = 0;
		
		if(o1.getMonto() != null && o2.getMonto() != null){
			resultado = o1.getMonto().compareTo(o2.getMonto());
		}
		return resultado;
	}

}
