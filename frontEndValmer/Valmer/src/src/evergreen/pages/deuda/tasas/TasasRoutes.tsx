import {Route, Routes} from "react-router-dom";
import React from "react";
import {Tasas} from "./Tasas";
import {Provider} from "react-redux";
import {storeTasas} from "../../../../redux";

export const TasasRoutes = () => (
    <Routes>
        <Route path="" element={
            <Provider store={storeTasas}>
                <Tasas />
            </Provider>
        } />
    </Routes>
);