// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Date;
import java.util.List;

public interface DiasInhabilesDivisasService {
    List<Date> getDiasInhabilesByIdDivisa(Long idDivisa);
}
