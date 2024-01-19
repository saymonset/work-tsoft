import React from "react";
import { MoonLoader } from "react-spinners";
import {generateUUID, getCatalogsSub} from "../../../../../../utils";
import {useSubastasForm} from "../../hooks";
import {SubFormProps} from "../../../../../../model";
export const SubastasForm = (p: SubFormProps) => {

    const {
        catalog,
        loading,
        log,
		loadingLogBox,
		isShowLog,
        valueGet,
        valueSet
    } = useSubastasForm(p)

    const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div className="flex flex-col items-center w-3/4">
					<div className="form-title text-center w-full">LOG SUBASTAS</div>
                    <div
                        className="bg-gray-900 text-green-500 p-2 w-full overflow-auto max-h-[30rem]"
                        dangerouslySetInnerHTML={{ __html: log }}
                        style={{ minHeight: '15rem' }}
                    />
                </div>
            );

        }
        else
        {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    if(loading || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={100}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <div className="flex flex-col text-sm mt-5 mb-5 ml-5 mr-5 animate__animated animate__fadeIn">
            {isShowLog ? (
                <div className="flex mb-44 justify-center items-center h-full">
                    {loadingLogBox ? (
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                    ) : renderLogContent()}
                </div>
            ) : (
                <table className='table'>
                    <thead className='thead'>
                    <tr>
                        <th>Fecha Subasta</th>
                        <th>Instrumento</th>
                        <th>Plazo</th>
                        <th>Tasa</th>
                        <th>Fecha Colocación</th>
                        <th>Instrumento</th>
                        <th>Monto</th>
                    </tr>
                    </thead>
                    <tbody className='tbody'>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("tasa_obj", "d_fecha_subasta")}
                                onChange={(e) => valueSet("tasa_obj",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Tasa Objetivo</td>
                        <td>1</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("tasa_obj", "n_tasa")}
                                onChange={(e) => valueSet("tasa_obj",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("tasa_obj", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("tasa_obj",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("cete28", "d_fecha_subasta")}
                                onChange={(e) => valueSet("cete28",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Cete</td>
                        <td>28</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("cete28", "n_tasa")}
                                onChange={(e) => valueSet("cete28",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("cete28", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("cete28",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                    border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("cete28", "s_instr")}
                                onChange={(e) => valueSet("cete28",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'cetes').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("cete28", "n_monto")}
                                onChange={(e) => valueSet("cete28",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>


                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("cete91", "d_fecha_subasta")}
                                onChange={(e) => valueSet("cete91",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Cete</td>
                        <td>91</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("cete91", "n_tasa")}
                                onChange={(e) => valueSet("cete91",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("cete91", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("cete91",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                    border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("cete91", "s_instr")}
                                onChange={(e) => valueSet("cete28",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'cetes').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("cete91", "n_monto")}
                                onChange={(e) => valueSet("cete91",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("cete182", "d_fecha_subasta")}
                                onChange={(e) => valueSet("cete182",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Cete</td>
                        <td>182</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("cete182", "n_tasa")}
                                onChange={(e) => valueSet("cete182",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("cete182", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("cete182",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900
                                    bg-transparent border-0 border-b-2 border-gray-200 appearance-none
                                    dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("cete182", "s_instr")}
                                onChange={(e) => valueSet("cete182",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'cetes').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("cete182", "n_monto")}
                                onChange={(e) => valueSet("cete182",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("cete364", "d_fecha_subasta")}
                                onChange={(e) => valueSet("cete364",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Cete</td>
                        <td>364</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("cete364", "n_tasa")}
                                onChange={(e) => valueSet("cete364",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("cete364", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("cete364",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("cete364", "s_instr")}
                                onChange={(e) => valueSet("cete364",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'cetes').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("cete364", "n_monto")}
                                onChange={(e) => valueSet("cete364",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("bonos3", "d_fecha_subasta")}
                                onChange={(e) => valueSet("bonos3",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Bono</td>
                        <td>3</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("bonos3", "n_tasa")}
                                onChange={(e) => valueSet("bonos3",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("bonos3", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("bonos3",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("bonos3", "s_instr")}
                                onChange={(e) => valueSet("bonos3",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'bonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("bonos3", "n_monto")}
                                onChange={(e) => valueSet("bonos3",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("bonos5", "d_fecha_subasta")}
                                onChange={(e) => valueSet("bonos5",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Bono</td>
                        <td>5</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("bonos5", "n_tasa")}
                                onChange={(e) => valueSet("bonos5",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("bonos5", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("bonos5",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("bonos5", "s_instr")}
                                onChange={(e) => valueSet("bonos5",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'bonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("bonos5", "n_monto")}
                                onChange={(e) => valueSet("bonos5",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("bonos10", "d_fecha_subasta")}
                                onChange={(e) => valueSet("bonos10",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Bono</td>
                        <td>10</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("bonos10", "n_tasa")}
                                onChange={(e) => valueSet("bonos10",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("bonos10", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("bonos10",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("bonos10", "s_instr")}
                                onChange={(e) => valueSet("bonos10",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'bonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("bonos10", "n_monto")}
                                onChange={(e) => valueSet("bonos10",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("bonos20", "d_fecha_subasta")}
                                onChange={(e) => valueSet("bonos20",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Bono</td>
                        <td>20</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("bonos20", "n_tasa")}
                                onChange={(e) => valueSet("bonos20",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("bonos20", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("bonos20",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("bonos20", "s_instr")}
                                onChange={(e) => valueSet("bonos20",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'bonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("bonos20", "n_monto")}
                                onChange={(e) => valueSet("bonos20",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("bonos30", "d_fecha_subasta")}
                                onChange={(e) => valueSet("bonos30",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Bono</td>
                        <td>30</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("bonos30", "n_tasa")}
                                onChange={(e) => valueSet("bonos30",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("bonos30", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("bonos30",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("bonos30", "s_instr")}
                                onChange={(e) => valueSet("bonos30",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'bonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("bonos30", "n_monto")}
                                onChange={(e) => valueSet("bonos30",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("udibonos3", "d_fecha_subasta")}
                                onChange={(e) => valueSet("udibonos3",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>UdiBono</td>
                        <td>3</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("udibonos3", "n_tasa")}
                                onChange={(e) => valueSet("udibonos3",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("udibonos3", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("udibonos3",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("udibonos3", "s_instr")}
                                onChange={(e) => valueSet("udibonos3",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'udibonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("udibonos3", "n_monto")}
                                onChange={(e) => valueSet("udibonos3",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("udibonos5", "d_fecha_subasta")}
                                onChange={(e) => valueSet("udibonos5",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>UdiBono</td>
                        <td>5</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("udibonos5", "n_tasa")}
                                onChange={(e) => valueSet("udibonos5",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("udibonos5", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("udibonos5",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="udibonos5"
                                value={valueGet("udibonos5", "s_instr")}
                                onChange={(e) => valueSet("udibonos5",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'udibonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("udibonos5", "n_monto")}
                                onChange={(e) => valueSet("udibonos5",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("udibonos10", "d_fecha_subasta")}
                                onChange={(e) => valueSet("udibonos10",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>UdiBono</td>
                        <td>10</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("udibonos10", "n_tasa")}
                                onChange={(e) => valueSet("udibonos10",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("udibonos10", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("udibonos10",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("udibonos10", "s_instr")}
                                onChange={(e) => valueSet("udibonos10",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'udibonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("udibonos10", "n_monto")}
                                onChange={(e) => valueSet("udibonos10",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("udibonos20", "d_fecha_subasta")}
                                onChange={(e) => valueSet("udibonos20",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>UdiBono</td>
                        <td>20</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("udibonos20", "n_tasa")}
                                onChange={(e) => valueSet("udibonos20",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("udibonos20", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("udibonos20",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("udibonos20", "s_instr")}
                                onChange={(e) => valueSet("udibonos20",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'udibonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("udibonos20", "n_monto")}
                                onChange={(e) => valueSet("udibonos20",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("udibonos30", "d_fecha_subasta")}
                                onChange={(e) => valueSet("udibonos30",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>UdiBono</td>
                        <td>30</td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("udibonos30", "n_tasa")}
                                onChange={(e) => valueSet("udibonos30",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("udibonos30", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("udibonos30",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <select 

                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                name="s_instr"
                                value={valueGet("udibonos30", "s_instr")}
                                onChange={(e) => valueSet("udibonos30",
                                    "s_instr", e.target.value)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'udibonos').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_monto"
                                value={valueGet("udibonos30", "n_monto")}
                                onChange={(e) => valueSet("udibonos30",
                                    "n_monto", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("inpcq", "d_fecha_subasta")}
                                onChange={(e) => valueSet("inpcq",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>INPC Q</td>
                        <td>
                            <select className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                        border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer text-center"
                                    name="s_plazo"
                                    value={valueGet("inpcq", "s_plazo")}
                                    onChange={(e) => valueSet("inpcq",
                                        "s_plazo", e.target.value)}
                                    required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'plazo').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("inpcq", "n_tasa")}
                                onChange={(e) => valueSet("inpcq",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("inpcq", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("inpcq",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("inpcm", "d_fecha_subasta")}
                                onChange={(e) => valueSet("inpcm",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>INPC M</td>
                        <td>
                            <select className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                        border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer text-center"
                                    name="s_plazo"
                                    value={valueGet("inpcm", "s_plazo")}
                                    onChange={(e) => valueSet("inpcm",
                                        "s_plazo", e.target.value)}
                                    required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'months').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("inpcm", "n_tasa")}
                                onChange={(e) => valueSet("inpcm",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("inpcm", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("inpcm",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("cpp", "d_fecha_subasta")}
                                onChange={(e) => valueSet("cpp",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>CPP</td>
                        <td>
                            <select className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                        border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer text-center"
                                    name="s_plazo"
                                    value={valueGet("cpp", "s_plazo")}
                                    onChange={(e) => valueSet("cpp",
                                        "s_plazo", e.target.value)}
                                    required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'months').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("cpp", "n_tasa")}
                                onChange={(e) => valueSet("cpp",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("cpp", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("cpp",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("sm", "d_fecha_subasta")}
                                onChange={(e) => valueSet("sm",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Salario Minimo</td>
                        <td>
                            <select className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                        border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer text-center"
                                    name="s_plazo"
                                    value={valueGet("sm", "s_plazo")}
                                    onChange={(e) => valueSet("sm",
                                        "s_plazo", e.target.value)}
                                    required
                            >
                                <option value="default">...</option>
                                {getCatalogsSub(catalog, 'years').map((column) => (
                                    <option key={generateUUID()} value={column[1]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("sm", "n_tasa")}
                                onChange={(e) => valueSet("sm",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("sm", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("sm",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("embi", "d_fecha_subasta")}
                                onChange={(e) => valueSet("embi",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>EMBI MX</td>
                        <td>
                            1
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("embi", "n_tasa")}
                                onChange={(e) => valueSet("embi",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("embi", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("embi",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("euro", "d_fecha_subasta")}
                                onChange={(e) => valueSet("euro",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Banxico euro</td>
                        <td>
                            1
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("euro", "n_tasa")}
                        onChange={(e) => valueSet("euro",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("euro", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("euro",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>

                    <tr className='tr'>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_subasta"
                                value={valueGet("yen", "d_fecha_subasta")}
                                onChange={(e) => valueSet("yen",
                                    "d_fecha_subasta", e.target.value)}
                                required
                            />
                        </td>
                        <td>Banxico yen</td>
                        <td>
                            1
                        </td>
                        <td>
                            <input
                                type="text"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                    border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="n_tasa"
                                value={valueGet("yen", "n_tasa")}
                                onChange={(e) => valueSet("yen",
                                    "n_tasa", e.target.value)}
                                placeholder=""
                                required
                            />
                        </td>
                        <td>
                            <input
                                type="date"
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                name="d_fecha_colocacion"
                                value={valueGet("yen", "d_fecha_colocacion")}
                                onChange={(e) => valueSet("yen",
                                    "d_fecha_colocacion", e.target.value)}
                                required
                            />
                        </td>
                    </tr>
                    </tbody>
                </table>
            )}
        </div>
    )
}