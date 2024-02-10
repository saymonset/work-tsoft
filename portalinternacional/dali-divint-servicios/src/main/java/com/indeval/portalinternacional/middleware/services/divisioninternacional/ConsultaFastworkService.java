package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.fo.FastworkMessageFO;
import com.indeval.portalinternacional.middleware.servicios.vo.FastworkMessageVO;

public interface ConsultaFastworkService {
	
	public PaginaVO consultaMensajes(FastworkMessageFO fastworkMessagefo, PaginaVO paginaVO, boolean reporte);
	
	public FastworkMessageVO consultaIso(String dbkey);
	
	public byte[] generarReporte(String ext, FastworkMessageFO fastworkMessagefo,  AgenteVO agenteFirmado, PaginaVO paginaReportes, List<String> swiftHeaders);

}
