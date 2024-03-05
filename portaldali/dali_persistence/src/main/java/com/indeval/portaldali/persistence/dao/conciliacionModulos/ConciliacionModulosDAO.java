package com.indeval.portaldali.persistence.dao.conciliacionModulos;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriteriosConciliacionModulosDTO;

public interface ConciliacionModulosDAO extends BaseDao {

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMovSlv(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMosSlv(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMatchMov(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMavSlv(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMav(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMov(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionSlvMos(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMatch(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMos(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionAeMav(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMovAs(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMosAs(CriteriosConciliacionModulosDTO criterios);

	List<ConciliacionModulosDTO> obtenRegistrosConciliacionMavAs(CriteriosConciliacionModulosDTO criterios);

}
