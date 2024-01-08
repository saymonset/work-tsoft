import React from "react";
import { PropsPrecalc } from "../../../../../../../model";
import { ButtonContent } from "../../../../../../../shared";
import {usePrecalcTvEmiSerie} from "./hooks";

export const PrecalcSuspend: React.FC<PropsPrecalc> = ({ setShowState }) => {

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
                <span>Suspendidas</span>
            </div>

            <div className="form-cols-2">
                <div className="form-date">
                    <input
                        type="date"
                        name="fecha_suspension"
                        id="fecha_suspension"
                        value={inputValue("precalc-suspendidas", "fecha_suspension")}
                        onChange={(e) => handleChange(e, "precalc-suspendidas")}
                    />
                    <label htmlFor="fecha_suspension">Fecha Suspensión</label>
                </div>
            </div>
            <div className="flex justify-center">
                <button className="btn"
                    type="button"
                    onClick={() => actPrecalc("suspendidos")}
                >
                    <ButtonContent name="Actualizar" loading={loadingAct} />
                </button>
            </div>
        </div>
    );
};