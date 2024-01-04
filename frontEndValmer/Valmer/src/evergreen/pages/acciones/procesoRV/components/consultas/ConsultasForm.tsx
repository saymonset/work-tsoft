import React, {useState} from "react";
import {ButtonContent} from "../../../../../../shared";
import { useConsultas } from "./hooks";
import { handleFileChange } from "../../../../../../utils";

export const ConsultasForm = () => {

    const {
        ShowIndicadoConsultas,
        log,
        loadingLogCsv,
        loadingInd,
        loadingCargaArchRv,
        selectedFile,
        setSelectedFile,
        setFileBase64,
        handleCarge,
        handleInd,
        downloadFilesFromUrl
    } = useConsultas()

    const tableStyle = {
        border: '0',
        width: '100%',
        height: '500px',
    };

    const [showPublica, setShowPublica] = useState(false);

    const handleShowPublica = () => {
        setShowPublica(!showPublica);
    }

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
                            <td height="35" align="center">
                                <button
                                    className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleInd}
                                >
                                    <ButtonContent name="Indicado" loading={loadingInd}/>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="center">
                                <button
                                    className="w-48 mt-11 mb-14 bg-green-700 hover:bg-green-800 text-white py-1 rounded-md px-3 mx-1"
                                    onClick={handleShowPublica}>
                                    <span>Publica Indice Bursatilidad</span>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                {showPublica &&
                                    <div>
                                        <div className="text-xs text-cyan-700 my-3">
                                            <label htmlFor="file-upload"
                                                   className="relative cursor-pointer bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                                                <span className="text-sm">Examinar...</span>
                                                <input id="file-upload"
                                                       type="file"
                                                       className="hidden"
                                                       onChange={(e) => handleFileChange(e,
                                                           setFileBase64, setSelectedFile)}/>
                                            </label>
                                            <span
                                                className="py-2 px-4 text-cyan-700">{selectedFile?.name ?? 'Ningún archivo seleccionado.'}</span>
                                        </div>
                                        <button
                                            className="mt-8 w-44 bg-cyan-700 hover:bg-green-700
                                                text-white py-1 rounded-md px-3 mx-1"
                                            onClick={handleCarge}
                                        >
                                            <ButtonContent name="Publica WEB" loading={loadingCargaArchRv}/>
                                        </button>
                                    </div>
                                }
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td align="center">
                </td>
            </tr>
            <tr>
                {ShowIndicadoConsultas &&
                    renderLogContent()
                }
            </tr>
            </tbody>
        </table>
    )
}