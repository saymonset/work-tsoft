import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {RespAccInstData} from "../../../../../../../../model";
import React from "react";
import {updateConsultaDataAccInst} from "../../../../../../../../redux";

export const usePrecalcPersist = () => {

    const consultaDataAccInst = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataAccInst) as unknown as RespAccInstData;

    const dispatch = useDispatch()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;

        const updatedValues = {
            ...consultaDataAccInst,
            body: {
                ...consultaDataAccInst?.body,
                acciones: {
                    ...consultaDataAccInst?.body?.acciones,
                    [name]: value
                }
            }
        };

        dispatch(updateConsultaDataAccInst(updatedValues));
    };

    return {handleChange}
}