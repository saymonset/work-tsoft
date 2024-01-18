import React from 'react'
import { useCalifAntNew, useInfoFinanciera } from './hooks';
import { MoonLoader } from 'react-spinners';
import {getCatalogs} from "../../../../../../../utils";
import { useBigInput } from '../../../../tasas/components/forms/hooks/useBigInput';

export const InformacionFinanciera = () => {
    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
	const {
        consultaData,
		loadingSubmit,
        handleChange,
		handleSubmitFinanciera
	} = useInfoFinanciera()

	const {
		catalogCalif,
		loadingCalif,
		getCatalogsFin,
	} = useCalifAntNew()

	if (loadingCalif || !catalogCalif.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loadingCalif ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

	return (
		<form>
			<div className="px-3">
				<div className="form-title">
					<span>Información Instrumento</span>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="s_instrumento"
							id="s_instrumento"
							placeholder=""
							required
							value={consultaData?.body?.s_instrumento ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('s_instrumento')}
							onBlur={handleBlur}
							style={sendStyle('s_instrumento')}
						/>
						<label
							htmlFor="s_instrumento"
						>
							Información Instrumento
						</label>
					</div>
				</div>
				<div className='form-cols-4'>
					<div className="form-select col-start-2">
						<select defaultValue="default"
								id="n_calificadora_a"
								name="n_calificadora_a"
								value={consultaData?.body?.n_calificadora_a ?? ''}
								onChange={handleChange}
						>
							<option value="default">...</option>
							{getCatalogs(catalogCalif, 'CALIFICADORAS').map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_calificadora_a"
						>
							Calificadora Anterior
						</label>
					</div>
					<div className="form-select">
						<select defaultValue="default"
								id="n_calificacion_a"
								name='n_calificacion_a'
								value={consultaData?.body?.n_calificacion_a ?? ''}
								onChange={handleChange}
						>
							<option value="default">...</option>
							{getCatalogsFin(consultaData?.body?.n_calificadora_a).map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_calificacion_a"
						>
							Calificación Anterior
						</label>
					</div>
				</div>
				<div className='form-cols-4'>
					<div className="form-select col-start-2">
						<select defaultValue="default"
								id="n_calificadora_n"
								name='n_calificadora_n'
								value={consultaData?.body?.n_calificadora_n ?? ''}
								onChange={handleChange}
						>
							<option value="default">...</option>
							{getCatalogs(catalogCalif, 'CALIFICADORAS').map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_calificadora_n"
						>
							Calificadora Nueva
						</label>
					</div>
					<div className="form-select">
						<select defaultValue="default"
								id="n_calificacion_n"
								name='n_calificacion_n'
								value={consultaData?.body?.n_calificacion_n ?? ''}
								onChange={handleChange}
						>
							<option value="default">...</option>
							{getCatalogsFin(consultaData?.body?.n_calificadora_n).map((column) => (
								<option key={column[0]} value={column[0]}>
									{column[1]}
								</option>
							))}
						</select>
						<label
							htmlFor="n_calificacion_n"
						>
							Calificación Nueva
						</label>
					</div>
				</div>
				<div className='form-cols-4'>
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_notches"
							id="n_notches"
							placeholder=""
							required
							value={consultaData?.body?.n_notches ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_notches')}
							onBlur={handleBlur}
							style={sendStyle('n_notches')}
						/>
						<label
							htmlFor="n_notches"
						>
							Notches
						</label>
					</div>
				</div>
			
				<div className="form-title">
					<span>Información Financiera</span>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_titulos_iniciales"
							id="n_titulos_if"
							placeholder=""
							required
							value={consultaData?.body?.n_titulos_iniciales ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_titulos_if')}
							onBlur={handleBlur}
							style={sendStyle('n_titulos_if')}
						/>
						<label
							htmlFor="n_titulos_if"
						>
							Títulos
						</label>
					</div>
					<div className="form-input">
						<input
							type="text"
							name="n_valor_nominal_act"
							id="n_valor_nominal_act"
							placeholder=""
							required
							value={consultaData?.body?.n_valor_nominal_act ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_valor_nominal_act')}
							onBlur={handleBlur}
							style={sendStyle('n_valor_nominal_act')}
						/>
						<label
							htmlFor="n_valor_nominal_act"
						>
							VN Actual
						</label>
					</div>
				</div>
				<div className='form-cols-4'>
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_monto_circulacion"
							id="n_monto_circulacion"
							placeholder=""
							required
							value={consultaData?.body?.n_monto_circulacion ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_monto_circulacion')}
							onBlur={handleBlur}
							style={sendStyle('n_monto_circulacion')}
						/>
						<label
							htmlFor="n_monto_circulacion"
						>
							Monto en Circulación
						</label>
					</div>
				 
					<div className="form-input">
						<input
							type="text"
							name="n_factor"
							id="n_factor"
							placeholder=""
							required
							value={consultaData?.body?.n_factor ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_factor')}
							onBlur={handleBlur}
							style={sendStyle('n_factor')}
						/>
						<label
							htmlFor="n_factor"
						>
							Factor
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_efectivo"
							id="n_efectivo"
							placeholder=""
							required
							value={consultaData?.body?.n_efectivo ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_efectivo')}
							onBlur={handleBlur}
							style={sendStyle('n_efectivo')}
						/>
						<label
							htmlFor="n_efectivo"
						>
							Efectivo
						</label>
					</div>
				 
					<div className="form-input">
						<input
							type="text"
							name="n_der_cobro"
							id="n_der_cobro"
							placeholder=""
							required
							value={consultaData?.body?.n_der_cobro ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_der_cobro')}
							onBlur={handleBlur}
							style={sendStyle('n_der_cobro')}
						/>
						<label
							htmlFor="n_der_cobro"
						>
							Der Cobro
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_der_35_porcent"
							id="n_der_35_porcent"
							placeholder=""
							required
							value={consultaData?.body?.n_der_35_porcent ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_der_35_porcent')}
							onBlur={handleBlur}
							style={sendStyle('n_der_35_porcent')}
						/>
						<label
							htmlFor="n_der_35_porcent"
						>
							Der 35%
						</label>
					</div>
			 
					<div className="form-input">
						<input
							type="text"
							name="n_ac"
							id="n_ac"
							placeholder=""
							required
							value={consultaData?.body?.n_ac ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_ac')}
							onBlur={handleBlur}
							style={sendStyle('n_ac')}
						/>
						<label
							htmlFor="n_ac"
						>
							AC
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_activo_no_circ"
							id="n_activo_no_circ"
							placeholder=""
							required
							value={consultaData?.body?.n_activo_no_circ ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_activo_no_circ')}
							onBlur={handleBlur}
							style={sendStyle('n_activo_no_circ')}
						/>
						<label
							htmlFor="n_activo_no_circ"
						>
							Activo NO Circulante
						</label>
					</div>
				 
					<div className="form-input">
						<input
							type="text"
							name="n_garantias"
							id="n_garantias"
							placeholder=""
							required
							value={consultaData?.body?.n_garantias ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_garantias')}
							onBlur={handleBlur}
							style={sendStyle('n_garantias')}
						/>
						<label
							htmlFor="n_garantias"
						>
							Garantías
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_re_anc"
							id="n_re_anc"
							placeholder=""
							required
							value={consultaData?.body?.n_re_anc ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_re_anc')}
							onBlur={handleBlur}
							style={sendStyle('n_re_anc')}
						/>
						<label
							htmlFor="n_re_anc"
						>
							Rec 35% ACTIVO NO CIRCULANTE
						</label>
					</div>
				 
					<div className="form-input">
						<input
							type="text"
							name="n_re_garantias"
							id="n_re_garantias"
							placeholder=""
							required
							value={consultaData?.body?.n_re_garantias ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_re_garantias')}
							onBlur={handleBlur}
							style={sendStyle('n_re_garantias')}
						/>
						<label
							htmlFor="n_re_garantias"
						>
							Rec 35% GARANTÍAS
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_ac_rec"
							id="n_ac_rec"
							placeholder=""
							required
							value={consultaData?.body?.n_ac_rec ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_ac_rec')}
							onBlur={handleBlur}
							style={sendStyle('n_ac_rec')}
						/>
						<label
							htmlFor="n_ac_rec"
						>
							AC + Rec
						</label>
					</div>
				 
					<div className="form-input">
						<input
							type="text"
							name="n_precio_vector_dha"
							id="n_precio_vector_dha"
							placeholder=""
							required
							value={consultaData.body?.n_precio_vector_dha ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_precio_vector_dha')}
							onBlur={handleBlur}
							style={sendStyle('n_precio_vector_dha')}
						/>
						<label
							htmlFor="n_precio_vector_dha"
						>
							Precio Anterior
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_porc_vna_ant"
							id="n_porc_vna_ant"
							placeholder=""
							required
							value={consultaData?.body?.n_porc_vna_ant ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_porc_vna_ant')}
							onBlur={handleBlur}
							style={sendStyle('n_porc_vna_ant')}
						/>
						<label
							htmlFor="n_porc_vna_ant"
						>
							%VNA Ant
						</label>
					</div>
			 
					<div className="form-input">
						<input
							type="text"
							name="n_porc_vna"
							id="n_porc_vna"
							placeholder=""
							required
							value={consultaData?.body?.n_porc_vna ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_porc_vna')}
							onBlur={handleBlur}
							style={sendStyle('n_porc_vna')}
						/>
						<label
							htmlFor="n_porc_vna"
						>
							%VNA
						</label>
					</div>
				</div>
				<div className="form-cols-4">
					<div className="form-input col-start-2">
						<input
							type="text"
							name="n_precio_nuevo"
							id="n_precio_nuevo"
							placeholder=""
							required
							value={consultaData?.body?.n_precio_nuevo ?? ''}
							onChange={handleChange}
							onFocus={() => handleFocus('n_precio_nuevo')}
							onBlur={handleBlur}
							style={sendStyle('n_precio_nuevo')}
						/>
						<label
							htmlFor="n_precio_nuevo"
						>
							Precio Nuevo
						</label>
					</div>
					<div className="form-check">
						<input
							id="b_cambio_precio_mkt"
							name='b_cambio_precio_mkt'
							type="checkbox"
						/>
						<label
							htmlFor='b_cambio_precio_mkt'>
							Cambio Precio MKT
						</label>
					</div>
				</div>

				<div className="form-cols-4">
					<div className="col-start-2">
					<div className='line' />
					</div>
					<div className='line' />
				</div>
				<div className="form-cols-4">
					
				</div>
				
				<div className='flex justify-center px-3'>
					<button
						type='submit'
						className='btn'
							onClick={handleSubmitFinanciera}
					>
						{loadingSubmit ? (
							<i className="fa fa-spinner fa-spin"></i>
						) : (
							<span>Guardar</span>
						)}
					</button>
				</div>
			</div>
		</form>
	);
	}

		
