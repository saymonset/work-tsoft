import {DerDefHandleDataProps} from "../../../../../../model"
import {useTvDerDef} from "./useTvDerDef"
import {useGetCatalogsDefDer, useGetCatalogsUnderlying} from "./index";
import {useGetCatalogsDer} from "../../../MainDerivados/components/hooks";
import { useTvEmiDef } from "./useTvEmiDef"
import React from "react";
import { useDispatch } from "react-redux";
import { updateFieldRequiredDerivados } from "../../../../../../redux";

export const useDerDefHandleData = (props: DerDefHandleDataProps) => {

    const {tv, loadingTv, serieOp, loadingSerie} = useTvDerDef()

    const {catalog, loading} = useGetCatalogsDer();
    const {catalogDefDer, loadingDefDer} = useGetCatalogsDefDer();
    const {catalogUnderlying, loadingUnderlying,
        catalogDcsCurveUnd, loadingDcsCurveUnd
    } = useGetCatalogsUnderlying();

    const {
        loadingConsultaData,
        loadingEmisoras,
        emisora,
        tipoTv,
        handleTv,
        handleEmisora,
        handleSelectedInst
    } = useTvEmiDef(props)

    const dispatch = useDispatch()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value} = e.target

        const updatedData = {
            ...props.consultaDataDerDef,
            body: {
                ...props.consultaDataDerDef.body,
                [name]: value
            }
        }

        const updatedFieldRequired = {...props.fieldRequiredDefDerivados, [name]: false}

        props.setConsultaDataDerDef(updatedData)

        dispatch(updateFieldRequiredDerivados(updatedFieldRequired))
    }

    const handleCheckChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target

        const updatedData = {
            ...props.consultaDataDerDef,
            body: {
                ...props.consultaDataDerDef.body,
                [name]: checked ? '1' : '0'
            }
        }
        
        props.setConsultaDataDerDef(updatedData)
    }

    const checkEval = (name: string): boolean => {
        const data = Object(props.consultaDataDerDef.body);
        return data[name] === '1';
    }

    return {
        catalog,
        loading,
        catalogDefDer,
        loadingDefDer,
        catalogUnderlying,
        loadingUnderlying,
        catalogDcsCurveUnd,
        loadingDcsCurveUnd,
        loadingConsultaData,
        loadingEmisoras,
        emisora,
        tv,
        loadingTv,
        serieOp,
        loadingSerie,
        tipoTv,
        handleTv,
        handleEmisora,
        handleChange,
        handleCheckChange,
        checkEval,
        handleSelectedInst
    }
}