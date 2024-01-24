import {MoonLoader} from "react-spinners";
import {SelectFileCalif} from "../SelectFileCalif";
import React from "react";
import {CalifProps} from "../../../../../../model";
import {getCatalogs} from "../../../../../../utils";

export const CalificacionesContent = (c: CalifProps) => {

    const handleLimitChange = <T extends HTMLTextAreaElement>(
        e: React.ChangeEvent<T>,
        callback: (e: React.ChangeEvent<T>) => void
    ) => {
        const value = e.target.value;

        if (value.length <= 2000) {
            callback(e);
        }
    };

    if (c.loading || !c.catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {c.loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <form onSubmit={c.handleSubmit}>
            <div className="px-3">
                <div className="grid grid-cols-4 ">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Fitch</span>
                        <div className="form-select">
                            <select
                                id="n_calif_fitch_n"
                                name="n_calif_fitch_n"
                                value={c.consultaData?.body?.n_calif_fitch_n}
                                onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'FITCH_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_fitch_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_fitch_g"
                                    name="n_calif_fitch_g"
                                    value={c.consultaData?.body?.n_calif_fitch_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'FITCH_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_fitch_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_fitch"
                                name="d_fec_fitch"
                                value={c.consultaData?.body?.d_fec_fitch}
                                onChange={c.handleChange}
                            />
                            <label htmlFor="d_fec_fitch">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_fitch"
                                    name="s_evento_fitch"
                                    value={c.consultaData?.body?.s_evento_fitch}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_fitch">
                                Evento Fitch
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF fitch: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_fitch ?? ''}
                                         section={"fitch"}/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Standard & Poors</span>
                        <div className="form-select">
                            <select
                                id="n_calif_sp_n"
                                name="n_calif_sp_n"
                                value={c.consultaData?.body?.n_calif_sp_n}
                                onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'SP_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_sp_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_sp_g"
                                    name="n_calif_sp_g"
                                    value={c.consultaData?.body?.n_calif_sp_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'SP_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_sp_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_sp"
                                name="d_fec_sp"
                                value={c.consultaData?.body?.d_fec_sp}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_sp">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_sp"
                                    name="s_evento_sp"
                                    value={c.consultaData?.body?.s_evento_sp}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_sp">
                                Evento SP
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF sp: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_sp ?? ''}
                                         section={"sp"}/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Moodys</span>
                        <div className="form-select">
                            <select id="n_calif_moody_n"
                                    name="n_calif_moody_n"
                                    value={c.consultaData?.body?.n_calif_moody_n}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'MOODY_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_moody_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_moody_g"
                                    name="n_calif_moody_g"
                                    value={c.consultaData?.body?.n_calif_moody_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'MOODY_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_moody_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_moody"
                                name="d_fec_moody"
                                value={c.consultaData?.body?.d_fec_moody}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_moody">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_moody"
                                    name="s_evento_moody"
                                    value={c.consultaData?.body?.s_evento_moody}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_moody">
                                Evento Moody
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF moody: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_moody ?? ''}
                                         section={"moody"}/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Hr</span>
                        <div className="form-select">
                            <select id="n_calif_hr_n"
                                    name="n_calif_hr_n"
                                    value={c.consultaData?.body?.n_calif_hr_n}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'HR_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_hr_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_hr_g"
                                    name="n_calif_hr_g"
                                    value={c.consultaData?.body?.n_calif_hr_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'HR_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_hr_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_hr"
                                name="d_fec_hr"
                                value={c.consultaData?.body?.d_fec_hr}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_hr">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_hr"
                                    name="s_evento_hr"
                                    value={c.consultaData?.body?.s_evento_hr}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_hr">
                                Evento hr
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF hr: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_hr ?? ''}
                                         section={"hr"}/>
                    </div>
                </div>
                <div className="form-cols-1">
                    <div className="line"></div>
                </div>
                <div className="grid grid-cols-4 ">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Verum</span>
                        <div className="form-select">
                            <select id="n_calif_verum_n"
                                    name="n_calif_verum_n"
                                    value={c.consultaData?.body?.n_calif_verum_n}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'VERUM_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_verum_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_verum_g"
                                    name="n_calif_verum_g"
                                    value={c.consultaData?.body?.n_calif_verum_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'VERUM_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_verum_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_verum"
                                name="d_fec_verum"
                                value={c.consultaData?.body?.d_fec_verum}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_verum">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_verum"
                                    name="s_evento_verum"
                                    value={c.consultaData?.body?.s_evento_verum}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_verum">
                                Evento verum
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF verum: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_verum ?? ''}
                                         section={"verum"}/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Dbrs</span>
                        <div className="form-select">
                            <select id="n_calif_dbrs_n"
                                    name="n_calif_dbrs_n"
                                    value={c.consultaData?.body?.n_calif_dbrs_n}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'DBRS_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_dbrs_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_dbrs_g"
                                    name="n_calif_dbrs_g"
                                    value={c.consultaData?.body?.n_calif_dbrs_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'DBRS_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_dbrs_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_dbrs"
                                name="d_fec_dbrs"
                                value={c.consultaData?.body?.d_fec_dbrs}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_dbrs">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_dbrs"
                                    name="s_evento_dbrs"
                                    value={c.consultaData?.body?.s_evento_dbrs}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_dbrs">
                                Evento dbrs
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF dbrs: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_dbrs ?? ''}
                                         section={"dbrs"}/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Best</span>
                        <div className="form-select">
                            <select id="n_calif_best_n"
                                    name="n_calif_best_n"
                                    value={c.consultaData?.body?.n_calif_best_n}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'BEST_N').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_best_n">
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="form-select">
                            <select id="n_calif_best_g"
                                    name="n_calif_best_g"
                                    value={c.consultaData?.body?.n_calif_best_g}
                                    onChange={c.handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(c.catalog, 'BEST_G').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_calif_best_g">
                                Calificación Global
                            </label>
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                id="d_fec_best"
                                name="d_fec_best"
                                value={c.consultaData?.body?.d_fec_best}
                                onChange={c.handleChange}
                                placeholder=""
                            />
                            <label htmlFor="d_fec_best">
                                Fecha
                            </label>
                        </div>
                        <div className="form-text-area h-8">
								<textarea
                                    className="max-h-8"
                                    id="s_evento_best"
                                    name="s_evento_best"
                                    value={c.consultaData?.body?.s_evento_best}
                                    onChange={(e) => handleLimitChange(e, c.handleChange)}
                                ></textarea>
                            <label htmlFor="s_evento_best">
                                Evento best
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF best: </span>
                        </div>
                        <SelectFileCalif handleFileChange={c.handleFileChange}
                                         fileName={c.consultaData?.body?.s_pdf_best ?? ''}
                                         section={"best"}/>
                    </div>
                </div>
            </div>
            <div className="flex justify-end px-3">
                <button className="bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md mx-2"
                        type="submit">
                    {c.loadingSubmitCalif ? (
                        <i className="fa fa-spinner fa-spin"></i>
                    ) : (
                        <>
                            <i></i> Guardar
                        </>
                    )}
                </button>
            </div>
        </form>
    );
}