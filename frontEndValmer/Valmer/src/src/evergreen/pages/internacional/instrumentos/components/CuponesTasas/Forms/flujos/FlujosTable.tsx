import { v4 as uuidv4 } from "uuid";
import { useDataCupones } from "../hooks";

export const FlujosTable = ({requeridos}: any) => {

	const { hFlujosVal, isRegisters, consultaData } = useDataCupones({requeridos});

	if (!consultaData?.body.h_flujos_val || consultaData?.body.h_flujos_val.length === 0) {
		return <p className='text-center'>No hay datos para mostrar.</p>
	}

	return (
		<table className='table'>
			<thead className='thead'>
			<th>No Flujo</th>
			<th>Fecha</th>
			<th>VN / Pagos</th>
			<th>amort_porc</th>
			</thead>
			<tbody className='tbody'>
			{isRegisters ? (
				hFlujosVal.map((data: any, index: number) => {
				return (
					<tr
						key={uuidv4()}
						className='tr'
					>
						<td>{index + 1}</td>
						<td>{data.property}</td>
						<td>{data.data.vn}</td>
						<td>{data.data.amort_porc}</td>
					</tr>
				);
			})) : (
				<tr>
					<td colSpan={8} className="text-center">No se encontraron registros</td>
				</tr>
			)}
			</tbody>
		</table>
	);
}