import { Routes, Route } from 'react-router-dom'
import React from "react";

const LazyHistoricoPanama = React.lazy(() =>
    import('./historico/HistoricoPanama').then(module => ({default: module.HistoricoPanama}))
)

const LazyPanama = React.lazy(() =>
    import('./Panama').then(module => ({default: module.Panama}))
)

export const PanamaRoutes = () => (
    <Routes>
        <Route path="/historico/*" element={<LazyHistoricoPanama/>} />
        <Route path="/panama/*" element={<LazyPanama/>} />
    </Routes>
)