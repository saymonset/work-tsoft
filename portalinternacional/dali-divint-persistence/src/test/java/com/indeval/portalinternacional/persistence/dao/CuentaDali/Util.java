// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.CuentaDali;

import com.indeval.portalinternacional.middleware.servicios.dto.CuentaEfectivoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoCuentaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoNaturalezaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;

import java.util.Date;

public class Util {

    public static CuentaEfectivoDTO getCriterio() {
        CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
        InstitucionWebDTO institucion = new InstitucionWebDTO();

        institucion.setIdInstitucion(9);
        institucion.setClaveTipoInstitucion("01");
        institucion.setFolio("009");
        institucion.setClaveCasfim("13032");
        institucion.setRazonSocial("VALORES MEXICANOS, CASA DE BOLSA, S. A. DE C.V.");
        institucion.setNombre("CBVALMEX");
        institucion.setIdTipoInstitucion(1);
        institucion.setInterna(false);
        institucion.setClaveSpei(90617);
        institucion.setClaveSpeiBeneficiario(40012);
        institucion.setClabeBeneficiario("012180004458234552");
        institucion.setOperaSiac(true);

        criterio.setTipoCuenta(new TipoCuentaDTO("N", ""));
        criterio.setIdCuenta(0);
        criterio.setNumeroCuenta("-1");
        criterio.setInstitucion(institucion);
        criterio.setTipoCustodia("E");
        criterio.setTipoNaturaleza(new TipoNaturalezaDTO("P", ""));

        return criterio;
    }

}
