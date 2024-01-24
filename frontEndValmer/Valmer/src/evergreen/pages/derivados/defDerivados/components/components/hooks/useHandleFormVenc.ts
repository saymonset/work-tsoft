import React, { useState } from "react"
import { FormValuesVenc, TableDefVenc } from "../../../../../../../model"
import { fetchDataPostAct, getCurrentDate } from "../../../../../../../utils"
import { useChargeFileGeneric } from "./useChargeFileGeneric"

interface FormVencProps {
    tv: string
    emisora: string
    setDataTable: React.Dispatch<React.SetStateAction<TableDefVenc[]>>
}

export const useHandleFormVenc = (props: FormVencProps) => {

    const {
        selectedFile,
        loadingCargaArch,
        fileVencimientos,
        loadingProcess,
        setFileBase64,
        setSelectedFile,
        handleCharge,
        handleProcessArch
    } = useChargeFileGeneric()

    const [isCargaOp, setIsCargaOp] = useState(false)
    const [loadingSaveVenc, setLoadingSaveVenc] = useState(false)

    const [isNewSerie, setIsNewSerie] = useState(false)

    const currentDate = getCurrentDate()

    const [formValuesVenc, setFormValuesVenc] = 
        useState<FormValuesVenc>({
                                    sTv: props.tv,
                                    sEmisora: props.emisora,
                                    date_picker0: currentDate
                                } as FormValuesVenc)
    
    const handleOpenCargaOp = () => {
        setIsCargaOp(true)
    }

    const handleCloseCargaOp = () => {
        setIsCargaOp(false)
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target
        const dataVenc = {...formValuesVenc, [name]: value}
        setFormValuesVenc(dataVenc)
    }

    const handleSubmitVenc = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setLoadingSaveVenc(true)
        const response = await fetchDataPostAct(
            "derivados/def/vencimientos/guardar-vencimiento",
            "Guardado",
            " al guardar vencimiento",
            [],
            formValuesVenc
        )
        props.setDataTable(response.body)
        setLoadingSaveVenc(false)
        setFormValuesVenc({...formValuesVenc, sSerie: "", date_picker0: currentDate})
    }

    return {
        isCargaOp,
        isNewSerie,
        formValuesVenc,
        loadingSaveVenc,
        selectedFile,
        loadingCargaArch,
        fileVencimientos,
        loadingProcess,
        setIsNewSerie,
        setFileBase64,
        setSelectedFile,
        handleCharge,
        handleOpenCargaOp,
        handleCloseCargaOp,
        handleChange,
        handleSubmitVenc,
        handleProcessArch
    }
}