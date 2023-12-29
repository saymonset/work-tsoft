import { useDispatch, useSelector } from "react-redux";
import { useTvOpt } from "../../../../../../../../shared";
import { useGetCatalogs } from "./useGetCatalogs";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import {FormValuesInter, IsFieldModifiedFvInterIns, ResponseConsultaDataInter} from "../../../../../../../../model";
import { useState } from "react";

export const useVarDecData = () => {

    const formValues = useSelector((state: RootState<any, any, any>) => 
        state.formValuesInter) as unknown as FormValuesInter;

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataInter) as unknown as ResponseConsultaDataInter;
    
    const selectedTv = useSelector((state: RootState<any, any, any>) => 
        state.selectedTvInter) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) => 
        state.selectedEmisoraInter) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) => 
        state.selectedSerieInter) as unknown as string;

    const emisora = useSelector((state: RootState<any, any, any>) => 
        state.emisoraInter) as unknown as string[];

    const serie = useSelector((state: RootState<any, any, any>) =>
        state.serieInter) as unknown as string[];

    const isNewInterForm = useSelector((state: RootState<any, any, any>) => 
        state.isNewInterForm) as unknown as boolean;

    const requiredTv = useSelector((state: RootState<any, any, any>) => 
        state.requiredTvInter) as unknown as boolean;

    const requiredEmisora = useSelector((state: RootState<any, any, any>) =>
        state.requiredEmisoraInter) as unknown as boolean;
    
    const requiredSerie = useSelector((state: RootState<any, any, any>) => 
        state.requiredSerieInter) as unknown as boolean;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerErase) as unknown as boolean;

    const fieldRequiredInternacional = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredIntern) as unknown as IsFieldModifiedFvInterIns;

    const {tv, loadingTv} = useTvOpt("INTER", isNewInterForm);
    
    const { catalog, loading, catalogCalif, loadingCalif } = useGetCatalogs();

    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);
    const [triggerConsultaData, setTriggerConsultaData] = useState(false);

    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);
    const [loadingConsultaData, setLoadingConsultaData] = useState(false);

    const dispatch = useDispatch();

    return {
        fieldRequiredInternacional,
        triggerErase,
        formValues,
        consultaData,
        catalog,
        loading,
        catalogCalif,
        loadingCalif,
        tv,
        loadingTv,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        emisora,
        serie,
        isNewInterForm,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        triggerEmisora,
        triggerSerie,
        triggerConsultaData,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        setTriggerEmisora,
        setTriggerSerie,
        setTriggerConsultaData,
        setLoadingEmisoras,
        setLoadingSerie,
        setLoadingConsultaData,
        dispatch
    }
}
