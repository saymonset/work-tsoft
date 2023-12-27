import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useState } from "react"
import {useDispatch, useSelector} from "react-redux"
import {downloadFiles, fetchDataGetRet, showAlert} from "../../../../../../../utils"
import { RespDownloadCsv } from "../../../../../../../model"
import {
    updateCheckDescargaCateRv,
    updateCheckFormateaCateRv,
    updateShowDescargaCateRv,
    updateShowFormateaCateRv
} from "../../../../../../../redux";

export const useCateRv = () => {

    const [loading, setLoading] = useState(false)
    const [loadingFormat, setLoadingFormat] = useState(false)
    const [downloadResponse, setDownloadResponse] = useState<RespDownloadCsv | null>(null);
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogCsv, setLoadingLogCsv] = useState(false);

    const checkDescarga = useSelector((state: RootState<any, any, any>) =>
        state.CheckDescargaCateRv) as unknown as boolean;

    const checkFormatea = useSelector((state: RootState<any, any, any>) =>
        state.CheckFormateaCateRv) as unknown as boolean;

    const fechaProcesoRV = useSelector((state: RootState<any, any, any>) => 
        state.fechaProcesoRV) as unknown as string;

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;
    
    const ShowFormateaCateRv = useSelector((state: RootState<any, any, any>) =>
        state.ShowFormateaCateRv) as unknown as boolean;

    const ShowDescargaCateRv = useSelector((state: RootState<any, any, any>) =>
        state.ShowDescargaCateRv) as unknown as string;

    const dispatch = useDispatch()

    const handleDescarga = async () => {
        dispatch(updateShowDescargaCateRv(false))
        dispatch(updateShowFormateaCateRv(false))
        if (fechaProcesoRV == '' || fechaProcesoRV == null)
        {
            await showAlert('warning', "Error", "Por favor ingrese FECHA")
        } 
        else if (selectedVectorRV == '' || selectedVectorRV == null) 
        {
            await showAlert('warning', "Error", "Por favor ingrese VECTOR")
        } 
        else 
        {
            dispatch(updateCheckDescargaCateRv(true))
            setLoading(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/descarga-caterv",
                    " se generó un error en la descarga",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )
                setDownloadResponse(response);
                setLog(response.body.log);
                dispatch(updateShowDescargaCateRv(true))
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)
            }
            finally {
                setLoading(false);
            }
        }
    }

    const handleFormatea = async () => {
        dispatch(updateShowDescargaCateRv(false))
        dispatch(updateShowFormateaCateRv(false))
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
            dispatch(updateCheckFormateaCateRv(true))
            setLoadingFormat(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/formatea-caterv",
                    " se generó un error al formatear",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )

                setDownloadResponse(response);
                setLog(response.body.log);
                dispatch(updateShowFormateaCateRv(true))
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)
            }
            finally {
                setLoadingFormat(false);
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

    const handleCheckDescargaLog = () => {
        dispatch(updateCheckDescargaCateRv(!checkDescarga))
    }

    const handleCheckFormateaLog = () => {
        dispatch(updateCheckFormateaCateRv(!checkFormatea))
    }

    return {
        checkDescarga,
        checkFormatea,
        loading,
        loadingFormat,
        ShowDescargaCateRv,
        log,
        loadingLogCsv,
        ShowFormateaCateRv,
        handleCheckDescargaLog,
        handleCheckFormateaLog,
        downloadFilesFromUrl,
        handleDescarga,
        handleFormatea
    }
}