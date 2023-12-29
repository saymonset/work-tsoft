import React, { useState } from "react"
import { fetchDataGetRet, getCurrentDate, showAlert } from "../../../../../utils"
import { ButtonContent } from "../../../../../shared"
import { Base64 } from "js-base64"
import fileDownload from "js-file-download"

interface GenerarArchivoProps {
    title: string
    n_institucion?: number
    n_nombre?: number
    url: string
    is_hist: boolean
}

export const GenerarArchivo = (data: GenerarArchivoProps) => {

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
            setFile(Base64.decode(response.body.contenido))
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
        fileDownload(file, nameFile)
    }

    return (
        <div className="flex justify-center -mt-3">
            <div className="grid grid-rows-1 gap-4 w-2/5">
                <span className="form-title w-full">{data.title}</span>
                <div className="form-cols-3">
                    <div className="form-cols-1 -my-3">
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_ini"
                                id="d_fecha_ini"
                                value={fechaIni}
                                onChange={handleChangeFecha}
                            />
                            <label htmlFor="d_fecha_ini">Fecha Inicio</label>
                        </div>
                    </div>
                    <div className="form-cols-1 -my-3">
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_fin"
                                id="d_fecha_fin"
                                value={fechaFin}
                                onChange={handleChangeFecha}
                            />
                            <label htmlFor="d_fecha_fin">Fecha Fin</label>
                        </div>
                    </div>
                    <div className="form-cols-1 ml-3">
                        <button
                            className="btn w-min"
                            onClick={handleGenerar}
                        >
                            <ButtonContent name="Generar" loading={loading}/>
                        </button>
                    </div>
                    {linkFile && (
                        <div className="form-cols-1 col-span-3">
                            <button
                                className="btn-link"
                                onClick={downloadFile}
                            >
                                DESCARGA
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}