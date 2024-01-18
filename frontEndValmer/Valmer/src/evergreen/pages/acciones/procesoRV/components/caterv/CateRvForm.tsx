import React, { useEffect, useState } from "react";
import { useCateRv } from "./hooks";
import {ButtonContent, TextAreaLog} from "../../../../../../shared";
import { getCurrentDate } from "../../../../../../utils";

interface CateRvFormProps {
    currentDate: string;
    isProcessSelected: boolean;
}

export const CateRvForm: React.FC<CateRvFormProps> = ({ currentDate, isProcessSelected }) => {
    const {
        checkDescarga,
        checkFormatea,
        loading,
        loadingFormat,
        ShowDescargaCateRv,
        log,
        loadingLogCsv,
        ShowFormateaCateRv,
        handleCheckDescargaLog,
        handleCheckFormateaLog,
        downloadFilesFromUrl,
        handleDescarga,
        handleFormatea
    } = useCateRv();

    const [disabled, setDisabled] = useState(false);

    useEffect(() => {
        if (currentDate && isProcessSelected) {
            const today = getCurrentDate();
            setDisabled(currentDate !== today);
        } else {
            setDisabled(false);
        }
    }, [currentDate, isProcessSelected]);

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
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv} />
                        </button>
                    </div>
                    <TextAreaLog log={log}/>
                </div>
            );

        }
        else {
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
                                    <td className="subtitulo" width="20" align="right">1</td>
                                    <td className="Info_Tabla" align="center">
                                        <input
                                            type="checkbox"
                                            className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                                focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                                focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                            checked={checkDescarga}
                                            onChange={handleCheckDescargaLog}
                                        />
                                    </td>
                                    <td height="35" align="center">
                                        <button
                                            className={`w-44 bg-cyan-700 hover:bg-green-700
                                    text-white py-1 rounded-md px-3 mx-1 ${disabled ? 'disabled pointer-events-none opacity-20' : ''}`}
                                            onClick={handleDescarga}
                                            disabled={disabled}
                                        >
                                            <ButtonContent name="Descarga" loading={loading} />
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td className="subtitulo" width="20" align="right">2</td>
                                    <td className="Info_Tabla" align="center">
                                        <input
                                            type="checkbox"
                                            className="ml-2 mr-4 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded
                                                focus:ring-cyan-700 dark:focus:ring-cyan-700 dark:ring-offset-gray-800
                                                focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                                            checked={checkFormatea}
                                            onChange={handleCheckFormateaLog}
                                        />
                                    </td>
                                    <td height="35" align="center">
                                        <button
                                            className={`w-44 bg-cyan-700 hover:bg-green-700
                                    text-white py-1 rounded-md px-3 mx-1 ${disabled ? 'disabled pointer-events-none opacity-20' : ''}`}
                                            onClick={handleFormatea}
                                            disabled={disabled}
                                        >
                                            <ButtonContent name="Formatea" loading={loadingFormat} />
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
                    {(ShowDescargaCateRv || ShowFormateaCateRv) &&
                        renderLogContent()
                    }
                </tr>
            </tbody>
        </table>
    )
}