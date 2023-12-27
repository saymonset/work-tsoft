import {Route, Routes} from "react-router-dom";
import React from "react";
import {LoginPage} from "../auth";
import {EvergreenRoutes} from "../evergreen";
import {PublicRoute} from "./PublicRoute";
import {PrivateRoute} from "./PrivateRoute";

export const AppRouter = () => {

    return (
        <Routes>
            <Route path="/login" element={
                <PublicRoute>
                    <LoginPage/>
                </PublicRoute>
            }/>

            <Route path="/*" element={
                <PrivateRoute>
                    <EvergreenRoutes />
                </PrivateRoute>
            } />
        </Routes>
    )
}