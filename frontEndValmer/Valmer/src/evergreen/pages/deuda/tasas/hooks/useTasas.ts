import {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {fetchDataGet, getCurrentDate} from "../../../../../utils";
import {updateConsultaTasas} from "../../../../../redux";
import {TasasConsultaResponse} from "../../../../../model";

export const useTasas = () => {

    const [currentDate, setCurrentDate] = useState<string>(getCurrentDate)

    const [isLoadingConsulta, setIsLoadingConsulta]
        = useState<boolean>(false)

    const [triggerErase, setTriggerErase] = useState<boolean>(false)

    const dispatch = useDispatch()

    const handleQueryTasas = async () => {
        dispatch(updateConsultaTasas({} as TasasConsultaResponse))
        setIsLoadingConsulta(true)
        await fetchDataGet(
            "/tasas/consultar-tasas",
            " al obtener consulta de tasas",
            {d_fecha: currentDate},
            updateConsultaTasas, dispatch).then()
        setIsLoadingConsulta(false)
    };

    const handleEraseData = () => {
        dispatch(updateConsultaTasas({} as TasasConsultaResponse))
        setTriggerErase(true)
        setTimeout(() => {
            setCurrentDate(getCurrentDate)
            setTriggerErase(false)
        }, 10);
    }

    useEffect(() => {
        handleEraseData()
    }, [])

    return {
        triggerErase,
        isLoadingConsulta,
        currentDate,
        setCurrentDate,
        handleQueryTasas,
        handleEraseData
    }
}