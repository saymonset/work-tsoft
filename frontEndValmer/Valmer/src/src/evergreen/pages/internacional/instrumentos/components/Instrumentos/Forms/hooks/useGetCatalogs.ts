import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { useDispatch, useSelector } from "react-redux";
import { Catalogo, ResponseDataCorp } from "../../../../../../../../model";
import { useEffect, useState } from "react";
import { updateCatalogCalifInter, updateCatalogInter } from "../../../../../../../../redux";
import { showAlert } from "../../../../../../../../utils";
import {valmerApi} from "../../../../../../../../api";

export const useGetCatalogs = () => {

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalogInter) as unknown as Catalogo[];

    const catalogCalif = useSelector((state: RootState<any, any, any>) =>
        state.catalogCalifInter) as unknown as Catalogo[];

    const [loading, setLoading] = useState(false);
    const [loadingCalif, setLoadingCalif] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
        if(!catalog || catalog.length === 0) {
            setLoading(true)
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCatalogInter(response.data.body.catalogos));
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
    }, [catalog]);

    useEffect(() => {
        if(!catalogCalif || catalogCalif.length === 0) {
            setLoadingCalif(true)
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos/calificaciones')
                .then(response => {
                    setLoadingCalif(false);
                    dispatch(updateCatalogCalifInter(response.data.body.catalogos))
                })
                .catch(async error => {
                    setLoadingCalif(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Calificaciones', error.message);
                    }
                })
        }
    }, [catalogCalif]);

    return { 
        catalog, 
        loading,
        catalogCalif,
        loadingCalif 
    };
};