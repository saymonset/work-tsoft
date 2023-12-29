import {TvEmiSerieProps} from "../../../../../model";
import {BarLoader} from "react-spinners";
import {generateUUID} from "../../../../../utils";
import React from "react";

export const SeriesNew = (propsDef : TvEmiSerieProps) => {

    const handleTv =(event:any) => {
        propsDef.handleClickTv(event);
        propsDef.handleChange(event);
    }

    const handleEmi =(event:any) => {
        propsDef.handleEmisora(event);
        propsDef.handleChange(event);
    }

    return (
        <>
            {propsDef.loadingConsultaData && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}
            <div className="bg-cyan-700 p-1 text-slate-50">
                <span>Instrumento</span>
            </div>
            <div className="form-cols-3">
                <div className="form-select">
                    <select
                        name="s_tv"
                        value={propsDef.selectedTv}
                        onChange={handleTv}
                        required
                    >
                        <option value="default">...</option>
                        {propsDef.tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="s_tv">
                        TV
                    </label>
                    {propsDef.requiredTv && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido sTv</span>
                    )}
                </div>
                <div className="form-select">
                    <select
                        name="s_emisora"
                        value={propsDef.selectedEmisora}
                        onChange={handleEmi}
                        required>
                        <option value="default">...</option>
                        {propsDef.emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {propsDef.loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={200} />}
                    <label htmlFor="s_emisora">
                        EMISORA
                    </label>
                    {propsDef.requiredEmisora && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido s_emisora</span>
                    )}
                </div>
                <div className="form-input">
                    <input 
                        type="text" 
                        name="s_serie"
                        value={propsDef.consultaData?.body?.s_serie ?? ''}
                        onChange={propsDef.handleChange}
                    />

                    {propsDef.loadingSerie && <BarLoader className="mt-2" color="#059669" width={200} />}
                    <label htmlFor="s_serie">
                        SERIE
                    </label>
                    {propsDef.requiredSerie && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido s_serie</span>
                    )}
                </div>
            </div>
        </>
    )
}