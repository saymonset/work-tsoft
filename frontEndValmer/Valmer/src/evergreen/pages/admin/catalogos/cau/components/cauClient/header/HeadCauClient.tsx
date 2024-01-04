import React from "react";
import {Catalogo} from "../../../../../../../../model";
import {getCatalogs} from "../../../../../../../../utils";

export const HeadCauClient = ({catalogCau}: {catalogCau: Catalogo[]}) => {
    return (
        <div className="ml-4 mr- mt-10 form-cols-2">
            <div className="relative z-0">
                <select defaultValue="default"
                        name="n_emp"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                >
                    <option value="default">...</option>
                    {getCatalogs(catalogCau, "CAU_EMPRESA").map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label
                    htmlFor="n_emp"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                >
                    Empresa
                </label>
            </div>
            <div className="relative z-0">
                <select defaultValue="default"
                        id="empresa"
                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                >
                    <option value="default">...</option>
                </select>
                <label
                    htmlFor="empresa"
                    className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                >
                    Cliente
                </label>
            </div>
        </div>
    )
}