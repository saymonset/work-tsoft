import React from 'react';
import {generateUUID} from "../../../../../../../utils";
import {BarLoader} from "react-spinners";
import {usePrecalcTvEmiSerie} from "./hooks";
import { ButtonContent } from "../../../../../../../shared";
import { PropsPrecalc } from "../../../../../../../model";

export const PrecalcVinc: React.FC<PropsPrecalc> = ({ setShowState }) => {

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
        selectValue,
        actPrecalc
    } = usePrecalcTvEmiSerie({ setShowState })

    return (
        <div className="animate__animated animate__fadeIn">
            <div className="mt-8 grid grid-cols-1 gap-4">
                <div className="border-gray-300 border my-5"></div>
            </div>

            <div className="mb-8 form-title text-center">
                <span>Vinculados</span>
            </div>

            <div className="form-cols-3">

                <div className="form-select">
                    <select
                        id="s_tv_vin"
                        name="s_tv_vin"
                        value={
                            selectedTv || 
                            selectValue(
                                "precalc-acciones-vinculadas",
                                "vinculo_vin",
                                "tv"
                            )
                        }
                        onChange={handleClickTv}
                    >
                        <option value="default">...</option>
                        {tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingTv && <BarLoader className="mt-2" color="#059669" width={80}/>}
                    <label htmlFor="s_tv_vin">TV</label>
                </div>
                <div className="form-select">
                    <select
                        id="s_emisora_vin"
                        name="s_emisora_vin"
                        value={
                            selectedEmisora || 
                            selectValue(
                                "precalc-acciones-vinculadas",
                                "vinculo_vin",
                                "emisora"
                            )
                        }
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
                    <label htmlFor="s_emisora_vin">Emisora</label>
                </div>
                <div className="form-select">
                    <select
                        id="s_serie_vin"
                        name="s_serie_vin"
                        value={
                            selectedSerie || 
                            selectValue(
                                "precalc-acciones-vinculadas",
                                "vinculo_vin",
                                "serie"
                            )
                        }
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
                    <label htmlFor="s_serie_vin">SERIE</label>
                </div>
            </div>
            <div className="flex justify-center">
                <button className="btn"
                    type="button"
                    onClick={() => actPrecalc("vinculados")}
                >
                    <ButtonContent name="Actualizar" loading={loadingAct} />
                </button>
            </div>
        </div>
    );
};