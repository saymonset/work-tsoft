// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.movimientoEfectivoInternacional;

import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Util {

    public static MovimientoEfectivoInternacionalVO getMovimientoDeposito() {
        MovimientoEfectivoInternacionalVO deposito = new MovimientoEfectivoInternacionalVO();
        DivisaDTO divisaDTO = new DivisaDTO();
        divisaDTO.setId(3);
        divisaDTO.setIdString("3");
        divisaDTO.setClaveAlfabetica("USD");

        Date fechaLiq = new Date();
        deposito.setParticipante("1009");
        deposito.setIdCuenta(3909L);
        deposito.setIdInstitucion(9);
        deposito.setCuenta("2000");
        deposito.setNombreInstitucion("CBVALMEX");
        deposito.setTipoMovimiento("0");
        deposito.setDivisa(divisaDTO);
        deposito.setBoveda(new BovedaDto(13, null, "E-EUROCLE", null, null));
        deposito.setSaldoDisponible(2.84205282238E9);
        deposito.setImporteTraspasar(888.0);
        deposito.setSaldoEfectivo(2.84205371038E9);
        deposito.setFechaLiquidacion(fechaLiq);
        deposito.setReferenciaRelacionada("Ej Ref Rel");
        deposito.setTipoMensaje("202");
        deposito.setFolioControl(637L);
        deposito.setIdCatbic(5L);
        return deposito;
    }

    public static MovimientoEfectivoInternacionalVO getMovimientoRetiro() {
        MovimientoEfectivoInternacionalVO retiro = new MovimientoEfectivoInternacionalVO();
        DivisaDTO divisaDTO = new DivisaDTO();
        divisaDTO.setId(3);
        divisaDTO.setIdString("3");
        divisaDTO.setClaveAlfabetica("USD");

        Date fechaLiq = new Date();
        retiro.setParticipante("1009");
        retiro.setIdCuenta(3909L);
        retiro.setIdInstitucion(9);
        retiro.setCuenta("2000");
        retiro.setNombreInstitucion("CBVALMEX");
        retiro.setTipoMovimiento("1");
        retiro.setDivisa(divisaDTO);
        retiro.setBoveda(new BovedaDto(13, null, "E-EUROCLE", null, null));
        retiro.setSaldoDisponible(2.84204818388E9);
        retiro.setImporteTraspasar(555.0);
        retiro.setSaldoEfectivo(2.84204762888E9);
        retiro.setFechaLiquidacion(fechaLiq);
        retiro.setReferenciaNumerica("531531");
        retiro.setReferenciaRelacionada("135asd");
        retiro.setNotasComentarios("Nota de prueba");
        retiro.setTipoMensaje("202");
        retiro.setIntermediaryOption("56D");
        retiro.setIntermediaryValue("Intermediary value");
        retiro.setIntermediaryNameAddress("Intermediary address");
        retiro.setAccountOption("57D");
        retiro.setAccountValue("Account value");
        retiro.setAccountNameAddress("Account name address");
        retiro.setBeneficiaryOption("58D");
        retiro.setBeneficiaryValue("3656");
        retiro.setBeneficiaryNameAddress("INTERNACIONAL, SEGURIDAD, AMBIENTE 12345678901234567890123456789012345.                                  -");
        retiro.setFolioControl(639L);
        retiro.setSeleccionadoAutorizar(false);
        retiro.setSeleccionadoLiberar(false);
        retiro.setSeleccionadoReenviar(false);
        retiro.setSeleccionadoCancelar(false);
        retiro.setIdCatbic(5L);
        return retiro;
    }

    public static CuentaTransitoria obtenerCuentaTransitoriaEsperada(
            MovimientoEfectivoInternacionalVO movimientoEfectivoInternacionalVO) {
        CuentaTransitoria cuentaTransitoria = new CuentaTransitoria();
        cuentaTransitoria.setIdCuentaTransitoria(1);

        DivisaInt divisa = new DivisaInt();
        divisa.setIdDivisa(
                new BigInteger(String.valueOf(movimientoEfectivoInternacionalVO.getDivisa().getId())));
        divisa.setClaveAlfabetica(movimientoEfectivoInternacionalVO.getDivisa().getClaveAlfabetica());
        divisa.setDescripcion(movimientoEfectivoInternacionalVO.getDivisa().getDescripcion());
        cuentaTransitoria.setIdDivisa(divisa);

        Custodio custodio = new Custodio();
        custodio.setId(2);
        custodio.setNombreCorto("EUROCLE");
        custodio.setDescripcion("EUROCLEAR BANK");
        custodio.setCodigoBanco("MGTC");
        cuentaTransitoria.setIdCustodio(custodio);

        cuentaTransitoria.setFolioRelacionado(
                String.valueOf(movimientoEfectivoInternacionalVO.getFolioControl()));
        cuentaTransitoria.setMonto(
                new BigDecimal(movimientoEfectivoInternacionalVO.getSaldoEfectivo().toString()));

        return cuentaTransitoria;
    }
}
