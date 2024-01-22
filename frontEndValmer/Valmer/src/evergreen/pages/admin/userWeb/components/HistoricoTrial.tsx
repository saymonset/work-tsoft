import React, { useEffect, useState } from "react"
import { DataHistoricoTrial, FvCheckbox, FvContratos } from "../../../../../model";
import { fetchDataGetRet, fetchDataPostRet, generateUUID } from "../../../../../utils";
import { BarLoader } from "react-spinners";
import { ButtonContent } from "../../../../../shared";

interface HistoricoTrialProps {
    n_nombre: number
    triggerInfo: boolean
    dataTable: DataHistoricoTrial[]
    setTrigger: React.Dispatch<React.SetStateAction<boolean>>
    setDataTable: React.Dispatch<React.SetStateAction<DataHistoricoTrial[]>>
}

export const HistoricoTrial = (data: HistoricoTrialProps) => {

    const [dataTable, setDataTable] = useState<DataHistoricoTrial[]>([])
    const [loading, setLoading] = useState<boolean>(false)
    const [loadingContrato, setLoadingContrato] = useState<boolean>(false)
    const [triggerContrato, setTriggerContrato] = useState<boolean>(false)
    const [checkbox, setCheckbox] = useState<FvCheckbox>({} as FvCheckbox)
    const [contratos, setContratos] = useState<FvContratos>({} as FvContratos)

    useEffect(() => {
        const getDataHist = async () => {
            if(data.triggerInfo) {
                setLoading(true)
                const response = await fetchDataGetRet(
                    "/admin-user-web/info-historico-trial",
                    " al consultar info historico trial",
                    {n_nombre: data.n_nombre}
                )
                setDataTable(response.body)
                setLoading(false)
                data.setTrigger(false)
            }
        }
        getDataHist().catch(() => {})
    }, [data.triggerInfo])

    useEffect(() => {
        const postDataContrato = async () => {
            if(triggerContrato) {
                setLoadingContrato(true)
                const response = await fetchDataPostRet(
                    "/admin-user-web/actualiza-contrato",
                    " al activar contrato",
                    contratos,
                    {n_nombre: data.n_nombre}
                )
                setDataTable(response)
                setLoadingContrato(false)
                setTriggerContrato(false)
            }
        }
        postDataContrato().catch(() => {})
    }, [triggerContrato])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value, checked} = e.target
        const checkedValue = {...checkbox, [name]: checked}
        const contratosValue = {...contratos, [name]: checked ? value : ''}
        setCheckbox(checkedValue)
        setContratos(contratosValue)
    }

    const handleActContrato = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setTriggerContrato(true)
    }

    return (
        <div className="form-cols-1 col-span-2 row-span-2">
            <div className="form-title">Historico Trial</div>
            {loading && <BarLoader className="mt-2" color="#059669" width={500} />}

            <div className="table">
                <table>
                    <thead className="thead text-xs">
                    <tr>
                        <th>PRODUCTO</th>
                        <th>JERARQUIA</th>
                        <th>FECHA INICIO</th>
                        <th>FECHA FIN</th>
                        <th>FECHA CONTRATO</th>
                        <th>DIAS</th>
                        <th>STATUS</th>
                        <th>CONTRATAR</th>
                    </tr>
                    </thead>
                    <tbody className="tbody">
                    {dataTable && dataTable.length > 0 && dataTable.map((item) => (
                        <tr className="tr" key={generateUUID()}>
                            <td> {item.nombre} </td>
                            <td> {item.jerarquia} </td>
                            <td> {item.d_fecha_inicio} </td>
                            <td> {item.d_fecha_fin} </td>
                            <td> {item.d_fecha_contrato} </td>
                            <td> {item.n_dias} </td>
                            <td> {item.b_trial} </td>
                            <td>
                                {item.b_trial === "A" && (
                                    <input
                                        type="checkbox"
                                        name={"contr_chkb_" + item.id_proceso}
                                        id={"contr_chkb_" + item.id_proceso}
                                        value={item.id_proceso}
                                        checked={checkbox["contr_chkb_" + item.id_proceso]}
                                        onChange={handleChange}
                                    />
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className="text-center">
                <button
                    className="btn"
                    onClick={handleActContrato}
                >
                    <ButtonContent name="Activar Contrato" loading={loadingContrato}/>
                </button>
            </div>
        </div>
    )
}