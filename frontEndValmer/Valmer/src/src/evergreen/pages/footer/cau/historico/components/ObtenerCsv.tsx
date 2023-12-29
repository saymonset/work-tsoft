import React, { useState } from "react"
import { fetchDataGetRet } from "../../../../../../utils"
import { Base64 } from "js-base64"
import fileDownload from "js-file-download"
import { BarLoader } from "react-spinners"

interface ObtenerCsvProps {
    n_status: string
    d_fecha_inicio: string
    d_fecha_fin: string
}

export const ObtenerCsv: React.FC<ObtenerCsvProps> = ({ n_status, d_fecha_inicio, d_fecha_fin }) => {

    const [loading, setLoading] = useState<boolean>(false)

    const handleObtenerCsv = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setLoading(true)
        const response = await fetchDataGetRet(
            '/cau/historico/genera-csv',
            " al descargar CSV",
            {n_status, d_fecha_inicio, d_fecha_fin}
        )
        const file = Base64.decode(response.body.contenido)
        fileDownload(file, response.body.nombreCompleto)
        setLoading(false)
    }

    return (
        <div className="block text-center">
            <button
                className="btn-link"
                onClick={handleObtenerCsv}
            >
                Obtener csv
            </button>
            {loading && <BarLoader className="mt-2" color="#059669" width={150} />}
        </div>
    )
}