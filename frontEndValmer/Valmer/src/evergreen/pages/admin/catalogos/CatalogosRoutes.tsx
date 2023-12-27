import {Mexico} from "./mexico";
import {Calificaciones} from "./calificaciones";
import {Panama} from "./panama";
import {CostaRica} from "./costaRica";
import {Cau} from "./cau";
import {Archivos} from "./archivo";
import {Brokers} from "./brokers";
import {Route, Routes} from "react-router-dom";
import React from "react";
import {Perfil} from "./perfil";
import {BaseAcc} from "./baseAcc";
import {ClassSec} from "./classSec";
import {Provider} from "react-redux";
import {storeAdmonCatalog} from "../../../../redux";


export const CatalogosRoutes = () => (
    <Provider store={storeAdmonCatalog}>
            <Routes>
                    <Route path='/mexico/*' element={<Mexico/>}/>
                    <Route path='/calificaciones/*' element={<Calificaciones/>}/>
                    <Route path='/panama/*' element={<Panama/>}/>
                    <Route path='/costa/rica/*' element={<CostaRica/>}/>
                    <Route path='/cau/*' element={<Cau/>}/>
                    <Route path='/archivos/*' element={<Archivos/>}/>
                    <Route path='/brokers/*' element={<Brokers/>}/>
                    <Route path='/perfil/*' element={<Perfil/>}/>
                    <Route path='/acc/*' element={<BaseAcc/>}/>
                    <Route path='/sec/*' element={<ClassSec/>}/>
            </Routes>
    </Provider>
)