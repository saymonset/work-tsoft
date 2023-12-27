import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import React, {useEffect, useState} from "react";
import {formEndpoint, formFields, showAlert, userEncoded} from "../../../../../../../utils";
import {valmerApi} from "../../../../../../../api";
import {FormName, FormsType, TasasConsultaResponse} from "../../../../../../../model";

export const useTasasForm = () => {
    const [loadingSave, setLoadingSave] = useState(false)

    const consultaTasas = useSelector((state: RootState<any, any, any>) =>
        state.consultaTasas) as unknown as TasasConsultaResponse;

    const [forms, setForms] = useState<FormsType>({});

    useEffect(() => {
        const newForms: FormsType = {};

        for (const form in formFields) {
            newForms[form] = {};
            formFields[form].forEach(field => {
                newForms[form][field] = consultaTasas[field as keyof typeof consultaTasas] || '';
            });
        }

        setForms(newForms);
    }, [consultaTasas]);

    const handleChange = (formName: string) =>
        (event: React.ChangeEvent<HTMLInputElement>) => {
            const { name, value } = event.target;
            setForms(prevForms => ({
                ...prevForms,
                [formName]: {
                    ...prevForms[formName],
                    [name]: value
                }
            }));
        };

    const handleSubmit = async (e: React.FormEvent, formName: FormName, d_fecha: string) => {
        e.preventDefault();
        const s_user = userEncoded() ?? ""
        let params = forms[formName];
        params = {...params, d_fecha, s_user}
        const url = formEndpoint[formName];
    

        try {
            setLoadingSave(true)
            await valmerApi.get(url, { params })
            await showAlert("success", "Actualizado", "Registro actualizado exitosamente")
            setLoadingSave(false)
        } catch (error: any) {
            if (error.message.includes('Network Error')) {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            } else {
                await showAlert('error', `Error al actualizar tasas`, error.message);
            }
        }
    };

    return {
        forms,
        loadingSave,
        handleSubmit,
        handleChange}

}