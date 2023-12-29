import React, { lazy } from 'react';
import { Route, Routes } from "react-router-dom";
import { Provider } from 'react-redux';
import { storeDer } from '../../../redux';

const LazyListadosRoutes = lazy(() =>
    import('./MainDerivados/listados').then(module => ({ default: module.ListadosRoutes }))
);
const LazyTeoricosRoutes = lazy(() =>
    import('./MainDerivados/teoricos').then(module => ({ default: module.TeoricosRoutes }))
);
const LazyChicagoRoutes = lazy(() =>
    import('./MainDerivados/chicago/ChicagoRoutes').then(module => ({ default: module.ChicagoRoutes }))
);
const LazyDefDervidados = lazy(() =>
    import('./defDerivados').then(module => ({ default: module.DefDerivadosRoutes }))
);

export const DerivadosRoutes = () => (
    <Provider store={storeDer}>
        <Routes>
            <Route path='/listados/*' element={<LazyListadosRoutes/>} />
            <Route path='/teoricos/*' element={<LazyTeoricosRoutes/>} />
            <Route path='/chicago/*' element={<LazyChicagoRoutes/>} />
            <Route path='/definicion/derivados/*' element={<LazyDefDervidados/>} />
        </Routes>
    </Provider>
);
