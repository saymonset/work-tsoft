package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionDivisasVO;

import java.util.List;

public interface ConciliacionDivisasIntService {
    List<ConciliacionDivisasVO> getAllBy(Integer idDivisa, Integer idBoveda);
}
