import React from 'react'
import { generateUUID } from '../../../../../../../../utils';
import { useDataCupones } from './hooks';

export const Intercupon: React.FC = () => {

	const {consultaData} = useDataCupones()

	return (
		<table className="table w-full">
			<thead className="thead">
				<tr>
					<th>Fecha Intercupón</th>
					<th>Tasa Intercupón</th>
					<th>Valor Nominal Intercupón</th>
				</tr>
			</thead>
			{(!consultaData?.body?.h_flujos_cupon ||
			  consultaData?.body?.h_flujos_cupon.length !== 0) && (
				<tbody className="tbody">
					{consultaData?.body?.h_flujos_cupon.toString() !== "{}" &&
					 consultaData?.body?.h_flujos_cupon.map((data: any) => {
						return (
							<tr key={generateUUID()} className="tr">
								<td>{data.data.d_fecha_intercupon}</td>
								<td>{data.data.n_tasa_intercupon}</td>
								<td>{data.data.n_vn_intercupon}</td>
							</tr>
						);
					})}
				</tbody>
			)}
		</table>
	);
}