import React from "react";
import {usePrecalcTvEmiSerie} from "./hooks";
import {generateUUID} from "../../../../../../../utils";
import {BarLoader} from "react-spinners";
import { ButtonContent } from "../../../../../../../shared";
import { PropsPrecalc } from "../../../../../../../model";

export const PrecalcAdr: React.FC<PropsPrecalc> = ({ setShowState }) => {

    const {
        tv,
        emisora,
        serie,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingTv,
        loadingEmisoras,
        loadingSerie,
        loadingAct,
        handleClickTv,
        handleEmisora,
        handleSerie,
        handleChange,
        inputValue,
        selectValue,
        actPrecalc
    } = usePrecalcTvEmiSerie({ setShowState })


    return (
    <div className="animate__animated animate__fadeIn">
        <div className="mt-8 grid grid-cols-1 gap-4">
            <div className="border-gray-300 border my-5"></div>
        </div>

        <div className="mb-8 form-title text-center">
            <span>ADR</span>
        </div>

        <div className="form-cols-3">

            <div className="form-select">
                <select
                    name="s_tv_der"
                    id="s_tv_der"
                    value={
                        selectedTv || 
                        selectValue(
                            "precalc-acciones-vinculadas", 
                            "vinculo_adr", 
                            "tv")}
                    onChange={handleClickTv}
                >
                    <option value="default">...</option>
                    {tv?.map((item: string) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>
                {loadingTv && <BarLoader className="mt-2" color="#059669" width={80}/>}
                <label htmlFor="s_tv_der">TV</label>
            </div>
            <div className="form-select">
                <select
                    name="s_emisora_der"
                    id="s_emisora_der"
                    value={
                        selectedEmisora || 
                        selectValue(
                            "precalc-acciones-vinculadas", 
                            "vinculo_adr", 
                            "emisora")}
                    onChange={handleEmisora}
                >
                    <option value="default">...</option>
                    {emisora?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>

                {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={80}/>}
                <label htmlFor="s_emisora_der">Emisora</label>
            </div>
            <div className="form-select">
                <select
                    name="s_serie_der"
                    id="s_serie_der"
                    value={
                        selectedSerie || 
                        selectValue("precalc-acciones-vinculadas", 
                        "vinculo_adr", 
                        "serie")}
                    onChange={handleSerie}
                >
                    <option value="default">...</option>
                    {serie?.map((item) => (
                        <option key={generateUUID()} value={item}>
                            {item}
                        </option>
                    ))}
                </select>

                {loadingSerie && <BarLoader className="mt-2" color="#059669" width={80}/>}
                <label htmlFor="s_serie_der">SERIE</label>
            </div>

            <div className="form-input">
                <input
                    type="text"
                    name="prpoporcion"
                    id="prpoporcion"
                    value={inputValue("precalc-acciones-vinculadas", "prpoporcion")}
                    onChange={(e) => handleChange(e, "precalc-acciones-vinculadas")}
                />
                <label htmlFor="prpoporcion">Proporción</label>
            </div>
        </div>
        <div className="flex justify-center">
            <button className="btn"
                type="button"
                onClick={() => actPrecalc("adr")}
            >
                <ButtonContent name="Actualizar" loading={loadingAct} />
            </button>
        </div>
    </div>
    );
};