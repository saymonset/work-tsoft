// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;

import java.util.List;

public interface CustodioService {

    Integer getIdCustodioByIdCatbic(Long idCatbic);

    List<Custodio> findAll() ;
}
