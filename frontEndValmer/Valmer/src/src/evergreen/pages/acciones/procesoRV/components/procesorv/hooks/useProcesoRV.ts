import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useState } from "react"
import {useDispatch, useSelector} from "react-redux"
import {downloadFiles, fetchDataGetRet, showAlert} from "../../../../../../../utils"
import { RespDownloadCsv } from "../../../../../../../model"
import {
    updateCheckPrecalIntProcesoRv,
    updateCheckPrecalNacProcesoRv,
    updateShowProcesoRvLog
} from "../../../../../../../redux";

export const useProcesoRV = () => {

    const [loadingDs, setLoadingDs] = useState(false)
    const [loadingPrecalInt, setLoadingPrecalInt] = useState(false)
    const [loadingPrecalNac, setLoadingPrecalNac] = useState(false)
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogCsv, setLoadingLogCsv] = useState(false);
    const [downloadResponse, setDownloadResponse] = useState<RespDownloadCsv | null>(null);

    const checkPrecalInt = useSelector((state: RootState<any, any, any>) =>
        state.CheckPrecalIntProcesoRv) as unknown as boolean;

    const checkPrecalNac = useSelector((state: RootState<any, any, any>) =>
        state.CheckPrecalNacProcesoRv) as unknown as boolean;

    const ShowProcesoRvLog = useSelector((state: RootState<any, any, any>) =>
        state.ShowProcesoRvLog) as unknown as boolean;

    const fechaProcesoRV = useSelector((state: RootState<any, any, any>) =>
        state.fechaProcesoRV) as unknown as string;

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;

    const dispatch = useDispatch()

    const handleDsRv = async () => {
        dispatch(updateShowProcesoRvLog(false))
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
            setLoadingDs(true)

            const response = await fetchDataGetRet(
                "/acciones/proceso-rv/ds-genera-rv-int",
                " al generar DS RV INT",
                {dFechaVal: fechaProcesoRV, sVector: selectedVectorRV}
            )

            setLoadingDs(false)
            setLog(response.body.log);
            dispatch(updateShowProcesoRvLog(true))
        }
    }

    const handlePrecalInt = async () => {
        dispatch(updateShowProcesoRvLog(false))
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
            dispatch(updateCheckPrecalIntProcesoRv(true))
            setLoadingPrecalInt(true)
            const response = await fetchDataGetRet(
                "/acciones/proceso-rv/precal-int",
                " al generar Precal Int",
                {
                    dFechaVal: fechaProcesoRV,
                    sVector: selectedVectorRV
                }
            )
            setLoadingPrecalInt(false)
            setLog(response.body.log)
            dispatch(updateShowProcesoRvLog(true))
        }
    }

    const handlePrecalNac = async () => {
        dispatch(updateShowProcesoRvLog(false))
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
            dispatch(updateCheckPrecalNacProcesoRv(true))
            setLoadingPrecalNac(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/precal-nac",
                    " al generar Precal Nac",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )
                setLoadingPrecalNac(false)
                setDownloadResponse(response);
                setLog(response.body.log)
                dispatch(updateShowProcesoRvLog(true))
            } catch (error) {
                await showAlert("error", "Error", ""+error)
            }
        }
    }

    const downloadFilesFromUrl = () => {
        setLoadingLogCsv(true);

        if(downloadResponse)
        {
            downloadFiles(downloadResponse.body.descargaLog.contenido,
                downloadResponse.body.descargaLog.nombreCompleto,
                'text/csv')
        }
        setLoadingLogCsv(false);
    };


    const handleCheckPrecalNac = () => {
        dispatch(updateCheckPrecalNacProcesoRv(!checkPrecalNac))
    }

    const handleCheckPrecalInt = () => {
        dispatch(updateCheckPrecalIntProcesoRv(!checkPrecalInt))
    }

    return {
        checkPrecalInt,
        checkPrecalNac,
        loadingDs,
        loadingPrecalInt,
        loadingPrecalNac,
        log,
        loadingLogCsv,
        ShowProcesoRvLog,
        handleCheckPrecalNac,
        handleCheckPrecalInt,
        downloadFilesFromUrl,
        handleDsRv,
        handlePrecalInt,
        handlePrecalNac
    }
}