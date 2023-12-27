import React, { lazy } from 'react';
import { Provider } from 'react-redux';
import { Route, Routes } from "react-router-dom";
import {storeAccInst, storeProcess} from "../../../redux";

const LazyInstrumentos = lazy(() =>
    import('./instrumentos').then(module => ({ default: module.Instrumentos }))
);
const LazyProcesoRV = lazy(() =>
    import('./procesoRV').then(module => ({ default: module.ProcesoRV }))
);
export const AccionesRoutes = () => (
    <Routes>
        <Route path='/instrumentos/*' element={
            <Provider store={storeAccInst}>
                <LazyInstrumentos/>
            </Provider>
        }
        />
        <Route path='/procesos/rv/*' element={
            <Provider store={storeProcess}>
                <LazyProcesoRV/>
            </Provider>
        } 
        />
    </Routes>
);
