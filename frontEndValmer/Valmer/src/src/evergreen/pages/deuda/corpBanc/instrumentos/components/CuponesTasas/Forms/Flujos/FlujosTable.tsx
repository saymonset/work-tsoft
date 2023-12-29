import {generateUUID} from "../../../../../../../../../utils";
import { useDataCupones } from '../hooks';

export const FlujosTable = () => {

	const {
		consultaData,
	} = useDataCupones()

	if (!consultaData?.body?.h_flujos_val || consultaData?.body?.h_flujos_val.length === 0) {
		return <p className='text-center'>No hay datos para mostrar.</p>;
	}

	return (
		<table className="table">
			<thead className="thead">
				<tr>
					<th>No Flujo</th>
					<th>Fecha</th>
					<th>VN / Pagos</th>
					<th>amort_porc</th>
				</tr>
			</thead>
			<tbody className="tbody">
				{consultaData?.body?.h_flujos_val.map((data: any, index) => {
					return (
						<tr key={generateUUID()} className="tr">
							<td>{index + 1}</td>
							<td>{data.property}</td>
							<td>{data.data.vn}</td>
							<td>{data.data.amort_porc}</td>
						</tr>
					);
				})}
			</tbody>
		</table>
	);
}