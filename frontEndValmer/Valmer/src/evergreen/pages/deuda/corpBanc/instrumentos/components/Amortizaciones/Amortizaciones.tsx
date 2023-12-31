import React from 'react'
import {useHandleDataCorp} from '../Instrumentos';

export const Amortizaciones: React.FC = () => {

	const {
		consultaData,
		handleChange,
		handleNumericChange,
	} = useHandleDataCorp()

	return (
		<form>
			<div className="px-3">
				<div className="form-title">
					<span>Amortizaciones</span>
				</div>
				<div className="form-cols-4">
					<div className="form-input">
						<input
							type="text"
							name="n_titulos_amortizar"
							id="n_titulos_amortizar"
							placeholder=""
							required
							value={consultaData?.body?.n_titulos_amortizar ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
						/>
						<label
							htmlFor="n_titulos_amortizar"
						>
							Títulos x Amortizar
						</label>
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_importe_amortizar"
							id="n_importe_amortizar"
							placeholder=""
							required
							value={consultaData?.body?.n_importe_amortizar ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
						/>
						<label
							htmlFor="n_importe_amortizar"
						>
							Importe x Amortizar
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-date">
						<input
							type="date"
							name="d_fecha_amort_ant"
							id="d_fecha_amort_ant"
							placeholder=""
							required
							value={consultaData?.body?.d_fecha_amort_ant ?? ''}
							onChange={handleChange}
						/>
						<label
							htmlFor="d_fecha_amort_ant"
						>
							Amortización x Acuerdo
						</label>
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_prima_amortizacion"
							id="n_prima_amortizacion"
							placeholder=""
							required
							value={consultaData?.body?.n_prima_amortizacion ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
						/>
						<label
							htmlFor="n_prima_amortizacion"
						>
							Prima x Amortizar
						</label>
					</div>
				</div>
			</div>
		</form>
	);
}