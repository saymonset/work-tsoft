import {IsFieldModifiedFvDerivados, ListItemType, RequeridosDerivados, RespDerivados} from "../../../../../../model";
import {useGetCatalogsDer, useTvDer} from "./index";
import React, {useRef, useState} from "react";
import {fetchDataPost, showAlert, userEncoded, validateFormDerivadosFields} from "../../../../../../utils";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { updateFieldRequiredDerivados } from "../../../../../../redux";

export const useDerHandleData = ({sMercado, urlSaveData}: {sMercado: string, urlSaveData: string}) => {

    const requeridosDer: RequeridosDerivados = {
        s_tv: useRef<HTMLSelectElement | null>(null),
        s_emisora: useRef<HTMLSelectElement | null>(null),
        s_serie: useRef<HTMLSelectElement | null>(null),
        n_tipo_instrumento: useRef<HTMLSelectElement | null>(null),
        n_fam_instrumento: useRef<HTMLSelectElement | null>(null),
        n_tipo_mercado: useRef<HTMLSelectElement | null>(null),
        n_pais: useRef<HTMLSelectElement | null>(null),
        n_moneda: useRef<HTMLSelectElement | null>(null),
        n_valor_nominal: useRef<HTMLInputElement | null>(null),
        n_crv_descuento: useRef<HTMLSelectElement | null>(null),
        n_tipo_tasa: useRef<HTMLSelectElement | null>(null),
        n_convencion_dias: useRef<HTMLSelectElement | null>(null),
        n_composicion_yield: useRef<HTMLSelectElement | null>(null),
        n_calendario: useRef<HTMLSelectElement | null>(null),
        n_tasa_cupon: useRef<HTMLInputElement | null>(null)
    }

    const [consultaDataDer, setConsultaDataDer]
        = useState<RespDerivados>({} as RespDerivados)

    const [loadingPersist, setLoadingPersist] = useState(false)

    const { tv, loadingTv } = useTvDer(sMercado, false);

    const { catalog, loading} = useGetCatalogsDer();

    const dispatch = useDispatch();
    const fieldRequiredDerivados = useSelector((state: RootState<any,any,any>) => state.fieldRequiredDerivados) as unknown as IsFieldModifiedFvDerivados;

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        const updatedData = {
            ...consultaDataDer,
            body: {
                ...consultaDataDer.body,
                [name]: value
            }
        };

        const updateRequiredData = {
            ...fieldRequiredDerivados, [name]: false
        }

        setConsultaDataDer(updatedData)

        dispatch(updateFieldRequiredDerivados(updateRequiredData))
    };

    const handleRowClick = (item: ListItemType) => {
        setConsultaDataDer(prevState => ({
            ...prevState,
            body: {
                ...prevState.body,
                s_consec: item.property,
                consec_precio_hoy: item.data.PRECIO_HOY,
                consec_precio_ayer: item.data.PRECIO_AYER
            }
        }));
    }

    const handleSubmitTeoricos = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (await validateFormDerivadosFields(consultaDataDer.body, dispatch, requeridosDer, true, true, fieldRequiredDerivados)) {
            setLoadingPersist(true)
            setTimeout(async () => {
                await showAlert("success", "Guardado","Teoricos Derivados");
                setLoadingPersist(false)
            }, 2000)
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        if(sMercado === "TEORICOS")
        {
            await handleSubmitTeoricos(event);
        }
        else {
            event.preventDefault();
            if(await validateFormDerivadosFields(consultaDataDer.body, dispatch, requeridosDer, false, true, fieldRequiredDerivados)){
                setLoadingPersist(true)
                await fetchDataPost(
                    urlSaveData,
                    " error al intentar guardar la informacion",
                    consultaDataDer.body,
                    {s_user: userEncoded()}
                )
                setLoadingPersist(false)
            }
        }
    }


    return {
        tv,
        loadingPersist,
        loading,
        loadingTv,
        catalog,
        consultaDataDer,
        fieldRequiredDerivados,
        requeridosDer,
        setConsultaDataDer,
        handleChange,
        handleRowClick,
        handleSubmit
    }
}