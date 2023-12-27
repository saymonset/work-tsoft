import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {initAcciones, IsFieldRequiredAccInst, RequeridosAcc, RespAccInstData} from "../../../../../model";
import {useEffect, useRef, useState} from "react";
import {
    updateConsultaDataAccInst,
    updateDividendosTable,
    updateNewFormInst,
    updateRequiredEmisoraAcc,
    updateRequiredFieldAccInst,
    updateRequiredSerieAcc,
    updateRequiredTvAcc,
    updateSelectedEmisoraAcc,
    updateSelectedSerieAcc,
    updateSelectedTvAcc,
    updateShowCarRvAcc,
    updateShowPrecalc,
    updateTriggerEraseAcc
} from "../../../../../redux";
import {fetchDataPost, updateMissingFields, userEncoded, validateFormAccFields} from "../../../../../utils";

export const useInstrumentosAcc = () => {

    const requeridosAcc: RequeridosAcc = {
        s_tv: useRef<HTMLInputElement | HTMLSelectElement | null>(null),
        s_emisora: useRef<HTMLInputElement | HTMLSelectElement | null>(null),
        s_serie: useRef<HTMLInputElement | HTMLSelectElement | null>(null),
        n_tipo_instrumento: useRef<HTMLSelectElement | null>(null),
        n_tipo_mercado: useRef<HTMLSelectElement | null>(null),
        n_pais: useRef<HTMLSelectElement | null>(null),
        n_fam_instrumento: useRef<HTMLSelectElement | null>(null),
        n_precio: useRef<HTMLInputElement | null>(null),
        n_moneda: useRef<HTMLSelectElement | null>(null),
        s_isin: useRef<HTMLInputElement | null>(null),
        n_status: useRef<HTMLSelectElement | null>(null),
        n_crv_ref: useRef<HTMLSelectElement | null>(null),
        n_crv_desc: useRef<HTMLSelectElement | null>(null),
        s_dividendo: useRef<HTMLInputElement | null>(null),
        n_tipo_rv: useRef<HTMLSelectElement | null>(null),
        n_equ_sec: useRef<HTMLSelectElement | null>(null),
        n_pais_o: useRef<HTMLSelectElement | null>(null),
        n_theo_model: useRef<HTMLSelectElement | null>(null),
        n_mkt_model: useRef<HTMLSelectElement | null>(null)
    }

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvAcc) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraAcc) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieAcc) as unknown as string;

    const showCarRvAcc = useSelector((state: RootState<any, any, any>) =>
        state.showCarRvAcc) as unknown as boolean;

    const fieldRequiredAccInst = useSelector(
        (state: RootState<any, any, any>) => state.fieldRequiredAccInst
    ) as unknown as IsFieldRequiredAccInst

    const [isOpenModal, setIsOpenModal] = useState(false);

    const [loading, setLoading] = useState<boolean>(false)

    const dispatch = useDispatch()

    const handleNewForm = () => {
        dispatch(updateNewFormInst(true))
        dispatch(updateTriggerEraseAcc(true))
        setTimeout(() => {
            dispatch(updateShowPrecalc(false))
            dispatch(updateShowCarRvAcc(false))
            dispatch(updateSelectedTvAcc(""))
            dispatch(updateSelectedEmisoraAcc(""))
            dispatch(updateSelectedSerieAcc(""))
            dispatch(updateConsultaDataAccInst({} as RespAccInstData))
            dispatch(updateTriggerEraseAcc(false))
        }, 10)
    }


    const handleErase = () => {
        dispatch(updateTriggerEraseAcc(true))
        setTimeout(() => {
            dispatch(updateShowPrecalc(false))
            dispatch(updateNewFormInst(false))
            dispatch(updateShowCarRvAcc(false))
            dispatch(updateSelectedTvAcc(""))
            dispatch(updateSelectedEmisoraAcc(""))
            dispatch(updateSelectedSerieAcc(""))
            dispatch(updateConsultaDataAccInst({} as RespAccInstData))
            dispatch(updateTriggerEraseAcc(false))
            dispatch(updateRequiredFieldAccInst({} as IsFieldRequiredAccInst))
            dispatch(updateRequiredTvAcc(false))
            dispatch(updateRequiredEmisoraAcc(false))
            dispatch(updateRequiredSerieAcc(false))
            dispatch(updateDividendosTable([]))
        }, 10)
    }

    const handleOpenCaracCv = () => {
        setIsOpenModal(true);
    }

    const handleCloseCaracCv = () => {
        setIsOpenModal(false);
    }

    const handleSave = async () => {

        const updatedAcciones = updateMissingFields(
            consultaDataAccInst?.body?.acciones,
            initAcciones,
            selectedTv,
            selectedEmisora,
            selectedSerie);

        const updatedConsultaDataAccInst = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst.body,
                acciones: updatedAcciones
            }
        };

        const formValues = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst.body,
                acciones: {
                    ...consultaDataAccInst?.body?.acciones,
                    s_tv: selectedTv,
                    s_emisora: selectedEmisora,
                    s_serie: selectedSerie
                }
            }
        }

        if (await validateFormAccFields(formValues?.body?.acciones, dispatch, requeridosAcc, fieldRequiredAccInst)) {
            setLoading(true)
            await fetchDataPost(
                "/acciones/instrumentos/actualiza-info",
                " al guardar instrumentos acciones",
                updatedConsultaDataAccInst.body.acciones,
                { s_user: userEncoded() }
            );
            setLoading(false)
        }
    };
    
    useEffect(() => {
        handleErase()
    }, [])

    return {
        isOpenModal,
        loading,
        showCarRvAcc,
        requeridosAcc,
        handleOpenCaracCv,
        handleCloseCaracCv,
        handleErase,
        handleNewForm,
        handleSave
    }
}