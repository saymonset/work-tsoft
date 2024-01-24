import { DataTableHist } from "../../../../../../model"
import React from "react";

interface TableCauHistProps {
    data: DataTableHist
}

export const TableCauHist: React.FC<TableCauHistProps> = ({ data }) => {

    return (
        <table className="table w-full mb-4 text-xs">
            <thead className="thead">
            <tr>
                <th>Folio</th>
                <th>Area</th>
                <th>Servicio</th>
                <th>Empresa</th>
                <th>Status</th>
                <th>Fecha</th>
                <th>Cliente</th>
                <th>Teléfono</th>
                <th>Correo</th>
                <th>Descripción</th>
                <th>Archivo</th>
            </tr>
            </thead>
            <tbody className="tbody">
            {Object.keys(data).map((folio) => {

                const key = data[folio]
                return (
                    <tr key={folio} className="tr">
                        <td className="min-w-content max-w-md">{key.Folio}</td>
                        <td className="min-w-content max-w-md">{ key.Area }</td>
                            <td className="min-w-content max-w-md">{ key.Servicio }</td>
                            <td className="min-w-content max-w-md">{ key.Empresa }</td>
                            <td className="min-w-content max-w-md">{ key.Status }</td>
                            <td className="min-w-content max-w-md">{ key.Fecha }</td>
                            <td className="min-w-content max-w-md">{ key.Cliente }</td>
                            <td className="min-w-content max-w-md">{ key.Telefono }</td>
                            <td className="min-w-content max-w-md">{ key.Correo }</td>
                            <td className="min-w-content max-w-md">{ key.Descripcion }</td>
                            <td className="min-w-content max-w-md">
                                <a
                                    href={'http://www.valmer.com.mx/VAL/CAU/' + key.Archivo}
                                    className="text-cyan-700 hover:text-white"
                                >
                                    {key.Archivo}
                                </a>
                            </td>
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}