import React from "react";
import {TableWalletProps} from "../../../../../../../model";
import {generateUUID} from "../../../../../../../utils";

export const TableWallet = ({
                                searchClientWallet,
                                sortedData,
                                handleSort,
                                getSortIcon,
                                setSearchClient} : TableWalletProps) => {
    return (
        <div className="flex flex-col text-sm mb-44 ml-5 mr-5
                                            animate__animated animate__fadeIn">

            <div className="flex justify-end">
                <div className="form-input w-1/4">
                    <input type="text"
                           name="searchData"
                           id="searchData"
                           placeholder="Buscar"
                           className="w-full"
                           value={searchClientWallet}
                           onChange={setSearchClient}
                    />
                </div>
            </div>


            <table className='table'>
                <thead className='thead'>
                <tr>
                    <th onClick={() => handleSort('fecha')}>
                        Fecha {getSortIcon('fecha')}</th>
                    <th onClick={() => handleSort('institucion')}>
                        Cliente {getSortIcon('institucion')}</th>
                    <th onClick={() => handleSort('instrumento')}>
                        Instrumento {getSortIcon('instrumento')}</th>
                    <th onClick={() => handleSort('precio')}>
                        Precio cartera {getSortIcon('precio')}</th>
                </tr>
                </thead>
                <tbody className="tbody">
                {
                    sortedData.length > 0 ? (
                        sortedData.map((data) => (
                            <tr className='tr' key={generateUUID()}>
                                <td>{data.fecha}</td>
                                <td>{data.institucion}</td>
                                <td>{data.instrumento}</td>
                                <td>{data.precio}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={4}>
                                <div className="mt-8 flex flex-col items-center">
                                    <h1 className="text-center">No hay
                                        información</h1>
                                    <div
                                        className="w-full border-gray-300 border my-5"></div>
                                </div>
                            </td>
                        </tr>
                    )
                }
                </tbody>
            </table>
        </div>
    );
};