package com.indeval.portalinternacional.persistence.dao.makercheker;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.makercheker.SolicitudAutorizacion;

public interface SolicitudAutorizacionDao extends BaseDao {

	public void save(List<SolicitudAutorizacion> solicitud);
	
	public SolicitudAutorizacion findById(long id);
	public List<SolicitudAutorizacion> findByIdProcesoAutorizacion(long idProceso);
	public List<SolicitudAutorizacion> findByClaveFlujoOperativo(String[] clavesFlujoOperativo);
	public int actualizaEstatus(long idSolicitud, String estatusRechazado);
}
