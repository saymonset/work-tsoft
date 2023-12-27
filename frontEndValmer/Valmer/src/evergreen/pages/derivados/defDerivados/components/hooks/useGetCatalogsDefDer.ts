import {useState, useEffect} from 'react';
import {showAlert} from "../../../../../../utils";
import {valmerApi} from "../../../../../../api";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateCatalogDefDer} from "../../../../../../redux";
import {Catalogo, ResponseDataCorp} from "../../../../../../model";

export const useGetCatalogsDefDer = () => {

    const catalogDefDer = useSelector((state: RootState<any, any, any>) =>
        state.catalogDefDer) as unknown as Catalogo[];

    const [loadingDefDer, setLoadingDefDer] = useState(false);

    const dispatch = useDispatch();


    useEffect(() => {
        if (!catalogDefDer || catalogDefDer.length === 0) {
            setLoadingDefDer(true)
            valmerApi.get<ResponseDataCorp>('/derivados/def/catalogos')
                .then(response => {
                    setLoadingDefDer(false);
                    dispatch(updateCatalogDefDer(response.data.body.catalogos));
                })
                .catch(async error => {
                    setLoadingDefDer(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Instrumento', error.message);
                    }
                })
        }
    }, [catalogDefDer]);
    return {catalogDefDer, loadingDefDer};
};