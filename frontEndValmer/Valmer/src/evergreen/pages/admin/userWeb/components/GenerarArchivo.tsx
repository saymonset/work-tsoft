import React from "react"
import { ButtonContent } from "../../../../../shared"
import {useGenerarArchivo} from "./hooks";
import {GenerarArchivoUserWebProps} from "../../../../../model";

export const GenerarArchivo = (data: GenerarArchivoUserWebProps) => {

    const {
        loading,
        linkFile,
        fechaIni,
        fechaFin,
        handleChangeFecha,
        handleGenerar,
        downloadFile
    } = useGenerarArchivo((data))

    return (
        <div className="flex justify-center -mt-3">
            <div className="grid grid-rows-1 gap-4 w-2/5">
                <span className="form-title w-full">{data.title}</span>
                <div className="form-cols-3">
                    <div className="form-cols-1 -my-3">
                        <div className="form-date form-date-my">
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
                        <div className="form-date form-date-my">
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