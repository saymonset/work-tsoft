package com.indeval.portaldali.middleware.services.conciliacionModulos;

import java.util.List;

import com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosDTO;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosExcelDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriteriosConciliacionModulosDTO;

public interface ConciliacionModulosService {

	List<ConciliacionModulosDTO> obtenConciliacionModulos(CriteriosConciliacionModulosDTO criterios);

	ConciliacionModulosExcelDTO generaExcelConciliacionModulos(CriteriosConciliacionModulosDTO criterios);

	void reenviaMensajes(List<ConciliacionModulosDTO> reenvios, FlujoConciliacionModulosEnum flujo, String claveUsuario);

	String reenviaRete(List<InstruccionEfectivoDTO> retes, String idUsuario, String ip);

}
