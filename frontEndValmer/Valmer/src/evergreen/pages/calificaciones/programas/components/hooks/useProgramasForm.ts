import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {ApiResponseCalificaciones, CalifProgramas, RatingAgency} from "../../../../../../model";
import React, {useEffect, useState} from "react";
import {valmerApi} from "../../../../../../api";
import {updateCalifProCarac, updateDataCalifProgramas} from "../../../../../../redux/Calificaciones/Programas/actions";
import {showAlert} from "../../../../../../utils";

export const useProgramasForm = () => {
    const catalogProCarac = useSelector((state: RootState<any, any, any>) =>
        state.catalogProCarac) as unknown as RatingAgency;

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.califProgramasData) as unknown as CalifProgramas;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerEraseCalifPro) as unknown as boolean;

    const [loading, setLoading] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        if (!catalogProCarac || Object.keys(catalogProCarac).length === 0) {
            setLoading(true);
            valmerApi.get<ApiResponseCalificaciones>('/calificaciones/programas/tabla-calificadora')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCalifProCarac(response.data.body));
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
    }, [catalogProCarac]);

    useEffect(() => {
        console.log(catalogProCarac['fitch'])
    }, [catalogProCarac]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        const updatedFormData = {
            ...formData,
            [name]: value,
        };

        dispatch(updateDataCalifProgramas(updatedFormData))
    };

    return {formData, triggerErase, loading, catalogProCarac, handleChange}
}