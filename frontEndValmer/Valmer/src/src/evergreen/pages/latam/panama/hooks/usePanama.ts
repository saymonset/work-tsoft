import React, {useEffect, useState} from "react";
import {Catalogo, RespConsultaDataPanam} from "../../../../../model";
import {fetchDataGetRet, fetchDataPost, userEncoded} from "../../../../../utils";

export const usePanama = () => {

    const [nemoTecnico, setNemoTecnico] = useState<string[]>([])

    const [selectedNemoTecnico, setSelectedNemoTecnico] = useState<string>("")

    const [consultaData, setConsultaData] =
        useState<RespConsultaDataPanam>({} as RespConsultaDataPanam);

    const [catalog, setCatalog] = useState<Catalogo[]>([]);

    const [activeNuevo, setActiveNuevo] = useState(false)

    const [loadingNemo, setLoadingNemo] = useState(false);

    const [loading, setLoading] = useState(false);

    const [loadingSave, setLoadingSave] = useState(false);

    const [loadingConsultaData, setLoadingConsultaData] = useState(false);

    const [checkboxValues, setCheckboxValues] = useState({
        inactivas: 0,
        amortAnticipadas: 0
    });

    useEffect(() => {
        const getNemoTenico = async () => {
            setLoadingNemo(true);
            const response = await fetchDataGetRet(
                "/latam/panama/nemotecnicos",
                " al obtener catalogos panama",
                {
                    check_amort_antic: checkboxValues.amortAnticipadas,
                    check_inactivas: checkboxValues.inactivas,
                    n_tipo_instrumento: 0})

            setNemoTecnico(response.body);
            setLoadingNemo(false);
        }

        getNemoTenico().then();

    }, []);

    useEffect(() => {
        const getCatalog = async () => {
            setLoading(true);
            const response = await fetchDataGetRet(
                "/latam/panama/catalogos",
                " al obtener catalogos panama",
                {})

            setCatalog(response.body.catalogos);
            setLoading(false);
        }

        getCatalog().then();

    }, []);

    const handleNuevo = () => {
        setActiveNuevo(true)
    }

    const handleCancel = () => {
        setActiveNuevo(false)
    }

    const handleSelectNemo = async (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedNemoTecnico(e.target.value)
        setLoadingConsultaData(true)
        const response: RespConsultaDataPanam = await fetchDataGetRet(
            "/latam/panama/consulta-info",
            " al obtener consultas info",
            {s_nemotecnico: e.target.value}
        )
        setConsultaData(response)
        setLoadingConsultaData(false)
    }

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        setCheckboxValues(prevValues => ({
            ...prevValues,
            [name]: checked ? 1 : 0
        }));
    };

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
        if(selectedNemoTecnico)
        {
            setLoadingSave(true)
            const request = {
                ...consultaData.body.info_bd,
                s_nemotecnico: selectedNemoTecnico
            };

            await fetchDataPost(
                "/latam/panama/actualiza-info",
                " al actualizar info Latam Panamá",
                request,
                {
                    check_amort_antic: checkboxValues.amortAnticipadas,
                    check_inactivas: checkboxValues.inactivas,
                    s_user: userEncoded()}
            )

            setLoadingSave(false)
        }
    }

    return {
        checkboxValues,
        consultaData,
        activeNuevo,
        nemoTecnico,
        selectedNemoTecnico,
        loading,
        loadingSave,
        loadingConsultaData,
        loadingNemo,
        catalog,
        setSelectedNemoTecnico,
        handleCheckboxChange,
        handleSelectNemo,
        handleNuevo,
        handleCancel,
        handleSave,
        handleChange
    }
}