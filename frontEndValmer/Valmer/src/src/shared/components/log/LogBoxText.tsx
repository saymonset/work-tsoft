import React from "react";
import {MoonLoader} from "react-spinners";
import {LogProps} from "../../../model";
import {useLog} from "./hooks";

export const LogBoxText = (p: LogProps) => {

    const {terminalVisible, loadingLog, log, handlerLog} = useLog(p)

    const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div
                    className="bg-gray-900 text-green-500 p-2 mt-4 w-full resize-y overflow-auto"
                    dangerouslySetInnerHTML={{ __html: log }}
                    style={{ minHeight: '30rem' }}
                />
            );
        } else {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    return(
        <div>
            <button
                className="bg-cyan-700 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                onClick={handlerLog}
            >
                {terminalVisible ? 'Ocultar Log' : 'Log'}
            </button>

            {terminalVisible && (
                <div className="flex justify-center items-center h-full">
                    {loadingLog ? (
                        <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                    ) : renderLogContent()}
                </div>
            )}
        </div>
    )
}