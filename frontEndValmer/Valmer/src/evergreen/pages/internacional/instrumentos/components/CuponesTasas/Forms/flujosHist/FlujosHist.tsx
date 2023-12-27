import { generateUUID } from '../../../../../../../../utils';
import { useDataCupones } from '../hooks';

export const FlujosHist = ({requeridos}: any) => {

	const { consultaData } = useDataCupones({requeridos})

	return (
		<div className="px-3">
			<div className="form-title">
				<span>Flujos Históricos</span>
			</div>
			<div className="flex flex-col mt-3">
				{(!consultaData?.body?.h_flujos_hist || consultaData?.body?.h_flujos_hist.length === 0) ?
					(
						<p className='text-center'>No hay datos para mostrar.</p>
					) : (
						<table className='table'>
							<thead className='thead'>
							<tr>
								<th>No Cupón</th>
								<th>Fecha Inicio</th>
								<th>Fecha Fin</th>
								<th>Tasa</th>
							</tr>
							</thead>
							<tbody className='tbody'>
								{consultaData?.body?.h_flujos_hist.map((data: any) => {
									return (
										<tr
											key={ generateUUID() }
											className='tr'
										>
											<td>{data.property}</td>
											<td>{data.data.d_fecha_ini_cupon}</td>
											<td>{data.data.d_fecha_fin_cupon}</td>
											<td>{data.data.n_tasa_cupon}</td>
										</tr>
									);
								})}
							</tbody>
						</table>
					)}
			</div>
		</div>
	);
}