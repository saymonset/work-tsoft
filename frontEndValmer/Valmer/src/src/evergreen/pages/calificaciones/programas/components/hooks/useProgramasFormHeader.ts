import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { CalifProgramas, Catalogo, DefaultValuesProgramas, RatingAgency, ResponseDataCorp } from "../../../../../../model";
import React, { useEffect, useState } from "react";
import { fetchDataGet, fetchDataPost, getEmisoras, showAlert, userEncoded } from "../../../../../../utils";
import {
    updateCalifProCarac,
    updateCatalogCalifPro, updateDataCalifProgramas,
    updateEmisoraCalifPro, updateSelectedTvCalifPro, updateTriggerEraseCalifPro,
    updateTvCalifPro
} from "../../../../../../redux/Calificaciones/Programas/actions";
import { valmerApi } from "../../../../../../api";

export const useProgramasFormHeader = () => {

    const tv = useSelector((state: RootState<any, any, any>) =>
        state.tvCalifPro) as unknown as string[];

    const emisora = useSelector((state: RootState<any, any, any>) =>
        state.emisoraCalifPro) as unknown as string[];

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalogoCalifPro) as unknown as Catalogo[];

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.califProgramasData) as unknown as CalifProgramas;

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvCalifPro) as unknown as string;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerEraseCalifPro) as unknown as boolean;

    const [loadingSave, setLoadingSave] = useState(false);

    const [loading, setLoading] = useState(false);

    const [loadingTv, setLoadingTv] = useState(false);

    const [loadingEmisoras, setLoadingEmisoras] = useState(false);

    const [triggerEmisora, setTriggerEmisora] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                await fetchDataGet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    { sMercado: "CALIF", isNew: false },
                    updateTvCalifPro, dispatch)
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
            dispatch,
            reduxAction: updateEmisoraCalifPro
        }).then();
    }, [triggerEmisora]);

    useEffect(() => {
        if (!catalog || catalog.length === 0) {
            setLoading(true);
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCatalogCalifPro(response.data.body.catalogos));
                })
                .catch(async error => {
                    setLoading(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Instrumento', error.message);
                    }
                })
        }
    }, [catalog]);

    useEffect(() => {
        handleErase()
    }, [])

    const handleTv = (e: React.ChangeEvent<HTMLSelectElement>) => {
        dispatch(updateSelectedTvCalifPro(e.target.value));
        setTriggerEmisora(true);
        handleChange(e)
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
      
        const defaultValue = DefaultValuesProgramas[name];
      
        const updatedFormData: CalifProgramas = { ...DefaultValuesProgramas, ...formData };
      
        updatedFormData[name] = value !== "" ? value : defaultValue;
      
        dispatch(updateDataCalifProgramas(updatedFormData));
      };

    const handleErase = () => {
        dispatch(updateTriggerEraseCalifPro(true))
        setTimeout(() => {
            dispatch(updateCalifProCarac({} as RatingAgency))
            dispatch(updateDataCalifProgramas({} as CalifProgramas))
            dispatch(updateCatalogCalifPro([]))
            dispatch(updateTvCalifPro([]))
            dispatch(updateEmisoraCalifPro([]))
            dispatch(updateSelectedTvCalifPro(""))
            dispatch(updateTriggerEraseCalifPro(false))
        }, 10)
    }

    const handleSave = async () => {
        setLoadingSave(true)

        await fetchDataPost(
            "/calificaciones/programas/actualiza-info",
            " al intentar guardar calificaciones programas",
            formData,
            { s_user: userEncoded() })
        setLoadingSave(false)
    }

    return {
        tv,
        emisora,
        formData,
        selectedTv,
        triggerErase,
        loading,
        loadingTv,
        loadingSave,
        loadingEmisoras,
        catalog,
        handleSave,
        handleErase,
        handleChange,
        handleTv
    }
}