import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useDispatch, useSelector } from "react-redux"
import { Catalogo, ResponseCat, ResponseDataCorp } from "../../../../../model"
import { useEffect, useState } from "react";
import {valmerApi} from "../../../../../api";
import { updateCatTipoUser, updateCatUri, updateCatalogoInst } from "../../../../../redux";
import { showAlert } from "../../../../../utils";

export const useGetCatalogs = () => {

    const catalogoInst = useSelector(
        (state: RootState<any, any, any>) => state.catalogoInst
    ) as unknown as Catalogo[];

    const catNom = useSelector(
        (state: RootState<any, any, any>) => state.catNom
    ) as unknown as ResponseCat;

    const catTipoUser = useSelector(
        (state: RootState<any, any, any>) => state.catTipoUser
    ) as unknown as ResponseCat;

    const catUri = useSelector(
        (state: RootState<any, any, any>) => state.catUri
    ) as unknown as ResponseCat;

    const [loadingInst, setLoadingInst] = useState(false)
    const [loadingTipoUser, setLoadingTipoUser] = useState(false)
    const [loadingCatUri, setLoadingCatUri] = useState(false)
    const [dataUri, setDataUri] = useState<Record<string, string>>({})

    const dispatch = useDispatch();

    useEffect(() => {
        if(!catalogoInst || catalogoInst.length === 0) {
            setLoadingInst(true)
            valmerApi
                .get<ResponseDataCorp>('/admin-user-web/institucion')
                .then((response) => {
                    dispatch(updateCatalogoInst(response.data.body.catalogos))
                    setLoadingInst(false)
                })
                .catch(async (error) => {
                    setLoadingInst(false);
                    if (error.message.includes('Network Error')) {
                      await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                      await showAlert('error', 'Error', error.message);
                    }
                });
        }
    }, [catalogoInst]);

    useEffect(() => {
        if(!catTipoUser.body) {
            setLoadingTipoUser(true)
            valmerApi
                .get<ResponseCat>('/admin-user-web/tipo-usuario')
                .then((response) => {
                    dispatch(updateCatTipoUser(response.data))
                    setLoadingTipoUser(false)
                })
                .catch(async (error) => {
                    setLoadingTipoUser(false)
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error', error.message);
                    }
                });
        }
    }, [catTipoUser]);

    useEffect(() => {
        if(!catUri.body) {
            setLoadingCatUri(true)
            valmerApi
                .get<ResponseCat>('/admin-user-web/uri')
                .then((response) => {
                    dispatch(updateCatUri(response.data))
                    setDataUri(response.data.body)
                })
                .catch(async (error) => {
                    if (error.message.includes('Network Error')) {
                        await showAlert('error', 'Error', 'No hay conexión con el servidor');
                    } else {
                        await showAlert('error', 'Error', error.message);
                    }
                }).finally(() => {
                setLoadingCatUri(false)
            });
        }
    }, [catUri]);

    return {
        catalogoInst,
        loadingInst,
        catNom,
        loadingTipoUser,
        catTipoUser,
        loadingCatUri,
        dataUri,
        catUri,
        setDataUri
    }
}