import { MoonLoader } from "react-spinners"
import { useDataGraphics } from "./hooks"
import { BarChartArea } from "./components"

export const GraficasArea = () => {

    const {
        loadingCliente,
        loadingServicio,
        loadingMesArea,
        dataMesArea,
        dataServicio,
        dataCliente,
        fechaInicio,
        fechaFin,
        setFechaFin,
        setFechaInicio,
        setTriggerCliente,
        setTriggerServicio
    } = useDataGraphics()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        if (name === "d_fecha_inicio") {
            setFechaInicio(value)
        } else {
            setFechaFin(value)
            setTriggerCliente(true)
        }
    }

    const handleConsultar = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setTriggerCliente(true)
        setTriggerServicio(true)
    }

    return (
        <>
            <div className="form-cols-5">
                <div className="form-date">
                    <input type="date"
                        id="d_fecha_inicio"
                        name="d_fecha_inicio"
                        value={fechaInicio ?? ''}
                        onChange={handleChange}
                    />
                    <label htmlFor="d_fecha_inicio">Fecha Inicio</label>
                </div>
                <div className="form-date">
                    <input type="date" 
                        id="d_fecha_fin"
                        name="d_fecha_fin"
                        value={fechaFin ?? ''}
                        onChange={handleChange}
                    />
                    <label htmlFor="d_fecha_fin">Fecha Fin</label>
                </div>
                <button 
                    className="btn my-3"
                    type="button"
                    onClick={handleConsultar}
                >
                    Consultar
                </button>
            </div>
            <div className="form-cols-2">
                <div className="card">
                    <div className="head">
                        <span>Solicitudes CAU por Cliente</span>
                    </div>
                    {loadingCliente ? (
                        <div className="body flex items-center justify-center">
                            <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                        </div>
                    ) : (
                        <div className="body">
                            <BarChartArea data={dataCliente} pantalla="cliente"/>
                        </div>
                    )}
                </div>
                <div className="card">
                    <div className="head">
                        <span>Solicitudes CAU por Servicio</span>
                    </div>
                    {loadingServicio ? (
                        <div className="body flex items-center justify-center">
                            <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                        </div>
                    ) : (
                        <div className="body">
                            <BarChartArea data={dataServicio} pantalla="servicio"/>
                        </div>
                    )}
                </div>
            </div>
            <div className="form-cols-1">
                <div className="card">
                    <div className="head">
                        <span>Solicitudes CAU del Mes por Area</span>
                    </div>
                    {loadingMesArea ? (
                        <div className="body flex items-center justify-center">
                            <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                        </div>
                    ) : (
                        <div className="body">
                            <BarChartArea data={dataMesArea} pantalla="area"/>
                        </div>
                    )}
                </div>
            </div>
        </>
    )
}