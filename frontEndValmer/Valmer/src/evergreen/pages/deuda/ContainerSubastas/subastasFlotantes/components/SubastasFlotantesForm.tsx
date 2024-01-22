import { MoonLoader } from "react-spinners";
import {generateUUID, getCatalogsSub} from "../../../../../../utils";
import { useSubastasForm} from "../../hooks";
import {SubFormProps} from "../../../../../../model";

export const SubastasFlotantesForm = (p: SubFormProps) => {

	const {
		catalog,
		loading,
		log,
		loadingLogBox,
		isShowLog,
		valueGet,
		valueSet
	} = useSubastasForm(p)

	const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div className="flex flex-col items-center w-3/4">
					<div className="form-title text-center w-full">LOG SUBASTAS</div>
                    <div
                        className="bg-gray-900 text-green-500 p-2 w-full overflow-auto max-h-[30rem]"
                        dangerouslySetInnerHTML={{ __html: log }}
                        style={{ minHeight: '15rem' }}
                    />
                </div>
            );

        }
        else
        {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

	const classNameInput = 
			`block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
			border-0 border-b-2 border-gray-300 appearance-none
			dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
			focus:outline-none focus:ring-0 focus:border-cyan-700 peer`;

	if(loading || !catalog.length)
	{
		return (
			<div className="flex justify-center items-center h-full">
				{loading ? (
					<MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={100}/>
				) : (
					<div>No hay información</div>
				)}
			</div>
		)
	}
	
	const catalogInstrumentoIs = getCatalogsSub(catalog, 'is');

	return (
		<div className="flex flex-col text-sm mt-5 mb-5 ml-5 mr-5 animate__animated animate__fadeIn">
			{isShowLog ? (
				<div className="flex mb-44 justify-center items-center h-full">
					{loadingLogBox ? (
						<MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
					) : renderLogContent()}
				</div>
			) : (
				<table className='table'>
					<thead className='thead'>
						<tr>
							<th>Fecha Subasta</th>
							<th>Instrumento</th>
							<th>Plazo</th>
							<th>Tasa</th>
							<th>Fecha Colocación</th>
							<th>Instrumento</th>
							<th>Monto</th>
						</tr>
					</thead>
					<tbody className='tbody'>
						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("lf1", "d_fecha_subasta")}
									onChange={(e) => valueSet("lf1",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>LF</td>
							<td>1</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("lf1", "n_tasa")}
									onChange={(e) => valueSet("lf1",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("lf1", "d_fecha_colocacion")}
									onChange={(e) => valueSet("lf1",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
									name="s_instr"
									value={valueGet("lf1", "s_instr")}
									onChange={(e) => valueSet("lf1",
										"s_instr", e.target.value)}
									required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'lf').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("lf1", "n_monto")}
									onChange={(e) => valueSet("lf1",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>

						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("lf2", "d_fecha_subasta")}
									onChange={(e) => valueSet("lf2",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>LF</td>
							<td>2</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("lf2", "n_tasa")}
									onChange={(e) => valueSet("lf2",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("lf2", "d_fecha_colocacion")}
									onChange={(e) => valueSet("lf2",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
										name="s_instr"
										value={valueGet("lf2", "s_instr")}
										onChange={(e) => valueSet("lf2",
											"s_instr", e.target.value)}
										required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'lf').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("lf2", "n_monto")}
									onChange={(e) => valueSet("lf2",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>

						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("lf3", "d_fecha_subasta")}
									onChange={(e) => valueSet("lf3",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>LF</td>
							<td>3</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("lf3", "n_tasa")}
									onChange={(e) => valueSet("lf3",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("lf3", "d_fecha_colocacion")}
									onChange={(e) => valueSet("lf3",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
										name="s_instr"
										value={valueGet("lf3", "s_instr")}
										onChange={(e) => valueSet("lf3",
											"s_instr", e.target.value)}
										required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'lf').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("lf3", "n_monto")}
									onChange={(e) => valueSet("lf3",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>

						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("lf5", "d_fecha_subasta")}
									onChange={(e) => valueSet("lf5",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>LF</td>
							<td>5</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("lf5", "n_tasa")}
									onChange={(e) => valueSet("lf5",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("lf5", "d_fecha_colocacion")}
									onChange={(e) => valueSet("lf5",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
									name="udibonos5"
									value={valueGet("lf5", "s_instr")}
									onChange={(e) => valueSet("lf5",
										"s_instr", e.target.value)}
									required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'lf').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("lf5", "n_monto")}
									onChange={(e) => valueSet("lf5",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>
						
						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("im3", "d_fecha_subasta")}
									onChange={(e) => valueSet("im3",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>IM</td>
							<td>3</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("im3", "n_tasa")}
									onChange={(e) => valueSet("im3",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("im3", "d_fecha_colocacion")}
									onChange={(e) => valueSet("im3",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
									name="s_instr"
									value={valueGet("im3", "s_instr")}
									onChange={(e) => valueSet("im3",
										"s_instr", e.target.value)}
									required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'im').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("im3", "n_monto")}
									onChange={(e) => valueSet("im3",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>

						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("iq5", "d_fecha_subasta")}
									onChange={(e) => valueSet("iq5",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>IQ</td>
							<td>5</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("iq5", "n_tasa")}
									onChange={(e) => valueSet("iq5",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("iq5", "d_fecha_colocacion")}
									onChange={(e) => valueSet("iq5",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
									name="s_instr"
									value={valueGet("iq5", "s_instr")}
									onChange={(e) => valueSet("iq5",
										"s_instr", e.target.value)}
									required
								>
									<option value="default">...</option>
									{getCatalogsSub(catalog, 'iq').map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("iq5", "n_monto")}
									onChange={(e) => valueSet("iq5",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>

						<tr className='tr'>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_subasta"
									value={valueGet("is7", "d_fecha_subasta")}
									onChange={(e) => valueSet("is7",
										"d_fecha_subasta", e.target.value)}
									required
								/>
							</td>
							<td>IS</td>
							<td>7</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_tasa"
									value={valueGet("is7", "n_tasa")}
									onChange={(e) => valueSet("is7",
										"n_tasa", e.target.value)}
									placeholder=""
									required
								/>
							</td>
							<td>
								<input
									type="date"
									className={classNameInput}
									name="d_fecha_colocacion"
									value={valueGet("is7", "d_fecha_colocacion")}
									onChange={(e) => valueSet("is7",
										"d_fecha_colocacion", e.target.value)}
									required
								/>
							</td>
							<td>
								<select className={classNameInput}
									name="s_instr"
									value={valueGet("is7", "s_instr")}
									onChange={(e) => valueSet("is7",
										"s_instr", e.target.value)}
									required
								>
									<option value="default">...</option>
									{catalogInstrumentoIs.slice(0, catalogInstrumentoIs.length - 1).map((column) => (
										<option key={generateUUID()} value={column[1]}>
											{column[1]}
										</option>
									))}
								</select>
							</td>
							<td>
								<input
									type="text"
									className={classNameInput}
									name="n_monto"
									value={valueGet("is7", "n_monto")}
									onChange={(e) => valueSet("is7",
										"n_monto", e.target.value)}
									placeholder=""
									required
								/>
							</td>
						</tr>
					</tbody>
				</table>

			)}
		</div>
	)
}