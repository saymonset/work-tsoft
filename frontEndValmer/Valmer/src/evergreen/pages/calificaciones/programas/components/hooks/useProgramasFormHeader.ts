import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { CalifProgramas, Catalogo, DefaultValuesProgramas, IsFieldReqCalifProg, RatingAgency, RefReqProg, ResponseDataCorp, SelectOrNull } from "../../../../../../model";
import React, { useEffect, useState, useRef } from "react";
import { fetchDataGet, fetchDataGetRet, fetchDataPost, showAlert, userEncoded, validChangeTvEmiSerie, validateFieldsAccCalifLatam } from "../../../../../../utils";
import {
    updateCalifProCarac,
    updateCatalogCalifPro, updateDataCalifProgramas,
    updateEmisoraCalifPro, updateRequiredCalifProg, updateRequiredTvCalifProg, updateSelectedTvCalifPro, updateTriggerEraseCalifPro,
    updateTvCalifPro
} from "../../../../../../redux/Calificaciones/Programas/actions";
import { valmerApi } from "../../../../../../api";

export const useProgramasFormHeader = () => {

    const refReqCalifProg: RefReqProg = {
        s_tv: useRef<SelectOrNull>(null),
        s_emisora: useRef<SelectOrNull>(null),
        n_plazo_calif: useRef<SelectOrNull>(null),
    }

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

    const isFieldReqCalifProg = useSelector((state: RootState<any, any, any>) => 
        state.isFieldReqCalifProg) as unknown as IsFieldReqCalifProg;

    const requiredTvCalifProg = useSelector((state: RootState<any, any, any>) => 
        state.requiredTvCalifProg) as unknown as boolean;

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
        const getDataEmisoras = async () => {
            if (triggerEmisora) {
                setLoadingEmisoras(true)
                const response = await fetchDataGetRet(
                    "/calificaciones/programas/emisoras",
                    " al obtener catalogo emisora",
                    {sTv: selectedTv}
                )
                dispatch(updateEmisoraCalifPro(response.body || []))
                setTriggerEmisora(false)
                setLoadingEmisoras(false)
            }
        };

        getDataEmisoras().catch(() => {})
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
        validChangeTvEmiSerie("s_tv", dispatch)
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

        dispatch(updateRequiredCalifProg({ ...isFieldReqCalifProg, [name]: false }));
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
            dispatch(updateRequiredCalifProg({} as IsFieldReqCalifProg))
            dispatch(updateRequiredTvCalifProg(false))
        }, 10)
    }

    const handleSave = async () => {
        if (await validateFieldsAccCalifLatam(formData, refReqCalifProg, false, false, dispatch, undefined, isFieldReqCalifProg)) {
            setLoadingSave(true)
    
            await fetchDataPost(
                "/calificaciones/programas/actualiza-info",
                " al intentar guardar calificaciones programas",
                formData,
                { s_user: userEncoded() })
            setLoadingSave(false)
        }
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
        isFieldReqCalifProg,
        refReqCalifProg,
        requiredTvCalifProg,
        handleSave,
        handleErase,
        handleChange,
        handleTv
    }
}