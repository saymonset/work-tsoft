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
                <div className="grid grid-cols-4 gap-4">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Fitch</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califNacFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califGlobFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_fitch_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califGlobFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_fitch"
                                onChange={handleChange}
                                required
                            />
                            <label
                                htmlFor="fechaFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_fitch"
                                    onChange={handleChange}
                                >
                                </textarea>
                            <label htmlFor="eventoFitch"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]">
                                Evento Fitch
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF fitch: {formData?.s_pdf_fitch}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_fitch" fieldB64="fitch_b64"/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Standard & Poors</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califNacFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califGlobFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label
                                htmlFor="califGlobFitch"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_sp_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_sp"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_sp" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_sp"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_sp"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
                                Evento SP
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF sp: {formData?.s_pdf_sp}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_sp" fieldB64="sp_b64"/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Moodys</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_moody_lp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_moody_cp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_moody_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_moody_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_moody"
                                onChange={handleChange}
                                required
                            />
                            <label
                                htmlFor="fechaMoody"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_moody"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="eventoMoody"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
                                Evento Moody
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF moody: {formData?.s_pdf_moody}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_moody" fieldB64="moody_b64"/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Hr</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_hr_lp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_hr_cp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_hr_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_hr_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                     border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                     dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_hr"
                                onChange={handleChange}
                                required
                            />
                            <label htmlFor="d_fec_hr" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_hr"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="eventoHr"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
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
                    <div className="border-gray-300 border my-5"></div>
                </div>
                <div className="grid grid-cols-4 gap-4">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Verum</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_verum_lp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_verum_cp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_verum_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_verum_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_verum"
                                onChange={handleChange}
                                required
                            />
                            <label
                                htmlFor="d_fec_verum"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                     -translate-y-6 origin-[0]"
                            >
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_verum"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_verum"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
                                Evento verum
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF verum: {formData?.s_pdf_verum}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_verum" fieldB64="verum_b64"/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Dbrs</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_dbrs_lp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_dbrs_cp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_dbrs_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_dbrs_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_dbrs"
                                onChange={handleChange}
                                required
                            />
                            <label
                                htmlFor="d_fec_dbrs"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_dbrs"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="s_evento_dbrs" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
                                Evento dbrs
                            </label>
                        </div>
                        <div className="text-xs text-cyan-700 my-3">
                            <span>Archivo PDF dbrs: {formData?.s_pdf_dbrs}</span>
                        </div>
                        <SelectFileCalifEmi name="s_pdf_dbrs" fieldB64="dbrs_b64"/>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Best</span>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_best_lp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_best_cp_n" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_best_lp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Largo Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select defaultValue="default"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
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
                            <label htmlFor="s_best_cp_g" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Corto Plazo Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                placeholder=""
                                name="d_fec_best"
                                onChange={handleChange}
                                required
                            />
                            <label
                                htmlFor="fechaBest"
                                className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Fecha
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
								<textarea
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900
									dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
									focus:border-cyan-700 peer"
                                    name="s_evento_best"
                                    onChange={handleChange}
                                ></textarea>
                            <label htmlFor="eventoBest"
                                   className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                       -translate-y-6 origin-[0]"
                            >
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