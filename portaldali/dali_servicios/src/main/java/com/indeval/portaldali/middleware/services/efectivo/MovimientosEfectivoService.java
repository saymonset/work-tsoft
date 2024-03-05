package com.indeval.portaldali.middleware.services.efectivo;

import java.util.Map;

import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

public interface MovimientosEfectivoService {

	String guardarOperacion(RetiroEfectivoDTO retiroDTO, Map<String, String> datosFirma);
	String guardarOperacion(TraspasoEfectivoVO traspaso, Map<String, String> datosFirma);
	String guardarOperacion(RetiroEfectivoVO retiro, Map<String, String> datosFirma);
	
	
}
