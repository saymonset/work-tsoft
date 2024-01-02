import {ButtonContent, TitleDate} from "../../../../shared";
import {TasasForm} from "./components";
import React from "react";
import {useTasas} from "./hooks";

export const Tasas = () => {

    const {
        triggerErase,
        isLoadingConsulta,
        currentDate,
        setCurrentDate,
        handleQueryTasas,
        handleEraseData} = useTasas()

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    return (
        <>
            <TitleDate title="Tasas"/>

            <div className="flex">
                <div className="relative z-0 w-40">
                    <input
                        type="date"
                        name="lastRegCoupon"
                        id="lastRegCoupon"
                        className="block py-0 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                          border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                          dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                        placeholder=""
                        required
                        defaultValue={currentDate}
                        onChange={(e) => setCurrentDate(e.target.value)}
                    />
                    <label
                        htmlFor="lastRegCoupon"
                        className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900
                          duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0]
                          peer-focus:left-0 peer-focus:text-cyan-700 peer-focus:dark:text-cyan-700
                          peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75
                          peer-focus:-translate-y-6"
                    >
                        Fecha
                    </label>
                </div>
                <div className="flex-1 mr-6 text-right text-cyan-700 font-bold">
                    <button
                        className="bg-cyan-700 hover:bg-green-700
                                    text-white py-0 rounded-md px-3 mx-1"
                        onClick={handleQueryTasas}>
                        <ButtonContent name="Consultar" loading={isLoadingConsulta}/>
                    </button>
                    <button
                        className="bg-cyan-700 hover:bg-green-700
                                    text-white py-0 rounded-md px-3 mx-1"
                        onClick={handleEraseData}>
                        <span>Limpiar</span>
                    </button>
                </div>
            </div>
            <div className="mt-2">
                <TasasForm d_fecha = {currentDate} />
            </div>
        </>
    )
}