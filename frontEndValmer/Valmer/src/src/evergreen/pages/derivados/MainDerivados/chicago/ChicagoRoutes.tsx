import {Route, Routes} from "react-router-dom";
import {Chicago} from "./Chicago";

export const ChicagoRoutes = () => {
    return (
        <Routes>
            <Route path="" element={<Chicago/>} />
        </Routes>
    )
}