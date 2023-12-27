import { FormInstProps } from "../../../../../model"
import { getCatalogs } from "../../../../../utils"
import { SelectFileCalif } from "../../../../../shared"

export const CalifInstForm = (props: FormInstProps) => {

    if (!props.catalogCalif?.body) {
        return (
            <div className="flex justify-center item-center h-full">
                No hay información
            </div>
        )
    }

    return (
		<form className="animate__animated animate__fadeIn">
			<div className="form">
				<div className="form-cols-4">
					<div className="form-col-1">
						<span className="form-title-card">Fitch</span>
						<div className="form-select">
							<select
								id="n_calif_fitch_n"
								name="n_calif_fitch_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_fitch_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.fitch, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_fitch_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_fitch_g"
								name="n_calif_fitch_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_fitch_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.fitch, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_fitch_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_fitch"
								id="d_fec_fitch"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_fitch || ""}
							/>
							<label
								htmlFor="d_fec_fitch"
							>
								Fecha Fitch
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_fitch"
									id="s_evento_fitch"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_fitch || ""}
								></textarea>
							<label htmlFor="s_evento_fitch"
							>
								Evento Fitch
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF fitch:
									{props.consultaData.s_pdf_fitch && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_fitch}`}>
											{" " + props.consultaData.s_pdf_fitch}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_fitch}
							section={"fitch"}
						/>
					</div>
					<div className="form-col-1">
						<span className="form-title-card">Standard & Poors</span>
						<div className="form-select">
							<select
								id="n_calif_sp_n"
								name="n_calif_sp_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_sp_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body["Standard & Poors"], "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_sp_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_sp_g"
								name="n_calif_sp_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_sp_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body["Standard & Poors"], "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_sp_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_sp"
								id="d_fec_sp"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_sp || ""}
							/>
							<label
								htmlFor="d_fec_sp"
							>
								Fecha sp
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_sp"
									id="s_evento_sp"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_sp || ""}
								></textarea>
							<label htmlFor="s_evento_sp"
							>
								Evento SP
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF sp:
									{props.consultaData.s_pdf_sp && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_sp}`}>
											{" " + props.consultaData.s_pdf_sp}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_sp}
							section="sp"
						/>
					</div>
					<div className="form-col-1">
						<span className="form-title-card">Moodys</span>
						<div className="form-select">
							<select
								id="n_calif_moody_n"
								name="n_calif_moody_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_moody_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Moodys, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_moody_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_moody_g"
								name="n_calif_moody_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_moody_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Moodys, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_moody_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_moody"
								id="d_fec_moody"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_moody || ""}
							/>
							<label
								htmlFor="d_fec_moody"
							>
								Fecha moody
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_moody"
									id="s_evento_moody"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_moody || ""}
								></textarea>
							<label htmlFor="s_evento_moody"
							>
								Evento Moody
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF moody:
									{props.consultaData.s_pdf_moody && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_moody}`}>
											{" " + props.consultaData.s_pdf_moody}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_moody}
							section="moody"
						/>
					</div>
					<div className="form-col-1">
						<span className="form-title-card">Hr</span>
						<div className="form-select">
							<select
								id="n_calif_hr_n"
								name="n_calif_hr_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_hr_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Hr, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_hr_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_hr_g"
								name="n_calif_hr_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_hr_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Hr, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_hr_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_hr"
								id="d_fec_hr"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_hr || ""}
							/>
							<label
								htmlFor="d_fec_hr"
							>
								Fecha hr
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_hr"
									id="s_evento_hr"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_hr || ""}
								></textarea>
							<label htmlFor="s_evento_hr"
							>
								Evento hr
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF hr:
									{props.consultaData.s_pdf_hr && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_hr}`}>
											{" " + props.consultaData.s_pdf_hr}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_hr}
							section="hr"
						/>
					</div>
				</div>
				<div className="line"></div>
				<div className="form-cols-4">
					<div className="form-col-1">
						<span className="form-title-card">Verum</span>
						<div className="form-select">
							<select
								id="n_calif_verum_n"
								name="n_calif_verum_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_verum_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Verum, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_verum_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_verum_g"
								name="n_calif_verum_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_verum_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.Verum, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_verum_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_verum"
								id="d_fec_verum"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_verum || ""}
							/>
							<label
								htmlFor="d_fec_verum"
							>
								Fecha verum
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_verum"
									id="s_evento_verum"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_verum || ""}
								></textarea>
							<label htmlFor="s_evento_verum"
							>
								Evento verum
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF verum:
									{props.consultaData.s_pdf_verum && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_verum}`}>
											{" " + props.consultaData.s_pdf_verum}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_verum}
							section="verum"
						/>
					</div>
					<div className="form-col-1">
						<span className="form-title-card">Dbrs</span>
						<div className="form-select">
							<select
								id="n_calif_dbrs_n"
								name="n_calif_dbrs_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_dbrs_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.DBRS, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_dbrs_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_dbrs_g"
								name="n_calif_dbrs_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_dbrs_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.DBRS, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_dbrs_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_dbrs"
								id="d_fec_dbrs"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_dbrs || ""}
							/>
							<label
								htmlFor="d_fec_dbrs"
							>
								Fecha dbrs
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_dbrs"
									id="s_evento_dbrs"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_dbrs || ""}
								></textarea>
							<label htmlFor="s_evento_dbrs"
							>
								Evento dbrs
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF dbrs:
									{props.consultaData.s_pdf_dbrs && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_dbrs}`}>
											{" " + props.consultaData.s_pdf_dbrs}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_dbrs}
							section="dbrs"
						/>
					</div>
					<div className="form-col-1">
						<span className="form-title-card">Best</span>
						<div className="form-select">
							<select
								id="n_calif_best_n"
								name="n_calif_best_n"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_best_n || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.BEST, "califN").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_best_n"
							>
								Calificación Nacional
							</label>
						</div>
						<div className="form-select">
							<select
								id="n_calif_best_g"
								name="n_calif_best_g"
								onChange={props.handleChange}
								value={props.consultaData.n_calif_best_g || ""}
							>
								<option value="default">...</option>
								{getCatalogs(props.catalogCalif.body.BEST, "califG").map((column) => (
									<option key={column[0]} value={column[0]}>
										{column[1]}
									</option>
								))}
							</select>
							<label
								htmlFor="n_calif_best_g"
							>
								Calificación Global
							</label>
						</div>
						<div className="form-date">
							<input
								type="date"
								name="d_fec_best"
								id="d_fec_best"
								onChange={props.handleChange}
								value={props.consultaData.d_fec_best || ""}
							/>
							<label
								htmlFor="d_fec_best"
							>
								Fecha best
							</label>
						</div>
						<div className="form-text-area">
								<textarea
									name="s_evento_best"
									id="s_evento_best"
									onChange={props.handleChange}
									value={props.consultaData.s_evento_best || ""}
								></textarea>
							<label htmlFor="s_evento_best"
							>
								Evento best
							</label>
						</div>
						<div className="form-label">
								<span>Archivo PDF best:
									{props.consultaData.s_pdf_best && (
										<a target="_blank"
										   href={`http://www.valmer.com.mx/VAL/CALIF/${props.consultaData.s_pdf_best}`}>
											{" " + props.consultaData.s_pdf_best}
										</a>
									)}
								</span>
						</div>
						<SelectFileCalif
							handleFileChange={props.handleChangeFile}
							fileName={props.consultaData.s_pdf_best}
							section="best"
						/>
					</div>
				</div>
			</div>
		</form>
	)
}