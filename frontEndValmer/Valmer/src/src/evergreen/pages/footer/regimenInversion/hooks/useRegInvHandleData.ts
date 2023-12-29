import {useEffect, useState} from "react";
import { fetchDataGetRet, fetchDataPost, getEmisoras, getSerie, userEncoded } from "../../../../../utils";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import { BodyRegInvData, RespRegInvData } from "../../../../../model/RegimenInversion";
import { useGetCatalogsRegInv } from "./useGetCatalogsRegInv";
import {
    updateConsultaDataRegInv,
    updateEmisoraRegInv,
    updateSelectedEmisoraRegInv,
    updateSelectedSerieRegInv,
    updateSelectedTvRegInv,
    updateSerieRegInv,
    updateShowCarRvRegInv,
    updateTvRegInv
} from "../../../../../redux/RegimenInversion";

export const useRegInvHandleData = () => {

    const {catalog, loadingCatalog} = useGetCatalogsRegInv();

    const tvRegInv = useSelector((state: RootState<any, any, any>) =>
        state.tvRegInv) as unknown as string[];

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvRegInv) as unknown as string;
    
    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraRegInv) as unknown as string;
    
    const emisorasRegInv = useSelector((state: RootState<any, any, any>) =>
        state.emisorasRegInv) as unknown as string[];

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieRegInv) as unknown as string;

    const serieRegInv = useSelector((state: RootState<any, any, any>) =>
        state.serieRegInv) as unknown as string[];
    
    const consultaDataRegInv = useSelector((state: RootState<any, any, any>) =>
    state.consultaDataRegInv) as unknown as RespRegInvData;
    
    const [loading, setLoading] = useState(false);
    const [triggerEmisora, setTriggerEmisora] = useState(false);
    const [triggerSerie, setTriggerSerie] = useState(false);
    const [loadingEmisoras, setLoadingEmisoras] = useState(false);
    const [triggerConsultaData, setTriggerConsultaData] = useState(false);
    const [loadingSerie, setLoadingSerie] = useState(false);
    const [loadingConsultaData, setLoadingConsultaData] = useState(false);
    const [loadingBtn, setLoadingBtn] = useState<boolean>(false);
    const [spanEnabled, setSpanEnabled] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
        if (!tvRegInv || tvRegInv.length === 0) {
            const getTv = async () => {
                setLoading(true)
                const response = await fetchDataGetRet(
                    "/regimen-inv/tv",
                    " al obtener catalogo tv",
                    {})

                dispatch(updateTvRegInv(response.body))
                setLoading(false);
            }
            getTv().then()
        }

    }, [tvRegInv]);

    useEffect(() => {
        getEmisoras({
            url: "/regimen-inv/emisoras",
            triggerEmisora,
            selectedTv,
            setTriggerEmisora,
            setLoadingEmisoras,
            dispatch,
            reduxAction: updateEmisoraRegInv
        }).then();
    }, [triggerEmisora]);

    useEffect(() => {
        getSerie({
            url: "/regimen-inv/series",
            triggerSerie,
            selectedTv,
            selectedEmisora,
            setTriggerSerie,
            setLoadingSerie,
            dispatch,
            reduxAction: updateSerieRegInv
        }).then();
    }, [triggerSerie]);

    useEffect(() => {
        async function fetchData() {
            try
            {
                if (triggerConsultaData) {

                    setLoadingConsultaData(true);
                    const response = await fetchDataGetRet(
                        "/regimen-inv/consulta-info",
                        " al obtener consulta data",
                        {
                            sTv: selectedTv,
                            sEmisora: selectedEmisora,
                            sSerie: selectedSerie
                        });
                    dispatch(updateConsultaDataRegInv(response));
                    dispatch(updateShowCarRvRegInv(true))
                    setTriggerConsultaData(false);
                    setLoadingConsultaData(false);
                    setSpanEnabled(true);
                }
            }
            catch (error) {
                console.error("Error fetching data:", error);
            }
        }

        fetchData().then();
    }, [triggerConsultaData]);

    const handleClickTv = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setTriggerEmisora(true);
        dispatch(updateSelectedEmisoraRegInv("..."))
        dispatch(updateSelectedTvRegInv(e.target.value))
    };

    const handleEmisora = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setTriggerSerie(true);
        dispatch(updateSelectedSerieRegInv("..."))
        dispatch(updateSelectedEmisoraRegInv(e.target.value))
    };

    const handleSerie = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setTriggerConsultaData(true);
        dispatch(updateSelectedSerieRegInv(e.target.value))
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;

        const updatedValues = {
            ...consultaDataRegInv,
            body: {
                ...consultaDataRegInv?.body,
                [name]: value
            }
        };

        dispatch(updateConsultaDataRegInv(updatedValues));
    };

    const handleSave = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoadingBtn(true);

        const instrumentosSelected = {
            s_tv: selectedTv,
            s_emisora: selectedEmisora,
            s_serie: selectedSerie
        };

        const filteredData: Partial<BodyRegInvData> = {};

        for (const key in consultaDataRegInv.body) {
            if (key !== "s_instrumento" && consultaDataRegInv.body[key] !== "") {
                filteredData[key] = consultaDataRegInv.body[key];
            }
        }

        const updatedConsultaDataAccInst = {
            ...instrumentosSelected,
            ...filteredData,
        };

        await fetchDataPost(
            "/regimen-inv/actualiza-info",
            " al guardar regimen de inversion",
            updatedConsultaDataAccInst,
            { s_user: userEncoded() }
        );

        setLoadingBtn(false);
    };

    const handleCheckbox = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, checked} = e.target;
        
        const value = name === 'B_CAMBIO' ? (checked ? '1' : '') : (checked ? '1' : '0');

        const updatedValues = {
            ...consultaDataRegInv,
            body: {
                ...consultaDataRegInv?.body,
                [name]: value
            }
        };

        dispatch(updateConsultaDataRegInv(updatedValues));
        
    };

    return {
        loading,
        selectedTv,
        handleClickTv,
        tvRegInv,
        selectedEmisora,
        handleEmisora,
        emisorasRegInv,
        loadingEmisoras,
        selectedSerie,
        handleSerie,
        serieRegInv,
        loadingSerie,
        handleChange,
        catalog,
        consultaDataRegInv,
        loadingConsultaData,
        loadingCatalog,
        handleSave,
        loadingBtn,
        handleCheckbox,
        spanEnabled
    };
}