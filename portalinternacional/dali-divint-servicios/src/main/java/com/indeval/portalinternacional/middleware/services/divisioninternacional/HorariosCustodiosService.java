// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public interface HorariosCustodiosService {

    Map<String, String> getRangoHorariosPorCustodio(Integer idCustodio);
}
