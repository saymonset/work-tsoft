import React from "react";
import {BodyEditCauClient, Catalogo, ConsultaDataEditCauClient} from "../../../../../../../../model";
import {getCatalogs} from "../../../../../../../../utils";

interface DataFormProps {
    Data: ConsultaDataEditCauClient;
    Catalog: Catalogo[];
    handleChange: <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => void;
}

export const DataForm = ({ Data, Catalog, handleChange }: DataFormProps) => {
    return (
        <>
            <div className="mt-14 bg-cyan-700 h-8 w-full">
                <div className="flex items-center justify-center h-full">
                    <span className="text-white text-lg font-bold">Datos</span>
                </div>
            </div>

            <div className="ml-4 mr- mt-10 form-cols-2">
                <div className="relative z-0">
                    <input name="N_CLIENTE"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.N_CLIENTE ?? ""}
                           readOnly
                    />
                    <label
                        htmlFor="N_CLIENTE"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        N_CLIENTE
                    </label>
                </div>
                <div className="relative z-0">
                    <select defaultValue="default"
                            name="N_EMPRESA"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={Data?.N_EMPRESA ?? ""}
                            onChange={handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(Catalog, "CAU_EMPRESA").map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label
                        htmlFor="N_EMPRESA"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        N_EMPRESA
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_NOMBRE"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_NOMBRE ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_NOMBRE"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_NOMBRE
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_PUESTO"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_PUESTO ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_PUESTO"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_PUESTO
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_TELEFONO"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_TELEFONO ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_TELEFONO"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_TELEFONO
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_EMAIL"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_EMAIL ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_EMAIL"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_EMAIL
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_USUARIO"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_USUARIO ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_USUARIO"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_USUARIO
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_PASSWORD"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_PASSWORD ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_PASSWORD"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_PASSWORD
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_AREA"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_AREA ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_AREA"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_AREA
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <select defaultValue="default"
                            name="N_SECTOR"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={Data?.N_SECTOR ?? ""}
                            onChange={handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(Catalog, "CAU_SECTOR").map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label
                        htmlFor="N_SECTOR"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        N_SECTOR
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="S_FAX"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.S_FAX ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="S_FAX"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        S_FAX
                    </label>
                </div>
                <div className="mt-8 relative z-0">
                    <input name="N_HITS"
                           className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                           value={Data?.N_HITS ?? ""}
                           onChange={handleChange}
                    />
                    <label
                        htmlFor="N_HITS"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        N_HITS
                    </label>
                </div>
                <div className="relative z-0 my-3">
                    <input
                        type="date"
                        name="D_FECHA"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                        value={Data?.D_FECHA ?? ""}
                        onChange={handleChange}
                    />
                    <label
                        htmlFor="D_FECHA"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75 -translate-y-6 origin-[0]"
                    >
                        D_FECHA
                    </label>
                </div>
                <div className="mt-4 mb-14 relative z-0">
                    <select defaultValue="default"
                            name="N_STATUS"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            value={Data?.N_STATUS ?? ""}
                            onChange={handleChange}
                    >
                        <option value="default">...</option>
                        {getCatalogs(Catalog, "CAU_STATUS_CLI").map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label
                        htmlFor="N_STATUS"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        N_STATUS
                    </label>
                </div>
            </div>
        </>
    )
}