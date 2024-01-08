import React from "react";
import {PrecalcAdr, PrecalcDerCorp, PrecalcFijo, PrecalcSuspend, PrecalcVinc} from "./components";
import {useInstrumentosPreCalc} from "../hooks";
import { BarLoader } from "react-spinners";

interface Props {
    loadingPrecalc: boolean
}

export const InstrumentosPreCalc: React.FC<Props> = ({ loadingPrecalc }) => {

    const {
        showState,
        handleShowVinculado,
        checkboxValue,
        deletedPrecalc,
        setShowState
    } = useInstrumentosPreCalc()


    return (
        <div className="mb-12">
            {loadingPrecalc && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}
            <div className="form-title">
                <span>Precalculados</span>
            </div>
            <div className="mt-4 justify-between grid grid-cols-5 gap-4">

                <div className="form-check justify-center">
                    <button 
                        className="fa fa-edit text-xs text-cyan-700" 
                        onClick={(e) => handleShowVinculado(e, "vinculado")}
                        disabled={checkboxValue("precalc-acciones-vinculadas", "check_adr")}
                    />
                    <input
                        name="vinculado"
                        id="vinculado"
                        type="checkbox"
                        checked={checkboxValue("precalc-acciones-vinculadas", "check_vinculado")}
                        disabled={checkboxValue("precalc-acciones-vinculadas", "check_adr")}
                        onChange={deletedPrecalc}
                    />
                    <label htmlFor="vinculado">
                        Vinculado
                    </label>
                </div>
                <div className="form-check justify-center">
                    <button 
                        className="fa fa-edit text-xs text-cyan-700" 
                        onClick={(e) => handleShowVinculado(e, "adr")}
                        disabled={checkboxValue("precalc-acciones-vinculadas", "check_vinculado")}
                    />
                    <input
                        name="adr"
                        id="adr"
                        type="checkbox"
                        checked={checkboxValue("precalc-acciones-vinculadas", "check_adr")}
                        disabled={checkboxValue("precalc-acciones-vinculadas", "check_vinculado")}
                        onChange={deletedPrecalc}
                    />
                    <label htmlFor="adr">ADR</label>
                </div>
                <div className="form-check justify-center">
                    <button 
                        className="fa fa-edit text-xs text-cyan-700" 
                        onClick={(e) => handleShowVinculado(e, "suspendido")}
                    />
                    <input
                        type="checkbox"
                        name="suspendido"
                        id="suspendido"
                        checked={checkboxValue("precalc-suspendidas", "check_suspendido")}
                        onChange={deletedPrecalc}
                    />
                    <label htmlFor="suspendido">Suspendido</label>
                </div>
                <div className="form-check justify-center">
                    <button 
                        className="fa fa-edit text-xs text-cyan-700" 
                        onClick={(e) => handleShowVinculado(e, "fijo")}
                    />
                    <input
                        type="checkbox"
                        name="fijo"
                        id="fijo"
                        checked={checkboxValue("precalc-fijos", "check_fijo")}
                        onChange={deletedPrecalc}
                    />
                    <label htmlFor="fijo">Fijo</label>
                </div>
                <div className="form-check justify-center">
                    <button 
                        className="fa fa-edit text-xs text-cyan-700" 
                        onClick={(e) => handleShowVinculado(e, "derCorp")}
                    />
                    <input
                        type="checkbox"
                        id="derCorp"
                        name="derCorp"
                        checked={checkboxValue("precalc-derecho-corp", "check_dercorp")}
                        onChange={deletedPrecalc}
                    />
                    <label htmlFor="derCorp">Der.Corp</label>
                </div>
            </div>


            {showState["vinculado"] &&
                <PrecalcVinc setShowState={setShowState} />
            }

            {showState["adr"] &&
                <PrecalcAdr setShowState={setShowState} />
            }

            {showState["suspendido"] &&
                <PrecalcSuspend setShowState={setShowState} />
            }

            {showState["fijo"] &&
                <PrecalcFijo setShowState={setShowState} />
            }

            {showState["derCorp"] &&
                <PrecalcDerCorp setShowState={setShowState} />
            }
        </div>
    );
};