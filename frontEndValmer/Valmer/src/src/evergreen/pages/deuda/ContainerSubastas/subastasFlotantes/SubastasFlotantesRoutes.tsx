import {Route, Routes} from "react-router-dom";
import React from "react";
import { storeSub } from "../../../../../redux";
import { Provider } from "react-redux";
import {MainSubastas} from "../MainSubastas";

export const SubastasFlotantesRoutes = () => (
    <Routes>
        <Route 
            path="" 
            element={
                <Provider store={storeSub}>
                    <MainSubastas title="Subastas Flotantes" isSub={false}/>
                </Provider>
            } 
        />
    </Routes>
);