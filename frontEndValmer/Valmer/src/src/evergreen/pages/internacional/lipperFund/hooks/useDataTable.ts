import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchDataGetConsultaData } from "../../../../../utils";
import { updateConsultaLipper, updateDataTableHead } from "../../../../../redux";
import { ConsultaLipper, ResponseDataTableHead } from "../../../../../model";
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState";

export const useDataTable = () => {

    const dataTableHead = useSelector((state: RootState<any, any, any>) =>
        state.dataTableHead) as unknown as ResponseDataTableHead

    const consultaLipper = useSelector((state: RootState<any, any, any>) => 
        state.consultaDataLipper) as unknown as ConsultaLipper

    const [loading, setLoading] = useState(false);
    const [loadingTableHead, setLoadingTableHead] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        const getQueryLipper = async () => {
            setLoading(true)
            await fetchDataGetConsultaData("/internacional/lipper-fund/tabla-info",
                " al consultar info lipper",
                {},
                updateConsultaLipper,
                dispatch)
            setLoading(false)
        }

        if (Object.keys(consultaLipper).length === 0 || (consultaLipper?.body?.length || 0) === 0) {
            getQueryLipper().then()
        }
    }, [consultaLipper]);

    useEffect(() => {
        const getDataTableHead = async () => {
            setLoadingTableHead(true)
            await fetchDataGetConsultaData("/internacional/lipper-fund/tabla-botones",
                " al consultar datos de tabla head",
                {},
                updateDataTableHead,
                dispatch)
            setLoadingTableHead(false)
        }

        if (!dataTableHead.body || Object.keys(dataTableHead.body).length === 0) {
            getDataTableHead().then()
        }
    }, [dataTableHead]);

    return {
        loading,
        loadingTableHead,
        consultaLipper
    }
}