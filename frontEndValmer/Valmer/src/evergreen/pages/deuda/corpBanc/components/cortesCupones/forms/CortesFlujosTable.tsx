import React from "react";
import {ResponseCorteCupon} from "../../../../../../../model";
import {generateUUID} from "../../../../../../../utils";

export const CortesFlujosTable = ({data}: {data: ResponseCorteCupon}) => {

    const { body = [] } = data;

    return (
        <table className='table w-full'>
            <thead className='thead'>
            <tr>
                <th>Instrumento</th>
                <th>Fecha Inicio Cupón</th>
                <th>Fecha Fin Cupón</th>
                <th>Fecha Vto</th>
                <th>Días x Vencer</th>
                <th>Valor Nominal</th>
                <th>Ref Mercado</th>
            </tr>
            </thead>
            <tbody className='tbody'>
            {body.map((item) => {
                return (
                    <tr
                        key={generateUUID()}
                        className='tr'
                    >
                        <td>{item.property}</td>
                        <td>{item.data.FEC_INI_CUPON}</td>
                        <td>{item.data.FEC_FIN_CUPON}</td>
                        <td>{item.data.FEC_VTO}</td>
                        <td>{item.data.DIAS_X_VENC}</td>
                        <td>{item.data.VAL_NOM}</td>
                        <td>{item.data.REF_MERC}</td>
                    </tr>
                );
            })}
            </tbody>
        </table>
    );
}