import React from "react";
import {getCatalogs} from "../../../../../utils";
import {MoonLoader} from "react-spinners";
import {useEmisorasHeader} from "./hooks";
import {ButtonContent} from "../../../../../shared";

export const EmisorasFormHeader = () => {

    const {
        loadingSave,
        loading,
        catalog,
        handleSave,
        handleChange} = useEmisorasHeader()

    if (loading || !catalog.length) {
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
        <div className="ml-4 mr- mt-10 form-cols-2">
            <div className="relative z-0">
                <select defaultValue="default"
                        name="n_emisor"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                        onChange={handleChange}
                >
                    <option value="default">...</option>
                    {getCatalogs(catalog, 'EMISOR').map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_emisor" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                    Emisora
                </label>
            </div>
            <div className="relative z-0">
                <select defaultValue="default"
                        name="n_pais"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                        onChange={handleChange}
                >
                    <option value="default">...</option>
                    {getCatalogs(catalog, 'PAIS').map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_pais" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                    País
                </label>
            </div>
            <div className="mt-4 mb-4 relative z-0">
                <input
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600 dark:focus:border-cyan-700
                    focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                    type="text"
                    name="s_entidad"
                    placeholder=""
                    onChange={handleChange}
                    required
                />
                <label htmlFor="s_entidad" className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                    Entidad
                </label>
            </div>


            <div className="flex justify-end items-center">
                <button className="btn" onClick={handleSave}>
                    <ButtonContent name="Guardar" loading={loadingSave}/>
                </button>
            </div>
        </div>
    )
}