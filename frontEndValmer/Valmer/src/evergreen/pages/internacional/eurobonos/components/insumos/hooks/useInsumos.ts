import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {downloadFiles, fetchDataGetRet, fetchDataGetSave, userEncoded} from "../../../../../../../utils";
import {updateIsRevFormInsumos, updateIsShowLogBox, updateRevisar1, updateStateCheckbox} from "../../../../../../../redux";
import { StateCheckbox } from "../../../../../../../model";

export const useInsumos = () => {

    const [loading, setLoading] = useState(false)
    const [loadingLogBox, setLoadingLogBox] = useState(false)
    const [loadingLogCsv, setLoadingLogCsv] = useState(false)
    const [checkInsumos, setCheckInsumos] = useState(false)
    const [checkRevisar1, setCheckRevisar1] = useState(false)
    const [loadingRevisar1, setLoadingRevisar1] = useState(false)
    const [isFetchDataGetSaveSuccessful, setIsFetchDataGetSaveSuccessful] = useState(false);
    const [log, setLog] = useState<string[]>([]);

    const isShowLogBox = useSelector((state: RootState<any, any, any>) =>
        state.isShowLogBox) as unknown as boolean;

    const showRevisarForm = useSelector((state: RootState<any, any, any>) =>
        state.isRevInsForm) as unknown as boolean;

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const selectedVector = useSelector((state: RootState<any, any, any>) =>
        state.selectedVector) as unknown as string;

    const isCheckboxChecked = useSelector((state: RootState<any, any, any>)=>
        state.isCheckboxChecked) as unknown as StateCheckbox;

    const dispatch = useDispatch()

    useEffect(() => {
        const realizarSegundaLlamada = async () => {
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
            realizarSegundaLlamada().then();
        }
    }, [isFetchDataGetSaveSuccessful]);

    const handleChange =(name: string, checked: boolean) =>{
        const updateValue = {...isCheckboxChecked, [name]: !checked}
        dispatch(updateStateCheckbox(updateValue))
    }

    const handleInsumos = async () => {
        dispatch(updateIsShowLogBox(false));
        setLoading(true);
        await fetchDataGetSave(
            "/internacional/proceso-eurobonos/carga-insumos",
            " se genero un error en la carga de insumos",
            {
                d_fecha: fechaEurobonos,
                s_user: userEncoded(),
                s_vector: selectedVector
            });

        setLoading(false);
        setIsFetchDataGetSaveSuccessful(true);
        dispatch(updateIsShowLogBox(true));
        handleChange("cbx_carga_insumos", false)
    };

    const handleRevisar1 = async () => {
        dispatch(updateIsShowLogBox(false));
        setLoadingRevisar1(true)
        const response = await fetchDataGetRet(
            "/internacional/proceso-eurobonos/tabla-revisa1",
            " se genero un error en la tabla revisa1",
            {d_fecha_val: fechaEurobonos})
        setLoadingRevisar1(false)
        handleChange("cbx_Revisar1", false)

        if(response)
        {
            dispatch(updateIsRevFormInsumos(true))
            dispatch(updateRevisar1(response))
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
        showRevisarForm,
        isShowLogBox,
        log,
        loading,
        loadingLogCsv,
        loadingLogBox,
        loadingRevisar1,
        checkInsumos,
        checkRevisar1,
        isCheckboxChecked,
        handleChange,
        handleLogCsv,
        handleInsumos,
        handleRevisar1,
        setCheckInsumos,
        setCheckRevisar1
    }
}