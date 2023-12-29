import React, { useState } from 'react'
import '../../table.css'
import {RegistrosLiqLatam,ILiqLatam} from '../../Models'
import { MoonLoader } from "react-spinners";
interface Table {
    onOpenDelete: (e: React.MouseEvent<HTMLElement>) => void
    onOpenEdit: (e:  React.MouseEvent<HTMLElement>) => void
    tableData: ILiqLatam
    loadingData : boolean
    totaPages : number
    n_cbo_pais : number
    textSearch:string
    getDataTable : (pais : number, numRegistros: number, position: number, txt_buscar : string) => Promise< void>
}

export const UmsLiquidezLatamTable:React.FC<Table> = ({onOpenDelete, onOpenEdit,
                                                        tableData,loadingData,totaPages,
                                                        n_cbo_pais,textSearch,getDataTable}) => {
    const [ position, setPosition ] = useState(0);
    const [ numRegistros, setNumRegistros ] = useState(12);
        
    const goToPage = (newPosition: number) => {
        setPosition(newPosition);
        getDataTable(n_cbo_pais,numRegistros,newPosition,textSearch);
    };


    if (loadingData || !tableData|| !tableData.body.registros) {
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
                            <th>INSTRUMENTO</th>
                            <th>RIC</th>
                            <th>TIPO</th>
                            <th>EDITAR</th>
                        </tr>
                    </thead>
                    <tbody>


                    {tableData.body.registros.map((registro :  RegistrosLiqLatam, index :  number) => (
                            <tr key={index}>
                            <td className='action'>
                                <button
                                            data-sisin ={registro.isin}
                                            data-ricformato ={registro.s_formato}
                                            data-instrumento={registro.s_instrumento}
                                            data-sric = {registro.s_ric}
                                            data-stipo = {registro.s_tipo}                                
                                            onClick={onOpenDelete}
                                >
                                    <i className="fa-regular fa-trash-can text-xl text-cyan-700"></i>
                                </button>
                            </td>
                                <td>{registro.isin}</td>
                                <td>{registro.s_instrumento}</td>
                                <td>{registro.s_ric}</td>
                                <td>{registro.s_tipo}</td>
                                <td className='action-edit'>
                                <tr>
                                    <td>
                                        <button
                                            data-sisin ={registro.isin}
                                            data-ricformato ={registro.s_formato}
                                            data-instrumento={registro.s_instrumento}
                                            data-sric = {registro.s_ric}
                                            data-stipo = {registro.s_tipo}
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
                    <button className={`hover:text-black ${totaPages === 1 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`} onClick={() => goToPage(position + numRegistros)} disabled={position + numRegistros >= tableData.body.total_registros}>
                        Siguiente
                    </button>
                </div> 
                
            </div>
        </>
    )
}