import React from "react";
import {usePrecalcPersist} from "./hooks";

export const PrecalcFijo = () => {

    const {handleChange} = usePrecalcPersist()

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
                        name="n_fijo_precio"
                        onChange={handleChange}
                    />
                    <label>
                        Precio
                    </label>
                </div>
            </div>
        </div>
    );
};