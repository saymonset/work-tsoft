import { useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import { CargaArchivoContent, UseChargeOptions } from "../../../../../../model";
import { handlePostError400 } from "../../../../../../utils";

export const useChargeFileGeneric = ({
    apiUrl,
    apiErrorMessage,
    data,
    method,
    params
}: UseChargeOptions) => {

    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileBase64, setFileBase64] = useState<string>("");
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    useEffect(() => {
        if (selectedFile?.name) {
            dispatch(method({} as CargaArchivoContent))

            const cadena = selectedFile.name;
            const partes = cadena.split(".");
            const nombre = partes[0];
            const extension = partes[1];
            const content = fileBase64.split(",")

            const request: CargaArchivoContent = {
                contenido: content[1],
                contentType: selectedFile.type,
                extension: extension,
                nombre: nombre,
                nombreCompleto: selectedFile.name
            }

            dispatch(method(request))
        }
    }, [fileBase64]);

    const handleCharge = async () => {
        setLoading(true);
        await handlePostError400(apiUrl, apiErrorMessage, data, params);
        setLoading(false);
    }

    return {
        selectedFile,
        loading,
        setFileBase64,
        setSelectedFile,
        handleCharge
    }
}
