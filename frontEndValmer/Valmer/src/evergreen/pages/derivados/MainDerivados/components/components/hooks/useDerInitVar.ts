import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import {useState} from "react";
import { useDispatch, useSelector } from "react-redux";

export const useDerInitVar = () => {

    const [selectedTv, setSelectedTv] = useState("")
    const [selectedEmisora, setSelectedEmisora] = useState("")
    const [selectedSerie, setSelectedSerie] = useState("")

    const requiredTv = useSelector((state: RootState<any, any, any>) => 
        state.requiredTvDer) as unknown as boolean

    const requiredEmisora = useSelector((state: RootState<any, any, any>) => 
        state.requiredEmisoraDer) as unknown as boolean
    
    const requiredSerie = useSelector((state: RootState<any, any, any>) => 
        state.requiredSerieDer) as unknown as boolean

    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);
    const [loadingConsultaData, setLoadingConsultaData] = useState(false);

    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);
    const [triggerConsultaData, setTriggerConsultaData] = useState(false);

    const dispatch = useDispatch()

    return {
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        triggerEmisora,
        triggerSerie,
        triggerConsultaData,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setTriggerConsultaData,
        setLoadingEmisoras,
        setLoadingSerie,
        setLoadingConsultaData,
        setTriggerEmisora,
        setTriggerSerie,
        dispatch
    }
}