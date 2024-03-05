package com.indeval.portaldali.middleware.services.tarea;

import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioDTO;
import com.indeval.portaldali.middleware.dto.TareaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

public interface TareaService {

	List<TareaDTO> find(CriterioDTO criterio, EstadoPaginacionDTO paginacion, String usuario, String ticket);
	long count(CriterioDTO criterio);

	TareaDTO findById(String idTarea, String ticket);
	void rechazarTarea(String idTarea, String claveUsuario, String ticket);
	void autorizarTarea(String idTarea, String claveUsuario, String ticket);
}
