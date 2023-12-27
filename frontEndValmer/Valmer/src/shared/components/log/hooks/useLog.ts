import {useState} from "react";
import {fetchDataGetRet, showAlert} from "../../../../utils";
import {LogProps} from "../../../../model";

export const useLog = ({logName}: LogProps) => {

    const [terminalVisible, setTerminalVisible] = useState(false);
    const [loadingLog, setLoadingLog] = useState(false);
    const [log, setLog] = useState<string[]>([]);

    const handlerLog = async () => {
        setTerminalVisible(!terminalVisible);
        if (!terminalVisible) {
            setLoadingLog(true)
            try {
                const response = await fetchDataGetRet(
                    "/log/consulta-log",
                    " ",
                    {logName: logName})
                setLog(response.body);
            } catch (error: any) {
                if (error.message.includes("Network Error")) {
                    await showAlert("error", "Error", "No hay conexión con el servidor");
                } else {
                    await showAlert("error", "Error al obtener log", error.message);
                }
            }
            finally {
                setLoadingLog(false)
            }
        }
    }

    return {
        terminalVisible,
        loadingLog,
        log,
        handlerLog
    }

}