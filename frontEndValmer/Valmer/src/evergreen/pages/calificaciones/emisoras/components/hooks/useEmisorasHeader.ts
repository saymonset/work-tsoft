import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CalifEmisoraData, Catalogo, SelectOrNull, IsFieldReqCalifEmi, RefReqEmi, fieldToValidateCalifEmi} from "../../../../../../model";
import React, {useEffect, useState, useRef} from "react";
import {fetchDataGetRet, fetchDataPostRet, focusElement, userEncoded} from "../../../../../../utils";
import {updateFormCalifEmi} from "../../../../../../redux/Calificaciones/Emisoras/actions";

export const useEmisorasHeader = () => {

    const reqRefEmi: RefReqEmi = {
        n_emisor: useRef<SelectOrNull>(null),
        n_pais: useRef<SelectOrNull>(null)
    }

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.formCalifEmi) as unknown as CalifEmisoraData;

    const dispatch = useDispatch()

    const [catalog, setCatalog] = useState<Catalogo[]>([]);
    const [loading, setLoading] = useState(false);
    const [loadingSave, setLoadingSave] = useState(false);
    const [isFieldReqCalifEmi, setIsFieldReqCalifEmi] = useState<IsFieldReqCalifEmi>({} as IsFieldReqCalifEmi)


    useEffect(() => {
        const getCatalog = async () => {
            setLoading(true);
            const response = await fetchDataGetRet(
                "/deuda/catalogos",
                " al obtener catalogos",
                {})

            setCatalog(response.body.catalogos);
            setLoading(false);
        }
        if (!catalog || catalog.length === 0) {
            getCatalog().then()
        }

    }, [catalog]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        const updatedFormData = {
            ...formData,
            [name]: value,
        };

        setIsFieldReqCalifEmi({ ...isFieldReqCalifEmi, [name]: false })

        dispatch(updateFormCalifEmi(updatedFormData))
    };

    const handleSave = async () => {
        if (fieldValidate(formData, isFieldReqCalifEmi, reqRefEmi, setIsFieldReqCalifEmi)) {
            setLoadingSave(true)
            const response = await fetchDataPostRet(
                "calificaciones/emisoras/actualiza-info",
                " al guardar calificaciones emisoras",
                formData,
                { s_user: userEncoded() }
            )
    
            if (response) {
                dispatch(updateFormCalifEmi(response));
            }
            setLoadingSave(false)
        }
    }

    const fieldValidate = (
        formValues: CalifEmisoraData,
        isFieldReqCalifEmi: IsFieldReqCalifEmi,
        refReqEmi: RefReqEmi,
        setAction: React.Dispatch<React.SetStateAction<IsFieldReqCalifEmi>>
    ) => {

        for (const field of fieldToValidateCalifEmi) {
            if (isInvalidField(formValues[field.name], field.defaultValue)) {
                updateFieldAsInvalid(setAction, isFieldReqCalifEmi, field.name);
                focusElement(field.name, refReqEmi[field.name]);
                return false;
            }
        }
    
        return true;
    };
    
    const isInvalidField = (fieldValue: any, defaultValue: string) => !fieldValue || fieldValue === defaultValue || fieldValue == 0;
    
    const updateFieldAsInvalid = (
        setAction: React.Dispatch<React.SetStateAction<IsFieldReqCalifEmi>>, 
        isFieldReqCalifEmi: IsFieldReqCalifEmi, 
        fieldName: string
    ) => {
        setAction({ ...isFieldReqCalifEmi, [fieldName]: true });
    }

    return {
        loadingSave,
        loading,
        catalog,
        isFieldReqCalifEmi,
        handleSave,
        handleChange
    }
}