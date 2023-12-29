import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useDispatch, useSelector } from "react-redux"
import { useState } from "react"

export const useVarDataFlujo = () => {

    const tv = useSelector((state: RootState<any, any, any>) => 
      state.selectedTvCorp) as unknown as string

    const emisora = useSelector((state: RootState<any, any, any>) => 
      state.selectedEmisoraCorp) as unknown as string

    const serie = useSelector((state: RootState<any, any, any>) => 
      state.selectedSerieCorp) as unknown as string

    const [isModalOpen, setIsModalOpen] = useState(false)

    const [titleModal, setTitleModal] = useState('')

    const [isDiaSig, setIsDiaSig] = useState(false)

    const [txtAreaCargaFlujos, setTxtAreaCargaFlujos] = useState('')

    const [triggerUpdateFlujos, setTriggerUpdateFlujos] = useState(false)

    const [triggerUpdateDiaSig, setTriggerUpdateDiaSig] = useState(false)

    const [loading, setLoading] = useState(false)

    const dispatch = useDispatch()

    return {
        tv,
        emisora,
        serie,
        isModalOpen,
        titleModal,
        isDiaSig,
        loading,
        txtAreaCargaFlujos,
        triggerUpdateFlujos,
        triggerUpdateDiaSig,
        setIsModalOpen,
        setTitleModal,
        setIsDiaSig,
        setLoading,
        setTxtAreaCargaFlujos,
        setTriggerUpdateFlujos,
        setTriggerUpdateDiaSig,
        dispatch
    }
}