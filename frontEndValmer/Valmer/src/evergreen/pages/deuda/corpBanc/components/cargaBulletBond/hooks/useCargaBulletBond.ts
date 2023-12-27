import React, {useEffect, useState} from "react";
import {CargaArchivoContent} from "../../../../../../../model";
import {fetchDataPost} from "../../../../../../../utils";

export const useCargaBulletBond = () => {
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileBase64, setFileBase64] = useState<string>("");
    const [cargaArchivoBulletBond, setCargaArchivoBulletBond]
        = useState<CargaArchivoContent>({} as CargaArchivoContent);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        if(selectedFile?.name)
        {
            setCargaArchivoBulletBond({} as CargaArchivoContent);

            const cadena = selectedFile?.name;
            const partes = cadena.split(".");
            const nombre = partes[0];
            const extension = partes[1];
            const contenidoFileBase64 = fileBase64.split(",");
            const contenido = contenidoFileBase64[1]


            const request: CargaArchivoContent = {
                contenido: contenido,
                contentType: selectedFile?.type,
                extension: extension,
                nombre: nombre,
                nombreCompleto: selectedFile?.name
            }

            setCargaArchivoBulletBond(request);
        }
    }, [fileBase64, selectedFile]);


    const handleCargaArchivo = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoading(true)
        await fetchDataPost("/instrumentos/corporativos/sube-archivo",
            "al intentar guardar datos carga archivo",
            cargaArchivoBulletBond, {s_tipo_layout: "INSTR"})
        setLoading(false)
    }


    return {
        handleCargaArchivo,
        setFileBase64,
        setSelectedFile,
        selectedFile,
        loading
    }
}