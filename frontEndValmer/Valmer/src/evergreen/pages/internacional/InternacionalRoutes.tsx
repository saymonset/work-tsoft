import {Route, Routes} from "react-router-dom";
import React from "react";

const LazyInstrumentosRoutes = React.lazy(() =>
    import('./instrumentos').then(module => ({default: module.InstrumentosRoutes}))
);
const LazyLipperFundRoutes = React.lazy(() =>
    import('./lipperFund').then(module => ({default: module.LipperFundRoutes}))
);
const LazyEurobonosRoutes = React.lazy(() =>
    import('./eurobonos').then(module => ({default: module.EurobonosRoutes}))
);
const LazyValuacionInstEuro = React.lazy(() =>
    import('./instrumentos').then(module => ({default: module.ValuacionInstEuro}))
);
export const InternacionalRoutes = () => {
    return (
        <Routes>
            <Route path="/instrumentos/*" element={<LazyInstrumentosRoutes/>}/>
            <Route path="/lipper/fund/*" element={<LazyLipperFundRoutes />} />
            <Route path="/eurobonos/*" element={<LazyEurobonosRoutes />} />
            <Route path="/valuacion" element={<LazyValuacionInstEuro />} />
        </Routes>
    )
}