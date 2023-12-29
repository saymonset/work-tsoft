import { useEffect } from "react";
import { useVarDecData } from "./useVarDecData"
import { eraseFormValuesInstrumentos, fetchDataGet, fetchDataGetConsultaData } from "../../../../../../../../utils";
import {
    updateConsultaDataInter,
    updateEmisoraInter,
    updateSelectedEmisoraInter,
    updateSelectedSerieInter,
    updateSelectedTvInter,
    updateSerieInter, updateTableClients
} from "../../../../../../../../redux";

export const useTvEmiSerie = () => {

    const {
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
    } = useVarDecData()

    useEffect(() => {
        const getDataEmisoras = async () => {
            if (triggerEmisora) {
                setLoadingEmisoras(true);
                await fetchDataGet("/instrumentos/eurobonos/emisoras",
                    "al obtener catalogo emisora",
                    {sTv:selectedTv}, updateEmisoraInter, dispatch)
                setTriggerEmisora(false)
                setLoadingEmisoras(false);
            }
        };

        getDataEmisoras().catch(() => {});
    }, [triggerEmisora]);

    useEffect(() => {
        const getDataSerie = async () => {
            if (triggerSerie)
            {
                setLoadingSerie(true);
                await fetchDataGet("/instrumentos/eurobonos/series",
                    "al obtener catalogo serie",
                    {sTv: selectedTv, sEmisora: selectedEmisora}, updateSerieInter, dispatch)
                setTriggerSerie(false)
                setLoadingSerie(false);
            }
        };
        getDataSerie().catch(() => {});
    }, [triggerSerie]);

    useEffect(() => {
        const getDefaultData = async () => {
            if (triggerConsultaData) {
                setLoadingConsultaData(true);
                await fetchDataGetConsultaData("/instrumentos/eurobonos/consulta-info",
                    " al obtener consulta data",
                    {sTv: selectedTv, sEmisora: selectedEmisora, sSerie: selectedSerie},
                    updateConsultaDataInter,
                    dispatch)


                await fetchDataGetConsultaData("/instrumentos/eurobonos/tabla-clientes",
                    " al obtener tabla-cliente",
                    {sTv: selectedTv, sEmisora: selectedEmisora, sSerie: selectedSerie},
                    updateTableClients,
                    dispatch)
                setTriggerConsultaData(false)
                setLoadingConsultaData(false);
            }
        };
        getDefaultData().catch(() => {});
    }, [triggerConsultaData]);

    const handleClickTv = async (e: any) => {
        if(isNewInterForm)
        {
            setLoadingConsultaData(true);
            await fetchDataConsultaAlta(e)
            setLoadingConsultaData(false);
            dispatch(updateSelectedTvInter(e));
        }
        else
        {
            eraseData()
            setTriggerEmisora(true)
            dispatch(updateSelectedEmisoraInter("..."));
            dispatch(updateSelectedTvInter(e.target.value));
        }
    }

    const handleEmisora = (e: any) => {
        eraseData()
        dispatch(updateSelectedSerieInter("..."));
        setTriggerSerie(true)
        dispatch(updateSelectedEmisoraInter(e.target.value));
    }

    const handleSerie = (e: any) => {
        eraseData()
        setTriggerConsultaData(true)
        dispatch(updateSelectedSerieInter(e.target.value));
    }

    const eraseData = () => {
        if(Object.keys(formValues).length > 0 || Object.keys(consultaData).length > 0)
        {
            dispatch(updateSelectedSerieInter("..."));
            eraseFormValuesInstrumentos(formValues, dispatch)
        }
    }

    const fetchDataConsultaAlta = async (sTv: string) => {
        await fetchDataGetConsultaData("/instrumentos/gubernamentales/consulta-reglas-alta",
            "consulta reglas alta", {sTv: sTv}, updateConsultaDataInter, dispatch);
    }

    return {
        fieldRequiredInternacional,
        triggerErase,
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
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        handleClickTv,
        handleEmisora,
        handleSerie
    }
}