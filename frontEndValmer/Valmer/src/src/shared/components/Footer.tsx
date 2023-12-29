import { Link } from "react-router-dom"
import { userEncoded } from "../../utils"

export const Footer = () => {
    return (
        <footer className={`bg-gradient-to-l from-cyan-950 to-cyan-800 py-4 mt-auto`}>
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="text-center">
                    <Link to="/home" className="text-white hover:text-green-500 font-bold py-2">
                        <span className="fa-solid fa-home"/> Inicio |
                    </Link>
                    <Link to="/regimenInversion" className="text-white hover:text-green-500 font-bold py-2">
                        <span className="fa-solid fa-book"/> Regimen inversión |
                    </Link>
                    <Link to="/cau/mantenimiento" className="text-white hover:text-green-500 font-bold py-2">
                        <span className="fa-solid fa-phone"/> CAU |
                    </Link>
                    <a className="text-white hover:text-green-500 font-bold py-2"
                       href={`/cgi/Solicitudes_Sistemas.pl?s_user=${userEncoded()}`} target="_blank">
                        <span className="fa-solid fa-computer"/> SCS |
                    </a>
                    <Link to="/latam/costa_rica/cau" className="text-white hover:text-green-500 font-bold py-2">
                        <span className="fa-solid fa-phone"/> CAU-CR |
                    </Link>
                    <a className="text-white hover:text-green-500 font-bold py-2"
                       href="http://www.valmer.com.mx/es/valmer/directorio" target="_blank">
                        <span className="fa-solid fa-book-open"/> Directorio |
                    </a>
                </div>
            </div>
        </footer>
    )
}