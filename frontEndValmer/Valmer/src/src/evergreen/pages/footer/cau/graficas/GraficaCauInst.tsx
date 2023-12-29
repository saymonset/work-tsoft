import { MoonLoader } from "react-spinners"
import { useDataGraphics } from "./hooks"
import { SimplePieChart } from "./components"

export const GraficaCauInst = () => {

    const {
        loadingDataInst,
        instGraphics
    } = useDataGraphics()

    return (
        <div className="text-center form-cols-1 mt-3">
            <div className="card my-3">
                <div className="head">
                    <span>Reporte de Solicitudes por Institución</span>
                </div>

                {loadingDataInst || instGraphics === undefined ? (
                    <div className="body flex items-center justify-center">
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                    </div>
                ) : (
                    <div className="body h-screen">
                        <SimplePieChart dataBody={instGraphics}/>
                    </div>
                )}
            </div>
        </div>
    )
}