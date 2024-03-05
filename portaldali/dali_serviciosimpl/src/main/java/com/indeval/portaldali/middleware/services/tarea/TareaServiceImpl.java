package com.indeval.portaldali.middleware.services.tarea;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.CriterioDTO;
import com.indeval.portaldali.middleware.dto.TareaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.workflow.vo.TaskVo;


public class TareaServiceImpl implements TareaService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private WorkflowClientService workflowClientService;
	
	
	@Override
	public List<TareaDTO> find(CriterioDTO criterio, EstadoPaginacionDTO paginacion, String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("find");
		
		List<TaskVo> tasks = workflowClientService.findTasksByUsername(claveUsuario, ticket);
		
		return TaskUtil.entityToDto(tasks);
	}

	@Override
	public long count(CriterioDTO criterio) {
		if(logger.isTraceEnabled())logger.trace("count");
		
		//httpClientHelper.countTasksByUsername("authWf01_1");
		return 0;
	}

	
	@Override
	public TareaDTO findById(String idTarea, String ticket) {
		if(logger.isTraceEnabled())logger.trace("findById");
		
		TaskVo task = workflowClientService.findTaskById(idTarea, ticket);
		return TaskUtil.entityToDto(task);
	}

	@Override
	public void rechazarTarea(String idTarea, String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("rechazarTarea");
		workflowClientService.completeTask(idTarea, false, claveUsuario, ticket);
	}

	@Override
	public void autorizarTarea(String idTarea, String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("autorizarTarea");
		workflowClientService.completeTask(idTarea, true, claveUsuario, ticket);
	}

	public WorkflowClientService getWorkflowClientService() {
		return workflowClientService;
	}

	public void setWorkflowClientService(WorkflowClientService workflowClientService) {
		this.workflowClientService = workflowClientService;
	}
}

class TaskUtil{
	
	public static List<TareaDTO> entityToDto(List<TaskVo> tasks){
		List<TareaDTO> result = new ArrayList<>();
		
		for(TaskVo vo: tasks) {
			result.add(entityToDto(vo));
		}
		
		return result;
	}
	
	public static TareaDTO entityToDto(TaskVo vo) {
		TareaDTO dto = new TareaDTO();
		
		if(vo != null) {
			dto.setIdTarea(vo.getId());
			dto.setNombreTarea(vo.getName());
			dto.setDescripcionTarea(vo.getDescription());
			dto.setFechaTarea(vo.getCreated());
			
			dto.setFlujoOperativo(vo.getWorkflowName());
			dto.setSolicitante(vo.getInitiator());
			dto.setAplicacion(vo.getApplication());
			dto.setDetalle(vo.getFullDescription());
			
			
		}
		
		return dto;
	}
}
