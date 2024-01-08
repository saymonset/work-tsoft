import React from "react";
import { ButtonContent } from "../../../../../../../shared";
import {usePrecalcTvEmiSerie} from "./hooks";
import { PropsPrecalc } from "../../../../../../../model";

export const PrecalcDerCorp: React.FC<PropsPrecalc> = ({ setShowState }) => {

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
                <span>Derecho Corp</span>
            </div>

            <div className="form-cols-2">
                <div className="form-date">
                    <input
                        type="date"
                        name="fecha_dercorp"
                        id="fecha_dercorp"
                        value={inputValue("precalc-derecho-corp", "fecha_dercorp")}
                        onChange={(e) => handleChange(e, "precalc-derecho-corp")}
                    />
                    <label htmlFor="fecha_dercorp">Fecha</label>
                </div>

                <div className="form-input">
                    <input
                        type="text"
                        name="n_monto_decorp"
                        id="n_monto_decorp"
                        value={inputValue("precalc-derecho-corp", "n_monto_decorp")}
                        onChange={(e) => handleChange(e, "precalc-derecho-corp")}
                    />
                    <label htmlFor="n_monto_decorp">Monto</label>
                </div>
            </div>
            <div className="flex justify-center">
                <button className="btn"
                    type="button"
                    onClick={() => actPrecalc("derecho-corp")}
                >
                    <ButtonContent name="Actualizar" loading={loadingAct} />
                </button>
            </div>
        </div>
    );
};