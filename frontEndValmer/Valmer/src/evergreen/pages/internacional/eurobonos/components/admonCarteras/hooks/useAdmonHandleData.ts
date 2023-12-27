import React, {useEffect, useState} from "react";
import {fetchDataGetConsultaData} from "../../../../../../../utils";
import {
    updateCatalogoInt,
    updateRespClientWallet,
    updateSearchClientWallet,
    updateSelectedClient,
    updateShowFileWallet,
    updateShowTableWallet
} from "../../../../../../../redux";
import {DataClient, RespCatalogoInt} from "../../../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {useSortData} from "./useSortData";

export const useAdmonHandleData = () => {

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const catalogInter = useSelector((state: RootState<any, any, any>) =>
        state.catalogInter) as unknown as RespCatalogoInt;

    const selectedClient = useSelector((state: RootState<any, any, any>) =>
        state.selectedClient) as unknown as string;

    const triggerErase = useSelector((state: RootState<any, any, any>) =>
        state.triggerEraseInt) as unknown as boolean;

    const showTableWallet = useSelector((state: RootState<any, any, any>) =>
        state.showTableWallet) as unknown as boolean;

    const showFileWallet = useSelector((state: RootState<any, any, any>) =>
        state.showFileWallet) as unknown as boolean;

    const searchClientWallet = useSelector((state: RootState<any, any, any>) =>
        state.searchClientWallet) as unknown as string;

    const [loadingInter, setLoadingInter] = useState(false)
    const [loadingQueryClient, setLoadingQueryClient] = useState(false)

    const dispatch = useDispatch()

    let {
        sortField,
        sortDirection,
        sortedData,
        setSortDirection,
        setSortField
    } = useSortData()


    useEffect(() => {
        const getDataClient = async () => {
            if (Object.keys(catalogInter).length === 0 || (catalogInter?.body?.catalogos?.length || 0) === 0) {
                setLoadingInter(true)
                await fetchDataGetConsultaData(
                    "/internacional/catalogos",
                    " al obtener catalogos internacional",
                    {},
                    updateCatalogoInt,
                    dispatch)
                setLoadingInter(false);
            }
        }
        getDataClient().then();
    }, []);


    const setSearchClient = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(updateSearchClientWallet(e.target.value))
    }
    const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        dispatch(updateSelectedClient(e.target.value));
    };

    const handleQueryClient = async () => {
        dispatch(updateShowTableWallet(false))
        dispatch(updateShowFileWallet(false))
        setLoadingQueryClient(true)
        await fetchDataGetConsultaData(
            "/internacional/proceso-eurobonos/consulta-cartera-clientes",
            " al obtener consulta-cartera-clientes",
            {n_institucion: selectedClient, d_fecha_val: fechaEurobonos},
            updateRespClientWallet,
            dispatch)
        setLoadingQueryClient(false)
        dispatch(updateShowTableWallet(true))
    }

    const handleShowFileWallet = async () => {
        dispatch(updateShowTableWallet(false))
        dispatch(updateShowFileWallet(true))
    }


    if (searchClientWallet) {
        sortedData = sortedData.filter(data =>
            data.fecha.includes(searchClientWallet) ||
            data.institucion.includes(searchClientWallet) ||
            data.instrumento.includes(searchClientWallet) ||
            data.precio.includes(searchClientWallet)
        );
    }

    const handleSort = (field: keyof DataClient) => {
        if (sortField === field) {
            setSortDirection(prev => prev === 'asc' ? 'desc' : 'asc');
        } else {
            setSortField(field);
            setSortDirection('asc');
        }
    };

    return {
        catalogInter,
        selectedClient,
        triggerErase,
        showTableWallet,
        showFileWallet,
        searchClientWallet,
        loadingInter,
        loadingQueryClient,
        sortField,
        sortDirection,
        sortedData,
        handleSort,
        setSearchClient,
        handleSelectChange,
        handleQueryClient,
        handleShowFileWallet
    }
}


