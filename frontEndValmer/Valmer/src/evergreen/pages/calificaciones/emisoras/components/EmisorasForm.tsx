import React from "react";
import {getCatalogs} from "../../../../../utils";
import {MoonLoader} from "react-spinners";
import {useEmisorasForm} from "./hooks";
import {SelectFileCalifEmi} from "./components";

export const EmisorasForm = () => {

    const {formData, loading, catalogEmi, handleChange} = useEmisorasForm()

    if (loading || !catalogEmi?.body) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <form>
            <div className="px-3">
                <div className="form-cols-4">
                    <div>
                        <div className="form-title">
                            <span>Fitch</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_fitch_lp_n"
                                    name="s_fitch_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_fitch_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_fitch_cp_n"
                                    name="s_fitch_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_fitch_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_fitch_lp_g"
                                    name="s_fitch_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_fitch_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_fitch_cp_g"
                                    name="s_fitch_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_fitch_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                placeholder=""
                                id="d_fec_fitch"
                                name="d_fec_fitch"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_fitch">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
                            <textarea
                                id="s_evento_fitch"
                                name="s_evento_fitch"
                                onChange={handleChange}
                            >
                            </textarea>
                            <label htmlFor="s_evento_fitch">
                                Evento Fitch
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF fitch: {formData?.s_pdf_fitch}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_fitch" fieldB64="fitch_b64"/>
                    </div>
                    <div>
                        <div className="form-title">
                            <span>Standard & Poors</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_sp_lp_n"
                                    name="s_sp_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body["Standard & Poors"], 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_sp_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_sp_cp_n"
                                    name="s_sp_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body["Standard & Poors"], 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_sp_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_sp_lp_g"
                                    name="s_sp_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body["Standard & Poors"], 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_sp_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_sp_cp_g"
                                    name="s_sp_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body["Standard & Poors"], 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_sp_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_sp"
                                name="d_fec_sp"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_sp">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
                            <textarea
                                id="s_evento_sp"
                                name="s_evento_sp"
                                onChange={handleChange}
                            ></textarea>
                            <label htmlFor="s_evento_sp">
                                Evento SP
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF sp: {formData?.s_pdf_sp}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_sp" fieldB64="sp_b64"/>
                    </div>
                    <div>
                        <div className="form-title">
                            <span>Moodys</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_moody_lp_n"
                                    name="s_moody_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Moodys, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_moody_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_moody_cp_n"
                                    name="s_moody_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Moodys, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_moody_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_moody_lp_g"
                                    name="s_moody_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Moodys, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_moody_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_moody_cp_g"
                                    name="s_moody_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Moodys, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_moody_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_moody"
                                name="d_fec_moody"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_moody">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
                            <textarea
                                id="s_evento_moody"
                                name="s_evento_moody"
                                onChange={handleChange}
                            ></textarea>
                            <label htmlFor="s_evento_moody">
                                Evento Moody
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF moody: {formData?.s_pdf_moody}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_moody" fieldB64="moody_b64"/>
                    </div>
                    <div>
                        <div className="form-title">
                            <span className="bg-cyan-700 text-slate-50 px-1">Hr</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_hr_lp_n"
                                    name="s_hr_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Hr, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_hr_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_hr_cp_n"
                                    name="s_hr_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Hr, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_hr_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_hr_lp_g"
                                    name="s_hr_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_hr_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_hr_cp_g"
                                    name="s_hr_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.fitch, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_hr_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_hr"
                                name="d_fec_hr"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_hr">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
								<textarea
                                    id="s_evento_hr"
                                    name="s_evento_hr"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_hr">
                                Evento hr
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF hr: {formData?.s_pdf_hr}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_hr" fieldB64="hr_b64"/>
                    </div>
                </div>
                <div className="grid grid-cols-1 gap-4">
                    <div className="line-y-1"/>
                </div>
                <div className="form-cols-4">
                    <div>
                        <div className="form-title">
                            <span>Verum</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_verum_lp_n"
                                    name="s_verum_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Verum, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_verum_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_verum_cp_n"
                                    name="s_verum_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Verum, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_verum_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_verum_lp_g"
                                    name="s_verum_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Verum, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_verum_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_verum_cp_g"
                                    name="s_verum_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.Verum, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_verum_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_verum"
                                name="d_fec_verum"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_verum">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
								<textarea
                                    id="s_evento_verum"
                                    name="s_evento_verum"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_verum">
                                Evento verum
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF verum: {formData?.s_pdf_verum}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_verum" fieldB64="verum_b64"/>
                    </div>
                    <div>
                        <div className="form-title">
                            <span className="bg-cyan-700 text-slate-50 px-1">Dbrs</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_dbrs_lp_n"
                                    name="s_dbrs_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.DBRS, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_dbrs_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_dbrs_cp_n"
                                    name="s_dbrs_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.DBRS, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_dbrs_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_dbrs_lp_g"
                                    name="s_dbrs_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.DBRS, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_dbrs_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_dbrs_cp_g"
                                    name="s_dbrs_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.DBRS, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_dbrs_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_dbrs"
                                name="d_fec_dbrs"
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fec_dbrs">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
								<textarea
                                    id="s_evento_dbrs"
                                    name="s_evento_dbrs"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_dbrs">
                                Evento dbrs
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF dbrs: {formData?.s_pdf_dbrs}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_dbrs" fieldB64="dbrs_b64"/>
                    </div>
                    <div>
                        <div className="form-title">
                            <span className="bg-cyan-700 text-slate-50 px-1">Best</span>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_best_lp_n"
                                    name="s_best_lp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.BEST, 'califLargoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_best_lp_n">
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_best_cp_n"
                                    name="s_best_cp_n"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.BEST, 'califCortoPlazoN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_best_cp_n">
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_best_lp_g"
                                    name="s_best_lp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.BEST, 'califLargoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_best_lp_g">
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="form-select">
                            <select defaultValue="default"
                                    id="s_best_cp_g"
                                    name="s_best_cp_g"
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogEmi.body.BEST, 'califCortoPlazoG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="s_best_cp_g">
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_best"
                                name="d_fec_best"
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fec_best">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area">
								<textarea
                                    id="s_evento_best"
                                    name="s_evento_best"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="eventoBest">
                                Evento best
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF best: {formData?.s_pdf_best}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_best" fieldB64="best_b64"/>
                    </div>
                </div>
            </div>
        </form>
    )
}