import { generateUUID } from "../../../../../utils"
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {ResponseDataTableHead} from "../../../../../model";

export const LipperFundFormHead = () => {

    const dataTableHead = useSelector((state: RootState<any, any, any>) =>
        state.dataTableHead) as unknown as ResponseDataTableHead

    return (
        <div className="flex flex-col text-sm mb-5 ml-5 w-full -mr-4">
            <table className='table'>
                <thead className='thead'>
                    <tr>
                        <th>Nombre</th>
                        <th>Fecha</th>
                        <th>Hora Mod</th>
                        <th>Lineas</th>
                        <th>Tamaño</th>
                    </tr>
                </thead>
                <tbody className='tbody'>
                    {Object.entries(dataTableHead.body).map(([filename, detailsOrStatus]) => (
                        <tr key={generateUUID()} className="tr">
                            <td>{filename}</td>
                            {typeof detailsOrStatus === 'string' ? (
                                <td className="text-red-600" colSpan={4}>{detailsOrStatus}</td>
                            ) : (
                                <>
                                    <td>{detailsOrStatus.fecha_mod}</td>
                                    <td>{detailsOrStatus.hora_mod}</td>
                                    <td>{detailsOrStatus.lineas}</td>
                                    <td>{detailsOrStatus.tamanio}</td>
                                </>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}