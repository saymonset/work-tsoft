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
        isFieldReqCalifProg,
        refReqCalifProg,
        requiredTvCalifProg,
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
                            ref={refReqCalifProg.s_tv}
                    >
                        <option value="default">...</option>
                        {tv?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="s_tv"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        TV
                    </label>
                    {requiredTvCalifProg && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido TV</span>
                    )}
                </div>
                <div className="relative z-0">
                    <select name="s_emisora"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={formData?.s_emisora ?? 'default'}
                            onChange={handleChange}
                            ref={refReqCalifProg.s_emisora}
                    >
                        <option value="default">...</option>
                        {emisora?.map((item) => (
                            <option key={generateUUID()} value={item}>
                                {item}
                            </option>
                        ))}
                    </select>

                    {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150}/>}
                    <label htmlFor="s_emisora"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Emisora
                    </label>
                    {isFieldReqCalifProg.s_emisora && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido Emisora</span>
                    )}
                </div>
                <div className="mt-4 relative z-0">
                    <select defaultValue="default"
                            name="n_plazo_calif"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            onChange={handleChange}
                            value={formData?.n_plazo_calif ?? 'default'}
                            ref={refReqCalifProg.n_plazo_calif}
                    >
                        <option value="default">...</option>
                        {getCatalogs(catalog,'PLAZO_CALIF').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_plazo_calif"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Plazo Calificación
                    </label>
                    {isFieldReqCalifProg.n_plazo_calif && (
                        <span className="fontError animate__animated animate__fadeIn">
                            Campo requerido Plazo Calificación</span>
                    )}
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
                    <label htmlFor="n_emisor"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Emisor
                    </label>
                </div>
            </div>
        </>
    )
}