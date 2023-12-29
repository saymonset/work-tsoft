import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {useEffect, useState} from "react";
import {
    downloadFiles,
    fetchDataGetRet,
    fetchDataGetSave,
    getCurrentDate,
    showAlert,
    userEncoded
} from "../../../../../../../utils";
import {updateIsShowLogBoxProc} from "../../../../../../../redux";
import {AxiosResponse} from "axios";
import {valmerApi} from "../../../../../../../api";
import {SalidaEuro} from "../../../../../../../model";
import { stateCheckbox } from "../../../../../../../model";
import {updateStateCheckbox} from "../../../../../../../redux";

export const useProceso = () => {

    const [loadingProd, setLoadingProd] = useState(false)
    const [loadingLogCsv, setLoadingLogCsv] = useState(false)
    const [loadingPrices, setLoadingPrices] = useState(false)
    const [loadingCalculateRates, setLoadingCalculateRates] = useState(false)
    const [loadingCalculatePrices, setLoadingCalculatePrices] = useState(false)
    const [loadingOutputs, setLoadingOutputs] = useState(false)
    const [checkActPrecios, setCheckActPrecios] = useState(false)
    const [checkCalcularTasas, setCheckCalcularTasas] = useState(false)
    const [checkCalculaPrecios, setCheckCalculaPrecios] = useState(false)
    const [checkSalidas, setCheckSalidas] = useState(false)
    const [tableOutputs, setTableOutputs] = useState<SalidaEuro[]>()
    const [isModalOpenProcesos, setIsModalOpenProcesos] = useState(false);
    const [isFetchDataGetLog, setIsFetchDataGetLog] = useState(false);
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogBox, setLoadingLogBox] = useState(false)
    const [isOpenEdit, setIsOpenEdit] = useState(false);

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const isShowLogBoxProc = useSelector((state: RootState<any, any, any>) =>
        state.isShowLogBoxProc) as unknown as boolean;

    const isCheckboxChecked = useSelector((state: RootState<any, any, any>)=>
        state.isCheckboxChecked) as unknown as stateCheckbox;

    const [isShowLogBoxProcModal, setIsShowLogBoxProcModal] = useState(false);
    const [isFetchDataGetLogModal, setIsFetchDataGetLogModal] = useState(false);
    const [loadingLogBoxModal, setLoadingLogBoxModal] = useState(false)
    const [logModal, setLogModal] = useState<string[]>([]);
    const [isOutputs, setIsOutputs] = useState(false)


    const dispatch = useDispatch()

    useEffect(() => {
        const getLogProcesoModal = async () => {
            setLoadingLogBoxModal(true);
            const response = await fetchDataGetRet(
                "/log/consulta-log",
                " ",
                {logName: "log_proceso_euro"})

            setLogModal(response.body);
            setIsFetchDataGetLogModal(false)
            setLoadingLogBoxModal(false);
            setIsShowLogBoxProcModal(true)
        };

        if (isFetchDataGetLogModal) {
            getLogProcesoModal().then();
        }
    }, [isFetchDataGetLogModal]);

    useEffect(() => {
        const getLogProceso = async () => {
            setLoadingLogBox(true);
            const response = await fetchDataGetRet(
                "/log/consulta-log",
                " ",
                {logName: "log_proceso_euro"})

            setLog(response.body);
            setIsFetchDataGetLog(false)
            setLoadingLogBox(false);
        };

        if (isFetchDataGetLog) {
            getLogProceso().then();
        }
    }, [isFetchDataGetLog]);


    const handleOpenCarga = async () => {
        setLoadingProd(true)
        const response = await fetchDataGetRet(
            "/internacional/proceso-eurobonos/salidas/produccion",
            " error al obtener salidas euro",
            {s_user: userEncoded(), d_fecha: getCurrentDate()})
        setLoadingProd(false)
        setTableOutputs(response.body.SALIDAS_EURO)
        setIsOpenEdit(true);
    }

    const handleOpenLogModal = async () => {
        setIsFetchDataGetLogModal(true);
    }

    const handleCloseEdit = () => {
        setIsOpenEdit(false);
        setIsShowLogBoxProcModal(false)
    }

    const handleUpdatePrices = async () => {
        dispatch(updateIsShowLogBoxProc(false));
        setLoadingPrices(true)
        setIsOutputs(false)
        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/actualiza-precios",
            " se genero un error en actualiza-precios",
            {
                d_fecha: fechaEurobonos,
                s_user: userEncoded(),
            })
        setLoadingPrices(false)

        setIsFetchDataGetLog(true);
        dispatch(updateIsShowLogBoxProc(true));
        handleChange("cbx_Act_Precios", false)
    };

    const handleCalculateRates = async () => {
        dispatch(updateIsShowLogBoxProc(false));
        setLoadingCalculateRates(true)
        setIsOutputs(false)

        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/calcula-tasas",
            " se genero un error en calcula-tasas",
            {
                d_fecha: fechaEurobonos,
                s_user: userEncoded(),
            })

        setIsFetchDataGetLog(true);
        dispatch(updateIsShowLogBoxProc(true));
        setLoadingCalculateRates(false)
        handleChange("cbx_Calcular_Tasas", false)
    };

    const handleCalculatePrices = async () => {
        dispatch(updateIsShowLogBoxProc(false));
        setLoadingCalculatePrices(true)
        setIsOutputs(false)

        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/calcula-precios",
            " se genero un error en calcula-precios",
            {
                d_fecha: fechaEurobonos,
                s_user: userEncoded(),
            })

        setLoadingCalculatePrices(false)
        setIsFetchDataGetLog(true);
        dispatch(updateIsShowLogBoxProc(true));
        handleChange("cbx_Calcula_Precios", false)
    };

    const handleOutputs = async () => {
        dispatch(updateIsShowLogBoxProc(false));
        setIsOutputs(true)
        setLoadingOutputs(true)

        const params = {
            d_fecha: fechaEurobonos,
            s_user: userEncoded(),
        }

        try {
            const response: AxiosResponse<any> = await valmerApi.get("/internacional/proceso-eurobonos/salidas",
                {params});

            if (response.data.body.includes("timed out")
                || response.data.body.includes("error")
                || response.data.body.includes("ERROR")) {
                await showAlert("error", "Error", response?.data?.body);
            }
            else
            {
                await showAlert("success", "Guardado",
                    response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);
            }
        } catch (error: any) {
            if (error.message.includes('Network Error')) {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            } else {
                await showAlert('error', `Error se genero un error en salidas eurobonos`, error.message);
            }
        }


        setLoadingOutputs(false)
        setIsFetchDataGetLog(true);
        dispatch(updateIsShowLogBoxProc(true));
        handleChange("cbx_Salidas", false)
    };

    const handleLogCsv = async () => {
        setLoadingLogCsv(true)
        const response = await fetchDataGetRet(
            "/internacional/proceso-eurobonos/descarga-log",
            " se genero un error en servicio descarga log",
            {})

        if (response) {
            downloadFiles(response.body.contenido,
                response.body.nombreCompleto,
                'text/csv')
        } else {
            console.error('No se recibió un archivo válido');
        }

        setLoadingLogCsv(false)
    }

    const handleOpenModal = () => {
        setIsModalOpenProcesos(true);
    }

    const handleCloseModal = () => {
        setIsModalOpenProcesos(false);
    }

    const handleChange =(name: string, checked: boolean) =>{
        const updateValue = {...isCheckboxChecked, [name]: !checked}
        dispatch(updateStateCheckbox(updateValue))
    }

    return {
        log,
        logModal,
        loadingProd,
        loadingLogCsv,
        loadingLogBox,
        loadingLogBoxModal,
        isModalOpenProcesos,
        isShowLogBoxProc,
        isShowLogBoxProcModal,
        isOpenEdit,
        isOutputs,
        loadingPrices,
        loadingCalculateRates,
        loadingCalculatePrices,
        loadingOutputs,
        checkActPrecios,
        checkCalcularTasas,
        checkCalculaPrecios,
        checkSalidas,
        tableOutputs,
        isCheckboxChecked,
        handleOpenLogModal,
        handleOpenCarga,
        handleCloseEdit,
        handleChange,
        handleLogCsv,
        handleUpdatePrices,
        handleCalculateRates,
        handleCalculatePrices,
        handleOutputs,
        handleOpenModal,
        handleCloseModal,
        setCheckActPrecios,
        setCheckCalcularTasas,
        setCheckCalculaPrecios,
        setCheckSalidas
    }
}