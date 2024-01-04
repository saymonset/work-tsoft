import React from "react";
import {getCatalogs} from "../../../../../utils";
import {MoonLoader} from "react-spinners";
import {useProgramasForm} from "./hooks";

export const ProgramasForm = () => {

    const {
        formData,
        triggerErase,
        loading,
        catalogProCarac,
        handleChange} = useProgramasForm()

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    if (loading || !Object.keys(catalogProCarac).length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading ? (
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
                <div className="grid grid-cols-4 gap-4">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Fitch</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_fitch_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_fitch_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Fitch'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_fitch_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_fitch_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Fitch'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_fitch"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_fitch ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha fitch
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Standard & Poors</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_sp_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    value={formData?.n_calif_sp_n ?? 'default'}
                                    onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Standard & Poors'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_sp_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_sp_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Standard & Poors'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_sp"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_sp ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha sp
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Moodys</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_moody_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_moody_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Moodys'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_moody_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_moody_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Moodys'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_moody"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_moody ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha moody
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Hr</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_hr_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_hr_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Hr'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_hr_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_hr_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Hr'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_hr"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_hr ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha hr
                            </label>
                        </div>
                    </div>
                </div>
                <div className="grid grid-cols-1 gap-4">
                    <div className="border-gray-300 border my-5"></div>
                </div>
                <div className="grid grid-cols-4 gap-4">
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Verum</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_verum_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_verum_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Verum'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_verum_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_verum_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['Verum'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_verum"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_verum ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha verum
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Dbrs</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_dbrs_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_dbrs_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['DBRS'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_dbrs_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_dbrs_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['DBRS'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_dbrs"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_dbrs ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha dbrs
                            </label>
                        </div>
                    </div>
                    <div className="grid grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1">Best</span>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_best_n"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_best_n ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['BEST'],'califN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Nacional
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <select name="n_calif_best_g"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                         border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                         dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                    onChange={handleChange}
                                    value={formData?.n_calif_best_g ?? 'default'}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalogProCarac['BEST'],'califG').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                            >
                                Calificación Global
                            </label>
                        </div>
                        <div className="relative z-0 my-3">
                            <input
                                type="date"
                                name="d_fec_best"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                onChange={handleChange}
                                value={formData?.d_fec_best ?? 'default'}
                                placeholder=""
                                required
                            />
                            <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                                Fecha best
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    )
}