import { Route ,Routes } from "react-router-dom"
import { Listados } from "./Listados"

export const ListadosRoutes = () => (
    <Routes>
        <Route path="" element={<Listados/>} />
    </Routes>
);