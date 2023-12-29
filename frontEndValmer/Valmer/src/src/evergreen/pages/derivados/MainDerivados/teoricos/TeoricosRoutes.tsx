import {Route, Routes} from "react-router-dom";
import {Teoricos} from "./Teoricos";

export const TeoricosRoutes = () => {
    return (
        <Routes>
            <Route path="" element={<Teoricos/>} />
        </Routes>
    )
}