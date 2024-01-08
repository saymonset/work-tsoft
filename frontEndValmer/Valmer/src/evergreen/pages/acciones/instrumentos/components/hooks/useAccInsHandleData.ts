import {useTvAccIns} from "./useTvAccIns";
import {useGetCatalogsDeuda} from "./useGetCatalogsDeuda";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {DividendosData, Precalculados, RespAccInstData} from "../../../../../../model";
import React, {useEffect, useState} from "react";
import {fetchDataGet, fetchDataGetRet, getEmisoras, getSerie, validChangeTvEmiSerie} from "../../../../../../utils";
import {
    updateConsultaDataAccInst,
    updateDividendosTable,
    updateEmisoraAccInst,
    updatePrecalculados,
    updateRequiredFieldAccInst,
    updateSelectedEmisoraAcc,
    updateSelectedSerieAcc,
    updateSelectedTvAcc,
    updateSerieAccInst,
    updateShowCarRvAcc, updateShowPrecalc, updateTriggerPrecalc
} from "../../../../../../redux";
import {useAccInitVar} from "./useAccInitVar";

export const useAccInsHandleData = () => {

    const {tvAccInst, loadingTv} = useTvAccIns();

    const {catalog, loading} = useGetCatalogsDeuda();

    const isNewFormInst = useSelector((state: RootState<any, any, any>) =>
        state.isNewFormInst) as unknown as boolean;

    const showPrecalc = useSelector((state: RootState<any, any, any>) =>
        state.showPrecalc) as unknown as boolean;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerEraseAcc) as unknown as boolean;

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;

    const emisorasAccInst = useSelector((state: RootState<any, any, any>) =>
        state.emisorasAccInst) as unknown as string[];

    const serieAccInst = useSelector((state: RootState<any, any, any>) =>
        state.serieAccInst) as unknown as string[];

    const dividendosTable = useSelector((state: RootState<any, any, any>) =>
        state.dividendosTable) as unknown as DividendosData[];

    const [loadingDividendos, setLoadingDividendos] = useState(false)

    const dispatch = useDispatch()

    const {
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
    } = useAccInitVar()

    useEffect(() => {
        getEmisoras({
            url: "/acciones/instrumentos/emisoras",
            triggerEmisora,
            selectedTv,
            setTriggerEmisora,
            setLoadingEmisoras,
            dispatch,
            reduxAction: updateEmisoraAccInst
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
            dispatch,
            reduxAction: updateSerieAccInst
        }).then();
    }, [triggerSerie]);

    useEffect(() => {
        async function fetchData() {
            try
            {
                if (triggerConsultaData) {

                    setLoadingConsultaData(true);
                    const response = await fetchDataGetRet(
                        "/acciones/instrumentos/consulta-info",
                        " al obtener consulta data",
                        {
                            sTv: selectedTv,
                            sEmisora: selectedEmisora,
                            sSerie: selectedSerie
                        });

                    dispatch(updateConsultaDataAccInst(response));
                    dispatch(updateShowCarRvAcc(true))
                    setTriggerConsultaData(false);
                    setLoadingConsultaData(false);
                    setLoadingDividendos(true)


                    await fetchDataGet(
                        "/acciones/instrumentos/tabla-bitacora-dividendos",
                        " al obtener tabla de dividendos",
                        {
                            sEmisora: selectedEmisora,
                            sSerie: selectedSerie,
                            sTv: selectedTv
                        },
                        updateDividendosTable,
                        dispatch
                    );
                    setLoadingDividendos(false)
                }
            }
            catch (error) {
                console.error("Error fetching data:", error);
            }
        }

        fetchData().then();
    }, [triggerConsultaData]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (triggerPrecalc) {
                    setLoadingPrecalc(true)
                    await fetchDataGet(
                        "/acciones/instrumentos/tabla-precalculados",
                        " al obtener precalculados",
                        {
                            sTv: selectedTv,
                            sEmisora: selectedEmisora,
                            sSerie: selectedSerie
                        },
                        updatePrecalculados,
                        dispatch
                    )

                    dispatch(updateShowPrecalc(true))
                    setLoadingPrecalc(false)
                    dispatch(updateTriggerPrecalc(false))
                }
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        }

        fetchData().then()
    },[triggerPrecalc])

    const handleClickTv = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {type} = e.target
        if (type !== "text") {
            setTriggerEmisora(true);
            dispatch(updateSelectedEmisoraAcc(""))
            dispatch(updateSelectedSerieAcc(""))
        }
        validChangeTvEmiSerie("s_tv", dispatch)
        dispatch(updateSelectedTvAcc(e.target.value))
        dispatch(updateConsultaDataAccInst({} as RespAccInstData))
        dispatch(updateDividendosTable([]))
        dispatch(updatePrecalculados({} as Precalculados))
        dispatch(updateShowPrecalc(false))
    };


    const handleEmisora = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {type} = e.target
        if (type !== "text") {
            setTriggerSerie(true);
            dispatch(updateSelectedSerieAcc(""))
        }
        validChangeTvEmiSerie("s_emisora", dispatch)
        dispatch(updateSelectedEmisoraAcc(e.target.value))
        dispatch(updateConsultaDataAccInst({} as RespAccInstData))
        dispatch(updateDividendosTable([]))
        dispatch(updatePrecalculados({} as Precalculados))
        dispatch(updateShowPrecalc(false))
    };


    const handleSerie = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {type} = e.target
        if (type !== "text") {
            setTriggerConsultaData(true);
            dispatch(updateTriggerPrecalc(true))
        }
        validChangeTvEmiSerie("s_serie", dispatch)
        dispatch(updateSelectedSerieAcc(e.target.value))
        dispatch(updateConsultaDataAccInst({} as RespAccInstData))
        dispatch(updateDividendosTable([]))
        dispatch(updatePrecalculados({} as Precalculados))
        dispatch(updateShowPrecalc(false))
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

        const updatedFieldRequired = {
            ...fieldRequiredAccInst,
            [name]: false
        }

        dispatch(updateRequiredFieldAccInst(updatedFieldRequired))

        dispatch(updateConsultaDataAccInst(updatedValues));
    };

    const handleCheckbox = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, checked} = e.target;
        const value = checked ? '1' : '0';

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
        isNewFormInst,
        showPrecalc,
        tvAccInst,
        loadingTv,
        catalog,
        loading,
        triggerErase,
        consultaDataAccInst,
        emisorasAccInst,
        serieAccInst,
        dividendosTable,
        loadingDividendos,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        fieldRequiredAccInst,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        handleChange,
        handleCheckbox,
        handleClickTv,
        handleEmisora,
        handleSerie,
        loadingPrecalc
    }
}