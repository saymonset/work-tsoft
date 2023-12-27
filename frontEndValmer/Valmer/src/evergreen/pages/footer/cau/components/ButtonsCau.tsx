import { Link } from "react-router-dom"
import { TitleDate } from "../../../../../shared"

interface ButtonsCauProps {
    title: string
}

export const ButtonsCau: React.FC<ButtonsCauProps> = ({ title }) => {
    return (
        <>
            <TitleDate title={title} />

            <div className="form-cols-4 mb-2">
                <Link to="/cau/historico" className="w-full">
                    <button className="btn w-full">Histórico</button>
                </Link>
                <Link to="/cau/abiertos" className="w-full">
                    <button className="btn w-full">Abiertos</button>
                </Link>
                <Link to="/cau/cerrados" className="w-full">
                    <button className="btn w-full">Cerrados</button>
                </Link>
                <Link to="/cau/modificacion" className="w-full">
                    <button className="btn w-full">Modificación</button>
                </Link>
            </div>
        </>
    )
}