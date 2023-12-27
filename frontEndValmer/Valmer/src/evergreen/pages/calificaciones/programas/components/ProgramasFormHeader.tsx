import React from "react";
import {
    generateUUID,
    getCatalogs,
} from "../../../../../utils";
import {BarLoader, MoonLoader} from "react-spinners";
import {ButtonContent} from "../../../../../shared";
import {useProgramasFormHeader} from "./hooks";

export const ProgramasFormHeader = () => {

    const {
        tv,
        emisora,
        formData,
        selectedTv,
        triggerErase,
        loading,
        loadingTv,
        loadingSave,
        loadingEmisoras,
        catalog,
        handleSave,
        handleErase,
        handleChange,
        handleTv
    } = useProgramasFormHeader()

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    if (loading || loadingTv || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading || loadingTv ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <div className="mt-4 flex justify-end items-center">
                <button className="btn"
                        onClick={handleSave}>
                    <ButtonContent name="Guardar" loading={loadingSave}/>
                </button>
                <button className="btn"
                        onClick={handleErase}>
                    <span>Borrar</span>
                </button>
            </div>

            <div className="ml-4 mr- mt-10 form-cols-2">
                <div className="relative z-0">
                    <select name="s_tv"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={selectedTv}
                            onChange={handleTv}
                    >
                        <option value="default">...</option>
                        {tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    <label
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        TV
                    </label>
                </div>
                <div className="relative z-0">
                    <select name="s_emisora"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={formData?.s_emisora ?? 'default'}
                            onChange={handleChange}
                    >
                        <option value="default">...</option>
                        {emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150}/>}
                    <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Emisora
                    </label>
                </div>
                <div className="mt-4 relative z-0">
                    <select defaultValue="default"
                            name="n_plazo_calif"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            onChange={handleChange}
                            value={formData?.n_plazo_calif ?? 'default'}
                    >
                        <option value="default">...</option>
                        {getCatalogs(catalog,'PLAZO_CALIF').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Plazo Calificación
                    </label>
                </div>
                <div className="mt-4 relative z-0">
                    <select defaultValue="default"
                            id="n_emisor"
                            name="n_emisor"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            onChange={handleChange}
                            value={formData?.n_emisor ?? 'default'}
                    >
                        <option value="default">...</option>
                        {getCatalogs(catalog,'EMISOR').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Emisor
                    </label>
                </div>
            </div>
        </>
    )
}