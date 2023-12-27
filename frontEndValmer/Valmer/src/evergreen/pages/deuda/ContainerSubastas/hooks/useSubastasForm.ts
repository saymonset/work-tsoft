import {
    ConsultaSubastasTransform,
    ConsultaSubastasTransformArray, initialValuesSub, initialVariableSubFlotantes,
    Instrumento, SubFormProps
} from "../../../../../model";
import {updateConsultaSubastas, updateIsConsultaLog} from "../../../../../redux";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {useGetCatalogsSubastas} from "./index";
import {useEffect, useState} from "react";
import { fetchDataGetRet } from "../../../../../utils";

export const useSubastasForm = (p: SubFormProps) => {

    const {
        catalog,
        loading
    } = useGetCatalogsSubastas()

    const consultaSubastas = useSelector((state: RootState<any, any, any>) =>
        state.consultaSub) as unknown as ConsultaSubastasTransformArray;

    const isShowLog = useSelector((state: RootState<any, any, any>) =>
        state.isShowLog) as unknown as boolean

    const isConsultaLog = useSelector((state: RootState<any, any, any>) =>
        state.isConsultaLog) as unknown as boolean

    const [log, setLog] = useState<string>("")
    const [loadingLogBox, setLoadingLogBox] = useState<boolean>(false)

    const dispatch = useDispatch()

    let initialVar : Instrumento[] = p.isSub ? initialValuesSub : initialVariableSubFlotantes

    const initialValuesTransformed: ConsultaSubastasTransform[] = initialVar.map(item => {
        return { [item.s_instrumento]: item }
    });

    useEffect(() => {
        dispatch(updateConsultaSubastas(initialValuesTransformed));
    }, [dispatch]);

    useEffect(() => {

        const getLogProceso = async () => {
            setLoadingLogBox(true);
            const response = await fetchDataGetRet(
                "/log/consulta-log",
                " ",
                { logName: "log_subastas" });
            const logCompleto = response.body;
            const logNew = transformLog(logCompleto);
            setLog(logNew);
            dispatch(updateIsConsultaLog(false));
            setLoadingLogBox(false);
        };

        if (isConsultaLog) {
            getLogProceso().then();
        }
    }, [isConsultaLog]);

    const transformLog = (logCompleto: string): string => {
        const logSeparate = logCompleto.split(/UPDATE|<br>/);
        let logNew = "";
        for (let i = 0; i < logSeparate.length; i++) {
            if (i % 2 !== 0) {
                logNew += "UPDATE " + logSeparate[i] + "<br>";
            }
        }
        return logNew;
    };

    const valueGet = (name: string, variable: keyof Instrumento) => {
        const object =
            consultaSubastas.find((obj: ConsultaSubastasTransform) => name in obj);
        return object?.[name]?.[variable] ?? '';
    }

    const valueSet = (name: string, variable: keyof Instrumento, value: string) => {
        let updatedValues = [...consultaSubastas];
        let index = updatedValues.findIndex((obj: ConsultaSubastasTransform) => name in obj);

        if (index !== -1) {
            let updatedObject: ConsultaSubastasTransform = { ...updatedValues[index] };
            if (updatedObject[name]) {
                let updatedInstrumento: Instrumento = { ...updatedObject[name] };
                // @ts-ignore
                updatedInstrumento[variable] = value as any;
                updatedObject[name] = updatedInstrumento;
                updatedValues[index] = updatedObject;
            }
        }

        dispatch(updateConsultaSubastas(updatedValues));
    }

    return {
        catalog,
        loading,
        log,
        loadingLogBox,
        isShowLog,
        valueGet,
        valueSet
    }
}