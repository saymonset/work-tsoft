import React, {Suspense, useState} from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import {Footer, Header, Sidebar} from "../../shared";
import {NotFoundPage} from "../pages";
import {MoonLoader} from "react-spinners";

const LazyHome = React.lazy(() =>
    import('../pages').then(module => ({default: module.Home}))
);
const LazyDeudaRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.DeudaRoutes}))
);
const LazyInternacionalRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.InternacionalRoutes}))
);
const LazyValores = React.lazy(() =>
    import('../pages').then(module => ({default: module.Valores}))
);
const LazyLatamRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.LatamRoutes}))
);
const LazyAdminRoutes = React.lazy(() =>
    import('../pages/admin/AdminRoutes').then(module => ({default: module.AdminRoutes}))
);
const LazyCalificacionesRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.CalificacionesRoutes}))
);
const LazyAccionesRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.AccionesRoutes}))
);
const LazyDerivadosRoutes = React.lazy(() =>
    import('../pages').then(module => ({default: module.DerivadosRoutes}))
);
const LazyRegimenInversionRoutes= React.lazy(() =>
    import('../pages/').then(module => ({default: module.RegimenInversionRoutes}))
);
const LazyMantenimientoCau = React.lazy(() => 
    import('../pages/footer/cau/CauRoutes').then(module => ({default: module.CauRoutes}))
);

export const EvergreenRoutes = () => {

    const [isOpen, setIsOpen] = useState(true);
    const toggleSidebar = () => {
        setIsOpen(!isOpen);
    }

    const getLoading = () => {
        return (
            <div className="mt-8 flex justify-center items-center h-full">
                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
            </div>
        )
    }

    return (
        <div className="flex h-screen overflow-hidden">

            <Sidebar isOpen={isOpen}/>

            <div className={`relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden 
                ${isOpen ? 'ml-64' : 'ml-0'} transition-all duration-1000`}>

                <Header onClick={toggleSidebar}/>

                <div className="px-4">
                    <Routes>
                        <Route path="home" element={
                            <Suspense fallback={getLoading()}>
                                <LazyHome/>
                            </Suspense>
                        }/>

                        <Route path="valores" element={
                            <Suspense fallback={getLoading()}>
                                <LazyValores/>
                            </Suspense>
                        }/>

                        <Route path="/deuda/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyDeudaRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/internacional/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyInternacionalRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/derivados/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyDerivadosRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/acciones/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyAccionesRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/calificaciones/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyCalificacionesRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/latam/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyLatamRoutes/>
                            </Suspense>
                        }
                        />
                        <Route path="/admin/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyAdminRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/regimenInversion/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyRegimenInversionRoutes/>
                            </Suspense>
                        }/>
                        <Route path="/cau/*" element={
                            <Suspense fallback={getLoading()}>
                                <LazyMantenimientoCau/>
                            </Suspense>
                        }/>
                        <Route path="/" element={<Navigate to="/home"/>}/>
                        <Route path="notFound" element={<NotFoundPage/>}/>
                        <Route path="*" element={<Navigate to="/notFound"/>}/>
                    </Routes>
                </div>
                <Footer/>
            </div>
        </div>
    )
}