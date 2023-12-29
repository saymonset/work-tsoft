import React from "react";
import {PrecalcAdr, PrecalcDerCorp, PrecalcFijo, PrecalcSuspend, PrecalcVinc} from "./components";
import {useInstrumentosPreCalc} from "../hooks";

export const InstrumentosPreCalc = () => {

    const {
        showVinculado,
        showAdr,
        showSuspend,
        showFijo,
        showDerCorp,
        handleShowVinculado,
        handleShowAdr,
        handleShowSuspend,
        handleShowFijo,
        handleShowDerCorp,
    } = useInstrumentosPreCalc()


    return (
        <div className="mb-12">
            <div className="form-title">
                <span>Precalculados</span>
            </div>
            <div className="mt-4 flex justify-between">

                <div className="form-check">
                    <input
                        type="checkbox"
                        checked={showVinculado}
                        onChange={handleShowVinculado}
                    />
                    <label htmlFor="vinculado">
                        Vinculado
                    </label>
                </div>
                <div className="form-check">
                    <input
                        type="checkbox"
                        checked={showAdr}
                        onChange={handleShowAdr}
                    />
                    <label htmlFor="adr">ADR</label>
                </div>
                <div className="form-check">
                    <input
                        type="checkbox"
                        name="b_tipo_crisis_shock"
                        checked={showSuspend}
                        onChange={handleShowSuspend}
                    />
                    <label htmlFor="b_tipo_crisis_shock">Suspendido</label>
                </div>
                <div className="form-check">
                    <input
                        type="checkbox"
                        name="b_valida_bbva"
                        checked={showFijo}
                        onChange={handleShowFijo}
                    />
                    <label htmlFor="b_valida_bbva">Fijo</label>
                </div>
                <div className="form-check">
                    <input
                        type="checkbox"
                        name="b_precio_cierre"
                        checked={showDerCorp}
                        onChange={handleShowDerCorp}
                    />
                    <label htmlFor="b_precio_cierre">Der.Corp</label>
                </div>
            </div>


            {showVinculado &&
                <PrecalcVinc/>
            }

            {showAdr &&
                <PrecalcAdr/>
            }

            {showSuspend &&
                <PrecalcSuspend/>
            }

            {showFijo &&
                <PrecalcFijo/>
            }

            {showDerCorp &&
                <PrecalcDerCorp/>
            }
        </div>
    );
};