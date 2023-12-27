import React from 'react'
import { useDataCupones } from './hooks';
import { ButtonContent } from '../../../../../../../../shared';

export const Cupones: React.FC = () => {

	const {
		editableRow, 
		isAct, 
		dataCup,
		loadingActCup,
		getDateInfo, 
		enableEditing, 
		disableEditing,
		handleChangeCup,
		handleUpdatedCupon
	} = useDataCupones()

	return (
		<div className="px-3">
			<div className="form-title">
				<span>Cupones</span>
			</div>
			<div className="flex flex-col mt-3">
				{(!dataCup || dataCup.length === 0) ? (
					<p className='text-center'>No hay datos para mostrar.</p>
				) : (
					<table className="table">
						<thead className="thead">
						<tr>
							<th>No Cupón</th>
							<th>Fecha Inicio</th>
							<th>Fecha Fin</th>
							<th>Tasa</th>
							{isAct && (
								<th className='w-1'>
									<button className='btn' onClick={handleUpdatedCupon}>
										<ButtonContent name='Actualizar' loading={loadingActCup} />
									</button>
								</th>
							)}
						</tr>
						</thead>
						<tbody className="tbody">
						{dataCup && [...dataCup]
							.sort((a, b) => parseInt(b.property) - parseInt(a.property))
							.map((data: any) => (
								<tr key={data.property} 
									className={`hover:cursor-pointer ${editableRow[data.property] ? "tr-editable" : "tr"}`} 
									onDoubleClick={() => enableEditing(data.property)}
								>
									<td>{data.property}</td>
									{editableRow[data.property] ? (
										<>
											<td>
												<input type="date"
													name="d_fecha_ini_cupon" 
													id="d_fecha_ini_cupon"
													value={data.data.d_fecha_ini_cupon}
													onChange={(e) => handleChangeCup(e, data.property)}
												/>
											</td>
											<td>
												<input type="date"
													name="d_fecha_fin_cupon" 
													id="d_fecha_fin_cupon"
													value={data.data.d_fecha_fin_cupon}
													onChange={(e) => handleChangeCup(e, data.property)}
												/>
											</td>
											<td>
												<input type="text" 
													className='w-10 text-center text-xs'
													name="n_tasa_cupon" 
													id="n_tasa_cupon"
													value={data.data.n_tasa_cupon}
													onChange={(e) => handleChangeCup(e, data.property)}
												/>
											</td>
											<td>
												<button 
													className='text-green-600'
													type='button'
													onClick={() => disableEditing(data.property)}
												>
													<span className='fa fa-reply'/>
												</button>
											</td>
										</>
									) : (
										<>
											<td>{getDateInfo(data.data.d_fecha_ini_cupon)}</td>
											<td>{getDateInfo(data.data.d_fecha_fin_cupon)}</td>
											<td>{data.data.n_tasa_cupon}</td>
										</>
									)}
								</tr>
							))}
						</tbody>
					</table>
				)}
			</div>
		</div>
	);
}