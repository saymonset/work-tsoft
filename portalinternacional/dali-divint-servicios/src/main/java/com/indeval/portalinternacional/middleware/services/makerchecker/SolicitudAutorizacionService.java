package com.indeval.portalinternacional.middleware.services.makerchecker;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.dto.SolicitudAutorizacionDTO;

public interface SolicitudAutorizacionService {

	public long nuevaSolicitud(String claveFluijoOperativo, SolicitudAutorizacionDTO solicitud, String ticket);

	public long nuevaSolicitud(String claveFlujoOperativo, List<SolicitudAutorizacionDTO> solicitudes,
			String descripcion, String ticket);

	List<SolicitudAutorizacionDTO> findByClaveFlujoOperativo(String... clavesFlujoOperativo);

}
