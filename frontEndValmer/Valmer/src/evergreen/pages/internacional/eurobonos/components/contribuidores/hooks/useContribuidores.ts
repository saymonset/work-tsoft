import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {useEffect, useState} from "react";
import {downloadFiles, fetchDataGetRet, fetchDataGetSave, showAlert} from "../../../../../../../utils";
import {
    updateFilePreciosCont,
    updateIsRevFormInsumos2,
    updateIsShowLogBoxCont,
    updateRevisar2,
    updateShowFileContributor,
    updateStateCheckbox
} from "../../../../../../../redux";
import {valmerApi} from "../../../../../../../api";
import { stateCheckbox } from "../../../../../../../model";

export const useContribuidores = () => {

    const showFileContributor = useSelector((state: RootState<any, any, any>) =>
        state.showFileContributor) as unknown as boolean;

    const showContribuidoresForm = useSelector((state: RootState<any, any, any>) =>
        state.isRevInsForm2) as unknown as boolean;

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const selectedVector = useSelector((state: RootState<any, any, any>) =>
        state.selectedVector) as unknown as string;

    const isShowLogBoxCont = useSelector((state: RootState<any, any, any>) =>
        state.isShowLogBoxCont) as unknown as boolean;

    const isCheckboxChecked = useSelector((state: RootState<any, any, any>)=>
        state.isCheckboxChecked) as unknown as stateCheckbox;

    const [loading, setLoading] = useState(false)
    const [loadingRevisar2, setLoadingRevisar2] = useState(false)
    const [loadingLogCsv, setLoadingLogCsv] = useState(false)
    const [checkObtenerCarteras, setCheckObtenerCarteras] = useState(false)
    const [flechaArriba, setFlechaArriba] = useState(true);
    const [isFetchDataGetSaveSuccessful, setIsFetchDataGetSaveSuccessful] = useState(false);
    const [log, setLog] = useState<string[]>([]);
    const [loadingLogBox, setLoadingLogBox] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        const getLog = async () => {
            setLoadingLogBox(true);
            const response = await fetchDataGetRet(
                "/log/consulta-log",
                " ",
                {logName: "log_proceso_euro"})

            setLog(response.body);
            setIsFetchDataGetSaveSuccessful(false)
            setLoadingLogBox(false);
        };

        if (isFetchDataGetSaveSuccessful) {
            getLog().then();
        }
    }, [isFetchDataGetSaveSuccessful]);


    const handleShowFileContributor = async () => {
        dispatch(updateShowFileContributor(!showFileContributor))
        setFlechaArriba(!flechaArriba);
    }

    const handleChange =(name: string, checked: boolean) =>{
        const updateValue = {...isCheckboxChecked, [name]: !checked}
        dispatch(updateStateCheckbox(updateValue))
    }

    const handleGetWallet = async () => {
        dispatch(updateIsRevFormInsumos2(false))
        dispatch(updateShowFileContributor(false))
        setLoading(true)
        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/obtener-cartera",
            " se genero un error al obtener carteras",
            {
                d_fecha: fechaEurobonos
            })
            setLoading(false)
            setIsFetchDataGetSaveSuccessful(true);
            dispatch(updateIsShowLogBoxCont(true));
            handleChange("cbx_obtener_carteras", false)
        };

    const handleRevisar2 = async () => {
        setLoadingRevisar2(true)

        let response : any

        try {
            response = await valmerApi.get(
                "/internacional/proceso-eurobonos/tabla-revisa2",
                { params: {d_fecha_val: fechaEurobonos, s_vector: selectedVector } })

            if(response.data) {
                const objetoArchivo = response.data.body.find((item: { [x: string]: any; }) => item['archivo-precios']);
                const archivo = objetoArchivo ? objetoArchivo['archivo-precios'] : null;

                const revisar2 = {
                    status: response.data.status,
                    body: response.data.body.filter((item: { [x: string]: any; }) => !item['archivo-precios'])
                }

                if(archivo) {
                    dispatch(updateFilePreciosCont(archivo))
                }
                dispatch(updateRevisar2(revisar2))
                dispatch(updateIsRevFormInsumos2(true))
            }
            else {
                dispatch(updateRevisar2({status: 200, body: []}))
                dispatch(updateIsRevFormInsumos2(true))
            }
        } catch (error: any) {
            if (error.message.includes('Network Error')) {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            }
            else
            {
                dispatch(updateRevisar2({status: 200, body: []}))
                dispatch(updateIsRevFormInsumos2(true))
            }
        }
        finally {
            setLoadingRevisar2(false)
            handleChange("cbx_revisar2", false)
        }
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

    return {
        showFileContributor,
        isShowLogBoxCont,
        log,
        loading,
        loadingLogCsv,
        loadingLogBox,
        loadingRevisar2,
        showContribuidoresForm,
        checkObtenerCarteras,
        flechaArriba,
        isCheckboxChecked,
        handleChange,
        handleLogCsv,
        handleGetWallet,
        handleRevisar2,
        handleShowFileContributor,
        setCheckObtenerCarteras,
        
    }
}