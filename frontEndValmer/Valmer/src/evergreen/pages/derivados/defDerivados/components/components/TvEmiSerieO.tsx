import { FormValuesVenc } from "../../../../../../model"
import { ButtonContent } from "../../../../../../shared"
import React from "react";

interface TvEmiSerieO {
    tv: string
    emisora: string
    serieOp: string[]
    formValuesVenc: FormValuesVenc
    loading: boolean
    setIsNewSerie: React.Dispatch<React.SetStateAction<boolean>>
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void
}

export const TvEmiSerieO = (props: TvEmiSerieO) => {

    const handleNewSerie = () => {
        props.setIsNewSerie(true)
    }

    return (
        <>
            <div className="flex">
                <div className="mt-2 text-sm w-1/4">
                    <label htmlFor="sTv" className="mr-1">TV</label>
                    <input 
                        type="text" 
                        name="sTv" 
                        id="sTv" 
                        className="border-2 border-gray-400 px-1 w-2/3"
                        value={props.tv}
                        disabled
                    />
                </div>
                <div className="mt-2 text-sm w-1/4">
                    <label htmlFor="sEmisora" className="mr-1">EMISORA</label>
                    <input 
                        type="text" 
                        name="sEmisora" 
                        id="sEmisora" 
                        className="border-2 border-gray-400 px-1 w-1/2"
                        value={props.emisora}
                        disabled
                    />
                </div>
                <div className="mt-2 text-sm w-1/4">
                    <label htmlFor="sSerie" className="mr-1">SERIE</label>
                    <select 
                        className="border-2 border-gray-400 px-1 w-2/3"
                        name="sSerie" 
                        id="sSerie"
                        value={props.formValuesVenc.sSerie || ''}
                        onChange={(e) => props.handleChange(e)}
                    >
                        <option value="default">...</option>
                        {props.serieOp.map((item) => (
                            <option key={item} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mt-2 text-sm w-1/4">
                    <button 
                        className="text-cyan-700 hover:text-cyan-600"
                        onClick={handleNewSerie}
                    >
                        Nueva Serie
                    </button>
                </div>
            </div>
            <div className="flex mt-3">
                <div className="mt-2 text-sm w-full">
                    <div className="my-1">
                        <label htmlFor="date_picker0">Fecha Vto 0:</label>
                        <input 
                            type="date"
                            name="date_picker0" 
                            id="date_picker0" 
                            className="border-2 border-gray-400 ml-1"
                            value={props.formValuesVenc.date_picker0}
                            onChange={(e) => props.handleChange(e)}
                        />
                    </div>
                    <div className="my-1">
                        <label htmlFor="date_picker0">Fecha Vto 1:</label>
                        <input 
                            type="date"
                            name="date_picker1" 
                            id="date_picker1" 
                            className="border-2 border-gray-400 ml-1"
                            value={props.formValuesVenc.date_picker1}
                            onChange={(e) => props.handleChange(e)}
                        />
                    </div>
                </div>
                <div className="mt-2 text-sm w-1/3">
                    
                </div>
                <div className="text-right w-full">
                    <button 
                        className="btn"
                    >
                        <ButtonContent name="Guardar" loading={props.loading} />
                    </button>
                </div>
            </div>
        </>
    )
}