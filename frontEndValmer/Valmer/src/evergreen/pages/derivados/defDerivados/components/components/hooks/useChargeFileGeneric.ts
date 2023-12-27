import { useEffect, useState } from "react"
import { CargaArchivoContent } from "../../../../../../../model"
import { fetchDataGetRet, fetchDataPost, fetchDataPostAct } from "../../../../../../../utils"

export const useChargeFileGeneric = () => {

    const [fileVencimientos, setFileVencimientos] = useState<CargaArchivoContent>({} as CargaArchivoContent)
    const [selectedFile, setSelectedFile] = useState<File | null>(null)
    const [fileBase64, setFileBase64] = useState<string>("")
    const [loadingCargaArch, setLoadingCargaArch] = useState(false)
    const [loadingProcess, setLoadingProcess] = useState(false)

    useEffect(() => {
        if (selectedFile?.name) {
            setFileVencimientos({} as CargaArchivoContent)

            const cadena = selectedFile.name
            const partes = cadena.split(".")
            const nombre = partes[0]
            const extension = partes[1]
            const filePartes = fileBase64.split(",")
            const fileBase64New = filePartes[1]


            const request: CargaArchivoContent = {
                contenido: fileBase64New,
                contentType: selectedFile.type,
                extension: extension,
                nombre: nombre,
                nombreCompleto: selectedFile.name
            }
            setFileVencimientos(request)
        }
    }, [fileBase64])

    const handleCharge = async () => {
        setLoadingCargaArch(true)
        await fetchDataPost(
            "derivados/def/vencimientos/subir-archivo",
            " al cargar archivo de Vencimientos",
            fileVencimientos,
            {}
        )
        setLoadingCargaArch(false)
    }

    const handleProcessArch = async (arch_fechas_vto: string | undefined) => {
        setLoadingProcess(true)
        await fetchDataPost(
            "derivados/def/vencimientos/cargar-archivo",
            " al procesar el archivo",
            [],
            {arch_fechas_vto}
        )
        setLoadingProcess(false)
    }

    return {
        selectedFile,
        loadingCargaArch,
        fileVencimientos,
        loadingProcess,
        setFileBase64,
        setSelectedFile,
        handleCharge,
        handleProcessArch
    }
}