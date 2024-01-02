import React, {useEffect} from "react";
import {RespConsultaDataCR} from "../../../../../../model";
import {fetchDataGetRet, fetchDataPost, showAlert, userEncoded} from "../../../../../../utils";
import {useInitialVarCr} from "./useInitialVarCr";

export const useCostaRica = () => {

    const {
        consultaData, setConsultaData,
        emisor, setEmisor,
        nemoInstrumento,setNemoInstrumento,
        serie, setSerie,
        catalog,setCatalog,
        selectedEmisor,setSelectedEmisor,
        selectedNemo,setSelectedNemo,
        selectedSerie,setSelectedSerie,
        checkboxValue,setCheckboxValue,
        loadingSave, setLoadingSave,
        loadingEmisor,setLoadingEmisor,
        loadingNemoInst,setLoadingNemoInst,
        loadingSerie,setLoadingSerie,
        loadingCatalogo,setLoadingCatalogo,
        loadingConsultaInfo, setLoadingConsultaInfo,
        triggerNemo,setTriggerNemo,
        triggerSerie, setTriggerSerie,
        triggerConsultaInfo, setTriggerConsultaInfo,
        activeNuevo,setActiveNuevo} = useInitialVarCr()

    useEffect(() => {
        const getCatalogs = async () => {
            setLoadingCatalogo(true);
            const response = await fetchDataGetRet(
                "/latam/cr/catalogos",
                " al obtener catalogos costa rica",
                {})

            setCatalog(response.body.catalogos);
            setLoadingCatalogo(false);
        }

        if (!catalog || catalog.length === 0) {
            getCatalogs().then();
        }
    }, []);

    useEffect(() => {
        const getEmisor = async () => {
            setLoadingEmisor(true);
            const response = await fetchDataGetRet(
                "/latam/cr/emisor",
                " al obtener catalogos emisor",
                {})

            setEmisor(response.body);
            setLoadingEmisor(false);
        }
        if (!emisor || emisor.length === 0) {
            getEmisor().then();
        }
    }, []);


    useEffect(() => {
        const getNemoInstrumento = async () => {
            if (triggerNemo)
            {
                setLoadingNemoInst(true);
                const response = await fetchDataGetRet(
                    "/latam/cr/nemo-instrumento",
                    " al obtener catalogos nemo instrumento",
                    {check_inactivas: checkboxValue, s_emisor: selectedEmisor}
                );
                setNemoInstrumento(response.body);
                setLoadingNemoInst(false);
                setTriggerNemo(false)
            }
        };
        getNemoInstrumento().then();

    }, [triggerNemo]);

    useEffect(() => {
        const getSerie = async () => {
            if (triggerSerie)
            {
                setLoadingSerie(true);
                const response = await fetchDataGetRet(
                    "/latam/cr/series",
                    " al obtener catalogos serie",
                    {
                        check_inactivas: checkboxValue,
                        s_emisor: selectedEmisor,
                        s_nemo_instr: selectedNemo}
                );
                setSerie(response.body);
                setLoadingSerie(false);
                setTriggerSerie(false)
            }
        };
        getSerie().then();

    }, [triggerSerie]);

    useEffect(() => {
        const getConsultaInfo = async () => {
            if (triggerConsultaInfo)
            {
                setLoadingConsultaInfo(true);
                const response: RespConsultaDataCR = await fetchDataGetRet(
                    "/latam/cr/consulta-info",
                    " al obtener consulta-info",
                    {
                        s_emisor: selectedEmisor,
                        s_nemo_instr: selectedNemo,
                        s_serie: selectedSerie
                    }
                );

                setConsultaData(response)
                setLoadingConsultaInfo(false);
                setTriggerConsultaInfo(false)
            }
        };
        getConsultaInfo().then();

    }, [triggerConsultaInfo]);

    const handleNuevo = () => {
        setActiveNuevo(true)
        setConsultaData({} as RespConsultaDataCR)
        setSelectedEmisor("")
        setSelectedNemo("")
        setSelectedSerie("")
        setCheckboxValue(1)
    }

    const handleCancel = () => {
        setActiveNuevo(false)
    }

    const handleEmisor = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedEmisor(e.target.value)
        setSelectedNemo("...")
        setSelectedSerie("...")
        setTriggerNemo(true)
    }
    const handleNemo = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedNemo(e.target.value)
        setSelectedSerie("...")
        setTriggerSerie(true)
    }

    const handleSerie = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedSerie(e.target.value)
        setTriggerConsultaInfo(true)
    }

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.checked) {
            setCheckboxValue(2);
        } else {
            setCheckboxValue(1);
        }
    };

    const handleErase = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setActiveNuevo(false)
        setConsultaData({} as RespConsultaDataCR)
        setSelectedEmisor("")
        setSelectedNemo("")
        setSelectedSerie("")
        setCheckboxValue(1)
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        setConsultaData(prevData => ({
            ...prevData,
            body: {
                ...prevData.body,
                info_bd: {
                    ...prevData.body?.info_bd,
                    [name]: value
                }
            }
        }));
    };

    const handleSave = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoadingSave(true);

        if (Object.keys(consultaData).length === 0) {
            setLoadingSave(false);
            return;
        }
        
        try {

            if(activeNuevo){
                const request = {
                    ...consultaData.body.info_bd,
                    s_emisor: selectedEmisor,
                    s_nemo_instr: selectedNemo,
                    s_serie: selectedSerie,
                }

                await fetchDataPost(
                    "/latam/cr/actualiza-info",
                    " al intentar actualizar informacion costa rica",
                    request,
                    {s_user: userEncoded()}
                )
            }else{
                const bodyInfo = consultaData.body.info_rw[Object.keys(consultaData.body.info_rw)[0]]
                const request = {
                    ...consultaData.body.info_bd,
                    ...bodyInfo[`${selectedEmisor}_${selectedNemo}_${selectedSerie}`],
                    s_emisor: selectedEmisor,
                    s_nemo_instr: selectedNemo,
                    s_serie: selectedSerie,
                }

                await fetchDataPost(
                    "/latam/cr/actualiza-info",
                    " al intentar actualizar informacion costa rica",
                    request,
                    {s_user: userEncoded()}
                )
            }
            
        }
        catch (err) {
            await showAlert("error", "Error", "Error al ejecutar la actualizacion de datos :::" + err)
        }

        setLoadingSave(false);
    }

    return {
        activeNuevo,
        consultaData,
        loadingConsultaInfo,
        loadingEmisor,
        loadingCatalogo,
        catalog,
        selectedEmisor,
        selectedNemo,
        selectedSerie,
        emisor,
        nemoInstrumento,
        serie,
        checkboxValue,
        loadingSave,
        loadingNemoInst,
        loadingSerie,
        setSelectedEmisor,
        setSelectedNemo,
        setSelectedSerie,
        handleChange,
        handleNuevo,
        handleCancel,
        handleEmisor,
        handleNemo,
        handleSerie,
        handleCheckboxChange,
        handleErase,
        handleSave
    }
}