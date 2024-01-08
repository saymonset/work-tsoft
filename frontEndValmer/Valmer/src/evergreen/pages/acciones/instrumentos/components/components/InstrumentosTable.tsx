import {generateUUID} from "../../../../../../utils";
import React from "react";
import {DividendosData} from "../../../../../../model";

export const InstrumentosTable = ({ data }: {data: DividendosData[]}) => {
    return (
        <>
            <div className="form-title">DIVIDENDOS</div>
            <div className="flex flex-col mt-3">
                <table className="table text-xs">
                    <thead className="thead">
                    <tr>
                        <th>FECHA</th>
                        <th>INSTRUMENTO</th>
                        <th>FECHA REESTRUCTURA</th>
                        <th>OPERACION</th>
                        <th>VALOR</th>
                    </tr>
                    </thead>
                    <tbody className="tbody">
                    { data.length > 0 &&
                        data.map((data) => {
                            return(
                                <tr key={generateUUID()} className="tr">
                                    <td>{ data.Fecha }</td>
                                    <td>{ data.Instrumento }</td>
                                    <td>{ data["Fecha Reestructura"] }</td>
                                    <td>{ data.Operacion }</td>
                                    <td>{ data.Valor }</td>
                                </tr>
                            )
                        })
                    }
                    </tbody>
                </table>
            </div>
        </>
    )
}