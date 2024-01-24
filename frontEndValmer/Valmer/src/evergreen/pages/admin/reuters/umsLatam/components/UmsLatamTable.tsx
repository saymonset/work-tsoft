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

    useEffect(() => {
        if (checkReload.current) {
            checkReload.current = false
            getDataTable(12, position, '', 9018).then()
        }
    }, [])

    const goToPage = (newPosition: number) => {
        setPosition(newPosition);
        getDataTable(12, newPosition, '', 9018);
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

    let color = "bg-white";
    let prevIsin = "";

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
                        {tableData.body.registros.map((registro: RegistrosUMSMexCat, index: number) => {
                            const instrumentosCount = tableData.body.registros.filter(reg => reg.S_ISIN === registro.S_ISIN).length;
                            const rowspan = index === 0 || registro.S_ISIN !== prevIsin ? instrumentosCount : 0;
                            color = prevIsin !== registro.S_ISIN ? (color === "bg-white" ? "bg-gray-300" : "bg-white") : color;
                            prevIsin = registro.S_ISIN;

                            return (
                                <tr key={index}>
                                    {rowspan > 0 && (
                                        <td rowSpan={rowspan} className={`${color} action`}>
                                            <button
                                                onClick={() => onOpenDelete(registro.S_ISIN)}
                                            >
                                                {tableData.body.registros.length > 1 && (
                                                    <i className="fa-regular fa-trash-can text-xl text-cyan-700"></i>
                                                )}
                                            </button>
                                        </td>
                                    )}
                                    {rowspan > 0 && <td rowSpan={rowspan} className={color}>{registro.S_ISIN}</td>}
                                    <td className={color}>{registro.S_RIC_FORMATO}</td>
                                    <td className={color}>{registro.S_PROVEDOR}</td>
                                    <td className={`${color} action-edit`}>
                                        <tr>
                                            <td>
                                                <button
                                                    data-sisin={registro.S_ISIN}
                                                    data-ricformato={registro.S_RIC_FORMATO}
                                                    data-proveedor={registro.S_PROVEDOR}
                                                    onClick={onOpenEdit}
                                                >
                                                    {tableData.body.registros.length > 1 && (
                                                        <i className="fa fa-file-pen text-xl text-cyan-700"></i>
                                                    )}
                                                </button>
                                            </td>
                                        </tr>

                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <div className="text-white bg-cyan-700 flex justify-center mt-10">
                    <button className={`hover:text-black ${position === 0 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`}
                        onClick={() => goToPage(position - 12)}
                        disabled={position === 0}>
                        Anterior
                    </button>
                    <span className="mx-2">
                        Página {position / 12 + 1} de {totaPages}
                    </span>
                    <button className={`hover:text-black ${totaPages === 1 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`}
                        onClick={() => goToPage(position + 12)} disabled={position + 12 >= tableData.body.total_registros}>
                        Siguiente
                    </button>
                </div>
            </div>
        </>
    )
}