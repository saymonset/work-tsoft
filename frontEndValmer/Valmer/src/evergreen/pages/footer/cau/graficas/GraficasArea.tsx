import { MoonLoader } from "react-spinners"
import { useDataGraphics } from "./hooks"
import { BarChartArea, PieChartClientServ } from "./components"
import { AreaGraphics } from "../../../../../model"
import { ColorsGraph } from "../../../../../utils"

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

    const getGraph = (data: AreaGraphics[], isCliente = true) => {
        if (!data) {
            return (
                <div className="flex justify-center h-full items-center">
                        No hay Información
                </div>
            )
        } else {
            return (
                <div className="body">
                    <PieChartClientServ dataBody={(isCliente)?dataCliente:dataServicio} />
                </div>
            )
        }
         
    }

    return (
        <>
            <div className="form-cols-5">
                <div className="form-date form-date-my">
                    <input type="date"
                        id="d_fecha_inicio"
                        name="d_fecha_inicio"
                        value={fechaInicio ?? ''}
                        onChange={handleChange}
                    />
                    <label htmlFor="d_fecha_inicio">Fecha Inicio</label>
                </div>
                <div className="form-date form-date-my">
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
                        <div className="head ">
                            <div className="form-cols-2">
                                
                                        <table className="table">
                                            <thead className="thead">
                                                <tr>
                                                    <th className="px-3"></th>
                                                    <th>Solicitudes CAU por Cliente</th>
                                                    <th className="px-1">Solicitudes -  %</th>
                                                </tr>
                                            </thead>
                                            <tbody className="tbody">
                                            {dataCliente?.map((item, key) => (
                                                <tr key={key}>
                                                <td style={{background: ColorsGraph[key  % ColorsGraph.length]}} ></td>
                                                <td>{ item.cliente }</td>
                                                <td>{ item.peticiones }</td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    
                                        {loadingCliente ? (
                                                <div className="body flex items-center justify-center">
                                                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                                                </div>
                                            ) : (
                                                getGraph(dataCliente, true)
                                            )}
                            </div>
                        </div>
                    
                 
                </div>
               
                    <div className="card">
                        <div className="head ">
                            <div className="form-cols-2">
                                
                                        <table className="table">
                                            <thead className="thead">
                                                <tr>
                                                    <th className="px-3"></th>
                                                    <th>Solicitudes CAU por Servicio</th>
                                                    <th className="px-1">Solicitudes -  %</th>
                                                </tr>
                                            </thead>
                                            <tbody className="tbody">
                                            {dataServicio?.map((item, key) => (
                                                <tr key={key}>
                                                <td style={{background: ColorsGraph[key  % ColorsGraph.length]}} ></td>
                                                <td>{ item.servicio }</td>
                                                <td>{ item.peticiones }</td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    
                                    {loadingServicio ? (
                                    <div className="body flex items-center justify-center">
                                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                                    </div>
                                        ) : (
                                            getGraph(dataServicio, false)
                                        )}
                            </div>
                        </div>
                    
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