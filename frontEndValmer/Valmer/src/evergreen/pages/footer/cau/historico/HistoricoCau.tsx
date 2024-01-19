import { BarLoader, MoonLoader } from "react-spinners";
import { useGetCatalogs } from "../hooks";
import { fetchDataGetRet, getCatalogs } from "../../../../../utils";
import { TitleDate } from "../../../../../shared";
import { ObtenerCsv, TableCauHist } from "./components";
import React, { useEffect, useState } from "react";
import { DataTableHist, FvDataHist } from "../../../../../model";
import { HocRestricted } from "../../../restrictedAccess";

export const HistoricoCau = () => {
    const {
        loadingCatalog,
        catalog
    } = useGetCatalogs();

    const [triggerDataHist, setTriggerDataHist] = useState<boolean>(false)
    const [loadingDataHist, setLoadingDataHist] = useState<boolean>(false)
    const [showMessageNoData, setShowMessageNoData] = useState<boolean>(false)
    const [fvDataHist, setFvDataHist] = useState<FvDataHist>({n_status: "0"} as FvDataHist)
    const [dataCauHist, setDataCauHist] = useState<DataTableHist>({} as DataTableHist)

    useEffect(() => {
        const getDataCauHist = async () => {
            try {
                if(triggerDataHist) {
                    setLoadingDataHist(true)
                    const response = await fetchDataGetRet(
                        '/cau/historico/consulta-info',
                        " al consultar datos históricos",
                        {
                            n_status: fvDataHist.n_status,
                            d_fecha_inicio: fvDataHist.d_fecha_inicio,
                            d_fecha_fin: fvDataHist.d_fecha_fin
                        }
                    )

                    if(response.message === "No existen registros") {
                        setDataCauHist({} as DataTableHist)
                        setShowMessageNoData(true)
                    }
                    else {
                        setDataCauHist(response.body)
                    }
                    setLoadingDataHist(false)
                    setTriggerDataHist(false)
                }
            } catch (error) {
                setLoadingDataHist(false)
                setTriggerDataHist(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getDataCauHist().then()
    })

    const handleGenerar = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setShowMessageNoData(false)
        setTriggerDataHist(true)
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target
        const values: FvDataHist = {...fvDataHist, [name]: value}
        setFvDataHist(values)
    }

    if (loadingCatalog || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-96">
                {loadingCatalog ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    const title = "Mantenimiento CAU"
    
    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title="Históricos Cau" />

            <div className="form-cols-1">
                <div className="form-title">Solicitud</div>
                <div className="form-cols-4">
                    <div className="form-select">
                        <select 
                            name="n_status"
                            id="n_status"
                            value={fvDataHist.n_status || "0"}
                            onChange={handleChange}
                        >
                            <option value="0">...</option>
                            {getCatalogs(catalog, 'CAU_STATUS').map((item) => (
                                <option value={item[0]} key={item[0]}>
                                    {item[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_status">STATUS</label>
                    </div>
                    <div className="form-date">
                        <input 
                            type="date"
                            id="d_fecha_inicio"
                            name="d_fecha_inicio"
                            value={fvDataHist.d_fecha_inicio}
                            onChange={handleChange}
                        />
                        <label htmlFor="d_fecha_inicio">Fecha Inicio</label>
                    </div>
                    <div className="form-date">
                        <input 
                            type="date"
                            id="d_fecha_fin"
                            name="d_fecha_fin"
                            value={fvDataHist.d_fecha_fin}
                            onChange={handleChange}
                        />
                        <label htmlFor="d_fecha_fin">Fecha Fin</label>
                    </div>
                    <div className="flex justify-center py-3">
                        <button 
                            className="btn w-full"
                            onClick={handleGenerar}
                        >
                            Generar
                        </button>
                    </div>
                </div>

                <hr className="line" />

                {loadingDataHist && <BarLoader className="mt-2" color="#059669" width={500} />}

                {Object.keys(dataCauHist).length === 0 && showMessageNoData ? (
                    <div className="text-center">No existe información disponible.</div>
                ) : (
                    <>
                        {Object.keys(dataCauHist).length !== 0 && (
                            <>
                                <ObtenerCsv
                                    n_status={fvDataHist.n_status}
                                    d_fecha_inicio={fvDataHist.d_fecha_inicio}
                                    d_fecha_fin={fvDataHist.d_fecha_fin}
                                />
                                <TableCauHist data={dataCauHist} />
                            </>
                        )}
                    </>
                )}
            </div>
        </HocRestricted>
    )
}