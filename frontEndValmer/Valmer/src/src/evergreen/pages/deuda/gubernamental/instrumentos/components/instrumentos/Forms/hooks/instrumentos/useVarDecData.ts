import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {ResponseConsultaData} from "../../../../../../../../../../model";
import {useGetCatalogs} from "./useGetCatalogs";
import {useTvOpt} from "../../../../../../../../../../shared";
import {useState} from "react";

export const useVarDecData = () => {

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaData) as unknown as ResponseConsultaData;

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTv) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisora) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerie) as unknown as string;

    const emisora = useSelector((state: RootState<any, any, any>) =>
        state.emisora) as unknown as string[];

    const serie = useSelector((state: RootState<any, any, any>) =>
        state.serie) as unknown as string[];

    const isNewGubForm= useSelector((state: RootState<any, any, any>) =>
        state.isNewGubForm) as unknown as boolean;

    const isNewSerieGub = useSelector((state: RootState<any, any, any>) => 
        state.isNewSerieGub) as unknown as boolean;

    const requiredTv= useSelector((state: RootState<any, any, any>) =>
        state.requiredTv) as unknown as boolean;

    const requiredEmisora= useSelector((state: RootState<any, any, any>) =>
        state.requiredEmisora) as unknown as boolean;

    const requiredSerie= useSelector((state: RootState<any, any, any>) =>
        state.requiredSerie) as unknown as boolean;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerErase) as unknown as boolean;

    const { tv, loadingTv } = useTvOpt(undefined, isNewGubForm);
    const { catalog, loading } = useGetCatalogs();

    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);
    const [triggerConsultaData, setTriggerConsultaData] = useState(false);

    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);
    const [loadingConsultaData, setLoadingConsultaData] = useState(false);

    const dispatch = useDispatch();


    return {
        triggerErase,
        isNewGubForm,
        isNewSerieGub,
        consultaData,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        emisora,
        serie,
        catalog,
        loading,
        tv,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        triggerEmisora,
        triggerSerie,
        triggerConsultaData,
        setTriggerSerie,
        setTriggerEmisora,
        setTriggerConsultaData,
        setLoadingEmisoras,
        setLoadingSerie,
        setLoadingConsultaData,
        dispatch
    };
}