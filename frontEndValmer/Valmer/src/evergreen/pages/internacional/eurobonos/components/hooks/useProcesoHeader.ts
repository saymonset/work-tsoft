import React, {useEffect, useState} from "react";
import {getCurrentDate} from "../../../../../../utils";
import {useDispatch, useSelector} from "react-redux";
import {
    updateFechaEurobonos,
    updateFileContributor,
    updateFileWallet,
    updateIsRevFormInsumos,
    updateIsRevFormInsumos2, updateIsShowLogBox, updateIsShowLogBoxCont,
    updateRespClientWallet,
    updateRevisar1,
    updateRevisar2,
    updateSearchClientWallet,
    updateSearchContributor,
    updateSelectedClient,
    updateSelectedVector,
    updateShowFileContributor,
    updateShowFileWallet,
    updateShowTableWallet,
    updateTriggerEraseInt
} from "../../../../../../redux";
import {CargaArchivoContent, RespClientWallet, Revisar1Response, Revisar2Response, stateCheckbox} from "../../../../../../model";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";

export const useProcesoHeader = () => {

    useEffect(() => {
        handleErase()
    }, [])

    const selectedVector = useSelector((state: RootState<any, any, any>) => 
        state.selectedVector) as unknown as string
    const [currentDate, setCurrentDate] = useState<string>(getCurrentDate);
    const dispatch = useDispatch();

    const setFechaEurobonos = (e: string) => {
        setCurrentDate(e)
        dispatch(updateFechaEurobonos(e))
    }

    const setSelectVector = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if(e.target.value === '...')
        {
            dispatch(updateSelectedVector(""))
        }
        else
        {
            dispatch(updateSelectedVector(e.target.value))
        }
    }

    const handleErase = () => {
        dispatch(updateTriggerEraseInt(true))
        setTimeout(() => {
            dispatch(updateRevisar1({} as Revisar1Response))
            dispatch(updateIsRevFormInsumos(false))
            dispatch(updateRevisar2({} as Revisar2Response))
            dispatch(updateIsRevFormInsumos2(false))
            dispatch(updateShowTableWallet(false))
            dispatch(updateShowFileWallet(false))
            dispatch(updateFileWallet({} as CargaArchivoContent))
            dispatch(updateFileContributor({} as CargaArchivoContent))
            dispatch(updateRespClientWallet({} as RespClientWallet))
            dispatch(updateSelectedClient(""))
            dispatch(updateSearchClientWallet(""))
            dispatch(updateSearchContributor(""))
            dispatch(updateShowFileContributor(false))
            dispatch(updateTriggerEraseInt(false))
            dispatch(updateIsShowLogBox(false))
            dispatch(updateIsShowLogBoxCont(false))
            dispatch(updateSelectedVector("DEFINITIVO"))
            setCurrentDate(getCurrentDate)
        }, 10);
    }

    return {
        currentDate,
        selectedVector,
        setFechaEurobonos,
        setSelectVector,
        handleErase
    }
}