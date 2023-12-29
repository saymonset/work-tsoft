import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CalifEmisoraData, CalifEmisoras} from "../../../../../../model";
import React, {useEffect, useState} from "react";
import {fetchDataGetRet} from "../../../../../../utils";
import {updateFormCalifEmi} from "../../../../../../redux/Calificaciones/Emisoras/actions";

export const useEmisorasForm = () => {

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.formCalifEmi) as unknown as CalifEmisoraData;

    const dispatch = useDispatch()

    const [catalogEmi, setCatalogEmi] = useState<CalifEmisoras>({} as CalifEmisoras);

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const getCatalog = async () => {
            setLoading(true);
            const response = await fetchDataGetRet(
                "/calificaciones/emisoras/tabla-calificadora",
                " al obtener catalogos",
                {})

            setCatalogEmi(response);
            setLoading(false);
        }
        if (!catalogEmi?.body) {
            getCatalog().then();
        }
    }, [catalogEmi]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;

        const updatedFormData = {
            ...formData,
            [name]: value,
        };

        dispatch(updateFormCalifEmi(updatedFormData))
    };

    return {formData, loading, catalogEmi, handleChange}
}

