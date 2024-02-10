// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.consultaSaldosEfectivo;

import com.indeval.portalinternacional.middleware.servicios.dto.*;

import java.util.Date;

public class Util {

    public static SaldoEfectivoDTO getCriterio() {
        SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
        BovedaDto boveda = new BovedaDto();
        CuentaEfectivoDTO cuenta = new CuentaEfectivoDTO();
        InstitucionWebDTO institucion = new InstitucionWebDTO();

        boveda.setIdBoveda(13);

        institucion.setIdInstitucion(9);
        institucion.setClaveTipoInstitucion("01");
        institucion.setFolio("009");
        institucion.setClaveCasfim("13032");
        institucion.setRazonSocial("VALORES MEXICANOS, CASA DE BOLSA, S. A. DE C.V.");
        institucion.setNombre("CBVALMEX");
        institucion.setIdTipoInstitucion(1);
        institucion.setClaveSpei(90617);
        institucion.setClaveSpeiBeneficiario(40012);
        institucion.setClabeBeneficiario("012180004458234552");
        institucion.setOperaSiac(true);

        cuenta.setTipoCuenta(new TipoCuentaDTO("N", null));
        cuenta.setIdCuenta(0);
        cuenta.setNumeroCuenta("-1");
        cuenta.setInstitucion(institucion);
        cuenta.setTipoCustodia("E");
        cuenta.setTipoNaturaleza(new TipoNaturalezaDTO("P", null));

        criterio.setDivisa(new DivisaDTO(3L));
        criterio.setBoveda(boveda);
        criterio.setCuenta(cuenta);
        criterio.setIdEstadoSaldo(0);
        criterio.setSaldo(0.0);
        criterio.setSaldoNoDisponible(0.0);
        criterio.setFecha(new Date());

        return criterio;
    }

}
