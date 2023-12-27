import { Route, Routes } from 'react-router-dom'
import { DefDerivados } from './DefDerivados'

export const DefDerivadosRoutes = () => {
    return (
        <Routes>
            <Route path='' element={<DefDerivados/>} />
        </Routes>
    )
}