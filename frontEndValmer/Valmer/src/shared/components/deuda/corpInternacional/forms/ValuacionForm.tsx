import React, { useEffect, useState } from "react";
import {capitalizeDate, generateUUID} from "../../../../../utils";
import {MoonLoader} from "react-spinners";
import {useValuacion} from "./hooks";
import { Modal } from "../../../Modal";
import { ButtonContent } from "../../../ButtonContent";

interface ValuacionFormProps {
    mercado: string
}

export const ValuacionForm: React.FC<ValuacionFormProps> = ({ mercado }) => {

    const {
        isChecked,
        isSelectDate,
        isSelectOption,
        selectDate,
        loadingQuery,
        loadingInst,
        checkedItems,
        catalog,
        query,
        isOpenModal,
        selectedInstr,
        dataActPrecio,
        loadingConsPrecio,
        loadingActPrecio,
        handleConsulta,
        handleSave,
        handleDate,
        handleDel,
        handleChangeOption,
        handleOpenModal,
        handleCloseModal,
        handleChangePrecio,
        handleConsultarPrecio,
        handleActualizarPrecio
    } = useValuacion(mercado)

    const [catalogInstrumentType, setCatalogInstrumentType] = useState<any[]>([]);

    useEffect(() => {
        if (catalog.registros && Object.keys(catalog.registros).length) {
            const requiredEntriesKeys = ['1','8', '2', '7', '3', '4'];
            const requiredEntries = requiredEntriesKeys.map(key => [key, catalog.registros[key]]);
            setCatalogInstrumentType(requiredEntries);
        }
    }, [catalog.registros]);



    if (loadingInst || !catalog) {
        return (
            <div className="mt-4 flex justify-center items-center h-full">
                {loadingInst ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }
    
    return (
        <>
            <div className="mb-4 flex justify-end pr-2">
                <button className="btn" onClick={handleConsulta}>
                    <span>Consulta</span>
                </button>
                <button className="btn" onClick={handleSave}>
                    <span>Guardar</span>
                </button>
            </div>

            <div className="px-3">
                <div className="bg-cyan-700 px-1 text-slate-50">
                    <span>Filtro Características Instrumento</span>
                </div>
                <div className="mt-4 grid grid-cols-4 gap-4">
                    <div className="relative z-0 my-3">
                        <input
                            type="date"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                border-b-2 border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                            placeholder=""
                            value={selectDate}
                            onChange={handleDate}
                            required

                        />
                        <label
                            className="peer-focus:font-medium absolute text-sm text-gray-900 dark:text-gray-900
                                duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0
                                peer-focus:text-cyan-700 peer-focus:dark:text-cyan-700 peer-placeholder-shown:scale-100
                                 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                        >
                            Fecha Val
                        </label>
                        {isSelectDate && (
                            <span
                                className="fontError animate__animated animate__fadeIn">Fecha valor es requerido</span>
                        )}
                    </div>
                    <div className="relative z-0 w-full my-3">
                        <select
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                    border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            name="n_tipo_instrumento"
                            onChange={handleChangeOption}
                            required
                        >
                            <option value="default">...</option>
                            {catalogInstrumentType.map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))
                            }
                        </select>
                        <label
                            className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                -translate-y-6 origin-[0]"
                        >
                            Tipo Instrumento
                        </label>
                        {isSelectOption && (
                            <span className="fontError animate__animated animate__fadeIn">
                                Tipo instrumento es requerido</span>
                        )}
                    </div>
                </div>
            </div>

            {loadingQuery ? (
                <div className="mt-24 flex justify-center items-center">
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                </div>
            ) : (
                <>
                    {isChecked && (
                        <span className="fontError flex justify-end items-end animate__animated animate__fadeIn">
                                Seleccione al menos un instrumento</span>
                    )}
                    <table className='mt-8 table text-xs w-full'>
                        <thead className='thead'>
                        <tr>
                            <th>Instrumento</th>
                            <th>PS MD</th>
                            <th>PL MD</th>
                            <th>PS 24H</th>
                            <th>PL 24H</th>
                            <th>DURACIÓN</th>
                            <th>CONVEX</th>
                            <th>REND</th>
                            <th>ST</th>
                            <th>ELIM MD</th>
                            <th>ELIM 24H</th>
                        </tr>
                        </thead>
                        <tbody className='tbody'>
                        {query?.body?.length > 0 && query.body.map((item) => {
                            if (item.property != "INSTRUMENTO") {
                                return (
                                    <tr key={generateUUID()} className='tr'>
                                        {mercado === "eurobonos" ? (
                                            <td>
                                                <button className="btn-link"
                                                    onClick={() => handleOpenModal(item.property)}
                                                >
                                                    {item.property}
                                                </button>
                                            </td>
                                        ) : (
                                            <td>{item.property}</td>
                                        )}
                                        <td>{item.data.PSMD}</td>
                                        <td>{item.data.PLMD}</td>
                                        <td>{item.data.PL24}</td>
                                        <td>{item.data.PS24}</td>
                                        <td>{item.data.DURACION}</td>
                                        <td>{item.data.CONVEXIDAD}</td>
                                        <td>{item.data.REND}</td>
                                        <td>{item.data.SOBRETASA}</td>
                                        <td>
                                            <input
                                                type="checkbox"
                                                className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                                            dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                                            dark:border-gray-600"
                                                name="b_rnv"
                                                checked={checkedItems["MD_" + item.property] || false}
                                                onChange={(e) => handleDel("MD_" + item.property, e)}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                type="checkbox"
                                                className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                                            dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                                            dark:border-gray-600"
                                                name="b_rnv"
                                                checked={checkedItems["24_" + item.property] || false}
                                                onChange={(e) => handleDel("24_" + item.property, e)}
                                            />
                                        </td>
                                    </tr>
                                )
                            }
                        })}
                        {(Object.keys(query).length === 0 && !isSelectOption) && 
                            <div>
                                <span className="text-center flex justify-end items-end animate__animated animate__fadeIn">
                                    No hay informacion
                                </span>
                            </div>
                        }
                        </tbody>
                    </table>
                    <Modal isOpen={isOpenModal} onClose={handleCloseModal} title="Actualiza TC" modalSize="medium">
                        <div className="w-full -mb-3 flex justify-between">
                            <span>{ selectedInstr }</span>
                            <span>{ capitalizeDate() }</span>
                        </div>
                        <div className="line"/>
                        <div className="w-full">
                            <div className="form-title">PRECIO MERCADO</div>
                            <div className="my-2">
                                <label htmlFor="n_precios">
                                    {selectedInstr}
                                </label>
                                <input 
                                    className="w-1/3 border border-gray-600 ml-2 px-1"
                                    type="text" 
                                    name="n_precio" 
                                    id="n_precio"
                                    value={dataActPrecio.n_precio || ""}
                                    onChange={handleChangePrecio}
                                />
                            </div>

                            <div className="flex mt-4">
                                <button className="btn"
                                    onClick={handleConsultarPrecio}
                                >
                                    <ButtonContent name="Consultar" loading={loadingConsPrecio} />
                                </button>
                                <button className="btn"
                                    onClick={handleActualizarPrecio}
                                >
                                    <ButtonContent name="Actualizar" loading={loadingActPrecio} />
                                </button>
                            </div>
                        </div>
                        {dataActPrecio.title_log && (
                            <div className="w-full border border-gray-600 mt-3">
                                <div className="bg-cyan-700 text-xs m-1 text-white text-center p-0.5 font-bold">
                                    {dataActPrecio.title_log}
                                </div>
                                <div className="px-1.5 py-3">
                                    {dataActPrecio.s_instrumento + " [" + (dataActPrecio.msg_log ?? dataActPrecio.n_precio) + "]"}
                                </div>
                            </div>
                        )}
                    </Modal>
                </>
            )}
        </>
    )
}