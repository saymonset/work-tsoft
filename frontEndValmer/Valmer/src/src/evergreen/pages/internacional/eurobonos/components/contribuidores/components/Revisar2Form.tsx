import React from "react";
import {generateUUID} from "../../../../../../../utils";
import {useRevisar2} from "./hooks";
import {ButtonContent, useInput} from "../../../../../../../shared";
import {usePersist} from "../../hooks";

export const Revisar2Form = () => {

   const {
       searchContributor2,
       sortField,
       sortDirection,
       sortedData,
       handleSortR2,
       setSearchContributor2
   } = useRevisar2()

    const {
        prices,
        inputRef,
        formatValueForDisplay,
        handlePriceChange}
        = useInput()

    const {
        loadingFile,
        loadingSave,
        loadingSend,
        handleDownloadFile,
        handleSave,
        handleSend} = usePersist({
        prices,
        messageSave: " error al guarda-revisa2",
        messageSend: " error al envia-revisa2",
        urlSave: "/internacional/proceso-eurobonos/guarda-revisa2",
        urlSend: "/internacional/proceso-eurobonos/envia-revisa2"})

    const getSortIcon = (field: string) => {
        if (sortField !== field) return <i className="ml-2 fa-solid fa-sort"></i>;
        if (sortDirection === "asc") return <i className="ml-2 fa-solid fa-sort-up"></i>;
        return <i className="ml-2 fa-solid fa-sort-down"></i>;
    };

    return (
        <div className="flex flex-col text-sm mt-5 mb-5 ml-5 mr-5 animate__animated animate__fadeIn">

            <div className="flex justify-center -mb-10">
                <div className="form-input w-1/6">
                    <button
                        className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleDownloadFile}>
                        <ButtonContent name="Descargar Archivo" loading={loadingFile}/>
                    </button>
                </div>
            </div>

            <div className="flex justify-end">
                <div className="form-input w-1/6">
                    <input type="text"
                           name="searchData"
                           id="searchData"
                           placeholder="Buscar"
                           className="w-full"
                           value={searchContributor2}
                           onChange={setSearchContributor2}
                    />
                </div>
            </div>

            <table className='table'>
                <thead className='thead'>
                <tr>
                    <th onClick={() => handleSortR2("isin")}>
                        ISIN {getSortIcon("isin")}
                    </th>
                    <th onClick={() => handleSortR2("instrumento")}>
                        Instrumento {getSortIcon("instrumento")}
                    </th>
                    <th onClick={() => handleSortR2("bloomberg")}>
                        Bloomberg {getSortIcon("bloomberg")}
                    </th>
                    <th onClick={() => handleSortR2("reuters")}>
                        Reuters {getSortIcon("reuters")}
                    </th>
                    <th onClick={() => handleSortR2("markit")}>
                        Markit {getSortIcon("markit")}
                    </th>
                    <th onClick={() => handleSortR2("clientes")}>
                        Cliente(s) {getSortIcon("clientes")}
                    </th>
                    <th onClick={() => handleSortR2("precio_promedio")}>
                        Precio promedio {getSortIcon("precio_promedio")}
                    </th>
                    <th onClick={() => handleSortR2("precio_ayer")}>
                        Precio Ayer {getSortIcon("precio_ayer")}
                    </th>
                    <th onClick={() => handleSortR2("variacion")}>
                        Variacion {getSortIcon("variacion")}
                    </th>
                    <th onClick={() => handleSortR2("comentario")}>
                        Comentario {getSortIcon("comentario")}
                    </th>
                </tr>
                </thead>
                <tbody className="tbody">
                {
                    sortedData.length > 0 ? sortedData.map((item) => (
                            <tr className='tr' key={generateUUID()}>
                                <td>{item.isin}</td>
                                <td>{item.instrumento}</td>
                                <td>{item.bloomberg}</td>
                                <td>{item.reuters}</td>
                                <td>{item.markit}</td>
                                <td>
                                    {"clientes" in item &&
                                        item.clientes.map((client) => {
                                            const clienteDetails = Object.values(client)[0];
                                            return (
                                                <div key={generateUUID()} className="flex items-center ml-4 mb-1">
                                                    <span className="text-blue-500">{clienteDetails.nombre}</span>
                                                    <span> : </span>
                                                    <span className="text-green-600 mr-1">{clienteDetails.precio}</span>
                                                    {clienteDetails.cambiaPrecio === "1" && (
                                                        <img src="/img/client.png" alt="Cliente Icon"/>
                                                    )}
                                                </div>
                                            );
                                        })
                                    }
                                </td>
                                <td>
                                    <input
                                        ref={inputRef}
                                        type="text"
                                        className="block py-1 px-0 w-full text-sm text-gray-900 bg-transparent border-0
                                         border-b-2 border-gray-300 appearance-none dark:text-slate-900
                                         dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none
                                         focus:ring-0 focus:border-cyan-700 peer"
                                        value={
                                            prices[item.isin] !== undefined
                                                ? formatValueForDisplay(prices[item.isin])
                                                : formatValueForDisplay(item.precio_promedio)
                                        }
                                        onChange={(e) => handlePriceChange(item.isin, e)}
                                    />
                                </td>
                                <td>{item.precio_ayer}</td>
                                <td>{item.variacion}</td>
                                <td>{item.comentario}</td>
                            </tr>
                        ))
                        :
                        <tr>
                            <td colSpan={10}>
                                <div className="flex flex-col items-center">
                                    <h1 className="mt-8 text-center">No hay información</h1>
                                    <div className="w-full border-gray-300 border my-5"></div>
                                </div>
                            </td>
                        </tr>
                }
                </tbody>
            </table>
            <div className="mt-4 justify-center">
                <button className="bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleSave}>
                    <ButtonContent name={"Guardar 2"} loading={loadingSave}/>
                </button>
                <button className="bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleSend}>
                    <ButtonContent name={"Enviar 2"} loading={loadingSend}/>
                </button>
            </div>
        </div>
    );
}