import React, { lazy } from 'react';
import { Route, Routes } from "react-router-dom";
import {storeCalifEmi} from "../../../redux/Calificaciones/Emisoras";
import {Provider} from "react-redux";
import {storeCalifPro} from "../../../redux/Calificaciones/Programas";
import { storeCalifInst } from '../../../redux/Calificaciones/Instrumentos';

const LazyEmisoras = lazy(() =>
    import('./emisoras').then(module => ({ default: module.Emisoras }))
);
const LazyProgramas = lazy(() =>
    import('./programas').then(module => ({ default: module.Programas }))
);
const LazyInstrumentosRoutes = lazy(() =>
    import('./instrumentos').then(module => ({ default: module.InstrumentosRoutes }))
);
export const CalificacionesRoutes = () => (
    <Routes>
        <Route path='/emisoras/*' element={
            <Provider store={storeCalifEmi}>
                <LazyEmisoras/>
            </Provider>
            }
        />
        <Route path='/programas/*' element={
            <Provider store={storeCalifPro}>
                <LazyProgramas/>
            </Provider>
        } />
        <Route path='/instrumentos/*' element={
            <Provider store={storeCalifInst}>
                <LazyInstrumentosRoutes/>
            </Provider>
        } />
    </Routes>
);
