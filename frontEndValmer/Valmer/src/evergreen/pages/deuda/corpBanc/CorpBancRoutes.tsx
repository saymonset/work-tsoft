import {Route, Routes} from "react-router-dom";
import React from "react";
import {Instrumentos} from "./instrumentos";
import {storeCorp} from "../../../../redux";
import {Provider} from "react-redux";
import {CambBonRef, CargaBulletBond, CargaInstr, InstCortesCupon, ValuacionInstCorp} from "./components";


export const CorpBancRoutes = () => (
    <Routes>
        <Route path="/instrumentos" element={
            <Provider store={storeCorp}>
                <Instrumentos />
            </Provider>
        } />
        <Route path="/cortes/cupon" element={<InstCortesCupon />} />
        <Route path="/bonos/referencia" element={<CambBonRef />} />
        <Route path="/valuacion/instrumentos" element={<ValuacionInstCorp />} />
        <Route path="/carga/bullet/bond" element={<CargaBulletBond />} />
        <Route path="/carga/instr" element={<CargaInstr />} />
    </Routes>
);