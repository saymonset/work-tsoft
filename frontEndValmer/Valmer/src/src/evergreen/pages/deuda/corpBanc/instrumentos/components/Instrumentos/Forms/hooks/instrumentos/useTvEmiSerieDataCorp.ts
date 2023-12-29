import {useEffect} from "react";
import {
    fetchDataGet,
    fetchDataGetConsultaData,
    fetchDataGetRet,
    showAlert
} from "../../../../../../../../../../utils";
import {
    updateCatalogCorp,
    updateConsultaDataCorp,
    updateEmisoraCorp,
    updateEmisoraRef1Corp,
    updateEmisoraRef2Corp,
    updateSelectedEmisoraCorp,
    updateSelectedSerieCorp,
    updateSelectedTvCorp,
    updateSerieCorp,
    updateSerieRef1Corp,
    updateSerieRef2Corp
} from "../../../../../../../../../../redux";
import {useVarDecDataCorp} from "./useVarDecDataCorp";
import {
    Catalogo,
    FvDeudaCorpInstrumentos,
    initialFormValuesCorp,
    ResponseConsultaDataCorp
} from "../../../../../../../../../../model";

export const useTvEmiSerieDataCorp = () => {

    const {
        emisorasRef1, serieRef1,
        emisorasRef2, serieRef2,
        fieldRequiredCorp, triggerErase,
        consultaData, selectedTv, selectedEmisora,
        selectedSerie, emisora, serie,
        catalog, loading,
        tv, loadingTv,
        isNewFormCorp, isNewSerieCorp,
        requiredTvCorp, requiredEmisoraCorp,
        requiredSerieCorp,
        state, updateState,
        dispatch,
    } = useVarDecDataCorp()


    useEffect(() => {
        const getDataEmisoras = async () => {
            if (state.triggerEmisora) {
                updateState({ loadingEmisoras: true });
                await fetchDataGet("/instrumentos/corporativos/emisoras",
                    "al obtener catalogo emisora",
                    { sTv: selectedTv }, updateEmisoraCorp, dispatch)
                updateState({ triggerEmisora: false, loadingEmisoras: false });
            }
        };

        getDataEmisoras().catch(() => {});
    }, [state.triggerEmisora]);

    useEffect(() => {
        const getDataEmisorasRef1 = async () => {
            if (state.triggerEmisoraRef1) {
                updateState({ loadingEmisorasRef1: true });
                const response = await fetchDataGetRet("/instrumentos/emisoras",
                    "al obtener catalogo emisora",
                    {sTv: consultaData.body.s_tv_ref_1})

                dispatch(updateEmisoraRef1Corp(response.body))
                updateState({ triggerEmisoraRef1: false, loadingEmisorasRef1: false });
            }
        };

        getDataEmisorasRef1().catch(() => {});
    }, [state.triggerEmisoraRef1]);

    useEffect(() => {
        const getDataEmisorasRef2 = async () => {
            if (state.triggerEmisoraRef2) {
                updateState({ loadingEmisorasRef2: true });
                const response = await fetchDataGetRet("/instrumentos/emisoras",
                    "al obtener catalogo emisora",
                    {sTv: consultaData.body.s_tv_ref_2})

                dispatch(updateEmisoraRef2Corp(response.body))
                updateState({ loadingEmisorasRef2: false, triggerEmisoraRef2: false });
            }
        };

        getDataEmisorasRef2().catch(() => {});
    }, [state.triggerEmisoraRef2]);

    useEffect(() => {
        const getDataSerie = async () => {
            if (state.triggerSerie) {
                updateState({ loadingSerie: true });
                await fetchDataGet("/instrumentos/corporativos/series",
                    "al obtener catalogo serie",
                    {sTv: selectedTv, sEmisora: selectedEmisora}, updateSerieCorp, dispatch)
                updateState({ loadingSerie: false, triggerSerie: false });
            }
        };

        getDataSerie().catch(() => {});
    }, [state.triggerSerie]);

    useEffect(() => {
        const getDataSerieRef1 = async () => {
            if (state.triggerSerieRef1) {
                updateState({ loadingSerieRef1: true });
                const response = await fetchDataGetRet("/instrumentos/series",
                    "al obtener catalogo serie",
                    {sTv: consultaData.body.s_tv_ref_1, sEmisora: consultaData.body.s_emisora_ref_1})
                dispatch(updateSerieRef1Corp(response.body))
                updateState({ loadingSerieRef1: false, triggerSerieRef1: false });
            }
        };

        getDataSerieRef1().catch(() => {});
    }, [state.triggerSerieRef1]);

    useEffect(() => {
        const getDataSerieRef2 = async () => {
            if (state.triggerSerieRef2) {
                updateState({ loadingSerieRef2: true });
                const response = await fetchDataGetRet("/instrumentos/series",
                    "al obtener catalogo serie",
                    {sTv: consultaData.body.s_tv_ref_2, sEmisora: consultaData.body.s_emisora_ref_2})
                dispatch(updateSerieRef2Corp(response.body))
                updateState({ loadingSerieRef2: false, triggerSerieRef2: false });
            }
        };

        getDataSerieRef2().catch(() => {});
    }, [state.triggerSerieRef2]);

    useEffect(() => {
        const getDefaultData = async () => {
            if (state.triggerConsultaData) {
                updateState({ loadingConsultaData: true });
                await fetchDataGetConsultaData("/instrumentos/corporativos/consulta-info",
                    "al obtener consulta data",
                    {sTv: selectedTv, sEmisora: selectedEmisora, sSerie: selectedSerie},
                    updateConsultaDataCorp,
                    dispatch)

                updateState({ loadingConsultaData: false, triggerConsultaData: false });
            }
        };

        getDefaultData().catch(() => {});
    }, [state.triggerConsultaData]);

    useEffect(() => {
        if(emisorasRef1.length < 1 || serieRef2.length < 1 )
        {
            if(consultaData?.body?.s_tv_ref_1 &&
                consultaData?.body?.s_tv_ref_2 &&
                consultaData?.body?.s_emisora_ref_1 &&
                consultaData?.body?.s_emisora_ref_2)
            {
                updateState({ triggerEmisoraRef1: true });
                updateState({ triggerSerieRef1: true });
                updateState({ triggerEmisoraRef2: true });
                updateState({ triggerSerieRef2: true });
            }
        }
    }, [consultaData]);

    useEffect(() => {
        const getRegisterRules = async () => {
            if(isNewFormCorp)
            {
                await fetchDataConsultaAlta("")
            }
        };
        getRegisterRules().then();
    }, [isNewFormCorp]);

    const handleClickTv = async (e: any) => {
        if(isNewFormCorp)
        {
            dispatch(updateSelectedTvCorp(e));
            await fetchDataConsultaAlta(e)
        }
        else
        {
            updateState({ triggerEmisora: true });
            dispatch(updateSelectedEmisoraCorp("..."));
            dispatch(updateSelectedTvCorp(e.target.value));
        }
    }

    const handleEmisora = (e: any) => {
        dispatch(updateSelectedSerieCorp("..."));
        updateState({ triggerSerie: true });
        dispatch(updateSelectedEmisoraCorp(e.target.value));
    }

    const handleSerie = (e: any) => {
        const {type} = e.target
        if (type !== 'text') updateState({ triggerConsultaData: true });
        dispatch(updateSelectedSerieCorp(e.target.value));
    }

    const handleTvRef1 = () => {
        let updatedData: ResponseConsultaDataCorp = { body: { ...consultaData.body,
                s_emisora_ref_1 : "...",
                s_serie_ref_1: "..." } };
        dispatch(updateConsultaDataCorp(updatedData));
        updateState({ triggerEmisoraRef1: true });
    }

    const handleEmiRef1 = () => {
        updateState({ triggerSerieRef1: true });
    }

    const handleTvRef2 = () => {
        let updatedData: ResponseConsultaDataCorp = { body: { ...consultaData.body,
                s_emisora_ref_2 : "...",
                s_serie_ref_2 : "..." } };
        dispatch(updateConsultaDataCorp(updatedData));
        updateState({ triggerEmisoraRef2: true });
    }

    const handleEmiRef2 = () => {
        updateState({ triggerSerieRef2: true });
    }

    const areAllFieldsEmpty = (data: Record<string, any>): boolean => {
        return Object.values(data).every(value => {
            if (Array.isArray(value)) return value.length === 0;
            if (typeof value === 'string') return value.trim() === '';
            return value == null;

        });
    };

    const fetchDataConsultaAlta = async (sTv: string) => {

        if (areAllFieldsEmpty(consultaData.body) || sTv) {
            updateState({ loadingConsultaData: true });
            const response = await fetchDataGetRet("/instrumentos/corporativos/consulta-reglas-alta",
                "consulta reglas alta", {sTv: sTv});

            if (!sTv)
            {
                try
                {
                    if (response.status === 200) {
                        const data = response.body;

                        const reglasAlta = data[0].reglas_alta;
                        const updatedFvDeudaCorpInstrumentos: FvDeudaCorpInstrumentos = {
                            ...initialFormValuesCorp,
                            ...reglasAlta
                        };
                        dispatch(updateConsultaDataCorp({ body: updatedFvDeudaCorpInstrumentos }));

                        const frecuenciaCupon = data[1].FRECUENCIA_CUPON;

                        const catalogo: Catalogo = {
                            catalogo: 'FRECUENCIA_CUPON',
                            registros: frecuenciaCupon
                        };

                        const updatedCatalog = [...catalog, catalogo];

                        dispatch(updateCatalogCorp(updatedCatalog));
                    }
                }
                catch (err)
                {
                    await showAlert('error', `Error al hacer peticion en consulta data alta`, ""+err);
                }
            }
            updateState({ loadingConsultaData: false });
        }
    }

    return {
        fieldRequiredCorp, triggerErase, catalog,
        loading, tv, loadingTv, selectedTv,
        selectedEmisora, selectedSerie, emisorasRef1,
        emisorasRef2, serieRef1, serieRef2,
        loadingEmisorasRef1 : state.loadingEmisorasRef1,
        loadingSerieRef1 : state.loadingSerieRef1,
        loadingEmisorasRef2 : state.loadingEmisorasRef2,
        loadingSerieRef2 : state.loadingSerieRef2,
        loadingEmisoras : state.loadingEmisoras,
        loadingSerie : state.loadingSerie,
        loadingConsultaData : state.loadingConsultaData,
        emisora, serie, consultaData, isNewFormCorp,
        isNewSerieCorp, requiredTvCorp,
        requiredEmisoraCorp, requiredSerieCorp,
        handleClickTv, handleEmisora, handleSerie, handleTvRef1,
        handleEmiRef1, handleTvRef2, handleEmiRef2
    }
}