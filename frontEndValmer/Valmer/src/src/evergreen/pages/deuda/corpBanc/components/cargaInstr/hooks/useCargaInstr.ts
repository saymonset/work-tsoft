import React, {useEffect, useState} from "react";
import {CargaArchivoContent, Catalogo} from "../../../../../../../model";
import {fetchDataGetRet, fetchDataPost} from "../../../../../../../utils";

export const useCargaInstr = () => {

    const [selectedLayout, setSelectedLayout] = useState<string>("");
    const [tipoLayoutCat, setTipoLayoutCat] = useState<Catalogo[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileBase64, setFileBase64] = useState<string>("");
    const [cargaArchivoInstr, setCargaArchivoInstr]
        = useState<CargaArchivoContent>({} as CargaArchivoContent);
    const [loading, setLoading] = useState<boolean>(false);
    const [loadingLayout, setLoadingLayout] = useState<boolean>(false);


    useEffect(() => {
        const getDataLayout = async () => {
            setLoadingLayout(true);
            const response = await fetchDataGetRet("/instrumentos/corporativos/carga-instrumentos/consulta-layout",
                "al obtener catalogo tipo layout",
                {})
            setTipoLayoutCat(response.body.catalogos)
            setLoadingLayout(false);
        };

        getDataLayout().catch(() => {});
    }, []);

    useEffect(() => {
        if(selectedFile?.name)
        {
            setCargaArchivoInstr({} as CargaArchivoContent);

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

            setCargaArchivoInstr(request);
        }
    }, [fileBase64, selectedFile]);

    const handleSelectLayout = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedLayout(e.target.value)
    }


    const handleCargaArchivo = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoading(true)
        await fetchDataPost("instrumentos/corporativos/carga-instrumentos/corporativos",
            "al intentar guardar datos carga archivo",
            cargaArchivoInstr,
            {s_tipo_layout: selectedLayout})
        setLoading(false)
    }


    return {
        handleCargaArchivo,
        handleSelectLayout,
        setFileBase64,
        setSelectedFile,
        selectedLayout,
        tipoLayoutCat,
        selectedFile,
        loading,
        loadingLayout
    }
}