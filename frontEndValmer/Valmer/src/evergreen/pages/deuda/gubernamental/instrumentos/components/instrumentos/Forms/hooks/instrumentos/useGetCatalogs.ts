import { useState, useEffect } from 'react';
import {valmerApi} from "../../../../../../../../../../api";
import {showAlert} from "../../../../../../../../../../utils";
import {Catalogo, ResponseDataCorp} from "../../../../../../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateCatalog} from "../../../../../../../../../../redux";

export const useGetCatalogs = () => {

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalog) as unknown as Catalogo[];
    const [loading, setLoading] = useState(false);

    const dispatch = useDispatch();


    useEffect(() => {
        if (!catalog || catalog.length === 0) {
            setLoading(true)
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCatalog(response.data.body.catalogos));
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


    return { catalog, loading };
};