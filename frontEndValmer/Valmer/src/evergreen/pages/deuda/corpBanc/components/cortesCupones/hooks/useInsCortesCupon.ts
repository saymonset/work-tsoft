import {useState} from "react";
import {BodyCorteCupon, ResponseCorteCupon} from "../../../../../../../model";
import {fetchDataGetRet, getCurrentDate} from "../../../../../../../utils";

export const useInsCortesCupon = () => {
    const [loading, setLoading] = useState(false);
    const [fijos, setFijos] = useState({} as ResponseCorteCupon);
    const [flotantes, setFlotantes] = useState({} as ResponseCorteCupon);
    const [fecha, setFecha] = useState(getCurrentDate);

    const handleConsulta = async () => {

        setLoading(true);
        const fijos = await fetchDataGetRet("/instrumentos/corporativos/corte-cupon",
            "al obtener catalogo serie",
            {d_fecha_corte: fecha, tipo: "fijos"})

        const flotantes = await fetchDataGetRet("/instrumentos/corporativos/corte-cupon",
            "al obtener catalogo serie",
            {d_fecha_corte: fecha, tipo: "flotantes"})
        setLoading(false);

        const dataFijos:BodyCorteCupon[] = fijos.body
        dataFijos.sort((a: any, b: any) => {
            if (a.property < b.property) return -1
            if (a.property > b.property) return 1
            return 0
        })

        const dataFlotantes:BodyCorteCupon[] = flotantes.body
        dataFlotantes.sort((a: any, b: any) => {
            if (a.property < b.property) return -1
            if (a.property > b.property) return 1
            return 0
        })

        setFijos({...fijos, dataFijos})
        setFlotantes({...flotantes, dataFlotantes})

    }

    const handleOnChange = (e: any) => {
        setFecha(e.target.value)
    }

    return {
        loading,
        fijos,
        flotantes,
        fecha,
        handleConsulta,
        handleOnChange
    }
}