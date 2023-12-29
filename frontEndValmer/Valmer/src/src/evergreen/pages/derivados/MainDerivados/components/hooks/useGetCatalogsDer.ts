import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Catalogo, ResponseDataCorp} from "../../../../../../model";
import {useEffect, useState} from "react";
import {showAlert} from "../../../../../../utils";
import {updateCatalogDer} from "../../../../../../redux";
import {valmerApi} from "../../../../../../api";

export const useGetCatalogsDer = () => {

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalogDeudaDer) as unknown as Catalogo[];

    const [loading, setLoading] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
        if (!catalog || catalog.length === 0) {
            setLoading(true)
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCatalogDer(response.data.body.catalogos));
                })
                .catch(async error => {
                    setLoading(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error catalogo deuda der', error.message);
                    }
                })
        }
    }, [catalog]);

    return {catalog, loading};
};