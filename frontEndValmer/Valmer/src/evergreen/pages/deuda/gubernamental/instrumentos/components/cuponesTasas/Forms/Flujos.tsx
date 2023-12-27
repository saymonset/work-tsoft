import React from 'react'
import {generateUUID} from "../../../../../../../../utils";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {FvDeudaGobInstrumentos} from "../../../../../../../../model";

export const Flujos = () => {

    const formValues = useSelector((state: RootState<any, any, any>) =>
        state.formValuesIns) as unknown as FvDeudaGobInstrumentos;

    if (!formValues.h_flujos || formValues.h_flujos.length === 0) {
        return <p className='text-center'>No hay datos para mostrar.</p>;
    }

	const sortedFlujos = [...formValues.h_flujos].sort((a, b) => {
		return Number(b.property) - Number(a.property);
	});

    return (
        <table className="w-full border-separate border-spacing-1 text-sm">
            <thead className="text-white bg-cyan-700">
            <tr>
                <th>No Cupon</th>
                <th>Fecha Inicio</th>
                <th>Fecha Fin</th>
                <th>Tasa</th>
            </tr>
            </thead>
            <tbody className="text-center">
            {sortedFlujos.map((data) => {
                return (
                    <tr key={generateUUID()} className="even:bg-gray-300 hover:bg-cyan-600 hover:text-white">
                        <td>{data.property}</td>
                        <td>{data.data.d_fecha_fin_cupon}</td>
                        <td>{data.data.d_fecha_ini_cupon}</td>
                        <td>{data.data.n_tasa_cupon}</td>
                    </tr>
                );
            })}
            </tbody>
        </table>
    );
}