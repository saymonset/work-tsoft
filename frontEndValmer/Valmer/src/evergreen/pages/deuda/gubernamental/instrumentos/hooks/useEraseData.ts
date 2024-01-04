import {useDispatch, useSelector} from "react-redux";
import {
    showCuponesTasas,
    updateEmisora,
    updateIsNewFormGub,
    updateRequiredEmisora,
    updateRequiredTv,
    updateRequiredSerie,
    updateIsNewSerieGub,
    updateSelectedEmisora,
    updateSelectedSerie,
    updateSelectedTv,
    updateSerie,
    updateTV,
    updateTriggerEraseGub, updateNInfoGuber, updateFieldRequiredGub, updateConsultaData
} from "../../../../../../redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    FvDeudaGobInstrumentos,
    HTMLInputSelectNull,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaData
} from "../../../../../../model";
import {
    eraseConsultaData,
    eraseFormInsValues,
    eraseFormValuesInstrumentos,
    fetchDataPost,
    userEncoded,
    validateFormFields
} from "../../../../../../utils";
import React, {useEffect, useRef, useState} from "react";

export const useEraseData = () => {

    useEffect(() => {
        handleLimpiarClick()
    }, [])

    const [isModalOpen, setIsModalOpen] = useState(false);

    const requeridosGuber: any = {
        s_tv: useRef<HTMLInputSelectNull>(null),
        s_emisora: useRef<HTMLInputSelectNull>(null),
        s_serie: useRef<HTMLInputSelectNull>(null),
        n_tipo_instrumento: useRef<HTMLSelectElement | null>(null),
        n_familia_instrumento: useRef<HTMLSelectElement | null>(null),
        n_tipo_mercado: useRef<HTMLSelectElement | null>(null),
        n_pais: useRef<HTMLSelectElement | null>(null),
        n_moneda: useRef<HTMLSelectElement | null>(null),
        n_pais_riesgo: useRef<HTMLSelectElement | null>(null),
        n_emisor: useRef<HTMLSelectElement | null>(null),
        d_fecha_emision: useRef<HTMLInputElement | null>(null),
        n_plazo: useRef<HTMLInputElement | null>(null),
        d_fecha_vto: useRef<HTMLInputElement | null>(null),
        d_fecha_vto_estimada: useRef<HTMLInputElement | null>(null),
        n_valor_nominal: useRef<HTMLInputElement | null>(null),
        n_prima: useRef<HTMLInputElement | null>(null),
        b_oferta_publica: useRef<HTMLInputElement | null>(null),
        n_crv_descuento: useRef<HTMLSelectElement | null>(null),
        n_tasa_referencia: useRef<HTMLSelectElement | null>(null),
        n_tipo_tasa: useRef<HTMLSelectElement | null>(null),
        n_convencion_dias: useRef<HTMLSelectElement | null>(null),
        n_composicion_yield: useRef<HTMLSelectElement | null>(null),
        n_tipo_deuda: useRef<HTMLSelectElement | null>(null),
        n_calendario: useRef<HTMLSelectElement | null>(null),
        n_tipo_calculo: useRef<HTMLSelectElement | null>(null),
        n_titulos_iniciales: useRef<HTMLInputElement | null>(null),
        n_titulos_circulacion: useRef<HTMLInputElement | null>(null),
        n_monto_emitido: useRef<HTMLInputElement | null>(null),
        n_monto_circulacion: useRef<HTMLInputElement | null>(null),
        n_frecuencia_cupon: useRef<HTMLSelectElement | null>(null),
        n_tipo_promedio: useRef<HTMLSelectElement | null>(null),
        n_calc_dias: useRef<HTMLSelectElement | null>(null),
        n_dias_deter_tasa: useRef<HTMLInputElement | null>(null),
        n_tasa_cupon: useRef<HTMLInputElement | null>(null),
        n_tasa_cupon_24h: useRef<HTMLInputElement | null>(null),
        n_valor_nominal_act: useRef<HTMLInputElement | null>(null),
        d_fecha_ini_cupon: useRef<HTMLInputElement | null>(null),
        d_fecha_fin_cupon: useRef<HTMLInputElement | null>(null),
        d_fecha_subasta: useRef<HTMLInputElement | null>(null),
        n_num_cupones: useRef<HTMLInputElement | null>(null),
        n_periodo_cupon: useRef<HTMLInputElement | null>(null),
        n_periodo_cupon_v: useRef<HTMLInputElement | null>(null)
    }

    const [loadingSubmit, setLoadingSubmit] = useState(false)

    const formValuesIns = useSelector((state: RootState<any, any, any>) =>
        state.formValuesIns) as unknown as FvDeudaGobInstrumentos;

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaData) as unknown as ResponseConsultaData;

    const fieldRequired = useSelector((state: RootState<any, any, any>) => 
        state.fieldRequiredGub) as unknown as IsFieldModifiedFvDdGobIns;

    const dispatch = useDispatch();

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (await validateFormFields(consultaData.body, dispatch, requeridosGuber, fieldRequired)) {
            setLoadingSubmit(true)
            await fetchDataPost("/instrumentos/gubernamentales/guarda-info",
                "al intentar guardar datos",
                consultaData.body,
                {s_user: userEncoded()})
            setLoadingSubmit(false)
            dispatch(updateNInfoGuber(3))
        }
    }

    const handleNuevoClick = () => {
        dispatch(updateIsNewSerieGub(false))
        dispatch(updateIsNewFormGub(true))
        dispatch(showCuponesTasas(true))
        eraseSerieEmisora()
        dispatch(updateNInfoGuber(2))
    }

    
    const handleCalculator = async  (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setIsModalOpen(true);

    };

    const handleNuevaSerieClick = async () => {
        console.log(formValuesIns.s_emisora)
        if (await validateFormFields(formValuesIns, dispatch, requeridosGuber)) {
            dispatch(updateTriggerEraseGub(true))
            setTimeout(() => {
                dispatch(updateSelectedSerie(""))
                const newFormValuesIns = {
                    body: {
                        ...formValuesIns,
                        s_serie: ""
                    }
                };
                dispatch(updateConsultaData(<ResponseConsultaData>newFormValuesIns));
                dispatch(updateIsNewSerieGub(true))
                dispatch(updateIsNewFormGub(false))
                dispatch(updateNInfoGuber(5))
                dispatch(updateTriggerEraseGub(false))
            }, 10);
        }
    }
    const eraseSerieEmisora = () => {
        dispatch(updateTriggerEraseGub(true))
        setTimeout(() => {
            dispatch(updateSelectedTv(""));
            dispatch(updateSelectedEmisora(""));
            dispatch(updateSelectedSerie(""));
            dispatch(updateTV([]))
            dispatch(updateEmisora([]));
            dispatch(updateSerie([]));
            dispatch(updateConsultaData({} as ResponseConsultaData))
            dispatch(updateTriggerEraseGub(false))
        }, 10);
    }

    const handleLimpiarClick = () => {
        eraseConsultaData(consultaData, dispatch);
        eraseFormValuesInstrumentos(formValuesIns, dispatch);
        eraseFormInsValues(formValuesIns, dispatch)
        dispatch(showCuponesTasas(false));
        dispatch(updateIsNewFormGub(false))
        dispatch(updateIsNewSerieGub(false))
        dispatch(updateRequiredTv(false))
        dispatch(updateRequiredEmisora(false))
        dispatch(updateRequiredSerie(false))
        dispatch(updateNInfoGuber(0))
        dispatch(updateFieldRequiredGub({} as IsFieldModifiedFvDdGobIns))
        eraseSerieEmisora();
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
    }

    return {loadingSubmit, 
            requeridosGuber, 
            handleSubmit, 
            handleLimpiarClick, 
            handleNuevoClick, 
            handleNuevaSerieClick,
            handleCalculator,
            isModalOpen,
            handleModalClose
        }
}