package com.indeval.portalinternacional.middleware.services.makerchecker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.SolicitudAutorizacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.makercheker.SolicitudAutorizacion;
import com.indeval.portalinternacional.persistence.dao.makercheker.SolicitudAutorizacionDao;

public class SolicitudAutorizacionServiceImpl implements SolicitudAutorizacionService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private WorkflowClientService workflowClientService;
	private SolicitudAutorizacionDao solicitudAutorizacionDao;
	
	@Override
	public List<SolicitudAutorizacionDTO> findByClaveFlujoOperativo(String... clavesFlujoOperativo){
		if(LOGGER.isTraceEnabled())LOGGER.trace("findByClaveFlujoOperativo");
		if(clavesFlujoOperativo == null || clavesFlujoOperativo.length < 1 )throw new IllegalArgumentException("al menos una clave de flujo operativo es requerida.");
		List<SolicitudAutorizacion> solicitudesPendientes = solicitudAutorizacionDao.findByClaveFlujoOperativo(clavesFlujoOperativo);
		
		List<SolicitudAutorizacionDTO> result = new ArrayList<>();
		
		for(SolicitudAutorizacion solicitud: solicitudesPendientes) {
			SolicitudAutorizacionDTO dto= new SolicitudAutorizacionDTO();
			BeanUtils.copyProperties(solicitud, dto);
			
			result.add(dto);
		}
		
		return result;
	}
	
	@Override
	public long nuevaSolicitud(String claveFlujoOperativo, SolicitudAutorizacionDTO solicitudDto, String ticket) {
		if(LOGGER.isTraceEnabled())
			LOGGER.trace("SolicitudAutorizacionServiceImpl :: nuevaSolicitud");
		if(solicitudDto == null)throw new IllegalArgumentException("solicitudDto no puede ser null.");
		if(StringUtils.isBlank(claveFlujoOperativo))throw new IllegalArgumentException("claveFlujo es requerido.");
		
		SolicitudAutorizacion solicitud = new SolicitudAutorizacion();
		BeanUtils.copyProperties(solicitudDto, solicitud);
		
		// String 
		long idProcesoAutorizacion = workflowClientService.enviarSolicitud(claveFlujoOperativo, solicitud.getIdentificadorNegocio(), solicitud.getDescripcion(), ticket);
		LOGGER.info("SolicitudAutorizacionServiceImpl :: idProcesoAutorizacion: " + idProcesoAutorizacion);
		if (idProcesoAutorizacion > 0) {
			solicitud.setClaveFlujoOperativo(claveFlujoOperativo);
			solicitud.setEstatus(Constantes.ESTATUS_SOLICITUD_PENDIENTE);
			solicitud.setFechaSolicitud(new Date());
			solicitud.setIdProcesoAutorizacion(idProcesoAutorizacion);
			solicitudAutorizacionDao.save(solicitud);
		} else {
			LOGGER.error("SolicitudAutorizacionServiceImpl :: No se pudo generar la solicitud.");
		}
		
		return idProcesoAutorizacion;
	}
	
	
	@Override
	public long nuevaSolicitud(String claveFlujoOperativo, List<SolicitudAutorizacionDTO> solicitudes, String descripcionGral, String ticket) {
		if(LOGGER.isTraceEnabled())
			LOGGER.trace("SolicitudAutorizacionServiceImpl :: nuevaSolicitud");
		if(solicitudes == null || solicitudes.isEmpty())throw new IllegalArgumentException("al menos una solicitud es requerida.");
		if(StringUtils.isBlank(claveFlujoOperativo))throw new IllegalArgumentException("claveFlujo es requerido.");
		
		List<SolicitudAutorizacion> result = new ArrayList<>();
		
		long idProcesoAutorizacion = workflowClientService.enviarSolicitud(claveFlujoOperativo,"VARIOS IDS", descripcionGral,  ticket);
		
		if (idProcesoAutorizacion > 0) {
			for(SolicitudAutorizacionDTO solicitudDto: solicitudes) {
				SolicitudAutorizacion solicitud = new SolicitudAutorizacion();
				BeanUtils.copyProperties(solicitudDto, solicitud);
				solicitud.setClaveFlujoOperativo(claveFlujoOperativo);
				solicitud.setIdProcesoAutorizacion(idProcesoAutorizacion);
				solicitud.setEstatus(Constantes.ESTATUS_SOLICITUD_PENDIENTE);
				solicitud.setFechaSolicitud(new Date());
				result.add(solicitud);
			}
			
			solicitudAutorizacionDao.save(result);
		}
		
		return idProcesoAutorizacion;
	}


	public void setSolicitudAutorizacionDao(SolicitudAutorizacionDao solicitudAutorizacionDao) {
		this.solicitudAutorizacionDao = solicitudAutorizacionDao;
	}

	public void setWorkflowClientService(WorkflowClientService workflowClientService) {
		this.workflowClientService = workflowClientService;
	}

	/**
	 * @return the workflowClientService
	 */
	public WorkflowClientService getWorkflowClientService() {
		return workflowClientService;
	}

	/**
	 * @return the solicitudAutorizacionDao
	 */
	public SolicitudAutorizacionDao getSolicitudAutorizacionDao() {
		return solicitudAutorizacionDao;
	}

}
