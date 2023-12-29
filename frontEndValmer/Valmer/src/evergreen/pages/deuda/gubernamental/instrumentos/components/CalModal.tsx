import React, { useEffect } from 'react'
// import {ButtonContent, Modal} from "../../../../../shared";
import { CalProps } from '../../../../../../model'
import { ButtonContent, Modal } from '../../../../../../shared';
import { useCalModal } from '../hooks/useCalModal';

export const CalModal = (c: CalProps) => {

    
   const {
    selectDate,
    nPrecioRendResp,
    nPrecioMercadoStResp,
    nPrecioMercadoRResp,
    loadingPrecioRend,
    loadingPrecioMercadoSt,
    loadingMercadoRResp,
    setSelectDate,
    handleCalcularPrecioRendimiento,
    handleCalcularSobretasa,
    handleCalcularRendimiento
} = useCalModal(c)
    
   useEffect(() => {
    if (c.isModalOpen) {
    }
}, [c.isModalOpen,
    ]);


  return (
    <Modal isOpen={c.isModalOpen} onClose={c.handleModalClose} title="">

    <div className="grid grid-cols-1 mt-1">

        <div className="mt-4 mb-4 p-1 bg-cyan-700 text-white text-center">
            <span>Calculo Tasa</span>
        </div>
        <div className="grid grid-cols-2 gap-4">
            <div className="relative z-0 w-full my-3">
                <input
                    type="text"
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    name="n_tasa_cupon_md_vig"
                    value={''}
                    onChange={(e) => console.log(Number(e.target.value))}
                />
                <label
                    htmlFor="plazo"
                    className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                        transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                        peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                        peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Tasa cupón MD
                </label>
            </div>
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
                <label htmlFor="precio"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                        -translate-y-6 origin-[0]"
                >
                    Fecha Inicio Cupón
                </label>
            </div>
            <div className="relative z-0 w-full my-3">
                <input
                    type="text"
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    name="n_tasa_cupon_md_vig"
                    value={''}
                    onChange={(e) => console.log(Number(e.target.value))}
                />
                <label
                    htmlFor="plazo"
                    className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900 duration-300
                        transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-cyan-700
                        peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0
                        peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                      Tasa cupón 24
                </label>
            </div>
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
                <label htmlFor="precio"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                        -translate-y-6 origin-[0]"
                >
                    Fecha Fin Cupón
                </label>
            </div>
            {/* <div className="relative z-0 w-full my-3 ml-20">
                <button className="btn" onClick={handleCalcularPrecioRendimiento}>
                    <ButtonContent name="Calcular" loading={loadingPrecioRend}/>
                </button>
            </div> */}
        </div>



        <div className="mt-4 mb-4 p-1 bg-cyan-700 text-white text-center">
            <span>Calculo Interes</span>
        </div>
        <div className="grid grid-cols-3 gap-4">
            <div className="relative z-0 w-full my-3">
                <input
                    type="text"
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    name="n_tasa_cupon_24_vig"
                    value={''}
                    onChange={(e) => console.log(Number(e.target.value))}
                />
                <label
                    htmlFor="plazo"
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
                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                        focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    value={nPrecioMercadoStResp}
                    disabled={true}
                />
                <label
                    htmlFor="fechaVencimientoEstimada"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75 -translate-y-6 origin-[0]"
                >
                    Interes 24
                </label>
            </div>
            {/* <div className="relative z-0 w-full my-3 ml-20">
                <button className="btn" onClick={handleCalcularSobretasa}>
                    <ButtonContent name="Calcular" loading={loadingPrecioMercadoSt}/>
                </button>
            </div> */}
        </div>
        

    </div>

</Modal>
  )
}
