import { useEffect, useState } from "react"
import { CargaArchivoContent } from "../../../../../../../model"
import { fetchDataPost } from "../../../../../../../utils"

export const useChangeFileAcc = () => {

    const [fileProcesoRv, setFileProcesoRv] = useState<CargaArchivoContent>({} as CargaArchivoContent)
    const [selectedFile, setSelectedFile] = useState<File | null>(null)
    const [fileBase64, setFileBase64] = useState<string>("")
    const [loadingCargaArchRv, setLoadingCargaArchRv] = useState(false)

    useEffect(() => {
        if (selectedFile?.name) {
            setFileProcesoRv({} as CargaArchivoContent)

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
            setFileProcesoRv(request)
        }
    }, [fileBase64])

    const handleCarge = async () => {
        setLoadingCargaArchRv(true)
        await fetchDataPost(
            "/acciones/proceso-rv/subir-archivo",
            " al cargar el archivo de Indice de Bursatilidad",
            fileProcesoRv,
            {}
        )
        setLoadingCargaArchRv(false)
        setFileProcesoRv({} as CargaArchivoContent)
        setSelectedFile(null)
        setFileBase64("")
    }

    return {
        selectedFile,
        loadingCargaArchRv,
        fileProcesoRv,
        setFileBase64,
        setSelectedFile,
        handleCarge
    }
}