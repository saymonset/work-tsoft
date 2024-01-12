import React from "react";
import {TitleDate} from "../../../../../../shared";
import {ActiveInsCortesCupon} from "./forms";
import {MoonLoader} from "react-spinners";
import {useInsCortesCupon} from "./hooks";

export const InstCortesCupon = () => {

   const {
       loading,
       fijos,
       flotantes,
       fecha,
       handleConsulta,
       handleOnChange} = useInsCortesCupon()

    return (
        <>
            <TitleDate title="Instrumentos Cortes de Cupón"/>

            <div className="px-3">
                <div className="bg-cyan-700 px-1 text-slate-50">
                    <span>Filtros</span>
                </div>
                <div className="grid grid-cols-4 gap-4">
                    <div className="form-date form-date-my">
                        <input
                            type="date"
                            name="d_fecha_vto"
                            onChange={handleOnChange}
                            value={fecha}
                        />
                        <label htmlFor="d_fecha_vto">
                            Fecha Corte Cupón
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
            </div>

            <div>
                {loading ? (
                    <div className="mt-8 flex justify-center items-center h-full">
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                    </div>
                ) : (
                    <div className="panel">
                        <div className="panel-body">
                            <ActiveInsCortesCupon fijos={fijos} flotantes={flotantes}/>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}