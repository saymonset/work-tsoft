import {useState, useEffect} from 'react';
import {showAlert} from "../../../../../../utils";
import {valmerApi} from "../../../../../../api";
import {Catalogo, RespDataDcsCurveUnd, RespDataUnderlying} from "../../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {updateCatalogDcsCurveUnd, updateCatalogUnderlying} from "../../../../../../redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const useGetCatalogsUnderlying = () => {

    const catalogUnderlying = useSelector((state: RootState<any, any, any>) =>
        state.catalogUnderlying) as unknown as Catalogo[];
    const catalogDcsCurveUnd = useSelector((state: RootState<any, any, any>) =>
        state.catalogDcsCurveUnd) as unknown as Catalogo[];

    const [loadingUnderlying, setLoadingUnderlying] = useState(false);
    const [loadingDcsCurveUnd, setLoadingDcsCurveUnd] = useState(false);

    const dispatch = useDispatch();

    const loadCatalogoDer = (catalogName: string, catalogRegistros: string []) => {
        const catalogoDer: Catalogo = {
            catalogo: catalogName,
            registros: {}
        };
        catalogRegistros.forEach((str) => {
            catalogoDer.registros[str] = str;
        });
        return catalogoDer;
    }


    useEffect(() => {
        if (!catalogUnderlying || catalogUnderlying.length === 0) {
            setLoadingUnderlying(true)
            valmerApi.get<RespDataUnderlying>('/derivados/def/underlying')
                .then(response => {
                    setLoadingUnderlying(false);
                    const catalogFinal = [
                        loadCatalogoDer("s_underlying", response.data.body.s_underlying),
                        loadCatalogoDer("s_underlying_cu", response.data.body.s_underlying_cu),
                        loadCatalogoDer("s_underlying_sp", response.data.body.s_underlying_sp),
                        loadCatalogoDer("s_underlying_sp_cu", response.data.body.s_underlying_sp_cu),
                    ];
                    dispatch(updateCatalogUnderlying(catalogFinal));

                })
                .catch(async error => {
                    setLoadingUnderlying(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Instrumento', error.message);
                    }
                })
        }
    }, [catalogUnderlying]);

    useEffect(() => {
        if (!catalogDcsCurveUnd || catalogDcsCurveUnd.length === 0) {
            setLoadingDcsCurveUnd(true)
            valmerApi.get<RespDataDcsCurveUnd>('derivados/def/dcs-curve-und')
                .then(response => {
                    setLoadingDcsCurveUnd(false);
                    const catalogFinal = [
                        loadCatalogoDer("dcs-curve-und", response.data.body),
                    ];
                    dispatch(updateCatalogDcsCurveUnd(catalogFinal));

                })
                .catch(async error => {
                    setLoadingDcsCurveUnd(false);
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error Instrumento', error.message);
                    }
                })
        }
    }, [loadingDcsCurveUnd]);

    return {catalogUnderlying, loadingUnderlying, catalogDcsCurveUnd, loadingDcsCurveUnd};
};