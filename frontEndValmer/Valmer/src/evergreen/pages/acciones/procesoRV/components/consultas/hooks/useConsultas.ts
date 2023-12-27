import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useState } from "react";
import {useDispatch, useSelector} from "react-redux"
import {downloadFiles, fetchDataGetRet, showAlert} from "../../../../../../../utils";
import { useChangeFileAcc } from "./useChangeFileAcc";
import { RespDownloadCsv } from "../../../../../../../model";
import {updateShowIndicadoConsultas} from "../../../../../../../redux";

export const useConsultas = () => {

    const {
        loadingCargaArchRv,
        selectedFile,
        setSelectedFile,
        setFileBase64,
        handleCarge
    } = useChangeFileAcc()

    const [loadingInd, setLoadingInd] = useState(false)
    const [downloadResponse, setDownloadResponse] = useState<RespDownloadCsv | null>(null);
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogCsv, setLoadingLogCsv] = useState(false);

    const fechaProcesoRV = useSelector((state: RootState<any, any, any>) =>
        state.fechaProcesoRV) as unknown as string;

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;

    const ShowIndicadoConsultas = useSelector((state: RootState<any, any, any>) =>
        state.ShowIndicadoConsultas) as unknown as boolean;

    const dispatch = useDispatch()

    const handleInd = async () => {
        dispatch(updateShowIndicadoConsultas(false))
        if (fechaProcesoRV == '' || selectedVectorRV == null)
        {
            await showAlert("warning", "Error", "Por favor ingrese FECHA")
        }
        else if (selectedVectorRV == '' || selectedVectorRV == null)
        {
            await showAlert("warning", "Error", "Por favor ingrese VECTOR")
        }
        else
        {
            setLoadingInd(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/consulta-indicado",
                    " al consultar Indicado",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )
                setLoadingInd(false)
                setDownloadResponse(response);
                setLog(response.body.log);
                dispatch(updateShowIndicadoConsultas(true))
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)
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

    return {
        ShowIndicadoConsultas,
        log,
        loadingLogCsv,
        loadingInd,
        loadingCargaArchRv,
        selectedFile,
        setSelectedFile,
        setFileBase64,
        handleCarge,
        handleInd,
        downloadFilesFromUrl
    }
}