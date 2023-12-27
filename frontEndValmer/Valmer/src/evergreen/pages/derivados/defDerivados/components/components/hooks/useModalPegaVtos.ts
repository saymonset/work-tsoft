import React, { useState } from "react"
import { fetchDataPost, showAlert } from "../../../../../../../utils"

export const useModalPegaVtos = ({tipoTv}: {tipoTv: string}) => {

    const [textArea, setTextArea] = useState("")
    const [loadingCarga, setLoadingCarga] = useState(false)

    const handleChangeTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setTextArea(e.target.value)
    }

    const handleLimpiar = () => {
        setTextArea("")
    }

    const handleCarga = async () => {
        setLoadingCarga(true)
        const s_tipo = tipoTv

        if (textArea !== "") {
            await fetchDataPost(
                "derivados/def/vencimientos/pega-vtos",
                " al cargar vencimientos",
                {textArea},
                {s_tipo}
            )
            setLoadingCarga(false)
        } else {
            setLoadingCarga(false)
            await showAlert("warning", "Error", "Debe ingresar los datos a cargar!")
        }
    }

    return {
        textArea,
        loadingCarga,
        handleChangeTextArea,
        handleLimpiar,
        handleCarga
    }
}