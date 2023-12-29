import React from 'react';
import {Route, Routes} from "react-router-dom";
import {storeEuro} from "../../../../redux";
import {Provider} from "react-redux";
import {Eurobonos} from "./Eurobonos";

export const EurobonosRoutes = () => (
    <Routes>
        <Route path=""
               element={
                   <Provider store={storeEuro}>
                       <Eurobonos/>
                   </Provider>
               }
        />
    </Routes>
);