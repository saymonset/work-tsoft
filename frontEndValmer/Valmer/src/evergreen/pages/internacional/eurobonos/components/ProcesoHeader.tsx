import React from "react";
import {useProcesoHeader} from "./hooks";

export const ProcesoHeader = () => {

    const {
        currentDate,
        selectedVector,
        setFechaEurobonos,
        setSelectVector,
        handleErase
    } = useProcesoHeader()

    return (
        <div className="flex">
            <div className="relative z-0 w-40 mr-28">
                <input
                    type="date"
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                          border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                          dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    placeholder=""
                    required
                    value={currentDate}
                    onChange={(e) => setFechaEurobonos(e.target.value)}
                />
                <label
                    htmlFor="lastRegCoupon"
                    className="peer-focus:font-medium absolute text-sm text-cyan-700
                          duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]
                          peer-focus:left-0 peer-focus:text-cyan-700 peer-focus:dark:text-cyan-700
                          peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75
                          peer-focus:-translate-y-6"
                >
                    Fecha
                </label>
            </div>
            <div className="ml-24 relative z-0 w-40">
                <select
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                            border-gray-200 appearance-none dark:border-gray-600 dark:focus:border-cyan-700
                            focus:outline-none
                            focus:ring-0 peer"
                    name="selectVector"
                    onChange={setSelectVector}
                    value={selectedVector}
                >
                    <option value="default">...</option>
                    <option value="DEFINITIVO">DEFINITIVO</option>
                </select>
                <label htmlFor="selectVector"
                       className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                        -translate-y-6 origin-[0]"
                >
                    PROCESO
                </label>
            </div>
            <div className="flex-1 mr-6 text-right text-cyan-700 font-bold">
                <button
                    className="bg-cyan-700 hover:bg-green-700
                                    text-white py-1 rounded-md px-3 mx-1"
                    onClick={handleErase}>
                    <span>Limpiar</span>
                </button>
            </div>
        </div>
    )
}