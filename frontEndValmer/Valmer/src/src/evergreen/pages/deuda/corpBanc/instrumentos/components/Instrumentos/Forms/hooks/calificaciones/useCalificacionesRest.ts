import {useEffect, useState} from "react";
import {valmerApi} from "../../../../../../../../../../api";
import {showAlert} from "../../../../../../../../../../utils";
import {Catalogo, ResponseDataCorp} from "../../../../../../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateCatalogCalifCorp} from "../../../../../../../../../../redux";

export const useCalificacionesRest = () => {

    const catalog = useSelector((state: RootState<any, any, any>) =>
        state.catalogCalifCorp) as unknown as Catalogo[];
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch()

    useEffect(() => {
        if (!catalog || catalog.length === 0) {
            setLoading(true)
            valmerApi.get<ResponseDataCorp>('/deuda/catalogos/calificaciones')
                .then(response => {
                    setLoading(false);
                    dispatch(updateCatalogCalifCorp(response.data.body.catalogos))
                })
                .catch(async error => {
                    setLoading(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Calificaciones', error.message);
                    }
                })
        }
    }, [catalog]);

    return { catalog, loading };
}