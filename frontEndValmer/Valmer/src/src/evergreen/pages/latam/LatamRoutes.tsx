import {Route, Routes} from 'react-router-dom'
import {CostaRicaRoutes} from './costaRica'
import { PanamaRoutes } from './panama';
import React from "react";

export const LatamRoutes = () => (
    <Routes>
        <Route path='/panama/*' element={<PanamaRoutes/>} />
        <Route path='/costa_rica/*' element={<CostaRicaRoutes/>} />
    </Routes>
)