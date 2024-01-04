import { ButtonContent } from "../../../../../../shared";
import { useProcesoRV } from "./hooks";
import React from "react";

export const ProcesoRvForm = () => {
    
    const {
        checkPrecalInt,
        checkPrecalNac,
        loadingDs,
        loadingPrecalInt,
        loadingPrecalNac,
        log,
        loadingLogCsv,
        ShowProcesoRvLog,
        handleCheckPrecalNac,
        handleCheckPrecalInt,
        downloadFilesFromUrl,
        handleDsRv,
        handlePrecalInt,
        handlePrecalNac
    } = useProcesoRV()
    
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
                            onClick={downloadFilesFromUrl}>
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv}/>
                        </button>
                    </div>
                    <div
                        className="bg-gray-900 text-green-500 p-2 w-3/4 resize-y overflow-auto max-h-[30rem]"
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
                            <td className="subtitulo" width="20" align="right">-</td>
                            <td className="Info_Tabla" align="center">
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleDsRv}
                                >
                                    <ButtonContent name="DS RV INT" loading={loadingDs}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td className="subtitulo" width="20" align="right">5</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                        focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                        focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkPrecalInt}
                                    onChange={handleCheckPrecalInt}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handlePrecalInt}
                                >
                                    <ButtonContent name="Precalc Int" loading={loadingPrecalInt}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td className="subtitulo" width="20" align="right">6</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                        focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                        focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkPrecalNac}
                                    onChange={handleCheckPrecalNac}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handlePrecalNac}
                                >
                                    <ButtonContent name="Precalc Nac" loading={loadingPrecalNac}/>
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td align="center">
                </td>
            </tr>
            <tr>
                {ShowProcesoRvLog && (
                    renderLogContent()
                )}
            </tr>
            </tbody>
        </table>
    )
}