package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import java.util.List;

import com.indeval.portaldali.middleware.dto.criterio.CriterioVencimientoReportosDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;

public interface ReportosDAO {
	long countReportos(CriterioVencimientoReportosDTO criterio);

	List<ConsultaOperacionesMatch> findReportos(CriterioVencimientoReportosDTO criterio);
}
