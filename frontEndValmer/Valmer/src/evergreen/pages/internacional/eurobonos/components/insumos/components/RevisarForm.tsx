import React from "react";
import {useRevisar} from "./hooks";
import {ButtonContent, useInput} from "../../../../../../../shared";
import {usePersist} from "../../hooks";
import { generateUUID } from "../../../../../../../utils";

export const RevisarForm = () => {

    const {
        searchContributor,
        dataRevisar1,
        sortField,
        sortDirection,
        sortedData,
        handleSortR,
        setSearchContributor } = useRevisar()

        const {
        prices,
        inputRef,
        formatValueForDisplay,
        handlePriceChange}
        = useInput()

    const {
        loadingSave,
        loadingSend,
        handleSave,
        handleSend} = usePersist({
        prices,
        messageSave: " error al guarda-revisa1",
        messageSend: " error al envia-revisa1",
        urlSave: "/internacional/proceso-eurobonos/guarda-revisa1",
        urlSend: "/internacional/proceso-eurobonos/envia-revisa1"})


    const getSortIcon = (field: string) => {
        if (sortField !== field) return <i className="ml-2 fa-solid fa-sort"></i>;
        if (sortDirection === "asc") return <i className="ml-2 fa-solid fa-sort-up"></i>;
        return <i className="ml-2 fa-solid fa-sort-down"></i>;
    };

    return (
        <div className="flex flex-col text-sm mb-5 ml-5 mr-5 animate__animated animate__fadeIn">

            <div className="flex justify-end">
                <div className="form-input w-1/6">
                    <input type="text"
                           name="searchData"
                           id="searchData"
                           placeholder="Buscar"
                           className="w-full"
                           value={searchContributor}
                           onChange={setSearchContributor}
                    />
                </div>
            </div>

            <table className='table -mb-3'>
                <thead className='thead'>
                <tr>
                    <th onClick={() => handleSortR("instrumento")}>
                        Instrumento {getSortIcon("instrumento")}
                    </th>
                    <th onClick={() => handleSortR("bloomberg")}>
                        Bloomberg {getSortIcon("bloomberg")}
                    </th>
                    <th onClick={() => handleSortR("reuters")}>
                        Reuters {getSortIcon("reuters")}
                    </th>
                    <th onClick={() => handleSortR("markit")}>
                        Markit {getSortIcon("markit")}
                    </th>
                    <th onClick={() => handleSortR("precio_promedio")}>
                        Precio promedio {getSortIcon("precio_promedio")
                    }</th>
                    <th onClick={() => handleSortR("precio_ayer")}>
                        Precio ayer {getSortIcon("precio_ayer")}
                    </th>
                    <th onClick={() => handleSortR("variacion")}>
                        Variación % {getSortIcon("variacion")}
                    </th>
                    <th onClick={() => handleSortR("comentario")}>
                        Comentario % {getSortIcon("comentario")}
                    </th>
                </tr>
                </thead>
                <tbody className="tbody">
                {
                    (dataRevisar1.body && dataRevisar1.body.length > 0) ?
                        sortedData.map((item) => (
                            <tr className='tr' key={generateUUID()}>
                                <td>{item.instrumento}</td>
                                <td>{item.bloomberg}</td>
                                <td>{item.reuters}</td>
                                <td>{item.markit}</td>
                                <td>
                                    <input
                                        ref={inputRef}
                                        type="text"
                                        className="block py-1 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                        border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
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
                            <td colSpan={8}>
                                <div className="flex flex-col items-center">
                                    <h1 className="text-center">No hay información</h1>
                                    <div className="w-full border-gray-300 border my-5"></div>
                                </div>
                            </td>
                        </tr>
                }
                </tbody>
            </table>
            <span className="line" />
            <div className="-mt-3">Mostrando {sortedData.length} de {dataRevisar1.body.length} Instrumentos</div>
            <div className="mt-4 justify-center">
                <button className="bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleSave}>
                    <ButtonContent name={"Guardar 1"} loading={loadingSave}/>
                </button>
                <button className="bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleSend}>
                    <ButtonContent name={"Enviar 1"} loading={loadingSend}/>
                </button>
            </div>
        </div>
    );
};
