import React, { useEffect, useState } from "react"
import { fetchDataGetRet, fetchDataPostAct } from "../../../../../../utils"
import { TableDefVenc } from "../../../../../../model"

interface DataModalProps {
    tv: string
    emisora: string
    tipoTv: string
    triggerVenc: boolean
    setTriggerVenc: React.Dispatch<React.SetStateAction<boolean>>
}

export const useDataModal = (props: DataModalProps) => {

    const [isHelp, setIsHelp] = useState(false)
    
    const [loadingDataTable, setLoadingDataTable] = useState(false)
    const [dataTableVenc, setDataTableVenc] = useState<TableDefVenc[]>([])
    
    const [sInstrumento, setSInstrumento] = useState("")
    const [isModalConfirm, setIsModalConfirm] = useState(false)
    const [loadingDeleted, setLoadingDeleted] = useState(false)

    const [modalCargaArc, setModalCargaArc] = useState(false)

    useEffect(() => {
        const getDataVenc = async () => {
            if (props.triggerVenc) {
                setDataTableVenc([])
                setLoadingDataTable(true)
    
                const response = await fetchDataGetRet(
                    "derivados/def/vencimientos/tabla-vencimientos",
                    " al consultar fechas vencimientos",
                    {sTv: props.tv, sEmisora: props.emisora}
                )
                setDataTableVenc(response.body)
                setLoadingDataTable(false)
                props.setTriggerVenc(false)
            }
        }
        getDataVenc().catch(() => {})
    }, [props.triggerVenc])

    const confirmDeleteVenc = (sInstrumento: string) => {
        setIsModalConfirm(true)
        setSInstrumento(sInstrumento)
    }

    const closeModalConfirm = () => {
        setIsModalConfirm(false)
        setSInstrumento("")
    }

    const deletedVenc = async () => {
        setLoadingDeleted(true)
        const response = await fetchDataPostAct(
            "derivados/def/vencimientos/eliminar-vencimiento",
            "Eliminado",
            " al eliminar vencimiento",
            [],
            {sInstrumento}
        )
        setDataTableVenc(response.body)
        setLoadingDeleted(false)
        setIsModalConfirm(false)
    }

    const modalCargaArcOpen = () => {
        setModalCargaArc(true)
    }

    const modalCargaArcClose = () => {
        setModalCargaArc(false)
    }

    return {
        isHelp,
        loadingDataTable,
        loadingDeleted,
        dataTableVenc,
        isModalConfirm,
        modalCargaArc,
        modalCargaArcOpen,
        modalCargaArcClose,
        setIsHelp,
        setDataTableVenc,
        confirmDeleteVenc,
        closeModalConfirm,
        deletedVenc
    }
}