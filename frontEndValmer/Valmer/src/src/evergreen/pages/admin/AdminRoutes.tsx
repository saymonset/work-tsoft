import React, { lazy } from 'react';
import { Provider } from 'react-redux';
import { Route, Routes } from "react-router-dom";
import { storeUserWeb } from '../../../redux/Admin/UserWeb';

const LazyCatalogosRoutes = lazy(() =>
    import('./catalogos/CatalogosRoutes').then(module => ({ default: module.CatalogosRoutes }))
);
const LazyUsuarios = lazy(() =>
    import('./usuarios').then(module => ({ default: module.Usuarios }))
);
const LazyMailRoutes = lazy(() =>
    import('./mail/MailRoutes').then(module => ({ default: module.MailRoutes }))
);
const LazyUserWeb = lazy(() =>
    import('./userWeb').then(module => ({ default: module.UserWeb }))
);
const LazyReutersRoutes = lazy(() =>
    import('./reuters/ReutersRoutes').then(module => ({ default: module.ReutersRoutes }))
);

export const AdminRoutes = () => (
    <Routes>
            <Route path='/catalogos/*' element={<LazyCatalogosRoutes/>}/>
            <Route path='/usuarios/*' element={<LazyUsuarios/>}/>
            <Route path='/mail/*' element={<LazyMailRoutes/>}/>
            <Route path='/user/web/*' element={
                <Provider store={storeUserWeb}>
                    <LazyUserWeb/>
                </Provider>
            }/>
            <Route path='reuters/*' element={<LazyReutersRoutes/>}/>
    </Routes>
);