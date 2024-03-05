/**
 * 2H Software - Bursatec
 * <p>
 * Sistema de Consulta de Estados de Cuenta
 * <p>
 * Dec 28, 2007
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;



/**
 * Interface que expone los métodos de acceso a la base de datos para las consultas
 * de los detalles del movimiento de saldos.
 *
 * @author Pablo Julián Balderas Méndez
 */
public interface DetalleMovimientoEfectivoDAO {

    /**
     * Obtiene el detalle de un moviento tomando como argumentos el id del registro contable y el tipo
     * de operación
     *
     * @param idRegistroContable     id del registro contable asociado al moviento
     * @param isHistorico            True si se tiene que buscar en los datos historicos, false en caso contrario
     * @param idFolioInstLiquidacion id del folio instruccion de efectivo
     * @return DTO con la información que conforma el detalle
     */
    DetalleMovimientoEfectivoDTO consultarDetalleMovimientoEfectivo(long idRegistroContable, boolean isHistorico, long idFolioInstLiquidacion);

    /**
     * Busca el identificador del registro contable de una operación de valores nombrada que corresponda
     * al registro contable de efectivo en una operación DVP.
     *
     * @param idRegistroContable el identificador del registro contable a consultar.
     * @param isHistorico        True si se tiene que buscar en los datos historicos, false en caso contrario
     * @return el identificador del registro contable de valor nombrada que corresponde.
     */
    long buscarIdRegistroContableValorNombradoDeOperacionDVP(long idRegistroContable, long idInstitucion, boolean isHistorico);


    /**
     * Obtiene el detalle de un moviento tomando como argumentos el id del registro contable y
     * el folio instruccion de Efectivo
     *
     * @param idRegistroContable     id del registro contable asociado al moviento
     * @param idFolioInstLiquidacion id del folio instruccion de efectivo
     * @param isHistorico            True si se tiene que buscar en los datos historicos, false en caso contrario
     * @return DTO con la información que conforma el detalle
     */
    DetalleMovimientoEfectivoDTO consultaDetalleEfectivoPorRegitroFolio(long idRegistroContable, long idFolioInstLiquidacion, boolean isHistorico);


}
