import { ButtonContent } from "../../../../../../shared";
import { useSalidas } from "./hooks";
import React from "react";

export const SalidasForm = () => {

    const {
        checkGenerar,
        checkProduccion,
        log,
        IsShowLogBoxOuts,
        loadingGenerar,
        loadingProd,
        loadingLogCsv,
        handleCheckGenerar,
        handleCheckProduccion,
        handleGenerar,
        handleProd,
        handleLogCsv
    } = useSalidas()
    
    const tableStyle = {
        border: '0',
        width: '100%',
        height: '500px',
    };

    const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div className="mt-8 flex flex-col items-center w-full">
                    <div className="w-1/8 mb-4">
                        <button
                            className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                            onClick={handleLogCsv}>
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv}/>
                        </button>
                    </div>
                    <div
                        className="bg-gray-900 text-green-500 p-2 sm:w-full w-3/4 resize-y overflow-auto max-h-[30rem]"
                        dangerouslySetInnerHTML={{ __html: log }}
                        style={{ minHeight: '30rem' }}
                    />
                </div>
            );

        }
        else
        {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    return (
        <table className="ml-8 mt-12" style={tableStyle}>
            <tbody>
            <tr>
                <td valign="top">
                    <table>
                        <tbody>
                        <tr>
                            <td className="subtitulo" width="20" align="right">7</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                        focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                        focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkGenerar}
                                    onChange={handleCheckGenerar}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleGenerar}
                                >
                                    <ButtonContent name="Generar" loading={loadingGenerar}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td className="subtitulo" width="20" align="right">8</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                        focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                        focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkProduccion}
                                    onChange={handleCheckProduccion}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleProd}
                                >
                                    <ButtonContent name="Producción" loading={loadingProd}/>
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    {IsShowLogBoxOuts && (
                        <div className="flex mb-44 mr-44 justify-center items-center ">
                            {renderLogContent()}
                        </div>
                    )}
                </td>
                <td align="center">
                </td>
            </tr>
            </tbody>
        </table>
    )
}