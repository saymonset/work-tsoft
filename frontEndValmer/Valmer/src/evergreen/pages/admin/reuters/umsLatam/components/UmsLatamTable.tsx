import React, { useEffect, useRef, useState } from 'react'
import { MoonLoader } from "react-spinners";
import { RegistrosUMSMexCat, IUmsMexCat } from '../../Models'


interface Table {
    onOpenDelete: (sisin: string) => void
    onOpenEdit: (e: React.MouseEvent<HTMLElement>) => void
    tableData: IUmsMexCat
    loadingData: boolean
    totaPages: number
    getDataTable: (numRegistros: number, position: number, txt_buscar: string, id_reu_formato: number) => Promise<void>
}


export const UmsLatamTable: React.FC<Table> = ({ onOpenDelete, onOpenEdit, tableData, loadingData, totaPages, getDataTable }) => {
    const [position, setPosition] = useState(0);
    const checkReload = useRef(true);
    const [numRegistros, setNumRegistros] = useState(12);

    useEffect(() => {
        if (checkReload.current) {
            checkReload.current = false
            getDataTable(numRegistros, position, '', 9018).then()
        }
    }, [])

    const goToPage = (newPosition: number) => {
        setPosition(newPosition);
        getDataTable(numRegistros, newPosition, '', 9018);
    };


    if (loadingData || !tableData || !tableData.body.registros) {
        return (
            <div className="flex justify-center items-center h-[256px]">
                {loadingData ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <div className="max-h-screen overflow-y-scroll mb-5">
                <table className='striped'>
                    <thead>
                        <tr>
                            <th>ELIMINAR</th>
                            <th>ISIN</th>
                            <th>RIC</th>
                            <th>PROVEDOR</th>
                            <th>EDITAR</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tableData.body.registros.map((registro: RegistrosUMSMexCat, index: number) => (
                            <tr key={index}>
                                <td className='action'>
                                    <button
                                        onClick={() => onOpenDelete(registro.S_ISIN)}
                                    >
                                        <i className="fa-regular fa-trash-can text-xl text-cyan-700"></i>
                                    </button>
                                </td>
                                <td>{registro.S_ISIN}</td>
                                <td>{registro.S_RIC_FORMATO}</td>
                                <td>{registro.S_PROVEDOR}</td>
                                <td className='action-edit'>
                                    <tr>
                                        <td>
                                            <button
                                                data-sisin={registro.S_ISIN}
                                                data-ricformato={registro.S_RIC_FORMATO}
                                                data-proveedor={registro.S_PROVEDOR}
                                                onClick={onOpenEdit}
                                            >
                                                <i className="fa fa-file-pen text-xl text-cyan-700"></i>
                                            </button>
                                        </td>
                                    </tr>

                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="text-white bg-cyan-700 flex justify-center mt-10">
                    <button className={`hover:text-black ${position === 0 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`}
                        onClick={() => goToPage(position - numRegistros)}
                        disabled={position === 0}>
                        Anterior
                    </button>
                    <span className="mx-2">
                        Página {position / numRegistros + 1} de {totaPages}
                    </span>
                    <button className={`hover:text-black ${totaPages === 1 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`}
                        onClick={() => goToPage(position + numRegistros)} disabled={position + numRegistros >= tableData.body.total_registros}>
                        Siguiente
                    </button>
                </div>
            </div>
        </>
    )
}