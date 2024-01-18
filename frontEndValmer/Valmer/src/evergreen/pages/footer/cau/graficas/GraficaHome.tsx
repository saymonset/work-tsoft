import { MoonLoader } from "react-spinners"
import { useDataGraphics } from "./hooks"
import { SimpleBarChart } from "./components"
import { Link } from "react-router-dom"

export const GraficaHome = () => {

    const {
        loading,
        data
    } = useDataGraphics()

    return (
        <div className="text-center form-cols-1 mt-3">
            <div className="card my-3">
                <div className="head">
                    <span>Reporte de Solicitudes</span>
                </div>

                {loading ? (
                    <div className="body flex items-center justify-center">
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                    </div>
                ) : (
                    <>
                        <div className="body">
                            <SimpleBarChart data={data}/>
                        </div>
                        <div className="footer">
                            <Link to="/cau/graficas/area">
                                <button className="btn-link font-semibold opacity-70">Graficas por áreas</button>
                            </Link>
                        </div>
                    </>
                )}
            </div>
        </div>
    )
}