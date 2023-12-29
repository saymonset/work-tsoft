import {useInitValPrecalc} from "./useInitValPrecalc";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {RespAccInstData} from "../../../../../../../../model";
import React, {useEffect} from "react";
import {fetchDataGetRet, getEmisoras, getSerie} from "../../../../../../../../utils";
import {updateConsultaDataAccInst} from "../../../../../../../../redux";

export const usePrecalcTvEmiSerie = () => {

    const {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        triggerEmisora,
        triggerSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        setTv,
        setEmisora,
        setSerie,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setTriggerEmisora,
        setTriggerSerie,
        setLoadingTv,
        setLoadingEmisoras,
        setLoadingSerie,
        dispatch
    } = useInitValPrecalc()

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;


    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    {sMercado: "ACC", isNew: false})

                setTv(response.body)
                setLoadingTv(false);
            }
            getTv().then()
        }
    }, [tv]);

    useEffect(() => {
        getEmisoras({
            url: "/acciones/instrumentos/emisoras",
            triggerEmisora,
            selectedTv,
            setTriggerEmisora,
            setLoadingEmisoras,
            setEmisora
        }).then();
    }, [triggerEmisora]);

    useEffect(() => {
        getSerie({
            url: "/acciones/instrumentos/series",
            triggerSerie,
            selectedTv,
            selectedEmisora,
            setTriggerSerie,
            setLoadingSerie,
            setSerie
        }).then();
    }, [triggerSerie]);

    const handleClickTv = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        handleChange(e)
        setSelectedEmisora("...")
        setTriggerEmisora(true);
        setSelectedTv(e.target.value)
    };

    const handleEmisora = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        handleChange(e)
        setSelectedSerie("...")
        setTriggerSerie(true);
        setSelectedEmisora(e.target.value)
    };


    const handleSerie = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        handleChange(e)
        setSelectedSerie(e.target.value)
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;

        const updatedValues = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst?.body,
                acciones: {
                    ...consultaDataAccInst?.body?.acciones,
                    [name]: value
                }
            }
        };

        dispatch(updateConsultaDataAccInst(updatedValues));
    };


    return {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        handleClickTv,
        handleEmisora,
        handleSerie,
        handleChange
    }


}