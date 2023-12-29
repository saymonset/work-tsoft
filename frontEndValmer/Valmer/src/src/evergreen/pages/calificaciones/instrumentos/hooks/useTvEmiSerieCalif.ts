import { useEffect } from "react"
import { fetchDataGetRet } from "../../../../../utils"
import { CalifInstData } from "../../../../../model"
import { useCalifInstVar } from "./useCalifInstVar"

export const useTvEmiSerieCalif = () => {
    
    const {
        isFondos,
        isNewInstr,
        isNewSerie,
        selectedTv,
        loadingTv,
        tv,
        loadingTvFondos,
        tvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        triggerEmisora,
        selectedSerie,
        series,
        loadingSeries,
        triggerSerie,
        consultaData,
        loadingConsultaData,
        triggerConsultaData,
        loadingSave,
        section,
        fileBase64,
        selectedFile,
        setSection,
        setFileBase64,
        setSelectedFile,
        setIsFondos,
        setIsNewInstr,
        setIsNewSerie,
        setLoadingSave,
        setSelectedTv,
        setTv,
        setLoadingTv,
        setTvFondos,
        setLoadingTvFondos,
        setSelectedEmisora,
        setEmisoras,
        setLoadingEmisoras,
        setTriggerEmisora,
        setSelectedSerie,
        setSeries,
        setLoadingSeries,
        setTriggerSerie,
        setConsultaData,
        setLoadingConsultaData,
        setTriggerConsultaData
    } = useCalifInstVar()

    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    {sMercado: "Calif", isNew: false}
                )
                setLoadingTv(false);
                setTv(response.body)
            }

            getTv().then()
        }

    }, [tv]);

    useEffect(() => {
        if (!tvFondos || tvFondos.length === 0) {
            const getTv = async () => {
                setLoadingTvFondos(true)
                const response = await fetchDataGetRet(
                    "calificaciones/instrumentos/fondos/tv",
                    " al obtener catalogo tv",
                    {}
                )
                setLoadingTvFondos(false);
                setTvFondos(response.body)
            }

            getTv().then()
        }

    }, [tvFondos]);
    
    useEffect(() => {
        const getDataEmisoras = async () => {
            if (triggerEmisora) {
                setLoadingEmisoras(true)
                const response = await fetchDataGetRet(
                    "/calificaciones/instrumentos/emisoras",
                    " al obtener catalogo emisora",
                    {sTv: selectedTv}
                )
                setEmisoras(response.body)
                setTriggerEmisora(false)
                setLoadingEmisoras(false)
            }
        };

        getDataEmisoras().catch(() => {})
    }, [triggerEmisora]);

    useEffect(() => {
        const getDataSeries = async () => {
            if (triggerSerie) {
                setLoadingSeries(true)
                const response = await fetchDataGetRet(
                    "/calificaciones/instrumentos/series",
                    " al obtener catalogo serie",
                    {sTv: selectedTv, sEmisora: selectedEmisora}
                )
                setSeries(response.body)
                setTriggerSerie(false)
                setLoadingSeries(false)
            }
        };

        getDataSeries().catch(() => {})
    }, [triggerSerie]);

    useEffect(() => {
        const getDefaultData = async () => {
            if (triggerConsultaData) {
                setLoadingConsultaData(true)
                const response = await fetchDataGetRet(
                    isFondos ? "/calificaciones/instrumentos/fondos/consulta-info" 
                             : "/calificaciones/instrumentos/consulta-info",
                    " al obtener consulta data",
                    isFondos ? 
                    {
                        sTv: selectedTv,
                        sEmisora: selectedEmisora
                    }
                    :
                    {
                        sTv: selectedTv, 
                        sEmisora: selectedEmisora, 
                        sSerie: selectedSerie
                    }
                )
                setConsultaData(response.body)
                setTriggerConsultaData(false)
                setLoadingConsultaData(false)
            }
        };

        getDefaultData().catch(() => {})
    }, [triggerConsultaData]);

    const handleTv = (e: any) => {
        setConsultaData({} as CalifInstData)
        setTriggerEmisora(true)
        setEmisoras([])
        setSeries([])
        setSelectedEmisora("...")
        setSelectedSerie("...")
        setSelectedTv(e.target.value)
    }

    const handleEmisora = (e: any) => {
        setConsultaData({} as CalifInstData)
        if (isFondos) {
            setTriggerConsultaData(true)
        } else {
            setTriggerSerie(true)
            setSeries([])
            setSelectedSerie("...")
        }
        setSelectedEmisora(e.target.value)
    }

    const handleSerie = (e: any) => {
        setConsultaData({} as CalifInstData)
        setTriggerConsultaData(true)
        setSelectedSerie(e.target.value)
    }

    return {
        isFondos,
        isNewInstr,
        isNewSerie,
        selectedTv,
        tv,
        loadingTv,
        tvFondos,
        loadingTvFondos,
        selectedEmisora,
        emisoras,
        loadingEmisoras,
        selectedSerie,
        series,
        loadingSeries,
        consultaData,
        loadingConsultaData,
        loadingSave,
        section,
        fileBase64,
        selectedFile,
        setSection,
        setFileBase64,
        setSelectedFile,
        setTv,
        setTvFondos,
        setIsFondos,
        setEmisoras,
        setSeries,
        setIsNewInstr,
        setIsNewSerie,
        setLoadingSave,
        setSelectedTv,
        setSelectedEmisora,
        setSelectedSerie,
        setConsultaData,
        handleTv,
        handleEmisora,
        handleSerie
    }

}