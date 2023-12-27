import { Routes, Route } from 'react-router-dom'
import React from "react";

const LazyCostaRica = React.lazy(() =>
    import('./costaRica').then(module => ({default: module.CostaRica}))
);
const LazyCau = React.lazy(() =>
    import('./cau').then(module => ({default: module.Cau}))
);

export const CostaRicaRoutes = () => (
    <Routes>
        <Route path="/costa_rica/*" element={<LazyCostaRica/>} />
        <Route path='/cau/*' element={<LazyCau/>} />
    </Routes>
)