import {useDispatch, useSelector} from "react-redux";
import {
    updateEmisoraCorp,
    updateIsNewFormCorp,
    updateIsNewSerieCorp,
    updateSelectedEmisoraCorp,
    updateSelectedSerieCorp,
    updateSelectedTvCorp,
    updateSerieCorp,
    updateRequiredTvCorp,
    updateRequiredEmisoraCorp,
    updateRequiredSerieCorp,
    updateSelectDatePrice,
    updateTriggerEraseCorp,
    updateFormValuesCorp, updateFieldRequiredCorp, updateConsultaDataCorp,
} from '../../../../../../redux';
import {
    eraseConsultaData,
    eraseFormInsValues,
    eraseFormValuesInstrumentos,
    fetchDataPost, getCurrentDate,
    userEncoded,
    validateFormFields
} from "../../../../../../utils";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    FvDeudaCorpInstrumentos,
    HTMLInputSelectNull,
    IsFieldModifiedFvDdGobIns,
    RequeridosCorp,
    ResponseConsultaDataCorp
} from "../../../../../../model";
import React, {useRef, useState} from "react";

export const useEraseData = () => {

    const requeridosCorp: RequeridosCorp = {
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
        s_tv_ref_1: useRef<HTMLSelectElement | null>(null),
        s_emisora_ref_1: useRef<HTMLSelectElement | null>(null),
        s_serie_ref_1: useRef<HTMLSelectElement | null>(null),
        s_tv_ref_2: useRef<HTMLSelectElement | null>(null),
        s_emisora_ref_2: useRef<HTMLSelectElement | null>(null),
        s_serie_ref_2: useRef<HTMLSelectElement | null>(null),
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
        n_periodo_cupon_v: useRef<HTMLInputElement | null>(null),
        s_inscrito_bolsa: useRef<HTMLSelectElement | null>(null),
    }

    const [loadingSubmit, setLoadingSubmit] = useState(false)

    const formValuesCorp = useSelector((state: RootState<any, any, any>) =>
        state.formValuesCorp) as unknown as FvDeudaCorpInstrumentos;
    
    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaDataCorp) as unknown as ResponseConsultaDataCorp;

    const fieldRequiredCorp = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredCorp) as unknown as IsFieldModifiedFvDdGobIns;

    const dispatch = useDispatch();

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (await validateFormFields(consultaData.body, dispatch, requeridosCorp, undefined , fieldRequiredCorp)) {
            setLoadingSubmit(true)
            await fetchDataPost("/instrumentos/corporativos/guarda-info",
                "al intentar guardar datos",
                consultaData.body,
                {s_user: userEncoded()})

            dispatch(updateFieldRequiredCorp({} as IsFieldModifiedFvDdGobIns))
            setLoadingSubmit(false)
        }
    }

    const handleNuevoClick = () => {
        setTimeout(() => {
            dispatch(updateIsNewFormCorp(false))
        },10)
        setTimeout(() => {
            dispatch(updateSelectedTvCorp(""));
            dispatch(updateFieldRequiredCorp({} as IsFieldModifiedFvDdGobIns))
            dispatch(updateIsNewSerieCorp(false))
            eraseConsultaData(consultaData, dispatch);
            eraseFormValuesInstrumentos(formValuesCorp, dispatch);
            eraseFormInsValues(formValuesCorp, dispatch)
            dispatch(updateIsNewFormCorp(true))
        },30)

        let updatedData: ResponseConsultaDataCorp = { body: { ...consultaData.body,
                d_fecha_emision: getCurrentDate(),
                d_fecha_ini_cupon: getCurrentDate(),
                d_fecha_primer_cupon: getCurrentDate() } };

        setTimeout(() => {
            dispatch(updateConsultaDataCorp(updatedData))
        },30)
    }

    const handleNuevaSerieClick = async () => {
        if (await validateFormFields(formValuesCorp, dispatch, requeridosCorp)) {
            dispatch(updateTriggerEraseCorp(true))
            setTimeout(() => {
                dispatch(updateSelectedSerieCorp(""));
                const newFormValuesIns = {
                    ...formValuesCorp,
                    s_serie: ""
                };
                dispatch(updateFormValuesCorp(<FvDeudaCorpInstrumentos>newFormValuesIns));
                dispatch(updateIsNewFormCorp(false))
                dispatch(updateIsNewSerieCorp(true))
                dispatch(updateTriggerEraseCorp(false))
            }, 10);
        }
    }

    const eraseSerieEmisora = () => {
        dispatch(updateTriggerEraseCorp(true))
        setTimeout(() => {
            dispatch(updateSelectedTvCorp(""));
            dispatch(updateSelectedEmisoraCorp(""));
            dispatch(updateSelectedSerieCorp(""));
            dispatch(updateEmisoraCorp([]));
            dispatch(updateSerieCorp([]));
            dispatch(updateTriggerEraseCorp(false))
        }, 10);
    }

    const handleLimpiarClick = () => {
        dispatch(updateSelectDatePrice(""))
        eraseConsultaData(consultaData, dispatch);
        eraseFormValuesInstrumentos(formValuesCorp, dispatch);
        eraseFormInsValues(formValuesCorp, dispatch)
        dispatch(updateIsNewFormCorp(false))
        dispatch(updateIsNewSerieCorp(false))
        dispatch(updateRequiredTvCorp(false))
        dispatch(updateRequiredEmisoraCorp(false))
        dispatch(updateRequiredSerieCorp(false))
        dispatch(updateFieldRequiredCorp({} as IsFieldModifiedFvDdGobIns))
        eraseSerieEmisora();
    };


    return {
        requeridosCorp,
        loadingSubmit,
        handleSubmit,
        handleNuevoClick,
        handleNuevaSerieClick,
        handleLimpiarClick
    }
}
