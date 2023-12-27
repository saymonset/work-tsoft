import {BarLoader} from "react-spinners";
import {useClientesActuales} from "./hooks";

export const ClientesActuales = () => {

    const {
        loadingClients,
        tableClients,
        handleNewClient,
        handleDeleteClient,
        handleSelectChange
    } = useClientesActuales()

    const renderClientesActuales = () => {
        if (loadingClients) {
            return <BarLoader className="w-full mt-2 mb-2" color="#059669" width={200} />;
        }

        if (tableClients?.body?.clientes_actuales && typeof(tableClients?.body?.clientes_actuales) != "string" && Object.keys(tableClients.body.clientes_actuales).length > 0) {
            return (
                <ul>
                    {Object.entries(tableClients.body.clientes_actuales).map(([key, value]) => (
                        <li key={key} className="flex justify-between items-center">
                            {value}
                            <button
                                type="button"
                                className="btn"
                                style={{ background: 'transparent', border: 'none', padding: 0, outline: 'none' }}
                                onClick={() => handleDeleteClient(key)}
                            >
                                <i className="fa fa-trash" style={{ color: 'black' }}></i>
                            </button>
                        </li>
                    ))}
                </ul>
            );
        }

        return <div>Sin clientes asociados</div>;
    }


    return (
        <>
            <div className="mb-4 form-title">
                <span>Gestor Clientes</span>
            </div>
            <div className="form-cols-3">
                <div className="form-select">
                    <select
                        onChange={(e) => handleSelectChange(e)}
                    >
                        <option value="default">...</option>
                        {
                            tableClients?.body?.clientes
                                ? Object.entries(tableClients.body.clientes)
                                    .sort((a, b) => a[1].localeCompare(b[1]))
                                    .map(([key, value]) => (
                                        <option key={key} value={key}>{value}</option>
                                    ))
                                : null
                        }
                    </select>
                    <label htmlFor="n_gestor_compania_sector">Clientes</label>
                </div>
                <div className="flex items-center justify-center">
                    <button type="button" className="btn" onClick={handleNewClient}>
                        <span className="mr-2">Agregar</span>
                        <i className="fa fa-angle-right"></i>
                    </button>
                </div>
                <div className="grid grid-cols-1">
                    <div className="mb-2 font-semibold">Clientes Actuales</div>
                    {renderClientesActuales()}
                </div>
            </div>
        </>
    )
}