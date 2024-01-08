import {useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import { IsFieldRequiredAccInst } from "../../../../../../model";

export const useAccInitVar = () => {

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvAcc) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraAcc) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieAcc) as unknown as string;
    
    const fieldRequiredAccInst = useSelector(
        (state: RootState<any, any, any>) => state.fieldRequiredAccInst
    ) as unknown as IsFieldRequiredAccInst

    const requiredTv = useSelector(
        (state: RootState<any, any, any>) => state.requiredTvAcc
    ) as unknown as boolean

    const requiredEmisora = useSelector(
        (state: RootState<any, any, any>) => state.requiredEmisoraAcc
    ) as unknown as boolean

    const requiredSerie = useSelector(
        (state: RootState<any, any, any>) => state.requiredSerieAcc
    ) as unknown as boolean

    const triggerPrecalc = useSelector(
        (state: RootState<any, any, any>) => state.triggerPrecalc
    ) as unknown as boolean

    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);
    const [loadingConsultaData, setLoadingConsultaData] = useState(false);
    const [loadingPrecalc, setLoadingPrecalc] = useState(false);

    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);
    const [triggerConsultaData, setTriggerConsultaData] = useState(false);

    return {
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        triggerEmisora,
        triggerSerie,
        triggerConsultaData,
        fieldRequiredAccInst,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        triggerPrecalc,
        loadingPrecalc,
        setTriggerConsultaData,
        setLoadingEmisoras,
        setLoadingSerie,
        setLoadingConsultaData,
        setTriggerEmisora,
        setTriggerSerie,
        setLoadingPrecalc
    }
}