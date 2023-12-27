import {useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    downloadFiles,
    fetchDataGetSave,
    fetchDataPostNoMsj
} from "../../../../../../utils";
import {ArchivoPreciosCont, UsePersistProps} from "../../../../../../model";

export const usePersist = ({ prices, urlSave, urlSend, messageSave, messageSend }: UsePersistProps) => {
    const [loadingSave, setLoadingSave] = useState(false)
    const [loadingSend, setLoadingSend] = useState(false)
    const [loadingFile, setLoadingFile] = useState(false)

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const filePreciosCont = useSelector((state: RootState<any, any, any>) =>
        state.filePreciosCont) as unknown as ArchivoPreciosCont;

    const handleSave = async () => {
        setLoadingSave(true)
        await fetchDataPostNoMsj(
            urlSave,
            messageSave,
            prices,
            {d_fecha_val: fechaEurobonos})
        setLoadingSave(false)
    }

    const handleSend = async () => {
        setLoadingSend(true)
        await fetchDataGetSave(
            urlSend,
            messageSend,
            {d_fecha: fechaEurobonos})
        setLoadingSend(false)
    }

    const handleDownloadFile = async () => {
        setLoadingFile(true);
        if (filePreciosCont) {
            downloadFiles(filePreciosCont.contenido,
                filePreciosCont.nombreCompleto,
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        } else {
            console.error('No se recibió un archivo válido');
        }
        setLoadingFile(false);
    };

    return {loadingFile, loadingSave, loadingSend, handleDownloadFile, handleSave, handleSend}
}