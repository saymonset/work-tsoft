import React, {useState} from "react";
import {useCalificacionesRest} from "./useCalificacionesRest";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateConsultaData, updateNInfoGuber} from "../../../../../../../../../../redux";
import {ResponseConsultaData} from "../../../../../../../../../../model";
import {handleSubmitCalif} from "../../../../../../../../../../utils";
export const useCalificacionesProcess = () => {

    const [loadingSubmitCalif, setLoadingSubmitCalif] = useState(false)
    const { catalog, loading } = useCalificacionesRest();
    const dispatch = useDispatch();

    const consultaData = useSelector((state: RootState<any, any, any>) => 
        state.consultaData) as unknown as ResponseConsultaData;

    const handleChange = <T extends HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>(e: React.ChangeEvent<T>) => {
        const { name, value } = e.target;
        const updatedData: ResponseConsultaData = { body: {...consultaData.body, [name]: value} };
        dispatch(updateConsultaData(updatedData));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        await handleSubmitCalif(e, consultaData.body, setLoadingSubmitCalif)
        dispatch(updateNInfoGuber(4))
    }

    return {
        loadingSubmitCalif,
        catalog,
        loading,
        consultaData,
        handleChange,
        handleSubmit
    }
}