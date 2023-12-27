import React, { useState } from "react"
import { getCurrentDate } from "../../../../../../utils"
import {useDispatch, useSelector} from "react-redux"
import {
    updateCheckActualiza1bActualizaBd,
    updateCheckDescargaCateRv,
    updateCheckFormateaCateRv,
    updateCheckGenerarSalidas,
    updateCheckInternacionalActualizaBd,
    updateCheckNacionalActualizaBd,
    updateCheckPrecalIntProcesoRv,
    updateCheckPrecalNacProcesoRv,
    updateCheckProduccionSalidas,
    updateFechaProcesoRV,
    updateIsShowLogBoxOuts,
    updateSelectedVectorRV,
    updateShowActualiza1BActBd,
    updateShowDescargaCateRv,
    updateShowFormateaCateRv,
    updateShowIndicadoConsultas,
    updateShowInternacionalActBd,
    updateShowNacionalActBd,
    updateShowProcesoRvLog
} from "../../../../../../redux"
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const useProcesoHeader = () => {

    const selectedVectorRV = useSelector((state: RootState<any, any, any>) =>
        state.selectedVectorRV) as unknown as string;

    const [currentDate, setCurrentDate] = useState<string>(getCurrentDate)

    const dispatch = useDispatch()

    const setFechaProcesoRV = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCurrentDate(e.target.value)
        dispatch(updateFechaProcesoRV(e.target.value))
    }

    const setSelectVector = (e: React.ChangeEvent<HTMLSelectElement>) => {
        dispatch(updateSelectedVectorRV(e.target.value))
        dispatch(updateCheckFormateaCateRv(true))
        dispatch(updateCheckDescargaCateRv(true))
        dispatch(updateCheckNacionalActualizaBd(true));
        dispatch(updateCheckGenerarSalidas(true))
        dispatch(updateCheckProduccionSalidas(true))
    }

    const handleErase = () => {
        dispatch(updateIsShowLogBoxOuts(false))
        dispatch(updateShowProcesoRvLog(false))
        dispatch(updateShowNacionalActBd(false));
        dispatch(updateShowInternacionalActBd(false));
        dispatch(updateShowActualiza1BActBd(false));
        dispatch(updateShowDescargaCateRv(false))
        dispatch(updateShowFormateaCateRv(false))
        dispatch(updateShowIndicadoConsultas(false))
        dispatch(updateSelectedVectorRV('...'))
        dispatch(updateCheckFormateaCateRv(false))
        dispatch(updateCheckDescargaCateRv(false))
        dispatch(updateCheckInternacionalActualizaBd(false))
        dispatch(updateCheckNacionalActualizaBd(false))
        dispatch(updateCheckActualiza1bActualizaBd(false))
        dispatch(updateCheckPrecalNacProcesoRv(false))
        dispatch(updateCheckPrecalIntProcesoRv(false))
        dispatch(updateCheckGenerarSalidas(false))
        dispatch(updateCheckProduccionSalidas(false))
    }

    return {
        selectedVectorRV,
        currentDate,
        setFechaProcesoRV,
        setSelectVector,
        handleErase
    }
}