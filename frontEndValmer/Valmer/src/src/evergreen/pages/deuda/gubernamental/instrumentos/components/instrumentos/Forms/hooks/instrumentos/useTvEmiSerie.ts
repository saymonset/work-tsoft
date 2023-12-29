import {useEffect} from "react";
import {
    fetchDataGet,
    fetchDataGetRet
} from "../../../../../../../../../../utils";
import {
    showCuponesTasas,
    updateCatalog,
    updateConsultaData,
    updateEmisora,
    updateNInfoGuber,
    updateSelectedEmisora,
    updateSelectedSerie,
    updateSelectedTv,
    updateSerie
} from "../../../../../../../../../../redux";
import {useVarDecData} from "./useVarDecData";
import {ResponseReglasAlta} from "../../../../../../../../../../model";

export const useTvEmiSerie = () => {

    const {
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
    } = useVarDecData()

    useEffect(() => {
        const getDataEmisoras = async () => {
            if (triggerEmisora) {
                setLoadingEmisoras(true);
                await fetchDataGet("/instrumentos/emisoras",
                    "al obtener catalogo emisora",
                    {sTv:selectedTv}, updateEmisora, dispatch)
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
                await fetchDataGet("/instrumentos/series",
                    "al obtener catalogo serie",
                    {sTv: selectedTv, sEmisora: selectedEmisora}, updateSerie, dispatch)
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
                const response = await fetchDataGetRet("/instrumentos/gubernamentales/consulta-info",
                    "al obtener consulta data",
                    {sTv: selectedTv, sEmisora: selectedEmisora, sSerie: selectedSerie})

                if (response.body.h_flujos === "{}") {
                    response.body.h_flujos = [];
                }

                dispatch(updateConsultaData(response));
                setTriggerConsultaData(false)
                setLoadingConsultaData(false);
                dispatch(showCuponesTasas(true));
            }
        };
        getDefaultData().catch(() => {});
    }, [triggerConsultaData]);

    useEffect(() => {
        const getRegisterRules = async () => {
            if(isNewGubForm)
            {
                setLoadingConsultaData(true);
                await fetchDataConsultaAlta("")
                setLoadingConsultaData(false);
            }
        };
        getRegisterRules().then();
    }, [isNewGubForm]);


    const handleClickTv = async (e: any) => {
        if(isNewGubForm)
        {
            setLoadingConsultaData(true);
            await fetchDataConsultaAlta(e)
            setLoadingConsultaData(false);
            dispatch(updateSelectedTv(e));
        }
        else
        {
            setTriggerEmisora(true)
            dispatch(updateSelectedEmisora("default"));
            dispatch(updateSelectedSerie("default"));
            dispatch(updateSelectedTv(e.target.value));
        }
    }

    const handleEmisora = (e: any) => {
        dispatch(updateSelectedSerie("default"));
        setTriggerSerie(true)
        dispatch(updateSelectedEmisora(e.target.value));
    }

    const handleSerie = (e: any) => {
        setTriggerConsultaData(true)
        dispatch(updateSelectedSerie(e.target.value));
        dispatch(updateNInfoGuber(1))
    }


    const fetchDataConsultaAlta = async (sTv: string) => {
        if (Object.keys(consultaData).length > 0) {
            if (!Object.values(consultaData.body).some((val) => val === '') && !sTv) {
                return;
            }
        }

        if (!sTv) {
            const response = await fetchDataGetRet(
                "/instrumentos/gubernamentales/consulta-reglas-alta",
                "consulta reglas alta",
                { sTv }
            );
            const body = response.body[0].reglas_alta
            const data = {...consultaData, body}

            const registros = response.body[1].FRECUENCIA_CUPON
            const catPeriodoCupon = {catalogo: "FRECUENCIA_CUPON-GUBER-NEW", registros: registros}

            dispatch(updateCatalog([...catalog, catPeriodoCupon]))
            dispatch(updateConsultaData(data));
            
            return;
        }

        const response: ResponseReglasAlta = await fetchDataGetRet(
            "/instrumentos/gubernamentales/consulta-reglas-alta",
            "consulta reglas alta",
            { sTv }
        );

        const data = response.body?.reglas[0]?.data;
        const catPeriodoCupon = response.body?.cat_periodo_cupon;

        if (catPeriodoCupon && data) {
            catPeriodoCupon.catalogo = "FRECUENCIA_CUPON-GUBER-NEW";
            dispatch(updateCatalog([...catalog, catPeriodoCupon]));
            dispatch(updateConsultaData({body: {...consultaData.body, ...data, s_tv: sTv}}));
        }
    }

    return {
        triggerErase,
        isNewGubForm,
        isNewSerieGub,
        catalog,
        loading,
        tv,
        loadingTv,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        emisora,
        serie,
        consultaData,
        handleClickTv,
        handleEmisora,
        handleSerie,
    }
}