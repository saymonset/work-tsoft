import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import {useState} from "react"
import {useDispatch, useSelector} from "react-redux"
import {downloadFiles, fetchDataGetRet, showAlert} from "../../../../../../../utils"
import {RespDownloadCsv} from "../../../../../../../model";
import {
    updateCheckGenerarSalidas,
    updateCheckProduccionSalidas,
    updateIsShowLogBoxOuts
} from "../../../../../../../redux";

export const useSalidas = () => {

    const [log, setLog] = useState<string[]>([]);

    const [loadingLogCsv, setLoadingLogCsv] = useState(false)

    const [downloadResponse, setDownloadResponse] = useState<RespDownloadCsv | null>(null);

    const [loadingGenerar, setLoadingGenerar] = useState(false)

    const [loadingProd, setLoadingProd] = useState(false)

    const checkGenerar = useSelector((state: RootState<any, any, any>) =>
        state.CheckGenerarSalidas) as unknown as boolean;

    const checkProduccion = useSelector((state: RootState<any, any, any>) =>
        state.CheckProduccionSalidas) as unknown as boolean;

    const fechaProcesoRV = useSelector((state: RootState<any, any, any>) => 
        state.fechaProcesoRV) as unknown as string;

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;

    const IsShowLogBoxOuts = useSelector((state: RootState<any, any, any>) =>
        state.IsShowLogBoxOuts) as unknown as boolean;

    const dispatch = useDispatch()

    const handleGenerar = async () => {
        dispatch(updateIsShowLogBoxOuts(false))
        if (fechaProcesoRV == '' || fechaProcesoRV == null) {
            await showAlert("warning", "Error", "Por favor ingrese FECHA")
        }
        else if (selectedVectorRV == '' || selectedVectorRV == null) {
            await showAlert("warning", "Error", "Por favor ingrese VECTOR")
        }
        else
        {
            dispatch(updateCheckGenerarSalidas(true))
            setLoadingGenerar(true)
            const response = await fetchDataGetRet(
                "/acciones/proceso-rv/genera-base-accionaria",
                " al salida generar",
                {
                    dFechaVal: fechaProcesoRV,
                    sVector: selectedVectorRV
                }
            )

            setLoadingGenerar(false)
            setDownloadResponse(response);
            setLog(response.body.log);
            dispatch(updateIsShowLogBoxOuts(true))
        }
    }

    const handleProd = async () => {
        dispatch(updateIsShowLogBoxOuts(false))
        if (fechaProcesoRV == '' || fechaProcesoRV == null)
        {
            await showAlert("warning", "Error", "Por favor ingrese FECHA")
        }
        else if (selectedVectorRV == '' || selectedVectorRV == null)
        {
            await showAlert("warning", "Error", "Por favor ingrese VECTOR")
        }
        else
        {
            dispatch(updateCheckProduccionSalidas(true))
            setLoadingProd(true)
            const response = await fetchDataGetRet(
                "/acciones/proceso-rv/copia-produccion",
                " en salida produccion",
                {
                    dFechaVal: fechaProcesoRV,
                    sVector: selectedVectorRV
                }
            )

            setLoadingProd(false)
            setDownloadResponse(response);
            setLog(response.body.log);
            dispatch(updateIsShowLogBoxOuts(true))
        }
    }

    const handleLogCsv = async () => {
        setLoadingLogCsv(true)

        if (downloadResponse) {
            downloadFiles(downloadResponse.body.descargaLog.contenido,
                downloadResponse.body.descargaLog.nombreCompleto,
                'text/csv')
        } else {
            await showAlert("error", "Error", 'No se recibió un archivo válido')
        }

        setLoadingLogCsv(false)
    }

    const handleCheckGenerar = async () => {
        dispatch(updateCheckGenerarSalidas(!checkGenerar))
    }

    const handleCheckProduccion = async () => {
        dispatch(updateCheckProduccionSalidas(!checkProduccion))
    }

    return {
        checkGenerar,
        checkProduccion,
        log,
        IsShowLogBoxOuts,
        loadingGenerar,
        loadingProd,
        loadingLogCsv,
        handleCheckGenerar,
        handleCheckProduccion,
        handleGenerar,
        handleProd,
        handleLogCsv
    }
}