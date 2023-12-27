import React from "react";
import {usePrecalcPersist} from "./hooks";

export const PrecalcSuspend = () => {

    const {handleChange} = usePrecalcPersist()

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
                        name="d_susp_fecha"
                        onChange={handleChange}
                    />
                    <label>
                        Fecha Suspensión
                    </label>
                </div>
            </div>
        </div>
    );
};