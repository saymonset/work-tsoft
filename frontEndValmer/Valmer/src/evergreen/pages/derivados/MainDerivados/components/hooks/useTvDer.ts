import {useEffect, useState} from "react";
import {fetchDataGetRet} from "../../../../../../utils";

export const useTvDer = (sMercado: string, isNew: boolean = false) => {

    const [tv, setTv] = useState<string[]>([])

    const [loadingTv, setLoadingTv] = useState(false);

    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/tv",
                    " al obtener catalogo tv",
                    {sMercado: sMercado, isNew: isNew})

                setTv(response.body)
                setLoadingTv(false);
            }
            getTv().then()
        }

    }, [tv, sMercado, isNew]);

    return { tv, loadingTv };
};