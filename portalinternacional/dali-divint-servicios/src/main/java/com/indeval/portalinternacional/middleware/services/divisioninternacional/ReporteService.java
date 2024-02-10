package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;

public interface ReporteService {
	
	public boolean generaReporteConciliacionAuditoria(List<DetalleConciliacionEfectivoInt> listaDetalleConciliacion);
		
}