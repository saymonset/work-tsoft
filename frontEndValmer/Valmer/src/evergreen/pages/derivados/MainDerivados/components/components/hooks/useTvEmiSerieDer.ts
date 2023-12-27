import React, {useEffect, useState} from "react";
import {
    getConsultaData,
    getEmisoras,
    getSerie,
    validChangeTvEmiSerie,
} from "../../../../../../../utils";
import {RespDerivados} from "../../../../../../../model";
import {useDerInitVar} from "./useDerInitVar";

interface TvEmiSerieHookProps {
    sMercado: string;
    urlConsultaData: string;
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    setConsultaDataDer: React.Dispatch<React.SetStateAction<RespDerivados>>;
}

export const useTvEmiSerieDer = ({
                                     sMercado,
                                     urlConsultaData,
                                     handleChange,
                                     setConsultaDataDer}: TvEmiSerieHookProps) => {

    const [emisora, setEmisora] = useState<string[]>([])
    const [serie, setSerie] = useState<string[]>([])
    const [urlEmisoras, setUrlEmisoras] = useState<string>("")
    const [urlSerie, setUrlSerie] = useState<string>("")

    const {
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
    } = useDerInitVar()

    useEffect(() => {
        urlBuild().then()
    }, []);


    useEffect(() => {
        getEmisoras({
            url: urlEmisoras,
            triggerEmisora,
            selectedTv,
            setTriggerEmisora,
            setLoadingEmisoras,
            setEmisora,
        }).then();
    }, [triggerEmisora]);

    useEffect(() => {
        getSerie({
            url: urlSerie,
            triggerSerie,
            selectedTv,
            selectedEmisora,
            setSerie,
            setTriggerSerie,
            setLoadingSerie
        }).then();
    }, [triggerSerie]);

    useEffect(() => {
        getConsultaData({
            url: urlConsultaData,
            triggerConsultaData,
            selectedTv,
            selectedEmisora,
            selectedSerie,
            setConsultaData: setConsultaDataDer,
            setTriggerConsultaData,
            setLoadingConsultaData
        }).then();
    }, [triggerConsultaData]);

    const handleClickTv = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setTriggerEmisora(true);
        setSelectedEmisora("...");
        setSelectedTv(e.target.value)
        validChangeTvEmiSerie('s_tv', dispatch)
        handleChange(e)
    };


    const handleEmisora = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedSerie("...");
        setTriggerSerie(true);
        setSelectedEmisora(e.target.value);
        validChangeTvEmiSerie('s_emisora', dispatch)
        handleChange(e)
    };


    const handleSerie = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setTriggerConsultaData(true);
        setSelectedSerie(e.target.value);
        validChangeTvEmiSerie('s_serie', dispatch)
        handleChange(e)
    };

    const urlBuild = async () => {
        if (sMercado === "TEORICOS")
        {
            setUrlEmisoras("/derivados/teoricos/emisoras")
            setUrlSerie("/derivados/teoricos/series")
        }
        else if (sMercado === "DERIV")
        {
            setUrlEmisoras("/derivados/listados/emisoras")
            setUrlSerie("/derivados/listados/series")
        }
        else if (sMercado === "DERIV_C")
        {
            setUrlEmisoras("/derivados/chicago/emisoras")
            setUrlSerie("/derivados/chicago/series")
        }
    }

    return {
        loadingConsultaData,
        loadingEmisoras,
        loadingSerie,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        handleClickTv,
        handleEmisora,
        handleSerie
    }
}