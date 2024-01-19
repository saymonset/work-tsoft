import {TitleDate} from "../../../../../../shared";
import React, {useEffect, useState} from "react";
import {fetchDataGetRet, getCurrentDate} from "../../../../../../utils";
import {ResponseBonosRef} from "../../../../../../model";
import {CambBonRefTable} from "./forms";
import {MoonLoader} from "react-spinners";

export const CambBonRef = () => {

    const [loading, setLoading] = useState(false)
    const [date, setDate] = useState(getCurrentDate)
    const [bonos, setBonos] = useState({} as ResponseBonosRef);

    useEffect(() => {
        handleConsulta();
    }, []);

    const handleConsulta = async () => {
        setLoading(true)
        const response = await fetchDataGetRet(
            "/instrumentos/corporativos/cambios-bonos-ref/consulta-cambios-ref",
            " al obtener informacion cambios referencia",
            {d_fecha_consulta: date})
        setLoading(false)
        setBonos(response)
    }

    const handleDate = (e: any) => {
        setDate(e.target.value)
    }

    return (
        <>
            <TitleDate title="Cambios Bonos Referencia"/>

            <div className="grid grid-cols-4 gap-4">
                <div className="form-date">
                    <input
                        type="date"
                        name="d_fecha_vto"
                        onChange={handleDate}
                        value={date}
                    />
                    <label htmlFor="d_fecha_vto">
                        Fecha
                    </label>
                </div>
                <div className="mt-4 relative z-0 w-full my-4">
                    <button
                        className="btn"
                        onClick={handleConsulta}
                    >
                        <span>Consultar</span>
                    </button>
                </div>
            </div>

            {loading ? (
                <div className="flex justify-center items-center h-full">
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                </div>
            ) : (
                <CambBonRefTable data={bonos} date={date}/>
            )}
        </>
    )
}