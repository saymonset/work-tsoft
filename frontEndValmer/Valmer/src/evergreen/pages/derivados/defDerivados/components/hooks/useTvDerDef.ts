import { useEffect, useState } from "react"
import { fetchDataGetRet } from "../../../../../../utils"

export const useTvDerDef = () => {
    const [tv, setTv] = useState<string[]>([])
    const [serieOp, setSerieOp] = useState<string[]>([])

    const [loadingTv, setLoadingTv] = useState(false)
    const [loadingSerie, setLoadingSerie] = useState(false)

    useEffect(() => {
        if (!tv || tv.length === 0) {
            const getTv = async () => {
                setLoadingTv(true)
                const response = await fetchDataGetRet(
                    "/derivados/def/tv",
                    " al obtener catalogo tv",
                    {}
                )
                setTv(response.body)
                setLoadingTv(false)
            }
            getTv().then()
        }
    }, [tv]);

    useEffect(() => {
        if (!serieOp || serieOp.length === 0) {
            const getSerieOp = async () => {
                setLoadingSerie(true)
                const response = await fetchDataGetRet(
                    "/derivados/def/vencimientos/series",
                    " al obtener catalogo serie",
                    {}
                )
                setSerieOp(response.body)
                setLoadingSerie(false)
            }
            getSerieOp().then()
        }
    }, [serieOp]);

    return { tv, loadingTv, serieOp, loadingSerie };
}