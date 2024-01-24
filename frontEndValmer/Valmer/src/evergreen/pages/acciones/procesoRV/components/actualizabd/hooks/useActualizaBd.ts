import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import {useState} from "react";
import {useDispatch, useSelector} from "react-redux"
import {downloadFiles, fetchDataGetRet, showAlert} from "../../../../../../../utils";
import { RespDownloadCsv } from "../../../../../../../model";
import {
    updateCheckActualiza1bActualizaBd,
    updateCheckInternacionalActualizaBd,
    updateCheckNacionalActualizaBd,
    updateShowActualiza1BActBd,
    updateShowInternacionalActBd,
    updateShowNacionalActBd
} from "../../../../../../../redux";

export const useActualizaBd = () => {

    const [loadingInter, setLoadingInter] = useState(false)
    const [loadingNac, setLoadingNac] = useState(false)
    const [loadingAct1B, setLoadingAct1B] = useState(false)
    const [downloadResponse, setDownloadResponse] = useState<RespDownloadCsv | null>(null);
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogCsv, setLoadingLogCsv] = useState(false);

    const checkInternacional = useSelector((state: RootState<any, any, any>) =>
        state.CheckInternacionalActualizaBd) as unknown as boolean;

    const checkNacional = useSelector((state: RootState<any, any, any>) =>
        state.CheckNacionalActualizaBd) as unknown as boolean;

    const checkActualiza1b = useSelector((state: RootState<any, any, any>) =>
        state.CheckActualiza1bActualizaBd) as unknown as boolean;

    const ShowActualiza1BActBd = useSelector((state: RootState<any, any, any>) =>
        state.ShowActualiza1BActBd) as unknown as boolean;

    const ShowNacionalActBd = useSelector((state: RootState<any, any, any>) =>
        state.ShowNacionalActBd) as unknown as boolean;

    const ShowInternacionalActBd = useSelector((state: RootState<any, any, any>) =>
        state.ShowInternacionalActBd) as unknown as boolean;

    const fechaProcesoRV = useSelector((state: RootState<any, any, any>) =>
        state.fechaProcesoRV) as unknown as string;

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;

    const dispatch = useDispatch()

    const handleInter = async () => {

        dispatch(updateShowNacionalActBd(false));
        dispatch(updateShowInternacionalActBd(false));
        dispatch(updateShowActualiza1BActBd(false));

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
            dispatch(updateCheckInternacionalActualizaBd(true));
            setLoadingInter(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/actualiza-base-accionaria-int",
                    " al actualizar base accionaria internacional",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )

                setDownloadResponse(response);
                setLog(response.body.log)
                dispatch(updateShowInternacionalActBd(true));
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)
            }
            finally {
                setLoadingInter(false);
            }
        }
    }

    const handleNac = async () => {
        dispatch(updateShowNacionalActBd(false));
        dispatch(updateShowInternacionalActBd(false));
        dispatch(updateShowActualiza1BActBd(false));
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
            dispatch(updateCheckNacionalActualizaBd(true));
            setLoadingNac(true)
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/actualiza-base-accionaria-nac",
                    " al actualizar base accionaria nacional",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                )

                setDownloadResponse(response);
                setLog(response.body.log)
                dispatch(updateShowNacionalActBd(true));
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)

            }
            finally {
                setLoadingNac(false);
            }
        }
    }

    const handleAct1B = async () => {
        dispatch(updateShowNacionalActBd(false));
        dispatch(updateShowInternacionalActBd(false));
        dispatch(updateShowActualiza1BActBd(false));
        if (fechaProcesoRV == '' || fechaProcesoRV == null) {
            await showAlert("warning", "Error", "Por favor ingrese FECHA");
        } else if (selectedVectorRV == '' || selectedVectorRV == null) {
            await showAlert("warning", "Error", "Por favor ingrese VECTOR");
        } else {

            dispatch(updateCheckActualiza1bActualizaBd(true));
            setLoadingAct1B(true);
            try {
                const response = await fetchDataGetRet(
                    "/acciones/proceso-rv/actualiza-1B",
                    " al actualizar base accionaria 1B",
                    {
                        dFechaVal: fechaProcesoRV,
                        sVector: selectedVectorRV
                    }
                );

                setDownloadResponse(response);
                setLog(response.body.log)
                dispatch(updateShowActualiza1BActBd(true));
            } catch (error) {
                await showAlert("error", "Error", 'Error durante la solicitud: '+error)
            }
            finally {
                setLoadingAct1B(false);
            }
        }
    };
    
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

    const handleCheckInternacional = () => {
        dispatch(updateCheckInternacionalActualizaBd(!checkInternacional));
    }

    const handleCheckNacional = () => {
        dispatch(updateCheckNacionalActualizaBd(!checkNacional));
    }

    const handleCheckActualiza1b = () => {
        dispatch(updateCheckActualiza1bActualizaBd(!checkActualiza1b));
    }

    return {
        checkInternacional,
        checkNacional,
        checkActualiza1b,
        loadingInter,
        loadingNac,
        loadingAct1B,
        ShowActualiza1BActBd,
        log,
        loadingLogCsv,
        ShowNacionalActBd,
        ShowInternacionalActBd,
        handleCheckInternacional,
        handleCheckNacional,
        handleCheckActualiza1b,
        downloadFilesFromUrl,
        handleInter,
        handleNac,
        handleAct1B
    }
}