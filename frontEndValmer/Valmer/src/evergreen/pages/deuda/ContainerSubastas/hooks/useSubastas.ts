import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {updateConsultaSubastas, updateIsConsultaLog, updateIsShowLog} from "../../../../../redux";
import {fetchDataPostNoMsj, showAlert} from "../../../../../utils";
import {valmerApi} from "../../../../../api";
import {ConsultaSubastas, ConsultaSubastasTransform, ConsultaSubastasTransformArray, Instrumento, initialValuesSub, initialVariableSubFlotantes, keysOfInstrumento} from "../../../../../model";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

interface SubastasProps {
    title: string;
}
export const useSubastas = (s: SubastasProps) => {

    const consultaSubastas = useSelector((state: RootState<any, any, any>) =>
        state.consultaSub) as unknown as ConsultaSubastasTransformArray;

    const [loadingConsulta, setLoadingConsulta] = useState(false)
    const [loadingActualizar, setLoadingActualizar] = useState(false)

    const dispatch = useDispatch()
    const isSub = s.title === "Subastas"

    const handleSubmit = async (e: any) => {
        e.preventDefault();

        if (validEmptyFields()) {
            await showAlert("warning", "Atención!", "No hay información que actualizar");
            return;
        }

        let result: Instrumento[] = consultaSubastas.map(obj => Object.values(obj)[0]);

        setLoadingActualizar(true);
        await fetchDataPostNoMsj(
            "/subastas/actualiza-subastas",
            "al guardar datos subastas",
            result,
            {isFlotantes: !isSub});
        setLoadingActualizar(false);
        dispatch(updateIsConsultaLog(true))
        dispatch(updateIsShowLog(true))
    }

    const handleConsulta = async () => {
        setLoadingConsulta(true);
        try {
            const url = isSub ? "/subastas" : "/subastas/flotantes";
            const response = await valmerApi.get<ConsultaSubastas>(url);

            const consultaSubastasMap =
                new Map(consultaSubastas.map(obj =>
                    [Object.keys(obj)[0], obj[Object.keys(obj)[0]]]));

            for (const instrumento of response.data.body)
            {
                const key = instrumento.s_instrumento;
                if (consultaSubastasMap.has(key)) {
                    consultaSubastasMap.set(key, {...consultaSubastasMap.get(key), ...instrumento});
                } else {
                    consultaSubastasMap.set(key, instrumento);
                }
            }
            const updatedConsultaSubastas =
                Array.from(consultaSubastasMap,
                    ([name, value]) => ({[name]: value}));
            dispatch(updateConsultaSubastas(updatedConsultaSubastas));
            dispatch(updateIsShowLog(false))
        } catch (error: any) {
            await handleError(error);
        } finally {
            setLoadingConsulta(false);
        }
    };

    const handleError = async (error: any) => {
        let errorMessage = error.message;

        if (errorMessage.includes('Network Error')) {
            errorMessage = 'No hay conexión con el servidor';
        } else {
            errorMessage = 'Error al obtener consulta subastas';
        }
        await showAlert('error', 'Error', errorMessage);
    };


    const handleErase = () => {
        let initialVariable = isSub ? initialValuesSub : initialVariableSubFlotantes;

        const consultaSubastasTransformArray: ConsultaSubastasTransformArray = initialVariable.map((instrumento) => {
            const key = instrumento.s_instrumento;
            const consultaSubastasTransform: ConsultaSubastasTransform = {
                [key]: instrumento,
            };
        
            return consultaSubastasTransform;
        });
        dispatch(updateConsultaSubastas(consultaSubastasTransformArray));
        dispatch(updateIsShowLog(false))
    }

    const validEmptyFields = (): boolean => {
        return consultaSubastas.every(item => {
            return Object.values(item).every(instrument => isValidInstrument(instrument));
        });
    };

    const isFieldEmpty = (field: string): boolean => field === '';

    const areAllFieldsEmpty = (instrumento: Instrumento): boolean => {
        return keysOfInstrumento.every(key => isFieldEmpty(instrumento[key]));
    };

    const isValidInstrument = (instrument: Instrumento): boolean => {
        return areAllFieldsEmpty(instrument);
    };


    return {
        loadingConsulta,
        loadingActualizar,
        handleSubmit,
        handleConsulta,
        handleErase
    }
}