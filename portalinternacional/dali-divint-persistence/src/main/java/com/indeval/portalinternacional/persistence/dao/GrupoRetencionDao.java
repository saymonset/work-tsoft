package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.GrupoRetencion;

public interface GrupoRetencionDao {

	List<GrupoRetencion> getGruposRetencionByIdDerecho(Long idDerechoCapital);
}
