import React, {useState} from "react";
import {downloadFiles, fetchDataGetRet, getCurrentDate, showAlert} from "../../../../../../utils";
import {GenerarArchivoUserWebProps} from "../../../../../../model";

export const useGenerarArchivo = (data: GenerarArchivoUserWebProps) => {
    const [fechaIni, setFechaIni] = useState(getCurrentDate)
    const [fechaFin, setFechaFin] = useState(getCurrentDate)
    const [file, setFile] = useState<string>("")
    const [nameFile, setNameFile] = useState<string>("")

    const [loading, setLoading] = useState<boolean>(false)
    const [linkFile, setLinkFile] = useState<boolean>(false)

    const handleChangeFecha = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        if (name === "d_fecha_ini") {
            setFechaIni(value);
        } else if (name === "d_fecha_fin") {
            setFechaFin(value);
        }
    }

    const handleGenerar = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLinkFile(false)
        setLoading(true)
        let params
        if (data.is_hist) {
            params = {
                d_fecha_ini_trial: fechaIni,
                d_fecha_fin_trial: fechaFin,
            }
        } else {
            params = {
                d_fecha_ini: fechaIni,
                d_fecha_fin: fechaFin,
                n_institucion: data.n_institucion,
                n_nombre: data.n_nombre
            }
        }
        const response = await fetchDataGetRet(
            data.url,
            " al descargar archivo csv",
            params
        )
        if (response?.body) {
            setFile(response.body.contenido)
            setNameFile(response.body.nombreCompleto)
            setLinkFile(true)
            setLoading(false)
        } else {
            setLoading(false)
            setLinkFile(false)
            await showAlert("info", "Información", response.message)
        }
    }

    const downloadFile = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()

        let type = "";

        if (nameFile.endsWith('.xlsx')) {
            type = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
        } else if (nameFile.endsWith('.csv')) {
            type = 'text/csv';
        }

        downloadFiles(file,
            nameFile,
            type)
    }

    return {
        loading,
        linkFile,
        fechaIni,
        fechaFin,
        handleChangeFecha,
        handleGenerar,
        downloadFile
    }
}