import {ButtonContent, Modal} from "../../../../../../shared";
import React from "react";
import {ModalCalProps} from "../../../../../../model";
import {useModalCalCorp} from "./hooks";
export const ModalCalCorp = (m: ModalCalProps) => {

    const {
        interests,
        characteristics,
        handleDateInit,
        handleDateEnd,
        handleValue } = useModalCalCorp(m.tasasInt)

    return (
        <>
            <Modal isOpen={m.isModalOpenCalTasas} onClose={m.handleModalCloseTasas} title="">
                <div className="grid grid-cols-1 mt-1">
                    <div className="p-1 w-full text-center text-green-700 text-lg font-bold">
                        <span>{m.instrument}</span>
                    </div>
                    <div className="p-1 bg-cyan-700 text-white text-center">
                        <span>Cálculo Tasa</span>
                    </div>
                    <div className="grid grid-cols-2 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa_cupon_md_vig"
                                value={characteristics?.n_tasa_cupon_md_vig}
                                onChange={handleValue}
                                disabled={true}
                            />
                            <label
                                htmlFor="plazo"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Tasa Cupón MD
                            </label>
                        </div>
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                                focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_ini_cupon"
                                value={characteristics?.d_fecha_ini_cupon}
                                onChange={handleDateInit}
                            />
                            <label htmlFor="d_fecha_ini_cupon"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                -translate-y-6 origin-[0]"
                            >
                                Fecha Inicio Cupón
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-2 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa_cupon_24_vig"
                                value={characteristics?.n_tasa_cupon_24_vig}
                                onChange={handleValue}
                            />
                            <label
                                htmlFor="plazo"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Tasa Cupón 24
                            </label>
                        </div>
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                                focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_fin_cupon"
                                value={characteristics?.d_fecha_fin_cupon}
                                onChange={handleDateEnd}
                            />
                            <label
                                htmlFor="fechaVencimientoEstimada"
                                className="font-medium absolute text-sm transform top-3
                                text-cyan-700 scale-75 -translate-y-6 origin-[0]">
                                Fecha Fin Cupón
                            </label>
                        </div>
                    </div>



                    <div className="p-1 bg-cyan-700 text-white text-center">
                        <span>Calculo Intereses</span>
                    </div>
                    <div className="grid grid-cols-2 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_intereses_md"
                                value={interests?.n_intereses_md ?? "0.000000"}
                                onChange={handleValue}
                                disabled={true}
                            />
                            <label htmlFor="n_intereses_md"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Intereses MD
                            </label>
                        </div>
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_intereses_24"
                                value={interests?.n_intereses_24 ?? "0.000000"}
                                onChange={handleValue}
                                disabled={true}
                                required
                            />
                            <label htmlFor="n_intereses_24"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Intereses 24
                            </label>
                        </div>
                    </div>

                    <div className='flex justify-center mt-12 px-3'>
                        <button
                            className='bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md'
                            type='button'
                            onClick={() => m.handleUpdateCalculator(
                                "/instrumentos/corporativos/actualiza-tasa-interes-calculadora",
                                " error al actualizar calculadora tasas intereses")}>
                            <ButtonContent name="Actualizar intereses" loading={m.loadingSaveCalculator}/>
                        </button>
                    </div>
                </div>
            </Modal>


            <Modal isOpen={m.isModalOpenCalPrecio} onClose={m.handleModalClosePrecio} title="">
                <div className="grid grid-cols-1 mt-1">
                    <div className="p-1 w-full text-center text-green-700 text-lg font-bold">
                        <span>{m.instrument}</span>
                    </div>
                    <div className="p-1 bg-cyan-700 text-white text-center">
                        <span>Cálculo Precio</span>
                    </div>
                    <div className="grid grid-cols-1 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                value={m?.precios?.body?.["PRECIO SUCIO FLUJOS"] || 0}
                                disabled={true}
                                required
                            />
                            <label htmlFor="PRECIO SUCIO FLUJOS"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Precio Sucio
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                value={m?.precios?.body?.["PRECIO LIMPIO FLUJOS"] || 0}
                                disabled={true}
                                required
                            />
                            <label htmlFor="PRECIO LIMPIO FLUJOS"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Precio Limpio
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1 gap-4">
                        <div className="relative z-0 w-full my-3">
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                value={m?.precios?.body?.["INTERESES FLUJOS"] || 0}
                                disabled={true}
                                required
                            />
                            <label htmlFor="INTERESES FLUJOS"
                                className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                                transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                                peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                                peer-focus:scale-75 peer-focus:-translate-y-6"
                            >
                                Intereses
                            </label>
                        </div>
                    </div>


                    <div className='flex justify-center mt-12 px-3'>
                        <button
                            className='bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md'
                            type='button'
                            onClick={() => m.handleUpdateCalculator(
                                "/instrumentos/corporativos/actualiza-precio-calculadora",
                                " error al actualizar precios calculadora")}>
                            <ButtonContent name="Actualizar precios" loading={m.loadingSaveCalculator}/>
                        </button>
                    </div>
                </div>
            </Modal>
        </>
    )
}