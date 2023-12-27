import {BarLoader} from "react-spinners";
import React from "react";
import {generateUUID} from "../../../../../../utils";
import {useTvEmiSerieDer} from "./hooks";
import {RequeridosDerivados, RespDerivados} from "../../../../../../model";

interface TvEmiSerieDerProps {
    tv: string[];
    sMercado: string;
    urlConsultaData: string;
    requeridos: RequeridosDerivados;
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    setConsultaDataDer: React.Dispatch<React.SetStateAction<RespDerivados>>;
}
export const TvEmiSerieDer = ({
                                  tv,
                                  sMercado,
                                  urlConsultaData,
                                  requeridos,
                                  handleChange,
                                  setConsultaDataDer}: TvEmiSerieDerProps) => {

    const {
        loadingConsultaData,
        loadingEmisoras,
        loadingSerie,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        handleClickTv,
        handleEmisora,
        handleSerie
    } = useTvEmiSerieDer({
        sMercado,
        urlConsultaData,
        handleChange,
        setConsultaDataDer})

    return (
        <>
            {loadingConsultaData && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}

            <div className="form-title">
                <span>Instrumento</span>
            </div>
            <div className="form-cols-3">
                <div className="form-select">
                    <select
                        name="s_tv"
                        value={selectedTv}
                        onChange={handleClickTv}
                        ref={requeridos.s_tv}
                    >
                        <option value="default">...</option>
                        {tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="s_tv">
                        TV
                    </label>
                    {requiredTv && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido TV</span>
                    )}
                </div>
                <div className="form-select">
                    <select
                        name="s_emisora"
                        value={selectedEmisora}
                        onChange={handleEmisora}
                        ref={requeridos.s_emisora}
                    >
                        <option value="default">...</option>
                        {emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150} />}
                    <label htmlFor="emisora">
                        Emisora
                    </label>
                    {requiredEmisora && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido Emisora</span>
                    )}
                </div>
                <div className="form-select">
                    <select
                        name="s_serie"
                        value={selectedSerie}
                        onChange={handleSerie}
                        ref={requeridos.s_serie}
                    >
                        <option value="default">...</option>
                        {serie?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    {loadingSerie && <BarLoader className="mt-2" color="#059669" width={150} />}
                    <label htmlFor="serie">
                        SERIE
                    </label>
                    {requiredSerie && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido Serie</span>
                    )}
                </div>
            </div>
        </>
    )
}