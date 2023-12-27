import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CalifEmisoraData, Catalogo} from "../../../../../../model";
import React, {useEffect, useState} from "react";
import {fetchDataGetRet, fetchDataPostRet, userEncoded} from "../../../../../../utils";
import {updateFormCalifEmi} from "../../../../../../redux/Calificaciones/Emisoras/actions";

export const useEmisorasHeader = () => {

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.formCalifEmi) as unknown as CalifEmisoraData;

    const dispatch = useDispatch()

    const [catalog, setCatalog] = useState<Catalogo[]>([]);
    const [loading, setLoading] = useState(false);
    const [loadingSave, setLoadingSave] = useState(false);


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

        dispatch(updateFormCalifEmi(updatedFormData))
    };

    const handleSave = async () => {
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

    return {
        loadingSave,
        loading,
        catalog,
        handleSave,
        handleChange
    }
}