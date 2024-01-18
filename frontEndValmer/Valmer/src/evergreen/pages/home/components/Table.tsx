import React from 'react'
import { generateUUID } from '../../../../utils'
import {BDBody} from "../../../../model";

interface TableBD {
    dataBody: BDBody[],
}

export const Table:React.FC<TableBD> = ( {dataBody} ) => {
    return (
        <>
            <div className="flex flex-col h-full">
                <table className="table text-xs">
                    <thead className="thead">
                        <tr>
                            <th key={generateUUID()} scope="col">INSUMO</th>
                            <th key={generateUUID()} scope="col">HORARIOS</th>
                        </tr>
                    </thead>
                    <tbody className="tbody">
                        {dataBody.map((dataBody) => {
                            return(
                               <tr key={generateUUID()} className='tr'>
                                    <td>{dataBody.BD.replace(/_/g, ' ')}</td>
                                    <td>{dataBody.horario}</td>
                               </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>
        </>
    )
}