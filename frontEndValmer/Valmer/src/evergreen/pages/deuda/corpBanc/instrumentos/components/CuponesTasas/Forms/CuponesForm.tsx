import React from 'react'
import { useDataCupones } from './hooks';
import {getCatalogs, useBigInput} from '../../../../../../../../utils';

export const CuponesForm = ({requeridos}: any) => {

	
    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
	const {
		isFrecuenciaBimestral,
		fieldRequiredCorp,
		catalog,
		consultaData,
		handleChange,
		handleCheckboxChange,
		handleNumericChange,
		checkboxValueEval,
	} = useDataCupones()

	const isRedondeo = checkboxValueEval("b_redondeo", consultaData)
	const isFechaCupon = checkboxValueEval("b_ajuste_fc", consultaData)

	return (
		<form>
			<div className="px-3">
				<div className="form-title">
					<span>Tasas</span>
				</div>
				<div className="form-cols-4">
					<div className="form-select">
						<select
							defaultValue="default"
							id="n_frecuencia_cupon"
							name='n_frecuencia_cupon'
							value={consultaData?.body?.n_frecuencia_cupon ?? ''}
							onChange={handleChange}
							ref={requeridos.n_frecuencia_cupon}
						>
							<option value="default">...</option>
							{getCatalogs(catalog, 'FRECUENCIA_CUPON').map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_frecuencia_cupon"
						>
							Forma de Pago
						</label>
						{fieldRequiredCorp.n_frecuencia_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Forma de Pago</span>
						)}
					</div>

					{isFrecuenciaBimestral && (
						<div className="form-input">
							<input
								type="number"
								name="n_dia_corte_cupon"
								placeholder=""
								required
								value={consultaData?.body?.n_dia_corte_cupon ?? ''}
								onChange={handleChange}
								onFocus={() => handleFocus('n_dia_corte_cupon')}
								onBlur={handleBlur}
								style={sendStyle('n_dia_corte_cupon')}
							/>
							<label
								htmlFor="n_dia_corte_cupon"
							>
								Día Corte Cup
							</label>
						</div>
					)}


					<div className="form-select">
						<select defaultValue="default"
								id="n_tipo_promedio"
								name='n_tipo_promedio'
								value={consultaData?.body?.n_tipo_promedio ?? ''}
								onChange={handleChange}
								ref={requeridos.n_tipo_promedio}
						>
							<option value="default">...</option>
							{getCatalogs(catalog, 'TIPO_PROMEDIO').map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_tipo_promedio"
						>
							Tipo Promedio
						</label>
						{fieldRequiredCorp.n_tipo_promedio && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tipo Promedio</span>
						)}
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-select">
						<select defaultValue="default"
								id="n_calc_dias"
								name='n_calc_dias'
								value={consultaData?.body?.n_calc_dias ?? ''}
								onChange={handleChange}
								ref={requeridos.n_calc_dias}
						>
							<option value="default">...</option>
							{getCatalogs(catalog, 'CALCULO_DIAS').map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_calc_dias"
						>
							Calc Días
						</label>
						{fieldRequiredCorp.n_calc_dias && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Calc Días</span>
						)}
					</div>
					<div className="form-input">
						<input
							type="number"
							name="n_dias_deter_tasa"
							id="n_dias_deter_tasa"
							placeholder=""
							required
							value={consultaData?.body?.n_dias_deter_tasa ?? ''}
							onChange={handleChange}
							ref={requeridos.n_dias_deter_tasa}
							onFocus={() => handleFocus('n_dias_deter_tasa')}
							onBlur={handleBlur}
							style={sendStyle('n_dias_deter_tasa')}
						/>
						<label
							htmlFor="n_dias_deter_tasa"
						>
							Días Determinación Tasa
						</label>
						{fieldRequiredCorp.n_dias_deter_tasa && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Días Determinación Tasa</span>
						)}
					</div>
					<div className="form-check">
						<input
							type="checkbox"
							id='b_redondeo'
							name='b_redondeo'
							checked={checkboxValueEval('b_redondeo', consultaData)}
							onChange={(e) => handleCheckboxChange(e)}
						/>
						<label
							htmlFor='b_redondeo'
						>
							Redondeo
						</label>
					</div>
					{isRedondeo && (
						<div className="form-input">
							<input
								type="number"
								name="n_redondeo_tasa_cupon"
								id="n_redondeo_tasa_cupon"
								placeholder=""
								required
								value={consultaData?.body?.n_redondeo_tasa_cupon ?? ''}
								onChange={(e) => handleChange(e)}
								onFocus={() => handleFocus('n_redondeo_tasa_cupon')}
								onBlur={handleBlur}
								style={sendStyle('n_redondeo_tasa_cupon')}
							/>
							<label
								htmlFor="n_redondeo_tasa_cupon"
							>
								Redondeo Tasa Cupon
							</label>
						</div>
					)}
				</div>
				<div className="form-cols-4">
					<div className="form-input">
						<input
							type="text"
							name="n_tasa_cupon"
							id="n_tasa_cupon"
							placeholder=""
							required
							value={consultaData?.body?.n_tasa_cupon ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
							ref={requeridos.n_tasa_cupon}
							onFocus={() => handleFocus('n_tasa_cupon')}
							onBlur={handleBlur}
							style={sendStyle('n_tasa_cupon')}
						/>
						<label
							htmlFor="n_tasa_cupon"
						>
							Tasa Cupon MD
						</label>
						{fieldRequiredCorp.n_tasa_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tasa Cupon MD</span>
						)}
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_tasa_cupon_24h"
							id="n_tasa_cupon_24h"
							placeholder=""
							required
							value={consultaData?.body?.n_tasa_cupon_24h ?? ''}
							onChange={(e) => handleChange(e)}
							ref={requeridos.n_tasa_cupon_24h}
							onFocus={() => handleFocus('n_tasa_cupon_24h')}
							onBlur={handleBlur}
							style={sendStyle('n_tasa_cupon_24h')}
						/>
						<label
							htmlFor="n_tasa_cupon_24h"
						>
							Tasa Cupon 24
						</label>
						{fieldRequiredCorp.n_tasa_cupon_24h && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Tasa Cupon 24</span>
						)}
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_valor_nominal_act"
							id="n_valor_nominal_act"
							placeholder=""
							required
							value={consultaData?.body?.n_valor_nominal_act ?? ''}
							onChange={(e) => handleChange(e)}
							ref={requeridos.n_valor_nominal_act}
							onFocus={() => handleFocus('n_valor_nominal_act')}
							onBlur={handleBlur}
							style={sendStyle('n_valor_nominal_act')}
						/>
						<label
							htmlFor="n_valor_nominal_act"
						>
							Valor Nominal Actualizado
						</label>
						{fieldRequiredCorp.n_valor_nominal_act && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Valor Nominal Actualizado</span>
						)}
					</div>
				</div>
			</div>
			<div className="px-3">
				<div className="form-title">
					<span>Cupones</span>
				</div>
				<div className="form-cols-4">
					<div className="form-date form-date-my">
						<input
							type="date"
							name="d_fecha_ini_cupon"
							id="d_fecha_ini_cupon"
							placeholder=""
							required
							value={consultaData?.body?.d_fecha_ini_cupon ?? ''}
							onChange={(e) => handleChange(e)}
							ref={requeridos.d_fecha_ini_cupon}
						/>
						<label
							htmlFor="d_fecha_ini_cupon"
						>
							Fecha Inicio Cupón
						</label>
						{fieldRequiredCorp.d_fecha_ini_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Inicio Cupón</span>
						)}
					</div>
					<div className="form-date form-date-my">
						<input
							type="date"
							name="d_fecha_fin_cupon"
							id="d_fecha_fin_cupon"
							placeholder=""
							required
							value={consultaData?.body?.d_fecha_fin_cupon ?? ''}
							onChange={(e) => handleChange(e)}
							ref={requeridos.d_fecha_fin_cupon}
						/>
						<label
							htmlFor="d_fecha_fin_cupon"
						>
							Fecha Fin Cupón
						</label>
						{fieldRequiredCorp.d_fecha_fin_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Fin Cupón</span>
						)}
					</div>
					<div className="form-check">
						<input
							type="checkbox"
							id='b_ajuste_fc'
							name='b_ajuste_fc'
							checked={checkboxValueEval('b_ajuste_fc', consultaData)}
							onChange={(e) => handleCheckboxChange(e)}
						/>
						<label
							htmlFor='b_ajuste_fc'
						>
							Ajuste Fechas Cupón
						</label>
					</div>
					{isFechaCupon && (
						<div className="form-select">
							<select defaultValue="default"
									id='n_calc_fc'
									name='n_calc_fc'
									value={consultaData?.body?.n_calc_fc ?? ''}
									onChange={(e) => handleChange(e)}
							>
								<option value="default">...</option>
								{getCatalogs(catalog, 'FECHAS_CUPON_DIAS').map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calc_fc"
							>
								Calculo Días Cupón
							</label>
						</div>
					)}
				</div>
				<div className="form-cols-4">
					<div className="form-date">
						<input
							type="date"
							name="d_fecha_primer_cupon"
							id="d_fecha_primer_cupon"
							placeholder=""
							required
							value={consultaData?.body?.d_fecha_primer_cupon ?? ''}
							onChange={(e) => handleChange(e)}
						/>
						<label
							htmlFor="d_fecha_primer_cupon"
						>
							Fecha Primer Cupon
						</label>
						{fieldRequiredCorp.d_fecha_primer_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Primer Cupon</span>
						)}
					</div>
					<div className="form-date">
						<input
							type="date"
							name="d_fecha_subasta"
							id="d_fecha_subasta"
							value={consultaData?.body?.d_fecha_subasta ?? ''}
							onChange={(e) => handleChange(e)}
							ref={requeridos.d_fecha_subasta}
							required
						/>
						<label htmlFor="d_fecha_subasta">
							Fecha Subasta Cupón
						</label>
						{fieldRequiredCorp.d_fecha_subasta && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Fecha Subasta Cupón</span>
						)}
					</div>
					<div className="form-check">
						<input
							type="checkbox"
							name='b_intereses_hibrido'
							id='b_intereses_hibrido'
							checked={checkboxValueEval('b_intereses_hibrido', consultaData)}
							onChange={(e) => handleCheckboxChange(e)}
						/>
						<label
							htmlFor='b_intereses_hibrido'
						>
							Intereses Híbridos
						</label>
					</div>
					<div className="form-check">
						<input
							type="checkbox"
							name='b_movimiento_tasa'
							id='b_movimiento_tasa'
							checked={checkboxValueEval('b_movimiento_tasa', consultaData)}
							onChange={(e) => handleCheckboxChange(e)}
						/>
						<label
							htmlFor='b_movimiento_tasa'
						>
							Mov. Tasa
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input">
						<input
							type="text"
							name="n_num_cupones"
							id="n_num_cupones"
							placeholder=""
							required
							value={consultaData?.body?.n_num_cupones ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
							ref={requeridos.n_num_cupones}
							onFocus={() => handleFocus('n_num_cupones')}
							onBlur={handleBlur}
							style={sendStyle('n_num_cupones')}
						/>
						<label
							htmlFor="n_num_cupones"
						>
							Cupones
						</label>
						{fieldRequiredCorp.n_num_cupones && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Cupones</span>
						)}
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_periodo_cupon"
							id="n_periodo_cupon"
							placeholder=""
							required
							value={consultaData?.body?.n_periodo_cupon ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
							ref={requeridos.n_periodo_cupon}
							onFocus={() => handleFocus('n_periodo_cupon')}
							onBlur={handleBlur}
							style={sendStyle('n_periodo_cupon')}
						/>
						<label
							htmlFor="n_periodo_cupon"
						>
							Periodo Cupón
						</label>
						{fieldRequiredCorp.n_periodo_cupon && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Periodo Cupón</span>
						)}
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_periodo_cupon_v"
							id="n_periodo_cupon_v"
							placeholder=""
							required
							value={consultaData?.body?.n_periodo_cupon_v ?? ''}
							onChange={(e) => handleNumericChange(e, handleChange)}
							ref={requeridos.n_periodo_cupon_v}
							onFocus={() => handleFocus('n_periodo_cupon_v')}
							onBlur={handleBlur}
							style={sendStyle('n_periodo_cupon_v')}
						/>
						<label
							htmlFor="n_periodo_cupon_v"
						>
							Periodo Cupón Vigente
						</label>
						{fieldRequiredCorp.n_periodo_cupon_v && (
							<span className="fontError animate__animated animate__fadeIn">
                                    Campo requerido Periodo Cupón Vigente</span>
						)}
					</div>
				</div>
			</div>
		</form>
	);
}