import { useEffect, useState } from "react";
import {fetchDataGetRet, reorderTvData} from "../../../../../utils";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateTv} from "../../../../../redux";

export const useTvOpt = (sMercado: string = "GUBER", isNew: boolean = false) => {

    const tv = useSelector((state: RootState<any, any, any>) =>
        state.tv) as unknown as string[];

    const [loadingTv, setLoadingTv] = useState(false);

    const dispatch = useDispatch()

    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)

                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    { sMercado: sMercado, nuevoRegistro: isNew }
                );

                if (isNew && response.status === 200) {
                    response.body = reorderTvData(response.body);
                }

                dispatch(updateTv(response.body));

                setLoadingTv(false);
            }
            getTv().then()
        }

    }, [tv, sMercado, isNew]);

    return { tv, loadingTv };
};