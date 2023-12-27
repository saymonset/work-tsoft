import {Catalogo, ResponseDataCorp} from "../../../../../../model";
import {useEffect, useState} from "react";
import {valmerApi} from "../../../../../../api";
import {showAlert} from "../../../../../../utils";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateCatalogAcc} from "../../../../../../redux";

export const useGetCatalogsDeuda = () => {

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalog) as unknown as Catalogo[];

    const [loading, setLoading] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        const fetchCatalogs = async () => {
            setLoading(true);
            try {
                const responseDeuda =
                    await valmerApi.get<ResponseDataCorp>('/deuda/catalogos');

                const responseAcciones =
                    await valmerApi.get<ResponseDataCorp>('/acciones/instrumentos/catalogos');

                dispatch(updateCatalogAcc([
                    ...responseDeuda.data.body.catalogos,
                    ...responseAcciones.data.body.catalogos]));

            }
            catch (error: any)
            {
                if (error.message.includes('Network Error')) {
                    await showAlert('error', 'Error', 'No hay conexión con el servidor');
                } else {
                    await showAlert('error', 'Error catalogo deuda der', error.message);
                }
            } finally {
                setLoading(false);
            }
        };

        if (!catalog || catalog.length === 0) {
            fetchCatalogs().then();
        }
    }, [catalog]);

    return { catalog, loading };
}