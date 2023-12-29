import { useEffect, useState } from "react";
import {valmerApi} from "../../../../../api";
import {CatalogoSubastas, ResponseDataSubastas} from "../../../../../model";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";
import { updateCatalogSubastas } from "../../../../../redux";
import { showAlert } from "../../../../../utils";
export const useGetCatalogsSubastas = () => {

    const catalog = useSelector((state: RootState<any, any, any>) => 
        state.catalogSub) as unknown as CatalogoSubastas[];
    const [loading, setLoading] = useState(true);

    const dispatch = useDispatch();

    useEffect(() => {
        valmerApi.get<ResponseDataSubastas>("/subastas/catalogos")
            .then(response => {
                setLoading(false);
                dispatch(updateCatalogSubastas(response.data.body.catalogos));
            })
            .catch(async error => {
                setLoading(false);
                if(error.message.includes('Network Error')) {
                    await showAlert('error', 'Error', 'No hay conexión con el servidor');
                } else {
                    await showAlert('error', 'Error catalogo Subastas', error.message);
                }
            })
    }, []);

    return {loading, catalog};
}