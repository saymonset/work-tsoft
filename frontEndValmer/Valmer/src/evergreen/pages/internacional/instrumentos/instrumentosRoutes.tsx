import {Route, Routes} from "react-router-dom";
import React from "react";
import {Instrumentos} from "./Instrumentos";
import { storeInter } from "../../../../redux";
import { Provider } from "react-redux";

export const InstrumentosRoutes = () => (
    <Routes>
        <Route path="" 
            element={
                <Provider store={storeInter}>
                    <Instrumentos/>
                </Provider>
            } 
        />
    </Routes>
);