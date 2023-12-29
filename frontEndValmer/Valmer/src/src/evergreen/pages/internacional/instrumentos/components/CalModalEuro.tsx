import {ButtonContent, Modal} from "../../../../../shared";
import React, { useEffect } from "react";
import {CalEuroProps} from "../../../../../model";
import {useCalModaEuro} from "./hooks";

export const CalModalEuro = (c: CalEuroProps) => {

   const {
       nPrecioMercadoSt,
       nPrecioMercadoR,
       selectDate,
       nPrecioRendResp,
       nPrecioMercadoStResp,
       nPrecioMercadoRResp,
       loadingPrecioRend,
       loadingPrecioMercadoSt,
       loadingMercadoRResp,
       setSelectDate,
       setNPrecioRend,
       setNPrecioMercadoSt,
       setNPrecioMercadoR,
       handleCalcularPrecioRendimiento,
       handleCalcularSobretasa,
       handleCalcularRendimiento
   } = useCalModaEuro(c)

   useEffect(() => {
        if (c.isModalOpen) {
            setNPrecioRend(c.calPrecios?.body?.n_precio_rend || 0);
            setNPrecioMercadoR(c.calPrecios?.body?.n_precio_mercado_r || 0);
            setNPrecioMercadoSt(c.calPrecios?.body?.n_precio_mercado_st || 0);
        }
    }, [c.isModalOpen,
        c.calPrecios?.body?.n_precio_rend,
        setNPrecioRend,
        c.calPrecios?.body?.n_precio_mercado_r,
        setNPrecioMercadoR,
        c.calPrecios?.body?.n_precio_mercado_st,
        setNPrecioMercadoSt
        ]);

    return (
        <Modal isOpen={c.isModalOpen} onClose={c.handleModalClose} title="">

            <div className="grid grid-cols-1 mt-1">
                <div className="grid grid-cols-3 gap-4">
                    <div className="relative z-0 w-full my-3">
                        <input
                            type="date"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            name="n_tasa_cupon_md_vig"
                            value={selectDate}
                            onChange={(e) => setSelectDate(e.target.value)}
                        />
                        <label
                            htmlFor="plazo"
                            className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                        >
                            Fecha
                        </label>
                    </div>
                </div>


                <div className="p-1 w-full text-center text-green-700 text-lg font-bold">
                    <span>{c.instrument}</span>
                </div>

                <div className="mt-4 mb-4 p-1 bg-cyan-700 text-white text-center">
                    <span>Precio</span>
                </div>
                <div className="grid grid-cols-3 gap-4">
                    <div className="relative z-0 w-full my-3">
                        <input
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            name="n_tasa_cupon_md_vig"
                            value={c.calPrecios?.body?.n_precio_rend}
                            onChange={(e) => setNPrecioRend(Number(e.target.value))}
                        />
                        <label
                            htmlFor="plazo"
                            className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                        >
                            Rendimiento
                        </label>
                    </div>
                    <div className="relative z-0 w-full my-3">
                        <input
                            name="precio"
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                                focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            value={nPrecioRendResp}
                            disabled={true}
                        />
                        <label htmlFor="precio"
                            className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                -translate-y-6 origin-[0]"
                        >
                            Precio
                        </label>
                    </div>
                    <div className="relative z-0 w-full my-3 ml-20">
                        <button className="btn" onClick={handleCalcularPrecioRendimiento}>
                            <ButtonContent name="Calcular" loading={loadingPrecioRend}/>
                        </button>
                    </div>
                </div>



                <div className="mt-4 mb-4 p-1 bg-cyan-700 text-white text-center">
                    <span>Sobretasa</span>
                </div>
                <div className="grid grid-cols-3 gap-4">
                    <div className="relative z-0 w-full my-3">
                        <input
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            name="n_tasa_cupon_24_vig"
                            value={nPrecioMercadoSt}
                            onChange={(e) => setNPrecioMercadoSt(Number(e.target.value))}
                        />
                        <label
                            htmlFor="plazo"
                            className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                        >
                            Precio Mercado
                        </label>
                    </div>
                    <div className="relative z-0 w-full my-3">
                        <input
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                                focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            value={nPrecioMercadoStResp}
                            disabled={true}
                        />
                        <label
                            htmlFor="fechaVencimientoEstimada"
                            className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75 -translate-y-6 origin-[0]"
                        >
                            Sobretasa
                        </label>
                    </div>
                    <div className="relative z-0 w-full my-3 ml-20">
                        <button className="btn" onClick={handleCalcularSobretasa}>
                            <ButtonContent name="Calcular" loading={loadingPrecioMercadoSt}/>
                        </button>
                    </div>
                </div>



                <div className="mt-4 mb-4 p-1 bg-cyan-700 text-white text-center">
                    <span>Rendimiento</span>
                </div>
                <div className="grid grid-cols-3 gap-4">
                    <div className="relative z-0 w-full my-3">
                        <input
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            name="n_tasa_cupon_md_vig"
                            value={nPrecioMercadoR}
                            onChange={(e) => setNPrecioMercadoR(Number(e.target.value))}
                        />
                        <label
                            htmlFor="plazo"
                            className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                        >
                            Precio Mercado
                        </label>
                    </div>
                    <div className="relative z-0 w-full my-3">
                        <input
                            name="rendimiento"
                            type="text"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                                focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            value={nPrecioMercadoRResp}
                            disabled={true}
                        />
                        <label htmlFor="rendimiento"
                            className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                -translate-y-6 origin-[0]"
                        >
                            Rendimiento
                        </label>
                    </div>

                    <div className="relative z-0 w-full my-3 ml-20">
                        <button className="btn" onClick={handleCalcularRendimiento}>
                            <ButtonContent name="Calcular" loading={loadingMercadoRResp}/>
                        </button>
                    </div>
                </div>

            </div>

        </Modal>
    )
}