import {Route, Routes} from "react-router-dom";
import React from "react";
import {Provider} from "react-redux";
import {storeGub} from "../../../../redux";
import {Instrumentos} from "./instrumentos";

export const GubernamentalRoutes = () => (
    <Routes>
        <Route
            path="/instrumentos"
            element={
                <Provider store={storeGub}>
                    <Instrumentos />
                </Provider>
            }
        />
    </Routes>
);