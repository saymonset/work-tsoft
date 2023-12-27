import {Route, Routes} from "react-router-dom";
import React from "react";
import {Provider} from "react-redux";
import { storeSub } from "../../../../../redux";
import {MainSubastas} from "../MainSubastas";

export const SubastasRoutes = () => (
    <Routes>
        <Route 
            path="" 
            element={
                <Provider store={storeSub}>
                    <MainSubastas title="Subastas" isSub={true}/>
                </Provider>
            } 
        />
    </Routes>
);