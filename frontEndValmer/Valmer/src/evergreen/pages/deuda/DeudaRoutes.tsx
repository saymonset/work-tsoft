import React from "react";
import {Route, Routes} from "react-router-dom";

const LazyGubernamentalRoutes = React.lazy(() =>
    import('./gubernamental').then(module => ({default: module.GubernamentalRoutes}))
);
const LazyCorpBancRoutes = React.lazy(() =>
    import('./corpBanc').then(module => ({default: module.CorpBancRoutes}))
);
const LazyTasasRoutes = React.lazy(() =>
    import('./tasas').then(module => ({default: module.TasasRoutes}))
);
const LazySubastasRoutes = React.lazy(() =>
    import('./ContainerSubastas').then(module => ({default: module.SubastasRoutes}))
);
const LazySubastasFlotantesRoutes = React.lazy(() =>
    import('./ContainerSubastas').then(module => ({default: module.SubastasFlotantesRoutes}))
);
const LazyGeneraSi = React.lazy(() =>
    import('./generaSi').then(module => ({default: module.GeneraSi}))
);
export const DeudaRoutes = () => (
    <Routes>
        <Route path="/gubernamental/*" element={<LazyGubernamentalRoutes />} />
        <Route path="/corpBanc/*" element={<LazyCorpBancRoutes />} />
        <Route path="/tasas/*" element={<LazyTasasRoutes />} />
        <Route path="/subastas/*" element={<LazySubastasRoutes />} />
        <Route path="/subastas/flotantes/*" element={<LazySubastasFlotantesRoutes />} />
        <Route path="/genera/si" element={<LazyGeneraSi />} />
    </Routes>
);