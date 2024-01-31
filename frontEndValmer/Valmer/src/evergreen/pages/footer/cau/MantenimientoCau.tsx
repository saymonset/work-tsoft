import { TitleDate } from "../../../../shared"
import { Link } from "react-router-dom"
import { HocRestricted } from "../../restrictedAccess"
import { GraficaHome } from "./graficas"

export const MantenimientoCau = () => {

    const title = "Mantenimiento CAU"
    
    return (
        <HocRestricted title={title} view={title}>

            <TitleDate title={title} />

            <div className="flex justify-start pr-2 mb-1">
                <Link to="/cau/historico">
                    <button className="btn">
                        <span>Histórico</span>
                    </button>
                </Link>
                <Link to="/cau/abiertos">
                    <button
                        className="btn"
                    >
                        <span>Abiertos</span>
                    </button>
                </Link>
                <Link to="/cau/cerrados">
                    <button
                        className="btn"
                    >
                        <span>Cerrados</span>
                    </button>
                </Link>
                <Link to="/cau/graficas">
                    <button
                        className="btn"
                    >
                        <span>Graficas</span>
                    </button>
                </Link>
            </div>

            <div className="form-cols-1">
                <GraficaHome/>
            </div>
        </HocRestricted>
    )
}