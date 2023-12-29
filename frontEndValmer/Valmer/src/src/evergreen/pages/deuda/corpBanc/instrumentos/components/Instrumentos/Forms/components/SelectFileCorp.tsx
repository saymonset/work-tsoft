import {useEffect, useState} from "react";
import {CargaArchivo, CargaArchivoContent} from "../../../../../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {updateCargaArchivoCorp} from "../../../../../../../../../redux";
import {handleFileChange} from "../../../../../../../../../utils";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const SelectFileCorp = () => {


    const cargaArchivoCorp = useSelector((state: RootState<any, any, any>) =>
        state.cargaArchivoCorp) as unknown as CargaArchivo;


    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileBase64, setFileBase64] = useState<string>("");
    const dispatch = useDispatch()


    useEffect(() => {
        if(selectedFile?.name)
        {
            dispatch(updateCargaArchivoCorp({} as CargaArchivoContent))

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
            dispatch(updateCargaArchivoCorp(request))
        }
    }, [fileBase64]);


    return (
        <div className="text-xs text-cyan-700 my-3">
            <label htmlFor="file-upload"
                   className="relative cursor-pointer bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                <span className="text-sm">Examinar...</span>
                <input id="file-upload" type="file"
                       className="hidden"
                       accept=".csv"
                       onChange={(e) => handleFileChange(e,
                           setFileBase64, setSelectedFile)}/>
            </label>
            <span className="py-2 px-2 text-cyan-700">
                    {selectedFile?.name ?? cargaArchivoCorp.nombreCompleto ?? 'Ningún archivo seleccionado.'}
                </span>
        </div>
    );
}