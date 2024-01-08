import React from 'react';
import { PropsPrecalc } from "../../../../../../../model";
import { ButtonContent } from "../../../../../../../shared";
import {usePrecalcTvEmiSerie} from "./hooks";

export const PrecalcFijo: React.FC<PropsPrecalc> = ({ setShowState }) => {

    const {
        loadingAct,
        inputValue,
        handleChange,
        actPrecalc
    } = usePrecalcTvEmiSerie({ setShowState })

    return (
        <div className="animate__animated animate__fadeIn">
            <div className="mt-8 grid grid-cols-1 gap-4">
                <div className="border-gray-300 border my-5"></div>
            </div>

            <div className="mb-8 form-title text-center">
                <span>Fijo</span>
            </div>

            <div className="form-cols-2">
                <div className="form-input">
                    <input
                        type="text"
                        name="n_precio"
                        id="n_precio"
                        value={inputValue("precalc-fijos", "n_precio")}
                        onChange={(e) => handleChange(e, "precalc-fijos")}
                    />
                    <label htmlFor="n_precio">Precio</label>
                </div>
            </div>
            <div className="flex justify-center">
                <button className="btn"
                    type="button"
                    onClick={() => actPrecalc("fijos")}
                >
                    <ButtonContent name="Actualizar" loading={loadingAct} />
                </button>
            </div>
        </div>
    );
};