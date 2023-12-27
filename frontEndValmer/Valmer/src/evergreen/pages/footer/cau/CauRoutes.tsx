import React from "react";
import { Provider } from "react-redux";
import { Route, Routes } from "react-router-dom";
import { storeCau } from "../../../../redux";

const LazyMantenimientoCauRoutes = React.lazy(() =>
    import('./MantenimientoCau').then(module => ({default: module.MantenimientoCau}))
);

const LazyHistoricoCauRoutes = React.lazy(() =>
    import('./historico/HistoricoCau').then(module => ({default: module.HistoricoCau}))
)

const LazyAbiertosCauRoutes = React.lazy(() => 
    import('./AbiertosCau').then(module => ({default: module.AbiertosCau}))
)

const LazyModificacionCauRoutes = React.lazy(() => 
    import('./ModificacionCau').then(module => ({default: module.ModificacionCau}))
)

const LazyCerradosCauRoutes = React.lazy(() => 
    import('./CerradosCau').then(module => ({default: module.CerradosCau}))
)

const LazyGraficasCauRoutes = React.lazy(() => 
    import('./graficas').then(module => ({default: module.GraficaCauInst}))
)

const LazyGraficasAreaRoutes = React.lazy(() =>
    import('./graficas').then(module => ({default: module.GraficasArea}))
)

export const CauRoutes = () => (
    <Provider store={storeCau}>
        <Routes>
            <Route 
                path="/mantenimiento"
                element={<LazyMantenimientoCauRoutes/>}
            />
            <Route 
                path="/historico"
                element={<LazyHistoricoCauRoutes/>}
            />
            <Route 
                path="/abiertos"
                element={<LazyAbiertosCauRoutes/>}
            />
            <Route 
                path="/modificacion"
                element={<LazyModificacionCauRoutes/>}
            />
            <Route
                path="/cerrados"
                element={<LazyCerradosCauRoutes/>}
            />
            <Route
                path="/graficas"
                element={<LazyGraficasCauRoutes/>}
            />
            <Route
                path="/graficas/area"
                element={<LazyGraficasAreaRoutes/>}
            />
        </Routes>
    </Provider>
)