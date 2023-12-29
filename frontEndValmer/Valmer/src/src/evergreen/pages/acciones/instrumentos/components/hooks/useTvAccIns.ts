import {useEffect, useState} from "react";
import {fetchDataGetRet} from "../../../../../../utils";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {updateTvAccInst} from "../../../../../../redux/Acciones/Instrumentos";

export const useTvAccIns = () => {

    const tvAccInst = useSelector((state: RootState<any, any, any>) =>
        state.tvAccInst) as unknown as string[];

    const [loadingTv, setLoadingTv] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
        if (!tvAccInst || tvAccInst.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    {sMercado: "ACC", isNew: false})

                dispatch(updateTvAccInst(response.body))
                setLoadingTv(false);
            }
            getTv().then()
        }

    }, [tvAccInst]);

    return { tvAccInst, loadingTv };
}