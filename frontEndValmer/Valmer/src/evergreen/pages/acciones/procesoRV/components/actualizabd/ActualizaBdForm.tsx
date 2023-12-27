import React from "react";
import { useActualizaBd } from "./hooks";
import { ButtonContent } from "../../../../../../shared";

export const ActualizaBdForm = () => {
    
    const {
        checkInternacional,
        checkNacional,
        checkActualiza1b,
        loadingInter,
        loadingNac,
        loadingAct1B,
        ShowActualiza1BActBd,
        log,
        loadingLogCsv,
        ShowNacionalActBd,
        ShowInternacionalActBd,
        handleCheckInternacional,
        handleCheckNacional,
        handleCheckActualiza1b,
        downloadFilesFromUrl,
        handleInter,
        handleNac,
        handleAct1B
    } = useActualizaBd()

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
                        className="bg-gray-900 text-green-500 p-2 w-3/4 resize-y overflow-auto"
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
                            <td className="subtitulo" width="20" align="right">3</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                                focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                                focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkInternacional}
                                    onChange={handleCheckInternacional}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                                text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleInter}
                                >
                                    <ButtonContent name="Internacional" loading={loadingInter}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td className="subtitulo" width="20" align="right">4</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                                focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                                focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkNacional}
                                    onChange={handleCheckNacional}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                                text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleNac}
                                >
                                    <ButtonContent name="Nacional" loading={loadingNac}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td className="subtitulo" width="20" align="right">9</td>
                            <td className="Info_Tabla" align="center">
                                <input
                                    type="checkbox"
                                    className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                                focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                                focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                    checked={checkActualiza1b}
                                    onChange={handleCheckActualiza1b}
                                />
                            </td>
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                                text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleAct1B}
                                >
                                    <ButtonContent name="Actualiza 1B" loading={loadingAct1B}/>
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
                {(ShowActualiza1BActBd || ShowNacionalActBd || ShowInternacionalActBd) &&
                    renderLogContent()
                }
            </tr>
            </tbody>
        </table>
    )
}