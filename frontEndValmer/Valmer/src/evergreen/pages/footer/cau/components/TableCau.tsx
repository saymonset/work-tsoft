import React, { useEffect, useState } from 'react'
import { BarLoader } from "react-spinners"
import { DataTableCau, DataTableCauAbiertos } from "../../../../../model"
import { FilterReg } from './FilterReg'
import { Paginador } from './Paginador'

interface TableCauProps {
    cau: string
    data: DataTableCau
    loading: boolean
    numRegistros: number
    posicion: number
    search: string
    setNumRegistros: React.Dispatch<React.SetStateAction<number>>
    setPosicion: React.Dispatch<React.SetStateAction<number>>
    setSearch: React.Dispatch<React.SetStateAction<string>>
    setFolio: React.Dispatch<React.SetStateAction<number>>
    setTriggerInfo: React.Dispatch<React.SetStateAction<boolean>>
}

export const TableCau: React.FC<TableCauProps> = (
        {cau, data, loading, numRegistros, posicion, search, setNumRegistros, setPosicion, setSearch, setFolio, setTriggerInfo }
    ) => {

    const [dataTable, setDataTable] = useState<DataTableCauAbiertos[]>([])
    const [totalRegistros, setTotalRegistros] = useState<number>(data?.total_registros)

    const fin = posicion + numRegistros

    useEffect(() => {
        setDataTable(data?.registros)
        setTotalRegistros(data?.total_registros)
    }, [])
    
    const handleBuscar = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const valueSearch = e.target.value
        setSearch(valueSearch)
        if (search) {

            const result = data?.registros?.filter(item => 
                Object.values(item).some(value => 
                        value.toString().toLowerCase().includes(valueSearch.toLowerCase())
                )
            )

            const num = result.length
            setTotalRegistros(num)
    
            setDataTable(result)
        }
    }
    
    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const num_registros = parseInt(e.target.value)
        setNumRegistros(num_registros)
    }
    
    const goToPage = (numPosicion: number) => {
        setPosicion(numPosicion)
    }

    const handleFolio = (e: React.MouseEvent<HTMLButtonElement>, n_folio: number) => {
        e.preventDefault()
        setFolio(n_folio)
        setTriggerInfo(true)
    }

    const truncateEmail = (email: string | undefined): string => {
        if (email) {
            const nombre = email.split("@")[0]
            return nombre
        }
        return ''
    }

    return (
        <>
            <FilterReg
                numRegistros = {numRegistros}
                search={search}
                handleSelectNum = {handleChange}
                handleSearch={handleBuscar}
            />
            {loading && <BarLoader className="mt-2" color="#059669" width={500} />}
            <div className="max-h-80 overflow-y-scroll">
                <table className="table w-full text-xs h-full -mt-1">
                    <thead className="thead sticky top-0">
                        <th>FOLIO</th>
                        {cau === "modificacion" ? (
                            <>
                                <th>EMPRESA</th>
                                <th>SECTOR</th>
                                <th>SERVICIO</th>
                            </>
                        ) : (
                            <>
                                <th>AREA</th>
                                <th>SERVICIO</th>
                                <th>EMPRESA</th>
                            </>
                        )}
                        <th>STATUS</th>
                        {cau === "cierre" ? (
                            <>
                                <th>APERTURA</th>
                                <th>HORA</th>
                                <th>CIERRE</th>
                                <th>HORA CIERRE</th>
                                <th>ATENDIDO</th>
                            </>
                        ) : (
                            <th>FECHA</th>
                        )}
                    </thead>
                    <tbody className="tbody">
                        {(dataTable !== undefined && dataTable !== null)
                        ?
                        dataTable.slice(posicion, fin).map((item) => {
                            return (
                                <tr key={item.n_folio} className="tr">
                                    <td className="min-w-content max-w-md">
                                        <button className="btn-link"
                                            onClick={(e) => handleFolio(e, item.n_folio)}
                                        >
                                            {item.n_folio}
                                        </button>
                                    </td>
                                    <td className="min-w-content max-w-md">{ item.n_area }</td>
                                    <td className="min-w-content max-w-md">{ item.n_servicio }</td>
                                    <td className="min-w-content max-w-md">{ item.n_empresa }</td>
                                    <td className="min-w-content max-w-md">{ item.n_status }</td>
                                    <td className="min-w-content max-w-md">{ item.d_fecha }</td>
                                    {cau === "cierre" && (
                                        <>
                                            <td className="min-w-content max-w-md">{ item.s_hora }</td>
                                            <td className="min-w-content max-w-md">{ item.d_fecha_cierre }</td>
                                            <td className="min-w-content max-w-md">{ item.s_hora_cierre }</td>
                                            <td className="min-w-content max-w-md">{ truncateEmail(item.email) }</td>
                                        </>
                                    )}
                                </tr>
                            )
                        })
                        :
                        <tr>
                            <td className='text-lg text-center' colSpan={6}>
                                No se encontró información
                            </td>
                        </tr>
                        }
                    </tbody>
                </table>
            </div>
            <hr className="border border-solid border-gray-300 -mt-4" />
            <Paginador
                posicion={posicion}
                numRegistros={numRegistros}
                totalRegistros={totalRegistros}
                goToPage={goToPage}
            />
        </>
    )
}